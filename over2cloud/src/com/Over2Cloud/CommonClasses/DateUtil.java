package com.Over2Cloud.CommonClasses;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT_INDIA = new SimpleDateFormat(
			"dd-MM-yyyy");
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMAT_Without_Hiphan = new SimpleDateFormat(
			"yyyyMMdd");

	public static Object format(String aValue, Format aFormat)

	{
		Object ret = null;
		if (aValue != null) {
			try {
				ret = aFormat.parseObject(aValue);
			} catch (ParseException pe) {
				pe.getStackTrace();
			}
		}
		return ret;
	}

	public static Date convertStringToDate(String aDate) {
		Date dt = null;
		try {
			dt = DATE_FORMAT.parse(aDate);
		} catch (ParseException pse) {
		}
		return dt;
	}

	public static String convertDateToUSFormat(String aDate) {

		String usDate = null;

		try {
			if(aDate.matches("[0-3]\\d-[01]\\d-\\d{4}"))	
			{
				String usd[] = aDate.split("-");
				usDate = usd[2] + "-" + usd[1] + "-" + usd[0];		
			}
			else{
				usDate=aDate;
			}
		}

		catch (Exception pse) {
		}

		return usDate;
	}


	public static String convertSlashDateToUSFormat(String aDate) {
		String usDate = null;

		try {
			String usd[] = aDate.split("/");
			usDate = usd[2] + "-" + usd[1] + "-" + usd[0];
		} catch (Exception pse) {
		}
		return usDate;
	}

	public static String convertDateToIndianFormat(String aDate) {
		String indianDate = null;

		try {
			if(aDate.matches("\\d{4}-[01]\\d-[0-3]\\d"))
			{
				String ind[] = aDate.split("-");
				indianDate = ind[2] + "-" + ind[1] + "-" + ind[0];
			}
			else{
				indianDate=aDate;
			}
		} catch (Exception pse) {
		}
		return indianDate;
	}

	public static double round(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	public static int timeDifferenceInMinute(Date aDate, Date bDate) {
		return (int) (aDate.getTime() - bDate.getTime()) / (1000 * 60);
	}

	@SuppressWarnings("static-access")
	public static String getAddressingDatetime(String date, String time,
			String time1, String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String time1arr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		time1arr = time1.split(":");
		adress_time_arr = address_time.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date);

			Calendar cal = Calendar.getInstance();
			// System.out.println("Date Time before   ::  "+cal.getTime());
			cal.setTime(convertedDate);
			// System.out.println("Date Time after   ::  "+cal.getTime());
			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));

			cal.add(cal.HOUR, Integer.parseInt(adress_time_arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(adress_time_arr[1]));
			NewDate = convertDateToIndianFormat(DATE_FORMAT.format(cal.getTime())) + " "
					+ cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return NewDate;
	}
	public static String getDayFromMonth(String month) 
	{
		String currentday="";
		try {
       
		if (month.equalsIgnoreCase("01")) 
		{
			currentday="January";
		}
		else if (month.equalsIgnoreCase("02")) 
		{
			currentday="February";
		}
		else if (month.equalsIgnoreCase("03")) 
		{
			currentday="March";
		}
		else if (month.equalsIgnoreCase("04")) 
		{
			currentday="April";
		}
		else if (month.equalsIgnoreCase("05")) 
		{
			currentday="May";
		}
		else if (month.equalsIgnoreCase("06")) 
		{
			currentday="June";
		}
		else if (month.equalsIgnoreCase("07")) 
		{
			currentday="July";
		}
		else if (month.equalsIgnoreCase("08")) 
		{
			currentday="August";
		}
		else if (month.equalsIgnoreCase("09")) 
		{
			currentday="September";
		}
		else if (month.equalsIgnoreCase("10")) 
		{
			currentday="October";
		}
		else if (month.equalsIgnoreCase("11")) 
		{
			currentday="November";
		}
		else if (month.equalsIgnoreCase("12")) 
		{
			currentday="December";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentday;
	}
	
	public static int getMonthFromDay(String month) 
	{
		int currentday=0;
		try {
       
		if (month.equalsIgnoreCase("01")) 
		{
			currentday=Calendar.JANUARY;
		}
		else if (month.equalsIgnoreCase("02")) 
		{
			currentday=Calendar.FEBRUARY;
		}
		else if (month.equalsIgnoreCase("03")) 
		{
			currentday=Calendar.MARCH;
		}
		else if (month.equalsIgnoreCase("04")) 
		{
			currentday=Calendar.APRIL;
		}
		else if (month.equalsIgnoreCase("05")) 
		{
			currentday=Calendar.MAY;
		}
		else if (month.equalsIgnoreCase("06")) 
		{
			currentday=Calendar.JUNE;
		}
		else if (month.equalsIgnoreCase("07")) 
		{
			currentday=Calendar.JULY;
		}
		else if (month.equalsIgnoreCase("08")) 
		{
			currentday=Calendar.AUGUST;
		}
		else if (month.equalsIgnoreCase("09")) 
		{
			currentday=Calendar.SEPTEMBER;
		}
		else if (month.equalsIgnoreCase("10")) 
		{
			currentday=Calendar.OCTOBER;
		}
		else if (month.equalsIgnoreCase("11")) 
		{
			currentday=Calendar.NOVEMBER;
		}
		else if (month.equalsIgnoreCase("12")) 
		{
			currentday=Calendar.DECEMBER;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentday;
	}
	
	public static Date getTimestamp(String aValue) {
		Date ret = null;
		if (aValue != null && aValue != "") {
			ret = (Date) format(aValue, TIMESTAMP_FORMAT);
			// search for milliseconds and apply half-even rounding
			// to higher precision number
			@SuppressWarnings("unused")
			int msIdx = aValue.lastIndexOf('.') + 1;
			/*
			 * if (aValue.length()-msIdx != 6) { String msg =
			 * "Incorrect format " + aValue + " does not match " +
			 * TIMESTAMP_FORMAT.toPattern() + ".SSSSSS";
			 * System.out.println(msg); } double millis =
			 * Double.parseDouble(aValue.substring(msIdx))/1000.0; millis =
			 * round(millis,0);
			 */
			Calendar cal = Calendar.getInstance();
			cal.setTime(ret);
			// cal.add(Calendar.MILLISECOND,(int)millis);
			ret = cal.getTime();
		}
		return ret;
	}

	public static String getCurrentDateUSFormat() {
		String CurrentDate = null;
		Calendar cal = Calendar.getInstance();
		CurrentDate = DATE_FORMAT.format(cal.getTime());

		// System.out.println(" CurrentDate "+CurrentDate);
		return CurrentDate;
	}

	public static String getCurrentDateIndianFormat() {
		String CurrentDate = null;
		Calendar cal = Calendar.getInstance();
		CurrentDate = DATE_FORMAT_INDIA.format(cal.getTime());

		// System.out.println(" CurrentDate "+CurrentDate);
		return CurrentDate;
	}

	public static String getCurrentTimeStamp() {
		String CurrentDate = null;
		Calendar cal = Calendar.getInstance();
		CurrentDate = TIMESTAMP_FORMAT.format(cal.getTime());
		return CurrentDate;
	}

	public static int getCurrentDay() {
		int cday;
		Calendar cal = Calendar.getInstance();
		cday = cal.get(cal.DAY_OF_WEEK) - 1;
		return new Integer(cday).intValue();
	}

	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		Integer month = new Integer(cal.get(Calendar.MONTH));
		Integer monthI = new Integer((month.intValue() + 1));
		return monthI.intValue();
	}

	public static int getCurretnYear() {
		Calendar cal = Calendar.getInstance();
		Integer year = new Integer(cal.get(Calendar.YEAR));
		return year.intValue();
	}

	public static String convertDateToString(Date aDate) {
		String strDate = null;
		try {
			strDate = DATE_FORMAT.format(aDate);
			// System.out.println("11111111111111111 "+strDate);
		} catch (Exception pse) {
		}
		return strDate;
	}

	public static String convertTimeStampToString(Date aDate) {
		String strDate = null;
		try {
			strDate = TIMESTAMP_FORMAT.format(aDate);
			// System.out.println(""+strDate);
		} catch (Exception pse) {
		}
		return strDate;
	}

	public static String getCurrentTime() {
		String CurrentDate = null;
		Calendar cal = Calendar.getInstance();
		CurrentDate = TIMESTAMP_FORMAT.format(cal.getTime());
		CurrentDate = CurrentDate.substring(11);
		// System.out.println(" CurrentTimeStamp "+CurrentDate);
		return CurrentDate;
	}

	public static String getCurrentTimeHourMin() {
		String CurrentDate = null;
		Calendar cal = Calendar.getInstance();
		CurrentDate = TIMESTAMP_FORMAT.format(cal.getTime());
		CurrentDate = CurrentDate.substring(11, 16);
		// System.out.println(" CurrentTimeStamp "+CurrentDate);
		return CurrentDate;
	}

	public String getDate() {
		String delim = "-";
		Calendar cal = Calendar.getInstance();
		Integer month = new Integer(cal.get(Calendar.MONTH));
		Integer day = new Integer(cal.get(Calendar.DATE));
		Integer year = new Integer(cal.get(Calendar.YEAR));

		StringBuffer date = new StringBuffer("");

		Integer monthI = new Integer((month.intValue() + 1));

		date.append(year.toString());
		date.append(delim);
		date.append(monthI.toString());
		date.append(delim);
		date.append(day.toString());

		return date.toString();
	}

	public static String getCurrentTimewithSeconds() {
		String timeissec = null;
		timeissec = (((new Date()).toString()).substring(11, 19));

		return timeissec;
	}

	public static String xlsDate(String dt) {
		String delim = "-";
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		StringBuffer date = new StringBuffer();
		String year = dt.substring(30, 34);
		String mnt = dt.substring(4, 7);
		String date1 = dt.substring(8, 10);
		int i = 0;
		for (i = 0; i < 13; i++) {
			if (mnt.equalsIgnoreCase(months[i]))
				break;

		}
		i += 1;
		Integer it = new Integer(i);
		String mnth = it.toString();
		if (mnth.length() < 2) {
			mnth = "0" + mnth;
		}

		date.append(year);
		date.append(delim);
		date.append(mnth);
		date.append(delim);
		date.append(date1);
		return date.toString();
	}

	public static String getNextDateAfter(int days) {
		String aDate = DateUtil.getCurrentDateIndianFormat();
		Calendar cal = Calendar.getInstance();
		String[] DateArr = aDate.split("-");
		cal.set(Integer.parseInt(DateArr[2]), Integer.parseInt(DateArr[1]),
				(Integer.parseInt(DateArr[0]) + days));
		cal.add(Calendar.MONTH, -1);
		String NewDate = DATE_FORMAT.format(cal.getTime());
		return NewDate;
	}

	public static boolean comparetwoDates(String datee1, String datee2) {
		boolean status = true;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(datee1);
			Date date2 = sdf.parse(datee2);
			if (date1.compareTo(date2) > 0) {
				status = false;
			} else if (date1.compareTo(date2) == 0) {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static String getAddedTime2Current(String time) {
		String aDate = DateUtil.getCurrentDateIndianFormat();
		String aTime = DateUtil.getCurrentTimeHourMin();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String[] DateArr = aDate.split("-");
		String[] TimeArr = aTime.split(":");
		cal.set(Integer.parseInt(DateArr[2]), Integer.parseInt(DateArr[1]),
				(Integer.parseInt(DateArr[0])), Integer.parseInt(TimeArr[0]),
				Integer.parseInt(TimeArr[1]));
		int hr = 0, min = 0;

		if (time.contains(":")) {
			String splitTime[] = time.split(":");
			hr = Integer.parseInt(splitTime[0]);
			min = Integer.parseInt(splitTime[1]);
		}

		cal.add(Calendar.HOUR_OF_DAY, hr);
		cal.add(Calendar.MINUTE, min);
		String NewDate = SDF.format(cal.getTime());
		return NewDate;

	}

	public static String getTimeB4Current() {
		String b4Time = "";

		String aDate = DateUtil.getCurrentDateIndianFormat();
		String aTime = DateUtil.getCurrentTimeHourMin();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		String[] DateArr = aDate.split("-");
		String[] TimeArr = aTime.split(":");

		cal.set(Integer.parseInt(DateArr[2]), Integer.parseInt(DateArr[1]),
				(Integer.parseInt(DateArr[0])), Integer.parseInt(TimeArr[0]),
				Integer.parseInt(TimeArr[1]));
		cal.add(Calendar.HOUR_OF_DAY, -1);
		String b4Date = SDF.format(cal.getTime());
		b4Time = b4Date.substring(11, 16);

		return b4Time;

	}

	public static String makeTitle(String title) {
		String oldTitle = "";
		String newTitle = "";
		String titleArr[] = title.split(" ");
		for (int p = 0; p < titleArr.length; p++) {
			oldTitle = titleArr[p];
			if (oldTitle != null && !oldTitle.equals("")) {
				newTitle = newTitle + " "
						+ (oldTitle.substring(0, 1)).toUpperCase()
						+ (oldTitle.substring(1)).toLowerCase();
				newTitle = newTitle.trim();
			}
		}
		return newTitle;
	}

	public static String removeSpace(String str) {
		String oldStr = "";
		String newStr = "";
		String titleArr[] = str.split(" ");

		for (int p = 0; p < titleArr.length; p++) {
			oldStr = titleArr[p];
			newStr = newStr + oldStr.toLowerCase();
			newStr = newStr.trim();
		}
		return newStr;
	}

	public List<String> ThreeMonthBeforeMonthYears() {
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		Calendar cal = Calendar.getInstance();
		int j = 3;
		List<String> MonthList = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			String month = null;
			String Year = null;
			int I = cal.get(Calendar.MONTH) - i - 1;
			if (I == -1) {
				month = months[11];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -2) {
				month = months[10];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -3) {
				month = months[9];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else {
				month = months[cal.get(Calendar.MONTH) - i - 1];
				Year = new Integer(cal.get(Calendar.YEAR)).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());

			}
			StringBuffer Month_year = new StringBuffer();
			Month_year.append(month);
			Month_year.append("-");
			Month_year.append(Year);
			MonthList.add(Month_year.toString());
		}
		return MonthList;
	}

	public int MonthDays(int Month, int Year) {
		int days = 0;
		if (Month == 2) {
			if ((Year % 4 == 0) && (Year % 400 == 0) && !(Year % 100 == 0))
				days = 29;
			else
				days = 28;
		} else if (Month == 1 || Month == 3 || Month == 5 || Month == 7
				|| Month == 8 || Month == 10 || Month == 12)
			days = 31;
		else
			days = 30;
		return days;
	}

	public List<String> MonthBeforeMonthYears(int Mont) {
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		Calendar cal = Calendar.getInstance();
		int j = Mont;
		List<String> MonthList = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			String month = null;
			String Year = null;
			int I = cal.get(Calendar.MONTH) - i - 1;
			if (I == -1) {
				month = months[11];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -2) {
				month = months[10];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -3) {
				month = months[9];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -4) {
				month = months[8];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -5) {
				month = months[7];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -6) {
				month = months[6];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else {
				month = months[cal.get(Calendar.MONTH) - i - 1];
				Year = new Integer(cal.get(Calendar.YEAR)).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());

			}
			StringBuffer Month_year = new StringBuffer();
			Month_year.append(month);
			Month_year.append("-");
			Month_year.append(Year);
			MonthList.add(Month_year.toString());
		}
		return MonthList;
	}

	public List<String> MonthBefore2MonthYearsanddate(int Mont) {
		Calendar cal = Calendar.getInstance();
		int j = Mont;
		List<String> MonthList = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			String month = null;
			String Year = null;
			int I = cal.get(Calendar.MONTH) - i - 1;
			if (I == -1) {
				month = Integer.toString(11);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else if (I == -2) {
				month = Integer.toString(10);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else if (I == -3) {
				month = Integer.toString(9);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else if (I == -4) {
				month = Integer.toString(8);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else if (I == -5) {
				month = Integer.toString(7);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else if (I == -6) {
				month = Integer.toString(6);
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString();
			} else {
				month = Integer.toString(cal.get(Calendar.MONTH) - i - 1);
				Year = new Integer(cal.get(Calendar.YEAR)).toString();

			}
			StringBuffer Month_year = new StringBuffer();
			Month_year.append(Year);
			Month_year.append("-");
			if (month.length() == 1) {
				Month_year.append("0" + month);
			} else {
				Month_year.append(month);
			}
			Month_year.append("-");
			Month_year.append("02");

			MonthList.add(Month_year.toString());
		}
		return MonthList;
	}

	public List<String> MonthBefore2MonthYears(int Mont) {
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		Calendar cal = Calendar.getInstance();
		int j = Mont;
		List<String> MonthList = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			String month = null;
			String Year = null;
			int I = cal.get(Calendar.MONTH) - i - 2;
			if (I == -1) {
				month = months[11];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -2) {
				month = months[10];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -3) {
				month = months[9];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -4) {
				month = months[8];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -5) {
				month = months[7];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else if (I == -6) {
				month = months[6];
				Year = new Integer(cal.get(Calendar.YEAR) - 1).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());
			} else {
				month = months[cal.get(Calendar.MONTH) - i - 2];
				Year = new Integer(cal.get(Calendar.YEAR)).toString()
						.substring(
								2,
								new Integer(cal.get(Calendar.YEAR)).toString()
										.length());

			}
			StringBuffer Month_year = new StringBuffer();
			Month_year.append(month);
			Month_year.append("-");
			Month_year.append(Year);
			MonthList.add(Month_year.toString());
		}
		return MonthList;
	}

	public static String currentdatetime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	public static String convertDateToUSFormatSlace(String aDate) {

		String usDate = null;

		try {

			String usd[] = aDate.split("/");
			usDate = usd[2] + "-" + usd[0] + "-" + usd[1];
		}

		catch (Exception pse) {
		}

		return usDate;
	}

	@SuppressWarnings("static-access")
	public static String newdate_AfterEscTime(String date, String time,
			String time1, String need_esc) {
		String NewDate = null;
		String timearr[] = null;
		String time1arr[] = null;
		timearr = time.split(":");
		time1arr = time1.split(":");
		try {
			Date convertedDate = null;
			if (need_esc.equalsIgnoreCase("Y")) {
				convertedDate = DATE_FORMAT.parse(date);
			} else if (need_esc.equalsIgnoreCase("N")) {
				convertedDate = DATE_FORMAT.parse(getNewDate("year", 50,
						getCurrentDateUSFormat()));
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(convertedDate);
			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));

			NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
					+ cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return NewDate;
	}

	@SuppressWarnings("static-access")
	public static String newdate_AfterEscInRoaster(String date, String time,
			String time1, String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String time1arr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		time1arr = time1.split(":");
		adress_time_arr = address_time.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(convertedDate);

			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));

			cal.add(cal.HOUR, Integer.parseInt(adress_time_arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(adress_time_arr[1]));

			NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
					+ cal.getTime().toString().substring(11, 16);

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return NewDate;
	}

	@SuppressWarnings("static-access")
	public static String newTime(String time) {
		String NewTime = null;
		Calendar cal = Calendar.getInstance();
		String[] arr = null;
		arr = time.split(":");
		if (arr[0].equals("00")) {
			cal.add(cal.MINUTE, Integer.parseInt(arr[1]));
		} else if (arr[1].equals("00")) {
			cal.add(cal.HOUR, Integer.parseInt(arr[0]));
		}

		else {
			cal.add(cal.HOUR, Integer.parseInt(arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(arr[1]));

		}
		NewTime = cal.getTime().toString().substring(11, 19);
		return NewTime;
	}

	@SuppressWarnings("static-access")
	public static String addTwoTimes(String date, String time1, String Time2) {
		String newTime = null;
		String[] arr1 = null;
		String[] arr2 = null;
		arr1 = time1.split(":");
		arr2 = time1.split(":");
		try {
			Calendar cal = Calendar.getInstance();
			Date convertedDate = DATE_FORMAT.parse(date);
			cal.setTime(convertedDate);
			if (!arr1[0].equals("00") && !arr1[1].equals("00")) {
				cal.add(cal.HOUR, Integer.parseInt(arr1[0]));
				cal.add(cal.MINUTE, Integer.parseInt(arr1[1]));
			} else if (arr1[0].equals("00") && !arr1[1].equals("00")) {
				cal.add(cal.MINUTE, Integer.parseInt(arr1[1]));
			} else if (arr1[1].equals("00") && !arr1[0].equals("00")) {
				cal.add(cal.HOUR, Integer.parseInt(arr1[0]));
			}

			if (!arr2[0].equals("00") && !arr2[1].equals("00")) {
				cal.add(cal.HOUR, Integer.parseInt(arr2[0]));
				cal.add(cal.MINUTE, Integer.parseInt(arr2[1]));
			} else if (arr2[0].equals("00") && !arr2[1].equals("00")) {
				cal.add(cal.MINUTE, Integer.parseInt(arr2[1]));
			} else if (arr2[1].equals("00") && !arr2[0].equals("00")) {
				cal.add(cal.HOUR, Integer.parseInt(arr2[0]));
			}

			newTime = cal.getTime().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newTime;
	}

	@SuppressWarnings("static-access")
	public static String time_difference(String date1, String time1,
			String date2, String time2) {
		/*System.out.println("Date One "+date1);
		System.out.println("Time One "+time1);
		System.out.println("Date Two "+date2);
		System.out.println("Time Two "+time2);*/
		
		String timeString = null;
		StringBuilder sb = new StringBuilder(64);
		String timearr1[] = null;
		timearr1 = time1.split(":");

		String timearr2[] = null;
		timearr2 = time2.split(":");

		try {
			Date convertedDate = DATE_FORMAT.parse(date1);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(convertedDate);
			cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));

			Date convertedDate2 = DATE_FORMAT.parse(date2);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(convertedDate2);
			cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));

			long millisesond1 = cal1.getTimeInMillis();
			long millisesond2 = cal2.getTimeInMillis();
			long diff = millisesond2 - millisesond1;

			if (diff < 0) {
				timeString = "00:00";
			} else {
				long hours = TimeUnit.MILLISECONDS.toHours(diff);
				diff -= TimeUnit.HOURS.toMillis(hours);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

				sb.append(hours);
				sb.append(":");
				timeString = sb.append(minutes).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeString;
	}

	final static long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	public static int getNoOfDays(String datee1, String datee2) {
		int days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(datee1);
			Date date2 = sdf.parse(datee2);

			days = (int) ((date1.getTime() - date2.getTime()) / DAY_IN_MILLIS);

			//System.out.println("Total No of Days  " + days);
			/*
			 * System.out.println(sdf.format(date1));
			 * System.out.println(sdf.format(date2));
			 */
		} catch (Exception e) {

		}
		return days;
	}

	@SuppressWarnings("static-access")
	public static String newdate_AfterTime(String date, String time,
			String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		adress_time_arr = address_time.split(":");
		try {
			if (date != null && time != null && address_time != null) {
				Date convertedDate = DATE_FORMAT.parse(date);

				Calendar cal = Calendar.getInstance();
				cal.setTime(convertedDate);

				cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
				cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

				cal.add(cal.HOUR, Integer.parseInt(adress_time_arr[0]));
				cal.add(cal.MINUTE, Integer.parseInt(adress_time_arr[1]));

				NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
						+ cal.getTime().toString().substring(11, 19);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NewDate;
	}

	public static String getTime(String curentTime) {
		// 10:28:41
		String time = null;
		curentTime = DateUtil.getCurrentTime();
		String split[] = curentTime.split(":");
		int hour = Integer.parseInt(split[0]);
		if (hour > 12) {
			time = split[0] + ":" + split[1] + "PM";
		} else {
			time = split[0] + ":" + split[1] + "AM";
		}
		return time;
	}

	@SuppressWarnings("static-access")
	public static boolean time_update(String date, String time) {
		boolean update = false;
		String timearr[] = null;
		timearr = time.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(convertedDate);

			cal1.add(cal1.HOUR, Integer.parseInt(timearr[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr[1]));

			Calendar cal = Calendar.getInstance();

			if (cal1.getTime().before(cal.getTime())) {
				update = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return update;
	}

	public static String getNewDate(String checkField, int count,
			String fromDate) {
		String NewDate = null;
		// @SuppressWarnings("unused")
		// String strDate[] = fromDate.split("-");
		try {
			Date convertedDate = DATE_FORMAT.parse(fromDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(convertedDate);
			// System.out.println("Date    "+cal.getTime());
			if (checkField.equalsIgnoreCase("month")) {
				cal.add(Calendar.MONTH, count);
			} else if (checkField.equalsIgnoreCase("year")) {
				cal.add(Calendar.YEAR, count);
			} else if (checkField.equalsIgnoreCase("day")) {
				cal.add(Calendar.DATE, count);
			} else if (checkField.equalsIgnoreCase("week")) {
				cal.add(Calendar.WEEK_OF_MONTH, count);
			}
			NewDate = DATE_FORMAT.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NewDate;
	}

	public static String mergeDateTime() {
		String date = getCurrentDateUSFormat();
		String time = getCurrentTimewithSeconds();
		String strDate[] = date.split("-");
		String strTime[] = time.split(":");
		String combineDateTime = new String();
		for (int i = 0; i < strDate.length; i++) {
			combineDateTime += strDate[i];
		}
		for (int i = 0; i < strTime.length; i++) {
			combineDateTime += strTime[i];
		}
		return combineDateTime;
	}

	@SuppressWarnings("static-access")
	public static String getResolutionTime(String time1, String time2) {
		String NewDate = null;
		String time1arr[] = null;
		String time2arr[] = null;
		time1arr = time1.split(":");
		time2arr = time2.split(":");
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));
			cal.add(cal.HOUR, Integer.parseInt(time2arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time2arr[1]));
			NewDate = cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NewDate;
	}

	public static String getResolutionTime(String addressTime, String escTime,
			String escduration) {
		String resolution_time = null;
		try {
			String[] arr1 = null;
			String[] arr2 = null;
			if (!addressTime.equals("") && !escTime.equals("")) {
				arr1 = addressTime.split(":");
				arr2 = escTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = (Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1])))
						* Integer.parseInt(escduration);
				b = (Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0])))
						* Integer.parseInt(escduration);
				if (a != 0 && a < 60) {
					c = a;
					resolution_time = "" + b + ":" + a;
				} else {
					d = a / 60;
					c = a % 60;
					d = b + d;
					resolution_time = "" + d + ":" + c;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resolution_time;
	}

	@SuppressWarnings("static-access")
	public static String newdate_BeforeTime(String date, String time,
			String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		adress_time_arr = address_time.split(":");
		try {
			if (date != null && time != null && address_time != null) {
				Date convertedDate = DATE_FORMAT.parse(date);

				Calendar cal = Calendar.getInstance();
				cal.setTime(convertedDate);

				cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
				cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

				cal.add(cal.HOUR, -(Integer.parseInt(adress_time_arr[0])));
				cal.add(cal.MINUTE, -(Integer.parseInt(adress_time_arr[1])));

				NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
						+ cal.getTime().toString().substring(11, 19);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NewDate;
	}

	public static String getDate(String date, String type) {
		String strDate[] = date.split("-");
		String dd = "";
		String mm = "";
		String yy = "";

		if (type.trim().equalsIgnoreCase("DD")) {
			return strDate[0];
		} else if (type.trim().equalsIgnoreCase("D")) {
			if (String.valueOf(strDate[0].charAt(0)).equalsIgnoreCase("0")) {
				return String.valueOf(strDate[0].charAt(1));
			}
		} else if (type.trim().equalsIgnoreCase("MM")) {
			return strDate[1];
		} else if (type.trim().equalsIgnoreCase("M")) {
			if (String.valueOf(strDate[1].charAt(0)).equalsIgnoreCase("0")) {
				return String.valueOf(strDate[1].charAt(1));
			}
		} else if (type.trim().equalsIgnoreCase("YYYY")) {
			return strDate[2];
		} else if (type.trim().equalsIgnoreCase("YY")) {
			return strDate[2].substring(2, strDate[2].length());
		}
		return "You have enter wrong date type !!!";
	}

	public static String getDateTime(String date_time) {
		String newDateTime = "";
		try {
			String[] date_timearr = new String[2];
			String[] timearr = new String[2];
			date_timearr[0] = date_time.substring(0, 8);
			date_timearr[1] = date_time.substring(8, 12);

			if (date_timearr[1] != null && date_timearr[1].length() > 0) {
				timearr[0] = date_timearr[1].substring(0, 2);
				timearr[1] = date_timearr[1].substring(2, 4);
			}
			Date convertedDate = DATE_FORMAT_Without_Hiphan
					.parse(date_timearr[0]);
			Calendar cal = Calendar.getInstance();
			cal.setTime(convertedDate);

			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));
			newDateTime = convertDateToUSFormat(DATE_FORMAT.format(cal
					.getTime()))
					+ "#" + cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDateTime;
	}

	public static String convertHyphenDateToUSFormat(String aDate) {

		String usDate = null;

		try {

			String usd[] = aDate.split("-");
			if (usd[1].length() < 2) {
				usd[1] = "0".concat(usd[1]);
			}
			if (usd[2].length() < 2) {
				usd[2] = "0".concat(usd[2]);
			}
			usDate = usd[0] + "-" + usd[2] + "-" + usd[1];
		}

		catch (Exception pse) {
		}

		return usDate;
	}

	public static boolean compareDateTime(String dateWithTime,
			String dateWithTime1) {
		boolean status = false;
		try {
			String[] date_timearr1 = dateWithTime.split(" ");
			String[] timearr1 = date_timearr1[1].split(":");

			String[] date_timearr2 = dateWithTime1.split(" ");
			String[] timearr2 = date_timearr2[1].split(":");

			Date convertedDate1 = DATE_FORMAT.parse(date_timearr1[0]);
			Date convertedDate2 = DATE_FORMAT.parse(date_timearr2[0]);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(convertedDate1);
			cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
			// System.out.println("Date Time  "+cal1.getTime());

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(convertedDate2);
			cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));
			// System.out.println("Date Time  "+cal2.getTime());

			if (cal1.getTime().before(cal2.getTime())) {
				status = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("static-access")
	public static String newdate_AfterEscTimeOld(String date, String time,
			String time1, String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String time1arr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		time1arr = time1.split(":");
		adress_time_arr = address_time.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date);

			Calendar cal = Calendar.getInstance();
			// System.out.println("Date Time before   ::  "+cal.getTime());
			cal.setTime(convertedDate);
			// System.out.println("Date Time after   ::  "+cal.getTime());
			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));

			cal.add(cal.HOUR, Integer.parseInt(adress_time_arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(adress_time_arr[1]));

			NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
					+ cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return NewDate;
	}

	@SuppressWarnings("static-access")
	public static String newdate_AfterEscTime_Old(String date, String time,
			String time1, String address_time) {
		String NewDate = null;
		String timearr[] = null;
		String time1arr[] = null;
		String adress_time_arr[] = null;
		timearr = time.split(":");
		time1arr = time1.split(":");
		adress_time_arr = address_time.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date);

			Calendar cal = Calendar.getInstance();
			// System.out.println("Date Time before   ::  "+cal.getTime());
			cal.setTime(convertedDate);
			// System.out.println("Date Time after   ::  "+cal.getTime());
			cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));

			cal.add(cal.HOUR, Integer.parseInt(time1arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(time1arr[1]));

			cal.add(cal.HOUR, Integer.parseInt(adress_time_arr[0]));
			cal.add(cal.MINUTE, Integer.parseInt(adress_time_arr[1]));

			NewDate = DATE_FORMAT.format(cal.getTime()) + "#"
					+ cal.getTime().toString().substring(11, 16);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return NewDate;
	}
	public static String findDayFromDate(String date) {
    	
		  SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
		  Date dt1 = null;
		try {
			dt1 = format1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  DateFormat format2=new SimpleDateFormat("EEEE"); 
		  String finalDay=format2.format(dt1);
		return finalDay;
	}
	 public static String findDifferenceTwoTime(String time1,String time2)  
		{
			StringBuilder sb = new StringBuilder(64);
			String timearr1[]=null;
			timearr1=time1.split(":");
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			String timearr2[]=null;
			timearr2=time2.split(":");
			
			   try {
			         
			         Calendar cal1 = Calendar.getInstance();
			        
			         cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
			         cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
			         
			         Calendar cal2 = Calendar.getInstance();
			        
			         cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			         cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));
			         
			         long millisesond1 = cal1.getTimeInMillis(); 
			         long millisesond2 = cal2.getTimeInMillis(); 
			         long diff = millisesond2-millisesond1;
			        if(diff < 0)
			         {
			             throw new IllegalArgumentException("Duration must be greater than zero!");
			         }
			         long hours = TimeUnit.MILLISECONDS.toHours(diff);
			         diff -= TimeUnit.HOURS.toMillis(hours);
			         long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
			         diff -= TimeUnit.MINUTES.toMillis(minutes);
			       
			         sb.append(hours);
			         sb.append(":");
			         sb.append(minutes);
			     
			       } 
			   catch (Exception e) {
				   e.printStackTrace();
				}
		    return(sb.toString());
		}
	
	 public static int countWeekendDays(String date) {
	    	
	    	String datearr[]=null;
	    	datearr=date.split("-");
	    	
	        Calendar calendar = Calendar.getInstance();
	        // Note that month is 0-based in calendar, bizarrely.
	        calendar.set(Integer.parseInt(datearr[0]), Integer.parseInt(datearr[1]) - 1, 1);
	        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int count = 0;
	        for (int day = 1; day <= daysInMonth; day++) {
	            calendar.set(Integer.parseInt(datearr[0]), Integer.parseInt(datearr[1]) - 1, day);
	            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	          
	            if (dayOfWeek == Calendar.SUNDAY) {
	                count++;
	                // Or do whatever you need to with the result.
	            }
	        }
	        @SuppressWarnings("unused")
			int i=daysInMonth-count;
	        return daysInMonth-count;
	    }
	
	 public  static String getLastDateFromLastDate(int count, String fromDate)
		{
		    String NewDate = null;
		    String[] datearr = fromDate.split("-");
		    try {
		    	 Date convertedDate = DATE_FORMAT.parse(fromDate); 
		    	 Calendar cal = Calendar.getInstance();
		    	 cal.setTime(convertedDate);
		    	   cal.add(Calendar.MONTH, count);
		        if (datearr[2].equals("28")) {
		        	 cal.add(Calendar.DATE, 3);
				}
		        else if(datearr[2].equals("29")){
					 cal.add(Calendar.DATE, 2);
				}
				else if(datearr[2].equals("30")){
					cal.add(Calendar.DATE, 1);
				}
				
		         NewDate = DATE_FORMAT.format(cal.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		   return NewDate;
		}
	 
	 
	 public  static String getMonthFromLastDate( String fromDate){
			
		 Date convertedDate = null;
			try {
				convertedDate = DATE_FORMAT.parse(fromDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		 Calendar date = Calendar.getInstance();
		date.setTime(convertedDate);
	     @SuppressWarnings("unused")
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
	     String[] nameOfMonth = { "January", "February", "March", "April",
	                     "May", "June", "July", "August", "September", "October",
	                     "November", "December" };
	   
	     int numberOfMonth = date.get(Calendar.MONTH);
	     int year=date.get(Calendar.YEAR);
	     String finalMonth= nameOfMonth[numberOfMonth];
	     String finalresult=finalMonth+" "+year;
	    
		return finalresult;
		
	}
	 public static String convertTimeInMinuteAndSecond(String time)
	    {
	    	
	    	String usDate=null;
	        
	        try{
	          
	           if (time.equalsIgnoreCase("1899-12-31")) {
	        	   usDate="00:00";
			}else{
	        	String usd[]=time.split(":");
	        	
	        	usDate=usd[0]+":"+usd[1];
	            }
	        } 
	        catch(Exception pse){}
	       
	        return usDate;
	    }
	 public static int countDaysInMonth(int month,int year){
	    	
	    	int daysInMonth;
	    	boolean isLeapYear = false;
	    	
	        if(year % 400 == 0)
	        {
	            isLeapYear = true;
	        }
	        else if (year % 100 == 0)
	        {
	            isLeapYear = false;
	        }
	        else if(year % 4 == 0)
	        {
	            isLeapYear = true;
	        }
	        else
	        {
	            isLeapYear = false;
	        }
		    if (month == 4 || month == 6 || month == 9 || month == 11)
		    {
			daysInMonth = 30;
		    }
			else if (month == 02) 
			{
			daysInMonth = (isLeapYear) ? 29 : 28;
			}
			else 
			{
			daysInMonth = 31;
			}
	    	return daysInMonth;
	    }
	public static String getDateAfterDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	/**
	 * @return returns current date containing year and month only in numeric
	 *         form i.e yyyy-mm
	 */
	public static String getCurrentDateYearMonth() {
		return DateUtil.getCurrentDateUSFormat().substring(0,
				DateUtil.getCurrentDateUSFormat().lastIndexOf("-"));
	}

	/**
	 * @return returns current date containing month and year only in numeric
	 *         form i.e mm-yyyy
	 */
	public static String getCurrentDateMonthYear() {
		return convertDateYearMonthToMonthYear(getCurrentDateYearMonth());
	}

	/**
	 * @param dateYearMonth
	 * @return returns date from yyyy-mm format to mm-yyyy format
	 */
	public static String convertDateYearMonthToMonthYear(String dateYearMonth) {
		return dateYearMonth.substring(dateYearMonth.lastIndexOf("-") + 1,
				dateYearMonth.length())
				+ "-"
				+ dateYearMonth.substring(0, dateYearMonth.lastIndexOf("-"));
	}

	/**
	 * @param dateMonthYear
	 * @return returns date from mm-yyyy format to yyyy-mm format
	 */
	public static String convertDateMonthYearToYearMonth(String dateMonthYear) {
		dateMonthYear = dateMonthYear.trim();
		return dateMonthYear.substring(dateMonthYear.lastIndexOf("-") + 1,
				dateMonthYear.length())
				+ "-"
				+ dateMonthYear.substring(0, dateMonthYear.lastIndexOf("-"));
	}

	
	@SuppressWarnings("static-access")
	public static boolean comparebetweenTwodateTime(String date1, String time1,String date2, String time2) {
		boolean update = false;
		String timearr1[] = null;
		timearr1 = time1.split(":");
		String timearr2[] = null;
		timearr2 = time2.split(":");
		try {
			Date convertedDate = DATE_FORMAT.parse(date1);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(convertedDate);

			cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
			
			Date convertedDate1 = DATE_FORMAT.parse(date2);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(convertedDate1);

			cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));

			if (cal1.getTime().before(cal2.getTime())) {
				update = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return update;
	}
	
	public static String getTimeDifference(String time1,String time2) {
		String calculated_time = null;
		try {
			
			String[] arr1 = null;
			String[] arr2 = null;
			if (time1!=null && !time1.equals("") && time2!=null && !time2.equals("")) {
				arr1 = time1.split(":");
				arr2 = time2.split(":");
				int a = 0, b = 0;
				a = Integer.parseInt((arr1[1])) - Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) - Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60) {
					String a_length = String.valueOf(a);
					if (a_length.length()>2)
					{
						calculated_time = "" + (b-1) + ":" + (60+(a));
					}
					else {
						calculated_time = "" + b + ":" + a;
					}
				} 
				else if (a==0) {
					calculated_time = "" + b + ":" + a;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calculated_time;
	}
	
	
	public static String getCurrentDayofWeek() {
		int cday;
		String currentday="";
		try {
		Calendar cal = Calendar.getInstance();
		cday = cal.get(cal.DAY_OF_WEEK) - 1;
		if (cday==1) {
			currentday="Monday";
		}
		else if (cday==2) {
			currentday="Tuesday";
		}
		else if (cday==3) {
			currentday="Wednesday";
		}
		else if (cday==4) {
			currentday="Thursday";
		}
		else if (cday==5) {
			currentday="Friday";
		}
		else if (cday==6) {
			currentday="Saturday";
		}
		else if (cday==7) {
			currentday="Sunday";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentday;
	}
	
	public static String getNextDayofWeek() {
		int cday;
		String currentday="";
		try {
		Calendar cal = Calendar.getInstance();
		cday = cal.get(cal.DAY_OF_WEEK);
		if (cday==1) {
			currentday="Monday";
		}
		else if (cday==2) {
			currentday="Tuesday";
		}
		else if (cday==3) {
			currentday="Wednesday";
		}
		else if (cday==4) {
			currentday="Thursday";
		}
		else if (cday==5) {
			currentday="Friday";
		}
		else if (cday==6) {
			currentday="Saturday";
		}
		else if (cday==7) {
			currentday="Sunday";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentday;
	}
	
	
	public static String getDayofDate(String date) {
		int cday;
		String currentday="";
		try {
			Date convertedDate = DATE_FORMAT.parse(date); 
            Calendar cal = Calendar.getInstance();
            cal.setTime(convertedDate);
          if (cal.get(cal.DAY_OF_WEEK)==1) {
        	  cday = 7;
		  }
          else {
        	  cday = cal.get(cal.DAY_OF_WEEK) - 1;
		  }
		if (cday==1) {
			currentday="Monday";
		}
		else if (cday==2) {
			currentday="Tuesday";
		}
		else if (cday==3) {
			currentday="Wednesday";
		}
		else if (cday==4) {
			currentday="Thursday";
		}
		else if (cday==5) {
			currentday="Friday";
		}
		else if (cday==6) {
			currentday="Saturday";
		}
		else if (cday==7) {
			currentday="Sunday";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentday;
	}
	

	public static String getdateofcurrentweekdays(String day){
		String date=null;
		try{
			Calendar cal=Calendar.getInstance();
			if(day.equalsIgnoreCase("Sunday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}else if(day.equalsIgnoreCase("Monday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}else if(day.equalsIgnoreCase("Tuesday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}else if(day.equalsIgnoreCase("Wednesday")){
			    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}else if(day.equalsIgnoreCase("Thursday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}else if(day.equalsIgnoreCase("Friday")){
            	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}else{
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);	
			}
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			date=df.format(cal.getTime());
			//System.out.println(df.format(cal.getTime()));      // This past Sunday [ May include today ]
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return date;
	}
	public static String getdateofweekdays(String day,String time){
		String date=null;
		try{
			Calendar cal=Calendar.getInstance();
			if(day.equalsIgnoreCase("Sunday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}else if(day.equalsIgnoreCase("Monday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}else if(day.equalsIgnoreCase("Tuesday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}else if(day.equalsIgnoreCase("Wednesday")){
			    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}else if(day.equalsIgnoreCase("Thursday")){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}else if(day.equalsIgnoreCase("Friday")){
            	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}else{
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);	
			}
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			
			//System.out.println(df.format(cal.getTime()));      // This past Sunday [ May include today ]
			
			cal.add(Calendar.DATE,7);
			SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm");
			Date dateaa = sdfs.parse(time);
			date=df.format(cal.getTime());
			//System.out.println(df.format(dateaa)); 
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return date;
		
	}
	
	
	@SuppressWarnings("static-access")
	public static String timeInBetween(String date,String time1, String time2,String time3) {
		//System.out.println("Current Time "+time3);
		String returnValue = "middle";//changed by Karniaka Due to Escalation(First escalation time entry was wrong) Issue in Medanta
		String timearr1[] = null;
		timearr1 = time1.split(":");
		String timearr2[] = null;
		timearr2 = time2.split(":");
		String timearr3[] = null;
		timearr3 = time3.split(":");
		try {
			 Date convertedDate = DATE_FORMAT.parse(date); 
             Calendar cal1 = Calendar.getInstance();
             cal1.setTime(convertedDate);
			//System.out.println(" Before Cal one time "+cal1.getTime());
			cal1.add(cal1.HOUR,Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
			//System.out.println("Cal one time "+cal1.getTime());
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(convertedDate);
			//System.out.println("Cal two time 1111"+cal2.getTime());
			cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));
			//System.out.println("Cal two time "+cal2.getTime());
			
			Date convertedDate1 = DATE_FORMAT.parse(DateUtil.getCurrentDateUSFormat()); 
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(convertedDate1);
			//System.out.println("Cal three time 1111"+cal3.getTime());
			cal3.add(cal3.HOUR, Integer.parseInt(timearr3[0]));
			cal3.add(cal3.MINUTE, Integer.parseInt(timearr3[1]));
			//System.out.println("Cal three time "+cal3.getTime());

			if (cal1.getTime().before(cal3.getTime())) {
				if (cal3.getTime().before(cal2.getTime())) {
					returnValue="middle";
				}
				else if (cal2.getTime().before(cal3.getTime())) {
					returnValue="after";
				}
			}
			else if (cal3.getTime().before(cal1.getTime())) {
				returnValue="before";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	public static String addTwoTime(String time1,String time2)
	 {
		String calculated_time = "00:00";
		try {
			
			String[] arr1 = null;
			String[] arr2 = null;
			if (time1!=null && !time1.equals("") && time2!=null && !time2.equals("")) {
				arr1 = time1.split(":");
				arr2 = time2.split(":");
				int a = 0, b = 0, c=0,d=0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				//System.out.println("Value of  A"+a);
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				//System.out.println("Value of  B"+b);
				if (a != 0 && a >= 60)
				 {
					c=a/60;
					d=a%60;
					calculated_time = "" + (b+c) + ":" + (d);
				} 
				else if (a != 0 && a < 60) {
						calculated_time = "" + b + ":" + a;
				} 
				else {
					calculated_time = "" + b + ":" + a;
			} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calculated_time;
	 }
	
	public static String getNextDateFromDate(String date, int days) {
		String NewDate ="";
		try
		 {
		   Date convertedDate = DATE_FORMAT.parse(date); 
           Calendar cal = Calendar.getInstance();
           cal.setTime(convertedDate);
		
		   String[] DateArr = date.split("-");
		   cal.set(Integer.parseInt(DateArr[2]), Integer.parseInt(DateArr[1]),
				(Integer.parseInt(DateArr[0]) + days));
		   cal.add(Calendar.MONTH, -1);
		   NewDate = DATE_FORMAT.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NewDate;
	}
	
	@SuppressWarnings("static-access")
	public static String newdate_AfterTime(String date, String time)
    {
		String NewDate="";
	   	String NewDateTime[] = null;
	   	String timearr[]=null;
	   	
	   	timearr=time.split(":");
	   	NewDateTime=date.split("#");
	   	
		String NewTime[] = null;
		NewTime=NewDateTime[1].split(":");
	   	   try {
	   	         Date convertedDate = DATE_FORMAT.parse(NewDateTime[0].toString()); 
	   	         
	   	         Calendar cal = Calendar.getInstance();
	   	         cal.setTime(convertedDate);
	   	         
	   	         cal.add(cal.HOUR, Integer.parseInt(NewTime[0]));
	   	         cal.add(cal.MINUTE, Integer.parseInt(NewTime[1]));
	   	         
	   	         cal.add(cal.HOUR, Integer.parseInt(timearr[0]));
	   	         cal.add(cal.MINUTE, Integer.parseInt(timearr[1]));
	   	         
	   	         NewDate = DATE_FORMAT.format(cal.getTime())+"#"+cal.getTime().toString().substring(11, 16);
	   	       } 
	   	   catch (Exception e) {
	  			//e.printStackTrace();
	  		}
	       return NewDate;
   }
	public static boolean checkDateBetween(String firstDate, String middleDate, String endDate) {
		boolean update = false;
		try {
			Date converted1stDate = DATE_FORMAT.parse(firstDate);
			Calendar firstDateInstance = Calendar.getInstance();
			firstDateInstance.setTime(converted1stDate);
			
			
			Date converted2ndDate = DATE_FORMAT.parse(middleDate);
			Calendar secondDateInstance = Calendar.getInstance();
			secondDateInstance.setTime(converted2ndDate);
			
			
			Date converted3rdDate = DATE_FORMAT.parse(endDate);
			Calendar thirdDateInstance = Calendar.getInstance();
			thirdDateInstance.setTime(converted3rdDate);
			
			
			if ((firstDateInstance.getTime().before(secondDateInstance.getTime()))
					&& (secondDateInstance.getTime().before(thirdDateInstance.getTime()))) {
				update = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return update;
	}
	public static String getNextDateAftermonth() {
		String aDate = DateUtil.getCurrentDateUSFormat();
		//System.out.println(">>>>>>>>>>>"+aDate);
		Calendar cal = Calendar.getInstance();
		String[] DateArr = aDate.split("-");
		String NewDate=null;
		try{
		         cal.set(Integer.parseInt(DateArr[0]), 
				 Integer.parseInt(DateArr[1]),
				 (Integer.parseInt(DateArr[2])));
		         
		cal.add(Calendar.MONTH,0);
		
		NewDate = DATE_FORMAT.format(cal.getTime());
		
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return NewDate;
	}

	 public static String findDaysForDates(String dateStart,String dateStop)  
		{
		 StringBuilder sb=new StringBuilder();
		// System.out.println("dateStart    "+dateStart);
		 //System.out.println("dateStop     "+dateStop);
		 
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			long diff = d1.getTime() - d2.getTime();

			
			long diffDays = diff / (24 * 60 * 60 * 1000);

			//System.out.print(diffDays + " days, ");
			sb.append(diffDays);
			//System.out.println("fgfg" +sb.toString());
			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return (sb.toString());

		 }
	 
		@SuppressWarnings("unused")
public static boolean getTimeEntryForAttendance(String time1, String time2) 
{
		boolean calculated_time = false;
		try
		{
			String[] arr1 = null;
			String[] arr2 = null;
			if (time1!=null && !time1.equals("") && time2!=null && !time2.equals("")) 
			 {
				  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					if(dateFormat.parse(time1).after(dateFormat.parse(time2)))
					{
						calculated_time=true;
		      }
			 }
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
  	return calculated_time;
	}
		
		public static String getcorrecctTime(String time)
		{
			String correct_time=time;
			try {
				 int indexOfColumn = correct_time.indexOf(":");
				// System.out.println("Index Of "+indexOfColumn);
				 if (indexOfColumn==1) {
					int lengthOfString  = correct_time.length();
					//System.out.println("Inside Length of String Inside If "+lengthOfString);
					if (lengthOfString>4) {
						correct_time=correct_time.substring(0, 4);
					}
				}
				 else if (indexOfColumn==2) {
					 int lengthOfString  = correct_time.length();
						//System.out.println("Inside Length of String Inside E;se If "+lengthOfString);
						if (lengthOfString>5) {
							correct_time=correct_time.substring(0, 5);
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return correct_time;
		}
		public static boolean checkTwoTime(String time1,String time2)
		 {
			boolean flag=false;
			
			//System.out.println("Time 1  "+time1);
		//	System.out.println("Time 2  "+time2);
			String arr1[]=null;
	        String arr2[]=null;
	        
	        arr1 = time1.split(":");
	        arr2 = time2.split(":");
	        
			String hr_time1=arr1[0];
			String hr_time2=arr2[0];
			String min_time1=arr1[1];
			String min_time2=arr2[1];
			
			if (min_time1.length()>2) {
				min_time1=min_time1.substring(0, 2);
			}
			
		    if (min_time2.length()>2) {
				min_time2=min_time2.substring(0, 2);
			}
		    if (min_time2.length()==1) {
				min_time2="0"+min_time2;
			}
			
			/*System.out.println("Hr 1"+hr_time1);
			System.out.println("Hr 2"+hr_time2);
			System.out.println("Min 1"+min_time1);
			System.out.println("Min 2"+min_time2);*/
			
			
			String updated_time1=hr_time1+min_time1;
			String updated_time2=hr_time2+min_time2;
			
			//System.out.println("Updated time 1  "+updated_time1);
			//System.out.println("Updated time 2  "+updated_time2);
			
			
			
			
			//String calculated_time = "00:00";
			int new_time1=Integer.parseInt(updated_time1);
			//System.out.println("Time 1 "+new_time1);
			int new_time2=Integer.parseInt(updated_time2);
			//System.out.println("Time 2 "+new_time2);
			try {
				if (time1!=null && !time1.equals("") && time2!=null && !time2.equals("")) {
					if (new_time1>=new_time2) {
						flag=true;
					}
					else if (new_time1<new_time2) {
						flag=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		 }
	
		@SuppressWarnings("static-access")
		public static String timeInBetween(String date,String time1, String time2,String date2,String time3) {
			//System.out.println("Current Time "+time3);
			String returnValue = "";
			String timearr1[] = null;
			timearr1 = time1.split(":");
			String timearr2[] = null;
			timearr2 = time2.split(":");
			String timearr3[] = null;
			timearr3 = time3.split(":");
			try {
				 Date convertedDate = DATE_FORMAT.parse(date); 
	             Calendar cal1 = Calendar.getInstance();
	             cal1.setTime(convertedDate);
				//System.out.println(" Before Cal one time "+cal1.getTime());
				cal1.add(cal1.HOUR,Integer.parseInt(timearr1[0]));
				cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
				//System.out.println("Cal one time "+cal1.getTime());
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(convertedDate);
				//System.out.println("Cal two time 1111"+cal2.getTime());
				cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
				cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));
				//System.out.println("Cal two time "+cal2.getTime());
				
				Date convertedDate1 = DATE_FORMAT.parse(date2); 
				Calendar cal3 = Calendar.getInstance();
				cal3.setTime(convertedDate1);
				//System.out.println("Cal three time 1111"+cal3.getTime());
				cal3.add(cal3.HOUR, Integer.parseInt(timearr3[0]));
				cal3.add(cal3.MINUTE, Integer.parseInt(timearr3[1]));
				//System.out.println("Cal three time "+cal3.getTime());

				if (cal1.getTime().before(cal3.getTime())) {
					if (cal3.getTime().before(cal2.getTime()) || cal3.getTime().equals(cal2.getTime())) {
						returnValue="middle";
					}
					else if (cal2.getTime().before(cal3.getTime())) {
						returnValue="after";
					}
				}
				else if (cal3.getTime().before(cal1.getTime())) {
					returnValue="before";
				}
				else if (cal3.getTime().equals(cal1.getTime())) {
					returnValue="middle";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returnValue;
		}
		
		public static boolean checkTwoTimeWithMilSec(String time1, String time2)
		{
			boolean flag = false;
			String timearr1[] = null;
			timearr1 = time1.split(":");

			String timearr2[] = null;
			timearr2 = time2.split(":");

			try
			{

				Calendar cal1 = Calendar.getInstance();

				cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
				cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));

				Calendar cal2 = Calendar.getInstance();

				cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
				cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));

				long millisesond1 = cal1.getTimeInMillis();
				long millisesond2 = cal2.getTimeInMillis();
				if (millisesond2 > millisesond1)
				{
					flag = true;
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return flag;
		}
		
		public static boolean comparetwoDatesForDAR(String datee1, String datee2) {
			boolean status = true;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = sdf.parse(datee1);
				Date date2 = sdf.parse(datee2);
				if (date1.compareTo(date2) > 0) {
					status = false;
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		
		public static boolean checkTimeBetween2Times(String firstTime, String lastTime, String checkTime)
		{
			String timearr1[] = null;
			timearr1 = firstTime.split(":");
			String timearr2[] = null;
			timearr2 = lastTime.split(":");
			String timearr3[] = null;
			timearr3 = checkTime.split(":");
			
			Calendar cal1 = Calendar.getInstance();
			cal1.add(cal1.HOUR,Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(cal2.HOUR,Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));
			
			Calendar cal3 = Calendar.getInstance();
			cal3.add(cal3.HOUR,Integer.parseInt(timearr3[0]));
			cal3.add(cal3.MINUTE, Integer.parseInt(timearr3[1]));
			

			if (cal1.getTime().before(cal3.getTime()) && cal3.getTime().before(cal2.getTime())) 
			{
				return true;
			}
			else if(cal1.getTime().equals(cal3.getTime()) || cal3.getTime().equals(cal2.getTime()))
			{
				return true;
			}
			else
			{
				return  false;
			}
			
		}
		public static List<String> returnListOfDatesBetweenTwoDates(
				String dateStart, String dateStop)   {
			List<String> listOfDates = new ArrayList<String>();
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		        Date date11;
				try {
					date11 = df.parse(dateStart);
					 Date date12 = df.parse(dateStop);
						Calendar startCal = Calendar.getInstance();
						startCal.setTime(date11);
						Calendar endCal = Calendar.getInstance();
						endCal.setTime(date12);
						while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
							java.util.Date date = startCal.getTime();
							listOfDates.add(new SimpleDateFormat("yyyy-MM-dd")
									.format(date).trim());
							startCal.add(Calendar.DATE, 1);
						}
					
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
		       
			return listOfDates;
		}
	public static void main(String[] args) {
		// System.out.println("New Date Time "+getNextDateFromDate(getCurrentDateIndianFormat(),-5));
		// "2014-01-21","23:00","02:00","02:30"));
		// System.out.println("Date Time  "+getDateTime("201305201530"));
		// System.out.println("Time diffrence  "+time_difference("2013-12-02",
		// "09:15:00","2013-12-02","14:15:00"));
	}
}