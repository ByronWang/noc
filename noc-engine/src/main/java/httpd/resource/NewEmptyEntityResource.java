package httpd.resource;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class NewEmptyEntityResource implements CachableResource<Object>, Resource {
    // private static final Log log =
    // LogFactory.getLog(NewEmptyEntityResource.class);

    final Store<String, ?> store;
    final Type type;
    Object underlyData;

    public NewEmptyEntityResource(Type type, Store<String, ?> store) {
        this.store = store;
        this.type = type;
        this.underlyData = null;
        this.lastModified = 2000;
    }

    // For Cache Check file

    long lastModified = -1;

    @Override
    public void update() {
    }

    @Override
    synchronized public void reload() {
    }

    @Override
    public long lastModified() {
        return this.lastModified;
    }

    @Override
    public Object getUnderlyObject() {
        return this.underlyData;
    }

    @Override
    public void handle(Request req, Response resp) {
    }

}
