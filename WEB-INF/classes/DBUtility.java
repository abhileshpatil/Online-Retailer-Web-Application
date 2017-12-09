import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBUtility {
    public static  Statement stmt;
	public static  Connection conn ;

        public static Connection getConnection() throws Exception {
                if (conn != null)
                        return conn;
                else {
                       final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
        final String DB_URL="jdbc:mysql://localhost:3306/OnlineRetailer";
        final String USER = "root";
        final String PASS = "Xperiazl7$";
       
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
	    } catch (Exception e) {
			System.out.println("*************ERROR in connecting mySQL DB *******************");
			
		}  
        return conn;
                }
        }
}