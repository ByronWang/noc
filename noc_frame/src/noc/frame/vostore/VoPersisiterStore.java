package noc.frame.vostore;

import noc.frame.Factory;
import noc.frame.Persister;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

public class VoPersisiterStore extends VoStore {
	public Persister<String,Vo> persister;

	public VoPersisiterStore(Factory parent,Type clz, Persister<String,Vo> persister) {
		super(parent,clz);
		this.persister = persister;
		
		for(Vo v: this.persister.list()){
			this.map.put(v.getIndentify(), v);
		}
	}

	@Override public Vo readData(String key) {
		if(key == null){
			return new VOImp(type);
		}
		Vo t = super.readData(key);
		if (t == null) {
			t = persister.readData(key);
		}
		super.returnData(t);
		return super.readData(key);
	}

	@Override public Vo returnData(Vo v) {
		super.returnData(v);
		return persister.returnData(v);
	}

}