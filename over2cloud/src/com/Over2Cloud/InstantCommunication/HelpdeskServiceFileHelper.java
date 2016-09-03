// HelpdeskServicefileHelper

package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class HelpdeskServiceFileHelper
	{
		String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";
		private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
		String deptComment = "NA";

		// Method body for Escalating Ticket @Khushal 24-01-2014
		public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
			{
				try
					{
						String query = "";
						query = "select id ,ticket_no ,to_dept_subdept,escalation_date ,escalation_time,status,open_time,location,level,subCatg,non_working_time from feedback_status_new where status IN ('Pending','High Priority','Re-Assign','Re-Opened') and escalation_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
						//System.out.println("query :::: "+query);
						List data4escalate = HUH.getData(query, connection);
						if (data4escalate != null && data4escalate.size() > 0)
							{
								String id = null, ticket_no = null, escalation_date = null, escalation_time = null, location = null, level = null, subCatg = null, needesc = null, module = "HDM";
								String res_time = "", non_working_time = "00:00", subcag = null, subCatDept = null, status = null;
								String ulevel = null, _2uLevel = null, _3uLevel = null, deptId = null;
								StringBuilder levelMsg = new StringBuilder();
								List esclevelData = null;
								List esclevel = null;
								for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null && !object[0].toString().equals(""))
											{
												id = object[0].toString();
											}
										if (object[1] != null && !object[1].toString().equals(""))
											{
												ticket_no = object[1].toString();
											}
										else
											{
												ticket_no = "NA";
											}
										if (object[2] != null && !object[2].toString().equals(""))
											{
												deptId = object[2].toString();
											}
										else
											{
												deptId = "NA";
											}

										if (object[3] != null && !object[3].toString().equals(""))
											{
												escalation_date = object[3].toString();
											}
										else
											{
												escalation_date = "NA";
											}
										if (object[4] != null && !object[4].toString().equals(""))
											{
												escalation_time = object[4].toString();
											}
										else
											{
												escalation_time = "NA";
											}
										if (object[5] != null && !object[5].toString().equals(""))
											{
												status = object[5].toString();
											}
										else
											{
												status = "NA";
											}
										if (object[7] != null && !object[7].toString().equals(""))
											{
												location = object[7].toString();
											}
										else
											{
												location = "NA";
											}
										if (object[8] != null && !object[8].toString().equals(""))
											{
												level = object[8].toString();
											}
										else
											{
												level = "NA";
											}
										if (object[9] != null && !object[9].toString().equals(""))
											{
												subCatg = object[9].toString();
											}
										else
											{
												subCatg = "NA";
											}

										if (object[10] != null && !object[10].toString().equals(""))
											{
												non_working_time = object[10].toString();
											}

										if (escalation_date != null && !escalation_date.equals("") && !escalation_date.equals("NA") && escalation_time != null && !escalation_time.equals("") && !escalation_time.equals("NA") && level != null && !level.equals("") && !level.equals("NA") && !level.equals("Level6"))
											{
												boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
												if (escnextlvl)
													{
														if (subCatg != null && !subCatg.equals("") && !subCatg.equals("0"))
															{
																StringBuilder query1 = new StringBuilder();
																query1.append(" Select subdept.id,subCat.subCategoryName,subCat.addressingTime,subCat.resolutionTime,subCat.escalateTime,subCat.needEsc from subdepartment as subdept ");
																query1.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
																query1.append(" inner join feedback_category catt on catt.fbType = fb.id ");
																query1.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
																query1.append(" WHERE subCat.id= " + subCatg);
																 // System.out.println("query1.toString() :::::::::: "+query1.toString());
																List subCategoryList = new createTable().executeAllSelectQuery(query1.toString(), connection);
																if (subCategoryList != null && subCategoryList.size() > 0)
																	{
																		Object[] objectCol = (Object[]) subCategoryList.get(0);
																		subCatDept = objectCol[0].toString();

																		if (objectCol[3] == null)
																			{
																				res_time = "10:00";
																			}
																		else
																			{
																				res_time = objectCol[3].toString();
																			}
																		if (objectCol[5] == null)
																			{
																				needesc = "Y";
																			}
																		else
																			{
																				needesc = objectCol[5].toString();
																			}
																		if (objectCol[1] != null)
																			{
																				subcag = objectCol[1].toString();
																			}
																	}
																WorkingHourAction WHA = new WorkingHourAction();
																String new_date = null, new_time = null;
																String calculationLevel = null;
																// System.out.println("level ::: "+level);
																if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
																	{
																		List confData = null;
																		String colName1 = null;
																		String colName2 = null;

																		calculationLevel = String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1);
																		if (calculationLevel != null && calculationLevel.equalsIgnoreCase("2"))
																			{
																				colName1 = "escLevelL2L3";
																				colName2 = "l2Tol3";
																			}
																		else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
																			{
																				colName1 = "escLevelL3L4";
																				colName2 = "l3Tol4";
																			}
																		else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
																			{
																				colName1 = "escLevelL4L5";
																				colName2 = "l4Tol5";
																			}
																		else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("5"))
																			{
																				colName1 = "escLevelL5L6";
																				colName2 = "l5Tol6";
																			}
																		if (colName1 != null && colName2 != null)
																			{
																				String qry = " SELECT " + colName1 + "," + colName2 + " FROM escalation_config_detail WHERE escSubDept ='" + subCatDept + "'";
																				confData = new createTable().executeAllSelectQuery(qry, connection);
																			}
																		if (confData != null && confData.size() > 0)
																			{
																				Object[] obj = (Object[]) confData.get(0);
																				if (obj[0] != null && obj[1] != null)
																					{
																						if (obj[0].toString().equalsIgnoreCase("Customised"))
																							{
																								List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, obj[1].toString(), "05:00", needesc, escalation_date, escalation_time, module);
																								if (dateTime != null && dateTime.size() > 0)
																									{
																										new_date = dateTime.get(0);
																										new_time = dateTime.get(1);
																									}
																							}
																						else
																							{
																								List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, res_time, "05:00", needesc, escalation_date, escalation_time, module);
																								if (dateTime != null && dateTime.size() > 0)
																									{
																										new_date = dateTime.get(0);
																										new_time = dateTime.get(1);
																									}
																							}
																					}
																				else
																					{
																						List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, res_time, "05:00", needesc, escalation_date, escalation_time, module);
																						if (dateTime != null && dateTime.size() > 0)
																							{
																								new_date = dateTime.get(0);
																								new_time = dateTime.get(1);
																							}
																					}
																			}
																		else
																			{

																				new_date = escalation_date;
																				new_time = escalation_time;

																			}
																	}
																List data = null;
																FeedbackPojo fbp = null;
																data = HUH.getFeedbackDetail("", "SD", status, id, connection);
																fbp = HUH.setFeedbackDataValues(data, "Pending", "SD");

																String updateLevel = "", highLevel = "";
																ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
																// subject = "L"
																// +
																// level.substring(5)
																// + " to L" +
																// ulevel +
																// " Escalate Alert: "
																// + ticket_no;
																levelMsg.append("L" + level.substring(5) + " to L" + (ulevel) + ": " + ticket_no + "");
																esclevel = HUH.getLevelForEscalation(subCatDept, connection);
																highLevel = (String) esclevel.get(0).toString();
																// System.out.println("highLevel  : "+highLevel);

																if (calculationLevel != null && !calculationLevel.equalsIgnoreCase(highLevel))
																	{
																		levelMsg.append(", Closed By: " + DateUtil.convertDateToIndianFormat(new_date) + "," + new_time + "");
																	}
																levelMsg.append(", Allot To: " + fbp.getFeedback_allot_to() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + subcag + ", Brief: " + fbp.getFeed_brief() + ".");

																esclevelData = HUH.getEmp4Escalation(subCatDept, "SD", module, ulevel, "Y", location, connection, deptId);
																updateLevel = "Level" + ulevel;
																if (esclevelData == null || esclevelData.size() == 0)
																	{
																		levelMsg.delete(0, levelMsg.length());
																		_2uLevel = "" + (Integer.parseInt(ulevel) + 1);
																		levelMsg.append("L" + level.substring(5) + " to L" + (_2uLevel) + ": " + ticket_no + "");
																		if (calculationLevel != null && !calculationLevel.equalsIgnoreCase(highLevel))
																			{
																				levelMsg.append(", Closed By: " + DateUtil.convertDateToIndianFormat(new_date) + "," + new_time + "");
																			}
																		levelMsg.append(", Allot To: " + fbp.getFeedback_allot_to() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + subcag + ", Brief: " + fbp.getFeed_brief() + ".");
																		esclevelData = HUH.getEmp4Escalation(subCatDept, "SD", module, _2uLevel, "Y", location, connection, deptId);
																		updateLevel = "Level" + _2uLevel;
																		if (esclevelData == null || esclevelData.size() == 0)
																			{
																				levelMsg.delete(0, levelMsg.length());
																				_3uLevel = "" + (Integer.parseInt(_2uLevel) + 1);
																				levelMsg.append("L" + level.substring(5) + " to L" + (_3uLevel) + ": " + ticket_no + "");
																				if (calculationLevel != null && !calculationLevel.equalsIgnoreCase(highLevel))
																					{
																						levelMsg.append(", Closed By: " + DateUtil.convertDateToIndianFormat(new_date) + "," + new_time + "");
																					}
																				levelMsg.append(", Allot To: " + fbp.getFeedback_allot_to() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + subcag + ", Brief: " + fbp.getFeed_brief() + ".");

																				esclevelData = HUH.getEmp4Escalation(subCatDept, "SD", module, _3uLevel, "Y", location, connection, deptId);
																				updateLevel = "Level" + _3uLevel;
																			}
																	}
																// System.out.println("escadata data for employessss  "+esclevelData.size());
																if (esclevelData != null && esclevelData.size() > 0)
																	{
																		// createEscalationHistory(connection)
																		// ;
																		String updateQry = "update feedback_status_new set escalation_date='" + new_date + "',escalation_time='" + new_time + "',level='" + updateLevel + "',non_working_time='" + non_working_time + "' where id='" + id + "'";
																		
																	//	System.out.println("Update Q : "+updateQry);
																		boolean updatefeedback = HUH.updateData(updateQry, connection);
																		CH.addMessage(fbp.getFeedback_allot_to(), "", fbp.getMobOne(), levelMsg.toString(), "", "Pending", "0", module, connection);
																		if(levelMsg.toString().contains("L1 to L2") && deptId.equalsIgnoreCase("5"))
																			CH.addMessage(fbp.getFeedback_allot_to(), "", "9873659528", levelMsg.toString(), "", "Pending", "0", module, connection);
																		
																		for (Iterator iterator3 = esclevelData.listIterator(); iterator3.hasNext();)
																			{
																				Object[] object3 = (Object[]) iterator3.next();
																				if (escalation_date != null && escalation_date != "" && escalation_time != null && escalation_time != "")
																					{
																						insertInoHistoryTable(id, escalation_date, escalation_time, updateLevel, object3[0].toString(), connection);
																						if (updatefeedback)
																							{
																								if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
																									{
																										CH.addMessage(object3[1].toString(), object3[4].toString(), object3[2].toString(), levelMsg.toString(), "", "Pending", "0", module, connection);
																									}
																								iterator3.remove();

																							}
																					}
																			}
																	}
																levelMsg.setLength(0);
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

		private void insertInoHistoryTable(String id2, String escalation_date, String escalation_time, String updateLevel, String escalateTo, SessionFactory connection)
			{
				try
					{
					
				//	System.out.println("In History Table");
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;

						ob = new InsertDataTable();
						ob.setColName("feedId");
						ob.setDataName(id2);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Escalated");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_date");
						ob.setDataName(escalation_date);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_time");
						ob.setDataName(escalation_time);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("escalation_level");
						ob.setDataName(updateLevel);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_by");
						ob.setDataName(escalateTo);
						insertData.add(ob);

						cbt.insertIntoTable("feedback_status_history", insertData, connection);
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}

			}

		// Method body for Snooze to Active ticket status 24-01-2014
		@SuppressWarnings("rawtypes")
		public void SnoozetoPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
			{

				try
					{
						String query = "select fstatus.id as feedID, ticket_no ,feed_by ,to_dept_subdept ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time,non_working_time,his.action_remark,his.id as hidID from feedback_status_new as fstatus " + "LEFT JOIN feedback_status_history as his on his.feedId=fstatus.id where fstatus.status='Snooze' and sn_upto_date<='" + DateUtil.getCurrentDateUSFormat()
								+ "' AND snooze_status=0";
						// System.out.println("query :::SNooze ::: "+query);
						List data4escalate = HUH.getData(query, connection);
						if (data4escalate != null && data4escalate.size() > 0)
							{
								String feedid = null, ticket_no = null, hisID = null, to_dept = null, feed_brief = null, sn_reason = null, snooze_upto_date = null, snooze_upto_time = null, location = null, subCatg = null, subCatgName = null, allot_to = null, adrr_time = null, res_time = null, needesc = "", non_working_time = "00:00";
								List esclevelData = null;
								for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();

										if (object[0] != null && !object[0].toString().equals(""))
											{
												feedid = object[0].toString();
											}

										if (object[1] != null && !object[1].toString().equals(""))
											{
												ticket_no = object[1].toString();
											}
										else
											{
												ticket_no = "NA";
											}

										if (object[12] != null && !object[12].toString().equals(""))
											{
												hisID = object[12].toString();
											}
										else
											{
												hisID = "NA";
											}

										if (object[3] != null && !object[3].toString().equals(""))
											{
												to_dept = object[3].toString();
											}
										else
											{
												to_dept = "NA";
											}

										if (object[4] != null && !object[4].toString().equals(""))
											{
												feed_brief = object[4].toString();
											}
										else
											{
												feed_brief = "NA";
											}

										if (object[5] != null && !object[5].toString().equals(""))
											{
												allot_to = object[5].toString();
											}
										else
											{
												allot_to = "NA";
											}

										if (object[6] != null && !object[6].toString().equals(""))
											{
												location = object[6].toString();
											}
										else
											{
												location = "NA";
											}

										if (object[7] != null && !object[7].toString().equals(""))
											{
												subCatg = object[7].toString();
											}
										else
											{
												subCatg = "NA";
											}

										if (object[8] != null && !object[8].toString().equals(""))
											{
												snooze_upto_date = object[8].toString();
											}
										else
											{
												snooze_upto_date = "NA";
											}

										if (object[9] != null && !object[9].toString().equals(""))
											{
												snooze_upto_time = object[9].toString();
											}
										else
											{
												snooze_upto_time = "NA";
											}

										if (object[10] != null && !object[10].toString().equals(""))
											{
												non_working_time = object[10].toString();
											}
										if (object[11] != null && !object[11].toString().equals(""))
											{
												sn_reason = object[11].toString();
											}
										else
											{
												sn_reason = "NA";
											}
										if (snooze_upto_date != null && !snooze_upto_date.equals("") && snooze_upto_time != null && !snooze_upto_time.equals(""))
											{
												boolean snoozetopending = DateUtil.time_update(snooze_upto_date, snooze_upto_time);
												if (snoozetopending)
													{
														// Getting Detail from
														// Sub Category Table
														if (!subCatg.equals("0"))
															{
																String query2 = "select * from feedback_subcategory where id='" + subCatg + "'";
																List subCategoryList = HUH.getData(query2, connection);
																if (subCategoryList != null && subCategoryList.size() > 0)
																	{
																		for (Iterator iterator2 = subCategoryList.iterator(); iterator2.hasNext();)
																			{
																				Object[] objectCol = (Object[]) iterator2.next();
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
																				if (objectCol[9] == null)
																					{
																						needesc = "Y";
																					}
																				else
																					{
																						needesc = objectCol[9].toString();
																					}
																				if (objectCol[2] != null)
																					{
																						subCatgName = objectCol[2].toString();
																					}

																			}
																	}

																String new_date = null, new_time = null;
																if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
																	{
																		String[] newdate_time = null;

																		getNextEscalationDateTime(adrr_time, res_time, to_dept, "HDM", connection);
																		if (new_escalation_datetime != null && new_escalation_datetime != "")
																			{
																				newdate_time = new_escalation_datetime.split("#");
																				if (newdate_time.length > 0)
																					{
																						new_date = newdate_time[0];
																						new_time = newdate_time[1];
																						non_working_time = DateUtil.addTwoTime(non_working_time, non_working_timing);
																					}
																			}
																	}
																esclevelData = HUH.getEmployeeData(allot_to.toString(), "2", connection);
																if (esclevelData != null && esclevelData.size() > 0)
																	{
																		for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
																			{
																				Object[] object3 = (Object[]) iterator3.next();
																				if (snooze_upto_date != null && snooze_upto_date != "" && snooze_upto_time != null && snooze_upto_time != "")
																					{
																						String updateQry = null;
																						if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
																							{
																								updateQry = "update feedback_status_new set escalation_date='" + new_date + "',escalation_time='" + new_time + "',status='Pending',level='Level1',non_working_time='" + non_working_time + "' where id='" + feedid + "'";
																							}
																						else
																							{
																								updateQry = "update feedback_status_new set status='Pending',level='Level1' where id='" + feedid + "'";
																							}
																						String hisUpdate = "update feedback_status_history set snooze_status ='1' WHERE id = " + hisID;
																						boolean hisUpdate1 = HUH.updateData(hisUpdate, connection);
																						boolean updatefeedback = HUH.updateData(updateQry, connection);
																						if (updatefeedback)
																							{
																								if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
																									{
																										String msg = "Parked to Active: " + ticket_no + ", Resolved By: " + new_time + ", Loc: " + location + ", Call Desc: " + subCatgName + " , Reason: " + sn_reason + " .";
																										CH.addMessage(object3[0].toString(), object3[5].toString(), object3[1].toString(), msg, ticket_no, "Pending", "0", "HDM", connection);
																									}
																								/*
																								 * if
																								 * (
																								 * fbp
																								 * !=
																								 * null
																								 * )
																								 * {
																								 * String
																								 * mailText
																								 * =
																								 * new
																								 * HelpdeskUniversalHelper
																								 * (
																								 * )
																								 * .
																								 * getConfigMessage
																								 * (
																								 * fbp
																								 * ,
																								 * "Snooze --> Pending Feedback Alert"
																								 * ,
																								 * "Pending"
																								 * ,
																								 * "SD"
																								 * ,
																								 * true
																								 * )
																								 * ;
																								 * CH
																								 * .
																								 * addMail
																								 * (
																								 * object3
																								 * [
																								 * 0
																								 * ]
																								 * .
																								 * toString
																								 * (
																								 * )
																								 * ,
																								 * object3
																								 * [
																								 * 5
																								 * ]
																								 * .
																								 * toString
																								 * (
																								 * )
																								 * ,
																								 * object3
																								 * [
																								 * 4
																								 * ]
																								 * .
																								 * toString
																								 * (
																								 * )
																								 * ,
																								 * "Snooze --> Pending Feedback Alert"
																								 * ,
																								 * mailText
																								 * ,
																								 * ticket_no
																								 * ,
																								 * "Pending"
																								 * ,
																								 * "0"
																								 * ,
																								 * ""
																								 * ,
																								 * "HDM"
																								 * ,
																								 * connection
																								 * )
																								 * ;
																								 * }
																								 */
																							}
																					}
																			}
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

		public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
			{

				// System.out.println("Inside Important method");
				String startTime = "", endTime = "", callflag = "";
				String dept_id = todept;

				String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
				String working_day = DateUtil.getDayofDate(working_date);
				// List of working timing data
				List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						endTime = workingTimingData.get(2).toString();

						// Here we know the complaint lodging time is inside the
						// the start
						// and end time or not
						callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
						if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
							{
								// System.out.println("Inside before");

								// date_time =
								// DateUtil.newdate_AfterEscInRoaster(date,
								// startTime, adrr_time, res_time);
								// new_escalation_datetime =
								// DateUtil.newdate_AfterEscTime(date,
								// startTime, esc_duration, needesc);
								new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
								// System.out.println("After Calculation First escalation Date time is  "+new_escalation_datetime);
								// (Differrence between current date/time and
								// date & starttime)
								String newdatetime[] = new_escalation_datetime.split("#");
								// Check the date time is lying inside the time
								// block
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
												// DateUtil.newdate_AfterEscInRoaster(final_date,
												// startTime, adrr_time,
												// res_time);
												non_working_timing = workingTimingData.get(3).toString();
												// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
											}
									}
							}
						else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
							{

								// Escalation date time at the time of opening
								// the ticket
								new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
								String newdatetime[] = new_escalation_datetime.split("#");
								// Check the date time is lying inside the time
								// block
								boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
								if (flag)
									{
										non_working_timing = "00:00";
									}
								else
									{
										non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
										String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
										String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
										String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
										workingTimingData.clear();

										workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
										if (workingTimingData != null && workingTimingData.size() > 0)
											{
												startTime = workingTimingData.get(1).toString();
												String left_time = workingTimingData.get(4).toString();
												String final_date = workingTimingData.get(5).toString();
												new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
												// new_escalation_datetime =
												// DateUtil.newdate_AfterEscInRoaster(final_date,
												// startTime, adrr_time,
												// res_time);
												non_working_timing = workingTimingData.get(3).toString();
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
										// DateUtil.newdate_AfterEscInRoaster(final_date,
										// startTime,
										// adrr_time, res_time);
										non_working_timing = workingTimingData.get(3).toString();
										// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
									}
							}
					}

				// System.out.println("Final   ::::   Escalation Date  "+new_escalation_datetime);
				// System.out.println("Final Non Working Timing  "+non_working_timing);
			}

		public void ReportGeneration(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
			{
				try
					{
						// System.out.println("Inside Try Block");
						List data4report = HUH.getReportToData(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), connection);
						if (data4report != null && data4report.size() > 0)
							{
								// Local variable defined
								String empname = null, mobno = null, emailid = null, subject = null, report_level = null, subdept_dept = null, deptLevel = null, type_report = null, id = null, module = null, department = null;
								String allotTo, registerBy, ticket_status;
								// Counts for Current Day
								int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, re = 0, reo = 0, total = 0;
								// Counts for Carry Forward
								int cfpc = 0, cfhc = 0, cfsc = 0, cftotal = 0;
								List subDeptList = null;

								for (Iterator iterator = data4report.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();

										if (object[0] != null)
											{
												id = object[0].toString();
											}

										if (object[1] != null)
											{
												report_level = object[1].toString();
											}

										if (object[2] != null)
											{
												subdept_dept = object[2].toString();
											}

										/*
										 * if (object[3] != null) { sms_status =
										 * object[3].toString(); }
										 * 
										 * if (object[4] != null) { mail_status
										 * = object[4].toString(); }
										 */

										if (object[5] != null)
											{
												type_report = object[5].toString();
											}

										if (object[6] != null)
											{
												module = object[6].toString();
											}

										if (object[7] != null)
											{
												empname = object[7].toString();
											}
										else
											{
												empname = "NA";
											}

										if (object[8] != null)
											{
												mobno = object[8].toString();
											}
										else
											{
												mobno = "NA";
											}

										if (object[9] != null)
											{
												emailid = object[9].toString();
											}
										else
											{
												emailid = "NA";
											}

										if (object[10] != null)
											{
												subject = object[10].toString();
											}
										else
											{
												subject = "NA";
											}

										if (object[11] != null && !object[11].toString().equals(""))
											{
												department = object[11].toString();
											}
										else
											{
												department = "NA";
											}
										if (object[12] != null && !object[12].toString().equals(""))
											{
												allotTo = object[12].toString();
											}
										else
											{
												allotTo = "NA";
											}
										if (object[13] != null && !object[13].toString().equals(""))
											{
												registerBy = getDataInQuotes(object[13].toString());
											}
										else
											{
												registerBy = "NA";
											}
										if (object[14] != null && !object[14].toString().equals(""))
											{
												ticket_status = object[14].toString();
											}
										else
											{
												ticket_status = "NA";
											}

										String newDate = null;
										if (!type_report.equals("") && !type_report.equals("NA"))
											{
												newDate = HUH.getNewDate(type_report);
											}
										String updateQry = "update report_configuration set report_date='" + newDate + "' where id='" + id + "'";
										boolean updateReport = HUH.updateData(updateQry, connection);
										if (module != null && !module.equals("") && module.equalsIgnoreCase("HDM") && updateReport)
											{
												// System.out.println("Inside If Block");
												List currentdayCounter = new ArrayList();
												List CFCounter = new ArrayList();
												List<FeedbackPojo> currentDayResolvedData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayRange = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayPendingData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDaySnoozeData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayHPData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayIgData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> CFData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayReOpenData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayReAssignData = new ArrayList<FeedbackPojo>();
												List<FeedbackPojo> currentDayAllReOpenData = new ArrayList<FeedbackPojo>();
												if (type_report.equals("D"))
													{
														subject = "Daily Summary Report for Help Desk Manager As On " + DateUtil.getCurrentDateIndianFormat() + ", " + DateUtil.getCurrentTime();
													}
												else if (type_report.equals("W"))
													{
														subject = "Weekly Summary Report for Help Desk Manager From " + DateUtil.getNewDate("day", -7, DateUtil.getCurrentDateUSFormat()) + " To " + DateUtil.getCurrentDateIndianFormat();
													}
												else if (type_report.equals("M"))
													{
														subject = "Monthly Summary Report for Help Desk Manager From " + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + " To " + DateUtil.getCurrentDateIndianFormat();
													}
												// System.out.println("Report Level   "
												// + report_level);
												// get Data when Dept Level is 2
												// in Organization
												// getting the data for All the
												// departments
												if (report_level.equals("2"))
													{
														// System.out.println("Inside If when report level is 2");
														currentDayRange = HUH.getTicketData(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, allotTo, registerBy, connection);

														currentdayCounter = HUH.getTicketCounters(report_level, type_report, true, subdept_dept, deptLevel, allotTo, registerBy, connection);
														CFCounter = HUH.getTicketCounters(report_level, type_report, false, subdept_dept, deptLevel, allotTo, registerBy, connection);
														if (ticket_status.contains("RD"))
															{
																currentDayResolvedData = HUH.getTicketData(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("P"))
															{
																currentDayPendingData = HUH.getTicketData(report_level, type_report, true, "Pending", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("S"))
															{
																currentDaySnoozeData = HUH.getTicketData(report_level, type_report, true, "Snooze", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("HP"))
															{
																currentDayHPData = HUH.getTicketData(report_level, type_report, true, "High Priority", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("I"))
															{
																currentDayIgData = HUH.getTicketData(report_level, type_report, true, "Ignore", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("RO"))
															{
																currentDayReOpenData = HUH.getTicketData(report_level, type_report, true, "Re-Opened", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("RS"))
															{
																currentDayReAssignData = HUH.getTicketData(report_level, type_report, true, "Re-assign", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														if (ticket_status.contains("RO"))
															{
																currentDayAllReOpenData = HUH.getTicketData(report_level, type_report, true, "Re-openedR", subdept_dept, deptLevel, allotTo, registerBy, connection);
															}
														CFData = HUH.getTicketData(report_level, type_report, false, "", subdept_dept, deptLevel, allotTo, registerBy, connection);
													}
												// getting the data for specific
												// department
												else if (report_level.equals("1"))
													{
														subDeptList = HUH.getSubDeptList(subdept_dept, connection);
														if (subDeptList != null)
															{
																currentDayRange = HUH.getTicketData(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, allotTo, registerBy, connection);

																currentdayCounter = HUH.getTicketCounters(report_level, type_report, true, subdept_dept, deptLevel, allotTo, registerBy, connection);
																// CFCounter =
																// HUH.getTicketCounters(report_level,
																// type_report,
																// false,
																// subdept_dept,
																// deptLevel,
																// connection);
																if (ticket_status.contains("RD"))
																	{
																		currentDayResolvedData = HUH.getTicketData(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("P"))
																	{
																		currentDayPendingData = HUH.getTicketData(report_level, type_report, true, "Pending", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("S"))
																	{
																		currentDaySnoozeData = HUH.getTicketData(report_level, type_report, true, "Snooze", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("HP"))
																	{
																		currentDayHPData = HUH.getTicketData(report_level, type_report, true, "High Priority", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("I"))
																	{
																		currentDayIgData = HUH.getTicketData(report_level, type_report, true, "Ignore", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("RO"))
																	{
																		currentDayReOpenData = HUH.getTicketData(report_level, type_report, true, "Re-Opened", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("RS"))
																	{
																		currentDayReAssignData = HUH.getTicketData(report_level, type_report, true, "Re-assign", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																if (ticket_status.contains("RO"))
																	{
																		currentDayAllReOpenData = HUH.getTicketData(report_level, type_report, true, "Re-OpenedR", subdept_dept, deptLevel, allotTo, registerBy, connection);
																	}
																// System.out.println("For Carry Forward Data");
																// CFData =
																// HUH.getTicketData(report_level,
																// type_report,
																// false, "",
																// subdept_dept,
																// deptLevel,
																// connection);
																// }
															}
													}
												// get Data when Dept Level is 1
												// in Organization

												// Check Counter for Current day
												if (currentdayCounter != null && currentdayCounter.size() > 0)
													{
														for (Iterator iterator2 = currentdayCounter.iterator(); iterator2.hasNext();)
															{
																Object[] object2 = (Object[]) iterator2.next();
																if (object2[1].toString().equalsIgnoreCase("Pending"))
																	{
																		pc = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("Snooze"))
																	{
																		sc = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("High Priority"))
																	{
																		hc = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("Ignore"))
																	{
																		ic = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("Resolved"))
																	{
																		rc = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("Re-assign"))
																	{
																		re = Integer.parseInt(object2[0].toString());
																	}
																else if (object2[1].toString().equalsIgnoreCase("Re-Opened"))
																	{
																		reo = Integer.parseInt(object2[0].toString());
																	}
															}
														total = pc + sc + hc + ic + rc + re + reo;
														// System.out.println("Snooze  :  "+sc);
													}
												// Check Counter for Carry
												// Forward
												/*
												 * if (CFCounter != null &&
												 * CFCounter.size() > 0) { for
												 * (Iterator iterator2 =
												 * CFCounter.iterator();
												 * iterator2.hasNext();) {
												 * Object[] object3 = (Object[])
												 * iterator2.next(); if
												 * (object3[
												 * 1].toString().equalsIgnoreCase
												 * ("Pending")) { cfpc =
												 * Integer.
												 * parseInt(object3[0].toString
												 * ()); } else if
												 * (object3[1].toString
												 * ().equalsIgnoreCase
												 * ("Snooze")) { cfsc =
												 * Integer.parseInt
												 * (object3[0].toString()); }
												 * else if
												 * (object3[1].toString()
												 * .equalsIgnoreCase
												 * ("High Priority")) { cfhc =
												 * Integer
												 * .parseInt(object3[0].toString
												 * ()); } } cftotal = cfpc +
												 * cfsc + cfhc; }
												 */
												String report_sms = "Dear " + empname + ", For All Ticket Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + pc + ", Parked: " + sc + ", Ignore: " + ic + ", Resolved: " + rc + ", Re-assign: " + re + ", Re-Opened: " + reo + ".";
												if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
													{
														@SuppressWarnings("unused")
														boolean putsmsstatus = CH.addMessage(empname, department, mobno, report_sms, subject, "Pending", "0", "HDM", connection);
													}

												String filepath = new DailyReportExcelWrite4Attach().writeDataInExcel(currentdayCounter, CFCounter, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, currentDayReOpenData, currentDayReAssignData, currentDayAllReOpenData, CFData, deptLevel, type_report);
												String mailtext = HUH.getConfigMailForReport(pc, hc, sc, ic, rc, re, reo, total, cfpc, cfsc, cfhc, cftotal, empname, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, currentDayReOpenData, currentDayReAssignData, currentDayAllReOpenData, currentDayRange, CFData, subdept_dept);
												CH.addMail(empname, department, emailid, subject, mailtext, "Report", "Pending", "0", filepath, "HDM", connection);
											}

										/*
										 * else if (module != null &&
										 * !module.equals("") &&
										 * module.equalsIgnoreCase("FM") &&
										 * updateReport) {
										 * 
										 * //
										 * System.out.println("For Fedback ");
										 * // For Feedback
										 * 
										 * int totalSnooze = 0; FeedbackHelper
										 * FRH = new FeedbackHelper();
										 * 
										 * List currentdayCounter = new
										 * ArrayList(); List CFCounter = new
										 * ArrayList(); List<FeedbackPojo>
										 * currentDayResolvedData = null;
										 * List<FeedbackPojo>
										 * currentDayPendingData = null;
										 * List<FeedbackPojo>
										 * currentDaySnoozeData = null;
										 * List<FeedbackPojo> currentDayHPData =
										 * null; List<FeedbackPojo>
										 * currentDayIgData = null;
										 * List<FeedbackPojo> CFData = null;
										 * List<FeedbackReportPojo> FRPList =
										 * null; FeedbackReportPojo FRP = null;
										 * 
										 * FeedbackReportPojo FRP4Counter =
										 * null; // get Data when Dept Level is
										 * 2 in Organization int cfp_Total = 0,
										 * cd_Total = 0, cd_Pending = 0,
										 * total_snooze = 0, CDR_Total = 0;
										 * deptLevel = report_level; if
										 * (deptLevel.equals("2")) { // getting
										 * the data for All the departments if
										 * (report_level.equals("2")) {
										 * deptComment = "All"; subject =
										 * "Patient Delight Score for All Dept. as on "
										 * +
										 * DateUtil.getCurrentDateIndianFormat()
										 * + " at " +
										 * DateUtil.getCurrentTime().substring
										 * (0, 5) + ""; StringBuilder sb = new
										 * StringBuilder(); sb.append(
										 * "select distinct dept.id,dept.deptName from feedback_type as feed"
										 * ); sb.append(
										 * " inner join department as dept on feed.dept_subdept=dept.id"
										 * );
										 * sb.append(" where feed.moduleName='FM'"
										 * );
										 * 
										 * List deptList = new
										 * HelpdeskUniversalHelper
										 * ().getData(sb.toString(),
										 * connection); if (deptList != null &&
										 * deptList.size() > 0) { FRPList = new
										 * ArrayList<FeedbackReportPojo>(); for
										 * (Iterator iterator2 =
										 * deptList.iterator();
										 * iterator2.hasNext();) { Object[]
										 * object2 = (Object[])
										 * iterator2.next(); FRP = new
										 * FeedbackReportPojo(); //
										 * System.out.println
										 * ("O index value is   "
										 * +object2[0].toString());
										 * CFCounter.clear();
										 * currentdayCounter.clear();
										 * 
										 * FRP.setCFP(0); FRP.setCDT(0);
										 * FRP.setCDP(0); FRP.setTS(0);
										 * FRP.setCDR(0); pc = 0; hc = 0; sc =
										 * 0; ic = 0; rc = 0; total = 0; rAc =
										 * 0; cfrAc = 0; cfpc = 0; cfhc = 0;
										 * cfsc = 0; cfic = 0; cfrc = 0; cftotal
										 * = 0; totalSnooze = 0;
										 * 
										 * currentdayCounter =
										 * FRH.getTicketCounters(report_level,
										 * type_report, true,
										 * object2[0].toString(), deptLevel,
										 * connection); CFCounter =
										 * FRH.getTicketCounters(report_level,
										 * type_report, false,
										 * object2[0].toString(), deptLevel,
										 * connection); if (currentdayCounter !=
										 * null && currentdayCounter.size() > 0)
										 * { // System.out.println(
										 * "Current day Counter for "
										 * +object2[1].toString()); for
										 * (Iterator iterator3 =
										 * currentdayCounter.iterator();
										 * iterator3.hasNext();) { Object[]
										 * object3 = (Object[])
										 * iterator3.next(); if
										 * (object3[1].toString
										 * ().equalsIgnoreCase("Pending")) { pc
										 * =
										 * Integer.parseInt(object3[0].toString
										 * ()); } else if
										 * (object3[1].toString().
										 * equalsIgnoreCase("Snooze")) { sc =
										 * Integer
										 * .parseInt(object3[0].toString()); }
										 * else if
										 * (object3[1].toString().equalsIgnoreCase
										 * ("High Priority")) { hc =
										 * Integer.parseInt
										 * (object3[0].toString()); } else if
										 * (object3
										 * [1].toString().equalsIgnoreCase
										 * ("Ignore")) { ic =
										 * Integer.parseInt(object3
										 * [0].toString()); } else if
										 * (object3[1]
										 * .toString().equalsIgnoreCase
										 * ("Resolved")) { rc =
										 * Integer.parseInt(
										 * object3[0].toString()); } } total =
										 * pc + sc + hc + ic + rc; } // Check
										 * Counter for Carry Forward if
										 * (CFCounter != null &&
										 * CFCounter.size() > 0) { //
										 * System.out.
										 * println("Carry Forward  Counter for "
										 * +object2[1].toString()); for
										 * (Iterator iterator4 =
										 * CFCounter.iterator();
										 * iterator4.hasNext();) { Object[]
										 * object4 = (Object[])
										 * iterator4.next(); if
										 * (object4[1].toString
										 * ().equalsIgnoreCase("Pending")) {
										 * cfpc =
										 * Integer.parseInt(object4[0].toString
										 * ()); } else if
										 * (object4[1].toString().
										 * equalsIgnoreCase("Snooze")) { cfsc =
										 * Integer
										 * .parseInt(object4[0].toString()); }
										 * else if
										 * (object4[1].toString().equalsIgnoreCase
										 * ("High Priority")) { cfhc =
										 * Integer.parseInt
										 * (object4[0].toString()); } } cftotal
										 * = cfpc + cfhc; } totalSnooze = sc +
										 * cfsc; // int //
										 * cfp_Total=0,cd_Total=0
										 * ,cd_Pending,total_snooze
										 * =0,CDR_Total=0; if ((CFCounter !=
										 * null && CFCounter.size() > 0) ||
										 * (currentdayCounter != null &&
										 * currentdayCounter.size() > 0)) { //
										 * System
										 * .out.println("Final Block "+object2
										 * [1].toString());
										 * FRP.setDeptName(object2
										 * [1].toString()); FRP.setCFP(cftotal);
										 * FRP.setCDT(total); FRP.setCDP(pc);
										 * FRP.setTS(totalSnooze);
										 * FRP.setCDR(rc); FRPList.add(FRP);
										 * cfp_Total = cfp_Total + cftotal;
										 * cd_Total = cd_Total + total;
										 * cd_Pending = cd_Pending + pc;
										 * total_snooze = total_snooze +
										 * totalSnooze; CDR_Total = CDR_Total +
										 * rc; } } }
										 * 
										 * currentDayResolvedData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Resolved", subdept_dept, deptLevel,
										 * connection); currentDayPendingData =
										 * FRH
										 * .getTicketDataforReport(report_level,
										 * type_report, true, "Pending",
										 * subdept_dept, deptLevel, connection);
										 * currentDaySnoozeData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Snooze", subdept_dept, deptLevel,
										 * connection); currentDayHPData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "High Priority", subdept_dept,
										 * deptLevel, connection);
										 * currentDayIgData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Ignore", subdept_dept, deptLevel,
										 * connection); CFData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, false,
										 * "", subdept_dept, deptLevel,
										 * connection);
										 * 
										 * int pp = 0, pn = 0, All = 0, NR = 0,
										 * Neg = 0;
										 * 
										 * // Code For First Block(Paper Mode)
										 * List paperData = new
										 * FeedbackHelper().
										 * getPaperData(type_report,
										 * connection);
										 * 
										 * if (paperData != null &&
										 * paperData.size() > 0) { for (Iterator
										 * iterator2 = paperData.iterator();
										 * iterator2.hasNext();) { Object[]
										 * object3 = (Object[])
										 * iterator2.next(); if (object3[0] !=
										 * null &&
										 * !object3[0].toString().equals("") &&
										 * object3
										 * [0].toString().equalsIgnoreCase
										 * ("No")) { pn =
										 * Integer.parseInt(object3
										 * [1].toString()); } else if
										 * (object3[0] != null &&
										 * !object3[0].toString().equals("") &&
										 * object3
										 * [0].toString().equalsIgnoreCase
										 * ("Yes")) { pp =
										 * Integer.parseInt(object3
										 * [1].toString()); } } }
										 * 
										 * // SMS Mode // For Total Feedback
										 * Seek Counters // All = new //
										 * FeedbackHelper
										 * ().getSMSData(type_report, "A", //
										 * connection); All = new
										 * FeedbackHelper(
										 * ).getSMSDataForTotalSeek(type_report,
										 * connection); // NR = new //
										 * FeedbackHelper
										 * ().getSMSData(type_report, // "NR",
										 * connection); int pre = 0; pre = new
										 * FeedbackHelper
										 * ().getRevertedSMSDetailsPrevious
										 * (true, false, type_report,
										 * connection); int current = 0; current
										 * = new FeedbackHelper().
										 * getRevertedSMSDetailsCurrent(true,
										 * false, type_report, connection); NR =
										 * pre + current; pre = 0; current = 0;
										 * pre = new FeedbackHelper().
										 * getRevertedSMSDetailsPrevious(false,
										 * true, type_report, connection);
										 * current = new FeedbackHelper().
										 * getRevertedSMSDetailsCurrent(false,
										 * true, type_report, connection); //
										 * Neg=new // FeedbackHelper().
										 * getRevertedSMSDetailsPrevious(false,
										 * // true, type_report, connection);
										 * Neg = pre + current;
										 * 
										 * FRP4Counter = new
										 * FeedbackReportPojo();
										 * FRP4Counter.setPt(pp + pn);
										 * FRP4Counter.setPp(pp);
										 * FRP4Counter.setPn(pn);
										 * FRP4Counter.setSt(All);
										 * FRP4Counter.setSnr(NR);
										 * FRP4Counter.setSn(Neg); } // getting
										 * the data for specific department else
										 * if (report_level.equals("1")) {
										 * deptComment = department; subject =
										 * "Patient Delight Score for " +
										 * deptComment + " as on " +
										 * DateUtil.getCurrentDateIndianFormat()
										 * + " at " +
										 * DateUtil.getCurrentTime().substring
										 * (0, 5) + ""; currentdayCounter =
										 * FRH.getTicketCounters(report_level,
										 * type_report, true, subdept_dept,
										 * deptLevel, connection); CFCounter =
										 * FRH.getTicketCounters(report_level,
										 * type_report, false, subdept_dept,
										 * deptLevel, connection);
										 * currentDayResolvedData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Resolved", subdept_dept, deptLevel,
										 * connection); currentDayPendingData =
										 * FRH
										 * .getTicketDataforReport(report_level,
										 * type_report, true, "Pending",
										 * subdept_dept, deptLevel, connection);
										 * currentDaySnoozeData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Snooze", subdept_dept, deptLevel,
										 * connection); currentDayHPData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "High Priority", subdept_dept,
										 * deptLevel, connection);
										 * currentDayIgData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, true,
										 * "Ignore", subdept_dept, deptLevel,
										 * connection); CFData =
										 * FRH.getTicketDataforReport
										 * (report_level, type_report, false,
										 * "", subdept_dept, deptLevel,
										 * connection); } // Check Counter for
										 * Current day if (currentdayCounter !=
										 * null && currentdayCounter.size() > 0)
										 * { for (Iterator iterator2 =
										 * currentdayCounter.iterator();
										 * iterator2.hasNext();) { Object[]
										 * object2 = (Object[])
										 * iterator2.next(); if
										 * (object2[1].toString
										 * ().equalsIgnoreCase("Pending")) { pc
										 * =
										 * Integer.parseInt(object2[0].toString
										 * ()); } else if
										 * (object2[1].toString().
										 * equalsIgnoreCase("Snooze")) { sc =
										 * Integer
										 * .parseInt(object2[0].toString()); }
										 * else if
										 * (object2[1].toString().equalsIgnoreCase
										 * ("High Priority")) { hc =
										 * Integer.parseInt
										 * (object2[0].toString()); } else if
										 * (object2
										 * [1].toString().equalsIgnoreCase
										 * ("Ignore")) { ic =
										 * Integer.parseInt(object2
										 * [0].toString()); } else if
										 * (object2[1]
										 * .toString().equalsIgnoreCase
										 * ("Resolved")) { rc =
										 * Integer.parseInt(
										 * object2[0].toString()); } } total =
										 * pc + sc + hc + ic + rc; } // Check
										 * Counter for Carry Forward if
										 * (CFCounter != null &&
										 * CFCounter.size() > 0) { for (Iterator
										 * iterator2 = CFCounter.iterator();
										 * iterator2.hasNext();) { Object[]
										 * object3 = (Object[])
										 * iterator2.next(); if
										 * (object3[1].toString
										 * ().equalsIgnoreCase("Pending")) {
										 * cfpc =
										 * Integer.parseInt(object3[0].toString
										 * ()); } else if
										 * (object3[1].toString().
										 * equalsIgnoreCase("Snooze")) { cfsc =
										 * Integer
										 * .parseInt(object3[0].toString()); }
										 * else if
										 * (object3[1].toString().equalsIgnoreCase
										 * ("High Priority")) { cfhc =
										 * Integer.parseInt
										 * (object3[0].toString()); } } cftotal
										 * = cfpc + cfhc; } totalSnooze = sc +
										 * cfsc; }
										 * 
										 * String report_sms = ""; if
										 * (report_level != null &&
										 * !report_level.equals("") &&
										 * report_level.equals("1")) {
										 * report_sms = "Dear " + empname +
										 * ", For " + deptComment +
										 * " Ticket Status as on " +
										 * DateUtil.getCurrentDateIndianFormat()
										 * + ": Pending: " + pc +
										 * ", C/F Pending: " + cftotal +
										 * ", Snooze: " + totalSnooze +
										 * ", Ignore: " + ic + ", Resolved: " +
										 * rc + "."; } else if (report_level !=
										 * null && !report_level.equals("") &&
										 * report_level.equals("2")) {
										 * report_sms = "Dear " + empname +
										 * ", For " + deptComment +
										 * " Ticket Status as on " +
										 * DateUtil.getCurrentDateIndianFormat()
										 * + ": Pending: " + cd_Pending +
										 * ", C/F Pending: " + cfp_Total +
										 * ", Snooze: " + total_snooze +
										 * ", Ignore: " + ic + ", Resolved: " +
										 * CDR_Total + "."; }
										 * 
										 * if (mobno != null && mobno != "" &&
										 * mobno != "NA" && mobno.length() == 10
										 * && (mobno.startsWith("9") ||
										 * mobno.startsWith("8") ||
										 * mobno.startsWith("7"))) {
										 * CH.addMessage(empname, department,
										 * mobno, report_sms, subject,
										 * "Pending", "0", "FM", connection); }
										 * String filepath = null; if
										 * ((currentDayResolvedData != null &&
										 * currentDayResolvedData.size() > 0) ||
										 * (currentDayPendingData != null &&
										 * currentDayPendingData.size() > 0) ||
										 * (currentDaySnoozeData != null &&
										 * currentDaySnoozeData.size() > 0) ||
										 * (currentDayHPData != null &&
										 * currentDayHPData.size() > 0) ||
										 * (currentDayIgData != null &&
										 * currentDayIgData.size() > 0) ||
										 * (CFData != null && CFData.size() >
										 * 0)) { // System.out.println(
										 * "Inside If for write Excel File   ");
										 * filepath = new
										 * WriteFeedbackData().writeDataInExcel
										 * (pc, hc, sc, ic, rc, total, cfpc,
										 * cfsc, cfhc, cftotal, totalSnooze,
										 * report_level, FRPList, deptComment,
										 * type_report, FRP4Counter, empname,
										 * currentDayResolvedData,
										 * currentDayPendingData,
										 * currentDaySnoozeData,
										 * currentDayHPData, currentDayIgData,
										 * CFData); // System.out.println(
										 * "Generated File path   >>>>>>>>>   "
										 * +filepath); String mailtext =
										 * FRH.getConfigMailForReport(pc, hc,
										 * sc, ic, rc, total, cfpc, cfsc, cfhc,
										 * cftotal, totalSnooze, report_level,
										 * FRPList, deptComment, type_report,
										 * FRP4Counter, empname,
										 * currentDayResolvedData,
										 * currentDayPendingData,
										 * currentDaySnoozeData,
										 * currentDayHPData, currentDayIgData,
										 * CFData, IPAddress, cfp_Total,
										 * cd_Total, cd_Pending, total_snooze,
										 * CDR_Total); CH.addMail(empname,
										 * department, emailid, subject,
										 * mailtext, "Report", "Pending", "0",
										 * filepath, "FM", connection); }
										 * 
										 * }
										 */
										/*
										 * else if (module != null &&
										 * !module.equals("") &&
										 * module.equalsIgnoreCase("compliance")
										 * && updateReport) {
										 * ComplianceServiceHelper srvcHelper =
										 * new ComplianceServiceHelper();
										 * CommunicationHelper cmnHelper = new
										 * CommunicationHelper();
										 * CommonOperInterface cbt = new
										 * CommonConFactory().createInterface();
										 * StringBuilder mailtext = new
										 * StringBuilder(""); List validData =
										 * srvcHelper.getReportData(connection,
										 * report_level, type_report, null);
										 * List<ComplianceDashboardBean>
										 * listForActionDataMail = new
										 * ArrayList<ComplianceDashboardBean>();
										 * List<ComplianceDashboardBean>
										 * listForPendingDataMail = new
										 * ArrayList<ComplianceDashboardBean>();
										 * 
										 * if (validData != null &&
										 * validData.size() > 0) {
										 * ComplianceDashboardBean obj = null;
										 * String reminderFor = null; for
										 * (Iterator iterator2 =
										 * validData.iterator();
										 * iterator2.hasNext();) { obj = new
										 * ComplianceDashboardBean(); Object[]
										 * objects = (Object[])
										 * iterator2.next();
										 * 
										 * if (objects[0] != null) { List
										 * lastCompDeatil =
										 * srvcHelper.getLastCompActionData
										 * (objects[0].toString(), connection);
										 * if (lastCompDeatil != null &&
										 * lastCompDeatil.size() > 0) { for
										 * (Iterator iterator1 =
										 * lastCompDeatil.iterator();
										 * iterator1.hasNext();) { Object[]
										 * object1 = (Object[])
										 * iterator1.next(); if (object1[0] !=
										 * null) {
										 * obj.setActionTakenOn(DateUtil.
										 * convertDateToIndianFormat
										 * (object1[0].toString())); } else {
										 * obj.setActionTakenOn("NA"); }
										 * 
										 * if (object1[1] != null) {
										 * obj.setActionBy
										 * (object1[1].toString()); } else {
										 * obj.setActionBy("NA"); }
										 * 
										 * if (object1[2] != null) {
										 * obj.setLastActionComment
										 * (object1[2].toString()); } else {
										 * obj.setLastActionComment("NA"); } } }
										 * }
										 * 
										 * if (objects[1] != null)
										 * obj.setFrequency(new
										 * ComplianceReminderHelper
										 * ().getFrequencyName
										 * (objects[1].toString())); else
										 * obj.setFrequency("NA");
										 * 
										 * if (objects[2] != null)
										 * obj.setTaskBrief
										 * (objects[2].toString()); else
										 * obj.setTaskBrief("NA");
										 * 
										 * if (objects[4] != null)
										 * obj.setDueDate
										 * (DateUtil.convertDateToIndianFormat
										 * (objects[4].toString())); else
										 * obj.setDueDate("NA");
										 * 
										 * if (objects[5] != null)
										 * obj.setActionStatus
										 * (objects[5].toString()); else
										 * obj.setActionStatus("NA");
										 * 
										 * if (objects[6] != null)
										 * obj.setTaskName
										 * (objects[6].toString()); else
										 * obj.setTaskName("NA");
										 * 
										 * if (objects[7] != null)
										 * obj.setTaskType
										 * (objects[7].toString()); else
										 * obj.setTaskType("NA");
										 * 
										 * if (objects[8] != null)
										 * obj.setDepartName
										 * (objects[8].toString()); else
										 * obj.setDepartName("NA");
										 * 
										 * if (objects[3] != null) reminderFor =
										 * objects[3].toString(); else
										 * reminderFor = "NA";
										 * 
										 * String contactId =
										 * reminderFor.replace("#",
										 * ",").substring(0,
										 * (reminderFor.toString().length() -
										 * 1));
										 * 
										 * StringBuilder builder = new
										 * StringBuilder(); builder.append(
										 * "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp "
										 * ); builder.append(
										 * "INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id "
										 * ); builder.append(
										 * "INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN("
										 * + contactId + ")"); List data1 = new
										 * createTable
										 * ().executeAllSelectQuery(builder
										 * .toString(), connection);
										 * 
										 * if (data1 != null && data1.size() >
										 * 0) { String mobileNo = null, emailId
										 * = null, empName = null; String
										 * mappedTeam = null; StringBuilder str
										 * = new StringBuilder(); for (Iterator
										 * iterator3 = data1.iterator();
										 * iterator3.hasNext();) { Object
										 * object2[] = (Object[])
										 * iterator3.next(); for (Iterator
										 * iterator4 = data1.iterator();
										 * iterator4.hasNext();) { Object
										 * object3[] = (Object[])
										 * iterator4.next();
										 * 
										 * str.append(object3[2].toString() +
										 * ", "); } if (str != null &&
										 * str.toString().length() > 0) {
										 * mappedTeam =
										 * str.toString().substring(0,
										 * str.toString().length() - 2); } }
										 * obj.setMappedTeam(mappedTeam); } else
										 * obj.setMappedTeam("NA");
										 * 
										 * listForPendingDataMail.add(obj); } }
										 * 
										 * List todayActionData =
										 * srvcHelper.getTodayActionData
										 * (connection, report_level,
										 * type_report, null);
										 * 
										 * if (todayActionData != null &&
										 * todayActionData.size() > 0) {
										 * ComplianceDashboardBean obj = null;
										 * String reminderFor = null; for
										 * (Iterator iterator5 =
										 * todayActionData.iterator();
										 * iterator5.hasNext();) { obj = new
										 * ComplianceDashboardBean(); Object[]
										 * object4 = (Object[])
										 * iterator5.next();
										 * 
										 * if (object4[1] != null)
										 * obj.setFrequency(new
										 * ComplianceReminderHelper
										 * ().getFrequencyName
										 * (object4[1].toString())); else
										 * obj.setFrequency("NA");
										 * 
										 * if (object4[2] != null)
										 * obj.setTaskBrief
										 * (object4[2].toString()); else
										 * obj.setTaskBrief("NA");
										 * 
										 * if (object4[3] != null) reminderFor =
										 * object4[3].toString(); else
										 * reminderFor = "NA";
										 * 
										 * if (object4[4] != null)
										 * obj.setDueDate
										 * (DateUtil.convertDateToIndianFormat
										 * (object4[4].toString())); else
										 * obj.setDueDate("NA");
										 * 
										 * if (object4[5] != null) { if
										 * (object4[
										 * 5].toString().equalsIgnoreCase
										 * ("Recurring")) {
										 * obj.setActionStatus("Done & " +
										 * object4[5].toString()); } else {
										 * obj.setActionStatus
										 * (object4[5].toString()); } } else {
										 * obj.setActionStatus("NA"); }
										 * 
										 * if (object4[6] != null)
										 * obj.setTaskName
										 * (object4[6].toString()); else
										 * obj.setTaskName("NA");
										 * 
										 * if (object4[7] != null)
										 * obj.setTaskType
										 * (object4[7].toString()); else
										 * obj.setTaskType("NA");
										 * 
										 * if (object4[8] != null)
										 * obj.setDepartName
										 * (object4[8].toString()); else
										 * obj.setDepartName("NA");
										 * 
										 * if (object4[9] != null)
										 * obj.setEmpId(object4[9].toString());
										 * else obj.setEmpId("NA");
										 * 
										 * if (object4[10] != null)
										 * obj.setComment
										 * (object4[10].toString()); else
										 * obj.setComment("NA");
										 * 
										 * String contactId =
										 * reminderFor.replace("#",
										 * ",").substring(0,
										 * (reminderFor.toString().length() -
										 * 1));
										 * 
										 * StringBuilder builder = new
										 * StringBuilder(); builder.append(
										 * "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp "
										 * ); builder.append(
										 * "INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id "
										 * ); builder.append(
										 * "INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN("
										 * + contactId + ")"); List data1 = new
										 * createTable
										 * ().executeAllSelectQuery(builder
										 * .toString(), connection);
										 * 
										 * if (data1 != null && data1.size() >
										 * 0) { String mobileNo = null, emailId
										 * = null, empName = null; String
										 * mappedTeam = null; StringBuilder str
										 * = new StringBuilder(); for (Iterator
										 * iterator6 = data1.iterator();
										 * iterator6.hasNext();) { Object
										 * object6[] = (Object[])
										 * iterator6.next(); for (Iterator
										 * iterator7 = data1.iterator();
										 * iterator7.hasNext();) { Object
										 * object7[] = (Object[])
										 * iterator7.next();
										 * 
										 * str.append(object7[2].toString() +
										 * ", "); } if (str != null &&
										 * str.toString().length() > 0) {
										 * mappedTeam =
										 * str.toString().substring(0,
										 * str.toString().length() - 2); } }
										 * obj.setMappedTeam(mappedTeam); } else
										 * obj.setMappedTeam("NA");
										 * 
										 * listForActionDataMail.add(obj); } }
										 * if (listForPendingDataMail != null ||
										 * listForActionDataMail != null ||
										 * listForActionDataMail.size() > 0 ||
										 * listForPendingDataMail.size() > 0) {
										 * String filePath = new
										 * DailyCompReportExcelWrite4Attach
										 * ().writeDataInExcel
										 * (listForActionDataMail,
										 * listForPendingDataMail, null,
										 * type_report); String mailSubject =
										 * "Today Compliance Status"; String
										 * mailText = new
										 * ComplianceReminderHelper
										 * ().configReportComp
										 * (listForPendingDataMail,
										 * listForActionDataMail, empname,
										 * mailSubject);
										 * cmnHelper.addMail(empname,
										 * department, emailid, mailSubject,
										 * mailText, "", "Pending", "0",
										 * filePath, "Compliance", connection);
										 * } else { String mailSubject =
										 * "Today There Are No Compliance";
										 * String mailText = new
										 * ComplianceReminderHelper
										 * ().configReportComp
										 * (listForPendingDataMail,
										 * listForActionDataMail, empname,
										 * mailSubject);
										 * cmnHelper.addMail(empname,
										 * department, emailid, mailSubject,
										 * mailText, "", "Pending", "0", "",
										 * "Compliance", connection); } }
										 */
									}
							}
						Runtime rt = Runtime.getRuntime();
						rt.gc();
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
			}

		public String getDataInQuotes(String data)
			{
				StringBuilder builder = new StringBuilder();
				if (data != null)
					{
						String str[] = data.split(", ");
						for (int i = 0; i < str.length; i++)
							{
								builder.append("'" + str[i] + "',");
							}
						builder = builder.deleteCharAt(builder.lastIndexOf(","));
					}
				return builder.toString();
			}
	}