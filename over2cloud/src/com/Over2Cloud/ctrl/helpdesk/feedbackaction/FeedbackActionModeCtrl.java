package com.Over2Cloud.ctrl.helpdesk.feedbackaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class FeedbackActionModeCtrl 
{
	FeedbackActionModeHelper FAMH = new FeedbackActionModeHelper();
	@SuppressWarnings("rawtypes")
	public boolean actionBySMS(SessionFactory connection, String ticketId, String mobileNo, String comment, String date, String time, String status)
	{
		boolean updateFlag = false;
		try
		{
			System.out.println(ticketId);
			if(ticketId!=null && !ticketId.equals(""))
			{
				ticketId=ticketId.trim();
				String openDate="00:00", openTime="00:00", resolveDuration = "00:00", deptLevel = null, moduleName = null, feedId = null, remark =null;
				
				List dataList = FAMH.getTicketDetails(connection,ticketId);
				List empList = FAMH.getEmpId(connection,mobileNo);
				System.out.println(dataList.size()+ " >>> "+empList.size());
				if(dataList!=null && dataList.size()>0 && empList!=null && empList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if(object[3]!=null && !object[3].toString().equals(""))
						{
							openDate = object[3].toString();
							openTime = object[4].toString();
							resolveDuration = DateUtil.time_difference(openDate, openTime, date, time);
						}
						if(object[6]!=null && !object[6].toString().equals(""))
							feedId = object[6].toString();
						
						if(object[7]!=null && !object[7].toString().equals(""))
							moduleName = object[7].toString();
						
						if(object[8]!=null && !object[8].toString().equals(""))
							deptLevel = object[8].toString();
						
						if(object[9]!=null && !object[9].toString().equals(""))
							remark = object[9].toString();
						
						System.out.println("status "+status);
						if(object[5]!=null && !object[5].toString().equals("") && !object[5].toString().equalsIgnoreCase("Resolved"))
						{
							Map<String, Object> data4Update = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							if(status.equalsIgnoreCase("Ignore"))
							{
								data4Update.put("status", status);
								data4Update.put("ig_date", date);
								data4Update.put("ig_time", time);
								data4Update.put("ig_reason", comment);
								data4Update.put("ignore_mode", "SMS");
								condtnBlock.put("ticket_no", ticketId);
								updateFlag = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", data4Update, condtnBlock, connection);
							}
							else if(status.equalsIgnoreCase("High Priority"))
							{
								data4Update.put("status", status);
								data4Update.put("hp_date", date);
								data4Update.put("hp_time", time);
								data4Update.put("hp_reason", comment);
								data4Update.put("hp_mode", "SMS");
								condtnBlock.put("ticket_no", ticketId);
								updateFlag = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", data4Update, condtnBlock, connection);
							}
							else if(status.equalsIgnoreCase("Resolved"))
							{
								data4Update.put("status", status);
								data4Update.put("resolve_date", date);
								data4Update.put("resolve_time", time);
								data4Update.put("resolve_remark", comment);
								data4Update.put("spare_used", "NA");
								data4Update.put("resolve_duration", resolveDuration);
								data4Update.put("resolve_by", empList.get(0).toString());
								data4Update.put("close_mode", "SMS");
								condtnBlock.put("ticket_no", ticketId);
								updateFlag = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", data4Update, condtnBlock, connection);
								System.out.println("Update Falg"+updateFlag);
							}
						}
						if (updateFlag)
						{
							HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
							//MsgMailCommunication MMC = new MsgMailCommunication();
							CommunicationHelper MMC = new CommunicationHelper();
							FeedbackPojo fbp = new FeedbackPojo();
							String levelMsg = "", complainatMsg = "", mailText = "", mailSubject = "", mailheading = "";
							List data = HUH.getFeedbackDetail("", "SD", status, feedId, connection);
							if (data != null && data.size() > 0)
							{
								fbp = HUH.setFeedbackDataValues(data, status, deptLevel);
							}

							if (status.equalsIgnoreCase("Resolved"))
							{
								levelMsg = "Close Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
								complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
								mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
								mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
							}
							else if (status.equalsIgnoreCase("High Priority"))
							{
								levelMsg = "High Priority Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By : " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + ".";
								complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + " is on High Priority.";
								mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
								mailheading = "High Priority Case Ticket Alert";
							}
							else if (status.equalsIgnoreCase("Ignore"))
							{
								levelMsg = "Ignore Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " should be Ignored.";
								complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " is Ignored.";
								mailSubject = "Ignore Feedback Alert: " + fbp.getTicket_no();
								mailheading = "Ignore Case Ticket Alert";
							}
							if (status.equalsIgnoreCase("Resolved"))
							{
								if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
								{
									MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticketId,status, "0", "HDM",connection);
								}

								if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
								{
									mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, status, deptLevel, false);
									MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticketId, status, "0", "", "HDM",connection);
								}

								if (fbp.getResolve_by_mobno() != null && fbp.getResolve_by_mobno() != "" && fbp.getResolve_by_mobno().trim().length() == 10 && (fbp.getResolve_by_mobno().startsWith("9") || fbp.getResolve_by_mobno().startsWith("8") || fbp.getResolve_by_mobno().startsWith("7")))
								{
									MMC.addMessage(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_mobno(), levelMsg, ticketId,status, "0", "HDM",connection);
								}

								if (fbp.getResolve_by_emailid() != null && !fbp.getResolve_by_emailid().equals(""))
								{
									mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, status, deptLevel, true);
									MMC.addMail(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_emailid(), mailSubject, mailText, ticketId, "Pending", "0", "", "HDM",connection);
								}

							}
							else if (status.equalsIgnoreCase("High Priority") || status.equalsIgnoreCase("Snooze") || status.equalsIgnoreCase("Ignore"))
							{

								if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
								{
									MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticketId, status, "0", "HDM",connection);
								}

								if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
								{
									mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, status, deptLevel, false);
									MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticketId, status, "0", "", "HDM",connection);
								}

								if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
								{
									MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketId, status, "0", "HDM",connection);
								}

								if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
								{
									mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, status, deptLevel, true);
									MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, ticketId, "Pending", "0", "", "HDM",connection);
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return updateFlag;
	}
}