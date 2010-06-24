package noc.frame;

import java.util.ArrayList;
import java.util.List;

public class ReferableList<V extends Referable> {
	List<V> entries = new ArrayList<V>();
	List<String> keys = new ArrayList<String>();

	public void put(V value) {
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getReferID().equals(value.getReferID())) {
				entries.set(i, value);
				return;
			}
		}
		entries.add(value);
		keys.add(value.getReferID());
	}

	public V get(String key) {
		for (int i = 0; i < entries.size(); i++) {
			V v = entries.get(i);
			if (v.getReferID().equals(key)) {
				return v;
			}
		}
		return null;
	}

	public boolean exist(String key) {
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getReferID().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getKeys() {
		return keys;
	}

	public List<V> getValues() {
		return entries;
	}

	public List<V> list() {
		return entries;
	}

	public int size() {
		return entries.size();
	}
}
