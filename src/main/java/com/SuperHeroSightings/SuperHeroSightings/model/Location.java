package com.SuperHeroSightings.SuperHeroSightings.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class Location {
    private int locationID;

    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 50, message = "Location name must be less than 50 characters.")
    private String locationName;

    @NotBlank(message = "Location description must not be empty.")
    @Size(max = 250, message = "Location description must be less than 250 characters.")
    private String locationDescription;

    @NotBlank(message = "Latitude must not be empty.")
    @Pattern(regexp=
            "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$",
            message = "Must be a numeric latitude between -90.000000 and 90.000000")
    private String latitude;

    @NotBlank(message = "Longitude must not be empty.")
    @Pattern(regexp =
            "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$",
            message = "Must be a numeric longitude between -180.000000 and 180.000000")
    private String longitude;

    @NotBlank(message = "Location address must not be empty.")
    @Size(max = 500, message = "Location address must be less than 500 characters.")
    private String locationAddress;

    @NotBlank(message = "Location city must not be empty.")
    @Size(max = 100, message = "Location city must be less than 100 characters.")
    private String locationCity;

    @NotBlank(message = "Location state must not be empty.")
    @Size(min = 2, max = 2, message = "Location state (abbreviation) must be 2 characters.")
    private String locationState;

    @NotBlank(message = "Location zip must not be empty.")
    @Size(min = 5, max = 5, message = "Location zip must be 5 characters.")
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
