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

	protected Type clz;
	protected String keyName;
//	protected String keyPropName;

	public VoStore(Type clz) {
		this.clz = clz;
		keyName = clz.getKeyField().getName();
		if(keyName==null){
			keyName = "primaryKey";
		}
	}

	@Override public Vo get(String key) {
		return map.get(key);
	}

	@Override public Vo put(Vo v) {
		String keyValue = v.S(keyName);
		map.put(keyValue, v);
		return map.get(keyValue);
	}

	@Override public List<Vo> list() {
		ArrayList<Vo> values = new ArrayList<Vo>();
		for(Vo v : map.values()){
			values.add(v);
		}
		return values;
	}
}
