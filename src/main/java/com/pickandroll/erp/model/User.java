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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Size(min = 9, max = 9)
    private String dni;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @Size(min = 2)
    @NotEmpty
    private String surname;

    @NotEmpty
    @Size(min = 5)
    private String email;

    @NotEmpty
    @Size(min = 9, max = 9)
    private String phone;

    @NotEmpty
    @Size(min = 4)
    private String password;

    @Transient
    @Size(min = 4)
    private String passwordCheck;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList();

    @ManyToMany
    @JoinTable(
            name = "user_cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles = new ArrayList();

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void deleteVehicle(Vehicle vehicle) {

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v2 = vehicles.get(i);
            if (v2.getId() == vehicle.getId()) {
                this.vehicles.remove(i);
            }
        }
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    // Comprueba si el usuario tiene el rol de administrador
    public boolean isAdmin() {
        Role r = new Role();
        r.setName("admin");
        return this.roles.contains(r);
    }
}
