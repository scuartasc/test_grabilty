package data.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.scc.testgrability.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import data.Session;
import model.Model;

public class User extends Model {
	
	protected static final String TABLE_NAME = "users";
	public static final String SQL_TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"is_active INTEGER DEFAULT 0 NOT NULL," +
			"first_name TEXT NOT NULL," +
			"last_name TEXT DEFAULT NULL," +
			"document TEXT UNIQUE NOT NULL," +
			"birth_date DATE DEFAULT NULL," +
			"phone_number TEXT DEFAULT NULL," +
			"municipally TEXT DEFAULT NULL," +
			"timeRegistre INTEGER NOT NULL,"+
			"created_at TEXT DEFAUTL NOT NULL);";
	
	public User(Context context) {
		super(context, TABLE_NAME);
	}
	
	// SETS
	public void setActive( boolean active ){ int int_active = active? 1 : 0; setValue("is_active", int_active); }
	private void setActiveByInt( int int_active ){ setValue("is_active", int_active); }
	public void setFirstName( String first_name ){ setValue("first_name", first_name); }
	public void setLastName( String last_name ){ setValue("last_name", last_name); }
	public void setDocument( String document ){ setValue("document", document); }
	public void setBirthDate( String birth_date ){ setValue("birth_date", birth_date); }
	public void setPhoneNumber( String phone_number ){ setValue("phone_number", phone_number); }
	public void setCreatedAt( String created_at ){ setValue("created_at", created_at); }
	public void setTimeRegistr(String time){ setValue("timeRegistre", time);}
	
	
	// GETS
	public boolean getActive(){ int int_active = (Integer) getValue("is_active"); return int_active==1; }
	public String getFirstName(){ return (String) getValue("first_name"); }
	public String getLatName(){ return (String) getValue("last_name"); }
	public String getDocument(){ return (String) getValue("document"); }
	public String getBirthDate(){ return (String) getValue("birth_date"); }
	public String getPhoneNumber(){ return (String) getValue("phone_number"); }
	public String getName(){ return getFirstName()+" "+getLatName(); }
	public String getCreatedAt(){ return (String) getValue("created_at"); }
	public String getTimeRegistre(){return (String)getValue("timeRegistre");}
	
	public String getPicturePath(){ 
		if( getId() == -1 ){
			return null;
		}
		return getPictureFolderPath()+getId()+".jpg";
	}
	
	
	
	
	
	public int getStarsCounter(){
		int stars_counter = 0;
		String selectQuery = "SELECT * FROM :P1 WHERE user_id  = :P2";
		
		// se reemplazan los parametros para realizar la consulta sobre la tabla
		// filtrando las duplas donde la columna indicada contenga el valor recibido

		selectQuery = selectQuery.replace(":P2", this.getId()+"");
		
		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(selectQuery, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return 0;
		}
		for(int i=0; i < c.getCount(); i++){
			stars_counter ++;
			c.moveToNext();
		}
		c.close();
		db.close();
		return stars_counter;
	}
	
	public String getPictureFolderPath(){
		if( this.getId() == -1 ){
			return null;
		}
		String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/users_pictures/";
		File newdir = new File(dir);
        newdir.mkdirs();
        return dir;
	}
	
	public static String getTempPictureFolderPath(){
		String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/users_pictures/temp/";
		File newdir = new File(dir);
        newdir.mkdirs();
        return dir;
	}
	
	public static String getTempPicturePath(){
		return getTempPictureFolderPath() + Session.getInstance().getSessionId()+".jpg";
	}
	
	@Override
	public boolean isValid() {
		boolean valid = true;
		error_messages.clear();
		if( getFirstName()==null || getFirstName().length()==0 ){
			error_messages.add(getContext().getString(R.string.first_name_erro));
			valid = false;
		}
		if( getDocument()==null || getDocument().length()==0 ){
			error_messages.add(getContext().getString(R.string.document_erro));
			valid = false;
		}else{
			User temp_user = new User(getContext());
			if( temp_user.loadBy("document", getDocument()) && temp_user.id != this.getId() ){
				error_messages.add(getContext().getString(R.string.document_erro));
				valid = false;
			}
		}
		String picture_path = getId()==-1?getTempPicturePath():getPicturePath();
		File picture_file = new File(picture_path);
		if( !picture_file.exists() ){
			error_messages.add(getContext().getString(R.string.photo_erro));
			valid = false;
		}
		return valid;
	}

	@Override
	public void load(Cursor c) {
		setId(c.getInt(c.getColumnIndex("id")));
		setActiveByInt(c.getInt(c.getColumnIndex("is_active")));
		setFirstName(c.getString(c.getColumnIndex("first_name")));
		setLastName(c.getString(c.getColumnIndex("last_name")));
		setDocument(c.getString(c.getColumnIndex("document")));
		setBirthDate(c.getString(c.getColumnIndex("birth_date")));
		setPhoneNumber(c.getString(c.getColumnIndex("phone_number")));
		setTimeRegistr(c.getString(c.getColumnIndex("timeRegistre")));
		setCreatedAt(c.getString(c.getColumnIndex("created_at")));
	}
	
	@Override
	public void create(){
		super.create();
		String picture_from_path = getTempPicturePath();
		String picture_to_path = getPicturePath();
		File picture_from_file = new File(picture_from_path);
		File picture_to_file = new File(picture_to_path);
		picture_from_file.renameTo(picture_to_file);
	}

	public static CharSequence getTableName() {
		return TABLE_NAME;
	}
	
	public JSONArray getAchievementsJSON() throws JSONException{
		String selectQuery = "SELECT * FROM :P1 WHERE user_id = :P2";
		selectQuery = selectQuery.replace(":P1", Achievement.getTableName());
		selectQuery = selectQuery.replace(":P2", this.getId()+"");
		
		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(selectQuery, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return null;
		}
		JSONArray model_list = new JSONArray();
		Achievement model;
		for(int i=0; i < c.getCount(); i++){
			model = new Achievement(getContext());
			model.load(c);
			model_list.put(model.toJSON());
			c.moveToNext();
		}
		c.close();
		db.close();
		return model_list;
	}

	public JSONArray getUserDataJSON() throws JSONException{
		String selectQuery = "SELECT * FROM :P1 WHERE user_id = :P2";
		selectQuery = selectQuery.replace(":P1", UserData.getTableName());
		selectQuery = selectQuery.replace(":P2", this.getId()+"");
		
		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(selectQuery, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return null;
		}
		JSONArray model_list = new JSONArray();
		UserData model;
		for(int i=0; i < c.getCount(); i++){
			model = new UserData(getContext());
			model.load(c);
			model_list.put(model.toJSON());
			c.moveToNext();
		}
		c.close();
		db.close();
		return model_list;
	}


	// JSON enviado
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.accumulate("id", getId());
		object.accumulate("first_name", getFirstName());
		object.accumulate("last_name", getLatName());
		object.accumulate("document", getDocument());
		object.accumulate("birth_date", getBirthDate());
		object.accumulate("phone_number", getPhoneNumber());
		object.accumulate("timeRegistre", getTimeRegistre());
		object.accumulate("created_at", getCreatedAt());
		object.accumulate("achievements", getAchievementsJSON());
		object.accumulate("user_data", getUserDataJSON());
		return object;
	}

}
