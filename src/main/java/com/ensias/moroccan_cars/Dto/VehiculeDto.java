package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.Image;
import com.ensias.moroccan_cars.models.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto extends RepresentationModel<VehiculeDto> {

    public VehiculeDto(Vehicule v){
        this(v.getId(),v.getOwner(),v.getModel(),v.getYear(),v.getFuel(),v.getTransmision(),v.getEngine(),v.getPower(),v.getSeats(),v.getPrice(),v.getQuantity(),v.getImages());
    }

    private int id;

    @NotNull
    @Size(min = 3)
    private String owner;

    @NotNull
    @Size(min = 3)
    private String model;

    @NotNull
    @Size(min=4,max = 4)
    private String year;

    @NotNull
    @Size(min = 3)
    private String fuel;

    @NotNull
    @Size(min = 3)
    private String transmision;

    private String engine;

    private String power;

    @Min(1)
    private int seats;

    @Min(50)
    private float price;

    @Min(1)
    private int quantity;

    private List<Image> images;



    public Vehicule asVehicule(){
        return new Vehicule(id,owner,model,year,fuel,transmision,engine,power,seats,price,quantity,images);
    }
}
