package com.Over2Cloud.ctrl.feedback.service;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class KeyWordRecv extends Thread
{
	SessionFactory connection=null;
	public KeyWordRecv(SessionFactory connection)
	{
		super();
		this.connection = connection;
	}

	public void run()
	{
		try
		{
			 while (true)
			 {
				try
				{
					 // Getting data From t2mkeyword table For Feedback
					 KeyWordRecvHelper help=new KeyWordRecvHelper();
					 help.getKeywords(connection);
					 
				        Runtime rt = Runtime.getRuntime();
						rt.gc();
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(5000);
						System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				}
				 catch (Exception e)
				 {
					e.printStackTrace();
				}
			 }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
			while(true){
				Thread uclient=new Thread(new KeyWordRecv(connection));
				uclient.start();
				uclient.join();
				Thread.sleep(1000*2);
			}
		}catch(Exception E){
			E.printStackTrace();
		}
	}
}
