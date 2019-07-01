package connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConDb {
	static Connection con;
	
	private ConDb() throws Exception {
		String url ="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String pass="123456";
		
		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, pass);
			System.out.println("Á¢¼Ó");
		 
		

	}
	public static Connection getConnection() throws Exception {
		if (con==null) {
			new ConDb();
		}
		return con;
	}

}
