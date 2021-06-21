package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.User;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findUserById(int id);
    User findUserByEmail(String email);

    //@Query("SELECT u from User AS u WHERE ((u.firstName like concat('%',?1,'%') or u.firstName is NULL or u.lastName like concat('%',?2,'%') or u.lastName is NULL or u.email like concat('%',?3,'%') or u.email is NULL) and (u.authority.id = ?4 or ?4 = 0))")
    @Query("SELECT u from User AS u WHERE u.authority.id = ?1 or ?1 = 0")
    List<User> findUsersByValues( int authority);

    @Override
    List<User> findAll();
}
