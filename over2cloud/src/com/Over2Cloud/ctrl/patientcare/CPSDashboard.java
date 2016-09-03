package com.Over2Cloud.ctrl.patientcare;
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
 
 
 

@SuppressWarnings("serial")
public class CPSDashboard extends GridPropertyBean
{

	
	private String fromDate = null;
	private String toDate = null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	List<DashboardPojo> data_list = null;
	private JSONArray jsonArr = null;
	private String id = null;
	private String name = null;
	private String tableFor=null;
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
	private String accountManagerName=null;
	List  corporateList = null;
	 	 
 	private String loginType;
	private boolean hodFlag;
	private boolean mgmtFlag;
	private boolean normalFlag;
  	
	
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
	corporateList=fetchCorporateList();
	  	if(tableFor!=null)
	getCounterData();
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
	public String getCounters()
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
	 	jsonObject.put(tableFor, pojo.getName());
	 	 	jsonObject.put("Id", pojo.getId());
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
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try {
	data_list = new ArrayList<DashboardPojo>();
	int total = 0,onTime=0,offTime=0,cancel=0,scheduled=0,appointment=0,serviceIn=0,serviceOut=0;
	 	String flagName="";
	 	Object[] object=null;
	 	List dataList = null;
	 	//System.out.println("tableFor>>>>>> "+tableFor);
	 	  if(tableFor!=null && tableFor.equalsIgnoreCase("corporateServiceProductivity") || tableFor.equalsIgnoreCase("corporateService") || tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("serviceDOC") || tableFor.equalsIgnoreCase("serviceDOCProductivity"))
	  	dataList = getAllServiceList();
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("corporateLocationProductivity") || tableFor.equalsIgnoreCase("corporateLocation") || tableFor.equalsIgnoreCase("locationForSpecialityProductivity") || tableFor.equalsIgnoreCase("locationForSpeciality") || tableFor.equalsIgnoreCase("locationForService") || tableFor.equalsIgnoreCase("locationForServiceProductivity"))
	  	dataList = getLocationNameListForService();
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity"))
	  	dataList = getServiceManagerNameListForLocation();
	 	  
	 	  
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("location")|| tableFor.equalsIgnoreCase("locationProductivity"))
	  	dataList = getAllLocationList();
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("serviceForLocation")|| tableFor.equalsIgnoreCase("locationForSpeciality") || tableFor.equalsIgnoreCase("serviceForLocationProductivity"))
	  	dataList = getServiceListForLocation();
	 	
	 	  
	 	  
	 	  
	  	else if(tableFor!=null && tableFor.equalsIgnoreCase("accountManager") ||  tableFor.equalsIgnoreCase("accountManagerProductivity"))
	 	dataList = getAccountManagerList();
	 	else if(tableFor!=null &&  tableFor.equalsIgnoreCase("corporate") || tableFor.equalsIgnoreCase("corporateProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity"))
	 	dataList = getCorporateNameList();
	   	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity"))
	 	dataList = getServiceNameList();
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity"))
	 	dataList = getLocationNameList();
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity"))
	 	dataList = getServiceManagerNameList();
	 	
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("serviceManager") || tableFor.equalsIgnoreCase("serviceManagerProductivity"))
	 	dataList = getAllServiceManagerNameList();
	 	  
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("corporateServiceSpecialityProductivity") || tableFor.equalsIgnoreCase("corporateServiceSpeciality") ||  tableFor.equalsIgnoreCase("locationForSpecialitys") || tableFor.equalsIgnoreCase("specialitysProductivity"))
	 	dataList = getAllSpecialityNameList();
	 	 
	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("corporateSpecialityDoctorProductivity") ||  tableFor.equalsIgnoreCase("corporateServiceDoctor") || tableFor.equalsIgnoreCase("corporateServiceSpeciality") || tableFor.equalsIgnoreCase("pecialitysToDoc") || tableFor.equalsIgnoreCase("specialitysToDocProductivity"))
	 	dataList = getAllSpecialityNameListToDOC();
	  	 
	  	
 
	 
	  	if (dataList != null && dataList.size() > 0) {
	 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	 	object = (Object[]) iterator.next();
	if (flagName!=null && !flagName.equalsIgnoreCase(object[1].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	cancel=0;
	serviceIn=0;
	serviceOut=0;
	appointment=0;
	scheduled=0;
	onTime=0;
	offTime=0;
	  	}
	 	DP.setId(object[0].toString());
	 	DP.setName(object[1].toString());
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")
	 	||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")
	 	||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") 
	 	|| tableFor.equalsIgnoreCase("corporate") || tableFor.equalsIgnoreCase("corporateService") || tableFor.equalsIgnoreCase("corporateLocation") || tableFor.equalsIgnoreCase("corporateServiceSpeciality") || tableFor.equalsIgnoreCase("corporateServiceSpeciality") || tableFor.equalsIgnoreCase("corporateServiceDoctor")
	 	|| tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") 
	 	|| tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") 
	 	|| tableFor.equalsIgnoreCase("serviceManager") || tableFor.equalsIgnoreCase("serviceDOC") 
	 	|| tableFor.equalsIgnoreCase("locationForSpeciality") || tableFor.equalsIgnoreCase("locationForSpecialitys") 
	 	|| tableFor.equalsIgnoreCase("pecialitysToDoc") ))
	 	{
	 	 
	 	if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Appointment") || object[2].toString().equalsIgnoreCase("Appointment Cancel") || object[2].toString().equalsIgnoreCase("Appointment Parked") )) 
	 	{
	 	 	appointment=appointment+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	else if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Scheduled"))) 
	 	{
	 	scheduled=scheduled+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	else if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Service Out"))) 
	 	{
	 	serviceOut=serviceOut+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	else if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Service In") || object[2].toString().equalsIgnoreCase("Service In Parked"))) 
	 	{
	 	serviceIn=serviceIn+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	else if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Cancel") )) 
	 	{
	 	 	cancel=cancel+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	  
	 	}
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")
	 	||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")
	 	||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") 
	 	|| tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") 
	 	|| tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") 
	 	|| tableFor.equalsIgnoreCase("serviceManagerProductivity")
	 	|| tableFor.equalsIgnoreCase("serviceDOCProductivity") || tableFor.equalsIgnoreCase("locationForSpecialityProductivity") || tableFor.equalsIgnoreCase("specialitysProductivity") || tableFor.equalsIgnoreCase("specialitysToDocProductivity") 
	 	|| tableFor.equalsIgnoreCase("corporateProductivity")  || tableFor.equalsIgnoreCase("corporateServiceProductivity") || tableFor.equalsIgnoreCase("corporateLocationProductivity") || tableFor.equalsIgnoreCase("corporateServiceSpecialityProductivity") || tableFor.equalsIgnoreCase("corporateSpecialityDoctorProductivity") ))
	 	{
	 	 
	 	 
	 	if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("1"))) 
	 	{
	 	 	onTime=onTime+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	else if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("2") || object[2].toString().equalsIgnoreCase("3") || object[2].toString().equalsIgnoreCase("4") || object[2].toString().equalsIgnoreCase("5") || object[2].toString().equalsIgnoreCase("6"))) 
	 	{
	 	offTime=offTime+Integer.parseInt(object[3].toString());
	 	total=total+Integer.parseInt(object[3].toString());
	 	 
	 	}
	 	}
	 	 
	  	 
	  	DP.setAppointment(String.valueOf(appointment));
	  	DP.setServiceIn(String.valueOf(serviceIn));
	  	DP.setServiceOut(String.valueOf(serviceOut));
	  	DP.setCancel(String.valueOf(cancel));
	  	DP.setScheduled(String.valueOf(scheduled));
	  	DP.setCancel(String.valueOf(cancel));
	  	DP.setOnTime(String.valueOf(onTime));
	  	DP.setOffTime(String.valueOf(offTime));
	  	DP.setCounter(String.valueOf(total));
	  	//System.out.println("Total "+total+" FlagName "+object[1].toString()+"");
	  	  	if (total != 0 && flagName!=null && !flagName.equalsIgnoreCase(object[1].toString()))
	 	data_list.add(DP);
	 	dashObj.setDashList(data_list);
	 	//System.out.println("dashObj "+dashObj.toString());
	 	flagName = object[1].toString();
	 	  
	 	}
	  
	}
	 
	} catch (Exception e) {
	e.printStackTrace();
	}
	}

	
	
	@SuppressWarnings("rawtypes")
	public List fetchCorporateList( )
	{
	List dataList = new ArrayList();
	StringBuffer query = new StringBuffer();

	try {
 	query.append("select CUSTOMER_NAME as Ids,CUSTOMER_NAME as Name from dreamsol_bl_corp_hc_pkg group by CUSTOMER_NAME ");
 	dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return dataList;
	}
	
	 
	
	@SuppressWarnings("rawtypes")
	public List getAllServiceList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	if(tableFor!=null && (tableFor.equalsIgnoreCase("corporateService") || tableFor.equalsIgnoreCase("serviceDOC") || tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("corporateServiceProductivity") || tableFor.equalsIgnoreCase("serviceDOCProductivity") || tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
 	  	 field1="cpd.level";
	query.append(" select cs.id,cs.service_name,"+field1+",count(*) from  cps_service  as cs ");
	query.append(" Inner join corporate_patience_data as cpd on  cpd.services=cs.id ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(tableFor.equalsIgnoreCase("corporateService"))
 	 	{
 	 	query.append(" and cpd.corp_name='"+serviceName+"'");
 	 	}
 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
	  	query.append(" group by cs.service_name,"+field1);
	  	//System.out.println("vvvv "+query);
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	 	
	 
	
	@SuppressWarnings("rawtypes")
	public List getLocationNameListForService()
	{
	//System.out.println("tableFor>>>>>>2 "+tableFor);
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("corporateLocation") ||  tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForSpeciality") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("corporateLocationProductivity") || tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForSpecialityProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.med_location as id,cpd.med_location as locationName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(serviceName!=null)
 	 	if(tableFor.equalsIgnoreCase("corporateLocation"))
 	 	{
 	 	query.append(" and cpd.corp_name='" + corporateName + "' and cpd.services='"+serviceName+"'");
 	 	}
 	 	else
 	 	{
 	 	query.append(" and cpd.services='" + serviceName + "' ");
 	 	}
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	} 
 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
 	 query.append(" group by cpd.med_location,"+field1);
 	//System.out.println("query "+query);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public List getServiceManagerNameListForLocation()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.service_manager,eb.empName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	query.append(" Inner join employee_basic as eb on  eb.id=cpd.service_manager ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(locationName!=null)
	query.append(" and cpd.med_location='" + locationName + "' ");
 	 	if(serviceName!=null)
	query.append(" and cpd.services='" + serviceName + "' ");
 	 	if(tableFor.equalsIgnoreCase("corporateServiceManager"))
 	 	{
 	 	query.append(" and cpd.corp_name='" + corporateName + "' ");
 	 	}
 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}

  	  	query.append(" group by cpd.service_manager,"+field1);
  	  	//System.out.println(" VVV "+query);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getAllLocationList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.med_location as id,cpd.med_location as locationName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	  	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}

 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
 	 query.append(" group by cpd.med_location,"+field1);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getServiceListForLocation()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForSpeciality") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cs.id,cs.service_name,"+field1+",count(*) from  cps_service  as cs ");
	query.append(" Inner join corporate_patience_data as cpd on  cpd.services=cs.id ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(locationName!=null)
	query.append(" and cpd.med_location='" + locationName + "' ");
 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
	  	query.append(" group by cs.service_name,"+field1);
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountManagerList()
	{
	List data=null;
	try {
	 
	StringBuffer query = new StringBuffer();
	String field1="cpd.status";
	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.ac_manager as ids,cpd.ac_manager as name,"+field1+",count(*) from corporate_patience_data as cpd ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')";
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	  	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
	  	query.append(" group by cpd.ac_manager,"+field1);
 	  //System.out.println(">>>>>>    "+query.toString());
 	 	  data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getCorporateNameList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("corporate") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("corporateProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	 	if(tableFor.equalsIgnoreCase("corporate") || tableFor.equalsIgnoreCase("corporateProductivity"))
	 	{
	 	query.append(" select cpd.corp_name,cch.CUSTOMER_NAME as Names,"+field1+",count(*) from  dreamsol_bl_corp_hc_pkg  as cch ");
	 	}
	 	else
	 	{
	 	query.append(" select cch.CUSTOMER_NAME as ids,cch.CUSTOMER_NAME as Names,"+field1+",count(*) from  dreamsol_bl_corp_hc_pkg  as cch ");
	 	}
	query.append(" Inner join corporate_patience_data as cpd on  cpd.corp_name=cch.id  ");
 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(accountManagerName!=null)
 	 	query.append(" and cpd.ac_manager='" + accountManagerName + "' ");
	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(accountManagerName!=null)
	query.append(" and cpd.ac_manager='" + accountManagerName + "' ");
 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
	  	query.append(" group by cch.CUSTOMER_NAME,"+field1+" order by count(*) desc limit 20");
	  	//System.out.println("query "+query);
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	 	
	@SuppressWarnings("rawtypes")
	public List getServiceNameList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cs.id,cs.service_name,"+field1+",count(*) from  cps_service  as cs ");
	query.append(" Inner join corporate_patience_data as cpd on  cpd.services=cs.id ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(accountManagerName!=null)
	query.append(" and cpd.ac_manager='" + accountManagerName + "' ");
 	 	if(corporateName!=null)
 	 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + corporateName + "' ) ");
 	 	
 	   	query.append(" group by cs.service_name,"+field1);
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getLocationNameList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.med_location as id,cpd.med_location as locationName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(serviceName!=null)
	query.append(" and cpd.services='" + serviceName + "' ");
 	 	if(accountManagerName!=null)
	query.append(" and cpd.ac_manager='" + accountManagerName + "' ");
 	 	if(corporateName!=null)
 	 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + corporateName + "' ) ");
 	  	query.append(" group by cpd.med_location,"+field1);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getServiceManagerNameList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.service_manager,eb.empName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	query.append(" Inner join employee_basic as eb on  eb.id=cpd.service_manager ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(locationName!=null)
	query.append(" and cpd.med_location='" + locationName + "' ");
 	 	if(serviceName!=null)
	query.append(" and cpd.services='" + serviceName + "' ");
 	 	if(accountManagerName!=null)
	query.append(" and cpd.ac_manager='" + accountManagerName + "' ");
 	 	if(corporateName!=null)
 	 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + corporateName + "' ) ");
 	
	  	query.append(" group by cpd.service_manager,"+field1);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}
	
	 
	@SuppressWarnings("rawtypes")
	public List getAllServiceManagerNameList()
	{
	List data=null;
	try {
	 	StringBuffer query = new StringBuffer();
	 	String field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("service") || tableFor.equalsIgnoreCase("locationForService")||tableFor.equalsIgnoreCase("serviceManagerForService") || tableFor.equalsIgnoreCase("location")||tableFor.equalsIgnoreCase("serviceForLocation") || tableFor.equalsIgnoreCase("accountManager") || tableFor.equalsIgnoreCase("corporateForAcntMng") || tableFor.equalsIgnoreCase("serviceForAcntMng") || tableFor.equalsIgnoreCase("locationForAcntMng") || tableFor.equalsIgnoreCase("serviceManagerForAcntMng") || tableFor.equalsIgnoreCase("serviceManager")))
	 	 field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceProductivity") || tableFor.equalsIgnoreCase("locationForServiceProductivity")||tableFor.equalsIgnoreCase("serviceManagerForServiceProductivity") || tableFor.equalsIgnoreCase("locationProductivity")||tableFor.equalsIgnoreCase("serviceForLocationProductivity") || tableFor.equalsIgnoreCase("accountManagerProductivity") || tableFor.equalsIgnoreCase("corporateForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceForAcntMngProductivity") || tableFor.equalsIgnoreCase("locationForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerForAcntMngProductivity") || tableFor.equalsIgnoreCase("serviceManagerProductivity")))
	  	 field1="cpd.level";
	query.append(" select cpd.service_manager,eb.empName,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	query.append(" Inner join employee_basic as eb on  eb.id=cpd.service_manager ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
	
 	   	query.append(" group by cpd.service_manager,"+field1);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}

	 //getAllSpecialityNameList	
	
	@SuppressWarnings("rawtypes")
	public List getAllSpecialityNameList()
	{
 
	List data=null;
	String serviceNameCheck=null;
	String serField=null;
	StringBuffer query = new StringBuffer();
	 	String field1 = null;
	try {
	
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("serviceDOC") || tableFor.equalsIgnoreCase("locationForSpecialitys") || tableFor.equalsIgnoreCase("corporateServiceSpeciality")))
	 	field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("specialitysProductivity") || tableFor.equalsIgnoreCase("corporateServiceSpecialityProductivity") ))
	  	 field1="cpd.level";
	 	serviceNameCheck=new CPSHelper().fetcServiceName(serviceName, connectionSpace);
	if(serviceNameCheck.equalsIgnoreCase("DayCare"))
	{
	serField="cpds.daycare_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("OPD"))
	{
	serField="cpds.opd_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("IPD"))
	{
	serField="cpds.ipd_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("EHC"))
	{
	serField="cpds.ehc_pack_type";
	}
	else if(serviceNameCheck.equalsIgnoreCase("Emergency"))
	{
	serField="cpds.em_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("Radiology"))
	{
	serField="cpds.radio_mod";
	}
	else if(serviceNameCheck.equalsIgnoreCase("Laboratory"))
	{
	serField="cpds.lab_mod";
	}
	else if(serviceNameCheck.equalsIgnoreCase("Diagnostics"))
	{
	serField="cpds.diagnostics_test";
	}
	else if(serviceNameCheck.equalsIgnoreCase("New Information Request"))
	{
	serField="cpd.remarks";
	}
	if(serviceNameCheck.equalsIgnoreCase("New Information Request"))
	{
	//System.out.println("serviceNameCheck "+serviceNameCheck);
	query.append(" select "+serField+" as id,"+serField+" as name,"+field1+",count(*) from  corporate_patience_data  as cpd ");
	}
	if(serviceNameCheck.equalsIgnoreCase("EHC") || serviceNameCheck.equalsIgnoreCase("Emergency") || serviceNameCheck.equalsIgnoreCase("Radiology") || serviceNameCheck.equalsIgnoreCase("Laboratory") || serviceNameCheck.equalsIgnoreCase("Diagnostics") || serviceNameCheck.equalsIgnoreCase("IPD") || serviceNameCheck.equalsIgnoreCase("OPD") || serviceNameCheck.equalsIgnoreCase("DayCare"))
	{
	//System.out.println("serviceNameCheck1 "+serviceNameCheck);
	query.append(" select "+serField+" as id,"+serField+" as name,"+field1+",count(*) from  cps_services_details  as cpds ");
	query.append(" Inner join corporate_patience_data as cpd on  cpd.id=cpds.cpsid ");
	}
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

	 	query.append(" and cpd.services='" + serviceName + "' and cpd.med_location='"+locationName+"' ");
	 	if(corporateName!=null)
	 	{
	 	query.append(" and cpd.corp_name='" + corporateName+"'");
	 	}
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	query.append(" group by "+serField+","+field1);
 	 	//System.out.println(" query >>>> "+query);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}

	//Doctror
	@SuppressWarnings("rawtypes")
	public List getAllSpecialityNameListToDOC()
	{
	List data=null;
	String serviceNameCheck=null;
	String serField=null,serField1=null,field1=null;
	StringBuffer query = new StringBuffer();
	try {
	
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("corporateServiceDoctor") || tableFor.equalsIgnoreCase("corporateServiceSpeciality") || tableFor.equalsIgnoreCase("pecialitysToDoc") || tableFor.equalsIgnoreCase("serviceDOC")))
	 	field1="cpd.status";
	 	if(tableFor!=null && (tableFor.equalsIgnoreCase("specialitysToDocProductivity") || tableFor.equalsIgnoreCase("corporateSpecialityDoctorProductivity")))
	  	 field1="cpd.level";
	 	serviceNameCheck=new CPSHelper().fetcServiceName(serviceName, connectionSpace);
	 	if(serviceNameCheck.equalsIgnoreCase("DayCare"))
	{
	 	serField="cpds.daycare_doc";
	serField1="cpds.daycare_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("OPD"))
	{
	serField="cpds.opd_doc";
	serField1="cpds.opd_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("IPD"))
	{
	serField="cpds.ipd_doc";
	serField1="cpds.ipd_spec";
	}
	else if(serviceNameCheck.equalsIgnoreCase("EHC"))
	{
	serField="cpds.ehc_pack";
	serField1="cpds.ehc_pack_type";
	}
	else if(serviceNameCheck.equalsIgnoreCase("Emergency"))
	{
	serField="cpds.em_spec_assis";
	serField1="cpds.em_spec";
	}
	query.append(" select "+serField+" as id,"+serField+" as name,"+field1+",count(*) from  cps_services_details  as cpds ");
	query.append(" Inner join corporate_patience_data as cpd on  cpd.id=cpds.cpsid ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '23:59:59')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cpd.date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')  or  (cpd.date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cpd.time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cpd.date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	 	
	 	query.append(" and cpd.services='" + serviceName + "' and "+serField1+"='"+locationName+"' ");
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("-1"))
 	query.append(" and cpd.corp_name In(Select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='" + statusFor + "' ) ");
 	 	if(status!=null && !status.equalsIgnoreCase("All"))
 	 	{
 	 	String statusList=null;
 	 	if(status.equalsIgnoreCase("Appointment"))
 	 	statusList="('Appointment','Appointment Parked','Appointment Cancel')"; 
 	 	else if(status.equalsIgnoreCase("Scheduled"))
 	 	statusList="('Scheduled')";
 	 	else if(status.equalsIgnoreCase("Service In"))
 	 	statusList="('Service In','Service In Parked')";
 	 	else if(status.equalsIgnoreCase("Service Out"))
 	 	statusList="('Service Out')";
 	 	else if(status.equalsIgnoreCase("Cancel"))
 	 	statusList="('Cancel')";
 	 	if(!status.equalsIgnoreCase("On Time") && !status.equalsIgnoreCase("Off Time"))
 	 	query.append(" and cpd.status In " + statusList + " ");
 	 	else if(status.equalsIgnoreCase("On Time"))
 	 	query.append(" and cpd.level In ('1') ");
 	 	else if(status.equalsIgnoreCase("Off Time"))
	 	query.append(" and cpd.level In ('2','3','4','5','6') ");
 	 	}
 	 	query.append(" group by "+serField+","+field1);
 	 	//System.out.println(" query >>>> "+query);
	   	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  	}
 	 catch (Exception e) {
	e.printStackTrace();
	 	}
	return data;
	}

	
	
	//View Data in activity board
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewCriticalData1()
	{
	 
	if (ValidateSession.checkSession())
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List dataCount = cbt.executeAllSelectQuery(" select count(*) from critical_data ", connectionSpace);
	// ////System.out.println("dataCount:"+dataCount);
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

	StringBuilder query = new StringBuilder();
	query.append("SELECT cd.id,cd.ticket_no,cd.status,cd.open_date,"
	+ "cd.open_time,cd.escalate_date,cd.escalate_time,cd.level,"
	+ "cd.dept,cd.uhid,cd.test_type,cpd.patient_name,cpd.patient_mob,cpd.patient_status,cpd.addmision_doc_name,cpd.nursing_unit,cpd.specialty,cpd.addmision_doc_mobile,cd.doc_name,cd.doc_mobile,cd.caller_emp_id,cd.caller_emp_name,cd.caller_emp_mobile,cpd.id as pid,cpd.bed_no  FROM critical_data AS cd ");

	query.append(" Inner JOIN critical_patient_data AS cpd ON cd.patient_id=cpd.id");
	query.append(" Inner JOIN critical_patient_report AS cpr ON cpr.patient_id=cpd.id ");
	if(tableFor!=null && (tableFor.equalsIgnoreCase("location") || tableFor.equalsIgnoreCase("locationProductivity") || tableFor.equalsIgnoreCase("nursingUnit") || tableFor.equalsIgnoreCase("nursingUnitProductivity") ))
	{
	query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cpd.nursing_unit ");
	query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
	
	}
	  	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	 	  
	
	if(productivityLimit!=null)
	    {
	if(status!=null && status.equalsIgnoreCase("On Time"))
	{
	if(productivityFor!=null && productivityFor.equalsIgnoreCase("OCC"))
	query.append(" and cd.tat_not_to_inform <='" + productivityLimit + "' ");
	else if(productivityFor!=null && productivityFor.equalsIgnoreCase("Department"))
	query.append(" and cd.tat_inform_to_close <='" + productivityLimit + "' ");
	}
	if(status!=null && status.equalsIgnoreCase("Off Time"))
	{
	if(productivityFor!=null && productivityFor.equalsIgnoreCase("OCC"))
	query.append(" and cd.tat_not_to_inform >'" + productivityLimit + "' ");
	else if(productivityFor!=null && productivityFor.equalsIgnoreCase("Department"))
	query.append(" and cd.tat_inform_to_close >'" + productivityLimit + "' ");
	}
	}
	  	
	  	
	  	
	if(status!=null && status.equalsIgnoreCase("All"))
	query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
	query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
	query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
	 	else if(status!=null && status.equalsIgnoreCase("Informed"))
	query.append(" and cd.status In ('Informed') ");
	  	
	  	
	  
	
	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 	query.append(" and cpd.patient_status='" + statusFor + "' ");
	
	  	
	
	  	
	if(tableFor!=null && (tableFor.equalsIgnoreCase("specialty") || tableFor.equalsIgnoreCase("specialtyProductivity") ))
	   	query.append(" and cpd.specialty = '" + id + "' ");
	   	else if(tableFor!=null && (tableFor.equalsIgnoreCase("testType")|| tableFor.equalsIgnoreCase("testTypeProductivity") || tableFor.equalsIgnoreCase("crcReport")) )
	   	query.append(" and cpr.test_type = '" + id + "' ");
	   	else if(tableFor!=null && (tableFor.equalsIgnoreCase("location") || tableFor.equalsIgnoreCase("locationProductivity") ))
	   	query.append(" and  fname.id='" + id + "' ");
	
	   	else if(tableFor!=null && (tableFor.equalsIgnoreCase("doctor") || tableFor.equalsIgnoreCase("doctorProductivity") ))
	   	query.append(" and cpd.addmision_doc_name = '" + id + "' ");
	   	else if(tableFor!=null && (tableFor.equalsIgnoreCase("testName")|| tableFor.equalsIgnoreCase("testNameProductivity") ) )
	   	query.append(" and cpr.test_name = '" + id + "' ");
	   	else if(tableFor!=null && (tableFor.equalsIgnoreCase("nursingUnit") || tableFor.equalsIgnoreCase("nursingUnitProductivity") ))
	   	query.append(" and  nunit.nursing_unit='" + id + "' ");
	
	
	
	query.append(" ORDER BY cd.id DESC ");
	
	 
	List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (list != null && list.size() > 0)
	{
	List<Object> tempList = new ArrayList<Object>();
	for (Iterator iterator = list.iterator(); iterator.hasNext();)
	{
	Object object[] = (Object[]) iterator.next();
	Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
	tempMap.put("id", getValueWithNullCheck(object[0]));
	tempMap.put("ticket_no", getValueWithNullCheck(object[1]));
	if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
	{
	tempMap.put("status", "OCC Park");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I"))
	{
	tempMap.put("status", "Dept Park");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore"))
	{
	tempMap.put("status", "OCC Ignore");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore-I"))
	{
	tempMap.put("status", "Dept Ignore");
	} else
	{
	tempMap.put("status", getValueWithNullCheck(object[2]));
	}
	tempMap.put("open_date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[3].toString())) + ", " + getValueWithNullCheck(object[4]).substring(0, 5));
	// tempMap.put("escalate_date",
	// getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[5].toString()))
	// + ", " + getValueWithNullCheck(object[6]));
	if (object[2]!=null && object[13]!=null && getValueWithNullCheck(object[2]).equalsIgnoreCase("Informed") || getValueWithNullCheck(object[13]).equalsIgnoreCase("IPD") ||  getValueWithNullCheck(object[13]).equalsIgnoreCase("OPD"))
	{
	tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[5].toString(), object[6].toString()));
	} 
	 
	else
	{
	tempMap.put("escalate_date", "NA");
	}
	tempMap.put("level", getValueWithNullCheck(object[7]));
	tempMap.put("dept", getValueWithNullCheck(object[8]));
	tempMap.put("uhid", getValueWithNullCheck(object[9]));
	tempMap.put("test_type", getValueWithNullCheck(object[10]));
	tempMap.put("patient_name", getValueWithNullCheck(object[11]));
	tempMap.put("patient_mob", getValueWithNullCheck(object[12]));
	tempMap.put("patient_status", getValueWithNullCheck(object[13]));
	tempMap.put("adm_doc", getValueWithNullCheck(object[14]));
	tempMap.put("nursing_unit", getValueWithNullCheck(object[15]));
	tempMap.put("spec", getValueWithNullCheck(object[16]));
	tempMap.put("adm_doc_mob", getValueWithNullCheck(object[17]));
	tempMap.put("lab_doc", getValueWithNullCheck(object[18]));
	tempMap.put("lab_doc_mob", getValueWithNullCheck(object[19]));
	tempMap.put("caller_emp_id", getValueWithNullCheck(object[20]));
	tempMap.put("caller_emp_name", getValueWithNullCheck(object[21]));
	tempMap.put("caller_emp_mobile", getValueWithNullCheck(object[22]));
	tempMap.put("patient_id", getValueWithNullCheck(object[23]));
	tempMap.put("bed_no", getValueWithNullCheck(object[24]));

	tempList.add(tempMap);
	}
	setMasterViewProp(tempList);
	 
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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



	public List getCorporateList() {
	return corporateList;
	}



	public void setCorporateList(List corporateList) {
	this.corporateList = corporateList;
	}



	public String getAccountManagerName() {
	return accountManagerName;
	}



	public void setAccountManagerName(String accountManagerName) {
	this.accountManagerName = accountManagerName;
	}
 
}