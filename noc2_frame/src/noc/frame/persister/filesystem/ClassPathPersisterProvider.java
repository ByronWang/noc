package noc.frame.persister.filesystem;


public class ClassPathPersisterProvider<V> extends FileSystemPersisterProvider<V>{
	ClassLoader loader = null;
	public ClassPathPersisterProvider(){
		loader = this.getClass().getClassLoader();
	}
	
	public ClassPathPersisterProvider(Class<?> clazz){
		loader = clazz.getClassLoader();
	}

}
