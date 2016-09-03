package com.Over2Cloud.ctrl.feedback.service;

import hibernate.common.HibernateSessionFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.SMSTest;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.worktimeing.WorkingTimeHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class FeedbackViaTab
{

	String escalation_date = "NA", escalation_time = "NA", resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA", non_working_timing = "00:00", Address_Date_Time = "NA";

	private final CommonOperInterface cbt = new CommonConFactory().createInterface();

	public static void main(String[] args)
	{
		FeedbackViaTab tab = new FeedbackViaTab();
		// tab.openTicket();
	}

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	public List getTicketNumber(SessionFactory connectionSpace, String str)
	{
		List ticketno = new ArrayList();
		try
		{
			String ticket_no = "select ticket_no from feedback_status_feed_paperRating  where via_from like '" + str + "%' order by id desc limit 1";
			ticketno = new createTable().executeAllSelectQuery(ticket_no, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ticketno;
	}

	public List getAllData(String table, String searchfield, String searchfieldvalue, String orderfield, String order, SessionFactory chunkSession)
	{
		List SubdeptallList = new ArrayList();
		try
		{
			String subdeptall = "select * from " + table + " where " + searchfield + "='" + searchfieldvalue + "' ORDER BY " + orderfield + " " + order + "";
			// System.out.println("Sub Category Query is   :: "+subdeptall);
			SubdeptallList = new createTable().executeAllSelectQuery(subdeptall, chunkSession);
			// System.out.println("List Size is   :: "+SubdeptallList.size());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SubdeptallList;
	}

	public List getEmpDataByUserName(String uName, String deptLevel, SessionFactory connectionSpace)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	// tab.openTicket(connectionSpace,
	// "1","Online","Avinash","9540202589","avinashp@dreamsol.biz", "1", "1",
	// "1", "1", "1", "1", "This is test by Avinash", "Saket City");
	public String openTicket(SessionFactory connectionSpace, String subCategory, String viaFrom, String feedbackBy, String mobileno, String emailId, String toDept, String byDept, String location, String feedbackRemarks, String feedType, String clientId, String patType, String userName, String patientId)
	{
		boolean sessionFlag = true;
		String ticketno = null;
		if (sessionFlag)
		{
			try
			{
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();

				String userDeptLevel = CommonWork.deptLevel(connectionSpace);
				String deptLevel = userDeptLevel;
				CommonOperInterface cot = new CommonConFactory().createInterface();
				String openByMobNo = "", openByEmailId = "";
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
				if (colName != null && colName.size() > 0)
				{
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName)
					{
						if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime"))
						{
							if (tableColumes.getColomnName().equalsIgnoreCase("feedback_remarks"))
							{
								TableColumes ob1 = new TableColumes();
								ob1.setColumnname(tableColumes.getColomnName());
								ob1.setDatatype("text");
								ob1.setConstraint("default NULL");
								TableColumnName.add(ob1);
							}
							else
							{
								TableColumes ob1 = new TableColumes();
								ob1.setColumnname(tableColumes.getColomnName());
								ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
								ob1.setConstraint("default NULL");
								TableColumnName.add(ob1);
							}
						}
					}
					// Method calling for creating table on the basis of above
					// column names
					cot.createTable22("feedback_status", TableColumnName, connectionSpace);

					// Object creation for Feedback Pojo Bean
					FeedbackPojo fbp = null;
					// Local variables declaration
					@SuppressWarnings("unused")
					String res_time = "", esc_time = "", allottoId = "1", to_dept_subdept = toDept, by_dept_subdept = byDept, feedby = feedbackBy, mailText = "";
					int ticket = 0;

					// Code for getting the ticket No
					if (viaFrom != null && viaFrom.equalsIgnoreCase("SMS"))
					{
						List fs = getTicketNumber(connectionSpace, "S");
						if (fs.size() > 0)
						{
							if (fs.size() > 0)
							{
								for (Iterator iterator = fs.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									ticket = Integer.parseInt(object.toString().substring(1, object.toString().length())) + 1;
								}
							}
							ticketno = "S" + ticket;
						}
						else
						{
							ticketno = "S" + 1000;
						}
					}
					else if (viaFrom != null && viaFrom.equalsIgnoreCase("Online"))
					{
						List fs = getTicketNumber(connectionSpace, "O");
						if (fs != null && fs.size() > 0)
						{
							if (fs.size() > 0)
							{
								for (Iterator iterator = fs.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									ticket = Integer.parseInt(object.toString().substring(1, object.toString().length())) + 1;
								}
							}
							ticketno = "O" + ticket;
						}
						else
						{
							ticketno = "O" + 1000;
						}
					}
					else if (viaFrom != null && viaFrom.equalsIgnoreCase("Paper"))
					{
						List fs = getTicketNumber(connectionSpace, "P");
						if (fs != null && fs.size() > 0)
						{
							if (fs.size() > 0)
							{
								for (Iterator iterator = fs.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									ticket = Integer.parseInt(object.toString().substring(1, object.toString().length())) + 1;
								}
							}
							ticketno = "P" + ticket;
						}
						else
						{
							ticketno = "P" + 1000;
						}
					}
					else if (viaFrom != null && viaFrom.equalsIgnoreCase("Voice"))
					{
						List fs = getTicketNumber(connectionSpace, "V");
						if (fs != null && fs.size() > 0)
						{
							if (fs.size() > 0)
							{
								for (Iterator iterator = fs.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									ticket = Integer.parseInt(object.toString().substring(1, object.toString().length())) + 1;
								}
							}
							ticketno = "V" + ticket;
						}
						else
						{
							ticketno = "V" + 1000;
						}
					}
					else
					{
						List fs = getTicketNumber(connectionSpace, "T");
						if (fs.size() > 0)
						{
							if (fs.size() > 0)
							{
								for (Iterator iterator = fs.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									ticket = Integer.parseInt(object.toString().substring(1, object.toString().length())) + 1;
								}
							}
							ticketno = "T" + ticket;
						}
						else
						{
							ticketno = "T" + 1000;
						}
					}

					// Code for getting the Escalation Date and Escalation Time
					String feed_brief = null;
					String adrr_time = "", tolevel = "", needesc = "";

					// Code for getting the Escalation Date and Escalation Time
					List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
					if (subCategoryList != null && subCategoryList.size() > 0)
					{
						for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
						{
							Object[] objectCol = (Object[]) iterator.next();
							if (objectCol[3] == null)
							{
								feed_brief = "NA";
							}
							else
							{
								feed_brief = objectCol[3].toString();
							}
							if (objectCol[4] == null)
							{
								adrr_time = "06:00";
							}
							else
							{
								adrr_time = objectCol[4].toString();
							}
							if (objectCol[5] == null)
							{
								res_time = "10:00";
							}
							else
							{
								res_time = objectCol[5].toString();
							}

							if (objectCol[8] == null)
							{
								tolevel = "Level1";
							}
							else
							{
								tolevel = objectCol[8].toString();
							}
							if (objectCol[9] == null)
							{
								needesc = "Y";
							}
							else
							{
								needesc = objectCol[9].toString();
							}
						}
					}

					// / Escalation Stops for Positive
					if (feedType != null && feedType.equalsIgnoreCase("Yes"))
					{
						needesc = "N";
					}

					WorkingHourAction WHA = new WorkingHourAction();
					String date = DateUtil.getCurrentDateUSFormat();
					String time = DateUtil.getCurrentTimeHourMin();
					if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
					{
						List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
						Address_Date_Time = dateTime.get(0) + " " + dateTime.get(1);
						escalation_date = dateTime.get(2);
						escalation_time = dateTime.get(3);
						System.out.println(dateTime.get(0) + " " + dateTime.get(1) + " " + dateTime.get(2) + " " + dateTime.get(3));
					}
					else
					{
						escalation_date = "NA";
						escalation_time = "NA";
					}

					// Method for get Escalation Date & Time after Check Holiday
				//	getEscalationDateTime(adrr_time, res_time, needesc, toDept, "FM");

					List allotto = null;
					List allotto1 = null;

					List<String> one = new ArrayList<String>();
					List<String> two = new ArrayList<String>();
					List<String> two_new = new ArrayList<String>();

					System.out.println("Going to get allot to details>>>>>>>>>>>>>>>>>" + toDept);
					if (toDept != null)
					{
						try
						{
							boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);
							System.out.println("Bedmap[ping " + bedMapping);
							if (bedMapping && toDept != null && !toDept.equals(""))
							{
								String floor_status = FUH.getFloorStatus(toDept, connectionSpace);
								// System.out.println(">>>>>>>>>" +
								// floor_status);
								if (floor_status != null && !floor_status.equalsIgnoreCase("") && floor_status.equalsIgnoreCase("B") && location != null && !location.equalsIgnoreCase(""))
								{
									allotto = FUH.getRandomEmp4BedSpecific(toDept, location, tolevel.substring(5), connectionSpace);
								}
								else
								{
									allotto = FUA.getRandomEmp4Escalation(toDept, "FM", tolevel.substring(5), "", "", connectionSpace);
								}
							}
							else
							{
								allotto = FUA.getRandomEmp4Escalation(toDept, "FM", tolevel.substring(5), "", "", connectionSpace);
							}

							if (allotto == null || allotto.size() == 0)
							{
								String newToLevel = "";
								newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
								allotto = FUA.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
								if (allotto == null || allotto.size() == 0)
								{
									newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
									allotto = FUA.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
									if (allotto == null || allotto.size() == 0)
									{
										newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
										allotto = FUA.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
									}
								}
							}
							allotto1 = FUH.getRandomEmployee(toDept, deptLevel, tolevel.substring(5), allotto, "FM", connectionSpace);
							if (allotto != null && allotto.size() > 0)
							{
								for (Object object : allotto)
								{
									one.add(object.toString());
								}
							}
							if (allotto1 != null && allotto1.size() > 0)
							{
								for (Object object : allotto1)
								{
									two.add(object.toString());
								}
							}

							if (one != null && one.size() > two.size())
							{
								one.removeAll(two);
								if (one.size() > 0)
								{
									for (Iterator iterator = one.iterator(); iterator.hasNext();)
									{
										Object object = (Object) iterator.next();
										allottoId = object.toString();
										break;
									}
								}
							}
							else
							{
								List pending_alloted = FUA.getPendingAllotto(toDept, "FM");
								if (pending_alloted != null && pending_alloted.size() > 0)
								{
									for (Object object : pending_alloted)
									{
										two_new.add(object.toString());
									}
								}
								if (two_new != null && two_new.size() > 0)
								{
									one.removeAll(two_new);
								}
								if (one != null && one.size() > 0)
								{
									allottoId = FUA.getRandomEmployee("FM", one);
								}
								else
								{
									allottoId = FUA.getRandomEmployee("FM", allotto);
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					Properties configProp = new Properties();
					FeedbackViaTab tab = new FeedbackViaTab();
					String adminDeptName = null;
					// Note Change Selected ID Value
					try
					{
						InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
						configProp.load(in);
						adminDeptName = configProp.getProperty("admin");

						if (allottoId != null)
						{
							List empData = cot.executeAllSelectQuery("select mobOne,emailIdOne from employee_basic where id='" + allottoId + "'", connectionSpace);
							if (empData != null && empData.size() > 0)
							{
								Enumeration enu = Collections.enumeration(empData);
								if (enu != null)
								{
									while (enu.hasMoreElements())
									{
										Object[] ob = (Object[]) enu.nextElement();
										if (ob[0] != null)
										{
											openByMobNo = ob[0].toString();
										}
										if (ob[1] != null)
										{
											openByEmailId = ob[1].toString();
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

					// System.out.println("Before Insertion In Table");
					// Setting the column values after getting from the page
					InsertDataTable ob = new InsertDataTable();

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob.setColName("ticket_no");
					ob.setDataName(ticketno);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("clientId");
					ob.setDataName(clientId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("patientId");
					ob.setDataName(patientId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_by");
					ob.setDataName(feedby);
					insertData.add(ob);
					// System.out.println(
					// "FeedBy is as <>>>>>>>>>>>>>>>>>>>>>>>>>>."+feedby);

					ob = new InsertDataTable();
					ob.setColName("feed_by_mobno");
					ob.setDataName(openByMobNo);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("patMobNo");
					ob.setDataName(mobileno);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_by_emailid");
					ob.setDataName(openByEmailId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("patEmailId");
					ob.setDataName(emailId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("deptHierarchy");
					ob.setDataName(deptLevel);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("by_dept_subdept");
					ob.setDataName(by_dept_subdept);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("subcatg");
					ob.setDataName(subCategory);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_brief");
					ob.setDataName(feed_brief);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("to_dept_subdept");
					ob.setDataName(toDept);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("allot_to");
					ob.setDataName(allottoId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation_date");
					ob.setDataName(escalation_date);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation_time");
					ob.setDataName(escalation_time);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("open_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("open_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("level");
					ob.setDataName("Level1");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Pending");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("via_from");
					ob.setDataName(viaFrom);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_registerby");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location");
					ob.setDataName(location);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feedback_remarks");
					ob.setDataName(feedbackRemarks);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("moduleName");
					ob.setDataName("FM");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("non_working_time");
					ob.setDataName(non_working_timing);
					insertData.add(ob);

					// Method calling for inserting the values in the
					// feedback_status table
					boolean status1 = false;
					if (allottoId != null && !(allottoId.equalsIgnoreCase("")))
					{
						status1 = cot.insertIntoTable("feedback_status", insertData, connectionSpace);
						// System.out.println(
						// "Data inserted in Feedback Status Feedback"+status1);
						int id3 = new createTable().getMaxId("feedbackdata", connectionSpace);
						StringBuffer buffer = new StringBuffer("update feedbackdata set handledBy='" + allottoId + "' where id='" + id3 + "'");
						new createTable().executeAllUpdateQuery(buffer.toString(), connectionSpace);
						insertData.clear();
					}

					// System.out.println("Status 1 is as >>>>>>>>>>>>>>>>>>"+
					// status1);
					// Block for sending SMS and Mail to the Engineer and
					// Complainant after inserting the data in the feedback
					// status table successfully
					if (status1)
					{
						// List for holding the data of currently lodged
						List dataList = new createTable().executeAllSelectQuery("select empName,mobOne,emailIdOne from employee_basic where id='" + allottoId + "'", connectionSpace);

						fbp = new FeedbackPojo();
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object != null)
								{
									fbp.setFeedback_allot_to(object[0].toString());
									fbp.setAllot_to_mobno(object[1].toString());
									fbp.setEmailIdOne(object[2].toString());
									fbp.setFeedaddressing_time(DateUtil.getCurrentTimewithSeconds());
								}
							}
						}
						dataList.clear();
						dataList = new createTable().executeAllSelectQuery("select escalation_date,escalation_time,feed_by,feed_brief from feedback_status", connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object != null)
								{
									if (object[0] != null)
									{
										fbp.setEscalation_date(object[0].toString());
									}
									if (object[1] != null)
									{
										fbp.setEscalation_time(object[1].toString());
									}
									if (object[2] != null)
									{
										fbp.setFeed_by(object[2].toString());
									}
									if (object[3] != null)
									{
										fbp.setFeed_brief(object[3].toString());
									}
									fbp.setFeed_registerby(feedby);
									fbp.setTicket_no(ticketno);
									fbp.setFeedback_to_dept(adminDeptName);
									fbp.setFeedback_catg("Patient Service");
									fbp.setFeedback_subcatg("Not Satisfied With Services");
									fbp.setFeedback_by_dept(adminDeptName);
									fbp.setLocation(location);
								}
							}
						}
						// if (fbp!=null) Conmmented on 28 May 2014
						if (fbp != null)
						{
							/*
							 * SMSTest fAct = new SMSTest(); // Block for
							 * sending sms for Level1 Engineer if
							 * (fbp.getAllot_to_mobno() != null &&
							 * fbp.getAllot_to_mobno() != "" &&
							 * fbp.getAllot_to_mobno().trim().length() == 10 &&
							 * (fbp.getAllot_to_mobno().startsWith("9") ||
							 * fbp.getAllot_to_mobno().startsWith("8") ||
							 * fbp.getAllot_to_mobno().startsWith("7"))) {
							 * String levelMsg = "Open Alert: Ticket No: " +
							 * fbp.getTicket_no() + ", Open By: " +
							 * DateUtil.makeTitle(fbp.getFeed_registerby()) +
							 * ", Location: " + fbp.getLocation() +
							 * ", Feedback: " + fbp.getFeed_brief() +
							 * ", To be closed by :" +
							 * DateUtil.convertDateToIndianFormat
							 * (fbp.getEscalation_date()) + "," +
							 * fbp.getEscalation_time(); new
							 * MsgMailCommunication
							 * ().addMessage(fbp.getFeedback_allot_to(),
							 * fbp.getFeedback_to_dept(),
							 * fbp.getAllot_to_mobno(), levelMsg, "", "Pending",
							 * "0", "FM"); } // Block for creating mail for
							 * Level1 Engineer if (fbp.getEmailIdOne() != null
							 * && !fbp.getEmailIdOne().equals("")) { String
							 * mailBody = getConfigMessage(fbp,
							 * "Open Feedback Alert", "Pending", deptLevel,
							 * true); boolean send = new
							 * MsgMailCommunication().addMail
							 * (fbp.getFeedback_allot_to(),
							 * fbp.getFeedback_to_dept(), fbp.getEmailIdOne(),
							 * "Open Feedback Alert", mailBody.toString(),
							 * ticketno, "Pending", "0", "", "FM"); }
							 */
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ticketno;
	}

	public void getEscalationDateTime(String adrr_time, String res_time, String needesc, String toDept, String module)
	{

		String date_time = "", level_date_time = "";
		String holiday_status = "", startTime = "", endTime = "", callflag = "";
		String dept_id = toDept;
		String date = DateUtil.getCurrentDateUSFormat();
		String currentday = DateUtil.getCurrentDayofWeek();
		// List of working timing data
		List workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			holiday_status = workingTimingData.get(0).toString();
		}
		// System.out.println("Holiday Status  ");
		if (holiday_status.equals("Y") || holiday_status.equals("N"))
		{
			// Name should be different for date & current day
			if (holiday_status.equals("Y"))
			{
				date = new WorkingTimeHelper().getWorkingDate(DateUtil.getCurrentDateUSFormat());
				// System.out.println("Inside yes Working date   "+date);
				currentday = DateUtil.getDayofDate(date);
				// System.out.println("Inside yes Working day   "+currentday);
			}
			workingTimingData.clear();
			workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
			if (workingTimingData != null && workingTimingData.size() > 0)
			{
				startTime = workingTimingData.get(1).toString();
				endTime = workingTimingData.get(2).toString();
				// Here we know the complaint lodging time is inside the the
				// start and end time or not
				callflag = DateUtil.timeInBetween(date, startTime, endTime, DateUtil.getCurrentTime());
				// System.out.println("Call Flag Value is   "+callflag);
				if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
				{
					// System.out.println("Inside before");
					date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);

					// (Differrence between current date/time and date &
					// starttime)
					String newdatetime[] = date_time.split("#");
					// Check the date time is lying inside the time block
					boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], date, endTime);
					if (flag)
					{
						level_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, "00:00");
						non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
					}
					else
					{
						String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
						String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
						String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
						workingTimingData.clear();

						date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1));
						currentday = DateUtil.getDayofDate(date);

						workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							startTime = workingTimingData.get(1).toString();
							String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
							date_time = DateUtil.newdate_AfterTime(new_date_time, main_difference);
							String level_difference = DateUtil.getTimeDifference(main_difference, res_time);
							level_date_time = DateUtil.newdate_AfterTime(new_date_time, level_difference);
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
						}
					}
				}
				else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
				{
					// System.out.println("Inside middle");
					// Escalation date time at the time of opening the ticket
					date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
					// System.out.println("New Date Time  "+date_time);
					String newdatetime[] = date_time.split("#");
					// Check the date time is lying inside the time block
					boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], DateUtil.getCurrentDateUSFormat(), endTime);
					// System.out.println("Flag Value is  "+flag);
					if (flag)
					{
						// System.out.println("Inside flag true");
						level_date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, "00:00");
						non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
					}
					else
					{
						// System.out.println("Inside flag false");
						String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
						// System.out.println("First difference "+timeDiff1);
						String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), DateUtil.getCurrentDateUSFormat(), endTime);
						// System.out.println("Second difference "+timeDiff2);
						String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
						// System.out.println("Main Differnce "+timeDiff2);
						workingTimingData.clear();

						date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1));
						// System.out.println("New Current date  "+date);
						currentday = DateUtil.getDayofDate(date);
						// System.out.println("New Current day  "+currentday);

						workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
						// System.out.println("New Working Time data  "+
						// workingTimingData);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							startTime = workingTimingData.get(1).toString();
							// System.out.println("New Start time  "+startTime);
							String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
							// System.out.println("New Date time  "+startTime);
							date_time = DateUtil.newdate_AfterTime(new_date_time, main_difference);
							String level_difference = DateUtil.getTimeDifference(main_difference, res_time);
							level_date_time = DateUtil.newdate_AfterTime(new_date_time, level_difference);
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							// System.out.println("Final non working time  "+
							// non_working_timing);
						}
					}
				}
				else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
				{
					workingTimingData.clear();
					if (holiday_status.equalsIgnoreCase("Y"))
					{
						date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateAfter(1));
						currentday = DateUtil.getDayofDate(date);
					}
					else if (holiday_status.equalsIgnoreCase("N"))
					{
						date = DateUtil.getNextDateAfter(1);
						currentday = DateUtil.getDayofDate(date);
					}

					workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						endTime = workingTimingData.get(2).toString();
						date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
						String newdatetime[] = date_time.split("#");
						// Check the date time is lying inside the time block
						boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], newdatetime[0], endTime);
						if (flag)
						{
							// System.out.println("Inside Flag true");
							// System.out.println("Date Value  "+date);
							//System.out.println("Start Time Value  "+startTime)
							// ;
							level_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, "00:00");
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
						}
						else
						{
							String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
							String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
							String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
							workingTimingData.clear();

							date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1));
							currentday = DateUtil.getDayofDate(date);

							// String nextday= DateUtil.getNextDayofWeek();
							workingTimingData = new WorkingTimeHelper().getWorkingTimeData(module, currentday, dept_id);
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								startTime = workingTimingData.get(1).toString();
								String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
								date_time = DateUtil.newdate_AfterTime(new_date_time, main_difference);
								String level_difference = DateUtil.getTimeDifference(main_difference, res_time);
								level_date_time = DateUtil.newdate_AfterTime(new_date_time, level_difference);
								non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							}
						}
					}
				}
			}
		}

		String[] date_time_arr = date_time.split("#");
		if (date_time_arr.length > 0)
		{
			if (needesc != null && !needesc.equals("") && needesc.equalsIgnoreCase("Y"))
			{
				escalation_date = date_time_arr[0];
				escalation_time = date_time_arr[1];
				resolution_date = date_time_arr[0];
				resolution_time = date_time_arr[1];
			}
			else
			{
				resolution_date = date_time_arr[0];
				resolution_time = date_time_arr[1];
			}
		}

		String[] level_date_time_arr = level_date_time.split("#");
		if (level_date_time_arr.length > 0)
		{
			level_resolution_date = level_date_time_arr[0];
			level_resolution_time = level_date_time_arr[1];
		}
		// System.out.println("Non Working Time   "+non_working_timing);

	}

	public String getConfigMessage(FeedbackPojo feedbackObj, String title, String status, String deptLevel, boolean flag)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Management</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
		mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + title + "</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
		if (flag)
		{
			mailtext.append("<b>Dear " + DateUtil.makeTitle(feedbackObj.getFeedback_allot_to()) + ",</b>");
		}
		else if (!flag)
		{
			mailtext.append("<b>Dear " + DateUtil.makeTitle(feedbackObj.getFeed_registerby()) + ",</b>");
		}

		mailtext.append("<br><br><b>Hello !!!</b><br>");
		if (flag)
		{
			mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
		}
		else if (!flag)
		{
			mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");
		}
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getTicket_no() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.getCurrentDateIndianFormat() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.getCurrentTime().substring(0, 5) + " Hrs</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getFeed_registerby() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getFeedback_by_dept() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getFeed_brief() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Addressing&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.newTime(feedbackObj.getFeedaddressing_time()).substring(0, 5) + " Hrs</td></tr>");
		if (status.equalsIgnoreCase("Resolved"))
		{
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> RCA:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getSpare_used() + "</td></tr>");
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> CAPA:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + feedbackObj.getResolve_remark() + "</td></tr>");
		}
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		try
		{
			if (status.equalsIgnoreCase("Pending"))
			{/*
			 * 
			 * InputStream in = this.getClass ().getResourceAsStream(
			 * "/com/Over2Cloud/CommonClasses/ipAddress.properties" );
			 * Properties configProp = new Properties(); configProp.load(in);
			 * 
			 * String hostName=configProp .getProperty("serverIp");
			 * mailtext.append( "Click on the Link Below to Close the Ticket" );
			 * mailtext.append("<br>"); mailtext .append("http://"+ hostName+
			 * "/cloudapp/view/Over2Cloud/feedback/actionOnTicketByMail.action?feedId="
			 * +feedbackObj.getId()+ "&ticketNo=" +feedbackObj.getTicket_no (
			 * )+"&feedbackBy="+feedbackObj .getFeed_registerby()+
			 * "&mobileno="+feedbackObj. getFeedback_by_mobno ()+"&emailId="
			 * +feedbackObj .getFeedback_by_emailid ()+
			 * "&feedBreif="+feedbackObj . getFeed_brief().replaceAll
			 * (" ","%20")+"&openDate="+ feedbackObj .getOpen_date()
			 * +"&openTime=" +feedbackObj. getOpen_time( )+"&allotto="
			 * +feedbackObj. getFeedback_allot_to());
			 */
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		mailtext
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public List<Integer> getRandomEmp4Escalation(String dept_subDept, String deptLevel, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			if (deptLevel.equals("2"))
			{
				qry = " select emp.id from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join subdepartment sub_dept on emp.subdept = sub_dept.id" + " inner join roaster_conf roaster on emp.empId = roaster.empId" + " inner join shift_conf shift on sub_dept.id = shift.dept_subdept" + " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='Level1' and sub_dept.id='" + dept_subDept + "' and shift.deptHierarchy='2' and shift.shiftFrom<='" + DateUtil.getCurrentTime()
						+ "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			else if (deptLevel.equals("1"))
			{
				qry = " select emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join department dept on emp.dept = dept.id" + " inner join roaster_conf roaster on emp.empId = roaster.empId" + " inner join shift_conf shift on dept.id = shift.dept_subdept" + " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='Level1' and dept.id='" + dept_subDept + "' and shift.deptHierarchy='1' and shift.shiftFrom<='"
						+ DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List<Integer> getRandomEmployee(String dept_subDept, String deptLevel, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		try
		{
			qry = "select distinct allot_to from feedback_status where to_dept_subdept='" + dept_subDept + "' and deptHierarchy='" + deptLevel + "' and open_date='" + DateUtil.getCurrentDateUSFormat() + "'";
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List getFeedbackDetail(String ticketno, String deptLevel, String status, String id, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (deptLevel.equals("2"))
			{
				query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
				query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
				query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
				query.append("emp.mobOne,emp.emailIdOne,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,dept2.deptName as todept,subdept2.subdeptname as tosubdept");
				query.append(" from feedback_status as feedback");
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
				query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
				query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				if (id != null && !id.equals("") && status != null && !status.equals(""))
				{
					query.append(" where feedback.id='" + id + "' ");
				}
				else
				{/*
				 * query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"
				 * +status+
				 * "' and feedback.id=(select max(id) from feedback_status)" );
				 */
				}
			}

			else if (deptLevel.equals("1"))
			{
				query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
				query.append("feedback.feed_by_emailid,emp.empName as allotto,feedtype.fbType,catg.categoryName,");
				query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
				query.append("dept1.deptName as bydept,dept2.deptName as todept");
				if (status.equalsIgnoreCase("Resolved"))
				{
					query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
				query.append(" from feedback_status as feedback");
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				if (status.equalsIgnoreCase("Resolved"))
				{
					query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
				}
				if (id != null && !id.equals(""))
				{
					query.append(" where feedback.status='" + status + "' and feedback.id='" + id + "' ");
				}
				else
				{
					query.append(" where feedback.ticket_no='" + ticketno + "' and feedback.status='" + status + "' and feedback.id=(select max(id) from feedback_status)");
				}
			}

			// System.out.println(
			// "Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			// +query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public FeedbackPojo setFeedbackDataValues(List data, String status, String deptLevel)
	{
		FeedbackPojo fbp = null;
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				fbp.setTicket_no(object[0].toString());
				fbp.setFeed_registerby(object[1].toString());
				fbp.setFeedback_by_mobno(object[2].toString());
				fbp.setFeedback_by_emailid(object[3].toString());
				fbp.setFeedback_allot_to(object[4].toString());
				fbp.setFeedtype(object[5].toString());
				fbp.setFeedback_catg(object[6].toString());
				fbp.setFeedback_subcatg(object[7].toString());
				fbp.setFeed_brief(object[8].toString());
				fbp.setFeedaddressing_time(object[9].toString());
				fbp.setLocation(object[11].toString());
				fbp.setOpen_date(object[12].toString());
				fbp.setOpen_time(object[13].toString());
				fbp.setEscalation_date(object[14].toString());
				fbp.setEscalation_time(object[15].toString());
				fbp.setLevel(object[16].toString());
				fbp.setStatus(object[17].toString());
				fbp.setVia_from(object[18].toString());

				if (!status.equals("Pending"))
				{
					fbp.setAction_by(object[34].toString());
				}

				if (status.equals("High Priority"))
				{
					fbp.setHp_date(object[19].toString());
					fbp.setHp_time(object[20].toString());
					fbp.setHp_reason(object[21].toString());
				}
				else if (status.equals("Snooze"))
				{
					fbp.setSn_reason(object[22].toString());
					fbp.setSn_on_date(object[23].toString());
					fbp.setSn_on_time(object[24].toString());
					fbp.setSn_date(object[25].toString());
					fbp.setSn_time(object[26].toString());
					fbp.setSn_duration(object[27].toString());
				}
				else if (status.equals("Ignore"))
				{
					fbp.setIg_date(object[28].toString());
					fbp.setIg_time(object[29].toString());
					fbp.setIg_reason(object[30].toString());
				}
				else if (object[31] != null && !object[31].toString().equals("") && object[32] != null && !object[32].toString().equals(""))
				{
					fbp.setTransfer_date(object[31].toString());
					fbp.setTransfer_time(object[32].toString());
					fbp.setTransfer_reason(object[33].toString());
				}

				else if (deptLevel.equals("2") && status.equals("Resolved"))
				{
					fbp.setResolve_date(object[41].toString());
					fbp.setResolve_time(object[42].toString());
					fbp.setResolve_duration(object[43].toString());
					fbp.setResolve_remark(object[44].toString());
					fbp.setResolve_by(object[45].toString());
					fbp.setSpare_used(object[46].toString());
				}

				else if (deptLevel.equals("1") && status.equals("Resolved"))
				{
					fbp.setResolve_date(object[39].toString());
					fbp.setResolve_time(object[40].toString());
					fbp.setResolve_duration(object[41].toString());
					fbp.setResolve_remark(object[42].toString());
					fbp.setResolve_by(object[43].toString());
					fbp.setSpare_used(object[44].toString());
				}

				if (deptLevel.equals("2"))
				{
					fbp.setFeedback_by_dept(object[37].toString());
					fbp.setFeedback_by_subdept(object[38].toString());
					fbp.setFeedback_to_dept(object[39].toString());
					fbp.setFeedback_to_subdept(object[40].toString());
				}
				else if (deptLevel.equals("1"))
				{
					fbp.setFeedback_by_dept(object[37].toString());
					fbp.setFeedback_to_dept(object[38].toString());
				}
				fbp.setMobOne(object[35].toString());
				fbp.setEmailIdOne(object[36].toString());
			}
		}
		return fbp;
	}

	public String getRandomEmployee(String tableId, List empId, SessionFactory connectionSpace)
	{
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String allotmentId = "";
		String qry = null;
		try
		{
			session = HibernateSessionFactory.getSession();
			qry = "select allot_to from feedback_status where open_date='" + DateUtil.getCurrentDateUSFormat() + "' group by allot_to having allot_to in " + empId.toString().replace("[", "(").replace("]", ")") + " order by count(allot_to) limit 1 ";
			/*
			 * Query query=session.createSQLQuery(qry);
			 * query.setParameterList("empId", empId);
			 */
			//System.out.println("Querry is as<><><><>><<><>><"+qry.toString());
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				allotmentId = object.toString();
			}
		}
		return allotmentId;
	}
}