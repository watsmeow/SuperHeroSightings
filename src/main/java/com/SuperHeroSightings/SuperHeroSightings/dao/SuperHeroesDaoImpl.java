package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SuperHeroesDaoImpl implements SuperHeroesDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<SuperHero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM superheroes;";
        return jdbc.query(SELECT_ALL_HEROES, new SuperHeroMapper());
    }

    @Override
    public SuperHero getSuperHeroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superheroes WHERE superID=?;";
            return jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperHeroMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero superHero) {
        final String INSERT_SUPERHERO = "INSERT INTO superheroes (superName," +
                "superDescription,superPower) VALUES (?,?,?);";
        jdbc.update(INSERT_SUPERHERO, superHero.getSuperName(),
                superHero.getSuperDescription(),
                superHero.getSuperPower());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superHero.setSuperID(newId);

        final String GET_POWER = "SELECT sp.* from superPowers sp " +
                "JOIN superHeroes sh ON sh.superPower = sp.superPowerName " +
                "WHERE sp.superPowerName = ?";
        SuperPower power = jdbc.queryForObject(GET_POWER,
                new SuperPowerMapper(), superHero.getSuperPower());

        final String INSERT_HEROES_POWERS = "INSERT INTO heroesPowers " +
                "(superID,superPowerID) VALUES (?,?)";
        jdbc.update(INSERT_HEROES_POWERS, superHero.getSuperID(), power.getSuperPowerID());

        return superHero;
    }

    @Override
    @Transactional
    public void updateSuperHero(SuperHero superHero) {
        final String GET_POWER = "SELECT * FROM superPowers WHERE superPowerName = ?";
        SuperPower power = jdbc.queryForObject(GET_POWER,
                new SuperPowerMapper(), superHero.getSuperPower());

        final String UPDATE_HERO_POWER = "UPDATE heroesPowers SET superPowerID=? " +
                "WHERE superID=?";
        jdbc.update(UPDATE_HERO_POWER, power.getSuperPowerID(), superHero.getSuperID());

        final String UPDATE_SUPERHERO = "UPDATE superheroes SET superName =?," +
                "superDescription=?,superPower=? WHERE superID=?;";
        jdbc.update(UPDATE_SUPERHERO, superHero.getSuperName(),
                superHero.getSuperDescription(), superHero.getSuperPower(),
                superHero.getSuperID());
    }

    @Override
    @Transactional
    public void deleteSuperHeroById(int superID) {
        final String DELETE_SUPER_TO_ORG_MAPPING = "DELETE FROM supertoorgmapping" +
                " WHERE superID=?;";
        jdbc.update(DELETE_SUPER_TO_ORG_MAPPING, superID);

        final String DELETE_SIGHTINGS = "DELETE FROM sightings" +
                " WHERE superID=?;";
        jdbc.update(DELETE_SIGHTINGS, superID);

        final String DELETE_SUPERPOWERS = "DELETE FROM heroesPowers" +
                " WHERE superID=?;";
        jdbc.update(DELETE_SUPERPOWERS, superID);

        final String DELETE_SUPERHERO_BY_ID = "DELETE FROM superheroes" +
                " WHERE superID=?;";
        jdbc.update(DELETE_SUPERHERO_BY_ID, superID);
    }

    @Override
    public List<Organization> getSuperHeroOrganizations(SuperHero superHero) {
       final String SELECT_ALL_ORGANIZATIONS_SUPERHERO = "SELECT * FROM orgs o " +
                "LEFT JOIN orgPhoneNumbers opn ON (opn.phoneNumberID = o.orgPhoneNumberID) " +
                "LEFT JOIN orgAddresses oa USING (orgAddressID) " +
                "LEFT JOIN superToOrgMapping s USING (orgID)" +
                "WHERE s.superID = ?;";

        return jdbc.query(SELECT_ALL_ORGANIZATIONS_SUPERHERO,
                new OrganizationDaoImpl.OrganizationMapper(),
                superHero.getSuperID());
    }

    @Override
    public List<Location> getSuperHeroLocation(SuperHero superHero) {
        final String SELECT_LOCATIONS_SUPERHERO = "SELECT loc.* FROM sightings s" +
                " INNER JOIN locations loc ON s.locationID = loc.locationID " +
                " INNER JOIN superheroes sh ON s.superID = sh.superID" +
                " WHERE s.superID = ?;";
        return jdbc.query(SELECT_LOCATIONS_SUPERHERO, new LocationDaoImpl.LocationMapper(),
                superHero.getSuperID());

    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        final String SELECT_ALL_POWERS = "SELECT * FROM superPowers;";
        return jdbc.query(SELECT_ALL_POWERS, new SuperPowerMapper());
    }

    @Override
    public SuperPower getSuperPowerById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superPowers WHERE superPowerID=?;";
            return jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperPowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERHERO = "INSERT INTO superPowers (superPowerName) VALUES (?);";

        jdbc.update(INSERT_SUPERHERO, superPower.getSuperPowerName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setSuperPowerID(newId);
        return superPower;
    }

    @Override
    @Transactional
    public void updateSuperPower(SuperPower superPower) {
        // Get old power name
        final String GET_OLD_POWER = "SELECT * FROM superPowers WHERE superPowerID = ?";
        SuperPower oldPower = jdbc.queryForObject(GET_OLD_POWER, new SuperPowerMapper(),
                superPower.getSuperPowerID());

        // Update superPowers table
        final String UPDATE_SUPERPOWER =
                "UPDATE superPowers SET superPowerName = ? WHERE superPowerID = ?;";
        jdbc.update(UPDATE_SUPERPOWER, superPower.getSuperPowerName(),superPower.getSuperPowerID());

        // Update superHeroes table
        final String UPDATE_SUPERHERO =
                "UPDATE superHeroes SET superPower = ? WHERE superPower = ?";
        jdbc.update(UPDATE_SUPERHERO, superPower.getSuperPowerName(), oldPower.getSuperPowerName());
    }

    @Override
    @Transactional
    public void deleteSuperPowerById(int superPowerID) {
        // Delete from heroesPowers table
        final String DELETE_HEROES_POWERS = "DELETE FROM heroesPowers" +
                " WHERE superPowerID=?;";
        jdbc.update(DELETE_HEROES_POWERS, superPowerID);

        // Get SuperPower object from superPowerID passed into method
        final String GET_POWER = "SELECT * FROM superPowers " +
                "WHERE superPowerID = ?";
        SuperPower superPower =
                jdbc.queryForObject(GET_POWER, new SuperPowerMapper(), superPowerID);

        // Get all heroes with the power
        final String GET_HEROES_WITH_POWER = "SELECT * FROM superHeroes " +
                "WHERE superPower = ?";
        List<SuperHero> heroesWithPower = jdbc.query(GET_HEROES_WITH_POWER,
                new SuperHeroMapper(), superPower.getSuperPowerName());

        // Delete from superToOrgMapping
        final String DELETE_SUPER_TO_ORG = "DELETE FROM superToOrgMapping " +
                "WHERE superID = ?";
        for (SuperHero superHero : heroesWithPower) {
            jdbc.update(DELETE_SUPER_TO_ORG, superHero.getSuperID());
        }

        // Delete SuperHero
        final String DELETE_SUPER_HERO = "DELETE FROM superHeroes " +
                "WHERE superPower = ?";
        jdbc.update(DELETE_SUPER_HERO, superPower.getSuperPowerName());

        // Delete superPower
        final String DELETE_SUPERPOWER_BY_ID = "DELETE FROM superPowers" +
                " WHERE superPowerID=?;";
        jdbc.update(DELETE_SUPERPOWER_BY_ID, superPowerID);
    }

    public static final class SuperHeroMapper implements RowMapper<SuperHero> {

        @Override
        public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
            SuperHero superHero = new SuperHero();
            superHero.setSuperID(rs.getInt("superID"));
            superHero.setSuperName(rs.getString("superName"));
            superHero.setSuperDescription(rs.getString("superDescription"));
            superHero.setSuperPower(rs.getString("superPower"));
            return superHero;
        }
    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
            SuperPower superPower = new SuperPower();
            superPower.setSuperPowerID(rs.getInt("superPowerID"));
            superPower.setSuperPowerName(rs.getString("superPowerName"));
            return superPower;
        }
    }
}
