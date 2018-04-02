package bikesafe;

import java.sql.SQLException;

import main.MysqlHelper;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark.*;

public class BikeSafe {
	//Matthijs & Mohammed
	MysqlHelper mysql;
	public BikeSafe() {
		try {
			mysql = new MysqlHelper(); 
		} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public String getSomething(Request req, Response res) {
			return mysql.exampleQuery().toString();
		}	
		public String getFietstrommels(Request req, Response res) {
			return mysql.query("SELECT Xcoord, Ycoord FROM Fietstrommels;").toString();
		}
		public String getBikeTheft(Request req, Response res) {
			return mysql.query("SELECT * FROM BikeTheft;").toString();
		}
		
	}


