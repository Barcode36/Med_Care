package com.Over2Cloud.ctrl.helpdesk.feedbackaction;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.helpdesk.activityboard.ActivityBoardAction;
import com.Over2Cloud.ctrl.helpdesk.activityboard.ActivityBoardHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class ActionOnFeedback extends GridPropertyBean
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();

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

	// /////////////////////////
	private String ticketNo = "";
	private String openDate = "";
	private String openTime = "";
	private String emailId = "";
	private String feedBreif = "";
	private String feedId = "", dylevel = "";
	private List cycleList;
	// ////////////////////////

	private String moduleName;

	private String backData;

	// ReAssign variable
	private String feedbackSubCatgory;

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	List<FeedbackPojo> feedbackList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	private Map<Integer, String> resolverList = null;
	private Map<String, String> statusList = null;
	private Map<Integer, String> deptList = null;
	private Map<String, String> dataMap;
	private File approvalDoc;
	private String approvalDocContentType;
	private String approvalDocFileName;
	private String storedDocPath;
	private String approvedBy;
	private String reason, data4;
	private String complaintid;
	private FeedbackPojo fstatus;
	private String resolve_Alert;
	Map<String, String> checkListMap = new LinkedHashMap<String, String>();
	private String orgImgPath;
	private String ignoreremark, hpremark, reopenremark, snoozeremark, reassignreason;

	String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";
	private String reopen;
	private String dataCheck, mgtFlag;

	public String redirectToJSP()
	{
		String returnResult = ERROR;

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList = new LinkedHashMap<String, String>();
				getTicketDetails(feedId);
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

			// System.out.println("########" + query.toString());

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

	public String beforeActionOnFeedback()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("HDM") && feedStatus != null && !feedStatus.equals("") && (feedStatus.equals("Pending") || feedStatus.equals("Snooze") || feedStatus.equals("High Priority")))
				{
					fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					setGridColomnNames();
					returnResult = SUCCESS;
				} else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("HDM") && feedStatus != null && !feedStatus.equals("") && feedStatus.equals("Resolved"))
				{
					fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					setGridColomnNames();
					returnResult = SUCCESS;
				} else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM") && feedStatus != null && !feedStatus.equals("") && (feedStatus.equals("Pending") || feedStatus.equals("Snooze") || feedStatus.equals("High Priority")))
				{
					fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					returnResult = "FMSUCCESS";
				} else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM") && feedStatus != null && !feedStatus.equals("") && feedStatus.equals("Resolved"))
				{
					fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					returnResult = "FMSUCCESS";
				} else if ((dashFor.equals("H") || dashFor.equals("M") || dashFor.equals("N") || dashFor.equals("C") || dashFor.equals("SC")) && mgtFlag == null)
				{
					// fromDate = DateUtil.getNewDate("day", -1,
					// DateUtil.getCurrentDateUSFormat());
					// toDate = DateUtil.getCurrentDateIndianFormat();
					setGridColomnNames();
					returnResult = "dashsuccess";
				}
				if (mgtFlag != null && mgtFlag.equals("MGT"))
				{
					setGridColomnNames();
					returnResult = "dashsuccessMgt";
				}

				if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM"))
				{
					// System.out.println("For FM");
					getColumnNamesFM();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// System.out.println("Return result   "+returnResult);
		return returnResult;
	}

	public void statusList()
	{
		try
		{
			statusList = new LinkedHashMap<String, String>();
			if (feedStatus.equalsIgnoreCase("Pending"))
			{
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("hold", "Seek Approval");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
			} else if (feedStatus.equalsIgnoreCase("Snooze"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("High Priority", "High Priority");
				statusList.put("hold", "Seek Approval");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
			} else if (feedStatus.equalsIgnoreCase("High Priority"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("hold", "Seek Approval");
				statusList.put("Resolved", "Resolved");
				statusList.put("Ignore", "Ignore");
			} else if (feedStatus.equalsIgnoreCase("Ignore"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("hold", "Seek Approval");
				statusList.put("Resolved", "Resolved");
			} else if (feedStatus.equalsIgnoreCase("Resolved"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("hold", "Seek Approval");
				statusList.put("Ignore", "Ignore");
			} else if (feedStatus.equalsIgnoreCase("Hold"))
			{
				statusList.put("Pending", "Pending");
				statusList.put("Snooze", "Snooze");
				statusList.put("High Priority", "High Priority");
				statusList.put("Ignore", "Ignore");
				statusList.put("Resolved", "Resolved");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
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

	public void setGridColomnNames()
	{
		feedbackColumnNames = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFormatter("statusDetail");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_dept");
		gpv.setHeadingName("To Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_subdept");
		gpv.setHeadingName("To Sub Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Brief");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setWidth(100);
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_dept");
		gpv.setHeadingName("By Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Feedback By");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_mobno");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_emailid");
		gpv.setHeadingName("Email Id");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("addressingTime");
		gpv.setHeadingName("Addr Date / Time");
		gpv.setAlign("center");
		gpv.setWidth(150);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_date");
			gpv.setHeadingName("Esc Date / Time");
			gpv.setAlign("center");
			gpv.setWidth(150);
			feedbackColumnNames.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setHideOrShow("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedtype");
		gpv.setHeadingName("Register By Id");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
	}

	public void getColumnNamesFM()
	{
		feedbackColumnNames = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_date");
		gpv.setHeadingName("Response Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_time");
		gpv.setHeadingName("Response Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending") || getFeedStatus().equals("HP"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_date");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_time");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Patient Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setWidth(100);
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Feedback/Complaint");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_remarks");
		gpv.setAlign("center");
		gpv.setHeadingName("Feedback Remarks");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_registerby");
		gpv.setHeadingName("Register By");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_dept");
		gpv.setHeadingName("By Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_mobno");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_emailid");
		gpv.setHeadingName("Email Id");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_dept");
		gpv.setHeadingName("To Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Snooze"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_reason");
			gpv.setHeadingName("Snooze Reason");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_date");
			gpv.setHeadingName("Snooze On");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_time");
			gpv.setHeadingName("Snooze At");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_date");
			gpv.setHeadingName("Snooze Up To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_time");
			gpv.setHeadingName("Snooze Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_duration");
			gpv.setHeadingName("Snooze Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("High Priority"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_date");
			gpv.setHeadingName("HP Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_time");
			gpv.setHeadingName("HP Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_reason");
			gpv.setHeadingName("HP Reason");
			gpv.setWidth(100);
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("Resolved"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Res. Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Resolve By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("CAPA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("RCA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Via From");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		if (!getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action_by");
			gpv.setHeadingName("Action By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}
	}

	public String getFeedbackDetail()
	{
		// System.out.println("Inside getFeedback Data");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();

				feedbackList = new ArrayList<FeedbackPojo>();
				// int count = 0 ;
				List data = new ArrayList();
				String dept_subdept_id = "", userType = "", emp_Name = "";

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						emp_Name = object[0].toString();
						dept_subdept_id = object[3].toString();
						userType = object[7].toString();
					}
				}

				if (dept_subdept_id != null && !dept_subdept_id.equals(""))
				{
					// System.out.println("Inisde if  when dataflag M ");
					if (dataFlag.equals("M"))
					{
						// System.out.println("Inside If");
						toDate = DateUtil.convertDateToUSFormat(toDate);
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
					} else
					{
						// System.out.println("Inside Else When there is no flag");
						toDate = toDate;
						fromDate = fromDate;
					}
					if (backData != null && !backData.equals("") && backData.equalsIgnoreCase("Y"))
					{
						data = HUA.getFeedbackDetail("feedback_status_15072014", getFeedStatus(), fromDate, toDate, dept_subdept_id, "", deptLevel, "feedback.id", "DESC", "HDM", emp_Name, userType, searchField, searchString, searchOper, connectionSpace);
					} else
					{
						data = HUA.getFeedbackDetail("feedback_status", getFeedStatus(), fromDate, toDate, dept_subdept_id, "", deptLevel, "feedback.id", "DESC", "HDM", emp_Name, userType, searchField, searchString, searchOper, connectionSpace);
					}

					// System.out.println("After geting data");
				}
				// System.out.println("Data Size is "+data.size());
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					feedbackList = HUH.setFeedbackValues(data, deptLevel, getFeedStatus());
					// System.out.println("@@@@@ "+feedbackList.size());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				if (orgDetail != null)
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
					address = orgData[1];
					city = orgData[2];
					pincode = orgData[3];
				}
				// System.out.println(orgData + " >>>> " + orgName);
				orgImgPath = new CommonWork().getOrganizationImage((SessionFactory) session.get("connectionSpace"));
				// System.out.println("Org Pic :::::::::::::: " + orgImgPath);
				fstatus = new ActionOnFeedback().getTicketDetail(feedId, "id");
				cycleList = new ActionOnFeedback().getTicketCycleDetail(feedId, "id");

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getTicketDetails Method");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// System.out.println(returnResult+"sdvc nsd vdn");
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getResolverData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				resolverList = new LinkedHashMap<Integer, String>();

				String dept_subdept = "";
				String allot_to_id = "";

				List empdata = new ArrayList<String>();
				List allotto_data = new ArrayList<String>();

				String tolevel = "";
				List feedbackList = HUA.getMultipleColumns("feedback_status_new", "to_dept_subdept", "subCatg", "allot_to", "level", "", "id", feedid, "", "", connectionSpace);
				if (feedbackList != null && feedbackList.size() > 0)
				{
					for (Iterator iterator = feedbackList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_subdept = object[0].toString();
						allot_to_id = object[2].toString();
						tolevel = object[3].toString().substring(5);
					}
					if (allot_to_id != null && !allot_to_id.equals(""))
					{
						allotto_data = HUA.getEmployeeData(allot_to_id, deptLevel);
					}
					boolean flag = false;
					String deptid = HUA.getData("subdepartment", "deptid", "id", dept_subdept);
					empdata = HUA.getEmp4EscInAllDept(deptid, deptLevel, "", tolevel, connectionSpace);
					if (empdata != null && empdata.size() > 0)
					{
						for (Object c : empdata)
						{
							Object[] dataC = (Object[]) c;
							resolverList.put((Integer) dataC[0], dataC[1].toString());
						}
						flag = resolverList.containsKey(Integer.parseInt(allot_to_id));
					}
					if (!flag)
					{
						if (allotto_data != null && allotto_data.size() > 0)
						{
							for (Object c : allotto_data)
							{
								Object[] dataC_new = (Object[]) c;
								resolverList.put((Integer) dataC_new[4], dataC_new[0].toString());
							}
						}
					}
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

	@SuppressWarnings("rawtypes")
	public String updateFeedbackStatus()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (status != null && !status.equals("-1") && !status.equalsIgnoreCase("Hold"))
				{
					String empIdofuser = (String) session.get("empIdofuser");
					HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					MsgMailCommunication MMC = new MsgMailCommunication();
					FeedbackPojo fbp = new FeedbackPojo();
					String duration = "NA";
					String assignTo = null;
					Map<String, Object> wherClause = new HashMap<String, Object>();
					String snDate = "", snTime = "", snUpToDate = "", snUpToTime = "", snDuration = "", escDate = null, escTime = null, moduleName = null, allottosn = null, userName = null;

					StringBuilder query = new StringBuilder();
					query.append("SELECT action_date,action_time,sn_upto_date,sn_upto_time,action_duration,escalation_date,escalation_time,open_date,open_time,action_remark,action_reason,fstatus.allot_to,fstatus.feed_registerby ");
					query.append(" FROM feedback_status_new as fstatus LEFT JOIN feedback_status_history as history on history.feedId=fstatus.id WHERE fstatus.id=" + getFeedid());
					List ticketData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (ticketData != null && ticketData.size() > 0)
					{
						for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && !object[0].toString().equals(""))
							{
								snDate = object[0].toString();
							} else
							{
								snDate = "NA";
							}
							if (object[1] != null && !object[1].toString().equals(""))
							{
								snTime = object[1].toString();
							} else
							{
								snTime = "NA";
							}
							if (object[2] != null && !object[2].toString().equals(""))
							{
								snUpToDate = object[2].toString();
							} else
							{
								snUpToDate = "NA";
							}
							if (object[3] != null && !object[3].toString().equals(""))
							{
								snUpToTime = object[3].toString();
							} else
							{
								snUpToTime = "NA";
							}
							if (object[4] != null && !object[4].toString().equals(""))
							{
								snDuration = object[4].toString();
							} else
							{
								snDuration = "NA";
							}

							if (object[5] != null && !object[5].toString().equals(""))
							{
								escDate = object[5].toString();
							}

							if (object[6] != null && !object[6].toString().equals(""))
							{
								escTime = object[6].toString();
							}

							if (object[7] != null && !object[7].toString().equals(""))
							{
								open_date = object[7].toString();
								open_time = object[8].toString();
							}
							if (object[11] != null && !object[11].toString().equals(""))
							{
								allottosn = object[11].toString();
							}
							if (object[12] != null && !object[12].toString().equals(""))
							{
								userName = object[12].toString();
							}

						}
					}
					String reason_id = null;
					if (getStatus().equalsIgnoreCase("Resolved"))
					{
						String cal_duration = "";
						if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
						{
							duration = DateUtil.time_difference(open_date, open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
						}
						if (snDuration != null && !snDuration.equals("") && !snDuration.equals("NA"))
						{
							boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
							if (flag)
							{
								cal_duration = DateUtil.getTimeDifference(duration, snDuration);
								if (cal_duration != null && !cal_duration.equals("") && !cal_duration.contains("-"))
								{
									duration = cal_duration;
								}
							} else
							{
								String newduration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), snUpToDate, snUpToTime);
								if (newduration != null && !newduration.equals("") && !newduration.equals("NA"))
								{
									newduration = DateUtil.getTimeDifference(snDuration, newduration);
									String new_cal_duration = DateUtil.getTimeDifference(duration, newduration);
									if (new_cal_duration != null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
									{
										duration = new_cal_duration;
									}
								}
							}
						}
						wherClause.put("status", getStatus());
						wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("action_duration", duration);
						wherClause.put("action_reason", getRemark());
						wherClause.put("action_remark", getSpareused());
						wherClause.put("feedId", getFeedid());
						wherClause.put("previous_allot_to", getResolver());
						reason_id = getRemark();
						String qry = " update feedback_status_new set status='" + getStatus() + "',allot_to= '" + getResolver() + "' where id=" + feedid;
						// System.out.println("Update " + qry);
						cbt.executeAllUpdateQuery(qry, connectionSpace);

						if (userName != null && userName.equalsIgnoreCase("discharge"))
							;
						occMeassage(getFeedid());

					} else if (getStatus().equalsIgnoreCase("Snooze"))
					{
						WorkingHourAction WHA = new WorkingHourAction();
						WorkingHourHelper WHH = new WorkingHourHelper();
						String newVartualSnoozeTime = "00:00";
						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.convertDateToUSFormat(snoozeDate)))
						{
							newVartualSnoozeTime = snoozeTime;
						} else
						{
							newVartualSnoozeTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
						}
						String workingTime = "00:00";
						if (DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(), escDate))
						{
							workingTime = WHH.getWorkingHrs(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connectionSpace, cbt, moduleName, escDate);
						}
						// System.out.println("Total Working Hrs "+workingTime);
						List<String> dataList = WHH.getDayDate(DateUtil.getCurrentDateUSFormat(), connectionSpace, cbt, moduleName);
						String date = dataList.get(0).toString();
						String day = dataList.get(1).toString();
						// System.out.println("workingTime before add" +
						// workingTime);

						if (date.equals(DateUtil.getCurrentDateUSFormat()))
						{
							List tempList = WHH.getWorkingTimeDetails(day, connectionSpace, cbt, moduleName);
							if (tempList != null && tempList.size() > 0)
							{
								String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
								for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									fromTime = object[0].toString();
									toTime = object[1].toString();
									workingHrs = object[2].toString();
								}
								if (DateUtil.checkTwoTimeWithMilSec(fromTime, DateUtil.getCurrentTimeHourMin()) && DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), toTime))
								{
									String todayRemainingWorkingTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), toTime);
									workingTime = DateUtil.addTwoTime(workingTime, todayRemainingWorkingTime);
								} else if (DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), fromTime))
								{
									workingTime = DateUtil.addTwoTime(workingTime, workingHrs);
								}
								// System.out.println("Working Time "+workingTime);
							}
						}
						if (DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(), escDate))
						{
							String escDay = DateUtil.getDayofDate(escDate);
							List tempList = WHH.getWorkingTimeDetails(escDay, connectionSpace, cbt, moduleName);
							if (tempList != null && tempList.size() > 0)
							{
								String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
								for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									fromTime = object[0].toString();
									toTime = object[1].toString();
									workingHrs = object[2].toString();
								}

								String remainingWorkingTimeOnEscDate = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), escDate, escTime);
								workingTime = DateUtil.addTwoTime(workingTime, remainingWorkingTimeOnEscDate);
							}
						}
						List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, newVartualSnoozeTime, workingTime, "Y", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), moduleName);
						if (dateTime != null && dateTime.size() > 0)
						{
							if (dateTime.get(0) != null && dateTime.get(1) != null)
								duration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), dateTime.get(0), dateTime.get(1));

							wherClause.put("status", getStatus());
							wherClause.put("action_reason", getSnoozecomment());
							wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("action_time", DateUtil.getCurrentTime());
							wherClause.put("sn_upto_date", dateTime.get(0));
							wherClause.put("sn_upto_time", dateTime.get(1));
							wherClause.put("action_duration", duration);
							wherClause.put("action_remark", snoozeremark);
							wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
							wherClause.put("feedId", getFeedid());
							wherClause.put("previous_allot_to", allottosn);
							reason_id = getSnoozecomment();
							String qry = " update feedback_status_new set status='Snooze',escalation_date='" + dateTime.get(2) + "',escalation_time='" + dateTime.get(3) + "' where id=" + feedid;
							// System.out.println("Update " + qry);
							cbt.executeAllUpdateQuery(qry, connectionSpace);
						}
					} else if (getStatus().equalsIgnoreCase("High Priority"))
					{
						wherClause.put("status", getStatus());
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("action_reason", getHpcomment());
						wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
						wherClause.put("action_remark", hpremark);
						wherClause.put("feedId", getFeedid());
						reason_id = getHpcomment();
						String qry = " update feedback_status_new set status='" + getStatus() + "' where id=" + feedid;
						// System.out.println("Update " + qry);
						cbt.executeAllUpdateQuery(qry, connectionSpace);
					} else if (getStatus().equalsIgnoreCase("Ignore"))
					{
						wherClause.put("status", getStatus());
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("action_reason", getIgnorecomment());
						wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
						wherClause.put("action_remark", ignoreremark);
						wherClause.put("feedId", getFeedid());
						reason_id = getIgnorecomment();
						String qry = " update feedback_status_new set status='" + getStatus() + "' where id=" + feedid;
						// System.out.println("Update " + qry);
						cbt.executeAllUpdateQuery(qry, connectionSpace);
					}

					else if (getStatus().equalsIgnoreCase("Re-Opened"))
					{
						ComplianceUniversalAction CUA = new ComplianceUniversalAction();
						// String new_escalation_datetime = "0000-00-00#00:00",
						// non_working_timing = "00:00";
						cbt = new CommonConFactory().createInterface();
						String Address_Date_Time = "", allot2 = null, location = "", feed_brief = "", subCategory = "", tosubdpt = "", ticket_No = "";
						String qry = "SELECT Addr_date_time,non_working_time,location,allot_to,feed_brief,subCatg,to_dept_subdept,ticket_no from feedback_status_new where id=" + feedid;
						List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								Address_Date_Time = CUA.getValueWithNullCheck(object[0]);
								non_working_timing = CUA.getValueWithNullCheck(object[1]);
								location = CUA.getValueWithNullCheck(object[2]);
								allot2 = CUA.getValueWithNullCheck(object[3]);
								feed_brief = CUA.getValueWithNullCheck(object[4]);
								subCategory = CUA.getValueWithNullCheck(object[5]);
								tosubdpt = CUA.getValueWithNullCheck(object[6]);
								ticket_No = CUA.getValueWithNullCheck(object[7]);
							}
						}
						HUH = new HelpdeskUniversalHelper();
						HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
						MMC = new MsgMailCommunication();
						fbp = null;
						String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", mailText = "", tolevel = "", needesc = "", esc_time = "";
						StringBuilder query1 = new StringBuilder();
						query1.append(" Select subdept.id,subCat.escalationLevel,subCat.addressingTime,subCat.resolutionTime,subCat.escalateTime,subCat.needEsc from subdepartment as subdept ");
						query1.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
						query1.append(" inner join feedback_category catt on catt.fbType = fb.id ");
						query1.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
						query1.append(" WHERE subCat.id= " + subCategory);
						// System.out.println(">>>>>> subCategory  >>>>> "+query1.toString());
						List subCategoryList = new createTable().executeAllSelectQuery(query1.toString(), connectionSpace);
						if (subCategoryList != null && subCategoryList.size() > 0)
						{
							Object[] objectCol = (Object[]) subCategoryList.get(0);
							if (objectCol[1] == null)
							{
								tolevel = "Level1";
							} else
							{
								tolevel = objectCol[1].toString();
							}
							tosubdept = objectCol[0].toString();
							if (objectCol[2] == null)
							{
								adrr_time = "06:00";
							} else
							{
								adrr_time = objectCol[2].toString();
							}
							if (objectCol[3] == null)
							{
								res_time = "10:00";
							} else
							{
								res_time = objectCol[3].toString();
							}
							if (objectCol[4] == null)
							{
								esc_time = "10:00";
							} else
							{
								esc_time = objectCol[4].toString();
							}
							if (objectCol[5] == null)
							{
								needesc = "Y";
							} else
							{
								needesc = objectCol[5].toString();
							}
						}
						String deptid = tosubdpt;
						String[] date_time_arr = null;
						String[] adddate_time = null;
						if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
						{
							getNextEscalationDateTime(adrr_time, res_time, deptid, "HDM", connectionSpace);
							String[] newdate_time = null;
							if (new_escalation_datetime != null && new_escalation_datetime != "")
							{
								newdate_time = new_escalation_datetime.split("#");
								if (newdate_time.length > 0)
								{
									escalation_date = newdate_time[0];
									escalation_time = newdate_time[1];
								}
							}
							Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);

							if (Address_Date_Time != null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA"))
							{
								String esc_start_time = "00:00";
								String esc_end_time = "24:00";
								String esc_working_day = "";
								esc_working_day = DateUtil.getDayofDate(escalation_date);
								// List of working timing data
								List workingTimingData = null;
								workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
								if (workingTimingData != null && workingTimingData.size() > 0)
								{
									String time_status = "", time_diff = "", working_hrs = "";
									adddate_time = Address_Date_Time.split("#");
									esc_start_time = workingTimingData.get(1).toString();
									esc_end_time = workingTimingData.get(2).toString();
									time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
									if (time_status != null && !time_status.equals("") && time_status.equals("before"))
									{
										time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
										String previous_working_date = "";
										previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date), -1), connectionSpace);
										esc_working_day = DateUtil.getDayofDate(previous_working_date);
										workingTimingData.clear();
										workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
										if (workingTimingData != null && workingTimingData.size() > 0)
										{
											esc_start_time = workingTimingData.get(1).toString();
											esc_end_time = workingTimingData.get(2).toString();
											working_hrs = workingTimingData.get(3).toString();
											if (DateUtil.checkTwoTime(working_hrs, time_diff))
											{
												Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
											} else
											{
												time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
												previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day", -1, previous_working_date), connectionSpace);

												esc_working_day = DateUtil.getDayofDate(previous_working_date);
												workingTimingData.clear();
												workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
												if (workingTimingData != null && workingTimingData.size() > 0)
												{
													esc_start_time = workingTimingData.get(1).toString();
													esc_end_time = workingTimingData.get(2).toString();
													working_hrs = workingTimingData.get(3).toString();
													if (DateUtil.checkTwoTime(working_hrs, time_diff))
													{
														Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
													} else
													{
														time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
													}
												}
											}
										}
									}
								}
							}
						} else
						{
							escalation_date = "0000-00-00";
							escalation_time = "00:00";
							Address_Date_Time = DateUtil.newdate_AfterTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time);
							date_time_arr = Address_Date_Time.split("#");
							String esc_start_time = "00:00";
							String esc_end_time = "24:00";
							String esc_working_day = "";
							esc_working_day = DateUtil.getDayofDate(date_time_arr[0]);
							// List of working timing data
							List workingTimingData = null;
							workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								String time_status = "", time_diff = "", working_hrs = "";
								adddate_time = Address_Date_Time.split("#");
								esc_start_time = workingTimingData.get(2).toString();
								esc_end_time = workingTimingData.get(3).toString();
								time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
								if (time_status != null && !time_status.equals("") && (time_status.equals("before") || time_status.equals("after")))
								{
									time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], adddate_time[0], esc_start_time);
									String previous_working_date = "";
									previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1), connectionSpace);
									esc_working_day = DateUtil.getDayofDate(previous_working_date);
									workingTimingData.clear();
									workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
									if (workingTimingData != null && workingTimingData.size() > 0)
									{
										esc_start_time = workingTimingData.get(2).toString();
										esc_end_time = workingTimingData.get(3).toString();
										working_hrs = workingTimingData.get(4).toString();
										if (DateUtil.checkTwoTime(working_hrs, time_diff))
										{
											Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
										} else
										{
											time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
											previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date, -1), connectionSpace);
											esc_working_day = DateUtil.getDayofDate(previous_working_date);
											workingTimingData.clear();
											workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
											if (workingTimingData != null && workingTimingData.size() > 0)
											{

												esc_start_time = workingTimingData.get(2).toString();
												esc_end_time = workingTimingData.get(3).toString();
												working_hrs = workingTimingData.get(4).toString();
												if (DateUtil.checkTwoTime(working_hrs, time_diff))
												{
													Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
												} else
												{
													time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
												}
											}
										}
									}
								}
							}
						}
						reason_id = reallotedcomment;
						String allottoId = null;
						if (reallotedto != null && !reallotedto.equalsIgnoreCase(""))
						{
							allottoId = reallotedto;
						} else
						{
							allottoId = allot2;
						}
						Map<String, Object> where = new HashMap<String, Object>();
						Map<String, Object> condition = new HashMap<String, Object>();
						where.put("status", "Re-Opened");
						where.put("level", tolevel);
						where.put("allot_to", allot2);
						where.put("escalation_date", escalation_date);
						where.put("escalation_time", escalation_time);
						where.put("open_time", DateUtil.getCurrentTime());
						where.put("open_date", DateUtil.getCurrentDateUSFormat());

						condition.put("id", getFeedid());
						// System.out.println("Update " + qry);
						boolean statusFlag = false;
						statusFlag = cbt.updateTableColomn("feedback_status_new", where, condition, connectionSpace);

						if (statusFlag)
						{
							wherClause.put("status", "Re-OpenedR");
							wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
							wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("action_time", DateUtil.getCurrentTime());
							wherClause.put("action_reason", reallotedcomment);
							wherClause.put("action_remark", reopenremark);
							wherClause.put("previous_allot_to", allottoId);
							wherClause.put("feedId", getFeedid());

							List data = HUH.getFeedbackDetail("", "SD", "Re-Opened", String.valueOf(getFeedid()), connectionSpace);
							fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);
							List allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, String.valueOf(Integer.parseInt(tolevel.substring(5)) + 1), "Y", location, connectionSpace, subCategory);
							String reasonVal = null;
							String queryReason = "SELECT tasktype FROM task_type WHERE id='" + reason_id + "'";
							List reasondata = cbt.executeAllSelectQuery(queryReason, connectionSpace);
							if (reasondata != null && !reasondata.isEmpty())
							{
								reasonVal = reasondata.get(0).toString();
							} else
							{
								reasonVal = "NA";
							}
							if (allotto != null && allotto.size() > 0)
							{
								StringBuilder qry1 = new StringBuilder();
								qry1.append(" SELECT emp.id,emp.empName,emp.mobOne,emp.emailIdOne FROM employee_basic AS emp ");
								qry1.append(" WHERE emp.id =' " + allotto.get(0).toString() + "' ");
								List resultList = HUH.getData(qry1.toString(), connectionSpace);
								if (resultList != null && resultList.size() > 0)
								{
									for (Object c : resultList)
									{
										Object[] objVar = (Object[]) c;
										if (objVar[2] != null && objVar[2].toString() != "" && objVar[2].toString().trim().length() == 10 && !objVar[2].toString().startsWith("NA") && (objVar[2].toString().startsWith("9") || objVar[2].toString().startsWith("8") || objVar[2].toString().startsWith("7")))
										{
											String levelMsg = "Re-Opened: " + fbp.getTicket_no() + ", Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + " " + fbp.getEscalation_time() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + fbp.getFeedback_subcatg() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + reasonVal + ".";
											MMC.addMessage(objVar[1].toString(), "", objVar[2].toString(), levelMsg, ticket_No, "Pending", "0", "HDM");
										}
										/*
										 * if (objVar[3] != null &&
										 * !objVar[3].toString().equals("") &&
										 * !objVar[3].toString().equals("NA")) {
										 * mailText = HUH.getConfigMessage(fbp,
										 * "Open Feedback Alert", "Pending",
										 * deptLevel, true);
										 * MMC.addMailWithCC(objVar
										 * [1].toString(),
										 * fbp.getFeedback_to_dept(),
										 * objVar[3].toString(),
										 * "Open Feedback Alert", mailText,
										 * ticket_No, "Pending", "0", "", "HDM",
										 * ""); }
										 */
									}
									resultList.clear();
								}
							}
							escTime = DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().substring(0, 5);
							if (fbp != null)
							{
								if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
								{
									String levelMsg = "Re-Opened: " + fbp.getTicket_no() + ", Closed By: " + fbp.getAddr_date_time() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + fbp.getFeedback_subcatg() + ", Brief: " + fbp.getFeed_brief() + ", Reason: " + reasonVal + ".";
									MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_No, "Pending", "0", "HDM");
								}
								/*
								 * if (fbp.getEmailIdOne() != null &&
								 * !fbp.getEmailIdOne().equals("") &&
								 * !fbp.getEmailIdOne().equals("NA")) { mailText
								 * = HUH.getConfigMessage(fbp,
								 * "Open Feedback Alert", "Pending", deptLevel,
								 * true);
								 * MMC.addMailWithCC(fbp.getFeedback_allot_to(),
								 * fbp.getFeedback_to_dept(),
								 * fbp.getEmailIdOne(), "Open Feedback Alert",
								 * mailText, ticket_No, "Pending", "0", "",
								 * "HDM", ""); }
								 */
							}
						}
					} else if (getStatus().equalsIgnoreCase("Re-Assign"))
					{
						String location = "NA", todept = "NA", subcat = "NA", allotto = "";
						List existTicketData = new HelpdeskUniversalHelper().getTransferTicketData(getFeedid(), connectionSpace);
						if (existTicketData != null && existTicketData.size() > 0)
						{
							Object[] object = (Object[]) existTicketData.get(0);
							if (object[0] != null && !object[0].toString().equals(""))
							{
								location = object[0].toString();
							}
							if (object[1] != null && !object[1].toString().equals(""))
							{
								todept = object[1].toString();
							}
							if (object[2] != null && !object[2].toString().equals(""))
							{
								subcat = object[2].toString();
							}
							if (object[3] != null && !object[3].toString().equals(""))
							{
								allotto = object[3].toString();
							}
							if (object[4] != null && object[5] != null)
							{
								// System.out.println("Open Date : " +
								// object[4].toString() + "Open Time: " +
								// object[5].toString());
							}
							assignTo = transferComplaint(emailId, location, getFeedid(), this.allotto, todept, subcat);
							if (assignTo != null)
							{

								wherClause.put("status", "Transfer");
								wherClause.put("action_by", session.get("empIdofuser").toString().split("-")[1]);
								wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
								wherClause.put("action_time", DateUtil.getCurrentTime());
								wherClause.put("action_reason", reassignreason);
								wherClause.put("action_remark", reAssignRemark);
								wherClause.put("previous_allot_to", allotto);

								wherClause.put("feedId", getFeedid());
							}
						}
					}

					boolean updateFeed = false;
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
						updateFeed = cbt.insertIntoTable("feedback_status_history", insertData, connectionSpace);
						insertData.clear();
					}
					if (updateFeed)
					{
						StringBuilder qry = new StringBuilder();
						qry.append("SELECT emp.id,emp.empId,feed.feed_by_mobno from feedback_status_new as feed ");
						qry.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.mobOne =feed.feed_by_mobno");
						qry.append(" where feed.id=" + getFeedid() + " AND status='Resolved'");
						List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator3 = dataList.iterator(); iterator3.hasNext();)
							{
								Object[] object = (Object[]) iterator3.next();
								if (object[1] != null && empIdofuser.split("-")[1].trim().equalsIgnoreCase(object[1].toString()))
								{
									setResolve_Alert("Done");
								}
							}
						}
						String reasonVal = null;
						String queryReason = "SELECT tasktype FROM task_type WHERE id='" + reason_id + "'";
						List reasondata = cbt.executeAllSelectQuery(queryReason, connectionSpace);
						if (reasondata != null && !reasondata.isEmpty())
						{
							reasonVal = reasondata.get(0).toString();
						} else
						{
							reasonVal = "NA";
						}
						String levelMsg = "", mailText = "", mailSubject = "", mailheading = "";
						List data = HUH.getFeedbackDetail("", "SD", getStatus(), getFeedid(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							fbp = HUH.setFeedbackDataValues(data, getStatus(), deptLevel);
						}
						if (getStatus().equalsIgnoreCase("Resolved"))
						{
							levelMsg = "Close: " + fbp.getTicket_no() + ", Loc: " + fbp.getRoomNo() + ", Assign To: " + fbp.getFeedback_allot_to() + ", Call Desc: " + fbp.getFeedback_subcatg() + " is Closed.";
							mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
							mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
						} else if (getStatus().equalsIgnoreCase("Re-Assign") && assignTo != null)
						{
							levelMsg = "Re-Assign: " + fbp.getTicket_no() + ", Loc: " + fbp.getRoomNo() + ", Assign To: " + assignTo + ", Call Desc: " + fbp.getFeedback_subcatg() + " is Re-Assign.";

							mailSubject = "Re-Assign Ticket Alert: " + fbp.getTicket_no();
							mailheading = "Re-Assign Ticket Alert: " + fbp.getTicket_no();
						} else if (getStatus().equalsIgnoreCase("High Priority"))
						{
							levelMsg = "High Priority: " + fbp.getTicket_no() + ", Loc: " + fbp.getRoomNo() + ", Assign To: " + fbp.getFeedback_allot_to() + ", Call Desc: " + fbp.getFeedback_subcatg() + ", Brief: " + fbp.getFeed_brief() + ", Reason: " + reasonVal + ".";

							mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
							mailheading = "High Priority Case Ticket Alert";
						} else if (getStatus().equalsIgnoreCase("Snooze"))
						{
							levelMsg = "Parked: " + fbp.getTicket_no() + ", Loc: " + fbp.getRoomNo() + ", Assign To: " + fbp.getFeedback_allot_to() + ", Call Desc: " + fbp.getFeedback_subcatg() + " Parked till " + DateUtil.convertDateToIndianFormat(fbp.getSn_upto_date()) + " " + fbp.getSn_upto_time() + ",Reason: " + reasonVal + ".";

							mailSubject = "Parked Feedback Alert: " + fbp.getTicket_no();
							mailheading = "Parked Case Ticket Alert";
						} else if (getStatus().equalsIgnoreCase("Ignore"))
						{
							levelMsg = "Cancelled: " + fbp.getTicket_no() + ", Loc: " + fbp.getRoomNo() + ", Call Desc: " + fbp.getFeedback_subcatg() + ", Assign To: " + fbp.getFeedback_allot_to() + ", Reason: " + reasonVal + " should be Cancelled.";

							mailSubject = "Cancelled Feedback Alert: " + fbp.getTicket_no();
							mailheading = "Cancelled Case Ticket Alert";
						}

						if (getStatus().equalsIgnoreCase("Resolved"))
						{
							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
							}
							/*
							 * if (fbp.getResolve_by_emailid() != null &&
							 * !fbp.getResolve_by_emailid().equals("")) {
							 * mailText = new
							 * HelpdeskUniversalHelper().getConfigMessage(fbp,
							 * mailheading, getStatus(), deptLevel, true);
							 * MMC.addMail(fbp.getResolve_by(),
							 * fbp.getFeedback_to_dept(),
							 * fbp.getResolve_by_emailid(), mailSubject,
							 * mailText, ticket_no, "Pending", "0", "", "HDM");
							 * }
							 */
						} else if (getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore"))
						{
							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
							}

							/*
							 * if (fbp.getEmailIdOne() != null &&
							 * !fbp.getEmailIdOne().equals("")) { mailText = new
							 * HelpdeskUniversalHelper().getConfigMessage(fbp,
							 * mailheading, getStatus(), deptLevel, true);
							 * MMC.addMail(fbp.getFeedback_allot_to(),
							 * fbp.getFeedback_to_dept(), fbp.getEmailIdOne(),
							 * mailSubject, mailText, ticket_no, "Pending", "0",
							 * "", "HDM"); }
							 */
						} else if (getStatus().equalsIgnoreCase("Re-Assign"))
						{
							if (fbp.getPre_allot_to_mob() != null && fbp.getPre_allot_to_mob() != "" && fbp.getPre_allot_to_mob().trim().length() == 10 && (fbp.getPre_allot_to_mob().startsWith("9") || fbp.getPre_allot_to_mob().startsWith("8") || fbp.getPre_allot_to_mob().startsWith("7")))
							{
								MMC.addMessage(fbp.getPre_allot_to(), fbp.getFeedback_to_dept(), fbp.getPre_allot_to_mob(), levelMsg, ticket_no, getStatus(), "0", "HDM");
							}
						}
					}

					addActionMessage("Feedback Updated in " + getStatus() + " Successfully !!!");
					returnResult = SUCCESS;
				} else
				{
					complaintid = feedid;
					moduleName = "HDM";
					if (approvalDocFileName != null)
					{
						String renameFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + DateUtil.mergeDateTime() + approvalDocFileName;
						String storeFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + approvalDocFileName;
						String str = renameFilePath.replace("//", "/");
						// renameFilePath=DateUtil.removeSpace(renameFilePath);
						// storeFilePath=DateUtil.removeSpace(storeFilePath);
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(approvalDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
								storedDocPath = renameFilePath;
							}
						}
					}
					returnResult = "ForwoardForApproval";
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

	// occ message for Discharge integration
	public void occMeassage(String feed_id)
	{

		java.sql.Connection con = null;
		try
		{

			Statement st = null;
			ResultSet rs = null;
			String kk = "p";
			CommonOperInterface cot = new CommonConFactory().createInterface();
			StringBuilder str = new StringBuilder("");
			String bed_refer_no = "", ticket_no = "", allot_to_name = "", location = "", sub_cat = "";
			str.append("select hh.bed_refer_no, fsn.ticket_no,emp.empName,det.roomno,subcat.subCategoryName from feedback_hdm_his_mapping as hh");
			str.append(" inner join feedback_status_new as fsn on fsn.id=hh.feed_id ");
			str.append(" inner join floor_room_detail as det on fsn.location=det.id");
			str.append(" inner join employee_basic as emp on emp.id=fsn.allot_to ");
			str.append(" inner join feedback_subcategory as subcat on subcat.id=fsn.subCatg ");
			str.append(" where hh.feed_id='" + feed_id + "'");

			List data = cot.executeAllSelectQuery(str.toString(), connectionSpace);
			boolean a = false;
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					bed_refer_no = object[0].toString();
					ticket_no = object[1].toString();
					allot_to_name = object[2].toString();
					location = object[3].toString();
					sub_cat = object[4].toString();
				}
				a = true;
			}
			System.out.println(bed_refer_no);
			System.out.println(ticket_no);
			System.out.println(allot_to_name);
			System.out.println(location);
			System.out.println(sub_cat);
			System.out.println(a);
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			System.out.println("day  "+day);
			if (a)
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");

				if (DateUtil.getCurrentDay() == 0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:45", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin()) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30"))

				{
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
					System.out.println("redirect  66 Server......");

				}
				
			
				else if ((day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "20:30", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:30" )))
				{
					
					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
					System.out.println("redirect  66 Server......");
					
				}
				else
				{

					con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
					System.out.println("okey  88 Server......");

				}

				/*
				 * con =DriverManager.getConnection(
				 * "jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol",
				 * "Dream_1$3");
				 */
				st = con.createStatement();
				String str1 = "select BED_BOOKING_REF_NO from dreamsol_dis_vw where BED_BOOKING_REF_NO='" + bed_refer_no + "'";
				rs = st.executeQuery(str1);

				while (rs.next())
				{
					kk = rs.getString("BED_BOOKING_REF_NO");
				}
				if (!kk.equalsIgnoreCase("p"))
				{
					String levelMsg = "Ticket: " + ticket_no + ", Location: " + location + ", Closed By Id: " + (String) cot.executeAllSelectQuery("SELECT empName FROM `employee_basic` WHERE `id`='" + session.get("empIdofuser").toString().split("-")[1] + "'", connectionSpace).get(0) + ".";
					MsgMailCommunication MMC = new MsgMailCommunication();
					MMC.addMessage(allot_to_name, "OCC", (String) cot.executeAllSelectQuery("SELECT mobOne FROM `employee_basic` WHERE `empName`='Discharge Mobile'", connectionSpace).get(0), levelMsg, ticket_no, "Pending", "0", "HDM");
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("oops!! Error found in sending message to OCC");

		} finally
		{
			if (con != null)
			{
				try
				{
					con.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;

		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_day = DateUtil.getDayofDate(working_date);
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();
			// Here we know the complaint lodging time is inside the the start
			// and end time or not
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
				} else
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				} else
				{
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					startTime = workingTimingData.get(1).toString();
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					non_working_timing = workingTimingData.get(3).toString();
				}
			}
		}
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public String transferComplaint(String emailId, String location, String previousId, String allottoId, String todept, String subcat)
	{
		String flag = null;
		try
		{
			CommonOperInterface cot = new CommonConFactory().createInterface();
			HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
			String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", needesc = "", level = "";
			List subCategoryList = HUH.getAllData("feedback_subcategory", "id", subcat, "subCategoryName", "ASC", connectionSpace);
			if (subCategoryList != null && subCategoryList.size() > 0)
			{
				for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[4] == null)
					{
						adrr_time = "06:00";
					} else
					{
						adrr_time = objectCol[4].toString();
					}
					if (objectCol[5] == null)
					{
						res_time = "10:00";
					} else
					{
						res_time = objectCol[5].toString();
					}
					if (objectCol[9] == null)
					{
						needesc = "Y";
					} else
					{
						needesc = objectCol[9].toString();
					}
					if (objectCol[8] == null)
					{
						level = "Level1";
					} else
					{
						level = objectCol[8].toString();
					}

				}
			}

			String[] date_time_arr = null;
			String[] adddate_time = null;
			String Address_Date_Time = "NA";
			String deptid = todept;
			if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
			{
				getNextEscalationDateTime(adrr_time, res_time, deptid, "HDM", connectionSpace);
				String[] newdate_time = null;
				if (new_escalation_datetime != null && new_escalation_datetime != "")
				{
					newdate_time = new_escalation_datetime.split("#");
					if (newdate_time.length > 0)
					{
						escalation_date = newdate_time[0];
						escalation_time = newdate_time[1];
					}
				}
				Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);
				if (Address_Date_Time != null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA"))
				{
					String esc_start_time = "00:00";
					String esc_end_time = "24:00";
					String esc_working_day = "";
					esc_working_day = DateUtil.getDayofDate(escalation_date);
					// List of working timing data
					List workingTimingData = null;
					workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						String time_status = "", time_diff = "", working_hrs = "";
						adddate_time = Address_Date_Time.split("#");
						esc_start_time = workingTimingData.get(1).toString();
						esc_end_time = workingTimingData.get(2).toString();
						time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
						if (time_status != null && !time_status.equals("") && time_status.equals("before"))
						{
							time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
							String previous_working_date = "";
							previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date), -1), connectionSpace);
							esc_working_day = DateUtil.getDayofDate(previous_working_date);
							workingTimingData.clear();
							workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								esc_start_time = workingTimingData.get(1).toString();
								esc_end_time = workingTimingData.get(2).toString();
								working_hrs = workingTimingData.get(3).toString();
								if (DateUtil.checkTwoTime(working_hrs, time_diff))
								{
									Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
								} else
								{
									time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
									previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day", -1, previous_working_date), connectionSpace);

									esc_working_day = DateUtil.getDayofDate(previous_working_date);
									workingTimingData.clear();
									workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
									if (workingTimingData != null && workingTimingData.size() > 0)
									{
										esc_start_time = workingTimingData.get(1).toString();
										esc_end_time = workingTimingData.get(2).toString();
										working_hrs = workingTimingData.get(3).toString();
										if (DateUtil.checkTwoTime(working_hrs, time_diff))
										{
											Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
										} else
										{
											time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
										}
									}
								}
							}
						}
					}
				}
			} else
			{
				escalation_date = "0000-00-00";
				escalation_time = "00:00";
				Address_Date_Time = DateUtil.newdate_AfterTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time);
				date_time_arr = Address_Date_Time.split("#");
				String esc_start_time = "00:00";
				String esc_end_time = "24:00";
				String esc_working_day = "";
				esc_working_day = DateUtil.getDayofDate(date_time_arr[0]);
				// List of working timing data
				List workingTimingData = null;
				workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					String time_status = "", time_diff = "", working_hrs = "";
					adddate_time = Address_Date_Time.split("#");
					esc_start_time = workingTimingData.get(2).toString();
					esc_end_time = workingTimingData.get(3).toString();
					time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
					if (time_status != null && !time_status.equals("") && (time_status.equals("before") || time_status.equals("after")))
					{
						time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], adddate_time[0], esc_start_time);
						String previous_working_date = "";
						previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1), connectionSpace);
						esc_working_day = DateUtil.getDayofDate(previous_working_date);
						workingTimingData.clear();
						workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							esc_start_time = workingTimingData.get(2).toString();
							esc_end_time = workingTimingData.get(3).toString();
							working_hrs = workingTimingData.get(4).toString();
							if (DateUtil.checkTwoTime(working_hrs, time_diff))
							{
								Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
							} else
							{
								time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
								previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date, -1), connectionSpace);
								esc_working_day = DateUtil.getDayofDate(previous_working_date);
								workingTimingData.clear();
								workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
								if (workingTimingData != null && workingTimingData.size() > 0)
								{

									esc_start_time = workingTimingData.get(2).toString();
									esc_end_time = workingTimingData.get(3).toString();
									working_hrs = workingTimingData.get(4).toString();
									if (DateUtil.checkTwoTime(working_hrs, time_diff))
									{
										Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
									} else
									{
										time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
									}
								}
							}
						}
					}
				}
			}
			Map<String, Object> where = new HashMap<String, Object>();
			Map<String, Object> condition = new HashMap<String, Object>();
			where.put("status", "Re-Assign");
			where.put("level", level);
			where.put("allot_to", allottoId);
			where.put("escalation_date", escalation_date);
			where.put("escalation_time", escalation_time);
			where.put("open_time", DateUtil.getCurrentTime());
			where.put("open_date", DateUtil.getCurrentDateUSFormat());
			condition.put("id", previousId);
			// System.out.println("Update " + qry);
			boolean statusFlag = false;
			statusFlag = cot.updateTableColomn("feedback_status_new", where, condition, connectionSpace);
			if (statusFlag)
			{
				String levelMsg = "";
				List data = HUH.getFeedbackDetail("", "SD", "Re-Assign", String.valueOf(previousId), connectionSpace);
				FeedbackPojo fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);
				flag = fbp.getFeedback_allot_to();
				if (fbp != null)
				{
					MsgMailCommunication MMC = new MsgMailCommunication();
					levelMsg = "Open: Call No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getRoomNo() + " , Allot To: " + fbp.getFeedback_allot_to() + ", Call Desc: " + fbp.getFeedback_subcatg() + " , Brief: " + fbp.getFeed_brief() + ".";
					if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
					{
						MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
					}
					if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
					{
						String mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, "Pending Ticket Alert : " + fbp.getTicket_no() + "", getStatus(), deptLevel, true);
						MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Pending Ticket Alert : " + fbp.getTicket_no() + "", mailText, ticket_no, "Pending", "0", "", "HDM");
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	// Method for Update Feedback Type (In Use)
	@SuppressWarnings("unchecked")
	public String modifyFeedSnooze()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					String updateQry = "update feedback_status set sn_reason='" + sn_reason + "',action_by='" + userName + "' where id='" + id + "'";
					new HelpdeskUniversalHelper().updateData(updateQry, connectionSpace);
					returnResult = SUCCESS;
				}
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

	public Map<String, String> getTicketDetails(String feedId)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List dataList = null;
			// StringBuilder NextEscalationTo = new StringBuilder();
			dataMap = new LinkedHashMap<String, String>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,dept.deptName AS bydepart,fstatus.feed_by,fCat.categoryName,fbtype.fbType,fstatus.status,fstatus.level,fstatus.feed_by_mobno,");
			query.append("deptTo.deptName AS todepart,emp.empName,emp.mobone,fsubCat.subCategoryName,fstatus.feed_brief,");
			query.append("fstatus.escalation_date,fstatus.escalation_time,fstatus.to_dept_subdept,fstatus.allot_to,emp1.empName AS resolveby,history.action_date,history.action_time,history.action_duration,fstatus.subCatg");
			query.append(" FROM feedback_status_new AS fstatus");
			query.append(" LEFT JOIN  feedback_status_history  AS history  ON  history.feedId =fstatus.id");
			query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");
			query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
			query.append(" LEFT JOIN  employee_basic  AS emp1  ON  emp1.id =history.action_by");
			query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =emp.deptname");
			query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
			query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
			query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
			query.append(" WHERE fstatus.id=" + feedId);
			// System.out.println("Ticket details for action :::@ " +
			// query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					ticket_no = CUA.getValueWithNullCheck(object[0]);
					if (object[1] != null)
						open_date = DateUtil.convertDateToIndianFormat(object[1].toString());
					else
						dataMap.put("Open Date", "NA");
					open_time = CUA.getValueWithNullCheck(object[2]).substring(0, CUA.getValueWithNullCheck(object[2]).length() - 3);

					dataMap.put("From Department", CUA.getValueWithNullCheck(object[3]));
					dataMap.put("Feedback By", CUA.getValueWithNullCheck(object[4]));

					dataMap.put("Category", CUA.getValueWithNullCheck(object[6]));

					dataMap.put("Current Status", CUA.getValueWithNullCheck(object[7]));
					dataMap.put("Current Level", CUA.getValueWithNullCheck(object[8]));
					dataMap.put("Mobile No.", CUA.getValueWithNullCheck(object[9]));
					dataMap.put("To Department", CUA.getValueWithNullCheck(object[10]));
					dataMap.put("Allotted To", CUA.getValueWithNullCheck(object[11]));
					dataMap.put("Requested By", CUA.getValueWithNullCheck(object[11]));
					allotto = CUA.getValueWithNullCheck(object[18]);
					dataMap.put("Mobile No", CUA.getValueWithNullCheck(object[12]));
					dataMap.put("Sub-Category", CUA.getValueWithNullCheck(object[13]));
					dataMap.put("Brief", CUA.getValueWithNullCheck(object[14]));
					dataMap.put("Lapse Time", DateUtil.time_difference(object[1].toString(), object[2].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin()));
					if (object[15] != null)
						dataMap.put("Next Escalation On", DateUtil.convertDateToIndianFormat(object[15].toString()) + ", " + CUA.getValueWithNullCheck(object[16]));
					else
						dataMap.put("Next Escalation On", "NA");

					/*
					 * String level =
					 * String.valueOf(Integer.parseInt(object[8].toString
					 * ().substring(5, 6)) + 1); List tempList =
					 * getEmp4Escalation(object[17].toString(), "HDM", level,
					 * connectionSpace); if (tempList != null && tempList.size()
					 * > 0) { for (Iterator iterator1 = tempList.iterator();
					 * iterator1.hasNext();) { Object[] object1 = (Object[])
					 * iterator1.next(); if (object[1] != null)
					 * NextEscalationTo.append(object1[1].toString() + ", ");
					 * else NextEscalationTo.append("NA");
					 * 
					 * } }
					 * 
					 * if (NextEscalationTo.toString().length() != 0)
					 * dataMap.put("Next Escalation To",
					 * NextEscalationTo.toString().substring(0,
					 * NextEscalationTo.toString().length() - 2)); else
					 * dataMap.put("Next Escalation To", "NA");
					 */
					dataMap.put("Resolved By", CUA.getValueWithNullCheck(object[19]));
					if (object[20] != null)
						dataMap.put("Resolved On", DateUtil.convertDateToIndianFormat(object[20].toString()) + ", " + object[21].toString().substring(0, object[21].toString().length() - 3));

					dataMap.put("Resolve Duration", CUA.getValueWithNullCheck(object[22]));

					if ((object[23]) != null)
					{
						subCatg = object[23].toString();
						/*
						 * String query1 =
						 * "SELECT id,completion_tip FROM  feedback_completion_tip WHERE subCatId="
						 * + object[23].toString() + " ORDER BY completion_tip";
						 * List dataList1 = new
						 * createTable().executeAllSelectQuery(query1,
						 * connectionSpace); if (dataList1 != null &&
						 * dataList1.size() > 0) { for (Iterator iterator2 =
						 * dataList1.iterator(); iterator2.hasNext();) {
						 * Object[] obj = (Object[]) iterator2.next(); if
						 * (object[0] != null && obj[1] != null)
						 * checkListMap.put(obj[0].toString(),
						 * obj[1].toString()); } }
						 */
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

	@SuppressWarnings("rawtypes")
	public FeedbackPojo getTicketDetail(String feedId, String flag)
	{
		try
		{
			List dataList = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,dept.deptName AS bydepart,fstatus.feed_by,fCat.categoryName,fbtype.fbType,fstatus.status ,");
			query.append("deptTo.deptName AS todepart,emp.empName,fsubCat.subCategoryName,fstatus.feed_brief,");
			query.append("room.roomno FROM feedback_status_new AS fstatus");
			query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");
			query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
			query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =fstatus.to_dept_subdept");
			query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
			query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
			query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
			query.append(" LEFT JOIN  floor_room_detail AS room ON fstatus.location = room.id");
			if (flag != null && flag.equalsIgnoreCase("id"))
				query.append(" WHERE fstatus.id=" + feedId);
			else if (flag != null && flag.equalsIgnoreCase("TicketNo"))
				query.append(" WHERE fstatus.ticket_no='" + feedId + "'");

			// System.out.println("Query : " + query.toString() +
			// "    FEED ID:  " + feedId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			fstatus = new FeedbackPojo();
			if (dataList != null && dataList.size() > 0)
			{

				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					fstatus.setTicket_no(CUA.getValueWithNullCheck(object[0]));
					if (object[1] != null)
						fstatus.setOpen_date(DateUtil.convertDateToIndianFormat(object[1].toString()));
					fstatus.setOpen_time(CUA.getValueWithNullCheck(object[2]).substring(0, object[2].toString().length() - 3));
					fstatus.setFeedback_by_dept(CUA.getValueWithNullCheck(object[3]));
					fstatus.setFeed_by(CUA.getValueWithNullCheck(object[4]));
					fstatus.setFeedback_catg(CUA.getValueWithNullCheck(object[6]));
					fstatus.setFeedtype(CUA.getValueWithNullCheck(object[5]));
					fstatus.setStatus(CUA.getValueWithNullCheck(object[7]));
					fstatus.setFeedback_to_dept(CUA.getValueWithNullCheck(object[8]));
					fstatus.setEmpName(CUA.getValueWithNullCheck(object[9]));
					fstatus.setFeedback_subcatg(CUA.getValueWithNullCheck(object[10]));
					fstatus.setFeed_brief(CUA.getValueWithNullCheck(object[11]));
					fstatus.setLocation(CUA.getValueWithNullCheck(object[12]));
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return fstatus;
	}

	public List getTicketCycleDetail(String feedId, String flag)
	{
		try
		{
			List dataList = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ActivityBoardHelper helperObject = new ActivityBoardHelper();
			dataList = helperObject.getTicketCycle(connectionSpace, cbt, feedId);
			String actionBy = "NA";
			if (dataList != null && dataList.size() > 0)
			{
				cycleList = new ActivityBoardAction().getCycleByOrder(dataList);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return cycleList;
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

	public String getReopen()
	{
		return reopen;
	}

	public void setReopen(String reopen)
	{
		this.reopen = reopen;
	}

	public String getDataCheck()
	{
		return dataCheck;
	}

	public void setDataCheck(String dataCheck)
	{
		this.dataCheck = dataCheck;
	}

	public String getMgtFlag()
	{
		return mgtFlag;
	}

	public void setMgtFlag(String mgtFlag)
	{
		this.mgtFlag = mgtFlag;
	}

	public void setData4(String data4)
	{
		this.data4 = data4;
	}

	public String getData4()
	{
		return data4;
	}

	public void setDylevel(String dylevel)
	{
		this.dylevel = dylevel;
	}

	public String getDylevel()
	{
		return dylevel;
	}

}