package com.Over2Cloud.ctrl.helpdesk.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
@SuppressWarnings("serial")
public class ManagementDash extends GridPropertyBean  {

	private String ticket_id;
	private String status_for;
	
	
    private String dataFlag="";
    private String feedStatus;
	private String dashFor="";
	private String level="";
	private String d_subdept_id="";
	
	private String dashboard;
	private String feedFor;
	private String fromDate="";
	private String toDate="";
	
	private String l_pending="0";
	private String l_snooze="0";
	private String l_hp="0";
	private String l_ignore="0";
	private String l_resolved="0";
	
	private String r_pending="0";
	private String r_snooze="0";
	private String r_hp="0";
	private String r_ignore="0";
	private String r_resolved="0";
	
	
	private String flag;
	private String feedFor1;
	private String feedFor2;
	private String deptHierarchy;
	private String caption,data4;
	private String headerValue;
	
	Map<String, Integer> graphCatgMap = null;
	Map<String, Integer> graphLevelMap = null;
	List<FeedbackPojo> ticketsList=null;
	List<FeedbackPojo> mgmtTicketsList=null;
	List<DashboardPojo> levelTicketsList=null;
	List<FeedbackPojo> empCountList=null;
	List<FeedbackPojo> catgCountList=null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private String dept="",dept_id="";
	String empName="";
	DashboardPojo dashObj=null;
	DashboardPojo leveldashObj=null;
	DashboardPojo deptdashObj=null;
	private List<DashboardPojo> deptdashList=null;
	private List<DashboardPojo> subdeptdashList=null;
	List<DashboardPojo> dept_subdeptcounterList  = null;
	List<FeedbackPojo> feedbackList = null;
	FeedbackPojo FP = null;
	private String loginType;
	private boolean hodFlag;
	private boolean mgmtFlag;
	private boolean normalFlag;
	private String maximizeDivBlock;
	private Map<Integer, Integer> doubleMap;
	
	private String dataCheck="",dydept;
	private Map<Integer, String> serviceDeptList;
	private String filterFlag=null;
	private String filterDeptId=null;

	@SuppressWarnings("rawtypes")
	public void generalMethod()
	{
	try
	{
	List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
	if (empData!=null && empData.size()>0) {
	for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	empName=object[0].toString();
	dept_id=object[3].toString();
	dept=object[4].toString();
	loginType=object[7].toString();
	
	if(loginType.equalsIgnoreCase("H"))
	{
	hodFlag=true;
	headerValue=dept;
	}
	else if (loginType.equalsIgnoreCase("M")) {
	mgmtFlag=true;
	headerValue="All Department";
	}
	else if (loginType.equalsIgnoreCase("N")) {
	headerValue=DateUtil.makeTitle(empName);
	normalFlag=true;
	}
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	}
	
	// First method for Helpdesk Dashboard
	public String beforeDashboardAction() {
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	try {
	this.generalMethod();
	if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
	fromDate=DateUtil.getCurrentDateIndianFormat();
	toDate=DateUtil.getCurrentDateIndianFormat();
	}
	//********************************************//
	//Scrolling Data(First Block)
	//getTicketDetail(loginType,dept_id,empName, connectionSpace);
	//********************************************//
	
	//********************************************//
	//Ticket Counters on the basis of status(Second Block)
	 
	if(dashFor!=null && dashFor.equalsIgnoreCase("Status"))
	{
	  getCounterData(fromDate,toDate);	
	}	
	if(dashFor!=null && dashFor.equalsIgnoreCase("Reopen"))
	{
	  getReopenCounterData(fromDate,toDate);	
	}
	//********************************************//
	
	//********************************************//	
	//Level wise Ticket Detail in Table (Fifth Block)
	if(dashFor!=null && dashFor.equalsIgnoreCase("level"))
	{
	getTicketDetailByLevel("T",fromDate,toDate);
	}
	//Level wise Ticket Detail in Graph (Fifth Block)
	 // getTicketDetailByLevel("G",fromDate,toDate);
	//********************************************//
	
	//********************************************//
	//Analytics for category in table (Third Block)
	if(dashFor!=null && dashFor.equalsIgnoreCase("categ"))
	{
	getAnalytics4SubCategory("T");
	}
	//Analytics for category in graph (Third Block)
	//  getAnalytics4SubCategory("G");
	//********************************************//
	
	//getDataInBars(connectionSpace);
	  getServiceDept();
	returnResult=SUCCESS;
	  } catch (Exception e) {
	addActionError("Ooops There Is Some Problem In beforeDashboardAction in DashboardAction !!!");
	e.printStackTrace();
	returnResult= ERROR;
	}
	} else {
	returnResult = LOGIN;
	}
	return returnResult;
	
	}
	
	
	
	@SuppressWarnings("rawtypes")
	void getServiceDept(){
	serviceDeptList = new LinkedHashMap<Integer, String>();
	
	List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2",orgLevel, orgId,"HDM", connectionSpace);
	if (departmentlist != null && departmentlist.size() > 0)
	{
	for (Iterator iterator = departmentlist.iterator(); iterator
	.hasNext();) 
	{
	Object[] object1 = (Object[]) iterator.next();
	serviceDeptList.put((Integer) object1[0], object1[1]
	.toString());
	}
	
	}
	}
	
