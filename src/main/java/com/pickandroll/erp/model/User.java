package com.pickandroll.erp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
