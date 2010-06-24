package noc.frame.store.memory;

import noc.frame.FindableList;
import noc.frame.Provider;
import noc.frame.Store;
import noc.frame.Vo;
import noc.frame.store.AbstractStore;

public class MemoryStore extends AbstractStore {
	String type = null;
	public MemoryStore(Provider<Store<Vo>> provider,String type) {
		super(provider);
		datas = new FindableList<String, Vo>();
		this.type = type;
	}

	@Override public void invalidate() {
		// TODO Auto-generated method stub		
	}

	@Override public String toString() {
		// TODO Auto-generated method stub
		return "Mem:" + type + "[" + datas.size() +"]";
	}
}
