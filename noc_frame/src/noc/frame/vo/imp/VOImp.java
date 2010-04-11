package noc.frame.vo.imp;

import java.util.ArrayList;
import java.util.Iterator;

import noc.frame.vo.V;
import noc.frame.vo.VOEntry;
import noc.frame.vo.VScalar;
import noc.frame.vo.Vo;
import noc.frame.vo.Vol;

public class VOImp implements Vo {
	ArrayList<VOEntry> values = new ArrayList<VOEntry>(20);

	private String keyValue = null;

	public VOImp() {
	}

	public VOImp(String keyValue, Object... params) {
		this.keyValue = keyValue;
		for (int i = 0; i < params.length; i += 2) {
			if (params[i + 1] instanceof V) {
				this.values.add(new VOEntryImp((String) params[i], (V) params[i + 1]));
			} else {
				this.values.add(new VOEntryImp((String) params[i], new VScalarImp(params[i + 1])));
			}
		}
	}

	@Override public V get(String name) {
		for (VOEntry e : values) {
			if (name.equals(e.getKey())) {
				return e.getValue();
			}
		}
		return null;
	}

	@Override public void put(String name, V v) {
		if ("name".equals(name) || "id".equals(name) || keyValue == null) {
			keyValue = name;
		}

		for (VOEntry e : values) {
			if (name.equals(e.getKey())) {
				e.setValue(v);
				return;
			}
		}
		values.add(new VOEntryImp(name, v));
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (VOEntry entry : values) {
			sb.append(entry.getKey());
			sb.append(":");
			if (entry.getValue() != null) {
				sb.append("\"");
				sb.append(entry.getValue().toString());
				sb.append("\"");
			}
			sb.append(",");
		}
		if (sb.length() > 1) {
			sb.setCharAt(sb.length() - 1, '}');
		} else {
			sb.append('}');
		}
		return sb.toString();
	}

	@Override public Vo O(String name) {
		return (Vo) this.get(name);
	}

	@Override public String S(String name) {
		VScalar v = (VScalar) this.get(name);
		return v != null ? v.getValue().toString() : null;
	}

	@Override public Vol L(String name) {
		return (Vol) this.get(name);
	}

	@Override public void add(V v) {
		throw new UnsupportedOperationException();
	}

	@Override public void put(String name, String value) {
		this.put(name, new VScalarImp(value));
	}

	@Override public Iterator<VOEntry> iterator() {
		return values.iterator();
	}

	@Override public int size() {
		return values.size();
	}

	@Override public Iterable<String> keys() {
		return new Iterable<String>() {
			@Override public Iterator<String> iterator() {
				return new Iterator<String>() {
					Iterator<VOEntry> i = values.iterator();

					@Override public boolean hasNext() {
						return i.hasNext();
					}

					@Override public String next() {
						return i.next().getKey();
					}

					@Override public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

		};

	}

	@Override public Iterable<V> values() {
		return new Iterable<V>() {
			@Override public Iterator<V> iterator() {
				return new Iterator<V>() {
					Iterator<VOEntry> i = values.iterator();

					@Override public boolean hasNext() {
						return i.hasNext();
					}

					@Override public V next() {
						return i.next().getValue();
					}

					@Override public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

		};
	}

	@Override public String getCanonicalForm() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (VOEntry entry : values) {
			sb.append(entry.getKey());
			sb.append(':');
			sb.append(entry.getValue().getCanonicalForm());
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	// @Override public String getKeyValue() {
	// return this.keyValue;
	// }
}
