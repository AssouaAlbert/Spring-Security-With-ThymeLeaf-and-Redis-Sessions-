package com.assoua.thyme_security.controller;//package com.aassoua.controller;

import com.assoua.thyme_security.service.CaptchaService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/user-login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("captcha") String captchaInput,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        System.out.println("Olla Olle");
        // Retrieve CAPTCHA from session
        String captchaStored = (String) session.getAttribute("captcha");
        if (captchaStored == null || !captchaStored.equals(captchaInput)) {
            model.addAttribute("errorCaptcha", "CAPTCHA verification failed");
            return "login"; // Redirect back to login page if CAPTCHA fails
        }

        // Create a UsernamePasswordAuthenticationToken with the user's credentials
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        // Set details (optional, typically useful if you need authentication details like IP address)
        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Manually pass the token to Spring Security's context for authentication
        SecurityContextHolder.getContext().setAuthentication(authRequest);

        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);
        System.out.println(isAuthenticated);


        // Optionally, redirect to another endpoint for further authentication handling
        return "redirect:/home"; // Redirect to home after successful CAPTCHA and setting credentials
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
