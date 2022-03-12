package com.pickandroll.erp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    // Raiz
    @GetMapping("/")
    public String root(Model model) {

        return "main";
    }
    
    @GetMapping("/register")
    public String register(Model model) {

        return "register";
    }

    @GetMapping("/recoverPassword")
    public String recoverPassword(Model model) {

        return "recoverPassword";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {

        return "changePassword";
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        return "profile";
    }

    @GetMapping("/cart")
    public String cart(Model model) {

        return "cart";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        return "main";
    }

    // Admin URLs
    @GetMapping("/modules")
    public String modules(Model model) {

        return "modules";
    }

    @GetMapping("/configModule")
    public String configModule(Model model) {

        return "configModule";
    }
}
