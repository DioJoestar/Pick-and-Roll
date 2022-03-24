package com.pickandroll.erp.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author santialves
 */
@Data
public class Cart {

    private double priceU = 0;
    private double subPrice = 0;
    private double IVA = 21;
    private double totalPrice = 0;
    private int days = 1;

    public void setPriceU(List<Vehicle> vehicles) {
        double priceU = 0;
        for (int i = 0; i < vehicles.size(); i++) {
            priceU += vehicles.get(i).getPrice();
        }

        this.priceU = priceU;
    }

    public void setSubPrice() {

        double subPrice = 0;

        subPrice = (priceU * days) * (1d - (IVA / 100));

        this.subPrice = subPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = (priceU * days);
    }

    public void removeVehicles(Vehicle v, Cart cart, List<Vehicle> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v2 = vehicles.get(i);
            if (v2.getId() == v.getId()) {
                vehicles.remove(i);
                cart.setPriceU(vehicles);
            }
        }
    }
    
}
