package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class SightingDaoImpl implements SightingDao {
    @Autowired
    JdbcTemplate jdbc;

    // newsfeed sightings
    public List<Sighting> newsFeedSightings() {
        try {
            final String SIGHTINGS_BY_DATE = "SELECT * FROM sightings " +
                    "ORDER BY timestamp " +
                    "DESC LIMIT 10;";
            List<Sighting> newsFeedSightings = jdbc.query(SIGHTINGS_BY_DATE, new SightingMapper());
            return newsFeedSightings;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /* CRUD implementations */
    @Override
    public List<Sighting> getAllSightings() {
        final String SQL_GET_ALL = "SELECT * FROM sightings;";
        List<Sighting> sightings = jdbc.query(SQL_GET_ALL, new SightingMapper());
        associateSuperHeroAndLocation(sightings);
        return sightings;
    }

    private void associateSuperHeroAndLocation(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getLocationID()));
            sighting.setSuperHero(getHeroForSighting(sighting.getSuperID()));
        }
    }

    private Location getLocationForSighting(int id) {
        final String GET_LOCATION = "SELECT * FROM locations " +
                "WHERE locationID = ?;";
        return jdbc.queryForObject(GET_LOCATION, new LocationDaoImpl.LocationMapper(), id);
    }

    private SuperHero getHeroForSighting(int id) {
        final String GET_HERO = "SELECT * FROM superHeroes " +
                "WHERE superID = ?;";
        return jdbc.queryForObject(GET_HERO, new SuperHeroesDaoImpl.SuperHeroMapper(), id);
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String SQL_ADD = "INSERT INTO sightings(sightingID,Timestamp,"
                + "superID,locationID) "
                + "VALUES(?,?,?,?)";

        jdbc.update(SQL_ADD, sighting.getSightingID(), sighting.getTimestamp(),
                sighting.getLocationID(), sighting.getSuperID());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newId);

        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String SQL_UPDATE = "UPDATE sightings SET locationID = ?,"
                + " timestamp = ?, superID = ? WHERE sightingID = ?";

        jdbc.update(SQL_UPDATE, sighting.getLocationID(), sighting.getTimestamp(),
                sighting.getSuperID(), sighting.getSightingID());

    }

    @Override
    public void deleteSightingByID(int sightingID) {
        final String SQL_DELETE = "DELETE FROM sightings WHERE sightingID = ?;";
        jdbc.update(SQL_DELETE, sightingID);
    }

    /* Additional functionality */
    @Override
    public Sighting getSightingByID(int sightingID) {
        try {
            final String SQL_GET = "SELECT * FROM sightings WHERE sightingID = ?;";
            return jdbc.queryForObject(SQL_GET, new SightingMapper(), sightingID);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("sightingID"));
            sighting.setTimestamp(rs.getString("Timestamp"));
            sighting.setSuperID(rs.getInt("superID"));
            sighting.setLocationID(rs.getInt("locationID"));
            return sighting;
        }
    }
}

