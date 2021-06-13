package com.ensias.moroccan_cars.models;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne(targetEntity = Authorities.class)
    @JoinColumn(name = "user_authority")
    private Authorities authority;
}
