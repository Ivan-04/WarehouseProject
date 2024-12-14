package com.example.warehouseproject.controllers.mvc;

import com.example.warehouseproject.exceptions.AuthenticationFailureException;
import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.*;
import com.example.warehouseproject.models.dtos.WarehouseInput;
import com.example.warehouseproject.models.dtos.WarehouseLogInput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.services.contracts.PartService;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import com.example.warehouseproject.services.contracts.WarehouseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/warehouses")
@Validated
public class WarehouseMvcController {

    private final WarehouseService warehouseService;
    private final AuthenticationHelper authenticationHelper;
    private final PartService partService;
    private final WarehousePartService warehousePartService;
    private final WarehouseLogService warehouseLogService;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/new")
    public String showNewWarehousePage(Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/Login";
        }

        model.addAttribute("warehouse", new WarehouseInput());
        return "WarehouseCreateView";
    }

    @PostMapping("/new")
    public String createWarehouse(@Valid @ModelAttribute("warehouse") WarehouseInput warehouseInput,
                                BindingResult bindingResult,
                                Model model,
                                HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/Login";
        }

        if (bindingResult.hasErrors()) {
            return "WarehouseCreateView";
        }

        try {
            warehouseService.createWarehouse(warehouseInput);
            return "redirect:/";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("title", "duplicate_contest", e.getMessage());
            return "WarehouseCreateView";
        }
    }

    @GetMapping
    public String getWarehouses(Model model,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size,
                           @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                           @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        Page<Warehouse> warehousesPage = warehouseService.getWarehousesWithFilters(name, page, size, sortBy, sortDirection);

        model.addAttribute("warehouses", warehousesPage.getContent());
        model.addAttribute("totalPages", warehousesPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("name", name);

        return "WarehousesView";
    }

    @GetMapping("/{id}")
    public String showSingleWarehouse(@PathVariable int id, Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            Warehouse warehouse = warehouseService.findWarehouseEntityById(id);
            model.addAttribute("warehouse", warehouse);
            Map<Part, Integer> partOfThisWarehouse = warehouseService.getAllPartsOfWarehouse(id);
            model.addAttribute("partsOfTheWarehouse", partOfThisWarehouse);
            return "WarehouseView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthenticationFailureException e) {
            return "AccessDeniedView";
        }
    }


    @GetMapping("/add/part/{id}")
    public String showNewAddPartPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/Login";
        }

        Part part = partService.findPartEntityById(id);
        List<Warehouse> warehouses = warehouseService.findAllWarehousesEntities();
        model.addAttribute("part", part);
        model.addAttribute("warehouses", warehouses);
        model.addAttribute("warehousePart", new WarehousePart());

        return "AddPartToWarehouseView";
    }


    @PostMapping("/add/part/{id}")
    public String addPartToWarehouse(@Valid @ModelAttribute("warehousePart") WarehousePart warehousePart,
                                     BindingResult bindingResult,
                                     @PathVariable int id,
                                     Model model,
                                     HttpSession session) {

        try {
            authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/Login";
        }

        if (bindingResult.hasErrors()) {
            Part part = partService.findPartEntityById(id);
            List<Warehouse> warehouses = warehouseService.findAllWarehousesEntities();
            model.addAttribute("part", part);
            model.addAttribute("warehouses", warehouses);
            return "AddPartToWarehouseView";
        }

        try {
            Part part = partService.findPartEntityById(id);
            Warehouse warehouse = warehouseService.findWarehouseEntityById(warehousePart.getWarehouse().getWarehouseId());

            WarehousePart existingPart = warehousePartService.findWarehousePart(warehouse, part);
            if (existingPart != null) {
                warehousePartService.changeTheQuantity(existingPart, warehousePart.getQuantity());
            } else {
                warehousePartService.createWarehousePart(warehouse, part, warehousePart.getQuantity());
            }

            WarehouseLogInput logInput = WarehouseLogInput.builder()
                    .part(part)
                    .quantity(warehousePart.getQuantity())
                    .warehouse(warehouse)
                    .user(authenticationHelper.tryGetUser(session))
                    .action(Action.ADD)
                    .build();

            warehouseLogService.createWarehouseLog(logInput);
            return "redirect:/";
        } catch (EntityNotFoundException | DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
}
