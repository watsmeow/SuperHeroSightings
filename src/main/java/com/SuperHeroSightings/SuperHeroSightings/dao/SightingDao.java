package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;

import java.util.List;

public interface SightingDao {
    List<Sighting> getAllSightings();
    Sighting getSightingByID(int sightingID);

//    Sighting getSightingByTime(int sightingID);
    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingByID(int sightingID);

    List<Sighting> newsFeedSightings();


}
