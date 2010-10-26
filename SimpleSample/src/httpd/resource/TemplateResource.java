package httpd.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
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

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateResource implements CachableResource {
    private static final Log log = LogFactory.getLog(TemplateResource.class);

    final Configuration engine;
    // final Configuration preEngine;
    protected Template template;
    String name;

    final String refer;
    final Address address;

    final Store<String, Type> store;
    final String typeName;
    protected Type type;

    public TemplateResource(Configuration engine, Store<String, Type> store, Address address, String refer) {
        this.engine = engine;
        // this.preEngine = new Configuration();
        // this.preEngine.setTemplateLoader(engine.getTemplateLoader());

        this.store = store;
        this.typeName = address.getPath().getSegments()[1];
        this.type = store.readData(typeName);

        this.address = address;
        this.refer = refer + ".ftl";
        this.name = typeName + "-" + refer + ".ftl";
    }

    void updateTemplate() {
        try {
            TemplateLoader loader = engine.getTemplateLoader();
            Object o = loader.findTemplateSource(name);
            long lastModified;

            Reader reader;

            if (o == null) {
                Object o1 = loader.findTemplateSource(refer);
                lastModified = loader.getLastModified(o1);

                if (lastModified - this.lastModified <= 1000) {
                    return;
                }

                Template combin = engine.getTemplate(refer);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out,"utf-8");

                Map<String, Object> root = new HashMap<String, Object>();
                root.put("type", type);
                combin.process(root, writer);
                writer.close();

                reader = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),"utf-8");

            } else {
                lastModified = loader.getLastModified(o);
                if (lastModified - this.lastModified <= 1000) {
                    return;
                }

                reader = loader.getReader(o, null);
            }

            Template tempTemplate = new Template(name, reader, engine);

            this.lastModified = lastModified;
            template = tempTemplate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long lastModified() {
        try {
            TemplateLoader loader = engine.getTemplateLoader();
            Object o = loader.findTemplateSource(name);
            if (o != null) {
                return loader.getLastModified(o);
            }

            o = loader.findTemplateSource(refer);
            if (o != null) {
                return loader.getLastModified(o);
            }

            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            // Cache
            long req_LastModified = req.getDate("If-Modified-Since");
            if (req_LastModified > 0) {
                long now = System.currentTimeMillis();

                if (now - lastChecked >= delay) {
                    lastModified = this.lastModified();
                    lastChecked = now;

                    if (lastModified - req_LastModified <= 1000) {
                        resp.setCode(304);
                        resp.close();
                        log.debug(req.getPath() + " Response 304 no change");
                        return;
                    }
                }
            }

            updateTemplate();

            // normal parse
            resp.set("Cache-Control", "max-age=60000000000");
            resp.set("Content-Language", "zh-CN");
            resp.set("Content-Type", "text/html; charset=UTF-8");
//            resp.setDate("Date", System.currentTimeMillis());
//            resp.setDate("Last-Modified", lastModified);
//            resp.set("ETag", "\"" + lastModified + "\"");
            
            PrintStream ps =   resp.getPrintStream();

                  
            ps.print("<html>");
            ps.print("<head>");
            ps.print("<link href=\"/js/prettify/prettify.css\" type=\"text/css\" rel=\"stylesheet\" />");
            ps.print("<script type=\"text/javascript\" src=\"/js/prettify/prettify.js\"></script>");
            ps.print("</head>");
            
            ps.print("<body onload=\"prettyPrint()\">");
            ps.print("<pre class=\"prettyprint lang-html\" >");
            

//            ps.print("&lt;tr onload=\"prettyPrint()\"/&gt; sdfdsafdsaf ");
            
//
//            // max-age
//            log.debug("Transfer file [" + req.getPath() + "] contents to client by [[" + req.getPath() + "]]");
//            template.dump(resp.getPrintStream());
//            
            
            String  ts = template.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            ps.print(ts);
            
            ps.print("</pre>");
            ps.print("</body>");            
            ps.print("</html>");
            
            
            
            resp.close();
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // For Cache Check file
    final int delay = 6000;
    long lastChecked;

    long lastModified;

}
