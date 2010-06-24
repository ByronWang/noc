package noc.frame.persister;

import java.util.ArrayList;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.Vo;

public abstract class AbstractPersister implements Persister<Vo>{
	ArrayList<Store<Vo>> refby  = new ArrayList<Store<Vo>>();
	
	public void refer(Store<Vo> store){
		refby.add(store);
	}
	
	public void invalidate(){
		for(Store<Vo> store:refby){
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
