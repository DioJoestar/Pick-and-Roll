package com.pickandroll.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecoverPasswordController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/recoverPassword")
    public String recoverPassword(Model model) {

        return "recoverPassword";
    }

    @PostMapping("/sendingEmail")
    public String emailSent(Model model, @RequestParam String email) {

        System.out.println(email);
        
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("FUNSIONA");
        msg.setText("ME FUNSIONA LA WEA");

        javaMailSender.send(msg);

        return "redirect:/";
    }
}
