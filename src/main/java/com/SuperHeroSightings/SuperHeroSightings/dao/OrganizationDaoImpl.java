package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationDaoImpl {

    public static final class OrganizationMapper implements RowMapper<Organization> {
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrgID(rs.getInt("orgID"));
            organization.setOrgName(rs.getString("orgName"));
            organization.setOrgDescription(rs.getString("orgDescription"));
            organization.setOrgAddress(rs.getString("orgAddress"));
            organization.setOrgCity(rs.getString("orgCity"));
            organization.setOrgState(rs.getString("orgState"));
            organization.setOrgZip(rs.getString("orgZip"));
            organization.setOrgPhoneNumber(rs.getString("orgPhoneNumber"));
            return organization;
        }
    }
}
