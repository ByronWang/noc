package noc.frame.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.frame.Vo;
import noc.frame.persister.db.TablePersister;

public abstract class DbPersisterProvider extends AbstractPersisterProvider {

	protected final String driverName;
	protected final String url;// "jdbc:derby:" + this.databaseName +
	// ";create=true"
	protected final String userName;
	protected final String userPassword;

	protected Connection conn;

	public DbPersisterProvider(String driverName, String url, String userName, String userPassword) {
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	@Override public void setup() {
		try {
			Class.forName(driverName).newInstance();
			System.out.println("== Load the driver");
			conn = DriverManager.getConnection(this.url, this.userName, this.userPassword);
			System.out.println("== create and connect by " + this.url);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override public void cleanup() {
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
			System.out.println("== Database disconnect");
		} catch (SQLException e) {
		}

		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			System.out.println("== Database shut down normally");
		} catch (SQLException se) {
		}
	}

	@Override protected Persister<Vo> find(String key) {
		return new TablePersister(null, conn);
	}

}
