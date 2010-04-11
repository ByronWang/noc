package noc.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Test {
	public static void main(String[] args) {
		try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("Load the embedded driver");
            Connection conn = null;
            Properties props = new Properties();
            props.put("user", "user1");  props.put("password", "user1");
           //create and connect the database named helloDB 
            conn=DriverManager.getConnection("jdbc:derby:helloDB;create=true", props);
            System.out.println("create and connect to helloDB");
            conn.setAutoCommit(false);
//			
//            DaoEmployee dao = new DaoEmployee(conn);
//			//dao.createTable();
//			
//			Employee e = new Employee();
//			e.set名称("wangshilian");
//			e.set工号("2332");
//
//			dao.insert(e);
//			
//			int max = 1500;
//			List<Employee> es = new ArrayList<Employee>(max);
//			for(int i=0;i<max;i++){
//				Employee em = new Employee();
//				em.set姓名("test_" + i);
//				em.set工号("T" + i);
//				
//				es.add(em);
//			}
//			dao.insert(es);
//
//			StringBuilder sb = new StringBuilder(100000);
//			for(Employee em :dao.list()){
//				sb.append(em.get名称());
//				sb.append(" : ");
//				sb.append(em.get工号());
//			}
//			//System.in.read();
//			
//			
//			
//			
//			
//			//e = dao.get("wangshilian");
//			
////			for(Employee em :dao.getAll()){
////				System.out.println(em.get名称() + " : " + em.get工号());
////			}
//			
//			e.set工号("9999");
//			
//			dao.update(e);
////			
////			for(Employee em :dao.getAll()){
////				System.out.println(em.get名称() + " : " + em.get工号());
////			}
//			
//			//dao.delete(e);
//			
//			//dao.dropTable();
//			
//			dao.clear();
//
//			conn.close();
//			
			
            try { // perform a clean shutdown 
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                System.out.println("Database shut down normally");
            }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
