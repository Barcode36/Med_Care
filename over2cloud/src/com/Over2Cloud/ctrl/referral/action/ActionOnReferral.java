package com.Over2Cloud.ctrl.referral.action;

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
import com.Over2Cloud.ctrl.referral.activity.ReferralActivityBoardHelper;
import com.Over2Cloud.ctrl.referral.services.ReferralServiceFileHelper;
import com.aspose.slides.p7cce53cf.qu;
import com.opensymphony.xwork2.ActionContext;

public class ActionOnReferral extends GridPropertyBean implements ServletRequestAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String status,level;
	private String ticketNo;
	private String refBy, refBySpec;
	private String refTo, ref_to,refToSpec,subSpec;
	private String reason;
	private String priority;
	private String bed, openDate,actionDate="NA",assignTo="NA";
	private String uhid,patientName,nursingUnit;
	private Map<String, String> statusMap;
	private Map<String, String> doctorNameMap;
	private Map<String, String> doctorIdMap;
	private JSONArray jsonArr;
	private String value;
	private String fromDate;
	private String toDate;
	private String empId;
	private String empName;
	private String refDoc;
	@SuppressWarnings("rawtypes")
	private List dataList;
	private String refDocTo;
	private String specialty;
	private String locationWise;
	private String pat_name;
	public String esacateActionOnReferral()
	{if (ValidateSession.checkSession())
	{
		try
		{
			ReferralServiceFileHelper RSFH=new ReferralServiceFileHelper();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			String level=request.getParameter("level");
			////System.out.println("vvv "+level.length());
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
			ob.setDataName("Level"+calculationLevel);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("rid");
			ob.setDataName(request.getParameter("rid"));
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("status");
			ob.setDataName("Escalate-D");
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
			

			boolean status = false ;
			//cbt.insertIntoTable("referral_data_history", insertData, connectionSpace);
			
				String subDept = RSFH.getSubDeptId(request.getParameter("ref_to"), new HelpdeskUniversalHelper(), connectionSpace);
				String nxtEsc = RSFH.escTime(request.getParameter("priority"), "tat" + (Integer.parseInt(calculationLevel) + 1), subDept, "l" + calculationLevel, connectionSpace);
				StringBuilder query = new StringBuilder();
				
				if (nxtEsc != null)
				{
					status = cbt.insertIntoTable("referral_data_history", insertData, connectionSpace);
					if(status){
					query.append("UPDATE referral_data SET level='Level" + calculationLevel + "', ");
					query.append(" escalate_date='" + nxtEsc.split("#")[0] + "', ");
					query.append(" escalate_time='" + nxtEsc.split("#")[1] + "' ");
					query.append(" WHERE id=" + request.getParameter("rid") + "");
					//System.out.println(query);
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
				//Add message to informed escalation
				if(request.getParameter("escalate_to_mob")!=null && !request.getParameter("escalate_to_mob").equalsIgnoreCase(""))
				{
					String empMob=request.getParameter("escalate_to_mob");
					if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
					{
						//String msg = "Escalation Alert: Ticket No: "+request.getParameter("ticketNo")+", Location: "+request.getParameter("bed")+"/"+request.getParameter("uhid")+", Priority: "+request.getParameter("priority")+".";
						//String msg ="Escalation Alert: "+"UHID:"+request.getParameter("uhid")+", Location: "+request.getParameter("bed")+",Priority:"+request.getParameter("priority")+",Assign To:"+request.getParameter("refTo")+",Ref Specialty:"+request.getParameter("refToSpec");//,Ref by specialty:"+request.getParameter("refBySpec")+"+"close by:"+DateUtil.getCurrentDateIndianFormat()+"-"+DateUtil.getCurrentTimeHourMin();
						
						String msg ="Escalation Alert: "+"UHID:"+request.getParameter("uhid")+", Location: "+request.getParameter("bed")+",Priority:"+request.getParameter("priority")+",Ref For:"+request.getParameter("refTo")+",Ref Specialty:"+request.getParameter("refToSpec");   //msg change assign to changes with REF FOR 12-02-2016
						new CommunicationHelper().addMessage("", "", empMob, msg, ticketNo, "Pending", "0", "CRF", connectionSpace);
					}
				}
				addActionMessage("Action Taken Successfully.");
			} else
			{
				addActionMessage("Oops!!! Some Error in Data.");
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

	public String beforeEscalateAction()
	{
		////System.out.println(level+"level");
		return SUCCESS;
	}
	// for History View of referral
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String viewActivityHistoryData()
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List list = null;
				dataList = new ArrayList<ArrayList<String>>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query.append("SELECT status,action_date,action_time,comments,esc_level,action_by,sn_upto_date,sn_upto_time,escalate_to,assign_to_name,escalate_to_mob, snAndIg_remarks FROM referral_data_history WHERE rid = '" + id + "' ");
				//System.out.println("history::"+query);
				list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				ArrayList<String> tempList = null;
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();

						tempList = new ArrayList<String>();
						////System.out.println(object[0]);
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
						
						tempList.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1]))+", "+CUA.getValueWithNullCheck(object[2]).substring(0, 5));
						if (object[3] != null)
						{
							if(object[6]!=null && object[7]!=null)
							{
								tempList.add(object[3]+" Parked Till Date:"+DateUtil.convertDateToIndianFormat(object[6].toString())+", "+object[7].toString());
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
						if (object[4] != null )
						{
							if(object[8]!=null && !"".equalsIgnoreCase(object[4].toString()))
							{
								if(object[10]!=null)
									tempList.add(object[4].toString()+": "+object[8].toString()+"("+object[10].toString()+")");
								else
									tempList.add(object[4].toString()+": "+object[8].toString());
							}
							else
							{
								tempList.add(object[4].toString());
							}
						}
						else
						{
							tempList.add("NA");
						}
						if (object[5] != null)
						{
							tempList.add(new ReferralActivityBoardHelper().getEmpNameMobByEmpId(object[5].toString(),connectionSpace,cbt).split("#")[0]);
						}
						else
						{
							tempList.add("Auto");
						}
						if (object[10] != null)
						{
							tempList.add(CUA.getValueWithNullCheck(object[9])+" ["+object[10].toString()+"]");
						}
						else
						{	
							tempList.add(CUA.getValueWithNullCheck(object[9]));
						}	
						
						if (object[11] != null)
						{
							tempList.add(CUA.getValueWithNullCheck(object[11]));
						}
						else
						{	
							tempList.add("NA");
						}
						dataList.add(tempList);
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}

			return "success";
		}
		
	@SuppressWarnings("rawtypes")
	public String beforeTakeActionReferral()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				doctorNameMap = new LinkedHashMap<String, String>();
				doctorIdMap = new LinkedHashMap<String, String>();
				List list = new createTable().executeAllSelectQuery("SELECT emp_id,name,spec from referral_doctor", connectionSpace);
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						doctorNameMap.put(Integer.parseInt(object[0].toString())+"#"+object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString(), object[1].toString());
						doctorIdMap.put(Integer.parseInt(object[0].toString())+"#"+object[1].toString()+"#"+object[0].toString()+"#"+object[2].toString(), object[0].toString());
					}
				}
				statusMap = new LinkedHashMap<String, String>();
			//	System.out.println("status   "+getStatus());
				if ("Not Informed".equalsIgnoreCase(status) || "OCC Park".equalsIgnoreCase(status))
				{
			//		System.out.println("statusnnn "+getStatus());
					statusMap.put("Informed", "Informed");
					statusMap.put("Snooze", "Park");
					statusMap.put("Ignore", "Ignore");
					statusMap.put("No Response", "No Response");
					
				} else if ("Informed".equalsIgnoreCase(status) || "Dept Park".equalsIgnoreCase(status))
				{
					list.clear();
					list=new createTable().executeAllSelectQuery("SELECT action_date, action_time,assign_to_name FROM referral_data_history WHERE status='Informed' AND rid='"+id+"' ORDER BY id DESC LIMIT 1",connectionSpace);
					if(list!=null && list.size()>0)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();) {
							Object object[] = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								actionDate=DateUtil.convertDateToIndianFormat(object[0].toString())+", "+object[1].toString().substring(0, 5);
							}
							if(object[2]!=null)
							{
								assignTo=object[2].toString();
							}
						}
					}
					
					statusMap.put("Seen", "Seen");
					statusMap.put("Snooze-I", "Park");
					statusMap.put("Ignore-I", "Ignore");
				}

				if(id.split(",").length<=1)
									return SUCCESS;
								else
									return "successInBulk";

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

	 
// For bulk close and single close both  : Sanjay
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	 public String takeActionOnReferral()
{
		boolean status=false ;
	 	 
		if (ValidateSession.checkSession())
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			try
			{
				int i= request.getParameter("rid").split(",").length;
				
				if(i<=1)
				{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					//System.out.println("ParameterValue  "+paramValue +" ParameterName  "+Parmname);
			 		if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("remark") || Parmname.equalsIgnoreCase("ignore_comments") && !paramValue.equalsIgnoreCase("-1") )
					{
						Parmname = "comments";
					}
					
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && (Parmname.equalsIgnoreCase("status") || Parmname.equalsIgnoreCase("toWhome") || Parmname.equalsIgnoreCase("assign_to_id") 
							|| Parmname.equalsIgnoreCase("assign_to_name") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("assign_desig") ||
							Parmname.equalsIgnoreCase("assign_close_by") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("sn_upto_date") || Parmname.equalsIgnoreCase("sn_upto_time")
							|| Parmname.equalsIgnoreCase("assign_ref_to") || Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("assign_to_emp_id") 
							|| Parmname.equalsIgnoreCase("assign_to_id_inf")|| Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by")|| Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no")) && !Parmname.equalsIgnoreCase("Ig_remarks") && !Parmname.equalsIgnoreCase("sn_remarks"))
					{
						ob = new InsertDataTable();
						if (Parmname.equalsIgnoreCase("sn_upto_date"))
						{
							ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
						}
						else if(paramValue.contains("#"))
						{
							ob.setDataName(paramValue.split("#")[2]);
						}
						else
						{
							ob.setDataName(paramValue);
						}
						if(Parmname.equalsIgnoreCase("toWhome") || Parmname.equalsIgnoreCase("assign_ref_to")|| Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("ignore_by") || Parmname.equalsIgnoreCase("snooze_by"))
						{
							Parmname="assign_to_name";
						}
						else if(Parmname.equalsIgnoreCase("assign_to_emp_id")|| Parmname.equalsIgnoreCase("assign_to_id_inf"))
						{
							Parmname="assign_to_id";
						}
						if(Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no"))
						{
							Parmname="escalate_to_mob";
						}
						ob.setColName(Parmname);
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
				
				
				if(!request.getParameter("Ig_remarks").equalsIgnoreCase(""))
				{
				ob = new InsertDataTable();
				ob.setColName("snAndIg_remarks");
				ob.setDataName(request.getParameter("Ig_remarks"));
				insertData.add(ob);
				}
				
				if(!request.getParameter("sn_remarks").equalsIgnoreCase(""))
				{
				ob = new InsertDataTable();
				ob.setColName("snAndIg_remarks");
				ob.setDataName(request.getParameter("sn_remarks"));
				insertData.add(ob);
				}
				

				 status = cbt.insertIntoTable("referral_data_history", insertData, connectionSpace);
				if (status  )
				{
					
					StringBuilder query = new StringBuilder();
					if(request.getParameter("status").equalsIgnoreCase("No Response"))
					{
						query.append("UPDATE referral_data SET no_response_flag='1'");
					}
					
				else{
					query.append("UPDATE referral_data SET status='" + request.getParameter("status") + "'");
					if(request.getParameter("status").equalsIgnoreCase("Informed"))
					{
						String time = new ReferralAdd().getEscalationTime(request.getParameter("prtType"),request.getParameter("refToSpec"), cbt, connectionSpace);
						// Getting new escalate date time
						//String escalateDateTime = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", time);
						if(time!=null)
						{
							query.append(" ,escalate_date='"+DateUtil.convertDateToUSFormat(time.split(" ")[0])+"',escalate_time='"+time.split(" ")[1]+"' ");
						}
						query.append(" ,no_response_flag='0' ,level='Level1'");
					}
					/*else if(request.getParameter("status").equalsIgnoreCase("Snooze") || request.getParameter("status").equalsIgnoreCase("Snooze-I"))
					{
						query.append(" ,escalate_date='"+DateUtil.convertDateToUSFormat(request.getParameter("sn_upto_date"))+"',escalate_time='"+request.getParameter("sn_upto_time")+"' ");
					}*/
					}
					
					query.append(" WHERE id='" + request.getParameter("rid") + "'");
					//System.out.println("query >>>>>>>>> "+query);
					cbt.updateTableColomn(connectionSpace, query);
				}
				
			}	
				else
				{
					
					for(int j=0;j<request.getParameter("rid").split(",").length;j++)
					{
						
					 	
			 		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String paramValue=null;
					while (it.hasNext())
					{
				  		String Parmname = it.next().toString();
						 paramValue = request.getParameter(Parmname);
					 	if (Parmname.equalsIgnoreCase("snooze_comments") || Parmname.equalsIgnoreCase("ignore_comments") && !paramValue.equalsIgnoreCase("-1") )
						{
							Parmname = "comments";
						}
						
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1")  && Parmname != null && (Parmname.equalsIgnoreCase("status")  ||Parmname.equalsIgnoreCase("assign_to_id") 
								|| Parmname.equalsIgnoreCase("assign_to_name") || Parmname.equalsIgnoreCase("comments") || Parmname.equalsIgnoreCase("assign_desig") ||
								Parmname.equalsIgnoreCase("assign_close_by") || Parmname.equalsIgnoreCase("rid") || Parmname.equalsIgnoreCase("sn_upto_date") || Parmname.equalsIgnoreCase("sn_upto_time")
								|| Parmname.equalsIgnoreCase("assign_ref_to") || Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("assign_to_emp_id") 
								|| Parmname.equalsIgnoreCase("assign_to_id_inf")|| Parmname.equalsIgnoreCase("ignore_by")|| Parmname.equalsIgnoreCase("snooze_by")|| Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no") || Parmname.equalsIgnoreCase("snAndIg_remarks")))
						{
							ob = new InsertDataTable();
							if (Parmname.equalsIgnoreCase("sn_upto_date"))
							{
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							}
							else if(paramValue.contains("#"))
							{
								ob.setDataName(paramValue.split("#")[2]);
							}
							else if(Parmname.equalsIgnoreCase("rid"))
							{
								ob.setDataName(paramValue.split(",")[j]);
								
					 		}
							else
							{
								ob.setDataName(paramValue);
							}
							if(Parmname.equalsIgnoreCase("assign_ref_to")|| Parmname.equalsIgnoreCase("assign_to_name_inf") || Parmname.equalsIgnoreCase("ignore_by") || Parmname.equalsIgnoreCase("snooze_by"))
							{
								Parmname="assign_to_name";
							}
							else if(Parmname.equalsIgnoreCase("assign_to_emp_id")|| Parmname.equalsIgnoreCase("assign_to_id_inf"))
							{
								Parmname="assign_to_id";
							}
							if(Parmname.equalsIgnoreCase("ignore_mob_no")|| Parmname.equalsIgnoreCase("snooze_mob_no")|| Parmname.equalsIgnoreCase("mob_no"))
							{
								Parmname="escalate_to_mob";
							}
							ob.setColName(Parmname);
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
					

					status = cbt.insertIntoTable("referral_data_history", insertData, connectionSpace);
					if (status && !request.getParameter("status").equalsIgnoreCase("No Response"))
					{
						StringBuilder query = new StringBuilder("UPDATE referral_data SET status='" + request.getParameter("status") + "'");
						if(request.getParameter("status").equalsIgnoreCase("Informed"))
						{
							String priSubDept = fetchPriSubDept(request.getParameter("rid").split(",")[j], connectionSpace);
							String time = new ReferralAdd().getEscalationTime(priSubDept.split("#")[0],priSubDept.split("#")[1], cbt, connectionSpace);
							// Getting new escalate date time
							//String escalateDateTime = DateUtil.getAddressingDatetime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:00", time);
							if(time!=null)
							{
								query.append(" ,escalate_date='"+DateUtil.convertDateToUSFormat(time.split(" ")[0])+"',escalate_time='"+time.split(" ")[1]+"' ");
							}
							query.append(" ,level='Level1'");
						}
						/*else if(request.getParameter("status").equalsIgnoreCase("Snooze") || request.getParameter("status").equalsIgnoreCase("Snooze-I"))
						{
							query.append(" ,escalate_date='"+DateUtil.convertDateToUSFormat(request.getParameter("sn_upto_date"))+"',escalate_time='"+request.getParameter("sn_upto_time")+"' ");
						}*/
						query.append(" WHERE id='" +request.getParameter("rid").split(",")[j] + "'".toString());
				 		cbt.updateTableColomn(connectionSpace, query);
					}
					
				}	
					
				}
				if (status)
				{
					//Add message to informed
					if(i<=1)
					{

						if((request.getParameter("status").equalsIgnoreCase("Informed") || request.getParameter("status").equalsIgnoreCase("Ignore") || request.getParameter("status").equalsIgnoreCase("Ignore-I")|| request.getParameter("status").equalsIgnoreCase("Snooze") || request.getParameter("status").equalsIgnoreCase("Snooze-I")))
						{
							String empMob=null;
							String sts=request.getParameter("status");
							if(sts.equalsIgnoreCase("Ignore"))
							{
								sts="OCC Ignore";
								empMob=request.getParameter("mob_no_ignore");
							}
							else if(sts.equalsIgnoreCase("Ignore-I"))
							{
								sts="Dept Ignore";
								empMob=request.getParameter("mob_no_ignore");
							}
							else
							{
								empMob=request.getParameter("mob_no");
							}
						
							if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
							{
								String msg=null;
								if(request.getParameter("status").equalsIgnoreCase("Informed"))
								{
									System.out.println("Inform "+request.getParameter("refTo")+"Location "+request.getParameter("bed")+"/"+request.getParameter("uhid")+" Referred To: "+ref_to+"Priority: "+request.getParameter("prtType"));
							 		String ref_to=	request.getParameter("refTo");
						 			msg = sts+": "+request.getParameter("ticketNo")+", Location: "+request.getParameter("bed")+"/"+request.getParameter("uhid")+" ( "+request.getParameter("pat_name")+" ) "+",Referred To: "+ref_to+", Priority: "+request.getParameter("prtType")+".";
					 			}
								else
								{
									msg = sts+": "+request.getParameter("ticketNo")+", Location: "+request.getParameter("bed")+"/"+request.getParameter("uhid")+", Priority: "+request.getParameter("prtType")+".";
								}
								new CommunicationHelper().addMessage("", "", empMob, msg, ticketNo, "Pending", "0", "CRF", connectionSpace);
							}
						}
						
					}
					//Only Bulk
					else
					{
						String ticketID = null,bed=null,uhid=null,ref_to=null,priority=null,nurshing_unit=null;
						String[] sendSMS=request.getParameter("rid").split(",");
						for(int j=0;j<sendSMS.length;j++)
						{
							StringBuilder query=new StringBuilder();
							CommonOperInterface cbt = new CommonConFactory().createInterface();
							query.append("select refdata.id,refdata.ticket_no,rpd.bed,rpd.uhid,rd.name,refdata.priority,rpd.nursing_unit,  rpd.patient_name FROM referral_data AS refdata "
								+"INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id "
								+"INNER JOIN referral_doctor AS rd ON refdata.ref_to=rd.id where refdata.id='"+sendSMS[j]+"' ");
							List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator1 = data.iterator(); iterator1.hasNext();)
								{
									Object[] object = (Object[]) iterator1.next();
									if (object != null)
									{
										ticketID=chkNULL(object[1].toString());
										bed=chkNULL(object[2].toString());
										uhid=chkNULL(object[3].toString());
										ref_to=chkNULL(object[4].toString());
										priority=chkNULL(object[5].toString());
										nurshing_unit=chkNULL(object[6].toString());
										pat_name = chkNULL(object[7].toString());
										System.out.println("nnnnn "+nurshing_unit);
									}
								}
							}
							if((request.getParameter("status").equalsIgnoreCase("Informed") || request.getParameter("status").equalsIgnoreCase("Ignore") || request.getParameter("status").equalsIgnoreCase("Ignore-I")|| request.getParameter("status").equalsIgnoreCase("Snooze") || request.getParameter("status").equalsIgnoreCase("Snooze-I")))
							{
								String empMob=null;
								String sts=request.getParameter("status");
								if(sts.equalsIgnoreCase("Ignore"))
								{
									sts="OCC Ignore";
									empMob=request.getParameter("mob_no_ignore");
								}
								else if(sts.equalsIgnoreCase("Ignore-I"))
								{
									sts="Dept Ignore";
									empMob=request.getParameter("mob_no_ignore");
								}
								else
								{
									empMob=request.getParameter("mob_no");
								}
							
								if (empMob != null && empMob != "" && empMob.trim().length() == 10 && !empMob.startsWith("NA") && empMob.startsWith("9") || empMob.startsWith("8") || empMob.startsWith("7") || empMob.startsWith("6"))
								{
									 
									String msg=null;
									if(request.getParameter("status").equalsIgnoreCase("Informed"))
									{
										System.out.println("Inform "+request.getParameter("refTo")+"Location "+request.getParameter("bed")+"/"+request.getParameter("uhid")+" Referred To: "+ref_to+"Priority: "+request.getParameter("prtType"));
								 		//String ref_to=	request.getParameter("refTo");
							 			msg = sts+": "+ticketID+", Location: "+bed+"/"+nurshing_unit+"/"+uhid+ " ("+pat_name+") ,Referred To: "+ref_to+", Priority: "+priority+".";
						 			}
									else
									{
										msg = sts+": "+ticketID+", Location: "+bed+"/"+nurshing_unit+"/"+uhid+", Priority: "+priority+".";
									}
									new CommunicationHelper().addMessage("", "", empMob, msg, ticketNo, "Pending", "0", "CRF", connectionSpace);
								}
							}
							
						
						}
					}
					
					addActionMessage("Action Taken Successfully.");
				} else
				{
					addActionMessage("Oops!!! Some Error in Data.");
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			finally
			{
				lock.unlock();
			}
		} else
		{
			return LOGIN;
		}
	
}
	
	private String fetchPriSubDept(String rid, SessionFactory connectionSpace) {
	// TODO Auto-generated method stub
		String rer = " # ";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder();
			empuery.append("select refData.priority, sub.subdeptname, sub.id from referral_data as refData inner join referral_doctor as refDoc on refDoc.id = refData.ref_to inner join subdepartment as sub on sub.subdeptname=refDoc.spec where refData.id="+rid+"");
			

			List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			if (empData1 != null && empData1.size() > 0)
			{

				for (Iterator it = empData1.iterator(); it.hasNext();)
				{
					Object[] object = (Object[]) it.next();
					if (object[0] != null && !"".equalsIgnoreCase(object[0].toString()))
					{
						
						rer = object[0].toString()+"#"+object[1].toString();
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	return rer;
}

	public String getReasonName()
	{
    	String returnresult=ERROR;
    	boolean sessionFlag = ValidateSession.checkSession();
    	if (sessionFlag)
    	{
    		try
    		{
    			StringBuffer query=new StringBuffer();
    			query.append("select id,reason_name from machine_reason_master ");
    			query.append("where module_name='CRF' ");
	    		if(value!=null && value.equalsIgnoreCase("Snooze-I"))
	    		{
	    			query.append(" and action_type='"+getValue()+"'  ORDER BY reason_name");
	    			////System.out.println("query State if "+query);
	    		}
	    		else
	    		{
	    			query.append(" and action_type='"+getValue()+"'  ORDER BY reason_name");
	    			////System.out.println("query State else "+query);
	    		}
	    		
	    		List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
    		
    		
	    		 if (dataList != null && dataList.size() > 0)
	    			{
	    			 jsonArr = new JSONArray();
	    				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
	    				{
	    					Object[] object = (Object[]) iterator.next();
	    					if (object[0] != null && object[1] != null)
	    					{
	    						JSONObject obj= new JSONObject();
	    						obj.put("ID",object[0].toString() );
	    						obj.put("NAME", object[1].toString());
	    						//System.out.println("ID "+object[0].toString());
	    						//System.out.println("Name "+object[1].toString());
	    						jsonArr.add(obj);
	    					}
	    				}
	    			}
    			
    			returnresult=SUCCESS;
    		}catch(Exception e){
    			e.printStackTrace();
    			returnresult = ERROR;
    			}
    	}else {
    		return LOGIN; 
    	}
    	return returnresult;
    	
	}
	// for get counter
	public String getCounterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
	if(sessionFlag){
		try{
			jsonArr= new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject objSer = new JSONObject();
			StringBuilder qry= new StringBuilder();
			
			
			qry.append(" select count(*),status from referral_data where reg_by='"+session.get("empIdofuser").toString().split("-")[1]+"'");
			
			if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase("") )
			{
				String str[] = getFromDate().split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					 
					qry.append (" and open_date between '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
				} else
				{
					 
					qry.append("and open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
				}
			}
			
			
			qry.append (" group by status "); 
			
			//System.out.println("counter query "+qry);
			List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
			
			if(data!=null && !data.isEmpty())
			{    String Informed="0",  NotInformed="0",Seen="0", Snooze="0", SnoozeD="0",Ignore="0",IgnoreD="0";
				
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{ 	
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						if(object[1].toString().equalsIgnoreCase("Informed"))
						{
							Informed=object[0].toString();	
						}else if(object[1].toString().equalsIgnoreCase("Not Informed"))
						{
							NotInformed=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Seen"))
						{
							Seen=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Snooze"))
						{
							Snooze=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Snooze-I"))
						{
							SnoozeD=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Ignore"))
						{
							Ignore=object[0].toString();
						}
						else if(object[1].toString().equalsIgnoreCase("Ignore-I"))
						{
							IgnoreD=object[0].toString();
						}
						
						
					}
				}
				obj.put("Informed", Informed);
				obj.put("NotInformed", NotInformed);
				obj.put("Seen",Seen);
				obj.put("Snooze", Snooze);
				obj.put("SnoozeD", SnoozeD);
				obj.put("Ignore",Ignore);
				obj.put("IgnoreD",IgnoreD);
				
				//jsonArr.add(obj);
			}
			
			qry.setLength(0);
			
			boolean flag = false;
			qry.append(" select count(*),refdata.status from referral_data as refdata    ");
			qry.append("  INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id ");
			qry.append("  INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
			qry.append("  INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
			qry.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
		 	/*if(flag){
				qry.append("  and ");
			}
			else {
				qry.append("  where ");
			}*/
			if(status!=null && ("SnoozeH".equalsIgnoreCase(getStatus()) || "SnoozeI".equalsIgnoreCase(getStatus())))
			{
				qry.append(" WHERE refdata.id!=0");
			}
			else
			{
				qry.append(" WHERE refdata.status=rdh.status ");
			}
			
			if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase("") && status != null && !"-1".equalsIgnoreCase(status))
			{
				String str[] = getFromDate().split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					qry.append(" AND refdata.open_date BETWEEN '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
				} else
				{
					qry.append(" AND refdata.open_date BETWEEN '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
				}
			}
			if (getSearchString() != null && !getSearchString().equalsIgnoreCase(""))
			{
				qry.append(" AND (refdata.ticket_no  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR refdata.open_date  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR rpd.uhid  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR rpd.patient_name  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR rpd.bed  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR rpd.nursing_unit  LIKE '%" + getSearchString() + "%'");
				qry.append(" OR refdata.status  LIKE '" + getSearchString() + "%')");
			}
			if (status != null && !"-1".equalsIgnoreCase(status) && !"All".equalsIgnoreCase(status) && !"OCC Escalate".equalsIgnoreCase(status) && !"Dept Escalate".equalsIgnoreCase(status)&& !"SnoozeH".equalsIgnoreCase(status)&& !"SnoozeI".equalsIgnoreCase(status))
			{
				qry.append(" AND refdata.status='" + getStatus() + "' ");
				
			} else if ("OCC Escalate".equalsIgnoreCase(status))
			{
				qry.append(" AND refdata.status IN('Not Informed','Snooze') AND refdata.level!='Level1'");
				
			} else if ("Dept Escalate".equalsIgnoreCase(status))
			{
				qry.append(" AND refdata.status IN('Seen','Snooze-I') AND refdata.level!='Level1'");
				
			} else if ("SnoozeH".equalsIgnoreCase(getStatus()))
			{
				qry.append(" AND rdh.status = 'Snooze'");
				
			}else if ("SnoozeI".equalsIgnoreCase(getStatus()))
			{
				qry.append(" AND rdh.status = 'Snooze-I'");
			}
			else if (!"All".equalsIgnoreCase(status))
			{
				qry.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
			}
			else if ("All".equalsIgnoreCase(status))
			{
				//qry.append(" AND refdata.status NOT IN('Seen','Ignore','Ignore-I') ");
			}
			else{
				
				qry.append(" AND refdata.status IN('Not Informed','Snooze','Snooze-I', 'Seen') ");
			}
			if (priority != null && !"-1".equalsIgnoreCase(priority))
			{
				qry.append(" AND refdata.priority='" + getPriority() + "' ");
			}
			if (nursingUnit != null && !"-1".equalsIgnoreCase(nursingUnit))
			{
				qry.append(" AND rpd.nursing_unit='" + getNursingUnit() + "' ");
			}
			

			if (refDocTo != null && !"-1".equalsIgnoreCase(refDocTo))
			{
				qry.append(" AND rd1.id='"+ getRefDocTo() +"' ");
			}
			
			
			if (specialty != null && !"-1".equalsIgnoreCase(specialty))
			{
				qry.append(" AND rd1.spec='" + getSpecialty() + "' ");
			}
			if (level != null && !"-1".equalsIgnoreCase(level))
			{
				qry.append(" AND refdata.level='" + getLevel() + "' ");
			}
			 
			String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
			String[] subModuleRightsArray = getSubModuleRights(empid);
			if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
			{
				for(String s:subModuleRightsArray)
				{
					if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No") )
					{
						String subDept=null;
						ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
						subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	 				 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
						{
				 		  qry.append(" and rd1.spec in ( "+subDept+" )");
						}
						else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
						{
				 			  qry.append(" and rd.spec in ( "+subDept+" )");	
						}
				 		 
					}
					if(s.equals("locationView_VIEW"))
					{
						ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
						String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
						if(nursingUnit!=null && !nursingUnit.contains("All"))
							qry.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
					}
					if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
					{
					if(s.equals("locationManagerView_VIEW"))
					{
						ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
						String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
						if(nursingUnit!=null  )
							qry.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
					}
					}
					
				}
			}
			
			qry.append (" group by status "); 
		//System.out.println("qryqry >>>>    "+qry);
			List dataser=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
			
			if(dataser!=null && !dataser.isEmpty())
			{   String Informeda="0",  NotInformeda="0",Seena="0", Snoozea="0", SnoozeDa="0",Ignorea="0",IgnoreDa="0";
				
			for (Iterator iterator = dataser.iterator(); iterator.hasNext();)
			{ 	
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					if(object[1].toString().equalsIgnoreCase("Informed"))
					{
						Informeda=object[0].toString();	
					}else if(object[1].toString().equalsIgnoreCase("Not Informed"))
					{
						NotInformeda=object[0].toString();
					}
					else if(object[1].toString().equalsIgnoreCase("Seen"))
					{
						Seena=object[0].toString();
					}
					else if(object[1].toString().equalsIgnoreCase("Snooze"))
					{
						Snoozea=object[0].toString();
					}
					else if(object[1].toString().equalsIgnoreCase("Snooze-I"))
					{
						SnoozeDa=object[0].toString();
					}
					else if(object[1].toString().equalsIgnoreCase("Ignore"))
					{
						Ignorea=object[0].toString();
					}
					else if(object[1].toString().equalsIgnoreCase("Ignore-I"))
					{
						IgnoreDa=object[0].toString();
					}
					
					
				}
			}
			empName = (String)session.get("empName").toString();
				objSer.put("Informed", Informeda);
				objSer.put("NotInformed", NotInformeda);
				objSer.put("Seen",Seena);
				objSer.put("Snooze", Snoozea);
				objSer.put("SnoozeD", SnoozeDa);
				objSer.put("Ignore",Ignorea);
				objSer.put("IgnoreD",IgnoreDa);
				objSer.put("empName",empName);
				
				
				
			}
			
			jsonArr.add(obj);
			jsonArr.add(objSer);
			
			
			//System.out.println("jsonArr  "+jsonArr);
			//System.out.println(jsonArr.size());
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}else{
		return LOGIN;
	}
	}

	
	private String[] getSubModuleRights(String userId)
	{
		String[] value = null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List adminRights = coi.executeAllSelectQuery("select linkVal from useraccount where name = '"+userId+"'", connectionSpace);
			if(adminRights != null && adminRights.size()>0)
			{
				value = adminRights.get(0).toString().split("#");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return value;
	}
	
	public String chkNULL(String value){
		//System.out.println("value value"+value);
		String sts = "NA";
		if(value==null){
			sts = "NA";
		}
		else{
			sts = value;
		}
		return sts;
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

	public String getRefBy()
	{
		return refBy;
	}

	public void setRefBy(String refBy)
	{
		this.refBy = refBy;
	}

	public String getRefTo()
	{
		return refTo;
	}

	public void setRefTo(String refTo)
	{
		this.refTo = refTo;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
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

	public String getRefBySpec()
	{
		return refBySpec;
	}

	public void setRefBySpec(String refBySpec)
	{
		this.refBySpec = refBySpec;
	}

	public String getRefToSpec()
	{
		return refToSpec;
	}

	public void setRefToSpec(String refToSpec)
	{
		this.refToSpec = refToSpec;
	}

	public String getBed()
	{
		return bed;
	}

	public void setBed(String bed)
	{
		this.bed = bed;
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

	public String getPatientName()
	{
		return patientName;
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public String getNursingUnit()
	{
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit)
	{
		this.nursingUnit = nursingUnit;
	}

	public String getSubSpec() {
		return subSpec;
	}

	public void setSubSpec(String subSpec) {
		this.subSpec = subSpec;
	}

	public Map<String, String> getDoctorNameMap() {
		return doctorNameMap;
	}

	public void setDoctorNameMap(Map<String, String> doctorNameMap) {
		this.doctorNameMap = doctorNameMap;
	}

	public Map<String, String> getDoctorIdMap() {
		return doctorIdMap;
	}

	public void setDoctorIdMap(Map<String, String> doctorIdMap) {
		this.doctorIdMap = doctorIdMap;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public JSONArray getJsonArr() {
		return jsonArr;
	}
	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRef_to() {
		return ref_to;
	}
	public void setRef_to(String ref_to) {
		this.ref_to = ref_to;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRefDoc() {
		return refDoc;
	}

	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}

	public String getRefDocTo() {
		return refDocTo;
	}

	public void setRefDocTo(String refDocTo) {
		this.refDocTo = refDocTo;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getLocationWise() {
		return locationWise;
	}

	public void setLocationWise(String locationWise) {
		this.locationWise = locationWise;
	}

	public String getPat_name()
	{
		return pat_name;
	}

	public void setPat_name(String patName)
	{
		pat_name = patName;
	}
	

}
