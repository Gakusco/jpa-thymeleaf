package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.UserValidation;
import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Staff;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.StaffService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @Autowired
    UserValidation userValidation;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/list")
    public String listar(Model model){
        List<Staff> staffs = staffService.findAll();
        List<User> users = userService.findByAuthoritiesAuthority("ROLE_GERENTE");
        List<Staff> managers = new ArrayList<>();
        for (User user: users){
            for (Staff staff : staffs){
                if(user.getId() == staff.getUser().getId()) managers.add(staff);
            }
        }
        model.addAttribute("managers", managers);
        model.addAttribute("menuActive", "manager");
        return "manager/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("staff", new Staff());
        model.addAttribute("menuActive", "manager");
        return "manager/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Staff staff, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // userValidation.validate(staff, result);
        if (result.hasErrors()){
            return "manager/form";
        }
        staff.getUser().setPassword(bCryptPasswordEncoder.encode(staff.getUser().getPassword()));
        staff.getUser().setEnabled(true);
        User user = userService.save(staff.getUser());
        staff.setUser(user);
        Authority authority = new Authority("ROLE_GERENTE");
        authority.setUser(user);
        authorityService.save(authority);
        staffService.save(staff);
        model.addAttribute("menuActive", "manager");
        redirectAttributes.addFlashAttribute("success", "El gerente ha sido registrado");
        return "redirect:/manager/list";
    }

}
