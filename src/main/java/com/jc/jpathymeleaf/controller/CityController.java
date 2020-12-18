package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.City;
import com.jc.jpathymeleaf.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("menuActive","city");
        model.addAttribute("city",new City());
        return "city/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute City city, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            return "city/form";
        }
        cityService.save(city);
        model.addAttribute("menuActive","city");
        model.addAttribute("cities", cityService.findAll());
        redirectAttributes.addFlashAttribute("success", "El pais a sido agregado");
        return "redirect:/city/list";
    }
}
