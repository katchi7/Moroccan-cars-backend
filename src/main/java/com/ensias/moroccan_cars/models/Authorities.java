package com.ensias.moroccan_cars.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private int id;
    @Column(name = "authority_name")
    private String name;

    public String getName() {
        return name;
    }
}
