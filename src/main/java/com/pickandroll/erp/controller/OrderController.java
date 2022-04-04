package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.OrderService;
import com.pickandroll.erp.service.UserService;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/orders")
    public String orders(Order order, Model model) {

        // Obté el email de l'usuari actual.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userLoggedEmail = auth.getName();
        Long userLoggedId = 0L;
        boolean isAdmin = false;
        List<User> users = userService.listUsers();

        for (User u : users) {
            if (u.getEmail().equals(userLoggedEmail)) {
                userLoggedId = u.getId();
                if (u.isAdmin()) {
                    isAdmin = true;
                }
            }
        }

        List<Order> orders = orderService.listOrders();

        List<Order> userOrders = new ArrayList<>();
        for (Order o : orders) {
            if (o.getUserId() == userLoggedId || isAdmin) {
                userOrders.add(o);
            }
        }

        model.addAttribute("orders", userOrders);

        List<Vehicle> vehicles = vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicles);

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

        if (errors.hasErrors()) {
            List<Order> orders = orderService.listOrders();
            model.addAttribute("orders", orders);
            return "orders";
        }

        orderService.addOrder(order);
        return "redirect:/orders";
    }
}
