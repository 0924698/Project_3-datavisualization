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
	
	public JSONArray query(String query){
		JSONArray ret = new JSONArray();
        try {
			ResultSet rs = conn.prepareStatement(query).executeQuery();
			
			ret= convertToJSON(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
	}

	public JSONArray exampleQuery(){
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
