package noc.frame.vo;

public interface Vo  extends V{
	void put(String name,V value);
	void put(String name,String value);
	V get(String name);
	
	@Deprecated
	String getIndentify();
			
	//void add(V v);
}
