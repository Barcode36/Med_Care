package com.Over2Cloud.ctrl.feedback.service;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class FeedBackServiceFiles extends Thread 
{
	SessionFactory connection=null;

	private final KeyWordRecvHelper help=new KeyWordRecvHelper();
	private final SMSSenderForFeedbackHelper helper=new SMSSenderForFeedbackHelper();
	private final VirtualNoDataReceiver virtual=new VirtualNoDataReceiver();
	
	public FeedBackServiceFiles(SessionFactory connection) {
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
					// For SMS Sender to Patients
					helper.sendSMSForFeedback(connection);
					// For KeyWord Recv
					help.getKeywords(connection);
					System.out.println("Getting Virtual No Data...");
					//virtual.getVirtualNoData(connection);
					
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

					Runtime rt = Runtime.getRuntime();
					rt.gc();
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
				Thread uclient=new Thread(new FeedBackServiceFiles(connection));
				uclient.start();
				uclient.join();
			}
		}catch(Exception E){
			E.printStackTrace();
		}
	}
}