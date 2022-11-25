package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperHeroesDao superHeroesDao;

    @GetMapping("organizations")
    public String getAllOrgsAHeroBelongsTo(Model model) {
        List<Organization> organizations = organizationDao.getAllOrgs();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

//    @GetMapping("organizations")
//    public String getAllMembersOfAnOrg(Model model, String orgName) {
//        List<SuperHero> superHeroes = organizationDao.getAllMembersOfAnOrg(orgName);
//        model.addAttribute("organizations", superHeroes);
//        return "organizations";
//    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String name = request.getParameter("orgName");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String phone = request.getParameter("phone");

        Organization organization = new Organization();
        organization.setOrgName(name);
        organization.setOrgDescription(description);
        organization.setOrgAddress(address);
        organization.setOrgState(state);
        organization.setOrgCity(city);
        organization.setOrgZip(zip);
        organization.setOrgPhoneNumber(phone);

        organizationDao.createOrganization(organization);

        return "redirect:/organizations";
    }
}
