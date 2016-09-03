package com.Over2Cloud.InstantCommunication;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.order.feedbackaction.OrderServiceFileHelper;

public class OrderServiceFile extends Thread {

	
SessionFactory connection=null;
HelpdeskUniversalHelper HUH=null;
CommunicationHelper CH = null;
FeedbackUniversalHelper FUH = null;
public OrderServiceFile(SessionFactory connection,HelpdeskUniversalHelper HUHObj,CommunicationHelper CHObj,FeedbackUniversalHelper FUHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
	this.FUH=FUHObj;
	
  }
	OrderServiceFileHelper OSFH = new OrderServiceFileHelper();
	public void run()
	{
		try {
			 while (true) {
				try {
					// Method calling for escalating a ticket
			         //HSFH.escalateToNextLevel(connection,HUH,CH);
			         OSFH.escalateToNextLevel(connection, HUH, CH);
			         OSFH.escalateCartToNextLevel(connection, HUH, CH);
			         OSFH.SnoozetoPending(connection, HUH, CH);
			         
					// Method calling for Snooze to Active 
					// HSFH.SnoozetoPending(connection,HUH,CH);
					// Method calling for Snooze to Active 
					  // HSFH.ReportGeneration(connection,HUH,CH);
					// Method calling for escalating a ticket
					// HSFH.escalationInAsset(connection,HUH,CH);
					 //  mreport.getMorningReportToData(DateUtil.getCurrentDateUSFormat(),connection,HUH,CH);
					
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
		     Thread esc = new Thread(new OrderServiceFile(connection,HUH,CH,FUH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 

}
