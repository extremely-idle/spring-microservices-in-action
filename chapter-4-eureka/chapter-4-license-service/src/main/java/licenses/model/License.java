package licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    @Column(name = "organisation_id", nullable = false)
    private String organisationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name = "comment")
    private String comment;

    public License withId(String licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public License withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public License withLicenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }

    public License withOrganisationId(String organisationId) {
        this.organisationId = organisationId;
        return this;
    }

    public License withComment(String comment) {
        this.comment = comment;
        return this;
    }
}