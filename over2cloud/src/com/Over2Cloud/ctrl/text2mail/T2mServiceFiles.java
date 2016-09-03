package com.Over2Cloud.ctrl.text2mail;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class T2mServiceFiles extends Thread 
{

	

	private static SessionFactory connection=null;

	
	private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	
	public T2mServiceFiles(SessionFactory connection) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						t2mvirtual.getVirtualNoData(connection);
						
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000*1);
						System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

						Runtime rt = Runtime.getRuntime();
						rt.gc();
	
					}
									}
				 catch (Exception e)
				 {
					 e.printStackTrace();
					 }
		
	}
	
	public static void main(String[] args) {
		Thread.State state ;
		try{
			connection = new ConnectionHelper().getSessionFactory("IN-1");
			
				Thread uclient=new Thread(new T2mServiceFiles(connection));
				state = uclient.getState(); 

			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
			
		}catch(Exception E){
			E.printStackTrace();
		}
	}




}
