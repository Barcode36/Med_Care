package com.Over2Cloud.ctrl.WorkingHrs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class WorkingHour
{
	public static void main(String[] args)
	{
		SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		WorkingHourAction WHA = new WorkingHourAction();
		String addressingTime = "04:00", resolutionTime = "4:00", escFlag="Y";
		String date = DateUtil.getCurrentDateUSFormat();
		String time = DateUtil.getCurrentTimeHourMin();
		String lastCheckDate = "2014-09-15";
		String moduleName = "HASTM";
		/*List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, addressingTime, resolutionTime, escFlag, date, time, moduleName);
		System.out.println(dateTime.get(0)+" "+dateTime.get(1)+" "+dateTime.get(2)+" "+dateTime.get(3));*/
		String nonWorkingTime = new WorkingHourHelper().getWorkingHrs(date, connection, cbt, moduleName,lastCheckDate);
		System.out.println(nonWorkingTime);
	}

}
