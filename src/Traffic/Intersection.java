package Traffic;

import java.io.*;
import java.time.Instant;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class Intersection {
    private final Map<String, Queue<Car>> lanes = new ConcurrentHashMap<>();
    private final List<Signal> signals = new ArrayList<>();
    private final BufferedWriter logWriter;
    private final Object logLock = new Object();

    public Intersection(String[] directions, String logFilePath) throws IOException {
        for (String d : directions) lanes.put(d, new ConcurrentLinkedQueue<>());
        this.logWriter = new BufferedWriter(new FileWriter(logFilePath));
        writeLogLine("--- TrafficRush Log Start " + Instant.now().toString());
    }

    public void addSignal(Signal s) { signals.add(s); }
    public List<Signal> getSignals() { return Collections.unmodifiableList(signals); }
    public Map<String, Queue<Car>> getLanes() { return lanes; }

    private String getCurrentTime() {
        return LocalTime.now().withNano(0).toString();
    }

    public void addCar(Car c) {
        Queue<Car> q = lanes.get(c.getDirection());
        if (q == null) throw new IllegalArgumentException("Invalid direction: " + c.getDirection());
        q.add(c);
        String time = getCurrentTime();
        try { writeLogLine(time + " | ARRIVAL: car=" + c.getId() + " dir=" + c.getDirection() + " time=" + c.getArrivalTime()); }
        catch (IOException ignored) {}
        System.out.println("[" + time + "] New car: " + c);
    }

    public void onSignalChange(Signal s) {
        String time = getCurrentTime();
        try { writeLogLine(time + " | SIGNAL: " + s.direction + " -> " + s.getStatus()); }
        catch (IOException ignored) {}
        System.out.println("[" + time + "] [Signal] " + s.direction + " -> " + s.getColoredStatus());
    }

    public void step(long currentTime) {
        for (Signal s : signals) {
            Queue<Car> q = lanes.get(s.direction);
            if (q == null) continue;
            Car car = q.peek();

            if (car != null) {
                String time = getCurrentTime();
                if (s.getStatus() == SignalStatus.GREEN) {
                    car.setEnterTime(currentTime);
                    long passDuration = Math.max(1, (long)Math.ceil(1.0 / Math.max(0.1, car.getSpeed())));
                    car.setExitTime(currentTime + passDuration);
                    q.remove();
                    try { writeLogLine(time + " | PASS: car=" + car.getId() + " dir=" + car.getDirection() + " enter=" + car.getEnterTime() + " exit=" + car.getExitTime() + " wait=" + car.waitingSeconds()); }
                    catch (IOException ignored) {}
                    System.out.println("[" + time + "] Car passed: " + car);
                } else if (s.getStatus() == SignalStatus.ORANGE) {
                    try { writeLogLine(time + " | WAIT: car=" + car.getId() + " dir=" + car.getDirection() + " waiting for GREEN"); }
                    catch (IOException ignored) {}
                }
            }
        }
    }

    public void printState() {
        String time = getCurrentTime();
        System.out.println("\n[" + time + "] --- Intersection State ---");
        for (Signal s : signals) System.out.println(s);
        System.out.println("Queues:");
        lanes.forEach((d, q) -> System.out.println(" " + d + " -> " + q.size() + " cars"));
        System.out.println("Controls: [n] North, [s] South, [e] East, [w] West, [q] Quit");
    }

    private void writeLogLine(String line) throws IOException {
        synchronized (logLock) {
            logWriter.write(line + "\n");
            logWriter.flush();
        }
    }

    public void close() throws IOException {
        writeLogLine("--- TrafficRush Log End " + Instant.now().toString());
        logWriter.close();
    }
}
