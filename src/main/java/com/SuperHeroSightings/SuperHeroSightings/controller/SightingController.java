package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.SightingDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SightingController {

    @Autowired
    SightingDao sightingDao;

    @GetMapping("/")
    public String newsFeedSightings(Model model) {
        List<Sighting> newsFeedSightings = sightingDao.newsFeedSightings();
        model.addAttribute("newsFeedSightings", newsFeedSightings);
        return "index";
    }
}
