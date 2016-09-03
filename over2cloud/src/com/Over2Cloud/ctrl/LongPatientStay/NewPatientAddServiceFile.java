package com.Over2Cloud.ctrl.LongPatientStay;

import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

public class NewPatientAddServiceFile implements Runnable {

private static final Log log = LogFactory.getLog(NewPatientAddServiceFile.class);
	

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	//private HISTESTHelper CDIH =  new HISTESTHelper();
	//private HISHelperRedirectServer CDIHRe = new HISHelperRedirectServer();
	//private MailReader mailread = new MailReader();
	CommunicationHelper ch = new CommunicationHelper();
	public Map<String, String> nurshingMap ;
	private List<Object> masterViewProp = new ArrayList<Object>();
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	private int total;
	
	public NewPatientAddServiceFile(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
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
			
				Thread uclient=new Thread(new NewPatientAddServiceFile(connection, sessionHis));
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
				
				System.out.println(DateUtil.getCurrentTime());
				if((DateUtil.getCurrentDay()!=0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "17:00" )))
						{
							fetchNewAddPatientData (log, connection, sessionHis, ch);
						}
				else{
					System.out.println("Off time ");
				}
				
				
				
				//fetchICuMovement(log, connection, sessionHis, ch);
				//fetchAllNurshingPatientData (log, connection, sessionHis, ch);
			
				
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

