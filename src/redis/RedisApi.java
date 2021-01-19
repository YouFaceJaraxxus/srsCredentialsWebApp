package redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class RedisApi {
	private Jedis jedis;
	public Jedis getJedis() {
		return jedis;
	}
	
	public static RedisApi  api = new RedisApi();

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public static void main(String[] args) {
		try {
			/*RedisApi api = new RedisApi("localhost", 6379);
			api.setCredential("foo2", "bar");
			api.setCredential("foo3", "bar3");
			api.setCredential("foo2", "barrrr");
			String value = api.popCredential("foo2");
			System.out.println(value);
			
			System.out.println(api.isEmpty());
			
			System.out.println(api.popListAsJSONString());
			System.out.println(api.isEmpty());
			System.out.println(api.getJedis().keys("*"));*/
			System.out.println(api.getListAsJSONString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public RedisApi() {
		this("localhost", 6379);
	}
	
	public RedisApi(int port) {
		this("localhost", port);
	}
	public RedisApi(String ipAddress) {
		this(ipAddress, 6379);
	}
	
	public RedisApi(String ipAddress, int port) {
		super();
		this.jedis = new Jedis(ipAddress, port);
	}
	
	public void setCredential(String username, String password) {
		jedis.set(username, password);
	}
	
	public String checkLogin(String username, String password) {
		String pass = jedis.get(username);
		if(pass==null||"".equals(pass)) return "No such username";
		else if (password==null||!password.equals(pass)) return "The password is incorrect.";
		else return "Good login. Welcome.";
	}
	
	public String popCredential(String username) {
		String password = jedis.get(username);
		jedis.del(username);
		return password;
	}
	
	public Map<String,String> popList() {
		Map<String, String> credentialsCopy = new HashMap<String, String>();
		for(String key : jedis.keys("*")) {
			credentialsCopy.put(key, jedis.get(key));
		}
		jedis.flushDB();
		return credentialsCopy;
	}
	
	public Map<String,String> getList() {
		Map<String, String> credentialsCopy = new HashMap<String, String>();
		for(String key : jedis.keys("*")) {
			credentialsCopy.put(key, jedis.get(key));
		}
		return credentialsCopy;
	}
	
	public boolean isEmpty() {
		return jedis.keys("*").size()==0;
	}
	
	public JSONArray popListAsJSON(){
		JSONArray array = new JSONArray();
		for(Entry<String, String> element: popList().entrySet())
		{
			//as an element would look line username=password, we need {username:password}
			JSONObject object = new JSONObject("{"+element.toString().replace("=", ":")+"}"); 
			array.put(object);
		}
		return array;
	}
	
	public JSONArray getListAsJSON(){
		JSONArray array = new JSONArray();
		for(Entry<String, String> element: getList().entrySet())
		{
			//as an element would look line username=password, we need {username:password}
			JSONObject object = new JSONObject("{"+element.toString().replace("=", ":")+"}"); 
			array.put(object);
		}
		return array;
	}
	
	public String popListAsJSONString() {
		return popListAsJSON().toString();
	}
	
	public String getListAsJSONString() {
		return getListAsJSON().toString();
	}
	
}
