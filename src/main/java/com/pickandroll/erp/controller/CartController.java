package com.pickandroll.erp.controller;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.Order;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.OrderService;
import com.pickandroll.erp.service.UserServiceInterface;
import com.pickandroll.erp.service.VehicleService;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

    //Crear variables
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private Cart cart = new Cart();
    
    //Crear serveis
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private OrderService orderService;

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

    @PersistenceContext
    private EntityManager entityManager;

    //Finalitzar la comanda
    @Transactional
    @RequestMapping(value = "/close_order")
    public String closeOrder(Authentication auth) {

        //Agafar la data actual
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        //Obtenir l'usuari actual
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = userService.findByEmail(userDetails.getUsername());
        
        //Insertar valors de la comanda
        Order order = new Order();
        order.setRentDays(cart.getDays());
        order.setStartDate(formatter.format(date));
        order.setTotalPrice(cart.getTotalPrice());
        order.setUserId((int)currUser.getId());
        orderService.addOrder(order);
        
        entityManager.joinTransaction();

//        //Insertar valors de la comanda
//        entityManager.createNativeQuery("INSERT INTO pickandroll.product_order (rent_days, start_date, user_id) VALUES (?,?,?)")
//                .setParameter(1, cart.getDays())
//                .setParameter(2, formatter.format(date))
//                .setParameter(3, currUser.getId())
//                .executeUpdate();

        //Obtenir l'id de l'ultima comanda
        var order_id = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM pickandroll.product_order")
                .getResultList().get(0);
        
        //int order_id = (int)order.getId();
        
        //Inserir valors
        for (int i = 0; i < currUser.getVehicles().size(); i++) {
            entityManager.createNativeQuery("INSERT INTO pickandroll.orders_vehicles (order_id, vehicle_id) VALUES (?,?)")
                    .setParameter(1, (int)order_id)
                    .setParameter(2, currUser.getVehicles().get(i))
                    .executeUpdate();
        }

        //Esborrar el cistell
        currUser.deleteAllVehicles();

        return "redirect:/cart";    
    }

}
