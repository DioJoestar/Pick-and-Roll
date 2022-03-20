package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.VehicleDAO;
import com.pickandroll.erp.model.Vehicle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xavi
 */
@Service
public class VehicleService implements VehicleServiceInterface {

    @Autowired
    private VehicleDAO vehicleDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> listVehicles() {
        return (List<Vehicle>) vehicleDao.findAll();
    }

    @Override
    @Transactional
    public void addVehicle(Vehicle vehicle) {
        this.vehicleDao.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(Vehicle vehicle) {
        this.vehicleDao.delete(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle findVehicle(Vehicle vehicle) {
        return this.vehicleDao.findById(vehicle.getId()).orElse(null);
    }
}
