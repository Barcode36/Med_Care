package com.Over2Cloud.ctrl.patientcare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.action.GridPropertyBean;

public class CPSHelper extends GridPropertyBean
{

	public static Map fetchServiceMap(SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> serviceMap = new LinkedHashMap<String, String>();
		List data = cbt.executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connection);
		if (data != null && data.size() > 0)
		{
			for (Object obj : data)
			{
				Object[] ob = (Object[]) obj;
				if (ob[0] != null && ob[1] != null)
				{
					serviceMap.put(ob[0].toString(), ob[1].toString());
				}
			}
		}
		return serviceMap;
	}

	public static List fetchServiceList(SessionFactory connection)
	{
		List data = null;
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select id, service_name from cps_service  group by service_name", connection);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;
	}

	public static Map fetchAcManager(SessionFactory connection, String empid, String usarname)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> acManager = new LinkedHashMap<String, String>();
		String value = null;
		List adminRights = cbt.executeAllSelectQuery("select linkVal from useraccount where name = '" + empid + "'", connection);
		if (adminRights != null && adminRights.size() > 0)
		{
			value = adminRights.get(0).toString();
		}
		if (value != null && value.contains("accMng_VIEW"))
		{
			// String userName = (String) session.get("uName");
			acManager.put(usarname, usarname);
		} else
		{
			List data = cbt.executeAllSelectQuery("select distinct ACCOUNT_OFFICER, id from dreamsol_bl_corp_hc_pkg group by ACCOUNT_OFFICER order By ACCOUNT_OFFICER", connection);
			if (data != null && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null && !ob[0].toString().equalsIgnoreCase("NA"))
					{
						acManager.put(ob[0].toString(), ob[0].toString());
					}
				}
				acManager.put("CMD Office", "CMD Office");
				acManager.put("COO Office", "COO Office");
			}
		}
		/*
		 * if (subModuleRightsArray != null && subModuleRightsArray.length > 0)
		 * { for (String s : subModuleRightsArray) { if
		 * (s.equals("cradyHealth_VIEW")) { System.out.println("SSSSSSSSS "+s);
		 * } else if (!s.equals("cradyHealth_VIEW")) { List data =
		 * cbt.executeAllSelectQuery(
		 * "select distinct ACCOUNT_OFFICER, id from dreamsol_bl_corp_hc_pkg group by ACCOUNT_OFFICER order By ACCOUNT_OFFICER"
		 * , connection); if (data != null && data.size() > 0) { for (Object obj
		 * : data) { Object[] ob = (Object[]) obj; if (ob[0] != null && ob[1] !=
		 * null) { acManager.put(ob[0].toString(), ob[0].toString()); } }
		 * acManager.put("CMD Office", "CMD Office");
		 * acManager.put("COO Office", "COO Office"); } } } }
		 */
		return acManager;
	}

	public static Map fetchAcManager(SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> acManager = new LinkedHashMap<String, String>();
		List data = cbt.executeAllSelectQuery("select distinct ACCOUNT_OFFICER, id from dreamsol_bl_corp_hc_pkg group by ACCOUNT_OFFICER order By ACCOUNT_OFFICER", connection);
		if (data != null && data.size() > 0)
		{
			for (Object obj : data)
			{
				Object[] ob = (Object[]) obj;
				if (ob[0] != null && ob[1] != null && !ob[1].toString().equalsIgnoreCase("NA"))
				{
					acManager.put(ob[0].toString(), ob[0].toString());
				}
			}
			acManager.put("CMD Office", "CMD Office");
			acManager.put("COO Office", "COO Office");
		}
		return acManager;
	}

	public static List fetchCorporateName(SessionFactory connection)
	{
		List data = null;
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select id, CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg group by CUSTOMER_NAME", connection);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;
	}

	public static Map fetchServiceManager(SessionFactory connection)
	{
		String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";// shiftname
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> serviceManager = new LinkedHashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select emp.id, emp.empName from employee_basic as emp ");
		sb.append(" inner join compliance_contacts as cc on cc.emp_id = emp.id ");
		sb.append(" inner join subdepartment as sub on cc.forDept_id = sub.id ");
		sb.append(" inner join department as dept on sub.deptid= dept.id ");
		sb.append(" where cc.forDept_id = (select id from subdepartment where subdeptname = 'Service Manager' ");
		sb.append("  and deptid = (select id from department where deptName='Patient Care' )) ");
		List data = cbt.executeAllSelectQuery(sb.toString(), connection);
		if (data != null && data.size() > 0)
		{
			for (Object obj : data)
			{
				Object[] ob = (Object[]) obj;
				if (ob[0] != null && ob[1] != null)
				{
					serviceManager.put(ob[0].toString(), ob[1].toString());
				}
			}
		}
		return serviceManager;
	}

	// for fetch spec

	public static Map fetchSpec(String loc, String sec, SessionFactory connection)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> specMap = new LinkedHashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select id, spec_name from cps_speciality where spec_loc = '" + loc + "' and spec_name = '" + sec + "' ");
		List data = cbt.executeAllSelectQuery(sb.toString(), connection);
		if (data != null && data.size() > 0)
		{
			for (Object obj : data)
			{
				Object[] ob = (Object[]) obj;
				if (ob[0] != null && ob[1] != null)
				{
					specMap.put(ob[0].toString(), ob[1].toString());
				}
			}
		}
		return specMap;
	}

	@SuppressWarnings("unchecked")
	public static List fetchCorporateName(String id, SessionFactory connection)
	{
		List<String> data = new ArrayList<String>();
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List data1 = null;
			if (id.equalsIgnoreCase("COO Office") || id.equalsIgnoreCase("CMD Office") || id.equalsIgnoreCase("Crady Health"))
			{
				// data1=cbt.executeAllSelectQuery("select id, CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg  group by CUSTOMER_NAME ",
				// connection);
				data1 = cbt.executeAllSelectQuery("select id, CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg where ACCOUNT_OFFICER= '" + id + "' group by CUSTOMER_NAME ", connection);
			} else
			{
				data1 = cbt.executeAllSelectQuery("select id, CUSTOMER_NAME from dreamsol_bl_corp_hc_pkg where ACCOUNT_OFFICER= '" + id + "' group by CUSTOMER_NAME ", connection);
			}
			if (data1 != null && !data1.isEmpty())
			{
				data.addAll(data1);
				data1.clear();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;

	}

	public static List fetchServicesLocation(String id, SessionFactory connection)
	{

		List data = null;
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			if (!id.contains("undefined"))
				data = cbt.executeAllSelectQuery("select id, serv_loc from cps_service where service_name in(select service_name from cps_service where id = " + id + ") order by serv_loc", connection);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;
	}

	public static String fillPatientDetails(String mobile, SessionFactory connection)
	{
		String patient_detail = "";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select cpd.patient_name, cpd.pat_mobile, cpd.pat_email,cpd.pat_dob, cpd.pat_gender, con.country_name, cpd.pat_state, cpd.pat_city,cpd.uhid from corporate_patience_data as cpd inner join country as con on con.code=cpd.pat_country where pat_mobile = '" + mobile + "' group by  pat_mobile";
			List data = cbt.executeAllSelectQuery(query, connection);

			if (data != null && !data.isEmpty() && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] ob = (Object[]) obj;

					for (int i = 0; i < ob.length; i++)
					{
						if (ob[i] != null)
						{
							patient_detail = patient_detail + "#" + ob[i].toString();
						} else
						{
							patient_detail = patient_detail + "#" + "NA";
						}
					}
				}

				patient_detail = patient_detail + "#";
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return patient_detail;
	}

	public static List fetchPatientName(String id, SessionFactory connection)
	{
		List data = null;
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select DISTINCT patient_name, pat_mobile from corporate_patience_data where corp_name = " + id + " order by patient_name", connection);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;
	}

	public static String empNameGet(String id, SessionFactory connection)
	{

		String empName = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List dataListEcho = cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne, dept.deptName FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id where emp.id =" + id + "".toString(), connection);
			if (dataListEcho != null && dataListEcho.size() > 0)
			{
				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empName = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString();
				}
			}
		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return empName;
	}

	public static String empNameGetServiceMng(String id, SessionFactory connection)
	{

		String empName = null, deptName = null;
		List dataList = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List dataListEcho = cbt.executeAllSelectQuery("SELECT sub.subdeptname FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id inner join compliance_contacts as cc on cc.emp_id=emp.id inner  join subdepartment as sub on sub.id=cc.forDept_id where emp.id =" + id + " order by cc.level desc  ".toString(), connection);
			if (dataListEcho != null && dataListEcho.size() > 0)
			{
				deptName = dataListEcho.get(0).toString();
			}
			if (deptName != null && deptName.equalsIgnoreCase("Service Manager"))
			{
				dataList = cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne, dept.deptName,cc.level FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id inner join compliance_contacts as cc on cc.emp_id=emp.id inner  join subdepartment as sub on sub.id=cc.forDept_id where emp.id =" + id + " and sub.subdeptname='" + deptName + "' ".toString(), connection);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						empName = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString() + "#" + object[3].toString();
					}
				}
			} else
			{
				dataList = cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne, dept.deptName,cc.level FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id inner join compliance_contacts as cc on cc.emp_id=emp.id inner  join subdepartment as sub on sub.id=cc.forDept_id where emp.id =" + id + " ".toString(), connection);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						empName = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString() + "#" + object[3].toString();
					}
				}
			}
		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return empName;
	}

	// method for get next level escalation date and time author : manab
	// 03-09-2015
	static String escTime(SessionFactory connection)
	{
		String esctm = "";
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			// Faisal the dept id 19 is used hard code because patientcare dept
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

	public static boolean addNewRequstOnAck(String cps_id, String location, String preferred_time, String service, String uhid, String remarks, String ehc_pack, String ehc_pack_type, String em_spec, String em_spec_assis, String radio_mod, String opd_spec, String opd_doc, String daycare_spec, String daycare_doc, String ipd_spec, String ipd_doc, String ipd_bed, String ipd_pat_type, String lab_mod, String diagnostics_test, String new_service_remarks, String resolved_by, String facilitation_mod, String telemedicine_mod, String userName, SessionFactory connectionSpace)
	{
		// TODO Auto-generated method stub
		String new_remark = null;
		if (remarks != null && remarks.equalsIgnoreCase(""))
		{
			new_remark = remarks;
		} else
		{
			new_remark = new_service_remarks;
		}
		// String userName = (String) session.get("uName");

		boolean flag = false;
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append(" select id, corp_type, corp_name, feed_status, patient_name, uhid, pat_mobile, pat_email, ");
		query.append(" pat_dob, pat_gender, pat_state, pat_city, pat_country, ac_manager from corporate_patience_data ");
		query.append(" where id=" + cps_id + " ");

		List dataList = null;
		dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			String id = "";
			String corp_type = "";
			String corp_name = "";
			String feed_status = "";
			String patient_name = "";
			String uhidold = "";
			String pat_mobile = "";
			String pat_email = "";
			String pat_dob = "";
			String pat_gender = "";
			String pat_state = "";
			String pat_city = "";
			String pat_country = "";
			String ac_manager = "";

			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] obdata = (Object[]) iterator.next();

				id = obdata[0].toString();
				corp_type = obdata[1].toString();
				corp_name = obdata[2].toString();
				feed_status = obdata[3].toString();
				patient_name = obdata[4].toString();
				uhidold = obdata[5].toString();
				pat_mobile = obdata[6].toString();
				pat_email = obdata[7].toString();
				pat_dob = obdata[8].toString();
				pat_gender = obdata[9].toString();
				pat_state = obdata[10].toString();
				pat_city = obdata[11].toString();
				pat_country = obdata[12].toString();
				ac_manager = obdata[13].toString();
			}

			ob = new InsertDataTable();
			ob.setColName("pat_city");
			ob.setDataName(pat_city);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_country");
			ob.setDataName(pat_country);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ac_manager");
			ob.setDataName(ac_manager);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("med_location");
			ob.setDataName(location);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("preferred_time");
			ob.setDataName(preferred_time);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("services");
			ob.setDataName(service);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("corp_type");
			ob.setDataName(corp_type);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("corp_name");
			ob.setDataName(corp_name);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("feed_status");
			ob.setDataName(feed_status);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("patient_name");
			ob.setDataName(patient_name);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_mobile");
			ob.setDataName(pat_mobile);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_email");
			ob.setDataName(pat_email);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_dob");
			ob.setDataName(pat_dob);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_gender");
			ob.setDataName(pat_gender);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("pat_state");
			ob.setDataName(pat_state);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("uhid");
			ob.setDataName(uhid);
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
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("remarks");
			ob.setDataName(new_remark);
			insertData.add(ob);

			// for ticket id change by Manab
			String nxtTkt = "";
			try
			{
				List dataLastTkt = cbt.executeAllSelectQuery("select ticket from corporate_patience_data order by id desc limit 1 ".toString(), connectionSpace);
				if (dataLastTkt != null && dataLastTkt.size() > 0)
				{
					int lastTicket = Integer.parseInt(dataLastTkt.get(0).toString().split("-")[1]);
					lastTicket = lastTicket + 1;
					nxtTkt = "CPS-" + lastTicket;
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
			ob.setDataName(fetchDeptID(connectionSpace));
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("level");
			ob.setDataName("1");
			insertData.add(ob);

			// cbt.insertIntoTable("corporate_patience_data", insertData,
			// connectionSpace);
			int maxId = cbt.insertDataReturnId("corporate_patience_data", insertData, connectionSpace);
			// System.out.println("maxIdmaxIdmaxId "+maxId);
			insertData.clear();

			ob = new InsertDataTable();
			ob.setColName("CPSId");
			ob.setDataName(maxId);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("location_name");
			ob.setDataName(location);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ehc_pack");
			ob.setDataName(ehc_pack);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ehc_pack_type");
			ob.setDataName(ehc_pack_type);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("em_spec");
			ob.setDataName(em_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("em_spec_assis");
			ob.setDataName(em_spec_assis);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("radio_mod");
			ob.setDataName(radio_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("facilitation_mod");
			ob.setDataName(facilitation_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("telemedicine_mod");
			ob.setDataName(telemedicine_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("opd_spec");
			ob.setDataName(opd_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("opd_doc");
			ob.setDataName(opd_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("daycare_spec");
			ob.setDataName(daycare_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("daycare_doc");
			ob.setDataName(daycare_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_spec");
			ob.setDataName(ipd_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_doc");
			ob.setDataName(ipd_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_bed");
			ob.setDataName(ipd_bed);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_pat_type");
			ob.setDataName(ipd_pat_type);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("lab_mod");
			ob.setDataName(lab_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("diagnostics_test");
			ob.setDataName(diagnostics_test);
			insertData.add(ob);

			int maxIds2 = cbt.insertDataReturnId("cps_services_details", insertData, connectionSpace);
			new ActivityBoardAction().sendOccAlertOnTicketLodge(nxtTkt, connectionSpace);
			String dName = "";
			String serviceName = fetcServiceName(service, connectionSpace);
			if (serviceName != null && serviceName.equalsIgnoreCase("OPD"))
			{
				dName = opd_doc;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("EHC"))
			{
				dName = ehc_pack;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("Radiology"))
			{
				dName = radio_mod;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("Facilitation"))
			{
				dName = facilitation_mod;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("Telemedicine"))
			{
				dName = telemedicine_mod;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("Diagnostic"))
			{
				dName = diagnostics_test;
			} else if (serviceName != null && serviceName.equalsIgnoreCase("Laboratory"))
			{
				dName = remarks;
			}
			// new ActivityBoardAction().sendPatientAlert("appointment",
			// patient_name, pat_mobile, service, preferred_time, location,
			// dName, "", connectionSpace);
			insertData.clear();

			ob = new InsertDataTable();
			ob.setColName("CPSId");
			ob.setDataName(maxId);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("location_name");
			ob.setDataName(location);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ehc_pack");
			ob.setDataName(ehc_pack);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ehc_pack_type");
			ob.setDataName(ehc_pack_type);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("em_spec");
			ob.setDataName(em_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("em_spec_assis");
			ob.setDataName(em_spec_assis);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("radio_mod");
			ob.setDataName(radio_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("opd_spec");
			ob.setDataName(opd_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("opd_doc");
			ob.setDataName(opd_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("daycare_spec");
			ob.setDataName(daycare_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("daycare_doc");
			ob.setDataName(daycare_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_spec");
			ob.setDataName(ipd_spec);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_doc");
			ob.setDataName(ipd_doc);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_bed");
			ob.setDataName(ipd_bed);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("ipd_pat_type");
			ob.setDataName(ipd_pat_type);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("lab_mod");
			ob.setDataName(lab_mod);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("diagnostics_test");
			ob.setDataName(diagnostics_test);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("new_service_remarks");
			ob.setDataName(new_service_remarks);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("action_by");
			ob.setDataName(userName);
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
			ob.setDataName("OCC");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("action_reason");
			ob.setDataName("Assign");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("escalation_level");
			ob.setDataName("1");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("status");
			ob.setDataName("Appointment");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("dept");
			ob.setDataName("OCC");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("resolved_by");
			ob.setDataName(empNameGet(resolved_by, connectionSpace).split("#")[0]);
			insertData.add(ob);

			String service_name = fetcServiceName(service, connectionSpace);
			ob = new InsertDataTable();
			ob.setColName("service_name");
			ob.setDataName(service_name);
			insertData.add(ob);

			cbt.insertIntoTable("cps_status_history", insertData, connectionSpace);
			insertData.clear();
			cbt.executeAllUpdateQuery("update cps_services_details set service_name='" + service + "' where id='" + maxIds2 + "'", connectionSpace);
			flag = true;
		}

		return flag;
	}

	public static List fetchDepartment(String id, SessionFactory connection)
	{
		List data = null;
		try
		{

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data = cbt.executeAllSelectQuery("select id,empName from employee_basic where id='" + id + "'", connection);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return data;
	}

	public static String[] getSubModuleRights(String userId, SessionFactory connection)
	{

		String[] value = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where name = '" + userId + "'", connection);
			if (adminRights != null && adminRights.size() > 0)
			{
				value = adminRights.get(0).toString().split("#");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

	/*
	 * public static String getSubDeptByEmpId(String empId, SessionFactory
	 * connection, CommonOperInterface cbt) {
	 * 
	 * @SuppressWarnings("unused") String subDeptName = null; String temp = "";
	 * try { List dataList =cbt.executeAllSelectQuery(
	 * "select sub.subdeptname from subdepartment as sub inner join compliance_contacts as cc on cc.forDept_id = sub.id where  cc.emp_id ='"
	 * + empId + "' and cc.moduleName='CPS' and cc.work_status='0' ",
	 * connection);
	 * 
	 * if (dataList != null && dataList.size() > 0) { subDeptName =
	 * dataList.get(0).toString(); for (Object s : dataList) { temp = temp + "'"
	 * + s.toString() + "'" + ","; } temp = temp.substring(0, temp.length() -
	 * 1); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return temp; }
	 */
	public static String getSubDeptByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		String subDeptName = null;
		String temp = "";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select sub.subdeptname from subdepartment as sub inner join compliance_contacts as cc on cc.forDept_id = sub.id where  cc.emp_id ='" + empId + "' and cc.moduleName='CPS' and cc.work_status='0' ", connection);

			if (dataList != null && dataList.size() > 0)
			{
				subDeptName = dataList.get(0).toString();
				for (Object s : dataList)
				{
					if (s.toString().equalsIgnoreCase("Service Manager"))
					{

					} else
					{
						temp = temp + "'" + s.toString() + "'" + ",";
					}
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("temp11 " + temp);
		return temp;
	}

	public static String getLocationName(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		@SuppressWarnings("unused")
		String subDeptName = null;
		String temp = "";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select cc.location from compliance_contacts as cc  where  cc.emp_id ='" + empId + "' and cc.moduleName='CPS' and cc.work_status='0' ", connection);
			if (dataList != null && dataList.size() > 0)
			{
				subDeptName = dataList.get(0).toString();
				for (Object s : dataList)
				{
					if (s.toString().equalsIgnoreCase("All"))
					{

					} else
					{
						temp = temp + "'" + s.toString() + "'" + ",";
					}
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("temp12 " + temp);
		return temp;
	}

	// added for roaster

	// Get Service Department List
	@SuppressWarnings("unchecked")
	public List getServiceDept_SubDept(String deptLevel, String orgLevel, String orgId, String module, SessionFactory connection)
	{
		// System.out.println("module "+module);
		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try
		{
			if (module.equalsIgnoreCase("COMPL"))
			{
				qry.append("select distinct dept.id,dept.deptName from compliance_task as compl");
				qry.append(" inner join department as dept on compl.departName=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel + "' and dept.mappedOrgnztnId='" + orgId + "'");
			} else if (module.equalsIgnoreCase("HDM"))
			{
				qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				if (deptLevel.equals("2"))
				{
					qry.append(" inner join subdepartment as subdept on feed.dept_subdept=subdept.id");
					qry.append(" inner join department as dept on subdept.deptid=dept.id");
				} else if (deptLevel.equals("1"))
				{
					qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
				}
				qry.append(" where dept.orgnztnlevel='" + orgLevel + "' and dept.mappedOrgnztnId='" + orgId + "' and feed.hide_show='Active' and feed.moduleName='" + module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("ASTM"))
			{
				qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				if (deptLevel.equals("2"))
				{
					qry.append(" inner join subdepartment as subdept on feed.dept_subdept=subdept.id");
					qry.append(" inner join department as dept on subdept.deptid=dept.id");
				} else if (deptLevel.equals("1"))
				{
					qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
				}
				qry.append(" where dept.orgnztnlevel='" + orgLevel + "' and dept.mappedOrgnztnId='" + orgId + "' and feed.hide_show='Active' and feed.moduleName='" + module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("CASTM"))
			{
				qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel + "' and dept.mappedOrgnztnId='" + orgId + "' and feed.hide_show='Active' and feed.moduleName='" + module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("FM"))
			{
				// Added by Avinash for FM
				qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel + "' and dept.mappedOrgnztnId='" + orgId + "' and feed.hide_show='Active' and feed.moduleName='" + module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("CPS"))
			{
				// Added by Faisal for FM
				qry.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append(" where orgnztnlevel='" + orgLevel + "' and mappedOrgnztnId='4' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("CPS"))
			{
				// Added by Kamlesh for CPS
				qry.append("select dept.id,dept.deptName from subdepartment as subdept  ");
				qry.append(" INNER JOIN department as dept on dept.id=subdept.deptid ");
				qry.append(" INNER JOIN groupinfo as gp on gp.id=dept.groupId ");
				qry.append(" where gp.groupName='Corporate Patient Service'  group by dept.deptName  order by dept.deptName asc ");
			} else
			{
				qry.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append(" where orgnztnlevel='" + orgLevel + "' and mappedOrgnztnId='" + orgId + "' ORDER BY dept.deptName ASC");
			}
			// System.out.println("qry shift" + qry);
			Session session = null;
			Transaction transaction = null;
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dept_subdeptList;

	}

	public static String fetcServiceName(String serid, SessionFactory connection)
	{
		String service_name = null;
		StringBuilder empuery = new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		empuery.append(" select service_name from cps_service where  id ='" + serid + "'  ");
		try
		{
			List escData = cbt.executeAllSelectQuery(empuery.toString(), connection);
			if (escData != null && escData.size() > 0)
			{
				service_name = escData.get(0).toString();

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return service_name;
	}

	// For getting rendom occ emp for ticket lodge alert

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public List selectRendomOccEmp(String map_dept_id, String department, String level, String module, SessionFactory connection)
	{
		List empList = new ArrayList();

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT emp.id, emp.empName, emp.mobOne, ewm.cps_service_id  FROM employee_basic AS emp");
			sb.append("  INNER JOIN cps_emp_subdept_mapping AS ewm ON ewm.empName=emp.id");
			sb.append("  INNER JOIN cps_shift_with_emp_mapping AS swew ON ewm.shiftId = swew.id");
			sb.append("  INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id`");
			sb.append("  INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id`");
			sb.append("  WHERE subdept.id=" + department + " AND swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >='" + DateUtil.getCurrentTime() + "'");
			sb.append(" and cc.level='" + level + "' AND cc.moduleName='" + module + "' and cc.work_status='0' ");
			sb.append(" and ewm.cps_service_id='OCC'  and ewm.cps_location_id='All' ");
			sb.append(" group by  emp.mobOne");
			// System.out.println(sb.toString());
			List dataList = cbt.executeAllSelectQuery(sb.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					String assignEmpDetails = object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString() + "#" + object[3].toString();
					empList.add(assignEmpDetails);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return empList;
	}

	public static String fetchAccountManager(String id, SessionFactory connection)
	{
		String accountManager = "";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List escData = cbt.executeAllSelectQuery("select account_officer from dreamsol_bl_corp_hc_pkg where id='" + id + "'", connection);
			if (escData != null && escData.size() > 0)
			{
				accountManager = escData.get(0).toString();

			}

		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		return accountManager;
	}

	public static String fetchDeptID(SessionFactory connection)
	{
		String deptID = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List escData = cbt.executeAllSelectQuery("SELECT id FROM department WHERE deptName ='Patient Care'", connection);
			if (escData != null && escData.size() > 0)
			{
				deptID = escData.get(0).toString();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return deptID;
	}

	public static String mailBodyAccountManager (String accMngrname, String patName, String patMobile, String corporateName ,String serviceName, String appointmentAt, String location, String serviceMngr, String Ticket )
	{

		// TODO Auto-generated method stub
		
		/*Dear Credihealth
		Mr Rohit Chadda reachable on 9971119080 corporate name Referral is scheduled for OPD on 25-08-2016 at 11:59 AM 
		at Medanta The Medicity, service manager assigned Prabha Sharma (9971991752) and Ticket No: CPS-1890 Status- Open 
		Team Medanta */
		
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b> Dear " + accMngrname + ",</b>");

		mailtext.append("<br>");
		mailtext.append("Patient "+patName+" reachable on "+patMobile+", corporate name "+corporateName+" is scheduled for "+serviceName+" on "+appointmentAt+" at "+location+", service manager "+serviceMngr+" is assigned to assist the patient with ticket ID: "+Ticket);

		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("Team Medanta");


		//

		// mailtext.append("<tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td><b>Medanta-The Medicity</b></tr>");

		mailtext.append("</tbody></table>");

		return mailtext.toString();
	
	}
	public static String mailBody(String smstext, String patName, String serviceBookTime, String location, String service, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String serviceName = service;
		boolean chkTestName = isInteger(service.trim());
		if (chkTestName == true)
		{
			serviceName = getServiceName(service, connection);
		} else
		{
			serviceName = service;
		}

		if (serviceName.equalsIgnoreCase("NA"))
		{
			serviceName = " ";
		}
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b> Dear " + patName + ",</b>");

		mailtext.append("<br><br><b>Greetings</b>");
		mailtext.append("<br>");
		mailtext.append("Welcome to Medanta-The Medicity Hospital and we are honoured that you have chosen us as your health care provider. Our goal is to provide the highest quality care for all of our patients in a timely and respectful manner.");

		mailtext.append("<br>");
		mailtext.append("We are pleased to confirm your executive health check-up <b>" + serviceName + "</b> with <b>" + location + "</b> on <b>" + serviceBookTime + "</b>. ");
		mailtext.append("<br>");
		mailtext.append("<br>");

		//

		// mailtext.append("<tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td><b>Medanta-The Medicity</b></tr>");

		mailtext.append("</tbody></table>");

		return mailtext.toString();
	}

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

	private static String getServiceName(String serviceId, SessionFactory connection)
	{
		String serviceName = "";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
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

	public static String mailBodyOPD(String smstext, String patName, String serviceBookTime, String location, String doc, SessionFactory connection)
	{

		// TODO Auto-generated method stub

		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b> Dear " + patName + ",</b>");

		mailtext.append("<br><br><b>Greetings!</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("Welcome to Medanta-The Medicity Hospital and we are honoured that you have chosen us as your health care provider. Our goal is to provide the highest quality care for all of our patients in a timely and respectful manner.");

		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("We are pleased to inform that your admission under  <b>" + doc + "</b> on <b>" + serviceBookTime + "</b> at <b>" + location + "</b>.<br> Please make a note of the following information in regards to your admission: ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&nbsp;1.</b>  Please bring Request for admission form, Bill estimate (if available) and photo ID proof (of patient) on the day of your admission and submit the same at the admission counter on the upper ground floor. ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&nbsp;2.</b>  Please note that bed allotment time is 30 to 45 minutes, subject to availability of the room. ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&nbsp;3.</b>  We have attached useful information about the hospital, request to please go through the same ");
		//

		// mailtext.append("<tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td><b>Medanta-The Medicity</b></tr>");

		mailtext.append("</tbody></table>");

		return mailtext.toString();

	}

	public static String mailBodyIPD(String smstext, String patName, String serviceBookTime, String location, String doc, SessionFactory connection)
	{

		// TODO Auto-generated method stub

		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b> Dear " + patName + ",</b>");

		mailtext.append("<br><br><b>Greetings!</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("We are pleased to inform that your admission under  <b>" + doc + "</b> on <b>" + serviceBookTime + "</b> at <b>" + location + "</b>. <br>We would request you to please bring the following documents along with you: ");
		mailtext.append("<br>");
		mailtext.append("&nbsp; <b>If you have Insurance /TPA(Third Party Administrator):</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Patient ID proof and address proof is mandatory (Voter ID card or rashan card or driving licence or passport or adhaar card)");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Policy holder/main member in policy-ID proof and address proof ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  PAN card mandatory in claim above Rs. 1 lac ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  TPA card of current year or current year policy ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Treatment record prior to this hospitalization ");
		mailtext.append("<br>");

		mailtext.append("<b>&nbsp;&nbsp;</b>a)	Doctor’s prescription/discharge summary ");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&nbsp;</b>b)	Investigation reports related to relevant treatment ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Treatment record related to current hospitalization ");
		mailtext.append("<br>");

		mailtext.append("<b>&nbsp;&nbsp;</b>a)	OPD prescription in planned admission or emergency notes in emergency admission");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&nbsp;</b>b)	Investigation reports related to relevant treatment ");
		//
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("&nbsp; <b>If you are in a Corporate:</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Corporate employee ID card (for self)/company issued dependent proof or patient ID proof (issued by the govt.) ");
		mailtext.append("<br>");
		mailtext.append("<br>");

		mailtext.append("&nbsp; <b>If you have CGHS (Central Health Government Scheme):</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  CGHS card ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Permission from CGHS doctor  ");
		mailtext.append("<br>");
		mailtext.append("<br><br>");

		mailtext.append("&nbsp; <b>Important Note:</b>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b> Reach 15 to 20 minutes before the scheduled appointment for registration & preliminary assessments  ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  Take a few minutes to jot down key questions about your illness");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b> Carry your latest medications & medical records (if any)  ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<b>&nbsp;&#x25BA;</b>  We have attached useful information about the hospital, request to please go through the same  ");
		mailtext.append("<br>");
		mailtext.append("<br>");
		// mailtext.append("<tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td><b>Medanta-The Medicity</b></tr>");
		mailtext.append("</tbody></table>");
		return mailtext.toString();
	}

	public static String fetchLocationID(String med_location, SessionFactory connection)
	{
		String locID = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List escData = cbt.executeAllSelectQuery("select id from cps_location where location_name='" + med_location + "'", connection);
			if (escData != null && escData.size() > 0)
			{
				locID = escData.get(0).toString();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return locID;
	}

	public static String fetchCorporateNameSingle(String corporateID, SessionFactory connection)
	{
		String corpName = " ";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List escData = cbt.executeAllSelectQuery("select cust.customer_name from  dreamsol_bl_corp_hc_pkg as cust inner join corporate_patience_data as cps on cps.corp_name=cust.id where cps.id '" + corporateID + "'", connection);
			if (escData != null && escData.size() > 0)
			{
				corpName = escData.get(0).toString();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return corpName;
	}

}