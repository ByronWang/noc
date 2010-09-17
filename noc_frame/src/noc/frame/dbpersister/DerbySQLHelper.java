package noc.frame.dbpersister;

import java.util.ArrayList;
import java.util.List;

import noc.lang.reflect.Field;
import noc.lang.reflect.Type;

public class DerbySQLHelper {

	Type clz;

	class Column {
		Column(String name, boolean key) {
			this.name = name;
			this.key = key;
		}

		String name;
		boolean key;
	}

	final Column[] columns;
	final Column[] keyColumns;
	final String tableName;
	final String fieldlist_comma;
	final String fieldlist_questions;
	final String wherekeys;

	DerbySQLHelper(Type type) {
		try {
			this.clz = type;

			tableName = decodeTypeName(type.getName());

			List<Field> fl = type.getFields();

			ArrayList<Column> fs = new ArrayList<Column>();
			for (Field f : fl) {
				if (f.isArray()) {
					// TODO
				} else if (f.getRefer().equals(Field.Scala)) {
					fs.add(new Column(decodeFieldName(type.getName(), f.getName()),
							f.getImportance() == Field.PrimaryKey));
				} else if (f.getRefer().equals(Field.Inline)) {
					// TODO fs.add(f.getName() + "_" +
					// f.getType().getKeyField().getName());
				} else if (f.getRefer().equals(Field.Reference)) {
					for (Field referField : f.getType().getFields()) {
						if (referField.getImportance() == Field.PrimaryKey) {
							fs.add(new Column(decodeFieldName(type.getName(), f.getName()) + "_"
									+ decodeFieldName(f.getType().getName(), referField.getName()),
									f.getImportance() == Field.PrimaryKey));
						}
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			StringBuilder sbq = new StringBuilder();
			ArrayList<Column> kfs = new ArrayList<Column>();
			String sql = "";

			for (Column column : fs) {
				sb.append(column.name);
				sb.append(',');
				sbq.append("?,");

				if (column.key) {
					kfs.add(column);
					sql += column.name + " = ? and ";
				}
			}

			this.keyColumns = kfs.toArray(new Column[0]);
			this.wherekeys = sql.substring(0, sql.length() - 4);

			this.columns = fs.toArray(new Column[0]);
			this.fieldlist_comma = sb.substring(0, sb.length() - 1);
			this.fieldlist_questions = sbq.substring(0, sbq.length() - 1);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String decodeFieldName(String typeName, String fieldName) {
		return fieldName;
	}

	protected String decodeTypeName(String typeName) {
		return typeName.replace('.', '_');
	}

	public String getTableName() {
		return this.tableName;
	}

	public static DerbySQLHelper builder(Type clz) {
		return new DerbySQLHelper(clz);
	}

	public String builderCount() {
		return "SELECT count(*) FROM " + this.tableName + " ";
	}

	public String builderList() {
		return "SELECT " + this.fieldlist_comma + " FROM " + this.tableName + " ";
	}

	public String builderDrop() {
		return "DROP TABLE " + this.tableName + " ";
	}

	public String builderGetMeta() {
		return "SELECT * FROM " + this.tableName + " WHERE 0=1";
	}

	public String builderCreate() {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE ").append(this.tableName).append("(");

		for (Column column : this.columns) {
			sb.append(column.name).append(" varchar(40)").append(",");
		}
		sb.setCharAt(sb.length() - 1, ')');

		return sb.toString();

	}

	public String builderInsert() {
		return "INSERT INTO  " + this.tableName + "(" + fieldlist_comma + ") values(" + fieldlist_questions + ")";

	}

	public String builderUpdate() {
		String sb = "UPDATE " + this.tableName + " SET ";

		for (Column column : this.columns) {
			sb += column.name + " = ? ,";
		}
		sb = sb.substring(0, sb.length() - 1);

		sb += "WHERE " + wherekeys;
		return sb.toString();
	}

	public String builderDelete() {
		return "DELETE FROM " + this.tableName + " WHERE " + wherekeys;
	}

	public String builderGet() {
		return "SELECT " + fieldlist_comma + "  FROM " + this.tableName + " WHERE " + wherekeys;
	}

	public Column[] builderColumns() {
		return this.columns;
	}
}
