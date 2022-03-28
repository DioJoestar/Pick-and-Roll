package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehicleController {

    public List<Vehicle> vehicles = new ArrayList<Vehicle>();

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String users(Vehicle vehicle, Model model) {

        //List<Vehicle> vehicles = vehicleDao.findAll();
        List<Vehicle> vehicles = vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicles);

        return "vehicles";
    }

    @PostMapping("/editVehicle")
    public String editVehicle(@ModelAttribute Vehicle vehicle, Model model) {
        List<Vehicle> vehicles = vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicles);

        //vehicle = vehicleDao.findByName(vehicle.getName());
        vehicle = vehicleService.findbyName(vehicle.getName());
        model.addAttribute("vehicle", vehicle);
        return "vehicles";
    }

    @PostMapping("/saveVehicle")
    public String saveData(@Valid Vehicle vehicle, Errors errors, Model model, RedirectAttributes msg) {
        Utils u = new Utils();

        if (errors.hasErrors()) {
            List<Vehicle> vehicles = vehicleService.listVehicles();
            model.addAttribute("vehicles", vehicles);
            return "vehicles";
        }

        vehicleService.addVehicle(vehicle);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/vehicles";
    }

    @PostMapping("/deleteVehicle")
    public String deleteVehicle(@Valid Vehicle vehicle, Errors errors, RedirectAttributes msg) {
        Utils u = new Utils();

        vehicleService.deleteVehicle(vehicle);
        msg.addFlashAttribute("success", u.alert("profile.success"));
        return "redirect:/vehicles";
    }

    @RequestMapping(value = "/addVehicle/{id}")
    public String addVehicle(Vehicle v) {
        v = vehicleService.findVehicle(v);
        if (!vehicles.contains(v)) {
            vehicles.add(v);
        }

        return "redirect:/vehicles";
    }

    private Cart cart = new Cart();

    @GetMapping("/cart")
    public String cart(Model model) {

        cart.setPriceU(vehicles);

        cart.setSubPrice();
        cart.setTotalPrice();

        model.addAttribute("cart", cart);
        model.addAttribute("vehicles", vehicles);
        return "cart";
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

    @RequestMapping(value = "/removeVehicle/{id}")
    public String removeVehicle(Vehicle v) {
        cart.removeVehicles(v, cart, vehicles);
        return "redirect:/cart";
    }

}
