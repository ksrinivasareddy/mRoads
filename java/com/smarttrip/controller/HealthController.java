package com.smarttrip.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private static final Logger logger = LogManager.getLogger(HealthController.class);

    @GetMapping("/health")
    public String checkHealth() {
        logger.info("Health check API called");
        return "SmartTrip Backend is Running!";
    }
}
