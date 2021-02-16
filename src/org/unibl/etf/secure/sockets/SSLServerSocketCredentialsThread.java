package org.unibl.etf.secure.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import redis.RedisApi;

public class SSLServerSocketCredentialsThread extends Thread {
	BufferedReader in;
	PrintWriter out;
	RedisApi api = new RedisApi();
	public SSLServerSocketCredentialsThread() {
		super();
	}
	
	public SSLServerSocketCredentialsThread(Socket socket) throws IOException{
		super();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}
	@Override
	public void run(){
		try {
			String line = null;
			line = in.readLine(); //the first line is the credentials in form type:key#value
			System.out.println(line);
			if(line!=null) {
				String type = line.split(":")[0];
				String[] credentials = line.split("#"); //0==key, 1==value
				if("login".equals(type)) {
					SSLServerSocket.loginsMap.put(credentials[0], credentials[1]);
				}else if ("secondLogin".equals(type)) {
					SSLServerSocket.secondLoginsMap.put(credentials[0], credentials[1]);
				}else {
					SSLServerSocket.registrationsMap.put(credentials[0], credentials[1]);
				}
				SSLServerSocket.socketMap.put(credentials[0], out);
			}
			out.println("Bounded " + line);
		} catch (IOException e) {
			System.out.println("IOException from thread run block.");
			e.printStackTrace();
			
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("IOException from thread finally block when closing.");
				e.printStackTrace();
			}
			this.out.close();
		}
		
		
	}
}
