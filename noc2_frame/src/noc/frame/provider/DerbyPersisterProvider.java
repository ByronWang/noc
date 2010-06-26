package noc.frame.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.frame.Store;
import noc.frame.lang.Type;
import noc.frame.model.Vo;
import noc.frame.persister.TablePersister;

public class DerbyPersisterProvider extends BufferedProvider<Persister<Vo>> {

	final static String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	protected final String driverName;
	protected final String url;
	protected final String userName;
	protected final String userPassword;
	protected Connection conn;
	protected Store<Type> types = null;

	public DerbyPersisterProvider(Store<Type> types, String driverName, String url, String userName, String userPassword) {
		this.types = types;
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public DerbyPersisterProvider(Store<Type> types, String url, String userName, String userPassword) {
		this(types, DRIVER_NAME, url, userName, userPassword);
	}

	public DerbyPersisterProvider(Store<Type> types, String databaseName) {
		this(types, DRIVER_NAME, "jdbc:derby:" + databaseName + ";create=true", "user", "password");
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

	protected Persister<Vo> get(Type type, String key) {
		return new TablePersister(type, conn);
	}

	@Override protected Persister<Vo> find(String key) {
		return new TablePersister(types.get(key), conn);
	}
}
