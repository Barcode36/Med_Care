package com.Over2Cloud.ctrl.criticalPatient;

import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;

/**
 * @author dreamsol
 *
 */
public class CriticalAdd extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	private String dataFor;
	private String patType;
	private String uhid;
	private String uhidStatus;
	private String testName;
	private JSONArray commonJSONArray;
	public List<GridDataPropertyView> viewPageUHIDColumns=null;
	private String docName;
	private String caller_emp_id;
	
	private List<Object> viewCritical = null;
	/**  Show Critical Lodge Page 
	 */
	public String beforeCriticalLodge()
	{
		String result = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				result = SUCCESS;
			}
			catch (Exception e)
			{
				result = ERROR;
				e.printStackTrace();
			}
		}
		return result;
	}


	
	
	/** 
	 * Set Test Type and Test Name
	 */
	@SuppressWarnings("unchecked")
	public String criticalLodgeDDValue()
	{
		String result = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				commonJSONArray = new JSONArray();
				JSONArray arr1 = new JSONArray();
				JSONArray arr2 = new JSONArray();
				List list = cbt.executeAllSelectQuery("select ty.id ,ty.test_type ,tn.id as testnameid ,tn.test_name,tn.test_type as testtypeid,tn.test_unit,tn.min,tn.max,tn.gender from test_type as ty left join test_name as tn on ty.id=tn.test_type  ", connectionSpace);
				if (list != null && list.size() > 0)
				{
					JSONObject testType = null;
					JSONObject testName = null;
					@SuppressWarnings("rawtypes")
					List testList=new ArrayList();
					//String value[] = new String[list.size()];
					int i = 0;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (!testList.contains(object[0].toString()))
						{
							testType = new JSONObject();
							testType.put("id", checkValue(object[0]));
							testType.put("name", checkValue(object[1]));
							arr1.add(testType);
						}
						testList.add(object[0].toString());
					
						testName = new JSONObject();
						testName.put("id", checkValue(object[2]));
						testName.put("name", checkValue(object[3]));
						testName.put("test_id", checkValue(object[4]));
						testName.put("test_unit", checkValue(object[5]));
						testName.put("min", checkValue(object[6]));
						testName.put("max", checkValue(object[7]));
						testName.put("gender", checkValue(object[8]));
						testName.put("LabName", checkValue(object[1]));
						testName.put("LabID", checkValue(object[0]));
						arr2.add(testName);
						i++;
					}
				}
				commonJSONArray.add(arr1);
				commonJSONArray.add(arr2);
				result = SUCCESS;
			}
			catch (Exception e)
			{
				result = ERROR;
				e.printStackTrace();
			}
		}
		return result;
	}

	public String checkValue(Object value)
	{
		return (value == null || value.toString().equals("") ? "NA" : value.toString());
	}

	public String fetchDetails()
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String tst = "NA";
		try
		{
			//////System.out.println(dataFor + "::::" + dataFor);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			if ("patient".equalsIgnoreCase(dataFor))
			{
				
				
				String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
				//System.out.println("day  "+day);
				if((DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
						(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
						(day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" ))){
					//System.out.println("1");
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
				}
				else{
					//System.out.println("2");
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
				}
				//System.out.println("3  "+con.isClosed());
				st = con.createStatement();
				
				
				
				if(uhid.contains("MM")){
					//System.out.println("kk");
					rs = st.executeQuery("select * from Dreamsol_IP_CR_TST_VW where  UHID= '" + uhid + "'");
				}
				else{
				rs = st.executeQuery("select * from Dreamsol_IP_CR_TST_VW where  SPECIMEN_NO= '" + uhid + "'");
				}
					//rs = st.executeQuery("SELECT * FROM Dreamsol_IP_CR_TST_VW WHERE UHIID='" + uhid + "' ");
					commonJSONArray = new JSONArray();
					JSONObject listObject = new JSONObject();
					while (rs.next())
					{
						
						
						String spec = "NA";
						if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().contains("&")){
							spec = rs.getString("ORDERING_DOCTOR_SPECIALTY");
							spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
							listObject.put("admSpec", spec);
						}
						else if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equalsIgnoreCase("Ear,Nose,Throat")){
														listObject.put("admSpec", "Ear Nose Throat");
						}
						else{
							listObject.put("admSpec", rs.getString("ORDERING_DOCTOR_SPECIALTY"));
						}
						
						tst = rs.getString("Patient Name").toString();
						////System.out.println("rs.getString(PATIENT_NAME)>>>>>>   "+rs.getString("PATIENT_NAME"));
						listObject.put("pName", rs.getString("Patient Name"));
						listObject.put("bedNo", rs.getString("BED_NUM"));
						//listObject.put("nursingUnit", rs.getString("LONG_DESC"));
						
						if(rs.getString("WARD").toString().contains("&")){
							spec = rs.getString("WARD");
							spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
							listObject.put("nursingUnit", spec);
						}
						else{
							listObject.put("nursingUnit", rs.getString("WARD"));
						}
						
						
						if(rs.getString("ORDERING_DOCTOR").toString().contains("&")){
							spec = rs.getString("ORDERING_DOCTOR");
							spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
							listObject.put("admDoc", spec);
						}
						else{
							listObject.put("admDoc", rs.getString("ORDERING_DOCTOR"));
						}
						
						if(rs.getString("SERVICE_NAME").toString().contains("&")){
							spec = rs.getString("SERVICE_NAME");
							spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
							listObject.put("testName", spec);
						}
						else{
							listObject.put("testName", rs.getString("SERVICE_NAME"));
						}
						
						if(rs.getString("LabName").toString().contains("&")){
							spec = rs.getString("LabName");
							spec = spec.split("&")[0].toString()+" "+spec.split("&")[1].toString();
							listObject.put("testFor", spec);
						}
						else{
							String tempTest =   rs.getString("LabName");
							if(rs.getString("LabName").equalsIgnoreCase("BIO")){
								tempTest="Biochemistry";
							}
							else if (rs.getString("LabName").equalsIgnoreCase("HEM")){
								tempTest="Hematology";
							}
							else if (rs.getString("LabName").equalsIgnoreCase("MIC")){
								tempTest="Microbiology";
							}
							listObject.put("testFor", tempTest);
						}
						
						if(rs.getString("Result")!=null && !rs.getString("Result").equalsIgnoreCase("")){
							listObject.put("test_value", rs.getString("Result"));
						}
						else{
							listObject.put("test_value", "NA");
						}
						
						if(rs.getString("TEST_UNITS")!=null && !rs.getString("TEST_UNITS").equalsIgnoreCase("")){
							listObject.put("TEST_UNITS", rs.getString("TEST_UNITS"));
						}
						else{
							listObject.put("TEST_UNITS", "NA");
						}
						
						if(rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
							listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
						}
						/*else if (rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
							listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
						}*/
						else{
							listObject.put("pat_mob", "NA");
						}
						//listObject.put("admDoc", rs.getString("ATTENDING_PRACTITIONER_NAME"));
						
						listObject.put("uhidRe", rs.getString("UHID"));
						listObject.put("bedNo", rs.getString("BED_NUM"));
						listObject.put("patientSts", "IPD");
						
						
					}
					
					if(tst.equalsIgnoreCase("NA")){
						
						////System.out.println("uhid1 " + uhid);
						
						if(uhid.contains("MM")){
							rs = st.executeQuery("select * from Dreamsol_EM_CR_TST_VW where UHID = '" + uhid + "'");
						}
						else{
						rs = st.executeQuery("select * from Dreamsol_EM_CR_TST_VW where  SPECIMEN_NO= '" + uhid + "'");
						}
					
						//commonJSONArray = new JSONArray();
						listObject.clear();
						//JSONObject listObject = new JSONObject();
						while (rs.next())
						{
							////System.out.println("rs.getString(PATIENT_NAME232323233)>>>>>>   "+rs.getString("PATIENT_NAME"));
							tst = rs.getString("PATIENT NAME").toString();
							listObject.put("pName", rs.getString("Patient Name"));
							listObject.put("bedNo", "Emergency");
							listObject.put("nursingUnit", "Emergency");
							listObject.put("patientSts", "EM");
							listObject.put("bedNo", "Emergency");
							
							String spec = "NA";
							if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().contains("&")){
								spec = rs.getString("ORDERING_DOCTOR_SPECIALTY");
								spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
								listObject.put("admSpec", spec);
							}
							else if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equalsIgnoreCase("Ear,Nose,Throat")){
								listObject.put("admSpec", "Ear Nose Throat");
}
							else{
								listObject.put("admSpec", rs.getString("ORDERING_DOCTOR_SPECIALTY"));
							}
							if(rs.getString("ORDERING_DOCTOR").toString().contains("&")){
								spec = rs.getString("ORDERING_DOCTOR");
								spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
								listObject.put("admDoc", spec);
							}
							else{
								listObject.put("admDoc", rs.getString("ORDERING_DOCTOR"));
							}
							
							if(rs.getString("SERVICE_NAME").toString().contains("&")){
								spec = rs.getString("SERVICE_NAME");
								spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
								listObject.put("testName", spec);
							}
							else{
								listObject.put("testName", rs.getString("SERVICE_NAME"));
							}
							
							if(rs.getString("LabName").toString().contains("&")){
								spec = rs.getString("LabName");
								spec = spec.split("&")[0].toString()+" "+spec.split("&")[1].toString();
								listObject.put("testFor", spec);
							}
							else{
								String tempTest =   rs.getString("LabName");
								if(rs.getString("LabName").equalsIgnoreCase("BIO")){
									tempTest="Biochemistry";
								}
								else if (rs.getString("LabName").equalsIgnoreCase("HEM")){
									tempTest="Hematology";
								}
								else if (rs.getString("LabName").equalsIgnoreCase("MIC")){
									tempTest="Microbiology";
								}
								listObject.put("testFor", tempTest);
							}
							
							
							if(rs.getString("TEST_UNITS")!=null && !rs.getString("TEST_UNITS").equalsIgnoreCase("")){
								listObject.put("TEST_UNITS", rs.getString("TEST_UNITS"));
							}
							else{
								listObject.put("TEST_UNITS", "NA");
							}
							
							if(rs.getString("Result")!=null && !rs.getString("Result").equalsIgnoreCase("")){
								listObject.put("test_value", rs.getString("Result"));
							}
							else{
								listObject.put("test_value", "NA");
							}
							
							if(rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
								listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
							}
							/*else if (rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
								listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
							}*/
							else{
								listObject.put("pat_mob", "NA");
							}
							//listObject.put("admDoc", rs.getString("ATTENDING_PRACTITIONER_NAME"));
							
							listObject.put("uhidRe", rs.getString("UHID"));
						}
						
						if(tst.equalsIgnoreCase("NA")){
							
							////System.out.println("SPEC " + uhid);
							con.close();
							st.close();
							rs.close();
							
							
							//String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
							//System.out.println("day  "+day);
							if((DateUtil.getCurrentDay()==0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30" )) ||
									(day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "19:55", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:59" )) ||
									(day.equalsIgnoreCase("02") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "00:00", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "07:00" ))){
							con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
							}
							else{
								con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
							}
							//con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
							st = con.createStatement();
							
							if(uhid.contains("MM")){
								rs = st.executeQuery("select * from Dreamsol_OP_CR_TST_VW where UHID = '" + uhid + "'");
							}
							else{
							rs = st.executeQuery("select * from Dreamsol_OP_CR_TST_VW where  SPECIMEN_NO= '" + uhid + "'");
							}
						
							//commonJSONArray = new JSONArray();
							listObject.clear();
							//JSONObject listObject = new JSONObject();
							while (rs.next())
							{
								
								String spec = "NA";
								
								////System.out.println("specality "+rs.getString("ORDERING_DOCTOR_SPECIALTY"));
								if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().contains("&")){
									spec = rs.getString("ORDERING_DOCTOR_SPECIALTY");
									spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
									listObject.put("admSpec", spec);
								}
								else if(rs.getString("ORDERING_DOCTOR_SPECIALTY").toString().equalsIgnoreCase("Ear,Nose,Throat")){
									listObject.put("admSpec", "Ear Nose Throat");
	}
								else{
									listObject.put("admSpec", rs.getString("ORDERING_DOCTOR_SPECIALTY"));
								}
								if(rs.getString("ORDERING_DOCTOR").toString().contains("&")){
									spec = rs.getString("ORDERING_DOCTOR");
									spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
									listObject.put("admDoc", spec);
								}
								else{
									listObject.put("admDoc", rs.getString("ORDERING_DOCTOR"));
								}
								
								if(rs.getString("SERVICE_NAME").toString().contains("&")){
									spec = rs.getString("SERVICE_NAME");
									spec = spec.split("&")[0].toString()+" and "+spec.split("&")[1].toString();
									listObject.put("testName", spec);
								}
								else{
									listObject.put("testName", rs.getString("SERVICE_NAME"));
								}
								
								if(rs.getString("LabName").toString().contains("&")){
									spec = rs.getString("LabName");
									spec = spec.split("&")[0].toString()+" "+spec.split("&")[1].toString();
									listObject.put("testFor", spec);
								}
								else{
									String tempTest =   rs.getString("LabName");
									if(rs.getString("LabName").equalsIgnoreCase("BIO")){
										tempTest="Biochemistry";
									}
									else if (rs.getString("LabName").equalsIgnoreCase("HEM")){
										tempTest="Hematology";
									}
									else if (rs.getString("LabName").equalsIgnoreCase("MIC")){
										tempTest="Microbiology";
									}
									listObject.put("testFor", tempTest);
								}
								////System.out.println("spec "+spec);
								listObject.put("pName", rs.getString("Patient Name"));
								listObject.put("bedNo", "OPD");
								listObject.put("nursingUnit", "OPD");
								//listObject.put("admDoc", rs.getString("ORDERING_DOCTOR"));
								//listObject.put("testFor", rs.getString("LabName"));
							//	listObject.put("testName", rs.getString("SERVICE_NAME"));
								listObject.put("patientSts", "OPD");
								listObject.put("uhidRe", rs.getString("UHID"));
								
								if(rs.getString("TEST_UNITS")!=null && !rs.getString("TEST_UNITS").equalsIgnoreCase("")){
									listObject.put("TEST_UNITS", rs.getString("TEST_UNITS"));
								}
								else{
									listObject.put("TEST_UNITS", "NA");
								}
								
								if(rs.getString("Result")!=null && !rs.getString("Result").equalsIgnoreCase("")){
									listObject.put("test_value", rs.getString("Result"));
								}
								else{
									listObject.put("test_value", "NA");
								}
								
								 
								if(rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
									listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
								}
								/*else if (rs.getString("CONTACT2_NO")!=null && !rs.getString("CONTACT2_NO").equalsIgnoreCase("")){
									listObject.put("pat_mob", rs.getString("CONTACT2_NO"));
								}*/
								else{
									listObject.put("pat_mob", "NA");
								}
								
								
								
							}
						}
					}
					commonJSONArray.add(listObject);
				
			}
			else if ("emp".equalsIgnoreCase(dataFor))
			{// ////System.out.println("for employee "+uhid);
				////System.out.println("uhid before" + uhid);
				int hisUHIDLength = 8;
				int uhidLength = uhid.length();
				int templength = 0;
				if (uhidLength < hisUHIDLength)
				{

					templength = hisUHIDLength - uhidLength;
					for (int j = 0; j < templength; j++)
					{
						uhid = "0" + uhid;
					}
				}
				////System.out.println("uhid after" + uhid);

				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.34:1521:KRP", "spectra", "spectra");
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM EMPLOYEEINFORMATION_VW WHERE PERSONNUM = '" + uhid + "' ");
				commonJSONArray = new JSONArray();
				JSONObject listObject = new JSONObject();
				while (rs.next())
				{
					// ////System.out.println("FName: "+rs.getString("FIRSTNAME")+" LName: "+rs.getString("LASTNAME")+" Person: "+rs.getString("PERSONNUM")+" Title: "+rs.getString("Title"));
					listObject.put("fName", rs.getString("FIRSTNAME"));
					listObject.put("lName", rs.getString("LASTNAME"));
					//listObject.put("mobile", rs.getString("mobile"));
				}
				commonJSONArray.add(listObject);
			}
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
		/*
		 * } else { returnResult = "login"; }
		 */
		return returnResult;
	}

	
	
	public String UHIDCheck(){
		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query= new StringBuilder(" select cd.patient_id from critical_patient_data as cpd   " );
				query.append("inner join critical_data as cd on cd.patient_id=cpd.id ");
				
				query.append(" where cpd.uhid='"+uhid+"' and cd.open_date>='"+DateUtil.getDateAfterDays(-1)+"'" +
						" GROUP BY cd.id ORDER BY cd.id DESC ");
				
				 ////System.out.println(">>>>   " +query.toString());
				List l1 = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if(l1 !=null && l1.size() > 0){
					dataFor=l1.get(0).toString();
					String link="<a href='#' onclick='showCurrentData()'><font color='green', size='5'><b>Data Found</b></font></a>";
					setUhidStatus(link);
				}else{
					String errorlink="<a href='#' ><font  size='3', color='red'>No Data Found</font></a>";
					setUhidStatus(errorlink);
				}
				
				return SUCCESS;
			} catch (Exception e){
				addActionMessage("Oops Error!!!");
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
				addActionMessage("Your session out!!! Login again");
				return LOGIN;
		}
		
	}
	
	
	public String beforeCriticalUHID()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				setDataFor(dataFor);
				setGridUHIDColumns();
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public void setGridUHIDColumns()
	{
		try
		{
			viewPageUHIDColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticket_no");
			gpv.setHeadingName("Ticket ID");
			gpv.setFormatter("historyView");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageUHIDColumns.add(gpv);
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("openDate");
			gpv.setHeadingName("Open Date");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("openTime");
			gpv.setHeadingName("Open Time");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalate_date");
			gpv.setHeadingName("TAT");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setFormatter("viewLevel");
			gpv.setWidth(50);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageUHIDColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("patient_name");
			gpv.setHeadingName("Patinet Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("bed_no");
			gpv.setHeadingName("Bed No");
			gpv.setHideOrShow("true");
			gpv.setWidth(130);
			
			viewPageUHIDColumns.add(gpv);
			gpv = new GridDataPropertyView();
			gpv.setColomnName("patient_type");
			gpv.setHeadingName("Patinet Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageUHIDColumns.add(gpv);
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("Adm_doc_name");
			gpv.setHeadingName("Admission Doctor");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageUHIDColumns.add(gpv); 
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("lab_doc_name");
			gpv.setHeadingName("Lab Doctor");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursing_unit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setWidth(100);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("spec");
			gpv.setHeadingName("Speciality");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageUHIDColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("patient_id");
			gpv.setHeadingName("patient_id");
			gpv.setHideOrShow("true");
			gpv.setWidth(05);
			viewPageUHIDColumns.add(gpv);
			
			
				
			
			 

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}

	}
	
	
	// View Data in UHID Check for current date
	@SuppressWarnings({ "rawtypes", "unused" })
	public String viewUHIDGridData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from critical_data ", connectionSpace);
				// //////// ////System.out.println("dataCount:"+dataCount);
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to < getRecords())
					to = getRecords();

				StringBuilder query = new StringBuilder();
				query.append("SELECT cridata.id,cridata.ticket_no,cridata.status,cridata.open_date,"
						+ "cridata.open_time,cridata.escalate_date,cridata.escalate_time,cridata.level,"
						+ "cpd.patient_name,cpd.patient_status,cpd.addmision_doc_name,cpd.nursing_unit,cpd.specialty,cridata.doc_name,cridata.patient_id , cpd.bed_no FROM critical_data AS cridata ");

				query.append(" LEFT JOIN critical_patient_data AS cpd ON cridata.patient_id=cpd.id");
				query.append(" LEFT JOIN critical_patient_report AS crep ON crep.patient_id=cpd.id ");
				query.append(" LEFT JOIN critical_data_history AS cdh ON cdh.rid=cpd.id ");
				
				
				if (uhidStatus != null && !uhidStatus.equalsIgnoreCase("undefined"))
				{
					query.append("where cridata.uhid='"+uhidStatus+"' and cridata.open_date >='"+DateUtil.getDateAfterDays(-1)+"' " +
							"  GROUP BY cridata.id ORDER BY cridata.id DESC ");
				}
					//// ////System.out.println("+++  "+ query.toString());
				//query.append(" GROUP BY refdata.id ORDER BY refdata.id DESC ");
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("ticket_no", getValueWithNullCheck(object[1]));
						tempMap.put("test", "View Test");
						if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
						{
							tempMap.put("status", "OCC Park");
						}  else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore"))
						{
							tempMap.put("status", "OCC Ignore");
						} else
						{
							tempMap.put("status", getValueWithNullCheck(object[2]));
						}
						tempMap.put("open_date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[3].toString())) + ", " + getValueWithNullCheck(object[4]).substring(0, 5));
						// tempMap.put("escalate_date",
						// getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[5].toString()))
						// + ", " + getValueWithNullCheck(object[6]));
						if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Not Informed") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Informed"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[5].toString(), object[6].toString()));
						} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I") || getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
						{
							tempMap.put("escalate_date", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[20].toString(), object[21].toString()));
						} else
						{
							tempMap.put("escalate_date", "00:00");
						}
						
						tempMap.put("openDate", DateUtil.convertDateToIndianFormat(object[3].toString()));
						tempMap.put("openTime", getValueWithNullCheck(object[4]));
						tempMap.put("level", getValueWithNullCheck(object[7]));
						tempMap.put("patient_name", getValueWithNullCheck(object[8]));
						tempMap.put("patient_type", getValueWithNullCheck(object[9]));
						tempMap.put("Adm_doc_name", getValueWithNullCheck(object[10]));
						tempMap.put("lab_doc_name", getValueWithNullCheck(object[13]));
						tempMap.put("nursing_unit", getValueWithNullCheck(object[11]));
						tempMap.put("spec", getValueWithNullCheck(object[12]));
						tempMap.put("patient_id", getValueWithNullCheck(object[14]));

						tempList.add(tempMap);
					}
					setViewCritical(tempList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}
	
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String addcritical()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			try
			{
				boolean isExist = false;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				// Do add referral if same UHID is already added for same
				// specialty on current day
				StringBuilder query = new StringBuilder();
				query.append("SELECT cd.id FROM critical_data AS cd ");
				query.append(" INNER JOIN critical_patient_data AS cpd ON cpd.id=cd.patient_id");
				query.append(" WHERE cpd.uhid='" + request.getParameter("uhid") + "' and cd.open_time >='" + DateUtil.getCurrentTimeHourMin() + "' AND cd.open_date between '" + DateUtil.getNextDateAfter(-1) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
				query.append(" and cd.status in ('Informed', 'Snooze', 'Ignore', 'Not Informed') ");
				// ////System.out.println("query pat::" + query);
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
				
								isExist = true;
								addActionMessage("Same Critical is Already Added .");
								return SUCCESS;
						
				}
				// isExist = cbt.checkExitOfTable("referral_data", Assing
				// connectionSpace, null, null);
				if (!isExist)
				{
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					TableColumes ob1 = new TableColumes();

					ob1 = new TableColumes();
					ob1.setColumnname("patient_id");
					ob1.setDatatype("int(10)");
					ob1.setConstraint("default 0");
					TableColumnName.add(ob1);

					List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_critical_configuration", accountID, connectionSpace, "", 0, "table_name", "critical_configuration");
					if (org2 != null)
					{
						// create table query based on configuration
						for (GridDataPropertyView gdp : org2)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							if (gdp.getColomnName().equalsIgnoreCase("ticket_no") || gdp.getColomnName().equalsIgnoreCase("caller_emp_id"))
							{
								ob1.setDatatype("varchar(20)");
								ob1.setConstraint(" default 'NA'");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("open_date") || gdp.getColomnName().equalsIgnoreCase("escalate_date") || gdp.getColomnName().equalsIgnoreCase("open_time") || gdp.getColomnName().equalsIgnoreCase("escalate_time"))
							{
								ob1.setDatatype("varchar(10)");
								ob1.setConstraint(" default 'NA'");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("reg_by"))
							{
								ob1.setDatatype("int(10)");
								ob1.setConstraint(" default 0 ");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("caller_emp_name") || gdp.getColomnName().equalsIgnoreCase("doc_name") || gdp.getColomnName().equalsIgnoreCase("patient_name") || gdp.getColomnName().equalsIgnoreCase("addmision_doc_name") || gdp.getColomnName().equalsIgnoreCase("specialty"))
							{
								ob1.setDatatype("varchar(30) ");
								ob1.setConstraint(" default 'NA'");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("level") || gdp.getColomnName().equalsIgnoreCase("priority") || gdp.getColomnName().equalsIgnoreCase("patient_status"))
							{
								ob1.setDatatype("varchar(7) ");
								ob1.setConstraint(" default 'NA'");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("status") || gdp.getColomnName().equalsIgnoreCase("uhid") || gdp.getColomnName().equalsIgnoreCase("nursing_unit") || gdp.getColomnName().equalsIgnoreCase("test_type"))
							{
								ob1.setDatatype("varchar(15)  ");
								ob1.setConstraint(" default 'NA'");
							}
							else if (gdp.getColomnName().equalsIgnoreCase("patient_moblie") || gdp.getColomnName().equalsIgnoreCase("caller_emp_mobile") || gdp.getColomnName().equalsIgnoreCase("doc_mobile") || gdp.getColomnName().equalsIgnoreCase("addmision_doc_mobile"))
							{
								ob1.setDatatype("varchar(25) ");
								ob1.setConstraint(" default 'NA'");
							}
							else
							{
								ob1.setDatatype("varchar(255) ");
								ob1.setConstraint(" default 'NA'");
							}
							
							TableColumnName.add(ob1);

						}
						
						ob1 = new TableColumes();
						ob1.setColumnname(" gender");
						ob1.setDatatype(" varchar(10)");
						ob1.setConstraint(" default 'NA' ");
						TableColumnName.add(ob1);

						
						cbt.createTable22("critical_data", TableColumnName, connectionSpace);

					}

					TableColumnName.clear();
					ob1 = new TableColumes();
					ob1.setColumnname("uhid");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_mob");
					ob1.setDatatype("varchar(25)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_status");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("addmision_doc_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("addmision_doc_mobile");
					ob1.setDatatype("varchar(25)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_unit");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("specialty");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("bed_no");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					cbt.createTable22("critical_patient_data", TableColumnName, connectionSpace);

					TableColumnName.clear();
					ob1 = new TableColumes();
					ob1.setColumnname("patient_id");
					ob1.setDatatype("int(15)");
					ob1.setConstraint("default 0");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("uhid");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("test_type");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("test_name");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("test_value");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("test_unit");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("test_comment");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);
					
					//add Gender 
					ob1 = new TableColumes();
					ob1.setColumnname("gender");
					ob1.setDatatype("varchar(8)");
					ob1.setConstraint("default 'Male'");
					TableColumnName.add(ob1);

					cbt.createTable22("critical_patient_report", TableColumnName, connectionSpace);

					TableColumnName.clear();

					ob1 = new TableColumes();
					ob1.setColumnname("rid");
					ob1.setDatatype("int(10)");
					ob1.setConstraint("default 0");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_by");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("close_by_id");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_sms");
					ob1.setDatatype("varchar(160)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_mob");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("adm_doc_sms");
					ob1.setDatatype("varchar(160)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("adm_doc_mob");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					//

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_inform");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_comment");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("doc_reason");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("doc_inform");
					ob1.setDatatype("varchar(200)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("doc_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("doc_comment");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nurse_reason");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("close_by_name");
					ob1.setDatatype("varchar(40)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("comments");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_desig");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_close_by");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("esc_level");
					ob1.setDatatype("varchar(7)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("sn_upto_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("sn_upto_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_status");
					ob1.setDatatype("varchar(1)");
					ob1.setConstraint("default 0");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("escalate_to");
					ob1.setDatatype("varchar(200)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("escalate_to_mob");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("authorization");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_mob");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("reason");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("instruction");
					ob1.setDatatype("varchar(500)");
					ob1.setConstraint(" default 'NA'");
					TableColumnName.add(ob1);
					
					

					cbt.createTable22("critical_data_history", TableColumnName, connectionSpace);
				}

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertDataTxt = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertCommonData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				InsertDataTable obtxt = null;
				InsertDataTable ob2 = null;
				List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String currentDate = DateUtil.getCurrentDateUSFormat();
				String currentTime = DateUtil.getCurrentTime();
				String testname[] = null;
				//String testname_txt[] = null;
				String testvalue[] = null;
				String testunit[] = null;
				String testcomment[] = null;
				String[] testTypeCommon = null;
				String testType = null;
				boolean status = false;
				
				/*while(it.hasNext()){
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& Parmname != null
							&& (Parmname.equalsIgnoreCase("test_name_txt")))
					{
						testname_txt= request.getParameterValues(Parmname);
						
					}
					
				}*/
				
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
				 //System.out.println("Parmname : "+Parmname+"paramValue : "+paramValue);
					if (Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& Parmname != null
							&& (Parmname.equalsIgnoreCase("doc_name") || Parmname.equalsIgnoreCase("doc_mobile") || Parmname.equalsIgnoreCase("uhid") || Parmname.equalsIgnoreCase("caller_emp_id") || Parmname.equalsIgnoreCase("caller_emp_name") || Parmname.equalsIgnoreCase("caller_emp_mobile") || Parmname.equalsIgnoreCase("test_name")  || Parmname.equalsIgnoreCase("test_value")
									|| Parmname.equalsIgnoreCase("gender")	|| Parmname.equalsIgnoreCase("test_unit") || Parmname.equalsIgnoreCase("test_comment") || Parmname.equalsIgnoreCase("test_type")))
					{
						if (Parmname.equalsIgnoreCase("test_name"))
						{
							testname = request.getParameterValues(Parmname);
						}
					
						else if (Parmname.equalsIgnoreCase("test_value"))
						{
							testvalue = request.getParameterValues(Parmname);
						}
						else if (Parmname.equalsIgnoreCase("test_unit"))
						{
							testunit = request.getParameterValues(Parmname);
						}
						else if (Parmname.equalsIgnoreCase("test_comment"))
						{
							testcomment = request.getParameterValues(Parmname);
						}
						else if (Parmname.equalsIgnoreCase("test_type"))
						{
							testTypeCommon=request.getParameterValues("test_type");
							/*for(int i=0;i<testTypeCommon.length;i++){
								if (testTypeCommon[i].equalsIgnoreCase("1"))
									testTypeCommon[i] =   "Lab";
								if (testTypeCommon[i].equalsIgnoreCase("2"))
									testTypeCommon[i] = "Radiology";
							}*/
							
						/*	if (paramValue != null && paramValue.equalsIgnoreCase("1"))
								testType =   "Lab";
							if (paramValue != null && paramValue.equalsIgnoreCase("2"))
								testType = "Radiology";
						*/		
						}

						else
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(checkBlank(paramValue));
							insertCommonData.add(ob);

						}
					}
					if (Parmname != null && !Parmname.equalsIgnoreCase("")
							&& (Parmname.equalsIgnoreCase("patient_name") || Parmname.equalsIgnoreCase("patient_mob") || Parmname.equalsIgnoreCase("patient_status") || Parmname.equalsIgnoreCase("addmision_doc_name") || Parmname.equalsIgnoreCase("addmision_doc_mobile") || Parmname.equalsIgnoreCase("nursing_unit") || Parmname.equalsIgnoreCase("specialty") || Parmname.equalsIgnoreCase("uhid") || Parmname.equalsIgnoreCase("bed_no")))
					{

						ob2 = new InsertDataTable();
						ob2.setColName(Parmname);
						ob2.setDataName(checkBlank(paramValue));
						insertData1.add(ob2);
					}
				}

				// Insert patient data and get inserted id
				int id = cbt.insertDataReturnId("critical_patient_data", insertData1, connectionSpace);
				
				// Getting new ticket no. common
 				String ticketNo = getCriticalTicketNo(cbt, connectionSpace);
				if (ticketNo == null)
				{
					ticketNo = "CRC1000";
				}
			//	asdas
				
				insertData.clear();
				
				
				insertData.addAll(insertCommonData);
					
				ob = new InsertDataTable();
				ob.setColName("reg_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("patient_id");
				ob.setDataName(id);
				insertData.add(ob);

				// Getting time for escalation for l2
				String time = getEscalationTime("Critical", "OCC-Subdepartment", cbt, connectionSpace);
				// Getting new escalate date time
				String escalateDateTime = DateUtil.getAddressingDatetime(currentDate, currentTime, "00:00", time);

				ob = new InsertDataTable();
				ob.setColName("escalate_date");
				ob.setDataName(DateUtil.convertDateToUSFormat(escalateDateTime.split(" ")[0]));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalate_time");
				ob.setDataName(escalateDateTime.split(" ")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_date");
				ob.setDataName(currentDate);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_time");
				ob.setDataName(currentTime);
				insertData.add(ob);
 
				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("Level1");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("ticket_no");
				ob.setDataName(ticketNo);
				insertData.add(ob);

				/*ob = new InsertDataTable();
				ob.setColName("test_type");
				ob.setDataName(testType);
				insertData.add(ob);*/

				ob = new InsertDataTable();
				ob.setColName("dept");
				ob.setDataName("17");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Not Informed");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("specimen_no");
				ob.setDataName(request.getParameter("specimen_no"));
				insertData.add(ob);
				
				// Insert patient data and get inserted id
				int maxId = cbt.insertDataReturnId("critical_data", insertData, connectionSpace);
				
				// Add multiple Test Type
				for(int k=0;k<testTypeCommon.length  ;k++)
				{
					testType=testTypeCommon[k];
					if(testType.equalsIgnoreCase("-1") || testType.equalsIgnoreCase("No Data") ||testType.equalsIgnoreCase(""))
						continue;
				insertData.clear();
				ob = new InsertDataTable();
				ob.setColName("test_type");
				
				boolean chkTestType =  isInteger(testType);
				if(chkTestType){
					testType = testNameTypeSearch ("test_type", "test_type", testType, connectionSpace);
				}
				else{
					testType = testType;
				}
				
				ob.setDataName(testType);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("uhid");
				ob.setDataName(request.getParameter("uhid"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("patient_id");
				ob.setDataName(id);
				insertData.add(ob);

				if (testname.length > 0 )
				{
						
					
					//System.out.println("khaskhkahsdkshdkjsd    "+testname[k].length());
					InsertDataTable ob1 = new InsertDataTable();
					ob1.setColName("test_name");
					if(testname[k].length()==0){
					//if(testname[k].equalsIgnoreCase(""))
						String []arr=request.getParameterValues("test_name_txt");
					ob1.setDataName(arr[0]);
					}
					else{
						//System.out.println("TEST Name : "+testname[k]);
						
						boolean chkTestName =  isInteger(testname[k]);
						if(chkTestName){
							testName = testNameTypeSearch ("test_name", "test_name", testname[k], connectionSpace);
						}
						else{
							testName = testName;
						}
						
						
						ob1.setDataName(testName);
					}
					insertData.add(ob1);
					//System.out.println("TEST value : "+testvalue[k]);
					InsertDataTable ob3 = new InsertDataTable();
					ob3.setColName("test_value");
					ob3.setDataName(testvalue[k]);
					insertData.add(ob3);

					InsertDataTable ob4 = new InsertDataTable();
					ob4.setColName("test_unit");
					ob4.setDataName(testunit[k]);
					insertData.add(ob4);

					InsertDataTable ob5 = new InsertDataTable();
					ob5.setColName("test_comment");
					ob5.setDataName(testcomment[k]);
					insertData.add(ob5);

					cbt.insertIntoTable("critical_patient_report", insertData, connectionSpace);

				}
				}
			
				if (maxId > 0)
				{
					insertData.clear();

					ob = new InsertDataTable();
					ob.setColName("rid");
					ob.setDataName(maxId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Not Informed");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_date");
					ob.setDataName(currentDate);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_time");
					ob.setDataName(currentTime);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_by");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("esc_level");
					ob.setDataName("Level1");
					insertData.add(ob);

					status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);

				}

//				int count = 0;
//				for (int i = 0; i > testname.length; i++)
//				{
//					ob2 = new InsertDataTable();
//					ob2.setColName("uhid");
//					ob2.setDataName(maxId);
//					insertData.add(ob2);
//
//					ob2 = new InsertDataTable();
//					ob2.setColName("test_name");
//					ob2.setDataName(testname[i]);
//					insertData.add(ob2);
//
//					ob2 = new InsertDataTable();
//					ob2.setColName("test_value");
//					ob2.setDataName(testvalue[i]);
//					insertData.add(ob2);
//
//					ob2 = new InsertDataTable();
//					ob2.setColName("test_unit");
//					ob2.setDataName(testunit[i]);
//					insertData.add(ob2);
//
//					ob2 = new InsertDataTable();
//					ob2.setColName("test_comment");
//					ob2.setDataName(testcomment[i]);
//					insertData.add(ob2);
//					cbt.insertDataReturnId("critical_patient_report", insertData, connectionSpace);
//					count++;
//				}
				// for loop
				if (status)
				{
					addActionMessage("Ticket Lodge Successfully.");
				}
				else
				{
					addActionMessage("Oops!!! Some Error in Data.");
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionMessage("Oops!!! Some Error in Data.");
				return ERROR;
			}
			finally
			{
				lock.unlock();
			}
		}
		else
		{
			return LOGIN;
		}
	}

	private String testNameTypeSearch(String tableName, String columnName,
			String searchData, SessionFactory connectionSpace) {
		// TODO Auto-generated method stub
		String data = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query_test = new StringBuilder();
		query_test.append("select "+columnName+" from "+tableName+" where id='"+searchData+"'");
		List data1 = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
		if (data1 != null && data1.size() > 0)
		{
			data = data1.get(0).toString();
		}
		
		return data;
	}




	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		catch (NullPointerException e)
		{
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
	public String checkBlank(String value)
	{
		return ((value.equalsIgnoreCase("") || value == null) ? "NA" : value);
	}

	public String getEscalationTime(String priority, String subDept, CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		try
		{
			String esctm = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT l1Tol2 FROM critical_escalation AS red");
			query.append(" INNER JOIN subdepartment AS subDept ON subDept.id=red.escsubdept");
			query.append(" WHERE  subDept.subdeptname='" + subDept + "'");
			////System.out.println("::::::::::" + query);
			List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				esctm = list.get(0).toString();
				/*
				 * for (Iterator iterator = list.iterator();
				 * iterator.hasNext();) { Object object[] = (Object[])
				 * iterator.next(); if (object[0] != null && object[2] != null
				 * && object[2].toString().equalsIgnoreCase("0")) { esctm =
				 * DateUtil
				 * .getAddressingDatetime(DateUtil.getCurrentDateUSFormat(),
				 * DateUtil.getCurrentTime(), "00:00", object[0].toString());
				 * 
				 * } if (object[2] != null &&
				 * object[2].toString().equalsIgnoreCase("1") && object[0] !=
				 * null && object[1] != null && object[3] != null && object[4]
				 * != null) { if (object[0] != null && object[1] != null &&
				 * object[3] != null && object[4] != null &&
				 * DateUtil.checkTimeBetween2Times(object[3].toString(),
				 * object[4].toString(), DateUtil.getCurrentTime())) { esctm =
				 * DateUtil
				 * .getAddressingDatetime(DateUtil.getCurrentDateUSFormat(),
				 * "00:00", "00:00", object[1].toString()); } else { esctm =
				 * DateUtil
				 * .getAddressingDatetime(DateUtil.getCurrentDateUSFormat(),
				 * DateUtil.getCurrentTime(), "00:00", object[0].toString());
				 * 
				 * } }
				 * 
				 * }
				 */
				return esctm;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "00:00";
	}

	// get list for dept
	public String getAlldept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try
			{
				commonJSONArray = new JSONArray();

				List departmentlist = getServiceDept_SubDept("CRC", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{

							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("dept", object[1].toString());
							commonJSONArray.add(innerobj);
						}
					}
				}

				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}

	@SuppressWarnings("rawtypes")
	private String getCriticalTicketNo(CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		List list = cbt.executeAllSelectQuery("SELECT ticket_no FROM critical_data ORDER BY id DESC LIMIT 1", connectionSpace);
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				return "CRC" + (Integer.parseInt(object.toString().substring(3)) + 1);
			}
		}
		return null;
	}

	public List getServiceDept_SubDept(String module, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		/*
		 * SELECT DISTINCT dept.id, dept.deptName FROM department AS dept INNER
		 * JOIN subdepartment AS subdept ON subdept.deptid=dept.id INNER JOIN
		 * compliance_contacts AS cc ON cc.fromDept_id=dept.id WHERE
		 * cc.moduleName="ORD"
		 */

		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try
		{
			if (module.equalsIgnoreCase("CRC"))
			{
				qry.append("SELECT DISTINCT dept.id, dept.deptName FROM department AS dept");
				qry.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id");
				qry.append(" INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id");
				qry.append(" WHERE cc.moduleName='" + module + "'");
			}

			else
			{
				qry.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append("  ORDER BY dept.deptName ASC");
			}
			// ////System.out.println("qry "+qry);
			Session session = null;
			Transaction transaction = null;
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();

		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dept_subdeptList;

	}

	public String getEmployeebydept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				commonJSONArray = new JSONArray();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id, emp.empName,contact.level,emp.empLogo,subdept.subdeptname FROM compliance_contacts AS contact");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = contact.forDept_id");
				query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
				query.append(" WHERE contact.moduleName='CRC' AND  contact.fromDept_Id='" + dataFor + "'");
				if (searchString != null && !searchString.equalsIgnoreCase(""))
				{
					query.append(" and (emp.empName like '%" + searchString + "%' OR contact.level like '%" + searchString + "%' OR subdept.subdeptname like '%" + searchString + "%' )");
				}
				query.append(" group by emp.id");
				//System.out.println("Shift Wise List For employee qry::" + query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[3] != null)
							{
								innerobj.put("logo", object[3].toString());
							}
							else
							{
								innerobj.put("logo", "images/noImage.JPG");
							}
							commonJSONArray.add(innerobj);
						}
					}
				}

				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getCriticalDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				commonJSONArray = new JSONArray();
				StringBuffer query1 = new StringBuffer("SELECT  id, critical_No FROM critical_master ");
				List dataList1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataList1 != null && dataList1.size() > 0)
				{
					for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
					{
						query1.delete(0, query1.length());
						Map<String, List> innerobj = new HashMap<String, List>();
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{

							query1.append("SELECT  id, critical_No FROM critical_master ");
							List dataList2 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
							int i = 0;
							if (dataList2 != null && dataList2.size() > 0)
							{
								List tempList = new ArrayList();
								for (Iterator iterator1 = dataList2.iterator(); iterator1.hasNext();)
								{
									Map<String, String> innerobj1 = new HashMap<String, String>();
									Object[] object2 = (Object[]) iterator1.next();
									if (object2[0] != null && object2[1] != null)
									{
										innerobj1.put("wingid", object2[0].toString());
										innerobj1.put("wingname", object2[1].toString());
										tempList.add(innerobj1);
									}
								}
								innerobj.put(object[1].toString(), tempList);
								commonJSONArray.add(innerobj);
							}
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	public String fetchDoctorName()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			JSONObject job = null;
			commonJSONArray = new JSONArray();
			String query = "select name,name as doctor from referral_doctor group by name order by name ";
			////System.out.println("query "+query);
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				
				for (Object obj : data)
				{
					job = new JSONObject();
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						
						job.put("id", ob[0].toString());
						job.put("name", ob[0].toString());
						commonJSONArray.add(job);
					}
				}

			}
			
			

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return SUCCESS;
	}
	public String fetchSpeciality()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			JSONObject job = null;
			commonJSONArray = new JSONArray();
			String query = "select spec as spec_name ,spec from referral_doctor where name='"+docName+"' group by name order by name ";
			////System.out.println("query "+query);
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				
				for (Object obj : data)
				{
					job = new JSONObject();
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						
						job.put("name", ob[0].toString());
						job.put("name", ob[1].toString());
						commonJSONArray.add(job);
					}
				}

			}
			
			

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return SUCCESS;
	}

	
	
	
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
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

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public void setUhidStatus(String uhidStatus) {
		this.uhidStatus = uhidStatus;
	}

	public String getUhidStatus() {
		return uhidStatus;
	}


	public List<GridDataPropertyView> getViewPageUHIDColumns() {
		return viewPageUHIDColumns;
	}

	public void setViewPageUHIDColumns(
			List<GridDataPropertyView> viewPageUHIDColumns) {
		this.viewPageUHIDColumns = viewPageUHIDColumns;
	}

	public void setViewCritical(List<Object> viewCritical) {
		this.viewCritical = viewCritical;
	}

	public List<Object> getViewCritical() {
		return viewCritical;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}




	public String getDocName() {
		return docName;
	}




	public void setDocName(String docName) {
		this.docName = docName;
	}




	public String getCaller_emp_id()
	{
		return caller_emp_id;
	}




	public void setCaller_emp_id(String callerEmpId)
	{
		caller_emp_id = callerEmpId;
	}



	

}
