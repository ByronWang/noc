package util;

import java.util.Map;

import noc.frame.vo.Vo;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class VoHelper {
	public static Vo putAll(Map<?, ?> params, Vo v, Type type) {

		for (Field f : type.getFields()) {
			if (f.isArray()) {
				// TODO
			} else if (f.getType().isScala()) {
				String key = f.getName();
				if (params.containsKey(key)) {
					String value = ((String[]) params.get(key))[0];
					if (f.isKey() && v.S(key) != null) {
						if (!value.equals(v.S(key))) {
							throw new RuntimeException("Primary key not match");
						}
					} else {
						v.put(key, value);
					}
				}
			} else if (f.isInline()) {
				for (Field fin : f.getType().getFields()) {
					if (fin.getType().isScala()) {
						String key = f.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String value = ((String[]) params.get(key))[0];
							v.put(key, value);
						}
					}
				}
			} else if (f.isRefer()) {
				if (f.getType().getPrimaryKeyField().isKey()) {
					String key = f.getName() + "_" + f.getType().getPrimaryKeyField().getName();
					if (params.containsKey(key)) {
						String[] va = (String[]) params.get(key);
						v.put(key, va[0]);
					}
				} else {
					for (Field fin : type.getKeyFields()) {
						String key = f.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String[] va = (String[]) params.get(key);
							v.put(key, va[0]);
						}
					}
				}
			}
		}

		if (!type.getPrimaryKeyField().isKey()) {
			String primaryKeyNewValue = "";

			String primaryKeyName = type.getPrimaryKeyField().getName();
			String primaryKeyValue;

			for (Field f : type.getKeyFields()) {
				if (!params.containsKey(f.getName())) {
					throw new RuntimeException("Not key field value");
				}
				primaryKeyNewValue += "_" + ((String[]) params.get(f.getName()))[0];
			}
			primaryKeyNewValue = primaryKeyNewValue.substring(1);

			if (params.containsKey(primaryKeyName)
					&& !(primaryKeyValue = ((String[]) params.get(primaryKeyName))[0]).equals("")) {
				if (primaryKeyValue.equals(primaryKeyNewValue)) {
					throw new RuntimeException("Primary key not match");
				}
			} else {
				v.put(primaryKeyName, primaryKeyNewValue);
			}
		}
		return v;
	}
}
