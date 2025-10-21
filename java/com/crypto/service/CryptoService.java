package com.crypto.service;

import com.crypto.dao.CryptoDAO;
import com.crypto.model.Crypto;
import com.crypto.util.APIClient;
import java.util.List;
import java.util.Map;

public class CryptoService {
    private final CryptoDAO dao = new CryptoDAO();

    public void syncPrices(int limit) {
        List<Map<String, Object>> data = APIClient.fetchTopCryptos(limit);
        for (Map<String, Object> m : data) {
            Crypto c = new Crypto((String) m.get("symbol"), (String) m.get("name"), (Double) m.get("price"));
            dao.saveOrUpdate(c);
        }
        System.out.println("✅ Synced " + data.size() + " crypto prices.");
    }

    public void showAll() {
        List<Crypto> list = dao.getAll();
        System.out.println("\n=== CRYPTO MARKET ===");
        System.out.printf("%-10s %-20s %-10s%n", "Symbol", "Name", "Price(USD)");
        System.out.println("-------------------------------------------");
        list.forEach(c -> System.out.printf("%-10s %-20s $%-10.2f%n", c.getSymbol(), c.getName(), c.getCurrentPrice()));
    }
}
