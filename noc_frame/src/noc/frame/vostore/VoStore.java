package noc.frame.vostore;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import noc.frame.Factory;
import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

public class VoStore implements Store<String,Vo> {
	Map<String, Vo> items ;
	protected Factory parent;
	protected Type type;
//	protected String keyPropName;

	public VoStore(Factory parent, Type type) {
		this.parent = parent;
		this.type = type;
	}

	@Override public Vo readData(String key) {
		return items.get(key);
	}

	@Override public Vo returnData(String key, Vo v) {
		VoAgent vo = (VoAgent)v;
		if(vo.isBeModified()){
			items.put(key, v);
		}
		return items.get(key);
	}

	@Override public List<Vo> list() {
		return new ArrayList<Vo>(items.values());
	}

	@Override
	public Vo borrowData(String key) {
		if (key == null) {
			return new VOImp(type);
		}
		return new VoAgent(this.readData(key));
	}

	@Override
	public void setUp() {
		items = new Hashtable<String, Vo>();
	}

	@Override
	public void tearDown() {
		items = null;
	}
}
