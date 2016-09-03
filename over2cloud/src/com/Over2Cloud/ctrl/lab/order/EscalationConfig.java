package com.Over2Cloud.ctrl.lab.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.action.GridPropertyBean;

public class EscalationConfig 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3423396173692629418L;

	public void esclatetoNextLevel(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {
		String UHID="NA",Patient_Name="NA", SPECIMEN_NO="NA", Order_Date="NA", NURSING_UNIT_CODE="NA", escalation_date="NA", escalation_time="NA", level="NA", ord_status="NA", id="NA", labName = "NA";
		String empName= "NA";
		String empMob= "NA";
		String nxtTat= "NA";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select UHID, Patient_Name, SPECIMEN_NO, Order_Date, NURSING_UNIT_CODE, escalation_date,escalation_time,level, LONG_DESC, id, LabName from dreamsol_ip_cr_tst_vw where escalation_date='"+DateUtil.getCurrentDateUSFormat()+"' and escalation_time <= '"+DateUtil.getCurrentTimeHourMin()+"' and long_desc not in ('Resulted - Complete', 'Rejected')";
			List data = cbt.executeAllSelectQuery(query, connection);

			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					UHID = ob[0].toString();
					Patient_Name = ob[1].toString(); 
					SPECIMEN_NO = ob[2].toString();
					Order_Date = ob[3].toString();
					NURSING_UNIT_CODE = ob[4].toString();
					escalation_date = ob[5].toString();
					escalation_time = ob[6].toString();
					level = ob[7].toString();
					ord_status = ob[8].toString();
					id = ob[9].toString();
					labName = ob[10].toString();
					if(ord_status.equalsIgnoreCase("In Process") ||
							ord_status.equalsIgnoreCase("In Progress") ||
							ord_status.equalsIgnoreCase("Registered") ||
							ord_status.equalsIgnoreCase("Resulted - Preliminary") ||
							ord_status.equalsIgnoreCase("Resulted - Partial") ||
							ord_status.equalsIgnoreCase("Consumed - Partial") ||
							ord_status.equalsIgnoreCase("On Hold (By Department)") )
					{
						if(level.equalsIgnoreCase("1") || level.equalsIgnoreCase("2") || level.equalsIgnoreCase("3"))
						{
							String escdata = escalationHolder(ord_status, level, labName, connection);
							if(!escdata.equalsIgnoreCase("NA#NA#NA"))
							{
								empName= escdata.split("#")[0].toString();
								empMob= escdata.split("#")[1].toString();
								nxtTat= escdata.split("#")[2].toString();
								
							boolean sts = 	sendMsg(Patient_Name, SPECIMEN_NO, Order_Date, ord_status, level, UHID, empMob,empName, connection);
							if(sts)
							{
								String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), nxtTat, "Y");
								updateNextEsc(esctm, level, id, connection);
								
								int no = Integer.parseInt(level);
								no = no+1;
								System.out.println("no "+no);
								Map<String, Object> wherClause = new HashMap<String, Object>();
								// update in history table status, action_date, action_time, level, comment
								wherClause.put("dream_view_id", id);
								wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
								wherClause.put("action_time", DateUtil.getCurrentTime());
								wherClause.put("level", "Level " + no );
								wherClause.put("status", "Escalate "+ord_status);
								wherClause.put("specimen_no", SPECIMEN_NO);
								wherClause.put("comment",  empName+"("+empMob+")");
								
								
								//wherClause.put("dept", department);
								if (wherClause != null && wherClause.size() > 0)
								{
									//CommonOperInterface cbt = new CommonConFactory().createInterface();
									InsertDataTable ob1 = new InsertDataTable();
									List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
									for (Map.Entry<String, Object> entry : wherClause.entrySet())
									{
										ob1 = new InsertDataTable();
										ob1.setColName(entry.getKey());
										ob1.setDataName(entry.getValue().toString());
										insertData.add(ob1);
									}
									boolean updateFeed = cbt.insertIntoTable("lab_order_history", insertData, connection);
									
									if(updateFeed){
										//StringBuilder sb = new StringBuilder();
										//String queryAgainUpdate = "update dreamsol_ip_cr_tst_vw set level = 'Level "+no+"' where dream_view_id = '"+id+"' and status = '"+ord_status+"'";
										//cbt.executeAllUpdateQuery(queryAgainUpdate, connection);
										System.out.println("yes");
									}
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
			// TODO: handle exception
		}
		
		
		
	}

	private void updateNextEsc(String esctm, String level, String id,
			SessionFactory connection) {
		// TODO Auto-generated method stub
		try{
			
			if(!level.equalsIgnoreCase("NA")){
				int no = Integer.parseInt(level);
				no = no+1;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			String query = "update dreamsol_ip_cr_tst_vw set escalation_date='" +esctm.split("#")[0]+ "', escalation_time='" +esctm.split("#")[1]+ "', level = '" +no + "' where id='" +id+ "'";
			cbt.executeAllUpdateQuery(query, connection);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		
		
	}

	private boolean sendMsg(String patientName, String sPECIMENNO,
			String orderDate, String ordStatus, String level, String uHID, String empMob, String empName, SessionFactory connection) {
		// TODO Auto-generated method stub
		boolean sts = false;
		
		try{
			if(!level.equalsIgnoreCase("NA")){
				int no = Integer.parseInt(level);
				no = no+1;
			String msgTxt = "Level "+no+" Escalation Alert: \n SPECIMEN NO:"+sPECIMENNO+", order Status:"+ordStatus+", order at:"+orderDate+", Patient: "+patientName+", UHID: "+uHID+" ";
			CommunicationHelper ch = new CommunicationHelper();
			sts = ch.addMessage(empName, "AUTO", empMob, msgTxt, " ", "Pending", "0", "LabOrd", connection);
			
			
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return sts;
	}

	private String escalationHolder(String ordStatus, String level,
			String labName, SessionFactory connection) {
		// TODO Auto-generated method stub
		String empName= "NA";
		String empMob= "NA";
		String nxtTat= "NA";
		String str = "NA#NA#NA";
		try
		{
			if(!level.equalsIgnoreCase("NA")){
				int no = Integer.parseInt(level);
				no = no+1;
			
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = " ";
			if(ordStatus.equalsIgnoreCase("In Progress")){
				 query = "select esc.l"+no+", esc.tat"+no+", emp.empName, emp.mobOne from lab_report_escalation_detail as esc inner join employee_basic as emp on emp.id = esc.l2 where esc.order_status='"+ordStatus+"' and LabName = '"+labName+"'";
			}
			else
			{
				query =  "select esc.l"+no+", esc.tat"+no+", emp.empName, emp.mobOne from lab_report_escalation_detail as esc inner join employee_basic as emp on emp.id = esc.l2 where esc.order_status='"+ordStatus+"'";
			
			}
			List data = cbt.executeAllSelectQuery(query, connection);

			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					empName = ob[2].toString();
					empMob = ob[3].toString(); 
					nxtTat = ob[1].toString();
					
					str = empName+"#"+empMob+"#"+nxtTat;
				}

			}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return str;
	}
	
	



}
