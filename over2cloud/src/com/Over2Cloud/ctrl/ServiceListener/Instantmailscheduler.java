package com.Over2Cloud.ctrl.ServiceListener;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;

public class Instantmailscheduler implements Job 
{
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException 
	{
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
		boolean mailstatus=false;
		try
		{
  	    
		stubs = new Mailsender();
  	    hsessionFactory=DBDynamicConnection.getSessionFactory();
  	    List smtpDetails = obhdata.executeAllSelectQuery("select * from smtp_detail",hsessionFactory);
	    if(smtpDetails !=null &&  smtpDetails.size()>0)
	    {
		    for (Iterator iterator = smtpDetails.iterator(); iterator.hasNext();) 
		    {
			    Object[] object = (Object[]) iterator.next();
			    serverName=object[0].toString();
			    portno= object[1].toString();
			    senderemailid= object[2].toString();
			    password= object[3].toString();
		     }
	    }
       List mailData=obhdata.executeAllSelectQuery("Select id,writemail,mail_to,subject,attachment from instantmail  where status=0 and mailType='Instant' limit 1",hsessionFactory);
      
       if( mailData!=null &&mailData.size()>0)
		    {
		             for (Iterator iterator = mailData.iterator(); iterator.hasNext();) {
		        	 Object[] object = (Object[]) iterator.next();
		        	 if(object[0]!=null && !object[0].toString().equalsIgnoreCase("") ){
		        	 mail_id      = object[0].toString();}
		          	 if(object[1]!=null && !object[1].toString().equalsIgnoreCase("") ){
		        	 writemail      =   object[1].toString();
		          	 }
		        	 mail_to    = object[2].toString();
		        	 subject =  object[3].toString();
		        	 if(object[4]!=null && !object[4].toString().equalsIgnoreCase("") ){
		        	 attachment=object[4].toString();
		        	 }
		              if(mail_id != null  &&  writemail != null  && attachment !=null ){
		            	  mailstatus= stubs.confMailwithAttachment(serverName, portno, senderemailid, password,mail_to , subject, writemail,attachment);
			           }else{
			        	   mailstatus= stubs.confMail(serverName, portno, senderemailid, password,mail_to , subject, writemail); 
			           }
	    		      if (mailstatus){
	    		    	 obhdata.executeAllupdateQuery("update instantmail  set status=3 where id="+mail_id,hsessionFactory );
	    		    	 System.out.println("Mail Send Successfully."); 
					  
	    		     }else{
					    System.out.println("There is some Problem to Send Mail."); 
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
