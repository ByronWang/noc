package noc.frame.dbpersister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SqlExecuteeHelper<T> {
	private static final Log log = LogFactory.getLog(SqlExecuteeHelper.class);

	T get(Connection conn, String sql, Object... keys) {
		PreparedStatement statement = null;
		ResultSet res = null;
		try {
			statement = conn.prepareStatement(sql);

			for (int i = 1; i <= keys.length; i++) {
				statement.setObject(i, keys[i - 1]);
			}

			res = statement.executeQuery();

			if (!res.next()) {
				return null;
			}

			T v = fillObject(res);

			if (res.next()) {
				throw new RuntimeException("Can not find record key:");
			}

			return v;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (res != null) res.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	List<T> query(Connection conn, String sql, Object... keys) {
		PreparedStatement statement = null;
		ResultSet res = null;
		try {

			statement = conn.prepareStatement(sql);

			for (int i = 1; i <= keys.length; i++) {
				statement.setObject(i, keys[i]);
			}

			res = statement.executeQuery();
			List<T> list = new ArrayList<T>();

			while (res.next()) {
				T v = fillObject(res);
				list.add(v);
			}

			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			log.debug("== SUCCEED When exec " + sql);
			
			try {
				if (res != null) res.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void execute(Connection conn, String sql, T v, Object... keys) {
		PreparedStatement statement = null;
		ResultSet res = null;
		try {

			statement = conn.prepareStatement(sql);

			int pos = fillParameter(statement, v);

			for (int i = 0; i < keys.length; i++) {
				statement.setObject(pos + i + 1, keys[i]);
			}

			statement.execute();

			conn.commit();

			log.debug("== SUCCEED When exec " + sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (res != null) res.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	abstract int fillParameter(PreparedStatement prepareStatement, T v) throws SQLException;

	abstract T fillObject(ResultSet result) throws SQLException;

}
