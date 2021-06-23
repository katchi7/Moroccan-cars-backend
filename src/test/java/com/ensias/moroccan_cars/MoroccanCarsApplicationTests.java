package com.ensias.moroccan_cars;

import com.ensias.moroccan_cars.Dto.VehiculeDto;
import com.ensias.moroccan_cars.models.Authorities;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.repositories.UserRepository;
import com.ensias.moroccan_cars.services.VehiculeService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
class MoroccanCarsApplicationTests {

	@Autowired
	VehiculeService vehiculeService;
	@Test
	void contextLoads() {
		List<VehiculeDto> vehicules = vehiculeService.getDisponible("2021-6-1","2021-7-1");
		log.info(vehicules);
	}

}
