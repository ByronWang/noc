package noc.frame.vo;

public interface Vo  extends V{
	void put(String name,Object value);
	Object get(String name);
	
	String getIndentify();
			
	//void add(V v);
}
