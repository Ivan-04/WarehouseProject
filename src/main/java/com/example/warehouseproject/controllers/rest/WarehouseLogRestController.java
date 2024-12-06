package com.example.warehouseproject.controllers.rest;

import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.helpers.PermissionHelper;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.WarehouseLogOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse/logs")
public class WarehouseLogRestController {

    private final WarehouseLogService warehouseLogService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping
    public ResponseEntity<List<WarehouseLogOutput>> getAllWarehouseLogs(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        return ResponseEntity.ok(warehouseLogService.findAllWarehouseLogs(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseLogOutput> getWarehouseLogById(@RequestHeader HttpHeaders headers,
                                                                  @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return ResponseEntity.ok(warehouseLogService.findWarehouseLogById(id, user));
    }
}
