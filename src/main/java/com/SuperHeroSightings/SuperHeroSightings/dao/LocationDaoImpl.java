package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoImpl implements LocationDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Location> getAllLocations() {
        final String SQL_GET_ALL = "SELECT * FROM locations";
        return jdbc.query(SQL_GET_ALL, new LocationMapper());
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String SQL_GET = "SELECT * FROM locations WHERE locationId = ?";
            return jdbc.queryForObject(SQL_GET, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Location addLocation(Location location) {
        final String SQL_ADD = "INSERT INTO locations(locationName,locationDescription,"
                + "latitude,longitude,locAddress,locCity,locState,locZip) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        jdbc.update(SQL_ADD, location.getLocationName(), location.getLocationDescription(),
                location.getLatitude(), location.getLongitude(),
                location.getLocationAddress(), location.getLocationCity(),
                location.getLocationState(), location.getLocationZip());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);

        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String SQL_UPDATE = "UPDATE locations SET locationName = ?, locationDescription = ?,"
                + " latitude = ?, longitude = ?, locAddress = ?, locCity = ?, "
                + "locState = ?, locZip = ? WHERE locationId = ?";

        jdbc.update(SQL_UPDATE, location.getLocationName(), location.getLocationDescription(),
                location.getLatitude(), location.getLongitude(), location.getLocationAddress(),
                location.getLocationCity(), location.getLocationState(),
                location.getLocationZip(), location.getLocationID());
    }

    @Override
    public void deleteLocationById(int locationId) {
        final String SQL_DELETE = "DELETE FROM locations WHERE locationId = ?";
        jdbc.update(SQL_DELETE, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(
                    rs.getString("locationDescription"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));
            location.setLocationAddress(rs.getString("locAddress"));
            location.setLocationCity(rs.getString("locCity"));
            location.setLocationState(rs.getString("locState"));
            location.setLocationZip(rs.getString("locZip"));
            return location;
        }
    }
}
