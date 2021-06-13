package com.ensias.moroccan_cars.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "rent_date_start")
    private Date dateStart;
    @Column(name = "rent_date_end")
    private Date DateEnd;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "rent_user")
    private User user;
    @ManyToOne(targetEntity = Vehicule.class)
    @JoinColumn(name = "rent_vehicule")
    private Vehicule vehicule;
}
