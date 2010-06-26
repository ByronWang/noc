package noc.frame.provider;

import java.util.HashMap;
import java.util.Map;

import noc.frame.Provider;

public abstract class BufferedProvider<T> implements Provider<T> {
	protected Map<String, T> items;

	public BufferedProvider() {
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

	@Override public void register(String name, T t) {
		items.put(name, t);
	}

	protected abstract T find(String key);

	@Override public void cleanup() {
	}

	@Override public void setup() {
	}

}
