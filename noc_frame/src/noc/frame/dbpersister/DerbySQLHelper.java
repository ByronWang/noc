package noc.frame.dbpersister;

import java.util.ArrayList;
import java.util.List;

import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class DerbySQLHelper {

	Type clz;

	final String keyfield;
	final String[] columns;
	final String tableName;
	final String fieldlist_comma;
	final String fieldlist_questions;

	DerbySQLHelper(Type clz) {
		try {
			this.clz = clz;

			tableName = clz.getName().replace('.', '_');

			List<Field> fl = clz.getFields();

			ArrayList<String> fs = new ArrayList<String>();
			for (Field f : fl) {
				if (f.isArray()) {
					// TODO
				} else if (f.getType().isScala()) {
					fs.add(f.getName());
				} else if (f.isInline()) {
					// TODO fs.add(f.getName() + "_" +
					// f.getType().getKeyField().getName());
				} else if (f.isRefer()) {
					if (f.getType().getKeyField() != null) {
						fs.add(f.getName() + "_" + f.getType().getKeyField().getName());
					} else {
						fs.add(f.getName() + "_" + "key");
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			StringBuilder sbq = new StringBuilder();
			for (String fieldName : fs) {
				sb.append(fieldName);
				sb.append(',');
				sbq.append("?,");
			}

			keyfield = clz.getKeyField().getName();
			columns = fs.toArray(new String[0]);
			fieldlist_comma = sb.substring(0, sb.length() - 1);
			fieldlist_questions = sbq.substring(0, sbq.length() - 1);

			if (keyfield == null) {
				throw new RuntimeException("Can not find key");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getKeyField() {
		return this.keyfield;
	}

	public String getTableName() {
		return this.tableName;
	}

	public static DerbySQLHelper builder(Type clz) {
		return new DerbySQLHelper(clz);
	}

	public String builderCount() {
		return "select count(*) from " + this.tableName + " ";
	}

	public String builderList() {
		return "select " + this.fieldlist_comma + " from " + this.tableName + " ";
	}

	public String builderDrop() {
		return "drop table " + this.tableName + " ";
	}

	public String builderGetMeta() {
		return "select * from " + this.tableName + " where 0=1";
	}

	public String builderCreate() {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE ").append(this.tableName).append("(");

		for (String columnName : this.columns) {
			sb.append(columnName).append(" varchar(40)").append(",");
		}
		sb.setCharAt(sb.length() - 1, ')');

		return sb.toString();

	}

	public String builderInsert() {
		return "insert into  " + this.tableName + "(" + fieldlist_comma + ") values("
				+ fieldlist_questions + ")";

	}

	public String builderUpdate() {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE ").append(this.tableName).append(" SET ");

		for (String f : columns) {
			sb.append(f).append("=?").append(",");
		}
		sb.setCharAt(sb.length() - 1, ' ');

		sb.append("WHERE ").append(this.keyfield).append("=?");
		return sb.toString();
	}

	public String builderDelete() {
		return "delete from " + this.tableName + " WHERE " + this.keyfield + "=?";
	}

	public String builderGet() {
		return "select " + fieldlist_comma + "  from " + this.tableName + " WHERE " + this.keyfield
				+ "=?";
	}

	public String[] builderColumns() {
		return this.columns;
	}
}
