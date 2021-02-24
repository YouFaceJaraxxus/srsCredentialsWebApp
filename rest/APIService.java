package rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import credential.Credential;
import redis.RedisApi;

@Path("/user")
public class APIService {
	private static String SUCCESSFUL_REGISTRATION = "Succesful registration.";
	private static String SUCCESSFUL_LOGIN = "Successful login.";
	private static String USERNAME_EXISTS = "Username already exists.";
	private static String INVALID_CREDENTIALS = "Invalid credentials.";
	private static int loginRedisPort = 6379;
	private static int secondLoginRedisPort = 6380;
	private static int registerRedisPort = 6381;
	
	private RedisApi loginRedis = new RedisApi(loginRedisPort);
	private RedisApi secondLoginRedis = new RedisApi(secondLoginRedisPort);
	private RedisApi registerRedis = new RedisApi(registerRedisPort);
	
	@GET
	@Path("/plain")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsgPlainText() {
		return "Hello World from Jersey 2";
	}
	
	@GET
	@Path("/html")
	@Produces(MediaType.TEXT_HTML)
	public String getMsgHTML() {
		return "<p>Hello World from Jersey 2</p>";
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMsgJSON() {
		return loginRedis.getListAsJSONString();
	}
	

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Credential credential) {
		try {
			String username = credential.getKey();
			String email = credential.getValue();
			String result = new SSLCredentialsClient("register", username, email).start();
			if("OK_REGISTER".equals(result)) return Response.status(201).entity(credential).build();
			else return Response.status(403).entity(credential).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Credential credential) {
		try {
			String username = credential.getKey();
			String password = credential.getValue();
			String response = new SSLCredentialsClient("login", username, password).start();
			if("OK_LOGIN".equals(response)) {
				return Response.status(200).entity(response).build();
			}
			else return Response.status(401).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/secondLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response secondLogin(Credential credential) {
		try {
			String username = credential.getKey();
			String code = credential.getValue();
			String response = new SSLCredentialsClient("secondLogin", username, code).start();
			if("OK_SECOND_LOGIN".equals(response)) {
				return Response.status(200).entity(response).build();
			}
			else return Response.status(401).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	/*@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Student student, @PathParam("id") int id) {
		if (service.update(student, id)) {
			return Response.status(200).entity(student).build();
		}
		return Response.status(404).build();
	}

	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") int id) {
		if (service.remove(id)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}*/
	
	/*@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ArrayList<Student> getAll() {
		System.out.println("Get all");
		return service.getStudents();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		Student student = service.getById(id);
		if (student != null) {
			return Response.status(200).entity(student).build();
		} else {
			return Response.status(404).build();
		}
	}*/
}
