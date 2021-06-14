package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findUserById(int id);
    User findUserByEmail(String email);
}
