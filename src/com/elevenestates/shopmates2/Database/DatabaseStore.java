package com.elevenestates.shopmates2.Database;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elevenestates.shopmates2.CustomAddress.AddressDetails;
import com.elevenestates.shopmates2.CustomStore.StoreDetails;

public class DatabaseStore {

	private static final String DATABASE_NAME = "DatabaseStore";
	private static final String DATABASE_TABLE_NAME = "DatabaseStoreTable";
	private static final String KEY_SNO = "Sno";
	private static final String KEY_TAG = "Tag";
	private static final String KEY_DETAILS = "Details";
	private static final String KEY_PINCODE = "Pincode";
	private static final String KEY_LAT = "Lat";
	private static final String KEY_LNG = "Lng";
	private static final int DATABASE_VERSION = 1;
	String columns[] = { KEY_SNO, KEY_TAG, KEY_DETAILS, KEY_PINCODE, KEY_LAT, KEY_LNG };
	
	Context context;
	DatabaseHelper dbh;
	SQLiteDatabase database;
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_NAME + " (" + KEY_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					KEY_TAG +" TEXT NOT NULL, " + KEY_DETAILS + " TEXT NOT NULL, " + KEY_PINCODE +" TEXT NOT NULL, "+ KEY_LAT + " TEXT NOT NULL, "+ KEY_LNG + " TEXT NOT NULL);");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXITS " + DATABASE_TABLE_NAME);
			onCreate(db);
		}
		
	}
	
	public DatabaseStore(Context context)
	{
		this.context = context;
	}
	public DatabaseStore open()
	{
		dbh = new DatabaseHelper(this.context);
		database = dbh.getWritableDatabase();
		return this;
	}
	public void close()
	{
		dbh.close();
	}
	public long createEntry(String tag, String details, String pincode, String sLat, String sLng)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_TAG,tag);
		cv.put(KEY_DETAILS,details);
		cv.put(KEY_PINCODE,pincode);
		cv.put(KEY_LAT,sLat);
		cv.put(KEY_LNG,sLng);
		return database.insert(DATABASE_TABLE_NAME, null, cv);
	}
	public void getData(ArrayList<StoreDetails> storeList)
	{
		Cursor c = database.query(DATABASE_TABLE_NAME, columns, null,null,null,null,null);
		int iSno = c.getColumnIndex(KEY_SNO);
		int iTag = c.getColumnIndex(KEY_TAG);
		int iDetails = c.getColumnIndex(KEY_DETAILS);
		int iPincode = c.getColumnIndex(KEY_PINCODE);
		int iLat = c.getColumnIndex(KEY_LAT);
		int iLng = c.getColumnIndex(KEY_LNG);
		if(c!=null && c.moveToFirst())
		{
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{
				storeList.add(new StoreDetails(c.getString(iTag),c.getString(iDetails),c.getString(iPincode),c.getString(iLat),c.getString(iLng)));
			}
		}
		
	}
	public void clear() {
		Cursor c = database.query(DATABASE_TABLE_NAME, columns,null,null,null,null,null);
		int iSno = c.getColumnIndex(KEY_SNO);
		if(c!=null && c.moveToFirst())
		{
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{
				String sno = c.getString(iSno);
				database.delete(DATABASE_TABLE_NAME, KEY_SNO + "=" + sno, null);
			}
		}
	}
	public int getPositionOf(String currentTag, String currentDetails,
			String currentPincode) {
		Cursor c = database.query(DATABASE_TABLE_NAME, columns, null,null,null,null,null);
		int iSno = c.getColumnIndex(KEY_SNO);
		int iTag = c.getColumnIndex(KEY_TAG);
		int iDetails = c.getColumnIndex(KEY_DETAILS);
		int iPincode = c.getColumnIndex(KEY_PINCODE);
		if(c!=null && c.moveToFirst())
		{
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{
				if(c.getString(iTag).equals(currentTag) && c.getString(iDetails).equals(currentDetails) && c.getString(iPincode).equals(currentPincode))
				{
					return Integer.parseInt(c.getString(iSno));
				}
			}
		}
		return 0;
	}
	public void editSno(int sno, AddressDetails ad) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_TAG,ad.getTag());
		cv.put(KEY_DETAILS,ad.getAddress());
		cv.put(KEY_PINCODE,ad.getPincode());
		database.update(DATABASE_TABLE_NAME,cv,KEY_SNO+"="+sno,null);
		
	}
	
}
