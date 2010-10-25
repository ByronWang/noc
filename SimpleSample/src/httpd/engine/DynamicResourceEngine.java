package httpd.engine;

import httpd.resource.EntityResource;
import httpd.resource.NullResource;
import httpd.resource.TypeResource;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DbConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.DataCenterConfiguration;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.http.Address;
import org.simpleframework.http.resource.Resource;

import frame.AbstractEngine;
import freemarker.TypeTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class DynamicResourceEngine extends AbstractEngine {
    private static final Log log = LogFactory.getLog(DynamicResourceEngine.class);

    TypeReadonlyStore typeStore;
    Configuration templateEngine;
    DataCenterConfiguration storeEngine;
    DbConfiguration dbEngine;

    protected final File homeDir;
    protected final Resource unknownResource;

    static final String APP_DEFINE_PATH = "app_define_path";

    static final String DB_DRIVERCLASS = "db_driverclass";
    static final String DB_URL = "db_url";
    static final String DB_USERNAME = "db_username";
    static final String DB_PASSWORD = "db_password";

    static final String DEBUG_MODE = "debug";

    static final String TEMPLATE_PATH = "template_path";
    static final String TEMPLATE_WORK_PATH = "template_work_path";

    public Resource getUnknownResource() {
        return unknownResource;
    }

    public DynamicResourceEngine() {
        this(new File("./htdocs"));
    }

    public DynamicResourceEngine(String root) {
        this(new File(root));
    }

    public DynamicResourceEngine(File root) {
        this.homeDir = root;
        unknownResource = new NullResource(new File(this.homeDir, "404.html"));
        this.init();
    }

    public File getRealPath(String path) {
        return new File(homeDir, path);
    }

    // Extension
    String TEMPLATE_EXTENSION = ".ftl";
    Properties props = new Properties();

    public void init() {

        try {

            props.load(new FileInputStream(new File("conf/web.properties")));

            dbEngine = DbConfiguration.getEngine(props.getProperty(DB_DRIVERCLASS), props.getProperty(DB_URL),
                    props.getProperty(DB_USERNAME), props.getProperty(DB_PASSWORD));

            typeStore = new TypeReadonlyStore();
            typeStore.setUp();

            File root = new File(props.getProperty(APP_DEFINE_PATH));

            typeStore.load(root);

            storeEngine = new DataCenterConfiguration(typeStore) {
                @Override
                protected Store<String, ?> find(String typeName) {
                    Type type = types.readData(typeName);
                    Persister<String, Vo> p = dbEngine.getPersister(Vo.class, type);
                    p.setUp();
                    Store<String, ?> store = new VoPersistableStore(this, type, p);
                    store.setUp();
                    return store;
                }
            };

            File tempateFolder = getRealPath("template");
            File templateWorkFolder = getRealPath("work_template");

            if (templateWorkFolder.exists())
                templateWorkFolder.delete();
            templateWorkFolder.mkdir();

            TemplateLoader[] loaders = new TemplateLoader[] { new FileTemplateLoader(tempateFolder),
                    new FileTemplateLoader(templateWorkFolder),
                    new TypeTemplateLoader(typeStore, tempateFolder, templateWorkFolder) };

            /* Create and adjust the configuration */
            templateEngine = new Configuration();
            templateEngine.setTemplateUpdateDelay(10);
            templateEngine.setTemplateLoader(new MultiTemplateLoader(loaders));
            // templateEngine.setSharedVariable("contextPath",
            // context.getContextPath());
            templateEngine.setObjectWrapper(new DefaultObjectWrapper());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resource make(Address target) {
        String[] segments = target.getPath().getSegments();
        // http://localhost/Customer/200100

        String typeName = segments[0];
        Type type = typeStore.readData(typeName);
        log.debug("ADDRESS : " + target.toString());
        
        Store<String, ?> store = (Store<String, ?>) storeEngine.get(type.getName());
        switch (segments.length) {
        case 1:
            return new TypeResource(type, templateEngine, store);
        case 2:
            return new EntityResource(type, templateEngine, store,segments[1]);
        }

        throw new RuntimeException();

    }

}
