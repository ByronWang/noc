package noc.frame.store.memory;

import noc.frame.AbstractProvider;
import noc.frame.Referable;
import noc.frame.Store;

public class MemoryStoreProvider<V extends Referable> extends AbstractProvider<Store<V>> {
	public MemoryStoreProvider() {
	}

	@Override protected Store<V> find(String key) {
		return new MemoryStore<V>(this,key);
	}

}
