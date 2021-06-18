package com.ensias.moroccan_cars.models;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor

@Entity
@Table(name = "vehicule")
public class Vehicule {

    public Vehicule(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicule_id")
    private int id;

    @Column(name = "vehicule_owner")
    private String owner;

    @Column(name = "vehicule_model")
    private String model;

    @Column(name = "vehicule_year")
    private String year;

    @Column(name = "vehicule_fuel")
    private String fuel;

    @Column(name = "vehicule_transmision")
    private String transmision;

    @Column(name = "vehicule_engine")
    private String engine;

    @Column(name = "vehicule_power")
    private String power;

    @Column(name = "vehicule_seats")
    private int seats;

    @Column(name = "vehicule_price")
    private float price;


    @Column(name = "vehicule_quantity")
    private int quantity;



    @OneToMany(targetEntity = Image.class,mappedBy = "vehicule",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;
}
