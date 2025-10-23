package com.smarttrip.service;

import org.springframework.stereotype.Service;

@Service
public class AIInsightsService {

    // ✅ Generate AI-based recommendations (mock logic for now)
    public String generateRecommendation(String destination) {
        return switch (destination.toLowerCase()) {
            case "paris" -> "Explore the Eiffel Tower and try a local café by the Seine.";
            case "tokyo" -> "Visit Shibuya Crossing and enjoy authentic sushi at Tsukiji Market.";
            case "london" -> "Tour the British Museum and take a stroll through Hyde Park.";
            default -> "Discover local landmarks and cultural spots unique to " + destination + ".";
        };
    }

    public String generateSafetyTips(String destination) {
        return "Stay alert in crowded tourist areas and keep valuables secure when traveling in " + destination + ".";
    }

    public String generateBudgetTips(String destination, double budget) {
        if (budget < 500) {
            return "Use public transport, stay in hostels, and try local street food.";
        } else if (budget < 2000) {
            return "Book mid-range hotels and enjoy a few guided tours.";
        } else {
            return "Enjoy premium dining and luxury stays for a comfortable experience!";
        }
    }
}
