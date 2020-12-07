package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.service.CustomerService;
import com.jc.jpathymeleaf.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/package")
public class PackageController {

    @Autowired
    PackageService packageService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("packages", packageService.findAll());
        model.addAttribute("menuActive", "packages");
        return "package/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("package", new Package());
        model.addAttribute("menuActive", "packages");
        return "package/form";
    }

    @PostMapping("/save")
    public String add(@Valid @ModelAttribute Package pack, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "package/form";
        }
        pack.setEnable(true);
        packageService.save(pack);
        model.addAttribute("menuActive", "packages");
        redirectAttributes.addFlashAttribute("success", "El paquete ha sido creado");
        return "redirect:/package/list";
    }

    @GetMapping("/customer/{idPackage}")
    public String addCustomer(Model model, @PathVariable String idPackage){
        Package pack = packageService.findById(Integer.parseInt(idPackage));
        refreshAddCustomer(model, pack);
        return "package/add-customer";
    }

    @GetMapping("/customer/add/{ids}")
    public String savePackage(@PathVariable String ids, Model model) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Customer customer = customerService.getById(idCustomer);
        Package pack = packageService.findById(idPackage);
        pack.addCustomer(customer);
        packageService.save(pack);
        refreshAddCustomer(model, pack);
        return "package/add-customer :: tabla-customer";
    }

    @GetMapping("/customer/delete/{ids}")
    public String deletePackage(Model model, @PathVariable String ids) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Customer customer = customerService.getById(idCustomer);
        Package pack = packageService.findById(idPackage);
        pack.removeCustomer(customer);
        packageService.save(pack);
        refreshAddCustomer(model, pack);
        return "package/add-customer :: tabla-customer";
    }

    @GetMapping("/enable/{idPackage}")
    public String enable(Model model, @PathVariable String idPackage){
        isEnabled(true, model, idPackage);
        model.addAttribute("menuActive", "packages");
        return "package/list :: list-pack";
    }

    @GetMapping("/disable/{idPackage}")
    public String disable(Model model, @PathVariable String idPackage){
        isEnabled(false, model, idPackage);
        model.addAttribute("menuActive", "packages");
        return "package/list :: list-pack";
    }

    private void isEnabled(Boolean change, Model model, String idPackage) {
        Package pack = packageService.findById(Integer.parseInt(idPackage));
        pack.setEnable(change);
        packageService.save(pack);
        model.addAttribute("menuActive", "packages");
        model.addAttribute("packages", packageService.findAll());
    }

    private void refreshAddCustomer(Model model, Package pack) {
        List<Customer> customersPackage = pack.getCustomers();
        List<Customer> customers = customerService.findAll();
        for ( Customer customerPackage : customersPackage ) {
            customers.removeIf(p -> p.getId() == customerPackage.getId());
        }
        model.addAttribute("package", pack);
        model.addAttribute("customersNew", customers);
        model.addAttribute("customers", customersPackage);
        model.addAttribute("menuActive", "packages");
    }
}
