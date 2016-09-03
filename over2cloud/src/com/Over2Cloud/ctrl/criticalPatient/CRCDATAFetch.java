package com.Over2Cloud.ctrl.criticalPatient;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;

public class CRCDATAFetch {

	public void CRCDATAFetch(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {
		// TODO Auto-generated method stub
		
		System.out.println("hit by manab 1");

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;
System.out.println("This is new file 82 server>>>>>>>>>>>>>>>>>>>>>>>> Dreamsol_IP_CR_TST_VW");
//CommunicationHelper ch = new CommunicationHelper();
		try
		{
			
			//String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			//System.out.println("test con " + con.isClosed());

			/*
			 * rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW ");
			 * ResultSetMetaData rsmd = rs.getMetaData(); int columnCount =
			 * rsmd.getColumnCount();
			 * 
			 * // The column count starts from 1 for (int i = 1; i < columnCount
			 * + 1; i++ ) { String name = rsmd.getColumnName(i); // Do stuff
			 * with name System.out.println(" name "+i+" : "+name ); }
			 */

			//System.out.println("lastORDDateTime>> " + lastORDDateTime);
			//15-12-2015 by aarif 
			rs = st.executeQuery("select * from dream_sol_helth_checkup_bg ");
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>dream_sol_helth_checkup_bg>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			
			 for (int i = 1; i < columnCount + 1; i++ ) { String name =
			 rsmd.getColumnName(i); // Do stuff with name
			System.out.println(" name "+i+" : "+name ); }
			 

			while (rs.next())
			{}
			

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Inner Calling Trace");
			//log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
			//ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 66 server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
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

	public void CRCDATAFetchONE(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {

		// TODO Auto-generated method stub
		
		System.out.println("hit by manab 1");

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;
System.out.println("This is new file 82 server>>>>>>>>>>>>>>>>>>>>>>>> Dreamsol_IP_CR_TST_VW");
//CommunicationHelper ch = new CommunicationHelper();
		try
		{
			
			//String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			//System.out.println("test con " + con.isClosed());

			/*
			 * rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW ");
			 * ResultSetMetaData rsmd = rs.getMetaData(); int columnCount =
			 * rsmd.getColumnCount();
			 * 
			 * // The column count starts from 1 for (int i = 1; i < columnCount
			 * + 1; i++ ) { String name = rsmd.getColumnName(i); // Do stuff
			 * with name System.out.println(" name "+i+" : "+name ); }
			 */

			//System.out.println("lastORDDateTime>> " + lastORDDateTime);
			//15-12-2015 by aarif 
			rs = st.executeQuery("select * from dream_sol_helth_checkup_bg ");
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>dream_sol_helth_checkup_bg>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			
			 for (int i = 1; i < columnCount + 1; i++ ) { String name =
			 rsmd.getColumnName(i); // Do stuff with name
			System.out.println(" name "+i+" : "+name ); }
			 

			while (rs.next())
			{}
			

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Inner Calling Trace");
			//log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
			//ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 66 server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
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

	public void CRCDATAFetchTWO(Log log, SessionFactory connection,
			Session sessionHis, CommunicationHelper ch) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		System.out.println("hit by manab 2");

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;
System.out.println("This is new file 82 server >>  Dreamsol_EM_CR_TST_VW");
//CommunicationHelper ch = new CommunicationHelper();
		try
		{
			
			//String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			
			rs = st.executeQuery("select * from Dreamsol_EM_CR_TST_VW where ROWNUM <= 5");
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Dreamsol_EM_CR_TST_VW>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			
			 for (int i = 1; i < columnCount + 1; i++ ) { String name =
			 rsmd.getColumnName(i); // Do stuff with name
			System.out.println(" name "+i+" : "+name ); }
			 

			while (rs.next())
			{
				 
				
					System.out.println("UHID"+rs.getString("UHID"));
			 System.out.println("Patient Name"+rs.getString("Patient Name"));
			 System.out.println("CONTACT1_NO"+rs.getString("CONTACT1_NO"));
			 System.out.println("CONTACT2_NO"+rs.getString("CONTACT2_NO"));
			 System.out.println("ASSIGN_BED_NUM"+rs.getString("ASSIGN_BED_NUM"));
			 System.out.println("ADMITING_DOCTOR"+rs.getString("ADMITING_DOCTOR"));
			 System.out.println("ORDERING_DOCTOR"+rs.getString("ORDERING_DOCTOR"));
			 System.out.println("ORDERING_DOCTOR_SPECIALTY"+rs.getString("ORDERING_DOCTOR_SPECIALTY"));
			 System.out.println("SPECIMEN_NO"+rs.getString("SPECIMEN_NO"));
			 System.out.println("ORDER_CATALOG_CODE"+rs.getString("ORDER_CATALOG_CODE"));
			 System.out.println("SERVICE_NAME"+rs.getString("SERVICE_NAME"));
			 System.out.println("Low Range"+rs.getString("Low Range"));
			 System.out.println("ENCOUNTER_ID"+rs.getString("ENCOUNTER_ID"));
			 System.out.println("High Range"+rs.getString("High Range"));
			 System.out.println("Result"+rs.getString("Result"));
			 System.out.println("TEST_UNITS"+rs.getString("TEST_UNITS"));
			 System.out.println("RELEASED_DATE"+rs.getString("RELEASED_DATE"));
			 System.out.println("LabName"+rs.getString("LabName"));
			 System.out.println("Order_Date"+rs.getString("Order_Date"));
				
				
			}
			

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Inner Calling Trace");
			//log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
			//ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 66 server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
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

}