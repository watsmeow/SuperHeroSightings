package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.TestApplicationConfiguration;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class SightingsDaoTest extends TestCase {
    @Autowired
    SightingDao sightingDao;

    public SightingsDaoTest() {}

    @Before
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingByID(sighting.getSightingID());
        }
    }

    @Test
    public void testGetAllSightings() {
        /* Create superhero1 and location1 objects */
        SuperHero superhero1 = new SuperHero();
        superhero1.setSuperID(01);
        superhero1.setSuperDescription("Test description1");
        superhero1.setSuperName("Test superhero1");
        superhero1.setSuperPower("Test power1");
        Location location1 = new Location();
        location1.setLocationID(01);
        location1.setLocationName("Test location name1");
        location1.setLocationDescription("Test location description1");
        location1.setLocationAddress("Test location address1");
        location1.setLocationCity("Test location city1");
        location1.setLocationState("NY");
        location1.setLocationZip("10027");

        /* Create sighting1 object */
        Sighting sighting1 = new Sighting();
        sighting1.setSightingID(1);
        sighting1.setLocationID(location1.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting1.setTimestamp(newTimeStamp1);
        sighting1.setSuperID(superhero1.getSuperID());

        /* Create superhero2 and location2 objects */
        SuperHero superhero2 = new SuperHero();
        superhero2.setSuperID(02);
        superhero2.setSuperDescription("Test description2");
        superhero2.setSuperName("Test superhero2");
        superhero2.setSuperPower("Test power2");
        Location location2 = new Location();
        location2.setLocationID(02);
        location2.setLocationName("Test location name2");
        location2.setLocationDescription("Test location description2");
        location2.setLocationAddress("Test location address2");
        location2.setLocationCity("Test location city2");
        location2.setLocationState("CA");
        location2.setLocationZip("90001");

        /* Create sighting2 object */
        Sighting sighting2 = new Sighting();
        sighting2.setSightingID(2);
        sighting2.setLocationID(location2.getLocationID());
        String testTimestamp2 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestamp2);
        sighting2.setTimestamp(newTimeStamp2);
        sighting2.setSuperID(superhero2.getSuperID());

        sightingDao.addSighting(sighting1);
        sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        Assertions.assertTrue(sightings.contains(sighting1));
        Assertions.assertTrue(sightings.contains(sighting2));

        sightingDao.deleteSightingByID(sighting1.getSightingID());
        sightingDao.deleteSightingByID(sighting2.getSightingID());

    }

    @Test
    public void testGetSightingByID() {
        /* Create superhero and location objects */
        SuperHero superhero = new SuperHero();
        superhero.setSuperID(01);
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower("Test power");
        Location location = new Location();
        location.setLocationID(01);
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setSightingID(1);
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(newTimeStamp1);
        sighting.setSuperID(superhero.getSuperID());

        List<Sighting> sightings = sightingDao.getAllSightings();

        sightingDao.addSighting(sighting);
        Assertions.assertTrue(sightings.contains(sighting));
        Assertions.assertTrue(sightings.size() == 1);
        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testAddSighting() {
        /* Create superhero and location objects */
        SuperHero superhero = new SuperHero();
        superhero.setSuperID(01);
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower("Test power");
        Location location = new Location();
        location.setLocationID(01);
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setSightingID(1);
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(newTimeStamp1);
        sighting.setSuperID(superhero.getSuperID());

        sightingDao.addSighting(sighting);
        Sighting sightingFromDao = sightingDao.getSightingByID(sighting.getSightingID());

        Assertions.assertEquals(sighting, sightingFromDao);
        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testUpdateSighting() {
        /* Create superhero and location objects */
        SuperHero superhero = new SuperHero();
        superhero.setSuperID(01);
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower("Test power");
        Location location = new Location();
        location.setLocationID(01);
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setSightingID(1);
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(newTimeStamp1);
        sighting.setSuperID(superhero.getSuperID());

        sightingDao.addSighting(sighting);

        Sighting daoSighting = sightingDao.getSightingByID(sighting.getSightingID());

        String testTimestamp2 = "1999-02-02 11:11:11";
        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestamp2);
        sighting.setTimestamp(newTimeStamp2);

        Assertions.assertNotEquals(sighting, daoSighting);

        sightingDao.deleteSightingByID(sighting.getSightingID());
    }

    @Test
    public void testDeleteSightingByID() {
        /* Create superhero and location objects */
        SuperHero superhero = new SuperHero();
        superhero.setSuperID(01);
        superhero.setSuperDescription("Test description");
        superhero.setSuperName("Test superhero");
        superhero.setSuperPower("Test power");
        Location location = new Location();
        location.setLocationID(01);
        location.setLocationName("Test location name");
        location.setLocationDescription("Test location description");
        location.setLocationAddress("Test location address");
        location.setLocationCity("Test location city");
        location.setLocationState("NY");
        location.setLocationZip("10027");

        /* Create sighting1 object */
        Sighting sighting = new Sighting();
        sighting.setSightingID(1);
        sighting.setLocationID(location.getLocationID());
        String testTimestamp1 = "2022-01-01 10:10:10";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestamp1);
        sighting.setTimestamp(newTimeStamp1);
        sighting.setSuperID(superhero.getSuperID());

        sightingDao.addSighting(sighting);
        List<Sighting> sightings = sightingDao.getAllSightings();

        Assertions.assertTrue(sightings.size() == 1);

        sightingDao.deleteSightingByID(sighting.getSightingID());
        Assertions.assertTrue(sightings.size() == 0);
        Assertions.assertNull(sightingDao.getSightingByID(sighting.getSightingID()));

    }

}
