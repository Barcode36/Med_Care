package com.Over2Cloud.compliance.serviceFiles;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.cglib.core.EmitUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

@SuppressWarnings("unused")
public class ComplianceReminderHelper
{
	CommunicationHelper CH = new CommunicationHelper();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	@SuppressWarnings("unchecked")
	public void checkReminder(SessionFactory connection)
	{
		List esclevelData = null;
		try
		{
			ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
			StringBuilder mailtext = new StringBuilder("");

			List validData = srvcHelper.getReminderData(connection);
			if (validData != null && validData.size() > 0)
			{
				String taskName = null, taskType = null, escalation = null, frequency = null, taskBrief = null, msgText = null, remidMode = null, reminderName = null, remindFor = null, compReminderId = null, compId = null, dueDate = null, dueTime = null, attachmentPath = null, remindDate = null;
				String applyWorkingDay = null;
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = validData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					taskName = getValueWithNullCheck(object[0]);
					frequency = getValueWithNullCheck(object[1]);
					taskBrief = getValueWithNullCheck(object[2]);
					msgText = getValueWithNullCheck(object[3]);
					remidMode = getValueWithNullCheck(object[4]);
					remindFor = getValueWithNullCheck(object[5]);
					compReminderId = getValueWithNullCheck(object[6]);
					compId = getValueWithNullCheck(object[7]);
					dueDate = getValueWithNullCheck(object[8]);
					dueTime = getValueWithNullCheck(object[9]);
					attachmentPath = getValueWithNullCheck(object[10]);
					remindDate = getValueWithNullCheck(object[12]);
					taskType = getValueWithNullCheck(object[13]);
					reminderName = getValueWithNullCheck(object[14]);
					escalation = getValueWithNullCheck(object[15]);
					applyWorkingDay = getValueWithNullCheck(object[16]);
					// update schedule columns
					boolean status = srvcHelper.updateCompliaceAccordingToFrequency(compReminderId, frequency, remindDate, connection, applyWorkingDay);

					// code for sending sms and mails
					if (status)
					{
						List complDetailData = new ArrayList();
						List contactList = new ArrayList();

						if (remindFor != null && !remindFor.equals(""))
						{
							String mailSubject = null, mailTitle = null;
							if (reminderName != null && reminderName.equalsIgnoreCase("Due Reminder"))
							{
								mailSubject = "Urgent Attention for Operation Task Due Today!";
								mailTitle = mailSubject;
								dueDate = "Today i.e " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
								msgText = "Operation Task Alert: Task Name: " + taskName + ", is having Due Date Today.";

							}
							else if (reminderName != null && !reminderName.equalsIgnoreCase("Due Reminder"))
							{
								mailSubject = "Operation Task Reminder Alert-" + reminderName.charAt(reminderName.length() - 1);
								mailTitle = mailSubject + " requires your kind attention!";
								dueDate = DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
								msgText = "Operation Task Alert: Reminder-" + reminderName.charAt(reminderName.length() - 1) + ", Task Name: " + taskName + ", Due Date: " + dueDate + ".";
							}

							if (escalation != null && escalation.equalsIgnoreCase("N"))
								escalation = "No";
							else if (escalation != null && escalation.equalsIgnoreCase("Y"))
								escalation = "Yes";

							String contactId = remindFor.replace("#", ",").substring(0, (remindFor.toString().length() - 1));
							String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
							List data1 = new createTable().executeAllSelectQuery(query2, connection);
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								frequency = getFrequencyName(frequency);

								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[4].toString().equalsIgnoreCase(object3[4].toString()))
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

								if (remidMode.equalsIgnoreCase("SMS"))
								{
									if (object2[0] != null && object2[0].toString() != "")
										cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Compliance", connection);

								}
								else if (remidMode.equalsIgnoreCase("Mail"))
								{
									String mailText = getConfigMail(taskName, taskType, taskBrief, frequency, escalation, compId, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle, "Pending");
									if (object2[1] != null && object2[1].toString() != "")
										cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);

								}
								else if (remidMode.equalsIgnoreCase("Both"))
								{
									if (object2[0] != null && object2[0].toString() != "")
										cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Compliance", connection);

									String mailText = getConfigMail(taskName, taskType, taskBrief, frequency, escalation, compId, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle, "Pending");
									if (object2[1] != null && object2[1].toString() != "")
										cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);
								}
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{
			"unchecked", "static-access"
	})
	public void checkEscalation(SessionFactory connection)
	{
		try
		{
			ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
			StringBuilder mailtext = new StringBuilder("");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List validData = srvcHelper.getEscalationData(connection);
			if (validData != null && validData.size() > 0)
			{
				String taskName = null, taskType = null, frequency = null, taskBrief = null, msgText = null, remidMode = null, remindFor = null, compId = null, dueDate = null, dueTime = null, attachmentPath = null;
				String escL1 = null, escL2 = null, escL3 = null, escL4 = null, escL1Duration = null, escL2Duration = null, escL3Duration = null, escL4Duration = null, currentEscLevel = null;
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = validData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					taskName = getValueWithNullCheck(object[0]);
					frequency = getValueWithNullCheck(object[1]);
					taskBrief = getValueWithNullCheck(object[2]);
					msgText = getValueWithNullCheck(object[3]);
					remidMode = getValueWithNullCheck(object[4]);
					remindFor = getValueWithNullCheck(object[5]);
					dueDate = getValueWithNullCheck(object[6]);
					dueTime = getValueWithNullCheck(object[7]);
					escL1 = getValueWithNullCheck(object[8]);
					escL1Duration = getValueWithNullCheck(object[9]);
					escL2 = getValueWithNullCheck(object[10]);
					escL2Duration = getValueWithNullCheck(object[11]);
					escL3 = getValueWithNullCheck(object[12]);
					escL3Duration = getValueWithNullCheck(object[13]);
					escL4 = getValueWithNullCheck(object[14]);
					escL4Duration = getValueWithNullCheck(object[15]);
					currentEscLevel = getValueWithNullCheck(object[16]);
					compId = getValueWithNullCheck(object[17]);
					taskType = getValueWithNullCheck(object[18]);

					int nextEscLevel = Integer.valueOf(currentEscLevel);
					String nextEscDuration = "00:00";
					String nextEscDateTime = null;
					boolean isNextEsc = false;
					if (nextEscLevel == 1 && !escL1Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL2Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 2 && !escL2Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL3Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 3 && !escL3Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL4Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 4)
					{
						nextEscDuration = "00:00";
						isNextEsc = true;
					}
					nextEscLevel++;

					if (nextEscLevel < 6)
					{
						WorkingHourAction WHA = new WorkingHourAction();
						String date = DateUtil.getCurrentDateUSFormat();
						String time = DateUtil.getCurrentTimeHourMin();
						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, nextEscDuration, "00:00", "Y", date, time, "COMPL");
					}
					// update schedule columns
					boolean status = false;
					if (isNextEsc)
					{
						status = srvcHelper.updateNextEscDate(compId, connection, nextEscLevel, nextEscDateTime);
					}

					// code for sending sms and mails
					List complDetailData = new ArrayList();
					List contactList = new ArrayList();
					String escL = null;
					if (nextEscLevel == 2 && isNextEsc)
						escL = escL1;
					else if (nextEscLevel == 3 && isNextEsc)
						escL = escL2;
					else if (nextEscLevel == 4 && isNextEsc)
						escL = escL3;
					else if (nextEscLevel == 5 && isNextEsc)
						escL = escL4;

					if (escL != null && !escL.equals(""))
					{
						frequency = getFrequencyName(frequency);

						String mailSubject = null, mailTitle = null;
						List data1 = null;
						if (escL != null && !escL.equalsIgnoreCase(" "))
						{
							String contactId = escL.replace("#", ",").substring(0, (escL.toString().length() - 1));
							String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
							data1 = new createTable().executeAllSelectQuery(query2, connection);
						}
						if (data1 != null && data1.size() > 0)
						{
							mailSubject = "Operation Task Escalation Alert: L" + currentEscLevel + " to L" + nextEscLevel + "!";
							mailTitle = mailSubject;
							msgText = "Operation Task Escalation Alert: L" + currentEscLevel + " to L" + nextEscLevel + " for Task Name: " + taskName + ", had Due Date " + DateUtil.convertDateToIndianFormat(dueDate) + ".";
							dueDate = "Passed & was on " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[4].toString().equalsIgnoreCase(object3[4].toString()))
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

								if (object2[0] != null && object2[0].toString() != "")
									cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Compliance", connection);

								String mailText = getConfigMail(taskName, taskType, taskBrief, frequency, "Yes", compId, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle, "Pending");
								if (object2[1] != null && object2[1].toString() != "")
									cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);

							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{
			"unchecked", "static-access"
	})
	public void checkRecurring(SessionFactory connection)
	{
		ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder mailtext = new StringBuilder("");
		List validData = srvcHelper.getRecurringData(connection);
		if (validData != null && validData.size() > 0)
		{
			for (Iterator iterator = validData.iterator(); iterator.hasNext();)
			{
				String nextEscLevel = null, nextEscDate = null, nextEscTime = null, escalation = null, frequency = null;
				;
				String remindDate = null, dueDate = null, dueTime = null, docPath1 = null, docPath2 = null, userName = null, compId = null, deptId = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List remindDateList = new ArrayList();
				InsertDataTable insertObj = null;
				Object object[] = (Object[]) iterator.next();
				List reminderDate = srvcHelper.getReminderDate(object[0].toString(), connection);

				// Code For Update Compliance Reminder Table
				if (reminderDate != null && reminderDate.size() > 0)
				{
					for (Iterator iterator2 = reminderDate.iterator(); iterator2.hasNext();)
					{
						Object object1[] = (Object[]) iterator2.next();
						if (object[1] != null && object[1].toString().equalsIgnoreCase("W"))
						{
							dueDate = DateUtil.getNewDate("day", 7, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("M"))
						{
							dueDate = DateUtil.getNewDate("month", 1, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("BM"))
						{
							dueDate = DateUtil.getNewDate("day", 15, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}

						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("Q"))
						{
							dueDate = DateUtil.getNewDate("month", 3, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("HY"))
						{
							dueDate = DateUtil.getNewDate("month", 6, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("Y"))
						{
							dueDate = DateUtil.getNewDate("year", 1, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("D"))
						{
							if (object[17] != null && object[17].toString().equalsIgnoreCase("Yes"))
							{
								dueDate = new ComplianceReminderHelper().getNewDateAfterLeaveCalculate(connection);
							}
							else
							{
								dueDate = DateUtil.getNewDate("day", 1, object[3].toString());
							}
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
							}
						}

						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						String nextUpdateDate = "";
						whereCondition.put("id", object1[3].toString());
						setVariables.put("due_date", dueDate);
						setVariables.put("remind_date", remindDate);
						setVariables.put("reminder_status", "0");
						setVariables.put("status", "Pending");
						cbt.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
					}
				}

				if (object[0] != null)
					compId = object[0].toString();

				if (object[1] != null)
					frequency = object[1].toString();

				if (object[2] != null)
					escalation = object[2].toString();

				if (object[4] != null)
					dueTime = object[4].toString();

				if (object[13] != null)
					docPath1 = object[13].toString();

				if (object[14] != null)
					docPath2 = object[14].toString();

				if (object[15] != null)
					userName = object[15].toString();

				if (object[16] != null)
					deptId = object[16].toString();

				List complSeries = new ComplianceServiceHelper().getCompIdPrefix_Suffix(deptId, frequency, connection);

				String prefix = null;
				String suffix = null;
				if (complSeries != null && complSeries.size() > 0)
				{
					if (complSeries.get(0) != null)
						prefix = complSeries.get(0).toString();

					if (complSeries.get(1) != null)
						suffix = complSeries.get(1).toString();
				}

				// Code For Update Compliance Detail Table
				if (object[2] != null && object[2].toString().equalsIgnoreCase("Y"))
				{
					if (object[5] != null && !object[6].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "1";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[6].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[7] != null && !object[8].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "2";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[8].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[9] != null && !object[10].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "3";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[10].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[11] != null && !object[12].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "4";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[12].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}

					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					String nextUpdateDate = "";
					whereCondition.put("id", object[0].toString());
					setVariables.put("dueDate", dueDate);
					setVariables.put("actionStatus", "Pending");
					setVariables.put("actionTaken", "Pending");
					if (prefix != null && suffix != null)
					{
						setVariables.put("compid_prefix", prefix);
						setVariables.put("compid_suffix", suffix);
					}
					setVariables.put("escalate_date", nextEscDate);
					setVariables.put("escalate_time", nextEscTime);
					setVariables.put("current_esc_level", nextEscLevel);
					cbt.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
				}
				else
				{
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					String nextUpdateDate = "";
					whereCondition.put("id", object[0].toString());
					setVariables.put("dueDate", dueDate);
					setVariables.put("actionStatus", "Pending");
					setVariables.put("actionTaken", "Pending");
					if (prefix != null && suffix != null)
					{
						setVariables.put("compid_prefix", prefix);
						setVariables.put("compid_suffix", suffix);
					}
					cbt.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
				}
				try
				{
					// data insert into compliance report table.
					insertData = new ArrayList<InsertDataTable>();

					if (prefix != null && suffix != null)
					{
						insertData = setParameterInObj("complainceId", prefix + suffix, insertData);
					}

					if (remindDateList != null && remindDateList.size() > 0)
					{
						for (int i = 0; i < remindDateList.size(); i++)
						{
							insertData = setParameterInObj("reminder" + (i + 1), remindDateList.get(i).toString(), insertData);
						}
					}

					insertData = setParameterInObj("dueDate", dueDate, insertData);
					insertData = setParameterInObj("dueTime", dueTime, insertData);
					insertData = setParameterInObj("userName", new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY), insertData);
					insertData = setParameterInObj("actionTaken", "Pending", insertData);
					insertData = setParameterInObj("complID", compId, insertData);
					insertData = setParameterInObj("document_config_1", docPath1, insertData);
					insertData = setParameterInObj("document_config_2", docPath2, insertData);
					insertData = setParameterInObj("actionTakenDate", DateUtil.getCurrentDateUSFormat(), insertData);
					insertData = setParameterInObj("actionTakenTime", DateUtil.getCurrentTimeHourMin(), insertData);

					if (escalation.equalsIgnoreCase("Y"))
					{
						insertData = setParameterInObj("current_esc_level", "1", insertData);
					}

					cbt.insertIntoTable("compliance_report", insertData, connection);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void checkSnooze(SessionFactory connection)
	{

		ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
		CommunicationHelper cmnHelper = new CommunicationHelper();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder mailtext = new StringBuilder("");
		List validData = srvcHelper.getSnoozeData(connection);
		if (validData != null && validData.size() > 0)
		{
			String frequency = null, compId = null, escalation = null, dueDate = null, dueTime = null, snoozeDate = null, snoozeTime = null, currentEscLevel = null, reminderFor = null;
			String msg = null, taskName = null, taskType = null, taskBrief = null, comment = null;
			for (Iterator iterator = validData.iterator(); iterator.hasNext();)
			{
				String nextEscLevel = null, nextEscDate = null, nextEscTime = null;
				String remindDate = null;
				Object object[] = (Object[]) iterator.next();

				compId = object[0].toString();

				if (object[1] != null)
					frequency = object[1].toString();

				if (object[3] != null)
					dueDate = object[3].toString();

				if (object[4] != null)
					dueTime = object[4].toString();

				if (object[13] != null)
					snoozeDate = object[13].toString();

				if (object[14] != null)
					snoozeTime = object[14].toString();

				if (object[16] != null)
					reminderFor = object[16].toString();

				if (object[18] != null)
					taskName = object[18].toString();

				if (object[19] != null)
					taskType = object[19].toString();

				if (object[20] != null)
					taskBrief = object[20].toString();

				if (object[21] != null)
					comment = object[21].toString();
				else
					comment = "NA";

				if (object[2].toString().equalsIgnoreCase("N"))
					escalation = "No";
				else
					escalation = "Yes";

				List reminderData = srvcHelper.getReminderDate(object[0].toString(), connection);
				if (reminderData != null && reminderData.size() > 0)
				{
					for (Iterator iterator2 = reminderData.iterator(); iterator2.hasNext();)
					{
						Object object1[] = (Object[]) iterator2.next();
						String newDueDate = null;
						String reminderCode = null, remindInterval = null;

						reminderCode = object1[0].toString();

						if (object1[2] != null)
							remindInterval = object1[2].toString();

						if (frequency != null && frequency.equalsIgnoreCase("OT"))
						{
							newDueDate = object[13].toString();
							dueTime = object[14].toString();
						}
						else if (frequency != null && frequency.equalsIgnoreCase("W"))
						{
							newDueDate = DateUtil.getNewDate("day", 7, dueDate);

							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -(Integer.valueOf(remindInterval)), newDueDate);

						}
						else if (frequency != null && frequency.equalsIgnoreCase("M"))
						{
							newDueDate = DateUtil.getNewDate("month", 1, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("BM"))
						{
							newDueDate = DateUtil.getNewDate("day", 15, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("Q"))
						{
							newDueDate = DateUtil.getNewDate("month", 3, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("HY"))
						{
							newDueDate = DateUtil.getNewDate("month", 6, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("Y"))
						{
							newDueDate = DateUtil.getNewDate("year", 1, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						Map<String, Object> whereParentCondition = new HashMap<String, Object>();
						String nextUpdateDate = "";
						whereCondition.put("id", object1[3].toString());

						if (frequency != null && frequency.equalsIgnoreCase("OT"))
						{
							setVariables.put("remind_date", newDueDate);
							setVariables.put("remind_time", dueTime);
							setVariables.put("reminder_status", "311");
						}
						else
						{
							if (reminderCode.equalsIgnoreCase("S"))
							{
								setVariables.put("reminder_status", "311");
								setVariables.put("status", "Snooze");

							}
							else
							{
								setVariables.put("due_date", newDueDate);
								setVariables.put("remind_date", remindDate);
								setVariables.put("reminder_status", "0");
								setVariables.put("status", "Pending");
							}
						}
						cbt.updateTableColomn("compliance_reminder", setVariables, whereCondition, connection);
					}
				}

				// Code For Update Compliance Detail Table
				if (object[2] != null && object[2].toString().equalsIgnoreCase("Y"))
				{

					if (object[5] != null && !object[6].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("1"))
					{
						// nextEscLevel="1";
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						String date = object[13].toString();
						String time = object[14].toString();
						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, object[6].toString(), "00:00", "Y", date, time, "COMPL");
						nextEscDate = dateTime.get(0);
						nextEscTime = dateTime.get(1);
					}
					else if (object[7] != null && !object[8].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("2"))
					{
						// nextEscLevel="2";
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						String date = object[13].toString();
						String time = object[14].toString();
						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, object[8].toString(), "00:00", "Y", date, time, "COMPL");
						nextEscDate = dateTime.get(0);
						nextEscTime = dateTime.get(1);
					}
					else if (object[9] != null && !object[10].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("3"))
					{
						// nextEscLevel="3";
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						String date = object[13].toString();
						String time = object[14].toString();
						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, object[10].toString(), "00:00", "Y", date, time, "COMPL");
						nextEscDate = dateTime.get(0);
						nextEscTime = dateTime.get(1);
					}
					else if (object[11] != null && !object[12].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("4"))
					{
						// nextEscLevel="4";
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						String date = object[13].toString();
						String time = object[14].toString();
						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, object[12].toString(), "00:00", "Y", date, time, "COMPL");
						nextEscDate = dateTime.get(0);
						nextEscTime = dateTime.get(1);
					}

				}
				else
				{
					nextEscDate = "NA";
					nextEscTime = "NA";
				}

				frequency = getFrequencyName(frequency);

				Map<String, Object> setVariables = new HashMap<String, Object>();
				Map<String, Object> whereCondition = new HashMap<String, Object>();
				whereCondition.put("id", compId);
				setVariables.put("dueDate", dueDate);
				setVariables.put("actionStatus", "Pending");
				setVariables.put("actionTaken", "Pending");
				if (nextEscDate != null && !nextEscDate.equalsIgnoreCase(""))
					setVariables.put("escalate_date", nextEscDate);

				if (nextEscTime != null && !nextEscTime.equalsIgnoreCase(""))
					setVariables.put("escalate_time", nextEscTime);

				boolean flag = cbt.updateTableColomn("compliance_details", setVariables, whereCondition, connection);
				if (flag)
				{
					if (reminderFor != null)
					{
						String mailSubject = null, mailTitle = null;
						mailSubject = "Operation Task Snooze To Active Alert:";
						mailTitle = mailSubject;
						String msgText = "Operation Task Snooze To Active Alert: For Task Name: " + taskName + ",  has been snoozed till " + DateUtil.convertDateToIndianFormat(snoozeDate) + " " + snoozeTime + ", Comments: " + comment + ".";
						dueDate = "Passed & was on " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;

						String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));
						// String query2=
						// "SELECT mobOne,emailIdOne,empName,id FROM employee_basic WHERE id IN("
						// +empId+")";
						/*
						 * String query2 =
						 * "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName,cc.id "
						 * +
						 * "AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON "
						 * +
						 * "emp.id=cc.emp_id INNER JOIN subdepartment AS subdept ON subdept.id=emp.subdept "
						 * +
						 * "INNER JOIN department AS dept ON dept.id=subdept.deptid WHERE cc.work_status=0 "
						 * + "AND cc.id IN(" + contactId + ")"; List data1 = new
						 * createTable().executeAllSelectQuery(query2,
						 * connection);
						 */
						String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
						List data1 = new createTable().executeAllSelectQuery(query2, connection);
						if (data1 != null && data1.size() > 0)
						{
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[4].toString().equalsIgnoreCase(object3[4].toString()))
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

								if (object2[0] != null && object2[0].toString() != "")
									cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Compliance", connection);

								String mailText = getConfigMail(taskName, taskType, taskBrief, frequency, "Yes", compId, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle, "Pending");
								if (object2[1] != null && object2[1].toString() != "")
									cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);

							}
						}

					}
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void checkTodayPending(SessionFactory connection)
	{
		try
		{
			List data = null;
			WorkingHourHelper WHH = new WorkingHourHelper();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> workingDayList = WHH.getDayDate(DateUtil.getCurrentDateUSFormat(), connection, cbt, "COMPL");
			String workingDay = workingDayList.get(1).toString();
			ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			if (DateUtil.getCurrentDateUSFormat().equals(workingDay))
			{
				data = srvcHelper.getTodayReminderFor(connection, DateUtil.getCurrentDateUSFormat(), "equalCondition");
			}
			else
			{
				data = srvcHelper.getTodayReminderFor(connection, workingDayList.get(1).toString(), "betweenCondition");
			}
			String mailSendDate = null, mailSendTime = null, id = null;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			List dataList = cbt.executeAllSelectQuery("SELECT date,time,id FROM compliance_todayTask_config WHERE moduleName='COMPL' AND date='" + DateUtil.getCurrentDateUSFormat() + "'", connection);
			if (dataList == null || dataList.size() == 0)
			{
				InsertDataTable ob = null;
				boolean status = false;
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("moduleName");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("compliance_todayTask_config", Tablecolumesaaa, connection);

				/*
				 * ob = new InsertDataTable(); ob.setColName("date");
				 * ob.setDataName(DateUtil.getCurrentDateUSFormat());
				 * insertData.add(ob);
				 * 
				 * ob = new InsertDataTable(); ob.setColName("time");
				 * ob.setDataName("08:00"); insertData.add(ob);
				 * 
				 * ob = new InsertDataTable(); ob.setColName("moduleName");
				 * ob.setDataName("COMPL"); insertData.add(ob);
				 * 
				 * cbt.insertIntoTable("compliance_todayTask_config",
				 * insertData, connection);
				 */

				// Map<String, Object> setVariables = new HashMap<String,
				// Object>();
				// Map<String, Object> whereCondition = new HashMap<String,
				// Object>();
				// setVariables.put("date", DateUtil.getCurrentDateUSFormat());
				// setVariables.put("time", "8:00");
				// setVariables.put("moduleName", "COMPL");
				// boolean flag =
				// cbt.updateTableColomn("compliance_todayTask_config",
				// setVariables, whereCondition, connection);
			}
			else if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						mailSendDate = object[0].toString();
						mailSendTime = object[1].toString();
						id = object[2].toString();
					}
				}
				if (mailSendDate != null && mailSendTime != null)
				{
					boolean status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), mailSendDate + " " + mailSendTime);
					if (!status)
					{
						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", id);
						setVariables.put("date", DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()));
						boolean flag = cbt.updateTableColomn("compliance_todayTask_config", setVariables, whereCondition, connection);
						if (flag == false)
						{
							mailSendDate = null;
							mailSendTime = null;
						}
					}
					else
					{
						mailSendDate = null;
						mailSendTime = null;
					}

				}
			}
			if (mailSendDate != null && mailSendTime != null)
			{
				Map<String, String> idWithData = new LinkedHashMap<String, String>();
				StringBuilder strBuilder = new StringBuilder();
				StringBuilder contactIdBuilder = new StringBuilder(",");
				String frequency = null, compId = null, escalation = null, dueDate = null, dueTime = null, actionType = null, reminderFor = null;
				String status = null, taskName = null, taskType = null, taskBrief = null, emailId = null, empName = null, comments = null;
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						if (object[1] != null && !object[1].toString().equalsIgnoreCase(""))
						{
							strBuilder.append(object[1].toString());
							idWithData.put(object[0].toString(), "#" + object[1].toString());
						}
					}
					if (strBuilder != null)
					{
						String contactId[] = strBuilder.toString().split("#");
						for (int j = 0; j < contactId.length; j++)
						{
							if (!contactIdBuilder.toString().contains("," + contactId[j] + ","))
							{
								contactIdBuilder.append(contactId[j] + ",");
							}
						}
						contactIdBuilder = contactIdBuilder.replace(0, 1, "");
						contactId = contactIdBuilder.toString().split(",");

						for (int i = 0; i < contactId.length; i++)
						{
							StringBuilder compIdList = new StringBuilder();
							Iterator<Entry<String, String>> hiterator = idWithData.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								if (paramPair.getValue().contains("#" + contactId[i] + "#"))
								{
									compIdList.append(paramPair.getKey() + ",");
								}
							}
							if (compIdList != null)
							{
								ComplianceDashboardBean obj = null;
								String mappedTeam = null;
								String comID = compIdList.toString().substring(0, (compIdList.toString().length() - 1));
								StringBuilder query = new StringBuilder();
								query.append("SELECT cd.id,ctn.taskName,cty.taskType,cd.frequency,cd.taskBrief,cd.dueDate,cd.dueTime,cd.reminder_for,cd.actionStatus,cd.action_type ");
								query.append("FROM compliance_details AS cd ");
								query.append("LEFT JOIN compliance_task AS ctn ON cd.taskName=ctn.id ");
								query.append("LEFT JOIN compl_task_type AS cty ON ctn.taskType=cty.id WHERE cd.id IN(" + comID + ")");
								List data1 = new createTable().executeAllSelectQuery(query.toString(), connection);
								if (data1 != null && data1.size() > 0)
								{
									List<ComplianceDashboardBean> listForMail = new ArrayList<ComplianceDashboardBean>();
									for (Iterator iterator = data1.iterator(); iterator.hasNext();)
									{
										obj = new ComplianceDashboardBean();
										Object[] object = (Object[]) iterator.next();
										if (object != null)
										{
											if (object[0] != null)
											{
												compId = object[0].toString();
											}
											if (object[1] != null)
											{
												taskName = object[1].toString();
											}
											else
											{
												taskName = "NA";
											}
											if (object[2] != null)
											{
												taskType = object[2].toString();
											}
											else
											{
												taskType = "NA";
											}
											if (object[3] != null)
											{
												frequency = getFrequencyName(object[3].toString());
											}
											else
											{
												frequency = "NA";
											}
											if (object[4] != null)
											{
												taskBrief = object[4].toString();
											}
											else
											{
												taskBrief = "NA";
											}
											if (object[5] != null)
											{
												if (object[5].toString().equals(DateUtil.getCurrentDateUSFormat()))
												{
													comments = "Today Due";
												}
												else
												{
													comments = "Pre-Remind due to holiday or week end";
												}
												dueDate = DateUtil.convertDateToIndianFormat(object[5].toString());
											}
											else
											{
												dueDate = "NA";
											}
											if (object[6] != null)
											{
												dueTime = object[6].toString();
											}
											else
											{
												dueTime = "NA";
											}
											if (object[7] != null)
											{
												String contId = object[7].toString().replace("#", ",").substring(0, (object[7].toString().length() - 1));
												StringBuilder query2 = new StringBuilder();
												query2.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId ");
												query2.append("FROM compliance_contacts AS cc ");
												query2.append("INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
												query2.append("WHERE cc.work_status=0 AND cc.id IN(" + contId + ")");
												List empDataList = new createTable().executeAllSelectQuery(query2.toString(), connection);
												if (empDataList != null && empDataList.size() > 0)
												{
													String mobileNo = null;
													for (Iterator iterator2 = empDataList.iterator(); iterator2.hasNext();)
													{
														Object object2[] = (Object[]) iterator2.next();
														StringBuilder str = new StringBuilder();
														if (object2[3] != null)
														{
															if (contactId[i].equals(object2[4].toString()))
															{
																emailId = object2[1].toString();
																empName = object2[2].toString();
															}
														}

														for (Iterator iterator3 = empDataList.iterator(); iterator3.hasNext();)
														{
															Object object3[] = (Object[]) iterator3.next();

															if (!object2[4].toString().equalsIgnoreCase(object3[4].toString()))
															{
																str.append(object3[2].toString() + ", ");
															}
														}
														if (str != null && str.toString().length() > 0)
														{
															mappedTeam = str.toString().substring(0, str.toString().length() - 1);
														}
														if (mappedTeam == null)
															mappedTeam = "You";
														else
															mappedTeam = mappedTeam + " and You";
													}
												}
											}
											if (object[8] != null)
											{
												status = object[8].toString();
											}
											else
											{
												status = "NA";
											}
											if (object[9] != null)
											{
												actionType = DateUtil.makeTitle(object[9].toString());
											}
											else
											{
												actionType = "NA";
											}
											obj.setCompId(compId);
											obj.setTaskType(taskType);
											obj.setTaskName(taskName);
											obj.setTaskBrief(taskBrief);
											obj.setFrequency(frequency);
											obj.setDueDate(dueDate);
											obj.setDueTime(dueTime);
											obj.setMappedTeam(mappedTeam);
											obj.setActionStatus(status);
											obj.setComment(comments);
											obj.setActionType(actionType);
											obj.setEmpId(contactId[i]);
										}
										listForMail.add(obj);
									}
									String mailText = getConfigMultipleCompMail(listForMail, empName, "Today All Pending Operation Task");
									if (emailId != null && emailId.toString() != "")
										cmnHelper.addMail("", "", emailId, "Today All Pending Operation Task", mailText, "", "Pending", "0", "", "Compliance", connection);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getFrequencyName(String freqAbrivation)
	{
		String frequency = null;

		if (freqAbrivation.equalsIgnoreCase("OT"))
			frequency = "One Time";
		else if (freqAbrivation.equalsIgnoreCase("D"))
			frequency = "Daily";
		else if (freqAbrivation.equalsIgnoreCase("W"))
			frequency = "Weekly";
		else if (freqAbrivation.equalsIgnoreCase("M"))
			frequency = "Monthly";
		else if (freqAbrivation.equalsIgnoreCase("BM"))
			frequency = "Bi-Monthly";
		else if (freqAbrivation.equalsIgnoreCase("Q"))
			frequency = "Quaterly";
		else if (freqAbrivation.equalsIgnoreCase("HY"))
			frequency = "Half Yearly";
		else if (freqAbrivation.equalsIgnoreCase("Y"))
			frequency = "Yearly";
		else if (freqAbrivation.equalsIgnoreCase("O"))
			frequency = "Other";

		return frequency;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	private List<InsertDataTable> setParameterInObj(String paramName, String paramValue, List<InsertDataTable> insertData)
	{
		InsertDataTable object = new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}

	public String getNewDateAfterLeaveCalculate(SessionFactory connection)
	{
		String working_day = null;
		boolean existFlag = false;
		ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connection);
		if (working_date.equals(DateUtil.getCurrentDateUSFormat()))
		{
			working_day = DateUtil.getDayofDate(working_date);
			existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
			if (existFlag)
			{
				return working_date;
			}
			else
			{
				working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 1, working_date), connection);
				working_day = DateUtil.getDayofDate(working_date);
				existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
				if (existFlag)
				{
					return working_date;
				}
				else
				{
					working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 2, working_date), connection);
					working_day = DateUtil.getDayofDate(working_date);
					existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
					if (existFlag)
					{
						return working_date;
					}
					else
					{
						working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 3, working_date), connection);
						working_day = DateUtil.getDayofDate(working_date);
						existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
						if (existFlag)
						{
							return working_date;
						}
					}
				}
			}
		}
		else
		{

			working_day = DateUtil.getDayofDate(working_date);
			existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
			if (existFlag)
			{
				return working_date;
			}
			else
			{
				working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 1, working_date), connection);
				working_day = DateUtil.getDayofDate(working_date);
				existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
				if (existFlag)
				{
					return working_date;
				}
				else
				{

					working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 2, working_date), connection);
					working_day = DateUtil.getDayofDate(working_date);
					existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
					if (existFlag)
					{
						return working_date;
					}
					else
					{
						working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNewDate("day", 3, working_date), connection);
						working_day = DateUtil.getDayofDate(working_date);
						existFlag = new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "COMPL", "", "", "", "", connection);
						if (existFlag)
						{
							return working_date;
						}
					}
				}
			}

		}
		return working_date;
	}

	public void createUser(SessionFactory connection)
	{
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		List<InsertDataTable> insertData11 = new ArrayList<InsertDataTable>();
		List<InsertDataTable> insertDataOBJ = new ArrayList<InsertDataTable>();
		String query = "SELECT mobOne,empName,emailIdOne,empId,id FROM employee_basic WHERE useraccountid IS NULL";
		List dataList = new createTable().executeAllSelectQuery(query, connection);
		TableColumes ob1;
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

		ob1 = new TableColumes();
		ob1.setColumnname("EmpName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("MobileNo");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("EmailId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("userName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("passwoard");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		new createTable().createTable22("newUserDetails", Tablecolumesaaa, connection);
		String passwoard = null;
		try
		{
			passwoard = Cryptography.encrypt("abc123", DES_ENCRYPTION_KEY);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();

				insertData = setParameterInObj1("MobileNo", object[0].toString(), insertData);
				insertData = setParameterInObj1("EmpName", object[1].toString(), insertData);
				insertData = setParameterInObj1("EmailId", object[2].toString(), insertData);

				String userName = null;
				try
				{
					userName = Cryptography.encrypt(object[3].toString(), DES_ENCRYPTION_KEY);
					insertData = setParameterInObj1("userName", object[3].toString(), insertData);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				insertData11 = setParameterInObj1("mobileNo", object[0].toString(), insertData11);
				insertData11 = setParameterInObj1("name", object[1].toString(), insertData11);
				insertData11 = setParameterInObj1("userID", userName, insertData11);
				insertData11 = setParameterInObj1("password", passwoard, insertData11);
				insertData11 = setParameterInObj1("date", DateUtil.getCurrentDateUSFormat(), insertData11);
				insertData11 = setParameterInObj1("time", DateUtil.getCurrentTimeHourMin(), insertData11);
				insertData11 = setParameterInObj1("userName", "prashant", insertData11);
				insertData11 = setParameterInObj1("active", "1", insertData11);
				insertData11 = setParameterInObj1("userForProductId", "1,2,3", insertData11);
				insertData11 = setParameterInObj1("validityFrom", "2014-08-01", insertData11);
				insertData11 = setParameterInObj1("validityto", "2014-08-15", insertData11);
				insertData11 = setParameterInObj1("userType", "N", insertData11);
				insertData11 = setParameterInObj1("linkVal", "vion_ADD#", insertData11);

				insertData = setParameterInObj1("passwoard", "abc123", insertData);
				String str = "SELECT id FROM useraccount WHERE userID='" + userName + "'";
				System.out.println(str);
				List checkDataList = new createTable().executeAllSelectQuery(str, connection);
				if (checkDataList.size() == 0)
				{
					new createTable().insertIntoTable("newUserDetails", insertData, connection);
					int maxId = new createTable().insertDataReturnId("useraccount", insertData11, connection);

					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					String nextUpdateDate = "";
					whereCondition.put("id", object[4].toString());
					setVariables.put("useraccountid", maxId);
					setVariables.put("flag", "0");
					new createTable().updateTableColomn("employee_basic", setVariables, whereCondition, connection);
				}
				insertData.clear();
				insertData11.clear();

			}

		}
	}

	private List<InsertDataTable> setParameterInObj1(String paramName, String paramValue, List<InsertDataTable> insertData)
	{
		InsertDataTable object = new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}

	public String getConfigMail(String taskName, String taskType, String taskBrief, String frequency, String escalation, String complianceID, String mappedTeam, String dueDate, String empName, String empId, String mailTitle, String status)
	{
		String acId = null;
		try
		{
			acId = Cryptography.encrypt("IN-8", DES_ENCRYPTION_KEY);
			empId = Cryptography.encrypt(empId, DES_ENCRYPTION_KEY);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String urlIS = "115.112.237.111:8080/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeTakeActionFromMail?complID=" + complianceID + "&dbName=" + acId + "&empId=" + empId;
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + taskName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + taskType + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + taskBrief + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + dueDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + frequency + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + mappedTeam + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Escalation:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + escalation + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + status + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append(urlIS);
		mailtext
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public String getConfigMultipleCompMail(List<ComplianceDashboardBean> mailDetail, String empName, String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");
		if (mailDetail != null && mailDetail.size() > 0)
		{
			mailtext.append("<br><b>Dear " + empName + ",</b><br>");
			mailtext.append(mailTitle);
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
			mailtext.append("<tr  bgcolor='#e8e7e8'>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Time</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due Comments</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;Type</b></td></tr>");
			for (ComplianceDashboardBean object : mailDetail)
			{
				// String[] object = (String[]) iterator.next();
				String acId = null, empId = null;
				try
				{
					acId = Cryptography.encrypt("IN-1", DES_ENCRYPTION_KEY);
					empId = Cryptography.encrypt(object.getEmpId(), DES_ENCRYPTION_KEY);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				String urlIS = "<a href=http://192.168.1.123:8080/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeTakeActionFromMail?complID=" + object.getCompId() + "&dbName=" + acId + "&empId=" + empId + ">Click Here!!</a>";

				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskType() + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskName() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskBrief() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueTime() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getComment() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionType() + "</b></td></tr>");
			}
			mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<BR>");
			mailtext.append("<BR>");
			mailtext.append("<br>");
			mailtext
					.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		}
		return mailtext.toString();
	}

	public String configReportComp(List<ComplianceDashboardBean> pendingDataMailDetail, List<ComplianceDashboardBean> actionDataMailDetail, String empName, String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><b>Dear " + empName.trim() + ",</b><br>");
		int i = 1;
		if (pendingDataMailDetail != null && pendingDataMailDetail.size() > 0)
		{
			mailtext.append("<br><br><b><center>Compliance Task Due Today i.e. " + DateUtil.getCurrentDateIndianFormat() + "</center></b>");
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
			mailtext.append("<tr><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b>");
			/*
			 * mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type</b>"
			 * );mailtext.append(
			 * "<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Dept&nbsp;Name</b></td>"
			 * );
			 */mailtext.append("<td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td>");
			mailtext.append("<td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief&nbsp;&nbsp;</b></td>");
			mailtext.append("<td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency</b></td>");
			mailtext.append("<td align='center' rowspan='2' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date&nbsp;&nbsp;</b></td>");
			mailtext.append("<td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");

			mailtext.append("<td align='center' colspan='3' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Last&nbsp;Action</b></td></tr>");

			mailtext.append("<tr><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Date&nbsp;&nbsp;&nbsp;</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> By</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments</b></td></tr>");
			for (ComplianceDashboardBean object : pendingDataMailDetail)
			{

				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + i + "</b>");
				/*
				 * mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
				 * +object.getTaskType()+"</b>");mailtext.append(
				 * "<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
				 * +object.getDepartName()+"</b></td>");
				 */
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskName() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskBrief() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus() + "</b></td>");
				mailtext.append("<td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionTakenOn() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionBy() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getLastActionComment() + "</b></td>");
				mailtext.append("</tr>");
				i++;
			}
			mailtext.append("</tbody></table>");

		}

		if (actionDataMailDetail != null && actionDataMailDetail.size() > 0)
		{
			i = 1;
			mailtext.append("<br><br><b><center>Compliances Task Action Taken Today i.e. " + DateUtil.getCurrentDateIndianFormat() + "</center></b>");
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b>");
			/*
			 * mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type</b>"
			 * );mailtext.append(
			 * "<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Dept&nbsp;Name</b></td>"
			 * );
			 */
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief&nbsp;&nbsp;</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency</b></td>");
			mailtext.append("<td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date&nbsp;&nbsp;</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;By</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
			mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments&nbsp;&nbsp;&nbsp;</b></td>");
			for (ComplianceDashboardBean object : actionDataMailDetail)
			{

				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + i + "</b>");
				/*
				 * mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
				 * +object.getTaskType()+"</b>");mailtext.append(
				 * "<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"
				 * +object.getDepartName()+"</b></td>");
				 */
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskName() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskBrief() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getEmpId() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus() + "</b></td>");
				mailtext.append("<td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getComment() + "</b></td>");
				mailtext.append("</tr>");
				i++;
			}
			mailtext.append("</tbody></table>");

		}

		mailtext.append("<font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");

		return mailtext.toString();
	}
}