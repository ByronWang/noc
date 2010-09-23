package noc.frame;

import java.util.List;

public interface Store<K,V> {// extends List<T> {
	void setUp();
	void tearDown();
	V readData(K key);
	V borrowData(K key);
	V returnData(K key, V v);
	void invalidateObject(K key);
	List<V> list();
}
