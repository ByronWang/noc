package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.Vo;

public class VoAgent implements Vo {
	protected Map<String, Object> changedData = new HashMap<String, Object>();
	protected Vo source = null;
	protected boolean beModified = false;

	public VoAgent(Vo source) {
		this.source = source;
	}
	
	@Override
	public Object get(String name) {
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
		Object oldValue = this.source.get(name);
		
		if (v instanceof Object) {
			if (oldValue != v) {
				changedData.put(name, (Object) v);
				beModified = true;
			}
		} else if(oldValue !=null){
			if (!oldValue.equals(v)) {
				changedData.put(name, v);
				beModified = true;
			}
		}
		
//		changed = this.changedData.size() < 10;//TODO Delete  add for test				
	}

	@Override
	public String getCanonicalForm() {
		return changedData.toString();
	}



	public Map<String, Object> getChangedData() {
		return changedData;
	}



	public Vo getSource() {
		return source;
	}

	public boolean isBeModified() {
		return beModified;
	}

	@Override
	public Object get(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}


}
