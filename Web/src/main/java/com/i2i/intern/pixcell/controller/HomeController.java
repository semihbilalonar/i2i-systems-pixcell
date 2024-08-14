package com.i2i.intern.pixcell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/logout")
    public String showLogout() {
        return "login";
    }
}
