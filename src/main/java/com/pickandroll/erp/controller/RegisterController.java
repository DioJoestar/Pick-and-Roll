package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Role;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.RoleService;
import com.pickandroll.erp.service.UserService;
import com.pickandroll.erp.utils.Utils;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/register")
    public String registerForm(User user) {
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid User user, Errors errors, RedirectAttributes msg) {
        // Volver si hay errores en el formulario
        if (errors.hasErrors()) {
            return "register";
        }

        Utils u = new Utils();
        // Email no valido
        if (!u.checkDni(user.getDni())) {
            msg.addFlashAttribute("error", u.alert("profile.error.dni"));
            return "redirect:/register";
        }

        // Si la contraseñas no coinciden
        if (!user.getPassword().equals(user.getPasswordCheck())) {
            msg.addFlashAttribute("error", u.alert("profile.error.passwdDoesNotMatch"));
            return "redirect:/register";
        }

        // Si el usuario ya existe
        if (checkIfUserExist(user.getEmail())) {
            msg.addFlashAttribute("error", u.alert("profile.error.emailAlreadyTaken"));
            return "redirect:/register";
        }

        // Encriptamos la contraseña antes de guardarla        
        user.setPassword(u.encrypPasswd(user.getPassword()));

        // Creamos un rol nuevo y se lo añadimos al nuevo usuario
        Role defaultRole = roleService.findByName("customer");
        user.addRole(defaultRole);

        // Lo guardamos en la BBDD
        userService.addUser(user);

        return "redirect:/";
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

    // Mètode per comporovar si el checkbox no està activat
    /*private boolean checkIfCheckboxIsNotActive() {

    }*/
}
