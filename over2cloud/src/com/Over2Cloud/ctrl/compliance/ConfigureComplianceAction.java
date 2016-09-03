/*
 * @author Harit
 * Used for Compliance
 * 
 */
package com.Over2Cloud.ctrl.compliance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigureComplianceAction extends ActionSupport implements ServletRequestAware
{

	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ConfigureComplianceAction.class);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public File uploadDoc;
	private String uploadDocContentType;
	private String uploadDocFileName;
	public File uploadDoc1;
	private String uploadDoc1ContentType;
	private String uploadDoc1FileName;
	String deptLevel = (String) session.get("userDeptLevel");

	private HttpServletRequest request;
	

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

	private void createCompTable(List<GridDataPropertyView> statusColName)
	{
		TableColumes ob1;
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		for (GridDataPropertyView gdp : statusColName)
		{
			if (!gdp.getColomnName().equalsIgnoreCase("reminder1") && !gdp.getColomnName().equalsIgnoreCase("reminder2") && !gdp.getColomnName().equalsIgnoreCase("reminder3"))
			{
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				if (gdp.getColomnName().equalsIgnoreCase("l1EscDuration") || gdp.getColomnName().equalsIgnoreCase("l2EscDuration") || gdp.getColomnName().equalsIgnoreCase("l3EscDuration") || gdp.getColomnName().equalsIgnoreCase("l4EscDuration"))
				{
					ob1.setConstraint("default '00:00'");
				}
				else
				{
					ob1.setConstraint("default NULL");
				}
				Tablecolumesaaa.add(ob1);
			}
		}
		ob1 = new TableColumes();
		ob1.setColumnname("escalate_date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("escalate_time");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("current_esc_level");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		cbt.createTable22("compliance_details", Tablecolumesaaa, connectionSpace);

		List<GridDataPropertyView> statusColName1 = Configuration.getConfigurationData("mapped_compliance_report_config", accountID, connectionSpace, "", 0, "table_name", "compliance_report_config");
		if (statusColName != null)
		{
			TableColumes ob2;
			List<TableColumes> Tablecolumesaaa1 = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName1)
			{
				ob2 = new TableColumes();
				ob2.setColumnname(gdp.getColomnName());
				ob2.setDatatype("varchar(255)");
				if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
				{
					ob2.setConstraint("default '0'");
				}
				else
				{
					ob2.setConstraint("default NULL");
				}
				Tablecolumesaaa1.add(ob2);
			}
			cbt.createTable22("compliance_report", Tablecolumesaaa1, connectionSpace);
		}
	}

	public String getUpdateDueDate(String frequency, String dueDate, String days, String customFrqOn)
	{

		if (frequency.equalsIgnoreCase("W"))
			dueDate = DateUtil.getNewDate("day", 7, dueDate);
		else if (frequency.equalsIgnoreCase("M"))
			dueDate = DateUtil.getNewDate("month", 1, dueDate);
		else if (frequency.equalsIgnoreCase("BM"))
			dueDate = DateUtil.getNewDate("day", 15, dueDate);
		else if (frequency.equalsIgnoreCase("Q"))
			dueDate = DateUtil.getNewDate("month", 3, dueDate);
		else if (frequency.equalsIgnoreCase("HY"))
			dueDate = DateUtil.getNewDate("month", 6, dueDate);
		else if (frequency.equalsIgnoreCase("Y"))
			dueDate = DateUtil.getNewDate("year", 1, dueDate);
		else if (frequency.equalsIgnoreCase("D"))
			dueDate = DateUtil.getNewDate("day", 1, dueDate);
		else if (frequency.equalsIgnoreCase("O"))
		{
			if(days!=null && customFrqOn!=null && !customFrqOn.equalsIgnoreCase("-1"))
			{
				if(customFrqOn.equalsIgnoreCase("D"))
					dueDate = DateUtil.getNewDate("day", Integer.parseInt(days), dueDate);
				else if(customFrqOn.equalsIgnoreCase("M"))
					dueDate = DateUtil.getNewDate("month", Integer.parseInt(days), dueDate);
				else if(customFrqOn.equalsIgnoreCase("Y"))
					dueDate = DateUtil.getNewDate("year", Integer.parseInt(days), dueDate);
			}
			
		}
			

		return dueDate;
	}

	private List<InsertDataTable> setParameterInObj(String paramName, String paramValue, List<InsertDataTable> insertData)
	{
		InsertDataTable object = new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}

	@SuppressWarnings(
	{ "unchecked", "static-access" })
	public String configureCompliance()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceCommonOperation cmplCmn = new ComplianceCommonOperation();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String docPath1 = null, docPath2 = null;
				String renameFilePath = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compliance_configuration", accountID, connectionSpace, "", 0, "table_name", "compliance_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertDataOBJ = new ArrayList<InsertDataTable>();
					StringBuilder strID = new StringBuilder();
					boolean status = false, otherMailFlag = false, otherSmsFlag = false, selfMailFlag = false, selfSmsFlag = false;
					String reminFor = null;
					List fileList = new ArrayList<String>();
					// create table
					createCompTable(statusColName);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String dueDate = null;
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("taskType") && !parmName.equalsIgnoreCase("taskBrief") && !parmName.equalsIgnoreCase("msg")
								&& !parmName.equalsIgnoreCase("newtaskBrief") && !parmName.equalsIgnoreCase("newremind_days") && !parmName.equalsIgnoreCase("budgetary") && !parmName.equalsIgnoreCase("reminder1")
								&& !parmName.equalsIgnoreCase("reminder2") && !parmName.equalsIgnoreCase("reminder3") && !parmName.equalsIgnoreCase("newbudgetary") && !parmName.equalsIgnoreCase("newtaskType")
								&& !parmName.equalsIgnoreCase("newtaskName") && !parmName.equalsIgnoreCase("newmsg") && !parmName.equalsIgnoreCase("__checkbox_othermail") && !parmName.equalsIgnoreCase("__checkbox_selfmail")
								&& !parmName.equalsIgnoreCase("__checkbox_othersms") && !parmName.equalsIgnoreCase("__checkbox_selfsms") && !parmName.equalsIgnoreCase("othermail") && !parmName.equalsIgnoreCase("selfmail")
								&& !parmName.equalsIgnoreCase("othersms") && !parmName.equalsIgnoreCase("selfsms") && !parmName.equalsIgnoreCase("customFrqOn") && !parmName.equalsIgnoreCase("remind_days"))
						{
							if (parmName.equals("fromDate") || parmName.equals("dueDate"))
							{
								dueDate = DateUtil.convertDateToUSFormat(paramValue);
								if (dueDate != null)
								{
									status = false;
									status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), dueDate + " " + request.getParameter("dueTime"));
									System.out.println(status);
									if (!status)
									{
										dueDate = getUpdateDueDate(request.getParameter("frequency"), dueDate, request.getParameter("remind_days"), request.getParameter("customFrqOn"));

									}
									insertData = setParameterInObj(parmName, dueDate, insertData);
								}
							}
							// REminder for
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToSelf"))
							{
								reminFor = paramValue;
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];

								if (userContID != null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));

							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToOther"))
							{
								reminFor = paramValue;
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToBoth"))
							{
								reminFor = paramValue;

								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];

								if (userContID != null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));

								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
							}
							else if (parmName.equals("taskName") && paramValue.equalsIgnoreCase("Configure New"))
							{
								Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
								String userDeptID = null;
								userDeptID = CUA.getEmpDetailsByUserName("COMPL", userName)[4];

								dataWithColumnName.put("taskType", request.getParameter("newtaskType"));
								dataWithColumnName.put("departName", userDeptID);

								int maxId = new ConfigureComplianceHelper().saveData("mapped_compl_task_type_config", "compl_task_type_config", "compl_task_type", dataWithColumnName);
								if (maxId > 0)
								{
									dataWithColumnName = new LinkedHashMap<String, String>();
									dataWithColumnName.put("taskName", request.getParameter("newtaskName"));
									dataWithColumnName.put("msg", request.getParameter("newmsg"));
									dataWithColumnName.put("taskBrief", request.getParameter("newtaskBrief"));
									if (request.getParameter("newbudgetary") != null)
									{
										dataWithColumnName.put("budgetary", request.getParameter("newbudgetary"));
										dataWithColumnName.put("accounting", "Yes");
									}
									else
									{
										dataWithColumnName.put("accounting", "No");
									}
									dataWithColumnName.put("taskType", String.valueOf(maxId));
									dataWithColumnName.put("departName", userDeptID);
									maxId = 0;
									maxId = new ConfigureComplianceHelper().saveData("mapped_compl_task_config", "compl_task_config", "compliance_task", dataWithColumnName);

									insertData = setParameterInObj("taskName", String.valueOf(maxId), insertData);
									insertData = setParameterInObj("msg", request.getParameter("newmsg"), insertData);
									insertData = setParameterInObj("taskBrief", request.getParameter("newtaskBrief"), insertData);
									insertData = setParameterInObj("budgetary", request.getParameter("newbudgetary"), insertData);
								}
							}
							else if (parmName.equals("taskName") && !paramValue.equalsIgnoreCase("Configure New"))
							{
								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("msg", request.getParameter("msg"), insertData);
								insertData = setParameterInObj("taskBrief", request.getParameter("taskBrief"), insertData);
								insertData = setParameterInObj("budgetary", request.getParameter("budgetary"), insertData);
							}
							else if (parmName.equals("l1EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								String escDateTime[] = new String[2];
								WorkingHourAction WHA = new WorkingHourAction();
								String date = DateUtil.convertDateToUSFormat(request.getParameter("dueDate"));
								String time = request.getParameter("dueTime");
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, paramValue, "00:00", "Y", date, time, "COMPL");
								escDateTime[0] = dateTime.get(0);
								escDateTime[1] = dateTime.get(1);

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "1", insertData);
							}
							else if (parmName.equals("l2EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l1EscDuration") == null || request.getParameter("l1EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new String[2];
								WorkingHourAction WHA = new WorkingHourAction();
								String date = DateUtil.convertDateToUSFormat(request.getParameter("dueDate"));
								String time = request.getParameter("dueTime");
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, paramValue, "00:00", "Y", date, time, "COMPL");
								escDateTime[0] = dateTime.get(0);
								escDateTime[1] = dateTime.get(1);

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "2", insertData);
							}
							else if (parmName.equals("l3EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l2EscDuration") == null || request.getParameter("l2EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new String[2];
								WorkingHourAction WHA = new WorkingHourAction();
								String date = DateUtil.convertDateToUSFormat(request.getParameter("dueDate"));
								String time = request.getParameter("dueTime");
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, paramValue, "00:00", "Y", date, time, "COMPL");
								escDateTime[0] = dateTime.get(0);
								escDateTime[1] = dateTime.get(1);

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "3", insertData);
							}
							else if (parmName.equals("l4EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l3EscDuration") == null || request.getParameter("l3EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new String[2];
								WorkingHourAction WHA = new WorkingHourAction();
								String date = DateUtil.convertDateToUSFormat(request.getParameter("dueDate"));
								String time = request.getParameter("dueTime");
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, paramValue, "00:00", "Y", date, time, "COMPL");
								escDateTime[0] = dateTime.get(0);
								escDateTime[1] = dateTime.get(1);

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "4", insertData);
							}
							else if (parmName.equals("escalation_level_1") || parmName.equals("escalation_level_2") || parmName.equals("escalation_level_3") || parmName.equals("escalation_level_4"))
							{
								String empId[] = request.getParameterValues(parmName);
								StringBuilder strIDD = new StringBuilder();
								for (int i = 0; i < empId.length; i++)
								{
									strIDD.append(empId[i]);
									strIDD.append("#");
								}
								insertData = setParameterInObj(parmName, strIDD.toString(), insertData);
							}
							else if (!parmName.equalsIgnoreCase("emp_id"))
							{
								insertData = setParameterInObj(parmName, paramValue, insertData);
							}
						}
						if (parmName.equalsIgnoreCase("selfmail"))
						{
							selfMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("selfsms"))
						{
							selfSmsFlag = true;
						}
						if (parmName.equalsIgnoreCase("othermail"))
						{
							otherMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("othersms"))
						{
							otherSmsFlag = true;
						}
					}
					if (otherMailFlag || selfMailFlag)
						insertData = setParameterInObj("ack_dge", "Yes", insertData);
					else if (otherSmsFlag || selfSmsFlag)
						insertData = setParameterInObj("ack_dge", "Yes", insertData);
					else
						insertData = setParameterInObj("ack_dge", "No", insertData);

					// setting the values of file.
					if (uploadDocFileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + getUploadDocFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + getUploadDocFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									docPath1 = renameFilePath;
									fileList.add(renameFilePath);
									insertData = setParameterInObj("uploadDoc", renameFilePath, insertData);
								}
							}
						}
					}
					if (uploadDoc1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + getUploadDoc1FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + getUploadDoc1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc1, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									docPath2 = renameFilePath;
									fileList.add(renameFilePath);
									insertData = setParameterInObj("uploadDoc1", renameFilePath, insertData);
								}
							}
						}
					}

					insertData = setParameterInObj("actionStatus", "Pending", insertData);
					insertData = setParameterInObj("actionTaken", "Pending", insertData);
					insertData = setParameterInObj("userName", userName, insertData);
					if(request.getParameter("frequency")!=null && request.getParameter("frequency").equalsIgnoreCase("O"))
					{
						if(!request.getParameter("customFrqOn").equals("-1") && !request.getParameter("remind_days").equals(" "))
						{
							insertData = setParameterInObj("remind_days", request.getParameter("customFrqOn")+"#"+request.getParameter("remind_days"), insertData);
						}
					}
					insertData = setParameterInObj("date", DateUtil.getCurrentDateUSFormat(), insertData);
					insertData = setParameterInObj("time", DateUtil.getCurrentTime(), insertData);

					String deptId = CUA.getEmpDataByUserName(userName)[3];
					String frq = request.getParameter("frequency");
					List complSeries = getCompIdPrefix_Suffix(deptId, frq);
					String prefix = null;
					int suffix = 0;
					if (complSeries != null && complSeries.size() > 0)
					{
						if (complSeries.get(0) != null)
							prefix = complSeries.get(0).toString();

						if (complSeries.get(1) != null)
							suffix = Integer.valueOf(complSeries.get(1).toString());
					}

					insertData = setParameterInObj("compid_prefix", prefix, insertData);

					if (request.getParameter("action_type") != null && request.getParameter("action_type").equalsIgnoreCase("individual"))
					{
						if (strID != null && !strID.toString().equalsIgnoreCase(""))
						{
							String[] empIds = strID.toString().split("#");
							if (empIds != null && empIds.length > 0)
							{
								for (int count = 0; count < empIds.length; count++)
								{
									String complianceId = prefix + String.valueOf(suffix + count);

									insertData = setParameterInObj("compid_suffix", String.valueOf(suffix + count), insertData);
									insertData = setParameterInObj("reminder_for", empIds[count] + "#", insertData);

									status = cbt.insertIntoTable("compliance_details", insertData, connectionSpace);
									insertData.remove(insertData.size() - 1);
									insertData.remove(insertData.size() - 1);

									int maxComplID = cmplCmn.getMaximumComplId();

									if (otherMailFlag || selfMailFlag)
									{
										if (maxComplID > 0)
										{
											String userEmpID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];

											mailSendComaplaince(maxComplID, reminFor, userEmpID, connectionSpace, selfMailFlag, otherMailFlag);
										}
									}
									if (otherSmsFlag || selfSmsFlag)
									{
										if (maxComplID > 0)
										{
											String userEmpID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
											smsSendComaplaince(maxComplID, userEmpID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
										}
									}

									// Insert Data Into Compliance Reminder
									// Table
									List<String> dateList = new ArrayList<String>();
									if (status)
									{
										List<String> dayCounterList = new ArrayList<String>();
										dateList.add(dueDate);
										dayCounterList.add("0");
										String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
										String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
										String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));
										if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
											dayCounterList.add(String.valueOf(dateDiff));

											status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate1, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate1);
											}
										}
										if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
											dayCounterList.add(String.valueOf(dateDiff));
											status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate2, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate2);
											}
										}
										if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
											dayCounterList.add(String.valueOf(dateDiff));
											status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate3, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate3);
											}
										}
										if (dateList != null && dateList.size() > 0)
										{
											for (int i = 0; i < dateList.size(); i++)
											{
												Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
												dataWithColumnName.put("due_date", dueDate);
												dataWithColumnName.put("due_time", request.getParameter("dueTime"));
												dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
												dataWithColumnName.put("reminder_status", "0");
												dataWithColumnName.put("status", "Pending");
												dataWithColumnName.put("compliance_id", String.valueOf(maxComplID));
												if (i == 0)
												{
													dataWithColumnName.put("reminder_name", "Due Reminder");
													dataWithColumnName.put("reminder_code", "D");
													dataWithColumnName.put("remind_date", dueDate);
												}
												else
												{
													dataWithColumnName.put("reminder_name", "Reminder " + i);
													dataWithColumnName.put("reminder_code", "R");
													dataWithColumnName.put("remind_date", dateList.get(i));
													dataWithColumnName.put("remind_interval", dayCounterList.get(i));
												}
												cmplCmn.saveComplReminder(dataWithColumnName);
											}
										}
									}
									insertDataOBJ = new ArrayList<InsertDataTable>();
									if (dateList != null && dateList.size() > 1)
									{
										for (int i = 1; i < dateList.size(); i++)
										{
											insertDataOBJ = setParameterInObj("reminder" + i, dateList.get(i).toString(), insertDataOBJ);
										}
									}

									insertDataOBJ = setParameterInObj("complainceId", complianceId, insertDataOBJ);
									insertDataOBJ = setParameterInObj("dueDate", dueDate, insertDataOBJ);
									insertDataOBJ = setParameterInObj("dueTime", request.getParameter("dueTime"), insertDataOBJ);
									insertDataOBJ = setParameterInObj("userName", new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY), insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTaken", "Pending", insertDataOBJ);
									insertDataOBJ = setParameterInObj("complID", String.valueOf(maxComplID), insertDataOBJ);
									insertDataOBJ = setParameterInObj("document_config_1", docPath1, insertDataOBJ);
									insertDataOBJ = setParameterInObj("document_config_2", docPath2, insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTakenDate", DateUtil.getCurrentDateUSFormat(), insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTakenTime", DateUtil.getCurrentTimeHourMin(), insertDataOBJ);

									if (request.getParameter("escalation").equalsIgnoreCase("Y"))
									{
										insertDataOBJ = setParameterInObj("current_esc_level", "1", insertDataOBJ);
									}

									cbt.insertIntoTable("compliance_report", insertDataOBJ, connectionSpace);
								}
							}
						}
						addActionMessage("Operation Task Added Successfully !!!");
						returnResult = SUCCESS;
					}
					else
					{

						insertData = setParameterInObj("compid_suffix", String.valueOf(suffix), insertData);
						insertData = setParameterInObj("reminder_for", strID.toString(), insertData);

						status = cbt.insertIntoTable("compliance_details", insertData, connectionSpace);
						int maxComplID = cmplCmn.getMaximumComplId();

						if (otherMailFlag || selfMailFlag)
						{
							if (maxComplID > 0)
							{
								String userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
								// fileList is used for excel name list
								mailSendComaplaince(maxComplID, reminFor, userContID, connectionSpace, selfMailFlag, otherMailFlag);
							}
						}
						if (otherSmsFlag || selfSmsFlag)
						{
							if (maxComplID > 0)
							{
								String userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
								smsSendComaplaince(maxComplID, userContID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
							}
						}
						// Insert Data Into Compliance Reminder Table
						List<String> dateList = new ArrayList<String>();
						if (status)
						{
							List<String> dayCounterList = new ArrayList<String>();
							dateList.add(dueDate);
							dayCounterList.add("0");
							String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
							String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
							String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));

							if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate1, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate1);
								}
							}
							if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate2, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate2);
								}
							}
							if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate3, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate3);
								}
							}
							if (dateList != null && dateList.size() > 0)
							{
								for (int i = 0; i < dateList.size(); i++)
								{
									Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
									dataWithColumnName.put("due_date", dueDate);
									dataWithColumnName.put("due_time", request.getParameter("dueTime"));
									dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
									dataWithColumnName.put("reminder_status", "0");
									dataWithColumnName.put("status", "Pending");
									dataWithColumnName.put("compliance_id", String.valueOf(maxComplID));
									if (i == 0)
									{
										dataWithColumnName.put("reminder_name", "Due Reminder");
										dataWithColumnName.put("reminder_code", "D");
										dataWithColumnName.put("remind_date", dueDate);
									}
									else
									{
										dataWithColumnName.put("reminder_name", "Reminder " + i);
										dataWithColumnName.put("reminder_code", "R");
										dataWithColumnName.put("remind_date", dateList.get(i));
										dataWithColumnName.put("remind_interval", dayCounterList.get(i));
									}
									cmplCmn.saveComplReminder(dataWithColumnName);
								}
							}
						}
						insertData = new ArrayList<InsertDataTable>();
						if (dateList != null && dateList.size() > 1)
						{
							for (int i = 1; i < dateList.size(); i++)
							{
								insertData = setParameterInObj("reminder" + i, dateList.get(i).toString(), insertData);
							}

						}

						insertData = setParameterInObj("complainceId", prefix + String.valueOf(suffix), insertData);
						insertData = setParameterInObj("dueDate", dueDate, insertData);
						insertData = setParameterInObj("dueTime", request.getParameter("dueTime"), insertData);
						insertData = setParameterInObj("userName", new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY), insertData);
						insertData = setParameterInObj("actionTaken", "Pending", insertData);
						insertData = setParameterInObj("complID", String.valueOf(maxComplID), insertData);
						insertData = setParameterInObj("document_config_1", docPath1, insertData);
						insertData = setParameterInObj("document_config_2", docPath2, insertData);
						insertData = setParameterInObj("actionTakenDate", DateUtil.getCurrentDateUSFormat(), insertData);
						insertData = setParameterInObj("actionTakenTime", DateUtil.getCurrentTimeHourMin(), insertData);

						if (request.getParameter("escalation").equalsIgnoreCase("Y"))
						{
							insertData = setParameterInObj("current_esc_level", "1", insertData);
						}
						cbt.insertIntoTable("compliance_report", insertData, connectionSpace);
						addActionMessage("Operation Task Added Successfully !!!");
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method configureCompliance of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String uploadDocument()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (uploadDocFileName != null)
				{
					String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + getUploadDocFileName();
					if (storeFilePath != null)
					{
						File theFile = new File(storeFilePath);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(uploadDoc, theFile);
							}
							catch (IOException e)
							{
								e.printStackTrace();
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

	@SuppressWarnings(
	{ "unchecked", "static-access" })
	public String configCompExcelData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceCommonOperation cmplCmn = new ComplianceCommonOperation();
				String docPath1 = null, docPath2 = null;
				String renameFilePath = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_compliance_configuration", accountID, connectionSpace, "", 0, "table_name", "compliance_configuration");
				if (statusColName != null)
				{
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertDataOBJ = new ArrayList<InsertDataTable>();
					StringBuilder strID = new StringBuilder();
					boolean status = false, otherMailFlag = false, otherSmsFlag = false, selfSmsFlag = false, selfMailFlag = false;
					String reminFor = null;
					List fileList = new ArrayList<String>();

					// Create Table
					createCompTable(statusColName);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String dueDate = null;
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("taskType") && !parmName.equalsIgnoreCase("taskBrief") && !parmName.equalsIgnoreCase("msg")
								&& !parmName.equalsIgnoreCase("newtaskBrief") && !parmName.equalsIgnoreCase("reminder1") && !parmName.equalsIgnoreCase("budgetary") && !parmName.equalsIgnoreCase("reminder2") && !parmName.equalsIgnoreCase("reminder3")
								&& !parmName.equalsIgnoreCase("complExcelDataId") && !parmName.equalsIgnoreCase("excelDataTableId") && !parmName.equalsIgnoreCase("newtaskType") && !parmName.equalsIgnoreCase("newtaskName")
								&& !parmName.equalsIgnoreCase("newmsg") && !parmName.equalsIgnoreCase("__checkbox_othermail") && !parmName.equalsIgnoreCase("__checkbox_selfmail") && !parmName.equalsIgnoreCase("__checkbox_othersms")
								&& !parmName.equalsIgnoreCase("__checkbox_selfsms") && !parmName.equalsIgnoreCase("othermail") && !parmName.equalsIgnoreCase("selfmail") && !parmName.equalsIgnoreCase("othersms")
								&& !parmName.equalsIgnoreCase("selfsms"))
						{
							if (parmName.equals("fromDate") || parmName.equals("dueDate"))
							{
								dueDate = DateUtil.convertDateToUSFormat(paramValue);
								if (dueDate != null)
								{
									status = false;
									status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), dueDate + " " + request.getParameter("dueTime"));
									if (!status)
										if (!status)
										{
											dueDate = getUpdateDueDate(request.getParameter("frequency"), dueDate, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
										}
									insertData = setParameterInObj(parmName, dueDate, insertData);
								}
							}
							// REminder for
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToSelf"))
							{
								reminFor = paramValue;
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];

								if (userContID != null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));

							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToOther"))
							{
								reminFor = paramValue;
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToBoth"))
							{
								reminFor = paramValue;
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];

								if (userContID != null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));

								String[] emplID = request.getParameterValues("emp_id");

								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
							}
							else if (parmName.equals("taskName") && paramValue.equalsIgnoreCase("Configure New"))
							{

								Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
								dataWithColumnName.put("taskType", request.getParameter("newtaskType"));
								int maxId = new ConfigureComplianceHelper().saveData("mapped_compl_task_type_config", "compl_task_type_config", "compl_task_type", dataWithColumnName);
								if (maxId > 0)
								{
									dataWithColumnName = new LinkedHashMap<String, String>();
									dataWithColumnName.put("taskName", request.getParameter("newtaskName"));
									dataWithColumnName.put("msg", request.getParameter("newmsg"));
									dataWithColumnName.put("taskBrief", request.getParameter("newtaskBrief"));
									dataWithColumnName.put("budgetary", request.getParameter("newbudgetary"));
									dataWithColumnName.put("taskType", String.valueOf(maxId));
									String userDeptID = null;
									userDeptID = CUA.getEmpDetailsByUserName("COMPL", userName)[4];

									dataWithColumnName.put("departName", userDeptID);
									maxId = 0;
									maxId = new ConfigureComplianceHelper().saveData("mapped_compl_task_config", "compl_task_config", "compliance_task", dataWithColumnName);

									insertData = setParameterInObj("taskName", String.valueOf(maxId), insertData);
									insertData = setParameterInObj("msg", request.getParameter("newmsg"), insertData);
									insertData = setParameterInObj("taskBrief", request.getParameter("newtaskBrief"), insertData);
									insertData = setParameterInObj("budgetary", request.getParameter("newbudgetary"), insertData);
								}

							}
							else if (parmName.equals("taskName") && !paramValue.equalsIgnoreCase("Configure New"))
							{
								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("msg", request.getParameter("msg"), insertData);
								insertData = setParameterInObj("taskBrief", request.getParameter("taskBrief"), insertData);
								insertData = setParameterInObj("completion", request.getParameter("completion"), insertData);
							}
							else if (parmName.equals("l1EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"), paramValue, "Y").split("#");

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "1", insertData);
							}
							else if (parmName.equals("l2EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l1EscDuration") == null || request.getParameter("l1EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"), paramValue, "Y").split("#");

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "2", insertData);

							}
							else if (parmName.equals("l3EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l2EscDuration") == null || request.getParameter("l2EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"), paramValue, "Y").split("#");

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "3", insertData);
							}
							else if (parmName.equals("l4EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("") && (request.getParameter("l3EscDuration") == null || request.getParameter("l3EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"), paramValue, "Y").split("#");

								insertData = setParameterInObj(parmName, paramValue, insertData);
								insertData = setParameterInObj("escalate_date", escDateTime[0], insertData);
								insertData = setParameterInObj("escalate_time", escDateTime[1], insertData);
								insertData = setParameterInObj("current_esc_level", "4", insertData);
							}
							else if (parmName.equals("escalation_level_1") || parmName.equals("escalation_level_2") || parmName.equals("escalation_level_3") || parmName.equals("escalation_level_4"))
							{
								String empId[] = request.getParameterValues(parmName);
								StringBuilder strIDD = new StringBuilder();
								for (int i = 0; i < empId.length; i++)
								{
									strIDD.append(empId[i]);
									strIDD.append("#");
								}
								insertData = setParameterInObj(parmName, strIDD.toString(), insertData);
							}
							else if (!parmName.equalsIgnoreCase("emp_id") && !parmName.equalsIgnoreCase("excelDataTableId") && !parmName.equalsIgnoreCase("complExcelDataId"))
							{
								insertData = setParameterInObj(parmName, paramValue, insertData);
							}
						}
						if (parmName.equalsIgnoreCase("selfmail"))
						{
							selfMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("selfsms"))
						{
							selfSmsFlag = true;
						}
						if (parmName.equalsIgnoreCase("othermail"))
						{
							otherMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("othersms"))
						{
							otherSmsFlag = true;
						}
					}
					if (otherMailFlag || selfMailFlag)
					{
						insertData = setParameterInObj("ack_dge", "Yes", insertData);
					}
					else if (otherSmsFlag || selfSmsFlag)
					{
						insertData = setParameterInObj("ack_dge", "Yes", insertData);
					}
					else
					{
						insertData = setParameterInObj("ack_dge", "No", insertData);
					}

					// setting the values of file.
					if (uploadDocFileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + getUploadDocFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + getUploadDocFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									docPath1 = renameFilePath;
									fileList.add(renameFilePath);

									insertData = setParameterInObj("uploadDoc", renameFilePath, insertData);

								}
							}
						}
					}
					if (uploadDoc1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + DateUtil.mergeDateTime() + getUploadDoc1FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("Compliance_Data") + "//" + getUploadDoc1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc1, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									docPath2 = renameFilePath;
									fileList.add(renameFilePath);

									insertData = setParameterInObj("uploadDoc1", renameFilePath, insertData);
								}
							}
						}
					}
					insertData = setParameterInObj("actionStatus", "Pending", insertData);
					insertData = setParameterInObj("actionTaken", "Pending", insertData);
					insertData = setParameterInObj("userName", userName, insertData);
					insertData = setParameterInObj("date", DateUtil.getCurrentDateUSFormat(), insertData);
					insertData = setParameterInObj("time", DateUtil.getCurrentTime(), insertData);

					String deptId = CUA.getEmpDataByUserName(userName)[3];
					String frq = request.getParameter("frequency");
					List complSeries = getCompIdPrefix_Suffix(deptId, frq);
					String prefix = null;
					int suffix = 0;
					if (complSeries != null && complSeries.size() > 0)
					{
						if (complSeries.get(0) != null)
							prefix = complSeries.get(0).toString();

						if (complSeries.get(1) != null)
							suffix = Integer.valueOf(complSeries.get(1).toString());
					}

					insertData = setParameterInObj("compid_prefix", prefix, insertData);

					if (request.getParameter("action_type") != null && request.getParameter("action_type").equalsIgnoreCase("individual"))
					{
						if (strID != null && !strID.toString().equalsIgnoreCase(""))
						{
							String[] empIds = strID.toString().split("#");
							if (empIds != null && empIds.length > 0)
							{
								for (int count = 0; count < empIds.length; count++)
								{
									String complianceId = prefix + String.valueOf(suffix + count);

									insertData = setParameterInObj("compid_suffix", String.valueOf(suffix + count), insertData);
									insertData = setParameterInObj("reminder_for", empIds[count] + "#", insertData);

									status = cbt.insertIntoTable("compliance_details", insertData, connectionSpace);
									insertData.remove(insertData.size() - 1);
									insertData.remove(insertData.size() - 1);

									int maxComplID = cmplCmn.getMaximumComplId();
									if (otherMailFlag || selfMailFlag)
									{
										if (maxComplID > 0)
										{

											String userEmpID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
											// fileList is used for excel name
											// list
											mailSendComaplaince(maxComplID, reminFor, userEmpID, connectionSpace, selfMailFlag, otherMailFlag);
										}
									}
									if (otherSmsFlag || selfSmsFlag)
									{
										if (maxComplID > 0)
										{
											String userEmpID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
											smsSendComaplaince(maxComplID, userEmpID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
										}
									}
									List<String> dateList = new ArrayList<String>();

									if (status)
									{
										List<String> dayCounterList = new ArrayList<String>();
										dateList.add(dueDate);
										dayCounterList.add("0");
										String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
										String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
										String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));

										if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
											dayCounterList.add(String.valueOf(dateDiff));

											status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate1, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate1);
											}
										}
										if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
											dayCounterList.add(String.valueOf(dateDiff));

											status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate2, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate2);
											}
										}
										if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
											dayCounterList.add(String.valueOf(dateDiff));
											status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate3, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
												if (newDate != null)
													dateList.add(newDate);
											}
											else
											{
												dateList.add(reminDate3);
											}
										}
										if (dateList != null && dateList.size() > 0)
										{
											for (int i = 0; i < dateList.size(); i++)
											{
												Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
												dataWithColumnName.put("due_date", dueDate);
												dataWithColumnName.put("due_time", request.getParameter("dueTime"));
												dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
												dataWithColumnName.put("reminder_status", "0");
												dataWithColumnName.put("status", "Pending");
												dataWithColumnName.put("compliance_id", String.valueOf(maxComplID));
												if (i == 0)
												{
													dataWithColumnName.put("reminder_name", "Due Reminder");
													dataWithColumnName.put("reminder_code", "D");
													dataWithColumnName.put("remind_date", dueDate);
												}
												else
												{
													dataWithColumnName.put("reminder_name", "Reminder " + i);
													dataWithColumnName.put("reminder_code", "R");
													dataWithColumnName.put("remind_date", dateList.get(i));
													dataWithColumnName.put("remind_interval", dayCounterList.get(i));
												}
												cmplCmn.saveComplReminder(dataWithColumnName);
											}
										}
									}
									// Insert data into compliance report table
									insertDataOBJ = new ArrayList<InsertDataTable>();

									if (dateList != null && dateList.size() > 1)
									{
										for (int i = 1; i < dateList.size(); i++)
										{
											insertDataOBJ = setParameterInObj("reminder" + i, dateList.get(i).toString(), insertDataOBJ);
										}
									}

									insertDataOBJ = setParameterInObj("complainceId", complianceId, insertDataOBJ);
									insertDataOBJ = setParameterInObj("dueDate", dueDate, insertDataOBJ);
									insertDataOBJ = setParameterInObj("dueTime", request.getParameter("dueTime"), insertDataOBJ);
									insertDataOBJ = setParameterInObj("userName", new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY), insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTaken", "Pending", insertDataOBJ);
									insertDataOBJ = setParameterInObj("complID", String.valueOf(maxComplID), insertDataOBJ);
									insertDataOBJ = setParameterInObj("document_config_1", docPath1, insertDataOBJ);
									insertDataOBJ = setParameterInObj("document_config_2", docPath2, insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTakenDate", DateUtil.getCurrentDateUSFormat(), insertDataOBJ);
									insertDataOBJ = setParameterInObj("actionTakenTime", DateUtil.getCurrentTimeHourMin(), insertDataOBJ);

									if (request.getParameter("escalation").equalsIgnoreCase("Y"))
									{
										insertDataOBJ = setParameterInObj("current_esc_level", "1", insertDataOBJ);
									}

									cbt.insertIntoTable("compliance_report", insertDataOBJ, connectionSpace);
								}
								cbt.executeAllUpdateQuery("UPDATE comp_excelupload_data SET configStatus='1' WHERE id=" + request.getParameter("complExcelDataId"), connectionSpace);
							}
						}
					}
					else
					{
						String complianceId = prefix + String.valueOf(suffix);
						insertData = setParameterInObj("compid_suffix", String.valueOf(suffix), insertData);

						insertData = setParameterInObj("reminder_for", strID.toString(), insertData);

						status = cbt.insertIntoTable("compliance_details", insertData, connectionSpace);
						insertData.remove(insertData.size() - 1);

						int maxComplID = cmplCmn.getMaximumComplId();
						if (otherMailFlag || selfMailFlag)
						{
							if (maxComplID > 0)
							{
								String userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
								// fileList is used for excel name list
								mailSendComaplaince(maxComplID, reminFor, userContID, connectionSpace, selfMailFlag, otherMailFlag);
							}
						}
						if (otherSmsFlag || selfSmsFlag)
						{
							if (maxComplID > 0)
							{
								String userContID = CUA.getEmpDetailsByUserName("COMPL", userName)[0];
								smsSendComaplaince(maxComplID, userContID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
							}
						}

						List<String> dateList = new ArrayList<String>();
						if (status)
						{
							List<String> dayCounterList = new ArrayList<String>();
							dateList.add(dueDate);
							dayCounterList.add("0");
							String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
							String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
							String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));

							if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
							{

								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate1, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate1);
								}
							}

							if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate2, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate2);
								}
							}
							if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
								dayCounterList.add(String.valueOf(dateDiff));
								status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									String newDate = getUpdateDueDate(request.getParameter("frequency"), reminDate3, request.getParameter("remind_days"), request.getParameter("customFrqOn"));
									if (newDate != null)
										dateList.add(newDate);
								}
								else
								{
									dateList.add(reminDate3);
								}
							}
							if (dateList != null && dateList.size() > 0)
							{
								for (int i = 0; i < dateList.size(); i++)
								{
									Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
									dataWithColumnName.put("due_date", dueDate);
									dataWithColumnName.put("due_time", request.getParameter("dueTime"));
									dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
									dataWithColumnName.put("reminder_status", "0");
									dataWithColumnName.put("status", "Pending");
									dataWithColumnName.put("compliance_id", String.valueOf(maxComplID));
									if (i == 0)
									{
										dataWithColumnName.put("reminder_name", "Due Reminder");
										dataWithColumnName.put("reminder_code", "D");
										dataWithColumnName.put("remind_date", dueDate);
									}
									else
									{
										dataWithColumnName.put("reminder_name", "Reminder " + i);
										dataWithColumnName.put("reminder_code", "R");
										dataWithColumnName.put("remind_date", dateList.get(i));
										dataWithColumnName.put("remind_interval", dayCounterList.get(i));
									}
									cmplCmn.saveComplReminder(dataWithColumnName);
								}
							}
						}
						insertDataOBJ = new ArrayList<InsertDataTable>();
						if (dateList != null && dateList.size() > 1)
						{
							for (int i = 1; i < dateList.size(); i++)
							{
								insertData = setParameterInObj("reminder" + i, dateList.get(i).toString(), insertData);
							}
						}

						insertData = setParameterInObj("complainceId", complianceId, insertData);
						insertData = setParameterInObj("dueDate", dueDate, insertData);
						insertData = setParameterInObj("dueTime", request.getParameter("dueTime"), insertData);
						insertData = setParameterInObj("userName", new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY), insertData);
						insertData = setParameterInObj("actionTaken", "Pending", insertData);
						insertData = setParameterInObj("complID", String.valueOf(maxComplID), insertData);
						insertData = setParameterInObj("document_config_1", docPath1, insertData);
						insertData = setParameterInObj("document_config_2", docPath2, insertData);
						insertData = setParameterInObj("actionTakenDate", DateUtil.getCurrentDateUSFormat(), insertData);
						insertData = setParameterInObj("actionTakenTime", DateUtil.getCurrentTimeHourMin(), insertData);

						if (request.getParameter("escalation").equalsIgnoreCase("Y"))
						{
							insertData = setParameterInObj("current_esc_level", "1", insertData);
						}
						cbt.insertIntoTable("compliance_report", insertDataOBJ, connectionSpace);
					}
					cbt.executeAllUpdateQuery("UPDATE comp_excelupload_data SET configStatus='1' WHERE id=" + request.getParameter("complExcelDataId"), connectionSpace);
					addActionMessage("Operation Task Added Successfully !!!");
					returnResult = SUCCESS;
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method configureCompliance of class " + getClass(), exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public void smsSendComaplaince(int maxComplID, String contactId, SessionFactory connection, String reminFor, boolean otherSmsFlag, boolean selfSmsFlag)
	{
		try
		{
			CommunicationHelper cmnHelper = new CommunicationHelper();
			ComplianceCommonOperation cmplCmn = new ComplianceCommonOperation();
			List<ComplianceDashboardBean> conmpData = cmplCmn.getComplainceData(maxComplID, contactId);
			String remaindTo = null;
			String taskName = null;
			String dueDate = null;
			String escTime = null;
			String allotedBy = null;
			String mobNo = null;
			String loggedMob = null;
			String loggedName = null;
			String loggedDept = null;
			String msgBody = null;
			String empName = null;
			String otherDept = null;
			if (conmpData != null && conmpData.size() > 0)
			{
				for (ComplianceDashboardBean c : conmpData)
				{
					remaindTo = c.getRemindTo();
					taskName = c.getTaskName();
					dueDate = c.getDueDate();
					escTime = c.getEsc2Count();
				}
			}
			if (reminFor != null && reminFor.equalsIgnoreCase("remToSelf"))
			{
				if (contactId.equalsIgnoreCase(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1))))
				{
					if (selfSmsFlag)
					{
						List other = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
						for (Iterator iterator = other.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								mobNo = object[2].toString();
								if (object[5].toString().equalsIgnoreCase(contactId))
								{
									allotedBy = "You";
								}
								else
								{
									allotedBy = object[0].toString();
								}
								if (object[0] != null)
								{
									loggedName = object[0].toString();
								}
								if (object[4] != null)
								{
									loggedDept = object[4].toString();
								}
							}
							msgBody = cmplCmn.getMsgBodyComplaince(allotedBy, taskName, dueDate, escTime);
							cmnHelper.addMessage(loggedName, loggedDept, mobNo, msgBody, "", "Pending", "0", "Compliance", connection);

						}
					}
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToOther"))
			{
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = cmplCmn.getEmployeeInfo(contactId);
				Object[] object = null;
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggedMob = object[2].toString();
						allotedBy = object[0].toString();
						if (object[4] != null)
						{
							loggedDept = object[4].toString();
						}
					}
				}
				List other = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							mobNo = object[2].toString();
							if (object[4] != null)
							{
								otherDept = object[4].toString();
							}
							allotedTo.append(empName + " ,");
							if (otherSmsFlag)
							{
								msgBody = cmplCmn.getMsgBodyComplaince(allotedBy, taskName, dueDate, escTime);
								cmnHelper.addMessage(empName, otherDept, mobNo, msgBody, "", "Pending", "0", "Compliance", connection);
							}
						}
					}
				}
				if (selfSmsFlag)
				{
					msgBody = cmplCmn.getMsgBodyComplainceReg(allotedTo.toString(), taskName, dueDate, escTime);
					cmnHelper.addMessage(allotedBy, loggedDept, loggedMob, msgBody, "", "Pending", "0", "Compliance", connection);
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToBoth"))
			{
				String mappedTeam = null;
				String alloteBy = null;
				boolean flag = false;
				StringBuilder allotedTo = new StringBuilder();
				Object[] object = null;
				List loggedUSer = cmplCmn.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggedMob = object[2].toString();
						allotedBy = object[0].toString();
						if (object[4] != null)
						{
							loggedDept = object[4].toString();
						}
					}
				}
				List other = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							mobNo = object[2].toString();
							if (!object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedTo.append(empName + " ,");
							}
							else
							{
								flag = true;
							}
							if (object[5].toString().equalsIgnoreCase(contactId))
							{
								alloteBy = "You";
							}
							else
							{
								alloteBy = allotedBy;
							}
							if (object[4] != null)
							{
								otherDept = object[4].toString();
							}
							if (otherSmsFlag)
							{
								msgBody = cmplCmn.getMsgBodyComplaince(alloteBy, taskName, dueDate, escTime);
								cmnHelper.addMessage(empName, otherDept, mobNo, msgBody, "", "Pending", "0", "Compliance", connection);
							}
						}
					}
				}
				if (allotedTo != null && allotedTo.toString().length() > 0)
				{
					mappedTeam = allotedTo.toString().substring(0, allotedTo.toString().length() - 2);
				}
				if (mappedTeam == null)
					mappedTeam = "You";
				else if (flag)
					mappedTeam = mappedTeam + " and You";

				if (selfSmsFlag)
				{
					msgBody = cmplCmn.getMsgBodyComplainceReg(mappedTeam, taskName, dueDate, escTime);
					cmnHelper.addMessage(allotedBy, loggedDept, loggedMob, msgBody, "", "Pending", "0", "Compliance", connection);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method smsSendComaplaince of class " + getClass(), e);
		}
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public void mailSendComaplaince(int maxId, String reminFor, String contactId, SessionFactory connection, boolean selfMailFlag, boolean otherMailFlag)
	{
		try
		{
			CommunicationHelper cmnHelper = new CommunicationHelper();
			ComplianceCommonOperation cmplCmn = new ComplianceCommonOperation();
			String mailBody = null;
			String empName = null;
			String empEmail = null;
			String empDept = null;
			String loggeName = null;
			String loggeEmail = null;
			String loggeDept = null;
			List<ComplianceDashboardBean> conmpData = cmplCmn.getComplainceData(maxId, contactId);
			String remaindTo = null;
			String taskName = null;
			if (conmpData != null && conmpData.size() > 0)
			{
				for (ComplianceDashboardBean c : conmpData)
				{
					remaindTo = c.getRemindTo();
					taskName = c.getTaskName();
				}
			}
			Object[] object = null;
			if (reminFor != null && reminFor.equalsIgnoreCase("remToSelf"))
			{
				if (contactId.equalsIgnoreCase(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1))))
				{
					if (selfMailFlag)
					{
						List loggeddata = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
						for (Iterator iterator = loggeddata.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								empName = object[0].toString();
								empEmail = object[1].toString();
								empDept = object[4].toString();
							}
						}
						mailBody = cmplCmn.getMailBodyComplaince(conmpData, "allot", empName, reminFor, "");
						cmnHelper.addMail(empName, empDept, empEmail, "Task Allotment Alert: " + taskName + "", mailBody, "", "Pending", "0", "", "Compliance", connection);
					}
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToOther"))
			{
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = cmplCmn.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggeName = object[0].toString();
						loggeEmail = object[1].toString();
						loggeDept = object[4].toString();
					}
				}
				List other = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							empEmail = object[1].toString();
							empDept = object[4].toString();
							allotedTo.append(empName + " ,");
							if (otherMailFlag)
							{
								mailBody = cmplCmn.getMailBodyComplaince(conmpData, "allot", empName, reminFor, loggeName);
								cmnHelper.addMail(empName, empDept, empEmail, "Task Allotment Alert: " + taskName + "", mailBody, "", "Pending", "0", "", "Compliance", connection);
							}
						}
					}
				}
				if (selfMailFlag)
				{
					mailBody = cmplCmn.getMailBodyComplaince(conmpData, "confi", loggeName, reminFor, allotedTo.substring(0, allotedTo.toString().length() - 1));
					cmnHelper.addMail(loggeName, loggeDept, loggeEmail, "Task Registration Alert: " + taskName + "", mailBody, "", "Pending", "0", "", "Compliance", connection);
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToBoth"))
			{
				String mappedTeam = null;
				String allotedBy = null;
				boolean flag = false;
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = cmplCmn.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggeName = object[0].toString();
						loggeEmail = object[1].toString();
						loggeDept = object[4].toString();
					}
				}
				List other = cmplCmn.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							empEmail = object[1].toString();
							empDept = object[4].toString();
							if (!object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedTo.append(empName + " ,");
							}
							else
							{
								flag = true;
							}
							if (object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedBy = "You";
							}
							else
							{
								allotedBy = loggeName;
							}
							if (otherMailFlag)
							{
								mailBody = cmplCmn.getMailBodyComplaince(conmpData, "allot", empName, reminFor, allotedBy);
								cmnHelper.addMail(empName, empDept, empEmail, "Task Allotment Alert: " + taskName + "", mailBody, "", "Pending", "0", "", "Compliance", connection);
							}
						}
					}
				}
				if (allotedTo != null && allotedTo.toString().length() > 0)
				{
					mappedTeam = allotedTo.toString().substring(0, allotedTo.toString().length() - 2);
				}
				if (mappedTeam == null)
					mappedTeam = "You";
				else if (flag)
					mappedTeam = mappedTeam + " and You";

				if (selfMailFlag)
				{
					mailBody = cmplCmn.getMailBodyComplaince(conmpData, "confi", loggeName, reminFor, mappedTeam);
					cmnHelper.addMail(loggeName, loggeDept, loggeEmail, "Task Registration Alert: " + taskName + "", mailBody, "", "Pending", "0", "", "Compliance", connection);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * @SuppressWarnings("rawtypes") public String getUserDeptId(String
	 * userName) { String userDeptId = null; try { List loggedUserData = new
	 * HelpdeskUniversalAction
	 * ().getEmpDataByUserName(Cryptography.encrypt(userName,
	 * DES_ENCRYPTION_KEY), deptLevel); if (loggedUserData != null &&
	 * loggedUserData.size() > 0) { for (Iterator iterator =
	 * loggedUserData.iterator(); iterator.hasNext();) { Object[] object =
	 * (Object[]) iterator.next(); if (deptLevel.equalsIgnoreCase("2")) {
	 * userDeptId = object[4].toString(); } else if
	 * (deptLevel.equalsIgnoreCase("1")) { userDeptId = object[3].toString(); }
	 * } } } catch (Exception e) { e.printStackTrace(); } return userDeptId; }
	 */

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public List getCompIdPrefix_Suffix(String deptId, String frq)
	{
		List dataList = new ArrayList();
		String complSeries = new HelpdeskUniversalHelper().getTicketNo(deptId, "COMPL", connectionSpace);
		if (!frq.equalsIgnoreCase("-1"))
		{
			if (frq.equalsIgnoreCase("OT") || frq.equalsIgnoreCase("BM") || frq.equalsIgnoreCase("HY"))
			{
				frq = String.valueOf(frq.charAt(0));
			}
		}
		if (complSeries != null)
		{
			String prefix = complSeries.substring(0, 3);
			prefix = prefix.concat(frq);
			dataList.add(prefix);
			String suffix = complSeries.substring(4, complSeries.length());
			dataList.add(suffix);
		}
		return dataList;
	}

	// SETTERS AND GETTERS
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public File getUploadDoc()
	{
		return uploadDoc;
	}

	public void setUploadDoc(File uploadDoc)
	{
		this.uploadDoc = uploadDoc;
	}

	public String getUploadDocContentType()
	{
		return uploadDocContentType;
	}

	public void setUploadDocContentType(String uploadDocContentType)
	{
		this.uploadDocContentType = uploadDocContentType;
	}

	public String getUploadDocFileName()
	{
		return uploadDocFileName;
	}

	public void setUploadDocFileName(String uploadDocFileName)
	{
		this.uploadDocFileName = uploadDocFileName;
	}

	public File getUploadDoc1()
	{
		return uploadDoc1;
	}

	public void setUploadDoc1(File uploadDoc1)
	{
		this.uploadDoc1 = uploadDoc1;
	}

	public String getUploadDoc1ContentType()
	{
		return uploadDoc1ContentType;
	}

	public void setUploadDoc1ContentType(String uploadDoc1ContentType)
	{
		this.uploadDoc1ContentType = uploadDoc1ContentType;
	}

	public String getUploadDoc1FileName()
	{
		return uploadDoc1FileName;
	}

	public void setUploadDoc1FileName(String uploadDoc1FileName)
	{
		this.uploadDoc1FileName = uploadDoc1FileName;
	}

	
}