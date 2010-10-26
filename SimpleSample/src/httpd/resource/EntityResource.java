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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EntityResource implements CachableResource {
    private static final Log log = LogFactory.getLog(EntityResource.class);

    public EntityResource(Type type, Configuration templateEngine, Store<String, ?> store, String key) {
        try {
            this.templateEngine = templateEngine;
            this.store = store;
            this.type = type;
            this.key = key;
            this.data = store.readData(key);
            template = templateEngine.getTemplate(type.getName() + "_" + "edit" + ".ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // For Cache Check file
    final int delay = 6000;
    long lastChecked;
    
    long lastModified;
    @Override
    public long lastModified() {
//        long now = System.currentTimeMillis();
//        if (now - lastChecked >= delay) {
//            lastModified = f.lastModified();
//            log.debug("Check File[ " + f.getPath() + "] LastModified : " + lastModified);
//            lastChecked = now;
//        }
//        return lastModified;
        
        return -1;
    }

    // /template/theme/ddd/type/language
    final Configuration templateEngine;
    final Store<String, ?> store;

    final Type type;
    final String key;

    final Template template;
    Object data;

    @Override
    public void handle(Request req, Response resp) {
        log.debug("Data : " + this.key);
        
        try {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("data", data);
            template.process(root, new OutputStreamWriter(resp.getOutputStream()));
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
