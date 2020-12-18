package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/list")
    public String findAll(Model model){
        model.addAttribute("menuActive","city");
        model.addAttribute("cities", cityService.findAll());
        return "city/list";
    }
}
