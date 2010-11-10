package noc.frame;

public interface Factory<T> {
    T get(String key);
}
