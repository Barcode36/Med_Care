package com.Over2Cloud.Rnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.Over2Cloud.CommonClasses.DateUtil;

public class Testing
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		   try {
			   
			   // create a calendar
			   /*Calendar cal = new GregorianCalendar();
			   System.out.println(cal.getTime());
			   // print current time zone
			   String name=cal.getTimeZone().getDisplayName();
			   System.out.println("Current Time Zone:" + name );
			   TimeZone tz = TimeZone.getTimeZone("IST");

			   // set the time zone with the given time zone value 
			   // and print it
			   Calendar cal2 = new  GregorianCalendar();
			   cal.setTimeZone(tz);
			   System.out.println(cal2.getTimeZone().getDisplayName());
			   System.out.println(cal2.getTime());*/
			   
			  /* TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
			   TimeZone timeZone2 = TimeZone.getTimeZone("IST");

			   Calendar calendar = new GregorianCalendar();

			   long timeCPH = calendar.getTimeInMillis();
			   System.out.println("timeCPH  = " + timeCPH);
			   System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));

			   calendar.setTimeZone(timeZone2);

			   long timeLA = calendar.getTimeInMillis();
			   System.out.println("timeLA   = " + timeLA);
			   System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
			   
			   
			   int year       = calendar.get(Calendar.YEAR);
			   int month      = calendar.get(Calendar.MONTH); 
			   int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
			   int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
			   int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
			   int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);

			   int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
			   int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
			   int minute     = calendar.get(Calendar.MINUTE);
			   int second     = calendar.get(Calendar.SECOND);
			   int millisecond= calendar.get(Calendar.MILLISECOND);
			   
			   
			   System.out.println(year+"-"+month+"-"+dayOfMonth);
			   
			   System.out.println(hourOfDay+":"+minute+":"+second);*/
			   
			   
			/*   Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			 //change the timezone
			   System.out.println(cSchedStartCal.getInstance());
			 cSchedStartCal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			 //get the current hour of the day in the new timezone
			 cSchedStartCal.get(Calendar.HOUR_OF_DAY);
			 System.out.println(cSchedStartCal.getInstance());*/
			   
			   /*
			    * 
			    * 



               Process p = Runtime.getRuntime().exec(
                               System.getenv("windir") + "\\system32\\"
                                               + "tasklist.exe");

               String line;
               BufferedReader input = new BufferedReader(
                               new InputStreamReader(p.getInputStream()));
               while ((line = input.readLine()) != null) {
                       System.out.println(line); // <-- Parse data here.
               }
       */
			   
			   Calendar now = Calendar.getInstance();
			   
			    //get current TimeZone using getTimeZone method of Calendar class
			    TimeZone timeZone = now.getTimeZone();
			   
			    //display current TimeZone using getDisplayName() method of TimeZone class
			    System.out.println("Current TimeZone is : " + timeZone);
			   SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		   String dateInString =DateUtil.getCurrentDateIndianFormat()+ " "+DateUtil.getCurrentTimewithSeconds();
		   
		   System.out.println("hamara time "+dateInString);
			   Date date = formatter.parse(dateInString);
			   SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				TimeZone tzInAmerica = TimeZone.getTimeZone(timeZone.getID());
				sdfAmerica.setTimeZone(tzInAmerica);
				String sDateInAmerica = sdfAmerica.format(date);
				Date dateInAmerica = formatter.parse(sDateInAmerica);

				System.out.println("TimeZone : " + tzInAmerica);
				System.out.println("Date (String) : " + sDateInAmerica);
				System.out.println("Date (Object) : " + formatter.format(dateInAmerica)); 
				
		   } catch (Exception E) {
               E.printStackTrace();
       }

	}

}
