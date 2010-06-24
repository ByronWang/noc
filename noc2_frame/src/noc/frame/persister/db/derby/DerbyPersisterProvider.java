package noc.frame.persister.db.derby;

import noc.frame.persister.db.DbPersisterProvider;

public class DerbyPersisterProvider<V> extends DbPersisterProvider<V> {

	final static String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

	public DerbyPersisterProvider(String url, String userName, String userPassword) {
		super(DRIVER_NAME, url, userName, userPassword);
	}

	public DerbyPersisterProvider(String databaseName) {
		super(DRIVER_NAME, "jdbc:derby:" + databaseName + ";create=true", "test", "test");
	}
}
