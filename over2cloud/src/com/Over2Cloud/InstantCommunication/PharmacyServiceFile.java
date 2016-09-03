package com.Over2Cloud.InstantCommunication;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.activityboard.MorningReport;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.order.pharmacy.PharmacyServiceFileHelper;

public class PharmacyServiceFile extends Thread{
	
SessionFactory connection=null;
HelpdeskUniversalHelper HUH=null;
CommunicationHelper CH = null;
FeedbackUniversalHelper FUH = null;
public PharmacyServiceFile(final SessionFactory connection,final HelpdeskUniversalHelper HUHObj,final CommunicationHelper CHObj,final FeedbackUniversalHelper FUHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
	this.FUH=FUHObj;
  }
	PharmacyServiceFileHelper HSFH = new PharmacyServiceFileHelper();
	 
	public void run()
	{
		try {
			 while (true) {
				try {
					// Method calling for escalating a ticket
					
					System.out.println("Esc Start............");
			         HSFH.escalateToNextLevel(connection,HUH,CH);
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
				} 	catch (Exception e) {
					CH.addMessage("kamlesh", "IT", "8010797571", "Pharmacy escalation!!!!", "", "Pending", "0", "TEST", connection);
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
			final SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-4");
		    final HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
		    final CommunicationHelper CH = new CommunicationHelper();
		    final FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
		     
		     Thread esc = new Thread(new PharmacyServiceFile(connection,HUH,CH,FUH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 }