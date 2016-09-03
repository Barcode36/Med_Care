package com.Over2Cloud.ctrl.feedback.report;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackAcknowledgement
{
	private String ticketNo;
	private String feedId;
	private String orgName;
	private String negKey;
	private String virtualNo;
	private String adminDept;
	
	private String feedbackBy;
	private String rca;
	private String capa;
	private String brief;
	private String openDate;
	private String mode;
	
	
	private String smsFormat;
	private String mailFormat;
	private String mrdNo;
	
	
	private ActivityPojo pojo;
	
	
	private String activityFlag;
	
	public String getSMSDraftForAcknowledgement()
	{
		//Dear&nbsp;<s:property value="%{#parameters.feedbackBy}" />,&nbsp;we&nbsp;had&nbsp;taken&nbsp;action&nbsp;<b><s:property value="%{#parameters.capa}" /></b>&nbsp;for&nbsp;feedback&nbsp;<b><s:property value="%{#parameters.feedbrief}" /></b>&nbsp;registered&nbsp;on&nbsp;<s:property value="%{#parameters.opendate}" />.&nbsp;We&nbsp;thank&nbsp;you&nbsp;to&nbsp;help&nbsp;us&nbsp;improvise&nbsp;our&nbsp;service&nbsp;quality.
		StringBuilder buffer=new StringBuilder("Dear&nbsp;"+pojo.getClientName()+",&nbsp;we&nbsp;had&nbsp;taken&nbsp;action&nbsp;"+pojo.getCapa()+"&nbsp;for&nbsp;feedback&nbsp;"+pojo.getBrief()+"&nbsp;registered&nbsp;on&nbsp;"+pojo.getDateTime()+".&nbsp;We&nbsp;thank&nbsp;you&nbsp;to&nbsp;help&nbsp;us&nbsp;improvise&nbsp;our&nbsp;service&nbsp;quality.");
		return buffer.toString();
	}
	
	public String getMailBodyForAcknowledgement()
	{
		StringBuilder mailtext=new StringBuilder("");
		
		mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
		mailtext.append("<b>Dear "+pojo.getClientName()+",</b>");
		mailtext.append("<br><br><b>Greetings&nbsp;from&nbsp;"+DateUtil.makeTitle(orgName)+" !!!</b><br>");
		mailtext.append("Thanks for providing your valuable feedback.<br /><br />");
		mailtext.append("We would like to inform you that we had escalated your concerns to the respective internal department to investigate the same and ensure it does not happen again.<br/>" +
				" FYI, we would like to share our internal activities that had been taken:<br />");
		
		mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ pojo.getBrief()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Registered&nbsp;On:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ pojo.getDateTime()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Mode:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pojo.getMode()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pojo.getRca() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Preventive&nbsp;Activity:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ pojo.getCapa() + "</td></tr>");
        mailtext.append("</tbody></table>");
        mailtext.append("<table><tbody></tbody></table><br><b>Thanks & Regards,<br>Patient Delight Application Team<br></b>");
        mailtext.append("---------------------------------------------------------------------------------------------------------------------<br><font face ='TIMESROMAN' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	   
        return mailtext.toString();
	}
	
	
	public String getAckDataView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = new ActivityPojo();
				pojo = helper.getPatientDetails4Ack(getFeedId(),getActivityFlag(),connectionSpace);
				setSmsFormat(getSMSDraftForAcknowledgement());
				Properties configProp=new Properties();
				InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
				configProp.load(in);
				String adminDeptName=configProp.getProperty("admin");
				if(adminDeptName!=null)
				{
					setAdminDept(adminDeptName);
				}
				else
				{
					setAdminDept("");
				}
				
				String orgName=new FeedbackHelper().getOrgName(connectionSpace);
				if(orgName!=null)
				{
					setOrgName(orgName);
				}
				else
				{
					setOrgName("");
				}
				String negKeyword=new ReportsConfigurationDao().getKeyWord(connectionSpace,"FM","1");
				if(negKeyword!=null)
				{
					setNegKey(negKeyword);
				}
				else
				{
					setNegKey("");
				}
				
				String virtualNo=new ReportsConfigurationDao().getVirtualNo(connectionSpace,"FM");
				if(virtualNo!=null)
				{
					setVirtualNo(virtualNo);
				}
				else
				{
					setVirtualNo("");
				}
				setMailFormat(getMailBodyForAcknowledgement());
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String sendSMSMailToPatient()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				System.out.println("SMS is as "+getSmsFormat());
				System.out.println("Mail is as "+getMailFormat());
				return "success";
			}
			catch (Exception e) 
			{
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getNegKey() {
		return negKey;
	}
	public void setNegKey(String negKey) {
		this.negKey = negKey;
	}
	public String getVirtualNo() {
		return virtualNo;
	}
	public void setVirtualNo(String virtualNo) {
		this.virtualNo = virtualNo;
	}
	public String getAdminDept() {
		return adminDept;
	}
	public void setAdminDept(String adminDept) {
		this.adminDept = adminDept;
	}

	public String getFeedbackBy() {
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}

	public String getRca() {
		return rca;
	}

	public void setRca(String rca) {
		this.rca = rca;
	}

	public String getCapa() {
		return capa;
	}

	public void setCapa(String capa) {
		this.capa = capa;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getSmsFormat() {
		return smsFormat;
	}

	public void setSmsFormat(String smsFormat) {
		this.smsFormat = smsFormat;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMailFormat() {
		return mailFormat;
	}

	public void setMailFormat(String mailFormat) {
		this.mailFormat = mailFormat;
	}

	public String getMrdNo()
	{
		return mrdNo;
	}

	public void setMrdNo(String mrdNo)
	{
		this.mrdNo = mrdNo;
	}

	public String getActivityFlag()
	{
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}
}
