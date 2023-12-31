package com.example.lab2.controllers;

import com.example.lab2.Dto.TaleDto;
import com.example.lab2.services.TaleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Operations with Admin")
public class AdminRestController {
    private final TaleServiceImpl taleService;

    @Autowired
    public AdminRestController(TaleServiceImpl taleService) {
        this.taleService = taleService;
    }
//READ

    @GetMapping("/tales")
    @Operation(
            summary = "Get All Tales",
            description = "Get a list of all tales for admin purposes"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaleDto.class))))
    })
    public ResponseEntity<List<TaleDto>> getAllTales() {
        List<TaleDto> allTales = taleService.findAll();
        return ResponseEntity.ok(allTales);
    }
    //CREATE
    @PostMapping("/tales")
    @Operation(
            summary = "Create Tale",
            description = "Create a new tale for admin purposes"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaleDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<TaleDto> createTale(@RequestBody TaleDto taleDto) {
        if (taleDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        TaleDto savedTale = taleService.save(taleDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedTale.getId()).toUri();

        return ResponseEntity.created(uri).body(savedTale);
    }
    //UPDATE
    @PutMapping("/tales/{id}")
    @Operation(
            summary = "Edit Tale",
            description = "Edit an existing tale for admin purposes",
            parameters = {@Parameter(name = "id", description = "Tale Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaleDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    public ResponseEntity<TaleDto> editTale(@PathVariable Long id, @RequestBody TaleDto taleDto) {
        if (taleDto.getId()!=null && !taleDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        TaleDto updatedTale = taleService.save(taleDto);
        return ResponseEntity.ok(updatedTale);
    }
    //DELETE
    @DeleteMapping("/tales/{id}")
    @Operation(
            summary = "Delete Tale",
            description = "Delete an existing tale for admin purposes",
            parameters = {@Parameter(name = "id", description = "Tale Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    public ResponseEntity<Void> deleteTale(@PathVariable("id") Long id) {
        taleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

