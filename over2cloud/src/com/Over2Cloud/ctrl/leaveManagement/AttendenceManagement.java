
package com.Over2Cloud.ctrl.leaveManagement;

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xpath.internal.operations.And;

@SuppressWarnings("serial")
public class AttendenceManagement extends GridPropertyBean implements
		ServletRequestAware
{
	static final Log log = LogFactory.getLog(LeaveRequest.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> timeSlotColumnTime = null;
	private String empname;
	private String fdate;
	private String tdate;
	private String modifyFlag;
	private String download4;
	private String tableName;
	private String excelName;
	private String downloadType;
	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	private String[] columnNames;
	private Map<String, String> columnMap = null;
	private List<ConfigurationUtilBean> columnMap2 = null;
	private File document;
	private String documentContentType;
	private String documentFileName;
	private String empStatus;
	private InputStream fileInputStream;
	private String fileName;
	private Map<Integer, String> employeeType = null;
	Map<String, String> attachFileMap = new LinkedHashMap<String, String>();
	private List<ConfigurationUtilBean> timeSlotColumnDrop = null;
	private List<ConfigurationUtilBean> attendanceColumnDropDown = null;
	private List<ConfigurationUtilBean> attendanceColumnDate = null;
	private List<ConfigurationUtilBean> reportColumnText = null;
	private List<ConfigurationUtilBean> attendanceMarkColumnDate = null;
	private List<ConfigurationUtilBean> attendanceMarkColumnText = null;
	private List<ConfigurationUtilBean> attendanceMarkColumnTime = null;
	private List<ConfigurationUtilBean> attendanceMarkColumnDropDown = null;
	private List<ConfigurationUtilBean> attendanceConfigColumnDate = null;
	private List<ConfigurationUtilBean> attendanceConfigColumnText = null;
	private List<ConfigurationUtilBean> attendanceConfigColumnDrop = null;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;
	private List<Object> viewReportAttendance;
	private String id;
	private int noOfTotalWorkingDays;
	private double carryForward;
	private double extraWorking;
	private double totalDeduction;
	private double carryForwar4Month;
	private List<GridDataPropertyView> slotGridColomnsNames = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> summaryColomnsNames = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
	private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private Map<Integer, String> employeelist = new HashMap<Integer, String>();
	private Map<Integer, String> employeelist1 = new HashMap<Integer, String>();
	private String dept;
	private String deptOrSubDeptId;
	private String mainHeaderName;
	private List<Object> viewTimeSlot;
	private List<GridDataPropertyView> reportGridColoumnNames = new ArrayList<GridDataPropertyView>();
	private List<Object> summaryView;
	private String emp;
	private Map<Integer, String> deptDataList = new LinkedHashMap<Integer, String>();
	
	private JSONObject responseDetailsJson = null;
	private JSONArray jsonArray = null;
	private String deptId;
	private String SubdeptId;
	private Map<String, String> deptName;
	private String fromTimeDate;
	
	private String f1date;
	private String t1date;
	
	private String counter1;
	private int chCounter;
	private int chCounter1;
	private int chCounter2;
	private int chCounter3;
	private int chCounter4;
	private int chCounter5;
	private int eworkcount;
    private int chCounter6 ;
    private String firstDay ;
    private String actionStatus1;
    private String actionStatus;

	@SuppressWarnings(
	{ "rawtypes" })
	public String beforeTimeSlot()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List<GridDataPropertyView> slotColumnList = Configuration.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,"table_name", "time_slot_configuration");
				timeSlotColumnTime = new ArrayList<ConfigurationUtilBean>();
				timeSlotColumnDrop = new ArrayList<ConfigurationUtilBean>();
				if (slotColumnList != null && slotColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : slotColumnList)
					{
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("Time")
								&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							timeSlotColumnTime.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							timeSlotColumnDrop.add(cub);
						}
					}
				}
				LeaveHelper LH=new LeaveHelper(); 
				String data[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				//deptDataList = new HashMap<Integer, String>();
				List dataList=LH.getDeptName("HR",data[4],connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					Object[] object3=null;
				    for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();) 
				    {
						object3 = (Object[]) iterator2.next();
						deptDataList.put((Integer)object3[0], object3[1].toString());
				    }
			    }
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	

	public String viewTimeSLot()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Time Slot >> View");

				setGridColomnNames();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void setGridColomnNames()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session
				.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		slotGridColomnsNames.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_time_slot_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"time_slot_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						slotGridColomnsNames.add(gpv);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String getTimeSlotView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from time_slot");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					Object obdata=null;
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
					     obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList(
							"mapped_time_slot_configuration", accountID,
							connectionSpace, "time_slot_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						Object obdata1=null;
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields
						    obdata1 = (Object) it.next();
							if (obdata1 != null)
							{
								if (obdata1.toString().equalsIgnoreCase("empname"))
								{
									if (i < fieldNames.size() - 1)
										query.append("emp1.empname,");
									else
										query.append("emp1.empname");
								}
								else
								{
									if (i < fieldNames.size() - 1)
										query.append("tr." + obdata1.toString()
												+ ",");
									else
										query.append("tr." + obdata1.toString());
								}
							}
							i++;
						}
					}
					query.append(" from time_slot tr INNER JOIN compliance_contacts cc on tr.empname= cc.id ");
					query.append( " INNER JOIN employee_basic AS emp1 ON emp1.id=cc.emp_id ");
					query.append( " where emp1.flag=0 and cc.work_status=0 "  );
					query.append("order by emp1.empname");
					System.out.println("query VIEW>>>>>>>>>"   +query);
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					}
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);

					if (data!=null && data.size()>0)
					{
						viewTimeSlot = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						Object[] obdata1=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
						    obdata1 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{

								if (obdata1[k] != null)
								{

									if (k == 0)
										one.put(fieldNames.get(k).toString(),
												(Integer) obdata1[k]);
									else
										one.put(fieldNames.get(k).toString(),
												obdata1[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewTimeSlot(Listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String modifyTimeSlot()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& parmName != null
								&& !parmName.equalsIgnoreCase("")
								&& !parmName.equalsIgnoreCase("id")
								&& !parmName.equalsIgnoreCase("oper")
								&& !parmName.equalsIgnoreCase("rowid")
								&& !parmName.equalsIgnoreCase("empname"))
							wherClause.put(parmName, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("time_slot", wherClause, condtnBlock,
							connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("time_slot", "id", condtIds
							.toString(), connectionSpace);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String beforeReportAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> attendanceColumnList = Configuration.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,"table_name", "attendance_record_configuration");
				attendanceColumnDate = new ArrayList<ConfigurationUtilBean>();
				attendanceColumnDropDown = new ArrayList<ConfigurationUtilBean>();
				if (attendanceColumnList != null&& attendanceColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : attendanceColumnList)
					{
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("Date")&& !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("date1"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceColumnDate.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D")&& !gdp.getColomnName().equalsIgnoreCase("status")
								&& !gdp.getColomnName().equalsIgnoreCase("attendence")
								&& !gdp.getColomnName().equalsIgnoreCase("lupdateto")
								&& !gdp.getColomnName().equalsIgnoreCase("lupdate")
								&& !gdp.getColomnName().equalsIgnoreCase("clientVisit"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceColumnDropDown.add(cub);
						}
					}
				}
				LeaveHelper LH=new LeaveHelper(); 
				String data[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				//deptDataList = new HashMap<Integer, String>();
				if (data!=null && !data.toString().equalsIgnoreCase(""))
				{
					List dataList=LH.getDeptName("HR",data[4],connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						Object[] object3=null;
					    for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();) 
					    {
							object3 = (Object[]) iterator2.next();
							deptDataList.put((Integer)object3[0], object3[1].toString());
					    }
				    }
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	public String beforeReportGridAction()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Attendance Day Wise Report >> View");
				setGridColomnNames1();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private void setGridColomnNames1()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			reportGridColoumnNames.add(gpv);
			
			if (modifyFlag!=null && modifyFlag.equalsIgnoreCase("0"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("empname");
				gpv.setHeadingName("Employee");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				reportGridColoumnNames.add(gpv);
			}
			System.out.println("reportGridColoumnNam     "+reportGridColoumnNames.size());
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_time_slot_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"attendance_record_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("fdate")
							&& !gdp.getColomnName().equalsIgnoreCase("tdate")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName().equalsIgnoreCase("empname")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"attendence")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("eworking")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"clientVisit")
							&& !gdp.getColomnName().equalsIgnoreCase("lupdate")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"lupdateto"))
					{
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
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	
	public String getColumn4Download()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("getColumn4Download method" + download4);
				if (download4 != null && download4.equals("viewAttendanceMark"))
				{
					returnResult = getColumnName(
							"mapped_time_slot_configuration",
							"attendance_record_configuration",
							"attendence_record");
					System.out.println("returnResult" + returnResult);
					tableName = "attendence_record";
					excelName = "Attendance Record";
				}

			}
			catch (Exception e)
			{
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method getColumn4Download of class "
										+ getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName, String basicTableName,
			String a)
	{
		String returnResult = ERROR;
		try
		{
			List<GridDataPropertyView> columnList = Configuration
					.getConfigurationData(mappedTableName, accountID,
							connectionSpace, "", 0, "table_name",
							basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			columnMap2 = new ArrayList<ConfigurationUtilBean>();
			if (columnList != null && columnList.size() > 0)
			{
				for (GridDataPropertyView gdp1 : columnList)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();

					if (a.equalsIgnoreCase("attendence_record"))
					{
						if (!gdp1.getColomnName().equalsIgnoreCase("lupdateto")
								&& !gdp1.getColomnName().equals("lupdate")
								&& !gdp1.getColomnName().equals("clientVisit")
								&& !gdp1.getColomnName().equals("eworking")
								&& !gdp1.getColomnName().equals("attendence")
								&& !gdp1.getColomnName().equals("status")
								&& !gdp1.getColomnName().equals("fdate")
								&& !gdp1.getColomnName().equals("tdate")
								&& !gdp1.getColomnName().equals("userName")
								&& !gdp1.getColomnName().equals("date")
								&& !gdp1.getColomnName().equals("time")
								&& !gdp1.getColomnName().equals("id")
								)
						{
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey(gdp1.getColomnName());
							System.out.println("gdp1.getColomnName() "
									+ gdp1.getColomnName());
							System.out.println("gdp1.getHeadingName() "
									+ gdp1.getHeadingName());
							obj.setValue(gdp1.getHeadingName());
							columnMap2.add(obj);
							columnMap.put(gdp1.getColomnName(), gdp1
									.getHeadingName());
						}
					}

				}
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				returnResult = SUCCESS;
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method getColumnName of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
	}
/*	 @SuppressWarnings(
				{ "unchecked", "rawtypes" })
				public String attendanceReportView()
				{

					String returnResult = ERROR;
					boolean sessionFlag = ValidateSession.checkSession();
					if (sessionFlag)
					{
						try
						{
							CommonOperInterface cbt = new CommonConFactory().createInterface();
							StringBuilder query1 = new StringBuilder("");
							query1.append("select count(*) from attendence_record");

							List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);

							if (dataCount != null && dataCount.size() > 0)
							{
								BigInteger count = new BigInteger("3");
								Object obdata=null;
								for (Iterator it = dataCount.iterator(); it.hasNext();)
								{
									obdata = (Object) it.next();
									count = (BigInteger) obdata;
								}
								setRecords(count.intValue());
								int to = (getRows() * getPage());
								if (to > getRecords())
									to = getRecords();
								// getting the list of colmuns
								StringBuilder query = new StringBuilder("");
								query.append("select ");
								List fieldNames = Configuration.getColomnList("mapped_time_slot_configuration", accountID,
										connectionSpace, "attendance_record_configuration");
								int i = 0;
								List fieldNames2 = new ArrayList<String>();
								if (fieldNames != null && fieldNames.size() > 0)
								{
									Object obdata1=null;
									for (Iterator it = fieldNames.iterator(); it.hasNext();)
									{
										
									     obdata1 = (Object) it.next();
										if (obdata1 != null && !obdata1.equals("fdate") 
												&& !obdata1.equals("tdate")
												&& !obdata1.equals("userName")
												&& !obdata1.equals("date")
												&& !obdata1.equals("time")
												&& !obdata1.equals("status")
												&& !obdata1.equals("attendence")
												&& !obdata1.equals("eworking")
												&& !obdata1.equals("clientVisit")
												&& !obdata1.equals("lupdate")
												&& !obdata1.equals("lupdateto"))
										{
											fieldNames2.add(obdata1);
											if (i < fieldNames.size() - 12)
												query.append("ar." + obdata1.toString()
														+ ",");
											else if (obdata1.toString().equalsIgnoreCase(
													"empname"))
											{
												query.append("emp.empName");
											}
											else
											{
												query.append(obdata1.toString());
											}

										}
										i++;
									}
								}
								query.append(" FROM attendence_record AS ar INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id ");   
								query.append( " INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
								
								if (  modifyFlag != null && modifyFlag.equalsIgnoreCase("0") )
								{
									
								}
								else if (modifyFlag != null && modifyFlag.equalsIgnoreCase("show") )
								{
									LeaveHelper LH=new LeaveHelper(); 
									String empId=(String) session.get("empIdofuser").toString().split("-")[1];
								    String data=LH.getContactId("HR", connectionSpace, empId);
								    System.out.println("data   "+data);
									if(data!=null )
									{
										query.append("where ar.empname IN (" + data+ ")");
									}
									if (getTdate()!=null) 
									{
										tdate=DateUtil.convertDateToUSFormat(tdate);
										//String tdate=DateUtil.getLastDateFromLastDate(-2, DateUtil.getCurrentDateUSFormat());
										String str[]=tdate.split("-");
										String finalString=str[0]+"-"+str[1];
										query.append("AND ar.date1  LIKE '%"+finalString+"%'" );
									}
									else 
									{
										query.append("AND MONTH(ar.date1) = MONTH(CURDATE())");
									
									}
								}
								else
								{
									query.append("where ar.empname='" + getEmpname()
											+ "' AND ar.date1 between '"
											+ DateUtil.convertDateToUSFormat(getFdate())
											+ "' AND '"
											+ DateUtil.convertDateToUSFormat(getTdate())
											+ "'");
								}
								query.append( " and emp.flag=0 and cc.work_status=0 ");
								if (getSearchField() != null && getSearchString() != null
										&& !getSearchField().equalsIgnoreCase("")
										&& !getSearchString().equalsIgnoreCase(""))
								{
									query.append(" where ");
									// add search query based on the search operation
									if (getSearchOper().equalsIgnoreCase("eq"))
									{
										query.append(" " + getSearchField() + " = '"
												+ getSearchString() + "'");
									}
									else if (getSearchOper().equalsIgnoreCase("cn"))
									{
										query.append(" " + getSearchField() + " like '%"
												+ getSearchString() + "%'");
									}
									else if (getSearchOper().equalsIgnoreCase("bw"))
									{
										query.append(" " + getSearchField() + " like '"
												+ getSearchString() + "%'");
									}
									else if (getSearchOper().equalsIgnoreCase("ne"))
									{
										query.append(" " + getSearchField() + " <> '"
												+ getSearchString() + "'");
									}
									else if (getSearchOper().equalsIgnoreCase("ew"))
									{
										query.append(" " + getSearchField() + " like '%"
												+ getSearchString() + "'");
									}
								}
								query.append("order by date1 asc");

								System.out.println("QUERY ISA SS  " + query.toString());
								List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if (data != null && data.size() > 0)
								{
									viewReportAttendance = new ArrayList<Object>();
									List<Object> Listhb = new ArrayList<Object>();
									Object[] obdata1=null;
									for (Iterator it = data.iterator(); it.hasNext();)
									{
										obdata1 = (Object[]) it.next();
										Map<String, Object> one = new HashMap<String, Object>();
										for (int k = 0; k < fieldNames2.size(); k++)
										{
											if (obdata1[k] != null)
											{
												if (fieldNames2.get(k).toString().equals("date1"))
												{
													one.put(fieldNames2.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
												}
												else
												{
													one.put(fieldNames2.get(k).toString(),obdata1[k].toString());
												}
											}
										}
										Listhb.add(one);
									}
									setViewReportAttendance(Listhb);
									setRecords(viewReportAttendance.size());
									setTotal((int) Math.ceil((double) getRecords()/ (double) getRows()));
								}
							}
							returnResult = SUCCESS;
						}
						catch (Exception e)
						{
							returnResult = ERROR;
							e.printStackTrace();
						}
					}
					else
					{
						returnResult = LOGIN;
					}
					return returnResult;
				}*/
	
	@SuppressWarnings(
			{ "unchecked", "rawtypes" })
			public String attendanceReportView()
			{

				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
				{
					try
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						StringBuilder query1 = new StringBuilder("");
						query1.append("select count(*) from attendence_record");

						List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);

						if (dataCount != null && dataCount.size() > 0)
						{
							BigInteger count = new BigInteger("3");	
							Object obdata=null;
							for (Iterator it = dataCount.iterator(); it.hasNext();)
							{
								obdata = (Object) it.next();
								count = (BigInteger) obdata;
							}
							setRecords(count.intValue());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							// getting the list of colmuns
							StringBuilder query = new StringBuilder("");
							query.append("select ");
							List fieldNames = Configuration.getColomnList("mapped_time_slot_configuration", accountID,
									connectionSpace, "attendance_record_configuration");
							int i = 0;
							List fieldNames2 = new ArrayList<String>();
							if (fieldNames != null && fieldNames.size() > 0)
							{
								Object obdata1=null;
								for (Iterator it = fieldNames.iterator(); it.hasNext();)
								{
									
								     obdata1 = (Object) it.next();
									if (obdata1 != null && !obdata1.equals("fdate") 
											&& !obdata1.equals("tdate")
											&& !obdata1.equals("userName")
											&& !obdata1.equals("date")
											&& !obdata1.equals("time")
											&& !obdata1.equals("status")
											&& !obdata1.equals("attendence")
											&& !obdata1.equals("eworking")
											&& !obdata1.equals("clientVisit")
											&& !obdata1.equals("lupdate")
											&& !obdata1.equals("lupdateto"))
									{
										fieldNames2.add(obdata1);
										if (i < fieldNames.size() - 12)
											query.append("ar." + obdata1.toString()
													+ ",");
										else if (obdata1.toString().equalsIgnoreCase(
												"empname"))
										{
											query.append("emp.empName");
										}
										else
										{
											query.append(obdata1.toString());
										}

									}
									i++;
								}
							}
							query.append(" FROM attendence_record AS ar INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id ");   
							query.append( " INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");

							if (  modifyFlag != null && modifyFlag.equalsIgnoreCase("0") )
							{
								
							}
							else if (modifyFlag != null && modifyFlag.equalsIgnoreCase("show") )
							{
								LeaveHelper LH=new LeaveHelper(); 
								String empId=(String) session.get("empIdofuser").toString().split("-")[1];
							    String data=LH.getContactId("HR", connectionSpace, empId);
							    System.out.println("data   "+data);
								if(data!=null )
								{
									query.append("where ar.empname IN (" + data+ ")");
								}


										if (getTdate()!=null && getFdate()!=null) 
										{
									
								                

					                         String str[]=getTdate().split("-");
											 String str1[]=getFdate().split("-");

											 String finalString=str[0];
											 String finalString1=str1[0];

											if(finalString.length()!=4 && finalString1.length()!=4)
											{
												fdate=DateUtil.convertDateToUSFormat(fdate);
								                tdate=DateUtil.convertDateToUSFormat(tdate);

                                             
											}

									//String tdate=DateUtil.getLastDateFromLastDate(-2, DateUtil.getCurrentDateUSFormat());
								/*	String str[]=tdate.split("-");
									String finalString=str[0]+"-"+str[1];
									//query.append("AND ar.date1  LIKE '%"+finalString+"%'" );
									query.append("AND ar.date1  LIKE '%"+finalString+"%'" );
									*/
								query.append("AND ar.date1  between "+"'"+fdate+"'"+ " AND "+"'"+tdate +"'");



										}
								else 
								{
									query.append("AND MONTH(ar.date1) = MONTH(CURDATE())");
								
								}
							}
							else
							{
								query.append("where ar.empname='" + getEmpname()
										+ "' AND ar.date1 between '"
										+ DateUtil.convertDateToUSFormat(getFdate())
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(getTdate())
										+ "'");
							}
							query.append( " and emp.flag=0 and cc.work_status=0 ");
							if (getSearchField() != null && getSearchString() != null
									&& !getSearchField().equalsIgnoreCase("")
									&& !getSearchString().equalsIgnoreCase(""))
							{
								query.append(" where ");
								// add search query based on the search operation
								
								if (getSearchOper().equalsIgnoreCase("eq"))
								{
									query.append(" " + getSearchField() + " = '"
											+ getSearchString() + "'");
								}
								else if (getSearchOper().equalsIgnoreCase("cn"))
								{
									query.append(" " + getSearchField() + " like '%"
											+ getSearchString() + "%'");
								}
								else if (getSearchOper().equalsIgnoreCase("bw"))
								{
									query.append(" " + getSearchField() + " like '"
											+ getSearchString() + "%'");
								}
								else if (getSearchOper().equalsIgnoreCase("ne"))
								{
									query.append(" " + getSearchField() + " <> '"
											+ getSearchString() + "'");
								}
								else if (getSearchOper().equalsIgnoreCase("ew"))
								{
									query.append(" " + getSearchField() + " like '%"
											+ getSearchString() + "'");
								}
							}
							

								
						System.out.println("actionStatus >>>>    " +actionStatus);
								if(actionStatus.equals("Ontime"))
								{

								query.append("AND beforetime LIKE '%On%' ");
								}
							
								if(actionStatus.equals("Late Coming"))
								{

								query.append("AND beforetime LIKE '%Late%' ");
								}

								if(actionStatus.equals("Early Going"))
								{

								query.append("AND beforetime LIKE '%Early%' ");
								}

								if(actionStatus.equals("All time"))
								{

								query.append("AND beforetime LIKE '% %' ");
								}

								if(actionStatus1.equals("Absent Type"))
								{

								query.append("AND beforetime LIKE '% %' ");
								}


								if(actionStatus1.equals("Intimated"))
								{

								
									query.append("AND ar.lupdate LIKE '%Call%' OR LIKE '%SMS%' OR LIKE '%VMN%' OR LIKE '%Pre%'");
								}
								if(actionStatus1.equals("Nonintimated"))
								{

								query.append("AND ar.lupdate LIKE '%No%' ");
								}


							query.append("order by date1 asc");
                   System.out.println("hjdskhfbskjgkjgfjgfdghdfjkgdfkj"+query);
							System.out.println("QUERY ISA SS  " + query.toString());
							List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if (data != null && data.size() > 0)
							{
								viewReportAttendance = new ArrayList<Object>();
								List<Object> Listhb = new ArrayList<Object>();
								Object[] obdata1=null;
								for (Iterator it = data.iterator(); it.hasNext();)
								{
									obdata1 = (Object[]) it.next();
									Map<String, Object> one = new HashMap<String, Object>();
									for (int k = 0; k < fieldNames2.size(); k++)
									{
										if (obdata1[k] != null)
										{
											if (fieldNames2.get(k).toString().equals("date1"))
											{
												one.put(fieldNames2.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
											}
											else
											{
												one.put(fieldNames2.get(k).toString(),obdata1[k].toString());
											}
										}
									}
									Listhb.add(one);
								}
								setViewReportAttendance(Listhb);
								setRecords(viewReportAttendance.size());
								setTotal((int) Math.ceil((double) getRecords()/ (double) getRows()));
							}
						}
						returnResult = SUCCESS;
					}
					catch (Exception e)
					{
						returnResult = ERROR;
						e.printStackTrace();
					}
				}
				else
				{
					returnResult = LOGIN;
				}
				return returnResult;
			}
	
	
	
	/*@SuppressWarnings("unchecked")
	public String modifyattendanceGridData()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					System.out.println("Parmname" + Parmname);
					System.out.println("paramValue" + paramValue);

					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				System.out.println("id" + id);
				condtnBlock.put("id", getId());
				System.out.println("condtIds.toString() " + condtnBlock);
				cbt.updateTableColomn("attendence_record", wherClause,
						condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length)
						condtIds.append(H + " ,");
					else
						condtIds.append(H);
					i++;
				}
				System.out
						.println("condtIds.toString() " + condtIds.toString());
				cbt.deleteAllRecordForId("attendence_record", "id", condtIds
						.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{

			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;
	}*/

	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public String modifyattendanceGridData()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					if(getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue=request.getParameter(parmName);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
									&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid")&& !parmName.equalsIgnoreCase("empname"))
							{
								if (parmName.equalsIgnoreCase("date1") ) 
								{
									wherClause.put(parmName,DateUtil.convertDateToUSFormat(paramValue));
								} 
								else 
								{
									wherClause.put(parmName, paramValue);
								}
							}
						}
						condtnBlock.put("id",getId());
						cbt.updateTableColomn("attendence_record", wherClause, condtnBlock,connectionSpace);
					}
					else if(getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						String tempIds[]=getId().split(",");
						StringBuilder condtIds=new StringBuilder();
							int i=1;
							for(String H:tempIds)
							{
								if(i<tempIds.length)
									condtIds.append(H+" ,");
								else
									condtIds.append(H);
								i++;
							}
							cbt.deleteAllRecordForId("attendence_record", "id", condtIds.toString(), connectionSpace);
					}
					returnResult =SUCCESS;
				}
				catch(Exception e)
				{
					returnResult=ERROR;
					 e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}	
	 
	 
	 
	@SuppressWarnings(
	{ "rawtypes" })
	public String beforeReportDetailAction()
	{
		String returnString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				List<GridDataPropertyView> reportColumnList = Configuration.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,"table_name", "record_data_configuration");
				reportColumnText = new ArrayList<ConfigurationUtilBean>();
				if (reportColumnList != null && reportColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : reportColumnList)
					{
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							reportColumnText.add(cub);
						}
					}
				}
				String countHoliday = null;
				int valueAbsent;
				double valueHalfday;
				String query6 = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				int totalDays = DateUtil.countWeekendDays(DateUtil.convertDateToUSFormat(getTdate()));
				String query = "select count(*) from holiday_list where fdate between '"+ DateUtil.convertDateToUSFormat(getFdate())
						+ "' AND '"+ DateUtil.convertDateToUSFormat(getTdate()) + "'";
				List result = cbt.executeAllSelectQuery(query, connectionSpace);
				if (result != null && result.size() > 0)
				{
					countHoliday = result.get(0).toString();
				}
				noOfTotalWorkingDays = totalDays- Integer.parseInt(countHoliday);
				String query1 = "select sum(eworking) from attendence_record where empname='"+ getEmpname()
						+ "' AND date1 between '"+ DateUtil.convertDateToUSFormat(getFdate())+ "' AND '"
						+ DateUtil.convertDateToUSFormat(getTdate()) + "'";
				System.out.println("query1 ::::::  "  +query1);
				List resultExtraWorking = cbt.executeAllSelectQuery(query1,connectionSpace);

				if (resultExtraWorking != null && resultExtraWorking.size() > 0)
				{
					extraWorking = Double.parseDouble(resultExtraWorking.get(0).toString());
				}
				else
				{
					extraWorking = 0;
				}
				String query2 = "select count(*) from attendence_record where empname='"+ getEmpname()+ "' AND date1 between '"
						+ DateUtil.convertDateToUSFormat(getFdate())+ "' AND '"
						+ DateUtil.convertDateToUSFormat(getTdate())+ "' AND status='0'  AND attendence='Absent' ";
				System.out.println("query2 ::::::  "  +query2);
				String query3 = "select count(*) from attendence_record where empname='"+ getEmpname()+ "' AND date1 between '"
						+ DateUtil.convertDateToUSFormat(getFdate())+ "' AND '"+ DateUtil.convertDateToUSFormat(getTdate())+ "' AND status='0.5' AND attendence='Half Day' ";
				System.out.println("query3 ::::::  "  +query3);
				List leaveAbsentDetails = cbt.executeAllSelectQuery(query2,connectionSpace);
				List leaveHalfdayDetails = cbt.executeAllSelectQuery(query3,connectionSpace);
				if (leaveAbsentDetails != null && leaveAbsentDetails.size() > 0)
				{
					valueAbsent = Integer.parseInt(leaveAbsentDetails.get(0).toString());
				}
				else
				{
					valueAbsent = 0;
				}
				if (leaveHalfdayDetails != null&& leaveHalfdayDetails.size() > 0)
				{
					valueHalfday = (Integer.parseInt(leaveHalfdayDetails.get(0).toString())) * 0.5;
				}
				else
				{
					valueHalfday = 0 * 0.5;
				}
				double finalTotalLeaves = valueAbsent + valueHalfday;  
				String[] data=new LeaveHelper().getEmployeeDetails(getEmpname(), "HR", connectionSpace);
				if (data[2].equalsIgnoreCase("1"))
				{
					query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee'";
				
				}
				else
				{
					query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent'";
					
				}
				System.out.println("query6 :else::::::  "  +query6);
				double cf = 0;
				String date = DateUtil.getLastDateFromLastDate(-1, DateUtil.convertDateToUSFormat(getTdate()));
				String query7 = "select lnextmonth from report_data where empname='"+ getEmpname() + "' and tdate='" + date + "'";
				System.out.println("query7  " +query7);
				List getcarryForward = cbt.executeAllSelectQuery(query7,connectionSpace);
				if (getcarryForward != null && getcarryForward.size() > 0)
				{
					if (getcarryForward.get(0) != null)
					{
						cf = Double.parseDouble(getcarryForward.get(0).toString());
						String tot = new Double(cf).toString();
						if (data[2].equalsIgnoreCase("1"))
						{
							carryForward = cf;
						}
						else
						{
							if (tot != null)
							{
								if (tot.startsWith("-"))
								{
									carryForward = 0;
								}
								else
								{
									carryForward = cf;
								}
							}
						}
					}
					else
					{
						cf = 0;
						carryForward = cf;
					}
				}
				double finalLeaveGranted = 0;
				List leaveGrant = cbt.executeAllSelectQuery(query6,connectionSpace);
				if (leaveGrant != null && leaveGrant.size() > 0)
				{
					finalLeaveGranted = Double.parseDouble(leaveGrant.get(0).toString());
				}
				System.out.println("extraworking====" + extraWorking);
				System.out.println("final leave grntedd===" + finalLeaveGranted);
				System.out.println("cf====" + carryForward);
				System.out.println("final total leaves====" + finalTotalLeaves);

				totalDeduction = (finalLeaveGranted + extraWorking + carryForward)- finalTotalLeaves;
				carryForwar4Month = totalDeduction;
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnString;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addReportData()
	{
		String returnResult = ERROR;
		boolean checkStatus = false;
		String empEmailIDs = null;
		String empName = null;
		String intime = null;
		String outtime = null;
		String timeSlot = null;
		String query8 = null;
		String data1 = null;;
		String data2 = null;
		String data3 = null;
		String data4 = null;
		String tLeaves = null;
		Map<String, String> leaveStatus = new LinkedHashMap<String, String>();
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String employeeId = request.getParameter("empname");
				String tdate = request.getParameter("tdate");
				String fdate = request.getParameter("fdate");
				String totalWorkinDays = request.getParameter("twdays");
				String totalDeduction = request.getParameter("totalDeduction");
				String carryFrwd = request.getParameter("cf");
				String extraWorkng = request.getParameter("extraworking");
				String lnextmonth = request.getParameter("lnextmonth");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "record_data_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						if (gdp.getColomnName().equalsIgnoreCase("date"))
						{
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}
					}
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname("empname");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);

					TableColumes ob11 = new TableColumes();
					ob11 = new TableColumes();
					ob11.setColumnname("tdate");
					ob11.setDatatype("varchar(255)");
					ob11.setConstraint("default NULL");
					tableColume.add(ob11);

					TableColumes ob111 = new TableColumes();
					ob111 = new TableColumes();
					ob111.setColumnname("total_leaves");
					ob111.setDatatype("varchar(255)");
					ob111.setConstraint("default NULL");
					tableColume.add(ob111);

					TableColumes ob1111 = new TableColumes();
					ob1111 = new TableColumes();
					ob1111.setColumnname("granted_leaves");
					ob1111.setDatatype("varchar(255)");
					ob1111.setConstraint("default NULL");
					tableColume.add(ob1111);

					TableColumes ob1112 = new TableColumes();
					ob1112 = new TableColumes();
					ob1112.setColumnname("total");
					ob1112.setDatatype("varchar(255)");
					ob1112.setConstraint("default NULL");
					tableColume.add(ob1112);

					TableColumes ob1113 = new TableColumes();
					ob1113 = new TableColumes();
					ob1113.setColumnname("balance");
					ob1113.setDatatype("varchar(255)");
					ob1113.setConstraint("default NULL");
					tableColume.add(ob1113);

					cbt.createTable22("report_data", tableColume,connectionSpace);

					String query12 = "select sum(status) from attendence_record where empname='"
							+ employeeId
							+ "' AND date1 between '"
							+ DateUtil.convertDateToUSFormat(fdate)
							+ "' AND '"
							+ DateUtil.convertDateToUSFormat(tdate)
							+ "' and status IN('0','0.5') AND attendence IN('Absent','Half Day')";
					System.out.println("query12 :::" +query12);
					List valueCount = cbt.executeAllSelectQuery(query12,
							connectionSpace);
					if (valueCount != null && valueCount.size() > 0)
					{
						tLeaves = valueCount.get(0).toString();
					}
					else
					{
						tLeaves = "0";
					}

					double leaveGranted = new ReportAction().getLeaveGranted(
							employeeId, connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null
									&& !parmName
											.equalsIgnoreCase("deptHierarchy")
									&& !parmName.equalsIgnoreCase("deptname")
									&& !parmName.equalsIgnoreCase("subdeptname")
									&& !parmName.equalsIgnoreCase("tdate")
									&& !parmName.equalsIgnoreCase("fdate")
									&& !parmName
											.equalsIgnoreCase("total_leaves"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
							if (parmName.equalsIgnoreCase("tdate"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
								insertData.add(ob);
							}
						}
						if (tLeaves != null)
						{
							ob = new InsertDataTable();
							ob.setColName("total_leaves");
							ob.setDataName(tLeaves);
							insertData.add(ob);
						}
						ob = new InsertDataTable();
						ob.setColName("granted_leaves");
						ob.setDataName(leaveGranted);
						insertData.add(ob);

						System.out.println("leaveGranted   " + leaveGranted);

						ob = new InsertDataTable();
						ob.setColName("total");
						ob.setDataName(leaveGranted + extraWorkng
								+ Double.parseDouble(carryFrwd));
						insertData.add(ob);

						System.out.println(" leaveGranted +"
								+ "+ extraWorkng+"
								+ "+carryFrwd   "
								+ Double.parseDouble(String
										.valueOf(leaveGranted))
								+ Double.parseDouble(extraWorkng)
								+ Double.parseDouble(carryFrwd));
						ob = new InsertDataTable();
						ob.setColName("balance");
						ob.setDataName(lnextmonth);
						insertData.add(ob);

						System.out.println("lnextmonth  " + lnextmonth);
						String dateFormat = DateUtil.getMonthFromLastDate(DateUtil.convertDateToUSFormat(tdate));
						String query2 = "select * from report_data where empname='"+ employeeId + "' and tdate='"+ DateUtil.convertDateToUSFormat(tdate) + "'";
						System.out.println("query is as=====" + query2);
						List checkPrevi = cbt.executeAllSelectQuery(query2,
								connectionSpace);
						if (checkPrevi != null && checkPrevi.size() > 0)
						{
							checkStatus = true;
						}
						if (!checkStatus)
						{
							status = cbt.insertIntoTable("report_data",insertData, connectionSpace);
						}
						LeaveHelper LH=new LeaveHelper();
					    String[] empdata=	LH.getEmpDetailsByUserName("HR", userName,connectionSpace);
						
					    List empDetailsIDs = cbt.executeAllSelectQuery("SELECT emailIdOne,empName FROM employee_basic WHERE empId = '"
										+ employeeId + "'", connectionSpace);
					    System.out.println("empDetailsIDs " +empDetailsIDs.size());
					    if (empDetailsIDs != null && empDetailsIDs.size() > 0)
						{
							Object[] object=null;
							for (Iterator iterator = empDetailsIDs.iterator(); iterator
									.hasNext();)
							{
								object = (Object[]) iterator.next();
								empEmailIDs = object[0].toString();
								 System.out.println("empEmailIDs " +empEmailIDs);
								empName = object[1].toString();
								 System.out.println("empName " +empEmailIDs);
							}
						}
						String query = "select ftime,ttime from time_slot where empname='"
								+ employeeId + "'";
						List timming = cbt.executeAllSelectQuery(query,
								connectionSpace);
						if (timming != null && timming.size() > 0)
						{
							Object[] object=null;
							for (Iterator iterator = timming.iterator(); iterator
									.hasNext();)
							{
								object = (Object[]) iterator.next();
								intime = object[0].toString();
								outtime = object[1].toString();
							}
						}
						timeSlot = intime + " Hr. to " + outtime + " Hr.";

						query8 = "select lupdate,count(*) from attendence_record where lupdate IN('SMS','Mail','Call','No Intimation','VMN')  AND empname='"
								+ employeeId
								+ "' AND date1 between '"
								+ DateUtil.convertDateToUSFormat(fdate)
								+ "' AND '"
								+ DateUtil.convertDateToUSFormat(tdate)
								+ "' group by lupdate";
						System.out.println("update leave queryy=====" + query8);
						List detailsUpdate = cbt.executeAllSelectQuery(query8,
								connectionSpace);
						if (detailsUpdate != null && detailsUpdate.size() > 0)
						{
							Object[] object=null;
							for (Iterator iterator = detailsUpdate.iterator(); iterator
									.hasNext();)
							{
								object = (Object[]) iterator.next();
								data1 = object[0].toString();
								data2 = object[1].toString();
								if (data1.equalsIgnoreCase("SMS"))
								{
									leaveStatus.put("1.Via SMS:", data2);
								}
								else
								{
									leaveStatus.put("1.Via SMS:", "NIL");
								}
								if (data1.equalsIgnoreCase("Mail"))
								{
									leaveStatus.put(
											"2. Via Pre-Planned over Mail:",
											data2);
								}
								else
								{
									leaveStatus.put(
											"2. Via Pre-Planned over Mail:",
											"NIL");
								}
								if (data1.equalsIgnoreCase("Call"))
								{
									leaveStatus.put("3. Via Call:", data2);
								}
								else
								{
									leaveStatus.put("3. Via Call:", "NIL");
								}
								if (data1.equalsIgnoreCase("No Intimation"))
								{
									leaveStatus.put("4. Via No Intimation:",
											data2);
								}
								else
								{
									leaveStatus.put("4. Via No Intimation:",
											"NIL");
								}
								if (data1.equalsIgnoreCase("VMN"))
								{
									leaveStatus.put("5. Via VMN:", data2);
								}
								else
								{
									leaveStatus.put("5. Via VMN:", "NIL");
								}
							}
						}
						else
						{
							leaveStatus.put("1.Via SMS:", "NIL");
							leaveStatus.put("2. Via Pre-Planned over Mail:",
									"NIL");
							leaveStatus.put("3. Via Call:", "NIL");
							leaveStatus.put("4. Via No Intimation:", "NIL");
							leaveStatus.put("5. Via VMN:", "NIL");
						}
						String query9 = null;
						query9 = "select clientVisit,count(*) from attendence_record where clientVisit In('Full Day','Half Day') AND empname='"
								+ employeeId
								+ "' AND date1 between '"
								+ DateUtil.convertDateToUSFormat(fdate) 
								+ "' AND '"
								+ DateUtil.convertDateToUSFormat(tdate)
								+ "' group by clientVisit";
						Map<String, String> clientVit = new LinkedHashMap<String, String>();
						List clientVisitDetail = cbt.executeAllSelectQuery(
								query9, connectionSpace);
						if (clientVisitDetail != null
								&& clientVisitDetail.size() > 0)
						{
							Object[] object=null;
							for (Iterator iterator = clientVisitDetail
									.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								data3 = object[0].toString();
								data4 = object[1].toString();

								if (data3.equalsIgnoreCase("Full Day"))
								{
									clientVit.put("1.Full Day Client Visits:",
											data4);
								}
								else
								{
									clientVit.put("1.Full Day Client Visits:",
											"0");
								}
								if (data3.equalsIgnoreCase("Half Day"))
								{
									clientVit.put("2.Half Day Client Visits:",
											data4);
								}
								else
								{
									clientVit.put("2.Half Day Client Visits:",
											"0");
								}
							}
						}
						else
						{
							clientVit.put("1.Full Day Client Visits:", "0");
							clientVit.put("2.Half Day Client Visits:", "0");
						}
						String query10 = null;
						String query11 = null;
						String query13 = null;
						String query14 = null;
						String query15 = null;
						String query16 = null;
						Map<String, String> lateCount = new LinkedHashMap<String, String>();

						if (intime != null)
						{
							if (intime.equalsIgnoreCase("09:15"))
							{
								query10 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '08:15' AND '"
										+ intime + "'";
								System.out.println("query10 :" +query10);
								query11 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '"
										+ intime
										+ "' AND '09:30'";
								System.out.println("query11 :" +query11);
								query13 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '09:30' AND '09:45'";
								System.out.println("query13 :" +query13);
								query14 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '09:45' AND '10:15'";
								System.out.println("query14 :" +query14);
								query15 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '10:15' AND '11:15'";
								System.out.println("query15 :" +query15);
								query16 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '11:15' AND '18:30'";
								System.out.println("query16 :" +query16);
								if (cbt.executeAllSelectQuery(query10,
										connectionSpace) != null)
								{
									lateCount.put("1. On Or Before Time :", cbt
											.executeAllSelectQuery(query10,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount
											.put("1. On Or Before Time :", "0");
								}
								if (cbt.executeAllSelectQuery(query11,
										connectionSpace) != null)
								{
									lateCount.put("2. Up to 15 Mins :", cbt
											.executeAllSelectQuery(query11,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("2. Up to 15 Mins :", "0");
								}
								if (cbt.executeAllSelectQuery(query13,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>3. From 15 to 30 Mins :</font>",
													cbt.executeAllSelectQuery(
															query13,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("3. From 15 to 30 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query14,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>4. From 30 to 60 Mins :</font>",
													cbt.executeAllSelectQuery(
															query14,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("4. From 30 to 60 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query15,
										connectionSpace) != null)
								{
									lateCount.put("5. More 1hr to 2hr :", cbt
											.executeAllSelectQuery(query15,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("5. More 1hr to 2hr :", "0");
								}

								if (cbt.executeAllSelectQuery(query16,
										connectionSpace) != null)
								{
									lateCount.put("6. More Than 2 hrs :", cbt
											.executeAllSelectQuery(query16,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("6. More Than 2 hrs :", "0");
								}
							}
							if (intime.equalsIgnoreCase("09:30"))
							{
								query10 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '08:30' AND '"
										+ intime + "'";
								System.out.println("query10 :" +query10);
								
								query11 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '09:31' AND '09:45'";
								System.out.println("query11 :" +query11);
								
								query13 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '09:46' AND '10:00'";
								System.out.println("query13 :" +query13);
								
								query14 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '10:01' AND '10:30'";
								System.out.println("query14 :" +query14);
								
								query15 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '10:31' AND '11:30'";
								System.out.println("query15 :" +query15);
								
								query16 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '11:31' AND '18:30'";
								System.out.println("query14 :" +query14);
								
								if (cbt.executeAllSelectQuery(query10,
										connectionSpace) != null)
								{
									lateCount.put("1. On Or Before Time :", cbt
											.executeAllSelectQuery(query10,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount
											.put("1. On Or Before Time :", "0");
								}
								if (cbt.executeAllSelectQuery(query11,
										connectionSpace) != null)
								{
									lateCount.put("2. Up to 15 Mins :", cbt
											.executeAllSelectQuery(query11,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("2. Up to 15 Mins :", "0");
								}
								if (cbt.executeAllSelectQuery(query13,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>3. From 15 to 30 Mins :</font>",
													cbt.executeAllSelectQuery(
															query13,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("3. From 15 to 30 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query14,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>4. From 30 to 60 Mins :</font>",
													cbt.executeAllSelectQuery(
															query14,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("4. From 30 to 60 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query15,
										connectionSpace) != null)
								{
									lateCount.put("5. More 1hr to 2hr :", cbt
											.executeAllSelectQuery(query15,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("5. More 1hr to 2hr :", "0");
								}

								if (cbt.executeAllSelectQuery(query16,
										connectionSpace) != null)
								{
									lateCount.put("6. More Than 2 hrs :", cbt
											.executeAllSelectQuery(query16,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("6. More Than 2 hrs :", "0");
								}
							}
							if (intime.equalsIgnoreCase("10:00"))
							{
								query10 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '09:00' AND '"
										+ intime + "'";
								query11 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '"
										+ intime
										+ "' AND '10:15'";
								query13 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '10:15' AND '10:30'";
								query14 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '10:30' AND '11:00'";
								query15 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '11:00' AND '11:45'";
								query16 = "select count(*) from attendence_record where empname='"
										+ employeeId
										+ "' AND date1 between '"
										+ DateUtil.convertDateToUSFormat(fdate)
										+ "' AND '"
										+ DateUtil.convertDateToUSFormat(tdate)
										+ "' AND in_time between '11:45' AND '18:30'";
								if (cbt.executeAllSelectQuery(query10,
										connectionSpace) != null)
								{
									lateCount.put("1. On Or Before Time :", cbt
											.executeAllSelectQuery(query10,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount
											.put("1. On Or Before Time :", "0");
								}
								if (cbt.executeAllSelectQuery(query11,
										connectionSpace) != null)
								{
									lateCount.put("2. Up to 15 Mins :", cbt
											.executeAllSelectQuery(query11,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("2. Up to 15 Mins :", "0");
								}
								if (cbt.executeAllSelectQuery(query13,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>3. From 15 to 30 Mins :</font>",
													cbt.executeAllSelectQuery(
															query13,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("3. From 15 to 30 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query14,
										connectionSpace) != null)
								{
									lateCount
											.put(
													"<font color=red>4. From 30 to 60 Mins :</font>",
													cbt.executeAllSelectQuery(
															query14,
															connectionSpace)
															.get(0).toString());
								}
								else
								{
									lateCount.put("4. From 30 to 60 Mins :",
											"0");
								}
								if (cbt.executeAllSelectQuery(query15,
										connectionSpace) != null)
								{
									lateCount.put("5. More 1hr to 2hr :", cbt
											.executeAllSelectQuery(query15,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("5. More 1hr to 2hr :", "0");
								}

								if (cbt.executeAllSelectQuery(query16,
										connectionSpace) != null)
								{
									lateCount.put("6. More Than 2 hrs :", cbt
											.executeAllSelectQuery(query16,
													connectionSpace).get(0)
											.toString());
								}
								else
								{
									lateCount.put("6. More Than 2 hrs :", "0");
								}
							}
							LeaveHelper LM=new LeaveHelper();
							String[] empdData=new LeaveHelper().getEmployeeDetails(getEmpname(), "HR", connectionSpace);

							String mailBody = getMailBody(empdData[0], timeSlot,
									dateFormat, totalWorkinDays,
									totalDeduction, carryFrwd, extraWorkng,
									lnextmonth, leaveStatus, clientVit,
									lateCount, leaveGranted, tLeaves, carryFrwd);
							GenericMailSender mail = new GenericMailSender();
							new MsgMailCommunication().addMail("","",empdData[1],""+ empdData[0]
													+ " Analytical Attendance Sheet for "
													+ dateFormat + "", mailBody
													.toString(), "", "Pending",
											"0", "", "HR");
							/*new MsgMailCommunication().addMail("","","sumitib@dreamsol.biz",
											""
													+  empdData[0]
													+ " Analytical Attendance Sheet for "
													+ dateFormat + "", mailBody
													.toString(), "", "Pending",
											"0", "", "HR");*/
						/*	mail.sendMail("sumitib@dreamsol.biz", "" +  empdData[0] 
									+ " Analytical Attendance Sheet for "
									+ dateFormat + "", mailBody.toString(), "",
									"smtp.gmail.com", "465", "sumitib@dreamsol.biz",
									"");*/
							if (checkStatus)
							{
								addActionMessage("Data Exist Already!!!");
							}
							else
							{
								if (status)
								{
									addActionMessage("Data Save Successfully!!!");
								}
							}
							returnResult = SUCCESS;
						}
						else
						{
							addActionMessage("May Be Your Time Slot is not Added !!!!!!");
						}
					}
					else
					{
						addActionMessage("Oops There is some error in data!!!!");
					}
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getMailBody(String empname, String timeSlot, String tdate,
			String totaldays, String totalDedu, String caryfwd,
			String extraworking, String nextMonth, Map<String, String> leaveSt,
			Map<String, String> cilentV, Map<String, String> lateCount,
			double currentMonthLeave, String totaLeaves, String cfl)
	{
		StringBuilder mailText = new StringBuilder("");
		try
		{
			
			// Calculate Total Deduction 
			
			float totalDeduction = (Float.parseFloat("2")+Float.parseFloat(caryfwd)+Float.parseFloat(extraworking)) - Float.parseFloat(totaLeaves);
			
			mailText.append("Dear <b>" + empname + "</b>,");
			mailText.append("<table>");
			mailText.append("<tr><td><br></tr></td>");
			mailText
					.append("<tr><td>Kindly check the analytical attendance sheet for <b>"
							+ tdate + ".</b></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			mailText
					.append("<tr><td>1. Your <b>chosen time slot</b> is: <b><u>"
							+ timeSlot + ".</b></u></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			mailText.append("<tr><td><b>2. Total Leaves Status:</b></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			mailText.append("<tr><td>1. Total Working Days: " + totaldays
					+ "</tr></td>");
			mailText.append("<tr><td>2. Extra Working: " + extraworking
					+ "</tr></td>");
			mailText.append("<tr><td>3. Carry Forward Leaves: " + caryfwd
					+ "</tr></td>");
			mailText
					.append("<tr><td>4. Total Deductions (If Any): ={[(Current Month CL i.e. "
							+ currentMonthLeave
							+ ")+Extra Working +Carry Forward Leaves]-[(Total Leaves)]} =>  {("
							+ currentMonthLeave
							+ ")+("
							+ extraWorking
							+ ")+"
							+ cfl
							+ "]-[("
							+ totaLeaves
							+ ")}= "
							+ totalDeduction
							+ "</tr></td>");
			mailText.append("<tr><td>5. Carry Forward Leaves for Next Month: "
					+ nextMonth + "</tr></td>" + ".");
			mailText.append("<tr><td><br></tr></td>");
			mailText
					.append("<tr><td><b>3. Total No. of Leaves Availed Analysis:<b></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			for (Entry<String, String> entry : leaveSt.entrySet())
			{
				mailText.append("" + entry.getKey() + " " + entry.getValue()
						+ "");
				mailText.append("<tr><td></tr></td>");
			}
			mailText.append("<tr><td><br></tr></td>");
			mailText
					.append("<tr><td><b>4. Total No. of Client Visits Analysis:</b></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			for (Entry<String, String> entry : cilentV.entrySet())
			{
				mailText.append("" + entry.getKey() + " " + entry.getValue()
						+ "");
				mailText.append("<tr><td></tr></td>");
			}
			mailText.append("<tr><td><br></tr></td>");
			mailText
					.append("<tr><td><b>5. Total No. of Late Instances Analysis:</b></tr></td>");
			mailText.append("<tr><td><br></tr></td>");
			for (Entry<String, String> entry : lateCount.entrySet())
			{
				mailText.append("" + entry.getKey() + " " + entry.getValue()
						+ "");
				mailText.append("<tr><td></tr></td>");
			}
			mailText.append("<tr><td></tr></td>");
			mailText.append("<tr><td></tr></td>");
			mailText
					.append("<tr><td>We request you to kindly come on time as per chosen slot by you.</tr></td>");
			mailText.append("<tr><td></tr></td>");
			mailText
					.append("<tr><td>For any queries / suggestions, kindly let us know.</tr></td>");
			mailText.append("<table>");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailText.toString();
	}

	// For Attendance Mark Part...........
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String beforAttendanceAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				String empIdofuser = (String) session.get("empIdofuser");
				String userName = (String) session.get("uName");
				String level = null;
				if (empIdofuser == null
						|| empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List<GridDataPropertyView> slotColumnList = Configuration
						.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "attendance_record_configuration");
				attendanceMarkColumnDate = new ArrayList<ConfigurationUtilBean>();
				attendanceMarkColumnText = new ArrayList<ConfigurationUtilBean>();
				attendanceMarkColumnTime = new ArrayList<ConfigurationUtilBean>();
				attendanceMarkColumnDropDown = new ArrayList<ConfigurationUtilBean>();
				if (slotColumnList != null && slotColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : slotColumnList)
					{
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("Date")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"fdate")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"tdate"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceMarkColumnDate.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName().equalsIgnoreCase("day")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"working")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"timeslot")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"totalhour")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"beforetime")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"aftertime"))
						{

							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceMarkColumnText.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase(
								"Time")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceMarkColumnTime.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceMarkColumnDropDown.add(cub);
						}
					}
				}
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String empId = empIdofuser.split("-")[1].trim();
				/*String queydeg = "SELECT d.levelofhierarchy FROM designation as d INNER JOIN employee_basic as eb on eb.designation=d.id WHERE eb.id='"
						+ empId + "'";
				List degLevel = cbt.executeAllSelectQuery(queydeg,
						connectionSpace);
				if (degLevel != null && degLevel.size() > 0)
				{
					level = degLevel.get(0).toString();
				}
				String query2 = "select levelofhierarchy from designation where levelofhierarchy >'"
						+ level + "'";
				List allLevelHierarchy = cbt.executeAllSelectQuery(query2,
						connectionSpace);*/
				/*String levels = null;
				if (allLevelHierarchy != null && allLevelHierarchy.size() > 0)
				{
					Object object=null;
					for (Iterator iterator = allLevelHierarchy.iterator(); iterator
							.hasNext();)
					{
						object = (Object) iterator.next();
						levels = object.toString();
						String query3 = "select DISTINCT id from designation where levelofhierarchy='"
								+ levels + "'";
						List employeeDesig = cbt.executeAllSelectQuery(query3,
								connectionSpace);
						if (employeeDesig != null && employeeDesig.size() > 0)
						{
							for (Object object2 : employeeDesig)
							{
								String id = object2.toString();
								String query4 = "select id,empName from employee_basic ";
									//	+ "where designation='"+ id + "'";
								List employeeNAmes = cbt.executeAllSelectQuery(
										query4, connectionSpace);
								if (employeeNAmes != null
										&& employeeNAmes.size() > 0)
								{
									Object[] object3=null;
									
									for (Iterator iterator2 = employeeNAmes
											.iterator(); iterator2.hasNext();)
									{
									    object3 = (Object[]) iterator2
												.next();
										employeelist1.put((Integer) object3[0],
												object3[1].toString());
									}
								}
							}
						}
					}
				}*/
				/// TEMPORAY PART TO BE CORRECT 
				String query4 = "select emp.id,emp.empName from employee_basic as emp  INNER Join groupinfo as gi ON gi.id=emp.groupId WHERE gi.groupName ='Employee' ";
				
				List employeeNAmes = cbt.executeAllSelectQuery(
						query4, connectionSpace);
				if (employeeNAmes != null
						&& employeeNAmes.size() > 0)
				{
					Object[] object3=null;
					
					for (Iterator iterator2 = employeeNAmes
							.iterator(); iterator2.hasNext();)
					{
					    object3 = (Object[]) iterator2
								.next();
						employeelist1.put((Integer) object3[0],
								object3[1].toString());
					}
				}
				
				
						LeaveHelper LH=new LeaveHelper(); 
				String data[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				//deptDataList = new HashMap<Integer, String>();
				if (data!=null && !data.toString().equalsIgnoreCase(""))
				{
					List dataList=LH.getDeptName("HR",data[4],connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						Object[] object3=null;
					    for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();) 
					    {
							object3 = (Object[]) iterator2.next();
							deptDataList.put((Integer)object3[0], object3[1].toString());
					    }
				    }
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	public String beforeSummaryGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMainHeaderName("Summary Report >> View");
				setGridColomnNam();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public void setGridColomnNam()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			summaryColomnsNames.add(gpv);

			GridDataPropertyView gpv1 = new GridDataPropertyView();
			gpv1.setColomnName("Month");
			gpv1.setHeadingName("Month");
			gpv1.setHideOrShow("false");
			gpv1.setEditable("true");
			gpv1.setSearch("true");
			gpv1.setWidth(100);
			summaryColomnsNames.add(gpv1);

			GridDataPropertyView gpv2 = new GridDataPropertyView();
			gpv2.setColomnName("CL");
			gpv2.setHeadingName("Last C/F");
			gpv2.setHideOrShow("false");
			gpv2.setEditable("true");
			gpv2.setSearch("true");
			gpv2.setWidth(100);
			summaryColomnsNames.add(gpv2);

			GridDataPropertyView gpv4 = new GridDataPropertyView();
			gpv4.setColomnName("CurrentLeave");
			gpv4.setHeadingName("Current Leave");
			gpv4.setHideOrShow("false");
			gpv4.setEditable("true");
			gpv4.setSearch("true");
			gpv4.setWidth(100);
			summaryColomnsNames.add(gpv4);

			GridDataPropertyView gpv3 = new GridDataPropertyView();
			gpv3.setColomnName("Total");
			gpv3.setHeadingName("Total");
			gpv3.setHideOrShow("false");
			gpv3.setEditable("true");
			gpv3.setSearch("true");
			gpv3.setWidth(100);
			summaryColomnsNames.add(gpv3);

			GridDataPropertyView gpv5 = new GridDataPropertyView();
			gpv5.setColomnName("Balance");
			gpv5.setHeadingName("Balance");
			gpv5.setHideOrShow("false");
			gpv5.setEditable("true");
			gpv5.setSearch("true");
			gpv5.setWidth(100);
			summaryColomnsNames.add(gpv5);

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_time_slot_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"record_data_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("twdays")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"totalDeduction")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("empname"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						summaryColomnsNames.add(gpv);
					}
				}
			}
			session.put("ListSumaryColumnNAmes", summaryColomnsNames);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String summaryViewData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> list = new ArrayList<GridDataPropertyView>();
				if (session.containsKey("ListSumaryColumnNAmes"))
				{
					list = (List<GridDataPropertyView>) session
							.get("ListSumaryColumnNAmes");
					session.remove("ListSumaryColumnNAmes");
				}
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String query1 = "select count(*) from report_data";
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					Object obdata=null;
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
					    obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList(
							"mapped_time_slot_configuration", accountID,
							connectionSpace, "record_data_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						Object obdata1=null;
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields
							obdata1 = (Object) it.next();
							if (obdata1 != null && !obdata1.equals("id")
									&& !obdata1.equals("twdays")
									&& !obdata1.equals("totalDeduction")
									&& !obdata1.equals("date")
									&& !obdata1.equals("empname"))
							{
								if (i < fieldNames.size() - 1)
								{
									query.append(obdata1.toString() + ",");
								}
								else
								{
									query.append(obdata1.toString());
								}
							}
							i++;
						}
					}
					query.append("tdate,total_leaves");
					query.append(" from report_data");
					query.append(" where empname='" + getEmpname() + "'");
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					}
					//query.append(" limit " + from + "," + to);
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					String level = null;   
					String query6 = null;
					String[] tempData=new LeaveHelper().getEmployeeDetails(getEmpname(), "HR", connectionSpace);
					
					if (tempData[2] != null && tempData[2].equalsIgnoreCase("1"))
					{
						query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Trainee' ";
					}
					else
					{
						query6 = "SELECT sum(a.leaveallo) FROM leave_configuration as a INNER JOIN employee_type AS et ON et.id =a.empType WHERE et.etype='Permanent' ";
					}
					double finalLeaveGranted = 0;
					List leaveGrant = cbt.executeAllSelectQuery(query6,
							connectionSpace);
					if (leaveGrant != null && leaveGrant.size() > 0)
					{
						finalLeaveGranted = Double.parseDouble(leaveGrant
								.get(0).toString());
					}
					if (data != null && data.size() > 0)
					{
						if (list != null && list.size() > 0)
						{
							summaryView = new ArrayList<Object>();
							List<Object> Listhb = new ArrayList<Object>();
							List<Object> dataSummary = new ArrayList<Object>();
							Object[] obdata1=null;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								obdata1 = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpd : list)
								{
									double totalValue = (Double
											.parseDouble(obdata1[0].toString())
											+ finalLeaveGranted + Double
											.parseDouble(obdata1[1].toString()));
									if (obdata1 != null)
									{
										if (!gpd.getColomnName()
												.equalsIgnoreCase("id"))
										{
											if (gpd.getColomnName()
													.equalsIgnoreCase("month"))
											{
												one
														.put(
																gpd
																		.getColomnName(),
																DateUtil
																		.convertDateToUSFormat(obdata1[3]
																				.toString()));
												dataSummary.add(obdata1[3]
														.toString());
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase("cf"))
											{
												one.put(gpd.getColomnName(),
														obdata1[0].toString());
												dataSummary.add(obdata1[0]
														.toString());
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase("CL"))
											{
												one.put(gpd.getColomnName(),
														finalLeaveGranted);
												dataSummary
														.add(String
																.valueOf(finalLeaveGranted));
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase(
															"extraworking"))
											{
												one.put(gpd.getColomnName(),
														obdata1[1].toString());
												dataSummary.add(obdata1[1]
														.toString());
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase("Total"))
											{
												one.put(gpd.getColomnName(),
														totalValue);
												dataSummary.add(String
														.valueOf(totalValue));
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase(
															"CurrentLeave"))
											{
												one.put(gpd.getColomnName(),
														obdata1[4].toString());
												dataSummary.add(obdata1[4]
														.toString());
											}
											if (gpd
													.getColomnName()
													.equalsIgnoreCase("Balance"))
											{
												one.put(gpd.getColomnName(),
														obdata1[2].toString());
												dataSummary.add(obdata1[2]
														.toString());
											}
											if (gpd.getColomnName()
													.equalsIgnoreCase(
															"lnextmonth"))
											{
												one.put(gpd.getColomnName(),
														obdata1[2].toString());
												dataSummary.add(obdata1[2]
														.toString());
											}
										}
									}
								}
								Listhb.add(one);
							}
							setSummaryView(Listhb);
							if (dataSummary != null && dataSummary.size() > 0)
							{
								session.put("summaryData", dataSummary);
							}
							setTotal((int) Math.ceil((double) getRecords()
									/ (double) getRows()));
						}
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addAttendanceMark()
	{
		System.out.println("nfsnff,msd");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String day = null;
				String intime = null;
				String outtime = null;
				String totalTime = null;
				String finalIncommin = null;
				String finalOutgoing = null;
				String workingFlag = null;
				String in_time = request.getParameter("in_time");
				String out_time = request.getParameter("out_time");
				String clientVisit = request.getParameter("clientVisit");
				String empid = request.getParameter("empname");
				String date = request.getParameter("date1");
				String status1 = request.getParameter("status");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				day = DateUtil.findDayFromDate(DateUtil.convertDateToUSFormat(date));
				 System.out.println(" day ATTE:::FIND:;; " +day);
				    System.out.println(" FDP.getDate():;; " +date);
				if (day != null)
				{
					if (day.equalsIgnoreCase("Sunday")
							&& in_time.equalsIgnoreCase("00:00")
							&& out_time.equalsIgnoreCase("00:00"))
					{
						workingFlag = "Holiday";
					}
					else
					{
						workingFlag = "Working";
					}
				}
				if (in_time.equalsIgnoreCase("00:00")
						&& out_time.equalsIgnoreCase("00:00"))
				{
					totalTime = "NA";
				}
				else
				{
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);
				}
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String query = "select ftime,ttime from time_slot where empname='"
						+ empid + "'";
				List timming = cbt
						.executeAllSelectQuery(query, connectionSpace);
				if (timming != null && timming.size() > 0)
				{
					Object[] object=null;
					for (Iterator iterator = timming.iterator(); iterator
							.hasNext();)
					{
						object = (Object[]) iterator.next();
						intime = object[0].toString();
						outtime = object[1].toString();
					}
				}
				if (in_time.equalsIgnoreCase("00:00")
						&& out_time.equalsIgnoreCase("00:00")
						&& clientVisit.equalsIgnoreCase("Full Day"))
				{
					finalIncommin = "CV";
					finalOutgoing = "CV";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);
				}
				else if (in_time.equalsIgnoreCase("00:00")
						&& clientVisit.equalsIgnoreCase("Half day"))
				{
					String outgoingStatus = getIncommingStatus(out_time,outtime);
					finalOutgoing = getFinalIncommingstatus(outgoingStatus);
					finalIncommin = "CV";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);

				}
				else if (out_time.equalsIgnoreCase("00:00")
						&& clientVisit.equalsIgnoreCase("Half day"))
				{
					String incomingStatus = getIncommingStatus(in_time, intime);
					finalIncommin = getFinalIncommingstatus(incomingStatus);
					finalOutgoing = "CV";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);

				}
				else if (in_time.equalsIgnoreCase("00:00")
						&& clientVisit.equalsIgnoreCase("Full day"))
				{
					String outgoingStatus = getIncommingStatus(out_time,
							outtime);
					finalOutgoing = getFinalIncommingstatus(outgoingStatus);
					finalIncommin = "CV";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);

				}
				else if (out_time.equalsIgnoreCase("00:00")
						&& clientVisit.equalsIgnoreCase("Full day"))
				{
					String incomingStatus = getIncommingStatus(in_time, intime);
					finalIncommin = getFinalIncommingstatus(incomingStatus);
					finalOutgoing = "CV";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);

				}
				else if (in_time.equalsIgnoreCase(intime)
						&& out_time.equalsIgnoreCase(outtime))
				{
					finalIncommin = "On Time";
					finalOutgoing = "On Time";
					totalTime = DateUtil.findDifferenceTwoTime(in_time,
							out_time);
				}
				else if (in_time.equalsIgnoreCase("00:00")
						&& out_time.equalsIgnoreCase("00:00")
						&& day.equalsIgnoreCase("Sunday"))
				{
					workingFlag = "Holiday";
					finalIncommin = "Holiday";
					finalOutgoing = "Holiday";
				}
				else if (in_time.equalsIgnoreCase("00:00")
						&& out_time.equalsIgnoreCase("00:00")
						&& status1.equalsIgnoreCase("Holiday"))
				{
					workingFlag = "Holiday";
					finalIncommin = "Holiday";
					finalOutgoing = "Holiday";
				}
				else
				{
					if (in_time.equalsIgnoreCase("00:00"))
					{
						finalIncommin = "ABSENT";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);
					}
					else
					{
						if (in_time != null && intime != null)
						{
							String incomingStatus = getIncommingStatus(in_time,
									intime);
							if (incomingStatus.equalsIgnoreCase("0:0"))
							{
								finalIncommin = "On Time";
							}
							else
							{
								finalIncommin = getFinalIncommingstatus(incomingStatus);
								totalTime = DateUtil.findDifferenceTwoTime(
										in_time, out_time);
							}
						}
					}
					if (out_time.equalsIgnoreCase("00:00"))
					{
						finalOutgoing = "ABSENT";
						totalTime = DateUtil.findDifferenceTwoTime(in_time,
								out_time);
					}
					else
					{
						if (out_time != null && outtime != null)
						{
							String outgoingStatus = getIncommingStatus(
									out_time, outtime);
							if (outgoingStatus.equalsIgnoreCase("0:0"))
							{
								finalOutgoing = "On Time";
							}
							else
							{
								finalOutgoing = getFinalIncommingstatus(outgoingStatus);
								totalTime = DateUtil.findDifferenceTwoTime(
										in_time, out_time);
							}
						}
					}
				}
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "attendance_record_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("fdate")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"tdate")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time"))
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}
						if (day != null)
						{
							if (gdp.getColomnName().equalsIgnoreCase("day"))
							{
								ob = new InsertDataTable();
								ob.setColName("day");
								ob.setDataName(day);
								insertData.add(ob);
							}
						}
						if (gdp.getColomnName().equalsIgnoreCase("working"))
						{
							ob = new InsertDataTable();
							ob.setColName("working");
							ob.setDataName(workingFlag);
							insertData.add(ob);
						}
						if (gdp.getColomnName().equalsIgnoreCase("timeslot"))
						{
							ob = new InsertDataTable();
							ob.setColName("timeslot");
							ob.setDataName(intime + " to " + outtime);
							insertData.add(ob);
						}
						if (gdp.getColomnName().equalsIgnoreCase("beforetime"))
						{
							ob = new InsertDataTable();
							ob.setColName("beforetime");
							ob.setDataName(finalIncommin);
							insertData.add(ob);
						}
						if (gdp.getColomnName().equalsIgnoreCase("aftertime"))
						{
							ob = new InsertDataTable();
							ob.setColName("aftertime");
							ob.setDataName(finalOutgoing);
							insertData.add(ob);
						}
						if (gdp.getColomnName().equalsIgnoreCase("totalhour"))
						{
							ob = new InsertDataTable();
							ob.setColName("totalhour");
							ob.setDataName(totalTime);
							insertData.add(ob);
						}
					}
					cbt.createTable22("attendence_record", tableColume,
							connectionSpace);
					String empName=null;
					String datevalue=null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request
									.getParameterNames());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							System.out.println("parmName" +parmName);
							System.out.println("paramValue" +paramValue);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null
									&& !parmName
											.equalsIgnoreCase("deptHierarchy")
									&& !parmName.equalsIgnoreCase("deptname")
									&& !parmName.equalsIgnoreCase("subdept")
									&& !parmName.equalsIgnoreCase("tdate")
									&& !parmName.equalsIgnoreCase("userName")
									&& !parmName.equalsIgnoreCase("date")
									&& !parmName.equalsIgnoreCase("time")
									&& !parmName.equalsIgnoreCase("fdate")
									&& !parmName.equalsIgnoreCase("empid")
									&& !parmName.equalsIgnoreCase("date1"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
								if (parmName!=null && parmName.equalsIgnoreCase("empname")) 
								{
									empName=paramValue;
									
								}
								
							}
							if (parmName.equalsIgnoreCase("date1"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
								insertData.add(ob);
								if (parmName!=null && parmName.equalsIgnoreCase("datevalue")) 
								{
									datevalue=paramValue;
								}
							}
						}
					}
					
					status = cbt.insertIntoTable("attendence_record",
							insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				}
				else
				{
					addActionMessage("Oops There is some error in data!!!!");
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		System.out.println(" downloadExcel ::::: ");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					List dataList = null;
					StringBuilder query = new StringBuilder("");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (downloadType != null
							&& downloadType.equals("uploadExcel"))
					{
						returnResult = new GenericWriteExcel().createExcel(
								"DreamSol TeleSolutions Pvt. Ltd.", excelName,
								dataList, titleList, null, excelName);
					}
					else
					{

						if (download4.equalsIgnoreCase("viewAttendanceMark"))
						{
							query.append("SELECT ");
							int i = 0;
							Object obdata=null;
							for (Iterator it = keyList.iterator(); it.hasNext();)
							{
								obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < keyList.size() - 1)
										query.append("ar." + obdata.toString()
												+ ",");
									else if (obdata.toString()
											.equalsIgnoreCase("empname"))
									{
										query.append("emp.empName");
									}
									else
									{
										query.append(obdata.toString());
									}
								}
								i++;
							}
							query.append(" FROM attendence_record AS ar INNER JOIN  compliance_contacts AS cc ON ar.empname=cc.id ");   
							query.append( " INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
							System.out.println("queryggg " + query.toString());
							dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								System.out.println("dataListvvv" + dataList);
								excelFileName = new GenericWriteExcel()
										.createExcel(
												"DreamSol TeleSolutions Pvt. Ltd.",
												excelName, dataList, titleList,
												null, excelName);
								if (session.containsKey("columnMap"))
								{
									session.remove("columnMap");
								}
							}
						}

					}
					if (excelFileName != null)
					{
						excelStream = new FileInputStream(excelFileName);
					}

					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method downloadExcel of class "
										+ getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getIncommingStatus(String in_time, String intime)
	{
		String spin_time[] = in_time.split(":");
		String spintime[] = intime.split(":");
		int time = Integer.parseInt(spintime[0])- Integer.parseInt(spin_time[0]);
		int time1 = Integer.parseInt(spintime[1])- Integer.parseInt(spin_time[1]);
		String totalTime = time + ":" + time1;
		return totalTime;
	}

	public String getFinalIncommingstatus(String incomingStatus)
	{
		System.out.println(":::::::FinalIncommingstatus::::::::::");
		String finalTime = null;
		String timeSplit[] = incomingStatus.split(":");
		if (incomingStatus != null)
		{
			if (timeSplit[0].startsWith("-") && timeSplit[1].startsWith("-"))
			{
				finalTime = "Late By "
						+ timeSplit[0].substring(1, timeSplit[0].length())
						+ " hour "
						+ timeSplit[1].substring(1, timeSplit[1].length())
						+ " mins";
			}
			else if (timeSplit[0].startsWith("-"))
			{
				int time3 = (Integer.parseInt(timeSplit[0].substring(1,
						timeSplit[0].length())) * 60)
						- Integer.parseInt(timeSplit[1]);
				finalTime = "Late By " + time3 + " mins";
			}
			else if (timeSplit[1].startsWith("-"))
			{
				finalTime = "Late By " + timeSplit[0] + " hour "
						+ timeSplit[1].substring(1, timeSplit[1].length())
						+ " mins";
			}
			else
				finalTime = "Early By " + timeSplit[0] + " hour "
						+ timeSplit[1] + " mins";
		}
		return finalTime;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String beforeAttendanceConfig()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List<GridDataPropertyView> configColumnList = Configuration
						.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "record_data_configuration");
				attendanceConfigColumnDate = new ArrayList<ConfigurationUtilBean>();
				attendanceConfigColumnText = new ArrayList<ConfigurationUtilBean>();
				attendanceConfigColumnDrop = new ArrayList<ConfigurationUtilBean>();
				if (configColumnList != null && configColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : configColumnList)
					{
						ConfigurationUtilBean cub = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("Date"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceConfigColumnDate.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"twdays"))
						{

							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceConfigColumnText.add(cub);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							cub.setKey(gdp.getColomnName());
							cub.setValue(gdp.getHeadingName());
							cub.setColType(gdp.getColType());
							cub.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								cub.setMandatory(true);
							}
							else
							{
								cub.setMandatory(false);
							}
							attendanceConfigColumnDrop.add(cub);
						}
					}
				}
				LeaveHelper LH=new LeaveHelper(); 
				String data[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				//deptDataList = new HashMap<Integer, String>();
				if (data!=null && !data.toString().equalsIgnoreCase(""))
				{
					List dataList=LH.getDeptName("HR",data[4],connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						Object[] object3=null;
					    for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();) 
					    {
							object3 = (Object[]) iterator2.next();
							deptDataList.put((Integer)object3[0], object3[1].toString());
					    }
				    }
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{

				e.printStackTrace();
			}
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addAttendanceConfig()
	{
	
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String tdate = request.getParameter("date");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData("mapped_time_slot_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "record_data_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("date"))
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							ob1.setConstraint("default NULL");
							tableColume.add(ob1);
						}
					}
					TableColumes ob11 = new TableColumes();
					ob11 = new TableColumes();
					ob11.setColumnname("tdate");
					ob11.setDatatype("varchar(255)");
					ob11.setConstraint("default NULL");
					tableColume.add(ob11);

					TableColumes ob111 = new TableColumes();
					ob111 = new TableColumes();
					ob111.setColumnname("total_leaves");
					ob111.setDatatype("varchar(255)");
					ob111.setConstraint("default NULL");
					tableColume.add(ob111);

					cbt.createTable22("report_data", tableColume,
							connectionSpace);
					int totalDays = DateUtil.countWeekendDays(DateUtil
							.convertDateToUSFormat(tdate));
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null
									&& !parmName
											.equalsIgnoreCase("deptHierarchy")
									&& !parmName.equalsIgnoreCase("deptname")
									&& !parmName.equalsIgnoreCase("subdeptname")
									&& !parmName.equalsIgnoreCase("date")
									&& !parmName
											.equalsIgnoreCase("total_leaves"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
							if (parmName.equalsIgnoreCase("date"))
							{
								ob = new InsertDataTable();
								ob.setColName("tdate");
								ob.setDataName(DateUtil
										.convertDateToUSFormat(paramValue));
								insertData.add(ob);
							}
						}
						ob = new InsertDataTable();
						ob.setColName("total_leaves");
						ob.setDataName("0");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("twdays");
						ob.setDataName(totalDays);
						insertData.add(ob);

						status = cbt.insertIntoTable("report_data", insertData,
								connectionSpace);
						if (status)
						{
							addActionMessage("Data Save Successfully!!!");
						}
						returnResult = SUCCESS;
					}
				}
				else
				{
					addActionMessage("Oops There is some error in data!!!!");
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	@SuppressWarnings("rawtypes")
	public String getSubDept() 
	{
		try 
		{
			String userName=(String)session.get("uName");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List data2=null;
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			jsonArray = new JSONArray();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			System.out.println("dept id===   "+deptId);
			if (getDeptId()!=null && !getDeptId().equalsIgnoreCase("")) 
			{
				data2 = cbt.executeAllSelectQuery("SELECT id,subdeptname FROM subdepartment WHERE deptid='"+deptId+"'",connectionSpace);
				if (data2 != null) 
				{
					JSONObject formDetailsJson = null;
					
					Object[] obdata=null;
					for (Iterator it = data2.iterator(); it.hasNext();) 
					{
					   obdata = (Object[]) it.next();
						if (obdata != null) 
						{
						    formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", obdata[0].toString());
							formDetailsJson.put("NAME", obdata[1].toString());
							jsonArray.add(formDetailsJson);
						}
					}
				}
		    }
			
		} 
		catch (Exception e) 
		{
			log.error("Date : "+ DateUtil.getCurrentDateIndianFormat()+ " Time: "
									+ DateUtil.getCurrentTime()+ " "
									+ "Problem in Over2Cloud  method: getSubDept of class "+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public String getEmployyeeReport() 
	{
		try 
		{
			String userName=(String)session.get("uName");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List data2=null;
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			jsonArray = new JSONArray();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			if (getDeptId()!=null && !getDeptId().equalsIgnoreCase("")) 
			{
				LeaveHelper LH=new LeaveHelper();
				String data[]=LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				
				data2 = LH .getOnChangeEmployeeData("HR", data[4], getDeptId(), connectionSpace);
				if (data2 != null && data2.size()>0) 
				{
					JSONObject formDetailsJson =null;
					
					Object[] obdata=null;
					for (Iterator it = data2.iterator(); it.hasNext();) 
					{
						obdata = (Object[]) it.next();
						if (obdata != null) 
						{
							formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", obdata[0].toString());
							formDetailsJson.put("NAME", obdata[1].toString());
							jsonArray.add(formDetailsJson);
						}
					}
				}
		    
			}
			
			
		} 
		catch (Exception e) 
		{
			log.error("Date : "+ DateUtil.getCurrentDateIndianFormat()+ " Time: "
									+ DateUtil.getCurrentTime()+ " "
									+ "Problem in Over2Cloud  method: getEmployyeeReport of class "+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/*public String FetchviewAttendancetHeader() 
	{
		System.out.println("FetchviewAttendancetHeader");
		if (ValidateSession.checkSession())
		{
		    try 
		    {
		    	fdate=DateUtil.getNextDateAfter(-31);
		    	System.out.println("fdate :::" +fdate);
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
	
	public String FetchviewAttendancetHeader() 
	{
		System.out.println("FetchviewAttendancetHeader");
		if (ValidateSession.checkSession())
		{
		    try 
		    {
                int day=0; 
		    	int foo=0;
                 String date = DateUtil.getCurrentDateIndianFormat();
               //  String date11 =DateUtil.convertDateToUSFormat(date);
				 String str[]=date.split("-");
				 String finalString=str[0];
					
				String a_letter = Character.toString(finalString.charAt(1));

				 String a_letter1 = Character.toString(finalString.charAt(0));

				if(a_letter1.equals("0"))
				
				{
			    	foo = Integer.parseInt(a_letter)-1;

				}
				else
				{
	                foo =Integer.parseInt(finalString)-1; 

				}
					

				
			      firstDay =DateUtil.getNextDateAfter(-foo);
                 CommonOperInterface cbt = new CommonConFactory().createInterface();
                 StringBuilder query1 = new StringBuilder("");
                // query1.append("SELECT COUNT(*) FROM attendence_record WHERE date1 BETWEEN "+"'"+fdate+"'"+ " AND "+"'"+tdate +"'"+ "AND beforetime LIKE '%Late%' " ); 
                // setCounter1(counterRecort(query1.toString()));
                 //System.out.println("get counter "+getCounter1());
                
                 // List data = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);

     
             System.out.println("query>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + query1);
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
	
	public String headercounter() 
	{


            StringBuilder query1 = new StringBuilder("");
            StringBuilder query2 = new StringBuilder("");
            StringBuilder query3 = new StringBuilder("");

            StringBuilder query4 = new StringBuilder("");
            StringBuilder query5 = new StringBuilder("");
            StringBuilder query6 = new StringBuilder("");


            LeaveHelper LH=new LeaveHelper(); 
            String empId=(String) session.get("empIdofuser").toString().split("-")[1];
            
		    String data=LH.getContactId("HR", connectionSpace, empId);
		    System.out.println("data   "+data);
			if(data!=null )
			{
				query1.append("SELECT  COUNT(*) FROM attendence_record where empname IN (" + data+ ")");
				query2.append("SELECT  COUNT(*) FROM attendence_record where empname IN (" + data+ ")");
				query3.append("SELECT SUM(eworking) FROM attendence_record where empname IN (" + data+ ")");
				 System.out.println("query3   "+query3);
                query4.append("SELECT  COUNT(*) FROM attendence_record where empname IN (" + data+ ")");
				query5.append("SELECT  COUNT(*) FROM attendence_record where empname IN (" + data+ ")");
                query6.append("SELECT  COUNT(*) FROM attendence_record where empname IN (" + data+ ")");

			}


					if (getT1date()!=null && getF1date()!=null) 
					{


	                         String str[]=getT1date().split("-");
							 String str1[]=getF1date().split("-");

							 String finalString=str[0];
							 String finalString1=str1[0];

							if(finalString.length()!=4 && finalString1.length()!=4)
							{
						      f1date = DateUtil.convertDateToUSFormat(getF1date());
						       t1date = DateUtil.convertDateToUSFormat(getT1date());
							}

		                        query1.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'"+"AND beforetime LIKE '%Late%' ");
				                query2.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'"+"AND beforetime LIKE '%on%' ");
				                query3.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'" );
				                System.out.println("query3   +++  " +query3);
				                query4.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'"+"AND attendence LIKE '%Absent%' ");
				                query5.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'"+"AND attendence LIKE '%Present%' ");
	                            query6.append( "AND date1  between "+"'"+f1date+"'"+ " AND "+"'"+t1date +"'"+"AND working LIKE '%working%' " );


			                setChCounter(counterRecort(query1.toString()));
			                setChCounter1(counterRecort(query2.toString()));
			                setEworkcount(counterRecort(query3.toString()));
			                System.out.println("setEworkcount >>>  " +getEworkcount());
			                setChCounter4(counterRecort(query4.toString()));
			                setChCounter5(counterRecort(query5.toString()));
			                setChCounter6(counterRecort(query6.toString()));




					}



       return SUCCESS;
	}


	private int counterRecort(String string) {
		// TODO Auto-generated method stub
		 BigInteger totalRecord=new BigInteger("3"); 
	        List listData=null; 
	        CommonOperInterface cbt = new CommonConFactory().createInterface(); 
	        listData=cbt.executeAllSelectQuery(string,connectionSpace); 
	              if(listData!=null) 
	              { 
	                  for(Iterator iterator=listData.iterator(); iterator.hasNext();) 
	                  { 
	                 
	                      totalRecord=(BigInteger) iterator.next(); 
	                  } 
	              } 
	         
	            return totalRecord.intValue(); 
	}
	
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


	public List<Object> getViewTimeSlot()
	{
		return viewTimeSlot;
	}

	public void setViewTimeSlot(List<Object> viewTimeSlot)
	{
		this.viewTimeSlot = viewTimeSlot;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getReportGridColoumnNames()
	{
		return reportGridColoumnNames;
	}

	public void setReportGridColoumnNames(
			List<GridDataPropertyView> reportGridColoumnNames)
	{
		this.reportGridColoumnNames = reportGridColoumnNames;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getDeptOrSubDeptId()
	{
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId)
	{
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(
			List<ConfigurationUtilBean> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public Map<Integer, String> getSubDeptList()
	{
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList)
	{
		this.subDeptList = subDeptList;
	}

	public Map<Integer, String> getEmployeelist()
	{
		return employeelist;
	}

	public void setEmployeelist(Map<Integer, String> employeelist)
	{
		this.employeelist = employeelist;
	}

	public List<GridDataPropertyView> getSlotGridColomnsNames()
	{
		return slotGridColomnsNames;
	}

	public void setSlotGridColomnsNames(
			List<GridDataPropertyView> slotGridColomnsNames)
	{
		this.slotGridColomnsNames = slotGridColomnsNames;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getFdate()
	{
		return fdate;
	}

	public void setFdate(String fdate)
	{
		this.fdate = fdate;
	}

	public String getTdate()
	{
		return tdate;
	}

	public void setTdate(String tdate)
	{
		this.tdate = tdate;
	}

	public int getNoOfTotalWorkingDays()
	{
		return noOfTotalWorkingDays;
	}

	public void setNoOfTotalWorkingDays(int noOfTotalWorkingDays)
	{
		this.noOfTotalWorkingDays = noOfTotalWorkingDays;
	}

	public double getCarryForward()
	{
		return carryForward;
	}

	public void setCarryForward(double carryForward)
	{
		this.carryForward = carryForward;
	}

	public double getExtraWorking()
	{
		return extraWorking;
	}

	public void setExtraWorking(double extraWorking)
	{
		this.extraWorking = extraWorking;
	}

	public Map<Integer, String> getEmployeelist1()
	{
		return employeelist1;
	}

	public void setEmployeelist1(Map<Integer, String> employeelist1)
	{
		this.employeelist1 = employeelist1;
	}

	public double getTotalDeduction()
	{
		return totalDeduction;
	}

	public void setTotalDeduction(double totalDeduction)
	{
		this.totalDeduction = totalDeduction;
	}

	public List<GridDataPropertyView> getSummaryColomnsNames()
	{
		return summaryColomnsNames;
	}

	public void setSummaryColomnsNames(
			List<GridDataPropertyView> summaryColomnsNames)
	{
		this.summaryColomnsNames = summaryColomnsNames;
	}

	public List<Object> getSummaryView()
	{
		return summaryView;
	}

	public void setSummaryView(List<Object> summaryView)
	{
		this.summaryView = summaryView;
	}

	public List<ConfigurationUtilBean> getTimeSlotColumnTime()
	{
		return timeSlotColumnTime;
	}

	public void setTimeSlotColumnTime(
			List<ConfigurationUtilBean> timeSlotColumnTime)
	{
		this.timeSlotColumnTime = timeSlotColumnTime;
	}

	public List<ConfigurationUtilBean> getTimeSlotColumnDrop()
	{
		return timeSlotColumnDrop;
	}

	public void setTimeSlotColumnDrop(
			List<ConfigurationUtilBean> timeSlotColumnDrop)
	{
		this.timeSlotColumnDrop = timeSlotColumnDrop;
	}

	public List<ConfigurationUtilBean> getAttendanceColumnDropDown()
	{
		return attendanceColumnDropDown;
	}

	public void setAttendanceColumnDropDown(
			List<ConfigurationUtilBean> attendanceColumnDropDown)
	{
		this.attendanceColumnDropDown = attendanceColumnDropDown;
	}

	public List<ConfigurationUtilBean> getAttendanceColumnDate()
	{
		return attendanceColumnDate;
	}

	public void setAttendanceColumnDate(
			List<ConfigurationUtilBean> attendanceColumnDate)
	{
		this.attendanceColumnDate = attendanceColumnDate;
	}

	public List<ConfigurationUtilBean> getReportColumnText()
	{
		return reportColumnText;
	}

	public void setReportColumnText(List<ConfigurationUtilBean> reportColumnText)
	{
		this.reportColumnText = reportColumnText;
	}

	public List<ConfigurationUtilBean> getAttendanceMarkColumnDate()
	{
		return attendanceMarkColumnDate;
	}

	public void setAttendanceMarkColumnDate(
			List<ConfigurationUtilBean> attendanceMarkColumnDate)
	{
		this.attendanceMarkColumnDate = attendanceMarkColumnDate;
	}

	public List<ConfigurationUtilBean> getAttendanceMarkColumnText()
	{
		return attendanceMarkColumnText;
	}

	public void setAttendanceMarkColumnText(
			List<ConfigurationUtilBean> attendanceMarkColumnText)
	{
		this.attendanceMarkColumnText = attendanceMarkColumnText;
	}

	public List<ConfigurationUtilBean> getAttendanceMarkColumnTime()
	{
		return attendanceMarkColumnTime;
	}

	public void setAttendanceMarkColumnTime(
			List<ConfigurationUtilBean> attendanceMarkColumnTime)
	{
		this.attendanceMarkColumnTime = attendanceMarkColumnTime;
	}

	public List<ConfigurationUtilBean> getAttendanceMarkColumnDropDown()
	{
		return attendanceMarkColumnDropDown;
	}

	public void setAttendanceMarkColumnDropDown(
			List<ConfigurationUtilBean> attendanceMarkColumnDropDown)
	{
		this.attendanceMarkColumnDropDown = attendanceMarkColumnDropDown;
	}

	public String getEmpname()
	{
		return empname;
	}

	public void setEmpname(String empname)
	{
		this.empname = empname;
	}

	public double getCarryForwar4Month()
	{
		return carryForwar4Month;
	}

	public void setCarryForwar4Month(double carryForwar4Month)
	{
		this.carryForwar4Month = carryForwar4Month;
	}

	public List<ConfigurationUtilBean> getAttendanceConfigColumnDate()
	{
		return attendanceConfigColumnDate;
	}

	public void setAttendanceConfigColumnDate(
			List<ConfigurationUtilBean> attendanceConfigColumnDate)
	{
		this.attendanceConfigColumnDate = attendanceConfigColumnDate;
	}

	public List<ConfigurationUtilBean> getAttendanceConfigColumnText()
	{
		return attendanceConfigColumnText;
	}

	public void setAttendanceConfigColumnText(
			List<ConfigurationUtilBean> attendanceConfigColumnText)
	{
		this.attendanceConfigColumnText = attendanceConfigColumnText;
	}

	public List<ConfigurationUtilBean> getAttendanceConfigColumnDrop()
	{
		return attendanceConfigColumnDrop;
	}

	public void setAttendanceConfigColumnDrop(
			List<ConfigurationUtilBean> attendanceConfigColumnDrop)
	{
		this.attendanceConfigColumnDrop = attendanceConfigColumnDrop;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
	}

	public String getDownload4()
	{
		return download4;
	}

	public void setDownload4(String download4)
	{
		this.download4 = download4;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public List<ConfigurationUtilBean> getColumnMap2()
	{
		return columnMap2;
	}

	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2)
	{
		this.columnMap2 = columnMap2;
	}

	public String getExcelName()
	{
		return excelName;
	}

	public void setExcelName(String excelName)
	{
		this.excelName = excelName;
	}

	public String getDownloadType()
	{
		return downloadType;
	}

	public void setDownloadType(String downloadType)
	{
		this.downloadType = downloadType;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
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

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public File getDocument()
	{
		return document;
	}

	public void setDocument(File document)
	{
		this.document = document;
	}

	public String getDocumentContentType()
	{
		return documentContentType;
	}

	public void setDocumentContentType(String documentContentType)
	{
		this.documentContentType = documentContentType;
	}

	public String getDocumentFileName()
	{
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName)
	{
		this.documentFileName = documentFileName;
	}

	public String getEmpStatus()
	{
		return empStatus;
	}

	public void setEmpStatus(String empStatus)
	{
		this.empStatus = empStatus;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Map<Integer, String> getEmployeeType()
	{
		return employeeType;
	}

	public void setEmployeeType(Map<Integer, String> employeeType)
	{
		this.employeeType = employeeType;
	}

	public Map<String, String> getAttachFileMap()
	{
		return attachFileMap;
	}

	public void setAttachFileMap(Map<String, String> attachFileMap)
	{
		this.attachFileMap = attachFileMap;
	}

	public List<Object> getViewReportAttendance()
	{
		return viewReportAttendance;
	}

	public void setViewReportAttendance(List<Object> viewReportAttendance)
	{
		this.viewReportAttendance = viewReportAttendance;
	}

	public JSONObject getResponseDetailsJson()
	{
		return responseDetailsJson;
	}

	public void setResponseDetailsJson(JSONObject responseDetailsJson)
	{
		this.responseDetailsJson = responseDetailsJson;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getSubdeptId()
	{
		return SubdeptId;
	}

	public void setSubdeptId(String subdeptId)
	{
		SubdeptId = subdeptId;
	}

	public Map<String, String> getDeptName()
	{
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName)
	{
		this.deptName = deptName;
	}

	public String getEmp()
	{
		return emp;
	}

	public void setEmp(String emp)
	{
		this.emp = emp;
	}

	public Map<Integer, String> getDeptDataList() {
		return deptDataList;
	}

	public void setDeptDataList(Map<Integer, String> deptDataList) {
		this.deptDataList = deptDataList;
	}



	public String getFromTimeDate() {
		return fromTimeDate;
	}



	public void setFromTimeDate(String fromTimeDate) {
		this.fromTimeDate = fromTimeDate;
	}



	public String getF1date() {
		return f1date;
	}



	public void setF1date(String f1date) {
		this.f1date = f1date;
	}



	public String getT1date() {
		return t1date;
	}



	public void setT1date(String t1date) {
		this.t1date = t1date;
	}



	public int getChCounter() {
		return chCounter;
	}



	public void setChCounter(int chCounter) {
		this.chCounter = chCounter;
	}



	public int getChCounter1() {
		return chCounter1;
	}



	public void setChCounter1(int chCounter1) {
		this.chCounter1 = chCounter1;
	}



	public int getChCounter2() {
		return chCounter2;
	}



	public void setChCounter2(int chCounter2) {
		this.chCounter2 = chCounter2;
	}



	public int getChCounter3() {
		return chCounter3;
	}



	public void setChCounter3(int chCounter3) {
		this.chCounter3 = chCounter3;
	}



	public int getChCounter4() {
		return chCounter4;
	}



	public void setChCounter4(int chCounter4) {
		this.chCounter4 = chCounter4;
	}



	public int getChCounter5() {
		return chCounter5;
	}



	public void setChCounter5(int chCounter5) {
		this.chCounter5 = chCounter5;
	}







	public int getEworkcount() {
		return eworkcount;
	}



	public void setEworkcount(int eworkcount) {
		this.eworkcount = eworkcount;
	}



	public int getChCounter6() {
		return chCounter6;
	}



	public void setChCounter6(int chCounter6) {
		this.chCounter6 = chCounter6;
	}



	public String getFirstDay() {
		return firstDay;
	}



	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}



	public String getCounter1() {
		return counter1;
	}



	public void setCounter1(String counter1) {
		this.counter1 = counter1;
	}



	public String getActionStatus1() {
		return actionStatus1;
	}



	public void setActionStatus1(String actionStatus1) {
		this.actionStatus1 = actionStatus1;
	}



	public String getActionStatus() {
		return actionStatus;
	}



	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}



	
	

}
