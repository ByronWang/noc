package noc.frame.vostore;

import noc.frame.Persister;
import noc.frame.vo.Vo;
import noc.lang.reflect.Type;

public class VoPersisiterStore extends VoStore {
	public Persister<Vo> persister;

	public VoPersisiterStore(Type clz, Persister<Vo> persister) {
		super(clz);
		this.persister = persister;
		
		for(Vo v: this.persister.list()){
			this.map.put(v.S(keyName), v);
		}
	}

	@Override public Vo get(String key) {
		Vo t = super.get(key);
		if (t == null) {
			t = persister.get(key);
		}
		super.update(t);
		return super.get(key);
	}

	@Override public Vo update(Vo v) {
		super.update(v);
		return persister.update(v);
	}

}