package rest;
import java.io.*;
import java.net.*;

import javax.net.ssl.*;

/**
 *Not to be mestaken for a real thread, this is just a class 
 *that wraps program code which works as a client for the server's threads
 *it waits until the request is handled by the traffic control gateway 
**/
class SSLCredentialsClient {
	private static final String HOST = "192.168.0.25";
	private static final int PORT = 8443;
	private String type;
	private String key;
	private String value;
	
	public SSLCredentialsClient() {
		super();
	}

	public SSLCredentialsClient(String type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}
	
	public String start() throws UnknownHostException, IOException {
		try {
			System.out.println("Starting client...");
			System.setProperty("javax.net.ssl.trustStore", "/home/milos2/eclipse-workspace/CredentialsWebApp/keystore.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "test123");

			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			Socket s = sf.createSocket(HOST, PORT);

			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out.println(this.type + ":" + this.key + "#" + this.value);
			String line = in.readLine();
			System.out.println(line);
			in.close();
			out.close();
			s.close();
			return line;
		}catch(Exception e) {
			e.printStackTrace();
			return "EXCEPTION_ERROR";
		}

		
	}
	
	public static void main(String args[]) {
		try {
		String result = new SSLCredentialsClient("login", "loshmee", "pass").start();
		System.out.println(result);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

