/**
 * 
 */
package com.Over2Cloud.ctrl.feedback.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.OpenTicketsForDept;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.feedbackaction.ActionOnFeedback;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Avinash
 * 
 */
public class ActivityBoard extends ActionSupport
{
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static FeedbackUniversalAction FUA = new FeedbackUniversalAction();

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
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private List<ActivityPojo> masterViewList;

	private String feedDataId;
	private CustomerPojo patientPojo;
	private Map<String, String> actionNameMap;
	private String feedByDept;
	boolean checkdept = false;
	private String feedbackDarft;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;
	private Map<Integer, String> serviceSubDeptList = null;
	private int levelOfFeedDraft = 1;
	private String feedLevel1;
	private String feedLevel2;
	private String feedLevel3;

	private Map<String, String> feedbackCategoryColumns = null;
	private Map<String, String> feedbackSubCategoryDDColumns = null;
	private Map<String, String> feedbackSubCategoryTextColumns = null;
	private Map<String, String> feedbackSubCategoryTimeColumns = null;
	private Map<String, String> feedbackTypeColumns = null;

	private List<ConfigurationUtilBean> complanantTextColumns = null;
	private List<ConfigurationUtilBean> feedbackDDColumns = null;
	private List<ConfigurationUtilBean> feedbackDTimeColumns = null;
	private List<ConfigurationUtilBean> feedbackTextColumns = null;

	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;
	private TreeMap<String, String> department;
	private Map<String, String> subCategory;
	// Serach Fields
	private String fromDate;
	private String toDate;
	private String status;
	private String patType;
	private String feedBack;
	private String mode;
	private String ticket_no;
	private String feedStatus;
	private Map<String, String> categary;
	private String wildsearch;
	private ActivityPojo pojo;
	private String subCat;
	private Map<String, String> ticketNoMap;
	private Map<String, String> feedByMap;
	private String feedBy;
	private String dept;
	private String catg;
	private String clientId;
	private Map<String, String> ticketStatus;
	private Map<String, String> dataCountMap;
	private String activityFlag;
	private String feedStatId;

