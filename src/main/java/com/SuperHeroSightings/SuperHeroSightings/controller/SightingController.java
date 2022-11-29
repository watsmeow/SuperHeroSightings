package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

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

    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();

//        String superName = request.getParameter("superName");
//        String locationName = request.getParameter("locationName");
//        SuperHero superHero = superHeroesDao.getAllHeroes()
//                .stream()
//                .filter(hero -> hero.getSuperName().equals(superName))
//                .findFirst().get();
//        int superID = superHero.getSuperID();
//
//        /* Retrieve location ID */
//        Location location = locationDao.getAllLocations()
//                .stream()
//                .filter(loc -> loc.getLocationName().equals(locationName))
//                .findFirst().get();

        model.addAttribute("sightings",sightings);
        model.addAttribute("superheroes",superheroes);
        model.addAttribute("locations",locations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String timestamp = request.getParameter("timestamp");
        Timestamp newTimeStamp = Timestamp.valueOf(timestamp);
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

//        System.out.println("********************");
//        List<Location> locationsMap = locationDao.getAllLocations();
//        for (Location location1: locationsMap) {
//            System.out.println(location1.getLocationName());
//        }
//        System.out.println("********************");

        int locationID = location.getLocationID();

        Sighting sighting = new Sighting();
        sighting.setTimestamp(newTimeStamp);
        sighting.setSuperID(superID);
        sighting.setLocationID(locationID);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingByID(id);
        return "redirect:/sightings";
    }
    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingID"));
        Sighting sighting = sightingDao.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @GetMapping("/")
    public String newsFeedSightings(Model model) {
        List<Sighting> newsFeedSightings = sightingDao.newsFeedSightings();
        model.addAttribute("newsFeedSightings", newsFeedSightings);
        return "index";
    }
}
