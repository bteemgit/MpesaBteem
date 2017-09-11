package com.example.admin.mpesabteem.vivacom.mzaad;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;
import java.util.Date;


public class BaseActivity extends Activity {

	ImageView titleIcon, footer;
	TextView title;
	LinearLayout mainTitileBg, upldBtn, calendarBtn;
	SharedPreferences preference;
	Context context;
	private int lastExpandedPosition = -1;
	ExpandableListView listView;
	Intent intent;
	Spinner timeSpnr;
	String filter_type, filter_query, filter;
	Button okBtn, cancelbtn, okBtnDP, cancelBtnDp;
	TextView startdate, enddate;
	ImageView fromBtn, toBtn;
	EditText filterET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected void initialize() {
		preference = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		filter_type = preference.getString(StringConstant.FILTER_TYPE,
				StringConstant.ALL_TIME);

		titleIcon = (ImageView) findViewById(R.id.titleBar_IV);
		title = (TextView) findViewById(R.id.titleText);
		mainTitileBg = (LinearLayout) findViewById(R.id.mainTitileBg);
		upldBtn = (LinearLayout) findViewById(R.id.upldBtn);
		footer = (ImageView) findViewById(R.id.footerImg);
		listView = (ExpandableListView) findViewById(R.id.list);
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					listView.collapseGroup(lastExpandedPosition);
				}
				lastExpandedPosition = groupPosition;
			}
		});
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void TimeInit() {
		calendarBtn = (LinearLayout) findViewById(R.id.calenderBtn);
		calendarBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowDialog();

			}
		});
	}

	protected void spinnerInit() {
		context = this;
		timeSpnr = (Spinner) findViewById(R.id.timeSpnr);
		ArrayList<String> list = new ArrayList<String>();
		list.add("Select Filter");
		list.add("All Time");
		list.add("Last Month");
		list.add("Last Year");

		ArrayAdapter<String> adapter;
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.custom_spinner, list) {

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				Typeface type = Typeface.createFromAsset(context.getAssets(),
						"TitilliumWeb-Regular.ttf");

				v.setBackgroundColor(Color.GRAY);
				((TextView) v).setTypeface(type);
				((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
				return v;
			}
		};

		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpnr.setAdapter(adapter);
		switch (filter_type) {
		case StringConstant.MONTH:
			timeSpnr.setSelection(2, false);
			break;
		case StringConstant.YEAR:
			timeSpnr.setSelection(3, false);
			break;
		case StringConstant.ALL_TIME:
			timeSpnr.setSelection(1, false);
			break;
		default:
			timeSpnr.setSelection(0, false);
			break;
		}

		timeSpnr.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 1:
					addvalue(StringConstant.ALL_TIME);
					break;
				case 2:
					addvalue(StringConstant.MONTH);
					break;
				case 3:
					addvalue(StringConstant.YEAR);
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private void addvalue(String type) {
		preference = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = preference.edit();
		editor.putString(StringConstant.FILTER_TYPE, type);
		if (type.equals(StringConstant.TIME_DUR)) {
			editor.putString(StringConstant.START_DATE, startdate.getText()
					.toString());
			editor.putString(StringConstant.END_DATE, enddate.getText()
					.toString());
			editor.putString(StringConstant.FILTER_NAME, filterET.getText()
					.toString());
			editor.putBoolean(StringConstant.FILTER_CHECK, true);
		}
		editor.commit();
		finish();
		startActivity(getIntent());
		this.overridePendingTransition(0, 0);
	}

	public void ShowDialog() {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.time_frame_dialog);

		okBtn = (Button) dialog.findViewById(R.id.okBtn);
		cancelbtn = (Button) dialog.findViewById(R.id.cancelBtn);
		fromBtn = (ImageView) dialog.findViewById(R.id.stDateBtn);
		toBtn = (ImageView) dialog.findViewById(R.id.endDateBtn);
		startdate = (TextView) dialog.findViewById(R.id.startDate);
		enddate = (TextView) dialog.findViewById(R.id.endDate);
		filterET = (EditText) dialog.findViewById(R.id.filterET);
		enddate.setText(DateFormat.format("yyyy-MM-dd", new Date()));
		filterET.setText("");
		dialog.show();
		dialog.setCancelable(false);
		fromBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker("1");

			}
		});
		toBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker("2");

			}
		});
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addvalue(StringConstant.TIME_DUR);
				dialog.dismiss();
			}

		});
		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		//
	}

	private void showDatePicker(final String id) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_datepicker_dialog);
		final DatePicker datePicker = (DatePicker) dialog
				.findViewById(R.id.datePicker1);
		okBtnDP = (Button) dialog.findViewById(R.id.OKBtnDP);
		cancelBtnDp = (Button) dialog.findViewById(R.id.cancelBtnDP);
		dialog.show();
		dialog.setCancelable(true);
		okBtnDP.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int month = datePicker.getMonth();
				int day = datePicker.getDayOfMonth();
				int year = datePicker.getYear();
				month = month + 1;
				String mon, dt;
				if (month < 10) {
					mon = "0" + Integer.toString(month);
				} else {
					mon = Integer.toString(month);
				}
				if (day < 10) {
					dt = "0" + Integer.toString(day);
				} else {
					dt = Integer.toString(day);
				}
				StringBuilder date = new StringBuilder().append(year)
						.append("-").append(mon).append("-").append(dt);
				if (id.equals("1")) {
					startdate.setText(date);
				} else {
					enddate.setText(date);
				}
				dialog.dismiss();
			}
		});
		cancelBtnDp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
}
