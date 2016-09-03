package com.Over2Cloud.ctrl.helpdesk.EscalationConfig;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class EscalationReminderHelper
{
	CommunicationHelper CH = new CommunicationHelper();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	public void checkEscalation(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		String query = "";
		query = "select id ,ticket_no ,feed_by ,feed_by_mobno ,feed_by_emailid ,to_dept_subdept,feed_brief,escalation_date ,escalation_time,open_date,open_time,location,level,subCatg,moduleName,non_working_time,roomNo from feedback_status where status='Pending' and escalation_date<='"
				+ DateUtil.getCurrentDateUSFormat() + "'";
		List data4escalate = HUH.getData(query, connection);
		if (data4escalate != null && data4escalate.size() > 0)
		{
			String id = null, ticket_no = null, feed_by = null, feed_by_mobno = null, feed_by_emailid = null, to_dept = null, feed_brief = null, to_dept_subdept = "", escalation_date = null, escalation_time = null, open_date = null, open_time = null, location = null, level = null, subCatg = null, catg_Dept = null, esc_duration = null, needesc = null, module = null;
			String adrr_time = "", res_time = "", non_working_time = "00:00", roomNo =null;
			String levelMsg = null, subject = null, ulevel = null, _2uLevel = null, _3uLevel = null;
			List esclevelData = null;
			
			for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null && !object[0].toString().equals(""))
					id = object[0].toString();
				
				if (object[1] != null && !object[1].toString().equals(""))
					ticket_no = getValueWithNullCheck(object[1]);
				
				if (object[2] != null && !object[2].toString().equals(""))
					feed_by = getValueWithNullCheck(object[2]);
				
				if (object[3] != null && !object[3].toString().equals(""))
					feed_by_mobno = getValueWithNullCheck(object[3]);
				
				if (object[4] != null && !object[4].toString().equals(""))
					feed_by_emailid = getValueWithNullCheck(object[4]);
				
				if (object[5] != null && !object[5].toString().equals(""))
					to_dept_subdept = getValueWithNullCheck(object[5]);
				
				if (object[6] != null && !object[6].toString().equals(""))
					feed_brief = getValueWithNullCheck(object[6]);

				if (object[7] != null && !object[7].toString().equals(""))
					escalation_date = getValueWithNullCheck(object[7]);
				
				if (object[8] != null && !object[8].toString().equals(""))
					escalation_time = getValueWithNullCheck(object[8]);
				
				if (object[9] != null && !object[9].toString().equals(""))
					open_date = getValueWithNullCheck(object[9]);
				
				if (object[10] != null && !object[10].toString().equals(""))
					open_time = getValueWithNullCheck(object[10]);
				
				if (object[11] != null && !object[11].toString().equals(""))
					location = getValueWithNullCheck(object[11]);
				
				if (object[12] != null && !object[12].toString().equals(""))
					level = getValueWithNullCheck(object[12]);
				
				if (object[13] != null && !object[13].toString().equals(""))
					subCatg = getValueWithNullCheck(object[13]);
				
				if (object[14] != null && !object[14].toString().equals(""))
					module = getValueWithNullCheck(object[14]);
				
				if (object[15] != null && !object[15].toString().equals(""))
				{
					non_working_time = object[15].toString();
				}
				
				if (object[16] != null && !object[16].toString().equals(""))
				{
					roomNo = object[16].toString();
				}
				
				if (subCatg != null && !subCatg.equals("") && !subCatg.equals("0"))
				{
					query = "select * from feedback_subcategory where id='" + subCatg + "'";
					List subCategoryList = HUH.getData(query, connection);
					if (subCategoryList != null && subCategoryList.size() > 0)
					{
						for (Iterator iterator5 = subCategoryList.iterator(); iterator5.hasNext();)
						{
							Object[] objectCol = (Object[]) iterator5.next();
							adrr_time = getValueWithNullCheck(objectCol[4]);
							res_time = getValueWithNullCheck(objectCol[5]);
							needesc = getValueWithNullCheck(objectCol[9]);
						}
					}
				}
				
				if(needesc!=null && !needesc.equalsIgnoreCase("NA") && needesc.equalsIgnoreCase("Y"))
				{
					if (escalation_date != null && !escalation_date.equals("") && !escalation_date.equals("NA") && escalation_time != null && !escalation_time.equals("")
							&& !escalation_time.equals("NA") && level != null && !level.equals("") && !level.equals("NA") && !level.equals("Level5"))
					{
						boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
						// If Escalating time passed away then go inside the If
						// block
						if (escnextlvl == true && module != null && !module.equals("") && !module.equals("NA"))
						{
							String[] newEscDateTime = new String[2];
							
							if (module.equals("HDM"))
							{
								String updateLevel = "";
								query = "select deptid from subdepartment where id='" + to_dept_subdept + "'";
								List data = HUH.getData(query, connection);
								if (data != null && data.size() > 0)
								{
									catg_Dept = data.get(0).toString();
									if(catg_Dept!=null)
									{
										query = "SELECT escLevel,considerRoaster,escLevelL2L3,l2Tol3,escLevelL3L4,l3Tol4,escLevelL4L5,l4Tol5 FROM escalation_config_detail WHERE escDept="+catg_Dept;
										List dataList = cbt.executeAllSelectQuery(query, connection);
										if(dataList!=null && dataList.size()>0)
										{
											for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();) 
											{
												Object[] object2 = (Object[]) iterator2.next();
												
												if(object2[0]!=null)
												{
													String time4Calc = null, escTimeCondtion = null;
													
													
													if(object2[0].toString().equalsIgnoreCase("subdept"))
													{
														if(object2[1]!=null)
														{
															if(object2[1].toString().equalsIgnoreCase("No"))
															{
																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																esclevelData = HUH.getEmp4Escalation(to_dept_subdept, "SD", module, ulevel, "N", "", connection,false);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[2].toString());
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[4].toString());
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[6].toString());
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
																
															}
															else if(object2[1].toString().equalsIgnoreCase("Yes"))
															{

																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																esclevelData = HUH.getEmp4Escalation(to_dept_subdept, "SD", module, ulevel, "N", "", connection,true);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[2].toString());
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[4].toString());
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7].toString());
																	escTimeCondtion = getValueWithNullCheck(object2[6].toString());
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
																
															}
														}
													}
													else if(object2[0].toString().equalsIgnoreCase("floor"))
													{

														if(object2[1]!=null)
														{
															if(object2[1].toString().equalsIgnoreCase("No"))
															{
																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																esclevelData = HUH.getEmp4Escalation( catg_Dept, roomNo, ulevel,"floor", connection);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3]);
																	escTimeCondtion = getValueWithNullCheck(object2[2]);
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5]);
																	escTimeCondtion = getValueWithNullCheck(object2[4]);
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7]);
																	escTimeCondtion = getValueWithNullCheck(object2[6]);
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
																
															}
															else if(object2[1].toString().equalsIgnoreCase("Yes"))
															{

																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																//getEmp4Escalation(String dept_subDept,String module, String level,String flrCondi, SessionFactory connectionSpace,String roomNo)
																esclevelData = HUH.getEmp4Escalation(to_dept_subdept, module, ulevel, "floor", connection,roomNo);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3]);
																	escTimeCondtion = getValueWithNullCheck(object2[2]);
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5]);
																	escTimeCondtion = getValueWithNullCheck(object2[4]);
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7]);
																	escTimeCondtion = getValueWithNullCheck(object2[6]);
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
															}
														}
													}
													else if(object2[0].toString().equalsIgnoreCase("wings"))
													{

														if(object2[1]!=null)
														{
															if(object2[1].toString().equalsIgnoreCase("No"))
															{
																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																esclevelData = HUH.getEmp4Escalation( catg_Dept, roomNo, ulevel,"wings", connection);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3]);
																	escTimeCondtion = getValueWithNullCheck(object2[2]);
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5]);
																	escTimeCondtion = getValueWithNullCheck(object2[4]);
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7]);
																	escTimeCondtion = getValueWithNullCheck(object2[6]);
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
																
															}
															else if(object2[1].toString().equalsIgnoreCase("Yes"))
															{

																
																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
																levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: "
																		+ DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: "
																		+ location + ", Brief: " + feed_brief + ".";
																//getEmp4Escalation(String dept_subDept,String module, String level,String flrCondi, SessionFactory connectionSpace,String roomNo)
																esclevelData = HUH.getEmp4Escalation(to_dept_subdept, module, ulevel, "wings", connection,roomNo);
																updateLevel = "Level" + ulevel;
																
																if(ulevel.equals("2"))
																{
																	time4Calc = getValueWithNullCheck(object2[3]);
																	escTimeCondtion = getValueWithNullCheck(object2[2]);
																}
																else if(ulevel.equals("3"))
																{
																	time4Calc = getValueWithNullCheck(object2[5]);
																	escTimeCondtion = getValueWithNullCheck(object2[4]);
																}
																else if(ulevel.equals("4"))
																{
																	time4Calc = getValueWithNullCheck(object2[7]);
																	escTimeCondtion = getValueWithNullCheck(object2[6]);
																}
																
																newEscDateTime = nextEscDateTime( escalation_date, escalation_time, res_time, ulevel, escTimeCondtion, time4Calc, module, connection);
																
															}
														}
													}
												}
											}
										}
										
										if (esclevelData != null && esclevelData.size() > 0)
										{
											for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
											{
												Object[] object3 = (Object[]) iterator3.next();

												if (escalation_date != null && escalation_date != "" && escalation_time != null && escalation_time != "")
												{
													String new_date = newEscDateTime[0];
													String new_time = newEscDateTime[1];

													String updateQry = "update feedback_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',level='" + updateLevel
															+ "',non_working_time='" + non_working_time + "' where id='" + id + "'";
													boolean updatefeedback = HUH.updateData(updateQry, connection);

													FeedbackPojo fbp = null;
													if (module.equalsIgnoreCase("HDM"))
													{
														data = HUH.getFeedbackDetail("", "SD", "Pending", id, connection);
														fbp = HUH.setFeedbackDataValues(data, "Pending", "SD");
													}
													if (updatefeedback)
													{
														if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
														{
															CH.addMessage(object3[1].toString(), object3[4].toString(), object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
														}
														if (fbp != null)
														{
															String mailText = HUH.configMessage4Escalation(fbp, subject, object3[1].toString(), "SD", true);
															CH.addMail(object3[1].toString(), object3[4].toString(), object3[3].toString(), subject, mailText, "", "Pending", "0", null, module, connection);
														}
														if (fbp != null && fbp.getFeed_registerby() != null && !fbp.getFeed_registerby().equals("") && fbp.getFeedback_by_mobno() != null
																&& !fbp.getFeedback_by_mobno().equals("") && !fbp.getFeedback_by_mobno().equals("NA"))
														{
															String msg = "Dear " + fbp.getFeed_registerby() + ", We apologies for delay of Ticket No: " + fbp.getTicket_no()
																	+ " that has now been escalated to " + DateUtil.makeTitle(object3[1].toString()) + ", Mob: " + object3[2].toString() + ". Thanks.";
															CH.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), msg, "", "Pending", "0", module, connection);
														}
													}
												}
											}
										}
										
									}
								}
							}
							else
							{
								catg_Dept = to_dept_subdept;
							}
						}
					}
				}
			}
		}
	}
	
	
	public String[] nextEscDateTime( String escDate, String escTime, String preDefineEscTime, String level, String escCondition, String escTime2Calc, String moduleName, SessionFactory connection)
	{
		WorkingHourAction WHA=new WorkingHourAction();
		String[] newdate_time = new String[2];

		if(escCondition!=null && escCondition.equalsIgnoreCase("Multiplicative"))
		{
			List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, preDefineEscTime, "05:00", "Y", escDate, escTime, moduleName);
			if (dateTime!=null && dateTime.size()>0)
			{
				newdate_time[0]=dateTime.get(0);
				newdate_time[1]=dateTime.get(1);
			}
		}
		else if(escCondition!=null && escCondition.equalsIgnoreCase("Customised"))
		{
			if(level!=null)
			{
				if(level.equals("2"))
				{
					List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, escTime2Calc, "05:00", "Y", escDate, escTime, moduleName);
					if (dateTime!=null && dateTime.size()>0)
					{
						newdate_time[0]=dateTime.get(0);
						newdate_time[1]=dateTime.get(1);
					}
				}
				else if(level.equals("3"))
				{
					List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, escTime2Calc, "05:00", "Y", escDate, escTime, moduleName);
					if (dateTime!=null && dateTime.size()>0)
					{
						newdate_time[0]=dateTime.get(0);
						newdate_time[1]=dateTime.get(1);
					}
				}
				else if(level.equals("4"))
				{
					List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, escTime2Calc, "05:00", "Y", escDate, escTime, moduleName);
					if (dateTime!=null && dateTime.size()>0)
					{
						newdate_time[0]=dateTime.get(0);
						newdate_time[1]=dateTime.get(1);
					}
				}
			}
		}
		return newdate_time;
	}
	
	
	
	
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}
}