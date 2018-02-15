package licenses.services;

import licenses.config.ServiceConfig;
import licenses.model.License;
import licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

    public License getLicense(String organisationId, String licenseId) {
        final License license = licenseRepository.findByOrganisationIdAndLicenseId(organisationId, licenseId);

        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicenseByOrg(String organisationId) {
        return licenseRepository.findByOrganisationId(organisationId);
    }

    public void saveLicense(License license) {
        license.withId(randomUUID().toString());
        licenseRepository.save(license);
    }
}