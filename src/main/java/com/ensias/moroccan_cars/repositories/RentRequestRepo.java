package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.models.Status;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.models.Vehicule;
import org.apache.coyote.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRequestRepo extends CrudRepository<RentRequest, Integer> {
    @Query("SELECT s from Status s where s.id = ?1")
    Status findStatusById(int status_id);

    @Query("select u.email from User as u where u.authority.id>=2")
    List<String> getAllRespEmails();

    @Override
    List<RentRequest> findAll();

    List<RentRequest> findRentRequestByUser(User u);

    @Query("select r.vehicule from RentRequest as r where r = ?1 ")
    Vehicule findVehiculeByRequest(RentRequest request);
    @Query("SELECT s from Status as s")
    List<Status> findAllStatus();
}
