package httpd.resource;


public interface Cachable<T> {
    void update();
    void reload();
    T getUnderlyObject();

    long lastModified();
}
