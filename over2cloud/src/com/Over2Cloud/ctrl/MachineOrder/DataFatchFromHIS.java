package com.Over2Cloud.ctrl.MachineOrder;

import hibernate.common.HisHibernateSessionFactory;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DataFatchFromHIS extends ActionSupport {
	
	private JSONArray commonJSONArray = new JSONArray();
	private String uhid;
	Session sessionHis = null; 
	
	
	// fetch the patient info from his
	
	public String fetchAllotTo(){
		
		
		sessionHis = HisHibernateSessionFactory.getSession();
		 Query query = sessionHis.createSQLQuery("select * from Dreamsol_Mc_ord_VW where ORDER_ID="+uhid+"");
			List clientData = query.list();
			//System.out.println("Client data List Size is "+clientData.size());
			if(clientData != null && clientData.size() > 0) {
				
				for (Iterator iterator = clientData.iterator(); iterator .hasNext();) {
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					 {
					 System.out.println("get the data");
					 }
			}
			}
			return SUCCESS;

		/*String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			java.sql.Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			
			try {
				 CommonOperInterface cot = new CommonConFactory().createInterface();
				 Class.forName("oracle.jdbc.driver.OracleDriver");
				 con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "report", "report");
				 st = con.createStatement();
				System.out.println("test con " + con.isClosed());
				rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW where ORDER_ID="+uhid+"");

				while (rs.next())
				{	//System.out.println("id: " + rs.getString("ID"));
					System.out.println("oredr id: " + rs.getString("ORDER_ID"));
					System.out.println("order type: " + rs.getString("ORDER_TYPE_CODE"));
					System.out.println("UHID: " + rs.getString("PATIENT_ID"));
					System.out.println("Patient Name: " + rs.getString("Patient_Name"));
					System.out.println("BED num: " + rs.getString("bed_num"));
					System.out.println("Added by: " + rs.getString("added_by_id"));
					System.out.println("priority: " + rs.getString("priority"));
					System.out.println("ORD_DATE_TIME: " + rs.getString("ORD_DATE_TIME"));
					System.out.println("Doctor name: " + rs.getString("practitioner_name"));
					
					
					
					JSONObject listObject = new JSONObject();
					
					listObject.put("pName", rs.getString("Patient_Name"));
					listObject.put("bedNo", rs.getString("bed_num"));
					listObject.put("nursingUnit", "1122");
					listObject.put("admDoc", rs.getString("practitioner_name"));
					listObject.put("admSpec", "Not Specified");
					commonJSONArray.add(listObject);
					
					
					
				
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;*/
	
		
		
	}
	
	
	
	
	

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}






	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}

}
