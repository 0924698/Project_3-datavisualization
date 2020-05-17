package main;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import bikeplacement.BikePlacement;
import bikesafe.BikeSafe;
import monumenttheft.MonumentTheft;


public class App {
	private static MonumentTheft monumentTheft = new MonumentTheft();
	
	
	
	public static void main(String[] args) {
		port(8080);
		
		options("/*",
		        (request, response) -> {

		            String accessControlRequestHeaders = request
		                    .headers("Access-Control-Request-Headers");
		            if (accessControlRequestHeaders != null) {
		                response.header("Access-Control-Allow-Headers",
		                        accessControlRequestHeaders);
		            }

		            String accessControlRequestMethod = request
		                    .headers("Access-Control-Request-Method");
		            if (accessControlRequestMethod != null) {
		                response.header("Access-Control-Allow-Methods",
		                        accessControlRequestMethod);
		            }

		            return "OK";
		        });

		before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
		
        get("/", ( request, response) -> "Homepage" );
        get("/api/monuments/all", (request, response) -> monumentTheft.getMomuments(request, response));
        get("/api/biketheft/all", (request, response) -> monumentTheft.getBikeTheft(request, response));
//       monumentTheft.updateDatabase();
    }
	
	
}
