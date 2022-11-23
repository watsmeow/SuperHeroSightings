package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;

import java.util.List;

public interface SuperHeroesDao {

    List<SuperHero> getAllHeroes();

    SuperHero getSuperHeroById(int id);

    SuperHero addSuperHero(SuperHero superHero);

    void updateSuperHero(SuperHero superHero);

    void deleteSuperHeroById(int superID);
}
