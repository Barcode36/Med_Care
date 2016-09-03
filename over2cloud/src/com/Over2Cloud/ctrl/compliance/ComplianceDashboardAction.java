package com.Over2Cloud.ctrl.compliance;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class ComplianceDashboardAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceDashboardAction.class);
	@SuppressWarnings("rawtypes")
    Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String deptLevel = (String) session.get("userDeptLevel");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String weekly_pending="0";
	private String monthly_pending="0";
	private String yearly_pending="0";
	private String departName;
	private String departId;
	private String graphId;
	private 	String 	frequency;
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();
	private 	Integer 	rows = 0;
	private 	Integer 	page = 0;
	private 	String 	sord = "";
	private 	String 	sidx = "";
	private 	String 	searchField = "";
	private 	String 	searchString = "";
	private 	String 	searchOper = "";
	private 	Integer 	total = 0;
	private 	Integer 	records = 0;
	private 	boolean 	loadonce = false;
	private 	String 	oper;
	private 	String 	id;
	private 	List<Object> 	masterViewList;
	private 	String 	mainHeaderName;
	private 	String 	modifyFlag;
	private	String          status;
	private	String	complID;
	private 	String 	allYearlyTotal;
	private 	String 	allYearlyMissedTotal;
	private 	String 	allMonthlyTotal;
	private 	String 	allMonthlyMissedTotal;
	private 	String 	allWeeklyTotal;
	private 	String 	allWeeklyMissedTotal;
	private 	String 	allCompId="";
	private 	String 	allCompId1st="";
	private 	String 	allCompId4th="";
	private 	String 	allselfDueCompliance="0";
    private	 	String 	allselfRem1Compliance="0";
    private 	String 	allselfRem2Compliance="0"; 
    private 	String 	allselfRem3Compliance="0";
    private 	String 	allteamDueCompliance="0";
    private 	String 	allteamRem1Compliance="0";
    private 	String 	allteamRem2Compliance="0";
    private 	String 	allteamRem3Compliance="0";
	private 	String 	data4;
	private 	String 	allAgeingYearlyTotal;
	private 	String 	allAgeingMonthlyTotal;
	private 	String 	allAgeingWeeklyTotal;
	private 	String 	allAgeingCompId="";
	private String orgName="";
	private String address="";
	private String city="";
	private String pincode="";
	private String deptName="";
	private String taskName="";
	private String dueDate="";
	private String taskType="";
	private String actionBy=null;
	private String actionTakenDate="";
	private String dueTime="";
	private String reminderFor="";
	private String tableName;
	private String excelName;
	private String selectedId;
	private String[] columnNames;
	private String tableAlis;
	private String divName;
	private Map<String,String > columnMap;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private Map<String, String> previousMonthUserCompDetail=null;
	private Map<String, String> totalComplStatusMap=null;
	private Map<String, String> todayCompDetail=null;
	List<ComplianceDashboardBean> userWiseUpcomeingComplList=null;
	List<ComplianceDashboardBean> deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	ComplianceDashboardBean complBeanForDashboard;
	ComplianceDashboardBean complageingDashboard;
	ComplianceDashboardBean totalComplDashboard;
	ComplianceDashboardBean totalDueTodayDashboard;
	ComplianceDashboardBean previousMonthComplDashboard;
	ComplianceDashboardBean escLevelCompDetailObj;
	private String totalOrMissed;
	private String oneMonthBeforeDate;
	private boolean reportPrint;
	private Map<String, String> mgmtCompDetail=null;
	private Map<String, String> ageingCompDetail=null;
	private String totalPending;
	private String totalMissed;
	private String totalDone;
	private String totalSnooze;
	private String lastMonthDoneTotal;
	private String lastMonthPendingTotal;
	private String esc1Total;
	private String esc2Total;
	private String esc3Total;
	private String esc4Total;
	private String esc5Total;
	private Map<String, String> escLevelCompDetail=null; 
	private String maximizeDivBlock;
	private String chartWidth;
	private String chartHeight;
	boolean legendShow;
	private String fromDate;
	private String toDate;
	private String totalSelfDue;
	private String totalSelfRem1;
	private String totalSelfRem2;
	private String totalSelfRem3;
	private String totalTeamDue;
	private String totalTeamRem1;
	private String totalTeamRem2;
	private String totalTeamRem3;
	private String compId;
	private Map<Integer,String> deptList=null;
	private JSONArray jsonArr=null;
	private String employee;
	private String actionName;
	private Map<String,String> deptNameList;
	private Map<Integer,String> empList;
	private JSONArray jsonArray;
	private String maxDivId;
	private JSONObject jsonObject;
	private String orgImgPath;
	
	@SuppressWarnings("rawtypes")
    public String getComplDashBoardDetail()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	complBeanForDashboard=new ComplianceDashboardBean();
	try
	{
	String dept_subdept_id ="";
	List empData = new ComplianceCommonOperation().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel,connectionSpace);
	Object[] object =null;
	if (empData!=null && empData.size()>0)
	{
	   for (Iterator iterator = empData.iterator(); iterator.hasNext();) 
	   {
	object = (Object[]) iterator.next();
	if(deptLevel.equalsIgnoreCase("2"))
	{
	dept_subdept_id = object[3].toString();
	}
	else
	{
	dept_subdept_id = object[3].toString();
	}
	   }
	}
	if(deptLevel.equalsIgnoreCase("2"))
	{
	List temp=cmnCompliance.getDeptDetailBySubdeptId(dept_subdept_id);
	if(temp!=null && temp.size()>0)
	{
	for (Iterator iterator = temp.iterator(); iterator.hasNext();)
	{
	object = (Object[]) iterator.next();
	complBeanForDashboard.setDepartName(object[0].toString());
	complBeanForDashboard.setDepartId(object[1].toString());
	}
	}
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
    public String getUserComplDashBoardDetail()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	complBeanForDashboard=new ComplianceDashboardBean();
	try
	{
	/* Get Department Data */
	String dept_subdept_id ="";
	Object[] object =null;
	List empData = new ComplianceCommonOperation().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel,connectionSpace);
	if (empData!=null && empData.size()>0)
	{
	   for (Iterator iterator = empData.iterator(); iterator.hasNext();) 
	   {
	object = (Object[]) iterator.next();
	if(deptLevel.equalsIgnoreCase("2"))
	{
	dept_subdept_id = object[3].toString();
	}
	else
	{
	dept_subdept_id = object[3].toString();
	}
	   }
	}
	setDepartId(dept_subdept_id);
	if(deptLevel.equalsIgnoreCase("2"))
	{
	String subDeptIDIS=cmnCompliance.getTaskDepartmentOrSubDepart(dept_subdept_id,"deptName","department");
	setDepartName(subDeptIDIS);
	}
	else
	{
	//String subDeptIDIS=cmnCompliance.getTaskDepartmentOrSubDepart(dept_subdept_id,"deptName","department");
	}
	String currentWeekCount=cmnCompliance.getCounterOfCompliance("CurrentWeek",dept_subdept_id,connectionSpace);
	String currentMonthCount=cmnCompliance.getCounterOfCompliance("CurrentMonth",dept_subdept_id,connectionSpace);
	String currentYearCount=cmnCompliance.getCounterOfCompliance("CurrentYear",dept_subdept_id,connectionSpace);
	String currentWeek=cmnCompliance.getCounterForDashboard(userName,"currentWeek",connectionSpace);
	if(currentWeekCount!=null && currentWeekCount!="")
	{
	setWeekly_pending(currentWeekCount);
	}
	else
	{
	setWeekly_pending("0");
	}
	if(currentMonthCount!=null && currentMonthCount!="")
	{
	setMonthly_pending(currentMonthCount);
	}
	else
	{
	setMonthly_pending("0");
	}
	
	if(currentYearCount!=null && currentYearCount!="")
	{
	setYearly_pending(currentYearCount);
	}
	else
	{
	setYearly_pending("0");
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
    //getMgmtComplDashBoardDetail	
	public String getMgmtComplDashBoardDetail()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	oneMonthBeforeDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()));
	getTotalComplByStatus();
	getMgmtDashboardCounter();
	getAgeingComplDashBoardDetail();
	getPreviousMonthCompDetail();
	getUpcomingCompl();
	getDoneLevel();
	getTodayDueTask();
	
	
	return SUCCESS;
	}
	catch (Exception ex) 
	{
	
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	
	//for all table counters
	public String dataCounteForDash(){
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{ 
	if(maximizeDivBlock!=null){
	if(maximizeDivBlock.equalsIgnoreCase("1stTableDta")){
	getTotalComplByStatus();
	return SUCCESS;
	}
	else if(maximizeDivBlock.equalsIgnoreCase("2ndDataBlockDiv")){
	getMgmtDashboardCounter();
	return SUCCESS;
	}
	else if(maximizeDivBlock.equalsIgnoreCase("3rdDataBlockDiv")){
	getAgeingComplDashBoardDetail();
	return SUCCESS;
	}
	else if(maximizeDivBlock.equalsIgnoreCase("4thDataBlockDiv")){
	getPreviousMonthCompDetail();
	return SUCCESS;
	}	
	else if(maximizeDivBlock.equalsIgnoreCase("7thDataBlockDiv")){
	getTodayDueTask();
	return SUCCESS;
	}
	
	}
	
	
	return ERROR;
	}
	catch (Exception ex) 
	{
	
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	
	}
	
	
	

	@SuppressWarnings("rawtypes")
    private String getTodayDueTask() 
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	ComplianceUniversalAction CUA=new ComplianceUniversalAction();
	try
	{
	
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	List<ComplianceDashboardBean> tempList=null;
	todayCompDetail = new LinkedHashMap<String, String>();
	totalDueTodayDashboard=new ComplianceDashboardBean();
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	tempList=cmnCompliance.getTotalTodayDueTask(userEmpID,userContID);
	}
	
	int temp=0,temp2=0,temp4=0,temp5=0,temp6=0,temp7=0,temp8=0,temp9=0;
	String temp10=null;
	jsonArray= new JSONArray();
	totalDueTodayDashboard.setComplList(tempList);
	if (tempList!=null &&tempList.size()>0) 
	{
	for(ComplianceDashboardBean obj:tempList)
	{
	temp=temp+Integer.valueOf(obj.getSelfDueDate());
	temp2=temp2+Integer.valueOf(obj.getSelfRem1());
	temp4=temp4+Integer.valueOf(obj.getSelfRem2());
	temp5=temp5+Integer.valueOf(obj.getSelfRem3());
	temp6=temp6+Integer.valueOf(obj.getTeamDueDate());
	temp7=temp7+Integer.valueOf(obj.getTeamRem1());
	temp8=temp8+Integer.valueOf(obj.getTeamRem2());
	temp9=temp9+Integer.valueOf(obj.getTeamRem3());
	temp10=obj.getDepartName();
	allselfDueCompliance  = allselfDueCompliance + obj.getSelfDueCompliance()+",";
	allselfRem1Compliance = allselfRem1Compliance +obj.getSelfRem1Compliance()+",";
	allselfRem2Compliance = allselfRem2Compliance + obj.getSelfRem2Compliance()+",";
	allselfRem3Compliance = allselfRem3Compliance +obj.getSelfRem3Compliance()+",";
	    allteamDueCompliance  = allteamDueCompliance +obj.getTeamDueCompliance()+",";
	    allteamRem1Compliance = allteamRem1Compliance + obj.getTeamRem1Compliance()+",";
	    allteamRem2Compliance = allteamRem2Compliance +obj.getTeamRem2Compliance()+",";
	    allteamRem3Compliance = allteamRem3Compliance +obj.getTeamRem3Compliance()+",";
	}
	}
	totalSelfDue=String.valueOf(temp);
	totalSelfRem1=String.valueOf(temp2);
	totalSelfRem2=String.valueOf(temp4);
	totalSelfRem3=String.valueOf(temp5);
	totalTeamDue=String.valueOf(temp6);
	totalTeamRem1=String.valueOf(temp7);
	totalTeamRem2=String.valueOf(temp8);
	totalTeamRem3=String.valueOf(temp9);
	
	todayCompDetail.put("Self Due", totalSelfDue);
	todayCompDetail.put("Team Due", totalTeamDue);
	todayCompDetail.put("Self Reminder 1", totalSelfRem1);
	todayCompDetail.put("Team Reminder 1", totalTeamRem1);
	todayCompDetail.put("Self Reminder 2", totalSelfRem2);
	todayCompDetail.put("Team Reminder 2", totalTeamRem2);
	todayCompDetail.put("Self Reminder 3", totalSelfRem3);
	todayCompDetail.put("Team Reminder 3", totalTeamRem3);
	todayCompDetail.put("dept", temp10);
	jsonArray.add(todayCompDetail);
	if (allselfDueCompliance!=null && !allselfDueCompliance.equalsIgnoreCase("")) 
	{
	allselfDueCompliance= allselfDueCompliance.substring(0, allselfDueCompliance.length()-1);
	}
	if (allselfRem1Compliance!=null && !allselfRem1Compliance.equalsIgnoreCase("")) 
	{
	allselfRem1Compliance= allselfRem1Compliance.substring(0, allselfRem1Compliance.length()-1);	
	}
	if (allselfRem2Compliance!=null && !allselfRem2Compliance.equalsIgnoreCase("")) 
	{
	allselfRem2Compliance= allselfRem2Compliance.substring(0, allselfRem2Compliance.length()-1);
	}
	if (allselfRem3Compliance!=null && !allselfRem3Compliance.equalsIgnoreCase("")) 
	{
	allselfRem3Compliance= allselfRem3Compliance.substring(0, allselfRem3Compliance.length()-1);	
	}
	if (allteamDueCompliance!=null && !allteamDueCompliance.equalsIgnoreCase("")) 
	{
	allteamDueCompliance= allteamDueCompliance.substring(0, allteamDueCompliance.length()-1);
	}
	if (allteamRem1Compliance!=null && !allteamRem1Compliance.equalsIgnoreCase("")) 
	{
	allteamRem1Compliance= allteamRem1Compliance.substring(0, allteamRem1Compliance.length()-1);	
	}
	if (allteamRem2Compliance!=null && !allteamRem2Compliance.equalsIgnoreCase("")) 
	{
	allteamRem2Compliance= allteamRem2Compliance.substring(0, allteamRem2Compliance.length()-1);
	}
	if (allteamRem3Compliance!=null && !allteamRem3Compliance.equalsIgnoreCase("")) 
	{
	allteamRem3Compliance= allteamRem3Compliance.substring(0, allteamRem3Compliance.length()-1);	
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}

	public String getMgmtDashboardCounter()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	complBeanForDashboard=new ComplianceDashboardBean();
	ComplianceUniversalAction CUA=new ComplianceUniversalAction();
	try
	{
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	
	mgmtCompDetail=new LinkedHashMap<String, String>();
	deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	complBeanForDashboard=new ComplianceDashboardBean();
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	deptWiseComplList=cmnCompliance.getComplianceMgmntDetailForDasgboard(connectionSpace,"totalMissed",userEmpID,userContID);
	}
	complBeanForDashboard.setComplList(deptWiseComplList);
	int temp=0,temp1=0,temp2=0,temp3=0,temp4=0,temp5=0;
	String temp6=null;
	jsonArray = new JSONArray();
	if(deptWiseComplList!=null && deptWiseComplList.size()>0)
	{
	for(ComplianceDashboardBean obj:deptWiseComplList)
	{
	temp=temp+Integer.valueOf(obj.getAnnualTotal());
	temp1=temp1+Integer.valueOf(obj.getAnnualMissed());
	temp2=temp2+Integer.valueOf(obj.getMonthlyTotal());
	temp3=temp3+Integer.valueOf(obj.getMonthlyMissed());
	temp4=temp4+Integer.valueOf(obj.getWeeklyTotal());
	temp5=temp5+Integer.valueOf(obj.getWeeklyMissed());
	temp6=obj.getDepartName();
	allCompId=allCompId+obj.getCompId()+",";
	}
	}
	if(allCompId!=null && !allCompId.equals(""))
	{
	allCompId=allCompId.substring(0,(allCompId.toString().length()-1));
	}
	allYearlyTotal=String.valueOf(temp);
	allYearlyMissedTotal=String.valueOf(temp1);
	allMonthlyTotal=String.valueOf(temp2);
	allMonthlyMissedTotal=String.valueOf(temp3);
	allWeeklyTotal=String.valueOf(temp4);
	allWeeklyMissedTotal=String.valueOf(temp5);
	
	mgmtCompDetail.put("Yearly Total", allYearlyTotal);
	mgmtCompDetail.put("Yearly Missed", allYearlyMissedTotal);
	mgmtCompDetail.put("Monthly Total", allMonthlyTotal);
	mgmtCompDetail.put("Monthly Missed", allMonthlyMissedTotal);
	mgmtCompDetail.put("Weekly Total", allWeeklyTotal);
	mgmtCompDetail.put("Weekly Missed", allWeeklyMissedTotal);
	mgmtCompDetail.put("dept", temp6);
	jsonArray.add(mgmtCompDetail);
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
public String getUserDataForComplGrid()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	setDepartId(getDepartId());
	setStatus(getStatus());
	setViewProp();
	return SUCCESS;
	}
	catch (Exception exp) 
	{
	exp.printStackTrace();
	log.error("@::>> Problem in ComplianceDashBoardAction in method getUserDataForComplGrid()",exp);
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
public String getDataForComplGrid()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	setViewProp();
	return SUCCESS;
	}
	catch (Exception exp) 
	{
	exp.printStackTrace();
	log.error("@::>> Problem in ComplianceDashBoardAction in method getDataForComplGrid()",exp);
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}

@SuppressWarnings("unchecked")
public void setViewProp()
{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("actionStatus");
	gpv.setHeadingName("Status");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setFormatter("true");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.compid_prefix");
	gpv.setHeadingName("Task Id");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.compid_suffix");
	gpv.setHeadingName("Task Suffix");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("true");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("dept.deptName");
	gpv.setHeadingName(new ComplianceUniversalAction().getFieldName("mapped_dept_config_param","dept_config_param","deptName"));
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("ctyp.taskType");
	gpv.setHeadingName("Task Type");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	masterViewProp.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("comp.escalation");
	gpv.setHeadingName("Escalation");
	gpv.setEditable("false");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(50);
	masterViewProp.add(gpv);
	
	List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_compliance_configuration",accountID,connectionSpace,"",0,"table_name","compliance_configuration");
	for(GridDataPropertyView gdp:returnResult)
	{
	if(!gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("taskType") && !gdp.getColomnName().equals("userName")
	&& !gdp.getColomnName().equals("uploadDoc") && !gdp.getColomnName().equals("escalation") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time")&& !gdp.getColomnName().equals("compid_suffix")
	&& !gdp.getColomnName().equals("uploadDoc1") && !gdp.getColomnName().equals("taskBrief") && !gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("actionStatus")
	&& !gdp.getColomnName().equals("fromDate") && !gdp.getColomnName().equals("actionTaken")&& !gdp.getColomnName().equals("compid_prefix")
	&& !gdp.getColomnName().equals("l1EscDuration") && !gdp.getColomnName().equals("l2EscDuration") && !gdp.getColomnName().equals("l3EscDuration") && !gdp.getColomnName().equals("l4EscDuration"))
	{
	gpv=new GridDataPropertyView();
	if(gdp.getColomnName().equalsIgnoreCase("taskName"))
	{
	gpv.setColomnName("ctn."+gdp.getColomnName());
	}
	else
	{
	gpv.setColomnName("comp."+gdp.getColomnName());
	}
	if (gdp.getColomnName().equalsIgnoreCase("dueDate") || gdp.getColomnName().equalsIgnoreCase("snooze_date")) 
	{
	gpv.setHeadingName(gdp.getHeadingName()+" & Time");
	} 
	else 
	{
	gpv.setHeadingName(gdp.getHeadingName());
	}
	
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	gpv.setWidth(gdp.getWidth());
	masterViewProp.add(gpv);
	}
	}
	session.put("masterViewProp", masterViewProp);
	}
}
	@SuppressWarnings("rawtypes")
	public String getAgeingComplDashBoardDetail()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	try
	{
	String userEmpID=null;
	String user = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	
	deptWiseComplList=new ArrayList<ComplianceDashboardBean>();
	complageingDashboard=new ComplianceDashboardBean();
	ageingCompDetail=new LinkedHashMap<String, String>();
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	deptWiseComplList=cmnCompliance.getComplianceMgmntDetailForDasgboard(connectionSpace,"upcomeingDues",userEmpID,userContID);
	}
	
	complageingDashboard.setComplList(deptWiseComplList);
	jsonArray= new JSONArray();
	int temp=0,temp2=0,temp4=0;
	String temp6=null;
	if(deptWiseComplList!=null && deptWiseComplList.size()>0)
	{
	for(ComplianceDashboardBean obj:deptWiseComplList)
	{
	temp=temp+Integer.valueOf(obj.getAnnualTotal());
	temp2=temp2+Integer.valueOf(obj.getMonthlyTotal());
	temp4=temp4+Integer.valueOf(obj.getWeeklyTotal());
	temp6=obj.getDepartName();
	allAgeingCompId=allAgeingCompId+obj.getCompId()+",";
	}
	}
	if(allAgeingCompId!=null && !allAgeingCompId.equals(""))
	{
	allAgeingCompId=allAgeingCompId.substring(0,(allAgeingCompId.toString().length()-1));
	}
	allAgeingYearlyTotal=String.valueOf(temp);
	allAgeingMonthlyTotal=String.valueOf(temp2);
	allAgeingWeeklyTotal=String.valueOf(temp4);
	
	ageingCompDetail.put("This Year", allAgeingYearlyTotal);
	ageingCompDetail.put("This Month", allAgeingMonthlyTotal);
	ageingCompDetail.put("This Week", allAgeingWeeklyTotal);
	ageingCompDetail.put("dept", temp6);
	jsonArray.add(ageingCompDetail);
	
	return SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	@SuppressWarnings("rawtypes")
    public String getTotalComplByStatus()
	{boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	totalComplStatusMap=new LinkedHashMap<String, String>();
	try
	{
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	jsonArray = new JSONArray();
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	totalComplDashboard=new ComplianceDashboardBean();
	List<ComplianceDashboardBean> tempList=null;
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	tempList=cmnCompliance.getTotalComplByStatus(userEmpID,userContID);
	}
	int temp=0,temp2=0,temp4=0,temp5=0;
	String temp6=null;
	totalComplDashboard.setComplList(tempList);
	
	if(tempList!=null && tempList.size()>0)
	{
	for(ComplianceDashboardBean obj:tempList)
	{
	temp=temp+Integer.valueOf(obj.getPending());
	temp2=temp2+Integer.valueOf(obj.getDone());
	temp4=temp4+Integer.valueOf(obj.getMissed());
	temp5=temp5+Integer.valueOf(obj.getSnooze());
	temp6=obj.getDepartName();
	
	allCompId1st = allCompId1st+obj.getCompId()+",";
	
	}
	if(allCompId1st!=null && !allCompId1st.equals(""))
	{
	allCompId1st=allCompId1st.substring(0,(allCompId1st.toString().length()-1));
	}
	}
	
	totalPending=String.valueOf(temp);
	totalDone=String.valueOf(temp2);
	totalMissed=String.valueOf(temp4);
	totalSnooze=String.valueOf(temp5);
	totalComplStatusMap.put("Pending", totalPending);
	totalComplStatusMap.put("Done", totalDone);
	totalComplStatusMap.put("Missed", totalMissed);
	totalComplStatusMap.put("Snooze", totalSnooze);
	totalComplStatusMap.put("dept", temp6);
	
	jsonArray.add(totalComplStatusMap);
	
	return SUCCESS;
	
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
}
	
	@SuppressWarnings("rawtypes")
    public String getUpcomingCompl()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	userWiseUpcomeingComplList=new ArrayList<ComplianceDashboardBean>();
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	try
	{
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	userWiseUpcomeingComplList=cmnCompliance.getAllUpcomingCompl(connectionSpace,userEmpID, userContID);
	}
	return SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	@SuppressWarnings("rawtypes")
    public String getPreviousMonthCompDetail()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	previousMonthUserCompDetail=new LinkedHashMap<String, String>();
	try
	{
	previousMonthComplDashboard=new ComplianceDashboardBean();
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	
	String userContID = null;
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	List<ComplianceDashboardBean> tempList=null;
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	tempList=cmnCompliance.getPreviousMonthCompDetail(userEmpID, userContID);
	}
	
	previousMonthComplDashboard.setComplList(tempList);
	int temp=0,temp1=0;
	jsonArray = new JSONArray();
	String temp2=null;
	if (tempList!=null && tempList.size()>0) 
	{
	for(ComplianceDashboardBean obj:tempList)
	{
	temp=temp+Integer.valueOf(obj.getPending());
	temp1=temp1+Integer.valueOf(obj.getDone());
	temp2=obj.getDepartName();
	allCompId4th = allCompId4th+obj.getCompId()+",";
	}
	if (allCompId4th!=null && allCompId4th.length()>0) 
	{
	allCompId4th = allCompId4th.substring(0, allCompId4th.length()-1);
	}
	}
	previousMonthUserCompDetail.put("Done",String.valueOf(temp1));
	previousMonthUserCompDetail.put("Pending",String.valueOf(temp));
	previousMonthUserCompDetail.put("dept", temp2);
	jsonArray.add(previousMonthUserCompDetail);
	lastMonthDoneTotal=String.valueOf(temp1);
	lastMonthPendingTotal=String.valueOf(temp);
	fromDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())) ;
	toDate = DateUtil.getCurrentDateIndianFormat();
	return SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	@SuppressWarnings("rawtypes")
	public String getDoneLevel()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	ComplianceCommonOperation cmnCompliance=new ComplianceCommonOperation();
	escLevelCompDetail=new LinkedHashMap<String, String>();
	try
	{
	escLevelCompDetailObj=new ComplianceDashboardBean();
	String userEmpID=null;
	String user = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
	userEmpID=new ComplianceUniversalAction().getEmpDataByUserName(userName)[5];
	List<ComplianceDashboardBean> tempList=cmnCompliance.getDoneLevel(userEmpID);
	escLevelCompDetailObj.setComplList(tempList);
	int temp1=0,temp2=0,temp3=0,temp4=0,temp5=0;
	for(ComplianceDashboardBean obj:tempList)
	{
	temp1=temp1+Integer.valueOf(obj.getEsc1Count());
	temp2=temp2+Integer.valueOf(obj.getEsc2Count());
	temp3=temp3+Integer.valueOf(obj.getEsc3Count());
	temp4=temp4+Integer.valueOf(obj.getEsc4Count());
	temp5=temp5+Integer.valueOf(obj.getEsc5Count());
	}
	escLevelCompDetail.put("Level 1",String.valueOf(temp1));
	escLevelCompDetail.put("Level 2",String.valueOf(temp2));
	escLevelCompDetail.put("Level 3",String.valueOf(temp3));
	escLevelCompDetail.put("Level 4",String.valueOf(temp4));
	escLevelCompDetail.put("Level 5",String.valueOf(temp5));
	esc1Total=String.valueOf(temp1);
	esc2Total=String.valueOf(temp2);
	esc3Total=String.valueOf(temp3);
	esc4Total=String.valueOf(temp4);
	esc5Total=String.valueOf(temp5);
	return SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	public String getMaximizeBlockData()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	if(maximizeDivBlock!=null)
	{
	
	if(maximizeDivBlock.equalsIgnoreCase("1stPie"))
	{
	getTotalComplByStatus();
	returnResult="VIEWGRAPHSUCCESS";
	graphId ="max1sPieGraph";
	chartWidth="500px";
	chartHeight="290px";
	legendShow=true;
	}else if(maximizeDivBlock.equalsIgnoreCase("1stDonut"))
	{
	getTotalComplByStatus();
	returnResult="VIEWDONUTGRAPHSUCCESS";
	graphId ="1stDonutChart";
	chartWidth="500px";
	chartHeight="290px";
	
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("1stTableDta"))
	{
	getTotalComplByStatus();
	returnResult="VIEWTABLESUCCESS";
	}else if(maximizeDivBlock.equalsIgnoreCase("1stBarGraph") || maximizeDivBlock.equalsIgnoreCase("1stColumn2AxesBar")
	|| maximizeDivBlock.equalsIgnoreCase("1stBubbleGraph")|| maximizeDivBlock.equalsIgnoreCase("1stLine")|| maximizeDivBlock.equalsIgnoreCase("1stPieGraph"))
	{
	
	returnResult="VIEW1STDASHGRAPH";
	}
	else if(maximizeDivBlock.equalsIgnoreCase("2ndDataBlockDiv"))
	{
	getMgmtDashboardCounter();
	returnResult="VIEWTABLESUCCESS";
	}
	else if(maximizeDivBlock.equalsIgnoreCase("2ndPie"))
	{
	graphId ="2ndPieGraphMax";
	getMgmtDashboardCounter();
	returnResult="VIEWGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	legendShow=true;
	} else if(maximizeDivBlock.equalsIgnoreCase("2ndDonut"))
	{
	graphId ="2ndDonutGraphMax";
	getMgmtDashboardCounter();
	returnResult="VIEWDONUTGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("2ndStackBarGraph")||maximizeDivBlock.equalsIgnoreCase("2ndColumn2AxesGraph")
	||maximizeDivBlock.equalsIgnoreCase("2ndBubbleGraph")||maximizeDivBlock.equalsIgnoreCase("2ndLine")||maximizeDivBlock.equalsIgnoreCase("2ndPieGraph"))
	{
	returnResult ="VIEW2NDDASHGRAPH";
	}
	else if(maximizeDivBlock.equalsIgnoreCase("3rdDataBlockDiv"))
	{
	getAgeingComplDashBoardDetail();
	returnResult="VIEWTABLESUCCESS";
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("3rdGraphBlockDiv"))
	{
	getAgeingComplDashBoardDetail();
	graphId ="maxChartDataView5";
	returnResult="VIEWGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	legendShow=true;
	}
	 else if(maximizeDivBlock.equalsIgnoreCase("3rdDonut"))
	{
	graphId ="3rdDonutGraphMax";
	getAgeingComplDashBoardDetail();
	returnResult="VIEWDONUTGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	
	}
	 else if(maximizeDivBlock.equalsIgnoreCase("4thDonut"))
	{
	graphId ="4rthDonutGraphMax";
	getPreviousMonthCompDetail();
	returnResult="VIEWDONUTGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("3rdStackBarGraph")||maximizeDivBlock.equalsIgnoreCase("3rdColumn2AxesGraph")
	||maximizeDivBlock.equalsIgnoreCase("3rdBubbleGraph")||maximizeDivBlock.equalsIgnoreCase("3rdLine")||maximizeDivBlock.equalsIgnoreCase("3rdPieGraph"))
	{
	returnResult="VIEW3RDDASHGRAPH";
	}
	else if(maximizeDivBlock.equalsIgnoreCase("4thDataBlockDiv"))
	{
	getPreviousMonthCompDetail();
	returnResult="VIEWTABLESUCCESS";
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("4thGraphBlockDiv"))
	{
	getPreviousMonthCompDetail();
	graphId ="maxChartDataView7";
	returnResult="VIEWGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	legendShow=true;
	}else if(maximizeDivBlock.equalsIgnoreCase("4thStackBarGraph")||maximizeDivBlock.equalsIgnoreCase("4thColumn2AxesGraph")
	||maximizeDivBlock.equalsIgnoreCase("4thBubbleGraph")||maximizeDivBlock.equalsIgnoreCase("4thLine")||maximizeDivBlock.equalsIgnoreCase("4thPieGraph"))
	{
	returnResult="VIEW4RTHDASHGRAPH";
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("5thMaxData"))
	{
	getUpcomingCompl();
	returnResult="VIEWTABLESUCCESS";
	}
	else if(maximizeDivBlock.equalsIgnoreCase("7thDataBlockDiv"))
	{
	getTodayDueTask();
	returnResult="VIEWTABLESUCCESS";
	}
	
	else if(maximizeDivBlock.equalsIgnoreCase("7thGraphBlockDiv"))
	{
	getTodayDueTask();
	graphId ="maxChartDataView11";
	returnResult="VIEWGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	legendShow=true;
	}else if(maximizeDivBlock.equalsIgnoreCase("7thStackBarGraph")||maximizeDivBlock.equalsIgnoreCase("7thColumn2AxesGraph")
	||maximizeDivBlock.equalsIgnoreCase("7thBubbleGraph")||maximizeDivBlock.equalsIgnoreCase("7thLine")||maximizeDivBlock.equalsIgnoreCase("7thPieGraph"))
	{
	returnResult="VIEW7THDASHGRAPH";
	}
	 
	else if(maximizeDivBlock.equalsIgnoreCase("7thDonut"))
	{
	graphId ="7thDonutGraphMax";
	getTodayDueTask();
	returnResult="VIEWDONUTGRAPHSUCCESS";
	chartWidth="500px";
	chartHeight="290px";
	
	}
	
	
	}
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	public String getCompDetail4Print()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	String orgDetail = (String)session.get("orgDetail");
	String orgName="",address="",city="",pincode="";
	if (orgDetail!=null && !orgDetail.equals("")) 
	{
	orgImgPath=new CommonWork().getOrganizationImage((SessionFactory)session.get("connectionSpace"));
	orgName=orgDetail.split("#")[0];
	    address=orgDetail.split("#")[1];
	    city=orgDetail.split("#")[2];
	    pincode=orgDetail.split("#")[3];
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder("");	
	query.append("SELECT dept.deptName,ts.taskName,ctyp.taskType,comp.frequency,comp.dueDate,comp.dueTime,");
	query.append("comp.reminder_for,comp.actionStatus FROM compliance_details AS comp ");
	query.append(" INNER JOIN compliance_task AS ts ON ts.id=comp.taskName");
	query.append(" INNER JOIN department AS dept ON dept.id=ts.departName ");
	query.append(" INNER JOIN compliance_report AS cr ON comp.id=cr.complID ");
	query.append(" INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType WHERE comp.id="+id);
	//System.out.println(query.toString());
	List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null)
	{
	Iterator itr=dataList.iterator();
	if(itr.hasNext())
	{
	Object ob[]=(Object[])itr.next();
	this.setDeptName(ob[0].toString());
	this.setTaskName(ob[1].toString());
	this.setTaskType(ob[2].toString());
	this.setFrequency(new ComplianceReminderHelper().getFrequencyName(ob[3].toString()));
	this.setDueDate(DateUtil.convertDateToIndianFormat(ob[4].toString()));
	this.setDueTime(ob[5].toString());
	this.setReminderFor(ob[6].toString());
	if(ob[6].toString() != null)
	{
	StringBuilder empName= new StringBuilder();
	String empId=ob[6].toString().replace("#", ",").substring(0,(ob[6].toString().length()-1));
	String query2="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
	List data1=cbt.executeAllSelectQuery(query2,connectionSpace );
	if (data1!=null && data1.size()>0) 
	{
	for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();) 
	{
	Object object1 = (Object) iterator1.next();
	empName.append(object1.toString()+", ");
	}
	}
	if (empName!=null && !empName.toString().equalsIgnoreCase("")) 
	{
	this.setReminderFor(empName.toString().substring(0, empName.toString().length()-2));
	}
	else 
	{
	this.setReminderFor("NA");
	}
	}
	this.setStatus(ob[7].toString());
	}
	}
	/*if(this.reminderFor != null && this.reminderFor !="" )
	{
	String s[]=reminderFor.split("#");
	System.out.println(s[0].toString());
	}
	*/
	setOrgName(orgName);
	setAddress(address);
	setCity(city);
	setPincode(pincode);
	if(actionBy!=null)
	{
	reportPrint=true;
	}
	
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	public String getCurrentColumn()
	{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	returnResult = getColumnName("mapped_compliance_configuration","compliance_configuration", "comp");
	tableName = "compliance_details";
	excelName = "Operation Task Details";
	tableAlis = "comp";
	if (employee!=null && !employee.equalsIgnoreCase("")) 
	{
	divName = "sendMailDiv";
	}
	else
	{
	divName = "";
	}
	returnResult=SUCCESS;
	}
	catch (Exception ex) 
	{
	ex.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName,String basicTableName,String tableAllis)
	{
	try
	{
	List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID,connectionSpace, "", 0, "table_name",basicTableName);
	columnMap = new LinkedHashMap<String, String>();
	if (columnList != null && columnList.size() > 0) 
	{
	for (GridDataPropertyView gdp : columnList) 
	{
	if(gdp.getColomnName().equals("taskName"))
	{
	columnMap.put("dept.deptName",new ComplianceUniversalAction().getFieldName("mapped_dept_config_param","dept_config_param", "deptName"));
	columnMap.put("ctn.taskName",new ComplianceUniversalAction().getFieldName("mapped_compl_task_config","compl_task_config", "taskName"));
	}
	else if(!gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("reminder1") && !gdp.getColomnName().equals("compid_suffix")
	&& !gdp.getColomnName().equals("reminder2") && !gdp.getColomnName().equals("reminder3") && !gdp.getColomnName().equals("taskType") && !gdp.getColomnName().equals("userName")
	&& !gdp.getColomnName().equals("uploadDoc") && !gdp.getColomnName().equals("escalation") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time")
	&& !gdp.getColomnName().equals("uploadDoc1") && !gdp.getColomnName().equals("taskBrief") && !gdp.getColomnName().equals("msg")
	&& !gdp.getColomnName().equals("fromDate")  && !gdp.getColomnName().equals("remindMode") && !gdp.getColomnName().equals("actionTaken")
	&& !gdp.getColomnName().equals("l1EscDuration") && !gdp.getColomnName().equals("l2EscDuration") && !gdp.getColomnName().equals("l3EscDuration") && !gdp.getColomnName().equals("l4EscDuration")
	&& !gdp.getColomnName().equals("apply_working_day") && !gdp.getColomnName().equals("remind_days"))
	{
	columnMap.put(tableAllis+"."+gdp.getColomnName(),gdp.getHeadingName());
	}
	}
	}
	if (columnMap != null && columnMap.size() > 0) 
	{
	session.put("columnMap", columnMap);
	}
	return SUCCESS;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();

	if (sessionFlag)
	{
	try 
	{
	CommunicationHelper cmnHelper=new CommunicationHelper();
	List keyList = new ArrayList();
	List titleList = new ArrayList();
	StringBuilder emailIds=null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	if (columnNames != null && columnNames.length > 0) 
	{
	keyList = Arrays.asList(columnNames);
	Map<String, String> tempMap = new LinkedHashMap<String, String>();
	tempMap = (Map<String, String>) session.get("columnMap");
	if(session.containsKey("columnMap"))
	session.remove("columnMap");
	
	List dataList = null;
	StringBuilder query = new StringBuilder("");
	for (int index = 0; index < keyList.size(); index++)
	{
	titleList.add(tempMap.get(keyList.get(index)));
	}
	if (keyList != null && keyList.size() > 0) 
	{
	query.append("SELECT ");
	int i = 0;
	for (Iterator it = keyList.iterator(); it.hasNext();) 
	{
	Object obdata = (Object) it.next();
	if (obdata != null) {
	if (i < keyList.size() - 1) 
	{
	query.append(obdata.toString() + ",");
	} else 
	{
	if (obdata.toString().equalsIgnoreCase("comp.compid_prefix")) 
	{
	query.append(obdata.toString()+",comp.compid_suffix");
	}
	else 
	{
	query.append(obdata.toString());	
	}
	}
	}
	i++;
	}
	query.append(" FROM " + tableName + " AS "+ tableAlis);
	query.append(" LEFT JOIN compliance_task AS ctn ON ctn.id="+tableAlis+".taskName");
	query.append(" LEFT JOIN department AS dept ON dept.id=ctn.departName");
	query.append(" WHERE "+tableAlis+".id IN("+selectedId+")");
	
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	String orgDetail = (String)session.get("orgDetail");
	String[] orgData = null;
	String orgName="";
	if (orgDetail!=null && !orgDetail.equals("")) 
	{
	orgData = orgDetail.split("#");
	orgName=orgData[0];
	}
	
	excelFileName=new ComplianceExcelDownload().writeDataInExcel(dataList,titleList,keyList,excelName,orgName,true,connectionSpace);
	if (employee!=null  && !employee.equalsIgnoreCase("") )
	{
	
	String mailSubject="Operation Task Detail";
	List emaildata= cbt.executeAllSelectQuery("SELECT emailIdOne FROM employee_basic WHERE id IN("+employee+")", connectionSpace);
	if (emaildata!=null && emaildata.size()>0)
	{
	emailIds=new StringBuilder();
	for (Iterator iterator = emaildata.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	emailIds.append(object.toString()+",");
	}
	}
	String mailText=getConfigMail(dataList,titleList,mailSubject);
	String str[]=emailIds.toString().split(",");
	if (str!=null)
	{
	for(int j=0;j<str.length;j++)
	{
	if(str[j]!=null && !str[j].equalsIgnoreCase(""))
	{
	cmnHelper.addMail("","",str[j],mailSubject, mailText,"","Pending", "0",excelFileName,"Compliance",connectionSpace);
	}
	}
	}
	}
	File file = new File(excelFileName);
	if(excelFileName!=null)
	{
	excelStream=new FileInputStream(file);
	}
	excelFileName=excelFileName.substring(excelFileName.lastIndexOf("//")+2, excelFileName.length());
	excelFileName = file.getName();
	
	
	if(emailIds!=null && emailIds.length()>2)
	{
	returnResult="SENDMAILSUCCESS";
	addActionMessage("Send Mail Successfully ...");
	}
	else 
	{
	returnResult=SUCCESS;
	}
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	}
	else
	{
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
	public String beforeViewUserCompl()
	{
	String userContID = null;
	try
	{
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
	mainHeaderName="User Dashboard";
	
	deptNameList=new LinkedHashMap<String, String>();
	String userEmpID=null;
	String empIdofuser = (String) session.get("empIdofuser");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	userEmpID = empIdofuser.split("-")[1].trim();
	if (userContID!=null && !userContID.equalsIgnoreCase(""))
	{
	deptNameList = new ComplianceReportAction().getDeptByMappedEmp(userContID,userEmpID,"COMPL","");
	}	
	
	
	
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	setDepartId(userContID);
	setStatus("All");
	setViewProp();
	return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String getConfigMail(List dataList,List titleList,String mailTitle) 
    {
    	  StringBuilder mailtext = new StringBuilder("");
	  mailtext.append("<br><b>Hello,</b><br>");
	  mailtext.append(mailTitle);
	  mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
	  mailtext.append("<tr  bgcolor='#e8e7e8'>");
	  for(int i=0;i<titleList.size();i++)
	  {
	  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+titleList.get(i).toString().trim().replace(" ", "&nbsp;")+ "</b></td>");
	  }
	  mailtext.append("</tr>");

	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'>");
	Object[] object = (Object[]) iterator.next();
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	String dataValue = null;
	if(object[cellIndex]!=null)
	{
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	dataValue=DateUtil.convertDateToIndianFormat(object[cellIndex].toString());
	}
	else
	{
	if(titleList.get(cellIndex).toString().equalsIgnoreCase("Allot To") || titleList.get(cellIndex).toString().equalsIgnoreCase("Level 2") || titleList.get(cellIndex).toString().equalsIgnoreCase("Level 3") || titleList.get(cellIndex).toString().equalsIgnoreCase("Level 4") || titleList.get(cellIndex).toString().equalsIgnoreCase("Level 5"))
	{
	String empId=object[cellIndex].toString().replace("#", ",").substring(0,(object[cellIndex].toString().toString().length()-1));
	String query="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
	List data=new createTable().executeAllSelectQuery(query,connectionSpace);
	if(data!=null && data.size()>0)
	{
	StringBuilder builder=new StringBuilder();
	for(int i=0;i<data.size();i++)
	{
	builder.append(data.get(i).toString()+",");
	}
	dataValue=builder.toString().substring(0, builder.toString().length()-1);
	}
	}
	else if(titleList.get(cellIndex).toString().equalsIgnoreCase("Frequency"))
	{
	if(object[cellIndex].toString().equalsIgnoreCase("W"))
	dataValue="Weekly";
	else if(object[cellIndex].toString().equalsIgnoreCase("OT"))
	dataValue="One Time";
	else if(object[cellIndex].toString().equalsIgnoreCase("D"))
	dataValue="Daily";
	else if(object[cellIndex].toString().equalsIgnoreCase("M"))
	dataValue="Monthly";
	else if(object[cellIndex].toString().equalsIgnoreCase("BM"))
	dataValue="Bi-Monthly";
	else if(object[cellIndex].toString().equalsIgnoreCase("Q"))
	dataValue="Quaterly";
	else if(object[cellIndex].toString().equalsIgnoreCase("HY"))
	dataValue="Half Yearly";
	else if(object[cellIndex].toString().equalsIgnoreCase("Y"))
	dataValue="Yearly";
	
	}
	else if(titleList.get(cellIndex).toString().equalsIgnoreCase("Ownership"))
	{
	dataValue= DateUtil.makeTitle( object[cellIndex].toString()) ;
	}
	else if(titleList.get(cellIndex).toString().equalsIgnoreCase("Compliance Id"))
	{
	dataValue= (object[cellIndex].toString()+object[cellIndex+1].toString()) ;
	}
	else
	{
	dataValue=object[cellIndex].toString();
	}
	}
	}
	else
	{
	dataValue="NA";
	}
	mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+dataValue.trim().replace(" ", "&nbsp;")+"</td>");
	}
	mailtext.append("</tr>");
	}
	  mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	  mailtext.append("<BR>");
	  mailtext.append("<BR>");
	  mailtext.append("<br>");
	  mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	  return mailtext.toString();
    }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getBeforeMailData()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String orgLevel = (String) session.get("orgnztnlevel");
	String orgId = (String) session.get("mappedOrgnztnId");
	List colmName = new ArrayList();
	Map<String, Object> order = new HashMap<String, Object>();
	Map<String, Object> wherClause = new HashMap<String, Object>();
	String status="'Active'";
	colmName.add("id");
	colmName.add("deptName");
	wherClause.put("orgnztnlevel", orgLevel);
	wherClause.put("mappedOrgnztnId", orgId);
	wherClause.put("flag", status);
	order.put("deptName", "ASC");
	HelpdeskUniversalAction HUA= new HelpdeskUniversalAction();
	    deptList= new LinkedHashMap<Integer, String>();
	    List departmentlist=HUA.getDataFromTable("department", colmName,wherClause, order, connectionSpace);
	if (departmentlist!=null && departmentlist.size()>0)
	{
	for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if(object[0]!=null && object[1]!=null)
	{
	deptList.put ( Integer.parseInt(object[0].toString()),object[1].toString());
	}
	}
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
	return  LOGIN;
	}
	}
	@SuppressWarnings("rawtypes")
	public String getEmployeeDataList()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
            	CommonOperInterface cbt = new CommonConFactory().createInterface();
            	empList=new LinkedHashMap<Integer,String>();
            	System.out.println(departId);
            	if (departId!=null && !departId.equalsIgnoreCase("")) 
	{
	//jsonArr = new JSONArray();
            	
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	StringBuilder query=new StringBuilder();
	query.append(" SELECT emp.id,emp.empName FROM employee_basic AS emp");
	query.append(" INNER JOIN department AS dept ON dept.id=emp.deptname");
	query.append(" WHERE dept.id IN(" + departId + ") ORDER BY emp.empName ASC");
	List data2=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (data2!=null && data2.size()>0) 
	{//JSONObject formDetailsJson = new JSONObject();
	Object[] object = null;
	for (Iterator iterator = data2.iterator(); iterator.hasNext();) 
	{
	object = (Object[]) iterator.next();
	if (object!=null) 
	{
	/*formDetailsJson = new JSONObject();
	formDetailsJson.put("ID", object[0].toString());
	formDetailsJson.put("NAME", object[1].toString());
	jsonArr.add(formDetailsJson);*/
	
	empList.put(Integer.parseInt(object[0].toString()), object[1].toString());
	 }
	 }
	}
	 return SUCCESS;
	}
	else
	{
	return ERROR;
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return  LOGIN;
	}
	}
	//jsona data  for Compliance Status
	public String getJsonData4TaskStatus()
	{
	
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	getTotalComplByStatus();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	for (ComplianceDashboardBean pojo : totalComplDashboard.getComplList())
	 {
	jsonObject.put("dept",pojo.getDepartName());
	jsonObject.put("Pending", pojo.getPending());
	jsonObject.put("Snooze", pojo.getSnooze());
	jsonObject.put("Missed", pojo.getMissed());
	jsonObject.put("Done", pojo.getDone());
	
	jsonArray.add(jsonObject);
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
	
	//jsona data  for Compliance Frequency
	public String getJsonData4Frequncy()
	{
	
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	getMgmtDashboardCounter();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	for (ComplianceDashboardBean pojo : deptWiseComplList)
	 {
	jsonObject.put("dept",pojo.getDepartName());
	jsonObject.put("YearlyMissed", pojo.getAnnualMissed());
	jsonObject.put("YearlyTotal", pojo.getAnnualTotal());
	jsonObject.put("MonthlyTotal", pojo.getMonthlyTotal());
	jsonObject.put("MonthlyMissed", pojo.getMonthlyMissed());
	jsonObject.put("WeeklyTotal", pojo.getWeeklyTotal());
	jsonObject.put("WeeklyMissed", pojo.getWeeklyMissed());
	
	
	jsonArray.add(jsonObject);
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
	
	//jsona data  for Compliance Agieng
	public String getJsonData4Agieng()
	{
	
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	getAgeingComplDashBoardDetail();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	for (ComplianceDashboardBean pojo : deptWiseComplList)
	 {
	jsonObject.put("dept",pojo.getDepartName());
	jsonObject.put("ThisYear", pojo.getAnnualTotal());
	jsonObject.put("ThisMonth", pojo.getMonthlyTotal());
	jsonObject.put("ThisWeek", pojo.getWeeklyTotal());
	
	
	
	jsonArray.add(jsonObject);
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
	
	//jsona data  for Compliance Status Before Month
	public String getJsonData4StatusBeforeMonth()
	{
	
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	getPreviousMonthCompDetail();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	for (ComplianceDashboardBean pojo : previousMonthComplDashboard.getComplList())
	 {
	jsonObject.put("dept",pojo.getDepartName());
	jsonObject.put("Done", pojo.getDone());
	jsonObject.put("Pending", pojo.getPending());
	jsonArray.add(jsonObject);
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
	
	
	//jsona data  for Compliance Status Todays Pending Task
	public String getJsonData4TodayPending()
	{
	
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	getTodayDueTask();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	for (ComplianceDashboardBean pojo : totalDueTodayDashboard.getComplList())
	 {
	jsonObject.put("dept",pojo.getDepartName());
	jsonObject.put("SelfDue", pojo.getSelfDueDate());
	jsonObject.put("TeamDue", pojo.getTeamDueDate());
	jsonObject.put("SelfReminder1",pojo.getSelfRem1());
	jsonObject.put("TeamReminder1", pojo.getTeamRem1());
	jsonObject.put("SelfReminder2", pojo.getSelfRem2());
	jsonObject.put("TeamReminder2",pojo.getTeamRem2());
	jsonObject.put("SelfReminder3", pojo.getSelfRem3());
	jsonObject.put("TeamReminder3", pojo.getTeamRem3());
	jsonArray.add(jsonObject);
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

	public String getWeekly_pending() {
	return weekly_pending;
	}

	public void setWeekly_pending(String weekly_pending) {
	this.weekly_pending = weekly_pending;
	}

	public String getMonthly_pending() {
	return monthly_pending;
	}

	public void setMonthly_pending(String monthly_pending) {
	this.monthly_pending = monthly_pending;
	}

	public String getYearly_pending() {
	return yearly_pending;
	}

	public void setYearly_pending(String yearly_pending) {
	this.yearly_pending = yearly_pending;
	}

	public String getDepartName() {
	return departName;
	}

	public void setDepartName(String departName) {
	this.departName = departName;
	}

	public ComplianceDashboardBean getComplBeanForDashboard() {
	return complBeanForDashboard;
	}

	public void setComplBeanForDashboard(
	ComplianceDashboardBean complBeanForDashboard) {
	this.complBeanForDashboard = complBeanForDashboard;
	}
	public String getDepartId() {
	return departId;
	}
	public void setDepartId(String departId) {
	this.departId = departId;
	}
	public String getStatus() {
	return status;
	}
	public void setStatus(String status) {
	this.status = status;
	}
	public String getFrequency() {
	return frequency;
	}
	public void setFrequency(String frequency) {
	this.frequency = frequency;
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
	public String getMainHeaderName() {
	return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
	this.mainHeaderName = mainHeaderName;
	}
	public String getModifyFlag() {
	return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
	this.modifyFlag = modifyFlag;
	}
	public String getTotalOrMissed() {
	return totalOrMissed;
	}
	public void setTotalOrMissed(String totalOrMissed) {
	this.totalOrMissed = totalOrMissed;
	}
	public String getComplID() {
	return complID;
	}
	public void setComplID(String complID) {
	this.complID = complID;
	}

	public String getAllYearlyTotal() {
	return allYearlyTotal;
	}

	public void setAllYearlyTotal(String allYearlyTotal) {
	this.allYearlyTotal = allYearlyTotal;
	}

	public String getAllYearlyMissedTotal() {
	return allYearlyMissedTotal;
	}

	public void setAllYearlyMissedTotal(String allYearlyMissedTotal) {
	this.allYearlyMissedTotal = allYearlyMissedTotal;
	}

	public String getAllMonthlyTotal() {
	return allMonthlyTotal;
	}

	public void setAllMonthlyTotal(String allMonthlyTotal) {
	this.allMonthlyTotal = allMonthlyTotal;
	}

	public String getAllMonthlyMissedTotal() {
	return allMonthlyMissedTotal;
	}

	public void setAllMonthlyMissedTotal(String allMonthlyMissedTotal) {
	this.allMonthlyMissedTotal = allMonthlyMissedTotal;
	}

	public String getAllWeeklyTotal() {
	return allWeeklyTotal;
	}

	public void setAllWeeklyTotal(String allWeeklyTotal) {
	this.allWeeklyTotal = allWeeklyTotal;
	}

	public String getAllWeeklyMissedTotal() {
	return allWeeklyMissedTotal;
	}

	public String getData4() {
	return data4;
	}

	public void setData4(String data4) {
	this.data4 = data4;
	}

	public void setAllWeeklyMissedTotal(String allWeeklyMissedTotal) {
	this.allWeeklyMissedTotal = allWeeklyMissedTotal;
	}

	public ComplianceDashboardBean getComplageingDashboard() {
	return complageingDashboard;
	}

	public void setComplageingDashboard(ComplianceDashboardBean complageingDashboard) {
	this.complageingDashboard = complageingDashboard;
	}

	public String getAllAgeingYearlyTotal() {
	return allAgeingYearlyTotal;
	}

	public void setAllAgeingYearlyTotal(String allAgeingYearlyTotal) {
	this.allAgeingYearlyTotal = allAgeingYearlyTotal;
	}

	public String getAllAgeingMonthlyTotal() {
	return allAgeingMonthlyTotal;
	}

	public void setAllAgeingMonthlyTotal(String allAgeingMonthlyTotal) {
	this.allAgeingMonthlyTotal = allAgeingMonthlyTotal;
	}

	public String getAllAgeingWeeklyTotal() {
	return allAgeingWeeklyTotal;
	}

	public void setAllAgeingWeeklyTotal(String allAgeingWeeklyTotal) {
	this.allAgeingWeeklyTotal = allAgeingWeeklyTotal;
	}

	public String getOrgName() {
	return orgName;
	}

	public void setOrgName(String orgName) {
	this.orgName = orgName;
	}

	public String getAddress() {
	return address;
	}

	public void setAddress(String address) {
	this.address = address;
	}

	public String getCity() {
	return city;
	}

	public void setCity(String city) {
	this.city = city;
	}

	public String getPincode() {
	return pincode;
	}

	public void setPincode(String pincode) {
	this.pincode = pincode;
	}

	public String getDeptName() {
	return deptName;
	}

	public void setDeptName(String deptName) {
	this.deptName = deptName;
	}

	public String getTaskName() {
	return taskName;
	}

	public void setTaskName(String taskName) {
	this.taskName = taskName;
	}

	public String getDueDate() {
	return dueDate;
	}

	public void setDueDate(String dueDate) {
	this.dueDate = dueDate;
	}

	public String getDueTime() {
	return dueTime;
	}

	public void setDueTime(String dueTime) {
	this.dueTime = dueTime;
	}

	public String getReminderFor() {
	return reminderFor;
	}

	public void setReminderFor(String reminderFor) {
	this.reminderFor = reminderFor;
	}
	
	public String getTaskType() {
	return taskType;
	}

	public void setTaskType(String taskType) {
	this.taskType = taskType;
	}

	public String getActionBy() {
	return actionBy;
	}

	public void setActionBy(String actionBy) {
	this.actionBy = actionBy;
	}

	public String getActionTakenDate() {
	return actionTakenDate;
	}

	public void setActionTakenDate(String actionTakenDate) {
	this.actionTakenDate = actionTakenDate;
	}

	public String getTableName() {
	return tableName;
	}

	public void setTableName(String tableName) {
	this.tableName = tableName;
	}

	public String getExcelName() {
	return excelName;
	}

	public void setExcelName(String excelName) {
	this.excelName = excelName;
	}

	public String getSelectedId() {
	return selectedId;
	}

	public void setSelectedId(String selectedId) {
	this.selectedId = selectedId;
	}
	
	public Map<String, String> getColumnMap() {
	return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
	this.columnMap = columnMap;
	}

	public String[] getColumnNames() {
	return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
	this.columnNames = columnNames;
	}

	public String getTableAlis() {
	return tableAlis;
	}

	public void setTableAlis(String tableAlis) {
	this.tableAlis = tableAlis;
	}

	public String getExcelFileName() {
	return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
	this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream() {
	return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream) {
	this.excelStream = excelStream;
	}

	public InputStream getInputStream() {
	return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
	}

	public String getContentType() {
	return contentType;
	}

	public void setContentType(String contentType) {
	this.contentType = contentType;
	}

	public Map<String, String> getTotalComplStatusMap() {
	return totalComplStatusMap;
	}

	public void setTotalComplStatusMap(Map<String, String> totalComplStatusMap) {
	this.totalComplStatusMap = totalComplStatusMap;
	}

	public List<ComplianceDashboardBean> getUserWiseUpcomeingComplList() {
	return userWiseUpcomeingComplList;
	}

	public void setUserWiseUpcomeingComplList(
	List<ComplianceDashboardBean> userWiseUpcomeingComplList) {
	this.userWiseUpcomeingComplList = userWiseUpcomeingComplList;
	}

	public Map<String, String> getPreviousMonthUserCompDetail() {
	return previousMonthUserCompDetail;
	}

	public void setPreviousMonthUserCompDetail(
	Map<String, String> previousMonthUserCompDetail) {
	this.previousMonthUserCompDetail = previousMonthUserCompDetail;
	}

	public String getOneMonthBeforeDate() {
	return oneMonthBeforeDate;
	}

	public void setOneMonthBeforeDate(String oneMonthBeforeDate) {
	this.oneMonthBeforeDate = oneMonthBeforeDate;
	}

	public boolean isReportPrint() {
	return reportPrint;
	}

	public void setReportPrint(boolean reportPrint) {
	this.reportPrint = reportPrint;
	}

	public Map<String, String> getMgmtCompDetail() {
	return mgmtCompDetail;
	}

	public void setMgmtCompDetail(Map<String, String> mgmtCompDetail) {
	this.mgmtCompDetail = mgmtCompDetail;
	}

	public Map<String, String> getAgeingCompDetail() {
	return ageingCompDetail;
	}

	public void setAgeingCompDetail(Map<String, String> ageingCompDetail) {
	this.ageingCompDetail = ageingCompDetail;
	}

	public String getTotalPending() {
	return totalPending;
	}

	public void setTotalPending(String totalPending) {
	this.totalPending = totalPending;
	}

	public String getTotalMissed() {
	return totalMissed;
	}

	public void setTotalMissed(String totalMissed) {
	this.totalMissed = totalMissed;
	}

	public String getTotalDone() {
	return totalDone;
	}

	public void setTotalDone(String totalDone) {
	this.totalDone = totalDone;
	}

	public ComplianceDashboardBean getTotalComplDashboard() {
	return totalComplDashboard;
	}

	public void setTotalComplDashboard(ComplianceDashboardBean totalComplDashboard) {
	this.totalComplDashboard = totalComplDashboard;
	}

	public ComplianceDashboardBean getPreviousMonthComplDashboard() {
	return previousMonthComplDashboard;
	}

	public void setPreviousMonthComplDashboard(
	ComplianceDashboardBean previousMonthComplDashboard) {
	this.previousMonthComplDashboard = previousMonthComplDashboard;
	}

	public String getLastMonthDoneTotal() {
	return lastMonthDoneTotal;
	}

	public void setLastMonthDoneTotal(String lastMonthDoneTotal) {
	this.lastMonthDoneTotal = lastMonthDoneTotal;
	}

	public String getLastMonthPendingTotal() {
	return lastMonthPendingTotal;
	}

	public void setLastMonthPendingTotal(String lastMonthPendingTotal) {
	this.lastMonthPendingTotal = lastMonthPendingTotal;
	}

	public String getEsc1Total() {
	return esc1Total;
	}

	public void setEsc1Total(String esc1Total) {
	this.esc1Total = esc1Total;
	}

	public String getEsc2Total() {
	return esc2Total;
	}

	public void setEsc2Total(String esc2Total) {
	this.esc2Total = esc2Total;
	}

	public String getEsc3Total() {
	return esc3Total;
	}

	public void setEsc3Total(String esc3Total) {
	this.esc3Total = esc3Total;
	}

	public String getEsc4Total() {
	return esc4Total;
	}

	public void setEsc4Total(String esc4Total) {
	this.esc4Total = esc4Total;
	}

	public String getEsc5Total() {
	return esc5Total;
	}

	public void setEsc5Total(String esc5Total) {
	this.esc5Total = esc5Total;
	}

	public Map<String, String> getEscLevelCompDetail() {
	return escLevelCompDetail;
	}

	public void setEscLevelCompDetail(Map<String, String> escLevelCompDetail) {
	this.escLevelCompDetail = escLevelCompDetail;
	}

	public ComplianceDashboardBean getEscLevelCompDetailObj() {
	return escLevelCompDetailObj;
	}

	public void setEscLevelCompDetailObj(
	ComplianceDashboardBean escLevelCompDetailObj) {
	this.escLevelCompDetailObj = escLevelCompDetailObj;
	}
	public String getMaximizeDivBlock() {
	return maximizeDivBlock;
	}
	public void setMaximizeDivBlock(String maximizeDivBlock) {
	this.maximizeDivBlock = maximizeDivBlock;
	}

	public String getChartWidth() {
	return chartWidth;
	}

	public void setChartWidth(String chartWidth) {
	this.chartWidth = chartWidth;
	}

	public String getChartHeight() {
	return chartHeight;
	}

	public void setChartHeight(String chartHeight) {
	this.chartHeight = chartHeight;
	}

	public boolean isLegendShow() {
	return legendShow;
	}

	public void setLegendShow(boolean legendShow) {
	this.legendShow = legendShow;
	}

	public String getTotalSnooze() {
	return totalSnooze;
	}

	public void setTotalSnooze(String totalSnooze) {
	this.totalSnooze = totalSnooze;
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
	public String getDivName() {
	return divName;
	}
	public void setDivName(String divName) {
	this.divName = divName;
	}

	public ComplianceDashboardBean getTotalDueTodayDashboard() {
	return totalDueTodayDashboard;
	}
	public void setTotalDueTodayDashboard(
	ComplianceDashboardBean totalDueTodayDashboard) {
	this.totalDueTodayDashboard = totalDueTodayDashboard;
	}

	public String getAllCompId() {
	return allCompId;
	}

	public void setAllCompId(String allCompId) {
	this.allCompId = allCompId;
	}

	public String getCompId() {
	return compId;
	}

	public void setCompId(String compId) {
	this.compId = compId;
	}

	public String getAllAgeingCompId() {
	return allAgeingCompId;
	}

	public void setAllAgeingCompId(String allAgeingCompId) {
	this.allAgeingCompId = allAgeingCompId;
	}

	public String getAllCompId1st() {
	return allCompId1st;
	}

	public void setAllCompId1st(String allCompId1st) {
	this.allCompId1st = allCompId1st;
	}

	public String getAllCompId4th() {
	return allCompId4th;
	}

	public void setAllCompId4th(String allCompId4th) {
	this.allCompId4th = allCompId4th;
	}

	public String getTotalSelfDue() {
	return totalSelfDue;
	}

	public void setTotalSelfDue(String totalSelfDue) {
	this.totalSelfDue = totalSelfDue;
	}

	public String getTotalSelfRem1() {
	return totalSelfRem1;
	}

	public void setTotalSelfRem1(String totalSelfRem1) {
	this.totalSelfRem1 = totalSelfRem1;
	}

	public String getTotalSelfRem2() {
	return totalSelfRem2;
	}

	public void setTotalSelfRem2(String totalSelfRem2) {
	this.totalSelfRem2 = totalSelfRem2;
	}

	public String getTotalSelfRem3() {
	return totalSelfRem3;
	}

	public void setTotalSelfRem3(String totalSelfRem3) {
	this.totalSelfRem3 = totalSelfRem3;
	}

	public String getTotalTeamDue() {
	return totalTeamDue;
	}

	public void setTotalTeamDue(String totalTeamDue) {
	this.totalTeamDue = totalTeamDue;
	}

	public String getTotalTeamRem1() {
	return totalTeamRem1;
	}

	public void setTotalTeamRem1(String totalTeamRem1) {
	this.totalTeamRem1 = totalTeamRem1;
	}

	public String getTotalTeamRem2() {
	return totalTeamRem2;
	}

	public void setTotalTeamRem2(String totalTeamRem2) {
	this.totalTeamRem2 = totalTeamRem2;
	}

	public String getTotalTeamRem3() {
	return totalTeamRem3;
	}

	public void setTotalTeamRem3(String totalTeamRem3) {
	this.totalTeamRem3 = totalTeamRem3;
	}

	public String getAllselfDueCompliance() {
	return allselfDueCompliance;
	}

	public void setAllselfDueCompliance(String allselfDueCompliance) {
	this.allselfDueCompliance = allselfDueCompliance;
	}

	public String getAllselfRem1Compliance() {
	return allselfRem1Compliance;
	}

	public void setAllselfRem1Compliance(String allselfRem1Compliance) {
	this.allselfRem1Compliance = allselfRem1Compliance;
	}

	public String getAllselfRem2Compliance() {
	return allselfRem2Compliance;
	}

	public void setAllselfRem2Compliance(String allselfRem2Compliance) {
	this.allselfRem2Compliance = allselfRem2Compliance;
	}

	public String getAllselfRem3Compliance() {
	return allselfRem3Compliance;
	}

	public void setAllselfRem3Compliance(String allselfRem3Compliance) {
	this.allselfRem3Compliance = allselfRem3Compliance;
	}

	public String getAllteamDueCompliance() {
	return allteamDueCompliance;
	}

	public void setAllteamDueCompliance(String allteamDueCompliance) {
	this.allteamDueCompliance = allteamDueCompliance;
	}

	public String getAllteamRem1Compliance() {
	return allteamRem1Compliance;
	}

	public void setAllteamRem1Compliance(String allteamRem1Compliance) {
	this.allteamRem1Compliance = allteamRem1Compliance;
	}

	public String getAllteamRem2Compliance() {
	return allteamRem2Compliance;
	}

	public void setAllteamRem2Compliance(String allteamRem2Compliance) {
	this.allteamRem2Compliance = allteamRem2Compliance;
	}

	public String getAllteamRem3Compliance() {
	return allteamRem3Compliance;
	}

	public void setAllteamRem3Compliance(String allteamRem3Compliance) {
	this.allteamRem3Compliance = allteamRem3Compliance;
	}

	public String getGraphId() {
	return graphId;
	}

	public void setGraphId(String graphId) {
	this.graphId = graphId;
	}

	public Map<String, String> getTodayCompDetail() {
	return todayCompDetail;
	}

	public void setTodayCompDetail(Map<String, String> todayCompDetail) {
	this.todayCompDetail = todayCompDetail;
	}

	public Map<Integer, String> getDeptList()
	{
	return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
	this.deptList = deptList;
	}

	public JSONArray getJsonArr()
	{
	return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
	this.jsonArr = jsonArr;
	}

	public String getEmployee()
	{
	return employee;
	}

	public void setEmployee(String employee)
	{
	this.employee = employee;
	}

	public String getActionName()
	{
	return actionName;
	}

	public void setActionName(String actionName)
	{
	this.actionName = actionName;
	}

	public Map<String, String> getDeptNameList()
	{
	return deptNameList;
	}

	public void setDeptNameList(Map<String, String> deptNameList)
	{
	this.deptNameList = deptNameList;
	}

	public Map<Integer,String> getEmpList()
	{
	return empList;
	}

	public void setEmpList(Map<Integer,String> empList)
	{
	this.empList = empList;
	}

	public JSONArray getJsonArray()
	{
	return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
	this.jsonArray = jsonArray;
	}

	public String getMaxDivId()
	{
	return maxDivId;
	}

	public void setMaxDivId(String maxDivId)
	{
	this.maxDivId = maxDivId;
	}

	public String getOrgImgPath() {
	return orgImgPath;
	}

	public void setOrgImgPath(String orgImgPath) {
	this.orgImgPath = orgImgPath;
	}
}