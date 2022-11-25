package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.stream.Collectors;

public interface OrganizationDao {

    Organization createOrganization(Organization org);

    void updateOrganization(Organization organization);

    void deleteOrgByID(int orgID);

    public List<Organization> getAllOrgsAHeroBelongsTo(int superID);

    List<SuperHero> getAllMembersOfAnOrg(String orgName);

    List<Organization> getAllOrgs();

}
