package licenses.clients;

import licenses.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Component
public class OrganisationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organisation getOrganisation(String organisationId) {
        ResponseEntity<Organisation> restExchange = restTemplate.exchange(
            "http://organisation-service/v1/organisations/{organisationId}",
            GET,
            null,
            Organisation.class,
            organisationId);

        return restExchange.getBody();
    }
}
