package com.SuperHeroSightings.SuperHeroSightings.controller;

import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperHero;
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
    public String deleteSuperHero(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("superID"));
        superHeroesDao.deleteSuperHeroById(id);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(HttpServletRequest request,Model model){
        int id=Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);

        model.addAttribute("superhero",superHero);
        return "editSuperHero";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("superID"));
        SuperHero superHero = superHeroesDao.getSuperHeroById(id);

        superHero.setSuperName(request.getParameter("superName"));
        superHero.setSuperDescription(request.getParameter("superDescription"));
        superHero.setSuperPower(request.getParameter("superPower"));

        superHeroesDao.updateSuperHero(superHero);
        return "redirect:/superheroes";
    }

    // for super powers

}