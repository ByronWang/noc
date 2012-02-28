package httpd.engine;

import httpd.resource.CachableResource;
import httpd.resource.EntityResource;
import httpd.resource.NewEmptyEntityResource;
import httpd.resource.TypeResource;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vostore.DataCenter;
import noc.lang.reflect.TypeReadonlyStore;

import org.simpleframework.http.Address;
import org.simpleframework.http.Path;

import com.google.inject.Inject;

import frame.Engine;

public class DataResourceEngine implements Engine<Address, CachableResource<Object>> {
    final Map<String, CachableResource<Object>> resources;
    
    final DataCenter storeEngine;
    final TypeReadonlyStore typeStore;

    @Inject
    public DataResourceEngine(TypeReadonlyStore typeStore, DataCenter storeEngine) {
        this.storeEngine = storeEngine;
        this.typeStore = typeStore;

        resources = new HashMap<String, CachableResource<Object>>();
    }


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
            CachableResource<Object> res = new TypeResource(typeStore.readData(typeName), storeEngine.get(typeName));
            return res;
        } else if (name.charAt(0) != '~') {
            String key = path.getName();
            CachableResource<Object> res = new EntityResource(typeStore.readData(typeName), storeEngine.get(typeName),
                    key);
            return res;
        } else {
            String key = name.substring(1);
            if ("new".equals(key)) {
                CachableResource<Object> res = new NewEmptyEntityResource(typeStore.readData(typeName), typeStore);
                return res;
            } else {
                throw new UnsupportedOperationException(path.toString() + " - " + key);
            }
        }
    }

}
