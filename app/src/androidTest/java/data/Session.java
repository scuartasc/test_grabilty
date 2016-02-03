package data;

import android.content.ContentValues;

import com.siat.pasajeros.data.model.User;

import java.util.Date;

public class Session {
	
	private static Session instance;
	private User user;

	private long session_id;
	
	protected ContentValues session_values;
	
	private Session(){
		clearAll();
	}
	
	
	public User getUser(){
		return this.user;
	}


	public long getSessionId(){
		return this.session_id;
	}
	
	public void login( User user ){
		this.user = user;
	}
	
	public boolean isLogged(){
		return this.user!=null;
	}
	
	public void clearAll(){
		session_values = new ContentValues();
		this.user = null;
		session_id = new Date().getTime();
	}
	
	public static Session getInstance(){
		if( instance == null ){
			instance = new Session();
		}
		return instance;
	}
	
	public void setValue( String key, String value ){
		session_values.put(key, value);
	}
	
	public void setValue( String key, int value ){
		session_values.put(key, value);
	}
	
	public void setValue( String key, boolean value ){
		session_values.put(key, value);
	}
	
	public String getValueAsString(String key){
		if(!session_values.containsKey(key)) return null;
		return session_values.getAsString(key);
	}
	
	public int getValueAsInteger(String key){
		if(!session_values.containsKey(key)) return 0;
		return session_values.getAsInteger(key);
	}
	
	public boolean getValueAsBoolean(String key){
		if(!session_values.containsKey(key)) return false;
		return session_values.getAsBoolean(key);
	}
	
}
