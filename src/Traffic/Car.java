package Traffic;

import java.util.concurrent.atomic.AtomicInteger;

public class Car {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1);
    private final int id;
    private final String direction;
    private final long arrivalTime;
    private final double speed;
    private Long enterTime = null;
    private Long exitTime = null;

    public Car(String direction, long arrivalTime, double speed) {
        this.id = ID_GEN.getAndIncrement();
        this.direction = direction;
        this.arrivalTime = arrivalTime;
        this.speed = speed;
    }

    public int getId() { return id; }
    public String getDirection() { return direction; }
    public long getArrivalTime() { return arrivalTime; }
    public double getSpeed() { return speed; }
    public Long getEnterTime() { return enterTime; }
    public Long getExitTime() { return exitTime; }

    public void setEnterTime(long enterTime) { this.enterTime = enterTime; }
    public void setExitTime(long exitTime) { this.exitTime = exitTime; }

    public long waitingSeconds() {
        if (enterTime == null) return 0L;
        return enterTime - arrivalTime;
    }

    @Override
    public String toString() {
        return String.format("Car{id=%d,dir=%s,arr=%d,speed=%.2f}", id, direction, arrivalTime, speed);
    }
}
