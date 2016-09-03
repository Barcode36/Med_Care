package com.Over2Cloud.ctrl.cps.corporate;

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
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionContext;

public class ServiceMaster extends GridPropertyBean implements ServletRequestAware{

	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	String accountID=(String)session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewStandard = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	private List<Object> viewListStandard;
	 
	private Map<String, String> location,serviceName;
	private String id;
	String[] serv_loc;
	String std_pack,cost;
	String location_name;
	private String service_name; 
	List<ConfigurationUtilBean> packageFields;
	private JSONArray jsonArray;
	private String status,serviceID;
	//Standered Packages
	public String beforeStanderedView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				location=new LinkedHashMap<String, String>();
				String query = null;
				query = "select location_name,location_name as loc from cps_location GROUP by location_name ORDER BY location_name ";
				location = new LinkedHashMap<String, String>();
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (query != null)
				{
					dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								if (!location.containsKey(object[0].toString()))
									location.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	//Before add Standered Packages
	public String beforeAddStandardMaster() 
	{

		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				location=new LinkedHashMap<String, String>();
				String query = null;
				query = "select location_name,location_name as loc from cps_location GROUP by location_name ORDER BY location_name ";
				location = new LinkedHashMap<String, String>();
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (query != null)
				{
					dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								if (!location.containsKey(object[0].toString()))
									location.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				setAddPageDataFieldsStandard();
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	public void setAddPageDataFieldsStandard() {
		try
		{
////System.out.println("Faisal");
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_cps_standard_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_standard_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user_name"))
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
					packageFields.add(obj);
					
			}

			
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			}
	}
	public String addStandardMasterDetails()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_cps_standard_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_standard_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName) {

						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("status")) {
							ob1.setConstraint("default 'Active'");
						} else {
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);
						
					}
					cbt.createTable22("cps_standard_packages", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							//System.out.println("parmName :"+parmName+" paramValue :"+paramValue);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !paramValue.equalsIgnoreCase("date")&& !paramValue.equalsIgnoreCase("user_name")&& !paramValue.equalsIgnoreCase("time")
							&& !paramValue.equalsIgnoreCase("location_name")&& !paramValue.equalsIgnoreCase("std_pack")&& !paramValue.equalsIgnoreCase("cost")		
							) {
								 
								 
							}
						}
					}
					 String[] abc=std_pack.trim().split(",");
                	 String[] abc_cost =cost.trim().split(",");
                	 String loc=request.getParameter("location_name");
					if(abc!=null)
					{
						for(int j=0;j<abc.length;j++)
						{
							
							if(abc[j]!=null && abc[j].trim().length()>0)
							{
								ob = new InsertDataTable();
								ob.setColName("location_name");
								ob.setDataName(loc);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("std_pack");
								ob.setDataName(abc[j]);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("cost");
								ob.setDataName(abc_cost[j].trim());
								insertData.add(ob);
								
								
								
								
								ob = new InsertDataTable();
								ob.setColName("user_name");
								ob.setDataName(userName);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("time");
								ob.setDataName(DateUtil.getCurrentTime());
								insertData.add(ob);
								
								status = cbt.insertIntoTable("cps_standard_packages", insertData, connectionSpace);
								insertData.clear();
							}
						}
					}
					
					
					if (status) {
						addActionMessage("Packages Added Successfully!!!");
						return SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data!!!!");
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
		
	}
	
	public String viewStandardMasterDetails(){
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewStandard();
				returnResult=SUCCESS;		
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	void setMasterViewStandard(){

		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewStandard.add(gpv);
		try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_cps_standard_configuration",accountID,connectionSpace,"",0,"table_name","cps_standard_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
							for(GridDataPropertyView gdp:statusColName)
							{
									gpv = new GridDataPropertyView();
									gpv.setColomnName(gdp.getColomnName());
									gpv.setHeadingName(gdp.getHeadingName());
									gpv.setEditable(gdp.getEditable());
									gpv.setSearch(gdp.getSearch());
									gpv.setHideOrShow(gdp.getHideOrShow());
									gpv.setFormatter(gdp.getFormatter());
									gpv.setWidth(gdp.getWidth());
									masterViewStandard.add(gpv);
							}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	// data view in grid for Machine Response master
	public String viewStandardMasterData() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from cps_standard_packages");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null) {
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_cps_standard_configuration", accountID, connectionSpace, "cps_standard_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();) {
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null) {
							if (i < fieldNames.size() - 1) {
								query.append(obdata + ", ");
							} else {
								query.append(obdata);
							}
						}
						i++;
					}

					query.append(" from cps_standard_packages where id!='0'  ");
					
					 if(status!=null && !status.equalsIgnoreCase("-1"))
					 {
						 query.append(" and status='"+status+"'  ");
					 }
					 if(serviceID!=null && !serviceID.equalsIgnoreCase("-1"))
					 {
						 query.append(" and location_name='"+serviceID+"'  ");
					 }
					 if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
						// add search query based on the search operation
						query.append(" and ");

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
 
					query.append(" order by location_name asc");
					//System.out.println("query  "+query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null) {
						for (Iterator it = data.iterator(); it.hasNext();) {
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {
									if (k == 0) {
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else {
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("date")) {
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("time")) {
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
								}
							}

							Listhb.add(one);

						}
						setViewListStandard(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}

				returnValue = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				returnValue = ERROR;
			}
		} else {
			returnValue = LOGIN;
		}
		return returnValue;
	}
	 public String editStandardMasterDataGrid()
	 {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
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
							{
								if (parmName.equalsIgnoreCase("user_name"))
								{
									wherClause.put(parmName, userName);
								}
								else if (parmName.equalsIgnoreCase("date"))
								{
									wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								}
								else if (parmName.equalsIgnoreCase("time"))
								{
									wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
								}
								else
								{
									wherClause.put(parmName, paramValue);
								}
							}
						}
						condtnBlock.put("id", getId());
						boolean status = cbt.updateTableColomn("cps_standard_packages", wherClause, condtnBlock, connectionSpace);
						if (status)
						{
							returnResult = SUCCESS;
						}
					}
					else if (getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						String tempIds[] = getId().split(",");
						for (String H : tempIds)
						{
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							wherClause.put("status", "Inactive");
							condtnBlock.put("id", id);
							cbt.updateTableColomn("cps_standard_packages", wherClause, condtnBlock, connectionSpace);
							returnResult = SUCCESS;
						}
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

	//End
	public String viewServiceMasterHeader() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				serviceName=new LinkedHashMap<String, String>();
				String query = null;
				query = "select service_name,service_name as service from cps_service GROUP by service_name ORDER BY service_name ";
				serviceName = new LinkedHashMap<String, String>();
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (query != null)
				{
					dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								if (!serviceName.containsKey(object[0].toString()))
									serviceName.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	public static List fetchServicesData(SessionFactory connection)
	{  List data = null;
		try {
			
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connection);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	    return data;
	}
	public String fetchService()
	{
		String returnResult = ERROR;
		try
		{
			List data = fetchServicesData(connectionSpace);
			JSONObject job = null;
			jsonArray = new JSONArray();
			
			if (data != null && data.size() > 0)
			{
				job = new JSONObject();
				job.put("ID", "0");
				job.put("NAME", "New");
				jsonArray.add(job);
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("ID", ob[0].toString());
						job.put("NAME", (ob[1].toString()));
						jsonArray.add(job);

					}
				}
			}
			return SUCCESS;
		}
		catch (Exception e) {
			returnResult = ERROR;
			e.printStackTrace();
		}
		return returnResult;
	}
	public String beforeAddServiceMaster(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				location=new LinkedHashMap<String, String>();
				String query = null;
				query = "select location_name,location_name as loc from cps_location GROUP by location_name ORDER BY location_name ";
				location = new LinkedHashMap<String, String>();
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (query != null)
				{
					dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								if (!location.containsKey(object[0].toString()))
									location.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				
				setAddPageDataFields();
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	
	public void setAddPageDataFields() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_cps_service_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_service_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("service_name"))
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
					packageFields.add(obj);
					
			}

			
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			}
	}

	public String addServiceMasterDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData(
								"mapped_cps_service_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"cps_service_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							Tablecolumesaaa.add(ob1);
					}
					
						status=	cbt.createTable22("cps_service", Tablecolumesaaa,connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					String [] packName =new String[0];
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);//
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null &&!Parmname.trim().equals("serv_loc") && !Parmname.trim().equals("__multiselect_serv_loc"))
						{
							if(Parmname.equalsIgnoreCase("service_name"))
							{
								packName=request.getParameterValues(Parmname);
							}
							else
							{
							}
						}
					}
					
					String[] testType = serv_loc;
					String test = "";
					String tabID = "NA"; 
					if (testType != null)
					{
						for (int i = 0; i < testType.length; i++)
						{
							if(!testType[i].equalsIgnoreCase(""))
							{
								test += testType[i]+"#";
							}
						}
					}
					List data = cbt.executeAllSelectQuery("select id,serv_loc from cps_service where service_name='"+request.getParameter("service_name")+"'", connectionSpace);
					if (data.size()>0)
					{
						String str=null;
						for (Iterator it1 = data.iterator(); it1.hasNext();)
						{
						Object[] obdata = (Object[]) it1.next();
						tabID = obdata[0].toString();
						if(obdata[1] !=null)
						{
							str=obdata[1].toString();
						}
						}
						String cc=test+""+str;
						StringBuilder sb = new StringBuilder();
						sb.append("update cps_service set serv_loc= '"+cc+"' where id="+tabID+"");
						status = cbt.updateTableColomn(connectionSpace, sb);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("serv_loc");
						ob.setDataName(test);
						insertData.add(ob);
						
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

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);
						
						for(String pvalue:packName){
								ob = new InsertDataTable();
								ob.setColName("service_name");
								ob.setDataName(pvalue);
								insertData.add(ob);
								if(pvalue!=null && pvalue.trim().length()>0 )
								status = cbt.insertIntoTable("cps_service",insertData, connectionSpace);
								insertData.remove(ob);
						}
						insertData.clear();
					}
					if (status)
					{
						addActionMessage("Service Added successfully!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
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
	
	public String viewServiceMasterDetails()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
				returnResult=SUCCESS;		
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	 void setMasterViewProps(){

		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_cps_service_configuration",accountID,connectionSpace,"",0,"table_name","cps_service_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
							for(GridDataPropertyView gdp:statusColName)
							{
									gpv = new GridDataPropertyView();
									gpv.setColomnName(gdp.getColomnName());
									gpv.setHeadingName(gdp.getHeadingName());
									gpv.setEditable(gdp.getEditable());
									gpv.setSearch(gdp.getSearch());
									gpv.setHideOrShow(gdp.getHideOrShow());
									gpv.setFormatter(gdp.getFormatter());
									gpv.setWidth(gdp.getWidth());
									masterViewProp.add(gpv);
							}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	 public String viewServiceMasterData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from cps_service");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
				Object obdata = (Object) it.next();
				count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords()) to = getRecords();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from cps_service as cp ");

				List fieldNames = Configuration.getColomnList("mapped_cps_service_configuration", accountID, connectionSpace, "cps_service_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
									queryTemp.append("cp." + obdata.toString() + ",");
								}
					}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where cp.id!='0' ");
						
				if(status!=null && !status.equalsIgnoreCase("-1"))
				{
					query.append(" and cp.status='"+status+"'");
				}
				if(serviceID!=null && !serviceID.equalsIgnoreCase("-1"))
				{
					query.append(" and cp.service_name='"+serviceID+"'");
				}
				//System.out.println("query "+query);
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
				// add search query based on the search operation
				query.append(" and ");

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
				
				query.append(" order by cp.serv_loc ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
				Object[] obdata = (Object[]) it.next();
				
					for (int k = 0; k < obdata[1].toString().split("#").length; k++)
					{
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("serv_loc", obdata[1].toString().split("#")[k]);
						one.put("service_name", obdata[2].toString());
						one.put("userName", obdata[3].toString());
						one.put("date", DateUtil.convertDateToIndianFormat(obdata[4].toString()));
						one.put("time",obdata[5]);
						one.put("status",obdata[6]);
						
						Listhb.add(one);
					}
					
				}
					
				}
				setViewList(Listhb);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				return "success";
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
	 }
	 public String editServiceMasterDataGrid()
	 {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
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
							{
								if (parmName.equalsIgnoreCase("userName"))
								{
									wherClause.put(parmName, userName);
								}
								else if (parmName.equalsIgnoreCase("date"))
								{
									wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								}
								else if (parmName.equalsIgnoreCase("time"))
								{
									wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
								}
								else
								{
									wherClause.put(parmName, paramValue);
								}
							}
						}
						condtnBlock.put("id", getId());
						boolean status = cbt.updateTableColomn("cps_service", wherClause, condtnBlock, connectionSpace);
						if (status)
						{
							returnResult = SUCCESS;
						}
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
	 public String deleteLocation()
	 {
		 String returnResult = ERROR;
		 CommonOperInterface cbt = new CommonConFactory().createInterface();
			String tabID = "NA"; 
			try
			{
				List data = cbt.executeAllSelectQuery("select id,serv_loc from cps_service where service_name='"+id+"'", connectionSpace);
				if (data != null)
				{
					String[] str=null;
				for (Iterator it = data.iterator(); it.hasNext();)
				{
				Object[] obdata = (Object[]) it.next();
				tabID = obdata[0].toString();
				if(obdata[1] !=null)
				{
					str=obdata[1].toString().split("#");
				}
				}
				String temp = "";
				for(int i=0; i<str.length; i++)
				{
					
					if(location_name.trim().equalsIgnoreCase(str[i].toString()))
					{
					}
					else
					{
						//System.out.println(" else  "+str[i]);
						if(temp.contains("#")){
						temp = temp+str[i]+"#";
						}
						else{
							temp = str[i]+"#";
						}
					}
					
				}
				StringBuilder sb = new StringBuilder();
				sb.append("update cps_service set serv_loc= '"+temp+"' where id="+tabID+"");
				boolean chk =  cbt.updateTableColomn(connectionSpace, sb);
				}
				returnResult=SUCCESS;		
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		
			return returnResult;
		
	 }
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getViewList() {
		return viewList;
	}

	public void setViewList(List<Object> viewList) {
		this.viewList = viewList;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ConfigurationUtilBean> getPackageFields() {
		return packageFields;
	}

	public void setPackageFields(List<ConfigurationUtilBean> packageFields) {
		this.packageFields = packageFields;
	}

	public Map<String, String> getLocation() {
		return location;
	}

	public void setLocation(Map<String, String> location) {
		this.location = location;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String serviceName) {
		service_name = serviceName;
	}

	public String[] getServ_loc() {
		return serv_loc;
	}

	public void setServ_loc(String[] servLoc) {
		serv_loc = servLoc;
	}
	public List<Object> getViewListStandard() {
		return viewListStandard;
	}
	public void setViewListStandard(List<Object> viewListStandard) {
		this.viewListStandard = viewListStandard;
	}
	public List<GridDataPropertyView> getMasterViewStandard() {
		return masterViewStandard;
	}
	public void setMasterViewStandard(List<GridDataPropertyView> masterViewStandard) {
		this.masterViewStandard = masterViewStandard;
	}
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public String getStd_pack() {
		return std_pack;
	}
	public void setStd_pack(String stdPack) {
		std_pack = stdPack;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, String> getServiceName() {
		return serviceName;
	}
	public void setServiceName(Map<String, String> serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String locationName) {
		location_name = locationName;
	}
	
	
	
}