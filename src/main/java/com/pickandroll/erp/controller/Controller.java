package com.pickandroll.erp.controller;

import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.UserServiceInterface;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {
    
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private VehicleService vehicleService;

    private List<Vehicle> vehicles;

    // Raiz
    @GetMapping("/")
    public String root(Vehicle vehicle, Model model, Authentication auth) {

        if (isAuthenticated()) {

            // Coger los datos de la sessión
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            // Crear el objeto User a partir del email (username) de la sesión actual
            User currUser = userService.findByEmail(userDetails.getUsername());
            
            // Si el usuario es admin redirigir a Modules
            if (currUser.isAdmin()) {
                return "redirect:/modules";
            }

            return "redirect:/vehicles";
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
        
        // Si no se ha iniciado sesión
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            // Si ha ha intentado pero las credenciales no son válidas
            if (ex != null) {
                Utils u = new Utils();
                msg.addFlashAttribute("error", u.alert("login.error.invalidLogin"));
            }
        }
        return "redirect:/login";
    }

    // Terminos
    @GetMapping("/terms")
    public String terms(Model model) {

        return "terms";
    }

    // Privacidad
    @GetMapping("/privacy")
    public String privacy(Model model) {
        return "privacy";
    }

    // Devuelve un booleano si se ha iniciado sesión correctamente
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
