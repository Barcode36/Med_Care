package com.Over2Cloud.ctrl.helpdesk.EscalationConfig;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;


public class EscalationReminder implements Runnable
{
	SessionFactory connection=null;
	HelpdeskUniversalHelper HUH=null;
	CommunicationHelper CH = null;
	public EscalationReminder(SessionFactory connection,HelpdeskUniversalHelper HUH,CommunicationHelper CH)
	{
		this.connection=connection;
		this.connection=connection;
		this.HUH=HUH;
		this.CH=CH;
	}
	EscalationReminderHelper ERH = new EscalationReminderHelper();
	public void run()
	{
		try
		{
			while (true) 
		    {
				try 
			    {
					ERH.checkEscalation(connection,HUH,CH);
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
			  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-2");
			  HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
			  CommunicationHelper CH = new CommunicationHelper();
			  Thread esc = new Thread(new EscalationReminder(connection,HUH,CH));
			  esc.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}