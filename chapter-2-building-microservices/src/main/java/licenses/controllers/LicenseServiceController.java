package licenses.controllers;

import licenses.model.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/v1/organisations/{organisationId}/licenses")
public class LicenseServiceController {

    @RequestMapping(value = "/{licenseId}", method = GET)
    public License getLicenses(
            @PathVariable("organisationId") String organisationId,
            @PathVariable("licenseId") String licenseId) {
        return new License().withId(licenseId)
                            .withProductName("Teleco")
                            .withLicenseType("Seat")
                            .withOrganisationId("TestOrg");
    }
}