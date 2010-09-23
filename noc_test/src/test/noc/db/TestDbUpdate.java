package noc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.TestCase;

public class TestDbUpdate extends TestCase {
	Connection conn = null;
	String tableName = "data_kao_Product3";

	int columns =50;
	int columnsUpdate = 10;
	int times = 2000;
	String sqlUpdate = "";
	String sqlCreate = "";
	String sqlInsert = "";

	@Override
	protected void setUp() throws Exception {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			//System.out.println("== Load the embedded driver");
			Properties props = new Properties();
			props.put("user", "user1");
			props.put("password", "user1");
			// create and connect the database named helloDB
			conn = DriverManager.getConnection("jdbc:derby:newnocbiz001;create=true", props);
			//System.out.println("== create and connect to helloDB");
			conn.setAutoCommit(false);

			int i;

			sqlCreate += "CREATE TABLE  " + tableName + "(";
			sqlCreate += "  code   " + " varchar(40)";
			for (i = 2; i < 2 + columns; i++) {
				sqlCreate += " ,code" + i + "  varchar(40)";
			}
			sqlCreate += ")";
			

			
			

			sqlInsert += "INSERT INTO " + tableName + " ( ";
			sqlInsert += "   code";
			for (i = 2; i < 2 + columns; i++) {
				sqlInsert += "  ,code" + i;
			}
			sqlInsert += ") values(";
			sqlInsert += "  ? ";
			for (i = 2; i < 2 + columns; i++) {
				sqlInsert += " ,? ";
			}
			sqlInsert += ")";
			
			

			conn.createStatement().execute(sqlCreate);
			conn.commit();

			PreparedStatement st = conn.prepareStatement(sqlInsert);

			st.setString(1, "codevalue");
			for (i = 2; i < 2 + columns; i++) {
				st.setString(i, "code" + i + "value");
			}

			st.execute();
			conn.commit();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void testUpdate_1() throws SQLException {
		columnsUpdate= 1;
		doRun();
	}

	public void testUpdate_10() throws SQLException {
		columnsUpdate= 10;
		doRun();
	}
	
	public void testUpdate_30() throws SQLException {
		columnsUpdate= 30;
		doRun();
	}

//	public void testUpdate_100() throws SQLException {
//		columnsUpdate= 100;
//		doRun();
//	}

//	public void testUpdate_500() throws SQLException {
//		columnsUpdate= 500;
//		doRun();
//	}

	
	private void doRun() throws SQLException {
		long start;
		int i;
		
		sqlUpdate = "";
		sqlUpdate += "UPDATE " + tableName + " SET ";
		sqlUpdate += "   code" + 2 + " = ?";
		for (i = 3; i < 2 + columnsUpdate; i++) {
			sqlUpdate += "  ,code" + i + " = ?";
		}
		sqlUpdate += "WHERE code=?";
		
		
		start = System.currentTimeMillis();
		for (int j = 0; j < times; j++) {
			PreparedStatement st = conn.prepareStatement(sqlUpdate);

			for (i = 2; i < 2 + columnsUpdate; i++) {
				st.setString(i - 1, "code" + i + "value");
			}
			st.setString(i - 1, "code");
			st.execute();
			conn.commit();
		}
		System.out.println(start + " -> " + System.currentTimeMillis() + " : " + (System.currentTimeMillis() - start));
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			String sqlDrop = "DROP TABLE " + tableName + "";
			conn.createStatement().execute(sqlDrop);
			conn.commit();
			if (conn != null) {
				conn.commit();
				conn.close();
			}
			//System.out.println("== Database disconnect");
			//System.out.println("");
		} catch (SQLException e) {
		}

		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			System.out.println("== Database shut down normally");
		} catch (SQLException se) {
		}
	}

}
