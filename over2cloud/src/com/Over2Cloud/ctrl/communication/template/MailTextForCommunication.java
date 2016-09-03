package com.Over2Cloud.ctrl.communication.template;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.Over2Cloud.CommonClasses.DateUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MailTextForCommunication 
{

	 
     
		public String getMailTexttt(String templated_id,String k,String date,String time,int maxid,String accountID,String existList11,String username,String emp_mailId) 
		{
			
			HttpServletRequest req = ServletActionContext.getRequest();
		     String ip;
		     StringBuilder mailtext = new StringBuilder("");
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
				int portNo=req.getServerPort();
				String URL="<a href=http://"+ip+":"+portNo+"/over2cloud/view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeTemplateView.action?username="+username+"&accountid="+accountID+">Click Here!!</a>";
				System.out.println("templated_id ghgh     " +templated_id+"template" + k+"date   "+date+"timennn" +time);
				System.out.println("url   "+URL);
				mailtext.append("<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
						+"<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Template ID :</font></td> "
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+templated_id+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Template:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+k+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Date:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+DateUtil.getCurrentDateIndianFormat()+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Time:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+DateUtil.getCurrentTime()+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Client ID:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+accountID+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Client name:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+existList11+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>User name:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+username+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>User Email_ID:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+emp_mailId+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Root:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;&nbsp;</font></td>"
						+"   </table>" );
				mailtext.append("<tr><td></td></tr>");
				mailtext.append("<tr><td>You are requested to take an appropriate action by clicking on below link: </td></tr>");
				mailtext.append(URL);
				System.out.println("sumitiii"+ mailtext   );
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mailtext.toString();
		     
			
		}
		
		public String getMailText(String temp_id,String templates,String tempp) 
		{
			
			
		     StringBuilder mailtext1 = new StringBuilder("");
			try {
				
				
				mailtext1.append("<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
						+"<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Template ID :</font></td> "
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+temp_id+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Current Template:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+templates+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Existing Template:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+tempp+"&nbsp;</font></td>"
						+"   </table>" );
				System.out.println("mailtext1" +mailtext1);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mailtext1.toString();
		     
			
		}

		
		public String getMailText2(String temp_id,String templates,String tempp,String operatorName,String operator_temp_ID,String accountID,String existList,String username, String emp_mailIdss) 
		{
		     StringBuilder mailtext2 = new StringBuilder("");
			try {
				
				
				mailtext2.append("<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
						+"<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Template ID :</font></td> "
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+temp_id+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Current Template:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+templates+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Exising Template:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+tempp+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Opreator Name</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+operatorName+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Operator Template ID:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+operator_temp_ID+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Client ID:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+accountID+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Client name:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+existList+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>User name:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+username+"&nbsp;</font></td>"
						+"</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>User Email_ID:</font></td>"
						+"<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"+emp_mailIdss+"&nbsp;</font></td>"
						+"   </table>" );
				System.out.println("mailtext1" +mailtext2);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mailtext2.toString();
		     
			
		}


}