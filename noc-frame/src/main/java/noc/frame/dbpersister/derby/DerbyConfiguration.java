package noc.frame.dbpersister.derby;

import java.sql.DriverManager;
import java.sql.SQLException;

import noc.frame.Persister;
import noc.frame.dbpersister.DBVoPersister;
import noc.frame.dbpersister.DbConfiguration;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DerbyConfiguration extends DbConfiguration {
	private static final Log log = LogFactory.getLog(DerbyConfiguration.class);

	public DerbyConfiguration(String driverClass, String url, String userName, String password) {
		super(driverClass, url, userName, password);
	}

	@SuppressWarnings("unchecked")
	public <T> Persister<String, T> getPersister(Class<T> t, Type type) {
		log.debug("== getPersister : " + type.getName());
		return (Persister<String, T>) new DBVoPersister(conn, type, new DerbySQLHelper(type));
	}

	public void shutdown() {
		super.shutdown();
		
		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			log.debug("== Database shut down normally");
		} catch (SQLException se) {
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.shutdown();
	}

}
