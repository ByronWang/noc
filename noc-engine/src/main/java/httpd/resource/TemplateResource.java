package httpd.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import noc.frame.Store;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateResource implements CachableResource<Template>, Resource {
    private static final Log log = LogFactory.getLog(TemplateResource.class);

    final protected Configuration engine;
    final protected Address address;

    final protected String refer;
    protected String name;
    protected Template template;
    protected ByteArrayOutputStream templateSource;

    final protected Store<String, Type> store;
    final protected String typeName;

    protected Type type;

    // For Cache Check file
    final protected int delay = 3000;

    protected long lastChecked;

    protected long sourceTemplateLastModified;

    protected long lastModified;
    
    final String path;
    final String actionPath;

    public TemplateResource(Configuration engine, Store<String, Type> store, Address address, String refer) {
        this.engine = engine;
        this.store = store;
        this.typeName = address.getPath().getSegments()[1];
        this.type = store.readData(typeName);

        this.address = address;
        this.path = address.getPath().toString()+ "?"  +  address.getQuery().toString();
        String name = address.getPath().getName();
        if(name!=null && name.charAt(0)=='~'){
            this.actionPath = address.getPath().getDirectory() +  "?"  +  address.getQuery().toString();   
        }else{
            this.actionPath = address.getPath().toString()+ "?"  +  address.getQuery().toString();            
        }
        
        this.refer = refer + ".ftl";
        this.name = typeName + "-" + refer + ".ftl";

        this.update();
    }

    public void update() {
        try {
            log.debug("update " + this.name);

            long srcLastModified = -1;

            TemplateLoader loader = engine.getTemplateLoader();
            Object o = loader.findTemplateSource(name);
            if (o != null) {
                srcLastModified = loader.getLastModified(o);
            } else {
                o = loader.findTemplateSource(refer);
                if (o != null) {
                    srcLastModified = loader.getLastModified(o);
                }
            }

            if (srcLastModified - sourceTemplateLastModified > 1000) {
                reload();
            }

            lastChecked = System.currentTimeMillis();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public void reload() {
        log.debug("check to reload " + this.name);

        try {
            TemplateLoader loader = engine.getTemplateLoader();
            Object o = loader.findTemplateSource(name);
            long sourceModified;

            Reader reader;

            if (o == null) {
                Object o1 = loader.findTemplateSource(refer);
                sourceModified = loader.getLastModified(o1);

                if (sourceModified - this.sourceTemplateLastModified <= 1000) {
                    return;
                }

                Template combin = engine.getTemplate(refer);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");

                Map<String, Object> root = new HashMap<String, Object>();
                root.put("path", this.path);
                root.put("actionPath", this.actionPath);
                root.put("type", type);
                combin.process(root, writer);
                writer.close();

                reader = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), "utf-8");

                log.debug("reload from " + refer);
            } else {
                sourceModified = loader.getLastModified(o);
                if (sourceModified - this.sourceTemplateLastModified <= 1000) {
                    return;
                }
                reader = loader.getReader(o, "utf-8");

                log.debug("reload from " + this.name);
            }

            // Create template
            Template tempTemplate = new Template(name, reader, engine);

            // Create template source(HTML)
            Template viewSourceTemplate = engine.getTemplate("source-view.ftl");
            String source = tempTemplate.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("name", this.name);
            root.put("source", source);
            viewSourceTemplate.process(root, new OutputStreamWriter(bufferStream));
            bufferStream.close();

            // update instance variable
            this.sourceTemplateLastModified = sourceModified;
            this.lastModified = System.currentTimeMillis();
            this.template = tempTemplate;
            this.templateSource = bufferStream;

            log.debug("refresh template, bufferStream " + this.lastModified);

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
    public Template getUnderlyObject() {
        long now = System.currentTimeMillis();
        if (now - lastChecked >= delay) {
            update();
        }
        return this.template;
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

            // normal parse
            resp.set("Cache-Control", "max-age=6000");
            resp.set("Content-Language", "zh-CN");
            resp.set("Content-Type", "text/html; charset=UTF-8");
            resp.setDate("Date", System.currentTimeMillis());
            resp.setDate("Last-Modified", this.lastModified);
            resp.set("ETag", "\"" + lastModified + "\"");

            this.templateSource.writeTo(resp.getOutputStream());
            
            log.debug(req.getPath() + " write source");

            resp.close();
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
