package monumenttheft;


import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark.*;


public class MonumentTheft {
	//Jesse & Jessin
	

    public MonumentTheft(){
    	
    }
    
    
    public String getSomething(Request req, Response res){
    	return "req: " + req.headers() + "    " + req.ip();
    }
    
	    
}