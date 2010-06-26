package noc.frame.provider;

import noc.frame.Store;
import noc.frame.model.Referable;
import noc.frame.store.MemoryStore;

public class MemoryStoreProvider<T extends Referable> extends BufferedProvider<Store<T>> {
	public MemoryStoreProvider() {
	}

	@Override protected Store<T> find(String typeName) {
		return new MemoryStore<T>();
	}
	
	protected <K extends Referable> Store<K> find(Class<K> clazz) {
		return new MemoryStore<K>();
	}
}
