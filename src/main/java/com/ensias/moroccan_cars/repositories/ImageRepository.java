package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Image;
import com.ensias.moroccan_cars.models.Vehicule;
import org.aspectj.bridge.IMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {
    List<Image> findByVehicule(Vehicule v);
}
