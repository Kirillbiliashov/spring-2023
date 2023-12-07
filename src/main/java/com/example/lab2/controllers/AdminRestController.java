package com.example.lab2.controllers;

import com.example.lab2.entity.Tale;
import com.example.lab2.services.TaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final TaleServiceImpl taleService;

    @Autowired
    public AdminRestController(TaleServiceImpl taleService) {
        this.taleService = taleService;
    }
//READ
    @GetMapping("/tales")
    public ResponseEntity<List<Tale>> getAllTales() {
        List<Tale> allTales = taleService.getAllTales().stream().toList();
        return ResponseEntity.ok(allTales);
    }
//CREATE
    @PostMapping("/tales")
    public ResponseEntity<Tale> createTale(@RequestBody Tale taleDto) {
        if (taleDto.getId() != null) {
         return ResponseEntity.badRequest().build();
        }
        Tale tale = taleService.save(taleDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(tale.getId()).toUri();

        return ResponseEntity.created(uri).body(tale);
    }
//UPDATE
    @PutMapping("/tales/{id}")
    public ResponseEntity<Tale> editTale(@PathVariable Long id, @RequestBody Tale taleDto) {
        if (taleDto.getId()!=null && !taleDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Tale tale = new Tale(id, taleDto.getTitle(), taleDto.getAuthor());
        tale = taleService.save(tale);
        return ResponseEntity.ok(tale);
    }
//DELETE
    @DeleteMapping("/tales/{id}")
    public ResponseEntity<Void> deleteTale(@PathVariable("id") Long id) {
        taleService.deleteTale(id);
        return ResponseEntity.noContent().build();
    }
}
