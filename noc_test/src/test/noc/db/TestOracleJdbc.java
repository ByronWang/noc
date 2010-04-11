package noc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestOracleJdbc {


	public static void main(String[] args) {
		try {
			Connection conn = null;

			Class.forName("oracle.jdbc.driver.OracleDriver");     
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.64.18.39:1521:dcms","dms","oval163");   
			
			

			Statement s = conn.createStatement();		
			ResultSet rs=  s.executeQuery("select count(*) from tabs");
			rs.next();
			System.out.println("select count(*) from tabs");
			System.out.println(rs.getInt(1));;
			
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
