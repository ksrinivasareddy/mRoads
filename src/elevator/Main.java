package elevator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalFloors = 10;
        List<Elevator> elevators = new ArrayList<>();
        int numElevators = 3;

        // Initialize elevators and start threads
        for (int i = 1; i <= numElevators; i++) {
            Elevator e = new Elevator(i, 6, totalFloors - 1);
            elevators.add(e);
            new Thread(e).start();
        }

        System.out.println("🏢 Elevator System Started!");
        System.out.println("Enter requests: <source> <destination> or 'exit' to quit\n");

        while (true) {
            System.out.print("Request: ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.split(" ");
                if (parts.length != 2) { System.out.println("⚠️ Enter two numbers"); continue; }

                int source = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);

                if (source < 0 || source >= totalFloors || dest < 0 || dest >= totalFloors) {
                    System.out.println("❌ Invalid floor range (0–" + (totalFloors - 1) + ")");
                    continue;
                }

                ElevatorController.assignRequest(elevators, new Request(source, dest));
            } catch (Exception e) {
                System.out.println("⚠️ Invalid input!");
            }
        }

        System.out.println("🏁 Simulation ended");
        sc.close();
    }
}
