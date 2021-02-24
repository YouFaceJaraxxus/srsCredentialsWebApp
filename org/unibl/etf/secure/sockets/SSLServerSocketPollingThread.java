package org.unibl.etf.secure.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import redis.RedisApi;

public class SSLServerSocketPollingThread extends Thread {
	BufferedReader in;
	PrintWriter out;
	RedisApi api = new RedisApi();
	public SSLServerSocketPollingThread() {
		super();
	}
	
	public SSLServerSocketPollingThread(Socket socket) throws IOException{
		super();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}
	@Override
	public void run(){
		try {
			String line = null;
			do {
				try {
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				line = in.readLine();
				System.out.println(line);
				if(line!=null) {
					if(line.equals("getLogins")) {
						out.println(SSLServerSocket.getMapAsJSONString(SSLServerSocket.loginsMap));
					}else if(line.equals("getSecondLogins")) {
						out.println(SSLServerSocket.getMapAsJSONString(SSLServerSocket.secondLoginsMap));
					}else if(line.equals("getRegistrations")) {
						out.println(SSLServerSocket.getMapAsJSONString(SSLServerSocket.registrationsMap));
					}else {
						String username = line.split(":")[0];
						PrintWriter credentialOut = SSLServerSocket.socketMap.get(username);
						if (line.startsWith("goodLog")) {
							credentialOut.println("OK_LOGIN");
						}else if (line.startsWith("badLog")) {
							String reason = line.split(":")[1];
							credentialOut.println(reason);
						}else if (line.startsWith("goodReg")) {
							credentialOut.println("OK_REGISTER");
						}else if (line.startsWith("badReg")) {
							String reason = line.split(":")[1];
							credentialOut.println(reason);
						}else if (line.startsWith("goodSecLog")) {
							credentialOut.println("OK_SECOND_LOGIN");
						}else if (line.startsWith("badSecLog")) {
							String reason = line.split(":")[1];
							credentialOut.println(reason);
						}
					}
					
				}
			}while (line!=null&&!line.equals("STOP"));
			out.println("STOP");
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
