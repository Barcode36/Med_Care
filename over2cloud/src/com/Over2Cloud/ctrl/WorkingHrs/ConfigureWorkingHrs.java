package com.Over2Cloud.ctrl.WorkingHrs;

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
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ConfigureWorkingHrs extends GridPropertyBean implements ServletRequestAware
{

	private HttpServletRequest request;
	private String dataFor;
	private String actionType;

	private String dept;
	private String working_type;
	private String holiday;
	private String fromDay;
	private String toDay;
	private String fromTime = "00:00";
	private String toTime = "24:00";
 	private List<GridDataPropertyView> viewColumnList = null;
	private List<ConfigurationUtilBean> createColumnList = null;
	private Map<String, String> deptList = null;
	private List<Object> masterViewList;
	Map<String, String> appDetails = null;
	private JSONArray arrObj = new JSONArray();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public String firstAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (dataFor != null && !dataFor.equals("") && actionType != null && !actionType.equals("") && actionType.equals("Add"))
				{
					// setWorkingTimeTags();
					if (dataFor != null && !dataFor.equals(""))
					{
						deptList = new LinkedHashMap<String, String>();
						List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
						if (departmentlist != null && departmentlist.size() > 0)
						{
							for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
							{
								Object[] object1 = (Object[]) iterator.next();
								deptList.put(object1[0].toString(), object1[1].toString());
							}
						}
					}
					returnResult = "addsuccess";
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getHeaderBeforeView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				appDetails = new LinkedHashMap<String, String>();
				appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				dataFor = "all";
				getApplyedWorkingHrsDept();
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
	
	public String beforeViewWorkingHrs()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				workingTimingColumns();
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

	public String getApplyedWorkingHrsDept()
	{
		deptList = new LinkedHashMap<String, String>();
		WorkingHourHelper WHH = new WorkingHourHelper();
		List dataList = WHH.applyedWorkingHrsDept(connectionSpace, cbt, dataFor);
		if(dataList!=null && dataList.size()>0)
		{
			JSONObject obj =null;
			deptList.put("all", "All Department");
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					deptList.put(object[0].toString(), object[1].toString());
					
					obj = new JSONObject();
					obj.put("id", object[0].toString());
					obj.put("name", object[1].toString());
					arrObj.add(obj);
				}
			}
		}
		return SUCCESS;
	}
	
	
	public void workingTimingColumns()
	{
		viewColumnList = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewColumnList.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("day");
		gpv.setHeadingName("Day");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setWidth(200);
		viewColumnList.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_working_timeing_configuration", accountID, connectionSpace, "", 0, "table_name",
				"working_timeing_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			if (gdp.getColomnName() != null && !gdp.getColomnName().equals("") && !gdp.getColomnName().equals("fromDay") && !gdp.getColomnName().equals("toDay"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				viewColumnList.add(gpv);
			}
		}

	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewWorkingHrs()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				StringBuilder query = new StringBuilder("");
				List fieldNames = new ArrayList();
				query.append("select ");
				List columnList = Configuration.getColomnList("mapped_working_timeing_configuration", accountID, connectionSpace, "working_timeing_configuration");
				columnList.add("day");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = columnList.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (!obdata.toString().equalsIgnoreCase("fromDay") && !obdata.toString().equalsIgnoreCase("toDay"))
						{
							if (i < columnList.size() - 1)
							{
								if(obdata.toString().equals("id"))
									query.append("wh." + obdata.toString() + " as workid,");
								else if(obdata.toString().equals("dept"))
									query.append("dept.deptName,");
								else
									query.append("wh." + obdata.toString() + ",");
								
								fieldNames.add(obdata.toString());
							}
							else
							{
								query.append("wh." + obdata.toString());
								fieldNames.add(obdata.toString());
							}
						}
					}
					i++;
				}
				query.append(" from working_timing as wh");
				query.append(" left join department as dept ON dept.id = wh.dept");
				query.append(" where wh.id!=0");
				if(dataFor!=null && !dataFor.equalsIgnoreCase("all") && dept!=null && dept.equalsIgnoreCase("all"))
				{
					query.append(" and wh.moduleName='" + dataFor + "' AND dept='A'");
					
				}
				else if(dataFor!=null && !dataFor.equalsIgnoreCase("all") && dept!=null && !dept.equalsIgnoreCase("all"))
				{
					query.append(" and wh.moduleName='" + dataFor + "' AND dept="+dept);
					
				}
				else if(dataFor!=null && dataFor.equalsIgnoreCase("all") && dept!=null && !dept.equalsIgnoreCase("all"))
				{
					query.append(" and dept="+dept);
					
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}
					 
					
				}
			 //	System.out.println("Data Query "+query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					Object[] obdata1 = null;
					for (Iterator it1 = data.iterator(); it1.hasNext();)
					{
						obdata1 = (Object[]) it1.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k <= fieldNames.size() - 1; k++)
						{
							if (obdata1[k] != null && !obdata1[k].toString().equalsIgnoreCase(""))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata1[k]);
								}
								else
								{
									if (fieldNames.get(k).toString().equals("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
									}
									else if (fieldNames.get(k).toString().equals("time"))
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString().substring(0, obdata1[k].toString().length() - 3));
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "All Contact-Sub Type");
							}
						}
						Listhb.add(one);
					}
					setRecords(Listhb.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String editWorkingHrs()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//System.out.println(" getOper() "+getOper());
				if (getOper().equalsIgnoreCase("edit"))
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String newAssetName = null;
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equals("fromTime"))
							{
								if (paramValue == null || paramValue.equals("") || paramValue.equals("00:00"))
									fromTime = "00:01";
								else
									fromTime = getTime(fromTime);
								
								wherClause.put(parmName, fromTime);
							}
							else if (parmName.equals("toTime"))
							{
								if (paramValue == null || paramValue.equals("") || paramValue.equals("24:00"))
									toTime = "23:59";
								else
									toTime = getTime(toTime);
								
								wherClause.put(parmName, toTime);
							}
						}
					}
					wherClause.put("working_hrs", DateUtil.findDifferenceTwoTime(fromTime, toTime));
					wherClause.put("non_working_hrs", DateUtil.findDifferenceTwoTime(DateUtil.findDifferenceTwoTime(fromTime, toTime),"24:00"));
					
					condtnBlock.put("id", getId());
					boolean status = cbt.updateTableColomn("working_timing", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					
					cbt.deleteAllRecordForId("working_timing", "id", getId(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String beforeAdd()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				deptList = new LinkedHashMap<String, String>();
				appDetails = new LinkedHashMap<String, String>();
				appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				setWorkingTimeTags();
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

	@SuppressWarnings("rawtypes")
	public String getConcernDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String deptLevel = "1";
				if (dataFor != null && dataFor.equals("HDM"))
				{
					deptLevel = "2";
				}
				List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept(deptLevel, orgLevel, orgId, dataFor, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					JSONObject obj = null;
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						obj = new JSONObject();
						Object[] object1 = (Object[]) iterator.next();
						obj.put("id", object1[0].toString());
						obj.put("name", object1[1].toString());
						arrObj.add(obj);
					}
				}
				//System.out.println(arrObj.size());
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

	public void setWorkingTimeTags()
	{
		ConfigurationUtilBean obj;
		createColumnList = new ArrayList<ConfigurationUtilBean>();
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_working_timeing_configuration", accountID, connectionSpace, "", 0, "table_name",
				"working_timeing_configuration");
		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().trim().equalsIgnoreCase("userName") && !gdv.getColomnName().trim().equalsIgnoreCase("date") && !gdv.getColomnName().trim().equalsIgnoreCase("time"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					createColumnList.add(obj);
				}
			}
		}
	}

	public String addWorkingTimings()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_working_timeing_configuration", "", connectionSpace, "", 0, "table_name",
						"working_timeing_configuration");

				if (colName != null && colName.size() > 0)
				{
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName)
					{
						if (!tableColumes.getColomnName().equals("toDay"))
						{
							TableColumes ob1 = new TableColumes();
							if (tableColumes.getColomnName().equalsIgnoreCase("fromDay"))
							{
								ob1.setColumnname("day");
							}
							else
							{
								ob1.setColumnname(tableColumes.getColomnName());
							}
							ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
					}
					cot.createTable22("working_timing", TableColumnName, connectionSpace);
				}

				InsertDataTable ob = null;
				InsertDataTable ob1 = null;
				String deptValue = "";
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("fromDay") && !Parmname.equalsIgnoreCase("today")
							&& !Parmname.equalsIgnoreCase("dataFor")&& !Parmname.equalsIgnoreCase("dept"))
					{
						
						 
						  if (Parmname.equals("fromTime"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							if (paramValue == null || paramValue.equals("") || paramValue.equals("00:00"))
								fromTime = "00:01";
							else
								fromTime = getTime(fromTime);
							
							ob.setDataName(fromTime);
							insertData.add(ob);
						}
						else if (Parmname.equals("toTime"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							if (paramValue == null || paramValue.equals("") || paramValue.equals("24:00"))
								toTime = "23:59";
							else
								toTime = getTime(toTime);
							
							ob.setDataName(toTime);
							insertData.add(ob);
						}
						else
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
						 
					}
				}

				ob = new InsertDataTable();
				ob.setColName("working_hrs");
				ob.setDataName(DateUtil.findDifferenceTwoTime(fromTime, toTime));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("non_working_hrs");
				ob.setDataName(DateUtil.findDifferenceTwoTime(DateUtil.findDifferenceTwoTime(fromTime, toTime),"24:00"));
				insertData.add(ob);
				
				if(dataFor!=null && !dataFor.equalsIgnoreCase(""))
				{
					String arr[]=getDataFor().split(",");
					ob = new InsertDataTable();
					ob.setColName("moduleName");
					ob.setDataName(arr[1].trim());
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				boolean status = false,existFlag=false;
				 
				 	ob1 = new InsertDataTable();
					ob1.setColName("dept");
					String dpt[]=dept.split(", ");
					
				//	System.out.println("DDDDDD"+dept);
					for(int a=0;a<dpt.length;a++)
					{
						int startPoint = Integer.parseInt(fromDay);
						int endPoint = Integer.parseInt(toDay);
						String dayValue = "";
					//	boolean existFlag = false;
						for (int i = startPoint; i <= endPoint; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("day");
							if (i == 1)
							{
								dayValue = "Monday";
							}
							else if (i == 2)
							{
								dayValue = "Tuesday";
							}
							else if (i == 3)
							{
								dayValue = "Wednesday";
							}
							else if (i == 4)
							{
								dayValue = "Thursday";
							}
							else if (i == 5)
							{
								dayValue = "Friday";
							}
							else if (i == 6)
							{
								dayValue = "Saturday";
							}
							else if (i == 7)
							{
								dayValue = "Sunday";

							}
							ob.setDataName(dayValue);
							insertData.add(ob);
						
						ob1.setDataName(dpt[a]);
						insertData.add(ob1);
						deptValue=dpt[a];
						existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "dept", deptValue, "day", dayValue, "moduleName", "HDM", connectionSpace);
						if (!existFlag)
						{
							status = cbt.insertIntoTable("working_timing", insertData, connectionSpace);
							insertData.remove(ob);
							insertData.remove(ob1);
							 
						}
					 
						  
						}
					}
				  if(status==true)
					  addActionMessage("Working Time Added Successfully !!");
				  else
						addActionMessage("Error in Working Time Added....Working Time Already Added For Selected Department !!");
					
				returnResult= SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getTime(String time)
	{
		StringBuilder strTime = new StringBuilder();
		String str[] = time.split(":");
		if(str[1].equals("00"))
			return DateUtil.findDifferenceTwoTime("00:01",time);
		else
			return time;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getWorking_type()
	{
		return working_type;
	}

	public void setWorking_type(String working_type)
	{
		this.working_type = working_type;
	}

	public String getHoliday()
	{
		return holiday;
	}

	public void setHoliday(String holiday)
	{
		this.holiday = holiday;
	}

	public String getFromDay()
	{
		return fromDay;
	}

	public void setFromDay(String fromDay)
	{
		this.fromDay = fromDay;
	}

	public String getToDay()
	{
		return toDay;
	}

	public void setToDay(String toDay)
	{
		this.toDay = toDay;
	}

	public String getFromTime()
	{
		return fromTime;
	}

	public void setFromTime(String fromTime)
	{
		this.fromTime = fromTime;
	}

	public String getToTime()
	{
		return toTime;
	}

	public void setToTime(String toTime)
	{
		this.toTime = toTime;
	}

	public List<GridDataPropertyView> getViewColumnList()
	{
		return viewColumnList;
	}

	public void setViewColumnList(List<GridDataPropertyView> viewColumnList)
	{
		this.viewColumnList = viewColumnList;
	}

	public List<ConfigurationUtilBean> getCreateColumnList()
	{
		return createColumnList;
	}

	public void setCreateColumnList(List<ConfigurationUtilBean> createColumnList)
	{
		this.createColumnList = createColumnList;
	}

	public Map<String, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<String, String> deptList)
	{
		this.deptList = deptList;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public Map<String, String> getAppDetails()
	{
		return appDetails;
	}

	public void setAppDetails(Map<String, String> appDetails)
	{
		this.appDetails = appDetails;
	}

	public JSONArray getArrObj()
	{
		return arrObj;
	}

	public void setArrObj(JSONArray arrObj)
	{
		this.arrObj = arrObj;
	}

}
