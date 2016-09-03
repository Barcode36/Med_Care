package com.Over2Cloud.ctrl.helpdesk.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.CategoryAnalisysPojo;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

@SuppressWarnings("serial")
public class DashboardAction extends GridPropertyBean
{

	private String ticket_id;
	private String status_for;

	private String dataFlag = "";
	private String feedStatus;
	private String dashFor = "";
	private String level = "";
	private String d_subdept_id = "";

	private String dashboard;
	private String feedFor;
	private String fromDate = "";
	private String toDate = "";

	private String l_pending = "0";
	private String l_snooze = "0";
	private String l_hp = "0";
	private String l_ignore = "0";
	private String l_resolved = "0";

	private String r_pending = "0";
	private String r_snooze = "0";
	private String r_hp = "0";
	private String r_ignore = "0";
	private String r_resolved = "0";

	private String flag;
	private String feedFor1;
	private String feedFor2;
	private String deptHierarchy,data4;
	private String caption;
	private String headerValue;

	Map<String, Integer> graphCatgMap = null;
	Map<String, Integer> graphLevelMap = null;
	List<FeedbackPojo> ticketsList = null;
	List<FeedbackPojo> mgmtTicketsList = null;
	List<DashboardPojo> levelTicketsList = null;
	List<FeedbackPojo> empCountList = null;
	List<FeedbackPojo> catgCountList = null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private String dept = "", dept_id = "";
	String empName = "";
	DashboardPojo dashObj = null;
	DashboardPojo leveldashObj = null;
	DashboardPojo deptdashObj = null;
	private List<DashboardPojo> deptdashList = null;
	private List<DashboardPojo> subdeptdashList = null;
	List<DashboardPojo> dept_subdeptcounterList = null;
	List<FeedbackPojo> feedbackList = null;
	FeedbackPojo FP = null;
	private String loginType;
	private boolean hodFlag;
	private boolean mgmtFlag;
	private boolean normalFlag;
	private String maximizeDivBlock;
	private Map<Integer, Integer> doubleMap;

	private String dataCheck = "";
	private Map<Integer, String> serviceDeptList;
	private String filterFlag = null;
	private String filterDeptId = null;
	List<DashboardPojo> location_ticketList = null;
	private String xaxis = null;
	private String escalated,reopen;
	private CategoryAnalisysPojo categPojo=null;
	private List<CategoryAnalisysPojo> listCateg=null;
	
	@SuppressWarnings("rawtypes")
	public void generalMethod()
	{
		try
		{
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "2");
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empName = object[0].toString();
					dept_id = object[3].toString();
					dept = object[4].toString();
					loginType = object[7].toString();

					if (loginType.equalsIgnoreCase("H"))
					{
						hodFlag = true;
						headerValue = dept;
					} else if (loginType.equalsIgnoreCase("M"))
					{
						mgmtFlag = true;
						headerValue = "All Department";
					} else if (loginType.equalsIgnoreCase("N"))
					{
						headerValue = DateUtil.makeTitle(empName);
						normalFlag = true;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// First method for Helpdesk Dashboard
	public String beforeDashboardAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				this.generalMethod();
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				// ********************************************//
				// Scrolling Data(First Block)
				// getTicketDetail(loginType,dept_id,empName, connectionSpace);
				// ********************************************//

				// ********************************************//
				// Ticket Counters on the basis of status(Second Block)

				if (dashFor != null && dashFor.equalsIgnoreCase("Status"))
				{
					getCounterData(fromDate, toDate);
				}
				// ********************************************//

				// ********************************************//
				// Level wise Ticket Detail in Table (Fifth Block)
				if (dashFor != null && dashFor.equalsIgnoreCase("level"))
				{
					getTicketDetailByLevel("T", fromDate, toDate);
				}
				// Level wise Ticket Detail in Graph (Fifth Block)
				// getTicketDetailByLevel("G",fromDate,toDate);
				// ********************************************//

				// ********************************************//
				// Analytics for category in table (Third Block)
				if (dashFor != null && dashFor.equalsIgnoreCase("categ"))
				{
					getAnalytics4SubCategory("T");
				}
				// Analytics for category in graph (Third Block)
				// getAnalytics4SubCategory("G");
				// ********************************************//

				// getDataInBars(connectionSpace);
				getServiceDept();
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

	public String beforeDashboardActionLocation()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				this.generalMethod();
				getServiceDept();
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				if (flag != null && flag.equalsIgnoreCase("location"))
				{
					getCounterDataForLocation(fromDate, toDate);
				} else if (flag != null && flag.equalsIgnoreCase("time"))
				{
					getCounterDataForTime(fromDate, toDate);
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
	void getServiceDept()
	{
		serviceDeptList = new LinkedHashMap<Integer, String>();

		List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "HDM", connectionSpace);
		if (departmentlist != null && departmentlist.size() > 0)
		{
			for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
			{
				Object[] object1 = (Object[]) iterator.next();
				serviceDeptList.put((Integer) object1[0], object1[1].toString());
			}

		}
	}

	// For Bar Graph
	public String getBarChart4DeptCounters()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				this.generalMethod();
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				getCounterData(fromDate, toDate);
				if (filterFlag != null && filterFlag.equalsIgnoreCase("H"))
				{
					getServiceDept();
				} else
				{

				}
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				int total = 0;
				for (DashboardPojo pojo : dept_subdeptcounterList)
				{
					total = Integer.parseInt(pojo.getPc()) + Integer.parseInt(pojo.getSc()) + Integer.parseInt(pojo.getIgc()) + Integer.parseInt(pojo.getRc()) + Integer.parseInt(pojo.getRac()) + Integer.parseInt(pojo.getReopc());
					if (total != 0)
					{
						jsonObject.put("dept", pojo.getDeptName());
						jsonObject.put("deptId", pojo.getId());
						jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
						jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
						jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
						jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
						jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
						jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

						jsonArray.add(jsonObject);
						total = 0;
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// For Bar Graph
	public String getBarChart4DeptCountersTime()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				getCounterDataForTime(fromDate, toDate);

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (DashboardPojo pojo : location_ticketList)
				{
					jsonObject.put("time", pojo.getTime());
					jsonObject.put("timeId", pojo.getId());
					jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
					jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
					jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
					jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
					jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
					jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
					jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

					jsonArray.add(jsonObject);
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// For Bar Graph
	public String getBarChart4DeptCountersLocation()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				getCounterDataForLocation(fromDate, toDate);

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (DashboardPojo pojo : location_ticketList)
				{
					jsonObject.put("floor", pojo.getLocation());
					jsonObject.put("floorId", Integer.parseInt(pojo.getLocId()));
					jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
					jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
					jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
					jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
					jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
					jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
					jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

					jsonArray.add(jsonObject);
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	//time floor analysis
	
	public String getTimeFloorAnalysis()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				if(xaxis != null && xaxis.equalsIgnoreCase("time"))
				{
					getCounterDataTimeLocation(fromDate, toDate);
				}
				else if(xaxis != null && xaxis.equalsIgnoreCase("staff"))
				{
					getCounterDataTimeStaff(fromDate, toDate);
				}
				

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (DashboardPojo pojo : location_ticketList)
				{
					if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						jsonObject.put("floor", pojo.getLocation());
						jsonObject.put("floorId", Integer.parseInt(pojo.getLocId()));

					}  else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
					{
						jsonObject.put("staff", pojo.getEmpName());
						jsonObject.put("staffId", pojo.getEmpId());

					}
					
					jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
					jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
					jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
					jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
					jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
					jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
					jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

					jsonArray.add(jsonObject);
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	
	
	//staff floor analysis
	
		public String getStaffFloorAnalysis()
		{
			boolean validSession = ValidateSession.checkSession();
			if (validSession)
			{
				try
				{
					if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
					{
						fromDate = DateUtil.getCurrentDateIndianFormat();
						toDate = DateUtil.getCurrentDateIndianFormat();
					}
					if(xaxis != null && xaxis.equalsIgnoreCase("staff"))
					{
						getCounterDataStaffLocation(fromDate, toDate);
					}
					else if(xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						getCounterDataStaffTime(fromDate, toDate);
					}
					

					jsonObject = new JSONObject();
					jsonArray = new JSONArray();

					for (DashboardPojo pojo : location_ticketList)
					{
						if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
						{
							jsonObject.put("floor", pojo.getLocation());
							jsonObject.put("floorId", Integer.parseInt(pojo.getLocId()));

						}  else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
						{
							jsonObject.put("time", pojo.getTime());

						}
						
						jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
						jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
						jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
						jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
						jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
						jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
						jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

						jsonArray.add(jsonObject);
					}
					return SUCCESS;
				} catch (Exception e)
				{
					return ERROR;
				}
			} else
			{
				return LOGIN;
			}
		}

	
	public String getFloorWingAnalysis()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				if (flag != null && flag.equalsIgnoreCase("floor"))
				{
					getCounterDataForFloorWing(fromDate, toDate);
				} else if (flag != null && flag.equalsIgnoreCase("wing"))
				{
					getCounterDataForWingRoom(fromDate, toDate);
				}

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (DashboardPojo pojo : location_ticketList)
				{
					if (xaxis != null && xaxis.equalsIgnoreCase("location"))
					{
						jsonObject.put("wing", pojo.getWingsname());
						jsonObject.put("wingId", Integer.parseInt(pojo.getWingId()));

					} else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						jsonObject.put("time", pojo.getTime());
						jsonObject.put("timeId", pojo.getId());
						
					} else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
					{
						jsonObject.put("staff", pojo.getEmpName());
						jsonObject.put("staffId", pojo.getEmpId());

					}

					jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));

					jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));

					jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));

					jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));

					jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));

					jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

					jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));

					jsonArray.add(jsonObject);
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getBarChart4DeptCountersStaff()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				getCounterDataForStaff(fromDate, toDate);

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (DashboardPojo pojo : location_ticketList)
				{
					jsonObject.put("staff", pojo.getEmpName());
					jsonObject.put("staffId", pojo.getEmpId());
					jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
					jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
					jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
					jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
					jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
					jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
					jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

					jsonArray.add(jsonObject);
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// For Bar Graph
	public String getBarChart4LevelCounters()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				this.generalMethod();

				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				getTicketDetailByLevel("T", fromDate, toDate);
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				int total = 0;
				for (DashboardPojo pojo : levelTicketsList)
				{
					// calculate total to not show in dashboard value is 0
					total = Integer.parseInt(pojo.getPc()) + Integer.parseInt(pojo.getHpc()) + Integer.parseInt(pojo.getSc()) + Integer.parseInt(pojo.getIgc()) + Integer.parseInt(pojo.getRc()) + Integer.parseInt(pojo.getRac()) + Integer.parseInt(pojo.getReopc());
					if (total != 0)
					{
						jsonObject.put("Level", pojo.getLevel());
						
						jsonObject.put("Pending", Integer.parseInt(pojo.getPc()));
						jsonObject.put("HighPriority", Integer.parseInt(pojo.getHpc()));
						jsonObject.put("Snooze", Integer.parseInt(pojo.getSc()));
						jsonObject.put("Ignore", Integer.parseInt(pojo.getIgc()));
						jsonObject.put("Resolved", Integer.parseInt(pojo.getRc()));
						jsonObject.put("Reassigned", Integer.parseInt(pojo.getRac()));
						jsonObject.put("Reopened", Integer.parseInt(pojo.getReopc()));

						jsonArray.add(jsonObject);
						total = 0;
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// for pie chart
	public String getBarChart4CategCounters()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				this.generalMethod();
				if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				// System.out.println(dept_id+"dept ID"+filterDeptId);
				getAnalytics4SubCategory("T");

				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (FeedbackPojo pojo : catgCountList)
				{
					jsonObject.put("Category", pojo.getFeedback_catg());
					jsonObject.put("Counter", pojo.getCounter());
					jsonObject.put("Id", pojo.getId());
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

	// Ticket Counters on the basis of status At HOD Level

	@SuppressWarnings("rawtypes")
	public void getCounterData(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{
			if (filterFlag == null || filterFlag.equalsIgnoreCase(""))
			{
				// this.generalMethod();
			} else
			{
				loginType = filterFlag;
				if (filterFlag != null && filterFlag.equalsIgnoreCase("H"))
				{
					hodFlag = true;
				}
			}
			/*if(filterFlag != null)
			{
				dataFlag="SC";
			}*/
			/*else
			{
				dataFlag="T";
			}*/
			flag = DateUtil.getCurrentDateIndianFormat().split("-")[0];
			dept_subdeptcounterList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = null;
			if (filterDeptId == null || filterDeptId.equalsIgnoreCase(""))
			{
				dept_subdeptNameList = new HelpdeskUniversalHelper().getSubDeptListByUID(loginType, dept_id, empName, connectionSpace);
			} else
			{
				dept_subdeptNameList = new HelpdeskUniversalHelper().getSubDeptListByUID(loginType, filterDeptId, empName, connectionSpace);
			}

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				int total=0;
				int hptot=0,igtot=0,pentot=0,snootot=0,reotot=0,reatot=0,restot=0,lasttot=0;
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setId(object1[0].toString());
					DP.setDeptName(object1[1].toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{
						dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("dept", "All", empName, object1[0].toString(), connectionSpace, fromDate, toDate);
					} else if (loginType.equalsIgnoreCase("H"))
					{
						dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("subdept", "All", empName, object1[0].toString(), connectionSpace, fromDate, toDate);
					} else if (loginType.equalsIgnoreCase("N"))
					{
						dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("subdept", "Normal", empName, object1[0].toString(), connectionSpace, fromDate, toDate);
					} else if (loginType.equalsIgnoreCase("C"))
					{
						dept_subdeptCounterList = new DashboardHelper().getDashboardCounter("subcatg", "", empName, object1[0].toString(), connectionSpace, fromDate, toDate);
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							total+=Integer.parseInt(object2[1].toString());
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								hptot=hptot+Integer.parseInt(object2[1].toString());
								DP.setHpc(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								igtot=igtot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								pentot=pentot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								snootot=snootot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								reotot=reotot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								reatot=reatot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								restot=restot+Integer.parseInt(object2[1].toString());
							}
							DP.setHt(Integer.toString(Integer.parseInt(DP.getHpc())+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc())));
						//	lasttot=lasttot+Integer.parseInt(DP.getHpc())+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc());
						}
					}
					if(total>0)
					{
						dept_subdeptcounterList.add(DP);
						dashObj.setDashList(dept_subdeptcounterList);
					}
					total=0;
				}
				if(data4!=null && data4.equalsIgnoreCase("table"))
				{
				DP=new DashboardPojo();
				DP.setId("-1");
				DP.setDeptName("Total");
				DP.setHpct(Integer.toString(hptot));
				DP.setIgnt(Integer.toString(igtot));
				DP.setPct(Integer.toString(pentot));
				DP.setSct(Integer.toString(snootot));
				DP.setReopct(Integer.toString(reotot));
				DP.setRact(Integer.toString(reatot));
				DP.setRct(Integer.toString(restot));
				String str=Integer.toString(hptot+igtot+pentot+snootot+reotot+reatot+restot);
				DP.setGrand(str);
				dept_subdeptcounterList.add(DP);
				dashObj.setDashList(dept_subdeptcounterList);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private void getCounterDataForFloorWing(String fromDate2, String toDate2)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = null;
			this.generalMethod();

			if (xaxis != null && xaxis.equalsIgnoreCase("location"))
			{
				dept_subdeptNameList = new DashboardHelper().getAllWings(dataFlag, connectionSpace);
			} else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
			{
				dept_subdeptNameList = getTimeList();
			} else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
			{
				dept_subdeptNameList = new DashboardHelper().getAllStaff(filterDeptId, connectionSpace);
			}

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = null;
					Object object2 = null;
					if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						object2 = (Object) iterator.next();
					} else
					{

						object1 = (Object[]) iterator.next();
					}

					DP = new DashboardPojo();
					if (xaxis != null && xaxis.equalsIgnoreCase("location"))
					{
						DP.setWingId(object1[0].toString());
						DP.setWingsname(object1[1].toString());
					} else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						DP.setTime(getTimeNameOfTime(object2.toString()));
						DP.setId(object2.toString());
					} else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
					{
						DP.setEmpId(object1[0].toString());
						DP.setEmpName(object1[1].toString());
					}

					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")) && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						} else if (filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "dept", "", filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "dept", "", filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "dept", "", "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "dept", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						}
					} else if (loginType.equalsIgnoreCase("H") && xaxis != null)
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")) && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						}

					} else if (loginType.equalsIgnoreCase("N") && xaxis != null)
					{
						if (xaxis != null && xaxis.equalsIgnoreCase("time"))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "", "", "", object2.toString(), connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "", "", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
						}

					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object3 = (Object[]) iterator2.next();

							if (object3[0].toString().equalsIgnoreCase("High Priority") && dashFor.equalsIgnoreCase("HighPriority"))
							{
								DP.setHpc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Ignore") && dashFor.equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Pending") && dashFor.equalsIgnoreCase("Pending"))
							{
								DP.setPc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Snooze") && dashFor.equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Re-Opened") && dashFor.equalsIgnoreCase("Reopened"))
							{
								DP.setReopc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Re-Assign") && dashFor.equalsIgnoreCase("Reassigned"))
							{
								DP.setRac(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Resolved") && dashFor.equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	private void getCounterDataForWingRoom(String fromDate2, String toDate2)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = null;
			this.generalMethod();

			if (xaxis != null && xaxis.equalsIgnoreCase("location"))
			{
				dept_subdeptNameList = new DashboardHelper().getAllRooms(dataFlag, connectionSpace);
			} else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
			{
				dept_subdeptNameList = getTimeList();
			} else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
			{
				dept_subdeptNameList = new DashboardHelper().getAllStaff(filterDeptId, connectionSpace);
			}

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = null;
					Object object2 = null;
					if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						object2 = (Object) iterator.next();
					} else
					{

						object1 = (Object[]) iterator.next();
					}

					DP = new DashboardPojo();
					if (xaxis != null && xaxis.equalsIgnoreCase("location"))
					{
						DP.setWingId(object1[0].toString());
						DP.setWingsname(object1[1].toString());
					} else if (xaxis != null && xaxis.equalsIgnoreCase("time"))
					{
						DP.setTime(getTimeNameOfTime(object2.toString()));
						DP.setId(object2.toString());
					} else if (xaxis != null && xaxis.equalsIgnoreCase("staff"))
					{
						DP.setEmpId(object1[0].toString());
						DP.setEmpName(object1[1].toString());
					}

					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")) && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						} else if (filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "dept", "", filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "dept", "", filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "dept", "", "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "dept", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						}
					} else if (loginType.equalsIgnoreCase("H") && xaxis != null)
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")) && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, "", object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase("") && xaxis != null)
						{
							if (xaxis != null && xaxis.equalsIgnoreCase("time"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, filterFlag, object2.toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						}

					} else if (loginType.equalsIgnoreCase("N") && xaxis != null)
					{
						if (xaxis != null && xaxis.equalsIgnoreCase("time"))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "", "", "", object2.toString(), connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorWingTimeStaffDetail(dataFlag, xaxis, "room", "", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
						}

					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object3 = (Object[]) iterator2.next();

							if (object3[0].toString().equalsIgnoreCase("High Priority") && dashFor.equalsIgnoreCase("HighPriority"))
							{
								DP.setHpc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Ignore") && dashFor.equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Pending") && dashFor.equalsIgnoreCase("Pending"))
							{
								DP.setPc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Snooze") && dashFor.equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Re-Opened") && dashFor.equalsIgnoreCase("Reopened"))
							{
								DP.setReopc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Re-Assign") && dashFor.equalsIgnoreCase("Reassigned"))
							{
								DP.setRac(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							} else if (object3[0].toString().equalsIgnoreCase("Resolved") && dashFor.equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object3[1].toString());
								totalCount += Integer.parseInt(object3[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	public void getCounterDataForLocation(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = null;
			this.generalMethod();
			dept_subdeptNameList = new HelpdeskUniversalHelper().getAllFloors(connectionSpace);

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				int hptot=0,igtot=0,pentot=0,snootot=0,reotot=0,reatot=0,restot=0,lasttot=0;
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setLocId(object1[0].toString());
					DP.setLocation(object1[1].toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{
						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						} else if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("dept", "", filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("dept", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("H"))
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("subdept", filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("subdept", filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("N"))
					{
						dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetail("", "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								DP.setHpc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								hptot=hptot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								igtot=igtot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								pentot=pentot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								snootot=snootot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reotot=reotot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reatot=reatot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								restot=restot+Integer.parseInt(object2[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}
				}
				if(data4!=null && data4.equalsIgnoreCase("table"))
				{
				DP=new DashboardPojo();
				DP.setLocId("-1");
				DP.setLocation("Total");
				DP.setHpct(Integer.toString(hptot));
				DP.setIgnt(Integer.toString(igtot));
				DP.setPct(Integer.toString(pentot));
				DP.setSct(Integer.toString(snootot));
				DP.setReopct(Integer.toString(reotot));
				DP.setRact(Integer.toString(reatot));
				DP.setRct(Integer.toString(restot));
				String str=Integer.toString(igtot+pentot+snootot+reotot+reatot+restot);
				//System.out.println("::::::::::::::::::::::::::::::::"+str);
				DP.setGrand(str);
				location_ticketList.add(DP);
				dashObj.setDashList(location_ticketList);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String beforegetCounterCategoryData(){
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			if (fromDate == null || fromDate.equalsIgnoreCase("") && toDate == null || toDate.equalsIgnoreCase(""))
				{
					fromDate = DateUtil.getCurrentDateIndianFormat();
					toDate = DateUtil.getCurrentDateIndianFormat();
				}
				try
				{
						if(dashFor!=null && dashFor.equalsIgnoreCase("floor")){
							getCounterCategoryData();
						}
						else
						{
							getCounterCategoryTimeData();
						}
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				return SUCCESS;
		} else
		{
			return LOGIN;
		}
		
		
	}
	
	//category data deptwise floor wise
	
	@SuppressWarnings("rawtypes")
	public String getCounterCategoryData()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			
				//getServiceDept();
				categPojo = new CategoryAnalisysPojo();
				CategoryAnalisysPojo DP = null;
				try
				{

					listCateg = new ArrayList<CategoryAnalisysPojo>();
					List dept_subdeptNameList = null;
					this.generalMethod();
					if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase(""))
					{
						dept_subdeptNameList = new DashboardHelper().getCategoryByDept(filterDeptId, connectionSpace);
					}
					else
					{
						dept_subdeptNameList = new DashboardHelper().getCategoryByDept(dept_id, connectionSpace);	
					}
					

					if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
					{
						for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
						{
							Object[] object1 = (Object[]) iterator.next();
							DP = new CategoryAnalisysPojo();
							DP.setCategId(object1[0].toString());
							DP.setCategName(object1[1].toString());
							// List for counters for Pending/ Snooze/ High Priority/
							// Ignore Status
							
							List dept_subdeptCounterList = new ArrayList();

							if (loginType.equalsIgnoreCase("M"))
							{
								
									dept_subdeptCounterList = new DashboardHelper().getAllFloorWiseCategCounter( dataFlag,filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);

								
			
							} else if (loginType.equalsIgnoreCase("H"))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorWiseCategCounter( dataFlag,filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
							if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
							{
								int totalCount = 0;
								for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
								{
									
									Object[] object2 = (Object[]) iterator2.next();
									if(object2[0].toString().equalsIgnoreCase("1st Floor"))
									{
										DP.setFlr1(object2[1].toString());
										totalCount += Integer.parseInt(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("2nd Floor"))
									{
										DP.setFlr2(object2[1].toString());
										totalCount += Integer.parseInt(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("3rd Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr3(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("4th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr4(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("5th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr5(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("6th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr6(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("7th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr7(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("8th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr8(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("9th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr9(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("10th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr10(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("11th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr11(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("12th Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr12(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("14h Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr14(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("15 Floor"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlr15(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("LG"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlrLG(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("UG"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlrUG(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("Basement"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlrBase(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("Outside"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlrOut(object2[1].toString());
									}else if(object2[0].toString().equalsIgnoreCase("Terrace"))
									{
										totalCount += Integer.parseInt(object2[1].toString());
										DP.setFlrTerr(object2[1].toString());
									}
									
								}
								DP.setTotal(String.valueOf(totalCount));
							}
							if (Integer.parseInt(DP.getTotal()) > 0)
							{
								listCateg.add(DP);
								categPojo.setCategList(listCateg);
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				return SUCCESS;
		} else
		{
			return LOGIN;
		}
		
	}
	
	//category data deptwise Time wise
	
		@SuppressWarnings("rawtypes")
		public String getCounterCategoryTimeData()
		{
			boolean validSession = ValidateSession.checkSession();
			if (validSession)
			{
				
					//getServiceDept();
					categPojo = new CategoryAnalisysPojo();
					CategoryAnalisysPojo DP = null;
					try
					{

						listCateg = new ArrayList<CategoryAnalisysPojo>();
						List dept_subdeptNameList = null;
						this.generalMethod();
						if(filterDeptId!=null && !filterDeptId.equalsIgnoreCase(""))
						{
							dept_subdeptNameList = new DashboardHelper().getCategoryByDept(filterDeptId, connectionSpace);
						}
						else
						{
							dept_subdeptNameList = new DashboardHelper().getCategoryByDept(dept_id, connectionSpace);	
						}
						

						if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
						{
							for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
							{
								Object[] object1 = (Object[]) iterator.next();
								DP = new CategoryAnalisysPojo();
								DP.setCategId(object1[0].toString());
								DP.setCategName(object1[1].toString());
								// List for counters for Pending/ Snooze/ High Priority/
								// Ignore Status
								
								List dept_subdeptCounterList = new ArrayList();

								if (loginType.equalsIgnoreCase("M"))
								{
									
										dept_subdeptCounterList = new DashboardHelper().getAllTimeWiseCategCounter( filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);

									
				
								} else if (loginType.equalsIgnoreCase("H"))
								{
									dept_subdeptCounterList = new DashboardHelper().getAllTimeWiseCategCounter( filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
								}
								if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
								{
									int totalCount = 0,temp=0;
									for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
									{
										
										Object[] object2 = (Object[]) iterator2.next();
										if(DateUtil.checkTimeBetween2Times("23:00:00","24:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm12());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm12(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										}
										else if(DateUtil.checkTimeBetween2Times("00:00:00","01:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm1());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm1(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("01:00:00","02:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm2());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm2(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("02:00:00","03:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm3());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm3(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("03:00:00","04:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm4());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm4(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("04:00:00","05:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm5());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm5(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("05:00:00","06:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm6());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm6(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("06:00:00","07:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm7());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm7(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("07:00:00","08:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm8());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm8(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("08:00:00","09:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm9());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm9(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("09:00:00","10:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm10());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm10(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("10:00:00","11:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getAm11());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setAm11(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("11:00:00","12:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm12());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm12(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("12:00:00","13:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm1());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm1(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("13:00:00","14:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm2());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm2(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("14:00:00","15:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm3());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm3(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("15:00:00","16:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm4());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm4(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("16:00:00","17:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm5());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm5(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("17:00:00","18:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm6());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm6(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("18:00:00","19:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm7());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm7(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("19:00:00","20:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm8());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm8(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("20:00:00","21:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm9());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm9(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("21:00:00","22:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm10());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm10(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										else if(DateUtil.checkTimeBetween2Times("22:00:00","23:00:00",object2[0].toString()))
										{
											temp=Integer.parseInt(DP.getPm11());
											temp=temp+Integer.parseInt(object2[1].toString());
											DP.setPm11(String.valueOf(temp));
											totalCount += Integer.parseInt(object2[1].toString());
										
										}
										
									}
									DP.setTotal(String.valueOf(totalCount));
								}
								
								if (Integer.parseInt(DP.getTotal()) > Integer.parseInt(dataFlag))
								{
									listCateg.add(DP);
									Collections.sort(listCateg);
									categPojo.setCategList(listCateg);
								}
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					return SUCCESS;
			} else
			{
				return LOGIN;
			}
			
		}
	
	//data method for time click location view
	@SuppressWarnings("rawtypes")
	public void getCounterDataStaffLocation(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = null;
			this.generalMethod();
			dept_subdeptNameList = new HelpdeskUniversalHelper().getAllFloors(connectionSpace);

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setLocId(object1[0].toString());
					DP.setLocation(object1[1].toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{
						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("subdept", dataFlag,filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("subdept", dataFlag,filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						} 
						else if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("dept", dataFlag,"", filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("dept",dataFlag, "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("H"))
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("subdept",dataFlag, filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("subdept",dataFlag, filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("N"))
					{
						dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnStaff("",dataFlag, "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								DP.setHpc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//data method for time click location view
		@SuppressWarnings("rawtypes")
		public void getCounterDataTimeLocation(String fromDate, String toDate)
		{
			dashObj = new DashboardPojo();
			DashboardPojo DP = null;
			try
			{

				location_ticketList = new ArrayList<DashboardPojo>();
				List dept_subdeptNameList = null;
				this.generalMethod();
				dept_subdeptNameList = new HelpdeskUniversalHelper().getAllFloors(connectionSpace);

				if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
				{
					for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						DP = new DashboardPojo();
						DP.setLocId(object1[0].toString());
						DP.setLocation(object1[1].toString());
						// List for counters for Pending/ Snooze/ High Priority/
						// Ignore Status
						List dept_subdeptCounterList = new ArrayList();

						if (loginType.equalsIgnoreCase("M"))
						{
							if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("subdept", dataFlag,filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

							} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("subdept", dataFlag,filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							} 
							else if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("dept", dataFlag,"", filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							} else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("dept",dataFlag, "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						} else if (loginType.equalsIgnoreCase("H"))
						{

							if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("subdept",dataFlag, filterDeptId, "", object1[0].toString(), connectionSpace, fromDate, toDate);

							} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("subdept",dataFlag, filterDeptId, filterFlag, object1[0].toString(), connectionSpace, fromDate, toDate);
							}
						} else if (loginType.equalsIgnoreCase("N"))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllFloorTicketsDetailOnTime("",dataFlag, "", "", object1[0].toString(), connectionSpace, fromDate, toDate);
						}

						if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
						{
							int totalCount = 0;
							for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
							{
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[0].toString().equalsIgnoreCase("High Priority"))
								{
									DP.setHpc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
								{
									DP.setIgc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Pending"))
								{
									DP.setPc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
								{
									DP.setSc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
								{
									DP.setReopc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
								{
									DP.setRac(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
								{
									DP.setRc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								}
							}
							DP.setTotal(String.valueOf(totalCount));
						}
						if (Integer.parseInt(DP.getTotal()) > 0)
						{
							location_ticketList.add(DP);
							dashObj.setDashList(location_ticketList);
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	private String getTimeNameOfTime(String time)
	{
		 if (time.equalsIgnoreCase("00:00:00-01:00:00"))
		{
			return "1 AM";
		} else if (time.equalsIgnoreCase("01:00:00-02:00:00"))
		{
			return "2 AM";
		} else if (time.equalsIgnoreCase("02:00:00-03:00:00"))
		{
			return "3 AM";
		} else if (time.equalsIgnoreCase("03:00:00-04:00:00"))
		{
			return "4 AM";
		} else if (time.equalsIgnoreCase("04:00:00-05:00:00"))
		{
			return "5 AM";
		} else if (time.equalsIgnoreCase("05:00:00-06:00:00"))
		{
			return "6 AM";
		} else if (time.equalsIgnoreCase("06:00:00-07:00:00"))
		{
			return "7 AM";
		} else if (time.equalsIgnoreCase("07:00:00-08:00:00"))
		{
			return "8 AM";
		} else if (time.equalsIgnoreCase("08:00:00-09:00:00"))
		{
			return "9 AM";
		} else if (time.equalsIgnoreCase("09:00:00-10:00:00"))
		{
			return "10 AM";
		} else if (time.equalsIgnoreCase("10:00:00-11:00:00"))
		{
			return "11 AM";
		} else if (time.equalsIgnoreCase("11:00:00-12:00:00"))
		{
			return "12 AM";
		} else if (time.equalsIgnoreCase("12:00:00-13:00:00"))
		{
			return "1 PM";
		} else if (time.equalsIgnoreCase("13:00:00-14:00:00"))
		{
			return "2 PM";
		} else if (time.equalsIgnoreCase("14:00:00-15:00:00"))
		{
			return "3 PM";
		} else if (time.equalsIgnoreCase("15:00:00-16:00:00"))
		{
			return "4 PM";
		} else if (time.equalsIgnoreCase("16:00:00-17:00:00"))
		{
			return "5 PM";
		} else if (time.equalsIgnoreCase("17:00:00-18:00:00"))
		{
			return "6 PM";
		} else if (time.equalsIgnoreCase("18:00:00-19:00:00"))
		{
			return "7 PM";
		} else if (time.equalsIgnoreCase("19:00:00-20:00:00"))
		{
			return "8 PM";
		} else if (time.equalsIgnoreCase("20:00:00-21:00:00"))
		{
			return "9 PM";
		}

		else if (time.equalsIgnoreCase("21:00:00-22:00:00"))
		{
			return "10 PM";
		} else if (time.equalsIgnoreCase("22:00:00-23:00:00"))
		{
			return "11 PM";
		}
		else if (time.equalsIgnoreCase("23:00:00-24:00:00"))
			{
				return "12 PM";
			} else
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getTimeList()
	{

		List dept_subdeptNameList = new ArrayList();
		 
		dept_subdeptNameList.add("00:00:00-01:00:00");
		dept_subdeptNameList.add("01:00:00-02:00:00");
		dept_subdeptNameList.add("02:00:00-03:00:00");
		dept_subdeptNameList.add("03:00:00-04:00:00");
		dept_subdeptNameList.add("04:00:00-05:00:00");
		dept_subdeptNameList.add("05:00:00-06:00:00");
		dept_subdeptNameList.add("06:00:00-07:00:00");
		dept_subdeptNameList.add("07:00:00-08:00:00");
		dept_subdeptNameList.add("08:00:00-09:00:00");
		dept_subdeptNameList.add("09:00:00-10:00:00");
		dept_subdeptNameList.add("10:00:00-11:00:00");
		dept_subdeptNameList.add("11:00:00-12:00:00");
		dept_subdeptNameList.add("12:00:00-13:00:00");
		dept_subdeptNameList.add("13:00:00-14:00:00");
		dept_subdeptNameList.add("14:00:00-15:00:00");
		dept_subdeptNameList.add("15:00:00-16:00:00");
		dept_subdeptNameList.add("16:00:00-17:00:00");
		dept_subdeptNameList.add("17:00:00-18:00:00");
		dept_subdeptNameList.add("18:00:00-19:00:00");
		dept_subdeptNameList.add("19:00:00-20:00:00");
		dept_subdeptNameList.add("20:00:00-21:00:00");
		dept_subdeptNameList.add("21:00:00-22:00:00");
		dept_subdeptNameList.add("22:00:00-23:00:00");
		dept_subdeptNameList.add("23:00:00-24:00:00");
		return dept_subdeptNameList;
	}

	@SuppressWarnings("rawtypes")
	public void getCounterDataForTime(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = getTimeList();
			this.generalMethod();

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				int hptot=0,igtot=0,pentot=0,snootot=0,reotot=0,reatot=0,restot=0,lasttot=0;
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object object1 = (Object) iterator.next();
					DP = new DashboardPojo();
					DP.setTime(getTimeNameOfTime(object1.toString()));
					DP.setId(object1.toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{

						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("subdept", filterDeptId, "", object1.toString(), connectionSpace, fromDate, toDate);
						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("subdept", filterDeptId, filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
						} else if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("", "", filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
						}

						else
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("", "", "", object1.toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("H"))
					{
						if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("subdept", filterDeptId, "", object1.toString(), connectionSpace, fromDate, toDate);
						} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("subdept", filterDeptId, filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("N"))
					{
						dept_subdeptCounterList = new DashboardHelper().getTimeTicketsDetail("", "", "", object1.toString(), connectionSpace, fromDate, toDate);
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								DP.setHpc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								hptot=hptot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								igtot=igtot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								pentot=pentot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								snootot=snootot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reotot=reotot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reatot=reatot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								restot=restot+Integer.parseInt(object2[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}
				}
				if(data4!=null && data4.equalsIgnoreCase("table"))
				{
					//System.out.println("true");
				DP=new DashboardPojo();
				DP.setId("-1");
				DP.setTime("Total");
				DP.setIgnt(Integer.toString(igtot));
				DP.setPct(Integer.toString(pentot));
				DP.setSct(Integer.toString(snootot));
				DP.setReopct(Integer.toString(reotot));
				DP.setRact(Integer.toString(reatot));
				DP.setRct(Integer.toString(restot));
				String str=Integer.toString(igtot+pentot+snootot+reotot+reatot+restot);
				DP.setGrand(Integer.toString(lasttot));
				location_ticketList.add(DP);
				dashObj.setDashList(location_ticketList);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	//for time click staff view data
	@SuppressWarnings("rawtypes")
	public void getCounterDataTimeStaff(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_staffList = null;
			this.generalMethod();
			
				dept_staffList = new DashboardHelper().getAllStaff(filterDeptId, connectionSpace);
			

			if (dept_staffList != null && dept_staffList.size() > 0)
			{
				for (Iterator iterator = dept_staffList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setEmpId(object1[0].toString());
					DP.setEmpName(object1[1].toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{

						if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetailOnTime(object1[0].toString(),dataFlag, filterFlag, connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetailOnTime(object1[0].toString(),dataFlag, "", connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("H"))
					{

						if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetailOnTime(object1[0].toString(),dataFlag, filterFlag, connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetailOnTime(object1[0].toString(),dataFlag, "", connectionSpace, fromDate, toDate);
						}
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								DP.setHpc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//for staff click time view data
		@SuppressWarnings("rawtypes")
		public void getCounterDataStaffTime(String fromDate, String toDate)
		{
			dashObj = new DashboardPojo();
			DashboardPojo DP = null;
			try
			{

				location_ticketList = new ArrayList<DashboardPojo>();
				List dept_subdeptNameList = getTimeList();
				this.generalMethod();

				if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
				{
					for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
					{
						Object object1 = (Object) iterator.next();
						DP = new DashboardPojo();
						DP.setTime(getTimeNameOfTime(object1.toString()));
						DP.setId(object1.toString());
						// List for counters for Pending/ Snooze/ High Priority/
						// Ignore Status
						List dept_subdeptCounterList = new ArrayList();

						if (loginType.equalsIgnoreCase("M"))
						{

							if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"subdept", filterDeptId, "", object1.toString(), connectionSpace, fromDate, toDate);
							} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"subdept", filterDeptId, filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
							} else if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"", "", filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
							}

							else
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"", "", "", object1.toString(), connectionSpace, fromDate, toDate);
							}
						} else if (loginType.equalsIgnoreCase("H"))
						{
							if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && (filterFlag == null || filterFlag.equalsIgnoreCase("")))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"subdept", filterDeptId, "", object1.toString(), connectionSpace, fromDate, toDate);
							} else if (filterDeptId != null && !filterDeptId.equalsIgnoreCase("") && filterFlag != null && !filterFlag.equalsIgnoreCase(""))
							{
								dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"subdept", filterDeptId, filterFlag, object1.toString(), connectionSpace, fromDate, toDate);
							}
						} else if (loginType.equalsIgnoreCase("N"))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllTimeTicketsDetailOnStaff(dataFlag,"", "", "", object1.toString(), connectionSpace, fromDate, toDate);
						}

						if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
						{
							int totalCount = 0;
							for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
							{
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[0].toString().equalsIgnoreCase("High Priority"))
								{
									DP.setHpc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
								{
									DP.setIgc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Pending"))
								{
									DP.setPc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
								{
									DP.setSc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
								{
									DP.setReopc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
								{
									DP.setRac(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
								{
									DP.setRc(object2[1].toString());
									totalCount += Integer.parseInt(object2[1].toString());
								}
							}
							DP.setTotal(String.valueOf(totalCount));
						}
						
						if (Integer.parseInt(DP.getTotal()) > 0)
						{
							location_ticketList.add(DP);
							dashObj.setDashList(location_ticketList);
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	@SuppressWarnings("rawtypes")
	public void getCounterDataForStaff(String fromDate, String toDate)
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{

			location_ticketList = new ArrayList<DashboardPojo>();
			List dept_staffList = null;
			this.generalMethod();
			
				dept_staffList = new DashboardHelper().getAllStaff(filterDeptId, connectionSpace);
			

			if (dept_staffList != null && dept_staffList.size() > 0)
			{
				for (Iterator iterator = dept_staffList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setEmpId(object1[0].toString());
					DP.setEmpName(object1[1].toString());
					// List for counters for Pending/ Snooze/ High Priority/
					// Ignore Status
					List dept_subdeptCounterList = new ArrayList();

					if (loginType.equalsIgnoreCase("M"))
					{

						if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetail(object1[0].toString(), filterFlag, connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetail(object1[0].toString(), "", connectionSpace, fromDate, toDate);
						}
					} else if (loginType.equalsIgnoreCase("H"))
					{

						if (filterFlag != null && !filterFlag.equalsIgnoreCase(""))
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetail(object1[0].toString(), filterFlag, connectionSpace, fromDate, toDate);
						} else
						{
							dept_subdeptCounterList = new DashboardHelper().getAllStaffTicketsDetail(object1[0].toString(), "", connectionSpace, fromDate, toDate);
						}
					}

					if (dept_subdeptCounterList != null && dept_subdeptCounterList.size() > 0)
					{
						int totalCount = 0;
						for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();)
						{
							Object[] object2 = (Object[]) iterator2.next();
							if (object2[0].toString().equalsIgnoreCase("High Priority"))
							{
								DP.setHpc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
							}
						}
						DP.setTotal(String.valueOf(totalCount));
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						location_ticketList.add(DP);
						dashObj.setDashList(location_ticketList);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void getTicketDetail(String status_for, String deptid, String empName, SessionFactory connectionSpace)
	{
		try
		{
			List ticketData = new HelpdeskUniversalAction().getLodgedTickets(deptid, status_for, empName, connectionSpace);
			ticketsList = new ArrayList<FeedbackPojo>();
			if (ticketData != null && ticketData.size() > 0)
			{
				for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					FeedbackPojo fp = new FeedbackPojo();
					fp.setId(Integer.parseInt(object[0].toString()));
					fp.setTicket_no(object[1].toString());
					fp.setFeed_by(DateUtil.makeTitle(object[2].toString()) + ", " + object[7].toString());
					fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString()) + ", " + object[4].toString().substring(0, 5));
					fp.setFeedback_subcatg(object[10].toString());
					fp.setFeedback_catg(object[11].toString());
					fp.setAllot_to_mobno(object[8].toString() + ", " + object[9].toString());
					// fp.setOpen_time(object[4].toString().substring(0, 5));
					if (object[5].toString().equalsIgnoreCase("Hold"))
						fp.setStatus("Seek Approval");
					else
						fp.setStatus(object[5].toString());

					fp.setLocation(object[6].toString());
					ticketsList.add(fp);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getTicketDetailByLevel(String status_for, String fromDate, String toDate)
	{
		try
		{
			if (filterFlag == null || filterFlag.equalsIgnoreCase(""))
			{
				// this.generalMethod();
			} else
			{
				loginType = filterFlag;
				if (filterFlag != null && filterFlag.equalsIgnoreCase("H"))
				{
					hodFlag = true;
				}
			}
			
			if (status_for != null && !status_for.equals("") && status_for.equals("T"))
			{
				//System.out.println(escalated+"::::"+reopen);
				List levelList = new ArrayList();
				if(escalated!=null && !escalated.equalsIgnoreCase("Yes"))
				{
					levelList.add("Level1");
				}
				levelList.add("Level2");
				levelList.add("Level3");
				levelList.add("Level4");
				levelList.add("Level5");
				levelList.add("Level6");

				levelTicketsList = new ArrayList<DashboardPojo>();
				DashboardPojo DP = null;
				leveldashObj = new DashboardPojo();
				int totalCount = 0;
				int igtot=0,pentot=0,snootot=0,reotot=0,reatot=0,restot=0,lasttot=0;
				for (Iterator iterator = levelList.iterator(); iterator.hasNext();)
				{
					totalCount = 0;
					Object object = (Object) iterator.next();
					DP = new DashboardPojo();
					DP.setLevel(object.toString());
					List ticketData = null;
					if (filterDeptId == null || filterDeptId.equalsIgnoreCase(""))
					{
						ticketData = new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType, object.toString(), status_for, empName, "All", connectionSpace, fromDate, toDate,reopen);
					} else
					{
						ticketData = new HelpdeskUniversalAction().getLevelTickets(filterDeptId, loginType, object.toString(), status_for, empName, "All", connectionSpace, fromDate, toDate,reopen);
					}

					if (ticketData != null && ticketData.size() > 0)
					{
						for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();)
						{
							Object[] object2 = (Object[]) iterator1.next();

							if (object2[0].toString().equalsIgnoreCase("Ignore"))
							{
								DP.setIgc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								igtot=igtot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Pending"))
							{
								DP.setPc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								pentot=pentot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Snooze"))
							{
								DP.setSc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								snootot=snootot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Opened"))
							{
								DP.setReopc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reotot=reotot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Re-Assign"))
							{
								DP.setRac(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								reatot=reatot+Integer.parseInt(object2[1].toString());
							} else if (object2[0].toString().equalsIgnoreCase("Resolved"))
							{
								DP.setRc(object2[1].toString());
								totalCount += Integer.parseInt(object2[1].toString());
								restot=restot+Integer.parseInt(object2[1].toString());
							}
							DP.setHt(Integer.toString(Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc())));
					//		lasttot=lasttot+Integer.parseInt(DP.getIgc())+Integer.parseInt(DP.getPc())+Integer.parseInt(DP.getSc())+Integer.parseInt(DP.getReopc())+Integer.parseInt(DP.getRac())+Integer.parseInt(DP.getRc());
						}
						DP.setTotal(String.valueOf(totalCount));
						ticketData.clear();
					}
					
					if (Integer.parseInt(DP.getTotal()) > 0)
					{
						level=level+",'"+DP.getLevel()+"'";
						levelTicketsList.add(DP);
						leveldashObj.setDashList(levelTicketsList);
					}
				}
				if(data4!=null && data4.equalsIgnoreCase("table"))
				{
				DP=new DashboardPojo();
				DP.setId("-1");
				DP.setLevel("Total");
				DP.setIgnt(Integer.toString(igtot));
				DP.setPct(Integer.toString(pentot));
				DP.setSct(Integer.toString(snootot));
				DP.setReopct(Integer.toString(reotot));
				DP.setRact(Integer.toString(reatot));
				DP.setRct(Integer.toString(restot));
				String str=Integer.toString(igtot+pentot+snootot+reotot+reatot+restot);
			//	System.out.println(str);
				DP.setGrand(str);
				levelTicketsList.add(DP);
				leveldashObj.setDashList(levelTicketsList);
				}
			} else if (status_for != null && !status_for.equals("") && status_for.equals("G"))
			{
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void getAnalytics4SubCategory(String status_for)
	{
		try
		{
			// this.generalMethod();
			List catgData = new HelpdeskUniversalAction().getAnalyticalData("C", fromDate, toDate, dept_id, filterDeptId, filterFlag, empName, searchField, searchString, searchOper, connectionSpace);
			catgCountList = new ArrayList<FeedbackPojo>();
			if (catgData != null && catgData.size() > 0)
			{
				if (status_for.equalsIgnoreCase("T"))
				{
					for (Iterator iterator = catgData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (status_for.equalsIgnoreCase("T"))
						{
							FeedbackPojo fp = new FeedbackPojo();
							fp.setId(Integer.parseInt(object[0].toString()));
							fp.setFeedback_catg(object[1].toString());
							fp.setCounter(object[2].toString());
							catgCountList.add(fp);
						}
					}
				} else if (status_for.equalsIgnoreCase("G"))
				{
					graphCatgMap = new LinkedHashMap<String, Integer>();
					for (Iterator iterator = catgData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						graphCatgMap.put(object[1].toString(), Integer.parseInt(object[2].toString()));
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String geTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();

			try
			{
				List dataList = new HelpdeskUniversalHelper().getTicketData(ticket_id, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						FP = new FeedbackPojo();
						if (object[0] != null && !object[0].toString().equals(""))
						{
							FP.setTicket_no(object[0].toString());
						} else
						{
							FP.setTicket_no("NA");
						}
						if (object[1] != null && !object[1].toString().equals(""))
						{
							FP.setFeedback_by_dept(DateUtil.makeTitle(object[1].toString()));
						} else
						{
							FP.setFeedback_by_dept("NA");
						}
						if (object[2] != null && !object[2].toString().equals(""))
						{
							FP.setFeed_by(DateUtil.makeTitle(object[2].toString()));
						} else
						{
							FP.setFeed_by("NA");
						}
						if (object[3] != null && !object[3].toString().equals(""))
						{
							FP.setFeedback_by_mobno(object[3].toString());
						} else
						{
							FP.setFeedback_by_mobno("NA");
						}
						if (object[4] != null && !object[4].toString().equals(""))
						{
							FP.setFeedback_by_emailid(object[4].toString());
						} else
						{
							FP.setFeedback_by_emailid("NA");
						}
						if (object[5] != null && !object[5].toString().equals(""))
						{
							FP.setFeedback_to_dept(DateUtil.makeTitle(object[5].toString()));
						} else
						{
							FP.setFeedback_to_dept("NA");
						}
						if (object[6] != null && !object[6].toString().equals(""))
						{
							FP.setFeedback_allot_to(DateUtil.makeTitle(object[6].toString()));
						} else
						{
							FP.setFeedback_allot_to("NA");
						}
						if (object[7] != null && !object[7].toString().equals(""))
						{
							FP.setFeedtype(DateUtil.makeTitle(object[7].toString()));
						} else
						{
							FP.setFeedtype("NA");
						}
						if (object[8] != null && !object[8].toString().equals(""))
						{
							FP.setFeedback_catg(DateUtil.makeTitle(object[8].toString()));
						} else
						{
							FP.setFeedback_catg("NA");
						}
						if (object[9] != null && !object[9].toString().equals(""))
						{
							FP.setFeedback_subcatg(DateUtil.makeTitle(object[9].toString()));
						} else
						{
							FP.setFeedback_subcatg("NA");
						}
						if (object[10] != null && !object[10].toString().equals(""))
						{
							FP.setFeed_brief(DateUtil.makeTitle(object[10].toString()));
						} else
						{
							FP.setFeed_brief("NA");
						}
						if (object[11] != null && !object[11].toString().equals(""))
						{
							FP.setLocation(object[11].toString());
						} else
						{
							FP.setLocation("NA");
						}
						if (object[12] != null && !object[12].toString().equals(""))
						{
							FP.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));
						} else
						{
							FP.setOpen_date("NA");
						}
						if (object[13] != null && !object[13].toString().equals(""))
						{
							FP.setOpen_time(object[13].toString().substring(0, 5));
						} else
						{
							FP.setOpen_time("NA");
						}
						if (object[14] != null && !object[14].toString().equals(""))
						{
							FP.setLevel(object[14].toString());
						} else
						{
							FP.setLevel("NA");
						}
						if (object[15] != null && !object[15].toString().equals(""))
						{
							FP.setStatus(object[15].toString());
						} else
						{
							FP.setStatus("NA");
						}
						if (object[16] != null && !object[16].toString().equals(""))
						{
							FP.setVia_from(object[16].toString());
						} else
						{
							FP.setVia_from("NA");
						}
						if (object[17] != null && !object[17].toString().equals(""))
						{
							FP.setFeed_registerby(DateUtil.makeTitle(object[17].toString()));
						} else
						{
							FP.setFeed_registerby("NA");
						}
						if (object[18] != null && !object[18].toString().equals(""))
						{
							FP.setSn_reason(object[18].toString());
						} else
						{
							FP.setSn_reason("NA");
						}
						if (object[19] != null && !object[19].toString().equals(""))
						{
							FP.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
						} else
						{
							FP.setSn_on_date("NA");
						}
						if (object[20] != null && !object[20].toString().equals(""))
						{
							FP.setSn_on_time(object[20].toString().substring(0, 5));
						} else
						{
							FP.setSn_on_time("NA");
						}
						if (object[21] != null && !object[21].toString().equals(""))
						{
							FP.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
						} else
						{
							FP.setSn_date("NA");
						}
						if (object[22] != null && !object[22].toString().equals(""))
						{
							FP.setSn_time(object[22].toString().substring(0, 5));
						} else
						{
							FP.setSn_time("NA");
						}
						if (object[23] != null && !object[23].toString().equals(""))
						{
							FP.setSn_duration(object[22].toString());
						} else
						{
							FP.setSn_duration("NA");
						}
						if (object[24] != null && !object[24].toString().equals(""))
						{
							FP.setHp_date(DateUtil.convertDateToIndianFormat(object[24].toString()));
						} else
						{
							FP.setHp_date("NA");
						}
						if (object[25] != null && !object[25].toString().equals(""))
						{
							FP.setHp_time(object[25].toString().substring(0, 5));
						} else
						{
							FP.setHp_time("NA");
						}
						if (object[26] != null && !object[26].toString().equals(""))
						{
							FP.setHp_reason(object[26].toString());
						} else
						{
							FP.setHp_reason("NA");
						}

						if (object[27] != null && !object[27].toString().equals(""))
						{
							FP.setFeedback_to_subdept(DateUtil.makeTitle(object[27].toString()));
						} else
						{
							FP.setFeedback_to_subdept("NA");
						}
						if (object[28] != null && !object[28].toString().equals(""))
						{
							FP.setEscalation_date(DateUtil.convertDateToIndianFormat(object[28].toString()));
						} else
						{
							FP.setEscalation_date("NA");
						}
						if (object[29] != null && !object[29].toString().equals(""))
						{
							FP.setEscalation_time(object[29].toString().substring(0, 5));
						} else
						{
							FP.setEscalation_time("NA");
						}
						if (object[30] != null && !object[30].toString().equals(""))
						{
							FP.setFeedaddressing_time(DateUtil.convertDateToIndianFormat(object[30].toString().substring(0, 10)) + "  (" + object[30].toString().substring(11, 16) + ")");
						} else
						{
							FP.setFeedaddressing_time("NA");
						}

						feedList.add(FP);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if (status_for.equalsIgnoreCase("T"))
			{
				caption = "Ticket Detail";
				returnResult = "ticket_success";
			} else if (status_for.equalsIgnoreCase("FB"))
			{
				caption = "Lodged By Detail";
				returnResult = "feedby_success";
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getDashboard()
	{
		return dashboard;
	}

	public void setDashboard(String dashboard)
	{
		this.dashboard = dashboard;
	}

	@SuppressWarnings("rawtypes")
	public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				/*
				 * String userName=(String)session.get("uName"); String
				 * deptLevel = (String)session.get("userDeptLevel");
				 * SessionFactory
				 * connectionSpace=(SessionFactory)session.get("connectionSpace"
				 * );
				 */
				feedbackList = new ArrayList<FeedbackPojo>();

				// int count = 0 ;
				List data = null;

				if (dashFor != null && !dashFor.equals("") && dashFor.equalsIgnoreCase("M"))
				{

				} else if (dashFor != null && !dashFor.equals("") && dashFor.equalsIgnoreCase("H"))
				{

				} else if (dashFor != null && !dashFor.equals("") && dashFor.equalsIgnoreCase("N"))
				{

				}

				String dept_id = "", empname = "";
				List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "2");
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						empname = object[0].toString();
						dept_id = object[3].toString();
					}
				}

				if (dataFlag != null && !dataFlag.equals("") && dataFlag.equals("L"))
				{
					d_subdept_id = dept_id;
				}
				//System.out.println(dashFor);
				data = new DashboardHelper().getDataForDashboard(feedStatus, fromDate, toDate, dashFor, d_subdept_id, dataFlag, "HDM", empname, level, searchField, searchString, searchOper, connectionSpace,"","table","");

				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					if (deptLevel.equals("2"))
					{
						feedbackList = new HelpdeskUniversalHelper().setFeedbackValues(data, deptLevel, getFeedStatus());
						dataCheck = "Confirm";
					}

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

	@SuppressWarnings("rawtypes")
	public String jsonChartData4TrendAnalysis()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{

				jsonArray = new JSONArray();
				jsonObject = new JSONObject();

				if((dept_id==null || "".equalsIgnoreCase(dept_id)) && filterFlag.equalsIgnoreCase("H"))
				{
					this.generalMethod();
				}
				List list = new DashboardHelper().getCounterDataList(dept_id, fromDate, toDate,filterFlag, connectionSpace);
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
				{
					Object[] object1 = (Object[]) iterator2.next();
					jsonObject.put("Total", object1[0].toString());
					jsonObject.put("Date", object1[1].toString());
					jsonArray.add(jsonObject);
					jsonObject.clear();
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

	public String getFeedFor()
	{
		return feedFor;
	}

	public void setFeedFor(String feedFor)
	{
		this.feedFor = feedFor;
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

	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getL_pending()
	{
		return l_pending;
	}

	public void setL_pending(String l_pending)
	{
		this.l_pending = l_pending;
	}

	public String getL_snooze()
	{
		return l_snooze;
	}

	public void setL_snooze(String l_snooze)
	{
		this.l_snooze = l_snooze;
	}

	public String getL_hp()
	{
		return l_hp;
	}

	public void setL_hp(String l_hp)
	{
		this.l_hp = l_hp;
	}

	public String getL_ignore()
	{
		return l_ignore;
	}

	public void setL_ignore(String l_ignore)
	{
		this.l_ignore = l_ignore;
	}

	public String getL_resolved()
	{
		return l_resolved;
	}

	public void setL_resolved(String l_resolved)
	{
		this.l_resolved = l_resolved;
	}

	public String getR_pending()
	{
		return r_pending;
	}

	public void setR_pending(String r_pending)
	{
		this.r_pending = r_pending;
	}

	public String getR_snooze()
	{
		return r_snooze;
	}

	public void setR_snooze(String r_snooze)
	{
		this.r_snooze = r_snooze;
	}

	public String getR_hp()
	{
		return r_hp;
	}

	public void setR_hp(String r_hp)
	{
		this.r_hp = r_hp;
	}

	public String getR_ignore()
	{
		return r_ignore;
	}

	public void setR_ignore(String r_ignore)
	{
		this.r_ignore = r_ignore;
	}

	public String getR_resolved()
	{
		return r_resolved;
	}

	public void setR_resolved(String r_resolved)
	{
		this.r_resolved = r_resolved;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
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

	public String getDeptHierarchy()
	{
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy)
	{
		this.deptHierarchy = deptHierarchy;
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

	public List<DashboardPojo> getSubdeptdashList()
	{
		return subdeptdashList;
	}

	public void setSubdeptdashList(List<DashboardPojo> subdeptdashList)
	{
		this.subdeptdashList = subdeptdashList;
	}

	public DashboardPojo getDeptdashObj()
	{
		return deptdashObj;
	}

	public void setDeptdashObj(DashboardPojo deptdashObj)
	{
		this.deptdashObj = deptdashObj;
	}

	public List<DashboardPojo> getDeptdashList()
	{
		return deptdashList;
	}

	public void setDeptdashList(List<DashboardPojo> deptdashList)
	{
		this.deptdashList = deptdashList;
	}

	/*
	 * public Map<String, Integer> getGraphEmpMap() { return graphEmpMap; }
	 * public void setGraphEmpMap(Map<String, Integer> graphEmpMap) {
	 * this.graphEmpMap = graphEmpMap; }
	 */

	public Map<String, Integer> getGraphCatgMap()
	{
		return graphCatgMap;
	}

	public void setGraphCatgMap(Map<String, Integer> graphCatgMap)
	{
		this.graphCatgMap = graphCatgMap;
	}

	public Map<Integer, Integer> getDoubleMap()
	{
		return doubleMap;
	}

	public void setDoubleMap(Map<Integer, Integer> doubleMap)
	{
		this.doubleMap = doubleMap;
	}

	public List<FeedbackPojo> getTicketsList()
	{
		return ticketsList;
	}

	public void setTicketsList(List<FeedbackPojo> ticketsList)
	{
		this.ticketsList = ticketsList;
	}

	public String getTicket_id()
	{
		return ticket_id;
	}

	public void setTicket_id(String ticket_id)
	{
		this.ticket_id = ticket_id;
	}

	public String getStatus_for()
	{
		return status_for;
	}

	public void setStatus_for(String status_for)
	{
		this.status_for = status_for;
	}

	public FeedbackPojo getFP()
	{
		return FP;
	}

	public void setFP(FeedbackPojo fp)
	{
		FP = fp;
	}

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public List<FeedbackPojo> getMgmtTicketsList()
	{
		return mgmtTicketsList;
	}

	public void setMgmtTicketsList(List<FeedbackPojo> mgmtTicketsList)
	{
		this.mgmtTicketsList = mgmtTicketsList;
	}

	public DashboardPojo getLeveldashObj()
	{
		return leveldashObj;
	}

	public void setLeveldashObj(DashboardPojo leveldashObj)
	{
		this.leveldashObj = leveldashObj;
	}

	public List<DashboardPojo> getLevelTicketsList()
	{
		return levelTicketsList;
	}

	public void setLevelTicketsList(List<DashboardPojo> levelTicketsList)
	{
		this.levelTicketsList = levelTicketsList;
	}

	public List<FeedbackPojo> getEmpCountList()
	{
		return empCountList;
	}

	public void setEmpCountList(List<FeedbackPojo> empCountList)
	{
		this.empCountList = empCountList;
	}

	public List<FeedbackPojo> getCatgCountList()
	{
		return catgCountList;
	}

	public void setCatgCountList(List<FeedbackPojo> catgCountList)
	{
		this.catgCountList = catgCountList;
	}

	public String getHeaderValue()
	{
		return headerValue;
	}

	public void setHeaderValue(String headerValue)
	{
		this.headerValue = headerValue;
	}

	public String getMaximizeDivBlock()
	{
		return maximizeDivBlock;
	}

	public void setMaximizeDivBlock(String maximizeDivBlock)
	{
		this.maximizeDivBlock = maximizeDivBlock;
	}

	public Map<String, Integer> getGraphLevelMap()
	{
		return graphLevelMap;
	}

	public void setGraphLevelMap(Map<String, Integer> graphLevelMap)
	{
		this.graphLevelMap = graphLevelMap;
	}

	public String getLoginType()
	{
		return loginType;
	}

	public void setLoginType(String loginType)
	{
		this.loginType = loginType;
	}

	public boolean isHodFlag()
	{
		return hodFlag;
	}

	public void setHodFlag(boolean hodFlag)
	{
		this.hodFlag = hodFlag;
	}

	public boolean isMgmtFlag()
	{
		return mgmtFlag;
	}

	public void setMgmtFlag(boolean mgmtFlag)
	{
		this.mgmtFlag = mgmtFlag;
	}

	public boolean isNormalFlag()
	{
		return normalFlag;
	}

	public void setNormalFlag(boolean normalFlag)
	{
		this.normalFlag = normalFlag;
	}

	public String getDataCheck()
	{
		return dataCheck;
	}

	public void setDataCheck(String dataCheck)
	{
		this.dataCheck = dataCheck;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
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

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
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

	public List<DashboardPojo> getLocation_ticketList()
	{
		return location_ticketList;
	}

	public void setLocation_ticketList(List<DashboardPojo> location_ticketList)
	{
		this.location_ticketList = location_ticketList;
	}

	public String getXaxis()
	{
		return xaxis;
	}

	public void setXaxis(String xaxis)
	{
		this.xaxis = xaxis;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(String dept_id)
	{
		this.dept_id = dept_id;
	}

	public String getEscalated() {
		return escalated;
	}

	public void setEscalated(String escalated) {
		this.escalated = escalated;
	}

	public String getReopen() {
		return reopen;
	}

	public void setReopen(String reopen) {
		this.reopen = reopen;
	}

	public CategoryAnalisysPojo getCategPojo() {
		return categPojo;
	}

	public void setCategPojo(CategoryAnalisysPojo categPojo) {
		this.categPojo = categPojo;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData4() {
		return data4;
	}

	

}