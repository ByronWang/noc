package noc.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.TestCase;

public class TestDerby3 extends TestCase {

	Connection conn = null;

	@Override protected void setUp() throws Exception {
		super.setUp();

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		System.out.println("Load the embedded driver");
		Properties props = new Properties();
		props.put("user", "user");
		props.put("password", "user1");
		// create and connect the database named helloDB
		conn = DriverManager.getConnection("jdbc:derby:testcreatetable;create=true", "user1",
				"user1");
		System.out.println("create and connect to helloDB");
		conn.setAutoCommit(false);
	}

	@Override protected void tearDown() throws Exception {
		conn.close();
		System.out.println("Committed transaction and closed connection");

		try { // perform a clean shutdown
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException se) {
			System.out.println("Database shut down normally");
		}
		super.tearDown();
	}

	// public void testCreateTable() throws SQLException {
	// Statement s = conn.createStatement();
	// s.execute("create table hellotable(name varchar(40), score int)");
	// conn.commit();
	// }

	public void testCreateTable1000() throws SQLException {
		Statement s = conn.createStatement();
		for (int i = 0; i < 1000; i++) {
			// s.execute("drop table hellotable" + i + " ");
			s.execute("create table hellotable" + i + " (name varchar(40), score int)");
		}
		conn.commit();
	}

	// public void testInsertRecord() throws SQLException {
	// long start;
	//
	// long max = 1000000;
	// System.out.println("==START INSERT DATA==");
	// start = System.currentTimeMillis();
	//
	// PreparedStatement p =
	// conn.prepareStatement("insert into hellotable values(?,?)");
	//
	// for (int j = 0; j < 100; j++) {
	// long count = max / 100;
	// for (int i = 0; i < count; i++) {
	// p.setString(1, "test_" + i);
	// p.setInt(2, i);
	// p.addBatch();
	// }
	// p.executeBatch();
	// }
	//
	// System.out.println("==FINISH INSERT DATA== " +
	// (System.currentTimeMillis() - start));
	// conn.commit();
	// }

	// public void testQueryRecord() throws SQLException {
	// long start;
	//
	// System.out.println("==START QUERY DATA==");
	// start = System.currentTimeMillis();
	//
	// PreparedStatement p =
	// conn.prepareStatement("select * from hellotable where name = ?");
	// p.setString(1, "test_100");
	// ResultSet rs = p.executeQuery();
	//	
	// StringBuilder builder = new StringBuilder();
	//	
	// System.out.println("==FINISH QUERY  " + (System.currentTimeMillis() -
	// start));
	//
	// int j = 0;
	//	
	// while (rs.next()) {
	// // String str = rs.getString(1);
	// // int i = rs.getInt(2);
	// builder.append(rs.getString(1));
	// builder.append("\t");
	// builder.append(rs.getInt(2));
	// j++;
	// }
	// System.out.println(builder.toString());
	// rs.close();
	// p.close();
	//	
	// System.out.println("==FINISH QUERY DATA== " + (System.currentTimeMillis()
	// - start) + "  count=" + j);
	//		
	// conn.commit();
	// }
	//
	//	

	public void testQueryRecord_N() throws SQLException {
	// long start;
	//	
	// // Statement s = conn.createStatement();
	// // s.execute("CREATE INDEX idx_hellotable_name ON hellotable(name)");
	// // s.close();
	//
	// System.out.println("==START QUERY DATA==");
	// start = System.currentTimeMillis();
	//
	// PreparedStatement p =
	// conn.prepareStatement("select * from hellotable where name = ?");
	// p.setString(1, "test_100");
	// ResultSet rs = p.executeQuery();
	//	
	// StringBuilder builder = new StringBuilder();
	//	
	// System.out.println("==FINISH QUERY  " + (System.currentTimeMillis() -
	// start));
	//
	// int j = 0;
	//	
	// while (rs.next()) {
	// // String str = rs.getString(1);
	// // int i = rs.getInt(2);
	// builder.append(rs.getString(1));
	// builder.append("\t");
	// builder.append(rs.getInt(2));
	// j++;
	// }
	// System.out.println(builder.toString());
	// rs.close();
	// p.close();
	//	
	// System.out.println("==FINISH QUERY DATA== " + (System.currentTimeMillis()
	// - start) + "  count=" + j);
	//		
	// conn.commit();
	}

}
