package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.VehiculeDto;
import com.ensias.moroccan_cars.Dto.VehiculeFilter;
import com.ensias.moroccan_cars.models.Image;
import com.ensias.moroccan_cars.models.Vehicule;
import com.ensias.moroccan_cars.services.VehiculeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/cars")
public class CarsController {
    private static final ArrayList<String> imageMimes = new ArrayList<String>(){{ add("image/png");add("image/bmp");add("image/cis-cod");add("image/gif");add("image/ief");add("image/jpeg");add("image/jpeg");add("image/jpeg");add("image/pipeg");add("image/svg+xml");add("image/tiff");add("image/tiff");add("image/x-cmu-raster");add("image/x-cmx");add("image/x-icon");add("image/x-portable-anymap");add("image/x-portable-bitmap");add("image/x-portable-graymap");add("image/x-portable-pixmap");add("image/x-rgb");add("image/x-xbitmap");add("image/x-xpixmap");add("image/x-xwindowdump"); }};


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
    @GetMapping("/seats")
    public HttpEntity<List<Integer>> getSeats(){
        return ResponseEntity.ok(vehiculeService.findSeats());
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

    @GetMapping("/images/{image_id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("image_id") int image_id) throws IOException {

        Resource image  = vehiculeService.findImage(image_id);
        String content_type = URLConnection.guessContentTypeFromStream(image.getInputStream());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,content_type).header("X-Frame-Options","ALLOW-FROM").body(image);
    }

    @PostMapping(value = "/{id}/images",consumes = {"multipart/form-data"})
    public HttpEntity<List<Image>> setCarImages(@RequestPart(value = "images",required = false) @Valid @NotNull @NotBlank ArrayList<MultipartFile> files,@PathVariable("id") int vehicule_id) throws IOException {

        log.info("Vehicule : "+vehicule_id);

        ArrayList<MultipartFile> filteredFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if(isImage(file)) filteredFiles.add(file);
        }
        log.info(filteredFiles.size());
        List<Image> images = vehiculeService.saveImages(filteredFiles,vehicule_id);
        if(images == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(images);
    }

    private boolean isImage(MultipartFile file){

        return imageMimes.contains(file.getContentType());
    }

    @ExceptionHandler(IllegalStateException.class)
    public HttpEntity<String> exceptionHadler(IllegalStateException e){
        return ResponseEntity.badRequest().body("IllegalStateException : Multipart Files not received");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity<String> MethodArgumentTypeMismatchExceptionHandeler(){
        return ResponseEntity.badRequest().body("not a valid Path");
    }
    @ExceptionHandler(IOException.class)
    public HttpEntity<String> IOExceptionHandler(){
        return ResponseEntity.badRequest().body("Storage Error");
    }
}
