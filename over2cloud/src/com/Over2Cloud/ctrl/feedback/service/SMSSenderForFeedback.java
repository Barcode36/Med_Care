package com.Over2Cloud.ctrl.feedback.service;

import org.hibernate.SessionFactory;

import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class SMSSenderForFeedback extends Thread
{
	SessionFactory connection=null;
	public SMSSenderForFeedback(SessionFactory connection) 
	{
		super();
		this.connection = connection;
	}

	public void run()
	{
		try
		{
			while(true)
			{
				try
				{
					SMSSenderForFeedbackHelper helper=new SMSSenderForFeedbackHelper();
					helper.sendSMSForFeedback(connection);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		try
		{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			while(true)
			{
				Thread uclient=new Thread(new SMSSenderForFeedback(connection));
				uclient.start();
				uclient.join();
				Thread.sleep(1000*2);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
