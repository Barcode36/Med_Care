package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class ComplianceEditAction extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	private String selectedId;
	private String taskName,taskType,taskBrief,msg,dueDate,dueTime,frequency,remind3rd,remind2nd,remind1st;
	private String remindMode,remindTo,selectedRemind2Cont,selectedEsc1Cont,selectedEsc2Cont,selectedEsc3Cont,selectedEsc4Cont;
	private Map<String, String> frequencyMap,viaFrom,remindToMap,emplMap,escalationMap,escL1Emp;
	private Map<String, String>	escContMap1,escContMap2,escContMap3,escContMap4;
	private JSONArray jsonArr = null;
	private String l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration;
	
	public String getCompliance4EditByEdit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				frequencyMap = new LinkedHashMap<String, String>();
				if(selectedId!=null && !selectedId.equalsIgnoreCase("undefined"))
				{
					StringBuilder contIdss = new StringBuilder();
					ComplianceCommonOperation CCO = new ComplianceCommonOperation();
					String deptId,escalation = null;
					String reminder_for = null,esc_level_1 = null;
					String esc_level_2 = null,esc_level_3 = null,esc_level_4 = null,budgetary,apply_working_day;
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					
					String userContID = null,empId = null,userDeptId = null;
					String str[] = CUA.getEmpDetailsByUserName("COMPL",userName);
					if(str!=null && str.length>0)
					{
						userContID = "#"+str[0]+"#";
						empId = str[6];
						userDeptId = str[4];
					}
					
					StringBuilder query = new StringBuilder();
					query.append("SELECT CT.taskName,TY.taskType,DEPT.id,COMP.frequency,COMP.escalation,COMP.taskBrief,COMP.msg,COMP.dueDate,");
					query.append("COMP.dueTime,COMP.reminder_for,COMP.remindMode,COMP.escalation_level_1,COMP.l1EscDuration,");
					query.append("COMP.escalation_level_2,COMP.l2EscDuration,COMP.escalation_level_3,COMP.l3EscDuration,");
					query.append("COMP.escalation_level_4,COMP.l4EscDuration,COMP.budgetary,COMP.apply_working_day ");
					query.append("FROM compliance_details AS COMP ");
					query.append("INNER JOIN compliance_task AS CT ON CT.id=COMP.taskName ");
					query.append("INNER JOIN compl_task_type AS TY ON TY.id=CT.taskType ");
					query.append("INNER JOIN department AS DEPT ON DEPT.id=TY.departName ");
					query.append("WHERE COMP.id="+selectedId);
					System.out.println("Main Query ::: "+query.toString());
					List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							taskName = CUA.getValueWithNullCheck(object[0]);
							taskType = CUA.getValueWithNullCheck(object[1]);
							deptId = CUA.getValueWithNullCheck(object[2]);
							frequency = CUA.getValueWithNullCheck(object[3]);
							escalation = CUA.getValueWithNullCheck(object[4]);
							taskBrief = CUA.getValueWithNullCheck(object[5]);
							msg = CUA.getValueWithNullCheck(object[6]);
							dueDate = CUA.getValueWithNullCheck(object[7]);
							dueTime = CUA.getValueWithNullCheck(object[8]);
							reminder_for = "#"+CUA.getValueWithNullCheck(object[9]);
							remindMode = CUA.getValueWithNullCheck(object[10]);
							esc_level_1 = "#"+CUA.getValueWithNullCheck(object[11]);
							l1EscDuration = CUA.getValueWithNullCheck(object[12]);
							esc_level_2 = "#"+CUA.getValueWithNullCheck(object[13]);
							l2EscDuration = CUA.getValueWithNullCheck(object[14]);
							esc_level_3 = "#"+CUA.getValueWithNullCheck(object[15]);
							l3EscDuration = CUA.getValueWithNullCheck(object[16]);
							esc_level_4 = "#"+CUA.getValueWithNullCheck(object[17]);
							l4EscDuration = CUA.getValueWithNullCheck(object[18]);
							budgetary = CUA.getValueWithNullCheck(object[19]);
							apply_working_day = CUA.getValueWithNullCheck(object[20]);
							
							
						}
						frequencyMap = new LinkedHashMap<String, String>();
						getFrequencyMap(frequency);
						getReminder(selectedId);
						
						// Escalation Map
						escalationMap = new LinkedHashMap<String, String>();
						escContMap1 = new LinkedHashMap<String, String>();
						escContMap2 = new LinkedHashMap<String, String>();
						escContMap3 = new LinkedHashMap<String, String>();
						escContMap4 = new LinkedHashMap<String, String>();
						
						escL1Emp = new LinkedHashMap<String, String>();
						escL1Emp = CCO.getComplianceAllContacts(userDeptId, "COMPL");
						
						if(escalation.equalsIgnoreCase("Y"))
						{
							escalationMap.put("Y", "Yes");
							escalationMap.put("N", "No");
							
							// 1st Escalation Map
							if(esc_level_1!=null && !esc_level_1.equalsIgnoreCase("#NA"))
							{
								escContMap1.putAll(escL1Emp); 
								//= CCO.getComplianceAllContacts(userDeptId, "COMPL");
								selectedEsc1Cont = "0"+esc_level_1.replace("#", ",")+"0";
								contIdss.append(selectedEsc1Cont);
							}
							
							// 2nd Escalation Map
							if(esc_level_2!=null && !esc_level_2.equalsIgnoreCase("#NA"))
							{
								escContMap2 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
								selectedEsc2Cont = "0"+esc_level_2.replace("#", ",")+"0";
								contIdss.append(","+selectedEsc2Cont);
							}
							else
							{
								escContMap2 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
							}
							
							// 3rd Escalation Map
							if(esc_level_3!=null && !esc_level_3.equalsIgnoreCase("#NA"))
							{
								escContMap3 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
								selectedEsc3Cont = "0"+esc_level_3.replace("#", ",")+"0";
								contIdss.append(","+selectedEsc3Cont);
							}
							else
							{
								escContMap3 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
							}
							
							if(esc_level_4!=null && !esc_level_4.equalsIgnoreCase("#NA"))
							{
								escContMap4 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
								selectedEsc4Cont = "0"+esc_level_4.replace("#", ",")+"0";
							}
							else
							{
								escContMap4 = CCO.getComplianceContacts(contIdss.toString(), userDeptId, "COMPL");
							}
						}
						else if(escalation.equalsIgnoreCase("N"))
						{
							escalationMap.put("N", "No");
							escalationMap.put("Y", "Yes");
						}
						
						//Remind Via
						viaFrom = new LinkedHashMap<String, String>();
						viaFrom.put("SMS", "SMS ");
						viaFrom.put("Mail", "Mail ");
						viaFrom.put("Both", "Both ");
						viaFrom.put("None", "None ");
						
						// Remind To Map
						remindToMap = new HashMap<String, String>();
						remindToMap.put("remToSelf", "Self ");
						remindToMap.put("remToOther", "Other ");
						remindToMap.put("remToBoth", "Both ");
						
						// Contact Map
						emplMap = new LinkedHashMap<String, String>();
						 						
						emplMap = CCO.getComplianceContacts(empId, userDeptId, "COMPL");
						if(reminder_for.contains(userContID))
						{
							if(reminder_for.length()==userContID.length())
							{
								remindTo = "remToSelf";
							}
							else if(reminder_for.length()>userContID.length())
							{ 
								remindTo = "remToBoth";
								selectedRemind2Cont = "0"+reminder_for.replace("#", ",")+"0";
							}
						}
						else
						{
							remindTo = "remToOther";
							selectedRemind2Cont = "0"+reminder_for.replace("#", ",")+"0";
						}
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			catch(Exception e)
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
	
	public void getFrequencyMap(String frequency)
	{
		if(!frequency.equals("NA")) 
		{
			if(frequency.equals("OT"))
			{
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("D"))
			{
				frequencyMap.put("D", "Daily");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("W"))
			{
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("M"))
			{
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("BM"))
			{
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("Q"))
			{
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("HY"))
			{
				frequencyMap.put("HY", "Half Yearly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("Y", "Yearly");
			}
			else if(frequency.equals("HY"))
			{
				frequencyMap.put("Y", "Yearly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
			}
			else if(frequency.equals("O"))
			{
				frequencyMap.put("O", "Other");
				frequencyMap.put("Y", "Yearly");
				frequencyMap.put("OT", "One-Time");
				frequencyMap.put("D", "Daily");
				frequencyMap.put("W", "Weekly");
				frequencyMap.put("M", "Monthly");
				frequencyMap.put("BM", "Bi-Monthly");
				frequencyMap.put("Q", "Quaterly");
				frequencyMap.put("HY", "Half Yearly");
			}
		}
	}
	
	public void getReminder(String compId)
	{
		String  reminQry= "SELECT reminder_name,remind_date from compliance_reminder WHERE compliance_id IN("+compId+")";
		System.out.println("Reminder Query :::: "+reminQry);
		List remindData = new createTable().executeAllSelectQuery(reminQry, connectionSpace);
		if(remindData!=null && remindData.size()>0)
		{
			for (Iterator iterator = remindData.iterator(); iterator.hasNext();) 
			   {
				  Object[] object = (Object[]) iterator.next();
				  if (object[0].toString().equalsIgnoreCase("Reminder 1")) 
				  {
					  remind3rd = object[1].toString();
				  }
				  else if (object[0].toString().equalsIgnoreCase("Reminder 2")) 
				  {
					  remind2nd = object[1].toString();
				  }
				  else if (object[0].toString().equalsIgnoreCase("Reminder 3")) 
				  {
					  remind1st = object[1].toString();
				  }
			   }
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
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String str[] = CUA.getEmpDetailsByUserName("COMPL",userName);
				if(str!=null && str.length>0)
				{
					deptId = str[4];
				}
				if (deptId != null && !deptId.equalsIgnoreCase("") && selectedId!=null)
				{
					jsonArr = new JSONArray();
					StringBuilder query = new StringBuilder();
					query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
					query.append(" WHERE cc.id NOT IN(" + selectedId + ") AND forDept_id=" + deptId + " AND moduleName='COMPL' AND work_status!=1 order by empName asc");
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
								jsonArr.add(formDetailsJson);
							}
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
	
	@SuppressWarnings("static-access")
	public String EditComplianceAction()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(selectedId!=null)
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					ComplianceCommonOperation CCO = new ComplianceCommonOperation();
					ConfigureComplianceAction CCA= new ConfigureComplianceAction();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					@SuppressWarnings("unchecked")
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					@SuppressWarnings("rawtypes")
					Iterator it = requestParameterNames.iterator();
					String dueDate = null,reminFor = null;
					StringBuilder strID = new StringBuilder();
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", selectedId);
					
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("taskBrief")
								&& !parmName.equalsIgnoreCase("msg") && !parmName.equalsIgnoreCase("selectedId")
								&& !parmName.equalsIgnoreCase("reminder1") && !parmName.equalsIgnoreCase("reminder2") && !parmName.equalsIgnoreCase("reminder3"))
						{

							if (parmName.equals("dueDate"))
							{
								dueDate = DateUtil.convertDateToUSFormat(paramValue);
								if (dueDate != null)
								{
									boolean status = false;
									status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), dueDate + " " + request.getParameter("dueTime"));
									if (!status)
									{
										dueDate = CCA.getUpdateDueDate(request.getParameter("frequency"),dueDate,request.getParameter("remind_days"),request.getParameter("customFrqOn"));
									}
									setVariables.put(parmName, dueDate);
								}
							}
							// REminder for
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToSelf"))
							{
								String userContID = null;
								userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
								
								if(userContID!=null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));
								
								setVariables.put(parmName, strID.toString());
								
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToOther"))
							{
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
								
								setVariables.put(parmName, strID.toString());
								
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToBoth"))
							{
								reminFor = paramValue;
								
								String userContID = null;
								userContID=CUA.getEmpDetailsByUserName("COMPL",userName)[0];
								
								if(userContID!=null && !userContID.equalsIgnoreCase("NA"))
									strID.append(userContID.concat("#"));
								
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
								setVariables.put(parmName, strID.toString());
							}
							else if (parmName.equals("l1EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(dueDate, request.getParameter("dueTime"),paramValue, "Y").split("#");

								setVariables.put(parmName, paramValue);
								setVariables.put("escalate_date", escDateTime[0]);
								setVariables.put("escalate_time", escDateTime[1]);
								setVariables.put("current_esc_level", "1");
							}
							else if (parmName.equals("l2EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l1EscDuration") == null || request.getParameter("l1EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),paramValue, "Y").split("#");
								
								setVariables.put(parmName, paramValue);
								setVariables.put("escalate_date", escDateTime[0]);
								setVariables.put("escalate_time", escDateTime[1]);
								setVariables.put("current_esc_level", "2");
							}
							else if (parmName.equals("l3EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l2EscDuration") == null || request.getParameter("l2EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),paramValue, "Y").split("#");
								setVariables.put(parmName, paramValue);
								setVariables.put("escalate_date", escDateTime[0]);
								setVariables.put("escalate_time", escDateTime[1]);
								setVariables.put("current_esc_level", "3");
							}
							else if (parmName.equals("l4EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l3EscDuration") == null || request.getParameter("l3EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),
										paramValue, "Y").split("#");

								setVariables.put(parmName, paramValue);
								setVariables.put("escalate_date", escDateTime[0]);
								setVariables.put("escalate_time", escDateTime[1]);
								setVariables.put("current_esc_level", "4");
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
								setVariables.put(parmName, strIDD.toString());
							}
							else if(parmName.equalsIgnoreCase("escalation"))
							{
								if(paramValue.equalsIgnoreCase("N"))
								{
									setVariables.put("escalation_level_1","(NULL)");
									setVariables.put("escalation_level_2","(NULL)");
									setVariables.put("escalation_level_3","(NULL)");
									setVariables.put("escalation_level_4","(NULL)");
									setVariables.put("l1EscDuration","00:00");
									setVariables.put("l2EscDuration","00:00");
									setVariables.put("l3EscDuration","00:00");
									setVariables.put("l4EscDuration","00:00");
									setVariables.put("escalate_date","(NULL)");
									setVariables.put("escalate_time","(NULL)");
									setVariables.put("current_esc_level","(NULL)");
								}
								setVariables.put(parmName,paramValue);
							}
							else if(!parmName.equalsIgnoreCase("emp_id"))
							{
								setVariables.put(parmName,paramValue);
							}
						}
						setVariables.put("msg", request.getParameter("msg"));
						setVariables.put("taskBrief", request.getParameter("taskBrief"));
						setVariables.put("userName",userName);
						setVariables.put("date",DateUtil.getCurrentDateUSFormat());
						setVariables.put("time",DateUtil.getCurrentTimeHourMin());
						
					}
					boolean status= cbt.updateTableColomn("compliance_details", setVariables, whereCondition, connectionSpace);
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
								String newDate=CCA.getUpdateDueDate(request.getParameter("frequency"), reminDate1,request.getParameter("remind_days"),request.getParameter("customFrqOn"));
								if(newDate!=null)
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
								String newDate=CCA.getUpdateDueDate(request.getParameter("frequency"), reminDate2,request.getParameter("remind_days"),request.getParameter("customFrqOn"));
								if(newDate!=null)
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
								String newDate=CCA.getUpdateDueDate(request.getParameter("frequency"), reminDate3,request.getParameter("remind_days"),request.getParameter("customFrqOn"));
								if(newDate!=null)
									dateList.add(newDate);
							}
							else
							{
								dateList.add(reminDate3);
							}
						}
						if (dateList != null && dateList.size() > 0)
						{
							cbt.deleteAllRecordForId("compliance_reminder", "compliance_id", selectedId, connectionSpace);
							
							for (int i = 0; i < dateList.size(); i++)
							{
								Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
								dataWithColumnName.put("due_date", dueDate);
								dataWithColumnName.put("due_time", request.getParameter("dueTime"));
								dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
								dataWithColumnName.put("reminder_status", "0");
								dataWithColumnName.put("status", "Pending");
								dataWithColumnName.put("compliance_id", selectedId);
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
								CCO.saveComplReminder(dataWithColumnName);
							}
						}
					}
				}
				addActionMessage("Operation Task Edit Sucessfully!!!");
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Some Problem In Operation Task Edit");
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
	
	public String getSelectedId()
	{
		return selectedId;
	}
	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getTaskBrief()
	{
		return taskBrief;
	}

	public void setTaskBrief(String taskBrief)
	{
		this.taskBrief = taskBrief;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getDueTime()
	{
		return dueTime;
	}

	public void setDueTime(String dueTime)
	{
		this.dueTime = dueTime;
	}

	public Map<String, String> getFrequencyMap()
	{
		return frequencyMap;
	}

	public void setFrequencyMap(Map<String, String> frequencyMap)
	{
		this.frequencyMap = frequencyMap;
	}

	public String getRemind3rd()
	{
		return remind3rd;
	}

	public void setRemind3rd(String remind3rd)
	{
		this.remind3rd = remind3rd;
	}

	public String getRemind2nd()
	{
		return remind2nd;
	}

	public void setRemind2nd(String remind2nd)
	{
		this.remind2nd = remind2nd;
	}

	public String getRemind1st()
	{
		return remind1st;
	}

	public void setRemind1st(String remind1st)
	{
		this.remind1st = remind1st;
	}

	public Map<String, String> getViaFrom()
	{
		return viaFrom;
	}

	public void setViaFrom(Map<String, String> viaFrom)
	{
		this.viaFrom = viaFrom;
	}

	public String getRemindMode()
	{
		return remindMode;
	}

	public void setRemindMode(String remindMode)
	{
		this.remindMode = remindMode;
	}

	public Map<String, String> getRemindToMap()
	{
		return remindToMap;
	}

	public void setRemindToMap(Map<String, String> remindToMap)
	{
		this.remindToMap = remindToMap;
	}

	public String getRemindTo()
	{
		return remindTo;
	}

	public void setRemindTo(String remindTo)
	{
		this.remindTo = remindTo;
	}

	public String getSelectedRemind2Cont()
	{
		return selectedRemind2Cont;
	}

	public void setSelectedRemind2Cont(String selectedRemind2Cont)
	{
		this.selectedRemind2Cont = selectedRemind2Cont;
	}

	public Map<String, String> getEmplMap()
	{
		return emplMap;
	}

	public void setEmplMap(Map<String, String> emplMap)
	{
		this.emplMap = emplMap;
	}

	public Map<String, String> getEscalationMap()
	{
		return escalationMap;
	}

	public void setEscalationMap(Map<String, String> escalationMap)
	{
		this.escalationMap = escalationMap;
	}

	public Map<String, String> getEscL1Emp()
	{
		return escL1Emp;
	}

	public void setEscL1Emp(Map<String, String> escL1Emp)
	{
		this.escL1Emp = escL1Emp;
	}

	public String getSelectedEsc1Cont()
	{
		return selectedEsc1Cont;
	}

	public void setSelectedEsc1Cont(String selectedEsc1Cont)
	{
		this.selectedEsc1Cont = selectedEsc1Cont;
	}

	public String getSelectedEsc2Cont()
	{
		return selectedEsc2Cont;
	}

	public void setSelectedEsc2Cont(String selectedEsc2Cont)
	{
		this.selectedEsc2Cont = selectedEsc2Cont;
	}

	public String getSelectedEsc3Cont()
	{
		return selectedEsc3Cont;
	}

	public void setSelectedEsc3Cont(String selectedEsc3Cont)
	{
		this.selectedEsc3Cont = selectedEsc3Cont;
	}

	public String getSelectedEsc4Cont()
	{
		return selectedEsc4Cont;
	}

	public void setSelectedEsc4Cont(String selectedEsc4Cont)
	{
		this.selectedEsc4Cont = selectedEsc4Cont;
	}

	public Map<String, String> getEscContMap1()
	{
		return escContMap1;
	}

	public void setEscContMap1(Map<String, String> escContMap1)
	{
		this.escContMap1 = escContMap1;
	}

	public Map<String, String> getEscContMap2()
	{
		return escContMap2;
	}

	public void setEscContMap2(Map<String, String> escContMap2)
	{
		this.escContMap2 = escContMap2;
	}

	public Map<String, String> getEscContMap3()
	{
		return escContMap3;
	}

	public void setEscContMap3(Map<String, String> escContMap3)
	{
		this.escContMap3 = escContMap3;
	}

	public Map<String, String> getEscContMap4()
	{
		return escContMap4;
	}

	public void setEscContMap4(Map<String, String> escContMap4)
	{
		this.escContMap4 = escContMap4;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getL1EscDuration()
	{
		return l1EscDuration;
	}

	public void setL1EscDuration(String l1EscDuration)
	{
		this.l1EscDuration = l1EscDuration;
	}

	public String getL2EscDuration()
	{
		return l2EscDuration;
	}

	public void setL2EscDuration(String l2EscDuration)
	{
		this.l2EscDuration = l2EscDuration;
	}

	public String getL3EscDuration()
	{
		return l3EscDuration;
	}

	public void setL3EscDuration(String l3EscDuration)
	{
		this.l3EscDuration = l3EscDuration;
	}

	public String getL4EscDuration()
	{
		return l4EscDuration;
	}

	public void setL4EscDuration(String l4EscDuration)
	{
		this.l4EscDuration = l4EscDuration;
	}
	
	
}



