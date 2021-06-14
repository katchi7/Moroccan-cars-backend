package com.ensias.moroccan_cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;
    @Column(name = "image_description")
    private String description;
    @Column(name = "image_link")
    private String link;
}
