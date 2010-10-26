package httpd.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DynamicResource implements CachableResource<Object>, Resource {
    private static final Log log = LogFactory.getLog(DynamicResource.class);

    // /template/theme/ddd/type/language

    final Address address;
    final CachableResource<?> dataResource;
    final CachableResource<Template> presentationResource;

    // final String sampleTemplateName;
    // Object underlyData;

    public DynamicResource(Address address, CachableResource<?> dataResource,
            CachableResource<Template> presentationResource) {
        this.address = address;
        this.dataResource = dataResource;
        this.presentationResource = presentationResource;
    }

    // For Cache Check file
    final int delay = 2000;
    long lastChecked = -1;

    long dataLastModified = -1;
    long presentationLastModified = -1;

    long lastModified = -1;

    protected ByteArrayOutputStream bufferedResponse;

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

        try {
            long tempDataResourceLastModified = dataResource.lastModified();
            long tempPresentionResourceLastModified = presentationResource.lastModified();

            if (tempDataResourceLastModified - this.dataLastModified <= 1000
                    && tempPresentionResourceLastModified - this.presentationLastModified <= 1000) {
                return;
            }

            ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();

            Map<String, Object> root = new HashMap<String, Object>();
            root.put("path", this.address.getPath().getPath());
            root.put("data", dataResource.getUnderlyObject());
            Template template = presentationResource.getUnderlyObject();
            template.process(root, new OutputStreamWriter(bufferStream));
            bufferStream.close();

            // update instance variable
            this.dataLastModified = tempDataResourceLastModified;
            this.presentationLastModified = tempPresentionResourceLastModified;
            this.lastModified = System.currentTimeMillis();
            this.bufferedResponse = bufferStream;

            log.debug("recreate response to bufferStream " + this.lastModified);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
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
        // long now = System.currentTimeMillis();
        // if (now - lastChecked >= delay) {
        // update();
        // }
        // return this.underlyData;
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

            if ("get".endsWith(req.getMethod())) {
                // normal parse
                resp.set("Cache-Control", "max-age=6000");
                resp.set("Content-Language", "zh-CN");
                resp.set("Content-Type", "text/html; charset=UTF-8");
                resp.setDate("Date", System.currentTimeMillis());
                resp.setDate("Last-Modified", this.lastModified);
                resp.set("ETag", "\"" + lastModified + "\"");

                this.bufferedResponse.writeTo(resp.getOutputStream());

                resp.close();
            } else {
                dataResource.handle(req, resp);

                // normal parse
                resp.set("Cache-Control", "max-age=6000");
                resp.set("Content-Language", "zh-CN");
                resp.set("Content-Type", "text/html; charset=UTF-8");
                resp.setDate("Date", System.currentTimeMillis());
                resp.setDate("Last-Modified", this.lastModified);
                resp.set("ETag", "\"" + lastModified + "\"");

                this.bufferedResponse.writeTo(resp.getOutputStream());

                resp.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
