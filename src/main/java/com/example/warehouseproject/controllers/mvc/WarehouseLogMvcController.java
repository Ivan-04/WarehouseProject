package com.example.warehouseproject.controllers.mvc;

import com.example.warehouseproject.models.Action;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehouseLog;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/warehouses/logs")
@Validated
public class WarehouseLogMvcController {

    private final WarehouseLogService warehouseLogService;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String getWarehouseLogs(Model model,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "warehouse", required = false) String warehouseName,
                                @RequestParam(value = "part", required = false) String partName,
                                @RequestParam(value = "action", required = false) Action action,
                                @RequestParam(value = "quantity", required = false) Integer quantity,
                                @RequestParam(value = "timestamp", required = false) LocalDateTime timestamp,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                @RequestParam(value = "sortBy", defaultValue = "timestamp") String sortBy,
                                @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        Page<WarehouseLog> warehouseLogPage = warehouseLogService.getWarehouseLogWithFilters(email, warehouseName,
                quantity, partName, action, description, timestamp, page, size, sortBy, sortDirection);

        model.addAttribute("warehouseLogs", warehouseLogPage.getContent());
        model.addAttribute("totalPages", warehouseLogPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("email", email);
        model.addAttribute("warehouseName", warehouseName);
        model.addAttribute("partName", partName);
        model.addAttribute("action", action);
        model.addAttribute("quantity", quantity);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("description", description);
        model.addAttribute("actions", Action.values());

        return "WarehouseLogsView";
    }
}
