package noc.frame.store;

import java.util.List;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.model.Referable;
import noc.frame.model.ReferableList;

public class PersistableStore<T extends Referable>  implements Store<T> {
	Persister<T> persister;
	ReferableList<T> datas = new ReferableList<T>();

	public Store<T> setPersistre(Persister<T> persister) {
		this.persister = persister;
		return this;
	}

	@Override public void setup() {
		persister.setup();

		List<T> vList = persister.list();
		for (T v : vList) {
			datas.put(v);
		}
	}

	@Override public String toString() {
		return this.getClass().getName() + "<<" + this.persister.toString();
	}

	@Override public void cleanup() {
		
	}
	@Override public T get(String key) {
		return datas.get(key);
	}

	@Override public List<T> list() {
		return datas.getValues();
	}

	@Override public T update(T v) {
		
		// TODO Auto-generated method stub
		return null;
	}
}
