package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.entity.User;
import com.example.lab2.services.TaleServiceImpl;
import com.example.lab2.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Operations with User")
public class UserRestController {
    private final UserServiceImpl userService;
    private final TaleServiceImpl taleService;

    @Autowired
    public UserRestController(UserServiceImpl userService, TaleServiceImpl taleService) {
        this.userService = userService;
        this.taleService = taleService;
    }

    @GetMapping("")
    @Operation(
            summary = "Get All Users",
            description = "Get a list of all users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))))
    })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Get by Id",
            description = "Get User by Id",
            parameters = {@Parameter(name = "userId", description = "Tale Id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<User> getUser(@PathVariable("userId") Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (OpenApiResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/{userId}/favorite")
    @Operation(
            summary = "Get Favorite Tales",
            description = "Get a list of favorite tales for a user",
            parameters = {@Parameter(name = "userId", description = "Tale Id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tale.class))))
    })
    public ResponseEntity<List<Tale>> getFavoriteTales(@PathVariable("userId") Long userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        List<Tale> favoriteTales = taleService.findFavoriteTalesByUserId(userId);
        return ResponseEntity.ok(favoriteTales);
    }

    @GetMapping("/{userId}/unread")
    @Operation(
            summary = "Get Unread Tales",
            description = "Get a list of unread tales for a user",
            parameters = {@Parameter(name = "userId", description = "Tale Id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tale.class))))
    })
    public ResponseEntity<List<Tale>> getUnreadTales(@PathVariable("userId") Long userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        List<Tale> unreadTales = taleService.findUnreadTalesByUserId(userId);
        return ResponseEntity.ok(unreadTales);
    }

    @PutMapping("/{userId}/tales/{taleId}")
    @Operation(
            summary = "Update User Interaction",
            description = "Update user interaction with a tale (like, read)",
            parameters = {
                @Parameter(name = "userId", description = "Tale Id", example = "1"),
                @Parameter(name = "taleId", description = "User Id", example = "2"),
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<String> updateUserInteraction(
            @PathVariable("userId") Long userId,
            @PathVariable("taleId") Long taleId,
            @RequestParam("like") boolean like,
            @RequestParam("read") boolean read) {
    if (userService.getUserById(userId) == null || taleService.findById(taleId) == null) {
            return ResponseEntity.notFound().build();
    }
        if (like) {
            taleService.addLikeToTale(userId, taleId);
        } else {
            taleService.removeLikeFromTale(userId, taleId);
        }
        if (read) {
            taleService.addReadToTale(userId, taleId);
        } else {
            taleService.removeReadFromTale(userId, taleId);
        }

        return ResponseEntity.ok("User interaction updated successfully");
    }
}