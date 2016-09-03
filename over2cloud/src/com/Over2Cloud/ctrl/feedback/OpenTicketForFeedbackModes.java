package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.worktimeing.WorkingTimeHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.feedback.service.FeedbackViaTab;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class OpenTicketForFeedbackModes 
{
	
	String  escalation_date = "NA",escalation_time = "NA",resolution_date = "NA",
	resolution_time = "NA",level_resolution_date = "NA", level_resolution_time = "NA",non_working_timing="00:00",Address_Date_Time = "NA";
	private final CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	public String getAdminDeptName()
	{

		String deptName=null;
		 try
         {
			 Properties configProp=new Properties();
        	 InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
        	 configProp.load(in);
        	 deptName=configProp.getProperty("admin");
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
		return deptName;
	}
	public String getAdminDeptId(SessionFactory connection)
	{
		String deptId=null;
		 try
         {
			 Properties configProp=new Properties();
			 String adminDeptName=null;
        	 InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
        	 configProp.load(in);
        	 adminDeptName=configProp.getProperty("admin");
        	 
             int adminDeptId=0;
             if(adminDeptName!=null)
             {
             	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from department where deptName='"+adminDeptName+"'");
             	if(adminDeptId!=0)
             	{
             		deptId=String.valueOf(adminDeptId);
             	}
             }
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
		return deptId;
	}
	
	public int getAdminDeptSubCatId(SessionFactory connection)
	{
		int subCatId=0;
		 try
         {
			 Properties configProp=new Properties();
			 String adminDeptName=null;
        	 InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
        	 configProp.load(in);
        	 adminDeptName=configProp.getProperty("admin");
        	 
             int adminDeptId=0;
             if(adminDeptName!=null)
             {
             	adminDeptId=new EscalationActionControlDao().getAllSingleCounter(connection,"select id from department where deptName='"+adminDeptName+"'");
             	subCatId=new EscalationActionControlDao().getAdminDeptSubCatId(connection,String.valueOf(adminDeptId));
             }
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
		return subCatId;
	}
	
	public boolean createFeedbackStatusFeedbackTable(SessionFactory connectionSpace)
	{
		boolean created=false;
		try
		{
			CommonOperInterface cot = new CommonConFactory().createInterface();
			List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
			if (colName!=null && colName.size()>0)
			{
				@SuppressWarnings("unused")
				boolean status= false;
				List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
				for (GridDataPropertyView tableColumes : colName)
				{
					if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime") ) 
					{
						if(tableColumes.getColomnName().equalsIgnoreCase("feedback_remarks"))
						{
							TableColumes ob1 = new TableColumes();
							ob1.setColumnname(tableColumes.getColomnName());
							ob1.setDatatype("text");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
						else
						{
							TableColumes ob1 = new TableColumes();
							ob1.setColumnname(tableColumes.getColomnName());
							ob1.setDatatype("varchar("+tableColumes.getWidth()+")");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
					}
				}
				
				created=cot.createTable22("feedback_status",TableColumnName,connectionSpace);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return created;
	}
	
	public List getTicketNumber(SessionFactory connectionSpace,String str)
	{
		 List  ticketno = new ArrayList();
		 try 
			{
				String ticket_no="select feedTicketNo from feedback_ticket  where id=(select max(id) from feedback_ticket where feedTicketNo like '"+str+"%')";
				ticketno=new createTable().executeAllSelectQuery(ticket_no,connectionSpace);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return ticketno;
	}
	
	public String getLatestTicketNoByMode(SessionFactory connectionSpace,String mode)
	{
		String ticketNo=null;
		int ticket = 0;
		if(mode!=null && mode.equalsIgnoreCase("SMS"))
		{
			List fs = getTicketNumber(connectionSpace,"S");
			if(fs.size()>0)
			{
				if(fs.size()>0)
				{
	                for (Iterator iterator = fs.iterator(); iterator.hasNext();) 
	                {
						Object object = (Object) iterator.next();
						ticket = Integer.parseInt(object.toString().substring(1, object.toString().length()))+1;
	                }
				}
				ticketNo="S"+ticket;
       	 	}
       	 	else
       	 	{
       	 		ticketNo="S"+1000;
       	 	}
		}
		else if(mode!=null && mode.equalsIgnoreCase("Online"))
        {
        	 List fs = getTicketNumber(connectionSpace,"O");
        	 if(fs!=null && fs.size()>0)
        	 {
        		 if(fs.size()>0)
        		 {
 	                for (Iterator iterator = fs.iterator(); iterator.hasNext();) 
 	                {
 						Object object = (Object) iterator.next();
 						ticket = Integer.parseInt(object.toString().substring(1, object.toString().length()))+1;
 					}
        		 }
        		 ticketNo="O"+ticket;
        	 }
        	 else
        	 {
        		 ticketNo="O"+1000;
        	 }
        	
        }
        else if(mode!=null && mode.equalsIgnoreCase("Mob Tab"))
        {
        	 List fs = getTicketNumber(connectionSpace,"T");
        	 if(fs!=null && fs.size()>0)
        	 {
        		 if(fs.size()>0)
        		 {
 	                for (Iterator iterator = fs.iterator(); iterator.hasNext();) 
 	                {
 						Object object = (Object) iterator.next();
 						ticket = Integer.parseInt(object.toString().substring(1, object.toString().length()))+1;
 					}
        		 }
        		 ticketNo="T"+ticket;
        	 }
        	 else
        	 {
        		 ticketNo="T"+1000;
        	 }
        	
        }
        else if(mode!=null && mode.equalsIgnoreCase("OBD"))
        {
        	 List fs = getTicketNumber(connectionSpace,"V");
        	 if(fs!=null && fs.size()>0)
        	 {
        		 if(fs.size()>0)
        		 {
 	                for (Iterator iterator = fs.iterator(); iterator.hasNext();) 
 	                {
 						Object object = (Object) iterator.next();
 						ticket = Integer.parseInt(object.toString().substring(1, object.toString().length()))+1;
 					}
        		 }
        		 ticketNo="V"+ticket;
        	 }
        	 else
        	 {
        		 ticketNo="V"+1000;
        	 }
        	
        }
        else if(mode!=null && mode.equalsIgnoreCase("Paper"))
        {
        	 List fs = getTicketNumber(connectionSpace,"P");
        	 if(fs!=null && fs.size()>0)
        	 {
        		 if(fs.size()>0)
        		 {
 	                for (Iterator iterator = fs.iterator(); iterator.hasNext();) 
 	                {
 						Object object = (Object) iterator.next();
 						ticket = Integer.parseInt(object.toString().substring(1, object.toString().length()))+1;
 					}
        		 }
        		 ticketNo="P"+ticket;
        	 }
        	 else
        	 {
        		 ticketNo="P"+1000;
        	 }
        	
        }
		return ticketNo;
	}
	
	public List getAllData(String table, String searchfield, String searchfieldvalue, String orderfield, String order,SessionFactory chunkSession)
	 {
		 List  subdeptallList = new ArrayList();
		 try 
		 {
				String subdeptall="select * from "+table+" where "+searchfield+"='"+searchfieldvalue+"' ORDER BY "+orderfield+" "+order+"";
				subdeptallList=new createTable().executeAllSelectQuery(subdeptall,chunkSession);
		 }
		 catch (Exception e)
		 {
				e.printStackTrace();
		 }
		 return subdeptallList;
		 
	 }
	
	public String openTicket(String mode,String subCategory,SessionFactory connectionSpace,String feedbackBy,String mobileNo,String emailId,String location,int feedDataId,String satisfaction,String patId,String patType,String crNo,String patientId)
	{
		boolean tableCreated=createFeedbackStatusFeedbackTable(connectionSpace);
		String ticketNo=null;
		System.out.println("tableCreated is as <><><<><>"+location);
		if(tableCreated)
		{
			try
			{
				String deptLevel="2";
				CommonOperInterface cot = new CommonConFactory().createInterface();
				ticketNo=getLatestTicketNoByMode(connectionSpace,mode);
				String res_time=null;
				String esc_time=null,escalation_date=null,escalation_time=null;
				String allottoId=null;
				String feed_brief=null;

				String toDept=getAdminDeptId(connectionSpace);
   				String byDept=getAdminDeptId(connectionSpace);
   				
   				FeedbackUniversalHelper FUH=new FeedbackUniversalHelper();
   			//	FeedbackUniversalAction FUA=new FeedbackUniversalAction();
   				
   				
   				
   				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
   				if(colName!=null && colName.size()>0)
   				{
   					boolean status= false;
   		        	List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
   		        	for (GridDataPropertyView tableColumes : colName)
   		        	{
   		        		if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime") ) 
   		        		{
   		        			if(tableColumes.getColomnName().equalsIgnoreCase("feedback_remarks"))
   		        			{
   		        				TableColumes ob1 = new TableColumes();
   		        				ob1.setColumnname(tableColumes.getColomnName());
   		        				ob1.setDatatype("text");
   		        				ob1.setConstraint("default NULL");
   		        				TableColumnName.add(ob1);
   		        			}
   		        			else
   		        			{
   		        				TableColumes ob1 = new TableColumes();
   		        				ob1.setColumnname(tableColumes.getColomnName());
   		        				ob1.setDatatype("varchar("+tableColumes.getWidth()+")");
   		        				ob1.setConstraint("default NULL");
   		        				TableColumnName.add(ob1);
   		        			}
   		        		}
   		        	}
   		        	cot.createTable22("feedback_status",TableColumnName,connectionSpace);
   				}
   				
                String adrr_time="",tolevel="",needesc="";
   				List subCategoryList = FUH.getAllData("feedback_subcategory", "id",subCategory, "subCategoryName", "ASC",connectionSpace);
                if (subCategoryList != null && subCategoryList.size() > 0) 
                {
                	for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();) 
                	{
                	Object[] objectCol = (Object[]) iterator.next();
                	if (objectCol[3] == null)
                	{
                	feed_brief = "NA";
                	}
                	else
                	{
                	feed_brief = objectCol[3].toString();
                	}
                	if (objectCol[4] == null)
                	{
                		adrr_time = "06:00";
                	}
                	else 
                	{
                		adrr_time = objectCol[4].toString();
                	}
                	if (objectCol[5] == null)
                	{
                		res_time = "10:00";
                	}
                	else
                	{
                		res_time = objectCol[5].toString();
                	}
	
                	if (objectCol[8] == null) 
                	{
                		tolevel = "Level1";
                	}
                	else 
                	{
                		tolevel = objectCol[8].toString();
                	}
                	if (objectCol[9] == null)
                	{
                		needesc = "Y";
                	}
                	else
                	{
                		needesc = objectCol[9].toString();
                	}
                	}
                }
				
                WorkingHourAction WHA=new WorkingHourAction();
				String date=DateUtil.getCurrentDateUSFormat();
				String time=DateUtil.getCurrentTimeHourMin();
                if(needesc != null && !needesc.equals("") && needesc.equals("Y"))
                {
                	List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
					Address_Date_Time = dateTime.get(0)+" "+dateTime.get(1);
					escalation_date = dateTime.get(2);
					escalation_time = dateTime.get(3);
					System.out.println(dateTime.get(0)+" "+dateTime.get(1)+" "+dateTime.get(2)+" "+dateTime.get(3));
                }
                else
                {
                	escalation_date="NA";
                	escalation_time="NA";
                }
                
                
		    	List allotto = null;
	      	    List allotto1 = null;
	      	    List<String> one = new ArrayList<String>();
	      	    List<String> two = new ArrayList<String>();
	  	   	        
	  	   	   	List<String> two_new = new ArrayList<String>(); 
	  	   	   
				if(toDept!=null)
				{
    	  	   	    try 
    	  	   	    {
    	  	   	    	
    	  	   	    	boolean bedMapping=FUH.checkTable("bed_mapping",connectionSpace);
    	  	   	    	if (bedMapping && toDept!=null && !toDept.equals(""))
    	  	   	    	{
    	  	   	    		String floor_status= FUH.getFloorStatus(toDept,connectionSpace);
    	  	   	    //		System.out.println(">>>>>>>>>"+floor_status);
    	  		    		if(floor_status!=null && !floor_status.equalsIgnoreCase("") && floor_status.equalsIgnoreCase("B") && location!=null && !location.equalsIgnoreCase(""))
    	  		    		{
    	  		    			allotto = FUH.getRandomEmp4BedSpecific(toDept,location,tolevel.substring(5),connectionSpace);
    	  		  	   		}
    	  		    		else
    	  		    		{
    	  		    			allotto = FUH.getRandomEmp4Escalation(toDept,"FM",tolevel.substring(5),"","",connectionSpace);
    	  		    		}
    	  		    	}
	    				else
	    				{
	    					allotto = FUH.getRandomEmp4Escalation(toDept,"FM",tolevel.substring(5),"","",connectionSpace);
	    				}

    	  	   	    	if (allotto==null || allotto.size()==0)
    	  	   	    	{
    	  	   	    		String newToLevel= "";
    	  	   	    		newToLevel = ""+(Integer.parseInt(tolevel.substring(5))+1);
    	  	   	    		allotto = FUH.getRandomEmp4Escalation(toDept,"FM",newToLevel,"","",connectionSpace);
    	  	   	    		if (allotto==null || allotto.size()==0)
    	  	   	    		{
    	  	   	    			newToLevel = ""+(Integer.parseInt(newToLevel)+1);
    	  //  System.out.println("New To Level inside Second If  "+newToLevel);
    	  	   	    			allotto = FUH.getRandomEmp4Escalation(toDept,"FM",newToLevel,"","",connectionSpace);
    	  	   	    			if (allotto==null || allotto.size()==0)
    	  	   	    			{
    	  	   	    				newToLevel = ""+(Integer.parseInt(newToLevel)+1);
   // 	System.out.println("New To Level inside Third If  "+newToLevel);
    	  	   	    				allotto = FUH.getRandomEmp4Escalation(toDept,"FM",newToLevel,"","",connectionSpace);
    	  	   	    			}
    	  	   	    		}
    	  	   	    	}
    	  	   	    	allotto1 =FUH.getRandomEmployee(toDept,deptLevel,tolevel.substring(5),allotto,"FM", connectionSpace);
    	  	   	    	if (allotto!=null && allotto.size()>0)
    	  	   	    	{
    	  	   	    		for (Object object : allotto) 
    	  	   	    		{
    	  	   	    			one.add(object.toString());
    	  	   	    		}
    	  	   	    	}
    	  	   	    	if (allotto1!=null && allotto1.size()>0) 
    	  	   	    	{
    	  	   	    		for (Object object : allotto1) 
    	  	   	    		{
    	  	   	    			two.add(object.toString());
    	  	   	    		}
    	  	   	    	}
    	
    	  	   	    	if (one!=null && one.size()>two.size())
    	  	   	    	{
    	  	   	    		one.removeAll(two);
    	  	   	    		if (one.size() > 0)
    	  	   	    		{
    	  	   	    			for (Iterator iterator = one.iterator(); iterator.hasNext();) 
    	  	   	    			{
    	  	   	    				Object object = (Object) iterator.next();
    	  	   	    				allottoId = object.toString();
    	  	   	    				break;
    	  	   	    			}
    	  	   	    		}
    	  	   	    	} 
    	  	   	    	else 
    	  	   	    	{
    	  	   	    		List pending_alloted = FUH.getPendingAllotto(toDept,"FM",connectionSpace);
    	  	   	    		if (pending_alloted!=null && pending_alloted.size()>0)
    	  	   	    		{
    	  	   	    			for (Object object : pending_alloted)
    	  	   	    			{
    	  	   	    				two_new.add(object.toString());
    	  	   	    			}
    	  	   	    		}
    	
    	  	   	    		if (two_new!=null && two_new.size()>0) 
    	  	   	    		{
    	  	   	    			one.removeAll(two_new);
    	  	   	    		}
    	  	   	    		if (one!=null && one.size()>0) 
    	  	   	    		{
    	  	   	    			allottoId = FUH.getRandomEmployee("FM", one,connectionSpace);
    	  	   	    		}
    	  	   	    		else
    	  	   	    		{
    	  	   	    			allottoId = FUH.getRandomEmployee("FM", allotto,connectionSpace);
    	  	   	    		}
    	  	   	    	}
    	  	   	    }
    	  	   	    catch (Exception e)
    	  	   	    {
    	  	   	    	e.printStackTrace();
    	  	   	    }
				}
   				
   				
   			//	System.out.println(byDept+"<<<<<allottoId is as "+allottoId+">>>>>"+toDept);
   				if(toDept!=null && byDept!=null && allottoId!=null && ticketNo!=null)
   				{
   					InsertDataTable ob=new InsertDataTable();
   					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
   					ob.setColName("ticket_no");
	   				ob.setDataName(ticketNo);
	   				insertData.add(ob);
	   			 
	   				ob=new InsertDataTable();
	   				ob.setColName("feed_by");
	   				ob.setDataName(feedbackBy);
	   				insertData.add(ob);
	   			
	   				//	 System.out.println("FeedBy is as <>>>>>>>>>>>>>>>>>>>>>>>>>>."+feedby);
	   				//patId
	   				
	   				ob=new InsertDataTable();
					ob.setColName("clientId");
					ob.setDataName(crNo);
					insertData.add(ob);
					
					ob=new InsertDataTable();
					ob.setColName("patientId");
					ob.setDataName(patientId);
					insertData.add(ob);
					
					ob=new InsertDataTable();
					ob.setColName("patMobNo");
					ob.setDataName(mobileNo.trim());
					insertData.add(ob);
					
					ob=new InsertDataTable();
					ob.setColName("patEmailId");
					ob.setDataName(emailId);
					insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("feed_by_mobno");
	   				ob.setDataName(mobileNo.trim());
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("feed_by_emailid");
	   				ob.setDataName(emailId);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("deptHierarchy");
	   				ob.setDataName(deptLevel);
	   				insertData.add(ob);
	   					 
	   				ob=new InsertDataTable();
	   				ob.setColName("by_dept_subdept");
	   				ob.setDataName(byDept);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("subcatg");
	   				ob.setDataName(subCategory);
	   				insertData.add(ob);
	   				
	   				ob=new InsertDataTable();
	   				ob.setColName("feed_brief");
	   				ob.setDataName(feed_brief);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("to_dept_subdept");
	   				ob.setDataName(toDept);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("allot_to");
	   				ob.setDataName(allottoId);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("escalation_date");
	   				ob.setDataName(escalation_date);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("escalation_time");
	   				ob.setDataName(escalation_time);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("open_date");
	   				ob.setDataName(DateUtil.getCurrentDateUSFormat());
	   				insertData.add(ob);
	   				
	   				ob=new InsertDataTable();
	   				ob.setColName("open_time");
	   				ob.setDataName(DateUtil.getCurrentTime());
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("level");
	   				ob.setDataName("Level1");
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("status");
	   				ob.setDataName("Pending");
	   				insertData.add(ob);
	   				
	   				ob=new InsertDataTable();
	   				ob.setColName("via_from");
	   				ob.setDataName(mode);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("feed_registerby");
	   				ob.setDataName(feedbackBy);
	   				insertData.add(ob);
	   				 
	   				ob=new InsertDataTable();
	   				ob.setColName("location");
	   				ob.setDataName(location);
	   				insertData.add(ob);
	   				
	   				ob=new InsertDataTable();
	                 ob.setColName("feedback_remarks");
	                 ob.setDataName("");
	                 insertData.add(ob);
	                 
	                 ob=new InsertDataTable();
	                 ob.setColName("moduleName");
	                 ob.setDataName("FM");
	                 insertData.add(ob);
	   				
	   				tableCreated=cot.insertIntoTable("feedback_status",insertData,connectionSpace);
	   				
	   			//	System.out.println("Data Inserted is as "+tableCreated);
	   				
	   				StringBuffer buffer=new StringBuffer("update feedbackdata set handledBy='"+allottoId+"' where id="+feedDataId+"");
					new createTable().executeAllUpdateQuery(buffer.toString(), connectionSpace);
			    	insertData.clear();
			    	
			    	if(tableCreated)
			    	{
			    		List dataList=new createTable().executeAllSelectQuery("select empName,mobOne,emailIdOne from employee_basic where id='"+allottoId+"'", connectionSpace);
			    		if(dataList!=null && dataList.size()>0)
			    		{
			    			FeedbackPojo fbp=new FeedbackPojo();

			        		 for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			        		 {
								Object[] object = (Object[]) iterator.next();
								if(object!=null)
								{
									fbp.setFeedback_allot_to(object[0].toString());
									fbp.setAllot_to_mobno(object[1].toString());
									fbp.setEmailIdOne(object[2].toString());
									fbp.setFeedaddressing_time(DateUtil.getCurrentTimewithSeconds());
								}
							}
			        		 
			        		 dataList.clear();
			        		 
			        		 dataList=new createTable().executeAllSelectQuery("select escalation_date,escalation_time,feed_by,feed_brief from feedback_status ", connectionSpace);
			        		 if(dataList!=null && dataList.size()>0)
			        		 {

				        		 for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				        		 {
									Object[] object = (Object[]) iterator.next();
									if(object!=null)
									{
										if(object[0]!=null)
										{
											fbp.setEscalation_date(object[0].toString());
										}
										
										if(object[1]!=null)
										{
											fbp.setEscalation_time(object[1].toString());
										}
										
										if(object[2]!=null)
										{
											fbp.setFeed_by(object[2].toString());
										}
										
										if(object[3]!=null)
										{
											fbp.setFeed_brief(object[3].toString());
										}
									}
								}
				        	 
			        		 }
			        		 fbp.setFeed_registerby(feedbackBy);
			        		 fbp.setTicket_no(ticketNo);
								
			        		 String adminDeptName=getAdminDeptName();
								
			        		 fbp.setFeedback_to_dept(adminDeptName);
			        		 if(needesc!=null && needesc.equalsIgnoreCase("Y"))
			        		 {
			        			 fbp.setFeedback_catg("Satisfaction");
				        		 fbp.setFeedback_subcatg("Not Satisfied");
			        		 }
			        		 else
			        		 {
			        			 fbp.setFeedback_catg("Appreciation");
				        		 fbp.setFeedback_subcatg("Patient is Satisfied");
			        		 }
			        		 
			        		 fbp.setFeedback_by_dept(adminDeptName);
			        		 fbp.setLocation(location);

			        		// if (fbp!=null && satisfaction.equalsIgnoreCase("1")) 
			        		 if(fbp!=null)
			        		 {
			        			 if (fbp.getAllot_to_mobno()!=null && fbp.getAllot_to_mobno()!="" && fbp.getAllot_to_mobno().trim().length()==10 && (fbp.getAllot_to_mobno().startsWith("9") || fbp.getAllot_to_mobno().startsWith("8") || fbp.getAllot_to_mobno().startsWith("7"))) 
			        			 {
			        				 String levelMsg="Open Alert: Ticket No: "+fbp.getTicket_no()+", Open By: "+DateUtil.makeTitle(fbp.getFeed_registerby())+", Location: "+fbp.getLocation()+", Feedback: "+fbp.getFeed_brief()+", To be closed by :"+DateUtil.convertDateToIndianFormat(fbp.getEscalation_date())+","+fbp.getEscalation_time();
			        				 //String name, String dept,String mobone, String msg, String ticketno, String status, String statusflag, String module,String smsDate,String smsTime,SessionFactory connectionSpace 
			        				 new MsgMailCommunication().addScheduledMessage(fbp.getFeedback_allot_to(),adminDeptName,fbp.getAllot_to_mobno(), levelMsg, "", "Pending", "0","FM",DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimewithSeconds(),connectionSpace);
			        			 }
			        			 
			        			 if (fbp.getEmailIdOne()!=null && !fbp.getEmailIdOne().equals(""))
			        			 {
			        				 String mailBody =new FeedbackViaTab().getConfigMessage(fbp,"Open Feedback Alert","Pending",deptLevel,true);
			        				 //String name, String dept,String emailid, String subject, String mailtext, String ticketno,String status, String statusflag, String attachment, String module,String sendDate,String sendTime,SessionFactory connectionSpace
			        				 boolean send = new MsgMailCommunication().addScheduleMail(fbp.getFeedback_allot_to(),adminDeptName,fbp.getEmailIdOne(), "Open Feedback Alert", mailBody.toString(), ticketNo, "Pending", "0","","FM",DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimewithSeconds(),connectionSpace);
			        			 }
			        			 
			        			 return ticketNo;
			        		 }
			    		}
			    	}
   				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return ticketNo;
	}
	
	public void getEscalationDateTime(String adrr_time, String res_time, String needesc,String toDept, String module)
	{

	String date_time="",level_date_time="";
	String holiday_status="",startTime="",endTime="",callflag="";
	String dept_id = toDept;
	String date = DateUtil.getCurrentDateUSFormat();
	String currentday= DateUtil.getCurrentDayofWeek();
	//List of working timing data
	List workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	if (workingTimingData!=null && workingTimingData.size()>0) {
	holiday_status=workingTimingData.get(0).toString();
	}
	//System.out.println("Holiday Status  ");
	if (holiday_status.equals("Y") || holiday_status.equals("N") ) {
            // Name should be different for date & current day
	if (holiday_status.equals("Y")) {
	date = new WorkingTimeHelper().getWorkingDate(DateUtil.getCurrentDateUSFormat());
	//System.out.println("Inside yes Working date   "+date);
	currentday= DateUtil.getDayofDate(date);
	//System.out.println("Inside yes Working day   "+currentday);
	}
	workingTimingData.clear();
	workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	if (workingTimingData!=null && workingTimingData.size()>0)
	  {
	startTime=workingTimingData.get(1).toString();
	endTime=workingTimingData.get(2).toString();
	// Here we know the complaint lodging time is inside the the start and end time or not
	callflag=DateUtil.timeInBetween(date,startTime, endTime, DateUtil.getCurrentTime());
	//System.out.println("Call Flag Value is   "+callflag);
	if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
	 {
	//System.out.println("Inside before");
	date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
	
	//(Differrence between current date/time and date & starttime)
	String newdatetime[]= date_time.split("#");
	// Check the date time is lying inside the time block
	boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], date, endTime);
	if (flag) {
	level_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, "00:00");
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	}
	else {
	String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
	String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
	    String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
	workingTimingData.clear();
	
	date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
	currentday= DateUtil.getDayofDate(date);
	
	workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	if (workingTimingData!=null && workingTimingData.size()>0) {
	startTime=workingTimingData.get(1).toString();
	String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
	date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
	String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
	level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	}
	}
	}
	else if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("middle")) {
	//System.out.println("Inside middle");
	//Escalation date time at the time of opening the ticket 
	date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
	//System.out.println("New Date Time  "+date_time);
	String newdatetime[]= date_time.split("#");
	// Check the date time is lying inside the time block
	boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], DateUtil.getCurrentDateUSFormat(), endTime);
	//System.out.println("Flag Value is  "+flag);
	if (flag) {
	//System.out.println("Inside flag true");
	level_date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, "00:00");
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	}
	else {
	//	System.out.println("Inside flag false");
	String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
	//	System.out.println("First difference "+timeDiff1);
	String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), DateUtil.getCurrentDateUSFormat(), endTime);
	//	System.out.println("Second difference "+timeDiff2);
	String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
	//System.out.println("Main Differnce "+timeDiff2);
	workingTimingData.clear();
	
	date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
	//System.out.println("New Current date  "+date);
	currentday= DateUtil.getDayofDate(date);
	//System.out.println("New Current day  "+currentday);
	
	workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	//System.out.println("New Working Time data  "+workingTimingData);
	if (workingTimingData!=null && workingTimingData.size()>0) {
	startTime=workingTimingData.get(1).toString();
	//	System.out.println("New Start time  "+startTime);
	String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
	//	System.out.println("New Date time  "+startTime);
	date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
	String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
	level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	//System.out.println("Final non working time  "+non_working_timing);
	}
	}
	}
	else if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
	  {
	workingTimingData.clear();
	if (holiday_status.equalsIgnoreCase("Y")) {
	date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateAfter(1));
	currentday= DateUtil.getDayofDate(date);
	}
	else if (holiday_status.equalsIgnoreCase("N")) {
	date = DateUtil.getNextDateAfter(1);
	currentday= DateUtil.getDayofDate(date);
	}
	
	workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	if (workingTimingData!=null && workingTimingData.size()>0)
	 {
	startTime=workingTimingData.get(1).toString();
	endTime=workingTimingData.get(2).toString();
	date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
	String newdatetime[]= date_time.split("#");
	// Check the date time is lying inside the time block
	boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], newdatetime[0], endTime);
	if (flag) {
	//System.out.println("Inside Flag true");
	//System.out.println("Date Value  "+date);
	//System.out.println("Start Time Value  "+startTime);
	level_date_time = DateUtil.newdate_AfterEscInRoaster(date,startTime, adrr_time, "00:00");
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	}
	else 
	 {
	String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
	String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
	    String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
	workingTimingData.clear();
	
	date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
	currentday= DateUtil.getDayofDate(date);
	
	//String nextday= DateUtil.getNextDayofWeek();
	workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
	if (workingTimingData!=null && workingTimingData.size()>0)
	  {
	startTime=workingTimingData.get(1).toString();
	String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
	date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
	String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
	level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
	non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
	  }
	  }
	    }
	    }
	   }
	   }
	
	String[] date_time_arr = date_time.split("#");
	if (date_time_arr.length > 0) {
	if (needesc!=null && !needesc.equals("") && needesc.equalsIgnoreCase("Y")) {
	escalation_date = date_time_arr[0];
	escalation_time = date_time_arr[1];	
	resolution_date = date_time_arr[0];
	resolution_time = date_time_arr[1];
	}
	else {
	resolution_date = date_time_arr[0];
	resolution_time = date_time_arr[1];
	}
	}
	
	String[] level_date_time_arr = level_date_time.split("#");
	if (level_date_time_arr.length > 0) {
	level_resolution_date = level_date_time_arr[0];
	level_resolution_time = level_date_time_arr[1];
	}
	//System.out.println("Non Working Time   "+non_working_timing);
	
	}
}