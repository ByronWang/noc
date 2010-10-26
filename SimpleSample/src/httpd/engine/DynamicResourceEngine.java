package httpd.engine;

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

import frame.Engine;
import freemarker.template.Configuration;

public class DynamicResourceEngine implements Engine<Address, Resource> {
    private static final Log log = LogFactory.getLog(DynamicResourceEngine.class);

    TypeReadonlyStore typeStore;
    Configuration templateEngine;
    DataCenterConfiguration storeEngine;
    DbConfiguration dbEngine;
    
    PresentationResourceEngine presentationEngine;
    

    protected final File homeFolder;

    static final String APP_DEFINE_PATH = "app_define_path";

    static final String DB_DRIVERCLASS = "db_driverclass";
    static final String DB_URL = "db_url";
    static final String DB_USERNAME = "db_username";
    static final String DB_PASSWORD = "db_password";

    static final String DEBUG_MODE = "debug";

    public DynamicResourceEngine(File root) {
        this.homeFolder = root;
        this.init();
    }

    public File getRealPath(String path) {
        return new File(homeFolder, path);
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

            File definePath = new File(props.getProperty(APP_DEFINE_PATH));

            typeStore.load(definePath);

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
            
            File templateFolder= new File(this.homeFolder, "template");
            presentationEngine = new PresentationResourceEngine(templateFolder, typeStore);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resource resolve(Address target) {
        String[] segments = target.getPath().getSegments();
        // http://localhost/Customer/200100

        String typeName = segments[1];
        Type type = typeStore.readData(typeName);
        log.debug("ADDRESS : " + target.toString());        
        
        Store<String, ?> store = (Store<String, ?>) storeEngine.get(type.getName());
        
        String key;
        if((key=target.getPath().getName())!=null){
            return presentationEngine.resolve(target);//Temp test
        }else{
            return new TypeResource(type, templateEngine, store);
        }        
    }

}
