package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDaoImpl.SuperHeroMapper;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
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
public class LocationDaoImpl implements LocationDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM locations";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String SQL_GET_LOC_BY_ID = "SELECT * FROM locations WHERE locationId = ?";
            return jdbc.queryForObject(SQL_GET_LOC_BY_ID, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String ADD_LOCATION = "INSERT INTO locations(locationName,locationDescription,"
                + "latitude,longitude,locAddress,locCity,locState,locZip) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        jdbc.update(ADD_LOCATION, location.getLocationName(), location.getLocationDescription(),
                location.getLatitude(), location.getLongitude(),
                location.getLocationAddress(), location.getLocationCity(),
                location.getLocationState(), location.getLocationZip());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);

        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE locations SET locationName = ?, "
                + "locationDescription = ?, latitude = ?, longitude = ?, locAddress = ?, "
                + "locCity = ?, locState = ?, locZip = ? WHERE locationId = ?";

        jdbc.update(UPDATE_LOCATION, location.getLocationName(), location.getLocationDescription(),
                location.getLatitude(), location.getLongitude(), location.getLocationAddress(),
                location.getLocationCity(), location.getLocationState(),
                location.getLocationZip(), location.getLocationID());
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) {
        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, locationId);

        final String DELETE_LOCATION = "DELETE FROM locations WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, locationId);
    }

    @Override
    public List<SuperHero> getSuperHeroesAtLocation(Location location) {
        final String LOCATION_SUPERHEROES = "SELECT DISTINCT sh.* from superHeroes sh "
                + "JOIN sightings s ON s.superID = sh.superID WHERE s.locationID = ?";
        List<SuperHero> superHeroesAtLocation = jdbc.query(LOCATION_SUPERHEROES,
                new SuperHeroMapper(), location.getLocationID());
        return superHeroesAtLocation;
    }

    public static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(
                    rs.getString("locationDescription"));
            location.setLatitude(rs.getString("latitude"));
            location.setLongitude(rs.getString("longitude"));
            location.setLocationAddress(rs.getString("locAddress"));
            location.setLocationCity(rs.getString("locCity"));
            location.setLocationState(rs.getString("locState"));
            location.setLocationZip(rs.getString("locZip"));
            return location;
        }
    }
}
