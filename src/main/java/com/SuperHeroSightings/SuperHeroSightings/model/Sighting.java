package com.SuperHeroSightings.SuperHeroSightings.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Sighting {

    private int sightingID;

    @NotEmpty(message = "Timestamp must not be blank")
    @Pattern(regexp=
            "^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2}$",
            message = "Must be valid date.")
    private String timestamp;

    //    @NotBlank(message = "Super name must not be blank")
//    @Size(max = 50, message = "Superhero name must be fewer than 50 characters.")
    private int superID;

    //    @NotBlank(message = "Location name must not be blank")
//    @Size(max = 50, message = "Location name must be fewer than 50 characters.")
    private int locationID;

    private Location location;

    private SuperHero superHero;

    public Location getLocation() {
        return location;
    }

    public SuperHero getSuperHero() {
        return superHero;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSuperHero(SuperHero superHero) {
        this.superHero = superHero;
    }

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getSuperID() {
        return superID;
    }

    public void setSuperID(int superID) {
        this.superID = superID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return sightingID == sighting.sightingID && superID == sighting.superID && locationID == sighting.locationID && timestamp.equals(sighting.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightingID, timestamp, superID, locationID);
    }
}

