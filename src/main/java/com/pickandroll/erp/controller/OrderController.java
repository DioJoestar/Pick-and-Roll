package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.service.OrderService;
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

    @GetMapping("/orders")
    public String users(Order order, Model model) {

        List<Order> orders = orderService.listOrders();
        model.addAttribute("orders", orders);

        return "orders";
    }

    @PostMapping("/editOrder")
    public String editOrder(@ModelAttribute Order order, Model model) {
        
        List<Order> orders = orderService.listOrders();
        model.addAttribute("orders", orders);

        order = orderService.findOrder(order.getId());
        model.addAttribute("order", order);
        return "orders";
    }

    @PostMapping("/saveOrder")
    public String saveData(@Valid Order order, Errors errors, Model model, RedirectAttributes msg) {
        
        Utils u = new Utils();
        
        if (errors.hasErrors()) {
            List<Order> orders = orderService.listOrders();
            model.addAttribute("orders", orders);
            return "orders";
        }

        orderService.addOrder(order);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/orders";
    }
}