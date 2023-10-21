package com.example.lab2.controller;

import com.example.lab2.entity.Tale;
import com.example.lab2.repository.TalesRepository;
import com.example.lab2.utils.TaleFormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tales")
public class TalesController {

    @GetMapping("")
    public String tales(Model model) {
        model.addAttribute("talesRatings", Map.of()); //TODO: add map of tales to ratings
        model.addAttribute("tale", new TaleFormData());
        return "tales";
    }

    @PostMapping("/add")
    public String addTale(@ModelAttribute TaleFormData formData) {
        return "redirect:/tales";
    }


    @GetMapping("/{id}")
    public String tale(@PathVariable("id") int id, Model model) { //TODO: find tale by id and add it as model attribute
        return "tale";
    }

    @PostMapping("/editTale")
    public String editTale(@ModelAttribute Tale tale) {
        return "redirect:/tales/" + tale.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteTale(@PathVariable int id) {
        return "redirect:/tales";
    }

    @GetMapping("/favorite")
    public String favoriteTales(Model model) {
        model.addAttribute("tales", List.of());
        return "favoriteTales";
    }

    @GetMapping("/best")
    public String bestTales(Model model) {
        model.addAttribute("tales", List.of());
        return "bestTales";
    }

    @GetMapping("/unread")
    public String unreadTales(Model model) {
        model.addAttribute("tales", List.of());
        return "unreadTales";
    }

}
