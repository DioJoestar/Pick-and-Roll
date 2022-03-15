package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.Role;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.RoleService;
import com.pickandroll.erp.service.UserService;
import com.pickandroll.erp.utils.Utils;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {

    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(User user, Model model) {

        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute User user, Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);

        // Sobreescribimos el usuario para recuperar los roles
        user = userDao.findByEmail(user.getEmail());

        List<Role> listRoles = roleService.listRoles();
        
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "users";
    }

    @PostMapping("/saveData")
    public String saveData(@Valid User user, Errors errors, RedirectAttributes msg) {

        // Hay que hacer toda la pesca de validación...
        Utils u = new Utils();
        
        // Lo guardamos en la BBDD
        userService.addUser(user);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/users";
    }
}
