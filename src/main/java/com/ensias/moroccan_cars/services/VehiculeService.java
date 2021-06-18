package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.Dto.VehiculeFilter;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    public final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public List<Vehicule> getAllVehicules(){
        return vehiculeRepository.findAll();
    }
    public Vehicule findById(int id){
        Optional<Vehicule> op = vehiculeRepository.findById(id);
        if(op.isPresent()){
            return op.get();
        }else {
            return null;
        }
    }
    public List<String> findMakes(){
        List<String> makes = vehiculeRepository.findAllMakes();
        return makes;
    }
    public List<String> findFuel(){
        return vehiculeRepository.findAllFuel();
    }
    public List<String> findTransmisions(){
        return vehiculeRepository.findAllTransmisions();
    }
    public List<Vehicule> findByFilter(VehiculeFilter filter){
        List<Vehicule> vehicules = null;
        int minYears = 0;
        int maxYears = 0;
        try {
            maxYears = Integer.parseInt(filter.getMaxYear());

        }catch (Exception ignore){ }
        try {
            minYears = Integer.parseInt(filter.getMinYear());

        }catch (Exception ignore){ }
        vehicules = vehiculeRepository.findByFilter(filter.getOwner(),filter.getModel(),filter.getFuel(),filter.getTransmision(),filter.getMinSeats(),filter.getMaxSeats(),minYears,maxYears);
        return  vehicules;
    }

    public Vehicule save(Vehicule v){
        v = vehiculeRepository.save(v);
        return v;
    }
}
