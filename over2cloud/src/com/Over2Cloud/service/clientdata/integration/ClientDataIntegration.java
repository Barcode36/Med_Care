package com.Over2Cloud.service.clientdata.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import hibernate.common.HisHibernateSessionFactory;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class ClientDataIntegration implements Runnable{
	private static final Log log = LogFactory.getLog(ClientDataIntegration.class);
	
	SessionFactory clientConnection = null;
	Session sessionHis = null; 
	private ClientDataIntegrationHelper CDIH =  new ClientDataIntegrationHelper();
	
	/**
	 * Constructor will create session 
	 */
	public ClientDataIntegration() {
		//Getting SessionFactory for Client...
		clientConnection = new ConnectionHelper().getSessionFactory("IN-4");
		//Getting Hibernate Session for HIS... 
		sessionHis = HisHibernateSessionFactory.getSession();
	}
	
	/**
	 * Main Method Call...
	 */
	public static void main(String[] args) {
		try{
				Thread clientDataIntegration = new Thread(new ClientDataIntegration());
				clientDataIntegration.start();
				clientDataIntegration.join();
		}catch(Exception E){
			E.printStackTrace();
			log.error("@ClientDataIntegration @"+DateUtil.getCurrentTimeStamp()+" :: "+E);
		}
	}

	@Override
	public void run() {
		//Case 1: Check and Create email ID
		try
		{
		 //CDIH.checkLoginDetailHibernate(log, clientConnection, sessionHis);
		 System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds());
			
				Thread.sleep(1000*5);
			
			System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimewithSeconds());

			Runtime rt = Runtime.getRuntime();
			rt.gc();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //Case 2: Update DOS 
		//UDOS.updateLoginDetail();
	}

	/**
	 * @category :: Close Hibernate Session At the time of Thread Close.
	 * 
	 */
	@SuppressWarnings("unused")
	private void end() {
		sessionHis.close();
	}
}
