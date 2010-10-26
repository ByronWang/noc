package httpd.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    final protected Configuration engine;
    final protected Address address;

    final protected String refer;
    protected String name;
    protected Template template;

    final protected Store<String, Type> store;
    final protected String typeName;

    protected Type type;

    // For Cache Check file
    final protected int delay = 6000;

    protected long lastChecked;
    protected long lastModified;

    public TemplateResource(Configuration engine, Store<String, Type> store, Address address, String refer) {
        this.engine = engine;
        this.store = store;
        this.typeName = address.getPath().getSegments()[1];
        this.type = store.readData(typeName);

        this.address = address;
        this.refer = refer + ".ftl";
        this.name = typeName + "-" + refer + ".ftl";
    }

    protected void update() {
        try {

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

            if (srcLastModified - lastModified > 1000) {
                reloadTemplate();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized protected void reloadTemplate() {
        try {
            TemplateLoader loader = engine.getTemplateLoader();
            Object o = loader.findTemplateSource(name);
            long sourceModified;

            Reader reader;

            if (o == null) {
                Object o1 = loader.findTemplateSource(refer);
                sourceModified = loader.getLastModified(o1);

                if (sourceModified - this.lastModified <= 1000) {
                    return;
                }

                Template combin = engine.getTemplate(refer);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");

                Map<String, Object> root = new HashMap<String, Object>();
                root.put("type", type);
                combin.process(root, writer);
                writer.close();

                reader = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), "utf-8");

            } else {
                sourceModified = loader.getLastModified(o);
                if (sourceModified - this.lastModified <= 1000) {
                    return;
                }
                reader = loader.getReader(o, "utf-8");
            }

            Template tempTemplate = new Template(name, reader, engine);
            

            this.lastModified = sourceModified;
            this.template = tempTemplate;

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
    public void handle(Request req, Response resp) {
        try {
            long now = System.currentTimeMillis();
            if (now - lastChecked >= delay) {
                update();
            }

            // Cache
            long clientLastModified = req.getDate("If-Modified-Since");
            if (clientLastModified > 0) {

                if (lastModified - clientLastModified <= 1000) {
                    resp.setCode(304);
                    resp.close();
                    log.debug(req.getPath() + " Response 304 no change");
                    return;
                }
            }

            // normal parse
            resp.set("Cache-Control", "max-age=60000000000");
            resp.set("Content-Language", "zh-CN");
            resp.set("Content-Type", "text/html; charset=UTF-8");
            // resp.setDate("Date", System.currentTimeMillis());
            // resp.setDate("Last-Modified", lastModified);
            // resp.set("ETag", "\"" + lastModified + "\"");

            PrintStream ps = resp.getPrintStream();

            ps.print("<html>");
            ps.print("<head>");
            ps.print("<link href=\"/js/prettify/prettify.css\" type=\"text/css\" rel=\"stylesheet\" />");
            ps.print("<script type=\"text/javascript\" src=\"/js/prettify/prettify.js\"></script>");
            ps.print("<title>" + this.name + "</title>");
            ps.print("</head>");

            ps.print("<body onload=\"prettyPrint()\">");
            ps.print("<pre class=\"prettyprint lang-html\" >");

            String ts = template.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");

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

}
