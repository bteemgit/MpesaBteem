package com.example.admin.mpesabteem.vivacom.mzaad;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.adapter.TimeAdapter;
import com.example.admin.mpesabteem.mpos.adapter.YearAdapter1;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.OptionDialog;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;


public class TimeActivity extends BaseActivity implements OnItemClickListener,
		OnClickListener {

	Context context;
	MposDao dao;
	ArrayList<Details> list;
	ArrayList<String> yearList, monthList;
	TextView rcvdAmnt, sentAmnt,yearTV;
	ListView yearView;
	LinearLayout layout;
	FormatDate fd;
	Money m;
	ImageView arrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		initialize();
		spinnerInit();
		TimeInit();
		init();
		yearView.setOnItemClickListener(this);
		upldBtn.setOnClickListener(this);
	}

	private void init() {
		context = this;
		dao = new MposDao(context);
		m = new Money(context);
		title.setText("SUMMARY BY YEAR & MONTH");
		titleIcon.setImageResource(R.drawable.timeiconsmall);
		mainTitileBg.setBackgroundColor(Color.parseColor("#c85826"));
		yearView = (ListView) findViewById(R.id.yaerList);
		fd = new FormatDate();
		layout = (LinearLayout) findViewById(R.id.ll3);
		rcvdAmnt = (TextView) findViewById(R.id.rcvdAmt);
		sentAmnt = (TextView) findViewById(R.id.sentAmt);
		yearTV=(TextView)findViewById(R.id.yearTV);
		arrow = (ImageView) findViewById(R.id.arrow);
		// list = dao.getDetails("");
		yearList = new ArrayList<String>();
		monthList = new ArrayList<String>();
		yearList = dao.getYears();
		listView.setGroupIndicator(null);

		YearAdapter1 adapter = new YearAdapter1(context, yearList);
		yearView.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		layout.setVisibility(View.VISIBLE);
		yearView.setVisibility(View.INVISIBLE);
		arrow.setImageResource(R.drawable.white_down);
		monthList = dao.getMonths(fd.setYear(yearList.get(position)));
		TimeAdapter adapter1 = new TimeAdapter(context, monthList);
		listView.setAdapter(adapter1);
		sentAmnt.setText(m.getFormat() + dao.getTotalSuperType(

		fd.setYear(yearList.get(position)), StringConstant.ST_SENT).toString());
		rcvdAmnt.setText(m.getFormat()
				+ dao.getTotalSuperType(fd.setYear(yearList.get(position)),
						StringConstant.ST_RECIEVE).toString());
		yearTV.setText(fd.setYear(yearList.get(position)));
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout.setVisibility(View.INVISIBLE);
				yearView.setVisibility(View.VISIBLE);
				arrow.setImageResource(R.drawable.arrow_white);
			}
		});
	}

	@Override
	public void onClick(View v) {
		OptionDialog dialog = new OptionDialog(context, "time");
		dialog.ShowDialog();

	}

}
