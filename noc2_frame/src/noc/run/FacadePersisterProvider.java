package noc.run;

import noc.frame.AbstractFacadeProvider;
import noc.frame.Persister;
import noc.frame.Referable;
import noc.frame.persister.filesystem.ClassPathPersisterProvider;
import noc.frame.persister.filesystem.LocalFileSystemPersisterProvider;

public class FacadePersisterProvider<V extends Referable> extends AbstractFacadeProvider<Persister<V>> {
	public FacadePersisterProvider() {
	}

	@Override public void setup() {
		this.register(".*", new ClassPathPersisterProvider<V>());
		this.register(".*", new LocalFileSystemPersisterProvider<V>("d:\\"));
//		this.register(".*", new DerbyPersisterProvider("TestDb001"));
		
		super.setup();
	}
}
