package com.example.weatherdashboard;

import java.io.*;
import java.util.*;

public class CacheManager {
    private static final String FILE_NAME = "cache.txt";

    public void saveCache(List<WeatherData> dataList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (WeatherData data : dataList) {
                writer.write(String.format("%s;%f;%d;%s%n",
                        data.getCity(), data.getTemperature(), data.getHumidity(), data.getCondition()));
            }
        } catch (IOException e) {
            System.out.println("Failed to save cache: " + e.getMessage());
        }
    }

    public List<WeatherData> loadCache() {
        List<WeatherData> cached = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return cached;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    cached.add(new WeatherData(
                            parts[0],
                            Double.parseDouble(parts[1]),
                            Integer.parseInt(parts[2]),
                            parts[3]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load cache: " + e.getMessage());
        }
        return cached;
    }
}
