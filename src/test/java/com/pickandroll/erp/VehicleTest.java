package com.pickandroll.erp;

import com.pickandroll.erp.model.Vehicle;
import com.pickandroll.erp.service.VehicleServiceInterface;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Xavi
 */
@SpringBootTest
public class VehicleTest {

    @Autowired
    private VehicleServiceInterface vehicleService;
            
    Vehicle vehicle;
    
    //Creem vehicle amb el qual testejar diferents mètodes
    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(100L);
        vehicle.setName("Xiaomi 3S");
        vehicle.setType("Bicicleta");
        vehicle.setDescription("Descripcio de prova");
        vehicle.setPrice(7.0);
        vehicle.setEnabled(true);
    }

    @Test
    @Order(1)
    public void addVehicle() throws Exception {
        vehicleService.addVehicle(vehicle);
        assertEquals("Xiaomi 3S", vehicleService.findbyName("Xiaomi 3S").getName());
    }
    
    @Test
    @Order(2)
    public void findVehicleByName() throws Exception {
        assertEquals("Xiaomi 3S", vehicleService.findbyName("Xiaomi 3S").getName());
    }

    @Test
    @Order(3)
    public void deleteVehicle() throws Exception {
        vehicleService.deleteVehicle(vehicleService.findbyName("Xiaomi 3S"));
        assertEquals(null, vehicleService.findbyName("Xiaomi 3S"));
    }
}
