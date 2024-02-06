package com.example.springbootclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringController {

    @GetMapping("/user-service")
    public String springController() {
        return "user service response 1";
    }
}
