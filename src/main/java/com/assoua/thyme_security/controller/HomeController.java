package com.assoua.thyme_security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;  // Import Controller instead of RestController
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // Use @Controller to return views
public class HomeController {
    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isAuth", isAuthenticated);

        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "home";  // This returns the home.html Thymeleaf template
    }
}
