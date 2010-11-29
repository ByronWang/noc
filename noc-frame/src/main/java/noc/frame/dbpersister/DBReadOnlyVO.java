package noc.frame.dbpersister;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import noc.frame.vo.Vo;
import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class DBReadOnlyVO implements Vo {
    final Type type;
    final Map<String, Object> map;
    final String[] realFields;

    final List<Object> data;

    final String m_Indentify;
    final String m_String;
    final String m_CanonicalForm;
    final Timestamp m_Timestamp;

    public DBReadOnlyVO(Type type, String[] realFields, List<Object> data) {
        this.type = type;
        this.map = new HashMap<String, Object>();
        this.data = data;
        this.realFields = realFields;

        ArrayList<String> primaryKeys = new ArrayList<String>();

        for (Field field : type.getFields()) {
            if (field.getImportance() == Field.PrimaryKey) {
                primaryKeys.add(field.getName());
            }
        }

        for (int i = 0; i < realFields.length; i++) {
            map.put(realFields[i], data.get(i));
        }

        m_Timestamp = (Timestamp) data.get(data.size() - 1);
        map.put("TIMESTAMP_", m_Timestamp);

        String indentify = null;

        switch (primaryKeys.size()) {
        case 1:
            indentify = this.get(primaryKeys.get(0)).toString();
            break;
        case 2:
            indentify = this.get(primaryKeys.get(0)).toString();
            indentify += "_" + this.get(primaryKeys.get(1)).toString();
            break;
        case 3:
            indentify = this.get(primaryKeys.get(0)).toString();
            indentify += "_" + this.get(primaryKeys.get(1)).toString();
            indentify += "_" + this.get(primaryKeys.get(2)).toString();
            break;
        case 4:
            indentify = this.get(primaryKeys.get(0)).toString();
            indentify += "_" + this.get(primaryKeys.get(1)).toString();
            indentify += "_" + this.get(primaryKeys.get(2)).toString();
            indentify += "_" + this.get(primaryKeys.get(3)).toString();
            break;
        }

        m_Indentify = indentify;
        m_String = this.tmpString();
        m_CanonicalForm = this.tmpCanonicalForm();
    }

    @Override
    public Object get(String name) {
        return map.get(name);
    }

    @Override
    public void put(String name, Object v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return m_String;
    }

    @Override
    public String getCanonicalForm() {
        return m_CanonicalForm;
    }

    @Override
    public String getId() {
        return m_Indentify;
    }

    private String tmpString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < realFields.length; i++) {
            sb.append(realFields[i]);
            sb.append(":");
            if (data.get(i) != null) {
                sb.append("\"");
                sb.append(data.get(i));
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

    private String tmpCanonicalForm() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < realFields.length; i++) {
            sb.append(realFields[i]);
            sb.append(':');
            sb.append(data.get(i));
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    @Override
    public Object get(int i) {
        return data.get(i);
    }

    @Override
    public int size() {
        return data.size();
    }
}
