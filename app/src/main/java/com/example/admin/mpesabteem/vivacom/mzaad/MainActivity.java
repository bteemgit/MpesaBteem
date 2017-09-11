package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

public class MainActivity extends BaseActivity implements OnClickListener {
	ImageView airtel, orangeMoney, safaricom, zaad;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		airtel.setOnClickListener(this);
		safaricom.setOnClickListener(this);
		zaad.setOnClickListener(this);
		footer.setOnClickListener(this);
	}

	private void init() {
		context = this;
		airtel = (ImageView) findViewById(R.id.airtel);
		orangeMoney = (ImageView) findViewById(R.id.orangeMoney);
		safaricom = (ImageView) findViewById(R.id.safaricom);
		zaad = (ImageView) findViewById(R.id.zaad);
		footer = (ImageView) findViewById(R.id.footerImg);
		preference = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preference.edit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.airtel:
			editor.putString(StringConstant.PROVIDER_ID, "1");
			editor.commit();
			intent = new Intent(getApplicationContext(), HomePage.class);
			intent.putExtra(StringConstant.PROVIDER_ID, "1");
			startActivity(intent);
			break;
		case R.id.safaricom:
			editor.putString(StringConstant.PROVIDER_ID, "3");
			editor.commit();
			intent = new Intent(getApplicationContext(), HomePage.class);
			intent.putExtra(StringConstant.PROVIDER_ID, "3");
			startActivity(intent);
			break;
		case R.id.zaad:
			editor.putString(StringConstant.PROVIDER_ID, "4");
			editor.commit();
			intent = new Intent(getApplicationContext(), HomePage.class);
			intent.putExtra(StringConstant.PROVIDER_ID, "4");
			startActivity(intent);
			break;
		case R.id.footerImg:
			finish();
			break;
		default:
			break;
		}

	}
}
