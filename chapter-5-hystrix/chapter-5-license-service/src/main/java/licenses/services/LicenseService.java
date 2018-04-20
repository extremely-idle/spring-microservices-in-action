package licenses.services;

import licenses.clients.OrganisationDiscoveryClient;
import licenses.clients.OrganisationFeignClient;
import licenses.clients.OrganisationRestTemplateClient;
import licenses.config.ServiceConfig;
import licenses.model.License;
import licenses.model.Organisation;
import licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class LicenseService {

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
        Organisation organisation = null;

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

    public List<License> getLicenseByOrg(String organisationId) {
        return licenseRepository.findByOrganisationId(organisationId);
    }

    public void saveLicense(License license) {
        license.withId(randomUUID().toString());
        licenseRepository.save(license);
    }
}