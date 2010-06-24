package noc.frame.store;

import noc.frame.ReferableList;
import noc.frame.Store;
import noc.frame.Vo;

public class AbstractStore extends ReferableList<Vo> implements Store<Vo> {

	@Override public Vo update(Vo v) {
		this.put(v);
		return this.get(v.getReferID());
	}

	@Override public void cleanup() {
		
	}

	@Override public void setup() {		
	}

}
