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

public class EntityResource implements Resource {
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
