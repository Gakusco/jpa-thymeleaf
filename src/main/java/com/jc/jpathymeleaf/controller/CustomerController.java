package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.CustomerValidation;
import com.jc.jpathymeleaf.model.*;
import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
    StaffService staffService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomerValidation customerValidation;

    @GetMapping("/list")
    public String listar(Model model){
        reloadListCustomer(model);
        return "customer/list";
    }

    @GetMapping("/mis-paquetes")
    public String misPaquetes(HttpServletRequest request, Model model){
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        Optional<Customer> customerOptional = customerService.findById(user.getId());
        customerOptional.ifPresent(customer -> refreshAddPackage(model, customer));
        model.addAttribute("menuActive","customer");
        model.addAttribute("title","Mis paquetes");
        model.addAttribute("action", "Registar");
        return "customer/add-package";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("title","Registrar cliente");
        model.addAttribute("customer", new Customer());
        model.addAttribute("menuActive", "customer");
        model.addAttribute("action", "Registar");
        return "customer/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        customerValidation.validate(customer, result);
        User userFound = userService.findByUsername(customer.getUser().getUsername());
        if (result.hasErrors() || userFound != null){
            model.addAttribute("userFound",userFound);
             model.addAttribute("title","Registrar cliente");
             model.addAttribute("menuActive", "customer");
            model.addAttribute("action", "Registar");
            return "customer/form";
        }
        IndexController.saveUser(customer, bCryptPasswordEncoder, userService, authorityService, customerService);
        model.addAttribute("menuActive", "customer");
        redirectAttributes.addFlashAttribute("success", "El cliente ha sido registrado");
        return "redirect:/customer/list";
    }

    @GetMapping("/package/{idCustomer}")
    public String addPackage(Model model, @PathVariable String idCustomer) {
        Optional<Customer> customerOptional = customerService.findById(Integer.parseInt(idCustomer));
        customerOptional.ifPresent(customer -> {
            refreshAddPackage(model, customer);
            model.addAttribute("title","Paquetes del cliente "+customerOptional.get().getName());
        });
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
            model.addAttribute("title","Paquetes del cliente "+customerOptional.get().getName());
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
            model.addAttribute("title","Paquetes del cliente "+customerOptional.get().getName());
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

    @GetMapping("/enable/{idCustomer}")
    public String enable(Model model, @PathVariable String idCustomer){
        isEnabled(true, model, idCustomer);
        return "customer/list :: list-user";
    }

    @GetMapping("/disable/{idCustomer}")
    public String disable(Model model, @PathVariable String idCustomer){
        isEnabled(false, model, idCustomer);
        return "customer/list :: list-user";
    }

    @GetMapping("/edit/{idCustomer}")
    public String editCustomer(@PathVariable String idCustomer, Model model) {
        Optional<Customer> customer = customerService.findById(Integer.parseInt(idCustomer));
        customer.ifPresent(c -> model.addAttribute("customer", c));
        model.addAttribute("title","Editar cliente");
        model.addAttribute("menuActive","customer");
        model.addAttribute("action", "Guardar");
        return "customer/form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        customerValidation.validate(customer, result);
        if (result.hasErrors()){
            model.addAttribute("title","Editar cliente");
            model.addAttribute("menuActive","customer");
            model.addAttribute("action", "Guardar");
            return "customer/form";
        }
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("success", "El cliente ha sido modificado");
        return "redirect:/customer/list";
    }

    private void isEnabled(Boolean change, Model model, String idCustomer) {
        Optional<Customer> customerOptional = customerService.findById(Integer.parseInt(idCustomer));
        customerOptional.ifPresent(customer -> {
            customer.getUser().setEnabled(change);
            userService.save(customer.getUser());
            reloadListCustomer(model);
        });
    }

    private void reloadListCustomer(Model model) {
        List<Customer> customerList = customerService.findAll();
        List<User> users = userService.findByAuthoritiesAuthority("ROLE_CLIENTE");
        List<Customer> customers = new ArrayList<>();
        for (User user: users){
            for (Customer c : customerList){
                if(user.getId() == c.getUser().getId()) customers.add(c);
            }
        }
        model.addAttribute("customers", customers);
        model.addAttribute("menuActive", "customer");
    }

}
