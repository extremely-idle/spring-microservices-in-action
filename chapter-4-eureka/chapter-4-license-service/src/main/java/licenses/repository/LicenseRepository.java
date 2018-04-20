package licenses.repository;

import licenses.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrganisationId(String organisationId);

    License findByOrganisationIdAndLicenseId(String organisationId, String licenseId);
}