package noc.frame.store;

import java.util.List;

import noc.frame.FindableList;
import noc.frame.Provider;
import noc.frame.Store;
import noc.frame.Vo;

public abstract class AbstractStore implements Store<Vo> {
	protected Provider<Store<Vo>> provider;
	protected FindableList<String, Vo> datas = null;

	public AbstractStore(Provider<Store<Vo>> provider) {
		this.provider = provider;
	}

	@Override public Vo get(String key) {
		return datas.get(key);
	}

	@Override public List<Vo> list() {
		return datas.getValues();
	}

	@Override public Vo update(Vo v) {
		datas.put(v.getReferID(), v);
		return datas.get(v.getReferID());
	}

	@Override public void cleanup() {
		
	}

	@Override public void setup() {		
	}
}
