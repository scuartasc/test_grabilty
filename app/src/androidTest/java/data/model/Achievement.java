package data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.siat.pasajeros.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Achievement  extends Model{
      	SQLiteDatabase db;
	protected static final String TABLE_NAME = "achievements";
	public static final String SQL_TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"user_id INTEGER NOT NULL," +
			"section_id INTEGER NOT NULL," +
			"timerSection INTEGER NOT NULL,"+
			"counterSectionVisited INTEGER NOT NULL,"+
			"sectionFinalized INTEGER NOT NULL,"+
			"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";
	public Achievement(Context context) {
		super(context, TABLE_NAME);
	}
	public static String getTableName(){
		return TABLE_NAME;
	}
	// SETS 
	public void setUserId( int user_id ){ setValue("user_id", user_id); }
	public void setSectionId( int section_id ){ setValue("section_id", section_id); }
	public void setTimeSection(int timerSection){ setValue("timerSection", timerSection);} 
	public void setCounterSectionVisited(int counterSectionVisited){setValue("counterSectionVisited", counterSectionVisited);}
	public void setSectionFinalized(int sectionFinalized){setValue("sectionFinalized", sectionFinalized);}
	private void setCreatedAt( String created_at ){ setValue("created_at", created_at);}
	
	
	// GETS
	public int getUserId(){ return (Integer) getValue("user_id"); }
	public int getSectionId(){ return (Integer) getValue("section_id"); }
	public int getTimerSection(){return (Integer)getValue("timerSection");}
	public int getCounterSectionVisited(){return (Integer)getValue("counterSectionVisited");}
	public int getsectionFinalized(){return(Integer)getValue("sectionFinalized");}
	public String getCreatedAt(){ return (String) getValue("created_at"); }
	
	public int[] searchTimerOnSection(int user_id,int section){
		int arreglo[]=new int[2];
		String query="SELECT timerSection,counterSectionVisited FROM achievements WHERE user_id= :P2 AND section_id= P3 ;";		
		query=query.replace(":P2", user_id+"");
		query=query.replace("P3", section+"");
		
	

		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(query, null);
		int timerSection=c.getColumnIndex("timerSection");
		int counterSectionVisited=c.getColumnIndex("counterSectionVisited");
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			
			return arreglo;
		}
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			arreglo[0]=c.getInt(timerSection);
			arreglo[1]=c.getInt(counterSectionVisited);
		}	
		c.close();
		db.close();
		
		
		return arreglo;
		
	}
	
	public void uptadteTimes(int time,int countSection,int user_id,int section_id){
		SQLiteDatabase db = this.getReadableDatabase();	
		content_values =new ContentValues();
		content_values.put("timerSection", time);
	    content_values.put("counterSectionVisited", countSection);		
		db.update("achievements", content_values,"user_id="+user_id+" AND "+"section_id="+section_id , null);
		db.close();
		
	}
	
	public void uptadteTimesFinalized(int time,int counter,int user_id,int finalized,int section_id){
		SQLiteDatabase db = this.getReadableDatabase();	
		content_values =new ContentValues();
		content_values.put("timerSection", time);
		content_values.put("sectionFinalized", finalized);
		content_values.put("counterSectionVisited", counter);
		db.update("achievements", content_values,"user_id="+user_id+" AND "+ "section_id="+section_id , null);
		db.close();
		
	}

	public String getSee(int user_id){				
	
		String res="";
		
		String query="SELECT section_id FROM achievements WHERE user_id= :P2 AND sectionFinalized='1' ";		
		query=query.replace(":P2", user_id+"");

		SQLiteDatabase db = this.getReadableDatabase();	
		Cursor c = db.rawQuery(query, null);
		int id_section=c.getColumnIndex("section_id");
		//int sectionFinalized=c.getColumnIndex("sectionFinalized");
		if (c == null || !c.moveToFirst()){
			c.close();
			db.close();
			return "";
		}
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			res= res + c.getString(id_section)+";";
		}
	
		c.close();
		db.close();
	return res;
	}
	
	public boolean loadByUserAndSection( int user_id, int section_id ){
		//consulta a realizar
		String selectQuery = "SELECT * FROM :P1 WHERE user_id  = :P2 AND section_id  = :P3";
		
		// se reemplazan los parametros para realizar la consulta sobre la tabla
		// filtrando las tuplas donde la columna indicada contenga el valor recibido
		selectQuery = selectQuery.replace(":P1", table_name);
		selectQuery = selectQuery.replace(":P2", user_id+"");
		selectQuery = selectQuery.replace(":P3", section_id+"");
		
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
		setSectionId(c.getInt(c.getColumnIndex("section_id")));
		setTimeSection(c.getInt(c.getColumnIndex("timerSection")));
		setCounterSectionVisited(c.getInt(c.getColumnIndex("counterSectionVisited")));
		setSectionFinalized(c.getInt(c.getColumnIndex("sectionFinalized")));
		setCreatedAt(c.getString(c.getColumnIndex("created_at")));
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.accumulate("id", getId());
		object.accumulate("user_id", getUserId());
		object.accumulate("section_id", getSectionId());
		object.accumulate("timerSection", getTimerSection());
		object.accumulate("counterSectionVisited", getCounterSectionVisited());
		object.accumulate("sectionFinalized", getsectionFinalized());
		object.accumulate("created_at", getCreatedAt());
		return object;
	}

}
