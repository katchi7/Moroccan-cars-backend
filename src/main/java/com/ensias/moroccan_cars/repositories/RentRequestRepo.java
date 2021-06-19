package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.models.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRequestRepo extends CrudRepository<RentRequest, Integer> {
    @Query("SELECT s from Status s where s.id = ?1")
    Status findStatusById(int status_id);
}
