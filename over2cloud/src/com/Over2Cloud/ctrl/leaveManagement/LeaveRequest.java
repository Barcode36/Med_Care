package com.Over2Cloud.ctrl.leaveManagement;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
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
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LeaveRequest extends ActionSupport implements ServletRequestAware {

	static final Log log = LogFactory.getLog(LeaveRequest.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings("rawtypes")
	// private Map session = ActionContext.getContext().getSession();
	private List<ConfigurationUtilBean> requestColumnText = null;
	private List<ConfigurationUtilBean> requestColumnDate = null;
	private List<ConfigurationUtilBean> requestColumnTime = null;
	private List<ConfigurationUtilBean> requestColumnDropdown = null;
	private List<ConfigurationUtilBean> requestColumnOnDate = null;
	private List<ConfigurationUtilBean> responseColumnText = null;
	private List<ConfigurationUtilBean> responseColumnDropdown = null;
	private List<ConfigurationUtilBean> requestColumnRadio = null;
	private Map<String, String> employeeNames = null;

	private List leaveType = null;
	private String actionStatus;
	private String destination;
	private String dept;
	private String empname;
	private String empemail;
	private String empmob;
	private String id;
	private String accountid;
	private String userName;
	private String fdate;
	private String tdate;
	private String odate;
	private String ftime;
	private String ttime;
	private String reason;
	private String comment;
	private String name;
	private String modifyFlag;

	private String fromDate;
	private String toDate;
	private String minCount;
	private String maxCount;
	private String leaveID;
	private String leaveStatusTemp;
	private String approveBy;
	private String mode;
	private String date1;
	private String comments;
	private String flagtemp;
	
	private String fdatetemp;
	private String tdatetemp;
	private String odatetemp;
	private String ftimetemp;
	private String ttimetemp;
	private String storeFdate;
	private String storeTdate;
	private String storeOdate;
	private String approveOnTemp;

	private List<GridDataPropertyView> requestGridColomns = new ArrayList<GridDataPropertyView>();
	private String headerName;
	private HttpServletRequest request;
	private String mainHeaderName;
	private String deptOrSubDeptId;
	private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private Map<Integer, String> employeelist = new HashMap<Integer, String>();
	private String leaveLeft;
	private List<Object> viewRequest;
	private List<Object> viewResponse;
	private List<Object> viewResponse1;
	private Map<String, String> subDept_DeptLevelName = null;
	private Map<Integer, String> deptList = null;
	@SuppressWarnings("unused")
	private Map<Integer, String> serviceDeptList = null;
	private int noOfTotalWorkingDays;
	private double carryForward;
	private double extraWorking;
	private double totalDeduction;
	private double carryForwar4Month;
	private String validate;
	private double totalLeaveAllowed;
	private String LeaveNextMonth_leaves;
	private double totalLeaveAvailed;
	private double totalLeaveBalance;
	private double empLeaveBalance;
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
	// Grid column view
	private String oper;

	@SuppressWarnings("rawtypes")
	public String beforeLeaveAdd() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String empIdofuser = (String) session.get("empIdofuser");
				String userName = (String) session.get("uName");
				String level = null;
				if (empIdofuser == null
						|| empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				LeaveHelper LH = new LeaveHelper();
				// String userName = (String) session.get("uName");
				String empId = (String) session.get("empIdofuser").toString()
						.split("-")[1];

				String dataemp = LH.getContactId("HR", connectionSpace, empId);

				List<GridDataPropertyView> requestColumnList = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "leave_request_configuration");
				requestColumnText = new ArrayList<ConfigurationUtilBean>();
				requestColumnDate = new ArrayList<ConfigurationUtilBean>();
				requestColumnDropdown = new ArrayList<ConfigurationUtilBean>();
				requestColumnTime = new ArrayList<ConfigurationUtilBean>();
				requestColumnOnDate = new ArrayList<ConfigurationUtilBean>();
				requestColumnRadio = new ArrayList<ConfigurationUtilBean>();
				employeeNames = new LinkedHashMap<String, String>();
				empLeaveBalance = getEmpLeaveBalance(connectionSpace, dataemp);
				// System.out.println("empLeaveBalance" +empLeaveBalance);
				LeaveNextMonth_leaves = getBalanceLeave(connectionSpace);
				totalLeaveAllowed = getLeaveGranted(connectionSpace);
				totalLeaveAvailed = getLeaveAvailed(connectionSpace);
				if (requestColumnList != null && requestColumnList.size() > 0) {
					ConfigurationUtilBean cub = null;
					for (GridDataPropertyView gdp : requestColumnList) {
						cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"comment")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("flag")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("name")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"mobno")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"email")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							requestColumnText.add(cub);
						} else if (gdp.getColType().equalsIgnoreCase("Time")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							requestColumnTime.add(cub);
						} else if (gdp.getColType().equalsIgnoreCase("R")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							requestColumnRadio.add(cub);
						} else if (gdp.getColType().equalsIgnoreCase("Date")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							requestColumnDate.add(cub);
						} else if (gdp.getColType().equalsIgnoreCase("D")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"status")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							requestColumnDropdown.add(cub);
						}
					}
					empId = empIdofuser.split("-")[1].trim();
					/*
					 * Stringqueydeg=
					 * "SELECT d.levelofhierarchy FROM designation as d INNER JOIN employee_basic as eb on eb.designation=d.id WHERE eb.id='"
					 * +empId+"'"; List
					 * degLevel=cbt.executeAllSelectQuery(queydeg,
					 * connectionSpace); if (degLevel!=null &&
					 * degLevel.size()>0) { level=degLevel.get(0).toString(); }
					 */

					leaveType = new ArrayList<String>();
					List list = cbt.executeAllSelectQuery(
							"SELECT ltype FROM leave_type", connectionSpace);
					if (list != null && list.size() > 0) {
						setLeaveType(list);
					}

					LH = new LeaveHelper();
					String[] empdata = LH.getEmpDetailsByUserName("HR",
							userName, connectionSpace);
					if (empdata != null
							&& !empdata.toString().equalsIgnoreCase("")) {
						StringBuilder query = new StringBuilder();
						query.append("SELECT cc.id,emp.empName FROM employee_basic AS emp  ");
						query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id  ");
						query.append(" WHERE cc.forDept_id="
								+ empdata[4]
								+ " AND cc.moduleName='HR' AND cc.level>(SELECT level FROM compliance_contacts WHERE id="
								+ empdata[0] + " AND forDept_id=" + empdata[4]
								+ ")");
						query.append(" and emp.flag=0 and cc.work_status=0 ");
						System.out.println("validat BYYYY >>>>>> " + query);
						List employeeNames = cbt.executeAllSelectQuery(
								query.toString(), connectionSpace);
						if (employeeNames != null && employeeNames.size() > 0) {
							Object[] object3 = null;
							for (Iterator iterator2 = employeeNames.iterator(); iterator2
									.hasNext();) {
								object3 = (Object[]) iterator2.next();
								employeelist.put((Integer) object3[0],
										object3[1].toString());
								System.out.println("employeelist >.... "
										+ employeelist);
							}
						}
					}
					returnResult = SUCCESS;
				}
			} catch (Exception e) {
				log.error(
						"Problem in method beforeLeaveAdd of class "
								+ getClass() + " on "
								+ DateUtil.getCurrentDateIndianFormat()
								+ " at " + DateUtil.getCurrentTimewithSeconds(),
						e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public String createLeaveRequest() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				String countryId = (String) session.get("countryid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String userName = (String) session.get("uName");
				LeaveHelper LM = new LeaveHelper();
				String[] loggdData = LM.getEmpDetailsByUserName("HR", userName,
						connectionSpace);

				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "leave_request_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					boolean flag = false;
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName) {
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date1"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("flag"))
							flag = true;
					}
					cbt.createTable22("leave_request", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {
						Iterator it = requestParameterNames.iterator();
						String flag1 = request.getParameter("leaveunit");
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null) {
								if (!parmName.equalsIgnoreCase("leaveunit")
										&& !parmName
												.equalsIgnoreCase("deptHierarchy")
										&& !parmName
												.equalsIgnoreCase("leaveLft")
										&& !parmName
												.equalsIgnoreCase("deptname")
										&& !parmName
												.equalsIgnoreCase("subdept")
												
										&& !parmName
												.equalsIgnoreCase("empname")) {
									ob = new InsertDataTable();
									if (flag1 != null) {
										if (flag1.equalsIgnoreCase("days")) {
											if (parmName
													.equalsIgnoreCase("fdate")
													|| parmName
															.equalsIgnoreCase("tdate")) {
												ob.setColName(parmName);
												ob.setDataName(DateUtil
														.convertDateToUSFormat(paramValue));
												insertData.add(ob);
											} else {
												ob.setColName(parmName);
												ob.setDataName(paramValue);
												insertData.add(ob);
											}
										} else if (flag1
												.equalsIgnoreCase("hour")) {
											if (parmName
													.equalsIgnoreCase("odate")) {
												ob.setColName(parmName);
												ob.setDataName(DateUtil
														.convertDateToUSFormat(paramValue));
												insertData.add(ob);
											} else {
												ob.setColName(parmName);
												ob.setDataName(paramValue);
												insertData.add(ob);
											}
										}
									} else {
										ob.setColName(parmName);
										ob.setDataName("pending");
										insertData.add(ob);
									}
									

								}
								
								
								if (parmName.equalsIgnoreCase("empname")) {
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(getEmpname() + ",");
									insertData.add(ob);
								}
								if (parmName.equalsIgnoreCase("leaveunit")) {
									ob = new InsertDataTable();
									ob.setColName("leaveunit");
									ob.setDataName(loggdData[0]);

									insertData.add(ob);
								}
							}
						}
						if (userTrue) {
							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(DateUtil.makeTitle((String) session
									.get("uName")));
							insertData.add(ob);
						}

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Pending");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("approveBy");
						ob.setDataName("No action");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("approveOn");
						ob.setDataName("NA");
						insertData.add(ob);

						if (flag) {
							ob = new InsertDataTable();
							ob.setColName("flag");
							ob.setDataName(flag1);
							insertData.add(ob);
						}
						if (dateTrue) {
							ob = new InsertDataTable();
							ob.setColName("date1");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);
						}
						if (timeTrue) {
							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);
						}

						ob = new InsertDataTable();
						ob.setColName("mode");
						ob.setDataName("Online");
						insertData.add(ob);
					}
					status = cbt.insertIntoTable("leave_request", insertData,
							connectionSpace);
					String empIdofuser = (String) session.get("empIdofuser");
					if (empIdofuser == null
							|| empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;

					String validate[] = getEmpname().trim().split(",");
					for (int i = 0; i < validate.length; i++) {
						List<String> idss = new ArrayList<String>();
						Map<String, String> dataMap = new LinkedHashMap<String, String>();

						LeaveHelper LH = new LeaveHelper();
						String empId = (String) session.get("empIdofuser")
								.toString().split("-")[1];
						String dataemp = LH.getContactId("HR", connectionSpace,
								empId);

						String validateData[] = LM.getEmployeeDetails(
								validate[i], "HR", connectionSpace);
						empLeaveBalance = getEmpLeaveBalance(connectionSpace,
								dataemp);
						System.out.println(" empLeaveBalance ::::  "
								+ empLeaveBalance);
						if (validateData != null) {
							String fdate = request.getParameter("fdate");
						String fromDate=DateUtil.convertDateToUSFormat(fdate);
							String tdate = request.getParameter("tdate");
							String toDate=DateUtil.convertDateToUSFormat(tdate);
							System.out.println("tdatetdatetdatetdate :::::  "+toDate);
							String odate = request.getParameter("odate");
							String ftime = request.getParameter("ftime");
							String ttime = request.getParameter("ttime");
							String leaveUnit = request
									.getParameter("leaveunit");
							String reason = request.getParameter("reason");
							String leaveStatus = request
									.getParameter("leavestatus");
							//String totalLeaverequest = DateUtil.findDaysForDates(fromDate, tdate);
							
							int valueAbsent;
							double valueHalfday;
							String query6 = null;
							cbt = new CommonConFactory().createInterface();
							String date = DateUtil.getCurrentDateUSFormat()
									.split("-")[0]
									+ "-"
									+ DateUtil.getCurrentDateUSFormat().split(
											"-")[1];
							System.out.println("dat ::" + date);

							String query2 = "select count(*) from attendence_record where empname IN ("
									+ dataemp
									+ ") AND date1 LIKE '%"
									+ date
									+ "%'  AND status='0'";
							// System.out.println("query2 ::  "+query2);
							String query3 = "select count(*) from attendence_record where empname IN ("
									+ dataemp
									+ ") AND date1 LIKE '%"
									+ date
									+ "%'  AND status='0.5'";
							// System.out.println("query3::  "+query3);
							List leaveAbsentDetails = cbt
									.executeAllSelectQuery(query2,
											connectionSpace);
							List leaveHalfdayDetails = cbt
									.executeAllSelectQuery(query3,
											connectionSpace);
							if (leaveAbsentDetails != null
									&& leaveAbsentDetails.size() > 0) {
								valueAbsent = Integer
										.parseInt(leaveAbsentDetails.get(0)
												.toString());
							} else {
								valueAbsent = 0;
							}
							if (leaveHalfdayDetails != null
									&& leaveHalfdayDetails.size() > 0) {
								valueHalfday = (Integer
										.parseInt(leaveHalfdayDetails.get(0)
												.toString())) * 0.5;
							} else {
								valueHalfday = 0 * 0.5;
							}
							double finalTotalLeaves = valueAbsent
									+ valueHalfday;
							String query1 = "select sum(eworking) from attendence_record where empname IN ("
									+ dataemp
									+ ") AND date1 LIKE  '%"
									+ date
									+ "%' ";
							List resultExtraWorking = cbt
									.executeAllSelectQuery(query1,
											connectionSpace);

							if (resultExtraWorking != null
									&& resultExtraWorking.size() > 0) {
								if (resultExtraWorking.get(0) != null) {
									extraWorking = Double
											.parseDouble(resultExtraWorking
													.get(0).toString());
								} else {
									extraWorking = 0;
								}

							} else {
								extraWorking = 0;
							}
							String[] data = new LeaveHelper()
									.getEmployeeDetails(getEmpname(), "HR",
											connectionSpace);
							if (data[2].equalsIgnoreCase("1")) {
								query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee'";

							} else {
								query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent'";

							}
							double cf = 0;
							String beforeDate = DateUtil
									.getLastDateFromLastDate(-1,
											DateUtil.getCurrentDateUSFormat());
							String str[] = beforeDate.split("-");
							String finalString = str[0] + "-" + str[1];
							String query7 = "select lnextmonth from report_data where empname IN ("
									+ dataemp
									+ ") and tdate LIKE '%"
									+ finalString + "%'";
							System.out.println("QUERY CARRY FORWARD ::  "
									+ query7);
							List getcarryForward = cbt.executeAllSelectQuery(
									query7, connectionSpace);
							if (getcarryForward != null
									&& getcarryForward.size() > 0) {
								if (getcarryForward.get(0) != null) {
									cf = Double.parseDouble(getcarryForward
											.get(0).toString());
									String tot = new Double(cf).toString();
									if (data[2].equalsIgnoreCase("1")) {
										carryForward = cf;
									} else {
										if (tot != null) {
											if (tot.startsWith("-")) {
												carryForward = 0;
											} else {
												carryForward = cf;
											}
										}
									}
								} else {
									cf = 0;
									carryForward = cf;
								}
							}
							double finalLeaveGranted = 0;
							List leaveGrant = cbt.executeAllSelectQuery(query6,
									connectionSpace);
							if (leaveGrant != null && leaveGrant.size() > 0) {
								finalLeaveGranted = Double
										.parseDouble(leaveGrant.get(0)
												.toString());
							}
							// System.out.println("extraworking====" +
							// extraWorking);
							// System.out.println("final leave grntedd===" +
							// finalLeaveGranted);
							// System.out.println("cf====" + carryForward);
							// System.out.println("final total leaves====" +
							// finalTotalLeaves);

							totalDeduction = (finalLeaveGranted + extraWorking + carryForward)
									- finalTotalLeaves;
							System.out.println("totalDeduction   "
									+ totalDeduction);
							String revertSMS = null;
							if (status) {
								GenericMailSender mail = new GenericMailSender();
								int maxid = new createTable().getMaxId(
										"leave_request", connectionSpace);
								// ////System.out.println(" maxid #####"
								// +maxid);
								if (leaveUnit.equals("Days")) 
								{
									int TotalNonWorkingDay = new WorkingHourHelper().getTotalNonWorkingDay(fromDate, connectionSpace, cbt, "HR",toDate);
									System.out.println("TotalNonWorkingDay :::::  "+TotalNonWorkingDay);
									@SuppressWarnings("static-access")
									int totalworkingday=new DateUtil().getNoOfDays(toDate, fromDate);
									int nototalworkingday=totalworkingday+1;
									int totalcountLeavedays=nototalworkingday-TotalNonWorkingDay;
									String mailBody = getMailBody(validateData[0], fdate, tdate,maxid, loggdData[1], countryId+ "-" + accountID, reason,
											leaveStatus, empLeaveBalance,totalcountLeavedays,(String) session.get("uName"));
									new MsgMailCommunication().addMail(
											validateData[0], "",
											validateData[1],
											"Leave Request by " + loggdData[1]
													+ "", mailBody, "",
											"Pending", "0", "", "HR");
									// mail.sendMail("sumitib@dreamsol.biz","Leave Request by "+loggdData[1]+"",
									// mailBody, "", "smtp.gmail.com", "465",
									// "sumitib@dreamsol.biz","amity1988",connectionSpace);

								}
								if (leaveUnit.equals("Hour")) {
									String mailBody1 = getMailBody1(
											validateData[0], odate, ftime,
											ttime, maxid, loggdData[1],
											countryId + "-" + accountID,
											reason, leaveStatus,
											empLeaveBalance,
											(String) session.get("uName"));
									new MsgMailCommunication().addMail(
											validateData[0], "",
											validateData[1],
											"Leave Request by " + loggdData[1]
													+ "", mailBody1, "",
											"Pending", "0", "", "HR");
									// mail.sendMail("sumitib@dreamsol.biz",
									// "Leave Request by "+loggdData[1]+"",
									// mailBody1, "", "smtp.gmail.com", "465",
									// "sumitib@dreamsol.biz", "amity1988");
								}
								returnResult = SUCCESS;
							} else {
								addActionMessage("Oops There is some error in data!!!!");
							}
						}
					}
				}
				addActionMessage("Leave Request Sent Successfully!!!");
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getMailBody(String validate, String fdate, String tdate,
			int maxid, String loggedName, String accounId, String reason,
			String leaveStatus, double totalDeduction,
			int totalLeaverequest, String userName) {
		System.out.println("userName TESTTTT   " + userName);
		List<AttendancePojo> dataList = new ArrayList<AttendancePojo>();
		AttendancePojo attP = null;

		StringBuilder mailText = new StringBuilder("");
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("ip  " +ip);
			int portNo = req.getServerPort();
			String URL = "<a href=http://"
					+ ip
					//192.168.1.116
					+ ":"
					+ portNo
					+ "/over2cloud/view/Over2Cloud/leaveManagement/addResponse4Request.action?id="
					+ maxid + "&userName=" + userName + "&accountid="
					+ accounId + "&validate=" + validate.replace(" ", "%20")
					+ ">Click Here!!</a>";
			// System.out.println("URL  " +URL);
			mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='3'><b>Dear "
					+ validate
					+ ",</b></font></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='2'>Please refer below a leave request to be approved/ disapproved by you:</font></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Requested By :</font></td> "
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ loggedName
					+ "&nbsp;</font></td>"
					+ "</tr>"
					+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Left:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ totalDeduction
					+ "&nbsp;</font></td>"
					+ "</tr>"
					+ "</tr>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Total leave Request:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ totalLeaverequest
					+ "&nbsp;</font></td>"
					+ "</tr>"
					+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ fdate
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ tdate
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Type:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.makeTitle(leaveStatus)
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.makeTitle(reason)
					+ "&nbsp;</font></td></tr>"
					+ "   </table>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td>You are requested to take an appropriate action by clicking on below link: </td></tr>");
			mailText.append(URL);
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
			mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
			mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
			mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
			mailText.append("<tr><td><div align='justify'><font size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr>");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return mailText.toString();
	}

	public String getMailBody1(String validate, String odate, String ftime,
			String ttime, int maxid, String username, String accountId,
			String reason, String leaveStatus, Double leaveLeft, String userName) {
		System.out.println("leaveLeft" + leaveLeft);
		StringBuilder mailText = new StringBuilder("");
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			String ip = InetAddress.getLocalHost().getHostAddress();
			int portNo = req.getServerPort();
			String URL = "<a href=http://"
					+ ip
					+ ":"
					+ portNo
					+ "/over2cloud/view/Over2Cloud/leaveManagement/addResponse4Request.action?id="
					+ maxid + "&userName=" + userName + "&accountid="
					+ accountId + "&validate=" + validate.replace(" ", "%20")
					+ ">Click Here!!</a>";
			// //System.out.println("userName :::" +userName);
			// //System.out.println("accountid :::" +accountid);
			mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='3'><b>Dear "
					+ validate
					+ ",</b></font></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='2'>Please refer below a leave request to be approved/ disapproved by you:</font></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Requested By :</font></td> "
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ username
					+ "&nbsp;</font></td>"

					+ "</tr><tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Left:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ leaveLeft
					+ "&nbsp;</font></td></tr>"

					+ "</tr><tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>On Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ odate
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Time:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ ftime
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Time:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ ttime
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Type:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.makeTitle(leaveStatus)
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.makeTitle(reason)
					+ "&nbsp;</font></td></tr>"
					+ "   </table>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td>You are requested to take an appropriate action by clicking on below link: </td></tr>");
			mailText.append(URL);
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
			mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
			mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
			mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
			mailText.append("<tr><td><div align='justify'><font size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the //System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr>");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return mailText.toString();
	}

	public String beforeRequestView() {
		boolean sessionFlag = ValidateSession.checkSession();
		String tLeaves = null;
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

				String dataemp = LH.getContactId("HR", connectionSpace, empId);

				empLeaveBalance = getEmpLeaveBalance(connectionSpace, dataemp);

				LeaveNextMonth_leaves = getBalanceLeave(connectionSpace);
				totalLeaveAllowed = getLeaveGranted(connectionSpace);
				totalLeaveAvailed = getLeaveAvailed(connectionSpace);
				// totalLeaveBalance=getEmpLeaveBalanceForMail(connectionSpace);
				System.out.println("empLeaveBalance" + empLeaveBalance);
				System.out.println("LeaveNextMonth_leaves" + LeaveNextMonth_leaves);
				
				fdate=DateUtil.getNextDateAfter(-7);
				tdate=DateUtil.getCurrentDateIndianFormat();
				
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Leave Request >> View");
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
		requestGridColomns.add(gpv);
		try {
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_leave_request_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"leave_request_configuration");
			if (statusColName != null && statusColName.size() > 0) {
				for (GridDataPropertyView gdp : statusColName) {
					if (!gdp.getColomnName().equalsIgnoreCase("userName")
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
						requestGridColomns.add(gpv);
					}
				}

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mode");
				gpv.setHeadingName("Mode");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				requestGridColomns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("approveBy");
				gpv.setHeadingName("Approve by");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				requestGridColomns.add(gpv);

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String getBalanceLeave(SessionFactory connectionSpace) {
		String counter = null;
		try {

			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace1 = (SessionFactory) session
					.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			LeaveHelper LH = new LeaveHelper();

			String empId = (String) session.get("empIdofuser").toString()
					.split("-")[1];
			String dataemp = LH.getContactId("HR", connectionSpace1, empId);
			cbt = new CommonConFactory().createInterface();
			String beforeDate = DateUtil.getLastDateFromLastDate(-1,
					DateUtil.getCurrentDateUSFormat());
			String str[] = beforeDate.split("-");
			String finalString = str[0] + "-" + str[1];
			String query7 = "select lnextmonth from report_data where empname IN ("
					+ dataemp + ") and tdate LIKE '%" + finalString + "%'";

			List dataList = cbt.executeAllSelectQuery(query7.toString(),
					connectionSpace1);
			if (dataList != null && dataList.size() > 0
					&& dataList.get(0) != null) {
				counter = dataList.get(0).toString();
			}

			return counter;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}

	@SuppressWarnings("rawtypes")
	public double getLeaveGranted(SessionFactory connectionSpace) {
		Map session = ActionContext.getContext().getSession();
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

	public double getLeaveAvailed(SessionFactory connectionSpace) {
		double finalTotalLeaves = 0;
		try {
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace1 = (SessionFactory) session
					.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			LeaveHelper LH = new LeaveHelper();
			String date = DateUtil.getCurrentDateUSFormat().split("-")[0];
			// System.out.println("date :::" +date);
			String empId = (String) session.get("empIdofuser").toString()
					.split("-")[1];
			String dataemp = LH.getContactId("HR", connectionSpace1, empId);
			cbt = new CommonConFactory().createInterface();

			String query2 = "select count(*) from attendence_record where empname IN ("
					+ dataemp
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0'  AND attendence='Absent' ";
			// System.out.println("query2 ::  " + query2);
			String query3 = "select count(*) from attendence_record where empname IN ("
					+ dataemp
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0.5'  AND attendence='Half Day' ";

			// System.out.println("query3 ::::::  " + query3);
			int valueAbsent;
			double valueHalfday;
			List leaveAbsentDetails = cbt.executeAllSelectQuery(query2,
					connectionSpace);
			List leaveHalfdayDetails = cbt.executeAllSelectQuery(query3,
					connectionSpace);
			if (leaveAbsentDetails != null && leaveAbsentDetails.size() > 0) {
				valueAbsent = Integer.parseInt(leaveAbsentDetails.get(0)
						.toString());
			} else {
				valueAbsent = 0;
			}
			if (leaveHalfdayDetails != null && leaveHalfdayDetails.size() > 0) {
				valueHalfday = (Integer.parseInt(leaveHalfdayDetails.get(0)
						.toString())) * 0.5;
			} else {
				valueHalfday = 0 * 0.5;
			}
			double finalTotalLeaves1 = valueAbsent + valueHalfday;

			// System.out.println("finalTotalLeaves1 <<<<<<<<<<<<"
			// +finalTotalLeaves1);
			return finalTotalLeaves1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalTotalLeaves;
	}

	public double getEmpLeaveBalance(SessionFactory connectionSpace,
			String empId) {
		double leaveBalance = 0;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			LeaveHelper LH = new LeaveHelper();

			String dataemp = LH.getContactId("HR", connectionSpace, empId);
			cbt = new CommonConFactory().createInterface();

			String countHoliday = null;
			int valueAbsent;
			double valueHalfday;
			String[] data = new LeaveHelper().getEmployeeDetails(empId, "HR",
					connectionSpace);
			System.out.println("data ::  " + data);
			String date = DateUtil.getCurrentDateUSFormat().split("-")[0] + "-"
					+ DateUtil.getCurrentDateUSFormat().split("-")[1];

			double cf = 0;
			String beforeDate = DateUtil.getLastDateFromLastDate(-1,
					DateUtil.getCurrentDateUSFormat());
			String str[] = beforeDate.split("-");
			String finalString = str[0] + "-" + str[1];
			String query7 = "select lnextmonth from report_data where empname IN ("
					+ empId + ") and tdate LIKE '%" + finalString + "%'";
			System.out.println("QUERY CARRY FORWARD ::  " + query7);
			List getcarryForward = cbt.executeAllSelectQuery(query7,
					connectionSpace);
			if (getcarryForward != null && getcarryForward.size() > 0) {
				if (getcarryForward.get(0) != null) {
					cf = Double.parseDouble(getcarryForward.get(0).toString());
					String tot = new Double(cf).toString();
					System.out.println("data[2] ::  " + data[2]);
					if (data[2].equalsIgnoreCase("1")) {
						carryForward = cf;
					} else {
						if (tot != null) {
							if (tot.startsWith("-")) {
								carryForward = 0;
							} else {
								carryForward = cf;
							}
						}
					}
				} else {
					cf = 0;
					carryForward = cf;
				}
			}

			String query6 = null;
			int totalDays = DateUtil.countWeekendDays(DateUtil
					.getCurrentDateUSFormat());
			String query = "select count(*) from holiday_list where fdate between '"
					+ DateUtil.convertDateToUSFormat(getFdate())
					+ "' AND '"
					+ DateUtil.convertDateToUSFormat(getTdate()) + "'";
			List result = cbt.executeAllSelectQuery(query, connectionSpace);
			if (result != null && result.size() > 0) {
				countHoliday = result.get(0).toString();
			}
			noOfTotalWorkingDays = totalDays - Integer.parseInt(countHoliday);

			String query1 = "select sum(eworking) from attendence_record where empname IN ("
					+ empId + ") AND date1 LIKE  '%" + date + "%' ";
			 System.out.println("query1 ::::::  " + query1);
			List resultExtraWorking = cbt.executeAllSelectQuery(query1,
					connectionSpace);

			if (resultExtraWorking != null && resultExtraWorking.size() > 0) {
				extraWorking = Double.parseDouble(resultExtraWorking.get(0)
						.toString());
			} else {
				extraWorking = 0;
			}

			String query2 = "select count(*) from attendence_record where empname IN ("
					+ empId
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0'  AND attendence='Absent' ";
			 System.out.println("query2 ::  " + query2);
			String query3 = "select count(*) from attendence_record where empname IN ("
					+ empId
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0.5'  AND attendence='Half Day' ";

			 System.out.println("query3 ::::::  " + query3);

			List leaveAbsentDetails = cbt.executeAllSelectQuery(query2,
					connectionSpace);
			List leaveHalfdayDetails = cbt.executeAllSelectQuery(query3,
					connectionSpace);
			if (leaveAbsentDetails != null && leaveAbsentDetails.size() > 0) {
				valueAbsent = Integer.parseInt(leaveAbsentDetails.get(0)
						.toString());
			} else {
				valueAbsent = 0;
			}
			if (leaveHalfdayDetails != null && leaveHalfdayDetails.size() > 0) {
				valueHalfday = (Integer.parseInt(leaveHalfdayDetails.get(0)
						.toString())) * 0.5;
			} else {
				valueHalfday = 0 * 0.5;
			}
			double finalTotalLeaves = valueAbsent + valueHalfday;

			// System.out.println("final total leaves====" + finalTotalLeaves);
			if (data[2].equalsIgnoreCase("1")) {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee'";

			} else {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent'";

			}
			// System.out.println("query6 :else::::::  " + query6);

			double finalLeaveGranted = 0;
			List leaveGrant = cbt
					.executeAllSelectQuery(query6, connectionSpace);
			if (leaveGrant != null && leaveGrant.size() > 0) {
				finalLeaveGranted = Double.parseDouble(leaveGrant.get(0)
						.toString());
			}
			 System.out.println("extraworking====" + extraWorking);
			System.out.println("final leave grntedd===" + finalLeaveGranted);
			 System.out.println("cf====" + carryForward);
			 System.out.println("final total leaves====" + finalTotalLeaves);

			leaveBalance = (finalLeaveGranted + extraWorking + carryForward)
					- finalTotalLeaves;

			return leaveBalance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveBalance;
	}

	public double getEmpLeaveBalanceForMail(SessionFactory connectionSpace,
			String empId) {
		double leaveBalance = 0;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			LeaveHelper LH = new LeaveHelper();

			String dataemp = LH.getContactId("HR", connectionSpace, empId);
			cbt = new CommonConFactory().createInterface();

			String countHoliday = null;
			int valueAbsent;
			double valueHalfday;
			String[] data = new LeaveHelper().getEmployeeDetails(empId, "HR",
					connectionSpace);
			String date = DateUtil.getCurrentDateUSFormat().split("-")[0] + "-"
					+ DateUtil.getCurrentDateUSFormat().split("-")[1];

			double cf = 0;
			String beforeDate = DateUtil.getLastDateFromLastDate(-1,
					DateUtil.getCurrentDateUSFormat());
			String str[] = beforeDate.split("-");
			String finalString = str[0] + "-" + str[1];
			String query7 = "select lnextmonth from report_data where empname IN ("
					+ empId + ") and tdate LIKE '%" + finalString + "%'";
			System.out.println("QUERY CARRY FORWARD ::  " + query7);
			List getcarryForward = cbt.executeAllSelectQuery(query7,
					connectionSpace);
			if (getcarryForward != null && getcarryForward.size() > 0) {
				if (getcarryForward.get(0) != null) {
					cf = Double.parseDouble(getcarryForward.get(0).toString());
					String tot = new Double(cf).toString();
					if (data[2].equalsIgnoreCase("1")) {
						carryForward = cf;
					} else {
						if (tot != null) {
							if (tot.startsWith("-")) {
								carryForward = 0;
							} else {
								carryForward = cf;
							}
						}
					}
				} else {
					cf = 0;
					carryForward = cf;
				}
			}

			String query6 = null;
			int totalDays = DateUtil.countWeekendDays(DateUtil
					.getCurrentDateUSFormat());
			String query = "select count(*) from holiday_list where fdate between '"
					+ DateUtil.convertDateToUSFormat(getFdate())
					+ "' AND '"
					+ DateUtil.convertDateToUSFormat(getTdate()) + "'";
			List result = cbt.executeAllSelectQuery(query, connectionSpace);
			if (result != null && result.size() > 0) {
				countHoliday = result.get(0).toString();
			}
			noOfTotalWorkingDays = totalDays - Integer.parseInt(countHoliday);

			String query1 = "select sum(eworking) from attendence_record where empname IN ("
					+ empId + ") AND date1 LIKE  '%" + date + "%' ";
			// System.out.println("query1 ::::::  " + query1);
			List resultExtraWorking = cbt.executeAllSelectQuery(query1,
					connectionSpace);

			if (resultExtraWorking != null && resultExtraWorking.size() > 0) {
				extraWorking = Double.parseDouble(resultExtraWorking.get(0)
						.toString());
			} else {
				extraWorking = 0;
			}

			String query2 = "select count(*) from attendence_record where empname IN ("
					+ empId
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0'  AND attendence='Absent' ";
			// System.out.println("query2 ::  " + query2);
			String query3 = "select count(*) from attendence_record where empname IN ("
					+ empId
					+ ") AND date1 LIKE '%"
					+ date
					+ "%'  AND status='0.5'  AND attendence='Half Day' ";

			// System.out.println("query3 ::::::  " + query3);

			List leaveAbsentDetails = cbt.executeAllSelectQuery(query2,
					connectionSpace);
			List leaveHalfdayDetails = cbt.executeAllSelectQuery(query3,
					connectionSpace);
			if (leaveAbsentDetails != null && leaveAbsentDetails.size() > 0) {
				valueAbsent = Integer.parseInt(leaveAbsentDetails.get(0)
						.toString());
			} else {
				valueAbsent = 0;
			}
			if (leaveHalfdayDetails != null && leaveHalfdayDetails.size() > 0) {
				valueHalfday = (Integer.parseInt(leaveHalfdayDetails.get(0)
						.toString())) * 0.5;
			} else {
				valueHalfday = 0 * 0.5;
			}
			double finalTotalLeaves = valueAbsent + valueHalfday;

			// System.out.println("final total leaves====" + finalTotalLeaves);
			if (data[2].equalsIgnoreCase("1")) {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee'";

			} else {
				query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent'";

			}
			// System.out.println("query6 :else::::::  " + query6);

			double finalLeaveGranted = 0;
			List leaveGrant = cbt
					.executeAllSelectQuery(query6, connectionSpace);
			if (leaveGrant != null && leaveGrant.size() > 0) {
				finalLeaveGranted = Double.parseDouble(leaveGrant.get(0)
						.toString());
			}
			// System.out.println("extraworking====" + extraWorking);
			// System.out.println("final leave grntedd===" + finalLeaveGranted);
			// System.out.println("cf====" + carryForward);
			// System.out.println("final total leaves====" + finalTotalLeaves);

			leaveBalance = (finalLeaveGranted + extraWorking + carryForward)
					- finalTotalLeaves;

			return leaveBalance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveBalance;
	}

	@SuppressWarnings("rawtypes")
	public String getViewInGridRequest()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID=(String)session.get("accountid");
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=(String)session.get("uName");
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
									return ERROR;
				String empId = empIdofuser.split("-")[1].trim();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				query.append("select count(*) from leave_request");
				List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null&&dataCount.size()>0)
				{
					BigInteger count=new BigInteger("1");
					Object obdata=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					query.setLength(0);    
					//getting the list of colmuns
					query.append("SELECT ");
					List fieldNames=Configuration.getColomnList("mapped_leave_request_configuration", accountID, connectionSpace, "leave_request_configuration");
					int i=0;
					if(fieldNames!=null&&fieldNames.size()>0)
					{
						Object obdata1=null;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dyanamic query based on selected fields
							    obdata1=(Object)it.next();
							    if(obdata1!=null)
							    {
							    	if(i<fieldNames.size()-1)
								    	query.append(obdata1.toString()+",");
								    else
								    	query.append(obdata1.toString());
							    }
							    i++;
							 }
						}
					
				query.append(" FROM leave_request");
			    query.append("  WHERE userName='"+userName+"'");
			    System.out.println("actionStatus >>>>>>>   "   +actionStatus);
			    if (actionStatus!=null && actionStatus.equalsIgnoreCase("ALL"))
				{
			    	System.out.println("INSIDE actionStatus IF");
				}
				else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Approve"))
				{
					System.out.println("INSIDE actionStatus ELSE");
					query.append(" AND status ='Approve' ");
				}
				else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Pending"))
				{
					System.out.println("INSIDE actionStatus ELSE");
					query.append(" AND status ='Pending' ");
				}
				else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Disapprove"))
				{
					System.out.println("INSIDE actionStatus ELSE");
					query.append(" AND status ='Disapprove' ");
				}
			    System.out.println("mode >>>>>>>   "   +mode);
			    if (mode!=null && mode.equalsIgnoreCase("ALL"))
				{
			    	System.out.println("INSIDE actionStatus IF");
				}
			    else if(mode!=null && mode.equalsIgnoreCase("online"))
			    {
			    	query.append(" AND mode ='online' ");	
			    }
			    else if(mode!=null && mode.equalsIgnoreCase("sms"))
			    {
			    	query.append(" AND mode ='sms' ");
			    }
			    if (getTdate()!=null && getFdate()!=null)
			    {
			    	query.append("and  date1 between '"
							+ DateUtil.convertDateToUSFormat(getFdate())
							+ "' AND '"
							+ DateUtil.convertDateToUSFormat(getTdate())
							+ "'");
			    	
			    }
				System.out.println("query TESTTT " +query);
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and ");
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
					}
				}
				List  dataCount1=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				query.setLength(0);
				if(dataCount1!=null && dataCount1.size()>0) 
				{
						StringBuilder dataList;
						viewRequest=new ArrayList<Object>();
						List<Object> Listhb=new ArrayList<Object>();
						Object[] obdata1=null;
						for(Iterator it=dataCount1.iterator(); it.hasNext();) 
						{
							
							dataList=new StringBuilder();
							obdata1=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)  
							{
								if(obdata1[k]!=null)       
								{
									if(k==0)
										one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									else
									{
										if(fieldNames.get(k).equals("empname"))
										{
											query.setLength(0);
											query.append("SELECT emp.empName FROM employee_basic AS emp  ");
											query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id  ");
											query.append(" WHERE cc.moduleName='LM' ");
											query.append(" and cc.id IN("+ obdata1[k].toString().substring(0, obdata1[k].toString().length()-1)+")");
											//System.out.println("quebnbxxxxxxbnnnnnnnnnnnnry2:::::::::" +query);
										List empdata=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
										    for (Iterator iterator = empdata.iterator(); iterator.hasNext();) 
										    {
												Object object = (Object) iterator.next();
												if (object!=null) 
												{
													dataList.append(object.toString()+",");
												}
											}
										    if (dataList!=null && dataList.length()>0) 
										    {
										    	one.put(fieldNames.get(k).toString(),dataList.substring(0, dataList.length()-1));
											}
										    query.setLength(0);
										}
									
										else if(fieldNames.get(k).equals("fdate") || fieldNames.get(k).equals("tdate") || fieldNames.get(k).equals("odate") || fieldNames.get(k).equals("date1"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else 
										{
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}
									}
								 }
								 else if(obdata1[k]==null)
								 {
							        if(k==0)
									    one.put(fieldNames.get(k).toString(),"NA");
								    else
									    one.put(fieldNames.get(k).toString(), "NA");
								 }
							}
							Listhb.add(one);
						}
						setViewRequest(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					
			}
			}
				return SUCCESS;
		}
	    catch(Exception e)
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

	@SuppressWarnings("unchecked")
	public String viewInGridResponse() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				StringBuilder query = new StringBuilder("");
				query1.append("select count(*) from leave_request");
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					query.append("SELECT ");
					List fieldNames = Configuration.getColomnList(
							"mapped_leave_request_configuration", accountID,
							connectionSpace, "leave_request_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0) {
						Object obdata1 = null;
						for (Iterator it = fieldNames.iterator(); it.hasNext();) {
							// generating the dyanamic query based on selected
							// fields
							obdata1 = (Object) it.next();
							if (obdata1 != null) {
								if (i < fieldNames.size() - 1)
									query.append(obdata1.toString() + ",");
								else
									query.append(obdata1.toString());
							}
							i++;
						}
					}
				}
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null
						|| empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				String empQuery = "SELECT id,empname FROM leave_request";
				List dataValidate = cbt.executeAllSelectQuery(empQuery,
						connectionSpace);
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				if (dataValidate != null && dataValidate.size() > 0) {
					Object[] object = null;
					for (Iterator iterator = dataValidate.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						if (object != null) {
							dataMap.put(object[0].toString(),
									object[1].toString());
						}
					}
				}
				List<String> idss = new ArrayList<String>();

				String userIdss = new LeaveHelper().getContactId("HR",
						connectionSpace, empIdofuser.split("-")[1]);
				// String userIdss=", "+new
				// LeaveHelper().getEmpDetailsByUserName("HR", userName,
				// connectionSpace)+",";
				// System.out.println("userIdss Line No 1048" +userIdss);
				if (userIdss != null && !userIdss.equalsIgnoreCase("")) {
					userIdss = userIdss.replace(" ", "");
					if (dataMap != null && dataMap.size() > 0) {
						String userId[] = userIdss.split(",");
						for (Map.Entry<String, String> a : dataMap.entrySet()) {
							if (userIdss != null) {
								for (String s : userId) {
									if ((", " + a.getValue())
											.contains(", " + s)) {
										idss.add(a.getKey());
									}
								}

							}
						}
					}
				}
				query.append(" FROM leave_request");
				// System.out.println("IDSSSS ::   "+idss);
				if (idss != null && idss.size() > 0) {
					// System.out.println("INSIDE idss IF");
					query.append("  WHERE id IN "
							+ (idss).toString().replace("[", "(")
									.replace("]", ")") + " ");
				} else {
					// System.out.println("INSIDE idss ELSE");
					query.append(" WHERE 0 ");
				}
				// System.out.println("INSIDE actionStatus:::  "+actionStatus);
				if (actionStatus != null
						&& actionStatus.equalsIgnoreCase("ALL")) {
					// System.out.println("INSIDE actionStatus IF");
				} else if (actionStatus != null
						&& actionStatus.equalsIgnoreCase("Approve")) {
					// System.out.println("INSIDE actionStatus ELSE");
					query.append(" AND status ='Approve' ");
				}
				else if( actionStatus!=null && actionStatus.equalsIgnoreCase("Disapprove"))
				{
					System.out.println("INSIDE actionStatus ELSE");
					query.append(" AND status ='Disapprove' ");
				}

				if (getSearchField() != null && getSearchString() != null
						&& !getSearchField().equalsIgnoreCase("")
						&& !getSearchString().equalsIgnoreCase("")) {
					query.append(" where ");
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq")) {
						query.append(" " + getSearchField() + " = '"
								+ getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn")) {
						query.append(" " + getSearchField() + " like '%"
								+ getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw")) {
						query.append(" " + getSearchField() + " like '"
								+ getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne")) {
						query.append(" " + getSearchField() + " <> '"
								+ getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew")) {
						query.append(" " + getSearchField() + " like '%"
								+ getSearchString() + "'");
					}
				}
				System.out.println("query:::     " + query.toString());
				List dataCount1 = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataCount1 != null && dataCount1.size() > 0) {
					List fieldNames = Configuration.getColomnList(
							"mapped_leave_request_configuration", accountID,
							connectionSpace, "leave_request_configuration");

					viewResponse = new ArrayList<Object>();
					List<Object> Listhb = new ArrayList<Object>();
					Object[] obdata = null;
					for (Iterator it = dataCount1.iterator(); it.hasNext();) {
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if (obdata[k] != null) {
								if (obdata[k].toString().matches(
										"\\d{4}-[01]\\d-[0-3]\\d")) {
									one.put(fieldNames.get(k).toString(),
											DateUtil.convertDateToIndianFormat(obdata[k]
													.toString()));
								} else {
									one.put(fieldNames.get(k).toString(),
											obdata[k].toString());
								}
							} else {
								one.put(fieldNames.get(k).toString(), "NA");
							}
							/*
							 * else if(obdata[k]==null) { if(k==0)
							 * one.put(fieldNames.get(k).toString(),"NA"); else
							 * one.put(fieldNames.get(k).toString(),"NA"); }
							 */
						}
						Listhb.add(one);
					}
					setViewResponse(Listhb);
					// System.out.println("SIze is as ::  "+viewResponse.size());
					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));

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

	public String beforeRequestResponseView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setMainHeaderName("Leave Response >> View");
				setGridColomnNamesWithFormatter();
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public void setGridColomnNamesWithFormatter() {
		Map session = ActionContext.getContext().getSession();
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session
				.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		requestGridColomns.add(gpv);
		try {
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_leave_request_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"leave_request_configuration");
			if (statusColName != null && statusColName.size() > 0) {
				for (GridDataPropertyView gdp : statusColName) {
					gpv = new GridDataPropertyView();
					if (!gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName().equalsIgnoreCase("id0")
							&& !gdp.getColomnName().equalsIgnoreCase("comment")
							&& !gdp.getColomnName().equalsIgnoreCase("empid")
							&& !gdp.getColomnName().equalsIgnoreCase("name")
							&& !gdp.getColomnName().equalsIgnoreCase("email")
							&& !gdp.getColomnName().equalsIgnoreCase("mobno")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"leaveunit")) {
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						requestGridColomns.add(gpv);
					}
				}
				gpv = new GridDataPropertyView();
				gpv.setColomnName("takeaction");
				gpv.setHeadingName("Take Action");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("requestTakeAction");
				requestGridColomns.add(gpv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String getResponseView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				StringBuilder query = new StringBuilder("");
				query1.append("select count(*) from leave_request");
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
					if (to > getRecords())
						to = getRecords();
					query.setLength(0);
					// getting the list of colmuns
					String empIdofuser = (String) session.get("empIdofuser");
					if (empIdofuser == null
							|| empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;

					String query2 = "SELECT id,empname FROM leave_request";
					List data1 = cbt.executeAllSelectQuery(query2,
							connectionSpace);
					Map<String, String> dataMap = new LinkedHashMap<String, String>();
					if (data1 != null && data1.size() > 0) {
						Object[] object = null;
						for (Iterator iterator = data1.iterator(); iterator
								.hasNext();) {
							object = (Object[]) iterator.next();
							if (object != null) {
								dataMap.put(object[0].toString(),
										object[1].toString());
							}
						}
					}

					List<String> idss = new ArrayList<String>();

					String userIdss = new LeaveHelper().getContactId("HR",
							connectionSpace, empIdofuser.split("-")[1]);
					// String userIdss=", "+new
					// LeaveHelper().getEmpDetailsByUserName("HR", userName,
					// connectionSpace)+",";
					if (userIdss != null && !userIdss.equalsIgnoreCase("")) {
						userIdss = userIdss.replace(" ", "");
						if (dataMap != null && dataMap.size() > 0) {
							String userId[] = userIdss.split(",");
							for (Map.Entry<String, String> a : dataMap
									.entrySet()) {
								if (userIdss != null) {
									for (String s : userId) {

										if ((", " + a.getValue()).contains(", "
												+ s)) {
											idss.add(a.getKey());
										}
									}

								}
							}
						}
					}

					List fieldNames = Configuration.getColomnList(
							"mapped_leave_request_configuration", accountID,
							connectionSpace, "leave_request_configuration");
					int i = 0;
					query.append("SELECT ");
					if (fieldNames != null && fieldNames.size() > 0) {
						Object obdata1 = null;
						for (Iterator it = fieldNames.iterator(); it.hasNext();) {
							// generating the dyanamic query based on selected
							// fields
							obdata1 = (Object) it.next();
							if (obdata1 != null) {
								if (i < fieldNames.size() - 1)
									query.append(obdata1.toString() + ",");
								else
									query.append(obdata1.toString());
							}
							i++;
						}
					}
					query.append(" FROM leave_request");

					if (idss != null && idss.size() > 0) {

						query.append(" WHERE id IN "
								+ (idss).toString().replace("[", "(")
										.replace("]", ")")
								+ " AND flag !='Action Done'");
					}

					else {
						query.append(" WHERE 0 ");
					}

					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" WHERE ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" " + getSearchField() + " like '"
									+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					}
				}
				List data = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				query.setLength(0);
				List fieldNames = Configuration.getColomnList(
						"mapped_leave_request_configuration", accountID,
						connectionSpace, "leave_request_configuration");

				if (data != null && data.size() > 0) {
					StringBuilder dataList;
					viewResponse1 = new ArrayList<Object>();
					List<Object> Listhb = new ArrayList<Object>();
					Object[] obdata = null;

					for (Iterator it = data.iterator(); it.hasNext();) {

						dataList = new StringBuilder();
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if (obdata[k] != null) {
								if (k == 0)
									one.put(fieldNames.get(k).toString(),
											(Integer) obdata[k]);
								else {
									if (fieldNames.get(k).equals("empname")) {

										query.append("SELECT emp.empName FROM employee_basic AS emp  ");
										query.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id  ");
										query.append(" WHERE cc.moduleName='LM' ");
										query.append(" and cc.id IN("
												+ obdata[k]
														.toString()
														.substring(
																0,
																obdata[k]
																		.toString()
																		.length() - 1)
												+ ")");

										List empdata = cbt
												.executeAllSelectQuery(
														query.toString(),
														connectionSpace);
										for (Iterator iterator = empdata
												.iterator(); iterator.hasNext();) {
											Object object = (Object) iterator
													.next();
											if (object != null) {
												dataList.append(object
														.toString() + ",");
											}
										}
										if (dataList != null
												&& dataList.length() > 0) {
											one.put(fieldNames.get(k)
													.toString(),
													dataList.substring(
															0,
															dataList.length() - 1));
										}
										query.setLength(0);
									} else if (fieldNames.get(k)
											.equals("fdate")
											|| fieldNames.get(k)
													.equals("tdate")
											|| fieldNames.get(k)
													.equals("odate")
											|| fieldNames.get(k)
													.equals("date1")) {
										one.put(fieldNames.get(k).toString(),
												DateUtil.convertDateToIndianFormat(obdata[k]
														.toString()));
									} else {
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									}
								}
							} else if (obdata[k] == null) {
								if (k == 0)
									one.put(fieldNames.get(k).toString(), "NA");
								else
									one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setViewResponse1(Listhb);
					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
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

	public String beforeResponseView() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				String userName = (String) session.get("uName");
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

	public void setGridColomnNames1() {
		Map session = ActionContext.getContext().getSession();
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session
				.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		requestGridColomns.add(gpv);
		try {
			fromDate = DateUtil.getNewDate("month", -1,
					DateUtil.getCurrentDateUSFormat());
			toDate = DateUtil.getCurrentDateUSFormat();
			minCount = "-30";
			maxCount = "30";
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_leave_request_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"leave_request_configuration");
			if (statusColName != null && statusColName.size() > 0) {
				for (GridDataPropertyView gdp : statusColName) {
					if (!gdp.getColomnName().equalsIgnoreCase("flag")
							&& !gdp.getColomnName().equalsIgnoreCase("empname")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"leavestatus")) {

						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(170);
						requestGridColomns.add(gpv);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String addResponse4Employee() {
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			try {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String query1 = "SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM leave_request AS lr INNER JOIN compliance_contacts AS cc ON cc.id=lr.leaveunit INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE  lr.id='"
						+ getId() + "'";
				List employeeDetails = cbt.executeAllSelectQuery(query1,
						connectionSpace);
				if (employeeDetails != null && employeeDetails.size() > 0) {
					Object[] object = null;
					for (Iterator iterator = employeeDetails.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						empname = object[0].toString();
						empmob = object[1].toString();
						empemail = object[2].toString();
					}
				}
				getResponsePageField();
				return SUCCESS;
			} catch (Exception e) {
				log.error(
						"Problem in method addResponse4Employee of class "
								+ getClass() + " on "
								+ DateUtil.getCurrentDateIndianFormat()
								+ " at " + DateUtil.getCurrentTimewithSeconds(),
						e);
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public void getResponsePageField() {
		try {
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			String accountID = (String) session.get("accountid");
			List<GridDataPropertyView> requestColumnList = Configuration
					.getConfigurationData("mapped_leave_request_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"leave_request_configuration");
			responseColumnText = new ArrayList<ConfigurationUtilBean>();
			responseColumnDropdown = new ArrayList<ConfigurationUtilBean>();
			if (requestColumnList != null && requestColumnList.size() > 0) {
				ConfigurationUtilBean cub = null;
				for (GridDataPropertyView gdp : requestColumnList) {
					cub = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							|| gdp.getColomnName().equalsIgnoreCase("name")
							|| gdp.getColomnName().equalsIgnoreCase("mobno")
							|| gdp.getColomnName().equalsIgnoreCase("comment")
							|| gdp.getColomnName().equalsIgnoreCase("email")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						cub.setKey(gdp.getColomnName());
						cub.setValue(gdp.getHeadingName());
						cub.setColType(gdp.getColType());
						cub.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy().equalsIgnoreCase("1")) {
							cub.setMandatory(true);
						} else {
							cub.setMandatory(false);
						}
						responseColumnText.add(cub);
					} else if (gdp.getColType().equalsIgnoreCase("D")
							&& gdp.getColomnName().equalsIgnoreCase("status")) {
						cub.setKey(gdp.getColomnName());
						cub.setValue(gdp.getHeadingName());
						cub.setColType(gdp.getColType());
						cub.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy().equalsIgnoreCase("1")) {
							cub.setMandatory(true);
						} else {
							cub.setMandatory(false);
						}
						responseColumnDropdown.add(cub);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"Problem in method getResponsePageField of class "
							+ getClass() + " on "
							+ DateUtil.getCurrentDateIndianFormat() + " at "
							+ DateUtil.getCurrentTimewithSeconds(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addResponse4request() {
		String returnResult = ERROR;
		try {
			Map session = ActionContext.getContext().getSession();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			session.put("clientAccont", getAccountid());
			SessionFactory connection = new ConnectionHelper()
					.getSessionFactory(getAccountid());
			session.put("clientConnection", connection);
			LeaveHelper lm=new LeaveHelper();
			String statusQuery = "select id, status, approveBy,reason,date1,comment,flag,fdate,tdate,odate,ftime,ttime,approveOn from leave_request  WHERE  id='"
					+ getId() + "'";
			System.out.println("statusQuery>>>>>>>" +statusQuery);
			
			List statusDetails = cbt.executeAllSelectQuery(statusQuery,
					connection);
			if (statusDetails != null && statusDetails.size() > 0) {
				Object[] object = null;
				for (Iterator iterator = statusDetails.iterator(); iterator
						.hasNext();) {
					object = (Object[]) iterator.next();
					leaveID = lm.getValueWithNullCheck(object[0]);
					leaveStatusTemp =lm.getValueWithNullCheck (object[1]);
					approveBy=lm.getValueWithNullCheck(object[2]);
					reason=lm.getValueWithNullCheck(object[3]);
					date1=lm.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[4].toString()));
					comments=lm.getValueWithNullCheck(object[5]);
					flagtemp=lm.getValueWithNullCheck(object[6]);
					storeFdate=lm.getValueWithNullCheck((object[7]));
					System.out.println("storeFdate  " +storeFdate);
					fdatetemp=DateUtil.convertDateToIndianFormat(storeFdate.toString());
					System.out.println("fdatetemp  " +fdatetemp);
					storeTdate=lm.getValueWithNullCheck((object[8]));
					tdatetemp=DateUtil.convertDateToIndianFormat(storeTdate);
					System.out.println("tdatetemp  " +tdatetemp);
					storeOdate=lm.getValueWithNullCheck((object[9]));
					odatetemp=DateUtil.convertDateToIndianFormat(storeOdate);
					ftimetemp=lm.getValueWithNullCheck(object[10]);
					ttimetemp=lm.getValueWithNullCheck(object[11]);
					approveOnTemp=lm.getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[12].toString()));
					
					
				}
			}
			System.out.println("leaveStatusTemp>>>>>>>>>>>>>>>>>"
					+ leaveStatusTemp);
			if (leaveStatusTemp != null
					&& !leaveStatusTemp.equalsIgnoreCase("Approve")
					&& !leaveStatusTemp.equalsIgnoreCase("Disapprove")) {

				String query1 = "SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM leave_request AS lr INNER JOIN compliance_contacts AS cc ON cc.id=lr.leaveunit INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE  lr.id='"
						+ getId() + "'";
				List employeeDetails = cbt.executeAllSelectQuery(query1,
						connection);
				if (employeeDetails != null && employeeDetails.size() > 0) {
					Object[] object = null;
					for (Iterator iterator = employeeDetails.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						empname = object[0].toString();
						empmob = object[1].toString();
						empemail = object[2].toString();
					}
				}
				List<GridDataPropertyView> requestColumnList = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								getAccountid(), connection, "", 0,
								"table_name", "leave_request_configuration");
				responseColumnText = new ArrayList<ConfigurationUtilBean>();
				responseColumnDropdown = new ArrayList<ConfigurationUtilBean>();
				if (requestColumnList != null && requestColumnList.size() > 0) {
					ConfigurationUtilBean cub = null;
					for (GridDataPropertyView gdp : requestColumnList) {
						cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								|| gdp.getColomnName().equalsIgnoreCase("name")
								|| gdp.getColomnName()
										.equalsIgnoreCase("mobno")
								|| gdp.getColomnName().equalsIgnoreCase(
										"comment")
								|| gdp.getColomnName()
										.equalsIgnoreCase("email")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"empemail")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							responseColumnText.add(cub);
						} else if (gdp.getColType().equalsIgnoreCase("D")
								&& gdp.getColomnName().equalsIgnoreCase(
										"status")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							responseColumnDropdown.add(cub);
						}
					}
				}
				returnResult = SUCCESS;
			} 
			
			if (leaveStatusTemp != null && !leaveStatusTemp.equalsIgnoreCase("Pending"))
			{
			if(flagtemp!=null && flagtemp.equalsIgnoreCase("Days")) 
			{
				//addActionMessage("Action already Has Been Taken by "+approveBy+" with "+leaveStatusTemp+" status");


				String query1 = "SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM leave_request AS lr INNER JOIN compliance_contacts AS cc ON cc.id=lr.leaveunit INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE  lr.id='"
						+ getId() + "'";
				List employeeDetails = cbt.executeAllSelectQuery(query1,
						connection);
				if (employeeDetails != null && employeeDetails.size() > 0) {
					Object[] object = null;
					for (Iterator iterator = employeeDetails.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						empname = object[0].toString();
						empmob = object[1].toString();
						empemail = object[2].toString();
					}
				}
				List<GridDataPropertyView> requestColumnList = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								getAccountid(), connection, "", 0,
								"table_name", "leave_request_configuration");
				responseColumnText = new ArrayList<ConfigurationUtilBean>();
				responseColumnDropdown = new ArrayList<ConfigurationUtilBean>();
				if (requestColumnList != null && requestColumnList.size() > 0) {
					ConfigurationUtilBean cub = null;
					for (GridDataPropertyView gdp : requestColumnList) {
						cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								|| gdp.getColomnName().equalsIgnoreCase("name")
								|| gdp.getColomnName()
										.equalsIgnoreCase("approveBy")
								|| gdp.getColomnName().equalsIgnoreCase(
										"comment")
								|| gdp.getColomnName()
										.equalsIgnoreCase("reason")
										|| gdp.getColomnName()
										.equalsIgnoreCase("fdate")
										|| gdp.getColomnName()
										.equalsIgnoreCase("tdate")
										|| gdp.getColomnName()
										.equalsIgnoreCase("date1")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"empemail")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							responseColumnText.add(cub);
						} 
					}
				}
			
				returnResult = "ActionStatussuccess";
			}
			
			if(flagtemp!=null && flagtemp.equalsIgnoreCase("Hour"))  
			{

				//addActionMessage("Action already Has Been Taken by "+approveBy+" with "+leaveStatusTemp+" status");


				String query1 = "SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM leave_request AS lr INNER JOIN compliance_contacts AS cc ON cc.id=lr.leaveunit INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id WHERE  lr.id='"
						+ getId() + "'";
				List employeeDetails = cbt.executeAllSelectQuery(query1,
						connection);
				if (employeeDetails != null && employeeDetails.size() > 0) {
					Object[] object = null;
					for (Iterator iterator = employeeDetails.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						empname = object[0].toString();
						empmob = object[1].toString();
						empemail = object[2].toString();
					}
				}
				List<GridDataPropertyView> requestColumnList = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								getAccountid(), connection, "", 0,
								"table_name", "leave_request_configuration");
				responseColumnText = new ArrayList<ConfigurationUtilBean>();
				responseColumnDropdown = new ArrayList<ConfigurationUtilBean>();
				if (requestColumnList != null && requestColumnList.size() > 0) {
					ConfigurationUtilBean cub = null;
					for (GridDataPropertyView gdp : requestColumnList) {
						cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								|| gdp.getColomnName().equalsIgnoreCase("name")
								|| gdp.getColomnName().equalsIgnoreCase("approveBy")
								|| gdp.getColomnName().equalsIgnoreCase("comment")
								|| gdp.getColomnName().equalsIgnoreCase("reason")
										|| gdp.getColomnName().equalsIgnoreCase("date1")
										|| gdp.getColomnName().equalsIgnoreCase("odate")
										
										|| gdp.getColomnName().equalsIgnoreCase("ftime")
										|| gdp.getColomnName().equalsIgnoreCase("ttime")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"empemail")) {
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							System.out.println(" gdp.getHeadingName-------------------------------" + cub.getValue());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1")) {
								cub.setMandatory(true);
							} else {
								cub.setMandatory(false);
							}
							responseColumnText.add(cub);
						} 
					}
				}
			
				returnResult = "ActionStatusHoursuccess";
				System.out.println("returnResult  "  +returnResult);
			
				
			}
		}

		} catch (Exception e) {
			log.error(
					"Problem in method addResponse4request of class "
							+ getClass() + " on "
							+ DateUtil.getCurrentDateIndianFormat() + " at "
							+ DateUtil.getCurrentTimewithSeconds(), e);
			e.printStackTrace();
			returnResult = ERROR;
		}
		return returnResult;
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateRequest() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		String dateFrom = null;
		String dateTo = null;
		String dateOn = null;
		String timeFrom = null;
		String timeTo = null;
		String reason = null;
		if (sessionFlag) {
			try {
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData(
								"mapped_leave_request_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "leave_request_configuration");
				if (statusColName != null && statusColName.size() > 0) {
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName) {
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					String empIdofuser = (String) session.get("empIdofuser");

					if (empIdofuser == null
							|| empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;

					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					StringBuilder query = new StringBuilder();
					query.append("update leave_request set ");
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null) {
								if (!parmName.equalsIgnoreCase("id")
										&& !parmName
												.equalsIgnoreCase("validate")
										&& !parmName
												.equalsIgnoreCase("userName")
										&& !parmName
												.equalsIgnoreCase("accountid")
										&& !parmName.equalsIgnoreCase("email")
										&& !parmName
												.equalsIgnoreCase("leaveunit")
										&& !paramValue.equalsIgnoreCase("'"
												+ getId() + "'")) {
									query.append("" + parmName + "='"
											+ paramValue + "', ");
								}
							}
						}
					}
					String userName = (String) session.get("uName");
					LeaveHelper LM = new LeaveHelper();
					String[] loggdData = LM.getEmpDetailsByUserName("HR",
							userName, connectionSpace);

					// query.append(" flag = 'Action Done' , email='"+empIdofuser
					// .split("-")[1]+"'   ");
					query.append(" email='"
							+ loggdData[1] + "' , approveBy='" + loggdData[1]
							+ "', approveOn="+DateUtil.getCurrentDateUSFormat()+" ");
					query.append(" where id='" + getId() + "'");

					status = cbt.updateTableColomn(connectionSpace, query);

					List colmName = new ArrayList<String>();
					colmName.add("fdate");
					colmName.add("tdate");
					colmName.add("odate");
					colmName.add("ftime");
					colmName.add("ttime");
					colmName.add("reason");

					Map<String, Object> wherClause = new HashMap<String, Object>();
					wherClause.put("id", getId());
					List data = cbt.viewAllDataEitherSelectOrAll(
							"leave_request", colmName, wherClause,
							connectionSpace);
					Object[] object = null;
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						object = (Object[]) iterator.next();
						if (object[0] != null) {
							dateFrom = object[0].toString();
						}
						if (object[1] != null) {
							dateTo = object[1].toString();
						}
						if (object[2] != null) {
							dateOn = object[2].toString();
						}
						if (object[3] != null) {
							timeFrom = object[3].toString();
						}
						if (object[4] != null) {
							timeTo = object[4].toString();
						}
						if (object[5] != null) {
							reason = object[5].toString();
						}
					}
					LeaveHelper LH = new LeaveHelper();

					String statusForm = request.getParameter("status");
					String emailId = request.getParameter("email");
					System.out.println("emailIdccccccccccccccc  "  +emailId );
					String requsetName = request.getParameter("name");
					String comment = request.getParameter("comment");
					String validateByEmpId = (String) session.get("empName");//
					String revertSMS = null;
					if (status) {
						MsgMailCommunication MM = new MsgMailCommunication();
						if (dateFrom != null && dateTo != null) {
							revertSMS = "Dear " + requsetName
									+ ", your request for " + reason
									+ " has been " + statusForm + " with "
									+ comment + ".";
							String mailBody = responseMailBody(requsetName,
									dateFrom, dateTo, statusForm, comment,
									reason, validateByEmpId, getId(),
									(String) session.get("uName"), accountID);
							MM.addMail("", "", emailId, "Leave " + statusForm + "Details", mailBody, "", "Pending", "0",
									"", "HR");
							revertSMS = "Dear " + requsetName
									+ ", your request for " + reason
									+ " has been " + statusForm + " with "
									+ comment + ".";
							System.out
									.println("update revertSMS >>>>>           "
											+ revertSMS);

							MM.addMessageHR(requsetName, "", empmob, revertSMS,
									"", "Pending", "0", "HR", connectionSpace);

						}
						if (dateOn != null && timeFrom != null
								&& timeTo != null) {
							revertSMS = "Dear " + requsetName
									+ ", your request for " + reason
									+ " has been " + statusForm + " with "
									+ comment + ".";
							// revertSMS = "hello";
							String mailBody1 = responseMailBody1(requsetName,
									dateOn, timeFrom, timeTo, statusForm,
									comment, reason, getValidate(), getId(),
									(String) session.get("uName"), accountID);
							MM.addMail("", "", emailId, "Leave " + statusForm
									+ "Details", mailBody1, "", "Pending", "0",
									"", "HR");
							System.out
									.println("update revertSMS >>>>>           "
											+ revertSMS);
							MM.addMessageHR(requsetName, "", empmob, revertSMS,
									"", "Pending", "0", "HR", connectionSpace);

						}
						addActionMessage("Data Updated Successfully!!!");
						returnResult = SUCCESS;
					} else {
						addActionMessage("Oops There is some error in data!!!!");
					}
				}
			} catch (Exception e) {
				returnResult = ERROR;
				log.error(
						"Problem in method updateRequest of class "
								+ getClass() + " on "
								+ DateUtil.getCurrentDateIndianFormat()
								+ " at " + DateUtil.getCurrentTimewithSeconds(),
						e);

				e.printStackTrace();
			}
		} 
		else
		{
			
			Map session = ActionContext.getContext().getSession();
			javax.servlet.http.HttpServletRequest req = ServletActionContext
					.getRequest();
			SessionFactory connectionClient = (SessionFactory) session
					.get("clientConnection");
			String accountClientId = (String) session.get("clientAccont");
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_leave_request_configuration",
							accountClientId, connectionClient, "", 0,
							"table_name", "leave_request_configuration");

			if (statusColName != null && statusColName.size() > 0) {
				boolean status = false;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				TableColumes ob1 = null;
				for (GridDataPropertyView gdp : statusColName) {
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
				}
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				StringBuilder query = new StringBuilder();
				query.append("update leave_request set ");
				if (requestParameterNames != null
						&& requestParameterNames.size() > 0) {
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& parmName != null) {
							if (!parmName.equalsIgnoreCase("id")
									&& !parmName.equalsIgnoreCase("validate")
									&& !parmName.equalsIgnoreCase("userName")
									&& !parmName.equalsIgnoreCase("accountid")
									&& !parmName.equalsIgnoreCase("empemail")
									&& !parmName.equalsIgnoreCase("empname")
									&& !parmName.equalsIgnoreCase("empmob")
									&& !paramValue.equalsIgnoreCase("'"
											+ getId() + "'")) {
								query.append("" + parmName + "='" + paramValue
										+ "', ");
							}
						}
					}
				}
				//query.append(" flag = 'Action Done' , email='" + validate
				//		+ "', approveBy='" + validate + "' ");
				query.append(" email='" + validate
						+ "', approveBy='" + validate + "' , approveOn='"+DateUtil.getCurrentDateUSFormat()+"' ");

				 System.out.println("query >>" +query);
				StringBuilder query2 = new StringBuilder();
				query2.append(query.substring(0, query.length()));
				query2.append(" where id='" + getId() + "'");
				String statusForm = req.getParameter("status");
				String emailId = req.getParameter("empemail");
				System.out.println("emailId emailId " +emailId);
				String requsetName = req.getParameter("empname");
				String comment = req.getParameter("comment");
				String revertSMS = null;

				LeaveHelper LH = new LeaveHelper();
				// HrVirtualNoDataReceiver hvmn=new HrVirtualNoDataReceiver();
				// String arrLr[] = hvmn.getClientAccountData("ds");
				// System.out.println("arrLr[] ::::::;  " +);
				// String[] empdata= LH.getEmpDetailsByMobileNo("HR", arrLr[0],
				// connectionClient);
				status = cbt.updateTableColomn(connectionClient, query2);
				List data = cbt.executeAllSelectQuery(
						"SELECT fdate,tdate,odate,ftime,ttime,reason FROM leave_request WHERE id = '"
								+ getId() + "'", connectionClient);
				Object[] object = null;
				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					object = (Object[]) iterator.next();
					if (object[0] != null) {
						dateFrom = object[0].toString();
					}
					if (object[1] != null) {
						dateTo = object[1].toString();
					}
					if (object[2] != null) {
						dateOn = object[2].toString();
					}
					if (object[3] != null) {
						timeFrom = object[3].toString();
					}
					if (object[4] != null) {
						timeTo = object[4].toString();
					}
					if (object[5] != null) {
						reason = object[5].toString();
					}
				}
				if (status) {
					CommunicationHelper CH = new CommunicationHelper();

					String mailBody = null;
					if (dateFrom != null && dateTo != null) {
						revertSMS = "Dear " + requsetName
								+ ", your request for " + reason + " has been "
								+ statusForm + " with " + comment + ".";
						// revertSMS = "hello";
						System.out.println("revertSMS after Action " +revertSMS);
						mailBody = responseMailBody(requsetName, dateFrom,
								dateTo, statusForm, comment, reason,
								getValidate(), getId(), userName, accountid);
						CH.addMail(requsetName, "", emailId, "Leave "+ statusForm + " Details", mailBody, "","Pending", "0", "", "HR", connectionClient);
						CH.addMessage(requsetName, "", empmob, revertSMS, "",
								"Pending", "0", "HR", connectionClient);

					}
					if (dateOn != null && timeFrom != null && timeTo != null) {
						revertSMS = "Dear " + requsetName
								+ ", your request for " + reason + " has been "
								+ statusForm + " with " + comment + ".";
						System.out.println("revertSMS after Action hours " +revertSMS);
						mailBody = responseMailBody1(requsetName, dateOn,timeFrom, timeTo, statusForm, comment, reason,getValidate(), getId(), userName, accountid);
						CH.addMail(requsetName, "", emailId, "Leave "+ statusForm + " Details", mailBody, "","Pending", "0", "", "HR", connectionClient);
						CH.addMessage(requsetName, "", empmob, revertSMS, "","Pending", "0", "HR", connectionClient);
					}
					addActionMessage("Data Updated Successfully!!!");
					returnResult = SUCCESS;
				} else {
					addActionMessage("Oops There is some error in data!!!!");
				}
			}
		}
		return returnResult;
	}

	public String responseMailBody(String requsetName, String fromdate,
			String todate, String status, String commn, String reason,
			String validate, String maxid, String userName, String accounId) {
		StringBuilder mailText = new StringBuilder("");
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			String ip = InetAddress.getLocalHost().getHostAddress();
			int portNo = req.getServerPort();
			String URL = "<a href=http://"
					+ ip
					+ ":"
					+ portNo
					+ "/over2cloud/view/Over2Cloud/leaveManagement/beforeReRequestAction.action?id="
					+ maxid + "&userName=" + userName + "&accountid="
					+ accounId + ">Click Here!!</a>";
			mailText.append("<table><tr><td>Dear "
					+ requsetName
					+ ",</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='2'>Here is the action status over the leave requested by you:</font></td></tr></table>");
			mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Validate By :</font></td> "
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ validate
					+ "&nbsp;</font></td>"
					+ "</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.convertDateToIndianFormat(fromdate)
					+ "&nbsp;</font></td>"
					+ "</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.convertDateToIndianFormat(todate)
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Status:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ status
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ reason
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Comment:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ commn + "&nbsp;</font></td></tr>" + "   </table>");
			/*
			 * mailText.append(
			 * "<tr><td>You are kindly requested to follow the same.</td></tr>"
			 * );mailText.append(
			 * "<tr><td>For Re- Request Kindly Click on above link :</td></tr>"
			 * ); mailText.append(URL);
			 */

			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
			mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
			mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
			mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
			mailText.append("<table><tr><td><div align='justify'><font face ='TIMESROMAN' size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the //System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailText.toString();
	}

	public String responseMailBody1(String requsetName, String dateOn,
			String timeFrom, String timeTo, String statusForm, String comment,
			String reason, String validate, String maxid, String userName,
			String accounId) {
		StringBuilder mailText = new StringBuilder("");
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			String ip = InetAddress.getLocalHost().getHostAddress();
			int portNo = req.getServerPort();
			String URL = "<a href=http://"
					+ ip
					+ ":"
					+ portNo
					+ "/over2cloud/view/Over2Cloud/leaveManagement/beforeReRequestAction.action?id="
					+ maxid + "&userName=" + userName + "&accountid="
					+ accounId + ">Click Here!!</a>";

			mailText.append("<table><tr><td>Dear "
					+ requsetName
					+ ",</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
					+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
					+ "<font color='#000000' size='2'>Here is the action status over the leave requested by you:</font></td></tr></table>");
			mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
					+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Validate By :</font></td> "
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ validate
					+ "&nbsp;</font></td>"
					+ "</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>On Date:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ DateUtil.convertDateToIndianFormat(dateOn)
					+ "&nbsp;</font></td>"
					+ "</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Time:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ timeFrom
					+ "&nbsp;</font></td></tr>"
					+ "</tr><tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Time:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ timeTo
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Status:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ statusForm
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ reason
					+ "&nbsp;</font></td></tr>"
					+ "<tr  bgcolor='#F6CECE'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Comment:</font></td>"
					+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
					+ comment + "&nbsp;</font></td></tr>" + "   </table>");

			/*
			 * mailText.append(
			 * "<tr><td>You are kindly requested to follow the same.</td></tr>"
			 * );mailText.append(
			 * "<tr><td>For Re- Request Kindly Click on above link :</td></tr>"
			 * ); mailText.append(URL);
			 */
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td></td></tr>");
			mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
			mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
			mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
			mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
			mailText.append("<table><tr><td><div align='justify'><font face ='TIMESROMAN' size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the //System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailText.toString();
	}

	@SuppressWarnings("rawtypes")
	public String reRequestActionData() {
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connection = new ConnectionHelper()
					.getSessionFactory(accountid);
			String query = "SELECT fdate,tdate,odate,ftime,ttime,empname,reason,comment FROM leave_request WHERE id='"
					+ getId() + "'";
			List data = cbt.executeAllSelectQuery(query, connection);
			if (data != null && data.size() > 0) {
				Object[] object = null;
				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					object = (Object[]) iterator.next();
					if (object != null) {
						if (object[0] != null) {
							fdate = DateUtil
									.convertDateToIndianFormat(object[0]
											.toString());
						}
						if (object[1] != null) {
							tdate = DateUtil
									.convertDateToIndianFormat(object[1]
											.toString());
						}
						if (object[2] != null) {
							odate = DateUtil
									.convertDateToIndianFormat(object[2]
											.toString());
						}
						if (object[3] != null) {
							ftime = object[3].toString();
						}
						if (object[4] != null) {
							ttime = object[4].toString();
						}
						if (object[5] != null) {
							empname = object[5].toString();
						}
						if (object[6] != null) {
							reason = object[6].toString();
						}
						if (object[7] != null) {
							comment = object[7].toString();
						}
					}
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" }) public String
	 * reRequestAddAction() { try { SessionFactory connection = new
	 * ConnectionHelper().getSessionFactory(accountid); CommonOperInterface
	 * cbt=new CommonConFactory().createInterface(); boolean status=false;
	 * List<String> requestParameterNames =
	 * Collections.list((Enumeration<String>) request.getParameterNames());
	 * StringBuilder query=new StringBuilder();
	 * query.append("update leave_request set "); if(requestParameterNames!=null
	 * && requestParameterNames.size()>0) {
	 * Collections.sort(requestParameterNames); Iterator it =
	 * requestParameterNames.iterator(); while (it.hasNext()) { String parmName
	 * = it.next().toString(); String paramValue=request.getParameter(parmName);
	 * if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null)
	 * { if(!parmName.equalsIgnoreCase("id") &&
	 * !parmName.equalsIgnoreCase("fdate") &&
	 * !parmName.equalsIgnoreCase("odate")&&
	 * !parmName.equalsIgnoreCase("ftime")&&
	 * !parmName.equalsIgnoreCase("ttime")&&
	 * !parmName.equalsIgnoreCase("empname")&&
	 * !parmName.equalsIgnoreCase("reason")&&
	 * !parmName.equalsIgnoreCase("comment") &&
	 * !parmName.equalsIgnoreCase("tdate") &&
	 * !parmName.equalsIgnoreCase("accountid")&&
	 * !paramValue.equalsIgnoreCase("'"+getId()+"'")) {
	 * query.append("reason ='"+paramValue+"', "); } } } } StringBuilder
	 * query2=new StringBuilder(); query2.append(query.substring(0,
	 * query.length()-2)); query2.append(" where id='"+getId()+"'");
	 * status=cbt.updateTableColomn(connection,query2); if (status) { String
	 * empName=null; String empname=null; String empEmailIDs=null; String
	 * leaveStatus=null; StringBuilder query1= new StringBuilder();
	 * query1.append(
	 * "SELECT fdate,tdate,odate,ftime,ttime,reason,comment,eb.empName as validate,eb1.empName ,eb1.emailIdOne,leavestatus FROM leave_request as lr  "
	 * ); query1.append("INNER JOIN employee_basic as eb ");
	 * query1.append("on lr.leaveunit = eb.id ");
	 * query1.append("INNER JOIN employee_basic as eb1 ");
	 * query1.append("on lr.empname=eb1.id   ");
	 * query1.append("WHERE lr.id='"+getId()+"'"); List
	 * listData=cbt.executeAllSelectQuery(query1.toString(), connection); if
	 * (listData!=null && listData.size()>0) { Object[] object=null; for
	 * (Iterator iterator = listData.iterator(); iterator.hasNext();) { object =
	 * (Object[]) iterator.next(); if (object[0]!=null) {
	 * fdate=DateUtil.convertDateToIndianFormat(object[0].toString()); } if
	 * (object[1]!=null) {
	 * tdate=DateUtil.convertDateToIndianFormat(object[1].toString()); } if
	 * (object[2]!=null) {
	 * odate=DateUtil.convertDateToIndianFormat(object[2].toString()); } if
	 * (object[3]!=null) { ftime=object[3].toString(); } if (object[4]!=null) {
	 * ttime=object[4].toString(); } if (object[5]!=null) {
	 * reason=object[5].toString(); } if (object[6]!=null) {
	 * comment=object[6].toString(); } if (object[7]!=null) {
	 * empname=object[7].toString(); } if (object[8]!=null) {
	 * empName=object[8].toString(); } if (object[9]!=null) {
	 * empEmailIDs=object[9].toString(); } if (object[10]!=null) {
	 * leaveStatus=object[10].toString(); } } } GenericMailSender mail=new
	 * GenericMailSender(); String mailBody=null; if (fdate!=null && tdate
	 * !=null) { mailBody=getMailBody(empName, fdate, tdate,
	 * Integer.parseInt(getId
	 * ()),empname,accountid,reason,leaveStatus,"0","himanshim"); } if
	 * (odate!=null && ftime!=null && ttime!=null) {
	 * mailBody=getMailBody1(empName, odate,ftime, ttime,
	 * Integer.parseInt(getId(
	 * )),empname,accountid,reason,leaveStatus,"0","himanshim"); }
	 * 
	 * new
	 * MsgMailCommunication().addMail("","",empEmailIDs,"Leave Request by "+empname
	 * +"", mailBody,"", "Pending", "0","","HR");
	 * //mail.sendMail("sumitib@dreamsol.biz", "Leave Request by "+empname+"",
	 * mailBody, "", "smtp.gmail.com", "465", "sumitib@dreamsol.biz",
	 * "amity1988"); return SUCCESS; } else {
	 * addActionMessage("OOPS There is some error in data !!!!!!!"); return
	 * ERROR; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return ERROR; }
	 * 
	 * }
	 */
	public String getEmpemail() {
		return empemail;
	}

	public void setEmpemail(String empemail) {
		this.empemail = empemail;
	}

	public String getEmpmob() {
		return empmob;
	}

	public void setEmpmob(String empmob) {
		this.empmob = empmob;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<GridDataPropertyView> getRequestGridColomns() {
		return requestGridColomns;
	}

	public void setRequestGridColomns(
			List<GridDataPropertyView> requestGridColomns) {
		this.requestGridColomns = requestGridColomns;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
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

	public List<Object> getViewRequest() {
		return viewRequest;
	}

	public void setViewRequest(List<Object> viewRequest) {
		this.viewRequest = viewRequest;
	}

	public Map<String, String> getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(Map<String, String> employeeNames) {
		this.employeeNames = employeeNames;
	}

	public List<ConfigurationUtilBean> getRequestColumnText() {
		return requestColumnText;
	}

	public void setRequestColumnText(
			List<ConfigurationUtilBean> requestColumnText) {
		this.requestColumnText = requestColumnText;
	}

	public List<ConfigurationUtilBean> getRequestColumnDate() {
		return requestColumnDate;
	}

	public void setRequestColumnDate(
			List<ConfigurationUtilBean> requestColumnDate) {
		this.requestColumnDate = requestColumnDate;
	}

	public List<ConfigurationUtilBean> getRequestColumnTime() {
		return requestColumnTime;
	}

	public void setRequestColumnTime(
			List<ConfigurationUtilBean> requestColumnTime) {
		this.requestColumnTime = requestColumnTime;
	}

	public List<ConfigurationUtilBean> getRequestColumnDropdown() {
		return requestColumnDropdown;
	}

	public void setRequestColumnDropdown(
			List<ConfigurationUtilBean> requestColumnDropdown) {
		this.requestColumnDropdown = requestColumnDropdown;
	}

	public List<ConfigurationUtilBean> getRequestColumnOnDate() {
		return requestColumnOnDate;
	}

	public void setRequestColumnOnDate(
			List<ConfigurationUtilBean> requestColumnOnDate) {
		this.requestColumnOnDate = requestColumnOnDate;
	}

	public List<ConfigurationUtilBean> getResponseColumnText() {
		return responseColumnText;
	}

	public void setResponseColumnText(
			List<ConfigurationUtilBean> responseColumnText) {
		this.responseColumnText = responseColumnText;
	}

	public List<ConfigurationUtilBean> getResponseColumnDropdown() {
		return responseColumnDropdown;
	}

	public void setResponseColumnDropdown(
			List<ConfigurationUtilBean> responseColumnDropdown) {
		this.responseColumnDropdown = responseColumnDropdown;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public Map<String, String> getSubDept_DeptLevelName() {
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(
			Map<String, String> subDept_DeptLevelName) {
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Map<Integer, String> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList) {
		this.subDeptList = subDeptList;
	}

	public String getDeptOrSubDeptId() {
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId) {
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public Map<Integer, String> getEmployeelist() {
		return employeelist;
	}

	public void setEmployeelist(Map<Integer, String> employeelist) {
		this.employeelist = employeelist;
	}

	public List<Object> getViewResponse() {
		return viewResponse;
	}

	public void setViewResponse(List<Object> viewResponse) {
		this.viewResponse = viewResponse;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public List<ConfigurationUtilBean> getRequestColumnRadio() {
		return requestColumnRadio;
	}

	public void setRequestColumnRadio(
			List<ConfigurationUtilBean> requestColumnRadio) {
		this.requestColumnRadio = requestColumnRadio;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getOdate() {
		return odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getTtime() {
		return ttime;
	}

	public void setTtime(String ttime) {
		this.ttime = ttime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@SuppressWarnings("rawtypes")
	public List getLeaveType() {
		return leaveType;
	}

	@SuppressWarnings("rawtypes")
	public void setLeaveType(List leaveType) {
		this.leaveType = leaveType;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
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

	public String getMinCount() {
		return minCount;
	}

	public void setMinCount(String minCount) {
		this.minCount = minCount;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeaveLeft() {
		return leaveLeft;
	}

	public void setLeaveLeft(String leaveLeft) {
		this.leaveLeft = leaveLeft;
	}

	public int getNoOfTotalWorkingDays() {
		return noOfTotalWorkingDays;
	}

	public void setNoOfTotalWorkingDays(int noOfTotalWorkingDays) {
		this.noOfTotalWorkingDays = noOfTotalWorkingDays;
	}

	public double getCarryForward() {
		return carryForward;
	}

	public void setCarryForward(double carryForward) {
		this.carryForward = carryForward;
	}

	public double getExtraWorking() {
		return extraWorking;
	}

	public void setExtraWorking(double extraWorking) {
		this.extraWorking = extraWorking;
	}

	public double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public double getCarryForwar4Month() {
		return carryForwar4Month;
	}

	public void setCarryForwar4Month(double carryForwar4Month) {
		this.carryForwar4Month = carryForwar4Month;
	}

	public String getLeaveID() {
		return leaveID;
	}

	public void setLeaveID(String leaveID) {
		this.leaveID = leaveID;
	}

	public String getLeaveStatusTemp() {
		return leaveStatusTemp;
	}

	public void setLeaveStatusTemp(String leaveStatusTemp) {
		this.leaveStatusTemp = leaveStatusTemp;
	}

	public double getTotalLeaveAllowed() {
		return totalLeaveAllowed;
	}

	public void setTotalLeaveAllowed(double totalLeaveAllowed) {
		this.totalLeaveAllowed = totalLeaveAllowed;
	}

	public String getLeaveNextMonth_leaves() {
		return LeaveNextMonth_leaves;
	}

	public void setLeaveNextMonth_leaves(String leaveNextMonth_leaves) {
		LeaveNextMonth_leaves = leaveNextMonth_leaves;
	}

	public double getTotalLeaveAvailed() {
		return totalLeaveAvailed;
	}

	public void setTotalLeaveAvailed(double totalLeaveAvailed) {
		this.totalLeaveAvailed = totalLeaveAvailed;
	}

	public double getTotalLeaveBalance() {
		return totalLeaveBalance;
	}

	public void setTotalLeaveBalance(double totalLeaveBalance) {
		this.totalLeaveBalance = totalLeaveBalance;
	}

	public double getEmpLeaveBalance() {
		return empLeaveBalance;
	}

	public void setEmpLeaveBalance(double empLeaveBalance) {
		this.empLeaveBalance = empLeaveBalance;
	}

	public List<Object> getViewResponse1() {
		return viewResponse1;
	}

	public void setViewResponse1(List<Object> viewResponse1) {
		this.viewResponse1 = viewResponse1;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFlagtemp() {
		return flagtemp;
	}

	public void setFlagtemp(String flagtemp) {
		this.flagtemp = flagtemp;
	}

	public String getFdatetemp() {
		return fdatetemp;
	}

	public void setFdatetemp(String fdatetemp) {
		this.fdatetemp = fdatetemp;
	}

	public String getTdatetemp() {
		return tdatetemp;
	}

	public void setTdatetemp(String tdatetemp) {
		this.tdatetemp = tdatetemp;
	}

	public String getOdatetemp() {
		return odatetemp;
	}

	public void setOdatetemp(String odatetemp) {
		this.odatetemp = odatetemp;
	}

	public String getFtimetemp() {
		return ftimetemp;
	}

	public void setFtimetemp(String ftimetemp) {
		this.ftimetemp = ftimetemp;
	}

	public String getTtimetemp() {
		return ttimetemp;
	}

	public void setTtimetemp(String ttimetemp) {
		this.ttimetemp = ttimetemp;
	}

	public String getStoreFdate() {
		return storeFdate;
	}

	public void setStoreFdate(String storeFdate) {
		this.storeFdate = storeFdate;
	}

	public String getStoreTdate() {
		return storeTdate;
	}

	public void setStoreTdate(String storeTdate) {
		this.storeTdate = storeTdate;
	}

	public String getStoreOdate() {
		return storeOdate;
	}

	public void setStoreOdate(String storeOdate) {
		this.storeOdate = storeOdate;
	}

	public String getApproveOnTemp() {
		return approveOnTemp;
	}

	public void setApproveOnTemp(String approveOnTemp) {
		this.approveOnTemp = approveOnTemp;
	}
	
	

}