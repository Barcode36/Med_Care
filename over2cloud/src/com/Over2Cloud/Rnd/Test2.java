package com.Over2Cloud.Rnd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;


public class Test2 {
	public static void main(String[] args) 
	{
		try
		{
			findDifferenceTwoTime("23:59", "24:00");
			
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
}