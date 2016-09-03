package com.Over2Cloud.InstantCommunication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class InstantMailSender implements Runnable
{
	String id = "";
	String emailid = "";
	String subject = "";
	String mailtext="";
	String attachment=null;
	String module = "";
	String server = "";
	String port = "java.lang.String";
	String from_emailid="";
	String password="";
	String cc_emailid = "";
	SessionFactory connection=null;
	
	public InstantMailSender(SessionFactory connection)
	{
		this.connection=connection;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
     try {
		  while (true)
		   {
			 try {
                    GenericMailSender GMS = new GenericMailSender();
		    		List listMail=new ArrayList();
		    		List colmName=new ArrayList();
		    		Map<String, Object> whereclause = new HashMap<String, Object>();
		    		whereclause.put("flag", "0");
		    		Map<String, Object> parameterClause = null;
		    		Map<String, Object> condtnBlock = null;
		    		
		    		colmName.add("id");
		    		colmName.add("emailid");
		    		colmName.add("subject");
		    		colmName.add("mail_text");
		    		colmName.add("attachment");
		    		colmName.add("module");
		    		colmName.add("cc_emailid");
		    		listMail=new HelpdeskUniversalHelper().getDataFromTable("instant_mail", colmName, whereclause, null, null, "", "", "", connection);
		    		
		    		System.out.println("Email sender");
		    		if(listMail!=null && listMail.size()>0)
		    		 {
		    			for (Iterator iterator = listMail.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null && !object[0].toString().equals("")) {
								id=object[0].toString();
							}
							if (object[1]!=null && !object[1].toString().equals("")) {
								emailid=object[1].toString();
							}
							else {
								emailid="NA";
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								subject=object[2].toString();
							}
							else {
								subject="NA";
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								mailtext=object[3].toString();
							}
							else {
								mailtext="NA";
							}
							
							if (object[4]!=null && !object[4].toString().equalsIgnoreCase("")) 
							{
								attachment="NA";
							}
							else
							{
								attachment=null;
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								module=object[5].toString();
							}
							else {
								module="NA";
							}
							if (object[6]!=null && !object[6].toString().equals("")) {
								cc_emailid=object[6].toString();
							}
							else {
								cc_emailid="NA";
							}
							parameterClause = new HashMap<String, Object>();
							if (emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
								parameterClause.put("flag", "3");
								parameterClause.put("status", "Send");
							}
							else {
								parameterClause.put("flag", "5");
								parameterClause.put("status", "Error");
							}
							parameterClause.put("update_date", DateUtil.getCurrentDateUSFormat());
							parameterClause.put("update_time", DateUtil.getCurrentTime());
							
				    		condtnBlock = new HashMap<String, Object>();
				    		condtnBlock.put("id", id);
							
							boolean update = new HelpdeskUniversalHelper().updateTableColomn("instant_mail", parameterClause, condtnBlock,connection);
							if (update) {
								if (module!=null && !module.equals("") && !module.equals("NA") && emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
									List mailSetting=new ArrayList();
						    		List columns=new ArrayList();
						    		Map<String, Object> conditionclause = new HashMap<String, Object>();
						    		conditionclause.put("moduleName", module);
						    		
						    		columns.add("server");
						    		columns.add("port");
						    		columns.add("emailid");
						    		columns.add("password");
						    		mailSetting=new HelpdeskUniversalHelper().getDataFromTable("email_configuration_data", columns, conditionclause, null, null, "", "", "", connection);
								    if (mailSetting!=null && mailSetting.size()>0) {
										for (Iterator iterator2 = mailSetting.iterator(); iterator2.hasNext();) {
											Object[] object2 = (Object[]) iterator2.next();
											if (object2[0]!=null && !object2[0].toString().equals("") && !object2[0].toString().equals("NA")) {
												server=object2[0].toString();
											}
											else {
												server="NA";
											}
											if (object2[1]!=null && !object2[1].toString().equals("") && !object2[1].toString().equals("NA")) {
												port=object2[1].toString();										
											}
											else {
												port="NA";
											}
											if (object2[2]!=null && !object2[2].toString().equals("") && !object2[2].toString().equals("NA")) {
												from_emailid=object2[2].toString();
											}
											else {
												from_emailid="NA";
											}
											if (object2[3]!=null && !object2[3].toString().equals("") && !object2[3].toString().equals("NA")) {
												password=object2[3].toString();
											}
											else {
												password="NA";
											}
										}
									}
								}
								if (emailid!=null && !emailid.equals("") && !emailid.equals("NA") && !subject.equals("") && !subject.equals("NA") && server!=null && !server.equals("") && !server.equals("NA") && port!=null && !port.equals("") && !port.equals("NA") && from_emailid!=null && !from_emailid.equals("") && !from_emailid.equals("NA") && password!=null && !password.equals("") && !password.equals("NA")) {
									if(!subject.equalsIgnoreCase("Appointment Booking"))
									{
									GMS.sendMail(emailid,cc_emailid, subject, mailtext, attachment, server, port, from_emailid, password);
									}
								}
							}
						}
		    		}
			       
			        Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(8000);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}		
	finally
	 {
		try {
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	}

	public static void main(String args[])
		{
			try{
				 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-4");
				System.out.println("Connectiopn is as "+connection);
				while(true){
					Thread uclient=new Thread(new InstantMailSender(connection));
					uclient.start();
					uclient.join();
					Thread.sleep(1000*60);
				}
			}catch(Exception E){
				E.printStackTrace();
			}
		}
}