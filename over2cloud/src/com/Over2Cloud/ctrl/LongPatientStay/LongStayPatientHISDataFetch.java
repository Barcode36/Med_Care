package com.Over2Cloud.ctrl.LongPatientStay;



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
import com.Over2Cloud.Rnd.MailReader;
import com.aspose.slides.p883e881b.els;





public class LongStayPatientHISDataFetch  extends Thread  {

	private static final Log log = LogFactory.getLog(LongStayPatientHISDataFetch.class);
	

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
	
	/*public LongStayPatientHISDataFetch(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						//t2mvirtual.getVirtualNoData(connection);
						//	CDIH.CRCDATAFetchONE(log, connection, sessionHis, ch);
						fetchAllPatientData (log, connection, sessionHis, ch);
						
						//fetchAllNurshingPatientData (log, connection, sessionHis, ch);
					
						
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 60 );
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
	
	*/
	

	public void fetchAllNurshingPatientData() {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		System.out.println("day  "+day);
		try
		{
			
			
		
			nurshingMap = new HashMap<String,String>();
				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					
						rs = st.executeQuery("SELECT * FROM Dreamsol_IP_VW  where ADMISSION_DATE >= TO_TIMESTAMP('2015-09-13 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF') and ADMISSION_DATE <= to_timestamp('2015-09-14 21:25:33.0', 'YYYY-MM-DD HH24:MI:SS.FF')  ");
						//commonJSONArray = new JSONArray();
						
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
						{
							
							//System.out.println("UHIID"+ rs.getString("UHIID"));
							//System.out.println("pName"+ rs.getString("PATIENT_NAME"));
							//System.out.println("bedNo"+ rs.getString("ASSIGN_BED_NUM"));
							//
							//System.out.println("attDoc"+ rs.getString("ATTENDING_PRACTITIONER_NAME"));
							//System.out.println("admDoc"+ rs.getString("ADMITTING_PRACTITIONER_NAME"));
							//System.out.println("admSpec"+ rs.getString("SPECIALITY"));
							//System.out.println("admSpec"+ rs.getString("ADMISSION_DATE"));
							//System.out.println("nursing_code "+ rs.getString("Assign_care_locn_code"));
							//.out.println("nursingUnit"+ rs.getString("LONG_DESC"));
							
							if (rs.getString("Assign_care_locn_code") != null && !rs.getString("Assign_care_locn_code").toString().equals("") && rs.getString("LONG_DESC") != null && !rs.getString("LONG_DESC").toString().equals(""))
							{
							nurshingMap.put(rs.getString("Assign_care_locn_code"), rs.getString("LONG_DESC"));
							//nursing_code = rsPat.getString("Assign_care_locn_code");
							}
							
						}
						
						System.out.println("nurshingMap "+nurshingMap);
						
						System.out.println("i  ==  "+i);
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

	public void fetchAllPatientData() {
		// TODO Auto-generated method stub
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		//String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
		//System.out.println("day  "+day);
		try
		{
			
			
		
			List<Object> tempList = new ArrayList<Object>();
				System.out.println("<<<<<<<         IT IS 88 SERVER         >>>>>>>>>");
				//System.out.println(dataFor+"::::"+patType);
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.82:1521:HISNEW", "dreamsol", "Dream_1$3");
					st = con.createStatement();
					
					
						rs = st.executeQuery("SELECT * FROM Dreamsol_IP_VW  where ADMISSION_DATE >= TO_TIMESTAMP('2015-09-13 00:00:00.0','YYYY-MM-DD HH24:MI:SS.FF') and ADMISSION_DATE <= to_timestamp('2015-09-14 21:25:33.0', 'YYYY-MM-DD HH24:MI:SS.FF') ");
						//commonJSONArray = new JSONArray();
						
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();

						// The column count starts from 1
						for (int i = 1; i < columnCount + 1; i++)
						{
							String name = rsmd.getColumnName(i); // Do stuffwith name
							System.out.println(" name one " + i + " : " + name);
						}
						//JSONObject listObject = new JSONObject() UHIID;
						int i = 1;
						while (rs.next())
						{
							Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
							//System.out.println("UHIID"+ rs.getString("UHIID"));
							//System.out.println("pName"+ rs.getString("PATIENT_NAME"));
							//System.out.println("bedNo"+ rs.getString("ASSIGN_BED_NUM"));
							
							//System.out.println("attDoc"+ rs.getString("ATTENDING_PRACTITIONER_NAME"));
							//System.out.println("admDoc"+ rs.getString("ADMITTING_PRACTITIONER_NAME"));
							//System.out.println("admSpec"+ rs.getString("SPECIALITY"));
							//System.out.println("ADMISSION_DATE"+ DateUtil.convertDateToIndianFormat(rs.getString("ADMISSION_DATE").split(" ")[0]) );
						//	System.out.println("nursing_code "+ rs.getString("Assign_care_locn_code"));
						//	System.out.println("nursingUnit"+ rs.getString("LONG_DESC"));
							
							tempMap.put("id", i);
							tempMap.put("uhid", rs.getString("UHIID"));
							tempMap.put("pName", rs.getString("PATIENT_NAME"));
							tempMap.put("bedNo", rs.getString("ASSIGN_BED_NUM"));
							tempMap.put("attDoc", rs.getString("ATTENDING_PRACTITIONER_NAME"));
							tempMap.put("admDoc", rs.getString("ADMITTING_PRACTITIONER_NAME"));
							tempMap.put("admSpec", rs.getString("SPECIALITY"));
							tempMap.put("ADMISSION_DATE", DateUtil.convertDateToIndianFormat(rs.getString("ADMISSION_DATE").split(" ")[0])+" "+rs.getString("ADMISSION_DATE").split(" ")[1]);
							
							//tempMap.put("nursing_code", rs.getString("Assign_care_locn_code"));
							tempMap.put("nursingUnit", rs.getString("LONG_DESC"));
							
							
							
							tempList.add(tempMap);
							//nursing_code = rsPat.getString("Assign_care_locn_code");
							System.out.println(tempList);
							i = i+1;
							 
						}
						setMasterViewProp(tempList);
						setTotal(i-1);
						System.out.println("i  ==  "+i);
						System.out.println(">>>> "+masterViewProp);
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Object> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

/*	public static void main(String[] args) {
		Thread.State state ;
		try{
			
			System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			System.out.println("After HIS DB connection"+sessionHis);
			
				Thread uclient=new Thread(new LongStayPatientHISDataFetch(connection, sessionHis));
				System.out.println("Thread Created Successfuly!!");
				state = uclient.getState(); 
				System.out.println("Thread's State:: "+state);
			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
				System.out.println("Thread Started Successfuly!!");
			
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
	}*/
}