package com.Over2Cloud.ctrl.order.pharmacy;




import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.MailReader;





public class PharmacyUpdate  extends Thread  {

	private static final Log log = LogFactory.getLog(PharmacyUpdate.class);
	

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	private HISTESTHelper CDIH =  new HISTESTHelper();
	private HISHelperRedirectServer CDIHRe = new HISHelperRedirectServer();
	//private MailReader mailread = new MailReader();
	CommunicationHelper ch = new CommunicationHelper();
	
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	
	public PharmacyUpdate(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
						System.out.println("het get get");
						
						String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
						System.out.println("day  "+day);
						if((DateUtil.getCurrentDay()==0&& DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
								(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" ))
								|| (day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" ))){
							System.out.println("yes this is the time to redirect to 66 server");
							//CDIHRe.CRCDATAFetchOTM(log, connection, sessionHis, ch);
							
							CDIHRe.CRCDATAPharmaUpdate(log, connection, sessionHis, ch);
						}
						else{
						//CDIH.CRCDATAFetchOTM(log, connection, sessionHis, ch);
							
							CDIH.CRCDATAPharmaUpdate(log, connection, sessionHis, ch);
						}
						//mailread.readMails(connection);	
							//CDIH.PHNotDispanseSms(log, connection, sessionHis, ch);
							
							
							
							//CDIH.testDataFieldName(log, connection, sessionHis, ch);
							//Dreamsol_IP_VW
							//CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
							//CDIH.CRCDATAFetchOTM(log, connection, sessionHis, ch);
						//CRCDATAFetchONE
						//CDIH.CRCDATAPharmaUpdate(log, connection, sessionHis, ch);
						
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 60 );
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
			
				Thread uclient=new Thread(new PharmacyUpdate(connection, sessionHis));
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