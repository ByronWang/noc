package noc.frame.store.persistable;

import java.util.List;

import noc.frame.Persister;
import noc.frame.Vo;
import noc.frame.store.AbstractStore;

public class PersistableStore extends AbstractStore {
	Persister<Vo> persister;

	public AbstractStore setPersistre(Persister<Vo> persister) {
		this.persister = persister;
		return this;
	}

	@Override public void setup() {
		super.setup();
		persister.setup();

		List<Vo> vList = persister.list();
		for (Vo v : vList) {
			super.put(v);
		}
	}

	@Override public void put(Vo v) {
		v = persister.update(v);
		super.put(v);
	}

	@Override public String toString() {
		return this.getClass().getName() + "<<" + this.persister.toString();
	}
}
