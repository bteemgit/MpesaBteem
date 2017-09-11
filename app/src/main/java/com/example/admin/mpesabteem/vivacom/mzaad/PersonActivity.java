package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.adapter.PersonAdapter;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.OptionDialog;

import java.util.ArrayList;


public class PersonActivity extends BaseActivity implements OnClickListener
	{
	ExpandableListView listView;
	Context context;
	MposDao dao;
	ArrayList<Details> list;
	ArrayList<String> person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		initialize();
		spinnerInit();
		TimeInit();
		init();
		upldBtn.setOnClickListener(this);
	}

	private void init() {
		context = this;
		dao = new MposDao(context);

		title.setText("PERSON/BUSINESS");
		titleIcon.setImageResource(R.drawable.personiconsmall);
		mainTitileBg.setBackgroundColor(Color.parseColor("#2481bc"));

		listView = (ExpandableListView) findViewById(R.id.list);

		person = new ArrayList<String>();
		person = dao.getPerson();
		listView.setGroupIndicator(null);

		PersonAdapter adapter = new PersonAdapter(context, person);
		listView.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		OptionDialog dialog = new OptionDialog(context, "person");
		dialog.ShowDialog();

	}

}
