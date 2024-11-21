package com.example.warehouseproject.controllers.rest;

import com.example.warehouseproject.models.dtos.Register;
import com.example.warehouseproject.models.dtos.UserOutput;
import com.example.warehouseproject.models.dtos.UserOutputId;
import com.example.warehouseproject.services.contracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOutput>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/firstName")
    public ResponseEntity<UserOutput> getUserByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(userService.findUserByFirstName(firstName));
    }

    @GetMapping("/lastName")
    public ResponseEntity<UserOutput> getUserByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(userService.findUserByLastName(lastName));
    }

    @GetMapping("/email")
    public ResponseEntity<UserOutput> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserOutputId> createUser(@Valid @RequestBody Register register) {
        return ResponseEntity.ok(userService.createUser(register));
    }
}
