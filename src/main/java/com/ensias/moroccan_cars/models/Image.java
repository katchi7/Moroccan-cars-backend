package com.ensias.moroccan_cars.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "image")
public class Image extends RepresentationModel<Image> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;
    @Column(name = "image_description")
    private String description;
    @Column(name = "image_link")
    private String link;
    @Column(name ="image_order")
    private int order;

    @JsonIgnore
    @ManyToOne(targetEntity = Vehicule.class)
    @JoinColumn(name = "image_vehicule")
    Vehicule vehicule;
}
