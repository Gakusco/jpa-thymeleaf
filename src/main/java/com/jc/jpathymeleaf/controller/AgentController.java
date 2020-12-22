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
@RequestMapping("/agent")
@SessionAttributes("staff")
public class AgentController {

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
        reloadListAgent(model);
        return "agent/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("title","Registrar agente");
        model.addAttribute("staff", new Staff());
        model.addAttribute("menuActive", "agent");
        return "agent/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Staff staff, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
         staffValidation.validate(staff, result);
        User userFound = userService.findByUsername(staff.getUser().getUsername());
        if (result.hasErrors() || userFound != null){
            model.addAttribute("userFound",userFound);
            model.addAttribute("title","Registrar agente");
            model.addAttribute("menuActive", "agent");
            return "agent/form";
        }
        staff.getUser().setPassword(bCryptPasswordEncoder.encode(staff.getUser().getPassword()));
        staff.getUser().setEnabled(true);
        User user = userService.save(staff.getUser());
        staff.setUser(user);
        Authority authority = new Authority("ROLE_AGENTE");
        authority.setUser(user);
        authorityService.save(authority);
        staff.setId(user.getId());
        staffService.save(staff);
        model.addAttribute("menuActive", "agent");
        redirectAttributes.addFlashAttribute("success", "El agente ha sido registrado");
        return "redirect:/agent/list";
    }

    @GetMapping("/enable/{idAgent}")
    public String enable(Model model, @PathVariable String idAgent){
        isEnabled(true, model, idAgent);
        return "agent/list :: list-user";
    }

    @GetMapping("/disable/{idAgent}")
    public String disable(Model model, @PathVariable String idAgent){
        isEnabled(false, model, idAgent);
        return "agent/list :: list-user";
    }

    @GetMapping("/edit/{idAgent}")
    public String editAgent(@PathVariable String idAgent, Model model) {
        Optional<Staff> staff = staffService.findById(Integer.parseInt(idAgent));
        staff.ifPresent(s -> model.addAttribute("staff", s));
        model.addAttribute("title","Editar agente");
        model.addAttribute("menuActive","agent");
        return "agent/form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Staff staff, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        staffValidation.validate(staff, result);
        if (result.hasErrors()){
            model.addAttribute("title","Editar agente");
            model.addAttribute("menuActive","agent");
            return "agent/form";
        }
        System.out.println(staff.getId());
        staffService.save(staff);
        redirectAttributes.addFlashAttribute("success", "El agente a sido modificado");
        return "redirect:/agent/list";
    }

    private void isEnabled(Boolean change, Model model, String idAgent) {
        Optional<Staff> staffOptional = staffService.findById(Integer.parseInt(idAgent));
        staffOptional.ifPresent(staff -> {
            staff.getUser().setEnabled(change);
            userService.save(staff.getUser());
            reloadListAgent(model);
        });
    }

    private void reloadListAgent(Model model) {
        List<Staff> staffs = staffService.findAll();
        List<User> users = userService.findByAuthoritiesAuthority("ROLE_AGENTE");
        List<Staff> agents = new ArrayList<>();
        for (User user: users){
            for (Staff s : staffs){
                if(user.getId() == s.getUser().getId()) agents.add(s);
            }
        }
        model.addAttribute("agents", agents);
        model.addAttribute("menuActive", "agent");
    }
}
