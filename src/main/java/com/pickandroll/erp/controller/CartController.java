package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserServiceInterface userService;

    private Cart cart = new Cart();

    @GetMapping("/cart")
    public String cart(Model model, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        vehicles = currUser.getVehicles();

        cart.setPriceU(vehicles);

        cart.setSubPrice();
        cart.setTotalPrice();

        model.addAttribute("cart", cart);
        model.addAttribute("vehicles", vehicles);
        return "cart";
    }

    @RequestMapping(value = "/removeVehicle/{id}")
    public String removeVehicle(Vehicle v, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        currUser.deleteVehicle(v);
        userService.addUser(currUser);

        vehicles = currUser.getVehicles();

        return "redirect:/cart";
    }

    @RequestMapping(value = "/addVehicle/{id}")
    public String addVehicle(Vehicle v, Authentication auth) {
        v = vehicleService.findVehicle(v);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        if (!currUser.getVehicles().contains(v)) {
            currUser.addVehicle(v);
            userService.addUser(currUser);
        }
        return "redirect:/vehicles";
    }

    @RequestMapping(value = "/minus_day")
    public String minusDay() {
        cart.setDays(cart.getDays() - 1);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/plus_day")
    public String plusDay() {
        cart.setDays(cart.getDays() + 1);
        return "redirect:/cart";
    }

//    @RequestMapping(value = "/removeVehicle/{id}")
//    public String removeVehicle(Vehicle v) {
//        cart.removeVehicles(v, cart, vehicles);
//        return "redirect:/cart";
//    }
}
