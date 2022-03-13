package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.userServiceInterface;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {

    private userServiceInterface userService;

    // Raiz
    @GetMapping("/")
    public String root(Model model) {

        return "main";
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

        var nomUser = "Santiago";
        var cogUser = "Alves P";
        var corUser = "santialves12@gmail.com";
        var dniUser = "12345678X";
        var telUser = "664033984";
        var passUser = "12345";
        var pass2User = "12345";

        model.addAttribute("nomUser", nomUser);
        model.addAttribute("cogUser", cogUser);
        model.addAttribute("corUser", corUser);
        model.addAttribute("dniUser", dniUser);
        model.addAttribute("telUser", telUser);
        model.addAttribute("passUser", passUser);
        model.addAttribute("pass2User", pass2User);

        return "profile";
    }

    @PostMapping("/editarPerfil")
    public String editarPerfil(@Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            //log.info("S'ha produït un error");
            return "profile";
        }

        userService.editarUsuari(user);

        //return "redirect:/";
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
