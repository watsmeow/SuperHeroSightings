package com.SuperHeroSightings.SuperHeroSightings.daoTests;

import com.SuperHeroSightings.SuperHeroSightings.TestApplicationConfiguration;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDaoImpl;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import junit.framework.TestCase;
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

//    public OrganizationDaoTest() {}

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Organization> organizations = organizationDao.getAllOrgs();
        for(Organization organization : organizations) {
            organizationDao.deleteOrgByID(organization.getOrgID());
        }


    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetOrg() {

        Organization organization = new Organization();
        organization.setOrgName("Test");
        organization.setOrgDescription("Desc");
        organization.setOrgAddress("Address");
        organization.setOrgCity("City");
        organization.setOrgState("ST");
        organization.setOrgZip("33333");
        organization.setOrgPhoneNumber("333-333-3333");
        organization = organizationDao.createOrganization(organization);

        Organization fromDao = organizationDao.getOrgByID(organization.getOrgID());

        Assertions.assertEquals(organization, fromDao);
    }

}
