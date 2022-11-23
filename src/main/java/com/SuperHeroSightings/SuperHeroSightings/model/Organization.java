package com.SuperHeroSightings.SuperHeroSightings.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Organization {

    private int orgID;

    private String orgName;

    private String orgDescription;

    private int superID;

    private String orgAddress;

    private String orgCity;

    private String orgState;

    private String orgZip;

    private String orgPhoneNumber;

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

    public int getSuperID() {
        return superID;
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

    // Setters
    public void setOrgID() {
        this.orgID = orgID;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public void setSuperID() {
        this.superID = superID;
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
}
