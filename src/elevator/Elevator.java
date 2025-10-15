package elevator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Elevator implements Runnable {
    private int id;
    private int currentFloor;
    private String direction; // "UP", "DOWN", "IDLE"
    private int maxCapacity;
    private int currentLoad;
    private int maxFloor;

    private final List<Request> requests;
    private boolean running = true;

    public Elevator(int id, int maxCapacity, int maxFloor) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
        this.currentLoad = 0;
        this.direction = "IDLE";
        this.requests = new ArrayList<>();
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public String getDirection() { return direction; }
    public boolean isIdle() { return requests.isEmpty(); }

    public synchronized void addRequest(Request req) {
        requests.add(req);
        if (direction.equals("IDLE")) direction = determineDirection();
    }

    public void stopElevator() { running = false; }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized(this) {
                    if (!requests.isEmpty()) step();
                    else Thread.sleep(500); // idle wait
                }
            } catch (InterruptedException e) {
                System.out.println("Elevator " + id + " interrupted.");
            }
        }
    }

    private void step() throws InterruptedException {
        if (requests.isEmpty()) {
            direction = "IDLE";
            return;
        }

        // Determine direction
        direction = determineDirection();

        // Move one floor
        if (direction.equals("UP")) currentFloor++;
        else if (direction.equals("DOWN")) currentFloor--;

        // Handle pickups/drop-offs
        Iterator<Request> iterator = requests.iterator();
        while (iterator.hasNext()) {
            Request r = iterator.next();

            // Pickup
            if (r.getSource() == currentFloor && currentLoad < maxCapacity) {
                openDoor();
                currentLoad++;
                System.out.println("1 passenger entered at floor " + currentFloor + " | Load: " + currentLoad);
                closeDoor();
            }

            // Drop-off
            if (r.getDestination() == currentFloor) {
                openDoor();
                currentLoad = Math.max(0, currentLoad - 1);
                System.out.println("1 passenger exited at floor " + currentFloor + " | Load: " + currentLoad);
                closeDoor();
                iterator.remove();
            }
        }

        if (allRequestsInDirectionServed()) direction = reverseDirectionIfNeeded();
    }

    private String determineDirection() {
        for (Request r : requests) {
            if (r.getDestination() > currentFloor) return "UP";
            else if (r.getDestination() < currentFloor) return "DOWN";
        }
        return "IDLE";
    }

    private boolean allRequestsInDirectionServed() {
        for (Request r : requests) {
            if (direction.equals("UP") && r.getDestination() > currentFloor) return false;
            if (direction.equals("DOWN") && r.getDestination() < currentFloor) return false;
        }
        return true;
    }

    private String reverseDirectionIfNeeded() {
        for (Request r : requests) {
            if (r.getDestination() != currentFloor) {
                return r.getDestination() > currentFloor ? "UP" : "DOWN";
            }
        }
        return "IDLE";
    }

    private void openDoor() { System.out.println("Elevator " + id + " door opened at floor " + currentFloor); }
    private void closeDoor() { System.out.println("Elevator " + id + " door closed"); }
}
