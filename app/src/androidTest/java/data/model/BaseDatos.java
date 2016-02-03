package data.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatos {
	public static final String N_BD="bd_testgrability";
	public static final int VERSION_BD=1;
	
	private BDHelper nHelper;
	private final Context nContexto;
	private SQLiteDatabase nBD;
	
	private static class BDHelper extends SQLiteOpenHelper {

		public BDHelper(Context context) {
			super(context, N_BD, null, VERSION_BD);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String createTable="CREATE TABLE see(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"id_see INTEGER NULL," +
					" id_user INTEGER NULL ," +
					"see_value INTEGER NULL);";
			db.execSQL(createTable);
	
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			db.execSQL(" DROP TABLE IF EXISTS see;");
          
			
		}
		
	}
public BaseDatos (Context c){
	nContexto=c;
}

	public BaseDatos abrir(){

		nHelper= new BDHelper(nContexto);
		nBD=nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar(){
	nHelper.close();
}

	public void insertarUser (int id_see,int id_user,int value_see){

		ContentValues cv = new ContentValues();
		cv.put("id_see", id_see);
		cv.put("id_user", id_user);
		cv.put("see_value",value_see);
	
	nBD.insert("see", null, cv);
	
	}


	public String receive(String from, String[] select,String where) {
	
		String resultado="";
		Cursor c = nBD.query(from, select,where,null,null, null, null);
		int id_see= c.getColumnIndex("id_see");
	
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
		resultado= resultado + c.getString(id_see)+";";
		}

		return resultado;

}

}