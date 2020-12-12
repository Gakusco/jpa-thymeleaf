package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.CustomerValidation;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.CustomerService;
import com.jc.jpathymeleaf.service.PackageService;
import com.jc.jpathymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer")
@SessionAttributes("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PackageService packageService;

    @Autowired
    CustomerValidation customerValidation;

//    @Autowired
//    UserService userService;

    @GetMapping("/list")
    public String listar(Model model){
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("menuActive", "customers");
        return "customer/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("menuActive", "customers");
        return "customer/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        customerValidation.validate(customer, result);
         if (result.hasErrors()){
            return "customer/form";
        }
        customerService.save(customer);
        model.addAttribute("menuActive", "customers");
        redirectAttributes.addFlashAttribute("success", "El cliente ha sido registrado");
        return "redirect:/customer/list";
    }

    @GetMapping("/package/{idCustomer}")
    public String addPackage(Model model, @PathVariable String idCustomer) {
        Customer customer = customerService.getById(Integer.parseInt(idCustomer));
        refreshAddPackage(model, customer);
        return "customer/add-package";
    }

    @GetMapping("/package/add/{ids}")
    public String savePackage(@PathVariable String ids, Model model) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Customer customer = customerService.getById(idCustomer);
        Package pack = packageService.findById(idPackage);
        pack.addCustomer(customer);
        packageService.save(pack);
        refreshAddPackage(model, customer);
        return "customer/add-package :: tabla-packages";
    }

    @GetMapping("/package/delete/{ids}")
    public String deletePackage(Model model, @PathVariable String ids) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Customer customer = customerService.getById(idCustomer);
        Package pack = packageService.findById(idPackage);
        pack.removeCustomer(customer);
        packageService.save(pack);
        refreshAddPackage(model, customer);
        return "customer/add-package :: tabla-packages";
    }

    private void refreshAddPackage(Model model, Customer customer) {
        List<Package> packagesCustomer = customer.getPackages();
        List<Package> packages = packageService.findAll();
        for ( Package packageCustomer : packagesCustomer ) {
            packages.removeIf(p -> p.getId() == packageCustomer.getId());
        }
        packages.removeIf(p -> !p.isEnable());
        packagesCustomer.removeIf(p -> !p.isEnable());
        model.addAttribute("customer", customer);
        model.addAttribute("packagesNew", packages);
        model.addAttribute("packages", packagesCustomer);
        model.addAttribute("menuActive", "customers");
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
