package com.example.cryptotracker.service;

import com.example.cryptotracker.dto.CryptoPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CryptoService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${crypto.api.url}")
    private String apiUrl;

    public CryptoPrice getCryptoPrice(String coin) {
        try {
            String url = apiUrl.replace("{coin}", coin.toLowerCase());

            Map<String, Map<String, Object>> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey(coin.toLowerCase())) {
                throw new RestClientException("Invalid response from API");
            }

            Object priceObj = response.get(coin.toLowerCase()).get("usd");
            double price = priceObj instanceof Integer
                    ? ((Integer) priceObj).doubleValue()
                    : (Double) priceObj;

            // ✅ Return CryptoPrice object
            return new CryptoPrice(coin, price);

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch crypto data: " + e.getMessage());
        }
    }
}