	public String beforeActivityTakeAction()
	{
	//	System.out.println("Feedstat Id is as >>>>>>>>>>" + getFeedStatId());
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();

				pojo = new ActivityPojo();
				pojo.setId(Integer.parseInt(getFeedDataId()));
				pojo = helper.getPatientFullDetails(connectionSpace, getClientId());
				// System.out.println("<<<<<<<<"+getTicket_no()+">>>>>"+pojo.
				// getMode());
				if (getActivityFlag().equalsIgnoreCase("1") && pojo.getMode().equalsIgnoreCase("Paper"))
				{
					// System.out.println("If block");
					pojo = helper.getFullDetailsForOpenTicketBySubCat(connectionSpace, getSubCat(), pojo);
					return "success";
				}
				else
				{
					// For SMS
					pojo = helper.getFullDetailsForOpenTicketBySubCat(connectionSpace, getSubCat(), pojo);
					actionNameMap = new HashMap<String, String>();
					StringBuffer buffer = new StringBuffer("select id,name from actioninfo order by name asc");
					List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								if (object[0] != null && object[1] != null)
								{
									actionNameMap.put(object[0].toString(), object[1].toString());
								}
							}
						}
					}

					// Getting all Dept Names for opening ticket
					String deptHierarchy = (String) session.get("userDeptLevel");
					String orgLevel = (String) session.get("orgnztnlevel");
					String orgId = (String) session.get("mappedOrgnztnId");

					serviceDeptList = new HashMap<Integer, String>();
					serviceDeptList = helper.getServiceDeptList(connectionSpace, deptHierarchy, orgLevel, orgId);
					setServiceSubDeptList(serviceDeptList);

					if (deptHierarchy.equals("2"))
					{
						setsubDept_DeptTags(deptHierarchy);
						checkdept = true;
					}
					else if (deptHierarchy.equals("1"))
					{
						setsubDept_DeptTags(deptHierarchy);
						checkdept = false;
					}
					return "smssuccess";
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String beforeTicketOpen4Activity()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid && getFeedDataId() != null)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String deptHierarchy = (String) session.get("userDeptLevel");
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				Properties configProp = new Properties();
				try
				{
					InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
					configProp.load(in);
					feedByDept = configProp.getProperty("admin");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				deptList = new LinkedHashMap<Integer, String>();
				serviceDeptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();

				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");

				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
				}

				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");

				// Getting Id, Dept Name for Non Service Department
				departmentlist = new OpenTicketsForDept().getDataFromTable("department", colmName, wherClause, order, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());

					}
				}

				if (departmentlist != null) departmentlist.removeAll(departmentlist);

				// Getting Id, Dept Name for Service Department
				departmentlist = new OpenTicketsForDept().getServiceDept_SubDept(deptHierarchy, orgLevel, orgId, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object1[0], object1[1].toString());
					}
				}

				// setServiceSubDeptList(getAllSubDepts());
				setServiceSubDeptList(serviceDeptList);

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
				}

				// Get Lodge Feedback Level and Fields Name
				setFeedLodgeTags();
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public void setFeedLodgeTags()
	{
		Map session = ActionContext.getContext().getSession();
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		String accountID = (String) session.get("accountid");
		List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
		complanantTextColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDDColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackDTimeColumns = new ArrayList<ConfigurationUtilBean>();
		feedbackTextColumns = new ArrayList<ConfigurationUtilBean>();

		if (feedLodgeTags != null && feedLodgeTags.size() > 0)
		{
			for (GridDataPropertyView gdv : feedLodgeTags)
			{
				ConfigurationUtilBean objdata = new ConfigurationUtilBean();
				if (gdv.getColomnName().equals("uniqueid") || gdv.getColomnName().equals("feed_by") || gdv.getColomnName().equals("feed_by_mobno") || gdv.getColomnName().equals("feed_by_emailid"))
				{
					objdata.setKey(gdv.getColomnName());
					objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if (gdv.getMandatroy() == null) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
					complanantTextColumns.add(objdata);
				}
				else if (gdv.getColomnName().equals("feedType") || gdv.getColomnName().equals("category"))
				{
					objdata.setKey(gdv.getColomnName());
					objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if (gdv.getMandatroy() == null) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
					feedbackDDColumns.add(objdata);
				}
				else if (gdv.getColomnName().equals("subCatg") || gdv.getColomnName().equals("resolutionTime"))
				{
					objdata.setKey(gdv.getColomnName());
					objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if (gdv.getMandatroy() == null) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
					feedbackDTimeColumns.add(objdata);
				}
				else if (gdv.getColomnName().equals("feed_brief"))
				{
					objdata.setKey(gdv.getColomnName());
					objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if (gdv.getMandatroy() == null) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
					feedbackTextColumns.add(objdata);
				}
			}
		}
	}

	// Method for getting the field Name for feedback Draft Module
	public void setfeedBackDraftTags(int flag)
	{
		// flag value is getting the employee level data based on the selected
		// no of employee configuration
		Map session = ActionContext.getContext().getSession();
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		String accountID = (String) session.get("accountid");

		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		if (flag > 0)
		{
			List<GridDataPropertyView> feedbackTypeList = Configuration.getConfigurationData("mapped_feedback_type_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_type_configuration");
			feedbackTypeColumns = new LinkedHashMap<String, String>();
			if (feedbackTypeList != null && feedbackTypeList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackTypeList)
				{
					if (gdv.getColType().trim().equalsIgnoreCase("T"))
					{
						feedbackTypeColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					}
				}
			}
		}
		if (flag > 1)
		{
			List<GridDataPropertyView> feedbackCategoryList = Configuration.getConfigurationData("mapped_feedback_category_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_category_configuration");
			feedbackCategoryColumns = new LinkedHashMap<String, String>();
			if (feedbackCategoryList != null && feedbackCategoryList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackCategoryList)
				{
					if (gdv.getColType().trim().equalsIgnoreCase("T") || gdv.getColType().trim().equalsIgnoreCase("D"))
					{
						feedbackCategoryColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					}
				}
			}
		}
		if (flag > 2)
		{
			List<GridDataPropertyView> feedbackSubCategoryList = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_subcategory_configuration");
			feedbackSubCategoryDDColumns = new LinkedHashMap<String, String>();
			feedbackSubCategoryTextColumns = new LinkedHashMap<String, String>();
			feedbackSubCategoryTimeColumns = new LinkedHashMap<String, String>();
			if (feedbackSubCategoryList != null && feedbackSubCategoryList.size() > 0)
			{
				for (GridDataPropertyView gdv : feedbackSubCategoryList)
				{
					if (gdv.getColType().trim().equalsIgnoreCase("D"))
					{
						feedbackSubCategoryDDColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					}
					else if (gdv.getColType().trim().equalsIgnoreCase("T"))
					{
						feedbackSubCategoryTextColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					}
					else if (gdv.getColType().trim().equalsIgnoreCase("Time"))
					{
						feedbackSubCategoryTimeColumns.put(gdv.getColomnName(), gdv.getHeadingName());
					}
				}
			}
		}
	}

	public void setsubDept_DeptTags(String level)
	{
		Map session = ActionContext.getContext().getSession();
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		String accountID = (String) session.get("accountid");
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				ConfigurationUtilBean objdata = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					objdata.setKey(gdv.getColomnName());
					objdata.setValue(gdv.getHeadingName());
					objdata.setColType(gdv.getColType());
					objdata.setValidationType(gdv.getValidationType());
					if (gdv.getMandatroy() == null) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
					else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
					subDept_DeptLevelName.add(objdata);
				}
			}
		}

		if (level.equals("2"))
		{
			List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0)
			{
				for (GridDataPropertyView gdv : subdept_deptList)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdv.getColomnName().equalsIgnoreCase("subdeptname"))
					{
						objdata.setKey(gdv.getColomnName());
						objdata.setValue(gdv.getHeadingName());
						objdata.setColType(gdv.getColType());
						objdata.setValidationType(gdv.getValidationType());
						if (gdv.getMandatroy() == null) objdata.setMandatory(false);
						else if (gdv.getMandatroy().equalsIgnoreCase("0")) objdata.setMandatory(false);
						else if (gdv.getMandatroy().equalsIgnoreCase("1")) objdata.setMandatory(true);
						subDept_DeptLevelName.add(objdata);
					}

				}
			}
		}
	}

	public String getFeedbackDetailsForDeptFeedback()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid && getFeedDataId() != null)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				// System.out.println("Feeddata Id is as "+getFeedDataId());
				patientPojo = new ActivityBoardHelper().getPatientsDetails(getFeedDataId(), connectionSpace);

				actionNameMap = new HashMap<String, String>();
				StringBuffer buffer = new StringBuffer("select id,name from actioninfo order by name asc");
				List data = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								actionNameMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}

				return "success";
			}
			catch (Exception e)
			{
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	@SuppressWarnings("unchecked")
	public String getHeaderPage()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				setFromDate(DateUtil.getCurrentDateUSFormat());
				setToDate(DateUtil.getCurrentDateUSFormat());
				String empId = null;
				String dept_subdept_id = null;
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_subdept_id = object[3].toString();
						empId = object[5].toString();
					}
					FeedbackHelper helper = new FeedbackHelper();

					/*
					 * if(dept_subdept_id!=null) {
					 * totalTickets=helper.getDeptTicketsCounters
					 * ("",dept_subdept_id, empId, connectionSpace); } else {
					 * totalTickets=helper.getDeptTicketsCounters("", "","",
					 * connectionSpace); }
					 */

				}
				ticketNoMap = new HashMap<String, String>();
				List<String> toDeptList = new FeedbackUniversalAction().getLoggedInEmpForDept(empId, dept_subdept_id, "FM", connectionSpace);
				ticketNoMap = new ActionOnFeedback().getTicketNoMap(toDeptList, connectionSpace);
				feedByMap = new HashMap<String, String>();
				feedByMap = new ActionOnFeedback().getDeptFeedBy(toDeptList, connectionSpace);
				department = new TreeMap<String, String>();
				department = new ActivityBoardHelper().getAllServiceDepts(connectionSpace);
				categary = new HashMap<String, String>();
				categary = new ActivityBoardHelper().getAllCategary(connectionSpace);
				subCategory = new HashMap<String, String>();
				subCategory = new ActivityBoardHelper().getAllSubCategory(connectionSpace);
				ticketStatus = new HashMap<String, String>();
				ticketStatus = new ActivityBoardHelper().getAllTicketStatus(connectionSpace);

				return "success";
			}
			catch (Exception e)
			{
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String getCountActivityPage()
	{

		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				String userType = (String) session.get("userTpe");

				String dept_subdept_id = "";
				String loggedEmpId = "";
				String loggedEmpName = "";
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpName = object[0].toString();
						dept_subdept_id = object[3].toString();
						loggedEmpId = object[5].toString();
					}
				}
				FeedbackHelper helper = new FeedbackHelper();
				dataCountMap = helper.getTicketsCounters(getFromDate(), getToDate(), getStatus(), getMode(), getTicket_no(), getDept(), getCatg(), getWildsearch(), getFeedBack(), getFeedBy(), userType, userName, loggedEmpId, dept_subdept_id, getSubCat(), getClientId(), connectionSpace);

			}
			catch (Exception e)
			{
				return "error";
			}
		}
		else
		{
			return "login";
		}

		return "success";

	}

	public String getActivityHeaderPage()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_activity_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_activity_configuration");
				GridDataPropertyView gpv = new GridDataPropertyView();
				/*
				 * gpv=new GridDataPropertyView();
				 * gpv.setColomnName("full_view");
				 * gpv.setHeadingName("Full View"); gpv.setEditable("false");
				 * gpv.setSearch("true"); gpv.setHideOrShow("false");
				 * gpv.setWidth(80); masterViewProp.add(gpv);
				 */

				/*
				 * gpv=new GridDataPropertyView(); gpv.setColomnName("history");
				 * gpv.setHeadingName("History"); gpv.setEditable("false");
				 * gpv.setSearch("true"); gpv.setHideOrShow("false");
				 * gpv.setFormatter("historyView"); gpv.setWidth(80);
				 * masterViewProp.add(gpv);
				 */

				/*
				 * gpv=new GridDataPropertyView();
				 * gpv.setColomnName("take_Action");
				 * gpv.setHeadingName("Actions"); gpv.setEditable("false");
				 * gpv.setSearch("true"); gpv.setHideOrShow("false");
				 * gpv.setFormatter("takeAction"); gpv.setWidth(80);
				 * masterViewProp.add(gpv);
				 */
				gpv = new GridDataPropertyView();
				gpv.setColomnName("history");
				gpv.setHeadingName("History");
				gpv.setFormatter("viewHistory");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				masterViewProp.add(gpv);

				/*
				 * gpv=new GridDataPropertyView();
				 * gpv.setColomnName("clientName");
				 * gpv.setHeadingName("Patient Name"); gpv.setEditable("false");
				 * gpv.setSearch("true"); gpv.setFormatter("fullView");
				 * gpv.setHideOrShow("false"); gpv.setWidth(150);
				 * masterViewProp.add(gpv);
				 */

				for (GridDataPropertyView gdp : returnResult)
				{
					if (gdp.getColomnName().equalsIgnoreCase("openDate") || gdp.getColomnName().equalsIgnoreCase("openTime") )
					{
					}
					else
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ratingFlag");
				gpv.setHeadingName("Rating");
				gpv.setHideOrShow("true");
				gpv.setWidth(50);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("dateTime");
				gpv.setHeadingName("Date of Entry");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("dept");
				gpv.setHeadingName("Department");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(80);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cat");
				gpv.setHeadingName("Category");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("subCat");
				gpv.setHeadingName("Sub Category");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("brief");
				gpv.setHeadingName("Brief");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("stage");
				gpv.setHeadingName("Stage");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setFormatter("viewStage");
				gpv.setHideOrShow("false");
				gpv.setWidth(75);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("fstatus");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("viewStatus");
				gpv.setHideOrShow("false");
				gpv.setWidth(75);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("level");
				gpv.setHeadingName("TAT");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("viewTAT");
				gpv.setHideOrShow("false");
				gpv.setWidth(40);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ticket_no");
				gpv.setHeadingName("Ticket No.");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("reopenedon");
				gpv.setHeadingName("Reopened On");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("true");
				gpv.setWidth(100);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("reopenstatus");
				gpv.setHeadingName("Reopened Status");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("viewReopenStatus");
				gpv.setHideOrShow("true");
				gpv.setWidth(80);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("reOpenLevel");
				gpv.setHeadingName("Reopened TAT");
				gpv.setEditable("false");
				gpv.setFormatter("viewDeptTAT");
				gpv.setSearch("false");
				gpv.setHideOrShow("true");
				gpv.setWidth(80);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("allot_to");
				gpv.setHeadingName("Alloted To");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("allotedInfo");
				gpv.setHideOrShow("false");
				gpv.setWidth(110);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("feedRegBy");
				gpv.setHeadingName("Open By");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setFormatter("openByDetail");
				gpv.setHideOrShow("false");
				gpv.setWidth(110);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("subCatId");
				gpv.setHeadingName("subCatId");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("true");
				gpv.setWidth(10);
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("feedStatId");
				gpv.setHeadingName("feedStatId");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("true");
				gpv.setWidth(10);
				masterViewProp.add(gpv);
				
				if("Resolved".equalsIgnoreCase(getStatus()))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("rca");
					gpv.setHeadingName("RCA");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(80);
					masterViewProp.add(gpv);
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("capa");
					gpv.setHeadingName("CAPA");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(120);
					masterViewProp.add(gpv);
				}

				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	/*
	 * public String getFeedbackDetail() { String returnResult = ERROR; boolean
	 * sessionFlag = ValidateSession.checkSession(); if (sessionFlag) { try {
	 * Map session = ActionContext.getContext().getSession(); String
	 * userName=(String)session.get("uName"); String deptLevel =
	 * (String)session.get("userDeptLevel"); String
	 * userType=(String)session.get("userTpe"); SessionFactory
	 * connectionSpace=(SessionFactory)session.get("connectionSpace");
	 * 
	 * Map<String, List> whereClauseList = new HashMap<String, List>();
	 * Map<String, Object> wherClause = new HashMap<String, Object>(); int count
	 * = 0 ; List data=null; String dept_subdept_id =""; String loggedEmpId="";
	 * String loggedEmpName=""; List empData = new
	 * FeedbackUniversalAction().getEmpDataByUserName
	 * (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel); if
	 * (empData!=null && empData.size()>0) { for (Iterator iterator =
	 * empData.iterator(); iterator.hasNext();) { Object[] object = (Object[])
	 * iterator.next(); loggedEmpName=object[0].toString(); dept_subdept_id =
	 * object[3].toString(); loggedEmpId=object[5].toString(); } }
	 * //System.out.println("CR No Is as "+getCrNo()); if (dept_subdept_id!=null
	 * && !dept_subdept_id.equals("")) { data=new
	 * FeedbackUniversalAction().getFeedbackDetail("feedback_status",
	 * getFeedStatus(),fromDate,toDate,
	 * dept_subdept_id,loggedEmpId,"ticket_no","DESC"
	 * ,searchField,searchString,searchOper,
	 * connectionSpace,getMode(),getFeedType
	 * (),getFeedCat(),getTicket_no(),getFeedBy
	 * (),getCrNo(),userType,loggedEmpName); }
	 * 
	 * int to = (getRows() getPage());
	 * 
	 * @SuppressWarnings("unused") int from = to - getRows(); if (to >
	 * getRecords()) to = getRecords();
	 * 
	 * 
	 * if(data!=null && data.size()>0){ setRecords(data.size());
	 * //feedbackList=new
	 * FeedbackUniversalAction().setFeedbackValues(data,deptLevel
	 * ,getFeedStatus()); setTotal((int) Math.ceil((double) getRecords() /
	 * (double) getRows())); } returnResult = "SUCCESS"; } catch(Exception e) {
	 * addActionError
	 * ("Ooops!!! There is some problem in getting Feedback Data");
	 * e.printStackTrace(); } } else { returnResult=LOGIN; } return
	 * returnResult; }
	 */

	public String getActivityData()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				String userType = (String) session.get("userTpe");

				String dept_subdept_id = "";
				String loggedEmpId = "";
				String loggedEmpName = "";
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpName = object[0].toString();
						dept_subdept_id = object[3].toString();
						loggedEmpId = object[5].toString();
					}
				}

				ActivityBoardHelper helper = new ActivityBoardHelper();
				masterViewList = new ArrayList<ActivityPojo>();

				StringBuilder query = new StringBuilder("select feedback.clientId,feedback.feed_by,feedback.location, " + "feedback.via_from,feedback.open_date,feedback.open_time,feedback.status as fstatus, ");
				query.append("feedback.ticket_no as ticket_no, ");
				query.append("dept2.deptName as todept,emp.empName as allot_to,feedtype.fbType,catg.categoryName, ");
				query.append("subcatg.subCategoryName,feedback.feed_brief,");
				query.append("feedback.level as ropen_tat,subcatg.needEsc,feedback.feed_registerby,feedback.id,subcatg.id as subCatId,feedback.patientId,feedback.stage");
				query.append(" ,feedback.spare_used,feedback.resolve_remark ");
				query.append(" from feedback_status as feedback");
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
				query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				// query.append(
				// " inner join feedback_ticket as feedtkt on feedback.ticket_no=feedtkt.feedTicketNo"
				// );
				query.append(" where feedback.id !='0' and feedback.moduleName='FM'");

				if (userType != null && userType.equalsIgnoreCase("M"))
				{

				}
				else if (userType != null && userType.equalsIgnoreCase("H"))
				{
					List departList = FUA.getLoggedInEmpForDept(loggedEmpId, dept_subdept_id, "FM", connectionSpace);
					if (departList.size() > 0)
					{
						query.append(" and feedback.to_dept_subdept in " + departList.toString().replace("[", "(").replace("]", ")") + "");
					}
				}
				else
				{
					if (userName != null)
					{
						query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
					}
				}
				if (getClientId() != null && !getClientId().equalsIgnoreCase(""))
				{
					query.append(" and feedback.clientId='" + getClientId() + "'");
				}
				else if (getWildsearch() != null && !getWildsearch().equalsIgnoreCase(""))
				{

					query.append(" and feedback.feed_by like '" + getWildsearch() + "%'");
				}
				else
				{

					if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
					{
						String str[] = getFromDate().split("-");
						if (str[0] != null && str[0].length() > 3)
						{
							query.append(" and feedback.open_date between '" + ((getFromDate())) + "' and '" + ((getToDate())) + "'");
						}
						else
						{
							query.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' and '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
						}
					}
					if (getStatus() != null && !getStatus().equalsIgnoreCase("-1"))
					{
						if (getStatus() != null && !getStatus().equalsIgnoreCase("All"))
						{
							query.append(" and feedback.status= '" + getStatus() + "'");
						}
					}
					else
					{
						query.append(" and feedback.status='Pending'");
					}
					/*
					 * if(getPatType()!=null &&
					 * !getPatType().equalsIgnoreCase("-1")) {
					 * query.append(" and compType= '"+getPatType()+" '"); }
					 */
					if (getMode() != null && !getMode().equalsIgnoreCase("-1"))
					{
						query.append(" and feedback.via_from= '" + getMode() + " '");
					}
					if (getTicket_no() != null && !getTicket_no().equalsIgnoreCase("-1"))
					{
						query.append(" and feedback.ticket_no= '" + getTicket_no() + " '");
					}

					if (getFeedBy() != null && !getFeedBy().equalsIgnoreCase("-1"))
					{
						query.append(" and feedback.feed_by= '" + getFeedBy() + " '");
					}

					if (getDept() != null && !getDept().equalsIgnoreCase("-1"))
					{
						query.append(" and feedback.to_dept_subdept='" + getDept() + " '");
					}

					if (getCatg() != null && !getCatg().equalsIgnoreCase("-1"))
					{
						query.append(" and catg.id='" + getCatg() + " '");
					}

					if (getSubCat() != null && !getSubCat().equalsIgnoreCase("-1"))
					{
						query.append(" and subcatg.id='" + getSubCat() + " '");
					}

					if (getFeedBack() != null && !getFeedBack().equalsIgnoreCase("-1"))
					{
						if (getFeedBack().equalsIgnoreCase("Negative"))
						{
							query.append(" and subcatg.needEsc= 'Y'");
						}
						else if (getFeedBack().equalsIgnoreCase("Positive"))
						{
							query.append(" and subcatg.needEsc= 'N'");
						}
					}
					else
					{
						query.append(" and subcatg.needEsc= 'Y'");
						/*int adminDept=helper.getAdminDeptId(connectionSpace);
						if(adminDept!=0 && adminDept>0)
						{
							query.append(" and feedback.to_dept_subdept!='"+adminDept+"'");
						}*/
					}
				}
				//System.out.println("query>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..." + query);
				List feedList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				int count = 1;
				if (feedList != null && feedList.size() > 0)
				{
					for (Iterator iterator = feedList.iterator(); iterator.hasNext();)
					{
						ActivityPojo pojo = new ActivityPojo();
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							pojo.setId(count++);
							if (object[0] != null)
							{
								pojo.setClientId(object[0].toString());
								pojo.setHistory(helper.getFeedbackCount(pojo.getClientId(), connectionSpace));
							}
							else
							{
								pojo.setClientId("NA");
							}
							if (object[1] != null)
							{
								pojo.setClientName(object[1].toString());
							}
							else
							{
								pojo.setClientName("NA");
							}
							if (object[2] != null)
							{
								pojo.setCenterCode(object[2].toString());
							}
							else
							{
								pojo.setCenterCode("NA");
							}
							if (object[3] != null)
							{
								pojo.setMode(object[3].toString());
							}
							else
							{
								pojo.setMode("NA");
							}
							if (object[4] != null && object[5] != null)
							{
								pojo.setDateTime(DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString().substring(0, 5));
							}
							else
							{
								pojo.setDateTime("NA");
							}
							if (object[6] != null)
							{
								pojo.setFstatus(object[6].toString());
							}
							else
							{
								pojo.setFstatus("NA");
							}
							if (object[7] != null)
							{
								pojo.setTicket_no(object[7].toString());
							}
							else
							{
								pojo.setTicket_no("NA");
							}
							if (object[8] != null)
							{
								pojo.setDept(object[8].toString());
							}
							else
							{
								pojo.setDept("NA");
							}
							if (object[9] != null)
							{
								pojo.setAllot_to(object[9].toString());
							}
							else
							{
								pojo.setAllot_to("NA");
							}
							if (object[10] != null)
							{
								pojo.setFeedType(object[10].toString());
							}
							else
							{
								pojo.setFeedType("NA");
							}
							if (object[11] != null)
							{
								pojo.setCat(object[11].toString());
							}
							else
							{
								pojo.setCat("NA");
							}
							if (object[12] != null)
							{
								pojo.setSubCat(object[12].toString());
							}
							else
							{
								pojo.setSubCat("NA");
							}
							if (object[13] != null)
							{
								pojo.setBrief(object[13].toString());
							}
							else
							{
								pojo.setBrief("NA");
							}
							if (object[14] != null)
							{
								pojo.setLevel(object[14].toString());
							}
							else
							{
								pojo.setLevel("NA");
							}
							if (object[15] != null)
							{
								if (object[15].toString().equalsIgnoreCase("Y"))
								{
									pojo.setTargetOn("Negative");
								}
								else
								{
									pojo.setTargetOn("Positive");
								}
							}
							else
							{
								pojo.setTargetOn("Positive");
							}
							if (object[16] != null)
							{
								String name = helper.getEmpNameByUserId(object[16].toString(), connectionSpace);
								if (name != null)
								{
									pojo.setFeedRegBy(name);
								}
								else
								{
									pojo.setFeedRegBy(object[16].toString());
								}
							}
							else
							{
								pojo.setFeedRegBy("NA");
							}
							if (object[17] != null)
							{
								pojo.setFeedStatId(object[17].toString());
								pojo = helper.getFeedbackReopenedDate(pojo, connectionSpace);
							}
							else
							{
								pojo.setFeedStatId("0");
							}
							if (object[18] != null)
							{
								pojo.setSubCatId(object[18].toString());
							}
							else
							{
								pojo.setSubCatId("NA");
							}
							
						//	feedback.patientId
							
							if (object[19] != null)
							{
								pojo.setPatientId(object[19].toString());
							}
							else
							{
								pojo.setPatientId("NA");
							}
							
							if (object[20] != null)
							{
								pojo.setStage(object[20].toString());
							}
							else
							{
								pojo.setStage("NA");
							}
							if("Resolved".equalsIgnoreCase(getStatus()))
							{
								if (object[21] != null)
								{
									pojo.setRca(object[21].toString());
								}
								else
								{
									pojo.setRca("NA");
								}
								if (object[22] != null)
								{
									pojo.setCapa(object[22].toString());
								}
								else
								{
									pojo.setCapa("NA");
								}
							}
							pojo.setRatingFlag("0");
							masterViewList.add(pojo);
						}
					}
				}
				List<ActivityPojo> pojoList = new ArrayList<ActivityPojo>();
				// if (pojo.getMode().equalsIgnoreCase("Paper"))
				{
					pojoList = helper.getActivityDetailsRating(getFeedBack(), getSubCat(), getCatg(), getDept(), getFeedBy(), getTicket_no(), getMode(), getStatus(), getFromDate(), getToDate(), getWildsearch(), getClientId(), userName, dept_subdept_id, loggedEmpId, userType, count, "", connectionSpace);
				}
				masterViewList.addAll(pojoList);
			//	System.out.println("List size is as >>>>>" + masterViewList.size());
				setMasterViewList(getMasterViewList());
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
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

	public List<ActivityPojo> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<ActivityPojo> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getFeedDataId()
	{
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId)
	{
		this.feedDataId = feedDataId;
	}

	public CustomerPojo getPatientPojo()
	{
		return patientPojo;
	}

	public void setPatientPojo(CustomerPojo patientPojo)
	{
		this.patientPojo = patientPojo;
	}

	public Map<String, String> getActionNameMap()
	{
		return actionNameMap;
	}

	public void setActionNameMap(Map<String, String> actionNameMap)
	{
		this.actionNameMap = actionNameMap;
	}

	public String getFeedByDept()
	{
		return feedByDept;
	}

	public void setFeedByDept(String feedByDept)
	{
		this.feedByDept = feedByDept;
	}

	public String getFeedbackDarft()
	{
		return feedbackDarft;
	}

	public void setFeedbackDarft(String feedbackDarft)
	{
		this.feedbackDarft = feedbackDarft;
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

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(List<ConfigurationUtilBean> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public Map<String, String> getFeedbackCategoryColumns()
	{
		return feedbackCategoryColumns;
	}

	public void setFeedbackCategoryColumns(Map<String, String> feedbackCategoryColumns)
	{
		this.feedbackCategoryColumns = feedbackCategoryColumns;
	}

	public Map<String, String> getFeedbackSubCategoryDDColumns()
	{
		return feedbackSubCategoryDDColumns;
	}

	public void setFeedbackSubCategoryDDColumns(Map<String, String> feedbackSubCategoryDDColumns)
	{
		this.feedbackSubCategoryDDColumns = feedbackSubCategoryDDColumns;
	}

	public Map<String, String> getFeedbackSubCategoryTextColumns()
	{
		return feedbackSubCategoryTextColumns;
	}

	public void setFeedbackSubCategoryTextColumns(Map<String, String> feedbackSubCategoryTextColumns)
	{
		this.feedbackSubCategoryTextColumns = feedbackSubCategoryTextColumns;
	}

	public Map<String, String> getFeedbackSubCategoryTimeColumns()
	{
		return feedbackSubCategoryTimeColumns;
	}

	public void setFeedbackSubCategoryTimeColumns(Map<String, String> feedbackSubCategoryTimeColumns)
	{
		this.feedbackSubCategoryTimeColumns = feedbackSubCategoryTimeColumns;
	}

	public Map<String, String> getFeedbackTypeColumns()
	{
		return feedbackTypeColumns;
	}

	public void setFeedbackTypeColumns(Map<String, String> feedbackTypeColumns)
	{
		this.feedbackTypeColumns = feedbackTypeColumns;
	}

	public List<ConfigurationUtilBean> getComplanantTextColumns()
	{
		return complanantTextColumns;
	}

	public void setComplanantTextColumns(List<ConfigurationUtilBean> complanantTextColumns)
	{
		this.complanantTextColumns = complanantTextColumns;
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

	public Map<Integer, String> getServiceSubDeptList()
	{
		return serviceSubDeptList;
	}

	public void setServiceSubDeptList(Map<Integer, String> serviceSubDeptList)
	{
		this.serviceSubDeptList = serviceSubDeptList;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getPatType()
	{
		return patType;
	}

	public void setPatType(String patType)
	{
		this.patType = patType;
	}

	public String getFeedBack()
	{
		return feedBack;
	}

	public void setFeedBack(String feedBack)
	{
		this.feedBack = feedBack;
	}

	public ActivityPojo getPojo()
	{
		return pojo;
	}

	public void setPojo(ActivityPojo pojo)
	{
		this.pojo = pojo;
	}

	public String getSubCat()
	{
		return subCat;
	}

	public void setSubCat(String subCat)
	{
		this.subCat = subCat;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public Map<String, String> getTicketNoMap()
	{
		return ticketNoMap;
	}

	public void setTicketNoMap(Map<String, String> ticketNoMap)
	{
		this.ticketNoMap = ticketNoMap;
	}

	public Map<String, String> getFeedByMap()
	{
		return feedByMap;
	}

	public void setFeedByMap(Map<String, String> feedByMap)
	{
		this.feedByMap = feedByMap;
	}

	public String getFeedBy()
	{
		return feedBy;
	}

	public void setFeedBy(String feedBy)
	{
		this.feedBy = feedBy;
	}

	public TreeMap<String, String> getDepartment()
	{
		return department;
	}

	public void setDepartment(TreeMap<String, String> department)
	{
		this.department = department;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getWildsearch()
	{
		return wildsearch;
	}

	public void setWildsearch(String wildsearch)
	{
		this.wildsearch = wildsearch;
	}

	public Map<String, String> getCategary()
	{
		return categary;
	}

	public void setCategary(Map<String, String> categary)
	{
		this.categary = categary;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public Map<String, String> getTicketStatus()
	{
		return ticketStatus;
	}

	public void setTicketStatus(Map<String, String> ticketStatus)
	{
		this.ticketStatus = ticketStatus;
	}

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}

	public Map<String, String> getSubCategory()
	{
		return subCategory;
	}

	public void setSubCategory(Map<String, String> subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getActivityFlag()
	{
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}

	public String getFeedStatId()
	{
		return feedStatId;
	}

	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}
}
