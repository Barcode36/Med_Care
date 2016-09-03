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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.Over2Cloud.Rnd.MailReader;
import com.aspose.slides.p883e881b.els;





public class LongSatyPatientHISDataFetch  extends Thread  {

	private static final Log log = LogFactory.getLog(LongSatyPatientHISDataFetch.class);
	

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
	
	public LongSatyPatientHISDataFetch(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
						fetchAllPatientData (log, connection, sessionHis, ch);
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
	
	
	

	private void fetchICuMovement(Log log2, SessionFactory connection2,
			Session sessionHis2, CommunicationHelper ch2) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		System.out.println("day  "+day);
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					
						rs = st.executeQuery(" SELECT * FROM dreamsol_ip_trfr_vw ");
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
						while (rs.next())
						{
							
							System.out.println("UHIID"+ rs.getString("PATIENT_NAME"));
							System.out.println("pName"+ rs.getString("CONTACT2_NO"));
							System.out.println("bedNo"+ rs.getString("ENCOUNTER_ID"));
							
							
							 
							 ob = new InsertDataTable();
								ob.setColName("PATIENT_ID");
								if (rs.getString("PATIENT_ID") != null && !rs.getString("PATIENT_ID").toString().equals(""))
								{
									ob.setDataName(rs.getString("PATIENT_ID"));
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
								ob.setColName("CONTACT2_NO");
								if (rs.getString("CONTACT2_NO") != null && !rs.getString("CONTACT2_NO").toString().equals(""))
								{
									ob.setDataName(rs.getString("CONTACT2_NO"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								
								ob = new InsertDataTable();
								ob.setColName("ENCOUNTER_ID");
								if (rs.getString("ENCOUNTER_ID") != null && !rs.getString("ENCOUNTER_ID").toString().equals(""))
								{
									ob.setDataName(rs.getString("ENCOUNTER_ID"));
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);
								
								
								
								
								System.out.println("attDoc"+ rs.getString("CURRENT_BED"));
								System.out.println("admDoc"+ rs.getString("CURRENT_NURSING_UNIT"));
								System.out.println("admSpec"+ rs.getString("ADMISSION_DATE_TIME"));
								System.out.println("admSpec"+ rs.getString("REQUEST_DATE_TIME"));
								System.out.println("admSpec"+ rs.getString("    "));
								System.out.println("nursing_code "+ rs.getString(""));
								System.out.println("nursingUnit"+ rs.getString(""));
								System.out.println("nursingUnit"+ rs.getString(""));
								 System.out.println("nursingUnit"+ rs.getString(""));
								 
								 
								 ob = new InsertDataTable();
									ob.setColName("CURRENT_BED");
									if (rs.getString("CURRENT_BED") != null && !rs.getString("CURRENT_BED").toString().equals(""))
									{
										ob.setDataName(rs.getString("CURRENT_BED"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									ob = new InsertDataTable();
									ob.setColName("CURRENT_NURSING_UNIT");
									if (rs.getString("CURRENT_NURSING_UNIT") != null && !rs.getString("CURRENT_NURSING_UNIT").toString().equals(""))
									{
										ob.setDataName(rs.getString("CURRENT_NURSING_UNIT"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_DATE_TIME");
									if (rs.getString("ADMISSION_DATE_TIME") != null && !rs.getString("ADMISSION_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE_TIME"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("REQUEST_DATE_TIME");
									if (rs.getString("REQUEST_DATE_TIME") != null && !rs.getString("REQUEST_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUEST_DATE_TIME"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									
									ob = new InsertDataTable();
									ob.setColName("PREF_DATE_TIME");
									if (rs.getString("PREF_DATE_TIME") != null && !rs.getString("PREF_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("PREF_DATE_TIME"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("REQUESTED_NURSING_UNIT");
									if (rs.getString("REQUESTED_NURSING_UNIT") != null && !rs.getString("REQUESTED_NURSING_UNIT").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUESTED_NURSING_UNIT"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("REQUESTED_BED_CLASS");
									if (rs.getString("REQUESTED_BED_CLASS") != null && !rs.getString("REQUESTED_BED_CLASS").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUESTED_BED_CLASS"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("REQ_BED_NO");
									if (rs.getString("REQ_BED_NO") != null && !rs.getString("REQ_BED_NO").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQ_BED_NO"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("TFR_REQ_STATUS");
									if (rs.getString("TFR_REQ_STATUS") != null && !rs.getString("TFR_REQ_STATUS").toString().equals(""))
									{
										ob.setDataName(rs.getString("TFR_REQ_STATUS"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
							}
						
						CommonOperInterface cot = new CommonConFactory().createInterface();
						int id = cot.insertDataReturnId("ER_bed_change", insertData, connection);
					
						
						System.out.println("nurshingMap "+nurshingMap);
						
						System.out.println("i  ==  "+i);
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

	private void fetchAllNurshingPatientData(Log log2,
			SessionFactory connection2, Session sessionHis2,
			CommunicationHelper ch2) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		System.out.println("day  "+day);
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					
						rs = st.executeQuery("SELECT * FROM Dreamsol_IP_VW  where ADMISSION_DATE >= TO_TIMESTAMP('2015-09-13 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF') and ADMISSION_DATE <= to_timestamp('2015-09-14 21:25:33.0', 'YYYY-MM-DD HH24:MI:SS.FF')  ");
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
						while (rs.next())
						{
							
							//System.out.println("UHIID"+ rs.getString("UHIID"));
							//System.out.println("pName"+ rs.getString("PATIENT_NAME"));
							//System.out.println("bedNo"+ rs.getString("ASSIGN_BED_NUM"));
							//
							//System.out.println("attDoc"+ rs.getString("ATTENDING_PRACTITIONER_NAME"));
							//System.out.println("admDoc"+ rs.getString("ADMITTING_PRACTITIONER_NAME"));
							//System.out.println("admSpec"+ rs.getString("SPECIALITY"));
							//System.out.println("admSpec"+ rs.getString("ADMISSION_DATE"));
							//System.out.println("nursing_code "+ rs.getString("Assign_care_locn_code"));
							//.out.println("nursingUnit"+ rs.getString("LONG_DESC"));
							
							if (rs.getString("Assign_care_locn_code") != null && !rs.getString("Assign_care_locn_code").toString().equals("") && rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
							{
							nurshingMap.put(rs.getString("Assign_care_locn_code"), rs.getString("LONG_DESC"));
							//nursing_code = rsPat.getString("Assign_care_locn_code");
							}
							
						}
						
						System.out.println("nurshingMap "+nurshingMap);
						
						System.out.println("i  ==  "+i);
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

	private void fetchAllPatientData(Log log2, SessionFactory connection2,
			Session sessionHis2, CommunicationHelper ch2) {
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
					
					
						rs = st.executeQuery(" SELECT * FROM Dreamsol_IP_VW ");
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
							StringBuilder sbDataUpdateData = new StringBuilder();
							sbDataUpdateData.append("TRUNCATE TABLE  Dreamsol_IP_VW");
							
							int chkDataTabUpdate = cbt.executeAllUpdateQuery(sbDataUpdateData.toString(), connection);
							
							
							System.out.println("chkDataTabUpdate");
						
						while (rs.next())
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
									ob.setColName("floor");
									ob.setDataName(floor);
									insertData.add(ob);
									
									
									CommonOperInterface cot = new CommonConFactory().createInterface();
									int id = cot.insertDataReturnId("Dreamsol_IP_VW", insertData, connection);
									
									insertData.clear();
									
									System.out.println("id ===  "+id);
							}
						
						
					
						
					//	System.out.println("nurshingMap "+nurshingMap);
						
						System.out.println("i  ==  "+i);
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Object> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp) {
		this.masterViewProp = masterViewProp;
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
			
				Thread uclient=new Thread(new LongSatyPatientHISDataFetch(connection, sessionHis));
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
}


