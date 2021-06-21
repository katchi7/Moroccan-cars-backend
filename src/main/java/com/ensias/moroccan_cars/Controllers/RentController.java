package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.RentDto;
import com.ensias.moroccan_cars.Dto.RentRequestDto;
import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.models.Rent;
import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.models.Status;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.services.RentService;
import com.ensias.moroccan_cars.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentService rentService;
    private final UserService userService;

    public RentController(RentService rentService, UserService userService) {
        this.rentService = rentService;
        this.userService = userService;
    }

    @PostMapping("")
    public HttpEntity<RentRequestDto> createRenRequest(@Valid @RequestBody RentRequestDto rentRequest , Errors errors) throws ParseException {
        if(errors.hasErrors()){ throw new RequestRejectedException("Not a valid body");}
        rentRequest.setDate(new Date());
        log.info(rentRequest);
        rentRequest.setUser(new UserDto(userService.getCurrentUser()));
        Status status = new Status();
        status.setId(1);
        rentRequest.setStatus(status);
        rentRequest = new RentRequestDto(rentService.createRentRequest(rentRequest.asRentRequest()));
        return ResponseEntity.ok(rentRequest);
    }

    @GetMapping("/rent-requests")
    public HttpEntity<List<RentRequestDto>> getAllRentRequests(){
        return ResponseEntity.ok(rentService.getAllRentRequests());
    }



    @GetMapping("/user-requests")
    public HttpEntity<List<RentRequestDto>> getUserRentRequests(){
        User u = userService.getCurrentUser();
        return ResponseEntity.ok(rentService.getUserRentRequests(u));
    }

    @PutMapping("/rent-requests/{id}")
    public HttpEntity<RentRequest> treatRequest(@PathVariable("id") int request_id,@RequestParam("status") int status_id){
        rentService.treatRentRequest(request_id,status_id);
        return null;
    }

    @GetMapping("/")
    public HttpEntity<List<RentDto>> getAllRent(){
        List<RentDto> rentDtos = new ArrayList<>();
        List<Rent> rents = rentService.getAllRents();
        for (Rent rent : rents) {
            rentDtos.add(new RentDto(rent));
        }
        return ResponseEntity.ok(rentDtos);
    }

    @ExceptionHandler(RequestRejectedException.class)
    public HttpEntity<String> RequestRejectedExceptionHandler(RequestRejectedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
