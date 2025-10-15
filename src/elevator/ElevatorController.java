package elevator;

import java.util.List;

public class ElevatorController {
    public static void assignRequest(List<Elevator> elevators, Request req) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - req.getSource());

            // Idle elevators first
            if (e.isIdle() && distance < minDistance) {
                best = e;
                minDistance = distance;
            }
            // Elevator moving same direction and can pick up
            else if (!e.isIdle()) {
                boolean sameDir = e.getDirection().equals("UP") && req.getDestination() > req.getSource() && e.getCurrentFloor() <= req.getSource();
                sameDir |= e.getDirection().equals("DOWN") && req.getDestination() < req.getSource() && e.getCurrentFloor() >= req.getSource();

                if (sameDir && distance < minDistance) {
                    best = e;
                    minDistance = distance;
                }
            }
        }

        if (best != null) {
            System.out.println("🟢 Elevator " + best.getId() + " assigned [From " + req.getSource() + " → " + req.getDestination() + "]");
            best.addRequest(req);
        } else {
            System.out.println("⚠️ No elevator available for request [From " + req.getSource() + " → " + req.getDestination() + "]");
        }
    }
}
