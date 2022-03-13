package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {

    @Autowired
    private UserDAO userDao;

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userDao.findAll();

        model.addAttribute("users", users);
        return "users";
    }
}
