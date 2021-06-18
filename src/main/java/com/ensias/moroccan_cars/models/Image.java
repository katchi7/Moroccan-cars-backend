package com.ensias.moroccan_cars.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Image{
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

    @JsonBackReference
    @ManyToOne(targetEntity = Vehicule.class,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "image_vehicule")
    Vehicule vehicule;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", order=" + order +
                '}';
    }
}
