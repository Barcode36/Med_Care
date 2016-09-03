package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class ActivityBoardGridView extends GridPropertyBean
{
	private String fromTime;
	private String toTime;
	private String maxDateValue;
	private String minDateValue;
	private String fromDepart;
	private String taskType;
	private String feedStatus;
	private String escLevel;
	private String escTAT;
	private String complianId;
	private String dept;
	private String category;
	private String advncSearch;
	private Map<String, String> dataCountMap;
	private List<Object> masterViewProp = new ArrayList<Object>();

	@SuppressWarnings("rawtypes")
	public String viewActivityBoardData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
			 	List dataCount = cbt.executeAllSelectQuery(" select count(*) from feedback_status_new ", connectionSpace);
				// ////System.out.println("dataCount:"+dataCount);
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
					String depId = getDeptRightsId();
					StringBuilder query = new StringBuilder();
					List<Object> tempList = new ArrayList<Object>();
					 
					// System.out.println("SMSRESEND**********"+SMSResend_feedId);
					if(advncSearch!=null && advncSearch.equals("true"))
					{
						
						//System.out.println("In ADVANCE SEARCH");
				  	  	query.append("SELECT SQL_CACHE feedback.id, feedback.feed_by, feedback.feed_by_mobno, catg.id AS catgId, feedback.ticket_no, ");
						query.append("feedtype.fbType as categoryName, subcatg.subCategoryName, ");
						query.append("feedback.feed_brief, feedback.open_date, feedback.open_time, ");
						query.append("dept.deptName, feedback.location,");
						query.append(" feedback.status as feed,");
					 	query.append(" feedback.level,emp.empName, feedtype.fbType,  dept1.deptName AS todepartment,floor.roomno,feedback.escalation_date,feedback.escalation_time,feedback.allot_to,feedback.pending_alert ");
						query.append(" FROM feedback_status_new as feedback");
						query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
						query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
						query.append(" LEFT JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
						query.append(" LEFT JOIN department AS dept ON dept.id = feedback.by_dept_subdept");
						query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
						query.append(" LEFT JOIN department AS dept1 ON dept1.id = feedback.to_dept_subdept");
						query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feedback.location");
					 	query.append(" WHERE feedback.id!=0 ");
					 	if ( !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" AND (feedback.ticket_no  like '%" + getSearchString() + "%'");
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
					 		query.append(" ORDER BY feedback.id DESC limit " + from + "," + to);
						 	//System.out.println("QQQQ<<<<<<<<<<<<<<<<< Query>>>>>>>>>>>>>>>  " + query);
					
						}
					
					}
					else
						{
						 	String userType = null, empId = null, userMobno = null;
							userType = (String) session.get("userTpe");
							query.append("SELECT SQL_CACHE feedback.id, feedback.feed_by, feedback.feed_by_mobno, catg.id AS catgId, feedback.ticket_no, ");
							query.append("feedtype.fbType as categoryName, subcatg.subCategoryName, ");
							query.append("feedback.feed_brief, feedback.open_date, feedback.open_time, ");
							query.append("dept.deptName, feedback.location,");
							if (feedStatus.equalsIgnoreCase("Re-OpenedR"))
								query.append(" history.status as feed,");
							else if (feedStatus.equalsIgnoreCase("SnoozeH"))
								query.append(" history.status as feed,");
							else
								query.append(" feedback.status as feed,");
						 	query.append(" feedback.level,emp.empName, feedtype.fbType,  dept1.deptName AS todepartment,floor.roomno,feedback.escalation_date,feedback.escalation_time,feedback.allot_to,feedback.pending_alert ");
							query.append(" FROM feedback_status_new as feedback");
							query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
							query.append(" LEFT JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
							query.append(" LEFT JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
							query.append(" LEFT JOIN department AS dept ON dept.id = feedback.by_dept_subdept");
							query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
							query.append(" LEFT JOIN department AS dept1 ON dept1.id = feedback.to_dept_subdept");
							query.append(" LEFT JOIN floor_room_detail AS floor ON floor.id = feedback.location");
							if (feedStatus.equalsIgnoreCase("Re-OpenedR") || feedStatus.equalsIgnoreCase("SnoozeH") )
								query.append(" LEFT JOIN feedback_status_history  AS history ON history.feedId = feedback.id");
		
							query.append(" WHERE feedback.id!=0 ");
							if (fromTime != null && !fromTime.equalsIgnoreCase("") && !fromTime.equalsIgnoreCase("null") && !fromTime.equalsIgnoreCase("00:00") && toTime != null && !toTime.equalsIgnoreCase("") && !toTime.equalsIgnoreCase("null") && !toTime.equalsIgnoreCase("00:00") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
							{
								query.append("  AND feedback.open_time BETWEEN '" + fromTime + "' AND '" + toTime + "'");
		
							}
							// System.out.println(getSearchString()+"::::::????????"+getFeedStatus());
							if (getSearchString() != null && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined") && getFeedStatus().equalsIgnoreCase("Resolved"))
							{
								query.append(" AND feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "' ");
							}
							else
							{
								if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
								{
									String str[] = maxDateValue.split("-");
									if (str[0].length() < 4)
									{
										if (feedStatus != null && feedStatus.equalsIgnoreCase("-1"))
										{
											query.append("  AND feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(DateUtil.getNewDate("day", -7, DateUtil.convertDateToUSFormat(minDateValue))) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
							 			}
										else
										{
							 				query.append("  AND feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
							  			}
					 					
						 			}
									else
									{
										if (feedStatus != null && feedStatus.equalsIgnoreCase("-1"))
										{
								 				query.append(" AND feedback.open_date BETWEEN '" + DateUtil.getNewDate("day", -7, minDateValue) + "' AND '" + maxDateValue + "'");
										}
										else
										{
								 	 		query.append(" AND feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
										}
								 	}
								}
							}
							if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
							{
								query.append(" AND  feedback.by_dept_subdept = " + fromDepart);
							}
		
						 	// System.out.println("LISt show :  "+depId);
							if (depId.length() >= 1)
							{
								query.append(" AND dept1.id IN(" + depId + ")");
							}
							if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.to_dept_subdept IN(" + depId + ")  ");
							else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.feed_by_mobno =" + userMobno + "  ");
							else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.allot_to =" + empId + "  ");
							else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.by_dept_subdept IN (" + depId + " ) ");
		
							if (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined"))
							{
								if (feedStatus != null && !feedStatus.equalsIgnoreCase("-1") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								{
									if (feedStatus.equalsIgnoreCase("Escalated"))
									{
										query.append(" AND feedback.level != 'Level1'  ");
									}
									else if (feedStatus.equalsIgnoreCase("Re-OpenedR"))
									{
										query.append(" AND history.status = '" + feedStatus + "'  ");
									}
									else if (feedStatus.equalsIgnoreCase("SnoozeH"))
									{
										query.append(" AND history.status = 'Snooze'  ");
									}
									else if (!feedStatus.equalsIgnoreCase("All"))
									{
										query.append(" AND feedback.status = '" + feedStatus + "'  ");
									}
								}
								else
								{
									query.append(" AND feedback.status NOT IN ('Resolved','Ignore','Re-Opened','Re-assign','High-Priority') ");
								}
							}
							if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.level = '" + escLevel + "'   ");
							if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.level = 'Level1'   ");
							else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("") || getSearchString().equalsIgnoreCase("undefined")))
								query.append(" AND feedback.level != 'Level1'   ");
		
							if (complianId != null && !complianId.equalsIgnoreCase("") && !complianId.equalsIgnoreCase("undefined"))
							{
								query.append(" AND feedback.id=" + complianId);
							}
		
							if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("undefined"))
							{
								// add search query based on the search operation
								query.append(" AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");
							}
		
							if (getSearchField() != null && getSearchString() != null && getSearchField().equalsIgnoreCase("All") && !getSearchString().equalsIgnoreCase(""))
							{
								query.append(" AND (feedback.ticket_no  like '%" + getSearchString() + "%'");
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
								/*
								 * if(!"-1".equalsIgnoreCase(getSubcategory()) &&
								 * null!=getSubcategory() &&
								 * !"".equalsIgnoreCase(getSubcategory())) {
								 * query.append(" AND subcatg.id  like '%" +
								 * getSubcategory() + "%'"); }
								 */
							}
						 		query.append(" ORDER BY feedback.id DESC limit " + from + "," + to);
						 //	System.out.println("QQQQ Query>>>>>>>>>>>>>>>  " + query);
					}
					List dataList = null;
					if (depId.length() != 0)
					{
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						Session session = null;
						Transaction transaction = null;
						try
						{
							session = connectionSpace.getCurrentSession();
							transaction = session.beginTransaction();
							// System.out.println("QQQQ >>>>>>>>>>>>>>>  " +
							// Calendar.getInstance().get(Calendar.MILLISECOND));
							// System.out.println("QQQQ Query>>>>>>>>>>>>>>>  "
							// + query);
							dataList = session.createSQLQuery(query.toString())

							.setCacheable(true).setCacheRegion("gridActivityData").setCacheMode(CacheMode.REFRESH).list();
							// System.out.println("Return QQQ>>>>>>>>>>>>>>>  "
							// +Calendar.getInstance().get(Calendar.MILLISECOND));
							transaction.commit();

						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							transaction.rollback();
						}
					}
					String unloack = "UNLOCK TABLES";
					cbt.executeAllUpdateQuery(unloack, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{

						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
							tempMap.put("id", getValueWithNullCheck(object[0]));
							// tempMap.put("history",
							// ABH.getComplaintCountByLocation(connectionSpace,
							// CUA.getValueWithNullCheck(object[11])));
							tempMap.put("ticketno", getValueWithNullCheck(object[4]));
							tempMap.put("todept", getValueWithNullCheck(object[16]));
							tempMap.put("category", getValueWithNullCheck(object[5]));
							tempMap.put("subcatg", getValueWithNullCheck(object[6]));
							tempMap.put("brief", getValueWithNullCheck(object[7]));
							tempMap.put("openat", DateUtil.convertDateToIndianFormat(object[8].toString()) + ", " + object[9].toString().substring(0, object[9].toString().length() - 3));
							tempMap.put("feedby", getValueWithNullCheck(object[1]));
							tempMap.put("bydept", getValueWithNullCheck(object[10]));
							tempMap.put("location", getValueWithNullCheck(object[17]));
							// System.out.println("Status is " +
							// CUA.getValueWithNullCheck(object[12]));

							if (getValueWithNullCheck(object[12]).equalsIgnoreCase("transfer"))
								tempMap.put("status", "Transfer");
							else if (getValueWithNullCheck(object[12]).equalsIgnoreCase("hold"))
								tempMap.put("status", "Seek Approval");
							else if (getValueWithNullCheck(object[12]).equalsIgnoreCase("Snooze"))
								tempMap.put("status", "Parked");
							else
								tempMap.put("status", getValueWithNullCheck(object[12]));
							tempMap.put("TAT", getValueWithNullCheck(object[13]));
							tempMap.put("allotedto", getValueWithNullCheck(object[14]));
							tempMap.put("feedbacktype", getValueWithNullCheck(object[15]));
							if (getValueWithNullCheck(object[12]).equalsIgnoreCase("Pending") || getValueWithNullCheck(object[12]).equalsIgnoreCase("Re-Assign") || getValueWithNullCheck(object[12]).equalsIgnoreCase("Re-Opened") || getValueWithNullCheck(object[12]).equalsIgnoreCase("High Priority") || getValueWithNullCheck(object[12]).equalsIgnoreCase("Snooze"))
							{
								tempMap.put("Timer", DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), object[18].toString(), object[19].toString()));
							}
							else
							{
								tempMap.put("Timer", "00:00");
							}

							tempMap.put("sms_flag", "sent");
							/*
							 * StringBuilder qry = new StringBuilder();
							 * qry.append
							 * ("SELECT instantMsg.flag,instantMsg.status ");
							 * qry.append(" FROM instant_msg AS instantMsg");
							 * qry.append(
							 * " LEFT JOIN feedback_status_new AS feedback ON instantMsg.msg_text like '"
							 * + CUA.getValueWithNullCheck(object[4]) +
							 * "%' where instantMsg.msg_text like '%" +
							 * CUA.getValueWithNullCheck(object[4]) +
							 * "%' order by instantMsg.id desc limit 1"); //
							 * System
							 * .out.println("******************"+qry.toString
							 * ()); dataList =
							 * cbt.executeAllSelectQuery(qry.toString(),
							 * connectionSpace); if (dataList != null &&
							 * dataList.size() > 0) { for (Iterator iterator1 =
							 * dataList.iterator(); iterator1.hasNext();) {
							 * Object[] object1 = (Object[]) iterator1.next();
							 * if (object1[0] != null && object1[1] != null &&
							 * object1[0].toString().equalsIgnoreCase("0") &&
							 * object1
							 * [1].toString().equalsIgnoreCase("Pending"))
							 * 
							 * { tempMap.put("sms_flag", "unsent");
							 * 
							 * } else { tempMap.put("sms_flag", "sent");
							 * 
							 * } } }
							 */

							tempList.add(tempMap);

						}
					}
					 
					setMasterViewProp(tempList);
					 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					 // System.out.println("Prepairing QQQ>>>>>>>>>>>>>>>  " +
				//  Calendar.getInstance().get(Calendar.MILLISECOND));
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

	
	@SuppressWarnings("rawtypes")
	public String viewActivityBoardDataCPS()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
			 	List dataCount = cbt.executeAllSelectQuery(" select count(*) from feedback_corporate_patient ", connectionSpace);
				// ////System.out.println("dataCount:"+dataCount);
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
					String depId = getDeptRightsId();
					StringBuilder query = new StringBuilder();
					List<Object> tempList = new ArrayList<Object>();
					 
					// System.out.println("SMSRESEND**********"+SMSResend_feedId);
					
						//System.out.println("In ADVANCE SEARCH");
				  	  	query.append("SELECT SQL_CACHE feedback.id,feedback.ticket_no,feedback.acc_manager,feedback.corp_name,feedback.corp_type,");
						query.append("feedtype.status,feedback_pat_name,feedback.uhid, feedback.service, feedback.location,");
						query.append(" feedback.preffered_date,");
					 	query.append(" feedback.preffered_time,feedtype.remark,feedback.allot_to_phase1,feedback.openDate_phase1,feedback.openTime_phase1,feedback_timer,feedback_tat");
						query.append(" FROM feedback_corporate_patient as feedback");
					
					 	query.append(" WHERE feedback.id!=0 ");
					 	
							
						
						 		query.append(" ORDER BY feedback.id DESC limit " + from + "," + to);
						 	//System.out.println("QQQQ Query>>>>>>>>>>>>>>>  " + query);
					
					List dataList = null;
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						Session session = null;
						Transaction transaction = null;
						try
						{
							session = connectionSpace.getCurrentSession();
							transaction = session.beginTransaction();
							// System.out.println("QQQQ >>>>>>>>>>>>>>>  " +
							// Calendar.getInstance().get(Calendar.MILLISECOND));
							// System.out.println("QQQQ Query>>>>>>>>>>>>>>>  "
							// + query);
							dataList = session.createSQLQuery(query.toString())

							.setCacheable(true).setCacheRegion("gridActivityDataCPS").setCacheMode(CacheMode.REFRESH).list();
							// System.out.println("Return QQQ>>>>>>>>>>>>>>>  "
							// +Calendar.getInstance().get(Calendar.MILLISECOND));
							transaction.commit();

						}
						catch (Exception ex)
						{
							transaction.rollback();
						}
					
					String unloack = "UNLOCK TABLES";
					cbt.executeAllUpdateQuery(unloack, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{

						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
							tempMap.put("id", getValueWithNullCheck(object[0]));
							tempMap.put("ticketno", getValueWithNullCheck(object[1]));
							tempMap.put("acc_manager", getValueWithNullCheck(object[2]));
							tempMap.put("corporate_name", getValueWithNullCheck(object[3]));
							tempMap.put("corp_type", getValueWithNullCheck(object[4]));
							tempMap.put("TAT", getValueWithNullCheck(object[16]));
							tempMap.put("TATOCC", getValueWithNullCheck(object[17]));
							tempMap.put("status", getValueWithNullCheck(object[5]));
							tempMap.put("pat_name", getValueWithNullCheck(object[6]));
							tempMap.put("uhid", getValueWithNullCheck(object[7]));
							tempMap.put("service_name", getValueWithNullCheck(object[8]));
							tempMap.put("location", getValueWithNullCheck(object[9]));
							tempMap.put("preffered", getValueWithNullCheck(object[10])+"/"+getValueWithNullCheck(object[11]));
							tempMap.put("remark", getValueWithNullCheck(object[12]));
							tempMap.put("phase1allot", getValueWithNullCheck(object[13]));
							tempMap.put("occdatetime",  getValueWithNullCheck(object[14])+"/"+getValueWithNullCheck(object[15]));
							
							tempList.add(tempMap);

						}
					}
					 
					setMasterViewProp(tempList);
					 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	@SuppressWarnings(
	{
		"rawtypes"
	})
	public String getDeptRightsId()
	{
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		String empId = null;
		String userType = null;
		List dataList = null;
		String deptList = "";
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
					deptList = object[1].toString();
				}
			}
		}

		return deptList;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
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

	public String getFromDepart()
	{
		return fromDepart;
	}

	public void setFromDepart(String fromDepart)
	{
		this.fromDepart = fromDepart;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
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

	public String getComplianId()
	{
		return complianId;
	}

	public void setComplianId(String complianId)
	{
		this.complianId = complianId;
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

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}




	public String getAdvncSearch() {
		return advncSearch;
	}




	public void setAdvncSearch(String advncSearch) {
		this.advncSearch = advncSearch;
	}

}