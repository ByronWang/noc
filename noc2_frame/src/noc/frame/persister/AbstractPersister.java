package noc.frame.persister;

import java.util.ArrayList;

import noc.frame.Persister;
import noc.frame.Store;

public abstract class AbstractPersister<T> implements Persister<T>{
	ArrayList<Store<T>> refby  = new ArrayList<Store<T>>();
	
	public void refer(Store<T> store){
		refby.add(store);
	}
	
	public void invalidate(){
		for(Store<T> store:refby){
			store.invalidate();
		}
	}

	@Override public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override public void setup() {
		// TODO Auto-generated method stub
		
	}
	
}
