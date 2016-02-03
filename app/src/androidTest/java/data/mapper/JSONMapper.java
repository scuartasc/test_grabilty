package data.mapper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.siat.pasajeros.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JSONMapper {
	private Context context;	
	public JSONMapper( Context context ){
		this.context = context;
	}
	public JSONArray readData() throws JSONException{
		ArrayList<User> users = getUsers();
		if( users == null ){
			return null;
		}
		int users_length = users.size();
		JSONArray json_users = new JSONArray();
		for( int i=0; i<users_length; i++ ){
			json_users.put(users.get(i).toJSON());
		}
		return json_users;
	}
	protected ArrayList<User> getUsers(){
		String selectQuery = "SELECT * FROM :P1 WHERE is_active=1";
		selectQuery = selectQuery.replace(":P1", User.getTableName());
		User user = new User(context);
		SQLiteDatabase db = user.getReadableDatabase();	
		Cursor c = db.rawQuery(selectQuery, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return null;
		}
		ArrayList<User> user_list = new ArrayList<User>();
		for(int i=0; i < c.getCount(); i++){
			user = new User(context);
			user.load(c);
			user_list.add(user);
			c.moveToNext();
		}
		c.close();
		db.close();
		return user_list;
	}	
}
