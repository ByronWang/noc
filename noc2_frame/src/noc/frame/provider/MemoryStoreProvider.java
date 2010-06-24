package noc.frame.provider;

import noc.frame.AbstractProvider;
import noc.frame.Store;
import noc.frame.Vo;
import noc.frame.store.memory.MemoryStore;

public class MemoryStoreProvider extends AbstractProvider<Store<Vo>> {
	public MemoryStoreProvider() {
	}

	@Override protected Store<Vo> find(String key) {
		return new MemoryStore(this,key);
	}

}
