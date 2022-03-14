package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    private UserDAO userDao;

    @GetMapping("/users")
    public String users(User user, Model model) {

        List<User> users = userDao.findAll();
        model.addAttribute("users", users);

        //user = userService.findUser(user);        
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/editUser")
    public String greetingSubmit(@ModelAttribute User user, Model model) {        
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        
        model.addAttribute("user", user);
        return "users";
    }
}
