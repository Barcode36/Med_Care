package com.Over2Cloud.ctrl.BugRepoting;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BugRepotingAction extends ActionSupport{

	@SuppressWarnings("unchecked")
	private Map session = ActionContext.getContext().getSession();
	private String userEmialId;
	private String userMobNo;
	private String modulename;
	private String submodule;
	private String feedback123;
	private String emailID;
	private String password;
	private String server;
	private String port;
	private String cmpnyName;
	private String flag;
	private List<String> allModuleList;
	Map<String, String> appDetails = null;
	CommonWork cw=new CommonWork();
	public Map<String, String> getAppDetails() {
		return appDetails;
	}

	public void setAppDetails(Map<String, String> appDetails) {
		this.appDetails = appDetails;
	}

	SessionFactory connectionSpace = (SessionFactory) session .get("connectionSpace");
	String userName = (String) session.get("uName");
	String encryptedID=(String)session.get("encryptedID");
	
	private String uploadDocFileName;
	public File uploadDoc;
	
	private final 	MsgMailCommunication MMC= new MsgMailCommunication();
	
	public String reportABug()
	{
		boolean valid=ValidateSession.checkSession();
		
		if(valid)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String sql_query2 = " select employee_basic.mobOne, employee_basic.emailIdOne from employee_basic INNER JOIN useraccount on useraccount.id=employee_basic.useraccountid where useraccount.userID='"+encryptedID+"'";
				List listData2 = cbt.executeAllSelectQuery(sql_query2, connectionSpace);
				for (Iterator iterator = listData2.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					userMobNo=object[0].toString();
					userEmialId=object[1].toString();
					
				}
				List<String> sourcemasterColname = new ArrayList<String>();
				Map<String, Object> order=new HashMap<String, Object>();
				sourcemasterColname.add("rights_heading");
				order.put("rights_heading", "ASC");
				appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String bugRepoting()
	{
		System.out.println("Hello "+getModulename()+"::::::::::"+getSubmodule()+":::::::::::"+getFeedback123());
		
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				BugReportingHelper helper=new BugReportingHelper();
				cmpnyName=helper.getOrgName(connectionSpace);
				
				
				
				String sql_query2 = " select employee_basic.mobOne, employee_basic.emailIdOne from employee_basic INNER JOIN useraccount on useraccount.id=employee_basic.useraccountid where useraccount.userID='"+encryptedID+"'";
				List listData2 = cbt.executeAllSelectQuery(sql_query2, connectionSpace);
				for (Iterator iterator = listData2.iterator(); iterator .hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					userMobNo=object[0].toString();
					userEmialId=object[1].toString();
				}
				String renameFilePath=null;
				if(uploadDocFileName!=null)
				{
					   
					 renameFilePath = new CreateFolderOs().createUserDir("BugReport_Data")+ "//"+DateUtil.mergeDateTime()+getUploadDocFileName();
					 String storeFilePath = new CreateFolderOs().createUserDir("BugReport_Data")+ "//"+getUploadDocFileName();
					 String str=renameFilePath.replace("//", "/");
					 if(storeFilePath!=null)
					 {
						 File theFile = new File(storeFilePath);
						 File newFileName = new File(str);
						 if(theFile!=null)
						 {
							 try 
							 { 
								 FileUtils.copyFile(uploadDoc, theFile);
								 if(theFile.exists())
								 {
									theFile.renameTo(newFileName);
								 }
							 } 
							 catch (IOException e) 
							 {
								 e.printStackTrace();
							 }
							 
						 }
					 }
				 
				}
				
				
				
				String msg = "Feedback From: " +cmpnyName+", " +"By: " +userName+", " +"Mail: " +userEmialId+", " +"Mob: " +userMobNo+" " +"Feedback: " +feedback123;
				  
				String mailText=new BugReportingHelper().getMailBodyForBugReportingToSupport(userEmialId, userMobNo, getModulename(), getSubmodule(), getFeedback123());
				
				MMC.addMessage(userName,"Feedback", userMobNo, msg,"","Pending","0","Common");
				MMC.addMail(userName, "Feedback", userEmialId, "Over2cloud Feedback", mailText, "", "Pending", "0", renameFilePath,"Common");
				MMC.addMail(userName, "Feedback", "support@dreamsol.biz", "Over2cloud Feedback", mailText, "", "Pending", "0", renameFilePath,"Common");
				
				addActionMessage("Feedback Submitted Successfully !!! ");
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String seekAssistence()
	{

	  CommonOperInterface cbt = new CommonConFactory().createInterface();
	  StringBuilder query1 = new StringBuilder("");
	  query1.append("select*from email_configuration_data");
	  List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			connectionSpace);
	if(dataCount!=null && dataCount.size()>0)
	{
		//emailID=(String) dataCount.get(0);
		for (Iterator it = dataCount.iterator(); it.hasNext();)
		{
			Object[] obdata = (Object[]) it.next();
			emailID=(String) obdata[3];
			password=(String) obdata[4];
			server=(String) obdata[1];
			port=(String) obdata[2];
		}	
		
		
		
		try {
			  StringBuilder query2 = new StringBuilder("");
			  query2.append("select*from organization_level1");
			  List dataCount2 = cbt.executeAllSelectQuery(query2.toString(),
					connectionSpace);
			  for(Iterator it = dataCount2.iterator(); it.hasNext();)
			  {Object[] obdata = (Object[]) it.next();
			  cmpnyName=(String)obdata[2];
				  
			  }
			  StringBuilder query3 = new StringBuilder("");
			  query3.append(" select rights_heading from user_rights where id='"+getModulename()+"'");
			  List dataCount3 = cbt.executeAllSelectQuery(query3.toString(),
					connectionSpace);
			
			  
			Mailtest mt = new Mailtest();
			Mailtest mt2 = new Mailtest();
			StringBuilder mailtext = new StringBuilder("");
			StringBuilder mailtext2 = new StringBuilder("");
			String SMSClient =
				"Feedback From: "
				+cmpnyName+", "
				+"By: "
				+userName+", "
				+"Mail: "
				+userEmialId+", "
				+"Mob: "
				+userMobNo+" "
				+"Feedback: "
				+feedback123;

	        mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tr><td align='center' ><font color='#A9A9A9' size='4'><b>Over2cloud</b></font></td></tr></table>");
    		mailtext.append("<hr width='60%'>");
    		mailtext.append("<br><table width='100%' align='center' style='border: 0'><tr><td align='center' ><font color='#A9A9A9' size='4'><b>FeedBack Lodge " +DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime()+
    				        "</b></font></td></tr></table>");
    		mailtext.append("<hr width='60%'>");
    		mailtext.append("<br><br>");
    		mailtext.append("<b>Hi,</b>");
    		mailtext.append("<br><center>Kindly find attached the following auto generated information: </center><br>");
    		mailtext.append("<br><br><table  border='2' cellpadding='0' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'");
    		mailtext.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Email ID:</font></td>" +
    				                               "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+getUserEmialId()+ "</font></td></tr>");
    		mailtext.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Mobile No:</font></td>" +
                    "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+getUserMobNo()+ "</font></td></tr>");

    		mailtext.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Module&nbsp;Name:</font></td>" +
                    "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+dataCount3.get(0)+ "</font></td></tr>");
            
    		mailtext.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Sub Module Name:</font></td>" +
                    "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+getSubmodule()+ "</font></td></tr>");
    		

    		mailtext.append("<tr  bgcolor='#A9A9A9'><td  align='left' width='22%'><font style='font-weight:bold;color:#ffffff;'>Feedback:</font></td>" +
                    "<td  align='left' width='28%'><font style='font-weight:bold;color:#ffffff;'>&nbsp;&nbsp;"+getFeedback123()+ "</font></td></tr>");
    		mailtext
			.append("</table><font face ='verdana' size='2'><br><br>Thanks !!!<br><br></FONT>");
    		mailtext2
			.append("<br><br><font face ='TIMESROMAN' size='2'>Thank you for your valuable feedback our support team will get back to you shortly");
			
				// Mail Go for Client
				mt.confMailHTML(
								getServer(),
								getPort(),
								getEmailID(),
								getPassword(),
								"support@dreamsol.biz",
					          "Over2cloud Seek Assistence",
								mailtext.toString());
				
				mt2.confMailHTML(
						getServer(),
						getPort(),
						getEmailID(),
						getPassword(),
						getUserEmialId(),
			           "Seek Assistence",
						mailtext2.toString());
				// SMS On DS mobile NO
				new SendHttpSMS().sendSMS(userMobNo, SMSClient);
			
				
			
			// SMS Client
			
			addActionMessage("Seek Assistence Successfully");
			return SUCCESS;
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}}
	return SUCCESS;
		
	
		
	
	}
	
	public String getFeedback123() {
		return feedback123;
	}

	public void setFeedback123(String feedback123) {
		this.feedback123 = feedback123;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}


	public String getUserEmialId() {
		return userEmialId;
	}

	public void setUserEmialId(String userEmialId) {
		this.userEmialId = userEmialId;
	}

	public String getUserMobNo() {
		return userMobNo;
	}

	public void setUserMobNo(String userMobNo) {
		this.userMobNo = userMobNo;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getSubmodule() {
		return submodule;
	}
	public void setSubmodule(String submodule) {
		this.submodule = submodule;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getCmpnyName() {
		return cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<String> getAllModuleList() {
		return allModuleList;
	}

	public void setAllModuleList(List<String> allModuleList) {
		this.allModuleList = allModuleList;
	}

	public String getUploadDocFileName() {
		return uploadDocFileName;
	}

	public void setUploadDocFileName(String uploadDocFileName) {
		this.uploadDocFileName = uploadDocFileName;
	}

	public File getUploadDoc() {
		return uploadDoc;
	}

	public void setUploadDoc(File uploadDoc) {
		this.uploadDoc = uploadDoc;
	}
	
	
}