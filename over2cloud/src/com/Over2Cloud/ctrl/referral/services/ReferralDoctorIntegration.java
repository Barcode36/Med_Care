package com.Over2Cloud.ctrl.referral.services;

import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;


public class ReferralDoctorIntegration extends Thread
{

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	ReferralDoctorIntegrationHelper RDIH = new ReferralDoctorIntegrationHelper();
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	
	public ReferralDoctorIntegration(SessionFactory connection ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						RDIH.fetchDoctorDetails(connection);
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000 * 60 * 60 * 24);
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
			
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			//sessionHis = HisHibernateSessionFactory.getSession();
			
				Thread uclient=new Thread(new ReferralDoctorIntegration(connection));
				System.out.println("Thread Created Successfuly!!");
				state = uclient.getState(); 
				System.out.println("Thread's State:: "+state);
			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
				System.out.println("Thread Started Successfuly!!");
			
		}catch(Exception E){
			try {
				OutputStream out = new FileOutputStream(new File("c://output.txt"));
				byte[] b=E.toString().getBytes();
				out.write(b);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			E.printStackTrace();
		}
	}






}