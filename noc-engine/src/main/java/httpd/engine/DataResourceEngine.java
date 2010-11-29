package httpd.engine;

import httpd.resource.CachableResource;
import httpd.resource.EntityResource;
import httpd.resource.NewEmptyEntityResource;
import httpd.resource.TypeResource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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

import frame.Engine;
import freemarker.template.Configuration;

public class DataResourceEngine implements Engine<Address, CachableResource<Object>> {

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
                Type type = types.getReadonly(typeName);
                Persister<String, Vo> p = dbEngine.getPersister(Vo.class, type);
                p.open();
                Store<String, ?> store = new VoPersistableStore(this, type, p);
                store.open();
                return store;
            }
        };
        resources = new HashMap<String, CachableResource<Object>>();
    }

    final Map<String, CachableResource<Object>> resources;

    @Override
    public CachableResource<Object> resolve(Address address) {
        Path path = address.getPath();
        CachableResource<Object> o = resources.get(path);
        if (o == null) {
            o = this.make(path);
            resources.put(path.getPath().toString(), o);
            o = resources.get(path.getPath().toString());
        }
        return o;
    }

    public CachableResource<Object> make(Path path) {

        String typeName = path.getSegments()[1];
        String name = path.getName();

        if (name == null) {
            CachableResource<Object> res = new TypeResource(typeStore.getReadonly(typeName), storeEngine.get(typeName));
            return res;
        } else if (name.charAt(0) != '~') {
            String key = path.getName();
            CachableResource<Object> res = new EntityResource(typeStore.getReadonly(typeName), storeEngine.get(typeName),
                    key);
            return res;
        } else {
            String key = name.substring(1);
            if ("new".equals(key)) {
                CachableResource<Object> res = new NewEmptyEntityResource(typeStore.getReadonly(typeName), typeStore);
                return res;
            } else {
                throw new UnsupportedOperationException(path.toString() + " - " + key);
            }
        }
    }

}
