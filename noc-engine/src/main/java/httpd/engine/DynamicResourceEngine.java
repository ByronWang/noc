package httpd.engine;

import httpd.Loader;
import httpd.Source;
import httpd.resource.CachableResource;
import httpd.resource.DynamicResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Properties;

import noc.lang.reflect.TypeReadonlyStore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class DynamicResourceEngine implements Engine<Address, Resource> {
    public PresentationResourceEngine getPresentationEngine() {
        return presentationEngine;
    }

    public DataResourceEngine getDataResourceEngine() {
        return dataResourceEngine;
    }

    private static final Log log = LogFactory.getLog(DynamicResourceEngine.class);

    final PresentationResourceEngine presentationEngine;
    final DataResourceEngine dataResourceEngine;

    final Configuration templateEngine;

    protected final File appHome;
    Properties props = new Properties();

    static final String APP_DEFINE_PATH = "app_define_path";

    static final String DEBUG_MODE = "debug";
    final Loader loader;

    public DynamicResourceEngine(Loader loader, String appHome) {
        try {
            this.appHome = new File(appHome);
            this.loader = loader;
            props.load(loader.findSource("/WEB-INF/web.properties").getInputStream());

            TypeReadonlyStore typeStore;
            typeStore = new TypeReadonlyStore();
            typeStore.setUp();
            String definePath = props.getProperty(APP_DEFINE_PATH);
            if (definePath.charAt(1) == ':' || definePath.charAt(0) == '/') {
                typeStore.load(new File(props.getProperty(APP_DEFINE_PATH)));
            } else {
                typeStore.load(new File(this.appHome, props.getProperty(APP_DEFINE_PATH)));
            }

            final Loader innerLoader = this.loader;
            /* Create and adjust the configuration */
            templateEngine = new Configuration();
            templateEngine.setEncoding(Locale.CHINA, "UTF-8");
            templateEngine.setTemplateUpdateDelay(10);
            templateEngine.setTemplateLoader(

            new TemplateLoader() {
                @Override
                public Object findTemplateSource(String name) throws IOException {
                    return innerLoader.findSource("/template/" + name);
                }
                @Override
                public Reader getReader(Object templateSource, String encoding) throws IOException {
                    return new InputStreamReader(((Source) templateSource).getInputStream(),encoding);
                }
                @Override
                public long getLastModified(Object templateSource) {
                    return ((Source) templateSource).getLastModified();
                }
                @Override
                public void closeTemplateSource(Object templateSource) throws IOException {
                    ((Source) templateSource).closeSource();
                }
            });
//            
//            
//            templateEngine.setDirectoryForTemplateLoading(new File(
//                    "D:\\linux\\workspace\\noc-engine\\target\\classes\\htdocs"));

            presentationEngine = new PresentationResourceEngine(templateEngine, typeStore);
            dataResourceEngine = new DataResourceEngine(this.appHome, props, typeStore, templateEngine);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getRealPath(String path) {
        return new File(this.appHome, path);
    }

    @Override
    public Resource resolve(Address target) {
        log.debug("ADDRESS : " + target.toString());

        CachableResource<Template> presentationreResource;
        CachableResource<Object> datareResource;
        if (target.getPath().getName() != null) {
            datareResource = dataResourceEngine.resolve(target);
            presentationreResource = presentationEngine.resolve(target);
        } else {
            datareResource = dataResourceEngine.resolve(target);
            presentationreResource = presentationEngine.resolve(target);
        }

        DynamicResource dynamicResource = new DynamicResource(target, datareResource, presentationreResource);
        return dynamicResource;
    }

}
