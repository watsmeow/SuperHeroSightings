package com.SuperHeroSightings.SuperHeroSightings.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Organization {

    private int orgID;

    @NotBlank(message = "Organization Name must not be blank")
    @Size(max = 50, message = "Organization Name cannot be more than 50 characters.")
    private String orgName;

    @NotBlank(message = "Organization Description must not be blank")
    @Size(max = 250, message = "Organization Description cannot be more than 250 characters")
    private String orgDescription;

    @NotBlank(message = "Organization Address must not be blank")
    @Size(max = 500, message = "Organization Address cannot be more than 500 characters")
    private String orgAddress;

    @NotBlank(message = "Organization City must not be blank")
    @Size(max = 100, message = "Organization City cannot be more than 100 characters.")
    private String orgCity;

    @NotBlank(message = "Organization State must not be blank")
    @Size(min = 2, max = 2, message = "Organization State must be 2 characters")
    @Pattern(regexp = "[a-zA-Z]{2}", message = "Organization state must be 2 letters (Ex: TX).")
    private String orgState;

    @NotBlank(message = "Organization Zip must not be blank")
    @Size(min = 5, max = 5, message = "Organization zip must be 5 characters")
    private String orgZip;

    @NotBlank(message = "Organization Phone number must not be blank")
    @Size(max = 12, message = "Organization Phone Number cannot be more than 12 characters.")
    private String orgPhoneNumber;

    @NotNull(message = "SuperHero ID must not be null.")
    private int superID;

    private int orgAddressID;
    private int orgPhoneNumberID;

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

    public int getOrgAddressID() {
        return orgAddressID;
    }

    public int getOrgPhoneNumberID() {
        return orgPhoneNumberID;
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

    public void setOrgAddressID(int orgAddressID) {
        this.orgAddressID = orgAddressID;
    }

    public void setOrgPhoneNumberID(int orgPhoneNumberID) {
        this.orgPhoneNumberID = orgPhoneNumberID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return orgID == that.orgID && superID == that.superID
                && orgAddressID == that.orgAddressID
                && orgPhoneNumberID == that.orgPhoneNumberID
                && Objects.equals(orgName, that.orgName)
                && Objects.equals(orgDescription, that.orgDescription)
                && Objects.equals(orgAddress, that.orgAddress)
                && Objects.equals(orgCity, that.orgCity)
                && Objects.equals(orgState, that.orgState)
                && Objects.equals(orgZip, that.orgZip)
                && Objects.equals(orgPhoneNumber, that.orgPhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgID, orgName, orgDescription, orgAddress,
                orgCity, orgState, orgZip, orgPhoneNumber, superID,
                orgAddressID, orgPhoneNumberID);
    }
}
