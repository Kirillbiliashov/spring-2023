package com.example.lab2.controllers;

import com.example.lab2.Dto.TaleDto;
import com.example.lab2.Dto.TaleWithLikesDto;
import com.example.lab2.services.TaleServiceImpl;
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
@RequestMapping("/tales")
@Tag(name = "Tale", description = "Operations with Tale")
public class TaleRestController {
    private final TaleServiceImpl taleService;

    @Autowired
    public TaleRestController(TaleServiceImpl taleService) {
        this.taleService = taleService;
    }

    @GetMapping("")
    @Operation(
            summary = "Get All Tales",
            description = "Get a list of all tales"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaleDto.class))),
    })
    public ResponseEntity<List<TaleDto>> getAllTales() {
        List<TaleDto> tales = taleService.findAll();
        return ResponseEntity.ok(tales);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get By Id",
            description = "Get Tale by identifier",
            parameters = {@Parameter(name = "id", description = "Tale Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaleDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<TaleDto> getTale(@PathVariable("id") Long id) {
        try {
            TaleDto tale = taleService.findById(id)
                    .orElseThrow(() -> new OpenApiResourceNotFoundException("Tale id=" + id + " not found"));
            return ResponseEntity.ok(tale);
        } catch (OpenApiResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get Best Tales",
            description = "Get a list of the best tales",
            parameters = {@Parameter(name = "count", description = "Number of best tales to retrieve", example = "4")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaleWithLikesDto.class)))),
    })
    @GetMapping("/best")
    public ResponseEntity<List<TaleWithLikesDto>> getBestTales() {
        List<TaleWithLikesDto> tales = taleService.findBestTales();
        return ResponseEntity.ok(tales);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search Tales",
            description = "Search tales by criteria"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaleDto.class))))
    })
    public ResponseEntity<List<TaleDto>> searchTales(@RequestParam("criteria") String criteria) {
        List<TaleDto> tales = taleService.findByCriteria(criteria);
        return ResponseEntity.ok(tales);
    }

}
