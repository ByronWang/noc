package noc.frame.dbpersister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.lang.reflect.Type;

public class DbConfiguration {

	final String databaseName;
	final String userName;
	final String userPassword;

	public DbConfiguration(String dbName, String userName, String userPassword) {
		this.databaseName = dbName;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	Connection conn = null;

	public void init() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			System.out.println("== Load the embedded driver");
			// create and connect the database named helloDB
			conn = DriverManager.getConnection("jdbc:derby:" + this.databaseName + ";create=true",
					this.userName, this.userPassword);
			System.out.println("== create and connect to " + this.databaseName);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked") public <T> Persister<T> getPersister(Class<T> t, Type de) {
		return (Persister<T>) new PersisterDBVoImp(de, conn);
	}

	public void destroy() {
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
			System.out.println("== Database disconnect");
		} catch (SQLException e) {}

		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			System.out.println("== Database shut down normally");
		} catch (SQLException se) {}
	}

	@Override protected void finalize() throws Throwable {
		this.destroy();
	}

}
