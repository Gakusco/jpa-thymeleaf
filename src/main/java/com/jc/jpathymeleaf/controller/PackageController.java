package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/package")
public class PackageController {

    @Autowired
    PackageService packageService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("packages", packageService.findAll());
        return "package/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("package", new Package());
        return "package/form";
    }
}
