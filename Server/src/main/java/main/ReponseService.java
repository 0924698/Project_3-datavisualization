package main;

import java.util.Collection;

public interface ReponseService {
	
	public void addResponse(Response r);
	public Collection<Response> getResponses();
	public Response getUser();
	
	
}
