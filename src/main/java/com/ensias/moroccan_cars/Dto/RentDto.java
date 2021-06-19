package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.Rent;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.models.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    public RentDto(Rent rent){
        this(rent.getId(),rent.getDateStart(),rent.getDateEnd(),rent.getUser().getId(),rent.getVehicule().getId());
    }

    private int id;

    private Date dateStart;

    private Date DateEnd;

    private int user_id;

    private int vehicule_id;
}
