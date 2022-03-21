package com.pickandroll.erp.Controller;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavi
 */
@Controller
public class VehicleController {

    @Autowired
    private VehicleDAO vehicleDao;

    @Autowired
    private VehicleService vehicleService;

    List<Vehicle> vehicles = new ArrayList<>();

    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        
        vehicles = vehicleDao.findAll();

        model.addAttribute("vehicles", vehicles);

        return "vehicles";
    }

    @PostMapping("/editVehicle")
    public String editVehicle(Vehicle vehicle, Model model) {

        List<Vehicle> vehicles = vehicleDao.findAll();
        model.addAttribute("vehicles", vehicles);

        vehicle = vehicleDao.findByName(vehicle.getName());
        model.addAttribute("vehicles", vehicle);

        return "vehicles";
    }

    @PostMapping("/saveVehicle")
    public String saveVehicle(Model model, Vehicle vehicle) {

        vehicleService.addVehicle(vehicle);

        return "redirect:/vehicles";
    }
}
