package com.example.admin.mpesabteem.mpos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static String DB_name = "mpesaDB";
	public static int DB_Ver = 1;
	 Context context;;
	public DatabaseHelper(Context context) {
		super(context, DB_name, null, DB_Ver);
	this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(StringQuery.regQuery);
		db.execSQL(StringQuery.typeQuery);
		db.execSQL(StringQuery.stQuery);
		db.execSQL(StringQuery.prvdrQuery);
		db.execSQL(StringQuery.detailQuery);
		db.execSQL(StringQuery.sampleQuery);
		new StaticDataInsert(context, db).importDB();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table " + ColumnID.REGEX_TABLE);
	    db.execSQL("drop table " + ColumnID.SUPER_TYPE_TABLE);
	    db.execSQL("drop table " + ColumnID.TYPE_TABEL);
	    db.execSQL("drop table " + ColumnID.PROVIDER_TABLE);
	    db.execSQL(StringQuery.regQuery);
	    db.execSQL(StringQuery.typeQuery);
	    db.execSQL(StringQuery.stQuery);
	    db.execSQL(StringQuery.prvdrQuery);
	    new StaticDataInsert(this.context, db).importDB();

	}

}
