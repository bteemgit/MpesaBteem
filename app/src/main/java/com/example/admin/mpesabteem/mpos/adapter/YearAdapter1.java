package com.example.admin.mpesabteem.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;

public class YearAdapter1 extends BaseAdapter {

	Context context;
	ArrayList<String> yearList;
	FormatDate fd;
	MposDao dao;
	Money m;
	String mf;

	public YearAdapter1(Context context, ArrayList<String> yearList

	) {
		this.context = context;
		this.yearList = yearList;
		fd = new FormatDate();
		dao = new MposDao(context);
		m = new Money(context);
		mf = m.getFormat();
	}

	@Override
	public int getCount() {

		return yearList.size();
	}

	@Override
	public Object getItem(int pos) {

		return yearList.get(pos);
	}

	@Override
	public long getItemId(int pos) {

		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {

		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = vi.inflate(R.layout.custom_year, null);
		TextView year = (TextView) convertView.findViewById(R.id.yearTV);
		TextView sent = (TextView) convertView.findViewById(R.id.sentAmt);
		TextView recv = (TextView) convertView.findViewById(R.id.rcvdAmt);
		year.setText(fd.setYear(yearList.get(pos)));
		sent.setText(mf
				+ dao.getTotalSuperType(fd.setYear(yearList.get(pos)),
						StringConstant.ST_SENT).toString());
		recv.setText(mf
				+ dao.getTotalSuperType(fd.setYear(yearList.get(pos)),
						StringConstant.ST_RECIEVE).toString());
		return convertView;
	}

}
