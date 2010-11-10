package noc.frame.dbpersister.oracle;

import noc.frame.Persister;
import noc.frame.dbpersister.DBVoPersister;
import noc.frame.dbpersister.DbConfiguration;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OracleConfiguration extends DbConfiguration {
    private static final Log log = LogFactory.getLog(OracleConfiguration.class);

    public OracleConfiguration(String driverClass, String url, String userName, String password) {
        super(driverClass, url, userName, password);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Persister<String, T> getPersister(Class<T> t, Type type) {
        log.debug("== getPersister : " + type.getName());
        return (Persister<String, T>) new DBVoPersister(conn, type, new OracleSQLHelper(type));
    }
}
