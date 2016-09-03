package com.Over2Cloud.ctrl.compliance.activityboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.ComplianceTransferAction;
import com.Over2Cloud.ctrl.compliance.ConfigureComplianceAction;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.ComplianceTaskAction;

@SuppressWarnings("serial")
public class ComplianceActivityBoard extends GridPropertyBean
{
	private List<Object> masterViewList;
	private String deptId;
	private String searchPeriodOn;
	private String fromDate;
	private String toDate;
	private String frequency;
	private String actionStatus;
	private String dataFrom;
	private Map<String, String> headermap;
	private Map<String, String> deptName;
	private Map<String, String> taskTypeMap;
	private String complID;
	private Map<String, String> complDetails;
	private Map<String, String> checkListDetails;
	private List<ComplianceDashboardBean> doneDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> allotDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> allotByDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> budgetDetails = new ArrayList<ComplianceDashboardBean>();
	private Map<String, String> statusDetails = null;
	private Map<String, String> escalationDetails = null;
	private Map<String, String> reminderDetails = null;
	private String taskId;
	private String budget;
	private String timmings;
	private String shareStatus;
	private String searching;
	private String type;
	private String taskName;
	private String fileName;
	private InputStream inputStream;
	private String doneDoc;
	private String dataFor;
	private String docName;
	private Map<String, String> workingmap;

