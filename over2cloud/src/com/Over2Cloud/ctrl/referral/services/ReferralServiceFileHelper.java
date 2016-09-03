package com.Over2Cloud.ctrl.referral.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ReferralServiceFileHelper
{

	@SuppressWarnings({ "rawtypes" })
	public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			String id = null, ticketNo = null, uhid = null, roomNo = null, escalation_date = null, escalation_time = null, status = null, priority = null, nursingUnit = null, level = null, ref_to = null , refTo = null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT rd.id,rd.ticket_no,rd.escalate_date,rd.escalate_time,rd.status,rd.priority,rd.level,rpd.uhid,rpd.bed,rpd.nursing_unit,rd.ref_to,rd1.name ");
			sb.append(" FROM referral_data AS rd ");
			sb.append(" INNER JOIN referral_patient_data AS rpd ON rpd.id=rd.uhid ");
			sb.append("INNER JOIN referral_doctor AS rd1 ON rd.ref_to=rd1.id ");
			sb.append(" WHERE rd.status IN ('Not Informed')");
			sb.append(" AND escalate_date<='" + DateUtil.getCurrentDateUSFormat() + "' and level!='Level5' ");
			System.out.println("vvvvvv   "+sb);
			List data4escalate = HUH.getData(sb.toString(), connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}

					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticketNo = object[1].toString();
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						escalation_date = object[2].toString();
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						escalation_time = object[3].toString();
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						status = object[4].toString();
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						priority = object[5].toString();
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						level = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						uhid = object[7].toString();
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						roomNo = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						nursingUnit = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						ref_to = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						refTo = object[11].toString();
					}

					// Escalation for OCC level
					/*
					 * if (status.equalsIgnoreCase("Not Informed") ||
					 * status.equalsIgnoreCase("Informed")) {
					 */
					boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
					if (escnextlvl)
					{
						String subDept = null;
						if (status.equalsIgnoreCase("Not Informed"))
						{
							subDept = "17";
						} else
						{
							subDept = getSubDeptId(ref_to, HUH, connection);
						}
						String calculationLevel = String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1);
						//Get escalation person ids
						String data = fetchDataForEscalation(subDept, priority, calculationLevel, connection);
						//System.out.println("vvvvvvvvvvvvvv  "+level);
						if (level != null && !level.equals("") && !level.equals("0") && data != null)
						{
							String empDetails=null;

							if (calculationLevel != null)
							{
								empDetails = sendMsg4Esc(data, "CRF", refTo, nursingUnit, roomNo, uhid, escalation_date, escalation_time, "L" + level.substring(level.length() - 1) + "toL" + calculationLevel, priority, CH, connection);
							}

							//Get next escalation date time
							String nxtEsc = escTime(priority, "tat" + (Integer.parseInt(calculationLevel) + 1), subDept, "l" + calculationLevel, connection);
							if (empDetails!=null && !"".equalsIgnoreCase(empDetails))
							{
								sb.setLength(0);
								sb.append(" UPDATE referral_data SET ");
								if (nxtEsc != null)
								{
									sb.append(" escalate_date='" + nxtEsc.split("#")[0] + "',");
									sb.append(" escalate_time='" + nxtEsc.split("#")[1] + "',");
								}
								sb.append(" level='Level" + calculationLevel + "'");
								sb.append(" WHERE id=" + id + "");
								//Update in data table
								int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
								if (chkUpdate > 0)
								{
									Map<String, Object> wherClause = new HashMap<String, Object>();
									// update in history table
									wherClause.put("rid", id);
									wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
									wherClause.put("action_time", DateUtil.getCurrentTime());
									wherClause.put("esc_level", "Level" + calculationLevel);
									wherClause.put("status", "Escalate");
									wherClause.put("escalate_to", empDetails);

									if (wherClause != null && wherClause.size() > 0)
									{
										CommonOperInterface cbt = new CommonConFactory().createInterface();
										InsertDataTable ob = new InsertDataTable();
										List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
										for (Map.Entry<String, Object> entry : wherClause.entrySet())
										{
											ob = new InsertDataTable();
											ob.setColName(entry.getKey());
											ob.setDataName(entry.getValue().toString());
											insertData.add(ob);
										}
										cbt.insertIntoTable("referral_data_history", insertData, connection);
										insertData.clear();
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String getSubDeptId(String ref_to, HelpdeskUniversalHelper HUH, SessionFactory connection)
	{
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT subDept.id FROM subdepartment AS subDept ");
			query.append(" INNER JOIN referral_doctor AS rd ON rd.spec=subDept.subdeptname ");
			query.append(" WHERE rd.id=" + ref_to);
			List list = HUH.getData(query.toString(), connection);
			if (list != null && list.size() > 0)
			{
				return list.get(0).toString();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private String fetchDataForEscalation(String subDept, String priority, String level, SessionFactory connection)
	{
		if (subDept != null)
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT l" + level + ",tat" + level + " FROM referral_escalation_detail WHERE esc_sub_dept='" + subDept + "' AND priority='" + priority + "' AND escalation='Yes' ");
			//System.out.println(query);
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						return "" + object[0] + ", " + object[1];
					}
				}
			}
		}
		return null;
	}

	// send sms for escalate
	private String sendMsg4Esc(String ids, String module, String refTo, String nursingUnit, String roomNo, String uhid, String escalation_date, String escalation_time, String lAlert, String priority, CommunicationHelper CH, SessionFactory connection)
	{
		String details="";
		try
		{
			String empIds[] = ids.split("#");
			for (int i = 0; i < empIds.length - 1; i++)
			{
				String escHolderDetails = escHolderNameMob(empIds[i], module, connection);
				
				String empName =null;
				String empMob=null;
				if(escHolderDetails!=null)
				{
					empName = escHolderDetails.split("#")[0];
					empMob = escHolderDetails.split("#")[1];
				}
				if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
				{
					//String levelMsg = lAlert + " Escalation Alert: Ticket No: + refTo +  " + ticketNo + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + ", " + escalation_time + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Priority: " + priority + ".";
					String levelMsg = lAlert + " Escalation Alert: UHID: " + uhid +", Location: " + nursingUnit + "/ " + roomNo + ", Priority: " + priority + ",Assign To:"+refTo+",Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "-" + escalation_time ;
					CH.addMessage(empName, "", empMob, levelMsg, refTo, "Pending", "0", module, connection);
					//Details will kept in history to show escalation person on history view
					details=details+empName+",";
				}
			}
			int i=details.lastIndexOf(",");
			if(i>0)
			{
				details=details.substring(0, i);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return details;
	}


	@SuppressWarnings("rawtypes")
	private String escHolderNameMob(String id, String module, SessionFactory connection)
	{
		String empDetails = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id='" + id + "' AND cc.moduleName='" + module + "'", connection);
			//System.out.println("emp query::::SELECT emp.empName, emp.mobOne FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id='" + id + "' AND cc.moduleName='" + module + "'");
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empDetails;
	}

	@SuppressWarnings("rawtypes")
	public String escTime(String priority, String ltol, String subDept, String escType, SessionFactory connection)
	{
		String esctm = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append("SELECT  " + ltol + ", esc_sub_dept FROM referral_escalation_detail WHERE esc_sub_dept='" + subDept + "' AND priority='" + priority + "'");

			List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (empData1 != null && empData1.size() > 0)
			{

				for (Iterator it = empData1.iterator(); it.hasNext();)
				{
					Object[] object = (Object[]) it.next();
					if (object[0] != null && !"".equalsIgnoreCase(object[0].toString()))
					{
						esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object[0].toString(), "Y");
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return esctm;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public void snoozeToPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			String id = null, ticketNo = null, uhid = null, roomNo = null, escalation_date = null, escalation_time = null, status = null, priority = null, nursingUnit = null, level = null, parkedTill = null, actionDate = null, actionTime = null,hisId=null;
			List list = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT rd.id,rd.ticket_no,rd.escalate_date,rd.escalate_time,rd.status,rd.priority,rd.level,rpd.uhid,rpd.bed,rpd.nursing_unit,rdh.sn_upto_date,rdh.sn_upto_time,rdh.action_date,rdh.action_time,rdh.id AS hisId ");
			query.append(" FROM referral_data AS rd ");
			query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=rd.id ");
			query.append(" INNER JOIN referral_patient_data AS rpd ON rpd.id=rd.uhid ");
			query.append(" WHERE rd.status IN ('Snooze','Snooze-I') AND rd.status=rdh.status ");
			query.append(" AND rdh.sn_upto_date<='" + DateUtil.getCurrentDateUSFormat() + "' AND action_status IS NULL ");
			//System.out.println("snooze query:::::" + query);
			List data4escalate = HUH.getData(query.toString(), connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}

					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticketNo = object[1].toString();
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						escalation_date = object[2].toString();
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						escalation_time = object[3].toString();
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						status = object[4].toString();
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						priority = object[5].toString();
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						level = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						uhid = object[7].toString();
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						roomNo = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						nursingUnit = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals("") && object[11] != null)
					{
						parkedTill = object[10].toString() + " " + object[11].toString();
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						actionDate = object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						actionTime = object[13].toString();
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						hisId = object[14].toString();
					}
					// for snooze referral activate to pending

					boolean escnextlvl = DateUtil.time_update(parkedTill.split(" ")[0].toString(), parkedTill.split(" ")[1].toString());
				//	System.out.println(escnextlvl + "parkedTill" + parkedTill);
					if (escnextlvl)
					{
						// Chk is the parked time and action time (is parked
						// //time is grater than action time)
						boolean isActionEscDiff = DateUtil.comparebetweenTwodateTime(actionDate, actionTime, parkedTill.split(" ")[0].toString(), parkedTill.split(" ")[1].toString());
					//	System.out.println("isActionEscDiff" + isActionEscDiff);
						if (isActionEscDiff)
						{ // calculate the working time till parked
							String diff_time = DateUtil.time_difference(actionDate, actionTime, escalation_date, escalation_time);
				//			System.out.println("diff_time" + diff_time);
							// calculate the when parked active String
							String new_escalation_datetime = DateUtil.newdate_AfterEscTime(parkedTill.split(" ")[0].toString(), parkedTill.split(" ")[1].toString(), diff_time, "Y");
					//		System.out.println("new_escalation_datetime " + new_escalation_datetime);

							if (new_escalation_datetime.length() > 0 && !new_escalation_datetime.split("#")[0].toString().equalsIgnoreCase("0000-00-00") && !new_escalation_datetime.split("#")[1].toString().equalsIgnoreCase("00:00"))
							{
								query.setLength(0);
								query.append(" UPDATE referral_data SET  ");
								query.append(" escalate_date='" + new_escalation_datetime.split("#")[0] + "',");
								query.append(" escalate_time='" + new_escalation_datetime.split("#")[1] + "',");
								if (status.equalsIgnoreCase("Snooze"))
								{
									query.append(" status='Not Informed' ");
								} else
								{
									query.append(" status='Informed' ");
								}
								query.append(" WHERE id=" + id + "");
					//			System.out.println("updae query:::"+query);
								int chkUpdate = new createTable().executeAllUpdateQuery(query.toString(), connection);

								if (chkUpdate > 0)
								{
									new createTable().executeAllUpdateQuery("UPDATE referral_data_history SET action_status='1' WHERE id="+hisId, connection);
									
									Map<String, Object> wherClause = new HashMap<String, Object>();
									wherClause.put("rid", id);
									wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
									wherClause.put("action_time", DateUtil.getCurrentTime());
									wherClause.put("comments", "Parked Activate");
									wherClause.put("esc_level", level);
									if (status.equalsIgnoreCase("Snooze"))
									{
										wherClause.put("status", "Not Informed");
									} else
									{
										wherClause.put("status", "Informed");
									}

									if (wherClause != null && wherClause.size() > 0)
									{
										CommonOperInterface cbt = new CommonConFactory().createInterface();
										InsertDataTable ob = new InsertDataTable();
										List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
										for (Map.Entry<String, Object> entry : wherClause.entrySet())
										{
											ob = new InsertDataTable();
											ob.setColName(entry.getKey());
											ob.setDataName(entry.getValue().toString());
											insertData.add(ob);
										}
										boolean updateFeed = cbt.insertIntoTable("referral_data_history", insertData, connection);
										//sendMsg4ParkedActive(level, "CRF", ticketNo, nursingUnit, roomNo, uhid, new_escalation_datetime.split("#")[0], new_escalation_datetime.split("#")[1], priority, CH, connection);
										insertData.clear();
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Parked sms send
	/*private int sendMsg4ParkedActive(String level, String module, String ticketNo, String nursingUnit, String roomNo, String uhid, String escalation_date, String escalation_time, String priority, CommunicationHelper CH, SessionFactory connection)
	{
		int flag = 0;
		String empName = "";
		String empMob = "";
		try
		{
			// send assign ORDer sms to holder if (empMob.length() > 9) {
			if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7"))
			{
				String levelMsg = "Parked To Assign Alert: Ticket No: " + ticketNo + ", To Be Closed By: " + escalation_date + " - " + escalation_time + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Priority: " + priority + ".";
				boolean isSent = CH.addMessage(empName, "", empMob, levelMsg, ticketNo, "Pending", "0", "CRF", connection);
				if (isSent)
				{
					System.out.println("sms sent to " + empName + " empMob: " + empMob + " ticketNo: " + ticketNo);
				}
			}
			flag = 1;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}*/
}
