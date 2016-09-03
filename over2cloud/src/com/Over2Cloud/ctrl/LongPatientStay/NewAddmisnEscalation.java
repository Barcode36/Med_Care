package com.Over2Cloud.ctrl.LongPatientStay;

import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class NewAddmisnEscalation implements Runnable
{
	String id = null;
	String ticket_no = null;
	String UHIID = null;
	String PATIENT_NAME = null;
	String ASSIGN_BED_NUM = null;
	String ATTENDING_PRACTITIONER_NAME = null;
	String ADMITTING_PRACTITIONER_NAME = null;
	String SPECIALITY = null;
	String ADMISSION_DATE = null;
	String ADMISSION_TIME = null;
	String nursing_unit = null;
	String floor = null;
	String allot_to = null;
	String status = null;
	String escalation_date = null;
	String escalation_time = null;
	String level = null;
	
	
	
	private static final Log log = LogFactory.getLog(NewAddmisnEscalation.class);
	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	HelpdeskUniversalHelper HUH=null;
	CommunicationHelper ch = new CommunicationHelper();
	public Map<String, String> nurshingMap ;
	private int total;
	
	public NewAddmisnEscalation(SessionFactory connection, HelpdeskUniversalHelper HUHObj,  Session sessionHis ) {
		this.connection = connection;
		this.HUH=HUHObj;
	}
	
	public static void main(String[] args) {
		Thread.State state ;
		try{
			
			System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			System.out.println("After HIS DB connection"+sessionHis);
			 HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				Thread uclient=new Thread(new NewAddmisnEscalation(connection, HUH, sessionHis));
				System.out.println("Thread Created Successfuly!!");
				state = uclient.getState(); 
				System.out.println("Thread's State:: "+state);
			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
				System.out.println("Thread Started Successfuly!!");
			
		}catch(Exception E){
			try {
				OutputStream out = new FileOutputStream(new File("c://output.txt"));
				byte[] b=E.toString().getBytes();
				out.write(b);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			E.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		
		try{
			while(true){
			
				//t2mvirtual.getVirtualNoData(connection);
				//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
				/*if(
						( DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "17:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && !DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
						( DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && !DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "09:00" ))
					
				)
				{
				
					System.out.println("stop escalation");
				}
				else{*/
				
				
				
				System.out.println("yes this is the time to Escalation");		
				
				escalateToNextLevel (log, connection, HUH, sessionHis, ch);
				//}
				
				System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				Thread.sleep(1000* 60 );
				System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

				Runtime rt = Runtime.getRuntime();
				rt.gc();

			}
							}
		 catch (Exception e)
		 {
			 e.printStackTrace();
			 }


		
	}

	private void escalateToNextLevel(Log log, SessionFactory connection, HelpdeskUniversalHelper HUH, Session sessionHis2, CommunicationHelper ch)
	{
		// TODO Auto-generated method stub
		
		try
		{
			String nxtEsc=null;
			StringBuilder sb = new StringBuilder();
			sb.append("select id, ticket_no, UHIID, PATIENT_NAME, ASSIGN_BED_NUM, ATTENDING_PRACTITIONER_NAME, ADMITTING_PRACTITIONER_NAME, ");
			sb.append(" SPECIALITY, ADMISSION_DATE, ADMISSION_TIME, nursing_unit, floor, allot_to, status, escalation_date, escalation_time, level ");
			sb.append("   from dreamsol_ip_vw_ticket where  status='Pending' and escalation_date <='" + DateUtil.getCurrentDateUSFormat() + "' and level!='4' ");    //by aarif 14-12-2015
			System.out.println("Escalation : "+sb);
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
						ticket_no = object[1].toString();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						UHIID = object[2].toString();
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						PATIENT_NAME = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						ASSIGN_BED_NUM = object[4].toString();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						ATTENDING_PRACTITIONER_NAME = object[5].toString();
					}
					if(object[6] != null && !object[6].toString().equals(""))
					{
						ADMITTING_PRACTITIONER_NAME=object[6].toString();
					}
					if(object[7] != null && !object[7].toString().equals(""))
					{
						SPECIALITY=object[7].toString();
					}
					if(object[8] != null && !object[8].toString().equals(""))
					{
						ADMISSION_DATE=object[8].toString();
					}
					if(object[9] != null && !object[9].toString().equals(""))
					{
						ADMISSION_TIME=object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						nursing_unit = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						floor = object[11].toString();
					}
					if(object[12] != null && !object[12].toString().equals(""))
					{
						allot_to=object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						status = object[13].toString();
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						escalation_date = object[14].toString();
					}
					if(object[15] != null && !object[15].toString().equals(""))
					{
						escalation_time=object[15].toString();
					}
					if(object[16] != null && !object[16].toString().equals(""))
					{
						level=object[16].toString();
					}
					
					String colName1 = null;
					String colName2 = null;
					String calculationLevel = null;
					boolean isMsgSent = false;
					boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
					String str = "NA";
					if (escnextlvl)
					{
						calculationLevel = String.valueOf(Integer.parseInt(level) + 1);
						str =selectEscHoldergetMeAppendedName(calculationLevel, "Operation Quality","LPS",connection);
						if (calculationLevel != null && calculationLevel.equalsIgnoreCase("2"))
						{
							colName1 = "escLevelL2L3";
							colName2 = "l2Tol3";
							
							
							if(!"NA#NA".equalsIgnoreCase(str))
							{
								isMsgSent = sendMsg4Esc(connection, ch, str, calculationLevel);
								
							}
							

							
						}
						if (calculationLevel != null && calculationLevel.equalsIgnoreCase("3"))
						{
							colName1 = "escLevelL3L4";
							colName2 = "l3Tol4";
							
							
							if(!"NA#NA".equalsIgnoreCase(str))
							{
								isMsgSent = sendMsg4Esc(connection, ch, str, calculationLevel);
								
							}
							

							
						}
						if (calculationLevel != null && calculationLevel.equalsIgnoreCase("4"))
						{
							colName1 = "escLevelL4L5";
							colName2 = "l4Tol5";
							
							
							if(!"NA#NA".equalsIgnoreCase(str))
							{
								isMsgSent = sendMsg4Esc(connection, ch, str, calculationLevel);
								
							}
							

							
						}
						
						
						
						if(colName1!=null && colName2!=null)
						{
							 nxtEsc = escTime("LPS", colName2,  colName1, "86", connection);
								if (isMsgSent)
								{
									sb.setLength(0);
									sb = new StringBuilder();
									sb.append(" update dreamsol_ip_vw_ticket set  ");

									if(nxtEsc!=null && !"NA".equalsIgnoreCase(nxtEsc) && !"".equalsIgnoreCase(nxtEsc))
									{
										sb.append(" escalation_date='" + nxtEsc.split("#")[0] + "',");
										sb.append(" escalation_time='" + nxtEsc.split("#")[1] + "',");
									}
									sb.append(" level=" + calculationLevel );
									sb.append(" where id=" + id + "");

									int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);

									if (chkUpdate > 0)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										// update in history table status, action_date, action_time, level, comment
										wherClause.put("dream_id", id);
										wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("level", "Level " + calculationLevel);
										wherClause.put("status", "Escalate");
										wherClause.put("comment",  str.split("#")[0]+"("+str.split("#")[1]+")");
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
											boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_vw_ticket_history", insertData, connection);
											insertData.clear();
										}
									}
								}
						}
						
					}
					
					
					
				}
			}
			
		
				
	
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			connection.close();
		}
	}

	private String escTime(String moduleName, String colName2, String colName1, String subDept, SessionFactory connection)
	{

		// TODO Auto-generated method stub
		String escTimeNext = "NA";
		
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  "+colName2+", escSubDept FROM escalation_order_detail WHERE escSubDept='"+subDept+"' AND module='"+moduleName+"'  ");
			
		System.out.println("sb >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>              "+sb);
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					//escTimeNext = object[0].toString()  ;
					escTimeNext = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object[0].toString(), "Y");
					//System.out.println("assignEmpDetails>>> "+assignEmpDetails);
					//empList.add(assignEmpDetails);
				}
					
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return escTimeNext;
	
	}

	private boolean sendMsg4Esc(SessionFactory connection, CommunicationHelper ch, String str, String calculationLevel)
	{
		// TODO Auto-generated method stub
		boolean  isSent = false;
		String levelMsg = "New Admission Escalation Alert: \n Dear Sir, Ticket ID "+ticket_no+", for new admission - "+UHIID+"  ("+PATIENT_NAME+"),\n Location: "+ nursing_unit + "/ " + floor + "/ " + ASSIGN_BED_NUM +" , \n Allotted to "+allot_to+" has not been closed. \n Ticket is now escalated to you at level "+calculationLevel+" for required action. \n Thanks.";
		//String levelMsg = "Level- "+ calculationLevel+ " New Admission Escalation Alert: \n Ticket: " + ticket_no + " UHID: " + UHIID+" ("+PATIENT_NAME + "),\n Location: " + nursing_unit + "/ " + floor + "/ " + ASSIGN_BED_NUM + " \n Spec: "+SPECIALITY+", \n Att. Doc: " + ATTENDING_PRACTITIONER_NAME + ", \n adm. Doc: " + ADMITTING_PRACTITIONER_NAME + ", \n To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + " - " + escalation_time;
		isSent = ch.addMessage(str.split("#")[0], "Operation Quality", str.split("#")[1], levelMsg, ticket_no, "Pending", "0", "LPS", connection);
		if (isSent)
		{
			System.out.println("sms sent to  empMob: "+str.split("#")[1]+" orderid: "+ticket_no);
			
		}
		
		return isSent;
	}

	private String selectEscHoldergetMeAppendedName(String calculationLevel, String subDept, String module, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String empDetails = "NA#NA";
		
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("select emp.empName, emp.mobOne from employee_basic as emp ");
			sb.append("  inner join compliance_contacts as cc on cc.emp_id = emp.id");
			sb.append("  where cc.moduleName= '"+module+"' and forDept_id = (select id from subdepartment where subdeptname = '"+subDept+"') and level = '"+calculationLevel+"'");
			sb.append("  and cc.work_status='0' ");
			
		System.out.println("sb >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>              "+sb);
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString() ;
					//System.out.println("assignEmpDetails>>> "+assignEmpDetails);
					//empList.add(assignEmpDetails);
				}
					
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return empDetails;
	}

	
}
