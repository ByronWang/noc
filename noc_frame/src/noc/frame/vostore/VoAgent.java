package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.V;
import noc.frame.vo.VScalar;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VScalarImp;

public class VoAgent implements Vo {
	protected Map<String, V> changedData = new HashMap<String, V>();
	protected Vo source = null;
	protected boolean changed = false;

	public VoAgent(Vo source) {
		this.source = source;
	}
	
	@Override
	public V get(String name) {
		if (changedData.containsKey(name)) {
			return changedData.get(name);
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
			if (!oldValue.getValue().equals(value)) {
				changedData.put(name, new VScalarImp(value));
				changed = true;
			}
		}
	}

	@Override
	public String getCanonicalForm() {
		return changedData.toString();
	}



	public Map<String, V> getChangedData() {
		return changedData;
	}



	public Vo getSource() {
		return source;
	}



	public boolean isChanged() {
		return changed;
	}

}
