package Traffic;

public class Signal implements Runnable {
    public final String id;
    public final String direction;
    private volatile SignalStatus status;
    private volatile boolean running = true;
    private final Intersection intersection;

    private static final int GREEN_TIME = 120;
    private static final int ORANGE_TIME = 10;
    private static final int RED_TIME = 60;

    public Signal(String id, String direction, Intersection intersection) {
        this.id = id;
        this.direction = direction;
        this.status = SignalStatus.RED;
        this.intersection = intersection;
    }

    public SignalStatus getStatus() { return status; }
    public void setStatus(SignalStatus s) { this.status = s; }
    public void stop() { running = false; }

    @Override
    public void run() {
        try {
            while (running) {
                setStatus(SignalStatus.GREEN);
                intersection.onSignalChange(this);
                sleepSeconds(GREEN_TIME);

                setStatus(SignalStatus.ORANGE);
                intersection.onSignalChange(this);
                sleepSeconds(ORANGE_TIME);

                setStatus(SignalStatus.RED);
                intersection.onSignalChange(this);
                sleepSeconds(RED_TIME);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sleepSeconds(int s) throws InterruptedException {
        for (int i = 0; i < s && running; i++) Thread.sleep(1000);
    }

    public void toggle() {
        if (status == SignalStatus.GREEN) setStatus(SignalStatus.RED);
        else setStatus(SignalStatus.GREEN);
        intersection.onSignalChange(this);
    }

    public String getColoredStatus() {
        switch (status) {
            case GREEN: return "\u001B[32mGREEN\u001B[0m";
            case ORANGE: return "\u001B[33mORANGE\u001B[0m";
            case RED: return "\u001B[31mRED\u001B[0m";
            default: return status.name();
        }
    }

    @Override
    public String toString() {
        return String.format("Signal{id=%s,dir=%s,status=%s}", id, direction, getColoredStatus());
    }
}
