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


public class InstantmsgScheduler implements Job {
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		    
		    CommonforClassdata obhdata = new CommonOperAtion();
			String msisdn = null;
			String msg = null;
			String clientid=null;
			String sms_id=null;
			InstantSMSsender stubs = null;
			int returncode = 0;
			String messageroot = null;
	        SessionFactory hsessionFactory=null;
          try{
      	   stubs = new InstantSMSsender();
      	   hsessionFactory=DBDynamicConnection.getSessionFactory();
	       List msgData=obhdata.executeAllSelectQuery("Select sms_id,msg,msisdn,client_id,messageroot from msg_stats where status=0 and msgType='Instant' limit 10",hsessionFactory);
	      
	       if(msgData!=null &&  msgData.size()>0)
  		    {
  		        for (Iterator iterator = msgData.iterator(); iterator.hasNext();) {
  		        	 Object[] object = (Object[]) iterator.next();
  		             sms_id      = object[0].toString();
  		             msg      =   object[1].toString();
			         msisdn    = object[2].toString();
			        clientid =  object[3].toString();
			        if(object[3]!=null){
			        	messageroot=object[4].toString();
			        }
			         if(msisdn != null && msg!= null)
				        {
			        	    returncode= stubs.sendSMS(msisdn,msg,clientid,"f111","b111");
				          }
			    	    
		    		     if (returncode==200){
		    		    	 obhdata.executeAllupdateQuery("update msg_stats set status=3 where sms_id="+sms_id,hsessionFactory );
						     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
						  
		    		     }else if (returncode==1201){
		    		      obhdata.executeAllupdateQuery("update msg_stats set status=3 where sms_id="+sms_id,hsessionFactory);
						  System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
						  
		    		     
		    		     }else if(returncode==1202){
						   System.out.println("SMS Not Send Successfully Because SMS PACKAGE ABOUT TO EXPIRE and Get Revert Code :"+returncode);	  
						 
		    		     
		    		     }else if(returncode==1203){
						  System.out.println("SMS Not Send Successfully Because SMS PACKAGE HAS EXPIRED and Get Revert Code :"+returncode);
						 
		    		     
		    		     }else if(returncode==1204){
						  System.out.println("SMS Not Send Successfully Because INVALID MOBILE NUMBER and Get Revert Code :"+returncode);
						 
		    		     
		    		     }else if(returncode==1205){
						  System.out.println("SMS Not Send Successfully Because VALIDITY PERIOD OF SMS PACKAGE HAS EXPIRED and Get Revert Code :"+returncode);
						 
		    		     }else{
		    		    	  obhdata.executeAllupdateQuery("update msg_stats set status=3 where sms_id="+sms_id,hsessionFactory);
						    System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode+">>>sms_id"+sms_id); 
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
