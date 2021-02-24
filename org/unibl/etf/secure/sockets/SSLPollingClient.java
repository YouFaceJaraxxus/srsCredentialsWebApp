package org.unibl.etf.secure.sockets;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.net.ssl.*;

class SSLPollingClient {
	private static final String HOST = "192.168.0.25";
	private static final int PORT = 8443;

	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			System.out.println("Starting client...");
			System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "test123");

			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			Socket s = sf.createSocket(HOST, PORT);

			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			/*out.println("Connection established...");
			String line = in.readLine();
			System.out.println(line);*/
			Thread readerThread = new Thread(() -> {
				String input = "";
				try {
					do {
						input = in.readLine();
						System.out.println("Client : " + input);
					}while(input!=null&&input!="STOP");
				}catch(IOException e) {
					e.printStackTrace();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			readerThread.setDaemon(true);
			readerThread.start();
			String line = "";
			Scanner scanner = new Scanner(System.in);
			do {
				System.out.println("Enter text (getAll for all users, getSingle:username#password for single user) (STOP to exit): ");
				line = scanner.nextLine();
				out.println(line);
			}
			while (!line.equals("STOP"));
			in.close();
			out.close();
			s.close();
			scanner.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}

}

