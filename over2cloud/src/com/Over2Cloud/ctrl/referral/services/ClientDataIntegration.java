package com.Over2Cloud.ctrl.referral.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.Over2Cloud.CommonClasses.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClientDataIntegration
{

	private JSONArray commonJSONArray;
	private String uhid;
	private String dataFor;
	private String patType;

	// fetch the patient info from his

	public String fetchDetails()
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		System.out.println("day  "+day);
		try
		{
			if((DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
					(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
					(day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" )))
			{
				
				
				System.out.println("<<<<<<<         IT IS 66 SERVER redirect        >>>>>>>>>");
				
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				if ("patient".equalsIgnoreCase(dataFor))
				{
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					if(patType!=null && !patType.equalsIgnoreCase("undefined") && patType.equalsIgnoreCase("Emergency"))
					{
						//System.out.println("uhid "+uhid);
						rs = st.executeQuery("select * from Dreamsol_em_pt_vw where UHID = '" + uhid + "'");
						//ResultSetMetaData rsmd = rs.getMetaData();
						//int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						/*for (int i = 1; i < columnCount + 1; i++ ) {
						  String name = rsmd.getColumnName(i);
						  // Do stuff with name
						  //System.out.println(" name "+i+" : "+name );
						}*/
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", "Emergency");
						}
						
						commonJSONArray.add(listObject);
						
					}
					else if(patType!=null && !patType.equalsIgnoreCase("undefined") && patType.equalsIgnoreCase("IPD"))
					{
						rs = st.executeQuery("SELECT PATIENT_NAME,ASSIGN_BED_NUM,LONG_DESC,ATTENDING_PRACTITIONER_NAME,SPECIALITY FROM Dreamsol_IP_VW WHERE UHIID='" + uhid + "' ");
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", rs.getString("ASSIGN_BED_NUM"));
							listObject.put("nursingUnit", rs.getString("LONG_DESC"));
							listObject.put("admDoc", rs.getString("ATTENDING_PRACTITIONER_NAME"));
							listObject.put("admSpec", rs.getString("SPECIALITY"));
						}
						commonJSONArray.add(listObject);
					}
				} else if ("emp".equalsIgnoreCase(dataFor))
				{////System.out.println("for employee "+uhid);
					//System.out.println("uhid before"+uhid);
					int hisUHIDLength = 8;
					int uhidLength = uhid.length();
					int templength = 0;
					if(uhidLength<hisUHIDLength){
						
						templength = hisUHIDLength - uhidLength ;
							for (int j = 0; j < templength; j++) {
								uhid = "0"+uhid;
							}
					}
					//System.out.println("uhid after"+uhid);
					
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.34:1521:KRP", "spectra", "spectra");
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM EMPLOYEEINFORMATION_VW WHERE PERSONNUM = '" + uhid + "' ");
					commonJSONArray = new JSONArray();
					JSONObject listObject = new JSONObject();
					while (rs.next())
					{
					//	//System.out.println("FName: "+rs.getString("FIRSTNAME")+" LName: "+rs.getString("LASTNAME")+" Person: "+rs.getString("PERSONNUM")+" Title: "+rs.getString("Title"));
						listObject.put("fName", rs.getString("FIRSTNAME"));
						listObject.put("lName", rs.getString("LASTNAME"));
					}
					commonJSONArray.add(listObject);}
				returnResult = "success";
				
				
				
				
			}
			
			else{

				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				if ("patient".equalsIgnoreCase(dataFor))
				{
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					if(patType!=null && !patType.equalsIgnoreCase("undefined") && patType.equalsIgnoreCase("Emergency"))
					{
						//System.out.println("uhid "+uhid);
						rs = st.executeQuery("select * from Dreamsol_em_pt_vw where UHID = '" + uhid + "'");
						//ResultSetMetaData rsmd = rs.getMetaData();
						//int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						/*for (int i = 1; i < columnCount + 1; i++ ) {
						  String name = rsmd.getColumnName(i);
						  // Do stuff with name
						  //System.out.println(" name "+i+" : "+name );
						}*/
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", "Emergency");
						}
						
						commonJSONArray.add(listObject);
						
					}
					else if(patType!=null && !patType.equalsIgnoreCase("undefined") && patType.equalsIgnoreCase("IPD"))
					{
						rs = st.executeQuery("SELECT PATIENT_NAME,ASSIGN_BED_NUM,LONG_DESC,ATTENDING_PRACTITIONER_NAME,SPECIALITY FROM Dreamsol_IP_VW WHERE UHIID='" + uhid + "' ");
						commonJSONArray = new JSONArray();
						JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							listObject.put("pName", rs.getString("PATIENT_NAME"));
							listObject.put("bedNo", rs.getString("ASSIGN_BED_NUM"));
							listObject.put("nursingUnit", rs.getString("LONG_DESC"));
							listObject.put("admDoc", rs.getString("ATTENDING_PRACTITIONER_NAME"));
							listObject.put("admSpec", rs.getString("SPECIALITY"));
						}
						commonJSONArray.add(listObject);
					}
				} else if ("emp".equalsIgnoreCase(dataFor))
				{////System.out.println("for employee "+uhid);
					//System.out.println("uhid before"+uhid);
					int hisUHIDLength = 8;
					int uhidLength = uhid.length();
					int templength = 0;
					if(uhidLength<hisUHIDLength){
						
						templength = hisUHIDLength - uhidLength ;
							for (int j = 0; j < templength; j++) {
								uhid = "0"+uhid;
							}
					}
					//System.out.println("uhid after"+uhid);
					
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.34:1521:KRP", "spectra", "spectra");
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM EMPLOYEEINFORMATION_VW WHERE PERSONNUM = '" + uhid + "' ");
					commonJSONArray = new JSONArray();
					JSONObject listObject = new JSONObject();
					while (rs.next())
					{
					//	//System.out.println("FName: "+rs.getString("FIRSTNAME")+" LName: "+rs.getString("LASTNAME")+" Person: "+rs.getString("PERSONNUM")+" Title: "+rs.getString("Title"));
						listObject.put("fName", rs.getString("FIRSTNAME"));
						listObject.put("lName", rs.getString("LASTNAME"));
					}
					commonJSONArray.add(listObject);}
				returnResult = "success";
				
				
				
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
		return returnResult;
	}

	public ResultSet fetchDoctorList()
	{

		try
		{
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			System.out.println("day  "+day);
			
			if((DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
					(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:50", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
					(day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" ))){
			
				
				System.out.println("<<<<<<<         IT IS 66 SERVER         >>>>>>>>>");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM dreamsol_dr_vw where ADDED_DATE = TO_TIMESTAMP('"+DateUtil.getDateAfterDays(-1)+" 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')");
				//System.out.println("date brfore one day "+DateUtil.getDateAfterDays(-1));
				//where ADDED_DATE > TO_TIMESTAMP('2015-09-23 23:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')
				/*ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				// The column count starts from 1
				for (int i = 1; i < columnCount + 1; i++ ) {
				  String name = rsmd.getColumnName(i);
				  // Do stuff with name
				  //System.out.println(" name "+i+" : "+name );
				}*/
				return rs;
				
				
			}
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM dreamsol_dr_vw where ADDED_DATE = TO_TIMESTAMP('"+DateUtil.getDateAfterDays(-1)+" 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')");
			//System.out.println("date brfore one day "+DateUtil.getDateAfterDays(-1));
			//where ADDED_DATE > TO_TIMESTAMP('2015-09-23 23:00:00.0','YYYY-MM-DD HH24:MI:SS.FF')
			/*ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++ ) {
			  String name = rsmd.getColumnName(i);
			  // Do stuff with name
			  //System.out.println(" name "+i+" : "+name );
			}*/
			return rs;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getPatType()
	{
		return patType;
	}

	public void setPatType(String patType)
	{
		this.patType = patType;
	}

}