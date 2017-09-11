package com.example.admin.mpesabteem.mpos.extra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.example.admin.mpesabteem.mpos.database.MposDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageReciever extends BroadcastReceiver {
	int typeID = 1;
	MposDao dao;
	Context context;
	String ref_id, date, time, name, date_sent;
	Float balance, amount;
	FormatDate fd;
	static String en = "EN", som = "SOM";
	public static final String SMS_BUNDLE = "pdus";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//  if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
		this.context = context;
		dao = new MposDao(context);
		fd = new FormatDate();

		Bundle intentExtras = intent.getExtras();
		if (intentExtras != null) {
			try {
				Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
				String smsMessageStr = "";
				for (int i = 0; i < sms.length; ++i) {
					SmsMessage smsMessage = SmsMessage
							.createFromPdu((byte[]) sms[i]);

					smsMessageStr = smsMessage.getMessageBody().toString();
					// String address = smsMessage.getOriginatingAddress();

					// smsMessageStr = "SMS From: " + address + "\n";
					smsMessageStr=smsMessageStr==null?"":smsMessageStr;
					InsertMessage(smsMessageStr, "4");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			}
			 /* Bundle extras = intent.getExtras();

			  String messages = "";

			  if ( extras != null )
			  {
				  // Get received SMS array
				  Object[] smsExtra = (Object[]) extras.get( SMS_BUNDLE );

				  // Get ContentResolver object for pushing encrypted SMS to incoming folder
				  ContentResolver contentResolver = context.getContentResolver();

				  for ( int i = 0; i < smsExtra.length; ++i )
				  {
					  SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);

					  String body = sms.getMessageBody().toString();
					  String address = sms.getOriginatingAddress();

					  messages += "SMS from " + address + " :\n";
					  messages += body + "\n";

					  // Here you can add any your code to work with incoming SMS
					  // I added encrypting of all received SMS
					  InsertMessage(body, "4");
					  dao.InsertSample(body);
				  }

				  // Display SMS message
				  Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
			  }*/
	}

	private void InsertMessage(String msgData, String prvdrId) {



	/*	if (regex(StringConstant.RM_ZAAD).matcher(msgData).find()) {
			typeID = dao.gettypeID(StringConstant.RECIEVE);
			Pattern pattern = getPattern(prvdrId, en);
			Matcher m = pattern.matcher(msgData);
			IRM_Zaad(m, prvdrId);
		} */
    /*  if (regex(StringConstant.RCHGM_ZAAD).matcher(msgData).find()) {
			typeID = dao.gettypeID(StringConstant.RECHARGE);
			Pattern pattern = getPattern(prvdrId, en);
			Matcher m = pattern.matcher(msgData);
			IRCH_Zaad(m, prvdrId);
		} else if (regex(StringConstant.DM_ZAAD).matcher(msgData).find()) {
			typeID = dao.gettypeID(StringConstant.DEPOSIT);
			Pattern pattern = getPattern(prvdrId, en);
			Matcher m = pattern.matcher(msgData);
			ID_Zaad(m, prvdrId);
		}*/ /*else if (regex(StringConstant.SM_ZAAD).matcher(msgData).find()) {

			if (regex(StringConstant.SM_SHOP_ZAAD).matcher(msgData).find()) {
				typeID = dao.gettypeID(StringConstant.SHOPPING);
				Pattern pattern = getPattern(prvdrId, en);
				Matcher m = pattern.matcher(msgData);
				ISMSHOP_Zaad(m, prvdrId);
			} else {
				typeID = dao.gettypeID(StringConstant.SENT);
				Pattern pattern = getPattern(prvdrId, en);
				Matcher m = pattern.matcher(msgData);
				ISM_Zaad(m, prvdrId);
			}

		} */
		/*else if (regex(StringConstant.DM_ZAAD_SOM).matcher(msgData)
				.find()) {
			typeID = dao.gettypeID(StringConstant.DEPOSIT);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			ID_Zaad(m, prvdrId);
		}*/
		/*else if (regex(StringConstant.RM_ZAAD_SOM).matcher(msgData)
				.find()) {
			typeID = dao.gettypeID(StringConstant.RECIEVE);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			IRM_Zaad(m, prvdrId);
		}
*/
/*	 else if (regex(StringConstant.SM_ZAAD_SOM).matcher(msgData)
				.find()) {
			 if (regex(StringConstant.SM_SHOP_ZAAD_SOM).matcher(msgData).find()) {
					typeID = dao.gettypeID(StringConstant.MERCHANT_SENT);
					Pattern pattern = getPattern(prvdrId, som);
					Matcher m = pattern.matcher(msgData);
					MER_Zaad(m, prvdrId);
				} else {
			typeID = dao.gettypeID(StringConstant.SENT);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			ISM_Zaad(m, prvdrId);
				}
				
		}*/
		
	/* else if (regex(StringConstant.SM_RECHARGE_SELF_SOM).matcher(msgData)
				.find()) {
			typeID = dao.gettypeID(StringConstant.RECHARGE);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			RE_Zaad(m, prvdrId);
		}*/
	
	/* else if (regex(StringConstant.WITH_ZAAD_SOM).matcher(msgData)
				.find()) {
			typeID = dao.gettypeID(StringConstant.WITHDRAW);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			IM_Zaad(m, prvdrId);
		}*/
		if (regex(StringConstant.MM_ZAAD_BTEEM).matcher(msgData)
				.find()) {
			typeID = dao.gettypeID(StringConstant.WITHDRAW);
			Pattern pattern = getPattern(prvdrId, en);
			Matcher m = pattern.matcher(msgData);
			IM_Zaad_bt(m, prvdrId);
		}
		
		else if (regex(StringConstant.MM_ZAAD_SOM).matcher(msgData)
				.find()) {
			dao.InsertSample(msgData);
			typeID = dao.gettypeID(StringConstant.WITHDRAW);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			IM_Zaad(m, prvdrId);
		} 
		/*else if (regex(StringConstant.WB_ZAAD_SOM).matcher(msgData).find()) {
			typeID = dao.gettypeID(StringConstant.WITHDRAWAL_BANK);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			ISMWB_Zaad(m, prvdrId);
		} else if (regex(StringConstant.TB_ZAAD_SOM).matcher(msgData).find()) {
			typeID = dao.gettypeID(StringConstant.TRANSFER_BANK);
			Pattern pattern = getPattern(prvdrId, som);
			Matcher m = pattern.matcher(msgData);
			ISMTB_Zaad(m, prvdrId);
		}
*/
	}
	
/*
	private void ISM_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = this.date_sent;
			amount = getfloat(m.group(2));
			name = m.group(3);
			m.groupCount();
			if (m.groupCount() != 4) {
				balance = Float.valueOf(0.0F);
			} else {
				balance = getfloat(m.group(4));
			}
			this.dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}

		
		

	}
	
	private void MER_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			name = (m.group(1) + "(" + m.group(4) + ")");
			ref_id = m.group(2);
			date = this.date_sent;
			amount = getfloat(m.group(3));
			m.groupCount();
			if (m.groupCount() != 5) {
				balance = Float.valueOf(0.0F);
			} else {
				balance = getfloat(m.group(5));
			}
			this.dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}

		
		

	}
*/

	/*private void ISMSHOP_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(2);
			date = this.date_sent;
			amount = getfloat(m.group(3));
			name = (m.group(1) + "(" + m.group(4) + ")");
			balance = getfloat(m.group(5));
			dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}
	}
	private void ISMWB_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = this.date_sent;
			amount = getfloat(m.group(2));
			name = m.group(3);
			balance = getfloat(m.group(4));
			dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}
	}*/
	private void ISMTB_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = "";
			date = this.date_sent;
			amount = getfloat(m.group(2));
			name = m.group(1);
			balance = 0.0f;
			dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}
	}


	private void IM_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2);
			amount = getfloat(m.group(3));
			name = "Zaad Withdraw";
			balance = getfloat(m.group(4));
			dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}
	}

	private void IM_Zaad_bt(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2)+" "+m.group(3);
			amount = getfloat(m.group(4));
			name = m.group(9);
			balance = getfloat(m.group(10));
			dao.InsertDetails(this.ref_id, this.fd.setDateZadd(this.date),
					this.amount, this.name, this.balance,
					Integer.toString(this.typeID), prvdrId);
		}
	}

