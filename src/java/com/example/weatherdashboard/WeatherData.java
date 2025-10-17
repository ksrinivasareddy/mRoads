package com.example.weatherdashboard;

public class WeatherData {

    private String city;
    private double temperature;
    private int humidity;
    private String condition;

    public WeatherData(String city, double temperature, int humidity, String condition) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }
}
