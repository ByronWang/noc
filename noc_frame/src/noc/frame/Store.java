package noc.frame;

import java.util.List;

public interface Store<T> {// extends List<T> {
	T get(String key);
	T update(T v);
	List<T> list();
}
