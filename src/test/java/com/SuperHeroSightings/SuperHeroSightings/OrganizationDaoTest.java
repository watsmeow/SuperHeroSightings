package com.SuperHeroSightings.SuperHeroSightings;

import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrganizationDaoTest {

    @Autowired
    OrganizationDao organizationDao;

    public OrganizationDaoTest() {}

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


}
