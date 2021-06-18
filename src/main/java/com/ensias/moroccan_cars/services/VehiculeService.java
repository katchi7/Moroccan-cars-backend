package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.Controllers.CarsController;
import com.ensias.moroccan_cars.Dto.VehiculeFilter;
import com.ensias.moroccan_cars.models.Image;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.repositories.ImageRepository;
import com.ensias.moroccan_cars.repositories.VehiculeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@Service
public class VehiculeService {

    public final VehiculeRepository vehiculeRepository;
    public final StorageService storageService;
    private final ImageRepository imageRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository, StorageService storageService, ImageRepository imageRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.storageService = storageService;
        this.imageRepository = imageRepository;
    }

    public List<Vehicule> getAllVehicules(){
        return vehiculeRepository.findAll();
    }
    public Vehicule findById(int id){
        Optional<Vehicule> op = vehiculeRepository.findById(id);
        if(op.isPresent()){
            return op.get();
        }else {
            return null;
        }
    }
    public List<String> findMakes(){
        List<String> makes = vehiculeRepository.findAllMakes();
        return makes;
    }
    public List<String> findFuel(){
        return vehiculeRepository.findAllFuel();
    }
    public List<String> findTransmisions(){
        return vehiculeRepository.findAllTransmisions();
    }
    public List<Vehicule> findByFilter(VehiculeFilter filter){
        List<Vehicule> vehicules = null;
        int minYears = 0;
        int maxYears = 0;
        try {
            maxYears = Integer.parseInt(filter.getMaxYear());

        }catch (Exception ignore){ }
        try {
            minYears = Integer.parseInt(filter.getMinYear());

        }catch (Exception ignore){ }
        vehicules = vehiculeRepository.findByFilter(filter.getOwner(),filter.getModel(),filter.getFuel(),filter.getTransmision(),filter.getMinSeats(),filter.getMaxSeats(),minYears,maxYears);
        return  vehicules;
    }

    public List<Integer> findSeats(){
        return vehiculeRepository.findAllSeats();
    }

    public Vehicule save(Vehicule v){
        v = vehiculeRepository.save(v);
        return v;
    }

    public List<Image> saveImages(List<MultipartFile> files, int id) throws IOException {
        Optional<Vehicule> vehiculeOp = vehiculeRepository.findById(id);
        ArrayList<Image> return_images = null;
        log.info(files.size());
        if(vehiculeOp.isPresent()){
            return_images = new ArrayList<>();
            Vehicule v = vehiculeOp.get();
            int i=1;
            for (MultipartFile file : files) {

                Image im = new Image(0,v.getOwner()+" "+v.getModel(),"".toString(),i,v);
                im = imageRepository.save(im);
                storageService.store(file,""+im.getId());
                im.setLink(linkTo(methodOn(CarsController.class).serveFile(im.getId())).toString());
                im = imageRepository.save(im);
                i++;
                return_images.add(im);
            }
        }

        return return_images;
    }
    public Vehicule deleteVehicule(int id){
        try {
            Vehicule v = vehiculeRepository.findById(id).get();
            log.info("Here");
            vehiculeRepository.delete(v);
            return v;
        }catch (NoSuchElementException ignore){
            return null;
        }
    }
    public Resource findImage(int image_id){
        return storageService.loadResource(image_id);
    }
}
