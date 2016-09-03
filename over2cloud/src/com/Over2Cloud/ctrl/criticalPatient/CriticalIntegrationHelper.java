package com.Over2Cloud.ctrl.criticalPatient;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

public class CriticalIntegrationHelper {

	public void FetchRecordsFromHIS(SessionFactory connection) {/*
		
	
			String returnResult = "error";
			java.sql.Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
						rs = st.executeQuery("select * from Dreamsol_em_pt_vw where date");
						// ResultSetMetaData rsmd = rs.getMetaData();
						// int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						
						 * for (int i = 1; i < columnCount + 1; i++ ) { String name
						 * = rsmd.getColumnName(i); // Do stuff with name
						 * System.out.println(" name "+i+" : "+name ); }
						 
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", "Emergency");
						}

						commonJSONArray.add(listObject);

					
						rs = st.executeQuery("SELECT PATIENT_NAME,ASSIGN_BED_NUM,LONG_DESC,PRACTITIONER_NAME,SPECIALITY FROM Dreamsol_IP_VW WHERE UHIID='" + uhid + "' ");
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", rs.getString("ASSIGN_BED_NUM"));
							listObject.put("nursingUnit", rs.getString("LONG_DESC"));
							listObject.put("admDoc", rs.getString("PRACTITIONER_NAME"));
							listObject.put("admSpec", rs.getString("SPECIALITY"));
						}
						commonJSONArray.add(listObject);
					
			
				returnResult = "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					con.close();
					st.close();
					rs.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			 * } else { returnResult = "login"; }
			 
			return returnResult;
	*/}
	

}
