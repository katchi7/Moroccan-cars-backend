package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.RentRequestDto;
import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.models.Status;
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
import java.util.Date;

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
        if(rentRequest.validDates()) throw new RequestRejectedException("Dates are nor valid");
        log.info(rentRequest);
        rentRequest.setUser(new UserDto(userService.getCurrentUser()));
        Status status = new Status();
        status.setId(1);
        rentRequest.setStatus(status);
        rentRequest = new RentRequestDto(rentService.createRentRequest(rentRequest.asRentRequest()));
        return ResponseEntity.ok(rentRequest);
    }

    @ExceptionHandler(RequestRejectedException.class)
    public HttpEntity<String> RequestRejectedExceptionHandler(RequestRejectedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
