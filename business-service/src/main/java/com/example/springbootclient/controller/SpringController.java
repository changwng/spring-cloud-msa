package com.example.springbootclient.controller;

import com.example.springbootclient.support.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SpringController {
    @GetMapping("/hello-world")
    public ResponseEntity<Map<String,Object>> helloWorld(
        HttpServletRequest httpServletRequest
    ) {
        Map <String,Object> responseMap = new HashMap<>();
        String message = "hello";
        String code = "ok";
        HttpStatus status = HttpStatus.OK;

        responseMap.put("header", ResponseUtils.setHeader(message, code, httpServletRequest));
        return new ResponseEntity<>(responseMap, status);
    }
}
