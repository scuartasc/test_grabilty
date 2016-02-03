package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.SQLHelper;

public abstract class Model extends SQLHelper {

	protected int id = -1;
	protected ContentValues content_values;
	protected ArrayList<String> error_messages = new ArrayList<String>();

	protected void setValue( String key, String value ){
		content_values.put(key, value);
	}

	protected void setValue( String key, int value ){
		content_values.put(key, value);
	}
	
	protected void setValue( String key, boolean value ){
		content_values.put(key, value);
	}

	protected Object getValue( String key ){
		return content_values.get(key);
	}
	
	public int delete(){
		String where_clause = "id=?";
		SQLiteDatabase db = this.getWritableDatabase();
		int row_number = db.delete(table_name, where_clause, new String[] {this.getId()+""});
		db.close();
		return row_number;
	}
	
	protected void create(){
		SQLiteDatabase db = this.getWritableDatabase();
		long new_id = db.insert(table_name, null, content_values);
		setId( (int) new_id );
		db.close();
	}

	protected void update(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(table_name, content_values, "id=?", new String[] { String.valueOf(getId()) });
		db.close();
	}

	public Model(Context context, String table_name) {
		super(context, table_name);
		content_values = new ContentValues();
	}

	public void setId( int id ){ this.id = id; }

	public int getId(){ return this.id; }

	public boolean loadById( int id ){
		return loadBy("id", id);
	}

	private boolean generalLoadBy(String column, String value )
	{	
		//consulta a realizar
		String selectQuery = "SELECT * FROM :P1 WHERE :P2  = :P3 LIMIT 1";
		
		// se reemplazan los parametros para realizar la consulta sobre la tabla
		// filtrando las tuplas donde la columna indicada contenga el valor recibido
		selectQuery = selectQuery.replace(":P1", table_name);
		selectQuery = selectQuery.replace(":P2", column);
		selectQuery = selectQuery.replace(":P3", value);
		
		return loadBySQL(selectQuery);
	}
	
	protected boolean loadBySQL( String sql_query ){
		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(sql_query, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return false;
		}
		c.moveToFirst();
		load(c);
		c.close();
		db.close();
		return true;
	}
	
	protected boolean see_ids(String query){
		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(query, null);
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return false;
		}
		c.moveToFirst();
		load(c);
		c.close();
		db.close();
		return true;
		
		
	}

	public boolean loadBy( String column, int value ){

		return generalLoadBy(column,String.valueOf(value));
	}

	public String getErrorMessage()
	{	
		StringBuilder error_message = new StringBuilder();
		ArrayList<String> errors = this.getErrorMessages();
		for( String em : errors ){			
			error_message.append("* "+em+"\n");
		}
		
		return error_message.toString();
	}

	public boolean loadBy( String column, String value ){		
		value = "'"+value+"'";		
		return generalLoadBy(column,value);
	}

	public void save(){
		if( getId()==-1 ){
			this.create();
			System.out.print("creando");
		}else{			
			System.out.print("actualizando");
			this.update();
		}
	}

	protected ArrayList<String> getErrorMessages(){
		return error_messages;
	}

	public abstract boolean isValid();

	public abstract void load( Cursor c );
	
	public abstract JSONObject toJSON() throws JSONException;

}
