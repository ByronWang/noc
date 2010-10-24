package httpd.resource;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.Vo;
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

    public EntityResource(Type type,Configuration templateEngine, Vo data) {
        try {
            this.templateEngine = templateEngine;
            this.type = type;
            this.data = data;
            template = templateEngine.getTemplate(type.getName() + "_" + "edit" + ".ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }        
    }

    Configuration templateEngine;
    Template template;
    Vo data;
    Type type;

    @Override
    public void handle(Request req, Response resp) {
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
