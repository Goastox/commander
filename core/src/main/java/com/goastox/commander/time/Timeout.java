package com.goastox.commander.time;

public class Timeout {

    private final long seconds;

    private final int nanos;

    public Timeout(long seconds, int nanos) {
        this.seconds = seconds;
        this.nanos = nanos;
    }

    public static Timeout ofSeconds(long seconds){
        return create(seconds, 0);
    }

    public static Timeout ofMinutes(long minutes){
        return create(Math.multiplyExact(minutes, 60), 0);
    }

    public static Timeout ofHours(long hours){
        return create(Math.multiplyExact(hours, 60 * 60), 0);
    }

    private static Timeout create(long seconds, int nanoAdjustment) {
        return new Timeout(seconds, nanoAdjustment);
    }

    public long getSeconds() {
        return seconds;
    }

    public int getNanos() {
        return nanos;
    }
}
