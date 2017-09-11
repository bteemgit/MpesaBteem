package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.adapter.RecentAdapter;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.OptionDialog;

import java.util.ArrayList;


public class RecentActivity extends BaseActivity implements OnClickListener {
	ExpandableListView listView;
	Context context;
	MposDao dao;
	ArrayList<Details> list,sapleArray;
	ArrayList<String> date,sample;
	TextView curBalance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent);
		initialize();
		init();
		upldBtn.setOnClickListener(this);
	}

	private void init() {

		context = this;
		dao = new MposDao(context);

		listView = (ExpandableListView) findViewById(R.id.list);
		curBalance = (TextView) findViewById(R.id.currBalance);
		list = dao.getRecentDetails();
		date = new ArrayList<>();
		sample = new ArrayList<>();

		date = dao.getDates();
		sapleArray = dao.getSamples();
		for (Details i : sapleArray) {
			sample.add(i.getName());

		}
		Toast.makeText(context, sample.toString(), Toast.LENGTH_SHORT).show();

		listView.setGroupIndicator(null);

		RecentAdapter adapter = new RecentAdapter(context, list, date,
				R.layout.recent_title, "recent");
		listView.setAdapter(adapter);
		
		   
		    preference = PreferenceManager.getDefaultSharedPreferences(context);
		    if (preference.getString("provider", "0").equals("1"))
		    {
		      curBalance.setText("not retrieved");
		     
		    }else {
			      curBalance.setText(new Money(context).getFormat() + String.valueOf(dao.getCurrentBalance()));
			      
			    }
		   
		
	}

	@Override
	public void onClick(View v) {
		OptionDialog dialog = new OptionDialog(context, "recent");
		dialog.ShowDialog();
		

	}


}
