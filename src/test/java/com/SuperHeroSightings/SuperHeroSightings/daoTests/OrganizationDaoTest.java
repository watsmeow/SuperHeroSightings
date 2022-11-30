package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.TestApplicationConfiguration;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class OrganizationDaoTest extends TestCase {

    @Autowired
    OrganizationDaoImpl organizationDao;

    @Autowired
    SuperHeroesDaoImpl superHeroesDao;

    @Before
    public void setUp() {
        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superHeroesDao.deleteSuperPowerById(superPower.getSuperPowerID());
        }
        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        for (SuperHero superHero : superHeroes) {
            superHeroesDao.deleteSuperHeroById(superHero.getSuperID());
        }
        List<Organization> organizations = organizationDao.getAllOrgs();
        for (Organization organization : organizations) {
            organizationDao.deleteOrgByID(organization.getOrgID());
        }
    }

    @Test
    public void testAddAndGetOrg() {
        SuperHero hero = new SuperHero();
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower Name");
        superHeroesDao.addSuperPower(superpower);

        hero.setSuperName("Test");
        hero.setSuperDescription("Description");
        hero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(hero);

        int superID = hero.getSuperID();
        Organization organization = new Organization();
        organization.setOrgName("Test");
        organization.setOrgDescription("Desc");
        organization.setOrgAddress("Address");
        organization.setOrgCity("City");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization.setSuperID(superID);
        organization = organizationDao.createOrganization(organization);

        Organization fromDao = organizationDao.getOrgByID(organization.getOrgID());

        Assertions.assertEquals(organization, fromDao);
        organizationDao.deleteOrgByID(organization.getOrgID());
    }

    @Test
    public void testGetAllOrgs() {
        SuperHero hero = new SuperHero();
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower Name");
        superHeroesDao.addSuperPower(superpower);

        hero.setSuperName("Test");
        hero.setSuperDescription("Description");
        hero.setSuperPower(superpower.getSuperPowerName());

        superHeroesDao.addSuperHero(hero);
        int superID = hero.getSuperID();
        Organization organization = new Organization();
        organization.setOrgName("Test1");
        organization.setOrgDescription("Desc1");
        organization.setOrgAddress("Address1");
        organization.setOrgCity("City1");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization.setSuperID(superID);
        organization = organizationDao.createOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setOrgName("Test2");
        organization2.setOrgDescription("Desc2");
        organization2.setOrgAddress("Address2");
        organization2.setOrgCity("City2");
        organization2.setOrgState("ST");
        organization2.setOrgZip("33333");
        organization2.setOrgPhoneNumber("333-333-3333");
        organization2.setSuperID(superID);
        organization2 = organizationDao.createOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrgs();

        Assertions.assertTrue(organizations.contains(organization));
        Assertions.assertTrue(organizations.contains(organization2));

        organizationDao.deleteOrgByID(organization.getOrgID());
        organizationDao.deleteOrgByID(organization2.getOrgID());
    }

    @Test
    public void testUpdateOrg() {
        SuperHero hero = new SuperHero();
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower Name");
        superHeroesDao.addSuperPower(superpower);

        hero.setSuperName("Test");
        hero.setSuperDescription("Description");
        hero.setSuperPower(superpower.getSuperPowerName());
        superHeroesDao.addSuperHero(hero);
        int superID = hero.getSuperID();

        Organization organization = new Organization();
        organization.setOrgName("Test1");
        organization.setOrgDescription("Desc1");
        organization.setOrgAddress("Address1");
        organization.setOrgCity("City1");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization.setSuperID(superID);
        organization = organizationDao.createOrganization(organization);

        Organization fromDao = organizationDao.getOrgByID(organization.getOrgID());

        organization.setOrgName("UPDATED NAME");
        organizationDao.updateOrganization(organization);

        Assertions.assertNotEquals(fromDao, organization);

        organizationDao.deleteOrgByID(organization.getOrgID());
    }

    @Test
    public void testDeleteOrg() {
        SuperHero hero = new SuperHero();
        SuperPower superpower = new SuperPower();
        superpower.setSuperPowerName("Test superpower Name");
        superHeroesDao.addSuperPower(superpower);

        hero.setSuperName("Test");
        hero.setSuperDescription("Description");
        hero.setSuperPower(superpower.getSuperPowerName());

        superHeroesDao.addSuperHero(hero);
        int superID = hero.getSuperID();
        Organization organization = new Organization();
        organization.setOrgName("Test1");
        organization.setOrgDescription("Desc1");
        organization.setOrgAddress("Address1");
        organization.setOrgCity("City1");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization.setSuperID(superID);
        organization = organizationDao.createOrganization(organization);


        organizationDao.deleteOrgByID(organization.getOrgID());

        Assertions.assertNull(organizationDao.getOrgByID(organization.getOrgID()));
    }

}
