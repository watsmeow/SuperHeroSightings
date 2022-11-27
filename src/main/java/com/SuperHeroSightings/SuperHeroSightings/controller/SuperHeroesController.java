package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.Location;
import com.SuperHeroSightings.SuperHeroSightings.model.Organization;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SuperHeroesController {
    @Autowired
    SuperHeroesDao superHeroesDao;


    @GetMapping("superheroes")
    public String displaySuperHeroes(Model model){
        List<SuperHero> superheroes = superHeroesDao.getAllHeroes();
        model.addAttribute("superheroes",superheroes);
        return "superheroes";
    }

    @PostMapping("addSuperHero")
    public String addSuperHero(HttpServletRequest request){
        String superName = request.getParameter("superName");
        String superDescription = request.getParameter("superDescription");
        String superPower = request.getParameter("superPower");

        SuperHero superHero = new SuperHero();
        superHero.setSuperName(superName);
        superHero.setSuperDescription(superDescription);
        superHero.setSuperPower(superPower);

        superHeroesDao.addSuperHero(superHero);

        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(int superID){
        superHeroesDao.deleteSuperHeroById(superID);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(int superID, Model model){
        SuperHero superHero = superHeroesDao.getSuperHeroById(superID);
        model.addAttribute("superhero",superHero);
        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String editSuperHero(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);

        superHero.setSuperName(request.getParameter("superName"));
        superHero.setSuperDescription(request.getParameter("superDescription"));
        superHero.setSuperPower(request.getParameter("superPower"));

        superHeroesDao.updateSuperHero(superHero);
        return "redirect:/superheroes";
    }

    @GetMapping("orgByHero")
    public String orgByHero(Model model,HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);
        List<Organization> organizations = superHeroesDao.getSuperHeroOrganizations(superHero);
        model.addAttribute("superhero", superHero);
        model.addAttribute("organizations", organizations);
        return "organizationsBySuperHero";
    }

    @GetMapping("locByHero")
    public String locByHero(HttpServletRequest request,Model model){
        int id=Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);

        List<Location> locs = superHeroesDao.getSuperHeroLocation(superHero);
        model.addAttribute("superheroes",superHero);
        model.addAttribute("locations",locs);
        return "locationsBySuperHero";
    }

}
