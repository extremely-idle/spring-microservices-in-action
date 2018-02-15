package licenses.controllers;

import licenses.config.ServiceConfig;
import licenses.model.License;
import licenses.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/v1/organisations/{organisationId}/licenses")
public class LicenseServiceController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/", method = GET)
    public List<License> getLicenses(@PathVariable("organisationId") String organisationId) {
        return licenseService.getLicenseByOrg(organisationId);
    }

    @RequestMapping(value = "/{licenseId}", method = GET)
    public License getLicenses(
            @PathVariable("organisationId") String organisationId,
            @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(organisationId, licenseId);
    }

    @RequestMapping(value = "{licenseId}", method = PUT)
    public String updateLicense(@PathVariable("licenseId") String licenseId) {
        return "This is the put";
    }

    @RequestMapping(value = "/", method = POST)
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @RequestMapping(value = "{licenseId}", method = DELETE)
    @ResponseStatus(NO_CONTENT)
    public String deleteLicense(@PathVariable("licenseId") String licenseId) {
        return "This is the delete";
    }
}