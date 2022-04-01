package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
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
        userService.addUser(user);
        
        // ENviar email
        sendMail(user.getEmail(), user.getResetPasswordToken());
        
        return "redirect:/recoverPassword";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(Model model, @RequestParam String token) {
        User user = null;

        // Retorna un User a partir del token
        user = userService.findByResetPasswordToken(token);

        // Si el token está vacío o no encuentra el usuario mostraremos la página de error 400 (Bad request)
        if (token.equals("") || token == null || user == null) {
            return "error/400";
        }

        model.addAttribute("user", user);
        
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(User user, Errors errors, RedirectAttributes msg) {
        User currUser = userService.findByEmail(user.getEmail());
        
        if (errors.hasErrors()) {
            return "changePassword";
        }
        
        Utils u = new Utils();
        if (!user.getPassword().equals(user.getPasswordCheck())) {
            msg.addFlashAttribute("error", u.alert("profile.error.passwdDoesNotMatch"));
            return "redirect:/changePassword?token=" + user.getResetPasswordToken();
        }
        
        currUser.setPassword(u.encrypPasswd(user.getPassword()));
        currUser.setResetPasswordToken(null);
        userService.addUser(currUser);
        
        return "login";
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
