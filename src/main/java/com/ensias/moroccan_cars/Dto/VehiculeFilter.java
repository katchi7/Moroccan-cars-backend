package com.ensias.moroccan_cars.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeFilter {
    private String owner;

    private String model;

    private String minYear;

    private String maxYear;

    private String fuel;

    private String transmision;

    private int minSeats;

    private int maxSeats;

    private float price;
}
