package com.Over2Cloud.ctrl.MachineOrder;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MachineMaster extends GridPropertyBean implements ServletRequestAware {
	/**
	 * This is for Machine order machine master class
	 * author: Manab 12-06-2015
	 */
	private static final long serialVersionUID = -192253594566464362L;
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> configKeyTextBox = null;
	private List<ConfigurationUtilBean> configKeyTextBox4Machine = null;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private JSONArray jsonList;
	private String searchFields;
	private String SearchValue;
	private List<Object> machineMasterData;
	private String deptFlag;
	private String deptId;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private JSONArray commonJSONArray = new JSONArray();
	private String slNo;
	private String ordType;
	
	
	// For grid header call
	public String machineMasterHeader(){
		return SUCCESS;
	}
	
	// for add page machine master call 
	public String beforeMachineAdd()
	{
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setAddPageMachineKeyword();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageMachineKeyword() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_machine_config");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("machine_master_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_machine_config", accountID, connectionSpace, "", 0, "table_name", "machine_master_configuration");
						configKeyTextBox = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status")
									&& !gdp.getColomnName().equalsIgnoreCase("deactive_by")
									&& !gdp.getColomnName().equalsIgnoreCase("deactive_on")
									&& !gdp.getColomnName().equalsIgnoreCase("created_by")
									&& !gdp.getColomnName().equalsIgnoreCase("created_on")
									&& !gdp.getColomnName().equalsIgnoreCase("sts_details")) {

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}

								configKeyTextBox.add(obj);

							}

						}

					}
				}
			}
			
			// for machine name
			empuery.delete(0, empuery.length());
			empuery.append("select table_name from mapped_machine_name_configuration");
			List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData1.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();

				if (obdata != null) {
					if (obdata.toString().equalsIgnoreCase("machine_name_configuration")) {

						List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_machine_name_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_name_configuration");
						configKeyTextBox4Machine = new ArrayList<ConfigurationUtilBean>();
						for (GridDataPropertyView gdp : offeringLevel1) {
							ConfigurationUtilBean obj = new ConfigurationUtilBean();
							if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status")
									&& !gdp.getColomnName().equalsIgnoreCase("deactive_by")
									&& !gdp.getColomnName().equalsIgnoreCase("deactive_on")
									&& !gdp.getColomnName().equalsIgnoreCase("created_by")
									&& !gdp.getColomnName().equalsIgnoreCase("created_on")
									&& !gdp.getColomnName().equalsIgnoreCase("sts_details")) {

								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1")) {
									obj.setMandatory(true);
								} else {
									obj.setMandatory(false);
								}

								configKeyTextBox4Machine.add(obj);

							}

						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// add Machine Name in db
	public String addMachineName() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_machine_name_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_name_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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

						if (gdp.getColomnName().equalsIgnoreCase("created_by"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("created_on"))
							dateTrue = true;
						
					}
					cbt.createTable22("machine_name", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null) {
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					if (userTrue) {
						ob = new InsertDataTable();
						ob.setColName("created_by");
						ob.setDataName(DateUtil.makeTitle(userName));
						insertData.add(ob);
					}
					if (dateTrue) {
						ob = new InsertDataTable();
						ob.setColName("created_on");
						ob.setDataName(DateUtil.getCurrentDateUSFormat()+"  "+DateUtil.getCurrentTimeHourMin() );
						insertData.add(ob);
					}
					
					status = cbt.insertIntoTable("machine_name", insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Save Successfully!!!");
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
	
	// add machine name with serial number  in db
	public String addMachineMaster() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_machine_config", accountID, connectionSpace, "", 0, "table_name", "machine_master_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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

						if (gdp.getColomnName().equalsIgnoreCase("created_by"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("created_on"))
							dateTrue = true;
						
					}
					cbt.createTable22("machine_master", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null) {
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					if (userTrue) {
						ob = new InsertDataTable();
						ob.setColName("created_by");
						ob.setDataName(DateUtil.makeTitle(userName));
						insertData.add(ob);
					}
					if (dateTrue) {
						ob = new InsertDataTable();
						ob.setColName("created_on");
						ob.setDataName(DateUtil.getCurrentDateUSFormat()+"  "+DateUtil.getCurrentTimeHourMin() );
						insertData.add(ob);
					}
					
					status = cbt.insertIntoTable("machine_master", insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Save Successfully!!!");
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
	
	// for main Grid page direct

	public String beforeMasterDataGridView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				setMachineMasterView();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

		} else {
			return LOGIN;
		}
	}
	// for main keyword column name set
	public void setMachineMasterView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_machine_config", accountID, connectionSpace, "", 0, "table_name", "machine_master_configuration");
				for (GridDataPropertyView gdp : returnResult) {
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());

					masterViewProp.add(gpv);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}
	
	
	// data view in grid for Machine data master
	public String viewMachinedata() {
		String returnValue = ERROR;
		if (ValidateSession.checkSession()) {
			try {
			//	System.out.println(" value " + SearchValue);
			//	System.out.println(" value2 " + searchFields);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from machine_master");

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				// //System.out.println("dataCount:" + dataCount);
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

					List fieldNames = Configuration.getColomnList("mapped_machine_config", accountID, connectionSpace, "machine_master_configuration");
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

					query.append(" from machine_master  ");
					if (!getSearchFields().equalsIgnoreCase("-1")) {
						query.append(" where status ='" + getSearchFields() + "'");
					}

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")) {
						// add search query based on the search operation
						if (!getSearchFields().equalsIgnoreCase("-1")) {
							query.append(" and ");
						} else {
							query.append(" where ");
						}

						if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase("")) {
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals("")) {
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" order by machine asc");
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
						setMachineMasterData(Listhb);
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
	
	// modify Machine data
	public String modifyMachineData() {

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {

			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("machine_master", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update machine_master set status ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	// modify cart settings 
	
	public String modifyMachineCartSet() {

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {

			try {
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("machine_order_cart_settings", wherClause, condtnBlock, connectionSpace);
				} else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuilder sb = new StringBuilder();
					sb.append("update machine_order_cart_settings set status ='Inactive' where id='" + getId() + "'");
					cbt.updateTableColomn(connectionSpace, sb);
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}
	// get list for dept
	public String getAlldept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try
			{
				jsonList = new JSONArray();
				MachineOrderHelper MOH = new MachineOrderHelper();

				if (deptFlag != null && deptFlag.equalsIgnoreCase("dept"))
				{
					List departmentlist = MOH.getServiceDept_SubDept("ORD", connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0)
					{
						for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{

								JSONObject innerobj = new JSONObject();
								innerobj.put("id", object[0]);
								innerobj.put("dept", object[1].toString());
								jsonList.add(innerobj);
							}
						}
					}
				}
				else
				{
					//jsonList = HUA.getAllSubDeptByDeptId(deptId, connectionSpace);
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
	
	
	// get list of Emp

	public String getEmployeebydept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id, emp.empName,contact.level,emp.empLogo,subdept.subdeptname FROM compliance_contacts AS contact");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = contact.forDept_id");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
				query.append(" WHERE contact.moduleName='ORD' AND contact.forDept_id IN(SELECT id from subdepartment where deptid='"+deptId+"') ");
				if (searchString != null && !searchString.equalsIgnoreCase(""))
				{
					query.append(" and (emp.empName like '%" + searchString + "%' OR contact.level like '%" + searchString + "%' OR subdept.subdeptname like '%" + searchString + "%' )");
				}
				query.append(" group by emp.id");
				// System.out.println("Shift Wise List For employee qry::"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[3] != null)
							{
								innerobj.put("logo", object[3].toString());
							}
							else
							{
								innerobj.put("logo", "images/noImage.JPG");
							}
							jsonList.add(innerobj);
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
	
	
	/*public String getMachineDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonList = new JSONArray();
				StringBuffer query1 = new StringBuffer("SELECT  id, machine FROM machine_master GROUP BY machine ");
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataList1 != null && dataList1.size() > 0)
				{
					for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
					{
						query1.delete(0, query1.length());
						Map<String, List> innerobj = new HashMap<String, List>();
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{

							query1.append("SELECT  id, serial_No FROM machine_master WHERE machine LIKE '%"+object[1].toString()+"%' ");
							List dataList2 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
							int i = 0;
							if (dataList2 != null && dataList2.size() > 0)
							{
								List tempList = new ArrayList();
								for (Iterator iterator1 = dataList2.iterator(); iterator1.hasNext();)
								{
									Map<String, String> innerobj1 = new HashMap<String, String>();
									Object[] object2 = (Object[]) iterator1.next();
									if (object2[0] != null && object2[1] != null)
									{
										innerobj1.put("wingid", object2[0].toString());
										innerobj1.put("wingname", object2[1].toString());
										tempList.add(innerobj1);
									}
								}
								innerobj.put(object[1].toString(), tempList);
								jsonList.add(innerobj);
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
	}*/
	
	
	public String getMachineDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonList = new JSONArray();
				if(deptId!=null && deptId.equalsIgnoreCase("OCC"))
				{
				StringBuffer query1 = new StringBuffer("SELECT  id, machine FROM machine_master GROUP BY machine ");
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataList1 != null && dataList1.size() > 0)
				{
					for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
					{
						query1.delete(0, query1.length());
						Map<String, List> innerobj = new HashMap<String, List>();
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{

					 	 	query1.append("SELECT  id, serial_No FROM machine_master ");
					 	 	query1.append(" WHERE machine LIKE '%"+object[1].toString()+"%' ");

							List dataList2 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
							int i = 0;
							if (dataList2 != null && dataList2.size() > 0)
							{
								List tempList = new ArrayList();
								for (Iterator iterator1 = dataList2.iterator(); iterator1.hasNext();)
								{
									Map<String, String> innerobj1 = new HashMap<String, String>();
									Object[] object2 = (Object[]) iterator1.next();
									if (object2[0] != null && object2[1] != null)
									{
										innerobj1.put("wingid", object2[0].toString());
										innerobj1.put("wingname", object2[1].toString());
										tempList.add(innerobj1);
									}
								}
								innerobj.put(object[1].toString(), tempList);
								jsonList.add(innerobj);
							}
						}
					}
				}
				}
				else
				{
					Map<String, List> innerobj = new HashMap<String, List>();
				 	StringBuilder query = new StringBuilder();
				 	if(deptId!=null && deptId.equalsIgnoreCase("CT Scan"))
				 		setDeptId("CTscan");
				 	query.append("SELECT  id, serial_No FROM machine_master ");
		 		 	query.append(" WHERE machine LIKE '%"+deptId+"%' ");
								List dataList2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								int i = 0;
								if (dataList2 != null && dataList2.size() > 0)
								{
									List tempList = new ArrayList();
									for (Iterator iterator1 = dataList2.iterator(); iterator1.hasNext();)
									{
										Map<String, String> innerobj1 = new HashMap<String, String>();
										Object[] object2 = (Object[]) iterator1.next();
										if (object2[0] != null && object2[1] != null)
										{
											innerobj1.put("wingid", object2[0].toString());
											innerobj1.put("wingname", object2[1].toString());
											tempList.add(innerobj1);
										}
									}
									innerobj.put(deptId, tempList);
									jsonList.add(innerobj);
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
	
	// get technician name from mapped machine
	
	public String fetchMachingMapping(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			
			
			try {
				 
				 if(ordType.contains("ULTRA"))
				{
					ordType="ULTRA";
				}
				else if(ordType.contains("CT"))
				{
					ordType="CT";
				}
				//aarif2
				else if(ordType.contains("ECHO"))
				{
					ordType="Echo";
				}
				else{
					ordType="XR";
				}
				
			//System.out.println("ordtype>>>>>>><<<<<<   "+ordType);

				StringBuffer query1 = new StringBuffer();
				query1.append("SELECT emp.id, emp.empName,subdept.subdeptname FROM employee_basic AS emp ");
				query1.append("INNER JOIN compliance_contacts as cc on cc.emp_id=emp.id");
				query1.append(" INNER JOIN emp_machine_mapping AS ewm ON ewm.empName=emp.id ");
				query1.append(" INNER JOIN shift_with_emp_wing AS swew ON ewm.shiftId = swew.id ");
				//query1.append(" INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id` ");
				query1.append(" INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id` ");
				query1.append(" WHERE dept_id= "+new MachineOrderHelper().getdeptIdbyName(ordType, connectionSpace)+" AND swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >'" + DateUtil.getCurrentTime() + "'");
				query1.append(" and ewm.machine='"+slNo+"' and cc.level='1' and cc.work_status='0'");				
			//	System.out.println("query1"+query1);
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataList1 != null && dataList1.size() > 0)
				{
					for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
					{
						query1.delete(0, query1.length());
						JSONObject listObject = new JSONObject();
						Object[] object2 = (Object[]) iterator.next();
						if(ordType!=null && !ordType.equalsIgnoreCase("") && ordType.equalsIgnoreCase("XR"))
						{
							listObject.put("empID", object2[0].toString());
							listObject.put("empName", object2[1].toString());
						}
						else if(ordType!=null && !ordType.equalsIgnoreCase("") && (ordType.equalsIgnoreCase("ULTRA") || ordType.equalsIgnoreCase("CT") || ordType.equalsIgnoreCase("Echo")))
						{
							if(object2[2]!=null && !object2[2].toString().equalsIgnoreCase("")){
								if(object2[2].toString().equalsIgnoreCase("Technician")
										){
									listObject.put("techId", object2[0].toString());
									listObject.put("techName", object2[1].toString());
									listObject.put("dept", object2[2].toString());
								}else if (object2[2].toString().equalsIgnoreCase("Doctor"))
								{
									listObject.put("docId", object2[0].toString());
									listObject.put("docName", object2[1].toString());
									listObject.put("dept", object2[2].toString());
								}
								else if (object2[2].toString().equalsIgnoreCase("CT Scan Doctor"))
								{
									listObject.put("docId", object2[0].toString());
									listObject.put("docName", object2[1].toString());
									listObject.put("dept", object2[2].toString());
								}
								else if (object2[2].toString().equalsIgnoreCase("Echo Doctor"))
								{
									listObject.put("docId", object2[0].toString());
									listObject.put("docName", object2[1].toString());
									listObject.put("dept", object2[2].toString());
								}
								
							
							}
							
						}
						//listObject.put("subDept", object2[2].toString());
						//System.out.println("object2[].toString() >>>>  "+object2[2].toString());
						//System.out.println(" id >> "+object2[0].toString()+"      name>>>>  "+object2[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				returnResult= SUCCESS;
					
					
					
					
					
					
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
		
	}
	
	
	// get all machine names for add machine page
	public String getAllMachineNames()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try
			{
				jsonList = new JSONArray();
				String query=" select id,machineName from machine_name ";
				List machinelist = cbt.executeAllSelectQuery(query, connectionSpace);
				if (machinelist != null && machinelist.size() > 0)
				{
					for (Iterator iterator = machinelist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{

							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("machinename", object[1].toString());
							jsonList.add(innerobj);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//**************************Greater Setter *****************************************//
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox() {
		return configKeyTextBox;
	}

	public void setConfigKeyTextBox(List<ConfigurationUtilBean> configKeyTextBox) {
		this.configKeyTextBox = configKeyTextBox;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getSearchValue() {
		return SearchValue;
	}

	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}

	public List<Object> getMachineMasterData() {
		return machineMasterData;
	}

	public void setMachineMasterData(List<Object> machineMasterData) {
		this.machineMasterData = machineMasterData;
	}

	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
	}

	public JSONArray getJsonList() {
		return jsonList;
	}

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setConfigKeyTextBox4Machine(List<ConfigurationUtilBean> configKeyTextBox4Machine) {
		this.configKeyTextBox4Machine = configKeyTextBox4Machine;
	}

	public List<ConfigurationUtilBean> getConfigKeyTextBox4Machine() {
		return configKeyTextBox4Machine;
	}

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getOrdType() {
		return ordType;
	}

	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}

}