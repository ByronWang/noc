package httpd.resource;

import org.simpleframework.http.resource.Resource;

public interface CachableResource extends Resource {
    long lastModified();
}
