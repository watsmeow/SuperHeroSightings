package com.SuperHeroSightings.SuperHeroSightings.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Sighting {

    private int sightingID;

    private Timestamp timestamp;

    private int superID;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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
