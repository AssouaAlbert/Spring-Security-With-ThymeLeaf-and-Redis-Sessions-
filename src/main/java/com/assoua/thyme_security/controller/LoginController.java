package com.assoua.thyme_security.controller;//package com.aassoua.controller;

import com.assoua.thyme_security.service.CaptchaService;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class LoginController {

    @Autowired
    private CaptchaService captchaService;

    // Serve CAPTCHA image
    @GetMapping("/captcha")
    public void getCaptchaImage(HttpServletResponse response, HttpSession session) throws IOException {

        // Generate a random string of characters that will serve as the CAPTCHA text.
        String captchaText = captchaService.generateCaptchaText();
        // Store the captchaText with key captcha in redis for user session
        session.setAttribute("captcha", captchaText); // Store CAPTCHA in session
        // Then writes the image in response
        captchaService.generateCaptchaImage(captchaText, response); // Write image to response
    }


    @GetMapping("/login")
    public String login() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        if (isAuthenticated) {
            return "redirect:/home";
        } else {
            return "login";
        }
    }
}
