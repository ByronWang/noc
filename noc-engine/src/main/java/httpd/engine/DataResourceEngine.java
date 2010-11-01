package httpd.engine;

import httpd.resource.CachableResource;
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

import org.simpleframework.http.Path;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import frame.Engine;
import freemarker.DefaultModel;
import freemarker.template.Configuration;

public class DataResourceEngine implements Engine<Path, CachableResource<Object>> {

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
    public CachableResource<Object> resolve(Path path) {

        String typeName = path.getSegments()[1];
        String name = path.getName();

        if (name == null) {
            CachableResource<Object> res = new TypeResource(typeStore.readData(typeName), storeEngine.get(typeName),
                    templateEngine);
            return res;
        } else if (name.charAt(0) != '~') {
            String key = path.getName();
            CachableResource<Object> res = new EntityResource(typeStore.readData(typeName), storeEngine.get(typeName),
                    key, templateEngine);
            return res;
        } else {
            String key = name.substring(1);
            if ("new".equals(key)) {
                CachableResource<Object> res = new CachableResource<Object>() {
                    @Override
                    public void handle(Request req, Response resp) {

                    }

                    @Override
                    public void update() {
                    }

                    @Override
                    public void reload() {
                    }

                    @Override
                    public long lastModified() {
                        return 2000;
                    }

                    @Override
                    public Object getUnderlyObject() {
                        return new DefaultModel("");
                    }
                };
                return res;
            } else {
                throw new UnsupportedOperationException(path.toString() + " - " + key);
            }
        }
    }

}
