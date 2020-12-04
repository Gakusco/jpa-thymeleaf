package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/customer")
@SessionAttributes("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/list")
    public String listar(Model model){
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Customer customer, BindingResult result, Model model) {
        // if (result.hasErrors()){
        //    return "customer/form";
        //}
        customerService.save(customer);
        return "redirect:/customer/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
