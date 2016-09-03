package com.Over2Cloud.Rnd;

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

public class HISTESTHelper
{

	public void CRCDATAFetchONE(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{
	}

	public void CRCDATAFetchOTM(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{

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
		String uhid = "NA", patient_name = "NA", assign_bed_num = "NA", nursing_unit = "NA",nursing_code = "NA", admitting_pra_name = "NA", attending_pra_name = "NA", speciality = "NA", admission_date_time = "NA", mark_arrival = "NA", date_of_birth = "NA", mark_arrival_time = "NA";
		try
		{
			String ENCOUNTER_ID = "NA";
			String PRIORITY = "Routine";
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			//System.out.println("day  " + day);

			String lastORDDateTime = lastPharmaOrderdateTimeget(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");

			st = con.createStatement();
			st2 = con.createStatement();
			//System.out.println("last date>>> " + lastORDDateTime);
			rs = st2.executeQuery(" select * from Dreamsol_IP_CR_TST_VW ");

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
				
				
				ob = new InsertDataTable();
				ob.setColName("UHID");
				if (rs.getString("UHID") != null && !rs.getString("UHID").toString().equals(""))
				{
					ob.setDataName(rs.getString("UHID"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Patient_Name");
				if (rs.getString("Patient Name") != null && !rs.getString("Patient Name").toString().equals(""))
				{
					ob.setDataName(rs.getString("Patient Name"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("CONTACT1_NO");
				if (rs.getString("CONTACT1_NO") != null && !rs.getString("CONTACT1_NO").toString().equals(""))
				{
					ob.setDataName(rs.getString("CONTACT1_NO"));
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
				ob.setColName("BED_NUM");
				if (rs.getString("BED_NUM") != null && !rs.getString("BED_NUM").toString().equals(""))
				{
					ob.setDataName(rs.getString("BED_NUM"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("WARD");
				if (rs.getString("WARD") != null && !rs.getString("WARD").toString().equals(""))
				{
					ob.setDataName(rs.getString("WARD"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ADMITING_DOCTOR");
				if (rs.getString("ADMITING_DOCTOR") != null && !rs.getString("ADMITING_DOCTOR").toString().equals(""))
				{
					ob.setDataName(rs.getString("ADMITING_DOCTOR"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR");
				if (rs.getString("ORDERING_DOCTOR") != null && !rs.getString("ORDERING_DOCTOR").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDERING_DOCTOR"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDERING_DOCTOR_SPECIALTY");
				if (rs.getString("ORDERING_DOCTOR_SPECIALTY") != null && !rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDERING_DOCTOR_SPECIALTY"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SPECIMEN_NO");
				if (rs.getString("SPECIMEN_NO") != null && !rs.getString("SPECIMEN_NO").toString().equals(""))
				{
					ob.setDataName(rs.getString("SPECIMEN_NO"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_CATALOG_CODE");
				if (rs.getString("ORDER_CATALOG_CODE") != null && !rs.getString("ORDER_CATALOG_CODE").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDER_CATALOG_CODE"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("SERVICE_NAME");
				if (rs.getString("SERVICE_NAME") != null && !rs.getString("SERVICE_NAME").toString().equals(""))
				{
					ob.setDataName(rs.getString("SERVICE_NAME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Low_Range");
				if (rs.getString("Low Range") != null && !rs.getString("Low Range").toString().equals(""))
				{
					ob.setDataName(rs.getString("Low Range"));
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
				ob.setColName("High_Range");
				if (rs.getString("High Range") != null && !rs.getString("High Range").toString().equals(""))
				{
					ob.setDataName(rs.getString("High Range"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Result");
				if (rs.getString("Result") != null && !rs.getString("Result").toString().equals(""))
				{
					ob.setDataName(rs.getString("Result"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("TEST_UNITS");
				if (rs.getString("TEST_UNITS") != null && !rs.getString("TEST_UNITS").toString().equals(""))
				{
					ob.setDataName(rs.getString("TEST_UNITS"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				ob = new InsertDataTable();
				ob.setColName("RELEASED_DATE");
				if (rs.getString("RELEASED_DATE") != null && !rs.getString("RELEASED_DATE").toString().equals(""))
				{
					ob.setDataName(rs.getString("RELEASED_DATE"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LabName");
				if (rs.getString("LabName") != null && !rs.getString("LabName").toString().equals(""))
				{
					ob.setDataName(rs.getString("LabName"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("Order_Date");
				if (rs.getString("Order_Date") != null && !rs.getString("Order_Date").toString().equals(""))
				{
					ob.setDataName(rs.getString("Order_Date"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("CRITICAL_VALUE_YN");
				if (rs.getString("CRITICAL_VALUE_YN") != null && !rs.getString("CRITICAL_VALUE_YN").toString().equals(""))
				{
					ob.setDataName(rs.getString("CRITICAL_VALUE_YN"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("RESULT_VERI_D_T");
				if (rs.getString("RESULT_VERI_D_T") != null && !rs.getString("RESULT_VERI_D_T").toString().equals(""))
				{
					ob.setDataName(rs.getString("RESULT_VERI_D_T"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ORDER_STATUS_CODE");
				if (rs.getString("ORDER_STATUS_CODE") != null && !rs.getString("ORDER_STATUS_CODE").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDER_STATUS_CODE"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("LONG_DESC");
				if (rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
				{
					ob.setDataName(rs.getString("LONG_DESC"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("MODIFIED_DATE");
				if (rs.getString("MODIFIED_DATE") != null && !rs.getString("MODIFIED_DATE").toString().equals(""))
				{
					ob.setDataName(rs.getString("MODIFIED_DATE"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				System.out.println("mm>>>>>.    "+insertData);
				
				int id = cot.insertDataReturnId("Dreamsol_IP_CR_TST_VW", insertData, connection);
				System.out.println("save id "+id);
				insertData.clear();
				/*
				
				
				System.out.println(rs.getString("DOC_NO"));
				
				System.out.println(rs.getString("ORDER_ID"));
				
				

				if (rs.getString("PRIORITY") != null && !rs.getString("PRIORITY").toString().equals(""))
				{
					PRIORITY = rs.getString("PRIORITY");
				}

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
				ob.setColName("ENCOUNTER_ID");
				if (rs.getString("TRN_GROUP_REF") != null && !rs.getString("TRN_GROUP_REF").toString().equals(""))
				{
					ob.setDataName(rs.getString("TRN_GROUP_REF"));

				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);
				
				String prio = "Routine";
				if(PRIORITY.equalsIgnoreCase("U")){
					prio ="Urgent";
				}
				ob = new InsertDataTable();
				ob.setColName("PRIORITY");
				if (prio != null && !prio.equals(""))
				{
					ob.setDataName(prio);

				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ORDER_ID");
				if (rs.getString("ORDER_ID") != null && !rs.getString("ORDER_ID").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDER_ID"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("DISPENSED_DATE_TIME");
				if (rs.getString("DISPENSED_DATE_TIME") != null && !rs.getString("DISPENSED_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("DISPENSED_DATE_TIME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("DISPENSED_DATE");
				if (rs.getString("DISPENSED_DATE_TIME") != null && !rs.getString("DISPENSED_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("DISPENSED_DATE_TIME").toString().split(" ")[0]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("DISPENSED_TIME");
				if (rs.getString("DISPENSED_DATE_TIME") != null && !rs.getString("DISPENSED_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("DISPENSED_DATE_TIME").toString().split(" ")[1].split(":")[0] + " " + rs.getString("DISPENSED_DATE_TIME").toString().split(" ")[1].split(":")[1]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ORD_DATE_TIME");
				if (rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORD_DATE_TIME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ORDER_QTY");
				if (rs.getString("ORDER_QTY") != null && !rs.getString("ORDER_QTY").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDER_QTY"));
					ORD_QTY = rs.getString("ORDER_QTY");
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ITEM_NAME");
				if (rs.getString("ITEM_NAME") != null && !rs.getString("ITEM_NAME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ITEM_NAME"));
					// //System.out.println("item name "+rs.getString("ITEM_NAME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("DISP_NO");
				if (rs.getString("DISP_NO") != null && !rs.getString("DISP_NO").toString().equals(""))
				{
					ob.setDataName(rs.getString("DISP_NO"));
					// //System.out.println("item name "+rs.getString("ITEM_NAME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("TRN_GROUP_REF");
				if (rs.getString("TRN_GROUP_REF") != null && !rs.getString("TRN_GROUP_REF").toString().equals(""))
				{
					ob.setDataName(rs.getString("TRN_GROUP_REF"));
					// //System.out.println("item name "+rs.getString("ITEM_NAME"));
					ENCOUNTER_ID = rs.getString("TRN_GROUP_REF");
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				
				 * ob = new InsertDataTable(); ob.setColName("DOC_NO"); if
				 * (rs.getString("DOC_NO")!=null &&
				 * !rs.getString("DOC_NO").toString().equals("")) {
				 * ob.setDataName(rs.getString("DOC_NO"));
				 * ////System.out.println("item name "+rs.getString("ITEM_NAME"));
				 * } else { ob.setDataName("NA"); } insertData.add(ob);
				 

				ob = new InsertDataTable();
				ob.setColName("DISP_QTY");
				if (rs.getString("DISP_QTY") != null && !rs.getString("DISP_QTY").toString().equals(""))
				{
					ob.setDataName(rs.getString("DISP_QTY"));
					// //System.out.println("item name "+rs.getString("ITEM_NAME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ADDED_DATE");
				if (rs.getString("ADDED_DATE") != null && !rs.getString("ADDED_DATE").toString().equals(""))
				{
					ob.setDataName(rs.getString("ADDED_DATE"));
					// //System.out.println("item name "+rs.getString("ITEM_NAME"));
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("STATUS");
				ob.setDataName("Pending");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ORDER_DATE");
				if (rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[0]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("ORDER_TIME");
				if (rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[1].split(":")[0] + " " + rs.getString("ORD_DATE_TIME").toString().split(" ")[1].split(":")[1]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				// High_Cost_Medicine

				ob = new InsertDataTable();
				ob.setColName("High_Cost_Medicine");
				if (rs.getString("High_Cost_Medicine") != null && !rs.getString("High_Cost_Medicine").toString().equals(""))
				{
					ob.setDataName(rs.getString("High_Cost_Medicine"));
					//ch.addMessage("Manab", "Alert", "8882572103", "High cost Medicine aa gaya : " + rs.getString("High_Cost_Medicine"), "", "Pending", "0", "ORD", connection);
					//ch.addMessage("Sanjay", "Alert", "8130772488", "High cost Medicine aa gaya Sanjay : " + rs.getString("High_Cost_Medicine"), "", "Pending", "0", "ORD", connection);
					//System.out.println("item name "+rs.getString("High_Cost_Medicine"));
					high_med = rs.getString("High_Cost_Medicine");
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				boolean isExist = ckhExist(rs.getString("ORDER_ID"), connection);
				if (!isExist)
				{

					int id = cot.insertDataReturnId("pharma_medicine_data", insertData, connection);
					//System.out.println("id   " + id);
					insertData.clear();
					if (id > 0)
					{
						chkNeweNTRY = true;
						// //System.out.println("Data Inserted in pharma_medicine_data");
						try
						{
							String chkEncounter = chkEncounterData(rs.getString("TRN_GROUP_REF").toString(), connection);
							if (chkEncounter.equalsIgnoreCase("not_exist"))
							{
								CommonOperInterface cbt = new CommonConFactory().createInterface();


								rsPat = st.executeQuery("select * from Dreamsol_IP_VW where UHIID='" + rs.getString("PATIENT_ID").toString() + "' ");

								while (rsPat.next())
								{

									uhid = rsPat.getString("UHIID");
									patient_name = rsPat.getString("PATIENT_NAME");
									assign_bed_num = rsPat.getString("ASSIGN_BED_NUM");
									nursing_unit = rsPat.getString("LONG_DESC");
									nursing_code = rsPat.getString("Assign_care_locn_code");
									admitting_pra_name = rsPat.getString("ADMITTING_PRACTITIONER_NAME");
									attending_pra_name = rsPat.getString("ATTENDING_PRACTITIONER_NAME");
									speciality = rsPat.getString("SPECIALITY");
									admission_date_time = DateUtil.currentdatetime();
									mark_arrival = DateUtil.currentdatetime();
									date_of_birth = DateUtil.getCurrentDateIndianFormat();
								}

								ob = new InsertDataTable();
								ob.setColName("uhid");
								ob.setDataName(uhid);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("patient_name");
								ob.setDataName(patient_name);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("encounter_id");
								ob.setDataName(ENCOUNTER_ID);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("assign_bed_num");
								ob.setDataName(assign_bed_num);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("nursing_unit");
								ob.setDataName(nursing_unit);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("admitting_pra_name");
								ob.setDataName(admitting_pra_name);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("attending_pra_name");
								ob.setDataName(attending_pra_name);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("speciality");
								ob.setDataName(speciality);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("admission_date_time");
								ob.setDataName(admission_date_time);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("nursing_code");
								ob.setDataName(nursing_code);
								insertData.add(ob);
								
								

								ob = new InsertDataTable();
								ob.setColName("admission_date");
								if (admission_date_time != null && !admission_date_time.equals("NA"))
								{
									ob.setDataName(admission_date_time.split(" ")[0]);
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("admission_time");
								if (admission_date_time != null && !admission_date_time.equals("NA"))
								{
									ob.setDataName(admission_date_time.split(" ")[1].split(":")[0] + " " + admission_date_time.split(" ")[1].split(":")[1]);
								} else
								{
									ob.setDataName("NA");
								}
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("mark_arrival");
								ob.setDataName(mark_arrival);
								insertData.add(ob);

								if (PRIORITY.equalsIgnoreCase("U"))
								{

									ob = new InsertDataTable();
									ob.setColName("priority");
									ob.setDataName("Urgent");
									insertData.add(ob);

								}

								ob = new InsertDataTable();
								ob.setColName("date_of_birth");
								ob.setDataName(date_of_birth);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("status");
								ob.setDataName("Ordered");
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("order_date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("order_time");
								ob.setDataName(DateUtil.getCurrentTimeHourMin());
								insertData.add(ob);

								String escDateTime = calculateEscDateTime(PRIORITY, connection);

								ob = new InsertDataTable();
								ob.setColName("escalation_date");
								ob.setDataName(DateUtil.convertDateToUSFormat(escDateTime.split(" ")[0].trim()));
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalation_time");
								ob.setDataName(escDateTime.split(" ")[1]);
								insertData.add(ob);

								int idPatientData = cot.insertDataReturnId("pharma_patient_detail", insertData, connection);
								
								StringBuilder sbDataUpdateData = new StringBuilder();
								sbDataUpdateData.append("update pharma_medicine_data set nursing_unit='"+nursing_unit+"' where encounter_id='" + ENCOUNTER_ID + "'");
								
								int chkDataTabUpdate = cot.executeAllUpdateQuery(sbDataUpdateData.toString(), connection);
								
								if (idPatientData > 0)
								{
									Map<String, Object> wherClause = new HashMap<String, Object>();
									wherClause.put("encounter_id", ENCOUNTER_ID);
									wherClause.put("action_by", "Auto");
									wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
									wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

									wherClause.put("close_by_id", "NA");
									wherClause.put("close_by_name", "NA");

									wherClause.put("comment", "Auto");
									wherClause.put("status", "Ordered");

									if (wherClause != null && wherClause.size() > 0)
									{
										InsertDataTable ob1 = new InsertDataTable();
										List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
										for (Map.Entry<String, Object> entry : wherClause.entrySet())
										{
											ob1 = new InsertDataTable();
											ob1.setColName(entry.getKey());
											ob1.setDataName(entry.getValue().toString());
											insertData1.add(ob1);
										}
										boolean updateFeed = cbt.insertIntoTable("pharma_patient_data_history", insertData1, connection);
										insertData1.clear();
									}
								}

							} else
							{
								if (PRIORITY.equalsIgnoreCase("U"))
								{

									StringBuilder sbPrio = new StringBuilder();
									sbPrio.append("update pharma_patient_detail set priority='Urgent' where encounter_id='" + ENCOUNTER_ID + "'");

									int chk = cot.executeAllUpdateQuery(sbPrio.toString(), connection);

								}
							}

							if (!high_med.equalsIgnoreCase("NA") && chkNeweNTRY)
							{
								String emailAlertId = fetchEmailAlert4Med(high_med, connection);
								if (!emailAlertId.equalsIgnoreCase("NA"))
								{
									System.out.println( rs.getString("PATIENT_ID").toString() );
									//System.out.println("lllll  2");
									

									String mailText = makeMailBody(rs.getString("ITEM_NAME"), high_med, rs.getString("ORDER_ID"), patient_name, uhid, nursing_unit, assign_bed_num, admitting_pra_name, speciality, ORD_QTY);
									//System.out.println("lllll  2  " +emailAlertId);
									if (emailAlertId.contains(","))
									{
										String emlaiId[] = emailAlertId.split(",");
										//System.out.println("lllll  3  ");
										for (int j = 0; j < emlaiId.length; j++)
										{
											
											//	System.out.println("lllll  4  "+emlaiId[j].toString());
												ch.addMail(emlaiId[j].toString(), "", emlaiId[j].toString(), "High Cost Medicine Alert ", mailText, "Report", "Pending", "0", "", "Pharmacy", connection);
											System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
										}
									} else
									{
										ch.addMail(emailAlertId, "", emailAlertId, "High Cost Medicine Alert ", mailText, "Report", "Pending", "0", "", "Pharmacy", connection);
									}
									
									high_med="NA";
									chkNeweNTRY= false;

								}
							}
						} catch (Exception e)
						{
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
				insertData.clear();

			*/}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
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

	private String fetchEmailAlert4Med(String code, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select contact_email_id from pharma_high_cost_medicine_alert where medicine_code='" + code + "' and status='Active'".toString(), connection);
			if (lastIdList.size() != 0)
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

	public String makeMailBody(String medName, String medCode, String ordID, String patName, String UHID, String nursing, String bed, String addDoc, String spec, String ORD_QTY)
	{
		// ////////////////////////////////////System.out.println("userName TESTTTT   "
		// +
		// userName);

		StringBuilder mailText = new StringBuilder("");
		// ////////////////////////////////////System.out.println("ip  " +ip);
		// 192.168.1.116
		// //////////////////////////////////////System.out.println("URL  " +URL);
		mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>" + "<font color='#000000' >Dear All,<br> </font></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'>" + "<font color='#000000' >The following medication has been requested to be used for the patient as per clinical needs:</font></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>" + "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Medicine Name :</font></td> " + "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ medName
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>"
				+ "Medicine Code:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ medCode
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "</tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Order ID :</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ ordID
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "</tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Patient Name:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ patName
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>UHID:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ UHID
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Nursing Unit:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ nursing
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Bed No:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;" + bed + "&nbsp;</font></td></tr>" + "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Admitting  Doc:</font></td>" + "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;" + addDoc + "&nbsp;</font></td></tr>" + "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Speciality:</font></td>" + "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;" + spec + "&nbsp;</font></td>" + "</tr>" 
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Order At:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ DateUtil.getCurrentDateIndianFormat()+" "+DateUtil.getCurrentTimeHourMin()
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:color:#000000;'>Order Quantity:</font></td>"
				+  "<td  align='left' width='30%'><font style='font-weight:color:#000000;'>&nbsp;"
				+ ORD_QTY
				+ "&nbsp;</font></td></tr>" +  "</table>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<br>As this is a high value and emergency use product, billing is requested to collect the interim payments and inform pharmacy of clearance ASAP for further dispensing the medication.");
		mailText.append("<br><b></b>");
		mailText.append("<br><font >Pharmacy to immediately dispense the medication on receiving the clearance from billing. ");
		mailText.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br></FONT>");
		mailText.append("<br><b></b>");
		mailText.append("<br>");
		return mailText.toString();
	}

	private String calculateEscDateTime(String priority, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		if (priority.equalsIgnoreCase("U"))
		{
			priority = "Urgent";
		} else
		{
			priority = "Routine";
		}
		String esctm = "00:30";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT L1ToL2,firstEsc,firstEscFlag,fromTime,toTime FROM escalation_order_detail ");
			query.append(" WHERE priority='" + priority + "' AND escSubDept='82'");
			// //System.out.println("::::::::::"+query);
			List list = cbt.executeAllSelectQuery(query.toString(), connection);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					if (object[0] != null && object[2] != null && object[2].toString().equalsIgnoreCase("0"))
					{
						esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());

					}
					if (object[2] != null && object[2].toString().equalsIgnoreCase("1") && object[0] != null && object[1] != null && object[3] != null && object[4] != null)
					{

						//System.out.println("time >>>>>     " + DateUtil.checkTimeBetween2Times(object[3].toString(), object[4].toString(), DateUtil.getCurrentTime()));
						if (object[0] != null && object[1] != null && object[3] != null && object[4] != null)
						{

							if (DateUtil.checkTimeBetween2Times(object[3].toString(), "24:00", DateUtil.getCurrentTime()))
							{
								esctm = DateUtil.getAddressingDatetime(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), "00:00", "00:00", object[1].toString());
								// esctm = '01-12-2015 12:00
							} else if (DateUtil.checkTimeBetween2Times("00:00", object[4].toString(), DateUtil.getCurrentTime()))
							{
								esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), "00:00", "00:00", object[1].toString());

							} else
							{
								esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());

							}
							// DateUtil.getEs
						} else
						{
							esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());

						}
					}

				}
				return esctm;
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return esctm;
	}

	public void CRCDATAPharmaUpdate(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		//System.out.println("day  " + day);

		try
		{

			String lastORDDateTime = lastDispatchORDdateTimeget(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*
			 * if ((DateUtil.getCurrentDay() == 0 &&
			 * DateUtil.comparebetweenTwodateTime
			 * (DateUtil.getCurrentDateUSFormat(), "09:45",
			 * DateUtil.getCurrentDateUSFormat(),
			 * DateUtil.getCurrentTimeHourMin()) &&
			 * DateUtil.comparebetweenTwodateTime
			 * (DateUtil.getCurrentDateUSFormat(),
			 * DateUtil.getCurrentTimeHourMin(),
			 * DateUtil.getCurrentDateUSFormat(), "16:30")) ||
			 * (day.equalsIgnoreCase("02") &&
			 * DateUtil.comparebetweenTwodateTime(
			 * DateUtil.getCurrentDateUSFormat(), "19:50",
			 * DateUtil.getCurrentDateUSFormat(),
			 * DateUtil.getCurrentTimeHourMin()) &&
			 * DateUtil.comparebetweenTwodateTime
			 * (DateUtil.getCurrentDateUSFormat(),
			 * DateUtil.getCurrentTimeHourMin(),
			 * DateUtil.getCurrentDateUSFormat(), "23:59"))) {
			 */
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");

			/*
			 * } else { con =
			 * DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR"
			 * , "dreamsol", "Dream_1$3"); }
			 */
			// con =
			// DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG",
			// "dreamsol", "Dream_1$3");
			st = con.createStatement();
			//System.out.println("lastORDDateTime " + lastORDDateTime) // 17162918 // 17163387;
			rs = st.executeQuery("select * from dreamsol_ph_pend_ord where  DOC_NO in  ('17162945', '17162941') ");
System.out.println( "'17162945', '17162941'");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			

			while (rs.next())
			{
				
				System.out.println("one "+rs.getString("DOC_NO").toString());
				System.out.println("two "+rs.getString("ORDER_ID").toString());
				
				/*
				String PATIENT_ID, ENCOUNTER_ID, ORDER_ID, DISPENSED_DATE_TIME, ORDER_QTY, ITEM_NAME, ORD_DATE_TIME, DISP_NO, DISP_QTY, DOC_NO, DOC_TYPE;

				PATIENT_ID = rs.getString("PATIENT_ID").toString();
				ENCOUNTER_ID = rs.getString("TRN_GROUP_REF").toString();
				ORDER_ID = rs.getString("ORDER_ID").toString();
				DISPENSED_DATE_TIME = rs.getString("DISPENSED_DATE_TIME").toString();
				ORDER_QTY = rs.getString("ORDER_QTY").toString();
				ITEM_NAME = rs.getString("ITEM_NAME").toString();
				ORD_DATE_TIME = rs.getString("ORD_DATE_TIME").toString();
				DISP_NO = rs.getString("DISP_NO").toString();
				DISP_QTY = rs.getString("DISP_QTY").toString();
				DOC_NO = rs.getString("DOC_NO").toString();
				DOC_TYPE = rs.getString("DOC_TYPE").toString();
				StringBuilder sb12 = new StringBuilder();
				String rem_qty = remQtyCalculate(ORDER_QTY, DISP_QTY);
				boolean isExist = ckhExistDis(ORDER_ID, connection);
				if (!isExist)
				{
					sb12.append("update pharma_medicine_data set DISPENSED_DATE_TIME='" + DISPENSED_DATE_TIME + "', DISPENSED_DATE='" + DISPENSED_DATE_TIME.split(" ")[0] + "', DISPENSED_TIME='" + DISPENSED_DATE_TIME.split(" ")[1].split(":")[0] + ":" + DISPENSED_DATE_TIME.split(" ")[1].split(":")[1] + "', DISP_NO='" + DISP_NO + "', DISP_QTY='" + DISP_QTY + "', STATUS='Dispatch' , REM_QTY='" + rem_qty + "', DOC_NO = '" + DOC_NO + "',DOC_TYPE='" + DOC_TYPE + "'  where ORDER_ID='" + ORDER_ID + "'");

					int chk = cot.executeAllUpdateQuery(sb12.toString(), connection);

					if (chk > 0)
					{
						//System.out.println("Data update for encounter id " + ENCOUNTER_ID);

						String chkFlag = chkAllEncounterDispatch(PATIENT_ID, connection);

						if (chkFlag.equalsIgnoreCase("Dispatch"))
						{

							StringBuilder sbDataUpdate = new StringBuilder();
							sbDataUpdate.append("update pharma_patient_detail set STATUS='Dispatch-P' where encounter_id='" + ENCOUNTER_ID + "'");
							boolean chkDispatchQtyFlag = chkDispatchQty(ORDER_QTY, DISP_QTY, connection);
							String status = "Dispatch";
							if (!chkDispatchQtyFlag)
							{
								status = "Dispatch-P";
							}
							int chkDataTabUpdate = cot.executeAllUpdateQuery(sbDataUpdate.toString(), connection);
							sbDataUpdate.setLength(0);
							if (chkDataTabUpdate > 0)
							{
								String ChkDispatchMedicine = ChkDispatchMedicineget(ENCOUNTER_ID, connection);
								if (ChkDispatchMedicine.equalsIgnoreCase("all_dispatched"))
								{
									StringBuilder sbDataUpdate1 = new StringBuilder();
									sbDataUpdate1.append("update pharma_patient_detail set STATUS='" + status + "', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
									int chkDataTabUpdateAllDispatched = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
									sbDataUpdate1.setLength(0);
									boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

									if (chkDataTabUpdateAllDispatched > 0 && !sts)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										wherClause.put("encounter_id", ENCOUNTER_ID);
										wherClause.put("action_by", "Auto");
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

										wherClause.put("close_by_id", "NA");
										wherClause.put("close_by_name", "NA");

										wherClause.put("comment", "Auto");
										wherClause.put("status", "Dispatch");

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob1 = new InsertDataTable();
											List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob1 = new InsertDataTable();
												ob1.setColName(entry.getKey());
												ob1.setDataName(entry.getValue().toString());
												insertData1.add(ob1);
											}
											boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
											insertData1.clear();
										}
									}
									if((DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "20:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" ))
											|| (DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:01", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "08:00" ))	)
									{
										sbDataUpdate1.append("update pharma_patient_detail set STATUS='Close', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
										int chkDataTabUpdateAllDispatchedClose = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
										sbDataUpdate1.setLength(0);
										//boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

										if (chkDataTabUpdateAllDispatchedClose > 0 )
										{
											Map<String, Object> wherClause = new HashMap<String, Object>();
											wherClause.put("encounter_id", ENCOUNTER_ID);
											wherClause.put("action_by", "Auto");
											wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
											wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

											wherClause.put("close_by_id", "NA");
											wherClause.put("close_by_name", "NA");

											wherClause.put("comment", "Auto");
											wherClause.put("status", "Auto Close");

											if (wherClause != null && wherClause.size() > 0)
											{
												InsertDataTable ob1 = new InsertDataTable();
												List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
												for (Map.Entry<String, Object> entry : wherClause.entrySet())
												{
													ob1 = new InsertDataTable();
													ob1.setColName(entry.getKey());
													ob1.setDataName(entry.getValue().toString());
													insertData1.add(ob1);
												}
												boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
												insertData1.clear();
											}
									}
									}
								} else
								{
									// boolean str = sendSms(ENCOUNTER_ID,
									// connection, ch);
								}
							}

						} else
						{
							StringBuilder sbDataUpdate = new StringBuilder();
							sbDataUpdate.append("update pharma_patient_detail set STATUS='Dispatch-P' where encounter_id='" + ENCOUNTER_ID + "'");

							boolean chkDispatchQtyFlag = chkDispatchQty(ORDER_QTY, DISP_QTY, connection);
							String status = "Dispatch";
							if (!chkDispatchQtyFlag)
							{
								status = "Dispatch-P";
							}
							int chkDataTabUpdate = cot.executeAllUpdateQuery(sbDataUpdate.toString(), connection);

							sbDataUpdate.setLength(0);
							if (chkDataTabUpdate > 0)
							{
								String ChkDispatchMedicine = ChkDispatchMedicineget(ENCOUNTER_ID, connection);
								if (ChkDispatchMedicine.equalsIgnoreCase("all_dispatched"))
								{
									StringBuilder sbDataUpdate1 = new StringBuilder();
									sbDataUpdate1.append("update pharma_patient_detail set STATUS='" + status + "', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
									int chkDataTabUpdateAllDispatched = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
									sbDataUpdate1.setLength(0);
									boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

									if (chkDataTabUpdateAllDispatched > 0 && !sts)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										wherClause.put("encounter_id", ENCOUNTER_ID);
										wherClause.put("action_by", "Auto");
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

										wherClause.put("close_by_id", "NA");
										wherClause.put("close_by_name", "NA");

										wherClause.put("comment", "Auto");
										wherClause.put("status", "Dispatch");

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob1 = new InsertDataTable();
											List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob1 = new InsertDataTable();
												ob1.setColName(entry.getKey());
												ob1.setDataName(entry.getValue().toString());
												insertData1.add(ob1);
											}
											boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
											insertData1.clear();
										}
									}
									if((DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "20:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" ))
											|| (DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "08:00" ))	)
									{
										sbDataUpdate1.append("update pharma_patient_detail set STATUS='Close', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
										int chkDataTabUpdateAllDispatchedClose = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
										sbDataUpdate1.setLength(0);
										//boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

										if (chkDataTabUpdateAllDispatchedClose > 0 )
										{
											Map<String, Object> wherClause = new HashMap<String, Object>();
											wherClause.put("encounter_id", ENCOUNTER_ID);
											wherClause.put("action_by", "Auto");
											wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
											wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

											wherClause.put("close_by_id", "NA");
											wherClause.put("close_by_name", "NA");

											wherClause.put("comment", "Auto");
											wherClause.put("status", "Auto Close");

											if (wherClause != null && wherClause.size() > 0)
											{
												InsertDataTable ob1 = new InsertDataTable();
												List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
												for (Map.Entry<String, Object> entry : wherClause.entrySet())
												{
													ob1 = new InsertDataTable();
													ob1.setColName(entry.getKey());
													ob1.setDataName(entry.getValue().toString());
													insertData1.add(ob1);
												}
												boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
												insertData1.clear();
											}
									}
									}
								} else
								{
									// boolean str = sendSms(ENCOUNTER_ID,
									// connection, ch);
								}
							}
						}
					}
				}
			*/}
			insertData.clear();

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			// ////System.out.println("Inner Calling Trace");
			log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private boolean chkDispatchUpdateInHistory(String eNCOUNTERID, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT * FROM `pharma_patient_data_history` WHERE STATUS='Dispatch' AND `encounter_id` ='" + eNCOUNTERID + "' ".toString(), connection);
			if (lastIdList != null && lastIdList.size() > 0)
			{
				str = true;
			}
			lastIdList.clear();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return str;
	}

	private String remQtyCalculate(String oRDERQTY, String dISPQTY)
	{
		// TODO Auto-generated method stubt
		String flag = "NA";
		int ordrQty = Integer.parseInt(oRDERQTY);
		int dispQty = Integer.parseInt(dISPQTY);

		flag = String.valueOf(ordrQty - dispQty);
		//System.out.println("flag = " + flag);
		return flag;

	}

	private boolean chkDispatchQty(String oRDERQTY, String dISPQTY, SessionFactory connection)
	{

		// TODO Auto-generated method stub
		boolean flag = true;
		int ordrQty = Integer.parseInt(oRDERQTY);
		int dispQty = Integer.parseInt(dISPQTY);

		if (ordrQty > dispQty)
		{
			flag = false;
		} else
		{
			flag = true;
		}

		return flag;
	}

	private boolean sendSms(String eNCOUNTERID, SessionFactory connection, CommunicationHelper ch)
	{
		// TODO Auto-generated method stub
		boolean flag = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" SELECT med.ORDER_ID, med.TRN_GROUP_REF,med.ORD_DATE_TIME FROM `pharma_medicine_data` AS med INNER JOIN `pharma_patient_detail` AS pat ON pat.encounter_id = med.`TRN_GROUP_REF` WHERE pat.STATUS='Dispatch-P' AND med.status='Pending' AND med.sms_flag='0' ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String strOne = object[1].toString();
				String strTwo = DateUtil.convertDateToIndianFormat(object[2].toString().split(" ")[0]) + " " + object[2].toString().split(" ")[1].split(":")[0] + ":" + object[2].toString().split(" ")[1].split(":")[1];

				//
				if (strOne != null)
				{

					List alertHolder = cbt.executeAllSelectQuery(" select mobOne, empName from employee_basic where id in (select emp_name from pharma_dispatch_alert where status='Active') ".toString(), connection);
					for (Iterator italertHolder = alertHolder.iterator(); italertHolder.hasNext();)
					{
						Object[] objectTwo = (Object[]) italertHolder.next();

						if (objectTwo[0] != null)
						{
							String levelMsg = "Dear " + objectTwo[1].toString() + ", PH order ID " + strOne + ", ordered at " + strTwo + "  is not DISPENSED.";
							boolean isSent = ch.addMessage(objectTwo[1].toString(), "PH", objectTwo[0].toString(), levelMsg, strOne, "Pending", "0", "PH_ORD", connection);
							if (isSent)
							{
								//System.out.println("sms sent to  empMob: " + objectTwo[0].toString() + " orderid: " + strOne);

								StringBuilder sb = new StringBuilder();
								sb.append("update pharma_medicine_data set sms_flag='1' where ORDER_ID='" + strOne + "'");
								int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);

								if (chkUpdate > 0)
								{
									//System.out.println("sms sent to  empMob: " + objectTwo[0].toString() + " orderid: " + strOne + " and update the order in table");
									flag = true;
								}

							}
						}
					}
					alertHolder.clear();

				}
				// //System.out.println("Last Order Normal Time  >>>>>>  " + str);
			}
			lastIdList.clear();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;
	}

	private String chkAllEncounterDispatch(String uhid, SessionFactory connection)
	{
		// TODO Auto-generated method stub

		// select id, status from pharma_medicine_data where
		// PATIENT_ID='MM00858641' and status='Pending' group by status

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String str = "Dispatch";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" select id, status from pharma_medicine_data where PATIENT_ID='" + uhid + "' and status='Pending' group by status ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str = object[1].toString();
				//System.out.println("Last Order Normal Time  >>>>>>  " + str);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	}

	private String chkEncounterData(String enconterID, SessionFactory connection)
	{
		// TODO Auto-generated method stub

		// select id, status from pharma_medicine_data where
		// PATIENT_ID='MM00858641' and status='Pending' group by status

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String str = "not_exist";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" select id, encounter_id from pharma_patient_detail where encounter_id='" + enconterID + "' ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str = object[1].toString();
				//System.out.println("Last Order Normal Time  >>>>>>  " + str);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	}

	private String lastDispatchORDdateTimeget(SessionFactory connection)
	{
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			String dateOne = DateUtil.getCurrentDateIndianFormat();
			lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + "00:01:02";
			//System.out.println("Last Pharma Order date time  >>>>>>  " + lastDataTime);

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;

	}

	private String lastDispatchORDdateTimegetYESTERDAY(SessionFactory connection)
	{
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			String dateOne = DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-1));// DateUtil.getCurrentDateIndianFormat();
			lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + "00:01:02";
			//System.out.println("Last Pharma Order date time  >>>>>>  " + lastDataTime);

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;

	}

	private String lastPharmaOrderdateTimeget(SessionFactory connection)
	{

		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" SELECT id, ADDED_DATE FROM `pharma_medicine_data` ORDER BY `ADDED_DATE` DESC LIMIT 1 ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String lastDataTime1 = object[1].toString();
				String dateOne = DateUtil.convertDateToIndianFormat(lastDataTime1.split(" ")[0]);
				//System.out.println("jhgjhg " + lastDataTime1.split(" ")[1].substring(0, 8));
				lastDataTime = dateOne.split("-")[0] + "/" + dateOne.split("-")[1] + "/" + dateOne.split("-")[2] + " " + lastDataTime1.split(" ")[1].substring(0, 8);
				// +" "+DateUtil.convertDateToIndianFormat(lastDataTime.split(" ")[1]).split(":")[0]+":"+DateUtil.convertDateToIndianFormat(lastDataTime.split(" ")[1]).split(":")[1];
				// //System.out.println(dateOne);
				//System.out.println("Last Pharma Order date time  >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		// lastDataTime = "2016-04-27 01:00:00.1";
		return lastDataTime;

	}

	private String lastPharmaPatientdateTimeget(SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" select id, admission_date_time from pharma_new_admission order by id desc limit 20 ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				lastDataTime = object[1].toString();
				//System.out.println("Last Pharma Patient date time  >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	}

	private boolean ckhExist(String orderID, SessionFactory connection)
	{

		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id from pharma_medicine_data where ORDER_ID='" + orderID + "' ".toString(), connection);
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

	private String ChkDispatchMedicineget(String eNCOUNTERID, SessionFactory connection)
	{
		// TODO Auto-generated method stub

		// select id, STATUS from pharma_medicine_data where
		// ENCOUNTER_ID='13144145' and status='Pending'

		// TODO Auto-generated method stub
		String str = "all_dispatched";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" select id, STATUS from  pharma_medicine_data where ENCOUNTER_ID='" + eNCOUNTERID + "' and  status='Pending' ".toString(), connection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str = object[1].toString();
				//System.out.println("Last Pharma Patient date time  >>>>>>  " + str);
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;

	}

	private boolean ckhExistDis(String orderID, SessionFactory connection)
	{
		// TODO Auto-generated method stub

		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id from pharma_medicine_data where ORDER_ID='" + orderID + "' and status='Dispatch'".toString(), connection);
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
		//System.out.println("str " + str);
		return str;

	}

	public void testDataFieldName(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		//System.out.println("Dreamsol_IP_VW view hit for patient");

		try
		{

			String lastORDDateTime = lastPharmaPatientdateTimeget(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			rs = st.executeQuery("select * from Dreamsol_IP_VW ");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			for (int i = 1; i < columnCount + 1; i++)
			{
				String name = rsmd.getColumnName(i); // Do stuff with name
				//System.out.println(" name " + i + " : " + name);
			}
			// Dreamsol_IP_VW

		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	// track old data status
	public void PharmaOldOrdStatus(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{
		// TODO Auto-generated method stub

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String ordNameChange = null;
		try
		{
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			//System.out.println("day  " + day);
			List lastIdList = new ArrayList();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			lastIdList = cbt.executeAllSelectQuery(" select order_id from pharma_medicine_data where status='Pending'".toString(), connection);
			String pendingOrd = fetchPendingOrd(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			//System.out.println("conn " + con.isClosed());
			st = con.createStatement();

			//System.out.println("last date>>>rrrr " + pendingOrd);

			rs = st.executeQuery("select  * from DREAMSOL_PH_PEND_ORD where ORDER_ID IN (" + pendingOrd + ")");
			//System.out.println("select * from DREAMSOL_PH_PEND_ORD where ORDER_ID IN (" + pendingOrd + ")");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			//System.out.println("name  ");
			List newOrdList = new ArrayList();
			while (rs.next())
			{

				//System.out.println("name 1 ");
				String PATIENT_ID, ENCOUNTER_ID, ORDER_ID, DISPENSED_DATE_TIME, ORDER_QTY, ITEM_NAME, ORD_DATE_TIME, DISP_NO, DISP_QTY, DOC_NO, DOC_TYPE;

				PATIENT_ID = rs.getString("PATIENT_ID").toString();
				ENCOUNTER_ID = rs.getString("TRN_GROUP_REF").toString();
				ORDER_ID = rs.getString("ORDER_ID").toString();
				ORD_DATE_TIME = rs.getString("ORD_DATE_TIME").toString();
				StringBuilder sb12 = new StringBuilder();
				newOrdList.add(ORDER_ID);

			}

			lastIdList.removeAll(newOrdList);
			//System.out.println("orderListSize after remove  " + lastIdList.size());
			StringBuilder sb12 = new StringBuilder();
			for (int i = 0; i < lastIdList.size(); i++)
			{
				String value = (String) lastIdList.get(i);
				//System.out.println("Element: " + value);

				sb12.append(" update  pharma_medicine_data set status ='HIS Cancel'  where ORDER_ID='" + value + "'");

				int chk = cot.executeAllUpdateQuery(sb12.toString(), connection);
				sb12.setLength(0);
				if (chk > 0)
				{
					//System.out.println("His Cancel Mark ");
					List lastIdListEncounterID = cbt.executeAllSelectQuery(" select id, encounter_id from pharma_medicine_data where order_id='" + value + "'".toString(), connection);
					// str = "";
					for (Iterator it = lastIdListEncounterID.iterator(); it.hasNext();)
					{
						Object[] object = (Object[]) it.next();
						// str = str + "'" + object[1].toString() + "' , ";
						String encounter = object[1].toString();

						sb12.append(" update  pharma_patient_detail set status ='HIS Cancel'  where encounter_id='" + encounter + "'");

						int chkDATATable = cot.executeAllUpdateQuery(sb12.toString(), connection);
						boolean sts = chkHISCANCELUpdateInHistory(encounter, connection);
						if (chkDATATable > 0 && !sts)
						{
							Map<String, Object> wherClause = new HashMap<String, Object>();
							wherClause.put("encounter_id", encounter);
							wherClause.put("action_by", "Auto");
							wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

							wherClause.put("close_by_id", "NA");
							wherClause.put("close_by_name", "NA");

							wherClause.put("comment", "Auto");
							wherClause.put("status", "HIS Cancel");

							if (wherClause != null && wherClause.size() > 0)
							{
								InsertDataTable ob1 = new InsertDataTable();
								List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									ob1 = new InsertDataTable();
									ob1.setColName(entry.getKey());
									ob1.setDataName(entry.getValue().toString());
									insertData1.add(ob1);
								}
								boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
								insertData1.clear();
							}
						}

					}

				}
			}

		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	private boolean chkHISCANCELUpdateInHistory(String encounter, SessionFactory connection)
	{

		// TODO Auto-generated method stub
		boolean str = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" SELECT * FROM `pharma_patient_data_history` WHERE STATUS='HIS Cancel' AND `encounter_id` ='" + encounter + "' ".toString(), connection);
			if (lastIdList != null && lastIdList.size() > 0)
			{
				str = true;
			}
			lastIdList.clear();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return str;

	}

	public void CRCDATAPharmaUpdateYESTERDAY(Log log, SessionFactory connection, Session sessionHis, CommunicationHelper ch)
	{
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;

		try
		{
			// //System.out.println("get call hit it is 66 server  for Update dispance get ");
			// String lastORDDateTime = lastORDdateTimeget(clientConnection);

			String lastORDDateTime = lastDispatchORDdateTimegetYESTERDAY(connection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			//System.out.println("lastORDDateTime " + lastORDDateTime);
			rs = st.executeQuery("select * from dreamsol_ph_pend_ord  where  dispensed_date_time >= TO_DATE('" + lastORDDateTime + "','dd/mm/yyyy hh24:mi:ss') order by dispensed_date_time  ");
			// rs =
			// st2.executeQuery("select * from DREAMSOL_PH_PEND_ORD where ADDED_DATE >= TO_DATE('"
			// + lastORDDateTime +
			// "','dd/mm/yyyy hh24:mi:ss') order by ADDED_DATE  ");

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			/*
			 * for (int i = 1; i < columnCount+ 1; i++ ) { String name =
			 * rsmd.getColumnName(i); // Do stuffwith name
			 * //System.out.println(" name "+i+" : "+name ); }
			 */
			while (rs.next())
			{

				String PATIENT_ID, ENCOUNTER_ID, ORDER_ID, DISPENSED_DATE_TIME, ORDER_QTY, ITEM_NAME, ORD_DATE_TIME, DISP_NO, DISP_QTY, DOC_NO, DOC_TYPE;
				// dfg

				PATIENT_ID = rs.getString("PATIENT_ID").toString();
				ENCOUNTER_ID = rs.getString("TRN_GROUP_REF").toString();
				ORDER_ID = rs.getString("ORDER_ID").toString();
				DISPENSED_DATE_TIME = rs.getString("DISPENSED_DATE_TIME").toString();

				// //System.out.println("jkh "+rs.getString("DISPENSED_DATE_TIME").toString());
				// DISPENSED_DATE_TIME =
				// String.valueOf(rs.getString("DISPENSED_DATE_TIME"));
				ORDER_QTY = rs.getString("ORDER_QTY").toString();
				ITEM_NAME = rs.getString("ITEM_NAME").toString();
				ORD_DATE_TIME = rs.getString("ORD_DATE_TIME").toString();
				DISP_NO = rs.getString("DISP_NO").toString();
				DISP_QTY = rs.getString("DISP_QTY").toString();
				DOC_NO = rs.getString("DOC_NO").toString();
				DOC_TYPE = rs.getString("DOC_TYPE").toString();
				StringBuilder sb12 = new StringBuilder();
				String rem_qty = remQtyCalculate(ORDER_QTY, DISP_QTY);
				boolean isExist = ckhExistDis(ORDER_ID, connection);
				if (!isExist)
				{
					sb12.append("update pharma_medicine_data set DISPENSED_DATE_TIME='" + DISPENSED_DATE_TIME + "', DISPENSED_DATE='" + DISPENSED_DATE_TIME.split(" ")[0] + "', DISPENSED_TIME='" + DISPENSED_DATE_TIME.split(" ")[1].split(":")[0] + ":" + DISPENSED_DATE_TIME.split(" ")[1].split(":")[1] + "', DISP_NO='" + DISP_NO + "', DISP_QTY='" + DISP_QTY + "', STATUS='Dispatch' , REM_QTY='" + rem_qty + "', DOC_NO = '" + DOC_NO + "',DOC_TYPE='" + DOC_TYPE + "'  where ORDER_ID='" + ORDER_ID + "'");

					int chk = cot.executeAllUpdateQuery(sb12.toString(), connection);

					// //System.out.println("chk    "+chk);
					if (chk > 0)
					{
						//System.out.println("Data update for encounter id " + ENCOUNTER_ID);

						String chkFlag = chkAllEncounterDispatch(PATIENT_ID, connection);

						if (chkFlag.equalsIgnoreCase("Dispatch"))
						{

							StringBuilder sbDataUpdate = new StringBuilder();
							sbDataUpdate.append("update pharma_patient_detail set STATUS='Dispatch-P' where encounter_id='" + ENCOUNTER_ID + "'");
							boolean chkDispatchQtyFlag = chkDispatchQty(ORDER_QTY, DISP_QTY, connection);
							String status = "Dispatch";
							if (!chkDispatchQtyFlag)
							{
								status = "Dispatch-P";
							}
							int chkDataTabUpdate = cot.executeAllUpdateQuery(sbDataUpdate.toString(), connection);
							sbDataUpdate.setLength(0);
							if (chkDataTabUpdate > 0)
							{
								String ChkDispatchMedicine = ChkDispatchMedicineget(ENCOUNTER_ID, connection);
								if (ChkDispatchMedicine.equalsIgnoreCase("all_dispatched"))
								{
									StringBuilder sbDataUpdate1 = new StringBuilder();
									sbDataUpdate1.append("update pharma_patient_detail set STATUS='" + status + "', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
									int chkDataTabUpdateAllDispatched = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
									sbDataUpdate1.setLength(0);
									boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

									if (chkDataTabUpdateAllDispatched > 0 && !sts)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										wherClause.put("encounter_id", ENCOUNTER_ID);
										wherClause.put("action_by", "Auto");
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

										wherClause.put("close_by_id", "NA");
										wherClause.put("close_by_name", "NA");

										wherClause.put("comment", "Auto");
										wherClause.put("status", "Dispatch");

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob1 = new InsertDataTable();
											List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob1 = new InsertDataTable();
												ob1.setColName(entry.getKey());
												ob1.setDataName(entry.getValue().toString());
												insertData1.add(ob1);
											}
											boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
											insertData1.clear();
										}
									}

								} else
								{
									// boolean str = sendSms(ENCOUNTER_ID,
									// connection, ch);
								}
							}

						} else
						{
							StringBuilder sbDataUpdate = new StringBuilder();
							sbDataUpdate.append("update pharma_patient_detail set STATUS='Dispatch-P' where encounter_id='" + ENCOUNTER_ID + "'");

							boolean chkDispatchQtyFlag = chkDispatchQty(ORDER_QTY, DISP_QTY, connection);
							String status = "Dispatch";
							if (!chkDispatchQtyFlag)
							{
								status = "Dispatch-P";
							}
							int chkDataTabUpdate = cot.executeAllUpdateQuery(sbDataUpdate.toString(), connection);

							sbDataUpdate.setLength(0);
							if (chkDataTabUpdate > 0)
							{
								String ChkDispatchMedicine = ChkDispatchMedicineget(ENCOUNTER_ID, connection);
								if (ChkDispatchMedicine.equalsIgnoreCase("all_dispatched"))
								{
									StringBuilder sbDataUpdate1 = new StringBuilder();
									sbDataUpdate1.append("update pharma_patient_detail set STATUS='" + status + "', document_no='" + DOC_NO + "' where encounter_id='" + ENCOUNTER_ID + "'");
									int chkDataTabUpdateAllDispatched = cot.executeAllUpdateQuery(sbDataUpdate1.toString(), connection);
									sbDataUpdate1.setLength(0);
									boolean sts = chkDispatchUpdateInHistory(ENCOUNTER_ID, connection);

									if (chkDataTabUpdateAllDispatched > 0 && !sts)
									{
										Map<String, Object> wherClause = new HashMap<String, Object>();
										wherClause.put("encounter_id", ENCOUNTER_ID);
										wherClause.put("action_by", "Auto");
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTimeHourMin());

										wherClause.put("close_by_id", "NA");
										wherClause.put("close_by_name", "NA");

										wherClause.put("comment", "Auto");
										wherClause.put("status", "Dispatch");

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob1 = new InsertDataTable();
											List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob1 = new InsertDataTable();
												ob1.setColName(entry.getKey());
												ob1.setDataName(entry.getValue().toString());
												insertData1.add(ob1);
											}
											boolean updateFeed = cot.insertIntoTable("pharma_patient_data_history", insertData1, connection);
											insertData1.clear();
										}
									}

								} else
								{
									// boolean str = sendSms(ENCOUNTER_ID,
									// connection, ch);
								}
							}
						}
					}
				}
			}
			insertData.clear();

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			// ////System.out.println("Inner Calling Trace");
			log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private String fetchPendingOrd(SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select id, order_id from pharma_medicine_data where status='Pending'".toString(), connection);
			str = "";
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				str = str + "'" + object[1].toString() + "' , ";
			}
			str = str + "' ' ";
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return str;
	}

}
