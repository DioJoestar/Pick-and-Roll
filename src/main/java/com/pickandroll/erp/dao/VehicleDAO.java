package com.pickandroll.erp.dao;

import com.pickandroll.erp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xavi
 */
public interface VehicleDAO extends JpaRepository<Vehicle, Long> {

    Vehicle findByName(String name);
}
