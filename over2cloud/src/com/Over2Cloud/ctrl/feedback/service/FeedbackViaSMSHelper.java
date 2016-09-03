package com.Over2Cloud.ctrl.feedback.service;

import org.hibernate.SessionFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;

public class FeedbackViaSMSHelper
{
	public SessionFactory getClientDBConnection(String clientId,String hostName,String userName,String password,String portNo)
	{
		AllConnection Conn1=new ConnectionFactory(clientId,hostName,userName,password,portNo);
        AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
        SessionFactory chunkSession=Ob.getSession();
        return chunkSession;
	}
	
	public String getCentreInfo(String centreCode)
	{
		String centerInfo=null;
		try
		{
			SessionFactory connection=getClientDBConnection("1_clouddb","localhost","dreamsol","dreamsol","3306");
			if(connection!=null)
			{
				StringBuffer buffer=new StringBuffer("select * from center_info where centreCode='"+centreCode+"'");
				List data=new createTable().executeAllSelectQuery(buffer.toString(), connection);
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null && object[4]!=null)
						{
							centerInfo=object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString()+"#"+object[3].toString()+"#"+object[4].toString();
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return centerInfo;
	}
	
	public void getFeedbackFromServer()
	{
		String mobNo="+919540202589";
		String msg="No";
		// URL 
		
		
		try
		{
			for(int i=0;i<10;i++)
			{
				if(mobNo!=null)
				{
					String msisdn=null;
					String clientInfo[]=null;
					String centreInfo[]=null;
					boolean valid=false;
					// checking mob no exists in customer info or not
					if(mobNo.startsWith("+91"))
					{
						msisdn=mobNo.substring(3);
						String clientDetails=getClientInfo(msisdn);
						clientInfo=clientDetails.split("#");
						if(clientInfo!=null)
						{
							String centreDetails=getCentreInfo(clientInfo[6]);
							if(centreDetails!=null)
							{
								centreInfo=centreDetails.split("#");
							}
						}
					}
					
					
					// checking Keyword Validity
					if(msg!=null)
					{
						valid=isValidKeyword(msg);
						if(valid && msg.equalsIgnoreCase("No"))
						{
							// Code to insert Data in Feedback Data table
							
							List <InsertDataTable> insertList=new ArrayList<InsertDataTable>();
							InsertDataTable ob4=null;
						                            
                            ob4=new InsertDataTable();
                            ob4.setColName("refNo");
                            ob4.setDataName(clientInfo[4]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("clientId");
                            ob4.setDataName(clientInfo[4]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("clientName");
                            ob4.setDataName(clientInfo[1]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("centerCode");
                            ob4.setDataName(centreInfo[1]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("centreName");
                            ob4.setDataName(centreInfo[2]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("mobNo");
                            ob4.setDataName(clientInfo[2]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("openDate");
                            ob4.setDataName(DateUtil.getCurrentDateUSFormat());
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("openTime");
                            ob4.setDataName(DateUtil.getCurrentTimeHourMin());
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("kword");
                            ob4.setDataName(msg);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("emailId");
                            ob4.setDataName(clientInfo[3]);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("targetOn");
                            ob4.setDataName(msg);
                            insertList.add(ob4);
                            
                            ob4=new InsertDataTable();
                            ob4.setColName("mode");
                            ob4.setDataName("SMS");
                            insertList.add(ob4);
                            
                            CommonOperInterface cbt=new CommonConFactory().createInterface();
                            
                            SessionFactory connection=getClientDBConnection("1_clouddb","localhost","dreamsol","dreamsol","3306");
                            
                            boolean status=cbt.insertIntoTable("feedbackdata", insertList, connection);
                            
                            // Going to open ticket for Level1
                            if(status)
                            {
                            	status=openTicket(clientInfo,centreInfo);
                            	
                            }
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean openTicket(String[] clientInfo,String[] centreInfo)
	{
		boolean ticketOpenFlag=false;
		SessionFactory connection=getClientDBConnection("1_clouddb","localhost","dreamsol","dreamsol","3306");
		if(connection!=null)
		{
			int id3=0;
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
			 
			 
	       /* Properties configProp=new Properties();
	        String subDeptName=null;
            // Note Change Selected ID Value
            try
            {
            	InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
    			configProp.load(in);
    			subDeptName=configProp.getProperty("admin");
            }
            catch (Exception e) {
            	e.printStackTrace();
			}
			int subCatId=0;
	        int patCareDeptId=0;
	       
	        if(subDeptName!=null)
	        {
	        	subCatId=new EscalationActionControlDao().getPatCareSubCatId(connection,subDeptName);
	         	patCareDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from subdepartment where subdeptname='"+subDeptName+"'");
	        }*/ 
	        
			 
			 Properties configProp=new Properties();
         	FeedbackViaTab tab=new FeedbackViaTab();
         	String adminDeptName=null;
     // Note Change Selected ID Value
         	try
         	{
         		InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
         		configProp.load(in);
         		adminDeptName=configProp.getProperty("admin");
         	}
         	catch (Exception e) {
         		e.printStackTrace();
         	}
         	
         	int subCatId=0;
            int adminDeptId=0;
            if(adminDeptName!=null)
            {
            	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from department where deptName='"+adminDeptName+"'");
            	subCatId=new EscalationActionControlDao().getAdminDeptSubCatId(connection,String.valueOf(adminDeptId));
            }
            if(adminDeptId!=0 && adminDeptId>0 && subCatId!=0)
            {
         //   	tab.openTicket(connection, String.valueOf(subCatId),"SMS",clientInfo[1],clientInfo[2],clientInfo[3], String.valueOf(adminDeptId), String.valueOf(adminDeptId));
            }
	        //tab.openTicket(connection, String.valueOf(subCatId),"SMS",clientInfo[1],clientInfo[2],clientInfo[3], "1", String.valueOf(patCareDeptId), "1", "1", "1", String.valueOf(patCareDeptId), clientInfo[5]);
	        
	        int id4=cbt.getMaxId("feedback_status_feedback", connection);
	        
	        String ticketNo=null;
			String openDate=null;
			String openTime=null;
			 StringBuilder queryNew=new StringBuilder("");
			 queryNew.append(" select * from feedback_status_feedback where id='"+id4+"'  ");
				List  dataNew=cbt.executeAllSelectQuery(queryNew.toString(),connection);
				for (Iterator iterator2 = dataNew.iterator(); iterator2.hasNext();)
				{
					Object[] object2 = (Object[]) iterator2.next();
					
					ticketNo=(String) object2[1].toString();
					openDate=(String) object2[13].toString();
					openTime=(String) object2[14].toString();
				}
				
				
				System.out.println("open time>>>>> "+ openTime);
				id3=cbt.getMaxId("feedback_status_feedback", connection);
				 List <InsertDataTable> insertHistory=new ArrayList<InsertDataTable>();
				 InsertDataTable ob4=null;
				 ob4=new InsertDataTable();
				 ob4.setColName("feedTicketNo");
				 ob4.setDataName(ticketNo);
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("feed_stat_id");
				 ob4.setDataName(id4);
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("feed_data_id");
				 ob4.setDataName(id3);
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("openDate");
				 ob4.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertHistory.add(ob4);
				 ob4=new InsertDataTable();
				 ob4.setColName("openTime");
				 ob4.setDataName(DateUtil.getCurrentTimewithSeconds());
				 ob4.setColName("userName");
				 ob4.setDataName("SMS");
				 insertHistory.add(ob4);
				 boolean added= cbt.insertIntoTable("feedback_ticket",insertHistory,connection);
				if(added)
				{
					 System.out.println("inserted in the feedback_ticket");
				}
		}
		return ticketOpenFlag;
	}
	
	
	public boolean isValidKeyword(String keyWord)
	{
		boolean valid=true;
		try
		{
			SessionFactory connection=getClientDBConnection("1_clouddb","localhost","dreamsol","dreamsol","3306");
			if(connection!=null)
			{
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return valid;
	}
	
	public String getClientInfo(String mobNo)
	{
		String clientInfo=null;
		try
		{ 
			SessionFactory connection=getClientDBConnection("1_clouddb","localhost","dreamsol","dreamsol","3306");
			if(connection!=null)
			{
				StringBuffer buffer=new StringBuffer("select * from customer_info where mobileNo='"+mobNo+"'");
				System.out.println("Buffer is as >>>>>>>>>>>>>>>>.."+buffer);
				List data=new createTable().executeAllSelectQuery(buffer.toString(), connection);
				String centreCode=null;
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							if(object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null && object[4]!=null && object[5]!=null)
							{
								clientInfo=object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString()+"#"+object[3].toString()+"#"+object[4].toString()+"#"+object[5].toString()+"#"+object[6].toString();
								centreCode=object[6].toString();
							}
						}
					}
				}
				System.out.println("Client Info is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+clientInfo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return clientInfo;
	}
}
