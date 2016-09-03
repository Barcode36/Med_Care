package com.Over2Cloud.ctrl.order.feedbackaction;

import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.MachineOrder.ClientOrdDataFetch;
import com.Over2Cloud.ctrl.MachineOrder.MachineOrderHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.service.clientdata.integration.ClientDataIntegrationHelper;
import com.Over2Cloud.service.clientdata.integration.ClientDataIntegrationRedirectServer;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
/*
 * This class is for various action taken in machine order Author: Manab
 * 20-06-2015
 */
public class ActionOnMachineOrder extends GridPropertyBean implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private String id;
	private String feedid;
	private String ticket_no;
	private String resolver;
	private String remark;
	private String status;
	private String addrDate;
	private String addrTime;
	private String open_date;
	private String open_time;
	private String feedbackBy;
	private String mobileno;
	private String catg;
	private String subCatg;
	private String feed_brief;
	private String allotto;
	private String location;
	private String reallotedto;
	private String reallotedcomment;
	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;
	private String hpcomment;
	private String ignorecomment;
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	private String sn_reason;
	private String todept;
	private String tosubdept;
	private String reAssignRemark;
	private String feedStatus;
	private String headingTitle;
	private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";
	private String fromDate = "";
	private String toDate = "";
	private String dataFlag = "";
	private String dashFor = "";
	private String d_subdept_id = "";
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String ticketNo = "";
	private String openDate = "";
	private String openTime = "";
	private String emailId = "";
	private String feedBreif = "";
	private String feedId = "";
	private List cycleList;
	private String moduleName;
	private String backData;
	private String cartID;
	private String searchFieldsFlag;
	private String allocateTo, data4;
	private List<Object> masterViewProp = new ArrayList<Object>();
	private String lastOrderDateTime = "NA";
	private String forceFetch = "0";

	// ReAssign variable
	private String feedbackSubCatgory;

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	List<FeedbackPojo> feedbackList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	private Map<Integer, String> resolverList = null;
	private Map<String, String> statusList = null;
	private Map<Integer, String> deptList = null;
	private Map<String, String> dataMap;
	private ArrayList<ArrayList<String>> ordHisList;
	private File approvalDoc;
	private String approvalDocContentType;
	private String approvalDocFileName;
	private String storedDocPath;
	private String approvedBy;
	private String reason;
	private String complaintid;
	private FeedbackPojo fstatus;
	private String resolve_Alert;
	Map<String, String> checkListMap = new LinkedHashMap<String, String>();
	private String orgImgPath;
	private String ignoreremark, hpremark, reopenremark, snoozeremark, reassignreason;
	private Map<String, String> machineSerialList;
	private Map<String, String> assignTechnician4Cart;
	public List uidPatirnt = null;
	private ArrayList<ArrayList<String>> pUHID;
	private ArrayList<ArrayList<String>> pDataCart;
	private ArrayList<ArrayList<String>> cartCount;
	private ArrayList<ArrayList<String>> ordCount;
	private String complaintidAll;
	private JSONArray jsonArr = null;
	private Map<String, String> columnMap;
	String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";
	private HttpServletRequest request;
	private String[] columnNames;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String excelFileName;
	private String toDepart;

	private String time;
	private String nursingId;
	private String machineId;
	private String escFlag;
	private static final Log log = LogFactory.getLog(ClientOrdDataFetch.class);
	CommunicationHelper ch = new CommunicationHelper();

	public String redirectToJSP()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList = new LinkedHashMap<String, String>();
				// getTicketDetails(feedId);
				if (feedStatus.equalsIgnoreCase("Resolved"))
				{
					statusList.put("Re-Opened", "Re-Opened");
				}
				if (feedStatus.equalsIgnoreCase("Pending"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("Parked"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("High Priority"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("Re-Opened"))
				{
					statusList.put("Snooze", "Parked");
					statusList.put("Resolved", "Resolved");
					statusList.put("Ignore", "Ignore");
				} else if (feedStatus.equalsIgnoreCase("Re-Assign"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
				} else if (feedStatus.equalsIgnoreCase("Ignore"))
				{
					statusList.put("Re-Opened", "Re-Opened");
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

	// for redirect the jsp for assign and unassign order take action
	public String redirectToJSPForCPS()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList = new LinkedHashMap<String, String>();
				if (feedStatus.equalsIgnoreCase("Assign"))
				{
					getTicketDetailsForAssignCPS(feedId);
					// getMachineDetailsForAssignCPS(feedId) ;

				} else
				{
					getTicketDetailsForTakeActionCPS(feedId);
				}

				if (feedStatus.equalsIgnoreCase("Resolved"))
				{
					statusList.put("Re-Opened", "Re-Opened");
				}
				if (feedStatus.equalsIgnoreCase("Pending"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("Parked"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("High Priority"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
					// statusList.put("hold", "Seek Approval");
				} else if (feedStatus.equalsIgnoreCase("Re-Opened"))
				{
					statusList.put("Snooze", "Parked");
					statusList.put("Resolved", "Resolved");
					statusList.put("Ignore", "Ignore");
				} else if (feedStatus.equalsIgnoreCase("Re-Assign"))
				{
					statusList.put("Resolved", "Resolved");
					statusList.put("Snooze", "Parked");
					statusList.put("High Priority", "High Priority");
					statusList.put("Ignore", "Ignore");
					statusList.put("Re-Assign", "Re-Assign");
				} else if (feedStatus.equalsIgnoreCase("Ignore"))
				{
					statusList.put("Re-Opened", "Re-Opened");
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

	// Lodge machine Order

	public String actionOnOrderLodge()
	{
		String returnResult = ERROR;

		try
		{
			String deptName = "";
			String mobOne = "";
			String orderid = "";
			String uhid = "";
			String pName = "";
			String roomNo = "";
			String nursingUnit = "";
			String priority = "";
			String orderName = "";
			String orderType = "";
			String subDept = "";

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
			if (requestParameters != null && requestParameters.size() > 0)
			{
				Collections.sort(requestParameters);
				Iterator it = requestParameters.iterator();
				while (it.hasNext())
				{
					String paramName = it.next().toString();
					String paramValue = request.getParameter(paramName);

					// fetch the data of order
					if (paramName.equalsIgnoreCase("feedid"))
					{
						// //System.out.println(paramValue);
						List dataList = cbt.executeAllSelectQuery("SELECT ord.orderid, ord.uhid, ord.pName, ord.roomNo, nu.nursing_unit, ord.priority, ord.orderName, ord.orderType from machine_order_data as ord  LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.id='" + paramValue + "'".toString(), connectionSpace);

						// //System.out.println("dataList.size()>>> " +
						// dataList.size());
						if (dataList != null && dataList.size() > 0)
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								orderid = object[0].toString();
								uhid = object[1].toString();
								pName = object[2].toString();
								roomNo = object[3].toString();
								if (object[4] != null)
								{
									nursingUnit = object[4].toString();
								} else
								{
									nursingUnit = "NA";
								}

								priority = object[5].toString();
								orderName = object[6].toString();
								orderType = object[7].toString();

							}
						}
						dataList.clear();

					}
					;
					if (paramName.equalsIgnoreCase("technician"))
					{
						// //System.out.println(paramValue);
						List dataList = cbt.executeAllSelectQuery("SELECT emp.deptname, emp.mobOne, cc.forDept_id FROM employee_basic as emp inner join compliance_contacts as cc on cc.emp_id = emp.id WHERE emp.empName='" + paramValue + "'".toString(), connectionSpace);

						if (dataList != null && dataList.size() > 0)
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								// machineSerialList.put(object[0].toString(),
								// object[1].toString());
								deptName = object[0].toString();
								mobOne = object[1].toString();
								subDept = object[2].toString();
							}
						}
					} else if (paramName.equalsIgnoreCase("doc"))
					{
						// //System.out.println(paramValue);
						List dataList = cbt.executeAllSelectQuery("SELECT emp.deptname, emp.mobOne, cc.forDept_id FROM employee_basic as emp inner join compliance_contacts as cc on cc.emp_id = emp.id WHERE emp.empName='" + paramValue + "'".toString(), connectionSpace);

						if (dataList != null && dataList.size() > 0)
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								// machineSerialList.put(object[0].toString(),
								// object[1].toString());
								deptName = object[0].toString();
								mobOne = object[1].toString();
								subDept = object[2].toString();
							}
						}
					}

				}

				String escDateTime = escTime(priority, orderName, deptName);
				StringBuilder sb = new StringBuilder();

				sb.append(" update machine_order_data set assignMchn='" + request.getParameter("assignTo") + "', ");
				sb.append(" department='" + subDept + "',");
				sb.append(" assignTech='" + request.getParameter("technicianId") + "',");
				sb.append(" openDate='" + DateUtil.getCurrentDateUSFormat() + "',");
				sb.append(" openTime='" + DateUtil.getCurrentTime() + "',");

				sb.append(" escalation_date='" + escDateTime.split("#")[0] + "',");
				sb.append(" escalation_time='" + escDateTime.split("#")[1] + "',");
				sb.append(" status='Assign',");
				sb.append(" resource='" + request.getParameter("assignTo") + "',");
				sb.append(" brief='" + request.getParameter("brief") + "',");
				sb.append(" level='Level1',");
				sb.append(" regBy='" + (String) session.get("empIdofuser").toString().trim().split("-")[1] + "'");
				sb.append(" where id=" + request.getParameter("feedid") + "");

				List chkAlreadyAssign = cbt.executeAllSelectQuery("SELECT STATUS FROM machine_order_data WHERE id='" + feedid + "' AND STATUS ='Un-assigned'".toString(), connectionSpace);

				String esctm = null;
				if (chkAlreadyAssign != null && chkAlreadyAssign.size() > 0)
				{
					int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);
					sb.setLength(0);
					if (chkUpdate > 0)
					{
						MsgMailCommunication MMC = new MsgMailCommunication();
						MachineOrderHelper moh = new MachineOrderHelper();
						if (mobOne != null && mobOne != "" && mobOne.trim().length() == 10 && !mobOne.startsWith("NA") && mobOne.startsWith("9") || mobOne.startsWith("8") || mobOne.startsWith("7"))
						{
							String levelMsg = "Open: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Priority: " + priority + ", Order No: " + orderid + ", To Be Closed By: " + escDateTime.split("#")[0] + " - " + escDateTime.split("#")[1] + ".";
							MMC.addMessage(moh.empNameGet(request.getParameter("technicianId"), connectionSpace).split("#")[0], moh.empNameGet(request.getParameter("technicianId"), connectionSpace).split("#")[2], mobOne, levelMsg, orderid, "Pending", "0", "ORD");
						}

					}

					Map<String, Object> wherClause = new HashMap<String, Object>();
					// update in history table
					wherClause.put("feedId", request.getParameter("feedid"));
					wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
					wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("action_time", DateUtil.getCurrentTime());
					wherClause.put("allocate_to", request.getParameter("technicianId"));
					wherClause.put("action_reason", request.getParameter("brief"));
					wherClause.put("escalation_level", "NA");
					wherClause.put("status", "Assign");
					wherClause.put("dept", deptName);

					if (wherClause != null && wherClause.size() > 0)
					{
						InsertDataTable ob = new InsertDataTable();
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							ob = new InsertDataTable();
							ob.setColName(entry.getKey());
							ob.setDataName(entry.getValue().toString());
							insertData.add(ob);
						}
						boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
						insertData.clear();
					}

					addActionMessage("Machine Order Assign Successfully !!!");
					returnResult = SUCCESS;

				} else
				{
					addActionMessage("Machine Order is Already Assigned !!!");
					returnResult = SUCCESS;
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;

	}

	// final action on order
	public String actionOnFinal()
	{
		String returnResult = ERROR;
		try
		{

			int i = request.getParameter("feedid").split(",").length;

			if (i <= 1)
			{
				String deptName = "";
				String mobOne = "";
				String orderid = "";
				String uhid = "";
				String pName = "";
				String roomNo = "";
				String nursingUnit = "";
				String priority = "";
				String orderName = "";
				String orderType = "";
				String assignTech = "";
				String escalationdate = "";
				String escalationtime = "";
				try
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameters != null && requestParameters.size() > 0)
					{
						Collections.sort(requestParameters);
						Iterator it = requestParameters.iterator();
						while (it.hasNext())
						{
							String paramName = it.next().toString();
							String paramValue = request.getParameter(paramName);

							// //System.out.println("paramName: " + paramName +
							// "  paramValue: " + paramValue);

							// fetch the data of order
							if (paramName.equalsIgnoreCase("feedid"))
							{
								// //System.out.println(paramValue);
								List dataList = cbt.executeAllSelectQuery("SELECT ord.orderid, ord.uhid, ord.pName, ord.roomNo, nu.nursing_unit, ord.priority, ord.orderName, ord.assignTech, ord.orderType, ord.escalation_date, ord.escalation_time from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit  where ord.id='" + paramValue + "'".toString(), connectionSpace);
								if (dataList != null && dataList.size() > 0)
								{

									for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										orderid = object[0].toString();
										uhid = object[1].toString();
										pName = object[2].toString();
										roomNo = object[3].toString();
										if (object[4] != null)
										{
											nursingUnit = object[4].toString();
										} else
										{
											nursingUnit = "NA";
										}

										priority = object[5].toString();
										orderName = object[6].toString();
										assignTech = object[7].toString();
										orderType = object[8].toString();
										escalationdate = object[9].toString();
										escalationtime = object[10].toString();
									}
								}

							}
							if (assignTech.length() > 1)
							{
								// //System.out.println(paramValue);
								List dataList = cbt.executeAllSelectQuery("SELECT deptname, mobOne FROM employee_basic WHERE id='" + assignTech + "'".toString(), connectionSpace);

								if (dataList != null && dataList.size() > 0)
								{

									for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										deptName = object[0].toString();
										mobOne = object[1].toString();
										// //System.out.println("mobOne " +
										// mobOne);
									}
								}
							}

						}

						String escDateTime = escTime(priority, orderName, deptName);
						StringBuilder sb = new StringBuilder();
						sb.append(" update machine_order_data set status='" + request.getParameter("status") + "', ");
						sb.append(" actionDate='" + DateUtil.getCurrentDateUSFormat() + "',");
						sb.append(" actionTime='" + DateUtil.getCurrentTime() + "',");
						if (orderName.equalsIgnoreCase("CT"))
						{
							sb.append(" finalActionBy='" + request.getParameter("resolveCTBy") + "',");
						} else
						{
							sb.append(" finalActionBy='" + request.getParameter("resolveBy") + "',");
						}

						if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
						{
							String tempTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), escalationdate, escalationtime);
							String parkedTime = request.getParameter("parkedTill");
							sb.append(" parkedTill='" + DateUtil.convertDateToUSFormat(parkedTime.split(" ")[0]) + "',");
							sb.append(" parkedTillTime='" + parkedTime.split(" ")[1] + "',");
							sb.append(" temp_time='" + tempTime + "',");
							sb.append(" escalation_date='" + DateUtil.convertDateToUSFormat(request.getParameter("parkedTill").split(" ")[0]) + "',");
							sb.append(" escalation_time='" + request.getParameter("parkedTill").split(" ")[1] + "',");

						}
						sb.append(" brief='" + request.getParameter("remark") + "'");

						sb.append(" where id =" + request.getParameter("feedid") + "");
						List chkAlreadyAssign = new ArrayList();
						{
						}
						;
						if (!request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
						{
							chkAlreadyAssign = cbt.executeAllSelectQuery("SELECT STATUS FROM machine_order_data WHERE id='" + feedid + "' AND STATUS ='" + request.getParameter("status") + "'".toString(), connectionSpace);
						}

						// //System.out.println("chkAlreadyAssign >>>> " +
						// chkAlreadyAssign.size());

						String esctm = null;
						if (request.getParameter("status").toString().equalsIgnoreCase("Snooze") || chkAlreadyAssign.size() == 0)
						{
							// //System.out.println("chkAlreadyAssign size: " +
							// chkAlreadyAssign.size());
							int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);

							// //System.out.println(chkUpdate);
							// send msg to holder as per status
							if (chkUpdate > 0)
							{

								MsgMailCommunication MMC = new MsgMailCommunication();
								MachineOrderHelper moh = new MachineOrderHelper();

								// //System.out.println("mobOne2 " + mobOne);
								if (mobOne != null && mobOne != "" && mobOne.trim().length() == 10

								&& mobOne.startsWith("9") || mobOne.startsWith("8") || mobOne.startsWith("7") || mobOne.startsWith("6"))
								{
									// //System.out.println("mobOne3 " +
									// mobOne);

									// snooze order sms
									if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
									{

										String levelMsg = "Parked: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Assign To: " + moh.empNameGet(assignTech, connectionSpace).split("#")[0] + "" + ", will be Parked till: " + request.getParameter("parkedTill") + ", Order No: " + orderid + ", Reason: " + request.getParameter("remark");
										MMC.addMessage(moh.empNameGet(assignTech, connectionSpace).split("#")[0], moh.empNameGet(assignTech, connectionSpace).split("#")[2], mobOne, levelMsg, orderid, "Pending", "0", "HDM");
										addActionMessage("Machine Order " + request.getParameter("status") + " Successfully !!!");
										// update in history table
										wherClause.put("feedId", request.getParameter("feedid"));
										wherClause.put("reason", request.getParameter("reason"));
										wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("allocate_to", assignTech);
										wherClause.put("action_reason", request.getParameter("remark"));
										wherClause.put("escalation_level", "NA");
										wherClause.put("status", request.getParameter("status"));
										wherClause.put("dept", deptName);
										wherClause.put("snooze_date", DateUtil.convertDateToUSFormat(request.getParameter("parkedTill").toString().split(" ")[0]));
										wherClause.put("snooze_time", request.getParameter("parkedTill").toString().split(" ")[1]);

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob = new InsertDataTable();
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob = new InsertDataTable();
												ob.setColName(entry.getKey());
												ob.setDataName(entry.getValue().toString());
												insertData.add(ob);
											}
											boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
											if (updateFeed)
											{

												// sb.setLength(0);
												StringBuilder sbUpdate = new StringBuilder();
												sbUpdate.append(" update machine_order_cart set  ");
												sbUpdate.append(" allotFalg='3',");
												sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
												sbUpdate.append(" where orderId='" + request.getParameter("feedid") + "'");
												// sbUpdate.append(" finalActionBy='"
												// +
												// request.getParameter("resolveBy")
												// +
												// "',");

												int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

											}

											insertData.clear();
										}

										returnResult = SUCCESS;

									}

									// Resolve order sms
									else if (request.getParameter("status").toString().equalsIgnoreCase("Resolved"))
									{

										String levelMsg = "Close: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Close By: " + request.getParameter("resolveBy") + ", Order No: " + orderid + "";

										MMC.addMessage(moh.empNameGet(assignTech, connectionSpace).split("#")[0], moh.empNameGet(assignTech, connectionSpace).split("#")[2], mobOne, levelMsg, orderid, "Pending", "0", "HDM");
										addActionMessage("Machine Order " + request.getParameter("status") + " Successfully !!!");

										// update in history table
										wherClause.put("feedId", request.getParameter("feedid"));
										wherClause.put("reason", request.getParameter("reason"));
										wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("allocate_to", assignTech);
										wherClause.put("action_reason", request.getParameter("remark"));
										wherClause.put("escalation_level", "NA");
										wherClause.put("status", request.getParameter("status"));
										wherClause.put("dept", deptName);

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob = new InsertDataTable();
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob = new InsertDataTable();
												ob.setColName(entry.getKey());
												ob.setDataName(entry.getValue().toString());
												insertData.add(ob);
											}
											boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
											if (updateFeed)
											{

												// sb.setLength(0);
												StringBuilder sbUpdate = new StringBuilder();
												sbUpdate.append(" update machine_order_cart set  ");
												sbUpdate.append(" allotFalg='2',");
												sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
												sbUpdate.append(" where orderId='" + request.getParameter("feedid") + "'");
												// sbUpdate.append(" finalActionBy='"
												// +
												// request.getParameter("resolveBy")
												// +
												// "',");

												int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

											}

											insertData.clear();
										}

										returnResult = SUCCESS;

									}

									// Ignore order sms
									else if (request.getParameter("status").toString().equalsIgnoreCase("ignore"))
									{
										String levelMsg = "Ignore: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Assign To: " + moh.empNameGet(assignTech, connectionSpace).split("#")[0] + ", Order No: " + orderid + ", Reason: " + request.getParameter("remark");

										MMC.addMessage(moh.empNameGet(assignTech, connectionSpace).split("#")[0], moh.empNameGet(assignTech, connectionSpace).split("#")[2], mobOne, levelMsg, orderid, "Pending", "0", "HDM");
										addActionMessage("Machine Order " + request.getParameter("status") + " Successfully !!!");

										// update in history table
										wherClause.put("feedId", request.getParameter("feedid"));
										wherClause.put("reason", request.getParameter("reason"));
										wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
										wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
										wherClause.put("action_time", DateUtil.getCurrentTime());
										wherClause.put("allocate_to", assignTech);
										wherClause.put("action_reason", request.getParameter("remark"));
										wherClause.put("escalation_level", "NA");
										wherClause.put("status", request.getParameter("status"));
										wherClause.put("dept", deptName);

										if (wherClause != null && wherClause.size() > 0)
										{
											InsertDataTable ob = new InsertDataTable();
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry<String, Object> entry : wherClause.entrySet())
											{
												ob = new InsertDataTable();
												ob.setColName(entry.getKey());
												ob.setDataName(entry.getValue().toString());
												insertData.add(ob);
											}
											boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
											insertData.clear();
											if (updateFeed)
											{

												// sb.setLength(0);
												StringBuilder sbUpdate = new StringBuilder();
												sbUpdate.append(" update machine_order_cart set  ");
												sbUpdate.append(" allotFalg='4',");
												sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
												sbUpdate.append(" where orderId='" + request.getParameter("feedid") + "'");
												// sbUpdate.append(" finalActionBy='"
												// +
												// request.getParameter("resolveBy")
												// +
												// "',");

												int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

											}
										}

										returnResult = SUCCESS;

									}
								}

							}

						}

						else
						{
							addActionMessage("Machine Order is Already " + request.getParameter("status") + " !!!");
							returnResult = SUCCESS;
						}

					}

				} catch (Exception e)
				{
					e.printStackTrace();
					returnResult = ERROR;
				}
			} else
			{

				String deptName = "";
				String mobOne = "";
				String orderid = "";
				String uhid = "";
				String pName = "";
				String roomNo = "";
				String nursingUnit = "";
				String priority = "";
				String orderName = "";
				String orderType = "";
				String assignTech = "";

				try
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameters != null && requestParameters.size() > 0)
					{
						// //System.out.println(request.getParameter("feedid").split(",").length);
						for (int j = 0; j < request.getParameter("feedid").split(",").length; j++)
						{

							Collections.sort(requestParameters);
							Iterator it = requestParameters.iterator();
							while (it.hasNext())
							{

								String paramName = it.next().toString();
								String paramValue = request.getParameter(paramName);

								// fetch the data of order
								if (paramName.equalsIgnoreCase("feedid"))
								{
									// //System.out.println(paramValue);
									List dataList = cbt.executeAllSelectQuery("SELECT ord.orderid, ord.uhid, ord.pName, ord.roomNo, nu.nursing_unit, ord.priority, ord.orderName, ord.assignTech, ord.orderType  from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.id='" + paramValue.split(",")[j] + "'".toString(), connectionSpace);
									if (dataList != null && dataList.size() > 0)
									{

										for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
										{
											Object[] object = (Object[]) iterator.next();
											orderid = object[0].toString();
											uhid = object[1].toString();
											pName = object[2].toString();
											roomNo = object[3].toString();
											if (object[4] != null)
											{
												nursingUnit = object[4].toString();
											} else
											{
												nursingUnit = "NA";
											}
											priority = object[5].toString();
											orderName = object[6].toString();
											assignTech = object[7].toString();
											orderType = object[8].toString();
										}
									}
									if (assignTech.length() > 1)
									{
										// //System.out.println(paramValue);
										List dataList1 = cbt.executeAllSelectQuery("SELECT deptname, mobOne FROM employee_basic WHERE id='" + assignTech + "'".toString(), connectionSpace);

										if (dataList1 != null && dataList1.size() > 0)
										{

											for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												deptName = object[0].toString();
												mobOne = object[1].toString();
												// //System.out.println("mobOne "
												// + mobOne);
											}
										}
									}
								}

							}

							String escDateTime = escTime(priority, orderName, deptName);
							StringBuilder sb = new StringBuilder();
							sb.append(" update machine_order_data set status='" + request.getParameter("status") + "', ");
							sb.append(" actionDate='" + DateUtil.getCurrentDateUSFormat() + "',");
							sb.append(" actionTime='" + DateUtil.getCurrentTime() + "',");

							if (orderName.equalsIgnoreCase("CT"))
							{
								sb.append(" finalActionBy='" + request.getParameter("resolveCTBy") + "',");
							} else
							{
								sb.append(" finalActionBy='" + request.getParameter("resolveBy") + "',");
							}

							if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
							{
								String parkedTime = request.getParameter("parkedTill");
								sb.append(" parkedTill='" + DateUtil.convertDateToUSFormat(parkedTime.split(" ")[0]) + "',");
								sb.append(" parkedTillTime='" + parkedTime.split(" ")[1] + "',");
								sb.append(" escalation_date='" + DateUtil.convertDateToUSFormat(request.getParameter("parkedTill").split(" ")[0]) + "',");
								sb.append(" escalation_time='" + request.getParameter("parkedTill").split(" ")[1] + "',");

							}
							sb.append(" brief='" + request.getParameter("remark") + "'");

							sb.append(" where id =" + request.getParameter("feedid").split(",")[j] + "");
							List chkAlreadyAssign = new ArrayList();

							if (!request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
							{
								chkAlreadyAssign = cbt.executeAllSelectQuery("SELECT STATUS FROM machine_order_data WHERE id='" + request.getParameter("feedid").split(",")[j] + "' AND STATUS ='" + request.getParameter("status") + "'".toString(), connectionSpace);
							}

							// //System.out.println("chkAlreadyAssign >>>> " +
							// chkAlreadyAssign.size());

							String esctm = null;
							if (request.getParameter("status").toString().equalsIgnoreCase("Snooze") || chkAlreadyAssign.size() == 0)
							{
								// //System.out.println("chkAlreadyAssign size: "
								// +
								// chkAlreadyAssign.size());
								int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);

								// //System.out.println(chkUpdate);
								// send msg to holder as per status
								if (chkUpdate > 0)
								{

									MsgMailCommunication MMC = new MsgMailCommunication();
									MachineOrderHelper moh = new MachineOrderHelper();

									// //System.out.println("mobOne2 " +
									// mobOne);
									if (mobOne != null && mobOne != "" && mobOne.trim().length() == 10

									&& mobOne.startsWith("9") || mobOne.startsWith("8") || mobOne.startsWith("7") || mobOne.startsWith("6"))
									{
										// //System.out.println("mobOne3 " +
										// mobOne);

										// snooze order sms

										// Resolve order sms
										if (request.getParameter("status").toString().equalsIgnoreCase("Resolved"))
										{

											String levelMsg = "Close: Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Close By: " + request.getParameter("resolveBy") + ", Order No: " + orderid + "";

											MMC.addMessage(moh.empNameGet(assignTech, connectionSpace).split("#")[0], moh.empNameGet(assignTech, connectionSpace).split("#")[2], mobOne, levelMsg, orderid, "Pending", "0", "HDM");
											addActionMessage("Machine Order " + request.getParameter("status") + " Successfully !!!");

											// update in history table
											wherClause.put("feedId", request.getParameter("feedid").split(",")[j]);
											wherClause.put("reason", request.getParameter("reason"));
											wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
											wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
											wherClause.put("action_time", DateUtil.getCurrentTime());
											wherClause.put("allocate_to", assignTech);
											wherClause.put("action_reason", request.getParameter("remark"));
											wherClause.put("escalation_level", "NA");
											wherClause.put("status", request.getParameter("status"));
											wherClause.put("dept", deptName);

											if (wherClause != null && wherClause.size() > 0)
											{
												InsertDataTable ob = new InsertDataTable();
												List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
												for (Map.Entry<String, Object> entry : wherClause.entrySet())
												{
													ob = new InsertDataTable();
													ob.setColName(entry.getKey());
													ob.setDataName(entry.getValue().toString());
													insertData.add(ob);
												}
												boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
												if (updateFeed)
												{

													// sb.setLength(0);
													StringBuilder sbUpdate = new StringBuilder();
													sbUpdate.append(" update machine_order_cart set  ");
													sbUpdate.append(" allotFalg='2',");
													sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
													sbUpdate.append(" where orderId='" + request.getParameter("feedid").split(",")[j] + "'");
													new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

												}

												insertData.clear();
											}

										}

									}

								}

							}

							else
							{
								addActionMessage("Machine Order is Already " + request.getParameter("status") + " !!!");
								returnResult = SUCCESS;
							}

						}
						returnResult = SUCCESS;
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					returnResult = ERROR;
				}

			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return returnResult;

	}

	// final action on order
	public String assignbulkorder()
	{

		String deptName = "";
		String mobOne = "";
		String orderid = "";
		String uhid = "";
		String pName = "";
		String roomNo = "";
		String nursingUnit = "";
		String priority = "";
		String orderName = "";
		String assignTech = "";
		String returnResult = ERROR;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
		if (requestParameters != null && requestParameters.size() > 0)
		{
			Collections.sort(requestParameters);
			Iterator it = requestParameters.iterator();
			while (it.hasNext())
			{
				String paramName = it.next().toString();
				String paramValue = request.getParameter(paramName);

				// //System.out.println("paramName: " + paramName +
				// "  paramValue: " + paramValue);

				// fetch the data of order
				if (paramName.equalsIgnoreCase("feedid"))
				{
					// //System.out.println(paramValue);
					List dataList = cbt.executeAllSelectQuery("SELECT ord.orderid, ord.uhid, ord.pName, ord.roomNo, nu.nursing_unit, ord.priority, ord.orderName, ord.assignTech from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.id='" + paramValue + "'".toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{

						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							orderid = object[0].toString();
							uhid = object[1].toString();
							pName = object[2].toString();
							roomNo = object[3].toString();
							if (object[4] != null)
							{
								nursingUnit = object[4].toString();
							} else
							{
								nursingUnit = "NA";
							}
							priority = object[5].toString();
							orderName = object[6].toString();
							assignTech = object[7].toString();
						}
					}

				}
				if (paramName.equalsIgnoreCase("technician"))
				{
					// //System.out.println(paramValue);
					List dataList = cbt.executeAllSelectQuery("SELECT deptname, mobOne FROM employee_basic WHERE id='" + assignTech + "'".toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{

						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							deptName = object[0].toString();
							mobOne = object[1].toString();
						}
					}
				}
			}

			String escDateTime = escTime(priority, orderName, deptName);
			StringBuilder sb = new StringBuilder();
			sb.append(" update machine_order_data set status='" + request.getParameter("status") + "', ");
			sb.append(" actionDate='" + DateUtil.getCurrentDateUSFormat() + "',");
			sb.append(" actionTime='" + DateUtil.getCurrentTime() + "',");

			if (orderName.equalsIgnoreCase("CT"))
			{
				sb.append(" finalActionBy='" + request.getParameter("resolveCTBy") + "',");
			} else
			{
				sb.append(" finalActionBy='" + request.getParameter("resolveBy") + "',");
			}

			if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
			{
				String parkedTime = request.getParameter("parkedTill");
				sb.append(" parkedTill='" + DateUtil.convertDateToUSFormat(parkedTime.split(" ")[0]) + "',");
				sb.append(" parkedTillTime='" + parkedTime.split(" ")[1] + "',");
			}
			sb.append(" brief='" + request.getParameter("remark") + "'");

			sb.append(" where id=" + request.getParameter("feedid") + "");

			List chkAlreadyAssign = cbt.executeAllSelectQuery("SELECT STATUS FROM machine_order_data WHERE id='" + feedid + "' AND STATUS ='Assign'".toString(), connectionSpace);
			String esctm = null;
			if (chkAlreadyAssign != null && chkAlreadyAssign.size() > 0)
			{
				new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);

				addActionMessage("Machine Order " + request.getParameter("status") + " Successfully !!!");
				returnResult = SUCCESS;

			} else
			{
				addActionMessage("Machine Order is Already Assigned !!!");
				returnResult = SUCCESS;
			}

		}
		return returnResult;

	}

	// return escalation date time with # seperated
	private String escTime(String priority, String orderName, String Dept)
	{
		// TODO Auto-generated method stub
		String esctm = "";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder empuery = new StringBuilder();
		empuery.append("SELECT  l1Tol2, escSubDept FROM escalation_order_detail WHERE escDept='" + Dept + "' AND module='ORD' AND priority='" + priority + "'");
		List empData1 = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
		for (Iterator it = empData1.iterator(); it.hasNext();)
		{
			Object[] object = (Object[]) it.next();
			esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object[0].toString(), "Y");
			// System.out.println("esctm >>>>>>  " + esctm);
		}
		return esctm;
	}

	@SuppressWarnings("rawtypes")
	public List getEmp4Escalation(String dept_subDept, String module, String level, SessionFactory connectionSpace)
	{
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{
			query.append("select distinct emp.id, emp.empName from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
			query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
			query.append(" inner join department dept on sub_dept.deptid = dept.id ");
			query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
			query.append(" where roaster.status='Active' and roaster.attendance='Present' and contacts.level='" + level + "' and contacts.work_status='3' and contacts.moduleName='" + module + "'");
			query.append(" and sub_dept.id='" + dept_subDept + "'");

			// //System.out.println("########" + query.toString());

			empList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	public String getReAllotPage()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void deptList()
	{
		try
		{
			deptList = new LinkedHashMap<Integer, String>();
			List departmentlist = new ArrayList();
			List colmName = new ArrayList();
			Map<String, Object> order = new HashMap<String, Object>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			colmName.add("id");
			colmName.add("deptName");
			wherClause.put("orgnztnlevel", orgLevel);
			wherClause.put("mappedOrgnztnId", orgId);
			order.put("deptName", "ASC");

			// Getting Id, Dept Name for Non Service Department
			departmentlist = new HelpdeskUniversalAction().getDataFromTable("department", colmName, wherClause, order, connectionSpace);
			if (departmentlist != null && departmentlist.size() > 0)
			{
				for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer) object[0], object[1].toString());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// get details of the order
	public Map<String, String> getTicketDetailsForAssignCPS(String feedId)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List dataList = null;
			String orderName = "";
			dataMap = new LinkedHashMap<String, String>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT ord.orderName,ord.orderType,ord.orderBy,ord.uhid,ord.pName,ord.roomNo, ord.priority, ord.treatingDoc, nu.nursing_unit, ord.treatingSpec, ord.orderDate, ord.orderTime,ord.orderid  from machine_order_data as ord  ");
			query.append(" LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
			query.append(" WHERE ord.id=" + feedId);
			// //System.out.println("Ticket details for action :::@ " +
			// query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					dataMap.put("Order Name", CUA.getValueWithNullCheck(object[0]) + "(ID: " + CUA.getValueWithNullCheck(object[12]) + ")");
					dataMap.put("Order Type", CUA.getValueWithNullCheck(object[1]));
					dataMap.put("Order By", CUA.getValueWithNullCheck(object[2]));
					dataMap.put("UHID", CUA.getValueWithNullCheck(object[3]));
					dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[4]));
					dataMap.put("Room No", CUA.getValueWithNullCheck(object[5]));
					dataMap.put("Nursing Unit", CUA.getValueWithNullCheck(object[8]));
					dataMap.put("Priority", CUA.getValueWithNullCheck(object[6]));
					dataMap.put("Adm. Doctor", CUA.getValueWithNullCheck(object[7]));
					dataMap.put("Adm. Spec.", CUA.getValueWithNullCheck(object[9]));
					dataMap.put("Order Date", CUA.getValueWithNullCheck(object[10]));
					dataMap.put("Order Time", CUA.getValueWithNullCheck(object[11]));
					orderName = CUA.getValueWithNullCheck(object[0]);

				}
			}

			// get machine name with serial no for X-Ray / Echo
			machineSerialList = new LinkedHashMap<String, String>();
			cartCount = new ArrayList<ArrayList<String>>();
			ordCount = new ArrayList<ArrayList<String>>();
			if (orderName.contains("XR"))
			{

				List dataListRX = cbt.executeAllSelectQuery("SELECT id, serial_No FROM machine_master WHERE machine = 'Xray'  and status='Active' ".toString(), connectionSpace);
				if (dataListRX != null && dataListRX.size() > 0)
				{

					for (Iterator iterator = dataListRX.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						machineSerialList.put(object[0].toString(), object[1].toString());
						cartCount = cartOrderCounterData("XR");
						ordCount = machineOrderCounterData("XR");

					}
				}
			}
			if (orderName.contains("CARD"))
			{
				List dataListEcho = cbt.executeAllSelectQuery("SELECT id, serial_No FROM machine_master WHERE machine = 'Echo' and status='Active' ".toString(), connectionSpace);
				if (dataListEcho != null && dataListEcho.size() > 0)
				{

					for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						machineSerialList.put(object[0].toString(), object[1].toString());
						cartCount = cartOrderCounterData("CARD");
						ordCount = machineOrderCounterData("CARD");
					}
				}

			}

			if (orderName.contains("ULTRA"))
			{
				List dataListEcho = cbt.executeAllSelectQuery("SELECT id, serial_No FROM machine_master WHERE machine = 'ultrasound' and status='Active'  ".toString(), connectionSpace);
				if (dataListEcho != null && dataListEcho.size() > 0)
				{

					for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						machineSerialList.put(object[0].toString(), object[1].toString());
						cartCount = cartOrderCounterData("ULTRA");
						ordCount = machineOrderCounterData("ULTRA");
					}
				}

			}
			if (orderName.contains("CT"))
			{
				List dataListEcho = cbt.executeAllSelectQuery("SELECT id, serial_No FROM machine_master WHERE machine = 'CTscan' and status='Active'  ".toString(), connectionSpace);
				if (dataListEcho != null && dataListEcho.size() > 0)
				{

					for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						machineSerialList.put(object[0].toString(), object[1].toString());
						cartCount = cartOrderCounterData("CT");
						ordCount = machineOrderCounterData("CT");
					}
				}

			}

			if (orderName.contains("ECHO"))
			{
				List dataListEcho = cbt.executeAllSelectQuery("SELECT id, serial_No FROM machine_master WHERE machine = 'Echo' and status='Active'  ".toString(), connectionSpace);
				if (dataListEcho != null && dataListEcho.size() > 0)
				{

					for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						machineSerialList.put(object[0].toString(), object[1].toString());
						cartCount = cartOrderCounterData("ECHO");
						ordCount = machineOrderCounterData("ECHO");
					}
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataMap;
	}

	private ArrayList<ArrayList<String>> machineOrderCounterData(String ordType)
	{
		// TODO Auto-generated method stub Dr Dhruv

		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qry = new StringBuilder("");
		qry.append("select count(ord.id) , emp.empName, ord.orderName,mm.machine, mm.serial_No from machine_order_data as ord ");
		qry.append(" inner join employee_basic as emp on emp.id =  assignTech ");
		qry.append(" inner join machine_master as mm on  ord.assignMchn= mm.id ");
		qry.append(" where  ord.orderName='" + ordType + "'    and ord.status = 'Assign' and ord.openDate = '" + DateUtil.getCurrentDateUSFormat() + "' ");
		qry.append(" group by ord.assignMchn order by count(ord.id) ");
		List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		ArrayList<String> tempList = null;
		ArrayList<String> tempList1 = null;
		ArrayList<ArrayList<String>> machineCount = new ArrayList<ArrayList<String>>();
		if (dataList != null && dataList.size() > 0)
		{

			for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
			{
				Object[] object = (Object[]) iterator1.next();

				// make list for uhid and room no
				tempList1 = new ArrayList<String>();

				tempList1.add(CUA.getValueWithNullCheck(object[0]));
				tempList1.add(CUA.getValueWithNullCheck(object[1]));
				tempList1.add(CUA.getValueWithNullCheck(object[2]));
				tempList1.add(CUA.getValueWithNullCheck(object[3]) + " - " + CUA.getValueWithNullCheck(object[4]));

				machineCount.add(tempList1);

			}
		}
		return machineCount;
	}

	private ArrayList<ArrayList<String>> cartOrderCounterData(String ordType)
	{
		// TODO Auto-generated method stub
		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qry = new StringBuilder("");
		qry.append("select count(ord.id) , emp.empName, ord.orderName,mm.machine, mm.serial_No from machine_order_data as ord ");
		qry.append(" inner join employee_basic as emp on emp.id =  assignTech ");
		qry.append(" inner join machine_master as mm on  ord.assignMchn= mm.id ");
		qry.append(" where  ord.orderName='" + ordType + "' and ord.status like '%Assign-cartItem%' and ord.openDate = '" + DateUtil.getCurrentDateUSFormat() + "' ");
		qry.append(" group by ord.assignMchn order by count(ord.id) ");
		List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		ArrayList<String> tempList = null;
		ArrayList<String> tempList1 = null;
		ArrayList<ArrayList<String>> cartCount = new ArrayList<ArrayList<String>>();
		if (dataList != null && dataList.size() > 0)
		{

			for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
			{
				Object[] object = (Object[]) iterator1.next();

				// make list for uhid and room no
				tempList1 = new ArrayList<String>();

				tempList1.add(CUA.getValueWithNullCheck(object[0]));
				tempList1.add(CUA.getValueWithNullCheck(object[1]));
				tempList1.add(CUA.getValueWithNullCheck(object[2]));
				tempList1.add(CUA.getValueWithNullCheck(object[3]) + " - " + CUA.getValueWithNullCheck(object[4]));

				cartCount.add(tempList1);

			}
		}
		return cartCount;
	}

	// get order detail for assign order
	public Map<String, String> getTicketDetailsForTakeActionCPS(String feedId)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			if (feedId.split(",").length <= 1)
			{
				List dataList = null;
				// StringBuilder NextEscalationTo = new StringBuilder();
				dataMap = new LinkedHashMap<String, String>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query.append("SELECT ORD.orderName,ORD.orderType,ORD.orderBy,ORD.uhid,ORD.pName,ORD.roomNo,emp.empName,mac.machine, mac.serial_No,ORD.brief,ORD.openDate,ORD.openTime, ORD.priority, ORD.treatingDoc, nu.nursing_unit, ORD.treatingSpec  FROM machine_order_data AS ORD ");
				query.append(" LEFT JOIN department AS dept ON dept.id=ord.department");
				query.append(" LEFT JOIN employee_basic AS emp ON emp.id=ord.assignTech");
				query.append(" LEFT JOIN machine_master AS mac ON mac.id=ord.assignMchn");
				query.append(" LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
				query.append(" WHERE ORD.id=" + feedId);
				// //System.out.println("Ticket details for action :::@ " +
				// query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{

					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{

						Object[] object = (Object[]) iterator.next();
						dataMap.put("Order Name", CUA.getValueWithNullCheck(object[0]));
						dataMap.put("Order Type", CUA.getValueWithNullCheck(object[1]));
						dataMap.put("Order By", CUA.getValueWithNullCheck(object[2]));
						dataMap.put("UHID", CUA.getValueWithNullCheck(object[3]));
						dataMap.put("Patient Name", CUA.getValueWithNullCheck(object[4]));
						dataMap.put("Room No", CUA.getValueWithNullCheck(object[5]));
						dataMap.put("Assign To", CUA.getValueWithNullCheck(object[6]));
						dataMap.put("Resource", CUA.getValueWithNullCheck(object[7]) + "-" + CUA.getValueWithNullCheck(object[8]));
						dataMap.put("Brief", CUA.getValueWithNullCheck(object[9]));
						dataMap.put("Assigned At", CUA.getValueWithNullCheck(object[10]) + "  " + CUA.getValueWithNullCheck(object[11]));
						dataMap.put("Nursing Unit", CUA.getValueWithNullCheck(object[14]));
						dataMap.put("Priority", CUA.getValueWithNullCheck(object[12]));
						dataMap.put("Adm. Doctor", CUA.getValueWithNullCheck(object[13]));
						dataMap.put("Adm. Spec.", CUA.getValueWithNullCheck(object[15]));

					}
				}

			}
			// fetchHistoryDetails();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataMap;
	}

	// get manual assign employee by roster for manual cart assign

	@SuppressWarnings("unchecked")
	public String ManualCartAssign()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				toDate = DateUtil.getCurrentDateIndianFormat();
				assignTechnician4Cart = new HashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder qry = new StringBuilder("");
				qry.append("SELECT emp.id, emp.empName, subdept.subdeptname FROM employee_basic AS emp ");
				qry.append(" INNER JOIN emp_machine_mapping AS ewm ON ewm.empName=emp.id ");
				qry.append(" INNER JOIN shift_with_emp_wing AS swew ON ewm.shiftId = swew.id ");
				qry.append(" INNER JOIN `compliance_contacts` AS cc ON cc.`emp_id`=emp.`id` ");
				qry.append(" INNER JOIN  subdepartment AS subdept ON subdept.id=cc.`forDept_id` ");
				qry.append(" WHERE ");
				if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("XR"))
				{
					qry.append(" dept_id = (select id from department where deptname like '%Xray%') AND ");
				} else if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("ULTRA"))
				{
					qry.append(" dept_id = (select id from department where deptname like '%ultrasound%') AND ");
				}

				qry.append("  swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >'" + DateUtil.getCurrentTime() + "' and cc.level='1' ");
				// //System.out.println(qry);
				List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							assignTechnician4Cart.put(object[0].toString(), object[1].toString());
						}

					}
				}

				returnResult = SUCCESS;

			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// assign cart oder to technician 07-07-2015 by:Manab

	public String assignbulkorderCart()
	{
		String deptName = "";
		String mobOne = "";
		String empName = "";
		String orderid = "";
		String uhid = "";
		String pName = "";
		String roomNo = "";
		String nursingUnit = "";
		String priority = "";
		String orderName = "";
		String assignTech = "";
		String resourse = "";
		String returnResult = ERROR;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
			Map<String, Object> wherClause = new HashMap<String, Object>();
			if (requestParameters != null && requestParameters.size() > 0)
			{
				String timeStamp = DateUtil.getCurrentTimeStamp();
				Collections.sort(requestParameters);
				Iterator it = requestParameters.iterator();

				StringBuilder qry = new StringBuilder("");

				qry.append("SELECT cc.forDept_id, emp.mobOne, ewm.machine,emp.empName FROM emp_machine_mapping AS  ewm  ");
				qry.append(" INNER JOIN employee_basic AS emp ON emp.id= ewm.empName ");
				qry.append(" INNER JOIN shift_with_emp_wing AS swew ON ewm.shiftId=swew.id ");
				qry.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=ewm.empName ");
				qry.append(" WHERE ewm.empName ='" + request.getParameter("assignToMan") + "' ");
				qry.append(" AND swew.fromShift<='" + DateUtil.getCurrentTime() + "' AND swew.toShift >'" + DateUtil.getCurrentTime() + "'");

				List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptName = object[0].toString();
						mobOne = object[1].toString();
						resourse = object[2].toString();
						empName = object[3].toString();
					}
				}

				// for send sms about cart to technician and update in db about
				// the
				// cart allocation
				String[] ordIdCart = request.getParameter("complaintid").toString().split(",");
				String[] ordUIdCart = null;
				for (int i = 0; i < ordIdCart.length; i++)
				{
					ordUIdCart = ordIdCart[i].split("kk");

					// String esctm =
					// DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(),
					// DateUtil.getCurrentTime(),
					// DateUtil.findDifferenceTwoTime(DateUtil.getCurrentTimeHourMin(),
					// request.getParameter("tatCart").toString().split(" ")[1]).toString(),
					// "Y");
					// String esctm
					// =DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
					// DateUtil.getCurrentTime(),
					// DateUtil.convertDateToUSFormat(request.getParameter("tatCart").toString().split(" ")[0]),
					// request.getParameter("tatCart").toString().split(" ")[1]);
					StringBuilder sb = new StringBuilder();
					sb.append(" update machine_order_data set assignMchn='" + resourse + "', ");
					sb.append(" department='" + deptName + "',");
					sb.append(" assignTech='" + request.getParameter("assignToMan") + "',");
					sb.append(" openDate='" + DateUtil.getCurrentDateUSFormat() + "',");
					sb.append(" openTime='" + DateUtil.getCurrentTime() + "',");

					sb.append(" escalation_date='" + DateUtil.convertDateToUSFormat(request.getParameter("tatCart1")) + "',");
					sb.append(" escalation_time='" + request.getParameter("tatCart").toString() + "',");
					sb.append(" status='Assign-" + ordUIdCart[0].toString() + ":" + timeStamp + "',");
					sb.append(" cart_name='Assign-" + ordUIdCart[0].toString() + ":" + timeStamp + "',");
					sb.append(" resource='" + resourse + "',");
					sb.append(" brief='" + request.getParameter("brief") + "',");
					sb.append(" level='Level1',");
					sb.append(" regBy='" + (String) session.get("empIdofuser").toString().trim().split("-")[1] + "'");
					sb.append(" where id=" + ordUIdCart[1].toString() + "");
					// //System.out.println("sb " + sb);
					int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);

					// delete from cart table
					if (chkUpdate > 0)
					{

						StringBuilder query = new StringBuilder();
						query.append(" update  machine_order_cart set allotFalg='1' where orderId='" + ordUIdCart[1].toString() + "'");
						int chkUp = cbt.executeAllUpdateQuery(query.toString(), connectionSpace);

						if (chkUp > 0)
						{
							wherClause.put("feedId", ordUIdCart[1]);
							wherClause.put("action_by", (String) session.get("empIdofuser").toString().trim().split("-")[1]);
							wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("action_time", DateUtil.getCurrentTime());
							wherClause.put("allocate_to", request.getParameter("assignToMan"));
							wherClause.put("action_reason", "Cart Allot");
							wherClause.put("escalation_level", "NA");
							wherClause.put("status", "Cart Assigned");
							wherClause.put("dept", deptName);

							if (wherClause != null && wherClause.size() > 0)
							{
								InsertDataTable ob = new InsertDataTable();
								List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									ob = new InsertDataTable();
									ob.setColName(entry.getKey());
									ob.setDataName(entry.getValue().toString());
									insertData.add(ob);
								}
								boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
								insertData.clear();

							}

						}

					}

				}
				CommunicationHelper CH = new CommunicationHelper();
				CH.addMessage(empName, "BULK CART", mobOne, "Cart Name: Assign-" + ordUIdCart[0].toString() + ":" + timeStamp + " Number Of Order: " + ordIdCart.length + " Due Date/Time: " + request.getParameter("tatCart1").toString() + "/" + request.getParameter("tatCart").toString(), "", "Pending", "0", "ORD", connectionSpace);

				addActionMessage(ordIdCart.length + " orders of Cart Assign Successfully !!!");
				returnResult = SUCCESS;
			}
		} catch (Exception e)
		{
			e.printStackTrace();

		}
		return returnResult;
	}

	// for redirect print cart page

	public String printCartPage()
	{
		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String ordids = "";
		if ("gridPrint".equalsIgnoreCase(dashFor))
		{
			ordids = request.getParameter("complaintid").toString();
			if ((ordids.lastIndexOf(",") == ordids.length() - 1))
			{
				ordids = ordids.substring(0, (ordids.length() - 1));
			}

		} else
		{
			String[] ordIdCart = request.getParameter("complaintid").toString().split(",");
			for (int i = 0; i < ordIdCart.length; i++)
			{
				String[] ordUIdCart = ordIdCart[i].split("kk");
				if (ordids.length() > 1)
				{
					ordids += ", " + ordUIdCart[1].toString();
				} else
				{

					ordids = ordUIdCart[1].toString();
				}
			}
		}

		uidPatirnt = new ArrayList();
		StringBuilder qry = new StringBuilder("");
		qry.append("SELECT ord.orderid, ord.orderName, ord.orderType, ord.uhid, ord.pName,  ord.priority, nu.nursing_unit, ord.regBy,");
		qry.append(" ord.roomNo, emp.empName, ord.openDate, ord.openTime, ord.escalation_date, ord.escalation_time ");
		qry.append(" FROM machine_order_data as ord");
		qry.append(" LEFT JOIN employee_basic AS emp ON emp.id=ord.assignTech");
		qry.append(" LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
		qry.append(" WHERE ord.id IN (" + ordids + ") ORDER BY ord.nursingUnit");

		List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		ArrayList<String> tempList = null;
		ArrayList<String> tempList1 = null;
		pUHID = new ArrayList<ArrayList<String>>();
		if (dataList != null && dataList.size() > 0)
		{

			for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
			{
				Object[] object = (Object[]) iterator1.next();

				// make list for uhid and room no
				tempList1 = new ArrayList<String>();

				tempList1.add(CUA.getValueWithNullCheck(object[0]));
				tempList1.add(CUA.getValueWithNullCheck(object[3]));
				tempList1.add(CUA.getValueWithNullCheck(object[8]));
				tempList1.add(CUA.getValueWithNullCheck(object[6]));
				pUHID.add(tempList1);

			}

			pDataCart = new ArrayList<ArrayList<String>>();
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();

				tempList = new ArrayList<String>();
				tempList.add(CUA.getValueWithNullCheck(object[0]));
				tempList.add(CUA.getValueWithNullCheck(object[1]));
				tempList.add(CUA.getValueWithNullCheck(object[2]));
				tempList.add(CUA.getValueWithNullCheck(object[3]));
				tempList.add(CUA.getValueWithNullCheck(object[4]));
				tempList.add(CUA.getValueWithNullCheck(object[5]));
				tempList.add(CUA.getValueWithNullCheck(object[6]));
				tempList.add(CUA.getValueWithNullCheck(object[7]));
				tempList.add(CUA.getValueWithNullCheck(object[8]));
				tempList.add(CUA.getValueWithNullCheck(object[9]));
				tempList.add(CUA.getValueWithNullCheck(object[10]));
				tempList.add(CUA.getValueWithNullCheck(object[11]));
				tempList.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[12])));
				tempList.add(CUA.getValueWithNullCheck(object[13]));

				pDataCart.add(tempList);

			}

		}
		return SUCCESS;
	}

	private String calculateTempTime(String feedid)
	{
		// TODO Auto-generated method stub

		String diffCal = "00:00";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			// System.out.println("feedid >> " + feedid);
			List dataListEcho = cbt.executeAllSelectQuery("select  action_date, action_time, status from order_status_history where feedId='" + feedid + "'  order by id desc limit 1".toString(), connectionSpace);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			if (dataListEcho != null && dataListEcho.size() > 0)
			{

				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[2].toString().equalsIgnoreCase("Assign") || object[2].toString().equalsIgnoreCase("Parked Activate") || object[2].toString().equalsIgnoreCase("Escalate"))
					{

						diffCal = DateUtil.time_difference(object[0].toString(), object[1].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin());
					}
					// System.out.println("diffCal>>> " + diffCal);
					// machineSerialList.put(object[0].toString(),
					// object[1].toString());
					// empName =
					// object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString();
				}
			}

		} catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}

		return diffCal;

	}

	// direct cart view page
	public String beforCartDetailsView()
	{

		return SUCCESS;
	}

	// show details cart header

	public String detailsCartPageHeader()
	{

		// //System.out.println("got the call" + cartID + "      " +
		// allocateTo);
		searchFieldsFlag = allocateTo;
		// //System.out.println("searchFieldsFlag>>>>>>  " + searchFieldsFlag);
		// TODO Auto-generated method stub
		// String actiontaken = ERROR;
		/*
		 * boolean sessionFlag = ValidateSession.checkSession(); if
		 * (sessionFlag) {
		 */
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderid");
			gpv.setHeadingName("Order Id");
			gpv.setHideOrShow("false");
			gpv.setWidth(300);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("order");
			gpv.setHeadingName("Order");
			gpv.setHideOrShow("false");
			gpv.setWidth(180);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderType");
			gpv.setHeadingName("Order Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(180);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("uhid");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("pName");
			gpv.setHeadingName("Patient Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(200);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("roomNo");
			gpv.setHeadingName("Room No");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursingUnit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderAt");
			gpv.setHeadingName("Order At");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignMchn");
			gpv.setHeadingName("Assigned Machine");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignTech");
			gpv.setHeadingName("Assigned Technician");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			// //System.out.println("viewPageColumns " +
			// viewPageColumns.size());
			return SUCCESS;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
	} /*
	 * else { return ERROR; }
	 */

	// return actiontaken;

	// show cart details

	public String detailsCartPageData()
	{

		// //System.out.println("searchFieldsFlag>>>>>>  " + searchFieldsFlag);

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from machine_order_data", connectionSpace);
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
				List<Object> tempList = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				query.append("select ord.id, ord.orderid, ord.orderName, ord.orderType, ord.uhid, ord.pName, ord.roomNo, ord.priority, nu.nursing_unit, ord.openDate, ord.openTime, ord.assignMchn, ord.assignTech ");
				query.append(" from  machine_order_data as ord ");
				query.append(" inner JOIN machine_order_cart as cart on ord.id= cart.orderId");
				query.append(" LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
				query.append(" where cart.cartId ='" + cartID + "' and cart.allotFalg='" + searchFieldsFlag + "' and openDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");

				// //System.out.println("QQQQ Query>>>>>>>>>>>>>>>  " + query);
				List dataList = null;
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{

					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						Object[] object = (Object[]) iterator.next();

						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("orderid", getValueWithNullCheck(object[1]));
						tempMap.put("order", getValueWithNullCheck(object[2]));
						tempMap.put("orderType", getValueWithNullCheck(object[3]));
						tempMap.put("uhid", getValueWithNullCheck(object[4]));
						tempMap.put("pName", getValueWithNullCheck(object[5]));
						tempMap.put("roomNo", getValueWithNullCheck(object[6]));
						tempMap.put("orderAt", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[9].toString())) + " " + getValueWithNullCheck(object[10]));
						tempMap.put("assignMchn", getValueWithNullCheck(object[11]));
						tempMap.put("assignTech", getValueWithNullCheck(object[12]));
						tempMap.put("nursingUnit", getValueWithNullCheck(object[8]));
						tempMap.put("priority", getValueWithNullCheck(object[7]));
						tempList.add(tempMap);

					}

				}

				setMasterViewProp(tempList);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				returnResult = SUCCESS;

			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String bulkCartTakeAtcion()
	{

		// //System.out.println(complaintidAll);
		return SUCCESS;

	}

	public String actionOnCartFinal()
	{

		// //System.out.println("going to take final action " + complaintidAll);
		String deptName = "";
		String mobOne = "";
		String orderid = "";
		String uhid = "";
		String pName = "";
		String roomNo = "";
		String nursingUnit = "";
		String priority = "";
		String orderName = "";
		String orderType = "";
		String assignTech = "";
		String returnResult = ERROR;

		String[] ordUIdCart = complaintidAll.split(",");
		for (int i = 0; i < ordUIdCart.length; i++)
		{
			String ordId = ordUIdCart[i];
			// //System.out.println(ordId);

			Map<String, Object> wherClause = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> requestParameters = Collections.list((Enumeration<String>) request.getParameterNames());
			if (requestParameters != null && requestParameters.size() > 0)
			{
				Collections.sort(requestParameters);
				Iterator it = requestParameters.iterator();
				while (it.hasNext())
				{
					String paramName = it.next().toString();
					String paramValue = request.getParameter(paramName);

					// //System.out.println("paramName: " + paramName +
					// "  paramValue: " + paramValue);

					// fetch the data of order
					if (!(ordId.length() < 1))
					{
						// //System.out.println(paramValue);
						List dataList = cbt.executeAllSelectQuery("SELECT ord.orderid, ord.uhid, ord.pName, ord.roomNo, nu.nursing_unit, ord.priority, ord.orderName, ord.assignTech, ord.orderType from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.id='" + ordId + "'".toString(), connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								orderid = object[0].toString();
								uhid = object[1].toString();
								pName = object[2].toString();
								roomNo = object[3].toString();
								if (object[4] != null)
								{
									nursingUnit = object[4].toString();
								} else
								{
									nursingUnit = "NA";
								}

								priority = object[5].toString();
								orderName = object[6].toString();
								assignTech = object[7].toString();
								orderType = object[8].toString();
							}
						}

					}
					if (assignTech.length() > 1)
					{
						// //System.out.println(paramValue);
						List dataList = cbt.executeAllSelectQuery("SELECT deptname, mobOne FROM employee_basic WHERE id='" + assignTech + "'".toString(), connectionSpace);

						if (dataList != null && dataList.size() > 0)
						{

							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								deptName = object[0].toString();
								mobOne = object[1].toString();
								// //System.out.println("mobOne " + mobOne);
							}
						}
					}

				}

				String escDateTime = escTime(priority, orderName, deptName);
				StringBuilder sb = new StringBuilder();
				sb.append(" update machine_order_data set status='" + request.getParameter("status") + "', ");
				sb.append(" actionDate='" + DateUtil.getCurrentDateUSFormat() + "',");
				sb.append(" actionTime='" + DateUtil.getCurrentTime() + "',");
				if (orderName.equalsIgnoreCase("CT"))
				{
					sb.append(" finalActionBy='" + request.getParameter("resolveCTBy") + "',");
				} else
				{
					sb.append(" finalActionBy='" + request.getParameter("resolveBy") + "',");
				}

				if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
				{
					sb.append(" parkedTill='" + request.getParameter("parkedTill") + "',");
					// sb.append(" escalation_date='" +
					// DateUtil.convertDateToUSFormat(request.getParameter("parkedTill").split(" ")[0])
					// + "',");
					// sb.append(" escalation_time='" +
					// request.getParameter("parkedTill").split(" ")[1] + "',");

				}
				sb.append(" brief='" + request.getParameter("remark") + "'");

				sb.append(" where id=" + ordId + "");

				List chkAlreadyAssign = cbt.executeAllSelectQuery("SELECT STATUS FROM machine_order_data WHERE id='" + ordId + "' AND STATUS ='" + request.getParameter("status") + "'".toString(), connectionSpace);
				// //System.out.println("chkAlreadyAssign >>>> " +
				// chkAlreadyAssign.size());
				String esctm = null;
				if (chkAlreadyAssign.size() == 0)
				{
					// //System.out.println("chkAlreadyAssign size: " +
					// chkAlreadyAssign.size());
					int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connectionSpace);

					// //System.out.println(chkUpdate);
					// send msg to holder as per status
					if (chkUpdate > 0)
					{

						MsgMailCommunication MMC = new MsgMailCommunication();
						MachineOrderHelper moh = new MachineOrderHelper();

						// //System.out.println("mobOne2 " + mobOne);
						if (mobOne != null && mobOne != "" && mobOne.trim().length() == 10

						&& mobOne.startsWith("9") || mobOne.startsWith("8") || mobOne.startsWith("7"))
						{
							// //System.out.println("mobOne3 " + mobOne);

							// snooze order sms
							if (request.getParameter("status").toString().equalsIgnoreCase("Snooze"))
							{

								String levelMsg = "Parked Order Alert: Order No: " + orderid + ", Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Assign To: " + moh.empNameGet(assignTech, connectionSpace).split("#")[0] + "" + ", will be Parked till: " + request.getParameter("parkedTill") + ", Reason: " + request.getParameter("remark");
								// MMC.addMessage(moh.empNameGet(assignTech,
								// connectionSpace).split("#")[0],
								// moh.empNameGet(assignTech,
								// connectionSpace).split("#")[2], mobOne,
								// levelMsg, orderid, "Pending", "0", "HDM");
								// addActionMessage("Machine Order " +
								// request.getParameter("status") +
								// " Successfully !!!");
								// update in history table
								wherClause.put("feedId", ordId);
								wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
								wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
								wherClause.put("action_time", DateUtil.getCurrentTime());
								wherClause.put("allocate_to", assignTech);
								wherClause.put("action_reason", request.getParameter("remark"));
								wherClause.put("escalation_level", "NA");
								wherClause.put("status", request.getParameter("status") + " by cart");
								wherClause.put("dept", deptName);

								if (wherClause != null && wherClause.size() > 0)
								{
									InsertDataTable ob = new InsertDataTable();
									List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
									for (Map.Entry<String, Object> entry : wherClause.entrySet())
									{
										ob = new InsertDataTable();
										ob.setColName(entry.getKey());
										ob.setDataName(entry.getValue().toString());
										insertData.add(ob);
									}
									boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
									insertData.clear();
									if (updateFeed)
									{
										// sb.setLength(0);
										StringBuilder sbUpdate = new StringBuilder();
										sbUpdate.append(" update machine_order_cart set  ");
										sbUpdate.append(" allotFalg='3',");
										sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
										sbUpdate.append(" where orderId='" + ordId + "'");
										// sbUpdate.append(" finalActionBy='" +
										// request.getParameter("resolveBy") +
										// "',");

										int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

									}
								}

								returnResult = SUCCESS;

							}

							// Resolve order sms
							else if (request.getParameter("status").toString().equalsIgnoreCase("Resolved"))
							{

								String levelMsg = "Close Order Alert: Order No: " + orderid + ", Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Assign To: " + moh.empNameGet(assignTech, connectionSpace).split("#")[0] + "";

								// MMC.addMessage(moh.empNameGet(assignTech,
								// connectionSpace).split("#")[0],
								// moh.empNameGet(assignTech,
								// connectionSpace).split("#")[2], mobOne,
								// levelMsg, orderid, "Pending", "0", "HDM");
								// addActionMessage("Machine Order " +
								// request.getParameter("status") +
								// " Successfully !!!");

								// update in history table
								wherClause.put("feedId", ordId);
								wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
								wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
								wherClause.put("action_time", DateUtil.getCurrentTime());
								wherClause.put("allocate_to", assignTech);
								wherClause.put("action_reason", request.getParameter("remark"));
								wherClause.put("escalation_level", "NA");
								wherClause.put("status", request.getParameter("status") + " by cart");
								wherClause.put("dept", deptName);

								if (wherClause != null && wherClause.size() > 0)
								{
									InsertDataTable ob = new InsertDataTable();
									List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
									for (Map.Entry<String, Object> entry : wherClause.entrySet())
									{
										ob = new InsertDataTable();
										ob.setColName(entry.getKey());
										ob.setDataName(entry.getValue().toString());
										insertData.add(ob);
									}
									boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
									insertData.clear();
									if (updateFeed)
									{
										// sb.setLength(0);
										StringBuilder sbUpdate = new StringBuilder();
										sbUpdate.append(" update machine_order_cart set  ");
										sbUpdate.append(" allotFalg='2',");
										sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
										sbUpdate.append(" where orderId='" + ordId + "'");
										// sbUpdate.append(" finalActionBy='" +
										// request.getParameter("resolveBy") +
										// "',");

										int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

									}
								}

								returnResult = SUCCESS;

							}

							// Ignore order sms
							else if (request.getParameter("status").toString().equalsIgnoreCase("ignore"))
							{

								String levelMsg = "Ignore Order Alert: Order No: " + orderid + ", Order: " + orderName + " - " + orderType + ", Location: " + nursingUnit + "/ " + roomNo + "/ " + uhid + ", Assign To: " + moh.empNameGet(assignTech, connectionSpace).split("#")[0] + "" + ", Reason: " + request.getParameter("remark");

								// MMC.addMessage(moh.empNameGet(assignTech,
								// connectionSpace).split("#")[0],
								// moh.empNameGet(assignTech,
								// connectionSpace).split("#")[2], mobOne,
								// levelMsg, orderid, "Pending", "0", "HDM");
								// addActionMessage("Machine Order " +
								// request.getParameter("status") +
								// " Successfully !!!");

								// update in history table
								wherClause.put("feedId", ordId);
								wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
								wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
								wherClause.put("action_time", DateUtil.getCurrentTime());
								wherClause.put("allocate_to", assignTech);
								wherClause.put("action_reason", request.getParameter("remark"));
								wherClause.put("escalation_level", "NA");
								wherClause.put("status", request.getParameter("status") + " by cart");
								wherClause.put("dept", deptName);

								if (wherClause != null && wherClause.size() > 0)
								{
									InsertDataTable ob = new InsertDataTable();
									List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
									for (Map.Entry<String, Object> entry : wherClause.entrySet())
									{
										ob = new InsertDataTable();
										ob.setColName(entry.getKey());
										ob.setDataName(entry.getValue().toString());
										insertData.add(ob);
									}
									boolean updateFeed = cbt.insertIntoTable("order_status_history", insertData, connectionSpace);
									insertData.clear();
									if (updateFeed)
									{
										// sb.setLength(0);
										StringBuilder sbUpdate = new StringBuilder();
										sbUpdate.append(" update machine_order_cart set  ");
										sbUpdate.append(" allotFalg='4',");
										sbUpdate.append(" datetime='" + DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime() + "'");
										sbUpdate.append(" where orderId='" + ordId + "'");
										// sbUpdate.append(" finalActionBy='" +
										// request.getParameter("resolveBy") +
										// "',");

										int chkUpdateCart = new createTable().executeAllUpdateQuery(sbUpdate.toString(), connectionSpace);

									}
								}

								returnResult = SUCCESS;

							}
						}

					}

				}

				else
				{
					addActionMessage("Machine Order is Already " + request.getParameter("status") + " !!!");
					returnResult = SUCCESS;
				}

			}
		}
		return returnResult;

	}

	public String getCounterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				StringBuilder qry = new StringBuilder();
				qry.append("select count(*),status from machine_order_data where orderDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "' and orderName='" + moduleName + "' GROUP by status");
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					String assinged = "", unaasined = "", ignore = "", park = "", HIScan = "", resolve = "", cart1UAss = "", cart2UAss = "", cart3UAss = "";
					int cart1Ass = 0, cart2Ass = 0, cart3Ass = 0;
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							if (object[1].toString().equalsIgnoreCase("Resolved"))
							{
								resolve = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Un-assigned"))
							{
								unaasined = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Assign"))
							{
								assinged = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Unassigned Cart-1"))
							{
								cart1UAss = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Unassigned Cart-2"))
							{
								cart2UAss = object[0].toString();
							} else if (object[1].toString().equalsIgnoreCase("Unassigned Cart-3"))
							{
								cart3UAss = object[0].toString();
							} else if (object[1].toString().contains("Assign-cartItem1"))
							{
								cart1Ass += Integer.parseInt(object[0].toString());
							} else if (object[1].toString().contains("Assign-cartItem2"))
							{
								cart2Ass += Integer.parseInt(object[0].toString());
							} else if (object[1].toString().contains("Assign-cartItem3"))
							{
								cart3Ass += Integer.parseInt(object[0].toString());
							} else if (object[1].toString().equalsIgnoreCase("Snooze"))
							{
								park = object[0].toString();
							} else if (object[1].toString().contains("Ignore"))
							{
								ignore = object[0].toString();
							} else if (object[1].toString().contains("CancelHIS"))
							{
								HIScan = object[0].toString();
							}
						}
					}
					obj.put("Assign", assinged);
					obj.put("UnAssign", unaasined);
					obj.put("ACart1", String.valueOf(cart1Ass));
					obj.put("ACart2", String.valueOf(cart2Ass));
					obj.put("ACart3", String.valueOf(cart3Ass));
					obj.put("UACart1", cart1UAss);
					obj.put("UACart2", cart2UAss);
					obj.put("UACart3", cart3UAss);
					obj.put("Ignore", ignore);
					obj.put("Resolved", resolve);
					obj.put("CancelHIS", HIScan);
					obj.put("Snooze", park);
					jsonArr.add(obj);
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

	public String fetchResolveEmpList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// System.out.println(" moduleName " + moduleName);
				jsonArr = new JSONArray();
				assignTechnician4Cart = new HashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				qry.append("select cc.emp_id,emp.empName from employee_basic as emp");
				qry.append(" INNER JOIN compliance_contacts as cc on cc.emp_id=emp.id");
				qry.append(" INNER JOIN department as dept on dept.id=cc.fromDept_id");
				if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("XR"))
				{
					qry.append(" where cc.fromDept_id=(select id from department where deptname like '%Xray%') and cc.level='1'");
				} else if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("ULTRA"))
				{
					qry.append(" where cc.fromDept_id=(select id from department where deptname like '%ultrasound%') and cc.level='1'");
				}

				// System.out.println("qry>>>>  " + qry);
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("name", object[1].toString());
							jsonArr.add(obj);
						}
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

	public String getFilterStatus()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				assignTechnician4Cart = new HashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				qry.append("select status,id from machine_order_data where openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' GROUP BY status ORDER BY status");

				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("name", object[0].toString());
							jsonArr.add(obj);
						}
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

	public String getFilterNunit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				assignTechnician4Cart = new HashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				qry.append("select nu.nursing_unit,ord.id, nu.nursing_code from machine_order_data as ord LEFT join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit where ord.openDate between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' group by ord.nursingUnit ORDER BY ord.nursingUnit");

				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[2].toString());
							obj.put("name", object[0].toString());
							jsonArr.add(obj);
						}
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

	public String fetchReason()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();
				assignTechnician4Cart = new HashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				qry.append(" select id,reason_name from machine_reason_master where status='Active' and action_type='" + actiontaken + "' and order_type='" + feedId + "' order by reason_name");
				// System.out.println("qry>>>>>> " + qry);
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);

				if (data != null && !data.isEmpty())
				{
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("id", object[0].toString());
							obj.put("name", object[1].toString());
							jsonArr.add(obj);
						}
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

	// for History View of order
	public String beforeViewActivityHistoryData()
	{
		// //System.out.println("id >>> " + id);
		getTicketDetailsForAssignCPS(id);

		// details of life cycle

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List dataList = null;
			String orderName = "";
			ordHisList = new ArrayList<ArrayList<String>>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("select oh.status,oh.action_date, oh.action_time, oh.action_reason, oh.escalation_level, oh.action_by, oh.allocate_to,oh.escalate_to, oh.snooze_date, oh.snooze_time   " + " from order_status_history as oh  where oh.feedId = '" + id + "' ");
		 	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			ArrayList<String> tempList1 = null;
			MachineOrderHelper moh = new MachineOrderHelper();
			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					tempList1 = new ArrayList<String>();

					if (object[0] != null && object[8] != null && object[0].toString().equalsIgnoreCase("Snooze") && !object[8].toString().equalsIgnoreCase("0"))
					{
						tempList1.add(object[0].toString() + " (" + DateUtil.convertDateToIndianFormat(object[8].toString()) + " " + object[9].toString() + ")");

					} else
					{
						tempList1.add(CUA.getValueWithNullCheck(object[0]));
					}
					tempList1.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1])));
					tempList1.add(CUA.getValueWithNullCheck(object[2]));

					// //System.out.println("object[3] >>  " + object[3] +
					// " length " + object[3].toString().length());
					if (object[3] != null)
					{
						tempList1.add(CUA.getValueWithNullCheck(object[3]));
					} else
					{

						tempList1.add("Auto");
					}

					// //System.out.println("object[4] " +
					// object[4].toString());
					if (object[7] != null && !"".equalsIgnoreCase(object[7].toString()))
					{
						tempList1.add(CUA.getValueWithNullCheck(object[4]) + ": " + object[7].toString());
					} else
					{
						tempList1.add(CUA.getValueWithNullCheck(object[4]));
					}

					if (object[5] != null)
					{
						if (!object[5].toString().equalsIgnoreCase("0"))
						{
							tempList1.add(moh.empNameGet(object[5].toString(), connectionSpace).split("#")[0]);

						} else
						{
							tempList1.add("Auto");
						}
					} else
					{

						tempList1.add("Auto");
					}

					if (object[6] == null)
					{
						tempList1.add("Auto");

					} else
					{
						tempList1.add(moh.empNameGet(object[6].toString(), connectionSpace).split("#")[0]);
					}

					if (object[0] != null && object[0].toString().equalsIgnoreCase("Resolved"))
					{
						// //

						try
						{
							// qry.append(");
							List dataListResolveBy = cbt.executeAllSelectQuery(" select finalActionBy from machine_order_data where id =" + id + "".toString(), connectionSpace);
							// dataList =
							// cbt.executeAllSelectQuery(query.toString(),
							// connectionSpace);

							if (dataListResolveBy != null && dataListResolveBy.size() > 0)
							{

								tempList1.add(dataListResolveBy.get(0).toString());
								// machineSerialList.put(object[0].toString(),
								// object[1].toString());
								// empName =
								// object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString();

							}

						} catch (SQLGrammarException e)
						{
							e.printStackTrace();
						}

						// ///
					} else
					{

						tempList1.add("--");
					}

					ordHisList.add(tempList1);
					// //System.out.println(" ordHisList  >  " +
					// ordHisList.size()
					// + "           " + ordHisList);

				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;

	}

	public String fetchResolveDoctorList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				jsonArr = new JSONArray();

				StringBuilder qry = new StringBuilder();
				qry.append("select dep.id, emp.empName,  dep.deptName from employee_basic as emp");
				if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("ULTRA"))
				{
					qry.append(" inner join department as dep on dep.id=emp.deptName where dep.id=(select id from department where deptName ='ultrasound') ");
				}
				if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("CT"))
				{
					qry.append(" inner join department as dep on dep.id=emp.deptName where dep.id=(select id from department where deptName ='CT') ");
				}
				if (moduleName != null && !moduleName.equalsIgnoreCase("") && !moduleName.equalsIgnoreCase("undefined") && moduleName.equalsIgnoreCase("ECHO"))
				{
					qry.append(" inner join department as dep on dep.id=emp.deptName where dep.id=(select id from department where deptName ='Echo') ");
				}
				List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
				// System.out.println("query123" + qry);
				if (data != null && !data.isEmpty() && data.size() > 0)
				{
					JSONObject obj = new JSONObject();
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							obj.put("name", object[1].toString());
							jsonArr.add(obj);
						}
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

	// Method For Dashboard data grid

	public String beforeActionOnFeedback()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				setGridColomnNames();
				returnResult = "dashsuccess";

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

	public String setGridColomnNames()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderid");
			gpv.setHeadingName("Order Id");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("order");
			gpv.setHeadingName("Order");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderType");
			gpv.setHeadingName("Order Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("uhid");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("pName");
			gpv.setHeadingName("Patient Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(200);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("roomNo");
			gpv.setHeadingName("Room No");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("nursingUnit");
			gpv.setHeadingName("Nursing Unit");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderBy");
			gpv.setHeadingName("Order By");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("orderAt");
			gpv.setHeadingName("Order At");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignMchn");
			gpv.setHeadingName("Assigned Machine");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("department");
			gpv.setHeadingName("Department");
			gpv.setHideOrShow("false");
			gpv.setWidth(90);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("assignTech");
			gpv.setHeadingName("Assigned Technician");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openOn");
			gpv.setHeadingName("Open On");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("true");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();
				columnMap.put("ord.orderid As Order_Id", "Order Id");
				columnMap.put("ord.orderName AS Order_Name ", "Order Name");
				columnMap.put("ord.orderType AS Order_Type", "Order Type");
				columnMap.put("ord.uhid as UHID", "UHID");
				columnMap.put("ord.pName as Patient_Name", "Patient Name");
				columnMap.put("ord.roomNo as Room_No", "Room No");

				columnMap.put("ord.orderBy As Order_By", "Order By");
				columnMap.put("ord.orderDate AS Order_Date ", "Order Date");
				columnMap.put("ord.orderTime AS Order_Time", "Order Time");
				columnMap.put("mac.serial_No as Assigned_Machine", "Assigned Machine");
				columnMap.put("dept.deptname as Department", "Department");
				columnMap.put("emp.empName as Assined_Techinician", "Assined Techinician");

				columnMap.put("ord.escalation_date As Escalation_Date", "Escalation Date");
				columnMap.put("ord.escalation_time AS Escalation_Time ", "Escalation Time");
				columnMap.put("ord.finalActionBy AS Resolved_By", "Resolved By");
				columnMap.put("ord.status as Status", "Status");
				columnMap.put("ord.brief as Action_Remarks", "Action Remarks");
				columnMap.put(" ord.priority as Priority", "Priority");

				columnMap.put("ord.treatingDoc As Treating_Doctor", "Treating Doctor");
				columnMap.put("ord.nursingUnit AS Nursing_Unit ", "Nursing Unit");
				columnMap.put("ord.treatingSpec AS Speciality", "Speciality");
				columnMap.put("ord.level as Level", "Level");
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				return SUCCESS;
			} catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String downloadExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Order Detail";

		if (sessionFlag)
		{
			try
			{
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userType = null, empId = null, userMobno = null;
				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				String fromTime = "";
				String toTime = "";
				if (dataFlag.equalsIgnoreCase("statusTime") || dataFlag.equalsIgnoreCase("floorTime") || dataFlag.equalsIgnoreCase("nursingUnitTime"))
				{

					fromTime = time.split("-")[0];
					toTime = time.split("-")[1];
				}

				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

				StringBuilder query = new StringBuilder();
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						setToDepart(object[3].toString());
					}
				}
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");
					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append("select distinct ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								} else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" from machine_order_data as ord");
						query.append(" Inner join subdepartment as dept on dept.id=ord.department");
						query.append(" Inner join employee_basic as emp on emp.id=ord.assignTech");
						query.append(" Inner join machine_master as mac on mac.id=ord.assignMchn");
						query.append(" Inner join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
						// System.out.println("dataFlag : " + dataFlag +
						// "  machineId: " + machineId + " feedStatus:" +
						// feedStatus + " feedId:" + feedId + "  dashFor:" +
						// dashFor);
						if (dataFlag.equalsIgnoreCase("location") || dataFlag.equalsIgnoreCase("locationStatus") || dataFlag.equalsIgnoreCase("floorStatus") || dataFlag.equalsIgnoreCase("floorOrder") || dataFlag.equalsIgnoreCase("floorTime") || dataFlag.equalsIgnoreCase("nursingUnitTime"))
							query.append(" Inner join floor_detail as fdetail on fdetail.id=nu.floorname");

						if (dataFlag.equalsIgnoreCase("level") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (feedId.equalsIgnoreCase("Total"))
										query.append(" Where   ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where  ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore')  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore')  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							}

						} else if (dataFlag.equalsIgnoreCase("level") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (feedId.equalsIgnoreCase("Total"))

									query.append(" Where   ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

							else
							{
								if (feedId.equalsIgnoreCase("Total"))

									query.append(" Where   ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						} else if (dataFlag.equalsIgnoreCase("level") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status='" + feedStatus + "'  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "'  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (feedId.equalsIgnoreCase("Total"))

										query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							}
						} else if (dataFlag.equalsIgnoreCase("level") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (feedId.equalsIgnoreCase("Total"))

									query.append(" Where   ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.level='" + feedId + "' and ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (feedId.equalsIgnoreCase("Total"))

									query.append(" Where   ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.level='" + feedId + "' and ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						}

						else if (dataFlag.equalsIgnoreCase("status") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
								{
									if (machineId.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.assignMchn='" + machineId + "' And ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								}
							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
								{
									if (machineId.equalsIgnoreCase("-1"))
										query.append(" Where  ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.assignMchn='" + machineId + "' And ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								}
							}
						} else if (dataFlag.equalsIgnoreCase("status") && feedStatus.equalsIgnoreCase("All"))
						{
							if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
							{
								if (machineId.equalsIgnoreCase("-1"))

									query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else

									query.append(" Where ord.assignMchn='" + machineId + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							}
						} else if (dataFlag.equalsIgnoreCase("nursingStatus") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							}

						} else if (dataFlag.equalsIgnoreCase("nursingStatus") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status='" + feedStatus + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "'   and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "'   And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "'    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							}
						} else if (dataFlag.equalsIgnoreCase("nursingStatus") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where  ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("nursingStatus") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else

									query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where    ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else

									query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						}

						else if (dataFlag.equalsIgnoreCase("nursingOrder") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("nursingOrder") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'     And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						} else if (dataFlag.equalsIgnoreCase("nursingOrder") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where    ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  ord.nursingUnit='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where    mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						} else if (dataFlag.equalsIgnoreCase("nursingOrder") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where   mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where   mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						}

						else if (dataFlag.equalsIgnoreCase("floorStatus") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							}
						} else if (dataFlag.equalsIgnoreCase("floorStatus") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							}
						} else if (dataFlag.equalsIgnoreCase("floorStatus") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And  mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("floorStatus") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						}

						else if (dataFlag.equalsIgnoreCase("floorOrder") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("floorOrder") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'    And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("floorOrder") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where     ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  fdetail.id='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where    mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where  fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("floorOrder") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where   mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else
							{
								if (location.equalsIgnoreCase("-1"))
									query.append(" Where  mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						}

						else if (dataFlag.equalsIgnoreCase("statusTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("statusTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.status='" + feedStatus + "'    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("statusTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else if (dataFlag.equalsIgnoreCase("statusTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

						else if (dataFlag.equalsIgnoreCase("floorTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))
										query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							}
						} else if (dataFlag.equalsIgnoreCase("floorTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status='" + feedStatus + "'    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								} else
								{
									if (location.equalsIgnoreCase("-1"))

										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
									else
										query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

								}
							}

						} else if (dataFlag.equalsIgnoreCase("floorTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							} else
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							}

						} else if (dataFlag.equalsIgnoreCase("floorTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							} else
							{
								if (location.equalsIgnoreCase("-1"))

									query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							}

						} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}
						} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
						{
							if (!feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							} else if (feedStatus.equalsIgnoreCase("Resolved"))
							{
								if (dashFor.equalsIgnoreCase("-1"))
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
								else
									query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

							}

						} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
								query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
						{
							if (dashFor.equalsIgnoreCase("-1"))
								query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

						if (escFlag != null && escFlag.equalsIgnoreCase("true"))
							query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6')");

						query.append(" ORDER BY  openDate, openTime desc  ");
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{

							excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
						}
						if (excelFileName != null)
						{
							setExcelStream(new FileInputStream(excelFileName));
						}
					}
					returnResult = SUCCESS;
				} else
				{
					addActionMessage("There are some error in data!!!!");
					returnResult = ERROR;
				}
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

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public String viewActivityBoardDataCPS()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataCount = cbt.executeAllSelectQuery(" select count(*) from machine_order_data", connectionSpace);
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
				List<Object> tempList = new ArrayList<Object>();
				StringBuilder query = new StringBuilder();
				String fromTime = "";
				String toTime = "";
				if (dataFlag.equalsIgnoreCase("statusTime") || dataFlag.equalsIgnoreCase("floorTime") || dataFlag.equalsIgnoreCase("nursingUnitTime"))
				{

					fromTime = time.split("-")[0];
					toTime = time.split("-")[1];
				}

				query.append("SELECT ord.id,ord.orderid,ord.orderName,ord.orderType,ord.uhid,ord.pName,");
				query.append(" ord.roomNo,ord.orderBy,ord.orderDate,ord.orderTime,mac.machine,mac.serial_No,dept.subdeptname,emp.empName,");
				query.append("ord.openDate,ord.OpenTime,ord.escalation_date,ord.escalation_time,ord.level,ord.status,ord.priority,nu.nursing_unit");
				query.append(" from machine_order_data as ord");
				query.append(" Inner join subdepartment as dept on dept.id=ord.department");
				query.append(" Inner join employee_basic as emp on emp.id=ord.assignTech");
				query.append(" Inner join machine_master as mac on mac.id=ord.assignMchn");
				query.append(" Inner join machine_order_nursing_details as nu on nu.nursing_code=ord.nursingUnit");
				// System.out.println("dataFlag : " + dataFlag + "  machineId: "
				// + machineId + " feedStatus:" + feedStatus + " feedId:" +
				// feedId + "  dashFor:" + dashFor);
				if (dataFlag.equalsIgnoreCase("location") || dataFlag.equalsIgnoreCase("locationStatus") || dataFlag.equalsIgnoreCase("floorStatus") || dataFlag.equalsIgnoreCase("floorOrder") || dataFlag.equalsIgnoreCase("floorTime") || dataFlag.equalsIgnoreCase("nursingUnitTime"))
					query.append(" Inner join floor_detail as fdetail on fdetail.id=nu.floorname");

				if (dataFlag.equalsIgnoreCase("level") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (feedId.equalsIgnoreCase("Total"))
								query.append(" Where   ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where  ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore')  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore')  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					}

				} else if (dataFlag.equalsIgnoreCase("level") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (feedId.equalsIgnoreCase("Total"))

							query.append(" Where   ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

					else
					{
						if (feedId.equalsIgnoreCase("Total"))

							query.append(" Where   ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				} else if (dataFlag.equalsIgnoreCase("level") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status='" + feedStatus + "'  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "'  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status='" + feedStatus + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (feedId.equalsIgnoreCase("Total"))

								query.append(" Where   ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.level='" + feedId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					}
				} else if (dataFlag.equalsIgnoreCase("level") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (feedId.equalsIgnoreCase("Total"))

							query.append(" Where   ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.level='" + feedId + "' and ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore')  And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (feedId.equalsIgnoreCase("Total"))

							query.append(" Where   ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.level='" + feedId + "' and ord.status in('Resolved','Pending','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				}

				else if (dataFlag.equalsIgnoreCase("status") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
						{
							if (machineId.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.assignMchn='" + machineId + "' And ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status='" + feedStatus + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						}
					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
						{
							if (machineId.equalsIgnoreCase("-1"))
								query.append(" Where  ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.assignMchn='" + machineId + "' And ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status in ('Resolved','CancelHIS','Ignore') and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						}
					}
				} else if (dataFlag.equalsIgnoreCase("status") && feedStatus.equalsIgnoreCase("All"))
				{
					if (!machineId.equalsIgnoreCase("Xray") && !machineId.equalsIgnoreCase("Ultrasound") && !machineId.equalsIgnoreCase("CTscan") && !machineId.equalsIgnoreCase("Echo"))
					{
						if (machineId.equalsIgnoreCase("-1"))

							query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else

							query.append(" Where ord.assignMchn='" + machineId + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						query.append(" Where ord.assignMchn In(Select id From machine_master where machine='" + machineId + "') And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					}
				} else if (dataFlag.equalsIgnoreCase("nursingStatus") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					}

				} else if (dataFlag.equalsIgnoreCase("nursingStatus") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status='" + feedStatus + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "'   and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "'   And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "'    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					}
				} else if (dataFlag.equalsIgnoreCase("nursingStatus") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where  ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "'  and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("nursingStatus") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where   ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else

							query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where    ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else

							query.append(" Where  ord.nursingUnit='" + location + "' And ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				}

				else if (dataFlag.equalsIgnoreCase("nursingOrder") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("nursingOrder") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'     And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				} else if (dataFlag.equalsIgnoreCase("nursingOrder") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where    ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  ord.nursingUnit='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where    mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				} else if (dataFlag.equalsIgnoreCase("nursingOrder") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where   mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where   mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				}

				else if (dataFlag.equalsIgnoreCase("floorStatus") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					}
				} else if (dataFlag.equalsIgnoreCase("floorStatus") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'  And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					}
				} else if (dataFlag.equalsIgnoreCase("floorStatus") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And  mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("floorStatus") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				}

				else if (dataFlag.equalsIgnoreCase("floorOrder") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("floorOrder") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'    And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where ord.priority='" + feedStatus + "'   And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.priority='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("floorOrder") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where     ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  fdetail.id='" + location + "'   And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where    mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where  fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And  ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("floorOrder") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where   mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else
					{
						if (location.equalsIgnoreCase("-1"))
							query.append(" Where  mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				}

				else if (dataFlag.equalsIgnoreCase("statusTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("statusTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.status='" + feedStatus + "'    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("statusTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
						query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					else
						query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

				} else if (dataFlag.equalsIgnoreCase("statusTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
						query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					else
						query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

				}

				else if (dataFlag.equalsIgnoreCase("floorTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))
								query.append(" Where ord.status='" + feedStatus + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status='" + feedStatus + "'   And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')    And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')  And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					}
				} else if (dataFlag.equalsIgnoreCase("floorTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status='" + feedStatus + "'    and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status='" + feedStatus + "'  And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						} else
						{
							if (location.equalsIgnoreCase("-1"))

								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore')   And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							else
								query.append(" Where ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

						}
					}

				} else if (dataFlag.equalsIgnoreCase("floorTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					} else
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					}

				} else if (dataFlag.equalsIgnoreCase("floorTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					} else
					{
						if (location.equalsIgnoreCase("-1"))

							query.append(" Where ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					}

				} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && !machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}
				} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && machineId.equalsIgnoreCase("-1") && !feedStatus.equalsIgnoreCase("All"))
				{
					if (!feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status='" + feedStatus + "' And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					} else if (feedStatus.equalsIgnoreCase("Resolved"))
					{
						if (dashFor.equalsIgnoreCase("-1"))
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
						else
							query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Resolved','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

					}

				} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
						query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'   and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					else
						query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' And ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

				} else if (dataFlag.equalsIgnoreCase("nursingUnitTime") && !machineId.equalsIgnoreCase("-1") && feedStatus.equalsIgnoreCase("All"))
				{
					if (dashFor.equalsIgnoreCase("-1"))
						query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "'  And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
					else
						query.append(" Where ord.nursingUnit='" + nursingId + "' and ord.status in ('Pending','Resolved','Snooze','CancelHIS','Ignore') And fdetail.id='" + location + "' And mac.machine='" + dashFor + "' And mac.serial_No='" + machineId + "' and ord.openTime between '" + fromTime + "' and '" + toTime + "' and ord.openDate Between '" + DateUtil.convertDateToUSFormat(fromDate) + "' And '" + DateUtil.convertDateToUSFormat(toDate) + "' ");

				}

				if (escFlag != null && escFlag.equalsIgnoreCase("true"))
					query.append(" and ord.level In ('Level2','Level3','Level4','Level5','Level6')");

				query.append(" ORDER BY  openDate, openTime desc  ");
				query.append(" limit " + from + "," + to + "");
				List dataList = null;
				// System.out.println("QQQQ"+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
						Object[] object = (Object[]) iterator.next();

						tempMap.put("id", getValueWithNullCheck(object[0]));
						tempMap.put("orderid", getValueWithNullCheck(object[1]));
						tempMap.put("order", getValueWithNullCheck(object[2]));
						tempMap.put("orderType", getValueWithNullCheck(object[3]));
						tempMap.put("uhid", getValueWithNullCheck(object[4]));
						tempMap.put("pName", getValueWithNullCheck(object[5]));
						tempMap.put("roomNo", getValueWithNullCheck(object[6]));
						tempMap.put("orderBy", getValueWithNullCheck(object[7]));
						tempMap.put("orderAt", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[8].toString())) + " " + getValueWithNullCheck(object[9]));
						tempMap.put("assignMchn", getValueWithNullCheck(object[10]) + "-" + getValueWithNullCheck(object[11]));
						tempMap.put("department", getValueWithNullCheck(object[12]));
						tempMap.put("assignTech", getValueWithNullCheck(object[13]));
						tempMap.put("openOn", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[14].toString())) + " " + getValueWithNullCheck(object[15]));
						tempMap.put("level", getValueWithNullCheck(object[18]));
						tempMap.put("status", getValueWithNullCheck(object[19]));
						tempMap.put("priority", getValueWithNullCheck(object[20]));
						tempMap.put("nursingUnit", getValueWithNullCheck(object[21]));
						tempList.add(tempMap);

						// ////System.out.println("Set Data in masterViewProp");

					}

				}

				setMasterViewProp(tempList);
				// ////System.out.println(" Size :  "+tempList.size());
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				returnResult = SUCCESS;

			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	public String orderFetchDetails(){
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List dataList = cbt.executeAllSelectQuery("SELECT orderDate, orderTime FROM machine_order_data  ORDER BY id DESC LIMIT 1", connectionSpace);
		ArrayList<String> tempList1 = null;

		MachineOrderHelper moh = new MachineOrderHelper();
		if (dataList != null && dataList.size() > 0)
		{

			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				lastOrderDateTime = DateUtil.convertDateToIndianFormat(object[0].toString()) +"  "+object[1].toString();
				
			String timeDiff = 	DateUtil.time_difference(object[0].toString(), object[1].toString(),DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin());

			timeDiff = timeDiff.split(":")[0];
			System.out.println("timeDiff "+timeDiff);
			//int foo = Integer.parseInt(timeDiff);
				if(Integer.parseInt(timeDiff)<=1){
					//timeDiff = timeDiff.split(":")[1];
					//if(Integer.parseInt(timeDiff)>10)
					forceFetch = "1";
				}
				else{
					forceFetch = "1";
				}
				System.out.println("forceFetch  "+forceFetch);
			}
		}
		return SUCCESS;
	}
	
	public String orderFetchDR(){
		
		ClientDataIntegrationRedirectServer CDIHRedirect =  new ClientDataIntegrationRedirectServer();
		SessionFactory con = new ConnectionHelper().getSessionFactory("IN-4");
		Session sessionHis = HisHibernateSessionFactory.getSession();
		CDIHRedirect.datafetchForOrder(log, con, sessionHis, ch);
		CDIHRedirect.datafetchForOrderEM(log, con, sessionHis, ch);
		
		return SUCCESS;
	}
	
public String orderFetchHIS(){
		
		ClientDataIntegrationHelper CDIH =  new ClientDataIntegrationHelper();
		SessionFactory con = new ConnectionHelper().getSessionFactory("IN-4");
		Session sessionHis = HisHibernateSessionFactory.getSession();
		CDIH.datafetchForOrder(log, con, sessionHis, ch);
		CDIH.datafetchForOrderEM(log, con, sessionHis, ch);
		
		return SUCCESS;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public Map<Integer, String> getResolverList()
	{
		return resolverList;
	}

	public void setResolverList(Map<Integer, String> resolverList)
	{
		this.resolverList = resolverList;
	}

	public String getResolver()
	{
		return resolver;
	}

	public void setResolver(String resolver)
	{
		this.resolver = resolver;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getSpareused()
	{
		return spareused;
	}

	public void setSpareused(String spareused)
	{
		this.spareused = spareused;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getHpcomment()
	{
		return hpcomment;
	}

	public void setHpcomment(String hpcomment)
	{
		this.hpcomment = hpcomment;
	}

	public String getIgnorecomment()
	{
		return ignorecomment;
	}

	public void setIgnorecomment(String ignorecomment)
	{
		this.ignorecomment = ignorecomment;
	}

	public String getSnoozeDate()
	{
		return snoozeDate;
	}

	public void setSnoozeDate(String snoozeDate)
	{
		this.snoozeDate = snoozeDate;
	}

	public String getSnoozeTime()
	{
		return snoozeTime;
	}

	public void setSnoozeTime(String snoozeTime)
	{
		this.snoozeTime = snoozeTime;
	}

	public String getSnoozecomment()
	{
		return snoozecomment;
	}

	public void setSnoozecomment(String snoozecomment)
	{
		this.snoozecomment = snoozecomment;
	}

	public String getTosubdept()
	{
		return tosubdept;
	}

	public void setTosubdept(String tosubdept)
	{
		this.tosubdept = tosubdept;
	}

	public String getReAssignRemark()
	{
		return reAssignRemark;
	}

	public void setReAssignRemark(String reAssignRemark)
	{
		this.reAssignRemark = reAssignRemark;
	}

	public String getFeedid()
	{
		return feedid;
	}

	public void setFeedid(String feedid)
	{
		this.feedid = feedid;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getHeadingTitle()
	{
		return headingTitle;
	}

	public void setHeadingTitle(String headingTitle)
	{
		this.headingTitle = headingTitle;
	}

	public String getFeedbackBy()
	{
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy)
	{
		this.feedbackBy = feedbackBy;
	}

	public String getMobileno()
	{
		return mobileno;
	}

	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public String getSubCatg()
	{
		return subCatg;
	}

	public void setSubCatg(String subCatg)
	{
		this.subCatg = subCatg;
	}

	public String getFeed_brief()
	{
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}

	public String getAllotto()
	{
		return allotto;
	}

	public void setAllotto(String allotto)
	{
		this.allotto = allotto;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getResolveon()
	{
		return resolveon;
	}

	public void setResolveon(String resolveon)
	{
		this.resolveon = resolveon;
	}

	public String getResolveat()
	{
		return resolveat;
	}

	public void setResolveat(String resolveat)
	{
		this.resolveat = resolveat;
	}

	public String getActiontaken()
	{
		return actiontaken;
	}

	public void setActiontaken(String actiontaken)
	{
		this.actiontaken = actiontaken;
	}

	public String getTodept()
	{
		return todept;
	}

	public void setTodept(String todept)
	{
		this.todept = todept;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPincode()
	{
		return pincode;
	}

	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getDataFlag()
	{
		return dataFlag;
	}

	public void setDataFlag(String dataFlag)
	{
		this.dataFlag = dataFlag;
	}

	public String getDashFor()
	{
		return dashFor;
	}

	public void setDashFor(String dashFor)
	{
		this.dashFor = dashFor;
	}

	public String getD_subdept_id()
	{
		return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id)
	{
		this.d_subdept_id = d_subdept_id;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(String openDate)
	{
		this.openDate = openDate;
	}

	public String getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(String openTime)
	{
		this.openTime = openTime;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getFeedBreif()
	{
		return feedBreif;
	}

	public void setFeedBreif(String feedBreif)
	{
		this.feedBreif = feedBreif;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public Map<String, String> getStatusList()
	{
		return statusList;
	}

	public void setStatusList(Map<String, String> statusList)
	{
		this.statusList = statusList;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getFeedbackSubCatgory()
	{
		return feedbackSubCatgory;
	}

	public void setFeedbackSubCatgory(String feedbackSubCatgory)
	{
		this.feedbackSubCatgory = feedbackSubCatgory;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public String getBackData()
	{
		return backData;
	}

	public void setBackData(String backData)
	{
		this.backData = backData;
	}

	public String getSn_reason()
	{
		return sn_reason;
	}

	public void setSn_reason(String sn_reason)
	{
		this.sn_reason = sn_reason;
	}

	public File getApprovalDoc()
	{
		return approvalDoc;
	}

	public void setApprovalDoc(File approvalDoc)
	{
		this.approvalDoc = approvalDoc;
	}

	public String getApprovalDocContentType()
	{
		return approvalDocContentType;
	}

	public void setApprovalDocContentType(String approvalDocContentType)
	{
		this.approvalDocContentType = approvalDocContentType;
	}

	public String getApprovalDocFileName()
	{
		return approvalDocFileName;
	}

	public void setApprovalDocFileName(String approvalDocFileName)
	{
		this.approvalDocFileName = approvalDocFileName;
	}

	public String getStoredDocPath()
	{
		return storedDocPath;
	}

	public void setStoredDocPath(String storedDocPath)
	{
		this.storedDocPath = storedDocPath;
	}

	public String getApprovedBy()
	{
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy)
	{
		this.approvedBy = approvedBy;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getComplaintid()
	{
		return complaintid;
	}

	public void setComplaintid(String complaintid)
	{
		this.complaintid = complaintid;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public FeedbackPojo getFstatus()
	{
		return fstatus;
	}

	public void setFstatus(FeedbackPojo fstatus)
	{
		this.fstatus = fstatus;
	}

	public Map<String, String> getCheckListMap()
	{
		return checkListMap;
	}

	public void setCheckListMap(Map<String, String> checkListMap)
	{
		this.checkListMap = checkListMap;
	}

	public String getOrgImgPath()
	{
		return orgImgPath;
	}

	public void setOrgImgPath(String orgImgPath)
	{
		this.orgImgPath = orgImgPath;
	}

	public String getResolve_Alert()
	{
		return resolve_Alert;
	}

	public void setResolve_Alert(String resolve_Alert)
	{
		this.resolve_Alert = resolve_Alert;
	}

	public String getReallotedto()
	{
		return reallotedto;
	}

	public void setReallotedto(String reallotedto)
	{
		this.reallotedto = reallotedto;
	}

	public String getReallotedcomment()
	{
		return reallotedcomment;
	}

	public void setReallotedcomment(String reallotedcomment)
	{
		this.reallotedcomment = reallotedcomment;
	}

	public void setNon_working_timing(String non_working_timing)
	{
		this.non_working_timing = non_working_timing;
	}

	public String getNon_working_timing()
	{
		return non_working_timing;
	}

	public void setNew_escalation_datetime(String new_escalation_datetime)
	{
		this.new_escalation_datetime = new_escalation_datetime;
	}

	public String getNew_escalation_datetime()
	{
		return new_escalation_datetime;
	}

	public String getIgnoreremark()
	{
		return ignoreremark;
	}

	public void setIgnoreremark(String ignoreremark)
	{
		this.ignoreremark = ignoreremark;
	}

	public String getHpremark()
	{
		return hpremark;
	}

	public void setHpremark(String hpremark)
	{
		this.hpremark = hpremark;
	}

	public String getReopenremark()
	{
		return reopenremark;
	}

	public void setReopenremark(String reopenremark)
	{
		this.reopenremark = reopenremark;
	}

	public String getSnoozeremark()
	{
		return snoozeremark;
	}

	public void setSnoozeremark(String snoozeremark)
	{
		this.snoozeremark = snoozeremark;
	}

	public String getReassignreason()
	{
		return reassignreason;
	}

	public void setReassignreason(String reassignreason)
	{
		this.reassignreason = reassignreason;
	}

	public List getCycleList()
	{
		return cycleList;
	}

	public void setCycleList(List cycleList)
	{
		this.cycleList = cycleList;
	}

	public Map<String, String> getMachineSerialList()
	{
		return machineSerialList;
	}

	public void setMachineSerialList(Map<String, String> machineSerialList)
	{
		this.machineSerialList = machineSerialList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

	public Map<String, String> getAssignTechnician4Cart()
	{
		return assignTechnician4Cart;
	}

	public void setAssignTechnician4Cart(Map<String, String> assignTechnician4Cart)
	{
		this.assignTechnician4Cart = assignTechnician4Cart;
	}

	public ArrayList<ArrayList<String>> getPUHID()
	{
		return pUHID;
	}

	public void setPUHID(ArrayList<ArrayList<String>> puhid)
	{
		pUHID = puhid;
	}

	public ArrayList<ArrayList<String>> getPDataCart()
	{
		return pDataCart;
	}

	public void setPDataCart(ArrayList<ArrayList<String>> dataCart)
	{
		pDataCart = dataCart;
	}

	public String getCartID()
	{
		return cartID;
	}

	public void setCartID(String cartID)
	{
		this.cartID = cartID;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getSearchFieldsFlag()
	{
		return searchFieldsFlag;
	}

	public void setSearchFieldsFlag(String searchFieldsFlag)
	{
		this.searchFieldsFlag = searchFieldsFlag;
	}

	public void setAllocateTo(String allocateTo)
	{
		this.allocateTo = allocateTo;
	}

	public String getAllocateTo()
	{
		return allocateTo;
	}

	public void setComplaintidAll(String complaintidAll)
	{
		this.complaintidAll = complaintidAll;
	}

	public String getComplaintidAll()
	{
		return complaintidAll;
	}

	public ArrayList<ArrayList<String>> getCartCount()
	{
		return cartCount;
	}

	public void setCartCount(ArrayList<ArrayList<String>> cartCount)
	{
		this.cartCount = cartCount;
	}

	public ArrayList<ArrayList<String>> getOrdCount()
	{
		return ordCount;
	}

	public void setOrdCount(ArrayList<ArrayList<String>> ordCount)
	{
		this.ordCount = ordCount;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ArrayList<ArrayList<String>> getOrdHisList()
	{
		return ordHisList;
	}

	public void setOrdHisList(ArrayList<ArrayList<String>> ordHisList)
	{
		this.ordHisList = ordHisList;
	}

	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getNursingId()
	{
		return nursingId;
	}

	public void setNursingId(String nursingId)
	{
		this.nursingId = nursingId;
	}

	public String getMachineId()
	{
		return machineId;
	}

	public void setMachineId(String machineId)
	{
		this.machineId = machineId;
	}

	public String getEscFlag()
	{
		return escFlag;
	}

	public void setEscFlag(String escFlag)
	{
		this.escFlag = escFlag;
	}

	public String getToDepart()
	{
		return toDepart;
	}

	public void setToDepart(String toDepart)
	{
		this.toDepart = toDepart;
	}

	public void setData4(String data4)
	{
		this.data4 = data4;
	}

	public String getData4()
	{
		return data4;
	}

	public String getLastOrderDateTime()
	{
		return lastOrderDateTime;
	}

	public void setLastOrderDateTime(String lastOrderDateTime)
	{
		this.lastOrderDateTime = lastOrderDateTime;
	}

	public String getForceFetch()
	{
		return forceFetch;
	}

	public void setForceFetch(String forceFetch)
	{
		this.forceFetch = forceFetch;
	}

}