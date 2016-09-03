package com.Over2Cloud.ctrl.leaveManagement;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;


public class HRServiceFiles extends Thread
{
	

	private static SessionFactory connection=null;

	
	private final HrVirtualNoDataReceiver hrvirtual=new HrVirtualNoDataReceiver();
	
	public HRServiceFiles(SessionFactory connection) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
						System.out.println("Requesting VMN Server for Data...");
						hrvirtual.getVirtualNoData(connection);
						
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000*60);
						System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

						Runtime rt = Runtime.getRuntime();
						rt.gc();
	
					}
									}
				 catch (Exception e)
				 {
					 }
		
	}
	
	public static void main(String[] args) {
		Thread.State state ;
		try{
			connection = new ConnectionHelper().getSessionFactory("IN-1");
			
				Thread uclient=new Thread(new HRServiceFiles(connection));
				state = uclient.getState(); 

			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
			
		}catch(Exception E){
			E.printStackTrace();
		}
	}


}
