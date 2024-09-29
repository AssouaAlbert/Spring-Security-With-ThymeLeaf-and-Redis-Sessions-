package com.assoua.thyme_security.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class LogOutController {

    private final WebClient webClient;

    public LogOutController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/logout-confirm")
    public String showLogoutConfirmation() {
        return "logout-confirm";  // This will return the Thymeleaf template for logout confirmation
    }

}
