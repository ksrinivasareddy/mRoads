package com.smarttrip.controller;

import com.smarttrip.entity.TripInsights;
import com.smarttrip.service.TripInsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insights")
public class TripInsightsController {

    @Autowired
    private TripInsightsService tripInsightsService;

    // ✅ Fetch insights for a given trip
    @GetMapping("/{tripId}")
    public TripInsights getInsights(@PathVariable("tripId") Long tripId) {
        TripInsights insights = tripInsightsService.getInsightsByTripId(tripId);
        if (insights == null) {
            throw new RuntimeException("No insights found for trip ID: " + tripId);
        }
        return insights;
    }
}
