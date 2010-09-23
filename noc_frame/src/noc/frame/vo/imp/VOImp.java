package noc.frame.vo.imp;

import java.util.ArrayList;
import java.util.Iterator;

import noc.frame.vo.V;
import noc.frame.vo.VOEntry;
import noc.frame.vo.VScalar;
import noc.frame.vo.Vo;
import noc.frame.vo.Vol;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class VOImp implements Vo {
	final Type type;
	ArrayList<VOEntry> values = new ArrayList<VOEntry>(20);

	public VOImp(Type type) {
		this.type = type;
	}

	public VOImp(Type type,String keyValue, Object... params) {
		this.type = type;
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
//	@Override public void add(V v) {
//		throw new UnsupportedOperationException();
//	}

	@Override public void put(String name, String value) {
		this.put(name, new VScalarImp(value));
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

	@Override
	public String getIndentify() {
		String id = "";
		for(Field field : type.getFields()){
			if(field.getImportance() == Field.PrimaryKey){
				id += this.get(field.getName()).toString() + "_";
			}
		}
		return id.substring(0,id.length()-1);
	}

	// @Override public String getKeyValue() {
	// return this.keyValue;
	// }
}
