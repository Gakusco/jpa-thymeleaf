package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.UserValidation;
import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.CustomerService;
import com.jc.jpathymeleaf.service.PackageService;
import com.jc.jpathymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
@SessionAttributes("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PackageService packageService;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserValidation userValidation;

    @GetMapping("/list")
    public String listar(Model model){
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("menuActive", "customer");
        return "customer/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("menuActive", "customer");
        return "customer/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        userValidation.validate(customer, result);
         if (result.hasErrors()){
            return "customer/form";
        }
        customer.getUser().setPassword(bCryptPasswordEncoder.encode(customer.getUser().getPassword()));
        customer.getUser().setEnabled(true);
        User user = userService.save(customer.getUser());
        customer.setUser(user);
        Authority authority = new Authority("ROLE_CLIENTE");
        authority.setUser(user);
        authorityService.save(authority);
        customerService.save(customer);
        model.addAttribute("menuActive", "customer");
        redirectAttributes.addFlashAttribute("success", "El cliente ha sido registrado");
        return "redirect:/customer/list";
    }

    @GetMapping("/package/{idCustomer}")
    public String addPackage(Model model, @PathVariable String idCustomer) {
        Optional<Customer> customerOptional = customerService.findById(Integer.parseInt(idCustomer));
        customerOptional.ifPresent(customer -> refreshAddPackage(model, customer));
        return "customer/add-package";
    }

    @GetMapping("/package/add/{ids}")
    public String savePackage(@PathVariable String ids, Model model) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Optional<Customer> customerOptional = customerService.findById(idCustomer);
        Optional<Package> packOptional = packageService.findById(idPackage);
        if (customerOptional.isPresent() && packOptional.isPresent()){
            packOptional.get().addCustomer(customerOptional.get());
            packageService.save(packOptional.get());
            refreshAddPackage(model, customerOptional.get());
        }
        return "customer/add-package :: tabla-packages";
    }

    @GetMapping("/package/delete/{ids}")
    public String deletePackage(Model model, @PathVariable String ids) {
        String[] idsArr = ids.split("-");
        int idPackage = Integer.parseInt(idsArr[0]);
        int idCustomer = Integer.parseInt(idsArr[1]);
        Optional<Customer> customerOptional = customerService.findById(idCustomer);
        Optional<Package> packOptional = packageService.findById(idPackage);
        if (customerOptional.isPresent() && packOptional.isPresent()){
            packOptional.get().removeCustomer(customerOptional.get());
            packageService.save(packOptional.get());
            refreshAddPackage(model, customerOptional.get());
        }
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
        model.addAttribute("menuActive", "customer");
    }

}
