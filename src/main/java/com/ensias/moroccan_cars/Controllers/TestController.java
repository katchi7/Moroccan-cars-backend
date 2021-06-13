package com.ensias.moroccan_cars.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/")
    public String getTest(){
        return "Hello";
    }
}
