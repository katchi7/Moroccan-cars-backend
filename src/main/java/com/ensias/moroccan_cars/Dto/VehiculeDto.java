package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.Image;
import com.ensias.moroccan_cars.models.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto {

    public VehiculeDto(Vehicule v){
        this(v.getId(),v.getOwner(),v.getModel(),v.getYear(),v.getFuel(),v.getTransmision(),v.getEngine(),v.getPower(),v.getSeats(),v.getPrice(),v.getQuantity(),v.getImages());
    }


    private int id;

    private String owner;

    private String model;

    private String year;

    private String fuel;

    private String transmision;

    private String engine;

    private String power;

    private int seats;

    private float price;

    private int quantity;

    private List<Image> images;



    public Vehicule asVehicule(){
        return new Vehicule(id,owner,model,year,fuel,transmision,engine,power,seats,price,quantity,images);
    }
}
