package com.Over2Cloud.ctrl.compliance.complTaskMaster;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceTaskAction extends ActionSupport implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String userType = (String) session.get("userTpe");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceTaskAction.class);
	String deptLevel = (String) session.get("userDeptLevel");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> complTaskTxtBox = null;
	private List<ConfigurationUtilBean> taskTypeList = null;
	private Map<String, String> taskTypeDropDown = null;
	private String uniqueId;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private TaskTypePojoBean taskObject = null;
	private String userDeptId;
	private String userDeptName;
	private Map<String, String> allDeptName = null;
	private Map<Integer, String> krGroupList = null;
	private String subGroupId;
	private Map<String, String> krName = null;
	private Map<String, String> accountingMap = null;
	private String dataId;
	private String dataFor;
	private List<ComplianceDashboardBean> dataList;
	private String module;
	private String taskId;

	@Override
	public String execute()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public String beforeTaskAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				accountingMap = new LinkedHashMap<String, String>();
				accountingMap.put("Yes", "Yes");
				accountingMap.put("No", "No");
				allDeptName = new LinkedHashMap<String, String>();
				krGroupList = new LinkedHashMap<Integer, String>();
				taskTypeDropDown = new LinkedHashMap<String, String>();
				if (userType != null && userType.equalsIgnoreCase("M"))
				{
					String query = "SELECT id,deptName FROM department WHERE flag=0 ORDER BY deptName";
					List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								allDeptName.put(object[0].toString(), object[1].toString());
							}
						}
					}

				}
				else
				{
					String[] userData = new ComplianceUniversalAction().getEmpDataByUserName(userName);
					userDeptId = userData[3];
					userDeptName = userData[4];
					taskTypeDropDown = getAllTaskType(userDeptId);
					krGroupList = getAllKRGroup(userDeptId);

				}
				setAddPageDataFields();
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method execute of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void setAddPageDataFields()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> complianceFieldsName = Configuration.getConfigurationData("mapped_compl_task_config", accountID, connectionSpace, "", 0, "table_name", "compl_task_config");
				if (complianceFieldsName != null)
				{
					complTaskTxtBox = new ArrayList<ConfigurationUtilBean>();
					taskTypeList = new ArrayList<ConfigurationUtilBean>();
					ConfigurationUtilBean conUtilBean=null;
					for (GridDataPropertyView gdp : complianceFieldsName)
					{
						conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());
							conUtilBean.setColType(gdp.getColType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							conUtilBean.setValidationType(gdp.getValidationType());
							complTaskTxtBox.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("departName"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());
							conUtilBean.setColType(gdp.getColType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							conUtilBean.setValidationType(gdp.getValidationType());
							taskTypeList.add(conUtilBean);

						}
					}
					conUtilBean = new ConfigurationUtilBean();
					conUtilBean.setField_name("Check List");
					conUtilBean.setField_value("completion");
					conUtilBean.setValidationType("sc");
					conUtilBean.setColType("T");
					conUtilBean.setMandatory(false);
					complTaskTxtBox.add(conUtilBean);
				}
			}
			catch (Exception exp)
			{
				log.error(
						"Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(),
						exp);
				exp.printStackTrace();
			}
		}
		else
		{

		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String complianceTaskAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "SELECT * FROM compliance_task WHERE taskName='" + request.getParameter("taskName") + "' AND departName='" + request.getParameter("departName") + "'";
				List data = cbt.executeAllSelectQuery(query, connectionSpace);
				if (data == null || data.size() == 0)
				{
					List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compl_task_config", accountID, connectionSpace, "", 0, "table_name", "compl_task_config");
					if (statusColName != null)
					{
						// create table query based on configuration
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						TableColumes ob1 = null;
						List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName)
						{
							if(!gdp.getColomnName().equalsIgnoreCase("completion"))
							{
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								if (gdp.getColomnName().equalsIgnoreCase("status"))
								{
									ob1.setConstraint("default 'Active'");
								}
								else
								{
									ob1.setConstraint("default NULL");
								}
								Tablecolumesaaa.add(ob1);
							}
						}
						cbt.createTable22("compliance_task", Tablecolumesaaa, connectionSpace);
						Tablecolumesaaa.clear();
						// Create Table For KR

						ob1 = new TableColumes();
						ob1.setColumnname("taskId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("krId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						cbt.createTable22("compliance_task_KR", Tablecolumesaaa, connectionSpace);
						Tablecolumesaaa.clear();

						// Create Table For Completion Tip

						ob1 = new TableColumes();
						ob1.setColumnname("taskId");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("completion_tip");
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);

						cbt.createTable22("compl_task_completion_tip", Tablecolumesaaa, connectionSpace);

						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("completion") && !Parmname.equalsIgnoreCase("kr_name"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.makeTitle(paramValue));
								insertData.add(ob);
							}
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
						int maxId = cbt.insertDataReturnId("compliance_task", insertData, connectionSpace);
						insertData.clear();
						
						
						if(maxId>0)
						{
							status = true;
							ob = new InsertDataTable();
							ob.setColName("taskId");
							ob.setDataName(String.valueOf(maxId));
							insertData.add(ob);
							
							// insert task id into compliance_task_KR table
							String krId[] = request.getParameterValues("kr_name");
							if(krId!=null && krId.length>0)
							{
								for (int i = 0; i < krId.length; i++)
								{
									ob = new InsertDataTable();
									ob.setColName("krId");
									ob.setDataName(krId[i]);
									insertData.add(ob);
									cbt.insertIntoTable("compliance_task_KR", insertData, connectionSpace);
									
									insertData.remove(insertData.size()-1);
								}
							}
							
							String completionTip[] = request.getParameterValues("completion");
							for (int i = 0; i < completionTip.length; i++)
							{
								if(completionTip[i]!=null && !completionTip[i].equalsIgnoreCase(""))
								{
									ob = new InsertDataTable();
									ob.setColName("completion_tip");
									ob.setDataName(completionTip[i]);
									insertData.add(ob);
									cbt.insertIntoTable("compl_task_completion_tip", insertData, connectionSpace);
									
									insertData.remove(insertData.size()-1);
								}
							}
							
						}
						
						if (status)
						{
							addActionMessage("Task Name Added successfully!!!");
							return SUCCESS;
						}
						else
						{
							addActionMessage("Oops There is some error in data!");
							return SUCCESS;
						}
					}
				}
				else
				{
					addActionMessage(request.getParameter("taskName") + " Task Name Already Exist!!!");
				}

			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String beforeViewComplTask()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setViewProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_compl_task_config", accountID, connectionSpace, "", 0, "table_name", "compl_task_config");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			masterViewProp.add(gpv);
		}
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("completion_tip");
		gpv.setHeadingName("Check List");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("ViewCompletionTip");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("kr_name");
		gpv.setHeadingName("KR");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("ViewKR");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		
		
	}

	@SuppressWarnings("rawtypes")
	public String viewComplTask()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String deptId = null;
				if(userType!=null && !userType.equalsIgnoreCase("M"))
				{
					deptId = new ComplianceUniversalAction().getEmpDataByUserName(userName)[3];
				}
				StringBuilder query1 = new StringBuilder("");
				if (deptId != null && !deptId.equalsIgnoreCase(" "))
				{
					query1.append("select count(*) from compliance_task where status='Active' and departName=" + deptId);
				}
				else
				{
					query1.append("select count(*) from compliance_task where status='Active'");
				}
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_compl_task_config", accountID, connectionSpace, "compl_task_config");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
								if (obdata.toString().equalsIgnoreCase("taskType"))
								{
									query1.append("ty." + obdata.toString() + ",");
								}
								else if (obdata.toString().equalsIgnoreCase("departName"))
								{
									query1.append("dep.deptName , ");
								}
								else
								{
									query1.append("ct." + obdata.toString() + ",");
								}

							else
								query1.append("ct." + obdata.toString()+",");
						}
						i++;
					}
					query.append(query1.substring(0, query1.toString().length()-1));
					query.append(" from compliance_task as ct  ");
					query.append(" INNER JOIN compl_task_type as ty ON ct.taskType = ty.id ");
					query.append(" INNER JOIN department as dep ON ct.departName = dep.id ");
					query.append(" where ct.status='Active' ");
					if (deptId != null && !deptId.equalsIgnoreCase(" "))
					{
						query.append(" and ct.departName=" + deptId);
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
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
					query.append(" ORDER BY taskName ");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else if(!fieldNames.get(k).toString().equalsIgnoreCase("taskType") || !fieldNames.get(k).toString().equalsIgnoreCase("departName"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setRecords(Listhb.size());
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method viewTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewModifyTask()
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("compliance_task", wherClause, condtnBlock, connectionSpace);
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
					StringBuilder query = new StringBuilder("UPDATE compliance_task SET status='InActive' WHERE id IN(" + condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// getAllTaskType
	@SuppressWarnings("rawtypes")
	public Map<String, String> getAllTaskType(String deptId)
	{

		boolean sessionFlag = ValidateSession.checkSession();
		Map<String, String> taskTypeMap = new LinkedHashMap<String, String>();
		if (sessionFlag)
		{
			try
			{
				List lstValue = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder qryString = new StringBuilder();
				qryString.append("select id,taskType from compl_task_type where status='Active' and departName IN (" + deptId + ") order by taskType");
				lstValue = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
				if (lstValue != null && lstValue.size() > 0)
				{
					Object[] object = null;
					for (Iterator iterator = lstValue.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						taskTypeMap.put(object[0].toString(), object[1].toString());
					}
				}
				return taskTypeMap;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getAllTaskType of class " + getClass(), exp);
			}
		}
		return taskTypeMap;
	}

	// getAllKRGroup
	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getAllKRGroup(String deptId)
	{

		boolean sessionFlag = ValidateSession.checkSession();
		Map<Integer, String> krGroupMap = new LinkedHashMap<Integer, String>();
		if (sessionFlag)
		{
			try
			{
				List lstValue = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder qryString = new StringBuilder();
				qryString.append("SELECT id,groupName FROM kr_group_data WHERE dept IN (" + deptId + ") ORDER BY groupName");
				lstValue = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
				if (lstValue != null && lstValue.size() > 0)
				{
					Object[] object = null;
					for (Iterator iterator = lstValue.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						krGroupMap.put((Integer) object[0], object[1].toString());
					}
				}
				return krGroupMap;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getAllTaskType of class " + getClass(), exp);
			}
		}
		return krGroupMap;
	}

	// getTaskTypeDetails
	@SuppressWarnings("rawtypes")
	public String getTaskTypeDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		ComplianceCommonOperation cmnCompl = new ComplianceCommonOperation();
		List taskDetails = null;
		if (sessionFlag)
		{
			try
			{
				if (getUniqueId() != null)
				{
					taskDetails = cmnCompl.getTaskTypeDetails(getUniqueId(), "compliance_task");
					if (taskDetails != null && taskDetails.size() > 0)
					{
						for (Iterator iterator = taskDetails.iterator(); iterator.hasNext();)
						{
							// qryString.append(
							// "select id,taskName,msg,taskBrief,taskType,departName from "
							// );
							taskObject = new TaskTypePojoBean();
							Object[] objectCol = (Object[]) iterator.next();

							if (objectCol[0] != null)
							{
								taskObject.setMsg(objectCol[0].toString());
							}
							else
							{
								taskObject.setMsg("NA");
							}

							if (objectCol[1] != null)
							{
								taskObject.setTaskBrief(objectCol[1].toString());
							}
							else
							{
								taskObject.setTaskBrief("NA");
							}

							if (objectCol[2] != null)
							{
								taskObject.setTaskType(objectCol[2].toString());
							}
							else
							{
								taskObject.setTaskType("NA");
							}
							if (objectCol[3] != null)
							{
								taskObject.setCompletion(objectCol[3].toString());
							}
							else
							{
								taskObject.setCompletion("NA");
							}
						}
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getTaskTypeDetails of class " + getClass(),
						exp);
			}
		}
		else
		{
			returnResult = LOGIN;
			;
		}
		return returnResult;
	}

	public String getKRName()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				krName = new LinkedHashMap<String, String>();
				if (subGroupId != null)
				{
					String query = "SELECT id,kr_name FROM kr_upload_data WHERE subGroupName=" + subGroupId + " ORDER BY kr_name";
					List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
								krName.put(object[0].toString(), object[1].toString());
						}
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}

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

	public String getCompletionTipKR()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(dataFor!=null && dataId!=null)
				{
					StringBuilder query = new StringBuilder();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					ComplianceDashboardBean CDB = null;
					dataList = new ArrayList<ComplianceDashboardBean>();
					if(dataFor.equalsIgnoreCase("KRName") && module==null && !dataId.equalsIgnoreCase("Configure New"))
					{
						query.append("SELECT ct_kr.id AS ctkrid,kr.id AS krid,kr.kr_name,kr.upload1 FROM kr_upload_data AS kr ");
						query.append(" INNER JOIN compliance_task_kr AS ct_kr ON ct_kr.krId=kr.id ");
						query.append(" WHERE ct_kr.taskId="+dataId+" ORDER BY kr.kr_name");
						System.out.println("QUERY IS AS KR ::::::::"+query.toString());
						List tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
						if(tempList!=null && tempList.size()>0)
						{
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								CDB = new ComplianceDashboardBean();
								Object[] object = (Object[]) iterator.next();
								CDB.setId(CUA.getValueWithNullCheck(object[1]));
								CDB.setTaskId(CUA.getValueWithNullCheck(object[0]));
								CDB.setName(CUA.getValueWithNullCheck(object[2]));
								if(object[3]!=null)
								{
									CDB.setDocPath(CUA.getValueWithNullCheck(getDocNameFromPath(object[3].toString())));
									CDB.setOrginalDocPath(object[3].toString());
								}
								
								
								
								dataList.add(CDB);
							}
						}
						
					}
					else if(dataFor.equalsIgnoreCase("CompletionTip") && module==null && !dataId.equalsIgnoreCase("Configure New"))
					{
						query.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId="+dataId+" ORDER BY completion_tip");
						List tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
						if(tempList!=null && tempList.size()>0)
						{
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								CDB = new ComplianceDashboardBean();
								Object[] object = (Object[]) iterator.next();
								CDB.setId(CUA.getValueWithNullCheck(object[0]));
								CDB.setName(CUA.getValueWithNullCheck(object[1]));
								
								dataList.add(CDB);
								
							}
						}
					}
					if(dataFor.equalsIgnoreCase("KRName") && module!=null && module.equalsIgnoreCase("Action"))
					{
						query.append("SELECT ct_kr.id AS ctkrid,kr.id AS krid,kr.kr_name,kr.upload1 FROM kr_upload_data AS kr ");
						query.append(" INNER JOIN compliance_task_kr AS ct_kr ON ct_kr.krId=kr.id ");
						query.append(" INNER JOIN compliance_task AS ct ON ct.id=ct_kr.taskId ");
						query.append(" INNER JOIN compliance_details AS comp ON comp.taskName=ct.id ");
						query.append(" WHERE comp.id="+dataId+" ORDER BY kr.kr_name");
						
						List tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
						if(tempList!=null && tempList.size()>0)
						{
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								CDB = new ComplianceDashboardBean();
								Object[] object = (Object[]) iterator.next();
								CDB.setId(CUA.getValueWithNullCheck(object[1]));
								CDB.setTaskId(CUA.getValueWithNullCheck(object[0]));
								CDB.setName(CUA.getValueWithNullCheck(object[2]));
								if(object[3]!=null)
								{
									CDB.setDocPath(CUA.getValueWithNullCheck(getDocNameFromPath(object[3].toString())));
									CDB.setOrginalDocPath(object[3].toString());
								}
								
								
								
								dataList.add(CDB);
							}
						}
						
					}
					else if(dataFor.equalsIgnoreCase("CompletionTip") && module!=null && module.equalsIgnoreCase("Action"))
					{
						query.append("SELECT ctct.id,ctct.completion_tip FROM compl_task_completion_tip AS ctct ");
						query.append(" INNER JOIN compliance_task AS ct ON ct.id=ctct.taskId "); 
						query.append(" INNER JOIN compliance_details AS comp ON comp.taskName=ct.id "); 
						query.append(" WHERE comp.id="+dataId+" ORDER BY ctct.completion_tip");
						
						List tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
						if(tempList!=null && tempList.size()>0)
						{
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								CDB = new ComplianceDashboardBean();
								Object[] object = (Object[]) iterator.next();
								CDB.setId(CUA.getValueWithNullCheck(object[0]));
								CDB.setName(CUA.getValueWithNullCheck(object[1]));
								
								dataList.add(CDB);
							}
						}
					}
					else if(dataFor.equalsIgnoreCase("CompletionTip") && module!=null && module.equalsIgnoreCase("Report"))
					{
						List tempList = new createTable().executeAllSelectQuery("SELECT checkid FROM compliance_report WHERE id = "+dataId, connectionSpace);
						if(tempList!=null && tempList.size()>0)
						{
							query.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE id IN("+tempList.get(0).toString()+") ORDER BY completion_tip ");
							tempList.clear();
							tempList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
						}
						if(tempList!=null && tempList.size()>0)
						{
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								CDB = new ComplianceDashboardBean();
								Object[] object = (Object[]) iterator.next();
								CDB.setId(CUA.getValueWithNullCheck(object[0]));
								CDB.setName(CUA.getValueWithNullCheck(object[1]));
								
								dataList.add(CDB);
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
	}
	
	public String getDocNameFromPath(String path)
	{
		String docName=null;
		if(path!=null)
		{
			String str=path.substring(path.lastIndexOf("//")+2, path.length());
			docName=str.substring(14,str.length());
		}
		return docName;
	}
	
	public String beforeViewComplTask4KR()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);

				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_compl_task_config", accountID, connectionSpace, "", 0, "table_name", "compl_task_config");
				for (GridDataPropertyView gdp : returnResult)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
				return SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String viewTaskWithoutKR()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder taskIdd = new StringBuilder();
				StringBuilder qurString = new StringBuilder();
				qurString.append("select comp.id,tip.id as tipid,kr.id as kr_id from compliance_task as comp");
				qurString.append(" left join compl_task_completion_tip as tip on comp.id=tip.taskId ");
				qurString.append(" left join compliance_task_kr as kr on comp.id=kr.taskId");
				List dataList=cbt.executeAllSelectQuery(qurString.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (@SuppressWarnings("rawtypes")
					Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[1]==null || object[2]==null)
						{
							taskIdd.append(object[0].toString()+",");
						}
					}
					dataList.clear();
				}
				taskIdd.append("0");
				
				String deptId = null;
				if(userType!=null && !userType.equalsIgnoreCase("M"))
				{
					deptId = new ComplianceUniversalAction().getEmpDataByUserName(userName)[3];
				}
				StringBuilder query1 = new StringBuilder("");
				if (deptId != null && !deptId.equalsIgnoreCase(" "))
				{
					query1.append("select count(*) from compliance_task where status='Active' and id IN("+taskIdd.toString()+") and departName=" + deptId);
				}
				else
				{
					query1.append("select count(*) from compliance_task where status='Active' and id IN("+taskIdd.toString()+")");
				}
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_compl_task_config", accountID, connectionSpace, "compl_task_config");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
								if (obdata.toString().equalsIgnoreCase("taskType"))
								{
									query1.append("ty." + obdata.toString() + ",");
								}
								else if (obdata.toString().equalsIgnoreCase("departName"))
								{
									query1.append("dep.deptName , ");
								}
								else
								{
									query1.append("ct." + obdata.toString() + ",");
								}

							else
								query1.append("ct." + obdata.toString()+",");
						}
						i++;
					}
					query.append(query1.substring(0, query1.toString().length()-1));
					query.append(" from compliance_task as ct  ");
					query.append(" INNER JOIN compl_task_type as ty ON ct.taskType = ty.id ");
					query.append(" INNER JOIN department as dep ON ct.departName = dep.id ");
					query.append(" where ct.status='Active' and ct.id IN("+taskIdd.toString()+") ");
					if (deptId != null && !deptId.equalsIgnoreCase(" "))
					{
						query.append(" and ct.departName=" + deptId);
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
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
					query.append(" ORDER BY taskName ");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else if(!fieldNames.get(k).toString().equalsIgnoreCase("taskType") || !fieldNames.get(k).toString().equalsIgnoreCase("departName"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setRecords(Listhb.size());
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method viewTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	
	public String beforeAddKRCompTipInTask()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				complTaskTxtBox = new ArrayList<ConfigurationUtilBean>();
				krGroupList = new LinkedHashMap<Integer, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder taskIdd = new StringBuilder();
				String qurString="SELECT departName FROM compliance_task WHERE id="+taskId;
				List dataList=cbt.executeAllSelectQuery(qurString, connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					String userDeptId=dataList.get(0).toString();
					krGroupList = getAllKRGroup(userDeptId);
				}
				ConfigurationUtilBean conUtilBean = new ConfigurationUtilBean();
				conUtilBean.setField_name("Completion Tip");
				conUtilBean.setField_value("completion");
				conUtilBean.setValidationType("sc");
				conUtilBean.setColType("T");
				conUtilBean.setMandatory(false);
				complTaskTxtBox.add(conUtilBean);
				
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method viewTaskType of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String addKRComplTipActionAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(taskId!=null && !taskId.equalsIgnoreCase("undefine") && !taskId.equalsIgnoreCase(""))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					TableColumes ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					
					ob1 = new TableColumes();
					ob1.setColumnname("taskId");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);
	
					ob1 = new TableColumes();
					ob1.setColumnname("krId");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);
	
					cbt.createTable22("compliance_task_kr", Tablecolumesaaa, connectionSpace);
					Tablecolumesaaa.clear();
	
					// Create Table For Completion Tip
	
					ob1 = new TableColumes();
					ob1.setColumnname("taskId");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);
	
					ob1 = new TableColumes();
					ob1.setColumnname("completion_tip");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);
	
					cbt.createTable22("compl_task_completion_tip", Tablecolumesaaa, connectionSpace);
					
					ob = new InsertDataTable();
					ob.setColName("taskId");
					ob.setDataName(taskId);
					insertData.add(ob);
					
					// insert task id into compliance_task_KR table
					String krId[] = request.getParameterValues("kr_name");
					
					if(krId!=null && krId.length>0)
					{
						for (int i = 0; i < krId.length; i++)
						{
							System.out.println("Kr Ids "+krId[i]);
							ob = new InsertDataTable();
							ob.setColName("krId");
							ob.setDataName(krId[i]);
							insertData.add(ob);
							status=cbt.insertIntoTable("compliance_task_kr", insertData, connectionSpace);
							
							insertData.remove(insertData.size()-1);
						}
					}
					
					String completionTip[] = request.getParameterValues("completion");
					for (int i = 0; i < completionTip.length; i++)
					{
						if(completionTip[i]!=null && !completionTip[i].equalsIgnoreCase(""))
						{
							ob = new InsertDataTable();
							ob.setColName("completion_tip");
							ob.setDataName(completionTip[i]);
							insertData.add(ob);
							status=cbt.insertIntoTable("compl_task_completion_tip", insertData, connectionSpace);
							
							insertData.remove(insertData.size()-1);
						}
					}
					if (status)
					{
						addActionMessage("Data Added successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
				
				
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
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

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getComplTaskTxtBox()
	{
		return complTaskTxtBox;
	}

	public void setComplTaskTxtBox(List<ConfigurationUtilBean> complTaskTxtBox)
	{
		this.complTaskTxtBox = complTaskTxtBox;
	}

	public Map<String, String> getTaskTypeDropDown()
	{
		return taskTypeDropDown;
	}

	public void setTaskTypeDropDown(Map<String, String> taskTypeDropDown)
	{
		this.taskTypeDropDown = taskTypeDropDown;
	}

	public String getUniqueId()
	{
		return uniqueId;
	}

	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}

	public TaskTypePojoBean getTaskObject()
	{
		return taskObject;
	}

	public void setTaskObject(TaskTypePojoBean taskObject)
	{
		this.taskObject = taskObject;
	}

	public List<ConfigurationUtilBean> getTaskTypeList()
	{
		return taskTypeList;
	}

	public void setTaskTypeList(List<ConfigurationUtilBean> taskTypeList)
	{
		this.taskTypeList = taskTypeList;
	}

	public String getUserDeptId()
	{
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId)
	{
		this.userDeptId = userDeptId;
	}

	public String getUserDeptName()
	{
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName)
	{
		this.userDeptName = userDeptName;
	}

	public Map<String, String> getAllDeptName()
	{
		return allDeptName;
	}

	public void setAllDeptName(Map<String, String> allDeptName)
	{
		this.allDeptName = allDeptName;
	}

	public Map<Integer, String> getKrGroupList()
	{
		return krGroupList;
	}

	public void setKrGroupList(Map<Integer, String> krGroupList)
	{
		this.krGroupList = krGroupList;
	}

	public String getSubGroupId()
	{
		return subGroupId;
	}

	public void setSubGroupId(String subGroupId)
	{
		this.subGroupId = subGroupId;
	}

	public Map<String, String> getKrName()
	{
		return krName;
	}

	public void setKrName(Map<String, String> krName)
	{
		this.krName = krName;
	}

	public Map<String, String> getAccountingMap()
	{
		return accountingMap;
	}

	public void setAccountingMap(Map<String, String> accountingMap)
	{
		this.accountingMap = accountingMap;
	}

	public String getDataId()
	{
		return dataId;
	}

	public void setDataId(String dataId)
	{
		this.dataId = dataId;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public List<ComplianceDashboardBean> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<ComplianceDashboardBean> dataList)
	{
		this.dataList = dataList;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}
 
}
