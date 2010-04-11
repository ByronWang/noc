package noc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

public class TestMeta extends TestCase {
	Connection conn = null;

	@Override protected void setUp() throws Exception {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			System.out.println("== Load the embedded driver");
			Properties props = new Properties();
			props.put("user", "user1");
			props.put("password", "user1");
			// create and connect the database named helloDB
			conn = DriverManager.getConnection("jdbc:derby:nocbiz;create=true", props);
			System.out.println("== create and connect to helloDB");
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void testGetMeta() throws SQLException{
        Statement st = conn.createStatement();

//        String sqlCreate = "create table data_sales_Order( " +
//        		" id varchar(40)," +
//        		" name varchar(40)" +
//        		")";
//        st.execute(sqlCreate);
        
        String sql = "select * from data_sales_Order where 0=1";
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        int rowCount = metaData.getColumnCount();
        
        System.out.println("Table Name : " + metaData.getTableName(2));
        System.out.println("Field  \tsize\tDataType");

        Map<String, String> cols = new HashMap<String, String>();
        
        for (int i = 0; i < rowCount; i++) {
        	cols.put(metaData.getColumnName(i + 1), metaData.getColumnTypeName(i + 1));
            System.out.print(metaData.getColumnName(i + 1) + "  \t");
            System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
            System.out.println(metaData.getColumnTypeName(i + 1));
        }

        String[] fields = new String[]{"id","name","defs","dsfsads"};
        ArrayList<String> noCol = new ArrayList<String>();
        for(String f : fields){
        	if(!cols.containsKey(f.toUpperCase())){
        		noCol.add(f);
        	}
        }
        
        conn.commit();
        
        if(noCol.size()>0){
        	for(String s : noCol){
            	st.addBatch("ALTER TABLE data_sales_Order ADD COLUMN " + s + " VARCHAR(40)");
        	}
        	st.executeBatch();    
        	
        }
        
        
        sql = "select * from data_sales_Order where 0=1";
        rs = st.executeQuery(sql);
        metaData = rs.getMetaData();

        rowCount = metaData.getColumnCount();
        
        System.out.println("Table Name : " + metaData.getTableName(2));
        System.out.println("Field  \tsize\tDataType");

        //Map<String, String> cols = new HashMap<String, String>();
        
        for (int i = 0; i < rowCount; i++) {
        	//cols.put(metaData.getColumnName(i + 1), metaData.getColumnTypeName(i + 1));
            System.out.print(metaData.getColumnName(i + 1) + "  \t");
            System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
            System.out.println(metaData.getColumnTypeName(i + 1));
        }
        //ALTER TABLE distributors ADD COLUMN address VARCHAR(30); 
	}

	@Override protected void tearDown() throws Exception {try {
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
	

}
