package com.example.admin.mpesabteem.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.database.StringQuery;
import com.example.admin.mpesabteem.mpos.extra.DetailDialog;
import com.example.admin.mpesabteem.mpos.extra.Details;
import com.example.admin.mpesabteem.mpos.extra.FormatDate;
import com.example.admin.mpesabteem.mpos.extra.Money;
import com.example.admin.mpesabteem.mpos.extra.StringConstant;

import java.util.ArrayList;


public class PersonChildAdapter extends BaseExpandableListAdapter {

	ArrayList<String> personList, dateList;
	private Context context;
	MposDao dao;
	FormatDate fd;
	Money m;
	String mf, person;

	public PersonChildAdapter(Context context, String person) {
		super();
		this.context = context;
		this.person = person;

		dao = new MposDao(context);
		fd = new FormatDate();
		m = new Money(context);
		mf = m.getFormat();
		this.dateList = dao.getDatesForPerson(person);
	}

	@Override
	public int getGroupCount() {
		return dateList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return dao.getPersonDetailDay(fd.setNormalDate(dateList.get(groupPosition)), person)
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return dateList.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return dao.getPersonDetailDay(fd.setNormalDate(dateList.get(groupPosition)), person)
				.get(childPosition);
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
		View header = inflater.inflate(R.layout.time_person_item, null);
		TextView name = (TextView) header.findViewById(R.id.nameDate);
		TextView rcv = (TextView) header.findViewById(R.id.recvTV);
		TextView sent = (TextView) header.findViewById(R.id.sentTV);

		name.setText(fd.setDate(dateList.get(groupPosition)));
		sent.setText(mf
				+ dao.getTPD(person, "Sent",
						fd.setNormalDate(dateList.get(groupPosition)))
						.toString());
		rcv.setText(mf
				+ dao.getTPD(person, "Recieve",
						fd.setNormalDate(dateList.get(groupPosition)))
						.toString());

		return header;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final Details details = dao.getPersonDetailDay(fd.setNormalDate(dateList.get(groupPosition)),
				person).get(childPosition);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.custom_child_item, null);
		TextView name = (TextView) header.findViewById(R.id.nameTime);
		TextView amount = (TextView) header.findViewById(R.id.amount);
		LinearLayout item_layout = (LinearLayout) header
				.findViewById(R.id.item_layout);
		name.setText(fd.setTime(details.getDate()));
		if (dao.getSuperType(details.getTypeId(),
				StringQuery.super_type_name_query).equals(
				StringConstant.ST_SENT)) {
			amount.setTextColor(context.getResources().getColor(R.color.colorAccent));
		} else {

			amount.setTextColor(context.getResources().getColor(R.color.colorPrimary));

		}
		amount.setText(mf + details.getAmount().toString());
		item_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new DetailDialog(context).ShowDialog(details);
			}
		});
		return header;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
