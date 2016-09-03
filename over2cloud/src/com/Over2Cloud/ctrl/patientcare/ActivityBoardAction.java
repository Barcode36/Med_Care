package com.Over2Cloud.ctrl.patientcare;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.Age;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class ActivityBoardAction extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;

	private String headingName = "";
	private List<ConfigurationUtilBean> pageFieldsColumns;
	private List<Object> masterViewProp;
	private List<GridDataPropertyView> masterViewPropCPS;
	private String serviceName = null;
	private List<ConfigurationUtilBean> servicePageFieldsColumns;
	private List<Object> cpsGridData;
	private List<ConfigurationUtilBean> addFields;
	private Map<String, String> acManagerMap;
	private Map<String, String> serviceMap;
	private JSONArray jsonArray;
	private JSONArray servicelocation;
	private JSONArray service;
	private String service_id;
	private String patient_details;
	private Map<String, String> stateMap = null;
	private String stateId;
	private Map<String, String> serviceManagerMap;
	private Map<String, String> bedType, paymenetType;
	private Map<String, String> SpecMap;
	private Map<String, String> dataMap;
	private JSONArray jsonArr = null;
	private String madLoc = "";
	private String pack = "";
	private String pefTime = "";
	private String spec = "";
	private String date = "";
	private String uhid = "";
	private String Loc = "";
	private ArrayList<ArrayList<String>> cpsHisList;

	private Map<String, String> patienceMap = null;
	private String id;
	private String minDateValue;
	private String maxDateValue;
	private String taskType;
	private List<String> dataList;
	private String feed_status;
	private String status_type;
	private String status;
	private String corp_name;
	private Map<String, String> corpMap;
	private Map<String, String> corpNameMap;
	private Map<String, String> feedList;
	private Map<String, String> serviceeMap;
	private Map<String, String> accountMap;
	private Map<String, String> locationMap;
	private Map<String, String> serviceMng;
	private String patienceSearch;
	private String wildSearch;
	private String services;
	private String ac_manager;
	private String med_location, pat_mobile, pat_name;
	private String searchData;
	private String DOB;
	private String service_manager;
	private String keyExist, valueExist;
	private String keyExistSec, valueExistSec;
	private String dataFor = "NA";
	private List commondata;
	private String servicesID;
	private Map<String, String> modality;
	CommonOperInterface cbt = new CommonConFactory().createInterface();

	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
		} catch (NumberFormatException e)
		{
			return false;
		} catch (NullPointerException e)
		{
			return false;
		}
		return true;
	}

	public String fetchAccountManagerRoaster()
	{
		try
		{
			String location_name = null;
			StringBuilder sb = new StringBuilder();
			sb.append(" select emp.id, emp.empName from cps_roster_config as crc ");
			sb.append(" INNER JOIN employee_basic AS emp ON emp.id=crc.employee ");
			boolean chkTestName = isInteger(id.trim());
			if (chkTestName == true)
			{
				sb.append(" where crc.location='" + id.trim() + "' and crc.day" + date.trim() + " like '%" + services + "%' ");
			} else
			{
				// System.out.println("mmmm ");
				location_name = new CPSHelper().fetchLocationID(id.trim(), connectionSpace);
				sb.append(" where crc.location='" + location_name + "' and crc.day" + date.trim() + " like '%" + services + "%' ");
			}

			// System.out.println(">>>>>>>>>>>>>>>>>>. " + sb);
			List data = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
			JSONObject job = null;
			jsonArray = new JSONArray();
			if (data != null && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("id", ob[0].toString());
						job.put("Name", ob[1].toString());

					}
					jsonArray.add(job);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String beforeActivityBoardView()
	{
		String returnString = ERROR;
		if (!ValidateSession.checkSession())
			return LOGIN;
		try
		{
			maxDateValue = DateUtil.getDateAfterDays(7);
			minDateValue = DateUtil.getCurrentDateUSFormat();
			DropdownSet(maxDateValue, minDateValue);
			System.out.println(">>>>>>>>>>>>>>>>>>> " + userEmpId);
			String empName = CPSHelper.empNameGet(userEmpId, connectionSpace).split("#")[0];
			String empNameDepartment = CPSHelper.empNameGetServiceMng(userEmpId, connectionSpace);
			String department = empNameDepartment.split("#")[2];
			String departmentLevel = empNameDepartment.split("#")[3];

			// keyExistSec, valueExistSec
			String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
			String value = null;
			List adminRights = cbt.executeAllSelectQuery("select linkVal from useraccount where name = '" + empid + "'", connectionSpace);
			if (adminRights != null && adminRights.size() > 0)
			{
				value = adminRights.get(0).toString();
			}
			if (value != null && value.contains("accMng_VIEW"))
			{
				keyExistSec = CPSHelper.empNameGet(userEmpId, connectionSpace).split("#")[0];
				valueExistSec = CPSHelper.empNameGet(userEmpId, connectionSpace).split("#")[0];
			} else
			{
				keyExistSec = "-1";
				valueExistSec = "A/C Manager";
			}
			if (department != null && department.equalsIgnoreCase("Patient Care") && departmentLevel.equalsIgnoreCase("1"))
			{
				keyExist = userEmpId;
				valueExist = empName;
			} else
			{
				keyExist = "-1";
				valueExist = "Service Mng.";
			}
			returnString = SUCCESS;
		} catch (Exception e)
		{
			// TODO: handle exception
			returnString = ERROR;
			e.printStackTrace();
		}
		return returnString;
	}

	private String getServiceName(String serviceId, SessionFactory connection)
	{
		String serviceName = "";
		try
		{
			StringBuilder empuery = new StringBuilder();
			empuery.append(" select service_name from cps_service where id='" + serviceId + "' ");
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			serviceName = escData.get(0).toString();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return serviceName;
	}

	// Send Patient Appointment Alert
	public String sendPatientAlert(String flag, String patientName, String patientMob, String service, String appoinmentTime, String location, String drName, String pre_time, SessionFactory connection, String serviceMnger)
	{

		// System.out.println(flag+"     patientMob "+patientMob+"     service "+service+"   appoinmentTime "+appoinmentTime+"    location "+location+"   drName "+drName+"   pre_time "+pre_time);

		if (appoinmentTime.contains("AM") || appoinmentTime.contains("PM"))
		{
			appoinmentTime = appoinmentTime.split(" ")[0] + " at " + DateUtil.getcorrecctTime(appoinmentTime.split(" ")[1])+" "+appoinmentTime.split(" ")[2];
		} else
		{
			appoinmentTime = appoinmentTime.split(" ")[0] + " at " + timeFormatChange(DateUtil.getcorrecctTime(appoinmentTime.split(" ")[1]));
		}

		CommunicationHelper ch = new CommunicationHelper();
		String Msg = "NA";
		CPSHelper cpsHelp = new CPSHelper();
		if (patientMob != null && !patientMob.isEmpty())
		{
			String Name = null;
			String serviceName = null;

			String reScheduleDrName = null;
			String reScheduleAppoinmentTime = null;
			String reScheduleLocation = null;

			if (flag.equalsIgnoreCase("appointment"))
			{
				boolean chkTestName = isInteger(service.trim());
				if (chkTestName == true)
				{
					serviceName = getServiceName(service, connection);
				} else
				{
					serviceName = service;
				}
			}

			else if (flag.equalsIgnoreCase("cancel"))
				serviceName = service;
			else
				serviceName = service;
			if (serviceName != null && serviceName.equalsIgnoreCase("OPD"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("opd_doc");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment with " + Name + " is fixed at " + location + " on " + appoinmentTime + ". Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " is fixed at " + location + " on " + appoinmentTime + " has been CANCELED. To book a new appointment. please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleDrName = request.getParameter("opd_doc");
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " is fixed at " + location + " on " + appoinmentTime + " has been Re-Scheduled with " + reScheduleDrName + " on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + " ,Please call +91 124 4855202.\nTeam Medanta";
				}

				ch.addMessage(patientName, "AUTO", patientMob, Msg, " ", "Pending", "0", "CPS", connection);
			}
			if (serviceName != null && serviceName.equalsIgnoreCase("EHC"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("ehc_pack");
				if (Name.equalsIgnoreCase("NA"))
				{
					Name = "-";
				}
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment for health check-up is fixed at " + location + " on " + appoinmentTime + ". Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on" + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for health check-up is fixed at " + location + " on " + appoinmentTime + " has been CANCELED. To book a new appointment. please call +91 124 4855202.\n Team Medanta";
				else
				{
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");

					//
					/*
					 * Dear <Patient>, Your appointment for<EHC> at
					 * <Location>location on <Booked date and time> has been
					 * Re-Scheduled with <reschedule Doctor Name> on <Reschedule
					 * date time> at <Location> location, Please call +91 124
					 * 4855202. Team Medanta
					 */

					Msg = "Dear " + patientName + ",\n Your appointment for health check-up is fixed at " + location + ", on " + appoinmentTime + " has been Re-Scheduled  on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ".Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on" + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + ", for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				}
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
			}
			if (serviceName != null && serviceName.equalsIgnoreCase("Radiology"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					// Name=request.getParameter("remarks");

					Name = request.getParameter("radio_mod");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + Name + " has been fixed at " + location + " on " + appoinmentTime + ".Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " at " + location + " on " + appoinmentTime + " has been CANCELED. for any further assistance please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " procedure at " + location + " on " + appoinmentTime + " has been Re-Scheduled  on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ".Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you,for more assistance, please call +91 124 4855202.\nTeam Medanta ";
				}
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
			}
			if (serviceName != null && serviceName.equalsIgnoreCase("Facilitation"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					// Name=request.getParameter("remarks");

					// "+ cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0]+"
					// ("+cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1]+"
					// )

					Name = request.getParameter("facilitation_mod");
				if (flag.equalsIgnoreCase("appointment"))
					// Msg = "Dear " + patientName +
					// ",\n Your request has been noted at "+location+" on "+appoinmentTime+", your "+
					// CPSHelper.empNameGet(serviceMnger,
					// connectionSpace).split("#")[0]+" reachable at "+CPSHelper.empNameGet(serviceMnger,
					// connectionSpace).split("#")[1]+"  will assist you. For further assistance please call +91 124 4855202.\nTeam Medanta.";
					Msg = "Dear " + patientName + ",\n Your appointment for " + Name + " is fixed at " + location + " on " + appoinmentTime + ".Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you,for more assistance, please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " procedure at " + location + " on " + appoinmentTime + " has been CANCELED.To book a new appointment. please call +91 124 4855202.\nTeam Medanta";
				/*
				 * else { reScheduleAppoinmentTime = pre_time;
				 * reScheduleLocation = request.getParameter("location");
				 * 
				 * 
				 * Dear <Patient Name> Your request has been noted at <Location>
				 * location on <Appointment Booked date time>, your<Service
				 * manager> reachable at <Service Manager Mobile No> will assist
				 * you. For further assistance please call +91 124 4855202. Team
				 * Medanta
				 * 
				 * Msg = "Dear " + patientName +
				 * ",\n Your request has been noted at "
				 * +location+" on "+appoinmentTime+
				 * ", your<Service manager> reachable at <Service Manager Mobile No> will assist you. For further assistance please call +91 124 4855202.\nTeam Medanta"
				 * ; //Msg = "Dear " + patientName + ", Your appointment for " +
				 * drName + " procedure at " + location + " location on " +
				 * appoinmentTime + " has been Re-Scheduled  on " +
				 * reScheduleAppoinmentTime + "  at " + reScheduleLocation +
				 * " location,Please call +91 124 4855202.Team Medanta"; }
				 */
				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
				}
			}

			if (serviceName != null && serviceName.equalsIgnoreCase("Telemedicine"))
			{

				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					// Name=request.getParameter("remarks");
					Name = request.getParameter("telemedicine_mod");
				if (flag.equalsIgnoreCase("appointment"))

					Msg = "Dear " + patientName + ",\n Your appointment for " + Name + " is fixed at " + location + "  on " + appoinmentTime + ". Please reach  20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";

				// Msg = "Dear " + patientName +
				// ", Your appointment for " +
				// Name + " has been fixed at " + location +
				// " location on " +
				// appoinmentTime +
				// ".Please reach  20 min early for registration,for more assistance.please call +91 124 4855202.Team Medanta";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " procedure at " + location + "  on " + appoinmentTime + " has been CANCELED.To book a new appointment. please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " procedure at " + location + " on " + appoinmentTime + " has been Re-Scheduled  on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ", for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				}
				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
				}
			}
			
			if (serviceName != null && serviceName.equalsIgnoreCase("Diagnostics"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("diagnostics_test");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + Name + " fixed at " + location + " on " + appoinmentTime + ".Please reach  20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you,for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " test at " + location + " on " + appoinmentTime + " has been CANCELED.To book a new appointment,please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " test at " + location + " on " + appoinmentTime + " has been Re-Scheduled  on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ", Please call +91 124 4855202.\nTeam Medanta";
				}
				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
				}
			}
			if (serviceName != null && serviceName.equalsIgnoreCase("Laboratory"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					// Name=request.getParameter("lab_mod");
					Name = request.getParameter("service_remark");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment for Laboratory is fixed at " + location + " on " + appoinmentTime + ".Please reach  20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment for Laboratory is fixed at " + location + " on " + appoinmentTime + " has been CANCELED.To book a new appointment,please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " test at " + location + " on " + appoinmentTime + " has been Re-Scheduled  on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ",Please call +91 124 4855202.\nTeam Medanta";
				}
				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, "", "Pending", "0", "CPS", connection);
				}
			}
			// IPD Service Message Genrate
			if (serviceName != null && serviceName.equalsIgnoreCase("IPD"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("ipd_doc");
				if (flag.equalsIgnoreCase("appointment"))

					/*
					 * Dear <Patient Name>, Youradmission at <Location> on
					 * <Appointment Booked date > is being processed .Please
					 * report to the admissions counter at the UG floor for
					 * registration, SERVICE MANAGER LINE TO COME.For further
					 * assistance please call +91 124 4855202. Team Medanta
					 */

					// Msg = "Dear " + patientName +
					// ",\n Your admission at " +
					// location + " on " + appoinmentTime +
					// " is being processed .Please report to the admissions counter at the UG floor for registration, SERVICE MANAGER LINE TO COME.For further assistance please call +91 124 4855202.\nTeam Medanta";
					Msg = "Dear " + patientName + ",\n Your appointment with  " + Name + " is fixed at " + location + " on " + appoinmentTime + ".Please reach  20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance,please call +91 124 4855202.\nTeam Medanta ";
				// Msg = "Dear " + patientName +
				// ", Your appointment with " +
				// Name + " has been fixed at " + location +
				// " location on " +
				// appoinmentTime + ".Please reach the " + serviceName +
				// " 20 min early for registration & vitals check. Carry your recent reports,priscriptions & medicines,please call +91 124 4855202 for any assistance.Team Medanta";
				else if (flag.equalsIgnoreCase("cancel"))

					// Your admission is cancelled at " + location + ".
					// In case
					// you want to rescheduled your admission please
					// call +91
					// 124 4855202 or contact Service manager
					// "+CPSHelper.empNameGet(serviceMnger, connectionSpace).split("#")[1]+".";
					Msg = "Your admission is cancelled at " + location + ". In case you want to rescheduled your admission please call +91 124 4855202 or contact Service manager " + CPSHelper.empNameGet(serviceMnger, connectionSpace).split("#")[1] + ".";
				else
				{
					reScheduleDrName = request.getParameter("ipd_doc");
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your appointment for " + drName + " at " + location + " on " + appoinmentTime + " has been Re-Scheduled with " + reScheduleDrName + " on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ", Please call +91 124 4855202.\nTeam Medanta";
				}
				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, " ", "Pending", "0", "CPS", connection);
				}
			}
			// DayCare Service Message Genrate
			if (serviceName != null && serviceName.equalsIgnoreCase("DayCare"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("daycare_doc");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\n Your appointment with " + Name + " is fixed at " + location + " on " + appoinmentTime + ".Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you, for more assistance, please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your appointment with " + Name + " at " + location + " on " + appoinmentTime + " has been CANCELED.To book a new appointment. please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleDrName = request.getParameter("daycare_doc");
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");

					// Dear <Patient>, your appointment with <Doctor
					// Name> at
					// <Location>location on <Booked date and time>
					// has been
					// Re-Scheduled with <reschedule Doctor Name> on
					// <Reschedule
					// date time> at <Location> location,Please call
					// +91 124
					// 4855202.Team Medanta
					if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
					Msg = "Dear " + patientName + ",\n Your appointment with " + Name + " at " + location + " on " + appoinmentTime + " has been Re-Scheduled with " + reScheduleDrName + " on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ", Please call +91 124 4855202.\nTeam Medanta";
					}
					}

				ch.addMessage(patientName, "AUTO", patientMob, Msg, " ", "Pending", "0", "CPS", connection);
			}
			// Emergency Service Message Genrate
			if (serviceName != null && serviceName.equalsIgnoreCase("Emergency"))
			{
				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("em_spec_assis");
				if (flag.equalsIgnoreCase("appointment"))
					Msg = "Dear " + patientName + ",\nYou request for Emergency is fixed at " + location + " on " + appoinmentTime + ". Please reach 20 min early for registration, your service manager " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " reachable on " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " will assist you,for more assistance, please call +91 124 4855202.\nTeam Medanta ";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\nYou request for Emergency at " + location + " on " + appoinmentTime + " has been CANCELED. To book a new appointment. please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleDrName = request.getParameter("em_spec_assis");
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n You request for " + drName + " at " + location + " on " + appoinmentTime + " has been Re-Scheduled with " + reScheduleDrName + " on " + reScheduleAppoinmentTime + "  at " + reScheduleLocation + ", Please call +91 124 4855202.\nTeam Medanta";
				}

				// ch.addMessage(patientName, "AUTO", patientMob, Msg,
				// " ",
				// "Pending", "0", "CPS", connection);
			}
			// New Information Request Service Message Genrate
			if (serviceName != null && serviceName.equalsIgnoreCase("New Information Request"))
			{

				if (flag.equalsIgnoreCase("appointment") && !drName.equalsIgnoreCase(""))
					Name = drName;
				else
					Name = request.getParameter("service_remark");
				if (flag.equalsIgnoreCase("appointment"))

					Msg = "Dear " + patientName + ",\n your request has been received our patient executive " + CPSHelper.empNameGet(serviceMnger, connectionSpace).split("#")[0] + " reachable at " + CPSHelper.empNameGet(serviceMnger, connectionSpace).split("#")[1] + " will reach out to you to address the same, please call +91 124 4855202 for assistance.\n Team Medanta";
				// Msg = "Dear " + patientName + ", Your request for " +
				// Name +
				// " has been fixed at " + location + " location on " +
				// appoinmentTime + ".Please reach the " + serviceName +
				// " 20 min early for registration & vitals check. Carry your recent reports,priscriptions & medicines,please call +91 124 4855202 for any assistance.Team Medanta";
				else if (flag.equalsIgnoreCase("cancel"))
					Msg = "Dear " + patientName + ",\n Your request for " + drName + " at " + location + " on " + appoinmentTime + " has been CANCELED. To book a new appointment. Please call +91 124 4855202.\nTeam Medanta";
				else
				{
					reScheduleDrName = request.getParameter("service_remark");
					reScheduleAppoinmentTime = pre_time;
					reScheduleLocation = request.getParameter("location");
					Msg = "Dear " + patientName + ",\n Your request for " + drName + " at " + location + " on " + appoinmentTime + " has been Re-Scheduled with " + reScheduleDrName + " on " + reScheduleAppoinmentTime + " at " + reScheduleLocation + ", Please call +91 124 4855202.\n Team Medanta";
				}

				if (Msg!=null && Msg.length() > 10 && !Msg.equalsIgnoreCase("NA")){
				ch.addMessage(patientName, "AUTO", patientMob, Msg, " ", "Pending", "0", "CPS", connection);
				}
			}
		}
		return Msg;
	}

	public String dynamicDDcall()
	{

		String returnString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				String str[] = getMinDateValue().split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					minDateValue = getMinDateValue();
					maxDateValue = getMaxDateValue();
				} else
				{
					minDateValue = DateUtil.convertDateToUSFormat(getMinDateValue());
					maxDateValue = DateUtil.convertDateToUSFormat(getMaxDateValue());
				}
				jsonArr = new JSONArray();
				JSONArray arr1 = new JSONArray();
				JSONArray arr2 = new JSONArray();
				JSONArray arr3 = new JSONArray();
				JSONArray arr4 = new JSONArray();
				JSONArray arr5 = new JSONArray();
				JSONArray arr6 = new JSONArray();
				JSONObject ob1 = null;
				JSONObject ob2 = null;
				JSONObject ob3 = null;
				JSONObject ob4 = null;
				JSONObject ob5 = null;
				JSONObject ob6 = null;
				List l1 = ddCorpName(minDateValue, maxDateValue);
				List l2 = ddServiceManagerListName(minDateValue, maxDateValue);
				List l3 = ddServiceListName(minDateValue, maxDateValue);
				List l4 = ddLocationName(minDateValue, maxDateValue);
				List l5 = ddAccountManager(minDateValue, maxDateValue);
				List l6 = ddStatus(minDateValue, maxDateValue);
				if (l1 != null && l1.size() > 0)
				{
					for (Iterator iterator1 = l1.iterator(); iterator1.hasNext();)
					{
						Object[] object = (Object[]) iterator1.next();
						if (object != null)
						{
							ob1 = new JSONObject();
							ob1.put("id", getValueWithNullCheck(object[0]));
							ob1.put("name", getValueWithNullCheck(object[1]));
							arr1.add(ob1);
						}
					}
				}
				if (l2 != null && l2.size() > 0)
				{
					for (Iterator iterator = l2.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							ob2 = new JSONObject();
							ob2.put("id", object[0].toString());
							ob2.put("name", object[1].toString());
							arr2.add(ob2);
						}
					}
				}

				if (l3 != null && l3.size() > 0)
				{
					for (Iterator iterator = l3.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob3 = new JSONObject();
							ob3.put("name", object.toString());
							arr3.add(ob3);
						}
					}
				}
				if (l4 != null && l4.size() > 0)
				{
					for (Iterator iterator = l4.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob4 = new JSONObject();
							ob4.put("name", object.toString());
							arr4.add(ob4);
						}
					}
				}
				if (l5 != null && l5.size() > 0)
				{
					for (Iterator iterator = l5.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob5 = new JSONObject();
							ob5.put("name", object.toString());
							arr5.add(ob5);
						}
					}
				}
				if (l6 != null && l6.size() > 0)
				{
					for (Iterator iterator = l6.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							ob6 = new JSONObject();
							ob6.put("name", "All Status");
							ob6.put("name", object.toString());
							arr6.add(ob6);
						}
					}
				}
				jsonArr.add(arr1);
				jsonArr.add(arr2);
				jsonArr.add(arr3);
				jsonArr.add(arr4);
				jsonArr.add(arr5);
				jsonArr.add(arr6);
				returnString = SUCCESS;
			} catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return returnString;
	}

	public List ddCorpName(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			String query = null;
			query = null;
			// query =
			// "select corp_type from corporate_patience_data where date between '"
			// + minDate + "' and '" + maxDate + "' group by corp_type";
			query = "SELECT cpd.corp_name ,cm.customer_name from corporate_patience_data as cpd left join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name  where cpd.date between '" + minDate + "' and '" + maxDate + "' group by cm.customer_name";
			// System.out.println("vvv "+query);
			data = cbt.executeAllSelectQuery(query, connectionSpace);
			// returnString=SUCCESS;
		} catch (Exception e)
		{
			// returnString = ERROR;
			e.printStackTrace();
		}
		return data;

	}

	public List ddServiceManagerListName(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			String query = null;
			query = null;
			// query =
			// "select feed_status from  corporate_patience_data where date between '"
			// + minDate + "' and '" + maxDate + "' group by feed_status ";
			query = " select cpd.service_manager, emp.empName from corporate_patience_data as cpd  inner join employee_basic as emp on emp.id =  cpd.service_manager where cpd.date between '" + minDate + "' and '" + maxDate + "' group by emp.empName";
			data = cbt.executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;

	}

	public List ddServiceListName(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			StringBuilder queryyEnd = new StringBuilder();
			queryyEnd.append(" select  csm.service_name from corporate_patience_data as cpd ");
			queryyEnd.append(" left join cps_service as csm on csm.id=cpd.services where cpd.date between '" + minDate + "' and '" + maxDate + "' group by cpd.services ");
			data = cbt.executeAllSelectQuery(queryyEnd.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public List ddLocationName(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			StringBuilder queryyEnd = new StringBuilder();
			queryyEnd.append(" select  med_location from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' group by med_location");
			data = cbt.executeAllSelectQuery(queryyEnd.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public List ddAccountManager(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			StringBuilder queryyEnd = new StringBuilder();
			queryyEnd.append(" select  ac_manager from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' group by ac_manager");
			data = cbt.executeAllSelectQuery(queryyEnd.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public List ddStatus(String minDate, String maxDate)
	{

		List data = null;
		try
		{
			StringBuilder queryyEnd = new StringBuilder();
			queryyEnd.append(" select  status from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' group by status");
			data = cbt.executeAllSelectQuery(queryyEnd.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public String DropdownSet(String maxDate, String minDate)
	{
		// ////////System.out.println("test");
		String returnString = ERROR;
		if (!ValidateSession.checkSession())
			return LOGIN;
		try
		{
			String query = null;
			StringBuilder queryEnd = new StringBuilder();
			List data = null;

			feedList = new LinkedHashMap<String, String>();
			corpMap = new LinkedHashMap<String, String>();
			corpNameMap = new LinkedHashMap<String, String>();
			serviceeMap = new LinkedHashMap<String, String>();
			accountMap = new LinkedHashMap<String, String>();
			locationMap = new LinkedHashMap<String, String>();
			serviceMng = new LinkedHashMap<String, String>();
			query = "select id, feed_status from  corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' group by feed_status ";
			data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						feedList.put(object[1].toString(), object[1].toString());
					}

				}
			}
			query = null;
			data = null;
			query = "select status, status as stat from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' group by status";
			data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						corpMap.put("All Status", "All Status");
						corpMap.put(object[1].toString(), object[1].toString());
					}

				}
			}
			query = null;
			data = null;

			queryEnd.append(" select  cp.id,  cm.customer_name from corporate_patience_data as cp ");
			queryEnd.append(" left join cps_customer_his as cm on cm.id=cp.corp_name where cp.date between '" + minDate + "' and '" + maxDate + "' group by cp.corp_name");
			data = cbt.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						corpNameMap.put(object[1].toString(), object[1].toString());
					}

				}
			}
			queryEnd = null;
			StringBuilder queryyEnd = new StringBuilder();
			data = null;

			queryyEnd.append(" select  cpd.id,  csm.service_name from corporate_patience_data as cpd ");
			queryyEnd.append(" left join cps_service as csm on csm.id=cpd.services where cpd.date between '" + minDate + "' and '" + maxDate + "' group by cpd.services ");
			data = cbt.executeAllSelectQuery(queryyEnd.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						serviceeMap.put(object[1].toString(), object[1].toString());
					}

				}
			}
			queryyEnd = null;
			StringBuilder queryyEndd = new StringBuilder();
			data = null;

			// queryyEndd.append(" select acm.id,acm.ACCOUNT_MANAGER_NAME from corporate_patience_data as cpd right join cps_billinggroup_his as acm on acm.id=cpd.ac_manager  where cpd.date between '"
			// + minDate + "' and '" + maxDate +
			// "' GROUP by acm.ACCOUNT_MANAGER_NAME order by acm.ACCOUNT_MANAGER_NAME  ");
			String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
			String value = null;
			List adminRights = cbt.executeAllSelectQuery("select linkVal from useraccount where name = '" + empid + "'", connectionSpace);
			if (adminRights != null && adminRights.size() > 0)
			{
				value = adminRights.get(0).toString();
			}
			if (value != null && value.contains("accMng_VIEW"))
			{
				queryyEndd.append(" select ac_manager as account,ac_manager from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' and ac_manager='Credihealth' GROUP by ac_manager order by ac_manager  ");
			} else
			{
				queryyEndd.append(" select ac_manager as account,ac_manager from corporate_patience_data where date between '" + minDate + "' and '" + maxDate + "' GROUP by ac_manager order by ac_manager  ");
			}

			// System.out.println("queryyEndd "+queryyEndd);
			data = cbt.executeAllSelectQuery(queryyEndd.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						accountMap.put(object[1].toString(), object[1].toString());
					}

				}
			}

			StringBuilder queryyyEndd = new StringBuilder();
			data = null;

			queryyyEndd.append(" select cpd.med_location as loc,  cpd.med_location from corporate_patience_data as cpd  where cpd.date between '" + minDate + "' and '" + maxDate + "' group by cpd.med_location");
			data = cbt.executeAllSelectQuery(queryyyEndd.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						locationMap.put(object[1].toString(), object[1].toString());
					}

				}
			}

			queryyyEndd.setLength(0);
			data = null;

			queryyyEndd.append(" select cpd.service_manager, emp.empName from corporate_patience_data as cpd inner join employee_basic as emp on emp.id =  cpd.service_manager   where cpd.date between '" + minDate + "' and '" + maxDate + "'  group by cpd.service_manager");
			data = cbt.executeAllSelectQuery(queryyyEndd.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						serviceMng.put(object[0].toString(), object[1].toString());
					}

				}
			}

			returnString = SUCCESS;

		}

		catch (Exception e)
		{
			returnString = ERROR;
			e.printStackTrace();
		}

		return returnString;
	}

	public String beforeCPSView()
	{
		String returnString = ERROR;
		if (!ValidateSession.checkSession())
			return LOGIN;
		try
		{
			setGridViewProp("mapped_corporate_patient_configuration", "corporate_patient_configuration");

			returnString = SUCCESS;
		} catch (Exception e)
		{
			returnString = ERROR;
			e.printStackTrace();
		}

		return returnString;
	}

	public void setGridViewProp(String table1, String table2)
	{
		masterViewPropCPS = new ArrayList<GridDataPropertyView>();

		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		gpv.setWidth(65);
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket");
		gpv.setHeadingName("Ticket ID");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setFormatter("viewHistory");
		gpv.setWidth(65);
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("Timer");
		gpv.setHeadingName("Timer");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setFormatter("colorCode");
		gpv.setWidth(35);
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setFormatter("levelCode");
		gpv.setWidth(40);
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("uhid");
		gpv.setHeadingName("UHID");
		gpv.setHideOrShow("false");
		gpv.setWidth(40);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_name");
		gpv.setHeadingName("Patient Name");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setFormatter("viewPatientHistory");
		gpv.setWidth(100);
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("services");
		gpv.setHeadingName("Service");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("service_manager");
		gpv.setHeadingName("Service Mng.");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("med_location");
		gpv.setHeadingName("Medanta Location");
		gpv.setHideOrShow("false");
		gpv.setWidth(120);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("preferred_time");
		gpv.setHeadingName("Preferred Schedule");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("service_book_time");
		gpv.setHeadingName("Booked Schedule");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("remarks");
		gpv.setHeadingName("Remarks");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_status");
		gpv.setHeadingName("Patient Type");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setFormatter("priorityVal");
		gpv.setWidth(70);
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("corp_name");
		gpv.setHeadingName("Corporate Name");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("corp_type");
		gpv.setHeadingName("Corporate Type");
		gpv.setHideOrShow("true");
		gpv.setWidth(100);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ac_manager");
		gpv.setHeadingName("Account Manager");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("userName");
		gpv.setHeadingName("Action By");
		gpv.setHideOrShow("false");
		gpv.setWidth(90);
		gpv.setEditable("true");
		gpv.setSearch("true");
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setHideOrShow("false");
		gpv.setEditable("true");
		gpv.setWidth(50);
		gpv.setSearch("true");
		// gpv.setFormatter(gdp.getFormatter());
		// gpv.setWidth(gdp.getWidth());
		masterViewPropCPS.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("pat_mobile");
		gpv.setHeadingName("Mobile");
		gpv.setHideOrShow("true");
		gpv.setEditable("true");
		gpv.setWidth(50);
		gpv.setSearch("true");
		// gpv.setFormatter(gdp.getFormatter());
		// gpv.setWidth(gdp.getWidth());
		masterViewPropCPS.add(gpv);
	}

	public String veiwCPSDetail()
	{
		String serviceNameCheck = null;
		String serField = null;
		String serField1 = null;
		String returnString = ERROR;
		if (!ValidateSession.checkSession())
			return LOGIN;
		try
		{

			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from corporate_patience_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
			if (dataCount != null)
			{
				StringBuilder query = new StringBuilder("select ");
				StringBuilder queryEnd = new StringBuilder(" from corporate_patience_data as cpd");

				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("id");
				fieldNames.add("status");
				fieldNames.add("patient_name");
				fieldNames.add("feed_status");
				fieldNames.add("corp_name");
				fieldNames.add("corp_type");
				fieldNames.add("services");

				fieldNames.add("med_location");
				fieldNames.add("preferred_time");
				fieldNames.add("userName");
				fieldNames.add("ac_manager");
				fieldNames.add("ticket");
				fieldNames.add("remarks");
				fieldNames.add("next_level_esc_date");
				fieldNames.add("next_level_esc_time");
				fieldNames.add("level");
				fieldNames.add("uhid");
				fieldNames.add("pat_mobile");
				fieldNames.add("service_book_time");
				fieldNames.add("service_manager");

				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Object ob : fieldNames)
					{
						if (ob.toString().equalsIgnoreCase("corp_name"))
						{
							query.append("cm.customer_name");
							queryEnd.append(" left join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name");
						} else if (ob.toString().equalsIgnoreCase("services"))// services
						{
							query.append("csm.service_name");
							queryEnd.append(" left join cps_service as csm on csm.id=cpd.services");
						} else
						{
							query.append("cpd." + ob.toString());
						}
						query.append(", ");
					}

					query.setLength(query.length() - 2);
					query.append(queryEnd.toString());
					query.append(" left join cps_services_details as cpds on cpds.cpsid=cpd.id");
					query.append(" WHERE cpd.id!=0 ");

					String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
					String[] subModuleRightsArray = CPSHelper.getSubModuleRights(empid, connectionSpace);
					if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
					{
						for (String s : subModuleRightsArray)
						{
							if (s.equals("cpsService_VIEW"))
							{
								String subDept = null;
								subDept = CPSHelper.getSubDeptByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
								if (subDept != null && !subDept.equalsIgnoreCase(""))
								{
									query.append(" AND cpd.services in (select id from cps_service where service_name in(" + subDept.substring(0, subDept.length() - 1) + "))");
								}

							}
							if (s.equals("cpsLocation_VIEW"))
							{
								String location = null;
								location = CPSHelper.getLocationName((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
								if (location != null && !location.equalsIgnoreCase("") && !location.contains("All"))
								{
									query.append(" and cpd.med_location In(" + location.substring(0, location.length() - 1) + ")");
								}
							}
						}
					}
					// add search query based on the search
					// operation by chandan
					if (getMinDateValue() != null && getMaxDateValue() != null && !getMinDateValue().equalsIgnoreCase("") && !getMaxDateValue().equalsIgnoreCase(""))
					{
						String str[] = getMinDateValue().split("-");
						if (str[0] != null && str[0].length() > 3)
						{
							query.append(" AND cpd.date BETWEEN '" + ((getMinDateValue())) + "' AND '" + ((getMaxDateValue())) + "'");
						} else
						{
							query.append(" AND cpd.date BETWEEN '" + (DateUtil.convertDateToUSFormat(getMinDateValue())) + "' AND '" + (DateUtil.convertDateToUSFormat(getMaxDateValue())) + "'");
						}
					} else
					{
						query.append(" AND cpd.date = '" + DateUtil.getCurrentDateUSFormat() + "'");
					}
					System.out.println("Status " + status);
					if (status != null && "All Status".equalsIgnoreCase(status))
					{

					} else if (status != null && !status.equalsIgnoreCase("") && !"-1".equalsIgnoreCase(status))
					{
						query.append(" AND cpd.status='" + getStatus() + "' ");
					} else
					{
						query.append(" AND cpd.status not in ('Appointment Parked','Service Out') ");
					}
					if (feed_status != null && !"-1".equalsIgnoreCase(feed_status))
					{
						query.append(" AND cpd.feed_status='" + getFeed_status() + "' ");
					}

					if (status_type != null && !"-1".equalsIgnoreCase(status_type))
					{
						if (status_type.equalsIgnoreCase("All"))
						{

						} else if (status_type.equalsIgnoreCase("On Time"))
						{
							query.append(" AND cpd.level='1' ");
						} else if (status_type.equalsIgnoreCase("Off Time"))
						{
							query.append(" AND cpd.level in('2','3','4','5','6')");
						} else
						{
							query.append(" AND cpd.status='" + getStatus_type() + "' ");
						}
					}

					if (corp_name != null && !"-1".equalsIgnoreCase(corp_name) && !corp_name.equalsIgnoreCase(""))
					{
						boolean chkTestName = isInteger(corp_name.trim());
						if (chkTestName == true)
						{
							query.append(" AND cpd.corp_name='" + getCorp_name() + "' ");
						} else
						{
							query.append(" AND cm.customer_name='" + getCorp_name() + "' ");
						}

					}

					if (services != null && !"-1".equalsIgnoreCase(services) && !services.equalsIgnoreCase(""))
					{
						boolean chkTestName = isInteger(services.trim());
						if (chkTestName == true)
						{
							query.append(" AND cpd.services='" + getServices() + "' ");
							serviceNameCheck = new CPSHelper().fetcServiceName(services, connectionSpace);
						} else
						{
							query.append(" AND csm.service_name='" + getServices() + "' ");
						}
					}
					if (med_location != null && !"-1".equalsIgnoreCase(med_location) && !med_location.equalsIgnoreCase(""))
					{
						query.append(" AND cpd.med_location='" + getMed_location() + "' ");
					}
					if (service_manager != null && !"-1".equalsIgnoreCase(service_manager) && !service_manager.equalsIgnoreCase("") && !service_manager.equalsIgnoreCase("undefined"))
					{
						query.append(" AND cpd.service_manager='" + getService_manager() + "' ");
					}
					System.out.println("ac_manager >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + ac_manager);
					String value = null;
					List adminRights = cbt.executeAllSelectQuery("select linkVal from useraccount where name = '" + empid + "'", connectionSpace);
					if (adminRights != null && adminRights.size() > 0)
					{
						value = adminRights.get(0).toString();
					}
					if (value != null && value.contains("accMng_VIEW"))
					{
						query.append(" AND cpd.ac_manager='Credihealth' ");
					}
					if (ac_manager != null && !"-1".equalsIgnoreCase(ac_manager) && !ac_manager.equalsIgnoreCase(""))
					{
						query.append(" AND cpd.ac_manager='" + getAc_manager() + "' ");
					}

					if (session.containsKey("CPSLogin") && userEmpId.equalsIgnoreCase(Integer.toString((Integer) session.get("CPSLogin"))))
					{
						query.append(" And cpd.ac_manager ='" + userEmpId + "'");
						session.remove("CPSLogin");
					} else
					{
						if (session.containsKey("CPSLogin"))
							session.remove("CPSLogin");
					}
					if (patienceSearch != null && !patienceSearch.equalsIgnoreCase("") && !patienceSearch.contains("undefined"))
					{
						query.append(" AND cpd.patient_name LIKE '%" + patienceSearch + "%'");
					}
					if (spec != null && !spec.equalsIgnoreCase(""))
					{
						if (serviceNameCheck.equalsIgnoreCase("DayCare"))
						{
							serField = "cpds.daycare_spec";
							serField1 = "cpds.daycare_doc";
						} else if (serviceNameCheck.equalsIgnoreCase("OPD"))
						{
							serField = "cpds.opd_spec";
							serField1 = "cpds.opd_doc";
						} else if (serviceNameCheck.equalsIgnoreCase("IPD"))
						{
							serField = "cpds.ipd_spec";
							serField1 = "cpds.ipd_doc";
						} else if (serviceNameCheck.equalsIgnoreCase("EHC"))
						{
							serField = "cpds.ehc_pack_type";
							serField1 = "cpds.ehc_pack";
						} else if (serviceNameCheck.equalsIgnoreCase("Emergency"))
						{
							serField = "cpds.em_spec";
							serField1 = "cpds.em_spec_assis";
						} else if (serviceNameCheck.equalsIgnoreCase("Radiology"))
						{
							serField = "cpds.radio_mod";
						} else if (serviceNameCheck.equalsIgnoreCase("Facilitation"))
						{
							serField = "cpds.facilitation_mod";
						} else if (serviceNameCheck.equalsIgnoreCase("Telemedicine"))
						{
							serField = "cpds.telemedicine_mod";
						} else if (serviceNameCheck.equalsIgnoreCase("Laboratory"))
						{
							serField = "cpds.lab_mod";
						} else if (serviceNameCheck.equalsIgnoreCase("Diagnostics"))
						{
							serField = "cpds.diagnostics_test";
						} else if (serviceNameCheck.equalsIgnoreCase("New Information Request"))
						{
							serField = "cpd.remarks";
						}
						query.append(" AND " + serField + "='" + getSpec() + "' ");
					}
					if (pack != null && !pack.equalsIgnoreCase(""))
					{
						query.append(" AND " + serField1 + "='" + getPack() + "' ");
					}

					if (wildSearch != null && !wildSearch.equalsIgnoreCase(""))
					{
						query.append(" and ");
						if (fieldNames != null && !fieldNames.isEmpty())
						{
							int k = 0;
							for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
							{
								String object = iterator.next().toString();
								// System.out.println("mmm  "+object.toString());//
								if (object != null)
								{
									if (object.equalsIgnoreCase("corp_name"))
									{
										query.append("cm.customer_name LIKE " + "'" + wildSearch + "%" + "'");
									} else if (object.equalsIgnoreCase("services"))
									{
										query.append("csm.service_name LIKE " + "'" + wildSearch + "%" + "'");
									} else if (object.equalsIgnoreCase("med_location"))
									{
										query.append("cpd.med_location LIKE " + "'" + wildSearch + "%" + "'");
									} else if (object.equalsIgnoreCase("ac_manager"))
									{
										query.append("cpd.ac_manager LIKE " + "'" + wildSearch + "%" + "'");
									} else if (object.equalsIgnoreCase("service_manager"))
									{
										query.append("cpd.service_manager LIKE " + "'" + wildSearch + "%" + "'");
									} else
									{
										query.append("cpd. " + object + " LIKE " + "'" + wildSearch + "%" + "'");
									}
								}
								if (k < fieldNames.size() - 1)
									query.append(" OR ");
								else
									query.append(" ");
								k++;
							}
						}
						// ////////System.out.println("wildSearch123"+wildSearch);undefined
						// query.append(" AND cpd.uhid LIKE '%"
						// + wildSearch +
						// "%'");
					}
					if (getSearchData() != null && !searchData.equalsIgnoreCase("") && !searchData.contains("undefined") && !searchData.contains("object") && !searchData.contains("VVIP"))
					{
						// ////System.out.println("vv "+getSearchData());
						query.append(" AND cpd.ticket LIKE '%" + getSearchData() + "%'");
					} else if (getSearchData() != null && !searchData.equalsIgnoreCase("") && !searchData.contains("undefined") && !searchData.contains("object"))
					{
						// ////System.out.println("vv "+getSearchData());
						query.append(" AND cpd.feed_status LIKE '%" + getSearchData() + "%'");
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search
						// operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" ca." + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" ca." + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" ca." + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" ca." + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" ca." + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}
					query.append(" order by service_book_time DESC");
					// System.out.println("query>>>>>>>>>>>>>>>>" +
					// query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && !data.isEmpty())
					{
						List<Object> listdata = new ArrayList<Object>();
						for (Iterator itr = data.iterator(); itr.hasNext();)
						{
							Object[] obdata = (Object[]) itr.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							// for(int k=0; k<12; k++)
							{
								if (obdata[k] == null)
								{
									obdata[k] = "NA";
								}
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}

									else if (fieldNames.get(k).toString().equalsIgnoreCase("service_book_time"))
									{
										if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA"))
										{
											String bookedTime = obdata[k].toString().split(" ")[0];

											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(bookedTime) + " " + obdata[k].toString().split(" ")[1]);
										} else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}
									}

									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
							}

							if (obdata[1].toString() != null && obdata[1].toString().equalsIgnoreCase("Service Out") || obdata[1].toString().equalsIgnoreCase("Schedule Parked") || obdata[1].toString().equalsIgnoreCase("Cancel") || obdata[1].toString().equalsIgnoreCase("Appointment Close") || obdata[1].toString().equalsIgnoreCase("Appointment Parked"))
							{
								one.put("Timer", "00:00");
								one.put("level", "Level-" + obdata[15].toString());
							}
							if (obdata[1].toString() != null && obdata[1].toString().equalsIgnoreCase("Appointment") || obdata[1].toString().equalsIgnoreCase("Service In") || obdata[1].toString().equalsIgnoreCase("Scheduled"))
							{
								one.put("Timer", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), obdata[13].toString(), obdata[14].toString()));
								one.put("level", "Level-" + obdata[15].toString());
							}
							if (obdata[19] == null || obdata[19].toString().equalsIgnoreCase("NA"))
							{
								one.put("service_manager", "NA");
							} else
							{
								CPSHelper ch = new CPSHelper();
								one.put("service_manager", ch.empNameGet(obdata[19].toString(), connectionSpace).split("#")[0]);
							}

							listdata.add(one);
						}
						setCpsGridData(listdata);
					}
				}
			}
			returnString = SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnString;
	}

	public String addCorporatePatience()
	{
		String returnString = "error";
		String nxtTkt = "CPS-1000";
		boolean status = false;
		int maxId = 0;
		String appointment_time = null;

		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_corporate_patient_configuration", accountID, connectionSpace, "", 0, "table_name", "corporate_patient_configuration");

			if (statusColName != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertDataStatusDetails = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1 = new TableColumes();
				for (GridDataPropertyView gdp : statusColName)
				{

					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

				}
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("corporate_patience_data", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					// System.out.println("Parmnamebfr > " +
					// Parmname +
					// "paramValuebrf>> " + paramValue);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && !Parmname.equalsIgnoreCase("pat_dob") && !Parmname.equalsIgnoreCase("pat_dob_txt")
							&& !Parmname.equalsIgnoreCase("ac_manager_widget")
							&& //
							!Parmname.equalsIgnoreCase("ac_manager_text") && !Parmname.equalsIgnoreCase("ac_manager") && !Parmname.equalsIgnoreCase("corp_type") && !Parmname.equalsIgnoreCase("corp_name") && !Parmname.equalsIgnoreCase("corp_name_widget") && !Parmname.equalsIgnoreCase("feed_status_widget") && !Parmname.equalsIgnoreCase("feed_status") && !Parmname.equalsIgnoreCase("pat_gender_widget") && !Parmname.equalsIgnoreCase("pat_gender") && !Parmname.equalsIgnoreCase("patgender") && !Parmname.equalsIgnoreCase("pat_country_name") && !Parmname.equalsIgnoreCase("pat_state_name") && !Parmname.equalsIgnoreCase("pat_city") && !Parmname.equalsIgnoreCase("pat_city_widget") && !Parmname.equalsIgnoreCase("services_widget") && !Parmname.equalsIgnoreCase("services")
							&& !Parmname.equalsIgnoreCase("pat_state_widget") && !Parmname.equalsIgnoreCase("pat_state") && !Parmname.equalsIgnoreCase("em_spec") && !Parmname.equalsIgnoreCase("em_spec_assis") && !Parmname.equalsIgnoreCase("radio_mod") && !Parmname.equalsIgnoreCase("facilitation_mod") && !Parmname.equalsIgnoreCase("telemedicine_mod") && !Parmname.equalsIgnoreCase("opd_spec") && !Parmname.equalsIgnoreCase("opd_doc") && !Parmname.equalsIgnoreCase("daycare_spec") && !Parmname.equalsIgnoreCase("daycare_doc") && !Parmname.equalsIgnoreCase("ipd_spec") && !Parmname.equalsIgnoreCase("ipd_doc") &&

							!Parmname.equalsIgnoreCase("ipd_doc_widget") && !Parmname.equalsIgnoreCase("daycare_doc_widget") && !Parmname.equalsIgnoreCase("opd_doc_widget") && !Parmname.equalsIgnoreCase("diagnostics_test_widget") && !Parmname.equalsIgnoreCase("em_spec_widget") && !Parmname.equalsIgnoreCase("em_spec_assis_widget") && !Parmname.equalsIgnoreCase("radio_mod_widget") && !Parmname.equalsIgnoreCase("facilitation_mod_widget") && !Parmname.equalsIgnoreCase("telemedicine_mod_widget") &&

							!Parmname.equalsIgnoreCase("ipd_bed") && !Parmname.equalsIgnoreCase("ipd_pat_type") && !Parmname.equalsIgnoreCase("lab_mod") && !Parmname.equalsIgnoreCase("diagnostics_test") && !Parmname.equalsIgnoreCase("ehc_pack") && !Parmname.equalsIgnoreCase("ehc_pack_widget") && !Parmname.equalsIgnoreCase("ehc_pack_type") && !Parmname.equalsIgnoreCase("ehc_pack_type_widget") && !Parmname.equalsIgnoreCase("pat_country_widget") && !Parmname.equalsIgnoreCase("pat_country") && !Parmname.equalsIgnoreCase("med_location_widget") && !Parmname.equalsIgnoreCase("med_location") && !Parmname.equalsIgnoreCase("patientName") && !Parmname.equalsIgnoreCase("patient_name") && !Parmname.equalsIgnoreCase("preferred_timecal") && !Parmname.equalsIgnoreCase("hour")
							&& !Parmname.equalsIgnoreCase("minuts") && !Parmname.equalsIgnoreCase("ampm") && !Parmname.equalsIgnoreCase("preferred_time") && !Parmname.equalsIgnoreCase("remarks") && !Parmname.equalsIgnoreCase("remarksNew") && !Parmname.equalsIgnoreCase("patient_name_widget"))
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					} else if (Parmname.equalsIgnoreCase("ehc_pack_type") || Parmname.equalsIgnoreCase("ehc_pack") || Parmname.equalsIgnoreCase("ehc_pack_widget") || Parmname.equalsIgnoreCase("em_spec") || Parmname.equalsIgnoreCase("em_spec_assis") || Parmname.equalsIgnoreCase("radio_mod") || Parmname.equalsIgnoreCase("facilitation_mod") || Parmname.equalsIgnoreCase("telemedicine_mod") || Parmname.equalsIgnoreCase("opd_spec") || Parmname.equalsIgnoreCase("opd_doc") || Parmname.equalsIgnoreCase("daycare_spec") || Parmname.equalsIgnoreCase("daycare_doc") || Parmname.equalsIgnoreCase("ipd_spec") || Parmname.equalsIgnoreCase("ipd_doc") || Parmname.equalsIgnoreCase("ipd_bed") || Parmname.equalsIgnoreCase("ipd_pat_type") || Parmname.equalsIgnoreCase("lab_mod")
							|| Parmname.equalsIgnoreCase("diagnostics_test"))
					{
						if (Parmname.equalsIgnoreCase("radio_mod_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("radio_mod");
							ob.setDataName(request.getParameter("radio_mod_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("facilitation_mod_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("facilitation_mod");
							ob.setDataName(request.getParameter("facilitation_mod_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("telemedicine_mod_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("telemedicine_mod");
							ob.setDataName(request.getParameter("telemedicine_mod_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("em_spec_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("em_spec");
							ob.setDataName(request.getParameter("em_spec_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("em_spec_assis_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("em_spec_assis");
							ob.setDataName(request.getParameter("em_spec_assis_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("diagnostics_test_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("diagnostics_test");
							ob.setDataName(request.getParameter("diagnostics_test_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("opd_doc_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("opd_doc");
							ob.setDataName(request.getParameter("opd_doc_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("daycare_doc_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("daycare_doc");
							ob.setDataName(request.getParameter("ipd_doc_widget"));
							insertDataStatusDetails.add(ob);
						}
						if (Parmname.equalsIgnoreCase("ipd_doc_widget"))
						{
							ob = new InsertDataTable();
							ob.setColName("ipd_doc");
							ob.setDataName(request.getParameter("ipd_doc_widget"));
							insertDataStatusDetails.add(ob);
						} else
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertDataStatusDetails.add(ob);
						}
					}
				}
				String patientUHID = request.getParameter("uhid");
				String remarksNew = "";
				if (!request.getParameter("remarks").equalsIgnoreCase("") && request.getParameter("remarks") != null)
				{
					remarksNew = request.getParameter("remarks");
				} else
				{
					remarksNew = request.getParameter("remarksNew");
				}
				if (patientUHID != null && !patientUHID.equalsIgnoreCase("NA"))
				{
					String query = "update corporate_patience_data set pat_mobile='" + request.getParameter("pat_mobile") + "', pat_email='" + request.getParameter("pat_email") + "', pat_dob = '" + request.getParameter("pat_dob") + "' where uhid='" + request.getParameter("uhid") + "'";
					// System.out.println("query "+query);
					// System.out.println("DOB "+request.getParameter("pat_dob"));
					cbt.executeAllUpdateQuery(query, connectionSpace);
				}
				if (request.getParameter("pat_dob") != null)
				{
					ob = new InsertDataTable();
					ob.setColName("pat_dob");
					ob.setDataName(request.getParameter("pat_dob"));
					insertData.add(ob);
					// System.out.println("DOB2 "+request.getParameter("pat_dob"));
				}
				if (!request.getParameter("patientName").equalsIgnoreCase("") && request.getParameter("patientName") != null)
				{
					if (!request.getParameter("patientName").trim().toString().contains("0"))
					{
						ob = new InsertDataTable();
						ob.setColName("patient_name");
						ob.setDataName(request.getParameter("patientName"));
						insertData.add(ob);
					} else if (request.getParameter("patientName") != null)
					{
						ob = new InsertDataTable();
						ob.setColName("patient_name");
						ob.setDataName(request.getParameter("patientName"));
						insertData.add(ob);
					}

				}
				if (request.getParameter("preferred_time").equalsIgnoreCase("") || request.getParameter("preferred_timecal") != null)
				{
					if (!request.getParameter("preferred_time").equalsIgnoreCase("") && request.getParameter("preferred_time") != null)
					{
						appointment_time = request.getParameter("preferred_time");
						ob = new InsertDataTable();
						ob.setColName("preferred_time");
						ob.setDataName(request.getParameter("preferred_time"));
						insertData.add(ob);
					} else
					// if(request.getParameter("patientName")!=null)
					{
						appointment_time = request.getParameter("preferred_timecal") + " " + request.getParameter("hour") + ":" + request.getParameter("minuts") + " " + request.getParameter("ampm");
						ob = new InsertDataTable();
						ob.setColName("preferred_time");
						ob.setDataName(request.getParameter("preferred_timecal") + " " + request.getParameter("hour") + ":" + request.getParameter("minuts") + " " + request.getParameter("ampm"));
						insertData.add(ob);
					}

				} else if (!request.getParameter("pat_dob_txt").equalsIgnoreCase("") && request.getParameter("pat_dob_txt") != null)
				{
					if (request.getParameter("pat_dob_txt").toString().contains("/"))
					{
						date = request.getParameter("pat_dob_txt");
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date birthDate = sdf.parse(date);
						Age age = calculateAge(birthDate);
						// ////////System.out.println("age "+age);
						ob = new InsertDataTable();
						ob.setColName("pat_dob");
						ob.setDataName(age.toString());
						insertData.add(ob);
					} else
					{

						ob = new InsertDataTable();
						ob.setColName("pat_dob");
						ob.setDataName(request.getParameter("pat_dob_txt"));
						insertData.add(ob);
					}
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("pat_dob");
					ob.setDataName("NA");
					insertData.add(ob);
				}//
				ob = new InsertDataTable();
				ob.setColName("med_location");
				ob.setDataName(request.getParameter("med_location_widget"));
				insertData.add(ob);// //pat_country_name pat_state_name
				if (request.getParameter("pat_country_widget") != null && !request.getParameter("pat_country_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("pat_country");
					ob.setDataName(request.getParameter("pat_country_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("pat_country");
					ob.setDataName(request.getParameter("pat_country"));
					insertData.add(ob);
				}
				if (request.getParameter("pat_state_widget") != null && !request.getParameter("pat_state_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("pat_state");
					ob.setDataName(request.getParameter("pat_state_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("pat_state");
					ob.setDataName(request.getParameter("pat_state_name"));
					insertData.add(ob);
				}
				/*
				 * ob = new InsertDataTable(); ob.setColName("corp_type");
				 * ob.setDataName(request.getParameter ("corp_type_widget"));
				 * insertData.add(ob);
				 */
				if (request.getParameter("ac_manager_widget") != null && !request.getParameter("ac_manager_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("ac_manager");
					ob.setDataName(request.getParameter("ac_manager_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("ac_manager");
					ob.setDataName(request.getParameter("ac_manager_text"));
					insertData.add(ob);
				}
				if (request.getParameter("pat_city_widget") != null && !request.getParameter("pat_city_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("pat_city");
					ob.setDataName(request.getParameter("pat_city_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("pat_city");
					ob.setDataName(request.getParameter("pat_city"));
					insertData.add(ob);
				}
				if (request.getParameter("services_widget") != null && !request.getParameter("services_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("services");
					ob.setDataName(request.getParameter("services_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("services");
					ob.setDataName(request.getParameter("services"));
					insertData.add(ob);
				}
				if (request.getParameter("corp_name_widget") != null && !request.getParameter("corp_name_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("corp_name");
					ob.setDataName(request.getParameter("corp_name_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("corp_name");
					ob.setDataName(request.getParameter("corp_name"));
					insertData.add(ob);
				}

				if (request.getParameter("pat_gender_widget") != null && !request.getParameter("pat_gender_widget").equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("pat_gender");
					ob.setDataName(request.getParameter("pat_gender_widget"));
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("pat_gender");
					ob.setDataName(request.getParameter("patgender"));
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("remarks");
				ob.setDataName(remarksNew);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_status");
				ob.setDataName(request.getParameter("feed_status_widget"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				// for ticket id change by Manab

				try
				{
					List dataLastTkt = null;
					String acManager = request.getParameter("ac_manager_widget");
					/*
					 * String corp_id = "", corp_name = ""; List data =
					 * CPSHelper.fetchDepartment(acManager.trim(),
					 * connectionSpace); for (Iterator iterator =
					 * data.iterator(); iterator.hasNext();) { Object[] obdata =
					 * (Object[]) iterator.next(); corp_id =
					 * obdata[0].toString(); corp_name = obdata[1].toString(); }
					 */
					String ticketHead = "CPS-";
					if (acManager != null && acManager.equalsIgnoreCase("COO Office"))
					{
						nxtTkt = "COO-1000";
						ticketHead = "COO-";
						dataLastTkt = cbt.executeAllSelectQuery("select ticket from corporate_patience_data where ticket like '%COO%' order by id desc limit 1 ".toString(), connectionSpace);
					} else if (acManager != null && acManager.equalsIgnoreCase("CMD Office"))
					{
						nxtTkt = "CMD-1000";
						ticketHead = "CMD-";
						dataLastTkt = cbt.executeAllSelectQuery("select ticket from corporate_patience_data where ticket like '%CMD%' order by id desc limit 1 ".toString(), connectionSpace);
					}

					else
					{
						dataLastTkt = cbt.executeAllSelectQuery("select ticket from corporate_patience_data where ticket like '%CPS%' order by id desc limit 1 ".toString(), connectionSpace);
					}
					if (dataLastTkt != null && dataLastTkt.size() > 0)
					{
						int lastTicket = Integer.parseInt(dataLastTkt.get(0).toString().split("-")[1]);
						lastTicket = lastTicket + 1;
						nxtTkt = ticketHead + lastTicket;
					}
				} catch (SQLGrammarException e)
				{
					e.printStackTrace();
				}
				ob = new InsertDataTable();
				ob.setColName("ticket");
				ob.setDataName(nxtTkt);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Appointment");
				insertData.add(ob);

				String escData1 = escTime(connectionSpace);
				// ////////System.out.println(">>>>>>>>>>>>>>>>>>>>>>  "+escData1);

				ob = new InsertDataTable();
				ob.setColName("next_level_esc_date");
				ob.setDataName(escData1.split("#")[0]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("next_level_esc_time");
				ob.setDataName(escData1.split("#")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("department");
				ob.setDataName(CPSHelper.fetchDeptID(connectionSpace));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("1");
				insertData.add(ob);

				// ////////System.out.println(insertData);

				maxId = cbt.insertDataReturnId("corporate_patience_data", insertData, connectionSpace);
				sendOccAlertOnTicketLodge(nxtTkt, connectionSpace);
				// sendPatientAlert("appointment",request.getParameter("patientName"),request.getParameter("pat_mobile"),request.getParameter("services"),appointment_time,request.getParameter("med_location_widget"),"","",
				// connectionSpace);
				insertData.clear();

				if (maxId > 0)
				{
					ob = new InsertDataTable();
					ob.setColName("CPSId");
					ob.setDataName(maxId);
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("med_location_widget"));
					insertDataStatusDetails.add(ob);
					// Insert CPS_Service Table
					cbt.insertIntoTable("cps_services_details", insertDataStatusDetails, connectionSpace);
					insertData.clear();
					// End
					ob = new InsertDataTable();
					ob.setColName("action_by");
					ob.setDataName(userName);
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("allocate_to");
					ob.setDataName("OCC");
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_reason");
					ob.setDataName("Assign");
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation_level");
					ob.setDataName("1");
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Appointment");
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("dept");
					ob.setDataName("OCC");
					insertDataStatusDetails.add(ob);

					String service_name = new CPSHelper().fetcServiceName(request.getParameter("services_widget"), connectionSpace);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(service_name);
					insertDataStatusDetails.add(ob);

					ob = new InsertDataTable();
					ob.setColName("new_service_remarks");
					ob.setDataName(chkNULL(request.getParameter("remarksNew")));
					insertDataStatusDetails.add(ob);

					status = cbt.insertIntoTable("cps_status_history", insertDataStatusDetails, connectionSpace);
					insertData.clear();
					// For OCC Lodge Alert
					// insert in to cps
				}
			}
			// END mapped table data
			if (status = true)
			{
				addActionMessage("Data saved successfully");
				returnString = SUCCESS;
			} else
			{
				addActionMessage("error in data!!!");
				returnString = SUCCESS;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
			// TODO: handle exception
		}
		return returnString;
	}

	public void sendOccAlertOnTicketLodge(String ticketNo, SessionFactory connection)
	{
		// String machineId = machineID(orderType, connection);
		List nameMob = new CPSHelper().selectRendomOccEmp("1", "17", "1", "CPS", connection);
		CommunicationHelper ch = new CommunicationHelper();
		/*
		 * if (nameMob != null && !nameMob.isEmpty()) { Object object = (Object)
		 * nameMob.get(0); ch.addMessage(object.toString().split("#")[1],
		 * "AUTO", object.toString().split("#")[2], "Appointment Ticket No: " +
		 * ticketNo + "", "", "Pending", "0", "CPS", connection); }
		 */

		if (nameMob != null && nameMob.size() > 0)
		{
			for (int a = 0; a < nameMob.size(); a++)
			{
				Object object = (Object) nameMob.get(a);

				ch.addMessage(object.toString().split("#")[1], "AUTO", object.toString().split("#")[2], "Appointment Ticket No: " + ticketNo + "", "", "Pending", "0", "CPS", connection);

			}
		}

	}

	// method for get next level escalation date and time author : manab
	// 03-09-2015
	String escTime(SessionFactory connection)
	{
		String esctm = "";
		try
		{

			StringBuilder empuery = new StringBuilder();
			// Faisal the dept id 19 is used hard code because patientcare
			// dept
			// id is 19
			empuery.append(" select l1Tol2 from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept='17' ");

			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

	// esc for dept level

	// method for get next level escalation date and time author : manab
	// 03-09-2015
	private String escTimeForDept(String serviceName, SessionFactory connection)
	{
		String esctm = "";
		try
		{

			StringBuilder empuery = new StringBuilder();
			empuery.append(" select esc.l1Tol2 from  escalation_cps_detail as esc  inner join subdepartment as sub on sub.id= esc.escSubDept where esc.escLevelL1L2='Customised' and sub.subdeptname ='" + serviceName + "' ");
			// System.out.println("empuery  " + empuery);
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;
	}

	public String berforeCPSAdd()
	{
		String returnString = ERROR;
		if (!ValidateSession.checkSession())
			return LOGIN;
		try
		{
			List<GridDataPropertyView> clientColumn = Configuration.getConfigurationData("mapped_corporate_patient_configuration", accountID, connectionSpace, "", 0, "table_name", "corporate_patient_configuration");
			addFields = new ArrayList<ConfigurationUtilBean>();
			if (clientColumn != null)
			{
				for (GridDataPropertyView gdp : clientColumn)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

					addFields.add(obj);

				}
				// Account manager
				String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
				acManagerMap = new LinkedHashMap<String, String>();
				acManagerMap = CPSHelper.fetchAcManager(connectionSpace, empid, CPSHelper.empNameGet(userEmpId, connectionSpace).split("#")[0]);
				// Service Map
				serviceMap = new LinkedHashMap<String, String>();
				serviceMap = CPSHelper.fetchServiceMap(connectionSpace);

			}
			// By Faisal 3-09-2015
			/*
			 * stateMap=new LinkedHashMap<String, String>(); String query =null;
			 * query=
			 * "select code,country_name from country GROUP by country_name ORDER BY country_name "
			 * ; //////////System.out.println("bbb "+query); if (query!=null) {
			 * List<?> dataList = new createTable().executeAllSelectQuery(query,
			 * connectionSpace); if (dataList != null && dataList.size() > 0) {
			 * for (Iterator<?> iterator = dataList.iterator();
			 * iterator.hasNext();) { Object[] object = (Object[])
			 * iterator.next(); if (object[0] != null && object[1] != null) {
			 * stateMap.put(object[0].toString(), object[1].toString()); } } } }
			 */

			String query = null;
			query = "select cpd.pat_country,cun.country_name,count(*) FROM corporate_patience_data as cpd inner join country as cun on cun.code=cpd.pat_country group by cpd.pat_country order by count(*) desc";
			stateMap = new LinkedHashMap<String, String>();
			List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						stateMap.put(object[0].toString(), object[1].toString());
					}
				}
			}

			query = null;
			// By Faisal 3-09-2015
			query = null;
			dataList = null;
			query = "select code,country_name from country GROUP by country_name ORDER BY country_name ";
			// //////System.out.println("bbb "+query);
			if (query != null)
			{
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							if (!stateMap.containsKey(object[0].toString()))
								stateMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
			}
			// End
			int age = 30;
			String curDate = DateUtil.getCurrentDateIndianFormat();
			// String curYear = curDate.substring(6, 10);
			// String changeDOB = String.valueOf(Integer.parseInt(curYear) -
			// age);
			// DOB = curDate.substring(0, 6) + changeDOB;
			DOB = curDate;
			returnString = "success";
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnString;
	}

	public String fetchServiceName()
	{

		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			List data = CPSHelper.fetchServiceList(connectionSpace);
			if (data != null && data.size() > 0)
			{
				JSONObject job = null;
				service = new JSONArray();
				for (Object obj : data)
				{

					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{

						job = new JSONObject();
						job.put("ID", ob[0].toString());
						job.put("NAME", ob[1].toString());
						service.add(job);
					}
				}

			}

			returnString = "success";

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;
	}

	public String fetchCorporateName()
	{
		try
		{
			List data = new createTable().executeAllSelectQuery("select id, CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg group by CUSTOMER_NAME", connectionSpace);
			JSONObject job = null;
			jsonArray = new JSONArray();
			if (data != null && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("id", ob[0].toString());
						job.put("Name", ob[1].toString());

					}
					jsonArray.add(job);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;

	}

	public String selectServices()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			List data = CPSHelper.fetchServicesLocation(service_id, connectionSpace);
			if (data != null && data.size() > 0)
			{
				JSONObject job = null;
				servicelocation = new JSONArray();
				for (Object obj : data)
				{

					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("ID", ob[0].toString());
						String[] cc = ob[1].toString().split("#");
						for (int i = 0; i < cc.length; i++)
						{
							job.put("NAME", cc[i].toString());
							servicelocation.add(job);
						}
						// job.put("NAME", ob[1].toString());

					}
				}

			}

			returnString = "success";

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;

	}

	public String stateList()
	{
		String returnresult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				commondata = new ArrayList();
				String query = null;
				query = "select cpd.pat_state,cun.state_name,count(*) FROM corporate_patience_data as cpd inner join state as cun on cun.state_name=cpd.pat_state where cun.city_code='" + getStateId() + "' group by cpd.pat_state order by count(*) desc";
				stateMap = new LinkedHashMap<String, String>();
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							commondata.add(object[0].toString());
						}
					}
				}

				query = null;
				dataList = null;
				query = "select city_code,state_name from state where city_code='" + getStateId() + "' GROUP by state_name ORDER BY state_name ";
				// //////System.out.println("query State "+query);
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					if (commondata != null && commondata.size() > 0)
					{
						// jsonArray = new JSONArray();
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{

								if (!commondata.contains(object[1].toString()))
									commondata.add(object[1].toString());
							}
						}
					} else
					{
						// jsonArray = new JSONArray();
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								commondata.add(object[1].toString());
							}
						}
					}
				}

				returnresult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnresult = ERROR;
			}
		} else
		{
			return LOGIN;
		}
		return returnresult;
	}

	public String fetchCityList()
	{
		String returnresult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				commondata = new ArrayList();
				String query = "select cpd.pat_city,cun.city_name,count(*) FROM corporate_patience_data as cpd inner join city as cun on cun.state_name=cpd.pat_state where cun.state_name='" + getStateId() + "' group by cpd.pat_city order by count(*) desc";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							commondata.add(object[0].toString());
						}
					}
				}

				query = null;
				dataList = null;
				query = "select country_code,city_name from city where state_name='" + getStateId() + "'  ORDER BY city_name ";
				// //////System.out.println("query State "+query);
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					if (commondata != null && commondata.size() > 0)
					{
						jsonArray = new JSONArray();
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								if (!commondata.contains(object[1].toString()))
									commondata.add(object[1].toString());
							}
						}
					} else
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								commondata.add(object[1].toString());
							}
						}
					}
				}

				returnresult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnresult = ERROR;
			}
		} else
		{
			return LOGIN;
		}
		return returnresult;
	}

	// end
	public String fetchCorName()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			List data = CPSHelper.fetchCorporateName(id.trim(), connectionSpace);
			JSONObject job = null;
			JSONObject job1 = null;
			String hismaxid = null;
			List hisdata = null;
			hisdata = cbt.executeAllSelectQuery("select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='Referral'", connectionSpace);
			if (hisdata != null && hisdata.size() > 0)
			{
				hisdata = cbt.executeAllSelectQuery("select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='Referral'", connectionSpace);
				hismaxid = hisdata.get(0).toString();
			} else
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertDataStatusDetails = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				ob = new InsertDataTable();
				ob.setColName("CUSTOMER_NAME");
				ob.setDataName("Referral");
				insertDataStatusDetails.add(ob);

				cbt.insertIntoTable("dreamsol_bl_corp_hc_pkg", insertDataStatusDetails, connectionSpace);
				insertData.clear();
				hisdata = cbt.executeAllSelectQuery("select id from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME='Referral'", connectionSpace);
				hismaxid = hisdata.get(0).toString();
			}
			if (data != null && data.size() > 0)
			{

				job = new JSONObject();
				job1 = new JSONObject();
				jsonArray = new JSONArray();
				job1.put("ID", hismaxid);
				job1.put("NAME", "Referral");
				jsonArray.add(job1);
				for (Object obj : data)
				{

					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{

						job.put("ID", ob[0].toString());
						job.put("NAME", ob[1].toString());
						jsonArray.add(job);

					}
				}

			} else if (id.trim().equalsIgnoreCase("COO Office") || id.trim().equalsIgnoreCase("CMD Office") || id.trim().equalsIgnoreCase("Credihealth"))
			{
				jsonArray = new JSONArray();
				job = new JSONObject();
				job.put("ID", hismaxid);
				job.put("NAME", "Referral");
				jsonArray.add(job);

			} else
			{
				jsonArray = new JSONArray();
				job = new JSONObject();
				job.put("ID", "-1");
				job.put("NAME", "No Data");
				jsonArray.add(job);

			}
			System.out.println("cc " + id);
			hisdata = null;
			returnString = "success";

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;
	}

	public String fetchServiceLocation()
	{

		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			List data = CPSHelper.fetchServicesLocation(service_id, connectionSpace);
			if (data != null && data.size() > 0)
			{
				JSONObject job = null;
				servicelocation = new JSONArray();
				for (Object obj : data)
				{

					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("ID", ob[0].toString());
						String[] cc = ob[1].toString().split("#");
						for (int i = 0; i < cc.length; i++)
						{
							job.put("NAME", cc[i].toString());
							servicelocation.add(job);
						}
						// job.put("NAME", ob[1].toString());

					}
				}

			}

			returnString = "success";

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;

	}

	public String fetchRemarks()
	{

		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				StringBuilder qry = new StringBuilder();

				qry.append("select action_type, reason_name from cps_reason_master where module_name='CPS' and status='Active' ");
				// System.out.println(qry);
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[1]);
							obj.put("Name", object[1]);
							jsonArr.add(obj);
						}
					}
					returnResult = SUCCESS;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String fetchPatientName()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			List data = CPSHelper.fetchPatientName(id.trim(), connectionSpace);

			JSONObject job = null;
			jsonArray = new JSONArray();

			job = new JSONObject();
			job.put("ID", "0");
			job.put("NAME", "First Visit");
			jsonArray.add(job);

			if (data != null && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{
						job = new JSONObject();
						job.put("ID", ob[1].toString());
						job.put("NAME", (ob[0].toString() + "-" + ob[1].toString()));
						jsonArray.add(job);

					}
				}
			}
			returnString = "success";
		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;
	}

	// Auto fill for visited patient details
	public String fillPatientDetails()
	{

		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			// ////////System.out.println("bbb  ffffffffffff "+id);
			patient_details = CPSHelper.fillPatientDetails(id.trim(), connectionSpace);

			returnString = "success";

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}

		return returnString;
	}

	// Fetch Account Manager
	public String fetchAccountManager()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			ac_manager = CPSHelper.fetchAccountManager(id.trim(), connectionSpace);
			returnString = "success";
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnString;
	}

	public String beforeServicesAdd()
	{
		try
		{
			String loc = "";
			String ser = "";
			List dataList = null;
			String searchFieldOne = "NA";
			String searchFieldTwo = "NA";
			ticketDetails(id);
			corpMap = new LinkedHashMap<String, String>();
			corpMap.put("Standard", "Standard");
			corpMap.put("Custom", "Custom");

			serviceMap = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						serviceMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			bedType = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select bed_type, bed_type as bed from cps_bed_type  group by bed_type order by bed_type", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						bedType.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			paymenetType = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select payment_type, payment_type as pay from cps_payment_type  group by payment_type order by payment_type", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						paymenetType.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			modality = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select modality, modality as test from cps_modality  group by modality order by modality", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						modality.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			StringBuilder query = new StringBuilder();
			query.append("select cs.service_name, cpd.med_location from corporate_patience_data as cpd");
			query.append(" inner join cps_service as cs on cs.id = cpd.services where cpd.id = " + id);
			// System.out.println(">>>>>>>>>>>>>>>>> "+query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						serviceName = object[0].toString();
						ser = object[0].toString();
						loc = object[1].toString();
					}
				}
				if (serviceName.contains("EHC"))
				{
					serviceName = "EHC";
					searchFieldOne = "ehc_pack_type";
					searchFieldTwo = "ehc_pack";
				} else if (serviceName.contains("OPD"))
				{
					serviceName = "OPD";
					searchFieldOne = "opd_spec";
					searchFieldTwo = "opd_doc ";
				} else if (serviceName.contains("Radiology"))
				{
					serviceName = "Radiology";
					searchFieldOne = "radio_mod";
				} else if (serviceName.contains("Facilitation"))
				{
					serviceName = "Facilitation";
					searchFieldOne = "facilitation_mod";
				} else if (serviceName.contains("Telemedicine"))
				{
					serviceName = "Telemedicine";
					searchFieldOne = "telemedicine_mod";
				} else if (serviceName.contains("Diagnostics"))
				{
					serviceName = "Diagnostics";
					searchFieldOne = "diagnostics_test";
				} else if (serviceName.contains("IPD"))
				{
					serviceName = "IPD";
					searchFieldOne = "ipd_spec";
					searchFieldTwo = "ipd_doc ";
				} else if (serviceName.contains("DayCare"))
				{
					serviceName = "Day Care";
					searchFieldOne = "daycare_spec";
					searchFieldTwo = "daycare_doc ";

				} else if (serviceName.contains("Laboratory"))
				{
					serviceName = "Laboratory";
					searchFieldOne = "lab_mod";

				} else if (serviceName.contains("Emergency"))
				{
					serviceName = "Emergency";

					searchFieldOne = "em_spec";
					searchFieldTwo = "em_spec_assis ";
				}
			}
			headingName = serviceName;
			// ////////System.out.println(serviceName);
			// Service manager
			serviceManagerMap = new LinkedHashMap<String, String>();
			serviceManagerMap = CPSHelper.fetchServiceManager(connectionSpace);
			// for select spec
			SpecMap = new LinkedHashMap<String, String>();
			SpecMap = CPSHelper.fetchSpec(loc, ser, connectionSpace);

			query.setLength(0);
			if (!serviceName.contains("New Information Request"))
			{
				if (serviceName.contains("Radiology") || serviceName.contains("Facilitation") || serviceName.contains("Telemedicine"))
				{
					query.append("select " + searchFieldOne + "," + searchFieldOne + " as rad");
				} else if (serviceName.contains("Laboratory"))
				{
					query.append("select " + searchFieldOne + "," + searchFieldOne + " as lab");
				} else if (serviceName.contains("Diagnostics"))
				{
					query.append("select " + searchFieldOne + "," + searchFieldOne + " as dia");
				} else if (!serviceName.contains("Laboratory") || !serviceName.contains("Radiology") || !serviceName.contains("Facilitation") || !serviceName.contains("Telemedicine") || !serviceName.contains("Diagnostics"))
				{
					query.append("select " + searchFieldOne);
					query.append(", " + searchFieldTwo);

				}
				/*
				 * query.append("select "+searchFieldOne);
				 * 
				 * if(!searchFieldOne.equalsIgnoreCase("NA")){
				 * query.append(", "+searchFieldTwo); }
				 */
				query.append(" from cps_services_details where cpsid = " + id);
				// System.out.println("query>>>>>. " + query);
				List dataBefore = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataBefore != null && !dataBefore.isEmpty() && dataBefore.size() > 0)
				{
					for (Iterator iterator = dataBefore.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							keyExist = object[0].toString();
							valueExist = object[0].toString();
							// System.out.println("keyExist =  "
							// + keyExist +
							// " valueExist==  " +
							// valueExist);
						}
						if (!searchFieldOne.equalsIgnoreCase("NA"))
						{
							if (object[1] != null)
							{
								keyExistSec = object[1].toString();
								valueExistSec = object[1].toString();
								// System.out.println("keyExistnnnn =  "
								// +
								// keyExist +
								// " valueExist==  " +
								// valueExist);
							}
						}
					}
				}
			}

			query.setLength(0);
			// query.append("select ");
			query.append("select services,med_location,patient_name,pat_mobile from corporate_patience_data where id=" + id);
			// System.out.println("query "+query);
			List dataL = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataL != null && !dataL.isEmpty() && dataL.size() > 0)
			{
				for (Iterator iterator = dataL.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						service_id = object[0].toString();
						madLoc = object[1].toString();
						pat_name = object[2].toString();
						pat_mobile = object[3].toString();
					}
				}
			}
			return "success";
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return "success";
	}

	public Map<String, String> ticketDetails(String feedId)
	{

		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder(); // csm.serv_loc
			query.append("select cpd.id, cpd.ticket,cm.customer_name, cpd.feed_status, cpd.patient_name,cpd.pat_mobile, cpd.pat_email,csm.service_name,cpd.med_location, cpd.preferred_time, cpd.remarks,cpd.corp_name,cpd.services ");// +
			// "cpd.med_location, cpd.preferred_time, cpd.remarks,  "
			// +
			// "cpd.date, acm.ACCOUNT_MANAGER_NAME , acm.ACCOUNT_MANAGER_MOBILE, cpd.service_book_time, cpd.pat_dob   ");
			query.append(" from corporate_patience_data as cpd ");
			query.append(" inner join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name ");
			query.append(" inner join cps_service as csm on csm.id=cpd.services ");
			// query.append(" inner join country as con on con.code=cpd.pat_country  ");
			// query.append(" left join cps_billinggroup_his as acm on acm.ACCOUNT_MANAGER_NAME=cpd.ac_manager  ");
			query.append(" where cpd.id = " + feedId + " group by cpd.id");
			// System.out.println("queryqueryfffffffffffffff " + query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			dataMap = new LinkedHashMap<String, String>();
			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					CPSHelper help = new CPSHelper();
					Object[] object = (Object[]) iterator.next();
					dataMap.put("Ticket No", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Corporate Name", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("Patient Type", CUA.getValueWithNullCheck(object[3]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[4]));
					dataMap.put("Patient Mobile", CUA.getValueWithNullCheck(object[5]));
					dataMap.put("Patient Email", CUA.getValueWithNullCheck(object[6]));
					dataMap.put("Service", CUA.getValueWithNullCheck(object[7]));
					dataMap.put("Location", CUA.getValueWithNullCheck(object[8]));
					corp_name = CUA.getValueWithNullCheck(object[11]);
					service_id = CUA.getValueWithNullCheck(object[12]);
					madLoc = CUA.getValueWithNullCheck(object[8]);
					if (CUA.getValueWithNullCheck(object[8]) != null)
					{
						List escData = cbt.executeAllSelectQuery("select id from cps_location where location_name='" + CUA.getValueWithNullCheck(object[8]) + "'", connectionSpace);
						if (escData != null && escData.size() > 0)
						{
							med_location = escData.get(0).toString();

						}
					}
					dataMap.put("Preferred Schedule", CUA.getValueWithNullCheck(object[9]));
				}

			}
			// Service manager
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}

		// ////////System.out.println("kjhdfkghdfkgj   "+dataMap.size());

		return dataMap;
	}

	public Map<String, String> getTicketDetailsForAssignCPS(String feedId)
	{

		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder(); // csm.serv_loc
			query.append("select cpd.id, cpd.ticket, cpd.corp_type, cm.customer_name, cpd.feed_status," + " cpd.patient_name, cpd.uhid, cpd.pat_mobile, cpd.pat_email, cpd.pat_gender, " + "cpd.pat_state, cpd.pat_city,  con.country_name, csm.service_name, " + "cpd.med_location, cpd.preferred_time, cpd.remarks,  " + "cpd.date, cpd.ac_manager , cm.ACCOUNT_MANAGER_MOB, cpd.service_book_time, cpd.pat_dob   ");
			// query.append(" csd.ehc_pack_type,csd.ehc_pack,csd.ipd_spec,csd.ipd_doc,csd.ipd_bed,csd.ipd_pat_type, ");
			// query.append(" csd.daycare_spec,csd.daycare_doc,csd.diagnostics_test,csd.em_spec,csd.em_spec_assis,csd.lab_mod,csd.opd_spec,csd.opd_doc,csd.radio_mod  ");
			query.append(" from corporate_patience_data as cpd ");
			query.append(" inner join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name ");
			query.append(" inner join cps_service as csm on csm.id=cpd.services ");
			query.append(" inner join country as con on con.code=cpd.pat_country  ");
			query.append(" right join cps_services_details as csd on csd.cpsid=cpd.id ");
			// query.append(" left join cps_billinggroup_his as acm on acm.ACCOUNT_MANAGER_NAME=cpd.ac_manager  ");
			// query.append(" inner join dreamsol_bl_corp_hc_pkg as cms on cms.ACCOUNT_MANAGER_NAME=cpd.ac_manager  ");
			query.append(" where cpd.id = " + feedId + " group by cpd.id");
			// System.out.println("queryqueryfffffffffffffff " + query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			dataMap = new LinkedHashMap<String, String>();
			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					CPSHelper help = new CPSHelper();
					Object[] object = (Object[]) iterator.next();
					dataMap.put("Ticket No", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Corporate Type", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("Corporate Name", CUA.getValueWithNullCheck(object[3]));
					dataMap.put("Patient Type", CUA.getValueWithNullCheck(object[4]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[5]));
					dataMap.put("Patient Mobile", CUA.getValueWithNullCheck(object[7]));
					dataMap.put("Patient Email", CUA.getValueWithNullCheck(object[8]));
					dataMap.put("Gender", CUA.getValueWithNullCheck(object[9]));
					dataMap.put("Country", CUA.getValueWithNullCheck(object[12]));
					dataMap.put("State", CUA.getValueWithNullCheck(object[10]));
					dataMap.put("City", CUA.getValueWithNullCheck(object[11]));
					dataMap.put("Service", CUA.getValueWithNullCheck(object[13]));
					dataMap.put("Location", CUA.getValueWithNullCheck(object[14]));
					dataMap.put("Preferred Schedule", CUA.getValueWithNullCheck(object[15]));
					dataMap.put("Remarks", CUA.getValueWithNullCheck(object[16]));
					dataMap.put("Age", CUA.getValueWithNullCheck(object[21]));
					dataMap.put("UHID", CUA.getValueWithNullCheck(object[6]));
					dataMap.put("Ac. Manager", CUA.getValueWithNullCheck(object[18]));
					dataMap.put("Ac. Manager No", CUA.getValueWithNullCheck(object[19]));
					dataMap.put("Booked Schedule", CUA.getValueWithNullCheck(object[20]));
				}

			}
			// Service manager
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return dataMap;
	}

	// take action on assign page redirect
	public String takeActionOnAssign()
	{
		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder(); // csm.serv_loc
			List dataList = null;
			query.append("select cpd.id, cpd.ticket, cpd.corp_type, acm.CUSTOMER_NAME, ");
			query.append(" cpd.feed_status, cpd.patient_name, cpd.uhid, cpd.pat_mobile, ");
			query.append(" cpd.pat_email, cpd.pat_gender, cpd.pat_state, cpd.pat_city,   ");
			query.append(" con.country_name, csm.service_name, cpd.med_location,  ");
			query.append(" cpd.remarks,  cpd.date,  cpd.ac_manager, cpd.service_book_time, ");
			query.append(" cpd.service_remark,cpd.pat_dob , cpd.level, emp.empName,emp.mobOne,cpd.corp_name,cpd.status,cpd.service_manager, ");
			query.append(" csd.opd_spec,csd.opd_doc,csd.ipd_spec,csd.ipd_doc, csd.ehc_pack_type, csd.ehc_pack, csd.em_spec, csd.em_spec_assis, csd.radio_mod, csd.daycare_spec, csd.daycare_doc, csd.lab_mod, csd.diagnostics_test, csd.facilitation_mod, csd.telemedicine_mod");
			query.append(" from corporate_patience_data as cpd ");
			query.append(" inner join cps_service as csm on csm.id=cpd.services ");
			query.append(" inner join country as con on con.code=cpd.pat_country ");
			query.append(" inner join cps_services_details as csd on csd.CPSId=cpd.id ");
			query.append(" left join dreamsol_bl_corp_hc_pkg as acm on acm.id=cpd.corp_name  ");
			query.append(" left join employee_basic as emp on emp.id=cpd.service_manager ");
			query.append(" where cpd.id = " + id);
			// System.out.println("queryquery " + query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			serviceManagerMap = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						serviceManagerMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			bedType = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select bed_type, bed_type as bed from cps_bed_type  group by bed_type order by bed_type", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						bedType.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			paymenetType = new LinkedHashMap<String, String>();
			dataList = new createTable().executeAllSelectQuery("select payment_type, payment_type as pay from cps_payment_type  group by payment_type order by payment_type", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						paymenetType.put(object[0].toString(), object[1].toString());
					}
				}
			}
			dataList.clear();
			serviceMng = CPSHelper.fetchServiceManager(connectionSpace);
			dataMap = new LinkedHashMap<String, String>();
			if (data != null && !data.isEmpty() && data.size() > 0)
			{

				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					serviceName = CUA.getValueWithNullCheck(object[13]);
					med_location = CUA.getValueWithNullCheck(object[14]);
					dataMap.put("Ticket No", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Corporate Type", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("Corporate Name", CUA.getValueWithNullCheck(object[3]));
					dataMap.put("Package Type", CUA.getValueWithNullCheck(object[4]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[5]));
					dataMap.put("Service", CUA.getValueWithNullCheck(object[13]));
					dataMap.put("Location", CUA.getValueWithNullCheck(object[14]));
					dataMap.put("Preferred Schedule", CUA.getValueWithNullCheck(CUA.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[18].toString().split(" ")[0]) + " " + object[18].toString().split(" ")[1])));
					dataMap.put("Remarks", CUA.getValueWithNullCheck(object[15]));
					dataMap.put("Service Manager", CUA.getValueWithNullCheck(object[22]));
					dataMap.put("Mobile", CUA.getValueWithNullCheck(object[23]));
					dataMap.put("Patient Mobile", CUA.getValueWithNullCheck(object[7]));
					dataMap.put("Patient Email", CUA.getValueWithNullCheck(object[8]));
					dataMap.put("Age", CUA.getValueWithNullCheck(object[20]));
					dataMap.put("Escalation Level", CUA.getValueWithNullCheck(object[21]));
					dataMap.put("UHID", CUA.getValueWithNullCheck(object[6]));
					if (serviceName.equalsIgnoreCase("OPD"))
					{
						dataMap.put("Speciality", CUA.getValueWithNullCheck(object[27]));
						dataMap.put("Doctor Name", CUA.getValueWithNullCheck(object[28]));

					} else if (serviceName.equalsIgnoreCase("IPD"))
					{
						dataMap.put("Speciality", CUA.getValueWithNullCheck(object[29]));
						dataMap.put("Doctor Name", CUA.getValueWithNullCheck(object[30]));

					} else if (serviceName.equalsIgnoreCase("EHC"))
					{
						dataMap.put("EHC Packages", CUA.getValueWithNullCheck(object[31]));
						dataMap.put("Packages", CUA.getValueWithNullCheck(object[32]));
					} else if (serviceName.equalsIgnoreCase("Emergency"))
					{
						dataMap.put("Emergency Speciality", CUA.getValueWithNullCheck(object[33]));
						dataMap.put("Assistance", CUA.getValueWithNullCheck(object[34]));
					} else if (serviceName.equalsIgnoreCase("Radiology"))
					{
						dataMap.put("Modality", CUA.getValueWithNullCheck(object[35]));
					} else if (serviceName.equalsIgnoreCase("DayCare"))
					{
						dataMap.put("Speciality", CUA.getValueWithNullCheck(object[36]));
						dataMap.put("Doctor Name", CUA.getValueWithNullCheck(object[37]));
					} else if (serviceName.equalsIgnoreCase("Laboratory"))
					{
						dataMap.put("Laboratory Test", CUA.getValueWithNullCheck(object[38]));
					}

					else if (serviceName.equalsIgnoreCase("Diagnostics"))
					{
						dataMap.put("Test Name", CUA.getValueWithNullCheck(object[39]));
					} else if (serviceName.equalsIgnoreCase("Facilitation"))
					{
						dataMap.put("Facilitation For", CUA.getValueWithNullCheck(object[40]));
					} else if (serviceName.equalsIgnoreCase("Telemedicine"))
					{
						dataMap.put("Telemedicine For", CUA.getValueWithNullCheck(object[41]));
					}

					uhid = "MM";
					servicesID = CUA.getValueWithNullCheck(object[24]);
					feed_status = CUA.getValueWithNullCheck(object[25]);
					ac_manager = CUA.getValueWithNullCheck(object[22]);
					service_id = CUA.getValueWithNullCheck(object[26]);
				}

			}
			// Status List
			feedList = new LinkedHashMap<String, String>();
			if (feed_status.equalsIgnoreCase("Scheduled"))
			{
				// feedList.put("Service Out", "Close");
				feedList.put("Re-Scheduled", "Re-Scheduled");
				feedList.put("Service In", "Mark Arrival");
				feedList.put("Cancel", "Cancel");
				feedList.put("Parked", "Parked");
			} else if (feed_status.equalsIgnoreCase("Service In"))
			{
				feedList.put("Service Out", "Close");
			} else if (feed_status.equalsIgnoreCase("Schedule Parked"))
			{
				feedList.put("Service In", "Mark Arrival");
				feedList.put("Re-Scheduled", "Re-Scheduled");
				feedList.put("Cancel", "Cancel");
			} else if (feed_status.equalsIgnoreCase("Cancel"))
			{
				feedList.put("Re-Scheduled", "Re-Scheduled");
			}
			return "success";
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return "success";
	}

	// Add services fields
	public String addServices()
	{

		String returnString = "error";
		String service_book_time;
		String ser_location = null;
		boolean sessionFlag = ValidateSession.checkSession();
		if (!sessionFlag)
			returnString = "login";
		try
		{
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			int ckhUpdate = 0;
			// Add data to table
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());

			Collections.sort(requestParameterNames);
			if (requestParameterNames != null && requestParameterNames.size() > 0)

			{
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					System.out.println("Parmname: " + Parmname);
					System.out.println("paramValue: " + paramValue);
					if (Parmname.equalsIgnoreCase("locationName") || Parmname.equalsIgnoreCase("serviceName") || Parmname.equalsIgnoreCase("ehc_pack_type") || Parmname.equalsIgnoreCase("ehc_pack") || Parmname.equalsIgnoreCase("em_spec") || Parmname.equalsIgnoreCase("em_spec_assis") || Parmname.equalsIgnoreCase("radio_mod") || Parmname.equalsIgnoreCase("facilitation_mod") || Parmname.equalsIgnoreCase("telemedicine_mod") || Parmname.equalsIgnoreCase("opd_spec") || Parmname.equalsIgnoreCase("opd_doc") || Parmname.equalsIgnoreCase("daycare_spec") || Parmname.equalsIgnoreCase("daycare_doc") || Parmname.equalsIgnoreCase("ipd_spec") || Parmname.equalsIgnoreCase("ipd_doc") || Parmname.equalsIgnoreCase("ipd_bed") || Parmname.equalsIgnoreCase("ipd_pat_type") || Parmname.equalsIgnoreCase("lab_mod")
							|| Parmname.equalsIgnoreCase("diagnostics_test"))
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}

				}
				if ("A".equalsIgnoreCase(request.getParameter("action")))
				{

					// String serviceName =
					// request.getParameter("serviceFiledNameId");
					// String escTimeForDept =
					// escTimeForDept(serviceName,
					// connectionSpace);
					service_book_time = request.getParameter("service_book_time");
					String service_remark = request.getParameter("service_remark");
					String service_manager = request.getParameter("service_manager");
					ser_location = request.getParameter("location");
					String cps_id = request.getParameter("cps_id");

					if (service_manager != null && !service_manager.equalsIgnoreCase("-1") && service_remark != null && service_book_time != null)
					{
						String query = "update corporate_patience_data set status = 'Scheduled',time='" + service_book_time.split(" ")[1] + "' ,service_book_time = '" + DateUtil.convertDateToUSFormat(service_book_time.split(" ")[0]) + " " + service_book_time.split(" ")[1] + "', service_remark='" + service_remark + "', service_manager='" + service_manager + "', next_level_esc_date='" + DateUtil.convertDateToUSFormat(service_book_time.split(" ")[0]) + "', next_level_esc_time='"+DateUtil.addTwoTime(service_book_time.split(" ")[1],"00:15")+"',med_location='" + ser_location + "',date='" + DateUtil.convertDateToUSFormat(service_book_time.split(" ")[0]) + "',level='1' where id =" + cps_id;
						ckhUpdate = cbt.executeAllUpdateQuery(query, connectionSpace);
					}

					if (ckhUpdate > 0)
					{
						CPSHelper cpsHelp = new CPSHelper();
						ob = new InsertDataTable();
						ob.setColName("CPSId");
						ob.setDataName(request.getParameter("cps_id"));
						insertData.add(ob);

						String empNameDepartment = CPSHelper.empNameGetServiceMng(userEmpId, connectionSpace);
						ob = new InsertDataTable();
						ob.setColName("action_by");
						ob.setDataName(userName + "(" + empNameDepartment.split("#")[2] + ")");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("allocate_to");
						ob.setDataName(cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_reason");
						ob.setDataName(request.getParameter("service_remark"));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("escalation_level");
						ob.setDataName("1");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Scheduled");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("dept");
						ob.setDataName("Service Manager");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("service_name");
						ob.setDataName(request.getParameter("serviceFiledNameId"));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("location_name");
						ob.setDataName(ser_location);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("new_service_remarks");
						ob.setDataName(chkNULL(request.getParameter("remarksNew")));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("service_book_time");
						ob.setDataName(DateUtil.convertDateToUSFormat(service_book_time.split(" ")[0]) + " " + service_book_time.split(" ")[1]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("service_manager");
						ob.setDataName(cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0]);
						insertData.add(ob);

						status = cbt.insertIntoTable("cps_status_history", insertData, connectionSpace);
						insertData.clear();

						String queryUpdate = "update cps_services_details set service_name='" + chkNULL(request.getParameter("serviceFiled")) + "', location_name='" + chkNULL(request.getParameter("location")) + "',ehc_pack_type='" + chkNULL(request.getParameter("ehc_pack_type")) + "',ehc_pack='" + chkNULL(request.getParameter("ehc_pack")) + "',em_spec='" + chkNULL(request.getParameter("em_spec")) + "',em_spec_assis='" + chkNULL(request.getParameter("em_spec_assis")) + "',radio_mod='" + chkNULL(request.getParameter("radio_mod")) + "',facilitation_mod='" + chkNULL(request.getParameter("facilitation_mod")) + "',telemedicine_mod='" + chkNULL(request.getParameter("telemedicine_mod")) + "',opd_spec='" + chkNULL(request.getParameter("opd_spec")) + "',opd_doc='"
								+ chkNULL(request.getParameter("opd_doc")) + "',daycare_spec='" + chkNULL(request.getParameter("daycare_spec")) + "',daycare_doc='" + chkNULL(request.getParameter("daycare_doc")) + "',ipd_spec='" + chkNULL(request.getParameter("ipd_spec")) + "',ipd_doc='" + chkNULL(request.getParameter("ipd_doc")) + "',ipd_bed='" + chkNULL(request.getParameter("ipd_bed")) + "',ipd_pat_type='" + chkNULL(request.getParameter("ipd_pat_type")) + "',lab_mod='" + chkNULL(request.getParameter("lab_mod")) + "',diagnostics_test='" + chkNULL(request.getParameter("diagnostics_test")) + "' where cpsid='" + request.getParameter("cps_id") + "'";
						cbt.executeAllUpdateQuery(queryUpdate, connectionSpace);
						// System.out.println("queryUpdate "+queryUpdate);
						String appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);

						if (request.getParameter("service_book_time").contains("AM") || request.getParameter("service_book_time").contains("PM"))
						{
							appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);
						} else
						{
							appoinmentTime = request.getParameter("service_book_time").split(" ")[0] + " at " + timeFormatChange(DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]));
						}

						if (status)
						{
							MsgMailCommunication MMC = new MsgMailCommunication();
							// String
							// bookTime=request.getParameter("service_book_time");
							// String serviceNameAccountMng
							// =
							// getServiceName(request.getParameter("services"),
							// connectionSpace) pat_mobile
							// madLoc;
							String serviceName = request.getParameter("services");
							String docName=null;
							if(serviceName!=null && serviceName.equalsIgnoreCase("DayCare"))
							{
								docName = request.getParameter("daycare_doc");
								System.out.println("docName "+docName);
							}
							else if(serviceName!=null && serviceName.equalsIgnoreCase("IPD"))
							{
								docName = request.getParameter("ipd_doc");
								System.out.println("docName "+docName);
							}
							else if(serviceName!=null && serviceName.equalsIgnoreCase("OPD"))
							{
								docName = request.getParameter("opd_doc");
								System.out.println("docName "+docName);
							}
							System.out.println("docName "+docName+" serviceName "+serviceName);
							
							
							
							/*Dear Jyoti
							Patient Abishek Mishrareachable on 7727804007, Referred from Abhay Singavi, 
							Corporate Name: Referral for appointment at Medanta TheMedicity, on 31-07-2016 at 12:10 PM,
							 Assigned to Prabha Sharma, For Service: Telemedicine, Remarks: ok
							Team Medanta*/

							
							/*	Dear Jyoti Bhatia
								Patient Nina Jaiswal reachable on 9801061157 referred from Credihealth, Corporate name Referral for appointment at Medanta The Medicity, on 26-08-2016 at 02:43 PM assigned to Jyoti Bhatia, for Service EHC, Remarks: patient will reach 2 hour late as informed by him, Ticket No.: CPS-1895
								 Team Medanta*/
							//service manager msg
							
							String ac_mag_details= fetchAccMangMob(request.getParameter("ticketNo"), connectionSpace);
							//String msg = "OPEN: " + request.getParameter("ticketNo") + "  Patient: " + request.getParameter("pat_name") + ", mobile: " + request.getParameter("pat_mobile") + ", Referred from : " + fetchAccMangMob(request.getParameter("ticketNo"), connectionSpace).split("#")[1] + ", Corporate Name: " + request.getParameter("madLoc") + " for appointment at: " + ser_location + ", on " + appoinmentTime + ", assign to: " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + ", For Service : " + request.getParameter("services") + ", Doctor Name : "+docName+", Remarks: " + request.getParameter("service_remark") + "";
							String msg = "Dear "+cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0]+"\n Patient " + request.getParameter("pat_name") +" reachable on "+ request.getParameter("pat_mobile")+" Referred from "+ac_mag_details.split("#")[1]+", Corporate Name: "+request.getParameter("madLoc")+" for appointment at "+ser_location + ", on " + appoinmentTime +  " assign to " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + ", for Service " + request.getParameter("services") + ", Remarks: " + request.getParameter("service_remark") + ", Ticket No.:"+request.getParameter("ticketNo")+"\n Team Medanta";

							MMC.addMessage(cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0], "Service Manager", cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1], msg, request.getParameter("ticketNo"), "Pending", "0", "CPS");

							String ac_mag_mob = fetchAccMangMob(request.getParameter("ticketNo"), connectionSpace).split("#")[0];

							System.out.println("acc_mang_mob " + ac_mag_mob);
							if (!ac_mag_mob.equalsIgnoreCase("NA"))
							{
								

								
								/*Dr I. H. A. Faruqi reachable on 9818424162corporate nameAccenture is scheduled for Emergency 
								on 30-07-2016 at 05:26 PM at Medanta TheMedicity, service manager assigned Prabha Sharma (9971991752) Status- Open
								Team Medanta*/
							


								/*Dear Credihealth,
								AkashChandel reachable on 7727056600 corporate name Referral is scheduled for DayCare on 26-08-2016 at 01:56 PM at Medanta The Medicity, service manager assigned Jyoti Bhatia (9971991753) and Ticket No: CPS-1893 Status- Open 
								Team Medanta*/

								
								
								
								/*String msgAcMng = ""request.getParameter("pat_name") + " Corporate: " + request.getParameter("madLoc") + " is scheduled for " + request.getParameter("services") 
								+ " on " + appoinmentTime + " at " + ser_location + ", service manager assigned " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " (" + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1] + " )";
*/
								String msgAcMng ="Dear "+ac_mag_details.split("#")[1]+",\n"+ request.getParameter("pat_name")+" reachable on "+request.getParameter("pat_mobile")+" corporate name "+request.getParameter("madLoc")+" is scheduled for "+request.getParameter("services")+" on "+appoinmentTime+" at "+ser_location+", service manager assigned "+cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " (" + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1]+") and Ticket No: "+request.getParameter("ticketNo")+" Status- Open \nTeam Medanta ";
								
								
								
								MMC.addMessage("Acc Manager", "Acc Manager", ac_mag_mob, msgAcMng, request.getParameter("ticketNo"), "Pending", "0", "CPS");
								String accmailbody =  CPSHelper.mailBodyAccountManager(ac_mag_details.split("#")[1], request.getParameter("pat_name"), request.getParameter("pat_mobile"), request.getParameter("madLoc") ,request.getParameter("services"), appoinmentTime, ser_location, cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " (" + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[1]+")", request.getParameter("ticketNo") );
						System.out.println(accmailbody);
								if (!ac_mag_details.split("#")[2].toString().equalsIgnoreCase("NA"))

								{
									new MsgMailCommunication().addMail(ac_mag_details.split("#")[1], "", ac_mag_details.split("#")[2], "Appointment Booking Information", accmailbody, "", "Pending", "0", "", "CPS");
								}
							
							
							}

							if (request.getParameter("services").equalsIgnoreCase("Facilitation"))
							{

								// h
								String dept_mag_mob = fetchDeptMangMob("Facilitation", connectionSpace).split("#")[0];

								if (!dept_mag_mob.equalsIgnoreCase("NA"))
								{
									String deptmngSms = "OPEN: " + request.getParameter("ticketNo") + "  Patient: " + request.getParameter("pat_name") + ", mobile: " + request.getParameter("pat_mobile") + ",Referred from : " + fetchAccMangMob(request.getParameter("ticketNo"), connectionSpace).split("#")[1] + " & " + request.getParameter("madLoc") + " as " + request.getParameter("Patient_Type") + " patient for appointment At: " + ser_location + " on " + appoinmentTime + " Assign To: " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " Remarks: " + request.getParameter("service_remark") + " For Service  : " + request.getParameter("services") + "";
									MMC.addMessage("Dept Manager", "Dept Manager", dept_mag_mob, deptmngSms, request.getParameter("ticketNo"), "Pending", "0", "CPS");
								}
							}

							if (request.getParameter("services").equalsIgnoreCase("Emergency"))
							{

								// h
								String dept_mag_mob = fetchDeptMangMob("Emergency", connectionSpace).split("#")[0];
								if (!dept_mag_mob.equalsIgnoreCase("NA"))
								{
									String deptmngSms = "OPEN: " + request.getParameter("ticketNo") + "  Patient: " + request.getParameter("pat_name") + ", mobile: " + request.getParameter("pat_mobile") + ",Referred from : " + fetchAccMangMob(request.getParameter("ticketNo"), connectionSpace).split("#")[1] + " & " + request.getParameter("madLoc") + " as " + request.getParameter("Patient_Type") + " patient for appointment At: " + ser_location + " on " + appoinmentTime + " Assign To: " + cpsHelp.empNameGet(request.getParameter("service_manager"), connectionSpace).split("#")[0] + " Remarks: " + request.getParameter("service_remark") + " For Service  : " + request.getParameter("services") + "";

									MMC.addMessage("Dept Manager", "Dept Manager", dept_mag_mob, deptmngSms, request.getParameter("ticketNo"), "Pending", "0", "CPS");
								}
							}

							// >>>>>>>>>>>>>>>>>>

							if (service_book_time.split(" ")[0].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
							{

							}

							// >>>>>>>>>>>>>>>>>

						}

					}

				} else if ("C".equalsIgnoreCase(request.getParameter("action")))
				{
/*
					String appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);

					if (request.getParameter("service_book_time").contains("AM") || request.getParameter("service_book_time").contains("PM"))
					{
						appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);
					} else
					{
						appoinmentTime = request.getParameter("service_book_time").split(" ")[0] + " at " + timeFormatChange(DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]));
					}*/
					String query = "update corporate_patience_data set status = 'Appointment Close' , remarks='" + request.getParameter("reason") + "',level='1' where id =" + request.getParameter("cps_id");
					ckhUpdate = cbt.executeAllUpdateQuery(query, connectionSpace);
					if (ckhUpdate > 0)
					{
						// ///////////////
						Map<String, Object> wherClause = new HashMap<String, Object>();
						// update in history table
						wherClause.put("CPSId", request.getParameter("cps_id"));
						wherClause.put("action_by", userName);
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("allocate_to", "NA");
						wherClause.put("action_reason", request.getParameter("reason"));
						wherClause.put("escalation_level", "NA");
						wherClause.put("status", "Appointment Close");
						wherClause.put("dept", "OCC");
						if (wherClause != null && wherClause.size() > 0)
						{
							InsertDataTable obclose = new InsertDataTable();
							List<InsertDataTable> insertDataClose = new ArrayList<InsertDataTable>();
							insertDataClose.clear();
							for (Map.Entry<String, Object> entry : wherClause.entrySet())
							{
								obclose = new InsertDataTable();
								obclose.setColName(entry.getKey());
								obclose.setDataName(entry.getValue().toString());
								insertDataClose.add(obclose);
							}
							boolean updateFeed = cbt.insertIntoTable("cps_status_history", insertDataClose, connectionSpace);
							if (updateFeed = true)
							{
							}
							insertDataClose.clear();
						}
					}
				} else if ("P".equalsIgnoreCase(request.getParameter("action")) && request.getParameter("parked_till").toString().length() != 0 && request.getParameter("parked_till").toString() != null)
				{
					System.out.println("Faisal>>> "+request.getParameter("parked_till"));
					//String appoinmentTime = request.getParameter("parked_till").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("parked_till").split(" ")[1]);

					/*if (request.getParameter("parked_till").contains("AM") || request.getParameter("parked_till").contains("PM"))
					{
						appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("parked_till").split(" ")[1]);
					} else
					{
						appoinmentTime = request.getParameter("parked_till").split(" ")[0] + " at " + timeFormatChange(DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]));
					}*/
					String query = "update corporate_patience_data set status = 'Appointment Parked' , remarks='" + request.getParameter("reason") + "', parked_till_date = '" + DateUtil.convertDateToUSFormat(request.getParameter("parked_till").split(" ")[0]) + "',  parked_till_time = '" + request.getParameter("parked_till").split(" ")[1] + "',next_level_esc_date='"+DateUtil.convertDateToUSFormat(request.getParameter("parked_till").split(" ")[0])+"',next_level_esc_time='"+request.getParameter("parked_till").split(" ")[1]+"',level='1'  where id =" + request.getParameter("cps_id");
					ckhUpdate = cbt.executeAllUpdateQuery(query, connectionSpace);

					if (ckhUpdate > 0)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						wherClause.put("CPSId", request.getParameter("cps_id"));
						wherClause.put("action_by", userName);
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("allocate_to", "NA");
						wherClause.put("action_reason", request.getParameter("reason"));
						wherClause.put("escalation_level", "NA");
						wherClause.put("status", "Appointment Parked");
						wherClause.put("dept", "OCC");
						wherClause.put("parked_till", request.getParameter("parked_till"));

						if (wherClause != null && wherClause.size() > 0)
						{
							InsertDataTable obclose = new InsertDataTable();
							List<InsertDataTable> insertDataClose = new ArrayList<InsertDataTable>();
							insertDataClose.clear();
							for (Map.Entry<String, Object> entry : wherClause.entrySet())
							{
								obclose = new InsertDataTable();
								obclose.setColName(entry.getKey());
								obclose.setDataName(entry.getValue().toString());
								insertDataClose.add(obclose);
							}
							boolean updateFeed = cbt.insertIntoTable("cps_status_history", insertDataClose, connectionSpace);
							insertDataClose.clear();

						}
					}

				}
				if ("1".equalsIgnoreCase(request.getParameter("sendSMS")) && "A".equalsIgnoreCase(request.getParameter("action")))
				{

					String appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);

					if (request.getParameter("service_book_time").contains("AM") || request.getParameter("service_book_time").contains("PM"))
					{
						appoinmentTime = request.getParameter("service_book_time").split(" ")[1].split(" ")[0] + " at " + DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]);
					} else
					{
						appoinmentTime = request.getParameter("service_book_time").split(" ")[0] + " at " + timeFormatChange(DateUtil.getcorrecctTime(request.getParameter("service_book_time").split(" ")[1]));
					}
					String smsText = sendPatientAlert("appointment", request.getParameter("pat_name"), request.getParameter("pat_mobile"), request.getParameter("services"), request.getParameter("service_book_time"), ser_location, "", "", connectionSpace, request.getParameter("service_manager"));
					String mailBody1 = new String();
					String serviceName = request.getParameter("services");
					boolean chkTestName = isInteger(request.getParameter("services").trim());
					if (chkTestName == true)
					{
						serviceName = getServiceName(request.getParameter("services"), connectionSpace);
					} else
					{
						serviceName = request.getParameter("services");
					}
					String subjectLine = "Appointment Booking";
					if (serviceName.equalsIgnoreCase("EHC"))
					{

						mailBody1 = CPSHelper.mailBody(smsText, request.getParameter("pat_name"), appoinmentTime, ser_location, request.getParameter("ehc_pack"), connectionSpace);
						subjectLine = "Appointment Booking For Executive Health Check-up (EHC)";
					}

					if (serviceName.equalsIgnoreCase("IPD"))
					{

						mailBody1 = CPSHelper.mailBodyIPD(smsText, request.getParameter("pat_name"), appoinmentTime, ser_location, request.getParameter("ipd_doc"), connectionSpace);
						subjectLine = "Appointment Booking For In-patient department (IPD)";
					}
					/*
					 * if (serviceName.equalsIgnoreCase("OPD")) {
					 * 
					 * //mailBody1 = CPSHelper.mailBodyOPD(smsText,
					 * request.getParameter("pat_name"),
					 * request.getParameter("service_book_time"), ser_location,
					 * request.getParameter("opd_doc"), connectionSpace);
					 * //subjectLine =
					 * "Appointment Booking For Out-patient department (OPD)" ;
					 * }
					 */
					if (!request.getParameter("pat_email").toString().equalsIgnoreCase("NA"))

					{
						new MsgMailCommunication().addMail(request.getParameter("pat_name"), "", request.getParameter("pat_email"), subjectLine, mailBody1, "", "Pending", "0", "", "CPS");
					}
				}
				if (ckhUpdate > 0)
				{
					addActionMessage("Data saved successfully");
					returnString = SUCCESS;
				} else
				{
					addActionMessage("error in data!!!");
					returnString = SUCCESS;
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
			// TODO: handle exception
		}
		return returnString;
	}

	private String timeFormatChange(String time)
	{

		System.out.println(time);
		String str = time;
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date date;
		try
		{
			date = displayFormat.parse(time);
			System.out.println(displayFormat.format(date) + " = " + parseFormat.format(date));
			str = parseFormat.format(date);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(parseFormat.format(date) + " = " +
		// displayFormat.format(date));

		return str;
	}

	private String fetchDeptMangMob(String parameter, SessionFactory connectionSpace)
	{
		// TODO Auto-generated method stub
		String dept_mang_mob = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select emp.empName, emp.mobOne from employee_basic as emp inner join compliance_contacts as cc on cc.emp_id= emp.id inner join subdepartment as sub on cc.forDept_id=sub.id where sub.subdeptname='" + parameter + "' and cc.level='3' and cc.moduleName='CPS'".toString(), connectionSpace);
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				dept_mang_mob = object[1].toString() + "#" + object[0].toString();
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return dept_mang_mob;
	}

	private String fetchAccMangMob(String parameter, SessionFactory connectionSpace)
	{
		// TODO Auto-generated method stub
		String acc_mang_mob = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select distinct cpd.ac_manager, bl.ACCOUNT_MANAGER_MOB, bl.ACCOUNT_OFFICER, bl.ACCOUNT_MANAGER_EMAIL from corporate_patience_data as cpd inner join dreamsol_bl_corp_hc_pkg as bl on bl.ACCOUNT_OFFICER = cpd.ac_manager where ticket='" + parameter + "'".toString(), connectionSpace);
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				acc_mang_mob = object[1].toString() + "#" + object[2].toString()+ "#" + object[3].toString();
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return acc_mang_mob;
	}

	public String chkNULL(String value)
	{
		// System.out.println("value value"+value);
		String sts = "NA";
		if (value == null)
		{
			sts = "NA";
		} else
		{
			sts = value;
		}
		return sts;
	}

	public String ViewPatientDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				patienceMap = new LinkedHashMap<String, String>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();

				StringBuilder query = new StringBuilder();
				query.append("SELECT cpd. id, cpd.pat_country, cpd.pat_state, cpd.pat_city, cpd.pat_email, cm.CUSTOMER_NAME, cpd.pat_dob, cpd.pat_mobile, cpd.pat_gender from corporate_patience_data as cpd ");
				query.append(" left join dreamsol_bl_corp_hc_pkg as cm on cm.id=cpd.corp_name");
				query.append(" where cpd.id = " + id);
				// ////////System.out.println("Query Is "+query.toString());
				List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator it = dataList.iterator(); it.hasNext();)
					{
						Object[] object = (Object[]) it.next();
						patienceMap.put("Country", CUA.getValueWithNullCheck(object[1]));
						patienceMap.put("State", CUA.getValueWithNullCheck(object[2]));
						patienceMap.put("City", CUA.getValueWithNullCheck(object[3]));
						patienceMap.put("Email ID", CUA.getValueWithNullCheck(object[4]));
						patienceMap.put("Corporate Name", CUA.getValueWithNullCheck(object[5]));
						patienceMap.put("Age", CUA.getValueWithNullCheck(object[6]));
						patienceMap.put("Mobile No.", CUA.getValueWithNullCheck(object[7]));
						patienceMap.put("Gender", CUA.getValueWithNullCheck(object[8]));

					}
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// final action on close

	public String actionOnFinal()
	{
		String returnString = "error";
		boolean takeNewRequest = false;
		String preferedTime = null;
		CPSHelper cpsHelp = new CPSHelper();
		boolean sessionFlag = ValidateSession.checkSession();
		if (!sessionFlag)
			returnString = "login";
		try
		{
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			int status = 0;
			String servicesName = null;
			String book_timr = null;
			String ac_name = null;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			String Parmname = null;
			String paramValue = null;
			String drName = null, patName = null, patMob = null, service = null, location = null, appointmentTime = null;
			if ("Cancel".equalsIgnoreCase(request.getParameter("status")) || "Re-Scheduled".equalsIgnoreCase(request.getParameter("status")))
			{

				String cps_id = request.getParameter("cps_id");
				StringBuilder query = new StringBuilder(); // csm.serv_loc
				query.append("select cpd.patient_name,cpd.pat_mobile,csm.service_name,cpd.med_location, cpd.preferred_time,csd.opd_doc,csd.ehc_pack,csd.radio_mod,csd.diagnostics_test,cpd.remarks,csd.facilitation_mod,csd.telemedicine_mod ");// +
				// "cpd.med_location, cpd.preferred_time, cpd.remarks,  "
				// +
				// "cpd.date, acm.ACCOUNT_MANAGER_NAME , acm.ACCOUNT_MANAGER_MOBILE, cpd.service_book_time, cpd.pat_dob   ");
				query.append(" from corporate_patience_data as cpd ");
				query.append(" inner join cps_services_details as csd on cpd.id=csd.cpsid ");
				query.append(" inner join cps_service as csm on csm.id=cpd.services ");
				query.append(" where cpd.id = " + cps_id + " group by cpd.id");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && !data.isEmpty() && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						CPSHelper help = new CPSHelper();
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null)
							patName = object[0].toString();
						if (object[1] != null)
							patMob = object[1].toString();
						if (object[2] != null)
							service = object[2].toString();
						if (object[3] != null)
							location = object[3].toString();
						if (object[4] != null)
							appointmentTime = object[4].toString();
						if (service.equalsIgnoreCase("OPD"))
							drName = object[5].toString();
						else if (service.equalsIgnoreCase("EHC"))
							drName = object[6].toString();
						else if (service.equalsIgnoreCase("Laboratory"))
							drName = object[7].toString();
						else if (service.equalsIgnoreCase("Diagnostic"))
							drName = object[8].toString();
						else if (service.equalsIgnoreCase("Radiology"))
							drName = object[9].toString();
						else if (service.equalsIgnoreCase("Facilitation"))
							drName = object[10].toString();
						else if (service.equalsIgnoreCase("Telemedicine"))
							drName = object[11].toString();

					}

				}

			}
			if (requestParameterNames != null && requestParameterNames.size() > 0)
			{
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					Parmname = it.next().toString();
					paramValue = request.getParameter(Parmname);
					if (Parmname.equalsIgnoreCase("locationName") || Parmname.equalsIgnoreCase("serviceName") || Parmname.equalsIgnoreCase("ehc_pack_type") || Parmname.equalsIgnoreCase("ehc_pack") || Parmname.equalsIgnoreCase("em_spec") || Parmname.equalsIgnoreCase("em_spec_assis") || Parmname.equalsIgnoreCase("radio_mod") || Parmname.equalsIgnoreCase("facilitation_mod") || Parmname.equalsIgnoreCase("telemedicine_mod") || Parmname.equalsIgnoreCase("opd_spec") || Parmname.equalsIgnoreCase("opd_doc") || Parmname.equalsIgnoreCase("daycare_spec") || Parmname.equalsIgnoreCase("daycare_doc") || Parmname.equalsIgnoreCase("ipd_spec") || Parmname.equalsIgnoreCase("ipd_doc") || Parmname.equalsIgnoreCase("ipd_bed") || Parmname.equalsIgnoreCase("ipd_pat_type") || Parmname.equalsIgnoreCase("lab_mod")
							|| Parmname.equalsIgnoreCase("diagnostics_test"))
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}

				}
				String uhidCheck = request.getParameter("uhid");
				if (uhidCheck.length() == 2)
				{
					uhidCheck = "NA";
				} else
				{
					uhidCheck = uhidCheck;
				}
				if (request.getParameter("chkNew").equalsIgnoreCase("1"))
				{

					if (!request.getParameter("preferred_time").equalsIgnoreCase("") && request.getParameter("preferred_time") != null)
					{
						preferedTime = request.getParameter("preferred_time");
					} else
					{
						preferedTime = preferedTime = request.getParameter("preferred_time_cal") + " " + request.getParameter("houra") + ":" + request.getParameter("minutsa") + " " + request.getParameter("ampma");
					}
					takeNewRequest = CPSHelper.addNewRequstOnAck(request.getParameter("cps_id"), request.getParameter("location"), preferedTime, request.getParameter("service"), uhidCheck, request.getParameter("remark"), request.getParameter("ehc_pack"), request.getParameter("ehc_pack_type"), request.getParameter("em_spec"), request.getParameter("em_spec_assis"), request.getParameter("radio_mod"), request.getParameter("opd_spec"), request.getParameter("opd_doc"), request.getParameter("daycare_spec"), request.getParameter("daycare_doc"), request.getParameter("ipd_spec"), request.getParameter("ipd_doc"), request.getParameter("ipd_bad"), request.getParameter("ipd_pat_type"), request.getParameter("lab_mod"), request.getParameter("diagnostics_test"), request.getParameter("remarksNew"), request
							.getParameter("resolved_by"), request.getParameter("facilitation_mod"), request.getParameter("telemedicine_mod"), userName, connectionSpace);
				} else
				{
					takeNewRequest = true;
				}
				if (takeNewRequest)
				{
					String cps_id = request.getParameter("cps_id");
					String query = "";
					if (cps_id != null && !cps_id.equalsIgnoreCase("-1"))
					{
						if ("Parked".equalsIgnoreCase(request.getParameter("status")))
						{
							query = "update corporate_patience_data set status = 'Schedule Parked' ,remarks = '" + request.getParameter("remark") + "',uhid = '" + uhidCheck + "' , next_level_esc_date = '" + DateUtil.convertDateToUSFormat(request.getParameter("parkedTill").split(" ")[0]) + "',  next_level_esc_time = '" + request.getParameter("parkedTill").split(" ")[1] + "',level='1' where id =" + cps_id;
						} else if ("Re-Scheduled".equalsIgnoreCase(request.getParameter("status")))
						{

							if (!request.getParameter("preferred_time").equalsIgnoreCase("") && request.getParameter("preferred_time") != null)
							{
								preferedTime = request.getParameter("preferred_time");
							} else
							{
								preferedTime = preferedTime = request.getParameter("preferred_time_cal") + " " + request.getParameter("houra") + ":" + request.getParameter("minutsa") + " " + request.getParameter("ampma");
							}
							query = "update corporate_patience_data set status = 'Scheduled',time='" + convert24HourFormat(preferedTime).split(" ")[1] + "' ,remarks = '" + request.getParameter("remark") + "',services = '" + request.getParameter("service") + "',med_location = '" + request.getParameter("location") + "',service_manager = '" + request.getParameter("service_managerB") + "',uhid = '" + uhidCheck + "' , next_level_esc_date = '" + DateUtil.convertDateToUSFormat(preferedTime.split(" ")[0]) + "',  next_level_esc_time = '"+DateUtil.addTwoTime(preferedTime.split(" ")[1],"00:15")+"',service_book_time='" + DateUtil.convertDateToUSFormat(convert24HourFormat(preferedTime).split(" ")[0]) + " " + convert24HourFormat(preferedTime).split(" ")[1] + "',date='" + DateUtil.convertDateToUSFormat(convert24HourFormat(preferedTime).split(" ")[0])
									+ "',level='1' where id =" + cps_id;
							// query =
							// "update corporate_patience_data set status = 'Scheduled',time='"+convert24HourFormat(preferedTime).split(" ")[1]+"' ,remarks = '"
							// +
							// request.getParameter("remark")
							// +
							// "',services = '" +
							// request.getParameter("service")
							// +
							// "',med_location = '" +
							// request.getParameter("location")
							// +
							// "',service_manager = '" +
							// request.getParameter("service_managerB")
							// +
							// "',uhid = '" + uhidCheck +
							// "' , next_level_esc_date = '"
							// +
							// DateUtil.convertDateToUSFormat(preferedTime.split(" ")[0])
							// +
							// "',  next_level_esc_time = '00:00',service_book_time='"
							// +
							// DateUtil.convertDateToUSFormat(convert24HourFormat(preferedTime).split(" ")[0])+" "+convert24HourFormat(preferedTime).split(" ")[1]+
							// "',date='" +
							// DateUtil.convertDateToUSFormat(convert24HourFormat(preferedTime).split(" ")[0])
							// + "' where id =" + cps_id;
							// System.out.println("Re-Shedule >>> "
							// + query);
						} else if ("Service In".equalsIgnoreCase(request.getParameter("status")))
						{
							String serviceInTat = serviceWiseTAT(request.getParameter("service_name"), connectionSpace);
							query = "update corporate_patience_data set status = '" + request.getParameter("status") + "', uhid = '" + uhidCheck + "',remarks = '" + request.getParameter("remark") + "', next_level_esc_date='" + serviceInTat.split("#")[0] + "', next_level_esc_time='" + serviceInTat.split("#")[1] + "',level='1' where id =" + cps_id;
						} else
						{
							query = "update corporate_patience_data set status = '" + request.getParameter("status") + "', uhid = '" + uhidCheck + "',remarks = '" + request.getParameter("remark") + "',level='1' where id =" + cps_id;
						}
						status = cbt.executeAllUpdateQuery(query, connectionSpace);
					}
				}
			}
			if (status > 0)
			{
				boolean updateFeed = false;

				List escData = cbt.executeAllSelectQuery("select service_manager,service_book_time from corporate_patience_data where id='" + request.getParameter("cps_id") + "'", connectionSpace);
				if (escData != null && escData.size() > 0)
				{
					for (Iterator iterator = escData.iterator(); iterator.hasNext();)
					{
						Object[] obdata = (Object[]) iterator.next();
						ac_name = obdata[0].toString();
						book_timr = obdata[1].toString();
					}
				}

				ob = new InsertDataTable();
				ob.setColName("CPSId");
				ob.setDataName(request.getParameter("cps_id"));
				insertData.add(ob);

				String empNameDepartment = CPSHelper.empNameGetServiceMng(userEmpId, connectionSpace);
				ob = new InsertDataTable();
				ob.setColName("action_by");
				ob.setDataName(userName + "(" + empNameDepartment.split("#")[2] + ")");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				if ("Re-Scheduled".equalsIgnoreCase(request.getParameter("status")))
				{
					ob = new InsertDataTable();
					ob.setColName("allocate_to");
					ob.setDataName(cpsHelp.empNameGet(request.getParameter("service_managerB"), connectionSpace).split("#")[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_manager");
					ob.setDataName(cpsHelp.empNameGet(request.getParameter("service_managerB"), connectionSpace).split("#")[0]);
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("allocate_to");
					ob.setDataName(cpsHelp.empNameGet(ac_name, connectionSpace).split("#")[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_manager");
					ob.setDataName(cpsHelp.empNameGet(ac_name, connectionSpace).split("#")[0]);
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("action_reason");
				ob.setDataName(request.getParameter("remark"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_level");
				ob.setDataName("NA");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("dept");
				ob.setDataName("Service Manager");
				insertData.add(ob);

				if ("Re-Scheduled".equalsIgnoreCase(request.getParameter("status")))
				{
					String serviceName = new CPSHelper().fetcServiceName(request.getParameter("service"), connectionSpace);
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Scheduled");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(serviceName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("location"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_book_time");
					// ob.setDataName(convert24HourFormat(preferedTime));
					ob.setDataName(preferedTime);
					insertData.add(ob);

					String queryUpdate = "update cps_services_details set service_name='" + chkNULL(request.getParameter("service")) + "',location_name='" + chkNULL(request.getParameter("location")) + "',ehc_pack_type='" + chkNULL(request.getParameter("ehc_pack_type")) + "',ehc_pack='" + chkNULL(request.getParameter("ehc_pack")) + "',em_spec='" + chkNULL(request.getParameter("em_spec")) + "',em_spec_assis='" + chkNULL(request.getParameter("em_spec_assis")) + "',radio_mod='" + chkNULL(request.getParameter("radio_mod")) + "',facilitation_mod='" + chkNULL(request.getParameter("facilitation_mod")) + "',telemedicine_mod='" + chkNULL(request.getParameter("telemedicine_mod")) + "',opd_spec='" + chkNULL(request.getParameter("opd_spec")) + "',opd_doc='" + chkNULL(request.getParameter("opd_doc"))
							+ "',daycare_spec='" + chkNULL(request.getParameter("daycare_spec")) + "',daycare_doc='" + chkNULL(request.getParameter("daycare_doc")) + "',ipd_spec='" + chkNULL(request.getParameter("ipd_spec")) + "',ipd_doc='" + chkNULL(request.getParameter("ipd_doc")) + "',ipd_bed='" + chkNULL(request.getParameter("ipd_bed")) + "',ipd_pat_type='" + chkNULL(request.getParameter("ipd_pat_type")) + "',lab_mod='" + chkNULL(request.getParameter("lab_mod")) + "',diagnostics_test='" + chkNULL(request.getParameter("diagnostics_test")) + "' where cpsid='" + request.getParameter("cps_id") + "'";

					cbt.executeAllUpdateQuery(queryUpdate, connectionSpace);
				} else if ("Parked".equalsIgnoreCase(request.getParameter("status")))
				{
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Schedule Parked");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(request.getParameter("service_name"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("med_location"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("parked_till");
					ob.setDataName(request.getParameter("parkedTill"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_book_time");
					ob.setDataName(book_timr);
					insertData.add(ob);

				} else if ("Cancel".equalsIgnoreCase(request.getParameter("status")))
				{
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName(request.getParameter("status"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(request.getParameter("service_name"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("med_location"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_book_time");
					ob.setDataName(book_timr);
					insertData.add(ob);
				} else if ("Service In".equalsIgnoreCase(request.getParameter("status")))
				{
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName(request.getParameter("status"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(request.getParameter("service_name"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("med_location"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_book_time");
					ob.setDataName(book_timr);
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("resolved_by");
					ob.setDataName(request.getParameter("resolved_by"));
					ob.setDataName(cpsHelp.empNameGet(request.getParameter("resolved_by"), connectionSpace).split("#")[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName(request.getParameter("status"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_name");
					ob.setDataName(request.getParameter("service_name"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location_name");
					ob.setDataName(request.getParameter("med_location"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("service_book_time");
					ob.setDataName(book_timr);
					insertData.add(ob);
				}
				updateFeed = cbt.insertIntoTable("cps_status_history", insertData, connectionSpace);
				insertData.clear();
				if (updateFeed = true)
				{

					if ("Re-Scheduled".equalsIgnoreCase(request.getParameter("status")))
					{
						if (patMob != null)
						{
							if ("1".equalsIgnoreCase(request.getParameter("sendSMS")))
							{
								sendPatientAlert(request.getParameter("status"), patName, patMob, service, appointmentTime, location, drName, preferedTime, connectionSpace, " ");
							}
						}
					}
				}
				addActionMessage("Data saved successfully");
				returnString = SUCCESS;
			} else
			{
				addActionMessage("error in data!!!");
				returnString = SUCCESS;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
			// TODO: handle exception
		}
		return returnString;

	}

	private String convert24HourFormat(String preferedTime)
	{
		// TODO Auto-generated method stub
		String temp = preferedTime;
		if (preferedTime.contains("PM"))
		{
			String str = preferedTime.split(" ")[1];
			str = str.split(":")[0];
			Integer hr = Integer.parseInt(str) + 12;

			temp = preferedTime.split(" ")[0] + " " + hr + ":" + (preferedTime.split(" ")[1]).split(":")[1];
		} else if (preferedTime.contains("AM"))
		{
			temp = preferedTime.split(" ")[0] + " " + preferedTime.split(" ")[1];
		} else
		{
			temp = preferedTime;
		}

		return temp;
	}

	/*
	 * ######################################################OLD Code for
	 * CPS###########################################
	 */

	public List<String> fillDDList(String DDvalue)
	{
		List<String> listData = new ArrayList<String>();
		String[] temp = DDvalue.split("#");
		for (int i = 0; i < temp.length; i++)
		{
			listData.add(temp[i]);

		}

		return listData;
	}

	public String services_name(String servicesName)
	{
		String[] temp = servicesName.split(",");
		String services = "";
		for (int i = 0; i < temp.length; i++)
		{
			services = services + "'" + temp[i].trim() + "',";
		}
		services = services.substring(0, (services.length() - 1));

		return services;
	}

	private Map<String, String> getAccmanagerList()
	{
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("Manab", "Mr. Manab");
		tempMap.put("Abhay", "Mr. Abhay");
		tempMap.put("Kamlesh", "Mr. Kamlesh");
		tempMap.put("Prabhat", "Mr. Prabhat");

		return tempMap;

	}

	private Map<String, String> getNodataMap()
	{

		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("-1", "No Data");

		return tempMap;

	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String counterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();
				qry.append(" select count(*),status from corporate_patience_data where id!=0");
				String empid = (String) session.get("empName").toString();// .trim().split("-")[1];
				String[] subModuleRightsArray = CPSHelper.getSubModuleRights(empid, connectionSpace);

				// ////////System.out.println(minDateValue+"   "+maxDateValue);

				if (minDateValue != null && !minDateValue.equalsIgnoreCase(""))
				{
					if (minDateValue.split("-")[0].length() > 2)
					{
						qry.append("  and date  BETWEEN '" + minDateValue + "' and  '" + maxDateValue + "'");
					} else
					{
						qry.append(" and date  BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' and  '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
					}
				}

				qry.append(" group by status  ");
				// System.out.println("qry  "+qry);
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					String Stage1 = "", Stage2 = "", Resolved = "";

					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							if (object[1].toString().equalsIgnoreCase("Service Out"))
							{
								Resolved = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Appointment"))
							{
								Stage1 = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Service In"))
							{
								Stage2 = object[0].toString();
							}

						}
					}
					obj.put("Stage1", Stage1);
					obj.put("Stage2", Stage2);
					obj.put("Resolved", Resolved);

					// jsonArr.add(obj);
				}

				qry.setLength(0);
				qry.append(" select count(*) , ser.service_name from corporate_patience_data as cpd inner join cps_service as ser on ser.id=cpd.services where cpd.id!=0  ");
				if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
				{
					for (String s : subModuleRightsArray)
					{
						if (s.equals("cpsService_VIEW"))
						{
							String subDept = null;
							subDept = CPSHelper.getSubDeptByEmpId((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (subDept != null && !subDept.equalsIgnoreCase(""))
							{
								qry.append(" and cpd.services in (select id from cps_service where service_name in(" + subDept.substring(0, subDept.length() - 1) + "))");
							}
						}
						if (s.equals("cpsLocation_VIEW"))
						{
							String location = null;
							location = CPSHelper.getLocationName((String) session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
							if (location != null && !location.equalsIgnoreCase("") && !location.contains("All"))
							{
								qry.append(" and cpd.med_location In(" + location.substring(0, location.length() - 1) + ")");
							}
						}
					}
				}
				if (minDateValue != null && !minDateValue.equalsIgnoreCase(""))
				{
					if (minDateValue.split("-")[0].length() > 2)
					{
						qry.append(" and cpd.date  BETWEEN '" + minDateValue + "' and  '" + maxDateValue + "'");
					} else
					{
						qry.append(" and cpd.date  BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' and  '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
					}
				}

				qry.append(" group by cpd.services ");
				// System.out.println("second query "+qry);
				List dataser = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (dataser != null && !dataser.isEmpty())
				{
					String EHC = "", IPD = "", Radiology = "", Facilitation = "", Telemedicine = "", OPD = "", DayCare = "", Laboratory = "", Emergency = "";

					for (Iterator iterator = dataser.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							if (object[1].toString().equalsIgnoreCase("EHC"))
							{
								EHC = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("IPD"))
							{
								IPD = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Radiology"))
							{
								Radiology = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Facilitation"))
							{
								Facilitation = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Telemedicine"))
							{
								Telemedicine = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("OPD"))
							{
								OPD = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("DayCare"))
							{
								DayCare = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Laboratory"))
							{
								Laboratory = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Emergency"))
							{
								Emergency = object[0].toString();
							}

						}
					}
					objSer.put("EHC", EHC);
					objSer.put("IPD", IPD);
					objSer.put("Radiology", Radiology);
					objSer.put("OPD", OPD);
					objSer.put("DayCare", DayCare);
					objSer.put("Laboratory", Laboratory);
					objSer.put("Emergency", Emergency);
					objSer.put("Facilitation", Facilitation);
					objSer.put("Telemedicine", Telemedicine);

				}

				jsonArr.add(obj);
				jsonArr.add(objSer);
				// ////////System.out.println(jsonArr.size());
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

	// fetch the package name with location wise

	public String fetchSpecLocData()
	{

		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();

				if (Loc.equalsIgnoreCase("Medanta Mediclinic"))
				{
					Loc = "Mediclinic";
					if (spec != null && !spec.equalsIgnoreCase("") && !spec.equalsIgnoreCase("-1"))
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic like '%Mediclinic%' and PRACTITIONER_NAME='" + spec + "' group by speciality");
					else
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic like '%Mediclinic%' group by speciality");
				} else if (Loc.equalsIgnoreCase("Cybercity Medanta"))
				{
					Loc = "Cybercity";
					if (spec != null && !spec.equalsIgnoreCase("") && !spec.equalsIgnoreCase("-1"))
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic  like '%Cybercity%' and PRACTITIONER_NAME='" + spec + "' group by speciality");
					else
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic  like '%Cybercity%'  group by speciality");
				} else
				{
					if (spec != null && !spec.equalsIgnoreCase("") && !spec.equalsIgnoreCase("-1"))
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic not like '%Cybercity%'  and clinic not like '%Mediclinic%' and PRACTITIONER_NAME='" + spec + "' group by speciality");
					else
						qry.append("select id, speciality, clinic from cps_doctor_his where clinic not like '%Cybercity%'  and clinic not like '%Mediclinic%' group by speciality");
				}
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							obj.put("id", object[1]);
							obj.put("Name", object[1]);
							jsonArr.add(obj);
						}
					}
					returnResult = SUCCESS;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// fetch the package name with location wise

	public String fetchPackageData()
	{
		// System.out.println("pack "+pack);
		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();
				// //System.out.println("pack "+pack);
				if (!pack.equalsIgnoreCase("Standard"))
				{
					if (dataFor != "NA")
					{
						qry.append("select id, HEALTH_PKG_NAME from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME= (Select CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg where id = " + madLoc + ");");
					} else
					{
						qry.append("select id, HEALTH_PKG_NAME from dreamsol_bl_corp_hc_pkg where CUSTOMER_NAME= (Select CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg where id = " + madLoc + ");");
					}
					// qry.append(" select id, pack_subpack from corp_package where pack_loc = '"+madLoc+"' and pack_type='"+pack+"' ");
					System.out.println(qry);
					List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

					if (data != null && !data.isEmpty())
					{

						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[1] != null)
							{

								obj.put("id", object[1]);
								obj.put("Name", object[1]);
								jsonArr.add(obj);

							}
						}

						// ////////System.out.println(jsonArr.size());
						returnResult = SUCCESS;
					}
				} else
				{
					// //System.out.println("hit");
					StringBuilder qry1 = new StringBuilder();
					qry1.append("select id, std_pack from cps_standard_packages where status ='Active' and location_name='" + med_location + "'");
					List data1 = new createTable().executeAllSelectQuery(qry1.toString(), connectionSpace);
					if (data1 != null && !data1.isEmpty())
					{

						for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
						{
							Object[] object1 = (Object[]) iterator1.next();
							if (object1[1] != null)
							{
								obj.put("id", object1[1]);
								obj.put("Name", object1[1]);
								jsonArr.add(obj);
							}
						}
					}
					data1.clear();
				}
				returnResult = SUCCESS;

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String fetchModality()
	{
		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();
				String location = null;
				location = new CPSHelper().fetchLocationID(med_location.trim(), connectionSpace);
				System.out.println("Location " + location + "  servicesID " + servicesID);
				if (servicesID != null && location != null)
				{
					qry.append("select distinct modality,modality as name from cps_modality where service_type='" + servicesID + "' and location_name='" + location + "' and status='Active' order by modality  ");
				} else
				{
					qry.append("select distinct modality,modality as name from cps_modality where and status='Active'order by modality  ");
				}
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[1]);
							obj.put("Name", object[1]);
							jsonArr.add(obj);
						}
					}
					returnResult = SUCCESS;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// Fetch Bed Type
	public String fetchBedType()
	{
		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();
				String location = null;
				location = new CPSHelper().fetchLocationID(med_location.trim(), connectionSpace);
				if (servicesID != null && location != null)
				{
					qry.append("select distinct bed_type,bed_type as name from cps_bed_type where service_type='" + servicesID + "' and location_name='" + location + "' order by bed_type  ");
				} else
				{
					qry.append("select distinct bed_type,bed_type as name from cps_bed_type order by bed_type  ");
				}
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[1]);
							obj.put("Name", object[1]);
							jsonArr.add(obj);
						}
					}
					returnResult = SUCCESS;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// get doctor name
	public String fetchDoctorData()
	{

		String returnResult = "ERROR";
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject objSer = new JSONObject();
				StringBuilder qry = new StringBuilder();

				if (Loc.equalsIgnoreCase("Medanta Mediclinic"))
				{
					if (spec != null && !spec.equalsIgnoreCase(""))
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic like '%Mediclinic%' and speciality = '" + spec + "' group by PRACTITIONER_NAME");
					else
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic like '%Mediclinic%' group by PRACTITIONER_NAME");
				} else if (Loc.equalsIgnoreCase("Cybercity Medanta"))
				{
					if (spec != null && !spec.equalsIgnoreCase(""))
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic like '%Cybercity%' and speciality = '" + spec + "' group by PRACTITIONER_NAME");
					else
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic like '%Cybercity%' group by PRACTITIONER_NAME");
				} else
				{
					if (spec != null && !spec.equalsIgnoreCase(""))
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic not like '%Cybercity%'  and clinic not like '%Mediclinic%' and speciality = '" + spec + "' group by PRACTITIONER_NAME");
					else
						qry.append("select id, PRACTITIONER_NAME from cps_doctor_his where clinic not like '%Cybercity%'  and clinic not like '%Mediclinic%' group by PRACTITIONER_NAME");
				}
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[1]);
							obj.put("Name", object[1]);
							jsonArr.add(obj);
						}
					}
					returnResult = SUCCESS;
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// get age from DOB

	public String ageView()
	{

		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			jsonArr = new JSONArray();
			JSONObject obj = new JSONObject();
			try
			{

				// ////////System.out.println("birthDate "+date);

				if (date.contains("/"))
				{
					try
					{

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date birthDate = sdf.parse(date);
						// ////////System.out.println("birthDate "+birthDate);
						Age age = calculateAge(birthDate);
						// ////////System.out.println("age "+age);

						obj.put("age", age);
						obj.put("ageY", "0");
						jsonArr.add(obj);
						return SUCCESS;

					} catch (Exception e)
					{
						// TODO: handle exception

						obj.put("age", "correct fromat is DD/MM/YYYY");
						obj.put("ageY", "2");
						jsonArr.add(obj);
						e.printStackTrace();
						return SUCCESS;
					}

				}

				else if (date.contains("-"))
				{
					date = date.split("-")[0] + "/" + date.split("-")[1] + "/" + date.split("-")[2];
					// ////////System.out.println("birthDate "+date);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date birthDate = sdf.parse(date);
					// ////////System.out.println("birthDate "+birthDate);
					Age age = calculateAge(birthDate);
					// ////////System.out.println("age "+age);
					obj.put("age", age);
					obj.put("ageY", "0");
					jsonArr.add(obj);
					return SUCCESS;
				} else
				{
					obj.put("age", date.toString());
					obj.put("ageY", "1");
					jsonArr.add(obj);
					return SUCCESS;
				}
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

	public String fetchService()
	{
		String returnresult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				serviceManagerMap = new LinkedHashMap<String, String>();
				List dataList = new createTable().executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							serviceManagerMap.put(object[0].toString(), object[1].toString());
						}
					}
				}

				returnresult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnresult = ERROR;
			}
		} else
		{
			return LOGIN;
		}
		return returnresult;
	}

	private boolean parseInt(String string)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public static Age calculateAge(Date birthDate)
	{
		int years = 0;
		int months = 0;
		int days = 0;
		// create calendar object for birth day
		Calendar birthDay = Calendar.getInstance();
		birthDay.setTimeInMillis(birthDate.getTime());
		// create calendar object for current day
		long currentTime = System.currentTimeMillis();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(currentTime);
		// Get difference between years
		years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		int currMonth = now.get(Calendar.MONTH) + 1;
		int birthMonth = birthDay.get(Calendar.MONTH) + 1;
		// Get difference between months
		months = currMonth - birthMonth;
		// if month difference is in negative then reduce years by one and
		// calculate the number of months.
		if (months < 0)
		{
			years--;
			months = 12 - birthMonth + currMonth;
			if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
				months--;
		} else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			years--;
			months = 11;
		}
		// Calculate the days
		if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
			days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
		else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			int today = now.get(Calendar.DAY_OF_MONTH);
			now.add(Calendar.MONTH, -1);
			days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
		} else
		{
			days = 0;
			if (months == 12)
			{
				years++;
				months = 0;
			}
		}
		// Create new Age object
		return new Age(days, months, years);
	}

	public String beforeViewActivityHistoryData()
	{

		getTicketDetailsForAssignCPS(id);
		try
		{

			List dataList = null;
			String orderName = "";
			cpsHisList = new ArrayList<ArrayList<String>>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append(" select oh.status,oh.action_date, oh.action_time, oh.action_reason, oh.escalation_level,  oh.action_by, oh.allocate_to,  ");
			query.append(" oh.parked_till,oh.service_book_time,oh.service_manager,oh.parked_till as park,oh.resolved_by,oh.location_name, ");
			query.append(" oh.ehc_pack_type,oh.ehc_pack,oh.em_spec,oh.em_spec_assis,oh.radio_mod,oh.opd_spec,oh.opd_doc,oh.daycare_spec,oh.daycare_doc,oh.ipd_spec,  ");
			query.append(" oh.ipd_doc,oh.ipd_bed,oh.ipd_pat_type,oh.lab_mod,oh.diagnostics_test,oh.new_service_remarks ,oh.facilitation_mod,oh.telemedicine_mod ");
			query.append(" from cps_status_history as oh  ");
			query.append(" where oh.CPSId = '" + id + "' GROUP by oh.id ");
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			ArrayList<String> tempList1 = null;
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					tempList1 = new ArrayList<String>();
					if (object[0].toString().contains("Parked"))
					{
						tempList1.add(CUA.getValueWithNullCheck(object[0]) + " [" + CUA.getValueWithNullCheck(object[7]) + "]");

					} else
					{
						tempList1.add(CUA.getValueWithNullCheck(object[0]));
					}
					tempList1.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1])) + " , " + CUA.getValueWithNullCheck(object[2]).substring(0, 5));
					tempList1.add(CUA.getValueWithNullCheck(object[2]));
					if (object[3] != null)
					{
						tempList1.add(CUA.getValueWithNullCheck(object[3]));
					} else
					{
						tempList1.add("Auto");
					}
					if (object[4] != null && !"".equalsIgnoreCase(object[4].toString()))
					{
						tempList1.add(CUA.getValueWithNullCheck(object[4]));
					} else
					{
						tempList1.add(CUA.getValueWithNullCheck(object[4]));
					}
					if (object[5] != null)
					{
						tempList1.add(CUA.getValueWithNullCheck(object[5]));
					} else
					{
						tempList1.add("Auto");
					}
					if (object[6] == null)
					{
						tempList1.add("Auto");

					} else
					{
						tempList1.add(object[6].toString());
					}
					// 
					if (!object[8].toString().equalsIgnoreCase("NA"))
					{
						tempList1.add(CUA.getValueWithNullCheck(CUA.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[8].toString().split(" ")[0]) + " " + object[8].toString().split(" ")[1])));
					} else
					{
						tempList1.add(CUA.getValueWithNullCheck(object[8]));
					}
					tempList1.add(CUA.getValueWithNullCheck(object[9]));
					tempList1.add(CUA.getValueWithNullCheck(object[10]));
					tempList1.add(CUA.getValueWithNullCheck(object[11]));
					tempList1.add(CUA.getValueWithNullCheck(object[12]));
					tempList1.add(CUA.getValueWithNullCheck(object[13]));
					tempList1.add(CUA.getValueWithNullCheck(object[14]));
					tempList1.add(CUA.getValueWithNullCheck(object[15]));
					tempList1.add(CUA.getValueWithNullCheck(object[16]));
					tempList1.add(CUA.getValueWithNullCheck(object[17]));
					tempList1.add(CUA.getValueWithNullCheck(object[18]));
					tempList1.add(CUA.getValueWithNullCheck(object[19]));
					tempList1.add(CUA.getValueWithNullCheck(object[20]));
					tempList1.add(CUA.getValueWithNullCheck(object[21]));

					tempList1.add(CUA.getValueWithNullCheck(object[22]));
					tempList1.add(CUA.getValueWithNullCheck(object[23]));
					tempList1.add(CUA.getValueWithNullCheck(object[24]));
					tempList1.add(CUA.getValueWithNullCheck(object[25]));
					tempList1.add(CUA.getValueWithNullCheck(object[26]));
					tempList1.add(CUA.getValueWithNullCheck(object[27]));
					tempList1.add(CUA.getValueWithNullCheck(object[28]));
					tempList1.add(CUA.getValueWithNullCheck(object[29]));

					cpsHisList.add(tempList1);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private String serviceWiseTAT(String parameter, SessionFactory connectionSpace)
	{
		String esctm = "";
		try
		{
			StringBuilder empuery = new StringBuilder();
			boolean chkTestName = isInteger(parameter.trim());
			if (chkTestName == true)
			{
				empuery.append(" select l1Tol2 from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept=(select id from subdepartment where subdeptname=(select service_name from cps_service where id ='" + parameter + "' and deptid=(select id from department where deptName='Patient Care'))");
			} else
			{
				empuery.append(" select l1Tol2 from escalation_cps_detail where escLevelL1L2='Customised' and escSubDept=(select id from subdepartment where subdeptname='" + parameter + "' and deptid=(select id from department where deptName='Patient Care')) ");
			}
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			if (escData != null && escData.size() > 0)
			{
				esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escData.get(0).toString(), "Y");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return esctm;

	}

	public String getHeadingName()
	{
		return headingName;
	}

	public void setHeadingName(String headingName)
	{
		this.headingName = headingName;
	}

	public List getPageFieldsColumns()
	{
		return pageFieldsColumns;
	}

	public void setPageFieldsColumns(List pageFieldsColumns)
	{
		this.pageFieldsColumns = pageFieldsColumns;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public List<ConfigurationUtilBean> getServicePageFieldsColumns()
	{
		return servicePageFieldsColumns;
	}

	public void setServicePageFieldsColumns(List<ConfigurationUtilBean> servicePageFieldsColumns)
	{
		this.servicePageFieldsColumns = servicePageFieldsColumns;
	}

	public List<Object> getCpsGridData()
	{
		return cpsGridData;
	}

	public void setCpsGridData(List<Object> cpsGridData)
	{
		this.cpsGridData = cpsGridData;
	}

	public List<ConfigurationUtilBean> getAddFields()
	{
		return addFields;
	}

	public void setAddFields(List<ConfigurationUtilBean> addFields)
	{
		this.addFields = addFields;
	}

	public Map<String, String> getAcManagerMap()
	{
		return acManagerMap;
	}

	public void setAcManagerMap(Map<String, String> acManagerMap)
	{
		this.acManagerMap = acManagerMap;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public Map<String, String> getServiceMap()
	{
		return serviceMap;
	}

	public void setServiceMap(Map<String, String> serviceMap)
	{
		this.serviceMap = serviceMap;
	}

	public JSONArray getServicelocation()
	{
		return servicelocation;
	}

	public void setServicelocation(JSONArray servicelocation)
	{
		this.servicelocation = servicelocation;
	}

	public String getService_id()
	{
		return service_id;
	}

	public void setService_id(String service_id)
	{
		this.service_id = service_id;
	}

	public List<GridDataPropertyView> getMasterViewPropCPS()
	{
		return masterViewPropCPS;
	}

	public void setMasterViewPropCPS(List<GridDataPropertyView> masterViewPropCPS)
	{
		this.masterViewPropCPS = masterViewPropCPS;
	}

	public Map<String, String> getStateMap()
	{
		return stateMap;
	}

	public void setStateMap(Map<String, String> stateMap)
	{
		this.stateMap = stateMap;
	}

	public String getStateId()
	{
		return stateId;
	}

	public void setStateId(String stateId)
	{
		this.stateId = stateId;
	}

	public String getPatient_details()
	{
		return patient_details;
	}

	public void setPatient_details(String patient_details)
	{
		this.patient_details = patient_details;
	}

	public Map<String, String> getServiceManagerMap()
	{
		return serviceManagerMap;
	}

	public void setServiceManagerMap(Map<String, String> serviceManagerMap)
	{
		this.serviceManagerMap = serviceManagerMap;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getMadLoc()
	{
		return madLoc;
	}

	public void setMadLoc(String madLoc)
	{
		this.madLoc = madLoc;
	}

	public String getPack()
	{
		return pack;
	}

	public void setPack(String pack)
	{
		this.pack = pack;
	}

	public Map<String, String> getSpecMap()
	{
		return SpecMap;
	}

	public void setSpecMap(Map<String, String> specMap)
	{
		SpecMap = specMap;
	}

	public String getPefTime()
	{
		return pefTime;
	}

	public void setPefTime(String pefTime)
	{
		this.pefTime = pefTime;
	}

	public String getSpec()
	{
		return spec;
	}

	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
	}

	public JSONArray getService()
	{
		return service;
	}

	public void setService(JSONArray service)
	{
		this.service = service;
	}

	public ArrayList<ArrayList<String>> getCpsHisList()
	{
		return cpsHisList;
	}

	public void setCpsHisList(ArrayList<ArrayList<String>> cpsHisList)
	{
		this.cpsHisList = cpsHisList;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public String getMaxDateValue()
	{
		return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue)
	{
		this.maxDateValue = maxDateValue;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public List<String> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<String> dataList)
	{
		this.dataList = dataList;
	}

	public String getFeed_status()
	{
		return feed_status;
	}

	public void setFeed_status(String feed_status)
	{
		this.feed_status = feed_status;
	}

	public String getStatus_type()
	{
		return status_type;
	}

	public void setStatus_type(String statusType)
	{
		status_type = statusType;
	}

	public String getCorp_name()
	{
		return corp_name;
	}

	public void setCorp_name(String corp_name)
	{
		this.corp_name = corp_name;
	}

	public Map<String, String> getCorpMap()
	{
		return corpMap;
	}

	public void setCorpMap(Map<String, String> corpMap)
	{
		this.corpMap = corpMap;
	}

	public Map<String, String> getCorpNameMap()
	{
		return corpNameMap;
	}

	public void setCorpNameMap(Map<String, String> corpNameMap)
	{
		this.corpNameMap = corpNameMap;
	}

	public Map<String, String> getFeedList()
	{
		return feedList;
	}

	public void setFeedList(Map<String, String> feedList)
	{
		this.feedList = feedList;
	}

	public Map<String, String> getServiceeMap()
	{
		return serviceeMap;
	}

	public void setServiceeMap(Map<String, String> serviceeMap)
	{
		this.serviceeMap = serviceeMap;
	}

	public Map<String, String> getAccountMap()
	{
		return accountMap;
	}

	public void setAccountMap(Map<String, String> accountMap)
	{
		this.accountMap = accountMap;
	}

	public Map<String, String> getLocationMap()
	{
		return locationMap;
	}

	public void setLocationMap(Map<String, String> locationMap)
	{
		this.locationMap = locationMap;
	}

	public String getPatienceSearch()
	{
		return patienceSearch;
	}

	public void setPatienceSearch(String patienceSearch)
	{
		this.patienceSearch = patienceSearch;
	}

	public String getWildSearch()
	{
		return wildSearch;
	}

	public void setWildSearch(String wildSearch)
	{
		this.wildSearch = wildSearch;
	}

	public String getServices()
	{
		return services;
	}

	public void setServices(String services)
	{
		this.services = services;
	}

	public String getAc_manager()
	{
		return ac_manager;
	}

	public void setAc_manager(String ac_manager)
	{
		this.ac_manager = ac_manager;
	}

	public String getMed_location()
	{
		return med_location;
	}

	public void setMed_location(String med_location)
	{
		this.med_location = med_location;
	}

	public Map<String, String> getPatienceMap()
	{
		return patienceMap;
	}

	public void setPatienceMap(Map<String, String> patienceMap)
	{
		this.patienceMap = patienceMap;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSearchData()
	{
		return searchData;
	}

	public void setSearchData(String searchData)
	{
		this.searchData = searchData;
	}

	public String getDOB()
	{
		return DOB;
	}

	public void setDOB(String dOB)
	{
		DOB = dOB;
	}

	public List getCommondata()
	{
		return commondata;
	}

	public void setCommondata(List commondata)
	{
		this.commondata = commondata;
	}

	public String getLoc()
	{
		return Loc;
	}

	public void setLoc(String loc)
	{
		Loc = loc;
	}

	public String getService_manager()
	{
		return service_manager;
	}

	public void setService_manager(String service_manager)
	{
		this.service_manager = service_manager;
	}

	public Map<String, String> getServiceMng()
	{
		return serviceMng;
	}

	public void setServiceMng(Map<String, String> serviceMng)
	{
		this.serviceMng = serviceMng;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getKeyExist()
	{
		return keyExist;
	}

	public void setKeyExist(String keyExist)
	{
		this.keyExist = keyExist;
	}

	public String getValueExist()
	{
		return valueExist;
	}

	public void setValueExist(String valueExist)
	{
		this.valueExist = valueExist;
	}

	public String getKeyExistSec()
	{
		return keyExistSec;
	}

	public void setKeyExistSec(String keyExistSec)
	{
		this.keyExistSec = keyExistSec;
	}

	public String getValueExistSec()
	{
		return valueExistSec;
	}

	public void setValueExistSec(String valueExistSec)
	{
		this.valueExistSec = valueExistSec;
	}

	public String getServicesID()
	{
		return servicesID;
	}

	public void setServicesID(String servicesID)
	{
		this.servicesID = servicesID;
	}

	public Map<String, String> getBedType()
	{
		return bedType;
	}

	public void setBedType(Map<String, String> bedType)
	{
		this.bedType = bedType;
	}

	public Map<String, String> getPaymenetType()
	{
		return paymenetType;
	}

	public void setPaymenetType(Map<String, String> paymenetType)
	{
		this.paymenetType = paymenetType;
	}

	public String getPat_mobile()
	{
		return pat_mobile;
	}

	public void setPat_mobile(String patMobile)
	{
		pat_mobile = patMobile;
	}

	public String getPat_name()
	{
		return pat_name;
	}

	public void setPat_name(String patName)
	{
		pat_name = patName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}