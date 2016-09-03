package com.Over2Cloud.ctrl.LongPatientStay;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.lab.order.LabPojo;
import com.Over2Cloud.ctrl.referral.activity.ReferralActivityBoardHelper;

@SuppressWarnings("serial")
public class longPatientStayActivityBoardForTicket extends GridPropertyBean implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(longPatientStayActivityBoardForTicket.class);
	CommonOperInterface coi = new CommonConFactory().createInterface();
	private String sdate,edate,location,nursing,doctor,speciality,level;
	private String uhid,patName, bedNo, admDoc,admSpec,atnDoc,admAt;
	public Map<String, String> locationMap ;
	public Map<String, String> nursingMap ;
	public Map<String, String> specialityMap ;
	public Map<String, String> admittungMap ;
	private HttpServletRequest request;
	List<LongPatientPojo> history_list = null;
	private JSONArray jsonArr = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
 	CommunicationHelper ch = new CommunicationHelper();
	private List<Object> masterViewProp = new ArrayList<Object>();
	public String beforeTakeActionLongPatient()
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
 

	public String fetchFilterspecialityTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		specialityMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct SPECIALITY as id, SPECIALITY as name from dreamsol_ip_vw_ticket ");
				if(edate!=null && sdate !=null)
				{
					qry.append(" where ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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
	
	
	public String fetchFilterAdmittingTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		admittungMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				 
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct ADMITTING_PRACTITIONER_NAME as id, ADMITTING_PRACTITIONER_NAME as name from dreamsol_ip_vw_ticket  ");
				if(edate!=null && sdate !=null)
				{
					qry.append(" where ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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
	public String fetchFilterLocationTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		locationMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append(" select distinct floor as id, floor as name from dreamsol_ip_vw_ticket  ");
				if(edate!=null && sdate !=null)
				{
					qry.append(" where ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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
 
	public String fetchFilterNurshingUnitTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		nursingMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append("select distinct nursing_code as id, nursing_unit as name from dreamsol_ip_vw_ticket ");
				if(edate!=null && sdate !=null)
				{
					qry.append(" where ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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

	
	
	public String viewActivityBoardDetailForTicket()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if (sdate==null || edate==null || sdate.equalsIgnoreCase("") || edate.equalsIgnoreCase("")|| sdate.equalsIgnoreCase(" ") || edate.equalsIgnoreCase(" ")) 
				{
					sdate=DateUtil.getCurrentDateIndianFormat();
					edate=DateUtil.getCurrentDateIndianFormat();
						 
				}
				 
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
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_long_patient_stay_configuration_ticket", accountID, connectionSpace, "", 0, "table_name", "long_patient_stay_configuration_ticket");
			if (statusColName != null && statusColName.size() > 0)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("action");
				gpv.setHeadingName("Action");
				gpv.setFormatter("takeActionTicket");
				gpv.setWidth(30);
				viewPageColumns.add(gpv);
				for (GridDataPropertyView gdp : statusColName)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
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
	public String viewActivityBoardDataForTicket()
	{
		try
		{
			if (sdate==null || edate==null || sdate.equalsIgnoreCase("") || edate.equalsIgnoreCase("")|| sdate.equalsIgnoreCase(" ") || edate.equalsIgnoreCase(" ")) 
			{
				sdate=DateUtil.getCurrentDateIndianFormat();
				edate=DateUtil.getCurrentDateIndianFormat();
					 
			}
			List dataList;
			fetchFilterspecialityTicket();
			fetchFilterAdmittingTicket();
			fetchFilterLocationTicket();
			fetchFilterNurshingUnitTicket();
			StringBuilder query = new StringBuilder("");
			if (!ValidateSession.checkSession())
				return LOGIN;
					List<Object> tempList = new ArrayList<Object>();
						 	query.append(" SELECT divw.id,divw.UHIID,divw.PATIENT_NAME,divw.ASSIGN_BED_NUM,divw.ATTENDING_PRACTITIONER_NAME,divw.ADMITTING_PRACTITIONER_NAME,divw.SPECIALITY,divw.ADMISSION_DATE,divw.ADMISSION_TIME,divw.nursing_unit,divw.floor, divw.Day_count,divw.status,divw.allot_to,divw.ticket_no,divw.level FROM dreamsol_ip_vw_ticket as divw");
							if(edate!=null && sdate !=null)
							{
								query.append(" where divw.ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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
									if(checkNull(obdata[12]).equalsIgnoreCase("Snooze"))
									{
										tempMap.put("status", "Parked");
									}
									else
									{
									tempMap.put("status", checkNull(obdata[12]));
									}
									tempMap.put("allot_to", checkNull(obdata[13]));
									tempMap.put("ticket_no", checkNull(obdata[14]));
									tempMap.put("level", checkNull(obdata[15]));
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
	public String fetchCounterStatusForTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				int total=0;
				jsonArr = new JSONArray();
				List dataList;
				JSONObject obj = new JSONObject();
				StringBuilder query = new StringBuilder("");
				if (sdate==null || edate==null || sdate.equalsIgnoreCase("") || edate.equalsIgnoreCase("")|| sdate.equalsIgnoreCase(" ") || edate.equalsIgnoreCase(" ")) 
				{
					sdate=DateUtil.getCurrentDateIndianFormat();
					edate=DateUtil.getCurrentDateIndianFormat();
						 
				}
				if (!ValidateSession.checkSession())
					return LOGIN;
				query.append(" SELECT   count(*),divw.status FROM dreamsol_ip_vw_ticket as divw");
				if(edate!=null && sdate !=null)
				{
					query.append(" where divw.ADMISSION_DATE Between  '"+DateUtil.convertDateToUSFormat(sdate)+"'  and  '"+DateUtil.convertDateToUSFormat(edate)+"' ");
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
				query.append(" group by divw.status ");
				dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			//	System.out.println("nursing::::  "+nursing);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] object =null;
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						 object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							if(object[1].toString().equalsIgnoreCase("Pending"))
							{
								obj.put("pending", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Close"))
							{
								obj.put("close", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							if(object[1].toString().equalsIgnoreCase("Snooze"))
							{
								obj.put("parked", object[0].toString());
								total=total+Integer.parseInt(object[0].toString());
							}
							jsonArr.add(obj);
						}
					}
					obj.put("total",total);
					jsonArr.add(obj);
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

	@SuppressWarnings("unchecked")
	public String actionOnTicket()
	{
		String returnResult = ERROR;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append(" update dreamsol_ip_vw_ticket ");
			sb.append(" set status='" + request.getParameter("actionStatus") + "' ");
			if(request.getParameter("actionStatus").equalsIgnoreCase("Snooze"))
			{
				sb.append(",escalation_date='" + DateUtil.getCurrentDateUSFormat() + "' ");
				sb.append(",escalation_time='" + request.getParameter("parkedTill") + "' ");
				sb.append(",snooze_date='" + DateUtil.getCurrentDateUSFormat() + "' ");
				sb.append(",snooze_time='" + request.getParameter("parkedTill") + "' ");
			}
			sb.append(" where id=" + id + "");
			int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);
			if (chkUpdate > 0)
			{
				Map<String, Object> wherClause = new HashMap<String, Object>();
				wherClause.put("dream_id", id);
				wherClause.put("action_by_name", (String) session.get("uName"));
				wherClause.put("action_by_id", session.get("empIdofuser").toString().split("-")[1]);
				wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
				wherClause.put("comment", request.getParameter("comment"));
				wherClause.put("status", request.getParameter("actionStatus"));
				if(request.getParameter("actionStatus").equalsIgnoreCase("Snooze"))
				{
					wherClause.put("snooze_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("snooze_time", request.getParameter("parkedTill"));
				}
				wherClause.put("close_by_id", request.getParameter("closeById"));
				wherClause.put("close_by_name", checkNull(request.getParameter("closeByName")));
				wherClause.put("level", checkNull(level));
				if (wherClause != null && wherClause.size() > 0)
				{
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (Map.Entry<String, Object> entry : wherClause.entrySet())
					{
						ob = new InsertDataTable();
						ob.setColName(entry.getKey());
						ob.setDataName(entry.getValue().toString());
						insertData.add(ob);
					}
					cbt.insertIntoTable("dreamsol_ip_vw_ticket_history", insertData, connectionSpace);
					insertData.clear();
				}
			}
			addActionMessage("Ticket Close Successfully !!!");
			returnResult = SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;

	}
	public String beforeViewTicketHistory()
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
				fetchHistoryDetailsData();
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

	/*private void fetchHistoryDetailsData()
	{
		try
		{
			LongPatientPojo HP = null;
			List data = null;
			history_list = new ArrayList<LongPatientPojo>();
			StringBuilder query = new StringBuilder("");
			query.append(" select his.action_date,his.action_time,his.status,his.level,his.comment,his.snooze_date,his.snooze_time from dreamsol_ip_vw_ticket_history as his  ");
			query.append(" where his.dream_id=" + id);
			query.append(" order by his.action_time asc ");
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					HP = new LongPatientPojo();
					HP.setActionDate(checkNull(object[0]));
					HP.setActionTime(checkNull(object[1]));
					HP.setStatus(checkNull(object[2]));
					HP.setLevel(checkNull(object[3]));
					if(checkNull(object[2]).equalsIgnoreCase("Snooze"))
						HP.setComment(checkNull(object[4])+"  Snooze upTo:"+DateUtil.convertDateToIndianFormat(checkNull(object[5]))+"    "+checkNull(object[6]));
					else
						HP.setComment(checkNull(object[4]));
					history_list.add(HP);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}

	}*/
	
	private void fetchHistoryDetailsData()
	{
		try
		{
			LongPatientPojo HP = null;
			List data = null;
			history_list = new ArrayList<LongPatientPojo>();
			StringBuilder query = new StringBuilder("");
			query.append(" select his.action_date,his.action_time,his.status,his.level,his.comment,his.snooze_date,his.snooze_time , his.action_by_name, his.close_by_name  from dreamsol_ip_vw_ticket_history as his  ");
			query.append(" where his.dream_id=" + id);
			query.append(" order by his.action_time asc ");
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					HP = new LongPatientPojo();
					HP.setActionDate(DateUtil.convertDateToIndianFormat(checkNull(object[0])));
					HP.setActionTime(checkNull(object[1]));
					HP.setStatus(checkNull(object[2]));
					HP.setLevel(checkNull(object[3]));
					HP.setAction_by(checkNull(object[7]));
					HP.setClose_by(checkNull(object[8]));
					if(checkNull(object[2]).equalsIgnoreCase("Snooze"))
						HP.setComment(checkNull(object[4])+"  Snooze upTo:"+DateUtil.convertDateToIndianFormat(checkNull(object[5]))+"    "+checkNull(object[6]));
					else
						HP.setComment(checkNull(object[4]));
					history_list.add(HP);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewLabOrderData of class " + getClass(), e);
			e.printStackTrace();
		}

	}
	public String fetchEmpNameById()
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String tst = "NA";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			{
				int hisUHIDLength = 8;
				int uhidLength = uhid.length();
				int templength = 0;
				if (uhidLength < hisUHIDLength)
				{

					templength = hisUHIDLength - uhidLength;
					for (int j = 0; j < templength; j++)
					{
						uhid = "0" + uhid;
					}
				}
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.34:1521:KRP", "spectra", "spectra");
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM EMPLOYEEINFORMATION_VW WHERE PERSONNUM = '" + uhid + "' ");
				jsonArr = new JSONArray();
				JSONObject listObject = new JSONObject();
				while (rs.next())
				{
					listObject.put("fName", rs.getString("FIRSTNAME"));
					listObject.put("lName", rs.getString("LASTNAME"));
				}
				jsonArr.add(listObject);
			}
			returnResult = "success";
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return returnResult;
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



	public String getUhid()
	{
		return uhid;
	}



	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}



	public String getPatName()
	{
		return patName;
	}



	public void setPatName(String patName)
	{
		this.patName = patName;
	}



	public String getBedNo()
	{
		return bedNo;
	}



	public void setBedNo(String bedNo)
	{
		this.bedNo = bedNo;
	}



	public String getAdmDoc()
	{
		return admDoc;
	}



	public void setAdmDoc(String admDoc)
	{
		this.admDoc = admDoc;
	}



	public String getAdmSpec()
	{
		return admSpec;
	}



	public void setAdmSpec(String admSpec)
	{
		this.admSpec = admSpec;
	}



	public String getAtnDoc()
	{
		return atnDoc;
	}



	public void setAtnDoc(String atnDoc)
	{
		this.atnDoc = atnDoc;
	}



	public String getAdmAt()
	{
		return admAt;
	}



	public String getLevel()
	{
		return level;
	}


	public void setLevel(String level)
	{
		this.level = level;
	}


	public void setAdmAt(String admAt)
	{
		this.admAt = admAt;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<LongPatientPojo> getHistory_list()
	{
		return history_list;
	}

	public void setHistory_list(List<LongPatientPojo> historyList)
	{
		history_list = historyList;
	}

}