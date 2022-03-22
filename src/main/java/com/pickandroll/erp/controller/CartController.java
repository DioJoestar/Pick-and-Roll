package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.ModuleServiceInterface;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.service.VehicleServiceInterface;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private int days = 1;
    private double priceU = 0;
    private boolean first = true;
    private double subPrice = 0;
    private double IVA = 21;
    private double totalPrice = 0;
    NumberFormat formatter = new DecimalFormat("#0.00");

    @GetMapping("/cart")
    public String cart(Model model) {

        if (first) {
            Vehicle e = new Vehicle();
            e.setName("aaa");
            e.setDescription("ddd");
            e.setPrice(10d);
            e.setType("Bicicleta");

            vehicles.add(e);

            for (int i = 0; i < vehicles.size(); i++) {
                priceU += vehicles.get(i).getPrice();
            }
            first = false;
        }
        
        subPrice = (priceU * days) * (1d - (IVA/100));
        totalPrice = (priceU * days);
        
        
        String s_PriceU = String.valueOf(formatter.format(priceU) + "€");
        String s_subPrice = String.valueOf(formatter.format(subPrice) + "€");
        String s_totalPrice = String.valueOf(formatter.format(totalPrice) + "€");
        
        model.addAttribute("preuUnitat", s_PriceU);
        model.addAttribute("days", days);
        model.addAttribute("subPrice", s_subPrice);
        model.addAttribute("IVA", IVA);
        model.addAttribute("totalPrice", s_totalPrice);
        model.addAttribute("vehicles", vehicles);
        return "cart";
    }

    @RequestMapping(value = "/minus_day")
    public String minusDay() {
        days--;
        return "redirect:/cart";
    }

    @RequestMapping(value = "/plus_day")
    public String plusDay() {
        days++;
        return "redirect:/cart";
    }
    
    @RequestMapping(value = "/removeVehicle/{id}")
    public String removeVehicle(Vehicle v) {
        
        for(int i = 0;i<vehicles.size();i++){
            Vehicle v2 = vehicles.get(i);
            if(v2.getId() == v.getId()){
                vehicles.remove(i);
                priceU = 0;       
            }
        }        
        return "redirect:/cart";
    }
    
}
