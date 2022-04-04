package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.OrderService;
import com.pickandroll.erp.service.UserService;
import com.pickandroll.erp.service.VehicleService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/orders")
    public String orders(Order order, Model model, Authentication auth) {

        // Coger los datos de la sessión
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        

        // Crear el objeto User a partir del email (username) de la sesión actual
        User currUser = userService.findByEmail(userDetails.getUsername());
        
        System.out.println(currUser.getOrders().get(0).getPickedDate());
        List<Order> userOrders = currUser.getOrders();
        
        model.addAttribute("orders", userOrders);

        return "orders";
    }

    @PostMapping("/editOrder")
    public String editOrder(@ModelAttribute Order order, Model model) {

        List<Order> orders = orderService.listOrders();
        model.addAttribute("orders", orders);

        order = orderService.findById(order.getId());
        model.addAttribute("order", order);

        return "orders";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@Valid Order order, Errors errors, Model model, RedirectAttributes msg) {

//        if (errors.hasErrors()) {
//            List<Order> orders = orderService.listOrders();
//            model.addAttribute("orders", orders);
//            return "orders";
//        }
        orderService.addOrder(order);
        return "redirect:/orders";
    }
}
