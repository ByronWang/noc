package httpd.resource;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

import freemarker.template.Configuration;

public class EntityResource implements CachableResource<Object>, Resource {
    private static final Log log = LogFactory.getLog(EntityResource.class);

    // /template/theme/ddd/type/language
    final Configuration templateEngine;
    final Store<String, ?> store;

    final Type type;
    final String key;

    final String sampleTemplateName;
    Object underlyData;

    public EntityResource(Type type, Store<String, ?> store, String primaryKey, Configuration templateEngine) {
        this.templateEngine = templateEngine;
        this.store = store;
        this.type = type;
        this.key = primaryKey;
        this.underlyData = store.readData(primaryKey);
        this.sampleTemplateName = type.getName() + "_" + "edit" + ".ftl";
    }

    // For Cache Check file
    final int delay = 1000;
    long lastChecked = -1;
    long sourceLastModified = -1;

    long lastModified = -1;

    public void update() {
        log.debug("update " + this.type.getName() + " - " + this.key);

        long srcLastModified = System.currentTimeMillis(); // TODO
                                                           // underlyFile.lastModified();

        if (srcLastModified - sourceLastModified > 1000) {
            reload();
        }

        lastChecked = System.currentTimeMillis();
    }

    synchronized public void reload() {
        log.debug("check to reload " + this.type.getName() + " - " + this.key);

        this.sourceLastModified = System.currentTimeMillis(); // TODO
                                                              // underlyFile.lastModified();
        this.lastModified = System.currentTimeMillis();

        log.debug("refresh data " + this.sourceLastModified);
    }

    @Override
    public long lastModified() {
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
//        try {
            long now = System.currentTimeMillis();
            if (now - lastChecked >= delay) {
                update();
            }
    }

}
