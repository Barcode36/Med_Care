package com.Over2Cloud.ctrl.patientcare;

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
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.ctrl.cps.corporate.CPSPOJO;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class CPSServiceFileHelper
{
	/*
	 * Author : Sanjay 20-01-2016
	 */

	String id = null;
	String corp_type = null;
	String corp_name = null;
	String feed_status = null;
	String patient_name = null;
	String uhid = null;
	String pat_mobile = null;
	String pat_city = null;
	String services = null;
	String med_location = null;
	String preferred_time = null;
	String remarks = null;
	String date = null;
	String time = null;
	String ac_manager = null;
	String status = null;
	String ticket = null;
	String next_level_esc_date = null;
	String next_level_esc_time = null;
	String department = null;
	String level = null;
	String park_till_date = null;
	String park_till_time = null;
	String user_name = null;

	public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{

		try
		{

			StringBuilder sb = new StringBuilder();
			sb.append(" select cpd.id, cpd.corp_type, dshis.customer_name, cpd.feed_status, cpd.patient_name, cpd.uhid, cpd.pat_mobile, cpd.pat_city, ");
			sb.append(" cpd.services, cpd.med_location,cpd.preferred_time, cpd.remarks, cpd.date, cpd.time, cpd.ac_manager, cpd.status, cpd.ticket,  ");
			sb.append(" cpd.next_level_esc_date,cpd.next_level_esc_time, cpd.department, cpd.level from corporate_patience_data as cpd");
			sb.append(" INNER JOIN  dreamsol_bl_corp_hc_pkg AS dshis ON dshis.id=cpd.corp_name ");
			sb.append(" where cpd.status in ('Appointment','Service In','Scheduled') AND cpd.next_level_esc_date<='" + DateUtil.getCurrentDateUSFormat() + "' AND cpd.next_level_esc_time<='" + DateUtil.getCurrentTimeHourMin() + "'");
			System.out.println("mmm" + sb);
			List data4escalate = huh.getData(sb.toString(), connection);
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
						corp_type = object[1].toString();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						corp_name = object[2].toString();
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						feed_status = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						patient_name = object[4].toString();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						uhid = object[5].toString();
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						pat_mobile = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						pat_city = object[7].toString();
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						services = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						med_location = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						preferred_time = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						remarks = object[11].toString();
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						date = object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						time = object[13].toString();
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						ac_manager = object[14].toString();
					}
					if (object[15] != null && !object[15].toString().equals(""))
					{
						status = object[15].toString();
					}
					if (object[16] != null && !object[16].toString().equals(""))
					{
						ticket = object[16].toString();
					}
					if (object[17] != null && !object[17].toString().equals(""))
					{
						next_level_esc_date = object[17].toString();
					}
					if (object[18] != null && !object[18].toString().equals(""))
					{
						next_level_esc_time = object[18].toString();
					}
					if (object[19] != null && !object[19].toString().equals(""))
					{
						department = object[19].toString();
					}
					if (object[20] != null && !object[20].toString().equals(""))
					{
						level = object[20].toString();
					}
					// escalation for OCC level
					if (status.equalsIgnoreCase("Appointment"))
					{

						boolean escnextlvl = DateUtil.time_update(next_level_esc_date, next_level_esc_time);
						if (escnextlvl)
						{
							if (department != null && !department.equals("") && !department.equals("0") && level != null && !level.equals("") && !level.equals("0"))
							{

								String colName1 = null;
								String colName2 = null;
								String calculationLevel = null;
								int isMsgSent = 0;

								calculationLevel = String.valueOf(Integer.parseInt(level) + 1);
								if (calculationLevel != null && calculationLevel.equalsIgnoreCase("2"))
								{
									colName1 = "escLevelL2L3";
									colName2 = "l2Tol3";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L1ToL2", "17", "2", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
								{
									colName1 = "escLevelL3L4";
									colName2 = "l3Tol4";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L2ToL3", "17", "3", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
								{
									colName1 = "escLevelL4L5";
									colName2 = "l4Tol5";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L3ToL4", "17", "4", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("5"))
								{
									colName1 = "escLevelL5L6";
									colName2 = "l5Tol6";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L4ToL5", "17", "5", "CPS", connection, ch);

								}

							}
						}

					}
					//Esclate Service Managere
					else if (status.equalsIgnoreCase("Scheduled"))
					{
					 
						boolean escnextlvl = DateUtil .time_update (next_level_esc_date , next_level_esc_time ); 
						if(escnextlvl ) 
						{ 
							if (department != null && !department.equals("" ) && !department . equals("0" ) && level !=null && !level .equals ("") && !level .equals ("0")) 
							{
					 
								String colName1 = null; String colName2 = null; String calculationLevel = null; int isMsgSent = 0; String
								subDeptId = getSubDeptIdByMarkarrival ("Mark Arrival",connection );
								calculationLevel =String. valueOf (Integer. parseInt( level) + 1); 
								if( calculationLevel != null && calculationLevel . equalsIgnoreCase ("2")) 
								{ 
									colName1 ="escLevelL2L3" ; colName2 = "l2Tol3"; 
									isMsgSent =sendMsg( id, ticket, corp_name , feed_status ,patient_name , services, med_location , preferred_time ,remarks, colName2, "L1ToL2", subDeptId , "2", "CPS",connection , ch); 
								} 
								else if( calculationLevel != null && calculationLevel . equalsIgnoreCase ("3"))
								{
									colName1 ="escLevelL3L4" ; colName2 = "l3Tol4"; isMsgSent =sendMsg( id, ticket, corp_name , feed_status ,patient_name , services, med_location , preferred_time ,remarks, colName2, "L2ToL3", subDeptId , "3", "CPS",connection , ch); 
								} 
								else if( calculationLevel != null && calculationLevel . equalsIgnoreCase ("4")) 
								{ 
									colName1 ="escLevelL4L5" ; colName2 = "l4Tol5"; isMsgSent =sendMsg( id, ticket, corp_name , feed_status ,patient_name , services, med_location , preferred_time ,remarks, colName2, "L3ToL4", subDeptId , "4", "CPS",connection , ch); 
								} 
								else if( calculationLevel != null && calculationLevel . equalsIgnoreCase ("5")) 
								{ 
									colName1 ="escLevelL5L6" ; colName2 = "l5Tol6"; isMsgSent =sendMsg( id, ticket, corp_name , feed_status ,patient_name , services, med_location , preferred_time ,remarks, colName2, "L4ToL5", subDeptId , "5", "CPS",connection , ch);
					  
								} 
							} 
						}
					 
					}
					//escalation for CPS Service Manager level
					else if (status.equalsIgnoreCase("Service In"))
					{
						boolean escnextlvl = DateUtil.time_update(next_level_esc_date, next_level_esc_time);
						if (escnextlvl)
						{
							if (department != null && !department.equals("") && !department.equals("0") && level != null && !level.equals("") && !level.equals("0"))
							{

								String colName1 = null;
								String colName2 = null;
								String calculationLevel = null;
								int isMsgSent = 0;
								String subDeptId = getSubDeptIdByServiceId(services, connection);
								calculationLevel = String.valueOf(Integer.parseInt(level) + 1);
							//	System.out.println("calculationLevel " + calculationLevel);
								if (calculationLevel != null && calculationLevel.equalsIgnoreCase("2"))
								{
									colName1 = "escLevelL2L3";
									colName2 = "l2Tol3";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L1ToL2", subDeptId, "2", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
								{
									colName1 = "escLevelL3L4";
									colName2 = "l3Tol4";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L2ToL3", subDeptId, "3", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
								{
									colName1 = "escLevelL4L5";
									colName2 = "l4Tol5";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L3ToL4", subDeptId, "4", "CPS", connection, ch);
								}
								else if (calculationLevel != null && calculationLevel.equalsIgnoreCase("5"))
								{
									colName1 = "escLevelL5L6";
									colName2 = "l5Tol6";
									isMsgSent = sendMsg(id, ticket, corp_name, feed_status, patient_name, services, med_location, preferred_time, remarks, colName2, "L4ToL5", subDeptId, "5", "CPS", connection, ch);

								}
							}
						}
					}
				}

			}

		}

		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private int sendMsg(String id, String ticket2, String corp_name2, String feed_status2, String patient_name2, String services2, String med_location2, String preferred_time2, String remarks2, String colName2, String colName3, String sub_dept, String level1, String module, SessionFactory connection, CommunicationHelper ch)
	{
		// TODO Auto-generated method stub
		int retnResult = 0;
		boolean sent = false;
		String levelMsg = "";
		String location_Id = getLocationIdByName(med_location2, connection);
		String EscHolder = getMeAppendedName(selectTechniToEsc(services2, location_Id, sub_dept, level1, "CPS", connection));
		System.out.println("EscHolder " + EscHolder);
		String serviceName = getServiceName(services2, connection);
		if (EscHolder != null && !EscHolder.equalsIgnoreCase("") && EscHolder.contains(","))
		{
			String[] arrEcs = EscHolder.trim().split(",");
			for (int i = 0; i < arrEcs.length; i++)
			{
				levelMsg = "CPS Escalation Alert: " + colName3 + " Ticket: " + ticket2 + " from " + corp_name2 + " / " + feed_status2 + " Patient Name: " + patient_name2 + " request for service: " + serviceName + " at " + med_location2 + " on " + preferred_time2 + " is escalated on:" + DateUtil.getCurrentDateIndianFormat() + " " + DateUtil.getCurrentTimeHourMin();
				boolean isSent = ch.addMessage(arrEcs[i].toString().split("#")[0], sub_dept, arrEcs[i].toString().split("#")[1], levelMsg, ticket2, "Pending", "0", "CPS", connection);
				if (isSent)
				{
					sent = isSent;
				}

			}
		}
		else if (EscHolder != null && !EscHolder.equalsIgnoreCase("") && !EscHolder.contains(","))
		{
			levelMsg = "CPS Escalation Alert: " + colName3 + " Ticket: " + ticket2 + " from " + corp_name2 + " / " + feed_status2 + " Patient Name: " + patient_name2 + " request for service: " + serviceName + " at " + med_location2 + " on " + preferred_time2 + " is escalated on:" + DateUtil.getCurrentDateIndianFormat() + " " + DateUtil.getCurrentTimeHourMin();
			boolean isSent = ch.addMessage(EscHolder.split("#")[0], sub_dept, EscHolder.split("#")[1], levelMsg, ticket2, "Pending", "0", "CPS", connection);
			if (isSent)
			{
				sent = isSent;

			}
		}
		if (sent == true)
		{
			String getnextTime = null;
			if (status.equalsIgnoreCase("Service In"))
			{
				getnextTime = escTime(colName2, "All", level, connection, "Mark Arrival");
			}
			else
			{
				System.out.println("colName2: "+colName2+" sub_dept: "+sub_dept+" level: "+level);
				getnextTime = escTime(colName2, sub_dept, level, connection, "");
			}
			System.out.println("getnextTime "+getnextTime);
			StringBuilder sb = new StringBuilder();
			sb.append(" update corporate_patience_data set  ");
			sb.append(" next_level_esc_date='" + getnextTime.split("#")[0] + "',");
			sb.append(" next_level_esc_time='" + getnextTime.split("#")[1] + "',");
			sb.append(" level=" + level1);
			sb.append(" where id=" + id + "");

			int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
			if (chkUpdate > 0)
			{

				Map<String, Object> wherClause = new HashMap<String, Object>();
				// update in history table
				wherClause.put("CPSId", id);
				wherClause.put("action_by", "0");
				wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("action_time", DateUtil.getCurrentTime());
				wherClause.put("allocate_to", "Auto");
				wherClause.put("action_reason", "Escalate");
				wherClause.put("escalation_level", level1);
				wherClause.put("status", "Escatate");
				wherClause.put("dept", serviceName);
				if (wherClause != null && wherClause.size() > 0)
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					insertData.clear();
					for (Map.Entry<String, Object> entry : wherClause.entrySet())
					{
						ob = new InsertDataTable();
						ob.setColName(entry.getKey());
						ob.setDataName(entry.getValue().toString());
						insertData.add(ob);
					}
					boolean updateFeed = cbt.insertIntoTable("cps_status_history", insertData, connection);

					insertData.clear();
				}

			}
		}
		return retnResult;
	}

	// method for get next level escalation date and time author : manab
	// 03-09-2015
	private String escTime(String col1, String dept, String level, SessionFactory connection, String mark_arrival)
	{
		String esctm = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			if (mark_arrival != null && !mark_arrival.equalsIgnoreCase(""))
			{
				empuery.append(" select " + col1 + " from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept='" + dept + "' and service_name='" + mark_arrival + "'");
			}
			else
			{
				empuery.append(" select " + col1 + " from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept='" + dept + "' ");
			}
			System.out.println("empuery "+empuery);
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

	private String getServiceName(String serviceId, SessionFactory connection)
	{
		String serviceName = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select service_name from cps_service where id='" + serviceId + "' ");
			List Data = cbt.executeAllSelectQuery(query.toString(), connection);
			serviceName = Data.get(0).toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return serviceName;
	}

	private String getLocationIdByName(String location, SessionFactory connection)
	{
		String locationId = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append(" select id from cps_location where location_name like '%" + location + "%' ");
			List Data = cbt.executeAllSelectQuery(query.toString(), connection);
			locationId = Data.get(0).toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return locationId;
	}

	@SuppressWarnings("unused")
	private String getSubDeptIdByMarkarrival(String serviceId, SessionFactory connection)
	{
		System.out.println("serviceId "+serviceId);
		String subdeptId = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append(" select id from subdepartment   where subdeptname='" + serviceId + "' ");
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData.size() > 0) subdeptId = escData.get(0).toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return subdeptId;
	}
	
	@SuppressWarnings("unused")
	private String getSubDeptIdByServiceId(String serviceId, SessionFactory connection)
	{
		String subdeptId = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append(" select subdept.id from subdepartment as subdept inner join  cps_service as cs on cs.service_name=subdept.subdeptname where cs.id='" + serviceId + "' ");
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData.size() > 0) subdeptId = escData.get(0).toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return subdeptId;
	}

	// For getting rendom for Escalation alert

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public List selectTechniToEsc(String services, String location, String sub_department, String level, String module, SessionFactory connection)
	{
		List empList = new ArrayList();

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			/*sb.append("SELECT emp.id, emp.empName, emp.mobOne, ewm.cps_service_id  FROM employee_basic AS emp");
			sb.append("  INNER JOIN cps_emp_subdept_mapping AS ewm ON ewm.empName=emp.id");
			sb.append("  INNER JOIN cps_shift_with_emp_mapping AS swew ON ewm.shiftId = swew.id");
			sb.append("  INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id`");
			sb.append("  INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id`");
			sb.append("  WHERE subdept.id=" + sub_department + " AND swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >='" + DateUtil.getCurrentTime() + "'");
			sb.append(" and cc.level='" + level + "' AND cc.moduleName='" + module + "' and cc.work_status='0' ");*/
			
			
			sb.append("SELECT emp.id, emp.empName, emp.mobOne FROM employee_basic AS emp");
			sb.append("  INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id`");
			sb.append("  INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id`");
			sb.append("  WHERE subdept.id=" + sub_department + " and cc.level='" + level + "' AND cc.moduleName='" + module + "' and cc.work_status='0' ");
			
			/*if (sub_department != "17")
			{
				if (services != null && location != null && !services.equalsIgnoreCase("") && !services.equalsIgnoreCase("0"))
				{
					sb.append(" and ewm.cps_service_id ='" + services + "_" + location + "" + "' ");
				}
			}*/
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					//String assignEmpDetails = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString() + "#" + object[3].toString();
					String assignEmpDetails = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString();
					empList.add(assignEmpDetails);
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return empList;
	}

	public String getMeAppendedName(List escalateTo)
	{
		String temp = "";
		int count = 0;
		for (Iterator iterator3 = escalateTo.iterator(); iterator3.hasNext();)
		{
			Object object2 = (Object) iterator3.next();
			if (count < escalateTo.size())
			{
				temp = temp + object2.toString().split("#")[1] + "#" + object2.toString().split("#")[2] + ", ";
			}
			else
			{
				temp = temp + object2.toString().split("#")[1] + "#" + object2.toString().split("#")[2];
			}
			count++;
		}

		return temp;
	}

	public void markArrivalUpdate(SessionFactory connection, HelpdeskUniversalHelper huh, CommunicationHelper cH)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String dateTime = "0000:00:00 00:00";
		StringBuilder sb = new StringBuilder();
		sb.append(" select id , service_book_time from corporate_patience_data where status='Scheduled'");
		List data4escalate = huh.getData(sb.toString(), connection);
		if (data4escalate != null && data4escalate.size() > 0)
		{

			for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();

				if (object[0] != null && !object[0].toString().equals("") && object[1] != null && !object[1].toString().equals(""))
				{
					id = object[0].toString();
					date = object[1].toString().split(" ")[0].trim();
					time = object[1].toString().split(" ")[1].trim();
					if (date.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat().trim()))
					{
						if (time.contains(DateUtil.getCurrentTimeHourMin()))
						{
							String tatMarkArrivl = serviceWiseTAT("All", connection);
							int status = cbt.executeAllUpdateQuery("update corporate_patience_data set next_level_esc_date='" + tatMarkArrivl.split("#")[0] + "', next_level_esc_time='" + tatMarkArrivl.split("#")[1] + "' where id =" + id + "".toString(), connection);

						}

					}
				}
			}
		}

	}

	private String serviceWiseTAT(String parameter, SessionFactory connectionSpace)
	{
		String esctm = "";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();

			empuery.append(" select l1Tol2 from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept='" + parameter + "' and service_name='Mark Arrival' ");

			List escData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;

	}

	public void ReportGeneration(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			List data4report = getReportToData(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), connection);
			if (data4report != null && data4report.size() > 0)
			{
				String empname = null, mobno = null, emailid = null, subject = null, report_level = null, dept_Id = null, deptLevel = null, type_report = null, id = null, module = null, department = null, report_date = null, report_time = null;
				String allotTo, registerBy, ticket_status, emp_Id;
				int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, re = 0, reo = 0, total = 0;
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
						dept_Id = object[2].toString();
					}

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
						emp_Id = object[12].toString();
					}
					else
					{
						emp_Id = "NA";
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						report_date = object[13].toString();
					}
					else
					{
						report_date = "NA";
					}

					if (object[14] != null && !object[14].toString().equals(""))
					{
						report_time = object[14].toString();
					}
					else
					{
						report_time = "NA";
					}

					String newDate = null;
					if (!type_report.equals("") && !type_report.equals("NA"))
					{
						newDate = getNewDate(type_report);
					}
					List dataList=null;
					 CPSPOJO cps;
					 List<CPSPOJO> cpsData=null;
					String updateQry = "update report_configuration set report_date='" + newDate + "' where id='" + id + "'";
					boolean updateReport = updateData(updateQry, connection);
					Map<String, List<CPSPOJO>> cpsDataMap = new LinkedHashMap<String, List<CPSPOJO>>();
					if (module != null && !module.equals("") && module.equalsIgnoreCase("CPS") && updateReport)
					{
						if (dept_Id != null && dept_Id.equalsIgnoreCase("18"))
						{
								dataList = getAccountManagerDetail(connection);
								emp_Id = dataList.get(0).toString();
								cpsData = getData4CPS(connection, type_report, emp_Id, dept_Id, report_time,"2");
								if (cpsData != null && cpsData.size() > 0)
								{
									cpsDataMap.put("IPD", cpsData);
								}
						}
						else
						{
			 			 dataList = getServiceDetail(connection);
			  			if (dataList != null && dataList.size() > 0)
						{
							 
							for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
							{
								cps = new CPSPOJO();
								Object[] obj = (Object[]) iterator2.next();
								if (obj[0] != null && obj[1] != null)
								{
									 cpsData = getData4CPS(connection, type_report, emp_Id, dept_Id, report_time, obj[0].toString());
									if (cpsData != null && cpsData.size() > 0)
									{
										cpsDataMap.put(obj[1].toString(), cpsData);
									}
								}
							}
						}
						}
						String mailSubject = "Corporate Patient Service(CPS) Report As On " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTimeHourMin();
				 		String mailtext = getCPSConfigMailForReport(empname, cpsDataMap);
						CH.addMail(empname, department, emailid, mailSubject, mailtext, "Report", "Pending", "0", "", "CPS", connection);
					}

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

	public boolean updateData(String query, SessionFactory connection)
	{
		boolean flag = false;
		int count;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			count = session.createSQLQuery(query).executeUpdate();
			if (count > 0)
			{
				flag = true;
			}
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		return flag;
	}

	public String getNewDate(String newDateType)
	{
		String data = "";
		try
		{
			if (newDateType.equals("D"))
			{
				data = DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat());
			}
			else if (newDateType.equals("W"))
			{
				data = DateUtil.getNewDate("day", 7, DateUtil.getCurrentDateUSFormat());
			}
			else if (newDateType.equals("M"))
			{
				data = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
			}
			else if (newDateType.equals("Q"))
			{
				data = DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
			}
			else if (newDateType.equals("H"))
			{
				data = DateUtil.getNewDate("month", 6, DateUtil.getCurrentDateUSFormat());
			}
			else if (newDateType.equals("Y"))
			{
				data = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public List getReportToData(String date, String time, SessionFactory connection)
	{

		List reportdatalist = new ArrayList();
		StringBuffer query = new StringBuffer();
		query.append("select report_conf.id,report_conf.report_level,report_conf.dept,report_conf.sms,report_conf.mail,report_conf.report_type,report_conf.module,emp.empName,emp.mobOne,emp.emailIdOne,report_conf.email_subject,dept.deptName,report_conf.empId,report_conf.report_date,report_conf.report_time from report_configuration as report_conf ");
		query.append(" Inner join employee_basic as emp on report_conf.empId=emp.id ");
		query.append(" Inner join department as dept on report_conf.dept=dept.id ");
		query.append(" where report_conf.report_date='" + date + "' and report_conf.report_time='" + time + "'");
		// System.out.println(""+query);
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			reportdatalist = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e)
			{

			}
		}
		return reportdatalist;
	}

	@SuppressWarnings("unchecked")
	public List<CPSPOJO> getData4CPS(SessionFactory connection, String reportType, String empId, String deptId, String report_time, String serviceId)
	{
		List dataList = new ArrayList();
		List<CPSPOJO> report = new ArrayList<CPSPOJO>();
		StringBuilder query = new StringBuilder("");
		query.append(" select cpd.id, cpd.ticket, cpd.corp_type, cm.CUSTOMER_NAME, ");
		query.append(" cpd.feed_status, cpd.patient_name, cpd.uhid, cpd.pat_mobile, ");
		query.append(" cpd.pat_email, cpd.pat_gender, cpd.pat_state, cpd.pat_city, ");
		query.append(" cpd.pat_country, csm.service_name, cpd.med_location, ");
		query.append(" cpd.remarks,  cpd.date, cpd.ac_manager, cpd.service_book_time, ");
		query.append(" cpd.service_remark, cpd.pat_dob , cpd.level, emp.empName,cpd.preferred_time,  ");
		query.append(" csd.ehc_pack_type,csd.ehc_pack,csd.opd_spec,csd.opd_doc,csd.radio_mod, ");
		query.append(" csd.ipd_spec,csd.ipd_doc,csd.ipd_bed,csd.ipd_pat_type,csd.daycare_spec,csd.daycare_doc,csd.lab_mod,csd.em_spec,csd.em_spec_assis,csd.diagnostics_test,cpd.status ");
		query.append(" from corporate_patience_data as cpd ");
		query.append(" inner join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name ");
		query.append(" inner join cps_service as csm on csm.id=cpd.services ");
		query.append(" inner join employee_basic as emp on emp.id=cpd.service_manager ");
		query.append(" inner join cps_services_details as csd on csd.CPSId=cpd.id ");
	  	if (deptId != null && deptId.equalsIgnoreCase("19") && reportType != null && !reportType.equals("") && reportType.equals("D"))
		{
			if (Integer.parseInt(report_time.split(":")[0]) <= 12) 
				query.append(" where   cpd.service_book_time like '" + DateUtil.getCurrentDateUSFormat() + "%' and cpd.status='Scheduled' ");
			else if (Integer.parseInt(report_time.split(":")[0]) > 12) 
				query.append(" where   ((cpd.service_book_time like '" + DateUtil.getNextDateAfter(1) + "%' and cpd.status In ('Scheduled')) or (cpd.service_book_time like '" + DateUtil.getCurrentDateUSFormat() + "%')) ");
			query.append(" and cpd.services='" + serviceId + "' ");
		}
		
		if (deptId != null && deptId.equalsIgnoreCase("18") && reportType != null && !reportType.equals("") && reportType.equals("D"))
		{
			if (Integer.parseInt(report_time.split(":")[0]) <= 12) 
				query.append(" where   cpd.service_book_time like '" + DateUtil.getCurrentDateUSFormat() + "%' and cpd.status='Scheduled' ");
			else if (Integer.parseInt(report_time.split(":")[0]) > 12) 
				query.append(" where   ((cpd.service_book_time like '" + DateUtil.getNextDateAfter(1) + "%' and cpd.status In ('Scheduled')) or (cpd.service_book_time like '" + DateUtil.getCurrentDateUSFormat() + "%')) ");
			query.append(" and cpd.services='2' and cpd.feed_status='VVIP'");
		}
		if (deptId != null && deptId.equalsIgnoreCase("18"))
		{
			query.append(" and cm.ACCOUNT_OFFICER='" + empId + "'  ");
		}
		if (deptId != null && deptId.equalsIgnoreCase("19"))
		{
			query.append(" and cpd.service_manager='" + empId + "'  ");

		}
		
		/*else if (reportType != null && !reportType.equals("") && reportType.equals("W"))
		{
			query.append(" and cpd.service_book_time Between like '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "%' and '" + DateUtil.getCurrentDateUSFormat() + "%' ");
		}
		else if (reportType != null && !reportType.equals("") && reportType.equals("M"))
		{
			query.append(" and cpd.service_book_time Between like '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "%' and '" + DateUtil.getCurrentDateUSFormat() + "%' ");

		}
		else if (reportType != null && !reportType.equals("") && reportType.equals("Q"))
		{
			query.append(" and cpd.service_book_time Between like '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "%' and '" + DateUtil.getCurrentDateUSFormat() + "%' ");
		}
		else if (reportType != null && !reportType.equals("") && reportType.equals("H"))
		{
			query.append(" and cpd.service_book_time Between like '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "%' and '" + DateUtil.getCurrentDateUSFormat() + "%' ");
		}*/
		query.append(" order by cpd.status,cpd.patient_name ");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dataList = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}

		if (dataList != null && dataList.size() > 0)
		{
			report = setFeedbackDataforCPS(dataList);
		}
		return report;
	}

	@SuppressWarnings("unchecked")
	public List<CPSPOJO> setFeedbackDataforCPS(List data)
	{
		List<CPSPOJO> fbpList = new ArrayList<CPSPOJO>();
		CPSPOJO fbp = null;
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new CPSPOJO();
				fbp.setId(getValueWithNullCheck(object[0]));
				fbp.setTicket(getValueWithNullCheck(object[1]));
				fbp.setCorp_type(getValueWithNullCheck(object[2]));
				fbp.setCorp_name(getValueWithNullCheck(object[3]));
				fbp.setFeed_status(getValueWithNullCheck(object[4]));
				fbp.setPatient_name(getValueWithNullCheck(object[5]));

				fbp.setUhid(getValueWithNullCheck(object[6]));
				fbp.setPat_mobile(getValueWithNullCheck(object[7]));
				fbp.setPat_email(getValueWithNullCheck(object[8]));
				fbp.setPat_gender(getValueWithNullCheck(object[9]));
				fbp.setPat_state(getValueWithNullCheck(object[10]));
				fbp.setPat_city(getValueWithNullCheck(object[11]));
				fbp.setPat_country(getValueWithNullCheck(object[12]));
				fbp.setService_name(getValueWithNullCheck(object[13]));
				fbp.setServ_loc(getValueWithNullCheck(object[14]));

				fbp.setRemarks(getValueWithNullCheck(object[15]));
				fbp.setDate(getValueWithNullCheck(object[16]));
				fbp.setAc_manager(getValueWithNullCheck(object[17]));

				fbp.setService_book_time(getValueWithNullCheck(object[18]));
				fbp.setService_remark(getValueWithNullCheck(object[19]));
				fbp.setPat_dob(getValueWithNullCheck(object[20]));
				fbp.setLevel(getValueWithNullCheck(object[21]));
				fbp.setService_mgr(getValueWithNullCheck(object[22]));
				fbp.setPreferred_time(getValueWithNullCheck(object[23]));
				fbp.setEhc_pack_type(getValueWithNullCheck(object[24]));
				fbp.setEhc_pack(getValueWithNullCheck(object[25]));
				fbp.setOpd_spec(getValueWithNullCheck(object[26]));
				fbp.setOpd_doc(getValueWithNullCheck(object[27]));
				fbp.setRadio_mod(getValueWithNullCheck(object[28]));
				fbp.setIpd_spec(getValueWithNullCheck(object[29]));
				fbp.setIpd_doc(getValueWithNullCheck(object[30]));
				fbp.setIpd_bed(getValueWithNullCheck(object[31]));
				fbp.setIpd_pat_type(getValueWithNullCheck(object[32]));
				fbp.setDaycare_spec(getValueWithNullCheck(object[33]));
				fbp.setDaycare_doc(getValueWithNullCheck(object[34]));
				fbp.setLab_mod(getValueWithNullCheck(object[35]));
				fbp.setEm_spec(getValueWithNullCheck(object[36]));
				fbp.setEm_spec_assis(getValueWithNullCheck(object[37]));
				fbp.setDiagnostics_test(getValueWithNullCheck(object[38]));
				fbp.setTicket_status(getValueWithNullCheck(object[39]));

				fbpList.add(fbp);
			}
		}
		return fbpList;
	}

	@SuppressWarnings("unchecked")
	public String getCPSConfigMailForReport(String empname, Map<String, List<CPSPOJO>> currentDayResolvedDataMap)
	{
		List orgData = new LoginImp().getUserInfomation("4", "IN");
		String orgName = "";
		if (orgData != null && orgData.size() > 0)
		{
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				orgName = object[0].toString();
			}
		}
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + orgName + "</b></td></tr></tbody></table>");
		mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Corporate Patient Service</b></td></tr></tbody></table>");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(empname) + ",</b>");
		mailtext.append("<br><br><b>Hello!!!</b>");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Corporate Patient Service Report As On " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTimeHourMin() + "</b></td></tr></tbody></table>");
		if (currentDayResolvedDataMap != null && currentDayResolvedDataMap.size() > 0)
		{
			Iterator<Entry<String, List<CPSPOJO>>> hiterator = currentDayResolvedDataMap.entrySet().iterator();
			while (hiterator.hasNext())
			{
				Map.Entry<String, List<CPSPOJO>> paramPair = (Entry<String, List<CPSPOJO>>) hiterator.next();
				String str = paramPair.getKey();
				mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
				mailtext.append("<tr bgcolor='#8db7d6'>");
				mailtext.append("<td align='center'  width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;Status</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Corporate&nbsp;Name</strong></td>");
				mailtext.append("<td align='center'  width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>UHID</strong></td>");
				mailtext.append("<td align='center'  width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Type</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Name</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Mobile</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Email</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Age</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Gender</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient State</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient City</strong></td>");
				mailtext.append("<td align='center'  width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient Country</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Preferred Date & Time</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Book Date & Time</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td>");
				mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Service</strong></td>");

				if (str != null && str.equalsIgnoreCase("EHC"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>EHC Package Type</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>EHC Package Name</strong></td>");
				}
				if (str != null && str.equalsIgnoreCase("OPD") || str.equalsIgnoreCase("DayCare"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Specialty</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Doctor Name</strong></td>");
				}
				if (str != null && str.equalsIgnoreCase("IPD"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Specialty</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Doctor Name</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Bed Type</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Payment Type</strong></td>");

				}
				 
				if (str != null && str.equalsIgnoreCase("Facilitation") || str.equalsIgnoreCase("Telemedicine"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Service For</strong></td>");
				}
				if (str != null && str.equalsIgnoreCase("Radiology") || str.equalsIgnoreCase("Laboratory"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Test Name</strong></td>");
				}

				if (str != null && str.equalsIgnoreCase("Emergency"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Specialty</strong></td>");
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Assistance</strong></td>");
				}
				if (str != null && str.equalsIgnoreCase("Diagnostics") || str.equalsIgnoreCase("Laboratory"))
				{
					mailtext.append("<td align='center'   width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Test Name</strong></td>");
				}
				mailtext.append("</tr>");
				List<CPSPOJO> tempList = paramPair.getValue();
				if (tempList != null && tempList.size() > 0)
				{
					int i = 0;
					for (CPSPOJO pojoObject : tempList)
					{
						int k = ++i;
						if (k % 2 != 0)
						{
							mailtext.append("<tr  bgcolor='#ffffff'>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTicket() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTicket_status() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getCorp_name() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getUhid() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getFeed_status() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPatient_name() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_mobile() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_email() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_dob() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_gender() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_state() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_city() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_country() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getServ_loc() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPreferred_time() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getService_book_time() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getRemarks() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getService_name() + "</td>");
							if (str != null && str.equalsIgnoreCase("EHC"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEhc_pack_type() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEhc_pack() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("OPD"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getOpd_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getOpd_doc() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("IPD"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_doc() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_bed() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_pat_type() + "</td>");

							}
							if (str != null && str.equalsIgnoreCase("Radiology"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getRadio_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Laboratory"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getLab_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Facilitation"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getFacilitation_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Telemedicine"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTelemedicine_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Emergency"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEm_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEm_spec_assis() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Diagnostics"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getDiagnostics_test() + "</td>");
							}

							mailtext.append("</tr>");
						}
						else
						{
							mailtext.append("<tr  bgcolor='#e8e7e8'>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTicket() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTicket_status() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getCorp_name() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getUhid() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getFeed_status() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPatient_name() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_mobile() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_email() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_dob() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_gender() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_state() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_city() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPat_country() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getServ_loc() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getPreferred_time() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getService_book_time() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getRemarks() + "</td>");
							mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getService_name() + "</td>");
							if (str != null && str.equalsIgnoreCase("EHC"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEhc_pack_type() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEhc_pack() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("OPD"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getOpd_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getOpd_doc() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("IPD"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_doc() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_bed() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getIpd_pat_type() + "</td>");

							}
							
							if (str != null && str.equalsIgnoreCase("Radiology"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getRadio_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Laboratory"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getLab_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Facilitation"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getFacilitation_mod() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Telemedicine"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getTelemedicine_mod() + "</td>");
							}
							 
							if (str != null && str.equalsIgnoreCase("Emergency"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEm_spec() + "</td>");
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getEm_spec_assis() + "</td>");
							}
							if (str != null && str.equalsIgnoreCase("Diagnostics"))
							{
								mailtext.append("<td align='center' width='4%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pojoObject.getDiagnostics_test() + "</td>");
							}

							mailtext.append("</tr>");
						}
					}
				}
				mailtext.append("</tbody></table>");
			}

			mailtext.append(" <font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");

		}
		else
		{
			mailtext.append("<tr  bgcolor='#e8e7e8'>");
			mailtext.append("<td align='center' width='10%' colspan='7' style='color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>No Data Available.</td></tr>");

			mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");

		}
		return mailtext.toString();

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

	public List getServiceDetail(SessionFactory connection)
	{
		Session session = null;
		Transaction transaction = null;
		List dataList = null, dataList1 = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT Distinct id,service_name FROM cps_service   ");
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dataList1 = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		return dataList1;
	}

	public List getAccountManagerDetail(SessionFactory connection)
	{
		Session session = null;
		Transaction transaction = null;
		List dataList = null, dataList1 = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT Distinct acntMngr.ACCOUNT_OFFICER FROM dreamsol_bl_corp_hc_pkg as acntMngr   ");
		query.append(" inner join employee_basic as emp on emp.mobOne=acntMngr.ACCOUNT_MANAGER_MOB ");

		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dataList1 = session.createSQLQuery(query.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		return dataList1;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public void escalateToNextLevelMarkarrival(SessionFactory connection, HelpdeskUniversalHelper hUHmark, CommunicationHelper chMark)
	{

		try
		{

			StringBuilder sb = new StringBuilder();
			sb.append(" select cpd.id, cpd.corp_type, dshis.customer_name, cpd.feed_status, cpd.patient_name, cpd.uhid, cpd.pat_mobile, cpd.pat_city, ");
			sb.append(" cpd.services, cpd.med_location,cpd.preferred_time, cpd.remarks, cpd.date, cpd.time, cpd.ac_manager, cpd.status, cpd.ticket,  ");
			sb.append(" cpd.next_level_esc_date,cpd.next_level_esc_time, cpd.department, cpd.level from corporate_patience_data as cpd");
			sb.append(" INNER JOIN  dreamsol_bl_corp_hc_pkg AS dshis ON dshis.id=cpd.corp_name ");
			sb.append(" where cpd.status in ('Scheduled') AND cpd.date<='" + DateUtil.getCurrentDateUSFormat() + "' and time<='" + DateUtil.getCurrentTimeHourMin() + "' ");
		//	System.out.println("mmmvvv    " + sb);
			List data4escalate = hUHmark.getData(sb.toString(), connection);
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
						corp_type = object[1].toString();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						corp_name = object[2].toString();
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						feed_status = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						patient_name = object[4].toString();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						uhid = object[5].toString();
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						pat_mobile = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						pat_city = object[7].toString();
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						services = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						med_location = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						preferred_time = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						remarks = object[11].toString();
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						date = object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						time = object[13].toString();
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						ac_manager = object[14].toString();
					}
					if (object[15] != null && !object[15].toString().equals(""))
					{
						status = object[15].toString();
					}
					if (object[16] != null && !object[16].toString().equals(""))
					{
						ticket = object[16].toString();
					}
					if (object[17] != null && !object[17].toString().equals(""))
					{
						next_level_esc_date = object[17].toString();
					}
					if (object[18] != null && !object[18].toString().equals(""))
					{
						next_level_esc_time = object[18].toString();
					}
					if (object[19] != null && !object[19].toString().equals(""))
					{
						department = object[19].toString();
					}
					if (object[20] != null && !object[20].toString().equals(""))
					{
						level = object[20].toString();
					}
					if (status.equalsIgnoreCase("Scheduled"))
					{

						boolean escnextlvl = DateUtil.time_update(date, time);
						if (escnextlvl)
						{
							if (department != null && !department.equals("") && !department.equals("0") && level != null && !level.equals("") && !level.equals("0"))
							{

								CommonOperInterface cbt = new CommonConFactory().createInterface();
								String escTimeForDept = escTimeForDept("All", connection, date, time);
								String query = "update corporate_patience_data set date='" + DateUtil.convertDateToUSFormat(escTimeForDept.split("#")[0]) + "', time='" + escTimeForDept.split("#")[1] + "' ,service_book_time = '" + DateUtil.convertDateToUSFormat(escTimeForDept.split("#")[0]) + " " + escTimeForDept.split("#")[1] + "', next_level_esc_date='" + DateUtil.convertDateToUSFormat(escTimeForDept.split("#")[0]) + "', next_level_esc_time='" + escTimeForDept.split("#")[1] + "' where id =" + id;
								cbt.executeAllUpdateQuery(query, connection);
							}
						}
					}
				}

			}

		}

		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// Appointment Parked ESC
	public void snoozetoPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{

		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append(" select cpd.id, cpd.corp_type, dshis.customer_name, cpd.feed_status, cpd.patient_name, cpd.uhid, cpd.pat_mobile, cpd.pat_city, ");
			sb.append(" cpd.services, cpd.med_location,cpd.preferred_time, cpd.remarks, cpd.date, cpd.time, cpd.ac_manager, cpd.status, cpd.ticket,  ");
			sb.append(" cpd.parked_till_date,cpd.parked_till_time, cpd.department, cpd.level,cpd.userName ");
			// sb.append(" csd.ehc_pack_type,csd.ehc_pack,csd.em_spec,csd.em_spec_assis,csd.radio_mod,csd.opd_spec,csd.opd_doc,csd.daycare_spec,csd.daycare_doc,csd.ipd_spec,csd.ipd_doc,csd.ipd_bed,csd.ipd_pat_type,csd.lab_mod,csd.diagnostics_test ");
			sb.append(" from corporate_patience_data as cpd ");
			sb.append(" INNER JOIN  dreamsol_bl_corp_hc_pkg AS dshis ON dshis.id=cpd.corp_name ");
			// sb.append(" INNER JOIN  cps_services_details AS csd ON csd.cpsid=cpd.services ");
			sb.append(" where cpd.status in ('Appointment Parked') AND cpd.parked_till_date<='" + DateUtil.getCurrentDateUSFormat() + "' and cpd.parked_till_time <= '" + DateUtil.getCurrentTimeHourMin() + "'");
			//System.out.println("mmm" + sb);
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
						corp_type = object[1].toString();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						corp_name = object[2].toString();
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						feed_status = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						patient_name = object[4].toString();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						uhid = object[5].toString();
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						pat_mobile = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						pat_city = object[7].toString();
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						services = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						med_location = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						preferred_time = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						remarks = object[11].toString();
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						date = object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						time = object[13].toString();
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						ac_manager = object[14].toString();
					}
					if (object[15] != null && !object[15].toString().equals(""))
					{
						status = object[15].toString();
					}
					if (object[16] != null && !object[16].toString().equals(""))
					{
						ticket = object[16].toString();
					}
					if (object[17] != null && !object[17].toString().equals(""))
					{
						park_till_date = object[17].toString();
					}
					if (object[18] != null && !object[18].toString().equals(""))
					{
						park_till_time = object[18].toString();
					}
					if (object[19] != null && !object[19].toString().equals(""))
					{
						department = object[19].toString();
					}
					if (object[20] != null && !object[20].toString().equals(""))
					{
						level = object[20].toString();
					}
					if (object[21] != null && !object[21].toString().equals(""))
					{
						user_name = object[21].toString();
					}

					data4escalate = null;
					if (status.equalsIgnoreCase("Appointment Parked"))
					{
						boolean escnextlvl = DateUtil.time_update(park_till_date, park_till_time);
						if (escnextlvl)
						{
							// Chk is the parked time and action time (is parked
							// time is grater than action time)
							boolean isActionEscDiff = DateUtil.comparebetweenTwodateTime(date, time, park_till_date, park_till_time);
							if (isActionEscDiff)
							{
								// calculate the working time till parked
								String escData1 = escTime(connection);
								String diff_time = DateUtil.time_difference(date, time, escData1.split("#")[0], escData1.split("#")[1]);
								// calculate the when parked active
								String new_escalation_datetime = DateUtil.newdate_AfterEscTime(park_till_date, park_till_time, diff_time, "Y");

								// get rendom one employee from roster to assign
								// String assignTechnicianDetail =
								// selectRendomTechni(department, connection);
								// String assignTechnicianDetail =
								// getEmpNameMobByEmpId(assignTech, connection);
								if (new_escalation_datetime.length() > 0 && !new_escalation_datetime.split("#")[0].toString().equalsIgnoreCase("0000-00-00") && !new_escalation_datetime.split("#")[1].toString().equalsIgnoreCase("00:00"))
								{
									sb.setLength(0);
									sb = new StringBuilder();
									sb.append(" update corporate_patience_data set  ");
									sb.append(" next_level_esc_date='" + new_escalation_datetime.split("#")[0] + "',");
									sb.append(" next_level_esc_time='" + new_escalation_datetime.split("#")[1] + "',");
									// sb.append(" park_till_date='NA',");
									// sb.append(" park_till_time='NA',");
									sb.append(" status='Appointment'");
									sb.append(" where id=" + id + "");
									int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
									if (chkUpdate > 0)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										// update in history table
										String service_name = fetcServiceName(services, connection);

										wherClause.put("cpsid", id);
										wherClause.put("location_name", med_location);
										wherClause.put("action_by", user_name);
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("allocate_to", "OCC");
										wherClause.put("action_reason", "Parked Activate");
										wherClause.put("escalation_level", level);
										wherClause.put("status", "Appointment");
										wherClause.put("dept", "OCC");
										wherClause.put("service_name", service_name);
										wherClause.put("dept", "OCC");
										String service = null;
										if (service_name.equalsIgnoreCase("EHC"))
										{
											service = fetchPack("ehc_pack_type", "ehc_pack", id, connection);
											wherClause.put("ehc_pack_type", getValueWithNullCheck(service.split("#")[0]));
											wherClause.put("ehc_pack", getValueWithNullCheck(service.split("#")[1]));

										}
										else if (service_name.equalsIgnoreCase("IPD"))
										{
											service = fetchPack("ipd_spec", "ipd_doc", id, connection);
											wherClause.put("ipd_spec", getValueWithNullCheck(service.split("#")[0]));
											wherClause.put("ipd_doc", getValueWithNullCheck(service.split("#")[1]));
										}
										else if (service_name.equalsIgnoreCase("Radiology"))
										{
											service = fetchPack("radio_mod", "radio_mod as radio", id, connection);
											wherClause.put("radio_mod", getValueWithNullCheck(service.split("#")[0]));
										}
										else if (service_name.equalsIgnoreCase("OPD"))
										{
											service = fetchPack("opd_spec", "opd_doc", id, connection);
											wherClause.put("opd_spec", getValueWithNullCheck(service.split("#")[0]));
											wherClause.put("opd_doc", getValueWithNullCheck(service.split("#")[1]));
										}
										else if (service_name.equalsIgnoreCase("DayCare"))
										{
											service = fetchPack("daycare_spec", "daycare_doc", id, connection);
											wherClause.put("daycare_spec", getValueWithNullCheck(service.split("#")[0]));
											wherClause.put("daycare_doc", getValueWithNullCheck(service.split("#")[1]));
										}
										else if (service_name.equalsIgnoreCase("Laboratory"))
										{
											wherClause.put("lab_mod", "NA");
										}
										else if (service_name.equalsIgnoreCase("Emergency"))
										{
											service = fetchPack("em_spec", "em_spec_assis", id, connection);
											wherClause.put("em_spec", getValueWithNullCheck(service.split("#")[0]));
											wherClause.put("em_spec_assis", getValueWithNullCheck(service.split("#")[1]));
										}
										else if (service_name.equalsIgnoreCase("Diagnostics"))
										{
											service = fetchPack("diagnostics_test", "diagnostics_test as ds", id, connection);
											wherClause.put("diagnostics_test", getValueWithNullCheck(service.split("#")[0]));
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
											boolean updateFeed = cbt.insertIntoTable("cps_status_history", insertData, connection);

											if (updateFeed)
											{
												// sendMsg4ParkedActive(department,
												// level, "ORD", orderid,
												// nursingUnit, roomNo, uhid,
												// new_escalation_datetime.split("#")[0],
												// new_escalation_datetime.split("#")[1],
												// orderName, orderType,
												// assignTechnicianDetail,
												// priority, CH, connection);
											}

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
		catch (Exception e)
		{
			e.printStackTrace();
			CH.addMessage("Manab", department, "8882572103", e.toString().substring(0, 50), ticket, "Pending", "0", "CPS", connection);

		}
	}

	// Service Name Fetch
	public static String fetcServiceName(String serid, SessionFactory connection)
	{
		String service_name = null;
		StringBuilder empuery = new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		empuery.append(" select service_name from cps_service where  id ='" + serid + "'  ");
		try
		{
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				service_name = escData.get(0).toString();

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return service_name;
	}

	// Package Fetch
	public static String fetchPack(String pack1, String pack2, String id, SessionFactory connection)
	{

		String packageName = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List dataListEcho = cbt.executeAllSelectQuery("SELECT " + pack1 + "," + pack2 + " FROM cps_services_details where cpsid =" + id + "".toString(), connection);
			if (dataListEcho != null && dataListEcho.size() > 0)
			{
				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					packageName = object[0].toString() + "#" + object[1].toString();
				}
			}
		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return packageName;
	}

	String escTime(SessionFactory connection)
	{
		String esctm = "";
		try
		{

			StringBuilder empuery = new StringBuilder();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			empuery.append(" select l1Tol2 from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept='17' ");

			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

	private String escTimeForDept(String serviceName, SessionFactory connection, String date, String time)
	{
		String esctm = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append(" select l1Tol2 from  escalation_cps_detail where escSubDept ='" + serviceName + "' ");
		//	System.out.println("empuery  " + empuery);
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(date, time, escData.get(0).toString(), "Y");

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

}