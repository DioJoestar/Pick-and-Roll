package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String profile(Model model, User user, Authentication auth) {
        
        // Si ya se ha autenticado un usuario, redirigir a la raíz 
        if (auth != null) {
            return "redirect:/";
        }

        return "login";
    }

}
