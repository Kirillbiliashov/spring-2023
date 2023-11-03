package com.example.lab2.controllers;

import com.example.lab2.entity.Rating;
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
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TaleService taleService;
    private final RatingService ratingService;

    @Autowired
    public UserController(UserService userService, TaleService taleService, RatingService ratingService) {
        this.userService = userService;
        this.taleService = taleService;
        this.ratingService = ratingService;
    }

    @GetMapping("")
    public String getAllTales(Model model) {
        List<Tale> tales = taleService.getAllTales();
        model.addAttribute("tales", tales);
        return "user";
    }

    @GetMapping("/tale/{id}")
    public String getTale(@PathVariable("id") int id, Model model) {
        Tale tale = taleService.getTaleById(id);
        model.addAttribute("tale", tale);
        model.addAttribute("user", userService.getUser());
        return "user/tale";
    }

    @PostMapping("/tale")
    public String saveTale(@RequestParam(name = "isRead", defaultValue = "false") boolean read,
                           @RequestParam(name = "isLiked", defaultValue = "false") boolean liked,
                           @ModelAttribute Tale tale){
        if (read) {
            userService.addTaleToRead(tale);
        } else {
            userService.removeTaleFromRead(tale);
        }
        if (liked) {
            userService.addTaleToFavorite(tale);
            ratingService.likeTale(tale);
        } else {
            userService.removeTaleFromFavorite(tale);
            ratingService.unLikeTale(tale);
        }
        return "redirect:/user";
    }

    @GetMapping("/unreadTales")
    public String getUnreadTales(Model model) {
        List<Tale> tales = taleService.getAllTales();
        List<Tale> unreadTales = userService.getUnreadTales(tales);
        model.addAttribute("tales", unreadTales);
        return "user/unreadTales";
    }

    @GetMapping("/favoriteTales")
    public String getFavoriteTales(Model model) {
        List<Tale> favoriteTales = userService.getFavoriteTales();
        model.addAttribute("tales", favoriteTales);
        return "user/favoriteTales";
    }

    @GetMapping("/bestTales")
    public String getBestTales(Model model) {
        List<Rating> bestTales = ratingService.getBest();
        model.addAttribute("ratings", bestTales);
        return "user/bestTales";
    }
}
