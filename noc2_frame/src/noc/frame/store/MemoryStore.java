package noc.frame.store;

import java.util.List;

import noc.frame.Store;
import noc.frame.model.ReferableList;
import noc.frame.model.Vo;

public class MemoryStore implements Store<Vo> {
	String type = null;
	public MemoryStore(String type) {
		this.type = type;
	}
	
	
	ReferableList<Vo> datas = new ReferableList<Vo>();
	
	@Override public void cleanup() {
		
	}

	@Override public void setup() {		
	}

	
	@Override public Vo get(String key) {
		return datas.get(key);
	}

	@Override public List<Vo> list() {
		return datas.getValues();
	}

	@Override public Vo update(Vo v) {
		datas.put(v);
		return datas.get(v.getReferID());
	}
	
	
	@Override public String toString() {
		// TODO Auto-generated method stub
		return "Mem:" + type + "[" + datas.size() +"]";
	}
	
	
}
