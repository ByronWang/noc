package httpd.engine;

import httpd.resource.CachableResource;
import httpd.resource.DynamicResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import noc.lang.reflect.TypeReadonlyStore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.Engine;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class DynamicResourceEngine implements Engine<Address, Resource> {
    private static final Log log = LogFactory.getLog(DynamicResourceEngine.class);

    final PresentationResourceEngine presentationEngine;
    final DataResourceEngine dataResourceEngine;

    final Configuration templateEngine;

    protected final File appHome;
    Properties props = new Properties();

    static final String APP_DEFINE_PATH = "app_define_path";

    static final String DEBUG_MODE = "debug";

    public DynamicResourceEngine(File appHome) {
        try {
            this.appHome = appHome;
            props.load(new FileInputStream(new File(this.appHome, "../conf/web.properties")));

            TypeReadonlyStore typeStore;
            typeStore = new TypeReadonlyStore();
            typeStore.setUp();
            String definePath = props.getProperty(APP_DEFINE_PATH);
            if (definePath.charAt(1) == ':' || definePath.charAt(0) == '/') {
                typeStore.load(new File(props.getProperty(APP_DEFINE_PATH)));
            } else {
                typeStore.load(new File(this.appHome, props.getProperty(APP_DEFINE_PATH)));
            }

            /* Create and adjust the configuration */
            templateEngine = new Configuration();
            templateEngine.setTemplateUpdateDelay(10);
            templateEngine.setDirectoryForTemplateLoading(new File(this.appHome, "template"));

            presentationEngine = new PresentationResourceEngine(templateEngine, typeStore);
            dataResourceEngine = new DataResourceEngine(appHome, props, typeStore, templateEngine);

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
            presentationreResource = presentationEngine.resolve(target);
            datareResource = dataResourceEngine.resolve(target);
        } else {
            presentationreResource = presentationEngine.resolve(target);
            datareResource = dataResourceEngine.resolve(target);
        }
        DynamicResource dynamicResource = new DynamicResource(datareResource, presentationreResource);
        return dynamicResource;
    }

}
