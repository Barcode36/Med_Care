package com.Over2Cloud.Rnd;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
   public static void main(String [] args) throws Exception {
       /*SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
       SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
       Date date = displayFormat.parse("13:30");
       //System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
       
       System.out.println(displayFormat.format(date) + " = " + parseFormat.format(date));*/
	   
	   String  appoinmentTime = "20-07-2016 16:00 AM";
	   
	   appoinmentTime = appoinmentTime.split(" ", 0)[1];
	   System.out.println("  "+appoinmentTime);
   }
}