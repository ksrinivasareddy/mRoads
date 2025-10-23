package com.smarttrip.controller;

import com.smarttrip.entity.Trip;
import com.smarttrip.entity.User;
import com.smarttrip.repository.TripRepository;
import com.smarttrip.repository.UserRepository;
import com.smarttrip.service.TripInsightsService;
import com.smarttrip.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripInsightsService tripInsightsService;

    @Autowired
    private ReportService reportService;

    // ✅ Create Trip via Map input (manual)
    @PostMapping("/createManual")
    public Trip createTripManual(@RequestBody Map<String, Object> data) {
        try {
            Long userId = Long.valueOf(data.get("userId").toString());
            String destination = data.get("destination").toString();
            double budget = Double.parseDouble(data.get("budget").toString());
            LocalDate startDate = LocalDate.parse(data.get("startDate").toString());
            LocalDate endDate = LocalDate.parse(data.get("endDate").toString());

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            Trip trip = Trip.builder()
                    .user(user)
                    .destination(destination)
                    .budget(budget)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            Trip savedTrip = tripRepository.save(trip);
            tripInsightsService.generateInsights(savedTrip);

            return savedTrip;
        } catch (Exception e) {
            throw new RuntimeException("Error creating trip manually: " + e.getMessage());
        }
    }

    // ✅ Get all trips
    @GetMapping("/all")
    public Iterable<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // ✅ Generate PDF report
    @GetMapping("/report/{tripId}")
    public String generateReport(@PathVariable("tripId") Long tripId) {
        return reportService.generateTripReport(tripId);
    }
}
