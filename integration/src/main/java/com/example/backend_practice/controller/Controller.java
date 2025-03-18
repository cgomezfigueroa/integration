package com.example.backend_practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/practice")
    public String getMessage() {
        String message = "Holi del controller";
        System.out.println("Message: " + message);
        return message;
    }
}

