package noc.frame.provider;

import noc.frame.AbstractFacadeProvider;
import noc.frame.Persister;
import noc.frame.Vo;

public class FacadePersisterProvider extends AbstractFacadeProvider<Persister<Vo>> {
	public FacadePersisterProvider() {
	}

	@Override public void setup() {
		this.register(".*", new ClassPathPersisterProvider());
		this.register(".*", new LocalFileSystemPersisterProvider("d:\\"));
//		this.register(".*", new DerbyPersisterProvider("TestDb001"));
		
		super.setup();
	}
}
