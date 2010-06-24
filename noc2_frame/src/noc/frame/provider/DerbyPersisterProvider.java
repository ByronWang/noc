package noc.frame.provider;


public class DerbyPersisterProvider extends DbPersisterProvider {

	final static String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

	public DerbyPersisterProvider(String url, String userName, String userPassword) {
		super(DRIVER_NAME, url, userName, userPassword);
	}

	public DerbyPersisterProvider(String databaseName) {
		super(DRIVER_NAME, "jdbc:derby:" + databaseName + ";create=true", "test", "test");
	}
}
