package com.smarttrip.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

@Component
public class CurrencyApiClient {

    @Value("${currency.api.url}")
    private String baseUrl;

    public double convert(String from, String to, double amount) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("base", from)
                    .queryParam("symbols", to)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(response);
            double rate = json.getJSONObject("rates").getDouble(to);
            return rate * amount;
        } catch (Exception e) {
            return -1;
        }
    }
}
