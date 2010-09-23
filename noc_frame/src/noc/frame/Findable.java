package noc.frame;

import java.util.HashMap;
import java.util.Map;

public abstract class Findable<K,V> {
	protected Map<K, V> items;

	public Findable() {
		items = new HashMap<K, V>();
	}

	public V get(K key) {
		V item = items.get(key);
		if (item == null) {
			item = find(key);
			if (item != null) {
				items.put(key, item);
			}
		}
		return item;
	}

	protected abstract V find(K key);

}
