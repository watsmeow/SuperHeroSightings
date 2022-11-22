package com.SuperHeroSightings.SuperHeroSightings.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    // Declare Location variables
    private int locationID;
    private String locationName;
    private String locationDescription;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationAddress;
    private String locationCity;
    private String locationState;
    private String locationZip;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationZip() {
        return locationZip;
    }

    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationID == location.locationID
                && Objects.equals(locationName, location.locationName)
                && Objects.equals(locationDescription, location.locationDescription)
                && Objects.equals(latitude, location.latitude)
                && Objects.equals(longitude, location.longitude)
                && Objects.equals(locationAddress, location.locationAddress)
                && Objects.equals(locationCity, location.locationCity)
                && Objects.equals(locationState, location.locationState)
                && Objects.equals(locationZip, location.locationZip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationID, locationName, locationDescription,
                latitude, longitude, locationAddress, locationCity,
                locationState, locationZip);
    }
}
