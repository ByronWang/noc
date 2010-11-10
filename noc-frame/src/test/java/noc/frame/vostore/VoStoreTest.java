package noc.frame.vostore;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import noc.frame.dbpersister.DBVoPersister;
import noc.frame.dbpersister.SqlHelper;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;
import noc.lang.reflect.TypeReadonlyStore;

public class VoStoreTest extends TestCase {

    final String typeName = "kao.master.Product50";
    final Type type;
    final SqlHelper sqlhelper;
    final DBVoPersister persister;
    final Connection conn;
    final VoStore voStroe;

    public VoStoreTest() {
        try {

            Properties props = new Properties();
            URL url = this.getClass().getResource("/app.properties");
            props.load(url.openStream());

            // Load Type
            TypeReadonlyStore typeStore = new TypeReadonlyStore();
            typeStore.setUp();
            String definePath = props.getProperty(APP_DEFINE_PATH);
            typeStore.load(definePath);

            type = typeStore.readData(typeName);

            // Load db derby
            String db = "derby";
            String driverClass = props.getProperty(db + "." + DB_DRIVERCLASS);
            String dburl = props.getProperty(db + "." + DB_URL);
            String userName = props.getProperty(db + "." + DB_USERNAME);
            String userPassword = props.getProperty(db + "." + DB_PASSWORD);
            Class.forName(driverClass).newInstance();
            conn = DriverManager.getConnection(dburl, userName, userPassword);
            conn.setAutoCommit(false);
            sqlhelper = new SqlHelper(type);

            try {
                String sqlDrop = sqlhelper.builderDrop();
				conn.createStatement().execute(sqlDrop);
				conn.commit();
			} catch (Exception e) {
			}

            persister = new DBVoPersister(conn, type, sqlhelper);
            persister.setUp();

            voStroe = new VoPersistableStore(null, type, persister);
            voStroe.setUp();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void test_List() {
        List<Vo> lv = voStroe.list();
        assertEquals(0, lv.size());
    }

    public void testReturnData_Insert() {

        String keyName = type.getFields().get(0).getName();

        List<Vo> vl = voStroe.list();
        assertEquals(0, vl.size());

        Vo v = new VOImp(type);
        v.put(keyName, "key01");
        Vo rv = voStroe.returnData("key01", v);
        assertEquals(VoAgent.class, rv.getClass());
        assertEquals("key01", rv.get(keyName));

        Timestamp timestamp = (Timestamp) rv.get("TIMESTAMP_");
        assertTrue(timestamp.getTime() <= System.currentTimeMillis());

        vl = voStroe.list();
        assertEquals(1, vl.size());
    }

    public void testReturnData_Update() {

        String keyName = type.getFields().get(0).getName();

        List<Vo> vl = persister.list();
        assertEquals(1, vl.size());
        voStroe.setUp();

        vl = voStroe.list();
        assertEquals(1, vl.size());

        Vo v = voStroe.borrowData("key01");
        v.put(type.getFields().get(1).getName(), "update");

        Vo rv = voStroe.returnData("key01", v);
        assertEquals(VoAgent.class, rv.getClass());
        assertEquals("key01", rv.get(keyName));

        Timestamp timestamp = (Timestamp) rv.get("TIMESTAMP_");
        assertTrue(timestamp.getTime() <= System.currentTimeMillis());

        vl = voStroe.list();
        assertEquals(1, vl.size());
    }

    //
    // public void testReadData() {
    // }
    //
    //
    // public void testBorrowData() {
    // }
    //
    // public void testSetUp() {
    // }
    //
    // public void testTearDown() {
    // }
    static final String APP_DEFINE_PATH = "app_define_path";
    static final String DB_DRIVERCLASS = "driverclass";
    static final String DB_URL = "url";
    static final String DB_USERNAME = "username";
    static final String DB_PASSWORD = "password";

}
