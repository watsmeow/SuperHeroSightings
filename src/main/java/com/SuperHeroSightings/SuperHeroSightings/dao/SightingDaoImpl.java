package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SightingDaoImpl implements SightingDao {
    @Autowired
    JdbcTemplate jdbc;

    /* CRUD implementations */
    @Override
    public List<Sighting> getAllSightings() {
        final String SQL_GET_ALL = "SELECT * FROM sightings;";
        return jdbc.query(SQL_GET_ALL, new SightingMapper());
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
        final String SQL_UPDATE = "UPDATE sightings SET sightingID = ?, x = ?,"
                + " x = ?, x = ?";

        jdbc.update(SQL_UPDATE, sighting.getSightingID(), sighting.getTimestamp(),
                sighting.getLocationID(), sighting.getSuperID());
    }

    @Override
    public void deleteSightingByID(int sightingID) {
        final String SQL_DELETE = "DELETE FROM sightings WHERE sighting_id = ?;";
        jdbc.update(SQL_DELETE, sightingID);
    }

    /* Additional functionality */
    @Override
    public Sighting getSightingByID(int sightingID) {
        try {
            final String SQL_GET = "SELECT * FROM sighting WHERE sightingID = ?;";
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
            sighting.setTimestamp(rs.getTimestamp("Timestamp"));
            sighting.setSuperID(rs.getInt("superID"));
            sighting.setLocationID(rs.getInt("locationID"));
            return sighting;
        }
    }
}
