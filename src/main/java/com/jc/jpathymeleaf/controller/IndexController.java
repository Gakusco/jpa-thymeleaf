package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.UserValidation;
import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class IndexController {

    @Autowired
    private UserValidation userValidation;

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model){
        if (request.getUserPrincipal() == null){
            model.addAttribute("login","login");
            model.addAttribute("menuActive", "register");
        }
        return "index";
    }
}
