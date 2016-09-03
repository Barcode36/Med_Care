package com.Over2Cloud.ctrl.compliance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.compliance.activityboard.ComplianceActivityBoard;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.TaskTypePojoBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpCommingComplianceAction extends ActionSupport implements ServletRequestAware
{
	/*
	 * Harit
	 */
	private static final long serialVersionUID = 9020231504575415301L;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private HttpServletRequest request;
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
	private String currentDay;
	private String currentWeek;
	private String currentMonth;
	private String status;
	private String complID;
	private String actionTaken;
	private String dbName;
	private String empId;
	private Map<String, String> dataMap;
	private Map<String, String> takeActionStatus = new LinkedHashMap<String, String>();;
	public File takeActionDoc;
	private String takeActionDocContentType;
	private String takeActionDocFileName;
	public File takeActionDoc1;
	private String takeActionDoc1ContentType;
	private String takeActionDoc1FileName;
	public File takeActionDoc2;
	private String takeActionDoc2ContentType;
	private String takeActionDoc2FileName;

	private String attach;
	private String taskName;
	private String nextDueDate;
	private String departId;
	private String data4;
	private String frequency;
	private String totalOrMissed;
	Map<String, String> currentCompl = new HashMap<String, String>();
	TaskTypePojoBean complDetails;
	Map<String, String> checkListMap = new LinkedHashMap<String, String>();
	private String budgetary;
	private String actualExpenses;
	private String checkid;
	private String dueDate;
	private String dueTime;

	public String execute() throws Exception
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			return SUCCESS;
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeUpCommingDues()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			StringBuilder heading = new StringBuilder();
			heading.append("View>>");
			heading.append("Current");
			if (getCurrentDay().equalsIgnoreCase("1"))
			{
				heading.append(" ");
				heading.append("Day");
			}
			else if (getCurrentWeek().equalsIgnoreCase("1"))
			{
				heading.append(" ");
				heading.append("Week");
			}
			else if (getCurrentMonth().equalsIgnoreCase("1"))
			{
				heading.append(" ");
				heading.append("Month");
			}
			setViewProp();
			returnResult = SUCCESS;
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeDeptWiseView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			setViewProp();
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
	public String beforeTakeActionOnCompliance()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			if (getComplID() != null)
			{
				String customFrqOn= null;
				ComplianceCommonOperation cmpl = new ComplianceCommonOperation();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List complDetailList = cmpl.getExistComplDetails(getComplID(), connectionSpace);
				String frequency = null, currentStatus = null, remindDays=null;
				if (complDetailList != null && complDetailList.size() > 0)
				{
					Object[] objectCol = null;
					dataMap = new LinkedHashMap<String, String>();

					for (Iterator iterator = complDetailList.iterator(); iterator.hasNext();)
					{
						// complDetails = new TaskTypePojoBean();
						objectCol = (Object[]) iterator.next();

						if (objectCol[4] != null && objectCol[4].toString() != "")
							dataMap.put("Department", objectCol[4].toString());
						else
							dataMap.put("Department", "NA");

						if (objectCol[0] != null && objectCol[0].toString() != "")
							dataMap.put("Task Type", objectCol[0].toString());
						else
							dataMap.put("Task Type", "NA");

						if (objectCol[1] != null && objectCol[1].toString() != "")
							dataMap.put("Task Brief", objectCol[1].toString());
						else
							dataMap.put("Task Brief", "NA");

						if (objectCol[6] != null && objectCol[6].toString() != "")
						{
							frequency = objectCol[6].toString();
							dataMap.put("Frequency", new ComplianceReminderHelper().getFrequencyName(frequency));
						}
						else
							dataMap.put("Frequency", "NA");

						if (objectCol[5] != null && objectCol[5].toString() != "")
						{
							dueTime = objectCol[5].toString();
						}

						if (objectCol[2] != null && objectCol[2].toString() != "")
						{
							dataMap.put("Due Date", DateUtil.convertDateToIndianFormat(objectCol[2].toString()) + ", " + dueTime);
							dueDate = DateUtil.convertDateToIndianFormat(objectCol[2].toString());
						}
						else
							dataMap.put("Due Date", "NA");

						if (objectCol[7] != null && objectCol[7].toString() != "")
							currentStatus = objectCol[7].toString();
						dataMap.put("Current Status", currentStatus);

						if (objectCol[12] != null && objectCol[12].toString() != "")
							dataMap.put("Current Level", "Level " + objectCol[12].toString());
						if (frequency.equalsIgnoreCase("D"))
						{
							// dataMap.put("Frequency", "Daily");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("W"))
						{
							// dataMap.put("Frequency", "Weekly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("M"))
						{
							// dataMap.put("Frequency", "Monthly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("BM"))
						{
							// dataMap.put("Frequency", "Bi-Monthly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("Q"))
						{
							// dataMap.put("Frequency", "Quaterly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("HY"))
						{
							// dataMap.put("Frequency", "Half Yearly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("Y"))
						{
							// dataMap.put("Frequency", "Yearly");
							dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate))));
							nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate)));
						}
						else if (frequency.equalsIgnoreCase("O"))
						{
							// dataMap.put("Frequency", "Other");
							
							if(objectCol[10]!=null && !objectCol[10].equals(" "))
							{
								customFrqOn = objectCol[10].toString().split("#")[0];
								remindDays = objectCol[10].toString().split("#")[1];
							}
							if(customFrqOn.equalsIgnoreCase("D"))
							{
								dataMap.put("Days", remindDays);
								dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate))));
								nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate)));
							}
							else if(customFrqOn.equalsIgnoreCase("M"))
							{
								dataMap.put("Months", remindDays);
								dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate))));
								nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate)));
							}
							else if(customFrqOn.equalsIgnoreCase("Y"))
							{
								dataMap.put("Years", remindDays);
								dataMap.put("Next Due", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate))));
								nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", Integer.parseInt(remindDays), DateUtil.convertDateToUSFormat(dueDate)));
							}
						}
						String query11 = "SELECT remind_date,remind_time FROM compliance_reminder WHERE compliance_id='" + complID + "' AND remind_date BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + dueDate
								+ "' AND status!='Done' AND reminder_code IN('D','R') ORDER BY remind_date ASC LIMIT 1";
						List remind = cbt.executeAllSelectQuery(query11, connectionSpace);
						if (remind != null && !remind.isEmpty())
						{
							Object[] obj = (Object[]) remind.get(0);
							dataMap.put("Reminder", DateUtil.convertDateToIndianFormat(obj[0].toString()) + ", " + obj[1].toString());
						}
						else
						{
							dataMap.put("Reminder", "NA");
						}
						if (objectCol[13] != null && objectCol[13].toString() != "")
							dataMap.put("Next Escalation On", DateUtil.convertDateToIndianFormat(objectCol[13].toString()) + ", " + objectCol[14].toString());
						else
							dataMap.put("Next Escalation On", "NA");

						if (objectCol[12] != null && objectCol[12].toString() != "" && objectCol[15] != null)
						{
							if (objectCol[12] != null && objectCol[12].toString().equalsIgnoreCase("1") && objectCol[15] != null)
							{
								StringBuilder empName = new StringBuilder();
								String contactId = objectCol[15].toString().replace("#", ",").substring(0, (objectCol[15].toString().length() - 1));
								String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
								List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
								Object object = null;
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									object = (Object) iterator1.next();
									empName.append(object.toString() + ", ");
								}
								dataMap.put("Next Escalation To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else if (objectCol[12] != null && objectCol[12].toString().equalsIgnoreCase("2") && objectCol[16] != null)
							{
								StringBuilder empName = new StringBuilder();
								String contactId = objectCol[16].toString().replace("#", ",").substring(0, (objectCol[16].toString().length() - 1));
								String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
								List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
								Object object = null;
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									object = (Object) iterator1.next();
									empName.append(object.toString() + ", ");
								}
								dataMap.put("Next Escalation To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else if (objectCol[12] != null && objectCol[12].toString().equalsIgnoreCase("3") && objectCol[17] != null)
							{
								StringBuilder empName = new StringBuilder();
								String contactId = objectCol[17].toString().replace("#", ",").substring(0, (objectCol[17].toString().length() - 1));
								String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
								List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
								Object object = null;
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									object = (Object) iterator1.next();
									empName.append(object.toString() + ", ");
								}
								dataMap.put("Next Escalation To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else if (objectCol[12] != null && objectCol[12].toString().equalsIgnoreCase("4") && objectCol[18] != null)
							{
								StringBuilder empName = new StringBuilder();
								String contactId = objectCol[18].toString().replace("#", ",").substring(0, (objectCol[18].toString().length() - 1));
								String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
								List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
								Object object = null;
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									object = (Object) iterator1.next();
									empName.append(object.toString() + ", ");
								}
								dataMap.put("Next Escalation To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else
							{
								dataMap.put("Next Escalation To", "NA");
							}
						}

						if (objectCol[3] != null && objectCol[3].toString() != "")
						{
							StringBuilder empName = new StringBuilder();
							String contactId = objectCol[3].toString().replace("#", ",").substring(0, (objectCol[3].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							Object object = null;
							for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
							{
								object = (Object) iterator1.next();
								empName.append(object.toString() + ", ");
							}
							dataMap.put("Alloted To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						else
						{
							dataMap.put("Alloted To", "NA");
						}

						if (objectCol[8] != null && !objectCol[8].toString().equalsIgnoreCase(""))
						{
							dataMap.put("Budgetary", objectCol[8].toString());
							budgetary = objectCol[8].toString();
						}
						else
						{
							dataMap.put("Budgetary", "0.00");
						}
						if (objectCol[11] != null && objectCol[11].toString() != "")
						{
							dataMap.put("Ownership", DateUtil.makeTitle(objectCol[11].toString()));
						}
						else
						{
							dataMap.put("Ownership", "NA");
						}
						taskName=new ComplianceActivityBoard().getValueWithNullCheck(objectCol[9]);
						dataMap.put("Document 1", new ComplianceActivityBoard().getValueWithNullCheck(objectCol[19]));
						dataMap.put("Document 2", new ComplianceActivityBoard().getValueWithNullCheck(objectCol[20]));
						if (objectCol[9] != null)
						{
							String query = "SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId=" + objectCol[9].toString() + " ORDER BY completion_tip";
							List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
								{
									Object[] object = (Object[]) iterator2.next();
									if (object[0] != null && object[1] != null)
										checkListMap.put(object[0].toString(), object[1].toString());
								}
							}
						}
					}
					if (frequency != null && currentStatus != null && frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Reschedule", "Reschedule");
					}
					else if (frequency != null && currentStatus != null && !frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Recurring", "Recurring & Done");
						takeActionStatus.put("Reschedule", "Reschedule");
					}

				}
				returnResult = SUCCESS;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void setViewProp()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_compliance_configuration", accountID, connectionSpace, "", 0, "table_name", "compliance_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (!gdp.getColomnName().equals("remMsg") || !gdp.getColomnName().equals("reminder"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
				}
			}

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionStatus");
			gpv.setHeadingName("Status");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionTaken");
			gpv.setHeadingName("Take Action");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("complTakeAction");
			masterViewProp.add(gpv);

			// PRINTING COMPLIANCE
			gpv = new GridDataPropertyView();
			gpv.setColomnName("printCompliance");
			gpv.setHeadingName("Print");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("printCurrentCompliance");
			masterViewProp.add(gpv);
		}
	}

	@SuppressWarnings("unchecked")
	public String getUpCommingDues()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceDBOperations dbOper = new ComplianceDBOperations();
				Set<Integer> cmpIds = new TreeSet<Integer>();
				List data = null;
				List fieldNames = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (getCurrentDay() != null && !getCurrentDay().equalsIgnoreCase("") && getCurrentDay().equalsIgnoreCase("1"))
				{
					cmpIds = dbOper.getCompliancesIdsByDate("CurrentDay");
				}
				else if (getCurrentWeek() != null && !getCurrentWeek().equalsIgnoreCase("") && getCurrentWeek().equalsIgnoreCase("1"))
				{
					cmpIds = dbOper.getCompliancesIdsByDate("CurrentWeek");
				}
				else if (getCurrentMonth() != null && !getCurrentMonth().equalsIgnoreCase("") && getCurrentMonth().equalsIgnoreCase("1"))
				{
					cmpIds = dbOper.getCompliancesIdsByDate("CurrentMonth");
				}
				if (dataCount != null && dataCount.size() > 0)
				{
					if (cmpIds != null)
					{
						for (Integer id : cmpIds)
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
							if (to > getRecords())
								to = getRecords();

							StringBuilder query = new StringBuilder("");
							query.append("select ");
							fieldNames = Configuration.getColomnList("mapped_compliance_configuration", accountID, connectionSpace, "compliance_configuration");
							fieldNames.remove("reminder1");
							fieldNames.remove("reminder2");
							fieldNames.remove("reminder3");
							fieldNames.add("actionStatus");
							fieldNames.add("actionTaken");

							int i = 0;
							for (Iterator it = fieldNames.iterator(); it.hasNext();)
							{
								Object obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < fieldNames.size() - 1)
										query.append(obdata.toString() + ",");
									else
										query.append(obdata.toString());
								}
								i++;
							}
							query.append(" from compliance_details");
							if (getCurrentDay() != null || getCurrentWeek() != null || getCurrentMonth() != null)
							{
								query.append(" where ");
								query.append("id");
								query.append(" = ");
								query.append("'");
								query.append(id);
								query.append("'");
							}
							if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
							{
								if (getSearchField().equalsIgnoreCase("dueDate") || getSearchField().equalsIgnoreCase("fromDate"))
								{
									setSearchString(DateUtil.convertDateToUSFormat(getSearchString()));
								}
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
							query.append(" and actionStatus='Pending'");
							query.append(" OR actionStatus='Partially Pending'");
							query.append(" limit " + from + "," + to);
							data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (data != null)
							{
								Object[] obdata = null;
								for (Iterator it = data.iterator(); it.hasNext();)
								{
									obdata = (Object[]) it.next();
									Map<String, Object> one = new HashMap<String, Object>();
									for (int k = 0; k < fieldNames.size(); k++)
									{
										if (obdata[k] != null)
										{
											if (k == 0)
											{
												if (fieldNames.get(k).toString().equals("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if (fieldNames.get(k).toString().equals("time"))
												{
													one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, obdata[k].toString().length() - 3));
												}
												else
												{
													one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
												}
											}
											else
											{
												if (fieldNames.get(k).toString().equals("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if (fieldNames.get(k).toString().equals("time"))
												{
													one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, obdata[k].toString().length() - 3));
												}
												else if (fieldNames.get(k).toString().equals("dueDate"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if (fieldNames.get(k).toString().equals("fromDate"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else
												{
													one.put(fieldNames.get(k).toString(), obdata[k].toString());
												}
											}
										}
									}
									Listhb.add(one);
								}
								setMasterViewList(Listhb);
								setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							}
						}
					}
				}
				returnResult = SUCCESS;
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

	@SuppressWarnings(
	{ "unchecked", "static-access", "rawtypes" })
	public String takeActionOnCompliance()
	{
		String returnResult = ERROR;
		System.out.println("INSIDE METHOD");
		System.out.println("krDoc ::: "+attach);
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String newVartualSnoozeTime = "00:00", newSnoozeDate = DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")), newSnoozeTime = request.getParameter("snoozeTime");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				boolean doneRemindFlag = false;
				String renameFilePath = null;
				List remindDateList = null;
				String dueDate = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compliance_report_config", accountID, connectionSpace, "", 0, "table_name", "compliance_report_config");

				if (statusColName != null)
				{
					TableColumes ob1;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
						{
							ob1.setConstraint("default '0'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						Tablecolumesaaa.add(ob1);
					}
					ob1 = new TableColumes();
					ob1.setColumnname("krDoc");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					cbt.createTable22("compliance_report", Tablecolumesaaa, connectionSpace);
				}
				if (actionTaken != null && !actionTaken.equals("-1"))
				{
					String compSeries = null;
					List prefix_suffixData = cbt.executeAllSelectQuery("SELECT compid_prefix,compid_suffix,dueDate FROM compliance_details WHERE id=" + complID, connectionSpace);

					if (prefix_suffixData != null && prefix_suffixData.size() > 0)
					{
						for (Iterator iterator = prefix_suffixData.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							compSeries = object[0].toString() + object[1].toString();
							dueDate = object[2].toString();
						}

					}
					List dateList = cbt.executeAllSelectQuery("SELECT id,reminder_code,remind_date FROM compliance_reminder WHERE reminder_code='D' OR reminder_code='R' AND compliance_id=" + complID, connectionSpace);
					if (dateList != null && dateList.size() > 0)
					{
						remindDateList = new ArrayList();
						for (Iterator iterator2 = dateList.iterator(); iterator2.hasNext();)
						{
							Object object2[] = (Object[]) iterator2.next();
							if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R") && object2[2] != null)
							{
								remindDateList.add(object2[2].toString());

							}
						}
					}

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					String comments = request.getParameter("comments");
					HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
					Map<String, Object> setVariables;
					Map<String, Object> whereCondition;
					if (actionTaken.equalsIgnoreCase("Done"))
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", complID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Done");
						boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("compliance_id", complID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Done");
							doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
						}
					}
					else if (actionTaken.equalsIgnoreCase("Recurring"))
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", complID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Done");
						boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("compliance_id", complID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Done");
							doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
						}
					}
					else if (actionTaken.equalsIgnoreCase("Snooze"))
					{

						WorkingHourAction WHA = new WorkingHourAction();

						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate"))))
						{
							newVartualSnoozeTime = request.getParameter("snoozeTime");
						}
						else
						{
							newVartualSnoozeTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")), request.getParameter("snoozeTime"));
						}
						List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, newVartualSnoozeTime, "00:00", "Y", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), "COMPL");
						if (dateTime != null && dateTime.size() > 0)
						{
							newSnoozeDate = dateTime.get(0).toString();
							newSnoozeTime = dateTime.get(1).toString();
						}

						List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", complID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Snooze");
						setVariables.put("snooze_date", newSnoozeDate);
						setVariables.put("snooze_time", newSnoozeTime);
						boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
						// String
						// duDate=DateUtil.convertDateToUSFormat(request.getParameter("dueDate"));
						// String duTime=request.getParameter("dueTime");
						// String
						// szDate=DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate"));
						// String szTime=request.getParameter("snoozeTime");
						/*
						 * boolean dateCompStatus =
						 * DateUtil.compareDateTime(duDate
						 * +" "+duTime,szDate+" "+szTime); if(dateCompStatus) {
						 * String str[] = getNewEscDate(complID,szDate,szTime);
						 * if(str!=null && str.length>0) { setVariables = new
						 * HashMap<String, Object>(); whereCondition = new
						 * HashMap<String, Object>(); whereCondition.put("id",
						 * complID); whereCondition.put("escalation", "Y");
						 * setVariables.put("escalate_date", newSnoozeDate);
						 * setVariables.put("escalate_time", newSnoozeTime);
						 * helpdeskHelper
						 * .updateTableColomn("compliance_details",
						 * setVariables, whereCondition, connectionSpace); } }
						 */

						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("compliance_id", complID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Snooze");
							doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
						}
						ob = new InsertDataTable();
						ob.setColName("reminder_name");
						ob.setDataName("Snooze");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("reminder_code");
						ob.setDataName("S");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("compliance_id");
						ob.setDataName(complID);
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("reminder_status");
						ob.setDataName("0");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("due_date");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("due_time");
						ob.setDataName(request.getParameter("dueTime"));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("remind_date");
						ob.setDataName(newSnoozeDate);
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("remind_time");
						ob.setDataName(newSnoozeTime);
						insertData1.add(ob);
						cbt.insertIntoTable("compliance_reminder", insertData1, connectionSpace);

					}
					else if (actionTaken.equalsIgnoreCase("Reschedule"))
					{
						remindDateList = new ArrayList();
						List data = cbt.executeAllSelectQuery("SELECT escalation,l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration FROM compliance_details WHERE id=" + complID, connectionSpace);
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", complID);
						setVariables.put("dueDate", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
						setVariables.put("dueTime", request.getParameter("rescheduleTime"));
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object object[] = (Object[]) iterator.next();
								if (object[0] != null && !object[0].toString().equalsIgnoreCase("N"))
								{
									if (object[1] != null && !object[1].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new String[2];
										WorkingHourAction WHA = new WorkingHourAction();
										String date = DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date"));
										String time = request.getParameter("rescheduleTime");
										List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[1].toString(), "00:00", "Y", date, time, "COMPL");
										escDateTime[0] = dateTime.get(0);
										escDateTime[1] = dateTime.get(1);

										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "1");
									}
									else if (object[2] != null && !object[2].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new String[2];
										WorkingHourAction WHA = new WorkingHourAction();
										String date = DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date"));
										String time = request.getParameter("rescheduleTime");
										List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[2].toString(), "00:00", "Y", date, time, "COMPL");
										escDateTime[0] = dateTime.get(0);
										escDateTime[1] = dateTime.get(1);

										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "2");
									}
									else if (object[3] != null && !object[3].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new String[2];
										WorkingHourAction WHA = new WorkingHourAction();
										String date = DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date"));
										String time = request.getParameter("rescheduleTime");
										List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[3].toString(), "00:00", "Y", date, time, "COMPL");
										escDateTime[0] = dateTime.get(0);
										escDateTime[1] = dateTime.get(1);

										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "3");
									}
									else if (object[4] != null && !object[4].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new String[2];
										WorkingHourAction WHA = new WorkingHourAction();
										String date = DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date"));
										String time = request.getParameter("rescheduleTime");
										List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[4].toString(), "00:00", "Y", date, time, "COMPL");
										escDateTime[0] = dateTime.get(0);
										escDateTime[1] = dateTime.get(1);

										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "4");
									}
									setVariables.put("actionStatus", "Pending");
									setVariables.put("actionTaken", "Pending");
									setVariables.put("comments", " ");
								}
								boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
								if (doneFlag)
								{
									List data1 = cbt.executeAllSelectQuery("SELECT id,reminder_code,remind_interval FROM compliance_reminder WHERE reminder_code='D' OR reminder_code='R' AND compliance_id=" + complID, connectionSpace);
									if (data1 != null && data1.size() > 0)
									{
										for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
										{
											Object object2[] = (Object[]) iterator2.next();
											setVariables = new HashMap<String, Object>();
											whereCondition = new HashMap<String, Object>();
											whereCondition.put("id", object2[0].toString());
											setVariables.put("due_date", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
											setVariables.put("due_time", request.getParameter("rescheduleTime"));
											setVariables.put("remind_time", request.getParameter("rescheduleTime"));
											if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R"))
											{
												String newRemindDate = DateUtil.getNewDate("day", -Integer.valueOf(object2[2].toString()), DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
												setVariables.put("remind_date", newRemindDate);
												remindDateList.add(newRemindDate);

											}
											else
											{
												setVariables.put("remind_date", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
											}
											setVariables.put("reminder_status", "0");
											setVariables.put("status", "Pending");
											doneFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
										}
									}
								}
							}
						}
					}
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("dueDate") && !parmName.equals("snoozeDate") && !parmName.equals("reschuedule_date") && !parmName.equals("takeActionDoc1")
								&& !parmName.equals("takeActionDoc2") && !parmName.equals("attach") && !parmName.equals("checkid") && !parmName.equals("actualExpenses") && !parmName.equals("budgetary") && !parmName.equals("__checkbox_checkid"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmName);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					if (takeActionDocFileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + takeActionDocFileName;
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + takeActionDocFileName;
						String str = renameFilePath.replace("//", "/");
						// renameFilePath=DateUtil.removeSpace(renameFilePath);
						// storeFilePath=DateUtil.removeSpace(storeFilePath);
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(takeActionDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("document_takeaction");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}

					if (takeActionDoc1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + takeActionDoc1FileName;
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + takeActionDoc1FileName;
						String str = renameFilePath.replace("//", "/");
						// renameFilePath=DateUtil.removeSpace(renameFilePath);
						// storeFilePath=DateUtil.removeSpace(storeFilePath);
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(takeActionDoc1, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("document_action_1");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}

					if (takeActionDoc2FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + takeActionDoc2FileName;
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + takeActionDoc2FileName;
						String str = renameFilePath.replace("//", "/");
						// renameFilePath=DateUtil.removeSpace(renameFilePath);
						// storeFilePath=DateUtil.removeSpace(storeFilePath);
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(takeActionDoc2, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("document_action_2");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					//krDocFileName=attach;
					if (attach != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + attach;
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + attach;
						String str = renameFilePath.replace("//", "/");
						
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									// if file doesnt exists, then create it
									if (!theFile.exists()) {
										theFile.createNewFile();
									}
									FileUtils.copyFile(theFile, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
									ob = new InsertDataTable();
									ob.setColName("krDoc");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
							}
						}
					}
					if (remindDateList != null && remindDateList.size() > 0)
					{
						for (int i = 0; i < remindDateList.size(); i++)
						{
							ob = new InsertDataTable();
							ob.setColName("reminder" + (i + 1));
							ob.setDataName(remindDateList.get(i));
							insertData.add(ob);
						}
					}
					ob = new InsertDataTable();
					ob.setColName("complainceId");
					ob.setDataName(compSeries);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("dueDate");
					ob.setDataName(dueDate);
					insertData.add(ob);

					if (actionTaken != null && actionTaken.equalsIgnoreCase("snooze"))
					{
						ob = new InsertDataTable();
						ob.setColName("snoozeDate");
						ob.setDataName(newSnoozeDate);
						insertData.add(ob);
					}

					if (actionTaken != null && actionTaken.equalsIgnoreCase("Reschedule"))
					{
						ob = new InsertDataTable();
						ob.setColName("rescheduleDate");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
						insertData.add(ob);
					}

					ob = new InsertDataTable();
					ob.setColName("budgetary");
					if (budgetary != null)
						ob.setDataName(budgetary);
					else
						ob.setDataName(0);

					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("actual_expenses");
					if (actualExpenses != null)
						ob.setDataName(actualExpenses);
					else
						ob.setDataName(0);

					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("difference");
					if (budgetary != null && actualExpenses != null && !actualExpenses.equalsIgnoreCase("") && !budgetary.equalsIgnoreCase("NA") && !budgetary.equalsIgnoreCase(""))
					{
						int totalDiff = Integer.parseInt(budgetary) - Integer.parseInt(actualExpenses);
						ob.setDataName(totalDiff);
					}
					else
					{
						ob.setDataName(0);
					}
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("actionTakenDate");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("actionTakenTime");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					if (checkid != null && !checkid.equalsIgnoreCase(""))
					{

						ob = new InsertDataTable();
						ob.setColName("checkid");
						ob.setDataName(checkid);
						insertData.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("checkid");
						ob.setDataName("0");
						insertData.add(ob);
					}
					doneRemindFlag = cbt.insertIntoTable("compliance_report", insertData, connectionSpace);
					boolean status = beforeMailConfiguration(complID, renameFilePath, actionTaken, connectionSpace);
					if (doneRemindFlag)
					{
						addActionMessage("Successfully done !!!!");
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String[] getNewEscDate(String complId, String snoozeDate, String snoozeTime)
	{
		String[] newEscArr = new String[2];
		if (snoozeDate != null && snoozeTime != null)
		{
			String query = "SELECT current_esc_level,l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration FROM compliance_details WHERE escalation='Y' AND id=" + complId;
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && Integer.parseInt(object[0].toString()) < 5)
					{
						if (object[0].toString().equals("1") && !object[1].toString().equals("00:00"))
						{
							String newDate = DateUtil.newdate_AfterTime(snoozeDate, snoozeTime, object[1].toString());
							if (newDate != null)
							{
								newEscArr[0] = newDate.split("#")[0];
								newEscArr[1] = newDate.split("#")[1];
							}
						}
						else if (object[0].toString().equals("2") && !object[2].toString().equals("00:00"))
						{
							String newDate = DateUtil.newdate_AfterTime(snoozeDate, snoozeTime, object[2].toString());
							if (newDate != null)
							{
								newEscArr[0] = newDate.split("#")[0];
								newEscArr[1] = newDate.split("#")[1];
							}
						}
						else if (object[0].toString().equals("3") && !object[3].toString().equals("00:00"))
						{
							String newDate = DateUtil.newdate_AfterTime(snoozeDate, snoozeTime, object[3].toString());
							if (newDate != null)
							{
								newEscArr[0] = newDate.split("#")[0];
								newEscArr[1] = newDate.split("#")[1];
							}
						}
						else if (object[0].toString().equals("4") && !object[4].toString().equals("00:00"))
						{
							String newDate = DateUtil.newdate_AfterTime(snoozeDate, snoozeTime, object[4].toString());
							if (newDate != null)
							{
								newEscArr[0] = newDate.split("#")[0];
								newEscArr[1] = newDate.split("#")[1];
							}
						}

					}
				}
			}
		}
		return newEscArr;
	}

	@SuppressWarnings("rawtypes")
	public boolean beforeMailConfiguration(String compId, String docPath, String actionStatus, SessionFactory connection)
	{
		boolean status = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			List data1 = cbt.executeAllSelectQuery("SELECT cty.taskType,ctn.taskName,cd.dueDate,cd.dueTime,cd.frequency,cd.reminder_for,cd.comments,cd.snooze_date,cd.snooze_time "
					+ "FROM compliance_details AS cd INNER JOIN compliance_task AS ctn ON ctn.id=cd.taskName INNER JOIN compl_task_type AS cty " + "ON cty.id=ctn.taskType WHERE cd.id=" + compId, connectionSpace);
			if (data1 != null && data1.size() > 0)
			{
				Object[] object = null;
				String taskType = null, taskName = null, dueDate = null, dueTime = null, frequency = null, reminderFor = null, comments = null, snoozeDate = null, snoozeTime = null;
				for (Iterator iterator = data1.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[0] != null)
						taskType = object[0].toString();

					if (object[1] != null)
						taskName = object[1].toString();

					if (object[2] != null)
						dueDate = object[2].toString();

					if (object[3] != null)
						dueTime = object[3].toString();

					if (object[4] != null)
						frequency = object[4].toString();

					if (object[5] != null)
						reminderFor = object[5].toString();

					if (object[6] != null)
						comments = object[6].toString();
					else
						comments = "NA";

					if (object[7] != null)
						snoozeDate = object[7].toString();

					if (object[8] != null)
						snoozeTime = object[8].toString();

					if (frequency.equalsIgnoreCase("OT"))
						frequency = "One Time";
					else if (frequency.equalsIgnoreCase("D"))
						frequency = "Daily";
					else if (frequency.equalsIgnoreCase("W"))
						frequency = "Weekly";
					else if (frequency.equalsIgnoreCase("M"))
						frequency = "Monthly";
					else if (frequency.equalsIgnoreCase("BM"))
						frequency = "Bi-Monthly";
					else if (frequency.equalsIgnoreCase("Q"))
						frequency = "Quaterly";
					else if (frequency.equalsIgnoreCase("HY"))
						frequency = "Half Yearly";
					else if (frequency.equalsIgnoreCase("Y"))
						frequency = "Yearly";

					if (reminderFor != null)
					{
						if (actionStatus != null && actionStatus.equalsIgnoreCase("Snooze"))
						{
							actionStatus = actionStatus + " (Till " + DateUtil.convertDateToIndianFormat(snoozeDate) + " " + snoozeTime + ")";
						}

						String mailSubject = null, mailTitle = null;
						mailSubject = "Operation Task Action Alert:";
						mailTitle = mailSubject;
						String msgText = "Operation Task Action Alert: For Task Name: " + taskName + ", Action Status " + actionStatus + ", Comments: " + comments + ".";
						dueDate = DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;

						String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));
						String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName,cc.id " + "AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON "
								+ "emp.id=cc.emp_id INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status=0 " + "AND cc.id IN(" + contactId + ")";

						List data2 = new createTable().executeAllSelectQuery(query2, connection);
						if (data2 != null && data2.size() > 0)
						{
							String mobileNo = null, emailId = null, empName = null;
							for (Iterator iterator2 = data2.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								StringBuilder str = new StringBuilder();
								String mappedTeam = null;

								if (object2[0] != null)
									mobileNo = object2[0].toString();

								if (object2[1] != null)
									emailId = object2[1].toString();

								if (object2[2] != null)
									empName = object2[2].toString();

								if (object2[3] != null)
									contactId = object2[3].toString();

								for (Iterator iterator3 = data2.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[5].toString().equalsIgnoreCase(object3[5].toString()))
									{
										str.append(object3[2].toString() + ", ");
									}
								}
								if (str != null && str.toString().length() > 0)
								{
									mappedTeam = str.toString().substring(0, str.toString().length() - 2);
								}
								if (mappedTeam == null)
									mappedTeam = "You";
								else
									mappedTeam = mappedTeam + " and You";

								if (mobileNo != null && mobileNo.toString() != "")
									cmnHelper.addMessage(empName, object2[4].toString(), mobileNo, msgText, "", "Pending", "0", "Compliance", connection);

								String mailText = getConfigMail(taskName, taskType, dueDate, frequency, mappedTeam, actionStatus, comments, empName, contactId, mailTitle);
								if (emailId != null && emailId.toString() != "")
									cmnHelper.addMail(empName, object2[4].toString(), emailId, mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);
							}
						}
					}
				}
			}
			status = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings(
	{ "static-access", "unused", "rawtypes" })
	public String beforeTakeActionByMail()
	{
		try
		{
			String dbId = new Cryptography().decrypt(dbName, DES_ENCRYPTION_KEY);
			SessionFactory connection = new ConnectionHelper().getSessionFactory(dbId);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List complDetailList = new ComplianceCommonOperation().getExistComplDetails(complID, connection);
			String frequency = null, currentStatus = null, dueDate = null, dueTime = null;
			takeActionStatus = new LinkedHashMap<String, String>();
			if (complDetailList != null && complDetailList.size() > 0)
			{

				Object[] objectCol = null;
				dataMap = new LinkedHashMap<String, String>();
				takeActionStatus = new LinkedHashMap<String, String>();

				for (Iterator iterator = complDetailList.iterator(); iterator.hasNext();)
				{
					complDetails = new TaskTypePojoBean();
					objectCol = (Object[]) iterator.next();
					if (objectCol[0] != null && objectCol[0].toString() != "")
						dataMap.put("Task Name", objectCol[0].toString());
					else
						dataMap.put("Task Name", "NA");

					if (objectCol[1] != null && objectCol[1].toString() != "")
						dataMap.put("Task Brief", objectCol[1].toString());
					else
						dataMap.put("Task Brief", "NA");

					if (objectCol[2] != null && objectCol[2].toString() != "")
					{
						dataMap.put("Due Date", DateUtil.convertDateToIndianFormat(objectCol[2].toString()));
						dueDate = DateUtil.convertDateToIndianFormat(objectCol[2].toString());
					}
					else
						dataMap.put("Due Date", "NA");

					if (objectCol[5] != null && objectCol[5].toString() != "")
					{
						dataMap.put("Due Time", objectCol[5].toString());
						dueTime = objectCol[5].toString();
					}
					else
						dataMap.put("Due Time", "NA");

					if (objectCol[6] != null && objectCol[6].toString() != "")
						frequency = objectCol[6].toString();

					if (objectCol[7] != null && objectCol[7].toString() != "")
						currentStatus = objectCol[7].toString();

					if (objectCol[3] != null && objectCol[3].toString() != "")
					{
						StringBuilder empName = new StringBuilder();
						String contactId = objectCol[3].toString().replace("#", ",").substring(0, (objectCol[3].toString().length() - 1));
						String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
						List data1 = cbt.executeAllSelectQuery(query2, connection);
						Object object = null;
						for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
						{
							object = (Object) iterator1.next();
							empName.append(object.toString() + ", ");
						}
						dataMap.put("Remind To", empName.toString().substring(0, empName.toString().length() - 2));
					}
					else
					{
						dataMap.put("Remind To", "NA");
					}

					if (objectCol[4] != null && objectCol[4].toString() != "")
						dataMap.put("Department Name", objectCol[4].toString());
					else
						dataMap.put("Department Name", "NA");

					if (objectCol[8] != null && objectCol[8].toString() != "")
					{
						dataMap.put("Budgetary", objectCol[8].toString());
						budgetary = objectCol[8].toString();
					}
					else
					{
						dataMap.put("Budgetary", "0.00");
					}

					if (objectCol[9] != null)
					{
						String query = "SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId=" + objectCol[9].toString() + " ORDER BY completion_tip";
						List dataList = new createTable().executeAllSelectQuery(query, connection);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
							{
								Object[] object = (Object[]) iterator2.next();
								if (object[0] != null && object[1] != null)
									checkListMap.put(object[0].toString(), object[1].toString());
							}
						}
					}

				}
				if (frequency != null && currentStatus != null && frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
				{
					takeActionStatus.put("Done", "Done");
					/*
					 * takeActionStatus.put("Snooze", "Snooze");
					 * takeActionStatus.put("Reschedule", "Reschedule");
					 */
				}
				else if (frequency != null && currentStatus != null && !frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
				{
					takeActionStatus.put("Done", "Done");
					takeActionStatus.put("Recurring", "Recurring & Done");
					/*
					 * takeActionStatus.put("Snooze", "Snooze");
					 * takeActionStatus.put("Reschedule", "Reschedule");
					 */
				}
				if (frequency.equalsIgnoreCase("OT"))
				{
					dataMap.put("Frequency", "One-Time");
				}
				else if (frequency.equalsIgnoreCase("D"))
				{
					dataMap.put("Frequency", "Daily");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("W"))
				{
					dataMap.put("Frequency", "Weekly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("M"))
				{
					dataMap.put("Frequency", "Monthly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("BM"))
				{
					dataMap.put("Frequency", "Bi-Monthly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("Q"))
				{
					dataMap.put("Frequency", "Quaterly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("HY"))
				{
					dataMap.put("Frequency", "Half Yearly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate)));
				}
				else if (frequency.equalsIgnoreCase("Y"))
				{
					dataMap.put("Frequency", "Yearly");
					dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate))));
					nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate)));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// takeActionOnCompliance
	@SuppressWarnings(
	{ "static-access", "unchecked", "rawtypes", "unused" })
	public String takeActionOnComplianceByMail()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String dbId = new Cryptography().decrypt(dbName, DES_ENCRYPTION_KEY);
			String emp_id = new Cryptography().decrypt(empId, DES_ENCRYPTION_KEY);
			SessionFactory connection = new ConnectionHelper().getSessionFactory(dbId);
			String actionTakenBy = null;
			String renameFilePath = null;
			List uidList = cbt.executeAllSelectQuery("SELECT userAC.userID FROM employee_basic AS emp INNER JOIN useraccount AS userAC ON userAC.id=emp.useraccountid WHERE emp.id=" + emp_id, connection);
			if (uidList != null && uidList.size() > 0)
			{
				actionTakenBy = uidList.get(0).toString();
			}
			boolean doneRemindFlag = false;

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compliance_report_config", "", connection, "", 0, "table_name", "compliance_report_config");
			if (statusColName != null)
			{
				TableColumes ob1;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
					{
						ob1.setConstraint("default '0'");
					}
					else
					{
						ob1.setConstraint("default NULL");
					}
					Tablecolumesaaa.add(ob1);
				}
				cbt.createTable22("compliance_report", Tablecolumesaaa, connection);
			}

			if (actionTaken != null && !actionTaken.equals("-1"))
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				String comments = request.getParameter("comments");
				HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
				Map<String, Object> setVariables;
				Map<String, Object> whereCondition;
				if (actionTaken.equalsIgnoreCase("Done"))
				{
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Done");
					boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("compliance_id", complID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Done");
						doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
					}
				}
				else if (actionTaken.equalsIgnoreCase("Recurring"))
				{
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Done");
					boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("compliance_id", complID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Done");
						doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
					}
				}
				else if (actionTaken.equalsIgnoreCase("Snooze"))
				{
					List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Snooze");
					setVariables.put("snooze_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					setVariables.put("snooze_time", request.getParameter("snoozeTime"));
					boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connection);

					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complID);
					whereCondition.put("escalation", "Y");
					setVariables.put("escalate_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					setVariables.put("escalate_time", request.getParameter("snoozeTime"));
					helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connection);

					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("compliance_id", complID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Snooze");
						doneRemindFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
					}
					ob = new InsertDataTable();
					ob.setColName("reminder_name");
					ob.setDataName("Snooze");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("reminder_code");
					ob.setDataName("S");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("compliance_id");
					ob.setDataName(complID);
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("reminder_status");
					ob.setDataName("0");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("due_date");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("due_time");
					ob.setDataName(request.getParameter("dueTime"));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("remind_date");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("remind_time");
					ob.setDataName(request.getParameter("snoozeTime"));
					insertData1.add(ob);
					cbt.insertIntoTable("compliance_reminder", insertData1, connection);
				}
				else if (actionTaken.equalsIgnoreCase("Reschedule"))
				{
					List data = cbt.executeAllSelectQuery("SELECT escalation,l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration FROM compliance_details WHERE id=" + complID, connection);
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", complID);
					setVariables.put("dueDate", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
					setVariables.put("dueTime", request.getParameter("rescheduleTime"));
					if (data != null && data.size() > 0)
					{
						Object object[] = null;
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object[0] != null && !object[0].toString().equalsIgnoreCase("N"))
							{
								if (object[1] != null && !object[1].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")), request.getParameter("rescheduleTime"), object[1].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "1");
								}
								else if (object[2] != null && !object[2].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")), request.getParameter("rescheduleTime"), object[2].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "2");
								}
								else if (object[3] != null && !object[3].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")), request.getParameter("rescheduleTime"), object[3].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "3");
								}
								else if (object[4] != null && !object[4].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")), request.getParameter("rescheduleTime"), object[4].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "4");
								}
								setVariables.put("actionStatus", "Pending");
								setVariables.put("actionTaken", "Pending");
								setVariables.put("comments", " ");
							}
							boolean doneFlag = helpdeskHelper.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
							if (doneFlag)
							{
								List data1 = cbt.executeAllSelectQuery("SELECT id,reminder_code,remind_interval FROM compliance_reminder WHERE reminder_code='D' OR reminder_code='R' AND compliance_id=" + complID, connection);
								if (data1 != null && data1.size() > 0)
								{
									Object object2[] = null;
									for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
									{
										object2 = (Object[]) iterator2.next();
										setVariables = new HashMap<String, Object>();
										whereCondition = new HashMap<String, Object>();
										whereCondition.put("id", object2[0].toString());
										setVariables.put("due_date", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
										setVariables.put("due_time", request.getParameter("rescheduleTime"));
										setVariables.put("remind_time", request.getParameter("rescheduleTime"));
										if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R"))
										{
											setVariables.put("remind_date", DateUtil.getNewDate("day", -Integer.valueOf(object2[2].toString()), DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate"))));
										}
										else
										{
											setVariables.put("remind_date", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
										}
										setVariables.put("reminder_status", "0");
										setVariables.put("status", "Pending");
										doneFlag = helpdeskHelper.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
									}
								}
							}
						}
					}
				}
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue = request.getParameter(parmName);
					if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("dueDate") && !parmName.equals("snoozeDate") && !parmName.equals("reschuedule_date"))
					{
						if (!parmName.equalsIgnoreCase("dbName") && !parmName.equalsIgnoreCase("empId"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmName);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
				}

				if (takeActionDocFileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + takeActionDocFileName;
					String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + takeActionDocFileName;
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath != null)
					{
						File theFile = new File(storeFilePath);
						File newFileName = new File(str);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(takeActionDoc, theFile);
								if (theFile.exists())
									theFile.renameTo(newFileName);
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							if (theFile != null)
							{
								ob = new InsertDataTable();
								ob.setColName("document_takeaction");
								ob.setDataName(renameFilePath);
								insertData.add(ob);
							}
						}
					}
				}

				ob = new InsertDataTable();
				ob.setColName("dueDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("snoozeDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("rescheduleDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("actionTakenDate");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(actionTakenBy);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("actionTakenTime");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				doneRemindFlag = cbt.insertIntoTable("compliance_report", insertData, connection);
				boolean status = beforeMailConfiguration(complID, renameFilePath, actionTaken, connection);
				if (doneRemindFlag)
				{
					addActionMessage("Successfully done !!!!");
					return SUCCESS;
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return ERROR;
	}

	public String getConfigMail(String taskName, String taskType, String dueDate, String frequency, String mappedTeam, String actionStatus, String comments, String empName, String empId, String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ taskName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ taskType + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ dueDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ frequency + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ mappedTeam + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ actionStatus + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ comments + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
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

	public String getCurrentDay()
	{
		return currentDay;
	}

	public void setCurrentDay(String currentDay)
	{
		this.currentDay = currentDay;
	}

	public String getCurrentWeek()
	{
		return currentWeek;
	}

	public void setCurrentWeek(String currentWeek)
	{
		this.currentWeek = currentWeek;
	}

	public String getCurrentMonth()
	{
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth)
	{
		this.currentMonth = currentMonth;
	}

	public Map<String, String> getCurrentCompl()
	{
		return currentCompl;
	}

	public void setCurrentCompl(Map<String, String> currentCompl)
	{
		this.currentCompl = currentCompl;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getComplID()
	{
		return complID;
	}

	public void setComplID(String complID)
	{
		this.complID = complID;
	}

	public TaskTypePojoBean getComplDetails()
	{
		return complDetails;
	}

	public void setComplDetails(TaskTypePojoBean complDetails)
	{
		this.complDetails = complDetails;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public String getActionTaken()
	{
		return actionTaken;
	}

	public void setActionTaken(String actionTaken)
	{
		this.actionTaken = actionTaken;
	}

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	public Map<String, String> getTakeActionStatus()
	{
		return takeActionStatus;
	}

	public void setTakeActionStatus(Map<String, String> takeActionStatus)
	{
		this.takeActionStatus = takeActionStatus;
	}

	public File getTakeActionDoc()
	{
		return takeActionDoc;
	}

	public void setTakeActionDoc(File takeActionDoc)
	{
		this.takeActionDoc = takeActionDoc;
	}

	public String getTakeActionDocContentType()
	{
		return takeActionDocContentType;
	}

	public void setTakeActionDocContentType(String takeActionDocContentType)
	{
		this.takeActionDocContentType = takeActionDocContentType;
	}

	public String getTakeActionDocFileName()
	{
		return takeActionDocFileName;
	}

	public void setTakeActionDocFileName(String takeActionDocFileName)
	{
		this.takeActionDocFileName = takeActionDocFileName;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getNextDueDate()
	{
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate)
	{
		this.nextDueDate = nextDueDate;
	}

	public String getDepartId()
	{
		return departId;
	}

	public void setDepartId(String departId)
	{
		this.departId = departId;
	}

	public String getData4()
	{
		return data4;
	}

	public void setData4(String data4)
	{
		this.data4 = data4;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getTotalOrMissed()
	{
		return totalOrMissed;
	}

	public void setTotalOrMissed(String totalOrMissed)
	{
		this.totalOrMissed = totalOrMissed;
	}

	public Map<String, String> getCheckListMap()
	{
		return checkListMap;
	}

	public void setCheckListMap(Map<String, String> checkListMap)
	{
		this.checkListMap = checkListMap;
	}

	public String getBudgetary()
	{
		return budgetary;
	}

	public void setBudgetary(String budgetary)
	{
		this.budgetary = budgetary;
	}

	public File getTakeActionDoc1()
	{
		return takeActionDoc1;
	}

	public void setTakeActionDoc1(File takeActionDoc1)
	{
		this.takeActionDoc1 = takeActionDoc1;
	}

	public File getTakeActionDoc2()
	{
		return takeActionDoc2;
	}

	public void setTakeActionDoc2(File takeActionDoc2)
	{
		this.takeActionDoc2 = takeActionDoc2;
	}

	public String getActualExpenses()
	{
		return actualExpenses;
	}

	public void setActualExpenses(String actualExpenses)
	{
		this.actualExpenses = actualExpenses;
	}

	public String getTakeActionDoc1ContentType()
	{
		return takeActionDoc1ContentType;
	}

	public void setTakeActionDoc1ContentType(String takeActionDoc1ContentType)
	{
		this.takeActionDoc1ContentType = takeActionDoc1ContentType;
	}

	public String getTakeActionDoc1FileName()
	{
		return takeActionDoc1FileName;
	}

	public void setTakeActionDoc1FileName(String takeActionDoc1FileName)
	{
		this.takeActionDoc1FileName = takeActionDoc1FileName;
	}

	public String getTakeActionDoc2ContentType()
	{
		return takeActionDoc2ContentType;
	}

	public void setTakeActionDoc2ContentType(String takeActionDoc2ContentType)
	{
		this.takeActionDoc2ContentType = takeActionDoc2ContentType;
	}

	public String getTakeActionDoc2FileName()
	{
		return takeActionDoc2FileName;
	}

	public void setTakeActionDoc2FileName(String takeActionDoc2FileName)
	{
		this.takeActionDoc2FileName = takeActionDoc2FileName;
	}

	public String getCheckid()
	{
		return checkid;
	}

	public void setCheckid(String checkid)
	{
		this.checkid = checkid;
	}

	public String getDueTime()
	{
		return dueTime;
	}

	public void setDueTime(String dueTime)
	{
		this.dueTime = dueTime;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	
	
	

	

	public String getAttach()
	{
		return attach;
	}

	public void setAttach(String attach)
	{
		this.attach = attach;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}


}