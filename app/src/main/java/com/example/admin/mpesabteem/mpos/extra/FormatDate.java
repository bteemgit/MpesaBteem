package com.example.admin.mpesabteem.mpos.extra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
	SimpleDateFormat sdf;

	public String setDate(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "dd-MMM";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setMonth(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "MMM";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setYear(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "yyyy";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setDateFormat(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "dd-MM-yyyy";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}

	public String setDate(String date, String time) {
		final String OLD_FORMAT = "dd/MM/yy h:mm a";
		final String NEW_FORMAT = "yyyy-MM-dd h:mm a";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date + " " + time);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setDateZadd(String date) {
		final String OLD_FORMAT = "MM/dd/yyyy h:mm:ss a";
		final String NEW_FORMAT = "yyyy-MM-dd h:mm a";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}

	public String setTime(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "h:mm a";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setDateOnly(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "dd";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}

	public String setNormalDate(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "yyyy-MM-dd";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
	public String setNormalMonth(String date) {
		final String OLD_FORMAT = "yyyy-MM-dd h:mm a";
		final String NEW_FORMAT = "yyyy-MM";

		String newDate = "";
		try {
			sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDate = sdf.format(d);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return newDate;
	}
}
