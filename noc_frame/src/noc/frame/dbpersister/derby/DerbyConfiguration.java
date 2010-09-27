package noc.frame.dbpersister.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.frame.dbpersister.DBVoPersister;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DerbyConfiguration {
	private static final Log log = LogFactory.getLog(DerbyConfiguration.class);

	final String url;
	final String userName;
	final String userPassword;

	public DerbyConfiguration(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.userPassword = password;
	}

	Connection conn = null;

	public void init() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			log.debug("== Load the embedded driver");
			// create and connect the database named helloDB
			conn = DriverManager.getConnection(this.url, this.userName, this.userPassword);
			log.debug("== create and connect to " + this.url);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> Persister<String, T> getPersister(Class<T> t, Type type) {
		return (Persister<String, T>) new DBVoPersister(conn, type, DerbySQLHelper.builder(type));
	}

	public void destroy() {
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
			log.debug("== Database disconnect");
		} catch (SQLException e) {
		}

		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			log.debug("== Database shut down normally");
		} catch (SQLException se) {
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.destroy();
	}

}
