package com.SuperHeroSightings.SuperHeroSightings.controller;


import com.SuperHeroSightings.SuperHeroSightings.dao.SuperHeroesDao;
import com.SuperHeroSightings.SuperHeroSightings.model.SuperPower;
import javax.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

@Controller
public class SuperPowersController {

    @Autowired
    SuperHeroesDao superHeroesDao;

    Set<ConstraintViolation<SuperPower>> violations = new HashSet();


    @GetMapping("superpowers")
    public String displaySuperPowers(Model model){
        List<SuperPower> superPowers = superHeroesDao.getAllSuperPowers();
        model.addAttribute("superpowers",superPowers);
        model.addAttribute("errors", violations);
        return "superpowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request){
        String superPowerName = request.getParameter("superPowerName");

        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName(superPowerName);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);

        if(violations.isEmpty()) {
            superHeroesDao.addSuperPower(superPower);
        }

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(int superPowerID){
        superHeroesDao.deleteSuperPowerById(superPowerID);

        return "redirect:/superpowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request,Model model){
        int id = Integer.parseInt(request.getParameter("superPowerID"));
        SuperPower superPower = superHeroesDao.getSuperPowerById(id);

        model.addAttribute("superpower",superPower);
        return "editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String editSuperPower(@Valid SuperPower superPower, BindingResult result){
        if(result.hasErrors()) {
            return "editSuperPower";
        }
        /*int id=Integer.parseInt(request.getParameter("superPowerID"));
        SuperPower superPower = superHeroesDao.getSuperPowerById(id);

        superPower.setSuperPowerName(request.getParameter("superPowerName"));
        */
        superHeroesDao.updateSuperPower(superPower);
        return "redirect:/superpowers";
    }

}
