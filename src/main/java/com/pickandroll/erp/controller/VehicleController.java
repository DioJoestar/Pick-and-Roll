package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.utils.FileUploadUtil;
import com.pickandroll.erp.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehicleController {

    public List<Vehicle> vehicles = new ArrayList<Vehicle>();

    @Autowired
    private VehicleDAO vehicleDao;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String users(Vehicle vehicle, Model model) {

        List<Vehicle> vehicles = vehicleDao.findAll();
        model.addAttribute("vehicles", vehicles);

        return "vehicles";
    }

    @PostMapping("/editVehicle")
    public String editVehicle(@ModelAttribute Vehicle vehicle, Model model) {
        List<Vehicle> vehicles = vehicleDao.findAll();
        model.addAttribute("vehicles", vehicles);

        vehicle = vehicleDao.findByName(vehicle.getName());
        model.addAttribute("vehicle", vehicle);
        return "vehicles";
    }

    @PostMapping("/saveVehicle")
    public String saveData(@Valid Vehicle vehicle, Errors errors, Model model, RedirectAttributes msg, @RequestParam("image_path") MultipartFile multipartFile) throws IOException, InterruptedException {
        Utils u = new Utils();

//        if (errors.hasErrors()) {
//            List<Vehicle> vehicles = vehicleDao.findAll();
//            model.addAttribute("vehicles", vehicles);
//            return "vehicles";
//        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        vehicle.setImage_path("/img/" + fileName);

        if (!isCorrect(vehicle)) {
            List<Vehicle> vehicles = vehicleDao.findAll();
            model.addAttribute("vehicles", vehicles);
            return "vehicles";
        }

        vehicleService.addVehicle(vehicle);

        String uploadDir = "src\\main\\resources\\static\\img";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        msg.addFlashAttribute("success", u.alert("profile.success"));

        Thread.sleep(3000);
        return "redirect:/vehicles";
    }


    public boolean isCorrect(Vehicle vehicle) {
        String[] image = vehicle.getImage_path().split("/");

        if (vehicle.getDescription().isBlank() || vehicle.getName().isBlank() || vehicle.getPrice() < 0 || vehicle.getType().isBlank() || image.length < 3) {
            return false;
        }
        return true;
    }
}
