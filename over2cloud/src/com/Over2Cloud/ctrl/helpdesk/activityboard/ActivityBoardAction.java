package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;

@SuppressWarnings("serial")
public class ActivityBoardAction extends GridPropertyBean
{
	private String taskType;
	private String fromDepart;
	private String toDepart;
	private String feedStatus;
	private String escLevel;
	private String escTAT;
	private String fromTime;
	private String toTime;
	private String tableAlis;
	private String divName;
	private Map<String, String> columnMap;
	private String[] columnNames;
	private String excelFileName;
	private String selectedId;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	private List<Object> masterViewProp = new ArrayList<Object>();
	private Map<String, String> dataCountMap;
	private String complianId;
	private String formaterOn;
	private String mainTable;
	private Map<String, String> dataMap;
	private Map<String, String> editableDataMap;
	private String maxDateValue;
	private String minDateValue;
	private String[] resolutionDetail;
	private Map<String, Map<String, String>> finalStatusMap;
	private JSONArray jsonArray;
	private String sel_id;
	private String dept;
	private String category;
	private Map<Integer, String> deptList;
	private List<FeedTicketCyclePojo> list;
	private String advncSearch;
	@SuppressWarnings("rawtypes")
	public String viewActivityBoardHeader()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	deptList = new LinkedHashMap<Integer, String>();
	List departmentlist = new ArrayList();

	departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "HDM", connectionSpace);
	if (departmentlist != null && departmentlist.size() > 0)
	{
	for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	deptList.put((Integer) object[0], object[1].toString());
	}
	departmentlist.clear();
	}

	maxDateValue = DateUtil.getCurrentDateUSFormat();
	minDateValue = DateUtil.getCurrentDateUSFormat();
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

	 

	
	public String viewActivityBoardColumn()
	{
	try
	{
	//String userRights = session.get("userRights").toString();
	viewPageColumns = new ArrayList<GridDataPropertyView>();
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	viewPageColumns.add(gpv);
	 	
	
	
	//      <<<<<   For apply user right on take action button hide/show      : Sanjay Kumar Soni  >>>>>>>
	
	
	/*if(!session.get("Department").toString().equalsIgnoreCase("OCC"))
	{*/
	/*if(userRights.contains("takeaction_VIEW"))
	{*/
	gpv = new GridDataPropertyView();
	gpv.setColomnName("action");
	gpv.setHeadingName("Action");
	gpv.setFormatter("takeAction");
	gpv.setHideOrShow("false");
	gpv.setWidth(35);
	viewPageColumns.add(gpv);
	/*}*/
	//}
	
	
	 
	 	
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("ticketno");
	gpv.setHeadingName("Ticket No.");
	gpv.setFormatter("statusDetail");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("openat");
	gpv.setHeadingName("Open On");
	gpv.setHideOrShow("false");
	gpv.setWidth(110);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("Timer");
	gpv.setHeadingName("Timer");
	gpv.setHideOrShow("false");
	gpv.setWidth(40);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("TAT");
	gpv.setHeadingName("TAT Status");
	gpv.setFormatter("tatDetail");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	//gpv.setFormatter("tatDetail");
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("location");
	gpv.setHeadingName("Location");
	gpv.setHideOrShow("false");
	gpv.setFormatter("viewLocation");
	gpv.setWidth(120);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("subcatg");
	gpv.setHeadingName("Sub-Category");
	gpv.setHideOrShow("false");
	gpv.setWidth(95);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("brief");
	gpv.setHeadingName("Feedback Brief");
	gpv.setHideOrShow("false");
	gpv.setWidth(140);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("status");
	gpv.setHeadingName("Status");
	gpv.setHideOrShow("true");
	gpv.setWidth(60);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("allotedto");
	gpv.setHeadingName("Alloted To");
	gpv.setFormatter("allotToDetail");
	gpv.setWidth(100);
	gpv.setHideOrShow("false");
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("todept");
	gpv.setHeadingName("To Department");
	gpv.setHideOrShow("false");
	gpv.setWidth(90);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("category");
	gpv.setHeadingName("Category");
	gpv.setHideOrShow("false");
	gpv.setWidth(95);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedby");
	gpv.setHeadingName("Feedback By");
	gpv.setFormatter("feedByDetail");
	gpv.setHideOrShow("true");
	gpv.setWidth(105);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("bydept");
	gpv.setHeadingName("By Department");
	gpv.setHideOrShow("true");
	gpv.setWidth(105);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedbacktype");
	gpv.setHeadingName("Feedback Type");
	gpv.setHideOrShow("true");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("lodgemode");
	gpv.setHeadingName("Lodge Mode");
	gpv.setFormatter("lodgeUserDetail");
	gpv.setHideOrShow("true");
	gpv.setWidth(40);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("closemode");
	gpv.setHeadingName("Close Mode");
	gpv.setHideOrShow("true");
	gpv.setFormatter("editCloseMode");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("sms_flag");
	gpv.setHeadingName("SMS");
	gpv.setHideOrShow("false");
	gpv.setWidth(40);

	gpv.setFormatter("resendSMS");
	viewPageColumns.add(gpv);

	return SUCCESS;
	}
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
/*
 * Kamlesh for CPS column view of activity board 
 * on 10 Jun
 */
	public String viewActivityBoardColumnCPS()
	{
	try
	{
	//String userRights = session.get("userRights").toString();
	viewPageColumns = new ArrayList<GridDataPropertyView>();
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("action");
	gpv.setHeadingName("Action");
	gpv.setFormatter("takeAction");
	gpv.setHideOrShow("false");
	gpv.setWidth(35);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("acc_manager");
	gpv.setHeadingName("Account Manager");
	gpv.setHideOrShow("false");
	gpv.setWidth(110);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("corporate_name");
	gpv.setHeadingName("Corporate Name");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("corp_type");
	gpv.setHeadingName("Corporate Type");
	gpv.setHideOrShow("false");
	gpv.setWidth(40);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("TAT");
	gpv.setHeadingName("TAT");
	//	gpv.setFormatter("tatDetail");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("level");
	gpv.setHeadingName("Level");
	//	gpv.setFormatter("tatDetail");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("status");
	gpv.setHeadingName("Status");
	gpv.setHideOrShow("false");
	gpv.setWidth(95);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("pat_name");
	gpv.setHeadingName("Patient Name");
	gpv.setHideOrShow("false");
	gpv.setWidth(140);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("uhid");
	gpv.setHeadingName("UHID");
	gpv.setHideOrShow("true");
	gpv.setWidth(60);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("service_name");
	gpv.setHeadingName("Service");
	//gpv.setFormatter("viewServices");
	gpv.setHideOrShow("false");
	gpv.setWidth(70);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("location");
	gpv.setHeadingName("Location");
	gpv.setHideOrShow("false");
	//gpv.setFormatter("viewLocation");
	gpv.setWidth(120);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("preffered ");
	gpv.setHeadingName("Preffered Date/Time");
	gpv.setHideOrShow("false");
	gpv.setWidth(40);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("phase1allot");
	gpv.setHeadingName("Alloted To OCC");
	gpv.setHideOrShow("false");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("occdatetime");
	gpv.setHeadingName("OCC Date/Time");
	gpv.setHideOrShow("false");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("time");
	gpv.setHeadingName("Timer OCC");
	gpv.setHideOrShow("false");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("TATOCC");
	gpv.setHeadingName("TAT OCC");
	gpv.setHideOrShow("false");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("remark");
	gpv.setHeadingName("Remark");
	gpv.setHideOrShow("false");
	gpv.setWidth(65);
	viewPageColumns.add(gpv);
	
	return SUCCESS;
	}
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}



	public String beforeViewActivityHistoryData()
	{
	try
	{
	viewPageColumns = new ArrayList<GridDataPropertyView>();
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("ticketno");
	gpv.setHeadingName("Ticket No.");
	gpv.setFormatter("statusDetail");
	gpv.setHideOrShow("false");
	gpv.setWidth(50);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("subcatg");
	gpv.setHeadingName("Sub-Category");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("brief");
	gpv.setHeadingName("Feedback-Brief");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("openat");
	gpv.setHeadingName("Open At");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("status");
	gpv.setHeadingName("Status");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("closeat");
	gpv.setHeadingName("Close At");
	gpv.setHideOrShow("false");
	gpv.setWidth(130);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("totalbreakdown");
	gpv.setHeadingName("Total Breakdown");
	gpv.setWidth(50);
	gpv.setHideOrShow("false");
	viewPageColumns.add(gpv);

	/*gpv = new GridDataPropertyView();
	gpv.setColomnName("totaltime");
	gpv.setHeadingName("Total Time");
	gpv.setWidth(50);
	gpv.setHideOrShow("false");
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("totalworkingtime");
	gpv.setHeadingName("Total Working Time");
	gpv.setWidth(50);
	gpv.setHideOrShow("false");
	viewPageColumns.add(gpv);
*/
	gpv = new GridDataPropertyView();
	gpv.setColomnName("actionby");
	gpv.setHeadingName("Action By");
	gpv.setWidth(120);
	gpv.setHideOrShow("false");
	viewPageColumns.add(gpv);
 
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("remarks");
	gpv.setHeadingName("Remarks");
	gpv.setHideOrShow("false");
	gpv.setWidth(120);
	viewPageColumns.add(gpv);

	 

	gpv = new GridDataPropertyView();
	gpv.setColomnName("level");
	gpv.setHeadingName("Level");
	gpv.setHideOrShow("false");
	gpv.setWidth(60);
	viewPageColumns.add(gpv);

	 

	return SUCCESS;
	}
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}

	}
