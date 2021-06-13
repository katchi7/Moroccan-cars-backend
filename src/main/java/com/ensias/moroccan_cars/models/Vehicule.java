package com.ensias.moroccan_cars.models;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private int id;

    @Column(name = "vehicule_owner")
    private String owner;

    @Column(name = "vehicule_model")
    private String model;

    @Column(name = "vehicule_year")
    private String year;

    @Column(name = "vehicule_price")
    private float price;

    @Column(name = "vehicule_description")
    private String description;

    @Column(name = "vehicule_quantity")
    private int quantity;

    @OneToOne(targetEntity = Image.class)
    @JoinColumn(name = "vehicule_default_image")
    private Image defaultImage;

    @OneToMany(targetEntity = Image.class)
    @JoinColumn(name = "image_vehicule")
    private List<Image> images;
}
