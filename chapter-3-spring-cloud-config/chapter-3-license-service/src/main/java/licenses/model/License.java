package licenses.model;

public class License {
    private String licenseId;
    private String productName;
    private String licenseType;
    private String organisationId;

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
}