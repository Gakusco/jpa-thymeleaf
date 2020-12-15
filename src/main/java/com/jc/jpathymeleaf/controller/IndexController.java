package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index(){
        return "index";
    }
}
