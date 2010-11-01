package httpd.resource;

import help.PrintObejct;

import java.io.IOException;
import java.util.List;

import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

public class TypeResource implements CachableResource<Object>, Resource {
    private static final Log log = LogFactory.getLog(TypeResource.class);

    @SuppressWarnings("unchecked")
    public TypeResource(Type type, Store<String, ?> store) {
        this.store = (Store<String, Object>) store;
        this.type = type;
    }

    Store<String, Object> store;
    Type type;

    List<?> underlyList;

    // For Cache Check file
    final int delay = 1000;
    long lastChecked = -1;
    long sourceLastModified = -1;

    long lastModified = -1;

    public void update() {
        log.debug("update " + this.type.getName());

        long srcLastModified = System.currentTimeMillis(); // TODO
                                                           // underlyFile.lastModified();

        if (srcLastModified - sourceLastModified > 1000) {
            reload();
        }

        lastChecked = System.currentTimeMillis();
    }

    synchronized public void reload() {
        log.debug("check to reload " + this.type.getName());

        this.underlyList = this.store.list();
        this.sourceLastModified = System.currentTimeMillis(); // TODO
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
        return this.underlyList;
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
                // Form f = req.getForm();
                // f.
                this.store.borrowData(null);
                Vo dest = (Vo) store.borrowData(null);
                PrintObejct.print(Request.class, req);
                dest = VoHelper.putAll(req.getForm(), dest, this.type);
                store.returnData(dest.getIndentify(), (Object) dest);
                this.underlyList = store.list();
                lastModified = 2000;
                this.sourceLastModified = System.currentTimeMillis(); // TODO
                this.lastModified = System.currentTimeMillis();
//                this.update();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
