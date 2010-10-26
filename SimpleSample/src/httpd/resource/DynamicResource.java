package httpd.resource;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DynamicResource implements Cachable<Object>, Resource {
    private static final Log log = LogFactory.getLog(DynamicResource.class);

    // /template/theme/ddd/type/language

    final Cachable<?> dataResource;
    final Cachable<Template> presentationResource;

    // final String sampleTemplateName;
    // Object underlyData;

    public DynamicResource(Cachable<?> dataResource, Cachable<Template> presentationResource) {
        this.dataResource = dataResource;
        this.presentationResource = presentationResource;
    }

    // For Cache Check file
    final int delay = 6000;
    long lastChecked = -1;

    long dataLastModified = -1;
    long presentationLastModified = -1;

    long lastModified = -1;

    public void update() {
        // log.debug("update " + this.type.getName() + " - " + this.key);

        long tempLastModified = dataResource.lastModified(); // TODO
                                                             // underlyFile.lastModified();
        if (tempLastModified - dataLastModified > 1000) {
            reload();
        }

        tempLastModified = dataResource.lastModified(); // TODO
        // underlyFile.lastModified();
        if (tempLastModified - presentationLastModified > 1000) {
            reload();
        }

        lastChecked = System.currentTimeMillis();
    }

    synchronized public void reload() {
        // log.debug("check to reload " + this.type.getName() + " - " +
        // this.key);

        this.presentationLastModified = presentationResource.lastModified(); // TODO
                                                                             // underlyFile.lastModified();
        this.dataLastModified = dataResource.lastModified();
        
        this.lastModified = System.currentTimeMillis();

        log.debug("refresh data " + this.presentationLastModified);
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
//        long now = System.currentTimeMillis();
//        if (now - lastChecked >= delay) {
//            update();
//        }
//        return this.underlyData;
        throw new UnsupportedOperationException();
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
            root.put("data", dataResource.getUnderlyObject());
            Template template = presentationResource.getUnderlyObject();
            template.process(root, new OutputStreamWriter(resp.getOutputStream()));
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
