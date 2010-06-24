package noc.frame.provider;

import noc.frame.AbstractFacadeProvider;
import noc.frame.Store;
import noc.frame.Vo;

public class FacadeStoreProvider extends AbstractFacadeProvider<Store<Vo>> {

	@Override public void setup() {
		this.register(".*", new MemoryStoreProvider());
		this.register(".*", new PersisterStoreProvider(new FacadePersisterProvider()));
		
		super.setup();
	}
}
