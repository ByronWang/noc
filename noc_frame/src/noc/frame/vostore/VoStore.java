package noc.frame.vostore;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import noc.frame.Store;
import noc.frame.vo.Vo;
import noc.lang.reflect.Type;

public class VoStore implements Store<Vo> {
	Map<String, Vo> map = new Hashtable<String, Vo>();
	protected Factory parent;
	protected Type type;
//	protected String keyPropName;

	public VoStore(Factory parent, Type type) {
		this.parent = parent;
		this.type = type;
	}

	@Override public Vo get(String key) {
		return map.get(key);
	}

	@Override public Vo update(Vo v) {
		map.put(v.getIndentify(), v);
		return map.get(v.getIndentify());
	}

	@Override public List<Vo> list() {
		return new ArrayList<Vo>(map.values());
	}
}
