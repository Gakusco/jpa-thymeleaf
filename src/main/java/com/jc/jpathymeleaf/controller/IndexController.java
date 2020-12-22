package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.CustomerValidation;
import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.CustomerService;
import com.jc.jpathymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    CustomerValidation customerValidation;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    CustomerService customerService;

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model){
        if (request.getUserPrincipal() == null){
            model.addAttribute("login","login");
            model.addAttribute("register", "register");
        }
        return "index";
    }

    @GetMapping("/register")
    public String add(Model model){
        model.addAttribute("title","Registrar cliente");
        model.addAttribute("customer", new Customer());
        model.addAttribute("register", "register");
        model.addAttribute("menuActive", "register");
        return "register/form";
    }

    @PostMapping("/register/save")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        customerValidation.validate(customer, result);
        User userFound = userService.findByUsername(customer.getUser().getUsername());
        if (result.hasErrors() || userFound != null){
            model.addAttribute("userFound",userFound);
            model.addAttribute("title","Registrar cliente");
            model.addAttribute("register", "register");
            model.addAttribute("menuActive", "register");
            return "register/form";
        }
        saveUser(customer, bCryptPasswordEncoder, userService, authorityService, customerService);
        redirectAttributes.addFlashAttribute("success", "El cliente ha sido registrado");
        return "redirect:/index";
    }

    static void saveUser(@ModelAttribute @Valid Customer customer, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, AuthorityService authorityService, CustomerService customerService) {
        customer.getUser().setPassword(bCryptPasswordEncoder.encode(customer.getUser().getPassword()));
        customer.getUser().setEnabled(true);
        User user = userService.save(customer.getUser());
        customer.setUser(user);
        Authority authority = new Authority("ROLE_CLIENTE");
        authority.setUser(user);
        authorityService.save(authority);
        customer.setId(user.getId());
        customerService.save(customer);
    }
}
