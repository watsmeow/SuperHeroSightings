package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
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
public class SuperHeroesController {
    @Autowired
    SuperHeroesDao superHeroesDao;

    @Autowired
    OrganizationDao organizationDao;

    Set<ConstraintViolation<SuperHero>> violations = new HashSet<>();

    @GetMapping("superheroes")
    public String displaySuperHeroes(Model model){
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        model.addAttribute("superheroes",superheroes);
        model.addAttribute("errors", violations);
        return "superheroes";
    }

    @PostMapping("addSuperHero")
    public String addSuperHero(HttpServletRequest request){
        String superName = request.getParameter("superName");
        String superDescription = request.getParameter("superDescription");
        String superPower = request.getParameter("superPower");

        SuperPower getPower = superHeroesDao.getAllSuperPowers()
                .stream()
                .filter(power -> power.getSuperPowerName().equals(superPower))
                .findFirst().get();

        String superPowerName = getPower.getSuperPowerName();

        SuperHero superHero = new SuperHero();
        superHero.setSuperName(superName);
        superHero.setSuperDescription(superDescription);
        superHero.setSuperPower(superPowerName);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superHero);

        if(violations.isEmpty()) {
            superHeroesDao.addSuperHero(superHero);
        }


        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(int superID){
        superHeroesDao.deleteSuperHeroById(superID);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);
        model.addAttribute("superhero",superHero);
        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String editSuperHero(@Valid SuperHero superHero, BindingResult result){
        if(result.hasErrors()) {
            return "editSuperHero";
        }

        superHeroesDao.updateSuperHero(superHero);
        return "redirect:/superheroes";
    }

    @GetMapping("orgByHero")
    public String orgByHero(int superID, Model model){
        SuperHero superHero = superHeroesDao.getSuperHeroById(superID);
        List<Organization> organizations = superHeroesDao.getSuperHeroOrganizations(superHero);
        model.addAttribute("superhero", superHero);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "organizationsBySuperHero";
    }

    @GetMapping("locByHero")
    public String locByHero(int superID, Model model){

        SuperHero superHero = superHeroesDao.getSuperHeroById(superID);

        List<Location> locs = superHeroesDao.getSuperHeroLocation(superHero);
        model.addAttribute("superhero",superHero);
        model.addAttribute("locations",locs);
        model.addAttribute("errors", violations);
        return "locationsBySuperHero";
    }

}
