package com.Over2Cloud.ctrl.BugRepoting;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class BugReportingHelper {

	
	
	public String getOrgName(SessionFactory connectionSpace)
	{
		String cmpnyName="";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List dataCount2 = cbt.executeAllSelectQuery("select * from organization_level1", connectionSpace);
		for(Iterator it = dataCount2.iterator(); it.hasNext();)
		{
			Object[] obdata = (Object[]) it.next();
			cmpnyName=(String)obdata[2];
		}
		return cmpnyName;
	}
	
	public String getMailBodyForBugReportingToSupport(String userEmialId,String userMobNo,String module,String subModule,String feedback)
	{
		StringBuilder mailBody = new StringBuilder("");
		
		mailBody.append("<br><br><table width='100%' align='center' style='border: 0'><tr><td align='center' ><font color='#A9A9A9' size='4'><b>Over2Cloud</b></font></td></tr></table>");
		mailBody.append("<hr width='60%'>");
		mailBody.append("<br><table width='100%' align='center' style='border: 0'><tr><td align='center' ><font color='#A9A9A9' size='4'><b>FeedBack Lodge " +DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+
 				        "</b></font></td></tr></table>");
		mailBody.append("<hr width='60%'>");
		mailBody.append("<br><br>");
 		mailBody.append("<b>Hi,</b>");
 		mailBody.append("<br><center>Kindly find attached the following auto generated information: </center><br>");
 		mailBody.append("<br><br><table  border='2' cellpadding='0' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'");
 		mailBody.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Email ID:</font></td>" +
 				                               "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+userMobNo+ "</font></td></tr>");
 		mailBody.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Mobile No:</font></td>" +
                 "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+userEmialId+ "</font></td></tr>");

 		mailBody.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Module&nbsp;Name:</font></td>" +
                 "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+module+ "</font></td></tr>");
         
 		mailBody.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Sub Module Name:</font></td>" +
                 "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+subModule+ "</font></td></tr>");
 		

 		mailBody.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Feedback:</font></td>" +
                 "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+feedback+ "</font></td></tr>");
 		mailBody
			.append("</table><font face ='verdana' size='2'><br><br>Thanks !!!<br><br></FONT>");
 		mailBody
			.append("<br><br><font face ='TIMESROMAN' size='2'>Thank you for your valuable feedback our support team will get back to you shortly");
		
		
		
		return mailBody.toString();
	}
}
