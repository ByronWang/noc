package noc.frame.vostore;

import noc.frame.Factory;
import noc.frame.Findable;
import noc.frame.Store;
import noc.lang.reflect.Type;

public class DataCenterConfiguration extends Findable<String, Store<String, ?>> implements Factory<Store<?, ?>> {
    protected Store<String, Type> types = null;

    public DataCenterConfiguration(Store<String, Type> types) {
        this.types = types;
        items.put(Type.class.getName(), types);
    }

    @Override
    protected Store<String, ?> find(String typeName) {
        Type type = types.readData(typeName);
        Store<String, ?> store = new VoStore(this, type);
        store.setUp();
        ;
        return store;
    }
}
