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
@RequestMapping("/manager")
@SessionAttributes("staff")
public class ManagerController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @Autowired
    CustomerValidation customerValidation;

    @Autowired
    StaffValidation staffValidation;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/list")
    public String listar(Model model){
        reloadListManager(model);
        return "manager/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("title","Registrar gerente");
        model.addAttribute("staff", new Staff());
        model.addAttribute("menuActive", "manager");
        return "manager/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Staff staff, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
         staffValidation.validate(staff, result);
        User userFound = userService.findByUsername(staff.getUser().getUsername());
        if (result.hasErrors() || userFound != null){
            model.addAttribute("userFound",userFound);
            model.addAttribute("title","Registrar gerente");
            model.addAttribute("menuActive", "manager");
            return "manager/form";
        }
        staff.getUser().setPassword(bCryptPasswordEncoder.encode(staff.getUser().getPassword()));
        staff.getUser().setEnabled(true);
        User user = userService.save(staff.getUser());
        staff.setUser(user);
        Authority authority = new Authority("ROLE_GERENTE");
        authority.setUser(user);
        authorityService.save(authority);
        staff.setId(user.getId());
        staffService.save(staff);
        model.addAttribute("menuActive", "manager");
        redirectAttributes.addFlashAttribute("success", "El gerente ha sido registrado");
        return "redirect:/manager/list";
    }

    @GetMapping("/enable/{idManager}")
    public String enable(Model model, @PathVariable String idManager){
        isEnabled(true, model, idManager);
        return "manager/list :: list-user";
    }

    @GetMapping("/disable/{idManager}")
    public String disable(Model model, @PathVariable String idManager){
        isEnabled(false, model, idManager);
        return "manager/list :: list-user";
    }

    @GetMapping("/edit/{idManager}")
    public String editManager(@PathVariable String idManager, Model model) {
        Optional<Staff> staff = staffService.findById(Integer.parseInt(idManager));
        staff.ifPresent(s -> model.addAttribute("staff", s));
        model.addAttribute("title","Editar gerente");
        model.addAttribute("menuActive","manager");
        return "manager/form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Staff staff, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        staffValidation.validate(staff, result);
        if (result.hasErrors()){
            model.addAttribute("title","Editar gerente");
            model.addAttribute("menuActive","manager");
            return "manager/form";
        }
        staffService.save(staff);
        redirectAttributes.addFlashAttribute("success", "El gerente a sido modificado");
        return "redirect:/manager/list";
    }

    private void isEnabled(Boolean change, Model model, String idManager) {
        Optional<Staff> staffOptional = staffService.findById(Integer.parseInt(idManager));
        staffOptional.ifPresent(staff -> {
            staff.getUser().setEnabled(change);
            userService.save(staff.getUser());
            reloadListManager(model);
        });
    }

    private void reloadListManager(Model model) {
        List<Staff> staffs = staffService.findAll();
        List<User> users = userService.findByAuthoritiesAuthority("ROLE_GERENTE");
        List<Staff> managers = new ArrayList<>();
        for (User user: users){
            for (Staff s : staffs){
                if(user.getId() == s.getUser().getId()) managers.add(s);
            }
        }
        model.addAttribute("managers", managers);
        model.addAttribute("menuActive", "manager");
    }

}
