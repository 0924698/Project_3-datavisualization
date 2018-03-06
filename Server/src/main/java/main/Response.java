package main;

public class Response {
	private int id;
	private String content;
	
	
	public Response(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
