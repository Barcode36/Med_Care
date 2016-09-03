package com.Over2Cloud.ctrl.feedback;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.Over2Cloud.BeanUtil.CounterPojo;
import com.Over2Cloud.BeanUtil.CustomerPojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.RandomNumberGenerator;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.adminDept.AdminDeptAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.subdepartmen.SubDepartmentAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CustomerActionControl extends ActionSupport implements ServletRequestAware{
	
	
	static final Log log = LogFactory.getLog(SubDepartmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	private Map<String, String> customerTextBox=null;
	private Map<String, String>customerCalender=new LinkedHashMap<String, String>();
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
	//Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private boolean ddTrue;
	private String title;
	private static FeedbackHelper fHelp=new FeedbackHelper();
	
	private String ddValue;
	private Map<Integer, String> sourceList=null;
	private String dob;
	private boolean dobTrue;
	private String dom;
	private boolean domTrue;
	private String searchFlag;
	private String flage;
	private String formater;
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream inputStream;
	
	
	
	private List<FeedbackTicketPojo> ticketDataList;
	private List<CustomerPojo> custDataList;
	
	List<FeedbackTicketPojo> gridModel;
	private String fromDate;
	private String toDate;
	private String mode;
	private String patType;
	private String actionStatus;
	
	
	private String spec;
	private String docName;
	
	private Map<String, String> actionNameMap;
	
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
	return SUCCESS;
	}
	
	public String beforeCustomerAdd()
	{
	try
	{
	
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	setCustomerAddPageDataFields();
	
	
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
	//gudiya 6-11-13
	private List<AdminDeptAction> adminList;
	List<AdminDeptAction> admindeptDataShow=new ArrayList<AdminDeptAction>();
	List<AdminDeptAction> gridModel2;
	public String viewAdminDeptData() 
	{
	boolean valid=ValidateSession.checkSession();
        if(valid)
        {
        	try
        	{
        	//	System.out.println("Hello>>>>>>>>>viewAdminDeptData() ");
        	 CommonOperInterface cbt=new CommonConFactory().createInterface();
        	 adminList=new ArrayList();
        	 StringBuilder qry=new StringBuilder("");
        	 qry.append("select admindept.id,dept.deptName,admindept.userName,admindept.allote_date,admindept.allote_time from admin_dept_info as admindept "
                            +  "inner join department as dept on dept.id=admindept.admin_dept_id");
        	 adminList=cbt.executeAllSelectQuery(qry.toString(),connectionSpace);
        	// System.out.println("size of the Admin Dept>>>>>>"+qry.toString());
        	//	 System.out.println("Admin Dept  Sise>>>>>>>>>."+adminList.size());
        	 List<AdminDeptAction> dataList=new ArrayList<AdminDeptAction>();
        	 if(adminList!=null && adminList.size()>0)
        	 {
        	 
        	 for (Iterator iterator = adminList.iterator(); iterator .hasNext();) 
        	 {
        	 AdminDeptAction adDept=new AdminDeptAction();
        	 Object[] type = (Object[]) iterator.next();
        	 if(type[0]!=null)
                         {
        	 adDept.setId((type[0].toString()));
                         }
        	 if(type[1]!=null)
                         {
        	 adDept.setDept_Name((type[1].toString()));
                         }
        	 if(type[2]!=null)
                         {
        	 adDept.setUserName1((type[2].toString()));
                         }
        	 if(type[3]!=null)
                         {
        	 adDept.setAllote_date((type[3].toString()));
                         }
        	 if(type[4]!=null)
                         {
        	 adDept.setAllote_time((type[4].toString()));
                         }
        	 dataList.add(adDept);
        	 }
        	
        	//	 System.out.println("dataList>>>>>>>>>>>"+dataList.size());
        	 }
        	  setGridModel2(dataList);
        	  return SUCCESS;
        	
        	}catch (Exception e) {
	
        	e.printStackTrace();
                return ERROR;
	}
        }
        else
        {
            return LOGIN;
        }
	}
	


	public void setCustomerAddPageDataFields()
	{
	List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_feedbackView_configuration",accountID,connectionSpace,"",0,"table_name","feedbackView_configuration");
	if(offeringLevel1!=null)
	{
	customerTextBox=new LinkedHashMap<String, String>();
	for(GridDataPropertyView  gdp:offeringLevel1)
	{
	if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
	{
	customerTextBox.put(gdp.getColomnName(), gdp.getHeadingName());
	
	}
	else if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
	{
	
	if(gdp.getColomnName().equalsIgnoreCase("title"))
	{
	title=gdp.getHeadingName();
	ddTrue=true;
	}
	
	
	}
	else if(gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
	{
	customerCalender.put(gdp.getColomnName(), gdp.getHeadingName());
	
	
	}
	}
	}
	}
	
	public String beforeTakeAction()
	{
	boolean valid=ValidateSession.checkSession();
	if(valid)
	{
	try
	{
	actionNameMap=new HashMap<String,String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuffer buffer=new StringBuffer("select id,name from actioninfo order by name asc");
	List data=cbt.executeAllSelectQuery(buffer.toString(),connectionSpace);
	if(data!=null && data.size()>0)
	{
	CounterPojo pojo=new CounterPojo();
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if(object!=null)
	{
	if(object[0]!=null && object[1]!=null)
	{
	actionNameMap.put(object[0].toString(),object[1].toString());
	}
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
	
	public String viewPositiveCustomerInGridM()
    {
        boolean valid=ValidateSession.checkSession();
        if(valid)
        {
            try
            {
                CommonOperInterface cbt=new CommonConFactory().createInterface();
                {
                	String deptLevel = (String)session.get("userDeptLevel");
                	String userType=(String)session.get("userTpe");
                	String loggedEmpId="";
                	List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
                	if (empData!=null && empData.size()>0)
                	{
                		for (Iterator iterator = empData.iterator(); iterator.hasNext();) 
                		{
                			Object[] object = (Object[]) iterator.next();
                			loggedEmpId=object[5].toString();
                		}
                	}
                	
                	 custDataList=new ArrayList<CustomerPojo>();
                     StringBuilder queryNew2=new StringBuilder("");
                     if(getFlage()!=null && getFlage().equalsIgnoreCase("positive"))
                     {
                    	// queryNew2.append("select feedData.id,feedData.problem,feedData.compType,feedData.refNo,feedData.clientId,feedData.clientName,feedData.centerCode,feedData.centreName,feedData.mobNo,feedData.remarks,feedData.openDate,feedData.openTime,feedData.actionDate,feedData.actionTime,feedData.resWaitDate,feedData.resWaitTime,feedData.contactDate,feedData.contactTime,feedData.tempDate,feedData.tempTime,feedData.resDate,feedData.resTime,feedData.kword,feedData.ip,feedData.handledBy,feedData.emailId from feedbackdata as feedData where feedData.targetOn='Yes' order by feedData.openDate DESC");
                      //   queryNew2.append("select feedData.problem,feedData.compType,feedData.refNo,feedData.clientId,feedData.clientName,feedData.centerCode,feedData.centreName,feedData.mobNo,feedData.remarks,feedData.openDate,feedData.openTime,feedData.actionDate,feedData.actionTime,feedData.resWaitDate,feedData.resWaitTime,feedData.contactDate,feedData.contactTime,feedData.tempDate,feedData.tempTime,feedData.resDate,feedData.resTime,feedData.kword,feedData.ip,feedData.handledBy,feedData.emailId from feedbackdata as feedData where feedData.targetOn='Yes' order by feedData.openDate DESC");
                    	 queryNew2.append("select feedData.clientId,feedData.clientName,feedData.mobNo,feedData.emailId,feedData.compType,feedData.handledBy,feedData.centerCode,feedData.mode," +
                    	 		" feedData.centreName,feedData.serviceBy,feedData.refNo,feedData.userName,feedData.id " +
                    	 		" ,feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.status, feedData.openDate,feedData.openTime,feedData.patientId,feedData.companytype,feedData.discharge_datetime from feedbackdata as feedData " +
                    	 		"  inner join feedback_ticket as feedbt on feedbt.feed_data_id=feedData.id " +
                    	 		"  inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id " +
                    	 		" where feedData.targetOn='Yes'");
                    	 
                    	 if(getFromDate()!=null && getToDate()!= null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
                         {
                         	String str[]=getFromDate().split("-");
                         	if(str[0]!=null && str[0].length()>3)
                         	{
                         		queryNew2.append(" and feedData.openDate between '"+((getFromDate()))+"' and '"+((getToDate()))+"'");
                         	}
                         	else
                        	{
                         		queryNew2.append(" and feedData.openDate between '"+(DateUtil.convertDateToUSFormat(getFromDate()))+"' and '"+(DateUtil.convertDateToUSFormat(getToDate()))+"'");
                        	}
                         	
                         }
                    	 else
                    	 {
                    		 queryNew2.append(" and feedData.openDate between '"+(DateUtil.getNextDateAfter(-6))+"' and '"+(DateUtil.getCurrentDateUSFormat())+"'");
                    	 }
                    	 
                    	 
                    	 if(getPatType()!=null && !getPatType().equalsIgnoreCase("-1"))
                         {
                    		 queryNew2.append(" and feedData.compType = '"+getPatType()+"'");
                         }
                    	 
                    	 
                    	 if(getMode()!=null &&!getMode().equalsIgnoreCase("-1"))
                    	 {
                    		 queryNew2.append(" and feedData.mode = '"+getMode()+"'");
                    	 }
                     
                    	 if(getSpec()!=null && !getSpec().equalsIgnoreCase("-1"))
                         {
                    		 queryNew2.append(" and feedData.centreName = '"+getSpec()+"'");
                         }
                         
                         if(getDocName()!=null && !getDocName().equalsIgnoreCase("-1"))
                         {
                        	 queryNew2.append(" and feedData.serviceBy = '"+getDocName()+"'");
                         }
                     }
                     else  if(getFlage()!=null && getFlage().equalsIgnoreCase("negative"))
                     {
                    	 queryNew2.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
                    	 	" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
                    	 	" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id," +
                    	 	" feedData.compType,feedData.handledBy," +
                    	 	" feedData.status,feedData.mode,feedData.userName,feedData.serviceBy,feedData.patientId,feedData.companytype,feedData.discharge_datetime from feedback_ticket as feedbt" +
                    	 	" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
                    	 	" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedData.targetOn='No' ");
                    	 
                    	 if(getFromDate()!=null && getToDate()!= null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
                         {
                         	String str[]=getFromDate().split("-");
                         	if(str[0]!=null && str[0].length()>3)
                         	{
                         		queryNew2.append(" and feedData.openDate between '"+((getFromDate()))+"' and '"+((getToDate()))+"'");
                         	}
                         	else
                        	{
                         		queryNew2.append(" and feedData.openDate between '"+(DateUtil.convertDateToUSFormat(getFromDate()))+"' and '"+(DateUtil.convertDateToUSFormat(getToDate()))+"'");
                        	}
                         	
                         }
                    	 else
                    	 {
                    		 queryNew2.append(" and feedData.openDate between '"+(DateUtil.getNextDateAfter(-6))+"' and '"+(DateUtil.getCurrentDateUSFormat())+"'");
                    	 }
                    	 
                    	 if(getPatType()!=null && !getPatType().equalsIgnoreCase("-1"))
                         {
                    		 queryNew2.append(" and feedData.compType = '"+getPatType()+"'");
                         }
                    	 
                    	 
                    	 if(getMode()!=null &&!getMode().equalsIgnoreCase("-1"))
                    	 {
                    		 queryNew2.append(" and feedData.mode = '"+getMode()+"'");
                    	 }
                    	 
                    	 
                    	 if(getSpec()!=null && !getSpec().equalsIgnoreCase("-1"))
                         {
                    		 queryNew2.append(" and feedData.centreName = '"+getSpec()+"'");
                         }
                         
                         if(getDocName()!=null && !getDocName().equalsIgnoreCase("-1"))
                         {
                        	 queryNew2.append(" and feedData.serviceBy = '"+getDocName()+"'");
                         }
                         
                         /*if(userType.equalsIgnoreCase("N"))
                         {
                        	 //loggedEmpId
                        	 queryNew2.append(" and feedData.handledBy = '"+loggedEmpId+"'");
                         }*/
                       //  queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.clientName,feedData.mobNo as feedback_ticketId from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id order by feedbck.open_date DESC");
                     }
                     else  if(getFlage()!=null && getFlage().equalsIgnoreCase("actionTaken"))
                     {
                    	 queryNew2.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
                     	 	" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
                     	 	" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," +
                     	 	" feedData.compType,feedData.handledBy,feedData.remarks," +
                     	 	" feedData.kword,feedData.ip,feedData.status,actionTaken.actionName, actionTaken.actionDate,actionTaken.actionTime from feedback_ticket as feedbt" +
                     	 	" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
                     	 	" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id" +
                     	 	" inner join feedback_actiontaken as actionTaken on actionTaken.feedDataId=feedData.id "+
                     	 	" where feedbck.status='Resolved'");
                     }
                     
                    if(userType!=null && userType.equalsIgnoreCase("N"))
          			{
                 		queryNew2.append(" and feedData.handledBy='"+loggedEmpId+"' ");
          			}
                    
                    if(getActionStatus()!=null && !getActionStatus().equalsIgnoreCase("-1") && !getActionStatus().equalsIgnoreCase(""))
                    {
                    	queryNew2.append(" and  feedData.status='"+getActionStatus()+"'");
                    }
                    
                    queryNew2.append(" order by feedData.id DESC");
                   
           //         System.out.println("Pos Neg Query::"+queryNew2);
                     FeedbackReportHelper helper=new FeedbackReportHelper(); 
                     ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
                     List<CustomerPojo> tempList=new ArrayList<CustomerPojo>();
                     int i=1;
                     if(ticketDataList!=null && ticketDataList.size()>0)
                        {
                    	 FeedbackUniversalHelper feedHelp=new FeedbackUniversalHelper();
                    	 FeedbackHelper feedHelper=new FeedbackHelper();
                            for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
                            {
                            	CustomerPojo feed=new CustomerPojo();
                                Object[] type = (Object[]) iterator.next();
                                
                                if(getFlage()!=null && (getFlage().equalsIgnoreCase("positive")))
                                {
                                	feed.setAction("Take Action");
                                    if(type[0]!=null)
                                    {
                                        feed.setClientId(type[0].toString());
                                    }
                                    else
                                    {
                                    	feed.setClientId("NA");
                                    }
                                    
                                    
                                    if(type[1]!=null)
                                    {
                                        feed.setClientName(DateUtil.makeTitle(type[1].toString()));
                                    }
                                    else
                                    {
                                    	feed.setClientName("NA");
                                    }
                                    
                                    if(type[2]!=null)
                                    {
                                        feed.setMobNo(type[2].toString());
                                       if(feed.getMobNo()!=null)
                                       {
                                    	   feed.setActionCounter(String.valueOf(feedHelper.getCustomerFeedbackDetails(connectionSpace,feed.getMobNo())));
                                       }
                                       else
                                       {
                                    	   feed.setActionCounter("0");
                                       }
                                    }
                                    else
                                    {
                                    	feed.setMobNo("NA");
                                    	feed.setActionCounter("0");
                                    }
                                    
                                    if(type[3]!=null)
                                    {
                                        feed.setEmailId(type[3].toString());
                                    }
                                    else
                                    {
                                    	feed.setEmailId("NA");
                                    }
                                    
                                    if(type[4]!=null)
                                    {
                                        feed.setCompType((type[4].toString()));
                                    }
                                    else
                                    {
                                    	feed.setCompType("NA");
                                    }
                                    
                                    if(type[5]!=null)
                                    {
                                    	String handledBy=new HelpdeskUniversalAction().getData("employee_basic", "empName", "id",type[5].toString());
                                        feed.setHandledBy(DateUtil.makeTitle(handledBy));
                                    }
                                    else
                                    {
                                    	feed.setHandledBy("NA");
                                    }
                                    
                                    if(type[6]!=null)
                                    {
                                        feed.setCenterCode(type[6].toString());
                                    }
                                    else
                                    {
                                    	feed.setCenterCode("NA");
                                    }
                                    
                                    if(type[7]!=null)
                                    {
                                    	if(type[7].toString().equalsIgnoreCase("Sms"))
                                		{
                                			feed.setMode("SMS");
                                		}
                                		else
                                		{
                                			feed.setMode(type[7].toString());
                                		}
                                    }
                                    else
                                    {
                                    	feed.setMode("NA");
                                    }
                                    
                                    if(type[8]!=null)
                                    {
                                    	feed.setCentreName(type[8].toString());
                                    }
                                    
                                    if(type[9]!=null)
                                    {
                                    	feed.setServiceBy(type[9].toString());
                                    }
                                    
                                    if(type[10]!=null)
                                    {
                                    	feed.setRefNo(type[10].toString());
                                    }
                                    
                                    if(type[11]!=null)
                                    {
                                    	
                                    	feed.setUserName(feedHelp.getLoginIdEmpName(connectionSpace,type[11].toString()));
                                    }
                                    
                                    if(type[12]!=null)
                                    {
                                    	feed.setId(Integer.parseInt(type[12].toString()));
                                    }
                                    
                                    if(type[13]!=null)
                                    {
                                    	feed.setFeedTicketId(Integer.parseInt(type[13].toString()));
                                    }
                                    
                                    if(type[14]!=null)
                                    {
                                    	feed.setTicketNo((type[14].toString()));
                                    }
                                    
                                    if(type[15]!=null)
                                    {
                                    	feed.setStatus((type[15].toString()));
                                    }
                                    
                                    if(type[16]!=null)
                                    {
                                    	feed.setOpenDate((DateUtil.convertDateToIndianFormat(type[16].toString())));
                                    }
                                    else
                                    {
                                    	feed.setOpenDate("NA");
                                    }
                                    
                                    if(type[17]!=null)
                                    {
                                    	feed.setOpenTime(type[17].toString().toString().substring(0,5));
                                    }
                                    
                                    if(type[18]!=null)
                                    {
                                    	feed.setPatientId(type[18].toString());
                                    }
                                    
                                    if(type[19]!=null)
                                    {
                                    	feed.setCompanytype(type[19].toString());
                                    }
                                    
                                    if(type[19]!=null)
                                    {
                                    	feed.setCompanytype(type[19].toString());
                                    }
                                    
                                    if(type[20]!=null)
                                    {
                                    	feed.setDischarge_datetime(type[20].toString());
                                    }
                                }
                                else if(getFlage()!=null && (getFlage().equalsIgnoreCase("negative")))
                                {
                                	feed.setAction("Take Action");
                                	
                                	if(type[0]!=null)
                                	{
                                		feed.setFeedTicketId(Integer.parseInt(type[0].toString()));
                                		feed.setActionCounter(fHelp.getCounterForNegFeedback(connectionSpace,type[1].toString()));
                                	}
                                	
                                	if(type[1]!=null)
                                	{
                                		feed.setTicketNo(type[1].toString());
                                	}
                                	else
                                    {
                                    	feed.setTicketNo("NA");
                                    }
                                	
                                	if(type[2]!=null)
                                	{
                                		feed.setOpenDate(DateUtil.convertDateToIndianFormat(type[2].toString()));
                                	}
                                	else
                                    {
                                    	feed.setOpenDate("NA");
                                    }
                                	
                                	if(type[3]!=null)
                                	{
                                		feed.setOpenTime(type[3].toString().toString().substring(0,5));
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
                                	feed.setLevel(DateUtil.makeTitle(type[6].toString()));
                                	}
                                	
                                	if(type[7]!=null)
                                	{
                                	feed.setClientId(DateUtil.makeTitle(type[7].toString()));
                                	}
                                	
                               /* 	if(feed.getClientId()!=null)
                                    {
                                    	feed.setActionCounter(helper.getFeedbackCounter(connectionSpace,feed.getClientId()));
                                    }*/
                                	
                                	if(type[8]!=null)
                                	{
                                	feed.setClientName(DateUtil.makeTitle(type[8].toString()));
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
                                	feed.setRefNo(type[11].toString());
                                	}
                                	
                                	if(type[12]!=null)
                                	{
                                	feed.setCentreCode(DateUtil.makeTitle(type[12].toString()));
                                	}
                                	
                                	if(type[13]!=null)
                                	{
                                	feed.setCentreName(DateUtil.makeTitle(type[13].toString()));
                                	}
                                	
                                	if(type[14]!=null)
                                	{
                                		feed.setId(Integer.parseInt(type[14].toString()));
                                	}
                                	
                                	if(type[15]!=null)
                                	{
                                	feed.setComplaintType((type[15].toString()));
                                	}
                                	
                                	if(type[16]!=null)
                                	{
                                		String handledBy=new HelpdeskUniversalAction().getData("employee_basic", "empName", "id",type[16].toString());
                                		feed.setHandledBy(DateUtil.makeTitle(handledBy));
                                	}
                                	
                                
                                	if(type[17]!=null)
                                	{
                                	feed.setStatus(type[17].toString());
                                	}
                                	if(type[18]!=null)
                                	{
                                		if(type[18].toString().equalsIgnoreCase("Sms"))
                                		{
                                			feed.setMode("SMS");
                                		}
                                		else
                                		{
                                			feed.setMode(type[18].toString());
                                		}
                                	}
                                	if(type[19]!=null)
                                	{
                                		feed.setUserName(feedHelp.getLoginIdEmpName(connectionSpace,type[19].toString()));
                                	}
                                	if(type[20]!=null)
                                	{
                                		feed.setServiceBy(type[20].toString());
                                	}
                                	if(type[21]!=null)
                                    {
                                    	feed.setPatientId(type[21].toString());
                                    }
                                	
                                	if(type[22]!=null)
                                    {
                                    	feed.setCompanytype(type[22].toString());
                                    }
                                	
                                	if(type[23]!=null)
                                    {
                                    	feed.setDischarge_datetime(type[23].toString());
                                    }
                                }
                                else if(getFlage()!=null && (getFlage().equalsIgnoreCase("actionTaken")))
                                {
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
                                	feed.setActionTaken(type[22].toString());
                                	}
                                	
                                	if(type[23]!=null)
                                	{
                                	feed.setActionDate(DateUtil.convertDateToIndianFormat(type[23].toString()));
                                	}
                                	
                                	if(type[24]!=null)
                                	{
                                	feed.setActionTime(type[24].toString().substring(0,5));
                                	}
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
	
	
	 public String beforeTotalFeedViewInGrid()
	    {

	    	boolean valid=ValidateSession.checkSession();
	    	if(valid)
	    	{
	    		try
	    		{
	    			if(getId()!=null)
	    			{
	    			//	System.out.println("Id is as >>>>>>"+getId());
	        			CommonOperInterface cbt=new CommonConFactory().createInterface();
	        			String mobNo=null;
	        			List dataList=cbt.executeAllSelectQuery("select mobNo from feedbackdata where id='"+getId()+"'", connectionSpace);
	        			if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
	        			{
	        				mobNo=dataList.get(0).toString();
	        			}
	        			
	        			if(mobNo!=null)
	        			{
	        				StringBuilder builder=new StringBuilder("select clientId,clientName,centerCode,compType,mode,openDate,targetOn,comment,actionComments,status from feedbackdata where mobNo='"+mobNo+"'");

	            			List data=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
	            			if(data!=null && data.size()>0)
	            			{
	            				List<CustomerPojo> tempList=new ArrayList<CustomerPojo>();
	            				for (Iterator iterator = data.iterator(); iterator.hasNext();)
	            				{
	            					Object[] type = (Object[]) iterator.next();
	            					if(type!=null)
	            					{
	            						CustomerPojo pojo=new CustomerPojo();
	            						if(type[0]!=null)
	            						{
	            							pojo.setClientId(type[0].toString());
	            						}
	            						else
	            						{
	            							pojo.setClientId("NA");
	            						}
	            						
	            						if(type[1]!=null)
	            						{
	            							pojo.setClientName(type[1].toString());
	            						}
	            						else
	            						{
	            							pojo.setClientName("NA");
	            						}
	            						
	            						if(type[2]!=null)
	            						{
	            							pojo.setCentreCode(type[2].toString());
	            						}
	            						else
	            						{
	            							pojo.setCentreCode("NA");
	            						}
	            						
	            						if(type[3]!=null)
	            						{
	            							pojo.setComplaintType(type[3].toString());
	            						}
	            						else
	            						{
	            							pojo.setComplaintType("NA");
	            						}
	            						
	            						if(type[4]!=null)
	            						{
	            							pojo.setMode(type[4].toString());
	            						}
	            						else
	            						{
	            							pojo.setMode("NA");
	            						}
	            						
	            						if(type[5]!=null)
	            						{
	            							pojo.setOpenDate(type[5].toString());
	            						}
	            						else
	            						{
	            							pojo.setOpenDate("NA");
	            						}
	            						
	            						if(type[6]!=null)
	            						{
	            							if(type[6].toString().equalsIgnoreCase("No"))
	            							{
	            								pojo.setFeedback("Negative");
	            							}
	            							else
	            							{
	            								pojo.setFeedback("Positive");
	            							}
	            						}
	            						else
	            						{
	            							pojo.setFeedback("NA");
	            						}
	            						
	            						if(type[7]!=null)
	            						{
	            							pojo.setComments(type[7].toString());
	            						}
	            						else
	            						{
	            							pojo.setComments("NA");
	            						}
	            						
	            						if(type[8]!=null)
	            						{
	            							pojo.setActionComments(type[8].toString());
	            						}
	            						else
	            						{
	            							pojo.setActionComments("NA");
	            						}
	            						tempList.add(pojo);
	            						setCustDataList(tempList);
	            					}
								}
	            			}
	        			}
	        		//	System.out.println("Cust size is as ::::"+getCustDataList().size());
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
	 
	public String addCustomerData()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List<GridDataPropertyView>statusColName=Configuration.getConfigurationData("mapped_feedbackView_configuration",accountID,connectionSpace,"",0,"table_name","feedbackView_configuration");
	if(statusColName!=null)
	{
	//create table query based on configuration
	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	InsertDataTable ob=null;
	boolean status=false;
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	boolean userTrue=false;
	boolean dateTrue=false;
	boolean timeTrue=false;
	boolean uniqueidTrue=false;
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
	
	 cbt.createTable22("feedbackview",Tablecolumesaaa,connectionSpace);
	 
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
	status=cbt.insertIntoTable("customerdetails",insertData,connectionSpace); 
	 if(status)
	 {
	 addActionMessage("Customer Added Successfully!!!");
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
	

	public String beforeCustomerView()
	{
	try
	{
	if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("takeAction"))
	{
	setMainHeaderName("Feedback >> Take Action");
	}
	else
	{
	setMainHeaderName("Overall Summary >> View");
	}
	
	/*if(userName==null || userName.equalsIgnoreCase(""))
	{
	//return LOGIN;
	}
*/	
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	public void setCustomerViewProp()
	{
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);
	
	List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedbackView_configuration",accountID,connectionSpace,"",0,"table_name","feedbackView_configuration");
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
	
	public String viewCustomerInGridSMS()
	{


	try
	{CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(userName==null || userName.equalsIgnoreCase(""))
	{
	return LOGIN;
	}
	else
	{
	// System.out.println("Level is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchFlag());
	 StringBuilder queryNew2=new StringBuilder("");
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedbt.id as feedback_ticketId from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbt.feedTicketNo like 'S%' order by feedbck.open_date DESC"); 
	//	 System.out.println("Querry is as <>>>>>>>>>>>>><>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+queryNew2.toString());
	 ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
	//System.out.println("size of the list ticketDataList>>>>>>"+ticketDataList.size());
	 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
	if(ticketDataList!=null && ticketDataList.size()>0)
	{
	for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
	{
	FeedbackTicketPojo feed=new FeedbackTicketPojo();
	Object[] type = (Object[]) iterator.next();
	
	if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("todayYes")||getSearchFlag().equalsIgnoreCase("yesterDayYes")||getSearchFlag().equalsIgnoreCase("totalYes")))
	{
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setFeedBy(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedByMob(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMailId(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setDoctorName(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setPurposeVisit(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[6].toString()));
	}
	if(type[7]!=null)
	{
	feed.setFeedTime(type[7].toString().substring(0,5));
	}	
	}
	else
	{
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setTicketNo(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedBy(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMob(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setFeedByMailId(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setDoctorName(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setPurposeVisit(type[6].toString());
	}
	if(type[7]!=null)
	{
	feed.setFeedLevel(type[7].toString());
	}
	if(type[8]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
	}
	if(type[9]!=null)
	{
	feed.setFeedTime(type[9].toString().substring(0,5));
	}
	if(type[10]!=null)
	{
	feed.setStatus(type[10].toString());
	}	
	if(type[11]!=null)
	{
	feed.setFeedTicketId(type[11].toString());
	}	
	}
	
	tempList.add(feed);
	
	}
	}
	//	System.out.println("size of the tempList>>>>>>>>>>>"+tempList.size());
	setTicketDataList(tempList);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
	
	
	}
	
	
	public String viewCustomerInGridTodays()
	{


	try
	{CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(userName==null || userName.equalsIgnoreCase(""))
	{
	return LOGIN;
	}
	else
	{
	//	 System.out.println("Level is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchFlag());
	 StringBuilder queryNew2=new StringBuilder("");
	 if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("todayYes"))
	 {
	 queryNew2.append("select feedData.id,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedData.date,feedData.time,feedData.comment from feedbackdata as feedData where feedData.date='"+DateUtil.getCurrentDateUSFormat()+"' && feedData.targetOn='Yes' order by feedData.date DESC"); 
	 }
	 else if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("todayNo"))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedData.comment from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedData.date='"+DateUtil.getCurrentDateUSFormat()+"' && feedData.targetOn='No' order by feedData.date DESC"); 
	 }
	 else if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("yesterDayYes"))
	 {
	 queryNew2.append("select feedData.id,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedData.date,feedData.time,feedData.comment from feedbackdata as feedData where feedData.date='"+DateUtil.getNewDate("day",-1,DateUtil.getCurrentDateUSFormat())+"' && feedData.targetOn='Yes'  order by feedData.date DESC");
	 }
	 else if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("yesterDayNo"))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedData.comment from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedData.date='"+DateUtil.getNewDate("day",-1,DateUtil.getCurrentDateUSFormat())+"' && feedData.targetOn='No' order by feedData.date DESC"); 
	 }
	 else if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("totalYes"))
	 {
	 queryNew2.append("select feedData.id,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedData.date,feedData.time,feedData.comment from feedbackdata as feedData where feedData.targetOn='Yes' order by feedData.date DESC");
	 }
	 else if(getSearchFlag()!=null && getSearchFlag().equalsIgnoreCase("totalNo"))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedData.comment from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedData.targetOn='No' order by feedData.date DESC"); 
	 }
	//	 System.out.println("Querry is as <>>>>>>>>>>>>><>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+queryNew2.toString());
	 ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
	// System.out.println("size of the list ticketDataList>>>>>>"+ticketDataList.size());
	 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
	if(ticketDataList!=null && ticketDataList.size()>0)
	{
	for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
	{
	FeedbackTicketPojo feed=new FeedbackTicketPojo();
	Object[] type = (Object[]) iterator.next();
	
	if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("todayYes")||getSearchFlag().equalsIgnoreCase("yesterDayYes")||getSearchFlag().equalsIgnoreCase("totalYes")))
	{
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setFeedBy(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedByMob(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMailId(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setDoctorName(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setPurposeVisit(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[6].toString()));
	}
	if(type[7]!=null)
	{
	feed.setFeedTime(type[7].toString().substring(0,5));
	}	
	if(type[8]!=null)
	{
	feed.setComments(type[8].toString());
	}	
	}
	else
	{
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setTicketNo(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedBy(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMob(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setFeedByMailId(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setDoctorName(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setPurposeVisit(type[6].toString());
	}
	if(type[7]!=null)
	{
	feed.setFeedLevel(type[7].toString());
	}
	if(type[8]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
	}
	if(type[9]!=null)
	{
	feed.setFeedTime(type[9].toString().substring(0,5));
	}
	if(type[10]!=null)
	{
	feed.setStatus(type[10].toString());
	}	
	if(type[11]!=null)
	{
	feed.setComments(type[11].toString());
	}
	}
	
	tempList.add(feed);
	
	}
	}
	
	Collections.reverse(tempList);
	
	setTicketDataList(tempList);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	public String viewCustomerInGridLevelWise()
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(userName==null || userName.equalsIgnoreCase(""))
	{
	return LOGIN;
	}
	else
	{
	 StringBuilder queryNew2=new StringBuilder("");
	
	 if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("OpenSMS")))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.status='Pending' && feedbt.feedTicketNo like 'S%' order by feedbck.open_date DESC");
	 }
	 else if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("closedSMS")))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.status='Resolved' && feedbt.feedTicketNo like 'S%' order by feedbck.open_date DESC");
	 }
	 else if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("takeAction")))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedbt.id as feedback_ticketId from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id order by feedbck.open_date DESC");
	 }
	 else if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("Level1SMS") || getSearchFlag().equalsIgnoreCase("Level2SMS") || getSearchFlag().equalsIgnoreCase("Level3SMS") || getSearchFlag().equalsIgnoreCase("Level4SMS")))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.level='"+getSearchFlag().substring(0,6)+"' && feedbt.feedTicketNo like 'S%' order by feedbck.open_date DESC");
	 }
	 else if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("Level1TAB") || getSearchFlag().equalsIgnoreCase("Level2TAB") || getSearchFlag().equalsIgnoreCase("Level3TAB") || getSearchFlag().equalsIgnoreCase("Level4TAB")))
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.level='"+getSearchFlag().substring(0,6)+"' && feedbt.feedTicketNo like 'T%' order by feedbck.open_date DESC");
	 }
	else
	 {
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.level='"+getSearchFlag()+"' order by feedbck.open_date DESC");
	 }
	 ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
	 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
	if(ticketDataList!=null && ticketDataList.size()>0)
	{
	for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
	{
	FeedbackTicketPojo feed=new FeedbackTicketPojo();
	Object[] type = (Object[]) iterator.next();
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setTicketNo(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedBy(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMob(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setFeedByMailId(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setDoctorName(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setPurposeVisit(type[6].toString());
	}
	if(type[7]!=null)
	{
	feed.setFeedLevel(type[7].toString());
	}
	if(type[8]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
	}
	if(type[9]!=null)
	{
	feed.setFeedTime(type[9].toString().substring(0,5));
	}
	if(type[10]!=null)
	{
	feed.setStatus(type[10].toString());
	}	
	if(getSearchFlag()!=null && (getSearchFlag().equalsIgnoreCase("takeAction")))
	{
	if(type[11]!=null)
	{
	feed.setFeedTicketId(type[11].toString());
	}
	}
	
	tempList.add(feed);
	}
	}
	setTicketDataList(tempList);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
	
	}
	
	
	public String viewCustomerInGridClosed()
	{
	try
	{CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(userName==null || userName.equalsIgnoreCase(""))
	{
	return LOGIN;
	}
	else
	{
	 
	 StringBuilder queryNew2=new StringBuilder("");
	//	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbck.status='Resolved' && feedbt.feedTicketNo like 'Tab%' order by feedbck.resolve_date ");
	
	 queryNew2.append("select feedData.id, feedbck.ticket_no,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,act.fir from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id inner join actiontaken as act on act.feedDataId=feedbt.feed_data_id order by feedbck.open_date desc");
	 
	 
	 ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
	
	 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
	if(ticketDataList!=null && ticketDataList.size()>0)
	{
	for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
	{
	FeedbackTicketPojo feed=new FeedbackTicketPojo();
	Object[] type = (Object[]) iterator.next();
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setTicketNo(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedBy(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMob(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setFeedByMailId(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setDoctorName(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setPurposeVisit(type[6].toString());
	}
	if(type[7]!=null)
	{
	feed.setFeedLevel(type[7].toString());
	}
	if(type[8]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
	}
	if(type[9]!=null)
	{
	feed.setFeedTime(type[9].toString().substring(0,5));
	}
	if(type[10]!=null)
	{
	feed.setStatus(type[10].toString());
	}
	if(type[11]!=null)
	{
	feed.setFir(type[11].toString());
	}
	tempList.add(feed);
	
	}
	}
	setTicketDataList(tempList);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewCustomerInGrid()
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	if(userName==null || userName.equalsIgnoreCase(""))
	{
	return LOGIN;
	}
	else
	{
	ticketDataList=new ArrayList();
	 StringBuilder queryNew2=new StringBuilder("");
	 queryNew2.append("select feedData.id, feedbt.feedTicketNo,feedData.name,feedData.mobileNo,feedData.emailId,feedData.docterName,feedData.purposeOfVisit ,feedbck.level,feedbck.open_date,feedbck.open_time,feedbck.status,feedbt.id as feedback_ticketId  from feedback_ticket as feedbt inner join feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where feedbt.feedTicketNo like 'T%' && feedbck.status='Pending' order by feedbck.open_date DESC");
	 ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
	 List<FeedbackTicketPojo> tempList=new ArrayList<FeedbackTicketPojo>();
	if(ticketDataList!=null && ticketDataList.size()>0)
	{
	for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
	{
	FeedbackTicketPojo feed=new FeedbackTicketPojo();
	Object[] type = (Object[]) iterator.next();
	if(type[0]!=null)
	{
	feed.setId(Integer.parseInt(type[0].toString()));
	}
	if(type[1]!=null)
	{
	feed.setTicketNo(type[1].toString());
	}
	if(type[2]!=null)
	{
	feed.setFeedBy(type[2].toString());
	}
	if(type[3]!=null)
	{
	feed.setFeedByMob(type[3].toString());
	}
	if(type[4]!=null)
	{
	feed.setFeedByMailId(type[4].toString());
	}
	if(type[5]!=null)
	{
	feed.setDoctorName(type[5].toString());
	}
	if(type[6]!=null)
	{
	feed.setPurposeVisit(type[6].toString());
	}
	if(type[7]!=null)
	{
	feed.setFeedLevel(type[7].toString());
	}
	if(type[8]!=null)
	{
	feed.setFeedDate(DateUtil.convertDateToIndianFormat(type[8].toString()));
	}
	if(type[9]!=null)
	{
	feed.setFeedTime(type[9].toString().substring(0,5));
	}
	if(type[10]!=null)
	{
	feed.setStatus(type[10].toString());
	}	
	
	if(type[11]!=null)
	{
	feed.setFeedTicketId(type[11].toString());
	}
	tempList.add(feed);
	}
	}
	setTicketDataList(tempList);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	public String viewModifyCustomer()
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
	cbt.updateTableColomn("customerdetails", wherClause, condtnBlock,connectionSpace);
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
	cbt.deleteAllRecordForId("customerdetails", "id", condtIds.toString(), connectionSpace);
	}
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}
	return SUCCESS;
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

	

	public Map<String, String> getCustomerTextBox() {
	return customerTextBox;
	}

	public void setCustomerTextBox(Map<String, String> customerTextBox) {
	this.customerTextBox = customerTextBox;
	}

	public String getTitle() {
	return title;
	}

	public void setTitle(String title) {
	this.title = title;
	}

	public String getDob() {
	return dob;
	}

	public void setDob(String dob) {
	this.dob = dob;
	}

	public String getDom() {
	return dom;
	}

	public void setDom(String dom) {
	this.dom = dom;
	}

	public boolean isDobTrue() {
	return dobTrue;
	}

	public void setDobTrue(boolean dobTrue) {
	this.dobTrue = dobTrue;
	}

	public boolean isDomTrue() {
	return domTrue;
	}

	public void setDomTrue(boolean domTrue) {
	this.domTrue = domTrue;
	}

	public Map<String, String> getCustomerCalender() {
	return customerCalender;
	}

	public void setCustomerCalender(Map<String, String> customerCalender) {
	this.customerCalender = customerCalender;
	}

	public List<FeedbackTicketPojo> getTicketDataList() {
	return ticketDataList;
	}

	public void setTicketDataList(List<FeedbackTicketPojo> ticketDataList) {
	this.ticketDataList = ticketDataList;
	}

	public List<FeedbackTicketPojo> getGridModel() {
	return gridModel;
	}

	public void setGridModel(List<FeedbackTicketPojo> gridModel) {
	this.gridModel = gridModel;
	}

	public String getSearchFlag() {
	return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
	this.searchFlag = searchFlag;
	}

	public String getFlage() {
	return flage;
	}

	public void setFlage(String flage) {
	this.flage = flage;
	}

	public List<CustomerPojo> getCustDataList() {
	return custDataList;
	}
	public void setCustDataList(List<CustomerPojo> custDataList) {
	this.custDataList = custDataList;
	}
	public Map<String, String> getActionNameMap() {
	return actionNameMap;
	}
	public void setActionNameMap(Map<String, String> actionNameMap) {
	this.actionNameMap = actionNameMap;
	}

	public List<AdminDeptAction> getAdminList() {
	return adminList;
	}

	public void setAdminList(List<AdminDeptAction> adminList) {
	this.adminList = adminList;
	}

	public List<AdminDeptAction> getGridModel2() {
	return gridModel2;
	}

	public void setGridModel2(List<AdminDeptAction> gridModel2) {
	this.gridModel2 = gridModel2;
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

	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPatType() {
		return patType;
	}
	public void setPatType(String patType) {
		this.patType = patType;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
}