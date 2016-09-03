package com.Over2Cloud.ctrl.LongPatientStay;

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
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.referral.activity.ReferralActivityBoardHelper;

@SuppressWarnings("serial")
public class viewLongPatientStayActivityBoard extends GridPropertyBean
{

	static final Log log = LogFactory.getLog(viewLongPatientStayActivityBoard.class);
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private String sdate,edate,location,nursing,recordSize,doctor,speciality;
	private String fromDays,toDays,fromDaysForDate,toDaysForDate,range,patDays,rangeForDate,patDaysForDate;
	private List<String> days;
	public Map<String, String> locationMap ;
	public Map<String, String> nursingMap ;
	public Map<String, String> specialityMap ;
	public Map<String, String> admittungMap ;
	private JSONArray jsonArr = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
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
					returnResult = SUCCESS;
					days=new ArrayList();
					for (int a=0;a<=365;a++)
					{
							days.add(String.valueOf(a));
					}
					fetchFilterspeciality();
					fetchFilterAdmitting();
					fetchFilterLocation();
					fetchFilterNurshingUnit();
					 // createParameterList();
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

 

	public String fetchFilterspeciality()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		specialityMap = new LinkedHashMap<String, String>();
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
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
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
				qry.append("select distinct SPECIALITY as id, SPECIALITY as name from dreamsol_ip_vw ");
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
				else if(rangeForDate!=null && endDate!=null && startDate !=null && !rangeForDate.equalsIgnoreCase("-1"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else 
					{
						qry.append(" where  ADMISSION_DATE <= '"+startDate+"' ");
					}
				String empid=(String)session.get("empName").toString();
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("longPatlocation_VIEW"))
						{
							String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null && !nursingUnit.contains("All"))
								qry.append(" and nursing_code In ( "+nursingUnit+" )");
						}
						 
					}
				}
				qry.append(" order by  SPECIALITY ");
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
								specialityMap.put("'"+object[0].toString()+"'", object[1].toString());
							}
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
	
	
	public String fetchFilterAdmitting()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		admittungMap = new LinkedHashMap<String, String>();
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
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
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
				qry.append("select distinct ADMITTING_PRACTITIONER_NAME as id, ADMITTING_PRACTITIONER_NAME as name from dreamsol_ip_vw  ");
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
				else if(rangeForDate!=null && endDate!=null && startDate !=null && !rangeForDate.equalsIgnoreCase("-1"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else 
				{
					qry.append(" where  ADMISSION_DATE <= '"+startDate+"' ");
				}
				String empid=(String)session.get("empName").toString();
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("longPatlocation_VIEW"))
						{
							String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null && !nursingUnit.contains("All"))
								qry.append(" and nursing_code In ( "+nursingUnit+" )");
						}
						 
					}
				}
				qry.append(" order by  ADMITTING_PRACTITIONER_NAME  asc ");
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
								admittungMap.put("'"+object[0].toString()+"'", object[1].toString());
							}
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
	public String fetchFilterLocation()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		locationMap = new LinkedHashMap<String, String>();
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
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
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
				qry.append(" select distinct floor as id, floor as name from dreamsol_ip_vw  ");
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
					qry.append(" where ADMISSION_DATE <= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Less"))
				{
					qry.append(" where ADMISSION_DATE > '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
				{
					qry.append(" where ADMISSION_DATE >= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Equal"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
				}
				else if(rangeForDate!=null && endDate!=null && startDate !=null && !rangeForDate.equalsIgnoreCase("-1"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else
				{
					qry.append(" where ADMISSION_DATE <= '"+DateUtil.getCurrentDateUSFormat()+"' ");
				}
				String empid=(String)session.get("empName").toString();
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("longPatlocation_VIEW"))
						{
							String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null && !nursingUnit.contains("All"))
								qry.append(" and nursing_code In ( "+nursingUnit+" )");
						}
						 
					}
				}
				 
				qry.append(" order by floor asc ");

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
								locationMap.put("'"+object[0].toString()+"'", object[1].toString());
							}
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
		nursingMap = new LinkedHashMap<String, String>();
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
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
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
					qry.append(" where ADMISSION_DATE <= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Less"))
				{
					qry.append(" where ADMISSION_DATE > '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("LessAndEqual"))
				{
					qry.append(" where ADMISSION_DATE >= '"+startDate+"' ");
				}
				else if(range!=null && range.equalsIgnoreCase("Equal"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+startDate+"' ");
				}
				else if(rangeForDate!=null && endDate!=null && startDate !=null && !rangeForDate.equalsIgnoreCase("-1"))
				{
					qry.append(" where ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
				}
				else
				{
					qry.append(" where ADMISSION_DATE <= '"+DateUtil.getCurrentDateUSFormat()+"' ");
				}
				String empid=(String)session.get("empName").toString();
				String[] subModuleRightsArray = getSubModuleRights(empid);
				if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for(String s:subModuleRightsArray)
					{
						if(s.equals("longPatlocation_VIEW"))
						{
							String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if(nursingUnit!=null && !nursingUnit.contains("All"))
								qry.append(" and nursing_code In ( "+nursingUnit+" )");
						}
						 
					}
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
								nursingMap.put("'"+object[0].toString()+"'", object[1].toString());
							}
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
			String startDate=null ,endDate =null;
			StringBuilder query = new StringBuilder("");
			if(range!=null && range.equalsIgnoreCase("Other"))
			{
				if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
				{
					  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
					  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
						 
				}
			}
			else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
			{
				
				if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
				{
					startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
				}
				else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
				{
					startDate=DateUtil.convertDateToUSFormat(sdate);
					endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
				}
				else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
				{
					startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
					endDate=DateUtil.convertDateToUSFormat(edate);
				}
				else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
				{
					startDate=DateUtil.convertDateToUSFormat(sdate);
					endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
				}
				else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
				{
					startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					endDate=DateUtil.convertDateToUSFormat(edate);
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
						 	query.append(" SELECT divw.id,divw.UHIID,divw.PATIENT_NAME,divw.ASSIGN_BED_NUM,divw.ATTENDING_PRACTITIONER_NAME,divw.ADMITTING_PRACTITIONER_NAME,divw.SPECIALITY,divw.ADMISSION_DATE,divw.ADMISSION_TIME,divw.nursing_unit,divw.floor, divw.Day_count FROM dreamsol_ip_vw as divw");
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
						
							else if(rangeForDate!=null && endDate!=null && startDate !=null )
							{
								query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
							}
							else
							{
								query.append(" where ADMISSION_DATE <= '"+DateUtil.getCurrentDateUSFormat()+"' ");
							}
						 	String empid=(String)session.get("empName").toString();
							String[] subModuleRightsArray = getSubModuleRights(empid);
							if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
							{
								for(String s:subModuleRightsArray)
								{
									if(s.equals("longPatlocation_VIEW"))
									{
										String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
										if(nursingUnit!=null && !nursingUnit.contains("All"))
											query.append(" and divw.nursing_code In ( "+nursingUnit+" )");
									}
									 
								}
							}
							 
							 
						 	
						 	if(nursing !=null && !nursing.equalsIgnoreCase("-1") && !nursing.equalsIgnoreCase("null"))
								query.append(" and divw.nursing_code In ("+nursing+") ");
							if(location !=null && !location.equalsIgnoreCase("-1") && !location.equalsIgnoreCase("null"))
								query.append(" and divw.floor In("+location+") ");
							if(doctor !=null && !doctor.equalsIgnoreCase("-1") && !doctor.equalsIgnoreCase("null"))
								query.append(" and divw.ADMITTING_PRACTITIONER_NAME In("+doctor+") ");
							if(speciality !=null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null"))
								query.append(" and divw.SPECIALITY In("+speciality+") ");
							query.append(" order by divw.ADMISSION_DATE ");
							
							dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
						//	System.out.println("nursing::::  "+nursing);
							if (dataList != null && dataList.size()>0)
							{
								for (Iterator it = dataList.iterator(); it.hasNext();)
								{
									Object[] obdata = (Object[]) it.next();
									
									Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
									tempMap.put("id", checkNull(obdata[0]));
									tempMap.put("UHIID", checkNull(obdata[1]));
									tempMap.put("PATIENT_NAME", checkNull(obdata[2]));
									tempMap.put("ASSIGN_BED_NUM",checkNull(obdata[3]));
									tempMap.put("ATTENDING_PRACTITIONER_NAME", checkNull(obdata[4]));
									tempMap.put("ADMITTING_PRACTITIONER_NAME", checkNull(obdata[5]));
									tempMap.put("SPECIALITY", checkNull(obdata[6]));
									tempMap.put("ADMISSION_DATE", DateUtil.convertDateToIndianFormat(checkNull(obdata[7]))+" "+checkNull(obdata[8]));
									tempMap.put("nursing_unit", checkNull(obdata[9]));
									tempMap.put("floor", checkNull(obdata[10]));
									tempMap.put("Day_count", checkNull(obdata[11]));
									tempList.add(tempMap);
								}
							}
					setMasterViewProp(tempList);
					setRecordSize(String.valueOf(tempList.size()));
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
				String startDate=null,endDate=null;
				StringBuilder query = new StringBuilder("");
				if(range!=null && range.equalsIgnoreCase("Other"))
				{
					if (fromDays!=null && toDays!=null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1")) 
					{
						  startDate=DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
						  endDate=DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));
							 
					}
				}
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
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
								else if(rangeForDate!=null && endDate!=null && startDate !=null )
								{
									query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
								}
								else
								{
									query.append(" where ADMISSION_DATE <= '"+DateUtil.getCurrentDateUSFormat()+"' ");
								}
							 	String empid=(String)session.get("empName").toString();
								String[] subModuleRightsArray = getSubModuleRights(empid);
								if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
								{
									for(String s:subModuleRightsArray)
									{
										if(s.equals("longPatlocation_VIEW"))
										{
											String nursingUnit = getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
											if(nursingUnit!=null && !nursingUnit.contains("All"))
												query.append(" and divw.nursing_code In ( "+nursingUnit+" )");
										}
										 
									}
								}
								 
							 	
								if(nursing !=null && !nursing.equalsIgnoreCase("-1") && !nursing.equalsIgnoreCase("null"))
									query.append(" and divw.nursing_code In ("+nursing+") ");
								if(location !=null && !location.equalsIgnoreCase("-1") && !location.equalsIgnoreCase("null"))
									query.append(" and divw.floor In("+location+") ");
								if(doctor !=null && !doctor.equalsIgnoreCase("-1") && !doctor.equalsIgnoreCase("null"))
									query.append(" and divw.ADMITTING_PRACTITIONER_NAME In ("+doctor+") ");
								if(speciality !=null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null"))
									query.append(" and divw.SPECIALITY In("+speciality+") ");
						
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
	
	public String[] getSubModuleRights(String userId)
	{
		String[] value = null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where name = '"+userId+"'", connectionSpace);
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
	public String getNursingUnitByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		@SuppressWarnings("unused")
		String subDeptName = null;
		String temp="";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select cc.location from compliance_contacts as cc  where  cc.emp_id ='"+empId+"' and cc.work_status='0' and cc.moduleName='LPS' ", connection);

			if (dataList != null && dataList.size() > 0) {
				subDeptName = dataList.get(0).toString();
				for(Object s:dataList)
				{
					temp=temp+"'"+s.toString()+"'"+",";
				}
				temp=temp.substring(0,temp.length()-1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return temp;
	}
	 private  String isCommonSeparated(String s){
         if(s.contains(",")){
             StringBuilder sb=new StringBuilder();
             for(int i=0;i<s.split(",").length;i++){
                 sb.append("'"+s.split(",")[i]+"'");
                     if(i<s.split(",").length-1){
                         sb.append(",");
                     }
             }
             return sb.toString();
         }else{
             return "'"+s+"'";
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

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	 

	public String getRangeForDate() {
		return rangeForDate;
	}

	public void setRangeForDate(String rangeForDate) {
		this.rangeForDate = rangeForDate;
	}

	public String getPatDaysForDate() {
		return patDaysForDate;
	}

	public void setPatDaysForDate(String patDaysForDate) {
		this.patDaysForDate = patDaysForDate;
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

	public String getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(String recordSize) {
		this.recordSize = recordSize;
	}

	public String getFromDaysForDate() {
		return fromDaysForDate;
	}

	public void setFromDaysForDate(String fromDaysForDate) {
		this.fromDaysForDate = fromDaysForDate;
	}

	public String getToDaysForDate() {
		return toDaysForDate;
	}

	public void setToDaysForDate(String toDaysForDate) {
		this.toDaysForDate = toDaysForDate;
	}

	public Map<String, String> getSpecialityMap()
	{
		return specialityMap;
	}

	public void setSpecialityMap(Map<String, String> specialityMap)
	{
		this.specialityMap = specialityMap;
	}

	public Map<String, String> getAdmittungMap()
	{
		return admittungMap;
	}

	public void setAdmittungMap(Map<String, String> admittungMap)
	{
		this.admittungMap = admittungMap;
	}

	public String getDoctor()
	{
		return doctor;
	}

	public void setDoctor(String doctor)
	{
		this.doctor = doctor;
	}

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

}