package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
            description = "Get a paginated list of all tales",
            parameters = {
                    @Parameter(name = "page", description = "Page number (default: 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page (default: 10)", example = "10"),
                    @Parameter(name = "sort", description = "Sort field (default: id)", example = "1"),
                    @Parameter(name = "filter", description = "Optional filter to apply")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
    })
    public ResponseEntity<List<Tale>> getAllTales() {
        List<Tale> tales = taleService.findAll();
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
                            schema = @Schema(implementation = Tale.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity<Tale> getTale(@PathVariable("id") Long id) {
        Tale tale = taleService.findById(id);
        if (tale != null) {
            return ResponseEntity.ok(tale);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Get Best Tales",
            description = "Get a list of the best tales",
            parameters = {@Parameter(name = "count", description = "Number of best tales to retrieve", example = "4")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tale.class)))),
    })
    @GetMapping("/best")
    public ResponseEntity<List<Tale>> getBestTales() {
        List<Tale> tales = taleService.findBestTales();
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
                            array = @ArraySchema(schema = @Schema(implementation = Tale.class))))
    })
    public ResponseEntity<Collection<Tale>> searchTales(@RequestParam("criteria") String criteria) {
        Collection<Tale> tales = taleService.findByCriteria(criteria);
        return ResponseEntity.ok(tales);
    }

}
