package com.example.admin.mpesabteem.mpos.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Money {
	Context context;
	String provider;

	public Money(Context context) {
		super();
		this.context = context;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		provider = preferences.getString(StringConstant.PROVIDER_ID, "");
	}

	public String getFormat() {

		switch (provider) {
		case "1":
			return "ksh";
		case "2":
			return "$";
		case "3":
			return "ksh";
		case "4":
			return "$";
		default:
			break;
		}
		return null;

	}
}
