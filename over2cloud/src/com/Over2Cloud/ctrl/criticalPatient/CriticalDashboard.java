package com.Over2Cloud.ctrl.criticalPatient;

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
public class CriticalDashboard extends GridPropertyBean
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
			int total = 0,off_time=0,total_close=0,total_open=0,on_time=0,total_informed=0,grand_inform=0,grand_open=0,grand_close=0;
			int hours=0,minutes=0,reso=0;
			String flagName="";
			String time2="00:00";
			Object[] object=null;
	 		List dataList = null;
	  		if(tableFor!=null && tableFor.equalsIgnoreCase("specialty"))
	 			dataList = getSpecialtyList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("doctor"))
	 			dataList = getDoctorList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("testType"))
	 			dataList = getTestTypeList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("testName"))
	 			dataList = getTestNameList();
	 	 	else if(tableFor!=null && tableFor.equalsIgnoreCase("location"))
	 			dataList = getLocationList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("nursingUnit"))
	 			dataList = getNursingUnitList(); 
	  		
	  		
	  		
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("specialtyProductivity"))
	 			dataList = getSpecialtyProductivityList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("doctorProductivity"))
	 			dataList = getDoctorProductivityList();
	 		else if(tableFor!=null && tableFor.equalsIgnoreCase("testTypeProductivity"))
		 		dataList = getTestTypeProductivityList();
		 	else if(tableFor!=null && tableFor.equalsIgnoreCase("testNameProductivity"))
	 			dataList = getTestNameProductivityList();
		 	 else if(tableFor!=null && tableFor.equalsIgnoreCase("locationProductivity"))
		 		dataList = getLocationProductivityList();
		 	else if(tableFor!=null && tableFor.equalsIgnoreCase("nursingUnitProductivity"))
	 			dataList = getNursingUnitProductivityList(); 
		 
		 	
		 	else if(tableFor!=null && tableFor.equalsIgnoreCase("crcReport"))
		 		dataList=getCRCReportList();
	 		if (dataList != null && dataList.size() > 0) {
			 	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
			 		object = (Object[]) iterator.next();
					if (flagName!=null && !flagName.equalsIgnoreCase(object[1].toString()))
					{
					DP = new DashboardPojo();
					total = 0;
					off_time=0;
					total_open=0;
					total_close=0;
					total_informed=0;
					on_time=0;
			 		}
			 		DP.setId(object[0].toString());
			 		DP.setName(object[1].toString());
			 		if(tableFor!=null && (tableFor.equalsIgnoreCase("doctorProductivity") || tableFor.equalsIgnoreCase("specialtyProductivity") || tableFor.equalsIgnoreCase("testNameProductivity") || tableFor.equalsIgnoreCase("testTypeProductivity")|| tableFor.equalsIgnoreCase("locationProductivity") || tableFor.equalsIgnoreCase("nursingUnitProductivity")))
			 		{
			 	 		on_time=on_time+Integer.parseInt(object[3].toString());
		 	 			total=total+Integer.parseInt(object[3].toString());
			 		 	off_time=off_time+Integer.parseInt(object[3].toString());
			   		 
			 		}
			 		
			 		else if(tableFor!=null && (tableFor.equalsIgnoreCase("crcReport") ))
			 		{
			 			if ((object[2]!=null)) 
				 		{
			 	 			total_open=total_open+Integer.parseInt(object[3].toString());
				 			total=total+Integer.parseInt(object[3].toString());
				 			grand_open=grand_open+total_open;
				 		}
				 		if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Informed") )) 
				 		{
				 			total_informed=total_informed+Integer.parseInt(object[3].toString());
				 			total=total+Integer.parseInt(object[3].toString());
				 			grand_inform=grand_inform+total_informed;
				 		}
				 		if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Close") || object[2].toString().equalsIgnoreCase("Ignore") || object[2].toString().equalsIgnoreCase("No Further Action Required"))) 
				 		{
			 	 			total_close=total_close+Integer.parseInt(object[3].toString());
				 			total=total+Integer.parseInt(object[3].toString());
				 			grand_close=grand_close+total_close;
				 		}
			   		 
			 		}
	 	 		else if(tableFor!=null && (tableFor.equalsIgnoreCase("specialty") || tableFor.equalsIgnoreCase("doctor")||tableFor.equalsIgnoreCase("testType") || tableFor.equalsIgnoreCase("testName")||tableFor.equalsIgnoreCase("location") || tableFor.equalsIgnoreCase("nursingUnit")))
			 		{
			 			 
			 			if ((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Not Informed") || object[2].toString().equalsIgnoreCase("Informed-P") || object[2].toString().equalsIgnoreCase("Informed") || object[2].toString().equalsIgnoreCase("Close-P")|| object[2].toString().equalsIgnoreCase("Snooze"))) 
				 		{
			 	 			total_open=total_open+Integer.parseInt(object[3].toString());
				 			total=total+Integer.parseInt(object[3].toString());
				 			 
				 		}
				 		else if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Ignore") || object[2].toString().equalsIgnoreCase("Close") || object[2].toString().equalsIgnoreCase("No Further Action Required"))) 
								 		{
			 	 			total_close=total_close+Integer.parseInt(object[3].toString());
				 			total=total+Integer.parseInt(object[3].toString());
				 			 
				 		}
			 			  
			 		}
			 		 
			 		DP.setCounter(String.valueOf(total));
			 		DP.setOffTime(String.valueOf(off_time));
			 		DP.setOnTime(String.valueOf(on_time));
			 		DP.setOpen(String.valueOf(total_open));
			 		DP.setClose(String.valueOf(total_close));
			 		DP.setInformed(String.valueOf(total_informed));
			 	 	
			 		if(tableFor!=null && (tableFor.equalsIgnoreCase("crcReport") ))
			 		{
			 			 
			 			 
			 			if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Informed") )) 
				 		{
			 			if(!object[4].toString().equalsIgnoreCase("00:00"))
				 		{
			 				
			 				if(!String.valueOf(total_informed).equalsIgnoreCase("0"))
			 				{
			 					
			 					time2=addTwoTime2(object[4].toString());
					 			reso=Integer.parseInt(time2)/(total_informed);
					 	 		hours = reso / 60;  
					 			minutes = reso % 60;
					 			
					 		 	if(String.valueOf(hours).length()==1 &&  String.valueOf(minutes).length()==1)
					 			{
					 			//	System.out.println("First Time : "+object[4].toString());
					 		 		DP.setFirstTAT("0"+hours+":"+"0"+minutes);
					 			}
					 			else
					 			{
					 			//	System.out.println("Sec Time: "+object[4].toString()+" ccccccc "+hours+":"+minutes);
					 				DP.setFirstTAT(String.valueOf(hours)+":"+String.valueOf(minutes));
					 			}
						 		 
					 			 
			 				}
			 				else
			 				{
			 					//System.out.println("Third Time: "+object[4].toString());
			 					DP.setFirstTAT("00:00");
			 				}
				 		}
				 		 
			 			else
			 			{
			 			//	System.out.println("Forth Time: "+object[4].toString());
			 				DP.setFirstTAT("00:00");
			 			}
				 		}
			 			if((object[2]!=null) && (object[2].toString().equalsIgnoreCase("Close") || object[2].toString().equalsIgnoreCase("Ignore") || object[2].toString().equalsIgnoreCase("No Further Action Required") )) 
				 		{
				 		if(!object[5].toString().equalsIgnoreCase("00:00"))
				 		{
				 			if(!String.valueOf(total_close).equalsIgnoreCase("0"))
			 				{
					 			time2=addTwoTime2(object[5].toString());
					 			reso=Integer.parseInt(time2)/(total_close);
					 			hours = reso / 60;
					 			minutes = reso % 60;
					 			
					 			if(String.valueOf(hours).length()==1 && String.valueOf(minutes).length()==1)
					 			{
					 				DP.setSecondTAT("0"+hours+":"+"0"+minutes);
					 			}
					 			else
					 			{
					 				DP.setSecondTAT(hours+":"+minutes);
					 			}
						 		 
			 				}
				 			else
				 			{
				 				DP.setSecondTAT("00:00");
				 			}
					 		 
				 		}
				 		else
				 		{
				 			DP.setSecondTAT("00:00");
				 		}
			 		}
			 	}
			 		if (total != 0 && flagName!=null && !flagName.equalsIgnoreCase(object[1].toString()))
			 			data_list.add(DP);
			 			dashObj.setDashList(data_list);
			 			flagName = object[1].toString();
			 		  
	 				}
			  
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	 

	 
	@SuppressWarnings("rawtypes")
	public List getSpecialtyList()
	{
		List data=null;
		try {
			  
		StringBuffer query = new StringBuffer();
		query.append(" select cpd.specialty as ids,cpd.specialty as name,cd.status,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		 
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  	if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by cpd.specialty,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getDoctorList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpd.addmision_doc_name as ids,cpd.addmision_doc_name as name,cd.status,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 		query.append(" and cpd.specialty = '" + id + "' ");
 		if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
 		if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by cpd.addmision_doc_name,cd.status ");

	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getTestTypeList()
	{
		List data=null;
		try {
			  
		StringBuffer query = new StringBuffer();
		query.append(" select cpr.test_type as ids,cpr.test_type as name,cd.status,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  	if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by cpr.test_type,cd.status ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getTestNameList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpr.test_name as ids,cpr.test_name as name,cd.status,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 		query.append(" and cpr.test_type = '" + id + "' ");
 		if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
 		if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by cpr.test_name,cd.status ");

	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getLocationList()
	{
		List data=null;
		try {
			  
		StringBuffer query = new StringBuffer();
		query.append(" select fname.id as ids,fname.floorname as name,cd.status,count(*) from critical_patient_data as cpd  ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cpd.nursing_unit ");
		query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  	if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by fname.floorname,cd.status  ");
	//	System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getNursingUnitList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select nunit.id as ids,nunit.nursing_unit as name,cd.status,count(*) from critical_patient_data as cpd  ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cpd.nursing_unit ");
		query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
 		query.append(" and  fname.id='" + id + "' ");
 		if(status!=null && status.equalsIgnoreCase("All"))
			query.append(" and cd.status In ('Not Informed','Informed-P','Informed','Close-P','Snooze','Ignore','Close','No Further Action Required') ");  	
	 	else if(status!=null && status.equalsIgnoreCase("Open"))
			query.append(" and cd.status In ('Informed','Close-P','Snooze','Not Informed','Informed-P') ");
	 	else if(status!=null && status.equalsIgnoreCase("Close"))
				query.append(" and cd.status In ('Ignore','Close','No Further Action Required') ");
 		if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	 	query.append(" group by nunit.nursing_unit,cd.status ");

	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	 
	
	
	@SuppressWarnings("rawtypes")
	public List getSpecialtyProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpd.specialty as ids,cpd.specialty as name,cd.level,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
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
	  	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 	 	query.append(" and cpd.patient_status='" + statusFor + "' ");
 	  	query.append(" group by cpd.specialty ");
 	  
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
 	
	@SuppressWarnings("rawtypes")
	public List getDoctorProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpd.addmision_doc_name as ids,cpd.addmision_doc_name as name,cd.level,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
 		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  	query.append(" and cpd.specialty ='" + id + "' ");
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
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
 	  	query.append(" group by cpd.addmision_doc_name ");
 	  
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
 	
	
	@SuppressWarnings("rawtypes")
	public List getLocationProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select fname.id as ids,fname.floorname as name,cd.level,count(*) from critical_patient_data as cpd  ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cpd.nursing_unit ");
		query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
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
	 	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 	 	query.append(" and cpd.patient_status='" + statusFor + "' ");
 	  	query.append(" group by fname.floorname ");
 	 
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
 	
	@SuppressWarnings("rawtypes")
	public List getNursingUnitProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
	 	query.append(" select nunit.id as ids,nunit.nursing_unit as name,cd.level,count(*) from critical_patient_data as cpd  ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join machine_order_nursing_details as nunit on nunit.nursing_unit=cpd.nursing_unit ");
		query.append(" Inner join floor_detail as fname on fname.id=nunit.floorname ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  	query.append(" and fname.id= '" + id + "' ");
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
	  	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
 	 	 query.append(" group by nunit.nursing_unit ");
 	  
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getCRCReportList()
	{
	 	List data=null;
		try {
			  
		StringBuffer query = new StringBuffer();
		query.append(" select cpr.test_type as ids,cpr.test_type as name,cd.status,count(*),cd.tat_not_to_inform,cd.tat_inform_to_close from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
	  	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	 
		if(statusFor!=null && !statusFor.equalsIgnoreCase("-1") && !statusFor.equalsIgnoreCase("All"))
		{
			query.append(" and cpd.patient_status='"+statusFor+"'");
		}
	 	query.append(" group by cpr.test_type,cd.status ");
		//System.out.println(">>>>>>>>"+query.toString());
 	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
	 
	@SuppressWarnings("rawtypes")
	public List getTestTypeProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpr.test_type as ids,cpr.test_type as name,cd.level,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		
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
		if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 	 	query.append(" and cpd.patient_status='" + statusFor + "' ");
  	 	query.append(" group by cpr.test_type ");
 	  
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
 	
	@SuppressWarnings("rawtypes")
	public List getTestNameProductivityList()
	{
		List data=null;
		try {
	 	StringBuffer query = new StringBuffer();
		query.append(" select cpr.test_name as ids,cpr.test_name as name,cd.level,count(*) from critical_patient_data as cpd ");
		query.append(" Inner join critical_data as cd on cd.patient_id=cpd.id ");
		query.append(" Inner join critical_patient_report as cpr on cpr.patient_id=cpd.id ");
		
 	
		
		
		if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
		{
			if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '23:59:59')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '00:00' and  '" + toTime + "')) ");
			else
				query.append("where ((cd.open_date='" + DateUtil.convertDateToUSFormat(fromDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (cd.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and cd.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
		}
		else
			query.append(" where  cd.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
	  
 		
		query.append(" and cpr.test_type = '" + id + "' ");
 		
		
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
	  
	   
	   	if(statusFor!=null && !statusFor.equalsIgnoreCase("All"))
	 		query.append(" and cpd.patient_status='" + statusFor + "' ");
	   	query.append(" group by cpr.test_name ");
	 	  
	  
	  	
	  	data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
  		}
 		 catch (Exception e) {
				e.printStackTrace();
		 	}
		return data;
	}
 	
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
		//System.out.println("CriticalActivityBoard.beforeCriticalView()");
		if (ValidateSession.checkSession())
		{
			try
			{
		
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPageColumns.add(gpv);

		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket ID");
		//gpv.setFormatter("historyView");
		gpv.setWidth(70);
		viewPageColumns.add(gpv);
		
		String empid=(String)session.get("empName").toString();
		 
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_critical_configuration", accountID, connectionSpace, "", 0, "table_name", "critical_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			if(!gdp.getColomnName().equalsIgnoreCase("doc_name") && !gdp.getColomnName().equalsIgnoreCase("doc_mobile") 
					&& !gdp.getColomnName().equalsIgnoreCase("test_type") && !gdp.getColomnName().equalsIgnoreCase("ticket_no") )
			{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setWidth(gdp.getWidth());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setHideOrShow(gdp.getHideOrShow());
			viewPageColumns.add(gpv);
			}
		}
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("test_type");
		gpv.setHeadingName("Test");
		gpv.setHideOrShow("true");
		gpv.setWidth(50);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_name");
		gpv.setHeadingName("Patient Name");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_mob");
		gpv.setHeadingName("Patient Mobile");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_status");
		gpv.setHeadingName("Type");
		gpv.setHideOrShow("false");
		gpv.setWidth(30);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("adm_doc");
		gpv.setHeadingName("Addmision Doc");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("nursing_unit");
		gpv.setHeadingName("Nursing Unit");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("bed_no");
		gpv.setHeadingName("Bed No");
		gpv.setHideOrShow("true");
		gpv.setWidth(80);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("spec");
		gpv.setHeadingName("Speciality");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("adm_doc_mob");
		gpv.setHeadingName("Add Doc Mob");
		gpv.setWidth(80);
		gpv.setHideOrShow("false");
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("lab_doc");
		gpv.setHeadingName("Lab Doctor");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("lab_doc_mob");
		gpv.setHeadingName("Lab Doc Mobile");
		gpv.setWidth(80);
		gpv.setHideOrShow("false");
		viewPageColumns.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_id");
		gpv.setHeadingName("Patient ID");
		gpv.setHideOrShow("true");
		viewPageColumns.add(gpv);
		
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
	
	
	
	//View Data in activity board
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewCriticalData()
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
	public static String addTwoTime2(String time1)
	 {
		String calculated_time = "00:00";
		try {
			
			int m,n;
			int total=0;
			int y=0;
			String j[]=time1.split(":");
				  m=Integer.parseInt(j[0]);
				  n=Integer.parseInt(j[1]);
				  y=(m*60)+n;
				   //System.out.printf("total"+ total);
				   calculated_time =String.valueOf(y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calculated_time;
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



 

	 

 

}
 