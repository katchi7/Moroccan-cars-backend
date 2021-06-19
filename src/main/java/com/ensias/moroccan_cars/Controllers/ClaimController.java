package com.ensias.moroccan_cars.Controllers;

import com.ensias.moroccan_cars.Dto.ClaimDto;
import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.models.Claim;
import com.ensias.moroccan_cars.services.ClaimService;
import com.ensias.moroccan_cars.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2

@RestController
@RequestMapping("/claim")
public class ClaimController {
    private final UserService userService;
    private final ClaimService claimService;

    public ClaimController(UserService userService, ClaimService claimService) {
        this.userService = userService;
        this.claimService = claimService;
    }

    @PostMapping("/create")
    public HttpEntity<ClaimDto> createClaim(@Valid @RequestBody(required = false) ClaimDto claimDto, Errors errors) throws MessagingException {
        if(claimDto == null) throw new RequestRejectedException("Request body not provided");
        if(errors.hasErrors()){ log.info(errors.getAllErrors()); throw new RequestRejectedException("Request data not valid");}
        log.info(claimDto);
        claimDto.setDate(new Date());
        claimDto.setUser(new UserDto(userService.getCurrentUser()));
        claimDto = new ClaimDto(claimService.saveClaim(claimDto.asClaim()));
        return ResponseEntity.ok(claimDto);

    }
    @GetMapping("")
    public HttpEntity<List<ClaimDto>> getClaims(@RequestParam(value = "user",defaultValue= "") Integer user_id){
        List<ClaimDto> claimDtos;
        if(user_id == null) claimDtos = claimService.findAllClaims();
        else claimDtos = claimService.findClaimByUser(user_id);
        return ResponseEntity.ok(claimDtos);
    }
    @PutMapping("/{id}")
    HttpEntity<ClaimDto> treatClaim(@RequestParam(value = "treated",defaultValue = "true") Boolean treated,@PathVariable("id") int claim_id){

        claimService.treatClaim(treated,claim_id);
        return ResponseEntity.ok().build();

    }
    @ExceptionHandler(RequestRejectedException.class)
    public HttpEntity<String> HttpMessageNotReadableExceptionHandler(RequestRejectedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(NoSuchElementException.class)
    public HttpEntity<String> NoSuchElementExceptionHandler(){
        return ResponseEntity.notFound().build();
    }
}
