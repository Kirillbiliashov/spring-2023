package com.example.lab2.controller;

import com.example.lab2.entity.Rating;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ratings")
public class RatingsController {

    @GetMapping("add")
    public String addRatingView(Model model) {
        model.addAttribute("rating", new Rating());
        return "addRating";
    }

    @PostMapping("add")
    public String addRating(@ModelAttribute Rating rating) {
        return "redirect:/tales";
    }

}
