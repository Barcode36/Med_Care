package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.pojo.ActionStatusPojo;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.feedback.service.FeedbackViaTab;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OpenTicketsForDept extends ActionSupport 
{
	Map session = ActionContext.getContext().getSession();
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;
	private Map<Integer, String> serviceSubDeptList = null;
	private String deptHierarchy;
	boolean checkdept = false;
	private List<ConfigurationUtilBean> complanantTextColumns = null;
	private List<ConfigurationUtilBean> feedbackDDColumns = null;
	private List<ConfigurationUtilBean> feedbackDTimeColumns = null;
	private List<ConfigurationUtilBean> feedbackTextColumns = null;
	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
	private String feedbackDarft;
	private int levelOfFeedDraft=1;
	private String feedLevel1;
	private String feedLevel2;
	private String feedLevel3;
	private Map<String, String> feedbackCategoryColumns = null;
	private Map<String, String> feedbackSubCategoryDDColumns = null;
	private Map<String, String> feedbackSubCategoryTextColumns = null;
	private Map<String, String> feedbackSubCategoryTimeColumns = null;
	private Map<String, String> feedbackTypeColumns = null;
	private String feedByDept;
	private String subdept;
	
	private String mobNo;
	private String actionStatusFeedback;
	private String fbDataId;
	private String feedTicketId;
	private String id;
	private String compType;
	private String clientId;
	
	
	private List<ActionStatusPojo> actionList;
	
	public String ticketsActionDone()
	{
		return SUCCESS;
	}
	
	
	public String getActionStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			actionList=new ArrayList<ActionStatusPojo>();
			StringBuffer buffer=new StringBuffer("select feed.clientName,feed.mobNo,actionTaken.actionName,actionTaken.actionDate,actionTaken.actionTime from feedbackdata as feed" +
					" inner join feedback_actiontaken as actionTaken on actionTaken.feedDataId=feed.id" +
					" where feed.id='"+getId()+"' ");
			createTable ct=new createTable();
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List dataList=ct.executeAllSelectQuery(buffer.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					{
						ActionStatusPojo pojo=new ActionStatusPojo();
						if(object[0]!=null)
						{
							pojo.setName(object[0].toString());
						}
						
						if(object[1]!=null)
						{
							pojo.setMobNo(object[1].toString());
						}
						
						if(object[2]!=null)
						{
							pojo.setActionName(object[2].toString());
						}
						
						if(object[3]!=null)
						{
							pojo.setActionDate(object[3].toString());
						}
						
						if(object[4]!=null)
						{
							pojo.setActionTime(object[4].toString());
						}
						
						actionList.add(pojo);
					}
				}
			}
			else
			{
				setActionStatusFeedback("No Records Found");
			}
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}
	}
	
	
	public  FeedbackPojo getFullDetailsRecvedKeyWord(String clientId,SessionFactory connection)
	{
		FeedbackPojo feedPojo=new FeedbackPojo();
		List patList=new createTable().executeAllSelectQuery("select id,cr_number,patient_name,admit_consultant,mobile_no,email,station,patient_type from patientinfo where cr_number ='"+ clientId+"'",connection);
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
						feedPojo.setPatId(object[1].toString());
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
				}
			}
		}
		else
		{
			feedPojo.setMobileNo(mobNo);
		}
		return feedPojo;
	}
	
	public void addFeedbackForRespectiveRound(String clientId)
	{
	    String accountID=(String)session.get("accountid");
	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	    
	    
	    List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	    boolean userTrue=false,dateTrue=false,timeTrue=false;
		List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_feedback_configuration",accountID,connectionSpace,"",0,"table_name","feedback_data_configuration");
		if(statusColName!=null && statusColName.size()>0)
		{
			for(GridDataPropertyView gdp:statusColName)
            {
                 TableColumes ob1=new TableColumes();
                 ob1=new TableColumes();
                 ob1.setColumnname(gdp.getColomnName());
                 ob1.setDatatype("varchar(255)");
                 ob1.setConstraint("default NULL");
                 Tablecolumesaaa.add(ob1);
                 if(gdp.getColomnName().equalsIgnoreCase("userName"))
                     userTrue=true;
                 else if(gdp.getColomnName().equalsIgnoreCase("openDate"))
                     dateTrue=true;
                 else if(gdp.getColomnName().equalsIgnoreCase("openTime"))
                     timeTrue=true;
            }
		}
		
		FeedbackPojo pojo=getFullDetailsRecvedKeyWord(getClientId(),connectionSpace);
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
        {
        	InsertDataTable ob=null;
        	ob=new InsertDataTable();
            ob.setColName("clientId");
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
            ob.setDataName("Paper");
            insertData.add(ob);
                
            ob=new InsertDataTable();
            ob.setColName("level");
            ob.setDataName("Level1");
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("targetOn");
            ob.setDataName("Yes");
            insertData.add(ob);
            pojo.setSatisfaction("Yes");
        }
        boolean inserted=new createTable().insertIntoTable("feedbackdata",insertData,connectionSpace);
        if(inserted)
        {
        	openTicketsForNegFeedback(connectionSpace,pojo);
        	fbDataId=String.valueOf(FeedbackHelper.getRowId(clientId, connectionSpace));
     //   	System.out.println("Feeddata id is as >>>>"+fbDataId);
        	setFbDataId(fbDataId);
        	feedTicketId=String.valueOf(getRowId(fbDataId, connectionSpace));
     //   	System.out.println("Feed Ticket Id is as "+feedTicketId);
        	setFeedTicketId(feedTicketId);
        }
	}
	
	public static int getRowId(String feedDataId, SessionFactory connectionSpace)
	{
		int id=0;
		StringBuilder buffer=new StringBuilder("select max(id) from feedback_ticket where feed_data_id = '"+feedDataId+"'");
		List data=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if(data!=null && data.size()>0 && data.get(0)!=null)
		{
			id=Integer.parseInt(data.get(0).toString());
		}
		else
		{
			id=0;
		}
		return id;
	}
	
	
	public boolean openTicketsForNegFeedback(SessionFactory connection,FeedbackPojo feedBackPojo)
	{
		boolean opened=false;
		try
		{
		//	System.out.println("For Open Ticket");
			FeedbackViaTab tab=new FeedbackViaTab();
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
            {String uName=(String)session.get("uName");
            	String ticketNo=tab.openTicket(connection, String.valueOf(subCatId),"Paper",feedBackPojo.getName(),feedBackPojo.getMobileNo(),feedBackPojo.getEmailId(), String.valueOf(adminDeptId), String.valueOf(adminDeptId),feedBackPojo.getCentreCode(),"","Yes",clientId,feedBackPojo.getPatType(),uName,"");
            //	System.out.println("Ticket No is as >>>"+ticketNo);
            	if(ticketNo!=null && !ticketNo.equalsIgnoreCase(""))
            	{
             	   int id4=0;
             	   StringBuffer buffer=new StringBuffer("select id from feedback_status where ticket_no='"+ticketNo+"'");
             	   List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connection);
             	   if(dataList!=null&& dataList.size()>0 && dataList.get(0)!=null)
             	   {
             	   id4=Integer.parseInt(dataList.get(0).toString());
             	   }
             	   if(id4!=0)
             	   {
             		  List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
             		   String openDate="",openTime="";
             		   StringBuilder queryNew=new StringBuilder("");
                            queryNew.append(" select * from feedback_status where id='"+id4+"'  ");
                            List  dataNew=new createTable().executeAllSelectQuery(queryNew.toString(),connection);
                            for(Iterator iterator=dataNew.iterator();iterator.hasNext();)
                            {
                         	   Object object[] = (Object[]) iterator.next();
                         	   openDate=(String) object[13];
                         	   openTime=(String) object[14];
                            }
                            Tablecolumesaaa.clear();
                            TableColumes ob1=new TableColumes();
                            ob1=new TableColumes();
                            ob1.setColumnname("feedTicketNo");
                            ob1.setDatatype("varchar(255)");
                            ob1.setConstraint("default NULL");
                            Tablecolumesaaa.add(ob1);
                               
                            TableColumes ob2=new TableColumes();
                            ob2=new TableColumes();
                            ob2.setColumnname("feed_stat_id");
                            ob2.setDatatype("varchar(255)");
                            ob2.setConstraint("default NULL");
                            Tablecolumesaaa.add(ob2);
       	 	
                            TableColumes ob3=new TableColumes();
                            ob3=new TableColumes();
                            ob3.setColumnname("feed_data_id");
                            ob3.setDatatype("varchar(255)");
                            ob3.setConstraint("default NULL");
                            Tablecolumesaaa.add(ob3);
       	 	
                            TableColumes tb4=new TableColumes();
                            tb4=new TableColumes();
                            tb4.setColumnname("openDate");
                            tb4.setDatatype("varchar(255)");
                            tb4.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb4);
       	 	
                            TableColumes tb5=new TableColumes();
                            tb5=new TableColumes();
                            tb5.setColumnname("openTime");
                            tb5.setDatatype("varchar(255)");
                            tb5.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb5);
       	 	
                            TableColumes tb6=new TableColumes();
                            tb6=new TableColumes();
                            tb6.setColumnname("userName");
                            tb6.setDatatype("varchar(255)");
                            tb6.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb6);
       	 	
                            TableColumes tb7=new TableColumes();
                            tb7=new TableColumes();
                            tb7.setColumnname("deptFlag");
                            tb7.setDatatype("varchar(255)");
                            tb7.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb7);
       	 	
                            TableColumes tb8=new TableColumes();
                            tb8=new TableColumes();
                            tb8.setColumnname("adminFlag");
                            tb8.setDatatype("varchar(255)");
                            tb8.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb8);
       	 	
                            TableColumes tb9=new TableColumes();
                            tb9=new TableColumes();
                            tb9.setColumnname("dept_feed_stat_id");
                            tb9.setDatatype("varchar(255)");
                            tb9.setConstraint("default NULL");
                            Tablecolumesaaa.add(tb9);
       	 	
                            int maxId=new createTable().getMaxId("feedbackdata", connection);
                            boolean tableCreated=new createTable().createTable22("feedback_ticket",Tablecolumesaaa,connection);
                            if(tableCreated)
                            {
                            	String userName=(String)session.get("uName");
                            	
                            	
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
                                ob4.setDataName(maxId);
                                insertHistory.add(ob4);
                                ob4=new InsertDataTable();
                                ob4.setColName("openDate");
                                ob4.setDataName(openDate);
                                insertHistory.add(ob4);
                                
                                ob4=new InsertDataTable();
                                ob4.setColName("openTime");
                                ob4.setDataName(openTime);
                                ob4.setColName("userName");
                                ob4.setDataName(userName);
                                insertHistory.add(ob4);
                                tableCreated= new createTable().insertIntoTable("feedback_ticket",insertHistory,connection);
                                addActionMessage("Your feedback added Successfully!!!"); 
                            }
             	   }
            	}
            }
		}
		catch (Exception e) {
			e.printStackTrace();
			opened=false;
		}
		return opened;
	}
	
	
	public String firstActionForPCC()
	{
//		System.out.println("fbDataId is as >>>>> "+fbDataId);
		System.out.println("Comp Type is as "+getCompType());
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String userName=(String)session.get("uName");
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String dbName=(String)session.get("Dbname");
			
			String orgLevel = (String)session.get("orgnztnlevel");
			String orgId = (String)session.get("mappedOrgnztnId");
			Properties configProp=new Properties();
			 try
	            {
	            	InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
	    			configProp.load(in);
	    			feedByDept=configProp.getProperty("admin");
	            }
	            catch (Exception e) {
	            	e.printStackTrace();
				}
			/*setFbDataId(getFbDataId());
			setFeedTicketId(getFeedTicketId());
			*/
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			deptList = new LinkedHashMap<Integer, String>();
			serviceDeptList = new LinkedHashMap<Integer, String>();
			List departmentlist = new ArrayList();
			
			List colmName = new ArrayList();
			Map<String, Object> order = new HashMap<String, Object>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			
			List<String>colname=new ArrayList<String>();
			colname.add("orgLevel");
			colname.add("levelName");
			
			
			/*Get Department Data*/
			deptHierarchy = (String) session.get("userDeptLevel");
			if (deptHierarchy.equals("2")) {
				setsubDept_DeptTags(deptHierarchy);
				checkdept=true;
			}
			else if (deptHierarchy.equals("1")) {
				setsubDept_DeptTags(deptHierarchy);
				checkdept=false;
			}
			
			colmName.add("id");
			colmName.add("deptName");
			wherClause.put("orgnztnlevel", orgLevel);
			wherClause.put("mappedOrgnztnId", orgId);
			order.put("deptName", "ASC");
			
			// Getting Id, Dept Name for Non Service Department
			departmentlist = getDataFromTable("department", colmName, wherClause, order, connectionSpace);
			if (departmentlist!=null && departmentlist.size()>0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer)object[0], object[1].toString());
					
				}
			}
			
			if(departmentlist!=null)
				departmentlist.removeAll(departmentlist);
			
			// Getting Id, Dept Name for Service Department
			departmentlist = getServiceDept_SubDept(deptHierarchy, orgLevel, orgId, connectionSpace);
			if (departmentlist!=null && departmentlist.size()>0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object1 = (Object[]) iterator.next();
					serviceDeptList.put((Integer)object1[0], object1[1].toString());
				}
			}
			
			
			//setServiceSubDeptList(getAllSubDepts());
			setServiceSubDeptList(serviceDeptList);
			
			//get Feedback Draft Level and Column Names
			if(getFeedbackDarft()!=null)
			 {
				String feedlevels[]=null;
				List feedBackDraft=cbt.viewAllDataEitherSelectOrAll("mapped_feedbackdraft_level",colname,connectionSpace);
				if(feedBackDraft!=null && feedBackDraft.size()>0)
				 {
					for (Object c : feedBackDraft) {
						Object[] dataC = (Object[]) c;
						levelOfFeedDraft= Integer.parseInt((String)dataC[0]);
						feedlevels=dataC[1].toString().split("#");
					}
				 }
				
			 	
				
				if(feedlevels!=null)
				 {
					if(levelOfFeedDraft>0)
					{feedLevel1=feedlevels[0]+" >> Registration";}
					if(levelOfFeedDraft>1)
					{feedLevel2=feedlevels[1]+" >> Registration";}
					if(levelOfFeedDraft>2)
					{feedLevel3=feedlevels[2]+" >> Registration";}
					setfeedBackDraftTags(levelOfFeedDraft);
				 }
			}
						
			// Get Lodge Feedback Level and Fields Name
			setFeedLodgeTags();
			
		//	System.out.println("fbDataId is as >>>>> Before Success"+fbDataId);
			returnResult =SUCCESS;
		}		
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
			 e.printStackTrace();
			 return ERROR;
		}
	  }
	else {returnResult = LOGIN;}
	return returnResult;
 
	}
	
	
	@SuppressWarnings("unchecked")
	public String firstActionMethod()
	{
		if(fbDataId!=null && fbDataId.equalsIgnoreCase("NA"))
		{
			addFeedbackForRespectiveRound(getClientId());
			
		}
//		System.out.println("fbDataId is as >>>>> "+fbDataId);
		String returnResult= ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String userName=(String)session.get("uName");
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			String dbName=(String)session.get("Dbname");
			
			String orgLevel = (String)session.get("orgnztnlevel");
			String orgId = (String)session.get("mappedOrgnztnId");
			Properties configProp=new Properties();
			 try
	            {
	            	InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
	    			configProp.load(in);
	    			feedByDept=configProp.getProperty("admin");
	            }
	            catch (Exception e) {
	            	e.printStackTrace();
				}
			/*setFbDataId(getFbDataId());
			setFeedTicketId(getFeedTicketId());
			*/
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			deptList = new LinkedHashMap<Integer, String>();
			serviceDeptList = new LinkedHashMap<Integer, String>();
			List departmentlist = new ArrayList();
			
			List colmName = new ArrayList();
			Map<String, Object> order = new HashMap<String, Object>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			
			List<String>colname=new ArrayList<String>();
			colname.add("orgLevel");
			colname.add("levelName");
			
			
			/*Get Department Data*/
			deptHierarchy = (String) session.get("userDeptLevel");
			if (deptHierarchy.equals("2")) {
				setsubDept_DeptTags(deptHierarchy);
				checkdept=true;
			}
			else if (deptHierarchy.equals("1")) {
				setsubDept_DeptTags(deptHierarchy);
				checkdept=false;
			}
			
			colmName.add("id");
			colmName.add("deptName");
			wherClause.put("orgnztnlevel", orgLevel);
			wherClause.put("mappedOrgnztnId", orgId);
			order.put("deptName", "ASC");
			
			// Getting Id, Dept Name for Non Service Department
			departmentlist = getDataFromTable("department", colmName, wherClause, order, connectionSpace);
			if (departmentlist!=null && departmentlist.size()>0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer)object[0], object[1].toString());
					
				}
			}
			
			if(departmentlist!=null)
				departmentlist.removeAll(departmentlist);
			
			// Getting Id, Dept Name for Service Department
			departmentlist = getServiceDept_SubDept(deptHierarchy, orgLevel, orgId, connectionSpace);
			if (departmentlist!=null && departmentlist.size()>0) {
				for (Iterator iterator = departmentlist.iterator(); iterator
						.hasNext();) {
					Object[] object1 = (Object[]) iterator.next();
					serviceDeptList.put((Integer)object1[0], object1[1].toString());
				}
			}
			
			
			//setServiceSubDeptList(getAllSubDepts());
			setServiceSubDeptList(serviceDeptList);
			
			//get Feedback Draft Level and Column Names
			if(getFeedbackDarft()!=null)
			 {
				String feedlevels[]=null;
				List feedBackDraft=cbt.viewAllDataEitherSelectOrAll("mapped_feedbackdraft_level",colname,connectionSpace);
				if(feedBackDraft!=null && feedBackDraft.size()>0)
				 {
					for (Object c : feedBackDraft) {
						Object[] dataC = (Object[]) c;
						levelOfFeedDraft= Integer.parseInt((String)dataC[0]);
						feedlevels=dataC[1].toString().split("#");
					}
				 }
				
			 	
				
				if(feedlevels!=null)
				 {
					if(levelOfFeedDraft>0)
					{feedLevel1=feedlevels[0]+" >> Registration";}
					if(levelOfFeedDraft>1)
					{feedLevel2=feedlevels[1]+" >> Registration";}
					if(levelOfFeedDraft>2)
					{feedLevel3=feedlevels[2]+" >> Registration";}
					setfeedBackDraftTags(levelOfFeedDraft);
				 }
			}
						
			// Get Lodge Feedback Level and Fields Name
			setFeedLodgeTags();
			
		//	System.out.println("fbDataId is as >>>>> Before Success"+fbDataId);
			returnResult =SUCCESS;
		}		
		catch(Exception e)
		{
			 addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
			 e.printStackTrace();
			 return ERROR;
		}
	  }
	else {returnResult = LOGIN;}
	return returnResult;
 }
	
	
	public Map<Integer, String> getAllSubDepts()
	{
		Map<Integer, String> subDeptMap=new HashMap<Integer, String>();
		List datalist=new ArrayList();
		
		StringBuffer buffer=new StringBuffer("select * from subdepartment");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		createTable ct=new createTable();
		datalist=ct.executeAllSelectQuery(buffer.toString(), connectionSpace);
		
		for (Iterator iterator = datalist.iterator(); iterator.hasNext();)
		{
			Object[] object1 = (Object[]) iterator.next();
			subDeptMap.put((Integer)object1[0], object1[1].toString());
			
		}
		return subDeptMap;
	}
	
	
	 // Method for getting the field Name for feedback Draft Module 
	public void setfeedBackDraftTags(int flag)
	 {
		//flag value is getting the employee level data based on the selected no of employee configuration
		//Map<String, String> configuration2=new HashMap<String,String>();
		//Map<String, Object> temp=new HashMap<String, Object>();
		
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		String accountID=(String)session.get("accountid");
		
		List<String> data=new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		if(flag>0)
		{
			List<GridDataPropertyView> feedbackTypeList = Configuration.getConfigurationData("mapped_feedback_type_configuration",accountID,connectionSpace,"",0,"table_name","feedback_type_configuration");
			feedbackTypeColumns = new LinkedHashMap<String, String>();
			if (feedbackTypeList!=null && feedbackTypeList.size()>0)
			 {
				for (GridDataPropertyView gdv : feedbackTypeList)
				 {
				   if(gdv.getColType().trim().equalsIgnoreCase("T"))
					 {
						feedbackTypeColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					 }
				 }
			}
		}
		if(flag>1)
		{
			List<GridDataPropertyView> feedbackCategoryList = Configuration.getConfigurationData("mapped_feedback_category_configuration",accountID,connectionSpace,"",0,"table_name","feedback_category_configuration");
			feedbackCategoryColumns = new LinkedHashMap<String, String>();
			if (feedbackCategoryList!=null && feedbackCategoryList.size()>0)
			 {
				for (GridDataPropertyView gdv : feedbackCategoryList)
				 {
				   if(gdv.getColType().trim().equalsIgnoreCase("T") || gdv.getColType().trim().equalsIgnoreCase("D"))
					 {
					   feedbackCategoryColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					 }
				 }
			}
		}
		if(flag>2)
		{
			List<GridDataPropertyView> feedbackSubCategoryList = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration",accountID,connectionSpace,"",0,"table_name","feedback_subcategory_configuration");
			feedbackSubCategoryDDColumns = new LinkedHashMap<String, String>();
			feedbackSubCategoryTextColumns = new LinkedHashMap<String, String>();
			feedbackSubCategoryTimeColumns = new LinkedHashMap<String, String>();
			if (feedbackSubCategoryList!=null && feedbackSubCategoryList.size()>0)
			 {
				for (GridDataPropertyView gdv : feedbackSubCategoryList)
				 {
				   if(gdv.getColType().trim().equalsIgnoreCase("D"))
					 {
					   feedbackSubCategoryDDColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					 }
				   else if (gdv.getColType().trim().equalsIgnoreCase("T")) {
					   feedbackSubCategoryTextColumns.put(gdv.getColomnName(), gdv.getHeadingName());
				     }
				   else if (gdv.getColType().trim().equalsIgnoreCase("Time")) {
					   feedbackSubCategoryTimeColumns.put(gdv.getColomnName(), gdv.getHeadingName());	
				     }
				 }
			}
		}
	}
	
	public List getServiceDept_SubDept(String deptLevel,String orgLevel, String orgId, SessionFactory connection)
	{
		boolean flag = new HelpdeskUniversalHelper().checkTable("feedback_type",connection);
		List dept_subdeptList = null;
		String query="";
		try {
			if (deptLevel.equals("2")) {
				query="select  dept.id , dept.deptName from department as dept inner join feedback_type as typ on dept.id=typ.dept_subdept && typ.moduleName='FM' order by dept.deptName ASC";
			}
			if (flag) {
			    Session session = null;  
			    Transaction transaction = null;  
		        session=connection.openSession(); 
				transaction = session.beginTransaction(); 
				dept_subdeptList=session.createSQLQuery(query).list();  
				transaction.commit(); 
			}	
			
		} catch (SQLGrammarException e) {
          e.printStackTrace();			
		}
		return dept_subdeptList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, Object> order,SessionFactory connection)
	  {
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		selectTableData.append("select ");
		
		// Set the columns name of a table
		if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
		  {
			int i=1;
			for(String H:colmName)
			 {
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
					i++;
			 }
		 }
		
		// Here we get the whole data of a table
		else
		 {
				selectTableData.append(" * from " +tableName);
		 }
	    
		// Set the values for where clause
		if(wherClause!=null && !wherClause.isEmpty())
		   {
			 if(wherClause.size()>0)
				{
					selectTableData.append(" where ");
				}
				int size=1;
				Set set =wherClause.entrySet(); 
				Iterator i = set.iterator();
				while(i.hasNext())
				{ 
					Map.Entry me = (Map.Entry)i.next();
					if(size<wherClause.size())
						selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
					else
						selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
					size++;
				} 
			}
		
		 // Set the value for type of order for getting data in specific order
		 if(order!=null && !order.isEmpty())
		   {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
			    }
		   }
			selectTableData.append(";");
			Session session = null;  
			Transaction transaction = null;  
			try {  
		            session=connection.openSession(); 
					transaction = session.beginTransaction(); 
					Data=session.createSQLQuery(selectTableData.toString()).list();  
					transaction.commit(); 
				}
			catch (Exception ex)
				 {
					transaction.rollback();
				 } 
			return Data;
		}
	
	
	public void setsubDept_DeptTags(String level)
	 {
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		String accountID=(String)session.get("accountid");
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList!=null && deptList.size()>0)
		 {
			for (GridDataPropertyView gdv : deptList)
			 {
				ConfigurationUtilBean objdata= new ConfigurationUtilBean();
			   if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
					objdata.setKey(gdv.getColomnName());
				   objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if(gdv.getMandatroy()==null)
					objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("0"))
						objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("1"))
						objdata.setMandatory(true);
				   subDept_DeptLevelName.add(objdata);
			   }
			 }
		}
		
		if (level.equals("2")) {
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf",accountID,connectionSpace,"",0,"table_name","subdeprtmentconf");	
			if (subdept_deptList!=null && subdept_deptList.size()>0)
			 {
				for (GridDataPropertyView gdv : subdept_deptList)
				 {
					ConfigurationUtilBean objdata= new ConfigurationUtilBean();
				   if (gdv.getColomnName().equalsIgnoreCase("subdeptname")) {
					   objdata.setKey(gdv.getColomnName());
					   objdata.setValue(gdv.getHeadingName());
						objdata.setColType(gdv.getColType());
						objdata.setValidationType(gdv.getValidationType());
						if(gdv.getMandatroy()==null)
						objdata.setMandatory(false);
						else if(gdv.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if(gdv.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
					   subDept_DeptLevelName.add(objdata);
				   }
				  
				 }
			}
		}
	 }
	
	public String getFbCatOfSubDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			//	StringBuffer buffer=new StringBuffer("select feedCat.id,feedCat.categoryName from feedback_category as feedCat inner join feedback_type as feedType on feedType.id=feedCat.fbType where feedType.dept_subdept='"+subdept+"'");
				StringBuffer buffer=new StringBuffer("select id,categoryName from feedback_category_feedback where deptid='"+subdept+"' && hide_show='1' ");
				createTable ct=new createTable();
				List dataList=ct.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if(dataList!=null)
				{
					feedbackCategoryColumns=new HashMap<String, String>();
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							feedbackCategoryColumns.put(object[0].toString(), object[1].toString());
						}
					}
				}
				return SUCCESS;
			
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public void setFeedLodgeTags()
	 {
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		String accountID=(String)session.get("accountid");
		String returnResult=ERROR;
		List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration",accountID,connectionSpace,"",0,"table_name","feedback_lodge_configuration");
		complanantTextColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDDColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDTimeColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackTextColumns = new ArrayList<ConfigurationUtilBean>();
		
		if (feedLodgeTags!=null && feedLodgeTags.size()>0)
		 {
			for (GridDataPropertyView gdv : feedLodgeTags)
			 {
				ConfigurationUtilBean objdata= new ConfigurationUtilBean();
			   if (gdv.getColomnName().equals("uniqueid") || gdv.getColomnName().equals("feed_by") || gdv.getColomnName().equals("feed_by_mobno") || gdv.getColomnName().equals("feed_by_emailid")) {
				   objdata.setKey(gdv.getColomnName());
				   objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if(gdv.getMandatroy()==null)
					objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("0"))
						objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("1"))
						objdata.setMandatory(true);
				   complanantTextColumns.add(objdata);
			   }
			   else if (gdv.getColomnName().equals("feedType") || gdv.getColomnName().equals("category")) 
			   {
				   objdata.setKey(gdv.getColomnName());
				   objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if(gdv.getMandatroy()==null)
					objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("0"))
						objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("1"))
						objdata.setMandatory(true);
				   feedbackDDColumns.add(objdata);
			     }
			   else if (gdv.getColomnName().equals("subCatg") || gdv.getColomnName().equals("resolutionTime")) {
				   objdata.setKey(gdv.getColomnName());
				   objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if(gdv.getMandatroy()==null)
					objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("0"))
						objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("1"))
						objdata.setMandatory(true);
				   feedbackDTimeColumns.add(objdata);	
			     }
			   else if (gdv.getColomnName().equals("feed_brief")) {
				   objdata.setKey(gdv.getColomnName());
				   objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if(gdv.getMandatroy()==null)
					objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("0"))
						objdata.setMandatory(false);
					else if(gdv.getMandatroy().equalsIgnoreCase("1"))
						objdata.setMandatory(true);
				   feedbackTextColumns.add(objdata);
			}
			 }
			returnResult=SUCCESS;
		}
	 }
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}
	public Map<Integer, String> getServiceDeptList() {
		return serviceDeptList;
	}
	public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
		this.serviceDeptList = serviceDeptList;
	}
	public String getDeptHierarchy() {
		return deptHierarchy;
	}
	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}
	public boolean isCheckdept() {
		return checkdept;
	}
	public void setCheckdept(boolean checkdept) {
		this.checkdept = checkdept;
	}
	
	public Map getSession() {
		return session;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	
	public String getFeedbackDarft() {
		return feedbackDarft;
	}
	public void setFeedbackDarft(String feedbackDarft) {
		this.feedbackDarft = feedbackDarft;
	}
	public int getLevelOfFeedDraft() {
		return levelOfFeedDraft;
	}

	public void setLevelOfFeedDraft(int levelOfFeedDraft) {
		this.levelOfFeedDraft = levelOfFeedDraft;
	}

	public String getFeedLevel1() {
		return feedLevel1;
	}

	public void setFeedLevel1(String feedLevel1) {
		this.feedLevel1 = feedLevel1;
	}

	public List<ConfigurationUtilBean> getComplanantTextColumns() {
		return complanantTextColumns;
	}


	public void setComplanantTextColumns(
			List<ConfigurationUtilBean> complanantTextColumns) {
		this.complanantTextColumns = complanantTextColumns;
	}


	public List<ConfigurationUtilBean> getFeedbackDDColumns() {
		return feedbackDDColumns;
	}


	public void setFeedbackDDColumns(List<ConfigurationUtilBean> feedbackDDColumns) {
		this.feedbackDDColumns = feedbackDDColumns;
	}


	public List<ConfigurationUtilBean> getFeedbackDTimeColumns() {
		return feedbackDTimeColumns;
	}


	public void setFeedbackDTimeColumns(
			List<ConfigurationUtilBean> feedbackDTimeColumns) {
		this.feedbackDTimeColumns = feedbackDTimeColumns;
	}


	public List<ConfigurationUtilBean> getFeedbackTextColumns() {
		return feedbackTextColumns;
	}


	public void setFeedbackTextColumns(
			List<ConfigurationUtilBean> feedbackTextColumns) {
		this.feedbackTextColumns = feedbackTextColumns;
	}


	public String getFeedLevel2() {
		return feedLevel2;
	}

	public void setFeedLevel2(String feedLevel2) {
		this.feedLevel2 = feedLevel2;
	}

	public String getFeedLevel3() {
		return feedLevel3;
	}

	public void setFeedLevel3(String feedLevel3) {
		this.feedLevel3 = feedLevel3;
	}

	public Map<String, String> getFeedbackCategoryColumns() {
		return feedbackCategoryColumns;
	}

	public void setFeedbackCategoryColumns(
			Map<String, String> feedbackCategoryColumns) {
		this.feedbackCategoryColumns = feedbackCategoryColumns;
	}

	public Map<String, String> getFeedbackSubCategoryDDColumns() {
		return feedbackSubCategoryDDColumns;
	}

	public void setFeedbackSubCategoryDDColumns(
			Map<String, String> feedbackSubCategoryDDColumns) {
		this.feedbackSubCategoryDDColumns = feedbackSubCategoryDDColumns;
	}

	public Map<String, String> getFeedbackSubCategoryTextColumns() {
		return feedbackSubCategoryTextColumns;
	}

	public void setFeedbackSubCategoryTextColumns(
			Map<String, String> feedbackSubCategoryTextColumns) {
		this.feedbackSubCategoryTextColumns = feedbackSubCategoryTextColumns;
	}

	public Map<String, String> getFeedbackSubCategoryTimeColumns() {
		return feedbackSubCategoryTimeColumns;
	}

	public void setFeedbackSubCategoryTimeColumns(
			Map<String, String> feedbackSubCategoryTimeColumns) {
		this.feedbackSubCategoryTimeColumns = feedbackSubCategoryTimeColumns;
	}

	public Map<String, String> getFeedbackTypeColumns() {
		return feedbackTypeColumns;
	}

	public void setFeedbackTypeColumns(Map<String, String> feedbackTypeColumns) {
		this.feedbackTypeColumns = feedbackTypeColumns;
	}

	public String getFeedByDept() {
		return feedByDept;
	}

	public void setFeedByDept(String feedByDept) {
		this.feedByDept = feedByDept;
	}

	public Map<Integer, String> getServiceSubDeptList() {
		return serviceSubDeptList;
	}

	public void setServiceSubDeptList(Map<Integer, String> serviceSubDeptList) {
		this.serviceSubDeptList = serviceSubDeptList;
	}
	public String getSubdept() {
		return subdept;
	}
	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getActionStatusFeedback() {
		return actionStatusFeedback;
	}

	public void setActionStatusFeedback(String actionStatusFeedback) {
		this.actionStatusFeedback = actionStatusFeedback;
	}
	public String getFbDataId() {
		return fbDataId;
	}

	public void setFbDataId(String fbDataId) {
		this.fbDataId = fbDataId;
	}


	public List<ConfigurationUtilBean> getSubDept_DeptLevelName() {
		return subDept_DeptLevelName;
	}


	public void setSubDept_DeptLevelName(
			List<ConfigurationUtilBean> subDept_DeptLevelName) {
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}


	public String getFeedTicketId() {
		return feedTicketId;
	}


	public void setFeedTicketId(String feedTicketId) {
		this.feedTicketId = feedTicketId;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ActionStatusPojo> getActionList() {
		return actionList;
	}
	public void setActionList(List<ActionStatusPojo> actionList) {
		this.actionList = actionList;
	}
	public String getCompType() {
		return compType;
	}
	public void setCompType(String compType) {
		this.compType = compType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
