package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.DailyReportExcelWrite4Attach;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class FeedbackReportHelper 
{
	
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	public String getPatientRevertDate(String mobNo,SessionFactory connection)
	{
		StringBuilder query=new StringBuilder("select date from t2mkeyword where mobNo='"+mobNo+"' ");
		List data=cbt.executeAllSelectQuery(query.toString(), connection);
		if(data!=null && data.size()>0)
		{
			return data.get(0).toString();
		}
		else
		{
			return "NA";
		}
	}
	
	public List<CustomerPojo> getFeedbackData(SessionFactory connection,String name)
	{
		StringBuilder query=new StringBuilder("select problem,clientName,centerCode,mobNo,emailId,centreName,remarks,level,targetOn from feedbackdata where openDate='"+DateUtil.getCurrentDateUSFormat()+"' ");
		List  data=new createTable().executeAllSelectQuery(query.toString(),connection);
        List<CustomerPojo> pojoData=getFeedbackDataAsPojo(data);
		return pojoData;
	}
	
	public String getMailBodyForAdminFeedback(SessionFactory connection,String name,List<CustomerPojo> feedList,int todayPos,int todayNeg,int total)
	{
		StringBuffer mailtext=new StringBuffer();
		
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody>");
	    
        mailtext.append("</tbody></table><table width='100%' align='center' style='border: 0'><tbody>");
       	mailtext.append("<tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr></tbody>");
        mailtext.append("</table><b>Dear "+name+",</b><br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr>");
        mailtext.append("<td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Details On "+ DateUtil.getCurrentDateIndianFormat()+ " at "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table><br>");

        mailtext.append("<td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Status of All Department On "+ DateUtil.getCurrentDateIndianFormat()+ " at "+DateUtil.getCurrentTimeHourMin()+"</b></td></tr></tbody></table><br>");
        
        mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#4A4747'>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Positive Ticket </b></td>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Negative Ticket </b></td>");
        mailtext.append("<td align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total </b>");
       	mailtext.append("<tr><td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+todayPos+"</td>");
       	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+todayNeg+"</td>");
       	mailtext.append("<td align='center'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+total+"</td>");
       	mailtext.append("</tr></tbody></table>");
       	
       	
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'>");
       	mailtext.append("<tbody><tr  bgcolor='#4A4747'>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Observation</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Room&nbsp;No</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile&nbsp;No</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Email&nbsp;Id</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Purpose&nbsp;Of&nbsp;Visit</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Level</strong></th>");
       	mailtext.append("<th align='center' style=' color:#FFFFFF; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Satisfaction</strong></th>");
    	mailtext.append("</tr>");        	
       	//////// Insert value in table
    	for (CustomerPojo c:feedList)
    	{
			mailtext.append("<tr  bgcolor='#FFFFFF'>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getProblem()+"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getClientName()+"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getCentreCode()+"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getMobNo()+ "</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getEmailId()+"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getCentreName()  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getRemarks()  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getLevel()  +"</td>");
           	mailtext.append("<td align='center' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ c.getFeedback()  +"</td>");
        	mailtext.append("</tr>");
		}
    	mailtext.append("</tbody></table><br><br>");
        mailtext.append("This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger ");
        mailtext.append("generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then ");
        mailtext.append("please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html	");
        mailtext.append("or you may kindly mail your feedback to <a href='mailto:support@dreamsol.biz'> support@dreamsol.biz </a>");
        mailtext.append("</tbody></table>");
        	return mailtext.toString();
	}
	
	public List<CustomerPojo> getFeedbackDataAsPojo(List data)
	{
		List<CustomerPojo> list=new ArrayList<CustomerPojo>();
		try
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				if(object!=null)
				{
					CustomerPojo pojo=new CustomerPojo();
					if(object[0]!=null)
					{
						pojo.setProblem(object[0].toString());
					}
					else
					{
						pojo.setProblem("NA");
					}
					
					if(object[1]!=null)
					{
						pojo.setClientName(object[1].toString());
					}
					else
					{
						pojo.setClientName("NA");
					}
					
					if(object[2]!=null)
					{
						pojo.setCentreCode(object[2].toString());
					}
					else
					{
						pojo.setCentreCode("NA");
					}

					if(object[3]!=null)
					{
						pojo.setMobNo(object[3].toString());
					}
					else
					{
						pojo.setMobNo("NA");
					}
					
					if(object[4]!=null)
					{
						pojo.setEmailId(object[4].toString());
					}
					else
					{
						pojo.setEmailId("NA");
					}
					
					if(object[5]!=null)
					{
						pojo.setCentreName(object[5].toString());
					}
					else
					{
						pojo.setCentreName("NA");
					}
					
					if(object[6]!=null)
					{
						pojo.setRemarks(object[6].toString());
					}
					else
					{
						pojo.setRemarks("NA");
					}
					
					if(object[7]!=null)
					{
						pojo.setLevel(object[7].toString());
					}
					else
					{
						pojo.setLevel("NA");
					}
					
					if(object[8]!=null)
					{
						pojo.setFeedback(object[8].toString());
					}
					else
					{
						pojo.setFeedback("NA");
					}
					
					list.add(pojo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getPositiveDataCount(SessionFactory connection)
	{
		int count=0;
		StringBuffer buffer=new StringBuffer("select count(*) from feedbackdata where targetOn='Yes' and openDate='"+DateUtil.getCurrentDateUSFormat()+"'");
		count=getAllSingleCountersByQuerry(buffer.toString(), connection);
		return count;
	}
	
	public int getNegativeDataCount(SessionFactory connection)
	{
		int count=0;
		StringBuffer buffer=new StringBuffer("select count(*) from feedbackdata where targetOn='No' and openDate='"+DateUtil.getCurrentDateUSFormat()+"'");
		count=getAllSingleCountersByQuerry(buffer.toString(), connection);
		return count;
	}
	
	public String getFeedbackCounter(SessionFactory connection,String clientId)
	{
		int count=0;
		StringBuffer buffer=new StringBuffer("select count(*) from feedbackdata where clientId='"+clientId+"'");
		count=getAllSingleCountersByQuerry(buffer.toString(), connection);
		return String.valueOf(count);
	}
	
	public int getAllSingleCountersByQuerry(String query,SessionFactory connection)
	{
		int count=0;
		try
		{
			List data=new createTable().executeAllSelectQuery(query, connection);
			if(data!=null && data.size()>0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if(object!=null)
					{
						count=Integer.parseInt(object.toString());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
}