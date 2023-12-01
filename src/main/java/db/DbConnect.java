package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* @return このメソッドは、資格情報を使用してデータベースへの接続を試行し、接続を返します。
*/
public class DbConnect {

static Connection connection = null;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		
		final String connect_driver = "com.mysql.cj.jdbc.Driver";
		final String connect_url = "jdbc:mysql://localhost:3306/kadaidb";
		final String connect_username = "root";
		final String connect_password = "Hasibarrafiul1";
		
		try {
			Class.forName(connect_driver);
			
			connection = DriverManager.getConnection(
					connect_url,
					connect_username, connect_password);
			System.out.println("Connected");
			
		} catch(SQLException ex) {
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return connection;
		
	}
}