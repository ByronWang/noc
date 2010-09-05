package util;

import java.util.Map;

import noc.frame.vo.Vo;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class VoHelper {
	public static Vo putAll(Map<?, ?> params, Vo v, Type type) {

		for (Field field : type.getFields()) {
			if (field.isArray()) {
				// TODO
			} else if (field.getRefer() == Field.Scala) {
				String key = field.getName();
				if (params.containsKey(key)) {
					String value = ((String[]) params.get(key))[0];
					if (field.isKey() && v.S(key) != null) {
						if (!value.equals(v.S(key))) {
							throw new RuntimeException("Key field cannot be modified!");
						}
					} else {
						v.put(key, value);
					}
				}
			} else if (field.getRefer() == Field.Inline) {
				for (Field fin : field.getType().getFields()) {
					if (fin.getRefer() == Field.Scala) {
						String key = field.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String value = ((String[]) params.get(key))[0];
							v.put(key, value);
						}
					}
				}
			} else if (field.getRefer() == Field.Reference) {
				// if (field.getType().getPrimaryKeyField().isKey()) {
				// String key = field.getName() + "_" +
				// field.getType().getPrimaryKeyField().getName();
				// if (params.containsKey(key)) {
				// String[] va = (String[]) params.get(key);
				// v.put(key, va[0]);
				// }
				// } else {
				for (Field fin : type.getFields()) {
					if(fin.isKey()){
						String key = field.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String[] va = (String[]) params.get(key);
							v.put(key, va[0]);
						}						
					}
				}
			}
		}
		return v;
	}
}
