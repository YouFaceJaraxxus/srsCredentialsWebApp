package rest;
import java.io.*;
import java.net.*;

import javax.net.ssl.*;

class SSLPollingClient {
	private static final String HOST = "192.168.0.25";
	private static final int PORT = 8443;
	private String type;
	private String key;
	private String value;
	
	public SSLPollingClient() {
		super();
	}

	public SSLPollingClient(String type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}
	
	public String start() throws UnknownHostException, IOException {
		try {
			System.out.println("Starting client...");
			System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "test123");

			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			Socket s = sf.createSocket(HOST, PORT);

			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out.println("Connection established...");
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

}

