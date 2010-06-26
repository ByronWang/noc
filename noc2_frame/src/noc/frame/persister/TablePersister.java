package noc.frame.persister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import noc.frame.Persister;
import noc.frame.lang.Type;
import noc.frame.model.FreeVo;
import noc.frame.model.Vo;
import noc.frame.persister.db.derby.DerbySQLHelper;

public class TablePersister implements Persister<Vo> {
	private final Connection conn;

	final Type type;
	final String tableName;
	final String SQL_DROP;
	final String SQL_CREATE;

	final String SQL_GET;
	final String SQL_GETMETA;
	final String SQL_INSERT;
	final String SQL_UPDATE;
	final String SQL_DELETE;
	final String SQL_LIST;
	final String SQL_COUNT;

	final String KEY_FIELD;

	final String[] fields;
	DerbySQLHelper builder;

	SimpleHelper<Vo> helper = new SimpleHelper<Vo>() {

		@Override int fillParameter(PreparedStatement prepareStatement, Vo v) throws SQLException {
			int i = 0;
			for (; i < fields.length; i++) {
				prepareStatement.setString(i + 1, v.getString(fields[i]));
			}
			return i;
		}

		@Override Vo fillObject(ResultSet resultSet) throws SQLException {
			FreeVo v = new FreeVo();
			for (int i = 0; i < fields.length; i++) {
				v.put(fields[i], resultSet.getString(i + 1));
			}
			return v;
		}
	};

	public TablePersister(Type type, Connection conn) {
		this.type = type;
		this.conn = conn;

		builder = DerbySQLHelper.builder(type);
		this.tableName = builder.getTableName();

		SQL_DROP = builder.builderDrop();
		SQL_CREATE = builder.builderCreate();

		SQL_GET = builder.builderGet();
		SQL_GETMETA = builder.builderGetMeta();
		SQL_INSERT = builder.builderInsert();
		SQL_UPDATE = builder.builderUpdate();
		SQL_DELETE = builder.builderDelete();

		fields = builder.builderColumns();
		SQL_COUNT = builder.builderCount();
		SQL_LIST = builder.builderList();
		KEY_FIELD = builder.getKeyField();
	}

	public void prepare() {
		try {
			Statement st = conn.createStatement();

			boolean exist = false;
			try {
				ResultSet rs = st.executeQuery(SQL_GETMETA);
				exist = true;
				ResultSetMetaData metaData = rs.getMetaData();
				int rowCount = metaData.getColumnCount();

				Map<String, String> cols = new HashMap<String, String>();
				System.out.println("== Before update column ");
				for (int i = 0; i < rowCount; i++) {
					cols.put(metaData.getColumnName(i + 1).toUpperCase(), metaData.getColumnTypeName(i + 1));
					System.out.print(metaData.getColumnName(i + 1) + "  \t");
					System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
					System.out.println(metaData.getColumnTypeName(i + 1));
				}

				ArrayList<String> noCol = new ArrayList<String>();
				for (String f : fields) {
					if (!cols.containsKey(f.toUpperCase())) {
						noCol.add(f);
					}
				}

				if (noCol.size() > 0) {
					for (String fieldName : noCol) {
						st.addBatch("ALTER TABLE " + this.tableName + " ADD COLUMN " + fieldName + " VARCHAR(40)");
					}
					st.executeBatch();

					rs = st.executeQuery(SQL_GETMETA);
					metaData = rs.getMetaData();
					rowCount = metaData.getColumnCount();
					System.out.println("== After update column ");
					for (int i = 0; i < rowCount; i++) {
						System.out.print(metaData.getColumnName(i + 1) + "  \t");
						System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
						System.out.println(metaData.getColumnTypeName(i + 1));
					}
				}
				conn.commit();
			} catch (SQLException e) {
			}

			if (!exist) {
				st.execute(SQL_CREATE);
			}
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override public Vo update(Vo value) {
		String key = ((Vo) value).getString(KEY_FIELD);
		Vo v = this.get(key);

		if (v == null) {
			this.doInsert(value);
		} else {
			this.doUpdate(value);
		}
		return (Vo) this.get(key);
	}

	protected void doUpdate(Vo value) {
		helper.execute(conn, SQL_UPDATE, value, value.getReferID());
	}

	protected void doInsert(Vo value) {
		helper.execute(conn, SQL_INSERT, value);
	}

	public void delete(Vo value) {
		helper.execute(conn, SQL_DELETE, value, value.getReferID());
	}

	@Override public Vo get(String key) {
		return helper.get(conn, SQL_GET, key);
	}

	@Override public List<Vo> list() {
		return helper.query(conn, SQL_LIST);
	}

	@Override public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override public void setup() {
		// TODO Auto-generated method stub

	}

}
