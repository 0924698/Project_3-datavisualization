package bikesafe;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import main.MysqlHelper;
import spark.Request;
import spark.Response;

public class BikeSafe {
	//Matthijs
	String base = "https://maps.googleapis.com/maps/api/geocode/json?";

	String apikey = "AIzaSyCDp_xppf5QvlzcMl5bkf9asDfstg2BO7A";
	
	MysqlHelper mysql;
    
	
	
	public BikeSafe(){
    	try {
			mysql = new MysqlHelper();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public String getSomething(Request req, Response res){
    	return mysql.exampleQuery().toString();
    }
    
    public String getFietstrommels(Request req, Response res){
    	return mysql.query("SELECT * FROM fietstrommels;").toString();
    }
    
    public String getBikeTheft(Request req, Response res){
    	return mysql.query("SELECT * FROM bikebike_theft;").toString();
    }
    
    
    /*
     * 
     * 
     * THIS IS FOR UPDATING THE TABLE, GET THE ID'S FROM THE TABLE, SEND IT TO GEOCODER, AND UDPATE THE TABLE
     * 
     */
    
    
    
    public String updateDatabase(){
    	JSONObject data = mysql.query("SELECT `ID` FROM fietstrommels WHERE lat IS NULL or '';");
    	System.out.println(data);
    	
    	JSONArray arr = data.getJSONArray("data");

    	//https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
    	
    	String ret = "";
    	for(int i = 0; i < arr.length(); i++){
    		try{
//    			System.out.println(arr.getJSONObject(i).get);

	    		int id =arr.getJSONObject(i).getInt("id");
	    		
	    		System.out.println(id);
	    		
	    		JSONArray fullData = mysql.query("SELECT * FROM fietstrommels WHERE `id`='" + id + "';").getJSONArray("data");
	    		JSONObject obj = fullData.getJSONObject(0);
	    		
	    		
	    		String url =  base + convertSpacesToPlus( "address=" + obj.getString("Straat") + obj.getString("Thv") + ", ROTTERDAM" )+ "&key=" + apikey;
	    		updateQuery(id, executePost((url)));
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	return ret;
    }	
    
    String convertSpacesToPlus(String ret){
    	String x = ret.replaceAll("\\s+","+").toLowerCase();
    	return x;
    }
    
    void updateQuery(int id, String json){
    	
    	JSONArray arr = new JSONObject(json).getJSONArray("results");    	
    	JSONObject locationObj= (JSONObject) arr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
    	System.out.println(locationObj.getFloat("lat"));
    	System.out.println(locationObj.getFloat("lng"));
    	
    	String query = "UPDATE fietstrommels"+
    					" SET Lat=" + locationObj.getFloat("lat") +
    					", `lng`="+ locationObj.getFloat("lng") + 
    					" WHERE `ID`='" + id + "';"	;
    	mysql.editQuery(query);
    }
    
    
    
    public String executePost(String targetURL) {
    	  HttpURLConnection connection = null;

    	  try {
    	    //Create connection
    	    URL url = new URL(targetURL);
    	    connection = (HttpURLConnection) url.openConnection();
    	    connection.setRequestMethod("GET");
    	    connection.setRequestProperty("Content-Type", 
    	        "application/x-www-form-urlencoded");


    	    connection.setUseCaches(false);
    	    connection.setDoOutput(true);
    	    
    	    //Send request
    	    DataOutputStream wr = new DataOutputStream (
    	        connection.getOutputStream());
    	    wr.close();

    	    //Get Response  
    	    InputStream is = connection.getInputStream();
    	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	    StringBuffer response = new StringBuffer(); // or StringBuffer if Java version 5+
    	    String line;
    	    while ((line = rd.readLine()) != null) {
    	      response.append(line);
    	      response.append('\r');
    	    }
    	    rd.close();
    	    return response.toString();
    	  } catch (Exception e) {
    	    e.printStackTrace();
    	    return null;
    	  } finally {
    	    if (connection != null) {
    	      connection.disconnect();
    	    }
    	  }
    	}
	    
}