package com.example.warehouseproject.controllers.mvc;

import com.example.warehouseproject.exceptions.AuthenticationFailureException;
import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.WarehouseInput;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import com.example.warehouseproject.services.contracts.WarehouseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/warehouse")
@Validated
public class WarehouseMvcController {

    private final WarehouseService warehouseService;
    private final WarehousePartService warehousePartService;
    private final WarehouseLogService warehouseLogService;
    private final AuthenticationHelper authenticationHelper;

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
}
