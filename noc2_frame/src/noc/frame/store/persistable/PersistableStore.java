package noc.frame.store.persistable;

import java.util.List;

import noc.frame.FindableList;
import noc.frame.Persister;
import noc.frame.Provider;
import noc.frame.Store;
import noc.frame.Vo;
import noc.frame.store.AbstractStore;

public class PersistableStore extends AbstractStore {
	Persister<Vo> persister;

	public PersistableStore(Provider<Store<Vo>> provider) {
		super(provider);
		datas = new FindableList<String, Vo>();
	}

	public AbstractStore setPersistre(Persister<Vo> persister) {
		this.persister = persister;
		this.persister.refer(this);
		return this;
	}

	@Override public void setup() {
		super.setup();
		persister.setup();

		List<Vo> vList = persister.list();
		for (Vo v : vList) {
			super.datas.put(v.getReferID(), v);
		}
	}

	public void invalidate() {

	}

	@Override public Vo get(String key) {
		Vo v = super.get(key);
		if (v != null) {
			return v;
		}

		v = persister.get(key);
		super.datas.put(v.getReferID(), v);

		return super.get(key);
	}

	@Override public List<Vo> list() {
		return super.list();
	}

	@Override public Vo update(Vo v) {
		v = persister.update(v);
		return super.update(v);
	}

	@Override public String toString() {
		return this.getClass().getName() + "<<" + this.persister.toString();
	}
}
