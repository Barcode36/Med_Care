package com.Over2Cloud.ctrl.criticalPatient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class CriticalServiceHelper
{
	/*
	 * This file is for escalation of order for occ level and department level
	 * created by: Manab 13-07-2015
	 */

	String id = null;
	String orderid = null;
	String orderType = null;
	String uhid = null;
	String roomNo = null;
	String assignMchn = null;
	String department = null;
	String assignTech = null;
	String escalation_date = null;
	String escalation_time = null;
	String status = null;
	String priority = null;
	String nursingUnit = null;
	String level = null;
	String parkedTill = null;
	String orderName = null;
	String actionDate = null;
	String actionTime = null;
	String parkedTillTime = null;
	String reource=null;
String	ticket_no, patient_name, patient_status, doc_name ,specialty,patient_id;



	public String getMeAppendedName(List escalateTo){
		String temp="";
		try{
			
			int count=0;
			for (Iterator iterator3 = escalateTo.iterator(); iterator3.hasNext();)
			{
				Object object2 = (Object) iterator3.next();
				if(object2!=null)
				{
					if(count<escalateTo.size())
					{
						temp=temp+object2.toString().split("#")[1]+", ";
					}else
					{
						temp=temp+object2.toString().split("#")[1];
					}
					count++;
				}
				
			}
			int i=temp.lastIndexOf(",");
			if(i>0)
			{
				temp=temp.substring(0,i);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return "NA";
		}
		return temp;
	}
	public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			String cartName=null, nxtEsc=null;
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT cd.id,cd.patient_id,cd.ticket_no,cpd.patient_name,cpd.patient_status,cpd.addmision_doc_name,cpd.nursing_unit, ");
			sb.append(" cpd.specialty,cd.uhid,cd.escalate_date,cd.escalate_time,ty.test_type,cd.status,cd.level,cd.dept ");
			sb.append(" from critical_data as cd ");
			sb.append(" LEFT JOIN critical_patient_data AS cpd ON cd.patient_id=cpd.id");
			sb.append(" LEFT JOIN test_type AS ty ON ty.id=cd.test_type ");
			sb.append(" WHERE (cd.status='Not Informed' || cd.status ='Informed-P' )  and cpd.patient_status in ('IPD', 'OPD') ");
			sb.append(" AND cd.escalate_date<='" + DateUtil.getCurrentDateUSFormat() + "' and cd.level!='level6'  ");
			//System.out.println(" occ Escalation query "+sb);
			List data4escalate = HUH.getData(sb.toString(), connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{

				for (@SuppressWarnings("rawtypes")
				Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						patient_id = object[1].toString();
					}
					

					if (object[1] != null && !object[1].toString().equals(""))
					{
						orderid = object[1].toString();
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						ticket_no=object[2].toString();
					//	orderType = object[2].toString();
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						patient_name= object[3].toString();
						//uhid = object[3].toString();
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						patient_status=object[4].toString();
						//roomNo = object[4].toString();
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						doc_name= object[5].toString();
						//assignMchn = object[5].toString();
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						nursingUnit=object[6].toString();
						//department = object[6].toString();
					}
					

					if (object[7] != null && !object[7].toString().equals(""))
					{
						specialty=object[7].toString();
						//assignTech = object[7].toString();
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						uhid= object[8].toString();
						//escalation_date = object[8].toString();
					}

					if (object[9] != null && !object[9].toString().equals(""))
					{
						escalation_date=object[9].toString();
						//escalation_time = object[9].toString();
					}

					if (object[10] != null && !object[10].toString().equals(""))
					{
						escalation_time=object[10].toString();
						//status = object[10].toString();
					}

					if (object[11] != null && !object[11].toString().equals(""))
					{
						priority = "'"+object[11].toString()+"'";
					}

					if (object[12] != null && !object[12].toString().equals(""))
					{
						status = object[12].toString();
						//nursingUnit = object[12].toString();
					}

					if (object[13] != null && !object[13].toString().equals(""))
					{
						level = object[13].toString();
					}

				if (object[14] != null && !object[14].toString().equals(""))
					{
						department = object[14].toString();
						if(department.equalsIgnoreCase("17")){
							department = "26";
						}
					}
				
						
					

					// Escalation for occ level

					if (status!=null && status.equalsIgnoreCase("Not Informed") || status.equalsIgnoreCase("Informed-P") )
					{
						boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
						if (escnextlvl)
						{
							// //System.out.println("kjjdhggkgkj"+escnextlvl+"   "+department);

							// send msg to respective holder

							if (department != null && !department.equals("") && !department.equals("0") && level != null && !level.equals("") && !level.equals("0"))
							{

								String colName1 = null;
								String colName2 = null;
								String calculationLevel = null;
								int isMsgSent = 0;
								
								calculationLevel = String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1);
								String str=getMeAppendedName(selectRendomTechni("26","1","CRC",connection));
								if (calculationLevel != null && calculationLevel.equalsIgnoreCase("2"))
								{
									colName1 = "escLevelL2L3";
									colName2 = "l2Tol3";
									
									
									if(!"".equalsIgnoreCase(str))
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", ticket_no, nursingUnit, patient_name, uhid, escalation_date, escalation_time, doc_name, patient_status, "L1toL2", specialty, CH, connection,cartName,str,"1");
									}else
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L1toL2", priority, CH, connection,cartName,null,"1");
									}
									
								} else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
								{
									colName1 = "escLevelL3L4";
									colName2 = "l3Tol4";
									
									if(!"".equalsIgnoreCase(str))
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L2toL3", priority, CH, connection,cartName,str,"1");
									}else
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L2toL3", priority, CH, connection,cartName,null,"1");
									}
								} else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
								{
									colName1 = "escLevelL4L5";
									colName2 = "l4Tol5";
									if(!"".equalsIgnoreCase(str))
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L3toL4", priority, CH, connection,cartName,str,"1");
									}else
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L3toL4", priority, CH, connection,cartName,null,"1");
									}
								} else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("5"))
								{
									colName1 = "escLevelL5L6";
									colName2 = "l5Tol6";
									
									if(!"".equalsIgnoreCase(str))
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L4toL5", priority, CH, connection,cartName,str,"1");
									}else
									{
										isMsgSent = sendMsg4Esc(department, String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1), "CRC", orderid, nursingUnit, roomNo, uhid, escalation_date, escalation_time, orderName, orderType, "L4toL5", priority, CH, connection,cartName,null,"1");
									}
								}
								if(colName1!=null && colName2!=null)
								{
									 nxtEsc = escTime("Critical", colName2, department, colName1, connection);
										if (isMsgSent > 0)
										{
											sb.setLength(0);
											sb = new StringBuilder();
											sb.append(" update critical_data set  ");

											if(nxtEsc!=null && !"".equalsIgnoreCase(nxtEsc))
											{
												sb.append(" escalate_date='" + nxtEsc.split("#")[0] + "',");
												sb.append(" escalate_time='" + nxtEsc.split("#")[1] + "',");
											}
											sb.append(" level='Level" + calculationLevel + "'");
											sb.append(" where id=" + id + "");

											int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);

											if (chkUpdate > 0)
											{
												Map<String, Object> wherClause = new HashMap<String, Object>();
												// update in history table
												wherClause.put("rid", patient_id);
												wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("esc_level", "Level " + calculationLevel);
												wherClause.put("status", "Escalate");
												wherClause.put("escalate_to",  getMeAppendedName(selectRendomTechni(department,calculationLevel,"CRC",connection)));
											//	wherClause.put("dept", department);
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
													boolean updateFeed = cbt.insertIntoTable("critical_data_history", insertData, connection);
													insertData.clear();
												}
											}
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

	// select rendom technician with roster mapping for assign order when parked
	// is activate to assign
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List selectRendomTechni(String department,String level,String module, SessionFactory connection)
	{
		List empList= new ArrayList();

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT emp.id, emp.empName, emp.mobOne, ewm.critical  FROM employee_basic AS emp");
			sb.append("  INNER JOIN critical_emp_mapping AS ewm ON ewm.empName=emp.id");
			sb.append("  INNER JOIN shift_with_emp_wing AS swew ON ewm.shiftId = swew.id");
			sb.append("  INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			sb.append("  INNER JOIN  subdepartment AS subdept ON subdept.id=cc.forDept_id");
			sb.append("  WHERE subdept.id=" + department + " AND swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >'" + DateUtil.getCurrentTime() + "'");
			sb.append(" and cc.level='"+level+"' AND cc.moduleName='" + module + "' and cc.work_status='0' ");
			/*if(MachineId!=null && !MachineId.equalsIgnoreCase("") && !MachineId.equalsIgnoreCase("0") )
			{
				sb.append(" and ewm.machine='"+MachineId+"'");
			}*/
			//sb.append(" group by emp.id ");
			//System.out.println("sb >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>              "+sb);
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					String assignEmpDetails = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString() + "#" + object[3].toString();
					//System.out.println("assignEmpDetails>>> "+assignEmpDetails);
					empList.add(assignEmpDetails);
				}
					
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return empList;
	}

	// send sms for escalate
	private int sendMsg4Esc(String department, String level, String module, String ticket_id, String nursingUnit, String patient, String uhid, String escalation_date, String escalation_time, String docname, String patient_status, String lAlert, String spec, CommunicationHelper CH, SessionFactory connection,String cartName,String assignTechDetails,String MachineId)
	{
		// //System.out.println("conn>>>  "+connection);
		int flag = 0;

		// //System.out.println(department +"   "+level+"   "+module);
		//String escHolderDetails = escHolderNameMob(department, level, module, connection);
		List escHolderDetails =null;
		
		if(MachineId!=null && !MachineId.equalsIgnoreCase(""))
		{
			escHolderDetails= selectRendomTechni(department, level, module, connection);
		}else{
			escHolderDetails= selectRendomTechni(department, level, module, connection);
		}
		try
		{

		String empMob = "";
		if (escHolderDetails != null && !escHolderDetails.isEmpty())
		{
			for (Iterator iterator = escHolderDetails.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				// send assign order sms to holder
				empMob=object.toString().split("#")[2];
				if (empMob.length() > 9)
				{
					// MsgMailCommunication MMC = new MsgMailCommunication();
					// MachineOrderHelper moh = new MachineOrderHelper();
					if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
					{
						String levelMsg=null;
						
							if(MachineId == null)
							{
								levelMsg=lAlert+"OCC";
							}
							levelMsg = "OCC Escalation For "+lAlert +" - "+patient_status+" Critical Escalation Alert: Patient Name: " + patient + ", Ticket: " + ticket_id + " , Location: " + nursingUnit  + "/ " + uhid + ", Doctor: " + docname + ", Speciality: " + spec + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + " - " + escalation_time + ".";
							
							if (assignTechDetails != null && !"".equalsIgnoreCase(assignTechDetails))
							{
								//levelMsg = levelMsg + ", Assign To:" + assignTechDetails.split("#")[0]+".";
							}
							else
							{
								levelMsg = levelMsg +".";
							}
						
						
						boolean isSent = CH.addMessage(object.toString().split("#")[1], department, empMob, levelMsg, orderid, "Pending", "0", "CRC", connection);
						if (isSent)
						{
							//System.out.println("sms sent to  empMob: "+empMob+" orderid: "+orderid);
						}

					}
					
			}
		}
			flag = 1;
			
			}

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}

		return flag;
	}

	// Parked sms send
	private int sendMsg4ParkedActive(String department, String level, String module, String orderid, String nursingUnit, String roomNo, String uhid, String escalation_date, String escalation_time, String orderName, String orderType, String assignTechnicianDetail, String priority, CommunicationHelper CH, SessionFactory connection)
	{
		int flag = 0;
		String empName = assignTechnicianDetail.split("#")[0];
		String empMob = assignTechnicianDetail.split("#")[1];

		try
		{

			// send assign order sms to holder
			if (empMob.length() > 9)
			{
				// MsgMailCommunication MMC = new MsgMailCommunication();
				// MachineOrderHelper moh = new MachineOrderHelper();
				if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
				{
					String levelMsg = "Parked To Assign Alert: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Priority: " + priority + ", Order No: " + orderid + ", To Be Closed By: " + escalation_date + " - " + escalation_time + ".";
					boolean isSent = CH.addMessage(empName, department, empMob, levelMsg, orderid, "Pending", "0", "CRC", connection);
					if (isSent)
					{
						// //System.out.println("sms sent to "+empName+" empMob: "+empMob+" orderid: "+orderid);
					}

				}
				flag = 1;
			}

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}

		return flag;
	}

	@SuppressWarnings("unused")
	private String getEmpNameMobByEmpId(String assignTech2, SessionFactory connection)
	{
		String empDetails = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT empName, mobOne FROM employee_basic ");
			sb.append(" WHERE  id=" + assignTech2);
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);

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
	@SuppressWarnings("unused")
	private String escHolderNameMob(String department, String level, String module, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String empDetails = null;

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("SELECT emp.empName, emp.`mobOne` FROM employee_basic AS emp INNER JOIN `compliance_contacts` AS cc ON cc.emp_id=emp.`id` WHERE forDept_id='" + department + "' AND LEVEL='" + level + "' AND `moduleName`='" + module + "'".toString()+" and cc.work_status='0' ", connection);

			if (dataListEcho != null && dataListEcho.size() > 0)
			{

				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
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

	public String escTime(String priority, String ltol, String Dept, String escType, SessionFactory connection)
	{
		String esctm = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append("SELECT  " + ltol + ", escSubDept FROM critical_escalation WHERE escSubDept='" + Dept + "'  and " + escType + "='Customised'");
			//System.out.println("empuery  "+empuery);

			List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connection);
			// //System.out.println(empData1.size());
			if (empData1 != null && empData1.size() > 0)
			{

				for (Iterator it = empData1.iterator(); it.hasNext();)
				{
					Object[] object = (Object[]) it.next();
					esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object[0].toString(), "Y");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

	// for snooze order activate to pending
	public void SnoozetoPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
			{
		/*StringBuilder sb = new StringBuilder();
		sb.append("SELECT ord.id, ord.orderid, ord.orderType, ord.uhid, ord.roomNo, ord.assignMchn, ord.department, ");
		sb.append(" ord.assignTech, ord.escalation_date, ord.escalation_time, ord.status, ord.priority, nu.nursing_unit, ord.level, ord.parkedTill, ord.orderName, ord.actionDate, ord.actionTime, ord.parkedTillTime ");
		sb.append(" FROM machine_order_data as ord ");
		sb.append(" LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
		sb.append(" WHERE ord.STATUS IN ('Snooze')");
		sb.append(" AND ord.parkedTill<='" + DateUtil.getCurrentDateUSFormat() + "' and ord.parkedTillTime <= '" + DateUtil.getCurrentTimeHourMin() + "'");
		//System.out.println("park query=" + sb);
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
					orderid = object[1].toString();
				}

				if (object[2] != null && !object[2].toString().equals(""))
				{
					orderType = object[2].toString();
				}

				if (object[3] != null && !object[3].toString().equals(""))
				{
					uhid = object[3].toString();
				}

				if (object[4] != null && !object[4].toString().equals(""))
				{
					roomNo = object[4].toString();
				}

				if (object[5] != null && !object[5].toString().equals(""))
				{
					assignMchn = object[5].toString();
				}

				if (object[6] != null && !object[6].toString().equals(""))
				{
					department = object[6].toString();
				}

				if (object[7] != null && !object[7].toString().equals(""))
				{
					assignTech = object[7].toString();
				}

				if (object[8] != null && !object[8].toString().equals(""))
				{
					escalation_date = object[8].toString();
				}

				if (object[9] != null && !object[9].toString().equals(""))
				{
					escalation_time = object[9].toString();
				}

				if (object[10] != null && !object[10].toString().equals(""))
				{
					status = object[10].toString();
				}

				if (object[11] != null && !object[11].toString().equals(""))
				{
					priority = object[11].toString();
				}

				if (object[12] != null && !object[12].toString().equals(""))
				{
					nursingUnit = object[12].toString();
				}

				if (object[13] != null && !object[13].toString().equals(""))
				{
					level = object[13].toString();
				}

				if (object[14] != null && !object[14].toString().equals(""))
				{
					parkedTill = object[14].toString();
				}
				if (object[15] != null && !object[15].toString().equals(""))
				{
					orderName = object[15].toString();
				}
				if (object[16] != null && !object[16].toString().equals(""))
				{
					actionDate = object[16].toString();
				}
				if (object[17] != null && !object[17].toString().equals(""))
				{
					actionTime = object[17].toString();
				}

				if (object[18] != null && !object[17].toString().equals(""))
				{
					parkedTillTime = object[18].toString();
				}
*/
			
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT cd.id,cd.patient_id,cd.ticket_no,cpd.patient_name,cpd.patient_status,cpd.addmision_doc_name,cpd.nursing_unit, ");
			sb.append(" cpd.specialty,cd.uhid,cdh.sn_upto_date,cdh.sn_upto_time,ty.test_type,cd.status,cd.level, cdh.action_date, cdh.action_time,cd.escalate_date,cd.escalate_time ");
			sb.append(" from critical_data as cd ");
			sb.append(" LEFT JOIN critical_patient_data AS cpd ON cd.patient_id=cpd.id");
			sb.append(" LEFT JOIN critical_data_history AS cdh ON cdh.rid=cpd.id ");
			sb.append(" LEFT JOIN test_type AS ty ON ty.id=cd.test_type ");
			sb.append(" WHERE cd.status='Snooze' ");
			sb.append(" AND cdh.sn_upto_date<='" + DateUtil.getCurrentDateUSFormat() + "' and cdh.sn_upto_time <= '" + DateUtil.getCurrentTimeHourMin() + "'");
			//System.out.println(" "+sb);
			List data4escalate = HUH.getData(sb.toString(), connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{

				for (@SuppressWarnings("rawtypes")
				Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						patient_id = object[1].toString();
					}
					

					if (object[1] != null && !object[1].toString().equals(""))
					{
						orderid = object[1].toString();
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						ticket_no=object[2].toString();
					//	orderType = object[2].toString();
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						patient_name= object[3].toString();
						//uhid = object[3].toString();
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						patient_status=object[4].toString();
						//roomNo = object[4].toString();
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						doc_name= object[5].toString();
						//assignMchn = object[5].toString();
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						nursingUnit=object[6].toString();
						//department = object[6].toString();
					}
					

					if (object[7] != null && !object[7].toString().equals(""))
					{
						specialty=object[7].toString();
						//assignTech = object[7].toString();
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						uhid= object[8].toString();
						//escalation_date = object[8].toString();
					}

					if (object[9] != null && !object[9].toString().equals(""))
					{
						parkedTill=object[9].toString();
						//escalation_time = object[9].toString();
					}

					if (object[10] != null && !object[10].toString().equals(""))
					{
						parkedTillTime=object[10].toString();
						//status = object[10].toString();
					}

					if (object[11] != null && !object[11].toString().equals(""))
					{
						priority = object[11].toString();
					}

					if (object[12] != null && !object[12].toString().equals(""))
					{
						status = object[12].toString();
						//nursingUnit = object[12].toString();
					}

					if (object[13] != null && !object[13].toString().equals(""))
					{
						level = object[13].toString();
					}

				if (object[14] != null && !object[14].toString().equals(""))
					{
					actionDate = object[14].toString();
					}
				
				if (object[15] != null && !object[15].toString().equals(""))
				{
					actionTime = object[15].toString();
				}
				
				if (object[16] != null && !object[16].toString().equals(""))
				{
					escalation_date = object[16].toString();
				}
			
			if (object[17] != null && !object[17].toString().equals(""))
			{
				escalation_time = object[17].toString();
			}
				
					
						// 17 is OCC Department ID
						department ="17";
					
				// for snooze order activate to pending

				if (status.equalsIgnoreCase("Snooze"))
				{
					 
					boolean escnextlvl = DateUtil.time_update(parkedTill, parkedTillTime);
					if (escnextlvl)
					{
						// Chk is the parked time and action time (is parked
						// time is grater than action time)
						boolean isActionEscDiff = DateUtil.comparebetweenTwodateTime(actionDate, actionTime, parkedTill, parkedTillTime);
						if (isActionEscDiff)
						{
							// calculate the working time till parked
							String diff_time = DateUtil.time_difference(actionDate, actionTime, escalation_date, escalation_time);
							// calculate the when parked active
							String new_escalation_datetime = DateUtil.newdate_AfterEscTime(parkedTill, parkedTillTime, diff_time, "Y");

							// get rendom one employee from roster to assign
							// String assignTechnicianDetail =
							// selectRendomTechni(department, connection);
					
							if (new_escalation_datetime.length() > 0 && !new_escalation_datetime.split("#")[0].toString().equalsIgnoreCase("0000-00-00") && !new_escalation_datetime.split("#")[1].toString().equalsIgnoreCase("00:00"))

							{

								sb.setLength(0);
								sb = new StringBuilder();
								sb.append(" update critical_data set  ");
								sb.append(" escalation_date='" + new_escalation_datetime.split("#")[0] + "',");
								sb.append(" escalation_time='" + new_escalation_datetime.split("#")[1] + "',");
								sb.append(" status='Snooze'");
								// sb.append(" assignMchn='" +
								// assignTechnicianDetail.split("#")[3] +
								// "',");
								// sb.append(" assignTech='" +
								// assignTechnicianDetail.split("#")[0] +
								// "',");
								// sb.append(" resource='" +
								// assignTechnicianDetail.split("#")[3] +
								// "'");
								sb.append(" where id=" + id + "");
								//System.out.println(sb);
								int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);

								if (chkUpdate > 0)
								{
									Map<String, Object> wherClause = new HashMap<String, Object>();
									// update in history table
									wherClause.put("id", patient_id);
									wherClause.put("action_by", "0");
									wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
									wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
									
									
									wherClause.put("escalation_level", level);
									wherClause.put("status","Not Informed-"+priority.substring(0, 1));
									//wherClause.put("dept", department);
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
										boolean updateFeed = cbt.insertIntoTable("critical_data_history", insertData, connection);

										/*if (updateFeed)
										{
											sendMsg4ParkedActive(department, level, "CRC", ticket_no, nursingUnit, roomNo, uhid, new_escalation_datetime.split("#")[0], new_escalation_datetime.split("#")[1], patient_name, doc_name, patient_status, specialty, CH, connection);
										}*/

										insertData.clear();
									}
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

	}}
	
	

	@SuppressWarnings("rawtypes")
	public String getSubDeptId(String ref_to, HelpdeskUniversalHelper HUH, SessionFactory connection)
	{
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT dept FROM critical_data  ");
			
			query.append(" WHERE patient_id=" + ref_to);
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
	

}