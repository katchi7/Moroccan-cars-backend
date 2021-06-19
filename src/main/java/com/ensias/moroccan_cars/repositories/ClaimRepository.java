package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Claim;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends CrudRepository<Claim,Integer>{
    @Query("select u.email from User as u where u.authority.id>=2")
    List<String> getAllRespEmails();

    @Override
    List<Claim> findAll();

    @Query("SELECT c from Claim c where c.user.id = ?1")
    List<Claim> findAllByUser(int user_id);
}
