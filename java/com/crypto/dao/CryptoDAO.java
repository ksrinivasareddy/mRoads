package com.crypto.dao;

import com.crypto.model.Crypto;
import com.crypto.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CryptoDAO {

    public void saveOrUpdate(Crypto crypto) {
        String sql = """
            INSERT INTO crypto_master (symbol, name, current_price, last_updated)
            VALUES (?, ?, ?, NOW())
            ON DUPLICATE KEY UPDATE current_price = VALUES(current_price), last_updated = NOW();
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crypto.getSymbol());
            ps.setString(2, crypto.getName());
            ps.setDouble(3, crypto.getCurrentPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    public List<Crypto> getAll() {
        List<Crypto> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT symbol, name, current_price FROM crypto_master")) {
            while (rs.next()) {
                list.add(new Crypto(
                        rs.getString("symbol"),
                        rs.getString("name"),
                        rs.getDouble("current_price")));
            }
        } catch (Exception e) {
            System.out.println("DB Fetch Error: " + e.getMessage());
        }
        return list;
    }
}
