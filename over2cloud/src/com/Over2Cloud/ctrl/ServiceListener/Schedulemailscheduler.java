package com.Over2Cloud.ctrl.ServiceListener;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;

public class Schedulemailscheduler implements Job{
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
	    CommonforClassdata obhdata = new CommonOperAtion();
		String mail_to = null;
		String writemail = null;
		String subject=null;
		String mail_id=null;
		Mailsender stubs = null;
        SessionFactory hsessionFactory=null;
        String portno=null;
		String serverName = null;
		String senderemailid= null;
		String password=null;
		String attachment=null;
		String date = null;
		String hours = null;
		String day = null;
		String sentdate = null;
		String senttime = null;
		String frequency="";
		String client_id=null;
		boolean mailstatus=false;
		try{
  	    stubs = new Mailsender();
  	    hsessionFactory=DBDynamicConnection.getSessionFactory();
		List smtpDetails = obhdata.executeAllSelectQuery("select * from smtp_detail",hsessionFactory);
		 if(smtpDetails!=null && smtpDetails.size()>0)
		   {
		  for (Iterator iterator = smtpDetails.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			    serverName=object[0].toString();
			    portno= object[1].toString();
			    senderemailid= object[2].toString();
			    password= object[3].toString();
		     }
			  
		  }
       List mailData=obhdata.executeAllSelectQuery("Select id,writemail,mail_to,subject,attachment,sent_date,sent_time,frequencys,date,day,hours from instantmail where status=0 and mailType='Schedule' and  date='"+DateUtil.getCurrentDateUSFormat()+" '  limit 1",hsessionFactory);
       if(mailData!=null)
		    {
		        for (Iterator iterator = mailData.iterator(); iterator.hasNext();) {
		        	 Object[] object = (Object[]) iterator.next();
		        	 if(object[0]!=null && !object[0].toString().equalsIgnoreCase("") ){
		        	 mail_id      = object[0].toString();
		        	 }
		        	 if(object[1]!=null && !object[1].toString().equalsIgnoreCase("") ){
		        	 writemail      =   object[1].toString();
		        	 }
		        	 mail_to    = object[2].toString();
		        	 subject =  object[3].toString();
		        	 if(object[4]!=null && !object[4].toString().equalsIgnoreCase("") ){
		        	 attachment=object[4].toString();
		        	 }
		        	 if(object[5]!=null){
		        	 sentdate   = object[5].toString();}
		        	 senttime   = object[6].toString();
		        	 if(object[7]!=null && !object[7].toString().equalsIgnoreCase("") ){
			         frequency = object[7].toString();
		        	 }
		        	 if(object[8]!=null && !object[8].toString().equalsIgnoreCase("") ){
			         date      = object[8].toString();}
		        	 if(object[9]!=null && !object[9].toString().equalsIgnoreCase("") ){
			         day       = object[9].toString();}
		        	 if(object[10]!=null && !object[10].toString().equalsIgnoreCase("") ){
			         hours     = object[10].toString();}
			         
		            
		            	  if(frequency.equalsIgnoreCase("One-Time"))
					       {
		            		 if(DateUtil.getCurrentDateUSFormat().equals(sentdate) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), senttime))
		            		  {
		            	           if(mail_id != null && writemail!= null && attachment!=null){
			    		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
			    			           }else{
			    			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
			    			           }
		            	    if (mailstatus){
		            	    	 obhdata.updateMail(mail_id,frequency,"instantmail",hsessionFactory);
			    		    	 System.out.println("Mail Send Successfully."); 
							  
			    		     }else{
							    System.out.println("There is some Problem to Send Mail."); 
							   }
					        }
					       }
		            	  if(frequency.equalsIgnoreCase("Daily"))
				           {
		            		  if(DateUtil.time_update(sentdate, senttime)){
		        	              if(mail_id != null && writemail!= null && attachment!=null){
		    		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
		    			           }else{
		    			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
		    			           }
			            	    if (mailstatus){
			            	    	 obhdata.updateMail(mail_id,frequency,"instantmail",hsessionFactory);
				    		    	 System.out.println("Mail Send Successfully."); 
								  
				    		     }else{
								    System.out.println("There is some Problem to Send Mail."); 
								   } 
		            		  }
				           }
		            	  if(frequency.equalsIgnoreCase("Weekly"))
					       {
		            		  if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours)){
		            		  
		            	           if(mail_id != null && writemail!= null && attachment!=null){
			    		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
			    			           }else{
			    			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
			    			           }
			            	    if (mailstatus){
			            	    	 obhdata.updateMail(mail_id,frequency,"instantmail",hsessionFactory);
				    		    	 System.out.println("Mail Send Successfully."); 
								  
				    		     }else{
								    System.out.println("There is some Problem to Send Mail."); 
								   }
		            		  }
					       }
		            	  if(frequency.equalsIgnoreCase("Monthly"))
					       {
		            		  if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours)){
		            		  
		            	           if(mail_id != null && writemail!= null && attachment!=null){
			    		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
			    			           }else{
			    			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
			    			           }
			            	    if (mailstatus){
			            	    	 obhdata.updateMail(mail_id,frequency,"instantmail",hsessionFactory);
				    		    	 System.out.println("Mail Send Successfully."); 
								  
				    		     }else{
								    System.out.println("There is some Problem to Send Mail."); 
								   }
		            		  }
					       }
		            	   if(frequency.equalsIgnoreCase("Yearly"))
					       {
		            		   if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours)){
		            		   
		            	           if(mail_id != null && writemail!= null && attachment!=null){
			    		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
			    			           }else{
			    			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
			    			           }
			            	    if (mailstatus){
			            	    	 obhdata.updateMail(mail_id,frequency,"instantmail",hsessionFactory);
				    		    	 System.out.println("Mail Send Successfully."); 
								  
				    		     }else{
								    System.out.println("There is some Problem to Send Mail."); 
								   }
		            		   }
					       }
			           }
	          
	          System.gc();
         }
      }catch (Exception e) {
  e.printStackTrace();
// TODO: handle exception
}
  System.gc();     
}

}
