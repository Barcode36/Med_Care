package com.Over2Cloud.ctrl.dischargeAnnounce.hisIntegration;

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




public class HISDIschargeAnnounceIntegration  extends Thread  {

	private static final Log log = LogFactory.getLog(HISDIschargeAnnounceIntegration.class);
	

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	//private HISTESTHelper CDIH =  new HISTESTHelper();
	//private HISHelperRedirectServer CDIHRe = new HISHelperRedirectServer();
	//private MailReader mailread = new MailReader();
	CommunicationHelper ch = new CommunicationHelper();
//	public Map<String, String> nurshingMap ;
//	private List<Object> masterViewProp = new ArrayList<Object>();
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	private int total;
	
	public HISDIschargeAnnounceIntegration(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
					
						fetchDischargeAnnounceDataFirst(log, connection, sessionHis, ch);
						updateDischargeAnnounce(log, connection, sessionHis, ch);
						
						//System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 30 );
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
	


	private void fetchDischargeAnnounceDataFirst(Log log2, SessionFactory connection,
			Session sessionHis2, CommunicationHelper ch2) 
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String UPDATE_DATE = "NA";
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		//System.out.println("day  "+day);
		try
		{
			
			
		
			//nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
				//System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				////System.out.println(dataFor+"::::"+patType);
	Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			
			//	String lastInsertDataTime = fetchLastReqstDateTime(connection);
				rs = st.executeQuery(" SELECT * FROM DREAMSOL_DIS_CLRN where DISCAHRGE_ADVICE_ADDED_DATE IS NOT NULL  AND  AUTHORIZED_DATE_TIME IS NULL  AND DISCHARGE_BILL_DATE_TIME IS NULL ");
				
		/*	Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/4_clouddb", "root", "root");
			st = con.createStatement();
			
				//String lastInsertDataTime = fetchLastReqstDateTime(connection);
				rs = st.executeQuery(" SELECT * FROM HIS_dreamsol_discharge_announce_vw where  DIS_SUMM_EVENT_DATE_TIME IS NULL  AND DIS_SUMM_EVENT_MODI_DATE_TIME IS NULL  AND DISCHARGE_BILL_DATE_TIME IS NULL ");*/
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						/*for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
							////System.out.println(" name one " + i + " : " + name);
						}*/
						//JSONObject listObject = new JSONObject() UHIID;
						int i = 0;
						while (rs.next())
						{
							/**  FIELDS NAMES ARE
							 * 
							    name one 1 : ADMISSION_DATE_TIME
								 name one 2 : PATIENT_ID
								 name one 3 : ENCOUNTER_ID
								 name one 4 : PATIENT_NAME
								 name one 5 : SEX
								 name one 6 : SPECIALTY_DESC
								 name one 7 : PRACTITIONER_NAME
								 name one 8 : NURSING_UNIT
								 name one 9 : BED_NUM
								 name one 10 : EXPECTED_DISCHARGE_DATE
								 name one 11 : EVENT_STATUS
								 name one 12 : DIS_SUMM_EVENT_DATE_TIME
								 name one 13 : DIS_SUMM_EVENT_MODI_DATE_TIME
								 name one 14 : PERFORMED_BY_ID
								 name one 15 : AUTHORIZED_DATE_TIME
								 name one 16 : AUTHORIZED_BY_ID
								 name one 17 : DISCHARGE_BILL_DATE_TIME
								 name one 18 : DISCHARGE_BILL_GEN_IND
								 name one 19 : BLNG_GRP_CUST_CODE
								 name one 20 : FIC_DETAILS
								 name one 21 : DIS_BILL_DOC_DETAILS
								 name one 22 : DISCAHRGE_ADVICE_ADDED_DATE
								 
							 */
							
						ob = new InsertDataTable();
						ob.setColName("PATIENT_ID");
						ob.setDataName(nullCheck(rs.getString("PATIENT_ID")));
						insertData.add(ob);
							 
						ob = new InsertDataTable();
						ob.setColName("ENCOUNTER_ID");
						ob.setDataName(nullCheck(rs.getString("ENCOUNTER_ID")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("PATIENT_NAME");
						ob.setDataName(nullCheck(rs.getString("PATIENT_NAME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("SEX");
						ob.setDataName(nullCheck(rs.getString("SEX")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("SPECIALTY_DESC");
						ob.setDataName(nullCheck(rs.getString("SPECIALTY_DESC")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("PRACTITIONER_NAME");
						ob.setDataName(nullCheck(rs.getString("PRACTITIONER_NAME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("NURSING_UNIT");
						ob.setDataName(nullCheck(rs.getString("NURSING_UNIT")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BED_NUM");
						ob.setDataName(nullCheck(rs.getString("BED_NUM")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("EXPECTED_DISCHARGE_DATE");
						ob.setDataName(nullCheck(rs.getString("EXPECTED_DISCHARGE_DATE")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("EVENT_STATUS");
						ob.setDataName(nullCheck(rs.getString("EVENT_STATUS")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("DIS_SUMM_EVENT_DATE_TIME");
						ob.setDataName(nullCheck(rs.getString("DIS_SUMM_EVENT_DATE_TIME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("DIS_SUMM_EVENT_MODI_DATE_TIME");
						ob.setDataName(nullCheck(rs.getString("DIS_SUMM_EVENT_MODI_DATE_TIME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("PERFORMED_BY_ID");
						ob.setDataName(nullCheck(rs.getString("PERFORMED_BY_ID")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("AUTHORIZED_DATE_TIME");
						ob.setDataName(nullCheck(rs.getString("AUTHORIZED_DATE_TIME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("AUTHORIZED_BY_ID");
						ob.setDataName(nullCheck(rs.getString("AUTHORIZED_BY_ID")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("DISCHARGE_BILL_DATE_TIME");
						ob.setDataName(nullCheck(rs.getString("DISCHARGE_BILL_DATE_TIME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("DISCHARGE_BILL_GEN_IND");
						ob.setDataName(nullCheck(rs.getString("DISCHARGE_BILL_GEN_IND")));
						insertData.add(ob);

					 	ob = new InsertDataTable();
						ob.setColName("BLNG_GRP_CUST_CODE");
						ob.setDataName(nullCheck(rs.getString("BLNG_GRP_CUST_CODE")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("FIC_DETAILS");
						ob.setDataName(nullCheck(rs.getString("FIC_DETAILS")));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("DIS_BILL_DOC_DETAILS");
						ob.setDataName(nullCheck(rs.getString("DIS_BILL_DOC_DETAILS")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("ADMISSION_DATE_TIME");
						ob.setDataName(nullCheck(rs.getString("ADMISSION_DATE_TIME")));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("MT_TICKET_NO");
						ob.setDataName(ticketMaker("MT_TICKET_NO",connection));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BL_TICKET_NO");
						ob.setDataName(ticketMaker("BL_TICKET_NO",connection));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BL_LEVEL");
						ob.setDataName("Level 1");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("MT_LEVEL");
						ob.setDataName("Level 1");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("ALLOT_TO");
						ob.setDataName("Chandrabhan");
						insertData.add(ob);
					
						
						/*List dataListLab1stEsc = cbt.executeAllSelectQuery(" select tat1 from lab_report_escalation_detail where order_status='"+LONG_DESC+"' ".toString(), connection);
						// dataList = cbt.executeAllSelectQuery(query.toString(),
						// connectionSpace);
						String esctm = null;
						if (dataListLab1stEsc != null && dataListLab1stEsc.size() > 0)
						{
							Object object = (Object) dataListLab1stEsc.get(0);
							esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");

						}*/
/*
						else
						{*/
							String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:59", "Y");

						//}
						
						ob = new InsertDataTable();
						ob.setColName("MT_FM_ESCALATION_DATE");
						ob.setDataName(esctm.split("#")[0]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("MT_FM_ESCALATION_TIME");
						ob.setDataName(esctm.split("#")[1]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BL_ESCALATION_DATE");
						ob.setDataName(esctm.split("#")[0]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BL_ESCALATION_TIME");
						ob.setDataName(esctm.split("#")[1]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("MT_STATUS");
						ob.setDataName("MT-Pending");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("BL_STATUS");
						ob.setDataName("BL-Pending");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("INSERT_DATE");
						ob.setDataName(rs.getString("DISCAHRGE_ADVICE_ADDED_DATE").split(" ")[0]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("INSERT_TIME");
						ob.setDataName(rs.getString("DISCAHRGE_ADVICE_ADDED_DATE").split(" ")[1].substring(0, 5));
						insertData.add(ob);
						
						
						ob = new InsertDataTable();
						ob.setColName("DISCAHRGE_ADVICE_ADDED_DATE");
						ob.setDataName(nullCheck(rs.getString("DISCAHRGE_ADVICE_ADDED_DATE")));
						insertData.add(ob);
						
									////System.out.println("chkEntry "+chkEntry);  
									
										////System.out.println("rs.getString(TFR_REQ_STATUS) ==  "+rs.getString("TFR_REQ_STATUS"));
										
											boolean chkEntry = chkNewEntry(rs.getString("PATIENT_ID"), connection);
											if(!chkEntry )
											{ 
												//System.out.println("This Data Not Exist>>  "+rs.getString("PATIENT_ID").toString());
											CommonOperInterface cot = new CommonConFactory().createInterface();
											int id = cot.insertDataReturnId("dreamsol_discharge_announce_vw", insertData, connection);
											
											if (id > 0)
											{
												/**Use For MT TICKET HISTORY*/
												
												//System.out.println("history update 1st ");
												Map<String, Object> wherClause = new HashMap<String, Object>();
												// update in history table status, action_date, action_time, level, comment
												wherClause.put("dream_id", id);
												wherClause.put("uhid", rs.getString("PATIENT_ID"));
												wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("level", "Level 1");
												wherClause.put("status", "MT Pending");
												wherClause.put("comment",  "MT Allot To(9935370850)");
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
													boolean updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
													//System.out.println("history update 3rd update  "+updateFeed);
													insertDataHis.clear();
												}
												
												/**Use For BILLING TICKET HISTORY*/
												
												Map<String, Object> wherClauseBilling = new HashMap<String, Object>();
												wherClauseBilling.put("dream_id", id);
												wherClauseBilling.put("uhid", rs.getString("PATIENT_ID"));
												wherClauseBilling.put("encounter_id", rs.getString("ENCOUNTER_ID"));
												wherClauseBilling.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClauseBilling.put("action_time", DateUtil.getCurrentTime());
												wherClauseBilling.put("level", "Level 1");
												wherClauseBilling.put("status", "BL Pending");
												wherClause.put("comment",  "BL Allot To(9935370850)");
												if (wherClauseBilling != null && wherClauseBilling.size() > 0)
												{
													CommonOperInterface cbt = new CommonConFactory().createInterface();
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClauseBilling.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
													insertDataHis.clear();
												}
											}
											
											}
											else
											{
												System.out.println("This Data Already Exist  >>  "+rs.getString("PATIENT_ID").toString());
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
	
	
	private void updateDischargeAnnounce(Log log2, SessionFactory connection,
			Session sessionHis2, CommunicationHelper ch2)
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String UPDATE_DATE = "NA";
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		//System.out.println("day  "+day);
		try
		{
		//	nurshingMap = new HashMap<String,String>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			
			//String lastInsertDataTime = fetchLastReqstDateTime(connection);
			
			rs = st.executeQuery(" SELECT * FROM DREAMSOL_DIS_CLRN where  DISCAHRGE_ADVICE_ADDED_DATE IS NOT NULL AND  AUTHORIZED_DATE_TIME IS NOT NULL    or   DISCHARGE_BILL_DATE_TIME IS NOT NULL ");
		/*	Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/4_clouddb", "root", "root");
			st = con.createStatement();
			//String lastInsertDataTime = fetchLastReqstDateTime(connection);
			rs = st.executeQuery(" SELECT * FROM HIS_dreamsol_discharge_announce_vw where  DIS_SUMM_EVENT_DATE_TIME IS NOT NULL   or   DIS_SUMM_EVENT_MODI_DATE_TIME IS NOT NULL   or   DISCHARGE_BILL_DATE_TIME IS NOT NULL ");*/
					//commonJSONArray = new JSONArray();
						////System.out.println("req bed class: "+rs.getString("REQUESTED_BED_CLASS"));
						////System.out.println("req bed No: "+rs.getString("REQ_BED_NO"));
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						/*for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
							////System.out.println(" name one " + i + " : " + name);
						}*/
						//JSONObject listObject = new JSONObject() UHIID;
						int i = 0;
						while (rs.next())
						{
						
							CommonOperInterface cbt = new CommonConFactory().createInterface();		
							/* String qry="select DIS_SUMM_EVENT_MODI_DATE_TIME,AUTHORIZED_DATE_TIME,DISCHARGE_BILL_DATE_TIME from dreamsol_discharge_announce_vw where PATIENT_ID='"+rs.getString("PATIENT_ID").toString()+"' ";
							 List dataListEcho = cbt.executeAllSelectQuery(qry, connection);
							 String DIS_SUMM_EVENT_MODI_DATE_TIME="NA",AUTHORIZED_DATE_TIME="NA",DISCHARGE_BILL_DATE_TIME="NA";
							 for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
							 {
								Object object[] = (Object[]) iterator.next();
								DIS_SUMM_EVENT_MODI_DATE_TIME=nullCheck(object[0]);
								AUTHORIZED_DATE_TIME=nullCheck(object[1]);
								DISCHARGE_BILL_DATE_TIME=nullCheck(object[2]);
							 }*/
							 String qry="select EVENT_STATUS ,AUTHORIZED_DATE_TIME,DISCHARGE_BILL_DATE_TIME,MT_STATUS,BL_STATUS from dreamsol_discharge_announce_vw where PATIENT_ID='"+rs.getString("PATIENT_ID").toString()+"' ";
							 List dataListEcho = cbt.executeAllSelectQuery(qry, connection);
							 String EVENT_STATUS="NA",AUTHORIZED_DATE_TIME="NA",DISCHARGE_BILL_DATE_TIME="NA",MT_STATUS="NA", BL_STATUS="NA";
							 for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
							 {
								Object object[] = (Object[]) iterator.next();
								EVENT_STATUS=nullCheck(object[0]);
								AUTHORIZED_DATE_TIME=nullCheck(object[1]);
								DISCHARGE_BILL_DATE_TIME=nullCheck(object[2]);
								MT_STATUS=nullCheck(object[3]);
								BL_STATUS=nullCheck(object[4]);
							 }
							 
							 
							 //System.out.println("data fetch for update  second method call");
							 /**Code for MT Close */
									 if (rs.getString("EVENT_STATUS").toString().equalsIgnoreCase("4") && !EVENT_STATUS.equalsIgnoreCase("4") )
									 {
										System.out.println("Inside MT CLose");
										 // escalation time for Flr manager
										 String esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:59", "Y");
											
										boolean chkData = chkdata( rs.getString("PATIENT_ID"), connection);
										String data_id = chkNewDataId(rs.getString("PATIENT_ID"), connection);
										System.out.println("chkData   "+chkData);
										System.out.println("data_id   "+data_id);
										if (chkData && !data_id.equalsIgnoreCase("NA")){
											System.out.println(" manab MT");
											StringBuilder  sb = new StringBuilder();
											sb.append(" update dreamsol_discharge_announce_vw set  ");
											sb.append(" MT_TICKET_NO = '"+ ticketMaker("FM_TICKET_NO", connection) );
											sb.append("' , FM_LEVEL = 'Level 1" );
											sb.append("' , MT_FM_ESCALATION_DATE = '"+esctm.split("#")[0]+"" );
											sb.append("' , MT_FM_ESCALATION_TIME = '"+esctm.split("#")[1]+""  );
											sb.append("' , MT_STATUS = 'FM-Pending" );
											sb.append("' , EVENT_STATUS = '"+rs.getString("EVENT_STATUS").toString()+"" );
											sb.append("' , DIS_SUMM_EVENT_MODI_DATE_TIME = '" + rs.getString("DIS_SUMM_EVENT_MODI_DATE_TIME") );
											sb.append("' where id=" + data_id + "");
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
												wherClause.put("level", "Level 1");
												wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
												wherClause.put("action_time", DateUtil.getCurrentTime());
												wherClause.put("status", "MT Close");
												wherClause.put("comment",  "NA");
												//wherClause.put("dept", department);
												if (wherClause != null && wherClause.size() > 0)
												{
													InsertDataTable ob1 = new InsertDataTable();
													List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
													for (Map.Entry<String, Object> entry : wherClause.entrySet())
													{
														ob1 = new InsertDataTable();
														ob1.setColName(entry.getKey());
														ob1.setDataName(entry.getValue().toString());
														insertDataHis.add(ob1);
													}
													boolean updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
													insertDataHis.clear();
													wherClause.clear();
												// another history for opening FLoor Manager Ticket
													if(updateFeed)
													{
														wherClause.put("dream_id", data_id);
														wherClause.put("uhid", rs.getString("PATIENT_ID"));
														wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
														wherClause.put("level", "Level 1");
														wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
														wherClause.put("action_time", DateUtil.getCurrentTime());
														wherClause.put("status", "FM Pending");
														wherClause.put("comment",  "FM Allo to(9935370850)");
														if (wherClause != null && wherClause.size() > 0)
														{
															for (Map.Entry<String, Object> entry : wherClause.entrySet())
															{
																ob1 = new InsertDataTable();
																ob1.setColName(entry.getKey());
																ob1.setDataName(entry.getValue().toString());
																insertDataHis.add(ob1);
															}
															updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
															insertDataHis.clear();
														}
													}
												}
											
											}
											
										
										}
									}
									
									 /**Code for Floor Manager Close */
									 else  if (rs.getString("AUTHORIZED_DATE_TIME")!=null && AUTHORIZED_DATE_TIME.equalsIgnoreCase("NA") )
									 {
										 System.out.println("Inside Floor Manager CLose");
											boolean chkData = chkdata( rs.getString("PATIENT_ID"), connection);
											String data_id = chkNewDataId(rs.getString("PATIENT_ID"), connection);
											System.out.println("chkData   "+chkData);
											System.out.println("data_id   "+data_id);
											if (chkData && !data_id.equalsIgnoreCase("NA")){
												System.out.println(" manab FM ");
												
												StringBuilder  sb = new StringBuilder();
												sb.append(" update dreamsol_discharge_announce_vw set  ");
												sb.append(" AUTHORIZED_DATE_TIME = '" + rs.getString("AUTHORIZED_DATE_TIME") );
												sb.append("' , EVENT_STATUS = '"+rs.getString("EVENT_STATUS").toString()+"" );
												sb.append("' , MT_STATUS = 'Close" );
												if(BL_STATUS.equalsIgnoreCase("Close") )
												{
													sb.append("' , STATUS = 'Close" );
												}
												sb.append("' where id=" + data_id + "");
												////System.out.println(sb.toString());
												int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
												sb.setLength(0);
												if (chkUpdate > 0)
												{
													Map<String, Object> wherClause = new HashMap<String, Object>();
													wherClause.put("dream_id", data_id);
													wherClause.put("uhid", rs.getString("PATIENT_ID"));
													wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
													wherClause.put("level", "Level 1");
													wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
													wherClause.put("action_time", DateUtil.getCurrentTime());
													wherClause.put("status", "FM Close");
													wherClause.put("comment",  "NA");
													if (wherClause != null && wherClause.size() > 0)
													{
														InsertDataTable ob1 = new InsertDataTable();
														List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
														for (Map.Entry<String, Object> entry : wherClause.entrySet())
														{
															ob1 = new InsertDataTable();
															ob1.setColName(entry.getKey());
															ob1.setDataName(entry.getValue().toString());
															insertDataHis.add(ob1);
														}
														boolean updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
														insertDataHis.clear();
													}
												
												}
												
											
											}
										}
									 /**Code for Biiling Close */
									 
									 else  if (rs.getString("DISCHARGE_BILL_DATE_TIME")!=null && DISCHARGE_BILL_DATE_TIME.equalsIgnoreCase("NA") ){
										 System.out.println("Inside BL CLose");
											boolean chkData = chkdata( rs.getString("PATIENT_ID"), connection);
											String data_id = chkNewDataId(rs.getString("PATIENT_ID"), connection);
											System.out.println("chkData   "+chkData);
											System.out.println("data_id   "+data_id);
											if (chkData && !data_id.equalsIgnoreCase("NA")){
												System.out.println(" manab ");
												
												StringBuilder  sb = new StringBuilder();
												sb.append(" update dreamsol_discharge_announce_vw set  ");
												sb.append(" DISCHARGE_BILL_DATE_TIME = '" + rs.getString("DISCHARGE_BILL_DATE_TIME") );
												sb.append("' , EVENT_STATUS = '"+rs.getString("EVENT_STATUS").toString()+"" );
												sb.append("' , BL_STATUS = 'Close" );
												if(MT_STATUS.equalsIgnoreCase("Close") )
												{
													sb.append("' , STATUS = 'Close" );
												}
												sb.append("' where id=" + data_id + "");
												int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
												sb.setLength(0);
												if (chkUpdate > 0)
												{

													Map<String, Object> wherClause = new HashMap<String, Object>();
													wherClause.put("dream_id", data_id);
													wherClause.put("uhid", rs.getString("PATIENT_ID"));
													wherClause.put("encounter_id", rs.getString("ENCOUNTER_ID"));
													wherClause.put("level", "Level 1");
													wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
													wherClause.put("action_time", DateUtil.getCurrentTime());
													wherClause.put("status", "BL Close");
													wherClause.put("comment",  "NA");
													if (wherClause != null && wherClause.size() > 0)
													{
														InsertDataTable ob1 = new InsertDataTable();
														List<InsertDataTable> insertDataHis = new ArrayList<InsertDataTable>();
														for (Map.Entry<String, Object> entry : wherClause.entrySet())
														{
															ob1 = new InsertDataTable();
															ob1.setColName(entry.getKey());
															ob1.setDataName(entry.getValue().toString());
															insertDataHis.add(ob1);
														}
														boolean updateFeed = cbt.insertIntoTable("discharge_announce_history", insertDataHis, connection);
														insertDataHis.clear();
													}
												
												}
												
											
											}
										}
									
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
	
	public String nullCheck(Object value)
	{
		return (value != null && !value.toString().equals("")?value.toString():"NA");
	}
	private String ticketMaker(String ticketFor,SessionFactory connection) {
		// TODO Auto-generated method stub
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List list=null;
		if(ticketFor.equalsIgnoreCase("MT_TICKET_NO"))
			list = cbt.executeAllSelectQuery("SELECT MT_TICKET_NO FROM dreamsol_discharge_announce_vw  ORDER BY id DESC LIMIT 1", connection);
		else if(ticketFor.equalsIgnoreCase("FM_TICKET_NO"))
			list = cbt.executeAllSelectQuery("SELECT MT_TICKET_NO FROM dreamsol_discharge_announce_vw  ORDER BY id DESC LIMIT 1", connection);
		else if(ticketFor.equalsIgnoreCase("BL_TICKET_NO"))
			list = cbt.executeAllSelectQuery("SELECT BL_TICKET_NO FROM dreamsol_discharge_announce_vw ORDER BY id DESC LIMIT 1", connection);
		
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(ticketFor.equalsIgnoreCase("MT_TICKET_NO"))
					return "MT-" + (Integer.parseInt(object.toString().substring(3)) + 1);
				else if(ticketFor.equalsIgnoreCase("BL_TICKET_NO"))
					return "BL-" + (Integer.parseInt(object.toString().substring(3)) + 1);
				if(ticketFor.equalsIgnoreCase("FM_TICKET_NO"))
					return "FM-" + (Integer.parseInt(object.toString().substring(3)) );
			}
		}
		else
		{
			if(ticketFor.equalsIgnoreCase("MT_TICKET_NO"))
				return "MT-1001";
			else if(ticketFor.equalsIgnoreCase("BL_TICKET_NO"))
				return "BL-1001";
			if(ticketFor.equalsIgnoreCase("FM_TICKET_NO"))
				return "FM-1001";
		}
		return null;
	}
	
	private String fetchLastReqstDateTime(SessionFactory connection)
	{

		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" SELECT DISCAHRGE_ADVICE_ADDED_DATE, id FROM dreamsol_discharge_announce_vw ORDER BY DISCAHRGE_ADVICE_ADDED_DATE DESC LIMIT 1".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);
			
				for (Iterator it = lastIdList.iterator(); it.hasNext();)
				{
					Object[] object = (Object[]) it.next();
					lastDataTime = nullCheck(object[0]) ;
					if(lastDataTime.equalsIgnoreCase("NA"))
					{
						lastDataTime=DateUtil.getCurrentDateUSFormat()+" 00:00:00.0";
					}
					 System.out.println("Last Order Normal Time  >>>>>>  " + lastDataTime);
				}
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	
	}
	
	private String fetchLastUpdateDateTime(SessionFactory connection2)
	{

		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" SELECT INSERT_DATE, id FROM `dreamsol_ip_trfr_vw` ORDER BY UPDATE_DATE DESC LIMIT 1".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

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





	

	private boolean chkdata(String patId, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		boolean sts = false ; 
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//System.out.println("aarif query>>>>>>>>>>>>>>>>.   select id from dreamsol_ip_trfr_vw where TFR_REQ_STATUS = '"+status+"' and  REQUEST_DATE_TIME = '"+reqDateTime+"' and  PATIENT_ID = '"+patId+"'");
			List dataListEcho = cbt.executeAllSelectQuery("select id from dreamsol_discharge_announce_vw where   PATIENT_ID = '"+patId+"'", connection);
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
			e.printStackTrace();
		}
		
		return sts;
	}

	private String changeStatusName(String status)
	{
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

	private String chkNewDataId(String UHID, SessionFactory connection2)
	{
		String data_id = "NA";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//System.out.println("select id,   from dreamsol_ip_trfr_vw where   PATIENT_ID = '"+UHID+"'");
			List dataListEcho = cbt.executeAllSelectQuery("select id  from dreamsol_discharge_announce_vw where   PATIENT_ID = '"+UHID+"'", connection);
			//System.out.println("select id  from dreamsol_discharge_announce_vw where   PATIENT_ID = '"+UHID+"'");
			if(dataListEcho.size()!=0){
			System.out.println(" size >>>>>>>>>>>>>>>>>>>> "+dataListEcho.size()+" uhid>>>>>>"+UHID);
			}
			
			for (Iterator it = dataListEcho.iterator(); it.hasNext();)
			{
				Object object = (Object) it.next();
				data_id =  object.toString() ;
				// System.out.println("Last update Time  >>>>>>  " + data_id);
			}
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data_id;
	}
	

	private boolean chkNewEntry(String PATIENT_ID, SessionFactory connection2) 
	{
		boolean check = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("select id, PATIENT_ID from dreamsol_discharge_announce_vw where  PATIENT_ID = '"+ PATIENT_ID + "' ", connection);
			if (dataListEcho != null && !dataListEcho.isEmpty() && dataListEcho.size() > 0)
			{
				check = true;
				dataListEcho.clear();
			} else
			{
				check = false;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return check;

	}

	public static void main(String[] args) 
	{
		Thread.State state ;
		try
		{
			
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			sessionHis = HisHibernateSessionFactory.getSession();
			Thread uclient=new Thread(new HISDIschargeAnnounceIntegration(connection, sessionHis));
			state = uclient.getState(); 
			if(!state.toString().equalsIgnoreCase("RUNNABLE"))
					uclient.start();
		}
		catch(Exception E)
		{
			try 
			{
				OutputStream out = new FileOutputStream(new File("c://output.txt"));
				byte[] b=E.toString().getBytes();
				out.write(b);
				out.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			E.printStackTrace();
		}
	}
}