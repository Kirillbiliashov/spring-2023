package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.entity.User;
import com.example.lab2.services.TaleServiceImpl;
import com.example.lab2.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserServiceImpl userService;
    private final TaleServiceImpl taleService;

    @Autowired
    public UserRestController(UserServiceImpl userService, TaleServiceImpl taleService) {
        this.userService = userService;
        this.taleService = taleService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/favorite")
    public ResponseEntity<List<Tale>> getFavoriteTales(@PathVariable("userId") Long userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        TreeSet<Long> userTales = userService.getFavoriteTales(userId);
        List<Tale> favoriteTales = userTales.stream()
                .map(taleService::getTaleById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(favoriteTales);
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<Tale>> getUnreadTales(@PathVariable("userId") Long userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        TreeSet<Long> userTales = userService.getUnreadTales(userId);
        List<Tale> unreadTales = userTales.stream()
                .map(taleService::getTaleById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(unreadTales);
    }

    @PutMapping("/{userId}/tales/{taleId}")
    public ResponseEntity<String> updateUserInteraction(
            @PathVariable("userId") Long userId,
            @PathVariable("taleId") Long taleId,
            @RequestParam("like") boolean like,
            @RequestParam("read") boolean read) {
    if (userService.getUserById(userId) == null || taleService.getTaleById(taleId) == null) {
            return ResponseEntity.notFound().build();
    }
        if (like) {
            taleService.addLikeToTale(userId, taleId);
            userService.addToFavorites(userId, taleId);
        } else {
            taleService.removeLikeFromTale(userId, taleId);
            userService.removeFromFavorites(userId, taleId);
        }

        if (!read) {
            userService.addToUnread(userId, taleId);
        } else {
            userService.removeFromUnread(userId, taleId);
        }

        return ResponseEntity.ok("User interaction updated successfully");
    }
}