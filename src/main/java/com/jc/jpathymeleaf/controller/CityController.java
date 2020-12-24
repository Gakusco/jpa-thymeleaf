package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.City;
import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/city")
@SessionAttributes("city")
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
        model.addAttribute("title","Agregar ciudad");
        model.addAttribute("action","Agregar");
        model.addAttribute("city",new City());
        return "city/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute City city, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            model.addAttribute("menuActive","city");
            model.addAttribute("title","Agregar ciudad");
            model.addAttribute("action","Agregar");
            return "city/form";
        }
        cityService.save(city);
        model.addAttribute("menuActive","city");
        redirectAttributes.addFlashAttribute("success", "El pais a sido agregado");
        return "redirect:/city/list";
    }

    @GetMapping("/edit/{idCity}")
    public String editCity(@PathVariable String idCity, Model model){
        Optional<City> city = cityService.findById(Integer.parseInt(idCity));
        city.ifPresent(p -> model.addAttribute("city", p));
        model.addAttribute("title","Editar ciudad");
        model.addAttribute("action","Guardar");
        model.addAttribute("menuActive","city");
        return "city/form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute City city, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("title","Editar ciudad");
            model.addAttribute("action","Guardar");
            model.addAttribute("menuActive","city");
            return "city/form";
        }
        cityService.save(city);
        model.addAttribute("menuActive","city");
        redirectAttributes.addFlashAttribute("success", "El pais a sido editado");
        return "redirect:/city/list";
    }
}
