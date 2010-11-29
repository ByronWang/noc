package noc.frame.vostore;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import noc.frame.Agent;
import noc.frame.EntityFactory;
import noc.frame.Factory;
import noc.frame.Identifiable;
import noc.frame.Store;
import noc.lang.reflect.Type;

public class DefaultStore<V extends Identifiable> implements Store<String, V>  {
    final protected Map<String, V> items;
    final protected Factory<?> parent;
    final protected Type type;
    final protected EntityFactory<V> fact;

    public DefaultStore(Factory<?> parent, Type type, EntityFactory<V> fact) {
    	this.items = new Hashtable<String, V>();
    	this.fact = fact;
        this.parent = parent;
        this.type = type;
    }

    @Override
    public V getReadonly(String key) {
        return items.get(key);
    }

    @Override
    public V update(String key, V v) {
    	if(v instanceof Agent){
            Agent vo = (Agent)v;
            if (vo.isBeModified()) {
                items.put(key, v);
                return items.get(key);
            }else{
            	return v;
            }            	
    	}else{
            items.put(key, v);
            return items.get(key);    		
    	}
    }

    @Override
    public List<V> list() {
        return new ArrayList<V>(items.values());
    }

    @Override
    public void open() {
    }

    @Override
    public void close() {
    	items.clear();
    }

	@Override
	public V getForUpdate(String key) {
		return fact.decorate(items.get(key));
	}
}