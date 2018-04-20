package licenses.clients;

import licenses.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class OrganisationDiscoveryClient {
    @Autowired
    private DiscoveryClient discoveryClient;

    public Organisation getOrganisation(String organisationId) {
        final RestTemplate restTemplate = new RestTemplate();
        final List<ServiceInstance> instances = discoveryClient.getInstances("organisation-service");

        if (instances.isEmpty()) {
            return null;
        }

        final String serviceUri = String.format("%s/v1/organisations/%s", instances.get(0)
                                                                                   .getUri()
                                                                                   .toString(),
                organisationId);

        final ResponseEntity<Organisation> restExchange = restTemplate.exchange(
                serviceUri,
                GET,
                null,
                Organisation.class,
                organisationId
        );

        return restExchange.getBody();
    }
}