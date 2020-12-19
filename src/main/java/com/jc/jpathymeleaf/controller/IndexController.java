package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model){
        if (request.getUserPrincipal() == null){
            model.addAttribute("login","login");
        }
        return "index";
    }
}
