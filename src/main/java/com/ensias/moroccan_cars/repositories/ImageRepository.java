package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Image;
import org.aspectj.bridge.IMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {
}
