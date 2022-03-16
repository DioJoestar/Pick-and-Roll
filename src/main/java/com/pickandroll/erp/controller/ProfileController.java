package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.utils.Utils;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/profile")
    public String profile(Model model, User user, Authentication auth) {
        // Coger los datos de la sessión
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Crear el objeto User a partir del email (username) de la sesión actual
        User currUser = userDao.findByEmail(userDetails.getUsername());

        model.addAttribute("user", currUser);

        return "profile";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid User user, Errors errors, Authentication auth, RedirectAttributes msg) {
        if (errors.hasErrors()) {
            return "/profile";
        }

        Utils u = new Utils();
        // Email no valido
        if (!u.checkDni(user.getDni())) {
            msg.addFlashAttribute("error", u.alert("profile.error.dni"));
            return "redirect:/profile";
        }

        // Si la contraseñas no coinciden
        if (!user.getPassword().equals(user.getPasswordCheck())) {
            msg.addFlashAttribute("error", u.alert("profile.error.passwdDoesNotMatch"));
            return "redirect:/profile";
        }
        
        // Encriptamos la contraseña antes de guardarla
        user.setPassword(u.encrypPasswd(user.getPassword()));

        userService.addUser(user);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/profile";
    }
}
