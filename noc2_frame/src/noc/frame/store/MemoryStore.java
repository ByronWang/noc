package noc.frame.store;

import java.util.List;

import noc.frame.Store;
import noc.frame.model.Referable;
import noc.frame.model.ReferableList;

public class MemoryStore<T extends Referable> implements Store<T> {
	public MemoryStore() {
	}

	ReferableList<T> datas = new ReferableList<T>();

	@Override public void cleanup() {

	}

	@Override public void setup() {
	}

	@Override public T get(String key) {
		return datas.get(key);
	}

	@Override public List<T> list() {
		return datas.getValues();
	}

	@Override public T update(T v) {
		datas.put(v);
		return datas.get(v.getReferID());
	}

	@Override public String toString() {
		// TODO Auto-generated method stub
		return "Mem:" + "[" + datas.size() + "]";
	}

}
