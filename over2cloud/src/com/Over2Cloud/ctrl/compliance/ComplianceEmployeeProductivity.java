package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceEmployeeProductivity extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private Map<String, String> employeeMap = null;
	String userName = (String) session.get("uName");
	private String fromDate;
	private String toDate;
	private String empName;
	ComplianceDashboardBean totalProductivity;
	private String totalTask;
	private String totalOnTime;
	private String totalOffTime;
	private String totalSnooze;
	private String totalPerOnTime;
	private String totalPerOffTime;
	private List<GridDataPropertyView> masterViewProp = null;
	private String complId;
	private String dataFor;
	private Map<String, String> dataMap = new LinkedHashMap<String, String>();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;

	@SuppressWarnings("rawtypes")
    public String beforeEmpProduct()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userContID = null;
				String empId = null;
				if (userName!= null && !userName.equalsIgnoreCase(""))
				{
					empId = session.get("empIdofuser").toString().split("-")[1];
				}
				List dataList = new ComplianceTransferAction().getAllContactIdByEmpId(empId);
				userContID= dataList.toString().replace(", ","#").replace("[", "0#").replace("]", "#0");
				fromDate = DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
				toDate = DateUtil.getCurrentDateUSFormat();
				productivityData(userContID, fromDate, toDate, empName);

				return SUCCESS;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
    public String employeeProductivityCounter()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				String userContID = null;
				String empId = null;
				if (userName!= null && !userName.equalsIgnoreCase(""))
				{
					empId = session.get("empIdofuser").toString().split("-")[1];
				}
				List dataList = new ComplianceTransferAction().getAllContactIdByEmpId(empId);
				//System.out.println("Size is as ::: "+dataList.toString());
				if (dataList!=null && dataList.size()==1) 
				{
					userContID= dataList.toString().replace(",", "#").substring(1,dataList.size());
				} 
				else 
				{
					userContID= dataList.toString().replace(",", "#").substring(1,dataList.size()-1);
				}
				
				productivityData(userContID, DateUtil.convertDateToUSFormat(fromDate), DateUtil.convertDateToUSFormat(toDate), empName);

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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	private void productivityData(String userContactID, String fromDate, String toDate, String empName)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceCommonOperation cmnCompliance = new ComplianceCommonOperation();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String selectedContId = null;
			Object[] object=null;
			String contId=null;
			employeeMap = new LinkedHashMap<String, String>();
			StringBuilder conatctId = new StringBuilder();
			if (empName != null && empName.length() > 0 && !empName.equalsIgnoreCase("-1"))
			{
				selectedContId = empName;
			}
			else
			{
				if (userContactID!=null && !userContactID.equalsIgnoreCase(""))
				{
					contId=userContactID;
				}
				else
				{
					String empId = null;
					if (userName!= null && !userName.equalsIgnoreCase(""))
					{
						empId = session.get("empIdofuser").toString().split("-")[1];
					}
					List dataList = new ComplianceTransferAction().getAllContactIdByEmpId(empId);
					contId= dataList.toString().replace(",", "#").replace("[", "").replace("]", "");
				}
				List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details ", connectionSpace);
				String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND csd.contact_id IN("+ contId.replace("#", ",")+")";
				List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
				List contIdList = new ArrayList();
				contIdList.add("#" + contId + "#");
				if (shareDataCount != null && shareDataCount.size() > 0)
				{
					for (int i = 0; i < shareDataCount.size(); i++)
					{
						contIdList.add("#" + shareDataCount.get(i).toString() + "#");
					}
				}
				if (dataCount != null && dataCount.size() > 0)
				{
					for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							String str = "#";
							str = str + object[1].toString();
							String arr[]=contIdList.toString().replace(", ", "#").split("#");
							for (int i = 0; i < arr.length; i++)
							{
								if (str.contains("#"+arr[i]+"#"))
								{
									conatctId.append(arr[i]+"#");
								}
							}
						}
					}
				}
			}
			if (conatctId != null && conatctId.length() > 0)
			{
				selectedContId = conatctId.toString().replace("#", ",").substring(0, (conatctId.toString().length() - 1));
			}
			String empIdQuery = "SELECT cc.id,emp.empName FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON cc.emp_id=emp.id  WHERE cc.id IN(" + selectedContId + ")";
			List empIdList = cbt.executeAllSelectQuery(empIdQuery, connectionSpace);
			if (empIdList != null && empIdList.size() > 0)
			{
				for (Iterator iterator = empIdList.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						employeeMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
			totalProductivity = new ComplianceDashboardBean();
			List<ComplianceDashboardBean> tempList = cmnCompliance.gettotalProductivity(employeeMap, fromDate, toDate, contId);
			int temp = 0, temp2 = 0, temp4 = 0, temp5 = 0, temp6 = 0, temp7 = 0;
			totalProductivity.setComplList(tempList);
			if (tempList != null && tempList.size() > 0)
			{
				for (ComplianceDashboardBean obj : tempList)
				{
					temp = temp + Integer.valueOf(obj.getTotalTask());
					temp2 = temp2 + Integer.valueOf(obj.getOnTime());
					temp4 = temp4 + Integer.valueOf(obj.getOffTime());
					temp5 = temp5 + Integer.valueOf(obj.getSnooze());
					temp6 = temp6 + Integer.valueOf(obj.getPerOnTime());
					temp7 = temp7 + Integer.valueOf(obj.getPerOffTime());
				}
			}
			totalTask = String.valueOf(temp);
			totalOnTime = String.valueOf(temp2);
			totalOffTime = String.valueOf(temp4);
			totalSnooze = String.valueOf(temp5);
			totalPerOnTime = String.valueOf(temp4);
			totalPerOffTime = String.valueOf(temp5);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String productivityGridCol()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				return LOGIN;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void setViewProp()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String accountID = (String) session.get("accountid");

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("crid");
			gpv.setHeadingName("Cr Id");
			gpv.setHideOrShow("true");
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("cr.complainceId");
			gpv.setHeadingName("Compliance Id");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ctyp.taskType");
			gpv.setHeadingName("Task Type");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration
			        .getConfigurationData("mapped_compliance_configuration", accountID, connectionSpace, "", 0, "table_name", "compliance_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (!gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("taskType") && !gdp.getColomnName().equals("taskBrief") && !gdp.getColomnName().equals("uploadDoc")
				        && !gdp.getColomnName().equals("reminder1") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time") && !gdp.getColomnName().equals("uploadDoc1")
				        && !gdp.getColomnName().equals("reminder2") && !gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("actionStatus") && !gdp.getColomnName().equals("comments")
				        && !gdp.getColomnName().equals("fromDate") && !gdp.getColomnName().equals("actionTaken") && !gdp.getColomnName().equals("reminder3")
				        && !gdp.getColomnName().equals("compid_prefix") && !gdp.getColomnName().equals("l1EscDuration") && !gdp.getColomnName().equals("l2EscDuration")
				        && !gdp.getColomnName().equals("l3EscDuration") && !gdp.getColomnName().equals("l4EscDuration") && !gdp.getColomnName().equals("escalation_level_1")
				        && !gdp.getColomnName().equals("escalation_level_2") && !gdp.getColomnName().equals("escalation_level_3") && !gdp.getColomnName().equals("escalation_level_4")
				        && !gdp.getColomnName().equals("snooze_date") && !gdp.getColomnName().equals("snooze_time") && !gdp.getColomnName().equals("remindMode")
				        && !gdp.getColomnName().equals("reminder_for") && !gdp.getColomnName().equals("ack_dge"))
				{
					gpv = new GridDataPropertyView();
					if (gdp.getColomnName().equalsIgnoreCase("taskName"))
					{
						gpv.setColomnName("ctn." + gdp.getColomnName());
					}
					else
					{
						gpv.setColomnName("comp." + gdp.getColomnName());
					}

					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			gpv = new GridDataPropertyView();
			gpv.setColomnName("cr.actionTakenDate");
			gpv.setHeadingName("Achieved On & At");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("cr.actionTakenTime");
			gpv.setHeadingName("Achieved At");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("true");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("cr.userName");
			gpv.setHeadingName("Action Taken By");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("cr.actionTaken");
			gpv.setHeadingName("Action Status");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("true");
			gpv.setWidth(150);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			session.put("masterViewProp", masterViewProp);
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String getProductivityGridData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					// getting the list of colmuns
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("SELECT ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("id"))
								{
									query.append("comp.id,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("crid"))
								{
									query.append("cr.id as reportId,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
								{
									query.append("cr.userName AS name,");
								}
								else
								{
									query.append(gpv.getColomnName().toString() + ",");
								}
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM compliance_details comp");
						query.append(" LEFT JOIN compliance_report AS cr ON comp.id=cr.complID ");
						query.append(" LEFT JOIN compliance_task ctn ON ctn.id=comp.taskName LEFT JOIN compl_task_type AS ctyp ON ctyp.id=ctn.taskType WHERE ");
						query.append("  comp.id IN (" + complId + ") ");
					     
						if (dataFor != null && dataFor.equalsIgnoreCase("snooze"))
						{
							query.append("  AND comp.actionTaken='Snooze'");
						}
						if (dataFor != null && dataFor.equalsIgnoreCase("ontime"))
						{
							query.append("  AND cr.dueDate >= cr.actionTakenDate AND cr.actionTaken IN('Recurring' ,'Done')");
						}
						if (dataFor != null && dataFor.equalsIgnoreCase("offtime"))
						{
							query.append("  AND cr.dueDate < cr.actionTakenDate AND cr.actionTaken IN('Recurring' ,'Done')");
						}
						if (dataFor != null && (dataFor.equalsIgnoreCase("total") || dataFor.equalsIgnoreCase("snooze")))
						{
							query.append(" GROUP BY comp.id ");
						}
						
						query.append(" ORDER BY comp.dueDate ASC ");
						//System.out.println("QUERY IS ASS       =====   "+query.toString());
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								int k = 0;
								Object[] obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null)
									{
										if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else if (gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
										{
											one.put(gpv.getColomnName(),new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
										}
										else if (gpv.getColomnName().equalsIgnoreCase("comp.action_type"))
										{
											one.put(gpv.getColomnName(), DateUtil.makeTitle(obdata[k].toString()));
										}
										else if (gpv.getColomnName().equalsIgnoreCase("comp.escalation"))
										{
											if (obdata[k].toString().equalsIgnoreCase("N"))
											{
												one.put(gpv.getColomnName(), "No");
											}
											else
											{
												one.put(gpv.getColomnName(), "Yes");
											}
										}
										else if (gpv.getColomnName().equalsIgnoreCase("comp.userName"))
										{
											one.put(gpv.getColomnName(), DateUtil.makeTitle(obdata[k].toString()));
										}
										else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
										{
											one.put(gpv.getColomnName(), DateUtil.makeTitle(Cryptography.decrypt(obdata[k].toString(), DES_ENCRYPTION_KEY)));
										}
										else
										{
											one.put(gpv.getColomnName(), obdata[k].toString());
										}
										if (gpv.getColomnName().equalsIgnoreCase("cr.actionTakenDate"))
										{
											one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + " " + obdata[k + 1].toString());
										}
									}
									else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String productivityGraphDataAction()
	{
		String map[] = dataFor.split(",");
		dataMap.put("On Time", map[0]);
		dataMap.put("Off Time", map[1]);
		dataMap.put("Snooze", map[2]);
		dataMap.put("Missed", map[3]);

		return SUCCESS;
	}

	public String beforeEscalationData()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewPropLevel();
				return SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				return LOGIN;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	private void setViewPropLevel()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("name");
			gpv.setHeadingName("Name");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("tat");
			gpv.setHeadingName("TAT");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);
		}
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public String escalationActionData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					query.append("SELECT ");
					query.append(" escalation_level_1,l1EscDuration,escalation_level_2,l2EscDuration, ");
					query.append("  escalation_level_3,l3EscDuration,escalation_level_4,l4EscDuration ");
					query.append(" FROM compliance_details  WHERE id = '" + complId + "'");
					// System.out.println("QUERY IS ASS       =====   "+query.toString());
					data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						Object[] obj=null;
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Map<String, Object> one = new HashMap<String, Object>();
							 obj = (Object[]) iterator.next();
							if (obj != null)
							{
								if (obj[0] != null)
								{
									one.put("level", "Level 1");
									StringBuilder empName = new StringBuilder();
									String empId = obj[0].toString().replace("#", ",").substring(0, (obj[0].toString().length() - 1));
									String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
									List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
									for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
									{
										Object object = (Object) iterator1.next();
										empName.append(object.toString() + " ,");
									}
									if (empName != null && !empName.toString().equalsIgnoreCase(""))
										one.put("name", empName.toString().substring(0, empName.toString().length() - 1));
									else
										one.put("name", "NA");

									one.put("tat", obj[1].toString());
									Listhb.add(one);
								}
								if (obj[2] != null)
								{
									one = new HashMap<String, Object>();
									one.put("level", "Level 2");
									StringBuilder empName = new StringBuilder();
									String empId = obj[2].toString().replace("#", ",").substring(0, (obj[2].toString().length() - 1));
									String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
									List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
									for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
									{
										Object object = (Object) iterator1.next();
										empName.append(object.toString() + " ,");
									}
									if (empName != null && !empName.toString().equalsIgnoreCase(""))
										one.put("name", empName.toString().substring(0, empName.toString().length() - 1));
									else
										one.put("name", "NA");
									one.put("tat", obj[3].toString());
									Listhb.add(one);
								}
								if (obj[4] != null)
								{
									one = new HashMap<String, Object>();
									one.put("level", "Level 3");
									StringBuilder empName = new StringBuilder();
									String empId = obj[4].toString().replace("#", ",").substring(0, (obj[4].toString().length() - 1));
									String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
									List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
									for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
									{
										Object object = (Object) iterator1.next();
										empName.append(object.toString() + " ,");
									}
									if (empName != null && !empName.toString().equalsIgnoreCase(""))
										one.put("name", empName.toString().substring(0, empName.toString().length() - 1));
									else
										one.put("name", "NA");
									one.put("tat", obj[5].toString());
									Listhb.add(one);
								}
								if (obj[6] != null)
								{
									one = new HashMap<String, Object>();
									one.put("level", "Level 4");
									StringBuilder empName = new StringBuilder();
									String empId = obj[6].toString().replace("#", ",").substring(0, (obj[6].toString().length() - 1));
									String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
									List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
									for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
									{
										Object object = (Object) iterator1.next();
										empName.append(object.toString() + " ,");
									}
									if (empName != null && !empName.toString().equalsIgnoreCase(""))
										one.put("name", empName.toString().substring(0, empName.toString().length() - 1));
									else
										one.put("name", "NA");
									one.put("tat", obj[7].toString());
									Listhb.add(one);
								}
								setMasterViewList(Listhb);
							}
						}
						setRecords(masterViewList.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeReminder()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewPropLevel1();
				return SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				return LOGIN;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	private void setViewPropLevel1()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("reminder");
			gpv.setHeadingName("Reminder");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("date");
			gpv.setHeadingName("Date");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			gpv.setFormatter("false");
			masterViewProp.add(gpv);
		}
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public String reminderActionData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					query.append("SELECT ");
					query.append(" reminder3,reminder2,reminder1  ");
					query.append(" FROM compliance_report  ");
					if (dataFor != null && dataFor.equalsIgnoreCase("port"))
					{
						query.append("  WHERE complID = '" + complId + "'");
					}
					else if (dataFor != null && dataFor.equalsIgnoreCase("productivity"))
					{
						query.append("  WHERE id = '" + complId + "'");
					}
					data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						Object[] obj=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Map<String, Object> one = null;
							obj = (Object[]) it.next();
							if (obj != null)
							{
								if (obj[2] != null)
								{
									one = new HashMap<String, Object>();
									one.put("reminder", "1");
									one.put("date", DateUtil.convertDateToIndianFormat(obj[2].toString()));
									Listhb.add(one);
								}
								if (obj[1] != null)
								{
									one = new HashMap<String, Object>();
									one.put("reminder", "2");
									one.put("date", DateUtil.convertDateToIndianFormat(obj[1].toString()));
									Listhb.add(one);
								}
								if (obj[0] != null)
								{
									one = new HashMap<String, Object>();
									one.put("reminder", "3");
									one.put("date", DateUtil.convertDateToIndianFormat(obj[0].toString()));
									Listhb.add(one);
								}
							}
						}
						setMasterViewList(Listhb);
						setRecords(masterViewList.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public Map<String, String> getEmployeeMap()
	{
		return employeeMap;
	}

	public void setEmployeeMap(Map<String, String> employeeMap)
	{
		this.employeeMap = employeeMap;
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

	public ComplianceDashboardBean getTotalProductivity()
	{
		return totalProductivity;
	}

	public void setTotalProductivity(ComplianceDashboardBean totalProductivity)
	{
		this.totalProductivity = totalProductivity;
	}

	public String getTotalTask()
	{
		return totalTask;
	}

	public void setTotalTask(String totalTask)
	{
		this.totalTask = totalTask;
	}

	public String getTotalOnTime()
	{
		return totalOnTime;
	}

	public void setTotalOnTime(String totalOnTime)
	{
		this.totalOnTime = totalOnTime;
	}

	public String getTotalOffTime()
	{
		return totalOffTime;
	}

	public void setTotalOffTime(String totalOffTime)
	{
		this.totalOffTime = totalOffTime;
	}

	public String getTotalSnooze()
	{
		return totalSnooze;
	}

	public void setTotalSnooze(String totalSnooze)
	{
		this.totalSnooze = totalSnooze;
	}

	public String getTotalPerOnTime()
	{
		return totalPerOnTime;
	}

	public void setTotalPerOnTime(String totalPerOnTime)
	{
		this.totalPerOnTime = totalPerOnTime;
	}

	public String getTotalPerOffTime()
	{
		return totalPerOffTime;
	}

	public void setTotalPerOffTime(String totalPerOffTime)
	{
		this.totalPerOffTime = totalPerOffTime;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getComplId()
	{
		return complId;
	}

	public void setComplId(String complId)
	{
		this.complId = complId;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
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

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}
}
