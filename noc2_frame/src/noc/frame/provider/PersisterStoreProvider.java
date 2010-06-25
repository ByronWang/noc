package noc.frame.provider;

import noc.frame.Persister;
import noc.frame.Provider;
import noc.frame.Store;
import noc.frame.model.Vo;
import noc.frame.store.PersistableStore;

public class PersisterStoreProvider extends BufferedProvider<Store<Vo>> {
	Provider<Persister<Vo>> persisterProvider = null;

	public PersisterStoreProvider(Provider<Persister<Vo>> persisterProvider) {
		this.persisterProvider = persisterProvider;
	}

	@Override public void cleanup() {
		for(Store<Vo> store:super.items.values()){
			store.cleanup();
		}
		
		persisterProvider.cleanup();
	}

	@Override public void setup() {
		persisterProvider.setup();
	}

	@Override protected Store<Vo> find(String key) {
		PersistableStore ps = null;
		Persister<Vo> persister = persisterProvider.get(key);
		if (persister != null) {
			ps = new PersistableStore();
			ps.setPersistre(persister);
			ps.setup();
		}

		return ps;
	}

}
