package ir.ounegh.nazdik;
import android.content.*;
import android.database.sqlite.*;
import java.util.*;
import android.database.*;
import org.apache.http.impl.client.*;

public class mdb extends SQLiteOpenHelper
{

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_AMAKEN);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMAKEN);
		onCreate(db);
		// TODO: Implement this method
	}
	SQLiteDatabase database;
	 
	public mdb(Context context) {
		super(context, NZapp.APPNAME, null, 1);
	}
	public static String TABLE_AMAKEN="tbl_amaken";
	
	
	
	public static String COL_NAME="name";
	
	public static String COL_PHONE="phone";
	
	public static String C_CAT="category";
	
	public static String C_LAT="latitude";
	
	public static String C_LON="longitude";
	
	public static String C_ID="id";
	
	public static String C_SERVER_ID="serverid";
	public static String CREATE_AMAKEN="Create table if not exists "+TABLE_AMAKEN+"("+
    C_ID+" integer ,"+
	COL_NAME+" text, "+
	COL_PHONE+" text, "+
	//C_SERVER_ID+" integer, "+
	C_CAT+" text, "+
	C_LAT+" text, "+
	C_LON+" text "+
	")";
	
	
	public ArrayList<mLocation> getByCategory(String cat){
		database = this.getReadableDatabase();
		Cursor cursor= database.rawQuery("select * from amaken where category="+cat,null);
		ArrayList<mLocation> amaken=new ArrayList<>();
		cursor.move(-1);
		while(cursor.moveToNext()){
			mLocation m=new mLocation("gps");
			m.setName(cursor.getString(cursor.getColumnIndex("name")));
			
			m.setCategory(cursor.getString(cursor.getColumnIndex("category")));
			m.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
			m.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
			m.setId(cursor.getInt(cursor.getColumnIndex("id")));
			
			amaken.add(m);
		}
		
		database.close();
		return amaken;
	}
	
	public void insertRecord(mLocation mloc) {
		database = this.getReadableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_NAME, mloc.getName());
		contentValues.put(COL_PHONE, mloc.getPhone());
		contentValues.put(C_ID,mloc.getId());
		contentValues.put(C_LAT,mloc.getLatitude());
		contentValues.put(C_LON,mloc.getLongitude());
		contentValues.put(C_CAT,mloc.getCategory());
		database.insert(TABLE_AMAKEN, null, contentValues);
		database.close();
	}
	
	
	
	public ArrayList<String> getcats(){
		database = this.getReadableDatabase();
		ArrayList<String> cats= new ArrayList<>();
		Cursor cusor= database.rawQuery("select distinct category from "+ TABLE_AMAKEN,null);
		cusor.moveToPosition(-1);
		while(cusor.moveToNext()){
			String l=cusor.getString(cusor.getColumnIndex("category"));
			cats.add(l);
		}
		database.close();
		return cats;
	}
	
	
	
}
