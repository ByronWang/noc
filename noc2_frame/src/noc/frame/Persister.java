package noc.frame;

import java.util.List;

public interface Persister<T> extends Openable {
//	void refer(Store<T> store);	
//	void invalidate();

	List<T> list();
	T update(T value);
	T get(String key);
}
