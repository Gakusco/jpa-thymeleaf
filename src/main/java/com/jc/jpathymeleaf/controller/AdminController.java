package com.jc.jpathymeleaf.controller;

import com.jc.jpathymeleaf.Validations.CustomerValidation;
import com.jc.jpathymeleaf.Validations.StaffValidation;
import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.Staff;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.service.AuthorityService;
import com.jc.jpathymeleaf.service.StaffService;
import com.jc.jpathymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes("staff")
public class AdminController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerValidation customerValidation;

    @Autowired
    private StaffValidation staffValidation;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/list")
    public String listar(Model model){
        reloadListAdmin(model);
        return "admin/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("title","Registrar administrador");
        model.addAttribute("staff", new Staff());
        model.addAttribute("menuActive", "admin");
        model.addAttribute("action", "Registrar");
        return "admin/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Staff staff, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        staffValidation.validate(staff, result);
        User userFound = userService.findByUsername(staff.getUser().getUsername());
        if (result.hasErrors() || userFound != null){
            model.addAttribute("userFound",userFound);
            model.addAttribute("title","Registrar administrador");
            model.addAttribute("menuActive", "admin");
            model.addAttribute("action", "Registrar");
            return "admin/form";
        }
        staff.getUser().setPassword(bCryptPasswordEncoder.encode(staff.getUser().getPassword()));
        staff.getUser().setEnabled(true);
        User user = userService.save(staff.getUser());
        staff.setUser(user);
        Authority authority = new Authority("ROLE_ADMINISTRADOR");
        authority.setUser(user);
        authorityService.save(authority);
        staff.setId(user.getId());
        staffService.save(staff);
        model.addAttribute("menuActive", "admin");
        redirectAttributes.addFlashAttribute("success", "El administrador ha sido registrado");
        return "redirect:/admin/list";
    }

    @GetMapping("/enable/{idAdmin}")
    public String enable(Model model, @PathVariable String idAdmin){
        isEnabled(true, model, idAdmin);
        return "admin/list :: list-user";
    }

    @GetMapping("/disable/{idAdmin}")
    public String disable(Model model, @PathVariable String idAdmin){
        isEnabled(false, model, idAdmin);
        return "admin/list :: list-user";
    }

    @GetMapping("/edit/{idAdmin}")
    public String editAdmin(@PathVariable String idAdmin, Model model) {
        Optional<Staff> staff = staffService.findById(Integer.parseInt(idAdmin));
        System.out.println(staff.get().getUser().getUsername());
        System.out.println(staff.get().getUser().getPassword());
        staff.ifPresent(s -> model.addAttribute("staff", s));
        model.addAttribute("title","Editar administrador");
        model.addAttribute("menuActive","admin");
        model.addAttribute("action", "Guardar");
        return "admin/form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Staff staff, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        staffValidation.validate(staff, result);
        if (result.hasErrors()){
            model.addAttribute("title","Editar administrador");
            model.addAttribute("menuActive","admin");
            model.addAttribute("action", "Guardar");
            return "admin/form";
        }
        System.out.println(staff.getUser().getUsername());
        System.out.println(staff.getUser().getPassword());
        staffService.save(staff);
        redirectAttributes.addFlashAttribute("success", "El administrador a sido modificado");
        return "redirect:/admin/list";
    }

    private void isEnabled(Boolean change, Model model, String idAdmin) {
        Optional<Staff> staffOptional = staffService.findById(Integer.parseInt(idAdmin));
        staffOptional.ifPresent(staff -> {
            staff.getUser().setEnabled(change);
            userService.save(staff.getUser());
            reloadListAdmin(model);
        });
    }

    private void reloadListAdmin(Model model) {
        List<Staff> staffs = staffService.findAll();
        List<User> users = userService.findByAuthoritiesAuthority("ROLE_ADMINISTRADOR");
        List<Staff> admins = new ArrayList<>();
        for (User user: users){
            for (Staff s : staffs){
                if(user.getId() == s.getUser().getId()) admins.add(s);
            }
        }
        model.addAttribute("admins", admins);
        model.addAttribute("menuActive", "admin");
    }
}
