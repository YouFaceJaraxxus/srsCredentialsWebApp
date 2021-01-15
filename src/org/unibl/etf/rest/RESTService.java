package org.unibl.etf.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/message")
public class RESTService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsgPlainText() {
		return "Hello World from Jersey 2";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getMsgHTML() {
		return "<p>Hello World from Jersey 2</p>";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMsgJSON() {
		return "{\"msg\": \"Hello World from Jersey 2\"}";
	}
}
