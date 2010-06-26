package noc.frame;

public interface Provider<T> extends Openable {
	public T get(String key);

	public void register(String name, T item);
}
