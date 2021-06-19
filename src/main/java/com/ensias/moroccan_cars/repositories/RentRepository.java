package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Rent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentRepository extends CrudRepository<Rent,Integer> {
    @Override
    List<Rent> findAll();
}
