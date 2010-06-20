package noc.frame;


public interface Persister<T> extends Store<T>{

	void prepare();
	void drop();


	// void insert(T value);

	// void insert(List<T> values);

	// T find(String key);

	// void update(List<T> values);

	// void delete(T value);

	// void clear();

	// void delete(List<T> values);
	// List<T> query(String key);

}
