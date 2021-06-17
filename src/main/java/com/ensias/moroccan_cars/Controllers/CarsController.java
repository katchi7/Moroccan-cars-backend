package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.VehiculeDto;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.services.VehiculeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsController {

    private final VehiculeService vehiculeService;

    public CarsController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping("")
    public HttpEntity<List<VehiculeDto>> getVehicules(){

        List<VehiculeDto> vehiculeDtos = new ArrayList<>();
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        for (Vehicule vehicule : vehicules) {
            vehiculeDtos.add(new VehiculeDto(vehicule));
        }
        return ResponseEntity.ok().body(vehiculeDtos);

    }
}
