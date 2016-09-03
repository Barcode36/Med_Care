package com.Over2Cloud.ctrl.feedback.common;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import java.io.InputStream;
import java.util.Properties;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceEditGridAction;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackUniversalAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String dbName = (String) session.get("Dbname");
	private String destination;
	private String subDept;
	private String feedTypeId;
	private String dept;
	private String catg;
	private String subcatg;
	private String location;
	private String from_page;
	private String listvalue;
	private static int AN = 0;
	private String deptHierarchy;
	private String deptOrSubDeptId;

	// for help desk modules
	private int levelOfFeedDraft = 1;
	private int levelOfSurvey = 1;

	private String feedbackDarft;
	private String feedLevel1;
	private String feedLevel2;
	private String feedLevel3;

	private String surveyLevel1;
	private String surveyLevel2;

	private String shiftConf;
	private String lodgeFeedback;
	private String feedStatus;
	private String shiftLevel;
	private String pageHeader;
	private String feedDarftLevelName;
	private String feedDarftCategoryLevelName;
	private String survey;

	private String roasterDownload;
	private String feedDarftDownload;

	// Variables defined for Excel Download
	private String DeptName;
	private String daily_report;
	private String report;
	private String dept_subdept;

	// Variables defined for Feedback Draft
	private String addressTime;
	private String escTime;
	private String flag;
	private String feedFor1;
	private String feedFor2;
	private boolean successStatus = false;
	private boolean accordianItem = false;

	private List<ConfigurationUtilBean> ticketConfigColumns = null;

	// Variable Define for accessing the method
	private String ticketflag;

	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;

	private Map<String, String> shiftLevelName = new LinkedHashMap<String, String>();
	private Map<String, String> fdSubCategoryLevelName = new LinkedHashMap<String, String>();
	private Map<String, String> shiftLevelNameForDate = new LinkedHashMap<String, String>();
	private Map<String, String> fdSubCategoryLevelNameForDate = new LinkedHashMap<String, String>();

	private List<ConfigurationUtilBean> surveyEventColumns = null;
	private List<ConfigurationUtilBean> surveyCategoryColumns = null;

	private List<ConfigurationUtilBean> feedbackTypeColumns = null;
	private List<ConfigurationUtilBean> feedbackCategoryColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryDDColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryTextColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryTimeColumns = null;

	private List<ConfigurationUtilBean> shiftTextColumns = null;
	private List<ConfigurationUtilBean> shiftTimeColumns = null;

	private List<ConfigurationUtilBean> complanantTextColumns = null;
	private List<ConfigurationUtilBean> feedbackDDColumns = null;
	private List<ConfigurationUtilBean> feedbackDTimeColumns = null;
	private List<ConfigurationUtilBean> feedbackTextColumns = null;

	private List<ConfigurationUtilBean> reportconfigDDColumns = null;
	private List<ConfigurationUtilBean> reportconfigDTColumns = null;
	private List<ConfigurationUtilBean> reportconfigTimeColumns = null;

	private Map<Integer, String> deptList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> serviceDeptList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> shiftlist = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> subDeptList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> empList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> downloadSubDeptList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> feedTypelist = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> feedCategorylist = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> feedSubCategorylist = new LinkedHashMap<Integer, String>();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	FeedbackDraftPojo feedDraft = null;
	boolean checkdept = false;

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept, String module, String level, String floor_status, String floor, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		// String qry = null;
		StringBuilder query = new StringBuilder();
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			query.append("select distinct emp.id from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
			if (module != null && !module.equals("") && module.equals("HDM"))
			{
				query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
				query.append(" inner join department dept on sub_dept.deptid = dept.id ");
				query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
			}
			else if (module != null && !module.equals("") && module.equals("FM"))
			{
				query.append(" inner join department dept on roaster.dept_subdept = dept.id ");
				query.append(" inner join shift_conf shift on dept.id = shift.dept_subdept ");
			}
			query.append(" where shift.shiftName=" + shiftname + " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='" + level + "' and contacts.work_status='3' and contacts.moduleName='" + module + "'");
			query.append(" and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'");
			if (module != null && !module.equals("") && module.equals("HDM"))
			{
				if (floor_status.equalsIgnoreCase("Y"))
				{
					query.append(" and roaster.floor='" + floor + "'  and dept.id=(select deptid from subdepartment where id='" + dept_subDept + "')");
				}
				else if (floor_status.equalsIgnoreCase("N"))
				{
					query.append(" and sub_dept.id='" + dept_subDept + "'");
				}
			}
			else if (module != null && !module.equals("") && module.equals("FM"))
			{
				query.append(" and dept.id='" + dept_subDept + "'");
			}
		//	System.out.println("First Query   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public synchronized static String getTicketNo(String deptid, String moduleName, SessionFactory connectionSpace)
	{
		String ticketno = "NA", ticket_type = "", ticket_series = "", prefix = "", sufix = "", ticket_no = "";
		List ticketSeries = new ArrayList();
		List deptTicketSeries = new ArrayList();
		try
		{
			// Code for getting the Ticket Type from table
			// ticketSeries = new
			// HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf",
			// null, null, null, null, connectionSpace);
			ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf", "moduleName", moduleName, "", "", connectionSpace);
			if (ticketSeries != null && ticketSeries.size() == 1)
			{
				for (Iterator iterator = ticketSeries.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					ticket_type = object[1].toString();
					ticket_series = object[2].toString();
				}
				// Code for getting the first time counters of Feedback Status
				// table, If get ticket counts greater than Zero than go in If
				// block and if get 0 than go in else block
				if (moduleName != null && !moduleName.equals("") && moduleName.equals("COMPL"))
				{
					ticket_no = new HelpdeskUniversalHelper().getComplTicketSeries(ticket_type, deptid, connectionSpace);
				}
				else if (moduleName != null && !moduleName.equals("") && moduleName.equals("CASTM"))
				{
					ticket_no = new HelpdeskUniversalHelper().getAssetTicketSeries(ticket_type, deptid, connectionSpace);
				}
				else if (moduleName != null && !moduleName.equals("") && moduleName.equals("ASTM"))
				{
					ticket_no = new HelpdeskUniversalHelper().getAssetSNOSeries(ticket_type, deptid, connectionSpace);
				}
				else
				{
					ticket_no = new HelpdeskUniversalHelper().getLatestTicket(ticket_type, deptid, moduleName, connectionSpace);
				}

				if (ticket_no != null && !ticket_no.equals(""))
				{
					if (ticket_no != null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N"))
					{
						if (moduleName != null && !moduleName.equals("") && moduleName.equals("COMPL"))
						{
							prefix = ticket_no.substring(0, 4);
							sufix = ticket_no.substring(4, ticket_no.length());
						}
						else if (moduleName != null && !moduleName.equals("") && moduleName.equals("FM"))
						{
							prefix = ticket_no.substring(ticket_no.length() - 6, ticket_no.length() - 4);
							sufix = ticket_no.substring(ticket_no.length() - 4, ticket_no.length());
						}
						else if (moduleName != null && !moduleName.equals("") && moduleName.equals("CASTM"))
						{
							prefix = ticket_no.substring(0, 3);
							sufix = ticket_no.substring(3, ticket_no.length());
						}
						else
						{
							prefix = ticket_no.substring(0, 2);
							sufix = ticket_no.substring(2, ticket_no.length());
						}
						setAN(Integer.parseInt(sufix));
						ticketno = prefix + ++AN;
					}
					else if (ticket_no != null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D"))
					{
						if (moduleName != null && !moduleName.equals("") && moduleName.equals("COMPL"))
						{
							prefix = ticket_no.substring(0, 4);
							sufix = ticket_no.substring(4, ticket_no.length());
						}
						else if (moduleName != null && !moduleName.equals("") && moduleName.equals("FM"))
						{
							prefix = ticket_no.substring(ticket_no.length() - 6, ticket_no.length() - 4);
							sufix = ticket_no.substring(ticket_no.length() - 4, ticket_no.length());
						}
						else if (moduleName != null && !moduleName.equals("") && moduleName.equals("CASTM"))
						{
							prefix = ticket_no.substring(0, 3);
							sufix = ticket_no.substring(3, ticket_no.length());
						}
						else if (moduleName != null && !moduleName.equals("") && moduleName.equals("ASTM"))
						{
							prefix = ticket_no.substring(0, 11);
							sufix = ticket_no.substring(11, ticket_no.length());
						}
						else
						{
							prefix = ticket_no.substring(0, 2);
							sufix = ticket_no.substring(2, ticket_no.length());
						}
						setAN(Integer.parseInt(sufix));
						ticketno = prefix + ++AN;
					}
				}
				else
				{
					if (ticket_type.equalsIgnoreCase("N"))
					{
						if (ticket_series != null && !ticket_series.equals("") && !ticket_series.equals("NA"))
						{
							ticketno = ticket_series;
						}
					}
					else if (ticket_type.equalsIgnoreCase("D"))
					{
						deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid, "moduleName", moduleName, "prefix", "ASC", connectionSpace);
						if (deptTicketSeries != null && deptTicketSeries.size() == 1)
						{
							for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();)
							{
								Object[] object1 = (Object[]) iterator2.next();
								if (object1[2] != null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA"))
								{
									ticketno = object1[2].toString() + object1[3].toString();
								}
								else
								{
									ticketno = "NA";
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ticketno;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String subdept, String module)
	{
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String qry = null;
		try
		{
			session = HibernateSessionFactory.getSession();
			qry = "select distinct allot_to from feedback_status where open_date='" + DateUtil.getCurrentDateUSFormat() + "' and to_dept_subdept=" + subdept + " and status='Pending' and moduleName='" + module + "'";
			// System.out.println("Third Query  "+qry);
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4EscalationOPD(String dept_subDept, String deptLevel, String level, String floor_status, String floor, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		// String qry = null;
		StringBuilder query = new StringBuilder();
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			query.append(" select distinct emp.id from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append("  inner join bed_mapping bed on contacts.id = bed.contact_id");
			query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
			query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
			query.append(" inner join department dept on sub_dept.deptid = dept.id ");
			query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
			query.append(" where shift.shiftName=" + shiftname + " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='" + level + "' and contacts.work_status='3' and contacts.moduleName='FM'");
			query.append(" and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'");
			if (floor_status.equalsIgnoreCase("Y"))
			{
				query.append(" and roaster.floor='" + floor + "'  and dept.id=(select deptid from subdepartment where id='" + dept_subDept + "')");
			}
			else
			{
				query.append(" and sub_dept.id='" + dept_subDept + "'");
			}

			//System.out.println("Get Employee from roaster   " + query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String firstActionMethod()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List departmentlist = new ArrayList();

				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();

				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");

				/* Get Department Data */
				deptHierarchy = (String) session.get("userDeptLevel");
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
					returnResult = SUCCESS;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
					returnResult = SUCCESS;
				}

				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");

				// Getting Id, Dept Name for Non Service Department
				departmentlist = getDataFromTable("department", colmName, wherClause, order, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
					returnResult = SUCCESS;
				}

				if (departmentlist != null)
					departmentlist.removeAll(departmentlist);

				// Getting Id, Dept Name for Service Department
				departmentlist = getServiceDept_SubDept(deptHierarchy, orgLevel, orgId, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object1[0], object1[1].toString());
					}
					returnResult = SUCCESS;
				}

				// get Feedback Draft Level and Column Names
				if (getFeedbackDarft() != null)
				{
					String feedlevels[] = null;
					List feedBackDraft = cbt.viewAllDataEitherSelectOrAll("mapped_feedbackdraft_level", colname, connectionSpace);
					if (feedBackDraft != null && feedBackDraft.size() > 0)
					{
						for (Object c : feedBackDraft)
						{
							Object[] dataC = (Object[]) c;
							levelOfFeedDraft = Integer.parseInt((String) dataC[0]);
							feedlevels = dataC[1].toString().split("#");
						}
					}
					if (feedlevels != null)
					{
						if (levelOfFeedDraft > 0)
						{
							feedLevel1 = feedlevels[0] + " >> Registration";
						}
						if (levelOfFeedDraft > 1)
						{
							feedLevel2 = feedlevels[1] + " >> Registration";
						}
						if (levelOfFeedDraft > 2)
						{
							feedLevel3 = feedlevels[2] + " >> Registration";
						}
						setfeedBackDraftTags(levelOfFeedDraft);
					}
					returnResult = SUCCESS;
				}

				if (getDaily_report() != null && getDaily_report().equals("1"))
				{
					setdailyReportTags();
					returnResult = SUCCESS;
				}

				if (getReport() != null && getReport().equals("1"))
				{
					setReportTags();
					returnResult = SUCCESS;
				}

				if (getTicketflag() != null && getTicketflag().equalsIgnoreCase("y"))
				{
					setTicketConfigTags();
					returnResult = SUCCESS;
				}

				if (getTicketflag() != null && getTicketflag().equalsIgnoreCase("v"))
				{
					String ticketSeriesType = "";
					List getTicketSeries = new HelpdeskUniversalAction().getDataFromTable("ticket_series_conf", null, null, null, connectionSpace);
					if (getTicketSeries != null && getTicketSeries.size() == 1)
					{
						for (Iterator iterator = getTicketSeries.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							ticketSeriesType = object[1].toString();
						}

						if (ticketSeriesType.equalsIgnoreCase("N"))
						{
							flag = "N";
							returnResult = "normalSeries";
						}
						else if (ticketSeriesType.equalsIgnoreCase("D"))
						{
							flag = "D";
							returnResult = "deptwiseSeries";
						}
					}
				}

				// Get Level & Fields for Shift Configuration
				if (getShiftConf() != null)
				{
					shiftLevel = "Shift Registration >> Add";
					setShiftTags();
					returnResult = SUCCESS;
				}

				// get Department Name and Sub Department List for Roaster and
				if ((getRoasterDownload() != null && getRoasterDownload().equals("1")) || (getFeedDarftDownload() != null && getFeedDarftDownload().equals("1")))
				{
					String userName = (String) session.get("uName");
					String deptLevel = (String) session.get("userDeptLevel");
					List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
					if (empData != null && empData.size() > 0)
					{
						for (Iterator iterator = empData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[4] != null && !object[4].toString().equals(""))
							{
								dept = object[4].toString();
							}
							if (object[5] != null && !object[5].toString().equals(""))
							{
								DeptName = object[5].toString();
							}
						}
					}

					List colName = new ArrayList();
					Map<String, Object> whrClause = new HashMap<String, Object>();
					Map<String, Object> ordr = new HashMap<String, Object>();
					colName.add("id");
					colName.add("subdeptname");
					whrClause.put("deptid", getDept());
					ordr.put("subdeptname", "ASC");

					List subDeptlist = getDataFromTable("subdepartment", colName, whrClause, ordr, connectionSpace);
					if (subDeptlist != null && subDeptlist.size() > 0)
					{
						for (Iterator iterator = subDeptlist.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							downloadSubDeptList.put((Integer) object[0], object[1].toString());
						}
					}

					// get Drop Down Fields for Feedback Draft Download
					setfeedBackDraftTags(3);
					returnResult = SUCCESS;
				}

				// Get Lodge Feedback Level and Fields Name
				if (getFeedStatus() != null && (getFeedStatus().equals("call") || getFeedStatus().equals("online") || getFeedStatus().equals("reAllot")))
				{
					if (getFeedStatus().equals("call"))
					{
						setPageHeader("Feeback Lodge >> Via Call");
						setFeedLodgeTags();
					}
					else if (getFeedStatus().equals("online"))
					{
						setPageHeader("Feeback Lodge >> Via Online");
						setFeedLodgeTags();
						/*
						 * List empdata= new
						 * HelpdeskUniversalAction().getEmpDataByUserName
						 * (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY),
						 * deptHierarchy); if (empdata!=null &&
						 * empdata.size()>0) { for (Iterator iterator =
						 * empdata.iterator(); iterator .hasNext();) { Object[]
						 * object = (Object[]) iterator.next();
						 * location=object[7].toString(); } }
						 */
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// Method for getting Sub Departments on the basis of department Id(In Use)
	@SuppressWarnings("unchecked")
	public String getSubDepartment()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List list = new ArrayList<String>();
				list.add("id");
				list.add("subdeptname");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subdeptname", "ASC");
				temp.put("deptid", getDept());
				list = new HelpdeskUniversalHelper().getDataFromTable("subdepartment", list, temp, null, order, connectionSpace);
				if (list.size() > 0)
				{
					for (Object c : list)
					{
						Object[] dataC = (Object[]) c;
						subDeptList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("subDeptDiv", "");
				}
				/*
				 * System.out.println("From Page Value is  "+from_page); if
				 * (from_page!=null && !from_page.equals("") &&
				 * from_page.equals("r")) { listvalue="All Sub Department"; }
				 * else { listvalue="Select Sub Department"; }
				 */
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

	@SuppressWarnings("unchecked")
	public String getFBType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				String deptLevel = (String) session.get("userDeptLevel");
				List feedTypeList = new ArrayList<String>();
				feedTypeList.add("id");
				feedTypeList.add("fbType");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("dept_subdept", getDeptOrSubDeptId());
				temp.put("deptHierarchy", deptLevel);
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("fbType", "ASC");

				feedTypeList = new HelpdeskUniversalHelper().getDataFromTable("feedback_type", feedTypeList, temp, null, order, connectionSpace);
				if (feedTypeList.size() > 0)
				{
					for (Object c : feedTypeList)
					{
						Object[] dataC = (Object[]) c;
						feedTypelist.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("feedTypeDiv", "");
				}
				/*
				 * System.out.println("From Page Value is in Feedback Type "+
				 * from_page); if (from_page!=null && !from_page.equals("") &&
				 * from_page.equals("r")) { listvalue="All Feedback Type"; }
				 * else { listvalue="Select Feedback Type"; }
				 */
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

	public String getNotificationDemo()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getFBCategory()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List feedCategory = new ArrayList<String>();
				feedCategory.add("id");
				feedCategory.add("categoryName");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("fbType", Integer.parseInt(getFeedTypeId()));
				// temp.put("hide_show", "Active");

				temp.put("hide_show", "1");
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("categoryName", "ASC");
				feedCategory = new HelpdeskUniversalHelper().getDataFromTable("feedback_category", feedCategory, temp, null, order, connectionSpace);
				// feedCategory = new
				// HelpdeskUniversalHelper().getCategorydetail(connectionSpace);

				if (feedCategory.size() > 0)
				{
					for (Object c : feedCategory)
					{
						Object[] dataC = (Object[]) c;
						feedCategorylist.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("categoryDiv", "");
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

	@SuppressWarnings("unchecked")
	public String getFBSubCategory()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			List feedSubCategory = new ArrayList<String>();
			feedSubCategory.add("id");
			feedSubCategory.add("subCategoryName");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("categoryName", Integer.parseInt(getCatg()));
			temp.put("hide_show", "1");
			Map<String, Object> order = new HashMap<String, Object>();
			order.put("subCategoryName", "ASC");
			feedSubCategory = new HelpdeskUniversalHelper().getDataFromTable("feedback_subcategory_feedback", feedSubCategory, temp, null, order, connectionSpace);
			if (feedSubCategory.size() > 0)
			{
				for (Object c : feedSubCategory)
				{
					Object[] dataC = (Object[]) c;
					feedSubCategorylist.put((Integer) dataC[0], dataC[1].toString());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// Method for getting Sub Departments on the basis of department Id(In Use)
	@SuppressWarnings("unchecked")
	public String getEmployee()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List list = new ArrayList<String>();
				list.add("id");
				list.add("empName");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("empName", "ASC");
				if (deptLevel.equals("2"))
				{
					temp.put("subdept", getDept_subdept());
				}
				else if (deptLevel.equals("1"))
				{
					temp.put("dept", getDept_subdept());
				}

				list = new HelpdeskUniversalHelper().getDataFromTable("employee_basic", list, temp, null, order, connectionSpace);
				if (list.size() > 0)
				{
					for (Object c : list)
					{
						Object[] dataC = (Object[]) c;
						empList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				if (destination != null)
				{
					destination = destination.replace("empName", "");
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

	@SuppressWarnings("unchecked")
	public int getCountByField(String table, String field, String value)
	{
		int count = 0;
		List list = new ArrayList();
		try
		{
			String qry = "select count(*) from " + table + " where " + field + "='" + value + "'";
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (list.size() > 0)
		{
			count = Integer.parseInt(list.get(0).toString());
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept, String deptLevel, String feedid, String level, SessionFactory connectionSpace)
	{
		List empList = new ArrayList();
		String qry = null;
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			if (deptLevel.equals("2"))
			{
				qry = " select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join subdepartment sub_dept on emp.subdept = sub_dept.id" + " inner join feedback_type feedtype on sub_dept.id  = feedtype.dept_subdept" + " inner join roaster_conf roaster on emp.empId = roaster.empId"
						+ " inner join shift_conf shift on sub_dept.id = shift.dept_subdept" + " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='" + level + "' and sub_dept.id='" + dept_subDept + "' and shift.deptHierarchy='2' and shift.shiftFrom<='" + DateUtil.getCurrentTime()
						+ "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			else if (deptLevel.equals("1"))
			{
				qry = " select emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join department dept on emp.dept = dept.id" + " inner join feedback_type feedtype on dept.id = feedtype.dept_subdept" + " inner join roaster_conf roaster on emp.empId = roaster.empId"
						+ " inner join shift_conf shift on dept.id = shift.dept_subdept" + " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='" + level + "' and feedtype.id=" + feedid + " and dept.id='" + dept_subDept + "' and shift.deptHierarchy='1' and shift.shiftFrom<='"
						+ DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			// System.out.println("Employee by Sub department wise  for Esc   "+qry);
			empList = new createTable().executeAllSelectQuery(qry, connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmp4EscInAllDept(String dept_subDept, String deptLevel, String feedid, String level, SessionFactory connectionSpace)
	{
		List empList = new ArrayList();
		String qry = null;
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			if (deptLevel.equals("2"))
			{
				qry = " select emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join subdepartment sub_dept on emp.subdept = sub_dept.id" + " inner join department dept on sub_dept.deptid = dept.id" + " inner join feedback_type feedtype on sub_dept.id  = feedtype.dept_subdept"
						+ " inner join roaster_conf roaster on emp.empId = roaster.empId" + " inner join shift_conf shift on sub_dept.id = shift.dept_subdept" + " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='" + level + "' and dept.id='" + dept_subDept
						+ "' and shift.deptHierarchy='2' and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			// System.out.println("Deptwise escalation  detail  "+qry);
			empList = new createTable().executeAllSelectQuery(qry, connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield, String searchfieldvalue, String orderfield, String order)
	{
		List SubdeptallList = new ArrayList();
		try
		{
			String query = "select * from " + table + " where " + searchfield + "='" + searchfieldvalue + "' ORDER BY " + orderfield + " " + order + "";
			SubdeptallList = new createTable().executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SubdeptallList;
	}

	@SuppressWarnings("unchecked")
	public List getConfigReportData(String table, String searchfield, String searchfieldvalue, String orderfield, String order)
	{
		List SubdeptallList = new ArrayList();
		try
		{
			String subdeptall = "select report_conf.id,emp.empName,report_conf.report_level,report_conf.report_type,report_conf.email_subject,report_conf.report_date,report_conf.report_time,report_conf.sms,report_conf.mail,report_conf.status,subdept.subdeptname from employee_basic as emp" + " inner join report_configuration as report_conf on report_conf.empId=emp.id"
					+ " inner join subdepartment as subdept on subdept.id=report_conf.dept_subdept" + " where subdept.id=" + searchfieldvalue + " ORDER BY " + orderfield + " " + order + "";
			SubdeptallList = new createTable().executeAllSelectQuery(subdeptall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SubdeptallList;
	}

	@SuppressWarnings("unchecked")
	public List getDataList(String table, String selectfield, String searchfield, String searchfieldvalue, SessionFactory connectionSpace)
	{
		List dataList = new ArrayList();
		try
		{
			String data = "select " + selectfield + " from " + table + " where " + searchfield + "='" + searchfieldvalue + "'";
			dataList = new createTable().executeAllSelectQuery(data, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public String getData(String table, String selectfield, String searchfield, String searchfieldvalue)
	{
		List dataList = new ArrayList();
		String data = "";
		try
		{
			String qry = "select " + selectfield + " from " + table + " where " + searchfield + "='" + searchfieldvalue + "'";
			dataList = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				data = object.toString();
			}
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4EscInDept(String dept_subDept, String deptLevel, String level, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		try
		{
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8, 10) + "_date";
			if (deptLevel.equals("2"))
			{
				qry = " select emp.id from employee_basic as emp" + " inner join designation desg on emp.designation = desg.id" + " inner join subdepartment sub_dept on emp.subdept = sub_dept.id" + " inner join department dept on sub_dept.deptid = dept.id" + " inner join roaster_conf roaster on emp.empId = roaster.empId" + " inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
						+ " where shift.shiftName=" + shiftname + " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='" + level + "' and dept.id='" + dept_subDept + "' and shift.deptHierarchy='2' and shift.shiftFrom<='" + DateUtil.getCurrentTime() + "' and shift.shiftTo >'" + DateUtil.getCurrentTime() + "'";
			}
			// System.out.println("Random By Dept  "+qry);
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept, String deptLevel, String toLevel, List empId, SessionFactory connectionSpace)
	{
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		try
		{
			qry = "select distinct allot_to from feedback_status as feed_status" + " inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id " + " inner join designation desg on sub_dept.id  = desg.mappedOrgnztnId" + " where feed_status.to_dept_subdept='" + dept_subDept + "' and feed_status.deptHierarchy=" + deptLevel + " and desg.levelofhierarchy='" + toLevel
					+ "' and allot_to in " + empId.toString().replace("[", "(").replace("]", ")") + " and feed_status.status not in ('Resolved','Ignore','Snooze') and feed_status.open_date='" + DateUtil.getCurrentDateUSFormat() + "'";
			// System.out.println("Random Employee  by Sub dept  taking ticket today  "+qry);
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getRandomEmployee(String module, List empId)
	{
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String allotmentId = "";
		StringBuilder sb = new StringBuilder();
		try
		{
			session = HibernateSessionFactory.getSession();
			sb.append("select allot_to from feedback_status where open_date='" + DateUtil.getCurrentDateUSFormat() + "' and moduleName='" + module + "'");
			sb.append(" group by allot_to having allot_to in " + empId.toString().replace("[", "(").replace("]", ")") + " order by count(allot_to) limit 1 ");

			/*
			 * qry = "select allot_to from feedback_status where open_date='" +
			 * DateUtil.getCurrentDateUSFormat() +
			 * "' group by allot_to having allot_to in " +
			 * empId.toString().replace("[", "(").replace("]", ")") +
			 * " order by count(allot_to) limit 1 ";
			 */
			// System.out.println("Final list >>>>>>>>>>>>>>>>>>>>>>  "+sb.toString());
			list = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				allotmentId = object.toString();
			}
		}
		return allotmentId;
	}

	@SuppressWarnings("unchecked")
	public List getTicketNumber(SessionFactory connectionSpace)
	{
		List ticketno = new ArrayList();
		try
		{
			String ticket_no = "select ticket_no from feedback_status  where id=(select max(id) from feedback_status)";
			ticketno = new createTable().executeAllSelectQuery(ticket_no, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ticketno;
	}

	@SuppressWarnings("unchecked")
	public List getEmpData(String uid, String deptLevel)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,sdept.id as sdeptid,sdept.subdeptname,emp.city from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id" + " inner join department as dept on sdept.deptid=dept.id where emp.empId='" + uid + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,dept.id as deptid,dept.deptname,emp.city from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id where emp.empId='" + uid + "'";
			}
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmpDataByUserName(String uName, String deptLevel)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid, dept.deptName,emp.id as empid,emp.city from employee_basic as emp" + " inner join department as dept on dept.id=emp.deptname" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName,emp.id as empid,emp.city from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			// System.out.println("Employee For roaster   "+empall);
			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmployeeData(String uid, String deptLevel)
	{
		List empList = new ArrayList();
		String empall = "";
		try
		{

			if (deptLevel.equals("2"))
			{
				empall = "select empName,mobOne,subdept,empId  from employee_basic where id='" + uid + "'";
			}
			else
			{
				empall = "select empName,mobOne,dept,empId  from employee_basic where id='" + uid + "'";
			}

			empList = new createTable().executeAllSelectQuery(empall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	// Method for getting Feedback Category and Sub Category Detail
	@SuppressWarnings("unchecked")
	public String getFBSubCategDetail()
	{
		String returnResult = ERROR;
		try
		{
			feedDraft = new FeedbackDraftPojo();
			List subCatgDetail = new ArrayList<String>();
			Map<String, Object> temp = new HashMap<String, Object>();

			temp.put("id", Integer.parseInt(getSubcatg()));
			Map<String, Object> order = new HashMap<String, Object>();
			subCatgDetail = new HelpdeskUniversalHelper().getDataFromTable("feedback_subcategory_feedback", null, temp, null, order, connectionSpace);
			if (subCatgDetail != null && subCatgDetail.size() > 0)
			{
				for (Iterator iterator = subCatgDetail.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null || objectCol[0].toString().equals(""))
					{
					}
					else
					{
						feedDraft.setId((Integer) objectCol[0]);
					}
					if (objectCol[3] == null && objectCol[3].toString().equals(""))
					{
						feedDraft.setFeed_msg("NA");
					}
					else
					{
						feedDraft.setFeed_msg(objectCol[3].toString());
					}
					if (objectCol[4] == null && objectCol[4].toString().equals(""))
					{
						feedDraft.setAddressing_time("00:30");
					}
					else
					{
						feedDraft.setAddressing_time(objectCol[4].toString());
					}
					if (objectCol[5] == null && objectCol[5].toString().equals(""))
					{
						feedDraft.setEscalation_time("02:00");
					}
					else
					{
						feedDraft.setEscalation_time(objectCol[5].toString());
					}
					if (objectCol[4] != null && !objectCol[4].toString().equals("") && objectCol[5] != null && !objectCol[5].toString().equals(""))
					{
						String date_time = DateUtil.newdate_AfterEscTimeOld(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), objectCol[4].toString(), objectCol[5].toString());
						// feedDraft.setResolution_time(DateUtil.getResolutionTime(objectCol[4].toString(),objectCol[5].toString()));
						if (date_time != null)
						{
							String strarr[] = date_time.split("#");
							if (strarr != null && strarr[0] != null && strarr[1] != null)
							{
								feedDraft.setResolution_time(DateUtil.convertDateToIndianFormat(strarr[0]) + " " + strarr[1]);
							}
						}
						else
						{
							feedDraft.setResolution_time(DateUtil.getResolutionTime(objectCol[4].toString(), objectCol[5].toString()));
						}
					}
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String getResolutionTime11()
	{
		String returnResult = ERROR;
		try
		{
			feedDraft = new FeedbackDraftPojo();
			String resolution_time = null;
			String[] arr1 = null;
			String[] arr2 = null;
			if (!addressTime.equals("") && !escTime.equals(""))
			{
				arr1 = addressTime.split(":");
				arr2 = escTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60)
				{
					c = a;
					resolution_time = "" + b + ":" + a;
				}
				else
				{
					d = a / 60;
					c = a % 60;
					d = b + d;
					resolution_time = "" + d + ":" + c;
				}
				if (resolution_time != null && !resolution_time.equals(""))
				{
					feedDraft.setResolution_time(resolution_time);
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String addTwoTimes(String addressTime, String resolutionTime)
	{
		String escalation_time = null;
		try
		{
			String[] arr1 = null;
			String[] arr2 = null;
			if (!addressTime.equals("") && !resolutionTime.equals(""))
			{
				arr1 = addressTime.split(":");
				arr2 = resolutionTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60)
				{
					c = a;
					escalation_time = "" + b + ":" + a;
				}
				else
				{
					d = a / 60;
					c = a % 60;
					d = b + d;
					escalation_time = "" + d + ":" + c;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return escalation_time;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String id)
	{
		List feedbackList = new ArrayList();
		try
		{
			String subdeptall = "select feed_msg,resolution_time,escalation_time,sub_catg_name from sub_category_detail where sub_catg_id=" + id + "";
			feedbackList = new createTable().executeAllSelectQuery(subdeptall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return feedbackList;
	}

	@SuppressWarnings("unchecked")
	public String getField(String table, String selectfield, String wherefield, String id, SessionFactory connectionSpace)
	{
		String returnField = "";
		List l = new ArrayList();
		try
		{
			String qry = "select " + selectfield + " from " + table + " where " + wherefield + "='" + id + "'";
			//System.out.println("HIIIIIIIIIIIII"+qry);
			l = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (l != null)
		{
			returnField = l.get(0).toString();
		}
		return returnField;
	}

	@SuppressWarnings("unchecked")
	public int getCountByField(String key, String tableName, Map<String, Object> condtnBlock)
	{
		int count = 0;
		List listdata = new ArrayList();
		StringBuilder selectTableData = new StringBuilder("");

		selectTableData.append("select count(*)");

		selectTableData.append(" from " + tableName + "");
		if (condtnBlock.size() > 0)
		{
			selectTableData.append(" where ");
		}

		int size = 1;
		Set set = condtnBlock.entrySet();
		Iterator i = set.iterator();
		i = set.iterator();
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			if (size < condtnBlock.size())
				selectTableData.append(me.getKey() + " = '" + me.getValue() + "' and ");
			else
				selectTableData.append(me.getKey() + " = '" + me.getValue() + "' ");
			size++;
		}

		try
		{
			listdata = new createTable().executeAllSelectQuery(selectTableData.toString(), connectionSpace);
			if (listdata.size() > 0)
			{
				count = Integer.parseInt(listdata.get(0).toString());
			}
		}
		catch (Exception ex)
		{
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public int getCount4Feedback(String table, String field1, String field1value, String fromDate, String toDate, String field2, List field2value, String field3, String field3value, SessionFactory connectionSpace)
	{
		Session session = null;
		List list = new ArrayList();
		int count = 0;
		StringBuffer qry = new StringBuffer();
		try
		{
			session = HibernateSessionFactory.getSession();
			qry.append("select count(*) from " + table + " where " + field1 + "='" + field1value + "' and  " + field2 + " in " + field2value.toString().replace("[", "(").replace("]", ")") + " and " + field3 + "='" + field3value + "'");
			if (field1value.equals("Resolved"))
			{
				qry.append(" and open_date between '" + fromDate + "' and '" + toDate + "'");
			}
			list = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				count = Integer.parseInt(object.toString());
			}
		}
		return count;
	}

	public List getFeedbackActionTakenDetails(String ticketNo, String status, SessionFactory connectionSpace)
	{

		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			query.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
			query.append("feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,");
			query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
			query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
			query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
			query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.feedback_remarks");
			if (status != null && status.equalsIgnoreCase("Resolved"))
			{
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
			}
			query.append(" from feedback_status as feedback");
			query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			if (status != null && status.equalsIgnoreCase("Resolved"))
			{
				query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
			}

			query.append(" where feedback.ticket_no like '" + ticketNo + "%'");

			/*
			 * if (status.equals("Resolved")) {
			 * query.append(" and feedback.open_date between '"
			 * +fromDate+"' and '"+toDate+"'"); }
			 */

			query.append(" and feedback.moduleName='FM'");
			query.append(" ORDER BY feedback.ticket_no ");

			//System.out.println(" History Querry is as " + query);

			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}

	public List<String> getDeptWiseTicketsId(List<String> deptList, SessionFactory connectionSpace)
	{
		List<String> dept = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer("select distinct id from feedback_status where to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");

		// System.out.println(">>>DPT>"+buffer);
		List data = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					dept.add(object.toString());
				}
			}
		}
		return dept;
	}

	public List<String> getParallelShareTicketsId(Set<String> allotIds, SessionFactory connectionSpace)
	{
		List<String> dept = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer("select distinct id from feedback_status where allot_to in " + allotIds.toString().replace("[", "(").replace("]", ")") + "");

		// System.out.println("Parallel Sharing ::"+buffer);
		List data = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					dept.add(object.toString());
				}
			}
		}
		return dept;
	}

	public String getFeedbackPatId(String feedId)
	{
		String clientId = "";
		List list = new createTable().executeAllSelectQuery("select clientId from feedbackdata where id='" + feedId + "'", connectionSpace);
		if (list != null && list.size() > 0)
		{
			if (list.get(0) != null)
			{
				clientId = list.get(0).toString();
			}
		}

		return clientId;
	}

	public List getFeedbackTicketFullDetailsForClient(String crNo)
	{
		List list = new ArrayList();
		try
		{
			StringBuilder query = new StringBuilder("");
			query.append(" select feedback.id,feedback.ticket_no,feedback.open_date,feedback.open_time,feedback.location,dept2.deptName as todept," + " emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief," + " feedback.resolve_date,feedback.resolve_time,feedback.resolve_remark,feedback.spare_used,feedback.status,feedback.via_from");
			query.append(" from feedback_status as feedback");
			query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			query.append(" where feedback.moduleName='FM'");

			if (crNo != null && !crNo.equalsIgnoreCase(""))
			{
				query.append(" and feedback.clientId = '" + crNo + "'");
				// query.append(" and feedback.id not in (select min(id) from feedback_status where clientId =  '"+crNo+"')");
			}
			query.append(" ORDER BY feedback.id desc");
			// System.out.println("History ::"+query);
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String table, String status, String fromDate, String todate, String deptid, String logEmpId, String orderField, String order, String searchField, String searchString, String searchOper, SessionFactory connectionSpace, String mode, String feedType, String feedCat, String ticketNo, String feedBy, String crNo, String userType, String userName)
	{

		List list = new ArrayList();
		try
		{
			// Method for For DeptIds

			List<String> deptList = new ArrayList<String>();
			/*
			 * if(!isAdminDept(Integer.parseInt(deptid))) {
			 * deptList=getLoggedInEmpForDept
			 * (logEmpId,deptid,"FM",connectionSpace); }
			 */

			/*
			 * if(deptList!=null && deptList.size()>0) { List<String>
			 * deptByIds=getDeptWiseTicketsId(deptList,connectionSpace);
			 * idsSet.addAll(deptByIds); }
			 * 
			 * // Method For Parallel Sharing Set<String>
			 * sharingList=getParallelSharingEmpIds
			 * (logEmpId,"FM",connectionSpace); if(sharingList!=null &&
			 * sharingList.size()>0) { List<String>
			 * sharingIds=getParallelShareTicketsId
			 * (sharingList,connectionSpace); idsSet.addAll(sharingIds); }
			 * 
			 * // Method For sharingList.clear();
			 * 
			 * sharingList=getLowerEmpId(logEmpId,"FM",connectionSpace);
			 * if(sharingList!=null && sharingList.size()>0) { List<String>
			 * sharingIds
			 * =getParallelShareTicketsId(sharingList,connectionSpace);
			 * idsSet.addAll(sharingIds); }
			 */

			// Querry For Ticket Details
			StringBuilder query = new StringBuilder("");

			query.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
			query.append("feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,");
			query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
			query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
			query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
			query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.feedback_remarks,feedback.clientId,feedback.patMobNo,feedback.patEmailId");
			if (status.equalsIgnoreCase("Resolved"))
			{
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used,feedback.actionComments");
			}
			if (status.equalsIgnoreCase("Noted"))
			{
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by");
			}
			query.append(" from feedback_status as feedback");
			query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Noted"))
			{
				query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
			}

			query.append(" where feedback.id !='0'");

			if (status != null && !status.equalsIgnoreCase(""))
			{
				query.append(" and feedback.status='" + status + "'");
			}
			//System.out.println("UserType is as >>>>>>>>>" + userType);
			/*
			 * if(deptList.size()>0) {
			 * query.append(" and feedback.to_dept_subdept in "+
			 * deptList.toString().replace("[", "(").replace("]",")") + ""); }
			 */
			if (userType != null && userType.equalsIgnoreCase("M"))
			{

			}
			else if (userType != null && userType.equalsIgnoreCase("H"))
			{
				deptList = getLoggedInEmpForDept(logEmpId, deptid, "FM", connectionSpace);
				if (deptList.size() > 0)
				{
					query.append(" and feedback.to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");
				}
			}
			else
			{
				if (userName != null)
				{
					query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + logEmpId + "')");
				}
			}

			if (fromDate != null && todate != null && !fromDate.equalsIgnoreCase("") && !todate.equalsIgnoreCase(""))
			{
				String str[] = fromDate.split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					query.append(" and feedback.open_date between '" + (fromDate) + "' and '" + ((todate)) + "'");
				}
				else
				{
					query.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(todate)) + "'");
				}
			}
			else
			{
				query.append(" and feedback.open_date between '" + (DateUtil.getNextDateAfter(-6)) + "' and '" + (DateUtil.getCurrentDateUSFormat()) + "'");
			}
			if (mode != null && !mode.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.via_from='" + mode + "'");
			}

			if (feedType != null && !feedType.equalsIgnoreCase("-1"))
			{
				query.append(" and feedtype.fbType='" + feedType + "'");
			}

			if (feedCat != null && !feedCat.equalsIgnoreCase("-1"))
			{
				query.append(" and catg.categoryName='" + feedCat + "'");
			}
			// ticketNo
			if (ticketNo != null && !ticketNo.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.ticket_no='" + ticketNo + "'");
			}

			// feedBy
			if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.feed_by='" + feedBy + "'");
			}

			// System.out.println("CR No is as "+crNo);
			// crNo
			if (crNo != null && !crNo.equalsIgnoreCase("Enter MRD No") && !crNo.equalsIgnoreCase(""))
			{
				query.append(" and feedback.clientId like '" + crNo + "%'");
			}

			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and feedback." + searchField + " = '" + searchString + "'");
			}

			query.append(" and feedback.moduleName='FM'");

			query.append(" ORDER BY feedback.id desc");
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

			//System.out.println(query.toString() + "  ::: Query String");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getLoggedInEmpForDept(String empId, String deptId, String module, SessionFactory connectionSpace)
	{
		List<String> forDeptId = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer("select forDept_id from compliance_contacts where emp_id='" + empId + "' and moduleName='" + module + "' and work_status='3'");
		//System.out.println("Hellop" + buffer);
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					forDeptId.add(object.toString());
				}
			}
		}

		return forDeptId;
	}

	public Set<String> getParallelSharingEmpIds(String empId, String moduleName, SessionFactory connectionSpace)
	{

		Set<String> allotIdSet = new TreeSet<String>();
		allotIdSet.add(empId);
		StringBuffer buffer = new StringBuffer("select contacts.emp_id from compliance_contacts as contacts" + " where id in (select sharing.sharing_with from contact_sharing_detail as" + " sharing inner join compliance_contacts as contacts on contacts.id=sharing.contact_id" + " where contacts.emp_id='" + empId + "' and contacts.moduleName='" + moduleName + "')");

		// System.out.println("Sharing "+buffer);
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null && !object.toString().equalsIgnoreCase(""))
				{
					allotIdSet.add(object.toString());
				}
			}
		}
		return allotIdSet;
	}

	public Map<String, String> getAllLevelOfLogInUser(String empId, SessionFactory connectionSpace, String moduleName)
	{
		Map<String, String> levelList = new HashMap<String, String>();
		StringBuffer buffer = new StringBuffer("select level,forDept_id from compliance_contacts where emp_id='" + empId + "' and moduleName='" + moduleName + "'");
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null && object[1] != null)
					{
						levelList.put(object[1].toString(), object[0].toString());
					}
				}
			}
		}
		return levelList;
	}

	public Set<String> getLowerEmpId(String empId, String moduleName, SessionFactory connectionSpace)
	{
		Set<String> empIdSet = new TreeSet<String>();
		/*
		 * StringBuffer buffer=new StringBuffer(
		 * "select contacts.emp_id from compliance_contacts as contacts " +
		 * " inner join contact_mapping_detail as mapping on mapping.contact_id=contacts.id "
		 * + " where mapping.mapped_with='"+empId+"' and contacts.moduleName='"+
		 * moduleName+"'"); //
		 * System.out.println("getLowerEmpId methos >>>"+buffer); List
		 * dataList=new createTable().executeAllSelectQuery(buffer.toString(),
		 * connectionSpace); if(dataList!=null && dataList.size()>0) { for
		 * (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
		 * Object object = (Object) iterator.next(); if(object!=null) {
		 * empIdSet.add(object.toString().trim()); } } }
		 */
		String lowerEmp = new ComplianceEditGridAction().getLevelHierarchy("FM", empId);
		if (lowerEmp != null)
		{
			String str[] = lowerEmp.split(",");
			if (str != null)
			{
				for (int i = 0; i < str.length; i++)
				{
					empIdSet.add(str[i]);
				}
			}
		}
		return empIdSet;
	}

	public List<String> getEmpHierarchy(String empId, String deptId, SessionFactory connectionSpace, String moduleName)
	{
		List<String> empList = new ArrayList<String>();
		Map<String, String> empDetailMap = getAllLevelOfLogInUser(empId, connectionSpace, "FM");
		for (Map.Entry<String, String> entry : empDetailMap.entrySet())
		{
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("5"))
			{

			}
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetailForDashboard(String status, String fromDate, String todate, String deptid, String order, String searchField, String searchString, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			query.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
			query.append("dept2.deptName as todept,emp.empName,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
			query.append("subcatg.addressingTime,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,");
			query.append("feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.location,feedback.feedback_remarks");
			if (status.equalsIgnoreCase("Resolved"))
			{
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
			}
			query.append(" from feedback_status as feedback");
			query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query.append(" inner join department subdept1 on feedback.by_dept_subdept= subdept1.id");
			query.append(" inner join department subdept2 on feedback.to_dept_subdept= subdept2.id");
			query.append(" inner join department dept1 on subdept1.id= dept1.id");
			query.append(" inner join department dept2 on subdept2.id= dept2.id");
			query.append(" inner join feedback_subcategory_feedback subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category_feedback catg on subcatg.categoryName = catg.id");
			if (status.equalsIgnoreCase("Resolved"))
			{
				query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
			}

			if (status.equalsIgnoreCase("Pending") && deptid != null)
			{
				query.append(" where feedback.status !='Resolved' and feedback.to_dept_subdept = '" + deptid + "'");
			}
			else if (status.equalsIgnoreCase("") && deptid != null)
			{
				query.append(" where feedback.to_dept_subdept = '" + deptid + "'");
			}
			else
			{
				query.append(" where feedback.status='" + status + "' and feedback.to_dept_subdept = '" + deptid + "'");
			}

			if (status.equals("Resolved"))
			{
				query.append(" and feedback.open_date between '" + fromDate + "' and '" + todate + "'");
			}

			if (searchField != null && !searchField.equalsIgnoreCase("") && searchString != null && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and " + searchField + "='" + searchString + "'");
			}
			query.append(" ORDER BY feedback.ticket_no " + order + "");
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			// System.out.println(query.toString()+"  ::: Query String");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getFeedbackToDeptIdByContacts(String deptId)
	{
		List<String> deptList = new ArrayList<String>();
		List dataList = new createTable().executeAllSelectQuery("select distinct forDept_id from compliance_contacts where fromDept_id='" + deptId + "' and moduleName='FM'", connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					deptList.add(object.toString());
				}
			}
		}
		return deptList;
	}

	public boolean isAdminDept(int deptId)
	{
		boolean adminDept = false;
		int adminDeptId = 0;
		try
		{
			Properties configProp = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
			configProp.load(in);
			String adminDeptName = configProp.getProperty("admin");

			if (adminDeptName != null)
			{
				adminDeptId = (new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select id from department where deptName='" + adminDeptName + "'"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (adminDeptId != 0)
		{
			if (deptId == adminDeptId)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return adminDept;
	}

	public List<String> getFeedbackToDeptId(String deptId)
	{
		List<String> deptList = new ArrayList<String>();
		String adminDeptName = null;
		try
		{
			Properties configProp = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
			configProp.load(in);
			adminDeptName = configProp.getProperty("admin");
			String adminDeptId = null;
			if (adminDeptName != null)
			{
				adminDeptId = String.valueOf(new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select id from department where deptName='" + adminDeptName + "'"));
			}

			if (adminDeptId != null)
			{
				if (deptId.equalsIgnoreCase(adminDeptId))
				{
					// Getting all dept Ids in the List
					// System.out.println("Getting All Dept Ids");
					// System.out.println("select distinct dept_subdept from feedback_type where dept_subdept!=0 and moduleName='FM'");
					List dataList = new createTable().executeAllSelectQuery("select distinct dept_subdept from feedback_type where dept_subdept!=0 and moduleName='FM'", connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object = (Object) iterator.next();
							if (object != null)
							{
								deptList.add(object.toString());
							}
						}
					}
				}
				else
				{
					deptList.add(deptId);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// System.out.println("DeptList Returned Size>>"+deptList.size());
		return deptList;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalReport(String reportFor, String fromDate, String todate, String dept, String tosubdept, String searchField, String searchString, String searchOper, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append("select dept1.deptName as bydept,subdept1.subdeptname as bysubdept,dept2.deptName as todept,subdept2.subdeptname as tosubdept,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,count(*) as counter from feedback_status as feedback");
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append("select dept2.deptName as dept,subdept2.subdeptname as subdept,emp.empName,emp.mobOne,emp.emailIdOne,count(*) as counter  from feedback_status as feedback");
			}

			query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
			query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
			query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append(" where open_date between '" + fromDate + "' and '" + todate + "' ");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" and  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=" + dept + ")");
				}
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" where open_date between '" + fromDate + "' and '" + todate + "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" and  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=" + dept + ")");
				}
			}

			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq"))
				{
					query.append(" " + searchField + " = '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("cn"))
				{
					query.append(" " + searchField + " like '%" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("bw"))
				{
					query.append(" " + searchField + " like '" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("ne"))
				{
					query.append(" " + searchField + " <> '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("ew"))
				{
					query.append(" " + searchField + " like '%" + searchString + "'");
				}
			}
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append(" group by feedback.subCatg order by counter desc");
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append(" group by emp.empName order by counter desc");
			}
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalGraphData(String reportFor, String fromDate, String todate, String dept, String tosubdept, String searchField, String searchString, String searchOper, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append("select  catg.categoryName,count(*) as counter from feedback_status as feedback");
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append("select emp.empName,count(*) as counter  from feedback_status as feedback");
			}

			query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
			query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
			query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append(" where open_date between '" + fromDate + "' and '" + todate + "' ");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" and  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=" + dept + ")");
				}
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" where open_date between '" + fromDate + "' and '" + todate + "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null && tosubdept.equals("-1"))
				{
					query.append(" and  dept2.id=" + dept + " ");
				}
				else if (dept != null && !dept.equals("-1") && tosubdept != null && !tosubdept.equals("-1"))
				{
					query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=" + dept + ")");
				}
			}

			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
			{
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq"))
				{
					query.append(" " + searchField + " = '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("cn"))
				{
					query.append(" " + searchField + " like '%" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("bw"))
				{
					query.append(" " + searchField + " like '" + searchString + "%'");
				}
				else if (searchOper.equalsIgnoreCase("ne"))
				{
					query.append(" " + searchField + " <> '" + searchString + "'");
				}
				else if (searchOper.equalsIgnoreCase("ew"))
				{
					query.append(" " + searchField + " like '%" + searchString + "'");
				}
			}
			if (reportFor.equalsIgnoreCase("C"))
			{
				query.append(" group by catg.categoryName order by counter desc");
			}
			else if (reportFor.equalsIgnoreCase("E"))
			{
				query.append(" group by emp.empName order by counter desc");
			}
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * @SuppressWarnings("unchecked") public List getAnalyticalDashData(String
	 * reportFor,String fromDate,String todate, SessionFactory connectionSpace)
	 * { List list = new ArrayList(); StringBuilder query = new
	 * StringBuilder(""); try { if (reportFor.equalsIgnoreCase("C")) {
	 * query.append(
	 * "select  catg.categoryName,count(*) as counter from feedback_status as feedback"
	 * ); } else if (reportFor.equalsIgnoreCase("E")) { query.append(
	 * "select emp.empName,count(*) as counter  from feedback_status as feedback"
	 * ); }
	 * 
	 * query.append(
	 * " inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id"
	 * ); query.append(
	 * " inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id"
	 * );
	 * query.append(" inner join department dept1 on subdept1.deptid= dept1.id"
	 * );
	 * query.append(" inner join department dept2 on subdept2.deptid= dept2.id"
	 * ); query.append(
	 * " inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"
	 * ); query.append(
	 * " inner join feedback_category catg on subcatg.categoryName = catg.id");
	 * if (reportFor.equalsIgnoreCase("C")) {
	 * query.append(" where open_date between '"
	 * +fromDate+"' and '"+todate+"' "); if (dept!=null && !dept.equals("-1") &&
	 * tosubdept!=null && tosubdept.equals("-1")) {
	 * query.append(" and  dept2.id="+dept+" "); } else if (dept!=null &&
	 * !dept.equals("-1") && tosubdept!=null && !tosubdept.equals("-1")) {
	 * query.append(
	 * " and feedback.to_dept_subdept in (select id from subdepartment where deptid="
	 * +dept+")"); } } else if (reportFor.equalsIgnoreCase("E")) {
	 * query.append(" inner join employee_basic emp on feedback.allot_to= emp.id"
	 * );
	 * query.append(" where open_date between '"+fromDate+"' and '"+toDate+"'");
	 * if (dept!=null && !dept.equals("-1") && tosubdept!=null &&
	 * tosubdept.equals("-1")) { query.append(" and  dept2.id="+dept+" "); }
	 * else if (dept!=null && !dept.equals("-1") && tosubdept!=null &&
	 * !tosubdept.equals("-1")) { query.append(
	 * " and feedback.to_dept_subdept in (select id from subdepartment where deptid="
	 * +dept+")"); } }
	 * 
	 * if (searchField != null && searchString != null &&
	 * !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	 * { query.append(" and");
	 * 
	 * if (searchOper.equalsIgnoreCase("eq")) { query.append(" " + searchField +
	 * " = '" + searchString + "'"); } else if
	 * (searchOper.equalsIgnoreCase("cn")) { query.append(" " + searchField +
	 * " like '%" + searchString + "%'"); } else if
	 * (searchOper.equalsIgnoreCase("bw")) { query.append(" " + searchField +
	 * " like '" + searchString + "%'"); } else if
	 * (searchOper.equalsIgnoreCase("ne")) { query.append(" " + searchField +
	 * " <> '" + searchString + "'"); } else if
	 * (searchOper.equalsIgnoreCase("ew")) { query.append(" " + searchField +
	 * " like '%" + searchString + "'"); } } if
	 * (reportFor.equalsIgnoreCase("C")) {
	 * query.append(" group by catg.categoryName order by counter desc"); } else
	 * if (reportFor.equalsIgnoreCase("E")) {
	 * query.append(" group by emp.empName order by counter desc"); }
	 * 
	 * System.out.println("Query for gettin Data >>>>>>>>>>>> ::: "+query.toString
	 * ()); list = new
	 * createTable().executeAllSelectQuery(query.toString(),connectionSpace); }
	 * catch (Exception e) { e.printStackTrace(); } return list; }
	 */

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail4Dashboard(String uid, String conditionValue, String fDate, String tDate, List subDeptList, String deptLevel, String searchField, String searchString, String searchOper, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (deptLevel.equals("2"))
			{
				query.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,feedback.feed_by,feedback.feed_by_mobno,");
				query.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
				query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by");
				/*
				 * if (field1value.equalsIgnoreCase("Resolved")) { query.append(
				 * ",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used"
				 * ); }
				 */
				query.append(" from feedback_status as feedback");
				if (conditionValue != null && conditionValue.equals("CL"))
				{
					query.append(" inner join employee_basic as emp on emp.mobOne=feedback.feed_by_mobno");
					query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				}
				else if (conditionValue != null && conditionValue.equals("CR"))
				{
					query.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
					query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				}
				else if (conditionValue != null && conditionValue.equals("HOD"))
				{
					query.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
					query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				}
				else if (conditionValue != null && conditionValue.equals("MGMT"))
				{
					query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				}
				query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
				query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
				query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				/*
				 * if (field1value.equalsIgnoreCase("Resolved")) { query.append(
				 * " inner join employee_basic emp1 on feedback.resolve_by= emp1.id"
				 * ); }
				 */

				if (conditionValue != null && (conditionValue.equals("CL") || conditionValue.equals("CR")))
				{
					query.append(" where userac.id='" + uid + "' and open_date between '" + fDate + "'  and '" + tDate + "'");
				}
				else if (conditionValue != null && conditionValue.equals("HOD"))
				{
					query.append(" where userac.id='" + uid + "' and feed_stats.to_dept_subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id= emp.subdept) and open_date between '" + fDate + "'  and '" + tDate + "') and");
				}
				else if (conditionValue != null && conditionValue.equals("MGMT"))
				{
					query.append(" where open_date between '" + fDate + "'  and '" + tDate + "' ");
				}
				query.append(" ORDER BY feedback.ticket_no DESC ");
			}

			else if (deptLevel.equals("1"))
			{/*
			 * query.append(
			 * "select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,"
			 * ); query.append(
			 * "feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,"
			 * ); query.append(
			 * "subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,"
			 * ); query.append(
			 * "feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,"
			 * ); query.append(
			 * "feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,"
			 * ); query.append(
			 * "feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,"
			 * ); query.append(
			 * "feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason"
			 * ); if (field1value.equalsIgnoreCase("Resolved")) { query.append(
			 * ",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used"
			 * ); } query.append(" from feedback_status as feedback");
			 * query.append
			 * (" inner join employee_basic emp on feedback.allot_to= emp.id");
			 * query
			 * .append(" inner join department dept1 on subdept1.deptid= dept1.id"
			 * ); query.append(
			 * " inner join department dept2 on subdept2.deptid= dept2.id");
			 * query.append(
			 * " inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"
			 * ); query.append(
			 * " inner join feedback_category catg on subcatg.categoryName = catg.id"
			 * ); query.append(
			 * " inner join feedback_type feedtype on catg.fbType = feedtype.id"
			 * ); if (field1value.equalsIgnoreCase("Resolved")) { query.append(
			 * " inner join employee_basic emp1 on feedback.resolve_by= emp1.id"
			 * ); } query.append(" where feedback.status='"+ field1value+
			 * "' and feedback.deptHierarchy='"+ field3value+
			 * "' and feedback.to_dept_subdept in "+
			 * field2value.toString().replace("[", "(").replace("]",")") + "");
			 * 
			 * if (searchField != null && searchString != null &&
			 * !searchField.equalsIgnoreCase("") &&
			 * !searchString.equalsIgnoreCase("")) { query.append(" and");
			 * 
			 * if (searchOper.equalsIgnoreCase("eq")) {query.append(" " +
			 * searchField + " = '" + searchString+ "'"); } else if
			 * (searchOper.equalsIgnoreCase("cn")) { query.append(" " +
			 * searchField + " like '%"+ searchString + "%'"); } else if
			 * (searchOper.equalsIgnoreCase("bw")) { query.append(" " +
			 * searchField + " like '"+ searchString + "%'"); } else if
			 * (searchOper.equalsIgnoreCase("ne")) { query.append(" " +
			 * searchField + " <> '" + searchString+ "'"); } else if
			 * (searchOper.equalsIgnoreCase("ew")) { query.append(" " +
			 * searchField + " like '%"+ searchString + "'"); } }
			 */
				query.append(" ORDER BY feedback.ticket_no DESC");
			}
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int getDeptId(String user, SessionFactory connectionSpace)
	{
		int count = 0;
		List list = new ArrayList();
		String qry = null;
		try
		{
			qry = " select dept.id from department as dept" + " inner join subdepartment as subdept on subdept.deptid = dept.id" + " inner join employee_basic as emp on emp.subdept = subdept.id" + " inner join useraccount as user on user.id = emp.useraccountid" + " where user.userID = '" + user + "'";
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (list.size() > 0)
		{
			count = Integer.parseInt(list.get(0).toString());
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName, Map<String, Object> wherClause, Map<String, Object> order, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select ");

		// Set the columns name of a table
		if (colmName != null && !colmName.equals("") && !colmName.isEmpty())
		{
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}

		// Here we get the whole data of a table
		else
		{
			selectTableData.append(" * from " + tableName);
		}

		// Set the values for where clause
		if (wherClause != null && !wherClause.isEmpty())
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = " + me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '" + me.getValue() + "'");
				size++;
			}
		}

		// Set the value for type of order for getting data in specific order
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " " + me.getValue() + "");
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		return Data;
	}

	// Method for getting the field Name for feedback Draft Module
	public void setfeedBackDraftTags(int flag)
	{
		// flag value is getting the employee level data based on the selected
		// no of employee configuration
		ConfigurationUtilBean obj;
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		if (flag > 0)
		{
			List<GridDataPropertyView> feedbackTypeList = Configuration.getConfigurationData("mapped_feedback_type_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_type_configuration");
			feedbackTypeColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackTypeList != null && feedbackTypeList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackTypeList)
				{
					obj = new ConfigurationUtilBean();
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					feedbackTypeColumns.add(obj);
				}
			}
		}
		if (flag > 1)
		{
			List<GridDataPropertyView> feedbackCategoryList = Configuration.getConfigurationData("mapped_feedback_category_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_category_configuration");
			feedbackCategoryColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackCategoryList != null && feedbackCategoryList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackCategoryList)
				{
					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("T") || gdv.getColType().trim().equalsIgnoreCase("D"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						feedbackCategoryColumns.add(obj);
					}
				}
			}
		}
		if (flag > 2)
		{
			List<GridDataPropertyView> feedbackSubCategoryList = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_subcategory_configuration");
			feedbackSubCategoryDDColumns = new ArrayList<ConfigurationUtilBean>();
			feedbackSubCategoryTextColumns = new ArrayList<ConfigurationUtilBean>();
			feedbackSubCategoryTimeColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackSubCategoryList != null && feedbackSubCategoryList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackSubCategoryList)
				{

					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("D"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						feedbackSubCategoryDDColumns.add(obj);
					}
					else if (gdv.getColType().trim().equalsIgnoreCase("T"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						feedbackSubCategoryTextColumns.add(obj);
					}
					else if (gdv.getColType().trim().equalsIgnoreCase("Time"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						feedbackSubCategoryTimeColumns.add(obj);
					}
				}
			}
		}
	}

	public void setdailyReportTags()
	{
		ConfigurationUtilBean obj;
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_daily_report_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_report_configuration");
		reportconfigDDColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigDTColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigTimeColumns = new ArrayList<ConfigurationUtilBean>();

		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().trim().equalsIgnoreCase("empId") || gdv.getColomnName().trim().equalsIgnoreCase("report_level"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					reportconfigDDColumns.add(obj);
				}
				else if (gdv.getColomnName().trim().equalsIgnoreCase("report_type") || gdv.getColomnName().trim().equalsIgnoreCase("email_subject"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					reportconfigDTColumns.add(obj);
				}
				else if (gdv.getColomnName().trim().equalsIgnoreCase("report_date") || gdv.getColomnName().trim().equalsIgnoreCase("report_time"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					reportconfigTimeColumns.add(obj);
				}
			}
		}
	}

	public void setReportTags()
	{
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_report_panel_configuration", accountID, connectionSpace, "", 0, "table_name", "report_panel_configuration");
		reportconfigDDColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigDTColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigTimeColumns = new ArrayList<ConfigurationUtilBean>();

		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();

				obj.setKey(gdv.getColomnName());
				obj.setValue(gdv.getHeadingName());
				obj.setValidationType(gdv.getValidationType());
				obj.setColType(gdv.getColType());
				if (gdv.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				}
				else
				{
					obj.setMandatory(false);
				}
				reportconfigDDColumns.add(obj);
			}
		}
		// System.out.println("reportconfigDDColumns size is as <>>>>>>>>>>>>>>>>>>>>>>>>>"+reportconfigDDColumns.size());
	}

	public void setTicketConfigTags()
	{
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> ticketConfigList = Configuration.getConfigurationData("mapped_ticket_panel_configuration", accountID, connectionSpace, "", 0, "table_name", "ticket_panel_configuration");
		ticketConfigColumns = new ArrayList<ConfigurationUtilBean>();
		if (ticketConfigList != null && ticketConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : ticketConfigList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				obj.setKey(gdv.getColomnName());
				obj.setValue(gdv.getHeadingName());
				obj.setValidationType(gdv.getValidationType());
				obj.setColType(gdv.getColType());
				if (gdv.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				}
				else
				{
					obj.setMandatory(false);
				}
				ticketConfigColumns.add(obj);
			}
		}
	}

	public void setTicketConfigTags(String seriesType)
	{
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		List<GridDataPropertyView> ticketConfigList = Configuration.getConfigurationData("mapped_ticket_panel_configuration", accountID, connectionSpace, "", 0, "table_name", "ticket_panel_configuration");

		ticketConfigColumns = new ArrayList<ConfigurationUtilBean>();
		if (ticketConfigList != null && ticketConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : ticketConfigList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();

				if (seriesType.equals("N"))
				{
					if (gdv.getColomnName().equals("ticket_type") || gdv.getColomnName().equals("n_series"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
					}
				}
				else if (seriesType.equals("D"))
				{
					if (gdv.getColomnName().equals("deptName") || gdv.getColomnName().equals("prefix") || gdv.getColomnName().equals("d_series"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
					}
				}
				ticketConfigColumns.add(obj);
			}
		}
	}

	// Method for getting the field Name for feedback Draft Module
	public void setSurveyTags(int flag)
	{
		// flag value is getting the employee level data based on the selected
		// no of employee configuration
		ConfigurationUtilBean obj;
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		if (flag > 0)
		{
			List<GridDataPropertyView> surveyCategoryList = Configuration.getConfigurationData("mapped_survey_category_configuration", accountID, connectionSpace, "", 0, "table_name", "survey_category_configuration");
			surveyCategoryColumns = new ArrayList<ConfigurationUtilBean>();
			if (surveyCategoryList != null && surveyCategoryList.size() > 0)
			{
				for (GridDataPropertyView gdv : surveyCategoryList)
				{
					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("T"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						surveyCategoryColumns.add(obj);
					}
				}
			}
		}
		if (flag > 1)
		{
			List<GridDataPropertyView> surveyEventList = Configuration.getConfigurationData("mapped_survey_event_configuration", accountID, connectionSpace, "", 0, "table_name", "survey_event_configuration");
			surveyEventColumns = new ArrayList<ConfigurationUtilBean>();
			if (surveyEventList != null && surveyEventList.size() > 0)
			{
				for (GridDataPropertyView gdv : surveyEventList)
				{
					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("T"))
					{
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						if (gdv.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
						else
						{
							obj.setMandatory(false);
						}
						surveyEventColumns.add(obj);
					}
				}
			}
		}
	}

	public void setsubDept_DeptTags(String level)
	{
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					subDept_DeptLevelName.add(obj);
				}
			}
		}

		if (level.equals("2"))
		{
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0)
			{
				for (GridDataPropertyView gdv1 : subdept_deptList)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname"))
					{
						obj.setKey(gdv1.getColomnName());
						obj.setValue(gdv1.getHeadingName());
						obj.setValidationType(gdv1.getValidationType());
						obj.setColType("D");
						obj.setMandatory(true);
						subDept_DeptLevelName.add(obj);
					}

				}
			}
		}
	}

	public void setShiftTags()
	{
		List<GridDataPropertyView> shiftList = Configuration.getConfigurationData("mapped_shift_configuration", accountID, connectionSpace, "", 0, "table_name", "shift_configuration");
		shiftTextColumns = new ArrayList<ConfigurationUtilBean>();
		shiftTimeColumns = new ArrayList<ConfigurationUtilBean>();
		ConfigurationUtilBean obj;
		if (shiftList != null && shiftList.size() > 0)
		{
			for (GridDataPropertyView gdv : shiftList)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColType().trim().equalsIgnoreCase("T"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					shiftTextColumns.add(obj);
				}
				else if (gdv.getColType().trim().equalsIgnoreCase("Time"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					shiftTimeColumns.add(obj);
				}
			}
		}
	}

	public void setFeedLodgeTags()
	{
		ConfigurationUtilBean obj;
		List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
		complanantTextColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDDColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDTimeColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackTextColumns = new ArrayList<ConfigurationUtilBean>();
		if (feedLodgeTags != null && feedLodgeTags.size() > 0)
		{
			for (GridDataPropertyView gdv : feedLodgeTags)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equals("uniqueid") || gdv.getColomnName().equals("feed_by") || gdv.getColomnName().equals("feed_by_mobno") || gdv.getColomnName().equals("feed_by_emailid"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					complanantTextColumns.add(obj);
				}
				else if (gdv.getColomnName().equals("feedType") || gdv.getColomnName().equals("category"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					feedbackDDColumns.add(obj);
				}
				else if (gdv.getColomnName().equals("subCatg") || gdv.getColomnName().equals("resolutionTime"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					feedbackDTimeColumns.add(obj);
				}
				else if (gdv.getColomnName().equals("feed_brief") || gdv.getColomnName().equals("location"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					feedbackTextColumns.add(obj);
				}
			}
		}
	}

	// Get Service Department List
	@SuppressWarnings("unchecked")
	public List getServiceDept_SubDept(String deptLevel, String orgLevel, String orgId, SessionFactory connection)
	{
		boolean flag = new HelpdeskUniversalHelper().checkTable("feedback_type", connection);
		List dept_subdeptList = null;
		String query = "";
		try
		{
			if (deptLevel.equals("2"))
			{
				query = "select id, deptName from department where orgnztnlevel='" + orgLevel + "' and mappedOrgnztnId='" + orgId + "' and id in (select distinct(deptid) from subdepartment where id in (select distinct(dept_subdept) from feedback_type where deptHierarchy='" + deptLevel + "')) ORDER BY deptName ASC;";
			}
			else if (deptLevel.equals("1"))
			{
				query = "select id, deptName from department where orgnztnlevel='" + orgLevel + "' and mappedOrgnztnId='" + orgId + "' and id in (select distinct(deptid) from subdepartment where id in (select distinct(dept_subdept) from feedback_type where deptHierarchy='" + deptLevel + "')) ORDER BY deptName ASC;";
			}
			if (flag)
			{
				Session session = null;
				Transaction transaction = null;
				session = connection.getCurrentSession();
				transaction = session.beginTransaction();
				dept_subdeptList = session.createSQLQuery(query).list();
				transaction.commit();
			}

		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dept_subdeptList;
	}

	@SuppressWarnings("unchecked")
	public List getReportData(String table, String searchfield, String searchfieldvalue, String orderfield, String order)
	{
		List SubdeptallList = new ArrayList();
		try
		{
			String subdeptall = "select * from " + table + " where " + searchfield + "='" + searchfieldvalue + "' ORDER BY " + orderfield + " " + order + "";
			SubdeptallList = new createTable().executeAllSelectQuery(subdeptall, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SubdeptallList;
	}

	public String checkMethod()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List getLodgedTickets(String dept, String status_for, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (status_for.equals("H"))
			{
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where status not in ('Resolved','Ignore') and  to_dept_subdept in (select id from subdepartment where deptid=" + dept + ")");
			}
			else if (status_for.equals("M"))
			{
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where status not in ('Resolved','Ignore')");
			}
			// System.out.println("Query  is   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getLevelTickets(String dept, String status_for, String level, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (status_for.equals("H"))
			{
				query.append("select status, count(*) from feedback_status where level='" + level + "'  and status not in ('Ignore') and  to_dept_subdept in (select id from subdepartment where deptid='" + dept + "') group by status ");
			}
			else if (status_for.equals("M"))
			{
				query.append("select status, count(*) from feedback_status where level='" + level + "'  and status not in ('Ignore') group by status ");
			}
			// System.out.println("Level Tickets Detail  :"+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getLevelDetailInGraph(String dept, String status_for, SessionFactory connectionSpace)
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			if (status_for.equals("H"))
			{
				query.append("select level, count(*) from feedback_status where  open_date='" + DateUtil.getCurrentDateUSFormat() + "'  and status not in ('Ignore') and  to_dept_subdept in (select id from subdepartment where deptid='" + dept + "') group by level ");
			}
			else if (status_for.equals("M"))
			{
				query.append("select level, count(*) from feedback_status where  open_date='" + DateUtil.getCurrentDateUSFormat() + "'  and status not in ('Ignore') group by level ");
			}
			// System.out.println("Level Tickets Detail  :"+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackValues(List feedValue, String deptLevel, String feedStatus)
	{
		// System.out.println("Setting Feedback Data Values ::::");
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if (feedValue != null && feedValue.size() > 0)
		{
			if (true)
			{
				for (Iterator iterator = feedValue.iterator(); iterator.hasNext();)
				{
					Object[] obdata = (Object[]) iterator.next();
					FeedbackPojo fbp = new FeedbackPojo();
					fbp.setId((Integer) obdata[0]);
					fbp.setTicket_no(obdata[1].toString());
					fbp.setFeedback_by_dept(obdata[2].toString());
					if (obdata[3] != null && !obdata[3].toString().equals(""))
					{
						fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
					}
					else
					{
						fbp.setFeed_by("NA");
					}

					if (obdata[4] != null)
					{
						fbp.setFeedback_by_mobno(obdata[4].toString());
					}
					else
					{
						fbp.setFeedback_by_mobno("NA");
					}

					if (obdata[5] != null && !obdata[5].toString().equals(""))
					{
						fbp.setFeedback_by_emailid(obdata[5].toString());
					}
					else
					{
						fbp.setFeedback_by_emailid("NA");
					}

					fbp.setFeedback_to_dept(obdata[6].toString());
					fbp.setFeedback_allot_to(obdata[7].toString());
					fbp.setFeedtype(obdata[8].toString());
					fbp.setFeedback_catg(obdata[9].toString());
					fbp.setFeedback_subcatg(obdata[10].toString());
					if (obdata[11] != null && !obdata[11].toString().equals(""))
					{
						fbp.setFeed_brief(obdata[11].toString());
					}
					else
					{
						fbp.setFeed_brief("NA");
					}

					fbp.setFeedaddressing_time(obdata[12].toString().substring(0, 5));

					if (obdata[12] != null && !obdata[12].toString().equals("") && obdata[15] != null && !obdata[15].toString().equals("") && obdata[16] != null && !obdata[16].toString().equals(""))
					{
						String addr_date_time = DateUtil.newdate_AfterTime(obdata[15].toString(), obdata[16].toString(), obdata[12].toString());
						String[] add_date_time = addr_date_time.split("#");
						for (int i = 0; i < add_date_time.length; i++)
						{
							fbp.setFeedaddressing_date(DateUtil.convertDateToIndianFormat(add_date_time[0]));
							fbp.setFeedaddressing_time(add_date_time[1].substring(0, 5));
						}
					}
					else
					{
						fbp.setFeedaddressing_date("NA");
						fbp.setFeedaddressing_time("NA");
					}

					if (obdata[14] != null && !obdata[14].toString().equals(""))
					{
						fbp.setLocation(obdata[14].toString());
					}
					else
					{
						fbp.setLocation("NA");
					}
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[15].toString()));
					fbp.setOpen_time(obdata[16].toString().substring(0, 5));
					if (feedStatus != null && feedStatus.equalsIgnoreCase("Pending"))
					{
						if (obdata[17] != null)
						{
							fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[17].toString()));
						}
						else
						{
							fbp.setEscalation_date(DateUtil.convertDateToIndianFormat("NA"));
						}
						if (obdata[18] != null && !obdata[18].toString().equals("") && !obdata[18].toString().equals("NA"))
						{
							fbp.setEscalation_time(obdata[18].toString());
						}
						else
						{
							fbp.setEscalation_time("NA");
						}
					}

					fbp.setLevel(obdata[19].toString());
					fbp.setStatus(obdata[20].toString());
					if (obdata[21] != null && !obdata[21].toString().equals(""))
					{
						fbp.setVia_from(DateUtil.makeTitle(obdata[21].toString()));
					}
					else
					{
						fbp.setVia_from("NA");
					}

					fbp.setFeed_registerby((obdata[22].toString()));

					if (feedStatus != null && feedStatus.equalsIgnoreCase("High Priority"))
					{
						fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[23].toString()));
						fbp.setHp_time(obdata[24].toString().substring(0, 5));
						fbp.setHp_reason(obdata[25].toString());
					}

					if (feedStatus != null && feedStatus.equalsIgnoreCase("Snooze"))
					{
						if (obdata[26] != null && !obdata[26].toString().equals(""))
						{
							fbp.setSn_reason(obdata[26].toString());
						}
						else
						{
							fbp.setSn_reason("NA");
						}

						if (obdata[27] != null && !obdata[27].toString().equals(""))
						{
							fbp.setSn_on_date(obdata[27].toString());
						}
						else
						{
							fbp.setSn_on_date("NA");
						}

						if (obdata[28] != null && !obdata[28].toString().equals(""))
						{
							fbp.setSn_on_time(obdata[28].toString());
						}
						else
						{
							fbp.setSn_on_time("NA");
						}

						if (obdata[29] != null && !obdata[29].toString().equals(""))
						{
							fbp.setSn_date(DateUtil.convertDateToIndianFormat(obdata[29].toString()));
						}
						else
						{
							fbp.setSn_date("NA");
						}

						if (obdata[30] != null && !obdata[30].toString().equals(""))
						{
							fbp.setSn_time(obdata[30].toString().substring(0, 5));
						}
						else
						{
							fbp.setSn_time("NA");
						}

						if (obdata[31] != null && !obdata[31].toString().equals(""))
						{
							fbp.setSn_duration(obdata[31].toString());
						}
						else
						{
							fbp.setSn_duration("NA");
						}
					}

					if (feedStatus != null && feedStatus.equalsIgnoreCase("Ignore"))
					{
						if (obdata[32] != null)
						{
							fbp.setIg_date(DateUtil.convertDateToIndianFormat(obdata[32].toString()));
						}

						if (obdata[33] != null)
						{
							fbp.setIg_time(obdata[33].toString().substring(0, 5));
						}

						if (obdata[34] != null)
						{
							fbp.setIg_reason(DateUtil.makeTitle(obdata[34].toString()));
						}
					}

					if (feedStatus != null && feedStatus.equalsIgnoreCase("Transfer"))
					{
						if (obdata[35] != null)
						{
							fbp.setTransfer_date(DateUtil.convertDateToIndianFormat(obdata[35].toString()));
						}

						if (obdata[36] != null)
						{
							fbp.setTransfer_time(obdata[36].toString().substring(0, 5));
						}

						if (obdata[37] != null)
						{
							fbp.setTransfer_reason(DateUtil.makeTitle(obdata[37].toString()));
						}
					}

					if (obdata[38] != null)
					{
						fbp.setAction_by(DateUtil.makeTitle(obdata[38].toString()));
					}

					if (obdata[39] != null)
					{
						fbp.setFeedback_remarks(obdata[39].toString());
					}
					else
					{
						fbp.setFeedback_remarks("NA");
					}

					if (obdata[40] != null)
					{
						fbp.setCr_no(obdata[40].toString());
					}
					else
					{
						fbp.setCr_no("NA");
					}

					if (obdata[41] != null)
					{
						fbp.setPatMobNo(obdata[41].toString());
					}
					else
					{
						fbp.setPatMobNo("NA");
					}

					if (obdata[42] != null)
					{
						fbp.setPatEmailId(obdata[42].toString());
					}
					else
					{
						fbp.setPatEmailId("NA");
					}

					if (feedStatus != null && (feedStatus.equalsIgnoreCase("Resolved") || feedStatus.equalsIgnoreCase("Noted")))
					{
						if (obdata[43] != null && !obdata[43].toString().equals(""))
						{
							fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[43].toString()));
						}
						else
						{
							fbp.setResolve_date("NA");
						}

						if (obdata[44] != null && !obdata[44].toString().equals(""))
						{
							fbp.setResolve_time(obdata[44].toString().substring(0, 5));
						}
						else
						{
							fbp.setResolve_time("NA");
						}

						if (obdata[45] != null && !obdata[45].equals(""))
						{
							fbp.setResolve_duration(obdata[45].toString());
						}
						else
						{
							if (obdata[15] != null && !obdata[15].toString().equals("") && obdata[16] != null && !obdata[16].toString().equals("") && obdata[43] != null && !obdata[43].toString().equals("") && obdata[44] != null && !obdata[44].toString().equals(""))
							{
								String dura1 = DateUtil.time_difference(obdata[15].toString(), obdata[16].toString(), obdata[43].toString(), obdata[44].toString());
								fbp.setResolve_duration(dura1);
							}
							else
							{
								fbp.setResolve_duration("NA");
							}
						}

						if (obdata[46] != null && !obdata[46].toString().equals(""))
						{
							fbp.setResolve_remark(obdata[46].toString());
						}
						else
						{
							fbp.setResolve_remark("NA");
						}
						fbp.setResolve_by(obdata[47].toString());

						if (feedStatus.equalsIgnoreCase("Resolved"))
						{
							fbp.setSpare_used(obdata[48].toString());

							if (obdata[48] != null && !obdata[48].toString().equals(""))
							{
								fbp.setActionComments(obdata[48].toString());
							}
						}

					}
					feedList.add(fbp);
				}
			}
		}
		return feedList;
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getAccountID()
	{
		return accountID;
	}

	public void setAccountID(String accountID)
	{
		this.accountID = accountID;
	}

	public String getFeedTypeId()
	{
		return feedTypeId;
	}

	public void setFeedTypeId(String feedTypeId)
	{
		this.feedTypeId = feedTypeId;
	}

	public String getSubDept()
	{
		return subDept;
	}

	public void setSubDept(String subDept)
	{
		this.subDept = subDept;
	}

	public Map<Integer, String> getFeedTypelist()
	{
		return feedTypelist;
	}

	public void setFeedTypelist(Map<Integer, String> feedTypelist)
	{
		this.feedTypelist = feedTypelist;
	}

	public Map<Integer, String> getFeedCategorylist()
	{
		return feedCategorylist;
	}

	public void setFeedCategorylist(Map<Integer, String> feedCategorylist)
	{
		this.feedCategorylist = feedCategorylist;
	}

	public Map<Integer, String> getShiftlist()
	{
		return shiftlist;
	}

	public void setShiftlist(Map<Integer, String> shiftlist)
	{
		this.shiftlist = shiftlist;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public Map<Integer, String> getFeedSubCategorylist()
	{
		return feedSubCategorylist;
	}

	public void setFeedSubCategorylist(Map<Integer, String> feedSubCategorylist)
	{
		this.feedSubCategorylist = feedSubCategorylist;
	}

	public String getSubcatg()
	{
		return subcatg;
	}

	public void setSubcatg(String subcatg)
	{
		this.subcatg = subcatg;
	}

	public FeedbackDraftPojo getFeedDraft()
	{
		return feedDraft;
	}

	public void setFeedDraft(FeedbackDraftPojo feedDraft)
	{
		this.feedDraft = feedDraft;
	}

	public String getDeptHierarchy()
	{
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy)
	{
		this.deptHierarchy = deptHierarchy;
	}

	public String getDeptOrSubDeptId()
	{
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId)
	{
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public String getFeedbackDarft()
	{
		return feedbackDarft;
	}

	public void setFeedbackDarft(String feedbackDarft)
	{
		this.feedbackDarft = feedbackDarft;
	}

	public int getLevelOfFeedDraft()
	{
		return levelOfFeedDraft;
	}

	public void setLevelOfFeedDraft(int levelOfFeedDraft)
	{
		this.levelOfFeedDraft = levelOfFeedDraft;
	}

	public String getFeedLevel1()
	{
		return feedLevel1;
	}

	public void setFeedLevel1(String feedLevel1)
	{
		this.feedLevel1 = feedLevel1;
	}

	public String getFeedLevel2()
	{
		return feedLevel2;
	}

	public void setFeedLevel2(String feedLevel2)
	{
		this.feedLevel2 = feedLevel2;
	}

	public String getFeedLevel3()
	{
		return feedLevel3;
	}

	public void setFeedLevel3(String feedLevel3)
	{
		this.feedLevel3 = feedLevel3;
	}

	public String getShiftConf()
	{
		return shiftConf;
	}

	public void setShiftConf(String shiftConf)
	{
		this.shiftConf = shiftConf;
	}

	public String getLodgeFeedback()
	{
		return lodgeFeedback;
	}

	public void setLodgeFeedback(String lodgeFeedback)
	{
		this.lodgeFeedback = lodgeFeedback;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getShiftLevel()
	{
		return shiftLevel;
	}

	public void setShiftLevel(String shiftLevel)
	{
		this.shiftLevel = shiftLevel;
	}

	public String getFeedDarftLevelName()
	{
		return feedDarftLevelName;
	}

	public void setFeedDarftLevelName(String feedDarftLevelName)
	{
		this.feedDarftLevelName = feedDarftLevelName;
	}

	public String getFeedDarftCategoryLevelName()
	{
		return feedDarftCategoryLevelName;
	}

	public void setFeedDarftCategoryLevelName(String feedDarftCategoryLevelName)
	{
		this.feedDarftCategoryLevelName = feedDarftCategoryLevelName;
	}

	public Map<String, String> getFdSubCategoryLevelName()
	{
		return fdSubCategoryLevelName;
	}

	public void setFdSubCategoryLevelName(Map<String, String> fdSubCategoryLevelName)
	{
		this.fdSubCategoryLevelName = fdSubCategoryLevelName;
	}

	public Map<String, String> getFdSubCategoryLevelNameForDate()
	{
		return fdSubCategoryLevelNameForDate;
	}

	public void setFdSubCategoryLevelNameForDate(Map<String, String> fdSubCategoryLevelNameForDate)
	{
		this.fdSubCategoryLevelNameForDate = fdSubCategoryLevelNameForDate;
	}

	public Map<String, String> getShiftLevelName()
	{
		return shiftLevelName;
	}

	public void setShiftLevelName(Map<String, String> shiftLevelName)
	{
		this.shiftLevelName = shiftLevelName;
	}

	public Map<String, String> getShiftLevelNameForDate()
	{
		return shiftLevelNameForDate;
	}

	public void setShiftLevelNameForDate(Map<String, String> shiftLevelNameForDate)
	{
		this.shiftLevelNameForDate = shiftLevelNameForDate;
	}

	public List<ConfigurationUtilBean> getFeedbackTypeColumns()
	{
		return feedbackTypeColumns;
	}

	public void setFeedbackTypeColumns(List<ConfigurationUtilBean> feedbackTypeColumns)
	{
		this.feedbackTypeColumns = feedbackTypeColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackCategoryColumns()
	{
		return feedbackCategoryColumns;
	}

	public void setFeedbackCategoryColumns(List<ConfigurationUtilBean> feedbackCategoryColumns)
	{
		this.feedbackCategoryColumns = feedbackCategoryColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryDDColumns()
	{
		return feedbackSubCategoryDDColumns;
	}

	public void setFeedbackSubCategoryDDColumns(List<ConfigurationUtilBean> feedbackSubCategoryDDColumns)
	{
		this.feedbackSubCategoryDDColumns = feedbackSubCategoryDDColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryTextColumns()
	{
		return feedbackSubCategoryTextColumns;
	}

	public void setFeedbackSubCategoryTextColumns(List<ConfigurationUtilBean> feedbackSubCategoryTextColumns)
	{
		this.feedbackSubCategoryTextColumns = feedbackSubCategoryTextColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryTimeColumns()
	{
		return feedbackSubCategoryTimeColumns;
	}

	public void setFeedbackSubCategoryTimeColumns(List<ConfigurationUtilBean> feedbackSubCategoryTimeColumns)
	{
		this.feedbackSubCategoryTimeColumns = feedbackSubCategoryTimeColumns;
	}

	public List<ConfigurationUtilBean> getShiftTextColumns()
	{
		return shiftTextColumns;
	}

	public void setShiftTextColumns(List<ConfigurationUtilBean> shiftTextColumns)
	{
		this.shiftTextColumns = shiftTextColumns;
	}

	public List<ConfigurationUtilBean> getShiftTimeColumns()
	{
		return shiftTimeColumns;
	}

	public void setShiftTimeColumns(List<ConfigurationUtilBean> shiftTimeColumns)
	{
		this.shiftTimeColumns = shiftTimeColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackDDColumns()
	{
		return feedbackDDColumns;
	}

	public void setFeedbackDDColumns(List<ConfigurationUtilBean> feedbackDDColumns)
	{
		this.feedbackDDColumns = feedbackDDColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackDTimeColumns()
	{
		return feedbackDTimeColumns;
	}

	public void setFeedbackDTimeColumns(List<ConfigurationUtilBean> feedbackDTimeColumns)
	{
		this.feedbackDTimeColumns = feedbackDTimeColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackTextColumns()
	{
		return feedbackTextColumns;
	}

	public void setFeedbackTextColumns(List<ConfigurationUtilBean> feedbackTextColumns)
	{
		this.feedbackTextColumns = feedbackTextColumns;
	}

	public String getPageHeader()
	{
		return pageHeader;
	}

	public void setPageHeader(String pageHeader)
	{
		this.pageHeader = pageHeader;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public boolean isCheckdept()
	{
		return checkdept;
	}

	public void setCheckdept(boolean checkdept)
	{
		this.checkdept = checkdept;
	}

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public Map<Integer, String> getSubDeptList()
	{
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList)
	{
		this.subDeptList = subDeptList;
	}

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(List<ConfigurationUtilBean> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public String getSurvey()
	{
		return survey;
	}

	public void setSurvey(String survey)
	{
		this.survey = survey;
	}

	public int getLevelOfSurvey()
	{
		return levelOfSurvey;
	}

	public void setLevelOfSurvey(int levelOfSurvey)
	{
		this.levelOfSurvey = levelOfSurvey;
	}

	public String getSurveyLevel1()
	{
		return surveyLevel1;
	}

	public void setSurveyLevel1(String surveyLevel1)
	{
		this.surveyLevel1 = surveyLevel1;
	}

	public String getSurveyLevel2()
	{
		return surveyLevel2;
	}

	public void setSurveyLevel2(String surveyLevel2)
	{
		this.surveyLevel2 = surveyLevel2;
	}

	public List<ConfigurationUtilBean> getSurveyEventColumns()
	{
		return surveyEventColumns;
	}

	public void setSurveyEventColumns(List<ConfigurationUtilBean> surveyEventColumns)
	{
		this.surveyEventColumns = surveyEventColumns;
	}

	public List<ConfigurationUtilBean> getSurveyCategoryColumns()
	{
		return surveyCategoryColumns;
	}

	public void setSurveyCategoryColumns(List<ConfigurationUtilBean> surveyCategoryColumns)
	{
		this.surveyCategoryColumns = surveyCategoryColumns;
	}

	public String getRoasterDownload()
	{
		return roasterDownload;
	}

	public void setRoasterDownload(String roasterDownload)
	{
		this.roasterDownload = roasterDownload;
	}

	public String getFeedDarftDownload()
	{
		return feedDarftDownload;
	}

	public void setFeedDarftDownload(String feedDarftDownload)
	{
		this.feedDarftDownload = feedDarftDownload;
	}

	public String getDeptName()
	{
		return DeptName;
	}

	public void setDeptName(String deptName)
	{
		DeptName = deptName;
	}

	public Map<Integer, String> getDownloadSubDeptList()
	{
		return downloadSubDeptList;
	}

	public void setDownloadSubDeptList(Map<Integer, String> downloadSubDeptList)
	{
		this.downloadSubDeptList = downloadSubDeptList;
	}

	public String getDaily_report()
	{
		return daily_report;
	}

	public void setDaily_report(String daily_report)
	{
		this.daily_report = daily_report;
	}

	public List<ConfigurationUtilBean> getComplanantTextColumns()
	{
		return complanantTextColumns;
	}

	public void setComplanantTextColumns(List<ConfigurationUtilBean> complanantTextColumns)
	{
		this.complanantTextColumns = complanantTextColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigDDColumns()
	{
		return reportconfigDDColumns;
	}

	public void setReportconfigDDColumns(List<ConfigurationUtilBean> reportconfigDDColumns)
	{
		this.reportconfigDDColumns = reportconfigDDColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigDTColumns()
	{
		return reportconfigDTColumns;
	}

	public void setReportconfigDTColumns(List<ConfigurationUtilBean> reportconfigDTColumns)
	{
		this.reportconfigDTColumns = reportconfigDTColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigTimeColumns()
	{
		return reportconfigTimeColumns;
	}

	public void setReportconfigTimeColumns(List<ConfigurationUtilBean> reportconfigTimeColumns)
	{
		this.reportconfigTimeColumns = reportconfigTimeColumns;
	}

	public String getDept_subdept()
	{
		return dept_subdept;
	}

	public void setDept_subdept(String dept_subdept)
	{
		this.dept_subdept = dept_subdept;
	}

	public Map<Integer, String> getEmpList()
	{
		return empList;
	}

	public void setEmpList(Map<Integer, String> empList)
	{
		this.empList = empList;
	}

	public String getAddressTime()
	{
		return addressTime;
	}

	public void setAddressTime(String addressTime)
	{
		this.addressTime = addressTime;
	}

	public String getEscTime()
	{
		return escTime;
	}

	public void setEscTime(String escTime)
	{
		this.escTime = escTime;
	}

	public String getReport()
	{
		return report;
	}

	public void setReport(String report)
	{
		this.report = report;
	}

	public List<ConfigurationUtilBean> getTicketConfigColumns()
	{
		return ticketConfigColumns;
	}

	public void setTicketConfigColumns(List<ConfigurationUtilBean> ticketConfigColumns)
	{
		this.ticketConfigColumns = ticketConfigColumns;
	}

	public String getTicketflag()
	{
		return ticketflag;
	}

	public void setTicketflag(String ticketflag)
	{
		this.ticketflag = ticketflag;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public boolean isSuccessStatus()
	{
		return successStatus;
	}

	public void setSuccessStatus(boolean successStatus)
	{
		this.successStatus = successStatus;
	}

	public boolean isAccordianItem()
	{
		return accordianItem;
	}

	public void setAccordianItem(boolean accordianItem)
	{
		this.accordianItem = accordianItem;
	}

	public String getFeedFor1()
	{
		return feedFor1;
	}

	public void setFeedFor1(String feedFor1)
	{
		this.feedFor1 = feedFor1;
	}

	public String getFeedFor2()
	{
		return feedFor2;
	}

	public void setFeedFor2(String feedFor2)
	{
		this.feedFor2 = feedFor2;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getFrom_page()
	{
		return from_page;
	}

	public void setFrom_page(String from_page)
	{
		this.from_page = from_page;
	}

	public String getListvalue()
	{
		return listvalue;
	}

	public void setListvalue(String listvalue)
	{
		this.listvalue = listvalue;
	}

	public static int getAN()
	{
		return AN;
	}

	public static void setAN(int aN)
	{
		AN = aN;
	}
}