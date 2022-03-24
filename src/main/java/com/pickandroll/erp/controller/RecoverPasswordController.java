package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecoverPasswordController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/recoverPassword")
    public String recoverPassword(Model model) {

        return "recoverPassword";
    }

    @PostMapping("/recoverPassword")
    public String emailSent(Model model, @RequestParam String email, RedirectAttributes msg) {
        Utils u = new Utils();
        User user = userService.findByEmail(email);

        msg.addFlashAttribute("info", u.alert("recover.info.emailSent"));
        // Email not found
        if (user == null) {
            return "redirect:/recoverPassword";
        }

        // Guardar el token al usuari
        user.setResetPasswordToken(u.genToken());
        //userDao.save(user);
        userService.addUser(user);
        sendMail(user.getEmail(), user.getResetPasswordToken());
        return "redirect:/recoverPassword";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(Model model, @RequestParam String token) {
        User u = null;

        // Retorna un User a partir del token
        u = userService.findByResetPasswordToken(token);

        // Si el token está vacío o no encuentra el usuario mostraremos la página de error 400 (Bad request)
        if (token.equals("") || token == null || u == null) {
            return "error/400";
        }

        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, @RequestParam String token) {


        return "changePassword";
    }

    public void sendMail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        String link = "http://localhost:8080/changePassword?token=" + token;

        String body = "Per canviar la teva contrasenya, segueix el seguent enllaç";

        message.setTo(to);
        message.setSubject("Pick & Roll | Recuperar contrasenya");
        message.setText(body + " " + link);
        javaMailSender.send(message);
    }
}
