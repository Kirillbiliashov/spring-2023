package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.services.RatingService;
import com.example.lab2.services.TaleService;
import com.example.lab2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final TaleService taleService;
    private final RatingService ratingService;

    @Autowired
    public AdminController(UserService userService, TaleService taleService, RatingService ratingService) {
        this.userService = userService;
        this.taleService = taleService;
        this.ratingService = ratingService;
    }

    @GetMapping("")
    public String tales(Model model) {
        List<Tale> allTales = taleService.getAllTales();
        model.addAttribute("tales", allTales);
        return "admin";
    }

    @GetMapping("/createTale")
    public String createTaleForm(Model model) {
        model.addAttribute("tale", new Tale());
        return "admin/createTale";
    }

    @PostMapping("/createTale")
    public String createTale(@ModelAttribute Tale tale) {
        taleService.createTale(tale);
        ratingService.addRating(tale);
        return "redirect:/admin";
    }

    @PostMapping("/editTale")
    public String saveEditedTale(@ModelAttribute Tale tale) {
        Tale oldTale = taleService.getTaleById(tale.getId());
        taleService.editTale(tale.getId(), tale);
        userService.editReadTale(oldTale,tale);
        userService.editLikedTale(oldTale,tale);
        ratingService.editRating(oldTale, tale);
        return "redirect:/admin";
    }

    @GetMapping("/editTale/{id}")
    public String editTale(@PathVariable("id") int id, Model model) {
        Tale tale = taleService.getTaleById(id);
        model.addAttribute("tale", tale);
        return "/admin/editTale";
    }

    @PostMapping("/delete/{id}")
    public String deleteTale(@PathVariable("id") int id) {
        Tale tale = taleService.getTaleById(id);
        userService.removeTaleFromRead(tale);
        userService.removeTaleFromFavorite(tale);
        taleService.deleteTale(id);
        ratingService.deleteRating(id);
        return "redirect:/admin";
    }
}
