package monumenttheft;


import java.sql.SQLException;

import main.MysqlHelper;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark.*;


public class MonumentTheft {
	//Jesse & Jessin
	
	MysqlHelper mysql;
    public MonumentTheft(){
    	try {
			mysql = new MysqlHelper();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public String getSomething(Request req, Response res){
    	return mysql.exampleQuery().toString();
    }
    
	    
}
