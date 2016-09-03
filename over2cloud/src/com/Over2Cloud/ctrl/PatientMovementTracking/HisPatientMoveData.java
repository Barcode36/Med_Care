package com.Over2Cloud.ctrl.PatientMovementTracking;
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
import com.Over2Cloud.Rnd.createTable;
import com.aspose.slides.p883e881b.els;





public class HisPatientMoveData  extends Thread  {

	private static final Log log = LogFactory.getLog(HisPatientMoveData.class);
	

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
	
	public HisPatientMoveData(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
						//fetchAllPatientData (log, connection, sessionHis, ch);
						fetchICuMovement(log, connection, sessionHis, ch);
						updateFetchICuMovement(log, connection, sessionHis, ch);
						//fetchAllNurshingPatientData (log, connection, sessionHis, ch);
					
						
						//System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 8 );
						//System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

						Runtime rt = Runtime.getRuntime();
						rt.gc();
	
					}
									}
				 catch (Exception e)
				 {
					 e.printStackTrace();
					 }
		
	}
	
	
	

	private void fetchICuMovement(Log log2, SessionFactory connection,
			Session sessionHis2, CommunicationHelper ch2) {

		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String UPDATE_DATE = "NA";
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					String lastInsertDataTime = fetchLastReqstDateTime(connection);
						rs = st.executeQuery(" SELECT * FROM dreamsol_ip_trfr_vw where  REQUEST_DATE_TIME >= TO_TIMESTAMP('" + lastInsertDataTime + "','YYYY-MM-DD HH24:MI:SS.FF') and TFR_REQ_STATUS = '0' ");
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
						}
						int i = 0;
						while (rs.next())
						{
							
							//name one 1 : PATIENT_ID
							// name one 2 : PATIENT_NAME
							// name one 3 : CONTACT2_NO
							// name one 4 : ENCOUNTER_ID
							// name one 5 : CURRENT_BED
							// name one 6 : CURRENT_NURSING_UNIT
							// name one 7 : ADMISSION_DATE_TIME
							// name one 8 : REQUEST_DATE_TIME
							// name one 9 : UPDATE_DATE
						//	 name one 10 : PREF_DATE_TIME
						//	 name one 11 : REQUESTED_NURSING_UNIT
						//	 name one 12 : REQUESTED_BED_CLASS
						//	 name one 13 : REQ_BED_NO
						//	 name one 14 : TFR_REQ_STATUS
							
							
							 
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
									ob.setColName("REQUEST_DATE");
									if (rs.getString("REQUEST_DATE_TIME") != null && !rs.getString("REQUEST_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUEST_DATE_TIME").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("REQUEST_TIME");
									if (rs.getString("REQUEST_DATE_TIME") != null && !rs.getString("REQUEST_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUEST_DATE_TIME").split(" ")[1]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_DATE");
									if (rs.getString("ADMISSION_DATE_TIME") != null && !rs.getString("ADMISSION_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE_TIME").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_TIME");
									if (rs.getString("ADMISSION_DATE_TIME") != null && !rs.getString("ADMISSION_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE_TIME").split(" ")[1]);
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
									
									ob = new InsertDataTable();
									ob.setColName("UPDATE_DATE");
									if (rs.getString("UPDATE_DATE") != null && !rs.getString("UPDATE_DATE").toString().equals(""))
									{
										ob.setDataName(rs.getString("UPDATE_DATE"));
										UPDATE_DATE = rs.getString("UPDATE_DATE").toString();
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("status");
									if (rs.getString("TFR_REQ_STATUS") != null && !rs.getString("TFR_REQ_STATUS").toString().equals(""))
									{
										ob.setDataName(rs.getString("TFR_REQ_STATUS"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:45", "Y");
									
									ob = new InsertDataTable();
									ob.setColName("escalation_date");
									ob.setDataName(esctm.split("#")[0]);
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("escalation_time");
									ob.setDataName(esctm.split("#")[1]);
									insertData.add(ob);
									
										if(rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("0")){
											boolean chkEntry = chkNewEntry(rs.getString("CURRENT_BED"), rs.getString("REQUEST_DATE_TIME"), rs.getString("PATIENT_ID"), connection);
											if(!chkEntry ){ 
									CommonOperInterface cot = new CommonConFactory().createInterface();
									int id = cot.insertDataReturnId("dreamsol_ip_trfr_vw", insertData, connection);
									
									if (id > 0)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										wherClause.put("dream_id", id);
										wherClause.put("uhid", rs.getString("PATIENT_ID"));
										wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
										wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("level", "Level 1");
										wherClause.put("status", "Transfer Request Initiated");
										wherClause.put("comment",  "NA");
										wherClause.put("current_bed",  rs.getString("CURRENT_BED"));
										if (wherClause != null && wherClause.size() > 0)
										{
											CommonOperInterface cbt = new CommonConFactory().createInterface();
											InsertDataTable ob1 = new InsertDataTable();
											List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob1 = new InsertDataTable();
												ob1.setColName(entry.getKey());
												ob1.setDataName(entry.getValue().toString());
												insertDataHis.add(ob1);
											}
											boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
											insertDataHis.clear();
										}
									}
									insertData.clear();
									}
									}
									
									insertData.clear();
							}
						
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
		
	}
	
	
	private String fetchLastReqstDateTime(SessionFactory connection)
	{

		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT `REQUEST_DATE_TIME`, id FROM `dreamsol_ip_trfr_vw` ORDER BY REQUEST_DATE_TIME DESC LIMIT 1".toString(), connection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				lastDataTime = object[0].toString() ;
				 System.out.println("Last Order Normal Time  >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lastDataTime;
	
	}
	
	private String fetchLastUpdateDateTime(SessionFactory connection2)
	{

		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT UPDATE_DATE, id FROM `dreamsol_ip_trfr_vw` ORDER BY UPDATE_DATE DESC LIMIT 1".toString(), connection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				lastDataTime = object[0].toString() ;
				 System.out.println("Last update Time  >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	
	}

	private void updateFetchICuMovement(Log log2, SessionFactory connection,
			Session sessionHis2, CommunicationHelper ch2){

	
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String UPDATE_DATE = "NA";
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					
					String lastUpdateDataTime = fetchLastUpdateDateTime(connection);
					rs = st.executeQuery(" SELECT * FROM dreamsol_ip_trfr_vw where UPDATE_DATE >= TO_TIMESTAMP('" + lastUpdateDataTime + "','YYYY-MM-DD HH24:MI:SS.FF') and TFR_REQ_STATUS != '0' ORDER By UPDATE_DATE ");
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
						}
						int i = 0;
						while (rs.next())
						{
							
							////System.out.println("UHIID"+ rs.getString("PATIENT_NAME"));
							////System.out.println("pName"+ rs.getString("CONTACT2_NO"));
							////System.out.println("bedNo"+ rs.getString("ENCOUNTER_ID"));
							
							//name one 1 : PATIENT_ID
							// name one 2 : PATIENT_NAME
							// name one 3 : CONTACT2_NO
							// name one 4 : ENCOUNTER_ID
							// name one 5 : CURRENT_BED
							// name one 6 : CURRENT_NURSING_UNIT
							// name one 7 : ADMISSION_DATE_TIME
							// name one 8 : REQUEST_DATE_TIME
							// name one 9 : UPDATE_DATE
						//	 name one 10 : PREF_DATE_TIME
						//	 name one 11 : REQUESTED_NURSING_UNIT
						//	 name one 12 : REQUESTED_BED_CLASS
						//	 name one 13 : REQ_BED_NO
						//	 name one 14 : TFR_REQ_STATUS
							
							
							 
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
									ob.setColName("REQUEST_DATE");
									if (rs.getString("REQUEST_DATE_TIME") != null && !rs.getString("REQUEST_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUEST_DATE_TIME").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("REQUEST_TIME");
									if (rs.getString("REQUEST_DATE_TIME") != null && !rs.getString("REQUEST_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("REQUEST_DATE_TIME").split(" ")[1]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_DATE");
									if (rs.getString("ADMISSION_DATE_TIME") != null && !rs.getString("ADMISSION_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE_TIME").split(" ")[0]);
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("ADMISSION_TIME");
									if (rs.getString("ADMISSION_DATE_TIME") != null && !rs.getString("ADMISSION_DATE_TIME").toString().equals(""))
									{
										ob.setDataName(rs.getString("ADMISSION_DATE_TIME").split(" ")[1]);
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
									
									ob = new InsertDataTable();
									ob.setColName("UPDATE_DATE");
									if (rs.getString("UPDATE_DATE") != null && !rs.getString("UPDATE_DATE").toString().equals(""))
									{
										ob.setDataName(rs.getString("UPDATE_DATE"));
										UPDATE_DATE = rs.getString("UPDATE_DATE").toString();
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									ob = new InsertDataTable();
									ob.setColName("status");
									if (rs.getString("TFR_REQ_STATUS") != null && !rs.getString("TFR_REQ_STATUS").toString().equals(""))
									{
										ob.setDataName(rs.getString("TFR_REQ_STATUS"));
									} else
									{
										ob.setDataName("NA");
									}
									insertData.add(ob);
									
									
									String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:59", "Y");
									
									
									
																		
									 if (rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("1")){
										boolean chkData = chkdata(rs.getString("TFR_REQ_STATUS"), rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), connection);
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), rs.getString("CURRENT_BED"), connection);
										if (!chkData && !data_id.equalsIgnoreCase("NA")){
											String req_bed = "NA";
											if(rs.getString("REQ_BED_NO") != null && !rs.getString("REQ_BED_NO").toString().equals(""))
											{
												req_bed = rs.getString("REQ_BED_NO");
											}
											String req_bed_class = "NA";
											if(rs.getString("REQUESTED_BED_CLASS") != null && !rs.getString("REQUESTED_BED_CLASS").toString().equals(""))
											{
												req_bed_class = rs.getString("REQUESTED_BED_CLASS");
											}
											
											StringBuilder  sb = new StringBuilder();
											sb.append(" update dreamsol_ip_trfr_vw set  ");

											sb.append(" TFR_REQ_STATUS = '" + rs.getString("TFR_REQ_STATUS") );
											sb.append("', UPDATE_DATE = '" + UPDATE_DATE );
											sb.append("', CURRENT_BED = '" + rs.getString("CURRENT_BED") );
											sb.append("', CURRENT_NURSING_UNIT = '" + rs.getString("CURRENT_NURSING_UNIT") );
											sb.append("', REQUESTED_NURSING_UNIT = '" + rs.getString("REQUESTED_NURSING_UNIT") );
											sb.append("', REQUESTED_BED_CLASS = '" + rs.getString("REQUESTED_BED_CLASS") );
											sb.append("', REQ_BED_NO = '" + req_bed );
											sb.append("', escalation_date = '" + esctm.split("#")[0] );
											sb.append("', escalation_time = '" + esctm.split("#")[1] );
											sb.append("', Level = 'Level 1' ");
											
											
											
											sb.append(" where id=" + data_id + "");
											int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
											sb.setLength(0);
											if (chkUpdate > 0)
											{

												Map<String, Object> wherClause = new HashMap<String, Object>();
												wherClause.put("dream_id", data_id);
												wherClause.put("uhid", rs.getString("PATIENT_ID"));
												wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												
												wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("level", "Level 1");
												wherClause.put("status", changeStatusName(rs.getString("TFR_REQ_STATUS").toString()));
												wherClause.put("comment",  "NA");
												wherClause.put("current_bed",  rs.getString("CURRENT_BED"));
												if (wherClause != null && wherClause.size() > 0)
												{
													CommonOperInterface cbt = new CommonConFactory().createInterface();
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClause.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
													insertDataHis.clear();
												}
											
											}
											
										
										}
									}
									
									else if (rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("3") ){
										boolean chkData = chkdata(rs.getString("TFR_REQ_STATUS"), rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), connection);
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), rs.getString("CURRENT_BED"), connection);
										if (!chkData && !data_id.equalsIgnoreCase("NA")){
											
											String req_bed = "NA";
											if(rs.getString("REQ_BED_NO") != null && !rs.getString("REQ_BED_NO").toString().equals(""))
											{
												req_bed = rs.getString("REQ_BED_NO");
											}
											String req_bed_class = "NA";
											if(rs.getString("REQUESTED_BED_CLASS") != null && !rs.getString("REQUESTED_BED_CLASS").toString().equals(""))
											{
												req_bed_class = rs.getString("REQUESTED_BED_CLASS");
											}
											
											StringBuilder  sb = new StringBuilder();
											sb.append(" update dreamsol_ip_trfr_vw set  ");

											sb.append(" TFR_REQ_STATUS = '" + rs.getString("TFR_REQ_STATUS") );
											sb.append("', UPDATE_DATE = '" + UPDATE_DATE );
											sb.append("', CURRENT_BED = '" + rs.getString("CURRENT_BED") );
											sb.append("', CURRENT_NURSING_UNIT = '" + rs.getString("CURRENT_NURSING_UNIT") );
											sb.append("', REQUESTED_NURSING_UNIT = '" + rs.getString("REQUESTED_NURSING_UNIT") );
											sb.append("', REQUESTED_BED_CLASS = '" + rs.getString("REQUESTED_BED_CLASS") );
											sb.append("', REQ_BED_NO = '" + req_bed );
											sb.append("', escalation_date = '" + esctm.split("#")[0] );
											sb.append("', escalation_time = '" + esctm.split("#")[1] );
											sb.append("', Level = 'Level 1' ");
											
											
											
											sb.append(" where id=" + data_id + "");
											int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
											sb.setLength(0);
											if (chkUpdate > 0)
											{

												Map<String, Object> wherClause = new HashMap<String, Object>();
												// update in history table status, action_date, action_time, level, comment
												wherClause.put("dream_id", data_id);
												wherClause.put("uhid", rs.getString("PATIENT_ID"));
												wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												
												wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("level", "Level 1");
												wherClause.put("status", changeStatusName(rs.getString("TFR_REQ_STATUS").toString()));
												wherClause.put("comment",  "NA");
												wherClause.put("current_bed",  rs.getString("CURRENT_BED"));
												//wherClause.put("dept", department);
												if (wherClause != null && wherClause.size() > 0)
												{
													CommonOperInterface cbt = new CommonConFactory().createInterface();
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClause.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
													insertDataHis.clear();
												}
											
											}
											
										
										}
									
										
									}
									else if (rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("4")){
										boolean chkData = chkdata(rs.getString("TFR_REQ_STATUS"), rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), connection);
										System.out.println("chkData  "+chkData);
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), rs.getString("REQ_BED_NO"), connection);
										System.out.println("sssss data_id==  "+ data_id);
										if (!chkData && !data_id.equalsIgnoreCase("NA")){
											String req_bed = "NA";
											if(rs.getString("REQ_BED_NO") != null && !rs.getString("REQ_BED_NO").toString().equals(""))
											{
												req_bed = rs.getString("REQ_BED_NO");
											}
											String req_bed_class = "NA";
											if(rs.getString("REQUESTED_BED_CLASS") != null && !rs.getString("REQUESTED_BED_CLASS").toString().equals(""))
											{
												req_bed_class = rs.getString("REQUESTED_BED_CLASS");
											}
											
											StringBuilder  sb = new StringBuilder();
											sb.append(" update dreamsol_ip_trfr_vw set  ");

											sb.append(" TFR_REQ_STATUS = '" + rs.getString("TFR_REQ_STATUS") );
											sb.append("', UPDATE_DATE = '" + UPDATE_DATE );
											sb.append("', REQUESTED_NURSING_UNIT = '" + rs.getString("REQUESTED_NURSING_UNIT") );
											sb.append("', REQUESTED_BED_CLASS = '" + rs.getString("REQUESTED_BED_CLASS") );
											sb.append("', REQ_BED_NO = '" + req_bed );
											
											
											
											sb.append("' where id=" + data_id + "");
											int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
											sb.setLength(0);
											if (chkUpdate > 0)
											{

												Map<String, Object> wherClause = new HashMap<String, Object>();
												wherClause.put("dream_id", data_id);
												wherClause.put("uhid", rs.getString("PATIENT_ID"));
												wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												
												wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("level", "Level 1");
												wherClause.put("status", changeStatusName(rs.getString("TFR_REQ_STATUS").toString()));
												wherClause.put("comment",  "NA");
												wherClause.put("current_bed",  rs.getString("CURRENT_BED"));
												//wherClause.put("dept", department);
												if (wherClause != null && wherClause.size() > 0)
												{
													CommonOperInterface cbt = new CommonConFactory().createInterface();
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClause.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
													insertDataHis.clear();
												}
											
											}
											
										
										}
									}
									else if (rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("9")){
										

										boolean chkData = chkdata(rs.getString("TFR_REQ_STATUS"), rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), connection);
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), rs.getString("CURRENT_BED"), connection);
										if (!chkData && !data_id.equalsIgnoreCase("NA")){
											

											
											String req_bed = "NA";
											if(rs.getString("REQ_BED_NO") != null && !rs.getString("REQ_BED_NO").toString().equals(""))
											{
												req_bed = rs.getString("REQ_BED_NO");
											}
											String req_bed_class = "NA";
											if(rs.getString("REQUESTED_BED_CLASS") != null && !rs.getString("REQUESTED_BED_CLASS").toString().equals(""))
											{
												req_bed_class = rs.getString("REQUESTED_BED_CLASS");
											}
											
											StringBuilder  sb = new StringBuilder();
											sb.append(" update dreamsol_ip_trfr_vw set  ");

											sb.append(" TFR_REQ_STATUS = '" + rs.getString("TFR_REQ_STATUS") );
											sb.append("', UPDATE_DATE = '" + UPDATE_DATE );
											sb.append("', CURRENT_BED = '" + rs.getString("CURRENT_BED") );
											sb.append("', CURRENT_NURSING_UNIT = '" + rs.getString("CURRENT_NURSING_UNIT") );
											sb.append("', REQUESTED_NURSING_UNIT = '" + rs.getString("REQUESTED_NURSING_UNIT") );
											sb.append("', REQUESTED_BED_CLASS = '" + rs.getString("REQUESTED_BED_CLASS") );
											sb.append("', REQ_BED_NO = '" + req_bed );
											
											
											
											sb.append("' where id=" + data_id + "");
											int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
											sb.setLength(0);
											if (chkUpdate > 0)
											{
												//System.out.println("yes update ");

												Map<String, Object> wherClause = new HashMap<String, Object>();
												// update in history table status, action_date, action_time, level, comment
												wherClause.put("dream_id", data_id);
												wherClause.put("uhid", rs.getString("PATIENT_ID"));
												wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												
												wherClause.put("action_date", DateUtil.convertDateToIndianFormat(rs.getString("CANCELLATION_DATE_TIME").split(" ")[0]) );
												wherClause.put("action_time", rs.getString("CANCELLATION_DATE_TIME").split(" ")[1]);
												wherClause.put("level", "Level 1");
												wherClause.put("status", changeStatusName(rs.getString("TFR_REQ_STATUS").toString()));
												wherClause.put("comment",  rs.getString("CANCEL_REASON"));
												wherClause.put("cancel_by",  rs.getString("CANCELLED_BY_ID"));
												wherClause.put("current_bed",  rs.getString("CURRENT_BED"));
												//wherClause.put("dept", department);
												if (wherClause != null && wherClause.size() > 0)
												{
													CommonOperInterface cbt = new CommonConFactory().createInterface();
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClause.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
													insertDataHis.clear();
												}
											
											}
											
										
										}
									
									}
									insertData.clear();
							}
			
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



	

	private boolean chkdata(String status, String patId, String reqDateTime, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		boolean sts = false ; 
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//System.out.println("aarif query>>>>>>>>>>>>>>>>.   select id from dreamsol_ip_trfr_vw where TFR_REQ_STATUS = '"+status+"' and  REQUEST_DATE_TIME = '"+reqDateTime+"' and  PATIENT_ID = '"+patId+"'");
			List dataListEcho = cbt.executeAllSelectQuery("select id from dreamsol_ip_trfr_vw where TFR_REQ_STATUS = '"+status+"' and  REQUEST_DATE_TIME = '"+reqDateTime+"' and  PATIENT_ID = '"+patId+"'", connection);
			////System.out.println(" size "+dataListEcho.size());
			/*if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size()>0)
			{
				//System.out.println("CancelOrder>>>>  " + dataListEcho.size());
				data_id = ;
			}*/
			if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size() > 0)
			{
				sts = true;
			}
			

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return sts;
	}

	private String changeStatusName(String status)
	{
		// TODO Auto-generated method stub
		String sts = "NA";
		if (status.trim().equalsIgnoreCase("1")){
			sts = "Transfer Request Accepted";
		}
		else if (status.trim().equalsIgnoreCase("3")){
			sts = "Transfer Out from Current Nursing Unit";
		}
		else if (status.trim().equalsIgnoreCase("4")){
			sts = "Transfer Completed";
		}
		else if (status.trim().equalsIgnoreCase("9")){
			sts = "Transfer Cancel";
		}
		return sts;

	}

	private String chkNewDataId(String UHID, String REQUEST_DATE_TIME, String CURRENT_BED, SessionFactory connection2)
	{
		// TODO Auto-generated method stub
		String data_id = "NA";

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			System.out.println("query for getting id >>>>>>>>>>>>>>    select id from dreamsol_ip_trfr_vw where CURRENT_BED = '"+CURRENT_BED+"' and  REQUEST_DATE_TIME = '"+REQUEST_DATE_TIME+"' and  PATIENT_ID = '"+UHID+"'");
			List dataListEcho = cbt.executeAllSelectQuery("select id, CURRENT_BED  from dreamsol_ip_trfr_vw where   REQUEST_DATE_TIME = '"+REQUEST_DATE_TIME+"' and  PATIENT_ID = '"+UHID+"'", connection);
			System.out.println(" size "+dataListEcho.size());
			/*if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size()>0)
			{
				//System.out.println("CancelOrder>>>>  " + dataListEcho.size());
				data_id = ;
			}*/
			/*System.out.println(dataListEcho.get(0).toString()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size() > 0)
			{
				data_id = dataListEcho.get(0).toString();
			}*/
			for (Iterator it = dataListEcho.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				data_id =  object[0].toString() ;
				 System.out.println("Last update Time  >>>>>>  " + data_id);
			}
			

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return data_id;
	
	
	}

	private boolean chkNewEntry(String CURRENT_BED, String REQUEST_DATE_TIME, String PATIENT_ID, SessionFactory connection2)
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		boolean check = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("select id, PATIENT_ID from dreamsol_ip_trfr_vw where REQUEST_DATE_TIME = '"+REQUEST_DATE_TIME+"' and  PATIENT_ID = '"+PATIENT_ID+"' and  CURRENT_BED = '"+CURRENT_BED+"'", connection);
			//System.out.println(" size "+dataListEcho.size());
			if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size()>0)
			{
				////System.out.println("CancelOrder>>>>  " + dataListEcho.size());
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

	public static void main(String[] args) {
		Thread.State state ;
		try{
			
			//System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			//System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			//System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			//System.out.println("After HIS DB connection"+sessionHis);
			
				Thread uclient=new Thread(new HisPatientMoveData(connection, sessionHis));
				//System.out.println("Thread Created Successfuly!!");
				state = uclient.getState(); 
				//System.out.println("Thread's State:: "+state);
			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
				//System.out.println("Thread Started Successfuly!!");
			
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



