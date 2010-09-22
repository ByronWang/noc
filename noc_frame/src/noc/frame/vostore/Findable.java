package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

public abstract class Findable<T> {
	protected Map<String, T> items;

	public Findable() {
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

}
