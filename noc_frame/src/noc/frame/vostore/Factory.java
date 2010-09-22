package noc.frame.vostore;


public interface Factory<T> {
	T get(String key);
}
