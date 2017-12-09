
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartConnection
               implements ServletContextListener{
    
	MongoDBDataStoreUtilities mongo = new MongoDBDataStoreUtilities();
	MySQLDataStoreUtilities sql = new MySQLDataStoreUtilities();
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		sql.connectToMySQL();
		mongo.getConnection();
		System.out.println("______________________________________________server started");
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		
		Connection conn = sql.getConnection();
		Statement st = sql.getStatement();
		
		try {
			st.close();
		
			conn.close();
			
			mongo.destroyMongoConnection();
		} catch (Exception e) {
			System.out.println("SQL close connection Error");
		}
		
		System.out.println("________________________________________________Server destroyed");
	}

  
}