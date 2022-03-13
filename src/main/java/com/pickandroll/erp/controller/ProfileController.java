package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.Role;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.utils.Utils;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
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
    public String updateUser(@Valid User user, Errors errors, Authentication auth) {
        if (errors.hasErrors()) {
            return "/profile";
        }

        Utils u = new Utils();
//        // Email no valido
//        if (!u.checkDni(user.getDni())) {
//            return "redirect:/profile";
//        }
//
//        // Si la contraseñas no coinciden
//        if (!user.getPassword().equals(user.getPasswordCheck())) {
//            return "redirect:/profile";
//        }
//
//        // Si el email ya está en uso
//        if (checkIfUserExist(user.getEmail())) {
//            return "redirect:/profile";
//        }

//        List<Role> roleList = user.getRoles();
        // Encriptamos la contraseña antes de guardarla
        user.setPassword(u.encrypPasswd(user.getPassword()));

        userService.addUser(user);
        return "redirect:/profile";
    }

    // Método para comprobar si el email ya existe en la DDBB
    private boolean checkIfUserExist(String email) {
        List<User> userList = userService.listUsers();
        for (User u : userList) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
