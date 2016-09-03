package com.Over2Cloud.ctrl.MachineOrder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import hibernate.common.HisHibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.service.clientdata.integration.ClientDataIntegration;
import com.Over2Cloud.service.clientdata.integration.ClientDataIntegrationHelper;
import com.Over2Cloud.service.clientdata.integration.ClientDataIntegrationRedirectServer;

public class ClientOrdDataFetch  extends Thread  {

	private static final Log log = LogFactory.getLog(ClientOrdDataFetch.class);
	

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	private ClientDataIntegrationHelper CDIH =  new ClientDataIntegrationHelper();
	private ClientDataIntegrationRedirectServer CDIHRedirect =  new ClientDataIntegrationRedirectServer();
	CommunicationHelper ch = new CommunicationHelper();
	
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	
	public ClientOrdDataFetch(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
						System.out.println("day  "+day);
						if((DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
								(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:50", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
								(day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" ))){
							System.out.println("yes this is the time to redirect to 66 server by manab");
							CDIHRedirect.datafetchForOrder(log, connection, sessionHis, ch);
							CDIHRedirect.datafetchForOrderEM(log, connection, sessionHis, ch);
						}
						
						else{
							System.out.println("88 server");
							try 
							{
								 CDIH.datafetchForOrder(log, connection, sessionHis, ch);
							} catch (Exception e) {
							}
						
							 try 
							 {
								 CDIH.datafetchForOrderEM(log, connection, sessionHis, ch);
							} catch (Exception e) {
							}
						}
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 5 * 60 );
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
			
			System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			System.out.println("After HIS DB connection"+sessionHis);
			
				Thread uclient=new Thread(new ClientOrdDataFetch(connection, sessionHis));
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
