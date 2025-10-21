package com.crypto.app;

import com.crypto.service.CryptoService;
import com.crypto.util.DBInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;

public class CryptoTraderApp {
    private static final Logger logger = LogManager.getLogger(CryptoTraderApp.class);
    private static final CryptoService service = new CryptoService();

    public static void main(String[] args) {
        logger.info(" Starting CryptoTrader Application...");
        DBInitializer.initialize();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        service.syncPrices(10);

        while (running) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. View Crypto List");
            System.out.println("2. Refresh Prices");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> service.showAll();
                case 2 -> service.syncPrices(10);
                case 3 -> {
                    running = false;
                    logger.info("Application exited.");
                }
                default -> System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}