//testing required
	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	columnMap = new LinkedHashMap<String, String>();
	
	columnMap.put("history.feedId", "Data Id");
	columnMap.put("feedback.ticket_no as Ticket_No", "Ticket No");
	columnMap.put("dept1.deptName AS To_department ", "To Department");
	columnMap.put("dept.deptName AS By_Department", "By Department");
	
	columnMap.put("feedtype.fbType as Category", "Category");
	
	columnMap.put("subcatg.subCategoryName as Sub_Category", "Sub-category");
	columnMap.put("feedback.feed_brief as Brief", "Feddback-Brief");
	/*if(feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{*/
	/*}
	else
	{*/	
	columnMap.put("feedback.open_date as Open_Date", "Open Date");
	columnMap.put("feedback.open_time as Open_Time", " Open Time");
	/*}*/
	columnMap.put("feedback.feed_by as Feedback_By", "Feedback By");
	columnMap.put("emp.empName AS Allot_To", "Alloted To");
	columnMap.put("feedback.feed_by_mobno as Feed_By_Mobile", " Mobile No");
	columnMap.put("feedback.feed_by_emailid as Feed_By_EmailId", "Email Id");
	
	  	columnMap.put("floor.roomno", "Location");
	columnMap.put("flr.floorname", "Floor");
	 	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	 	columnMap.put("history.status as Status", "Status");
	 	else
	 	columnMap.put("feedback.status as Status", "Current Status");
	columnMap.put("feedback.level as TAT_Status", "TAT Status");
	 	columnMap.put("feedback.escalation_date as Escalation_Date", "Escalation Date");
	columnMap.put("feedback.escalation_time as Escalation_Time", "Escalation Time");
	columnMap.put("emp1.empName as Action_By", " Action By");
	columnMap.put("history.action_remark as Action_Remarks", " Action Remarks");
	
	 
	//if(feedStatus.equalsIgnoreCase("Resolved") ||feedStatus.equalsIgnoreCase("ignore") || feedStatus.equalsIgnoreCase("Re-assign") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	if(feedStatus.equalsIgnoreCase("ignore") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	 	}
	/*	else if(feedStatus.equalsIgnoreCase("Snooze") ||feedStatus.equalsIgnoreCase("All") || feedStatus.equalsIgnoreCase("-1") || feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	columnMap.put("history.sn_upto_date as Parked_Upto_Date", "Parked Upto Date"); 
	columnMap.put("history.sn_upto_time as Parked_Upto_Time", "Parked Upto Time"); 
	}*/
	
	columnMap.put("history.action_by   as First_Re-Opened_action_by", "First_Re-Opened By");
	columnMap.put("history.action_date as First_Re-Opened_Action_Date", "First_Re-Opened Date");
	columnMap.put("history.action_time as First_Re-Opened_Action_Time", "First_Re-Opened Time");
	
	columnMap.put("history.action_by   as Second_Re-Opened_action_by", "Second_Re-Opened By");
	columnMap.put("history.action_date as Second_Re-Opened_Action_Date", "Second_Re-Opened Date");
	columnMap.put("history.action_time as Second_Re-Opened_Action_Time", "Second_Re-Opened Time");
	
	columnMap.put("history.action_by   as Third_Re-Opened_action_by", "Third_Re-Opened By");
	columnMap.put("history.action_date as Third_Re-Opened_Action_Date", "Third_Re-Opened Date");
	columnMap.put("history.action_time as Third_Re-Opened_Action_Time", "Third_Re-Opened Time");
	
	columnMap.put("history.action_by   as Fourth_Re-Opened_action_by", "Fourth_Re-Opened By");
	columnMap.put("history.action_date as Fourth_Re-Opened_Action_Date", "Fourth_Re-Opened Date");
	columnMap.put("history.action_time as Fourth_Re-Opened_Action_Time", "Fourth_Re-Opened Time");
	
	columnMap.put("history.action_by   as Fifth_Re-Opened_action_by", "Fifth_Re-Opened By");
	columnMap.put("history.action_date as Fifth_Re-Opened_Action_Date", "Fifth_Re-Opened Date");
	columnMap.put("history.action_time as Fifth_Re-Opened_Action_Time", "Fifth_Re-Opened Time");
	
	
	columnMap.put("history.action_date as First_Action_Date", "First Parked Action Date");
	columnMap.put("history.action_time as First_Action_Time", "First Parked Action Time");
	columnMap.put("history.action_duration as First_Action_Duration", "First Parked Action Duration");
	columnMap.put("history.sn_upto_date as First_Parked_Upto_Date", "First Parked Upto Date");
	columnMap.put("history.sn_upto_time as First_Parked_Upto_Time", "First Parked Upto Time");
	
	columnMap.put("history.action_date as Second_Action_Date", "Second Parked Action Date");
	columnMap.put("history.action_time as Second_Action_Time", "Second Parked Action Time");
	columnMap.put("history.action_duration as Second_Action_Duration", "Second Parked Action Duration");
	columnMap.put("history.sn_upto_date as Second_Parked_Upto_Date", "Second Parked Upto Date");
	columnMap.put("history.sn_upto_time as Second_Parked_Upto_Time", "Second Parked Upto Time");
	
	
	columnMap.put("history.action_date as First_actDate", "First Esclated On");
	columnMap.put("history.action_time as First_actTime", "First Escalate Time");
	columnMap.put("history.action_by as First_escalate_to", "First Esclated to");
	
	columnMap.put("history.action_date as Second_actDate", "Second Esclated On");
	columnMap.put("history.action_time as Second_actTime", "Second Escalate Time");
	columnMap.put("history.action_by as Second_escalate_to", "Second Esclated to");
	
	columnMap.put("history.action_date as Third_actDate", "Third Esclated On");
	columnMap.put("history.action_time as Third_actTime", "Third Escalate Time");
	columnMap.put("history.action_by as Third_escalate_to", "Third Esclated to");
	
	columnMap.put("history.action_date as Fourth_actDate", "Fourth Esclated On");
	columnMap.put("history.action_time as Fourth_actTime", "Fourth Escalate Time");
	columnMap.put("history.action_by as Fourth_escalate_to", "Fourth Esclated to");
	
	columnMap.put("history.action_date as Fifth_actDate", "Fifth Esclated On");
	columnMap.put("history.action_time as Fifth_actTime", "Fifth Escalate Time");
	columnMap.put("history.action_by as Fifth_escalate_to", "Fifth Esclated to");
	
	/*	columnMap.put("history.action_date as FirstSnooze_actDate", "Fiest Snooze On");
	columnMap.put("history.action_time as FirstSnooze_actTime", "First Snooze Time");
	columnMap.put("history.action_by as FirstSnooze_action_by", "First Snooze By");
	
	columnMap.put("history.action_date as SecondSnooze_actDate", "Second Snooze On");
	columnMap.put("history.action_time as SecondSnooze_actTime", "Second Snooze Time");
	columnMap.put("history.action_by as SecondSnooze_action_by", "Second Snooze By");*/

	columnMap.put("history.action_by as resolve1_action_by", "Resolve1 Action By");
	columnMap.put("history.action_date as Resolve1_Action_Date", "Resolve1 Action Date");
	columnMap.put("history.action_time as Resolve1_Action_Time", "Resolve1 Action Time");
	columnMap.put("history.action_duration as Resolve1_Action_Duration","Resolve1 Action Duration"); 
	
	columnMap.put("history.action_by as Resolve2_Action_By", "Resolve2 Action By");
	columnMap.put("history.action_date as Resolve2_Action_Date", "Resolve2 Action Date");
	columnMap.put("history.action_time as Resolve2_Action_Time", "Resolve2 Action Time");
	columnMap.put("history.action_duration as Resolve2_Action_Duration","Resolve2 Action Duration"); 
	
	columnMap.put("history.action_by as Resolve3_Action_By", "Resolve3 Action By");
	columnMap.put("history.action_date as Resolve3_Action_Date", "Resolve3 Action Date");
	columnMap.put("history.action_time as Resolve3_Action_Time", "Resolve3 Action Time");
	columnMap.put("history.action_duration as Resolve3_Action_Duration","Resolve3 Action Duration"); 
	
	columnMap.put("history.action_by as Resolve4_Action_by", "Resolve4 Action By");	
	columnMap.put("history.action_date as Resolve4_Action_Date", "Resolve4 Action Date");
	columnMap.put("history.action_time as Resolve4_Action_Time", "Resolve4 Action Time");
	columnMap.put("history.action_duration as Resolve4_Action_Duration","Resolve4 Action Duration"); 
	
	columnMap.put("history.action_by as Resolve5_Action_by", "Resolve5 Action By");
	columnMap.put("history.action_date as Resolve5_Action_Date", "Resolve5 Action Date");
	columnMap.put("history.action_time as Resolve5_Action_Time", "Resolve5 Action Time");
	columnMap.put("history.action_duration as Resolve5_Action_Duration","Resolve5 Action Duration"); 
	
	columnMap.put("history.action_by as Resolve6_Action_by", "Resolve6 Action By");
	columnMap.put("history.action_date as Resolve6_Action_Date", "Resolve6 Action Date");
	columnMap.put("history.action_time as Resolve6_Action_Time", "Resolve6 Action Time");
	columnMap.put("history.action_duration as Resolve6_Action_Duration","Resolve6 Action Duration"); 
	
	//columnMap.put("resolution_time", "Resolution Time");
	
	  if (columnMap != null && columnMap.size() > 0)
	{
	session.put("columnMap", columnMap);
	}
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
	//Live running
	/*@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	columnMap = new LinkedHashMap<String, String>();
	
	columnMap.put("history.feedId", "Data Id");
	columnMap.put("feedback.ticket_no as Ticket_No", "Ticket No");
	columnMap.put("dept1.deptName AS To_department ", "To Department");
	columnMap.put("dept.deptName AS By_Department", "By Department");
	
	columnMap.put("feedtype.fbType as Category", "Category");
	
	columnMap.put("subcatg.subCategoryName as Sub_Category", "Sub-category");
	columnMap.put("feedback.feed_brief as Brief", "Feddback-Brief");
	if(feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{
	}
	else
	{	
	columnMap.put("feedback.open_date as Open_Date", "Open Date");
	columnMap.put("feedback.open_time as Open_Time", " Open Time");
	}
	columnMap.put("feedback.feed_by as Feedback_By", "Feedback By");
	columnMap.put("emp.empName AS Allot_To", "Alloted To");
	columnMap.put("feedback.feed_by_mobno as Feed_By_Mobile", " Mobile No");
	columnMap.put("feedback.feed_by_emailid as Feed_By_EmailId", "Email Id");
	
	  	columnMap.put("floor.roomno", "Location");
	columnMap.put("flr.floorname", "Floor");
	 	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	 	columnMap.put("history.status as Status", "Status");
	 	else
	 	columnMap.put("feedback.status as Status", "Current Status");
	columnMap.put("feedback.level as TAT_Status", "TAT Status");
	 	columnMap.put("feedback.escalation_date as Escalation_Date", "Escalation Date");
	columnMap.put("feedback.escalation_time as Escalation_Time", "Escalation Time");
	columnMap.put("emp1.empName as Action_By", " Action By");
	columnMap.put("history.action_remark as Action_Remarks", " Action Remarks");
	
	 
	if(feedStatus.equalsIgnoreCase("Resolved") ||feedStatus.equalsIgnoreCase("ignore") || feedStatus.equalsIgnoreCase("Re-assign") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	 	}
	else if(feedStatus.equalsIgnoreCase("Snooze") ||feedStatus.equalsIgnoreCase("All") || feedStatus.equalsIgnoreCase("-1") || feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	columnMap.put("history.sn_upto_date as Parked_Upto_Date", "Parked Upto Date"); 
	columnMap.put("history.sn_upto_time as Parked_Upto_Time", "Parked Upto Time"); 
	}
	
	columnMap.put("history.action_by   as First_Re-Opened_action_by", "First_Re-Opened By");
	columnMap.put("history.action_date as First_Re-Opened_Action_Date", "First_Re-Opened Date");
	columnMap.put("history.action_time as First_Re-Opened_Action_Time", "First_Re-Opened Time");
	
	columnMap.put("history.action_by   as Second_Re-Opened_action_by", "Second_Re-Opened By");
	columnMap.put("history.action_date as Second_Re-Opened_Action_Date", "Second_Re-Opened Date");
	columnMap.put("history.action_time as Second_Re-Opened_Action_Time", "Second_Re-Opened Time");
	
	columnMap.put("history.action_date as First_Action_Date", "First Parked Action Date");
	columnMap.put("history.action_time as First_Action_Time", "First Parked Action Time");
	columnMap.put("history.action_duration as First_Action_Duration", "First Snooze Action Duration");
	columnMap.put("history.sn_upto_date as First_Parked_Upto_Date", "First Parked Upto Date");
	columnMap.put("history.sn_upto_time as First_Parked_Upto_Time", "First Parked Upto Time");
	
	columnMap.put("history.action_date as Second_Action_Date", "Second Parked Action Date");
	columnMap.put("history.action_time as Second_Action_Time", "Second Parked Action Time");
	columnMap.put("history.action_duration as Second_Action_Duration", "Second Snooze Action Duration");
	columnMap.put("history.sn_upto_date as Second_Parked_Upto_Date", "Second Parked Upto Date");
	columnMap.put("history.sn_upto_time as Second_Parked_Upto_Time", "Second Parked Upto Time");
	
	
	columnMap.put("history.action_date as First_actDate", "First Esclated On");
	columnMap.put("history.action_time as First_actTime", "First Escalate Time");
	columnMap.put("history.action_by as First_escalate_to", "First Esclated to");
	
	columnMap.put("history.action_date as Second_actDate", "Second Esclated On");
	columnMap.put("history.action_time as Second_actTime", "Second Escalate Time");
	columnMap.put("history.action_by as Second_escalate_to", "Second Esclated to");
	
	columnMap.put("history.action_date as Third_actDate", "Third Esclated On");
	columnMap.put("history.action_time as Third_actTime", "Third Escalate Time");
	columnMap.put("history.action_by as Third_escalate_to", "Third Esclated to");
	
	columnMap.put("history.action_date as Fourth_actDate", "Fourth Esclated On");
	columnMap.put("history.action_time as Fourth_actTime", "Fourth Escalate Time");
	columnMap.put("history.action_by as Fourth_escalate_to", "Fourth Esclated to");
	
	columnMap.put("history.action_date as Fifth_actDate", "Fifth Esclated On");
	columnMap.put("history.action_time as Fifth_actTime", "Fifth Escalate Time");
	columnMap.put("history.action_by as Fifth_escalate_to", "Fifth Esclated to");
	
	columnMap.put("history.action_date as FirstSnooze_actDate", "First Snooze On");
	columnMap.put("history.action_time as FirstSnooze_actTime", "First Snooze Time");
	columnMap.put("history.action_by as FirstSnooze_action_by", "First Snooze By");
	
	columnMap.put("history.action_date as SecondSnooze_actDate", "Second Snooze On");
	columnMap.put("history.action_time as SecondSnooze_actTime", "Second Snooze Time");
	columnMap.put("history.action_by as SecondSnooze_action_by", "Second Snooze By");
	
	columnMap.put("resolution_time", "Resolution Time");
	
	  if (columnMap != null && columnMap.size() > 0)
	{
	session.put("columnMap", columnMap);
	}
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
	}*/

	/*@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String downloadExcel()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	excelFileName = "Feedback Status Detail";

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
	List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

	StringBuilder query = new StringBuilder();
	if (empDataList != null && empDataList.size() > 0)
	{
	for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	userMobno = CUA.getValueWithNullCheck(object[1]);
	toDepart = object[3].toString();
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
	query.append("Select osh.action_date,osh.action_time,emp.empName,feedId from feedback_status_history as osh  LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId INNER JOIN employee_basic AS emp ON emp.id=osh.action_by where osh.status like 'Escala%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List escdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	query = new StringBuilder();
	query.append(" Select max(osh.action_date),max(osh.action_time),max(action_by),feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId where osh.status like 'Resolv%' group by feedId ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List resdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	
	query = new StringBuilder();
	query.append(" Select osh.action_date,osh.action_time,action_duration,sn_upto_date,sn_upto_time,feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId where osh.status like 'Snoo%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List snoozedataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	
	query = new StringBuilder();
	query.append(" Select action_by,osh.action_date,osh.action_time,feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId where osh.status like 'Re-Open%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List reopdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	
	query = new StringBuilder();
	query.append("select distinct ");
	int i = 0;
	for (Iterator it = keyList.iterator(); it.hasNext();)
	{
	Object obdata = (Object) it.next();
	if(!obdata.toString().equalsIgnoreCase("history.action_date as First_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Second_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Fourth_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Third_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Fifth_actDate")
	   && !obdata.toString().equalsIgnoreCase("history.action_time as First_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Second_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Third_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Fourth_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Fifth_actTime")	
	   && !obdata.toString().equalsIgnoreCase("history.action_by as First_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Second_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Third_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to")
	   && !obdata.toString().equalsIgnoreCase("resolution_time") && !obdata.toString().equalsIgnoreCase("history.action_date as First_Action_Date") &&  !obdata.toString().equalsIgnoreCase("history.action_time as First_Action_Time") 
	   && !obdata.toString().equalsIgnoreCase("history.action_duration as First_Action_Duration") &&  !obdata.toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time") &&  !obdata.toString().equalsIgnoreCase("history.action_date as Second_Action_Date") &&  !obdata.toString().equalsIgnoreCase("history.action_time as Second_Action_Time") 
	   && !obdata.toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration") &&  !obdata.toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time") && !obdata.toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by") 
	   && !obdata.toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date") && !obdata.toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time")
	   && !obdata.toString().equalsIgnoreCase("history.action_date as FirstSnooze_actDate") && !obdata.toString().equalsIgnoreCase("history.action_time as FirstSnooze_actTime") 
	   && !obdata.toString().equalsIgnoreCase("history.action_by as FirstSnooze_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as SecondSnooze_actDate")
	   && !obdata.toString().equalsIgnoreCase("history.action_time as SecondSnooze_actTime") && !obdata.toString().equalsIgnoreCase("history.action_by as SecondSnooze_action_by")
	)
	{
	if (obdata != null)
	{
	if (i < keyList.size() - 1)
	{
	if(feedStatus.equalsIgnoreCase("Resolved") ||feedStatus.equalsIgnoreCase("ignore") || feedStatus.equalsIgnoreCase("Re-assign") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	{
	if(obdata.toString().equalsIgnoreCase("history.action_duration as Action_Duration"))
	{
	query.append(obdata.toString());
	}else{
	query.append(obdata.toString() + ",");
	}
	}
	else{
	if(obdata.toString().equalsIgnoreCase("history.sn_upto_time as Parked_Upto_Time"))
	{
	query.append(obdata.toString());
	}else{
	query.append(obdata.toString() + ",");
	}
	}
	}
	else
	{
	query.append(obdata.toString());
	}
	}
	}
	i++;
	}
	query.append(" FROM feedback_status_new as feedback");
	query.append(" LEFT JOIN feedback_status_history AS history ON history.feedId = feedback.id");
	query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");  
	query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");       
	query.append(" LEFT JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");             
	query.append(" LEFT JOIN department AS dept ON dept.id = feedback.by_dept_subdept");          
	query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
	query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id = history.action_by");
	query.append(" LEFT JOIN department AS dept1 ON dept1.id = feedback.to_dept_subdept");
	query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feedback.location");
	query.append(" LEFT JOIN floor_detail AS flr ON flr.id = floor.floorname");
	 
	query.append(" WHERE ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append("  feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append("  feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")))
	{
	query.append(" AND fstatus.by_dept_subdept = " + fromDepart);
	}
	else
	{
	String depId = getFromDeptId();
	if (!depId.equals("0"))
	{
	query.append(" AND fstatus.by_dept_subdept IN(" + depId + ")");
	}
	}

	if (fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");

	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append(" AND  feedback.by_dept_subdept = " + fromDepart);
	}
	
	String depId1 = getDeptRightsId();
	//System.out.println("LISt show :  "+depId1);
	if (depId1.length()>=1)
	{
	query.append(" AND dept1.id IN(" + depId1 + ")");
	}

	if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.to_dept_subdept IN(" + depId1 + ")");
	else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.feed_by_mobno =" + userMobno + "");
	else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.allot_to =" + empId + "");
	else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND  feedback.by_dept_subdept IN (" + depId1 + ")");
	
	if(searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined"))
	{
	if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	if (feedStatus.equalsIgnoreCase("Escalated"))
	{
	query.append("  AND feedback.level != 'Level1'");
	}
	else if (feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{
	query.append(" AND history.status = '" + feedStatus + "'  ");
	}
	else if (feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	query.append(" AND history.status = 'Snooze'  ");
	}
	else if(!feedStatus.equalsIgnoreCase("All"))
	{
	query.append(" AND feedback.status = '" + feedStatus + "'");
	}
	}
	else 
	{
	//	query.append(" AND feedback.status NOT IN ('Ignore') ");
	}
	}

	if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level = '" + escLevel + "'");
	if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append("  AND feedback.level = 'Level1' ");
	else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level != 'Level1' ");

	if (complianId != null && !complianId.equalsIgnoreCase("") && !complianId.equalsIgnoreCase("undefined"))
	{
	query.append(" AND feedback.id=" + complianId);
	}

	if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	// add search query based on the search operation
	query.append("AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");
	}

	if (getSearchField() != null && getSearchString() != null && getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	query.append("AND (feedback.ticket_no  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_date  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_time  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.level  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.feed_brief  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.location  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.status  like '" + getSearchString() + "%'");
	query.append(" OR floor.roomno  like '%" + getSearchString() + "%'");
	query.append(" OR emp.empname  like '%" + getSearchString() + "%'");
	query.append(" OR dept1.deptName  like '%" + getSearchString() + "%'");
	query.append(" OR feedtype.fbType  like '%" + getSearchString() + "%'");
	query.append(" OR subcatg.subCategoryName  like '%" + getSearchString() + "%') ");
	}
	if (!"-1".equalsIgnoreCase(getDept()) && null != getDept() && !"".equalsIgnoreCase(getDept()))
	{
	query.append(" AND dept1.id  like '%" + getDept() + "%'");
	if (!"-1".equalsIgnoreCase(getCategory()) && null != getCategory() && !"".equalsIgnoreCase(getCategory()))
	{
	query.append(" AND subcatg.id  like '%" + getCategory() + "%'");
	}
	 
	}
	 
	//	query.append(" AND history.status='Re-OpenedR' ");
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	query.append(" order by history.id ");
	else if(feedStatus.equalsIgnoreCase("Re-Opened") || feedStatus.equalsIgnoreCase("Re-assign"))
	query.append(" group by feedback.ticket_no order by feedback.id ");
	//else
	//	query.append(" AND history.status=feedback.status order by feedback.id limit 10");
	//	query.append(" limit 10 ");
	// System.out.println("sel id >>>>>>>>>>>   "+getSel_id());
	System.out.println("query%%%%%   "+query);
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	String orgDetail = (String) session.get("orgDetail");
	String orgName = "";
	if (orgDetail != null && !orgDetail.equals(""))
	{
	orgName = orgDetail.split("#")[0];
	}
	if (dataList != null && titleList != null && keyList != null)
	{

	excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace,escdataList,resdataList,snoozedataList,reopdataList);
	}
	if (excelFileName != null)
	{
	excelStream = new FileInputStream(excelFileName);
	}
	 	}
	returnResult = SUCCESS;
	}
	else
	{
	addActionMessage("There are some error in data!!!!");
	returnResult = ERROR;
	}
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
*/
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String downloadExcel()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	excelFileName = "Feedback Status Detail";

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
	List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

	StringBuilder query = new StringBuilder();
	if (empDataList != null && empDataList.size() > 0)
	{
	for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	userMobno = CUA.getValueWithNullCheck(object[1]);
	toDepart = object[3].toString();
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
	query.append("Select osh.action_date,osh.action_time,emp.empName,feedId from feedback_status_history as osh  LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId INNER JOIN employee_basic AS emp ON emp.id=osh.action_by where osh.status like 'Escala%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List escdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	query = new StringBuilder();
	query.append(" Select emp.empName,osh.action_date,osh.action_time,osh.action_duration,feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId INNER JOIN employee_basic AS emp ON emp.id=osh.action_by where osh.status like 'Resolv%'");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	System.out.println(" Resolved Query:::::::::::::; "+query.toString());
	List resdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	
	query = new StringBuilder();
	query.append(" Select osh.action_date,osh.action_time,action_duration,sn_upto_date,sn_upto_time,feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId where osh.status like 'Snoo%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List snoozedataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	
	
	query = new StringBuilder();
	query.append(" Select emp.empName,osh.action_date,osh.action_time,feedId from feedback_status_history as osh LEFT JOIN feedback_status_new AS feedback ON feedback.id = osh.feedId INNER JOIN employee_basic AS emp ON emp.id=osh.action_by where osh.status like 'Re-Open%' ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append(" and feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append(" and feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
	}
	query.append(" order by osh.id ");
	List reopdataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query=null;
	
	query = new StringBuilder();
	query.append("select distinct ");
	int i = 0;
	for (Iterator it = keyList.iterator(); it.hasNext();)
	{
	Object obdata = (Object) it.next();
	if(!obdata.toString().equalsIgnoreCase("history.action_date as First_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Second_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Fourth_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Third_actDate") && !obdata.toString().equalsIgnoreCase("history.action_date as Fifth_actDate")
	   && !obdata.toString().equalsIgnoreCase("history.action_time as First_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Second_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Third_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Fourth_actTime") && !obdata.toString().equalsIgnoreCase("history.action_time as Fifth_actTime")	
	   && !obdata.toString().equalsIgnoreCase("history.action_by as First_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Second_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Third_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to") && !obdata.toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to")
	   && !obdata.toString().equalsIgnoreCase("resolution_time") && !obdata.toString().equalsIgnoreCase("history.action_date as First_Action_Date") &&  !obdata.toString().equalsIgnoreCase("history.action_time as First_Action_Time") 
	   && !obdata.toString().equalsIgnoreCase("history.action_duration as First_Action_Duration") &&  !obdata.toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time") &&  !obdata.toString().equalsIgnoreCase("history.action_date as Second_Action_Date") &&  !obdata.toString().equalsIgnoreCase("history.action_time as Second_Action_Time") 
	   && !obdata.toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration") &&  !obdata.toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time") 
	   
	   && !obdata.toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date")  && !obdata.toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time")
	   && !obdata.toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date") && !obdata.toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time")
	   && !obdata.toString().equalsIgnoreCase("history.action_by   as Third_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as Third_Re-Opened_Action_Date")  && !obdata.toString().equalsIgnoreCase("history.action_time as Third_Re-Opened_Action_Time")
	   && !obdata.toString().equalsIgnoreCase("history.action_by   as Fourth_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as Fourth_Re-Opened_Action_Date") && !obdata.toString().equalsIgnoreCase("history.action_time as Fourth_Re-Opened_Action_Time")
	   && !obdata.toString().equalsIgnoreCase("history.action_by   as Fifth_Re-Opened_action_by") && !obdata.toString().equalsIgnoreCase("history.action_date as Fifth_Re-Opened_Action_Date")  && !obdata.toString().equalsIgnoreCase("history.action_time as Fifth_Re-Opened_Action_Time")
	
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve1_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve1_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve1_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve1_Action_Duration")	
	
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve2_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve2_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve2_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve2_Action_Duration")	
	   
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve3_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve3_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve3_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve3_Action_Duration")	
	   
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve4_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve4_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve4_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve4_Action_Duration")	
	   
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve5_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve5_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve5_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve5_Action_Duration")	
	   
	   && !obdata.toString().equalsIgnoreCase("history.action_by as Resolve6_Action_By") && !obdata.toString().equalsIgnoreCase("history.action_date as Resolve6_Action_Date") 
	   && !obdata.toString().equalsIgnoreCase("history.action_time as Resolve6_Action_Time") && !obdata.toString().equalsIgnoreCase("history.action_duration as Resolve6_Action_Duration")	
	)
	{
	if (obdata != null)
	{
	if (i < keyList.size() - 1)
	{
	if(feedStatus.equalsIgnoreCase("ignore") || feedStatus.equalsIgnoreCase("Re-assign") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	{
	if(obdata.toString().equalsIgnoreCase("history.action_duration as Action_Duration"))
	{
	query.append(obdata.toString());
	}else{
	query.append(obdata.toString() + ",");
	}
	}
	else{
	if(obdata.toString().equalsIgnoreCase("history.action_remark as Action_Remarks"))
	{
	query.append(obdata.toString());
	}else{
	query.append(obdata.toString() + ",");
	}
	}
	}
	else
	{
	query.append(obdata.toString());
	}
	}
	}
	i++;
	}
	query.append(" FROM feedback_status_new as feedback");
	query.append(" LEFT JOIN feedback_status_history AS history ON history.feedId = feedback.id");
	query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");  
	query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");       
	query.append(" LEFT JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");             
	query.append(" LEFT JOIN department AS dept ON dept.id = feedback.by_dept_subdept");          
	query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
	query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id = history.action_by");
	query.append(" LEFT JOIN department AS dept1 ON dept1.id = feedback.to_dept_subdept");
	query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feedback.location");
	query.append(" LEFT JOIN floor_detail AS flr ON flr.id = floor.floorname");
	 
	query.append(" WHERE ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append("  feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append("  feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")))
	{
	query.append(" AND fstatus.by_dept_subdept = " + fromDepart);
	}
	else
	{
	String depId = getFromDeptId();
	if (!depId.equals("0"))
	{
	query.append(" AND fstatus.by_dept_subdept IN(" + depId + ")");
	}
	}

	if (fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");

	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append(" AND  feedback.by_dept_subdept = " + fromDepart);
	}
	
	String depId1 = getDeptRightsId();
	//System.out.println("LISt show :  "+depId1);
	if (depId1.length()>=1)
	{
	query.append(" AND dept1.id IN(" + depId1 + ")");
	}

	if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.to_dept_subdept IN(" + depId1 + ")");
	else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.feed_by_mobno =" + userMobno + "");
	else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.allot_to =" + empId + "");
	else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND  feedback.by_dept_subdept IN (" + depId1 + ")");
	
	if(searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined"))
	{
	if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	if (feedStatus.equalsIgnoreCase("Escalated"))
	{
	query.append("  AND feedback.level != 'Level1'");
	}
	else if (feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{
	query.append(" AND history.status = '" + feedStatus + "'  ");
	}
	else if (feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	query.append(" AND history.status = 'Snooze'  ");
	}
	else if(!feedStatus.equalsIgnoreCase("All"))
	{
	query.append(" AND feedback.status = '" + feedStatus + "'");
	}
	}
	else 
	{
	//	query.append(" AND feedback.status NOT IN ('Ignore') ");
	}
	}

	if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level = '" + escLevel + "'");
	if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append("  AND feedback.level = 'Level1' ");
	else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level != 'Level1' ");

	if (complianId != null && !complianId.equalsIgnoreCase("") && !complianId.equalsIgnoreCase("undefined"))
	{
	query.append(" AND feedback.id=" + complianId);
	}

	if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	// add search query based on the search operation
	query.append("AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");
	}

	if (getSearchField() != null && getSearchString() != null && getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	query.append("AND (feedback.ticket_no  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_date  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_time  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.level  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.feed_brief  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.location  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.status  like '" + getSearchString() + "%'");
	query.append(" OR floor.roomno  like '%" + getSearchString() + "%'");
	query.append(" OR emp.empname  like '%" + getSearchString() + "%'");
	query.append(" OR dept1.deptName  like '%" + getSearchString() + "%'");
	query.append(" OR feedtype.fbType  like '%" + getSearchString() + "%'");
	query.append(" OR subcatg.subCategoryName  like '%" + getSearchString() + "%') ");
	}
	if (!"-1".equalsIgnoreCase(getDept()) && null != getDept() && !"".equalsIgnoreCase(getDept()))
	{
	query.append(" AND dept1.id  like '%" + getDept() + "%'");
	if (!"-1".equalsIgnoreCase(getCategory()) && null != getCategory() && !"".equalsIgnoreCase(getCategory()))
	{
	query.append(" AND subcatg.id  like '%" + getCategory() + "%'");
	}
	 
	}
	 
	//	query.append(" AND history.status='Re-OpenedR' ");
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	query.append(" group by feedback.id order by history.id ");
	else if(feedStatus.equalsIgnoreCase("Re-Opened") || feedStatus.equalsIgnoreCase("Re-assign"))
	query.append(" group by feedback.ticket_no order by feedback.id ");
	else
	query.append(" AND history.status=feedback.status group by feedback.id order by feedback.id ");
	//else
	//	query.append(" AND history.status=feedback.status order by feedback.id limit 10");
	//	query.append(" limit 10 ");
	// System.out.println("sel id >>>>>>>>>>>   "+getSel_id());
	System.out.println("query%%%%%   "+query);
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	String orgDetail = (String) session.get("orgDetail");
	String orgName = "";
	if (orgDetail != null && !orgDetail.equals(""))
	{
	orgName = orgDetail.split("#")[0];
	}
	if (dataList != null && titleList != null && keyList != null)
	{

	excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace,escdataList,resdataList,snoozedataList,reopdataList);
	}
	if (excelFileName != null)
	{
	excelStream = new FileInputStream(excelFileName);
	}
	 	}
	returnResult = SUCCESS;
	}
	else
	{
	addActionMessage("There are some error in data!!!!");
	returnResult = ERROR;
	}
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
	/*	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	columnMap = new LinkedHashMap<String, String>();
	columnMap.put("feedback.ticket_no as Ticket_No", "Ticket No");
	columnMap.put("dept1.deptName AS To_department ", "To Department");
	columnMap.put("dept.deptName AS By_Department", "By Department");
	
	columnMap.put("feedtype.fbType as Category", "Category");
	
	columnMap.put("subcatg.subCategoryName as Sub_Category", "Sub-category");
	columnMap.put("feedback.feed_brief as Brief", "Feddback-Brief");
	if(feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{
	columnMap.put("history.action_date as Action_Date", "Re-Opened Date");
	columnMap.put("history.action_time as Action_Time", " Re-Opened Time");
	
	}
	else
	{	
	columnMap.put("feedback.open_date as Open_Date", "Open Date");
	columnMap.put("feedback.open_time as Open_Time", " Open Time");
	}
	columnMap.put("feedback.feed_by as Feedback_By", "Feedback By");
	columnMap.put("emp.empName AS Allot_To", "Alloted To");
	columnMap.put("feedback.feed_by_mobno as Feed_By_Mobile", " Mobile No");
	columnMap.put("feedback.feed_by_emailid as Feed_By_EmailId", "Email Id");
	
	  	columnMap.put("floor.roomno", "Location");
	columnMap.put("flr.floorname", "Floor");
	 	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	 	columnMap.put("history.status as Status", "Status");
	 	else
	 	columnMap.put("feedback.status as Status", "Current Status");
	columnMap.put("feedback.level as TAT_Status", "TAT Status");
	 	columnMap.put("feedback.escalation_date as Escalation_Date", "Escalation Date");
	columnMap.put("feedback.escalation_time as Escalation_Time", "Escalation Time");
	columnMap.put("emp1.empName as Action_By", " Action By");
	columnMap.put("history.action_remark as Action_Remarks", " Action Remarks");
	
	 
	if(feedStatus.equalsIgnoreCase("Resolved") ||feedStatus.equalsIgnoreCase("ignore") || feedStatus.equalsIgnoreCase("Re-assign") ||  feedStatus.equalsIgnoreCase("High-Priority"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	 	}
	else if(feedStatus.equalsIgnoreCase("Snooze") ||feedStatus.equalsIgnoreCase("All") || feedStatus.equalsIgnoreCase("-1") || feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	columnMap.put("history.action_date as Action_Date", " Action Date");
	columnMap.put("history.action_time as Action_Time", "Action Time");
	columnMap.put("history.action_duration as Action_Duration","Action Duration"); 
	columnMap.put("history.sn_upto_date as Parked_Upto_Date", "Parked Upto Date");
	columnMap.put("history.sn_upto_time as Parked_Upto_Time", "Parked Upto Time");
	}
	
	  if (columnMap != null && columnMap.size() > 0)
	{
	session.put("columnMap", columnMap);
	}
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String downloadExcel()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	excelFileName = "Feedback Status Detail";

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
	List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

	StringBuilder query = new StringBuilder();
	if (empDataList != null && empDataList.size() > 0)
	{
	for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	userMobno = CUA.getValueWithNullCheck(object[1]);
	toDepart = object[3].toString();
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
	}
	else
	{
	query.append(obdata.toString());
	}
	}
	i++;
	}
	query.append(" FROM feedback_status_new as feedback");
	query.append(" LEFT JOIN feedback_status_history AS history ON history.feedId = feedback.id");
	query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
	query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
	query.append(" LEFT JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
	query.append(" LEFT JOIN department AS dept ON dept.id = feedback.by_dept_subdept");
	query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
	query.append(" LEFT JOIN employee_basic AS emp1 ON emp1.id = history.action_by");
	 query.append(" LEFT JOIN department AS dept1 ON dept1.id = feedback.to_dept_subdept");
	query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feedback.location");
	query.append(" LEFT JOIN floor_detail AS flr ON flr.id = floor.floorname");
	 
	query.append(" WHERE ");
	if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
	{
	String str[] = maxDateValue.split("-");
	if (str[0].length() < 4)
	query.append("  feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
	else
	query.append("  feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")))
	{
	query.append(" AND fstatus.by_dept_subdept = " + fromDepart);
	}
	else
	{
	String depId = getFromDeptId();
	if (!depId.equals("0"))
	{
	query.append(" AND fstatus.by_dept_subdept IN(" + depId + ")");
	}
	}

	if (fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");

	}
	if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	query.append(" AND  feedback.by_dept_subdept = " + fromDepart);
	}
	
	String depId1 = getDeptRightsId();
	//System.out.println("LISt show :  "+depId1);
	if (depId1.length()>=1)
	{
	query.append(" AND dept1.id IN(" + depId1 + ")");
	}

	if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.to_dept_subdept IN(" + depId1 + ")");
	else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.feed_by_mobno =" + userMobno + "");
	else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.allot_to =" + empId + "");
	else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND  feedback.by_dept_subdept IN (" + depId1 + ")");
	
	if(searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined"))
	{
	if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	{
	if (feedStatus.equalsIgnoreCase("Escalated"))
	{
	query.append("  AND feedback.level != 'Level1'");
	}
	else if (feedStatus.equalsIgnoreCase("Re-OpenedR"))
	{
	query.append(" AND history.status = '" + feedStatus + "'  ");
	}
	else if (feedStatus.equalsIgnoreCase("SnoozeH"))
	{
	query.append(" AND history.status = 'Snooze'  ");
	}
	else if(!feedStatus.equalsIgnoreCase("All"))
	{
	query.append(" AND feedback.status = '" + feedStatus + "'");
	}
	}
	else 
	{
	query.append(" AND feedback.status NOT IN ('Resolved','Ignore') ");
	}
	}

	if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level = '" + escLevel + "'");
	if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append("  AND feedback.level = 'Level1' ");
	else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
	query.append(" AND feedback.level != 'Level1' ");

	if (complianId != null && !complianId.equalsIgnoreCase("") && !complianId.equalsIgnoreCase("undefined"))
	{
	query.append(" AND feedback.id=" + complianId);
	}

	if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	// add search query based on the search operation
	query.append("AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");
	}

	if (getSearchField() != null && getSearchString() != null && getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
	{
	query.append("AND (feedback.ticket_no  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_date  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.open_time  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.level  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.feed_brief  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.location  like '%" + getSearchString() + "%'");
	query.append(" OR feedback.status  like '" + getSearchString() + "%'");
	query.append(" OR floor.roomno  like '%" + getSearchString() + "%'");
	query.append(" OR emp.empname  like '%" + getSearchString() + "%'");
	query.append(" OR dept1.deptName  like '%" + getSearchString() + "%'");
	query.append(" OR feedtype.fbType  like '%" + getSearchString() + "%'");
	query.append(" OR subcatg.subCategoryName  like '%" + getSearchString() + "%') ");
	}
	if (!"-1".equalsIgnoreCase(getDept()) && null != getDept() && !"".equalsIgnoreCase(getDept()))
	{
	query.append(" AND dept1.id  like '%" + getDept() + "%'");
	if (!"-1".equalsIgnoreCase(getCategory()) && null != getCategory() && !"".equalsIgnoreCase(getCategory()))
	{
	query.append(" AND subcatg.id  like '%" + getCategory() + "%'");
	}
	 
	}
	 
	if (!"".equalsIgnoreCase(getSel_id()) && null != getSel_id())
	{
	query.append(" AND feedback.id IN (" + getSel_id() + ") ");
	}
	if(feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH"))
	query.append(" order by history.id ");
	else if(feedStatus.equalsIgnoreCase("Re-Opened") || feedStatus.equalsIgnoreCase("Re-assign"))
	query.append(" group by feedback.ticket_no order by feedback.id ");
	else
	query.append(" AND history.status=feedback.status order by feedback.id");
 
	// System.out.println("sel id >>>>>>>>>>>   "+getSel_id());
	//System.out.println("query%%%%%   "+query);
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
	excelStream = new FileInputStream(excelFileName);
	}
	 	}
	returnResult = SUCCESS;
	}
	else
	{
	addActionMessage("There are some error in data!!!!");
	returnResult = ERROR;
	}
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

	}*/
	

	@SuppressWarnings({ "rawtypes" })
	public String getDeptRightsId()
	{
	ActivityBoardHelper ABH = new ActivityBoardHelper();
	String empId = null;
	String userType = null;
	List dataList = null;
	String deptList="";
	String empIdofuser = (String) session.get("empIdofuser");
	userType = (String) session.get("userTpe");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	empId = empIdofuser.split("-")[1].trim();

	dataList = ABH.getDeptRightsByEmpId(connectionSpace, empId, userType);
	if (dataList != null && dataList.size() >= 1)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[1] != null)
	{
	deptList=object[1].toString();
	}
	}
	}
	 
	return deptList;
	}

	@SuppressWarnings({ "rawtypes", "null" })
	public String getFromDeptId()
	{
	ActivityBoardHelper ABH = new ActivityBoardHelper();
	String empId = null;
	String userType = null;
	List dataList = null;
	StringBuilder fromDepId = new StringBuilder();
	String empIdofuser = (String) session.get("empIdofuser");
	userType = (String) session.get("userTpe");
	if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
	return ERROR;
	empId = empIdofuser.split("-")[1].trim();

	dataList = ABH.getServiceDeptByEmpId(connectionSpace, empId, userType);
	if (dataList == null && dataList.size() == 0)
	{
	dataList = ABH.getAllDepartment(connectionSpace, empId);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && object[1] != null)
	{
	fromDepId.append(object[0].toString() + ",");
	}
	}
	}
	}
	fromDepId.append("0");
	return fromDepId.toString();
	}

	@SuppressWarnings("rawtypes")
	public String viewActivityHistoryData()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List<Object> tempList = new ArrayList<Object>();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	String  location = null;
	StringBuilder query = new StringBuilder();
	if (id != null)
	{
	query.append("SELECT feedback.location,feedback.id");
	query.append(" FROM feedback_status_new AS feedback");
	query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id= feedback.subCatg");
	query.append(" WHERE feedback.id=" + id);
	//System.out.println("Get Location : "+query.toString());
	List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	//feedby = CUA.getValueWithNullCheck(object[0]);
	//mobno = CUA.getValueWithNullCheck(object[1]);
	//catgId = CUA.getValueWithNullCheck(object[2]);
	location = CUA.getValueWithNullCheck(object[0]);
	}
	}
	dataList.clear();

	query.append("SELECT feedback.id, feedback.ticket_no, ");
	query.append("subcatg.subCategoryName, ");
	query.append("feedback.feed_brief, feedback.open_date, feedback.open_time,history.action_date, history.action_time,");
	query.append("history.action_duration, emp.empName as actionBy, history.action_remark,");
	query.append("feedback.level, feedback.status");
	 	query.append(" FROM feedback_status_new AS feedback ");
	 	query.append(" LEFT JOIN feedback_status_history AS history ON history.feedId = feedback.id");
	 	query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
	query.append(" LEFT JOIN employee_basic AS emp ON emp.id = history.action_by");
	query.append(" WHERE feedback.location ='" + location+"'");
	 	query.append(" group by  feedback.ticket_no ORDER BY feedback.id DESC");
	 	//System.out.println("Data Query : "+query.toString());
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	query.setLength(0);
	if (dataList != null && dataList.size() > 0)
	{
	List timeList = new ArrayList();
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();

	Map<String, Object> tempMap = new HashMap<String, Object>();
	tempMap.put("id", CUA.getValueWithNullCheck(object[0]));
	tempMap.put("ticketno", CUA.getValueWithNullCheck(object[1]));
	tempMap.put("subcatg", CUA.getValueWithNullCheck(object[2]));
	tempMap.put("brief", CUA.getValueWithNullCheck(object[3]));
	tempMap.put("openat", DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString().substring(0, object[5].toString().length() - 3));
	if (object[6] != null && object[7] != null)
	tempMap.put("closeat", DateUtil.convertDateToIndianFormat(object[6].toString()) + ", " + object[7].toString().substring(0, object[7].toString().length() - 3));
	else
	tempMap.put("closeat", "NA");

	tempMap.put("totalbreakdown", CUA.getValueWithNullCheck(object[8]));
	tempMap.put("actionby", CUA.getValueWithNullCheck(object[9]));
	 	tempMap.put("remarks", CUA.getValueWithNullCheck(object[10]));
	 	tempMap.put("level", CUA.getValueWithNullCheck(object[11]));
	tempMap.put("status", CUA.getValueWithNullCheck(object[12]));
	/*if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Resolved"))
	{

	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[6]), CUA.getValueWithNullCheck(object[14]), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[8]), object[6].toString(), object[7].toString(), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Pending"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Snooze"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("High Priority"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Re-OpenedR"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("Ignore"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[20]), CUA.getValueWithNullCheck(object[21]), cbt);
	}
	else if (CUA.getValueWithNullCheck(object[13]).equalsIgnoreCase("transfer"))
	{
	timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]), CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[22]), CUA.getValueWithNullCheck(object[23]), cbt);
	}
	if (timeList != null && timeList.size() > 0)
	{
	tempMap.put("totaltime", timeList.get(0).toString());
	tempMap.put("totalworkingtime", timeList.get(1).toString());
	}*/
	tempList.add(tempMap);

	}
	}
	setMasterViewProp(tempList);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTotalAndWorkingTime(String complainid, String openDate, String openTime, String snoozeDate, String snoozeTime, String snoozeUp2Date, String snoozeUp2Time, String snoozeDur, String calculateDate, String calculateTime, CommonOperInterface cbt)
	{
	List dataList = new ArrayList();
	try
	{

	String totalHrs = DateUtil.time_difference(openDate, openTime, calculateDate, calculateTime);
	String totalWorkingHrs = totalHrs;
	if (snoozeDate != null && !snoozeDate.equalsIgnoreCase("NA"))
	{
	if (DateUtil.compareDateTime(snoozeUp2Date + " " + snoozeUp2Time, calculateDate + " " + calculateTime))
	{
	totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, snoozeDur);
	}
	else
	{

	String tempTime = DateUtil.time_difference(calculateDate, calculateTime, snoozeDate, snoozeTime);
	totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
	}
	}
	String query = "SELECT request_on,request_at,approved_on,approved_at,status FROM seek_approval_detail WHERE complaint_id =" + complainid;
	List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (list != null && list.size() > 0)
	{
	for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	if (object2[4] != null && !object2[4].toString().equalsIgnoreCase("Pending"))
	{
	String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), object2[2].toString(), object2[3].toString());
	totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
	}
	else if (object2[4] != null && object2[4].toString().equalsIgnoreCase("Pending"))
	{
	String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), calculateDate, calculateTime);
	totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
	}
	}
	}

	dataList.add(totalHrs);
	dataList.add(totalWorkingHrs);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return dataList;
	}

	@SuppressWarnings("rawtypes")
	public String getComplaintActivityDeatil()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	ActivityBoardHelper helperObject = new ActivityBoardHelper();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List dataList = null;
	dataMap = new LinkedHashMap<String, String>();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	if (formaterOn != null)
	{
	if (formaterOn.equalsIgnoreCase("smsCode"))
	{
	dataMap.put("Ticket Status", "Code");
	dataMap.put("Close Ticket", "MDT CT <Ticket No.>*<Comment>");
	dataMap.put("Ignore Ticket", "MDT IT <Ticket No.>*<Comment>");
	dataMap.put("High-Priority Ticket", "MDT HT <Ticket No.>*<Comment>");
	dataMap.put("Noted Ticket", "MDT NT <Ticket No.>*<Comment>");
	}

	if (formaterOn.equalsIgnoreCase("smsResend"))
	{
	return "success";
	}
	}
	if (complianId != null && formaterOn != null && mainTable != null)
	{
	if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("feed_by"))
	{
	dataList = helperObject.getFeedByEmployeeDetail(connectionSpace, cbt, complianId);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
	dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
	dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
	dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
	}
	}
	else
	{
	dataMap.put("Name", "NA");
	dataMap.put("Mobile", "NA");
	dataMap.put("Email", "NA");
	dataMap.put("Department", "NA");
	}
	}
	else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("allot_to"))
	{
	dataList = helperObject.getAllotToEmployeeDetail(connectionSpace, cbt, complianId);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
	dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
	dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
	dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
	}
	}
	else
	{
	dataMap.put("Name", "NA");
	dataMap.put("Mobile", "NA");
	dataMap.put("Email", "NA");
	dataMap.put("Department", "NA");
	}
	}
	else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("lodgeMode"))
	{
	dataList = helperObject.getLodgerEmployeeDetail(connectionSpace, cbt, complianId);
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
	dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
	dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
	dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
	}
	}
	else
	{
	dataMap.put("Name", "NA");
	dataMap.put("Mobile", "NA");
	dataMap.put("Email", "NA");
	dataMap.put("Department", "NA");
	}
	}
	else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("status"))
	{
	dataList = helperObject.getTicketCycle(connectionSpace, cbt, complianId);
	if (dataList != null && dataList.size() > 0)
	{
	getCycleByOrder(dataList);
	}
	}
	else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("TAT"))
	{
	WorkingHourAction WHA = new WorkingHourAction();
	dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
	String addressingDate = null, addressingTime = "00:00", resolutionDate = null, resolutionTime = "00:00";
	if (dataList != null && dataList.size() > 0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	dataMap.put("Status", CUA.getValueWithNullCheck(object[0]));
	dataMap.put("Level", CUA.getValueWithNullCheck(object[1]));
	dataMap.put("Open Date & Time", DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, object[3].toString().length() - 3));
	List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[4].toString(), object[5].toString(), "Y", object[2].toString(), object[3].toString(), "HDM");
	addressingDate = dateTime.get(0);
	addressingTime = dateTime.get(1);
	resolutionDate = dateTime.get(2);
	resolutionTime = dateTime.get(3);
	dateTime.clear();
	dataMap.put("Addressing Date & Time", DateUtil.convertDateToIndianFormat(addressingDate) + ", " + addressingTime);
	dataMap.put("Resolution Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
	List empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "2", connectionSpace, cbt);
	StringBuilder empName = new StringBuilder();
	if (empList != null)
	{
	for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	if (object2[1] != null)
	empName.append(object2[1].toString() + ", ");
	}
	if (empName != null && empName.length() > 0)
	{
	dataMap.put("L-2 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
	dataMap.put("L-2 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
	}
	empList.clear();
	empName.setLength(0);

	dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
	resolutionDate = dateTime.get(0);
	resolutionTime = dateTime.get(1);

	empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "3", connectionSpace, cbt);

	if (empList != null && empList.size() > 0)
	{
	for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	if (object2[1] != null)
	empName.append(object2[1].toString() + ", ");
	}
	if (empName != null && empName.length() > 0)
	{
	dataMap.put("L-3 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
	dataMap.put("L-3 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
	}
	empList.clear();
	empName.setLength(0);

	dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
	resolutionDate = dateTime.get(0);
	resolutionTime = dateTime.get(1);
	empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "4", connectionSpace, cbt);

	if (empList != null && empList.size() > 0)
	{
	for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
	{
	Object[] object2 = (Object[]) iterator2.next();
	if (object2[1] != null)
	empName.append(object2[1].toString() + ", ");
	}
	if (empName != null && empName.length() > 0)
	{
	dataMap.put("L-4 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
	dataMap.put("L-4 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
	}
	empList.clear();
	empName.setLength(0);
	}
	}
	}
	}
	}
	}
	else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("closeMode"))
	{
	dataMap = new LinkedHashMap<String, String>();
	editableDataMap = new LinkedHashMap<String, String>();
	dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
	if (dataList != null && dataList.size() > 0)
	{
	dataMap = new ActionOnFeedback().getTicketDetails(complianId);
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	editableDataMap.put("Action Taken", CUA.getValueWithNullCheck(object[7]));
	editableDataMap.put("Resources Used", CUA.getValueWithNullCheck(object[8]));
	}
	}
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
 
	@SuppressWarnings("rawtypes")
	public List getCycleByOrder(List dataList)
	{
	FeedTicketCyclePojo  feed;
	String hisID="0";
	String hisID1="0";
	list= new ArrayList<FeedTicketCyclePojo>();
	//	List timeList = new ArrayList();
	//	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	 //String sndate=null,sntime=null,snuptodate=null,snuptotime=null,snduration=null;
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	feed = new FeedTicketCyclePojo();
	 Object[] object = (Object[]) iterator.next();
	 if (object[0] != null )
	{
	 if (object[0].toString().equalsIgnoreCase("Pending"))
	 feed.setStatus("Register");
	 else if (object[0].toString().equalsIgnoreCase("Transfer"))
	 feed.setStatus("Re-assign");
	 else if (object[0].toString().equalsIgnoreCase("Snooze"))
	 feed.setStatus("Parked");
	 else if (object[0].toString().equalsIgnoreCase("Re-OpenedR"))
	 feed.setStatus("Re-Opened");
	 else
	 feed.setStatus(object[0].toString());
	 
	}
	 else 
	 feed.setStatus("NA");
	
	 if (object[1] != null )
	{
	feed.setAction_date( DateUtil.convertDateToIndianFormat(object[1].toString()));
	}
	 else 
	 feed.setAction_date( "NA");
	
	 if (object[2] != null )
	{
	  	 if(object[2].toString().length()>5)
	  	 feed.setAction_time(object[2].toString().substring(0, object[2].toString().length() - 3));
	  	 else
	  	feed.setAction_time(object[2].toString());
	   
	}
	 else 
	 feed.setAction_time("NA");
	
	 if (object[3] != null )
	{
	feed.setSn_upto_date ( DateUtil.convertDateToIndianFormat(object[3].toString()));
	}
	 else
	 feed.setSn_upto_date ( "NA");
	 
	 if (object[4] != null )
	{
	feed.setSn_upto_time(object[4].toString());
	}
	 else
	 feed.setSn_upto_time("NA");
	 
	 if (object[5] != null )
	{
	feed.setAction_duration(object[5].toString());
	}
	 else
	 feed.setAction_duration("NA");
	 
	 if (object[6] != null )
	{
	feed.setTasktype(object[6].toString());
	}
	 else
	 feed.setTasktype("NA");
	 
	 if (object[7] != null )
	{
	 if (object[0].toString().equalsIgnoreCase("Escalated"))
	 feed.setActionby ("");
	 else
	feed.setActionby(object[7].toString());
	}
	 else
	 feed.setActionby("NA");
	 
	 if (object[8] != null )
	{
	 
	   if(object[0].toString().equalsIgnoreCase("Snooze"))
	    {
	    	feed.setAction_remark("Reason: "+object[8].toString()+", Parked Upto: "+feed.getSn_upto_date()+", "+feed.getSn_upto_time());
	    }
	    else
	    {
	    	feed.setAction_remark(object[8].toString());
	    }
	}
	 else
	 feed.setAction_remark("NA");
	 
	 if (object[18] != null )
	{
	  if (object[0].toString().equalsIgnoreCase("Pending"))
	  {
	  feed.setAction_remark(object[18].toString());
	  }
	 
	}
	  
	 
	 if (object[8]==null && object[14] != null && !object[14].toString().equalsIgnoreCase("Level1"))
	{
	 
	 feed.setAction_remark(object[14].toString()+": "+object[7].toString());
	 }
	  
	 if (object[11] != null )
	{
	feed.setOpen_date( DateUtil.convertDateToIndianFormat(object[11].toString()));
	}
	 else
	 feed.setOpen_date("NA");
	 
	 if (object[12] != null )
	{
	feed.setOpen_time(object[12].toString().substring(0, object[12].toString().length() - 3));
	}
	 else
	 feed.setOpen_time("NA");
	 
	// System.out.println("hello");
	 if (object[15] != null )
	 {
	  if (!object[0].toString().equalsIgnoreCase("Escalated"))
	  {
	  if (object[0].toString().equalsIgnoreCase("Transfer"))
	 feed.setAllotto (object[13].toString());
	 else
	 feed.setAllotto (object[15].toString());
	  }
	  else if (object[13] != null && object[0].toString().equalsIgnoreCase("Escalated") )
	  feed.setAllotto (object[13].toString());
	
	 }
	 else if(object[15] == null && (object[13] != null))
	 	  
	 	 feed.setAllotto (object[13].toString());
	 else if(object[15] != null && (object[13] != null))
	 {
	 if (!object[0].toString().equalsIgnoreCase("Escalated"))
	 {
	 if (object[0].toString().equalsIgnoreCase("Transfer"))
	 feed.setAllotto (object[13].toString());
	 else
	 feed.setAllotto (object[15].toString());
	 }
	  else if (object[13] != null && object[0].toString().equalsIgnoreCase("Escalated") )
	  feed.setAllotto (object[13].toString());
	
	 }
 	 
	 else
	 feed.setAllotto ("NA");
	 
	
	 
	 if (object[17] != null )
	 {
	  
	 feed.setFeed_by (object[17].toString());
	 }
	 else
	  feed.setFeed_by ("NA");
	 
	 if (object[0] != null )
	{
	 if (object[0].toString().equalsIgnoreCase("Resolved"))
	 
	feed.setStatusmode ("Close" );
	 else
	 feed.setStatusmode ("Open" );
	}
	 else
	 feed.setStatusmode ("NA");
	 if (object[0].toString().equalsIgnoreCase("Snooze"))
	 {
	 /*sndate=object[1].toString();
	 sntime=object[2].toString();
	 snduration=object[5].toString();
	 snuptodate=object[3].toString();
	 snuptotime=object[4].toString();*/
	  
	 feed.setTotal_time(object[5].toString());
	 feed.setWorking_time(object[5].toString());
	 }
	 else if(object[0].toString().equalsIgnoreCase("High Priority"))
	 {
	 String totalTime = DateUtil.time_difference(object[11].toString(), object[12].toString(), object[1].toString(), object[2].toString());
	 feed.setTotal_time(totalTime);
	 feed.setWorking_time(totalTime);
	
	 }
	 else if(object[0].toString().equalsIgnoreCase("Ignore"))
	 {
	/* System.out.println("In ignore :::::    "+sndate+"    "+sntime+"  "+snduration+"   "+snuptodate+"    "+snuptotime);
	 timeList = getTotalAndWorkingTime(object[10].toString(), object[11].toString(), object[12].toString(), CUA.getValueWithNullCheck(sndate), CUA.getValueWithNullCheck(sntime), CUA.getValueWithNullCheck(snduration),
	CUA.getValueWithNullCheck(snuptodate), CUA.getValueWithNullCheck(snuptotime), CUA.getValueWithNullCheck(object[1].toString()), CUA.getValueWithNullCheck(object[2].toString()), cbt);
	*/	
	 String totalTime = DateUtil.time_difference(object[11].toString(), object[12].toString(), object[1].toString(), object[2].toString());
	 feed.setTotal_time(totalTime);
	 feed.setWorking_time(totalTime);
	 
	/* if (timeList != null && timeList.size() > 0)
	{
	 
	 feed.setTotal_time( timeList.get(0).toString());
	 feed.setWorking_time( timeList.get(1).toString());
	}
	timeList.clear();*/
	
	 }
	 list.add(feed);
	}
	 return list;
	}






	public String editSMSModeData()
	{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{

	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();

	if (resolutionDetail[0] != null || resolutionDetail[0].equals(""))
	wherClause.put("resolve_remark", resolutionDetail[0]);

	if (resolutionDetail[1] != null || resolutionDetail[1].equals(""))
	wherClause.put("spare_used", resolutionDetail[1]);

	condtnBlock.put("id", complianId);
	boolean updateFeed = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
	if (updateFeed)
	{
	addActionMessage("Data Update Sucessfully.");
	return SUCCESS;
	}
	else
	{
	addActionMessage("Opps, There are some problem.");
	return ERROR;
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

	public Map<String, String> sortByComparator(Map<String, String> unsortMap, final boolean order)
	{

	List<Entry<String, String>> list = new LinkedList<Entry<String, String>>(unsortMap.entrySet());

	// Sorting the list based on values
	Collections.sort(list, new Comparator<Entry<String, String>>()
	{
	public int compare(Entry<String, String> o1, Entry<String, String> o2)
	{
	if (order)
	{
	return o1.getValue().compareTo(o2.getValue());
	}
	else
	{
	return o2.getValue().compareTo(o1.getValue());

	}
	}
	});

	// Maintaining insertion order with the help of LinkedList
	Map<String, String> sortedMap = new LinkedHashMap<String, String>();
	for (Entry<String, String> entry : list)
	{
	sortedMap.put(entry.getKey(), entry.getValue());
	}

	return sortedMap;
	}

	public String getMaxDateValue()
	{
	return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue)
	{
	this.maxDateValue = maxDateValue;
	}

	public String getMinDateValue()
	{
	return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
	this.minDateValue = minDateValue;
	}
	public List<Object> getMasterViewProp()
	{
	return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
	this.masterViewProp = masterViewProp;
	}

	public String getTaskType()
	{
	return taskType;
	}

	public void setTaskType(String taskType)
	{
	this.taskType = taskType;
	}

	public String getFromDepart()
	{
	return fromDepart;
	}

	public void setFromDepart(String fromDepart)
	{
	this.fromDepart = fromDepart;
	}

	public String getToDepart()
	{
	return toDepart;
	}

	public void setToDepart(String toDepart)
	{
	this.toDepart = toDepart;
	}

	public String getFeedStatus()
	{
	return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
	this.feedStatus = feedStatus;
	}

	public String getEscLevel()
	{
	return escLevel;
	}

	public void setEscLevel(String escLevel)
	{
	this.escLevel = escLevel;
	}

	public String getEscTAT()
	{
	return escTAT;
	}

	public void setEscTAT(String escTAT)
	{
	this.escTAT = escTAT;
	}

	public Map<String, String> getDataCountMap()
	{
	return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
	this.dataCountMap = dataCountMap;
	}
	public String getComplianId()
	{
	return complianId;
	}

	public void setComplianId(String complianId)
	{
	this.complianId = complianId;
	}

	public String getFormaterOn()
	{
	return formaterOn;
	}

	public void setFormaterOn(String formaterOn)
	{
	this.formaterOn = formaterOn;
	}

	public String getMainTable()
	{
	return mainTable;
	}

	public void setMainTable(String mainTable)
	{
	this.mainTable = mainTable;
	}

	public Map<String, String> getDataMap()
	{
	return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
	this.dataMap = dataMap;
	}

	public Map<String, Map<String, String>> getFinalStatusMap()
	{
	return finalStatusMap;
	}

	public void setFinalStatusMap(Map<String, Map<String, String>> finalStatusMap)
	{
	this.finalStatusMap = finalStatusMap;
	}

	public String getTableAlis()
	{
	return tableAlis;
	}

	public void setTableAlis(String tableAlis)
	{
	this.tableAlis = tableAlis;
	}

	public String getDivName()
	{
	return divName;
	}

	public void setDivName(String divName)
	{
	this.divName = divName;
	}

	public String getSelectedId()
	{
	return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
	this.selectedId = selectedId;
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

	public String[] getResolutionDetail()
	{
	return resolutionDetail;
	}

	public void setResolutionDetail(String[] resolutionDetail)
	{
	this.resolutionDetail = resolutionDetail;
	}

	public Map<String, String> getEditableDataMap()
	{
	return editableDataMap;
	}

	public void setEditableDataMap(Map<String, String> editableDataMap)
	{
	this.editableDataMap = editableDataMap;
	}
	public String getFromTime()
	{
	return fromTime;
	}

	public void setFromTime(String fromTime)
	{
	this.fromTime = fromTime;
	}

	public String getToTime()
	{
	return toTime;
	}

	public void setToTime(String toTime)
	{
	this.toTime = toTime;
	}
	public JSONArray getJsonArray()
	{
	return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
	this.jsonArray = jsonArray;
	}

	public String getSel_id()
	{
	return sel_id;
	}

	public void setSel_id(String sel_id)
	{
	this.sel_id = sel_id;
	}

	public String getDept()
	{
	return dept;
	}

	public void setDept(String dept)
	{
	this.dept = dept;
	}

	public String getCategory()
	{
	return category;
	}

	public void setCategory(String category)
	{
	this.category = category;
	}


	public Map<Integer, String> getDeptList()
	{
	return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
	this.deptList = deptList;
	}

	public List<FeedTicketCyclePojo> getList()
	{
	return list;
	}

	public void setList(List<FeedTicketCyclePojo> list)
	{
	this.list = list;
	}




	public String getAdvncSearch() {
	return advncSearch;
	}




	public void setAdvncSearch(String advncSearch) {
	this.advncSearch = advncSearch;
	}

}