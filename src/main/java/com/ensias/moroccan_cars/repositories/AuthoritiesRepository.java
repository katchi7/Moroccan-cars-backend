package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Integer> {
}
