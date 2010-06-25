package noc.frame.persister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.frame.model.Vo;
import noc.frame.provider.BufferedProvider;


public class DerbyPersisterProvider extends BufferedProvider<Persister<Vo>>  {

	final static String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	protected final String driverName;
	protected final String url;
	protected final String userName;
	protected final String userPassword;
	protected Connection conn;
	
	public DerbyPersisterProvider(String driverName, String url, String userName, String userPassword) {
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public DerbyPersisterProvider(String url, String userName, String userPassword) {
		this(DRIVER_NAME, url, userName, userPassword);
	}

	public DerbyPersisterProvider(String databaseName) {
		this(DRIVER_NAME, "jdbc:derby:" + databaseName + ";create=true", "test", "test");
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
