package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.services.TaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<Tale>> getAllTales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String filter) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
        Page<Tale> tales;

        tales = taleService.getAllTalesPageWithFilter(pageRequest, filter);

        return ResponseEntity.ok(tales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tale> getTale(@PathVariable("id") Long id) {
        Tale tale = taleService.getTaleById(id);
        if (tale != null) {
            return ResponseEntity.ok(tale);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/best")
    public ResponseEntity<List<Tale>> getBestTales() {
        List<Tale> tales = taleService.findBestTales(4);
        return ResponseEntity.ok(tales);
    }
}
