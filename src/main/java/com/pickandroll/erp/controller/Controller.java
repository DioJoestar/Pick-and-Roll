package com.pickandroll.erp.controller;

import com.pickandroll.erp.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {

    // Raiz
    @GetMapping("/")
    public String root(Model model) {

        return "vehicles";
    }

    @GetMapping("/loginError")
    public String login(HttpServletRequest request, RedirectAttributes msg) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                Utils u = new Utils();
                msg.addFlashAttribute("error", u.alert("login.error.invalidLogin"));
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/recoverPassword")
    public String recoverPassword(Model model) {

        return "recoverPassword";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {

        return "changePassword";
    }

    @GetMapping("/cart")
    public String cart(Model model) {

        return "cart";
    }
}
