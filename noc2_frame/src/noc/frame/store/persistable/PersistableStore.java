package noc.frame.store.persistable;

import java.util.List;

import noc.frame.FindableList;
import noc.frame.Persister;
import noc.frame.Provider;
import noc.frame.Referable;
import noc.frame.Store;
import noc.frame.store.AbstractStore;

public class PersistableStore<V extends Referable> extends AbstractStore<V> {
	Persister<V> persister;

	public PersistableStore(Provider<Store<V>> provider) {
		super(provider);
		datas = new FindableList<String, V>();
	}

	public AbstractStore<V> setPersistre(Persister<V> persister) {
		this.persister = persister;
		this.persister.refer(this);
		return this;
	}

	@Override public void setup() {
		super.setup();
		persister.setup();

		List<V> vList = persister.list();
		for (V v : vList) {
			super.datas.put(v.getReferID(), v);
		}
	}

	public void invalidate() {

	}

	@Override public V get(String key) {
		V v = super.get(key);
		if (v != null) {
			return v;
		}

		v = persister.get(key);
		super.datas.put(v.getReferID(), v);

		return super.get(key);
	}

	@Override public List<V> list() {
		return super.list();
	}

	@Override public V update(V v) {
		v = persister.update(v);
		return super.update(v);
	}

	@Override public String toString() {
		return this.getClass().getName() + "<<" + this.persister.toString();
	}
}
