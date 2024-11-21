package com.example.warehouseproject.controllers.rest;

import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.services.contracts.LineService;
import com.example.warehouseproject.services.contracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lines")
public class LineRestController {

    private final LineService lineService;
    private final AuthenticationHelper authenticationHelper;

    @GetMapping
    public ResponseEntity<List<LineOutput>> getAllLines() {
        return ResponseEntity.ok(lineService.findAllLines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineOutput> getLineById(@PathVariable int id) {
        return ResponseEntity.ok(lineService.findLineById(id));
    }

    @GetMapping("/num")
    public ResponseEntity<LineOutput> getLineByLineNum(@RequestParam int num) {
        return ResponseEntity.ok(lineService.findLineByNum(num));
    }

    @PostMapping
    public ResponseEntity<LineOutputId> createLine(@RequestHeader HttpHeaders headers, @Valid @RequestBody LineInput lineInput) {
        User user = authenticationHelper.tryGetUser(headers);
        return ResponseEntity.ok(lineService.createLine(lineInput, user));
    }
}
