package com.ensias.moroccan_cars.Controllers;


import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    public final Logger logger;
    public final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(getClass().getName());
    }

    @RequestMapping(value = "user",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public HttpEntity<UserDto> createUser(@Valid @RequestBody UserDto user, Errors errors){

        if(errors.hasErrors()){
            logger.info("error " +user);
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }

        user = new UserDto(userService.CreateUser(user.User()));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
