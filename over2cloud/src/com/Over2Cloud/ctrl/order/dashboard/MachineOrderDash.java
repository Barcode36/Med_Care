package com.Over2Cloud.ctrl.order.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.dashboard.DashboardHelper;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
@SuppressWarnings("serial")
public class MachineOrderDash extends GridPropertyBean  {

    private String dataFlag="";
    private String feedStatus;
	private String dashFor="";
	private String level="";
 	private String dashboard;
 	private String fromDate="";
	private String toDate="";
	private String flag;
	private String headerValue;
	
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
	List<DashboardPojo> location_ticketList = null;
 	FeedbackPojo FP = null;
	private String loginType;
	private boolean hodFlag;
	private boolean mgmtFlag;
	private boolean normalFlag;
	private String maximizeDivBlock;
 	private Map<String,String> serviceDeptList;
	private String filterFlag=null;
	private String filterDeptId=null;
	private String escFlag=null;
	private String machineName;
	private String location;
	private String time;
	private JSONArray jsonArr = null;
	private String data4;
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
	headerValue="For"+" "+dept;
	}
	else if (loginType.equalsIgnoreCase("M")) {
	mgmtFlag=true;
	headerValue="";
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
	
 	public String beforeDashboardAction() {
	//System.out.println("MachineOrderDash.beforeDashboardAction()");
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	try {
	this.generalMethod();
	if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
	fromDate=DateUtil.getCurrentDateIndianFormat();
	toDate=DateUtil.getCurrentDateIndianFormat();
	//	System.out.println(fromDate+"   &  "+toDate);
	}
	if(dashFor!=null && dashFor.equalsIgnoreCase("Status"))
	{
	  getCounterData(fromDate,toDate);	
	}	
	else if(dashFor!=null && dashFor.equalsIgnoreCase("Level"))
	{
	getTicketDetailByLevel(fromDate,toDate);	
	}
	/*else if(dashFor!=null && dashFor.equalsIgnoreCase("locationLevel"))
	{
	getCounterDataForLocationLevel(fromDate,toDate);	
	}*/
	else if(dashFor!=null && dashFor.equalsIgnoreCase("NursingStatus"))
	{
	getCounterDataForNursingUnit(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("NursingOrder"))
	{
	getCounterDataForNursingUnitOrder(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("statusFloor"))
	{
	getCounterDataForFloorStatus(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("orderFloor"))
	{
	getCounterDataForFloorOrder(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("statusTime"))
	{
	getCounterDataForTime(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("floorTime"))
	{
	getCounterDataForTimeFloorStatus(fromDate,toDate);	
	}
	else if(dashFor!=null && dashFor.equalsIgnoreCase("nursingUnitTime"))
	{
	getCounterDataForNursingUnitTime(fromDate,toDate);	
	}
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
	serviceDeptList = new LinkedHashMap<String, String>();
	StringBuilder qry = new StringBuilder();
	 	qry.append("select machineName as ids,machineName as name from machine_name ");
	 	 	List departmentlist = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
	if (departmentlist != null && departmentlist.size() > 0)
	{
	for (Iterator iterator = departmentlist.iterator(); iterator
	.hasNext();) 
	{
	Object[] object1 = (Object[]) iterator.next();
	serviceDeptList.put(object1[0].toString(), object1[1].toString());
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
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : dept_subdeptcounterList)
	 {
	 	
	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("deptId",pojo.getId());
	jsonObject.put("Pending",Integer.parseInt(pojo.getPc()));
	 	jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
	 	jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
	 	
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
	public String getBarChart4NursingStatusCounters()
	{
	
	 	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
	try
	{
	if(fromDate==null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate==null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")){
	fromDate=DateUtil.getCurrentDateIndianFormat();
	toDate=DateUtil.getCurrentDateIndianFormat();
	}
	if(filterFlag.equalsIgnoreCase("N"))
	getCounterDataForNursingUnit(fromDate,toDate);
	else if(filterFlag.equalsIgnoreCase("F"))
	getCounterDataForFloorStatus(fromDate,toDate);
	else if(filterFlag.equalsIgnoreCase("T"))
	getCounterDataForTimeFloorStatus(fromDate,toDate);
	else if(filterFlag.equalsIgnoreCase("NT"))
	getCounterDataForNursingUnitTime(fromDate,toDate);
	
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : dept_subdeptcounterList)
	 {
	 	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("deptId",pojo.getId());
	jsonObject.put("Pending",Integer.parseInt(pojo.getPc()));
	 	jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
	 	jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
	jsonObject.put("Total", Integer.parseInt(pojo.getTotal()));
	 
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
	public String getBarChart4NursingOrderTypeCounters()
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
	if(filterFlag.equalsIgnoreCase("N"))
	getCounterDataForNursingUnitOrder(fromDate,toDate);
	else if(filterFlag.equalsIgnoreCase("F"))
	getCounterDataForFloorOrder(fromDate,toDate);
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : dept_subdeptcounterList)
	 {
	 	jsonObject.put("dept",pojo.getDeptName());
	jsonObject.put("deptId",pojo.getId());
	jsonObject.put("Urgent",Integer.parseInt(pojo.getPc()));
	 	jsonObject.put("Stat", Integer.parseInt(pojo.getSc()));
	 	jsonObject.put("Routine", Integer.parseInt(pojo.getRc()));
	 	
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
	getTicketDetailByLevel(fromDate,toDate);
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();
	
	for (DashboardPojo pojo : levelTicketsList)
	 {
	jsonObject.put("level",pojo.getLevel());
	jsonObject.put("id",pojo.getId());
	jsonObject.put("Pending",Integer.parseInt(pojo.getPc()));
	jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
	jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
	jsonObject.put("Total", Integer.parseInt(pojo.getTotal()));
	
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
	public String getBarChart4DeptCountersTime()
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
	getCounterDataForTime(fromDate, toDate);

	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	for (DashboardPojo pojo : location_ticketList)
	{
	jsonObject.put("time", pojo.getTime());
	jsonObject.put("timeId", pojo.getId());
	jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
	 	jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
	 	jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
	 	jsonObject.put("Total", Integer.parseInt(pojo.getTotal()));
	 
	jsonArray.add(jsonObject);
	}
	return SUCCESS;
	} catch (Exception e)
	{
	return ERROR;
	}
	} else
	{
	return LOGIN;
	}
	}

	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForTime(String fromDate, String toDate)
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{

	location_ticketList = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = getTimeList();
	this.generalMethod();

	if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
	{
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
	{
	Object object1 = (Object) iterator.next();
	DP = new DashboardPojo();
	DP.setTime(getTimeNameOfTime(object1.toString()));
	DP.setId(object1.toString());
	List dept_subdeptCounterList = new ArrayList();
	dept_subdeptCounterList =getTimeTicketsDetail(object1.toString(), connectionSpace, fromDate, toDate);
	 	if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
	{
	int total = 0,resolve=0;
	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending"))
	{
	DP.setPc(object2[1].toString());
	total += Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	DP.setSc(object2[1].toString());
	total += Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	} 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	}
	DP.setTotal(String.valueOf(total));
	}
	
	if (Integer.parseInt(DP.getTotal()) > 0)
	{
	location_ticketList.add(DP);
	dashObj.setDashList(location_ticketList);
	}
	}
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setTime("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	location_ticketList.add(DP);
	dashObj.setDashList(location_ticketList);
	}
	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getTimeTicketsDetail(String time,SessionFactory connection,String fromDate,String toDate){
	List dept_subdeptList = null;
	String fromTime=time.split("-")[0];
	String toTime=time.split("-")[1];
	StringBuilder query= new StringBuilder();
	try {
	query.append("select ord.status,count(*) from machine_order_data as ord ");
	query.append("inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	query.append("inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ")	;
	query.append("inner join floor_detail as fname on fname.id=nunit.floorname  ");
	query.append(" where ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openTime between '"+fromTime+"' and '"+toTime+"' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1")) 
	{
	query.append(" and mac.machine='" + filterDeptId + "' ");
	}
	else{
	query.append(" and mac.machine is not null ");
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	  	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.status order by ord.status");
	  	
	//	System.out.println("Time Query : "+query.toString());
	 	dept_subdeptList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	

	} catch (Exception e) {
	e.printStackTrace();
	}
	return dept_subdeptList;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getTimeList()
	{

	List dept_subdeptNameList = new ArrayList();
	 
	dept_subdeptNameList.add("00:00:00-01:00:00");
	dept_subdeptNameList.add("01:00:00-02:00:00");
	dept_subdeptNameList.add("02:00:00-03:00:00");
	dept_subdeptNameList.add("03:00:00-04:00:00");
	dept_subdeptNameList.add("04:00:00-05:00:00");
	dept_subdeptNameList.add("05:00:00-06:00:00");
	dept_subdeptNameList.add("06:00:00-07:00:00");
	dept_subdeptNameList.add("07:00:00-08:00:00");
	dept_subdeptNameList.add("08:00:00-09:00:00");
	dept_subdeptNameList.add("09:00:00-10:00:00");
	dept_subdeptNameList.add("10:00:00-11:00:00");
	dept_subdeptNameList.add("11:00:00-12:00:00");
	dept_subdeptNameList.add("12:00:00-13:00:00");
	dept_subdeptNameList.add("13:00:00-14:00:00");
	dept_subdeptNameList.add("14:00:00-15:00:00");
	dept_subdeptNameList.add("15:00:00-16:00:00");
	dept_subdeptNameList.add("16:00:00-17:00:00");
	dept_subdeptNameList.add("17:00:00-18:00:00");
	dept_subdeptNameList.add("18:00:00-19:00:00");
	dept_subdeptNameList.add("19:00:00-20:00:00");
	dept_subdeptNameList.add("20:00:00-21:00:00");
	dept_subdeptNameList.add("21:00:00-22:00:00");
	dept_subdeptNameList.add("22:00:00-23:00:00");
	dept_subdeptNameList.add("23:00:00-24:00:00");
	return dept_subdeptNameList;
	}
	
	
	private String getTimeNameOfTime(String time)
	{
	if (time.equalsIgnoreCase("00:00:00-01:00:00"))
	{
	return "1 AM";
	} else if (time.equalsIgnoreCase("01:00:00-02:00:00"))
	{
	return "2 AM";
	} else if (time.equalsIgnoreCase("02:00:00-03:00:00"))
	{
	return "3 AM";
	} else if (time.equalsIgnoreCase("03:00:00-04:00:00"))
	{
	return "4 AM";
	} else if (time.equalsIgnoreCase("04:00:00-05:00:00"))
	{
	return "5 AM";
	} else if (time.equalsIgnoreCase("05:00:00-06:00:00"))
	{
	return "6 AM";
	} else if (time.equalsIgnoreCase("06:00:00-07:00:00"))
	{
	return "7 AM";
	} else if (time.equalsIgnoreCase("07:00:00-08:00:00"))
	{
	return "8 AM";
	} else if (time.equalsIgnoreCase("08:00:00-09:00:00"))
	{
	return "9 AM";
	} else if (time.equalsIgnoreCase("09:00:00-10:00:00"))
	{
	return "10 AM";
	} else if (time.equalsIgnoreCase("10:00:00-11:00:00"))
	{
	return "11 AM";
	} else if (time.equalsIgnoreCase("11:00:00-12:00:00"))
	{
	return "12 AM";
	} else if (time.equalsIgnoreCase("12:00:00-13:00:00"))
	{
	return "1 PM";
	} else if (time.equalsIgnoreCase("13:00:00-14:00:00"))
	{
	return "2 PM";
	} else if (time.equalsIgnoreCase("14:00:00-15:00:00"))
	{
	return "3 PM";
	} else if (time.equalsIgnoreCase("15:00:00-16:00:00"))
	{
	return "4 PM";
	} else if (time.equalsIgnoreCase("16:00:00-17:00:00"))
	{
	return "5 PM";
	} else if (time.equalsIgnoreCase("17:00:00-18:00:00"))
	{
	return "6 PM";
	} else if (time.equalsIgnoreCase("18:00:00-19:00:00"))
	{
	return "7 PM";
	} else if (time.equalsIgnoreCase("19:00:00-20:00:00"))
	{
	return "8 PM";
	} else if (time.equalsIgnoreCase("20:00:00-21:00:00"))
	{
	return "9 PM";
	}

	else if (time.equalsIgnoreCase("21:00:00-22:00:00"))
	{
	return "10 PM";
	} else if (time.equalsIgnoreCase("22:00:00-23:00:00"))
	{
	return "11 PM";
	}
	else if (time.equalsIgnoreCase("23:00:00-24:00:00"))
	{
	return "12 PM";
	} 
	return null;
	}
	
	 
	
	
	
	public List getAllFloors(SessionFactory connection){
	List dept_subdeptList = null;
	StringBuilder qry= new StringBuilder();
	try {
	
	qry.append(" SELECT id,floorname from floor_detail");
	Session session = null;
	Transaction transaction = null;
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dept_subdeptList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();

	} catch (SQLGrammarException e) {
	e.printStackTrace();
	}
	return dept_subdeptList;
	}
	
	
	 
	
	 
	@SuppressWarnings("rawtypes")
	public void getCounterData(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	 dept_subdeptNameList=getSubDeptListByUID(filterDeptId,connectionSpace);
 	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
 	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	if(!filterDeptId.equalsIgnoreCase("-1"))
	DP.setDeptName(object1[1].toString()+"-"+object1[2].toString());
	else
	DP.setDeptName(object1[1].toString());
	 List dept_subdeptCounterList = new ArrayList();
	 	dept_subdeptCounterList = getDashboardCounter(object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	int total=0,resolve=0;
	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	
	
	 	DP.setTotal(String.valueOf(total));
	 
	     }
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}

	
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForNursingUnit(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList=getNursingUnitList(connectionSpace);
	 
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0,resolve=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnit( "nursingStatus",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForNursingUnitTime(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList=getNursingUnitList(connectionSpace);
	 
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0,resolve=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnit( "nursingTimeStatus",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForFloorStatus(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList = getAllFloors(connectionSpace);
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0,resolve=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnit( "floorStatus",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForTimeFloorStatus(String fromDate, String toDate)
	 {
	 dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList = getAllFloors(connectionSpace);
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0,resolve=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnit( "timeFloorStatus",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForNursingUnitOrder(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList=getNursingUnitList(connectionSpace);
	 
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnitOrder( "nursingOrder",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Urgent")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Stat")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Routine")) {
	DP.setRc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	
	@SuppressWarnings("rawtypes")
	public void getCounterDataForFloorOrder(String fromDate, String toDate)
	 {
	dashObj=new DashboardPojo();
	DashboardPojo DP=null;
	try {
	 	this.generalMethod();
	 
	 flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
	List dept_subdeptNameList = null;
	dept_subdeptNameList = getAllFloors(connectionSpace);
 
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
	int pentot=0,snootot=0,restot=0;
	for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
	Object[] object1 = (Object[]) iterator.next();
	DP=new DashboardPojo();
	DP.setId(object1[0].toString());
	DP.setDeptName(object1[1].toString());
	 	List dept_subdeptCounterList = new ArrayList();
	 	int total=0;
	 	dept_subdeptCounterList = getDashboardCounterForNursingUnitOrder( "floorOrder",object1[0].toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Urgent")) {
	DP.setPc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Stat")) {
	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Routine")) {
	DP.setRc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	}
	     }
	DP.setTotal(String.valueOf(total));
	 
	     }
	 	if(total!=0)
	 	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	     }
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setDeptName("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	}

	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	@SuppressWarnings("rawtypes")
	public List getDashboardCounter (String subdeptid, SessionFactory connection, String fromDate, String toDate)
	{
	List dashDeptList = new ArrayList();
	StringBuffer query = new StringBuffer();
	
	try {
	  
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1"))
	{
	query.append("select  ord.status,count(*) from machine_order_data as ord ");
	query.append(" where ord.assignMchn=" + subdeptid + " and ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status order by ord.status");
	
	}
	else if(filterDeptId!=null && filterDeptId.equalsIgnoreCase("-1"))
	{
	query.append("select  ord.status,count(*) from machine_order_data as ord ");
	query.append(" where ord.assignMchn In(Select id From machine_master where machine='"+subdeptid+"') and ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status order by ord.status");
	
	}
//	System.out.println("@@@@@@@@@@@@"+query.toString());
	dashDeptList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	return dashDeptList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getDashboardCounterForNursingUnit (String flag, String id, SessionFactory connection, String fromDate, String toDate)
	{
	List dashDeptList = new ArrayList();
	StringBuffer query = new StringBuffer();
	
	if(flag.equalsIgnoreCase("nursingStatus"))
	{
	 	query.append("select ord.status,count(*) from machine_order_data as ord ");
	query.append("	 Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	query.append("Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ");
	query.append("	Inner join floor_detail as fname on fname.id=nunit.floorname  ");
	query.append("	where ord.nursingUnit='" + id + "' and fname.id='"+location+"' and ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1")) 
	{
	query.append(" and mac.machine='" + filterDeptId + "' ");
	}
	else{
	query.append(" and mac.machine is not null ");
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	 	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.status order by ord.status");
	
	}
	else if(flag.equalsIgnoreCase("floorStatus"))
	
	{
	
	 	query.append("select ord.status, count(*) from machine_order_data as ord  ");
	query.append("	 Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	  	query.append("	 Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit  ");
	 	query.append("	Inner join floor_detail as fname on fname.id=nunit.floorname  ");
	query.append(" where fname.id='" + id + "' and  ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore') and  ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) +"' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1")) 
	{
	query.append(" and mac.machine='" + filterDeptId + "' ");
	}
	else{
	query.append(" and mac.machine is not null ");
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	 	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.status order by ord.status");
	//System.out.println("@@@@@@@@@@@@FloorStatus    "+query);
	 	}
	else if(flag.equalsIgnoreCase("timeFloorStatus"))
	{
	String fromTime=time.split("-")[0];
	String toTime=time.split("-")[1];
	 	query.append("select ord.status,count(*) from machine_order_data as ord ");
	query.append("	 Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	query.append("Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ");
	query.append("	Inner join floor_detail as fname on fname.id=nunit.floorname  ");
	query.append(" where  fname.id='" + id + "'  and ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openTime between '"+fromTime+"' and '"+toTime+"' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1")) 
	{
	query.append(" and mac.machine='" + filterDeptId + "' ");
	}
	else{
	query.append(" and mac.machine is not null ");
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.status order by ord.status");
	 	
	}
	
	else if(flag.equalsIgnoreCase("nursingTimeStatus"))
	{
	String fromTime=time.split("-")[0];
	String toTime=time.split("-")[1];
	 
	 	query.append("select ord.status,count(*) from machine_order_data as ord ");
	query.append("	 Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	query.append("Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ");
	query.append("	Inner join floor_detail as fname on fname.id=nunit.floorname  ");
	query.append(" where ord.nursingUnit='" + id + "' and fname.id='"+location+"'  and ord.status in('Pending','Resolved','Snooze','CancelHIS','Ignore') and ord.openTime between '"+fromTime+"' and '"+toTime+"'  and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1")) 
	{
	query.append(" and mac.machine='" + filterDeptId + "' ");
	}
	else{
	query.append(" and mac.machine is not null ");
	} 
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	 	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.status order by ord.status");
	 	
	}
	//System.out.println("@@@@@@@@@@@@NursingUnitStatusTime    "+query);
	dashDeptList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

	return dashDeptList;
	}
	
	public List getDashboardCounterForNursingUnitOrder(String flag, String id, SessionFactory connection, String fromDate, String toDate)
	{
	List dashDeptList = new ArrayList();
	StringBuffer query = new StringBuffer();
	 	if(flag.equalsIgnoreCase("nursingOrder"))
	{
	query.append("select ord.priority,count(*) from machine_order_data as ord ");
	query.append("Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	query.append("Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ");
	 	query.append("Inner join floor_detail as fname on fname.id=nunit.floorname ");
	query.append(" where ord.nursingUnit='" + id + "' and fname.id='"+location+"'  and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1"))
	{
	query.append(" and mac.machine='" + filterDeptId + "'");	
	}
	else{
	query.append(" and mac.machine is not null ");	
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	  	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.priority order by ord.priority");
	 
	 
	}
	else if(flag.equalsIgnoreCase("floorOrder"))
	{
	query.append("select ord.priority,count(*) from machine_order_data as ord ");
	query.append("Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	query.append("Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursingUnit ");
	query.append("Inner join floor_detail as fname on fname.id=nunit.floorname ");
	query.append(" where fname.id='" + id + "' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase("-1"))
	{
	query.append(" and mac.machine='" + filterDeptId + "'");	
	}
	else{
	query.append(" and mac.machine is not null ");	
	}
	if(machineName!=null && !machineName.equalsIgnoreCase("-1"))
	query.append(" and mac.serial_No='" + machineName + "' ");
	  	if(escFlag!=null && escFlag.equalsIgnoreCase("true"))
	 	query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6') ");
	query.append(" group by ord.priority order by ord.priority");
	  	
	}
	 	//System.out.println("@@@@@@@@@@@@NursingUnitOrderType    "+query);
	dashDeptList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

	return dashDeptList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getDashboardCounterForLevel (String empName,String level,SessionFactory connection, String fromDate, String toDate)
	{
	List dashDeptList = new ArrayList();
	StringBuffer query = new StringBuffer();
	 	query.append("select  ord.status,count(*),ord.level from machine_order_data as ord ");
	 	query.append("	 Inner join machine_master as mac on  mac.id=ord.assignMchn  ");
	 	if(machineName!=null && !machineName.equalsIgnoreCase("-1") && !filterDeptId.equalsIgnoreCase("-1"))
	 	{
	 	query.append(" where ord.assignMchn in(select deptName.id from machine_master as deptName where deptName.status='Active' and deptName.machine='"+filterDeptId+"' and deptName.serial_No='"+machineName+"' ) and ord.level='" + level + "' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status ");
	 	}
	 	else if(machineName!=null && machineName.equalsIgnoreCase("-1") && !filterDeptId.equalsIgnoreCase("-1"))
	 	{
	 	query.append(" where ord.assignMchn in(select deptName.id from machine_master as deptName where deptName.status='Active' and deptName.machine='"+filterDeptId+"' ) and ord.level='" + level + "' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status ");
	 	}
	  	else if(!machineName.equals("-1") && filterDeptId.equals("-1")) {
	  	query.append(" where ord.assignMchn in(select deptName.id from machine_master as deptName where deptName.status='Active' and deptName.serial_No='"+machineName+"' ) and ord.level='" + level + "' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status ");	
	 	}
	  	else if(machineName.equals("-1") && filterDeptId.equals("-1")) {
	  	query.append(" where ord.assignMchn in(select deptName.id from machine_master as deptName where deptName.status='Active' ) and ord.level='" + level + "' and ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.status ");	
	 	}
	// 	System.out.println("$$$$$$$$$$$$$ For Level :  "+query);
	dashDeptList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

	return dashDeptList;
	}

	 
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUID(String dept_id,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer query=new StringBuffer();
	  if (loginType.equalsIgnoreCase("M")) 
	{
	  if(!dept_id.equalsIgnoreCase("-1"))
	  {
	  query.append("select dept.id,dept.machine,dept.serial_No from machine_master as dept");
	  query.append(" where dept.status='Active' and dept.machine='"+dept_id+"'");
	  }
	  else
	  {
	  query.append("select distinct dept.machine as id,dept.machine as name from machine_master as dept");
	  query.append(" where dept.status='Active'");
	  }
	 // System.out.println("QQQ >> "+query.toString());
	}
	 Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	public String fetchMachineSerial()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	 	jsonArr = new JSONArray();
	 	StringBuilder qry = new StringBuilder();
	qry.append("select serial_No as ids,serial_No as name from machine_master");
	qry.append(" Where machine='"+filterDeptId+"' ");
	 	 
	//	System.out.println("qry>>>>  " + qry);
	List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

	if (data != null && !data.isEmpty())
	{
	JSONObject obj = new JSONObject();
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && object[1] != null)
	{
	obj.put("id", object[0].toString());
	obj.put("name", object[1].toString());
	jsonArr.add(obj);
	}
	}

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
 

	
	@SuppressWarnings("unchecked")
	public List getNursingUnitList(SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer query=new StringBuffer();
	  if (loginType.equalsIgnoreCase("M")) 
	{
	query.append("select nursing_code,nursing_unit from machine_order_nursing_details ");
	 	query.append(" where status='1' ");
	}
	 Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	
	
	 	
	
	@SuppressWarnings("rawtypes")
	public void getTicketDetailByLevel(String fromDate, String toDate)
	 {
	 	DashboardPojo DP=null;
	try {
	
	 
	this.generalMethod();
	 	flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
	 	List levelList = new ArrayList();
	 
	levelList.add("Level1");
	 	levelList.add("Level2");
	levelList.add("Level3");
	levelList.add("Level4");
	levelList.add("Level5");
	levelList.add("Level6");

	levelTicketsList = new ArrayList<DashboardPojo>();
	 	leveldashObj = new DashboardPojo();
	int totalCount = 0;
	//List dept_subdeptNameList = null;
	int hptot=0,
	igtot=0,
	pentot=0,snootot=0,chistot=0,restot=0,lasttot=0;
	 	 if (levelList!=null && levelList.size()>0) {
	for (Iterator iterator = levelList.iterator(); iterator.hasNext();)
	{
	totalCount = 0;
	Object object = (Object) iterator.next();
	DP = new DashboardPojo();
	  
	DP.setLevel(object.toString());
	 	List dept_subdeptCounterList = new ArrayList();
	  	dept_subdeptCounterList = getDashboardCounterForLevel( empName,object.toString(),connectionSpace,fromDate,toDate);
	 	if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
	int total=0,resolve=0;
	
	 	for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
	
	Object[] object2 = (Object[]) iterator2.next();
	  if (object2[0].toString().equalsIgnoreCase("Pending")) {
	 	DP.setPc(object2[1].toString());
	pentot=pentot+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	}
	else if (object2[0].toString().equalsIgnoreCase("Snooze")) {
	 	DP.setSc(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	snootot=snootot+Integer.parseInt(object2[1].toString());
	    }
	 
	else  if (object2[0].toString().equalsIgnoreCase("Resolved") || object2[0].toString().equalsIgnoreCase("Ignore") || object2[0].toString().equalsIgnoreCase("CancelHIS")) 
	{
	if(object2[0].toString().equalsIgnoreCase("Resolved"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("Ignore"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	else if(object2[0].toString().equalsIgnoreCase("CancelHIS"))
	{
	resolve=resolve+Integer.parseInt(object2[1].toString());
	total=total+Integer.parseInt(object2[1].toString());
	restot=restot+Integer.parseInt(object2[1].toString());
	 	}
	 	DP.setRc(String.valueOf(resolve));
	  	}
	     }
	 	
	  	DP.setTotal(String.valueOf(total));
	 
	     }
	 
	 	levelTicketsList.add(DP);
	 	leveldashObj.setDashList(levelTicketsList);
	 
	 	}
	if(data4!=null && data4.equalsIgnoreCase("table"))
	{
	DP=new DashboardPojo();
	DP.setId("-1");
	DP.setLevel("Total");
	DP.setPct(Integer.toString(pentot));
	DP.setSct(Integer.toString(snootot));
	DP.setRct(Integer.toString(restot));
	String str=Integer.toString(pentot+snootot+restot);
	DP.setGrand(str);
	levelTicketsList.add(DP);
	leveldashObj.setDashList(levelTicketsList);
	}
	  }} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	
	
	
 
	 
	
	public String getDashboard() {
	return dashboard;
	}
	public void setDashboard(String dashboard) {
	this.dashboard = dashboard;
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

	 

	public String getFlag() {
	return flag;
	}

	public void setFlag(String flag) {
	this.flag = flag;
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

  
	public List<FeedbackPojo> getTicketsList() {
	return ticketsList;
	}
	public void setTicketsList(List<FeedbackPojo> ticketsList) {
	this.ticketsList = ticketsList;
	}

 
	public FeedbackPojo getFP() {
	return FP;
	}
	public void setFP(FeedbackPojo fp) {
	FP = fp;
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

	public Map<String, String> getServiceDeptList() {
	return serviceDeptList;
	}

	public void setServiceDeptList(Map<String, String> serviceDeptList) {
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
	public List<DashboardPojo> getLocation_ticketList() {
	return location_ticketList;
	}

	public void setLocation_ticketList(List<DashboardPojo> location_ticketList) {
	this.location_ticketList = location_ticketList;
	}

	 

	public String getEscFlag() {
	return escFlag;
	}

	public void setEscFlag(String escFlag) {
	this.escFlag = escFlag;
	}

	public String getMachineName() {
	return machineName;
	}

	public void setMachineName(String machineName) {
	this.machineName = machineName;
	}
	public String getLocation() {
	return location;
	}

	public void setLocation(String location) {
	this.location = location;
	}

	public String getTime() {
	return time;
	}

	public void setTime(String time) {
	this.time = time;
	}

	public JSONArray getJsonArr() {
	return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr) {
	this.jsonArr = jsonArr;
	}

	public void setData4(String data4) {
	this.data4 = data4;
	}

	public String getData4() {
	return data4;
	}

	
}