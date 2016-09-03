package com.Over2Cloud.ctrl.AllCommonModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommanOper;

import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendmailiandsmsAction extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	private int id=0;
	private String[] emailid=null;
	private String emailtwo=null;
	private String subject=null;
	private String mailbody=null;
	private String tags=null;
	boolean mailstatus=false;
	
	@SuppressWarnings("unchecked")
	public String sendmailandsms(){

		  System.out.println("tags>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+tags);
		  System.out.println("subject>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+subject);
		  System.out.println("mailbody>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+mailbody);
		 
		String returnResult = ERROR;
  	    CommonOperInterface cbt=new CommonConFactory().createInterface();
  		  String portno=null;
  		  String serverName = null;
  		  String senderemailid= null;
  		  String password=null;
	    		try{
	    		CommonforClass eventDao = new CommanOper();
	    		List<Smtp> smtpDetails = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
				   if(smtpDetails!=null && smtpDetails.size()>0)
				   {
				for (Smtp smtp : smtpDetails) {
					serverName=smtp.getServer();
					    portno=smtp.getPort();
					    senderemailid=smtp.getEmailid();
					    password=smtp.getPwd();
					    
				     }
				   }}catch (Exception e) {
					   e.printStackTrace();
					// TODO: handle exception
				}
	    		  Mailtest mailObjt=new Mailtest();
	    		  StringBuffer bufferobj=new StringBuffer(); 
	    		  bufferobj.append("<html>");
	    		  bufferobj.append("<body>");
	    		  bufferobj.append(mailbody);
	    		  bufferobj.append("</body>");
	    		  bufferobj.append("</html>");
	    		    if(tags!=null && !tags.equalsIgnoreCase("")){
	    				  emailid=tags.split(",");
	    			 for (int i = 0; i < emailid.length; i++) {
	    			   if(!emailid[i].equalsIgnoreCase(emailid[0])){
	    				 mailstatus= mailObjt.confMailHTML(serverName, portno, senderemailid, password,emailid[i].toString().trim() , subject, bufferobj.toString());
	    		        }
	    			 }}
				    if(mailstatus){
	    			  addActionMessage("Mail has been Sent");
	    		  }else{
	    			  addActionError("There is an Exception during Sending mial Plazz Call Mr.Pankaj");
	    		  }  
			 
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmailtwo() {
		return emailtwo;
	}

	public void setEmailtwo(String emailtwo) {
		this.emailtwo = emailtwo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailbody() {
		return mailbody;
	}

	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	
	
}
