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

public class TypeResource implements Resource {
    private static final Log log = LogFactory.getLog(TypeResource.class);

    public TypeResource(Type type,Configuration templateEngine, Store<String,?> store) {
        try {
            this.templateEngine = templateEngine;
            this.type = type;
            this.store = store;
            template = templateEngine.getTemplate(type.getName() + "_" + "list" + ".ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }        
    }

    Configuration templateEngine;
    Template template;
    Store<String,?> store;
    Type type;

    @Override
    public void handle(Request req, Response resp) {
        log.debug(type.getName() + " : " + store.list().size());
        
        resp.set("Content-Type", "text/html; charset=UTF-8");
        try {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("data", store.list());
            template.process(root, new OutputStreamWriter(resp.getOutputStream()));
            resp.close();
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
