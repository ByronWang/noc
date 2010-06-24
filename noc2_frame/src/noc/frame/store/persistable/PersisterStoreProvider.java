package noc.frame.store.persistable;

import noc.frame.AbstractProvider;
import noc.frame.Persister;
import noc.frame.Provider;
import noc.frame.Referable;
import noc.frame.Store;

public class PersisterStoreProvider<V extends Referable> extends AbstractProvider<Store<V>> {
	Provider<Persister<V>> persisterProvider = null;

	public PersisterStoreProvider(Provider<Persister<V>> persisterProvider) {
		this.persisterProvider = persisterProvider;
	}

	@Override public void cleanup() {
		for(Store<V> store:super.items.values()){
			store.cleanup();
		}
		
		persisterProvider.cleanup();
	}

	@Override public void setup() {
		persisterProvider.setup();
	}

	@Override protected Store<V> find(String key) {
		PersistableStore<V> ps = null;
		Persister<V> persister = persisterProvider.get(key);
		if (persister != null) {
			ps = new PersistableStore<V>(this);
			ps.setPersistre(persister);
			ps.setup();
		}

		return ps;
	}

}
