package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    public final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public List<Vehicule> getAllVehicules(){
        return vehiculeRepository.findAll();
    }
}
