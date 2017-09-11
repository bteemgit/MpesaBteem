package com.example.admin.mpesabteem.mpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.database.StringQuery;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.MyExpandableListView;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;


public class TypeAdapter extends BaseExpandableListAdapter {

	ArrayList<String> typeList, dateList;
	private Context context;
	MposDao dao;
	FormatDate fd;
	Money m;
	String mf;

	public TypeAdapter(Context context, ArrayList<String> type) {
		super();
		this.context = context;
		this.typeList = type;

		dao = new MposDao(context);
		fd = new FormatDate();
		m = new Money(context);
		mf = m.getFormat();
	}

	@Override
	public int getGroupCount() {
		return typeList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return typeList.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

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
		View header = inflater.inflate(R.layout.custom_child_item, null);
		header.setBackgroundColor(Color.WHITE);
		TextView name = (TextView) header.findViewById(R.id.nameTime);
		TextView sent = (TextView) header.findViewById(R.id.amount);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		arrow.setVisibility(View.VISIBLE);
		// TextView sent = (TextView) header.findViewById(R.id.sentTV);
		name.setText(typeList.get(groupPosition));
		if (dao.getSuper_Type(typeList.get(groupPosition),
				StringQuery.ST_FOR_TYPE).equals(StringConstant.ST_SENT)) {
			sent.setTextColor(context.getResources().getColor(R.color.colorAccent));
			sent.setText(mf
					+ dao.getTotalType(typeList.get(groupPosition), "Sent")
							.toString());
		} else {
			sent.setTextColor(context.getResources().getColor(R.color.colorPrimary));
			sent.setText(mf + dao.getTotalType(typeList.get(groupPosition),
							StringConstant.ST_RECIEVE).toString());
		}
		return header;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.inner_item, null);
		MyExpandableListView listView = (MyExpandableListView) header
				.findViewById(R.id.list);
		TypeChildAdapter adapter1 = new TypeChildAdapter(context,
				typeList.get(groupPosition));
		listView.setGroupIndicator(null);
		listView.setAdapter(adapter1);

		return header;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
