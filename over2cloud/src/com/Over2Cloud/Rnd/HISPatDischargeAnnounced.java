package com.Over2Cloud.Rnd;

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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.PatientMovementTracking.HisPatientMoveData;



public class HISPatDischargeAnnounced  extends Thread  {

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
	
	public HISPatDischargeAnnounced(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
						//fetchAllPatientData (log, connection, sessionHis, ch);
						fetchDischargeData(log, connection, sessionHis, ch);
						//updateFetchICuMovement(log, connection, sessionHis, ch);
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
	
	private void fetchDischargeData(Log log2, SessionFactory connection2, Session sessionHis2, CommunicationHelper ch2)
	{
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String UPDATE_DATE = "NA";
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		//System.out.println("day  "+day);
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				//System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				////System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					//String lastInsertDataTime = fetchLastReqstDateTime(connection);
						rs = st.executeQuery(" SELECT * FROM DREAMSOL_DIS_CLRN ");
						//commonJSONArray = new JSONArray();
						////System.out.println("req bed class: "+rs.getString("REQUESTED_BED_CLASS"));
						////System.out.println("req bed No: "+rs.getString("REQ_BED_NO"));
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
						{/*
							
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
									
									//System.out.println("req bed class: "+rs.getString("REQUESTED_BED_CLASS"));
									//System.out.println("req bed No: "+rs.getString("REQ_BED_NO"));
									
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
									
									
									////System.out.println("chkEntry "+chkEntry);
									
										////System.out.println("rs.getString(TFR_REQ_STATUS) ==  "+rs.getString("TFR_REQ_STATUS"));
										if(rs.getString("TFR_REQ_STATUS").equalsIgnoreCase("0")){
											System.out.println("manab 0");
											boolean chkEntry = chkNewEntry(rs.getString("CURRENT_BED"), rs.getString("REQUEST_DATE_TIME"), rs.getString("PATIENT_ID"), connection);
											if(!chkEntry ){ 
											//System.out.println(" manab0");
										ob = new InsertDataTable();
										ob.setColName("status");
										ob.setDataName("Transfer Request Initiated");
										insertData.add(ob);
										
									CommonOperInterface cot = new CommonConFactory().createInterface();
									int id = cot.insertDataReturnId("dreamsol_ip_trfr_vw", insertData, connection);
									
									if (id > 0)
									{
										//System.out.println("history update 1st ");
										Map<String, Object> wherClause = new HashMap<String, Object>();
										// update in history table status, action_date, action_time, level, comment
										wherClause.put("dream_id", id);
										wherClause.put("uhid", rs.getString("PATIENT_ID"));
										wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
										wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("level", "Level 1");
										wherClause.put("status", "Transfer Request Initiated");
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
											//System.out.println("history update 2nd ");
											boolean updateFeed = cbt.insertIntoTable("dreamsol_ip_trfr_vw_history", insertDataHis, connection);
											//System.out.println("history update 3rd update  "+updateFeed);
											insertDataHis.clear();
										}
									}
									insertData.clear();
									}
									}
									
																		
									
									
									
									
									else{
										//System.out.println("else data update outer");
										//System.out.println(rs.getString("PATIENT_ID"));
										//System.out.println(rs.getString("REQ_BED_NO"));
										//System.out.println(rs.getString("CURRENT_BED"));
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), rs.getString("REQUEST_DATE_TIME"), rs.getString("CURRENT_BED"), connection);
										
										StringBuilder  sb = new StringBuilder();
										sb.append(" update dreamsol_ip_trfr_vw set  ");

										sb.append(" TFR_REQ_STATUS = " + rs.getString("TFR_REQ_STATUS") );
										sb.append(", UPDATE_DATE = " + rs.getString("UPDATE_DATE") );
										sb.append(", CURRENT_BED = " + rs.getString("CURRENT_BED") );
										sb.append(", CURRENT_NURSING_UNIT = " + rs.getString("CURRENT_NURSING_UNIT") );
										sb.append(", REQUESTED_NURSING_UNIT = " + rs.getString("REQUESTED_NURSING_UNIT") );
										sb.append(", REQUESTED_BED_CLASS = " + rs.getString("REQUESTED_BED_CLASS") );
										sb.append(", REQ_BED_NO = " + rs.getString("REQ_BED_NO") );
										
										
										
										sb.append(" where id=" + data_id + "");
////System.out.println(sb.toString());
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
											
											wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
											wherClause.put("action_time", DateUtil.getCurrentTime());
											wherClause.put("level", "Level 1");
											wherClause.put("status", changeStatusName(rs.getString("TFR_REQ_STATUS").toString()));
											wherClause.put("comment",  "NA");
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
									
									
									insertData.clear();
							*/}
						
						
					
						
						////System.out.println("nurshingMap "+nurshingMap);
						
						////System.out.println("i  ==  "+i);
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

	public static void main(String[] args) {
		Thread.State state ;
		try{
			
			//System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			//System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			//System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			//System.out.println("After HIS DB connection"+sessionHis);
			
				Thread uclient=new Thread(new HISPatDischargeAnnounced(connection, sessionHis));
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
	
