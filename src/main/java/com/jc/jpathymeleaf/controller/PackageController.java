package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.service.PackageService;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public String add(@ModelAttribute Package pack) {
        pack.setEnable(true);
        packageService.save(pack);
        return "redirect:/package/list";
    }

    @GetMapping("/customer/{idCustomer}")
    public String addCustomer(@PathVariable String idCustomer){
        return "package/add-customer";
    }
}
