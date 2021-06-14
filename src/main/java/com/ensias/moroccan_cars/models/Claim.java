package com.ensias.moroccan_cars.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private int id;
    @Column(name = "claim_subject")
    private String subject;
    @Column(name = "claim_body")
    private String body;
    @Column(name = "claim_date")
    private Date date;
    @Column(name = "claim_treatement_date")
    private Date treatmentDate;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "claim_user")
    private User user;
}