	// For Bar Graph
	public String getBarChart4DeptCounters()
	{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
	fromDate=DateUtil.getCurrentDateIndianFormat();
	toDate=DateUtil.getCurrentDateIndianFormat();
	}
	getCounterData(fromDate,toDate);
	if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
	getServiceDept();
	}else{
	
	}
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : dept_subdeptcounterList)
	 {
	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("deptId",pojo.getId());
	jsonObject.put("Pending",Integer.parseInt(pojo.getPc()));
	jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
	jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
	jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
	jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
	jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
	jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));
	
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
	
	
	
	
	
	// For Bar Graph
	public String getBarChart4LevelCounters()
	{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	
	
	if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
	fromDate=DateUtil.getCurrentDateIndianFormat();
	toDate=DateUtil.getCurrentDateIndianFormat();
	}
	getTicketDetailByLevel("T",fromDate,toDate);
	/*if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
	getServiceDept();
	}else{
	
	}*/
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : levelTicketsList)
	 {
	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("id",pojo.getId());
	jsonObject.put("Level3",Integer.parseInt(pojo.getPc()));
	jsonObject.put("Level1", Integer.parseInt(pojo.getHpc()));
	jsonObject.put("Level4", Integer.parseInt(pojo.getSc()));
	jsonObject.put("Level2", Integer.parseInt(pojo.getIgc()));
	jsonObject.put("Level5", Integer.parseInt(pojo.getRc()));
	jsonObject.put("Level6", Integer.parseInt(pojo.getRac()));
	
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
	//for pie chart
	// for pie chart
	public String getBarChart4CategCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{
	if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
	{
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	// System.out.println(dept_id+"dept ID"+filterDeptId);
	getAnalytics4SubCategory("T");

	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	for (FeedbackPojo pojo : catgCountList)
	{
	jsonObject.put("Category", pojo.getFeedback_catg());
	jsonObject.put("Counter", pojo.getCounter());
	jsonObject.put("Id", pojo.getId());
	jsonArray.add(jsonObject);
	}
	return SUCCESS;
	} catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	} else
	{
	return LOGIN;
	}
	}
	
	//Reopen Guage
	
	public String jsonChartData4ReopenCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{
	if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
	{
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	getReopenCounterData(fromDate,toDate);
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : dept_subdeptcounterList)
	 {
	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("deptId",pojo.getId());
	 	jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));
	 	jsonArray.add(jsonObject);
	 }
	
	return SUCCESS;
	} catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	} else
	{
	return LOGIN;
	}
	}
	
	@SuppressWarnings("rawtypes")
	public void getReopenCounterData(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	if(filterFlag==null || filterFlag.equalsIgnoreCase(""))
	{
	this.generalMethod();
	}
	else
	{
	loginType=filterFlag;
	if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
	hodFlag=true;
	}
	}
	
	flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	if(filterDeptId==null || filterDeptId.equalsIgnoreCase("-1"))
	{
	dept_subdeptNameList= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,dept_id,empName,connectionSpace);
	 	}
	else
	{
	dept_subdeptNameList= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,filterDeptId,empName,connectionSpace);
	  	}
	 
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int reoptot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	int total=0;
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 
	if (loginType.equalsIgnoreCase("M")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounterForReopen("dept","All",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("H")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounterForReopen("subdept","All",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("N")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounterForReopen("subdept","Normal",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	
	
	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	 
	if (object2[0].toString().equalsIgnoreCase("Re-Opened")) {
	total=total+Integer.parseInt(object2[1].toString());
	reoptot=reoptot+Integer.parseInt(object2[1].toString());
	DP.setReopc(object2[1].toString());
	    }
	 
	     }
	     }
	 if(total>0)
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setReopct(Integer.toString(reoptot));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	  }}} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	/*public String jsonChartData4ReopenCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{
	if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
	{
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	StringBuilder qry = new StringBuilder();
	if (filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1"))
	{
	qry.append("select count(*) from feedback_status_new where to_dept_subdept='"+filterDeptId+"' and open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and  '"+DateUtil.convertDateToUSFormat(toDate)+"' and status='Re-Opened' ");
	}
	else
	{
	qry.append("select count(*) from feedback_status_new where  open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and  '"+DateUtil.convertDateToUSFormat(toDate)+"' and status='Re-Opened' ");
	}
	 List temp = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
	 int count=0;
	 if(temp!=null && temp.size()>0)
	 {
	 count=Integer.parseInt(temp.get(0).toString());
	 }

	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	
	jsonObject.put("Counter", count);
	jsonArray.add(jsonObject);
	
	return SUCCESS;
	} catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	} else
	{
	return LOGIN;
	}
	}*/
	//Ticket Counters on the basis of status At HOD Level
	
	@SuppressWarnings("rawtypes")
	public void getCounterData(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	//System.out.println("filterFlag :::::::::::::::::"+filterFlag);
	if(filterFlag==null || filterFlag.equalsIgnoreCase(""))
	{
	this.generalMethod();
	}
	else
	{
	loginType=filterFlag;
	if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
	hodFlag=true;
	}
	}
	
	flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	if(filterDeptId==null || filterDeptId.equalsIgnoreCase(""))
	{
	dept_subdeptNameList= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,dept_id,empName,connectionSpace);
	}
	else
	{
	dept_subdeptNameList= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,filterDeptId,empName,connectionSpace);
	}
	
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int hptot=0,igtot=0,pentot=0,snootot=0,reotot=0,reatot=0,restot=0,lasttot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	int total=0;
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
	List dept_subdeptCounterList = new ArrayList();
	 
	if (loginType.equalsIgnoreCase("M")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("dept","All",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("H")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("subdept","All",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("N")) {
	dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("subdept","Normal",empName,object1[0].toString(),connectionSpace,fromDate,toDate);
	}
	
	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("High Priority")) {
	total=total+Integer.parseInt(object2[1].toString());
	hptot=hptot+Integer.parseInt(object2[1].toString());
	DP.setHpc(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Ignore")) {
	total=total+Integer.parseInt(object2[1].toString());
	igtot=igtot+Integer.parseInt(object2[1].toString());
	DP.setIgc(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Pending")) {
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	DP.setPc(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	DP.setSc(object2[1].toString());
	    }
	else if (object2[0].toString().equalsIgnoreCase("Re-Opened")) {
	total=total+Integer.parseInt(object2[1].toString());
	reotot=reotot+Integer.parseInt(object2[1].toString());
	DP.setReopc(object2[1].toString());
	    }
	else if (object2[0].toString().equalsIgnoreCase("Re-Assign")) {
	total=total+Integer.parseInt(object2[1].toString());
	reatot=reatot+Integer.parseInt(object2[1].toString());
	DP.setRac(object2[1].toString());
	    }
	else  if (object2[0].toString().equalsIgnoreCase("Resolved")) {
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	DP.setRc(object2[1].toString());
	}
	DP.setHt(Integer.toString(Integer.parseInt(DP.getHpc())+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc())));
//	lasttot=lasttot+Integer.parseInt(DP.getHpc())+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc());  
	}
	     }
	 if(total>0)
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setHpct(Integer.toString(hptot));
	DP.setIgnt(Integer.toString(igtot));
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setReopct(Integer.toString(reotot));
	DP.setRact(Integer.toString(reatot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(hptot+igtot+pentot+snootot+reotot+reatot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}

	
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	public void getTicketDetail(String status_for,String deptid,String empName, SessionFactory connectionSpace)
	 {
	try {
	List ticketData= new HelpdeskUniversalAction().getLodgedTickets(deptid, status_for, empName, connectionSpace);
	ticketsList = new ArrayList<FeedbackPojo>();
	if (ticketData!=null && ticketData.size()>0) {
	for (Iterator iterator = ticketData.iterator(); iterator
	.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	FeedbackPojo fp=new FeedbackPojo();
	fp.setId(Integer.parseInt( object[0].toString()));
	fp.setTicket_no(object[1].toString());
	fp.setFeed_by(DateUtil.makeTitle(object[2].toString())+", "+object[7].toString());
	fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString())+", "+object[4].toString().substring(0, 5));
	fp.setFeedback_subcatg(object[10].toString());
	fp.setFeedback_catg(object[11].toString());
	fp.setAllot_to_mobno(object[8].toString()+", "+object[9].toString());
	//fp.setOpen_time(object[4].toString().substring(0, 5));
	if(object[5].toString().equalsIgnoreCase("Hold"))
	fp.setStatus("Seek Approval");
	else 
	fp.setStatus(object[5].toString());
	
	fp.setLocation(object[6].toString());
	ticketsList.add(fp);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	 }
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getTicketDetailByLevel(String status_for, String fromDate, String toDate)
	 {
	try {
	if(filterFlag==null || filterFlag.equalsIgnoreCase(""))
	{
	this.generalMethod();
	}else
	{
	loginType=filterFlag;
	if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
	hodFlag=true;
	}
	}
	if (status_for!=null && !status_for.equals("") && status_for.equals("T"))
	 {
	 List departmentlist=null;
	 if(filterDeptId==null || filterDeptId.equalsIgnoreCase(""))
	{
	departmentlist= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,dept_id,empName,connectionSpace);
	}
	else
	{
	departmentlist= new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,filterDeptId,empName,connectionSpace);
	}
	
	levelTicketsList = new ArrayList<DashboardPojo>();
	DashboardPojo DP=null;
	leveldashObj= new DashboardPojo();
	int hptot=0,igtot=0,pentot=0,snootot=0,reotot=0,reatot=0;
	for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	int total=0;
	DP=new DashboardPojo();
	DP.setDeptName(object[1].toString());
	DP.setId(object[0].toString());
	List ticketData=null;
	
	if (loginType.equalsIgnoreCase("M")) {
	ticketData = new DashboardHelper().getDashboardLevelCounter("dept","All",empName,object[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("H")) {
	ticketData = new DashboardHelper().getDashboardLevelCounter("subdept","Pending",empName,object[0].toString(),connectionSpace,fromDate,toDate);
	}
	else if (loginType.equalsIgnoreCase("N")) {
	ticketData = new DashboardHelper().getDashboardLevelCounter("subdept","Normal",empName,object[0].toString(),connectionSpace,fromDate,toDate);
	}
	
	//ticketData= getLevelTickets(object[0].toString(), connectionSpace,fromDate,toDate);
	if (ticketData!=null && ticketData.size()>0) {
	for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
	Object[] object2 = (Object[]) iterator1.next();
	
	if (object2[0].toString().equalsIgnoreCase("Level1")) {
	total=total+Integer.parseInt(object2[1].toString());
	DP.setHpc(object2[1].toString());
	hptot=hptot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Level2")) {
	total=total+Integer.parseInt(object2[1].toString());
	igtot=igtot+Integer.parseInt(object2[1].toString());
	DP.setIgc(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Level3")) {
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	DP.setPc(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Level4")) {
	total=total+Integer.parseInt(object2[1].toString());
	DP.setSc(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	else if (object2[0].toString().equalsIgnoreCase("Level5")) {
	total=total+Integer.parseInt(object2[1].toString());
	reotot=reotot+Integer.parseInt(object2[1].toString());
	DP.setReopc(object2[1].toString());
	    }
	else if (object2[0].toString().equalsIgnoreCase("Level6")) {
	total=total+Integer.parseInt(object2[1].toString());
	reatot=reatot+Integer.parseInt(object2[1].toString());
	DP.setRac(object2[1].toString());
	    }
	DP.setHt(Integer.toString(Integer.parseInt(DP.getHpc())+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc())));
	    }
	ticketData.clear();
	}
	/*ticketData= new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType,object.toString(),status_for,empName,"Resolved", connectionSpace,fromDate,toDate);
	if (ticketData!=null && ticketData.size()>0) {
	for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
	Object[] object1 = (Object[]) iterator1.next();
	
	 if (object1[0].toString().equals("Resolved")) {
	dp.setRc(object1[1].toString());
	    }
	    }
	ticketData.clear();
	}*/
	if(total>0)
	{
	dydept=dydept+",'"+DP.getId()+"'";	
	levelTicketsList.add(DP);
	leveldashObj.setDashList(levelTicketsList);
	}
	}
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setHpct(Integer.toString(hptot));
	DP.setIgnt(Integer.toString(igtot));
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setReopct(Integer.toString(reotot));
	DP.setRact(Integer.toString(reatot));
	String str=Integer.toString(hptot+igtot+pentot+snootot+reotot+reatot);
	DP.setGrand(str);
	levelTicketsList.add(DP);
	leveldashObj.setDashList(levelTicketsList);
	}
	
	}
	else if (status_for!=null && !status_for.equals("") && status_for.equals("G")) 
	 {/*
	List ticketData= new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType,"",status_for,empName, connectionSpace);
	if (ticketData!=null && ticketData.size()>0)
	 {
	for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
	Object[] object1 = (Object[]) iterator1.next();
	graphLevelMap = new LinkedHashMap<String, Integer>();
	graphLevelMap.put(object1[0].toString(), Integer.parseInt(object1[1].toString()));
	    }
	    }
	     */}	
	 } catch (Exception e) {
	e.printStackTrace();
	}
	 }
	
	
	@SuppressWarnings("rawtypes")
	public void getAnalytics4SubCategory(String status_for)
	 {
	try {
	 this.generalMethod();
	 List catgData = new HelpdeskUniversalAction().getAnalyticalData("C", fromDate, toDate, dept_id, filterDeptId, filterFlag, empName, searchField, searchString, searchOper, connectionSpace);
	 catgCountList = new ArrayList<FeedbackPojo>();
	 if (catgData!=null && catgData.size()>0) {
	    	 if (status_for.equalsIgnoreCase("T")) {
	    	 int total=0;    	
	    	 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	if (status_for.equalsIgnoreCase("T")) {
	FeedbackPojo fp=new FeedbackPojo();
	fp.setId(Integer.parseInt(object[0].toString()));
	fp.setFeedback_catg(object[1].toString());
	fp.setCounter(object[2].toString());
	total=total+Integer.parseInt(object[2].toString());
	catgCountList.add(fp);
	}
	FeedbackPojo fp=new FeedbackPojo();
	
	    	 }
	    	 if(data4!=null && data4.equalsIgnoreCase("table"))
	    	{
	    	FeedbackPojo fp=new FeedbackPojo();
	    	fp.setId(Integer.parseInt("-1"));
	    	fp.setFeedback_catg("Total");
	    	fp.setGrand(Integer.toString(total));
	    	catgCountList.add(fp);
	    	}
	    	 }
	    	 else if (status_for.equalsIgnoreCase("G")) {
	    	 graphCatgMap=new LinkedHashMap<String, Integer>();
	    	 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	graphCatgMap.put(object[1].toString(),Integer.parseInt(object[2].toString()));
	    	 }
	}
	    	 }} catch (Exception e) {
	e.printStackTrace();
	}
	 }
	
	
	@SuppressWarnings("rawtypes")
	public String geTicketDetails()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	
	try {
	List dataList = new HelpdeskUniversalHelper().getTicketData(ticket_id, connectionSpace);
	if (dataList!=null && dataList.size()>0) {
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	FP = new FeedbackPojo();
	if (object[0]!=null && !object[0].toString().equals("")) {
	FP.setTicket_no(object[0].toString());
	}
	else {
	FP.setTicket_no("NA");
	}
	if (object[1]!=null && !object[1].toString().equals("")) {
	FP.setFeedback_by_dept(DateUtil.makeTitle(object[1].toString()));	
	}
	else {
	FP.setFeedback_by_dept("NA");
	}
	if (object[2]!=null && !object[2].toString().equals("")) {
	FP.setFeed_by(DateUtil.makeTitle(object[2].toString()));	
	}
	else {
	FP.setFeed_by("NA");
	}
	if (object[3]!=null && !object[3].toString().equals("")) {
	FP.setFeedback_by_mobno(object[3].toString());	
	}
	else {
	FP.setFeedback_by_mobno("NA");
	}
	if (object[4]!=null && !object[4].toString().equals("")) {
	FP.setFeedback_by_emailid(object[4].toString());	
	}
	else {
	FP.setFeedback_by_emailid("NA");
	}
	if (object[5]!=null && !object[5].toString().equals("")) {
	FP.setFeedback_to_dept(DateUtil.makeTitle(object[5].toString()));	
	}
	else {
	FP.setFeedback_to_dept("NA");
	}
	if (object[6]!=null && !object[6].toString().equals("")) {
	FP.setFeedback_allot_to(DateUtil.makeTitle(object[6].toString()));	
	}
	else {
	FP.setFeedback_allot_to("NA");
	}
	if (object[7]!=null && !object[7].toString().equals("")) {
	FP.setFeedtype(DateUtil.makeTitle(object[7].toString()));	
	}
	else {
	FP.setFeedtype("NA");
	}
	if (object[8]!=null && !object[8].toString().equals("")) {
	FP.setFeedback_catg(DateUtil.makeTitle(object[8].toString()));	
	}
	else {
	FP.setFeedback_catg("NA");
	}
	if (object[9]!=null && !object[9].toString().equals("")) {
	FP.setFeedback_subcatg(DateUtil.makeTitle(object[9].toString()));	
	}
	else {
	FP.setFeedback_subcatg("NA");
	}
	if (object[10]!=null && !object[10].toString().equals("")) {
	FP.setFeed_brief(DateUtil.makeTitle(object[10].toString()));	
	}
	else {
	FP.setFeed_brief("NA");
	}
	if (object[11]!=null && !object[11].toString().equals("")) {
	FP.setLocation(object[11].toString());	
	}
	else {
	FP.setLocation("NA");
	}
	if (object[12]!=null && !object[12].toString().equals("")) {
	FP.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));	
	}
	else {
	FP.setOpen_date("NA");
	}
	if (object[13]!=null && !object[13].toString().equals("")) {
	FP.setOpen_time(object[13].toString().substring(0, 5));	
	}
	else {
	FP.setOpen_time("NA");
	}
	if (object[14]!=null && !object[14].toString().equals("")) {
	FP.setLevel(object[14].toString());	
	}
	else {
	FP.setLevel("NA");
	}
	if (object[15]!=null && !object[15].toString().equals("")) {
	FP.setStatus(object[15].toString());	
	}
	else {
	FP.setStatus("NA");
	}
	if (object[16]!=null && !object[16].toString().equals("")) {
	FP.setVia_from(object[16].toString());	
	}
	else {
	FP.setVia_from("NA");
	}
	if (object[17]!=null && !object[17].toString().equals("")) {
	FP.setFeed_registerby(DateUtil.makeTitle(object[17].toString()));	
	}
	else {
	FP.setFeed_registerby("NA");
	}
	if (object[18]!=null && !object[18].toString().equals("")) {
	FP.setSn_reason(object[18].toString());	
	}
	else {
	FP.setSn_reason("NA");
	}
	if (object[19]!=null && !object[19].toString().equals("")) {
	FP.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));	
	}
	else {
	FP.setSn_on_date("NA");
	}
	if (object[20]!=null && !object[20].toString().equals("")) {
	FP.setSn_on_time(object[20].toString().substring(0, 5));	
	}
	else {
	FP.setSn_on_time("NA");
	}
	if (object[21]!=null && !object[21].toString().equals("")) {
	FP.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));	
	}
	else {
	FP.setSn_date("NA");
	}
	if (object[22]!=null && !object[22].toString().equals("")) {
	FP.setSn_time(object[22].toString().substring(0, 5));	
	}
	else {
	FP.setSn_time("NA");
	}
	if (object[23]!=null && !object[23].toString().equals("")) {
	FP.setSn_duration(object[22].toString());	
	}
	else {
	FP.setSn_duration("NA");
	}
	if (object[24]!=null && !object[24].toString().equals("")) {
	FP.setHp_date(DateUtil.convertDateToIndianFormat(object[24].toString()));	
	}
	else {
	FP.setHp_date("NA");
	}
	if (object[25]!=null && !object[25].toString().equals("")) {
	FP.setHp_time(object[25].toString().substring(0, 5));	
	}
	else {
	FP.setHp_time("NA");
	}
	if (object[26]!=null && !object[26].toString().equals("")) {
	FP.setHp_reason(object[26].toString());	
	}
	else {
	FP.setHp_reason("NA");
	}
	
	if (object[27]!=null && !object[27].toString().equals("")) {
	FP.setFeedback_to_subdept(DateUtil.makeTitle(object[27].toString()));	
	}
	else {
	FP.setFeedback_to_subdept("NA");
	}
	if (object[28]!=null && !object[28].toString().equals("")) {
	FP.setEscalation_date(DateUtil.convertDateToIndianFormat(object[28].toString()));	
	}
	else {
	FP.setEscalation_date("NA");
	}
	if (object[29]!=null && !object[29].toString().equals("")) {
	FP.setEscalation_time(object[29].toString().substring(0, 5));	
	}
	else {
	FP.setEscalation_time("NA");
	}
	if (object[30]!=null && !object[30].toString().equals("")) {
	FP.setFeedaddressing_time(DateUtil.convertDateToIndianFormat(object[30].toString().substring(0, 10))+"  ("+object[30].toString().substring(11, 16)+")");	
	}
	else {
	FP.setFeedaddressing_time("NA");
	}
	
	feedList.add(FP);
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	if (status_for.equalsIgnoreCase("T")) {
	caption = "Ticket Detail";
	returnResult = "ticket_success";
	}
	else if (status_for.equalsIgnoreCase("FB")) {
	caption = "Lodged By Detail";
	returnResult = "feedby_success" ;
	}
	 }
	else
	{
	returnResult = LOGIN;
	}
	return returnResult;
	}
	
	
	public String getDashboard() {
	return dashboard;
	}
	public void setDashboard(String dashboard) {
	this.dashboard = dashboard;
	}

	
	
	@SuppressWarnings("rawtypes")
	public String getFeedbackDetail()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	try
	{
	
	/*String userName=(String)session.get("uName");
	String deptLevel = (String)session.get("userDeptLevel");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
	feedbackList = new ArrayList<FeedbackPojo>();
	
	//	int count = 0 ;
	List data=null;
	
	
	
	
	if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("M")) {
	
	}
	else if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("H")) {
	
	}
            else if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("N")) {
	
	}
	
	String dept_id="",empname="";
	List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
	if (empData!=null && empData.size()>0) {
	for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	empname=object[0].toString();
	dept_id=object[3].toString();
	}
	}
	
	if (dataFlag!=null && !dataFlag.equals("") && dataFlag.equals("L")) {
	d_subdept_id=dept_id;
	}
	data=getDataForDashboard(feedStatus,fromDate,toDate,dashFor, d_subdept_id,dataFlag,"HDM",empname,level,searchField,searchString,searchOper, connectionSpace);
	
	if(data!=null && data.size()>0){ 
	setRecords(data.size());
	int to = (getRows() * getPage());
	@SuppressWarnings("unused")
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	if (deptLevel.equals("2")) {
	feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getFeedStatus());
	dataCheck="Confirm";
	}
	
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	returnResult = SUCCESS;
	}
	catch(Exception e)
	{
	 addActionError("Ooops!!! There is some problem in getting Feedback Data");
	 e.printStackTrace();
	}
	}
	else {
	returnResult=LOGIN;
	}
	return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public List getDataForDashboard(String status, String fromDate, String toDate, String dashFor, String dept_subdeptid, String dataFlag, String module, String empname, String level,
	String searchField, String searchString, String searchOper, SessionFactory connectionSpace)
	{// System.out.println("Emp name  :::::::::::::::::::>>>>>>>>>>>>>>> "+ empname);
	List list = new ArrayList();
	StringBuilder query = new StringBuilder("");
	try
	{
	query.append("select feedback.id,feedback.ticket_no,dept2.deptName as todept,feedtype.fbType as tosubdept,");
	query.append("emp.empName as allot_to,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
	query.append("frd.roomno,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
	query.append("feedback.open_date,feedback.open_time,feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,");
	query.append("feedback.level,feedback.status,feedback.feed_registerby ");
	
	/*if (!status.equalsIgnoreCase("Pending"))
	{
	query.append(",history.action_date,history.action_time,history.action_duration,history.action_remark, emp1.empName as action_by ");
	if (status.equalsIgnoreCase("Snooze"))
	{
	query.append(",history.sn_upto_date,history.sn_upto_time ");
	}
	}*/
	
	query.append(" from feedback_status_new as feedback");
	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
	query.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
	query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
	query.append(" inner join floor_room_detail as frd on frd.id = feedback.location ");
	/*if(!status.equalsIgnoreCase("Pending"))
	{
	query.append(" inner join feedback_status_history as history on feedback.id=history.feedId");	
	query.append(" inner join employee_basic emp1 on history.action_by= emp1.id");
	}*/
	if(status!=null && !status.equalsIgnoreCase(""))
	{
	query.append(" where feedback.status='" + status + "'");
	}
	else
	{	
	query.append(" where feedback.id!=0 ");
	}	
	/*if(status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Snooze") || status.equalsIgnoreCase("Ignore"))
	{
	query.append(" and history.status=feedback.status ");	
	}*/
	/*else if(!status.equalsIgnoreCase("Pending"))
	{
	query.append(" and history.feedId=feedback.id ");
	}*/

	if(dataFlag!=null && dataFlag.equals("T"))
	{
	if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
	{
	query.append(" and dept2.id='" + dept_subdeptid + "'");
	}
	else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
	{
	query.append(" and feedtype.dept_subdept='" + dept_subdeptid + "'");
	}
	else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
	{
	query.append(" and subdept2.id='" + dept_subdeptid + "' and feedback.feed_by='" + empname + "'");
	}
	}
	//For Levels
	else if(dataFlag!=null && dataFlag.equals("L"))
	{
	if (dashFor != null && !dashFor.equals("") && dashFor.equals("M"))
	{
	query.append(" and feedback.level='" + level + "'");
	}
	
	else if (dashFor != null && !dashFor.equals("") && dashFor.equals("H"))
	{
	query.append(" and dept2.id='" + dept_subdeptid + "' and feedback.level='" + level + "'");
	}
	
	else if (dashFor != null && !dashFor.equals("") && dashFor.equals("N"))
	{
	query.append("  and feedback.feed_by='" + empname + "' and feedback.level='" + level + "'");
	}
	}
	//For Category
	else if(dataFlag!=null && dataFlag.equals("C"))
	{
	query.append(" and feedback.subCatg='" + dept_subdeptid + "'");
	}
	
	if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	query.append(" and");

	if (searchOper.equalsIgnoreCase("eq"))
	{
	query.append(" " + searchField + " = '" + searchString + "'");
	}
	else if (searchOper.equalsIgnoreCase("cn"))
	{
	query.append(" " + searchField + " like '%" + searchString + "%'");
	}
	else if (searchOper.equalsIgnoreCase("bw"))
	{
	query.append(" " + searchField + " like '" + searchString + "%'");
	}
	else if (searchOper.equalsIgnoreCase("ne"))
	{
	query.append(" " + searchField + " <> '" + searchString + "'");
	}
	else if (searchOper.equalsIgnoreCase("ew"))
	{
	query.append(" " + searchField + " like '%" + searchString + "'");
	}
	}
	query.append(" AND feedback.open_date BETWEEN '"+DateUtil.convertDateToUSFormat(fromDate)+"' AND '"+DateUtil.convertDateToUSFormat(toDate)+"' ");
	query.append(" GROUP BY feedback.id ORDER BY feedback.id DESC");
	//	System.out.println("QQQQQQ   Padam      ::: "+query.toString());
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return list;
	

}


	@SuppressWarnings({ "rawtypes" })
	public List getLevelTickets(String dept,  SessionFactory connectionSpace,String fromDate, String toDate)
	{
	List list = new ArrayList();
	StringBuilder query = new StringBuilder("");
	try
	{
	if (dept!=null)
	{
	query.append("select level, count(*) from feedback_status_new where to_dept_subdept='"+dept+"' and status='Pending'"
	+ " and open_date  between '"+DateUtil.convertDateToUSFormat(fromDate)+"' "
	+ "and '"+DateUtil.convertDateToUSFormat(toDate)+"' group by level ");
	}
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return list;
	}


	public String getFeedFor() {
	return feedFor;
	}
	public void setFeedFor(String feedFor) {
	this.feedFor = feedFor;
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

	public List<FeedbackPojo> getFeedbackList() {
	return feedbackList;
	}
	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
	this.feedbackList = feedbackList;
	}

	public String getL_pending() {
	return l_pending;
	}
	public void setL_pending(String l_pending) {
	this.l_pending = l_pending;
	}


	public String getL_snooze() {
	return l_snooze;
	}
	public void setL_snooze(String l_snooze) {
	this.l_snooze = l_snooze;
	}


	public String getL_hp() {
	return l_hp;
	}
	public void setL_hp(String l_hp) {
	this.l_hp = l_hp;
	}


	public String getL_ignore() {
	return l_ignore;
	}
	public void setL_ignore(String l_ignore) {
	this.l_ignore = l_ignore;
	}


	public String getL_resolved() {
	return l_resolved;
	}
	public void setL_resolved(String l_resolved) {
	this.l_resolved = l_resolved;
	}


	public String getR_pending() {
	return r_pending;
	}
	public void setR_pending(String r_pending) {
	this.r_pending = r_pending;
	}


	public String getR_snooze() {
	return r_snooze;
	}
	public void setR_snooze(String r_snooze) {
	this.r_snooze = r_snooze;
	}


	public String getR_hp() {
	return r_hp;
	}
	public void setR_hp(String r_hp) {
	this.r_hp = r_hp;
	}

	
	public String getR_ignore() {
	return r_ignore;
	}
	public void setR_ignore(String r_ignore) {
	this.r_ignore = r_ignore;
	}


	public String getR_resolved() {
	return r_resolved;
	}
	public void setR_resolved(String r_resolved) {
	this.r_resolved = r_resolved;
	}

	public String getFlag() {
	return flag;
	}

	public void setFlag(String flag) {
	this.flag = flag;
	}

	public String getFeedFor1() {
	return feedFor1;
	}

	public void setFeedFor1(String feedFor1) {
	this.feedFor1 = feedFor1;
	}

	public String getFeedFor2() {
	return feedFor2;
	}

	public void setFeedFor2(String feedFor2) {
	this.feedFor2 = feedFor2;
	}

	public String getDeptHierarchy() {
	return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy) {
	this.deptHierarchy = deptHierarchy;
	}

	public String getDataFlag() {
	return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
	this.dataFlag = dataFlag;
	}

	public String getDashFor() {
	return dashFor;
	}

	public void setDashFor(String dashFor) {
	this.dashFor = dashFor;
	}

	public String getD_subdept_id() {
	return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id) {
	this.d_subdept_id = d_subdept_id;
	}
	public String getFeedStatus() {
	return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
	this.feedStatus = feedStatus;
	}
	

	public List<DashboardPojo> getDept_subdeptcounterList() {
	return dept_subdeptcounterList;
	}


	public void setDept_subdeptcounterList(
	List<DashboardPojo> dept_subdeptcounterList) {
	this.dept_subdeptcounterList = dept_subdeptcounterList;
	}



	public DashboardPojo getDashObj() {
	return dashObj;
	}


	public void setDashObj(DashboardPojo dashObj) {
	this.dashObj = dashObj;
	}


	public List<DashboardPojo> getSubdeptdashList() {
	return subdeptdashList;
	}



	public void setSubdeptdashList(List<DashboardPojo> subdeptdashList) {
	this.subdeptdashList = subdeptdashList;
	}



	public DashboardPojo getDeptdashObj() {
	return deptdashObj;
	}



	public void setDeptdashObj(DashboardPojo deptdashObj) {
	this.deptdashObj = deptdashObj;
	}



	public List<DashboardPojo> getDeptdashList() {
	return deptdashList;
	}



	public void setDeptdashList(List<DashboardPojo> deptdashList) {
	this.deptdashList = deptdashList;
	}



/*	public Map<String, Integer> getGraphEmpMap() {
	return graphEmpMap;
	}
	public void setGraphEmpMap(Map<String, Integer> graphEmpMap) {
	this.graphEmpMap = graphEmpMap;
	}*/



	public Map<String, Integer> getGraphCatgMap() {
	return graphCatgMap;
	}
	public void setGraphCatgMap(Map<String, Integer> graphCatgMap) {
	this.graphCatgMap = graphCatgMap;
	}

	public Map<Integer, Integer> getDoubleMap() {
	return doubleMap;
	}
	public void setDoubleMap(Map<Integer, Integer> doubleMap) {
	this.doubleMap = doubleMap;
	}

	public List<FeedbackPojo> getTicketsList() {
	return ticketsList;
	}
	public void setTicketsList(List<FeedbackPojo> ticketsList) {
	this.ticketsList = ticketsList;
	}


	public String getTicket_id() {
	return ticket_id;
	}



	public void setTicket_id(String ticket_id) {
	this.ticket_id = ticket_id;
	}



	public String getStatus_for() {
	return status_for;
	}



	public void setStatus_for(String status_for) {
	this.status_for = status_for;
	}



	public FeedbackPojo getFP() {
	return FP;
	}
	public void setFP(FeedbackPojo fp) {
	FP = fp;
	}



	public String getCaption() {
	return caption;
	}



	public void setCaption(String caption) {
	this.caption = caption;
	}



	public List<FeedbackPojo> getMgmtTicketsList() {
	return mgmtTicketsList;
	}



	public void setMgmtTicketsList(List<FeedbackPojo> mgmtTicketsList) {
	this.mgmtTicketsList = mgmtTicketsList;
	}



	public DashboardPojo getLeveldashObj() {
	return leveldashObj;
	}



	public void setLeveldashObj(DashboardPojo leveldashObj) {
	this.leveldashObj = leveldashObj;
	}



	public List<DashboardPojo> getLevelTicketsList() {
	return levelTicketsList;
	}



	public void setLevelTicketsList(List<DashboardPojo> levelTicketsList) {
	this.levelTicketsList = levelTicketsList;
	}



	public List<FeedbackPojo> getEmpCountList() {
	return empCountList;
	}



	public void setEmpCountList(List<FeedbackPojo> empCountList) {
	this.empCountList = empCountList;
	}



	public List<FeedbackPojo> getCatgCountList() {
	return catgCountList;
	}



	public void setCatgCountList(List<FeedbackPojo> catgCountList) {
	this.catgCountList = catgCountList;
	}

	


	public String getHeaderValue() {
	return headerValue;
	}


	public void setHeaderValue(String headerValue) {
	this.headerValue = headerValue;
	}

	public String getMaximizeDivBlock() {
	return maximizeDivBlock;
	}


	public void setMaximizeDivBlock(String maximizeDivBlock) {
	this.maximizeDivBlock = maximizeDivBlock;
	}


	public Map<String, Integer> getGraphLevelMap() {
	return graphLevelMap;
	}


	public void setGraphLevelMap(Map<String, Integer> graphLevelMap) {
	this.graphLevelMap = graphLevelMap;
	}


	


	public String getLoginType() {
	return loginType;
	}


	public void setLoginType(String loginType) {
	this.loginType = loginType;
	}


	public boolean isHodFlag() {
	return hodFlag;
	}


	public void setHodFlag(boolean hodFlag) {
	this.hodFlag = hodFlag;
	}


	public boolean isMgmtFlag() {
	return mgmtFlag;
	}


	public void setMgmtFlag(boolean mgmtFlag) {
	this.mgmtFlag = mgmtFlag;
	}


	public boolean isNormalFlag() {
	return normalFlag;
	}


	public void setNormalFlag(boolean normalFlag) {
	this.normalFlag = normalFlag;
	}

	public String getDataCheck() {
	return dataCheck;
	}

	public void setDataCheck(String dataCheck) {
	this.dataCheck = dataCheck;
	}

	public String getLevel() {
	return level;
	}

	public void setLevel(String level) {
	this.level = level;
	}



	public JSONArray getJsonArray()
	{
	return jsonArray;
	}



	public void setJsonArray(JSONArray jsonArray)
	{
	this.jsonArray = jsonArray;
	}



	public JSONObject getJsonObject()
	{
	return jsonObject;
	}



	public void setJsonObject(JSONObject jsonObject)
	{
	this.jsonObject = jsonObject;
	}

	public Map<Integer, String> getServiceDeptList() {
	return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
	this.serviceDeptList = serviceDeptList;
	}

	public String getFilterFlag() {
	return filterFlag;
	}

	public void setFilterFlag(String filterFlag) {
	this.filterFlag = filterFlag;
	}

	public String getFilterDeptId() {
	return filterDeptId;
	}

	public void setFilterDeptId(String filterDeptId) {
	this.filterDeptId = filterDeptId;
	}


	public String getDept() {
	return dept;
	}

	public void setDept(String dept) {
	this.dept = dept;
	}

	public String getDept_id() {
	return dept_id;
	}

	public void setDept_id(String dept_id) {
	this.dept_id = dept_id;
	}

	public void setData4(String data4) {
	this.data4 = data4;
	}

	public String getData4() {
	return data4;
	}

	public void setDydept(String dydept) {
	this.dydept = dydept;
	}

	public String getDydept() {
	return dydept;
	}


	
}