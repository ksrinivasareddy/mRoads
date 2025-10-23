package com.smarttrip.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "✅ SmartTrip API is running successfully!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from SmartTrip!";
    }
}
