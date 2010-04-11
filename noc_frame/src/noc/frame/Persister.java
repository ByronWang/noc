package noc.frame;

import java.util.List;

public interface Persister<T> {
	
	 void prepare();

	 void drop();

//	 void insert(T value);

//	 void insert(List<T> values);

	 T update(T value);

//	 void update(List<T> values);

	 void delete(T value);

//	 void clear();

//	 void delete(List<T> values);

	T get(String key);
	
	T find(String key);

	List<T> list();

//	List<T> query(String key);

}