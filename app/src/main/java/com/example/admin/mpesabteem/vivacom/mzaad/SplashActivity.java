package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.DatabaseHelper;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.FontsOverride;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;


public class SplashActivity extends BaseActivity {

	private static int SPLASH_TIME_OUT = 3000;
	boolean isInsert, isCsvUpdated;
	Context context;
	MposDao dao;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	Fabric.with(this, new Crashlytics());
		setContentView(R.layout.activity_splash);
		context = this;

		new DatabaseHelper(context).getWritableDatabase();
		preference = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preference.edit();

		FontsOverride.setDefaultFont(this, "MONOSPACE",
				"TitilliumWeb-Regular.ttf");

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				editor.putString(StringConstant.PROVIDER_ID, "4");
				editor.commit();
				intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
				intent.putExtra(StringConstant.PROVIDER_ID, "4");
				startActivity(intent);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}
