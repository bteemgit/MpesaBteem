package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.adapter.TypeAdapter;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.OptionDialog;

import java.util.ArrayList;


public class TypeActivity extends BaseActivity implements OnClickListener {
	ExpandableListView listView;
	Context context;
	MposDao dao;
	ArrayList<Details> list;
	ArrayList<String> type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_type);
		initialize();
		spinnerInit();
		TimeInit();
		init();
		upldBtn.setOnClickListener(this);
	}

	private void init() {
		context = this;
		dao = new MposDao(context);
		title.setText("TYPE OF TRANSACTION");
		titleIcon.setImageResource(R.drawable.typeiconsmall);
		mainTitileBg.setBackgroundColor(Color.parseColor("#0c7765"));

		listView = (ExpandableListView) findViewById(R.id.list);

		// list = dao.getDetails("");
		type = new ArrayList<String>();
		type = dao.getTypes();
		listView.setGroupIndicator(null);

		TypeAdapter adapter = new TypeAdapter(context, type);
		listView.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		OptionDialog dialog = new OptionDialog(context, "type");
		dialog.ShowDialog();

	}

}
