package httpd.resource;

import noc.frame.dbpersister.DbColumn;
import noc.frame.dbpersister.SqlHelper;
import noc.lang.reflect.Type;

public class SqlHelperWithoutKey extends SqlHelper {


	public SqlHelperWithoutKey(Type type) {
	    super(type);
	}

	@Override
	public String builderCreate() {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE ").append(this.tableName).append("(");

		for (DbColumn column : this.columns) {
			sb.append(column.name).append(" varchar(40)").append(",");
		}
		sb.append("TIMESTAMP_").append(" TIMESTAMP");
		sb.append(")");

		return sb.toString();

	}
}
