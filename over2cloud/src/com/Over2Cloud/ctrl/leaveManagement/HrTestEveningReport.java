package com.Over2Cloud.ctrl.leaveManagement;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class HrTestEveningReport extends Thread
{

private static SessionFactory connection=null;
HRReportHelper HUH=null;
CommunicationHelper CH = null;


@SuppressWarnings("static-access")
public HrTestEveningReport(SessionFactory connection,HRReportHelper HUHObj,CommunicationHelper CHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
  }
	//HelpdeskServiceFileHelper HSFH = new HelpdeskServiceFileHelper();
HRReportHelper HRR=new HRReportHelper();
	@SuppressWarnings("null")
	public void run()
	{
		try {
			 while (true) {
				try {
					
					
					List<AttendancePojo> reportData=HRR.getAllData(connection);
					List<AttendancePojo> reportDataBeforeDate=HRR.getAllDataBeforeDate(connection);
					List<AttendancePojo> reportForApprovalStatus=HRR.getApprovalStatus(connection);
					List<AttendancePojo> reportForNewJoiners=HRR.getNewJoiners(connection);
					List<AttendancePojo> reportForEvents=HRR.getEvents(connection);
					List<AttendancePojo> reportForAbsentData=HRR.getAbsentDataBeforeDate(connection);
					System.out.println("reportForEvents .........."+ reportForEvents.size());
					if (reportForAbsentData !=null || reportForAbsentData.size()>0   || reportForEvents !=null || reportForEvents.size()>0   || reportForNewJoiners !=null || reportForNewJoiners.size()>0 || reportData !=null || reportData.size()>0 || reportForApprovalStatus !=null || reportForApprovalStatus.size()>0 ||reportDataBeforeDate!=null || reportDataBeforeDate.size()>0) 
					{
						String mailText = HRR.getConfigMailForReport(reportData,reportForApprovalStatus,reportForNewJoiners,reportDataBeforeDate,reportForEvents,reportForAbsentData);
						
					//String filepath=HRR.writeDataInExcel(reportData,reportForApprovalStatus,reportForNewJoiners);
						if (mailText!=null && !mailText.equals("")) {
							new CommunicationHelper().addMail("Ms. Sumiti Bajpai","Technical","sumitib@dreamsol.biz","Human Resource Summary Report for " +DateUtil.getCurrentDateIndianFormat(), mailText,"","Pending", "0","","HR",connection);
							//new CommunicationHelper().addMail("Ms. Sumiti Bajpai","Technical","sumitib@dreamsol.biz","Human Resource Summary Report for " +DateUtil.getCurrentDate(), mailtext,"","Pending", "0",filepath,"HR",connection);
						}
					}
					
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
			System.out.println("----------");
			 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		     CommunicationHelper CH = new CommunicationHelper();
		     HRReportHelper HRR=new HRReportHelper();
		     Thread esc = new Thread(new HrTestEveningReport(connection,HRR,CH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 

}
