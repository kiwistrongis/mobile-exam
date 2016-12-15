package ca.uoit.csci4100.assessments.finalexamstarter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PreferredStoreLocationDBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_FILENAME = "preferredStoreLocation.db";

	private static final String CREATE_STATEMENT = "" +
			"create table PreferredStore(" +
			"  _id integer primary key autoincrement," +
			"  storeId integer not null," +
			"  name text not null," +
			"  streetAddress text null," +
			"  city text null," +
			"  postalCode text null)";

	private static final String DROP_STATEMENT = "" +
			"drop table PreferredStore";

	public PreferredStoreLocationDBHelper(Context context) {
		super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_STATEMENT);
		db.execSQL(CREATE_STATEMENT);
	}

	/**
	 * updatePreferredStore
	 *
	 * This method will take a Store object, and save all of its fields to the database.
	 *
	 * @param preferredStore The newly selected preferred store, to be saved
	 */
	public void updatePreferredStore(Store preferredStore) {
		// TODO:  Implement this method
	}

	/**
	 * getPreferredStore
	 *
	 * This method will read the last saved preferred store's ID from the database.  If no store has
	 * been saved before, it will return -1.
	 *
	 * @return The ID of the last preferred store, or -1 if there were none saved
	 */
	public int getPreferredStore() {
		// TODO:  Implement this method
		
		return -1;}
}
