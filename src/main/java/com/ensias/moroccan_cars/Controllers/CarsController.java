package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.VehiculeDto;
import com.ensias.moroccan_cars.Dto.VehiculeFilter;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.services.VehiculeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/cars")
public class CarsController {

    private final VehiculeService vehiculeService;

    public CarsController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping("")
    public HttpEntity<List<VehiculeDto>> getVehicules(@RequestBody(required = false) VehiculeFilter v){

        if(v==null){
            List<VehiculeDto> vehiculeDtos = new ArrayList<>();
            List<Vehicule> vehicules = vehiculeService.getAllVehicules();
            for (Vehicule vehicule : vehicules) {
                VehiculeDto vehiculeDto = new VehiculeDto(vehicule);
                vehiculeDto.add(linkTo(methodOn(CarsController.class).getVehiculeById(vehiculeDto.getId())).withSelfRel());
                vehiculeDtos.add(vehiculeDto);
            }
            return ResponseEntity.ok().body(vehiculeDtos);
        }
        List<Vehicule> vehicules = vehiculeService.findByFilter(v);
        List<VehiculeDto> vehiculeDtos = new ArrayList<>();

        for (Vehicule vehicule : vehicules) {
            VehiculeDto vehiculeDto = new VehiculeDto(vehicule);
            vehiculeDto.add(linkTo(methodOn(CarsController.class).getVehiculeById(vehiculeDto.getId())).withSelfRel());
            vehiculeDtos.add(vehiculeDto);
        }
        return ResponseEntity.ok(vehiculeDtos);
    }
    @GetMapping("/{id}")
    public HttpEntity<VehiculeDto> getVehiculeById(@PathVariable("id") int vehicule_id){

        Vehicule v = vehiculeService.findById(vehicule_id);
        if(v == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VehiculeDto(v));
    }
    @GetMapping("/makes")
    public HttpEntity<List<String>> getMakes(){
        return ResponseEntity.ok(vehiculeService.findMakes());
    }
    @GetMapping("/transmisions")
    public HttpEntity<List<String>> getTransmisions(){
        return ResponseEntity.ok(vehiculeService.findTransmisions());
    }
    @GetMapping("/fuel")
    public HttpEntity<List<String>> getfuel(){
        return ResponseEntity.ok(vehiculeService.findFuel());
    }

    @PostMapping("")
    public HttpEntity<VehiculeDto> createVehicule(@Valid @RequestBody VehiculeDto vehiculeDto, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        vehiculeDto = new VehiculeDto(vehiculeService.save(vehiculeDto.asVehicule()));
        vehiculeDto.add(linkTo(methodOn(CarsController.class).getVehiculeById(vehiculeDto.getId())).withSelfRel());
        return ResponseEntity.ok(vehiculeDto);


    }
}
