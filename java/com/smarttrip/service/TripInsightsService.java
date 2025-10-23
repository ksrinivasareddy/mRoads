package com.smarttrip.service;

import com.smarttrip.entity.Trip;
import com.smarttrip.entity.TripInsights;
import com.smarttrip.repository.TripInsightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service
public class TripInsightsService {

    @Autowired
    private TripInsightsRepository tripInsightsRepository;

    public TripInsights generateInsights(Trip trip) {
        // Dummy API responses for now
        String weather = "Sunny, 28°C";
        String currencyRate = "1 USD = 83.20 INR";

        TripInsights insights = TripInsights.builder()
                .trip(trip)
                .weather(weather)
                .currencyRate(currencyRate)
                .recommendations("Explore the local culture and cuisine.")
                .safetyTips("Stay hydrated and avoid late-night travel.")
                .budgetTips("Use public transport and avoid peak-season hotel rates.")
                .build();

        return tripInsightsRepository.save(insights);
    }

    public TripInsights getInsightsByTripId(Long tripId) {
        return tripInsightsRepository.findByTripId(tripId)
                .orElseThrow(() -> new RuntimeException("Insights not found for Trip ID: " + tripId));
    }
}
