package com.Over2Cloud.ctrl.order.pharmacy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.order.pharmacy.DashboardPojo;
import java.text.DecimalFormat;
 
 

@SuppressWarnings("serial")
public class PharmacyDashboard extends GridPropertyBean
{

	
	private String fromDate = null;
	private String toDate = null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private JSONArray jsonArr = null;
	private String id = null;
	private String name = null;
	private String tableFor=null;
	List<DashboardPojo> data_list = null;
	DashboardPojo dashObj = null;
	private String status=null;
	private String statusFor=null;
	private String fromTime;
	private String toTime;
	private String productivityLimit=null;
	private String productivityFor=null;
 	private List<GridDataPropertyView> viewPageColumns = new ArrayList<GridDataPropertyView>();
 	private List<Object> masterViewProp = null;
	private String corporateName = null;
 	private String locationName=null;
	private String serviceName=null;
	public Map<String, String> locationMap;
	public Map<String, String> nursingMap;	 
 	private String loginType;
	private boolean hodFlag;
	private boolean mgmtFlag;
	private boolean normalFlag;
	private String location;
	private String nursingUnit,priority;
	
	@SuppressWarnings("rawtypes")
  	public String beforeDashboardAction()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
	try {
	// this.generalMethod();
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	 	}
	  	if(tableFor!=null)
	  	{
	  	fetchFilterNurshingUnit();
	fetchFilterLocation();
	getCounterData();
	  	}
	 	returnResult = SUCCESS;
	} catch (Exception e) {
	addActionError("Ooops There Is Some Problem In beforeDashboardAction in DashboardAction !!!");
	e.printStackTrace();
	returnResult = ERROR;
	}
	} else {
	returnResult = LOGIN;
	}
	return returnResult;

	}

	 

	// For Bar Graph
	public String fetchCounters()
	{

 	boolean validSession = ValidateSession.checkSession();
	if (validSession) {
	try {
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	 	getCounterData();
	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	for (DashboardPojo pojo : data_list) {
	if(tableFor!=null)
	{
	jsonObject.put("Id", pojo.getId());
	jsonObject.put("Status", pojo.getStatus());
	jsonObject.put("TableFor", pojo.getTableFor());
	if(tableFor.equalsIgnoreCase("productivityPie") || tableFor.equalsIgnoreCase("productivityBar"))
	jsonObject.put(tableFor,"Level"+ pojo.getName());
	 	else
	 	jsonObject.put(tableFor, pojo.getName());	
	 	jsonObject.put("Counter", Integer.parseInt(pojo.getCounter()));
	 	jsonArray.add(jsonObject);
	}
	}
	return SUCCESS;
	} catch (Exception e) {
	return ERROR;
	}
	} else {
	return LOGIN;
	}
	}

	 
	 
	@SuppressWarnings("rawtypes")
	public void getCounterData()
	{
	 	DashboardPojo DP = null;
	try {
	data_list = new ArrayList<DashboardPojo>();
	int total = 0,ordered=0,dispatch=0,dispatch_error=0,close=0,dispatch_p=0,close_p=0,auto_close=0,force_close=0;
	int level1=0,level2=0,level3=0,level4=0,level5=0;
	 	String flagName="";
	 	Object[] object=null;
	 	List dataList = null;
	  	if(tableFor!=null && tableFor.equalsIgnoreCase("statusPie") || tableFor.equalsIgnoreCase("statusBar") || tableFor.equalsIgnoreCase("statusBarTAT"))
	 	dataList = fetchPharmacyStatusCounter();
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("priorityPie") || tableFor.equalsIgnoreCase("priorityBar"))
	 	dataList = fetchPharmacyPriorityCounter();
	  	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("locationStatusBar"))
	 	dataList = fetchPharmacyLocationStatusCounter();
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("nursingUnitStatusBar"))
	 	dataList = fetchPharmacyNursingStatusCounter();
	  	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("specialityBar"))
	 	dataList = fetchPharmacySpecialityStatusCounter();
	  	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("doctorBar"))
	 	dataList = fetchPharmacyDoctorStatusCounter();
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("productivityPie") || tableFor.equalsIgnoreCase("productivityBar"))
	 	dataList = fetchPharmacyProductivityCounter();
	 	//else if(tableFor!=null && tableFor.equalsIgnoreCase("locationProductivityBar"))
	 	//	dataList = fetchPharmacyLocationProductivityCounter();
	 	 
	 	 
	 	//	else if(tableFor!=null && tableFor.equalsIgnoreCase("nursingUnitProductivityBar"))
	 	//	dataList = fetchPharmacyNursingProductivityCounter();
	  	
	  	
	  	if (dataList != null && dataList.size() > 0) {
	 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	 	object = (Object[]) iterator.next();
	 	
	 	if (flagName!=null && !flagName.equalsIgnoreCase(object[0].toString()))
	 	{
	 	 	DP = new DashboardPojo();
	 	 	ordered=0;
	 	 	dispatch=0;
	 	 	dispatch_error=0;
	 	 	dispatch_p=0;
	 	 	close=0;
	 	 	close_p=0;
	 	 	force_close=0;
	 	 	auto_close=0;
	 	 	level1=0;
	 	 	level2=0;
	 	 	level3=0;
	 	 	level4=0;
	 	 	level5=0;
	 	 	total=0;
	 	}
	  	  	DP.setName(object[0].toString());
	  	   	  	DP.setId(object[3].toString());
	  	   	  	DP.setStatus(object[1].toString());
	  	   	  	DP.setTableFor(tableFor);
	  	  	if(tableFor!=null && (tableFor.equalsIgnoreCase("locationStatusBar") || tableFor.equalsIgnoreCase("statusPie") || tableFor.equalsIgnoreCase("statusBar") || tableFor.equalsIgnoreCase("nursingUnitStatusBar") || tableFor.equalsIgnoreCase("specialityBar") || tableFor.equalsIgnoreCase("doctorBar") || tableFor.equalsIgnoreCase("statusBarTAT")))
	 	{
	  	  	if(object[1]!=null && (object[1].toString().equalsIgnoreCase("Ordered") || object[1].toString().equalsIgnoreCase("Parked")) )
	  	  	ordered=ordered+Integer.parseInt(object[2].toString());
	  	  	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Dispatch"))
	  	  	dispatch=dispatch+Integer.parseInt(object[2].toString());
	  	 	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Dispatch-P"))
	  	  	dispatch_p=dispatch_p+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("Dispatch Error"))
	  	dispatch_error=dispatch_error+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Close-P"))
	  	close_p=close_p+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Close"))
	  	close=close+Integer.parseInt(object[2].toString());
	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Force Close"))
	force_close=force_close+Integer.parseInt(object[2].toString());
	if(object[1]!=null && object[1].toString().equalsIgnoreCase("Auto Close"))
	auto_close=auto_close+Integer.parseInt(object[2].toString());
	  	
	 	 	total=total+Integer.parseInt(object[2].toString());
	 	   	 
	 	}
	  	  if(tableFor!=null && ( tableFor.equalsIgnoreCase("priorityPie") || tableFor.equalsIgnoreCase("priorityBar")))
	 	{
	  	total=Integer.parseInt(object[2].toString());
	 	}
	 	 
	 	  	  if(tableFor!=null && (tableFor.equalsIgnoreCase("locationProductivityBar") || tableFor.equalsIgnoreCase("productivityPie") || tableFor.equalsIgnoreCase("productivityBar") || tableFor.equalsIgnoreCase("nursingUnitProductivityBar") ))
	 	{
	  	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("1"))
	  	  	level1=level1+Integer.parseInt(object[2].toString());
	  	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("2")  )
	  	  	level2=level2+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("3"))
	  	level3=level3+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("4")  )
	  	level4=level4+Integer.parseInt(object[2].toString());
	  	if(object[1]!=null &&  object[1].toString().equalsIgnoreCase("5")  )
	  	level5=level5+Integer.parseInt(object[2].toString());
	  	
	 	 	total=total+Integer.parseInt(object[2].toString());
	 	   	 
	 	}
	  	  	
	 	  	DP.setCounter(String.valueOf(total));
	 	  	DP.setOrdered(String.valueOf(ordered));
	 	DP.setDispatch(String.valueOf(dispatch));
	 	DP.setDispatchError(String.valueOf(dispatch_error));
	 	DP.setClose(String.valueOf(close));
	 	DP.setDispatchP(String.valueOf(dispatch_p));
	 	DP.setCloseP(String.valueOf(close_p));
	 	DP.setForceClose(String.valueOf(force_close));
	 	DP.setAutoClose(String.valueOf(auto_close));
	 	
	 	DP.setLevel1(String.valueOf(level1));
	 	DP.setLevel2(String.valueOf(level2));
	 	DP.setLevel3(String.valueOf(level3));
	 	DP.setLevel4(String.valueOf(level4));
	 	DP.setLevel5(String.valueOf(level5));
	 	if (total != 0 && flagName!=null && !flagName.equalsIgnoreCase(object[0].toString()))
	 	data_list.add(DP);
	   	flagName = object[0].toString();
	  	}
	 	}
	 	 
	} catch (Exception e) {
	e.printStackTrace();
	}
	}

	 
	
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyStatusCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select cd.status as status1,cd.status as status2,count(*),cd.id from pharma_patient_detail as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("undefined")  && !location.equalsIgnoreCase("null")&& !location.equalsIgnoreCase("undefined")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("undefined") && !nursingUnit.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	  	
	 	query.append(" and cd.nursing_code!='NA' group by cd.status ");
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyPriorityCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select cd.priority as ids,cd.priority as name ,count(*),cd.id from pharma_patient_detail as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	query.append(" group by cd.priority ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public String fetchPharmacyTATCounter()
	{
	DashboardPojo DP = null;
	DecimalFormat df = new DecimalFormat("#.##");
	data_list = new ArrayList<DashboardPojo>();
	int withInTatRoutine=0,outOfTatRoutine=0,withInTatUrgent=0,outOfTatUrgent=0;
	List dataList=null;
	String resolutionTime=null;
	try {
	 	StringBuffer query = new StringBuffer();
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	query.append(" select distinct cd.ENCOUNTER_ID,cd.DISPENSED_DATE,cd.DISPENSED_TIME,cd.close_date,cd.close_time,cd.PRIORITY from pharma_medicine_data as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") && !location.equalsIgnoreCase("undefined"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.ORDER_DATE='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.ORDER_DATE='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.ORDER_DATE between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") && !location.equalsIgnoreCase("undefined")) 
 	{
 	query.append(" and fname.id In(" + location + " )");
 	}
 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
 	{
 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
 	}
 	if(priority!=null && !priority.equalsIgnoreCase("All")  )
 	{
 	query.append(" and cd.PRIORITY = '" + priority + "' ");
 	}
	query.append(" and cd.DISPENSED_DATE!='NA' and cd.DISPENSED_TIME!='NA' and cd.close_date!='NA' and cd.close_time!='NA' and priority!='NA'  group by cd.ENCOUNTER_ID");
//	System.out.println(">>>>>>>>"+query.toString());
	 	dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
 	  	if (dataList != null && dataList.size() > 0) {
	 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	 	Object[] object = (Object[]) iterator.next();
	 	 
	  	   if(object[5]!=null && object[5].toString().equalsIgnoreCase("Routine"))
	  	   {
	  	   if(object[1]!=null  && object[2]!=null  && object[3]!=null  && object[4]!=null )
	  	   {
	  	resolutionTime = DateUtil.time_difference(object[1].toString(), object[2].toString().replace(" ", ":"), DateUtil.convertDateToUSFormat(object[3].toString()), object[4].toString().replace(" ", ":"));
	  	int i = Integer.parseInt(resolutionTime.split(":")[0]);
	  	if(i < 2){
	  	withInTatRoutine++;
	}
	else{
	outOfTatRoutine++;
	}
	  	   }
	  	   
	  	   }
	  	 if(object[5]!=null && object[5].toString().equalsIgnoreCase("Urgent"))
	  	   {
	  	   if(object[1]!=null  && object[2]!=null  && object[3]!=null  && object[4]!=null )
	  	   {
	  	resolutionTime = DateUtil.time_difference(object[1].toString(), object[2].toString().replace(" ", ":"), DateUtil.convertDateToUSFormat(object[3].toString()), object[4].toString().replace(" ", ":"));
	  	int i = Integer.parseInt(resolutionTime.split(":")[0]);
	  	if(i < 1){
	  	withInTatUrgent++;
	}
	else{
	outOfTatUrgent++;
	}
	  	   }
	  	   }
	  	  	 	  
	 	}
	 	DP = new DashboardPojo();
	 	  	DP.setStatus("Urgent");
	 	  	DP.setWithInTat(String.valueOf(withInTatUrgent));
	 	DP.setOutOfTat(String.valueOf(outOfTatUrgent));
	 	data_list.add(DP);
	 	
	 	DP = new DashboardPojo();
	 	DP.setStatus("Routine");
	 	  	DP.setWithInTat(String.valueOf(withInTatRoutine));
	 	DP.setOutOfTat(String.valueOf(outOfTatRoutine));
	 	data_list.add(DP);
	 	
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	for (DashboardPojo pojo : data_list) {
	 
	
	 
	
	jsonObject.put("Status", pojo.getStatus());
	 	jsonObject.put("WithInTAT", Integer.parseInt(pojo.getWithInTat()));
	 	jsonObject.put("WithInTATper", df.format(((double)Integer.parseInt(pojo.getWithInTat())/(Integer.parseInt(pojo.getWithInTat())+Integer.parseInt(pojo.getOutOfTat())))*100));
	 	jsonObject.put("OutOfTAT", Integer.parseInt(pojo.getOutOfTat()));
	 	jsonObject.put("OutOfTATper", df.format(((double)Integer.parseInt(pojo.getOutOfTat())/(Integer.parseInt(pojo.getWithInTat())+Integer.parseInt(pojo.getOutOfTat())))*100));
	 	jsonArray.add(jsonObject);
	 
	}
	   	 
 	}
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return "success";
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public String fetchPharmacyTATCounterPie()
	{
	DashboardPojo DP = null;
	DecimalFormat df = new DecimalFormat("#.##");
	data_list = new ArrayList<DashboardPojo>();
	int withInTatRoutine=0,outOfTatRoutine=0,withInTatUrgent=0,outOfTatUrgent=0;
	List dataList=null;
	String resolutionTime=null;
	try {
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	}
	 	StringBuffer query = new StringBuffer();
	query.append(" select distinct cd.ENCOUNTER_ID,cd.DISPENSED_DATE,cd.DISPENSED_TIME,cd.close_date,cd.close_time,cd.PRIORITY from pharma_medicine_data as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") && !location.equalsIgnoreCase("undefined"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.ORDER_DATE='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.ORDER_DATE='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.ORDER_DATE between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") && !location.equalsIgnoreCase("undefined")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null") && !nursingUnit.equalsIgnoreCase("undefined"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	if(priority!=null && !priority.equalsIgnoreCase("All")  )
	 	{
	 	query.append(" and cd.PRIORITY = '" + priority + "' ");
	 	}
	query.append(" and cd.DISPENSED_DATE!='NA' and cd.DISPENSED_TIME!='NA' and cd.close_date!='NA' and cd.close_time!='NA' and priority!='NA'  group by cd.ENCOUNTER_ID");
	//	System.out.println(">>>>>>>>"+query.toString());
	 	dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
 	  	if (dataList != null && dataList.size() > 0) {
	 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	 	Object[] object = (Object[]) iterator.next();
	 	 
	  	   if(object[5]!=null && object[5].toString().equalsIgnoreCase("Routine"))
	  	   {
	  	   if(object[1]!=null  && object[2]!=null  && object[3]!=null  && object[4]!=null )
	  	   {
	  	resolutionTime = DateUtil.time_difference(object[1].toString(), object[2].toString().replace(" ", ":"), DateUtil.convertDateToUSFormat(object[3].toString()), object[4].toString().replace(" ", ":"));
	  	int i = Integer.parseInt(resolutionTime.split(":")[0]);
	  	if(i < 2){
	  	withInTatRoutine++;
	}
	else{
	outOfTatRoutine++;
	}
	  	   }
	  	   
	  	   }
	  	 if(object[5]!=null && object[5].toString().equalsIgnoreCase("Urgent"))
	  	   {
	  	   if(object[1]!=null  && object[2]!=null  && object[3]!=null  && object[4]!=null )
	  	   {
	  	resolutionTime = DateUtil.time_difference(object[1].toString(), object[2].toString().replace(" ", ":"), DateUtil.convertDateToUSFormat(object[3].toString()), object[4].toString().replace(" ", ":"));
	  	int i = Integer.parseInt(resolutionTime.split(":")[0]);
	  	if(i < 1){
	  	withInTatUrgent++;
	}
	else{
	outOfTatUrgent++;
	}
	  	   }
	  	   }
	  	  	 	  
	 	}
	 	if(priority!=null && priority.equalsIgnoreCase("All")  )
	 	{
	 	DP = new DashboardPojo();
	 	  	DP.setStatus("With In TAT");
	 	  	DP.setWithInTat(String.valueOf(withInTatUrgent+withInTatRoutine));
	 	data_list.add(DP);
	 	
	 	DP = new DashboardPojo();
	 	DP.setStatus("Out Of TAT");
	 	DP.setWithInTat(String.valueOf(outOfTatUrgent+outOfTatRoutine));
	 	data_list.add(DP);
	 	}
	if(priority!=null && priority.equalsIgnoreCase("Routine")  )
	 	{
	 	DP = new DashboardPojo();
	 	  	DP.setStatus("With In TAT");
	 	  	DP.setWithInTat(String.valueOf(withInTatRoutine));
	 	data_list.add(DP);
	 	
	 	DP = new DashboardPojo();
	 	DP.setStatus("Out Of TAT");
	 	DP.setWithInTat(String.valueOf(outOfTatRoutine));
	 	data_list.add(DP);
	 	}
	if(priority!=null && priority.equalsIgnoreCase("Urgent")  )
	 	{
	 	DP = new DashboardPojo();
	 	  	DP.setStatus("With In TAT");
	 	  	DP.setWithInTat(String.valueOf(withInTatUrgent));
	 	data_list.add(DP);
	 	
	 	DP = new DashboardPojo();
	 	DP.setStatus("Out Of TAT");
	 	DP.setWithInTat(String.valueOf(outOfTatUrgent));
	 	data_list.add(DP);
	 	}
	 	
	 	jsonObject = new JSONObject();
	jsonArray = new JSONArray();

	for (DashboardPojo pojo : data_list) {
	jsonObject.put("Status", pojo.getStatus());
	 	jsonObject.put("WithInTAT", Integer.parseInt(pojo.getWithInTat()));
	 	jsonArray.add(jsonObject);
	}
	   	 
 	}
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return "success";
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyLocationStatusCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select fname.floorname as name,cd.status,count(*),fname.id from pharma_patient_detail as cd ");
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	query.append(" group by fname.floorname,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacySpecialityStatusCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select cd.speciality as name,cd.status,count(*),cd.speciality as id from pharma_patient_detail as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	query.append(" group by cd.speciality,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyNursingStatusCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select nunit.nursing_unit as name,cd.status,count(*),nunit.id from pharma_patient_detail as cd ");
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	  	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	if(id!=null && !id.equalsIgnoreCase(""))
	 	{
	 	query.append(" and nunit.floorname='" + id + "' ");
	 	}
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	query.append(" group by nunit.nursing_unit,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyDoctorStatusCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select cd.admitting_pra_name as name,cd.status,count(*),cd.admitting_pra_name as id from pharma_patient_detail as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}  	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	 	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.status='" + status + "' ");
	 	}
	 	if(id!=null && !id.equalsIgnoreCase(""))
	 	{
	 	query.append(" and cd.speciality='" + id.trim() + "' ");
	 	}
	 	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	query.append(" group by cd.admitting_pra_name,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyProductivityCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select cd.level as lvl1,cd.level as lvl2,count(*),cd.id from pharma_patient_detail as cd ");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null"))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=cd.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.level='" + status + "' ");
	 	}
	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
	 	{
	 	query.append(" and fname.id In(" + location + " )");
	 	}
	 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
	 	{
	 	query.append(" and cd.nursing_code In(" + nursingUnit + ") ");
	 	}
	 	
	 	query.append(" group by cd.level ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	
	/*@SuppressWarnings("rawtypes")
	public List fetchPharmacyLocationProductivityCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select fname.floorname as name,cd.level,count(*),fname.id from pharma_patient_detail as cd ");
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cd.nursing_unit ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.level='" + status + "' ");
	 	}
	 	
	 	
	 	query.append(" group by fname.floorname,cd.level ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchPharmacyNursingProductivityCounter()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	query.append(" select nunit.nursing_unit as name,cd.level,count(*),fname.id from pharma_patient_detail as cd ");
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cd.nursing_unit ");
 	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '23:59:59')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	   
	if(status!=null && !status.equalsIgnoreCase("") &&  !status.equalsIgnoreCase("All"))
	 	{
	 	query.append(" and cd.level='" + status + "' ");
	 	}
	if(id!=null && !id.equalsIgnoreCase(""))
	 	{
	 	query.append(" and nunit.floorname='" + id + "' ");
	 	}
	 	
	 	query.append(" group by nunit.nursing_unit,cd.level ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}*/
	

	// Method For Dashboard data grid

	public String beforeActionOnFeedback()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{

	setGridColomnNames();
	returnResult = "dashsuccess";

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	} else
	{
	returnResult = LOGIN;
	}
	return returnResult;
	} 
	
	
	
	public String setGridColomnNames()
	{
	try
	{
	   
	CommonOperInterface cbt = new CommonConFactory().createInterface();
 	viewPageColumns = new ArrayList<GridDataPropertyView>();
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	viewPageColumns.add(gpv);
	 
	  
	gpv = new GridDataPropertyView();
	gpv.setColomnName("encounter_id");
	gpv.setHeadingName("Ticket No");
	gpv.setFormatter("pharmaHistory");
	gpv.setHideOrShow("false");
	gpv.setWidth(90);
	viewPageColumns.add(gpv);
 	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("document_no");
	gpv.setHeadingName("Document No");
 	gpv.setHideOrShow("false");
	gpv.setWidth(90);
	viewPageColumns.add(gpv);
	
 	gpv = new GridDataPropertyView();
	gpv.setColomnName("priority");
	gpv.setHeadingName("Priority");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	gpv.setFormatter("pharmaPriorityColorCode");
	viewPageColumns.add(gpv);
	 
	gpv = new GridDataPropertyView();
	gpv.setColomnName("level");
	gpv.setHeadingName("Level");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);
	 
	gpv = new GridDataPropertyView();
	gpv.setColomnName("status");
	gpv.setHeadingName("Status");
	gpv.setHideOrShow("true");
	gpv.setWidth(100);
	viewPageColumns.add(gpv);
	
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("order");
	gpv.setHeadingName("Order At");
	gpv.setHideOrShow("false");
	gpv.setWidth(140);
	viewPageColumns.add(gpv);
	
	 	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("uhid");
	gpv.setHeadingName("UHID");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	viewPageColumns.add(gpv);
	
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_name");
	gpv.setHeadingName("Patient Name");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("assign_bed_num");
	gpv.setHeadingName("Bed No");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("nursing_unit");
	gpv.setHeadingName("Nursing Unit");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	viewPageColumns.add(gpv);
	
	 
	gpv = new GridDataPropertyView();
	gpv.setColomnName("Item");
	gpv.setHeadingName("Medicine");
	gpv.setHideOrShow("false");
	gpv.setFormatter("takeMedicineDetail");
	gpv.setWidth(90);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("admitting_pra_name");
	gpv.setHeadingName("Admitting Doctor");
	gpv.setHideOrShow("false");
	gpv.setWidth(140);
	viewPageColumns.add(gpv);
	
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("attending_pra_name");
	gpv.setHeadingName("Attending Doctor");
	gpv.setHideOrShow("false");
	gpv.setWidth(140);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("speciality");
	gpv.setHeadingName("Speciality");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	viewPageColumns.add(gpv);
	return SUCCESS;
	}
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String viewPharmacyDashboardBoard()
	{
 	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{

	try
	{
	 	CommonOperInterface cbt = new CommonConFactory().createInterface();
	 	List dataCount = cbt.executeAllSelectQuery(" select count(*) from pharma_patient_detail", connectionSpace);
	 	 	BigInteger count = new BigInteger("3");
	for (Iterator it = dataCount.iterator(); it.hasNext();)
	{
	Object obdata = (Object) it.next();
	count = (BigInteger) obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to < getRecords())
	to = getRecords(); 
	List<Object> tempList = new ArrayList<Object>();
	StringBuilder query = new StringBuilder();
	query.append("SELECT ord.id,ord.uhid as patientID,ord.patient_name,ord.assign_bed_num,ord.level,ord.status,ord.priority,ord.nursing_unit,ord.admitting_pra_name,ord.attending_pra_name,ord.speciality,ord.encounter_id,ord.escalation_date,ord.escalation_time,ord.order_date,ord.order_time, ord.document_no ");
	query.append(" from pharma_patient_detail as ord");
	if(location!=null  && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null") || (tableFor!=null && tableFor.equalsIgnoreCase("nursingUnitStatusBar") || tableFor.equalsIgnoreCase("locationStatusBar")))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursing_code ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	}	
	  	  	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ord.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and ord.order_time between '" + fromTime + "' and  '23:59:59')  or  (ord.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and ord.order_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ord.order_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and ord.order_time between '" + fromTime + "' and  '" + toTime + "')  or  (ord.order_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and ord.order_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  ord.order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	
	if (status!=null && !status.equalsIgnoreCase(""))
	{
	 
	if(status.equalsIgnoreCase("Auto Close") || status.equalsIgnoreCase("Force Close") || status.equalsIgnoreCase("Close") || status.equalsIgnoreCase("Dispatch Error") || status.equalsIgnoreCase("Dispatch-P") || status.equalsIgnoreCase("Dispatch") || status.equalsIgnoreCase("Ordered"))
	query.append(" and ord.status = '"+status+"' ");
	else if(status.equalsIgnoreCase("1") || status.equalsIgnoreCase("2") || status.equalsIgnoreCase("3") || status.equalsIgnoreCase("4") || status.equalsIgnoreCase("5"))
	query.append(" and ord.level= '"+status+"' ");
	else if(status.equalsIgnoreCase("Routine") || status.equalsIgnoreCase("Urgent"))
	query.append(" and ord.priority = '"+status+"' ");
	}
	if(tableFor!=null && id!=null && (tableFor.equalsIgnoreCase("locationStatusBar")))
	{
	query.append(" and fname.id='"+id+"' ");
	}
	if(tableFor!=null && (tableFor.equalsIgnoreCase("nursingUnitStatusBar")))
	{
	query.append(" and nunit.id='"+id+"' ");	
	}
	if(tableFor!=null && id!=null && (tableFor.equalsIgnoreCase("specialityBar")))
	{
	query.append(" and ord.speciality='"+id+"' ");
	}
	if(tableFor!=null && (tableFor.equalsIgnoreCase("doctorBar")))
	{
	query.append(" and ord.admitting_pra_name='"+id+"' ");	
	}
	if(location!=null && !location.equalsIgnoreCase("") && !location.equalsIgnoreCase("null")) 
 	{
 	query.append(" and fname.id In(" + location + " )");
 	}
 	if(nursingUnit!=null && !nursingUnit.equalsIgnoreCase("") && !nursingUnit.equalsIgnoreCase("null"))
 	{
 	query.append(" and ord.nursing_code In(" + nursingUnit + ") ");
 	}
	query.append(" ORDER BY  ord.order_date, ord.order_time desc  ");
//	System.out.println("Status: "+status+"  tableFor:  "+tableFor+"   id:  "+id);
	List dataList = null;
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	 	if (dataList != null && dataList.size() > 0)
	{
	 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
	Object[] object = (Object[]) iterator.next();
	 	 	
	tempMap.put("id", getValueWithNullCheck(object[0]));
	tempMap.put("uhid", getValueWithNullCheck(object[1]));
	tempMap.put("patient_name", getValueWithNullCheck(object[2]));
	tempMap.put("assign_bed_num", getValueWithNullCheck(object[3]));
	tempMap.put("level", "Level"+getValueWithNullCheck(object[4]));
	tempMap.put("status", getValueWithNullCheck(object[5]));
	tempMap.put("priority", getValueWithNullCheck(object[6]));
	tempMap.put("nursing_unit", getValueWithNullCheck(object[7]));
	   	tempMap.put("admitting_pra_name", getValueWithNullCheck(object[8]));
	  	tempMap.put("attending_pra_name", getValueWithNullCheck(object[9]));
	  	tempMap.put("speciality", getValueWithNullCheck(object[10]));
	  	tempMap.put("encounter_id", getValueWithNullCheck(object[11]));
	  	tempMap.put("document_no", getValueWithNullCheck(object[16]));
	  	if (object[14]!=null && object[15]!=null) 
	  	tempMap.put("order", DateUtil.convertDateToIndianFormat(object[14].toString())+"    "+getValueWithNullCheck(object[15]));
	  	else
	  	tempMap.put("order","NA");
	 	tempList.add(tempMap);
	 	  	
	}
	
	}
	 
	setMasterViewProp(tempList);
	 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	 	returnResult = SUCCESS;
	 
	}
	catch (Exception e)
	{
	returnResult = ERROR;
	e.printStackTrace();
	}
	}
	else
	{
	returnResult = LOGIN;
	}
	return returnResult;
	}
	
	 
	public String fetchFilterNurshingUnit()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	nursingMap = new LinkedHashMap<String, String>();
	if (sessionFlag)
	{
	try
	{
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") || toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	 	}
	StringBuilder qry = new StringBuilder();
	qry.append("select distinct nursing_code, nursing_unit from pharma_patient_detail ");
	if (fromDate != null && toDate != null)
	{
	qry.append(" where order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	}
	qry.append(" order by nursing_unit asc ");
	List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	if (data != null && data.size() > 0)
	{
	for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && object[1] != null)
	{
	nursingMap.put("'" + object[0].toString() + "'", object[1].toString());
	}
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
	return LOGIN;
	}
	}
	public String fetchFilterLocation()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	locationMap = new LinkedHashMap<String, String>();
	if (sessionFlag)
	{
	try
	{
	if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") || toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
	fromDate = DateUtil.getCurrentDateIndianFormat();
	toDate = DateUtil.getCurrentDateIndianFormat();
	 	}
	StringBuilder qry = new StringBuilder();
	qry.append(" select distinct fname.id, fname.floorname from pharma_patient_detail as ord ");
	qry.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_code=ord.nursing_code ");
	qry.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	if (fromDate != null && toDate != null)
	{
	qry.append(" where order_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	}
	qry.append(" order by fname.floorname asc ");
	List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	if (data != null && data.size() > 0)
	{
	for (Iterator<?> iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && object[1] != null)
	{
	locationMap.put("'" + object[0].toString() + "'", object[1].toString());
	}
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
	return LOGIN;
	}
	}
	
	
	public String getValueWithNullCheck(Object value)
	{
	return ((value==null || value.toString().equals("") ) ?  "NA" : value.toString());
	}

	public String getFromDate()
	{
	return fromDate;
	}

	public void setFromDate(String fromDate)
	{
	this.fromDate = fromDate;
	}

	public String getToDate()
	{
	return toDate;
	}

	public void setToDate(String toDate)
	{
	this.toDate = toDate;
	}
 

	 

	public DashboardPojo getDashObj()
	{
	return dashObj;
	}
 
	
	public void setDashObj(DashboardPojo dashObj)
	{
	this.dashObj = dashObj;
	}

	  
	public String getLoginType()
	{
	return loginType;
	}

	public void setLoginType(String loginType)
	{
	this.loginType = loginType;
	}

	public boolean isHodFlag()
	{
	return hodFlag;
	}

	public void setHodFlag(boolean hodFlag)
	{
	this.hodFlag = hodFlag;
	}

	public boolean isMgmtFlag()
	{
	return mgmtFlag;
	}

	public void setMgmtFlag(boolean mgmtFlag)
	{
	this.mgmtFlag = mgmtFlag;
	}

	public boolean isNormalFlag()
	{
	return normalFlag;
	}

	public void setNormalFlag(boolean normalFlag)
	{
	this.normalFlag = normalFlag;
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
 
 
 
	public JSONArray getJsonArr()
	{
	return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
	this.jsonArr = jsonArr;
	}
 
	 
	public List<DashboardPojo> getData_list()
	{
	return data_list;
	}

	public void setData_list(List<DashboardPojo> dataList)
	{
	data_list = dataList;
	}

	public String getId()
	{
	return id;
	}

	public void setId(String id)
	{
	this.id = id;
	}

	public String getName()
	{
	return name;
	}

	public void setName(String name)
	{
	this.name = name;
	}

	public String getCorporateName()
	{
	return corporateName;
	}

	public void setCorporateName(String corporateName)
	{
	this.corporateName = corporateName;
	}

	public String getStatus()
	{
	return status;
	}

	public void setStatus(String status)
	{
	this.status = status;
	}

	public String getLocationName()
	{
	return locationName;
	}

	public void setLocationName(String locationName)
	{
	this.locationName = locationName;
	}

	public String getServiceName()
	{
	return serviceName;
	}

	public void setServiceName(String serviceName)
	{
	this.serviceName = serviceName;
	}

	public String getTableFor()
	{
	return tableFor;
	}

	public void setTableFor(String tableFor)
	{
	this.tableFor = tableFor;
	}



	public String getStatusFor() {
	return statusFor;
	}



	public void setStatusFor(String statusFor) {
	this.statusFor = statusFor;
	}



	public String getFromTime() {
	return fromTime;
	}



	public void setFromTime(String fromTime) {
	this.fromTime = fromTime;
	}



	public String getToTime() {
	return toTime;
	}



	public void setToTime(String toTime) {
	this.toTime = toTime;
	}


 


	public String getProductivityLimit() {
	return productivityLimit;
	}



	public void setProductivityLimit(String productivityLimit) {
	this.productivityLimit = productivityLimit;
	}



	public String getProductivityFor() {
	return productivityFor;
	}



	public void setProductivityFor(String productivityFor) {
	this.productivityFor = productivityFor;
	}



	public List<GridDataPropertyView> getViewPageColumns() {
	return viewPageColumns;
	}



	public void setViewPageColumns(List<GridDataPropertyView> viewPageColumns) {
	this.viewPageColumns = viewPageColumns;
	}



	public List<Object> getMasterViewProp() {
	return masterViewProp;
	}



	public void setMasterViewProp(List<Object> masterViewProp) {
	this.masterViewProp = masterViewProp;
	}



	public String getLocation()
	{
	return location;
	}



	public void setLocation(String location)
	{
	this.location = location;
	}



	public String getNursingUnit()
	{
	return nursingUnit;
	}



	public Map<String, String> getLocationMap()
	{
	return locationMap;
	}



	public void setLocationMap(Map<String, String> locationMap)
	{
	this.locationMap = locationMap;
	}



	public Map<String, String> getNursingMap()
	{
	return nursingMap;
	}



	public void setNursingMap(Map<String, String> nursingMap)
	{
	this.nursingMap = nursingMap;
	}



	public String getPriority()
	{
	return priority;
	}



	public void setPriority(String priority)
	{
	this.priority = priority;
	}



	public void setNursingUnit(String nursingUnit)
	{
	this.nursingUnit = nursingUnit;
	}
 
}