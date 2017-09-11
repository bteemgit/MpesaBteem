package com.example.admin.mpesabteem.mpos.extra;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.mpesabteem.R;
import com.example.admin.mpesabteem.vivacom.mzaad.PrintDialogActivity;

import java.io.File;


public class OptionDialog implements OnClickListener, AsyncResponse {
	Context context;
	Button printBtn, mailBtn;
	EditText lastNameEt, RoomNoEt;
	Dialog dialog;
	String lName, roomNo;
	SharedPreferences preferences;
	String type;
	GeneratePdfReport report;
	String option;

	public OptionDialog(Context context, String type) {
		super();
		this.context = context;
		this.type = type;

	}

	public void ShowDialog() {

		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.option_dialog);

		printBtn = (Button) dialog.findViewById(R.id.printBtn);
		mailBtn = (Button) dialog.findViewById(R.id.mailBtn);
		dialog.show();
		printBtn.setOnClickListener(this);
		mailBtn.setOnClickListener(this);
		dialog.setCancelable(true);
		report = new GeneratePdfReport(context, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.printBtn:
			option = "print";
			report.execute(type + ".pdf", type);
			break;
		case R.id.mailBtn:
			option = "mail";
			report.execute(type + ".pdf", type);

			break;

		default:
			break;
		}

	}

	private void mail() {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Transaction History-"
				+ type);
		emailIntent.setType("vnd.android.cursor.dir/email");
		Uri uri = getUri(type + ".pdf");
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

		context.startActivity(Intent.createChooser(emailIntent,
				"Select an Email Client:"));

	}

	@Override
	public void processFinish(boolean success) {
		Uri uri = getUri(type + ".pdf");
		if (option.equals("print")) {
			Intent printIntent = new Intent(context, PrintDialogActivity.class);
			printIntent.setDataAndType(uri, "application/pdf");
			printIntent.putExtra("title", "Transaction History-" + type);
			context.startActivity(printIntent);

		} else {
			mail();
		}
		dialog.dismiss();
	}

	private Uri getUri(String name) {
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/mpos");
		File f = new File(directory, name);
		return Uri.fromFile(f);
	}
}
