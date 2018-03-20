package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;


public class MysqlHelper {
	
	private String databaseUrl = "localhost:3306";
	private String databaseUser = "root";
	private String databasePass = "jessinrodenburg";
	private String tableName = "Datavisualisatie";
	
	private Connection conn;
	
	
	
	public MysqlHelper() throws SQLException {
		String connectionUrl = "jdbc:mysql://localhost:3306/" + tableName + "?useUnicode=true&characterEncoding=UTF-8&user=" + databaseUser + "&password=" + databasePass;
        conn = DriverManager.getConnection(connectionUrl);
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	
	//example query
	
	public JSONObject query(String query){
		JSONObject obj;
        try {
			ResultSet rs = conn.prepareStatement(query).executeQuery();
			
			obj = new JSONObject
				("{success: true, data:" +  
				convertToJSON(rs).toString()
				+ "}");
			
		} catch (SQLException e) {
			obj = new JSONObject("{success: false, error: '" + e.toString() + "'}");
			e.printStackTrace();
		} catch (Exception e) {
			obj = new JSONObject("{success: false, error: '" + e.toString() + "'}");
			e.printStackTrace();
		}
        return obj;
	}

	public JSONObject exampleQuery(){
		return query("SHOW TABLES");
	}
	
	public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
            }
          jsonArray.put(obj);
        }
        return jsonArray;
    }
}
