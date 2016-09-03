package com.Over2Cloud.ctrl.leaveManagement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ReportAction extends GridPropertyBean {
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	static final Log log = LogFactory.getLog(ReportAction.class);
	private Map<String, String> deptName;
	private String deptId;
	private String SubdeptId;
	private String emp;
	private JSONObject responseDetailsJson = null;
	private JSONArray jsonArray = null;
	private List<GridDataPropertyView> reportGridColoumnNames = null;
	private List<GridDataPropertyView> pullreportGridColomns = new ArrayList<GridDataPropertyView>();
	private List<Object> reportTotalView;
	private List<Object> viewLeavePullData;
	private String tdate;
	private String empIds;
	// GRID OPERATION
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = true;
	// Grid colomn view
	private String oper;
	private String fdate;
	private String fieldser;
	private String fieldval;

	@SuppressWarnings("rawtypes")
	public String totalReport() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				LeaveHelper LH = new LeaveHelper();
				String data[] = LH.getEmpDetailsByUserName("HR", userName,
						connectionSpace);
				deptName = new LinkedHashMap<String, String>();
				if (data != null && !data.toString().equalsIgnoreCase("")) {
					deptName.put("All", "All Department");
					List dataCount = LH.getDeptName("HR", data[4],
							connectionSpace);
					if (dataCount != null && dataCount.size() > 0) {
						Object[] object = null;
						for (Iterator iterator = dataCount.iterator(); iterator
								.hasNext();) {
							object = (Object[]) iterator.next();
							if (object != null) {
								deptName.put(object[0].toString(),
										object[1].toString());
							}
						}
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getEmployyeeReport() {
		try {
			List data2 = null;
			String data[] = null;
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			jsonArray = new JSONArray();
			LeaveHelper LH = new LeaveHelper();
			if (getDeptId() != null && !getDeptId().equalsIgnoreCase("All")) {
				data = LH.getEmpDetailsByUserName("HR", userName,
						connectionSpace);
				data2 = LH.getOnChangeEmployeeData("HR", data[4], getDeptId(),
						connectionSpace);
			} else {
				data = LH.getEmpDetailsByUserName("HR", userName,
						connectionSpace);
				data2 = LH.getOnChangeEmployeeData("HR", data[4], "",
						connectionSpace);
			}
			if (data2 != null && data2.size() > 0) {
				JSONObject formDetailsJson = new JSONObject();
				formDetailsJson.put("ID", "All");
				formDetailsJson.put("NAME", "All Employee");
				jsonArray.add(formDetailsJson);
				Object[] object = null;
				for (Iterator iterator = data2.iterator(); iterator.hasNext();) {
					object = (Object[]) iterator.next();
					if (object != null) {
						formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", object[0].toString());
						formDetailsJson.put("NAME", object[1].toString());
						jsonArray.add(formDetailsJson);
					}
				}
			}
		} catch (Exception e) {
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method: getEmployyeeReport of class "
							+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String gridTotalReport() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setGridColomnNames1();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void setGridColomnNames1() {
		try {
			reportGridColoumnNames = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			reportGridColoumnNames.add(gpv);
			GridDataPropertyView gpv1 = new GridDataPropertyView();
			gpv1.setColomnName("empname");
			gpv1.setHeadingName("Employee Name");
			gpv1.setHideOrShow("false");
			gpv1.setEditable("true");
			gpv1.setSearch("true");
			gpv1.setWidth(120);
			reportGridColoumnNames.add(gpv1);
			GridDataPropertyView gpv2 = new GridDataPropertyView();
			gpv2.setColomnName("CL");
			gpv2.setHeadingName("Last C/F");
			gpv2.setHideOrShow("false");
			gpv2.setEditable("true");
			gpv2.setSearch("true");
			gpv2.setWidth(120);
			reportGridColoumnNames.add(gpv2);

			GridDataPropertyView gpv4 = new GridDataPropertyView();
			gpv4.setColomnName("CurrentLeave");
			gpv4.setHeadingName("Current Leave");
			gpv4.setHideOrShow("false");
			gpv4.setEditable("true");
			gpv4.setSearch("true");
			gpv4.setWidth(115);
			reportGridColoumnNames.add(gpv4);
			GridDataPropertyView gpv3 = new GridDataPropertyView();
			gpv3.setColomnName("Total");
			gpv3.setHeadingName("Total");
			gpv3.setHideOrShow("false");
			gpv3.setEditable("true");
			gpv3.setSearch("true");
			gpv3.setWidth(100);
			reportGridColoumnNames.add(gpv3);
			GridDataPropertyView gpv5 = new GridDataPropertyView();
			gpv5.setColomnName("Balance");
			gpv5.setHeadingName("Balance");
			gpv5.setHideOrShow("false");
			gpv5.setEditable("true");
			gpv5.setSearch("true");
			gpv5.setWidth(100);
			reportGridColoumnNames.add(gpv5);

			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_time_slot_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"record_data_configuration");
			if (statusColName != null && statusColName.size() > 0) {
				for (GridDataPropertyView gdp : statusColName) {
					if (!gdp.getColomnName().equalsIgnoreCase("twdays")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"totalDeduction")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("empname")) {
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						reportGridColoumnNames.add(gpv);
					}
				}
			}
			session.put("ListReportColumnNAmes", reportGridColoumnNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String reportDataAll() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				List<GridDataPropertyView> list = new ArrayList<GridDataPropertyView>();
				if (session.containsKey("ListReportColumnNAmes")) {
					list = (List<GridDataPropertyView>) session
							.get("ListReportColumnNAmes");
					session.remove("ListReportColumnNAmes");
				}
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String query1 = "select count(*) from report_data";
				System.out.println("query1  :::" + query1);
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				if (dataCount != null && dataCount.size() > 0) {
					BigInteger count = new BigInteger("1");
					Object obdata = null;
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					String queryEmployee = null;
					if (getDeptId() != null
							&& getDeptId().equalsIgnoreCase("All")
							&& getEmp() != null
							&& !getEmp().equalsIgnoreCase("All")) {
						queryEmployee = "SELECT empname FROM report_data WHERE tdate LIKE '%"
								+ getTdate().split("-")[2]
								+ "-"
								+ getTdate().split("-")[1]
								+ "%' AND empname = " + getEmp() + "";
						System.out.println("queryEmployee:::" + queryEmployee);
					} else if (getDeptId() != null
							&& !getDeptId().equalsIgnoreCase("All")
							&& !getEmp().equalsIgnoreCase("All")) {
						queryEmployee = "SELECT empname FROM report_data WHERE tdate LIKE '%"
								+ getTdate().split("-")[2]
								+ "-"
								+ getTdate().split("-")[1]
								+ "%' AND empname = " + getEmp() + "";
						System.out.println("queryEmployee else If:::"
								+ queryEmployee);
					} else {
						queryEmployee = "SELECT empname FROM report_data WHERE tdate LIKE '%"
								+ getTdate().split("-")[2]
								+ "-"
								+ getTdate().split("-")[1] + "%'";
						System.out.println("queryEmployee else :::"
								+ queryEmployee);
					}
					List empnameList = cbt.executeAllSelectQuery(queryEmployee,
							connectionSpace);
					StringBuilder query = null;
					List<Object> Listhb = new ArrayList<Object>();
					List dataTotal = new ArrayList<String>();
					if (empnameList != null && empnameList.size() > 0) {
						Object object = null;
						for (Iterator iterator = empnameList.iterator(); iterator
								.hasNext();) {
							object = (Object) iterator.next();
							if (object != null) {
								query = new StringBuilder();
								query.append("SELECT ");
								List fieldNames = Configuration.getColomnList(
										"mapped_time_slot_configuration",
										accountID, connectionSpace,
										"record_data_configuration");
								int i = 0;
								if (fieldNames != null && fieldNames.size() > 0) {
									Object obdata1 = null;
									for (Iterator it = fieldNames.iterator(); it
											.hasNext();) {
										// generating the dyanamic query based
										// on selected fields
										obdata1 = (Object) it.next();
										if (obdata1 != null
												&& !obdata1.equals("id")
												&& !obdata1.equals("twdays")
												&& !obdata1
														.equals("totalDeduction")
												&& !obdata1.equals("date")
												&& !obdata1.equals("empname")) {
											if (i < fieldNames.size() - 1) {
												query.append(obdata1.toString()
														+ ",");
											} else {
												query.append(obdata1.toString());
											}
										}
										i++;
									}
								}
								query.append("emp.empName,total_leaves,rd.empname as empIdd");
								query.append(" from report_data as rd ");
								query.append(" INNER JOIN compliance_contacts AS cc On cc.id=rd.empname ");
								query.append(" INNER JOIN employee_basic as emp on cc.emp_id=emp.id ");
								query.append(" WHERE rd.empname='"
										+ object.toString()
										+ "' AND tdate LIKE '%"
										+ getTdate().split("-")[2] + "-"
										+ getTdate().split("-")[1] + "%'");
								System.out.println("query >>>>>" + query);
								if (getSearchField() != null
										&& getSearchString() != null
										&& !getSearchField().equalsIgnoreCase(
												"")
										&& !getSearchString().equalsIgnoreCase(
												"")) {
									query.append(" where ");
									// add search query based on the search
									// operation
									if (getSearchOper().equalsIgnoreCase("eq")) {
										query.append(" " + getSearchField()
												+ " = '" + getSearchString()
												+ "'");
									} else if (getSearchOper()
											.equalsIgnoreCase("cn")) {
										query.append(" " + getSearchField()
												+ " like '%"
												+ getSearchString() + "%'");
									} else if (getSearchOper()
											.equalsIgnoreCase("bw")) {
										query.append(" " + getSearchField()
												+ " like '" + getSearchString()
												+ "%'");
									} else if (getSearchOper()
											.equalsIgnoreCase("ne")) {
										query.append(" " + getSearchField()
												+ " <> '" + getSearchString()
												+ "'");
									} else if (getSearchOper()
											.equalsIgnoreCase("ew")) {
										query.append(" " + getSearchField()
												+ " like '%"
												+ getSearchString() + "'");
									}
								}
								// query.append(" limit "+from+","+to);
								List data = cbt.executeAllSelectQuery(
										query.toString(), connectionSpace);
								double finalLeaveGranted = getLeaveGranted(
										object.toString(), connectionSpace);
								Map<String, Object> one = null;
								if (data != null && data.size() > 0) {
									if (list != null && list.size() > 0) {
										reportTotalView = new ArrayList<Object>();
										Object[] obdata1 = null;
										for (Iterator it = data.iterator(); it
												.hasNext();) {
											obdata1 = (Object[]) it.next();
											one = new HashMap<String, Object>();
											for (GridDataPropertyView gpd : list) {
												double totalValue = (Double
														.parseDouble(obdata1[0]
																.toString())
														+ finalLeaveGranted + Double
														.parseDouble(obdata1[1]
																.toString()));
												if (obdata1 != null) {
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"id")) {
														one.put(gpd
																.getColomnName(),
																obdata1[5]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"empname")) {
														one.put(gpd
																.getColomnName(),
																obdata1[3]
																		.toString());
														dataTotal
																.add(obdata1[3]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"cf")) {
														one.put(gpd
																.getColomnName(),
																obdata1[0]
																		.toString());
														dataTotal
																.add(obdata1[0]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"CL")) {
														one.put(gpd
																.getColomnName(),
																finalLeaveGranted);
														dataTotal
																.add(finalLeaveGranted);
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"extraworking")) {
														one.put(gpd
																.getColomnName(),
																obdata1[1]
																		.toString());
														dataTotal
																.add(obdata1[1]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"Total")) {
														one.put(gpd
																.getColomnName(),
																totalValue);
														dataTotal
																.add(totalValue);
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"CurrentLeave")) {
														one.put(gpd
																.getColomnName(),
																obdata1[4]
																		.toString());
														dataTotal
																.add(obdata1[4]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"Balance")) {
														one.put(gpd
																.getColomnName(),
																obdata1[2]
																		.toString());
														dataTotal
																.add(obdata1[2]
																		.toString());
													}
													if (gpd.getColomnName()
															.equalsIgnoreCase(
																	"lnextmonth")) {
														one.put(gpd
																.getColomnName(),
																obdata1[2]
																		.toString());
														dataTotal
																.add(obdata1[2]
																		.toString());
													}
												}
											}
										}
										Listhb.add(one);
									}
								}
								setReportTotalView(Listhb);
								setTotal((int) Math.ceil((double) getRecords()
										/ (double) getRows()));
							}
						}
						if (dataTotal != null && dataTotal.size() > 0) {
							session.put("totalData", dataTotal);
						}
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public double getLeaveGranted(String id, SessionFactory connectionSpace) {
		LeaveHelper LH = new LeaveHelper();
		String userName = (String) session.get("uName");
		String empId = (String) session.get("empIdofuser").toString()
				.split("-")[1];
		String dataemp = LH.getContactId("HR", connectionSpace, empId);
		double finalLeaveGranted = 0;
		try {
			String query6 = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String[] data = new LeaveHelper().getEmployeeDetails(id, "HR",
					connectionSpace);
			if (data[1] != null && data[1].equalsIgnoreCase("1")) {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee' ";
			} else {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent' ";
			}

			List leaveAlloted = cbt.executeAllSelectQuery(query6,
					connectionSpace);
			if (leaveAlloted != null && leaveAlloted.size() > 0) {
				finalLeaveGranted = Double.parseDouble(leaveAlloted.get(0)
						.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalLeaveGranted;
	}

	@SuppressWarnings("rawtypes")
	public String sendMailReportAction() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String datearr[] = getEmpIds().split(",");
				System.out.println("datearr" + datearr);
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String cf = null;
				String extraworking = null;
				String lnextmonth = null;
				String empName = null;
				String emailIdOne = null;
				String total_leaves = null;
				System.out.println("datearr.length :::: " + datearr.length);
				for (int i = 0; i < datearr.length; i++) {
					String query = "SELECT cf,extraworking,lnextmonth,emp.empName,emp.emailIdOne,total_leaves from report_data as rd "
							+ " INNER JOIN compliance_contacts AS cc On cc.id=rd.empname "
							+ " INNER JOIN employee_basic as emp on cc.emp_id=emp.id "
							+ "WHERE rd.empname='"
							+ datearr[i]
							+ "' AND tdate LIKE '%"
							+ getTdate().split("-")[2]
							+ "-" + getTdate().split("-")[1] + "%'";
					System.out.println("query for send mail" + query);
					List data = cbt.executeAllSelectQuery(query,
							connectionSpace);
					if (data != null && data.size() > 0) {
						Object[] object = null;
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							object = (Object[]) iterator.next();
							if (object != null) {
								cf = object[0].toString();
								extraworking = object[1].toString();
								lnextmonth = object[2].toString();
								empName = object[3].toString();
								emailIdOne = object[4].toString();
								total_leaves = object[5].toString();
							}
						}
						double finalLeaveGranted = getLeaveGranted(datearr[i],
								connectionSpace);
						double totalValue = (Double.parseDouble(cf)
								+ finalLeaveGranted + Double
								.parseDouble(extraworking));
						String mailText = getMailBody(empName,
								finalLeaveGranted, total_leaves, totalValue,
								lnextmonth, cf, extraworking);
						new MsgMailCommunication().addMail("", "", emailIdOne,
								"Leave Report", mailText, "", "Pending", "0",
								"", "HR");
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}

	}

	private String getMailBody(String empName, double finalLeaveGranted,
			String total_leaves, double totalValue, String lnextmonth,
			String cf, String extraworking) {
		StringBuilder mailText = new StringBuilder();
		try {
			mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Dear "
					+ empName + ",</b></td></tr></tbody></table>");
			mailText.append("<br>");
			mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Your Leave Summary as follows:</b></td></tr></tbody></table>");
			mailText.append("<br>");

			mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Leave Allowed:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ finalLeaveGranted + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Current Leave:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ total_leaves + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ totalValue + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Balance:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ lnextmonth + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> C/F Leave:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ cf + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Extra Working:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ extraworking + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next C/F Leave:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
					+ lnextmonth + "</td></tr>");
			mailText.append("</table>");
			mailText.append("<br>");
			mailText.append("For any query kindly contact to HR Dept.");
			mailText.append("<br>");
			mailText.append("<br>");
			mailText.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailText.toString();
	}

	@SuppressWarnings("rawtypes")
	public String sendAutomaticReport() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				List<AttendancePojo> finalList = new ArrayList<AttendancePojo>();
				AttendancePojo ap = null;
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String date = DateUtil.getLastDateFromLastDate(-1,
						DateUtil.getCurrentDateUSFormat());

				String cf = null;
				String extraworking = null;
				String lnextmonth = null;
				String empName = null;
				String emailIdOne = null;
				String total_leaves = null;
				String empId = null;
				String dataQuery = "SELECT cf,extraworking,lnextmonth,emp.empName,emp.emailIdOne,total_leaves,rd.empname as empID from report_data as rd "
						+ "INNER JOIN employee_basic as emp on rd.empname=emp.empId "
						+ "WHERE  tdate LIKE '%2013-10-%'";
				System.out.println("dataQuery:::" + dataQuery);
				/*
				 * String dataQuery=
				 * "SELECT cf,extraworking,lnextmonth,emp.empName,emp.emailIdOne,total_leaves,rd.empname as empID from report_data as rd "
				 * +
				 * "INNER JOIN employee_basic as emp on rd.empname=emp.empId "+
				 * "WHERE tdate LIKE '%"
				 * +date.split("-")[0]+"-"+date.split("-")[1]+"-"+"%'";
				 */
				List data1 = cbt.executeAllSelectQuery(dataQuery,
						connectionSpace);
				if (data1 != null && data1.size() > 0) {
					Object[] object1 = null;
					for (Iterator iterator1 = data1.iterator(); iterator1
							.hasNext();) {
						object1 = (Object[]) iterator1.next();
						ap = new AttendancePojo();
						if (object1 != null) {
							cf = object1[0].toString();
							extraworking = object1[1].toString();
							lnextmonth = object1[2].toString();
							empName = object1[3].toString();
							emailIdOne = object1[4].toString();
							total_leaves = object1[5].toString();
							empId = object1[6].toString();

							System.out.println("cf   " + cf);
							System.out
									.println("extraworking   " + extraworking);
							System.out.println("lnextmonth   " + lnextmonth);
							System.out.println("empName   " + empName);
							System.out
									.println("total_leaves   " + total_leaves);
							System.out.println("empId        " + empId);
							ap.setEmpname(empName);
							ap.setCf(cf);
							ap.setExtraworking(extraworking);
							ap.setLnextmonth(lnextmonth);
							ap.setTotal_leaves(total_leaves);
							double finalLeaveGranted = getLeaveGranted(empId,
									connectionSpace);
							ap.setFinalLeaveGranted(finalLeaveGranted);
							double totalValue = (Double.parseDouble(cf)
									+ finalLeaveGranted + Double
									.parseDouble(extraworking));
							ap.setTotalValue(totalValue);
							System.out
									.println("---------------------------------------------  ");

							String mailText = getMailBody(empName,
									finalLeaveGranted, total_leaves,
									totalValue, lnextmonth, cf, extraworking);
							new MsgMailCommunication()
									.addMail(
											"",
											"",
											emailIdOne,
											"Leave Report As On "
													+ DateUtil
															.convertDateToIndianFormat(date)
													+ "", mailText, "",
											"Pending", "0", "", "HR");
							finalList.add(ap);
						}
					}
				}

				// GenericMailSender ms = new GenericMailSender();
				// ms.sendMail("karnikag@dreamsol.biz", "Leave Report",
				// mailText, "", "smtp.gmail.com", "465", "hr@dreamsol.biz",
				// "hr@dreamsol");
				String mailTextReport = getMailTextReport(finalList);
				GenericMailSender ms = new GenericMailSender();
				// ms.sendMail("karnikag@dreamsol.biz", "Leave Report",
				// mailTextReport, "", "smtp.gmail.com", "465",
				// "hr@dreamsol.biz", "hr@dreamsol");

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}

	}

	private String getMailTextReport(List<AttendancePojo> finalList) {
		StringBuilder mailtext = new StringBuilder("");
		try {
			if (finalList != null && finalList.size() > 0) {
				mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Telesolutions Pvt. Ltd.</b></td></tr></tbody></table>");
				mailtext.append("<br><b>Hello!!!</b>");
				mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Consolidated Attendance Report As On "
						+ DateUtil.getCurrentDateIndianFormat()
						+ " At "
						+ DateUtil.getCurrentTimeHourMin()
						+ "</b></td></tr></tbody></table>");

				mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
				mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Last&nbsp;Cf</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Extra&nbsp;Working</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Leave Allowed</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total(Last Cf+Ex. Working+Leave Allowed)</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Leave Taken</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Next&nbsp;Month&nbsp;Cf</strong></td></tr>");

				int i = 0;
				for (AttendancePojo FBP : finalList) {
					int k = ++i;
					if (k % 2 != 0) {
						mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getEmpname()
								+ "</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getCf()
								+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getExtraworking()
								+ "</td> <td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getFinalLeaveGranted()
								+ "</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getTotalValue()
								+ "</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getTotal_leaves()
								+ "</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getLnextmonth() + "</td></tr>");
					} else {
						mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getEmpname()
								+ "</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getCf()
								+ "</td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getExtraworking()
								+ "</td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getFinalLeaveGranted()
								+ "</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getTotalValue()
								+ "</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getTotal_leaves()
								+ "</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
								+ FBP.getLnextmonth() + "</td></tr>");
					}
				}
				mailtext.append("</tbody></table>");
				mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
				mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailtext.toString();
	}

	public String beforeLeavePullReport() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				LeaveHelper LH = new LeaveHelper();
				String userName = (String) session.get("uName");
				String empId = (String) session.get("empIdofuser").toString()
						.split("-")[1];

				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setGridColomnNames();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public void setGridColomnNames() {
		Map session = ActionContext.getContext().getSession();
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session
				.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		gpv.setSearch("true");
		pullreportGridColomns.add(gpv);
		System.out.println("pullreportGridColomns >>>>>>>"
				+ pullreportGridColomns.size());
		try {
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData(
							"mapped_Leavepullreport_configuration", accountID,
							connectionSpace, "", 0, "table_name",
							"Leavepullreport_configuration");
			if (statusColName != null && statusColName.size() > 0) {
				for (GridDataPropertyView gdp : statusColName) {
					if (!gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("comment")
							&& !gdp.getColomnName().equalsIgnoreCase("empid")
							&& !gdp.getColomnName().equalsIgnoreCase("name")
							&& !gdp.getColomnName().equalsIgnoreCase("mobno")) {
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						pullreportGridColomns.add(gpv);
						System.out.println("pullreportGridColomns try>>>>>>>"
								+ pullreportGridColomns.size());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String beforeLeavePullReportHeader() {
		System.out.println("FetchviewAttendancetHeader");
		if (ValidateSession.checkSession()) {
			try {
				fdate = DateUtil.getNextDateAfter(-7);
				System.out.println("fdate :::" + fdate);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String viewLeavePullReport() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			System.out.println(" ViewEmfgfgailSetting      ");
			try {
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				List data = null;
				query1.append("select count(*) from instant_msg as ins  ");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				if (dataCount != null && dataCount.size() > 0) {
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column

					query1 = new StringBuilder();
					query1.append("  Select ins.id,ins.empName,ins.mobno,ins.IncomingSms,ins.reason,ins.description,ins.InComingDate,ins.InComingTime from leave_sms_Report as ins  ");
					
					if (getTdate()!=null && getFdate()!=null) 
					{
						query1.append("where  ins.InComingDate between '"
							+ getFdate()
							+ "' AND '"
							+ getTdate()
							+ "'");
					}
					
					if (getFieldser() != null
							&& !getFieldser().equalsIgnoreCase("-1")
							&& getFieldval() != null
							&& !getFieldval().equalsIgnoreCase("-1")) {
						query1.append(" where ins. " + getFieldser()
								+ " like '%" + getFieldval() + "%'");
						System.out.println("query1 ??????????   " + query1);
					}
					query1.append("  group by ins.id asc ");
					data = cbt.executeAllSelectQuery(query1.toString(),
							connectionSpace);
					System.out.println("query1 ??????????   " + query1);
					System.out.println("data >>>" + data);
					if (data != null && data.size() > 0) {
						List<String> fieldNames = new ArrayList<String>();
						fieldNames.add("id");
						fieldNames.add("empName");
						fieldNames.add("mobileNo");
						fieldNames.add("keyword");
						fieldNames.add("reason");
						fieldNames.add("description");
						fieldNames.add("date");
						fieldNames.add("time");
						System.out.println("fieldNames  :::: " + fieldNames);
						viewLeavePullData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						Object[] obdata1 = null;
						for (Iterator it = data.iterator(); it.hasNext();) {
							obdata1 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata1[k].equals("-1")) {
									one.put(fieldNames.get(k).toString(), "NA");
								} else {
									if (k == 0)
										one.put(fieldNames.get(k).toString(),
												(Integer) obdata1[k]);
									else {
										one.put(fieldNames.get(k).toString(),
												obdata1[k].toString());
									}
								}
							}
							Listhb.add(one);
						}
						setViewLeavePullData(Listhb);
						System.out.println("fieldNames  :::: " + fieldNames);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public Map<String, String> getDeptName() {
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public JSONObject getResponseDetailsJson() {
		return responseDetailsJson;
	}

	public void setResponseDetailsJson(JSONObject responseDetailsJson) {
		this.responseDetailsJson = responseDetailsJson;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public List<GridDataPropertyView> getReportGridColoumnNames() {
		return reportGridColoumnNames;
	}

	public void setReportGridColoumnNames(
			List<GridDataPropertyView> reportGridColoumnNames) {
		this.reportGridColoumnNames = reportGridColoumnNames;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<Object> getReportTotalView() {
		return reportTotalView;
	}

	public void setReportTotalView(List<Object> reportTotalView) {
		this.reportTotalView = reportTotalView;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getSubdeptId() {
		return SubdeptId;
	}

	public void setSubdeptId(String subdeptId) {
		SubdeptId = subdeptId;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public List<GridDataPropertyView> getPullreportGridColomns() {
		return pullreportGridColomns;
	}

	public void setPullreportGridColomns(
			List<GridDataPropertyView> pullreportGridColomns) {
		this.pullreportGridColomns = pullreportGridColomns;
	}

	public List<Object> getViewLeavePullData() {
		return viewLeavePullData;
	}

	public void setViewLeavePullData(List<Object> viewLeavePullData) {
		this.viewLeavePullData = viewLeavePullData;
	}

	public String getFieldser() {
		return fieldser;
	}

	public void setFieldser(String fieldser) {
		this.fieldser = fieldser;
	}

	public String getFieldval() {
		return fieldval;
	}

	public void setFieldval(String fieldval) {
		this.fieldval = fieldval;
	}
}