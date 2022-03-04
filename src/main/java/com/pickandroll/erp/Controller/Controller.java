package com.pickandroll.erp.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    // Raiz
    @GetMapping("/")
    public String root(Model model) {

        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        return "main";
    }

    @GetMapping("/recoverPassword")
    public String recoverPassword(Model model) {

        return "main";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {

        return "main";
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        return "main";
    }

    @GetMapping("/cart")
    public String cart(Model model) {

        return "main";
    }

    @GetMapping("/orders")
    public String orders(Model model) {

        return "main";
    }

    // Admin URLs
    @GetMapping("/modules")
    public String modules(Model model) {

        return "main";
    }

    @GetMapping("/users")
    public String users(Model model) {

        return "main";
    }
}
