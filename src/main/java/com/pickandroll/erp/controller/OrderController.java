package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.OrderService;
import com.pickandroll.erp.service.VehicleService;
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
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/orders")
    public String orders(Order order, Model model) {

        List<Order> orders = orderService.listOrders();
        model.addAttribute("orders", orders);
        
        List<Vehicle> vehicles = vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicles);

        return "orders";
    }

    @PostMapping("/editOrder")
    public String editOrder(@ModelAttribute Order order, Model model) {
        List<Order> orders = orderService.listOrders();
        model.addAttribute("orders", orders);

        order = orderService.findById(order.getId());

        //List<Vehicle> listVehicles = vehicleService.listVehicles();

        model.addAttribute("order", order);
        //model.addAttribute("listVehicles", listVehicles);
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

    @PostMapping("/deleteOrder")
    public String deleteOrder(@Valid Order order, Errors errors, RedirectAttributes msg) {
        Utils u = new Utils();

        orderService.deleteOrder(order);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/orders";
    }
}
