package com.example.admin.mpesabteem.mpos.extra;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.mpos.database.MposDao;


public class DetailDialog {
	Context context;
	Dialog dialog;
	TextView typeTv, nameTv, dateTv, amountTv, balanceTv, name;
	Details details;
	MposDao dao;
	String mf;

	public DetailDialog(Context context) {
		super();
		this.context = context;
		dao = new MposDao(context);
		mf = new Money(context).getFormat();
	}

	public void ShowDialog(Details details) {
		this.details = details;
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.detail_dialog);

		typeTv = (TextView) dialog.findViewById(R.id.typeTv);
		nameTv = (TextView) dialog.findViewById(R.id.nameTv);
		dateTv = (TextView) dialog.findViewById(R.id.dateTv);
		amountTv = (TextView) dialog.findViewById(R.id.amountTv);
		balanceTv = (TextView) dialog.findViewById(R.id.balanceTv);
		name = (TextView) dialog.findViewById(R.id.Dname);
		setData(details);
		dialog.show();
		dialog.setCancelable(true);
	}

	private void setData(Details details) {

		typeTv.setText(dao.getType(details.getTypeId()));
		String typ_id = details.getTypeId();
		if (typ_id.equals("5") | typ_id.equals("6") | typ_id.equals("7")
				| typ_id.equals("10")) {
			name.setText("Number");
		}
		nameTv.setText(details.getName());
		dateTv.setText(details.getDate());
		amountTv.setText(mf + details.getAmount().toString());
		balanceTv.setText(mf + details.getBalance().toString());
	}
}
