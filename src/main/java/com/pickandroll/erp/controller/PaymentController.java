package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * @author santialves
 */
@Controller
public class PaymentController {
    
    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/payment")
    public String payment(Model model, User user, Authentication auth) {

        //Pagament

        return "payment";
    }

}
