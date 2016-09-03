package com.Over2Cloud.ctrl.patientcare;

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

public class CPSHISIntregrationHelper
{

	public void getCustomerData(Log log, SessionFactory clientConnection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{
		// TODO Auto-generated method stub
		////System.out.println("get the call");

		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;

		try
		{

			//String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			////System.out.println("test con " + con.isClosed());

			////System.out.println(" <<<<<<<<<<<<<<<<<UNDER REDIRECT IT IS HISDR 66 Server >>>>>>>>>>>>>");
			
			 rs = st.executeQuery("select * from dream_sol_helth_checkup_bg ");
			 /*ResultSetMetaData rsmd = rs.getMetaData(); 
			 int columnCount = rsmd.getColumnCount();*/
			 
			 //
			 //The column count starts from 1 
			 /*for (int i = 1; i < columnCount+ 1; i++ ) 
			 { String name = rsmd.getColumnName(i); // Do stuffwith name 
			 ////System.out.println(" name "+i+" : "+name ); 
			 }*/
			 
			 

			//////System.out.println("lastORDDateTime>> " + lastORDDateTime);
			//rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW where ORD_DATE_TIME > TO_TIMESTAMP('" + lastORDDateTime + "','YYYY-MM-DD HH24:MI:SS.FF') AND ORDER_TYPE_CODE='MXR'  ");
			//rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW ");
			
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();

		// The column count starts from 1
			/*
			 * for (int i = 1; i < columnCount + 1; i++ ) { String name =
			 * rsmd.getColumnName(i); // Do stuff with name
			 * ////System.out.println(" name "+i+" : "+name ); }
			 */

			
			//int dataId=  cot.insertDataReturnId("cps_customer_his",insertData,clientConnection);
			
			//////System.out.println("status"+dataId);
			
			while (rs.next())
			{
				


				
				// for customer group code
				ob = new InsertDataTable();
				ob.setColName("CUST_CODE");
				if (rs.getString("CUST_CODE_CTO")!=null && !rs.getString("CUST_CODE_CTO").toString().equals("")) {
				ob.setDataName(rs.getString("CUST_CODE_CTO").toString());
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				// for customer type name
				ob = new InsertDataTable();
				ob.setColName("BLNG_GRP_ID");
				if (rs.getString("BLNG_GRP_ID_CTO")!=null && !rs.getString("BLNG_GRP_ID_CTO").toString().equals("")) {
				ob.setDataName(rs.getString("BLNG_GRP_ID_CTO").toString());
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				// for customer his valid from 
				ob = new InsertDataTable();
				ob.setColName("CUSTOMER_NAME");
				if (rs.getString("BLNG_GRP_DESC_CTO")!=null && !rs.getString("BLNG_GRP_DESC_CTO").toString().equals("")) {
					String temp = "NA";
					if(rs.getString("BLNG_GRP_DESC_CTO").toString().contains("&amp;")){
						temp = rs.getString("BLNG_GRP_DESC_CTO");
						temp = temp.split("&amp;")[0].toString()+" and "+temp.split("&amp;")[1].toString();
						ob.setDataName(temp.trim());
					}
					else {
					
							ob.setDataName(rs.getString("BLNG_GRP_DESC_CTO"));
						
					}
					
				
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ACCOUNT_MANAGER_NAME");
				if (rs.getString("ACCOUNT_MANAGER")!=null && !rs.getString("ACCOUNT_MANAGER").toString().equals("")) {
				ob.setDataName(rs.getString("ACCOUNT_MANAGER"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				// for HEALTH_PKG_CODE
				ob = new InsertDataTable();
				ob.setColName("HEALTH_PKG_CODE");
				if (rs.getString("HEALTH_PKG_CODE")!=null && !rs.getString("HEALTH_PKG_CODE").toString().equals("")) {
				ob.setDataName(rs.getString("HEALTH_PKG_CODE"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				// for  HEALTH_PKG_NAME
				ob = new InsertDataTable();
				ob.setColName("HEALTH_PKG_NAME");
				if (rs.getString("HEALTH_PKG_NAME")!=null && !rs.getString("HEALTH_PKG_NAME").toString().equals("")) {
				
				
				
				
					String temp1 = "NA";
					if(rs.getString("HEALTH_PKG_NAME").toString().contains("&amp;")){
						temp1 = rs.getString("HEALTH_PKG_NAME");
						temp1 = temp1.split("&amp;")[0].toString()+" and "+temp1.split("&amp;")[1].toString();
						ob.setDataName(temp1.trim());
					}
					
					else if(rs.getString("HEALTH_PKG_NAME").toString().contains("&lt;")){
						temp1 = rs.getString("HEALTH_PKG_NAME");
						temp1 = temp1.split("&lt;")[0].toString()+" < "+temp1.split("&lt;")[1].toString();
						ob.setDataName(temp1.trim());
					}
					
					else if(rs.getString("HEALTH_PKG_NAME").toString().contains("&gt;")){
						temp1 = rs.getString("HEALTH_PKG_NAME");
						temp1 = temp1.split("&gt;")[0].toString()+" > "+temp1.split("&gt;")[1].toString();
						ob.setDataName(temp1.trim());
					}
					else{
						ob.setDataName(rs.getString("HEALTH_PKG_NAME"));
					}
				
				
				
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				
				
				// for HEALTH_PKG_COST
				ob = new InsertDataTable();
				ob.setColName("HELATH_PKG_COST");
				if (rs.getString("HELATH_PKG_COST")!=null && !rs.getString("HELATH_PKG_COST").toString().equals("")) {
				ob.setDataName(rs.getString("HELATH_PKG_COST"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				ob = new InsertDataTable();
				ob.setColName("ACTIVE_YN");
				if (rs.getString("ACTIVE_HPYN")!=null && !rs.getString("ACTIVE_HPYN").toString().equals("")) {
				ob.setDataName(rs.getString("ACTIVE_HPYN"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ACCOUNT_OFFICER");
				if (rs.getString("ACCOUNT_OFFICER")!=null && !rs.getString("ACCOUNT_OFFICER").toString().equals("")) {
				ob.setDataName(rs.getString("ACCOUNT_OFFICER"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				//  select id from cps_customer_his where customer_code='GCGO0006'
				
				int id=cot.insertDataReturnId("Dreamsol_bl_corp_hc_pkg1", insertData, clientConnection);
				if(id>0)
				{
				System.out.println("Data Inserted.");
				}
				/*boolean isExist = ckhExist("id", "cps_customer_his", "customer_code", rs.getString("CUST_CODE").toString(), connection );
				if(isExist){
			
				int id=cot.insertDataReturnId("cps_customer_his", insertData, connection);
				if(id>0)
				{
					////System.out.println("Data Inserted.");
				}
				}*/
				insertData.clear();
			
				
			
			}


		} catch (Exception e)
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

	
	
	private boolean ckhExist(String column, String tableName, String where,
			String data, SessionFactory clientConnection) {
		// TODO Auto-generated method stub
		//  select id from cps_customer_his where customer_code='GCGO0006'
		boolean flag = false;
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery(" select "+column+" from "+tableName+" where "+where+"='"+data+"' ".toString(), clientConnection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);
			if (lastIdList != null && !lastIdList.isEmpty())
			{
			
			}
			else {
				flag = true;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return flag;
	}



	public void getPackageData(Log log, SessionFactory clientConnection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{
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

			//String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
			st = con.createStatement();
			////System.out.println("test con " + con.isClosed());

			////System.out.println(" <<<<<<<<<<<<<<<<<Helth chk up data fetch >>>>>>>>>>>>>");
			 rs = st.executeQuery("select * from dreamsol_hlth_chck_mst_view ");
			
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{
				
				

				
				/*////System.out.println("SERVICE_GROUP : "+rs.getString("SERVICE_GROUP"));
				////System.out.println("SERVICE_GROUP_CLASSIFICATION : "+rs.getString("SERVICE_GROUP_CLASSIFICATION"));
				////System.out.println("BLNG_SERV_CODE : "+rs.getString("BLNG_SERV_CODE"));
				////System.out.println("ORDER_CATALOG_CODE : "+rs.getString("ORDER_CATALOG_CODE"));
				////System.out.println("LONG_DESC : "+rs.getString("LONG_DESC"));
				////System.out.println("OP_RATE : "+rs.getString("OP_RATE"));*/
				////System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				
				// for SERVICE_GROUP
				ob = new InsertDataTable();
				ob.setColName("service_group");
				if (rs.getString("SERVICE_GROUP")!=null && !rs.getString("SERVICE_GROUP").toString().equals("")) {
				ob.setDataName(rs.getString("SERVICE_GROUP"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				// for SERVICE_GROUP_CLASSIFICATION
				ob = new InsertDataTable();
				ob.setColName("service_group_classification");
				if (rs.getString("SERVICE_GROUP_CLASSIFICATION")!=null && !rs.getString("SERVICE_GROUP_CLASSIFICATION").toString().equals("")) {
				ob.setDataName(rs.getString("SERVICE_GROUP_CLASSIFICATION"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				// for billing_service_code
				ob = new InsertDataTable();
				ob.setColName("billing_service_code");
				if (rs.getString("BLNG_SERV_CODE")!=null && !rs.getString("BLNG_SERV_CODE").toString().equals("")) {
				ob.setDataName(rs.getString("BLNG_SERV_CODE"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				// for health_chkup_code
				ob = new InsertDataTable();
				ob.setColName("health_chkup_code");
				if (rs.getString("ORDER_CATALOG_CODE")!=null && !rs.getString("ORDER_CATALOG_CODE").toString().equals("")) {
				ob.setDataName(rs.getString("ORDER_CATALOG_CODE"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				
				// for health_chkup_name
				ob = new InsertDataTable();
				ob.setColName("health_chkup_name");
				if (rs.getString("LONG_DESC")!=null && !rs.getString("LONG_DESC").toString().equals("")) {
				ob.setDataName(rs.getString("LONG_DESC"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
				
				
				// for health_chkup_cost
				ob = new InsertDataTable();
				ob.setColName("health_chkup_cost");
				if (rs.getString("OP_RATE")!=null && !rs.getString("OP_RATE").toString().equals("")) {
				ob.setDataName(rs.getString("OP_RATE"));
				}
				else {
				ob.setDataName("NA");
				}
				insertData.add(ob);
			
				boolean isExist = ckhExist("id", "cps_healthchkup_his", "health_chkup_name", rs.getString("LONG_DESC").toString(), clientConnection );
				if(isExist){
				int id=cot.insertDataReturnId("cps_healthchkup_his", insertData, clientConnection);
				if(id>0)
				{
					////System.out.println("Data Inserted.");
				}
				}
				insertData.clear();
			}


		} catch (Exception e)
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

	public void getBillingGroupData(Log log, SessionFactory clientConnection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{
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

				//String lastORDDateTime = lastORDdateTimeget(clientConnection);
				CommonOperInterface cot = new CommonConFactory().createInterface();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
				st = con.createStatement();
				////System.out.println("test con " + con.isClosed());

				////System.out.println(" <<<<<<<<<<<<<<<<<cps_billinggroup_his data fetch >>>>>>>>>>>>>");
				 rs = st.executeQuery("select * from dream_sol_blng_group_detail  ");
				
				
				 
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				//The column count starts from 1 
				 for (int i = 1; i < columnCount+ 1; i++ ) 
				 { String name = rsmd.getColumnName(i); // Do stuffwith name 
				 System.out.println(" name "+i+" : "+name ); 
				 }
				while (rs.next())
				{
					
					////System.out.println("BLNG_GRP_CATG : "+rs.getString("BLNG_GRP_CATG"));
					////System.out.println("BLNG_GRP_CATG_DESC : "+rs.getString("BLNG_GRP_CATG_DESC"));
					////System.out.println("Patient Type : "+rs.getString("Patient Type"));
					////System.out.println("BLNG_GRP_ID : "+rs.getString("BLNG_GRP_ID"));
					////System.out.println("BLNG_GRP_ID_DESC : "+rs.getString("BLNG_GRP_ID_DESC"));
					////System.out.println("SETTLEMENT_IND : "+rs.getString("SETTLEMENT_IND"));
					
					////System.out.println("NEW_PRICE_CLASS_CODE : "+rs.getString("NEW_PRICE_CLASS_CODE"));
					////System.out.println("NEW_PRICE_CLASS_DESC : "+rs.getString("NEW_PRICE_CLASS_DESC"));
					////System.out.println("STATUS : "+rs.getString("STATUS"));
					////System.out.println("ADDED_DATE : "+rs.getString("ADDED_DATE"));
					////System.out.println("ADDED_BY_ID : "+rs.getString("ADDED_BY_ID"));
					////System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					
					// for BLNG_GRP_CATG
					ob = new InsertDataTable();
					ob.setColName("BLNG_GRP_CATG");
					if (rs.getString("BLNG_GRP_CATG")!=null && !rs.getString("BLNG_GRP_CATG").toString().equals("")) {
					ob.setDataName(rs.getString("BLNG_GRP_CATG"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for billing_grp_name
					ob = new InsertDataTable();
					ob.setColName("billing_grp_name");
					if (rs.getString("BLNG_GRP_CATG_DESC")!=null && !rs.getString("BLNG_GRP_CATG_DESC").toString().equals("")) {
					ob.setDataName(rs.getString("BLNG_GRP_CATG_DESC"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for billing_service_code
					ob = new InsertDataTable();
					ob.setColName("patient_type");
					if (rs.getString("Patient Type")!=null && !rs.getString("Patient Type").toString().equals("")) {
					ob.setDataName(rs.getString("Patient Type"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for health_chkup_code
					ob = new InsertDataTable();
					ob.setColName("BLNG_GRP_ID");
					if (rs.getString("BLNG_GRP_ID")!=null && !rs.getString("BLNG_GRP_ID").toString().equals("")) {
					ob.setDataName(rs.getString("BLNG_GRP_ID"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					// for health_chkup_name
					ob = new InsertDataTable();
					ob.setColName("billing_grp_code");
					if (rs.getString("NEW_PRICE_CLASS_CODE")!=null && !rs.getString("NEW_PRICE_CLASS_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("NEW_PRICE_CLASS_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for health_chkup_cost
					ob = new InsertDataTable();
					ob.setColName("SETTLEMENT_IND");
					if (rs.getString("SETTLEMENT_IND")!=null && !rs.getString("SETTLEMENT_IND").toString().equals("")) {
					ob.setDataName(rs.getString("SETTLEMENT_IND"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					
					
					// for NEW_PRICE_CLASS_CODE
					ob = new InsertDataTable();
					ob.setColName("NEW_PRICE_CLASS_CODE");
					if (rs.getString("NEW_PRICE_CLASS_CODE")!=null && !rs.getString("NEW_PRICE_CLASS_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("NEW_PRICE_CLASS_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for NEW_PRICE_CLASS_DESC
					ob = new InsertDataTable();
					ob.setColName("NEW_PRICE_CLASS_DESC");
					if (rs.getString("NEW_PRICE_CLASS_DESC")!=null && !rs.getString("NEW_PRICE_CLASS_DESC").toString().equals("")) {
					ob.setDataName(rs.getString("NEW_PRICE_CLASS_DESC"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for STATUS
					ob = new InsertDataTable();
					ob.setColName("STATUS");
					if (rs.getString("STATUS")!=null && !rs.getString("STATUS").toString().equals("")) {
					ob.setDataName(rs.getString("STATUS"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for ADDED_DATE
					ob = new InsertDataTable();
					ob.setColName("ADDED_DATE");
					if (rs.getString("ADDED_DATE")!=null && !rs.getString("ADDED_DATE").toString().equals("")) {
					ob.setDataName(rs.getString("ADDED_DATE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for ADDED_BY_ID
					ob = new InsertDataTable();
					ob.setColName("ACCOUNT_MANAGER_NAME");
					if (rs.getString("ACCOUNT_MANAGER_NAME")!=null && !rs.getString("ACCOUNT_MANAGER_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("ACCOUNT_MANAGER_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for Team_member_name TEAM_MEMBER_NAME
					ob = new InsertDataTable();
					ob.setColName("TEAM_MEMBER_NAME");
					if (rs.getString("TEAM_MEMBER_NAME")!=null && !rs.getString("TEAM_MEMBER_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("TEAM_MEMBER_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for MANAGER_CONTACT_NO
					ob = new InsertDataTable();
					ob.setColName("MANAGER_CONTACT_NO");
					if (rs.getString("MANAGER_CONTACT_NO")!=null && !rs.getString("MANAGER_CONTACT_NO").toString().equals("")) {
					ob.setDataName(rs.getString("MANAGER_CONTACT_NO"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					boolean isExist = true;//ckhExist("id", "cps_billinggroup_his", "billing_grp_name", rs.getString("BLNG_GRP_CATG_DESC").toString(), clientConnection );
					if(isExist){
					
				
					int id=cot.insertDataReturnId("cps_billinggroup_his", insertData, clientConnection);
					if(id>0)
					{
						////System.out.println("Data Inserted.");
					}
					}
					insertData.clear();
				}


			} catch (Exception e)
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

	public void getAppointment(Log log, SessionFactory clientConnection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{
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
				 rs = st.executeQuery("select * from Dreamsol_dr_cl_vw  ");
				
				
				 
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				//The column count starts from 1 
				 for (int i = 1; i < columnCount+ 1; i++ ) 
				 { String name = rsmd.getColumnName(i); // Do stuffwith name 
				 	System.out.println(" name "+i+" : "+name ); 
				 }
				while (rs.next())
				{
					

					
					////System.out.println("BLNG_GRP_CATG : "+rs.getString("BLNG_GRP_CATG"));
					////System.out.println("BLNG_GRP_CATG_DESC : "+rs.getString("BLNG_GRP_CATG_DESC"));
					////System.out.println("Patient Type : "+rs.getString("Patient Type"));
					////System.out.println("BLNG_GRP_ID : "+rs.getString("BLNG_GRP_ID"));
					////System.out.println("BLNG_GRP_ID_DESC : "+rs.getString("BLNG_GRP_ID_DESC"));
					////System.out.println("SETTLEMENT_IND : "+rs.getString("SETTLEMENT_IND"));
					
					////System.out.println("NEW_PRICE_CLASS_CODE : "+rs.getString("NEW_PRICE_CLASS_CODE"));
					////System.out.println("NEW_PRICE_CLASS_DESC : "+rs.getString("NEW_PRICE_CLASS_DESC"));
					////System.out.println("STATUS : "+rs.getString("STATUS"));
					////System.out.println("ADDED_DATE : "+rs.getString("ADDED_DATE"));
					////System.out.println("ADDED_BY_ID : "+rs.getString("ADDED_BY_ID"));
					////System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					
					// for PRACT_TYPE
					ob = new InsertDataTable();
					ob.setColName("PRACT_TYPE");
					if (rs.getString("PRACT_TYPE")!=null && !rs.getString("PRACT_TYPE").toString().equals("")) {
					ob.setDataName(rs.getString("PRACT_TYPE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for PRACTITIONER_NAME
					ob = new InsertDataTable();
					ob.setColName("PRACTITIONER_NAME");
					if (rs.getString("PRACTITIONER_NAME")!=null && !rs.getString("PRACTITIONER_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("PRACTITIONER_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for PRIMARY_SPECIALITY_CODE
					ob = new InsertDataTable();
					ob.setColName("PRIMARY_SPECIALITY_CODE");
					if (rs.getString("PRIMARY_SPECIALITY_CODE")!=null && !rs.getString("PRIMARY_SPECIALITY_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("PRIMARY_SPECIALITY_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for SPECIALITY
					ob = new InsertDataTable();
					ob.setColName("SPECIALITY");
					if (rs.getString("SPECIALITY")!=null && !rs.getString("SPECIALITY").toString().equals("")) {
					ob.setDataName(rs.getString("SPECIALITY"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					// for CLINIC_CODE
					ob = new InsertDataTable();
					ob.setColName("CLINIC_CODE");
					if (rs.getString("CLINIC_CODE")!=null && !rs.getString("CLINIC_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("CLINIC_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for  CLINIC
					ob = new InsertDataTable();
					ob.setColName("CLINIC");
					if (rs.getString("CLINIC")!=null && !rs.getString("CLINIC").toString().equals("")) {
					ob.setDataName(rs.getString("CLINIC"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					
					
					// for PRACTITIONER_ID
					ob = new InsertDataTable();
					ob.setColName("PRACTITIONER_ID");
					if (rs.getString("PRACTITIONER_ID")!=null && !rs.getString("PRACTITIONER_ID").toString().equals("")) {
					ob.setDataName(rs.getString("PRACTITIONER_ID"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for EFF_STATUS
					ob = new InsertDataTable();
					ob.setColName("EFF_STATUS");
					if (rs.getString("EFF_STATUS")!=null && !rs.getString("EFF_STATUS").toString().equals("")) {
					ob.setDataName(rs.getString("EFF_STATUS"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					
					
					
					boolean isExist = true;//ckhExist("id", "cps_billinggroup_his", "billing_grp_name", rs.getString("BLNG_GRP_CATG_DESC").toString(), clientConnection );
					if(isExist){
					
				
					int id=cot.insertDataReturnId("cps_Doctor_his", insertData, clientConnection);
					if(id>0)
					{
						////System.out.println("Data Inserted.");
					}
					}
					insertData.clear();
				
					
				}


			} catch (Exception e)
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
	
	
	public void fetchCorporateData(Log log, SessionFactory clientConnection, HelpdeskUniversalHelper huh, CommunicationHelper ch)
	{
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
				System.out.println("get call hit for fetch new data 15-02-2016");
				//String lastORDDateTime = lastORDdateTimeget(clientConnection);
				CommonOperInterface cot = new CommonConFactory().createInterface();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
				st = con.createStatement();
				////System.out.println("test con " + con.isClosed());

				////System.out.println(" <<<<<<<<<<<<<<<<<Appointment details >>>>>>>>>>>>>");
				 rs = st.executeQuery("select * from Dreamsol_bl_corp_hc_pkg  ");
				
				
				 
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				//The column count starts from 1 
				 for (int i = 1; i < columnCount+ 1; i++ ) 
				 { String name = rsmd.getColumnName(i); // Do stuffwith name 
				 	System.out.println(" name "+i+" : "+name ); 
				 }
				while (rs.next())
				{

					

					
					System.out.println("CUST_CODE : "+rs.getString("CUST_CODE"));
					System.out.println("BLNG_GRP_ID : "+rs.getString("BLNG_GRP_ID"));
					System.out.println("CUSTOMER_NAME : "+rs.getString("CUSTOMER_NAME"));
					System.out.println("ACCOUNT_MANAGER_NAME : "+rs.getString("ACCOUNT_MANAGER_NAME"));
					System.out.println("HEALTH_PKG_CODE : "+rs.getString("HEALTH_PKG_CODE"));
					System.out.println("HEALTH_PKG_NAME : "+rs.getString("HEALTH_PKG_NAME"));
					
					System.out.println("HEALTH_PKG_COST : "+rs.getString("HELATH_PKG_COST"));
					System.out.println("ACTIVE_YN : "+rs.getString("ACTIVE_YN"));
					//System.out.println("STATUS : "+rs.getString("STATUS"));
					////System.out.println("ADDED_DATE : "+rs.getString("ADDED_DATE"));
					////System.out.println("ADDED_BY_ID : "+rs.getString("ADDED_BY_ID"));
					////System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********************************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					
					// for CUST_CODE
					ob = new InsertDataTable();
					ob.setColName("CUST_CODE");
					if (rs.getString("CUST_CODE")!=null && !rs.getString("CUST_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("CUST_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for BLNG_GRP_ID
					ob = new InsertDataTable();
					ob.setColName("BLNG_GRP_ID");
					if (rs.getString("BLNG_GRP_ID")!=null && !rs.getString("BLNG_GRP_ID").toString().equals("")) {
					ob.setDataName(rs.getString("BLNG_GRP_ID"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for CUSTOMER_NAME
					ob = new InsertDataTable();
					ob.setColName("CUSTOMER_NAME");
					if (rs.getString("CUSTOMER_NAME")!=null && !rs.getString("CUSTOMER_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("CUSTOMER_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					// for ACCOUNT_MANAGER_NAME
					ob = new InsertDataTable();
					ob.setColName("ACCOUNT_MANAGER_NAME");
					if (rs.getString("ACCOUNT_MANAGER_NAME")!=null && !rs.getString("ACCOUNT_MANAGER_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("ACCOUNT_MANAGER_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					// for HEALTH_PKG_CODE
					ob = new InsertDataTable();
					ob.setColName("HEALTH_PKG_CODE");
					if (rs.getString("HEALTH_PKG_CODE")!=null && !rs.getString("HEALTH_PKG_CODE").toString().equals("")) {
					ob.setDataName(rs.getString("HEALTH_PKG_CODE"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for  HEALTH_PKG_NAME
					ob = new InsertDataTable();
					ob.setColName("HEALTH_PKG_NAME");
					if (rs.getString("HEALTH_PKG_NAME")!=null && !rs.getString("HEALTH_PKG_NAME").toString().equals("")) {
					ob.setDataName(rs.getString("HEALTH_PKG_NAME"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					
					
					// for HEALTH_PKG_COST
					ob = new InsertDataTable();
					ob.setColName("HELATH_PKG_COST");
					if (rs.getString("HELATH_PKG_COST")!=null && !rs.getString("HELATH_PKG_COST").toString().equals("")) {
					ob.setDataName(rs.getString("HELATH_PKG_COST"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					// for ACTIVE_YN
					ob = new InsertDataTable();
					ob.setColName("ACTIVE_YN");
					if (rs.getString("ACTIVE_YN")!=null && !rs.getString("ACTIVE_YN").toString().equals("")) {
					ob.setDataName(rs.getString("ACTIVE_YN"));
					}
					else {
					ob.setDataName("NA");
					}
					insertData.add(ob);
					
					
					
					
					
					
					boolean isExist = true;//ckhExist("id", "cps_billinggroup_his", "billing_grp_name", rs.getString("BLNG_GRP_CATG_DESC").toString(), clientConnection );
					if(isExist){
					
				
					int id=cot.insertDataReturnId("Dreamsol_bl_corp_hc_pkg", insertData, clientConnection);
					if(id>0)
					{
						////System.out.println("Data Inserted.");
					}
					}
					insertData.clear();
				
					
				
				}


			} catch (Exception e)
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



	public void fetchCorporateDataNew(Log log, SessionFactory connection,
			HelpdeskUniversalHelper hUH, CommunicationHelper cH) {
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
				System.out.println("get call hit for fetch new data 15-02-2016");
				//String lastORDDateTime = lastORDdateTimeget(clientConnection);
				CommonOperInterface cot = new CommonConFactory().createInterface();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
				st = con.createStatement();
				////System.out.println("test con " + con.isClosed());

				////System.out.println(" <<<<<<<<<<<<<<<<<Appointment details >>>>>>>>>>>>>")
				 rs = st.executeQuery("select * from dream_sol_helth_checkup_bg ");
				
				
				 
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = new InsertDataTable();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				//The column count starts from 1 
				 for (int i = 1; i < columnCount+ 1; i++ ) 
				 { String name = rsmd.getColumnName(i); // Do stuffwith name 
				 	System.out.println(" name "+i+" : "+name ); 
				 }
				while (rs.next())
				{
					
					
					
				}


			} catch (Exception e)
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
