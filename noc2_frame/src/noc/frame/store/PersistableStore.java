package noc.frame.store;

import java.util.List;

import noc.frame.Persister;
import noc.frame.ReferableList;
import noc.frame.Store;
import noc.frame.Vo;

public class PersistableStore  implements Store<Vo> {
	Persister<Vo> persister;
	ReferableList<Vo> datas = new ReferableList<Vo>();

	public Store<Vo> setPersistre(Persister<Vo> persister) {
		this.persister = persister;
		return this;
	}

	@Override public void setup() {
		persister.setup();

		List<Vo> vList = persister.list();
		for (Vo v : vList) {
			datas.put(v);
		}
	}

	@Override public String toString() {
		return this.getClass().getName() + "<<" + this.persister.toString();
	}

	@Override public void cleanup() {
		
	}
	@Override public Vo get(String key) {
		return datas.get(key);
	}

	@Override public List<Vo> list() {
		return datas.getValues();
	}

	@Override public Vo update(Vo v) {
		
		// TODO Auto-generated method stub
		return null;
	}
}
