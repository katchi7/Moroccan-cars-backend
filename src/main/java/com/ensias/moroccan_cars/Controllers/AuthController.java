package com.ensias.moroccan_cars.Controllers;


import com.ensias.moroccan_cars.Dto.UserDto;
import com.ensias.moroccan_cars.config.JwtUtil;
import com.ensias.moroccan_cars.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*")
public class AuthController {

    @Value("${jwt.hdr}")
    public static String TOKEN_HEADER;

    public final Logger logger;
    public final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.logger = LoggerFactory.getLogger(getClass().getName());
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public HttpEntity<UserDto> createUser(@Valid @RequestBody UserDto user, Errors errors){

        if(errors.hasErrors()){
            logger.info("error " +user);
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }

        user = new UserDto(userService.CreateUser(user.User()));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public HttpEntity<UserDto> getUser(){

        return ResponseEntity.ok().body(new UserDto(userService.getCurrentUser()));
    }

    @PostMapping(value = "/login")
    public HttpEntity<UserDto> login(@Valid @RequestBody UserDto userDto,Errors errors){
        if(errors.hasFieldErrors("email") || errors.hasFieldErrors("password")) {
            return new ResponseEntity<>(userDto, HttpStatus.BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));

            userDto = new UserDto(userService.findUserByEmail(userDto.getEmail()));
            ResponseEntity<UserDto> response = ResponseEntity.ok().header(TOKEN_HEADER,jwtUtil.generateToken(userDto.getEmail())).body(userDto);
            return response;
        }catch (org.springframework.security.core.AuthenticationException e){
            return new ResponseEntity<>(userDto, HttpStatus.NOT_FOUND);
        }

    }
}
