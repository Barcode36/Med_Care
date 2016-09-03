package com.Over2Cloud.modal.dao.imp.feedbackDashboard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.BeanUtil.CounterPojo;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.BeanUtil.FeedDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojoDashboard;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.pojo.DeptActionPojo;
import com.Over2Cloud.ctrl.feedback.pojo.FeedbackAnalysis;
import com.Over2Cloud.ctrl.feedback.pojo.TicketAllotmentPojo;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackDashboardDao
{
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();

	public List<DeptActionPojo> getDepartmentDetails(SessionFactory connectionSpace, String fromDate, String toDate)
	{
		List<DeptActionPojo> pojoList = new ArrayList<DeptActionPojo>();
		StringBuilder builder = null;
		builder = new StringBuilder("SELECT  fs.to_dept_subdept,dept.deptName FROM feedback_status AS fs INNER JOIN department AS dept ON dept.id=fs.to_dept_subdept WHERE fs.moduleName='FM' GROUP BY fs.to_dept_subdept ORDER BY dept.deptName ASC");

		List data = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{

			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null && object[1] != null)
					{
						DeptActionPojo deptPojo = new DeptActionPojo();
						deptPojo.setDeptId(object[0].toString());
						deptPojo.setDeptName(object[1].toString());

						builder.delete(0, builder.length());

						builder.append(" select count(*) from feedback_status where to_dept_subdept='" + deptPojo.getDeptId() + "' and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

						deptPojo.setTotalIsues(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())));

						builder.delete(0, builder.length());

						builder.append("select count(*) from feedback_status where to_dept_subdept='" + deptPojo.getDeptId() + "' and action_by IS NOT NULL and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

						deptPojo.setTotalAction(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())));

						builder.delete(0, builder.length());

						builder.append("select count(*) from feedback_status where to_dept_subdept='" + deptPojo.getDeptId() + "' and resolve_remark IS NOT NULL and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

						deptPojo.setTotalCapa(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())));

						pojoList.add(deptPojo);

					}
				}
			}

		}

		return pojoList;

	}

	@SuppressWarnings("rawtypes")
	public int getSingleCounterFromQuerry(SessionFactory connection, String query)
	{
		int count = 0;
		List dataList = new createTable().executeAllSelectQuery(query, connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					count = Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}

	// to be merge 31-10-2014
	public List<FeedbackAnalysis> getAnalysisData(SessionFactory connectionSpace, String type, String fromDate, String toDate)
	{
		List<FeedbackAnalysis> analysisList = new ArrayList<FeedbackAnalysis>();
		StringBuilder builder = null;
		if (type == null)
		{
			builder = new StringBuilder("select count(*),feedback.status from feedback_status as feedback where feedback.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and feedback.moduleName='FM'  group by feedback.status");
			// System.out.println("Count Wali>>" + builder);
			List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						FeedbackAnalysis pojo = new FeedbackAnalysis();
						if (object[0] != null && object[1] != null)
						{
							pojo.setHeadingName(object[1].toString());
							pojo.setValue(object[0].toString());
							analysisList.add(pojo);
						}
					}
				}
			}
		} else if (type != null && type.equalsIgnoreCase("Appreciation") || type.equalsIgnoreCase("Suggestion"))
		{
			analysisList.clear();
			builder = new StringBuilder("select count(*) from feedback_status as feedback ");
			builder.append(" inner join feedback_subcategory as subCat on feedback.subCatg=subCat.id ");
			builder.append(" inner join feedback_category as cat on subCat.categoryName=cat.id ");
			builder.append(" inner join feedback_type as typ on cat.fbType=typ.id ");
			builder.append(" where feedback.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and typ.moduleName='FM' and typ.fbType='" + type + "'");
			//System.out.println("Second Wali>>" + builder);
			List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				if (dataList.get(0) != null)
				{
					FeedbackAnalysis pojo = new FeedbackAnalysis();
					pojo.setHeadingName(type);
					pojo.setValue(dataList.get(0).toString());
					analysisList.add(pojo);
				}
			}
		}
		return analysisList;
	}

	public String getPaperRefNo(SessionFactory connectionSpace)
	{
		String paperRefNo = "NA";
		List dataList = cbt.executeAllSelectQuery("select count(*) from feedbackdata where mode='Paper'", connectionSpace);
		if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
		{
			int paperRef = Integer.parseInt(dataList.get(0).toString());
			if (paperRef > 0)
			{
				paperRefNo = String.valueOf(paperRef + 1);
			} else
			{
				paperRefNo = String.valueOf(1);
			}
		} else
		{
			paperRefNo = String.valueOf(1);
		}
		return paperRefNo;
	}

	public List<TicketAllotmentPojo> getDeptWiseTicketAllotmentDetails(String deptId, SessionFactory connectionSpace)
	{
		List<TicketAllotmentPojo> pojoList = new ArrayList<TicketAllotmentPojo>();
		List<EmpBasicPojoBean> empIds = new ArrayList<EmpBasicPojoBean>();
		List empIdList = null;
		if (deptId != null)
		{
			// select contact.emp_Id,emp.empName from compliance_contacts as
			// contact inner join employee_basic as emp on emp.id=contact.emp_Id
			// where contact.forDept_id='6' and contact.work_status='3'
			empIdList = cbt.executeAllSelectQuery("select contact.emp_Id,emp.empName from compliance_contacts as contact inner join employee_basic as emp on emp.id=contact.emp_Id where contact.forDept_id='" + deptId + "' and contact.work_status='3' order by contact.level", connectionSpace);
		} else
		{
			empIdList = cbt.executeAllSelectQuery("select contact.emp_Id,emp.empName from compliance_contacts as contact inner join employee_basic as emp on emp.id=contact.emp_Id where contact.work_status='3' order by contact.level", connectionSpace);
		}
		if (empIdList != null && empIdList.size() > 0)
		{
			for (Iterator iterator = empIdList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					EmpBasicPojoBean pojo = new EmpBasicPojoBean();
					pojo.setEmpId(object[0].toString());
					pojo.setEmpName(object[1].toString());
					empIds.add(pojo);
				}
			}
		}

		if (empIds != null && empIds.size() > 0)
		{
			for (EmpBasicPojoBean emp : empIds)
			{
				TicketAllotmentPojo pojo = new TicketAllotmentPojo();
				pojo.setEmpName(emp.getEmpName());
				pojo.setTotalPending(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "Pending", emp.getEmpId())));
				pojo.setHighPriority(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "High Priority", emp.getEmpId())));
				pojo.setIgnore(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "Ignore", emp.getEmpId())));
				pojo.setReAsign(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "Re-Assign", emp.getEmpId())));
				pojo.setSnooze(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "Snooze", emp.getEmpId())));
				pojo.setResolved(String.valueOf(getCountersFromTableAllotment(connectionSpace, deptId, "Resolved", emp.getEmpId())));
				pojo.setTotalTickets(String.valueOf(Integer.parseInt(pojo.getTotalPending()) + Integer.parseInt(pojo.getHighPriority()) + Integer.parseInt(pojo.getIgnore()) + Integer.parseInt(pojo.getReAsign()) + Integer.parseInt(pojo.getReAsign()) + Integer.parseInt(pojo.getResolved())));

				pojoList.add(pojo);
			}
		}
		return pojoList;
	}

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	@SuppressWarnings("rawtypes")
	public List<FeedbackPojoDashboard> getAllServiceDept(SessionFactory connectionSpace, boolean deptFlag)
	{
		List<FeedbackPojoDashboard> deptNames = new ArrayList<FeedbackPojoDashboard>();
		String query = null;
		if (deptFlag)
		{
			query = "SELECT  fs.to_dept_subdept,dept.deptName FROM feedback_status AS fs INNER JOIN department AS dept ON dept.id=fs.to_dept_subdept WHERE fs.moduleName='FM' GROUP BY fs.to_dept_subdept ORDER BY dept.deptName ASC";
		} else
		{
			query = "SELECT  conf.deptName , conf.prefix FROM dept_ticket_series_conf AS conf INNER JOIN feedback_status AS fs On fs.to_dept_subdept=conf.deptName WHERE fs.moduleName='FM' GROUP BY fs.to_dept_subdept ORDER BY conf.prefix";
		}
		List data = cbt.executeAllSelectQuery(query, connectionSpace);
		if (data != null & data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					FeedbackPojoDashboard pojo = new FeedbackPojoDashboard();
					if (object[0] != null)
					{
						pojo.setDeptId(object[0].toString());
					}
					if (object[1] != null)
					{
						pojo.setDeptName(object[1].toString());
					}
					deptNames.add(pojo);
				}
			}
		}
		return deptNames;
	}

	@SuppressWarnings("rawtypes")
	public int getCountersFromTableAllotment(SessionFactory connection, String deptid, String status, String allotTo)
	{
		int counter = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from feedback_status where to_dept_subdept = " + deptid + " && status='" + status + "' && allot_to='" + allotTo + "' and moduleName='FM'");
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					counter = Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	}

	// change by kamlesh 29-10-2014
	@SuppressWarnings("rawtypes")
	public int getCountersFromTable(SessionFactory connection, String deptid, String status, String fromDate, String toDate)
	{
		int counter = 0;
		StringBuffer buffer = new StringBuffer();
		if(deptid.equalsIgnoreCase("109"))
		{
			buffer.append(" select count(*) from feedback_status_feed_paperRating where (feed_brief  like '%Poor' or feed_brief  like '%Average') and by_dept_subdept = " + deptid + " and status='" + status + "' and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
			List list= new createTable().executeAllSelectQuery(buffer.toString(), connection);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object != null)
					{
						counter = Integer.parseInt(object.toString());
					}
				}
			}
			buffer.delete(0, buffer.length());
		}
		buffer.append("select count(*) from feedback_status where to_dept_subdept = " + deptid + " && status='" + status + "' and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					counter+= Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	}

	@SuppressWarnings("rawtypes")
	public int getSingleCounter(SessionFactory connection, String tableName, String searchFiled, String searchString, String deptId)
	{
		int count = 0;
		StringBuffer buffer = new StringBuffer("select count(*) from " + tableName + " where  to_dept_subdept = '" + deptId + "' && " + searchFiled + "='" + searchString + "' AND status IN('Pending','Snooze','High Priority') and moduleName='FM'");
		// System.out.println("LEVEL  QUERy :::   "+buffer.toString());
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					count = Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}

	// change by kamlesh 29-10-2014
	public List<FeedbackPojoDashboard> getDashboardTicketsDataShow(SessionFactory connectionSpace, String deptId, boolean flag, boolean deptFlag, String fromDate, String toDate)
	{
		List<FeedbackPojoDashboard> list = new ArrayList<FeedbackPojoDashboard>();
		List<FeedbackPojoDashboard> deptList = null;
		int count=0;
		if (deptFlag)
		{
			deptList = getAllServiceDept(connectionSpace, deptFlag);
		} else
		{
			deptList = getAllServiceDept(connectionSpace, deptFlag);
		}
		// getAllServiceDept(connectionSpace,false);
		for (FeedbackPojoDashboard pojo : deptList)
		{

			// setting status
			// change by kamlesh 29-10-2014
			pojo.setPending(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Pending", fromDate, toDate)));
			pojo.setSz(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Snooze", fromDate, toDate)));
			pojo.setHp(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "High Priority", fromDate, toDate)));
			pojo.setIg(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Ignore", fromDate, toDate)));
			pojo.setRAs(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Re-Assign", fromDate, toDate)));
			pojo.setResolved(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Resolved", fromDate, toDate)));
			pojo.setNoted(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Noted", fromDate, toDate)));
			pojo.setTicketOpened(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "Ticket Opened", fromDate, toDate)));
			pojo.setNfa(String.valueOf(getCountersFromTable(connectionSpace, pojo.getDeptId(), "No Further Action Required", fromDate, toDate)));
			// change end
			// setting level counter
			// change by kamlesh 29-10-2014
			if (flag)
			{
				pojo.setPendingL1(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "level", "Level1", pojo.getDeptId(), fromDate, toDate)));
				pojo.setPendingL2(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "level", "Level2", pojo.getDeptId(), fromDate, toDate)));
				pojo.setPendingL3(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "level", "Level3", pojo.getDeptId(), fromDate, toDate)));
				pojo.setPendingL4(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "level", "Level4", pojo.getDeptId(), fromDate, toDate)));
				pojo.setPendingL5(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "level", "Level5", pojo.getDeptId(), fromDate, toDate)));
			}
			if(pojo.getDeptId().equalsIgnoreCase("109"))
			{
				count=getSingleCounter(connectionSpace, "feedback_status_feed_paperRating", "feed_brief", "", pojo.getDeptId(), fromDate, toDate);
			}
			//pojo.setTotalCounter(String.valueOf(Integer.parseInt(pojo.getPending())+Integer.parseInt(pojo.getSz())+Integer.parseInt(pojo.getHp())+Integer.parseInt(pojo.getIg())+Integer.parseInt(pojo.getRAs())+Integer.parseInt(pojo.getResolved())+Integer.parseInt(pojo.getNoted()+Integer.parseInt(pojo.getTicketOpened())+Integer.parseInt(pojo.getNfa()))));
			pojo.setTotalCounter(String.valueOf(getSingleCounterLevel(connectionSpace, "feedback_status", "", "", pojo.getDeptId(), fromDate, toDate)+count));
			count=0;
			StringBuilder builder = new StringBuilder();
			/*builder.append(" select count(*) from feedback_status where to_dept_subdept='" + pojo.getDeptId() + "' and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

			pojo.setTotalIsues(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())));*/

			builder.delete(0, builder.length());
			//Action taken for PCC
			if(pojo.getDeptId().equalsIgnoreCase("109"))
			{
				builder.append("select count(*) from feedback_status_feed_paperRating where by_dept_subdept='" + pojo.getDeptId() + "' and status NOT IN ('Pending') and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
				count=getSingleCounterFromQuerry(connectionSpace, builder.toString());
				builder.delete(0, builder.length());
			}
			//Action taken
			builder.append("select count(*) from feedback_status where to_dept_subdept='" + pojo.getDeptId() + "' and status NOT IN ('Pending') and stage='2' and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

			pojo.setTotalAction(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())+count));

			builder.delete(0, builder.length());
			
			//Capa
			builder.append("select count(*) from feedback_status where to_dept_subdept='" + pojo.getDeptId() + "' and resolve_remark IS NOT NULL and moduleName='FM' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");

			pojo.setTotalCapa(String.valueOf(getSingleCounterFromQuerry(connectionSpace, builder.toString())));
			list.add(pojo);
			count=0;
		}
		list = deptList;
		return list;
	}

	// changed 31-10-2014
	@SuppressWarnings("rawtypes")
	public int getSingleCounterLevel(SessionFactory connection, String tableName, String searchFiled, String searchString, String deptId, String fromDate, String toDate)
	{
		int count = 0;
		StringBuffer buffer = new StringBuffer("select count(*) from " + tableName + " where   moduleName='FM' and to_dept_subdept = '" + deptId + "' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		if (searchFiled != null && !searchFiled.equalsIgnoreCase("") && searchString != null && !searchString.equalsIgnoreCase(""))
		{
			buffer.append(" and " + searchFiled + "='" + searchString + "'");
		}
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					count = Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}
	
	public int getSingleCounter(SessionFactory connection, String tableName, String searchFiled, String searchString, String deptId, String fromDate, String toDate)
	{
		int count = 0;
		StringBuffer buffer = new StringBuffer("select count(*) from " + tableName + " where   moduleName='FM' and by_dept_subdept = '" + deptId + "' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		if (searchFiled != null && !searchFiled.equalsIgnoreCase(""))
		{
			buffer.append(" and (" + searchFiled + " LIKE '%Poor' or "+searchFiled+" LIKE '%Average')");
		}
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					count = Integer.parseInt(object.toString());
				}
			}
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	public String getLoggedInEmpInfo(SessionFactory connectionSpace, String uName, String searchField)
	{
		String subDept = null;
		try
		{
			StringBuffer buffer = new StringBuffer("select " + searchField + " from employee_basic" + " inner join useraccount on employee_basic.useraccountid=useraccount.id " + "where useraccount.userID='" + Cryptography.encrypt(uName, DES_ENCRYPTION_KEY) + "'");

			List data = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object != null)
					{
						subDept = object.toString();
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (subDept == null)
		{
			subDept = "0";
		}
		return subDept;
	}

	public String getFeedStatusIdForFeedback(String feedDataId, SessionFactory connectionSpace)
	{
		String feedStatId = "";
		List data = new createTable().executeAllSelectQuery("select feed_stat_id from feedback_ticket where feed_data_id='" + feedDataId + "'", connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					feedStatId = object.toString();
				}
			}
		}
		return feedStatId;
	}

	public String getTableColumn(String colName, String tableName, String searchFiled, String searchString, SessionFactory connectionSpace)
	{
		String colVal = "";
		List data = new createTable().executeAllSelectQuery("select " + colName + " from " + tableName + " where " + searchFiled + "='" + searchString + "'", connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					colVal = object.toString();
				}
			}
		}

		return colVal;
	}

	/*@SuppressWarnings({ "rawtypes", "unused" })
	public List<NewsAlertsPojo> getDashboardAlertsList(String alertType, SessionFactory connectionSpace)
	{
		List<NewsAlertsPojo> temp = new ArrayList<NewsAlertsPojo>();

		StringBuffer buffer = new StringBuffer("select newsName,newsDesc,severity from newsandalerts ORDER BY severity asc");
		boolean flag = false;
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
						NewsAlertsPojo map = new NewsAlertsPojo();
						map.setName(object[0].toString());
						map.setDesc(object[1].toString());
						map.setSeverity(object[2].toString());
						temp.add(map);
					}
				}
			}
		}
		return temp;
	}*/
	@SuppressWarnings({ "rawtypes", "unused" })
	public List<NewsAlertsPojo> getDashboardAlertsList(String alertType, SessionFactory connectionSpace,String empDept)
	{
		List<NewsAlertsPojo> temp = new ArrayList<NewsAlertsPojo>();
		String today=DateUtil.getCurrentDateUSFormat();
		StringBuffer buffer = new StringBuffer("select newsName,newsDesc,severity,module_name from newsandalerts where ByDept=" +empDept+ " and '"+today+"' between startDate and endDate   ORDER BY severity asc ");
	 	boolean flag = false;
	 	System.out.println("N&A"+today);
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
						NewsAlertsPojo map = new NewsAlertsPojo();
						map.setName(object[0].toString());
						map.setDesc(object[1].toString());
						map.setSeverity(object[2].toString());
						map.setModule_name(object[3].toString());
						temp.add(map);
					}
				}
			}
		}
		return temp;
	}
	public Map<String, Integer> getFeedStatMap(SessionFactory connectionSpace)
	{
		Map<String, Integer> pieYesNoMap = new HashMap<String, Integer>();
		List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedback_status", "status", " select ");
		int counter = 0;
		for (String s : whrClausParam)
		{
			counter = new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedback_status", "status", s);
			pieYesNoMap.put(s, counter);
		}
		return pieYesNoMap;
	}

	public Map<String, Integer> getFeedStatMapDeptWise(SessionFactory connectionSpace, List<Integer> toDept)
	{
		Map<String, Integer> pieYesNoMap = new HashMap<String, Integer>();
		List<String> whrClausParam = getDeptFeedStatus(connectionSpace, toDept);
		int counter = 0;
		for (String s : whrClausParam)
		{
			counter = getGraphDataCounterDeptWise(connectionSpace, s, toDept);
			pieYesNoMap.put(s, counter);
		}
		return pieYesNoMap;
	}

	@SuppressWarnings("rawtypes")
	private int getGraphDataCounterDeptWise(SessionFactory connectionSpace, String status, List<Integer> toDept)
	{
		int count = 0;
		try
		{
			List data = null;
			Session hSession = null;

			try
			{
				hSession = connectionSpace.openSession();
				String qru = "select count(*) from feedback_status where status='Pending' && to_dept_subdept in " + toDept.toString().replace("[", "(").replace("]", ")");
				data = hSession.createSQLQuery(qru).list();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				hSession.flush();
				hSession.close();
			}

			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object != null)
					{
						count = Integer.parseInt(object.toString());
					}
				}
			}
			return count;

		} catch (Exception e)
		{
			count = 0;
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	private List<String> getDeptFeedStatus(SessionFactory connectionSpace, List<Integer> toDept)
	{
		List<String> status = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer("select distinct status from feedback_status where to_dept_subdept in " + toDept.toString().replace("[", "(").replace("]", ")") + "");
		List data = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					status.add(object.toString());
				}
			}
		}
		return status;
	}

	public List<CounterPojo> getDeptFeedCounters(SessionFactory connectionSpace)
	{
		List<CounterPojo> count = new ArrayList<CounterPojo>();
		List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedback_status", "status", " select ");
		for (String s : whrClausParam)
		{
			CounterPojo cp = new CounterPojo();
			cp.setCount(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedback_status_feedback", "status", s));
			cp.setName(s);

			count.add(cp);
		}

		return count;
	}

	public Map<String, Integer> getDeptRemarksMap(SessionFactory connectionSpace, String subDeptId)
	{
		Map<String, Integer> pieYesNoMap = new HashMap<String, Integer>();
		try
		{
			String subDeptName = new ReportsConfigurationDao().getSingleColumnValue("subdeptname", "subdepartment", subDeptId, "id", connectionSpace);
			String deptFeed = new ReportsConfigurationDao().getSingleColumnValue("field_value", "feedback_data_configuration", subDeptName, "field_name", connectionSpace);
			List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedbackdata", deptFeed, " select ");
			int counter = 0;
			for (String s : whrClausParam)
			{
				counter = new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", deptFeed, s);
				pieYesNoMap.put(s, counter);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return pieYesNoMap;
	}

	public List<CounterPojo> getDeptRemarksData(SessionFactory connectionSpace, String subDeptId)
	{
		List<CounterPojo> counterList = new ArrayList<CounterPojo>();
		try
		{
			String subDeptName = new ReportsConfigurationDao().getSingleColumnValue("subdeptname", "subdepartment", subDeptId, "id", connectionSpace);
			String deptFeed = new ReportsConfigurationDao().getSingleColumnValue("field_value", "feedback_data_configuration", subDeptName, "field_name", connectionSpace);
			List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedbackdata", deptFeed, " select ");
			int counter = 0;
			for (String s : whrClausParam)
			{
				counter = new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", deptFeed, s);
				CounterPojo cPojo = new CounterPojo();
				cPojo.setName(s);
				cPojo.setCount(counter);
				counterList.add(cPojo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return counterList;
	}

	public Map<String, Integer> getYesNoMap(SessionFactory connectionSpace)
	{
		Map<String, Integer> pieYesNoMap = new HashMap<String, Integer>();
		List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedbackdata", "targetOn", " select ");
		int counter = 0;
		for (String s : whrClausParam)
		{
			counter = new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", "targetOn", s);
			pieYesNoMap.put(s, counter);
		}
		return pieYesNoMap;
	}

	public List<CounterPojo> getPositiveNegativeCounters(SessionFactory connectionSpace)
	{
		List<CounterPojo> count = new ArrayList<CounterPojo>();
		List<String> whrClausParam = new EscalationActionControlDao().getGraphData(connectionSpace, "feedbackdata", "targetOn", " select ");
		for (String s : whrClausParam)
		{
			CounterPojo cp = new CounterPojo();
			cp.setCount(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", "targetOn", s));
			cp.setName(s);

			count.add(cp);
		}

		return count;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackTicketPojo> getFeedTicketDashboard(SessionFactory connectionSpace)
	{
		List<FeedbackTicketPojo> feedTicketList = new ArrayList<FeedbackTicketPojo>();
		StringBuffer qry = new StringBuffer("select ticket_no, feed_by,feed_by_mobno,open_date,id from feedback_status where status IN ('Pending','Snooze','High Priority') && moduleName='FM' order by id desc");
		List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					FeedbackTicketPojo feedTicket = new FeedbackTicketPojo();
					if (object[0] != null)
					{
						feedTicket.setTicketNo(object[0].toString());
					}

					if (object[1] != null)
					{
						feedTicket.setFeedBy(object[1].toString());
					}

					if (object[2] != null)
					{
						feedTicket.setFeedByMob(object[2].toString());
					}

					if (object[3] != null)
					{
						feedTicket.setFeedDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
					}
					if (object[4] != null)
					{
						feedTicket.setId(Integer.parseInt(object[4].toString()));
					}
					feedTicketList.add(feedTicket);
				}
			}
		}

		return feedTicketList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackTicketPojo> getFeedTicketDashboardDeptWise(SessionFactory connectionSpace, List<Integer> toDept)
	{
		List<FeedbackTicketPojo> feedTicketList = new ArrayList<FeedbackTicketPojo>();
		// order by id desc
		StringBuffer qry = new StringBuffer("select ticket_no, feed_by,feed_by_mobno,open_date from feedback_status where status not in ('Resolved') && to_dept_subdept in " + toDept.toString().replace("[", "(").replace("]", ")") + " order by id desc");
		List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					FeedbackTicketPojo feedTicket = new FeedbackTicketPojo();
					if (object[0] != null)
					{
						feedTicket.setTicketNo(object[0].toString());
					}

					if (object[1] != null)
					{
						feedTicket.setFeedBy(object[1].toString());
					}

					if (object[2] != null)
					{
						feedTicket.setFeedByMob(object[2].toString());
					}

					if (object[3] != null)
					{
						feedTicket.setFeedDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
					}
					feedTicketList.add(feedTicket);
				}
			}
		}

		return feedTicketList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedDataPojo> getFeedbackDataDashboard(SessionFactory connectionSpace)
	{
		List<FeedDataPojo> feedList = new ArrayList<FeedDataPojo>();
		// Feedback Details

		StringBuffer qry = new StringBuffer("select name,mobileNo,docterName from feedbackdata order by id desc limit 10");
		List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
			{
				Object[] object2 = (Object[]) iterator2.next();
				if (object2 != null)
				{
					FeedDataPojo feed = new FeedDataPojo();

					if (object2[0] != null)
					{
						feed.setName(object2[0].toString());
					}

					if (object2[1] != null)
					{
						feed.setMobNo(object2[1].toString());
					}

					if (object2[2] != null)
					{
						feed.setDoctName(object2[2].toString());
					}

					feedList.add(feed);
				}
			}
		}
		return feedList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackDataPojo> getFeedbackCounters(SessionFactory connectionSpace)
	{
		List<FeedbackDataPojo> feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
		StringBuffer bufr = new StringBuffer("select distinct status from feedbackdata WHERE status!='openTicket' group by status  ORDER BY status DESC");
		List data = new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);

		if (data != null && data.size() > 0)
		{
			for (Iterator iterator1 = data.iterator(); iterator1.hasNext();)
			{
				FeedbackDataPojo fP = new FeedbackDataPojo();
				Object object1 = (Object) iterator1.next();
				fP.setActionName(object1.toString());
				if (bufr != null && bufr.length() > 0)
				{
					bufr.setLength(0);
				}
				bufr.append("SELECT count(*) FROM  feedbackdata WHERE status='" + object1.toString() + "'");
				List totalList = new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);
				if (totalList != null && totalList.size() > 0)
				{
					fP.setActionCounter(Integer.parseInt(totalList.get(0).toString()));
				}
				if (bufr != null && bufr.length() > 0)
				{
					bufr.setLength(0);
				}
				bufr.append("SELECT count(*) FROM  feedbackdata WHERE status='" + object1.toString() + "' AND openDate='" + DateUtil.getCurrentDateUSFormat() + "'");
				List todayList = new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);
				if (todayList != null && todayList.size() > 0)
				{
					fP.setActionToday(todayList.get(0).toString());
				}

				feedDataDashboardList.add(fP);
			}
		}

		/*
		 * List<FeedbackDataPojo> feedDataDashboardList = new
		 * ArrayList<FeedbackDataPojo>(); StringBuffer bufr=newStringBuffer(
		 * "select field_name, field_value from feedback_data_configuration where activeType='true' and id in (5,6,7,8,9,10,11,12)"
		 * ); List data=new createTable().executeAllSelectQuery(bufr.toString(),
		 * connectionSpace); if(data!=null && data.size()>0) {
		 * 
		 * for (Iterator iterator1 = data.iterator(); iterator1.hasNext();) {
		 * FeedbackDataPojo fP=new FeedbackDataPojo(); Object[] object1 =
		 * (Object[]) iterator1.next(); if(object1!=null) { if(object1[0]!=null
		 * && object1[1]!=null) { fP.setSubDept(object1[0].toString());
		 * fP.setPoor(new
		 * EscalationActionControlDao().getGraphDataCounter(connectionSpace
		 * ,"feedbackdata",object1[1].toString(),"Poor")); fP.setAvg(new
		 * EscalationActionControlDao
		 * ().getGraphDataCounter(connectionSpace,"feedbackdata"
		 * ,object1[1].toString(),"Average")); fP.setGood(new
		 * EscalationActionControlDao
		 * ().getGraphDataCounter(connectionSpace,"feedbackdata"
		 * ,object1[1].toString(),"Good")); fP.setVgood(new
		 * EscalationActionControlDao
		 * ().getGraphDataCounter(connectionSpace,"feedbackdata"
		 * ,object1[1].toString(),"Very Good")); fP.setExclnt(new
		 * EscalationActionControlDao
		 * ().getGraphDataCounter(connectionSpace,"feedbackdata"
		 * ,object1[1].toString(),"Excellent")); } }
		 * 
		 * feedDataDashboardList.add(fP); } }
		 */

		return feedDataDashboardList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackDataPojo> getfeedbackTypelist(SessionFactory connectionSpace, List<String> deptId)
	{
		List<FeedbackDataPojo> feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
		try
		{
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("uName");
			String userType = (String) session.get("userTpe");
			String deptLevel = (String) session.get("userDeptLevel");

			String loggedEmpId = "";
			String dept_subdept_id = "";
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					loggedEmpId = object[5].toString();
					dept_subdept_id = object[3].toString();
				}
			}

			StringBuilder qry = new StringBuilder();
			createTable ct = new createTable();
			qry.append("SELECT fbType,id FROM feedback_type WHERE moduleName='FM'  ");
			if (deptId != null && !deptId.toString().equalsIgnoreCase(""))
			{
				qry.append(" AND dept_subdept IN " + deptId.toString().replace("[", "(").replace("]", ")") + "");
			}
			qry.append(" GROUP BY fbType ORDER BY ABS(fbType) ");

			/*
			 * if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) {
			 * qry.append(" AND dept_subdept IN "+deptId.toString().replace("[",
			 * "(").replace("]", ")")+""); }
			 * qry.append(" GROUP BY fbType ORDER BY ABS(fbType) ");
			 */

			// System.out.println("Feedback Type List is as >>>>>>>>>>>>>>>>>>"+
			// qry);
			List type = ct.executeAllSelectQuery(qry.toString(), connectionSpace);
			qry.setLength(0);
			if (type != null && type.size() > 0)
			{
				for (Iterator iterator = type.iterator(); iterator.hasNext();)
				{
					FeedbackDataPojo fP = new FeedbackDataPojo();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						fP.setFeedType(object[0].toString());
						fP.setId(Integer.parseInt(object[1].toString()));
						qry.append(" SELECT COUNT(*) FROM feedback_status AS fs INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND  ft.fbType='" + object[0].toString() + "' ");

						if (userType != null && userType.equalsIgnoreCase("M"))
						{

						} else if (userType != null && userType.equalsIgnoreCase("D"))
						{
							if (deptId.size() > 0)
							{
								qry.append(" AND fs.to_dept_subdept IN " + deptId.toString().replace("[", "(").replace("]", ")") + "");
							}
						} else
						{
							if (userName != null)
							{
								qry.append(" and (fs.feed_registerby='" + userName + "' or fs.allot_to='" + loggedEmpId + "')");
							}
						}
						// System.out.println(
						// "For Counter Total >>>>>>>>>>>>>>>>>>>"+qry);
						List total = ct.executeAllSelectQuery(qry.toString(), connectionSpace);
						qry.setLength(0);
						if (total != null && total.size() > 0)
						{
							fP.setFeedTypeTotal(Integer.parseInt(total.get(0).toString()));
						}
						qry.append("SELECT COUNT(*) FROM feedback_status AS fs INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND  ft.fbType='" + object[0].toString() + "' AND fs.open_date='" + DateUtil.getCurrentDateUSFormat() + "' ");

						if (userType != null && userType.equalsIgnoreCase("M"))
						{

						} else if (userType != null && userType.equalsIgnoreCase("D"))
						{
							if (deptId.size() > 0)
							{
								qry.append(" AND fs.to_dept_subdept IN " + deptId.toString().replace("[", "(").replace("]", ")") + "");
							}
						} else
						{
							if (userName != null)
							{
								qry.append(" and (fs.feed_registerby='" + userName + "' or fs.allot_to='" + loggedEmpId + "')");
							}
						}
						/*
						 * if (deptId!=null &&
						 * !deptId.toString().equalsIgnoreCase("")) {
						 * qry.append(
						 * " AND fs.to_dept_subdept IN "+deptId.toString
						 * ().replace("[", "(").replace("]", ")")+""); }
						 */

						// System.out.println(
						// "For Counter Today >>>>>>>>>>>>>>>>>>>"+qry);
						List today = ct.executeAllSelectQuery(qry.toString(), connectionSpace);
						qry.setLength(0);
						if (today != null && today.size() > 0)
						{
							fP.setFeedTypeToday(Integer.parseInt(today.get(0).toString()));
						}
					}
					feedDataDashboardList.add(fP);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return feedDataDashboardList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackDataPojo> getFeedbackCountersDeptWise(SessionFactory connectionSpace, String subDept)
	{
		List<FeedbackDataPojo> feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
		StringBuffer bufr = new StringBuffer("select field_name, field_value from feedback_data_configuration where activeType='true' && field_name='" + subDept + "' && id in (5,6,7,8,9,10,11,12)");
		List data = new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator1 = data.iterator(); iterator1.hasNext();)
			{
				FeedbackDataPojo fP = new FeedbackDataPojo();
				Object[] object1 = (Object[]) iterator1.next();
				if (object1 != null)
				{
					if (object1[0] != null && object1[1] != null)
					{
						fP.setSubDept(object1[0].toString());
						fP.setPoor(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", object1[1].toString(), "Poor"));
						fP.setAvg(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", object1[1].toString(), "Average"));
						fP.setGood(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", object1[1].toString(), "Good"));
						fP.setVgood(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", object1[1].toString(), "Very Good"));
						fP.setExclnt(new EscalationActionControlDao().getGraphDataCounter(connectionSpace, "feedbackdata", object1[1].toString(), "Excellent"));
					}
				}

				feedDataDashboardList.add(fP);
			}
		}
		return feedDataDashboardList;
	}

	@SuppressWarnings("rawtypes")
	public boolean getUserAbovLevel1OrNot(SessionFactory connectionSpace, String userName)
	{
		boolean showFlag = false;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("select desg.levelofhierarchy,emp.empName,emp.subdept from designation as desg" + " inner join employee_basic as emp on emp.designation=desg.id" + " inner join useraccount as usr on usr.id=emp.useraccountid where userID='" + Cryptography.encrypt(userName, DES_ENCRYPTION_KEY) + "' limit 1");

			List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
			if (dataList != null)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						if (object[0].toString() != null && object[0].toString().equalsIgnoreCase("Level1"))
						{
							showFlag = false;
						} else
						{
							showFlag = true;
						}
					}
				}
			}
		} catch (Exception e)
		{
			showFlag = false;
		}

		return showFlag;
	}

	@SuppressWarnings("rawtypes")
	public int getStatusCounterTodays(SessionFactory connectionSpace, String status, boolean todayFlag, boolean deptFlag, int subDept)
	{
		int counter = 0;

		StringBuffer buffer = new StringBuffer("select count(*) from feedback_status where id!=0 ");

		if (status != null)
		{
			buffer.append(" && status='" + status + "'");
		}

		if (deptFlag && subDept != 0)
		{
			buffer.append(" && to_dept_subdept='" + subDept + "'");
		}

		if (todayFlag)
		{
			buffer.append("&& open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
		}
		List dataList = new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					counter = Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackTicketPojo> getFeedTicketDashboardNormal(SessionFactory connectionSpace, List<String> deptList)
	{
		List<FeedbackTicketPojo> feedTicketList = new ArrayList<FeedbackTicketPojo>();
		try
		{
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("uName");
			String userType = (String) session.get("userTpe");
			String deptLevel = (String) session.get("userDeptLevel");

			String loggedEmpId = "";
			String dept_subdept_id = "";
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					loggedEmpId = object[5].toString();
					dept_subdept_id = object[3].toString();
				}
			}

			StringBuffer qry = new StringBuffer("select ticket_no, feed_by,feed_by_mobno,open_date,status," + "cat.categoryName,feed_brief,fs.id,emp.empName,clientId,fs.location from feedback_status  AS fs" + " LEFT JOIN feedback_subcategory AS scat ON scat.id=fs.subCatg " + " LEFT JOIN feedback_category AS cat ON cat.id=scat.categoryName " + " LEFT join employee_basic emp on fs.allot_to= emp.id"
					+ " where status not in ('Resolved','Ignore','No Further Action Required','Re-Assign') AND moduleName='FM'");
			// " AND to_dept_subdept IN "+deptId.toString().replace("[",
			// "(").replace("]",
			// ")")+"  GROUP BY ticket_no  order by fs.id desc");
			if (userType != null && userType.equalsIgnoreCase("M"))
			{

			} else if (userType != null && userType.equalsIgnoreCase("D"))
			{
				if (deptList.size() > 0)
				{
					qry.append(" and fs.to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");
				}
			} else if (userType != null && userType.equalsIgnoreCase("N"))
			{
				if (userName != null)
				{
					qry.append(" and (fs.feed_registerby='" + userName + "' or fs.allot_to='" + loggedEmpId + "')");
				}
			}

			// System.out.println(">>>>>> Second Quadrant >>>>>>>>>>>>>>>>>>>>> "
			// +qry.toString());
			List data = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						FeedbackTicketPojo feedTicket = new FeedbackTicketPojo();
						if (object[0] != null)
						{
							feedTicket.setTicketNo(object[0].toString());
						}

						if (object[1] != null)
						{
							feedTicket.setFeedBy(object[1].toString());
						}

						if (object[2] != null)
						{
							feedTicket.setFeedByMob(object[2].toString());
						}

						if (object[3] != null)
						{
							feedTicket.setFeedDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
						}
						if (object[4] != null)
						{
							feedTicket.setStatus(object[4].toString());
						}
						if (object[5] != null)
						{
							feedTicket.setCategoryName(object[5].toString());
						}
						if (object[6] != null)
						{
							feedTicket.setFeedBrief(object[6].toString());
						}
						if (object[7] != null)
						{
							feedTicket.setId(Integer.parseInt(object[7].toString()));
						}

						if (object[8] != null)
						{
							feedTicket.setAllotTo((object[8].toString()));
						}

						if (object[9] != null)
						{
							feedTicket.setCr_No(object[9].toString());
						}

						if (object[10] != null)
						{
							feedTicket.setLocation(object[10].toString());
						}

						feedTicketList.add(feedTicket);
					}
				}
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return feedTicketList;
	}

	public FeedbackPojo getPatientDetailsByTicketNo(String ticketNo, SessionFactory connectionSpace)
	{
		FeedbackPojo pojo = new FeedbackPojo();
		int feedId = 0;
		String tempTicketNo = null;
		if (ticketNo.length() > 4)
		{
			tempTicketNo = ticketNo.substring(0, 5);
		} else
		{
			tempTicketNo = ticketNo;
		}

		StringBuilder builder = new StringBuilder("select clientId,compType,serviceBy,centerCode,problem,mobNo,emailId from feedbackdata where id=(select feed_data_id from feedback_ticket where feedTicketNo like '" + tempTicketNo + "%')");

		List data = new createTable().executeAllSelectQuery(builder.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null)
					{
						pojo.setCrNo(object[0].toString());
					}
					if (object[1] != null)
					{
						pojo.setPatType(object[1].toString());
					}
					if (object[2] != null)
					{
						pojo.setDocterName(object[2].toString());
					}
					if (object[3] != null)
					{
						// For Room No
						pojo.setCentreCode(object[3].toString());
					}
					if (object[4] != null)
					{
						pojo.setObservation(object[4].toString());
					}

					if (object[5] != null)
					{
						pojo.setMobileNo(object[5].toString());
					}

					if (object[6] != null)
					{
						pojo.setEmailId(object[6].toString());
					}
				}
			}
		}
		return pojo;
	}

	public List<FeedbackDataPojo> getFeedbackCategoryCounters(SessionFactory connectionSpace, List<String> deptId)
	{
		List<FeedbackDataPojo> feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
		try
		{
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("uName");
			String userType = (String) session.get("userTpe");
			String deptLevel = (String) session.get("userDeptLevel");

			String loggedEmpId = "";
			String dept_subdept_id = "";
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					loggedEmpId = object[5].toString();
					dept_subdept_id = object[3].toString();
				}
			}

			StringBuffer bufr = new StringBuffer("select cat.categoryName,count(*),cat.id FROM feedback_status  AS fs INNER JOIN feedback_subcategory AS scat ON scat.id=fs.subCatg INNER JOIN feedback_category AS cat ON cat.id=scat.categoryName INNER JOIN feedback_type AS ft ON ft.id=cat.fbType where fs.moduleName='FM' ");
			if (userType != null && userType.equalsIgnoreCase("M"))
			{

			} else if (userType != null && userType.equalsIgnoreCase("D"))
			{
				if (deptId.size() > 0)
				{
					bufr.append(" AND fs.to_dept_subdept IN " + deptId.toString().replace("[", "(").replace("]", ")") + "");
				}
			} else
			{
				if (userName != null)
				{
					bufr.append(" and (fs.feed_registerby='" + userName + "' or fs.allot_to='" + loggedEmpId + "')");
				}
			}
			bufr.append(" GROUP BY cat.categoryName order by count(*) desc limit 0,10");
			// "AND to_dept_subdept IN "+deptId.toString().replace("[",
			// "(").replace("]", ")")+
			// " AND ft.fbType='Complaint'  GROUP BY cat.categoryName order by count(*) desc limit 0,10"
			// );

			// System.out.println("Category Details>>>" + bufr);
			List data = new createTable().executeAllSelectQuery(bufr.toString(), connectionSpace);

			if (data != null && data.size() > 0)
			{
				for (Iterator iterator1 = data.iterator(); iterator1.hasNext();)
				{
					FeedbackDataPojo fP = new FeedbackDataPojo();
					Object object1[] = (Object[]) iterator1.next();
					fP.setActionName(object1[0].toString());
					fP.setActionCounter(Integer.parseInt(object1[1].toString()));
					fP.setId(Integer.parseInt(object1[2].toString()));

					feedDataDashboardList.add(fP);
				}
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return feedDataDashboardList;
	}

	@SuppressWarnings("rawtypes")
	public List<FeedbackPojoDashboard> getDashboardLevelStatus(SessionFactory connectionSpace, List<String> deptId)
	{
		List<FeedbackPojoDashboard> list = new ArrayList<FeedbackPojoDashboard>();
		try
		{
			Map session = ActionContext.getContext().getSession();
			String userName = (String) session.get("uName");
			String userType = (String) session.get("userTpe");
			String deptLevel = (String) session.get("userDeptLevel");

			String loggedEmpId = "";
			String dept_subdept_id = "";
			List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					loggedEmpId = object[5].toString();
					dept_subdept_id = object[3].toString();
				}
			}

			StringBuilder query = new StringBuilder();
			createTable ct = new createTable();
			List<String> statusList = new ArrayList<String>();
			statusList.add("Pending");
			statusList.add("High Priority");
			statusList.add("Snooze");
			statusList.add("Ignore");
			statusList.add("Re-Assign");
			statusList.add("Resolved");
			statusList.add("Noted");
			for (String status : statusList)
			{
				query.append(" select feedback.level,count(*) from feedback_status as feedback ");
				query.append(" inner join feedback_subcategory as subcatg on feedback.subcatg = subcatg.id  ");
				query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id  ");
				query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id ");
				query.append(" where subcatg.needEsc='Y' and feedback.moduleName='FM' ");
				if (userType != null && userType.equalsIgnoreCase("M"))
				{

				} 
				else if (userType != null && userType.equalsIgnoreCase("D"))
				{
					if (deptId.size() > 0)
					{
						query.append(" and feedback.to_dept_subdept in " + deptId.toString().replace("[", "(").replace("]", ")") + "");
					}
				} 
				else
				{
					if (userName != null)
					{
						query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
					}
				}

				if (status != null)
				{
					query.append(" and feedback.status='" + status + "' ");
				}

				query.append(" group by feedback.level ");

				// System.out.println("QUERY IS AS level Find :::   "+query.
				// toString());
				List data = ct.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					FeedbackPojoDashboard fpd = new FeedbackPojoDashboard();
					fpd.setPending(status);
					fpd.setDeptId(deptId.toString().replace("[", "").replace("]", ""));
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						int level1 = 0, level2 = 0, level3 = 0, level4 = 0, level5 = 0;
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0].toString().equalsIgnoreCase("Level1"))
							{
								level1 = Integer.parseInt(object[1].toString());
							} else if (object[0].toString().equalsIgnoreCase("Level2"))
							{
								level2 = Integer.parseInt(object[1].toString());
							} else if (object[0].toString().equalsIgnoreCase("Level3"))
							{
								level3 = Integer.parseInt(object[1].toString());
							} else if (object[0].toString().equalsIgnoreCase("Level4"))
							{
								level4 = Integer.parseInt(object[1].toString());
							} else if (object[0].toString().equalsIgnoreCase("Level5"))
							{
								level5 = Integer.parseInt(object[1].toString());
							}
						}
						if (level1 > 0)
						{
							fpd.setPendingL1(String.valueOf(Integer.parseInt(fpd.getPendingL1()) + level1));
						}
						if (level2 > 0)
						{
							fpd.setPendingL2(String.valueOf(Integer.parseInt(fpd.getPendingL2()) + level2));
						}
						if (level3 > 0)
						{
							fpd.setPendingL3(String.valueOf(Integer.parseInt(fpd.getPendingL3()) + level3));
						}
						if (level4 > 0)
						{
							fpd.setPendingL4(String.valueOf(Integer.parseInt(fpd.getPendingL4()) + level4));
						}
						if (level5 > 0)
						{
							fpd.setPendingL5(String.valueOf(Integer.parseInt(fpd.getPendingL5()) + level5));
						}
					}
					list.add(fpd);
				}
				query.setLength(0);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

}
