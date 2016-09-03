package com.Over2Cloud.ctrl.patientcare;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
//import com.aspose.p6a2feef8;
@SuppressWarnings("serial")
public class PatientDetailsModify extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	
    private Map<String, String> acManager;
    private Map<String, String> serviceMap;
    private Map<String,String> stateMap=null;
    private Map<String,String> serviceManager=null;
    private String id;
    private String manager,managerId,corp_name,corp_nameId,patient_type,patient_name,pgenders,pat_mobile,pat_email,pat_dob,countryid,country_name,stateid,state_name,cityid,city_name,services_Id,services_name,location_id,location_name,preferred_time,acc_mob,empName,emp_id;
    private String remarks;
    private String uhid,stateId;
    private JSONArray jsonArray;
    private Map<String, String> dataMap=new LinkedHashMap<String, String>();
    List<Object> listdata = new ArrayList<Object>();
    private String today;
    private String tomorrow;
    private String fetchDate;
    private String DOB;
    public String fetchs()
	{   CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> acManager = new LinkedHashMap<String,String>();
		List data = cbt.executeAllSelectQuery("select cpd.pat_state,cun.state_name,count(*) FROM corporate_patience_data as cpd inner join state as cun on cun.state_name=cpd.pat_state where cun.city_code='"+getStateId()+"' group by cpd.pat_state order by count(*) desc", connectionSpace);
		JSONObject job = null;
    	if(data != null && data.size()>0)
    	{ 
    		jsonArray = new JSONArray();
    		for(Object obj:data)
    		{
    			Object[] ob = (Object[]) obj;
    			if(ob[0] != null && ob[1] != null)
    			{
    				job = new JSONObject();
    				job.put("ID", ob[0].toString());
    				job.put("NAME", ob[1].toString());
    				jsonArray.add(job);
    			}
    		}
	  	}
		return SUCCESS;
	}
    public String beforePatienceModify()
    {
    acManager = new LinkedHashMap<String, String>();
    acManager = CPSHelper.fetchAcManager(connectionSpace);
    serviceMap = new LinkedHashMap<String, String>();
    serviceMap = CPSHelper.fetchServiceMap(connectionSpace);
    serviceManager= new LinkedHashMap<String, String>();
    stateMap=new LinkedHashMap<String, String>();
    List<?> dataLists = new createTable().executeAllSelectQuery("select id, empName from employee_basic where deptname=(select id from department where deptName='Patient Care')  group by empName", connectionSpace);
	  if (dataLists != null && dataLists.size() > 0)
	  {
		  for (Iterator<?> iterator = dataLists.iterator(); iterator.hasNext();)
	      {
		      Object[] object = (Object[]) iterator.next();
		      if (object[0] != null && object[1] != null)
		      {
		    	  serviceManager.put(object[0].toString(), object[1].toString());
		      }
	      }
	  }
    int age = 30;
	String curDate = DateUtil.getCurrentDateIndianFormat();
	//String curYear = curDate.substring(6, 10);
	//String changeDOB = String.valueOf(Integer.parseInt(curYear) - age);
	//DOB = curDate.substring(0, 6) + changeDOB;
	DOB=curDate;
    String query =null;
	query="select code,country_name from country GROUP by country_name ORDER BY country_name ";
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
		    	  stateMap.put(object[0].toString(), object[1].toString());
		      }
	      }
	  }
	}
	try 
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append(" select cpd.id, cpd.patient_name,cpd.feed_status,cm.CUSTOMER_NAME,csm.service_name, cpd.med_location as loc,cpd.preferred_time,cpd.ac_manager as man,cpd.remarks, ");
		query1.append(" cpd.ac_manager,cpd.corp_name,cpd.pat_gender,cpd.uhid, cpd.pat_mobile,cpd.pat_dob,cpd.pat_country,co.country_name ");
		query1.append(" ,cpd.pat_state,cpd.pat_city,cpd.services,cpd.med_location,cpd.pat_email,cm.ACCOUNT_MANAGER_MOB,empb.empName,cpd.service_manager ");
		query1.append(" from corporate_patience_data as cpd  ");
		query1.append(" left join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name ");
		query1.append(" left join country as co on co.code=cpd.pat_country ");
		query1.append(" left join cps_service as csm on csm.id=cpd.services ");
		query1.append(" left JOIN employee_basic as empb on empb.id=cpd.service_manager ");
		query1.append(" left join cps_billinggroup_his as emp on emp.id=cpd.ac_manager WHERE cpd.id!=0 and cpd.id='"+id+"'");
		//System.out.println("query1query1 "+query1);
		List dataCount1 = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if (dataCount1!=null && dataCount1.size() > 0) 
		{
			for (Iterator iterator = dataCount1.iterator(); iterator .hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				
				if(object[0]!=null) 
				{
					manager=(getValueWithNullCheck(object[7]));
					setAcc_mob(getValueWithNullCheck(object[22]));
					managerId=(getValueWithNullCheck(object[9]));
					corp_name=(getValueWithNullCheck(object[3]));
					corp_nameId=(getValueWithNullCheck(object[10]));
					patient_type=(getValueWithNullCheck(object[2]));
					pgenders=(getValueWithNullCheck(object[11]));
					setPat_mobile(getValueWithNullCheck(object[13]));
					setPat_dob(getValueWithNullCheck(object[14]));
					countryid=(getValueWithNullCheck(object[15]));
					country_name=(getValueWithNullCheck(object[16]));
					stateid=(getValueWithNullCheck(object[17]));
					cityid=(getValueWithNullCheck(object[18]));
					services_Id=(getValueWithNullCheck(object[19]));
					services_name=(getValueWithNullCheck(object[4]));
					location_id=(getValueWithNullCheck(object[20]));
					location_name=(getValueWithNullCheck(object[5]));
					setPreferred_time(getValueWithNullCheck(object[6]));
					setRemarks(getValueWithNullCheck(object[8]));
					setUhid(getValueWithNullCheck(object[12]));
					setPat_email(getValueWithNullCheck(object[21]));
					setPatient_name(getValueWithNullCheck(object[1]));
					if(object[24]!=null && !object[24].toString().equalsIgnoreCase("NA") )
					{
						empName=(object[23].toString());
						emp_id=(object[24].toString());
					}
					else
					{
						//System.out.println("object[24].toString() ");
						empName="Select Service Manager";
						emp_id="-1";
					}
				}
			}
		}
			
	} 
	catch (Exception e) 
	{
		
		e.printStackTrace();
	}
	return SUCCESS;
    }
    public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}
	public String modifyCorporatePatience()
	{
		try 
		{
			List list = null;
			InsertDataTable ob = null;
			boolean status1 = false;
			String patDob = request.getParameter("pat_dob").trim();
			String patDob1 = request.getParameter("pat_dob1").trim();
			String preferTime=null;
			StringBuilder query= new StringBuilder();
			List<InsertDataTable> insert =new ArrayList<InsertDataTable>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			cbt.executeAllUpdateQuery("update dreamsol_bl_corp_hc_pkg set ACCOUNT_MANAGER_MOB='"+request.getParameter("acc_mob").trim()+"' where ACCOUNT_OFFICER='"+request.getParameter("ac_manager").trim()+"'", connectionSpace);
			query.append("select cpd.id,cpd.ac_manager,cpd.corp_name,cpd.feed_status,cpd.patient_name,cpd.pat_gender,cpd.uhid, ");
			query.append(" cpd.pat_mobile,cpd.pat_email,cpd.pat_dob,cpd.pat_country,cpd.pat_state,cpd.pat_city,cpd.services,cpd.med_location,cpd.preferred_time,cpd.remarks,cpd.service_manager ");
			query.append(" from corporate_patience_data as cpd ");
			query.append(" left join cps_customer_his as cm on cm.id=cpd.corp_name ");
			query.append(" left join country as co on co.code=cpd.pat_country  ");
			query.append(" left join cps_service as csm on csm.id=cpd.services ");
			query.append(" left join cps_billinggroup_his as emp on emp.id=cpd.ac_manager ");
			query.append(" where cpd.id='"+getId()+"' ");
			//System.out.println("00000000 "+query);
			list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					ob = new InsertDataTable();
					ob.setColName("patient_id");
					ob.setDataName(getValueWithNullCheck(object[0]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("ac_manager");
					ob.setDataName(getValueWithNullCheck(object[1]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("corp_name");
					ob.setDataName(getValueWithNullCheck(object[2]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("feed_status");
					ob.setDataName(getValueWithNullCheck(object[0]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("patient_name");
					ob.setDataName(getValueWithNullCheck(object[4]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_gender");
					ob.setDataName(getValueWithNullCheck(object[5]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("uhid");
					ob.setDataName(getValueWithNullCheck(object[6]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_mobile");
					ob.setDataName(getValueWithNullCheck(object[7]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_email");
					ob.setDataName(getValueWithNullCheck(object[8]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_dob");
					ob.setDataName(getValueWithNullCheck(object[9]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_country");
					ob.setDataName(getValueWithNullCheck(object[10]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_state");
					ob.setDataName(getValueWithNullCheck(object[11]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("pat_city");
					ob.setDataName(getValueWithNullCheck(object[12]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("services");
					ob.setDataName(getValueWithNullCheck(object[13]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("med_location");
					ob.setDataName(getValueWithNullCheck(object[14]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("preferred_time");
					ob.setDataName(getValueWithNullCheck(object[15]));
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("remarks");
					ob.setDataName(getValueWithNullCheck(object[16]));
					insert.add(ob);
					
					if(object[17]!=null)
					{
						ob = new InsertDataTable();
						ob.setColName("service_manager");
						ob.setDataName(getValueWithNullCheck(object[17]));
						insert.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("service_manager");
						ob.setDataName("(NULL)");
						insert.add(ob);
					}
					
					ob = new InsertDataTable();
					ob.setColName("update_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("update_time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insert.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("edit_by");
					ob.setDataName(userName);
					insert.add(ob);
					
					status1 = cbt.insertIntoTable("patient_data_edit_history",insert, connectionSpace);
				}
			}
			
			List<GridDataPropertyView> statusColName = Configuration
			.getConfigurationData("mapped_corporate_patient_configuration",
			                               accountID, connectionSpace, "", 0, "table_name","corporate_patient_configuration");
			boolean status = false;
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			if (statusColName != null && statusColName.size() > 0) 
			{
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();//
				
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null &&  Parmname != null && !Parmname.equalsIgnoreCase("pat_dob") 
					&& !Parmname.equalsIgnoreCase("pat_dob1") && !Parmname.equalsIgnoreCase("acc_mob")
					&& !Parmname.equalsIgnoreCase("preferred_timecal") && !Parmname.equalsIgnoreCase("hour")
					&& !Parmname.equalsIgnoreCase("service_manager") && !Parmname.equalsIgnoreCase("minuts") && !Parmname.equalsIgnoreCase("ampm") && !Parmname.equalsIgnoreCase("preferred_time"))
					{
						wherClause.put(Parmname, paramValue);
						
					}
				}
				if(request.getParameter("service_manager").trim()!=null && !request.getParameter("service_manager").trim().equalsIgnoreCase("-1"))
				{
					wherClause.put("service_manager", request.getParameter("service_manager").trim());
				}
				else
				{
					wherClause.put("service_manager", "NA");
				}
				if(patDob!=null && !patDob.equalsIgnoreCase(""))
				{
					wherClause.put("pat_dob", patDob);
				}
				else
				{
					wherClause.put("pat_dob", patDob1);
				}
				if(request.getParameter("preferred_timecal").trim()!=null && !request.getParameter("preferred_timecal").equalsIgnoreCase("") && request.getParameter("hour").trim()!=null && request.getParameter("minuts").trim()!=null && request.getParameter("ampm").trim()!=null)
				{
					preferTime=request.getParameter("preferred_timecal")+" " + request.getParameter("hour") +":"+ request.getParameter("minuts")+" "+request.getParameter("ampm");
					wherClause.put("preferred_time", preferTime);
				}
				else
				{
					preferTime=request.getParameter("preferred_time");
					wherClause.put("preferred_time", preferTime);
				}
				
				condtnBlock.put("id", getId());
				//cb.updateTableColomn("cps_status_history", wherClause,condtnBlock, connectionSpace);
				status=cbt.updateTableColomn("corporate_patience_data", wherClause,condtnBlock, connectionSpace);
				if (status) 
				{
					addActionMessage("Updated Successfully...");
				}
				else
				{
					addActionMessage("OOPS There is some error in data..."); 
				}
				
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String fetchDatepreferedTime()
	{
		jsonArray= new JSONArray();
		JSONObject obj = new JSONObject();
		if(fetchDate!=null && fetchDate.equals("today"))
		{
			 today=DateUtil.getCurrentDateIndianFormat()+" 08:00 AM";//+" "+DateUtil.getCurrentTimeHourMin();
			 obj.put("Today", today);
		}
		else if(fetchDate!=null && fetchDate.equals("tomorrow"))
		{
		  String tom=DateUtil.getNewDate("day",1, DateUtil.getCurrentDateUSFormat());
		  tomorrow=DateUtil.convertDateToIndianFormat(tom)+" 08:00 AM";//" "+DateUtil.getCurrentTimeHourMin();
		  obj.put("Tomorrow", tomorrow);
		}
		jsonArray.add(obj);
		  return SUCCESS;
	}
	public String fetchDateDOB()
	{
		 int age=30;
		 String p_dob;
		  String curDate=DateUtil.getCurrentDateIndianFormat();
		  String curYear=curDate.substring(6,10);
		  String changeDOB=String.valueOf(Integer.parseInt(curYear)-age);
		 // p_dob=DateUtil.convertDateToUSFormat(curDate.substring(0, 6)+changeDOB);
		  p_dob=curDate.substring(0, 6)+changeDOB;
		  jsonArray= new JSONArray();
		  JSONObject obj = new JSONObject();
		  obj.put("Today", p_dob);
		  jsonArray.add(obj);
		  return SUCCESS;
	}
	
	public String fetchState()
	{
    	String returnresult=ERROR;
        try
        {
        	   CommonOperInterface cbt = new CommonConFactory().createInterface();
        	List dataList = cbt.executeAllSelectQuery("SELECT state_name as name,state_name from state where city_code='"+getStateId()+"' group by state_name order by state_name", connectionSpace);
         if (dataList != null && dataList.size() > 0)
            {
             jsonArray = new JSONArray();
                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                {
                    Object[] object = (Object[]) iterator.next();
                    if (object[0] != null && object[1] != null)
                    {
                        JSONObject obj= new JSONObject();
                        obj.put("NAME",object[0].toString() );
                        obj.put("NAME", object[1].toString());
                        jsonArray.add(obj);
                    }
                }
            }
            
            returnresult=SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            returnresult = ERROR;
            }
    
        return returnresult;
    
	}
	public String fetchCity()
	{
    	String returnresult=ERROR;
        try
        {
        	   CommonOperInterface cbt = new CommonConFactory().createInterface();
        	List dataList = cbt.executeAllSelectQuery("SELECT city_name as cityName,city_name from city where state_name='"+getStateId()+"' group by city_name order by city_name", connectionSpace);
         if (dataList != null && dataList.size() > 0)
            {
             jsonArray = new JSONArray();
                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                {
                    Object[] object = (Object[]) iterator.next();
                    if (object[0] != null && object[1] != null)
                    {
                        JSONObject obj= new JSONObject();
                        obj.put("NAME",object[0].toString() );
                        obj.put("NAME", object[1].toString());
                        jsonArray.add(obj);
                    }
                }
            }
            
            returnresult=SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            returnresult = ERROR;
            }
    
        return returnresult;
    
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
	this.request=arg0;
	
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public Map<String, String> getServiceMap() {
		return serviceMap;
	}



	public void setServiceMap(Map<String, String> serviceMap) {
		this.serviceMap = serviceMap;
	}



	public Map<String, String> getStateMap() {
		return stateMap;
	}



	public void setStateMap(Map<String, String> stateMap) {
		this.stateMap = stateMap;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Map<String, String> getAcManager() {
		return acManager;
	}



	public void setAcManager(Map<String, String> acManager) {
		this.acManager = acManager;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corpName) {
		corp_name = corpName;
	}
	public String getCorp_nameId() {
		return corp_nameId;
	}
	public void setCorp_nameId(String corpNameId) {
		corp_nameId = corpNameId;
	}
	public String getPatient_type() {
		return patient_type;
	}
	public void setPatient_type(String patientType) {
		patient_type = patientType;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patientName) {
		patient_name = patientName;
	}
	public String getPgenders() {
		return pgenders;
	}
	public void setPgenders(String pgenders) {
		this.pgenders = pgenders;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getPat_mobile() {
		return pat_mobile;
	}
	public void setPat_mobile(String patMobile) {
		pat_mobile = patMobile;
	}
	public String getPat_email() {
		return pat_email;
	}
	public void setPat_email(String patEmail) {
		pat_email = patEmail;
	}
	
	
	
	public String getPat_dob() {
		return pat_dob;
	}
	public void setPat_dob(String patDob) {
		pat_dob = patDob;
	}
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String countryName) {
		country_name = countryName;
	}
	public String getStateid() {
		return stateid;
	}
	public void setStateid(String stateid) {
		this.stateid = stateid;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String stateName) {
		state_name = stateName;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String cityName) {
		city_name = cityName;
	}
	public String getServices_Id() {
		return services_Id;
	}
	public void setServices_Id(String servicesId) {
		services_Id = servicesId;
	}
	public String getServices_name() {
		return services_name;
	}
	public void setServices_name(String servicesName) {
		services_name = servicesName;
	}
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String locationId) {
		location_id = locationId;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String locationName) {
		location_name = locationName;
	}
	public String getPreferred_time() {
		return preferred_time;
	}
	public void setPreferred_time(String preferredTime) {
		preferred_time = preferredTime;
	}
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	public List<Object> getListdata() {
		return listdata;
	}
	public void setListdata(List<Object> listdata) {
		this.listdata = listdata;
	}


	public String getStateId() {
		return stateId;
	}


	public void setStateId(String stateId) {
		this.stateId = stateId;
	}


	public String getToday() {
		return today;
	}


	public void setToday(String today) {
		this.today = today;
	}


	public String getTomorrow() {
		return tomorrow;
	}


	public void setTomorrow(String tomorrow) {
		this.tomorrow = tomorrow;
	}


	public String getFetchDate() {
		return fetchDate;
	}


	public void setFetchDate(String fetchDate) {
		this.fetchDate = fetchDate;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getAcc_mob() {
		return acc_mob;
	}
	public void setAcc_mob(String accMob) {
		acc_mob = accMob;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String empId) {
		emp_id = empId;
	}
	public Map<String, String> getServiceManager() {
		return serviceManager;
	}
	public void setServiceManager(Map<String, String> serviceManager) {
		this.serviceManager = serviceManager;
	}
	
	
}