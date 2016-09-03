package com.Over2Cloud.ctrl.feedback.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;
import com.Over2Cloud.ctrl.feedback.FeedbackAddViaTab;
import com.Over2Cloud.ctrl.feedback.OpenTicketForFeedbackModes;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.beanUtil.SMSKeywordPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class KeyWordRecvHelper
{
	
	private static Map<String,Object>  parameterClause = new HashMap<String, Object>();
	private static Map<String,Object> condtnBlock = new HashMap<String, Object>();

	private static List<SMSKeywordPojo> keyWordList=null;
	private static FeedbackPojo feedPojo=new FeedbackPojo();
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	public void getKeywords(SessionFactory connection)
	{
		try
		{

			// Getting All Keyword from t2mkeyword table
			keyWordList=getKeywordsRecv(connection);
			if(keyWordList!=null && keyWordList.size()>0)
			{
				// Checking the Module For KeyWords
				keyWordList=getModuleWiseKeyWord(keyWordList,connection);
				System.out.println("Total Keywords Received size is as <>>>>>>>>"+keyWordList.size());
				if(keyWordList!=null && keyWordList.size()>0)
				{
					for(SMSKeywordPojo smsPojo:keyWordList)
					{
						if(smsPojo.getMobNo()!=null && !smsPojo.getMobNo().equalsIgnoreCase(""))
						{
							feedPojo=getFullDetailsRecvedKeyWord(smsPojo.getMobNo(),smsPojo.getSatisfaction(),connection);
							
							if(createFeedTable(connection))
							{
								addDataOfFeedbackReceived(feedPojo,connection,smsPojo.getSatisfaction());
							}
						}
					}
				}
			}
			keyWordList.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean addDataOfFeedbackReceived(FeedbackPojo pojo,SessionFactory connection,String satisfaction)
	{
		boolean inserted=false;
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
        {
        	InsertDataTable ob=null;
        	ob=new InsertDataTable();
            ob.setColName("clientId");
            ob.setDataName(pojo.getCrNo());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("patientId");
            ob.setDataName(pojo.getPatId());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("clientName");
            ob.setDataName(pojo.getName());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("emailId");
            ob.setDataName(pojo.getEmailId());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("mobNo");
            ob.setDataName(pojo.getMobileNo());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("centerCode");
            ob.setDataName(pojo.getCentreCode());
            insertData.add(ob);
                
            
            ob=new InsertDataTable();
            ob.setColName("centreName");
            ob.setDataName(pojo.getSpeciality());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("compType");
            ob.setDataName(pojo.getPatType());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("openDate");
            ob.setDataName(DateUtil.getCurrentDateUSFormat());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("openTime");
            ob.setDataName(DateUtil.getCurrentTime());
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("status");
            ob.setDataName("Pending");
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("serviceBy");
            ob.setDataName(pojo.getDocterName());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("mode");
            ob.setDataName("SMS");
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("level");
            ob.setDataName("Level1");
            insertData.add(ob);
            
            
            
            if(satisfaction!=null && satisfaction.equalsIgnoreCase("1"))
                {
                	ob=new InsertDataTable();
                    ob.setColName("targetOn");
                    ob.setDataName("No");
                    insertData.add(ob);
                    
                    pojo.setSatisfaction("No");
                }
                else
                {
                	ob=new InsertDataTable();
                    ob.setColName("targetOn");
                    ob.setDataName("Yes");
                    insertData.add(ob);
                    pojo.setSatisfaction("Yes");
                }

                inserted=cbt.insertIntoTable("feedbackdata",insertData,connection); 
                if(satisfaction!=null && satisfaction.equalsIgnoreCase("1"))
                {
                	System.out.println("Opening Ticket");
                	String msg = "We apologies for the sad experience. Our team will get back to you shortly to have more details with you to improvise on same.";
                	
					new CommunicationHelper().addMessage("","Patient",pojo.getMobileNo(),msg,"","Pending","0","FM",connection);
                	openTicketsForNegFeedback(connection,pojo);
                }
                else
                {
                	String msg = "Thanks for your positive feedback. We wish you a healthy and happy life ahead.";
                	new CommunicationHelper().addMessage("","Patient",pojo.getMobileNo(),msg,"","Pending","0","FM",connection);
                	
                	// Added by Me for Pos SMS >>>
                	// 12-June-2014  Commented becoze no ticket open on positive feedback
                	openTicketsForNegFeedback(connection,pojo);
                }
        	}
        return inserted;
	}
	
	
	public  FeedbackPojo getFullDetailsRecvedKeyWord(String mobNo,String satisfaction,SessionFactory connection)
	{
		List patList=cbt.executeAllSelectQuery("select id,cr_number,patient_name,admit_consultant,mobile_no,email,station,patient_type,patientId,discharge_datetime from patientinfo where mobile_no ='"+ mobNo+"' and patient_type='IPD'  order by id desc limit 1 ",connection);
		if(patList!=null & patList.size()>0)
		{
			Object[] object=null;
			for (Iterator iterator = patList.iterator(); iterator.hasNext();)
			{
				object = (Object[]) iterator.next();
				if(object!=null)
				{
					if(object[0]!=null)
					{
						feedPojo.setId(Integer.parseInt(object[0].toString()));
					}
					
					if(object[1]!=null)
					{
						feedPojo.setCrNo(object[1].toString());
					}
					
					if(object[2]!=null)
					{
						feedPojo.setName(object[2].toString());
					}
					
					if(object[3]!=null)
					{
						feedPojo.setDocterName(object[3].toString());
					}
					
					if(object[4]!=null)
					{
						feedPojo.setMobileNo(object[4].toString());
					}
					
					if(object[5]!=null)
					{
						feedPojo.setEmailId(object[5].toString());
					}
					
					if(object[6]!=null)
					{
						feedPojo.setCentreCode(object[6].toString());
					}
					
					if(object[7]!=null)
					{
						feedPojo.setPatType(object[7].toString());
					}
					
					if(object[8]!=null)
					{
						feedPojo.setPatId(object[8].toString());
					}
					if(object[9]!=null)
					{
						feedPojo.setDischargeDateTime(object[9].toString());
					}
					
					if(satisfaction!=null && satisfaction.equalsIgnoreCase("0"))
					{
						parameterClause.clear();
						parameterClause.put("smsFlag", "7");
						condtnBlock.clear();
						condtnBlock.put("id",Integer.parseInt(object[0].toString()));
						boolean update = new HelpdeskUniversalHelper().updateTableColomn("patientinfo", parameterClause, condtnBlock,connection);
						System.out.println(" Yes Update :::  "+update);
						if(update)
						{
							return feedPojo;
						}
					}
					else if(satisfaction!=null && satisfaction.equalsIgnoreCase("1"))
					{
						parameterClause.clear();
						parameterClause.put("smsFlag", "5");
						condtnBlock.clear();
						condtnBlock.put("id",Integer.parseInt(object[0].toString()));
						boolean update = new HelpdeskUniversalHelper().updateTableColomn("patientinfo", parameterClause, condtnBlock,connection);
						System.out.println(" No Update :::  "+update);
						if(update)
						{
							return feedPojo;
						}
					}
				}
			}
		}
		else
		{
			feedPojo.setMobileNo(mobNo);
		}
		return feedPojo;
	}
	
	
	public boolean openTicketsForNegFeedback(SessionFactory connection,FeedbackPojo feedBackPojo)
	{
		boolean opened=false;
		
		try
		{
			Properties configProp=new Properties();
            String adminDeptName=null;
            InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
       	 	configProp.load(in);
       	 	adminDeptName=configProp.getProperty("admin");
       	 
       	 	int subCatId=0;
            int adminDeptId=0;
            if(adminDeptName!=null)
            {
            	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from department where deptName='"+adminDeptName+"'");
            	if(feedBackPojo.getSatisfaction().equalsIgnoreCase("No"))
            	{
            		subCatId=new EscalationActionControlDao().getAdminDeptSubCatId(connection,String.valueOf(adminDeptId));
            	}
            	else
            	{
            		subCatId=new EscalationActionControlDao().getAdminDeptSubCatIdPos(connection,String.valueOf(adminDeptId));
            	}
            }
            
            if(adminDeptId!=0 && adminDeptId>0 && subCatId!=0)
            {
           	 	OpenTicketForFeedbackModes tab=new OpenTicketForFeedbackModes();
           	 	int feedDataId=new createTable().getMaxId("feedbackdata", connection);
           	 	String ticketNo= tab.openTicket("SMS",String.valueOf(subCatId),connection,feedBackPojo.getName(),feedBackPojo.getMobileNo().trim(),feedBackPojo.getEmailId(),feedBackPojo.getCentreCode(),feedDataId,feedBackPojo.getSatisfaction(),feedBackPojo.getPatId(),feedBackPojo.getPatType(),feedBackPojo.getCrNo(),feedBackPojo.getPatId());
           		opened=new FeedbackAddViaTab().createFeedback_TicketTable(connection);
                if(opened)
                {
                	opened=new FeedbackAddViaTab().insertInFeedbackTicket(connection,feedDataId,ticketNo);
                }
            }
		}
		catch (Exception e) {
			e.printStackTrace();
			opened=false;
		}
		return opened;
	}
	
	public boolean createFeedTable(SessionFactory connection)
	{
		boolean tableCreated=false;
		List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_feedback_configuration","",connection,"",0,"table_name","feedback_data_configuration");
		if(statusColName!=null)
		{
			 	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	            boolean userTrue=false;
	            boolean dateTrue=false;
	            boolean timeTrue=false;
	            for(GridDataPropertyView gdp:statusColName)
	            {
	            	TableColumes ob2=new TableColumes();
	                ob2=new TableColumes();
	                ob2.setColumnname(gdp.getColomnName());
	                ob2.setDatatype("varchar(255)");
	                ob2.setConstraint("default NULL");
	                Tablecolumesaaa.add(ob2);
	                if(gdp.getColomnName().equalsIgnoreCase("userName"))
	                {
	                	userTrue=true;
	                }
	                else if(gdp.getColomnName().equalsIgnoreCase("openDate"))
	                {
	                	 dateTrue=true;
	                }
	                else if(gdp.getColomnName().equalsIgnoreCase("openTime"))
	                {
	                	timeTrue=true;
	                }
	            }
	            tableCreated=cbt.createTable22("feedbackdata",Tablecolumesaaa,connection);
		}
		return tableCreated;
	}
	
	public List<FeedbackPojo> getFullDetailsForRecvKeyword(SessionFactory connection,List<String> mobNo)
	{
		List<FeedbackPojo> feedbackPojo=new ArrayList<FeedbackPojo>();
		if(mobNo!=null && mobNo.size()>0)
		{
			StringBuilder buffer=new StringBuilder("select * from patientinfo where patientMobileNo in "+ mobNo.toString().replace("[", "(").replace("]",")") + "");
			List patList=cbt.executeAllSelectQuery(buffer.toString(),connection);
			if(patList!=null & patList.size()>0)
			{
				Object[] object=null;
				for (Iterator iterator = patList.iterator(); iterator.hasNext();)
				{
					FeedbackPojo pojo=new FeedbackPojo();
					object = (Object[]) iterator.next();
					if(object!=null)
					{
						if(object[0]!=null)
						{
							pojo.setId(Integer.parseInt(object[0].toString()));
						}
						
						if(object[1]!=null)
						{
							pojo.setPatId(object[1].toString());
						}
						
						if(object[2]!=null)
						{
							pojo.setName(object[2].toString());
						}
						
						if(object[3]!=null)
						{
							pojo.setDocterName(object[3].toString());
						}
						
						if(object[4]!=null)
						{
							pojo.setPurposeOfVisit(object[3].toString());
						}
						
						if(object[9]!=null)
						{
							pojo.setCentreCode(object[9].toString());
						}
						
						if(object[10]!=null)
						{
							pojo.setMobileNo(object[9].toString());
						}
						
						if(object[11]!=null)
						{
							pojo.setEmailId(object[9].toString());
						}
						feedbackPojo.add(pojo);
					}
				}
			}
		}
		return feedbackPojo;
	}
	
	
	public List<String> getAllMobNoFromKeywordList(SessionFactory connection,List<SMSKeywordPojo> keyWords)
	{
		List<String> mobList=new ArrayList<String>();
		for(SMSKeywordPojo key:keyWords)
		{
			mobList.add(key.getMobNo());
		}
		return mobList;
	}
	
	public List<SMSKeywordPojo> getModuleWiseKeyWord(List<SMSKeywordPojo> keyWords,SessionFactory connection)
	{
		List<SMSKeywordPojo> ticketKeyWords=new ArrayList<SMSKeywordPojo>();
		if(keyWords!=null && keyWords.size()>0)
		{
			for(SMSKeywordPojo p:keyWords)
			{
				StringBuilder buffer=new StringBuilder("select type from feedback_sms_config where keyword='"+p.getKeyWord()+"'");
				System.out.println("QUERRY"+buffer);
				List data=cbt.executeAllSelectQuery(buffer.toString(), connection);
				if(data!=null && data.size()>0)
				{
					Object object=null;
					for (Iterator iterator = data.iterator(); iterator .hasNext();)
					{
						object = (Object) iterator.next();
						if(object!=null)
						{
							p.setSatisfaction(object.toString());
							ticketKeyWords.add(p);
						}
					}
				}
			}
			keyWords.clear();
		}
		return ticketKeyWords;
	}
	
	
	public List<SMSKeywordPojo> getKeywordsRecv(SessionFactory connection)
	{
		List<SMSKeywordPojo> keyWordList=new ArrayList<SMSKeywordPojo>();
		try
		{

			StringBuilder buffer=new StringBuilder("select id,mobNo,keyword,date,time,status from t2mkeyword where status='0'");
			System.out.println("Querry is as >>>>  "+buffer);
			List data=cbt.executeAllSelectQuery(buffer.toString(), connection);
			if(data!=null & data.size()>0)
			{
				Object[] object =null;
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					object= (Object[]) iterator.next();
					if(object!=null)
					{
						SMSKeywordPojo pojo=new SMSKeywordPojo();
						
						if(object[0]!=null)
						{
							pojo.setId(Integer.parseInt(object[0].toString()));
						}
						if(object[1]!=null)
						{
							pojo.setMobNo(object[1].toString());
						}
						if(object[2]!=null)
						{
							pojo.setKeyWord(object[2].toString());
						}
						if(object[3]!=null)
						{
							pojo.setRecvDate(object[3].toString());
						}
						if(object[4]!=null)
						{
							pojo.setRecvTime(object[4].toString());
						}
						if(object[5]!=null)
						{
							pojo.setStatus(object[5].toString());
						}
						boolean updated= updateKeyRecvTable(connection,Integer.parseInt(object[0].toString()));
						System.out.println("Going to update The Keyword Column"+updated);
						if(updated)
						{
							keyWordList.add(pojo);
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return keyWordList;
	}
	public boolean updateKeyRecvTable(SessionFactory connection,int id)
	{
		Map<String,Object>  parameterClause = new HashMap<String, Object>();
		parameterClause.put("status", "3");
		parameterClause.put("updatedDate", DateUtil.getCurrentDateUSFormat());
		parameterClause.put("updatedTime", DateUtil.getCurrentTime());
			
		Map<String,Object> condtnBlock = new HashMap<String, Object>();
 		condtnBlock.put("id", id);
		boolean update = new HelpdeskUniversalHelper().updateTableColomn("t2mkeyword", parameterClause, condtnBlock,connection);
		return update;
	}
}