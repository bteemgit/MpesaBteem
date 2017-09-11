package com.example.admin.mpesabteem.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.MyExpandableListView;

import java.util.ArrayList;


public class PersonAdapter extends BaseExpandableListAdapter {

	ArrayList<String> personList, dateList;
	private Context context;
	MposDao dao;
	FormatDate fd;
	Money m;
	String mf;

	public PersonAdapter(Context context, ArrayList<String> person) {
		super();
		this.context = context;
		this.personList = person;

		dao = new MposDao(context);
		fd = new FormatDate();
		m = new Money(context);
		mf = m.getFormat();
	}

	@Override
	public int getGroupCount() {
		return personList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return personList.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		/*
		 * return dao.getDatesForPerson(personList.get(groupPosition)).get(
		 * childPosition);
		 */
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
		View header = inflater.inflate(R.layout.recent_title, null);
		TextView name = (TextView) header.findViewById(R.id.nameDate);
		TextView rcv = (TextView) header.findViewById(R.id.recvTV);
		TextView sent = (TextView) header.findViewById(R.id.sentTV);

		name.setText(personList.get(groupPosition));
		sent.setText(mf
				+ dao.getTotalPerson(personList.get(groupPosition), "Sent")
						.toString());
		rcv.setText(mf
				+ dao.getTotalPerson(personList.get(groupPosition), "Recieve")
						.toString());
		return header;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		/*
		 * dateList = dao.getDatesForPerson(personList.get(groupPosition));
		 * LayoutInflater inflater = (LayoutInflater) context
		 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View header =
		 * inflater.inflate(R.layout.time_person_item, null); TextView name =
		 * (TextView) header.findViewById(R.id.nameDate); TextView rcv =
		 * (TextView) header.findViewById(R.id.recvTV); TextView sent =
		 * (TextView) header.findViewById(R.id.sentTV);
		 * 
		 * name.setText(fd.setDate(dateList.get(childPosition)));
		 * sent.setText(mf+dao.getTPD(personList.get(groupPosition), "Sent",
		 * fd.setNormalDate(dateList.get(childPosition))).toString());
		 * rcv.setText(mf+dao.getTPD(personList.get(groupPosition), "Recieve",
		 * fd.setNormalDate(dateList.get(childPosition))).toString());
		 */
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.inner_item, null);
		MyExpandableListView listView = (MyExpandableListView) header
				.findViewById(R.id.list);
		PersonChildAdapter adapter1 = new PersonChildAdapter(context,
				personList.get(groupPosition));
		listView.setGroupIndicator(null);
		listView.setAdapter(adapter1);

		return header;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
