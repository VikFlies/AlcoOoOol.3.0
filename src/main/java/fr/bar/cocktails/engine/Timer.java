package fr.bar.cocktails.engine;

public class Timer {
    private final long duration;
    private long startTime;
    private boolean running = false;
    private boolean requested = false;
    private long remaining;

    public Timer(long duration) {
        this.duration = duration;
        this.remaining = duration;
    }

    public void update(long now) {
        if (running) {
            remaining = duration - (System.currentTimeMillis() - startTime);
            if (remaining <= 0) {
                running = false;
            }
        } else if (requested) {
            running = true;
            requested = false;
            startTime = System.currentTimeMillis();
            remaining = duration;
        }
    }

    public void start() {
        if (!running) {
            requested = true;
        } else {
            remaining = duration;
        }
    }

    public boolean isRunning() {
        return running || requested;
    }

    public long getRemaining() {
        return remaining;
    }
}
