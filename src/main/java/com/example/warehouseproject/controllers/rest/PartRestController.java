package com.example.warehouseproject.controllers.rest;

import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.services.contracts.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parts")
public class PartRestController {

    private final PartService partService;


    @GetMapping
    public ResponseEntity<List<PartOutput>> getAllParts() {
        return ResponseEntity.ok(partService.findAllParts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartOutput> getPartById(@PathVariable int id) {
        return ResponseEntity.ok(partService.findPartById(id));
    }

    @GetMapping("/title")
    public ResponseEntity<PartOutput> getPartByTitle(@RequestParam String title) {
        return ResponseEntity.ok(partService.findPartByTitle(title));
    }

    @PostMapping
    public ResponseEntity<PartOutputId> createPart(@Valid @RequestBody PartInput partInput) {
        return ResponseEntity.ok(partService.createPart(partInput));
    }
}
