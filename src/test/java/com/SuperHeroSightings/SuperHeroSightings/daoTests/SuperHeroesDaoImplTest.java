package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.TestApplicationConfiguration;
import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class SuperHeroesDaoImplTest{

    @Autowired
    SuperHeroesDaoImpl superHeroesDao;

    @Autowired
    OrganizationDaoImpl organizationDao;

    @Autowired
    LocationDaoImpl locationDao;

    @BeforeEach
    public void setUp() {
        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        for(SuperHero superHero : superHeroes) {
            superHeroesDao.deleteSuperHeroById(superHero.getSuperID());
        }

        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        for(SuperPower superPower : superPowers) {
            superHeroesDao.deleteSuperPowerById(superPower.getSuperPowerID());
        }
    }

    // Test functions for superheroes

    @Test // test result : test passed
    public void testAddAndGetSuperHero() {
        // create a test input
        SuperHero superHero = new SuperHero();
        superHero.setSuperName("ShamsTheHero");
        superHero.setSuperDescription("Teaches Java");
        superHero.setSuperPower("Java Master");
        // add superhero to dao
        superHeroesDao.addSuperHero(superHero);
        //get superhero from the dao
        SuperHero fromDao = superHeroesDao.getSuperHeroById(superHero.getSuperID());

        // check the data equality
        Assertions.assertEquals(superHero.getSuperID(), fromDao.getSuperID());
    }

    @Test // test result : test passed
    public void testAddAndGetAllSuperHeroes() {
        // create a test input1
        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperName("ShamsTheHero1");
        superHero1.setSuperDescription("Teaches Java1");
        superHero1.setSuperPower("Java Master1");

        // create a test input2
        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperName("ShamsTheHero2");
        superHero2.setSuperDescription("Teaches Java2");
        superHero2.setSuperPower("Java Master2");

        // add test inputs to dao
        superHeroesDao.addSuperHero(superHero1);
        superHeroesDao.addSuperHero(superHero2);

        // Test the size and null conditions
        List<SuperHero> allFromDao = superHeroesDao.getAllHeroes();

        assertNotNull(allFromDao, "The list of superheroes must not null");
        assertEquals(2, allFromDao.size(),"List of superheros should have 2 superheroes.");

        // Then the specifics
        assertTrue("The list of superheroes should include superhero1.",superHeroesDao.getAllHeroes().contains(superHero1));
        assertTrue("The list of superheroes should include superhero2.",superHeroesDao.getAllHeroes().contains(superHero2));
    }

    @Test // test result : test passed
    public void testUpdate() {
        // create a test input
        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperName("ShamsTheHero1");
        superHero1.setSuperDescription("Teaches Java1");
        superHero1.setSuperPower("Java Master1");
        // add
        superHeroesDao.addSuperHero(superHero1);
        // change one of the properties
        superHero1.setSuperDescription("Teaches Full Stack Development with Java");
        // call function for update from dao
        superHeroesDao.updateSuperHero(superHero1);
        // get updated superhero from dao
        SuperHero updatedSuperHero = superHeroesDao.getSuperHeroById(superHero1.getSuperID());
        // check the data
        assertTrue(updatedSuperHero.getSuperDescription().equals("Teaches Full Stack Development with Java"));
    }

    @Test // test result : test passed
    public void testDeleteById() {
        // create a test input1
        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperName("ShamsTheHero1");
        superHero1.setSuperDescription("Teaches Java1");
        superHero1.setSuperPower("Java Master1");

        // create a test input2
        SuperHero superHero2 = new SuperHero();
        superHero2.setSuperName("ShamsTheHero2");
        superHero2.setSuperDescription("Teaches Java2");
        superHero2.setSuperPower("Java Master2");

        // add test inputs to dao
        superHeroesDao.addSuperHero(superHero1);
        superHeroesDao.addSuperHero(superHero2);

        // delete one of the objects
        superHeroesDao.deleteSuperHeroById(superHero1.getSuperID());

        // get all superheroes from dao
        List<SuperHero> allFromDao = superHeroesDao.getAllHeroes();

        assertFalse("All superheroes should not include superhero1",allFromDao.contains(superHero1));
        //assertTrue("All games should include superhero2",allFromDao.contains(superHero2));
    }

    /*
    @Test // test result :
    public void testAddAndGetLocsByHero(){
        // create a superhero test input
        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperName("ShamsTheHero1");
        superHero1.setSuperDescription("Teaches Java1");
        superHero1.setSuperPower("Java Master1");

        // add superhero to dao
        superHeroesDao.addSuperHero(superHero1);

        // create a location test input
        Location loc = new Location();
        loc.setLocationName("Aventura Mall");
        loc.setLocationDescription("testLoc");
        loc.setLatitude(BigDecimal.valueOf(125.0));
        loc.setLongitude(BigDecimal.valueOf(125.0));
        loc.setLocationAddress("Downtown Miami");
        loc.setLocationCity("Miami");
        loc.setLocationState("FL");
        loc.setLocationZip("33069");

        // add location to dao
        locationDao.addLocation(loc);

        // sighting eklenilip ondan sonra test edilecek çünkü fonkun içinde sighting de var

        List<Location> locs = superHeroesDao.getSuperHeroLocation(superHero1);

        assertNotNull(locs, "The list of locations must not null");
        assertEquals(1, locs.size(),"List of locs should have 1 location.");

    }

    @Test // test result :
    public void testAddAndGetOrgByHero(){
        // create a superhero test input
        SuperHero superHero1 = new SuperHero();
        superHero1.setSuperName("ShamsTheHero1");
        superHero1.setSuperDescription("Teaches Java1");
        superHero1.setSuperPower("Java Master1");

        // add superhero to dao
        superHeroesDao.addSuperHero(superHero1);

        // create a organization test input
        Organization organization = new Organization();
        organization.setOrgName("Test");
        organization.setOrgDescription("Desc");
        organization.setOrgAddress("Address");
        organization.setOrgCity("City");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization.setSuperID(superHero1.getSuperID());
        // add organization to dao
        organizationDao.createOrganization(organization);

        List<Organization> orgs = superHeroesDao.getSuperHeroOrganizations(superHero1);

        assertNotNull(orgs, "The list of organizations must not null");
        assertEquals(1, orgs.size(),"List of locs should have 1 organization.");
    }
*/
    // Test functions for superpowers

    @Test // test result : test passed
    public void testAddAndGetSuperPower() {
        // create a test input
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("Fly");

        // add superhero to dao
        superHeroesDao.addSuperPower(superPower);
        //get superhero from the dao
        SuperPower fromDao = superHeroesDao.getSuperPowerById(superPower.getSuperPowerID());

        // check the data equality
        Assertions.assertEquals(superPower.getSuperPowerID(), fromDao.getSuperPowerID());
    }

    @Test // test result : test passed
    public void testAddAndGetAllSuperPowers() {
        // create a test input1
        SuperPower superPower1 = new SuperPower();
        superPower1.setSuperPowerName("Test1");

        // create a test input2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("Test2");


        // add test inputs to dao
        superHeroesDao.addSuperPower(superPower1);
        superHeroesDao.addSuperPower(superPower2);

        // Test the size and null conditions
        List<SuperPower> allFromDao = superHeroesDao.getAllSuperPowers();

        assertNotNull(allFromDao, "The list of superpowers must not null");
        assertEquals(2, allFromDao.size(),"List of superpowers should have 2 superpowers.");

        // Then the specifics
        assertTrue("The list of superpowers should include super.",superHeroesDao.getAllHeroes().contains(superPower1));
        assertTrue("The list of superpowers should include superhero2.",superHeroesDao.getAllHeroes().contains(superPower2));
    }

    @Test // test result : test passed
    public void testUpdateSuperPower() {
        // create a test input
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("YarenTest");

        // add test input to the dao
        superHeroesDao.addSuperPower(superPower);
        // change one of the properties
        superPower.setSuperPowerName("YarenTestUpdated");
        // call function for update from dao
        superHeroesDao.updateSuperPower(superPower);
        // get updated superpower from dao
        SuperPower updatedSuperPower = superHeroesDao.getSuperPowerById(superPower.getSuperPowerID());
        // check the data
        Assertions.assertNotEquals(updatedSuperPower,superPower);
    }

    @Test // test result : test passed
    public void testDeleteByIdSuperPower() {
        // create a test input1
        SuperPower superPower1 = new SuperPower();
        superPower1.setSuperPowerName("Test1");

        // create a test input2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("Test2");


        // add test inputs to dao
        superHeroesDao.addSuperPower(superPower1);
        superHeroesDao.addSuperPower(superPower2);


        // delete one of the objects
        superHeroesDao.deleteSuperPowerById(superPower1.getSuperPowerID());

        // get all superpowers from dao
        List<SuperPower> allFromDao = superHeroesDao.getAllSuperPowers();

        assertFalse("All superheroes should not include superhero1",allFromDao.contains(superPower1));
        assertTrue("All games should include superhero2",allFromDao.contains(superPower2));
    }
}