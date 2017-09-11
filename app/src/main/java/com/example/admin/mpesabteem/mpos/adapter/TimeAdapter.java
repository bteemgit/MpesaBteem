package com.example.admin.mpesabteem.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.MyExpandableListView;

import java.util.ArrayList;


public class TimeAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	ArrayList<String> monthList, dateList;
	MposDao dao;
	String filter_type, filter_query, mf;
	FormatDate fd;
	Money m;

	public TimeAdapter(Context context, ArrayList<String> date) {
		super();
		this.context = context;
		this.monthList = date;
		dao = new MposDao(context);
		fd = new FormatDate();
		m = new Money(context);
		mf = m.getFormat();

	}

	@Override
	public int getGroupCount() {
		return monthList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	// dao.getDatesForMonth(
	// fd.setNormalMonth(monthList.get(groupPosition))).size()
	@Override
	public Object getGroup(int groupPosition) {
		return monthList.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	// dao.getDatesForMonth(
	// fd.setNormalMonth(monthList.get(groupPosition))).get(
	// childPosition)
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.time_person_title, null);
		TextView name = (TextView) header.findViewById(R.id.nameDate);
		TextView rcv = (TextView) header.findViewById(R.id.recvTV);
		TextView sent = (TextView) header.findViewById(R.id.sentTV);
		ImageView indc = (ImageView) header.findViewById(R.id.arrow);
		name.setText(fd.setMonth(monthList.get(groupPosition)));
		sent.setText(mf+dao.getTotalSuperType(
				fd.setNormalMonth(monthList.get(groupPosition)), "Sent")
				.toString());
		rcv.setText(mf+dao.getTotalSuperType(
				fd.setNormalMonth(monthList.get(groupPosition)), "Recieve")
				.toString());
		if (isExpanded) {
			indc.setImageResource(R.drawable.arrow_down);
		} else {
			indc.setImageResource(R.drawable.arrow);
		}
		return header;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		dateList = dao.getDatesForMonth(fd.setNormalMonth(monthList
				.get(groupPosition)));

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.inner_item, null);
		MyExpandableListView listView = (MyExpandableListView) header
				.findViewById(R.id.list);
		RecentAdapter adapter1 = new RecentAdapter(context, dao.getDetails(""),
				dateList, R.layout.time_person_item, "time");
		listView.setGroupIndicator(null);
		listView.setAdapter(adapter1);

		return header;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
