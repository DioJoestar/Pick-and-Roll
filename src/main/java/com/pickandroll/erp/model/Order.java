package com.pickandroll.erp.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author Xavi
 */
@Data
@Entity
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private int rentDays;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private boolean picked;

    @NotEmpty
    private boolean returned;

    @NotEmpty
    private int user_id;
}
