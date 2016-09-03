package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.BeanUtil.FeedbackDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackOverallSummaryBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.subdepartmen.SubDepartmentAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EscalationActionControl extends ActionSupport implements ServletRequestAware{
	
	
	static final Log log = LogFactory.getLog(SubDepartmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	private Map<String, String> escalationTextBox=null;
	private Map<String, String>escalationCalender=new LinkedHashMap<String, String>();
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	List<FeedbackPojo> gridModel;
	//Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private boolean ddTrue;
	private String level;
	
	private String ddValue;
	private Map<Integer, String> sourceList=null;
	private String mobileNo;
	private String name;
	private String emailId;
	private String designation;
	private String timeofEscalation;
	private String fromDate;
	private String toDate;
	private List<FeedbackPojo> dataList;
	private String dataQuery;
	
	private String formater;
	private List<FeedbackOverallSummaryBean> summaryList;
	
	
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream inputStream;
	
	
	
// Instance Variables For Dashboard Of CRM
	
	private String currentDate;
	private int todayYes;
	private int todayNo;
	private int yesterdayYes;
	private int yesterdayNo;
	private int totalYes;
	private int totalNo;
	private int totalToday;
	private int totalYesterday;
	private int totalAll;
	private String userDeptName;
	private String patientCareFlag;
	
	private List<CustomerPojo> custDataList;
	private List<FeedbackDataPojo> feedbackList;
	private List<FeedbackDashboardPojo> feedDashboardList;
	private List<FeedbackDataPojo> feedDataDashboardList;
	
	public List<FeedbackDataPojo> getAllFeedbackTickets()
	{
		List<FeedbackDataPojo> tempList=new ArrayList<FeedbackDataPojo>();
		FeedbackDataPojo feedPojo=new FeedbackDataPojo();
		feedPojo.setId(1);
		feedPojo.setOpenTickets(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"Pending","","T"));
		feedPojo.setClosedTickets(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"Resolved","","T"));
		feedPojo.setAtL1(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level1","T"));
		feedPojo.setAttL2(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level2","T"));
		feedPojo.setAtL3(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level3","T"));
		feedPojo.setAtL4(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level4","T"));
		
		
		feedPojo.setSmsOpen(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"Pending","","S"));
		feedPojo.setSmsClosed(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"Resolved","","S"));
		feedPojo.setSmsL1(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level1","S"));
		feedPojo.setSmsL2(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level2","S"));
		feedPojo.setSmsL3(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level3","S"));
		feedPojo.setSmsL4(new EscalationActionControlDao().dashboardTicketCounter(connectionSpace,"","Level4","S"));
		
		tempList.add(feedPojo);
		
		
		return tempList;
	}
	
	public int getCounter(String toSubDept,String whereQuery)
	{
		int count=0;
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		StringBuffer buffer=new StringBuffer("select count(*) from feedback_status where to_dept_subdept='"+toSubDept+"' "+whereQuery);
		//System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+buffer.toString());
		List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					count=Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}
	public String getDeptDashboardData()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
			//	System.out.println("In for Dept Dashboard");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				String userName=(String)session.get("uName");
				String deptLevel = (String)session.get("userDeptLevel");String dept_subdept_id ="";
				String subDeptName=null;
				List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				if (empData!=null && empData.size()>0) {
					for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							if(object[3]!=null)
							{
								dept_subdept_id = object[3].toString();
							}
							
							if(object[6]!=null)
							{
								subDeptName = object[6].toString();
							}
						}
					}
				}
			//	System.out.println("Sub Department name is as >>>>>>>>>>>>>>>>>>>"+subDeptName);
				boolean showAllFlag=false;
				
				if(subDeptName!=null && (subDeptName.equalsIgnoreCase("Patient Care")))
				{
					showAllFlag=true;
				}
				else
				{
					showAllFlag=false;
				}
				List subDeptList = new ArrayList();
				if(showAllFlag)
				{
					
					StringBuffer buffer=new StringBuffer("select subDept.id,subDept.subdeptname from subdepartment as subDept inner join feedback_type as fbType on fbType.dept_subdept=subDept.id");
				//	System.out.println("BUFFER"+buffer.toString());
					dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
				}
				else
				{
					subDeptList.add(dept_subdept_id);
					StringBuffer buffer=new StringBuffer("select subDept.id,subDept.subdeptname from subdepartment as subDept inner join feedback_type as fbType on fbType.dept_subdept=subDept.id where subDept.id in "+subDeptList.toString().replace("[", "(").replace("]", ")"));
				//	System.out.println("BUFFER"+buffer.toString());
					dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
				}
				
				
				if(dataList!=null && dataList.size()>0)
				{
					feedDashboardList=new ArrayList<FeedbackDashboardPojo>();
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						FeedbackDashboardPojo fPojo=new FeedbackDashboardPojo();
						int i=0;
						if(object!=null)
						{
							//System.out.println(">>>>>>>>>>>>>>");
							fPojo.setId(++i);
							fPojo.setSubDeptName(object[1].toString());
							fPojo.setOpenTickets(String.valueOf(getCounter(object[0].toString()," and status='Pending'")));
							fPojo.setResolvedTickets(String.valueOf(getCounter(object[0].toString()," and status='Resolved'")));
							fPojo.setLevel1(String.valueOf(getCounter(object[0].toString()," and level='Level1'")));
							fPojo.setLevel2(String.valueOf(getCounter(object[0].toString()," and level='Level2'")));
							fPojo.setLevel3(String.valueOf(getCounter(object[0].toString()," and level='Level3'")));
							fPojo.setLevel4(String.valueOf(getCounter(object[0].toString()," and level='Level4'")));
					//		System.out.println("SubDept is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fPojo.getSubDeptName());
						}
						feedDashboardList.add(fPojo);
					}
				}
				setMainHeaderName("Department Wise Dashboard>> View");
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return INPUT;
		}
	}
	
	
	public String getDashboardData()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				boolean tableExists=new createTable().tableExists("feedbackdata",connectionSpace);
				if(tableExists)
				{

					summaryList=new ArrayList<FeedbackOverallSummaryBean>();
					String userName=session.get("uName").toString();
				//	System.out.println("User Name is as <<<><><><><><><><><><><>>"+userName);
					
					todayYes=new EscalationActionControlDao().dashboardCounter(connectionSpace,"feedbackdata","openDate",DateUtil.getCurrentDateUSFormat(),"targetOn","Yes");
				//	System.out.println("Today Yes<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+todayYes);
					todayNo=new EscalationActionControlDao().dashboardCounter(connectionSpace,"feedbackdata","openDate",DateUtil.getCurrentDateUSFormat(),"targetOn","No");
				//	System.out.println("Today No<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+todayNo);
					yesterdayYes=new EscalationActionControlDao().dashboardCounter(connectionSpace,"feedbackdata","openDate",DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()),"targetOn","Yes");
				//	System.out.println("yesterdayYes<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+yesterdayYes);
					yesterdayNo=new EscalationActionControlDao().dashboardCounter(connectionSpace,"feedbackdata","openDate",DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()),"targetOn","No");
				//	System.out.println("yesterdayNo<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+yesterdayNo);
					totalYes=new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata","targetOn","Yes");
					totalNo=new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata","targetOn","No");
					currentDate=DateUtil.getCurrentDateIndianFormat();
					totalToday=todayNo+todayYes;
					totalYesterday=yesterdayYes+yesterdayNo;
					totalAll=totalYes+totalNo;

					StringBuffer buffer=new StringBuffer();
					buffer.append("select subdeptname from subdepartment where id=(select subdept from employee_basic where useraccountid=(select id from useraccount where userID='"+Cryptography.encrypt(userName,DES_ENCRYPTION_KEY)+"' limit 1))");
					List dataList=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
					if(dataList!=null)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object = (Object) iterator.next();
							if(object!=null)
							{
								setUserDeptName(object.toString());
								if(object.toString()!=null && object.toString().equalsIgnoreCase("Patient Care"))
								{
									setPatientCareFlag("true");
								}
								else
								{
									setPatientCareFlag("false");
								}
							}
						}
					}
					
					// setting Tickets Details
					setFeedbackList(getAllFeedbackTickets());
					setMainHeaderName("Patient Dashboard >>View ");
				//	System.out.println("List Size is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<"+getFeedbackList().size());
					
					//feedDataDashboardList
					feedDataDashboardList=new ArrayList<FeedbackDataPojo>();
					StringBuffer bufr=new StringBuffer("select field_name, field_value from feedback_data_configuration where activeType='true' and id in (5,6,7,8,9,10,11,12)");
					List data=new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);
					if(data!=null && data.size()>0)
					{
						if(tableExists)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								FeedbackDataPojo fP=new FeedbackDataPojo();
								Object[] object = (Object[]) iterator.next();
								if(object!=null)
								{
									if(object[0]!=null && object[1]!=null)
									{
										fP.setSubDept(object[0].toString());
										fP.setPoor(new EscalationActionControlDao().getGraphDataCounter(connectionSpace,"feedbackdata",object[1].toString(),"Poor"));
										fP.setAvg(new EscalationActionControlDao().getGraphDataCounter(connectionSpace,"feedbackdata",object[1].toString(),"Average"));
										fP.setGood(new EscalationActionControlDao().getGraphDataCounter(connectionSpace,"feedbackdata",object[1].toString(),"Good"));
										fP.setVgood(new EscalationActionControlDao().getGraphDataCounter(connectionSpace,"feedbackdata",object[1].toString(),"Very Good"));
										fP.setExclnt(new EscalationActionControlDao().getGraphDataCounter(connectionSpace,"feedbackdata",object[1].toString(),"Excellent"));
									}
								}
								
								feedDataDashboardList.add(fP);
							}
						
						}
					}
					
					// Code for Overall Summary Starts From Here
					int i=1;
					int counter=0;
					List<String> modesList=new EscalationActionControlDao().getGraphData(connectionSpace,"feedbackdata","mode","select");
					StringBuffer bufer=null;
					for(String str:modesList)
					{
						FeedbackOverallSummaryBean summary=new FeedbackOverallSummaryBean();
						
						summary.setId(i++);
						summary.setMode(str);
						
						// For Yesterdays Yes and No
						bufer=new StringBuffer("select count(*) from feedbackdata where openDate='"+DateUtil.getNextDateAfter(-1)+"' and mode='"+str+"' && targetOn='Yes'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayYes(String.valueOf(counter));
						bufer=new StringBuffer("select count(*) from feedbackdata where openDate='"+DateUtil.getNextDateAfter(-1)+"' and mode='"+str+"' && targetOn='No'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayNo(String.valueOf(counter));
						
						
						// For Todays Yes and No
						bufer=new StringBuffer("select count(*) from feedbackdata where openDate='"+DateUtil.getCurrentDateUSFormat()+"' and mode='"+str+"' && targetOn='Yes'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayYes(String.valueOf(counter));
						bufer=new StringBuffer("select count(*) from feedbackdata where openDate='"+DateUtil.getCurrentDateUSFormat()+"' and mode='"+str+"' && targetOn='No'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayNo(String.valueOf(counter));
						
						
						// For Total Yes and No
						bufer=new StringBuffer("select count(*) from feedbackdata where mode='"+str+"' && targetOn='Yes'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalYes(String.valueOf(counter));
						bufer=new StringBuffer("select count(*) from feedbackdata where mode='"+str+"' && targetOn='No'");
						counter=new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalNo(String.valueOf(counter));
						
						
						summaryList.add(summary);
					}
				}
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 e.printStackTrace();
		}

		setMainHeaderName("Negative Response SMS>> View");
		return SUCCESS;

	}
	
	public String beforeEscalationAdd()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setEscalationAddPageDataFields();
			
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeleadAdd of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem !");
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public void setEscalationAddPageDataFields()
	{
		List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_escalationLevel_configuration",accountID,connectionSpace,"",0,"table_name","escalationLevel_configuration");
		if(offeringLevel1!=null)
		{
			escalationTextBox=new LinkedHashMap<String, String>();
			for(GridDataPropertyView  gdp:offeringLevel1)
			{
				if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					escalationTextBox.put(gdp.getColomnName(), gdp.getHeadingName());
				}
				else if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					
					if(gdp.getColomnName().equalsIgnoreCase("level"))
					{
						level=gdp.getHeadingName();
						ddTrue=true;
					}
					
					
				}
				else if(gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					escalationCalender.put(gdp.getColomnName(), gdp.getHeadingName());
				}
			}
		}
	}
	
	public String addEscalationData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_escalationLevel_configuration",accountID,connectionSpace,"",0,"table_name","escalationLevel_configuration");
			if(statusColName!=null)
			{
				//create table query based on configuration
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				InsertDataTable ob=null;
				boolean status=false;
				List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
				boolean userTrue=false;
				boolean dateTrue=false;
				boolean timeTrue=false;
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
					 else if(gdp.getColomnName().equalsIgnoreCase("date"))
						 dateTrue=true;
					 else if(gdp.getColomnName().equalsIgnoreCase("time"))
						 timeTrue=true;
				}
				
				 cbt.createTable22("escalationlevel",Tablecolumesaaa,connectionSpace);
				 
				 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				 Collections.sort(requestParameterNames);
				 Iterator it = requestParameterNames.iterator();
				 while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
							{
							 ob=new InsertDataTable();
							// System.out.println("Parmname"+Parmname);
							 ob.setColName(Parmname);
							 ob.setDataName(paramValue);
							 insertData.add(ob);
							}
				 }
				 if(userTrue)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("userName");
					 ob.setDataName(userName);
					 insertData.add(ob);
				 }
				 if(dateTrue)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("date");
					 ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 insertData.add(ob);
				 }
				 if(timeTrue)
				 {
					 ob=new InsertDataTable();
					 ob.setColName("time");
					 ob.setDataName(DateUtil.getCurrentTime());
					 insertData.add(ob);
				 }
			//	 System.out.println("level>>>>"+getLevel());
				 
			 	    if(level.equalsIgnoreCase("level1"))
			 	    {
			 	    	    String updateEmail=getEmailId();
			 	    	//    System.out.println("emailId at level 1>>>>>>"+emailId);
			 	    	    String kk="'level1'";
							wherClause.put("emailId",emailId);
							wherClause.put("timeofEscalation",timeofEscalation);
					 	    condtnBlock.put("level",kk);
					 	   status=cbt.updateTableColomn("escalationlevel", wherClause, condtnBlock,connectionSpace);
			 	    }	
			 	    if(level.equalsIgnoreCase("level2"))
			 	    {
			 	    	
			 	    	String updateEmail=getEmailId();
			 	    	String kk="'level2'";
			 	    	wherClause.put("emailId",updateEmail);
			 	    	wherClause.put("timeofEscalation",timeofEscalation);
			 	    	condtnBlock.put("leve2",kk);
			 	    	status=cbt.updateTableColomn("escalationlevel", wherClause, condtnBlock,connectionSpace);
			 	    }	
			 	    if(level.equalsIgnoreCase("level3"))
			 	    {
			 	    	
			 	    	String updateEmail=getEmailId();
			 	    	String kk="'level3'";
			 	    	wherClause.put("emailId",updateEmail);
			 	    	wherClause.put("timeofEscalation",timeofEscalation);
			 	    	condtnBlock.put("level",kk);
			 	    	status=cbt.updateTableColomn("escalationlevel", wherClause, condtnBlock,connectionSpace);
			 	    }	
				 
				// status=cbt.insertIntoTable("escalationlevel",insertData,connectionSpace); 
					//condtnBlock.put("id",getId());
					//status=cbt.updateTableColomn("leadgeneration", wherClause, condtnBlock,connectionSpace);
				 if(status)
				 {
					 addActionMessage("LEVEL Configured Successfully!!!");
					 return SUCCESS;
				 }
				 else
				 {
					 addActionError("Oops There is some error in data!");
					 return ERROR;
				 }
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method leadGenerationAdd of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Oops There is some error in data!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String beforeEscalationView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getModifyFlag().equalsIgnoreCase("0"))
				setModifyFlag("false");
			else
				setModifyFlag("true");	
			if(getDeleteFlag().equalsIgnoreCase("0"))
				setDeleteFlag("false");
			else
				setDeleteFlag("true");
			
			if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false"))
				setMainHeaderName("Escalation >> View");
			else if(getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false"))
				setMainHeaderName("Escalation >> Modify");
			else if(getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true"))
				setMainHeaderName("Escalation >> Delete");
			
			setEscalationViewProp();
					
					
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setEscalationViewProp()
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_escalationLevel_configuration",accountID,connectionSpace,"",0,"table_name","escalationLevel_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}
	
	public String viewEscalationInGrid()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from escalationlevel");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
			{
				BigInteger count=new BigInteger("3");
				for(Iterator it=dataCount.iterator(); it.hasNext();)
				{
					 Object obdata=(Object)it.next();
					 count=(BigInteger)obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				List fieldNames=Configuration.getColomnList("mapped_escalationLevel_configuration", accountID, connectionSpace, "escalationLevel_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
					    Object obdata=(Object)it.next();
					    if(obdata!=null)
					    {
						    if(i<fieldNames.size()-1)
						    	query.append(obdata.toString()+",");
						    else
						    	query.append(obdata.toString());
					    }
					    i++;
					
				}
				
				query.append(" from escalationlevel");
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" where ");
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				query.append(" limit "+from+","+to);
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						Map<String, Object> one=new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if(obdata[k]!=null)
							{
									if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String viewModifyEscalation()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("escalationlevel", wherClause, condtnBlock,connectionSpace);
			}
			else if(getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String tempIds[]=getId().split(",");
				StringBuilder condtIds=new StringBuilder();
					int i=1;
					for(String H:tempIds)
					{
						if(i<tempIds.length)
							condtIds.append(H+" ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("escalationlevel", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}

	
	public String beforeViewAction()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				setMainHeaderName("Feedback >> Total View");
				if(getLevel()!=null && getLevel().equalsIgnoreCase("No"))
				{
					setGridColomnNamesNegative();
					return "negativeSearch";
				}
				else
				{
					setFeedbackViewProp();
				}
				return SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getSearchedNegativeDataInGrid()
	{
        boolean valid=ValidateSession.checkSession();
        if(valid)
        {
            try
            {
                CommonOperInterface cbt=new CommonConFactory().createInterface();
                {
                	custDataList=new ArrayList<CustomerPojo>();
                	
                	
                     StringBuilder queryNew2=new StringBuilder("");
                    

                	 if(getFromDate()!=null && !getFromDate().equalsIgnoreCase("") && getToDate()!=null && !getToDate().equalsIgnoreCase(""))
                	 {
                		 queryNew2.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
                     	 		" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
                     	 		" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," +
                     	 		" feedData.compType,feedData.handledBy,feedData.remarks," +
                     	 		" feedData.kword,feedData.ip,feedData.status,feedbck.spare_used,feedbck.resolve_remark from feedback_ticket as feedbt" +
                     	 		" inner join feedback_status_feedback as feedbck on feedbt.feed_stat_id=feedbck.id " +
                     	 		" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id " +
                     	 		" where feedData.targetOn='No'" +
                     	 		" && openDate between '"+DateUtil.convertDateToUSFormat(getFromDate())+"' and '"+DateUtil.convertDateToUSFormat(getToDate())+
                     	 		" order by feedData.openDate DESC");
                	 }
                	 else
                	 {
                		 queryNew2.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
                      	 		" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
                      	 		" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," +
                      	 		" feedData.compType,feedData.handledBy,feedData.remarks," +
                      	 		" feedData.kword,feedData.ip,feedData.status,feedbck.spare_used,feedbck.resolve_remark from feedback_ticket as feedbt" +
                      	 		" inner join feedback_status_feedback as feedbck on feedbt.feed_stat_id=feedbck.id " +
                      	 		" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id " +
                      	 		" where feedData.targetOn='No'" +
                      	 		" order by feedData.openDate DESC");
                	 }
                     List ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
                     List<CustomerPojo> tempList=new ArrayList<CustomerPojo>();
                     int i=1;
                     if(ticketDataList!=null && ticketDataList.size()>0)
                        {
                            for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
                            {
                            	CustomerPojo feed=new CustomerPojo();
                                Object[] type = (Object[]) iterator.next();
                            	
                            	if(type[14]!=null)
                            	{
                            		feed.setId(Integer.parseInt(type[14].toString()));
                            	}
                            	
                            	if(type[0]!=null)
                            	{
                            		feed.setFeedTicketId(Integer.parseInt(type[0].toString()));
                            	}
                            	if(type[1]!=null)
                            	{
                            		feed.setTicketNo(type[1].toString());
                            	}
                            	if(type[2]!=null)
                            	{
                            		feed.setOpenDate(DateUtil.convertDateToIndianFormat(type[2].toString()));
                            	}
                            	
                            	if(type[3]!=null)
                            	{
                            		feed.setOpenTime(type[3].toString());
                            	}
                            	
                            	if(type[4]!=null)
                            	{
                            		feed.setEscalationDate(DateUtil.convertDateToIndianFormat(type[4].toString()));
                            	}
                            	
                            	if(type[5]!=null)
                            	{
                            		feed.setEscalationTime(type[5].toString());
                            	}
                            	
                            	if(type[6]!=null)
                            	{
                            		feed.setLevel(type[6].toString());
                            	}
                            	
                            	if(type[7]!=null)
                            	{
                            		feed.setClientId(type[7].toString());
                            	}
                            	
                            	if(type[8]!=null)
                            	{
                            		feed.setClientName(type[8].toString());
                            	}
                            	
                            	if(type[9]!=null)
                            	{
                            		feed.setMobNo(type[9].toString());
                            	}
                            	
                            	if(type[10]!=null)
                            	{
                            		feed.setEmailId(type[10].toString());
                            	}
                            	
                            	if(type[11]!=null)
                            	{
                            		feed.setRefNo(type[12].toString());
                            	}
                            	
                            	if(type[12]!=null)
                            	{
                            		feed.setCentreCode(type[12].toString());
                            	}
                            	
                            	if(type[13]!=null)
                            	{
                            		feed.setCentreName(type[13].toString());
                            	}
                            	
                            	if(type[15]!=null)
                            	{
                            		feed.setProblem(type[15].toString());
                            	}
                            	
                            	if(type[16]!=null)
                            	{
                            		feed.setComplaintType(type[16].toString());
                            	}
                            	
                            	if(type[17]!=null)
                            	{
                            		feed.setHandledBy(type[17].toString());
                            	}
                            	
                            	if(type[18]!=null)
                            	{
                            		feed.setRemarks(type[18].toString());
                            	}
                            	
                            	if(type[19]!=null)
                            	{
                            		feed.setKeyWord(type[19].toString());
                            	}
                            	
                            	if(type[20]!=null)
                            	{
                            		feed.setIp(type[20].toString());
                            	}
                            	if(type[21]!=null)
                            	{
                            		feed.setStatus(type[21].toString());
                            	}
                            	if(type[22]!=null)
                            	{
                            		feed.setRca(type[22].toString());
                            	}
                            	if(type[23]!=null)
                            	{
                            		feed.setCapa(type[23].toString());
                            	}
                                tempList.add(feed);
                            }
                            setCustDataList(tempList);
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
	 public void setGridColomnNamesNegative()
	    {
		 masterViewProp=new ArrayList<GridDataPropertyView>();
	    	
	    	
	    	GridDataPropertyView gpv=new GridDataPropertyView();
		
	    	gpv.setColomnName("id");
			gpv.setHeadingName("ID");
			gpv.setHideOrShow("true");
			gpv.setAlign("center");
			gpv.setFrozenValue("false") ;
			masterViewProp.add(gpv); 
			
			gpv.setColomnName("feedback_ticketId");
			gpv.setHeadingName("id");
			gpv.setKey("true");
			gpv.setHideOrShow("true");
			gpv.setAlign("center");
			gpv.setFrozenValue("false") ;
			masterViewProp.add(gpv); 
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("ticketNo");
			gpv.setHeadingName("Ticket No");
			gpv.setFrozenValue("false");
			gpv.setAlign("center");
			gpv.setWidth(100);
			if(getSearchString()!=null && getSearchString().equalsIgnoreCase("actionTaken"))
			{
				
			}
			else
			{
				gpv.setFormatter("ticketNo2");
			}
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setAlign("center");
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("rca");
			gpv.setHeadingName("RCA");
			gpv.setAlign("center");
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("capa");
			gpv.setHeadingName("CAPA");
			gpv.setAlign("center");
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("openDate");
			gpv.setHeadingName("Open Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("openTime");
			gpv.setHeadingName("Open Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalationDate");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalationTime");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("clientId");
			gpv.setHeadingName("Patient Id");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("clientName");
			gpv.setHeadingName("Patient Name");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("mobNo");
			gpv.setHeadingName("Mobile No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("emailId");
			gpv.setHeadingName("Email");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("refNo");
			gpv.setHeadingName("Reference No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("centreCode");
			gpv.setHeadingName("Room No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("centreName");
			gpv.setHeadingName("Purpose of Visit");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("problem");
			gpv.setHeadingName("Observation");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("complaintType");
			gpv.setHeadingName("Type");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			/*gpv=new GridDataPropertyView();
			gpv.setColomnName("handledBy");
			gpv.setHeadingName("Handled By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);*/
			
			/*gpv=new GridDataPropertyView();
			gpv.setColomnName("remarks");
			gpv.setHeadingName("Remarks");
			gpv.setAlign("center");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("keyWord");
			gpv.setHeadingName("Keyword");
			gpv.setAlign("center");
			gpv.setWidth(70);
			masterViewProp.add(gpv);*/
			
			if(getSearchString()!=null && getSearchString().equalsIgnoreCase("actionTaken"))
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName("actionTaken");
				gpv.setHeadingName("Action Taken");
				gpv.setAlign("center");
				gpv.setWidth(70);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("actionDate");
				gpv.setHeadingName("Action Date");
				gpv.setAlign("center");
				gpv.setWidth(70);
				masterViewProp.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("actionTime");
				gpv.setHeadingName("Action Time");
				gpv.setAlign("center");
				gpv.setWidth(70);
				masterViewProp.add(gpv);
			}
	    }
	public void setFeedbackViewProp()
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_configuration",accountID,connectionSpace,"",0,"table_name","feedback_data_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(100);
			masterViewProp.add(gpv);
		}
	}
	
	public String getActionReport()
	{
		boolean validSession=ValidateSession.checkSession();
		EscalationActionControlDao actionControlDao=new EscalationActionControlDao();
		if(validSession)
		{
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from feedbackdata");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null)
				{
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					
					if(getLevel()!=null && getLevel().equalsIgnoreCase("No"))
					{}
					else
					{
						StringBuilder query=new StringBuilder("");
						query.append("select ");
						List fieldNames=Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
						List<Object> Listhb=new ArrayList<Object>();
						int i=0;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dyanamic query based on selected fields
							    Object obdata=(Object)it.next();
							    if(obdata!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	query.append(obdata.toString()+",");
								    else
								    	query.append(obdata.toString());
							    }
							    i++;
							
						}

						query.append(" from feedbackdata where id!=0 ");
						if(getLevel()!=null)
						{
							if((getLevel().equalsIgnoreCase("Yes") || getLevel().equalsIgnoreCase("No")))
							{
								query.append(" && targetOn='"+getLevel()+"'");
							}
						}
						
						if(getFromDate()!=null && getToDate()!=null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
						{
							query.append(" && openDate between '"+DateUtil.convertDateToUSFormat(getFromDate())+"' and '"+DateUtil.convertDateToUSFormat(getToDate())+"'");
						}
						
						
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						setDataQuery(query.toString());
						if(data!=null && data.size()>0)
						{

							Collections.reverse(data);
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k]!=null)
									{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("openDate"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("openTime"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												else
												{
													one.put(fieldNames.get(k).toString(), obdata[k].toString());
												}
											}
												
									}
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						
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
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public Map<Integer, String> getSourceList() {
		return sourceList;
	}

	public void setSourceList(Map<Integer, String> sourceList) {
		this.sourceList = sourceList;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public boolean isDdTrue() {
		return ddTrue;
	}

	public void setDdTrue(boolean ddTrue) {
		this.ddTrue = ddTrue;
	}

	public String getDdValue() {
		return ddValue;
	}

	public void setDdValue(String ddValue) {
		this.ddValue = ddValue;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Map<String, String> getEscalationTextBox() {
		return escalationTextBox;
	}

	public void setEscalationTextBox(Map<String, String> escalationTextBox) {
		this.escalationTextBox = escalationTextBox;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Map<String, String> getEscalationCalender() {
		return escalationCalender;
	}

	public void setEscalationCalender(Map<String, String> escalationCalender) {
		this.escalationCalender = escalationCalender;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getTimeofEscalation() {
		return timeofEscalation;
	}

	public void setTimeofEscalation(String timeofEscalation) {
		this.timeofEscalation = timeofEscalation;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<FeedbackPojo> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<FeedbackPojo> gridModel) {
		this.gridModel = gridModel;
	}

	public List<FeedbackPojo> getDataList() {
		return dataList;
	}

	public void setDataList(List<FeedbackPojo> dataList) {
		this.dataList = dataList;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public int getTodayYes() {
		return todayYes;
	}

	public void setTodayYes(int todayYes) {
		this.todayYes = todayYes;
	}

	public int getTodayNo() {
		return todayNo;
	}

	public void setTodayNo(int todayNo) {
		this.todayNo = todayNo;
	}

	public int getYesterdayYes() {
		return yesterdayYes;
	}

	public void setYesterdayYes(int yesterdayYes) {
		this.yesterdayYes = yesterdayYes;
	}

	public int getYesterdayNo() {
		return yesterdayNo;
	}

	public void setYesterdayNo(int yesterdayNo) {
		this.yesterdayNo = yesterdayNo;
	}

	public int getTotalYes() {
		return totalYes;
	}

	public void setTotalYes(int totalYes) {
		this.totalYes = totalYes;
	}

	public int getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(int totalNo) {
		this.totalNo = totalNo;
	}

	public int getTotalToday() {
		return totalToday;
	}

	public void setTotalToday(int totalToday) {
		this.totalToday = totalToday;
	}

	public int getTotalYesterday() {
		return totalYesterday;
	}

	public void setTotalYesterday(int totalYesterday) {
		this.totalYesterday = totalYesterday;
	}

	public int getTotalAll() {
		return totalAll;
	}

	public void setTotalAll(int totalAll) {
		this.totalAll = totalAll;
	}

	public List<FeedbackDataPojo> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackDataPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}

	public String getPatientCareFlag() {
		return patientCareFlag;
	}

	public void setPatientCareFlag(String patientCareFlag) {
		this.patientCareFlag = patientCareFlag;
	}

	public List<FeedbackDashboardPojo> getFeedDashboardList() {
		return feedDashboardList;
	}

	public void setFeedDashboardList(List<FeedbackDashboardPojo> feedDashboardList) {
		this.feedDashboardList = feedDashboardList;
	}

	public String getDataQuery() {
		return dataQuery;
	}

	public void setDataQuery(String dataQuery) {
		this.dataQuery = dataQuery;
	}

	public List<FeedbackDataPojo> getFeedDataDashboardList() {
		return feedDataDashboardList;
	}

	public void setFeedDataDashboardList(
			List<FeedbackDataPojo> feedDataDashboardList) {
		this.feedDataDashboardList = feedDataDashboardList;
	}

	public List<FeedbackOverallSummaryBean> getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(List<FeedbackOverallSummaryBean> summaryList) {
		this.summaryList = summaryList;
	}

	public List<CustomerPojo> getCustDataList() {
		return custDataList;
	}

	public void setCustDataList(List<CustomerPojo> custDataList) {
		this.custDataList = custDataList;
	}
}
