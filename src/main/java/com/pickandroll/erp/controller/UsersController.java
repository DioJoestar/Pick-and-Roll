package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class UsersController {     
    
    @Autowired
    private UserDAO userDao;
    
    @GetMapping("/users")
    public String users(Model model) {
        var users = userDao.findAll();
        
        model.addAttribute("users", users);
        return "users";
    }
}
