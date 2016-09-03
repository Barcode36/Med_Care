package com.Over2Cloud.ctrl.feedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.hibernate.SessionFactory;

import java.util.ArrayList;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.worktimeing.WorkingTimeHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LodgeFeedbackForFeedback extends GridPropertyBean
{
	// Feedback Data Id
	private String feedDataId;
	private String feedTicketId;
	private String subCategory;
	private String viaFrom;
	private String feedbackBy;
	private String mobileno;
	private String emailId;
	private String selectedId;
	private String tosubdept;
	private String bydept_subdept;
	private String bysubdept;
	private String todept;
	private String byDept;
	private String feed_brief;
	private String location;
	private String recvSMS;
	private String recvEmail;
	// private int id;
	private String ticket_no;
	private String catg;
	private String open_date;
	private String open_time;
	private String allotto;
	private String subCatg;
	private String escTime;
	private String esc_Date;
	private String allot_to_mobno;
	private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";
	private String addrTime;
	private String addrDate;
	private String addr_Date_Time = "";
	private String clientId;
	private String compType;
	private String subdept;

	String escalation_date = "NA", escalation_time = "NA", resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA", non_working_timing = "00:00",
			new_escalation_datetime = "0000-00-00#00:00";

	private final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private final MsgMailCommunication communication = new MsgMailCommunication();

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	public String registerFeedbackViaDeptForFeedback()
	{
		String returnResult = SUCCESS;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();

				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();

				String accountId = (String) session.get("accountid");
				String openBy = "NA";
				String openByMailId = "NA";
				String openByMobNo = "NA";
				String doctName = "NA";
				String mode = "", room_bed_no = "", Address_Date_Time = "NA";
				if (getFeedDataId() != null)
				{
					com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo pojo = null;
					List dataList = new LodgeFeedbackHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								if (object[0] != null)
								{
									openBy = object[0].toString();
								}
								if (object[1] != null)
								{
									openByMobNo = object[1].toString();
								}
								if (object[2] != null)
								{
									openByMailId = object[2].toString();
								}
							}
						}
						List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name",
								"feedback_lodge_configuration");
						if (colName != null && colName.size() > 0)
						{
							List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
							for (GridDataPropertyView tableColumes : colName)
							{
								if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category")
										&& !tableColumes.getColomnName().equals("resolutionTime"))
								{
									TableColumes ob1 = new TableColumes();
									ob1.setColumnname(tableColumes.getColomnName());
									ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
									ob1.setConstraint("default NULL");
									TableColumnName.add(ob1);
								}
							}

							// Method calling for creating table on the basis of
							// above column names
							cbt.createTable22("feedback_status", TableColumnName, connectionSpace);

							// Object creation for Feedback Pojo Bean
							FeedbackPojo fbp = null;
							// Local variables declaration
							@SuppressWarnings("unused")
							String res_time = "", adrr_time = "", tolevel = "", esc_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", needesc = "";

							// Code for getting the Escalation Date and
							// Escalation Time
							List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
							if (subCategoryList != null && subCategoryList.size() > 0)
							{
								for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
								{
									Object[] objectCol = (Object[]) iterator.next();
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

							WorkingHourAction WHA=new WorkingHourAction();
							String[] adddate_time = null;
							String date=DateUtil.getCurrentDateUSFormat();
							String time=DateUtil.getCurrentTimeHourMin();
							if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
							{
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
								Address_Date_Time = dateTime.get(0)+" "+dateTime.get(1);
								escalation_date = dateTime.get(2);
								escalation_time = dateTime.get(3);
							}
							else
							{
								
							}


							// Block for setting the Allotment Id/Feedback By
							// Name/Feedback By Mobile No/ Feedback By email Id
							// and ToDept/ByDept OR To Sub Dept/By Sub dept in
							// the case of Via Call feedback Lodging

							// Block for setting the Allotment Id/Feedback By
							// Name/Feedback By Mobile No/ Feedback By email Id
							// and ToDept/ByDept OR To Sub Dept/By Sub dept in
							// the case of Online feedback Lodging
							if (viaFrom != null && viaFrom.equals("online"))
							{
								StringBuffer buffer = new StringBuffer("select mode,centerCode from feedbackdata where id='" + getFeedDataId() + "'");
								List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
								if (data != null & data.size() > 0)
								{
									for (Iterator iterator = data.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null)
										{
											mode = object[0].toString();
										}
										if (object[1] != null && !object[1].toString().equals(""))
										{
											room_bed_no = object[1].toString();
										}
										else
										{
											room_bed_no = "NA";
										}
									}
								}

								// System.out.println("Inside online block");
								List empData = new LodgeFeedbackHelper().getEmpWithoutRoasterForFeedback(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
								if (empData != null && empData.size() > 0)
								{
									for (Iterator iterator = empData.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null && !object[0].toString().equals(""))
										{
											feedby = object[0].toString();
										}
										else
										{
											feedby = "NA";
										}

										if (object[1] != null && !object[1].toString().equals(""))
										{
											feedbymob = object[1].toString();
										}
										else
										{
											feedbymob = "NA";
										}

										if (object[2] != null && !object[2].toString().equals(""))
										{
											feedbyemailid = object[2].toString();
										}
										else
										{
											feedbyemailid = "NA";
										}

										if (object[3] != null && !object[3].toString().equals(""))
										{
											by_dept_subdept = object[3].toString();
										}
										else
										{
											by_dept_subdept = "-1";
										}
									}
								}

								if (deptLevel != null && deptLevel.equals("2"))
								{
									to_dept_subdept = subdept;
								}
								else if (deptLevel != null && deptLevel.equals("1"))
								{
									to_dept_subdept = todept;
								}
								// New Ticket No code starts
								// String deptid = new
								// HelpdeskUniversalAction().getData("subdepartment",
								// "deptid", "id", to_dept_subdept);

								ticketno = new FeedbackUniversalHelper().getTicketNo(subdept, "FM", connectionSpace);
								// ticketno = new
								// LodgeFeedbackHelper().getTicketNo(subdept,
								// connectionSpace);
								// New Ticket No Code Ends
								if (getFeedTicketId() != null)
								{
									FeedbackActionControl feedbackActionControl = new FeedbackActionControl();
									ticketno = feedbackActionControl.getTicketNo(Integer.parseInt(getFeedTicketId())) + ticketno;
								}
								List allotto = null;
								List allotto1 = null;
								List<String> one = new ArrayList<String>();
								List<String> two = new ArrayList<String>();

								String toDeptId = subdept;
								List<String> two_new = new ArrayList<String>();
								if (toDeptId != null)
								{
									// allottoId = String.valueOf(new
									// LodgeFeedbackHelper().getRandomEmp4EscalationFeedback(toDeptId,deptLevel,
									// connectionSpace,"1").get(0));

									try
									{
										// allotto =
										// HUA.getRandomEmp4Escalation(toDeptId,"FM",tolevel.substring(5),"","",connectionSpace);

										// Worked to show details of Patient in
										// Ticket
										if (getFeedDataId() != null)
										{
											pojo = new LodgeFeedbackHelper().getPatientDetails(getFeedDataId(), connectionSpace);
										}

										boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);

										System.out.println("For Bed Mapping >>>>>" + bedMapping);
										if (bedMapping && toDeptId != null && !toDeptId.equals(""))
										{

											String floor_status = FUH.getFloorStatus(toDeptId, connectionSpace);
											System.out.println("IFFFFF" + floor_status);
											if (floor_status != null && floor_status.equalsIgnoreCase("B") && pojo.getCentreCode() != null && !pojo.getCentreCode().equalsIgnoreCase(""))
											{
												System.out.println("Flooer Stasus" + floor_status);
												allotto = FUH.getRandomEmp4BedSpecific(toDeptId, pojo.getCentreCode(), tolevel.substring(5), connectionSpace);
											}
											else
											{
												System.out.println("Esle of Floor Status");
												allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connectionSpace);
											}
										}
										else
										{
											System.out.println("ELSEEEEEE");
											allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connectionSpace);
										}

										if (allotto == null || allotto.size() == 0)
										{
											String newToLevel = "";
											newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
											// System.out.println("New To Level inside First If  "+newToLevel);
											allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
											if (allotto == null || allotto.size() == 0)
											{
												newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
												// System.out.println("New To Level inside Second If  "+newToLevel);
												allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
												if (allotto == null || allotto.size() == 0)
												{
													newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
													// System.out.println("New To Level inside Third If  "+newToLevel);
													allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
												}
											}
										}

										allotto1 = FUH.getRandomEmployee(toDeptId, deptLevel, tolevel.substring(5), allotto, "FM", connectionSpace);

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
										// System.out.println("List One  "+one.toString());
										// System.out.println("List Two  "+two.toString());

										if (one != null && one.size() > two.size())
										{
											// System.out.println("Inside If Block");
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
											List pending_alloted = FUA.getPendingAllotto(toDeptId, "FM");
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
							}
							boolean status1 = false;
							if (allottoId != null && !(allottoId.equalsIgnoreCase("")))
							{
								// Setting the column values after getting from
								// the page
								InsertDataTable ob = new InsertDataTable();
								List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
								ob.setColName("ticket_no");
								ob.setDataName(ticketno);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_by_mobno");
								ob.setDataName(openByMobNo);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_by_emailid");
								ob.setDataName(openByMailId);
								insertData.add(ob);

								if (pojo != null)
								{
									ob = new InsertDataTable();
									ob.setColName("feed_by");
									ob.setDataName(pojo.getName());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("location");
									ob.setDataName(pojo.getCentreCode());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("clientId");
									ob.setDataName(pojo.getCrNo());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("patMobNo");
									ob.setDataName(pojo.getMobileNo());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("patEmailId");
									ob.setDataName(pojo.getEmailId());
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName("feed_by");
									ob.setDataName(openBy);
									insertData.add(ob);
								}

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

								if (pojo != null)
								{
									ob = new InsertDataTable();
									ob.setColName("feed_brief");
									ob.setDataName(getFeed_brief());
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName("feed_brief");
									ob.setDataName(feed_brief);
									insertData.add(ob);
								}

								ob = new InsertDataTable();
								ob.setColName("to_dept_subdept");
								ob.setDataName(to_dept_subdept);
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
								ob.setColName("Addr_date_time");
								ob.setDataName(Address_Date_Time);
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
								ob.setDataName(mode);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_registerby");
								ob.setDataName(openBy);
								insertData.add(ob);

								/*
								 * ob=new InsertDataTable();
								 * ob.setColName("feedback_remarks");
								 * ob.setDataName(pojo.getRemarks());
								 * insertData.add(ob);
								 */

								ob = new InsertDataTable();
								ob.setColName("moduleName");
								ob.setDataName("FM");
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("non_working_time");
								ob.setDataName(non_working_timing);
								insertData.add(ob);

								// Method calling for inserting the values in
								// the feedback_status table
								status1 = cbt.insertIntoTable("feedback_status", insertData, connectionSpace);
								insertData.clear();
							}

							// Block for sending SMS and Mail to the Engineer
							// and Complainant after inserting the data in the
							// feedback status table successfully
							if (status1)
							{
								// List for holding the data of currently lodged
								List data = new FeedbackUniversalHelper().getFeedbackDetail(ticketno, deptLevel, "Pending", "", connectionSpace);
								fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, "Pending", deptLevel);
								setTodept(fbp.getFeedback_to_dept());
								printTicket(fbp);
								if (fbp != null)
								{

									String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);

									// Block for sending sms for Level1 Engineer
									boolean mailFlag = false;
									if (needesc.equalsIgnoreCase("Y") && fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10
											&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
									{
										// Open Alert: Ticket No: <Ticket No.>,
										// Open By: <PCC Name> for <Patient
										// Name>, <IP No.>, Location: 2273-ESP,
										// Feedback: <Brief (120)>, To be closed
										// by: <Date & Time>.
										// Open Alert: ticket no :<01001it1002>,
										// Open by:<deeksha> for <patient_name>,
										// Location: <Room_no> , feedback
										// :<Observation>, To be closed by
										// :<13-12-2013,16:50>
										String levelMsg = "Open Alert: Ticket No: " + fbp.getTicket_no() + ", Open By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + " for "
												+ DateUtil.makeTitle(fbp.getFeed_by()) + "," + fbp.getCr_no() + ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg()
												+ ", To be closed by :" + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time();
										// String levelMsg =
										// "Open Alert: Ticket No: "+fbp.getTicket_no()+", To Be Closed By: "+fbp.getEscalation_date()+","+fbp.getEscalation_time()+
										// ", Reg. By: "+
										// DateUtil.makeTitle(fbp.getFeed_registerby())+", Location: "+fbp.getLocation()+" , Brief: "+
										// fbp.getFeed_brief()+ ".";
										communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "FM");
									}
									// Block for sending sms for Complainant
									if (needesc.equalsIgnoreCase("Y") && getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != ""
											&& fbp.getFeedback_by_mobno().trim().length() == 10
											&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
									{
										// Dear <Deeksha Nangia>,Ticket No:
										// <O1000EN1002>, for <PatientName>,
										// Location: <Room_no> , feedback
										// :<Observation>, Resolution Time:
										// <14-12-2013,01:48>,Status is :< open>
										// String complainMsg = "Dear "+
										// DateUtil.makeTitle(fbp.getFeed_registerby())
										// + ","+ " Ticket No: "
										// +fbp.getTicket_no()+ ", for ""+",
										// Addressing Time:
										// "+DateUtil.getCurrentDateIndianFormat()+","+DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5)+ ",
										// Location: "+fbp.getLocation()+",
										// Brief: "+fbp.getFeed_brief() + " is
										// open.";
										String complainMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + "," + " Ticket No: " + fbp.getTicket_no() + ", for " + fbp.getFeed_by()
												+ ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg() + ", Resolution Time: " + DateUtil.getCurrentDateIndianFormat() + ","
												+ DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5) + " ,Status is : Open";
										// communication.addMessage(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_mobno(),
										// complainMsg, ticketno, "Pending",
										// "0","FM");
									}
									// Block for creating mail for Level1
									// Engineer
									if (needesc.equalsIgnoreCase("Y") && fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true, orgName);
										communication.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketno,
												"Pending", "0", null, "FM");
									}
									// Block for creating mail for complainant
									if (needesc.equalsIgnoreCase("Y") && getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("")
											&& !fbp.getFeedback_by_emailid().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, false, orgName);
										// new
										// MsgMailCommunication().sendMail(fbp.getFeedback_by_emailid(),
										// "Open Feedback Alert", mailText,
										// ticketno,"Pending",
										// "0",fbp.getFeedback_allot_to());
										// communication.addMail(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_emailid(),
										// "Open Feedback Alert",
										// mailText.toString(),
										// ticketno,"Pending", "0",null,"FM");
									}

									int feedId = cbt.getMaxId("feedback_status", connectionSpace);
									// System.out.println("getFeedDataId() is as >>>>>>>>>>>>>>>>>>>>>>"+getFeedDataId());
									if (getFeedDataId() != null)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										Map<String, Object> condtnBlock = new HashMap<String, Object>();
										CommonOperInterface cbt = new CommonConFactory().createInterface();
										wherClause.put("status", "Ticket Opened");
										condtnBlock.put("id", getFeedDataId());
										cbt.updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);

										List<TableColumes> tableColumn = new ArrayList<TableColumes>();
										List<String> columnName = new ArrayList<String>();

										// columnName.add("id");
										columnName.add("feedDataId");
										columnName.add("actionName");
										columnName.add("actionDate");
										columnName.add("actionTime");
										columnName.add("comments");
										columnName.add("user");
										for (String cloumn : columnName)
										{
											TableColumes ob1 = new TableColumes();
											ob1.setColumnname(cloumn);
											ob1.setDatatype("varchar(255)");
											ob1.setConstraint("default NULL");
											tableColumn.add(ob1);
										}
										status1 = cbt.createTable22("feedback_actionTaken", tableColumn, connectionSpace);
										if (status1)
										{
											List<InsertDataTable> insertAction = new ArrayList<InsertDataTable>();

											InsertDataTable ob1 = null;

											if (getFeedDataId() != null)
											{
												ob1 = new InsertDataTable();
												ob1.setColName("feedDataId");
												ob1.setDataName(getFeedDataId());
												insertAction.add(ob1);
											}

											ob1 = new InsertDataTable();
											ob1.setColName("actionName");
											ob1.setDataName("Open Ticket");
											insertAction.add(ob1);

											ob1 = new InsertDataTable();
											ob1.setColName("actionDate");
											ob1.setDataName(DateUtil.getCurrentDateUSFormat());
											insertAction.add(ob1);

											ob1 = new InsertDataTable();
											ob1.setColName("actionTime");
											ob1.setDataName(DateUtil.getCurrentTime());
											insertAction.add(ob1);

											ob1 = new InsertDataTable();
											ob1.setColName("comments");
											ob1.setDataName("");
											insertAction.add(ob1);

											ob1 = new InsertDataTable();
											ob1.setColName("user");
											ob1.setDataName(userName);
											insertAction.add(ob1);

											cbt.insertIntoTable("feedback_actionTaken", insertAction, connectionSpace);
										}
									}
								}

								if (getFeedDataId() != null)
								{
									try
									{
										int feed_statId = 0;
										List dataId = new createTable().executeAllSelectQuery("select feed_stat_id from feedback_ticket where feed_data_id='" + getFeedDataId() + "'", connectionSpace);
										if (dataId != null && dataId.size() > 0)
										{
											for (Iterator iterator = dataId.iterator(); iterator.hasNext();)
											{
												Object object = (Object) iterator.next();
												if (object != null)
												{
													feed_statId = Integer.parseInt(object.toString());
												}
											}
										}

										if (feed_statId != 0)
										{
											Map<String, Object> wherClause = new HashMap<String, Object>();
											Map<String, Object> condtnBlock = new HashMap<String, Object>();
											wherClause.put("status", "Re-Assign");
											condtnBlock.put("id", feed_statId);
											boolean updateFlag = new createTable().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
											if (updateFlag && getFeedTicketId() != null && !(feedTicketId.equalsIgnoreCase("NA")))
											{
												int id2 = cbt.getMaxId("feedback_status", connectionSpace);

												wherClause.clear();
												wherClause.put("dept_feed_stat_id", id2);

												condtnBlock.clear();
												condtnBlock.put("id", getFeedTicketId());

												updateFlag = cbt.updateTableColomn("feedback_ticket", wherClause, condtnBlock, connectionSpace);
												addActionMessage("Feedback Lodge Successfully!!!");
												returnResult = SUCCESS;
											}
										}
									}
									catch (Exception e)
									{
										e.printStackTrace();
										returnResult = ERROR;
									}
								}
							}
							else
							{
								addActionMessage("Feedback Not Lodge Successfully!!!");
								returnResult = "unsuccess";
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String registerFeedbackViaDeptForFeedbackPcc()
	{
		System.out.println("Client Id is as " + getClientId() + "<><><><>" + getCompType());
		String returnResult = SUCCESS;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();

				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();

				String accountId = (String) session.get("accountid");
				String openBy = "NA";
				String openByMailId = "NA";
				String openByMobNo = "NA";
				String doctName = "NA";
				String mode = "", room_bed_no = "", Address_Date_Time = "NA";
				if (getClientId() != null)
				{
					com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo pojo = null;
					List dataList = new LodgeFeedbackHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								if (object[0] != null)
								{
									openBy = object[0].toString();
								}
								if (object[1] != null)
								{
									openByMobNo = object[1].toString();
								}
								if (object[2] != null)
								{
									openByMailId = object[2].toString();
								}
							}
						}
						List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name",
								"feedback_lodge_configuration");
						if (colName != null && colName.size() > 0)
						{
							List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
							for (GridDataPropertyView tableColumes : colName)
							{
								if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category")
										&& !tableColumes.getColomnName().equals("resolutionTime"))
								{
									TableColumes ob1 = new TableColumes();
									ob1.setColumnname(tableColumes.getColomnName());
									ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
									ob1.setConstraint("default NULL");
									TableColumnName.add(ob1);
								}
							}

							// Method calling for creating table on the basis of
							// above column names
							cbt.createTable22("feedback_status", TableColumnName, connectionSpace);

							// Object creation for Feedback Pojo Bean
							FeedbackPojo fbp = null;
							// Local variables declaration
							@SuppressWarnings("unused")
							String res_time = "", adrr_time = "", tolevel = "", esc_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", needesc = "";

							// Code for getting the Escalation Date and
							// Escalation Time
							List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
							if (subCategoryList != null && subCategoryList.size() > 0)
							{
								for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
								{
									Object[] objectCol = (Object[]) iterator.next();
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

							String[] adddate_time = null;
							WorkingHourAction WHA=new WorkingHourAction();
							String date=DateUtil.getCurrentDateUSFormat();
							String time=DateUtil.getCurrentTimeHourMin();
							if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
							{

								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
								Address_Date_Time = dateTime.get(0)+" "+dateTime.get(1);
								escalation_date = dateTime.get(2);
								escalation_time = dateTime.get(3);
								System.out.println(dateTime.get(0)+" "+dateTime.get(1)+" "+dateTime.get(2)+" "+dateTime.get(3));
							
							}
							else
							{}
							// Block for setting the Allotment Id/Feedback By
							// Name/Feedback By Mobile No/ Feedback By email Id
							// and ToDept/ByDept OR To Sub Dept/By Sub dept in
							// the case of Via Call feedback Lodging
							// Block for setting the Allotment Id/Feedback By
							// Name/Feedback By Mobile No/ Feedback By email Id
							// and ToDept/ByDept OR To Sub Dept/By Sub dept in
							// the case of Online feedback Lodging
							if (viaFrom != null && viaFrom.equals("online"))
							{
								StringBuffer buffer = new StringBuffer("select mode,centerCode from feedbackdata where id='" + getFeedDataId() + "'");
								List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
								if (data != null & data.size() > 0)
								{
									for (Iterator iterator = data.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null)
										{
											mode = object[0].toString();
										}
										if (object[1] != null && !object[1].toString().equals(""))
										{
											room_bed_no = object[1].toString();
										}
										else
										{
											room_bed_no = "NA";
										}
									}
								}

								// System.out.println("Inside online block");
								List empData = new LodgeFeedbackHelper().getEmpWithoutRoasterForFeedback(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
								if (empData != null && empData.size() > 0)
								{
									for (Iterator iterator = empData.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null && !object[0].toString().equals(""))
										{
											feedby = object[0].toString();
										}
										else
										{
											feedby = "NA";
										}

										if (object[1] != null && !object[1].toString().equals(""))
										{
											feedbymob = object[1].toString();
										}
										else
										{
											feedbymob = "NA";
										}

										if (object[2] != null && !object[2].toString().equals(""))
										{
											feedbyemailid = object[2].toString();
										}
										else
										{
											feedbyemailid = "NA";
										}

										if (object[3] != null && !object[3].toString().equals(""))
										{
											by_dept_subdept = object[3].toString();
										}
										else
										{
											by_dept_subdept = "-1";
										}
									}
								}

								if (deptLevel != null && deptLevel.equals("2"))
								{
									to_dept_subdept = subdept;
								}
								else if (deptLevel != null && deptLevel.equals("1"))
								{
									to_dept_subdept = todept;
								}
								// New Ticket No code starts
								// String deptid = new
								// HelpdeskUniversalAction().getData("subdepartment",
								// "deptid", "id", to_dept_subdept);
								ticketno = new FeedbackUniversalHelper().getTicketNo(subdept, "FM", connectionSpace);
								// ticketno = new
								// LodgeFeedbackHelper().getTicketNo(subdept,
								// connectionSpace);
								// New Ticket No Code Ends
								// if(getFeedTicketId()!=null)
								{
									ticketno = "PCC" + ticketno;
								}
								List allotto = null;
								List allotto1 = null;
								List<String> one = new ArrayList<String>();
								List<String> two = new ArrayList<String>();

								String toDeptId = subdept;
								List<String> two_new = new ArrayList<String>();
								if (toDeptId != null)
								{
									// allottoId = String.valueOf(new
									// LodgeFeedbackHelper().getRandomEmp4EscalationFeedback(toDeptId,deptLevel,
									// connectionSpace,"1").get(0));
									try
									{
										// allotto =
										// HUA.getRandomEmp4Escalation(toDeptId,"FM",tolevel.substring(5),"","",connectionSpace);

										// Worked to show details of Patient in
										// Ticket
										if (getClientId() != null)
										{
											pojo = new LodgeFeedbackHelper().getPatientDetailsByPatId(getClientId(), "IPD", connectionSpace);
										}
										boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);
										System.out.println("For Bed Mapping >>>>>" + bedMapping);
										if (bedMapping && toDeptId != null && !toDeptId.equals(""))
										{
											String floor_status = FUH.getFloorStatus(toDeptId, connectionSpace);
											System.out.println("IFFFFF" + floor_status);
											if (floor_status != null && floor_status.equalsIgnoreCase("B") && pojo.getCentreCode() != null && !pojo.getCentreCode().equalsIgnoreCase(""))
											{
												System.out.println("Flooer Stasus" + floor_status);
												allotto = FUH.getRandomEmp4BedSpecific(toDeptId, pojo.getCentreCode(), tolevel.substring(5), connectionSpace);
											}
											else
											{
												System.out.println("Esle of Floor Status");
												allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connectionSpace);
											}
										}
										else
										{
											System.out.println("ELSEEEEEE");
											allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connectionSpace);
										}

										if (allotto == null || allotto.size() == 0)
										{
											String newToLevel = "";
											newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
											// System.out.println("New To Level inside First If  "+newToLevel);
											allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
											if (allotto == null || allotto.size() == 0)
											{
												newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
												// System.out.println("New To Level inside Second If  "+newToLevel);
												allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
												if (allotto == null || allotto.size() == 0)
												{
													newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
													// System.out.println("New To Level inside Third If  "+newToLevel);
													allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connectionSpace);
												}
											}
										}
										allotto1 = FUH.getRandomEmployee(toDeptId, deptLevel, tolevel.substring(5), allotto, "FM", connectionSpace);

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
										// System.out.println("List One  "+one.toString());
										// System.out.println("List Two  "+two.toString());

										if (one != null && one.size() > two.size())
										{
											// System.out.println("Inside If Block");
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
											List pending_alloted = FUA.getPendingAllotto(toDeptId, "FM");
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
							}
							boolean status1 = false;
							if (allottoId != null && !(allottoId.equalsIgnoreCase("")) && pojo.getCrNo() != null && !pojo.getCrNo().equalsIgnoreCase("NA") && pojo.getName() != null
									&& !pojo.getName().equalsIgnoreCase("NA"))
							{
								// Setting the column values after getting from
								// the page
								InsertDataTable ob = new InsertDataTable();
								List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
								ob.setColName("ticket_no");
								ob.setDataName(ticketno);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_by_mobno");
								ob.setDataName(openByMobNo);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_by_emailid");
								ob.setDataName(openByMailId);
								insertData.add(ob);

								if (pojo != null)
								{
									ob = new InsertDataTable();
									ob.setColName("feed_by");
									ob.setDataName(pojo.getName());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("location");
									ob.setDataName(pojo.getCentreCode());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("clientId");
									ob.setDataName(pojo.getCrNo());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("patientId");
									ob.setDataName(pojo.getPatId());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("patMobNo");
									ob.setDataName(pojo.getMobileNo());
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("patEmailId");
									ob.setDataName(pojo.getEmailId());
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName("feed_by");
									ob.setDataName(openBy);
									insertData.add(ob);
								}

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

								if (pojo != null)
								{
									ob = new InsertDataTable();
									ob.setColName("feed_brief");
									ob.setDataName(getFeed_brief());
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName("feed_brief");
									ob.setDataName(feed_brief);
									insertData.add(ob);
								}

								ob = new InsertDataTable();
								ob.setColName("to_dept_subdept");
								ob.setDataName(to_dept_subdept);
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
								ob.setColName("Addr_date_time");
								ob.setDataName(Address_Date_Time);
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
								ob.setDataName(getCompType());
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("feed_registerby");
								ob.setDataName(userName);
								insertData.add(ob);

								/*
								 * ob=new InsertDataTable();
								 * ob.setColName("feedback_remarks");
								 * ob.setDataName(pojo.getRemarks());
								 * insertData.add(ob);
								 */

								ob = new InsertDataTable();
								ob.setColName("moduleName");
								ob.setDataName("FM");
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("non_working_time");
								ob.setDataName(non_working_timing);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("stage");
								ob.setDataName("2");
								insertData.add(ob);

								// Method calling for inserting the values in
								// the feedback_status table
								status1 = cbt.insertIntoTable("feedback_status", insertData, connectionSpace);
								insertData.clear();

								// Block for sending SMS and Mail to the
								// Engineer and Complainant after inserting the
								// data in the feedback status table
								// successfully
								if (status1)
								{

									// List for holding the data of currently
									// lodged
									List data = new FeedbackUniversalHelper().getFeedbackDetail(ticketno, deptLevel, "Pending", "", connectionSpace);
									fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, "Pending", deptLevel);
									setTodept(fbp.getFeedback_to_dept());
									printTicket(fbp);
									if (fbp != null)
									{

										String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);

										// Block for sending sms for Level1
										// Engineer
										boolean mailFlag = false;
										if (needesc.equalsIgnoreCase("Y") && fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10
												&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
										{
											// Open Alert: Ticket No: <Ticket
											// No.>, Open By: <PCC Name> for
											// <Patient Name>, <IP No.>,
											// Location: 2273-ESP, Feedback:
											// <Brief (120)>, To be closed by:
											// <Date & Time>.
											// Open Alert: ticket no
											// :<01001it1002>, Open by:<deeksha>
											// for <patient_name>, Location:
											// <Room_no> , feedback
											// :<Observation>, To be closed by
											// :<13-12-2013,16:50>
											String levelMsg = "Open Alert: Ticket No: " + fbp.getTicket_no() + ", Open By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + " for "
													+ DateUtil.makeTitle(fbp.getFeed_by()) + "," + fbp.getCr_no() + ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg()
													+ ", To be closed by :" + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time();
											// String levelMsg =
											// "Open Alert: Ticket No: "+fbp.getTicket_no()+", To Be Closed By: "+fbp.getEscalation_date()+","+fbp.getEscalation_time()+
											// ", Reg. By: "+
											// DateUtil.makeTitle(fbp.getFeed_registerby())+", Location: "+fbp.getLocation()+" , Brief: "+
											// fbp.getFeed_brief()+ ".";
											communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "FM");
										}
										// Block for sending sms for Complainant
										if (needesc.equalsIgnoreCase("Y") && getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != ""
												&& fbp.getFeedback_by_mobno().trim().length() == 10
												&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
										{
											// Dear <Deeksha Nangia>,Ticket No:
											// <O1000EN1002>, for <PatientName>,
											// Location: <Room_no> , feedback
											// :<Observation>, Resolution Time:
											// <14-12-2013,01:48>,Status is :<
											// open>
											// String complainMsg = "Dear "+
											// DateUtil.makeTitle(fbp.getFeed_registerby())
											// + ","+ " Ticket No: "
											// +fbp.getTicket_no()+ ", for ""+",
											// Addressing Time:
											// "+DateUtil.getCurrentDateIndianFormat()+","+DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5)+ ",
											// Location: "+fbp.getLocation()+",
											// Brief: "+fbp.getFeed_brief() + "
											// is open.";
											String complainMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + "," + " Ticket No: " + fbp.getTicket_no() + ", for " + fbp.getFeed_by()
													+ ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg() + ", Resolution Time: " + DateUtil.getCurrentDateIndianFormat()
													+ "," + DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5) + " ,Status is : Open";
											communication.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainMsg, ticketno, "Pending", "0", "FM");
										}
										// Block for creating mail for Level1
										// Engineer
										if (needesc.equalsIgnoreCase("Y") && fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
										{
											mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true, orgName);
											communication.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketno,
													"Pending", "0", null, "FM");
										}
										// Block for creating mail for
										// complainant
										if (needesc.equalsIgnoreCase("Y") && getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("")
												&& !fbp.getFeedback_by_emailid().equals("NA"))
										{
											mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, false, orgName);
											// new
											// MsgMailCommunication().sendMail(fbp.getFeedback_by_emailid(),
											// "Open Feedback Alert", mailText,
											// ticketno,"Pending",
											// "0",fbp.getFeedback_allot_to());
											communication.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), "Open Feedback Alert", mailText.toString(),
													ticketno, "Pending", "0", null, "FM");
										}

										int feedId = cbt.getMaxId("feedback_status", connectionSpace);
										// System.out.println("getFeedDataId() is as >>>>>>>>>>>>>>>>>>>>>>"+getFeedDataId());
										if (getFeedDataId() != null)
										{
											Map<String, Object> wherClause = new HashMap<String, Object>();
											Map<String, Object> condtnBlock = new HashMap<String, Object>();
											CommonOperInterface cbt = new CommonConFactory().createInterface();
											wherClause.put("status", "Ticket Opened");
											condtnBlock.put("id", getFeedDataId());
											cbt.updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);

											List<TableColumes> tableColumn = new ArrayList<TableColumes>();
											List<String> columnName = new ArrayList<String>();

											// columnName.add("id");
											columnName.add("feedDataId");
											columnName.add("actionName");
											columnName.add("actionDate");
											columnName.add("actionTime");
											columnName.add("comments");
											columnName.add("user");
											for (String cloumn : columnName)
											{
												TableColumes ob1 = new TableColumes();
												ob1.setColumnname(cloumn);
												ob1.setDatatype("varchar(255)");
												ob1.setConstraint("default NULL");
												tableColumn.add(ob1);
											}
											status1 = cbt.createTable22("feedback_actionTaken", tableColumn, connectionSpace);
											if (status1)
											{
												List<InsertDataTable> insertAction = new ArrayList<InsertDataTable>();

												InsertDataTable ob1 = null;
												if (getFeedDataId() != null)
												{
													ob1 = new InsertDataTable();
													ob1.setColName("feedDataId");
													ob1.setDataName(getFeedDataId());
													insertAction.add(ob1);
												}

												ob1 = new InsertDataTable();
												ob1.setColName("actionName");
												ob1.setDataName("Open Ticket");
												insertAction.add(ob1);

												ob1 = new InsertDataTable();
												ob1.setColName("actionDate");
												ob1.setDataName(DateUtil.getCurrentDateUSFormat());
												insertAction.add(ob1);

												ob1 = new InsertDataTable();
												ob1.setColName("actionTime");
												ob1.setDataName(DateUtil.getCurrentTime());
												insertAction.add(ob1);

												ob1 = new InsertDataTable();
												ob1.setColName("comments");
												ob1.setDataName("");
												insertAction.add(ob1);

												ob1 = new InsertDataTable();
												ob1.setColName("user");
												ob1.setDataName(userName);
												insertAction.add(ob1);

												cbt.insertIntoTable("feedback_actionTaken", insertAction, connectionSpace);
											}
										}
									}

									if (getFeedDataId() != null)
									{
										try
										{
											int feed_statId = 0;
											List dataId = new createTable().executeAllSelectQuery("select feed_stat_id from feedback_ticket where feed_data_id='" + getFeedDataId() + "'",
													connectionSpace);
											if (dataId != null && dataId.size() > 0)
											{
												for (Iterator iterator = dataId.iterator(); iterator.hasNext();)
												{
													Object object = (Object) iterator.next();
													if (object != null)
													{
														feed_statId = Integer.parseInt(object.toString());
													}
												}
											}
											if (feed_statId != 0)
											{
												Map<String, Object> wherClause = new HashMap<String, Object>();
												Map<String, Object> condtnBlock = new HashMap<String, Object>();
												wherClause.put("status", "Re-Assign");
												condtnBlock.put("id", feed_statId);
												boolean updateFlag = new createTable().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
												if (updateFlag && getFeedTicketId() != null && !(feedTicketId.equalsIgnoreCase("NA")))
												{
													int id2 = cbt.getMaxId("feedback_status", connectionSpace);
													wherClause.clear();
													wherClause.put("dept_feed_stat_id", id2);
													condtnBlock.clear();
													condtnBlock.put("id", getFeedTicketId());
													updateFlag = cbt.updateTableColomn("feedback_ticket", wherClause, condtnBlock, connectionSpace);
													addActionMessage("Feedback Lodge Successfully!!!");
													returnResult = SUCCESS;
												}
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
											returnResult = ERROR;
										}
									}
								}
							}
							else
							{
								addActionMessage("Feedback Not Lodge Successfully!!!");
								returnResult = "unsuccess";
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
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
						// System.out.println("New Working Time data  "+workingTimingData);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							startTime = workingTimingData.get(1).toString();
							// System.out.println("New Start time  "+startTime);
							String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
							// System.out.println("New Date time  "+new_date_time);
							// System.out.println("main Differnce   "+main_difference);
							date_time = DateUtil.newdate_AfterTime(new_date_time, main_difference);
							// System.out.println("Updated date time Date time  "+date_time);
							String level_difference = DateUtil.getTimeDifference(main_difference, res_time);
							// System.out.println("Difference at level   "+level_difference);
							level_date_time = DateUtil.newdate_AfterTime(new_date_time, level_difference);
							// System.out.println("Level Date Time    "+level_date_time);
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							// System.out.println("Final non working time  "+non_working_timing);
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
							// System.out.println("Start Time Value  "+startTime);
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

	public void printTicket(FeedbackPojo data)
	{

		Map session = ActionContext.getContext().getSession();
		String orgDetail = (String) session.get("orgDetail");
		String[] orgData = null;
		String orgName = "", address = "", city = "", pincode = "";
		if (orgDetail != null && !orgDetail.equals(""))
		{
			orgData = orgDetail.split("#");
			orgName = orgData[0];
			address = DateUtil.makeTitle(orgData[1]);
			city = DateUtil.makeTitle(orgData[2]);
			pincode = orgData[3];
		}
		if (data != null)
		{
			System.out.println("" + data.getAddr_Date_Time());
			setAddr_Date_Time(DateUtil.convertDateToIndianFormat(data.getAddr_Date_Time().substring(0, 10)) + " " + data.getAddr_Date_Time().substring(11, 16));

			setTicket_no(data.getTicket_no());
			setFeedbackBy(DateUtil.makeTitle(data.getFeed_registerby()));
			setMobileno(data.getFeedback_by_mobno());
			setTodept(data.getFeedback_to_dept());
			setTosubdept(data.getFeedback_to_subdept());
			setAllotto(data.getFeedback_allot_to());
			setCatg(data.getFeedback_catg());
			setSubCatg(data.getFeedback_subcatg());
			setFeed_brief(data.getFeed_brief());
			setLocation(data.getLocation());
			setOpen_date(DateUtil.convertDateToIndianFormat(data.getOpen_date()));
			setOpen_time(data.getOpen_time());
			setEscTime(DateUtil.convertDateToIndianFormat(data.getEscalation_date()) + " " + data.getEscalation_time());
			setAllot_to_mobno(data.getMobOne());
			setOrgName(orgName);
			setAddress(address);
			setCity(city);
			setPincode(pincode);
			if (data.getOpen_date() != null && !data.getOpen_date().equals("") && data.getOpen_time() != null && !data.getOpen_time().equals("") && data.getFeedaddressing_time() != null
					&& !data.getFeedaddressing_time().equals(""))
			{
				String addr_date_time = DateUtil.newdate_AfterTime(data.getOpen_date(), data.getOpen_time(), data.getFeedaddressing_time());
				String[] add_date_time = addr_date_time.split("#");
				for (int i = 0; i < add_date_time.length; i++)
				{
					setAddrDate(DateUtil.convertDateToIndianFormat(add_date_time[0]));
					setAddrTime(add_date_time[1]);
				}
			}
			else
			{
				setAddrDate("NA");
				setAddrTime("NA");
			}
		}
	}

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{

		// System.out.println("Inside Important method");
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;

		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_day = DateUtil.getDayofDate(working_date);
		// System.out.println("At First working date  "+working_date);
		// System.out.println("At First working day  "+working_day);
		// System.out.println("Current Date   "+DateUtil.getCurrentDateUSFormat());
		// System.out.println("Current Date   "+DateUtil.getCurrentTime());
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();

			// System.out.println("Working Date  "+working_date);
			// System.out.println("Start Time  "+startTime);
			// System.out.println("End Time  "+endTime);
			// Here we know the complaint lodging time is inside the the start
			// and end time or not
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			// System.out.println("Call Flag Value is "+callflag);
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{
				// System.out.println("Inside before");

				// date_time = DateUtil.newdate_AfterEscInRoaster(date,
				// startTime, adrr_time, res_time);
				// new_escalation_datetime = DateUtil.newdate_AfterEscTime(date,
				// startTime, esc_duration, needesc);
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				// System.out.println("After Calculation First escalation Date time is  "+new_escalation_datetime);
				// (Differrence between current date/time and date & starttime)
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					// System.out.println("Inside If when flag is true");

					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("Non Working time inside if  "+non_working_timing);
				}
				else
				{
					// System.out.println("Inside Else when flag is false");

					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("First Non Working time  "+non_working_timing);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					// System.out.println("Final After calculation Second Non Working time  "+non_working_timing);
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					// System.out.println("First diffrence "+timeDiff1);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					// System.out.println("Second diffrence "+timeDiff2);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					// System.out.println("Main diffrence "+main_difference);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id,
							connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						// System.out.println("Inside Second If   "+workingTimingData.toString());
						startTime = workingTimingData.get(1).toString();
						// System.out.println("Final Start Time :::::::::: "+startTime);
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						// System.out.println("Final Date ::::::::: "+final_date);
						// System.out.println("Final Left time :::::::: "+left_time);
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
						// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
					}
				}
			}
			else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{
				// System.out.println("Inside Middle");

				// Escalation date time at the time of opening the ticket
				// System.out.println("Addressing Time :::: "+adrr_time);
				// System.out.println("Resolution Time :::: "+res_time);

				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				// System.out.println("New Escalation Date & Time  "+new_escalation_datetime);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				}
				else
				{
					// System.out.println("Inside Else when flag is false");
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					// System.out.println("Final After calculation Second Non Working time  "+non_working_timing);
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					// System.out.println("First diffrence "+timeDiff1);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					// System.out.println("Second diffrence "+timeDiff2);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					// System.out.println("Main diffrence "+main_difference);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id,
							connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						// System.out.println("Inside Second If   "+workingTimingData.toString());
						startTime = workingTimingData.get(1).toString();
						// System.out.println("Final Start Time :::::::::: "+startTime);
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						// System.out.println("Final Date ::::::::: "+final_date);
						// System.out.println("Final Left time :::::::: "+left_time);
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// System.out.println("At last the escalation Date and time   "+new_escalation_datetime);
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
						// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
					}
				}
			}
			else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				// System.out.println("Inside After");

				// System.out.println("Inside After");

				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				// System.out.println("Main Difference in After  "+main_difference);
				workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					// System.out.println("Inside Second If   "+workingTimingData.toString());
					startTime = workingTimingData.get(1).toString();
					// System.out.println("Final Start Time :::::::::: "+startTime);
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					// System.out.println("Final Date ::::::::: "+final_date);
					// System.out.println("Final Left time :::::::: "+left_time);
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					// new_escalation_datetime =
					// DateUtil.newdate_AfterEscInRoaster(final_date, startTime,
					// adrr_time, res_time);
					non_working_timing = workingTimingData.get(3).toString();
					// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
				}
			}
		}

		// System.out.println("Final   ::::   Escalation Date  "+new_escalation_datetime);
		// System.out.println("Final Non Working Timing  "+non_working_timing);

	}

	/*
	 * public void getNextEscalationDateTime(String adrr_time, String
	 * res_time,String todept, String module,SessionFactory connectionSpace) {
	 * //System.out.println("Inside Important method"); String
	 * startTime="",endTime="",callflag=""; String dept_id = todept; String
	 * working_date = DateUtil.getCurrentDateUSFormat(); String working_day=
	 * DateUtil.getCurrentDayofWeek();
	 * 
	 * 
	 * String working_date = DateUtil.convertDateToUSFormat(new
	 * EscalationHelper(
	 * ).getNextOrPreviousWorkingDate("N",DateUtil.getCurrentDateIndianFormat
	 * (),connectionSpace)); String working_day=
	 * DateUtil.getDayofDate(working_date);
	 * System.out.println("At First working date  "+working_date);
	 * System.out.println("At First working day  "+working_day);
	 * System.out.println("Current Date   "+DateUtil.getCurrentDateUSFormat());
	 * System.out.println("Current Date   "+DateUtil.getCurrentTime()); //List
	 * of working timing data List workingTimingData= new
	 * EscalationHelper().getWorkingTimeData
	 * (module,working_day,dept_id,connectionSpace); if (workingTimingData!=null
	 * && workingTimingData.size()>0) {
	 * startTime=workingTimingData.get(1).toString();
	 * endTime=workingTimingData.get(2).toString();
	 * 
	 * 
	 * System.out.println("1111 Working Date  "+working_date);
	 * System.out.println(" 1111 Start Time  "+startTime);
	 * System.out.println(" 11111End Time  "+endTime); //Here we know the
	 * complaint lodging time is inside the the start and end time or not
	 * callflag=DateUtil.timeInBetween(working_date,startTime, endTime,
	 * DateUtil.getCurrentTime());
	 * System.out.println("Call Flag Value is "+callflag); if (callflag!=null &&
	 * !callflag.equals("") && callflag.equalsIgnoreCase("before")) {
	 * System.out.println("Inside before");
	 * 
	 * // date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime,
	 * adrr_time, res_time); // new_escalation_datetime =
	 * DateUtil.newdate_AfterEscTime(date, startTime, esc_duration, needesc);
	 * new_escalation_datetime =
	 * DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time,
	 * res_time);
	 * //System.out.println("After Calculation First escalation Date time is  "
	 * +new_escalation_datetime); //(Differrence between current date/time and
	 * date & starttime) String newdatetime[]=
	 * new_escalation_datetime.split("#"); // Check the date time is lying
	 * inside the time block boolean flag=
	 * DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1],
	 * working_date, endTime); if (flag) {
	 * System.out.println("Inside If when flag is true");
	 * 
	 * 
	 * non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat
	 * (), DateUtil.getCurrentTime(), working_date, startTime);
	 * //System.out.println("Non Working time inside if  "+non_working_timing);
	 * } else { System.out.println("Inside Else when flag is false");
	 * 
	 * System.out.println("Working Date   333232   "+working_date);
	 * non_working_timing
	 * =DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(), working_date, startTime);
	 * System.out.println("First Non Working time  "+non_working_timing);
	 * non_working_timing=DateUtil.addTwoTime(non_working_timing,
	 * DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
	 * System.out.println("Final After calculation Second Non Working time  "+
	 * non_working_timing); String
	 * timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	 * startTime, newdatetime[0], newdatetime[1]);
	 * System.out.println("First diffrence "+timeDiff1); String
	 * timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	 * startTime, working_date, endTime);
	 * System.out.println("Second diffrence "+timeDiff2); String main_difference
	 * = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
	 * System.out.println("Main diffrence "+main_difference);
	 * workingTimingData.clear();
	 * 
	 * 
	 * // workingTimingData= new
	 * EscalationHelper().getNextWorkingTimeData(module
	 * ,DateUtil.getNextDateFromDate
	 * (DateUtil.convertDateToIndianFormat(working_date
	 * ),1),non_working_timing,main_difference,dept_id,connectionSpace);
	 * workingTimingData= new
	 * EscalationHelper().getNextWorkingTimeData(module,DateUtil
	 * .getNextDateFromDate
	 * (DateUtil.convertDateToIndianFormat(working_date),1),non_working_timing
	 * ,main_difference,dept_id,connectionSpace); if (workingTimingData!=null &&
	 * workingTimingData.size()>0) {
	 * //System.out.println("Inside Second If   "+workingTimingData.toString());
	 * startTime=workingTimingData.get(1).toString();
	 * //System.out.println("Final Start Time :::::::::: "+startTime); String
	 * left_time=workingTimingData.get(4).toString(); String
	 * final_date=workingTimingData.get(5).toString();
	 * //System.out.println("Final Date ::::::::: "+final_date);
	 * //System.out.println("Final Left time :::::::: "+left_time);
	 * new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date,
	 * startTime, left_time, "Y"); // new_escalation_datetime =
	 * DateUtil.newdate_AfterEscInRoaster(final_date, startTime, adrr_time,
	 * res_time); non_working_timing=workingTimingData.get(3).toString();
	 * //System.out.println("Non Non 222  :::::::::  "+non_working_timing); } }
	 * } else if (callflag!=null && !callflag.equals("") &&
	 * callflag.equalsIgnoreCase("middle")) {
	 * System.out.println("Inside Middle");
	 * 
	 * //Escalation date time at the time of opening the ticket //date_time =
	 * DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(), adrr_time, res_time); new_escalation_datetime
	 * = DateUtil.newdate_AfterEscInRoaster(working_date,
	 * DateUtil.getCurrentTime(), adrr_time, res_time);
	 * //System.out.println("New Escalation Date & Time  "
	 * +new_escalation_datetime); String newdatetime[]=
	 * new_escalation_datetime.split("#"); // Check the date time is lying
	 * inside the time block boolean flag=
	 * DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1],
	 * working_date, endTime); if (flag) { non_working_timing="00:00"; } else {
	 * System.out.println("Inside Else when flag is false");
	 * System.out.println("hjugyhyh   "+working_date);
	 * //non_working_timing=DateUtil
	 * .time_difference(DateUtil.getCurrentDateUSFormat(), endTime,
	 * working_date, "24:00");
	 * //System.out.println("First Non Working time  "+non_working_timing);
	 * non_working_timing=DateUtil.addTwoTime(non_working_timing,
	 * DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
	 * //System.out.println("Final After calculation Second Non Working time  "+
	 * non_working_timing); String
	 * timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
	 * //System.out.println("First diffrence "+timeDiff1); String
	 * timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(), working_date, endTime);
	 * //System.out.println("Second diffrence "+timeDiff2); String
	 * main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
	 * //System.out.println("Main diffrence "+main_difference);
	 * workingTimingData.clear();
	 * 
	 * 
	 * workingTimingData= new
	 * EscalationHelper().getNextWorkingTimeData(module,DateUtil
	 * .getNextDateFromDate
	 * (DateUtil.convertDateToIndianFormat(working_date),1),non_working_timing
	 * ,main_difference,dept_id,connectionSpace); if (workingTimingData!=null &&
	 * workingTimingData.size()>0) { //
	 * System.out.println("Inside Second If   "+workingTimingData.toString());
	 * startTime=workingTimingData.get(1).toString();
	 * //System.out.println("Final Start Time :::::::::: "+startTime); String
	 * left_time=workingTimingData.get(4).toString(); String
	 * final_date=workingTimingData.get(5).toString();
	 * //System.out.println("Final Date ::::::::: "+final_date);
	 * //System.out.println("Final Left time :::::::: "+left_time);
	 * new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date,
	 * startTime, left_time, "Y"); // new_escalation_datetime =
	 * DateUtil.newdate_AfterEscInRoaster(final_date, startTime, adrr_time,
	 * res_time); non_working_timing=workingTimingData.get(3).toString();
	 * //System.out.println("Non Non 222  :::::::::  "+non_working_timing); } }
	 * } else if (callflag!=null && !callflag.equals("") &&
	 * callflag.equalsIgnoreCase("after")) {
	 * //System.out.println("Inside After");
	 * 
	 * //System.out.println("Inside After");
	 * 
	 * non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat
	 * (), DateUtil.getCurrentTime(), working_date, "24:00");
	 * 
	 * workingTimingData.clear(); String
	 * main_difference=DateUtil.addTwoTime(adrr_time, res_time);
	 * //System.out.println("Main Difference in After  "+main_difference);
	 * workingTimingData= new
	 * EscalationHelper().getNextWorkingTimeData(module,DateUtil
	 * .getNextDateFromDate
	 * (DateUtil.convertDateToIndianFormat(working_date),1),non_working_timing
	 * ,main_difference,dept_id,connectionSpace); if (workingTimingData!=null &&
	 * workingTimingData.size()>0) {
	 * //System.out.println("Inside Second If   "+workingTimingData.toString());
	 * startTime=workingTimingData.get(1).toString();
	 * //System.out.println("Final Start Time :::::::::: "+startTime); String
	 * left_time=workingTimingData.get(4).toString(); String
	 * final_date=workingTimingData.get(5).toString();
	 * //System.out.println("Final Date ::::::::: "+final_date);
	 * //System.out.println("Final Left time :::::::: "+left_time);
	 * new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date,
	 * startTime, left_time, "Y"); // new_escalation_datetime =
	 * DateUtil.newdate_AfterEscInRoaster(final_date, startTime, adrr_time,
	 * res_time); non_working_timing=workingTimingData.get(3).toString();
	 * //System.out.println("Non Non 222  :::::::::  "+non_working_timing); } }
	 * } }
	 */

	public static void main(String[] args)
	{
		SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		// String date =
		// getNextOrPreviousWorkingDate("N","2014-06-21",connection);

		new LodgeFeedbackForFeedback().getNextEscalationDateTime("16:00", "08:00", "10", "FM", connection);
		System.out.println("" + new LodgeFeedbackForFeedback().new_escalation_datetime);
	}

	public String getFeedDataId()
	{
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId)
	{
		this.feedDataId = feedDataId;
	}

	public String getFeedTicketId()
	{
		return feedTicketId;
	}

	public void setFeedTicketId(String feedTicketId)
	{
		this.feedTicketId = feedTicketId;
	}

	public String getSubCategory()
	{
		return subCategory;
	}

	public void setSubCategory(String subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getViaFrom()
	{
		return viaFrom;
	}

	public void setViaFrom(String viaFrom)
	{
		this.viaFrom = viaFrom;
	}

	public String getFeedbackBy()
	{
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy)
	{
		this.feedbackBy = feedbackBy;
	}

	public String getMobileno()
	{
		return mobileno;
	}

	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getTosubdept()
	{
		return tosubdept;
	}

	public void setTosubdept(String tosubdept)
	{
		this.tosubdept = tosubdept;
	}

	public String getBydept_subdept()
	{
		return bydept_subdept;
	}

	public void setBydept_subdept(String bydept_subdept)
	{
		this.bydept_subdept = bydept_subdept;
	}

	public String getBysubdept()
	{
		return bysubdept;
	}

	public void setBysubdept(String bysubdept)
	{
		this.bysubdept = bysubdept;
	}

	public String getTodept()
	{
		return todept;
	}

	public void setTodept(String todept)
	{
		this.todept = todept;
	}

	public String getByDept()
	{
		return byDept;
	}

	public void setByDept(String byDept)
	{
		this.byDept = byDept;
	}

	public String getFeed_brief()
	{
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getRecvSMS()
	{
		return recvSMS;
	}

	public void setRecvSMS(String recvSMS)
	{
		this.recvSMS = recvSMS;
	}

	public String getRecvEmail()
	{
		return recvEmail;
	}

	public void setRecvEmail(String recvEmail)
	{
		this.recvEmail = recvEmail;
	}

	/*
	 * public int getId() { return id; } public void setId(int id) { this.id =
	 * id; }
	 */

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getAllotto()
	{
		return allotto;
	}

	public void setAllotto(String allotto)
	{
		this.allotto = allotto;
	}

	public String getSubCatg()
	{
		return subCatg;
	}

	public void setSubCatg(String subCatg)
	{
		this.subCatg = subCatg;
	}

	public String getEscTime()
	{
		return escTime;
	}

	public void setEscTime(String escTime)
	{
		this.escTime = escTime;
	}

	public String getAllot_to_mobno()
	{
		return allot_to_mobno;
	}

	public void setAllot_to_mobno(String allot_to_mobno)
	{
		this.allot_to_mobno = allot_to_mobno;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPincode()
	{
		return pincode;
	}

	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getSubdept()
	{
		return subdept;
	}

	public void setSubdept(String subdept)
	{
		this.subdept = subdept;
	}

	public String getAddr_Date_Time()
	{
		return addr_Date_Time;
	}

	public void setAddr_Date_Time(String addr_Date_Time)
	{
		this.addr_Date_Time = addr_Date_Time;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getCompType()
	{
		return compType;
	}

	public void setCompType(String compType)
	{
		this.compType = compType;
	}
}