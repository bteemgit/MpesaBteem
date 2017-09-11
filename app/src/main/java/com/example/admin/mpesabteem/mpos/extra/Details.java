package com.example.admin.mpesabteem.mpos.extra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Details {
	String ref_ID;
	Float amount;
	String name;
	Float balance;
	String date;
	String typeId;

	public String getRef_ID() {
		return ref_ID;
	}

	public void setRef_ID(String ref_ID) {
		this.ref_ID = ref_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public String getParsedDate() {
		return new FormatDate().setDateFormat(date);
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

}
