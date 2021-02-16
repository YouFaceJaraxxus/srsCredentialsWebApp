package org.unibl.etf.secure.sockets;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class SSLServerSocket {
	private static final int PORT = 8443;
	public static HashMap<String, PrintWriter> socketMap = new HashMap<>();
	public static HashMap<String, String> loginsMap = new HashMap<>();
	public static HashMap<String, String> registrationsMap = new HashMap<>();
	public static HashMap<String, String> secondLoginsMap = new HashMap<>();
	
	public static void addLogin(String username, String password) {
		loginsMap.put(username, password);
	}
	
	public static void addSecondLogin(String username, String code) {
		loginsMap.put(username, code);
	}
	
	public static void addRegistration(String username, String email) {
		registrationsMap.put(username, email);
	}
	
	public static Map<String,String> popMap(HashMap<String, String> map) {
		Map<String, String> mapCopy = new HashMap<String, String>(map);
		map.clear();
		return mapCopy;
	}
	
	public static Map<String,String> getMap(HashMap<String, String> map) {
		Map<String, String> mapCopy = new HashMap<String, String>(map);
		return mapCopy;
	}
	
	
	public static JSONArray popMapAsJSON(HashMap<String, String> map){
		JSONArray array = new JSONArray();
		for(Entry<String, String> element: popMap(map).entrySet())
		{
			//as an element would look line username=password, we need {username:password}
			JSONObject object = new JSONObject("{"+element.toString().replace("=", ":")+"}"); 
			array.put(object);
		}
		return array;
	}
	
	public static JSONArray getMapAsJSON(HashMap<String, String> map){
		JSONArray array = new JSONArray();
		for(Entry<String, String> element: getMap(map).entrySet())
		{
			//as an element would look line username=password, we need {username:password} and {email:code}
			JSONObject object = new JSONObject("{"+element.toString().replace("=", ":")+"}"); 
			array.put(object);
		}
		return array;
	}
	
	public static String popMapAsJSONString(HashMap<String, String> map) {
		return popMapAsJSON(map).toString();
	}
	
	public static String getMapAsJSONString(HashMap<String, String> map) {
		return getMapAsJSON(map).toString();
	}

	public static void main(String[] args) throws IOException {
		try {
			System.out.println("Server started...");
			System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "test123");
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ServerSocket ss = ssf.createServerSocket(PORT);
			Socket pollingSocket = ss.accept();
			new SSLServerSocketPollingThread(pollingSocket).start();
			System.out.println("Accepted connection!");
			System.out.println("Address: " + pollingSocket.getInetAddress() +":" + pollingSocket.getPort());
			System.out.println("=========================");
			while(true) {
				Socket s = ss.accept();
				new SSLServerSocketCredentialsThread(s).start();
				System.out.println("Accepted connection!");
				System.out.println("Address: " + s.getInetAddress() +":" + s.getPort());
				System.out.println("=========================");
			}
			
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}

}
 