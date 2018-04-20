package licenses.model;

import javax.persistence.*;

@Entity
@Table(name = "licenses")
public class License {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    @Column(name = "organisation_id", nullable = false)
    private String organisationId;
    @Transient
    private String organisationName ="";
    @Transient
    private String contactName = "";
    @Transient
    private String contactEmail = "";
    @Transient
    private String contactPhone = "";
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name = "comment")
    private String comment;

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public License withOrganisationName(String organisationName) {
        this.organisationName = organisationName;
        return this;
    }

    public License withContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public License withContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public License withContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }
}