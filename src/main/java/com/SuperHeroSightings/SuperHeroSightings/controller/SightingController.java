package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
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
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperHeroesDao superHeroesDao;


    Set<ConstraintViolation<Sighting>> violations = new HashSet<ConstraintViolation<Sighting>>();


    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sightings",sightings);
        model.addAttribute("superheroes",superheroes);
        model.addAttribute("locations",locations);
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String timestamp = request.getParameter("timestamp");
//        Timestamp newTimeStamp = Timestamp.valueOf(timestamp);

        String superName = request.getParameter("superName");
        String locationName = request.getParameter("locationName");

        /* Retrieve superhero ID */
        SuperHero superHero = superHeroesDao.getAllHeroes()
                .stream()
                .filter(hero -> hero.getSuperName().equals(superName))
                .findFirst().get();
        int superID = superHero.getSuperID();

        /* Retrieve location ID */
        Location location = locationDao.getAllLocations()
                .stream()
                .filter(loc -> loc.getLocationName().equals(locationName))
                .findFirst().get();

        int locationID = location.getLocationID();

        Sighting sighting = new Sighting();
        sighting.setTimestamp(timestamp);
        sighting.setSuperID(superID);
        sighting.setLocationID(locationID);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer sightingID) {
        sightingDao.deleteSightingByID(sightingID);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingID"));
        Sighting sighting = sightingDao.getSightingByID(id);
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sighting",sighting);
        model.addAttribute("superheroes",superheroes);
        model.addAttribute("locations",locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performUpdateOrganization(@Valid Sighting sighting, BindingResult result) {
        if(result.hasErrors()) {
            return "editSighting";
        }

        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("/")
    public String newsFeedSightings(Model model) {
        List<Sighting> newsFeedSightings = sightingDao.newsFeedSightings();
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("newsFeedSightings",newsFeedSightings);
        model.addAttribute("superheroes",superheroes);
        model.addAttribute("locations",locations);
        return "homepage";
    }

}

