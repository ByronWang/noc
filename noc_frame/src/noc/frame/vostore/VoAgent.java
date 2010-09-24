package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.V;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VScalarImp;

public class VoAgent implements Vo {
	protected Map<String, V> changedData = new HashMap<String, V>();
	protected Vo source = null;
	protected boolean beModified = false;

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
	public void put(String name, Object v) {
		V oldValue = this.source.get(name);
		
		if (v instanceof V) {
			if (oldValue != v) {
				changedData.put(name, (V) v);
				beModified = true;
			}
		} else if(oldValue !=null){
			if (!oldValue.toString().equals(v)) {
				changedData.put(name, new VScalarImp(v));
				beModified = true;
			}
		}
		
//		changed = this.changedData.size() < 10;//TODO Delete  add for test				
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
		return beModified;
	}

}
