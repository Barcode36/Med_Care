package com.Over2Cloud.ctrl.referral.dashboard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.referral.activity.ReferralActivityBoard;
import com.Over2Cloud.ctrl.referral.activity.ReferralActivityBoardHelper;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardSpecialityPojo;
 
@SuppressWarnings("serial")
public class ReferralDashboard extends GridPropertyBean
{
	private String dataFlag = "";
	private String feedStatus;
	private String dashFor = "";
	private String fromDate1 = "";
	private String toDate1 = "";
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	DashboardPojo dashObj = null;
	DashboardSpecialityPojo dashSpecObj = null;
	List<DashboardPojo> dept_subdeptcounterList = null;
	List<DashboardSpecialityPojo> specCounterList = null;
 	Map<String, String> specMap;
	private Map<String, String> serviceDeptList;
	private String filterFlag = null;
	private String filterDeptId = null;
	private String fromYear;
	private String fromMonth;
	private String toYear;
	private String fromTime;
	private String toTime;
 	private List specList;
	private String attendedBy;
	private String specialty;
	private String emergency;
	private String currentDayMonthValue;
	private String currentDayMonthKey;
	private String currentYear;
	private String flag;
	private List<Object> viewDashReferralData = null;
	private String graphDate;
	private String subDepartment;
	private String departmentView;
	private String refDoc;
	private String locationWise;
	public String beforeDashboardAction()
	{
		String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
		String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
		if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
		{
		for(String s:subModuleRightsArray)
		{
		if(s.equals("deptView_VIEW"))
		{
		setDepartmentView("DepartmentView");
		 
		}
		if(s.equals("locationManagerView_VIEW"))
		{
			 setLocationWise("LocationManagerView");
	 		 
		}
		}
		}
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
 	String subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
 	setSubDepartment(subDept);
	setCurrentYear(String.valueOf(DateUtil.getCurretnYear()));
	setCurrentDayMonthValue(getMonthInString(String.valueOf(DateUtil.getCurrentMonth())));
 	 if(String.valueOf(DateUtil.getCurrentMonth()).length()==1)
	 setCurrentDayMonthKey("0"+String.valueOf(DateUtil.getCurrentMonth()));
	 else
	 setCurrentDayMonthKey(String.valueOf(DateUtil.getCurrentMonth()));
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	specList = getDoctorSpecList(connectionSpace);
	if (dashFor != null && dashFor.equalsIgnoreCase("Order"))
	{
	if (filterFlag != null && filterFlag.equalsIgnoreCase("Day"))
	{
	getReferralOrderCountersDay();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Week"))
	{
	getReferralOrderCountersWeek();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Month"))
	{
	getReferralOrderCountersMonth();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Year"))
	{
	getReferralOrderCountersYear();
	}

	else if (filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	getReferralOrderCountersDate();
	}
	}

	if (dashFor != null && dashFor.equalsIgnoreCase("Status"))
	{
	if (filterFlag != null && filterFlag.equalsIgnoreCase("Day"))
	{
	getReferralStatusCountersDay();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Week"))
	{
	getReferralStatusCountersWeek();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Month"))
	{
	getReferralStatusCountersMonth();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Year"))
	{
	getReferralStatusCountersYear();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	getReferralStatusCountersDate();
	}
	}

	if (dashFor != null && dashFor.equalsIgnoreCase("Attended"))
	{
	if (filterFlag != null && filterFlag.equalsIgnoreCase("Day"))
	{
	getReferralAttendedCountersDay();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Week"))
	{
	getReferralAttendedCountersWeek();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Month"))
	{
	getReferralAttendedCountersMonth();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Year"))
	{
	getReferralAttendedCountersYear();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	getReferralAttendedCountersDate();
	}
	}

	if (dashFor != null && dashFor.equalsIgnoreCase("Speciality"))
	{
	if (filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	getReferralSpecialityCountersDate();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Day"))
	{
	getReferralSpecialityCountersDay();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Month"))
	{
	getReferralSpecialityCountersMonth();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Year"))
	{
	getReferralSpecialityCountersYear();
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Week"))
	{
	getReferralSpecialityCountersWeek();
	}

	}
	returnResult = SUCCESS;
	} catch (Exception e)
	{
	addActionError("Ooops There Is Some Problem In beforeDashboardAction in DashboardAction !!!");
	e.printStackTrace();
	returnResult = ERROR;
	}
	} else
	{
	returnResult = LOGIN;
	}
	return returnResult;

	}

	@SuppressWarnings("rawtypes")
	public String getReferralStatusCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{

	jsonArray = new JSONArray();
	jsonObject = new JSONObject();
	// getReferralStatusCounters1(fromDate, toDate);
	if (filterFlag.equalsIgnoreCase("Month"))
	getReferralStatusCountersMonth();
	else if (filterFlag.equalsIgnoreCase("Day"))
	getReferralStatusCountersDay();
	else if (filterFlag.equalsIgnoreCase("Week"))
	getReferralStatusCountersWeek();
	else if (filterFlag.equalsIgnoreCase("Year"))
	getReferralStatusCountersYear();
	else if (filterFlag.equalsIgnoreCase("Date"))
	getReferralStatusCountersDate();
	for (DashboardPojo pojo : dept_subdeptcounterList)
	{
	jsonObject.put("Date", pojo.getTime());
	jsonObject.put("Open", Integer.parseInt(pojo.getPc()));
	jsonObject.put("Close", Integer.parseInt(pojo.getRc()));
	jsonObject.put("total", Integer.parseInt(pojo.getTotal()));

	jsonArray.add(jsonObject);

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

	@SuppressWarnings("rawtypes")
	public String getReferralAttendedCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{

	jsonArray = new JSONArray();
	jsonObject = new JSONObject();
	if (filterFlag.equalsIgnoreCase("Month"))
	getReferralAttendedCountersMonth();
	else if (filterFlag.equalsIgnoreCase("Day"))
	getReferralAttendedCountersDay();
	else if (filterFlag.equalsIgnoreCase("Week"))
	getReferralAttendedCountersWeek();
	else if (filterFlag.equalsIgnoreCase("Year"))
	getReferralAttendedCountersYear();
	else if (filterFlag.equalsIgnoreCase("Date"))
	getReferralAttendedCountersDate();
	for (DashboardPojo pojo : dept_subdeptcounterList)
	{
	jsonObject.put("Date", pojo.getTime());
	jsonObject.put("Consultant", Integer.parseInt(pojo.getPc()));
	jsonObject.put("Resident", Integer.parseInt(pojo.getRc()));
	jsonObject.put("MedicalOfficer", Integer.parseInt(pojo.getHpc()));
	jsonObject.put("total", Integer.parseInt(pojo.getTotal()));

	jsonArray.add(jsonObject);

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
 

	public String getReferralOrderCounters()
	{
	boolean validSession = ValidateSession.checkSession();
	if (validSession)
	{
	try
	{

	jsonArray = new JSONArray();
	jsonObject = new JSONObject();
	if (filterFlag.equalsIgnoreCase("Month"))
	getReferralOrderCountersMonth();
	else if (filterFlag.equalsIgnoreCase("Day"))
	getReferralOrderCountersDay();
	else if (filterFlag.equalsIgnoreCase("Week"))
	getReferralOrderCountersWeek();
	else if (filterFlag.equalsIgnoreCase("Year"))
	getReferralOrderCountersYear();
	else if (filterFlag.equalsIgnoreCase("Date"))
	getReferralOrderCountersDate();

	for (DashboardPojo pojo : dept_subdeptcounterList)
	{
	jsonObject.put("Date", pojo.getTime());
	jsonObject.put("Urgent", Integer.parseInt(pojo.getHpc()));
	jsonObject.put("Routine", Integer.parseInt(pojo.getRc()));
	jsonObject.put("Stat", Integer.parseInt(pojo.getPc()));
	jsonObject.put("total", Integer.parseInt(pojo.getTotal()));
	jsonArray.add(jsonObject);

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

	public void getReferralStatusCountersDate()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralStatusForDate(fromDate1, toDate1, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, resolve = 0, open = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (!time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	resolve = 0;
	open = 0;
	}
	// DP.setTime(object2[2].toString());

	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));

	/*
	 * if(object2[0].toString().equalsIgnoreCase("Ignore"))
	 * {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString());
	 * 
	 * } else
	 * if(object2[0].toString().equalsIgnoreCase("Ignore-I"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString());
	 * 
	 * }
	 */
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	resolve = resolve + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}
	} else if (object2[0].toString().equalsIgnoreCase("Not Informed") || object2[0].toString().equalsIgnoreCase("Informed") || object2[0].toString().equalsIgnoreCase("Snooze") || object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	if (object2[0].toString().equalsIgnoreCase("Not Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}

	}
	// //System.out.println("Total :    "+total);

	DP.setPc(String.valueOf(open));
	DP.setRc(String.valueOf(resolve));
	DP.setTotal(String.valueOf(total));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((open * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((resolve * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");
	}
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralStatusCountersDay()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralStatusForDay(fromMonth, fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, resolve = 0, open = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (!time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	resolve = 0;
	open = 0;
	}
	// DP.setTime(object2[2].toString());

	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));

	/*
	 * if(object2[0].toString().equalsIgnoreCase("Ignore"))
	 * {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString());
	 * 
	 * } else
	 * if(object2[0].toString().equalsIgnoreCase("Ignore-I"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString());
	 * 
	 * }
	 */
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	resolve = resolve + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}
	} else if (object2[0].toString().equalsIgnoreCase("Not Informed") || object2[0].toString().equalsIgnoreCase("Informed") || object2[0].toString().equalsIgnoreCase("Snooze") || object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	if (object2[0].toString().equalsIgnoreCase("Not Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}

	}
	// //System.out.println("Total :    "+total);

	DP.setPc(String.valueOf(open));
	DP.setRc(String.valueOf(resolve));
	DP.setTotal(String.valueOf(total));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((open * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((resolve * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");
	}
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralStatusCountersMonth()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List monthList = new ArrayList();

	monthList.add("Jan");
	monthList.add("Feb");
	monthList.add("Mar");
	monthList.add("Apr");
	monthList.add("May");
	monthList.add("Jun");
	monthList.add("Jul");
	monthList.add("Aug");
	monthList.add("Sep");
	monthList.add("Oct");
	monthList.add("Nov");
	monthList.add("Dec");
	String time = null;
	for (Iterator iterator = monthList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = object.toString() + "-" + fromYear.substring(2, 4);
	int total = 0, resolve = 0, open = 0;
	DP = new DashboardPojo();
	referral_status_list = getCounterDataForReferralStatusForMonth(object.toString(), fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	/*
	 * if(object2[0].toString().equalsIgnoreCase("Ignore"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * } else
	 * if(object2[0].toString().equalsIgnoreCase("Ignore-I"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * }
	 */
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	resolve = resolve + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	} else if (object2[0].toString().equalsIgnoreCase("Not Informed") || object2[0].toString().equalsIgnoreCase("Informed") || object2[0].toString().equalsIgnoreCase("Snooze") || object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	if (object2[0].toString().equalsIgnoreCase("Not Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}

	}
	DP.setTime(time);
	DP.setPc(String.valueOf(open));
	DP.setRc(String.valueOf(resolve));
	DP.setTotal(String.valueOf(total));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((open * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((resolve * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");
	}
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralStatusCountersYear()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	int length = Integer.parseInt(toYear) - Integer.parseInt(fromYear);
	String time = null;
	for (int a = 0; a <= length; a++)
	{
	time = String.valueOf(Integer.parseInt(fromYear) + a);
	int total = 0, resolve = 0, open = 0;
	DP = new DashboardPojo();
	referral_status_list = getCounterDataForReferralStatusForYear(time, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	/*
	 * if(object2[0].toString().equalsIgnoreCase("Ignore"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * } else
	 * if(object2[0].toString().equalsIgnoreCase("Ignore-I"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * }
	 */
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	resolve = resolve + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	} else if (object2[0].toString().equalsIgnoreCase("Not Informed") || object2[0].toString().equalsIgnoreCase("Informed") || object2[0].toString().equalsIgnoreCase("Snooze") || object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	if (object2[0].toString().equalsIgnoreCase("Not Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}

	}
	DP.setTime(time);
	DP.setPc(String.valueOf(open));
	DP.setRc(String.valueOf(resolve));
	DP.setTotal(String.valueOf(total));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((open * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((resolve * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");
	}
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralStatusCountersWeek()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List weekList = new ArrayList();
	weekList.add("1");
	weekList.add("2");
	weekList.add("3");
	weekList.add("4");
	weekList.add("5");
	String time = null;
	for (Iterator iterator = weekList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = fromMonth + "-" + fromYear + "-Week" + object.toString();
	int total = 0, resolve = 0, open = 0;
	DP = new DashboardPojo();
	referral_status_list = getCounterDataForReferralStatusForWeek(object.toString(), fromYear, fromMonth, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	/*
	 * if(object2[0].toString().equalsIgnoreCase("Ignore"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * } else
	 * if(object2[0].toString().equalsIgnoreCase("Ignore-I"
	 * )) {
	 * resolve=resolve+Integer.parseInt(object2[1].toString
	 * ());
	 * total=total+Integer.parseInt(object2[1].toString
	 * ());
	 * 
	 * }
	 */
	if (object2[0].toString().equalsIgnoreCase("Seen"))
	{
	resolve = resolve + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	} else if (object2[0].toString().equalsIgnoreCase("Not Informed") || object2[0].toString().equalsIgnoreCase("Informed") || object2[0].toString().equalsIgnoreCase("Snooze") || object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	if (object2[0].toString().equalsIgnoreCase("Not Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Informed"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Snooze-I"))
	{
	open = open + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}

	}
	DP.setTime(time);
	DP.setPc(String.valueOf(open));
	DP.setRc(String.valueOf(resolve));
	DP.setTotal(String.valueOf(total));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((open * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((resolve * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");
	}
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralOrderCountersDate()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralOrderForDate(fromDate1, toDate1, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, routine = 0, stat = 0, urgent = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (!time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	routine = 0;
	urgent = 0;
	stat = 0;
	}
	if (object2[0].toString().equalsIgnoreCase("Routine"))
	{
	routine = routine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Urgent"))
	{
	urgent = urgent + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Stat"))
	{
	stat = stat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	DP.setPc(String.valueOf(stat));
	DP.setHpc(String.valueOf(urgent));
	DP.setRc(String.valueOf(routine));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((stat * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((urgent * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((routine * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setTotal(String.valueOf(total));
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralOrderCountersDay()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralOrderForDay(fromMonth, fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, routine = 0, stat = 0, urgent = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (!time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	routine = 0;
	urgent = 0;
	stat = 0;
	}
	if (object2[0].toString().equalsIgnoreCase("Routine"))
	{
	routine = routine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Urgent"))
	{
	urgent = urgent + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0].toString().equalsIgnoreCase("Stat"))
	{
	stat = stat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	DP.setPc(String.valueOf(stat));
	DP.setHpc(String.valueOf(urgent));
	DP.setRc(String.valueOf(routine));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((stat * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((urgent * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((routine * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setTotal(String.valueOf(total));
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralOrderCountersMonth()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List monthList = new ArrayList();

	monthList.add("Jan");
	monthList.add("Feb");
	monthList.add("Mar");
	monthList.add("Apr");
	monthList.add("May");
	monthList.add("Jun");
	monthList.add("Jul");
	monthList.add("Aug");
	monthList.add("Sep");
	monthList.add("Oct");
	monthList.add("Nov");
	monthList.add("Dec");
	String time = null;
	for (Iterator iterator = monthList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = object.toString() + "-" + fromYear.substring(2, 4);
	;
	int total = 0, routine = 0, stat = 0, urgent = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralOrderForMonth(object.toString(), fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Routine"))
	{
	routine = routine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Urgent"))
	{
	urgent = urgent + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Stat"))
	{
	stat = stat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}

	}

	}
	DP.setTime(time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((stat * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((urgent * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((routine * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setPc(String.valueOf(stat));
	DP.setHpc(String.valueOf(urgent));
	DP.setRc(String.valueOf(routine));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralOrderCountersYear()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	int length = Integer.parseInt(toYear) - Integer.parseInt(fromYear);
	String time = null;
	for (int a = 0; a <= length; a++)
	{
	time = String.valueOf(Integer.parseInt(fromYear) + a);
	int total = 0, routine = 0, stat = 0, urgent = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralOrderForYear(time, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Routine"))
	{
	routine = routine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Urgent"))
	{
	urgent = urgent + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Stat"))
	{
	stat = stat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}

	}

	}
	DP.setTime("Year-" + time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((stat * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((urgent * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((routine * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}

	DP.setPc(String.valueOf(stat));
	DP.setHpc(String.valueOf(urgent));
	DP.setRc(String.valueOf(routine));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralOrderCountersWeek()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List weekList = new ArrayList();
	weekList.add("1");
	weekList.add("2");
	weekList.add("3");
	weekList.add("4");
	weekList.add("5");
	String time = null;
	for (Iterator iterator = weekList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = fromMonth + "-" + fromYear + "-Week" + object.toString();
	int total = 0, routine = 0, stat = 0, urgent = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralOrderForWeek(object.toString(), fromYear, fromMonth, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0].toString().equalsIgnoreCase("Routine"))
	{
	routine = routine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Urgent"))
	{
	urgent = urgent + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	} else if (object2[0].toString().equalsIgnoreCase("Stat"))
	{
	stat = stat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());
	}

	}

	}
	DP.setTime(time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((stat * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((urgent * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((routine * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setPc(String.valueOf(stat));
	DP.setHpc(String.valueOf(urgent));
	DP.setRc(String.valueOf(routine));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralAttendedCountersDate()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralAttendedForDate(fromDate1, toDate1, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, Consultant = 0, Resident = 0, MedicalOfficer = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null)
	{
	if (object2[0] != null && !time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	Consultant = 0;
	Resident = 0;
	MedicalOfficer = 0;
	}
	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Consultant"))
	{
	Consultant = Consultant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Resident"))
	{
	Resident = Resident + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Officer"))
	{
	MedicalOfficer = MedicalOfficer + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	DP.setPc(String.valueOf(Consultant));
	DP.setHpc(String.valueOf(MedicalOfficer));
	DP.setRc(String.valueOf(Resident));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((Consultant * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((MedicalOfficer * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((Resident * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setTotal(String.valueOf(total));
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}
	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralAttendedCountersDay()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralAttendedForDay(fromMonth, fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, Consultant = 0, Resident = 0, MedicalOfficer = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null)
	{
	if (object2[0] != null && !time1.equalsIgnoreCase(object2[2].toString()))
	{
	DP = new DashboardPojo();
	total = 0;
	Consultant = 0;
	Resident = 0;
	MedicalOfficer = 0;
	}
	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Consultant"))
	{
	Consultant = Consultant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Resident"))
	{
	Resident = Resident + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Officer"))
	{
	MedicalOfficer = MedicalOfficer + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}
	String mnth[] = object2[2].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	DP.setPc(String.valueOf(Consultant));
	DP.setHpc(String.valueOf(MedicalOfficer));
	DP.setRc(String.valueOf(Resident));

	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((Consultant * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((MedicalOfficer * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((Resident * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setTotal(String.valueOf(total));
	if (total != 0 && !time1.equalsIgnoreCase(object2[2].toString()))
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);
	time1 = object2[2].toString();

	}
	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralAttendedCountersMonth()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List monthList = new ArrayList();

	monthList.add("Jan");
	monthList.add("Feb");
	monthList.add("Mar");
	monthList.add("Apr");
	monthList.add("May");
	monthList.add("Jun");
	monthList.add("Jul");
	monthList.add("Aug");
	monthList.add("Sep");
	monthList.add("Oct");
	monthList.add("Nov");
	monthList.add("Dec");
	String time = null;
	for (Iterator iterator = monthList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = object.toString() + "-" + fromYear.substring(2, 4);
	;
	int total = 0, Consultant = 0, Resident = 0, MedicalOfficer = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralAttendedForMonth(object.toString(), fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Consultant"))
	{
	Consultant = Consultant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Resident"))
	{
	Resident = Resident + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Officer"))
	{
	MedicalOfficer = MedicalOfficer + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}
	DP.setTime(time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((Consultant * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((MedicalOfficer * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((Resident * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setPc(String.valueOf(Consultant));
	DP.setHpc(String.valueOf(MedicalOfficer));
	DP.setRc(String.valueOf(Resident));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralAttendedCountersYear()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	int length = Integer.parseInt(toYear) - Integer.parseInt(fromYear);
	String time = null;
	for (int a = 0; a <= length; a++)
	{
	time = String.valueOf(Integer.parseInt(fromYear) + a);
	int total = 0, Consultant = 0, Resident = 0, MedicalOfficer = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralAttendedForYear(time, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Consultant"))
	{
	Consultant = Consultant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Resident"))
	{
	Resident = Resident + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Officer"))
	{
	MedicalOfficer = MedicalOfficer + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}
	DP.setTime("Year-" + time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((Consultant * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((MedicalOfficer * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((Resident * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}

	DP.setPc(String.valueOf(Consultant));
	DP.setHpc(String.valueOf(MedicalOfficer));
	DP.setRc(String.valueOf(Resident));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralAttendedCountersWeek()
	{
	dashObj = new DashboardPojo();
	DashboardPojo DP = null;
	try
	{
	dept_subdeptcounterList = new ArrayList<DashboardPojo>();
	List referral_status_list = new ArrayList();
	List weekList = new ArrayList();
	weekList.add("1");
	weekList.add("2");
	weekList.add("3");
	weekList.add("4");
	weekList.add("5");
	String time = null;
	for (Iterator iterator = weekList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = fromMonth + "-" + fromYear + "-Week" + object.toString();
	int total = 0, Consultant = 0, Resident = 0, MedicalOfficer = 0;
	DP = new DashboardPojo();

	referral_status_list = getCounterDataForReferralAttendedForWeek(object.toString(), fromYear, fromMonth, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Consultant"))
	{
	Consultant = Consultant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Resident"))
	{
	Resident = Resident + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Officer"))
	{
	MedicalOfficer = MedicalOfficer + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	}

	}
	DP.setTime(time);
	if (total > 0)
	{
	DP.setPcPer(String.valueOf(Math.round((Consultant * 100) / total)));
	DP.setHpcPer(String.valueOf(Math.round((MedicalOfficer * 100) / total)));
	DP.setRcPer(String.valueOf(Math.round((Resident * 100) / total)));
	DP.setTotalPer(String.valueOf(Math.round((total * 100) / total)));
	} else
	{
	DP.setPcPer("0");
	DP.setHpcPer("0");
	DP.setRcPer("0");
	DP.setTotalPer("0");

	}
	DP.setPc(String.valueOf(Consultant));
	DP.setHpc(String.valueOf(MedicalOfficer));
	DP.setRc(String.valueOf(Resident));
	DP.setTotal(String.valueOf(total));
	dept_subdeptcounterList.add(DP);
	dashObj.setDashList(dept_subdeptcounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralSpecialityCountersDate()
	{
	dashSpecObj = new DashboardSpecialityPojo();
	DashboardSpecialityPojo DP = null;
	try
	{
	specCounterList = new ArrayList<DashboardSpecialityPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralSpecialityForDate(fromDate1, toDate1, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{
	String time1 = "";
	int total = 0, Medical_Oncology_Hematology = 0, Phlebotomy = 0, Radiation_Oncology = 0, Critical_Care = 0, Cardiology = 0, Orthopaedics = 0, Liver_Transplant = 0, Pediatric_Surgery = 0, Cardio_Surg = 0, Neurology = 0, Endocrinology = 0;
	int Laboratory_Services = 0, Anaesthesia = 0, Ophthalmology = 0, Pediatric_Gastro_Hepatology = 0, Plastic_Surgery = 0, Radiology = 0, Nuclear_Medicine = 0, Gastroenterology = 0, General_Speciality = 0, Emergency_Trauma_Services = 0;
	int Nephrology = 0, GI_Surgery = 0, Neurosurgery = 0, Urology_Andrology = 0, Internal_Medicine = 0, Vascular_Endovascular_Surgery = 0, Rheumatology = 0, Dental = 0, Ear_Nose_Throat = 0, Respiratory_Sleep_Medicine = 0;
	int Pediatric_Cardiology = 0, Ortho_Anaesthesia = 0, Gynaecology_Obstetrics = 0, Executive_Health_Check = 0, Head_Neck_Oncology = 0, Interventional_Radiology = 0, Breast_Services = 0, Thoracic_Surgery = 0;
	int Dermatology = 0, Pediatrics = 0, In_vitro_fertilisation = 0, Neonatology = 0, Bariatric_Surgery = 0, Ayurvedic_Medicine = 0, Physiotherapy = 0;

	int grand_total = 0, Medical_Oncology_Hematology_total = 0, Phlebotomy_total = 0, Radiation_Oncology_total = 0, Critical_Care_total = 0, Cardiology_total = 0, Orthopaedics_total = 0, Liver_Transplant_total = 0, Pediatric_Surgery_total = 0, Cardio_Surg_total = 0, Neurology_total = 0, Endocrinology_total = 0;
	int Laboratory_Services_total = 0, Anaesthesia_total = 0, Ophthalmology_total = 0, Pediatric_Gastro_Hepatology_total = 0, Plastic_Surgery_total = 0, Radiology_total = 0, Nuclear_Medicine_total = 0, Gastroenterology_total = 0, General_Speciality_total = 0, Emergency_Trauma_Services_total = 0;
	int Nephrology_total = 0, GI_Surgery_total = 0, Neurosurgery_total = 0, Urology_Andrology_total = 0, Internal_Medicine_total = 0, Vascular_Endovascular_Surgery_total = 0, Rheumatology_total = 0, Dental_total = 0, Ear_Nose_Throat_total = 0, Respiratory_Sleep_Medicine_total = 0;
	int Pediatric_Cardiology_total = 0, Ortho_Anaesthesia_total = 0, Gynaecology_Obstetrics_total = 0, Executive_Health_Check_total = 0, Head_Neck_Oncology_total = 0, Interventional_Radiology_total = 0, Breast_Services_total = 0, Thoracic_Surgery_total = 0;
	int Dermatology_total = 0, Pediatrics_total = 0, In_vitro_fertilisation_total = 0, Neonatology_total = 0, Bariatric_Surgery_total = 0, Ayurvedic_Medicine_total = 0, Physiotherapy_total = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null)
	{
	if (object2[0] != null && !time1.equalsIgnoreCase(object2[3].toString()))
	{
	DP = new DashboardSpecialityPojo();
	total = 0;
	Medical_Oncology_Hematology = 0;
	Phlebotomy = 0;
	Radiation_Oncology = 0;
	Critical_Care = 0;
	Cardiology = 0;
	Orthopaedics = 0;
	Liver_Transplant = 0;
	Pediatric_Surgery = 0;
	Cardio_Surg = 0;
	Neurology = 0;
	Endocrinology = 0;
	Laboratory_Services = 0;
	Anaesthesia = 0;
	Ophthalmology = 0;
	Pediatric_Gastro_Hepatology = 0;
	Plastic_Surgery = 0;
	Radiology = 0;
	Nuclear_Medicine = 0;
	Gastroenterology = 0;
	General_Speciality = 0;
	Emergency_Trauma_Services = 0;
	Nephrology = 0;
	GI_Surgery = 0;
	Neurosurgery = 0;
	Urology_Andrology = 0;
	Internal_Medicine = 0;
	Vascular_Endovascular_Surgery = 0;
	Rheumatology = 0;
	Dental = 0;
	Ear_Nose_Throat = 0;
	Respiratory_Sleep_Medicine = 0;
	Pediatric_Cardiology = 0;
	Ortho_Anaesthesia = 0;
	Gynaecology_Obstetrics = 0;
	Executive_Health_Check = 0;
	Head_Neck_Oncology = 0;
	Interventional_Radiology = 0;
	Breast_Services = 0;
	Thoracic_Surgery = 0;
	Dermatology = 0;
	Pediatrics = 0;
	In_vitro_fertilisation = 0;
	Neonatology = 0;
	Bariatric_Surgery = 0;
	Ayurvedic_Medicine = 0;
	Physiotherapy = 0;

	grand_total = 0;
	Medical_Oncology_Hematology_total = 0;
	Phlebotomy_total = 0;
	Radiation_Oncology_total = 0;
	Critical_Care_total = 0;
	Cardiology_total = 0;
	Orthopaedics_total = 0;
	Liver_Transplant_total = 0;
	Pediatric_Surgery_total = 0;
	Cardio_Surg_total = 0;
	Neurology_total = 0;
	Endocrinology_total = 0;
	Laboratory_Services_total = 0;
	Anaesthesia_total = 0;
	Ophthalmology_total = 0;
	Pediatric_Gastro_Hepatology_total = 0;
	Plastic_Surgery_total = 0;
	Radiology_total = 0;
	Nuclear_Medicine_total = 0;
	Gastroenterology_total = 0;
	General_Speciality_total = 0;
	Emergency_Trauma_Services_total = 0;
	Nephrology_total = 0;
	GI_Surgery_total = 0;
	Neurosurgery_total = 0;
	Urology_Andrology_total = 0;
	Internal_Medicine_total = 0;
	Vascular_Endovascular_Surgery_total = 0;
	Rheumatology_total = 0;
	Dental_total = 0;
	Ear_Nose_Throat_total = 0;
	Respiratory_Sleep_Medicine_total = 0;
	Pediatric_Cardiology_total = 0;
	Ortho_Anaesthesia_total = 0;
	Gynaecology_Obstetrics_total = 0;
	Executive_Health_Check_total = 0;
	Head_Neck_Oncology_total = 0;
	Interventional_Radiology_total = 0;
	Breast_Services_total = 0;
	Thoracic_Surgery_total = 0;
	Dermatology_total = 0;
	Pediatrics_total = 0;
	In_vitro_fertilisation_total = 0;
	Neonatology_total = 0;
	Bariatric_Surgery_total = 0;
	Ayurvedic_Medicine_total = 0;
	Physiotherapy_total = 0;

	}

	if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy = Phlebotomy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology = Radiation_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care = Critical_Care + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{

	Cardiology = Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics = Orthopaedics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant = Liver_Transplant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery = Pediatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg = Cardio_Surg + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology = Neurology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology = Endocrinology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services = Laboratory_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia = Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology = Ophthalmology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology = Pediatric_Gastro_Hepatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology = Medical_Oncology_Hematology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery = Plastic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology = Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine = Nuclear_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology = Gastroenterology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality = General_Speciality + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services = Emergency_Trauma_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology = Nephrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery = GI_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery = Neurosurgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology = Urology_Andrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine = Internal_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery = Vascular_Endovascular_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology = Rheumatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental = Dental + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat = Ear_Nose_Throat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine = Respiratory_Sleep_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology = Pediatric_Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia = Ortho_Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics = Gynaecology_Obstetrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check = Executive_Health_Check + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology = Head_Neck_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology = Interventional_Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services = Breast_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery = Thoracic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology = Dermatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics = Pediatrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation = In_vitro_fertilisation + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology = Neonatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery = Bariatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine = Ayurvedic_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy = Physiotherapy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy_total = Phlebotomy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology_total = Radiation_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care_total = Critical_Care_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology_total = Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics_total = Orthopaedics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant_total = Liver_Transplant_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery_total = Pediatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg_total = Cardio_Surg_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology_total = Neurology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology_total = Endocrinology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services_total = Laboratory_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia_total = Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology_total = Ophthalmology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology_total = Pediatric_Gastro_Hepatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology_total = Medical_Oncology_Hematology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery_total = Plastic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology_total = Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine_total = Nuclear_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology_total = Gastroenterology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality_total = General_Speciality_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services_total = Emergency_Trauma_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology_total = Nephrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery_total = GI_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery_total = Neurosurgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology_total = Urology_Andrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine_total = Internal_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery_total = Vascular_Endovascular_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology_total = Rheumatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental_total = Dental_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat_total = Ear_Nose_Throat_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine_total = Respiratory_Sleep_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology_total = Pediatric_Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia_total = Ortho_Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics_total = Gynaecology_Obstetrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check_total = Executive_Health_Check_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology_total = Head_Neck_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology_total = Interventional_Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services_total = Breast_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery_total = Thoracic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology_total = Dermatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics_total = Pediatrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation_total = In_vitro_fertilisation_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology_total = Neonatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && attendedBy.equalsIgnoreCase("-1") && (object2[2].toString().equalsIgnoreCase("Resident") || object2[2].toString().equalsIgnoreCase("Consultant") || object2[2].toString().equalsIgnoreCase("Medical Officer")) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery_total = Bariatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine_total = Ayurvedic_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy_total = Physiotherapy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	String mnth[] = object2[3].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	if (Phlebotomy_total > 0)
	DP.setPhlebotomy_Per(String.valueOf(Math.round((Phlebotomy * 100) / Phlebotomy_total)));
	else
	DP.setPhlebotomy_Per("0");
	if (Radiation_Oncology_total > 0)
	DP.setRadiation_Oncology_Per(String.valueOf(Math.round((Radiation_Oncology * 100) / Radiation_Oncology_total)));
	else
	DP.setRadiation_Oncology_Per("0");
	if (Critical_Care_total > 0)
	DP.setCritical_Care_Per(String.valueOf(Math.round((Critical_Care * 100) / Critical_Care_total)));
	else
	DP.setCritical_Care_Per("0");
	if (Cardiology_total > 0)
	DP.setCardiology_Per(String.valueOf(Math.round((Cardiology * 100) / Cardiology_total)));
	else
	DP.setCardiology_Per("0");
	if (Orthopaedics_total > 0)
	DP.setOrthopaedics_Per(String.valueOf(Math.round((Orthopaedics * 100) / Orthopaedics_total)));
	else
	DP.setOrthopaedics_Per("0");
	if (Liver_Transplant_total > 0)
	DP.setLiver_Transplant_Per(String.valueOf(Math.round((Liver_Transplant * 100) / Liver_Transplant_total)));
	else
	DP.setLiver_Transplant_Per("0");
	if (Pediatric_Surgery_total > 0)
	DP.setPediatric_Surgery_Per(String.valueOf(Math.round((Pediatric_Surgery * 100) / Pediatric_Surgery_total)));
	else
	DP.setPediatric_Surgery_Per("0");
	if (Cardio_Surg_total > 0)
	DP.setCardio_Surg_Per(String.valueOf(Math.round((Cardio_Surg * 100) / Cardio_Surg_total)));
	else
	DP.setCardio_Surg_Per("0");
	if (Neurology_total > 0)
	DP.setNeurology_Per(String.valueOf(Math.round((Neurology * 100) / Neurology_total)));
	else
	DP.setNeurology_Per("0");
	if (Endocrinology_total > 0)
	DP.setEndocrinology_Per(String.valueOf(Math.round((Endocrinology * 100) / Endocrinology_total)));
	else
	DP.setEndocrinology_Per("0");
	if (Laboratory_Services_total > 0)
	DP.setLaboratory_Services_Per(String.valueOf(Math.round((Laboratory_Services * 100) / Laboratory_Services_total)));
	else
	DP.setLaboratory_Services_Per("0");
	if (Anaesthesia_total > 0)
	DP.setAnaesthesia_Per(String.valueOf(Math.round((Anaesthesia * 100) / Anaesthesia_total)));
	else
	DP.setAnaesthesia_Per("0");
	if (Ophthalmology_total > 0)
	DP.setOphthalmology_Per(String.valueOf(Math.round((Ophthalmology * 100) / Ophthalmology_total)));
	else
	DP.setOphthalmology_Per("0");
	if (Pediatric_Gastro_Hepatology_total > 0)
	DP.setPediatric_Gastro_Hepatology_Per(String.valueOf(Math.round((Pediatric_Gastro_Hepatology * 100) / Pediatric_Gastro_Hepatology_total)));
	else
	DP.setPediatric_Gastro_Hepatology_Per("0");
	if (Medical_Oncology_Hematology_total > 0)
	DP.setMedical_Oncology_Hematology_Per(String.valueOf(Math.round((Medical_Oncology_Hematology * 100) / Medical_Oncology_Hematology_total)));
	else
	DP.setMedical_Oncology_Hematology_Per("0");
	if (Plastic_Surgery_total > 0)
	DP.setPlastic_Surgery_Per(String.valueOf(Math.round((Plastic_Surgery * 100) / Plastic_Surgery_total)));
	else
	DP.setPlastic_Surgery_Per("0");
	if (Radiology_total > 0)
	DP.setRadiology_Per(String.valueOf(Math.round((Radiology * 100) / Radiology_total)));
	else
	DP.setRadiology_Per("0");
	if (Nuclear_Medicine_total > 0)
	DP.setNuclear_Medicine_Per(String.valueOf(Math.round((Nuclear_Medicine * 100) / Nuclear_Medicine_total)));
	else
	DP.setNuclear_Medicine_Per("0");
	if (Gastroenterology_total > 0)
	DP.setGastroenterology_Per(String.valueOf(Math.round((Gastroenterology * 100) / Gastroenterology_total)));
	else
	DP.setGastroenterology_Per("0");
	if (General_Speciality_total > 0)
	DP.setGeneral_Speciality_Per(String.valueOf(Math.round((General_Speciality * 100) / General_Speciality_total)));
	else
	DP.setGeneral_Speciality_Per("0");
	if (Emergency_Trauma_Services_total > 0)
	DP.setEmergency_Trauma_Services_Per(String.valueOf(Math.round((Emergency_Trauma_Services * 100) / Emergency_Trauma_Services_total)));
	else
	DP.setEmergency_Trauma_Services_Per("0");
	if (Nephrology_total > 0)
	DP.setNephrology_Per(String.valueOf(Math.round((Nephrology * 100) / Nephrology_total)));
	else
	DP.setNephrology_Per("0");
	if (GI_Surgery_total > 0)
	DP.setGI_Surgery_Per(String.valueOf(Math.round((GI_Surgery * 100) / GI_Surgery_total)));
	else
	DP.setGI_Surgery_Per("0");
	if (Neurosurgery_total > 0)
	DP.setNeurosurgery_Per(String.valueOf(Math.round((Neurosurgery * 100) / Neurosurgery_total)));
	else
	DP.setNeurosurgery_Per("0");
	if (Urology_Andrology_total > 0)
	DP.setUrology_Andrology(String.valueOf(Math.round((Urology_Andrology * 100) / Urology_Andrology_total)));
	else
	DP.setUrology_Andrology("0");
	if (Internal_Medicine_total > 0)
	DP.setInternal_Medicine_Per(String.valueOf(Math.round((Internal_Medicine * 100) / Internal_Medicine_total)));
	else
	DP.setInternal_Medicine_Per("0");
	if (Vascular_Endovascular_Surgery_total > 0)
	DP.setVascular_Endovascular_Surgery_Per(String.valueOf(Math.round((Vascular_Endovascular_Surgery * 100) / Vascular_Endovascular_Surgery_total)));
	else
	DP.setVascular_Endovascular_Surgery_Per("0");
	if (Rheumatology_total > 0)
	DP.setRheumatology_Per(String.valueOf(Math.round((Rheumatology * 100) / Rheumatology_total)));
	else
	DP.setRheumatology_Per("0");
	if (Dental_total > 0)
	DP.setDental_Per(String.valueOf(Math.round((Dental * 100) / Dental_total)));
	else
	DP.setDental_Per("0");
	if (Ear_Nose_Throat_total > 0)
	DP.setEar_Nose_Throat_Per(String.valueOf(Math.round((Ear_Nose_Throat * 100) / Ear_Nose_Throat_total)));
	else
	DP.setEar_Nose_Throat_Per("0");
	if (Respiratory_Sleep_Medicine_total > 0)
	DP.setRespiratory_Sleep_Medicine_Per(String.valueOf(Math.round((Respiratory_Sleep_Medicine * 100) / Respiratory_Sleep_Medicine_total)));
	else
	DP.setRespiratory_Sleep_Medicine_Per("0");
	if (Pediatric_Cardiology_total > 0)
	DP.setPediatric_Cardiology_Per(String.valueOf(Math.round((Pediatric_Cardiology * 100) / Pediatric_Cardiology_total)));
	else
	DP.setPediatric_Cardiology_Per("0");
	if (Ortho_Anaesthesia_total > 0)
	DP.setOrtho_Anaesthesia_Per(String.valueOf(Math.round((Ortho_Anaesthesia * 100) / Ortho_Anaesthesia_total)));
	else
	DP.setOrtho_Anaesthesia_Per("0");
	if (Gynaecology_Obstetrics_total > 0)
	DP.setGynaecology_Obstetrics_Per(String.valueOf(Math.round((Gynaecology_Obstetrics * 100) / Gynaecology_Obstetrics_total)));
	else
	DP.setGynaecology_Obstetrics_Per("0");
	if (Executive_Health_Check_total > 0)
	DP.setExecutive_Health_Check_Per(String.valueOf(Math.round((Executive_Health_Check * 100) / Executive_Health_Check_total)));
	else
	DP.setExecutive_Health_Check_Per("0");
	if (Head_Neck_Oncology_total > 0)
	DP.setHead_Neck_Oncology_Per(String.valueOf(Math.round((Head_Neck_Oncology * 100) / Head_Neck_Oncology_total)));
	else
	DP.setHead_Neck_Oncology_Per("0");
	if (Interventional_Radiology_total > 0)
	DP.setInterventional_Radiology_Per(String.valueOf(Math.round((Interventional_Radiology * 100) / Interventional_Radiology_total)));
	else
	DP.setInterventional_Radiology_Per("0");
	if (Breast_Services_total > 0)
	DP.setBreast_Services_Per(String.valueOf(Math.round((Breast_Services * 100) / Breast_Services_total)));
	else
	DP.setBreast_Services_Per("0");
	if (Thoracic_Surgery_total > 0)
	DP.setThoracic_Surgery_Per(String.valueOf(Math.round((Thoracic_Surgery * 100) / Thoracic_Surgery_total)));
	else
	DP.setThoracic_Surgery_Per("0");
	if (Dermatology_total > 0)
	DP.setDermatology_Per(String.valueOf(Math.round((Dermatology * 100) / Dermatology_total)));
	else
	DP.setDermatology_Per("0");
	if (Pediatrics_total > 0)
	DP.setPediatrics_Per(String.valueOf(Math.round((Pediatrics * 100) / Pediatrics_total)));
	else
	DP.setPediatrics_Per("0");
	if (In_vitro_fertilisation_total > 0)
	DP.setIn_vitro_fertilisation_Per(String.valueOf(Math.round((In_vitro_fertilisation * 100) / In_vitro_fertilisation_total)));
	else
	DP.setIn_vitro_fertilisation_Per("0");
	if (Neonatology_total > 0)
	DP.setNeonatology_Per(String.valueOf(Math.round((Neonatology * 100) / Neonatology_total)));
	else
	DP.setNeonatology_Per("0");
	if (Bariatric_Surgery_total > 0)
	DP.setBariatric_Surgery_Per(String.valueOf(Math.round((Bariatric_Surgery * 100) / Bariatric_Surgery_total)));
	else
	DP.setBariatric_Surgery_Per("0");
	if (Ayurvedic_Medicine_total > 0)
	DP.setAyurvedic_Medicine_Per(String.valueOf(Math.round((Ayurvedic_Medicine * 100) / Ayurvedic_Medicine_total)));
	else
	DP.setAyurvedic_Medicine_Per("0");
	if (Physiotherapy_total > 0)
	DP.setPhysiotherapy_Per(String.valueOf(Math.round((Physiotherapy * 100) / Physiotherapy_total)));
	else
	DP.setPhysiotherapy_Per("0");
	if (grand_total > 0)
	DP.setTotal_Per(String.valueOf(Math.round((total * 100) / grand_total)));
	else
	DP.setTotal_Per("0");
	if (attendedBy.equalsIgnoreCase("-1"))
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy_total));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology_total));
	DP.setCritical_Care(String.valueOf(Critical_Care_total));
	DP.setCardiology(String.valueOf(Cardiology_total));
	DP.setOrthopaedics(String.valueOf(Orthopaedics_total));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant_total));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery_total));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg_total));
	DP.setNeurology(String.valueOf(Neurology_total));
	DP.setEndocrinology(String.valueOf(Endocrinology_total));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services_total));
	DP.setAnaesthesia(String.valueOf(Anaesthesia_total));
	DP.setOphthalmology(String.valueOf(Ophthalmology_total));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology_total));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology_total));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery_total));
	DP.setRadiology(String.valueOf(Radiology_total));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine_total));
	DP.setGastroenterology(String.valueOf(Gastroenterology_total));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality_total));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services_total));
	DP.setNephrology(String.valueOf(Nephrology_total));
	DP.setGI_Surgery(String.valueOf(GI_Surgery_total));
	DP.setNeurosurgery(String.valueOf(Neurosurgery_total));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology_total));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine_total));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery_total));
	DP.setRheumatology(String.valueOf(Rheumatology_total));
	DP.setDental(String.valueOf(Dental_total));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat_total));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine_total));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology_total));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia_total));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics_total));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check_total));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology_total));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology_total));
	DP.setBreast_Services(String.valueOf(Breast_Services_total));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery_total));
	DP.setDermatology(String.valueOf(Dermatology_total));
	DP.setPediatrics(String.valueOf(Pediatrics_total));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation_total));
	DP.setNeonatology(String.valueOf(Neonatology_total));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery_total));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine_total));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy_total));
	DP.setTotal(String.valueOf(grand_total));
	} else
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology));
	DP.setCritical_Care(String.valueOf(Critical_Care));
	DP.setCardiology(String.valueOf(Cardiology));
	DP.setOrthopaedics(String.valueOf(Orthopaedics));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg));
	DP.setNeurology(String.valueOf(Neurology));
	DP.setEndocrinology(String.valueOf(Endocrinology));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services));
	DP.setAnaesthesia(String.valueOf(Anaesthesia));
	DP.setOphthalmology(String.valueOf(Ophthalmology));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery));
	DP.setRadiology(String.valueOf(Radiology));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine));
	DP.setGastroenterology(String.valueOf(Gastroenterology));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services));
	DP.setNephrology(String.valueOf(Nephrology));
	DP.setGI_Surgery(String.valueOf(GI_Surgery));
	DP.setNeurosurgery(String.valueOf(Neurosurgery));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery));
	DP.setRheumatology(String.valueOf(Rheumatology));
	DP.setDental(String.valueOf(Dental));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology));
	DP.setBreast_Services(String.valueOf(Breast_Services));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery));
	DP.setDermatology(String.valueOf(Dermatology));
	DP.setPediatrics(String.valueOf(Pediatrics));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation));
	DP.setNeonatology(String.valueOf(Neonatology));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy));
	DP.setTotal(String.valueOf(total));
	}

	if (total != 0 && !time1.equalsIgnoreCase(object2[3].toString()))
	specCounterList.add(DP);
	dashSpecObj.setDashList(specCounterList);
	time1 = object2[3].toString();

	}
	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void getReferralSpecialityCountersDay()
	{
	dashSpecObj = new DashboardSpecialityPojo();
	DashboardSpecialityPojo DP = null;
	try
	{
	specCounterList = new ArrayList<DashboardSpecialityPojo>();
	List referral_status_list = new ArrayList();
	referral_status_list = getCounterDataForReferralSpecialityForDay(fromMonth, fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	String time1 = "";
	int total = 0, Medical_Oncology_Hematology = 0, Phlebotomy = 0, Radiation_Oncology = 0, Critical_Care = 0, Cardiology = 0, Orthopaedics = 0, Liver_Transplant = 0, Pediatric_Surgery = 0, Cardio_Surg = 0, Neurology = 0, Endocrinology = 0;
	int Laboratory_Services = 0, Anaesthesia = 0, Ophthalmology = 0, Pediatric_Gastro_Hepatology = 0, Plastic_Surgery = 0, Radiology = 0, Nuclear_Medicine = 0, Gastroenterology = 0, General_Speciality = 0, Emergency_Trauma_Services = 0;
	int Nephrology = 0, GI_Surgery = 0, Neurosurgery = 0, Urology_Andrology = 0, Internal_Medicine = 0, Vascular_Endovascular_Surgery = 0, Rheumatology = 0, Dental = 0, Ear_Nose_Throat = 0, Respiratory_Sleep_Medicine = 0;
	int Pediatric_Cardiology = 0, Ortho_Anaesthesia = 0, Gynaecology_Obstetrics = 0, Executive_Health_Check = 0, Head_Neck_Oncology = 0, Interventional_Radiology = 0, Breast_Services = 0, Thoracic_Surgery = 0;
	int Dermatology = 0, Pediatrics = 0, In_vitro_fertilisation = 0, Neonatology = 0, Bariatric_Surgery = 0, Ayurvedic_Medicine = 0, Physiotherapy = 0;

	int grand_total = 0, Medical_Oncology_Hematology_total = 0, Phlebotomy_total = 0, Radiation_Oncology_total = 0, Critical_Care_total = 0, Cardiology_total = 0, Orthopaedics_total = 0, Liver_Transplant_total = 0, Pediatric_Surgery_total = 0, Cardio_Surg_total = 0, Neurology_total = 0, Endocrinology_total = 0;
	int Laboratory_Services_total = 0, Anaesthesia_total = 0, Ophthalmology_total = 0, Pediatric_Gastro_Hepatology_total = 0, Plastic_Surgery_total = 0, Radiology_total = 0, Nuclear_Medicine_total = 0, Gastroenterology_total = 0, General_Speciality_total = 0, Emergency_Trauma_Services_total = 0;
	int Nephrology_total = 0, GI_Surgery_total = 0, Neurosurgery_total = 0, Urology_Andrology_total = 0, Internal_Medicine_total = 0, Vascular_Endovascular_Surgery_total = 0, Rheumatology_total = 0, Dental_total = 0, Ear_Nose_Throat_total = 0, Respiratory_Sleep_Medicine_total = 0;
	int Pediatric_Cardiology_total = 0, Ortho_Anaesthesia_total = 0, Gynaecology_Obstetrics_total = 0, Executive_Health_Check_total = 0, Head_Neck_Oncology_total = 0, Interventional_Radiology_total = 0, Breast_Services_total = 0, Thoracic_Surgery_total = 0;
	int Dermatology_total = 0, Pediatrics_total = 0, In_vitro_fertilisation_total = 0, Neonatology_total = 0, Bariatric_Surgery_total = 0, Ayurvedic_Medicine_total = 0, Physiotherapy_total = 0;

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null)
	{
	if (object2[0] != null && !time1.equalsIgnoreCase(object2[3].toString()))
	{
	DP = new DashboardSpecialityPojo();
	total = 0;
	Medical_Oncology_Hematology = 0;
	Phlebotomy = 0;
	Radiation_Oncology = 0;
	Critical_Care = 0;
	Cardiology = 0;
	Orthopaedics = 0;
	Liver_Transplant = 0;
	Pediatric_Surgery = 0;
	Cardio_Surg = 0;
	Neurology = 0;
	Endocrinology = 0;
	Laboratory_Services = 0;
	Anaesthesia = 0;
	Ophthalmology = 0;
	Pediatric_Gastro_Hepatology = 0;
	Plastic_Surgery = 0;
	Radiology = 0;
	Nuclear_Medicine = 0;
	Gastroenterology = 0;
	General_Speciality = 0;
	Emergency_Trauma_Services = 0;
	Nephrology = 0;
	GI_Surgery = 0;
	Neurosurgery = 0;
	Urology_Andrology = 0;
	Internal_Medicine = 0;
	Vascular_Endovascular_Surgery = 0;
	Rheumatology = 0;
	Dental = 0;
	Ear_Nose_Throat = 0;
	Respiratory_Sleep_Medicine = 0;
	Pediatric_Cardiology = 0;
	Ortho_Anaesthesia = 0;
	Gynaecology_Obstetrics = 0;
	Executive_Health_Check = 0;
	Head_Neck_Oncology = 0;
	Interventional_Radiology = 0;
	Breast_Services = 0;
	Thoracic_Surgery = 0;
	Dermatology = 0;
	Pediatrics = 0;
	In_vitro_fertilisation = 0;
	Neonatology = 0;
	Bariatric_Surgery = 0;
	Ayurvedic_Medicine = 0;
	Physiotherapy = 0;

	grand_total = 0;
	Medical_Oncology_Hematology_total = 0;
	Phlebotomy_total = 0;
	Radiation_Oncology_total = 0;
	Critical_Care_total = 0;
	Cardiology_total = 0;
	Orthopaedics_total = 0;
	Liver_Transplant_total = 0;
	Pediatric_Surgery_total = 0;
	Cardio_Surg_total = 0;
	Neurology_total = 0;
	Endocrinology_total = 0;
	Laboratory_Services_total = 0;
	Anaesthesia_total = 0;
	Ophthalmology_total = 0;
	Pediatric_Gastro_Hepatology_total = 0;
	Plastic_Surgery_total = 0;
	Radiology_total = 0;
	Nuclear_Medicine_total = 0;
	Gastroenterology_total = 0;
	General_Speciality_total = 0;
	Emergency_Trauma_Services_total = 0;
	Nephrology_total = 0;
	GI_Surgery_total = 0;
	Neurosurgery_total = 0;
	Urology_Andrology_total = 0;
	Internal_Medicine_total = 0;
	Vascular_Endovascular_Surgery_total = 0;
	Rheumatology_total = 0;
	Dental_total = 0;
	Ear_Nose_Throat_total = 0;
	Respiratory_Sleep_Medicine_total = 0;
	Pediatric_Cardiology_total = 0;
	Ortho_Anaesthesia_total = 0;
	Gynaecology_Obstetrics_total = 0;
	Executive_Health_Check_total = 0;
	Head_Neck_Oncology_total = 0;
	Interventional_Radiology_total = 0;
	Breast_Services_total = 0;
	Thoracic_Surgery_total = 0;
	Dermatology_total = 0;
	Pediatrics_total = 0;
	In_vitro_fertilisation_total = 0;
	Neonatology_total = 0;
	Bariatric_Surgery_total = 0;
	Ayurvedic_Medicine_total = 0;
	Physiotherapy_total = 0;

	}

	if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy = Phlebotomy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology = Radiation_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care = Critical_Care + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{

	Cardiology = Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics = Orthopaedics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant = Liver_Transplant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery = Pediatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg = Cardio_Surg + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology = Neurology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology = Endocrinology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services = Laboratory_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia = Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology = Ophthalmology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology = Pediatric_Gastro_Hepatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology = Medical_Oncology_Hematology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery = Plastic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology = Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine = Nuclear_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology = Gastroenterology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality = General_Speciality + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services = Emergency_Trauma_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology = Nephrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery = GI_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery = Neurosurgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology = Urology_Andrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine = Internal_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery = Vascular_Endovascular_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology = Rheumatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental = Dental + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat = Ear_Nose_Throat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine = Respiratory_Sleep_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology = Pediatric_Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia = Ortho_Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics = Gynaecology_Obstetrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check = Executive_Health_Check + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology = Head_Neck_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology = Interventional_Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services = Breast_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery = Thoracic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology = Dermatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics = Pediatrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation = In_vitro_fertilisation + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology = Neonatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery = Bariatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine = Ayurvedic_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy = Physiotherapy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy_total = Phlebotomy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology_total = Radiation_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care_total = Critical_Care_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology_total = Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics_total = Orthopaedics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant_total = Liver_Transplant_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery_total = Pediatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg_total = Cardio_Surg_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology_total = Neurology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology_total = Endocrinology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services_total = Laboratory_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia_total = Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology_total = Ophthalmology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology_total = Pediatric_Gastro_Hepatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology_total = Medical_Oncology_Hematology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery_total = Plastic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology_total = Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine_total = Nuclear_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology_total = Gastroenterology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality_total = General_Speciality_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services_total = Emergency_Trauma_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology_total = Nephrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery_total = GI_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery_total = Neurosurgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology_total = Urology_Andrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine_total = Internal_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery_total = Vascular_Endovascular_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology_total = Rheumatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental_total = Dental_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat_total = Ear_Nose_Throat_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine_total = Respiratory_Sleep_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology_total = Pediatric_Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia_total = Ortho_Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics_total = Gynaecology_Obstetrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check_total = Executive_Health_Check_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology_total = Head_Neck_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology_total = Interventional_Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services_total = Breast_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery_total = Thoracic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology_total = Dermatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics_total = Pediatrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation_total = In_vitro_fertilisation_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology_total = Neonatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && attendedBy.equalsIgnoreCase("-1") && (object2[2].toString().equalsIgnoreCase("Resident") || object2[2].toString().equalsIgnoreCase("Consultant") || object2[2].toString().equalsIgnoreCase("Medical Officer")) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery_total = Bariatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine_total = Ayurvedic_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy_total = Physiotherapy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}
	String mnth[] = object2[3].toString().split("-");
	DP.setTime(mnth[2] + "-" + DateUtil.getDayFromMonth(mnth[1]).substring(0, 3) + "-" + mnth[0].substring(2, 4));
	if (Phlebotomy_total > 0)
	DP.setPhlebotomy_Per(String.valueOf(Math.round((Phlebotomy * 100) / Phlebotomy_total)));
	else
	DP.setPhlebotomy_Per("0");
	if (Radiation_Oncology_total > 0)
	DP.setRadiation_Oncology_Per(String.valueOf(Math.round((Radiation_Oncology * 100) / Radiation_Oncology_total)));
	else
	DP.setRadiation_Oncology_Per("0");
	if (Critical_Care_total > 0)
	DP.setCritical_Care_Per(String.valueOf(Math.round((Critical_Care * 100) / Critical_Care_total)));
	else
	DP.setCritical_Care_Per("0");
	if (Cardiology_total > 0)
	DP.setCardiology_Per(String.valueOf(Math.round((Cardiology * 100) / Cardiology_total)));
	else
	DP.setCardiology_Per("0");
	if (Orthopaedics_total > 0)
	DP.setOrthopaedics_Per(String.valueOf(Math.round((Orthopaedics * 100) / Orthopaedics_total)));
	else
	DP.setOrthopaedics_Per("0");
	if (Liver_Transplant_total > 0)
	DP.setLiver_Transplant_Per(String.valueOf(Math.round((Liver_Transplant * 100) / Liver_Transplant_total)));
	else
	DP.setLiver_Transplant_Per("0");
	if (Pediatric_Surgery_total > 0)
	DP.setPediatric_Surgery_Per(String.valueOf(Math.round((Pediatric_Surgery * 100) / Pediatric_Surgery_total)));
	else
	DP.setPediatric_Surgery_Per("0");
	if (Cardio_Surg_total > 0)
	DP.setCardio_Surg_Per(String.valueOf(Math.round((Cardio_Surg * 100) / Cardio_Surg_total)));
	else
	DP.setCardio_Surg_Per("0");
	if (Neurology_total > 0)
	DP.setNeurology_Per(String.valueOf(Math.round((Neurology * 100) / Neurology_total)));
	else
	DP.setNeurology_Per("0");
	if (Endocrinology_total > 0)
	DP.setEndocrinology_Per(String.valueOf(Math.round((Endocrinology * 100) / Endocrinology_total)));
	else
	DP.setEndocrinology_Per("0");
	if (Laboratory_Services_total > 0)
	DP.setLaboratory_Services_Per(String.valueOf(Math.round((Laboratory_Services * 100) / Laboratory_Services_total)));
	else
	DP.setLaboratory_Services_Per("0");
	if (Anaesthesia_total > 0)
	DP.setAnaesthesia_Per(String.valueOf(Math.round((Anaesthesia * 100) / Anaesthesia_total)));
	else
	DP.setAnaesthesia_Per("0");
	if (Ophthalmology_total > 0)
	DP.setOphthalmology_Per(String.valueOf(Math.round((Ophthalmology * 100) / Ophthalmology_total)));
	else
	DP.setOphthalmology_Per("0");
	if (Pediatric_Gastro_Hepatology_total > 0)
	DP.setPediatric_Gastro_Hepatology_Per(String.valueOf(Math.round((Pediatric_Gastro_Hepatology * 100) / Pediatric_Gastro_Hepatology_total)));
	else
	DP.setPediatric_Gastro_Hepatology_Per("0");
	if (Medical_Oncology_Hematology_total > 0)
	DP.setMedical_Oncology_Hematology_Per(String.valueOf(Math.round((Medical_Oncology_Hematology * 100) / Medical_Oncology_Hematology_total)));
	else
	DP.setMedical_Oncology_Hematology_Per("0");
	if (Plastic_Surgery_total > 0)
	DP.setPlastic_Surgery_Per(String.valueOf(Math.round((Plastic_Surgery * 100) / Plastic_Surgery_total)));
	else
	DP.setPlastic_Surgery_Per("0");
	if (Radiology_total > 0)
	DP.setRadiology_Per(String.valueOf(Math.round((Radiology * 100) / Radiology_total)));
	else
	DP.setRadiology_Per("0");
	if (Nuclear_Medicine_total > 0)
	DP.setNuclear_Medicine_Per(String.valueOf(Math.round((Nuclear_Medicine * 100) / Nuclear_Medicine_total)));
	else
	DP.setNuclear_Medicine_Per("0");
	if (Gastroenterology_total > 0)
	DP.setGastroenterology_Per(String.valueOf(Math.round((Gastroenterology * 100) / Gastroenterology_total)));
	else
	DP.setGastroenterology_Per("0");
	if (General_Speciality_total > 0)
	DP.setGeneral_Speciality_Per(String.valueOf(Math.round((General_Speciality * 100) / General_Speciality_total)));
	else
	DP.setGeneral_Speciality_Per("0");
	if (Emergency_Trauma_Services_total > 0)
	DP.setEmergency_Trauma_Services_Per(String.valueOf(Math.round((Emergency_Trauma_Services * 100) / Emergency_Trauma_Services_total)));
	else
	DP.setEmergency_Trauma_Services_Per("0");
	if (Nephrology_total > 0)
	DP.setNephrology_Per(String.valueOf(Math.round((Nephrology * 100) / Nephrology_total)));
	else
	DP.setNephrology_Per("0");
	if (GI_Surgery_total > 0)
	DP.setGI_Surgery_Per(String.valueOf(Math.round((GI_Surgery * 100) / GI_Surgery_total)));
	else
	DP.setGI_Surgery_Per("0");
	if (Neurosurgery_total > 0)
	DP.setNeurosurgery_Per(String.valueOf(Math.round((Neurosurgery * 100) / Neurosurgery_total)));
	else
	DP.setNeurosurgery_Per("0");
	if (Urology_Andrology_total > 0)
	DP.setUrology_Andrology(String.valueOf(Math.round((Urology_Andrology * 100) / Urology_Andrology_total)));
	else
	DP.setUrology_Andrology("0");
	if (Internal_Medicine_total > 0)
	DP.setInternal_Medicine_Per(String.valueOf(Math.round((Internal_Medicine * 100) / Internal_Medicine_total)));
	else
	DP.setInternal_Medicine_Per("0");
	if (Vascular_Endovascular_Surgery_total > 0)
	DP.setVascular_Endovascular_Surgery_Per(String.valueOf(Math.round((Vascular_Endovascular_Surgery * 100) / Vascular_Endovascular_Surgery_total)));
	else
	DP.setVascular_Endovascular_Surgery_Per("0");
	if (Rheumatology_total > 0)
	DP.setRheumatology_Per(String.valueOf(Math.round((Rheumatology * 100) / Rheumatology_total)));
	else
	DP.setRheumatology_Per("0");
	if (Dental_total > 0)
	DP.setDental_Per(String.valueOf(Math.round((Dental * 100) / Dental_total)));
	else
	DP.setDental_Per("0");
	if (Ear_Nose_Throat_total > 0)
	DP.setEar_Nose_Throat_Per(String.valueOf(Math.round((Ear_Nose_Throat * 100) / Ear_Nose_Throat_total)));
	else
	DP.setEar_Nose_Throat_Per("0");
	if (Respiratory_Sleep_Medicine_total > 0)
	DP.setRespiratory_Sleep_Medicine_Per(String.valueOf(Math.round((Respiratory_Sleep_Medicine * 100) / Respiratory_Sleep_Medicine_total)));
	else
	DP.setRespiratory_Sleep_Medicine_Per("0");
	if (Pediatric_Cardiology_total > 0)
	DP.setPediatric_Cardiology_Per(String.valueOf(Math.round((Pediatric_Cardiology * 100) / Pediatric_Cardiology_total)));
	else
	DP.setPediatric_Cardiology_Per("0");
	if (Ortho_Anaesthesia_total > 0)
	DP.setOrtho_Anaesthesia_Per(String.valueOf(Math.round((Ortho_Anaesthesia * 100) / Ortho_Anaesthesia_total)));
	else
	DP.setOrtho_Anaesthesia_Per("0");
	if (Gynaecology_Obstetrics_total > 0)
	DP.setGynaecology_Obstetrics_Per(String.valueOf(Math.round((Gynaecology_Obstetrics * 100) / Gynaecology_Obstetrics_total)));
	else
	DP.setGynaecology_Obstetrics_Per("0");
	if (Executive_Health_Check_total > 0)
	DP.setExecutive_Health_Check_Per(String.valueOf(Math.round((Executive_Health_Check * 100) / Executive_Health_Check_total)));
	else
	DP.setExecutive_Health_Check_Per("0");
	if (Head_Neck_Oncology_total > 0)
	DP.setHead_Neck_Oncology_Per(String.valueOf(Math.round((Head_Neck_Oncology * 100) / Head_Neck_Oncology_total)));
	else
	DP.setHead_Neck_Oncology_Per("0");
	if (Interventional_Radiology_total > 0)
	DP.setInterventional_Radiology_Per(String.valueOf(Math.round((Interventional_Radiology * 100) / Interventional_Radiology_total)));
	else
	DP.setInterventional_Radiology_Per("0");
	if (Breast_Services_total > 0)
	DP.setBreast_Services_Per(String.valueOf(Math.round((Breast_Services * 100) / Breast_Services_total)));
	else
	DP.setBreast_Services_Per("0");
	if (Thoracic_Surgery_total > 0)
	DP.setThoracic_Surgery_Per(String.valueOf(Math.round((Thoracic_Surgery * 100) / Thoracic_Surgery_total)));
	else
	DP.setThoracic_Surgery_Per("0");
	if (Dermatology_total > 0)
	DP.setDermatology_Per(String.valueOf(Math.round((Dermatology * 100) / Dermatology_total)));
	else
	DP.setDermatology_Per("0");
	if (Pediatrics_total > 0)
	DP.setPediatrics_Per(String.valueOf(Math.round((Pediatrics * 100) / Pediatrics_total)));
	else
	DP.setPediatrics_Per("0");
	if (In_vitro_fertilisation_total > 0)
	DP.setIn_vitro_fertilisation_Per(String.valueOf(Math.round((In_vitro_fertilisation * 100) / In_vitro_fertilisation_total)));
	else
	DP.setIn_vitro_fertilisation_Per("0");
	if (Neonatology_total > 0)
	DP.setNeonatology_Per(String.valueOf(Math.round((Neonatology * 100) / Neonatology_total)));
	else
	DP.setNeonatology_Per("0");
	if (Bariatric_Surgery_total > 0)
	DP.setBariatric_Surgery_Per(String.valueOf(Math.round((Bariatric_Surgery * 100) / Bariatric_Surgery_total)));
	else
	DP.setBariatric_Surgery_Per("0");
	if (Ayurvedic_Medicine_total > 0)
	DP.setAyurvedic_Medicine_Per(String.valueOf(Math.round((Ayurvedic_Medicine * 100) / Ayurvedic_Medicine_total)));
	else
	DP.setAyurvedic_Medicine_Per("0");
	if (Physiotherapy_total > 0)
	DP.setPhysiotherapy_Per(String.valueOf(Math.round((Physiotherapy * 100) / Physiotherapy_total)));
	else
	DP.setPhysiotherapy_Per("0");
	if (grand_total > 0)
	DP.setTotal_Per(String.valueOf(Math.round((total * 100) / grand_total)));
	else
	DP.setTotal_Per("0");
	if (attendedBy.equalsIgnoreCase("-1"))
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy_total));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology_total));
	DP.setCritical_Care(String.valueOf(Critical_Care_total));
	DP.setCardiology(String.valueOf(Cardiology_total));
	DP.setOrthopaedics(String.valueOf(Orthopaedics_total));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant_total));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery_total));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg_total));
	DP.setNeurology(String.valueOf(Neurology_total));
	DP.setEndocrinology(String.valueOf(Endocrinology_total));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services_total));
	DP.setAnaesthesia(String.valueOf(Anaesthesia_total));
	DP.setOphthalmology(String.valueOf(Ophthalmology_total));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology_total));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology_total));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery_total));
	DP.setRadiology(String.valueOf(Radiology_total));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine_total));
	DP.setGastroenterology(String.valueOf(Gastroenterology_total));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality_total));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services_total));
	DP.setNephrology(String.valueOf(Nephrology_total));
	DP.setGI_Surgery(String.valueOf(GI_Surgery_total));
	DP.setNeurosurgery(String.valueOf(Neurosurgery_total));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology_total));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine_total));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery_total));
	DP.setRheumatology(String.valueOf(Rheumatology_total));
	DP.setDental(String.valueOf(Dental_total));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat_total));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine_total));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology_total));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia_total));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics_total));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check_total));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology_total));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology_total));
	DP.setBreast_Services(String.valueOf(Breast_Services_total));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery_total));
	DP.setDermatology(String.valueOf(Dermatology_total));
	DP.setPediatrics(String.valueOf(Pediatrics_total));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation_total));
	DP.setNeonatology(String.valueOf(Neonatology_total));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery_total));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine_total));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy_total));
	DP.setTotal(String.valueOf(grand_total));
	} else
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology));
	DP.setCritical_Care(String.valueOf(Critical_Care));
	DP.setCardiology(String.valueOf(Cardiology));
	DP.setOrthopaedics(String.valueOf(Orthopaedics));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg));
	DP.setNeurology(String.valueOf(Neurology));
	DP.setEndocrinology(String.valueOf(Endocrinology));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services));
	DP.setAnaesthesia(String.valueOf(Anaesthesia));
	DP.setOphthalmology(String.valueOf(Ophthalmology));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery));
	DP.setRadiology(String.valueOf(Radiology));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine));
	DP.setGastroenterology(String.valueOf(Gastroenterology));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services));
	DP.setNephrology(String.valueOf(Nephrology));
	DP.setGI_Surgery(String.valueOf(GI_Surgery));
	DP.setNeurosurgery(String.valueOf(Neurosurgery));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery));
	DP.setRheumatology(String.valueOf(Rheumatology));
	DP.setDental(String.valueOf(Dental));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology));
	DP.setBreast_Services(String.valueOf(Breast_Services));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery));
	DP.setDermatology(String.valueOf(Dermatology));
	DP.setPediatrics(String.valueOf(Pediatrics));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation));
	DP.setNeonatology(String.valueOf(Neonatology));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy));
	DP.setTotal(String.valueOf(total));
	}

	if ((total != 0 || grand_total != 0) && !time1.equalsIgnoreCase(object2[3].toString()))
	specCounterList.add(DP);
	dashSpecObj.setDashList(specCounterList);
	time1 = object2[3].toString();

	}
	}

	}

	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralSpecialityCountersMonth()
	{
	dashSpecObj = new DashboardSpecialityPojo();
	DashboardSpecialityPojo DP = null;
	try
	{
	specCounterList = new ArrayList<DashboardSpecialityPojo>();
	List referral_status_list = new ArrayList();
	List monthList = new ArrayList();

	monthList.add("Jan");
	monthList.add("Feb");
	monthList.add("Mar");
	monthList.add("Apr");
	monthList.add("May");
	monthList.add("Jun");
	monthList.add("Jul");
	monthList.add("Aug");
	monthList.add("Sep");
	monthList.add("Oct");
	monthList.add("Nov");
	monthList.add("Dec");
	String time = null;
	for (Iterator iterator = monthList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = object.toString() + "-" + fromYear.substring(2, 4);
	 
	int total = 0, Medical_Oncology_Hematology = 0, Phlebotomy = 0, Radiation_Oncology = 0, Critical_Care = 0, Cardiology = 0, Orthopaedics = 0, Liver_Transplant = 0, Pediatric_Surgery = 0, Cardio_Surg = 0, Neurology = 0, Endocrinology = 0;
	int Laboratory_Services = 0, Anaesthesia = 0, Ophthalmology = 0, Pediatric_Gastro_Hepatology = 0, Plastic_Surgery = 0, Radiology = 0, Nuclear_Medicine = 0, Gastroenterology = 0, General_Speciality = 0, Emergency_Trauma_Services = 0;
	int Nephrology = 0, GI_Surgery = 0, Neurosurgery = 0, Urology_Andrology = 0, Internal_Medicine = 0, Vascular_Endovascular_Surgery = 0, Rheumatology = 0, Dental = 0, Ear_Nose_Throat = 0, Respiratory_Sleep_Medicine = 0;
	int Pediatric_Cardiology = 0, Ortho_Anaesthesia = 0, Gynaecology_Obstetrics = 0, Executive_Health_Check = 0, Head_Neck_Oncology = 0, Interventional_Radiology = 0, Breast_Services = 0, Thoracic_Surgery = 0;
	int Dermatology = 0, Pediatrics = 0, In_vitro_fertilisation = 0, Neonatology = 0, Bariatric_Surgery = 0, Ayurvedic_Medicine = 0, Physiotherapy = 0;

	int grand_total = 0, Medical_Oncology_Hematology_total = 0, Phlebotomy_total = 0, Radiation_Oncology_total = 0, Critical_Care_total = 0, Cardiology_total = 0, Orthopaedics_total = 0, Liver_Transplant_total = 0, Pediatric_Surgery_total = 0, Cardio_Surg_total = 0, Neurology_total = 0, Endocrinology_total = 0;
	int Laboratory_Services_total = 0, Anaesthesia_total = 0, Ophthalmology_total = 0, Pediatric_Gastro_Hepatology_total = 0, Plastic_Surgery_total = 0, Radiology_total = 0, Nuclear_Medicine_total = 0, Gastroenterology_total = 0, General_Speciality_total = 0, Emergency_Trauma_Services_total = 0;
	int Nephrology_total = 0, GI_Surgery_total = 0, Neurosurgery_total = 0, Urology_Andrology_total = 0, Internal_Medicine_total = 0, Vascular_Endovascular_Surgery_total = 0, Rheumatology_total = 0, Dental_total = 0, Ear_Nose_Throat_total = 0, Respiratory_Sleep_Medicine_total = 0;
	int Pediatric_Cardiology_total = 0, Ortho_Anaesthesia_total = 0, Gynaecology_Obstetrics_total = 0, Executive_Health_Check_total = 0, Head_Neck_Oncology_total = 0, Interventional_Radiology_total = 0, Breast_Services_total = 0, Thoracic_Surgery_total = 0;
	int Dermatology_total = 0, Pediatrics_total = 0, In_vitro_fertilisation_total = 0, Neonatology_total = 0, Bariatric_Surgery_total = 0, Ayurvedic_Medicine_total = 0, Physiotherapy_total = 0;

	DP = new DashboardSpecialityPojo();

	referral_status_list = getCounterDataForReferralSpecialityForMonth(object.toString(), fromYear, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy = Phlebotomy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology = Radiation_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care = Critical_Care + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology = Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics = Orthopaedics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant = Liver_Transplant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery = Pediatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg = Cardio_Surg + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology = Neurology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology = Endocrinology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services = Laboratory_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia = Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology = Ophthalmology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology = Pediatric_Gastro_Hepatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology = Medical_Oncology_Hematology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery = Plastic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology = Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine = Nuclear_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology = Gastroenterology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality = General_Speciality + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services = Emergency_Trauma_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology = Nephrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery = GI_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery = Neurosurgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology = Urology_Andrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine = Internal_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery = Vascular_Endovascular_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology = Rheumatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental = Dental + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat = Ear_Nose_Throat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine = Respiratory_Sleep_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology = Pediatric_Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia = Ortho_Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics = Gynaecology_Obstetrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check = Executive_Health_Check + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology = Head_Neck_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology = Interventional_Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services = Breast_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery = Thoracic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology = Dermatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics = Pediatrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation = In_vitro_fertilisation + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology = Neonatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery = Bariatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine = Ayurvedic_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy = Physiotherapy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy_total = Phlebotomy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology_total = Radiation_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care_total = Critical_Care_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology_total = Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics_total = Orthopaedics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant_total = Liver_Transplant_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery_total = Pediatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg_total = Cardio_Surg_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology_total = Neurology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology_total = Endocrinology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services_total = Laboratory_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia_total = Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology_total = Ophthalmology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology_total = Pediatric_Gastro_Hepatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology_total = Medical_Oncology_Hematology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery_total = Plastic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology_total = Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine_total = Nuclear_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology_total = Gastroenterology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality_total = General_Speciality_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services_total = Emergency_Trauma_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology_total = Nephrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery_total = GI_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery_total = Neurosurgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology_total = Urology_Andrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine_total = Internal_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery_total = Vascular_Endovascular_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology_total = Rheumatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental_total = Dental_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat_total = Ear_Nose_Throat_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine_total = Respiratory_Sleep_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology_total = Pediatric_Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia_total = Ortho_Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics_total = Gynaecology_Obstetrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check_total = Executive_Health_Check_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology_total = Head_Neck_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology_total = Interventional_Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services_total = Breast_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery_total = Thoracic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology_total = Dermatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics_total = Pediatrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation_total = In_vitro_fertilisation_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology_total = Neonatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && attendedBy.equalsIgnoreCase("-1") && (object2[2].toString().equalsIgnoreCase("Resident") || object2[2].toString().equalsIgnoreCase("Consultant") || object2[2].toString().equalsIgnoreCase("Medical Officer")) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery_total = Bariatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine_total = Ayurvedic_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy_total = Physiotherapy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	}

	}

	if (Phlebotomy_total > 0)
	DP.setPhlebotomy_Per(String.valueOf(Math.round((Phlebotomy * 100) / Phlebotomy_total)));
	else
	DP.setPhlebotomy_Per("0");
	if (Radiation_Oncology_total > 0)
	DP.setRadiation_Oncology_Per(String.valueOf(Math.round((Radiation_Oncology * 100) / Radiation_Oncology_total)));
	else
	DP.setRadiation_Oncology_Per("0");
	if (Critical_Care_total > 0)
	DP.setCritical_Care_Per(String.valueOf(Math.round((Critical_Care * 100) / Critical_Care_total)));
	else
	DP.setCritical_Care_Per("0");
	if (Cardiology_total > 0)
	DP.setCardiology_Per(String.valueOf(Math.round((Cardiology * 100) / Cardiology_total)));
	else
	DP.setCardiology_Per("0");
	if (Orthopaedics_total > 0)
	DP.setOrthopaedics_Per(String.valueOf(Math.round((Orthopaedics * 100) / Orthopaedics_total)));
	else
	DP.setOrthopaedics_Per("0");
	if (Liver_Transplant_total > 0)
	DP.setLiver_Transplant_Per(String.valueOf(Math.round((Liver_Transplant * 100) / Liver_Transplant_total)));
	else
	DP.setLiver_Transplant_Per("0");
	if (Pediatric_Surgery_total > 0)
	DP.setPediatric_Surgery_Per(String.valueOf(Math.round((Pediatric_Surgery * 100) / Pediatric_Surgery_total)));
	else
	DP.setPediatric_Surgery_Per("0");
	if (Cardio_Surg_total > 0)
	DP.setCardio_Surg_Per(String.valueOf(Math.round((Cardio_Surg * 100) / Cardio_Surg_total)));
	else
	DP.setCardio_Surg_Per("0");
	if (Neurology_total > 0)
	DP.setNeurology_Per(String.valueOf(Math.round((Neurology * 100) / Neurology_total)));
	else
	DP.setNeurology_Per("0");
	if (Endocrinology_total > 0)
	DP.setEndocrinology_Per(String.valueOf(Math.round((Endocrinology * 100) / Endocrinology_total)));
	else
	DP.setEndocrinology_Per("0");
	if (Laboratory_Services_total > 0)
	DP.setLaboratory_Services_Per(String.valueOf(Math.round((Laboratory_Services * 100) / Laboratory_Services_total)));
	else
	DP.setLaboratory_Services_Per("0");
	if (Anaesthesia_total > 0)
	DP.setAnaesthesia_Per(String.valueOf(Math.round((Anaesthesia * 100) / Anaesthesia_total)));
	else
	DP.setAnaesthesia_Per("0");
	if (Ophthalmology_total > 0)
	DP.setOphthalmology_Per(String.valueOf(Math.round((Ophthalmology * 100) / Ophthalmology_total)));
	else
	DP.setOphthalmology_Per("0");
	if (Pediatric_Gastro_Hepatology_total > 0)
	DP.setPediatric_Gastro_Hepatology_Per(String.valueOf(Math.round((Pediatric_Gastro_Hepatology * 100) / Pediatric_Gastro_Hepatology_total)));
	else
	DP.setPediatric_Gastro_Hepatology_Per("0");
	if (Medical_Oncology_Hematology_total > 0)
	DP.setMedical_Oncology_Hematology_Per(String.valueOf(Math.round((Medical_Oncology_Hematology * 100) / Medical_Oncology_Hematology_total)));
	else
	DP.setMedical_Oncology_Hematology_Per("0");
	if (Plastic_Surgery_total > 0)
	DP.setPlastic_Surgery_Per(String.valueOf(Math.round((Plastic_Surgery * 100) / Plastic_Surgery_total)));
	else
	DP.setPlastic_Surgery_Per("0");
	if (Radiology_total > 0)
	DP.setRadiology_Per(String.valueOf(Math.round((Radiology * 100) / Radiology_total)));
	else
	DP.setRadiology_Per("0");
	if (Nuclear_Medicine_total > 0)
	DP.setNuclear_Medicine_Per(String.valueOf(Math.round((Nuclear_Medicine * 100) / Nuclear_Medicine_total)));
	else
	DP.setNuclear_Medicine_Per("0");
	if (Gastroenterology_total > 0)
	DP.setGastroenterology_Per(String.valueOf(Math.round((Gastroenterology * 100) / Gastroenterology_total)));
	else
	DP.setGastroenterology_Per("0");
	if (General_Speciality_total > 0)
	DP.setGeneral_Speciality_Per(String.valueOf(Math.round((General_Speciality * 100) / General_Speciality_total)));
	else
	DP.setGeneral_Speciality_Per("0");
	if (Emergency_Trauma_Services_total > 0)
	DP.setEmergency_Trauma_Services_Per(String.valueOf(Math.round((Emergency_Trauma_Services * 100) / Emergency_Trauma_Services_total)));
	else
	DP.setEmergency_Trauma_Services_Per("0");
	if (Nephrology_total > 0)
	DP.setNephrology_Per(String.valueOf(Math.round((Nephrology * 100) / Nephrology_total)));
	else
	DP.setNephrology_Per("0");
	if (GI_Surgery_total > 0)
	DP.setGI_Surgery_Per(String.valueOf(Math.round((GI_Surgery * 100) / GI_Surgery_total)));
	else
	DP.setGI_Surgery_Per("0");
	if (Neurosurgery_total > 0)
	DP.setNeurosurgery_Per(String.valueOf(Math.round((Neurosurgery * 100) / Neurosurgery_total)));
	else
	DP.setNeurosurgery_Per("0");
	if (Urology_Andrology_total > 0)
	DP.setUrology_Andrology(String.valueOf(Math.round((Urology_Andrology * 100) / Urology_Andrology_total)));
	else
	DP.setUrology_Andrology("0");
	if (Internal_Medicine_total > 0)
	DP.setInternal_Medicine_Per(String.valueOf(Math.round((Internal_Medicine * 100) / Internal_Medicine_total)));
	else
	DP.setInternal_Medicine_Per("0");
	if (Vascular_Endovascular_Surgery_total > 0)
	DP.setVascular_Endovascular_Surgery_Per(String.valueOf(Math.round((Vascular_Endovascular_Surgery * 100) / Vascular_Endovascular_Surgery_total)));
	else
	DP.setVascular_Endovascular_Surgery_Per("0");
	if (Rheumatology_total > 0)
	DP.setRheumatology_Per(String.valueOf(Math.round((Rheumatology * 100) / Rheumatology_total)));
	else
	DP.setRheumatology_Per("0");
	if (Dental_total > 0)
	DP.setDental_Per(String.valueOf(Math.round((Dental * 100) / Dental_total)));
	else
	DP.setDental_Per("0");
	if (Ear_Nose_Throat_total > 0)
	DP.setEar_Nose_Throat_Per(String.valueOf(Math.round((Ear_Nose_Throat * 100) / Ear_Nose_Throat_total)));
	else
	DP.setEar_Nose_Throat_Per("0");
	if (Respiratory_Sleep_Medicine_total > 0)
	DP.setRespiratory_Sleep_Medicine_Per(String.valueOf(Math.round((Respiratory_Sleep_Medicine * 100) / Respiratory_Sleep_Medicine_total)));
	else
	DP.setRespiratory_Sleep_Medicine_Per("0");
	if (Pediatric_Cardiology_total > 0)
	DP.setPediatric_Cardiology_Per(String.valueOf(Math.round((Pediatric_Cardiology * 100) / Pediatric_Cardiology_total)));
	else
	DP.setPediatric_Cardiology_Per("0");
	if (Ortho_Anaesthesia_total > 0)
	DP.setOrtho_Anaesthesia_Per(String.valueOf(Math.round((Ortho_Anaesthesia * 100) / Ortho_Anaesthesia_total)));
	else
	DP.setOrtho_Anaesthesia_Per("0");
	if (Gynaecology_Obstetrics_total > 0)
	DP.setGynaecology_Obstetrics_Per(String.valueOf(Math.round((Gynaecology_Obstetrics * 100) / Gynaecology_Obstetrics_total)));
	else
	DP.setGynaecology_Obstetrics_Per("0");
	if (Executive_Health_Check_total > 0)
	DP.setExecutive_Health_Check_Per(String.valueOf(Math.round((Executive_Health_Check * 100) / Executive_Health_Check_total)));
	else
	DP.setExecutive_Health_Check_Per("0");
	if (Head_Neck_Oncology_total > 0)
	DP.setHead_Neck_Oncology_Per(String.valueOf(Math.round((Head_Neck_Oncology * 100) / Head_Neck_Oncology_total)));
	else
	DP.setHead_Neck_Oncology_Per("0");
	if (Interventional_Radiology_total > 0)
	DP.setInterventional_Radiology_Per(String.valueOf(Math.round((Interventional_Radiology * 100) / Interventional_Radiology_total)));
	else
	DP.setInterventional_Radiology_Per("0");
	if (Breast_Services_total > 0)
	DP.setBreast_Services_Per(String.valueOf(Math.round((Breast_Services * 100) / Breast_Services_total)));
	else
	DP.setBreast_Services_Per("0");
	if (Thoracic_Surgery_total > 0)
	DP.setThoracic_Surgery_Per(String.valueOf(Math.round((Thoracic_Surgery * 100) / Thoracic_Surgery_total)));
	else
	DP.setThoracic_Surgery_Per("0");
	if (Dermatology_total > 0)
	DP.setDermatology_Per(String.valueOf(Math.round((Dermatology * 100) / Dermatology_total)));
	else
	DP.setDermatology_Per("0");
	if (Pediatrics_total > 0)
	DP.setPediatrics_Per(String.valueOf(Math.round((Pediatrics * 100) / Pediatrics_total)));
	else
	DP.setPediatrics_Per("0");
	if (In_vitro_fertilisation_total > 0)
	DP.setIn_vitro_fertilisation_Per(String.valueOf(Math.round((In_vitro_fertilisation * 100) / In_vitro_fertilisation_total)));
	else
	DP.setIn_vitro_fertilisation_Per("0");
	if (Neonatology_total > 0)
	DP.setNeonatology_Per(String.valueOf(Math.round((Neonatology * 100) / Neonatology_total)));
	else
	DP.setNeonatology_Per("0");
	if (Bariatric_Surgery_total > 0)
	DP.setBariatric_Surgery_Per(String.valueOf(Math.round((Bariatric_Surgery * 100) / Bariatric_Surgery_total)));
	else
	DP.setBariatric_Surgery_Per("0");
	if (Ayurvedic_Medicine_total > 0)
	DP.setAyurvedic_Medicine_Per(String.valueOf(Math.round((Ayurvedic_Medicine * 100) / Ayurvedic_Medicine_total)));
	else
	DP.setAyurvedic_Medicine_Per("0");
	if (Physiotherapy_total > 0)
	DP.setPhysiotherapy_Per(String.valueOf(Math.round((Physiotherapy * 100) / Physiotherapy_total)));
	else
	DP.setPhysiotherapy_Per("0");
	if (grand_total > 0)
	DP.setTotal_Per(String.valueOf(Math.round((total * 100) / grand_total)));
	else
	DP.setTotal_Per("0");

	DP.setTime(time);
	if (attendedBy.equalsIgnoreCase("-1"))
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy_total));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology_total));
	DP.setCritical_Care(String.valueOf(Critical_Care_total));
	DP.setCardiology(String.valueOf(Cardiology_total));
	DP.setOrthopaedics(String.valueOf(Orthopaedics_total));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant_total));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery_total));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg_total));
	DP.setNeurology(String.valueOf(Neurology_total));
	DP.setEndocrinology(String.valueOf(Endocrinology_total));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services_total));
	DP.setAnaesthesia(String.valueOf(Anaesthesia_total));
	DP.setOphthalmology(String.valueOf(Ophthalmology_total));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology_total));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology_total));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery_total));
	DP.setRadiology(String.valueOf(Radiology_total));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine_total));
	DP.setGastroenterology(String.valueOf(Gastroenterology_total));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality_total));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services_total));
	DP.setNephrology(String.valueOf(Nephrology_total));
	DP.setGI_Surgery(String.valueOf(GI_Surgery_total));
	DP.setNeurosurgery(String.valueOf(Neurosurgery_total));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology_total));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine_total));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery_total));
	DP.setRheumatology(String.valueOf(Rheumatology_total));
	DP.setDental(String.valueOf(Dental_total));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat_total));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine_total));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology_total));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia_total));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics_total));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check_total));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology_total));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology_total));
	DP.setBreast_Services(String.valueOf(Breast_Services_total));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery_total));
	DP.setDermatology(String.valueOf(Dermatology_total));
	DP.setPediatrics(String.valueOf(Pediatrics_total));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation_total));
	DP.setNeonatology(String.valueOf(Neonatology_total));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery_total));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine_total));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy_total));
	DP.setTotal(String.valueOf(grand_total));
	} else
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology));
	DP.setCritical_Care(String.valueOf(Critical_Care));
	DP.setCardiology(String.valueOf(Cardiology));
	DP.setOrthopaedics(String.valueOf(Orthopaedics));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg));
	DP.setNeurology(String.valueOf(Neurology));
	DP.setEndocrinology(String.valueOf(Endocrinology));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services));
	DP.setAnaesthesia(String.valueOf(Anaesthesia));
	DP.setOphthalmology(String.valueOf(Ophthalmology));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery));
	DP.setRadiology(String.valueOf(Radiology));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine));
	DP.setGastroenterology(String.valueOf(Gastroenterology));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services));
	DP.setNephrology(String.valueOf(Nephrology));
	DP.setGI_Surgery(String.valueOf(GI_Surgery));
	DP.setNeurosurgery(String.valueOf(Neurosurgery));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery));
	DP.setRheumatology(String.valueOf(Rheumatology));
	DP.setDental(String.valueOf(Dental));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology));
	DP.setBreast_Services(String.valueOf(Breast_Services));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery));
	DP.setDermatology(String.valueOf(Dermatology));
	DP.setPediatrics(String.valueOf(Pediatrics));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation));
	DP.setNeonatology(String.valueOf(Neonatology));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy));
	DP.setTotal(String.valueOf(total));
	}
	specCounterList.add(DP);
	dashSpecObj.setDashList(specCounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralSpecialityCountersYear()
	{
	dashSpecObj = new DashboardSpecialityPojo();
	DashboardSpecialityPojo DP = null;
	try
	{
	specCounterList = new ArrayList<DashboardSpecialityPojo>();
	List referral_status_list = new ArrayList();
	int length = Integer.parseInt(toYear) - Integer.parseInt(fromYear);
	String time = null;
	for (int a = 0; a <= length; a++)
	{
	time = String.valueOf(Integer.parseInt(fromYear) + a);
	int total = 0, Medical_Oncology_Hematology = 0, Phlebotomy = 0, Radiation_Oncology = 0, Critical_Care = 0, Cardiology = 0, Orthopaedics = 0, Liver_Transplant = 0, Pediatric_Surgery = 0, Cardio_Surg = 0, Neurology = 0, Endocrinology = 0;
	int Laboratory_Services = 0, Anaesthesia = 0, Ophthalmology = 0, Pediatric_Gastro_Hepatology = 0, Plastic_Surgery = 0, Radiology = 0, Nuclear_Medicine = 0, Gastroenterology = 0, General_Speciality = 0, Emergency_Trauma_Services = 0;
	int Nephrology = 0, GI_Surgery = 0, Neurosurgery = 0, Urology_Andrology = 0, Internal_Medicine = 0, Vascular_Endovascular_Surgery = 0, Rheumatology = 0, Dental = 0, Ear_Nose_Throat = 0, Respiratory_Sleep_Medicine = 0;
	int Pediatric_Cardiology = 0, Ortho_Anaesthesia = 0, Gynaecology_Obstetrics = 0, Executive_Health_Check = 0, Head_Neck_Oncology = 0, Interventional_Radiology = 0, Breast_Services = 0, Thoracic_Surgery = 0;
	int Dermatology = 0, Pediatrics = 0, In_vitro_fertilisation = 0, Neonatology = 0, Bariatric_Surgery = 0, Ayurvedic_Medicine = 0, Physiotherapy = 0;

	int grand_total = 0, Medical_Oncology_Hematology_total = 0, Phlebotomy_total = 0, Radiation_Oncology_total = 0, Critical_Care_total = 0, Cardiology_total = 0, Orthopaedics_total = 0, Liver_Transplant_total = 0, Pediatric_Surgery_total = 0, Cardio_Surg_total = 0, Neurology_total = 0, Endocrinology_total = 0;
	int Laboratory_Services_total = 0, Anaesthesia_total = 0, Ophthalmology_total = 0, Pediatric_Gastro_Hepatology_total = 0, Plastic_Surgery_total = 0, Radiology_total = 0, Nuclear_Medicine_total = 0, Gastroenterology_total = 0, General_Speciality_total = 0, Emergency_Trauma_Services_total = 0;
	int Nephrology_total = 0, GI_Surgery_total = 0, Neurosurgery_total = 0, Urology_Andrology_total = 0, Internal_Medicine_total = 0, Vascular_Endovascular_Surgery_total = 0, Rheumatology_total = 0, Dental_total = 0, Ear_Nose_Throat_total = 0, Respiratory_Sleep_Medicine_total = 0;
	int Pediatric_Cardiology_total = 0, Ortho_Anaesthesia_total = 0, Gynaecology_Obstetrics_total = 0, Executive_Health_Check_total = 0, Head_Neck_Oncology_total = 0, Interventional_Radiology_total = 0, Breast_Services_total = 0, Thoracic_Surgery_total = 0;
	int Dermatology_total = 0, Pediatrics_total = 0, In_vitro_fertilisation_total = 0, Neonatology_total = 0, Bariatric_Surgery_total = 0, Ayurvedic_Medicine_total = 0, Physiotherapy_total = 0;

	DP = new DashboardSpecialityPojo();

	referral_status_list = getCounterDataForReferralSpecialityForYear(time, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy = Phlebotomy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology = Radiation_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care = Critical_Care + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{

	Cardiology = Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics = Orthopaedics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant = Liver_Transplant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery = Pediatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg = Cardio_Surg + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology = Neurology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology = Endocrinology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services = Laboratory_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia = Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology = Ophthalmology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology = Pediatric_Gastro_Hepatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology = Medical_Oncology_Hematology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery = Plastic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology = Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine = Nuclear_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology = Gastroenterology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality = General_Speciality + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services = Emergency_Trauma_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology = Nephrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery = GI_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery = Neurosurgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology = Urology_Andrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine = Internal_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery = Vascular_Endovascular_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology = Rheumatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental = Dental + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat = Ear_Nose_Throat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine = Respiratory_Sleep_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology = Pediatric_Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia = Ortho_Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics = Gynaecology_Obstetrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check = Executive_Health_Check + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology = Head_Neck_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology = Interventional_Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services = Breast_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery = Thoracic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology = Dermatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics = Pediatrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation = In_vitro_fertilisation + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology = Neonatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery = Bariatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine = Ayurvedic_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy = Physiotherapy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy_total = Phlebotomy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology_total = Radiation_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care_total = Critical_Care_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology_total = Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics_total = Orthopaedics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant_total = Liver_Transplant_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery_total = Pediatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg_total = Cardio_Surg_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology_total = Neurology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology_total = Endocrinology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services_total = Laboratory_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia_total = Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology_total = Ophthalmology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology_total = Pediatric_Gastro_Hepatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology_total = Medical_Oncology_Hematology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery_total = Plastic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology_total = Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine_total = Nuclear_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology_total = Gastroenterology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality_total = General_Speciality_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services_total = Emergency_Trauma_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology_total = Nephrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery_total = GI_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery_total = Neurosurgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology_total = Urology_Andrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine_total = Internal_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery_total = Vascular_Endovascular_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology_total = Rheumatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental_total = Dental_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat_total = Ear_Nose_Throat_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine_total = Respiratory_Sleep_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology_total = Pediatric_Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia_total = Ortho_Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics_total = Gynaecology_Obstetrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check_total = Executive_Health_Check_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology_total = Head_Neck_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology_total = Interventional_Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services_total = Breast_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery_total = Thoracic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology_total = Dermatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics_total = Pediatrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation_total = In_vitro_fertilisation_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology_total = Neonatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && attendedBy.equalsIgnoreCase("-1") && (object2[2].toString().equalsIgnoreCase("Resident") || object2[2].toString().equalsIgnoreCase("Consultant") || object2[2].toString().equalsIgnoreCase("Medical Officer")) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery_total = Bariatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine_total = Ayurvedic_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy_total = Physiotherapy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	}

	}
	DP.setTime("Year-" + time);
	if (Phlebotomy_total > 0)
	DP.setPhlebotomy_Per(String.valueOf(Math.round((Phlebotomy * 100) / Phlebotomy_total)));
	else
	DP.setPhlebotomy_Per("0");
	if (Radiation_Oncology_total > 0)
	DP.setRadiation_Oncology_Per(String.valueOf(Math.round((Radiation_Oncology * 100) / Radiation_Oncology_total)));
	else
	DP.setRadiation_Oncology_Per("0");
	if (Critical_Care_total > 0)
	DP.setCritical_Care_Per(String.valueOf(Math.round((Critical_Care * 100) / Critical_Care_total)));
	else
	DP.setCritical_Care_Per("0");
	if (Cardiology_total > 0)
	DP.setCardiology_Per(String.valueOf(Math.round((Cardiology * 100) / Cardiology_total)));
	else
	DP.setCardiology_Per("0");
	if (Orthopaedics_total > 0)
	DP.setOrthopaedics_Per(String.valueOf(Math.round((Orthopaedics * 100) / Orthopaedics_total)));
	else
	DP.setOrthopaedics_Per("0");
	if (Liver_Transplant_total > 0)
	DP.setLiver_Transplant_Per(String.valueOf(Math.round((Liver_Transplant * 100) / Liver_Transplant_total)));
	else
	DP.setLiver_Transplant_Per("0");
	if (Pediatric_Surgery_total > 0)
	DP.setPediatric_Surgery_Per(String.valueOf(Math.round((Pediatric_Surgery * 100) / Pediatric_Surgery_total)));
	else
	DP.setPediatric_Surgery_Per("0");
	if (Cardio_Surg_total > 0)
	DP.setCardio_Surg_Per(String.valueOf(Math.round((Cardio_Surg * 100) / Cardio_Surg_total)));
	else
	DP.setCardio_Surg_Per("0");
	if (Neurology_total > 0)
	DP.setNeurology_Per(String.valueOf(Math.round((Neurology * 100) / Neurology_total)));
	else
	DP.setNeurology_Per("0");
	if (Endocrinology_total > 0)
	DP.setEndocrinology_Per(String.valueOf(Math.round((Endocrinology * 100) / Endocrinology_total)));
	else
	DP.setEndocrinology_Per("0");
	if (Laboratory_Services_total > 0)
	DP.setLaboratory_Services_Per(String.valueOf(Math.round((Laboratory_Services * 100) / Laboratory_Services_total)));
	else
	DP.setLaboratory_Services_Per("0");
	if (Anaesthesia_total > 0)
	DP.setAnaesthesia_Per(String.valueOf(Math.round((Anaesthesia * 100) / Anaesthesia_total)));
	else
	DP.setAnaesthesia_Per("0");
	if (Ophthalmology_total > 0)
	DP.setOphthalmology_Per(String.valueOf(Math.round((Ophthalmology * 100) / Ophthalmology_total)));
	else
	DP.setOphthalmology_Per("0");
	if (Pediatric_Gastro_Hepatology_total > 0)
	DP.setPediatric_Gastro_Hepatology_Per(String.valueOf(Math.round((Pediatric_Gastro_Hepatology * 100) / Pediatric_Gastro_Hepatology_total)));
	else
	DP.setPediatric_Gastro_Hepatology_Per("0");
	if (Medical_Oncology_Hematology_total > 0)
	DP.setMedical_Oncology_Hematology_Per(String.valueOf(Math.round((Medical_Oncology_Hematology * 100) / Medical_Oncology_Hematology_total)));
	else
	DP.setMedical_Oncology_Hematology_Per("0");
	if (Plastic_Surgery_total > 0)
	DP.setPlastic_Surgery_Per(String.valueOf(Math.round((Plastic_Surgery * 100) / Plastic_Surgery_total)));
	else
	DP.setPlastic_Surgery_Per("0");
	if (Radiology_total > 0)
	DP.setRadiology_Per(String.valueOf(Math.round((Radiology * 100) / Radiology_total)));
	else
	DP.setRadiology_Per("0");
	if (Nuclear_Medicine_total > 0)
	DP.setNuclear_Medicine_Per(String.valueOf(Math.round((Nuclear_Medicine * 100) / Nuclear_Medicine_total)));
	else
	DP.setNuclear_Medicine_Per("0");
	if (Gastroenterology_total > 0)
	DP.setGastroenterology_Per(String.valueOf(Math.round((Gastroenterology * 100) / Gastroenterology_total)));
	else
	DP.setGastroenterology_Per("0");
	if (General_Speciality_total > 0)
	DP.setGeneral_Speciality_Per(String.valueOf(Math.round((General_Speciality * 100) / General_Speciality_total)));
	else
	DP.setGeneral_Speciality_Per("0");
	if (Emergency_Trauma_Services_total > 0)
	DP.setEmergency_Trauma_Services_Per(String.valueOf(Math.round((Emergency_Trauma_Services * 100) / Emergency_Trauma_Services_total)));
	else
	DP.setEmergency_Trauma_Services_Per("0");
	if (Nephrology_total > 0)
	DP.setNephrology_Per(String.valueOf(Math.round((Nephrology * 100) / Nephrology_total)));
	else
	DP.setNephrology_Per("0");
	if (GI_Surgery_total > 0)
	DP.setGI_Surgery_Per(String.valueOf(Math.round((GI_Surgery * 100) / GI_Surgery_total)));
	else
	DP.setGI_Surgery_Per("0");
	if (Neurosurgery_total > 0)
	DP.setNeurosurgery_Per(String.valueOf(Math.round((Neurosurgery * 100) / Neurosurgery_total)));
	else
	DP.setNeurosurgery_Per("0");
	if (Urology_Andrology_total > 0)
	DP.setUrology_Andrology(String.valueOf(Math.round((Urology_Andrology * 100) / Urology_Andrology_total)));
	else
	DP.setUrology_Andrology("0");
	if (Internal_Medicine_total > 0)
	DP.setInternal_Medicine_Per(String.valueOf(Math.round((Internal_Medicine * 100) / Internal_Medicine_total)));
	else
	DP.setInternal_Medicine_Per("0");
	if (Vascular_Endovascular_Surgery_total > 0)
	DP.setVascular_Endovascular_Surgery_Per(String.valueOf(Math.round((Vascular_Endovascular_Surgery * 100) / Vascular_Endovascular_Surgery_total)));
	else
	DP.setVascular_Endovascular_Surgery_Per("0");
	if (Rheumatology_total > 0)
	DP.setRheumatology_Per(String.valueOf(Math.round((Rheumatology * 100) / Rheumatology_total)));
	else
	DP.setRheumatology_Per("0");
	if (Dental_total > 0)
	DP.setDental_Per(String.valueOf(Math.round((Dental * 100) / Dental_total)));
	else
	DP.setDental_Per("0");
	if (Ear_Nose_Throat_total > 0)
	DP.setEar_Nose_Throat_Per(String.valueOf(Math.round((Ear_Nose_Throat * 100) / Ear_Nose_Throat_total)));
	else
	DP.setEar_Nose_Throat_Per("0");
	if (Respiratory_Sleep_Medicine_total > 0)
	DP.setRespiratory_Sleep_Medicine_Per(String.valueOf(Math.round((Respiratory_Sleep_Medicine * 100) / Respiratory_Sleep_Medicine_total)));
	else
	DP.setRespiratory_Sleep_Medicine_Per("0");
	if (Pediatric_Cardiology_total > 0)
	DP.setPediatric_Cardiology_Per(String.valueOf(Math.round((Pediatric_Cardiology * 100) / Pediatric_Cardiology_total)));
	else
	DP.setPediatric_Cardiology_Per("0");
	if (Ortho_Anaesthesia_total > 0)
	DP.setOrtho_Anaesthesia_Per(String.valueOf(Math.round((Ortho_Anaesthesia * 100) / Ortho_Anaesthesia_total)));
	else
	DP.setOrtho_Anaesthesia_Per("0");
	if (Gynaecology_Obstetrics_total > 0)
	DP.setGynaecology_Obstetrics_Per(String.valueOf(Math.round((Gynaecology_Obstetrics * 100) / Gynaecology_Obstetrics_total)));
	else
	DP.setGynaecology_Obstetrics_Per("0");
	if (Executive_Health_Check_total > 0)
	DP.setExecutive_Health_Check_Per(String.valueOf(Math.round((Executive_Health_Check * 100) / Executive_Health_Check_total)));
	else
	DP.setExecutive_Health_Check_Per("0");
	if (Head_Neck_Oncology_total > 0)
	DP.setHead_Neck_Oncology_Per(String.valueOf(Math.round((Head_Neck_Oncology * 100) / Head_Neck_Oncology_total)));
	else
	DP.setHead_Neck_Oncology_Per("0");
	if (Interventional_Radiology_total > 0)
	DP.setInterventional_Radiology_Per(String.valueOf(Math.round((Interventional_Radiology * 100) / Interventional_Radiology_total)));
	else
	DP.setInterventional_Radiology_Per("0");
	if (Breast_Services_total > 0)
	DP.setBreast_Services_Per(String.valueOf(Math.round((Breast_Services * 100) / Breast_Services_total)));
	else
	DP.setBreast_Services_Per("0");
	if (Thoracic_Surgery_total > 0)
	DP.setThoracic_Surgery_Per(String.valueOf(Math.round((Thoracic_Surgery * 100) / Thoracic_Surgery_total)));
	else
	DP.setThoracic_Surgery_Per("0");
	if (Dermatology_total > 0)
	DP.setDermatology_Per(String.valueOf(Math.round((Dermatology * 100) / Dermatology_total)));
	else
	DP.setDermatology_Per("0");
	if (Pediatrics_total > 0)
	DP.setPediatrics_Per(String.valueOf(Math.round((Pediatrics * 100) / Pediatrics_total)));
	else
	DP.setPediatrics_Per("0");
	if (In_vitro_fertilisation_total > 0)
	DP.setIn_vitro_fertilisation_Per(String.valueOf(Math.round((In_vitro_fertilisation * 100) / In_vitro_fertilisation_total)));
	else
	DP.setIn_vitro_fertilisation_Per("0");
	if (Neonatology_total > 0)
	DP.setNeonatology_Per(String.valueOf(Math.round((Neonatology * 100) / Neonatology_total)));
	else
	DP.setNeonatology_Per("0");
	if (Bariatric_Surgery_total > 0)
	DP.setBariatric_Surgery_Per(String.valueOf(Math.round((Bariatric_Surgery * 100) / Bariatric_Surgery_total)));
	else
	DP.setBariatric_Surgery_Per("0");
	if (Ayurvedic_Medicine_total > 0)
	DP.setAyurvedic_Medicine_Per(String.valueOf(Math.round((Ayurvedic_Medicine * 100) / Ayurvedic_Medicine_total)));
	else
	DP.setAyurvedic_Medicine_Per("0");
	if (Physiotherapy_total > 0)
	DP.setPhysiotherapy_Per(String.valueOf(Math.round((Physiotherapy * 100) / Physiotherapy_total)));
	else
	DP.setPhysiotherapy_Per("0");
	if (grand_total > 0)
	DP.setTotal_Per(String.valueOf(Math.round((total * 100) / grand_total)));
	else
	DP.setTotal_Per("0");

	if (attendedBy.equalsIgnoreCase("-1"))
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy_total));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology_total));
	DP.setCritical_Care(String.valueOf(Critical_Care_total));
	DP.setCardiology(String.valueOf(Cardiology_total));
	DP.setOrthopaedics(String.valueOf(Orthopaedics_total));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant_total));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery_total));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg_total));
	DP.setNeurology(String.valueOf(Neurology_total));
	DP.setEndocrinology(String.valueOf(Endocrinology_total));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services_total));
	DP.setAnaesthesia(String.valueOf(Anaesthesia_total));
	DP.setOphthalmology(String.valueOf(Ophthalmology_total));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology_total));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology_total));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery_total));
	DP.setRadiology(String.valueOf(Radiology_total));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine_total));
	DP.setGastroenterology(String.valueOf(Gastroenterology_total));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality_total));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services_total));
	DP.setNephrology(String.valueOf(Nephrology_total));
	DP.setGI_Surgery(String.valueOf(GI_Surgery_total));
	DP.setNeurosurgery(String.valueOf(Neurosurgery_total));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology_total));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine_total));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery_total));
	DP.setRheumatology(String.valueOf(Rheumatology_total));
	DP.setDental(String.valueOf(Dental_total));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat_total));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine_total));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology_total));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia_total));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics_total));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check_total));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology_total));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology_total));
	DP.setBreast_Services(String.valueOf(Breast_Services_total));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery_total));
	DP.setDermatology(String.valueOf(Dermatology_total));
	DP.setPediatrics(String.valueOf(Pediatrics_total));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation_total));
	DP.setNeonatology(String.valueOf(Neonatology_total));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery_total));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine_total));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy_total));
	DP.setTotal(String.valueOf(grand_total));
	} else
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology));
	DP.setCritical_Care(String.valueOf(Critical_Care));
	DP.setCardiology(String.valueOf(Cardiology));
	DP.setOrthopaedics(String.valueOf(Orthopaedics));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg));
	DP.setNeurology(String.valueOf(Neurology));
	DP.setEndocrinology(String.valueOf(Endocrinology));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services));
	DP.setAnaesthesia(String.valueOf(Anaesthesia));
	DP.setOphthalmology(String.valueOf(Ophthalmology));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery));
	DP.setRadiology(String.valueOf(Radiology));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine));
	DP.setGastroenterology(String.valueOf(Gastroenterology));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services));
	DP.setNephrology(String.valueOf(Nephrology));
	DP.setGI_Surgery(String.valueOf(GI_Surgery));
	DP.setNeurosurgery(String.valueOf(Neurosurgery));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery));
	DP.setRheumatology(String.valueOf(Rheumatology));
	DP.setDental(String.valueOf(Dental));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology));
	DP.setBreast_Services(String.valueOf(Breast_Services));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery));
	DP.setDermatology(String.valueOf(Dermatology));
	DP.setPediatrics(String.valueOf(Pediatrics));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation));
	DP.setNeonatology(String.valueOf(Neonatology));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy));
	DP.setTotal(String.valueOf(total));
	}
	specCounterList.add(DP);
	dashSpecObj.setDashList(specCounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	@SuppressWarnings("rawtypes")
	public void getReferralSpecialityCountersWeek()
	{
	dashSpecObj = new DashboardSpecialityPojo();
	DashboardSpecialityPojo DP = null;
	try
	{
	specCounterList = new ArrayList<DashboardSpecialityPojo>();
	List referral_status_list = new ArrayList();
	List weekList = new ArrayList();
	weekList.add("1");
	weekList.add("2");
	weekList.add("3");
	weekList.add("4");
	weekList.add("5");
	String time = null;
	for (Iterator iterator = weekList.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	time = fromMonth + "-" + fromYear + "-Week" + object.toString();
	int total = 0, Medical_Oncology_Hematology = 0, Phlebotomy = 0, Radiation_Oncology = 0, Critical_Care = 0, Cardiology = 0, Orthopaedics = 0, Liver_Transplant = 0, Pediatric_Surgery = 0, Cardio_Surg = 0, Neurology = 0, Endocrinology = 0;
	int Laboratory_Services = 0, Anaesthesia = 0, Ophthalmology = 0, Pediatric_Gastro_Hepatology = 0, Plastic_Surgery = 0, Radiology = 0, Nuclear_Medicine = 0, Gastroenterology = 0, General_Speciality = 0, Emergency_Trauma_Services = 0;
	int Nephrology = 0, GI_Surgery = 0, Neurosurgery = 0, Urology_Andrology = 0, Internal_Medicine = 0, Vascular_Endovascular_Surgery = 0, Rheumatology = 0, Dental = 0, Ear_Nose_Throat = 0, Respiratory_Sleep_Medicine = 0;
	int Pediatric_Cardiology = 0, Ortho_Anaesthesia = 0, Gynaecology_Obstetrics = 0, Executive_Health_Check = 0, Head_Neck_Oncology = 0, Interventional_Radiology = 0, Breast_Services = 0, Thoracic_Surgery = 0;
	int Dermatology = 0, Pediatrics = 0, In_vitro_fertilisation = 0, Neonatology = 0, Bariatric_Surgery = 0, Ayurvedic_Medicine = 0, Physiotherapy = 0;

	int grand_total = 0, Medical_Oncology_Hematology_total = 0, Phlebotomy_total = 0, Radiation_Oncology_total = 0, Critical_Care_total = 0, Cardiology_total = 0, Orthopaedics_total = 0, Liver_Transplant_total = 0, Pediatric_Surgery_total = 0, Cardio_Surg_total = 0, Neurology_total = 0, Endocrinology_total = 0;
	int Laboratory_Services_total = 0, Anaesthesia_total = 0, Ophthalmology_total = 0, Pediatric_Gastro_Hepatology_total = 0, Plastic_Surgery_total = 0, Radiology_total = 0, Nuclear_Medicine_total = 0, Gastroenterology_total = 0, General_Speciality_total = 0, Emergency_Trauma_Services_total = 0;
	int Nephrology_total = 0, GI_Surgery_total = 0, Neurosurgery_total = 0, Urology_Andrology_total = 0, Internal_Medicine_total = 0, Vascular_Endovascular_Surgery_total = 0, Rheumatology_total = 0, Dental_total = 0, Ear_Nose_Throat_total = 0, Respiratory_Sleep_Medicine_total = 0;
	int Pediatric_Cardiology_total = 0, Ortho_Anaesthesia_total = 0, Gynaecology_Obstetrics_total = 0, Executive_Health_Check_total = 0, Head_Neck_Oncology_total = 0, Interventional_Radiology_total = 0, Breast_Services_total = 0, Thoracic_Surgery_total = 0;
	int Dermatology_total = 0, Pediatrics_total = 0, In_vitro_fertilisation_total = 0, Neonatology_total = 0, Bariatric_Surgery_total = 0, Ayurvedic_Medicine_total = 0, Physiotherapy_total = 0;

	DP = new DashboardSpecialityPojo();

	referral_status_list = getCounterDataForReferralSpecialityForWeek(object.toString(), fromYear, fromMonth, connectionSpace);
	if (referral_status_list != null && referral_status_list.size() > 0)
	{

	for (Iterator iterator2 = referral_status_list.iterator(); iterator2.hasNext();)
	{

	Object[] object2 = (Object[]) iterator2.next();
	if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy = Phlebotomy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology = Radiation_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care = Critical_Care + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology = Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics = Orthopaedics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant = Liver_Transplant + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery = Pediatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg = Cardio_Surg + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology = Neurology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology = Endocrinology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services = Laboratory_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia = Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology = Ophthalmology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology = Pediatric_Gastro_Hepatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology = Medical_Oncology_Hematology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery = Plastic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology = Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine = Nuclear_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology = Gastroenterology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality = General_Speciality + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services = Emergency_Trauma_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology = Nephrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery = GI_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery = Neurosurgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology = Urology_Andrology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine = Internal_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery = Vascular_Endovascular_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology = Rheumatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental = Dental + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat = Ear_Nose_Throat + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine = Respiratory_Sleep_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology = Pediatric_Cardiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia = Ortho_Anaesthesia + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics = Gynaecology_Obstetrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check = Executive_Health_Check + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology = Head_Neck_Oncology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology = Interventional_Radiology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services = Breast_Services + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery = Thoracic_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology = Dermatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics = Pediatrics + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation = In_vitro_fertilisation + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology = Neonatology + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery = Bariatric_Surgery + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine = Ayurvedic_Medicine + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[2].toString().equalsIgnoreCase(attendedBy) && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy = Physiotherapy + Integer.parseInt(object2[1].toString());
	total = total + Integer.parseInt(object2[1].toString());

	}

	if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Phlebotomy"))
	{
	Phlebotomy_total = Phlebotomy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiation Oncology"))
	{
	Radiation_Oncology_total = Radiation_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Critical Care"))
	{
	Critical_Care_total = Critical_Care_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardiology"))
	{
	Cardiology_total = Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Orthopaedics"))
	{
	Orthopaedics_total = Orthopaedics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Liver Transplant"))
	{
	Liver_Transplant_total = Liver_Transplant_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Surgery"))
	{
	Pediatric_Surgery_total = Pediatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Cardio-Thoracic Vascular Surg."))
	{
	Cardio_Surg_total = Cardio_Surg_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurology"))
	{
	Neurology_total = Neurology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Endocrinology"))
	{
	Endocrinology_total = Endocrinology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Laboratory Services"))
	{
	Laboratory_Services_total = Laboratory_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Anaesthesia"))
	{
	Anaesthesia_total = Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ophthalmology"))
	{
	Ophthalmology_total = Ophthalmology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Gastro and Hepatology"))
	{
	Pediatric_Gastro_Hepatology_total = Pediatric_Gastro_Hepatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Medical Oncology and Hematology"))
	{
	Medical_Oncology_Hematology_total = Medical_Oncology_Hematology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Plastic Surgery"))
	{
	Plastic_Surgery_total = Plastic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Radiology"))
	{
	Radiology_total = Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nuclear Medicine"))
	{
	Nuclear_Medicine_total = Nuclear_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gastroenterology"))
	{
	Gastroenterology_total = Gastroenterology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("General Speciality"))
	{
	General_Speciality_total = General_Speciality_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Emergency and Trauma Services"))
	{
	Emergency_Trauma_Services_total = Emergency_Trauma_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Nephrology"))
	{
	Nephrology_total = Nephrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("GI Surgery"))
	{
	GI_Surgery_total = GI_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neurosurgery"))
	{
	Neurosurgery_total = Neurosurgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Urology and Andrology"))
	{
	Urology_Andrology_total = Urology_Andrology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Internal Medicine"))
	{
	Internal_Medicine_total = Internal_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Vascular and Endovascular Surgery"))
	{
	Vascular_Endovascular_Surgery_total = Vascular_Endovascular_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Rheumatology"))
	{
	Rheumatology_total = Rheumatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dental"))
	{
	Dental_total = Dental_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ear,Nose,Throat"))
	{
	Ear_Nose_Throat_total = Ear_Nose_Throat_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Respiratory and Sleep Medicine"))
	{
	Respiratory_Sleep_Medicine_total = Respiratory_Sleep_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatric Cardiology"))
	{
	Pediatric_Cardiology_total = Pediatric_Cardiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ortho Anaesthesia"))
	{
	Ortho_Anaesthesia_total = Ortho_Anaesthesia_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Gynaecology and Obstetrics"))
	{
	Gynaecology_Obstetrics_total = Gynaecology_Obstetrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Executive Health Check"))
	{
	Executive_Health_Check_total = Executive_Health_Check_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Head and Neck Oncology"))
	{
	Head_Neck_Oncology_total = Head_Neck_Oncology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Interventional Radiology"))
	{
	Interventional_Radiology_total = Interventional_Radiology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Breast Services"))
	{
	Breast_Services_total = Breast_Services_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Thoracic Surgery"))
	{
	Thoracic_Surgery_total = Thoracic_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Dermatology"))
	{
	Dermatology_total = Dermatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Pediatrics"))
	{
	Pediatrics_total = Pediatrics_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("In vitro fertilisation"))
	{
	In_vitro_fertilisation_total = In_vitro_fertilisation_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Neonatology"))
	{
	Neonatology_total = Neonatology_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && attendedBy.equalsIgnoreCase("-1") && (object2[2].toString().equalsIgnoreCase("Resident") || object2[2].toString().equalsIgnoreCase("Consultant") || object2[2].toString().equalsIgnoreCase("Medical Officer")) && object2[0].toString().equalsIgnoreCase("Bariatric Surgery"))
	{
	Bariatric_Surgery_total = Bariatric_Surgery_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Ayurvedic Medicine"))
	{
	Ayurvedic_Medicine_total = Ayurvedic_Medicine_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	} else if (object2[0] != null && object2[0].toString().equalsIgnoreCase("Physiotherapy"))
	{
	Physiotherapy_total = Physiotherapy_total + Integer.parseInt(object2[1].toString());
	grand_total = grand_total + Integer.parseInt(object2[1].toString());

	}

	}

	}
	DP.setTime(time);
	if (Phlebotomy_total > 0)
	DP.setPhlebotomy_Per(String.valueOf(Math.round((Phlebotomy * 100) / Phlebotomy_total)));
	else
	DP.setPhlebotomy_Per("0");
	if (Radiation_Oncology_total > 0)
	DP.setRadiation_Oncology_Per(String.valueOf(Math.round((Radiation_Oncology * 100) / Radiation_Oncology_total)));
	else
	DP.setRadiation_Oncology_Per("0");
	if (Critical_Care_total > 0)
	DP.setCritical_Care_Per(String.valueOf(Math.round((Critical_Care * 100) / Critical_Care_total)));
	else
	DP.setCritical_Care_Per("0");
	if (Cardiology_total > 0)
	DP.setCardiology_Per(String.valueOf(Math.round((Cardiology * 100) / Cardiology_total)));
	else
	DP.setCardiology_Per("0");
	if (Orthopaedics_total > 0)
	DP.setOrthopaedics_Per(String.valueOf(Math.round((Orthopaedics * 100) / Orthopaedics_total)));
	else
	DP.setOrthopaedics_Per("0");
	if (Liver_Transplant_total > 0)
	DP.setLiver_Transplant_Per(String.valueOf(Math.round((Liver_Transplant * 100) / Liver_Transplant_total)));
	else
	DP.setLiver_Transplant_Per("0");
	if (Pediatric_Surgery_total > 0)
	DP.setPediatric_Surgery_Per(String.valueOf(Math.round((Pediatric_Surgery * 100) / Pediatric_Surgery_total)));
	else
	DP.setPediatric_Surgery_Per("0");
	if (Cardio_Surg_total > 0)
	DP.setCardio_Surg_Per(String.valueOf(Math.round((Cardio_Surg * 100) / Cardio_Surg_total)));
	else
	DP.setCardio_Surg_Per("0");
	if (Neurology_total > 0)
	DP.setNeurology_Per(String.valueOf(Math.round((Neurology * 100) / Neurology_total)));
	else
	DP.setNeurology_Per("0");
	if (Endocrinology_total > 0)
	DP.setEndocrinology_Per(String.valueOf(Math.round((Endocrinology * 100) / Endocrinology_total)));
	else
	DP.setEndocrinology_Per("0");
	if (Laboratory_Services_total > 0)
	DP.setLaboratory_Services_Per(String.valueOf(Math.round((Laboratory_Services * 100) / Laboratory_Services_total)));
	else
	DP.setLaboratory_Services_Per("0");
	if (Anaesthesia_total > 0)
	DP.setAnaesthesia_Per(String.valueOf(Math.round((Anaesthesia * 100) / Anaesthesia_total)));
	else
	DP.setAnaesthesia_Per("0");
	if (Ophthalmology_total > 0)
	DP.setOphthalmology_Per(String.valueOf(Math.round((Ophthalmology * 100) / Ophthalmology_total)));
	else
	DP.setOphthalmology_Per("0");
	if (Pediatric_Gastro_Hepatology_total > 0)
	DP.setPediatric_Gastro_Hepatology_Per(String.valueOf(Math.round((Pediatric_Gastro_Hepatology * 100) / Pediatric_Gastro_Hepatology_total)));
	else
	DP.setPediatric_Gastro_Hepatology_Per("0");
	if (Medical_Oncology_Hematology_total > 0)
	DP.setMedical_Oncology_Hematology_Per(String.valueOf(Math.round((Medical_Oncology_Hematology * 100) / Medical_Oncology_Hematology_total)));
	else
	DP.setMedical_Oncology_Hematology_Per("0");
	if (Plastic_Surgery_total > 0)
	DP.setPlastic_Surgery_Per(String.valueOf(Math.round((Plastic_Surgery * 100) / Plastic_Surgery_total)));
	else
	DP.setPlastic_Surgery_Per("0");
	if (Radiology_total > 0)
	DP.setRadiology_Per(String.valueOf(Math.round((Radiology * 100) / Radiology_total)));
	else
	DP.setRadiology_Per("0");
	if (Nuclear_Medicine_total > 0)
	DP.setNuclear_Medicine_Per(String.valueOf(Math.round((Nuclear_Medicine * 100) / Nuclear_Medicine_total)));
	else
	DP.setNuclear_Medicine_Per("0");
	if (Gastroenterology_total > 0)
	DP.setGastroenterology_Per(String.valueOf(Math.round((Gastroenterology * 100) / Gastroenterology_total)));
	else
	DP.setGastroenterology_Per("0");
	if (General_Speciality_total > 0)
	DP.setGeneral_Speciality_Per(String.valueOf(Math.round((General_Speciality * 100) / General_Speciality_total)));
	else
	DP.setGeneral_Speciality_Per("0");
	if (Emergency_Trauma_Services_total > 0)
	DP.setEmergency_Trauma_Services_Per(String.valueOf(Math.round((Emergency_Trauma_Services * 100) / Emergency_Trauma_Services_total)));
	else
	DP.setEmergency_Trauma_Services_Per("0");
	if (Nephrology_total > 0)
	DP.setNephrology_Per(String.valueOf(Math.round((Nephrology * 100) / Nephrology_total)));
	else
	DP.setNephrology_Per("0");
	if (GI_Surgery_total > 0)
	DP.setGI_Surgery_Per(String.valueOf(Math.round((GI_Surgery * 100) / GI_Surgery_total)));
	else
	DP.setGI_Surgery_Per("0");
	if (Neurosurgery_total > 0)
	DP.setNeurosurgery_Per(String.valueOf(Math.round((Neurosurgery * 100) / Neurosurgery_total)));
	else
	DP.setNeurosurgery_Per("0");
	if (Urology_Andrology_total > 0)
	DP.setUrology_Andrology(String.valueOf(Math.round((Urology_Andrology * 100) / Urology_Andrology_total)));
	else
	DP.setUrology_Andrology("0");
	if (Internal_Medicine_total > 0)
	DP.setInternal_Medicine_Per(String.valueOf(Math.round((Internal_Medicine * 100) / Internal_Medicine_total)));
	else
	DP.setInternal_Medicine_Per("0");
	if (Vascular_Endovascular_Surgery_total > 0)
	DP.setVascular_Endovascular_Surgery_Per(String.valueOf(Math.round((Vascular_Endovascular_Surgery * 100) / Vascular_Endovascular_Surgery_total)));
	else
	DP.setVascular_Endovascular_Surgery_Per("0");
	if (Rheumatology_total > 0)
	DP.setRheumatology_Per(String.valueOf(Math.round((Rheumatology * 100) / Rheumatology_total)));
	else
	DP.setRheumatology_Per("0");
	if (Dental_total > 0)
	DP.setDental_Per(String.valueOf(Math.round((Dental * 100) / Dental_total)));
	else
	DP.setDental_Per("0");
	if (Ear_Nose_Throat_total > 0)
	DP.setEar_Nose_Throat_Per(String.valueOf(Math.round((Ear_Nose_Throat * 100) / Ear_Nose_Throat_total)));
	else
	DP.setEar_Nose_Throat_Per("0");
	if (Respiratory_Sleep_Medicine_total > 0)
	DP.setRespiratory_Sleep_Medicine_Per(String.valueOf(Math.round((Respiratory_Sleep_Medicine * 100) / Respiratory_Sleep_Medicine_total)));
	else
	DP.setRespiratory_Sleep_Medicine_Per("0");
	if (Pediatric_Cardiology_total > 0)
	DP.setPediatric_Cardiology_Per(String.valueOf(Math.round((Pediatric_Cardiology * 100) / Pediatric_Cardiology_total)));
	else
	DP.setPediatric_Cardiology_Per("0");
	if (Ortho_Anaesthesia_total > 0)
	DP.setOrtho_Anaesthesia_Per(String.valueOf(Math.round((Ortho_Anaesthesia * 100) / Ortho_Anaesthesia_total)));
	else
	DP.setOrtho_Anaesthesia_Per("0");
	if (Gynaecology_Obstetrics_total > 0)
	DP.setGynaecology_Obstetrics_Per(String.valueOf(Math.round((Gynaecology_Obstetrics * 100) / Gynaecology_Obstetrics_total)));
	else
	DP.setGynaecology_Obstetrics_Per("0");
	if (Executive_Health_Check_total > 0)
	DP.setExecutive_Health_Check_Per(String.valueOf(Math.round((Executive_Health_Check * 100) / Executive_Health_Check_total)));
	else
	DP.setExecutive_Health_Check_Per("0");
	if (Head_Neck_Oncology_total > 0)
	DP.setHead_Neck_Oncology_Per(String.valueOf(Math.round((Head_Neck_Oncology * 100) / Head_Neck_Oncology_total)));
	else
	DP.setHead_Neck_Oncology_Per("0");
	if (Interventional_Radiology_total > 0)
	DP.setInterventional_Radiology_Per(String.valueOf(Math.round((Interventional_Radiology * 100) / Interventional_Radiology_total)));
	else
	DP.setInterventional_Radiology_Per("0");
	if (Breast_Services_total > 0)
	DP.setBreast_Services_Per(String.valueOf(Math.round((Breast_Services * 100) / Breast_Services_total)));
	else
	DP.setBreast_Services_Per("0");
	if (Thoracic_Surgery_total > 0)
	DP.setThoracic_Surgery_Per(String.valueOf(Math.round((Thoracic_Surgery * 100) / Thoracic_Surgery_total)));
	else
	DP.setThoracic_Surgery_Per("0");
	if (Dermatology_total > 0)
	DP.setDermatology_Per(String.valueOf(Math.round((Dermatology * 100) / Dermatology_total)));
	else
	DP.setDermatology_Per("0");
	if (Pediatrics_total > 0)
	DP.setPediatrics_Per(String.valueOf(Math.round((Pediatrics * 100) / Pediatrics_total)));
	else
	DP.setPediatrics_Per("0");
	if (In_vitro_fertilisation_total > 0)
	DP.setIn_vitro_fertilisation_Per(String.valueOf(Math.round((In_vitro_fertilisation * 100) / In_vitro_fertilisation_total)));
	else
	DP.setIn_vitro_fertilisation_Per("0");
	if (Neonatology_total > 0)
	DP.setNeonatology_Per(String.valueOf(Math.round((Neonatology * 100) / Neonatology_total)));
	else
	DP.setNeonatology_Per("0");
	if (Bariatric_Surgery_total > 0)
	DP.setBariatric_Surgery_Per(String.valueOf(Math.round((Bariatric_Surgery * 100) / Bariatric_Surgery_total)));
	else
	DP.setBariatric_Surgery_Per("0");
	if (Ayurvedic_Medicine_total > 0)
	DP.setAyurvedic_Medicine_Per(String.valueOf(Math.round((Ayurvedic_Medicine * 100) / Ayurvedic_Medicine_total)));
	else
	DP.setAyurvedic_Medicine_Per("0");
	if (Physiotherapy_total > 0)
	DP.setPhysiotherapy_Per(String.valueOf(Math.round((Physiotherapy * 100) / Physiotherapy_total)));
	else
	DP.setPhysiotherapy_Per("0");
	if (grand_total > 0)
	DP.setTotal_Per(String.valueOf(Math.round((total * 100) / grand_total)));
	else
	DP.setTotal_Per("0");

	if (attendedBy.equalsIgnoreCase("-1"))
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy_total));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology_total));
	DP.setCritical_Care(String.valueOf(Critical_Care_total));
	DP.setCardiology(String.valueOf(Cardiology_total));
	DP.setOrthopaedics(String.valueOf(Orthopaedics_total));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant_total));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery_total));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg_total));
	DP.setNeurology(String.valueOf(Neurology_total));
	DP.setEndocrinology(String.valueOf(Endocrinology_total));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services_total));
	DP.setAnaesthesia(String.valueOf(Anaesthesia_total));
	DP.setOphthalmology(String.valueOf(Ophthalmology_total));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology_total));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology_total));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery_total));
	DP.setRadiology(String.valueOf(Radiology_total));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine_total));
	DP.setGastroenterology(String.valueOf(Gastroenterology_total));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality_total));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services_total));
	DP.setNephrology(String.valueOf(Nephrology_total));
	DP.setGI_Surgery(String.valueOf(GI_Surgery_total));
	DP.setNeurosurgery(String.valueOf(Neurosurgery_total));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology_total));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine_total));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery_total));
	DP.setRheumatology(String.valueOf(Rheumatology_total));
	DP.setDental(String.valueOf(Dental_total));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat_total));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine_total));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology_total));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia_total));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics_total));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check_total));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology_total));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology_total));
	DP.setBreast_Services(String.valueOf(Breast_Services_total));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery_total));
	DP.setDermatology(String.valueOf(Dermatology_total));
	DP.setPediatrics(String.valueOf(Pediatrics_total));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation_total));
	DP.setNeonatology(String.valueOf(Neonatology_total));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery_total));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine_total));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy_total));
	DP.setTotal(String.valueOf(grand_total));
	} else
	{
	DP.setPhlebotomy(String.valueOf(Phlebotomy));
	DP.setRadiation_Oncology(String.valueOf(Radiation_Oncology));
	DP.setCritical_Care(String.valueOf(Critical_Care));
	DP.setCardiology(String.valueOf(Cardiology));
	DP.setOrthopaedics(String.valueOf(Orthopaedics));
	DP.setLiver_Transplant(String.valueOf(Liver_Transplant));
	DP.setPediatric_Surgery(String.valueOf(Pediatric_Surgery));
	DP.setCardio_Surg(String.valueOf(Cardio_Surg));
	DP.setNeurology(String.valueOf(Neurology));
	DP.setEndocrinology(String.valueOf(Endocrinology));
	DP.setLaboratory_Services(String.valueOf(Laboratory_Services));
	DP.setAnaesthesia(String.valueOf(Anaesthesia));
	DP.setOphthalmology(String.valueOf(Ophthalmology));
	DP.setPediatric_Gastro_Hepatology(String.valueOf(Pediatric_Gastro_Hepatology));
	DP.setMedical_Oncology_Hematology(String.valueOf(Medical_Oncology_Hematology));
	DP.setPlastic_Surgery(String.valueOf(Plastic_Surgery));
	DP.setRadiology(String.valueOf(Radiology));
	DP.setNuclear_Medicine(String.valueOf(Nuclear_Medicine));
	DP.setGastroenterology(String.valueOf(Gastroenterology));
	DP.setGeneral_Speciality(String.valueOf(General_Speciality));
	DP.setEmergency_Trauma_Services(String.valueOf(Emergency_Trauma_Services));
	DP.setNephrology(String.valueOf(Nephrology));
	DP.setGI_Surgery(String.valueOf(GI_Surgery));
	DP.setNeurosurgery(String.valueOf(Neurosurgery));
	DP.setUrology_Andrology(String.valueOf(Urology_Andrology));
	DP.setInternal_Medicine(String.valueOf(Internal_Medicine));
	DP.setVascular_Endovascular_Surgery(String.valueOf(Vascular_Endovascular_Surgery));
	DP.setRheumatology(String.valueOf(Rheumatology));
	DP.setDental(String.valueOf(Dental));
	DP.setEar_Nose_Throat(String.valueOf(Ear_Nose_Throat));
	DP.setRespiratory_Sleep_Medicine(String.valueOf(Respiratory_Sleep_Medicine));
	DP.setPediatric_Cardiology(String.valueOf(Pediatric_Cardiology));
	DP.setOrtho_Anaesthesia(String.valueOf(Ortho_Anaesthesia));
	DP.setGynaecology_Obstetrics(String.valueOf(Gynaecology_Obstetrics));
	DP.setExecutive_Health_Check(String.valueOf(Executive_Health_Check));
	DP.setHead_Neck_Oncology(String.valueOf(Head_Neck_Oncology));
	DP.setInterventional_Radiology(String.valueOf(Interventional_Radiology));
	DP.setBreast_Services(String.valueOf(Breast_Services));
	DP.setThoracic_Surgery(String.valueOf(Thoracic_Surgery));
	DP.setDermatology(String.valueOf(Dermatology));
	DP.setPediatrics(String.valueOf(Pediatrics));
	DP.setIn_vitro_fertilisation(String.valueOf(In_vitro_fertilisation));
	DP.setNeonatology(String.valueOf(Neonatology));
	DP.setBariatric_Surgery(String.valueOf(Bariatric_Surgery));
	DP.setAyurvedic_Medicine(String.valueOf(Ayurvedic_Medicine));
	DP.setPhysiotherapy(String.valueOf(Physiotherapy));
	DP.setTotal(String.valueOf(total));
	}
	specCounterList.add(DP);
	dashSpecObj.setDashList(specCounterList);

	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public List getCounterDataForReferralStatusForDate(String fromDate, String toDate, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String str[] = fromDate.split("-");
	String str1[] = toDate.split("-");
	if (str[0].length() < 4)
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	if (str1[0].length() < 4)
	toDate=DateUtil.convertDateToUSFormat(toDate);
	 
	query.append("select  ref.status,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate   + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" +  toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + DateUtil.convertDateToUSFormat(toDate) + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" +fromDate  + "' and '" +toDate  + "' ");
	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	query.append(" and refdoc.spec='" + specialty + "' ");
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append(" and   ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
	 
	if(s.equals("deptView_VIEW")  && locationWise!=null && locationWise.equalsIgnoreCase("No"))
	{
		String subDept=null;
		setDepartmentView("DepartmentView");
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
		{
 		  query.append(" and refdoc.spec in ( "+subDept+" )");
		}
		else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
		{
 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
		}
 		 
	}
	
	if(s.equals("locationView_VIEW"))
	{
	 
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,ref.status");
	////System.out.println("Status query.toString()***** : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralStatusForDay(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = forYear + "-" + month + "-01";
	String toDate = forYear + "-" + month + "-31";
	
	////System.out.println("Day >>> From Date:"+fromDate +"  To Date:"+toDate);
	query.append("select  ref.status,count(*),ref.open_date from referral_data as ref ");
 	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
  	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
  	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" +  toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" +  fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" +  toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" +  fromDate + "' and '" +  toDate + "' ");

	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	query.append(" and refdoc.spec='" + specialty + "' ");
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append(" and  ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	 	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,ref.status");
	//System.out.println("Ref Status Day : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralStatusForMonth(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = getFromDate("01", month, forYear);
	String toDate = getFromDate("31", month, forYear);
	////System.out.println("Month >>> From Date:"+fromDate +"  To Date:"+toDate);
	query.append("select  ref.status,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" +  toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" +  fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" +  toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" + fromDate + "' and '" +  toDate + "' ");

	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	query.append(" and refdoc.spec='" + specialty + "' ");
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	//// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by month(ref.open_date),ref.status");
	////System.out.println(" Ref Status Month : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralStatusForYear(String year, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = year + "-01-01";
	String toDate = year + "-12-31";
	////System.out.println("Year >>> From Date:"+fromDate +"  To Date:"+toDate);
	query.append("select  ref.status,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" +  fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" + fromDate  + "' and '" +  toDate + "' ");

	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	query.append(" and refdoc.spec='" + specialty + "' ");
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append(" and   ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	//// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by year(ref.open_date),ref.status");
	////System.out.println("Ref Status Year : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralStatusForWeek(String week, String forYear, String month, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = null;
	String toDate = null;
	if (week.equalsIgnoreCase("1"))
	{
	fromDate = getFromDate("01", month, forYear);
	toDate = getFromDate("07", month, forYear);
	} else if (week.equalsIgnoreCase("2"))
	{
	fromDate = getFromDate("08", month, forYear);
	toDate = getFromDate("14", month, forYear);
	} else if (week.equalsIgnoreCase("3"))
	{
	fromDate = getFromDate("15", month, forYear);
	toDate = getFromDate("21", month, forYear);
	} else if (week.equalsIgnoreCase("4"))
	{
	fromDate = getFromDate("22", month, forYear);
	toDate = getFromDate("28", month, forYear);
	} else if (week.equalsIgnoreCase("5"))
	{
	fromDate = getFromDate("29", month, forYear);
	toDate = getFromDate("31", month, forYear);
	}
	////System.out.println("Week >>> From Date:"+fromDate +"  To Date:"+toDate);
	query.append("select  ref.status,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
//	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");

	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	query.append(" and refdoc.spec='" + specialty + "' ");
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append(" and   ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by week(ref.open_date),ref.status");
	////System.out.println("Ref Status week : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralOrderForDay(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = forYear + "-" + month + "-01";
	String toDate = forYear + "-" + month + "-31";
	query.append("select  ref.priority,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined") )
	query.append(" and refdoc.spec='" + specialty + "' ");
 	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
 	query.append("    and ref.status not In ('Ignore','Ignore-I') ");
 	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,ref.priority");
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralOrderForDate(String fromDate, String toDate, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String str[] = fromDate.split("-");
	String str1[] = toDate.split("-");
	if (str[0].length() < 4)
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	if (str1[0].length() < 4)
	toDate=DateUtil.convertDateToUSFormat(toDate);
	query.append("select  ref.priority,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
 	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	{
	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	else
	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" +toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	}
	else
	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
 	 query.append(" and refdoc.spec='" + specialty + "' ");
  	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
  	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
  	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,ref.priority");
	////System.out.println("Order query.toString()***** : "+query.toString());

	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralOrderForMonth(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = getFromDate("01", month, forYear);
	String toDate = getFromDate("31", month, forYear);
	query.append("select  ref.priority,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	// query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
 	{
 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
 	else
 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
 	}
 	else
 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
 	query.append(" and refdoc.spec='" + specialty + "' ");
 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("    and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by month(ref.open_date),ref.priority");
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralOrderForYear(String year, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = year + "-01-01";
	String toDate = year + "-12-31";
	query.append("select  ref.priority,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
//	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by year(ref.open_date),ref.priority");
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralOrderForWeek(String week, String forYear, String month, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = null;
	String toDate = null;
	if (week.equalsIgnoreCase("1"))
	{
	fromDate = getFromDate("01", month, forYear);
	toDate = getFromDate("07", month, forYear);
	} else if (week.equalsIgnoreCase("2"))
	{
	fromDate = getFromDate("08", month, forYear);
	toDate = getFromDate("14", month, forYear);
	} else if (week.equalsIgnoreCase("3"))
	{
	fromDate = getFromDate("15", month, forYear);
	toDate = getFromDate("21", month, forYear);
	} else if (week.equalsIgnoreCase("4"))
	{
	fromDate = getFromDate("22", month, forYear);
	toDate = getFromDate("28", month, forYear);
	} else if (week.equalsIgnoreCase("5"))
	{
	fromDate = getFromDate("29", month, forYear);
	toDate = getFromDate("31", month, forYear);
	}
	query.append("select  ref.priority,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	//query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("    and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by week(ref.open_date),ref.priority");

	// ////System.out.println("Week Order : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralAttendedForDate(String fromDate, String toDate, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String str[] = fromDate.split("-");
	String str1[] = toDate.split("-");
	if (str[0].length() < 4)
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	if (str1[0].length() < 4)
	toDate=DateUtil.convertDateToUSFormat(toDate);

	query.append("select  refh.assign_desig,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,refh.assign_desig");

	// //System.out.println("Attended By Date : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralAttendedForDay(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = forYear + "-" + month + "-01";
	String toDate = forYear + "-" + month + "-31";
	query.append("select  refh.assign_desig,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by ref.open_date,refh.assign_desig");

	// //System.out.println("Attended By Day : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralAttendedForMonth(String month, String forYear, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = getFromDate("01", month, forYear);
	String toDate = getFromDate("31", month, forYear);
	query.append("select  refh.assign_desig,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("  and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by month(ref.open_date),refh.assign_desig");
	// ////System.out.println("Attended By Month : "+query.toString());

	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralAttendedForYear(String year, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = year + "-01-01";
	String toDate = year + "-12-31";
	query.append("select refh.assign_desig,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by year(ref.open_date),refh.assign_desig");
	// ////System.out.println("Attended By Year : "+query.toString());

	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralAttendedForWeek(String week, String forYear, String month, SessionFactory connectionSpace)
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	String fromDate = null;
	String toDate = null;
	if (week.equalsIgnoreCase("1"))
	{
	fromDate = getFromDate("01", month, forYear);
	toDate = getFromDate("07", month, forYear);
	} else if (week.equalsIgnoreCase("2"))
	{
	fromDate = getFromDate("08", month, forYear);
	toDate = getFromDate("14", month, forYear);
	} else if (week.equalsIgnoreCase("3"))
	{
	fromDate = getFromDate("15", month, forYear);
	toDate = getFromDate("21", month, forYear);
	} else if (week.equalsIgnoreCase("4"))
	{
	fromDate = getFromDate("22", month, forYear);
	toDate = getFromDate("28", month, forYear);
	} else if (week.equalsIgnoreCase("5"))
	{
	fromDate = getFromDate("29", month, forYear);
	toDate = getFromDate("31", month, forYear);
	}
	query.append("select  refh.assign_desig,count(*),ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	 	query.append("inner join referral_doctor as refdoc on refdoc.id=ref.ref_to ");
	 	query.append("inner join referral_doctor as refdocBy on refdocBy.id=ref.ref_by ");
	  	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	 	query.append(" and refdoc.spec='" + specialty + "' ");
	 	 
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
		if(s.equals("deptView_VIEW") && locationWise!=null && locationWise.equalsIgnoreCase("No"))
		{
			String subDept=null;
			setDepartmentView("DepartmentView");
			ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
			subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
			 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
			{
	 		  query.append(" and refdoc.spec in ( "+subDept+" )");
			}
			else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
			{
	 			  query.append(" and refdocBy.spec in ( "+subDept+" )");	
			}
	 		 
		}
	
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" group by week(ref.open_date),refh.assign_desig");
	// ////System.out.println("Attended By Week : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralSpecialityForDate(String fromDate, String toDate, SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	String str[] = fromDate.split("-");
	String str1[] = toDate.split("-");
	if (str[0].length() < 4)
	fromDate=DateUtil.convertDateToUSFormat(fromDate);
	if (str1[0].length() < 4)
	toDate=DateUtil.convertDateToUSFormat(toDate);

	query.append("select doc.spec,count(*),refh.assign_desig,ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as doc on doc.id=ref.ref_to ");
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" +fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	query.append(" group by ref.open_date,doc.spec,refh.assign_desig");

	////System.out.println("Speciality By Date : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralSpecialityForDay(String month, String forYear, SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	String fromDate = forYear + "-" + month + "-01";
	String toDate = forYear + "-" + month + "-31";
	query.append("select doc.spec,count(*),refh.assign_desig,ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as doc on doc.id=ref.ref_to ");
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	query.append(" group by ref.open_date,doc.spec,refh.assign_desig");
	// //System.out.println("Speciality By Day : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralSpecialityForMonth(String month, String forYear, SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	String fromDate = getFromDate("01", month, forYear);
	String toDate = getFromDate("31", month, forYear);
	query.append("select doc.spec,count(*),refh.assign_desig,ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as doc on doc.id=ref.ref_to ");
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	query.append(" group by ref.open_date,doc.spec,refh.assign_desig");
	///System.out.println("Speciality By Month : "+query.toString());

	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralSpecialityForYear(String year, SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	String fromDate = year + "-01-01";
	String toDate = year + "-12-31";
	query.append("select doc.spec,count(*),refh.assign_desig,ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as doc on doc.id=ref.ref_to ");
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	query.append(" group by ref.open_date,doc.spec,refh.assign_desig");
	////System.out.println("Speciality By Year : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getCounterDataForReferralSpecialityForWeek(String week, String forYear, String month, SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	String fromDate = null;
	String toDate = null;
	if (week.equalsIgnoreCase("1"))
	{
	fromDate = getFromDate("01", month, forYear);
	toDate = getFromDate("07", month, forYear);
	} else if (week.equalsIgnoreCase("2"))
	{
	fromDate = getFromDate("08", month, forYear);
	toDate = getFromDate("14", month, forYear);
	} else if (week.equalsIgnoreCase("3"))
	{
	fromDate = getFromDate("15", month, forYear);
	toDate = getFromDate("21", month, forYear);
	} else if (week.equalsIgnoreCase("4"))
	{
	fromDate = getFromDate("22", month, forYear);
	toDate = getFromDate("28", month, forYear);
	} else if (week.equalsIgnoreCase("5"))
	{
	fromDate = getFromDate("29", month, forYear);
	toDate = getFromDate("31", month, forYear);
	}
	query.append("select doc.spec,count(*),refh.assign_desig,ref.open_date from referral_data as ref ");
	query.append("inner join referral_patient_data as rpd on rpd.id=ref.uhid ");
	query.append("inner join referral_data_history as refh on refh.rid=ref.id ");
	query.append("inner join referral_doctor as doc on doc.id=ref.ref_to ");
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '23:59:59')  or  (ref.open_date='" + toDate + "' and ref.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append("where ((ref.open_date='" + fromDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (ref.open_date='" + toDate + "' and ref.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	 	else
	 	query.append(" where ref.open_date between '" + fromDate + "' and '" + toDate + "' ");
	
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	query.append("   and ref.status not In ('Ignore','Ignore-I') ");
	query.append(" group by ref.open_date,doc.spec,refh.assign_desig");
	////System.out.println("Speciality By Week : "+query.toString());
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public List getDoctorSpecList(SessionFactory connectionSpace)
	{
	StringBuilder query = new StringBuilder();
	query.append("select distinct spec from referral_doctor ");
	List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	return list;

	}

	public String getFromDate(String day, String month, String forYear)
	{
	String str = null, mth = null;
	if (month != null)
	{
	if (month.equalsIgnoreCase("Jan"))
	mth = "01";
	else if (month.equalsIgnoreCase("Feb"))
	mth = "02";
	else if (month.equalsIgnoreCase("Mar"))
	mth = "03";
	else if (month.equalsIgnoreCase("Apr"))
	mth = "04";
	else if (month.equalsIgnoreCase("May"))
	mth = "05";
	else if (month.equalsIgnoreCase("Jun"))
	mth = "06";
	else if (month.equalsIgnoreCase("Jul"))
	mth = "07";
	else if (month.equalsIgnoreCase("Aug"))
	mth = "08";
	else if (month.equalsIgnoreCase("Sep"))
	mth = "09";
	else if (month.equalsIgnoreCase("Oct"))
	mth = "10";
	else if (month.equalsIgnoreCase("Nov"))
	mth = "11";
	else if (month.equalsIgnoreCase("Dec"))
	mth = "12";
	}
	str = forYear + "-" + mth + "-" + day;
	return str;
	}

	public String getMonthInString(String month)
	{
	String mth = null;
	if (month.equalsIgnoreCase("01") || month.equalsIgnoreCase("1"))
	mth = "Jan";
	else if (month.equalsIgnoreCase("02") || month.equalsIgnoreCase("2") )
	mth = "Feb";
	else if (month.equalsIgnoreCase("03") || month.equalsIgnoreCase("3") )
	mth = "Mar";
	else if (month.equalsIgnoreCase("04") || month.equalsIgnoreCase("4"))
	mth = "Apr";
	else if (month.equalsIgnoreCase("05") || month.equalsIgnoreCase("5") )
	mth = "May";
	else if (month.equalsIgnoreCase("06") || month.equalsIgnoreCase("6") )
	mth = "Jun";
	else if (month.equalsIgnoreCase("07")|| month.equalsIgnoreCase("7") )
	mth = "Jul";
	else if (month.equalsIgnoreCase("08")|| month.equalsIgnoreCase("8") )
	mth = "Aug";
	else if (month.equalsIgnoreCase("09") || month.equalsIgnoreCase("9") )
	mth = "Sep";
	else if (month.equalsIgnoreCase("10"))
	mth = "Oct";
	else if (month.equalsIgnoreCase("11"))
	mth = "Nov";
	else if (month.equalsIgnoreCase("12"))
	mth = "Dec";
	return mth;
	}

	// Method For Dashboard data grid

	public String getStatusGridView()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	setGridColumns();
	returnResult = "success";

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

	// Setting dashboard column names
	public void setGridColumns()
	{
	String empid = (String) session.get("empName").toString();
	try
	{
	viewPageColumns = new ArrayList<GridDataPropertyView>();
	List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_referral_configuration", accountID, connectionSpace, "", 0, "table_name", "referral_configuration");
	GridDataPropertyView gpv = null;

	gpv = new GridDataPropertyView();
	gpv.setColomnName("ref_Id");
	gpv.setHeadingName("ref_Id");
	gpv.setWidth(30);
	gpv.setHideOrShow("true");
	viewPageColumns.add(gpv);
	for (GridDataPropertyView gdp : returnResult)
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	if (gdp.getColomnName().equalsIgnoreCase("escalate_date"))
	{
	gpv.setHideOrShow("true");
	} else
	gpv.setHideOrShow(gdp.getHideOrShow());
	gpv.setFormatter(gdp.getFormatter());
	gpv.setWidth(gdp.getWidth());
	viewPageColumns.add(gpv);
	if (gdp.getColomnName().equalsIgnoreCase("uhid"))
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName("patient_name");
	gpv.setHeadingName("Patient Name");
	gpv.setWidth(100);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("bed");
	gpv.setHeadingName("Bed");
	gpv.setWidth(50);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("nursing_unit");
	gpv.setHeadingName("Nursing Unit");
	gpv.setWidth(80);
	viewPageColumns.add(gpv);
	} else if (gdp.getColomnName().equalsIgnoreCase("ref_by"))
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName("spec");
	gpv.setHeadingName("Specialty");
	gpv.setWidth(110);
	viewPageColumns.add(gpv);
	} else if (gdp.getColomnName().equalsIgnoreCase("ref_to"))
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName("ref_spec");
	gpv.setHeadingName("Specialty");
	gpv.setWidth(110);
	viewPageColumns.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("ref_to_sub_spec");
	gpv.setHeadingName("Sub Specialty");
	gpv.setWidth(100);
	viewPageColumns.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("caller_emp_id");
	gpv.setHeadingName("Emp Id");
	gpv.setHideOrShow("true");
	gpv.setWidth(150);
	viewPageColumns.add(gpv);

	 
	gpv = new GridDataPropertyView();
	gpv.setColomnName("caller_emp_name");
	gpv.setHeadingName("Emp Name");
	gpv.setHideOrShow("true");
	gpv.setWidth(150);
	viewPageColumns.add(gpv);
	}
	}
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	// View Data in dashboard
	@SuppressWarnings(
	{ "rawtypes", "unused" })
	public String getStatusGridData()
	{
	if (ValidateSession.checkSession())
	{
	try
	{
	// //System.out.println("flag: "+flag+"  feedStatus: "+feedStatus+"  fromYear: "+fromYear+" fromMonth:"+fromMonth+" filterFlag:  "+filterFlag+"  toYear:"
	// +toYear+"  fromTime:"+fromTime+"  toTime:"+toTime+"  specialty: "+specialty+"  graphDate:"+graphDate);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List dataCount = cbt.executeAllSelectQuery(" select count(*) from referral_data ", connectionSpace);
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
	String fromDate = null;
	String toDate = null;

	StringBuilder query = new StringBuilder();
	query.append("SELECT refdata.id,refdata.ticket_no,refdata.status,refdata.open_date,refdata.open_time,refdata.escalate_date,refdata.escalate_time," + "refdata.level,refdata.priority,rpd.uhid,rd.name,rd1.name AS ref_to,refdata.reason,rpd.patient_name,rpd.bed,rpd.nursing_unit,rd.spec,rd1.spec" + " AS ref_spec,refdata.ref_to_sub_spec,rd1.id AS refId,rdh.sn_upto_date,rdh.sn_upto_time,refdata.caller_emp_id,refdata.caller_emp_name FROM referral_data AS refdata ");

	query.append(" INNER JOIN referral_patient_data AS rpd ON refdata.uhid=rpd.id");
	query.append(" INNER JOIN referral_doctor AS rd ON refdata.ref_by=rd.id ");
	query.append(" INNER JOIN referral_doctor AS rd1 ON refdata.ref_to=rd1.id ");
	query.append(" INNER JOIN referral_data_history AS rdh ON rdh.rid=refdata.id ");
	query.append(" Where ");
	if (feedStatus != null)
	{
	if (flag != null && flag.equalsIgnoreCase("Status"))
	query.append(" refdata.status In (" + feedStatus + ") and  refdata.status!='null'  ");
	else if (flag != null && flag.equalsIgnoreCase("Order"))
	{
	if(feedStatus.equalsIgnoreCase("All"))
	query.append(" refdata.priority In ('Routine','Urgent','Stat') and refdata.priority!='null' and   refdata.status not In ('Ignore','Ignore-I') ");
	else
	query.append(" refdata.priority In ('" + feedStatus + "') and refdata.priority!='null'   and refdata.status not In ('Ignore','Ignore-I') ");
	}
	else if (flag != null && flag.equalsIgnoreCase("Attended"))
	query.append(" rdh.assign_desig In ('" + feedStatus + "')    and refdata.status not In ('Ignore','Ignore-I') ");
	else if (flag != null && flag.equalsIgnoreCase("Speciality"))
	{
	 	if(attendedBy!=null && attendedBy.equalsIgnoreCase("-1"))
	query.append(" rdh.assign_desig In ('Resident','Consultant','Medical Officer')  and refdata.status not In ('Ignore','Ignore-I') ");
	else if(attendedBy!=null && !attendedBy.equalsIgnoreCase("-1"))
	query.append(" rdh.assign_desig='" + attendedBy + "'  and  refdata.status not In ('Ignore','Ignore-I') ");
	 	if(!feedStatus.equalsIgnoreCase("Total"))
	 	query.append(" and rd1.spec='"+feedStatus+"' ");
	
	}
	}
	if (specialty != null && !specialty.equalsIgnoreCase("-1") && !specialty.equalsIgnoreCase("undefined"))
	{
	query.append(" and rd1.spec='" + specialty + "' ");
	}
	if (graphDate != null)
	{
	////System.out.println("graphDate : "+graphDate);
	String Date1[] = graphDate.split("-");
	 	if (filterFlag != null && filterFlag.equalsIgnoreCase("Day"))
	{
	fromDate = getFromDate(Date1[0], Date1[1], "20" + Date1[2]);
	toDate = getFromDate(Date1[0], Date1[1], "20" + Date1[2]);
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Month"))
	{
	fromDate = getFromDate("01", Date1[0], "20" + Date1[1]);
	toDate = getFromDate("31", Date1[0], "20" + Date1[1]);
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Year"))
	{
	fromDate = getFromDate("01", "Jan", Date1[0]);
	toDate = getFromDate("31", "Dec", Date1[0]);
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Week"))
	{
	if (Date1[2].equalsIgnoreCase("Week1"))
	{
	fromDate = getFromDate("01", Date1[0], Date1[1]);
	toDate = getFromDate("07", Date1[0], Date1[1]);
	} else if (Date1[2].equalsIgnoreCase("Week2"))
	{
	fromDate = getFromDate("08", Date1[0], Date1[1]);
	toDate = getFromDate("14", Date1[0], Date1[1]);
	} else if (Date1[2].equalsIgnoreCase("Week3"))
	{
	fromDate = getFromDate("15", Date1[0], Date1[1]);
	toDate = getFromDate("21", Date1[0], Date1[1]);
	} else if (Date1[2].equalsIgnoreCase("Week4"))
	{
	fromDate = getFromDate("22", Date1[0], Date1[1]);
	toDate = getFromDate("28", Date1[0], Date1[1]);
	} else if (Date1[2].equalsIgnoreCase("Week5"))
	{
	fromDate = getFromDate("29", Date1[0], Date1[1]);
	toDate = getFromDate("31", Date1[0], Date1[1]);
	}
	} else if (filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	fromDate = getFromDate(Date1[0], Date1[1], "20" + Date1[2]);
	toDate = getFromDate(Date1[0], Date1[1], "20" + Date1[2]);
	}
//	query.append(" AND refdata.open_date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
	}
	
	////System.out.println("Filter Flag : "+filterFlag+" fromDate1: "+fromDate1+"  fromDate: "+fromDate);
	
	if(filterFlag != null && filterFlag.equalsIgnoreCase("Date"))
	{
	 	if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	{
	 	if(fromDate.equalsIgnoreCase(DateUtil.convertDateToUSFormat(fromDate1)))
	 	query.append(" and refdata.open_date='" + fromDate + "' and refdata.open_time between '" + fromTime + "' and  '23:59:59' ");
	 	if(fromDate.equalsIgnoreCase(DateUtil.convertDateToUSFormat(toDate1)))
	 	query.append(" and refdata.open_date='" + toDate + "' and refdata.open_time between '00:00' and  '" + toTime + "' ");

	 	}
	 	else
	 	query.append(" and ((refdata.open_date='" + fromDate + "' and refdata.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (refdata.open_date='" + toDate+ "' and refdata.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	
	 	else
	 	{
	 	query.append(" and refdata.open_date between '" + fromDate + "' and '" + toDate + "' ");
	 	}
	 }
	else
	{
	 if (fromTime != null && !fromTime.equalsIgnoreCase("") && toTime != null && !toTime.equalsIgnoreCase(""))
	 	{
	 	if (Integer.parseInt(fromTime.substring(0, 2)) > Integer.parseInt(toTime.substring(0, 2)))
	 	query.append(" and ((refdata.open_date='" + fromDate + "' and refdata.open_time between '" + fromTime + "' and  '23:59:59')  or  (refdata.open_date='" + toDate+ "' and refdata.open_time between '00:00' and  '" + toTime + "')) ");
	 	else
	 	query.append(" and ((refdata.open_date='" + fromDate + "' and refdata.open_time between '" + fromTime + "' and  '" + toTime + "')  or  (refdata.open_date='" + toDate + "' and refdata.open_time between '" + fromTime + "' and  '" + toTime + "')) ");
	 	}
	
	 	else
	 	query.append(" and refdata.open_date between '" + fromDate + "' and '" + toDate + "' ");
	}
	if (emergency != null && emergency.equalsIgnoreCase("true"))
	query.append(" and rpd.bed='Emergency' ");
	else if (emergency != null && emergency.equalsIgnoreCase("false"))
	query.append(" and rpd.bed!='Emergency' ");
	String empid=(String)session.get("empName").toString();//.trim().split("-")[1];
	String[] subModuleRightsArray =new ReferralActivityBoard().getSubModuleRights(empid);
	if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
	{
	for(String s:subModuleRightsArray)
	{
	 
	if(s.equals("deptView_VIEW")&& locationWise!=null && locationWise.equalsIgnoreCase("No"))
	{
		String subDept=null;
		setDepartmentView("DepartmentView");
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		subDept = refHelp.getSubDeptByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		 	if(refDoc!=null && refDoc.equalsIgnoreCase("toMe"))
		{
 		  query.append(" and rd1.spec in ( "+subDept+" )");
		}
		else if(refDoc!=null && refDoc.equalsIgnoreCase("byMe"))
		{
 			  query.append(" and rd.spec in ( "+subDept+" )");	
		}
 		 
	}
	if(s.equals("locationView_VIEW"))
	{
	// setLocation("Location");
	ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
	String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
	if(nursingUnit!=null && !nursingUnit.contains("All"))
	query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	if( locationWise!=null && locationWise.equalsIgnoreCase("Yes"))
	{
	if(s.equals("locationManagerView_VIEW"))
	{
		ReferralActivityBoardHelper refHelp = new ReferralActivityBoardHelper();
		String nursingUnit = refHelp.getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
		if(nursingUnit!=null  )
			query.append(" and rpd.nursing_unit in ( "+nursingUnit+" )");
	}
	}
	}
	}
	query.append(" GROUP BY refdata.id ORDER BY refdata.id DESC ");
	////System.out.println("DashBoard query ::::::"+query);
	List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (list != null && list.size() > 0)
	{
	List<Object> tempList = new ArrayList<Object>();
	for (Iterator iterator = list.iterator(); iterator.hasNext();)
	{
	Object object[] = (Object[]) iterator.next();
	Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
	tempMap.put("id", getValueWithNullCheck(object[0]));
	tempMap.put("ticket_no", getValueWithNullCheck(object[1]));
	if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze"))
	{
	tempMap.put("status", "OCC Park");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Snooze-I"))
	{
	tempMap.put("status", "Dept Park");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore"))
	{
	tempMap.put("status", "OCC Ignore");
	} else if (getValueWithNullCheck(object[2]).equalsIgnoreCase("Ignore-I"))
	{
	tempMap.put("status", "Dept Ignore");
	} else
	{
	tempMap.put("status", getValueWithNullCheck(object[2]));
	}
	tempMap.put("open_date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(object[3].toString())) + ", " + getValueWithNullCheck(object[4]).substring(0, 5));
	tempMap.put("level", getValueWithNullCheck(object[7]));
	tempMap.put("priority", getValueWithNullCheck(object[8]));
	tempMap.put("uhid", getValueWithNullCheck(object[9]));
	tempMap.put("ref_by", getValueWithNullCheck(object[10]));
	tempMap.put("ref_to", getValueWithNullCheck(object[11]));
	tempMap.put("reason", getValueWithNullCheck(object[12]));
	tempMap.put("patient_name", getValueWithNullCheck(object[13]));
	tempMap.put("bed", getValueWithNullCheck(object[14]));
	tempMap.put("nursing_unit", getValueWithNullCheck(object[15]));
	tempMap.put("spec", getValueWithNullCheck(object[16]));
	tempMap.put("ref_spec", getValueWithNullCheck(object[17]));
	tempMap.put("ref_to_sub_spec", getValueWithNullCheck(object[18]));
	tempMap.put("ref_Id", getValueWithNullCheck(object[19]));
	tempMap.put("caller_emp_id", getValueWithNullCheck(object[22]));
	tempMap.put("caller_emp_name", getValueWithNullCheck(object[23]));
	tempList.add(tempMap);
	}
	setViewDashReferralData(tempList);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	public String getFromDate1()
	{
	return fromDate1;
	}

	public void setFromDate1(String fromDate1)
	{
	this.fromDate1 = fromDate1;
	}

	public String getToDate1()
	{
	return toDate1;
	}

	public void setToDate1(String toDate1)
	{
	this.toDate1 = toDate1;
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

	public String getFeedStatus()
	{
	return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
	this.feedStatus = feedStatus;
	}

	public List<DashboardPojo> getDept_subdeptcounterList()
	{
	return dept_subdeptcounterList;
	}

	public void setDept_subdeptcounterList(List<DashboardPojo> dept_subdeptcounterList)
	{
	this.dept_subdeptcounterList = dept_subdeptcounterList;
	}

	public DashboardPojo getDashObj()
	{
	return dashObj;
	}

	public void setDashObj(DashboardPojo dashObj)
	{
	this.dashObj = dashObj;
	}

	public JSONArray getJsonArray()
	{
	return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
	this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObject()
	{
	return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
	this.jsonObject = jsonObject;
	}

	public Map<String, String> getServiceDeptList()
	{
	return serviceDeptList;
	}

	public void setServiceDeptList(Map<String, String> serviceDeptList)
	{
	this.serviceDeptList = serviceDeptList;
	}

	public String getFilterFlag()
	{
	return filterFlag;
	}

	public void setFilterFlag(String filterFlag)
	{
	this.filterFlag = filterFlag;
	}

	public String getFilterDeptId()
	{
	return filterDeptId;
	}

	public void setFilterDeptId(String filterDeptId)
	{
	this.filterDeptId = filterDeptId;
	}

	public String getFromYear()
	{
	return fromYear;
	}

	public void setFromYear(String fromYear)
	{
	this.fromYear = fromYear;
	}

	public String getFromMonth()
	{
	return fromMonth;
	}

	public void setFromMonth(String fromMonth)
	{
	this.fromMonth = fromMonth;
	}

	public String getToYear()
	{
	return toYear;
	}

	public void setToYear(String toYear)
	{
	this.toYear = toYear;
	}

	public String getAttendedBy()
	{
	return attendedBy;
	}

	public void setAttendedBy(String attendedBy)
	{
	this.attendedBy = attendedBy;
	}

	public Map<String, String> getSpecMap()
	{
	return specMap;
	}

	public void setSpecMap(Map<String, String> specMap)
	{
	this.specMap = specMap;
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

	public List getSpecList()
	{
	return specList;
	}

	public void setSpecList(List specList)
	{
	this.specList = specList;
	}

	public String getSpecialty()
	{
	return specialty;
	}

	public void setSpecialty(String specialty)
	{
	this.specialty = specialty;
	}

	public String getEmergency()
	{
	return emergency;
	}

	public void setEmergency(String emergency)
	{
	this.emergency = emergency;
	}

	public String getCurrentDayMonthValue()
	{
	return currentDayMonthValue;
	}

	public void setCurrentDayMonthValue(String currentDayMonthValue)
	{
	this.currentDayMonthValue = currentDayMonthValue;
	}

	public String getCurrentDayMonthKey()
	{
	return currentDayMonthKey;
	}

	public void setCurrentDayMonthKey(String currentDayMonthKey)
	{
	this.currentDayMonthKey = currentDayMonthKey;
	}

	public String getCurrentYear()
	{
	return currentYear;
	}

	public void setCurrentYear(String currentYear)
	{
	this.currentYear = currentYear;
	}

	public List<Object> getViewDashReferralData()
	{
	return viewDashReferralData;
	}

	public void setViewDashReferralData(List<Object> viewDashReferralData)
	{
	this.viewDashReferralData = viewDashReferralData;
	}

	public String getGraphDate()
	{
	return graphDate;
	}

	public void setGraphDate(String graphDate)
	{
	this.graphDate = graphDate;
	}

	public String getFlag()
	{
	return flag;
	}

	public void setFlag(String flag)
	{
	this.flag = flag;
	}

	public DashboardSpecialityPojo getDashSpecObj()
	{
	return dashSpecObj;
	}

	public void setDashSpecObj(DashboardSpecialityPojo dashSpecObj)
	{
	this.dashSpecObj = dashSpecObj;
	}

	public List<DashboardSpecialityPojo> getSpecCounterList()
	{
	return specCounterList;
	}

	public void setSpecCounterList(List<DashboardSpecialityPojo> specCounterList)
	{
	this.specCounterList = specCounterList;
	}

	public String getValueWithNullCheck(Object value)
	{
	return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String getSubDepartment() {
	return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
	this.subDepartment = subDepartment;
	}

	public String getDepartmentView() {
	return departmentView;
	}

	public void setDepartmentView(String departmentView) {
	this.departmentView = departmentView;
	}

	public String getRefDoc() {
		return refDoc;
	}

	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}

	public String getLocationWise() {
		return locationWise;
	}

	public void setLocationWise(String locationWise) {
		this.locationWise = locationWise;
	}
  

}