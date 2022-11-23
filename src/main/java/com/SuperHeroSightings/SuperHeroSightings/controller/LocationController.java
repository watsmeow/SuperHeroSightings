package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;

public class LocationController {
    // Declare Dao objects
    private final LocationDao locationDao;

    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
    }


}
