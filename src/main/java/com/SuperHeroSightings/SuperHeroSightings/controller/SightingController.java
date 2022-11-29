package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.LocationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.OrganizationDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
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
        model.addAttribute("sightings",sightings);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String timestamp = request.getParameter("timestamp");
        Timestamp newTimeStamp = Timestamp.valueOf(timestamp);

        //TODO

        return "redirect:/sightings";
    }
}
