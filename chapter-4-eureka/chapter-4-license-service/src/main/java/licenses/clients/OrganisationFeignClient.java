package licenses.clients;

import licenses.model.Organisation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient("organisation-service")
public interface OrganisationFeignClient {
    @RequestMapping(method = GET,
                    value = "/v1/organisations/{organisationId}",
                    consumes = "application/json")
    Organisation getOrganisation(@PathVariable("organsiationId") String organisationId);
}