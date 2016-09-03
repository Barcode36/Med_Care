package com.Over2Cloud.InstantCommunication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import propertyfiles.ReadPropertyFile;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class InstantSMSSender implements Runnable{
	
	SessionFactory connection=null;
	HelpdeskUniversalHelper HUH=null;
	 String id = "",mobno = "",msg_text = "",module = "",date = "",time = "",updateQry="";
	 List listSMS=new ArrayList();
	
	private static CommonOperInterface cbt=new CommonConFactory().createInterface();

	HelpdeskServiceFileHelper HSFH = null;
	CommunicationHelper CH =null;
	public InstantSMSSender(final SessionFactory connection,final HelpdeskUniversalHelper HUHObj,final HelpdeskServiceFileHelper HF,final CommunicationHelper CHR)
	 {
		this.connection=connection;
		this.HUH=HUHObj;
		this.HSFH=HF;
		this.CH=CHR;
	 }
	
	@Override
	public void run() {
     try {
		  while (true)
		   {
			 try {
				// HSFH.escalateToNextLevel(connection,HUH,CH);
						 
			//	 HSFH.ReportGeneration(connection,HUH,CH);
					// Method calling for Snooze to Active
				 // HSFH.SnoozetoPending(connection,HUH,CH);
				
		    		listSMS.clear();
		    		String query="select id ,mobno ,msg_text,module,date,time from instant_msg where flag='0' and date<='"+DateUtil.getCurrentDateUSFormat()+"'";
		    		listSMS = HUH.getData(query,connection);
		    		if(listSMS!=null && listSMS.size()>0)
		    		{
		    			for (Iterator iterator = listSMS.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							id=object[0].toString();
							if (object[1]!=null && !object[1].toString().equals("")) {
								mobno=object[1].toString();
							}
							else {
								mobno="NA";
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								msg_text=object[2].toString();
							}
							else {
								msg_text="NA";
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								module=object[3].toString();
							}
							else {
								module="NA";
							}
							if (object[4]!=null && !object[4].toString().equals("")) {
								date=object[4].toString();
							}
							else {
								date="NA";
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								time=object[5].toString();
							}
							else {
								time="NA";
							}
							
						 if(date!=null && !date.equals("") && !date.equals("NA") && time!=null && !time.equals("") && !time.equals("NA")) 
						  {
							  boolean escnextlvl=DateUtil.time_update(date,time);
							  // If Escalating time passed away then go inside the If block
							   if(escnextlvl)
			           		      {
									    updateQry="update instant_msg set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
				                        boolean update = HUH.updateData(updateQry, connection);
								          if (update)
				                         {
											if (mobno!=null && !mobno.equals("") && !mobno.equals("NA") && msg_text!=null && !msg_text.equals("") && !msg_text.equals("NA")) {
												sendSMS(mobno, msg_text,module,id,connection,HUH);
											}
										 }
				                        
								 		
									 
					    		  }
						  }
		    		 }
		    		}
		    		
		    		
		    		//Schedule mail 
		    	/*	GenericMailSender GMS = new GenericMailSender();
		    		List listMail=new ArrayList();
		    	
		    		String query1="select id ,emailid ,subject ,mail_text ,attachment ,date ,time from instant_mail where flag='0' and date<='"+DateUtil.getCurrentDateUSFormat()+"'";
		    		listMail = HUH.getData(query1,connection);
		    		
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
							
							if (object[4]!=null && !object[4].toString().equals("")) {
								attachment=object[4].toString();
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								module=object[5].toString();
							}
							else {
								module="NA";
							}
							
							String updateQry="";
							System.out.println("Mail text "+mailtext);
							if (emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
								System.out.println("inside if");
								updateQry="update instant_mail set flag='3',status='Send',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								HUH.updateData(updateQry, connection);
							}
							else {
								System.out.println("inside if");
								updateQry="update instant_mail set flag='5',status='Error',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
								HUH.updateData(updateQry, connection);
							}
	                       
							if (true)
							{
								System.out.println("Sending Mail to :: "+emailid);
								if (emailid!=null &&!emailid.equals("") &&!emailid.equals("NA") && mailtext!=null &&!mailtext.equals("") &&!mailtext.equals("NA")) {
									if (emailid!=null && !emailid.equals("") && !emailid.equals("NA") && !subject.equals("") && !subject.equals("NA")) {
										GMS.sendMail(emailid, subject, mailtext, attachment,"smtp.gmail.com","587","o2chdm@gmail.com","dreamsol",id,connection);
										//  GMS.sendMail(emailid, subject, mailtext,attachment,"smtp.gmail.com", "465", "karnikag@dreamsol.biz", "karnikagupta");
											
										
										
									}
								}
							}
						}
		    		}
			       */
		    		
		    		
		    		
		    		Runtime rt = Runtime.getRuntime();
					rt.gc();
				    System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(5000);
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
	
	public String getSenderId(String module,SessionFactory connection)
	{
		String senderId=null;
		List dataList=cbt.executeAllSelectQuery("select distinct senderId from module_clientid_mapping where moduleName='"+module+"'", connection);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
		{
			return dataList.get(0).toString();
		}
		return senderId;
	}
	
	public String getClientId(String module,SessionFactory connection)
	{
		String clientId=null;
		List dataList=cbt.executeAllSelectQuery("select distinct clientId from module_clientid_mapping where moduleName='"+module+"'", connection);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
		{
			return dataList.get(0).toString();
		}
		return clientId;
	}
	
	
	
	// Method body for sending SMS @Khushal 24-01-2014
	@SuppressWarnings("deprecation")
	public void sendSMS(String msisdn,String fmsg,String module, String id,SessionFactory connection,HelpdeskUniversalHelper HUH)
	 {
		try {
			
		      String encodedMsg = URLEncoder.encode(fmsg);
		      if((msisdn != null &&  !msisdn.equals("")) && msisdn.length() == 10 && ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg!= null && !fmsg.equals("")))
		        {
					try {
						  String URL = null;
				          StringBuffer url = new StringBuffer();
				          URL="http://luna.a2wi.co.in:7501/failsafe/HttpLink?aid=572456&pin=med@1";
				          url.append(URL);
	   				      url.append("&mnumber=");
				          url.append(msisdn);
	   				      url.append("&message=");
	   				      url.append(encodedMsg);
	   				     System.out.println("url:::::::::"+url.toString());
						  URL codedURL = new URL(url.toString());
						 final String [] ip_prox= new ReadPropertyFile().getIpPort();
						  System.setProperty("http.proxyHost", ip_prox[0]);
						  System.setProperty("http.proxyPort", ip_prox[1]);
						  System.setProperty("http.proxyUser", ip_prox[2]);
						  System.setProperty("http.proxyPassword", ip_prox[3]);
						  
						  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
						  HURLC.connect();
						  int revertCode= HURLC.getResponseCode();
						  HURLC.disconnect();
						  System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+revertCode); 
   						  url = null; 
   						  if(revertCode==200)
   						  {
   							 updateQry="update instant_msg set flag='3',status='Sent',update_date='"+DateUtil.getCurrentDateUSFormat()+"',update_time='"+DateUtil.getCurrentTime()+"' where id='"+id+"'";
    						   HUH.updateData(updateQry, connection);
   						  }
						
	                        
					}
					catch (Exception E)
					  {
						E.printStackTrace();
						
					 }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String args[])
		{
		  try{
				final SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-4");
				final HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
			final HelpdeskServiceFileHelper HF=new HelpdeskServiceFileHelper();
			final CommunicationHelper CH= new CommunicationHelper();
				Thread uclient=new Thread(new InstantSMSSender(connection,HUH,HF,CH));
				uclient.start();
			 }
		  catch(Exception E){
				E.printStackTrace();
			}
		}
}