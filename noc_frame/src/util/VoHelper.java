package util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import noc.frame.vo.Vo;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class VoHelper {
	private static final Log log = LogFactory.getLog(VoHelper.class);
	
	public static Vo putAll(Map<?, ?> params, Vo v, Type type) {

		for (Field field : type.getFields()) {
			if (field.isArray()) {
				// TODO
			} else if (field.getRefer() == Field.Scala) {
				String key = field.getName();
				if (params.containsKey(key)) {
					String value = ((String[]) params.get(key))[0];
					if ((field.getImportance() == Field.PrimaryKey ||field.getImportance() == Field.Core )&& v.S(key) != null) {
						if (!value.equals(v.S(key))) {
							throw new RuntimeException("Key field cannot be modified!");
						}
					} else {
						v.put(key, value);
						log.debug(key + ": " + value);
					}
				}
			} else if (field.getRefer() == Field.Inline) {
				for (Field fin : field.getType().getFields()) {
					if (fin.getRefer() == Field.Scala) {
						String key = field.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String value = ((String[]) params.get(key))[0];
							v.put(key, value);
							log.debug(key + ": " + value);
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
				for (Field fin : field.getType().getFields()) {
					if(fin.getImportance() == Field.PrimaryKey || fin.getImportance() == Field.Core){
						String key = field.getName() + "_" + fin.getName();
						if (params.containsKey(key)) {
							String[] va = (String[]) params.get(key);
							v.put(key, va[0]);
							log.debug(key + ": " + va[0]);
						}						
					}
				}
			}
		}
		return v;
	}
}
