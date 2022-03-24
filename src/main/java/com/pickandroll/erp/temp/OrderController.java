package com.pickandroll.erp.temp;

import com.pickandroll.erp.dao.OrderDAO;
import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Xavi
 */
@Controller
public class OrderController {

    @Autowired
    private OrderDAO orderDao;

    @Autowired
    private OrderService orderService;

    List<Order> orders = new ArrayList<>();

    @GetMapping("/orders")
    public String orders(Model model) {

        orders = orderDao.findAll();
        model.addAttribute("orders", orders);

        return "orders";
    }

    @GetMapping("/editOrder")
    public String editOrder(Order order, Model model) {

        order = orderService.findOrder(order);

        model.addAttribute("order", order);

        return "editOrder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(Model model, Order order) {

        orderService.addOrder(order);

        return "redirect:/orders";
    }
}
