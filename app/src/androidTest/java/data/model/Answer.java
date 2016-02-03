package data.model;

import android.content.Context;
import android.database.Cursor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Model;

public class Answer extends Model {
	
	protected static final String TABLE_NAME = "answers";
	public static final String SQL_TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"user_id INTEGER NOT NULL," +
			"key TEXT NOT NULL," +
			"value TEXT NOT NULL," +
			"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";
	
	public Answer(Context context) {
		super(context, TABLE_NAME);
	}
	
	// SETS
	public void setUserId( int user_id ){ setValue("user_id", user_id); }
	public void setKey( String key ){ setValue("key", key); }
	public void setValue( String value ){setValue("value", value); }
	private void setCreatedAt( String created_at ){ setValue("created_at", created_at); }
	
	public void setValues( ArrayList<String> values ){
		JSONArray json = new JSONArray(values);
		setValue(json.toString());
	}
	
	// GETS
	public int getUserId(){ return (Integer) getValue("user_id"); }
	public String getKey(){ return (String) getValue("key"); }
	public String getValue(){ return (String) getValue("value"); }
	public String getCreatedAt(){ return (String) getValue("created_at"); }
	
	public boolean loadByUserIdAndKey( int user_id, String key ){
		//consulta a realizar
		String selectQuery = "SELECT * FROM :P1 WHERE user_id  = :P2 AND key  = ':P3'";
		
		// se reemplazan los parametros para realizar la consulta sobre la tabla
		// filtrando las tuplas donde la columna indicada contenga el valor recibido
		selectQuery = selectQuery.replace(":P1", table_name);
		selectQuery = selectQuery.replace(":P2", user_id+"");
		selectQuery = selectQuery.replace(":P3", key);
		
		return loadBySQL(selectQuery);
	}
	
	@Override
	public boolean isValid() {
		boolean valid = true;
		return valid;
	}

	@Override
	public void load(Cursor c) {
		setId(c.getInt(c.getColumnIndex("id")));
		setUserId(c.getInt(c.getColumnIndex("user_id")));
		setKey(c.getString(c.getColumnIndex("key")));
		setValue(c.getString(c.getColumnIndex("value")));	
		setCreatedAt(c.getString(c.getColumnIndex("created_at")));
	}
	
	@Override
	public void save(){
		String temp_value = getValue();
		if(loadByUserIdAndKey(getUserId(), getKey())){
			setValue(temp_value);
		}
		super.save();
	}

	public static CharSequence getTableName() {
		return TABLE_NAME;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.accumulate("id", getId());
		object.accumulate("user_id", getUserId());
		object.accumulate("key", getKey());
		object.accumulate("value", getValue());
		object.accumulate("created_at", getCreatedAt());
		return object;
	}

}
