package licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import licenses.clients.OrganisationDiscoveryClient;
import licenses.clients.OrganisationFeignClient;
import licenses.clients.OrganisationRestTemplateClient;
import licenses.config.ServiceConfig;
import licenses.model.License;
import licenses.model.Organisation;
import licenses.repository.LicenseRepository;
import licenses.utils.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;

@Service
public class LicenseService {
    private static final Logger logger = Logger.getLogger(LicenseService.class.getName());;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

    @Autowired
    private OrganisationFeignClient organisationFeignClient;
    @Autowired
    private OrganisationRestTemplateClient organisationRestClient;
    @Autowired
    private OrganisationDiscoveryClient organisationDiscoveryClient;

    public License getLicense(String organisationId, String licenseId, String clientType) {
        final License license = licenseRepository.findByOrganisationIdAndLicenseId(organisationId, licenseId);

        final Organisation organisation = retrieveOrganisationInfo(organisationId, clientType);

        return license.withOrganisationName(organisation.getName())
                      .withContactName(organisation.getContactName())
                      .withContactEmail(organisation.getContactEmail())
                      .withContactPhone(organisation.getContactPhone())
                      .withComment(config.getExampleProperty());
    }

    private Organisation retrieveOrganisationInfo(String organisationId, String clientType) {
        Organisation organisation;

        switch(clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organisation = organisationFeignClient.getOrganisation(organisationId);
                break;
            case "rest":
                System.out.println("I am using the test client");
                organisation = organisationRestClient.getOrganisation(organisationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organisation = organisationDiscoveryClient.getOrganisation(organisationId);
                break;
            default:
                organisation = organisationRestClient.getOrganisation(organisationId);
        }

        return organisation;
    }

    @HystrixCommand(
        fallbackMethod = "buildFallbackLicenseList",
        threadPoolKey =  "licenseByOrgThreadPool",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
        },
        commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets",  value = "5")
        })
    public List<License> getLicenseByOrg(String organisationId) {
        logger.fine("getLicenseByOrg Correlation id: " + UserContextHolder.getContext()
                                                                               .getCorrelationId());

        return licenseRepository.findByOrganisationId(organisationId);
    }

    private List<License> buildFallbackLicenseList(String organisationId) {
        return asList(new License().withId("000000-00-00000")
                                   .withOrganisationId(organisationId)
                                   .withProductName("Sorry no licensing information currently available"));
    }

    public void saveLicense(License license) {
        license.withId(randomUUID().toString());
        licenseRepository.save(license);
    }
}