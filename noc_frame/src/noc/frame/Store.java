package noc.frame;

import java.util.List;

public interface Store<K,V> {// extends List<T> {
	V readData(K key);
	V borrowData(K key);
	V returnData(V v);
	void invalidateObject(K key);
	List<V> list();
}
