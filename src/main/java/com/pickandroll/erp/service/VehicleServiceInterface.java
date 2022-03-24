package com.pickandroll.erp.service;

import com.pickandroll.erp.model.Vehicle;
import java.util.List;

/**
 *
 * @author Xavi
 */
public interface VehicleServiceInterface {

    public List<Vehicle> listVehicles();

    public void addVehicle(Vehicle vehicle);

    public void deleteVehicle(Vehicle vehicle);

    public Vehicle findbyName(String name);
    
    public Vehicle findVehicle(Vehicle vehicle);

}
