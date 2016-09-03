package com.Over2Cloud.ctrl.universal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.UserRights;
import com.Over2Cloud.BeanUtil.RightsCategory;



@SuppressWarnings("serial")
public class UniversalAction extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	static final Log log = LogFactory.getLog(UniversalAction.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String level1=new String();
	private int levelOforganization=2;
	private Map<Integer, String>levelList=new HashMap<Integer, String>();
	private Map<Integer, String>level2=new HashMap<Integer, String>();
	private Map<Integer, String>level3=new HashMap<Integer, String>();
	private Map<Integer, String>level4=new HashMap<Integer, String>();
	private Map<Integer, String>level5=new HashMap<Integer, String>();
	private Map<String, String> empListData=new HashMap<String, String>();
	//ajax calling for getting the emp list based on the selected dept
	private String subDeptID;
	private String orgIdInLevel;
	private Map<Integer, String>deptList=new HashMap<Integer, String>();
	private Map<Integer, String> subDepartmentList=new HashMap<Integer, String>();
	private Map<Integer, String> designationList=new HashMap<Integer, String>();
	//for beforeuser management status for executing a query based on selected menu option
	private String designationFlag;
	private String flafSeleced;
	private Map<String, String> appList=new LinkedHashMap<String, String>();
	private String empFalg;
	private String id;
	private String empName;
	private String mobOne;
	private boolean SFA=true;
	private boolean HD=true;
	private String deptFlag;
	private String headerNameForDept;
	private String headerNameForSubDept;
	private String subDeptfalg;
	private String empModuleFalgForDeptSubDept;
	private int deptHierarchy=1;
	private String level1LabelName;
	private String level2LabelName;
	private String level3LabelName;
	private String level4LabelName;
	private String level5LabelName;
	private String deptLabelName;
	private String subDeptLabelName;
	private int empLevelForAdd=1;
	//for KRA KPI flag for method
	private String kraKPIFlag;
	private Map<String, String>kraKPIParam=null;
	//emp basic add page generation
	private List<ConfigurationUtilBean>empBasicLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empBasicFile=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empBasicCalendr=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empBasicDropDown=new ArrayList<ConfigurationUtilBean>();
	private String mobNoLevelName=null;
	//emp personal add page generation
	private List<ConfigurationUtilBean>empPerLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empPerCalendr=new ArrayList<ConfigurationUtilBean>();
	private String empNameForOther;
	//emp work exprience add page generation
	private List<ConfigurationUtilBean>empWorkExpLevels=new ArrayList<ConfigurationUtilBean>();
	private List<ConfigurationUtilBean>empWorkExpCalendr=new ArrayList<ConfigurationUtilBean>();
	//emp bank add page generation
	private String accountTypeLevel;
	private List<ConfigurationUtilBean>empBankLevels=new ArrayList<ConfigurationUtilBean>();
	//for setting the create employee page accordian based on selected level of data
	private int empForAddBasic=0;
	private int empForAddPrsl=0;
	private int empForAddWorkQua=0;
	private int empForAddBaank=0;
	
	//for help desk modules
	private String feedbackDarft;
	private int levelOfFeedDraft=1;
	private String feedLevel1;
	private String feedLevel2;
	private String feedLevel3;
	private String shiftConf;
	private String shiftLevel;
	private String feedDarftLevelName;
	private String feedDarftCategoryLevelName;
	private Map<String, String> fdSubCategoryLevelName=new LinkedHashMap<String, String>();
	private Map<String, String> fdSubCategoryLevelNameForDate=new LinkedHashMap<String, String>();
	private Map<String, String> shiftLevelName = new LinkedHashMap<String, String>();
	private Map<String, String> shiftLevelNameForDate = new LinkedHashMap<String, String>();
	
	private Map<String, String> deptDataOther=new LinkedHashMap<String, String>();
	private Map<String, String> subDeptOtherData=new LinkedHashMap<String, String>();
	private Map<String, String> designastionTextBox=new LinkedHashMap<String, String>();
	private Map<String, String> designastionTextDropDown=new LinkedHashMap<String, String>();
	private ArrayList<RightsCategory> userRightsList = null;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private boolean statusFlag=false;
	Map<Integer,String> officeMap=null;
	Map<String,String> groupMap=null;
	private List<ConfigurationUtilBean> contactDD;
	private Map<Integer, String> subGroupMap;
	private List<ConfigurationUtilBean> subDeptDD;
	private List<ConfigurationUtilBean> subdeptTextBox;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	/*String offeringLevel=(String)session.get("offeringLevel");
	String orgLevel=(String)session.get("orgLevel");
	String deptLevel=(String)session.get("deptLevel");*/
	
	
	public List<ConfigurationUtilBean>  pageFieldsColumns  = null;
	private boolean hodStatus;
	private boolean mgtStatus;
	
	@SuppressWarnings("unchecked")
	public String beforeEmployee111()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	/*System.out.println("offeringLevel  "+offeringLevel+"  orgLevel  "+orgLevel+"  deptLevel  "+deptLevel);*/
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String levelsName=new String();
	List level1Tenp=new ArrayList<String>();
	level1Tenp.add("companyName");
	List level2Tenp=new ArrayList<String>();
	level2Tenp.add("id");
	level2Tenp.add("locCompanyName");
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	level1Tenp=cbt.viewAllDataEitherSelectOrAll("organization_level1",level1Tenp,connectionSpace);
	level2Tenp=cbt.viewAllDataEitherSelectOrAll("organization_level2",level2Tenp,connectionSpace);
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	
	//getting the current logined user organization level
	if(orgntnlevel!=null)
	{
	for (Object c : orgntnlevel) {
	Object[] dataC = (Object[]) c;
	levelOforganization= Integer.parseInt((String)dataC[0]);
	levelsName=(String)dataC[1];
	}
	}
	else
	{
	levelOforganization=Integer.parseInt("2");
	}
	//System.out.println("levelsName :::: "+levelsName);
	String tempName[]=levelsName.split("#");
	int j=0;
	/**
	 * Setting the Organization levels name with the taken hierarchy of organization
	 */
	for(int i=1;i<=levelOforganization;i++)
	{
	levelList.put(i, tempName[j]);
	j++;
	}
	if (tempName.length>0)
	level1LabelName=tempName[0];
	if (tempName.length>1)
	level2LabelName=tempName[1];
	//for organization name LEVEL1
	if(level1Tenp!=null)
	{
	for (Object c : level1Tenp) {
	Object dataC = (Object) c;
	level1=dataC.toString();
	}
	}
	//for level 2 data of hierarchy
	if(level2Tenp!=null)
	{
	for (Object c : level2Tenp) {
	Object[] dataC = (Object[]) c;
	level2.put((Integer)dataC[0],dataC[1].toString());
	}
	}
	/*if(level2Tenp==null)
	{
	 addActionMessage("Ooops You Must Create Organization On "+level2LabelName+" !");
	 return SUCCESS;
	}*/
	
	//for user management, setting the all purchased product in drop down for user management for making the user for selected product
	if(getFlafSeleced()!=null)
	{
	
	    AllConnection Conn=new ConnectionFactory("localhost");
	            AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
	            SessionFactory sessfact=Ob1.getSession();
	List<String>appListName=new ArrayList<String>();
	appListName.add("productCategory");
	appListName=cbt.viewAllDataEitherSelectOrAll("client_product",appListName,connectionSpace);
	String productList=new String();
	if(appListName!=null && appListName.size()>0)
	{
	for (Object c : appListName) {
	Object dataC = (Object) c;
	if(dataC!=null)
	productList=dataC.toString();
	}
	}
	
	//setting the product list purchased via the client
	if(!productList.equalsIgnoreCase("") && productList!=null)
	{
	String productUniqIds=new String();
	String tempProduct[]=productList.split("#");
	int i=1;
	for(String H:tempProduct)
	{
	if(!H.equalsIgnoreCase(""))
	{
	if(i < tempProduct.length)
	productUniqIds=productUniqIds+"'"+H+"',";
	else
	productUniqIds=productUniqIds+"'"+H+"'";
	i++;
	}
	}
	if(!productUniqIds.equalsIgnoreCase("") && productUniqIds!=null)
	{
	StringBuilder query=new StringBuilder("");
	query.append("select App_code,App_name from apps_details where App_code in("+productUniqIds+")");
	List  data=new createTable().executeAllSelectQuery(query.toString(),sessfact);
	if(data!=null)
	{
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	appList.put(obdata[0].toString(),obdata[1].toString());
	}
	}
	}
	
	}
	//setting the according in user create page based on selected product
	if(!productList.contains("SF"))
	{
	SFA=false;
	}
	if(!productList.contains("Helpdesk"))
	{
	HD=false;
	}
	}
	
	if(getDeptFlag()!=null)
	{
	//if the dept flag value is not null set the name of dept levels and the header name on department accordin based on name
	setDepartmentAndSubDeptNames(1);
	setHeaderNameForDept(getHeaderNameForDept()+" >> Registration");
	}
	if(getSubDeptfalg()!=null)
	{
	//if the sub dept flag value is not null set the name of dept and sub dept levels and the header name on department accordin based on name
	setDepartmentAndSubDeptNames(2);
	setHeaderNameForDept(getHeaderNameForDept()+" >> Registration");
	//setting dyanamic name of dept and sub dept for sub dept
	}
	if(getEmpModuleFalgForDeptSubDept()!=null)
	{
	//if this flag value is not null set the name of dept, sub dept and designation
	setDepartmentAndSubDeptNames(3);
	//setting the level of employee data hierarchy
	//setting the levels name the viewe data
	
	setViewPageProps();
	}
	
	if(getKraKPIFlag()!=null)
	{
	setKRAKPIParameterMap();
	}
	if(getDesignationFlag()!=null)
	{
	setDepartmentAndSubDeptNames(4);
	}
	//for help desk modules 
	if(getFeedbackDarft()!=null)
	{
	String feedlevels[]=null;
	List feedBackDraft=cbt.viewAllDataEitherSelectOrAll("mapped_feedbackdraft_level",colname,connectionSpace);
	//getting the current logined user organization level
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
	{
	feedLevel1=feedlevels[0]+" >> Registration";
	}
	if(levelOfFeedDraft>1)
	{
	feedLevel2=feedlevels[1]+" >> Registration";
	}
	if(levelOfFeedDraft>2)
	{
	feedLevel3=feedlevels[2]+" >> Registration";
	}
	setfeedBackDraftTags(levelOfFeedDraft);
	}
	
	}
	
	//Code for help desk modules 
	if(getShiftConf()!=null)
	  {
	 shiftLevel="Shift Registration";
	 setShiftTags();
	  }
	
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method beforeEmployee of class "+getClass(), e);
	 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
	 e.printStackTrace();
	 return ERROR;
	}
	return SUCCESS;
	}

	
	
	
	public String beforeCreateUser()
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	try
	{
	//groupMap=new LinkedHashMap<String, String>();
	//String user=Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
	/*StringBuilder query = new StringBuilder();
	query.append("SELECT emp.id,emp.regLevel FROM employee_basic AS emp ");
	query.append(" INNER JOIN useraccount AS uac ON uac.id=emp.useraccountid ");
	query.append(" WHERE uac.userID='"+user+"'");*/
	/*List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);*/
	String userType = (String)session.get("userTpe");
        	officeMap=new LinkedHashMap<Integer, String>();
        	String query =null;
        	String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
        	if (userType!=null && userType.equalsIgnoreCase("H")) 
        	{
        	hodStatus=true;
        	query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
	} 
        	else if(userType!=null && userType.equalsIgnoreCase("M"))
        	{
        	    mgtStatus = true;
        	    hodStatus = true;
        	query = "SELECT id,name FROM country_data ORDER BY name";
        	}
        	else 
        	{
        	query = "SELECT id,groupName FROM groupinfo WHERE regLevel = '"+s[1]+"' ORDER BY groupName";
	}
	if (query!=null)
	{
	List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && object[1] != null)
	{
	officeMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
	}
	}
	}
	}
	/*if(dataList==null || dataList.size()==0)
	{
	
            	statusFlag=true;
	
	}
	else
	{
	Object[] obj=(Object[]) dataList.get(0);
	String regLevel=obj[1].toString();
	List groupDataList=cbt.executeAllSelectQuery("SELECT id,groupName FROM groupinfo WHERE regLevel="+regLevel+" AND status='Active' ORDER BY groupName", connectionSpace);
	if(groupDataList!=null && groupDataList.size()>0)
	{
	for (Iterator iterator = groupDataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[1]!=null)
	{
	groupMap.put(object[0].toString(), object[1].toString());
	}
	
	}
	}
	
	}*/
	
	
	//Anoop, 31-7-2013
	//User management
	createLinks(id);
	return SUCCESS;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	
	}
	
	
	
	
	/**
	 * ajax method for getting the employee list based of the selected sub dept.
	 * This method is called in the user creation.
	 * Only the list of employee will return whose user is not created , once an employee mapped with a sub-dept is allot a user id,
	 * It will not shown in the user creation for user id allocation.
	 * @return
	 */
	private String tempUSer;
	public String getEmpWithSubDept()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List empSelectParam=new ArrayList<String>();
	empSelectParam.add("id");
	empSelectParam.add("empName");
	empSelectParam.add("useraccountid");
	Map<String, Object> temp=new HashMap<String, Object>();
	temp.put("subdept",getSubDeptID());
	if(getEmpFalg()!=null && getEmpFalg().equalsIgnoreCase("1"))
	{
	temp.put("status","0");
	}
	empSelectParam=cbt.viewAllDataEitherSelectOrAll("employee_basic",empSelectParam,temp,connectionSpace);
	if(empSelectParam!=null && empSelectParam.size()>0)
	{
	for (Object c : empSelectParam) {
	Object[] dataC = (Object[]) c;
	if(getTempUSer()!=null && dataC[2]==null && getEmpFalg().equalsIgnoreCase("0"))
	{
	//for user management , return list of employee whose user not created
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	else if(getEmpFalg().equalsIgnoreCase("1"))
	{
	//for employee history
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	else if(getEmpFalg().equalsIgnoreCase("0"))
	{
	//for all employee list based on sub dept
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getEmpWithSubDept of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	/**
	 * When an employee select in the user action it will take the id of that employee from the emp basic and
	 * ajax will called and return the selected employee mobile number and the the name of the employee which is read only in the JSP page.
	 */
	
	public String getEmpMobileNumber()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List empSelectParam=new ArrayList<String>();
	empSelectParam.add("mobOne");
	empSelectParam.add("empName");
	Map<String, Object> temp=new HashMap<String, Object>();
	temp.put("id",getId());
	empSelectParam=cbt.viewAllDataEitherSelectOrAll("employee_basic",empSelectParam,temp,connectionSpace);
	if(empSelectParam!=null && empSelectParam.size()>0)
	{
	for (Object c : empSelectParam) {
	Object[] dataC = (Object[]) c;
	if(dataC!=null)
	{
	mobOne=dataC[0].toString();
	empName=dataC[1].toString();
	}
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getEmpMobileNumber of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String getNextLowerLevel3ForDept()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	Map<String, Object> temp=new HashMap<String, Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List lowerLevel3=new ArrayList<String>();
	lowerLevel3.add("id");
	lowerLevel3.add("branchAddress");
	temp.put("locationCompany",getOrgIdInLevel());
	lowerLevel3=cbt.viewAllDataEitherSelectOrAll("organization_level3",lowerLevel3,temp,connectionSpace);
	if(temp!=null && temp.size()>0)
	{
	for (Object c : lowerLevel3) {
	Object[] dataC = (Object[]) c;
	level3.put((Integer)dataC[0], dataC[1].toString());
	}
	}
	String levelsName=new String();
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	//getting the current logined user organization level
	if(orgntnlevel!=null && orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel) {
	Object[] dataC = (Object[]) c;
	levelOforganization= Integer.parseInt((String)dataC[0]);
	levelsName=(String)dataC[1];
	}
	}
	String tempName[]=levelsName.split("#");
	if (tempName.length>2)
	level3LabelName=tempName[2];
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getNextLowerLevel3ForDept of class "+getClass(), e);
	}
	return SUCCESS;
	}
	
	
	public String getNextLowerLevel4ForDept()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	Map<String, Object> temp=new HashMap<String, Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List lowerLevel3=new ArrayList<String>();
	lowerLevel3.add("id");
	lowerLevel3.add("level4Address");
	temp.put("level3Registration",getOrgIdInLevel());
	lowerLevel3=cbt.viewAllDataEitherSelectOrAll("organization_level4",lowerLevel3,temp,connectionSpace);
	if(temp!=null && temp.size()>0)
	{
	for (Object c : lowerLevel3) {
	Object[] dataC = (Object[]) c;
	level4.put((Integer)dataC[0], dataC[1].toString());
	}
	}
	String levelsName=new String();
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	//getting the current logined user organization level
	if(orgntnlevel!=null && orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel) {
	Object[] dataC = (Object[]) c;
	levelOforganization= Integer.parseInt((String)dataC[0]);
	levelsName=(String)dataC[1];
	}
	}
	String tempName[]=levelsName.split("#");
	if (tempName.length>3)
	level4LabelName=tempName[3];
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getNextLowerLevel4ForDept of class "+getClass(), e);
	}
	return SUCCESS;
	}
	
	
	
	
	public String getNextLowerLevel5ForDept()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	Map<String, Object> temp=new HashMap<String, Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List lowerLevel3=new ArrayList<String>();
	lowerLevel3.add("id");
	lowerLevel3.add("level5Address");
	temp.put("level4Registration",getOrgIdInLevel());
	lowerLevel3=cbt.viewAllDataEitherSelectOrAll("organization_level5",lowerLevel3,temp,connectionSpace);
	if(lowerLevel3!=null && lowerLevel3.size()>0)
	{
	for (Object c : lowerLevel3) {
	Object[] dataC = (Object[]) c;
	level5.put((Integer)dataC[0], dataC[1].toString());
	}
	}
	String levelsName=new String();
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	//getting the current logined user organization level
	if(orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel) {
	Object[] dataC = (Object[]) c;
	levelOforganization= Integer.parseInt((String)dataC[0]);
	levelsName=(String)dataC[1];
	}
	}
	String tempName[]=levelsName.split("#");
	if (tempName.length>4)
	level5LabelName=tempName[4];
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getNextLowerLevel5ForDept of class "+getClass(), e);
	}
	return SUCCESS;
	}
	
	
	public void setDepartmentAndSubDeptNames(int deptFalgOrSubDept)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	String namesofDepts[]=new String[3];
	StringBuilder query=new StringBuilder("");
	query.append("select levelName, orgLevel from mapped_dept_level_config");
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null && deptFalgOrSubDept==1 || deptFalgOrSubDept==2)
	{
	String names=null;
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	if(obdata[0]!=null)
	names=obdata[0].toString();
	
	
	}
	namesofDepts=names.split("#");
	}
	if(deptFalgOrSubDept==1)
	{
	headerNameForDept=namesofDepts[0];
	deptLabelName=namesofDepts[0];
	List<String> data1=new ArrayList<String>();
	data1.add("mappedid");
	data1.add("table_name");
	Map<String, Object> temp=new HashMap<String, Object>();
	Map<String, String>returnResult=Configuration.getConfigurationIds("mapped_dept_config_param", data1, accountID, temp, connectionSpace,"T");
	
	if(returnResult!=null)
	{
	for (Map.Entry<String, String> entry : returnResult.entrySet()) {
	
	if(!entry.getKey().equals("deptName") && !entry.getKey().equals("mappedOrgnztnId")&& !entry.getKey().equals("userName")
	&& !entry.getKey().equals("date")&& !entry.getKey().equals("time"))
	{
	deptDataOther.put(entry.getKey(), entry.getValue());
	}
	}
	}
	//deptDataOther
	}
	if(deptFalgOrSubDept==2)
	{
	headerNameForDept=namesofDepts[1];
	deptLabelName=namesofDepts[0];
	subDeptLabelName=namesofDepts[1];
	List<String> data1=new ArrayList<String>();
	data1.add("mappedid");
	data1.add("table_name");
	Map<String, Object> temp=new HashMap<String, Object>();
	Map<String, String>returnResult=Configuration.getConfigurationIds("mapped_subdeprtmentconf", data1, accountID, temp, connectionSpace,"T");
	
	if(returnResult!=null)
	{
	for (Map.Entry<String, String> entry : returnResult.entrySet()) {
	if(!entry.getKey().equals("subdeptname") && !entry.getKey().equals("deptid")&& !entry.getKey().equals("organizationLevel")
	&& !entry.getKey().equals("date")&& !entry.getKey().equals("time")&& !entry.getKey().equals("userName"))
	{
	subDeptOtherData.put(entry.getKey(), entry.getValue());
	}
	}
	}
	//subDeptOtherData
	}
	if(deptFalgOrSubDept==3)
	{
	if(data!=null)
	{
	String names=null;int level=1;
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	if(obdata[0]!=null)
	names=obdata[0].toString();
	level=Integer.parseInt(obdata[1].toString()); 
	
	}
	namesofDepts=names.split("#");
	deptHierarchy=level;
	}
	if(namesofDepts.length>0)
	headerNameForDept=namesofDepts[0];
	if(namesofDepts.length>1)
	headerNameForSubDept=namesofDepts[1];
	if(namesofDepts.length>0)
	deptLabelName=namesofDepts[0];
	if(namesofDepts.length>1)
	subDeptLabelName=namesofDepts[1];
	
	//set the employee basic , prsnl , qualification or bank detail accordin based on the selected level of configuration
	StringBuilder empuery=new StringBuilder("");
	empuery.append("select table_name from mapped_employee_basic_configuration");
	List  empData=cbt.executeAllSelectQuery(empuery.toString(),connectionSpace);
	
	if(empData!=null)
	{
	for(Iterator it=empData.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 
	 if(obdata!=null)
	 {
	 if(obdata.toString().equalsIgnoreCase("employee_basic_configuration"))
	 {
	 empForAddBasic=1;
	 }
	 else if(obdata.toString().equalsIgnoreCase("employee_personal_configuration"))
	 {
	 empForAddPrsl=1;
	 }
	 else if(obdata.toString().equalsIgnoreCase("employee_work_exprience_configuration"))
	 {
	 empForAddWorkQua=1;
	 }
	 else if(obdata.toString().equalsIgnoreCase("employee_bank_details_configuration"))
	 {
	 empForAddBaank=1;
	 }
	 }
	 
	 
	}
	}
	
	}
	
	if(deptFalgOrSubDept==4)
	{
	List<String> data1=new ArrayList<String>();
	data1.add("mappedid");
	data1.add("table_name");
	Map<String, Object> temp=new HashMap<String, Object>();
	Map<String, String>returnResult=Configuration.getConfigurationIds("mapped_designation_confg", data1, accountID, temp, connectionSpace,"T");
	if(returnResult!=null)
	{
	for (Map.Entry<String, String> entry : returnResult.entrySet()) {
	
	if(!entry.getKey().equals("mappedOrgnztnId")&& !entry.getKey().equals("userName")
	&& !entry.getKey().equals("date")&& !entry.getKey().equals("time"))
	{
	designastionTextBox.put(entry.getKey(), entry.getValue());
	}
	}
	}
	returnResult=Configuration.getConfigurationIds("mapped_designation_confg", data1, accountID, temp, connectionSpace,"D");
	if(returnResult!=null)
	{
	for (Map.Entry<String, String> entry : returnResult.entrySet()) {
	if(!entry.getKey().equals("orgnztnlevel"))
	{
	designastionTextDropDown.put(entry.getKey(), entry.getValue());
	}
	}
	}
	//deptDataOther
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method setDepartmentAndSubDeptNames(int deptFalgOrSubDept) of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	
	
	

	public void setfeedBackDraftTags(int flag)
	{
	//flag value is getting the employee level data based on the selected no of employee configuration
	Map<String, String>configuration2=new HashMap<String,String>();
	Map<String, Object> temp=new HashMap<String, Object>();
	List<String> data=new ArrayList<String>();
	data.add("mappedid");
	data.add("table_name");
	if(flag>0)
	{
	configuration2=Configuration.getConfigurationIds("mapped_feedback_type_configuration", data, accountID, temp, connectionSpace,"T");
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	feedDarftLevelName=entry.getValue();
	}
	}
	if(flag>1)
	{
	configuration2=Configuration.getConfigurationIds("mapped_feedback_category_configuration", data, accountID, temp, connectionSpace,"T");
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	feedDarftCategoryLevelName=entry.getValue();
	}
	}
	if(flag>2)
	{
	configuration2=Configuration.getConfigurationIds("mapped_feedback_subcategory_configuration", data, accountID, temp, connectionSpace,"T");
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	fdSubCategoryLevelName.put(entry.getKey(),entry.getValue());
	}
	configuration2=Configuration.getConfigurationIds("mapped_feedback_subcategory_configuration", data, accountID, temp, connectionSpace,"Time");
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	fdSubCategoryLevelNameForDate.put(entry.getKey(),entry.getValue());
	}
	}
	
	}
	

	public void setShiftTags()
	 {
	Map<String, String>configuration2=new HashMap<String,String>();
	Map<String, Object> temp=new HashMap<String, Object>();
	List<String> data=new ArrayList<String>();
	data.add("mappedid");
	data.add("table_name");
	
	configuration2=Configuration.getConfigurationIds("mapped_shift_configuration", data, accountID, temp, connectionSpace,"T");
	for (Map.Entry<String, String> entry : configuration2.entrySet())
	   {
	shiftLevelName.put(entry.getKey(),entry.getValue());
	   }
	    configuration2=Configuration.getConfigurationIds("mapped_shift_configuration", data, accountID, temp, connectionSpace,"Time");
	for (Map.Entry<String, String> entry : configuration2.entrySet())
	   {
	shiftLevelNameForDate.put(entry.getKey(),entry.getValue());
	   }
	 }
	
	public void setViewPageProps()
	{
	try
	{
	List<String> data=new ArrayList<String>();
	data.add("mappedid");
	data.add("table_name");
	
	if(empForAddBasic==1)
	{
	List<GridDataPropertyView>empBasicText=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_basic_configuration");
	ConfigurationUtilBean objEjb=null;
	if(empBasicText!=null)
	{
	for(GridDataPropertyView gdp:empBasicText)
	{
	    objEjb=new ConfigurationUtilBean();
	if(gdp.getColType().equalsIgnoreCase("T"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("userName") 
	&& !gdp.getColomnName().equalsIgnoreCase("mobOne")&& !gdp.getColomnName().equalsIgnoreCase("personalIds") && !gdp.getColomnName().equalsIgnoreCase("qualificationIds")
	&& !gdp.getColomnName().equalsIgnoreCase("workExprienceIDs") && !gdp.getColomnName().equalsIgnoreCase("bankaccount")
	&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")
	&& !gdp.getColomnName().equalsIgnoreCase("useraccountid") && !gdp.getColomnName().equalsIgnoreCase("status")){
	          objEjb.setKey(gdp.getColomnName());
	    	         objEjb.setValue(gdp.getHeadingName());
	    	         objEjb.setColType(gdp.getColType());
	    	         objEjb.setValidationType(gdp.getValidationType());
	    	         if(gdp.getMandatroy()==null)
	    	objEjb.setMandatory(false);
	    	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	    	objEjb.setMandatory(false);
	    	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	    	objEjb.setMandatory(true);
	    	         empBasicLevels.add(objEjb);
	   }
	}
	else if(gdp.getColType().equalsIgnoreCase("F")){
    	 objEjb.setKey(gdp.getColomnName());
    	         objEjb.setValue(gdp.getHeadingName());
    	         objEjb.setColType(gdp.getColType());
    	         objEjb.setValidationType(gdp.getValidationType());
    	         if(gdp.getMandatroy()==null)
    	objEjb.setMandatory(false);
    	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
    	objEjb.setMandatory(false);
    	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
    	objEjb.setMandatory(true);
    	            empBasicFile.add(objEjb);
	          }
	else if(gdp.getColType().equalsIgnoreCase("Time")){
	 objEjb.setKey(gdp.getColomnName());
   	         objEjb.setValue(gdp.getHeadingName());
   	         objEjb.setColType(gdp.getColType());
   	         objEjb.setValidationType(gdp.getValidationType());
   	         if(gdp.getMandatroy()==null)
   	objEjb.setMandatory(false);
   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
   	objEjb.setMandatory(false);
   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
   	objEjb.setMandatory(true);
	empBasicCalendr.add(objEjb);
	         }
	else if(gdp.getColType().equalsIgnoreCase("D"))
	{
	if(gdp.getColomnName().equalsIgnoreCase("designation")){
	             objEjb.setKey(gdp.getColomnName().trim());
	             objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	      objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	empBasicDropDown.add(objEjb);
	}
	}
	}
	}
	}
	if(empForAddPrsl==1)
	{	 
	List<GridDataPropertyView>empPer=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_personal_configuration");
	ConfigurationUtilBean objEjb=null;
	if(empPer!=null)
	{
	for(GridDataPropertyView gdp:empPer)
	{
	    objEjb=new ConfigurationUtilBean();
	if(gdp.getColType().equalsIgnoreCase("T"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
	    objEjb.setKey(gdp.getColomnName());
	   	         objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	         objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	   	      empPerLevels.add(objEjb);
	}
	   
	}
	else if(gdp.getColType().equalsIgnoreCase("Time")){
	    objEjb.setKey(gdp.getColomnName());
	   	         objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	         objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	empPerCalendr.add(objEjb);
	}
	else if(gdp.getColType().equalsIgnoreCase("D"))
	empNameForOther=gdp.getHeadingName();
	}
	}
	}
	if(empForAddWorkQua==1)
	{
	List<GridDataPropertyView>empQua=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_work_exprience_configuration");
	ConfigurationUtilBean objEjb=null;
	if(empQua!=null)
	{
	for(GridDataPropertyView gdp:empQua)
	{
	    objEjb= new ConfigurationUtilBean();
	if(gdp.getColType().equalsIgnoreCase("T"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
	    objEjb.setKey(gdp.getColomnName());
	   	         objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	         objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	   	         empWorkExpLevels.add(objEjb);
	}
	}
	else if(gdp.getColType().equalsIgnoreCase("Time")){
	    objEjb.setKey(gdp.getColomnName());
	   	         objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	         objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	   	         empWorkExpCalendr.add(objEjb);
	}
	
	else if(gdp.getColType().equalsIgnoreCase("D"))
	empNameForOther=gdp.getHeadingName();
	}
	}
	}
	if(empForAddBaank==1)
	{
	//for bank details
	List<GridDataPropertyView>empBank=Configuration.getConfigurationData("mapped_employee_basic_configuration",accountID,connectionSpace,"",0,"table_name","employee_bank_details_configuration");
	ConfigurationUtilBean objEjb=null;
	if(empBank!=null)
	{
	for(GridDataPropertyView gdp:empBank)
	{
	    objEjb=new ConfigurationUtilBean();
	if(gdp.getColType().equalsIgnoreCase("T"))
	{
	if(!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")){
	    objEjb.setKey(gdp.getColomnName());
	   	         objEjb.setValue(gdp.getHeadingName());
	   	         objEjb.setColType(gdp.getColType());
	   	         objEjb.setValidationType(gdp.getValidationType());
	   	         if(gdp.getMandatroy()==null)
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("0"))
	   	objEjb.setMandatory(false);
	   	else if(gdp.getMandatroy().equalsIgnoreCase("1"))
	   	objEjb.setMandatory(true);
	   	         empBankLevels.add(objEjb);
	}
	}
	else if(gdp.getColType().equalsIgnoreCase("D") && gdp.getColomnName().equalsIgnoreCase("empName"))
	empNameForOther=gdp.getHeadingName();
	else if(gdp.getColType().equalsIgnoreCase("D"))
	accountTypeLevel=gdp.getHeadingName();
	}
	}
	}
	}
	catch(Exception me)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method setViewPageProps() of class "+getClass(), me);
	me.printStackTrace();
	}
	}
	public String getEmpWithDept()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List empSelectParam=new ArrayList<String>();
	empSelectParam.add("id");
	empSelectParam.add("empName");
	empSelectParam.add("useraccountid");
	Map<String, Object> temp=new HashMap<String, Object>();
	temp.put("dept",getSubDeptID());
	if(getEmpFalg()!=null && getEmpFalg().equalsIgnoreCase("1"))
	{
	temp.put("status","0");
	}
	empSelectParam=cbt.viewAllDataEitherSelectOrAll("employee_basic",empSelectParam,temp,connectionSpace);
	if(empSelectParam!=null && empSelectParam.size()>0)
	{
	for (Object c : empSelectParam) {
	Object[] dataC = (Object[]) c;
	if(getTempUSer()!=null && dataC[2]==null && getEmpFalg().equalsIgnoreCase("0"))
	{
	//for employee list for user management ,return only the list of employee whose user not created
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	else if(getEmpFalg().equalsIgnoreCase("1"))
	{
	//for employee history
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	else if(getEmpFalg().equalsIgnoreCase("0"))
	{
	//for all employee history
	empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
	}
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getEmpWithDept() of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public void setKRAKPIParameterMap()
	{
	List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_kra_kpi_configuration",accountID,connectionSpace,"",0,"table_name","kra_kpi_configuration");
	kraKPIParam=new LinkedHashMap<String, String>();
	if(offeringLevel1!=null)
	{
	for(GridDataPropertyView  gdp:offeringLevel1)
	{
	if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
	{
	kraKPIParam.put(gdp.getColomnName(), gdp.getHeadingName());
	}
	}
	}
	}
	
	/*
	 * Anoop, 6-8-2013
	 * 
	 */
	private List getModAbbreviationByUserId(String userId)
	{
	List modAbbList = null;
	try 
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	String params = null;
	StringBuffer tokens = new StringBuffer();
	String[] value = getSubModuleRights(userId);//Get sub module rights from table 'user_rights' 
	for(String val:value)
	{
	tokens.append("'"+val.split("_")[0]+"',");//get rights name
	}
	params = tokens.substring(0,tokens.lastIndexOf(","));//params contains comma separated values of rights_name so that distinct module_abbreviation can be fetched from table 'user_rights'
	//Fetch module_abbreviation to be used in user creation
	modAbbList = coi.executeAllSelectQuery("select distinct(module_abbreviation) from user_rights where rights_name in("+
	params+")", connectionSpace);
	} 
	catch (Exception e) {
	e.printStackTrace();
	} 
	return modAbbList;
	}
	/*
	 * Anoop, 12-8-2013
	 * Get sub module rights from table 'user_rights' 
	 */
	private String[] getSubModuleRights(String userId)
	{
	String[] value = null;
	try 
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where userID = '"
	+Cryptography.encrypt(userId, DES_ENCRYPTION_KEY)+"'", connectionSpace);
	if(adminRights != null && adminRights.size()>0)
	{
	value = adminRights.get(0).toString().split("#");
	}
	} 
	catch (Exception e) {
	e.printStackTrace();
	} 
	return value;
	}
	
	/*
	 * Anoop, 6-8-2013
	 * get number of users created for different modules in a MAP<module_name, number_of_users>
	 */
	private HashMap getNumberOfUsersCreatedForModules()
	{
	HashMap<String, Integer> count = null;
	try 
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount", connectionSpace);
	String params = null;
	if(adminRights != null && adminRights.size()>0)
	{
	count = new LinkedHashMap<String, Integer>();
	for(Object obj:adminRights)
	{
	
	StringBuffer tokens = new StringBuffer();
	String[] value = obj.toString().split("#");
	for(String val:value)
	{
	tokens.append("'"+val.split("_")[0]+"',");//get rights name
	}
	params = tokens.substring(0,tokens.lastIndexOf(","));//params contains comma separated values of rights_name so that distinct module_abbreviation can be fetched from table 'user_rights'
	
	//Fetch module_abbreviation to be used in user creation
	List modAbbList = coi.executeAllSelectQuery("select distinct(module_abbreviation) from user_rights where rights_name in("+
	params+")", connectionSpace);//AM,ABR,HDM
	
	for(Object s:modAbbList)
	{
	if(count.containsKey(s.toString().trim()))
	{
	int c = count.get(s.toString().trim());
	count.put(s.toString().trim(), ++c);
	}
	else
	{
	count.put(s.toString(),1);
	}
	}
	}
	}
	} 
	catch (Exception e) {
	e.printStackTrace();
	} 
	return count;
	}

	//Anoop, create user ritht's link
	private void createLinks(String id)
	{
	/****************************************************************************************************************************
	 * Anoop, 31-7-2013
	 * user management 
	 ***************************************************************************************************************************/
	CommonOperInterface coi = new CommonConFactory().createInterface();
	//Start: Put apps list and no. of users in a map
	LinkedHashMap<String , Integer> appUserMap = new LinkedHashMap<String, Integer>();
	
	List modAbbList = getModAbbreviationByUserId(userName);
	
	//Start: Get all sub modules rights for the user being modified
	ArrayList<String> subModuleRightsList = null;
	if(id != null)//In case of editing a user
	{
	String[] subModuleRightsArray = getSubModuleRights(id);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	subModuleRightsList = new ArrayList<String>();
	for(String s:subModuleRightsArray)
	{
	subModuleRightsList.add(s.trim());
	}
	}
	}
	//End: Get all sub modules rights for the user being modified}
	
	//Start:Fetch productCategory and productuser from client_product 
	List clientProductList = coi.executeAllSelectQuery("select productCategory, productuser from client_product", connectionSpace);
	if(clientProductList != null && clientProductList.size()>0)
	{
	Object[] obj = (Object[])clientProductList.get(0);
	String[] appsHashSeparated  = obj[0].toString().split("#");
	String[] usersHashSeparated = obj[1].toString().split("#");
	for(int i=0; i<appsHashSeparated.length; i++)
	{
	if(modAbbList.contains(appsHashSeparated[i]))
	{
	appUserMap.put(appsHashSeparated[i], Integer.parseInt(usersHashSeparated[i].trim()));//MAP<modules, number_of_users> rights a user can give to others and its total users
	}
	}
	}
	//End:Fetch productCategory and productuser from client_product
	
	//Start: Fetch number of users made for a module
	HashMap<String, Integer> moduleUserCount = getNumberOfUsersCreatedForModules();//Map having modules and its current user count
	
	ArrayList<UserRights> listWFPM = new ArrayList<UserRights>();
	ArrayList<UserRights> listCommon = new ArrayList<UserRights>();
	ArrayList<UserRights> listSM = new ArrayList<UserRights>();
	ArrayList<UserRights> listHRA = new ArrayList<UserRights>();
	ArrayList<UserRights> listHDM = new ArrayList<UserRights>();
	ArrayList<UserRights> listCOMPL = new ArrayList<UserRights>();
	ArrayList<UserRights> listFM = new ArrayList<UserRights>();
	ArrayList<UserRights> listLM = new ArrayList<UserRights>();
	ArrayList<UserRights> listDAR = new ArrayList<UserRights>();
	ArrayList<UserRights> listCommonSpace = new ArrayList<UserRights>();
	ArrayList<UserRights> listCommunication = new ArrayList<UserRights>();
	ArrayList<UserRights> listASTM = new ArrayList<UserRights>();
	ArrayList<UserRights> listKR = new ArrayList<UserRights>();
	ArrayList<UserRights> listDREAM = new ArrayList<UserRights>();
	ArrayList<UserRights> listDEPT = new ArrayList<UserRights>();
	ArrayList<UserRights> listSTARTUP = new ArrayList<UserRights>();
	ArrayList<UserRights> listDASH = new ArrayList<UserRights>();
	ArrayList<UserRights> listOrd = new ArrayList<UserRights>();
	ArrayList<UserRights> listOrdReq = new ArrayList<UserRights>();
	ArrayList<UserRights> listRef = new ArrayList<UserRights>();
	ArrayList<UserRights> listCrc = new ArrayList<UserRights>();
	ArrayList<UserRights> cps = new ArrayList<UserRights>();
	ArrayList<UserRights> lps = new ArrayList<UserRights>();
	ArrayList<UserRights> labOrd = new ArrayList<UserRights>();
	//fetch data from table 'user_rights'
	String query = "select * from user_rights order by module_heading asc";
	List list = coi.executeAllSelectQuery(query, connectionSpace);
	userRightsList = new ArrayList<RightsCategory>();
	if(list != null && list.size() > 0)
	{
	for(Object obj:list)
	{
	Object[] data = (Object[]) obj;
	UserRights ur = new UserRights();
	if(!appUserMap.containsKey(data[7].toString()))continue; //Do not show module to a user that he has not been given or validity expired
	int maxAllowedCount = appUserMap.get(data[7].toString());
	int currentCount = moduleUserCount.get(data[7].toString());
	
	if(currentCount == maxAllowedCount) continue;//If module's number of users are exceeding then it will not be shown in user management
	ur.setId(Integer.parseInt(data[0].toString()));
	if(data[1] == null || data[1].toString().equalsIgnoreCase(""))continue;
	else ur.setRightsName(data[1].toString());
	
	if(data[2] == null || data[2].toString().equalsIgnoreCase(""))continue;
	else ur.setRightsHeading(data[2].toString());
	
	if(data[3] == null || data[3].toString().equalsIgnoreCase(""))continue;
	else if(id != null && subModuleRightsList.contains(data[1].toString()+"_ADD")) ur.setAdd("HAVING"); 
	else ur.setAdd(data[3].toString());
	
	if(data[4] == null || data[4].toString().equalsIgnoreCase(""))continue;
	else if(id != null && subModuleRightsList.contains(data[1].toString()+"_VIEW")) ur.setView("HAVING");
	else ur.setView(data[4].toString());
	
	if(data[5] == null || data[5].toString().equalsIgnoreCase(""))continue;
	else if(id != null && subModuleRightsList.contains(data[1].toString()+"_MODIFY")) ur.setModify("HAVING");
	else ur.setModify(data[5].toString());
	
	if(data[6] == null || data[6].toString().equalsIgnoreCase(""))continue;
	else if(id != null && subModuleRightsList.contains(data[1].toString()+"_DELETE")) ur.setDelete("HAVING");
	else ur.setDelete(data[6].toString());
	
	if(data[7] == null || data[7].toString().equalsIgnoreCase(""))continue;//abbreviation
	else ur.setModuleAbbreviation(data[7].toString());
	
	if(data[8] == null || data[8].toString().equalsIgnoreCase(""))continue;
	else ur.setModuleHeading(data[8].toString());
	//create a tab panel containing rights
	if(data[8].toString().equalsIgnoreCase("WFPM"))
	{
	listWFPM.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Admin"))
	{
	listCommon.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Salary"))
	{
	listSM.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("HR"))
	{
	listHRA.add(ur);
	}
	
	if(data[8].toString().equalsIgnoreCase("PatientDelight"))
	{
	listFM.add(ur);
	}
	////////////////////////////////////////////////////////////////
	if(data[8].toString().equalsIgnoreCase("Helpdesk"))
	{
	listHDM.add(ur);
	}
	////////////======================================================
	if(data[8].toString().equalsIgnoreCase("Operations"))
	{
	listCOMPL.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("HR"))
	{
	listLM.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Projects"))
	{
	listDAR.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Common"))
	{
	listCommonSpace.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Communication"))
	{
	listCommunication.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Asset"))
	{
	listASTM.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("KR"))
	{
	listKR.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Compliants"))
	{
	listDREAM.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Department"))
	{
	listDEPT.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("StartingUp"))
	{
	listSTARTUP.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Dashboard"))
	{
	listDASH.add(ur);
	}
	if(data[8].toString().equalsIgnoreCase("Order"))
	{
	listOrd.add(ur);
	
	}
	if(data[8].toString().equalsIgnoreCase("OrderRequest"))
	{
	listOrdReq.add(ur);
	
	}
	if(data[8].toString().equalsIgnoreCase("Referral"))
	{
	listRef.add(ur);
	
	}
	if(data[8].toString().equalsIgnoreCase("CriticalResult"))
	{
	listCrc.add(ur);
	
	}
	if(data[8].toString().equalsIgnoreCase("CorporatePatient"))
	{
		cps.add(ur);		
	}
	if(data[8].toString().equalsIgnoreCase("LongPatientStay"))
	{
		lps.add(ur);		
	}
	if(data[8].toString().equalsIgnoreCase("LabOrder"))
	{
		labOrd.add(ur);		
	}
	}
	}
	
	//create a tab panel
	if(listCommon.size() > 0)
	{
	RightsCategory rc4 = new RightsCategory();
	rc4.setModule(listCommon.get(0).getModuleHeading());
	rc4.setList(listCommon);
	userRightsList.add(rc4);
	}
	if(listASTM.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listASTM.get(0).getModuleHeading());
	rc9.setList(listASTM);
	userRightsList.add(rc9);
	
	}
	if(listDREAM.size() > 0)
	{
	RightsCategory rc4 = new RightsCategory();
	rc4.setModule(listDREAM.get(0).getModuleHeading());
	rc4.setList(listDREAM);
	userRightsList.add(rc4);
	}
	if(listCommonSpace.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listCommonSpace.get(0).getModuleHeading());
	rc9.setList(listCommonSpace);
	userRightsList.add(rc9);
	//System.out.println("userRightsList ::::" +userRightsList);
	}
	if(listCommunication.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listCommunication.get(0).getModuleHeading());
	rc9.setList(listCommunication);
	userRightsList.add(rc9);
	//System.out.println("userRightsList ::::" +userRightsList);
	}
	if(listHDM.size() > 0)
	{
	RightsCategory rc7 = new RightsCategory();
	rc7.setModule(listHDM.get(0).getModuleHeading());
	rc7.setList(listHDM);
	userRightsList.add(rc7);
	}
	if(listLM.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listLM.get(0).getModuleHeading());
	rc9.setList(listLM);
	userRightsList.add(rc9);
	}
	if(listKR.size() > 0)
	{
	RightsCategory rc8 = new RightsCategory();
	rc8.setModule(listKR.get(0).getModuleHeading());
	rc8.setList(listKR);
	userRightsList.add(rc8);
	}
	if(listCOMPL.size() > 0)
	{
	RightsCategory rc8 = new RightsCategory();
	rc8.setModule(listCOMPL.get(0).getModuleHeading());
	rc8.setList(listCOMPL);
	userRightsList.add(rc8);
	}
	if(listFM.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listFM.get(0).getModuleHeading());
	rc9.setList(listFM);
	userRightsList.add(rc9);
	}
	if(listDAR.size()>0)
	{
	RightsCategory rc9=new RightsCategory();
	rc9.setModule(listDAR.get(0).getModuleHeading());
	rc9.setList(listDAR);
	userRightsList.add(rc9);
	}
	if(listWFPM.size() > 0)
	{
	RightsCategory rc3 = new RightsCategory();
	rc3.setModule(listWFPM.get(0).getModuleHeading());
	rc3.setList(listWFPM);
	userRightsList.add(rc3);
	}
	if(listSM.size() > 0)
	{
	RightsCategory rc5 = new RightsCategory();
	rc5.setModule(listSM.get(0).getModuleHeading());
	rc5.setList(listSM);
	userRightsList.add(rc5);
	}
	if(listDEPT.size() > 0)
	{
	RightsCategory rc16 = new RightsCategory();
	rc16.setModule(listDEPT.get(0).getModuleHeading());
	rc16.setList(listDEPT);
	userRightsList.add(rc16);
	}
	if(listSTARTUP.size() > 0)
	{
	RightsCategory rc17 = new RightsCategory();
	rc17.setModule(listSTARTUP.get(0).getModuleHeading());
	rc17.setList(listSTARTUP);
	userRightsList.add(rc17);
	}
	if(listDASH.size() > 0)
	{
	RightsCategory rc18 = new RightsCategory();
	rc18.setModule(listDASH.get(0).getModuleHeading());
	rc18.setList(listDASH);
	userRightsList.add(rc18);
	}
	if(listOrd.size() > 0)
	{
	RightsCategory rc19 = new RightsCategory();
	rc19.setModule(listOrd.get(0).getModuleHeading());
	rc19.setList(listOrd);
	//System.out.println("rc19>>   "+rc19);
	userRightsList.add(rc19);
	}
	if(listOrdReq.size() > 0)
	{
	RightsCategory rc21 = new RightsCategory();
	rc21.setModule(listOrdReq.get(0).getModuleHeading());
	rc21.setList(listOrdReq);
//	System.out.println("rc20>>   "+rc21);
	userRightsList.add(rc21);
	}
	if(listRef.size() > 0)
	{
	RightsCategory rc22 = new RightsCategory();
	rc22.setModule(listRef.get(0).getModuleHeading());
	rc22.setList(listRef);
	//System.out.println("rc20>>   "+rc22);
	userRightsList.add(rc22);
	}
	if(listCrc.size() > 0)
	{
	RightsCategory crc = new RightsCategory();
	crc.setModule(listCrc.get(0).getModuleHeading());
	crc.setList(listCrc);
	//System.out.println("crc>>   "+crc);
	userRightsList.add(crc);
	}
	
	if(cps.size() > 0)
	{
		RightsCategory rc6 = new RightsCategory();
		rc6.setModule(cps.get(0).getModuleHeading());
		rc6.setList(cps);
		userRightsList.add(rc6);
	}
	if(lps.size() > 0)
	{
		RightsCategory lps1 = new RightsCategory();
		lps1.setModule(lps.get(0).getModuleHeading());
		lps1.setList(lps);
		userRightsList.add(lps1);
	}
	if(labOrd.size() > 0)
	{
		RightsCategory labOrd1 = new RightsCategory();
		labOrd1.setModule(labOrd.get(0).getModuleHeading());
		labOrd1.setList(labOrd);
		userRightsList.add(labOrd1);
	}
	/*if(listHRA.size() > 0)
	{
	RightsCategory rc6 = new RightsCategory();
	rc6.setModule(listHRA.get(0).getModuleHeading());
	rc6.setList(listHRA);
	userRightsList.add(rc6);
	}*/
	//////////////////////////
	
	//System.out.println("userRightsList ::::" +userRightsList.toString());
	/*User Management ****************************************************************************************************************************/
	}
	
	//Update user rights
	public String beforeModifyUserData()
	{
	try
	{
	if(userName == null || userName.equalsIgnoreCase(""))
	return LOGIN;
	//code to modify user
	createLinks(id);
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method updateUser of class "+getClass(), e);
	e.printStackTrace();
	addActionError("Oops there is some problem!!!");
	return ERROR;
	}
	
	return SUCCESS;
	}
	
//////////////////////////////Add By padam /////////////////////////////////
	public String getFieldName(String mapTableName,String masterTableName,String columnName)
	{
	String returnResult=null;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	List<GridDataPropertyView> spareCatgColumnList=Configuration.getConfigurationData(mapTableName,accountID,connectionSpace,"",0,"table_name",masterTableName);
	if(spareCatgColumnList!=null&&spareCatgColumnList.size()>0)
	{
	for(GridDataPropertyView  gdp:spareCatgColumnList)
	{
	if(gdp.getColomnName().equalsIgnoreCase(columnName))
	{
	returnResult=gdp.getHeadingName();
	}
	}
	}
	else
	{
	addActionMessage(mapTableName+" Table Is Not properly Configured !");
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getFieldName of class "+getClass(), e);
	e.printStackTrace();
	}
	}
	return returnResult;
	}
	
	public String beforesubdeptAdd()
	 {
	boolean valid = ValidateSession.checkSession();
	if (valid)
	{
	try
	{
	officeMap = new HashMap<Integer, String>();
	setBeforesubdeptAdd();
	List data=cbt.executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);
            	//System.out.println("Data List Size is  "+data.size());
            	if(data!=null && data.size()>0 && data.get(0)!=null)
            	{
            	String arr[]=data.get(0).toString().split("#");
            	for (int i = 0; i < arr.length; i++) 
            	{
            	officeMap.put(i+1, arr[i].toString());
	}
            	}
	
	return "success";
	}
	catch (Exception e)
	{
	e.printStackTrace();
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAdd of class " + getClass(), e);
	return "error";
	}
	}
	else
	{
	return "login";
	}
	}
	
	public void setBeforesubdeptAdd()
	{
	ConfigurationUtilBean obj;
	pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
	//System.out.println("setBeforesubdeptAdd>>>>");
	try
	{
	List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name","subdeprtmentconf");
	if (gridFields != null && gridFields.size()>0)
	{
	for (GridDataPropertyView gdv : gridFields) {
	 obj = new ConfigurationUtilBean();
	if (!gdv.getColomnName().equalsIgnoreCase("userName") && !gdv.getColomnName().equalsIgnoreCase("date")
	&& !gdv.getColomnName().equalsIgnoreCase("time") && !gdv.getColomnName().equalsIgnoreCase("status")) {
	obj.setKey(gdv.getColomnName());
	obj.setValue(gdv.getHeadingName());
	obj.setValidationType(gdv.getValidationType());
	obj.setColType("D");
	obj.setMandatory(true);
	pageFieldsColumns.add(obj);
	}
	}
	
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactAddPageDataFields of class "
	+ getClass(), e);
	}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String insertSubDeptData()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String accountID = (String) session.get("accountid");
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");

	if (msg != null && msg.size() > 0)
	{
	// create table query based on configuration
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	List<TableColumes> tableColume = new ArrayList<TableColumes>();
	for (GridDataPropertyView gdp : msg)
	{
	TableColumes ob1 = new TableColumes();
	if (!gdp.getColomnName().equalsIgnoreCase("mappedOrgnztnId") && !gdp.getColomnName().equalsIgnoreCase("groupId")) {
	ob1 = new TableColumes();
	ob1.setColumnname(gdp.getColomnName());
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	}
	}
	TableColumes ob1 = new TableColumes();
	ob1.setColumnname("status");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default 'Active'");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("deactiveOn");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	cbt.createTable22("subdepartment", tableColume, connectionSpace);
	String subdept=null;
	String deptId=null;
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	if (requestParameterNames != null && requestParameterNames.size() > 0)
	{

	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	while (it.hasNext())
	{
	String parmName = it.next().toString();
	String paramValue = request.getParameter(parmName);
	if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("mappedOrgnztnId")&& !paramValue.equalsIgnoreCase("groupId"))
	{
	ob = new InsertDataTable();
	ob.setColName(parmName);
	ob.setDataName(paramValue);
	insertData.add(ob);
	if (parmName.equalsIgnoreCase("subdeptname"))
	{
	subdept=paramValue;
	}
	if (parmName.equalsIgnoreCase("deptid"))
	{
	deptId=paramValue;
	}
	}
	}
	}

	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("userName");
	ob.setDataName(userName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("status");
	ob.setDataName("Active");
	insertData.add(ob);
	
	boolean exists=new HelpdeskUniversalHelper().isExist("subdepartment", "deptid", deptId, "subdeptname", subdept, null, null, null, null, connectionSpace);
	if (!exists)
	{
	status = cbt.insertIntoTable("subdepartment", insertData, connectionSpace);
	 addActionMessage("Data Added Successfully !!!");
	}
	else
	{
	 addActionMessage("Data Already Exists !!!");
	}
	
	return "success";
	}
	else
	{
	 addActionMessage("Oops there is some problem !!!");
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
	returnResult = LOGIN;
	}
	return returnResult;
	}


	
	
	public Map getSession() {
	return session;
	}

	public void setSession(Map session) {
	this.session = session;
	}

	public String getUserName() {
	return userName;
	}

	public void setUserName(String userName) {
	this.userName = userName;
	}

	public String getAccountID() {
	return accountID;
	}

	public void setAccountID(String accountID) {
	this.accountID = accountID;
	}

	public String getLevel1() {
	return level1;
	}

	public void setLevel1(String level1) {
	this.level1 = level1;
	}

	public int getLevelOforganization() {
	return levelOforganization;
	}

	public void setLevelOforganization(int levelOforganization) {
	this.levelOforganization = levelOforganization;
	}

	public Map<Integer, String> getLevelList() {
	return levelList;
	}

	public void setLevelList(Map<Integer, String> levelList) {
	this.levelList = levelList;
	}

	public Map<Integer, String> getLevel2() {
	return level2;
	}

	public void setLevel2(Map<Integer, String> level2) {
	this.level2 = level2;
	}

	public Map<String, String> getEmpListData() {
	return empListData;
	}

	public void setEmpListData(Map<String, String> empListData) {
	this.empListData = empListData;
	}

	public String getSubDeptID() {
	return subDeptID;
	}

	public void setSubDeptID(String subDeptID) {
	this.subDeptID = subDeptID;
	}
	
	public String getId() {
	return id;
	}


	public void setId(String id) {
	this.id = id;
	}

	public String getEmpName() {
	return empName;
	}

	public void setEmpName(String empName) {
	this.empName = empName;
	}

	public String getMobOne() {
	return mobOne;
	}

	public void setMobOne(String mobOne) {
	this.mobOne = mobOne;
	}

	public Map<Integer, String> getLevel3() {
	return level3;
	}

	public void setLevel3(Map<Integer, String> level3) {
	this.level3 = level3;
	}

	public Map<Integer, String> getLevel4() {
	return level4;
	}

	public void setLevel4(Map<Integer, String> level4) {
	this.level4 = level4;
	}

	public Map<Integer, String> getLevel5() {
	return level5;
	}

	public void setLevel5(Map<Integer, String> level5) {
	this.level5 = level5;
	}

	public String getOrgIdInLevel() {
	return orgIdInLevel;
	}

	public void setOrgIdInLevel(String orgIdInLevel) {
	this.orgIdInLevel = orgIdInLevel;
	}

	public Map<Integer, String> getSubDepartmentList() {
	return subDepartmentList;
	}

	public void setSubDepartmentList(Map<Integer, String> subDepartmentList) {
	this.subDepartmentList = subDepartmentList;
	}

	public Map<Integer, String> getDesignationList() {
	return designationList;
	}

	public void setDesignationList(Map<Integer, String> designationList) {
	this.designationList = designationList;
	}

	public String getEmpFalg() {
	return empFalg;
	}

	public void setEmpFalg(String empFalg) {
	this.empFalg = empFalg;
	}

	public String getFlafSeleced() {
	return flafSeleced;
	}

	public void setFlafSeleced(String flafSeleced) {
	this.flafSeleced = flafSeleced;
	}

	public Map<String, String> getAppList() {
	return appList;
	}

	public void setAppList(Map<String, String> appList) {
	this.appList = appList;
	}
	public Map<Integer, String> getDeptList() {
	return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
	this.deptList = deptList;
	}

	public boolean isSFA() {
	return SFA;
	}

	public void setSFA(boolean sfa) {
	SFA = sfa;
	}

	public boolean isHD() {
	return HD;
	}

	public void setHD(boolean hd) {
	HD = hd;
	}

	public String getDeptFlag() {
	return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
	this.deptFlag = deptFlag;
	}

	public String getHeaderNameForDept() {
	return headerNameForDept;
	}

	public void setHeaderNameForDept(String headerNameForDept) {
	this.headerNameForDept = headerNameForDept;
	}

	public String getSubDeptfalg() {
	return subDeptfalg;
	}

	public void setSubDeptfalg(String subDeptfalg) {
	this.subDeptfalg = subDeptfalg;
	}

	public String getEmpModuleFalgForDeptSubDept() {
	return empModuleFalgForDeptSubDept;
	}

	public void setEmpModuleFalgForDeptSubDept(String empModuleFalgForDeptSubDept) {
	this.empModuleFalgForDeptSubDept = empModuleFalgForDeptSubDept;
	}

	public String getHeaderNameForSubDept() {
	return headerNameForSubDept;
	}

	public void setHeaderNameForSubDept(String headerNameForSubDept) {
	this.headerNameForSubDept = headerNameForSubDept;
	}

	public int getDeptHierarchy() {
	return deptHierarchy;
	}

	public void setDeptHierarchy(int deptHierarchy) {
	this.deptHierarchy = deptHierarchy;
	}

	public String getLevel1LabelName() {
	return level1LabelName;
	}

	public void setLevel1LabelName(String level1LabelName) {
	this.level1LabelName = level1LabelName;
	}

	public String getLevel2LabelName() {
	return level2LabelName;
	}

	public void setLevel2LabelName(String level2LabelName) {
	this.level2LabelName = level2LabelName;
	}

	public String getLevel3LabelName() {
	return level3LabelName;
	}

	public void setLevel3LabelName(String level3LabelName) {
	this.level3LabelName = level3LabelName;
	}

	public String getLevel4LabelName() {
	return level4LabelName;
	}

	public void setLevel4LabelName(String level4LabelName) {
	this.level4LabelName = level4LabelName;
	}

	public String getLevel5LabelName() {
	return level5LabelName;
	}

	public void setLevel5LabelName(String level5LabelName) {
	this.level5LabelName = level5LabelName;
	}

	public String getDeptLabelName() {
	return deptLabelName;
	}

	public void setDeptLabelName(String deptLabelName) {
	this.deptLabelName = deptLabelName;
	}

	public String getSubDeptLabelName() {
	return subDeptLabelName;
	}

	public void setSubDeptLabelName(String subDeptLabelName) {
	this.subDeptLabelName = subDeptLabelName;
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

	public String getFeedDarftLevelName() {
	return feedDarftLevelName;
	}

	public void setFeedDarftLevelName(String feedDarftLevelName) {
	this.feedDarftLevelName = feedDarftLevelName;
	}

	public String getFeedDarftCategoryLevelName() {
	return feedDarftCategoryLevelName;
	}

	public void setFeedDarftCategoryLevelName(String feedDarftCategoryLevelName) {
	this.feedDarftCategoryLevelName = feedDarftCategoryLevelName;
	}

	public Map<String, String> getFdSubCategoryLevelName() {
	return fdSubCategoryLevelName;
	}

	public void setFdSubCategoryLevelName(Map<String, String> fdSubCategoryLevelName) {
	this.fdSubCategoryLevelName = fdSubCategoryLevelName;
	}

	public Map<String, String> getFdSubCategoryLevelNameForDate() {
	return fdSubCategoryLevelNameForDate;
	}

	public void setFdSubCategoryLevelNameForDate(
	Map<String, String> fdSubCategoryLevelNameForDate) {
	this.fdSubCategoryLevelNameForDate = fdSubCategoryLevelNameForDate;
	}

	public int getEmpLevelForAdd() {
	return empLevelForAdd;
	}

	public void setEmpLevelForAdd(int empLevelForAdd) {
	this.empLevelForAdd = empLevelForAdd;
	}

	

	public List<ConfigurationUtilBean> getEmpBasicLevels() {
	    return empBasicLevels;
	}


	public void setEmpBasicLevels(List<ConfigurationUtilBean> empBasicLevels) {
	    this.empBasicLevels = empBasicLevels;
	}


	public List<ConfigurationUtilBean> getEmpBasicFile() {
	    return empBasicFile;
	}

	public void setEmpBasicFile(List<ConfigurationUtilBean> empBasicFile) {
	    this.empBasicFile = empBasicFile;
	}

       
	public List<ConfigurationUtilBean> getEmpBasicCalendr() {
	    return empBasicCalendr;
	}


	public void setEmpBasicCalendr(List<ConfigurationUtilBean> empBasicCalendr) {
	    this.empBasicCalendr = empBasicCalendr;
	}


	public List<ConfigurationUtilBean> getEmpBasicDropDown() {
	    return empBasicDropDown;
	}


	public void setEmpBasicDropDown(List<ConfigurationUtilBean> empBasicDropDown) {
	    this.empBasicDropDown = empBasicDropDown;
	}
       

	public List<ConfigurationUtilBean> getEmpPerLevels() {
	    return empPerLevels;
	}
	public void setEmpPerLevels(List<ConfigurationUtilBean> empPerLevels) {
	    this.empPerLevels = empPerLevels;
	}
	public List<ConfigurationUtilBean> getEmpPerCalendr() {
	    return empPerCalendr;
	}
	public void setEmpPerCalendr(List<ConfigurationUtilBean> empPerCalendr) {
	    this.empPerCalendr = empPerCalendr;
	}
	public String getMobNoLevelName() {
	return mobNoLevelName;
	}

	public void setMobNoLevelName(String mobNoLevelName) {
	this.mobNoLevelName = mobNoLevelName;
	}

	public String getEmpNameForOther() {
	return empNameForOther;
	}

	public void setEmpNameForOther(String empNameForOther) {
	this.empNameForOther = empNameForOther;
	}
      
	public List<ConfigurationUtilBean> getEmpWorkExpLevels() {
	    return empWorkExpLevels;
	}


	public void setEmpWorkExpLevels(List<ConfigurationUtilBean> empWorkExpLevels) {
	    this.empWorkExpLevels = empWorkExpLevels;
	}


	public List<ConfigurationUtilBean> getEmpWorkExpCalendr() {
	    return empWorkExpCalendr;
	}


	public void setEmpWorkExpCalendr(List<ConfigurationUtilBean> empWorkExpCalendr) {
	    this.empWorkExpCalendr = empWorkExpCalendr;
	}


	public String getAccountTypeLevel() {
	return accountTypeLevel;
	}

	public void setAccountTypeLevel(String accountTypeLevel) {
	this.accountTypeLevel = accountTypeLevel;
	}


	public List<ConfigurationUtilBean> getEmpBankLevels() {
	    return empBankLevels;
	}


	public void setEmpBankLevels(List<ConfigurationUtilBean> empBankLevels) {
	    this.empBankLevels = empBankLevels;
	}


	public String getShiftConf() {
	return shiftConf;
	}

	public void setShiftConf(String shiftConf) {
	this.shiftConf = shiftConf;
	}

	public String getShiftLevel() {
	return shiftLevel;
	}

	public void setShiftLevel(String shiftLevel) {
	this.shiftLevel = shiftLevel;
	}

	public Map<String, String> getShiftLevelName() {
	return shiftLevelName;
	}

	public void setShiftLevelName(Map<String, String> shiftLevelName) {
	this.shiftLevelName = shiftLevelName;
	}

	public Map<String, String> getShiftLevelNameForDate() {
	return shiftLevelNameForDate;
	}

	public void setShiftLevelNameForDate(Map<String, String> shiftLevelNameForDate) {
	this.shiftLevelNameForDate = shiftLevelNameForDate;
	}

	public String getDesignationFlag() {
	return designationFlag;
	}

	public void setDesignationFlag(String designationFlag) {
	this.designationFlag = designationFlag;
	}

	public Map<String, String> getDesignastionTextBox() {
	return designastionTextBox;
	}

	public void setDesignastionTextBox(Map<String, String> designastionTextBox) {
	this.designastionTextBox = designastionTextBox;
	}

	public Map<String, String> getDesignastionTextDropDown() {
	return designastionTextDropDown;
	}

	public void setDesignastionTextDropDown(
	Map<String, String> designastionTextDropDown) {
	this.designastionTextDropDown = designastionTextDropDown;
	}
	public Map<String, String> getDeptDataOther() {
	return deptDataOther;
	}

	public void setDeptDataOther(Map<String, String> deptDataOther) {
	this.deptDataOther = deptDataOther;
	}

	public Map<String, String> getSubDeptOtherData() {
	return subDeptOtherData;
	}

	public void setSubDeptOtherData(Map<String, String> subDeptOtherData) {
	this.subDeptOtherData = subDeptOtherData;
	}

	public int getEmpForAddBasic() {
	return empForAddBasic;
	}

	public void setEmpForAddBasic(int empForAddBasic) {
	this.empForAddBasic = empForAddBasic;
	}

	public int getEmpForAddPrsl() {
	return empForAddPrsl;
	}

	public void setEmpForAddPrsl(int empForAddPrsl) {
	this.empForAddPrsl = empForAddPrsl;
	}

	public int getEmpForAddWorkQua() {
	return empForAddWorkQua;
	}

	public void setEmpForAddWorkQua(int empForAddWorkQua) {
	this.empForAddWorkQua = empForAddWorkQua;
	}

	public int getEmpForAddBaank() {
	return empForAddBaank;
	}

	public void setEmpForAddBaank(int empForAddBaank) {
	this.empForAddBaank = empForAddBaank;
	}

	public Map<String, String> getKraKPIParam() {
	return kraKPIParam;
	}

	public void setKraKPIParam(Map<String, String> kraKPIParam) {
	this.kraKPIParam = kraKPIParam;
	}

	public String getKraKPIFlag() {
	return kraKPIFlag;
	}

	public void setKraKPIFlag(String kraKPIFlag) {
	this.kraKPIFlag = kraKPIFlag;
	}


	public String getTempUSer() {
	return tempUSer;
	}


	public void setTempUSer(String tempUSer) {
	this.tempUSer = tempUSer;
	}


	public SessionFactory getConnectionSpace() {
	return connectionSpace;
	}


	public void setConnectionSpace(SessionFactory connectionSpace) {
	this.connectionSpace = connectionSpace;
	}


	public ArrayList<RightsCategory> getUserRightsList() {
	return userRightsList;
	}


	public void setUserRightsList(ArrayList<RightsCategory> userRightsList) {
	this.userRightsList = userRightsList;
	}

	public boolean isStatusFlag() {
	return statusFlag;
	}

	public void setStatusFlag(boolean statusFlag) {
	this.statusFlag = statusFlag;
	}

	public Map<Integer, String> getOfficeMap() {
	return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap) {
	this.officeMap = officeMap;
	}

	public Map<String, String> getGroupMap() {
	return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap) {
	this.groupMap = groupMap;
	}




	public List<ConfigurationUtilBean> getContactDD() {
	return contactDD;
	}




	public void setContactDD(List<ConfigurationUtilBean> contactDD) {
	this.contactDD = contactDD;
	}




	public Map<Integer, String> getSubGroupMap() {
	return subGroupMap;
	}




	public void setSubGroupMap(Map<Integer, String> subGroupMap) {
	this.subGroupMap = subGroupMap;
	}




	public List<ConfigurationUtilBean> getSubDeptDD() {
	return subDeptDD;
	}




	public void setSubDeptDD(List<ConfigurationUtilBean> subDeptDD) {
	this.subDeptDD = subDeptDD;
	}




	public List<ConfigurationUtilBean> getSubdeptTextBox() {
	return subdeptTextBox;
	}




	public void setSubdeptTextBox(List<ConfigurationUtilBean> subdeptTextBox) {
	this.subdeptTextBox = subdeptTextBox;
	}




	public List<ConfigurationUtilBean> getPageFieldsColumns() {
	return pageFieldsColumns;
	}




	public void setPageFieldsColumns(List<ConfigurationUtilBean> pageFieldsColumns) {
	this.pageFieldsColumns = pageFieldsColumns;
	}




	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request=request;
	
	}




	public boolean isHodStatus() {
	return hodStatus;
	}




	public void setHodStatus(boolean hodStatus) {
	this.hodStatus = hodStatus;
	}




	public boolean isMgtStatus() {
	return mgtStatus;
	}




	public void setMgtStatus(boolean mgtStatus) {
	this.mgtStatus = mgtStatus;
	}
	
	

}