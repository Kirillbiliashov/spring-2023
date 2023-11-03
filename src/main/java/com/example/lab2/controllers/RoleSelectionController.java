package com.example.lab2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleSelectionController {
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/")
    public ModelAndView selectRole(@RequestParam("role") String role) {
        if ("user".equals(role)) {
            return new ModelAndView("redirect:/user");
        } else if ("admin".equals(role)) {
            return new ModelAndView("redirect:/admin");
        } else {
            return new ModelAndView("redirect:/login?error");
        }
    }
}
