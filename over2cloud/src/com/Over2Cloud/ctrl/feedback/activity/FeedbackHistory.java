package com.Over2Cloud.ctrl.feedback.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackHistory
{
	private String id;
	private String clientId;
	private ActivityPojo pojo;
	private List<ConfigurationUtilBean> paperRatings = null;
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String, String> dataMap;
	private Map<String,Map<String,String>> dataMapTAT;
	private String feedStatId;
	private String dataFor;
	private String ticketNo;
	public static final boolean DESC = false;
	private String activityFlag; 

	private Map<String, Map<String, String>> finalStatusMap;
	private Map<String,Map<String,Map<String,String>>> statusMap = new LinkedHashMap<String, Map<String,Map<String,String>>>();;
	private List<ActivityPojo> statusList;
	
	public String getStatusFullDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{

				return "success";
			}
			catch (Exception e)
			{
				return "error";
			}
		}
		else
		{
			return "error";
		}
	}

	public String getFeedbackHistory()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getPatientFullDetails(connectionSpace, getClientId());
				if (pojo.getFeedDataId().equalsIgnoreCase("NA"))
				{
					return "nofeed";
				}
				else
				{
					List<GridDataPropertyView> ipdForm = null;
					List<GridDataPropertyView> opdForm = null;
					paperRatings = new ArrayList<ConfigurationUtilBean>();
					if (pojo.getCompType().equalsIgnoreCase("IPD") && pojo.getMode().equalsIgnoreCase("Paper"))
					{
						pojo = helper.getPatientIPDRatings(connectionSpace, pojo);
						return "ipdsuccess";
					}
					else if (pojo.getCompType().equalsIgnoreCase("OPD") && pojo.getMode().equalsIgnoreCase("Paper"))
					{
						pojo = helper.getPatientOPDRatings(connectionSpace, pojo);
						return "opdsuccess";
					}
					else
					{
						return "normal";
					}
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

	public String getPatHistory()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getPatientFullDetails(connectionSpace, getClientId());
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

	public String viewEmpDetail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getEmpFullDetails(connectionSpace, getId(),getActivityFlag());
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

	public String viewOpenByDetail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getOpenByDetails(connectionSpace, getId(),getActivityFlag());
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

	public String viewOpenedTATDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				dataMap = new HashMap<String, String>();
				WorkingHourAction WHA = new WorkingHourAction();
				ActivityBoardHelper helperObject = new ActivityBoardHelper();
				int count=0;
				ActivityPojo activityPojo = null;
				pojo=new ActivityPojo();
				List list=null;
				statusList=new ArrayList<ActivityPojo>();
				if (getDataFor() != null && getDataFor().equalsIgnoreCase("status") && getFeedStatId() != null && !getFeedStatId().equalsIgnoreCase("NA"))
				{
					Map<String, String> dataHashMap = new HashMap<String, String>();
					List tempList = null;
					List dataList = helperObject.getTicketCycle(connectionSpace, cbt, getFeedStatId(), getTicketNo(),getActivityFlag());
					String actionBy = "NA";
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list=(List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								activityPojo=new ActivityPojo();
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null)
								{
									activityPojo.setFstatus(object[0].toString());
								}
	
								if (object[22] != null)
								{
									tempList = helperObject.getLodgerEmployeeDetailByUserName(connectionSpace, cbt, object[22].toString());
									if (tempList != null && tempList.size() > 0)
									{
										Object[] obj = (Object[]) tempList.get(0);
										actionBy = obj[0].toString();
									}
									tempList.clear();
								}
								
								//Pending
								if (object[0] != null)
								{
									if (object[33] != null)
									{
										activityPojo.setFeedRegBy(object[33].toString());
										tempList = helperObject.getLodgerEmployeeDetailByUserName(connectionSpace, cbt, object[33].toString());
										if (tempList != null && tempList.size() > 0)
										{
											Object[] obj = (Object[]) tempList.get(0);
											activityPojo.setFeedRegBy(obj[0].toString());
										}
										tempList.clear();
									}
									if (object[31] != null)
									{
										activityPojo.setDept(object[31].toString());
									}
									
									if (object[32] != null)
									{
										activityPojo.setTicket_no(object[32].toString());
									}
									
									if (object[26] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[26].toString());
										String time = object[27].toString();
										activityPojo.setDateTime(date + ", " + time.substring(0, time.length() - 3));
									}
									else
									{	
										activityPojo.setDateTime("NA");
									}	
									if (object[28] != null)
									{
										activityPojo.setCat(object[28].toString());
									}
									
									if (object[29] != null)
									{
										activityPojo.setSubCat(object[29].toString());
									}
									
									
									if (object[30] != null)
									{
										activityPojo.setBrief(object[30].toString());
									}
								}
								
								//Snooze
								if (object[1] != null)
								{
	
									if (object[1] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[1].toString());
										String time = object[2].toString();
										activityPojo.setSnoozeAt(date + ", " + time.substring(0, time.length() - 3));
									}
									else
									{
										activityPojo.setSnoozeAt("NA");
									}
	
									if (object[3] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[3].toString());
										String time = object[4].toString();
										activityPojo.setSnoozeUpto(date + ", " + time);
									}
									else
									{
										activityPojo.setSnoozeUpto("NA");
									}
									if (object[5] != null)
									{
										activityPojo.setDuration(object[5].toString());
									}
									if (object[6] != null)
									{
										activityPojo.setSnoozeBy(actionBy);
									}
									if (object[6] != null)
									{
										activityPojo.setSnoozeReason(object[6].toString());
									}
								
								}
								
								//High Priority
								if (object[7] != null)
								{
									if (object[7] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[7].toString());
										String time = object[8].toString();
										pojo.setHighPriorityAt(date + ", " + time.substring(0, time.length() - 3));
									}
									else
									{
										activityPojo.setHighPriorityAt("NA");
									}
									if (object[9] != null)
									{
										activityPojo.setHighPriorityBy(actionBy);
									}
									if (object[9] != null)
									{
										activityPojo.setHighPriorityReason((object[9].toString()));
									}
									if (object[25] != null)
									{
										activityPojo.setHighPriorityActionMode((object[25].toString()));
									}
								}
								//Ignore
								if (object[10] != null)
								{
									if (object[10] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[10].toString());
										String time = object[11].toString();
										activityPojo.setIgnoreAt(date + ", " + time.substring(0, time.length() - 3));
									}
									if (object[12] != null)
									{
										activityPojo.setIgnoreBy(actionBy);
									}
									if (object[12] != null)
									{
										activityPojo.setIgnoreReason((object[12].toString()));
									}
									if (object[24] != null)
									{
										activityPojo.setIgnoreActionMode((object[24].toString()));
									}
								
								}
								//Resolved
								if (object[13] != null)
								{
	
									if (object[13] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[13].toString());
										String time = object[14].toString().substring(0, object[14].toString().length() - 3);
										activityPojo.setResolveAt(date + ", " + time);
									}
									else
										activityPojo.setResolveAt("NA");
	
									if (object[16] != null)
									{
										activityPojo.setResolveBy((object[16].toString()));
									}
									else
									{
										activityPojo.setResolveBy("NA");
									}
									if (object[17] != null)
									{
										activityPojo.setCapa(object[17].toString());
									}
									else
									{
										activityPojo.setCapa("NA");
									}
									if (object[18] != null)
									{
										activityPojo.setRca((object[18].toString()));
									}
									else
									{
										activityPojo.setRca("NA");
									}
								}
								//Re-assign
								if (object[19] != null)
								{
	
									if (object[19] != null)
									{
										String date = DateUtil.convertDateToIndianFormat(object[19].toString());
										String time = object[20].toString();
										activityPojo.setReassignAt(date + ", " + time.substring(0, time.length() - 3));
									}
									else
									{	
										activityPojo.setReassignAt("NA");
									}	
									if (object[21] != null)
									{
										activityPojo.setReassignBy(actionBy);
									}
									if (object[21] != null)
									{
										activityPojo.setReassignReason(object[21].toString());
									}
									
								}
								//System.out.println("Department>>>>>>>>>>>>>>>."+pojo.getDept());
								statusList.add(activityPojo);
								//dataHashMap = sortByComparator(dataHashMap, DESC);
								
								//getCycleByOrder(dataList, dataHashMap, actionBy, tempList,count);
								//count++;
						}
						}	
						//System.out.println("list size>>>>>>>>>>>>>>"+statusList.size());
					}
					else
					{
						dataMap.put("Name", "NA");
						dataMap.put("Mobile", "NA");
						dataMap.put("Email", "NA");
						dataMap.put("Department", "NA");
					}
				}
				else if(getDataFor() != null && getDataFor().equalsIgnoreCase("stage") && getFeedStatId() != null && !getFeedStatId().equalsIgnoreCase("NA"))
				{
					Map<String, String> dataHashMap = new HashMap<String, String>();
					String adminDeptName = null;
					Properties configProp = new Properties();
					InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
					configProp.load(in);
					adminDeptName = configProp.getProperty("admin");
					List dataList = helperObject.getStageData(connectionSpace, cbt, getFeedStatId(), getTicketNo(),getActivityFlag());
					int recCount=0;
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list=(List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								activityPojo=new ActivityPojo();
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null)
								{
									activityPojo.setFstatus(object[0].toString());
								}
								if(object[1]!=null && object[2]!=null)
								{
									String date = DateUtil.convertDateToIndianFormat(object[1].toString());
									String time = object[2].toString();
									activityPojo.setDateTime(date + ", " + time.substring(0, time.length() - 3));
								}
								if(object[3]!=null)
								{
									activityPojo.setComments(object[3].toString());
								}
								else
								{
									activityPojo.setComments("NA");
								}
								if(object[4]!=null)
								{
									activityPojo.setAllot_to(object[4].toString());
								}
								if(object[5]!=null)
								{
									activityPojo.setMobNo(object[5].toString());
								}
								else
								{
									activityPojo.setMobNo("NA");
								}
								if(object[6]!=null && object[7]!=null)
								{
									activityPojo.setDept(object[6].toString());
									String rating=helperObject.getRatingForCategory(object[7].toString(),object[8].toString(),getTicketNo(),connectionSpace);
									if(list.size()>1)
									{
										activityPojo.setReason(object[6].toString()+", "+object[7].toString() +", "+rating);
									}
									else
									{	
										pojo.setReason(object[6].toString()+", "+object[7].toString() +", "+rating);
									}
								}
								if(object[10]!=null)
								{
									activityPojo.setTicket_no(object[10].toString());
								}
								if(object[13]!=null)
								{
									pojo.setFeed_by(object[13].toString());
								}
								if(object[14]!=null)
								{
									pojo.setFeed_by_mob(object[14].toString());
								}
								else
								{
									pojo.setFeed_by_mob("NA");
								}
								if(object[15]!=null)
								{
									if(activityPojo.getFstatus().equalsIgnoreCase("Ticket Opened")){
										activityPojo.setStage("1");
									}
									else
									{
										activityPojo.setStage(object[15].toString());
									}
									
								}
								if(recCount==0 && getActivityFlag().equalsIgnoreCase("1"))
								{
									//activityPojo.setStage("1");
									recCount++;
									activityPojo.setDept(adminDeptName);
								}
								/*else
								{
									activityPojo.setStage("2");
								}*/
								
								statusList.add(activityPojo);
							}
						}
					}
				}
				else
				{
					List dataList = null;
					int countRec=0;
					dataMapTAT=new LinkedHashMap<String, Map<String,String>>();
					if (getId() != null && !getId().equalsIgnoreCase("") && !getId().equalsIgnoreCase("0"))
					{
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, getId(), false, getDataFor(), getTicketNo(),getActivityFlag());
					}
					String addressingDate = null, addressingTime = "00:00", resolutionDate = null, resolutionTime = "00:00";
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list=(List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
								{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("ticket_no", (object[9].toString()));
								dataMap.put("Status", (object[0].toString()));
								dataMap.put("Level", (object[1].toString()));
								dataMap.put("Open Date & Time", DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, object[3].toString().length() - 3));
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[4].toString(), object[5].toString(), "Y", object[2].toString(), object[3].toString(), "FM");
								addressingDate = dateTime.get(0);
								addressingTime = dateTime.get(1);
								resolutionDate = dateTime.get(2);
								resolutionTime = dateTime.get(3);
								dateTime.clear();
								dataMap.put("Addressing Date & Time", DateUtil.convertDateToIndianFormat(addressingDate) + ", " + addressingTime);
								dataMap.put("Resolution Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
								List empList=null;
								if(getActivityFlag().equalsIgnoreCase("1") && countRec==0)
								{
									 empList= helperObject.getEmp4Escalation(String.valueOf(helperObject.getAdminDeptId(connectionSpace)), "FM", "2", connectionSpace, cbt);
									 countRec++;
								}
								else
								{
									empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "2", connectionSpace, cbt);
								}
								//List empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "2", connectionSpace, cbt);
								StringBuilder empName = new StringBuilder();
								if (empList != null)
								{
									for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
									{
										Object[] object2 = (Object[]) iterator2.next();
										if (object2[1] != null)
										{
											empName.append(object2[1].toString() + ", ");
										}
									}
									if (empName != null && empName.length() > 0)
									{
										dataMap.put("L-2 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
										dataMap.put("L-2 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
									}
									empList.clear();
									empName.setLength(0);
	
									dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "FM");
									resolutionDate = dateTime.get(0);
									resolutionTime = dateTime.get(1);
									empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "3", connectionSpace, cbt);
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
	
										dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "FM");
										resolutionDate = dateTime.get(0);
										resolutionTime = dateTime.get(1);
										empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "4", connectionSpace, cbt);
	
										if (empList != null && empList.size() > 0)
										{
											for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
											{
												Object[] object2 = (Object[]) iterator2.next();
												if (object2[1] != null)
												{
													empName.append(object2[1].toString() + ", ");
												}
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
								
								dataMapTAT.put(dataMap.get("Open Date & Time"), dataMap);
								//dataMap.clear();
								dataMap=new HashMap<String, String>();
								/*count++;
								addressingDate = null;
								addressingTime = "00:00";
								resolutionDate = null;
								resolutionTime = "00:00";
								*/
						}
						}
					}
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

	public void getCycleByOrder(List dataList, Map<String, String> dataMap, String actionBy, List tempList,int count)
	{
		finalStatusMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> pendingMap = new LinkedHashMap<String, String>();
		Map<String, String> snoozeMap = new LinkedHashMap<String, String>();
		Map<String, String> hpMap = new LinkedHashMap<String, String>();
		Map<String, String> seekMap = new LinkedHashMap<String, String>();
		Map<String, String> reassignMap = new LinkedHashMap<String, String>();
		Map<String, String> ignoreMap = new LinkedHashMap<String, String>();
		Map<String, String> resolvedMap = new LinkedHashMap<String, String>();
		for (Entry<String, String> entry : dataMap.entrySet())
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (entry.getKey().equalsIgnoreCase("Pending"))
				{
					pendingMap.put("Opened By", "User Name");
					
					if (object[31] != null)
					{

						pendingMap.put("Opened For", object[31].toString());
					}
					
					if (object[26] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[26].toString());
						String time = object[27].toString();
						pendingMap.put("Opened At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
					{	
						pendingMap.put("Opened At", "NA");
					}	
					if (object[28] != null)
					{

						pendingMap.put("Category", object[28].toString());
					}
					
					if (object[29] != null)
					{

						pendingMap.put("Sub Category", object[29].toString());
					}
					
					
					if (object[30] != null)
					{

						pendingMap.put("Brief", object[30].toString());
					}
					
					finalStatusMap.put("Pending", pendingMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Snooze"))
				{
					if (object[1] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[1].toString());
						String time = object[2].toString();

						snoozeMap.put("Snooze At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						snoozeMap.put("Snooze At", "NA");

					if (object[3] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[3].toString());
						String time = object[4].toString();

						snoozeMap.put("Snooze Upto", date + ", " + time);
					}
					else
						snoozeMap.put("Snooze Upto", "NA");

					snoozeMap.put("Durartion", (object[5].toString()));
					snoozeMap.put("Snooze By", actionBy);
					snoozeMap.put("Reasons", (object[6].toString()));
					finalStatusMap.put("Snooze", snoozeMap);
				}
				else if (entry.getKey().equalsIgnoreCase("High Priority"))
				{
					if (object[7] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[7].toString());
						String time = object[8].toString();

						hpMap.put("High Priority At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						hpMap.put("High Priority At", "NA");

					hpMap.put("High Priority By", actionBy);
					hpMap.put("Reasons", (object[9].toString()));
					hpMap.put("Action Mode", (object[25].toString()));
					finalStatusMap.put("High Priority", hpMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Ignore"))
				{
					if (object[10] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[10].toString());
						String time = object[11].toString();

						ignoreMap.put("Ignore At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						ignoreMap.put("Ignore At", "NA");

					ignoreMap.put("Ignore By", actionBy);
					ignoreMap.put("Reasons", (object[12].toString()));
					ignoreMap.put("Action Mode", (object[24].toString()));
					finalStatusMap.put("Ignore", ignoreMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Resolved"))
				{
					if (object[13] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[13].toString());
						String time = object[14].toString().substring(0, object[14].toString().length() - 3);

						resolvedMap.put("Resolve At", date + ", " + time);
					}
					else
						resolvedMap.put("Resolve At", "NA");

					if (object[16] != null)
					{
						resolvedMap.put("Resolved By", (object[16].toString()));
					}
					else
					{
						resolvedMap.put("Resolved By", ("NA"));
					}
					if (object[17] != null)
					{
						resolvedMap.put("CAPA", (object[17].toString()));
					}
					else
					{
						resolvedMap.put("CAPA", ("NA"));
					}
					if (object[18] != null)
					{
						resolvedMap.put("RCA", (object[18].toString()));
					}
					else
					{
						resolvedMap.put("RCA", ("NA"));
					}

					// resolvedMap.put("Action Mode", (object[23].toString()));
					finalStatusMap.put("Resolved", resolvedMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Re-assign"))
				{
					if (object[19] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[19].toString());
						String time = object[20].toString();

						reassignMap.put("Re-assign At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						reassignMap.put("Re-assign At", "NA");

					reassignMap.put("Re-assign By", actionBy);
					reassignMap.put("Reason", (object[21].toString()));
					finalStatusMap.put("Re-assign", reassignMap);
				}
				else if (entry.getKey().equalsIgnoreCase("SeekApproval"))
				{
					if (tempList != null && tempList.size() > 0)
					{
						String requestedDate = null;
						for (Iterator iterator2 = tempList.iterator(); iterator2.hasNext();)
						{
							Object[] object1 = (Object[]) iterator2.next();
							if (object1[1] != null)
							{

								String date = DateUtil.convertDateToIndianFormat(object1[1].toString());
								String time = object1[2].toString();

								seekMap.put("Request At", date + ", " + time);
							}

							seekMap.put("Requested By", (object1[3].toString()));
							seekMap.put("Reasons", (object1[4].toString()));
							seekMap.put("Requested To", (object1[5].toString()));
							seekMap.put("Current Status", (object1[6].toString()));
							if (object1[7] != null)
							{
								String date = DateUtil.convertDateToIndianFormat(object1[7].toString());
								String time = object1[8].toString();
								seekMap.put("Approved At", date + ", " + time);
							}
							else
								seekMap.put("Approved At", "NA");

							seekMap.put("Comments", (object1[9].toString()));
						}
						finalStatusMap.put("Seek Approval", seekMap);
					}
				}
			}
		}
		//statusMap.put(String.valueOf(count), finalStatusMap);
		//System.out.println("size status Map>>>>>>>>>>>>"+count);
		
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

		for (Entry<String, String> entry : sortedMap.entrySet())
		{
			//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}

		return sortedMap;
	}

	public String viewReopenedTATDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getReopenTATDetails(connectionSpace, getId());
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public ActivityPojo getPojo()
	{
		return pojo;
	}

	public void setPojo(ActivityPojo pojo)
	{
		this.pojo = pojo;
	}

	public List<ConfigurationUtilBean> getPaperRatings()
	{
		return paperRatings;
	}

	public void setPaperRatings(List<ConfigurationUtilBean> paperRatings)
	{
		this.paperRatings = paperRatings;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public String getFeedStatId()
	{
		return feedStatId;
	}

	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public static boolean isDesc()
	{
		return DESC;
	}

	public Map<String, Map<String, String>> getFinalStatusMap()
	{
		return finalStatusMap;
	}

	public void setFinalStatusMap(Map<String, Map<String, String>> finalStatusMap)
	{
		this.finalStatusMap = finalStatusMap;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public Map<String,Map<String,String>> getDataMapTAT()
	{
		return dataMapTAT;
	}

	public void setDataMapTAT(Map<String,Map<String,String>> dataMapTAT)
	{
		this.dataMapTAT = dataMapTAT;
	}

	public Map<String, Map<String, Map<String, String>>> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, Map<String, Map<String, String>>> statusMap) {
		this.statusMap = statusMap;
	}

	public List<ActivityPojo> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<ActivityPojo> statusList) {
		this.statusList = statusList;
	}

	public String getActivityFlag()
	{
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}
	
}
