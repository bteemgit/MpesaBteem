package com.example.admin.mpesabteem.mpos.database;

public class StringQuery {

	String time_filter;
	public static final String regQuery = "Create table "
			+ ColumnID.REGEX_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.PROVIDER_ID
			+ " text," + ColumnID.TYPE_ID + " text," + ColumnID.LANG + " text," + ColumnID.EXPRESSION
			+ " text);";
	public static final String prvdrQuery = "Create table "
			+ ColumnID.PROVIDER_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.PROVIDER
			+ " text);";
	public static final String typeQuery = "Create table "
			+ ColumnID.TYPE_TABEL + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.SUPER_TYPE_ID
			+ " text," + ColumnID.TYPE + " text);";
	public static final String stQuery = "Create table "
			+ ColumnID.SUPER_TYPE_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.SUPER_TYPE
			+ " text);";

	public static final String sampleQuery = "Create table "
			+ ColumnID.SAMPLE_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.SAMPLE
			+ " text);";

	/*public static final String detailQuery = "Create table "
			+ ColumnID.DETAIL_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.REF_ID
			+ " text," + ColumnID.DATE + " text," + ColumnID.AMOUNT + " float,"
			+ ColumnID.NAME + " text," + ColumnID.BALANCE + " float,"
			+ ColumnID.PROVIDER_ID + " text," + ColumnID.TYPE_ID
			+ " text,UNIQUE(" + ColumnID.REF_ID + ") ON CONFLICT IGNORE);";*/

	public static final String detailQuery = "Create table "
			+ ColumnID.DETAIL_TABLE + "(" + ColumnID.ID
			+ " integer primary key autoincrement," + ColumnID.DATE
			+ " text," + ColumnID.REF_ID + " text," + ColumnID.PHONE + " text,"
			+ ColumnID.NAME + " text,"+ ColumnID.AMOUNT + " float,"
			+ ColumnID.CREDIT + " float," + ColumnID.BALANCE + " float,"
			+ ColumnID.PROVIDER_ID + " text," + ColumnID.TYPE_ID
			+ " text,UNIQUE(" + ColumnID.REF_ID + ") ON CONFLICT IGNORE);";

	public static final String super_type_name_query = "SELECT st.super_type from msg_dtl_table as m inner join type_table as t on m.typeid=t._id inner join super_type_table as st on t.super_typeid=st._id where m.typeid=";
	public static final String TYPE_QUERY = "SELECT t.type from msg_dtl_table as m inner join type_table as t on m.typeid=t._id where m.typeid=";

	public static final String CUR_BALANCE1 = "SELECT balance from msg_dtl_table ";
	public static final String CUR_BALANCE2 ="' order by date DESC limit 1";
	public static final String CUR_BALANCE3="' order by _id DESC limit 1";

	public static final String PERSON_NAME = "select name from msg_dtl_table ";
	public static final String DATE_FOR_PERSON = "select date from msg_dtl_table as m where name='";
	public static final String TYPE_LIST = "SELECT type from msg_dtl_table as m  inner join type_table as t on m.typeid=t._id ";
	public static final String DATE_FOR_TYPE = "SELECT date from msg_dtl_table as m  inner join type_table as t on m.typeid=t._id  where type='";
	public static final String TOTAL_QUERY = "SELECT sum(amount) from msg_dtl_table as m  inner join type_table as t on m.typeid=t._id inner join super_type_table as s on t.super_typeid=s._id where s.super_type='";
	public static final String TOTAL_QUERY2 = "SELECT sum(amount) from msg_dtl_table as m  inner join type_table as t on m.typeid=t._id inner join super_type_table as s on t.super_typeid=s._id where t.type='";

	public static final String ST_FOR_TYPE = "SELECT super_type from type_table as t inner join super_type_table as s on t.super_typeid=s._id where t.type='";
	public static final String DATE_FOR_MONTH = "SELECT * from msg_dtl_table where date like '";
	public static final String TQ1 = "SELECT * from msg_dtl_table ";
	public static final String TQ2 = "where  date>=datetime('now','";
	public static final String TQ3 = "' order by date DESC";
	public static final String TQ6 = "' order by "+ColumnID.ID +" DESC";

	

	public static final String TQ4 = "' order by type ASC";
	public static final String TQ5 = "' and date>=datetime('now','";
	public static final String DETAIL_FOR_TYPE = "SELECT * from msg_dtl_table as m  inner join type_table as t on m.typeid=t._id where date like '";
	
	public static final String PRVDR1 = "where providerid='";
	public static final String PRVDR2 = " and providerid='";
	public static final String PRVDR3 = "' and providerid='";
	public static final String NAME = "' and name='";
	public static final String TYPE = "' and type='";
}
