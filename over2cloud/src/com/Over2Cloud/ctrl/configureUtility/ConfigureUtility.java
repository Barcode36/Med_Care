package com.Over2Cloud.ctrl.configureUtility;

import java.math.BigInteger;
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
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ConfigureUtility extends ActionSupport implements ServletRequestAware
{

	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private List<ConfigurationUtilBean> configureUtilityColumnText = null;
	private List<ConfigurationUtilBean> UtilityColumnDropdown = null;
	private List<ConfigurationUtilBean> dailyUtilityColumnText = null;
	private List<ConfigurationUtilBean> dailyUtilityColumnDropdown = null;
	private Map<Integer, String> outletTypeList = null;
	private Map<Integer, String> utility_List = null;
	private List<GridDataPropertyView> viewConfigureUtilityGrid = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewDailyConfigureGrid = new ArrayList<GridDataPropertyView>();
	private JSONArray jsonArr = null;
	private String associateType;
	private Map<String, String> empolyeeMap = null;
	private List<Object> viewDailyConfigureData = null;
	private List<Object> viewConfigureData = null;
	private Boolean sms;

	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private String outletName;
	private String outlet_Name;

	public String configureUtilityAdd()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setconfigureUtilityAdd();
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	@SuppressWarnings("unchecked")
	private void setconfigureUtilityAdd()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			configureUtilityColumnText = new ArrayList<ConfigurationUtilBean>();
			UtilityColumnDropdown = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_configure_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_utility_configuration");
			if (msg != null)
			{
				for (GridDataPropertyView gdp : msg)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") || gdp.getColomnName().equalsIgnoreCase("utility_Name") || gdp.getColomnName().equalsIgnoreCase("brief") || gdp.getColomnName().equalsIgnoreCase("threshold_Level_Min")
							|| gdp.getColomnName().equalsIgnoreCase("threshold_Level_Max") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("emp_id") && !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("associateName") && !gdp.getColomnName().equalsIgnoreCase("threshold_Level_Max_alert")
							&& !gdp.getColomnName().equalsIgnoreCase("threshold_Level_Max_alert") && !gdp.getColomnName().equalsIgnoreCase("threshold_Level_Min_no") && !gdp.getColomnName().equalsIgnoreCase("threshold_Level_Min_yes")
							&& !gdp.getColomnName().equalsIgnoreCase("Threshold_Level_Max1") && !gdp.getColomnName().equalsIgnoreCase("Threshold_Level_Min1") && !gdp.getColomnName().equalsIgnoreCase("outlet_id"))

					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}

						configureUtilityColumnText.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("D") || gdp.getColomnName().equalsIgnoreCase("outlet_type") || gdp.getColomnName().equalsIgnoreCase("outlet_Name") || gdp.getColomnName().equalsIgnoreCase("alert_to"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						UtilityColumnDropdown.add(obj);
					}

				}
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder qryString = new StringBuilder();
			List dataList = null;
			empolyeeMap = new LinkedHashMap<String, String>();
			try
			{
				qryString.append("SELECT complc.id,emp.empName FROM employee_basic AS emp");
				qryString.append(" INNER JOIN compliance_contacts AS complc ON complc.emp_id=emp.id");
				qryString.append(" WHERE moduleName='ASTM' AND work_status!=1 GROUP BY emp.empName order by empName asc");
				System.out.println("Query String is "+qryString.toString());
				dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] objectCol = null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						objectCol = (Object[]) iterator.next();
						if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
						{
							empolyeeMap.put(objectCol[0].toString(), objectCol[1].toString());
						}
					}
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
			/* Get Outlet Type List */

			List AssociatStorelist = new ArrayList<String>();

			outletTypeList = new HashMap<Integer, String>();
			List colmName1 = new ArrayList<String>();
			colmName1.add("id");
			colmName1.add("associateType");
			AssociatStorelist = cbt.viewAllDataEitherSelectOrAllWithOPutUnder("associatetype", colmName1, connectionSpace);
			if (AssociatStorelist != null && AssociatStorelist.size() > 0)
			{
				for (Iterator iterator = AssociatStorelist.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					outletTypeList.put((Integer) object[0], object[1].toString());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String configureAddAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_configure_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_utility_configuration");

				if (msg != null && msg.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : msg)
					{
						if (gdp.getColomnName().equalsIgnoreCase("utility_Name") || gdp.getColomnName().equalsIgnoreCase("brief") || gdp.getColomnName().equalsIgnoreCase("threshold_Level_Min")
								|| gdp.getColomnName().equalsIgnoreCase("threshold_Level_Max_alert") || gdp.getColomnName().equalsIgnoreCase("threshold_Level_Min_alert") || gdp.getColomnName().equalsIgnoreCase("threshold_Level_Max")
								&& !gdp.getColomnName().equalsIgnoreCase("utility_id") && !gdp.getColomnName().equalsIgnoreCase("Threshold_Level_Min1") && !gdp.getColomnName().equalsIgnoreCase("Threshold_Level_Max1")
								&& !gdp.getColomnName().equalsIgnoreCase("userName"))
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}

					}

					cbt.createTable22("configure_utility", tableColume, connectionSpace);

					if (tableColume != null && tableColume.size() > 0)
					{
						tableColume.clear();
					}
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname("utility_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("outlet_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					cbt.createTable22("configure_outlet_data", tableColume, connectionSpace);

					if (tableColume != null && tableColume.size() > 0)
					{
						tableColume.clear();
					}
					TableColumes ob2 = new TableColumes();
					ob2 = new TableColumes();
					ob2.setColumnname("alert_to");
					ob2.setDatatype("varchar(255)");
					ob2.setConstraint("default NULL");
					tableColume.add(ob2);

					ob1 = new TableColumes();
					ob1.setColumnname("utility_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					cbt.createTable22("configure_alert_to_data", tableColume, connectionSpace);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("userName") && !parmName.equalsIgnoreCase("outlet_Name") && !parmName.equalsIgnoreCase("alert_to")
									&& !parmName.equalsIgnoreCase("associateType") && !parmName.equalsIgnoreCase("userName") && !parmName.equalsIgnoreCase("outlet_id"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}

						}
					}

					int maxId = cbt.insertDataReturnId("configure_utility", insertData, connectionSpace);

					if (maxId > 0)
					{
						if (insertData != null && insertData.size() > 0)
						{
							// Insert Data Into configure_outlet_data
							insertData.clear();
							ob = new InsertDataTable();
							ob.setColName("utility_id");
							ob.setDataName(String.valueOf(maxId));
							insertData.add(ob);

							String[] outletId = request.getParameterValues("outlet_Name");
							for (int i = 0; i < outletId.length; i++)
							{
								ob = new InsertDataTable();
								ob.setColName("outlet_id");
								ob.setDataName(outletId[i].toString());
								insertData.add(ob);

								cbt.insertIntoTable("configure_outlet_data", insertData, connectionSpace);

								insertData.remove(insertData.size() - 1);
							}

							// Insert Data Into Alert Table
							insertData.clear();
							ob = new InsertDataTable();
							ob.setColName("utility_id");
							ob.setDataName(String.valueOf(maxId));
							insertData.add(ob);

							String[] alertToId = request.getParameterValues("alert_to");
							for (int i = 0; i < alertToId.length; i++)
							{
								if (alertToId[i] != null)
								{
									ob = new InsertDataTable();
									ob.setColName("alert_to");
									ob.setDataName(alertToId[i].toString());
									insertData.add(ob);
									cbt.insertIntoTable("configure_alert_to_data", insertData, connectionSpace);
								}
								insertData.remove(insertData.size() - 1);
							}
						}
					}

					if (status)
					{
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				}
				else
				{
					addActionMessage("Oops There is some error in data!!!!");
				}
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

	public String beforeViewConfigureGrid()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setViewConfigureGrid();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setViewConfigureGrid()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewConfigureUtilityGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_configure_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_utility_configuration");
			for (GridDataPropertyView gdp : msg)
			{

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewConfigureUtilityGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewConfigureUtilityDataGrid()
	{
		System.out.println("hi........");

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from configure_utility");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select con.id,con.utility_Name,con.brief,at.associateType,abd.associateName,con.threshold_Level_Min,con.threshold_Level_Max,emp.empName,con.threshold_Level_Min_alert,con.threshold_Level_Max_alert "
							+ " from configure_utility as con " + " INNER JOIN configure_outlet_data AS config ON config.utility_id=con.id " + " INNER JOIN associate_basic_data AS abd ON abd.id=config.outlet_id"
							+ " INNER JOIN associatetype AS at ON at.id=abd.associateType " + " INNER JOIN configure_alert_to_data  AS caltd ON caltd.utility_id  =con.id" + " INNER JOIN compliance_contacts AS complc ON caltd.alert_to=complc.id "
							+ " INNER JOIN employee_basic AS emp  ON complc.emp_id=emp.id " + " WHERE complc.moduleName='ASTM' AND complc.work_status!=1");
					/*
					 * List fieldNames=Configuration.getColomnList(
					 * "mapped_configure_utility_configuration", accountID,
					 * connectionSpace, "configure_utility_configuration"); int
					 * i=0; if(fieldNames!=null && fieldNames.size()>0) {
					 * for(Iterator it=fieldNames.iterator(); it.hasNext();) {
					 * //generating the dynamic query based on selected fields
					 * Object obdata=(Object)it.next(); if(obdata!=null) {
					 * if(i<fieldNames.size()-1)
					 * query.append(obdata.toString()+","); else
					 * query.append(obdata.toString()); } i++; } }
					 */

					System.out.println("query" + query);
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
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

					System.out.println(">>>>>>>>>>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					List fieldNames = Configuration.getColomnList("mapped_configure_utility_configuration", accountID, connectionSpace, "configure_utility_configuration");
					System.out.println("fieldNames size::::" + fieldNames.size());
					System.out.println("fieldNames  ::::" + fieldNames);
					if (data != null && data.size() > 0)
					{
						viewConfigureData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewConfigureData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
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

	public String modifyConfigureUtility()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("configure_utility", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("configure_utility", "id", condtIds.toString(), connectionSpace);
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

	public String dailyUtilityAdd()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			String empId = (String) session.get("empIdofuser").toString().split("-")[1];

			StringBuilder query = new StringBuilder();
			query.append("SELECT distinct outlet.id,outlet.associateName FROM employee_basic AS emp ");
			query.append(" INNER JOIN associate_contact_data AS outletCont ON outletCont.department=emp.deptname ");
			query.append(" INNER JOIN associate_basic_data AS outlet ON outlet.id=outletCont.associateName ");
			query.append(" WHERE emp.id=" + empId);

			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						outlet_Name = object[0].toString();
						outletName = object[1].toString();
					}

				}
			}

			setdailyUtilityAdd();
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setdailyUtilityAdd()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			dailyUtilityColumnText = new ArrayList<ConfigurationUtilBean>();
			dailyUtilityColumnDropdown = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_daily_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_utility_configuration");
			if (msg != null)
			{
				for (GridDataPropertyView gdp : msg)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") || gdp.getColomnName().equalsIgnoreCase("date") || gdp.getColomnName().equalsIgnoreCase("outlet_Name") || gdp.getColomnName().equalsIgnoreCase("start_reading")
							|| gdp.getColomnName().equalsIgnoreCase("end_reading") || gdp.getColomnName().equalsIgnoreCase("total_reading") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName"))

					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}

						dailyUtilityColumnText.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("D") || gdp.getColomnName().equalsIgnoreCase("utility_Name"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						dailyUtilityColumnDropdown.add(obj);
					}

				}
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List utilityName_list = new ArrayList<String>();
			utility_List = new HashMap<Integer, String>();
			List colmName1 = new ArrayList<String>();
			colmName1.add("id");
			colmName1.add("utility_Name");
			utilityName_list = cbt.viewAllDataEitherSelectOrAllWithOPutUnder("configure_utility", colmName1, connectionSpace);
			if (utilityName_list != null && utilityName_list.size() > 0)
			{
				for (Iterator iterator = utilityName_list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					utility_List.put((Integer) object[0], object[1].toString());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	public String getAssociateNameAction()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String userDeptID = null;
			try
			{
				Object object[] = null;

				jsonArr = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT id,associateName FROM associate_basic_data WHERE associateType=" + associateType);
				List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				JSONObject formDetailsJson = new JSONObject();
				if (data2 != null && data2.size() > 0)
				{
					for (Iterator iterator = data2.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", object[0].toString());
							formDetailsJson.put("NAME", object[1].toString());
							jsonArr.add(formDetailsJson);
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

	public String beforeViewdailyGrid()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setViewdailyGrid();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setViewdailyGrid()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewDailyConfigureGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_daily_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_utility_configuration");
			for (GridDataPropertyView gdp : msg)
			{

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewDailyConfigureGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String insertDataFordailyUtility()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_daily_utility_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_utility_configuration");

				if (msg != null && msg.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : msg)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}

					cbt.createTable22("daily_utility", tableColume, connectionSpace);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							System.out.println("parmName:::" + parmName);
							System.out.println("paramValue:::" + paramValue);
							if (paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					status = cbt.insertIntoTable("daily_utility", insertData, connectionSpace);
					System.out.println("status::::" + status);
					if (status)
					{
						sendAlertIfThreshold(request.getParameter("total_reading"), connectionSpace, request.getParameter("utility_Name"));

						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				}
				else
				{
					addActionMessage("Oops There is some error in data!!!!");
				}
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

	private void sendAlertIfThreshold(String totalReading, SessionFactory connectionSpace2, String utilityName)
	{
		System.out.println(":::: inside Thissss");
		try
		{
			int threshold_Level_Min = 0;
			int threshold_Level_Max = 0;
			String empName = null;
			String empMob = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT  threshold_Level_Min,threshold_Level_Max " + " FROM configure_utility " + "WHERE id ='" + utilityName + "' AND threshold_Level_Min_alert='Yes' OR threshold_Level_Max_alert='Yes'";
			System.out.println(":::: " + query);
			List data = cbt.executeAllSelectQuery(query, connectionSpace2);

			System.out.println("data::" + data.size());
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						threshold_Level_Min = Integer.parseInt(object[0].toString());
						threshold_Level_Max = Integer.parseInt(object[1].toString());
					}
				}
			}

			if (totalReading != null && Integer.parseInt(totalReading.trim()) < threshold_Level_Min)
			{
				String query2 = "SELECT emp.empName,emp.mobOne" + " FROM configure_utility AS cu" + " INNER JOIN configure_alert_to_data as cat ON cat.utility_id=cu.id" + " INNER JOIN compliance_contacts as cc ON cc.id= cat.alert_to"
						+ " INNER JOIN employee_basic as emp ON emp.id=cc.emp_id WHERE cu.id='" + utilityName + "'";
				System.out.println(":::::" + query2);
				List data1 = cbt.executeAllSelectQuery(query2, connectionSpace2);
				if (data1 != null && data1.size() > 0)
				{
					for (Iterator iterator = data1.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							empName = object[0].toString();
							empMob = object[1].toString();
						}
					}
				}
				if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && (empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7")))
				{
					String empMsg = "Dear" + empName + " - (" + empMob + ") Your Threshold Level is" + threshold_Level_Min;

					new MsgMailCommunication().addMessage(empMob, empMsg, "", "Pending", "0", "ASTM", connectionSpace);
					System.out.println("empMsg>>>" + empMsg);
				}

			}
			if (totalReading != null && Integer.parseInt(totalReading.trim()) > threshold_Level_Max)
			{
				String query3 = "SELECT emp.empName,emp.mobOne" + " FROM configure_utility AS cu" + " INNER JOIN configure_alert_to_data as cat ON cat.utility_id=cu.id" + " INNER JOIN compliance_contacts as cc ON cc.id= cat.alert_to"
						+ " INNER JOIN employee_basic as emp ON emp.id=cc.emp_id WHERE cu.id='" + utilityName + "'";
				System.out.println(":::::" + query3);
				List data1 = cbt.executeAllSelectQuery(query3, connectionSpace2);
				if (data1 != null && data1.size() > 0)
				{
					for (Iterator iterator = data1.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							empName = object[0].toString();
							empMob = object[1].toString();
						}
					}
				}
				if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && (empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7")))
				{
					String empMsg = "Dear" + empName + " - (" + empMob + ") Your Threshold Level " + threshold_Level_Max;

					new MsgMailCommunication().addMessage(empMob, empMsg, "", "Pending", "0", "ASTM", connectionSpace);
					System.out.println("empMsg>>>" + empMsg);
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public String viewDailyConfigureDataGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from daily_utility");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_daily_utility_configuration", accountID, connectionSpace, "daily_utility_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dynamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{

									if (obdata.toString().equalsIgnoreCase("utility_Name"))
									{
										query.append("CU." + obdata.toString() + ",");
									}
									else if(obdata.toString().equalsIgnoreCase("outlet_Name"))
									{
										query.append("outlet.associateName,");
									}
									else
									{
										query.append("DU." + obdata.toString() + ",");
									}
								}
								else
								{
									query.append("DU." + obdata.toString());
								}

							}
							i++;
						}
					}
					query.append(" from daily_utility AS DU ");
					query.append(" LEFT JOIN configure_utility AS CU ON CU.id=DU.utility_Name");
					query.append(" LEFT JOIN associate_basic_data AS outlet ON outlet.id=DU.outlet_Name");
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" DU." + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" DU." + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" DU." + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" DU." + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" DU." + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					System.out.println("@@@@@@ " + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						viewDailyConfigureData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else
									{
										if(fieldNames.get(k).toString().equalsIgnoreCase("date"))
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										else
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(),"NA");
								}
							}
							Listhb.add(one);
						}
						setViewDailyConfigureData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
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

	public String modifyDailyUtility()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("daily_utility", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("daily_utility", "id", condtIds.toString(), connectionSpace);
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

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<ConfigurationUtilBean> getConfigureUtilityColumnText()
	{
		return configureUtilityColumnText;
	}

	public void setConfigureUtilityColumnText(List<ConfigurationUtilBean> configureUtilityColumnText)
	{
		this.configureUtilityColumnText = configureUtilityColumnText;
	}

	public List<ConfigurationUtilBean> getUtilityColumnDropdown()
	{
		return UtilityColumnDropdown;
	}

	public void setUtilityColumnDropdown(List<ConfigurationUtilBean> utilityColumnDropdown)
	{
		UtilityColumnDropdown = utilityColumnDropdown;
	}

	public List<ConfigurationUtilBean> getDailyUtilityColumnText()
	{
		return dailyUtilityColumnText;
	}

	public void setDailyUtilityColumnText(List<ConfigurationUtilBean> dailyUtilityColumnText)
	{
		this.dailyUtilityColumnText = dailyUtilityColumnText;
	}

	public List<ConfigurationUtilBean> getDailyUtilityColumnDropdown()
	{
		return dailyUtilityColumnDropdown;
	}

	public void setDailyUtilityColumnDropdown(List<ConfigurationUtilBean> dailyUtilityColumnDropdown)
	{
		this.dailyUtilityColumnDropdown = dailyUtilityColumnDropdown;
	}

	public Map<Integer, String> getOutletTypeList()
	{
		return outletTypeList;
	}

	public void setOutletTypeList(Map<Integer, String> outletTypeList)
	{
		this.outletTypeList = outletTypeList;
	}

	public List<GridDataPropertyView> getViewConfigureUtilityGrid()
	{
		return viewConfigureUtilityGrid;
	}

	public void setViewConfigureUtilityGrid(List<GridDataPropertyView> viewConfigureUtilityGrid)
	{
		this.viewConfigureUtilityGrid = viewConfigureUtilityGrid;
	}

	public List<GridDataPropertyView> getViewDailyConfigureGrid()
	{
		return viewDailyConfigureGrid;
	}

	public void setViewDailyConfigureGrid(List<GridDataPropertyView> viewDailyConfigureGrid)
	{
		this.viewDailyConfigureGrid = viewDailyConfigureGrid;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getAssociateType()
	{
		return associateType;
	}

	public void setAssociateType(String associateType)
	{
		this.associateType = associateType;
	}

	public Map<String, String> getEmpolyeeMap()
	{
		return empolyeeMap;
	}

	public void setEmpolyeeMap(Map<String, String> empolyeeMap)
	{
		this.empolyeeMap = empolyeeMap;
	}

	public Map<Integer, String> getUtility_List()
	{
		return utility_List;
	}

	public void setUtility_List(Map<Integer, String> utility_List)
	{
		this.utility_List = utility_List;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getViewDailyConfigureData()
	{
		return viewDailyConfigureData;
	}

	public void setViewDailyConfigureData(List<Object> viewDailyConfigureData)
	{
		this.viewDailyConfigureData = viewDailyConfigureData;
	}

	public List<Object> getViewConfigureData()
	{
		return viewConfigureData;
	}

	public void setViewConfigureData(List<Object> viewConfigureData)
	{
		this.viewConfigureData = viewConfigureData;
	}

	public Boolean getSms()
	{
		return sms;
	}

	public void setSms(Boolean sms)
	{
		this.sms = sms;
	}

	public String getOutletName()
	{
		return outletName;
	}

	public void setOutletName(String outletName)
	{
		this.outletName = outletName;
	}

	public String getOutlet_Name()
	{
		return outlet_Name;
	}

	public void setOutlet_Name(String outlet_Name)
	{
		this.outlet_Name = outlet_Name;
	}

}