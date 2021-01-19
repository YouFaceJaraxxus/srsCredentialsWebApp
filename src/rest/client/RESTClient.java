package rest.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class RESTClient {

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client.target(getBaseURI()).path("rest").path("login");
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.get();
		String result = response.readEntity(String.class);
		System.out.println(result);
		
		invocationBuilder =  webTarget.request(MediaType.TEXT_HTML);
		response = invocationBuilder.get();
		result = response.readEntity(String.class);
		System.out.println(result);
		
		invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		result = response.readEntity(String.class);
		System.out.println(result);
	}


	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/CredentialsWebApp/").build();
	}
}
