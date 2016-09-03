package com.Over2Cloud.ctrl.order.pharmacy;

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
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.Over2Cloud.InstantCommunication.DailyReportExcelWrite4Attach;
import com.Over2Cloud.ctrl.cps.corporate.CPSPOJO;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
public class PharmacyServiceFileHelper {
	 
	
	String id = null;
	String assign_bed_num = "NA";
	String nursing_unit = "NA";
	String status = "NA";
	String patient_name = "NA";
	String uhid = "NA";
	String encounter_id = "NA";
	String order_date = "NA";
	String order_time = "NA";
	String escalation_date = "NA";
	String escalation_time = "NA";
	String action_date = "NA";
	String action_time = "NA";
	String snooze_time = "NA";
 	String level = "NA";
	String priority = "NA";
	String document_no = "NA";
	
	
	
	public void escalateToNextLevel(SessionFactory connection,
			HelpdeskUniversalHelper huh, CommunicationHelper ch) {
		
		try{
			
		 
		StringBuilder sb = new StringBuilder();
		sb.append(" select id, uhid, patient_name, assign_bed_num, nursing_unit, status, level, ");
		sb.append(" encounter_id, order_date, order_time, escalation_date, escalation_time ,priority,document_no ");
		sb.append(" from pharma_patient_detail where status Not in ('Close','Close-P','Parked', 'HIS Cancel') AND nursing_code IN ('NU50', 'NU51', 'NU21', 'NU81', 'NU64', 'NU66', 'NU40', 'NU41', 'NU42', 'NU43', 'NU82', 'NU83') AND escalation_date='"+DateUtil.getCurrentDateUSFormat()+"' AND escalation_time<='"+DateUtil.getCurrentTimeHourMin()+"' and order_date");
		
		System.out.println("sb.toString() :  "+sb.toString());
		
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
					uhid = object[1].toString();
				}
				if (object[2] != null && !object[2].toString().equals(""))
				{
					patient_name = object[2].toString();
				}
				if (object[3] != null && !object[3].toString().equals(""))
				{
					assign_bed_num = object[3].toString();
				}
				if (object[4] != null && !object[4].toString().equals(""))
				{
					nursing_unit = object[4].toString();
				}
				if (object[5] != null && !object[5].toString().equals(""))
				{
					status = object[5].toString();
				}
				if (object[6] != null && !object[6].toString().equals(""))
				{
					level =object[6].toString();
				}
				if (object[7] != null && !object[7].toString().equals(""))
				{
					encounter_id = object[7].toString();
				}
				if (object[8] != null && !object[8].toString().equals(""))
				{
					order_date = object[8].toString();
				}
				if (object[9] != null && !object[9].toString().equals(""))
				{
					order_time = object[9].toString();
				}
				if (object[10] != null && !object[10].toString().equals(""))
				{
					escalation_date = object[10].toString();
				}
				if (object[11] != null && !object[11].toString().equals(""))
				{
					escalation_time = object[11].toString();
				}
				if (object[12] != null && !object[12].toString().equals(""))
				{
					priority = object[12].toString();
				}
				if (object[13] != null && !object[13].toString().equals(""))
				{
					document_no = object[13].toString();
				}
			 	// escalation for OCC level 
			 		
					boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
					if (escnextlvl)
					{
						if ( level != null && !level.equals("") && !level.equals("0"))
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
								isMsgSent = sendMsg(id, encounter_id,document_no,status, patient_name,uhid,assign_bed_num, nursing_unit,priority,order_date,order_time, colName2, "L1ToL2",  "82","2","Pharmacy",connection, ch );
							}
							else if(calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
							{
								colName1 = "escLevelL3L4";
								colName2 = "l3Tol4";
								isMsgSent = sendMsg(id, encounter_id,document_no,status, patient_name,uhid, assign_bed_num, nursing_unit,priority,order_date,order_time, colName2, "L2ToL3",  "82","3","Pharmacy",connection, ch );
										}
							else if(calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
							{
								colName1 = "escLevelL4L5";
								colName2 = "l4Tol5";
								isMsgSent = sendMsg(id, encounter_id,document_no,status, patient_name,uhid, assign_bed_num, nursing_unit,priority,order_date,order_time, colName2, "L3ToL4",  "82","4","Pharmacy",connection, ch );
										}
							else if(calculationLevel != null && calculationLevel.equalsIgnoreCase("5"))
							{
								colName1 = "escLevelL5L6";
								colName2 = "l5Tol6";
								isMsgSent = sendMsg(id, encounter_id,document_no,status, patient_name,uhid, assign_bed_num, nursing_unit,priority,order_date,order_time, colName2, "L4ToL5",  "82","5","Pharmacy",connection, ch );
											
							}
				 		}
					}
			 	 
	  		}
 		}
	 }

	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	
	
	public void SnoozetoPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
			{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ord.id, ord.encounter_id, ord.uhid, ");
		sb.append(" ord.status,ord.level,ord.escalation_date, ord.escalation_time,ord.action_date, ord.action_time,ord.snooze_time ");
		sb.append(" FROM pharma_patient_detail as ord ");
	 	sb.append(" WHERE ord.status IN ('Parked')");
		sb.append(" and ord.snooze_time <= '" + DateUtil.getCurrentTimeHourMin() + "'");
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
					encounter_id = object[1].toString();
				}

				 
				if (object[2] != null && !object[2].toString().equals(""))
				{
					uhid = object[2].toString();
				}

				if (object[3] != null && !object[3].toString().equals(""))
				{
					status = object[3].toString();
				}

		 
				if (object[4] != null && !object[4].toString().equals(""))
				{
					level = object[4].toString();
				}
				 
				if (object[5] != null && !object[5].toString().equals(""))
				{
					escalation_date = object[5].toString();
				}

				if (object[6] != null && !object[6].toString().equals(""))
				{
					escalation_time = object[6].toString();
				}
				if (object[7] != null && !object[7].toString().equals(""))
				{
					action_date = object[7].toString();
				}

				if (object[8] != null && !object[8].toString().equals(""))
				{
					action_time = object[8].toString();
				}
 

				if (object[9] != null && !object[9].toString().equals(""))
				{
					snooze_time = object[9].toString();
				}
			 
				if (status.equalsIgnoreCase("Parked"))
				{
			 		boolean escnextlvl = DateUtil.time_update(DateUtil.getCurrentDateUSFormat(),snooze_time);
			 		System.out.println("escnextlvl : "+escnextlvl);
					if (escnextlvl)
					{
			 			boolean isActionEscDiff = DateUtil.comparebetweenTwodateTime(action_date, action_time,   DateUtil.getCurrentDateUSFormat(),snooze_time);
			 			System.out.println("isActionEscDiff : "+isActionEscDiff);
			 			
			 			if (isActionEscDiff)
						{
				 			String diff_time = DateUtil.time_difference(action_date, action_time, escalation_date, escalation_time);
				 			String new_escalation_datetime = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), snooze_time, diff_time, "Y");
				 			if (new_escalation_datetime.length() > 0 && !new_escalation_datetime.split("#")[0].toString().equalsIgnoreCase("0000-00-00") && !new_escalation_datetime.split("#")[1].toString().equalsIgnoreCase("00:00"))
				 			{

								sb.setLength(0);
								sb = new StringBuilder();
								sb.append(" update pharma_patient_detail set  ");
								sb.append(" escalation_date='" + new_escalation_datetime.split("#")[0] + "',");
								sb.append(" escalation_time='" + new_escalation_datetime.split("#")[1] + "',");
								sb.append(" status='Ordered'");
						 		sb.append(" where encounter_id=" + encounter_id + "");
					 			int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
					 			if (chkUpdate > 0)
								{
									Map<String, Object> wherClause = new HashMap<String, Object>();
				 					wherClause.put("encounter_id", encounter_id);
									wherClause.put("action_by", "0");
									wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
									wherClause.put("action_time", DateUtil.getCurrentTime());
							 		wherClause.put("comment", "Parked Activate");
									wherClause.put("escalation_level", level);
									wherClause.put("status", "Pending");
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
										boolean updateFeed = cbt.insertIntoTable("pharma_patient_data_history", insertData, connection);

										if (updateFeed)
										{
											//sendMsg4ParkedActive(department, level, "ORD", orderid, nursingUnit, roomNo, uhid, new_escalation_datetime.split("#")[0], new_escalation_datetime.split("#")[1], orderName, orderType, assignTechnicianDetail, priority, CH, connection);
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
	} catch (Exception e)
	{
		e.printStackTrace();
	 
	}
}
 
	
	
	
	
 private int sendMsg(String id, String encounter_id,String documentNo, String status, String patient_name,String uhid,String assign_bed_num, String nursing_unit,String priority,String order_date,String order_time, String colName2, String colName3,String sub_dept,String level, String module, SessionFactory connection, CommunicationHelper ch) {
 		int retnResult = 0;
		boolean sent = false ;
		String levelMsg = "";
	  	String EscHolder =getMeAppendedName( selectTechniToEsc(sub_dept,level,module,connection));
	 	if(EscHolder!=null && !EscHolder.equalsIgnoreCase("") && EscHolder.contains(",")){
		String[] arrEcs = EscHolder.trim().split(",");
		for (int i = 0; i < arrEcs.length; i++) {
		 	levelMsg = "Pharmacy Escalation Alert: "+colName3 +", Encounter No: "+encounter_id+ ",Document No: "+documentNo+ ", UHID: "+uhid+ ", Patient Name: "+patient_name+" request for Medicine on "+DateUtil.convertDateToIndianFormat(order_date)+"   "+order_time+ " is escalated on:"+DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin();
			boolean isSent = ch.addMessage(arrEcs[i].toString().split("#")[0], sub_dept, arrEcs[i].toString().split("#")[1], levelMsg, encounter_id, "Pending", "0", module, connection);
			if (isSent)
			{
				sent = isSent;
		 		}
			
		}
	}
		else if(EscHolder!=null && !EscHolder.equalsIgnoreCase("") && !EscHolder.contains(",")){
		 	levelMsg = "Pharmacy Escalation Alert: "+colName3 +", Encounter No: "+encounter_id+ ",Document No: "+documentNo+ ", UHID: "+uhid+ ", Patient Name: "+patient_name+" request for Medicine on "+DateUtil.convertDateToIndianFormat(order_date)+"   "+order_time+ " is escalated on:"+DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin();
		 	boolean isSent = ch.addMessage(EscHolder.split("#")[0], sub_dept, EscHolder.split("#")[1], levelMsg, encounter_id, "Pending", "0", module, connection);
			if (isSent)
			{
				sent = isSent;

			}
		}
	 		if(sent == true){
			
			String getnextTime = escTime(colName2, sub_dept, level,priority,connection);
	 		StringBuilder sb = new StringBuilder();
			sb.append(" update pharma_patient_detail set  ");
			sb.append(" escalation_date='" + getnextTime.split("#")[0] + "',");
			sb.append(" escalation_time='" + getnextTime.split("#")[1] + "',");
			sb.append(" level="+level);
			sb.append(" where id=" + id + "");
			
			int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
			if(chkUpdate > 0)
			{
		 		Map<String, Object> wherClause = new HashMap<String, Object>();
	 			wherClause.put("encounter_id", encounter_id);
				wherClause.put("action_by", "Auto");
				wherClause.put("close_by_id", "NA");
				wherClause.put("close_by_name", "NA");
				wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
		 		wherClause.put("comment", "Escalate");
				wherClause.put("escalation_level", level);
				wherClause.put("status", "Escalate");
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
					 cbt.insertIntoTable("pharma_patient_data_history", insertData, connection);
					insertData.clear();
				}
	 		}
		}
		return retnResult;
	}

	
	// method for get next level escalation date and time author : manab 03-09-2015
	private String escTime(String col1, String sub_dept, String level, String priority, SessionFactory  connection )
	{
		String esctm = "";
		try
		{
 			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append(" select "+col1+" from escalation_order_detail where escLevelL1L2='Customised' and escSubDept='"+sub_dept+"' and priority='"+priority+"'");
			
			System.out.println("From Esc Table  :"+empuery.toString());
			
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}
	 	 
	
//For getting rendom for Escalation  alert
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List selectTechniToEsc(String sub_department,String level,String module, SessionFactory connection)
	{
		List empList= new ArrayList();
 		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT emp.id, emp.empName, emp.mobOne  FROM employee_basic AS emp");
		 	sb.append("  INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id`");
			sb.append("  INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id`");
			sb.append("  WHERE subdept.id=" + sub_department + " ");
			sb.append(" and cc.level='"+level+"' AND cc.moduleName='" + module + "' and cc.work_status='0' ");
			
			System.out.println("Techniciane : "+ sb.toString());
			
	  		List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);
	 		if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					String assignEmpDetails = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString();
		 			empList.add(assignEmpDetails);
				}
					
			}

		} catch (Exception e)
		{
		 		e.printStackTrace();
		}
 		return empList;
	}
 	
	public String getMeAppendedName(List escalateTo){
		String temp="";
		int count=0;
		for (Iterator iterator3 = escalateTo.iterator(); iterator3.hasNext();)
		{
			Object object2 = (Object) iterator3.next();
			if(count<escalateTo.size())
			{
				temp=temp+object2.toString().split("#")[1] + "#"+object2.toString().split("#")[2]+", ";
			}else
			{
				temp=temp+object2.toString().split("#")[1] + "#"+object2.toString().split("#")[2];
			}
			count++;
		}
		
		return temp;
	}

	 
	 
	public String getValueWithNullCheck(Object value)
	{
	return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
}