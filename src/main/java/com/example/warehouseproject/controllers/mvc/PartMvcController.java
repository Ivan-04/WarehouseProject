package com.example.warehouseproject.controllers.mvc;

import com.example.warehouseproject.exceptions.AuthenticationFailureException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.PartOutput;
import com.example.warehouseproject.services.contracts.PartService;
import com.example.warehouseproject.services.contracts.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/parts")
@Validated
public class PartMvcController {

    private final PartService partService;
    private final AuthenticationHelper authenticationHelper;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }


    @GetMapping
    public String getParts(Model model,
                           @RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "description", required = false) String description,
                           //@RequestParam(value = "price", required = false) double price,
                           @RequestParam(value = "created_at", required = false) LocalDateTime createdAt,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size,
                           @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
                           @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        Page<Part> partsPage = partService.getPartsWithFilters(title, description, page, size, sortBy, sortDirection);

        model.addAttribute("parts", partsPage.getContent());
        model.addAttribute("totalPages", partsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("títle", title);
        model.addAttribute("description", description);
        //model.addAttribute("price", price);

        return "PartsView";
    }

    @GetMapping("/{id}")
    public String showSinglePart(@PathVariable int id, Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            Part part = partService.findPartEntityById(id);
            model.addAttribute("part", part);
            return "PartView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthenticationFailureException e) {
            return "AccessDeniedView";
        }
    }

}
