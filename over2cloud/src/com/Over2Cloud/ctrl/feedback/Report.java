package com.Over2Cloud.ctrl.feedback;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.SMSTest;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.ticketconfiguration.TicketConfiguration;

public class Report implements Runnable{
	
	SessionFactory connectionSpace=null;
	AllConnection conn=null;
    AllConnectionEntry ob=null;
    
    String clientid=null;

    public Report() {
		conn=new ConnectionFactory("1_clouddb", "127.0.0.1","dreamsol","dreamsol","3306");
		ob=conn.GetAllCollectionwithArg();
		connectionSpace=ob.getSession();
	}
  
	public Report(String clientId) {
		this.clientid=clientId;
		 connectionSpace = new ConnectionHelper().getSessionFactory(clientId);
	}
	
	@Override
	public void run() {
		while(true)
		{
			try {
					String mailText = "";
					Mailtest mail = new Mailtest();
					List list = getEmailIdListForReport(connectionSpace, String.valueOf(2));
					boolean mailSend=true;
					if(list!=null && list.size()>0)
					{
					//	System.out.println("List Size is as <><><><><>"+list.size());
						for (Iterator iterator = list.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							
							if(DateUtil.time_update(object[4].toString(), object[5].toString()))
							{
								mailText = sendDailyReport(object[0].toString());
								mailSend = mail.confMail("smtp.gmail.com", "465", "services@dreamsol.biz", "dreamsol", object[2].toString(), object[3].toString(), mailText);
							}
							
							if(mailSend)
							{
								mailSend=updateDateByEmail(connectionSpace, object[4].toString(), object[6].toString());
								if(mailSend)
								{
									String msg=getSMSMessageForDailyReport(object[0].toString(),connectionSpace);
									mailSend=new SMSTest().sendSMS(msg, object[1].toString());
									//System.out.println("SMS >>>>"+msg+"Mob>>>>"+object[1].toString());
								}
							}
						}
					}
					List hodList = getEmailIdListForHODReport(connectionSpace, String.valueOf(1));
					
					if(hodList!=null && hodList.size()>0)
					{
						for (Iterator iterator = hodList.iterator(); iterator .hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							if(DateUtil.time_update(object[6].toString(), object[7].toString()))
							{
								mailText = getHodDailyReport(object[0].toString(), object[4].toString(), object[5].toString());
								mailSend = mail.confMail("smtp.gmail.com", "465", "services@dreamsol.biz", "dreamsol", object[2].toString(), object[3].toString(), mailText);
							}
							if(mailSend)
							{
								mailSend=updateDateByEmail(connectionSpace, object[6].toString(), object[8].toString());
								if(mailSend)
								{
									String str=getSingleColumnValueOnId(connectionSpace,"subdeptname","subdepartment","id",object[4].toString());
									String msg=getSMSMessageForDailyReportHOD(object[0].toString(),str,object[4].toString());
									mailSend=new SMSTest().sendSMS(msg, object[1].toString());
						//			System.out.println("SMS >>>>"+msg+"Mob>>>>"+object[1].toString());
								}
							}
						}
					}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			finally
			{
				try {
					Thread.currentThread();
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	public String getSingleColumnValueOnId(SessionFactory session,String colName,String tableName,String whereColName,String whereColVal )
	{
		String val=null;
		List data=null;
		Session hSession=null;
		try
		{
			hSession=session.openSession();
			//select distinct subdeptname from subdepartment
			String qru="select distinct "+colName+" from "+tableName+" where "+whereColName+"='"+whereColVal+"'";
			data=hSession.createSQLQuery(qru).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					val=(object.toString());
				}
			}
		}
		
		return val;
	}
	public String getSMSMessageForDailyReport(String name,SessionFactory connectionSpace)
	{
		EscalationActionControlDao controlDao = new EscalationActionControlDao();
		int totalPositive=controlDao.dashboardCounter(connectionSpace,"feedbackdata","date",DateUtil.getCurrentDateUSFormat(),"targetOn","Yes");
		int negativeTab=controlDao.dashboardTicketCounter(connectionSpace,"","","T");
		int negativeSMS=controlDao.dashboardTicketCounter(connectionSpace,"","","S");
		
		// Patient Care
		int pc_Open=getCounter("9","and status='Pending'",connectionSpace);
		int pc_Closed=getCounter("9","and status='Resolved'",connectionSpace);
		
		
		
		// Front Office
		int fo_Open=getCounter("1","and status='Pending'",connectionSpace);
		int fo_Closed=getCounter("1","and status='Resolved'",connectionSpace);
		
		// Medical Care
		int mc_Open=getCounter("2","and status='Pending'",connectionSpace);
		int mc_Closed=getCounter("2","and status='Resolved'",connectionSpace);
		
		// Nursing Care
		int nc_Open=getCounter("3","and status='Pending'",connectionSpace);
		int nc_Closed=getCounter("3","and status='Resolved'",connectionSpace);
		
		// Diagnostics
		int dg_Open=getCounter("4","and status='Pending'",connectionSpace);
		int dg_Closed=getCounter("4","and status='Resolved'",connectionSpace);
		
		// Pharmacy
		int py_Open=getCounter("5","and status='Pending'",connectionSpace);
		int py_Closed=getCounter("5","and status='Resolved'",connectionSpace);
		
		// HouseKeeping
		int hk_Open=getCounter("6","and status='Pending'",connectionSpace);
		int hk_Closed=getCounter("6","and status='Resolved'",connectionSpace);
		
		// Others
		int ot_Open=getCounter("7","and status='Pending'",connectionSpace);
		int ot_Closed=getCounter("7","and status='Resolved'",connectionSpace);
		
		
		int totalOpen=pc_Open+fo_Open+mc_Open+nc_Open+dg_Open+py_Open+hk_Open+ot_Open;
		int totalClosed=pc_Closed+fo_Closed+mc_Closed+nc_Closed+dg_Closed+py_Closed+hk_Closed+ot_Closed;
		StringBuffer buffer=new StringBuffer();	
		/*buffer.append("Dear "+name+", For all Departments Ticket Status as on "+DateUtil.getCurrentDateIndianFormat());
		buffer.append(", Positive: "+totalPositive+", Negative Tab: "+negativeTab+", Negative SMS: "+negativeSMS);
		buffer.append(", PC: Open("+pc_Open+"),Closed("+pc_Closed+")");
		buffer.append(", FO: Open("+fo_Open+"),Closed("+fo_Closed+")");
		buffer.append(", MC: Open("+mc_Open+"),Closed("+mc_Closed+")");
		buffer.append(", NC: Open("+nc_Open+"),Closed("+nc_Closed+")");
		buffer.append(", DG: Open("+dg_Open+"),Closed("+dg_Closed+")");
		buffer.append(", PY: Open("+py_Open+"),Closed("+py_Closed+")");
		buffer.append(", HK: Open("+hk_Open+"),Closed("+hk_Closed+")");
		buffer.append(", OT: Open("+ot_Open+"),Closed("+ot_Closed+")");
		buffer.append(", Total: Open("+totalOpen+"),Closed("+totalClosed+")");*/
		//buffer.append(" thanks.");
		
		
		buffer.append("Dear "+name+", For all Departments Ticket Status as on "+DateUtil.getCurrentDateIndianFormat()+", Positive: "+totalPositive+", Negative Tab: "+negativeTab+", Negative SMS: "+negativeSMS+", PC: Open-"+pc_Open+", Closed-"+pc_Closed+", FO: Open-"+fo_Open+", Closed-"+fo_Closed+", MC: Open-"+mc_Open+", Closed-"+mc_Closed+", NC: Open-"+nc_Open+", Closed-"+nc_Closed+", DG: Open-"+dg_Open+", Closed-"+dg_Closed+", PY: Open-"+py_Open+", Closed-"+py_Closed+", HK: Open-"+hk_Open+", Closed-"+hk_Closed+", OT: Open-"+ot_Open+", Closed-"+ot_Closed+" so total: Open-"+totalOpen+", Closed-"+totalClosed+". Thanks.");
		
		
		return buffer.toString();
	}
	
	public String getSMSMessageForDailyReportHOD(String name,String deptName,String subDeptId)
	{
		int totalOpen=getCounter(subDeptId,"and status='Pending'",connectionSpace);
		int totalClosed=getCounter(subDeptId,"and status='Resolved'",connectionSpace);;
		
		StringBuffer buffer=new StringBuffer();	
	/*	buffer.append("Dear "+name+", For "+deptName+" Ticket Status as on "+DateUtil.getCurrentDateIndianFormat());
		buffer.append(", Todays Open("+totalOpen+"), Closed("+totalClosed+"), C/F Open("+totalOpen+")");*/
		
		buffer.append("Dear "+name+", For "+deptName+" Ticket Status as on "+DateUtil.getCurrentDateIndianFormat()+", Todays Open-"+totalOpen+", Closed-"+totalClosed+", C/F Open-"+totalOpen+". Thanks.");
		//Dear <Name>, For <Department Name> Ticket Status as on <dd-mm-yyyy>, Todays Open-<Count (3)>, Closed-<Count (3)>, C/F Open-<Count (3)>. Thanks.
		return buffer.toString();
	}
	public int getCounter(String toSubDept,String whereQuery,SessionFactory connectionSpace)
	{
		int count=0;
		StringBuffer buffer=new StringBuffer("select count(*) from feedback_status where to_dept_subdept='"+toSubDept+"' "+whereQuery);
		//System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+buffer.toString());
		List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					count=Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}
	
	private String sendDailyReport(String name)
	{
		EscalationActionControlDao controlDao = new EscalationActionControlDao();
		int todayPositiveTicket = controlDao.dashboardCounter(connectionSpace,"feedbackdata","date",DateUtil.getCurrentDateUSFormat(),"targetOn","Yes");
		int todayNegativeTicket = controlDao.dashboardCounter(connectionSpace,"feedbackdata","date",DateUtil.getCurrentDateUSFormat(),"targetOn","No");
		int negativeViaTab = controlDao.dashboardTicketCounter(connectionSpace,"Pending","","T");
		int negativeViaSMS = controlDao.dashboardTicketCounter(connectionSpace,"Pending","","S");
		
		int total = negativeViaTab+ negativeViaSMS;
		List list = controlDao.getAllSubDept(connectionSpace);
		List report  = getDailyTicketReport(connectionSpace);
		
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody>");
	    
        mailtext.append("</tbody></table><table width='100%' align='center' style='border: 0'><tbody>");
       	mailtext.append("<tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody>");
	     
        mailtext.append("</table><b>Dear "+name+",</b><br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr>");
        mailtext.append("<td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Status of All Department On "+ DateUtil.getCurrentDateIndianFormat()+ " at "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table><br>");
        mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#4A4747'>");
	     
        
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Positive Ticket </b></td>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Negative Ticket </b></td>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Negative via TAB </b>");
		mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Negative Via SMS </b></td>");
       	
       	mailtext.append("<tr  bgcolor='#E6E6E6'><td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+todayPositiveTicket+"</td>");
       	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+todayNegativeTicket+"</td>");
       	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+negativeViaTab+"</td>");
       	mailtext.append("<td align='center'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+negativeViaSMS+"</td>");
       	
       	mailtext.append("</tr></tbody></table>");
       	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'>");
       	mailtext.append("<tbody><tr  bgcolor='#4A4747'>");
       	
       	////////////////Set Header
       	int j=0;
       	for (Iterator iterator = list.iterator(); iterator.hasNext();)
       	{
       		j++;
			Object[] object = (Object[]) iterator.next();
			mailtext.append("<th colspan='2' align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>"+ object[1] +"</strong></th>");
			if(j>6)
			{
				break;
			}
		}
       	mailtext.append("<th colspan='2'  align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total</strong></th></tr>");
       	
       	//////////////////Set Ticket Status
       	mailtext.append("<tr  bgcolor='#696565'>");
       	for (int i = 0; i <= 6; i++) {
       		
       		mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Open</td>");
           	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Close</td>");
		}
       	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Open</td>");
       	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Close</td>");
       	mailtext.append("</tr>");
       	
       	///////////////Insert Records in table
       	mailtext.append("<tr  bgcolor='#FFFFFF'>");
       	int totalTicket = 0;
       	j=0;
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    		j++;
			Object[] object = (Object[]) iterator.next();
			totalTicket += (controlDao.getOpenTicketStatus(connectionSpace, DateUtil.getCurrentDateUSFormat(), "Open", object[0].toString())+ controlDao.getOpenTicketStatus(connectionSpace, DateUtil.getCurrentDateUSFormat(), "Resolved", object[0].toString()));
			mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ getCountTicketViaSubDept(connectionSpace, "Pending", object[0].toString())  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ getCountTicketViaSubDept(connectionSpace, "Resolved", object[0].toString())  +"</td>");
           	if(j>6)
			{
				break;
			}
		}
    	//System.out.println(">>>>>>>>>>>>Total Ticket : "+ totalTicket);
       	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+totalTicket+"</td>");
       	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+totalTicket+"</td>");
       	mailtext.append("</tr></tbody></table>");
       	
       	
       	//////// Set table header
       	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'>");
       	mailtext.append("<tbody><tr  bgcolor='#4A4747'>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket No.</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To Dept.</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Feed. By</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile No</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Open Date & Time</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>FIR</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></th>");
    	mailtext.append("</tr>");       	
       
       	//////// Insert value in table
       	for (Iterator iterator = report.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			mailtext.append("<tr  bgcolor='#FFFFFF'>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[1]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[6]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[3]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[4]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(object[11].toString()) +" "+ object[12] + "</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[10]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[13]  +"</td>");
        	mailtext.append("</tr>");
		}
       mailtext.append("</tbody></table><br><br>");
       mailtext.append("This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger ");
       mailtext.append("generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then ");
       mailtext.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html	");
       mailtext.append("or you may kindly mail your feedback to <a href='mailto:support@dreamsol.biz'> support@dreamsol.biz </a>");
       mailtext.append("</tbody></table>");
       	
       	//////////////////Set Ticket Status
        return mailtext.toString() ; 
	}
	
