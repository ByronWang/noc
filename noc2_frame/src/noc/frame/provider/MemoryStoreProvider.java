package noc.frame.provider;

import noc.frame.Store;
import noc.frame.model.Vo;
import noc.frame.store.MemoryStore;

public class MemoryStoreProvider extends BufferedProvider<Store<Vo>> {
	public MemoryStoreProvider() {
	}

	@Override protected Store<Vo> find(String typeName) {
		return new MemoryStore(typeName);
	}

}
