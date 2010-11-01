package lock;

public class SynchronizeBenchmark implements Counter {
    private long count = 0;

    public long getValue() {
        return count;
    }

    public synchronized void increment() {
        count++;
    }
}