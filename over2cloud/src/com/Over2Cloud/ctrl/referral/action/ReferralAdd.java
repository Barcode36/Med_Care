package com.Over2Cloud.ctrl.referral.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

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

public class ReferralAdd extends GridPropertyBean implements ServletRequestAware
{
	private Map<String, String> doctorNameMap;
	private Map<String, String> doctorIdMap;
	private Set<String> doctorSpecSet;
	private HttpServletRequest request;
	
	private String adm_doc, adm_spec, bed, caller_emp_id,  caller_emp_name,    nursing_unit, patient_name,  priority,  reason,  
	ref_by,  ref_by_widget,  ifrepete,
	ref_to,  ref_to_emp_id,  ref_to_emp_id_widget,  ref_to_sub_spec,  ref_to_widget,  uhid;
	
	
	
	
	

	/**	
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addReferral()
	{
		//// System.out.println("hiiiii>>>>>>");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			try
			{
				boolean isExist = false;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				//Do add referral if same UHID is already added for same specialty on current day 
				StringBuilder query = new StringBuilder();
				query.append("SELECT rd.spec FROM referral_data AS refdata ");
				query.append(" INNER JOIN referral_doctor AS rd ON rd.id=refdata.ref_to");
				query.append(" INNER JOIN referral_patient_data AS rpd ON rpd.id=refdata.uhid");
				query.append(" WHERE rpd.uhid='" + request.getParameter("uhid") + "' AND refdata.open_date between '" + DateUtil.getNextDateAfter(-1) + "' and '"+DateUtil.getCurrentDateUSFormat()+"'");
				query.append(" and refdata.status in ('Informed', 'Snooze', 'Snooze-I', 'Not Informed') ");
				//// System.out.println("query pat::" + query);
				
				
				
				////
			
				
				
				
				
				
				////
				
				
				
				if(ifrepete.equalsIgnoreCase("false"))
				{
				
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					////// System.out.println(request.getParameter("ref_to_emp_id")+"b4:::"+request.getParameter("ref_to"));
					String refSpec = request.getParameter("ref_to");
					if(refSpec.contains("#"))
					{
						refSpec=refSpec.split("#")[1];
					}
					else
					{
						refSpec=request.getParameter("ref_to_emp_id").split("#")[2];
					}
					////// System.out.println("Added::" + refSpec);
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						String spec = (String) iterator.next();
						if (spec != null)
						{
							////// System.out.println("Spec::" + spec);
							if (spec.equalsIgnoreCase(refSpec))
							{
								isExist = true;
								//addActionMessage("Same Referral is Already Added for Same Referred Specialty.");
								
								
								adm_doc = request.getParameter("adm_doc");
								adm_spec = request.getParameter("adm_spec");
								bed = request.getParameter("bed");
								caller_emp_id = request.getParameter("caller_emp_id");
								caller_emp_name = request.getParameter("caller_emp_name");
								nursing_unit = request.getParameter("nursing_unit");
								patient_name = request.getParameter("patient_name");
								priority = request.getParameter("priority");
								reason = request.getParameter("reason");
								ref_by = request.getParameter("ref_by");
								ref_by_widget = request.getParameter("ref_by_widget");
								ref_to = request.getParameter("ref_to");
								ref_to_emp_id = request.getParameter("ref_to_emp_id");
								ref_to_emp_id_widget = request.getParameter("ref_to_emp_id_widget");
								ref_to_sub_spec = request.getParameter("ref_to_sub_spec");
								ref_to_widget = request.getParameter("ref_to_widget");
								uhid = request.getParameter("uhid");
								ifrepete = "true";
								//adm_doc = request.getParameter("adm_doc");
								
								
								return "REPEATE";
							}
						}
					}
				}
				}
				
				// isExist = cbt.checkExitOfTable("referral_data", Assing
				// connectionSpace, null, null);
				if (!isExist)
				{
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					TableColumes ob1 = new TableColumes();
					List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_referral_configuration", accountID, connectionSpace, "", 0, "table_name", "referral_configuration");
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
							} else if (gdp.getColomnName().equalsIgnoreCase("open_date") || gdp.getColomnName().equalsIgnoreCase("escalate_date") || gdp.getColomnName().equalsIgnoreCase("open_time") || gdp.getColomnName().equalsIgnoreCase("escalate_time"))
							{
								ob1.setDatatype("varchar(10)");
							} else if (gdp.getColomnName().equalsIgnoreCase("uhid") || gdp.getColomnName().equalsIgnoreCase("ref_by") || gdp.getColomnName().equalsIgnoreCase("ref_to") || gdp.getColomnName().equalsIgnoreCase("reg_by"))
							{
								ob1.setDatatype("int(10)");
							} else if (gdp.getColomnName().equalsIgnoreCase("caller_emp_name") || gdp.getColomnName().equalsIgnoreCase("ref_to_sub_spec"))
							{
								ob1.setDatatype("varchar(30)");
							} else if (gdp.getColomnName().equalsIgnoreCase("level") || gdp.getColomnName().equalsIgnoreCase("priority"))
							{
								ob1.setDatatype("varchar(7)");
							} else if (gdp.getColomnName().equalsIgnoreCase("status"))
							{
								ob1.setDatatype("varchar(15)");
							} else
							{
								ob1.setDatatype("varchar(255)");
							}
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);

						}
						cbt.createTable22("referral_data", TableColumnName, connectionSpace);

						TableColumnName.clear();

					}

					ob1 = new TableColumes();
					ob1.setColumnname("uhid");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("patient_name");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("bed");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("nursing_unit");
					ob1.setDatatype("varchar(40)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("adm_doc");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("adm_spec");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					cbt.createTable22("referral_patient_data", TableColumnName, connectionSpace);

					TableColumnName.clear();

					ob1 = new TableColumes();
					ob1.setColumnname("rid");
					ob1.setDatatype("int(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("status");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_by");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_to_id");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_to_name");
					ob1.setDatatype("varchar(40)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("comments");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_desig");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("assign_close_by");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("esc_level");
					ob1.setDatatype("varchar(7)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("sn_upto_date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("sn_upto_time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("action_status");
					ob1.setDatatype("varchar(1)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("escalate_to");
					ob1.setDatatype("varchar(200)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("escalate_to_mob");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);


					cbt.createTable22("referral_data_history", TableColumnName, connectionSpace);
				}

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
				InsertDataTable ob2 = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String currentDate = DateUtil.getCurrentDateUSFormat();
				String currentTime = DateUtil.getCurrentTime();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					 //// System.out.println(Parmname+"::::::"+paramValue);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("uhid") && !Parmname.equalsIgnoreCase("bed")
							&& !Parmname.equalsIgnoreCase("patient_name") && !Parmname.equalsIgnoreCase("nursing_unit") && !Parmname.equalsIgnoreCase("adm_doc") && !Parmname.equalsIgnoreCase("adm_spec") 
							&& !Parmname.contains("widget") && !Parmname.contains("ref_to_emp_id") && !Parmname.contains("ifrepete")  )
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						if (Parmname.equalsIgnoreCase("ref_to") || Parmname.equalsIgnoreCase("ref_by"))
						{
							ob.setDataName(paramValue.split("#")[0]);
						} else
						{
							ob.setDataName(paramValue);
						}
						insertData.add(ob);
					} else if (paramValue != null && !paramValue.equalsIgnoreCase("") && !Parmname.contains("ifrepete") && Parmname != null && !Parmname.contains("widget") && !Parmname.contains("ref_to_emp_id"))
					{
						ob2 = new InsertDataTable();
						ob2.setColName(Parmname);
						ob2.setDataName(paramValue);
						insertData1.add(ob2);
					}
				}

				// Insert patient data and get inserted id
				int maxId = cbt.insertDataReturnId("referral_patient_data", insertData1, connectionSpace);

				ob = new InsertDataTable();
				ob.setColName("reg_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("uhid");
				ob.setDataName(maxId);
				insertData.add(ob);

				// Getting time for escalation for l2
				String time = getEscalationTime(request.getParameter("priority"), "OCC-Subdepartment", cbt, connectionSpace);
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
				ob.setColName("status");
				ob.setDataName("Not Informed");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("Level1");
				insertData.add(ob);

				// Getting new ticket no
				String ticketNo = getReferralTicketNo(cbt, connectionSpace);
				if (ticketNo == null)
				{
					ticketNo = "MDR1000";
				}
				ob = new InsertDataTable();
				ob.setColName("ticket_no");
				ob.setDataName(ticketNo);
				insertData.add(ob);

				int rid = cbt.insertDataReturnId("referral_data", insertData, connectionSpace);

				if (rid > 0)
				{
					insertData.clear();

					ob = new InsertDataTable();
					ob.setColName("rid");
					ob.setDataName(rid);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Not Informed");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("comments");
					ob.setDataName(request.getParameter("reason"));
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

					boolean status = cbt.insertIntoTable("referral_data_history", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Referral Added Successfully.");
						return SUCCESS;
					} else
					{
						addActionMessage("Oops!!! Some Error in Data.");
						return ERROR;
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				addActionMessage("Oops!!! Some Error in Data.");
				return ERROR;
			} finally
			{
				lock.unlock();
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	private String getReferralTicketNo(CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		List list = cbt.executeAllSelectQuery("SELECT ticket_no FROM referral_data ORDER BY id DESC LIMIT 1", connectionSpace);
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				return "MDR" + (Integer.parseInt(object.toString().substring(3)) + 1);
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public String getEscalationTime(String priority, String subDept, CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		try
		{
			String esctm=null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT tat2,firstEsc,firstEscFlag,fromTime,toTime FROM referral_escalation_detail AS red");
			query.append(" INNER JOIN subdepartment AS subDept ON subDept.id=red.esc_sub_dept");
			query.append(" WHERE red.priority='" + priority + "' AND subDept.subdeptname='" + subDept + "'");
			//System.out.println("::::::::::"+query);
			List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
				 		if(object[0]!=null && object[2]!=null && object[2].toString().equalsIgnoreCase("0"))
						{
					 		esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());
					 		
						}
						if(object[2]!=null && object[2].toString().equalsIgnoreCase("1") && object[0]!=null && object[1]!=null && object[3]!=null && object[4]!=null)
						{
							
							 System.out.println("time >>>>>     "+DateUtil.checkTimeBetween2Times(object[3].toString(), object[4].toString(), DateUtil.getCurrentTime()));
							if(object[0]!=null && object[1]!=null && object[3]!=null && object[4]!=null )
							{
								
								if(DateUtil.checkTimeBetween2Times(object[3].toString(), "24:00", DateUtil.getCurrentTime()))
								{
									esctm = DateUtil.getAddressingDatetime(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), "00:00", "00:00", object[1].toString());
									//esctm = '01-12-2015 12:00
								}
								else if ( DateUtil.checkTimeBetween2Times("00:00", object[4].toString(), DateUtil.getCurrentTime()))
										{
									esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), "00:00", "00:00", object[1].toString());
									
								}
								else
								{
							 		esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());
						 			
								}
						 	//DateUtil.getEs
				 			}
							else
							{
						 		esctm = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", object[0].toString());
					 			
							}
						}
				 		 
		 		}
			return esctm;
			}
			 
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "00:00";
	}

	@SuppressWarnings("rawtypes")
	public String beforeReferralLodge()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				doctorNameMap = new LinkedHashMap<String, String>();
				doctorIdMap = new LinkedHashMap<String, String>();
				doctorSpecSet = new HashSet<String>();
				List list = new createTable().executeAllSelectQuery("SELECT id,name,spec,emp_id from referral_doctor", connectionSpace);
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						doctorNameMap.put(Integer.parseInt(object[0].toString()) + "#" + object[2].toString() + "#" + object[3].toString(), object[1].toString());
						doctorIdMap.put(Integer.parseInt(object[0].toString()) + "#" + object[1].toString() + "#" + object[2].toString(), object[3].toString());
						doctorSpecSet.add(object[2].toString());
					}
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

	public Map<String, String> getDoctorNameMap()
	{
		return doctorNameMap;
	}

	public void setDoctorNameMap(Map<String, String> doctorNameMap)
	{
		this.doctorNameMap = doctorNameMap;
	}

	public Map<String, String> getDoctorIdMap()
	{
		return doctorIdMap;
	}

	public void setDoctorIdMap(Map<String, String> doctorIdMap)
	{
		this.doctorIdMap = doctorIdMap;
	}

	public Set<String> getDoctorSpecSet()
	{
		return doctorSpecSet;
	}

	public void setDoctorSpecSet(Set<String> doctorSpecSet)
	{
		this.doctorSpecSet = doctorSpecSet;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getAdm_doc() {
		return adm_doc;
	}

	public void setAdm_doc(String adm_doc) {
		this.adm_doc = adm_doc;
	}

	public String getAdm_spec() {
		return adm_spec;
	}

	public void setAdm_spec(String adm_spec) {
		this.adm_spec = adm_spec;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getCaller_emp_id() {
		return caller_emp_id;
	}

	public void setCaller_emp_id(String caller_emp_id) {
		this.caller_emp_id = caller_emp_id;
	}

	public String getCaller_emp_name() {
		return caller_emp_name;
	}

	public void setCaller_emp_name(String caller_emp_name) {
		this.caller_emp_name = caller_emp_name;
	}

	

	public String getNursing_unit() {
		return nursing_unit;
	}

	public void setNursing_unit(String nursing_unit) {
		this.nursing_unit = nursing_unit;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRef_by() {
		return ref_by;
	}

	public void setRef_by(String ref_by) {
		this.ref_by = ref_by;
	}

	public String getRef_by_widget() {
		return ref_by_widget;
	}

	public void setRef_by_widget(String ref_by_widget) {
		this.ref_by_widget = ref_by_widget;
	}

	public String getRef_to() {
		return ref_to;
	}

	public void setRef_to(String ref_to) {
		this.ref_to = ref_to;
	}

	public String getRef_to_emp_id() {
		return ref_to_emp_id;
	}

	public void setRef_to_emp_id(String ref_to_emp_id) {
		this.ref_to_emp_id = ref_to_emp_id;
	}

	public String getRef_to_emp_id_widget() {
		return ref_to_emp_id_widget;
	}

	public void setRef_to_emp_id_widget(String ref_to_emp_id_widget) {
		this.ref_to_emp_id_widget = ref_to_emp_id_widget;
	}

	public String getRef_to_sub_spec() {
		return ref_to_sub_spec;
	}

	public void setRef_to_sub_spec(String ref_to_sub_spec) {
		this.ref_to_sub_spec = ref_to_sub_spec;
	}

	public String getRef_to_widget() {
		return ref_to_widget;
	}

	public void setRef_to_widget(String ref_to_widget) {
		this.ref_to_widget = ref_to_widget;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getIfrepete() {
		return ifrepete;
	}

	public void setIfrepete(String ifrepete) {
		this.ifrepete = ifrepete;
	}
	
	
}
