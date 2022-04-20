package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Vehicle;
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
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String vehicles(Vehicle vehicle, Model model) {

        //Llistar tots els vehicles
        List<Vehicle> vehicles = vehicleService.listVehicles();

        //Afegir els vehicles a l'html
        model.addAttribute("vehicles", vehicles);

        return "vehicles";
    }

    //Mètode per editar vehicle
    @PostMapping("/editVehicle")
    public String editVehicle(@ModelAttribute Vehicle vehicle, Model model) {

        List<Vehicle> vehicles = vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicles);

        // Cargar el vehiculo seleccionado en el formulario
        vehicle = vehicleService.findById(vehicle.getId());

        model.addAttribute("vehicle", vehicle);
        return "vehicles";
    }

    @PostMapping("/saveVehicle")
    public String saveData(@Valid Vehicle vehicle, Errors errors, Model model, RedirectAttributes msg, @RequestParam("image") MultipartFile file) throws IOException, InterruptedException {

        // Control de errores
        // MultipartFile siempre da error aunque fundione
//        if (errors.hasErrors()) {
//            List<Vehicle> vehicles = vehicleService.listVehicles();
//            model.addAttribute("vehicles", vehicles);
//            return "vehicles";
//        }
        
        // Subir la imagen al servidor si el input no está vacío
        if (!file.getOriginalFilename().isBlank()) {
            long id = vehicle.getId();
            
            // Si se crea un nuevo vehículo, se cambiara la id de 0 al último
            if (id == 0) {
                id = vehicleService.listVehicles().size();
            }
            
            String fileName = StringUtils.cleanPath(id + "_thumbnail.png");   
            try {
                String uploadDir = "src/main/resources/static/img/vehicles";
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                Thread.sleep(3000); // Delay para que le de tiempo a subir la imagen
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // Guardar los cambios en la DDBB
        vehicleService.addVehicle(vehicle);

        Utils u = new Utils();
        msg.addFlashAttribute("success", u.alert("profile.success"));

        return "redirect:/vehicles";
    }
}
