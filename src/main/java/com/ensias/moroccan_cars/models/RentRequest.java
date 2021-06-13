package com.ensias.moroccan_cars.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "rent_request")
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "rent_request_date")
    private Date date;
    @Column(name = "rent_request_date_start")
    private Date dateStart;
    @Column(name = "rent_request_date_end")
    private Date dateEnd;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "rent_request_user")
    private User user;

    @ManyToOne(targetEntity = Vehicule.class)
    @JoinColumn(name = "rent_request_vehicule")
    private Vehicule vehicule;
    @ManyToOne(targetEntity = Status.class)
    @JoinColumn(name = "rent_request_status")
    private Status status;
}
