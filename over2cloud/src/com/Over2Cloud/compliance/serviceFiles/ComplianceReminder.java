package com.Over2Cloud.compliance.serviceFiles;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class ComplianceReminder implements Runnable
{
	SessionFactory connection=null;
	public ComplianceReminder(SessionFactory connection)
	{
		this.connection=connection;
	}
	ComplianceReminderHelper CRH = new ComplianceReminderHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					CRH.checkReminder(connection);
					CRH.checkEscalation(connection);
					CRH.checkRecurring(connection);
					CRH.checkSnooze(connection);
					CRH.checkTodayPending(connection);
					//CRH.createUser(connection);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(4000);
					System.out.println("Woke Up........................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
			    }
			    catch (Exception e) 
			    {
			    	e.printStackTrace();
			    }
		    }
		}
		catch (Exception e) 
		{
		  e.printStackTrace();
		}
    }
	
	public static void main(String[] args) 
	{
		try 
		{
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			  Thread esc = new Thread(new ComplianceReminder(connection));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
