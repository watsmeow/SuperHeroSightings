package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuperHeroesDao {

    List<SuperHero> getAllHeroes();

    SuperHero getSuperHeroById(int id);

    SuperHero addSuperHero(SuperHero superHero);

    void updateSuperHero(SuperHero superHero);

    void deleteSuperHeroById(int superID);

    List<Organization> getSuperHeroOrganizations(SuperHero superHero);

    List<Location> getSuperHeroLocation(SuperHero superHero);

    List<SuperPower> getAllSuperPowers();

    SuperPower getSuperPowerById(int id);

    @Transactional
    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    @Transactional
    void deleteSuperPowerById(int superPowerID);
}
