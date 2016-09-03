package com.Over2Cloud.ctrl.criticalPatient;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class CriticalServiceFile extends Thread {

	
SessionFactory connection=null;
HelpdeskUniversalHelper HUH=null;
CommunicationHelper CH = null;
FeedbackUniversalHelper FUH = null;
public CriticalServiceFile(SessionFactory connection,HelpdeskUniversalHelper HUHObj,CommunicationHelper CHObj,FeedbackUniversalHelper FUHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
	this.FUH=FUHObj;
	
  }
CriticalServiceHelper OSFH = new CriticalServiceHelper();
CriticalIntegrationHelper CIH=new CriticalIntegrationHelper();
	public void run()
	{
		try {
			 while (true) {
				try {
					// Method calling for escalating a ticket
			         //HSFH.escalateToNextLevel(connection,HUH,CH);
			         OSFH.escalateToNextLevel(connection, HUH, CH);
			        // OSFH.SnoozetoPending(connection, HUH, CH);
			       //CIH.FetchRecordsFromHIS(connection);
					
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	 {
		try {
			 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-4");
		     HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
		     CommunicationHelper CH = new CommunicationHelper();
		     FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
		     Thread esc = new Thread(new CriticalServiceFile(connection,HUH,CH,FUH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 

}
