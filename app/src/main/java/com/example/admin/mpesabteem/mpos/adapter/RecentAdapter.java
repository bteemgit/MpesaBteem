package com.example.admin.mpesabteem.mpos.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
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

import static com.wagnerandade.coollection.Coollection.eq;
import static com.wagnerandade.coollection.Coollection.from;


public class RecentAdapter extends BaseExpandableListAdapter {

	private Context context;
	ArrayList<Details> list;
	ArrayList<String> dateList;
	FormatDate fd;
	MposDao dao;
	String cat, mf;
	int layout;
	Money m;

	public RecentAdapter(Context context, ArrayList<Details> list,
			ArrayList<String> date, int timePersonItem, String cat) {
		super();
		this.context = context;
		this.list = list;
		this.dateList = date;
		fd = new FormatDate();
		dao = new MposDao(context);
		this.layout = timePersonItem;
		this.cat = cat;
		m = new Money(context);
		mf = m.getFormat();
	}

	@Override
	public int getGroupCount() {
		return dateList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return from(list)
				.where("getParsedDate",
						eq(fd.setDateFormat(dateList.get(groupPosition))))
				.all().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return dateList.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return (Details) from(list)
				.where("getParsedDate",
						eq(fd.setDateFormat(dateList.get(groupPosition))))
				.all().get(childPosition);
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
		View header = inflater.inflate(layout, null);
		TextView date = (TextView) header.findViewById(R.id.nameDate);
		TextView sent = (TextView) header.findViewById(R.id.sentTV);
		TextView recieve = (TextView) header.findViewById(R.id.recvTV);
		ImageView indc = (ImageView) header.findViewById(R.id.arrow);
		if (cat.equals("time")) {
			date.setText(fd.setDateOnly(dateList.get(groupPosition)));
		} else {
			date.setText(fd.setDate(dateList.get(groupPosition)));
		}

		sent.setText(mf
				+ dao.getTotalSuperType(
						fd.setNormalDate(dateList.get(groupPosition)), "Sent")
						.toString());
		recieve.setText(mf
				+ dao.getTotalSuperType(
						fd.setNormalDate(dateList.get(groupPosition)),
						"Recieve").toString());
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

		final Details details = (Details) from(list)
				.where("getParsedDate",
						eq(fd.setDateFormat(dateList.get(groupPosition))))
				.all().get(childPosition);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.custom_recent_item, null);
		TextView nameTime = (TextView) header.findViewById(R.id.nameTime);
		TextView amount = (TextView) header.findViewById(R.id.amount);
		LinearLayout item_layout = (LinearLayout) header
				.findViewById(R.id.item_layout);
		if (cat.equals("time")) {
			item_layout.setBackgroundColor(context.getResources().getColor(
					R.color.colorAccent));
		}
		// TextView sType = (TextView) header.findViewById(R.id.superType);
		nameTime.setText(fd.setTime(details.getDate()) + " "
				+ details.getName());
		if (dao.getSuperType(details.getTypeId(),
				StringQuery.super_type_name_query).equals(
				StringConstant.ST_SENT)) {
			amount.setTextColor(context.getResources().getColor(R.color.colorAccent));
		} else {

			amount.setTextColor(context.getResources().getColor(R.color.colorPrimary));

		}
		amount.setText(mf + details.getAmount().toString());
		// sType.setText(dao.getSuperType(details.getTypeId(),
		// StringQuery.super_type_name_query));
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
