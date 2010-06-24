package noc.run;

import noc.frame.AbstractFacadeProvider;
import noc.frame.Referable;
import noc.frame.Store;
import noc.frame.store.memory.MemoryStoreProvider;
import noc.frame.store.persistable.PersisterStoreProvider;

public class FacadeStoreProvider<V extends Referable> extends AbstractFacadeProvider<Store<V>> {

	@Override public void setup() {
		this.register(".*", new MemoryStoreProvider<V>());
		this.register(".*", new PersisterStoreProvider<V>(new FacadePersisterProvider<V>()));
		
		super.setup();
	}
}
