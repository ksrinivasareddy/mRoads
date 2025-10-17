package com.example.weatherdashboard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherService {

    private static final String API_KEY = "23a5fcfca21d41f17e8787249d415490"; // Replace with your OpenWeatherMap key

    public WeatherData getWeather(String city, String country) throws Exception {
        String urlString = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s,%s&units=metric&appid=%s",
                city, country, API_KEY);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new Exception("City not found or API error");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(sb.toString());

        String cityName = json.getString("name");
        double temp = json.getJSONObject("main").getDouble("temp");
        int humidity = json.getJSONObject("main").getInt("humidity");
        String condition = json.getJSONArray("weather").getJSONObject(0).getString("description");

        return new WeatherData(cityName, temp, humidity, condition);
    }
}
