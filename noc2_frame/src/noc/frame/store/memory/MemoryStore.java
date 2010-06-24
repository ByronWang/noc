package noc.frame.store.memory;

import noc.frame.store.AbstractStore;

public class MemoryStore extends AbstractStore {
	String type = null;
	public MemoryStore(String type) {
		this.type = type;
	}
	@Override public String toString() {
		// TODO Auto-generated method stub
		return "Mem:" + type + "[" + this.size() +"]";
	}
}