	private void fetchNewAddPatientData(Log log2, SessionFactory connection,
			Session sessionHis2, CommunicationHelper ch2) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		System.out.println("day  "+day);
		String floor = "NA";
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					String lastDateTime = fetchLastdateTime(connection);
						rs = st.executeQuery(" SELECT * FROM Dreamsol_IP_VW where ADMISSION_DATE >= TO_DATE('" + lastDateTime + "','dd/mm/yyyy hh24:mi:ss') and ASSIGN_BED_NUM IS NOT NULL order by ADMISSION_DATE  ");
						//commonJSONArray = new JSONArray();
						
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
							System.out.println(" name one " + i + " : " + name);
						}
						//JSONObject listObject = new JSONObject() UHIID;
						int i = 0;
						
						
						
						/*
						
							List lastIdList = cbt.executeAllSelectQuery(" TRUNCATE TABLE  Dreamsol_IP_VW ".toString(), connection);*/
						CommonOperInterface cbt = new CommonConFactory().createInterface();
							
							
						
						while (rs.next())
						{
							
							boolean chkduplicate = chkDupli(rs.getString("UHIID"), rs.getString("ADMISSION_DATE").split(" ")[0], connection );
							System.out.println("ckh update "+chkduplicate);
							if(!chkduplicate && !rs.getString("LONG_DESC").equalsIgnoreCase("Mediclinic Daycare"))
							{
							 
								ob = new InsertDataTable();
								ob.setColName("UHIID");
								if (rs.getString("UHIID") != null && !rs.getString("UHIID").toString().equals(""))
								{
									
									ob.setDataName(rs.getString("UHIID"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								ob = new InsertDataTable();
								ob.setColName("PATIENT_NAME");
								if (rs.getString("PATIENT_NAME") != null && !rs.getString("PATIENT_NAME").toString().equals(""))
								{
									ob.setDataName(rs.getString("PATIENT_NAME"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								ob = new InsertDataTable();
								ob.setColName("ASSIGN_BED_NUM");
								if (rs.getString("ASSIGN_BED_NUM") != null && !rs.getString("ASSIGN_BED_NUM").toString().equals(""))
								{
									ob.setDataName(rs.getString("ASSIGN_BED_NUM"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								
								ob = new InsertDataTable();
								ob.setColName("ATTENDING_PRACTITIONER_NAME");
								if (rs.getString("ATTENDING_PRACTITIONER_NAME") != null && !rs.getString("ATTENDING_PRACTITIONER_NAME").toString().equals(""))
								{
									ob.setDataName(rs.getString("ATTENDING_PRACTITIONER_NAME"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								
								
								
								 
								 
								 	ob = new InsertDataTable();
									ob.setColName("ADMITTING_PRACTITIONER_NAME");
									if (rs.getString("ADMITTING_PRACTITIONER_NAME") != null && !rs.getString("ADMITTING_PRACTITIONER_NAME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMITTING_PRACTITIONER_NAME"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									ob = new InsertDataTable();
									ob.setColName("SPECIALITY");
									if (rs.getString("SPECIALITY") != null && !rs.getString("SPECIALITY").toString().equals(""))
									{
										ob.setDataName(rs.getString("SPECIALITY"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_DATE");
									if (rs.getString("ADMISSION_DATE") != null && !rs.getString("ADMISSION_DATE").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									
									ob = new InsertDataTable();
									ob.setColName("Day_count");
									if (rs.getString("ADMISSION_DATE") != null && !rs.getString("ADMISSION_DATE").toString().equals(""))
									{
										String dateOne = rs.getString("ADMISSION_DATE").split(" ")[0];
										
										
										ob.setDataName(DateUtil.getNoOfDays(DateUtil.getCurrentDateUSFormat(), dateOne));
										//ob.setDataName(rs.getString("ADMISSION_DATE").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_TIME");
									if (rs.getString("ADMISSION_DATE") != null && !rs.getString("ADMISSION_DATE").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE").split(" ")[1].split(":")[0]+":"+rs.getString("ADMISSION_DATE").split(" ")[1].split(":")[1]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									ob = new InsertDataTable();
									ob.setColName("nursing_code");
									System.out.println( "111111111");
									if (rs.getString("Assign_care_locn_code") != null && !rs.getString("Assign_care_locn_code").toString().equals(""))
									{
										ob.setDataName(rs.getString("Assign_care_locn_code"));
										
										floor = fetchFloorName(rs.getString("Assign_care_locn_code").toString(), connection);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("nursing_unit");
									if (rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
									{
										ob.setDataName(rs.getString("LONG_DESC"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									ob = new InsertDataTable();
									ob.setColName("subdept_id");
									ob.setDataName("55");
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("status");
									ob.setDataName("Pending");
									insertData.add(ob);
									
									String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:59", "Y");
									
									ob = new InsertDataTable();
									ob.setColName("escalation_date");
									
									
									ob.setDataName(esctm.split("#")[0]);
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("escalation_time");
									ob.setDataName(esctm.split("#")[1]);
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("level");
									ob.setDataName("1");
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("ticket_no");
									String ticket = ticketMaker(connection);
									
									if (ticket == null)
									{
										ticket = "NEWAD1000";
									}
									
									ob.setDataName(ticket);
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("floor");
									ob.setDataName(floor);
									insertData.add(ob);
									
									
									String sendAlertTo = sendAlert(floor, ticket, rs.getString("ASSIGN_BED_NUM"), rs.getString("Assign_care_locn_code"),  connection );
									
									
									ob = new InsertDataTable();
									ob.setColName("allot_to");
									
									if(!"NA".equalsIgnoreCase(sendAlertTo.split("#")[0].toString()))
									{
									ob.setDataName(sendAlertTo.split("#")[0].toString());
									}
									else {
										ob.setDataName(floor);
									}
									insertData.add(ob);
									
									
									
									
										
										String msg = "New Admission Ticket: "+ticket+",\n UHID: "+rs.getString("UHIID")+" ("+rs.getString("PATIENT_NAME")+"),\n Bed No:"+rs.getString("ASSIGN_BED_NUM")+",\n Att. Doc: "+rs.getString("ATTENDING_PRACTITIONER_NAME")+"\n Adm. Doc: "+rs.getString("ADMITTING_PRACTITIONER_NAME")+"\n Spel: "+rs.getString("SPECIALITY")+" \n Please attend the patient before "+esctm.split("#")[1].toString()+" on "+DateUtil.convertDateToIndianFormat(esctm.split("#")[0]) +" \n Team Medanta" ;
										
										//System.out.println(msg);
										boolean sendMsg = false;
										if(sendAlertTo.split("#")[0].toString().equalsIgnoreCase("Dr Aditi"))
										{
											sendMsg = ch.addMessage(sendAlertTo.split("#")[0].toString(), "New Adm", sendAlertTo.split("#")[1].toString(), msg , "", "Pending", "0", "LPS", connection);
											sendMsg = ch.addMessage("Chandrabhan", "New Adm", "8826336111", msg , "", "Pending", "0", "LPS", connection);
										}
										else{
											sendMsg = ch.addMessage(sendAlertTo.split("#")[0].toString(), "New Adm", sendAlertTo.split("#")[1].toString(), msg , "", "Pending", "0", "LPS", connection);
										}
										
										
									CommonOperInterface cot = new CommonConFactory().createInterface();
									int id = cot.insertDataReturnId("Dreamsol_IP_VW_ticket", insertData, connection);
									System.out.println("id ===  "+id);
									insertData.clear();
									}
									
									
									//System.out.println("id ===  "+id);
							}
						
						
					
						
					//	System.out.println("nurshingMap "+nurshingMap);
						
						//System.out.println("i  ==  "+i);
						//commonJSONArray.add(listObject);
					
				
				//returnResult = "success";
				
				
				
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		/*
		 * } else { returnResult = "login"; }
		 */
	
		
	
		
	
	
		
	}
	
	private String sendAlert(String floor, String ticket,
			String bed_no, String NU,  SessionFactory connection) {
		// TODO Auto-generated method stub
		String str = "NA#NA";
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List list = cbt.executeAllSelectQuery("select emp.empName, emp.mobOne from employee_basic as emp  inner join long_stay_emp_wing_mapping as empMap on empMap.empName = emp.id INNER JOIN long_stay_shift_with_emp_wing as shift on shift.id= empMap.shiftId  where shift.fromShift<='"+DateUtil.getCurrentTimewithSeconds()+"' and shift.toShift >'"+DateUtil.getCurrentTimewithSeconds()+"'  and empMap.wingsname in ( select nu.id as nuid from machine_order_nursing_details as nu where nu.floorname = (select id from floor_detail where floorname = '"+floor+"')  and nu.nursing_code='"+NU+"' )    group by emp.empName", connection);
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				
				str = object[0].toString() +"#"+object[1].toString();
			}
		}
		else{
			str = "Dr Aditi#9582558964";
		}
		
		return str;
	}

	private String ticketMaker(SessionFactory connection) {
		// TODO Auto-generated method stub
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List list = cbt.executeAllSelectQuery("SELECT ticket_no FROM Dreamsol_IP_VW_ticket ORDER BY id DESC LIMIT 1", connection);
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				return "NEWAD" + (Integer.parseInt(object.toString().substring(5)) + 1);
			}
		}
		return null;
	}

	private boolean chkDupli(String UHID, String DATE,
			SessionFactory connection) {
		// TODO Auto-generated method stub
		boolean check = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("select id from dreamsol_ip_vw_ticket where UHIID='"+UHID+"' and ADMISSION_DATE='"+DATE+"' ", connection);
			System.out.println(" size "+dataListEcho.size());
			if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size()>0)
			{
				System.out.println("CancelOrder>>>>  " + dataListEcho.size());
					check = true;
				dataListEcho.clear();
			}
			else
			{
				check = false;
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return check;
	}

	private String fetchLastdateTime(SessionFactory connection2) {
		// TODO Auto-generated method stub


		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select ADMISSION_DATE, ADMISSION_TIME from dreamsol_ip_vw_ticket order by id desc limit 1 ".toString(), connection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String lastDataTime1 = object[0].toString()+" "+object[1].toString();
				String dateOne = DateUtil.convertDateToIndianFormat(lastDataTime1.split(" ")[0]);
				lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + "00:00";
				System.out.println(lastDataTime);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lastDataTime;

	
	}

	private String fetchFloorName(String nuCode, SessionFactory connection2) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select fd.id, fd.floorname from machine_order_nursing_details as mon inner join floor_detail as fd on mon.floorname= fd.id where nursing_code='"+nuCode+"'".toString(), connection);
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str =  object[1].toString();
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return str;
	
	}
	
}


