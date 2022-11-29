package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationDaoTest {
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperHeroesDao superHeroesDao;

    public LocationDaoTest(){
    }

    @BeforeEach
    void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationID());
        }

        List<Organization> organizations = organizationDao.getAllOrgs();
        for (Organization organization : organizations) {
            organizationDao.deleteOrgByID(organization.getOrgID());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingByID(sighting.getSightingID());
        }

        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        for (SuperHero superHero : superHeroes) {
            superHeroesDao.deleteSuperHeroById(superHero.getSuperID());
        }

        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superHeroesDao.deleteSuperPowerById(superPower.getSuperPowerID());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddAndGetLocation() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude(28.376478);
        location.setLongitude(-81.549424);
        location.setLocationAddress("200 Epcot Center Dr");
        location.setLocationCity("Lake Buena Vista");
        location.setLocationState("FL");
        location.setLocationZip("32830");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationID());

        assertEquals(location, fromDao);
    }

    @Test
    void testGetAllLocations() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude(28.376478);
        location.setLongitude(-81.549424);
        location.setLocationAddress("200 Epcot Center Dr");
        location.setLocationCity("Lake Buena Vista");
        location.setLocationState("FL");
        location.setLocationZip("32830");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setLocationName("Test Location Name 2");
        location2.setLocationDescription("Test Location Description 2");
        location2.setLatitude(28.376478);
        location2.setLongitude(-81.549424);
        location2.setLocationAddress("123 Test Street");
        location2.setLocationCity("Test City");
        location2.setLocationState("TX");
        location2.setLocationZip("12345");
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude(28.376478);
        location.setLongitude(-81.549424);
        location.setLocationAddress("200 Epcot Center Dr");
        location.setLocationCity("Lake Buena Vista");
        location.setLocationState("FL");
        location.setLocationZip("32830");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);

        location.setLocationName("New Test Location Name");
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getLocationID());

        assertEquals(location, fromDao);
    }

    @Test
    void testDeleteLocationById() {
        Location location = new Location();
        location.setLocationName("Test Location Name");
        location.setLocationDescription("Test Location Description");
        location.setLatitude(28.376478);
        location.setLongitude(-81.549424);
        location.setLocationAddress("200 Epcot Center Dr");
        location.setLocationCity("Lake Buena Vista");
        location.setLocationState("FL");
        location.setLocationZip("32830");
        location = locationDao.addLocation(location);

        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Flight");
        superPower = superHeroesDao.addSuperPower(superPower);

        SuperHero superHero = new SuperHero();
        superHero.setSuperName("Test SuperHero Name");
        superHero.setSuperDescription("Test SuperHero Description");
        superHero.setSuperPower(superPower.getSuperPowerName());
        superHero = superHeroesDao.addSuperHero(superHero);

        Sighting sighting = new Sighting();
        sighting.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        sighting.setSuperID(superHero.getSuperID());
        sighting.setLocationID(location.getLocationID());
        sighting = sightingDao.addSighting(sighting);

        Location fromDao = locationDao.getLocationById(location.getLocationID());
        assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getLocationID());

        fromDao = locationDao.getLocationById(location.getLocationID());
        assertNull(fromDao);
    }
}
