package com.zhoholiev.studyservice.StudyService.controllers;


import com.zhoholiev.studyservice.StudyService.models.User;
import com.zhoholiev.studyservice.StudyService.services.RoleService;
import com.zhoholiev.studyservice.StudyService.services.UserService;
import com.zhoholiev.studyservice.StudyService.util.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    UserService userService;
    RoleService roleService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") User user,
                                   @ModelAttribute("takeRole") CheckRole checkRole) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid User user,
                               BindingResult bindingResult, Model model, @ModelAttribute("takeRole") CheckRole checkRole) {
        if (bindingResult.hasErrors())
            return "auth/registration";
        if (checkRole.getRoleUser().equals("student")) {
            user.setRole(roleService.findOne(4));
        } else user.setRole(roleService.findOne(3));
        userService.save(user);
        return "redirect:auth/login";
    }
}
