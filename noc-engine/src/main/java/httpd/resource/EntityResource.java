package httpd.resource;

import java.io.IOException;

import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class EntityResource implements CachableResource<Object>, Resource {
    private static final Log log = LogFactory.getLog(EntityResource.class);

    final Store<String, Object> store;

    final Type type;
    final String key;

    Object underlyData;

    @SuppressWarnings("unchecked")
    public EntityResource(Type type, Store<String, ?> store, String primaryKey) {
        this.store = (Store<String, Object>) store;
        this.type = type;
        this.key = primaryKey;
        this.underlyData = store.readData(primaryKey);
    }

    // For Cache Check file
    final int delay = 1000;
    long lastChecked = -1;
    long sourceLastModified = -1;

    long lastModified = -1;

    @Override
    public void update() {
        log.debug("update " + this.type.getName() + " - " + this.key);

        long srcLastModified = System.currentTimeMillis(); // TODO

        if (srcLastModified - sourceLastModified > 1000) {
            reload();
        }

        lastChecked = System.currentTimeMillis();
    }

    @Override
    synchronized public void reload() {
        log.debug("Reload " + this.type.getName() + " - " + this.key);

        this.sourceLastModified = System.currentTimeMillis(); // TODO
                                                              // underlyFile.lastModified();
        this.lastModified = System.currentTimeMillis();

        log.debug("refresh data " + this.sourceLastModified);
    }

    @Override
    public long getLastModified() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            update();
        }
        return this.lastModified;
    }

    @Override
    public Object getUnderlyObject() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            update();
        }
        return this.underlyData;
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            if ("GET".equals(req.getMethod())) {
                long now = System.currentTimeMillis();
                if (now - lastChecked >= delay) {
                    update();
                }
            } else if ("POST".equals(req.getMethod())) {
                this.store.borrowData(null);
                Vo dest = (Vo) store.borrowData(this.key);
                dest = VoHelper.putAll(req.getForm(), dest, this.type);
                store.returnData(dest.getIndentify(), dest);
                this.underlyData = store.readData(key);
                this.sourceLastModified = System.currentTimeMillis(); // TODO
                this.lastModified = System.currentTimeMillis();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
