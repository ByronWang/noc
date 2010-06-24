package noc.frame.store.memory;

import noc.frame.FindableList;
import noc.frame.Provider;
import noc.frame.Referable;
import noc.frame.Store;
import noc.frame.store.AbstractStore;

public class MemoryStore<V extends Referable> extends AbstractStore<V> {
	String type = null;
	public MemoryStore(Provider<Store<V>> provider,String type) {
		super(provider);
		datas = new FindableList<String, V>();
		this.type = type;
	}

	@Override public void invalidate() {
		// TODO Auto-generated method stub		
	}

	@Override public String toString() {
		// TODO Auto-generated method stub
		return "Mem:" + type + "[" + datas.size() +"]";
	}
}
