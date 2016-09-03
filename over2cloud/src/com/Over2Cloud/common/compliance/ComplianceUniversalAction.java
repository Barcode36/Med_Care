package com.Over2Cloud.common.compliance;

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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.ComplianceTaskAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceUniversalAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceUniversalAction.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private List<ConfigurationUtilBean> complianceTextBox = null;
	private List<ConfigurationUtilBean> complianceCheckBox = null;
	private List<ConfigurationUtilBean> complianceFile = null;
	private List<ConfigurationUtilBean> complianceTxtArea = null;
	private List<ConfigurationUtilBean> complianceCalender = null;
	private List<ConfigurationUtilBean> complianceTime = null;

	private List<ConfigurationUtilBean> complianceRadio = null;
	private List<ConfigurationUtilBean> complianceDropDown = null;
	private List<ConfigurationUtilBean> complRemindDropDown = null;
	// RD
	private Map<String, String> escalationDropDown = null;
	private Map<String, String> escalationMap = null;
	private Map<String, String> emplMap = null;
	private Map<Integer, String> remindDayMap = null;
	// variables for radio button.
	private Map<String, String> viaFrom;
	private Map<String, String> remindToMap;
	private Map<String, String> frequencyMap;
	private Map<String, String> taskTypeMap;
	private Map<String, String> excelDataMap;
	private Map<String, String> actionType;
	private String subDept;
	private String dept;
	boolean checkdept = false;
	private String deptHierarchy = (String) session.get("userDeptLevel");
	private String deptOrSubDeptId;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private Map<String, String> subDept_DeptLevelName = null;
	private Map<Integer, String> employeeList = new HashMap<Integer, String>();
	private String frequency;
	private String totalReminder;
	private String divId;
	private Map<String, String> escL1Emp = null;
	private String escL1SelectValue;
	private String escL2SelectValue;
	private String escL3SelectValue;
	private String countDays, selectedDate;
	private Map<String, String> taskDetailMap = null;
	private String taskId;
	private String taskType;
	private String dueDate;
	private String minDateValue;
	private String deptId;
	private Map<String, String> workingDayMap = null;

	private Map<String, String> remndDate = null;
	ComplianceDashboardBean remindate = null;
	private JSONArray jsonArr = null;
	private String reminDays;
	private String escName;
	private String divName;
	private Map<Integer, String> escList;

	@Override
	public String execute()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			returnResult = SUCCESS;
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String firstActionMethod()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();

				workingDayMap = new LinkedHashMap<String, String>();
				emplMap = new LinkedHashMap<String, String>();
				escL1Emp = new LinkedHashMap<String, String>();
				deptList = new LinkedHashMap<Integer, String>();
				excelDataMap = new LinkedHashMap<String, String>();
				taskTypeMap = new LinkedHashMap<String, String>();

				List departmentlist = new ArrayList();
				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				/* Get Department Data */
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
				}

				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				Object[] object = null;
				departmentlist = cmnOper.getDataFromTable("department", colmName, wherClause, order, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{

					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
					departmentlist.removeAll(departmentlist);
				}
				ComplianceTaskAction ct = new ComplianceTaskAction();
				String[] userData = new ComplianceUniversalAction().getEmpDataByUserName(userName);
				Map<String,String> tempMap = ct.getAllTaskType(userData[3]);
				taskTypeMap.put("Configure New", "Configure New");
				if(tempMap!=null && tempMap.size()>0)
				{
					for(Map.Entry<String, String> entry : tempMap.entrySet())
					{
						taskTypeMap.put(entry.getKey(), entry.getValue());
					}
				}
				
				// set the fields of Compliance Form
				setAddPageDataFields();
				viaFrom = new LinkedHashMap<String, String>();
				viaFrom.put("SMS", "SMS ");
				viaFrom.put("Mail", "Mail ");
				viaFrom.put("Both", "Both ");
				viaFrom.put("None", "None ");
				setViaFrom(viaFrom);
				// Remind To Map
				remindToMap = new HashMap<String, String>();
				remindToMap.put("remToSelf", "Self ");
				remindToMap.put("remToOther", "Other ");
				remindToMap.put("remToBoth", "Both ");
				setRemindToMap(remindToMap);
				// Frequency Map
				frequencyMap = new LinkedHashMap<String, String>();
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
				frequencyMap.put("O", "Other");
				setFrequencyMap(frequencyMap);
				// Escalation Map
				escalationMap = new LinkedHashMap<String, String>();
				escalationMap.put("Y", "Yes");
				escalationMap.put("N", "No");

				//Apply Working Day
				workingDayMap.put("No", "No");
				workingDayMap.put("Yes", "Yes");
				
				// Action Type Map
				actionType = new LinkedHashMap<String, String>();
				actionType.put("individual", "Individual ");
				actionType.put("group", "Group ");

				String userEmpID = null;
				String deptId = null;
				String[] loggedUserData = getEmpDataByUserName(userName);
				if (loggedUserData != null && loggedUserData.length > 0)
				{
					
						deptId = loggedUserData[3];
						this.deptId = deptId;
						userEmpID = loggedUserData[5];
				}
				if (userEmpID != null)
				{
					emplMap = cmnOper.getComplianceContacts(userEmpID, deptId, "COMPL");
					escL1Emp = cmnOper.getComplianceAllContacts(deptId, "COMPL");
					newExcelData();
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method firstActionMethod of class " + getClass(),
						exp);
				exp.printStackTrace();

			}
			return SUCCESS;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String newExcelData()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String userDeptID = null;
		try
		{
			excelDataMap = new LinkedHashMap<String, String>();
			String[] loggedUserData = getEmpDataByUserName(userName);
			if (loggedUserData != null && loggedUserData.length > 0)
			{
				userDeptID = loggedUserData[3];
			}
			jsonArr = new JSONArray();
			StringBuilder query = new StringBuilder();
			query.append("SELECT cty.id,cty.taskType FROM compl_task_type AS cty INNER JOIN compliance_task AS ctn ON ctn.taskType=cty.id "
					+ "INNER JOIN comp_excelupload_data AS ced ON ced.taskName=ctn.id WHERE ced.configStatus=0 AND ctn.departName=" + userDeptID + " ORDER BY cty.taskType");
			List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data2 != null && data2.size() > 0)
			{
				JSONObject formDetailsJson = new JSONObject();
				Object[] object = null;
				for (Iterator iterator = data2.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", object[0].toString());
						formDetailsJson.put("NAME", object[1].toString());
						excelDataMap.put(object[0].toString(), object[1].toString());
						jsonArr.add(formDetailsJson);
					}
				}
			}
			if (excelDataMap == null)
			{
				excelDataMap.put("-1", "No Data");
			}
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

	}

	public String getComplContacts()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
			try
			{
				String userEmpID = null;
				String deptId = null;
				String[] loggedUserData = getEmpDataByUserName(userName);
				if (loggedUserData != null && loggedUserData.length > 0)
				{
					deptId = loggedUserData[3];
					userEmpID = loggedUserData[5];
				}
				emplMap = cmnOper.getComplianceContacts(userEmpID, deptId, "COMPL");
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

	public void setAddPageDataFields()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		// getting user from session
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> complianceFieldsName = Configuration.getConfigurationData("mapped_compliance_configuration", accountID, connectionSpace, "", 0, "table_name",
						"compliance_configuration");
				if (complianceFieldsName != null)
				{
					complianceTextBox = new ArrayList<ConfigurationUtilBean>();
					complianceFile = new ArrayList<ConfigurationUtilBean>();
					complianceRadio = new ArrayList<ConfigurationUtilBean>();
					complianceDropDown = new ArrayList<ConfigurationUtilBean>();
					complianceTxtArea = new ArrayList<ConfigurationUtilBean>();
					complianceCalender = new ArrayList<ConfigurationUtilBean>();
					complianceTime = new ArrayList<ConfigurationUtilBean>();
					complRemindDropDown = new ArrayList<ConfigurationUtilBean>();
					complianceCheckBox = new ArrayList<ConfigurationUtilBean>();
					escalationDropDown = new LinkedHashMap<String, String>();
					for (GridDataPropertyView gdp : complianceFieldsName)
					{
						ConfigurationUtilBean conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("actionStatus") && !gdp.getColomnName().equalsIgnoreCase("comments")
								&& !gdp.getColomnName().equalsIgnoreCase("actionTaken") && !gdp.getColomnName().equalsIgnoreCase("compid_suffix")
								&& !gdp.getColomnName().equalsIgnoreCase("compid_prefix"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceTextBox.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("R") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceRadio.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceFile.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("C") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceCheckBox.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceDropDown.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("TA") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceTxtArea.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("snooze_date") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceCalender.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("snooze_time") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complianceTime.add(conUtilBean);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("RD") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complRemindDropDown.add(conUtilBean);
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setsubDept_DeptTags(String level)
	{
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
		subDept_DeptLevelName = new LinkedHashMap<String, String>();
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					subDept_DeptLevelName.put(gdv.getColomnName(), gdv.getHeadingName());
				}

			}
		}
		if (level.equals("2"))
		{
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0)
			{
				for (GridDataPropertyView gdv1 : subdept_deptList)
				{
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname"))
					{
						subDept_DeptLevelName.put(gdv1.getColomnName(), gdv1.getHeadingName());
					}

				}
			}
		}
	}

	public String getFieldName(String mapTableName, String masterTableName, String columnName)
	{
		String returnResult = null;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> spareCatgColumnList = Configuration.getConfigurationData(mapTableName, accountID, connectionSpace, "", 0, "table_name", masterTableName);
				if (spareCatgColumnList != null && spareCatgColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : spareCatgColumnList)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							if (gdp.getColomnName().equalsIgnoreCase(columnName))
							{
								returnResult = gdp.getHeadingName();
							}
						}
					}
				}
				else
				{
					addActionMessage(mapTableName + " Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getFieldName of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	public String getReminder()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (frequency != null && dueDate != null && !frequency.equalsIgnoreCase("D"))
				{
					remndDate = new LinkedHashMap<String, String>();
					if (frequency.equalsIgnoreCase("OT"))
					{
						int dateDiff = DateUtil.getNoOfDays(DateUtil.convertDateToUSFormat(dueDate), DateUtil.getCurrentDateUSFormat());
						remndDate.put("maxDate", String.valueOf(dateDiff - 1));
						remndDate.put("minDate", "0");
					}
					else if (frequency.equalsIgnoreCase("W"))
					{
						String newDate = DateUtil.getNewDate("day", -7, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((7 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("BM"))
					{
						String newDate = DateUtil.getNewDate("day", -15, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((15 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("M"))
					{
						String newDate = DateUtil.getNewDate("month", -1, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((30 + (dateDiff))));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("Q"))
					{
						String newDate = DateUtil.getNewDate("month", -3, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((90 + (dateDiff)) + 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("HY"))
					{
						String newDate = DateUtil.getNewDate("month", -6, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((180 + (dateDiff)) + 3));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("Y"))
					{
						String newDate = DateUtil.getNewDate("year", -1, DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((365 + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
					}
					else if (frequency.equalsIgnoreCase("O") && !reminDays.equalsIgnoreCase("") && reminDays!=null)
					{
						System.out.println("frequency :::::::::::"+frequency);
						System.out.println("reminDays :::::::::::"+reminDays);
						String newDate = DateUtil.getNewDate("day",Integer.parseInt(reminDays), DateUtil.convertDateToUSFormat(dueDate));
						int dateDiff = DateUtil.getNoOfDays(newDate, DateUtil.getCurrentDateUSFormat());
						if (minDateValue != null && minDateValue.equalsIgnoreCase("0"))
						{
							minDateValue = String.valueOf(dateDiff);
						}
						remndDate.put("maxDate", String.valueOf((Integer.parseInt(reminDays) + (dateDiff)) - 1));
						remndDate.put("minDate", String.valueOf(minDateValue));
						remndDate.put("minDateValue", String.valueOf(minDateValue));
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

	@SuppressWarnings("rawtypes")
	public String getNextEscMap()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String deptId = null;
				String[] loggedUserData = getEmpDataByUserName(userName);
				if (loggedUserData != null && loggedUserData.length > 0)
				{
					deptId = loggedUserData[3];
				}
				if (deptId != null && !deptId.equalsIgnoreCase(""))
				{
					jsonArr = new JSONArray();
					
					if (escL1SelectValue != null && !escL1SelectValue.equalsIgnoreCase(""))
					{
						setEscName("escalation_level_2");
						divName="l3";
					}
					if (escL2SelectValue != null && !escL2SelectValue.equalsIgnoreCase(""))
					{
						escL1SelectValue = escL1SelectValue + "," + escL2SelectValue;
						setEscName("escalation_level_3");
						divName="l4";
					}
					if (escL3SelectValue != null && !escL3SelectValue.equalsIgnoreCase(""))
					{
						escL1SelectValue = escL1SelectValue + "," + escL3SelectValue;
						setEscName("escalation_level_4");
					}
					
					
					/*if (escL3SelectValue != null && !escL3SelectValue.equalsIgnoreCase(""))
					{
						escL1SelectValue = escL1SelectValue + "," + escL3SelectValue;
						escName="escalation_level_4";
					}
										
					System.out.println(escName);
					System.out.println(escL1SelectValue);
					System.out.println(deptId);*/
					StringBuilder query = new StringBuilder();
					query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
					query.append(" WHERE cc.id NOT IN(" + escL1SelectValue + ") AND forDept_id=" + deptId + " AND moduleName='COMPL' AND work_status!=1 order by empName asc");
					
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
						//	JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						escList=new LinkedHashMap<Integer,String>();
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
							/*	formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArr.add(formDetailsJson);
							*/
								escList.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
					if(getEscName().equals("escalation_level_4"))
					{
						return "lsuccess";
					}
					else
					{
					return SUCCESS;
					}
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

	public String getTotalDay()
	{
		if (selectedDate != null && !selectedDate.equalsIgnoreCase(""))
		{
			countDays = String.valueOf(DateUtil.getNoOfDays(DateUtil.convertDateToUSFormat(selectedDate), DateUtil.getCurrentDateUSFormat()));
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String getTaskDetail()
	{

		String returnString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				taskDetailMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "SELECT cty.taskType,cexd.taskBrief,cexd.msg,cexd.frequency,cexd.dueDate,cexd.dueTime,cexd.taskName,cexd.id,ctn.completion "
						+ "FROM comp_excelupload_data AS cexd INNER JOIN compliance_task AS ctn ON ctn.id=cexd.taskName "
						+ "INNER JOIN compl_task_type AS cty ON cty.id=ctn.taskType WHERE cexd.configStatus=0 AND cexd.taskName='" + taskId + "'";
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object object[] = null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[0] != null)
							taskDetailMap.put("taskType", object[0].toString());
						else
							taskDetailMap.put("taskType", "NA");

						if (object[1] != null)
							taskDetailMap.put("taskBrief", object[1].toString());
						else
							taskDetailMap.put("taskBrief", "NA");

						if (object[2] != null)
							taskDetailMap.put("msg", object[2].toString());
						else
							taskDetailMap.put("msg", "NA");

						if (object[3] != null)
							taskDetailMap.put("frequency", object[3].toString());
						else
							taskDetailMap.put("frequency", "NA");

						if (object[4] != null)
							taskDetailMap.put("dueDate", DateUtil.convertDateToIndianFormat(object[4].toString()));
						else
							taskDetailMap.put("dueDate", "NA");

						if (object[5] != null)
							taskDetailMap.put("dueTime", object[5].toString().substring(0, object[5].toString().length() - 3));
						else
							taskDetailMap.put("dueTime", "NA");

						if (object[6] != null)
							taskDetailMap.put("taskName", object[6].toString());

						if (object[8] != null)
							taskDetailMap.put("completion", object[8].toString());
						else
							taskDetailMap.put("completion", "NA");

						taskDetailMap.put("complExcelDataId", object[7].toString());

					}
				}
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;

	}

	@SuppressWarnings("rawtypes")
	public String getCompContactLevel(String userName)
	{
		List dataList = new ArrayList();
		String level = null;
		try
		{
			userName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT max(level) from compliance_contacts AS cc INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id ");
			builder.append("INNER JOIN useraccount AS uc ON uc.id=eb.useraccountid WHERE uc.userID='" + userName + "' AND cc.work_status='0' AND cc.moduleName='COMPL'");
			dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				if (dataList.get(0) != null)
				{
					level = dataList.get(0).toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return level;
	}

	@SuppressWarnings("rawtypes")
	public String compContactLevel(String userName)
	{
		List dataList = new ArrayList();
		StringBuilder level = new StringBuilder();
		try
		{
			userName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT level as l1 ,o_access,share_with from compliance_contacts AS cc INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id ");
			builder.append("INNER JOIN useraccount AS uc ON uc.id=eb.useraccountid WHERE uc.userID='" + userName + "' AND cc.work_status='0' AND cc.moduleName='COMPL'");
			dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object object[] = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						if (object[0] != null)
						{
							level.append(object[0].toString() + ",");
						}
						else
						{
							level.append("NA" + ",");
						}
						if (object[1] != null)
						{
							level.append(object[1].toString() + ",");
						}
						else
						{
							level.append("NA" + ",");
						}
						if (object[2] != null)
						{
							level.append(object[2].toString());
						}
						else
						{
							level.append("NA");
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return level.toString();
	}

	@SuppressWarnings("rawtypes")
	public String getTaskNameDetails()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//String userDeptID = null;
			try
			{
				Object object[] = null;
				/*String[] loggedUserData = getEmpDataByUserName(userName);
				if (loggedUserData != null && loggedUserData.length > 0)
				{
					userDeptID = loggedUserData[3];
				}*/
				jsonArr = new JSONArray();
				StringBuilder query = new StringBuilder();
				//query.append("SELECT id,taskName FROM compliance_task WHERE taskType=" + taskType + " AND departName=" + userDeptID + " ORDER BY taskName ");
				query.append("SELECT id,taskName FROM compliance_task WHERE ");
				if(deptId!=null && !deptId.equalsIgnoreCase("undefined"))
				{
					query.append("departName ="+deptId+" AND ");
				}
				query.append("taskType=" + taskType + " ORDER BY taskName");
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
				if (divId != null && !divId.equalsIgnoreCase("escelData"))
				{
					formDetailsJson.put("ID", "Configure New");
					formDetailsJson.put("NAME", "Configure New");
					jsonArr.add(formDetailsJson);
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

	/*@SuppressWarnings("rawtypes")
	public List getEmpDataByUserName(String uName, String deptLevel, String moduleName)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id as deptid, dept.deptName,emp.id as empid,emp.city,uac.userType,cc.id from employee_basic as emp"
						+ " inner join compliance_contacts as cc on cc.emp_id=emp.id" + " inner join subdepartment as sdept on emp.subdept=sdept.id"
						+ " inner join department as dept on sdept.deptid=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where cc.moduleName='" + moduleName
						+ "' and uac.userID='" + uName + "' and cc.forDept_id=dept.id ";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName,emp.id as empid,emp.city,cc.id from employee_basic as emp"
						+ " inner join compliance_contacts as cc on cc.emp_id=emp.id" + " inner join department as dept on emp.dept=dept.id"
						+ " inner join useraccount as uac on emp.useraccountid=uac.id where cc.moduleName='" + moduleName + "' and uac.userID='" + uName + "' and cc.forDept_id=dept.id";
			}
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}*/
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] getEmpDetailsByUserName(String moduleName,String userName)
	{
		String[] values =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			StringBuilder query=new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[7];
				Object[] object=(Object[])dataList.get(0);
				values[0]=getValueWithNullCheck(object[0]);
				values[1]=getValueWithNullCheck(object[1]);
				values[2]=getValueWithNullCheck(object[2]);
				values[3]=getValueWithNullCheck(object[3]);
				values[4]=getValueWithNullCheck(object[4]);
				values[5]=getValueWithNullCheck(object[5]);
				values[6]=getValueWithNullCheck(object[6]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	
	

	@SuppressWarnings("unchecked")
	public String[] getEmpDataByUserName(String uName)
	{
		String[] dataArr =null;
		StringBuilder query = new StringBuilder();
		try
		{
			uName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			query.append("select emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid,gi.groupName from employee_basic as emp ");
			query.append(" inner join department as dept on emp.deptname=dept.id ");
			query.append(" inner join groupinfo as gi on emp.groupId=gi.id ");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'");
			List empList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if(empList!=null && empList.size()>0)
			{
				dataArr= new String[7];
				Object[] obArr=(Object[]) empList.get(0);
				dataArr[0]=getValueWithNullCheck(obArr[0]);
				dataArr[1]=getValueWithNullCheck(obArr[1]);
				dataArr[2]=getValueWithNullCheck(obArr[2]);
				dataArr[3]=getValueWithNullCheck(obArr[3]);
				dataArr[4]=getValueWithNullCheck(obArr[4]);
				dataArr[5]=getValueWithNullCheck(obArr[5]);
				dataArr[6]=getValueWithNullCheck(obArr[6]);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataArr;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	
	
	
	
	public List<ConfigurationUtilBean> getComplianceTextBox()
	{
		return complianceTextBox;
	}

	public void setComplianceTextBox(List<ConfigurationUtilBean> complianceTextBox)
	{
		this.complianceTextBox = complianceTextBox;
	}

	public List<ConfigurationUtilBean> getComplianceFile()
	{
		return complianceFile;
	}

	public void setComplianceFile(List<ConfigurationUtilBean> complianceFile)
	{
		this.complianceFile = complianceFile;
	}

	public List<ConfigurationUtilBean> getComplianceCheckBox()
	{
		return complianceCheckBox;
	}

	public void setComplianceCheckBox(List<ConfigurationUtilBean> complianceCheckBox)
	{
		this.complianceCheckBox = complianceCheckBox;
	}

	public List<ConfigurationUtilBean> getComplianceRadio()
	{
		return complianceRadio;
	}

	public void setComplianceRadio(List<ConfigurationUtilBean> complianceRadio)
	{
		this.complianceRadio = complianceRadio;
	}

	public Map<String, String> getViaFrom()
	{
		return viaFrom;
	}

	public void setViaFrom(Map<String, String> viaFrom)
	{
		this.viaFrom = viaFrom;
	}

	public Map<String, String> getRemindToMap()
	{
		return remindToMap;
	}

	public void setRemindToMap(Map<String, String> remindToMap)
	{
		this.remindToMap = remindToMap;
	}

	public Map<String, String> getFrequencyMap()
	{
		return frequencyMap;
	}

	public void setFrequencyMap(Map<String, String> frequencyMap)
	{
		this.frequencyMap = frequencyMap;
	}

	public List<ConfigurationUtilBean> getComplianceDropDown()
	{
		return complianceDropDown;
	}

	public void setComplianceDropDown(List<ConfigurationUtilBean> complianceDropDown)
	{
		this.complianceDropDown = complianceDropDown;
	}

	public List<ConfigurationUtilBean> getComplianceTxtArea()
	{
		return complianceTxtArea;
	}

	public void setComplianceTxtArea(List<ConfigurationUtilBean> complianceTxtArea)
	{
		this.complianceTxtArea = complianceTxtArea;
	}

	public List<ConfigurationUtilBean> getComplianceCalender()
	{
		return complianceCalender;
	}

	public void setComplianceCalender(List<ConfigurationUtilBean> complianceCalender)
	{
		this.complianceCalender = complianceCalender;
	}

	public List<ConfigurationUtilBean> getComplianceTime()
	{
		return complianceTime;
	}

	public void setComplianceTime(List<ConfigurationUtilBean> complianceTime)
	{
		this.complianceTime = complianceTime;
	}

	public Map<String, String> getEscalationMap()
	{
		return escalationMap;
	}

	public void setEscalationMap(Map<String, String> escalationMap)
	{
		this.escalationMap = escalationMap;
	}

	public Map<String, String> getEscalationDropDown()
	{
		return escalationDropDown;
	}

	public void setEscalationDropDown(Map<String, String> escalationDropDown)
	{
		this.escalationDropDown = escalationDropDown;
	}

	public Map<String, String> getTaskTypeMap()
	{
		return taskTypeMap;
	}

	public void setTaskTypeMap(Map<String, String> taskTypeMap)
	{
		this.taskTypeMap = taskTypeMap;
	}

	public Map<String, String> getEmplMap()
	{
		return emplMap;
	}

	public void setEmplMap(Map<String, String> emplMap)
	{
		this.emplMap = emplMap;
	}

	public List<ConfigurationUtilBean> getComplRemindDropDown()
	{
		return complRemindDropDown;
	}

	public void setComplRemindDropDown(List<ConfigurationUtilBean> complRemindDropDown)
	{
		this.complRemindDropDown = complRemindDropDown;
	}

	public Map<Integer, String> getRemindDayMap()
	{
		return remindDayMap;
	}

	public void setRemindDayMap(Map<Integer, String> remindDayMap)
	{
		this.remindDayMap = remindDayMap;
	}

	public String getSubDept()
	{
		return subDept;
	}

	public void setSubDept(String subDept)
	{
		this.subDept = subDept;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public boolean isCheckdept()
	{
		return checkdept;
	}

	public void setCheckdept(boolean checkdept)
	{
		this.checkdept = checkdept;
	}

	public String getDeptOrSubDeptId()
	{
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId)
	{
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public Map<Integer, String> getSubDeptList()
	{
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList)
	{
		this.subDeptList = subDeptList;
	}

	public Map<String, String> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(Map<String, String> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public Map<Integer, String> getEmployeeList()
	{
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList)
	{
		this.employeeList = employeeList;
	}

	public String getTotalReminder()
	{
		return totalReminder;
	}

	public void setTotalReminder(String totalReminder)
	{
		this.totalReminder = totalReminder;
	}

	public String getDivId()
	{
		return divId;
	}

	public void setDivId(String divId)
	{
		this.divId = divId;
	}

	public Map<String, String> getEscL1Emp()
	{
		return escL1Emp;
	}

	public void setEscL1Emp(Map<String, String> escL1Emp)
	{
		this.escL1Emp = escL1Emp;
	}

	public String getEscL1SelectValue()
	{
		return escL1SelectValue;
	}

	public void setEscL1SelectValue(String escL1SelectValue)
	{
		this.escL1SelectValue = escL1SelectValue;
	}

	public String getEscL2SelectValue()
	{
		return escL2SelectValue;
	}

	public void setEscL2SelectValue(String escL2SelectValue)
	{
		this.escL2SelectValue = escL2SelectValue;
	}

	public String getEscL3SelectValue()
	{
		return escL3SelectValue;
	}

	public void setEscL3SelectValue(String escL3SelectValue)
	{
		this.escL3SelectValue = escL3SelectValue;
	}

	public String getCountDays()
	{
		return countDays;
	}

	public void setCountDays(String countDays)
	{
		this.countDays = countDays;
	}

	public String getSelectedDate()
	{
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate)
	{
		this.selectedDate = selectedDate;
	}

	public Map<String, String> getExcelDataMap()
	{
		return excelDataMap;
	}

	public void setExcelDataMap(Map<String, String> excelDataMap)
	{
		this.excelDataMap = excelDataMap;
	}

	public Map<String, String> getTaskDetailMap()
	{
		return taskDetailMap;
	}

	public void setTaskDetailMap(Map<String, String> taskDetailMap)
	{
		this.taskDetailMap = taskDetailMap;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public Map<String, String> getActionType()
	{
		return actionType;
	}

	public void setActionType(Map<String, String> actionType)
	{
		this.actionType = actionType;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public Map<String, String> getRemndDate()
	{
		return remndDate;
	}

	public void setRemndDate(Map<String, String> remndDate)
	{
		this.remndDate = remndDate;
	}

	public ComplianceDashboardBean getRemindate()
	{
		return remindate;
	}

	public void setRemindate(ComplianceDashboardBean remindate)
	{
		this.remindate = remindate;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public Map<String, String> getWorkingDayMap()
	{
		return workingDayMap;
	}

	public void setWorkingDayMap(Map<String, String> workingDayMap)
	{
		this.workingDayMap = workingDayMap;
	}

	public String getReminDays()
	{
		return reminDays;
	}

	public void setReminDays(String reminDays)
	{
		this.reminDays = reminDays;
	}

	public String getDivName()
	{
		return divName;
	}

	public void setDivName(String divName)
	{
		this.divName = divName;
	}

	public String getEscName()
	{
		return escName;
	}

	public void setEscName(String escName)
	{
		this.escName = escName;
	}

	public Map<Integer, String> getEscList()
	{
		return escList;
	}

	public void setEscList(Map<Integer, String> escList)
	{
		this.escList = escList;
	}

}
