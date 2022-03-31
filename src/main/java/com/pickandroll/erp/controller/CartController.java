package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;

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

        //Obtenir l'usuari actual
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        //Carregar els vehicles del cistell de la base de dades a la llista privada de vehicles
        vehicles = currUser.getVehicles();

        //Fixar els preus 
        cart.setPriceU(vehicles);

        cart.setSubPrice();
        cart.setTotalPrice();

        model.addAttribute("cart", cart);
        model.addAttribute("vehicles", vehicles);
        return "cart";
    }

    @RequestMapping(value = "/removeVehicle/{id}")
    public String removeVehicle(Vehicle v, Authentication auth) {

        //Obtenir l'usuari actual
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        //Esborrar els vehicles del cistell de la base de dades
        currUser.deleteVehicle(v);
        //Actualitzar l'usuari per obtenir el nou cistell
        userService.addUser(currUser);

        //Actualitzar la llista de vehicles privada
        vehicles = currUser.getVehicles();

        return "redirect:/cart";
    }

    @RequestMapping(value = "/addVehicle/{id}")
    public String addVehicle(Vehicle v, Authentication auth) {
        v = vehicleService.findVehicle(v);

        //Obtenir l'usuari actual
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());

        //Afegir els vehicles al cistell de la base de dades
        if (!currUser.getVehicles().contains(v)) {
            currUser.addVehicle(v);
            userService.addUser(currUser);
        }
        return "redirect:/vehicles";
    }

    //Disminuir el nombre de dies de la comanda
    @RequestMapping(value = "/minus_day")
    public String minusDay() {
        cart.setDays(cart.getDays() - 1);
        return "redirect:/cart";
    }

    //Augmentar el nombre de dies de la comanda
    @RequestMapping(value = "/plus_day")
    public String plusDay() {
        cart.setDays(cart.getDays() + 1);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/close_order")
    public String closeOrder() {
        //ToDo
        return "";
    }

//    @RequestMapping(value = "/removeVehicle/{id}")
//    public String removeVehicle(Vehicle v) {
//        cart.removeVehicles(v, cart, vehicles);
//        return "redirect:/cart";
//    }
}
