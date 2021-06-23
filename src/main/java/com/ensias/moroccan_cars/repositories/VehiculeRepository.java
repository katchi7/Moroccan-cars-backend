package com.ensias.moroccan_cars.repositories;

import com.ensias.moroccan_cars.models.Vehicule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.SqlResultSetMapping;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface VehiculeRepository extends CrudRepository<Vehicule,Integer> {
    List<Vehicule> findAll();
    @Query("select distinct v.owner from Vehicule as v")
    List<String> findAllMakes();
    @Query("SELECT distinct v.transmision from Vehicule as v")
    List<String> findAllTransmisions();
    @Query("SELECT distinct v.fuel from Vehicule as v")
    List<String> findAllFuel();
    @Query("SELECT distinct v.seats from Vehicule as v")
    List<Integer> findAllSeats();


    @Query("SELECT v from Vehicule as v WHERE (v.owner LIKE concat('%',?1,'%') or ?1 is null) and (v.model like concat('%',?2,'%') or ?2 is null) " +
            "and (v.fuel like concat('%',?3,'%') or ?3 is null) and (v.transmision like concat('%',?4,'%') or ?4 is null)" +
            "and (v.seats >= ?5 or ?5 = 0) and (v.seats <= ?6 or ?6 = 0)"+
            "and (function('MYCONVERT', v.year) >= ?7 or ?7 = 0) and ( function('MYCONVERT', v.year) <= ?8 or ?8 = 0)"
    )
    List<Vehicule> findByFilter(String owner,
                                String model,
                                String fuel,
                                String transmision,
                                int minSeats,
                                int maxSeats,
                                int minYear,
                                int maxYear);

    @Query(value = "DELETE FROM vehicule WHERE vehicule_id = ?1",nativeQuery = true)

    void deleteById(int id);

    @Query(value = "SELECT rent_vehicule as id,Count(rent_id) as nb FROM moroccan_cars.rent where (rent_date_start between ?1 and ?2) or (rent_date_end between ?1 and ?2) GROUP BY rent_vehicule",nativeQuery = true)
    List<Count> findNbRentedVehicule(Date dateStart, Date dateEnd);

    public static interface Count{
        int getId();
        int getNb();
    }
}
