package com.example.warehouseproject.controllers.mvc;

import com.example.warehouseproject.helpers.AuthenticationHelper;
import com.example.warehouseproject.models.Role;
import com.example.warehouseproject.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final AuthenticationHelper authenticationHelper;

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

//    @ModelAttribute("isOwnerOrManager")
//    public void populateIsOwnerOrManager(HttpSession session, Model model) {
//        User user = authenticationHelper.tryGetUser(session);
//        if (user.getRole().equals(Role.MANAGER) || user.getRole().equals(Role.OWNER)) {
//            model.addAttribute("isOwnerOrManager", true);
//        }else {
//            model.addAttribute("isOwnerOrManager", false);
//        }
//    }

    @GetMapping
    public String showHomePage() {
        return "HomeView";
    }
}
