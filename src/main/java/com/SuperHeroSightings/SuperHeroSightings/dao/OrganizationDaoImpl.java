package com.SuperHeroSightings.SuperHeroSightings.dao;

import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Organization createOrganization(Organization org) {
        final String INSERT_ADDRESS = "INSERT INTO orgaddresses (orgAddress, orgCity, orgState, orgZip" +
                "VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(INSERT_ADDRESS,
                org.getOrgAddress(),
                org.getOrgCity(),
                org.getOrgState(),
                org.getOrgZip());
        int addressID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        final String INSERT_PHONE = "INSERT INTO orgphonenumbers (phoneNumber) VALUES (?);";
        jdbcTemplate.update(INSERT_PHONE,
                org.getOrgPhoneNumber());
        int phoneID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        final String INSERT_ORG = "INSERT INTO orgs (orgName, orgDescription, orgAddressID, orgPhoneID) " +
                "VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(INSERT_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                addressID,
                phoneID);
        int orgID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgID(orgID);


        final String INSERT_MAP = "INSERT INTO supertoorgmapping (orgID, superID) VALUES (?, ?);";
        jdbcTemplate.update(INSERT_MAP,
                org.getOrgID(),
                org.getSuperID());

        return org;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        int orgID = organization.getOrgID();

        final String UPDATE_ADDRESS = "UPDATE orgaddresses SET orgAddress = ?, orgCity = ?, orgState = ?, orgZip = ?" +
                "JOIN orgs USING (orgAddressID)" +
                "WHERE orgID = ?;";
        jdbcTemplate.update(UPDATE_ADDRESS,
            organization.getOrgAddress(),
            organization.getOrgCity(),
            organization.getOrgState(),
            organization.getOrgZip(),
                orgID);

        final String UPDATE_PHONE = "UPDATE orgphonenumbers SET phoneNumber = ?" +
                "JOIN orgs ON (phoneNumberID = orgPhoneNumberID) " +
                "WHERE orgID = ?;";
        jdbcTemplate.update(UPDATE_PHONE,
                organization.getOrgPhoneNumber(),
                orgID);

        final String UPDATE_ORG = "UPDATE orgs SET orgName, orgDescription WHERE orgID = ?;";
        jdbcTemplate.update(UPDATE_ORG,
                organization.getOrgName(),
                organization.getOrgDescription(),
                orgID);
    }

    @Override
    @Transactional
    public void deleteOrgByID(int orgID) {
        final String DELETE_MAPPING = "DELETE FROM supertoorgmapping WHERE orgID = ?;";
        jdbcTemplate.update(DELETE_MAPPING, orgID);

        final String DELETE_ORG = "DELETE FROM orgs WHERE orgID = ?;";
        jdbcTemplate.update(DELETE_ORG, orgID);
    }

    @Override
    public List<Organization> getAllOrgsAHeroBelongsTo(int superID) {
        try {
            final String SQL_GET_ALL = "SELECT orgName, orgDescription, orgAddress, orgCity, orgState, orgZip, phoneNumber, " +
                    "superID FROM orgs " +
                    "LEFT JOIN orgPhoneNumbers ON (phoneNumberID = orgPhoneNumberID) " +
                    "LEFT JOIN orgAddresses USING (orgAddressID) " +
                    "LEFT JOIN superToOrgMapping USING (orgID)" +
                    "WHERE superID = ?;";
            return jdbcTemplate.queryForStream(SQL_GET_ALL, new OrganizationMapper(), superID)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrgs() {
        try {
            final String SQL_GET_ALL = "SELECT orgName, orgDescription, orgAddress, orgCity, orgState, orgZip, phoneNumber" +
                    "FROM orgs " +
                    "LEFT JOIN orgPhoneNumbers ON (phoneNumberID = orgPhoneNumberID) " +
                    "LEFT JOIN orgAddresses USING (orgAddressID) " +
                    "LEFT JOIN superToOrgMapping USING (orgID);";
            return jdbcTemplate.queryForStream(SQL_GET_ALL, new OrganizationMapper())
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SuperHero> getAllMembersOfAnOrg(String orgName) {
        try {
            final String SQL_GET_ALL = "SELECT superName FROM superHeroes " +
                    "LEFT JOIN superToOrgMapping USING (superID) " +
                    "LEFT JOIN orgs USING (orgID) " +
                    "WHERE orgName = ?;";
            return jdbcTemplate.queryForStream(SQL_GET_ALL, new SuperHeroesDaoImpl.SuperHeroMapper(), orgName)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            return null;
        }
    }

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
            organization.setSuperID(rs.getInt("superID"));
            return organization;
        }
    }


}
