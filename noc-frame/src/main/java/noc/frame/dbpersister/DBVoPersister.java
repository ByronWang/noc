package noc.frame.dbpersister;

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
import noc.frame.vo.Vo;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBVoPersister implements Persister<String,Vo> {
	private static final Log log = LogFactory.getLog(DBVoPersister.class);

	final private Connection conn;

	final private Type type;
	final private String tableName;
//	final private String SQL_DROP;
	final private String SQL_CREATE;

	final private String SQL_GET;
	final private String SQL_GETMETA;
	final private String SQL_INSERT;
	final private String SQL_UPDATE;
	final private String SQL_DELETE;
	final private String SQL_LIST;
//	final private String SQL_COUNT;

	final private DbColumn[] columns;
	final private String[] realFields;
	final private DbColumn[] keyColumns;
	final private SqlHelper builder;
	
	final Map<String,Integer> map;
	

	final String[] systemFields = new String[]{"TIMESTAMP_"};
	SqlExecuteeHelper<Vo> helper = new SqlExecuteeHelper<Vo>() {

		@Override
		int fillParameter(PreparedStatement prepareStatement, Vo v) throws SQLException {
			int i = 0;
			for (; i < columns.length; i++) {
				prepareStatement.setString(i + 1, String.valueOf(v.get(columns[i].name)));
				log.trace((i + 1) + ": " + String.valueOf(v.get(columns[i].name)));
			}
			return i;
		}

		@Override
		Vo fillObject(ResultSet resultSet) throws SQLException {
			List<Object> data = new ArrayList<Object>(realFields.length + systemFields.length);

			int i = 0;
			for (; i < columns.length; i++) {
				data.add(resultSet.getString(i + 1));
				log.trace(columns[i].name + ": " + resultSet.getString(i + 1));
			}
			data.add(resultSet.getTimestamp(i + 1));

			return new DBReadOnlyVO(type, realFields, data);
		}
	};

	public DBVoPersister(Connection conn,Type type,SqlHelper helper) {
		this.type = type;
		this.conn = conn;

		builder = helper;
		this.tableName = builder.getTableName();

//		SQL_DROP = builder.builderDrop();
		SQL_CREATE = builder.builderCreate();

		SQL_GET = builder.builderGet();
		SQL_GETMETA = builder.builderGetMeta();
		SQL_INSERT = builder.builderInsert();
		SQL_UPDATE = builder.builderUpdate();
		SQL_DELETE = builder.builderDelete();

		columns = builder.builderColumns();
		
		realFields = new String[columns.length];
		for(int i=0;i<columns.length;i++){
			realFields[i] = columns[i].name;
		}
		
//		SQL_COUNT = builder.builderCount();
		SQL_LIST = builder.builderList();

		keyColumns = builder.getKeyColumns();
				
		Map<String,Integer> tmpMap = new HashMap<String, Integer>();
		for(int i=0;i<realFields.length;i++){
			tmpMap.put(realFields[i], i);
		}
		map = tmpMap;
	}

	public void setUp() {
		try {
			Statement st = conn.createStatement();

			boolean exist = false;
			try {
				ResultSet rs = st.executeQuery(SQL_GETMETA);
				exist = true;
				ResultSetMetaData metaData = rs.getMetaData();
				int rowCount = metaData.getColumnCount();

				Map<String, String> cols = new HashMap<String, String>();
				log.debug("== Before update column ");
				for (int i = 0; i < rowCount; i++) {
					cols.put(metaData.getColumnName(i + 1).toUpperCase(), metaData.getColumnTypeName(i + 1));
					log.debug(metaData.getColumnName(i + 1) + "  \t");
					log.debug(metaData.getColumnDisplaySize(i + 1) + "\t");
					log.debug(metaData.getColumnTypeName(i + 1));
				}

				ArrayList<String> noCol = new ArrayList<String>();
				for (DbColumn f : columns) {
					if (!cols.containsKey(f.name.toUpperCase())) {
						noCol.add(f.name);
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
					log.debug("== After update column ");
					for (int i = 0; i < rowCount; i++) {
						log.debug(metaData.getColumnName(i + 1) + "  \t");
						log.debug(metaData.getColumnDisplaySize(i + 1) + "\t");
						log.debug(metaData.getColumnTypeName(i + 1));
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

	@Override
	public Vo returnData(String key, Vo value) {
		String[] keys = new String[keyColumns.length];
		for (int i = 0; i < keyColumns.length; i++) {
			keys[i] = value.get(keyColumns[i].name).toString();
		}
		log.debug("Vo" + value.getClass().getName());
		
		Vo v = this.get((Object[])keys);

		if (v == null) {
			this.doInsert(value);
		} else {
			this.doUpdate(value, (Object[])keys);
		}
		return (Vo) this.get((Object[])keys);
	}

	protected void doUpdate(Vo value, Object... keys) {
		log.debug(SQL_UPDATE + " : " +  value);
		helper.execute(conn, SQL_UPDATE, value, keys);
	}

	protected void doInsert(Vo value) {
		log.debug(SQL_INSERT + " : " +  value);
		helper.execute(conn, SQL_INSERT, value);
	}

	public void delete(Vo value) {
		log.debug(SQL_DELETE + " : " +  value);
		helper.execute(conn, SQL_DELETE, value);
	}

	public Vo get(Object... keys) {
		log.debug(SQL_GET + " : " +  keys);
		return helper.get(conn, SQL_GET, keys);
	}

	@Override
	public List<Vo> list() {
		return helper.query(conn, SQL_LIST);
	}

	@Override
	public void tearDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public Vo borrowData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vo readData(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
