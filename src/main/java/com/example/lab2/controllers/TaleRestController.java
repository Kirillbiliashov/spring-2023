package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.services.TaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tales")
public class TaleRestController {
    private final TaleServiceImpl taleService;

    @Autowired
    public TaleRestController(TaleServiceImpl taleService) {
        this.taleService = taleService;
    }

    @GetMapping("")
    public ResponseEntity<List<Tale>> getAllTales() {
        List<Tale> tales = taleService.getAllTales();
        return ResponseEntity.ok(tales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tale> getTale(@PathVariable("id") Long id) {
        Tale tale = taleService.getTaleById(id);
        return ResponseEntity.ok(tale);
    }

    @GetMapping("/best")
    public ResponseEntity<List<Tale>> getBestTales() {
        List<Tale> tales = taleService.findBestTales(4);
        return ResponseEntity.ok(tales);
    }
}
