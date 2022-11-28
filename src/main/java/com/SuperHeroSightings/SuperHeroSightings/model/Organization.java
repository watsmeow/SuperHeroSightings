package com.SuperHeroSightings.SuperHeroSightings.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Organization {

    private int orgID;

    @NotBlank(message = "Name must not be blank")
    private String orgName;

    @NotBlank(message = "Description must not be blank")
    private String orgDescription;

    @NotBlank(message = "Address must not be blank")
    private String orgAddress;

    @NotBlank(message = "City must not be blank")
    private String orgCity;

    @NotBlank(message = "State must not be blank")
    @Size(max = 2, message="Name must be 2 characters")
    private String orgState;

    @NotBlank(message = "Zip must not be blank")
    private String orgZip;

    @NotBlank(message = "Phone number must not be blank")
    private String orgPhoneNumber;

    private int superID;

    // Getters
    public int getOrgID() {
        return orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public String getOrgState() {
        return orgState;
    }

    public String getOrgZip() {
        return orgZip;
    }

    public String getOrgPhoneNumber() {
        return orgPhoneNumber;
    }

    public int getSuperID() {
        return superID;
    }

    // Setters
    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

    public void setOrgZip(String orgZip) {
        this.orgZip = orgZip;
    }

    public void setOrgPhoneNumber(String orgPhoneNumber) {
        this.orgPhoneNumber = orgPhoneNumber;
    }

    public void setSuperID(int superID) {
        this.superID = superID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization organization = (Organization) o;
        return orgID == organization.orgID
                && Objects.equals(orgName, organization.orgName)
                && Objects.equals(orgDescription, organization.orgDescription)
                && Objects.equals(orgAddress, organization.orgAddress)
                && Objects.equals(orgCity, organization.orgCity)
                && Objects.equals(orgState, organization.orgState)
                && Objects.equals(orgZip, organization.orgZip)
                && Objects.equals(orgPhoneNumber, organization.orgPhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgName, orgDescription, orgAddress, orgCity, orgState, orgZip, orgPhoneNumber);
    }
}
