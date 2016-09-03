package com.Over2Cloud.ctrl.feedback.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class ActivityTakeAction
{
	public String feedId;
	public String feedToDept;
	public String subCatId;
	public String clientId;
	private final CommonOperInterface cbt=new CommonConFactory().createInterface();
	private final MsgMailCommunication communication=new MsgMailCommunication();
	private String recvSMS;
	private String recvEmail;
	private com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo fbp = null;
	private String action;
	private String escalation_date = "NA", escalation_time = "NA", resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA", non_working_timing = "00:00",
			new_escalation_datetime = "0000-00-00#00:00";
	
	private String mode;
	private String feed_brief;
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	private String activityFlag;
	private String feedStatId;
	private String comments;
	private String ticket_no;
	
	public String takeActionForActivity()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
		//		System.out.println("Action is as "+getAction()+""+getActivityFlag());
			//	System.out.println("Feedback Status is as "+getFeedStatId());
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String deptLevel = (String) session.get("userDeptLevel");
				String userName = (String) session.get("uName");
				ActivityBoardHelper helper=new ActivityBoardHelper();
				FeedbackUniversalAction FUA=new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH=new FeedbackUniversalHelper();
				HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
				// Updating Feedback Table Status
				
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				//System.out.println("register id=>>>>>>>>>>>>"+getFeedId());
				condtnBlock.put("id",getFeedId());
				
				if(getAction().equalsIgnoreCase("No Further Action Required") || getAction().equalsIgnoreCase("2"))
				{
					if(getActivityFlag().equalsIgnoreCase("1"))
					{
						StringBuilder query=new StringBuilder("update feedback_status_feed_paperRating set status='No Further Action Required',feedback_remarks='"+getComments()+"',stage='2'  where id="+getFeedStatId()+"");
						int i=cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
					}
					else
					{
						wherClause.put("status","No Further Action Required");
						boolean updateFeed=	new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock,connectionSpace);
						
						StringBuilder query=new StringBuilder("update feedback_status set status='No Further Action Required',feedback_remarks='"+getComments()+"',stage='2' where id=(select feed_stat_id from feedback_ticket where feed_data_id='"+getFeedId()+"' limit 1)");
						int i=cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
					}
					return "noFurtherAction";
				}
				else if(getAction().equalsIgnoreCase("Open Ticket") || getAction().equalsIgnoreCase("1"))
				{
					String arr[]=helper.getSubCatDetails(connectionSpace, getSubCatId());
					String adrr_time=arr[0];
					String res_time=arr[1];
					String needesc=arr[3];
					String[] adddate_time = null;
					String Address_Date_Time = "NA";
					FeedbackPojo pojo = new LodgeFeedbackHelper().getPatientDetailsByPatId(getClientId(), "", connectionSpace);
					boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);
					String allottoId=helper.getTicketAllotToId(connectionSpace,getFeedToDept(),bedMapping,pojo,arr[2],deptLevel);
					String ticketNo=helper.getTicketNoForDept(connectionSpace,getTicket_no(), getFeedToDept());
					EmpBasicPojoBean empPojo=helper.getLoggedUserDetails(connectionSpace, userName, deptLevel);
					WorkingHourAction WHA=new WorkingHourAction();
					String date=DateUtil.getCurrentDateUSFormat();
					String time=DateUtil.getCurrentTimeHourMin();
					if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
					{
						List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
						Address_Date_Time = dateTime.get(0)+" "+dateTime.get(1);
						escalation_date = dateTime.get(2);
						escalation_time = dateTime.get(3);
					}
					boolean ticketAdded=false;
					if (allottoId != null && !(allottoId.equalsIgnoreCase("")) && pojo.getCrNo() != null && !pojo.getCrNo().equalsIgnoreCase("NA") && pojo.getName() != null && !pojo.getName().equalsIgnoreCase("NA"))
					{
							// Setting the column values after getting from
							// the page
							InsertDataTable ob = new InsertDataTable();
							List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
							ob.setColName("ticket_no");
							ob.setDataName(ticketNo);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_by_mobno");
							ob.setDataName(empPojo.getMobOne());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_by_emailid");
							ob.setDataName(empPojo.getEmailIdOne());
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

							ob = new InsertDataTable();
							ob.setColName("deptHierarchy");
							ob.setDataName(deptLevel);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("by_dept_subdept");
							ob.setDataName(empPojo.getDeptName());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("subcatg");
							ob.setDataName(getSubCatId());
							insertData.add(ob);

							if(getFeed_brief()!=null && !getFeed_brief().equalsIgnoreCase(""))
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
								ob.setDataName(arr[4]);
								insertData.add(ob);
							}

							ob = new InsertDataTable();
							ob.setColName("to_dept_subdept");
							ob.setDataName(getFeedToDept());
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
							ob.setDataName(getMode());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_registerby");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_by");
							ob.setDataName(userName);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("moduleName");
							ob.setDataName("FM");
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("non_working_time");
							ob.setDataName(non_working_timing);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("feedback_remarks");
							ob.setDataName(getComments());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("stage");
							ob.setDataName("2");
							insertData.add(ob);

							// Method calling for inserting the values in
							// the feedback_status table
							
							ticketAdded = cbt.insertIntoTable("feedback_status", insertData, connectionSpace);
							insertData.clear();
							
							if (ticketAdded)
							{
								List data = new FeedbackUniversalHelper().getFeedbackDetail(ticketNo, deptLevel, "Pending", "", connectionSpace);
								fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, "Pending", deptLevel);
								String mailText="";
						//		setTodept(fbp.getFeedback_to_dept());
								if (fbp != null)
								{
									String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);
									boolean mailFlag = false;
									
									if (needesc.equalsIgnoreCase("Y") && fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10
											&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
									{
										String levelMsg = "Open Alert: Ticket No: " + fbp.getTicket_no() + ", Open By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + " for "
												+ DateUtil.makeTitle(fbp.getFeed_by()) + "," + fbp.getCr_no() + ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg()
												+ ", To be closed by :" + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time();
										communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketNo, "Pending", "0", "FM");
									}

									if (needesc.equalsIgnoreCase("Y") && getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != ""
											&& fbp.getFeedback_by_mobno().trim().length() == 10
											&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
									{
										String complainMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + "," + " Ticket No: " + fbp.getTicket_no() + ", for " + fbp.getFeed_by()
												+ ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg() + ", Resolution Time: " + DateUtil.getCurrentDateIndianFormat() + ","
												+ DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5) + " ,Status is : Open";
										communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainMsg, ticketNo, "Pending", "0", "FM");
									}

									if (needesc.equalsIgnoreCase("Y") && fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true, orgName);
										communication.addMail(fbp.getFeed_by(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketNo,
												"Pending", "0", null, "FM");
									}
									if (needesc.equalsIgnoreCase("Y") && getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("")
											&& !fbp.getFeedback_by_emailid().equals("NA"))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, false, orgName);
										communication.addMail(fbp.getFeed_by(), fbp.getFeedback_by_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketNo,
												"Pending", "0", null, "FM");
									}
								}
							}
					}
					if(ticketAdded)
					{
						if(getActivityFlag().equalsIgnoreCase("1"))
						{
							StringBuilder query=new StringBuilder("update feedback_status_feed_paperRating set status='Ticket Opened',stage='2' where id="+getFeedStatId()+"");
							int i=cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						}
						else
						{
							wherClause.put("status","Ticket Opened");
							boolean updateFeed=	new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock,connectionSpace);
						
							StringBuilder query=new StringBuilder("update feedback_status set status='Ticket Opened',stage='2' where id=(select feed_stat_id from feedback_ticket where feed_data_id='"+getFeedId()+"' limit 1)");
							int i=cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						}
						return "success";
					}
					else
					{
						
						return "unsuccess";
					}
				}
				else if(getAction().equalsIgnoreCase("Snooze") || getAction().equalsIgnoreCase("3"))
				{
					String snDate = "", snTime = "", snUpToDate = "", snUpToTime = "", snDuration = "", escDate = null, escTime = null,moduleName=null;
					
					String feedStatId=helper.getFeedIdofFeedbackData(getFeedId(),connectionSpace);
					
					StringBuilder query = new StringBuilder();
					query.append("SELECT sn_on_date,sn_on_time,sn_upto_date,sn_upto_time,sn_duration,escalation_date,escalation_time,moduleName FROM feedback_status");
					query.append(" WHERE id =" +feedStatId);
					List ticketData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (ticketData != null && ticketData.size() > 0)
					{
						for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && !object[0].toString().equals(""))
							{
								snDate = object[0].toString();
							}
							else
							{
								snDate = "NA";
							}
							if (object[1] != null && !object[1].toString().equals(""))
							{
								snTime = object[1].toString();
							}
							else
							{
								snTime = "NA";
							}
							if (object[2] != null && !object[2].toString().equals(""))
							{
								snUpToDate = object[2].toString();
							}
							else
							{
								snUpToDate = "NA";
							}
							if (object[3] != null && !object[3].toString().equals(""))
							{
								snUpToTime = object[3].toString();
							}
							else
							{
								snUpToTime = "NA";
							}
							if (object[4] != null && !object[4].toString().equals(""))
							{
								snDuration = object[4].toString();
							}
							else
							{
								snDuration = "NA";
							}
							
							if (object[5] != null && !object[5].toString().equals(""))
							{
								escDate = object[5].toString();
							}
							
							if (object[6] != null && !object[6].toString().equals(""))
							{
								escTime = object[6].toString();
							}
							
							if (object[7] != null && !object[7].toString().equals(""))
							{
								moduleName = object[7].toString();
							}
						}
					}
					
					wherClause.put("status","Snooze");
					boolean updateFeed=	new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock,connectionSpace);
					System.out.println("Comment>>>>>"+getSnoozecomment());
					System.out.println("Date Snooze>>>"+getSnoozeDate());
					System.out.println("Snooze time >>>"+getSnoozeTime());
					
					String duration = "NA";
					
					WorkingHourAction WHA = new WorkingHourAction();
					WorkingHourHelper WHH = new WorkingHourHelper();
					String newVartualSnoozeTime = "00:00";
					if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.convertDateToUSFormat(snoozeDate)))
					{
						newVartualSnoozeTime = snoozeTime;
					}
					else
					{
						newVartualSnoozeTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
					}
				//    System.out.println("newVartualSnoozeTime " + newVartualSnoozeTime+" >>> "+escDate);
					// String grossEscTime =
					// DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
					// DateUtil.getCurrentTimeHourMin(), escDate, escTime);
					String workingTime = "00:00";
					if(!DateUtil.getCurrentDateUSFormat().equals(escDate))
					{
						workingTime=WHH.getWorkingHrs(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connectionSpace, cbt, moduleName, escDate);
					}
				//	System.out.println("Total Working Hrs "+workingTime);
					List<String> dataList = WHH.getDayDate(DateUtil.getCurrentDateUSFormat(), connectionSpace, cbt, moduleName);
					String date = dataList.get(0).toString();
					String day = dataList.get(1).toString();
		//			System.out.println("workingTime before add" + workingTime);

					if (date.equals(DateUtil.getCurrentDateUSFormat()))
					{
						List tempList = WHH.getWorkingTimeDetails(day, connectionSpace, cbt, moduleName);
						if (tempList != null && tempList.size() > 0)
						{
							String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
							for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								fromTime = object[0].toString();
								toTime = object[1].toString();
								workingHrs = object[2].toString();
							}
							if (DateUtil.checkTwoTimeWithMilSec(fromTime, DateUtil.getCurrentTimeHourMin()) && DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), toTime))
							{
								String todayRemainingWorkingTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(),
										toTime);
								workingTime = DateUtil.addTwoTime(workingTime, todayRemainingWorkingTime);
							}
							else if (DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), fromTime))
							{
								workingTime = DateUtil.addTwoTime(workingTime, workingHrs);
							}
						//	System.out.println("Working Time "+workingTime);
						}
					}
					String escDay = DateUtil.getDayofDate(escDate);
					List tempList = WHH.getWorkingTimeDetails(escDay, connectionSpace, cbt, moduleName);
					if (tempList != null && tempList.size() > 0)
					{
						String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							fromTime = object[0].toString();
							toTime = object[1].toString();
							workingHrs = object[2].toString();
						}
					//	System.out.println(escDate+" >>>> "+ fromTime+" >>>> "+ escDate+" >>>> "+ escTime);
						String remainingWorkingTimeOnEscDate = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), escDate, escTime);
				//	System.out.println(remainingWorkingTimeOnEscDate + " >>> remainingWorkingTimeOnEscDate");
						workingTime = DateUtil.addTwoTime(workingTime, remainingWorkingTimeOnEscDate);

					}
				//	System.out.println(" newVartualSnoozeTime " + newVartualSnoozeTime + " >>>>>" + workingTime);
					List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, newVartualSnoozeTime, workingTime, "Y", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(),
							moduleName);
					if (dateTime != null && dateTime.size() > 0)
					{
						if (dateTime.get(0) != null && dateTime.get(1) != null)
							duration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), dateTime.get(0), dateTime.get(1));

			//			System.out.println("Snooze Date " + dateTime.get(0) + " >>> " + dateTime.get(1));
					//	System.out.println("Escalation Date " + dateTime.get(2) + " >>> " + dateTime.get(3));
						/*
						 * if(dateTime.get(2)!=null && dateTime.get(3)!=null) {
						 * escDate = dateTime.get(2); escTime = dateTime.get(3);
						 * }
						 */

						wherClause.put("sn_reason", DateUtil.makeTitle(getSnoozecomment()));
						if (snDate.equals("NA") || snTime.equals("NA") || snDate.equals("") || snTime.equals(""))
						{
							wherClause.put("sn_on_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("sn_on_time", DateUtil.getCurrentTime());
						}
						wherClause.put("sn_upto_date", dateTime.get(0));
						wherClause.put("sn_upto_time", dateTime.get(1));
						wherClause.put("sn_duration", duration);
						wherClause.put("escalation_date", dateTime.get(2));
						wherClause.put("escalation_time", dateTime.get(3));
						wherClause.put("action_by", userName);
						condtnBlock.put("id", feedStatId);
					}
					//getFeedId()
					
					if(getActivityFlag().equalsIgnoreCase("1"))
					{
						updateFeed = HUH.updateTableColomn("feedback_status_feed_paperRating", wherClause, condtnBlock, connectionSpace);
					}
					else
					{
						updateFeed = HUH.updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
					}
					return "noFurther Action";
				}
				else
				{
					return "error";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public String getFeedId() {
		return feedId;
	}
	public String getFeedToDept() {
		return feedToDept;
	}
	public String getSubCatId() {
		return subCatId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public void setFeedToDept(String feedToDept) {
		this.feedToDept = feedToDept;
	}
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRecvSMS() {
		return recvSMS;
	}
	public void setRecvSMS(String recvSMS) {
		this.recvSMS = recvSMS;
	}
	public String getRecvEmail() {
		return recvEmail;
	}
	public void setRecvEmail(String recvEmail) {
		this.recvEmail = recvEmail;
	}
	public com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo getFbp() {
		return fbp;
	}
	public void setFbp(com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo fbp) {
		this.fbp = fbp;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFeed_brief() {
		return feed_brief;
	}
	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
	}
	public String getSnoozeDate() {
		return snoozeDate;
	}
	public void setSnoozeDate(String snoozeDate) {
		this.snoozeDate = snoozeDate;
	}
	public String getSnoozeTime() {
		return snoozeTime;
	}
	public void setSnoozeTime(String snoozeTime) {
		this.snoozeTime = snoozeTime;
	}
	public String getSnoozecomment() {
		return snoozecomment;
	}
	public void setSnoozecomment(String snoozecomment) {
		this.snoozecomment = snoozecomment;
	}
	public String getActivityFlag()
	{
		return activityFlag;
	}
	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}
	public String getFeedStatId()
	{
		return feedStatId;
	}
	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public String getTicket_no()
	{
		return ticket_no;
	}
	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}
}
