package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;

import java.util.List;

public interface SuperHeroesDao {

    List<SuperHero> getAllHeroes();

    SuperHero getSuperHeroById(int id);

    SuperHero addSuperHero(SuperHero superHero);

    void updateSuperHero(SuperHero superHero);

    void deleteSuperHeroById(int superID);

    List<Organization> getSuperHeroOrganizations(SuperHero superHero);

    Location getSuperHeroLocation(int sightingID);
}
