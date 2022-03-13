package com.pickandroll.erp.model;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "Vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "No has introduït el nom")
    @Size(min = 5, message="El nom mínim ha de ser de 5 caràcters")
    private String name;


}