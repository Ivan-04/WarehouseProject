package com.example.warehouseproject.controllers.rest;

import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.services.contracts.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouses")
public class WarehouseRestController {

    private final WarehouseService warehouseService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping
    public ResponseEntity<List<WarehouseOutput>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.findAllWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseOutput> getWarehouseById(@PathVariable int id) {
        return ResponseEntity.ok(warehouseService.findWarehouseById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<WarehouseOutput> getWarehouseByName(@RequestParam String name) {
        return ResponseEntity.ok(warehouseService.findWarehouseByName(name));
    }

    @PostMapping
    public ResponseEntity<WarehouseOutputId> createWarehouse(@Valid @RequestBody WarehouseInput warehouseInput) {
        return ResponseEntity.ok(warehouseService.createWarehouse(warehouseInput));
    }

    @PostMapping("/add/part/warehouse")
    public  ResponseEntity<String> addPartToWarehouse(@RequestHeader HttpHeaders headers, @RequestParam String warehouseName, String partTitle,
                                                      int quantityOfPAart){

        User user = authenticationHelper.tryGetUser(headers);
        warehouseService.addPartToWarehouse(user, warehouseName, partTitle, quantityOfPAart);
        return new ResponseEntity<>("Successfully added part!", HttpStatus.OK);
    }
}
