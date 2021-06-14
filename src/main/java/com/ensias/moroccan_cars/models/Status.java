package com.ensias.moroccan_cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int id;
    @Column(name = "status_name")
    private String name;
    @Column(name = "status_description")
    private String description;
}
