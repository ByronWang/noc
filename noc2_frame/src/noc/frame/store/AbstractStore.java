package noc.frame.store;

import java.util.List;

import noc.frame.FindableList;
import noc.frame.Provider;
import noc.frame.Referable;
import noc.frame.Store;

public abstract class AbstractStore<V extends Referable> implements Store<V> {
	protected Provider<Store<V>> provider;
	protected FindableList<String, V> datas = null;

	public AbstractStore(Provider<Store<V>> provider) {
		this.provider = provider;
	}

	@Override public V get(String key) {
		return datas.get(key);
	}

	@Override public List<V> list() {
		return datas.getValues();
	}

	@Override public V update(V v) {
		datas.put(v.getReferID(), v);
		return datas.get(v.getReferID());
	}

	@Override public void cleanup() {
		
	}

	@Override public void setup() {		
	}
}
