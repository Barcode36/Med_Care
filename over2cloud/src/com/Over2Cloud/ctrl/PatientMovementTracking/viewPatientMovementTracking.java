package com.Over2Cloud.ctrl.PatientMovementTracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class viewPatientMovementTracking extends GridPropertyBean
{

	static final Log log = LogFactory.getLog(viewPatientMovementTracking.class);
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private String fromDate,toDate,location,nursing;
	private String fromDays,toDays,range,patDays;
	private List<String> days;
	public Map<String, String> locationMap ;
	public Map<String, String> nursingMap ;
	private JSONArray jsonArr = null;
	
 	CommunicationHelper ch = new CommunicationHelper();
	private List<Object> masterViewProp = new ArrayList<Object>();
	public String beforeViewActivityBoard()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if (fromDate == null || fromDate.equalsIgnoreCase("") || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(" ")) {
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
	 			}
					returnResult = SUCCESS;
					 createParameterList();
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

	@SuppressWarnings("unchecked")
	void createParameterList()
	{
		days=new ArrayList();
		List dataList;
		nursingMap = new HashMap<String,String>();
		locationMap = new HashMap<String,String>();
		String startDate=DateUtil.getCurrentDateUSFormat(),endDate=DateUtil.getCurrentDateUSFormat();
		try
		{
			StringBuilder query = new StringBuilder("");
			query.append(" select distinct nursing_code as id, nursing_unit as name from dreamsol_ip_vw ");
			query.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
			query.append(" order by nursing_unit asc ");

			dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						nursingMap.put("'"+object[0].toString()+"'", object[1].toString());
					}
				}
		   }
			
			query.setLength(0);
			query.append(" select distinct floor as id, floor as name from dreamsol_ip_vw where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' order by floor asc ");
		 	dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						locationMap.put("'"+object[0].toString()+"'", object[1].toString());
					}
				}
		   }
		 		 
			for (int a=0;a<=365;a++)
			{
					days.add(String.valueOf(a));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	 
	public String fetchFilterLocation()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		String startDate=DateUtil.getCurrentDateUSFormat(),endDate=DateUtil.getCurrentDateUSFormat();
		if (sessionFlag)
		{
			try
			{
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
					{
						  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
						  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
							 
					}
				}
				else if(range!=null && !range.equalsIgnoreCase("Other"))
				{
					if(patDays!=null && !patDays.equalsIgnoreCase(""))
					{
						startDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDays));
					}
				}
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append(" select distinct floor as id, floor as name from dreamsol_ip_vw ");
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Greater"))
				{
					qry.append(" where ADMISSION_DATE < '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("GreaterAndEqual"))
				{
					qry.append(" where divw.ADMISSION_DATE <= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Less"))
				{
					qry.append(" where ADMISSION_DATE > '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
				{
					qry.append(" where divw.ADMISSION_DATE >= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Equal"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
				}
				
				qry.append(" order by floor asc ");

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
 
	public String fetchFilterNurshingUnit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		String startDate=DateUtil.getCurrentDateUSFormat(),endDate=DateUtil.getCurrentDateUSFormat();
		if (sessionFlag)
		{
			try
			{
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
					{
						  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
						  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
							 
					}
				}
				else if(range!=null && !range.equalsIgnoreCase("Other"))
				{
					if(patDays!=null && !patDays.equalsIgnoreCase(""))
					{
						startDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDays));
					}
				}
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct nursing_code as id, nursing_unit as name from dreamsol_ip_vw ");
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Greater"))
				{
					qry.append(" where ADMISSION_DATE < '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("GreaterAndEqual"))
				{
					qry.append(" where divw.ADMISSION_DATE <= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Less"))
				{
					qry.append(" where ADMISSION_DATE > '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
				{
					qry.append(" where divw.ADMISSION_DATE >= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Equal"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
				}
				qry.append(" order by nursing_unit asc ");
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

	String checkNull(Object obj)
	{
		String returnResult = "NA";
		if (obj != null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1"))
		{
			returnResult = obj.toString().trim();
		}
		return returnResult;
	}

	
	
	public String viewActivityBoardDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
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

	void setMasterViewProps()
	{

		viewPageColumns=new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_long_patient_stay_configuration", accountID, connectionSpace, "", 0, "table_name", "long_patient_stay_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					viewPageColumns.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewActivityBoardData()
	{
		try
		{
			List dataList;
			String startDate=DateUtil.getCurrentDateUSFormat(),endDate=DateUtil.getCurrentDateUSFormat();
			StringBuilder query = new StringBuilder("");
			if(range!=null && range.equalsIgnoreCase("Other"))
			{
				if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
				{
					  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
					  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
						 
				}
			}
			else if(range!=null && !range.equalsIgnoreCase("Other"))
			{
				if(patDays!=null && !patDays.equalsIgnoreCase(""))
				{
					startDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDays));
				}
			}
			if (!ValidateSession.checkSession())
				return LOGIN;
					List<Object> tempList = new ArrayList<Object>();
						 	query.append(" SELECT divw.id,divw.UHIID,divw.PATIENT_NAME,divw.ASSIGN_BED_NUM,divw.ATTENDING_PRACTITIONER_NAME,divw.ADMITTING_PRACTITIONER_NAME,divw.SPECIALITY,divw.ADMISSION_DATE,divw.ADMISSION_TIME,divw.nursing_unit,divw.floor FROM dreamsol_ip_vw as divw");
						 	if(range!=null && range.equalsIgnoreCase("Other"))
							{
						 		query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
							}
							else if(range!=null && range.equalsIgnoreCase("Greater"))
							{
								query.append(" where divw.ADMISSION_DATE < '"+startDate+"' ");
							}
							else if(range!=null && range.equalsIgnoreCase("GreaterAndEqual"))
							{
								query.append(" where divw.ADMISSION_DATE <= '"+startDate+"' ");
							}
							else if(range!=null && range.equalsIgnoreCase("Less"))
							{
								query.append(" where divw.ADMISSION_DATE > '"+startDate+"' ");
							}
							else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
							{
								query.append(" where divw.ADMISSION_DATE >= '"+startDate+"' ");
							}
							else if(range!=null && range.equalsIgnoreCase("Equal"))
							{
								query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
							}
							if(nursing !=null && !nursing.equalsIgnoreCase("-1") && !nursing.equalsIgnoreCase("null"))
								query.append(" and divw.nursing_code In ('"+nursing+"') ");
							if(location !=null && !location.equalsIgnoreCase("-1") && !location.equalsIgnoreCase("null"))
								query.append(" and divw.floor In('"+location+"') ");
							query.append(" order by divw.ADMISSION_DATE ");
							
						//	System.out.println("query.toString(): "+query.toString());
							dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
							if (dataList != null && dataList.size()>0)
							{
								for (Iterator it = dataList.iterator(); it.hasNext();)
								{
									Object[] obdata = (Object[]) it.next();
									
									Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
									tempMap.put("id", checkNull(obdata[0]));
									tempMap.put("uhid", checkNull(obdata[1]));
									tempMap.put("pName", checkNull(obdata[2]));
									tempMap.put("bedNo",checkNull(obdata[3]));
									tempMap.put("attDoc", checkNull(obdata[4]));
									tempMap.put("admDoc", checkNull(obdata[5]));
									tempMap.put("admSpec", checkNull(obdata[6]));
									tempMap.put("ADMISSION_DATE", DateUtil.convertDateToIndianFormat(checkNull(obdata[7]))+" "+checkNull(obdata[8]));
									tempMap.put("nursing_unit", checkNull(obdata[9]));
									tempMap.put("floor", checkNull(obdata[10]));
									tempList.add(tempMap);
								}
							}
					setMasterViewProp(tempList);
			 		return "success";
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
 
	
	public String fetchCounterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				List dataList;
				String startDate=DateUtil.getCurrentDateUSFormat(),endDate=DateUtil.getCurrentDateUSFormat();
				StringBuilder query = new StringBuilder("");
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
					{
						  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
						  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
							 
					}
				}
				else if(range!=null && !range.equalsIgnoreCase("Other"))
				{
					if(patDays!=null && !patDays.equalsIgnoreCase(""))
					{
						startDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDays));
					}
				}
				if (!ValidateSession.checkSession())
					return LOGIN;
							 	query.append(" SELECT count(*) FROM dreamsol_ip_vw as divw");
							 	if(range!=null && range.equalsIgnoreCase("Other"))
								{
							 		query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
								}
								else if(range!=null && range.equalsIgnoreCase("Greater"))
								{
									query.append(" where divw.ADMISSION_DATE < '"+startDate+"' ");
								}
								else if(range!=null && range.equalsIgnoreCase("GreaterAndEqual"))
								{
									query.append(" where divw.ADMISSION_DATE <= '"+startDate+"' ");
								}
								else if(range!=null && range.equalsIgnoreCase("Less"))
								{
									query.append(" where divw.ADMISSION_DATE > '"+startDate+"' ");
								}
								else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
								{
									query.append(" where divw.ADMISSION_DATE >= '"+startDate+"' ");
								}
								else if(range!=null && range.equalsIgnoreCase("Equal"))
								{
									query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
								}
								if(nursing !=null && !nursing.equalsIgnoreCase("-1") && !nursing.equalsIgnoreCase("null"))
									query.append(" and divw.nursing_code In ('"+nursing+"') ");
								if(location !=null && !location.equalsIgnoreCase("-1") && !location.equalsIgnoreCase("null"))
									query.append(" and divw.floor In('"+location+"') ");
								query.append(" order by divw.ADMISSION_DATE ");
								
								//System.out.println("query.toString(): "+query.toString());
								dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
								 if (dataList != null && !dataList.isEmpty())
								{
									JSONObject obj = new JSONObject();
									obj.put("Total", dataList.get(0));
									jsonArr.add(obj);
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
	
	public List<Object> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	 

	public Map<String, String> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(Map<String, String> locationMap) {
		this.locationMap = locationMap;
	}

 
	 

	public String getFromDays() {
		return fromDays;
	}

	public void setFromDays(String fromDays) {
		this.fromDays = fromDays;
	}

	public String getToDays() {
		return toDays;
	}

	public void setToDays(String toDays) {
		this.toDays = toDays;
	}

	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) {
		this.days = days;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, String> getNursingMap() {
		return nursingMap;
	}

	public void setNursingMap(Map<String, String> nursingMap) {
		this.nursingMap = nursingMap;
	}

	public String getNursing() {
		return nursing;
	}

	public void setNursing(String nursing) {
		this.nursing = nursing;
	}

	public JSONArray getJsonArr() {
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getPatDays() {
		return patDays;
	}

	public void setPatDays(String patDays) {
		this.patDays = patDays;
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
 

}