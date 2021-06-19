package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.repositories.RentRepository;
import com.ensias.moroccan_cars.repositories.RentRequestRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RentService {
    private final RentRepository rentRepository;
    private final RentRequestRepo rentRequestRepo;

    public RentService(RentRepository rentRepository, RentRequestRepo rentRequestRepo) {
        this.rentRepository = rentRepository;
        this.rentRequestRepo = rentRequestRepo;
    }
    public RentRequest createRentRequest(RentRequest rentRequest){
        rentRequest = rentRequestRepo.save(rentRequest);
        rentRequest.setStatus(rentRequestRepo.findStatusById(rentRequest.getStatus().getId()));
        return rentRequest;
    }
}
