package com.example.admin.mpesabteem.mpos.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class StaticDataInsert {
	 Context context;
	  SQLiteDatabase database;
	  public StaticDataInsert(Context context, SQLiteDatabase sqLiteDatabase)
	  {
	    this.context = context;
	    this.database = sqLiteDatabase;
	  }
	void importDB() {

		AddSuperType();
		AddType();
		AddProvider();
		AddRegEx();

	}

	private void AddRegEx() {

		BufferedReader reader2 = null;

		try {
			reader2 = getReadValue("regTable.csv");

			String line = "";
			while ((line = reader2.readLine()) != null) {

				line = line.replace("\"", "");
				String[] str = line.split(",", 5);

				String provider = str[1];
				String type = str[2];
				String lang = str[3];
				String regex = str[4];

				InsertRegEx(provider, type, regex,lang);
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	private void AddProvider() {

		BufferedReader reader2 = null;

		try {
			reader2 = getReadValue("providerTabel.csv");

			String line = "";
			while ((line = reader2.readLine()) != null) {

				line = line.replace("\"", "");
				String[] str = line.split(",");

				String provider = str[1];

				InsertProvider(provider);
			}
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	private void AddType() {

		BufferedReader reader2 = null;

		try {
			reader2 = getReadValue("typeTable.csv");

			String line = "";
			while ((line = reader2.readLine()) != null) {

				line = line.replace("\"", "");
				String[] str = line.split(",");

				String supertype = str[1];
				String type = str[2];

				InsertType(supertype, type);
			}
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	private void AddSuperType() {

		BufferedReader reader2 = null;

		try {
			reader2 = getReadValue("superTable.csv");

			String line = "";
			while ((line = reader2.readLine()) != null) {

				line = line.replace("\"", "");
				String[] str = line.split(",");

				String supertype = str[1];

				InsertSuperType(supertype);
			}
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	public BufferedReader getReadValue(String csvName) {
		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(context.getAssets().open(
					csvName));

			reader = new BufferedReader(is);
		} catch (IOException e) {
			// TODO: handle exception
		}
		return reader;

	}
	
	public void InsertSuperType(String superType) {

		ContentValues values = new ContentValues();
		values.put(ColumnID.SUPER_TYPE, superType);

		

		database.insert(ColumnID.SUPER_TYPE_TABLE, null, values);
		
	}

	public void InsertType(String superType, String Type) {

		ContentValues values = new ContentValues();
		values.put(ColumnID.SUPER_TYPE_ID, superType);
		values.put(ColumnID.TYPE, Type);

		

		database.insert(ColumnID.TYPE_TABEL, null, values);
		
	}

	public void InsertProvider(String provider) {

		ContentValues values = new ContentValues();
		values.put(ColumnID.PROVIDER, provider);

		

		database.insert(ColumnID.PROVIDER_TABLE, null, values);
		
	}

public void InsertRegEx(String provider, String type, String expression, String lang) {

	ContentValues values = new ContentValues();
	values.put(ColumnID.EXPRESSION, expression);
	values.put(ColumnID.PROVIDER_ID, provider);
	values.put(ColumnID.TYPE_ID, type);
	values.put(ColumnID.LANG,lang);

	

	database.insert(ColumnID.REGEX_TABLE, null, values);
	
}
}

