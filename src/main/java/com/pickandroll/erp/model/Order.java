package com.pickandroll.erp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Xavi
 */
@Data
@Entity
@Table(name = "product_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "rent_days")
    private int rentDays;

    @NotEmpty
    @Column(name = "start_date")
    private String startDate;

    @Column(name = "picked_date")
    private String pickedDate;
    
    @Column(name = "returned_date")
    private String returnedDate;
    
    @NotNull
    @Column(name = "total_price")
    private double totalPrice;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @ManyToMany
    @JoinTable(
            name = "orders_vehicles",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles = new ArrayList();
}
