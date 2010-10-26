package httpd.engine;

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

    // Extension
    String TEMPLATE_EXTENSION = ".ftl";

    @Override
    public Resource resolve(Address target) {
        // String[] segments = target.getPath().getSegments();
        // http://localhost/Customer/200100

        // String typeName = segments[1];
        // Type type = typeStore.readData(typeName);
        log.debug("ADDRESS : " + target.toString());

        // Store<String, ?> store = (Store<String, ?>)
        // storeEngine.get(type.getName());

        // String key;
        if (target.getPath().getName() != null) {
            return presentationEngine.resolve(target);// Temp test
        } else {
            return presentationEngine.resolve(target);// Temp test
            // return new TypeResource(type, templateEngine, store);
        }
    }

}
