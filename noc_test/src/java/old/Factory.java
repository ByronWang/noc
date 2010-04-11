package old;

import noc.frame.Store;

public abstract class Factory {
	public Factory(){
		
	}
	public abstract <T> Store<T>  getStore(Class<T> clazz);
	public abstract <T> Store<T> getTimeSequenceStore(Class<T> clazz);
	
	static String config = "noc.model.Person";
	
	static ThreadLocal<StoreFactory> uniqueFact = new ThreadLocal<StoreFactory>(){
		@Override protected StoreFactory initialValue() {
			return new StoreFactory(config);
		}		
	};
	
	public static Factory instance(){
		return uniqueFact.get(); 
	}

	
}
