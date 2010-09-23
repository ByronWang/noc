package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.V;
import noc.frame.vo.VScalar;
import noc.frame.vo.Vo;
import noc.frame.vo.Vol;
import noc.frame.vo.imp.VScalarImp;

public class VoAgent implements Vo {
	Map<String, V> changedData = new HashMap<String, V>();
	Vo source = null;
	boolean changed = false;

	public VoAgent(Vo source) {
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
	public void put(String name, V value) {
		V oldValue = this.source.get(name);
		if (oldValue != value) {
			changedData.put(name, new VScalarImp(value));
			changed = true;
		}
	}

	@Override
	public void put(String name, String value) {
		VScalar oldValue = (VScalar) this.source.get(name);
		if (oldValue != null) {
			if (!oldValue.equals(value)) {
				changedData.put(name, new VScalarImp(value));
				changed = true;
			}
		}
	}

	@Override
	public String getCanonicalForm() {
		// TODO Auto-generated method stub
		return null;
	}

}