	@SuppressWarnings("rawtypes")
	public String viewReminderFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				reminderDetails = new LinkedHashMap<String, String>();
				String qry = "SELECT cc.remindMode,cr.due_time,cr.remind_date FROM compliance_details AS cc INNER JOIN compliance_reminder AS cr On cr.compliance_id=cc.id WHERE cc.id=" + complID + "  AND reminder_code!='S'";
				List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (data != null && data.size() > 0)
				{
					int i = 1;
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						reminderDetails.put("Mode", object[0].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						i++;
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
	public String viewBudgetedFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT budgetary,actual_expenses,difference,actionTakenDate FROM compliance_report WHERE complID=" + complID + " AND actionTaken IN('Done','Recurring') ORDER BY id DESC LIMIT 3;";
				// / System.out.println(qry);
				List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						ComplianceDashboardBean CDB = new ComplianceDashboardBean();
						Object[] object = (Object[]) iterator.next();
						CDB.setBudgeted(getValueWithNullCheck(object[0]));
						CDB.setActual(getValueWithNullCheck(object[1]));
						CDB.setDifference(getValueWithNullCheck(object[2]));
						CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[3])));
						budgetDetails.add(CDB);
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
	public String viewAllotedToFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT reminder_for FROM compliance_details WHERE id=" + complID;
				List reminderFor = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (reminderFor != null && reminderFor.size() > 0)
				{
					String emp = reminderFor.toString().replace("#", ",").substring(1, reminderFor.toString().length() - 2);
					qry = "SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id = emp.id INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.id IN(" + emp
							+ ")";
					List data = cbt.executeAllSelectQuery(qry, connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							Object[] object = (Object[]) iterator.next();
							CDB.setRemindTo(object[0].toString());
							CDB.setMobNo(object[1].toString());
							CDB.setEmailId(object[2].toString());
							CDB.setDepartName(object[3].toString());
							allotDetails.add(CDB);
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

	@SuppressWarnings("rawtypes")
	public String viewAllotedByFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT userName FROM compliance_details WHERE id=" + complID;
				List reminderFor = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (reminderFor != null && reminderFor.size() > 0)
				{
					String emp = Cryptography.encrypt(reminderFor.get(0).toString(), DES_ENCRYPTION_KEY);
					qry = "SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp INNER JOIN useraccount AS uc ON uc.id = emp.useraccountid INNER JOIN department AS dept ON dept.id=emp.deptname WHERE uc.userID ='"
							+ emp + "'";
					List data = cbt.executeAllSelectQuery(qry, connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							Object[] object = (Object[]) iterator.next();
							CDB.setRemindTo(object[0].toString());
							CDB.setMobNo(object[1].toString());
							CDB.setEmailId(object[2].toString());
							CDB.setDepartName(object[3].toString());
							allotByDetails.add(CDB);
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

	@SuppressWarnings("rawtypes")
	public String viewStatus()
	{
		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT actionTaken,actionTakenDate,actionTakenTime,comments,snoozeDate,snoozeTime,rescheduleDate,");
			query.append("rescheduleTime,document_action_1,document_action_2,actual_expenses,userName,dueDate,dueTime,id,document_takeaction ");
			query.append("FROM compliance_report WHERE complID='" + complID + "' AND complainceId='" + taskId + "' ORDER BY actionTakenDate DESC ");
			// System.out.println("query is as:::::::"+query.toString());
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						ComplianceDashboardBean CDB = new ComplianceDashboardBean();
						 if(object[0]!=null)
							{
								CDB.setActionStatus(CUA.getValueWithNullCheck(object[0]));
								String interval = DateUtil.time_difference(object[12].toString(),object[13].toString(),object[1].toString(),object[2].toString());
								CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(object[1].toString())+", "+object[2].toString());
								if (object[4]!=null && object[5]!=null)
								{
									CDB.setDueDate(DateUtil.convertDateToIndianFormat(object[4].toString())+", "+object[5].toString());
								}
								CDB.setComment(CUA.getValueWithNullCheck(object[3]));
								CDB.setName(DateUtil.makeTitle(Cryptography.decrypt(CUA.getValueWithNullCheck(object[11]), DES_ENCRYPTION_KEY)));
								CDB.setCost(CUA.getValueWithNullCheck(object[10]));
								CDB.setDifference(interval);
								if (object[8]!=null) 
								{
									String str=object[8].toString().substring(object[8].toString().lastIndexOf("//")+2, object[8].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath(docName);
								} else 
								{
									CDB.setDocPath("NA");
								}
								if (object[9]!=null) 
								{
									String str=object[9].toString().substring(object[9].toString().lastIndexOf("//")+2, object[9].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath1(docName);
								} else 
								{
									CDB.setDocPath1("NA");
								}
								if (object[15]!=null && !object[15].toString().equalsIgnoreCase("")) 
								{
									String str=object[15].toString().substring(object[15].toString().lastIndexOf("//")+2, object[15].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath2(docName);
								} 
								else 
								{
									CDB.setDocPath2("NA");
								}
								CDB.setId(object[14].toString());
								doneDetails.add(CDB);
								interval="";
							}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	@SuppressWarnings({"rawtypes"})
	public String viewStatusFormatter()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			String qry = "SELECT actionTakenDate,actionTakenTime FROM compliance_report WHERE complID ='" + complID + "' AND actionTaken='Pending' ORDER BY id DESC LIMIT 1";
			 System.out.println("QUERY ::: "+qry);
			String actionTakenDate = null;

			List tempList = cbt.executeAllSelectQuery(qry, connectionSpace);
			if (tempList != null && tempList.size() > 0)
			{
				Object[] object1 = (Object[]) tempList.get(0);
				actionTakenDate = object1[0].toString();

				StringBuilder query = new StringBuilder();
				query.append("SELECT actionTaken,actionTakenDate,actionTakenTime,comments,snoozeDate,snoozeTime,rescheduleDate,");
				query.append("rescheduleTime,document_action_1,document_action_2,actual_expenses,userName,dueDate,dueTime,id,document_takeaction ");
				query.append("FROM compliance_report WHERE complID='"+complID+"' AND actionTaken !='Pending' AND actionTakenDate BETWEEN '"+actionTakenDate+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY actionTakenDate DESC");
				
			    System.out.println("query is as:::::::"+query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							 if(object[0]!=null)
							 {
								CDB.setActionStatus(CUA.getValueWithNullCheck(object[0]));
								String interval=null;
								boolean status=DateUtil.comparetwoDates(object[12].toString(), object[1].toString());
								if (status)
								{
									 interval = DateUtil.time_difference(object[12].toString(),object[13].toString(),object[1].toString(),object[2].toString());
								}
								else
								{
									 interval = DateUtil.time_difference(object[1].toString(),object[2].toString(),object[12].toString(),object[13].toString());
								}
								CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(object[1].toString())+", "+object[2].toString());
								if (object[4]!=null && object[5]!=null)
								{
									CDB.setDueDate(DateUtil.convertDateToIndianFormat(object[4].toString())+", "+object[5].toString());
								}
								CDB.setComment(CUA.getValueWithNullCheck(object[3]));
								CDB.setName(DateUtil.makeTitle(Cryptography.decrypt(CUA.getValueWithNullCheck(object[11]), DES_ENCRYPTION_KEY)));
								CDB.setCost(CUA.getValueWithNullCheck(object[10]));
								CDB.setDifference(interval);
								if (object[8]!=null) 
								{
									String str=object[8].toString().substring(object[8].toString().lastIndexOf("//")+2, object[8].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath(docName);
								} 
								else 
								{
									CDB.setDocPath("NA");
								}
								if (object[9]!=null) 
								{
									String str=object[9].toString().substring(object[9].toString().lastIndexOf("//")+2, object[9].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath1(docName);
								} 
								else 
								{
									CDB.setDocPath1("NA");
								}
								if (object[15]!=null && !object[15].toString().equalsIgnoreCase("")) 
								{
									String str=object[15].toString().substring(object[15].toString().lastIndexOf("//")+2, object[15].toString().length());
									String docName=str.substring(14,str.length());
									CDB.setDocPath2(docName);
								} 
								else 
								{
									CDB.setDocPath2("NA");
								}
								CDB.setId(object[14].toString());
								doneDetails.add(CDB);
							}
						}
					}
				}
			}
			 workingmap=new LinkedHashMap<String,String>();
			 qry="SELECT  holiday,fromTime,toTime FROM working_timing WHERE moduleName='COMPL' ORDER BY id DESC LIMIT 1";
			 List data=cbt.executeAllSelectQuery(qry, connectionSpace);
			 if (data!=null && !data.isEmpty())
			 {
				 Object object[]=(Object[])data.get(0);
				 qry="SELECT  day FROM working_timing WHERE moduleName='COMPL' ";
				 List data2=cbt.executeAllSelectQuery(qry, connectionSpace);
				 if (data2!=null && !data2.isEmpty())
				 {
					 Object a[]=data2.toArray();
					 if (object[0].toString().equalsIgnoreCase("N"))
					 {
						 workingmap.put("Working Days", a[0].toString()+" to "+a[a.length-1].toString()+" (No Holiday)");
					 }
					 else
					 {
						 workingmap.put("Working Days", a[0].toString()+" to "+a[a.length-1].toString()+" (Consider Holiday)");
					 }
					 workingmap.put("Working Hours", object[1].toString()+" Hrs to "+object[2].toString()+" Hrs");
					 
				 }
			 }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String viewTATFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				statusDetails = new LinkedHashMap<String, String>();
				escalationDetails = new LinkedHashMap<String, String>();
				String qry = "SELECT actionStatus,current_esc_level,dueDate,dueTime,escalation,escalation_level_1,l1EscDuration,escalation_level_2" + ",l2EscDuration,escalation_level_3,l3EscDuration,escalation_level_4,l4EscDuration FROM "
						+ "compliance_details WHERE id=" + complID;
				System.out.println(">>>>TAT Query::: " + qry);
				List level = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (level != null && level.size() > 0)
				{
					Object[] obj = (Object[]) level.get(0);
					statusDetails.put("Current Status", obj[0].toString());
					if (obj[1] != null)
					{
						statusDetails.put("Level", obj[1].toString());
					}
					else
					{
						statusDetails.put("Level", "1");
					}
					if (obj[4].toString().equalsIgnoreCase("Y"))
					{
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						if (obj[5] != null)
						{
							
							String date = obj[2].toString();
							String time = obj[3].toString();
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[6].toString(), "00:00", "Y", date, time, "COMPL");
							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);
							
							escalationDetails.put("L-2 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[5].toString().replace("#", ",").substring(0, (obj[5].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-2 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[7] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[8].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");
							
							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);
							
							escalationDetails.put("L-3 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[7].toString().replace("#", ",").substring(0, (obj[7].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-3 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[9] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[10].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");
							
							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);
							
							escalationDetails.put("L-4 Escalate Date & Time",  escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[9].toString().replace("#", ",").substring(0, (obj[7].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-4 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[11] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[12].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");
							
							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);
							
							escalationDetails.put("L-5 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[11].toString().replace("#", ",").substring(0, (obj[11].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-5 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
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

	@SuppressWarnings("rawtypes")
	public String beforeActivityBoardView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				fromDate = DateUtil.getNewDate("day", -7, DateUtil.getCurrentDateUSFormat());
				toDate = DateUtil.getCurrentDateUSFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String compId = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				String qry = "SELECT dept.id,dept.deptName From department AS dept INNER JOIN compliance_task AS ct ON ct.departName = dept.id INNER JOIN compliance_details AS cc ON cc.taskName=ct.id WHERE cc.id IN (" + compId + ") GROUP BY id";
				List deptList = cbt.executeAllSelectQuery(qry, connectionSpace);
				deptName = new LinkedHashMap<String, String>();
				taskTypeMap = new LinkedHashMap<String, String>();
				if (deptList != null && deptList.size() > 0)
				{
					for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							deptName.put(object[0].toString(), object[1].toString());
						}
					}
				}
				ComplianceTaskAction ct = new ComplianceTaskAction();
				String[] userData = new ComplianceUniversalAction().getEmpDataByUserName(userName);
				Map<String, String> tempMap = ct.getAllTaskType(userData[3]);
				taskTypeMap.put("All", "All Task Type");
				if (tempMap != null && tempMap.size() > 0)
				{
					for (Map.Entry<String, String> entry : tempMap.entrySet())
					{
						taskTypeMap.put(entry.getKey(), entry.getValue());
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
	{ "rawtypes", "unchecked" })
	public String fetchCounters()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String compId = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				StringBuilder complIds = new StringBuilder();
				if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					ComplianceCommonOperation CO = new ComplianceCommonOperation();
					String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="
							+ userEmpID + ")";
					List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
					List contactList = new ArrayList();
					if (shareDataCount != null && shareDataCount.size() > 0)
					{
						for (int i = 0; i < shareDataCount.size(); i++)
						{
							contactList.add("#" + shareDataCount.get(i).toString() + "#");
							String contactId = CO.getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
							if (contactId != null && !contactId.equalsIgnoreCase(""))
							{
								String str[] = contactId.split(",");
								for (int count = 0; count < str.length; count++)
								{
									contactList.add("#" + str[count] + "#");
								}
							}
						}
					}
					String contactId = CO.getMappedAllContactId(userEmpID, "COMPL");
					if (contactId != null && !contactId.equalsIgnoreCase(""))
					{
						String str[] = contactId.split(",");
						for (int i = 0; i < str.length; i++)
						{
							contactList.add("#" + str[i] + "#");
						}
					}
					List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details", connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							Object object1[] = (Object[]) iterator.next();
							String str = "#";
							str = str + object1[1].toString();
							for (int i = 0; i < contactList.size(); i++)
							{
								if (str.contains(contactList.get(i).toString()))
								{
									complIds.append(object1[0].toString() + ",");
								}
							}
						}
					}
					complIds.append(compId);
				}
				headermap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT actionStatus FROM compliance_details AS  comp " + "INNER JOIN compliance_report AS cr ON comp.id=cr.complID  " + "INNER JOIN compliance_task AS ts ON ts.id=comp.taskName  "
						+ "INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType  " + "INNER JOIN department AS dept ON dept.id=ts.departName  " + "WHERE ");
				if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareMe"))
				{
					query.append(" comp.id IN(" + compId + ") ");
				}
				else if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareBy"))
				{
					query.append(" comp.userName ='" + userName + "' ");
				}
				else if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					query.append(" comp.id IN(" + complIds.toString() + ") ");
				}
				if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
				{
					String str[] = fromDate.split("-");
					if (str[0].length() < 3)
					{
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
						toDate = DateUtil.convertDateToUSFormat(toDate);
						query.append(" AND cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					}
					else
					{
						query.append(" AND cr.actionTakenDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
				}
				else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("dueDate"))
				{
					String str[] = fromDate.split("-");
					if (str[0].length() < 3)
					{
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
						toDate = DateUtil.convertDateToUSFormat(toDate);
						query.append(" AND cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					}
					else
					{
						query.append(" AND cr.dueDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
				}
				else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("All"))
				{
					if (fromDate != null & !fromDate.equalsIgnoreCase("All") && toDate != null && !toDate.equalsIgnoreCase("All"))
					{
						String str[] = fromDate.split("-");
						if (str[0].length() < 3)
						{
							fromDate = DateUtil.convertDateToUSFormat(fromDate);
							toDate = DateUtil.convertDateToUSFormat(toDate);
						}
						query.append(" AND (cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
						query.append(" OR cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "')");
					}
				}
				if (timmings != null && timmings.equalsIgnoreCase("onTime"))
				{
					query.append(" AND comp.dueDate < '" + DateUtil.getCurrentDateUSFormat() + "'");
				}
				if (timmings != null && timmings.equalsIgnoreCase("offTime"))
				{
					query.append(" AND comp.dueDate > '" + DateUtil.getCurrentDateUSFormat() + "'");
				}
				if (frequency != null && !frequency.equalsIgnoreCase("All"))
				{
					query.append(" AND comp.frequency = '" + frequency + "'");
				}
				if (deptId != null && !deptId.equalsIgnoreCase("All"))
				{
					query.append(" AND dept.id = '" + deptId + "'");
				}
				if (actionStatus != null && !actionStatus.equalsIgnoreCase("ALL"))
				{
					query.append(" AND comp.actionStatus='" + actionStatus + "'");
				}
				if (budget != null && !budget.equalsIgnoreCase("All"))
				{
					if (budget.equalsIgnoreCase("NB"))
					{
						query.append(" AND comp.budgetary='NA' OR comp.budgetary=' '");
					}
					else
					{
						query.append(" AND comp.budgetary!='NA' AND comp.budgetary!=' '");
					}
				}
				if (searching != null && !searching.equalsIgnoreCase(""))
				{
					query.append(" AND ts.taskName LIKE '%" + searching + "%'");
				}
				if (type != null && !type.equalsIgnoreCase("All"))
				{
					query.append(" AND ctyp.id='" + type + "' ");
				}
				query.append(" GROUP BY comp.id");
				// System.out.println("VVVVVVv   "+query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				int total = 0;
				int pendingc = 0, snoozec = 0, donec = 0;
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object.toString().equalsIgnoreCase("Pending"))
						{
							pendingc = pendingc + 1;
						}
						else if (object.toString().equalsIgnoreCase("Snooze"))
						{
							snoozec = snoozec + 1;
						}
						else if (object.toString().equalsIgnoreCase("Done"))
						{
							donec = donec + 1;
						}
					}
					if (pendingc > 0)
					{
						headermap.put("Pending", String.valueOf(pendingc));
					}
					else
					{
						headermap.put("Pending", "0");
					}
					if (snoozec > 0)
					{
						headermap.put("Snooze", String.valueOf(snoozec));
					}
					else
					{
						headermap.put("Snooze", "0");
					}
					if (donec > 0)
					{
						headermap.put("Done", String.valueOf(donec));
					}
					else
					{
						headermap.put("Done", "0");
					}
					total = pendingc + snoozec + donec;
					headermap.put("Total", String.valueOf(total));

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
	{ "unchecked" })
	public String activityBoardView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> sessionvalues = new ArrayList<GridDataPropertyView>();
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.history");
				gpv.setHeadingName("History");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(30);
				gpv.setFormatter("historyFullView");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("dept.deptName");
				gpv.setHeadingName("Department");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ctyp.taskType");
				gpv.setHeadingName("Task Type");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ts.taskName");
				gpv.setHeadingName("Task Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compList");
				gpv.setHeadingName("Check List");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(40);
				gpv.setFormatter("checkList");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compid_prefix");
				gpv.setHeadingName("Task ID");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compid_suffix");
				gpv.setHeadingName("Compliance Suffix");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.frequency");
				gpv.setHeadingName("Frequency");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.dueDate");
				gpv.setHeadingName("Next Due On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(70);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.actionStatus");
				gpv.setHeadingName("Current Status");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("viewStatus");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.userName");
				gpv.setHeadingName("Last Action By & On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(130);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenDate");
				gpv.setHeadingName("Achieved On & At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				gpv.setFormatter("false");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenTime");
				gpv.setHeadingName("Achieved At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				gpv.setFormatter("false");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_date");
				gpv.setHeadingName("Escalation");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("viewTAT");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_time");
				gpv.setHeadingName("TATTime");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.budgetary");
				gpv.setHeadingName("Budgeted");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				gpv.setFormatter("budgetedView");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.reminder_for");
				gpv.setHeadingName("Allot To");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("allotedTo");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.userName");
				gpv.setHeadingName("Alloted By");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(70);
				gpv.setFormatter("allotedBy");

				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.reminder");
				gpv.setHeadingName("Reminder");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(120);
				gpv.setFormatter("reminder");
				viewPageColumns.add(gpv);

				session.put("masterViewProp", sessionvalues);

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
	{ "unchecked", "rawtypes" })
	public String viewActivityBoardData()
	{
		String retunString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				String compIdSeries = null;
				Object object = null;
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				StringBuilder complIds = new StringBuilder();
				compIdSeries = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					ComplianceCommonOperation CO = new ComplianceCommonOperation();
					String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="
							+ userEmpID + ")";
					List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
					List contactList = new ArrayList();
					if (shareDataCount != null && shareDataCount.size() > 0)
					{
						for (int i = 0; i < shareDataCount.size(); i++)
						{
							contactList.add("#" + shareDataCount.get(i).toString() + "#");
							String contactId = CO.getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
							if (contactId != null && !contactId.equalsIgnoreCase(""))
							{
								String str[] = contactId.split(",");
								for (int count = 0; count < str.length; count++)
								{
									contactList.add("#" + str[count] + "#");
								}
							}
						}
					}
					String contactId = CO.getMappedAllContactId(userEmpID, "COMPL");
					if (contactId != null && !contactId.equalsIgnoreCase(""))
					{
						String str[] = contactId.split(",");
						for (int i = 0; i < str.length; i++)
						{
							contactList.add("#" + str[i] + "#");
						}
					}
					List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details", connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							Object object1[] = (Object[]) iterator.next();
							String str = "#";
							str = str + object1[1].toString();
							for (int i = 0; i < contactList.size(); i++)
							{
								if (str.contains(contactList.get(i).toString()))
								{
									complIds.append(object1[0].toString() + ",");
								}
							}
						}
					}
					complIds.append(compIdSeries);
				}

				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName() != null)
								{
									if (gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("comp.id,");
									}
									else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
									{
										query.append("cr.userName AS actionBy,");
									}
									else
									{
										query.append(gpv.getColomnName().toString() + ",");
									}
								}
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM compliance_details AS comp " + "INNER JOIN compliance_report AS cr ON comp.id=cr.complID  " + "INNER JOIN compliance_task AS ts ON ts.id=comp.taskName  "
								+ "INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType  " + "INNER JOIN department AS dept ON dept.id=ts.departName  " + "WHERE ");
						if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareMe"))
						{
							query.append(" comp.id IN(" + compIdSeries + ") ");
						}
						else if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareBy"))
						{
							query.append(" comp.userName ='" + userName + "' ");
						}
						else if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
						{
							query.append(" comp.id IN(" + complIds.toString() + ") ");
						}
						if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
						{
							String str[] = fromDate.split("-");
							if (str[0].length() < 3)
							{
								fromDate = DateUtil.convertDateToUSFormat(fromDate);
								toDate = DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							}
							else
							{
								query.append(" AND cr.actionTakenDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							}
						}
						else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("dueDate"))
						{
							String str[] = fromDate.split("-");
							if (str[0].length() < 3)
							{
								fromDate = DateUtil.convertDateToUSFormat(fromDate);
								toDate = DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							}
							else
							{
								query.append(" AND cr.dueDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							}
						}
						else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("All"))
						{
							if (fromDate != null & !fromDate.equalsIgnoreCase("All") && toDate != null && !toDate.equalsIgnoreCase("All"))
							{
								String str[] = fromDate.split("-");
								if (str[0].length() < 3)
								{
									fromDate = DateUtil.convertDateToUSFormat(fromDate);
									toDate = DateUtil.convertDateToUSFormat(toDate);
								}
								query.append(" AND (cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
								query.append(" OR cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "')");
							}
						}
						if (timmings != null && timmings.equalsIgnoreCase("onTime"))
						{
							query.append(" AND comp.dueDate < '" + DateUtil.getCurrentDateUSFormat() + "'");
						}
						if (timmings != null && timmings.equalsIgnoreCase("offTime"))
						{
							query.append(" AND comp.dueDate > '" + DateUtil.getCurrentDateUSFormat() + "'");
						}
						if (frequency != null && !frequency.equalsIgnoreCase("All"))
						{
							query.append(" AND comp.frequency = '" + frequency + "'");
						}
						if (deptId != null && !deptId.equalsIgnoreCase("All"))
						{
							query.append(" AND dept.id = '" + deptId + "'");
						}
						if (actionStatus != null && !actionStatus.equalsIgnoreCase("ALL"))
						{
							query.append(" AND comp.actionStatus='" + actionStatus + "'");
						}
						if (budget != null && !budget.equalsIgnoreCase("All"))
						{
							if (budget.equalsIgnoreCase("NB"))
							{
								query.append(" AND comp.budgetary='NA' OR comp.budgetary=' '");
							}
							else
							{
								query.append(" AND comp.budgetary!='NA' AND comp.budgetary!=' '");
							}
						}
						if (searching != null && !searching.equalsIgnoreCase(""))
						{
							query.append(" AND ts.taskName LIKE '%" + searching + "%'");
						}
						if (type != null && !type.equalsIgnoreCase("All") && !type.equalsIgnoreCase("-1"))
						{
							query.append(" AND ctyp.id='" + type + "' ");
						}
						if (taskName != null && !taskName.equalsIgnoreCase("All") && !taskName.equalsIgnoreCase("-1"))
						{
							query.append(" AND ts.id='" + taskName + "' ");
						}
						query.append(" GROUP BY comp.id ORDER BY ts.taskName ASC ");
						System.out.println("query.toString()   " + query.toString());
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

						if (data != null && !data.isEmpty())
						{
							Object[] obdata = null;
							String complId = null;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								int k = 0;
								obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("undefined") && !obdata[k].toString().equalsIgnoreCase(""))
									{
										if (gpv.getColomnName() != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												if (gpv.getColomnName().equalsIgnoreCase("comp.dueDate"))
												{
													one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else
												{
													one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + obdata[k + 1].toString());
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("id"))
											{
												complId = obdata[k].toString();
												one.put(gpv.getColomnName(), obdata[k].toString());
												String query11 = "SELECT * FROM compliance_report WHERE complID=" + obdata[k].toString() + " GROUP BY  complainceId";
												List history = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (history != null)
												{
													one.put("comp.history", history.size());
												}
												else
												{
													one.put("comp.history", "NA");
												}
												query11 = "SELECT remind_date,remind_time FROM compliance_reminder WHERE compliance_id='" + complId + "'  AND status!='Done' AND reminder_code IN('D','R') ORDER BY remind_date ASC LIMIT 1";
												List remind = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (remind != null && !remind.isEmpty())
												{
													Object[] obj = (Object[]) remind.get(0);
													one.put("comp.reminder", DateUtil.convertDateToIndianFormat(obj[0].toString()) + ", " + obj[1].toString());
												}
												else
												{
													one.put("comp.reminder", "NA");
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("comp.compid_prefix"))
											{
												one.put(gpv.getColomnName(), obdata[k].toString() + "" + obdata[k + 1].toString());
											}
											else if (gpv.getColomnName().equalsIgnoreCase("comp.userName"))
											{
												one.put(gpv.getColomnName(), DateUtil.makeTitle(obdata[k].toString()));
											}
											else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
											{
												String query11 = "SELECT emp.empName,actionTakenDate,actionTakenTime FROM compliance_report AS cr INNER JOIN useraccount AS uc ON uc.userID=cr.userName  INNER JOIN employee_basic AS emp ON emp.useraccountid=uc.id  WHERE complID="
														+ complId + " ORDER BY cr.id DESC LIMIT 1";
												List actionBy = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (actionBy != null && !actionBy.isEmpty())
												{
													Object[] obj = (Object[]) actionBy.get(0);
													one.put(gpv.getColomnName(), obj[0].toString() + " On " + DateUtil.convertDateToIndianFormat(obj[1].toString()) + " At " + obj[2].toString());
												}
												else
												{
													one.put(gpv.getColomnName(), "NA");
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("comp.reminder_for"))
											{
												StringBuilder empName = new StringBuilder();
												String empId = obdata[k].toString().replace("#", ",").substring(0, (obdata[k].toString().length() - 1));
												String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
												List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
												for (Iterator iterator = data1.iterator(); iterator.hasNext();)
												{
													object = (Object) iterator.next();
													empName.append(object.toString() + ", ");
												}
												one.put(gpv.getColomnName(), empName.toString().substring(0, empName.toString().length() - 2));

											}
											else if (gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
											{
												one.put(gpv.getColomnName(), new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
											}
											else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										}
									}
									else
									{
										if (gpv.getColomnName().equalsIgnoreCase("comp.escalation"))
										{
											one.put(gpv.getColomnName().toString(), "No");
										}
										else
										{
											one.put(gpv.getColomnName().toString(), "NA");
										}
									}
									k++;
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							return SUCCESS;
						}
					}
				}
				retunString = SUCCESS;
				session.remove("masterViewProp");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			retunString = LOGIN;
		}
		return retunString;
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public String viewCheckList()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				complDetails = new LinkedHashMap<String, String>();
				checkListDetails = new LinkedHashMap<String, String>();
				String taskName = null,remindDays=null,customFrqOn = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CT = new ComplianceUniversalAction();
				StringBuilder qry = new StringBuilder();
				qry.append(" SELECT dept.deptName,ctyp.tasktype,comp.uploadDoc,comp.uploadDoc1,comp.userName AS alloetdBy ");
				qry.append(",comp.date,comp.reminder_for,comp.budgetary,comp.frequency,comp.dueDate,comp.actionStatus ");
				qry.append(",comp.current_esc_level,comp.taskName,comp.dueTime,comp.remind_days  ");
				qry.append(" FROM compliance_details AS comp ");
				qry.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				if (dataFrom != null && dataFrom.equalsIgnoreCase("History"))
				{
					qry.append("INNER JOIN compliance_report AS cr ON comp.id=cr.complID ");
				}
				qry.append("INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType ");
				qry.append("INNER JOIN department AS dept ON dept.id=ts.departName WHERE ");
				if (dataFrom != null && dataFrom.equalsIgnoreCase("Task"))
				{
					qry.append("  comp.id= " + complID + "");
				}
				else
				{
					qry.append("  cr.id= " + complID + "");
				}
				List data = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						complDetails.put("Department", CT.getValueWithNullCheck(object[0]));
						complDetails.put("Task Type", CT.getValueWithNullCheck(object[1]));

						if (object[2] != null)
						{
							String str = object[2].toString().substring(object[2].toString().lastIndexOf("//") + 2, object[2].toString().length());
							String docName = str.substring(14, str.length());
							complDetails.put("Document 1", docName);
						}
						else
						{
							complDetails.put("Document 1", CT.getValueWithNullCheck(object[2]));
						}
						if (object[3] != null)
						{
							String str = object[3].toString().substring(object[3].toString().lastIndexOf("//") + 2, object[3].toString().length());
							String docName = str.substring(14, str.length());
							complDetails.put("Document 2", docName);
						}
						else
						{
							complDetails.put("Document 2", CT.getValueWithNullCheck(object[3]));
						}
						complDetails.put("Alloted By", DateUtil.makeTitle(CT.getValueWithNullCheck(object[4])));
						complDetails.put("Alloted On", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[5])));

						if (object[6] != null)
						{
							StringBuilder empName = new StringBuilder();
							String empId = object[6].toString().replace("#", ",").substring(0, (object[6].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							if (data1 != null && data1.size() > 0)
							{
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									Object object1 = (Object) iterator1.next();
									empName.append(object1.toString() + ", ");
								}
							}
							if (empName != null && !empName.toString().equalsIgnoreCase(""))
							{
								complDetails.put("Alloted To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else
							{
								complDetails.put("Alloted To", "NA");
							}
						}
						else
						{
							complDetails.put("Alloted To", "NA");
						}
						
						remindDays = CT.getValueWithNullCheck(object[14]);
						if(!CT.getValueWithNullCheck(object[14]).equalsIgnoreCase("NA"))
						{
							
							customFrqOn = remindDays.split("#")[0];
							remindDays = remindDays.split("#")[1];
						}
						
						complDetails.put("Budgeted", CT.getValueWithNullCheck(object[7]));
						complDetails.put("Frequency", new ComplianceReminderHelper().getFrequencyName(CT.getValueWithNullCheck(object[8])));
						complDetails.put("Next Due Date",DateUtil.convertDateToIndianFormat(new ConfigureComplianceAction().getUpdateDueDate(CT.getValueWithNullCheck(object[8]), CT.getValueWithNullCheck(object[9]), remindDays,customFrqOn)) + ", " + CT.getValueWithNullCheck(object[13]));
						complDetails.put("Current Status", CT.getValueWithNullCheck(object[10]));
						if (object[11]!=null)
						{
							complDetails.put("Current Level", "Level " + object[11].toString());
						}
						else
						{
							complDetails.put("Current Level", "NA");
						}
						
						taskName = CT.getValueWithNullCheck(object[12]);
					}
				}
				qry.setLength(0);
				if (dataFrom!=null && dataFrom.equalsIgnoreCase("Task"))
				{
					qry.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId=" + taskName + " ORDER BY completion_tip");
					List tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						int i = 1;
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							checkListDetails.put(String.valueOf(i), object[1].toString());
							i++;
						}
					}
					if (checkListDetails.size()==0)
					{
						checkListDetails.put(" ","There is no check list");
					}
				}
				else if(dataFrom!=null && dataFrom.equalsIgnoreCase("History"))
				{
					qry.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE id IN(SELECT checkid FROM compliance_report WHERE id ='" + complID + "') ORDER BY completion_tip");
					List tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						int i = 1;
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							checkListDetails.put(String.valueOf(i), object[1].toString());
							i++;
						}
					}
					if (checkListDetails.size()==0)
					{
						checkListDetails.put(" ","There is no check list");
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
	public String DocDownload() throws IOException
	{
		String result = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List docName=null;
		try{
		    if(getDocName()!=null && getDocName()!="" && getDataFor()!=null  && getDataFor()!="")
			 {
		    	if(getDataFor().equalsIgnoreCase("doc1"))
		    	{
		    		docName=cbt.executeAllSelectQuery("SELECT document_takeaction FROM compliance_report WHERE id="+getDocName()+";", connectionSpace);
		    	}
		    	else if(getDataFor().equalsIgnoreCase("doc2"))
		    	{
		    		docName=cbt.executeAllSelectQuery("SELECT document_action_1 FROM compliance_report WHERE id="+getDocName()+";", connectionSpace);
		    	}
		    	else if(getDataFor().equalsIgnoreCase("doc3"))
		    	{
		    		docName=cbt.executeAllSelectQuery("SELECT document_action_2 FROM compliance_report WHERE id="+getDocName()+";", connectionSpace);
		    	}
		    	else if(getDataFor().equalsIgnoreCase("Document 1"))
				{
					docName=cbt.executeAllSelectQuery("SELECT  uploadDoc FROM  compliance_details WHERE id="+getDocName()+";", connectionSpace);
				}
		    	else if (getDataFor().equalsIgnoreCase("Document 2"))
				{
					docName=cbt.executeAllSelectQuery("SELECT  uploadDoc1 FROM  compliance_details WHERE id="+getDocName()+";", connectionSpace);
				}
				if (docName.get(0)!=null && !docName.get(0).equals("")) 
				{
					fileName =(String) docName.get(0);
					System.out.println(" file name " +fileName);
					File file = new File(fileName);
					inputStream = new FileInputStream(file);
					fileName = file.getName();
					result = SUCCESS;
				}
				else 
				{
					System.out.println("in Error");
					result = ERROR;
				} 
			 }
		}
	     catch(Exception e)
	     {
	    	 inputStream.close();
	    	 e.printStackTrace();
	}
		return result;
}
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewFullViewFormatter()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CT = new ComplianceUniversalAction();
				ComplianceReminderHelper RH = new ComplianceReminderHelper();
				complDetails = new LinkedHashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				qry.append(" SELECT dept.deptName,comp.userName AS alloetdBy ");
				qry.append(",comp.reminder_for,comp.frequency,min(cr.dueDate) ");
				qry.append(",min(cr.dueTime),comp.action_type FROM compliance_details AS comp ");
				qry.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				qry.append("INNER JOIN compliance_report AS cr ON comp.id=cr.complID ");
				qry.append("INNER JOIN department AS dept ON dept.id=ts.departName WHERE comp.id= " + complID + " GROUP By dept.deptName");
				List data = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						complDetails.put("Department", CT.getValueWithNullCheck(object[0]));
						complDetails.put("Alloted By", DateUtil.makeTitle(CT.getValueWithNullCheck(object[1])));
						if (object[2] != null)
						{
							StringBuilder empName = new StringBuilder();
							String empId = object[2].toString().replace("#", ",").substring(0, (object[2].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							if (data1 != null && data1.size() > 0)
							{
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									Object object1 = (Object) iterator1.next();
									empName.append(object1.toString() + ", ");
								}
							}
							if (empName != null && !empName.toString().equalsIgnoreCase(""))
							{
								complDetails.put("Alloted To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else
							{
								complDetails.put("Alloted To", "NA");
							}
						}
						else
						{
							complDetails.put("Alloted To", "NA");
						}
						// complDetails.put("Frequency",new
						// ComplianceReminderHelper().getFrequencyName(
						// CT.getValueWithNullCheck(object[3])));
						complDetails.put("Started From", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[4])) + ", " + CT.getValueWithNullCheck(object[5]));
						complDetails.put("Frequency", RH.getFrequencyName(object[3].toString()));
						complDetails.put("Ownership", DateUtil.makeTitle(CT.getValueWithNullCheck(object[6])));
					}

				}
				List<GridDataPropertyView> sessionvalues = new ArrayList<GridDataPropertyView>();
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.complainceId");
				gpv.setHeadingName("Task ID");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.dueDate");
				gpv.setHeadingName("Due On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.dueTime");
				gpv.setHeadingName("Due Time");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(110);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenDate");
				gpv.setHeadingName("Action Taken On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(110);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenTime");
				gpv.setHeadingName("Action Taken On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(80);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.userName");
				gpv.setHeadingName("Action Taken By");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(90);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTaken");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("viewStatus");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.checkList");
				gpv.setHeadingName("Check List");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(30);
				gpv.setFormatter("checkList");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_date");
				gpv.setHeadingName("TAT");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("viewTAT");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_time");
				gpv.setHeadingName("TATTime");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.budgetary");
				gpv.setHeadingName("Budgeted");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.difference");
				gpv.setHeadingName("Cost");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.reminder");
				gpv.setHeadingName("Reminder");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("reminder");
				viewPageColumns.add(gpv);

				session.put("masterViewProp", sessionvalues);

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
	{ "rawtypes", "unchecked" })
	public String fullViewActivity()
	{
		String retunString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_report");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName() != null)
								{
									if (gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("cr.id,");
									}
									else
									{
										query.append(gpv.getColomnName().toString() + ",");
									}
								}
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM compliance_report AS cr " + "INNER JOIN compliance_details AS comp ON comp.id=cr.complID  " + "WHERE cr.id IN(SELECT max(id) from compliance_report WHERE  complID=" + complID + " GROUP BY complainceId) ");

						query.append(" ORDER BY cr.actionTakenDate ASC");
						// System.out.println("query1.toString():::::::::::::;"+query.toString());
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

						if (data != null)
						{
							Object[] obdata = null;
							String dueDate = null;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								int k = 0;
								obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
									{
										if (gpv.getColomnName() != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												if (gpv.getColomnName().equalsIgnoreCase("cr.dueDate"))
												{
													dueDate = obdata[k].toString();
												}
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + obdata[k + 1].toString());
											}
											else if (gpv.getColomnName().equalsIgnoreCase("id"))
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
												String query11 = "SELECT remind_date,remind_time FROM compliance_reminder WHERE compliance_id='" + complID + "' AND remind_date BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + dueDate
														+ "' AND status!='Done' AND reminder_code IN('D','R') ORDER BY remind_date ASC LIMIT 1";
												List remind = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (remind != null && !remind.isEmpty())
												{
													Object[] obj = (Object[]) remind.get(0);

													one.put("cr.reminder", DateUtil.convertDateToIndianFormat(obj[0].toString()) + ", " + obj[1].toString());
												}
												else
												{
													one.put("cr.reminder", "NA");
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
											{
												one.put(gpv.getColomnName(), DateUtil.makeTitle(Cryptography.decrypt(obdata[k].toString(), DES_ENCRYPTION_KEY)));
											}
											else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										}
									}
									else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							return SUCCESS;
						}
					}
				}
				retunString = SUCCESS;
				session.remove("masterViewProp");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			retunString = LOGIN;
		}
		return retunString;

	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getSearchPeriodOn()
	{
		return searchPeriodOn;
	}

	public void setSearchPeriodOn(String searchPeriodOn)
	{
		this.searchPeriodOn = searchPeriodOn;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public Map<String, String> getHeadermap()
	{
		return headermap;
	}

	public void setHeadermap(Map<String, String> headermap)
	{
		this.headermap = headermap;
	}

	public String getComplID()
	{
		return complID;
	}

	public void setComplID(String complID)
	{
		this.complID = complID;
	}

	public Map<String, String> getComplDetails()
	{
		return complDetails;
	}

	public void setComplDetails(Map<String, String> complDetails)
	{
		this.complDetails = complDetails;
	}

	public Map<String, String> getCheckListDetails()
	{
		return checkListDetails;
	}

	public void setCheckListDetails(Map<String, String> checkListDetails)
	{
		this.checkListDetails = checkListDetails;
	}


	public List<ComplianceDashboardBean> getDoneDetails()
	{
		return doneDetails;
	}

	public void setDoneDetails(List<ComplianceDashboardBean> doneDetails)
	{
		this.doneDetails = doneDetails;
	}

	public Map<String, String> getStatusDetails()
	{
		return statusDetails;
	}

	public void setStatusDetails(Map<String, String> statusDetails)
	{
		this.statusDetails = statusDetails;
	}

	public Map<String, String> getEscalationDetails()
	{
		return escalationDetails;
	}

	public void setEscalationDetails(Map<String, String> escalationDetails)
	{
		this.escalationDetails = escalationDetails;
	}

	public List<ComplianceDashboardBean> getAllotDetails()
	{
		return allotDetails;
	}

	public void setAllotDetails(List<ComplianceDashboardBean> allotDetails)
	{
		this.allotDetails = allotDetails;
	}

	public List<ComplianceDashboardBean> getAllotByDetails()
	{
		return allotByDetails;
	}

	public void setAllotByDetails(List<ComplianceDashboardBean> allotByDetails)
	{
		this.allotByDetails = allotByDetails;
	}

	public Map<String, String> getReminderDetails()
	{
		return reminderDetails;
	}

	public void setReminderDetails(Map<String, String> reminderDetails)
	{
		this.reminderDetails = reminderDetails;
	}

	public List<ComplianceDashboardBean> getBudgetDetails()
	{
		return budgetDetails;
	}

	public void setBudgetDetails(List<ComplianceDashboardBean> budgetDetails)
	{
		this.budgetDetails = budgetDetails;
	}

	public String getDataFrom()
	{
		return dataFrom;
	}

	public void setDataFrom(String dataFrom)
	{
		this.dataFrom = dataFrom;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}


	public Map<String, String> getDeptName()
	{
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName)
	{
		this.deptName = deptName;
	}

	public String getBudget()
	{
		return budget;
	}

	public void setBudget(String budget)
	{
		this.budget = budget;
	}

	public String getTimmings()
	{
		return timmings;
	}

	public void setTimmings(String timmings)
	{
		this.timmings = timmings;
	}

	public String getShareStatus()
	{
		return shareStatus;
	}

	public void setShareStatus(String shareStatus)
	{
		this.shareStatus = shareStatus;
	}

	public String getSearching()
	{
		return searching;
	}

	public void setSearching(String searching)
	{
		this.searching = searching;
	}

	public Map<String, String> getTaskTypeMap()
	{
		return taskTypeMap;
	}

	public void setTaskTypeMap(Map<String, String> taskTypeMap)
	{
		this.taskTypeMap = taskTypeMap;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getDoneDoc()
	{
		return doneDoc;
	}

	public void setDoneDoc(String doneDoc)
	{
		this.doneDoc = doneDoc;
	}

	

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public Map<String, String> getWorkingmap()
	{
		return workingmap;
	}

	public void setWorkingmap(Map<String, String> workingmap)
	{
		this.workingmap = workingmap;
	}

}