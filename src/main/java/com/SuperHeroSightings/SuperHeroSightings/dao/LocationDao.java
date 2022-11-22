package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Location;

import java.util.List;

public interface LocationDao {

    List<Location> getAllLocations();

    Location getLocationById(int id);

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int locationId);
}
