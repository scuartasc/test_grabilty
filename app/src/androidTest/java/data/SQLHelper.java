package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.siat.pasajeros.data.model.Achievement;
import com.siat.pasajeros.data.model.AchivementScoreCarreraSalvaje;
import com.siat.pasajeros.data.model.AchivementScoreCoinsCatcher;
import com.siat.pasajeros.data.model.AchivementScoreMfa;
import com.siat.pasajeros.data.model.Answer;
import com.siat.pasajeros.data.model.GameAnswer;
import com.siat.pasajeros.data.model.GameMFAAnswer;
import com.siat.pasajeros.data.model.Score;
import com.siat.pasajeros.data.model.Star;
import com.siat.pasajeros.data.model.SubSectionAchievement;
import com.siat.pasajeros.data.model.User;
import com.siat.pasajeros.data.model.UserData;

public abstract class SQLHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "fundacion.db";
	private static final int DATABASE_VERSION = 2;
	protected String table_name;
	private Context context;	
	public SQLHelper( Context context, String table_name ){
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
		this.context = context;
		this.table_name = table_name;
	}
	public Context getContext(){
		return this.context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create users table
		db.execSQL(User.SQL_TABLE_CREATE);
		db.execSQL(Answer.SQL_TABLE_CREATE);
		db.execSQL(Achievement.SQL_TABLE_CREATE);
		db.execSQL(SubSectionAchievement.SQL_TABLE_CREATE);
		db.execSQL(UserData.SQL_TABLE_CREATE);
		db.execSQL(Score.SQL_TABLE_CREATE);
		db.execSQL(Star.SQL_TABLE_CREATE);
		db.execSQL(GameAnswer.SQL_TABLE_CREATE);
		db.execSQL(GameMFAAnswer.SQL_TABLE_CREATE);
        db.execSQL(AchivementScoreMfa.SQL_TABLE_CREATE);
        db.execSQL(AchivementScoreCarreraSalvaje.SQL_TABLE_CREATE);
        db.execSQL(AchivementScoreCoinsCatcher.SQL_TABLE_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + this.table_name);
	    onCreate(db);
	    db.close();
	}

}
