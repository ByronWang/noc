package noc.frame.provider;

import noc.frame.Persister;
import noc.frame.Vo;

public class ClassPathPersisterProvider extends FileSystemPersisterProvider {
	ClassLoader loader = null;

	public ClassPathPersisterProvider() {
		loader = this.getClass().getClassLoader();
	}

	public ClassPathPersisterProvider(Class<?> clazz) {
		loader = clazz.getClassLoader();
	}

	@Override protected Persister<Vo> find(String key) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
