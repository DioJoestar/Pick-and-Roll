package com.pickandroll.erp.Controller;

import com.pickandroll.erp.dao.OrderDAO;
import com.pickandroll.erp.model.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Xavi
 */ 
@Controller
public class OrderController {
    
    @Autowired
    private OrderDAO orderDao;
    
    @GetMapping("/orders")
    public String orders(Model model) {
        
        //List<Order> orders = orderDao.findAll();
        //model.addAttribute("orders", orders);
        
        return "orders";
    }
}
