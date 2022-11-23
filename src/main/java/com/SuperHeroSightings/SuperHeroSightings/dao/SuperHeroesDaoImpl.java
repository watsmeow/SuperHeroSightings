package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
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
public class SuperHeroesDaoImpl implements SuperHeroesDao{

    @Autowired
    JdbcTemplate jdbc;

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

    @Override
    public List<SuperHero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM superheroes;";
        return jdbc.query(SELECT_ALL_HEROES,new SuperHeroMapper());
    }

    @Override
    public SuperHero getSuperHeroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superheroes WHERE superID=?;";
            return jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperHeroMapper(), id);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero superHero) {
        final String INSERT_SUPERHERO = "INSERT INTO superheroes (superName," +
                "superDescription,superPower) VALUES (?,?,?);";
        jdbc.update(INSERT_SUPERHERO,superHero.getSuperName(),superHero.getSuperDescription(),
                superHero.getSuperName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()",Integer.class);
        superHero.setSuperID(newId);
        return superHero;
    }

    @Override
    public void updateSuperHero(SuperHero superHero) {
        final String UPDATE_SUPERHERO = "UPDATE superheroes SET superName =?," +
                "superDescription=?,superPower=?;";
        jdbc.update(UPDATE_SUPERHERO,superHero.getSuperName(),
                superHero.getSuperDescription(),superHero.getSuperPower());
    }

    @Override
    @Transactional
    public void deleteSuperHeroById(int superID) {
        final String DELETE_SUPER_TO_ORG_MAPPING = "DELETE FROM supertoorgmapping" +
                "WHERE superID=?;";
        jdbc.update(DELETE_SUPER_TO_ORG_MAPPING,superID);

        final String DELETE_SIGHTINGS = "DELETE FROM sightings" +
                "WHERE superID=?;";
        jdbc.update(DELETE_SIGHTINGS,superID);

        final String DELETE_SUPERHERO_BY_ID = "DELETE FROM superheroes" +
                "WHERE superID=?;";
        jdbc.update(DELETE_SUPERHERO_BY_ID,superID);

    }
}
