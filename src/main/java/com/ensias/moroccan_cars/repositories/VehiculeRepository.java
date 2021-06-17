package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Vehicule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepository extends CrudRepository<Vehicule,Integer> {
    List<Vehicule> findAll();
}
