package com.example.admin.mpesabteem.vivacom.mzaad;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.ParseMessage;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;
import com.example.admin.mpesabteem.permission.PermissionsActivity;
import com.example.admin.mpesabteem.permission.PermissionsChecker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomePage extends BaseActivity implements OnClickListener {
	ImageView recentIV, timeIV, personIV, typeIV;
	Intent intent;
	MposDao dao;
	String prvdrId, no, upd_Date;
	String detail;
	PermissionsChecker checker;
	private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_SMS};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		ArrayList<String> detaillist = new ArrayList<String>();
		checker = new PermissionsChecker(HomePage.this);
		//detail = dao.getDet();
		
	/*	for (Details i : detail) {
			detaillist.add(i.getBalance().toString());
		}*/
		
		init();
		/*if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
			startPermissionsActivity(PERMISSIONS_READ_STORAGE);
		}
		else*/
		readMessage();

		recentIV.setOnClickListener(this);
		timeIV.setOnClickListener(this);
		personIV.setOnClickListener(this);
		typeIV.setOnClickListener(this);
		footer.setOnClickListener(this);
	}




	private void startPermissionsActivity(String[] permission) {
		PermissionsActivity.startActivityForResult(this, 0, permission);
	}

	private void readMessage() {
		Cursor cursor;
		ContentResolver contentResolver = getContentResolver();

		// +919447931271 for mobile
		// 123456 for emu
try
{
		if (upd_Date.equals("")) {

			cursor = contentResolver.query( Uri.parse( "content://sms/inbox" ), null, null, null, null);
		} else {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd h:mm a");
			Date d = null;
			try {
				d = f.parse(upd_Date);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			long milliseconds = d.getTime();
			cursor = contentResolver.query(Uri.parse("content://sms/inbox"),
					null, "date_sent>" + milliseconds, null, null);
		}

		if (cursor != null) {

			// android.provider.Telephony.Sms.BODY
			boolean success = new ParseMessage(context).parser(cursor, prvdrId);
			if (success) {
				updateLUD();
			}
		}
}
catch(Exception e)
{
	
}

	}

	private void init() {
		context = this;
		recentIV = (ImageView) findViewById(R.id.recentIV);
		timeIV = (ImageView) findViewById(R.id.timeIV);
		personIV = (ImageView) findViewById(R.id.personIV);
		typeIV = (ImageView) findViewById(R.id.typeIV);
		footer = (ImageView) findViewById(R.id.footerImg);
		dao = new MposDao(context);
		intent = getIntent();
		prvdrId = intent.getStringExtra(StringConstant.PROVIDER_ID);
		preference = PreferenceManager.getDefaultSharedPreferences(context);
		upd_Date = preference.getString(StringConstant.LUD + prvdrId, "");
		switch (prvdrId) {
		case "3":

			no = "+919447931271";
			break;
		case "4":
			no = "654321";
			break;

		default:
			break;
		}

	}

	private void updateLUD() {

		SharedPreferences.Editor editor = preference.edit();
		editor.putString(StringConstant.LUD + prvdrId, new SimpleDateFormat(
				"yyyy-MM-dd h:mm a").format(new Date()));
		editor.commit();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.recentIV:
			intent = new Intent(getApplicationContext(), RecentActivity.class);
			startActivity(intent);
			break;
		case R.id.timeIV:
			intent = new Intent(getApplicationContext(), TimeActivity.class);
			startActivity(intent);
			break;
		case R.id.personIV:
			intent = new Intent(getApplicationContext(), PersonActivity.class);
			startActivity(intent);
			break;

		case R.id.typeIV:
			Intent intent = new Intent(getApplicationContext(),
					TypeActivity.class);
			startActivity(intent);
			break;
		case R.id.footerImg:
			finish();
			break;
			
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		boolean isfilter = preference.getBoolean(StringConstant.FILTER_CHECK,
				false);
		if (isfilter) {
			SharedPreferences.Editor editor = preference.edit();
			editor.putString(StringConstant.FILTER_TYPE,
					StringConstant.ALL_TIME);
			editor.putBoolean(StringConstant.FILTER_CHECK, false);
			editor.commit();
		}

		super.onResume();
	}
}
