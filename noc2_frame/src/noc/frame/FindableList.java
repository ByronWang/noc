package noc.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FindableList<K, V> {
	ArrayList<Entry<K, V>> entries = new ArrayList<Entry<K, V>>();

	public void put(K key, V value) {
		for (Entry<K, V> entry : entries) {
			FindableListEntry<K, V> fEntry = (FindableListEntry<K, V>) entry;
			if (fEntry.key.equals(key)) {
				fEntry.value = value;
				return;
			}
		}

		entries.add(new FindableListEntry<K, V>(key, value));
	}

	public V get(K key) {
		for (Entry<K, V> entry : entries) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public boolean exist(K key) {
		for (Entry<K, V> entry : entries) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public List<Entry<K, V>> getEntries() {
		return (List<Entry<K, V>>) this.entries;
	}

	public List<K> getKeys() {
		ArrayList<K> keys = new ArrayList<K>();
		for (Entry<K, V> entry : entries) {
			keys.add(entry.getKey());
		}
		return keys;
	}

	public List<V> getValues() {
		ArrayList<V> values = new ArrayList<V>();
		for (Entry<K, V> entry : entries) {
			values.add(entry.getValue());
		}
		return values;
	}

	public int size() {
		return entries.size();
	}

	class FindableListEntry<KK, VV> implements Map.Entry<KK, VV> {

		KK key;
		VV value;

		public KK getKey() {
			return key;
		}

		public VV getValue() {
			return value;
		}

		FindableListEntry(KK key, VV value) {
			this.key = key;
			this.value = value;
		}

		@Override public VV setValue(VV value) {
			this.value = value;
			return value;
		}
	}
}
