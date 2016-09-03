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


public class SchedulerJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		    int returncode = 0;
		    SchedulemsgSender stubs = null;
		    CommonforClassdata obhdata = new CommonOperAtion();
		    SessionFactory hsessionFactory=null;
		    Object[] object=null;
 			String msisdn = null;
 			String fmsg = null;
 			String date = null;
 			String hours = null;
 			String day = null;
 			String msgdate = null;
 			String msgtime = null;
 			String slno =null;
 			String frequency="";
 			String client_id=null;
		    try{
		      hsessionFactory=DBDynamicConnection.getSessionFactory();
		      String query="Select sms_id,msg,msisdn,msg_date,msg_time,frequencys,date,day,hours,client_id from msg_stats where status=0 and msgType='Schedule' and date='"+DateUtil.getCurrentDateUSFormat()+"' limit 2 ";
		      List msgData=obhdata.executeAllSelectQuery(query,hsessionFactory);
			  if(msgData!=null && msgData.size()>0)
	    		{
				    stubs = new SchedulemsgSender();
		 			
	    		for (Iterator iterator = msgData.iterator(); iterator
		                    .hasNext();) {
						object = (Object[]) iterator.next();
					}
	    		     if(object[0]!=null && !object[0].toString().equalsIgnoreCase("")){
	    		       slno      = object[0].toString();}
	    		     if(object[1]!=null && !object[1].toString().equalsIgnoreCase("")){
				       fmsg      = object[1].toString(); }
	    		     if(object[2]!=null && !object[2].toString().equalsIgnoreCase("")){
				       msisdn    = object[2].toString();}
	    		     if(object[3]!=null && !object[3].toString().equalsIgnoreCase("")){
				       msgdate   = object[3].toString();}
	    		     if(object[4]!=null && !object[4].toString().equalsIgnoreCase("")){
				       msgtime   = object[4].toString();}
	    		     if(object[5]!=null && !object[5].toString().equalsIgnoreCase("")){
				       frequency = object[5].toString();}
	    		     if(object[6]!=null && !object[6].toString().equalsIgnoreCase("")){
				       date      = object[6].toString();}
	    		     if(object[7]!=null && !object[7].toString().equalsIgnoreCase("")){
				       day       = object[7].toString();}
	    		     if(object[8]!=null && !object[8].toString().equalsIgnoreCase("")){
				       hours     = object[8].toString();}
	    		     if(object[9]!=null && !object[9].toString().equalsIgnoreCase("")){
				       client_id = object[9].toString();}
		 		  if(msisdn != null && fmsg!= null)
			       {
			        	 
				       if(frequency.equalsIgnoreCase("One-Time"))
				       {
				    	   if(DateUtil.getCurrentDateUSFormat().equals(msgdate) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), msgtime))
				    	   {
								 returncode= stubs.sendSMS(msisdn,fmsg,client_id,"f111","b111");
				    		     if (returncode==200){
				    		    	 
				    		    	 obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
								     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
								  
				    		     
				    		      }if (returncode==1201){
				    		    	  
				    		      obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
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
								 System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode); 
								    }
					            }
				    	   }
				          if(frequency.equalsIgnoreCase("Daily"))
				           {
				        	  
				    	   if(DateUtil.time_update(msgdate, msgtime))
				    	    {
				    		   returncode= stubs.sendSMS(msisdn,fmsg,client_id,"f111","b111");
				    		   if (returncode==200){
				    			   
				    			    obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
								     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
				    		     
				    		     }if (returncode==1201){
				    		    	 obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
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
								 System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode); 
								    }
				    	   }
				       }
				       if(frequency.equalsIgnoreCase("Weekly"))
				       {
				    		if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours))
				    	   {
				    		   returncode= stubs.sendSMS(msisdn,fmsg,client_id,"f111","b111");
				    		   if (returncode==200){
				    			    obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
								     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
				    		     
				    		     }if (returncode==1201){
				    		    	 obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
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
								 System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode); 
								    }
				    	
				    	   }
   				   }
				       if(frequency.equalsIgnoreCase("Monthly"))
				       {
				    	   if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours))
				    	   {
				    		   returncode= stubs.sendSMS(msisdn,fmsg,client_id,"f111","b111");
				    		   if (returncode==200){
				    			     obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
								     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
				    		     
				    		     }if (returncode==1201){
				    		      obhdata.executeAllupdateQuery("update schedule_msg set status=3 where id="+slno, hsessionFactory);
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
								 System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode); 
								    }
				    	   }
				       }
				       if(frequency.equalsIgnoreCase("Yearly"))
				       {
				    	   if(DateUtil.getCurrentDateUSFormat().equals(date) && DateUtil.time_update(DateUtil.getCurrentDateUSFormat(), hours))
				    	   {
				    		   returncode= stubs.sendSMS(msisdn,fmsg,client_id,"f111","b111");
				    		   if (returncode==200){
				    			   obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
								     System.out.println("SMS Send Successfully From First Route and Get Revert Code :"+returncode); 
				    		     
				    		     }if (returncode==1201){
				    		    	 obhdata.updateMsgStatus(slno,frequency,"msg_stats",hsessionFactory);
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
				    		    	 
								 System.out.println("SMS Not Send Successfully Because UNAUTHORIZED USER, ACCESS DENIED!!! and Get Revert Code :"+returncode); 
								  }
				    	     }
				         } 
			          }
	    		  }
			  System.gc();
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	
	}
}
		
