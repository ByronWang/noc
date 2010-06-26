package noc.frame.model;

import java.util.List;
import java.util.Map.Entry;

public class FreeVo extends ListMap<String, Object> implements Vo {

	@SuppressWarnings("unchecked") 
	@Override public List<Vo> getVoList(String fieldName) {
		Object v = this.get(fieldName);
		return v != null ? (List<Vo>) v : null;
	}

	@Override public String getString(String fieldName) {
		Object v = this.get(fieldName);
		return v != null ? (String) v : null;
	}

	@Override public String[] getStringArray(String fieldName) {
		Object v = this.get(fieldName);
		return v != null ? (String[]) v : null;
	}

	@Override public Vo getVo(String fieldName) {
		Object v = this.get(fieldName);
		return v != null ? (Vo) v : null;
	}

	@Override public String getReferID() {
		return super.entries.get(0).getValue().toString();
	}
	
	@Override public String toString() {
		String s = "";
		for(Entry<String, Object> e : this.entries){
			s += e.getKey() + ":" + e.getValue() ;
			s += ", ";
		}
		
		
		return super.toString() + "{" + s.substring(0, s.length()-2) + "}";
	}
}
