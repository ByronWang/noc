package noc.frame.vostore;

import noc.frame.Store;
import noc.lang.reflect.Type;


public class DataConfiguration extends Findable<Store<?>> implements Factory<Store<?>> {
	protected Store<Type>	types = null;
	public DataConfiguration(Store<Type> types){
		this.types = types;
		items.put(Type.class.getName(), types);
	}
	
	@Override
	protected Store<?> find(String typeName) {
		Type type = types.get(typeName);
		Store<?> store = new VoStore(this,type);
		items.put(typeName, store);
		return items.get(typeName);
	}
}
