package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperHeroesDao superHeroesDao;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();


    @GetMapping("organizations")
    public String getAllOrgs(Model model) {
        List<Organization> organizations = organizationDao.getAllOrgs();
        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("superheroes", superHeroes);
        model.addAttribute("errors", violations);
        return "organizations";
    }


    @PostMapping("addOrganization")
    public String createOrganization(HttpServletRequest request) {
        String name = request.getParameter("orgName");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String phone = request.getParameter("phone");
        String superName = request.getParameter("member");

        SuperHero superHero = superHeroesDao.getAllHeroes()
                .stream()
                .filter(hero -> hero.getSuperName().equals(superName))
                .findFirst().get();

        int superID = superHero.getSuperID();

        Organization organization = new Organization();
        organization.setOrgName(name);
        organization.setOrgDescription(description);
        organization.setOrgAddress(address);
        organization.setOrgState(state);
        organization.setOrgCity(city);
        organization.setOrgZip(zip);
        organization.setOrgPhoneNumber(phone);
        organization.setSuperID(superID);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if(violations.isEmpty()) {
            organizationDao.createOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String updateOrganization(int orgID, Model model) {
        Organization organization = organizationDao.getOrgByID(orgID);
        List<SuperHero> superHeroes = superHeroesDao.getAllHeroes();
        model.addAttribute("organization", organization);
        model.addAttribute("superHeroes", superHeroes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performUpdateOrganization(@Valid Organization organization, BindingResult result) {
        if(result.hasErrors()) {
            return "editOrganization";
        }

        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(int orgID) {
        organizationDao.deleteOrgByID(orgID);
        return "redirect:/organizations";
    }

    @GetMapping("organizationMembers")
    public String getMembersOfOrg(int orgID, Model model) {
        Organization organization = organizationDao.getOrgByID(orgID);
        String orgName = organization.getOrgName();
        List<SuperHero> superHeroes = organizationDao.getAllMembersOfAnOrg(orgName);
        model.addAttribute("organization", organization);
        model.addAttribute("superHeroes", superHeroes);
        return "organizationMembers";
    }
}
