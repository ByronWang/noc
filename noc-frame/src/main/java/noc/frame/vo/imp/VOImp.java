package noc.frame.vo.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import noc.frame.vo.Vo;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class VOImp implements Vo {
    final Type type;
    Map<String, Object> items = new HashMap<String, Object>();
    String[] keys;

    boolean beModified = false;
    String indentify = null;

    public VOImp(Type type) {
        this.type = type;
        ArrayList<String> kes = new ArrayList<String>();

        for (Field field : type.getFields()) {
            if (field.getImportance() == Field.PrimaryKey) {
                kes.add(field.getName());
            }
        }
        keys = kes.toArray(new String[0]);
    }

    public VOImp(Type type, String keyValue, Object... params) {
        this(type);

        for (int i = 0; i < params.length; i += 2) {
            if (params[i + 1] instanceof Object) {
                items.put((String) params[i], params[i + 1]);
            } else {
                items.put((String) params[i], params[i + 1]);
            }
        }
        beModified = true;
    }

    @Override
    public Object get(String name) {
        return items.get(name);
    }

    @Override
    public void put(String name, Object v) {
        items.put(name, v);
        beModified = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Object> entry : items.entrySet()) {
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

    // @Override public void add(Object v) {
    // throw new UnsupportedOperationException();
    // }

    @Override
    public String getCanonicalForm() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            sb.append(entry.getKey());
            sb.append(':');
            sb.append(entry.getValue());
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    @Override
    public String getIndentify() {
        if (!beModified) {
            return indentify;
        }

        switch (keys.length) {
        case 1:
            indentify = this.get(keys[0]).toString();
            break;
        case 2:
            indentify = this.get(keys[0]).toString();
            indentify += "_" + this.get(keys[1]).toString();
            break;
        case 3:
            indentify = this.get(keys[0]).toString();
            indentify += "_" + this.get(keys[1]).toString();
            indentify += "_" + this.get(keys[2]).toString();
            break;
        case 4:
            indentify = this.get(keys[0]).toString();
            indentify += "_" + this.get(keys[1]).toString();
            indentify += "_" + this.get(keys[2]).toString();
            indentify += "_" + this.get(keys[3]).toString();
            break;
        }

        beModified = false;

        return indentify;
    }

    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    // @Override public String getKeyValue() {
    // return this.keyValue;
    // }
}
