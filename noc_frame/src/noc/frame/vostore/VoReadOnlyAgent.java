package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.V;
import noc.frame.vo.Vo;
import noc.frame.vo.Vol;

public class VoReadOnlyAgent implements Vo {
	Map<String, V> changedData = new HashMap<String, V>();
	Vo source = null;
	boolean changed = false;

	public VoReadOnlyAgent(Vo source) {
		this.source = source;
	}

	@Override
	public V get(String name) {
		if (changedData.containsKey(name)) {
			return (Vol) changedData.get(name);
		} else {
			return source.get(name);
		}
	}

	@Override
	public String getIndentify() {
		return source.getIndentify();
	}

	@Override
	public void put(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCanonicalForm() {
		return source.getCanonicalForm();
	}

}
