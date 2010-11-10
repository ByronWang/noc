package httpd.resource;

import org.simpleframework.http.resource.Resource;

public interface CachableResource<T> extends Resource {
    void update();

    void reload();

    T getUnderlyObject();

    long lastModified();
}
