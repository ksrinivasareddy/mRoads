package com.crypto.util;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class APIClient {

    public static List<Map<String, Object>> fetchTopCryptos(int limit) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String api = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=" + limit + "&page=1&sparkline=false";
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                JsonArray arr = JsonParser.parseReader(in).getAsJsonArray();
                for (JsonElement e : arr) {
                    JsonObject o = e.getAsJsonObject();
                    Map<String, Object> crypto = new HashMap<>();
                    crypto.put("symbol", o.get("symbol").getAsString().toUpperCase());
                    crypto.put("name", o.get("name").getAsString());
                    crypto.put("price", o.get("current_price").getAsDouble());
                    list.add(crypto);
                }
                in.close();
            }
        } catch (Exception e) {
            System.out.println("Error fetching API data: " + e.getMessage());
        }
        return list;
    }
}
