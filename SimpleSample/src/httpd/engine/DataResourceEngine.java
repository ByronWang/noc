package httpd.engine;

import httpd.resource.EntityResource;
import httpd.resource.TypeResource;

import java.io.File;
import java.util.Properties;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.dbpersister.DbConfiguration;
import noc.frame.vo.Vo;
import noc.frame.vostore.DataCenterConfiguration;
import noc.frame.vostore.VoPersistableStore;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

import org.simpleframework.http.Address;
import org.simpleframework.http.Path;
import org.simpleframework.http.resource.Resource;

import frame.Engine;
import freemarker.template.Configuration;

public class DataResourceEngine implements Engine<Address, Resource> {

    DataCenterConfiguration storeEngine;
    DbConfiguration dbEngine;

    Configuration templateEngine;
    TypeReadonlyStore typeStore;

    static final String DB_DRIVERCLASS = "db_driverclass";
    static final String DB_URL = "db_url";
    static final String DB_USERNAME = "db_username";
    static final String DB_PASSWORD = "db_password";

    public DataResourceEngine(File appHome, Properties props, TypeReadonlyStore typeStore, Configuration templateEngine) {

        this.typeStore = typeStore;

        dbEngine = DbConfiguration.getEngine(props.getProperty(DB_DRIVERCLASS), props.getProperty(DB_URL),
                props.getProperty(DB_USERNAME), props.getProperty(DB_PASSWORD));
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

    }

    @Override
    public Resource resolve(Address target) {
        
        Path path = target.getPath();
        String typeName = path.getSegments()[1];

        if (path.getName() == null) {
            Resource res = new TypeResource(typeStore.readData(typeName), storeEngine.get(typeName), templateEngine);
            return res;
        } else {
            String key = path.getName();
            Resource res = new EntityResource(typeStore.readData(typeName), storeEngine.get(typeName), key,
                    templateEngine);
            return res;
        }
    }

}
