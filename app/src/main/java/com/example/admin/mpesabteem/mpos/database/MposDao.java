package com.example.admin.mpesabteem.mpos.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;


public class MposDao {
	DatabaseHelper dbHelper;
	SQLiteDatabase database;
	String filter_type, date_var, providerid;

	String tq5, tq2, stdate, endDate, filter_name;
	SharedPreferences preference;

	public MposDao(Context context) {
		super();
		dbHelper = new DatabaseHelper(context);
		preference = PreferenceManager.getDefaultSharedPreferences(context);
		filter_type = preference.getString(StringConstant.FILTER_TYPE,
				StringConstant.ALL_TIME);
		providerid = preference.getString(StringConstant.PROVIDER_ID, "0");
		getquery();
	}

	public void openDB() {

		database = dbHelper.getWritableDatabase();

	}

	public void closeDB() {

		database.close();
	}

	public void InsertSample(String ref_id) {

		ContentValues values = new ContentValues();
		values.put(ColumnID.SAMPLE, ref_id);

		openDB();
		database.beginTransaction();
		database.insert(ColumnID.SAMPLE_TABLE, null, values);
		database.setTransactionSuccessful();
		database.endTransaction();
		closeDB();
	}

	public void InsertDetails(String ref_id, String date, Float amount,
			String name, Float balance, String typeid, String providerid) {

		ContentValues values = new ContentValues();
		values.put(ColumnID.REF_ID, ref_id);
		values.put(ColumnID.DATE, date);

		values.put(ColumnID.AMOUNT, amount);
		values.put(ColumnID.NAME, name);
		values.put(ColumnID.BALANCE, balance);
		values.put(ColumnID.TYPE_ID, typeid);
		values.put(ColumnID.PROVIDER_ID, providerid);

		openDB();

		database.insertWithOnConflict(ColumnID.DETAIL_TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		closeDB();
	}

	public String getRegEx(String provider, String type, String lang) {
		String regEx = null;
		openDB();
		Cursor cursor = this.database.query(ColumnID.REGEX_TABLE, null,
				ColumnID.PROVIDER_ID + "=" + provider + " AND "
						+ ColumnID.TYPE_ID + "=" + type + " AND "
						+ ColumnID.LANG + "='" + lang + "'", null, null, null,
				null);

		while (cursor.moveToNext()) {
			regEx = cursor
					.getString(cursor.getColumnIndex(ColumnID.EXPRESSION));
		}
		return regEx;

	}
	public ArrayList<String> getDet() {
		ArrayList<String> list = new ArrayList<String>();
		openDB();

		String query = "Select * from" +ColumnID.DETAIL_TABLE ;

		Cursor cursor = database.rawQuery(query, null);

		while (cursor.moveToNext()) {
			
			String date= cursor
					.getString(cursor.getColumnIndex(ColumnID.BALANCE ));
			list.add(date);
			
		}
		return list;

	}

	public int gettypeID(String type) {
		int id = 0;
		openDB();
		Cursor cursor = database.query(ColumnID.TYPE_TABEL, null, ColumnID.TYPE
				+ "='" + type + "'", null, null, null, null);

		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(ColumnID.ID));
		}
		return id;

	}

	public ArrayList<Details> getRecentDetails() {
		ArrayList<Details> list = new ArrayList<Details>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TQ1 + StringQuery.TQ2
				+ StringConstant.LAST_WEEK + "')" + StringQuery.PRVDR2
				+ providerid + StringQuery.TQ6, null);

		while (cursor.moveToNext()) {
			Details details = new Details();
			details.setRef_ID(cursor.getString(cursor
					.getColumnIndex(ColumnID.REF_ID)));
			details.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME)));
			details.setDate(cursor.getString(cursor
					.getColumnIndex(ColumnID.DATE)));
			details.setAmount(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.AMOUNT)));
			details.setBalance(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.BALANCE)));
			details.setTypeId(cursor.getString(cursor
					.getColumnIndex(ColumnID.TYPE_ID)));
			list.add(details);
		}
		return list;

	}

	public ArrayList<Details> getDetails(String type) {
		ArrayList<Details> list = new ArrayList<Details>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TQ1 + tq2
				+ StringQuery.PRVDR2 + providerid + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			Details details = new Details();
			details.setRef_ID(cursor.getString(cursor
					.getColumnIndex(ColumnID.REF_ID)));
			details.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME)));
			details.setDate(cursor.getString(cursor
					.getColumnIndex(ColumnID.DATE)));
			details.setAmount(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.AMOUNT)));
			details.setBalance(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.BALANCE)));
			details.setTypeId(cursor.getString(cursor
					.getColumnIndex(ColumnID.TYPE_ID)));
			list.add(details);
		}
		return list;

	}

	public ArrayList<Details> getDetailsDay(String date) {
		ArrayList<Details> list = new ArrayList<Details>();

		openDB();
		Cursor cursor = database
				.rawQuery(StringQuery.DATE_FOR_MONTH + date + "%"
						+ StringQuery.PRVDR3 + providerid + StringQuery.TQ3,
						null);

		while (cursor.moveToNext()) {
			Details details = new Details();
			details.setRef_ID(cursor.getString(cursor
					.getColumnIndex(ColumnID.REF_ID)));
			details.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME)));
			details.setDate(cursor.getString(cursor
					.getColumnIndex(ColumnID.DATE)));
			details.setAmount(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.AMOUNT)));
			details.setBalance(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.BALANCE)));
			details.setTypeId(cursor.getString(cursor
					.getColumnIndex(ColumnID.TYPE_ID)));
			list.add(details);
		}
		return list;

	}

	public ArrayList<Details> getPersonDetailDay(String date, String name) {
		ArrayList<Details> list = new ArrayList<Details>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.DATE_FOR_MONTH + date
				+ "%" + StringQuery.NAME + name + StringQuery.PRVDR3
				+ providerid + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			Details details = new Details();
			details.setRef_ID(cursor.getString(cursor
					.getColumnIndex(ColumnID.REF_ID)));
			details.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME)));
			details.setDate(cursor.getString(cursor
					.getColumnIndex(ColumnID.DATE)));
			details.setAmount(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.AMOUNT)));
			details.setBalance(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.BALANCE)));
			details.setTypeId(cursor.getString(cursor
					.getColumnIndex(ColumnID.TYPE_ID)));
			list.add(details);
		}
		return list;

	}

	public ArrayList<Details> getTypeDetailDay(String date, String type) {
		ArrayList<Details> list = new ArrayList<Details>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.DETAIL_FOR_TYPE + date
				+ "%" + StringQuery.TYPE + type + StringQuery.PRVDR3
				+ providerid + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			Details details = new Details();
			details.setRef_ID(cursor.getString(cursor
					.getColumnIndex(ColumnID.REF_ID)));
			details.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME)));
			details.setDate(cursor.getString(cursor
					.getColumnIndex(ColumnID.DATE)));
			details.setAmount(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.AMOUNT)));
			details.setBalance(cursor.getFloat(cursor
					.getColumnIndex(ColumnID.BALANCE)));
			details.setTypeId(cursor.getString(cursor
					.getColumnIndex(ColumnID.TYPE_ID)));
			list.add(details);
		}
		return list;

	}

	public ArrayList<Details> getSamples() {
		ArrayList<Details> countyList = new ArrayList<Details>();
		openDB();
		String query = "Select *  from " + ColumnID.DETAIL_TABLE ;
		Cursor cursor = database.rawQuery(query, null);


		while (cursor.moveToNext()) {
			Details county = new Details();

			county.setName(cursor.getString(cursor
					.getColumnIndex(ColumnID.BALANCE)));




			countyList.add(county);

		}
		return countyList;

	}


	public ArrayList<String> getDates() {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TQ1 + StringQuery.TQ2
				+ StringConstant.LAST_WEEK + "')" + StringQuery.PRVDR2
				+ providerid + StringQuery.TQ6, null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setDate(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}

		}
		return list;

	}

	public ArrayList<String> getDatesForPerson(String personName) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.DATE_FOR_PERSON
				+ personName + tq5 + StringQuery.PRVDR2 + providerid
				+ StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setDate(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}

		}
		return list;

	}

	public Float getCurrentBalance() {
		Float f = Float.valueOf(0.0F);
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.CUR_BALANCE1
				+ StringQuery.PRVDR1 + providerid + StringQuery.CUR_BALANCE3,
				null);
		if (cursor != null && cursor.moveToFirst()) {

			f = cursor.getFloat(cursor.getColumnIndex(ColumnID.BALANCE));
		}
		closeDB();
		return f;

	}

	public String getSuperType(String types, String query) {
		String type = "";
		openDB();
		Cursor cursor = database.rawQuery(query + types, null);
		if (cursor != null && cursor.moveToFirst()) {

			type = cursor.getString(cursor.getColumnIndex(ColumnID.SUPER_TYPE));
		}
		closeDB();
		return type;

	}

	public String getSuper_Type(String types, String query) {
		String type = "";
		openDB();
		Cursor cursor = database.rawQuery(query + types + "'", null);
		if (cursor != null && cursor.moveToFirst()) {
			type = cursor.getString(cursor.getColumnIndex(ColumnID.SUPER_TYPE));
		}
		closeDB();
		return type;

	}

	public Float getTotalSuperType(String date, String type) {
		Float total = null;
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TOTAL_QUERY + type
				+ "' and m.date like'" + date + "%" + tq5 + StringQuery.PRVDR2
				+ providerid + StringQuery.TQ3, null);
		if (cursor != null && cursor.moveToFirst()) {
			total = cursor.getFloat(0);
		}
		closeDB();
		return total;

	}

	public Float getTotalPerson(String person, String super_type) {
		Float total = null;
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TOTAL_QUERY + super_type
				+ "' and name='" + person + StringQuery.PRVDR3 + providerid
				+ "'", null);
		if (cursor != null && cursor.moveToFirst()) {
			total = cursor.getFloat(0);
		}
		closeDB();
		return total;

	}

	public Float getTotalType(String type, String super_type) {
		Float total = null;
		openDB();
		Cursor cursor = database.rawQuery(
				StringQuery.TOTAL_QUERY + super_type + "' and type='" + type
						+ StringQuery.PRVDR3 + providerid + "'", null);
		if (cursor != null && cursor.moveToFirst()) {
			total = cursor.getFloat(0);
		}
		closeDB();
		return total;

	}

	public Float getTPD(String person, String type, String date) {
		Float total = null;
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TOTAL_QUERY + type
				+ "' and m.name='" + person + "' and m.date like '" + date
				+ "%'", null);
		if (cursor != null && cursor.moveToFirst()) {
			total = cursor.getFloat(0);
		}
		closeDB();
		return total;

	}

	public Float getTotTypeDate(String type, String date) {
		Float total = null;
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TOTAL_QUERY2 + type
				+ "' and m.date like '" + date + "%'", null);
		if (cursor != null && cursor.moveToFirst()) {
			total = cursor.getFloat(0);
		}
		closeDB();
		return total;

	}

	public ArrayList<String> getPerson() {
		ArrayList<String> list = new ArrayList<String>();
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.PERSON_NAME + tq2
				+ StringQuery.PRVDR2 + providerid + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			String person = cursor.getString(cursor
					.getColumnIndex(ColumnID.NAME));
			if (!list.contains(person)) {
				list.add(person);
			}
		}
		return list;
	}

	public ArrayList<String> getTypes() {
		ArrayList<String> list = new ArrayList<String>();
		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TYPE_LIST + tq2
				+ StringQuery.PRVDR2 + providerid + StringQuery.TQ4, null);

		while (cursor.moveToNext()) {
			String type = cursor
					.getString(cursor.getColumnIndex(ColumnID.TYPE));
			if (!list.contains(type)) {
				list.add(type);
			}
		}
		return list;
	}

	public ArrayList<String> getDatesForType(String type) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database
				.rawQuery(StringQuery.DATE_FOR_TYPE + type + tq5
						+ StringQuery.PRVDR2 + providerid + StringQuery.TQ3,
						null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setDate(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}

		}
		return list;

	}

	public ArrayList<String> getMonths(String year) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TQ1 + tq2
				+ StringQuery.PRVDR2 + providerid + "'" + "and date like '"
				+ year + "%" + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setMonth(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}
		}
		return list;
	}

	public ArrayList<String> getYears() {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database.rawQuery(StringQuery.TQ1 + tq2
				+ StringQuery.PRVDR2 + providerid + StringQuery.TQ3, null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setYear(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}
		}
		return list;
	}

	public ArrayList<String> getDatesForMonth(String month) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();

		openDB();
		Cursor cursor = database.rawQuery(
				StringQuery.DATE_FOR_MONTH + month + "%" + tq5
						+ StringQuery.PRVDR2 + providerid + StringQuery.TQ3,
				null);

		while (cursor.moveToNext()) {
			String date = cursor
					.getString(cursor.getColumnIndex(ColumnID.DATE));
			String dt = new FormatDate().setDate(date);
			if (!tempList.contains(dt)) {
				list.add(date);
				tempList.add(dt);
			}

		}
		return list;

	}

	public String getType(String paramString) {
		String str = "";
		openDB();
		Cursor localCursor = this.database
				.rawQuery(
						"SELECT t.type from msg_dtl_table as m inner join type_table as t on m.typeid=t._id where m.typeid="
								+ paramString, null);
		if ((localCursor != null) && (localCursor.moveToFirst())) {
			str = localCursor.getString(localCursor
					.getColumnIndex(ColumnID.TYPE));
		}

		closeDB();
		return str;
	}

	private void getquery() {
		switch (filter_type) {
		case StringConstant.ALL_TIME:
			date_var = StringConstant.ALL_TIME_MSG;
			tq2 = "where  date>=datetime('now','" + date_var + "')";
			tq5 = "' and date>=datetime('now','" + date_var + "')";
			break;
		case StringConstant.MONTH:

			date_var = StringConstant.LAST_MONTH;
			tq2 = "where  date>=datetime('now','" + date_var + "')";
			tq5 = "' and date>=datetime('now','" + date_var + "')";
			break;
		case StringConstant.YEAR:

			date_var = StringConstant.LAST_YEAR;

			tq2 = "where  date>=datetime('now','" + date_var + "')";
			tq5 = "' and date>=datetime('now','" + date_var + "')";
			break;
		case StringConstant.TIME_DUR:
			stdate = preference.getString("startDate", "");
			endDate = preference.getString("endDate", "");
			filter_name = preference.getString(StringConstant.FILTER_NAME, "");
			if (!stdate.equals("") & !endDate.equals("")) {
				tq2 = "where  date between '" + stdate + "' and '" + endDate
						+ "' and name like'%" + filter_name + "%'";
				tq5 = "' and  date between '" + stdate + "' and '" + endDate
						+ "' and name like'%" + filter_name + "%'";
			} else {
				tq2 = "where name like'%" + filter_name + "%'";
				tq5 = "' and name like'%" + filter_name + "%'";
			}
			break;
		default:
			break;
		}
	}
}
