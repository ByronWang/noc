package noc.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class HelloJavaDB {
    public static void main(String[] args) {
        try { // load the driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("Load the embedded driver");
            Connection conn = null;
            Properties props = new Properties();
            props.put("user", "user1");  props.put("password", "user1");
           //create and connect the database named helloDB 
            conn=DriverManager.getConnection("jdbc:derby:helloDB;create=true", props);
            System.out.println("create and connect to helloDB");
            conn.setAutoCommit(false);

            // create a table and insert two records
            Statement s = conn.createStatement();
            s.execute("create table hellotable(name varchar(40), score int)");
            System.out.println("Created table hellotable");
            s.execute("insert into hellotable values('Ruth Cao', 86)");
            s.execute("insert into hellotable values ('Flora Shi', 92)");
            // list the two records
            ResultSet rs = s.executeQuery(
                "SELECT name, score FROM hellotable ORDER BY score");
            System.out.println("name\t\tscore");
            while(rs.next()) {
                StringBuilder builder = new StringBuilder(rs.getString(1));
                builder.append("\t");
                builder.append(rs.getInt(2));
                System.out.println(builder.toString());
            }
            // delete the table
            s.execute("drop table hellotable");
            System.out.println("Dropped table hellotable");
            
            rs.close();
            s.close();
            System.out.println("Closed result set and statement");
            conn.commit();
            conn.close();
            System.out.println("Committed transaction and closed connection");
            
            try { // perform a clean shutdown 
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                System.out.println("Database shut down normally");
            }
        } catch (Throwable e) {
            // handle the exception
        }
        System.out.println("SimpleApp finished");
    }
}
