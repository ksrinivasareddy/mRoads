package com.example.weatherdashboard;

import java.util.Scanner;

public class WeatherApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherService service = new WeatherService();

        System.out.println("==== Weather Dashboard ====");

        // Get city and country from user
        System.out.print("Enter city name: ");
        String city = scanner.nextLine().trim();

        System.out.print("Enter country code (e.g., IN, US): ");
        String country = scanner.nextLine().trim();

        try {
            // Fetch weather for city + country
            WeatherData data = service.getWeather(city, country);

            // Display report
            System.out.println("\nWeather Report for " + city + ", " + country + ":");
            System.out.println("Temperature: " + data.getTemperature() + "°C");
            System.out.println("Humidity: " + data.getHumidity() + "%");
            System.out.println("Condition: " + data.getCondition());

        } catch (Exception e) {
            System.out.println("Error fetching weather: " + e.getMessage());
        }

        System.out.println("\nExiting Weather Dashboard...");
        scanner.close();
    }
}
