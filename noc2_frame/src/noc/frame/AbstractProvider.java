package noc.frame;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProvider<T> implements Provider<T> {
	protected Map<String, T> items;

	public AbstractProvider() {
		items = new HashMap<String, T>();
	}

	public T get(String key) {
		T item = items.get(key);
		if (item == null) {
			item = find(key);
			if (item != null) {
				items.put(key, item);
			}
		}
		return item;
	}

	protected abstract T find(String key);

	@Override public void cleanup() {
	}

	@Override public void setup() {
	}

}
