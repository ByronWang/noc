package noc.frame.vo;

import java.util.Iterator;

public interface Vo  extends V{
	void put(String name,V value);
	void put(String name,String value);
	V get(String name);
	String S(String name);
	Vo O(String name);
	Vol L(String name); 
	
	Iterator<VOEntry> iterator();
	
	int size();
	Iterable<String> keys();
	Iterable<V> values();
	
	
	//void add(V v);
}
