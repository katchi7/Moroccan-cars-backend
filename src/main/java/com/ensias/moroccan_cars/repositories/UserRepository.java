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

    @Query("SELECT u from User AS u WHERE ((u.firstName like concat('%',?1,'%') or u.lastName like concat('%',?2,'%') or u.email like concat('%',?3,'%')) and (u.authority.id = ?4 or ?4 = 0))")
    List<User> findUsersByValues(String firstName,String lastName,String email, int authority);
}
