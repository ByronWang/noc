package lock;

public class SynchronizeBenchmark implements Counter {
    private long count = 0;

    @Override
    public long getValue() {
        return count;
    }

    @Override
    public synchronized void increment() {
        count++;
    }
}