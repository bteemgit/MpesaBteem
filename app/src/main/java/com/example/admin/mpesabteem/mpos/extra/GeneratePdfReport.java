package com.example.admin.mpesabteem.mpos.extra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.admin.mpesabteem.mpos.database.MposDao;
import com.example.admin.mpesabteem.mpos.database.StringQuery;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class GeneratePdfReport extends AsyncTask<String, String, String> {
	MposDao dao;
	FormatDate fd;
	Context context;
	Font bfBold12, bf12, headFont;
	ArrayList<Details> details;
	String sent, recieve, date;
	BaseColor black, white, headerColor, lightblue;
	Money m;
	String mf;
	AsyncResponse delegate = null;
	ProgressDialog pd;

	public GeneratePdfReport(Context context, AsyncResponse delegate) {
		super();

		this.context = context;
		dao = new MposDao(context);
		fd = new FormatDate();
		m = new Money(context);
		mf = m.getFormat();
		bfBold12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(
				0, 0, 0));
		headFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
		bf12 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

		black = WebColors.getRGBColor("#000000");
		white = WebColors.getRGBColor("#ffffff");
		lightblue = WebColors.getRGBColor("#1f99e0");
		headerColor = WebColors.getRGBColor("#0474b5");

		this.delegate = delegate;
	}

	public void createPDF(String pdfFilename, String type) {

		bf12.setColor(black);
		bfBold12.setColor(white);
		headFont.setColor(headerColor);
		Document doc = new Document();
		PdfWriter docWriter = null;
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/mpos");
		directory.mkdirs();
		try {

			File file = new File(directory, pdfFilename);
			FileOutputStream out = new FileOutputStream(file);
			docWriter = PdfWriter.getInstance(doc, out);

			doc.addAuthor("bteem");
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator("Bteem");
			doc.addTitle("Transaction History");
			doc.setPageSize(PageSize.A4);

			doc.open();
			doc.add(AddContent(type));

		} catch (DocumentException dex) {
			dex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (doc != null) {

				doc.close();
			}
			if (docWriter != null) {

				docWriter.close();
			}
		}
	}

	private Paragraph AddContent(String type) {
		switch (type) {
		case "recent":
			return createRecent();

		case "time":

			return createTime();
		case "person":

			return createPerson();
		case "type":
			return createType();

		default:
			break;
		}
		return null;

	}

	private Paragraph createTime() {
		ArrayList<String> month, year, dateList;
		month = new ArrayList<String>();
		year = new ArrayList<String>();
		dateList = new ArrayList<String>();
		details = new ArrayList<Details>();
		year = dao.getYears();
		Paragraph paragraph = new Paragraph();
		Chunk chunk = new Chunk("TRANSACTION BASED ON TIME");
		chunk.setFont(headFont);
		paragraph.add(chunk);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		float[] columnWidths = { 2f, 3f, 2f, 2f };
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		insertCell(table, "Time", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		insertCell(table, "Name", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		insertCell(table, "Sent", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		insertCell(table, "Recieve", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		table.setHeaderRows(1);
		insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12, white);
		for (String y : year) {
			month = dao.getMonths(fd.setYear(y));
			for (String m : month) {
				dateList = dao.getDatesForMonth(fd.setNormalMonth(m));
				for (String d : dateList) {

					insertCell(table, fd.setDateFormat(d), Element.ALIGN_LEFT,
							4, bfBold12, lightblue);
					details = dao.getDetailsDay(fd.setNormalDate(d));
					for (Details dtl : details) {
						String stype = dao.getSuperType(dtl.getTypeId(),
								StringQuery.super_type_name_query);
						insertCell(table, fd.setTime(dtl.getDate()),
								Element.ALIGN_LEFT, 1, bf12, white);
						insertCell(table, dtl.getName(), Element.ALIGN_LEFT, 1,
								bf12, white);
						if (stype.equals(StringConstant.ST_SENT)) {
							sent = mf + dtl.getAmount().toString();
							recieve = "";
						} else {
							sent = "";
							recieve = mf + dtl.getAmount().toString();
						}
						insertCell(table, sent, Element.ALIGN_LEFT, 1, bf12,
								white);
						insertCell(table, recieve, Element.ALIGN_LEFT, 1, bf12,
								white);
					}
				}

			}
		}

		paragraph.add(table);
		return paragraph;

	}

	private Paragraph createType() {
		ArrayList<String> type, dateList;
		type = new ArrayList<String>();
		dateList = new ArrayList<String>();
		type = dao.getTypes();
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		Chunk chunk = new Chunk("TRANSACTION BASED ON TYPE");
		chunk.setFont(headFont);
		paragraph.add(chunk);
		float[] columnWidths = { 2.5f, 2.5f };
		PdfPTable table = new PdfPTable(columnWidths);

		table.setWidthPercentage(50f);

		insertCell(table, "Date", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);

		insertCell(table, "Total", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);

		table.setHeaderRows(1);
		insertCell(table, "", Element.ALIGN_LEFT, 2, bfBold12, white);
		for (int x = 0; x < type.size(); x++) {

			insertCell(table, type.get(x), Element.ALIGN_LEFT, 2, bfBold12,
					lightblue);
			dateList = dao.getDatesForType(type.get(x));
			for (int i = 0; i < dateList.size(); i++) {

				date = (fd.setDateFormat(dateList.get(i)));
				sent = mf
						+ (dao.getTotTypeDate(type.get(x),
								fd.setNormalDate(dateList.get(i))).toString());
				insertCell(table, date, Element.ALIGN_LEFT, 1, bf12, white);
				insertCell(table, sent, Element.ALIGN_LEFT, 1, bf12, white);

			}

		}
		paragraph.add(table);
		return paragraph;

	}

	private Paragraph createPerson() {
		ArrayList<String> person, dateList;
		person = new ArrayList<String>();
		dateList = new ArrayList<String>();
		person = dao.getPerson();
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		Chunk chunk = new Chunk("TRANSACTION BASED ON PERSON");
		chunk.setFont(headFont);
		paragraph.add(chunk);
		float[] columnWidths = { 2.5f, 2f, 2f };
		PdfPTable table = new PdfPTable(columnWidths);

		table.setWidthPercentage(90f);

		insertCell(table, "Date", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);

		insertCell(table, "Sent", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		insertCell(table, "Receive", Element.ALIGN_CENTER, 1, bfBold12,
				headerColor);
		table.setHeaderRows(1);
		insertCell(table, "", Element.ALIGN_LEFT, 3, bfBold12, white);
		for (int x = 0; x < person.size(); x++) {

			insertCell(table, person.get(x), Element.ALIGN_LEFT, 3, bfBold12,
					lightblue);
			dateList = dao.getDatesForPerson(person.get(x));
			for (int i = 0; i < dateList.size(); i++) {

				date = (fd.setDateFormat(dateList.get(i)));
				sent = mf
						+ (dao.getTPD(person.get(x), "Sent",
								fd.setNormalDate(dateList.get(i))).toString());
				recieve = mf
						+ (dao.getTPD(person.get(x), "Recieve",
								fd.setNormalDate(dateList.get(i))).toString());

				insertCell(table, date, Element.ALIGN_LEFT, 1, bf12, white);
				insertCell(table, sent, Element.ALIGN_LEFT, 1, bf12, white);
				insertCell(table, recieve, Element.ALIGN_LEFT, 1, bf12, white);
			}

		}
		paragraph.add(table);
		return paragraph;

	}

	private Paragraph createRecent() {
		details = new ArrayList<Details>();
		details = dao.getRecentDetails();
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		Chunk chunk = new Chunk("RECENT TRANSACTION");
		chunk.setFont(headFont);
		paragraph.add(chunk);
		float[] columnWidths = { 1.5f, 5f, 2f, 2f };
		PdfPTable table = new PdfPTable(columnWidths);

		table.setWidthPercentage(90f);

		insertCell(table, "Date", Element.ALIGN_RIGHT, 1, bfBold12, headerColor);
		insertCell(table, "Name", Element.ALIGN_LEFT, 1, bfBold12, headerColor);
		insertCell(table, "Sent", Element.ALIGN_LEFT, 1, bfBold12, headerColor);
		insertCell(table, "Receive", Element.ALIGN_LEFT, 1, bfBold12,
				headerColor);
		table.setHeaderRows(1);

		insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12, white);

		for (int x = 0; x < details.size(); x++) {

			Details dtlList = details.get(x);
			String stype = dao.getSuperType(dtlList.getTypeId(),
					StringQuery.super_type_name_query);
			insertCell(table, dtlList.getDate(), Element.ALIGN_RIGHT, 1, bf12,
					white);
			insertCell(table, dtlList.getName(), Element.ALIGN_LEFT, 1, bf12,
					white);
			if (stype.equals(StringConstant.ST_SENT)) {
				sent = mf + dtlList.getAmount().toString();
				recieve = "";
			} else {
				sent = "";
				recieve = mf + dtlList.getAmount().toString();
			}
			insertCell(table, sent, Element.ALIGN_LEFT, 1, bf12, white);
			insertCell(table, recieve, Element.ALIGN_LEFT, 1, bf12, white);
		}
		paragraph.add(table);
		return paragraph;

	}

	private void insertCell(PdfPTable table, String text, int align,
							int colspan, Font font, BaseColor color) {

		// create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		// set the cell alignment
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(color);
		cell.setPaddingTop(7f);
		cell.setPaddingBottom(7f);
		cell.setPaddingRight(4f);
		// set the cell column span in case you want to merge two or more cells
		cell.setColspan(colspan);
		// in case there is no text and you wan to create an empty row
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(12f);
		}
		// add the call to the table
		table.addCell(cell);

	}

	@Override
	protected String doInBackground(String... params) {
		createPDF(params[0], params[1]);
		return null;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.show();
		pd.setMessage("Please wait...");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		delegate.processFinish(true);
		pd.dismiss();
		super.onPostExecute(result);
	}

}
