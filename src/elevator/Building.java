package elevator;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private int totalFloors;
    private List<Elevator> elevators;

    public Building(int totalFloors, int numElevators, int maxCapacity) {
        this.totalFloors = totalFloors;
        elevators = new ArrayList<>();
        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new Elevator(i, maxCapacity, totalFloors - 1));
        }
    }

    public List<Elevator> getElevators() { return elevators; }
    public int getTotalFloors() { return totalFloors; }

    public void showStatus() {
        System.out.println("\n--- Elevator Status ---");
        for (Elevator e : elevators) {
            System.out.println("Elevator " + e.getId() + " | Floor: " + e.getCurrentFloor() +
                    " | Dir: " + e.getDirection() + " | Status: " + e.getStatus());
        }
    }
}