/*	private void IRCH_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2);
			amount = getfloat(m.group(3));
			name = m.group(4);
			balance = getfloat(m.group(5));

			dao.InsertDetails(ref_id, fd.setDateZadd(date), amount, name,
					balance, Integer.toString(typeID), prvdrId);
		}
	}
	private void ID_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2);
			amount = getfloat(m.group(3));
			name = "Zaad Deposit";
			balance = getfloat(m.group(5));

			dao.InsertDetails(ref_id, fd.setDateZadd(date), amount, name,
					balance, Integer.toString(typeID), prvdrId);
		}
	}
	*/
/*	private void WD_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2);
			amount = getfloat(m.group(3));
			name = "Zaad Withdraw";
			balance = getfloat(m.group(5));

			dao.InsertDetails(ref_id, fd.setDateZadd(date), amount, name,
					balance, Integer.toString(typeID), prvdrId);
		}
	}
	
	private void RE_Zaad(Matcher m, String prvdrId) {
		if (m.find()) {
			ref_id = m.group(1);
			date = m.group(2);
			amount = getfloat(m.group(3));
			name = "Zaad Recharge";
			balance = getfloat(m.group(5));

			dao.InsertDetails(ref_id, fd.setDateZadd(date), amount, name,
					balance, Integer.toString(typeID), prvdrId);
		}
	}
	private void insert3(Matcher m, String prvdrId) {
		if (m.find()) {

			ref_id = m.group(1);
			amount = getfloat(m.group(2));
			name = "Safaricom";
			date = m.group(3);
			time = m.group(4);
			balance = getfloat(m.group(5));
			dao.InsertDetails(ref_id, fd.setDate(date, time), amount, name,
					balance, Integer.toString(typeID), prvdrId);
		}

	}

	private void insert2(Matcher m, String prvdrId) {
		if (m.find()) {

			ref_id = m.group(1);
			amount = getfloat(m.group(2));
			name = m.group(3);
			date = m.group(4);
			time = m.group(5);
			balance = getfloat(m.group(6));
			dao.InsertDetails(ref_id, fd.setDate(date, time), amount, name,
					balance, Integer.toString(typeID), prvdrId);

		}
	}

	private void insert1(Matcher m, String prvdrId) {

		if (m.find()) {

			ref_id = m.group(1);
			date = m.group(2);
			time = m.group(3);
			amount = getfloat(m.group(4));
			name = m.group(5);
			balance = getfloat(m.group(6));

			dao.InsertDetails(ref_id, fd.setDate(date, time), amount, name,
					balance, Integer.toString(typeID), prvdrId);

		}
	}

	private void IRM_Zaad(Matcher m, String prvdrId) {

		if (m.find()) {

			ref_id = m.group(1);
			amount = getfloat(m.group(2));
			name = m.group(3);
			date = m.group(4);
			balance = getfloat(m.group(5));

			dao.InsertDetails(ref_id, fd.setDateZadd(date), amount, name,
					balance, Integer.toString(typeID), prvdrId);

		}
	}*/



	private Float getfloat(String group) {
		String s = group.replace(",", "");
		Float f = Float.valueOf(s);
		return f;

	}

	private Pattern getPattern(String prvdrId, String lan) {
		Pattern r = Pattern.compile(dao.getRegEx(prvdrId,
				Integer.toString(typeID), lan));
		if (r == null) {
			r = Pattern.compile("No match found");
		}
		return r;
	}

	private Pattern regex(String deposit) {
		Pattern pattern = Pattern.compile(deposit);
		return pattern;
	}

}
