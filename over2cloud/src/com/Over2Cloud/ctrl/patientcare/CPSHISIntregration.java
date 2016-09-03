
//package com.Over2Cloud.InstantCommunication;
package com.Over2Cloud.ctrl.patientcare;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.MachineOrder.ClientOrdDataFetch;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
public class CPSHISIntregration extends Thread {

private static final Log log = LogFactory.getLog(ClientOrdDataFetch.class);
SessionFactory connection=null;
HelpdeskUniversalHelper HUH=null;
CommunicationHelper CH = null;
FeedbackUniversalHelper FUH = null;

public CPSHISIntregration(SessionFactory connection,HelpdeskUniversalHelper HUHObj,CommunicationHelper CHObj,FeedbackUniversalHelper FUHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
	this.FUH=FUHObj;
	
  }
CPSHISIntregrationHelper CPSINTHELP = new CPSHISIntregrationHelper();
	public void run()
	{
		try {
			 while (true) {
				try {
					//CPSINTHELP.fetchCorporateData(log, connection, HUH, CH);
					// for fetch Package from HIS
					CPSINTHELP.getCustomerData(log, connection, HUH, CH);
					
					//CPSINTHELP.getPackageData(log, connection, HUH, CH);
			     //  CPSINTHELP.getBillingGroupData(log, connection, HUH, CH);
			         
			        // CPSINTHELP.getAppointment(log, connection, HUH, CH);
					
					// 11-03-2016 new VIEW 
				//	CPSINTHELP.fetchCorporateDataNew(log, connection, HUH, CH);
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					////System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60*60*24);
					////System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
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
		     Thread esc = new Thread(new CPSHISIntregration(connection,HUH,CH,FUH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 

}
