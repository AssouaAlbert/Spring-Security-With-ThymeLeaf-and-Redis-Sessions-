package com.assoua.thyme_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ProfileController {
    @GetMapping({"/prof", "/profile"})
    public String newsPage() {
        return "profile";
    }
}
