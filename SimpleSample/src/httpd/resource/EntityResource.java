package httpd.resource;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EntityResource implements Cachable<Object>, Resource {
    private static final Log log = LogFactory.getLog(EntityResource.class);

    // /template/theme/ddd/type/language
    final Configuration templateEngine;
    final Store<String, ?> store;

    final Type type;
    final String key;

    final String sampleTemplateName;
    Object underlyData;

    public EntityResource(Type type, Configuration templateEngine, Store<String, ?> store, String primaryKey) {
        this.templateEngine = templateEngine;
        this.store = store;
        this.type = type;
        this.key = primaryKey;
        this.underlyData = store.readData(primaryKey);
        this.sampleTemplateName = type.getName() + "_" + "edit" + ".ftl";
    }

    // For Cache Check file
    final int delay = 6000;
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
        try {
            long now = System.currentTimeMillis();
            if (now - lastChecked >= delay) {
                update();
            }

            // Cache
            long clientLastModified = req.getDate("If-Modified-Since");
            if (clientLastModified > 0) {
                if (this.lastModified - clientLastModified <= 1000) {
                    resp.setCode(304);
                    resp.close();
                    log.debug(req.getPath() + " Response 304 no change");
                    return;
                }
            }

            Map<String, Object> root = new HashMap<String, Object>();
            root.put("data", underlyData);
            Template sampleTemplate = templateEngine.getTemplate(this.sampleTemplateName);
            sampleTemplate.process(root, new OutputStreamWriter(resp.getOutputStream()));
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
