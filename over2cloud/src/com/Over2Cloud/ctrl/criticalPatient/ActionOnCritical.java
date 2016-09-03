package com.Over2Cloud.ctrl.criticalPatient;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;

public class ActionOnCritical extends GridPropertyBean implements ServletRequestAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String status, level, complainIds;
	private String ticketNo;
	private String uhid, openDate;
	private String pid;
	private String viewData;
	private String data;
	private Map<String, String> statusMap;
	private Map<String, String> doctorNameMap;
	private Map<String, String> doctorIdMap;
	private String nursing_unit;
	private String admdoc;
	private String infoPartial;
	private String admdoc_mob;
	private String test_type;
	private String patient_type;
	private String patient_mob;
	private ArrayList<ArrayList<String>> testDetails;
	private List dataList;
	private String priority;
	private String actionDate = "NA", assignTo = "NA";
	private JSONArray commonJSONArray;
	private String docID;
	private String send_sms;
	@SuppressWarnings("rawtypes")
	public String beforeTakeActionCritical()
	{

		if (ValidateSession.checkSession())
		{
			try
			{
				String data[] = viewData.split(",");
				setPid(pid);
				setData(viewData);
				setPatient_type(data[8]);
				infoPartial = "";
				StringBuilder table = new StringBuilder();
				table.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
				table.append("<tr  height='30' bgcolor='#8db7d6' align='left'  ><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' class='Title' width='15%'><b>Caller Employee ID:</b></td ><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[0]
						+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Caller Emp Name:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[1]
						+ "</td><td  width='15%'  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Caller Emp Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[2] + "</td></tr>");
				table.append("<tr  height='30' bgcolor='#ffffff' align='left'  ><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Lab Doctor Name:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[3]
						+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Lab Doctor Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[4]
						+ "</td><td   width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient UHID:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[5] + "</td></tr>");
				table.append("<tr height='30'  bgcolor='#8db7d6'  align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Patient Name:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' >" + data[6]
						+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[7]
						+ "</td><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' width='15%'><b>Patient Status:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[8] + "</td></tr>");
				table.append("<tr height='30' bgcolor='#ffffff'  align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Admission Doctor:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[9]
						+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Admission Doctor Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[10]
						+ "</td><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Nursing Unit:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[11] + "</td></tr>");
				table.append("<tr height='30'  bgcolor='#8db7d6' align='left'  ><td  width='15%'style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' ><b>Specialty:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[12]
						+ "</td><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Open Date:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[13] + "," + data[14]
						+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>HIS Added At:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[20] + "</td></tr>");

				table.append("<tr height='30'  bgcolor='#ffffff' align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Bed No.</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[18]
				 + "</td><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Specimen No.</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[19]
				 	+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Test Details:</b></td> <td  width='15%' style=' color:#0040ff; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' ><a style='cursor: pointer;text-decoration: none;'  href='#' onclick=displayFullDetail('" + data[17] + "') ><p style='color:blue'>View Test</p></a></td></tr>");
				     table.append("</tbody></table> ");
				setViewData(table.toString());

				if (status != null && (status.equalsIgnoreCase("Informed")) || (status.equalsIgnoreCase("Not Informed") && (data[8].toString().equalsIgnoreCase("OPD"))))
				{
					doctorNameMap = new LinkedHashMap<String, String>();
					doctorIdMap = new LinkedHashMap<String, String>();
					List list = new createTable().executeAllSelectQuery("SELECT emp_id,name,spec from referral_doctor", connectionSpace);
					if (list != null && list.size() > 0)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							doctorNameMap.put(Integer.parseInt(object[0].toString()) + "#" + object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString(), object[1].toString());
							doctorIdMap.put(Integer.parseInt(object[0].toString()) + "#" + object[1].toString() + "#" + object[0].toString() + "#" + object[2].toString(), object[0].toString());
						}
					}
					statusMap = new LinkedHashMap<String, String>();
					if ("Informed".equalsIgnoreCase(status) || "Informed".equalsIgnoreCase(status) || "OCC Park".equalsIgnoreCase(status))
					{
						statusMap.put("Close", "Close");
						statusMap.put("Snooze", "Park");
						statusMap.put("Ignore", "Ignore");
						statusMap.put("No Further Action Required", "No Further Action Required");
					}
					else if ("Informed".equalsIgnoreCase(status) || "Dept Park".equalsIgnoreCase(status))
					{
						list.clear();
						list = new createTable().executeAllSelectQuery("SELECT action_date, action_time,assign_to_name FROM referral_data_history WHERE status='Informed' AND rid='" + id + "' ORDER BY id DESC LIMIT 1", connectionSpace);
						if (list != null && list.size() > 0)
						{
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								Object object[] = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									actionDate = DateUtil.convertDateToIndianFormat(object[0].toString()) + ", " + object[1].toString().substring(0, 5);
								}
								if (object[2] != null)
								{
									assignTo = object[2].toString();
								}
							}
						}
						statusMap.put("Seen", "Seen");
						statusMap.put("Snooze-I", "Park");
						statusMap.put("Ignore-I", "Ignore");
					}
					return "Close";
				}
				else
				{

					if (status != null && (status.equalsIgnoreCase("Informed-P") || status.equalsIgnoreCase("Informed-P")))
					{
						StringBuilder qry = new StringBuilder("");
						qry.append(" select nursing_mob,nursing_comment,doc_reason,adm_doc_mob,doc_name,doc_comment,nursing_name  from critical_data_history where rid=" + id + " and status='Informed-P'");
						qry.append(" and action_date=(select max(action_date) from critical_data_history where  status='Informed-p'  and rid=" + id + ") ");
						qry.append("  and action_time=(select max(action_time) from critical_data_history where action_date=(select max(action_date) from critical_data_history where  status='Informed-p'  and rid=" + id + ") and status='Informed-p'  and rid=" + id + ") ;");

						List list = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
						if (list != null && list.size() > 0)
						{
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								Object object[] = (Object[]) iterator.next();

								infoPartial = object[0].toString() + "," + object[1].toString() + "," + object[2].toString() + "," + object[3].toString() + "," + object[4].toString() + "," + object[5].toString() + "," + object[6].toString();

							}
						}
						return SUCCESS;

					}
					if (status != null && (status.equalsIgnoreCase("CLose-P") || status.equalsIgnoreCase("Close-P")))
					{
						StringBuilder qry = new StringBuilder("");
						qry.append(" select comments, instruction from critical_data_history where rid=" + id + " and status='Close-P'");
						qry.append(" and action_date=(select max(action_date) from critical_data_history where  status='Close-P'  and rid=" + id + ") ");
						qry.append("  and action_time=(select max(action_time) from critical_data_history where action_date=(select max(action_date) from critical_data_history where  status='Close-P'  and rid=" + id + ") and status='Close-P'  and rid=" + id + ") ;");
						List list = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
						if (list != null && list.size() > 0)
						{
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								Object object[] = (Object[]) iterator.next();

								infoPartial = object[0].toString() + "," + object[1].toString();

							}
						}
						return "Close";
					}
					return SUCCESS;
				}
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

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public String takeActionOnCritical()
	{
		if (ValidateSession.checkSession())
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			String open_date=null,open_time = null,cur_date=null,cur_time=null,tat_not_to_inform=null;
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (request.getParameter("sendsms") != null)
				{

					String docMobile = request.getParameter("adm_doc_mob");
					String nurseMobile = request.getParameter("nursing_mob");
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Send SMS");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("rid");
					ob.setDataName(request.getParameter("rid"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_name");
					ob.setDataName(request.getParameter("nursing_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_sms");
					ob.setDataName(request.getParameter("nursing_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_mob");
					ob.setDataName(nurseMobile);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("adm_doc_sms");
					ob.setDataName(request.getParameter("adm_doc_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("doc_name");
					ob.setDataName(request.getParameter("adm_doc_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("adm_doc_mob");
					ob.setDataName(docMobile);
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
					ob.setColName("action_by");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);
					sendSms(nurseMobile, docMobile, connectionSpace, cbt, request.getParameter("rid"), DateUtil.getAddedTime2Current("00:60").split(" ")[1]);
					
					boolean status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("SMS Send Successfully ");
					}
				}
				else
				{

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					for (Iterator it = requestParameterNames.iterator(); it.hasNext();)
					{
						if (request.getParameter(it.next().toString()).equalsIgnoreCase("-1"))
						{
							it.remove();
						}
					}
					Iterator it = requestParameterNames.iterator();
					boolean nurse = false;
					boolean doc = false;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments"))
						{
							Parmname = "comments";
						}
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("nursecheck") || Parmname.equalsIgnoreCase("doccheck") || Parmname.equalsIgnoreCase("nursing_name") || Parmname.equalsIgnoreCase("nursing_mob") || Parmname.equalsIgnoreCase("nursing_comment") || Parmname.equalsIgnoreCase("doc_reason") || Parmname.equalsIgnoreCase("doc_name") || Parmname.equalsIgnoreCase("doc_mob") || Parmname.equalsIgnoreCase("doc_comment")
										|| Parmname.equalsIgnoreCase("doc_reason") || Parmname.equalsIgnoreCase("rid")

								))
						{
							ob = new InsertDataTable();
							if (Parmname.equalsIgnoreCase("nursing_mob"))
							{
								ob.setColName("nursing_mob");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("doc_mob"))
							{
								ob.setColName("adm_doc_mob");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("nursecheck"))
							{
								ob.setColName("nursing_inform");
								ob.setDataName(paramValue);
								nurse = true;
							}
							else if (Parmname.equalsIgnoreCase("doccheck"))
							{
								ob.setColName("doc_inform");
								ob.setDataName(paramValue);
								doc = true;
							}
							else if (Parmname.equalsIgnoreCase("doc_mob"))
							{
								ob.setColName("adm_doc_sms");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("sn_upto_date"))
							{
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							}
							else if (Parmname.equalsIgnoreCase("nursing_name"))
							{
								ob.setColName("nursing_name");
								ob.setDataName(request.getParameter("nursing_name")+" "+request.getParameter("nursing_empID")+" ");
								//nurse = paramValue;
							}
							else
							{
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
							}
							insertData.add(ob);
						}
					}

					ob = new InsertDataTable();
					ob.setColName("action_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_by");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");

					if (nurse==true && doc==true)
					{
						//ob.setDataName("Informed");
						ob.setDataName("Close");
					}
					else
					{
						ob.setDataName("Informed-P");
					}
					insertData.add(ob);
					boolean status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
					if (status)
					{
						List list = new createTable().executeAllSelectQuery("select open_date,open_time from critical_data where id='"+request.getParameter("rid")+"'", connectionSpace);
						if (list != null && list.size() > 0) 
						{
						 	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								open_date=object[0].toString();
								open_time=object[1].toString();
						 	}
						}
						cur_date=DateUtil.getCurrentDateUSFormat();
						cur_time=DateUtil.getCurrentTimewithSeconds();

						StringBuilder query = new StringBuilder();
						if (nurse==true && doc==true)
						{
							tat_not_to_inform=DateUtil.time_difference(open_date, open_time,cur_date, cur_time);
							String est = escTime("Critical Care", "67");
							sendSms(request.getParameter("nursing_mob"), request.getParameter("doc_mob"), connectionSpace, cbt, request.getParameter("rid"), DateUtil.getAddedTime2Current("00:30").split(" ")[1]);
							//query.append(" UPDATE critical_data SET status='Informed',escalate_date='" + est.split("#")[0] + "',escalate_time='" + est.split("#")[1] + "',tat_not_to_inform='"+tat_not_to_inform+"'    where patient_id='" + request.getParameter("rid") + "'");
							query.append(" UPDATE critical_data SET status='Close',escalate_date='" + est.split("#")[0] + "',escalate_time='" + est.split("#")[1] + "',tat_not_to_inform='"+tat_not_to_inform+"'    where patient_id='" + request.getParameter("rid") + "'");
						}
						else
						{
							query.append("UPDATE critical_data SET status='Informed-P'  where patient_id='" + request.getParameter("rid") + "'");
							sendSms(request.getParameter("nursing_mob"), request.getParameter("doc_mob"), connectionSpace, cbt, request.getParameter("rid"), DateUtil.getAddedTime2Current("00:30").split(" ")[1]);
						}

						cbt.updateTableColomn(connectionSpace, query);
					}
					if (status)
					{
						addActionMessage("Action Taken Successfully.");
					}
					else
					{
						addActionMessage("Oops!!! Some Error in Data.");
					}
				}

				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
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

	private void sendSms(String nurseMobile, String docMobile, SessionFactory connectionSpace, CommonOperInterface cbt, String string, String time)
	{
		StringBuilder query_test=new StringBuilder();
		query_test.append("select test_name,test_value from critical_patient_report where patient_id='"+string+"'");
		String test_name = "",test_val="";
		List data = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				test_name=object[0].toString();
				test_val=object[1].toString();
			}
		}
		query_test.setLength(0);
		boolean sts =	isInteger(test_name);
		if(!sts){
			
			
			test_name=test_name;
		}
		else{
		
			query_test.append("select test_name from test_name where id='"+test_name+"'");
			List data1 = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
			if (data1 != null && data1.size() > 0)
			{
				test_name = data1.get(0).toString();
			}
		}

		query_test.append("SELECT cridata.uhid AS uhid, criPat.bed_no FROM `critical_data` AS cridata INNER JOIN `critical_patient_data` AS criPat ON cridata.`uhid`=criPat.`uhid` WHERE cridata.`id`='"+string+"'");
		String Bed_no = "";
		List dataBed = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
		if (dataBed != null && dataBed.size() > 0)
		{
			for (Iterator iterator = dataBed.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				Bed_no=object[1].toString();
			//	test_val=object[1].toString();
			}
		}
		List list = cbt.executeAllSelectQuery("select * from critical_patient_data where id='" + string + "'", connectionSpace);
		if (list != null && list.size() > 0)
		{
			Object[] object = (Object[]) list.get(0);
			if (nurseMobile != null && nurseMobile != "" && nurseMobile.trim().length() == 10 && !nurseMobile.startsWith("NA") && nurseMobile.startsWith("9") || nurseMobile.startsWith("8") || nurseMobile.startsWith("7") || nurseMobile.startsWith("6"))
			{
				String msg = "Critical Patient Alert: " +object[1].toString() + " (" + object[2].toString() + "), Nursing Unit: " + object[7].toString() + ",Bed No.:" + Bed_no + ", Test Name: "+test_name+", Critical Value: "+test_val+", close by:" + DateUtil.getCurrentDateIndianFormat() + " " + time;
				new CommunicationHelper().addMessage(object[7].toString(), "", nurseMobile, msg, ticketNo, "Pending", "0", "CRC", connectionSpace);

			}
			if (docMobile != null && docMobile != "" && docMobile.trim().length() == 10 && !docMobile.startsWith("NA") && docMobile.startsWith("9") || docMobile.startsWith("8") || docMobile.startsWith("7") || docMobile.startsWith("6"))
			{
				String msg = "Critical Patient Alert: "  + object[1].toString() + " (" + object[2].toString() + ") , Nursing Unit: " + object[7].toString() + ", Bed No.:" + Bed_no+ ", Test Name: "+test_name+", Critical Value: "+test_val+", close by:" + DateUtil.getCurrentDateIndianFormat() + " " + time;
				new CommunicationHelper().addMessage(object[5].toString(), "", docMobile, msg, ticketNo, "Pending", "0", "CRC", connectionSpace);

			}
		}
	}

	private String escTime(String module, String Dept)
	{
		// TODO Auto-generated method stub
		String esctm = "";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder empuery = new StringBuilder();
		empuery.append("SELECT  l1Tol2, escSubDept FROM critical_escalation WHERE escSubDept='" + Dept + "' AND module='" + module + "' ");
		List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
		for (Iterator it = empData1.iterator(); it.hasNext();)
		{
			Object[] object = (Object[]) it.next();
			esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object[0].toString(), "Y");
		}
		return esctm;
	}

	/*public String closeCriticalTicket()
	{
		boolean status = false;
		String patMob = "";
		if (ValidateSession.checkSession())
		{

			try
			{

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				// ////System.out.println(paramValue +"   "+Parmname);
				
				if (patient_type != null && patient_type.equalsIgnoreCase("OPD")|| patient_type.equalsIgnoreCase("EM"))
				{int count = 0;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						////System.out.println("Parmname   =   " + Parmname + "    paramValue   ===  " + paramValue);

						patMob = request.getParameter("patient_mob_sms");
						
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && !Parmname.equalsIgnoreCase("patient_mob_sms") && Parmname != null
								&& (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("authorization") || Parmname.equalsIgnoreCase("patient_mob") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("patient_name") || Parmname.equalsIgnoreCase("doc_name") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("doc_mobile") || Parmname.equalsIgnoreCase("instruction") || Parmname.equalsIgnoreCase("patient_mob_sms")))
						{
							////System.out.println("Parmname1   =   " + Parmname + "    paramValue1   ===  " + paramValue);
							ob = new InsertDataTable();

							if (patMob != null && !patMob.equalsIgnoreCase("") && count == 0 && Parmname.equalsIgnoreCase("instruction")  )
							{
								
								count = count + 1;
								ob.setColName("instruction");
								ob.setDataName(request.getParameter("instruction") + " [" + request.getParameter("patient_mob_sms")+"]");
								

							}
						
							else if (Parmname.equalsIgnoreCase("status"))
							{

								if (request.getParameter("status").equalsIgnoreCase("Close-P"))
								{

									ob.setColName("status");
									ob.setDataName("Close-P");
								}

								else
								{
									ob.setColName("status");
									ob.setDataName("Close");
								}

							}
							else
							{
								if (Parmname.equalsIgnoreCase("doc_mob"))
								{
									////System.out.println("doc Number..." + Parmname);
									ob.setColName("adm_doc_sms");
									ob.setDataName(paramValue);

								}
								else
								{
									ob.setDataName(paramValue);
									ob.setColName(Parmname);

								}
							}
							insertData.add(ob);
							// String query =
							// "update critical_patient_data set patient_mob='"+request.getParameter("patient_mob")+"' where id='"+request.getParameter("rid")+"'";
							// cbt.executeAllUpdateQuery(query,
							// connectionSpace);

						}
					}
				}
				else
				{
					while (it.hasNext())
					{String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					//assign_to_emp_id
					//System.out.println("Parmname "+Parmname);
					//System.out.println("paramValue "+paramValue);
					
			//|| Parmname.equalsIgnoreCase("assign_to_emp_id") 
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("assign_to_id") 
						|| Parmname.equalsIgnoreCase("assign_to_name") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("assign_desig") ||  Parmname.equalsIgnoreCase("snooze_by_id") ||
						Parmname.equalsIgnoreCase("assign_close_by") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("sn_upto_date") || Parmname.equalsIgnoreCase("sn_upto_time") 
						|| Parmname.equalsIgnoreCase("assign_ref_to") || Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("ignore_by_id")
						|| Parmname.equalsIgnoreCase("assign_to_id_inf")|| Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_emp_id") || Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no") || Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments"))  && !Parmname.equalsIgnoreCase("reason"))
				{
					ob = new InsertDataTable();
					if (Parmname.equalsIgnoreCase("sn_upto_date"))
					{
						ob.setColName(Parmname);
						ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
						
					}
					else if (Parmname.equalsIgnoreCase("assign_to_emp_id"))
					{
						ob.setColName("assign_to_id");
						ob.setDataName(paramValue);
						
					}
					else if (Parmname.equalsIgnoreCase("assign_to_name"))
					{
						ob.setColName("close_by_name");
						ob.setDataName(paramValue);
						
					}
					
					else if(Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_name_inf"))
					{
						ob.setDataName(paramValue);
						ob.setColName("close_by_name");
					}
					else if(Parmname.equalsIgnoreCase("snooze_by_id")|| Parmname.equalsIgnoreCase("ignore_by_id") || Parmname.equalsIgnoreCase("assign_to_id_inf") || Parmname.equalsIgnoreCase("assign_to_emp_id"))
					{
						if(paramValue.contains("#"))
						{
							ob.setDataName(paramValue.split("#")[0]);
							ob.setColName("close_by_id");
						}
						else
						{
						ob.setDataName(paramValue);
						ob.setColName("close_by_id");
						}
					}
					else if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments")  )
					{
						ob.setDataName(paramValue);
						ob.setColName("reason");
					}
					else if(Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no") || Parmname.equalsIgnoreCase("mob_no"))
					{
						ob.setDataName(paramValue);
						ob.setColName("escalate_to_mob");
					}
					else{
					ob.setDataName(paramValue);
					ob.setColName(Parmname);
					}
					insertData.add(ob);
					
				}
				}

				}
				ob = new InsertDataTable();
				ob.setColName("action_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				
				 * if(!request.getParameter("Ig_remarks").equalsIgnoreCase(""))
				 * { ob = new InsertDataTable();
				 * ob.setColName("snAndIg_remarks");
				 * ob.setDataName(request.getParameter("Ig_remarks"));
				 * insertData.add(ob); }
				 * 
				 * if(!request.getParameter("sn_remarks").equalsIgnoreCase(""))
				 * { ob = new InsertDataTable();
				 * ob.setColName("snAndIg_remarks");
				 * ob.setDataName(request.getParameter("sn_remarks"));
				 * insertData.add(ob); }
				 

				status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
				if (status)
				{

					if (patient_type != null && patient_type.equalsIgnoreCase("IPD"))
					{

						StringBuilder query = new StringBuilder("UPDATE critical_data SET status='" + request.getParameter("status") + "'");
						if (request.getParameter("status").equalsIgnoreCase("close"))
						{
							// String time = new
							// ReferralAdd().getEscalationTime(request.getParameter("prtType"),request.getParameter("refToSpec"),
							// cbt, connectionSpace);
							// Getting new escalate date time
							// String escalateDateTime =
							// DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(),
							// DateUtil.getCurrentTime(), "00:00", time);

							query.append(" ,escalate_date='" + DateUtil.getCurrentDateUSFormat() + "',escalate_time='" + DateUtil.addTwoTime(DateUtil.getCurrentTimeHourMin(), "00:30") + "' ");

							query.append(" ,level='Level2'");
						}
						query.append(" WHERE patient_id='" + request.getParameter("rid") + "'");
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);
						
						 * else
						 * if(request.getParameter("status").equalsIgnoreCase
						 * ("Snooze") ||
						 * request.getParameter("status").equalsIgnoreCase
						 * ("Snooze-I")) {
						 * query.append(" ,escalate_date='"+DateUtil
						 * .convertDateToUSFormat
						 * (request.getParameter("sn_upto_date"
						 * ))+"',escalate_time='"
						 * +request.getParameter("sn_upto_time")+"' "); }
						 

					}
					else
					{
						// ////System.out.println("llllll  "+request.getParameter("authorization"));
						StringBuilder query = new StringBuilder("");
						if (request.getParameter("authorization").equalsIgnoreCase("Yes"))
						{
							// ////System.out.println("kjkjkjkjkjkjkjkj   "+request.getParameter("status"));
							if (request.getParameter("status").equalsIgnoreCase("Close"))
							{
								////System.out.println("kkkkkkkkkk" + request.getParameter("status"));
								query.append(" UPDATE critical_data SET status='Close' ");
								if(send_sms!=null && send_sms.equalsIgnoreCase("true"))
								{
									String msg = "Critical Result Alert From Medanta: " + request.getParameter("instruction");
									new CommunicationHelper().addMessage("", "", request.getParameter("patient_mob_sms"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
								}
								
								// String time = new
								// ReferralAdd().getEscalationTime(request.getParameter("prtType"),request.getParameter("refToSpec"),
								// cbt, connectionSpace);
								// Getting new escalate date time
								// String escalateDateTime =
								// DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(),
								// DateUtil.getCurrentTime(), "00:00", time);
								
								 * query.append(" ,escalate_date='"+DateUtil.getCurrentDateUSFormat
								 * ()+"',escalate_time='"+DateUtil.addTwoTime(
								 * DateUtil.getCurrentTimeHourMin(),
								 * "00:30")+"' ");
								 * 
								 * query.append(" ,level='Level2'");
								 

							}
							else if (request.getParameter("status").equalsIgnoreCase("Close-P"))
							{

								query.append(" UPDATE critical_data SET status='Close-P' ");

							}

						}
						else
						{
							if(send_sms!=null && send_sms.equalsIgnoreCase("true"))
							{
								String msg = "Critical Result Alert From Medanta:- " + request.getParameter("comments");
								new CommunicationHelper().addMessage(request.getParameter("patient_name"), "", request.getParameter("patient_mob"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
								new CommunicationHelper().addMessage(request.getParameter("doc_name"), "", request.getParameter("doc_mob"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
							}
							

							query.append(" UPDATE critical_data SET status='Close' ");
						}

						query.append(" WHERE patient_id='" + request.getParameter("rid") + "'");
						////System.out.println(".......>>>>>>>>....<<<<<<<<<....." + query.toString());
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);

					}
					addActionMessage("Action Taken Successfully ");

				}
				else
				{
					addActionMessage("Oops!!! Some Error in Data ");
				}

				
				 * if (status) { //Add message to informed
				 * if((request.getParameter("status").equalsIgnoreCase("Close")
				 * || request.getParameter("status").equalsIgnoreCase("Ignore")
				 * ||
				 * request.getParameter("status").equalsIgnoreCase("Ignore-I")||
				 * request.getParameter("status").equalsIgnoreCase("Snooze") ||
				 * request.getParameter("status").equalsIgnoreCase("Snooze-I")))
				 * { String empMob=null; String
				 * sts=request.getParameter("status");
				 * if(sts.equalsIgnoreCase("Ignore")) { sts="OCC Ignore";
				 * empMob=request.getParameter("mob_no_ignore"); } else
				 * if(sts.equalsIgnoreCase("Ignore-I")) { sts="Dept Ignore";
				 * empMob=request.getParameter("mob_no_ignore"); } else {
				 * empMob=request.getParameter("mob_no"); }
				 * 
				 * if (empMob != null && empMob != "" && empMob.trim().length()
				 * == 10 && !empMob.startsWith("NA") && empMob.startsWith("9")
				 * || empMob.startsWith("8") || empMob.startsWith("7") ||
				 * empMob.startsWith("6")) { String msg =
				 * sts+": "+request.getParameter
				 * ("ticketNo")+", Location: "+request
				 * .getParameter("bed")+"/"+request
				 * .getParameter("uhid")+", Priority: "
				 * +request.getParameter("prtType")+"."; new
				 * CommunicationHelper().addMessage("", "", empMob, msg,
				 * ticketNo, "Pending", "0", "CRF", connectionSpace); } }
				 * addActionMessage("Action Taken Successfully."); } else {
				 * addActionMessage("Oops!!! Some Error in Data."); }
				 

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
	}*/
	
	
	
	public String closeCriticalTicket()
	{
		boolean status = false;
		String patMob = "";
		String open_date=null,open_time = null,cur_date=null,cur_time=null,tat_not_to_inform=null;
		if (ValidateSession.checkSession())
		{

			try
			{

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				if (patient_type != null && patient_type.equalsIgnoreCase("OPD")|| patient_type.equalsIgnoreCase("EM"))
				{
					int count = 0;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);

						patMob = request.getParameter("patient_mob_sms");
						
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && !Parmname.equalsIgnoreCase("patient_mob_sms") && Parmname != null
								&& (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("authorization") || Parmname.equalsIgnoreCase("patient_mob") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("patient_name") || Parmname.equalsIgnoreCase("doc_name") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("doc_mobile") || Parmname.equalsIgnoreCase("instruction") || Parmname.equalsIgnoreCase("patient_mob_sms")))
						{
							ob = new InsertDataTable();

							if (patMob != null && !patMob.equalsIgnoreCase("") && count == 0 && Parmname.equalsIgnoreCase("instruction")  )
							{
								
								count = count + 1;
								ob.setColName("instruction");
								ob.setDataName(request.getParameter("instruction") + " [" + request.getParameter("patient_mob_sms")+"]");
								

							}
						
							else if (Parmname.equalsIgnoreCase("status"))
							{

								if (request.getParameter("status").equalsIgnoreCase("Close-P"))
								{

									ob.setColName("status");
									ob.setDataName("Close-P");
								}

								else
								{
									ob.setColName("status");
									if(request.getParameter("status").equalsIgnoreCase("Close"))
									{
										ob.setDataName("Close");
									}
									else if(request.getParameter("status").equalsIgnoreCase("No Response"))
									{
										ob.setDataName("No Response");
									}
									else
									{
										ob.setDataName("No Forther Action require");
									}
									//ob.setDataName((request.getParameter("status").equalsIgnoreCase("Close")?"Close":"No Forther Action require"));
								} 
							}
							else
							{
								if (Parmname.equalsIgnoreCase("doc_mob"))
								{
									ob.setColName("adm_doc_sms");
									ob.setDataName(paramValue);

								}
								else if(Parmname.equalsIgnoreCase("comments") && request.getParameter("toWhome")!=null)
										{
											ob.setColName(Parmname);
											ob.setDataName("No Response From ("+request.getParameter("toWhome")+")"+", Comment: "+request.getParameter("comments"));
										}
								else
								{
									ob.setDataName(paramValue);
									ob.setColName(Parmname);

								}
							}
							insertData.add(ob);
						}
					}
				}
				else
				{
					while (it.hasNext())
					{String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("assign_to_id") 
						|| Parmname.equalsIgnoreCase("assign_to_name") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("assign_desig") ||  Parmname.equalsIgnoreCase("snooze_by_id") ||
						Parmname.equalsIgnoreCase("assign_close_by") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("sn_upto_date") || Parmname.equalsIgnoreCase("sn_upto_time") 
						|| Parmname.equalsIgnoreCase("assign_ref_to") || Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("ignore_by_id")
						|| Parmname.equalsIgnoreCase("assign_to_id_inf")|| Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_emp_id") || Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no") || Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments"))  && !Parmname.equalsIgnoreCase("reason"))
				{
					ob = new InsertDataTable();
					if (Parmname.equalsIgnoreCase("sn_upto_date"))
					{
						ob.setColName(Parmname);
						ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
						
					}
					else if (Parmname.equalsIgnoreCase("assign_to_name"))
					{
						ob.setColName("close_by_name");
						ob.setDataName(paramValue);
						
					}
					
					else if(Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_name_inf"))
					{
						ob.setDataName(paramValue);
						ob.setColName("close_by_name");
					}
					else if(Parmname.equalsIgnoreCase("snooze_by_id")|| Parmname.equalsIgnoreCase("ignore_by_id") || Parmname.equalsIgnoreCase("assign_to_id_inf") || Parmname.equalsIgnoreCase("assign_to_emp_id"))
					{
						if(paramValue.contains("#"))
						{
							ob.setDataName(paramValue.split("#")[0]);
							ob.setColName("close_by_id");
						}
						else
						{
						ob.setDataName(paramValue);
						ob.setColName("close_by_id");
						}
					}
					else if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments")  )
					{
						ob.setDataName(paramValue);
						ob.setColName("reason");
					}
					else if(Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no") || Parmname.equalsIgnoreCase("mob_no"))
					{
						ob.setDataName(paramValue);
						ob.setColName("escalate_to_mob");
					}
					else{
					ob.setDataName(paramValue);
					ob.setColName(Parmname);
					}
					insertData.add(ob);
					
				}
				}

				}
				ob = new InsertDataTable();
				ob.setColName("action_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
				if (status && !request.getParameter("status").equalsIgnoreCase("No Response"))
				{

					if (patient_type != null && patient_type.equalsIgnoreCase("IPD")|| patient_type.equalsIgnoreCase("EM"))
					{
						List list = new createTable().executeAllSelectQuery("select action_date,action_time,status from critical_data_history where status in('Informed') and rid='"+request.getParameter("rid")+"'", connectionSpace);
						if (list != null && list.size() > 0) 
						{
						 	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								open_date=object[0].toString();
								open_time=object[1].toString();
						 	}
						}
						cur_date=DateUtil.getCurrentDateUSFormat();
						cur_time=DateUtil.getCurrentTimewithSeconds();
						StringBuilder query = new StringBuilder("UPDATE critical_data SET status='" + request.getParameter("status") + "'");
						if (request.getParameter("status").equalsIgnoreCase("Close"))
						{
							tat_not_to_inform=DateUtil.time_difference(open_date, open_time,cur_date, cur_time);
							query.append(" ,escalate_date='" + DateUtil.getCurrentDateUSFormat() + "',escalate_time='" + DateUtil.addTwoTime(DateUtil.getCurrentTimeHourMin(), "00:30") + "' ");
							query.append(" ,level='Level2' ,tat_inform_to_close='"+tat_not_to_inform+"' ");
						}
						query.append(" WHERE patient_id='" + request.getParameter("rid") + "'");
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);
						
					}
					else
					{
						StringBuilder query = new StringBuilder("");
						if (request.getParameter("authorization").equalsIgnoreCase("Yes"))
						{
							List list1 = new createTable().executeAllSelectQuery("select open_date,open_time from critical_data where patient_id='"+request.getParameter("rid")+"'", connectionSpace);
							if (list1 != null && list1.size() > 0) 
							{
							 	for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
									Object[] object = (Object[]) iterator.next();
									open_date=object[0].toString();
									open_time=object[1].toString();
							 	}
							}
							cur_date=DateUtil.getCurrentDateUSFormat();
							cur_time=DateUtil.getCurrentTimewithSeconds();
							if (request.getParameter("status").equalsIgnoreCase("Close"))
							{
								tat_not_to_inform=DateUtil.time_difference(open_date, open_time,cur_date, cur_time);
								query.append(" UPDATE critical_data SET status='Close' ,tat_not_to_inform='"+tat_not_to_inform+"' ,tat_inform_to_close='"+tat_not_to_inform+"' ");
								if(send_sms!=null && send_sms.equalsIgnoreCase("true"))
								{
									String msg = "Critical Result Alert From Medanta: " + request.getParameter("instruction");
									new CommunicationHelper().addMessage("", "", request.getParameter("patient_mob_sms"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
								}
								

							}
							else if (request.getParameter("status").equalsIgnoreCase("Close-P"))
							{

								query.append(" UPDATE critical_data SET status='Close-P' ");

							}
							else if (request.getParameter("status").equalsIgnoreCase("No Further Action Required"))
							{

								query.append(" UPDATE critical_data SET status='No Further Action Required' ");

							}

						}
						else
						{
							if (request.getParameter("authorization").equalsIgnoreCase("No Further Action Required"))
							{
								query.append(" UPDATE critical_data SET status='No Further Action Required' ");
								
							}
							else
							{
								if(send_sms!=null && send_sms.equalsIgnoreCase("true"))
								{
									String msg = "Critical Result Alert From Medanta:- " + request.getParameter("comments");
									new CommunicationHelper().addMessage(request.getParameter("patient_name"), "", request.getParameter("patient_mob"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
									new CommunicationHelper().addMessage(request.getParameter("doc_name"), "", request.getParameter("doc_mob"), msg, ticketNo, "Pending", "0", "CRC", connectionSpace);
								}
								
								query.append(" UPDATE critical_data SET status='Close' ");
							}
							
							

							
						}

						query.append(" WHERE patient_id='" + request.getParameter("rid") + "'");
						//System.out.println(".......>>>>>>>>....<<<<<<<<<....." + query.toString());
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);

					}
					addActionMessage("Action Taken Successfully ");

				}
				else if(request.getParameter("status").equalsIgnoreCase("No Response"))
				{
					addActionMessage("Action Taken Successfully ");
				}

				else
				{
					addActionMessage("Oops!!! Some Error in Data ");
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


	public String testDetails()
	{

		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query = new StringBuilder();
				query.append("select cpr.uhid,tt.test_type,tn.test_name,cpr.test_value,cpr.test_unit,cpr.test_comment, cpr.test_type as test, cpr.test_name as testr from critical_patient_report as cpr ");
				query.append(" left join test_name as tn on tn.id=cpr.test_name  ");
				query.append(" left join test_type as tt on tt.id=tn.test_type  ");
				query.append(" where cpr.patient_id='" + pid + "'");
				List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					testDetails = new ArrayList<ArrayList<String>>();
					ArrayList<String> tempList = null;
					for (Object obj : list)
					{
						Object[] data = (Object[]) obj;
						tempList = new ArrayList<String>();

						// if(data[1].)
						boolean sts = isInteger(data[6].toString());

						boolean stsTwo = isInteger(data[7].toString());

						tempList.add(data[0].toString());
						if (!sts)
						{
							tempList.add(checkBlank(data[6].toString()));
						}
						else
						{
							tempList.add(checkBlank(data[1].toString()));

						}
						if (!stsTwo)
						{
							tempList.add(checkBlank(data[7].toString()));
						}
						else
						{
							tempList.add(checkBlank(data[2].toString()));

						}
						tempList.add(checkBlank(data[3].toString()));
						tempList.add(checkBlank(data[4].toString()));
						tempList.add(checkBlank(data[5].toString()));

						testDetails.add(tempList);
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
		return SUCCESS;

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

	public String beforeEscalateAction()
	{
		return SUCCESS;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public String viewTicketHistory()
	{
		try
		{

			String data[] = viewData.split(",");
			StringBuilder table = new StringBuilder();
			table.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
			table.append("<tr  height='30' bgcolor='#8db7d6' align='left'  ><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' class='Title' width='15%'><b>Caller Employee ID:</b></td ><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[0]
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Caller Emp Name:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[1]
					+ "</td><td  width='15%'  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Caller Emp Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[2] + "</td></tr>");
			table.append("<tr  height='30' bgcolor='#ffffff' align='left'  ><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Lab Doctor Name:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[3]
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Lab Doctor Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[4]
					+ "</td><td   width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient UHID:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[5] + "</td></tr>");
			table.append("<tr height='30'  bgcolor='#8db7d6'  align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Patient Name:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' >" + data[6]
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Patient Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[7]
					+ "</td><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' width='15%'><b>Patient Status:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[8] + "</td></tr>");
			table.append("<tr height='30' bgcolor='#ffffff'  align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Admission Doctor:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[9]
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Admission Doctor Mobile:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[10]
					+ "</td><td  style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Nursing Unit:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[11] + "</td></tr>");
			table.append("<tr height='30'  bgcolor='#8db7d6' align='left'  ><td  width='15%'style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' ><b>Specialty:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[12]
					+ "</td><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Open Date:</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[13] + "," + data[14]
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>His Added At:</b></td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[19] + "</td></tr>");
			table.append("<tr height='30'  bgcolor='#ffffff' align='left' ><td style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'  width='15%'><b>Bed No.</b></td><td width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + data[18]
					+ "</td><td </td><td >"
					+ "</td><td  width='15%' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Test Details:</b></td> <td  width='15%' style=' color:#0040ff; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;' ><a style='cursor: pointer;text-decoration: none;'  href='#' onclick=displayFullDetail('" + data[17] + "') ><p style='color:blue'>View Test</p></a></td></tr>");
			table.append("</tbody></table> ");
			setViewData(table.toString());

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List list = null;
			dataList = new ArrayList<ArrayList<String>>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT status,action_date,action_time,comments,esc_level,action_by,sn_upto_date,sn_upto_time,escalate_to,close_by_name,escalate_to_mob,nursing_mob,nursing_sms,adm_doc_mob,adm_doc_sms,nurse_reason,doc_reason,nursing_name,doc_name,instruction,comments as com FROM critical_data_history WHERE rid = '" + id + "' ");
			list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			ArrayList<String> tempList = null;
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					tempList = new ArrayList<String>();
					if (CUA.getValueWithNullCheck(object[0]).equalsIgnoreCase("Snooze"))
					{
						tempList.add("OCC Park");
					}
					else if (CUA.getValueWithNullCheck(object[0]).equalsIgnoreCase("Snooze-I"))
					{
						tempList.add("Dept Park");
					}
					else if (CUA.getValueWithNullCheck(object[0]).equalsIgnoreCase("Ignore"))
					{
						tempList.add("OCC Ignore");
					}
					else if (CUA.getValueWithNullCheck(object[0]).equalsIgnoreCase("Ignore-I"))
					{
						tempList.add("Dept Ignore");
					}
					else
					{
						tempList.add(CUA.getValueWithNullCheck(object[0]));
					}

					tempList.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1])) + ", " + CUA.getValueWithNullCheck(object[2]).substring(0, 5));
					if (object[3] != null)
					{
						if (object[6] != null && object[7] != null)
						{
							tempList.add(object[3] + " Parked Till Date:" + DateUtil.convertDateToIndianFormat(object[6].toString()) + ", " + object[7].toString());
						}
						else
						{
							tempList.add(object[3].toString());
						}
					}
					else
					{
						tempList.add("NA");
					}
					if (object[4] != null)
					{
						if (object[8] != null && !"".equalsIgnoreCase(object[4].toString()) && !"NA".equalsIgnoreCase(object[4].toString()))
						{

							if (object[10] != null) tempList.add(object[4].toString() + ": " + object[8].toString() + "(" + object[10].toString() + ")");
							else tempList.add(object[4].toString() + ": " + object[8].toString());
						}
						else
						{
							tempList.add("-");
						}
					}

					if (object[5] != null)
					{
						if (object[5].toString().equalsIgnoreCase("NA"))
						{
							tempList.add("Automated");
						}
						else
						{
							tempList.add(getEmpNameMobByEmpId(object[5].toString(), connectionSpace, cbt).split("#")[0]);

						}
					}
					else
					{
						tempList.add("Auto");
					}
					if (object[10] != null)
					{
						tempList.add(CUA.getValueWithNullCheck(object[9]) + " [" + object[10].toString() + "]");
					}
					else
					{
						tempList.add(CUA.getValueWithNullCheck(object[9]));
					}

					tempList.add(CUA.getValueWithNullCheck(object[11]));

					tempList.add(CUA.getValueWithNullCheck(object[12]));

					tempList.add(CUA.getValueWithNullCheck(object[13]));
					tempList.add(CUA.getValueWithNullCheck(object[14]));

					tempList.add(CUA.getValueWithNullCheck(object[15]));

					tempList.add(CUA.getValueWithNullCheck(object[16]));

					tempList.add(object[17].toString());

					tempList.add(object[18].toString());

					tempList.add(object[19].toString());
					tempList.add(object[20].toString());

					dataList.add(tempList);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "success";
	}

	public String getEmpNameMobByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		String empDetails = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("SELECT empName, mobOne FROM employee_basic WHERE  id=" + empId, connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empDetails;
	}

	public String esacateActionOnCritical()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CriticalServiceHelper CSFH = new CriticalServiceHelper();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				String level = request.getParameter("level");
				String calculationLevel = String.valueOf(Integer.parseInt(level.substring(5, 6)) + 1);

				ob = new InsertDataTable();
				ob.setColName("escalate_to");
				ob.setDataName(request.getParameter("escalate_to"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalate_to_mob");
				ob.setDataName(request.getParameter("escalate_to_mob"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("esc_level");
				ob.setDataName("Level" + calculationLevel);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("rid");
				ob.setDataName(request.getParameter("rid"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Escalate"); // previous is Escalate-D remove
				// after discussion with Manish sir
				// by Aarif 07-01-2016
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
				ob.setColName("action_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				boolean status = false;

				String subDept = CSFH.getSubDeptId(request.getParameter("rid"), new HelpdeskUniversalHelper(), connectionSpace);
				int next = Integer.parseInt(calculationLevel) + 1;

				String nxtEsc = CSFH.escTime(priority, "l" + calculationLevel + "Tol" + String.valueOf(next), subDept, "escLevelL" + calculationLevel + "L" + next, connectionSpace);
				StringBuilder query = new StringBuilder();

				if (nxtEsc != null)
				{
					status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
					if (status)
					{
						query.append("UPDATE critical_data SET level='Level" + calculationLevel + "', ");
						query.append(" escalate_date='" + nxtEsc.split("#")[0] + "', ");
						query.append(" escalate_time='" + nxtEsc.split("#")[1] + "' ");
						query.append(" WHERE patient_id=" + request.getParameter("rid") + "");
						status = cbt.updateTableColomn(connectionSpace, query);
					}
				}
				else
				{
					addActionMessage("There is no one Mapped for Next Level.");
					return SUCCESS;
				}

				if (status)
				{
					// Add message to informed escalation
					if (request.getParameter("escalate_to_mob") != null && !request.getParameter("escalate_to_mob").equalsIgnoreCase(""))
					{
						String empMob = request.getParameter("escalate_to_mob");
						if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
						{
							StringBuilder query_test=new StringBuilder();
							query_test.append("select test_name,test_value from critical_patient_report where patient_id='"+request.getParameter("rid")+"'");
							String test_name = "",test_val="";
							List data = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									test_name=object[0].toString();
									test_val=object[1].toString();
								}
							}
							query_test.setLength(0);
							boolean sts =	isInteger(test_name);
							if(!sts){
								
								
								test_name=test_name;
							}
							else{
							
								query_test.append("select test_name from test_name where id='"+test_name+"'");
								List data1 = cbt.executeAllSelectQuery(query_test.toString(), connectionSpace);
								if (data1 != null && data1.size() > 0)
								{
									test_name = data1.get(0).toString();
								}
							}
							 
							String msg ="Escalation Alert for "+request.getParameter("level")+": "+"UHID:"+request.getParameter("uhid")+", Test Name: "+test_name+", Critical Value:"+test_val;
							new CommunicationHelper().addMessage("", "", empMob, msg, ticketNo, "Pending", "0", "CRC", connectionSpace);

						}
					}
					addActionMessage("Action Taken Successfully.");
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
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}

	public String fetchDetailsData()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			JSONObject job = null;
			commonJSONArray = new JSONArray();
			String query = "select name,spec from referral_doctor where emp_id='" + docID + "' group by name order by name ";
			////System.out.println("query " + query);
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{

				for (Object obj : data)
				{
					job = new JSONObject();
					Object[] ob = (Object[]) obj;
					if (ob[0] != null && ob[1] != null)
					{

						job.put("name", ob[0].toString() + "(" + ob[1].toString() + ")");
						commonJSONArray.add(job);
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnString = "error";
		}
		return SUCCESS;
	}

	public String fetchDetailsEmp()
	{
		String returnResult = "error";
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String tst = "NA";
		try
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
				listObject.put("fName", rs.getString("FIRSTNAME"));
				listObject.put("lName", rs.getString("LASTNAME"));
			}
			////System.out.println("rs.getString(FIRSTNAME)>>>   " + rs.getString("FIRSTNAME"));
			commonJSONArray.add(listObject);

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
	
	public String beforeBulkActionCritical(){
		String sts = "error";
		try{
		if(status.equalsIgnoreCase("Not Informed") || status.equalsIgnoreCase("Informed-P")){
		//System.out.println("status>>   "+status);
		sts = "SUCCESS";
		}
		if(status.equalsIgnoreCase("Informed")){
			//System.out.println("status>>   "+status);
			
			
			doctorNameMap = new LinkedHashMap<String, String>();
			doctorIdMap = new LinkedHashMap<String, String>();
			List list = new createTable().executeAllSelectQuery("SELECT emp_id,name,spec from referral_doctor", connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					doctorNameMap.put(Integer.parseInt(object[0].toString()) + "#" + object[0].toString() + "#" + object[1].toString() + "#" + object[2].toString(), object[1].toString());
					doctorIdMap.put(Integer.parseInt(object[0].toString()) + "#" + object[1].toString() + "#" + object[0].toString() + "#" + object[2].toString(), object[0].toString());
				}
			}
			
			
			
			statusMap = new LinkedHashMap<String, String>();
			
				statusMap.put("Close", "Close");
				statusMap.put("Snooze", "Park");
				statusMap.put("Ignore", "Ignore");
				statusMap.put("No Further Action Required", "No Further Action Required");
			
			
			
			sts = "CLOSE";
			}
		//System.out.println("complainID>>   "+complainIds);
		
		return sts;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sts;
	}

	
	//@SuppressWarnings( { "unchecked", "rawtypes" })
	public String takeBulkActionOnCritical()
	{
		if (ValidateSession.checkSession())
		{
			//Lock lock = new ReentrantLock();
			//lock.lock();
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				boolean sts = false;
				// ////System.out.println(">>>>>>>>>>>>>>>>>>>>  "+request.getParameter("sendsms").toString());
				if (request.getParameter("sendsms") != null)
				{
					//System.out.println("complainIds:   "+complainIds);
					
					String[] complainID = complainIds.split(",");
					
					for (int i = 0; i < complainID.length; i++)
					{
						insertData.clear();
					
					String docMobile = request.getParameter("adm_doc_mob");
					String nurseMobile = request.getParameter("nursing_mob");
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Send SMS");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("rid");
					ob.setDataName(complainID[i]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_name");
					ob.setDataName(request.getParameter("nursing_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_sms");
					ob.setDataName(request.getParameter("nursing_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursing_mob");
					ob.setDataName(nurseMobile);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("adm_doc_sms");
					ob.setDataName(request.getParameter("adm_doc_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("doc_name");
					ob.setDataName(request.getParameter("adm_doc_sms"));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("adm_doc_mob");
					ob.setDataName(docMobile);
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
					ob.setColName("action_by");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);
					////System.out.println("A");
					sendSms(nurseMobile, docMobile, connectionSpace, cbt, complainID[i], DateUtil.getAddedTime2Current("00:60").split(" ")[1]);
					
					sts = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
					
					}
					if (sts)
					{
						addActionMessage("SMS Send Successfully ");
					}
				}
				else
				{
					boolean status = false;
					String[] complainID = complainIds.split(",");
					
					for (int i = 0; i < complainID.length; i++)
					
					{
						insertData.clear();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					for (Iterator it = requestParameterNames.iterator(); it.hasNext();)
					{
						if (request.getParameter(it.next().toString()).equalsIgnoreCase("-1"))
						{
							it.remove();
						}
					}
					Iterator it = requestParameterNames.iterator();
					String nurse = null;
					String doc = null;
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments"))
						{
							Parmname = "comments";
						}
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("nursecheck") || Parmname.equalsIgnoreCase("doccheck") || Parmname.equalsIgnoreCase("nursing_name") || Parmname.equalsIgnoreCase("nursing_mob") || Parmname.equalsIgnoreCase("nursing_comment") || Parmname.equalsIgnoreCase("doc_reason") || Parmname.equalsIgnoreCase("doc_name") || Parmname.equalsIgnoreCase("doc_mob") || Parmname.equalsIgnoreCase("doc_comment")
										|| Parmname.equalsIgnoreCase("doc_reason") || Parmname.equalsIgnoreCase("rid")

								))
						{
							ob = new InsertDataTable();
							if (Parmname.equalsIgnoreCase("nursing_mob"))
							{
								ob.setColName("nursing_mob");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("doc_mob"))
							{
								ob.setColName("adm_doc_mob");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("nursecheck"))
							{
								ob.setColName("nursing_inform");
								ob.setDataName(paramValue);
								nurse = paramValue;
							}
							else if (Parmname.equalsIgnoreCase("doccheck"))
							{
								ob.setColName("doc_inform");
								ob.setDataName(paramValue);
								doc = paramValue;
							}
							else if (Parmname.equalsIgnoreCase("doc_mob"))
							{
								ob.setColName("adm_doc_sms");
								ob.setDataName(paramValue);

							}
							else if (Parmname.equalsIgnoreCase("sn_upto_date"))
							{
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							}
							else
							{
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
							}
							insertData.add(ob);
						}
					}

					
					ob = new InsertDataTable();
					ob.setColName("rid");
					ob.setDataName(complainID[i]);
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
					ob.setColName("action_by");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");

					if (nurse != null && doc != null && nurse.equalsIgnoreCase("true") && nurse.equalsIgnoreCase("true"))
					{
						//ob.setDataName("Informed");
						ob.setDataName("Close");
					}
					else
					{
						ob.setDataName("Informed-P");
					}
					insertData.add(ob);
					status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
					if (status)
					{
						StringBuilder query = new StringBuilder();
						if (nurse != null && doc != null && nurse.equalsIgnoreCase("true") && nurse.equalsIgnoreCase("true"))
						{

							String est = escTime("Critical Care", "67");
							sendSms(request.getParameter("nursing_mob"), request.getParameter("doc_mob"), connectionSpace, cbt, complainID[i], DateUtil.getAddedTime2Current("00:30").split(" ")[1]);
							//query.append(" UPDATE critical_data SET status='Informed',escalate_date='" + est.split("#")[0] + "',escalate_time='" + est.split("#")[1] + "'    where patient_id='" + complainID[i] + "'");
							query.append(" UPDATE critical_data SET status='Close',escalate_date='" + est.split("#")[0] + "',escalate_time='" + est.split("#")[1] + "'    where patient_id='" + complainID[i] + "'");
						}
						else
						{
							query.append("UPDATE critical_data SET status='Informed-P'  where patient_id='" + complainID[i] + "'");
							sendSms(request.getParameter("nursing_mob"), request.getParameter("doc_mob"), connectionSpace, cbt, complainID[i], DateUtil.getAddedTime2Current("00:30").split(" ")[1]);
						}

						cbt.updateTableColomn(connectionSpace, query);
					}
				}
					if (status)
					{
						
						addActionMessage("Action Taken Successfully.");
					}
					else
					{
						addActionMessage("Oops!!! Some Error in Data.");
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
	
	
	public String bulkCloseCriticalTicket()
	{
		boolean status = false;
		String patMob = "";
		if (ValidateSession.checkSession())
		{

			try
			{
				String[] complainID = complainIds.split(",");
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				for (int i = 0; i < complainID.length; i++)
				{
					insertData.clear();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				// ////System.out.println(paramValue +"   "+Parmname);
				
				if (patient_type != null && patient_type.equalsIgnoreCase("OPD")|| patient_type.equalsIgnoreCase("EM"))
				{}
				else
				{
					while (it.hasNext())
					{String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					//assign_to_emp_id
					//System.out.println("Parmname "+Parmname);
					//System.out.println("paramValue "+paramValue);
					
			//|| Parmname.equalsIgnoreCase("assign_to_emp_id") 
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("assign_to_id") 
						|| Parmname.equalsIgnoreCase("assign_to_name") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("assign_desig") ||  Parmname.equalsIgnoreCase("snooze_by_id") ||
						Parmname.equalsIgnoreCase("assign_close_by") ||  Parmname.equalsIgnoreCase("sn_upto_date") || Parmname.equalsIgnoreCase("sn_upto_time") 
						|| Parmname.equalsIgnoreCase("assign_ref_to") || Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("ignore_by_id")
						|| Parmname.equalsIgnoreCase("assign_to_id_inf")|| Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_emp_id") || Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no") || Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments"))  && !Parmname.equalsIgnoreCase("reason"))
				{
					ob = new InsertDataTable();
					if (Parmname.equalsIgnoreCase("sn_upto_date"))
					{
						ob.setColName(Parmname);
						ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
						
					}
					/*else if (Parmname.equalsIgnoreCase("assign_to_emp_id"))
					{
						ob.setColName("assign_to_id");
						ob.setDataName(paramValue);
						
					}*/
					else if (Parmname.equalsIgnoreCase("assign_to_name"))
					{
						ob.setColName("close_by_name");
						ob.setDataName(paramValue);
						
					}
					
					else if(Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by") || Parmname.equalsIgnoreCase("assign_to_name_inf"))
					{
						ob.setDataName(paramValue);
						ob.setColName("close_by_name");
					}
					else if(Parmname.equalsIgnoreCase("snooze_by_id")|| Parmname.equalsIgnoreCase("ignore_by_id") || Parmname.equalsIgnoreCase("assign_to_id_inf") || Parmname.equalsIgnoreCase("assign_to_emp_id"))
					{
						if(paramValue.contains("#"))
						{
							ob.setDataName(paramValue.split("#")[0]);
							ob.setColName("close_by_id");
						}
						else
						{
						ob.setDataName(paramValue);
						ob.setColName("close_by_id");
						}
					}
					else if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments")  )
					{
						ob.setDataName(paramValue);
						ob.setColName("reason");
					}
					else if(Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no") || Parmname.equalsIgnoreCase("mob_no"))
					{
						ob.setDataName(paramValue);
						ob.setColName("escalate_to_mob");
					}
					else{
					ob.setDataName(paramValue);
					ob.setColName(Parmname);
					}
					insertData.add(ob);
					
				}
				}

				}
				
				ob = new InsertDataTable();
				ob.setColName("rid");
				ob.setDataName(complainID[i]);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("action_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("action_by");
				ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
				insertData.add(ob);

				status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
				if (status)
				{

				

						StringBuilder query = new StringBuilder("UPDATE critical_data SET status='" + request.getParameter("status") + "'");
						if (request.getParameter("status").equalsIgnoreCase("close"))
						{
							query.append(" ,escalate_date='" + DateUtil.getCurrentDateUSFormat() + "',escalate_time='" + DateUtil.addTwoTime(DateUtil.getCurrentTimeHourMin(), "00:30") + "' ");

							query.append(" ,level='Level2'");
						}
						query.append(" WHERE patient_id='" + complainID[i] + "'");
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);

					
					
					addActionMessage("Action Taken Successfully ");

				}
				else
				{
					addActionMessage("Oops!!! Some Error in Data ");
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
	
	// No Response IN IPD CASE
	
	public String takeActionOnCriticalWithNoResponse()
	{
		if (ValidateSession.checkSession())
			{
				Lock lock = new ReentrantLock();
				lock.lock();
				String open_date=null,open_time = null,cur_date=null,cur_time=null,tat_not_to_inform=null;
				try
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (request.getParameter("sendsms") != null)
					{

						String docMobile = request.getParameter("adm_doc_mob");
						String nurseMobile = request.getParameter("nursing_mob");
					

						ob = new InsertDataTable();
						ob.setColName("rid");
						ob.setDataName(request.getParameter("rid"));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("No Response");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("comments");
						ob.setDataName("No Response From ("+request.getParameter("toWhome")+")"+", Comment: "+request.getParameter("comment"));
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
						ob.setColName("action_by");
						ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
						insertData.add(ob);
						
						boolean status = cbt.insertIntoTable("critical_data_history", insertData, connectionSpace);
						if (status)
						{
							addActionMessage("Action Taken Successfully ");
						}
					}
					
					return SUCCESS;
				}
				catch (Exception e)
				{
					e.printStackTrace();
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
		
	
	
	public String checkBlank(String value)
	{
		return ((value.equalsIgnoreCase("") || value == null) ? "NA" : value);
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	public Map<String, String> getStatusMap()
	{
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap)
	{
		this.statusMap = statusMap;
	}

	public String getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(String openDate)
	{
		this.openDate = openDate;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public List getDataList()
	{
		return dataList;
	}

	public void setDataList(List dataList)
	{
		this.dataList = dataList;
	}

	public String getUhid()
	{
		return uhid;
	}

	public void setUhid(String uhid)
	{
		this.uhid = uhid;
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

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public String getActionDate()
	{
		return actionDate;
	}

	public void setActionDate(String actionDate)
	{
		this.actionDate = actionDate;
	}

	public String getAssignTo()
	{
		return assignTo;
	}

	public void setAssignTo(String assignTo)
	{
		this.assignTo = assignTo;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getViewData()
	{
		return viewData;
	}

	public void setViewData(String viewData)
	{
		this.viewData = viewData;
	}

	public String getNursing_unit()
	{
		return nursing_unit;
	}

	public void setNursing_unit(String nursing_unit)
	{
		this.nursing_unit = nursing_unit;
	}

	public String getAdmdoc()
	{
		return admdoc;
	}

	public void setAdmdoc(String admdoc)
	{
		this.admdoc = admdoc;
	}

	public String getAdmdoc_mob()
	{
		return admdoc_mob;
	}

	public void setAdmdoc_mob(String admdoc_mob)
	{
		this.admdoc_mob = admdoc_mob;
	}

	public String getTest_type()
	{
		return test_type;
	}

	public void setTest_type(String test_type)
	{
		this.test_type = test_type;
	}

	public String getPatient_type()
	{
		return patient_type;
	}

	public void setPatient_type(String patient_type)
	{
		this.patient_type = patient_type;
	}

	public String getPatient_mob()
	{
		return patient_mob;
	}

	public void setPatient_mob(String patient_mob)
	{
		this.patient_mob = patient_mob;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public ArrayList<ArrayList<String>> getTestDetails()
	{
		return testDetails;
	}

	public void setTestDetails(ArrayList<ArrayList<String>> testDetails)
	{
		this.testDetails = testDetails;
	}

	public String getInfoPartial()
	{
		return infoPartial;
	}

	public void setInfoPartial(String infoPartial)
	{
		this.infoPartial = infoPartial;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public String getDocID()
	{
		return docID;
	}

	public void setDocID(String docID)
	{
		this.docID = docID;
	}

	public String getSend_sms()
	{
		return send_sms;
	}

	public void setSend_sms(String sendSms)
	{
		send_sms = sendSms;
	}

	public String getComplainIds()
	{
		return complainIds;
	}

	public void setComplainIds(String complainIds)
	{
		this.complainIds = complainIds;
	}
	

}
