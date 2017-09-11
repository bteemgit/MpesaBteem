package com.example.admin.mpesabteem.vivacom.mzaad;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

public class ConfigurationActivity extends Activity implements OnClickListener {

	Button loginCancelBtn, loginBtn, cancelBtn, saveBtn;
	EditText passwordEt, oldPwdEt, newPwdEt, cnfmPwdEt;
	SharedPreferences preferences;
	Context context;
	boolean isPwdEnabled = false, isReset = false;
	LinearLayout loginLayout, setupLayout;
	TextView resetPwdTv,oldpwdTv;
	Intent intent;
	SharedPreferences.Editor editor;
	String oldPwd, newPwd, cnfPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		init();
	}

	private void init() {
		context = this;
		loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
		setupLayout = (LinearLayout) findViewById(R.id.newPwdLayout);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginCancelBtn = (Button) findViewById(R.id.loginCancelBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		saveBtn = (Button) findViewById(R.id.savePwdBtn);
		passwordEt = (EditText) findViewById(R.id.loginEt);
		oldPwdEt = (EditText) findViewById(R.id.oldpwdEt);
		newPwdEt = (EditText) findViewById(R.id.newPwdEt);
		cnfmPwdEt = (EditText) findViewById(R.id.cnfmPwdEt);
		resetPwdTv = (TextView) findViewById(R.id.resetPwdTv);
		oldpwdTv= (TextView) findViewById(R.id.oldpwdTv);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preferences.edit();
		isPwdEnabled = preferences
				.getBoolean(StringConstant.PWD_ENABLED, false);
		if (isPwdEnabled) {
			loginLayout.setVisibility(View.VISIBLE);
			setupLayout.setVisibility(View.GONE);

		}
loginBtn.setOnClickListener(this);
loginCancelBtn.setOnClickListener(this);
cancelBtn.setOnClickListener(this);
saveBtn.setOnClickListener(this);
resetPwdTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			String pwd = passwordEt.getText().toString().trim();
			if (pwd.equals("") | pwd == null) {
			//	passwordEt.setError("Please Ener the password");
				Toast.makeText(getApplicationContext(),
						"Please Ener the password",
						Toast.LENGTH_LONG).show();
			} else {
				if (pwd.equals(preferences.getString(StringConstant.LOGIN_PWD,
						""))) {
					startNavigation();

				} else {
				//	passwordEt.setError("Wrong Password");
					Toast.makeText(getApplicationContext(),
							"Wrong Password",
							Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.loginCancelBtn:
			finish();
			break;
		case R.id.savePwdBtn:
			if (isReset) {
				oldPwd = oldPwdEt.getText().toString().trim();
				if (oldPwd.equals(preferences.getString(
						StringConstant.LOGIN_PWD, ""))) {
					confirmNewPwd();
				} else {
					//oldPwdEt.setError("Old password not matching");
					Toast.makeText(getApplicationContext(),
							"Old password not matching",
							Toast.LENGTH_LONG).show();
				}
			} else {
				confirmNewPwd();
			}

			break;
		case R.id.cancelBtn:
			finish();
			break;
		case R.id.resetPwdTv:
			loginLayout.setVisibility(View.GONE);
			setupLayout.setVisibility(View.VISIBLE);
			isReset = true;
			oldPwdEt.setVisibility(View.VISIBLE);
oldpwdTv.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	private void confirmNewPwd() {

		newPwd = newPwdEt.getText().toString().trim();
		cnfPwd = cnfmPwdEt.getText().toString().trim();
		if (newPwd.equals(cnfPwd)) {
			if (newPwd.length() > 4) {
				editor.putBoolean(StringConstant.PWD_ENABLED, true);
				editor.putString(StringConstant.LOGIN_PWD, newPwd);
				editor.commit();
				startNavigation();
			} else {
				Toast.makeText(getApplicationContext(),
						"Password length should be greater han 4",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Password not matching",
					Toast.LENGTH_LONG).show();
		}
	}

	private void startNavigation() {
		editor.putString(StringConstant.PROVIDER_ID, "4");
		editor.commit();
		intent = new Intent(getApplicationContext(), HomePage.class);
		intent.putExtra(StringConstant.PROVIDER_ID, "4");
		startActivity(intent);
		finish();
	}
}
