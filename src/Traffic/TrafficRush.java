package Traffic;

import java.time.Instant;
import java.util.*;

public class TrafficRush {
    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        String[] directions = {"N", "S", "E", "W"};
        Intersection intersection = new Intersection(directions, "traffic_log.txt");

        Map<String, Signal> signalMap = new HashMap<>();
        for (String d : directions) {
            Signal s = new Signal("S-" + d, d, intersection);
            signalMap.put(d, s);
            Thread t = new Thread(s, "signal-" + d);
            t.start();
        }

        // Random car generator thread
        Thread carGenThread = new Thread(() -> {
            Random rnd = new Random();
            while (running) {
                try {
                    Thread.sleep(1000);
                    if (rnd.nextDouble() < 0.3) {
                        String dir = directions[rnd.nextInt(directions.length)];
                        double speed = 0.5 + rnd.nextDouble() * 1.5;
                        Car c = new Car(dir, Instant.now().getEpochSecond(), speed);
                        intersection.addCar(c);
                    }
                    intersection.step(Instant.now().getEpochSecond());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        carGenThread.start();

        // Dashboard display thread (every 2 seconds)
        Thread displayThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(2000);
                    intersection.printState();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        displayThread.start();

        // Key input thread
        Thread keyThread = new Thread(() -> {
            try {
                while (running) {
                    if (System.in.available() > 0) {
                        int key = System.in.read();
                        switch (key) {
                            case 'n': case 'N': toggleSignal(signalMap, "N"); break;
                            case 's': case 'S': toggleSignal(signalMap, "S"); break;
                            case 'e': case 'E': toggleSignal(signalMap, "E"); break;
                            case 'w': case 'W': toggleSignal(signalMap, "W"); break;
                            case 'q': case 'Q': running = false; break;
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        keyThread.start();

        while (running) Thread.sleep(500);

        signalMap.values().forEach(Signal::stop);
        carGenThread.interrupt();
        displayThread.interrupt();
        keyThread.interrupt();
        intersection.close();
        System.out.println("Simulation ended.");
    }

    private static void toggleSignal(Map<String, Signal> map, String dir) {
        Signal s = map.get(dir.toUpperCase());
        if (s != null) {
            s.toggle();
            String time = java.time.LocalTime.now().withNano(0).toString();
            System.out.println("[" + time + "] [Manual] " + dir + " toggled to " + s.getColoredStatus());
        }
    }
}
