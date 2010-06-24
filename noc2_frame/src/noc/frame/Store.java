package noc.frame;

import java.util.List;

public interface Store<V> extends Openable{
	V get(String key);
	V update(V v);
	List<V> list();
}
