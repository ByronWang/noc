package noc.frame.model;

import java.util.List;


public interface Vo extends Referable {

	public Object get(String fieldName);
	public void put(String fieldName,Object name);
	
	public String getString(String fieldName);
	public String[] getStringArray(String fieldName);
	
	public Vo getVo(String fieldName);
	public List<Vo> getVoList(String fieldName);

}
