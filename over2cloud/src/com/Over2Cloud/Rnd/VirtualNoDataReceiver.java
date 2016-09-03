package com.Over2Cloud.Rnd;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;

public class VirtualNoDataReceiver {

	public String[] getClientAccountData(String keyWord) {
		String AccountDetails = null;
		String[] Account = null;

		try {
			//Creating URL 
			System.out.println("Connecting to Server");
			  String url = "http://74.63.233.38:8080/VMMServer/virtualPoll?Keyword="+keyWord;
			  
			 
			if (url != null) {
				URL codedURL = new URL(url.toString());
				HttpURLConnection HURLC = (HttpURLConnection) codedURL
						.openConnection();
				HURLC.setRequestMethod("GET");
				HURLC.setDoOutput(true);
				HURLC.setDoInput(true);
				HURLC.setUseCaches(false);
				HURLC.connect();
				AccountDetails = HURLC.getHeaderField("1708");
				HURLC.disconnect();
				if (AccountDetails != null) {
					Account = AccountDetails.split("_");
				}
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
		return Account;
	}

	
	
	
	public String rescvSMS()
	{
		System.out.println("Before SMS Send<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		StringBuffer url =new StringBuffer("http://74.63.233.38:8080/VMMServer/virtualPoll?Keyword=schn");
		String revertMsg=null;
		if (url != null)
		   {
			try
			{
				System.out.println("Inside TRY<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				  URL codedURL = new URL(url.toString());
				  HttpURLConnection HURLC21 =(HttpURLConnection)codedURL.openConnection();
				  HURLC21.connect();
				  revertMsg=HURLC21.getResponseMessage();
				  HURLC21.disconnect();
			} 
			catch (Exception E)
			{
				E.printStackTrace();
			}
		   }
			return revertMsg;
	}
	
	public void getVirtualNoData()
	{
		try
		{
			String arr[]=getClientAccountData("schn");
			System.out.println("Array Is <>>>>>>    >>>>>>>>>>>>>>>>>>>>.");
			if(arr!=null && arr.length>0)
			{
				System.out.println("Array Not Null");
				if(arr[1]!=null)
				{
					String mobNo=arr[0];
					String msg=arr[1];
					System.out.println("Mobile  No is as <>>>>>>>>>>>>>>>>>>>>>>>>"+mobNo);
					System.out.println("Message is as <>>>>>>>>>>>>>>>>>>>>>>><><><>"+msg);
					
					if(mobNo!=null && msg!=null && msg.trim().equalsIgnoreCase("schn"))
					{
						System.out.println("Getting ConnectionFactory of the Client's Database>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						AllConnection Conn1=new ConnectionFactory("1_clouddb","localhost","dreamsol","dreamsol","3306");
				        AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
				        
				        SessionFactory chunkSession=Ob.getSession();
				        StringBuffer buffer=new StringBuffer("select distinct id,name,emailId from feedbackdata where mobileNo='"+mobNo+"'");
				        CommonOperInterface cbt=new CommonConFactory().createInterface();
				        
				        System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+buffer.toString());
				        
				        List dataList=cbt.executeAllSelectQuery(buffer.toString(), chunkSession);
				        int id3=0;
				        /*Properties configProp=new Properties();
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
	                    	subCatId=new EscalationActionControlDao().getPatCareSubCatId(chunkSession,subDeptName);
	                    	patCareDeptId=new EscalationActionControlDao().getAllSingleCounter(chunkSession,"select id from subdepartment where subdeptname='"+subDeptName+"'");
	                    }*/
	                    

						 Properties configProp=new Properties();
			         	FeedbackViaTab tab=new FeedbackViaTab();
			         	String adminDeptName=null;
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
			            	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(chunkSession,"select id from department where deptName='"+adminDeptName+"'");
			            	subCatId=new EscalationActionControlDao().getAdminDeptSubCatId(chunkSession,String.valueOf(adminDeptId));
			            }
			         		            
				        
	                    if(dataList!=null && adminDeptId!=0 && subCatId!=0)
				        {
				        	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				        	{

								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null && object[2]!=null )
								{
									id3=Integer.parseInt(object[0].toString());

									// Note Change Selected ID Value
							//		tab.openTicket(chunkSession, String.valueOf(subCatId),"SMS",object[1].toString(),mobNo,object[2].toString(), String.valueOf(adminDeptId), String.valueOf(adminDeptId));
								//	tab.openTicket(chunkSession, "4","SMS",,mobNo,, "1", "1", "1", "1", "1", "1", "Saket City");
									System.out.println("Ticket Opened");
									
									int id4=cbt.getMaxId("feedback_status", chunkSession);
									 
									String ticketNo=null;
									String openDate=null;
									String openTime=null;
									System.out.println("Value of id4 is as <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+id4);
									 StringBuilder queryNew=new StringBuilder("");
									 queryNew.append(" select * from feedback_status where id='"+id4+"'  ");
										List  dataNew=cbt.executeAllSelectQuery(queryNew.toString(),chunkSession);
										System.out.println("queryNew.toString() is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+queryNew.toString());
										System.out.println("Size of List is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>"+dataNew.size());
										for (Iterator iterator2 = dataNew.iterator(); iterator2.hasNext();)
										{
											Object[] object2 = (Object[]) iterator2.next();
											
											ticketNo=(String) object2[1].toString();
											openDate=(String) object2[13].toString();
											openTime=(String) object2[14].toString();
										}
										
										
										System.out.println("open time>>>>> "+ openTime);
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
										 cbt.insertIntoTable("feedback_ticket",insertHistory,chunkSession);
										 System.out.println("inserted in the feedback_ticket");
								}
							
							}
				        }
					}
				}
			}
			else
			{
				System.out.println("No Data Received<>>>>>>>>>>>>>>>>>>>>");
			}
		
			  Runtime rt = Runtime.getRuntime();
				rt.gc();
				System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				Thread.sleep(1000*5);
				System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String sddss[])
	{
		System.out.println("Connecting Virtual No...");
		
		
		System.out.println("Response MSG is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
