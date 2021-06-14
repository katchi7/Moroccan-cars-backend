package com.ensias.moroccan_cars.models;

import lombok.AllArgsConstructor;

import lombok.Data;


import javax.persistence.*;


@Data
@AllArgsConstructor

@Entity
@Table(name = "user")
public class User {
    public User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_first_name")
    private String firstName;
    @Column(name = "user_last_name")
    private String lastName;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_adr")
    private String adr;
    @Column(name = "user_num")
    private String num;
    @Column(name = "user_image")
    private String image;
    @Column(name = "user_password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "user_authority")
    private Authorities authority;
}
