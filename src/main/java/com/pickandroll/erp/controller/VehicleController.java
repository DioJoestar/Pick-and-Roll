package com.pickandroll.erp.Controller;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Vehicle;
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
public class VehicleController {

    @Autowired
    private VehicleDAO vehicleDao;

    @GetMapping("/vehicles")
    public String root(Model model) {

        //List<Vehicle> vehicles = vehicleDao.findAll();
        //model.addAttribute("vehicles", vehicles);

        return "vehicles";
    }
}
