package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.VehicleService;
import com.pickandroll.erp.utils.Utils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {
    
    @Autowired
    private VehicleService vehicleService;
    
    private List<Vehicle> vehicles;

    // Raiz
    @GetMapping("/")
    public String root(Vehicle vehicle, Model model) {

        if (isAuthenticated()) {
            List<Vehicle> vehicles = vehicleService.listVehicles();
            model.addAttribute("vehicles", vehicles);
            
            return "vehicles";
        }

        return "login";
    }

    @PostMapping("/")
    public String addVehicle(Model model, Vehicle vechicle, @RequestParam long token) {

        Vehicle v = vehicleService.findById(token);
        vehicles.add(v);

        return "main";
    }

    @GetMapping("/loginError")
    public String login(HttpServletRequest request, RedirectAttributes msg) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                Utils u = new Utils();
                msg.addFlashAttribute("error", u.alert("login.error.invalidLogin"));
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/terms")
    public String terms(Model model) {

        return "terms";
    }

    @GetMapping("/privacy")
    public String privacy(Model model) {
        return "privacy";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
