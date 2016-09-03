package com.Over2Cloud.ctrl.lab.order;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;

public class LabOrderFetchHelper {

	
	
	public void DATAFetchLabIP(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {
		// TODO Auto-generated method stub


		java.sql.Connection con = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsPat = null;
		ResultSet rsPat4HighCostMed = null;
		String pri = "Routine";
		String high_med = "NA";
		String ORD_QTY="NA";
		boolean chkNeweNTRY = false;
		String ordNameChange = null;
		
		
		
		
		String UHID = "NA", Patient_Name = "NA", CONTACT1_NO = "NA", CONTACT2_NO = "NA",ORDERING_DOCTOR = "NA", BED_NUM = "NA",
		WARD = "NA", ADMITING_DOCTOR = "NA", ENCOUNTER_ID = "NA", ORDERING_DOCTOR_SPECIALTY = "NA", SPECIMEN_NO = "NA",
		ORDER_CATALOG_CODE = "NA",
		SERVICE_NAME = "NA",
		Low_Range = "NA",
		High_Range = "NA",
		Result = "NA",
		TEST_UNITS = "NA",
		LabName ="NA",
		Order_Date = "NA",
		SPEC_COLLTD_DATE_TIME = "NA",
		SPEC_DIS_DT_TIME = "NA",
		SPEC_REG_DT_TIME = "NA",
		RELEASED_DATE = "NA",
		CRITICAL_VALUE_YN = "NA",
		RESULT_VERI_D_T = "NA",
		ORDER_STATUS_CODE = "NA",
		LONG_DESC = "NA",
		SPEC_COLLTD_DATE = "NA",
		SPEC_COLLTD_TIME = "NA",
		SPEC_DIS_DATE = "NA",
		SPEC_DIS_TIME = "NA",
		SPEC_REG_DATE = "NA",
		SPEC_REG_TIME = "NA",
		
		RESULT_VERI_DATE = "NA",
		RESULT_VERI_TIME = "NA",
		
		
		MODIFIED_DATE = "NA";
		try
		{
			//String ENCOUNTER_ID = "NA";
			String PRIORITY = "Routine";
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			//System.out.println("day  " + day);
			String lastORDDateTime = lastLabOrderdateTimegetIP(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");

			st = con.createStatement();
			st2 = con.createStatement();
			//System.out.println("last date>>> " + lastORDDateTime);
			//rs = st2.executeQuery(" select * from Dreamsol_IP_CR_TST_VW where MODIFIED_DATE >= TO_DATE('" + lastORDDateTime + "','dd/mm/yyyy hh24:mi:ss') order by MODIFIED_DATE");
			rs = st2.executeQuery(" select * from Dreamsol_IP_CR_TST_VW where MODIFIED_DATE >= TO_DATE('" + lastORDDateTime + "','dd/mm/yyyy hh24:mi:ss') order by MODIFIED_DATE");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++)
			{
				String name = rsmd.getColumnName(i); // Do stuffwith name
				System.out.println(" name " + i + " : " + name);
			}
			
			while (rs.next())
			{
				
				insertData.clear();
				ob = new InsertDataTable();
				ob.setColName("UHID");
				if (rs.getString("UHID") != null && !rs.getString("UHID").toString().equals(""))
				{
					UHID = rs.getString("UHID");
					ob.setDataName(UHID);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Patient_Name");
				if (rs.getString("Patient Name") != null && !rs.getString("Patient Name").toString().equals(""))
				{
					Patient_Name = rs.getString("Patient Name");
					ob.setDataName(Patient_Name);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("CONTACT1_NO");
				if (rs.getString("CONTACT1_NO") != null && !rs.getString("CONTACT1_NO").toString().equals(""))
				{
					CONTACT1_NO = rs.getString("CONTACT1_NO");
					ob.setDataName(CONTACT1_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("CONTACT2_NO");
				if (rs.getString("CONTACT2_NO") != null && !rs.getString("CONTACT2_NO").toString().equals(""))
				{
					CONTACT2_NO = rs.getString("CONTACT2_NO");
					ob.setDataName(CONTACT2_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("BED_NUM");
				if (rs.getString("BED_NUM") != null && !rs.getString("BED_NUM").toString().equals(""))
				{
					BED_NUM = rs.getString("BED_NUM");
					ob.setDataName(BED_NUM);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("WARD");
				if (rs.getString("WARD") != null && !rs.getString("WARD").toString().equals(""))
				{
					WARD = rs.getString("WARD");
					ob.setDataName(WARD);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ADMITING_DOCTOR");
				if (rs.getString("ADMITING_DOCTOR") != null && !rs.getString("ADMITING_DOCTOR").toString().equals(""))
				{
					ADMITING_DOCTOR = rs.getString("ADMITING_DOCTOR");
					ob.setDataName(ADMITING_DOCTOR);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR");
				if (rs.getString("ORDERING_DOCTOR") != null && !rs.getString("ORDERING_DOCTOR").toString().equals(""))
				{
					
					ORDERING_DOCTOR = rs.getString("ORDERING_DOCTOR");
					ob.setDataName(ORDERING_DOCTOR);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR_SPECIALTY");
				if (rs.getString("ORDERING_DOCTOR_SPECIALTY") != null && !rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equals(""))
				{
					
					ORDERING_DOCTOR_SPECIALTY = rs.getString("ORDERING_DOCTOR_SPECIALTY");
					ob.setDataName(ORDERING_DOCTOR_SPECIALTY);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SPECIMEN_NO");
				if (rs.getString("SPECIMEN_NO") != null && !rs.getString("SPECIMEN_NO").toString().equals(""))
				{
					SPECIMEN_NO = rs.getString("SPECIMEN_NO");
					ob.setDataName(SPECIMEN_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_CATALOG_CODE");
				if (rs.getString("ORDER_CATALOG_CODE") != null && !rs.getString("ORDER_CATALOG_CODE").toString().equals(""))
				{
					ORDER_CATALOG_CODE = rs.getString("ORDER_CATALOG_CODE");
					ob.setDataName(ORDER_CATALOG_CODE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SERVICE_NAME");
				if (rs.getString("SERVICE_NAME") != null && !rs.getString("SERVICE_NAME").toString().equals(""))
				{
					SERVICE_NAME = rs.getString("SERVICE_NAME");
					ob.setDataName(SERVICE_NAME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Low_Range");
				if (rs.getString("Low Range") != null && !rs.getString("Low Range").toString().equals(""))
				{
					Low_Range = rs.getString("Low Range");
					ob.setDataName(Low_Range);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ENCOUNTER_ID");
				if (rs.getString("ENCOUNTER_ID") != null && !rs.getString("ENCOUNTER_ID").toString().equals(""))
				{
					ENCOUNTER_ID = rs.getString("ENCOUNTER_ID");
					ob.setDataName(ENCOUNTER_ID);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("High_Range");
				if (rs.getString("High Range") != null && !rs.getString("High Range").toString().equals(""))
				{
					High_Range = rs.getString("High Range");
					ob.setDataName(High_Range);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Result");
				if (rs.getString("Result") != null && !rs.getString("Result").toString().equals(""))
				{
					Result = rs.getString("Result");
					ob.setDataName(Result);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("TEST_UNITS");
				if (rs.getString("TEST_UNITS") != null && !rs.getString("TEST_UNITS").toString().equals(""))
				{
					TEST_UNITS = rs.getString("TEST_UNITS");
					ob.setDataName(TEST_UNITS);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("RELEASED_DATE");
				if (rs.getString("RELEASED_DATE") != null && !rs.getString("RELEASED_DATE").toString().equals(""))
				{
					RELEASED_DATE = rs.getString("RELEASED_DATE");
					ob.setDataName(RELEASED_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LabName");
				if (rs.getString("LabName") != null && !rs.getString("LabName").toString().equals(""))
				{
					LabName = rs.getString("LabName");
					ob.setDataName(LabName);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Order_Date");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					Order_Date = rs.getString("Order_Date");
					ob.setDataName(Order_Date);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORD_DATE");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					ob.setDataName(rs.getString("Order_Date").split(" ")[0]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORD_TIME");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					ob.setDataName(rs.getString("Order_Date").split(" ")[1].split(":")[0]+":"+rs.getString("Order_Date").split(" ")[1].split(":")[1]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("CRITICAL_VALUE_YN");
				if (rs.getString("CRITICAL_VALUE_YN") != null && !rs.getString("CRITICAL_VALUE_YN").toString().equals(""))
				{
					CRITICAL_VALUE_YN = rs.getString("CRITICAL_VALUE_YN");
					ob.setDataName(CRITICAL_VALUE_YN);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_D_T");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_D_T = rs.getString("RESULT_VERI_D_T");
					ob.setDataName(RESULT_VERI_D_T);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_DATE");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_DATE = rs.getString("RESULT_VERI_D_T").split(" ")[0];
					ob.setDataName(RESULT_VERI_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_TIME");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_TIME = rs.getString("RESULT_VERI_D_T").split(" ")[1];
					ob.setDataName(RESULT_VERI_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_STATUS_CODE");
				if (rs.getString("ORDER_STATUS_CODE") != null && !rs.getString("ORDER_STATUS_CODE").toString().equals(""))
				{
					
					ORDER_STATUS_CODE = rs.getString("ORDER_STATUS_CODE");
					ob.setDataName(ORDER_STATUS_CODE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LONG_DESC");
				if (rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
				{
					
					LONG_DESC = rs.getString("LONG_DESC");
					
					if (LONG_DESC.equalsIgnoreCase("Registered/Specimen Received/Patient Arrived"))
					{
						LONG_DESC = "Registered";
					}
					ob.setDataName(LONG_DESC);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_DATE_TIME");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_DATE_TIME =  DateUtil.convertDateToIndianFormat(rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_COLLTD_DATE_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_DATE");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_DATE =  rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[0].toString()+" " ;
					ob.setDataName(SPEC_COLLTD_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_TIME");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_TIME =  rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_COLLTD_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_DT_TIME");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_DT_TIME = DateUtil.convertDateToIndianFormat(rs.getString("SPEC_DIS_DT_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_DIS_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_DIS_DT_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_DATE");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_DATE = rs.getString("SPEC_DIS_DT_TIME").split(" ")[0].toString()+"";
					ob.setDataName(SPEC_DIS_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_TIME");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_TIME = rs.getString("SPEC_DIS_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_DIS_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_DT_TIME");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_DT_TIME =  DateUtil.convertDateToIndianFormat(rs.getString("SPEC_REG_DT_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_REG_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_REG_DT_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_DATE");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_DATE =  rs.getString("SPEC_REG_DT_TIME").split(" ")[0].toString()+" ";
					ob.setDataName(SPEC_REG_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_TIME");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_TIME =  rs.getString("SPEC_REG_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_REG_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("MODIFIED_DATE");
				if (rs.getString("MODIFIED_DATE") != null && !rs.getString("MODIFIED_DATE").toString().equals(""))
				{
					MODIFIED_DATE = rs.getString("MODIFIED_DATE");
					ob.setDataName(MODIFIED_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				//NURSING_UNIT_CODE
				
				ob = new InsertDataTable();
				ob.setColName("NURSING_UNIT_CODE");
				if (rs.getString("NURSING_UNIT_CODE") != null && !rs.getString("NURSING_UNIT_CODE").toString().equals(""))
				{
					//MODIFIED_DATE = rs.getString("NURSING_UNIT_CODE");
					ob.setDataName(rs.getString("NURSING_UNIT_CODE"));
					
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				List dataListLab1stEsc = cbt.executeAllSelectQuery(" select tat1 from lab_report_escalation_detail where order_status='"+LONG_DESC+"' ".toString(), connection);
				// dataList = cbt.executeAllSelectQuery(query.toString(),
				// connectionSpace);
				String esctm = null;
				if (dataListLab1stEsc != null && dataListLab1stEsc.size() > 0)
				{
					Object object = (Object) dataListLab1stEsc.get(0);
					esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");

				}

				else
				{
					esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:15", "Y");

				}
				dataListLab1stEsc.clear();
				
				ob = new InsertDataTable();
				ob.setColName("escalation_date");
				ob.setDataName(esctm.split("#")[0]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(esctm.split("#")[1]);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("department");
				ob.setDataName("87");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("1");
				insertData.add(ob);
				
				//System.out.println("mm>>>>>.    "+insertData);
				
				// chk any duplicate entry 
				boolean chkExist = chkExistData(rs.getString("ORDER_STATUS_CODE"), rs.getString("ORDER_CATALOG_CODE"), rs.getString("SPECIMEN_NO"), connection );
				
				// if entry is not duplicate 
				if (!chkExist){
				int id = cot.insertDataReturnId("Dreamsol_IP_CR_TST_VW", insertData, connection);
				System.out.println("save id "+id);
				insertData.clear();
				
				if(id>0){
					Map<String, Object> wherClause = new HashMap<String, Object>();
					wherClause.put("dream_view_id", id);
					wherClause.put("specimen_no", rs.getString("SPECIMEN_NO"));
					wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
					wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
					wherClause.put("level", "Level 1");
					//wherClause.put("SPEC_COLLTD_DATE_TIME", SPEC_COLLTD_DATE_TIME);
					//wherClause.put("SPEC_DIS_DT_TIME", SPEC_DIS_DT_TIME);
					//wherClause.put("SPEC_REG_DT_TIME", SPEC_REG_DT_TIME);
					wherClause.put("status", LONG_DESC);

					if (wherClause != null && wherClause.size() > 0)
					{
						InsertDataTable ob1 = new InsertDataTable();
						List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
						insertData1.clear();
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							ob1 = new InsertDataTable();
							ob1.setColName(entry.getKey());
							ob1.setDataName(entry.getValue().toString());
							insertData1.add(ob1);
						}
						boolean updateFeed = cot.insertIntoTable("lab_order_history", insertData1, connection);
						insertData1.clear();
					}
				}
				}
				
				// if entry is duplicate 
				else{
					// this part for update the change status in previous order 
					// chk the status is change or not 
					
					boolean chkSameStatus  = chkSameStatusData(rs.getString("ORDER_STATUS_CODE"), rs.getString("ORDER_CATALOG_CODE"), rs.getString("SPECIMEN_NO"), connection );
					// if status is not same as in local db then update in main table and history table
					
					if(!chkSameStatus){
						
						
						
						StringBuilder sb = new StringBuilder();
						sb.append("update dreamsol_ip_cr_tst_vw set Low_Range='"+Low_Range+"', High_Range='"+High_Range+"', Result='"+Result+"', TEST_UNITS='"+TEST_UNITS+"', RELEASED_DATE='"+RELEASED_DATE+"', LabName='"+LabName+"', CRITICAL_VALUE_YN='"+CRITICAL_VALUE_YN+"', RESULT_VERI_D_T='"+RESULT_VERI_D_T+"', ORDER_STATUS_CODE='"+ORDER_STATUS_CODE+"', LONG_DESC='"+LONG_DESC+"', MODIFIED_DATE='"+MODIFIED_DATE+"', SPEC_COLLTD_DATE_TIME='"+SPEC_COLLTD_DATE_TIME+"', SPEC_DIS_DT_TIME='"+SPEC_DIS_DT_TIME+"', SPEC_REG_DT_TIME='"+SPEC_REG_DT_TIME+"' ");
						sb.append(", SPEC_COLLTD_DATE= '"+SPEC_COLLTD_DATE+"' ");
						sb.append(", SPEC_COLLTD_TIME= '"+SPEC_COLLTD_TIME+"' ");
						sb.append(", SPEC_DIS_DATE= '"+SPEC_DIS_DATE+"' ");
						sb.append(", SPEC_DIS_TIME= '"+SPEC_DIS_TIME+"' ");
						
						sb.append(", SPEC_REG_DATE= '"+SPEC_REG_DATE+"' ");
						sb.append(", SPEC_REG_TIME= '"+SPEC_REG_TIME+"' ");
						sb.append(", RESULT_VERI_DATE= '"+RESULT_VERI_DATE+"' ");
						sb.append(", RESULT_VERI_TIME= '"+RESULT_VERI_TIME+"' ");
						
						
						
						 
						if(LONG_DESC.equalsIgnoreCase("In Process"))
						{
							
							List dataListLab2stEsc = cbt.executeAllSelectQuery(" select tat1 from lab_report_escalation_detail where order_status='"+LONG_DESC+"' ".toString(), connection);
							// dataList = cbt.executeAllSelectQuery(query.toString(),
							// connectionSpace);
							String esctm2 = null;
							if (dataListLab1stEsc != null && dataListLab1stEsc.size() > 0)
							{
								Object object = (Object) dataListLab1stEsc.get(0);
								esctm2 = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");

							}

							else
							{
								esctm2 = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:15", "Y");

							}
							dataListLab2stEsc.clear();
							
							sb.append(" , escalation_date = '"+esctm2.split("#")[0]+"', escalation_time = '"+esctm2.split("#")[1]+"' ");
							
							
						}
						sb.append(" where SPECIMEN_NO='"+rs.getString("SPECIMEN_NO")+"' and ORDER_CATALOG_CODE='"+rs.getString("ORDER_CATALOG_CODE")+"'");
						int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
						
						String DataID = fetchDataID(SPECIMEN_NO, ORDER_CATALOG_CODE, connection);
						if(chkUpdate>0 && !DataID.equalsIgnoreCase("NA")){

							Map<String, Object> wherClause = new HashMap<String, Object>();
							wherClause.put("dream_view_id", DataID);
							wherClause.put("specimen_no", rs.getString("SPECIMEN_NO"));
							wherClause.put("action_date", DateUtil.convertDateToIndianFormat(MODIFIED_DATE.split(" ")[0]) );
							wherClause.put("action_time", MODIFIED_DATE.split(" ")[1].substring(0, 5));
							wherClause.put("level", "Level 1");
							//wherClause.put("SPEC_COLLTD_DATE_TIME", SPEC_COLLTD_DATE_TIME);
							//wherClause.put("SPEC_DIS_DT_TIME", SPEC_DIS_DT_TIME);
							//wherClause.put("SPEC_REG_DT_TIME", SPEC_REG_DT_TIME);
							wherClause.put("status", LONG_DESC);

							
							if (wherClause != null && wherClause.size() > 0)
							{
								InsertDataTable ob1 = new InsertDataTable();
								List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
								insertData1.clear();
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									ob1 = new InsertDataTable();
									ob1.setColName(entry.getKey());
									ob1.setDataName(entry.getValue().toString());
									insertData1.add(ob1);
								}
								boolean updateFeed = cot.insertIntoTable("lab_order_history", insertData1, connection);
								insertData1.clear();
							}
						
						}
					}
					
					//else if()
				}
				}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			log.info("Error in LabOderFetchHelper.java \n " + e.getStackTrace());
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

		}

	
	}
	
	
	public void DATAFetchLabOP(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {

		// TODO Auto-generated method stub


		java.sql.Connection con = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsPat = null;
		ResultSet rsPat4HighCostMed = null;
		String pri = "Routine";
		String high_med = "NA";
		String ORD_QTY="NA";
		boolean chkNeweNTRY = false;
		String ordNameChange = null;
		
		
		
		
		String UHID = "NA", Patient_Name = "NA", CONTACT1_NO = "NA", CONTACT2_NO = "NA",ORDERING_DOCTOR = "NA", BED_NUM = "NA",
		WARD = "NA", ADMITING_DOCTOR = "NA", ENCOUNTER_ID = "NA", ORDERING_DOCTOR_SPECIALTY = "NA", SPECIMEN_NO = "NA",
		ORDER_CATALOG_CODE = "NA",
		SERVICE_NAME = "NA",
		Low_Range = "NA",
		High_Range = "NA",
		Result = "NA",
		TEST_UNITS = "NA",
		LabName ="NA",
		Order_Date = "NA",
		SPEC_COLLTD_DATE_TIME = "NA",
		SPEC_DIS_DT_TIME = "NA",
		SPEC_REG_DT_TIME = "NA",
		RELEASED_DATE = "NA",
		CRITICAL_VALUE_YN = "NA",
		RESULT_VERI_D_T = "NA",
		ORDER_STATUS_CODE = "NA",
		LONG_DESC = "NA",
		MODIFIED_DATE = "NA",
		SPEC_COLLTD_DATE = "NA",
		SPEC_COLLTD_TIME = "NA",
		SPEC_DIS_DATE = "NA",
		SPEC_DIS_TIME = "NA",
		SPEC_REG_DATE = "NA",
		SPEC_REG_TIME = "NA",
		
		RESULT_VERI_DATE = "NA",
		RESULT_VERI_TIME = "NA";
		try
		{
			//String ENCOUNTER_ID = "NA";
			String PRIORITY = "Routine";
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			//System.out.println("day  " + day);
			String lastORDDateTime = lastLabOrderdateTimegetOP(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");

			st = con.createStatement();
			st2 = con.createStatement();
			//System.out.println("last date>>> " + lastORDDateTime);
			//rs = st2.executeQuery(" select * from Dreamsol_IP_CR_TST_VW where MODIFIED_DATE >= TO_DATE('" + lastORDDateTime + "','dd/mm/yyyy hh24:mi:ss') order by MODIFIED_DATE");
			rs = st2.executeQuery(" select * from Dreamsol_OP_CR_TST_VW where MODIFIED_DATE >= TO_DATE('" + lastORDDateTime + "','dd/mm/yyyy hh24:mi:ss') order by MODIFIED_DATE");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++)
			{
				String name = rsmd.getColumnName(i); // Do stuffwith name
				System.out.println(" name " + i + " : " + name);
			}
			
			while (rs.next())
			{
				
				insertData.clear();
				ob = new InsertDataTable();
				ob.setColName("UHID");
				if (rs.getString("UHID") != null && !rs.getString("UHID").toString().equals(""))
				{
					UHID = rs.getString("UHID");
					ob.setDataName(UHID);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Patient_Name");
				if (rs.getString("Patient Name") != null && !rs.getString("Patient Name").toString().equals(""))
				{
					Patient_Name = rs.getString("Patient Name");
					ob.setDataName(Patient_Name);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("CONTACT1_NO");
				if (rs.getString("CONTACT1_NO") != null && !rs.getString("CONTACT1_NO").toString().equals(""))
				{
					CONTACT1_NO = rs.getString("CONTACT1_NO");
					ob.setDataName(CONTACT1_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("CONTACT2_NO");
				if (rs.getString("CONTACT2_NO") != null && !rs.getString("CONTACT2_NO").toString().equals(""))
				{
					CONTACT2_NO = rs.getString("CONTACT2_NO");
					ob.setDataName(CONTACT2_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("BED_NUM");
				ob.setDataName("OPD");
				/*if (rs.getString("BED_NUM") != null && !rs.getString("BED_NUM").toString().equals(""))
				{
					BED_NUM = rs.getString("BED_NUM");
					ob.setDataName(BED_NUM);
				} else
				{
					ob.setDataName("NA");
				}*/
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("WARD");
				ob.setDataName("OPD");
				/*if (rs.getString("WARD") != null && !rs.getString("WARD").toString().equals(""))
				{
					WARD = rs.getString("WARD");
					ob.setDataName(WARD);
				} else
				{
					ob.setDataName("NA");
				}*/
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ADMITING_DOCTOR");
				ob.setDataName("NA");
				/*if (rs.getString("ADMITING_DOCTOR") != null && !rs.getString("ADMITING_DOCTOR").toString().equals(""))
				{
					ADMITING_DOCTOR = rs.getString("ADMITING_DOCTOR");
					ob.setDataName(ADMITING_DOCTOR);
				} else
				{
					ob.setDataName("NA");
				}*/
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR");
				if (rs.getString("ORDERING_DOCTOR") != null && !rs.getString("ORDERING_DOCTOR").toString().equals(""))
				{
					
					ORDERING_DOCTOR = rs.getString("ORDERING_DOCTOR");
					ob.setDataName(ORDERING_DOCTOR);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR_SPECIALTY");
				if (rs.getString("ORDERING_DOCTOR_SPECIALTY") != null && !rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equals(""))
				{
					
					ORDERING_DOCTOR_SPECIALTY = rs.getString("ORDERING_DOCTOR_SPECIALTY");
					ob.setDataName(ORDERING_DOCTOR_SPECIALTY);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SPECIMEN_NO");
				if (rs.getString("SPECIMEN_NO") != null && !rs.getString("SPECIMEN_NO").toString().equals(""))
				{
					SPECIMEN_NO = rs.getString("SPECIMEN_NO");
					ob.setDataName(SPECIMEN_NO);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_CATALOG_CODE");
				if (rs.getString("ORDER_CATALOG_CODE") != null && !rs.getString("ORDER_CATALOG_CODE").toString().equals(""))
				{
					ORDER_CATALOG_CODE = rs.getString("ORDER_CATALOG_CODE");
					ob.setDataName(ORDER_CATALOG_CODE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SERVICE_NAME");
				if (rs.getString("SERVICE_NAME") != null && !rs.getString("SERVICE_NAME").toString().equals(""))
				{
					SERVICE_NAME = rs.getString("SERVICE_NAME");
					ob.setDataName(SERVICE_NAME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Low_Range");
				if (rs.getString("Low Range") != null && !rs.getString("Low Range").toString().equals(""))
				{
					Low_Range = rs.getString("Low Range");
					ob.setDataName(Low_Range);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ENCOUNTER_ID");
				ob.setDataName("NA");
				/*if (rs.getString("ENCOUNTER_ID") != null && !rs.getString("ENCOUNTER_ID").toString().equals(""))
				{
					ENCOUNTER_ID = rs.getString("ENCOUNTER_ID");
					ob.setDataName(ENCOUNTER_ID);
				} else
				{
					ob.setDataName("NA");
				}*/
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("High_Range");
				if (rs.getString("High Range") != null && !rs.getString("High Range").toString().equals(""))
				{
					High_Range = rs.getString("High Range");
					ob.setDataName(High_Range);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Result");
				if (rs.getString("Result") != null && !rs.getString("Result").toString().equals(""))
				{
					Result = rs.getString("Result");
					ob.setDataName(Result);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("TEST_UNITS");
				if (rs.getString("TEST_UNITS") != null && !rs.getString("TEST_UNITS").toString().equals(""))
				{
					TEST_UNITS = rs.getString("TEST_UNITS");
					ob.setDataName(TEST_UNITS);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("RELEASED_DATE");
				if (rs.getString("RELEASED_DATE") != null && !rs.getString("RELEASED_DATE").toString().equals(""))
				{
					RELEASED_DATE = rs.getString("RELEASED_DATE");
					ob.setDataName(RELEASED_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LabName");
				if (rs.getString("LabName") != null && !rs.getString("LabName").toString().equals(""))
				{
					LabName = rs.getString("LabName");
					ob.setDataName(LabName);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Order_Date");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					Order_Date = rs.getString("Order_Date");
					ob.setDataName(Order_Date);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORD_DATE");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					ob.setDataName(rs.getString("Order_Date").split(" ")[0]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORD_TIME");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					ob.setDataName(rs.getString("Order_Date").split(" ")[1].split(":")[0]+":"+rs.getString("Order_Date").split(" ")[1].split(":")[1]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("CRITICAL_VALUE_YN");
				if (rs.getString("CRITICAL_VALUE_YN") != null && !rs.getString("CRITICAL_VALUE_YN").toString().equals(""))
				{
					CRITICAL_VALUE_YN = rs.getString("CRITICAL_VALUE_YN");
					ob.setDataName(CRITICAL_VALUE_YN);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_D_T");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_D_T = rs.getString("RESULT_VERI_D_T");
					ob.setDataName(RESULT_VERI_D_T);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_DATE");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_DATE = rs.getString("RESULT_VERI_D_T").split(" ")[0];
					ob.setDataName(RESULT_VERI_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_TIME");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					RESULT_VERI_TIME = rs.getString("RESULT_VERI_D_T").split(" ")[1];
					ob.setDataName(RESULT_VERI_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_STATUS_CODE");
				if (rs.getString("ORDER_STATUS_CODE") != null && !rs.getString("ORDER_STATUS_CODE").toString().equals(""))
				{
					
					ORDER_STATUS_CODE = rs.getString("ORDER_STATUS_CODE");
					ob.setDataName(ORDER_STATUS_CODE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LONG_DESC");
				if (rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
				{
					LONG_DESC = rs.getString("LONG_DESC");
					if(LONG_DESC.equalsIgnoreCase("Registered/Specimen Received/Patient Arrived"))
					{
						LONG_DESC = "Registered";
					}
					ob.setDataName(LONG_DESC);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_DATE_TIME");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_DATE_TIME =  DateUtil.convertDateToIndianFormat(rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[1].toString().substring(0, 5) ;
					ob.setDataName(SPEC_COLLTD_DATE_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_DATE");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_DATE =  rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[0].toString()+" " ;
					ob.setDataName(SPEC_COLLTD_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_COLLTD_TIME");
				if (rs.getString("SPEC_COLLTD_DATE_TIME") != null && !rs.getString("SPEC_COLLTD_DATE_TIME").toString().equals(""))
				{
					SPEC_COLLTD_TIME =  rs.getString("SPEC_COLLTD_DATE_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_COLLTD_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_DT_TIME");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_DT_TIME = DateUtil.convertDateToIndianFormat(rs.getString("SPEC_DIS_DT_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_DIS_DT_TIME").split(" ")[1].toString().substring(0, 5) ;
					ob.setDataName(SPEC_DIS_DT_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_DATE");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_DATE = rs.getString("SPEC_DIS_DT_TIME").split(" ")[0].toString()+"";
					ob.setDataName(SPEC_DIS_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_DIS_TIME");
				if (rs.getString("SPEC_DIS_DT_TIME") != null && !rs.getString("SPEC_DIS_DT_TIME").toString().equals(""))
				{
					SPEC_DIS_TIME = rs.getString("SPEC_DIS_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_DIS_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_DT_TIME");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_DT_TIME =  DateUtil.convertDateToIndianFormat(rs.getString("SPEC_REG_DT_TIME").split(" ")[0].toString())+" "+rs.getString("SPEC_REG_DT_TIME").split(" ")[1].toString().substring(0, 5) ;
					ob.setDataName(SPEC_REG_DT_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_DATE");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_DATE =  rs.getString("SPEC_REG_DT_TIME").split(" ")[0].toString()+" ";
					ob.setDataName(SPEC_REG_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("SPEC_REG_TIME");
				if (rs.getString("SPEC_REG_DT_TIME") != null && !rs.getString("SPEC_REG_DT_TIME").toString().equals(""))
				{
					SPEC_REG_TIME =  rs.getString("SPEC_REG_DT_TIME").split(" ")[1].toString() ;
					ob.setDataName(SPEC_REG_TIME);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("MODIFIED_DATE");
				if (rs.getString("MODIFIED_DATE") != null && !rs.getString("MODIFIED_DATE").toString().equals(""))
				{
					MODIFIED_DATE = rs.getString("MODIFIED_DATE");
					ob.setDataName(MODIFIED_DATE);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				//NURSING_UNIT_CODE
				
				ob = new InsertDataTable();
				ob.setColName("NURSING_UNIT_CODE");
				ob.setDataName("OPD");
				/*if (rs.getString("NURSING_UNIT_CODE") != null && !rs.getString("NURSING_UNIT_CODE").toString().equals(""))
				{
					MODIFIED_DATE = rs.getString("NURSING_UNIT_CODE");
					ob.setDataName(MODIFIED_DATE);
				} else
				{
					ob.setDataName("NA");
				}*/
				insertData.add(ob);
				
				
CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				List dataListLab1stEsc = cbt.executeAllSelectQuery(" select tat1 from lab_report_escalation_detail where order_status='"+LONG_DESC+"' ".toString(), connection);
				// dataList = cbt.executeAllSelectQuery(query.toString(),
				// connectionSpace);
				String esctm = null;
				if (dataListLab1stEsc != null && dataListLab1stEsc.size() > 0)
				{
					Object object = (Object) dataListLab1stEsc.get(0);
					esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");

				}

				else
				{
					esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:15", "Y");

				}
				dataListLab1stEsc.clear();
				
				ob = new InsertDataTable();
				ob.setColName("escalation_date");
				ob.setDataName(esctm.split("#")[0]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(esctm.split("#")[1]);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("department");
				ob.setDataName("87");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("1");
				insertData.add(ob);
				
				//System.out.println("mm>>>>>.    "+insertData);
				
				// chk any duplicate entry 
				boolean chkExist = chkExistData(rs.getString("ORDER_STATUS_CODE"), rs.getString("ORDER_CATALOG_CODE"), rs.getString("SPECIMEN_NO"), connection );
				
				// if entry is not duplicate 
				if (!chkExist){
				int id = cot.insertDataReturnId("Dreamsol_IP_CR_TST_VW", insertData, connection);
				System.out.println("save id "+id);
				insertData.clear();
				
				if(id>0){
					Map<String, Object> wherClause = new HashMap<String, Object>();
					wherClause.put("dream_view_id", id);
					wherClause.put("specimen_no", rs.getString("SPECIMEN_NO"));
					wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
					wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
					wherClause.put("level", "Level 1");
					//wherClause.put("SPEC_COLLTD_DATE_TIME", SPEC_COLLTD_DATE_TIME);
					//wherClause.put("SPEC_DIS_DT_TIME", SPEC_DIS_DT_TIME);
					//wherClause.put("SPEC_REG_DT_TIME", SPEC_REG_DT_TIME);
					wherClause.put("status", LONG_DESC);

					if (wherClause != null && wherClause.size() > 0)
					{
						InsertDataTable ob1 = new InsertDataTable();
						List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
						insertData1.clear();
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							ob1 = new InsertDataTable();
							ob1.setColName(entry.getKey());
							ob1.setDataName(entry.getValue().toString());
							insertData1.add(ob1);
						}
						boolean updateFeed = cot.insertIntoTable("lab_order_history", insertData1, connection);
						insertData1.clear();
					}
				}
				}
				
				// if entry is duplicate 
				else{
					// this part for update the change status in previous order 
					// chk the status is change or not 
					boolean chkSameStatus  = chkSameStatusData(rs.getString("ORDER_STATUS_CODE"), rs.getString("ORDER_CATALOG_CODE"), rs.getString("SPECIMEN_NO"), connection );
					// if status is not same as in local db then update in main table and history table
					
					if(!chkSameStatus){
						StringBuilder sb = new StringBuilder();
						sb.append("update dreamsol_ip_cr_tst_vw set Low_Range='"+Low_Range+"', High_Range='"+High_Range+"', Result='"+Result+"', TEST_UNITS='"+TEST_UNITS+"', RELEASED_DATE='"+RELEASED_DATE+"', LabName='"+LabName+"', CRITICAL_VALUE_YN='"+CRITICAL_VALUE_YN+"', RESULT_VERI_D_T='"+RESULT_VERI_D_T+"', ORDER_STATUS_CODE='"+ORDER_STATUS_CODE+"', LONG_DESC='"+LONG_DESC+"', MODIFIED_DATE='"+MODIFIED_DATE+"', SPEC_COLLTD_DATE_TIME='"+SPEC_COLLTD_DATE_TIME+"', SPEC_DIS_DT_TIME='"+SPEC_DIS_DT_TIME+"', SPEC_REG_DT_TIME='"+SPEC_REG_DT_TIME+"'  where SPECIMEN_NO='"+rs.getString("SPECIMEN_NO")+"' and ORDER_CATALOG_CODE='"+rs.getString("ORDER_CATALOG_CODE")+"'");
						int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
						
						String DataID = fetchDataID(SPECIMEN_NO, ORDER_CATALOG_CODE, connection);
						if(chkUpdate>0 && !DataID.equalsIgnoreCase("NA")){

							Map<String, Object> wherClause = new HashMap<String, Object>();
							wherClause.put("dream_view_id", DataID);
							wherClause.put("specimen_no", rs.getString("SPECIMEN_NO"));
							wherClause.put("action_date", DateUtil.getCurrentDateIndianFormat());
							wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());
							wherClause.put("level", "Level 1");
							//wherClause.put("SPEC_COLLTD_DATE_TIME", SPEC_COLLTD_DATE_TIME);
							//wherClause.put("SPEC_DIS_DT_TIME", SPEC_DIS_DT_TIME);
							//wherClause.put("SPEC_REG_DT_TIME", SPEC_REG_DT_TIME);
							wherClause.put("status", LONG_DESC);

							
							if (wherClause != null && wherClause.size() > 0)
							{
								InsertDataTable ob1 = new InsertDataTable();
								List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
								insertData1.clear();
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									ob1 = new InsertDataTable();
									ob1.setColName(entry.getKey());
									ob1.setDataName(entry.getValue().toString());
									insertData1.add(ob1);
								}
								boolean updateFeed = cot.insertIntoTable("lab_order_history", insertData1, connection);
								insertData1.clear();
							}
						
						}
					}
					
					//else if()
				}
				}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			log.info("Error in LabOderFetchHelper.java \n " + e.getStackTrace());
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

		}

	
	
	}
	
	private String fetchDataID(String specNo, String ordCatCode,
			SessionFactory connection) {

		// TODO Auto-generated method stub

		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id from dreamsol_ip_cr_tst_vw where SPECIMEN_NO='"+specNo+"' and ORDER_CATALOG_CODE='"+ordCatCode+"'".toString(), connection);
			if (lastIdList.size() > 0)
			{
				str = lastIdList.get(0).toString();
			} 

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	
	
	
	}


	private boolean chkSameStatusData(String status, String orderCode, String specNo,
			SessionFactory connection) {
		// TODO Auto-generated method stub

		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id from dreamsol_ip_cr_tst_vw where SPECIMEN_NO='"+specNo+"' and ORDER_CATALOG_CODE='"+orderCode+"' and ORDER_STATUS_CODE ='"+status+"'".toString(), connection);
			if (lastIdList.size() > 0)
			{
				str = true;
			} else
			{
				str = false;
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	
	
	}


	private boolean chkExistData(String status, String orderCode, String specNo,
			SessionFactory connection) {
		// TODO Auto-generated method stub


		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id from dreamsol_ip_cr_tst_vw where SPECIMEN_NO='"+specNo+"' and ORDER_CATALOG_CODE='"+orderCode+"'".toString(), connection);
			if (lastIdList.size() > 0)
			{
				str = true;
			} else
			{
				str = false;
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	
	}


	private String lastLabOrderdateTimegetIP(SessionFactory connection)
	{

		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT id, MODIFIED_DATE FROM dreamsol_ip_cr_tst_vw where NURSING_UNIT_CODE !='OPD' ORDER BY `MODIFIED_DATE` DESC LIMIT 1 ".toString(), connection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String lastDataTime1 = object[1].toString();
				String dateOne = DateUtil.convertDateToIndianFormat(lastDataTime1.split(" ")[0]);
				lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + lastDataTime1.split(" ")[1].substring(0, 8);
				System.out.println(lastDataTime);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lastDataTime;

	}
	
	private String lastLabOrderdateTimegetOP(SessionFactory connection)
	{

		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT id, MODIFIED_DATE FROM dreamsol_ip_cr_tst_vw where NURSING_UNIT_CODE ='OPD' ORDER BY `MODIFIED_DATE` DESC LIMIT 1 ".toString(), connection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String lastDataTime1 = object[1].toString();
				String dateOne = DateUtil.convertDateToIndianFormat(lastDataTime1.split(" ")[0]);
				lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + lastDataTime1.split(" ")[1].substring(0, 8);
				System.out.println(lastDataTime);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lastDataTime;

	}


/*	public void DATAFetchLabUpdate(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {
		// TODO Auto-generated method stub
		
	}*/


}