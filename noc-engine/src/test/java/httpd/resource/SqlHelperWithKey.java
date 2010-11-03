package httpd.resource;

import noc.frame.dbpersister.DbColumn;
import noc.frame.dbpersister.SqlHelper;
import noc.lang.reflect.Type;

public class SqlHelperWithKey extends SqlHelper {

	public SqlHelperWithKey(Type type) {
        super(type);
    }

	@Override
    public String builderCreate() {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE ").append(this.tableName).append("(");

        for (DbColumn column : this.columns) {
            if (column.key) {
                sb.append(column.name).append(" varchar(40) ").append(" PRIMARY KEY,");
            } else {
                sb.append(column.name).append(" varchar(40)").append(",");

            }
        }
		sb.append("TIMESTAMP_").append(" TIMESTAMP");
		sb.append(")");

		return sb.toString();

	}
}
