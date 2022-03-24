package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

//    public List<Vehicle> vehicles = new ArrayList<Vehicle>();
//
//    private boolean first = true;
//
//    private Cart cart = new Cart();
//
//    @GetMapping("/cart")
//    public String cart(Model model) {
//
//        if (first) {
//            Vehicle e = new Vehicle();
//            e.setName("aaa");
//            e.setDescription("ddd");
//            e.setPrice(10d);
//            e.setType("Bicicleta");
//
//            vehicles.add(e);
//            cart.setPriceU(vehicles);
//            first = false;
//        }
//
//        cart.setSubPrice();
//        cart.setTotalPrice();
//
//        model.addAttribute("cart", cart);
//        model.addAttribute("vehicles", vehicles);
//        return "cart";
//    }
//
//    @RequestMapping(value = "/minus_day")
//    public String minusDay() {
//        cart.setDays(cart.getDays() - 1);
//        return "redirect:/cart";
//    }
//
//    @RequestMapping(value = "/plus_day")
//    public String plusDay() {
//        cart.setDays(cart.getDays() + 1);
//        return "redirect:/cart";
//    }
//
//    @RequestMapping(value = "/removeVehicle/{id}")
//    public String removeVehicle(Vehicle v) {
//       cart.removeVehicles(v, cart, vehicles);
//        return "redirect:/cart";
//    }
}
