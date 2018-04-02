package bikesafe;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class BikesafeMysql {
	private String databaseURL = "localhost:3306";
	private String databaseUser = "root";
	private String databasePass = "";
	private String tableName = "Datavisualisatie";
	
	private Connection conn;
	
	public BikesafeMysql() throws SQLException {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/" + tableName + "?useUnicode=true&characterEncoding=UTF-8&user=" + databaseUser + "&password=" + databasePass;
        conn = DriverManager.getConnection(connectionUrl);
	}
}
