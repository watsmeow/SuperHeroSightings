package com.SuperHeroSightings.SuperHeroSightings.controller;


import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SuperPowersController {

    @Autowired
    SuperHeroesDao superHeroesDao;

    @GetMapping("superpowers")
    public String displaySuperPowers(Model model){
        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        model.addAttribute("superpowers",superPowers);
        return "superpowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperHero(HttpServletRequest request){
        String superPowerName = request.getParameter("superPowerName");

        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName(superPowerName);

        superHeroesDao.addSuperPower(superPower);

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperHero(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("superPowerID"));
        superHeroesDao.deleteSuperPowerById(id);

        return "redirect:/superpowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request,Model model){
        int id=Integer.parseInt(request.getParameter("superPowerID"));
        SuperPower superPower = superHeroesDao.getSuperPowerById(id);

        model.addAttribute("superpower",superPower);
        return "editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("superPowerID"));
        SuperPower superPower = superHeroesDao.getSuperPowerById(id);

        superPower.setSuperPowerName(request.getParameter("superPowerName"));

        superHeroesDao.updateSuperPower(superPower);
        return "redirect:/superpowers";
    }

}
