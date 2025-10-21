package com.crypto.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    private static final Logger logger = LogManager.getLogger(DBInitializer.class);

    public static void initialize() {
        createDatabaseIfNotExists();
        createTables();
    }

    private static void createDatabaseIfNotExists() {
        String url = "jdbc:mysql://localhost:3306/";
        try (Connection conn = java.sql.DriverManager.getConnection(url, "root", "Srinu@45");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS crypto_trader");
            logger.info("✅ Database 'crypto_trader' verified/created.");
        } catch (Exception e) {
            logger.error("❌ Failed to create database: " + e.getMessage(), e);
        }
    }

    private static void createTables() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS crypto_master (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  symbol VARCHAR(10) NOT NULL UNIQUE,
                  name VARCHAR(100),
                  current_price DECIMAL(12,2),
                  last_updated DATETIME
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS portfolio (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  symbol VARCHAR(10) NOT NULL UNIQUE,
                  quantity DECIMAL(12,4),
                  avg_buy_price DECIMAL(12,2),
                  current_value DECIMAL(12,2)
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS transactions (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  symbol VARCHAR(10) NOT NULL,
                  type ENUM('BUY','SELL'),
                  quantity DECIMAL(12,4),
                  price DECIMAL(12,2),
                  timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                );
            """);

            logger.info("✅ Tables verified/created successfully.");
        } catch (Exception e) {
            logger.error("❌ Failed to initialize tables: " + e.getMessage(), e);
        }
    }
}
