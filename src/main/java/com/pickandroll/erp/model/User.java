package com.pickandroll.erp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotEmpty
    private String dni;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String surname;
    
    @NotEmpty
    private String email;
    
    @NotEmpty
    private String phone;

    @NotEmpty
    private String password;

    @OneToMany 
    @JoinColumn(name = "id")
    private List<Role> roles;
}
