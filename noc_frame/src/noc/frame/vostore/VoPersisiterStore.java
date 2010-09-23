package noc.frame.vostore;

import noc.frame.Factory;
import noc.frame.Persister;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

public class VoPersisiterStore extends VoStore {
	public Persister<String, Vo> persister;

	public VoPersisiterStore(Factory parent, Type clz, Persister<String, Vo> persister) {
		super(parent, clz);
		this.persister = persister;

		for (Vo v : this.persister.list()) {
			this.map.put(v.getIndentify(), new VoReadOnlyAgent(v));
		}
	}

	@Override
	public Vo readData(String key) {
		Vo t = super.readData(key);
		if (t == null) {
			t = persister.readData(key);
		}
		super.returnData(null, t);
		return super.readData(key);
	}

	@Override
	public Vo returnData(String key, Vo v) {
		super.returnData(key, v);
		return persister.returnData(key, v);
	}

	@Override
	public Vo borrowData(String key) {
		if (key == null) {
			return new VOImp(type);
		}
		return new VoAgent(this.readData(key));
	}

	@Override
	public void invalidateObject(String key) {
		// TODO
		throw new UnsupportedOperationException();
	}

}
