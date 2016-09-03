package com.Over2Cloud.ctrl.criticalPatient;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class TestHISTableHelper {

	public void tableGet(Log log, SessionFactory connection, Session sessionHis) {
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		
		 
		 
		 

			// TODO Auto-generated method stub
			

			// TODO Auto-generated method stub
			////System.out.println("get the call");

			java.sql.Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String pri = "Routine";
			String ordNameChange = null;

			try
			{
				System.out.println("get call hit");
				//String lastORDDateTime = lastORDdateTimeget(clientConnection);
				CommonOperInterface cot = new CommonConFactory().createInterface();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
				st = con.createStatement();
				////System.out.println("test con " + con.isClosed());

				////System.out.println(" <<<<<<<<<<<<<<<<<Appointment details >>>>>>>>>>>>>");
				 rs = st.executeQuery("select * from Dreamsol_IP_CR_TST_VW  ");
				
				
				 
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				//The column count starts from 1 
				 for (int i = 1; i < columnCount+ 1; i++ ) 
				 { String name = rsmd.getColumnName(i); // Do stuffwith name 
				 	System.out.println(" name "+i+" : "+name ); 
				 }
	}catch (Exception e)
	{
		// TODO: handle exception
		e.printStackTrace();
		////System.out.println("Inner Calling Trace");
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

}
