package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.TestApplicationConfiguration;
import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class SightingsDaoTest extends TestCase {
    @Autowired
    SightingDao sightingDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperHeroesDao superHeroesDao;

    public SightingsDaoTest() {}

    @Before
    public void setUp() {
        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        for (SuperHero superHero : superHeroes) {
            superHeroesDao.deleteSuperHeroById(superHero.getSuperID());
        }
        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superHeroesDao.deleteSuperPowerById(superPower.getSuperPowerID());
        }
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationID());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingByID(sighting.getSightingID());
        }
    }

    @Test
    public void testGetAllSightings() {
        /* Create superhero1 and superPower1 object */
        SuperPower superpower1 = new SuperPower();
        superpower1.setSuperPowerName("Test superpower1 Name");
        superHeroesDao.addSuperPower(superpower1);

        SuperHero superhero1 = new SuperHero();
        superhero1.setSuperDescription("Test description1");
        superhero1.setSuperName("Test superhero1");
        superhero1.setSuperPower(superpower1.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero1);

        /* Create location1 object */
        Location location1 = new Location();
        location1.setLocationName("Test location name1");
        location1.setLocationDescription("Test location description1");
        location1.setLatitude("28.376478");
        location1.setLongitude("-81.549424");
        location1.setLocationAddress("Test location address1");
        location1.setLocationCity("Test location city1");
        location1.setLocationState("NY");
        location1.setLocationZip("10027");
        locationDao.addLocation(location1);

        /* Create sighting1 object */
        Sighting sighting1 = new Sighting();
        sighting1.setLocationID(location1.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting1.setTimestamp(testTimestamp1);
        sighting1.setSuperID(superhero1.getSuperID());
        sighting1.setLocation(location1);
        sighting1.setSuperHero(superhero1);
        sightingDao.addSighting(sighting1);

        /* Create superhero2 and location2 objects */
        SuperPower superpower2 = new SuperPower();
        superpower2.setSuperPowerName("Test superpower2 Name");
        superHeroesDao.addSuperPower(superpower2);

        SuperHero superhero2 = new SuperHero();
        superhero2.setSuperDescription("Test description2");
        superhero2.setSuperName("Test superhero2");

        superhero2.setSuperPower(superpower2.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero2);

        Location location2 = new Location();
        location2.setLocationName("Test location name2");
        location2.setLocationDescription("Test location description2");
        location2.setLatitude("28.376478");
        location2.setLongitude("-81.549424");
        location2.setLocationAddress("Test location address2");
        location2.setLocationCity("Test location city2");
        location2.setLocationState("CA");
        location2.setLocationZip("90001");
        locationDao.addLocation(location2);

        /* Create sighting2 object */
        Sighting sighting2 = new Sighting();
        sighting2.setLocationID(location2.getLocationID());
        String testTimestamp2 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestamp2);
        sighting2.setTimestamp(testTimestamp2);
        sighting2.setSuperID(superhero2.getSuperID());
        sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertNotNull("The list of superheroes must not null", sightings);
        assertEquals("List of superheros should have 2 superheroes.", 2, sightings.size());

        assertTrue(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));

        sightingDao.deleteSightingByID(sighting1.getSightingID());
        sightingDao.deleteSightingByID(sighting2.getSightingID());
    }

    @Test
    public void testGetSightingByID() {
        /* Create superhero and location objects */
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower Name");
        superHeroesDao.addSuperPower(superpower);

        SuperHero superhero = new SuperHero();
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero);

        Location location = new Location();
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLatitude("28.376478");
        location.setLongitude("-81.549424");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");
        locationDao.addLocation(location);

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(testTimestamp1);
        sighting.setSuperID(superhero.getSuperID());
        sightingDao.addSighting(sighting);

        List<Sighting> sightings = sightingDao.getAllSightings();

        Assertions.assertTrue(sightings.contains(sighting));
        Assertions.assertTrue(sightings.size() == 1);
        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testAddSighting() {
        /* Create superhero and location objects */
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower1 Name");
        superHeroesDao.addSuperPower(superpower);

        SuperHero superhero = new SuperHero();
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero);

        Location location = new Location();
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLatitude("28.376478");
        location.setLongitude("-81.549424");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");
        locationDao.addLocation(location);

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(testTimestamp1);
        sighting.setSuperID(superhero.getSuperID());
        sightingDao.addSighting(sighting);

        Sighting sightingFromDao = sightingDao.getSightingByID(sighting.getSightingID());

        Assertions.assertEquals(sighting, sightingFromDao);
        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testUpdateSighting() {
        /* Create superhero and location objects */
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower1 Name");
        superHeroesDao.addSuperPower(superpower);

        SuperHero superhero = new SuperHero();
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero);

        Location location = new Location();
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLatitude("28.376478");
        location.setLongitude("-81.549424");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");
        locationDao.addLocation(location);

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(testTimestamp1);
        sighting.setSuperID(superhero.getSuperID());

        sightingDao.addSighting(sighting);

        Sighting daoSighting = sightingDao.getSightingByID(sighting.getSightingID());

        String testTimestamp2 = "1999-02-02 11:11:11";
//        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestamp2);
        sighting.setTimestamp(testTimestamp2);

        Assertions.assertNotEquals(sighting, daoSighting);
        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testDeleteSightingByID() {
        /* Create superhero and location objects */
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower1 Name");
        superHeroesDao.addSuperPower(superpower);

        SuperHero superhero = new SuperHero();
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(superhero);

        Location location = new Location();
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLatitude("28.376478");
        location.setLongitude("-81.549424");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");
        locationDao.addLocation(location);

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
//        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(testTimestamp1);
        sighting.setSuperID(superhero.getSuperID());
        sightingDao.addSighting(sighting);

        List<Sighting> sightings = sightingDao.getAllSightings();

        Assertions.assertTrue(sightings.size() == 1);

        sightingDao.deleteSightingByID(sighting.getSightingID());
        Assertions.assertNull(sightingDao.getSightingByID(sighting.getSightingID()));
    }
}