	private String getHodDailyReport(String name, String subDept, String subDeptName)
	{
		
		StringBuilder mailtext=new StringBuilder("");
		try{
		
		List hodReport = getDailyTicketReportForHOD(connectionSpace, subDept);
		
	
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody>");
	    
        mailtext.append("</tbody></table><table width='100%' align='center' style='border: 0'><tbody>");
       	mailtext.append("<tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody>");
	     
        mailtext.append("</table><b>Dear "+name+",</b><br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr>");
        mailtext.append("<td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Status of "+subDeptName+" On "+ DateUtil.getCurrentDateIndianFormat()+ " at "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table><br>");
        mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#4A4747'>");
        mailtext.append("<th colspan='2' align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>"+subDeptName+"</strong></th></tr>");
        mailtext.append("<tr  bgcolor='#696565'>");
       	mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Open</td>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Close</td></tr>");
        
        mailtext.append("<tr  bgcolor='#FFFFFF'>");
        mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ getCountTicketViaSubDept(connectionSpace, "Pending", String.valueOf(9))  +"</td>");
       	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ getCountTicketViaSubDept(connectionSpace, "Resolved", String.valueOf(9))  +"</td>");
	   	mailtext.append("</tr></tbody></table>");
	   	
	   	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'>");
       	mailtext.append("<tbody><tr  bgcolor='#4A4747'>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket No.</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Feed. By</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile No</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Open Date & Time</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>FIR</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></th>");
    	mailtext.append("</tr>");        	
       
       	//////// Insert value in table
    	for (Iterator iterator = hodReport.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			mailtext.append("<tr  bgcolor='#FFFFFF'>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[1]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[2]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[3]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(object[10].toString())+" "+ object[11] + "</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[9]  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ object[12]  +"</td>");
        	mailtext.append("</tr>");
		}
    	mailtext.append("</tbody></table><br><br>");
        mailtext.append("This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger ");
        mailtext.append("generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then ");
        mailtext.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html	");
        mailtext.append("or you may kindly mail your feedback to <a href='mailto:support@dreamsol.biz'> support@dreamsol.biz </a>");
        mailtext.append("</tbody></table>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	    return mailtext.toString();
	}
	
	public int getCountTicketViaSubDept(SessionFactory session,String status, String subDept)
	{
		int counter=0;

		StringBuffer buffer=new StringBuffer("select count(*) from feedback_status " +
				"where to_dept_subdept='"+subDept+"' " +
				"and open_date='"+DateUtil.getCurrentDateUSFormat()+"' and status='"+status+"'");
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List data=cbt.executeAllSelectQuery(buffer.toString(), session);
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					counter=Integer.parseInt(object.toString());
				}
			}
		}
		
		return counter;
	}
	
	public List getEmailIdListForReport(SessionFactory session, String reportLavel)
	{
		List data=null;
		try{
		String query = "select emp_name,emp_mob,emp_email,subject, report_date,report_time, id from report where report_level='"+reportLavel+"'";
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		data=cbt.executeAllSelectQuery(query.toString(), session);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private List getEmailIdListForHODReport(SessionFactory session, String reportLavel)
	{
		List data=null;
		try{
	//	String query = "select emp_name,emp_mob,emp_email,subject, subdept_dept, subdeptname,report_date,report_time,id  from report,subdepartment where report_level='"+reportLavel+"' and report.subdept_dept=subdepartment.id";
		String query="select reprt.emp_name,reprt.emp_mob,reprt.emp_email,reprt.subject,reprt.subdept_dept, subDept.subdeptname,reprt.report_date,reprt.report_time,reprt.id  from report as reprt inner join subdepartment as subDept on subDept.id=reprt.subdept_dept where reprt.report_level='"+reportLavel+"'";
	//	System.out.println("QUERRRRRRRRRRYYYYYYYYYYYYYYYYYY"+query);
		CommonOperInterface cbt=new CommonConFactory().createInterface();
	     data=cbt.executeAllSelectQuery(query.toString(), session);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public List getDailyTicketReport(SessionFactory session)
	{
		List list=null;
		try{
		String query = "select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by," +
				"feedback.feed_by_mobno,feedback.feed_by_emailid,subdept2.subdeptname as tosubdept," +
				"feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief," +
				"feedback.open_date,feedback.open_time,feedback.status " +
				"from feedback_status as feedback " +
				"inner join employee_basic emp on feedback.allot_to= emp.id " +
				"inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id " +
				"inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id " +
				"inner join department dept1 on subdept1.deptid= dept1.id " +
				"inner join department dept2 on subdept2.deptid= dept2.id " +
				"inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id " +
				"inner join feedback_category catg on subcatg.categoryName = catg.id " +
				"inner join feedback_type feedtype on catg.fbType = feedtype.id " +
				"where feedback.to_dept_subdept in (1,2,3,4,5,6,7,8) and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' " +
				"ORDER BY feedback.ticket_no DESC";
	//	System.out.println(">>>>>>>>>>Query  ticket Report:"+ query);
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		list = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private List getDailyTicketReportForHOD(SessionFactory session, String subDept)
	{
		List data=null;
		try{
		String query = "select feedback.id,feedback.ticket_no,feedback.feed_by," +
				"feedback.feed_by_mobno,feedback.feed_by_emailid,subdept2.subdeptname as tosubdept," +
				"feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief," +
				"feedback.open_date,feedback.open_time,feedback.status " +
				"from feedback_status as feedback " +
				"inner join employee_basic emp on feedback.allot_to= emp.id " +
				"inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id " +
				"inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id " +
				"inner join department dept1 on subdept1.deptid= dept1.id " +
				"inner join department dept2 on subdept2.deptid= dept2.id " +
				"inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id " +
				"inner join feedback_category catg on subcatg.categoryName = catg.id " +
				"inner join feedback_type feedtype on catg.fbType = feedtype.id " +
				"where feedback.to_dept_subdept in ("+subDept+") and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' " +
				"ORDER BY feedback.ticket_no DESC";
		
		//System.out.println(">>>>>>>>>>>>HOD Query : "+ query);
		CommonOperInterface cbt=new CommonConFactory().createInterface();
        data=cbt.executeAllSelectQuery(query.toString(), session);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private boolean updateDateByEmail(SessionFactory session, String date, String id) 
	{
		boolean mailFlag=false;
		try
		{
			String query = "update report set report_date='"+DateUtil.getNextDateAfter(1)+"' where id="+ Integer.parseInt(id);
		//	System.out.println(">>>>>>>>>>>Query : "+ query);
			CommonOperInterface cbt=new CommonConFactory().createInterface();
	        cbt.updateTableColomn(session,new StringBuilder(query));
	        mailFlag=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			mailFlag=false;
		}
		return mailFlag;
	}
	public static void main(String[] args) {
		new Thread(new Report("IN-1")).start();
		//new Report("IN-1").sendReport();
	}

}
