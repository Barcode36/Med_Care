package com.Over2Cloud.ctrl.helpdesk.common;

import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;

public class HelpdeskUniversalAction extends GridPropertyBean {

	private static final long serialVersionUID = 1L;

	private String dept;
	private String subcatg;
	private String deptOrSubDeptId;

	// for help desk modules
	private int levelOfFeedDraft = 1;
	private int levelOfSurvey = 1;

	private String lodgeFeedback;
	private String feedStatus;

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

	// Variable Define for accessing the method
	private String ticketflag;
	private String dataFor;
	private List<ConfigurationUtilBean> subDept_DeptLevelName = null;

	private List<ConfigurationUtilBean> complanantTextColumns = null;

	private List<ConfigurationUtilBean> reportconfigDDColumns = null;
	private List<ConfigurationUtilBean> reportconfigDTColumns = null;
	private List<ConfigurationUtilBean> reportconfigTimeColumns = null;
	private List<ConfigurationUtilBean> reportconfigTextColumns = null;

	private List<ConfigurationUtilBean> emailConfigTextColumns = null;

	private Map<Integer, String> subDeptList = null;
	private Map<Integer, String> empList = null;
	private Map<Integer, String> downloadSubDeptList = null;

	private JSONArray empArray = new JSONArray();
	private JSONArray commonJSONArray = new JSONArray();

	FeedbackDraftPojo feedDraft = null;
	boolean checkdept = false;

	private String table = "";
	private String selectVar_One = "";
	private String selectVar_Two = "";
	private String conditionVar_Key = "";
	private String conditionVar_Value = "";
	private String conditionVar_Key2 = "";
	private String conditionVar_Value2 = "";
	private String order_Key = "";
	private String order_Value = "";

	List<ConfigurationUtilBean> complaintDropMap = null;

	private List<ConfigurationUtilBean> feedbackTypeColumns = null;
	private List<ConfigurationUtilBean> feedbackCategoryColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryDDColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryTextColumns = null;
	private List<ConfigurationUtilBean> feedbackSubCategoryTimeColumns = null;

	private String reopen;

	public String firstActionMethod() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (getDaily_report() != null && getDaily_report().equals("1")) {
					setdailyReportTags();
					returnResult = SUCCESS;
				}

				if (getReport() != null && getReport().equals("1")) {
					setReportTags();
					returnResult = SUCCESS;
				}
			} catch (Exception e) {
				addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
				e.printStackTrace();
				return ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// Method for getting Sub Departments on the basis of department Id(In Use)
	@SuppressWarnings("unchecked")
	public String getSubDepartment() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				subDeptList = new LinkedHashMap<Integer, String>();
				List list = new ArrayList<String>();
				list.add("id");
				list.add("subdeptname");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subdeptname", "ASC");
				temp.put("deptid", getDept());
				list = new HelpdeskUniversalHelper().getDataFromTable(
						"subdepartment", list, temp, null, order,
						connectionSpace);
				if (list.size() > 0) {
					for (Object c : list) {
						Object[] dataC = (Object[]) c;
						subDeptList
								.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// Method for getting Sub Departments on the basis of department Id(In Use)
	@SuppressWarnings("unchecked")
	public String getEmployee() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				empList = new LinkedHashMap<Integer, String>();
				List list = new ArrayList<String>();
				list.add("id");
				list.add("empName");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("empName", "ASC");
				if (deptLevel.equals("2")) {
					temp.put("subdept", getDept_subdept());
				} else if (deptLevel.equals("1")) {
					temp.put("dept", getDept_subdept());
				}

				list = new HelpdeskUniversalHelper().getDataFromTable(
						"employee_basic", list, temp, null, order,
						connectionSpace);
				if (list.size() > 0) {
					for (Object c : list) {
						Object[] dataC = (Object[]) c;
						empList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public int getCountByField(String table, String field, String value) {
		int count = 0;
		List list = new ArrayList();
		try {
			String qry = "select count(*) from " + table + " where " + field
					+ "='" + value + "'";
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			count = Integer.parseInt(list.get(0).toString());
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept,
			String deptLevel, String level, String floor_status, String floor,
			SessionFactory connectionSpace, String subCategory) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		try {
			query.append("select distinct emp.id from employee_basic as emp");
			query
					.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query
					.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
			query
					.append(" inner join feedback_type fb on fb.dept_subdept = sub_dept.id");
			query
					.append(" inner join feedback_category catt on catt.fbType = fb.id");
			query
					.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id");
			query
					.append(" inner join department dept on sub_dept.deptid = dept.id ");
			query
					.append(" inner join emp_wing_mapping esc on esc.empName =contacts.emp_id  ");
			query
					.append(" inner join wings_detail wing on wing.id = esc.wingsname ");
			query
					.append(" inner join floor_room_detail room on room.wingsname = wing.id ");
			query
					.append(" inner join shift_with_emp_wing shift on shift.id = esc.shiftId ");
			query
					.append(" where  contacts.level='"
							+ level
							+ "' and contacts.work_status='0' and contacts.moduleName='HDM'");
			query.append(" and shift.fromShift<='" + DateUtil.getCurrentTime()
					+ "' and shift.toShift >'" + DateUtil.getCurrentTime()
					+ "'");
			if ("Y".equalsIgnoreCase(floor_status)) {
				// query.append(" and roaster.floor='" + floor +
				// "'  and dept.id=(select deptid from subdepartment where id='"
				// + dept_subDept + "')");
				query.append(" and room.id='" + floor
						+ "'  and  fb.dept_subdept='" + dept_subDept + "'   ");
			} else {
				query.append(" and sub_dept.id='" + dept_subDept + "'");
			}

			// //System.out.println("Allot to query :::::  "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getEmp4EscInAllDept(String dept_subDept, String deptLevel,
			String feedid, String level, SessionFactory connectionSpace) {
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,
					10)
					+ "_date";
			if (deptLevel.equals("2")) {
				query
						.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp");
				query
						.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query
						.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
				query
						.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
				query
						.append(" inner join department dept on dept.id = sub_dept.deptid ");
				query
						.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
				query
						.append(" inner join escalation_contact_config_detail esc on esc.empName =emp.id  ");
				query
						.append(" inner join wings_detail wing on wing.id = esc.wingsname ");
				query
						.append(" inner join floor_room_detail room on room.wingsname = wing.id ");
				query
						.append(" where shift.shiftName="
								+ shiftname
								+ " and roaster.status='active' and roaster.attendance='present' and contacts.level<='"
								+ level
								+ "' and contacts.work_status='3' and contacts.moduleName='HDM' and dept.id='"
								+ dept_subDept + "'");
				query.append(" and shift.shiftFrom<='"
						+ DateUtil.getCurrentTime() + "' and shift.shiftTo >'"
						+ DateUtil.getCurrentTime() + "'");
			}

			// ////System.out.println("@@@@@@@@@@@"+query.toString());

			empList = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmployeeData4Asset(String uid) {
		List empList = new ArrayList();
		String empall = "";
		try {
			empall = "select empName,id  from employee_basic where id='" + uid
					+ "'";
			empList = new createTable().executeAllSelectQuery(empall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmp4EscInAsset(String outletid, String level,
			SessionFactory connectionSpace) {
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			if (deptLevel.equals("2")) {
				query
						.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp");
				query
						.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query
						.append(" inner join outlet_to_contact_mapping outlet on contacts.id = outlet.contactid ");
				query
						.append(" where  contacts.level<='"
								+ level
								+ "' and contacts.moduleName='ASTM' and outlet.sendflag='NC' and outlet.outletid='"
								+ outletid + "'");
			}
			empList = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getDataList(String table, String selectfield,
			String searchfield, String searchfieldvalue,
			SessionFactory connectionSpace) {
		List dataList = new ArrayList();
		try {
			String data = "select " + selectfield + " from " + table
					+ " where " + searchfield + "='" + searchfieldvalue + "'";
			dataList = new createTable().executeAllSelectQuery(data,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public String getData(String table, String selectfield, String searchfield,
			String searchfieldvalue) {
		List dataList = new ArrayList();
		String data = "";
		try {
			String qry = "select " + selectfield + " from " + table + " where "
					+ searchfield + "='" + searchfieldvalue + "'";
			dataList = new createTable().executeAllSelectQuery(qry,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dataList != null && dataList.size() > 0) {
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				data = object.toString();
			}
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation4Feedback(String dept_subDept,
			String deptLevel, String level, SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		try {
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,
					10)
					+ "_date";
			if (deptLevel.equals("2")) {
				query
						.append("select distinct emp.id from employee_basic as emp");
				query
						.append(" inner join designation desg on emp.designation = desg.id");
				query
						.append(" inner join subdepartment sub_dept on emp.subdept = sub_dept.id");
				query
						.append(" inner join roaster_conf roaster on emp.empId = roaster.empId");
				query
						.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept");
				query
						.append(" where shift.shiftName="
								+ shiftname
								+ " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and sub_dept.id='"
								+ dept_subDept
								+ "' and desg.levelofhierarchy='"
								+ level
								+ "' and shift.deptHierarchy='2' and shift.shiftFrom<='"
								+ DateUtil.getCurrentTime()
								+ "' and shift.shiftTo >'"
								+ DateUtil.getCurrentTime() + "'");
			} else if (deptLevel.equals("1")) {
				query
						.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne from employee_basic as emp");
				query
						.append(" inner join designation desg on emp.designation = desg.id");
				query
						.append(" inner join department dept on emp.dept = dept.id");
				query
						.append(" inner join roaster_conf roaster on emp.empId = roaster.empId");
				query
						.append(" inner join shift_conf shift on dept.id = shift.dept_subdept");
				query
						.append(" where shift.shiftName="
								+ shiftname
								+ " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='"
								+ level
								+ "' and dept.id='"
								+ dept_subDept
								+ "' and shift.deptHierarchy='1' and shift.shiftFrom<='"
								+ DateUtil.getCurrentTime()
								+ "' and shift.shiftTo >'"
								+ DateUtil.getCurrentTime() + "'");
			}
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4EscInDept(String dept_subDept,
			String deptLevel, String level, SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		try {
			String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,
					10)
					+ "_date";
			if (deptLevel.equals("2")) {
				qry = " select distinct emp.id from employee_basic as emp"
						+ " inner join designation desg on emp.designation = desg.id"
						+ " inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
						+ " inner join department dept on sub_dept.deptid = dept.id"
						+ " inner join roaster_conf roaster on emp.empId = roaster.empId"
						+ " inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
						+ " where shift.shiftName="
						+ shiftname
						+ " and roaster.attendance='Present' and emp.status='0' and roaster.status='Active' and desg.levelofhierarchy='"
						+ level
						+ "' and dept.id='"
						+ dept_subDept
						+ "' and shift.deptHierarchy='2' and shift.shiftFrom<='"
						+ DateUtil.getCurrentTime() + "' and shift.shiftTo >'"
						+ DateUtil.getCurrentTime() + "'";
			}
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept,
			String deptLevel, String toLevel, List empId,
			SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		String arr = empId.toString().replace("[", "(").replace("]", ")");
		try {
			query
					.append("select distinct allot_to from feedback_status as feed_status");
			query
					.append(" inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id ");
			query
					.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
			query.append(" where sub_dept.id='" + dept_subDept
					+ "' and contacts.level='" + toLevel + "' and allot_to in "
					+ arr + " and feed_status.open_date='"
					+ DateUtil.getCurrentDateUSFormat() + "'");
			// ////System.out.println("qqqqqq"+query.toString());

			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept,
			String deptLevel, String toLevel, List empId, String module,
			SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		String arr = empId.toString().replace("[", "(").replace("]", ")");
		try {
			query
					.append("select distinct allot_to from feedback_status as feed_status");
			if (module != null && !module.equals("")
					&& module.equalsIgnoreCase("HDM")) {
				query
						.append(" inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id ");
				query
						.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
				query.append(" where sub_dept.id='" + dept_subDept
						+ "' and contacts.level='" + toLevel
						+ "' and allot_to in " + arr
						+ " and feed_status.open_date='"
						+ DateUtil.getCurrentDateUSFormat() + "'");
			} else if (module != null && !module.equals("")
					&& module.equalsIgnoreCase("FM")) {
				query
						.append(" inner join department dept on feed_status.to_dept_subdept = dept.id ");
				query
						.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
				query.append(" where dept.id='" + dept_subDept
						+ "' and contacts.level='" + toLevel
						+ "' and allot_to in " + arr
						+ " and feed_status.open_date='"
						+ DateUtil.getCurrentDateUSFormat() + "'");
			}
			query.append(" and feed_status.moduleName='" + module + "'");
			// //System.out.println("Second Query  "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public boolean getLastAllocateEmployee(String dept_subDept,
			String deptLevel, String toLevel, String empId, int size,
			SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		String qry = null;
		boolean flag = false;
		try {
			qry = "select distinct feed_status.id from feedback_status as feed_status"
					+ " inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id "
					+ " inner join designation desg on sub_dept.id  = desg.mappedOrgnztnId"
					+ " where feed_status.to_dept_subdept='"
					+ dept_subDept
					+ "' and feed_status.deptHierarchy="
					+ deptLevel
					+ " and desg.levelofhierarchy='"
					+ toLevel
					+ "' and feed_status.open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' and feed_status.allot_to="
					+ empId
					+ " and feed_status.status not in ('Pending','High Priority')  order by feed_status.id asc limit "
					+ (size - 1) + "";
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
			if (list != null && list.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String subdept, String deptLevel) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String qry = null;
		try {
			session = HibernateSessionFactory.getSession();
			qry = "select distinct allot_to from feedback_status where open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' and to_dept_subdept="
					+ subdept
					+ " and status='Pending'";
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getRandomEmployee(String tableId, List empId) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String allotmentId = "";
		String qry = null;
		try {
			session = HibernateSessionFactory.getSession();
			qry = "select allot_to from feedback_status where open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' group by allot_to having allot_to in "
					+ empId.toString().replace("[", "(").replace("]", ")")
					+ " order by count(allot_to) limit 1 ";
			// //System.out.println("Final list >>>>>>>>>>>>>>>>>>>>>>  "+qry);
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				allotmentId = object.toString();
			}
		}
		return allotmentId;
	}

	@SuppressWarnings("unchecked")
	public List getTicketNumber(SessionFactory connectionSpace) {
		List ticketno = new ArrayList();
		try {
			String ticket_no = "select ticket_no from feedback_status  where id=(select max(id) from feedback_status)";
			ticketno = new createTable().executeAllSelectQuery(ticket_no,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketno;
	}

	@SuppressWarnings("unchecked")
	public String getEmpDetail() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List empList = new ArrayList<String>();
				empList.add("id");
				empList.add("empName");
				Map<String, Object> temp = new HashMap<String, Object>();
				if (deptLevel.equals("2")) {
					temp.put("subdept", getDeptOrSubDeptId());
				} else if (deptLevel.equals("1")) {
					temp.put("dept", getDeptOrSubDeptId());
				}

				Map<String, Object> order = new HashMap<String, Object>();
				order.put("empName", "ASC");

				empList = new HelpdeskUniversalHelper().getDataFromTable(
						"employee_basic", empList, temp, null, order,
						connectionSpace);
				if (empList.size() > 0) {
					for (Object c : empList) {
						JSONObject empObject = new JSONObject();
						Object[] dataC = (Object[]) c;
						empObject.put("id", dataC[0].toString());
						empObject.put("name", dataC[1].toString());
						empArray.add(empObject);
					}

				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String commonSelectMethod() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List selectVarList = new ArrayList<String>();
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				List resultList = new ArrayList<String>();

				selectVarList.add(selectVar_One);
				selectVarList.add(selectVar_Two);
				if (conditionVar_Key != null && !conditionVar_Key.equals("")
						&& conditionVar_Value != null
						&& !conditionVar_Value.equals("")
						&& !conditionVar_Value.equals("undefined")) {
					// //System.out.println("Inside first if  "+conditionVar_Key+
					// "     "+conditionVar_Value);
					temp.put(conditionVar_Key, conditionVar_Value);
				}
				if (conditionVar_Key2 != null && !conditionVar_Key2.equals("")
						&& conditionVar_Value2 != null
						&& !conditionVar_Value2.equals("")
						&& !conditionVar_Value2.equals("undefined")) {
					// //System.out.println("Inside second if  "+conditionVar_Key2+
					// "     "+conditionVar_Value2);
					temp.put(conditionVar_Key2, conditionVar_Value2);
				}
				if (table.equalsIgnoreCase("feedback_type")) {
					temp.put("hide_show", "Active");
				}

				if (order_Key != null && !order_Key.equals("")
						&& order_Value != null && !order_Value.equals("")) {
					order.put(order_Key, order_Value);
				}
				resultList = new HelpdeskUniversalHelper().getDataFromTable(
						table, selectVarList, temp, null, order,
						connectionSpace);
				if (resultList != null && resultList.size() > 0) {
					for (Object c : resultList) {
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getServiceDepartment() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			StringBuilder qry = new StringBuilder();
			try {
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List resultList = new ArrayList<String>();
				qry
						.append(" select distinct subdept.id,subdept.subdeptname from feedback_type as feed");
				qry
						.append(" inner join subdepartment  subdept on feed.dept_subdept= subdept.id");
				qry
						.append(" inner join department  dept on subdept.deptid= dept.id");
				qry
						.append(" where dept.id="
								+ conditionVar_Value
								+ " and feed.moduleName='"
								+ conditionVar_Value2
								+ "' and feed.hide_show='Active' order by subdept.subdeptname");
				// //System.out.println("Service Dept Query  "+qry.toString());
				resultList = new HelpdeskUniversalHelper().getData(qry
						.toString(), connectionSpace);
				if (resultList.size() > 0) {
					for (Object c : resultList) {
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public List getServiceDeptOrFeedType(String deptid, String module,
			String dataFor) {
		List resultList = null;
		StringBuilder qry = new StringBuilder();
		try {
			if (dataFor != null && !dataFor.equals("")
					&& dataFor.equalsIgnoreCase("SD")) {
				qry
						.append(" select distinct subdept.id,subdept.subdeptname from feedback_type as feed");
				qry
						.append(" inner join subdepartment  subdept on feed.dept_subdept= subdept.id");
				qry
						.append(" inner join department  dept on subdept.deptid= dept.id");
				qry
						.append(" where dept.id="
								+ deptid
								+ " and feed.moduleName='"
								+ module
								+ "' and feed.hide_show='Active' order by subdept.subdeptname");
			} else if (dataFor != null && !dataFor.equals("")
					&& dataFor.equalsIgnoreCase("FT")) {
				qry
						.append(" select distinct feed.id,feed.fbType from feedback_type as feed");
				qry
						.append(" inner join department  dept on feed.dept_subdept= dept.id");
				qry.append(" where dept.id=" + deptid
						+ " and feed.moduleName='" + module
						+ "' and feed.hide_show='Active' order by feed.fbType");
			}

			// //System.out.println("Service Dept / Feed typeQuery  "+qry.toString(
			// ));
			resultList = new HelpdeskUniversalHelper().getData(qry.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List getEmpData(String uid, String deptLevel, String columnName) {

		// //System.out.println("****ColumneNmae  "+columnName);
		List empList = new ArrayList();
		String empall = null;
		try {
			// //System.out.println("ddddd lllllevel   "+deptLevel);
			if (columnName != null && deptLevel != null
					&& deptLevel.equals("SD") && columnName.equals("uids")) {
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,emp.city,emp.empId from employee_basic as emp "
						+ " inner join department as dept on emp.deptname=dept.id "
						+ " where emp.empId='" + uid + "'";
			} else if (columnName != null && deptLevel != null
					&& deptLevel.equals("SD") && columnName.equals("mobile")) {

				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,emp.city,emp.empId from employee_basic as emp "
						+ " inner join department as dept on emp.deptname=dept.id "
						+ " where emp.mobOne='" + uid + "'";

			}

			else if (columnName != null && deptLevel != null
					&& deptLevel.equals("SD") && columnName.equals("ids")) {

				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,emp.city,emp.empId from employee_basic as emp "
						+ " inner join department as dept on emp.deptname=dept.id "
						+ " where emp.id='" + uid + "'";

			} else {
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,emp.city,emp.empId from employee_basic as emp "
						+ " inner join department as dept on emp.deptname=dept.id where emp.empId='"
						+ uid + "'";
			}
			// //System.out.println("Query for getting employee detail    "+empall)
			// ;
			empList = new createTable().executeAllSelectQuery(empall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmpDataByUserName(String uName, String deptLevel) {
		List empList = new ArrayList();
		String empall = null;
		try {
			empall = "select emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid,emp.city,uac.userType from employee_basic as emp"
					+ " inner join department as dept on emp.deptname=dept.id"
					+ " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='"
					+ uName + "'";
			// //System.out.println("Emp Data Query   "+empall);
			// }
			empList = new createTable().executeAllSelectQuery(empall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmployeeData(String uid, String deptLevel) {
		List empList = new ArrayList();
		String empall = "";
		try {
			empall = "select empName,mobOne,deptname,empId,id  from employee_basic where id='"
					+ uid + "'";
			// //System.out.println("Second Query  "+empall);
			empList = new createTable().executeAllSelectQuery(empall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	@SuppressWarnings("unchecked")
	public List getEmployeeData4Roaster(String uid) {
		List empList = new ArrayList();
		StringBuilder str = new StringBuilder();
		try {

			str
					.append(" select comp.id,comp.level,comp.forDept_id,emp.empName from employee_basic as emp");
			str
					.append(" inner join compliance_contacts as comp on comp.emp_id=emp.id");
			str.append(" where comp.id='" + uid + "'");
			// //System.out.println(">>>>>>  "+str.toString());
			empList = new createTable().executeAllSelectQuery(str.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}

	// Method for getting Feedback Category and Sub Category Detail
	@SuppressWarnings("unchecked")
	public String getFBSubCategDetail() {
		String returnResult = ERROR;
		try {
			feedDraft = new FeedbackDraftPojo();
			List subCatgDetail = new ArrayList<String>();
			StringBuilder qry = new StringBuilder();
			qry
					.append("SELECT subCate.id as subCateId,subDept.subdeptname,ftype.fbType,cate.categoryName,subCate.subCategoryName,subCate.feedBreif,subCate.addressingTime ,subCate.escalateTime,subCate.resolutionTime,subDept.id as subDeptId,dept.id,dept.deptName  ");
			qry.append(" FROM feedback_subcategory AS subCate ");
			qry
					.append(" LEFT JOIN feedback_category as cate on cate.id=subCate.categoryName ");
			qry
					.append(" LEFT JOIN feedback_type AS ftype on ftype.id=cate.fbType ");
			qry
					.append(" LEFT JOIN subdepartment AS subDept on subDept.id=ftype.dept_subdept ");
			qry
					.append(" LEFT JOIN department AS dept ON dept.id = subDept.deptid ");
			qry.append(" WHERE subCate.id='" + getSubcatg() + "' ");
			subCatgDetail = new HelpdeskUniversalHelper().getData(qry
					.toString(), connectionSpace);
			// //System.out.println("qry.toString() ::::  "+qry.toString());
			/*
			 * Map<String, Object> temp = new HashMap<String, Object>(); sysou
			 * temp.put("id", Integer.parseInt(getSubcatg())); Map<String,
			 * Object> order = new HashMap<String, Object>(); subCatgDetail =
			 * new
			 * HelpdeskUniversalHelper().getDataFromTable("feedback_subcategory"
			 * , null, temp, null, order, connectionSpace);
			 */
			if (subCatgDetail != null && subCatgDetail.size() > 0) {
				for (Iterator iterator = subCatgDetail.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null
							|| objectCol[0].toString().equals("")) {
					} else {
						feedDraft.setId((Integer) objectCol[0]);
					}
					if (objectCol[1] == null
							&& objectCol[1].toString().equals("")) {
						feedDraft.setSubdept("NA");
					} else {
						feedDraft.setSubdept(objectCol[1].toString());
					}
					if (objectCol[2] == null
							&& objectCol[2].toString().equals("")) {
						feedDraft.setFeedtype("NA");
					} else {
						feedDraft.setFeedtype(objectCol[2].toString());
					}
					if (objectCol[3] == null
							&& objectCol[3].toString().equals("")) {
						feedDraft.setCategory("NA");
					} else {
						feedDraft.setCategory(objectCol[3].toString());
					}
					if (objectCol[5] == null
							&& objectCol[5].toString().equals("")) {
						feedDraft.setFeed_msg("NA");
					} else {
						feedDraft.setFeed_msg(objectCol[5].toString());
					}
					if (objectCol[6] == null
							&& objectCol[6].toString().equals("")) {
						feedDraft.setAddressing_time("00:30");
					} else {
						feedDraft.setAddressing_time(objectCol[6].toString());
					}
					if (objectCol[7] == null
							&& objectCol[7].toString().equals("")) {
						feedDraft.setEscalation_time("02:00");
					} else {
						feedDraft.setEscalation_time(objectCol[7].toString());
					}
					if (objectCol[6] != null
							&& !objectCol[6].toString().equals("")
							&& objectCol[7] != null
							&& !objectCol[7].toString().equals("")) {
						feedDraft.setResolution_time(DateUtil
								.getResolutionTime(objectCol[6].toString(),
										objectCol[7].toString()));
					}
					if (objectCol[9] == null
							&& objectCol[9].toString().equals("")) {
					} else {
						feedDraft.setFeedtype_id(objectCol[9].toString());
					}
					if (objectCol[10] == null
							&& objectCol[10].toString().equals("")) {
					} else {
						feedDraft.setDeptId(objectCol[10].toString());
					}
					if (objectCol[11] == null
							&& objectCol[11].toString().equals("")) {
					} else {
						feedDraft.setDeptName(objectCol[11].toString());
					}
				}
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public String getFBSubCategDetailDept() {
		String returnResult = ERROR;
		try {
			subDeptList = new HashMap<Integer, String>();
			List subCatgDetail = new ArrayList<String>();
			StringBuilder qry = new StringBuilder();
			qry.append("SELECT dept.id,dept.deptName  ");
			qry.append(" FROM feedback_subcategory AS subCate ");
			qry
					.append(" LEFT JOIN feedback_category as cate on cate.id=subCate.categoryName ");
			qry
					.append(" LEFT JOIN feedback_type AS ftype on ftype.id=cate.fbType ");
			qry
					.append(" LEFT JOIN subdepartment AS subDept on subDept.id=ftype.dept_subdept ");
			qry
					.append(" LEFT JOIN department AS dept ON dept.id = subDept.deptid ");
			qry.append(" WHERE subCate.id='" + getSubcatg() + "' ");
			subCatgDetail = new HelpdeskUniversalHelper().getData(qry
					.toString(), connectionSpace);
			/*
			 * Map<String, Object> temp = new HashMap<String, Object>();
			 * 
			 * temp.put("id", Integer.parseInt(getSubcatg())); Map<String,
			 * Object> order = new HashMap<String, Object>(); subCatgDetail =
			 * new
			 * HelpdeskUniversalHelper().getDataFromTable("feedback_subcategory"
			 * , null, temp, null, order, connectionSpace);
			 */
			if (subCatgDetail != null && subCatgDetail.size() > 0) {
				for (Iterator iterator = subCatgDetail.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] != null && objectCol[1] != null) {
						subDeptList.put(Integer.parseInt(objectCol[0]
								.toString()), objectCol[1].toString());
					}
				}
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public String getResolutionTime11() {
		String returnResult = ERROR;
		try {
			feedDraft = new FeedbackDraftPojo();
			String resolution_time = null;
			String[] arr1 = null;
			String[] arr2 = null;
			if (!addressTime.equals("") && !escTime.equals("")) {
				arr1 = addressTime.split(":");
				arr2 = escTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60) {
					c = a;
					resolution_time = "" + b + ":" + a;
				} else {
					d = a / 60;
					c = a % 60;
					d = b + d;
					resolution_time = "" + d + ":" + c;
				}
				if (resolution_time != null && !resolution_time.equals("")) {
					feedDraft.setResolution_time(resolution_time);
				}
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public String addTwoTimes(String addressTime, String resolutionTime) {
		String escalation_time = null;
		try {
			String[] arr1 = null;
			String[] arr2 = null;
			if (!addressTime.equals("") && !resolutionTime.equals("")) {
				arr1 = addressTime.split(":");
				arr2 = resolutionTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60) {
					c = a;
					escalation_time = "" + b + ":" + a;
				} else {
					d = a / 60;
					c = a % 60;
					d = b + d;
					escalation_time = "" + d + ":" + c;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return escalation_time;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String id) {
		List feedbackList = new ArrayList();
		try {
			String subdeptall = "select feed_msg,resolution_time,escalation_time,sub_catg_name from sub_category_detail where sub_catg_id="
					+ id + "";
			feedbackList = new createTable().executeAllSelectQuery(subdeptall,
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedbackList;
	}

	@SuppressWarnings("unchecked")
	public String getField(String table, String selectfield, String wherefield,
			String id, SessionFactory connectionSpace) {
		String returnField = "";
		List l = new ArrayList();
		try {
			String qry = "select " + selectfield + " from " + table + " where "
					+ wherefield + "='" + id + "'";
			l = new createTable().executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (l != null) {
			returnField = l.get(0).toString();
		}
		return returnField;
	}

	@SuppressWarnings("unchecked")
	public List getMultipleColumns(String table, String selectfield1,
			String selectfield2, String selectfield3, String selectfield4,
			String selectfield5, String wherefield1, String whereValue1,
			String wherefield2, String whereValue2,
			SessionFactory connectionSpace) {
		// String returnField = "";
		List l = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			query.append("select " + selectfield1 + "");
			if (selectfield2 != null && !selectfield2.equals("")) {
				query.append("," + selectfield2 + "");
			}
			if (selectfield3 != null && !selectfield3.equals("")) {
				query.append("," + selectfield3 + "");
			}
			if (selectfield4 != null && !selectfield4.equals("")) {
				query.append("," + selectfield4 + "");
			}
			if (selectfield5 != null && !selectfield5.equals("")) {
				query.append("," + selectfield5 + "");
			}
			query.append(" from " + table + " ");

			if (wherefield1 != null && !wherefield1.equals("")
					&& whereValue1 != null && !whereValue1.equals("")) {
				query
						.append(" where " + wherefield1 + "='" + whereValue1
								+ "'");
			}
			if (wherefield2 != null && !wherefield2.equals("")
					&& whereValue2 != null && !whereValue2.equals("")) {
				query.append(" and " + wherefield2 + "='" + whereValue2 + "'");
			}
			// //System.out.println("First Query   "+query.toString());
			l = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public int getCountByField(String key, String tableName,
			Map<String, Object> condtnBlock) {
		int count = 0;
		List listdata = new ArrayList();
		StringBuilder selectTableData = new StringBuilder("");

		selectTableData.append("select count(*)");

		selectTableData.append(" from " + tableName + "");
		if (condtnBlock.size() > 0) {
			selectTableData.append(" where ");
		}

		int size = 1;
		Set set = condtnBlock.entrySet();
		Iterator i = set.iterator();
		i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			if (size < condtnBlock.size())
				selectTableData.append(me.getKey() + " = '" + me.getValue()
						+ "' and ");
			else
				selectTableData.append(me.getKey() + " = '" + me.getValue()
						+ "' ");
			size++;
		}

		try {
			listdata = new createTable().executeAllSelectQuery(selectTableData
					.toString(), connectionSpace);
			if (listdata.size() > 0) {
				count = Integer.parseInt(listdata.get(0).toString());
			}
		} catch (Exception ex) {
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public int getCount4Feedback(String table, String field1,
			String field1value, String fromDate, String toDate, String field2,
			List field2value, String field3, String field3value,
			SessionFactory connectionSpace) {
		Session session = null;
		List list = new ArrayList();
		int count = 0;
		StringBuffer qry = new StringBuffer();
		try {
			session = HibernateSessionFactory.getSession();
			qry.append("select count(*) from "
					+ table
					+ " where "
					+ field1
					+ "='"
					+ field1value
					+ "' and  "
					+ field2
					+ " in "
					+ field2value.toString().replace("[", "(")
							.replace("]", ")") + " and " + field3 + "='"
					+ field3value + "'");
			if (field1value.equals("Resolved")) {
				qry.append(" and open_date between '" + fromDate + "' and '"
						+ toDate + "'");
			}
			list = new createTable().executeAllSelectQuery(qry.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				count = Integer.parseInt(object.toString());
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String table, String field1value,
			String fromDate, String toDate, List field2value,
			String field3value, String orderField, String order,
			String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (field3value.equals("2")) {
				query
						.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,feedback.feed_by,feedback.feed_by_mobno,");
				query
						.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
				query
						.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query
						.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query
						.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query
						.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query
						.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by");
				if (field1value.equalsIgnoreCase("Resolved")) {
					query
							.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
				query.append(" from feedback_status as feedback");
				query
						.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query
						.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
				query
						.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
				query
						.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query
						.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query
						.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query
						.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query
						.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				if (field1value.equalsIgnoreCase("Resolved")) {
					query
							.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
				}

				query.append(" where feedback.status='"
						+ field1value
						+ "' and feedback.deptHierarchy='"
						+ field3value
						+ "' and feedback.to_dept_subdept in "
						+ field2value.toString().replace("[", "(").replace("]",
								")") + "");
				if (field1value.equals("Resolved")) {
					query.append(" and feedback.open_date between '" + fromDate
							+ "' and '" + toDate + "'");
				}
				query.append(" ORDER BY feedback.id " + order + "");
			}

			else if (field3value.equals("1")) {
				query
						.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
				query
						.append("feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,");
				query
						.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query
						.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query
						.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query
						.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query
						.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason");
				if (field1value.equalsIgnoreCase("Resolved")) {
					query
							.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
				query.append(" from feedback_status as feedback");
				query
						.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query
						.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query
						.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query
						.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query
						.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query
						.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				if (field1value.equalsIgnoreCase("Resolved")) {
					query
							.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
				}
				query.append(" where feedback.status='"
						+ field1value
						+ "' and feedback.deptHierarchy='"
						+ field3value
						+ "' and feedback.to_dept_subdept in "
						+ field2value.toString().replace("[", "(").replace("]",
								")") + "");

				if (searchField != null && searchString != null
						&& !searchField.equalsIgnoreCase("")
						&& !searchString.equalsIgnoreCase("")) {
					query.append(" and");

					if (searchOper.equalsIgnoreCase("eq")) {
						query.append(" " + searchField + " = '" + searchString
								+ "'");
					} else if (searchOper.equalsIgnoreCase("cn")) {
						query.append(" " + searchField + " like '%"
								+ searchString + "%'");
					} else if (searchOper.equalsIgnoreCase("bw")) {
						query.append(" " + searchField + " like '"
								+ searchString + "%'");
					} else if (searchOper.equalsIgnoreCase("ne")) {
						query.append(" " + searchField + " <> '" + searchString
								+ "'");
					} else if (searchOper.equalsIgnoreCase("ew")) {
						query.append(" " + searchField + " like '%"
								+ searchString + "'");
					}
				}
				query.append(" ORDER BY feedback.id " + order + "");
			}
			// //System.out.println(query.toString()+"  ::: Query String");
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getCategoryDetail(String catgId) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			/*
			 * query.append(
			 * "select feedback.id,feedback.ticket_no,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,feedback.feed_by,feedback.feed_by_mobno,"
			 * );query.append(
			 * "feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,"
			 * );query.append(
			 * "subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,"
			 * );query.append(
			 * "feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,"
			 * );query.append(
			 * "feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,"
			 * );query.append(
			 * "feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,"
			 * );query.append(
			 * "feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by"
			 * );query.append(
			 * ",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used"
			 * ); query.append(" from feedback_status as feedback");
			 * query.append
			 * (" left join employee_basic emp on feedback.allot_to= emp.id");
			 * query.append(
			 * " left join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id"
			 * );query.append(
			 * " left join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id"
			 * );query.append(
			 * " left join department dept1 on subdept1.deptid= dept1.id");
			 * query
			 * .append(" left join department dept2 on subdept2.deptid= dept2.id"
			 * );query.append(
			 * " left join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"
			 * );query.append(
			 * " left join feedback_category catg on subcatg.categoryName = catg.id"
			 * );query.append(
			 * " left join feedback_type feedtype on catg.fbType = feedtype.id"
			 * );query.append(
			 * " left join employee_basic emp1 on feedback.resolve_by= emp1.id"
			 * );
			 */

			query
					.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
			query
					.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
			query.append("subcatg.subCategoryName,feedback.feed_brief,");
			query
					.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
			query
					.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
			query
					.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query
					.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.Addr_date_time");
			query
					.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
			query.append(" from feedback_status as feedback");
			query
					.append(" left join employee_basic emp on feedback.allot_to= emp.id");
			query
					.append(" left join department dept1 on feedback.by_dept_subdept= dept1.id");
			query
					.append(" left join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query
					.append(" left join department dept2 on subdept2.deptid= dept2.id");
			query
					.append(" left join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" left join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" left join feedback_type feedtype on catg.fbType = feedtype.id");
			query
					.append(" left join employee_basic emp1 on feedback.resolve_by= emp1.id");
			query.append(" where  catg.id='"
					+ catgId
					+ "' and feedback.open_date between '"
					+ DateUtil.getNewDate("day", -7, DateUtil
							.getCurrentDateUSFormat()) + "' and '"
					+ DateUtil.getCurrentDateUSFormat() + "'");
			query.append(" ORDER BY feedback.id ASC ");
			// //System.out.println(query.toString()+"  ::: pppppp   Query String")
			// ;
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String table, String field1value,
			String fromDate, String toDate, String deptid, String field3value,
			String orderField, String order, String module, String searchField,
			String searchString, String searchOper,
			SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			query
					.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
			query
					.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
			query.append("subcatg.subCategoryName,feedback.feed_brief,");
			query
					.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
			query
					.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
			query
					.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query
					.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.Addr_date_time");
			if (field1value.equalsIgnoreCase("Resolved")) {
				query
						.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
			}
			query.append(" from feedback_status as feedback");
			query
					.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query
					.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query
					.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query
					.append(" inner join department dept2 on subdept2.deptid= dept2.id");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			if (field1value.equalsIgnoreCase("Resolved")) {
				query
						.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
			}

			query.append(" where feedback.status='" + field1value
					+ "' and dept2.id='" + deptid + "'");
			if (field1value.equals("Resolved")) {
				query.append(" and feedback.open_date between '" + fromDate
						+ "' and '" + toDate + "'");
			}
			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			query.append(" and feedback.moduleName='" + module + "'");
			query.append(" ORDER BY " + orderField + " " + order + "");

			// //System.out.println("PPPPPPPPPPPPPP  :::::   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// //System.out.println("List Size is "+list.size());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String table, String field1value,
			String fromDate, String toDate, String todeptid, String fromdeptid,
			String field3value, String orderField, String order, String module,
			String emp_Name, String userType, String searchField,
			String searchString, String searchOper,
			SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			query
					.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
			query
					.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
			query.append("subcatg.subCategoryName,feedback.feed_brief,");
			query
					.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
			query
					.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
			query
					.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query
					.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,feedback.Addr_date_time");
			if (field1value.equalsIgnoreCase("Resolved")) {
				query
						.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
			}
			query.append(" from " + table + " as feedback");
			query
					.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query
					.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query
					.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query
					.append(" inner join department dept2 on subdept2.deptid= dept2.id");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			if (field1value.equalsIgnoreCase("Resolved")) {
				query
						.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
			}

			query.append(" where feedback.status='" + field1value + "'");

			if (userType != null && !userType.equals("")
					&& userType.equalsIgnoreCase("H")) {
				query.append(" and dept2.id='" + todeptid + "'");
			} else if (userType != null && !userType.equals("")
					&& userType.equalsIgnoreCase("N")) {
				query.append(" and feedback.feed_by='" + emp_Name + "'");
			}

			if (fromdeptid != null && !fromdeptid.equals("")
					&& !fromdeptid.equals("-1")) {
				query.append(" and dept1.id='" + fromdeptid + "'");
			}
			if (field1value.equals("Resolved")) {
				// //System.out.println("At last In Query From Date  "+fromDate);
				// //System.out.println("At last In Query To DAte "+toDate);
				query.append(" and feedback.open_date between '" + fromDate
						+ "' and '" + toDate + "'");
			}

			query.append(" and feedback.moduleName='" + module + "'");
			query.append(" ORDER BY " + orderField + " " + order + "");

			// //System.out.println("QQQQQQ   :::::   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getDataForDashboard(String status, String fromDate,
			String toDate, String dashFor, String dept_subdeptid,
			String dataFlag, String module, String empname, String level,
			String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace, String reopen) {// //System.out.println("Emp name  :::::::::::::::::::>>>>>>>>>>>>>>> "+
		// empname);
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			query
					.append("select feedback.id,feedback.ticket_no,dept2.deptName as todept,feedtype.fbType as tosubdept,");
			query
					.append("emp.empName as allot_to,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
			query
					.append("frd.roomno,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
			query
					.append("feedback.open_date,feedback.open_time,feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,");
			query
					.append("feedback.level,feedback.status,feedback.feed_registerby ");

			if (!status.equalsIgnoreCase("Pending")) {
				query
						.append(",history.action_date,history.action_time,history.action_duration,history.action_remark, emp1.empName as action_by ");
				if (status.equalsIgnoreCase("Snooze")) {
					query.append(",history.sn_upto_date,history.sn_upto_time ");
				}
			}

			query.append(" from feedback_status_new as feedback");
			query
					.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query
					.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query
					.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			query
					.append(" inner join floor_room_detail as frd on frd.id = feedback.location ");
			if (!status.equalsIgnoreCase("Pending")) {
				query
						.append(" inner join feedback_status_history as history on feedback.id=history.feedId");
				query
						.append(" inner join employee_basic emp1 on history.action_by= emp1.id");
			}
			if (status != null && !status.equalsIgnoreCase("")
					&& !status.equalsIgnoreCase("All")) {
				query.append(" where feedback.status='" + status + "'");
			} else {
				query.append(" where feedback.id!=0 ");
			}
			if ((status.equalsIgnoreCase("Resolved")
					|| status.equalsIgnoreCase("Snooze") || status
					.equalsIgnoreCase("Ignore"))
					&& !"Yes".equalsIgnoreCase(reopen)) {
				query.append(" and history.status=feedback.status ");
			} else if (!status.equalsIgnoreCase("Pending")) {
				query.append(" and history.feedId=feedback.id ");
			}

			if (dataFlag != null && dataFlag.equals("T")) {
				if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("M")) {
					query.append(" and dept2.id='" + dept_subdeptid + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("H")) {
					query.append(" and feedtype.dept_subdept='"
							+ dept_subdeptid + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("N")) {
					query.append(" and subdept2.id='" + dept_subdeptid
							+ "' and feedback.feed_by='" + empname + "'");
				}
			} else if (dataFlag != null && dataFlag.equals("SC")) {
				if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("H")) {
					query.append(" and feedback.to_dept_subdept='"
							+ dept_subdeptid + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("N")) {
					query.append(" and subdept2.id='" + dept_subdeptid
							+ "' and feedback.feed_by='" + empname + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("C")) {
					query.append(" and feedtype.dept_subdept='"
							+ dept_subdeptid + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("SC")) {
					query.append(" and feedback.subCatg='" + dept_subdeptid
							+ "'");
				}
			}
			// For Levels
			else if (dataFlag != null && dataFlag.equals("L")) {
				if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("M")) {
					query.append(" and feedback.level='" + level + "'");
				}

				else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("H")) {
					query.append(" and dept2.id='" + dept_subdeptid
							+ "' and feedback.level='" + level + "'");
				}

				else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("N")) {
					query.append("  and feedback.feed_by='" + empname
							+ "' and feedback.level='" + level + "'");
				}
				if (reopen.equalsIgnoreCase("Yes")) {
					query.append(" and history.status='Re-OpenedR' ");
				}
			}
			// For Category
			else if (dataFlag != null && dataFlag.equals("C")) {
				query.append(" and feedback.subCatg='" + dept_subdeptid + "'");
			}
			// for location
			else if (dataFlag != null && dataFlag.equals("LD")
					|| dataFlag.equals("SD") || dataFlag.equals("TD")) {
				if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("H")) {
					query.append(" and feedback.to_dept_subdept='"
							+ dept_subdeptid + "'");
				} else if (dashFor != null && !dashFor.equals("")
						&& dashFor.equals("N")) {
					query.append(" and subdept2.id='" + dept_subdeptid
							+ "' and feedback.feed_by='" + empname + "'");
				}
				if (dataFlag.equals("LD")) {
					query.append(" and frd.floorname='" + level + "'");
				} else if (dataFlag.equals("SD")) {
					query.append(" and feedback.allot_to='" + level + "'");
				} else if (dataFlag.equals("TD")) {
					String[] a = level.split("-");
					query.append(" and feedback.open_time between '" + a[0]
							+ "' and '" + a[1] + "'");
				}

			}

			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			query.append(" AND feedback.open_date BETWEEN '"
					+ DateUtil.convertDateToUSFormat(fromDate) + "' AND '"
					+ DateUtil.convertDateToUSFormat(toDate) + "' ");
			query.append(" GROUP BY feedback.id ORDER BY feedback.id DESC");
			// //System.out.println("QQQQQQ   Padam      ::: " +
			// query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// dashboard data for location
	@SuppressWarnings("unchecked")
	public List getDataForDashboardLoc(String status, String fromDate,
			String toDate, String dashFor, String dept_subdeptid,
			String dataFlag, String module, String empname, String level,
			String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace, String dataCheck) {// //System.out.println("Emp name  :::::::::::::::::::>>>>>>>>>>>>>>> "+
		// empname);
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			query
					.append("select feedback.id,feedback.ticket_no,dept2.deptName as todept,feedtype.fbType as tosubdept,");
			query
					.append("emp.empName as allot_to,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,");
			query
					.append("frd.roomno,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,");
			query
					.append("feedback.open_date,feedback.open_time,feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,");
			query
					.append("feedback.level,feedback.status,feedback.feed_registerby ");
			/*
			 * if (!status.equalsIgnoreCase("Pending")) {query.append(
			 * ",history.action_date,history.action_time,history.action_duration,history.action_remark, emp1.empName as action_by "
			 * ); if (status.equalsIgnoreCase("Snooze")) {
			 * query.append(",history.sn_upto_date,history.sn_upto_time "); } }
			 */

			query.append(" from feedback_status_new as feedback");
			query
					.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			query
					.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
			query
					.append(" inner join department dept2 on dept2.id = feedback.to_dept_subdept");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
			query
					.append(" inner join floor_room_detail as frd on frd.id = feedback.location ");
			/*
			 * if (!status.equalsIgnoreCase("Pending")) {query.append(
			 * " inner join feedback_status_history as history on feedback.id=history.feedId"
			 * );query.append(
			 * " inner join employee_basic emp1 on history.action_by= emp1.id");
			 * }
			 */
			if (status != null && !status.equalsIgnoreCase("")) {
				query.append(" where feedback.status='" + status + "'");
			} else {
				query.append(" where feedback.id!=0 ");
			}
			/*
			 * if ((status.equalsIgnoreCase("Resolved") ||
			 * status.equalsIgnoreCase("Snooze") ||
			 * status.equalsIgnoreCase("Ignore")) &&
			 * !"Yes".equalsIgnoreCase(reopen)) { if(dataCheck!=null &&
			 * !dataCheck.equalsIgnoreCase("") &&
			 * !dataCheck.equalsIgnoreCase("reopen")){
			 * query.append(" and history.status=feedback.status "); }
			 * 
			 * } else if (!status.equalsIgnoreCase("Pending")) {
			 * query.append(" and history.feedId=feedback.id "); }
			 */

			if (dashFor != null && !dashFor.equals("") && dashFor.equals("H")) {
				query.append(" and feedback.to_dept_subdept='" + dept_subdeptid
						+ "'");
			} else if (dashFor != null && !dashFor.equals("")
					&& dashFor.equals("N")) {
				query.append(" and subdept2.id='" + dept_subdeptid
						+ "' and feedback.feed_by='" + empname + "'");
			}

			// for location & Time & Staff
			if (dataFlag != null && dataFlag.equals("LD")
					|| dataFlag.equals("SD") || dataFlag.equals("TD")) {

				if (dataFlag.equals("LD")) {
					query.append(" and frd.floorname='" + level + "'");
				} else if (dataFlag.equals("SD")) {
					query.append(" and feedback.allot_to='" + level + "'");
				} else if (dataFlag.equals("TD")) {
					String[] a = level.split("-");
					query.append(" and feedback.open_time between '" + a[0]
							+ "' and '" + a[1] + "'");
				}

			}
			if (dataCheck != null && !dataCheck.equalsIgnoreCase("")
					&& !"Location".equalsIgnoreCase(dataCheck)
					&& !status.equalsIgnoreCase("Pending")) {

				if (dataCheck != null && !dataCheck.equalsIgnoreCase("")
						&& dataCheck.equalsIgnoreCase("escalated")) {
					query.append(" and feedback.level>'Level1' ");
				} else if (dataCheck != null && !dataCheck.equalsIgnoreCase("")
						&& dataCheck.equalsIgnoreCase("reopen")) {
					query.append(" and history.status='Re-OpenedR' ");
				}

			}

			// for location Wing and room Data

			if (dataFlag != null && (dataFlag.equalsIgnoreCase("WAL"))) {
				query.append(" and frd.wingsname='" + level.split("KK")[0]
						+ "'");
				query.append(" and frd.floorname='" + level.split("KK")[1]
						+ "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("WAT")) {

				String[] a = level.split("KK");
				String[] b = a[0].split("-");
				query.append(" and frd.floorname='" + a[1] + "'");
				query.append(" and feedback.open_time between '" + b[0]
						+ "' and '" + b[1] + "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("WAS")) {

				query.append(" and feedback.allot_to='" + level.split("KK")[0]
						+ "'");
				query.append(" and frd.floorname='" + level.split("KK")[1]
						+ "'");

			}

			// for location Room and Time,Staff Data

			if (dataFlag != null && (dataFlag.equalsIgnoreCase("RAL"))) {
				query.append(" and frd.id='" + level.split("KK")[0] + "'");
				query.append(" and frd.wingsname='" + level.split("KK")[1]
						+ "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("RAT")) {

				String[] a = level.split("KK");
				String[] b = a[0].split("-");
				query.append(" and frd.wingsname='" + a[1] + "'");
				query.append(" and feedback.open_time between '" + b[0]
						+ "' and '" + b[1] + "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("RAS")) {

				query.append(" and feedback.allot_to='" + level.split("KK")[0]
						+ "'");
				query.append(" and frd.wingsname='" + level.split("KK")[1]
						+ "'");

			}

			// Time breakup data for Location And Staff

			if (dataFlag != null && (dataFlag.equalsIgnoreCase("TAL"))) {

				String[] a = level.split("KK");
				String[] b = a[1].split("-");
				query.append(" and frd.floorname='" + a[0] + "'");
				query.append(" and feedback.open_time between '" + b[0]
						+ "' and '" + b[1] + "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("TAS")) {

				String[] a = level.split("KK");
				String[] b = a[1].split("-");
				query.append(" and feedback.allot_to='" + a[0] + "'");
				query.append(" and feedback.open_time between '" + b[0]
						+ "' and '" + b[1] + "'");

			}

			// Staff break up data of location & time

			if (dataFlag != null && (dataFlag.equalsIgnoreCase("SAL"))) {
				query.append(" and frd.floorname='" + level.split("KK")[0]
						+ "'");
				query.append(" and feedback.allot_to='" + level.split("KK")[1]
						+ "'");

			} else if (dataFlag != null && dataFlag.equalsIgnoreCase("SAT")) {

				String[] a = level.split("KK");
				String[] b = a[1].split("-");
				query.append(" and feedback.allot_to='" + a[0] + "'");
				query.append(" and feedback.open_time between '" + b[0]
						+ "' and '" + b[1] + "'");

			}

			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			query.append(" AND feedback.open_date BETWEEN '"
					+ DateUtil.convertDateToUSFormat(fromDate) + "' AND '"
					+ DateUtil.convertDateToUSFormat(toDate) + "' ");
			query.append(" GROUP BY feedback.id ORDER BY feedback.id DESC");
			// //System.out.println("QQQQQQ   Padam      ::: " +
			// query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalData(String reportFor, String fromDate,
			String toDate, String dept, String tosubdept, String loginType,
			String empName, String searchField, String searchString,
			String searchOper, SessionFactory connectionSpace) {
		// //System.out.println(
		// "Inside category Method  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
		// );

		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (reportFor.equalsIgnoreCase("C")) {
				query
						.append("select subcatg.id,subcatg.subCategoryName,count(*) as counter from feedback_status_new as feedback");
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append("select emp.empName,count(*) as counter  from feedback_status as feedback");
			}

			query
					.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id ");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("C")) {
				query.append(" where  feedback.open_date BETWEEN '"
						+ DateUtil.convertDateToUSFormat(fromDate) + "' and '"
						+ DateUtil.convertDateToUSFormat(toDate) + "' ");
				if (loginType != null && loginType.equalsIgnoreCase("H")) {
					if (dept != null && !dept.equals("-1") && tosubdept != null
							&& tosubdept.equals("-1")) {
						query.append(" and  dept2.id=" + dept + " ");
					} else if (dept != null && !dept.equals("-1")
							&& tosubdept != null && !tosubdept.equals("-1")) {
						// query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="
						// + dept + ")");
						query.append(" and feedback.to_dept_subdept='"
								+ tosubdept + "'");
					}
				} else if (loginType != null && loginType.equalsIgnoreCase("N")) {
					query.append(" and feedback.feed_by='" + empName + "'");
				}
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" where feedback.moduleName='HDM' and open_date='"
						+ fromDate + "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null
						&& tosubdept.equals("-1")) {
					query.append(" and  dept2.id=" + dept + " ");
				} else if (dept != null && !dept.equals("-1")
						&& tosubdept != null && !tosubdept.equals("-1")) {
					query
							.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="
									+ dept + ")");
				}
			}

			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			if (reportFor.equalsIgnoreCase("C")) {
				if (tosubdept != null && tosubdept.equals("-1")) {
					query
							.append(" group by  subcatg.subCategoryName order by counter desc  limit 0,10");
				} else {
					query
							.append(" group by  subcatg.subCategoryName order by counter desc limit 0,15 ");
				}

			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append(" group by emp.empName order by counter desc  limit 0,10");
			}
			// //System.out.println("Category Query ::" + query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalReport(String reportFor, String fromDate,
			String toDate, String dept, String tosubdept, String searchField,
			String searchString, String searchOper,
			SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (reportFor.equalsIgnoreCase("C")) {
				query
						.append("select  catg.categoryName,count(*) as counter from feedback_status as feedback");
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append("select emp.empName,count(*) as counter  from feedback_status as feedback");
			}

			query
					.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
			query
					.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
			query
					.append(" inner join department dept1 on subdept1.deptid= dept1.id");
			query
					.append(" inner join department dept2 on subdept2.deptid= dept2.id");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("C")) {
				query.append(" where open_date between '" + fromDate
						+ "' and '" + toDate + "' ");
				if (dept != null && !dept.equals("-1") && tosubdept != null
						&& tosubdept.equals("-1")) {
					query.append(" and  dept2.id=" + dept + " ");
				} else if (dept != null && !dept.equals("-1")
						&& tosubdept != null && !tosubdept.equals("-1")) {
					query
							.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="
									+ dept + ")");
				}
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" where open_date between '" + fromDate
						+ "' and '" + toDate + "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null
						&& tosubdept.equals("-1")) {
					query.append(" and  dept2.id=" + dept + " ");
				} else if (dept != null && !dept.equals("-1")
						&& tosubdept != null && !tosubdept.equals("-1")) {
					query
							.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="
									+ dept + ")");
				}
			}

			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			if (reportFor.equalsIgnoreCase("C")) {
				query
						.append(" group by catg.categoryName order by counter desc");
			} else if (reportFor.equalsIgnoreCase("E")) {
				query.append(" group by emp.empName order by counter desc");
			}
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalGridReport(String reportFor, String fromDate,
			String toDate, String dept, String subdept, String searchField,
			String searchString, String searchOper,
			SessionFactory connectionSpace, String statusFor,
			String moduleName, String byDept) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			String tosubdept = null;
			// //System.out.println("subdept : " + subdept);
			if (subdept != null && !subdept.equalsIgnoreCase("null")
					&& subdept.equalsIgnoreCase("-1")) {
				tosubdept = "-1";

				// //System.out.println("tosubdept : " + tosubdept);
			} else {
				tosubdept = subdept;
			}

			if (reportFor.equalsIgnoreCase("Category")) {
				query
						.append("SELECT subcatg.id ,catg.categoryName,count(*) as counter,subcatg.subCategoryName ");
			} else if (reportFor.equalsIgnoreCase("Employee")) {
				query
						.append("SELECT  emp.id as employeeId,emp.empName,count(*) as counter");
			}
			if (moduleName != null && moduleName.equalsIgnoreCase("HDM")) {
				query.append(",subdept2.subdeptname ");
				query.append(" FROM feedback_status_new as feedback ");
				query
						.append(" INNER JOIN subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
				query
						.append(" INNER JOIN department dept2 on subdept2.deptid=dept2.id ");
				// query.append(" INNER JOIN department dept1 on feedback.by_dept_subdept= dept1.id");
			}

			query
					.append(" INNER JOIN feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" INNER JOIN feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("Employee")) {
				query
						.append(" INNER JOIN employee_basic emp on feedback.allot_to= emp.id");
			}

			query.append(" WHERE open_date between '" + fromDate + "' and '"
					+ toDate + "'");
			if (moduleName != null && moduleName.equalsIgnoreCase("HDM")) {
				if (dept != null && !dept.equals("-1") && tosubdept != null
						&& tosubdept.equals("-1")) {
					query.append(" AND  dept2.id=" + dept + " ");
				} else if (dept != null && !dept.equals("-1")
						&& tosubdept != null && !tosubdept.equals("-1")) {
					query.append(" AND subdept2.id=" + tosubdept + "  ");

					query.append(")");
				}
			}

			if (statusFor != null && statusFor.equalsIgnoreCase("Ontime")) {
				query
						.append(" AND feedback.level='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore' ");
			} else if (statusFor != null
					&& statusFor.equalsIgnoreCase("Offtime")) {
				query
						.append(" AND feedback.level!='Level1' AND feedback.status!='Snooze' AND feedback.status!='Ignore'  ");
			} else if (statusFor != null
					&& statusFor.equalsIgnoreCase("Snooze")) {
				query
						.append(" AND feedback.level='Level1' AND feedback.status='Snooze'  ");
			} else if (statusFor != null
					&& statusFor.equalsIgnoreCase("Ignore")) {
				query.append(" AND feedback.status='Ignore' ");
			}

			if (reportFor.equalsIgnoreCase("Category")) {
				if (byDept != null && !byDept.equalsIgnoreCase("-1")) {
					query.append(" AND dept1.id='" + byDept + "' ");
				}
				query
						.append(" GROUP BY feedback.subCatg ORDER BY counter desc");
			} else if (reportFor.equalsIgnoreCase("Employee")) {
				query.append(" GROUP BY emp.empName ORDER BY counter DESC");
			}
			// //System.out.println("QUERY IS ASSS  ::;   " + query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalData(String reportFor, String fromDate,
			String dept, String tosubdept, String loginType, String empName,
			String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace) {
		// //System.out.println(
		// "Inside category Method  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
		// );

		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (reportFor.equalsIgnoreCase("C")) {
				query
						.append("select subcatg.id,subcatg.subCategoryName,count(*) as counter from feedback_status_new as feedback");
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append("select emp.empName,count(*) as counter  from feedback_status as feedback");
			}

			query
					.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id ");
			query
					.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
			query
					.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			query
					.append(" inner join feedback_type feedType on catg.fbType = feedType.id");
			if (reportFor.equalsIgnoreCase("C")) {
				query.append(" where feedback.open_date='"
						+ DateUtil.convertDateToUSFormat(fromDate) + "'");
				if (loginType != null && loginType.equalsIgnoreCase("H")) {
					if (tosubdept != null && !tosubdept.equals("-1")) {
						query
								.append(" and feedType.dept_subdept in (select id from subdepartment where deptid="
										+ tosubdept + ")");
					}
				}

			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query
						.append(" where feedback.moduleName='HDM' and feedback.open_date='"
								+ DateUtil.convertDateToUSFormat(fromDate)
								+ "'");
				if (dept != null && !dept.equals("-1") && tosubdept != null
						&& tosubdept.equals("-1")) {
					query.append(" and  dept2.id=" + dept + " ");
				} else if (dept != null && !dept.equals("-1")
						&& tosubdept != null && !tosubdept.equals("-1")) {
					query
							.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="
									+ dept + ")");
				}
			}

			if (searchField != null && searchString != null
					&& !searchField.equalsIgnoreCase("")
					&& !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '" + searchString
							+ "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%" + searchString
							+ "'");
				}
			}
			if (reportFor.equalsIgnoreCase("C")) {
				query
						.append(" group by  subcatg.subCategoryName order by counter desc  limit 0,10");
			} else if (reportFor.equalsIgnoreCase("E")) {
				query
						.append(" group by emp.empName order by counter desc  limit 0,10");
			}
			// //System.out.println("Category Query ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: "
			// + query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getFeedbackDetail4Dashboard(String uid, String conditionValue,
			String fDate, String tDate, List subDeptList, String deptLevel,
			String searchField, String searchString, String searchOper,
			SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (deptLevel.equals("2")) {
				query
						.append("select feedback.id,feedback.ticket_no,dept1.deptName as bydept,subdept1.subdeptname as bysubdept,feedback.feed_by,feedback.feed_by_mobno,");
				query
						.append("feedback.feed_by_emailid,dept2.deptName as todept,subdept2.subdeptname as tosubdept,emp.empName,feedtype.fbType,catg.categoryName,");
				query
						.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query
						.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query
						.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query
						.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query
						.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by");
				/*
				 * if (field1value.equalsIgnoreCase("Resolved")) {query.append(
				 * ",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used"
				 * ); }
				 */
				query.append(" from feedback_status as feedback");
				if (conditionValue != null && conditionValue.equals("CL")) {
					query
							.append(" inner join employee_basic as emp on emp.mobOne=feedback.feed_by_mobno");
					query
							.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				} else if (conditionValue != null
						&& conditionValue.equals("CR")) {
					query
							.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
					query
							.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				} else if (conditionValue != null
						&& conditionValue.equals("HOD")) {
					query
							.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
					query
							.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
				} else if (conditionValue != null
						&& conditionValue.equals("MGMT")) {
					query
							.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				}
				query
						.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
				query
						.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
				query
						.append(" inner join department dept1 on subdept1.deptid= dept1.id");
				query
						.append(" inner join department dept2 on subdept2.deptid= dept2.id");
				query
						.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
				query
						.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
				query
						.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
				/*
				 * if (field1value.equalsIgnoreCase("Resolved")) {query.append(
				 * " inner join employee_basic emp1 on feedback.resolve_by= emp1.id"
				 * ); }
				 */

				if (conditionValue != null
						&& (conditionValue.equals("CL") || conditionValue
								.equals("CR"))) {
					query.append(" where userac.id='" + uid
							+ "' and open_date between '" + fDate + "'  and '"
							+ tDate + "'");
				} else if (conditionValue != null
						&& conditionValue.equals("HOD")) {
					query
							.append(" where userac.id='"
									+ uid
									+ "' and feed_stats.to_dept_subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id= emp.subdept) and open_date between '"
									+ fDate + "'  and '" + tDate + "') and");
				} else if (conditionValue != null
						&& conditionValue.equals("MGMT")) {
					query.append(" where open_date between '" + fDate
							+ "'  and '" + tDate + "' ");
				}
				query.append(" ORDER BY feedback.ticket_no DESC ");
			}
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int getDeptId(String user, SessionFactory connectionSpace) {
		int count = 0;
		List list = new ArrayList();
		String qry = null;
		try {
			qry = " select dept.id from department as dept"
					+ " inner join subdepartment as subdept on subdept.deptid = dept.id"
					+ " inner join employee_basic as emp on emp.subdept = subdept.id"
					+ " inner join useraccount as user on user.id = emp.useraccountid"
					+ " where user.userID = '" + user + "'";
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			count = Integer.parseInt(list.get(0).toString());
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,
			Map<String, Object> wherClause, Map<String, Object> order,
			SessionFactory connection) {
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select ");

		// Set the columns name of a table
		if (colmName != null && !colmName.equals("") && !colmName.isEmpty()) {
			int i = 1;
			for (String H : colmName) {
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}

		// Here we get the whole data of a table
		else {
			selectTableData.append(" * from " + tableName);
		}

		// Set the values for where clause
		if (wherClause != null && !wherClause.isEmpty()) {
			if (wherClause.size() > 0) {
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}

		// Set the value for type of order for getting data in specific order
		if (order != null && !order.isEmpty()) {
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " "
						+ me.getValue() + "");
			}
		}
		selectTableData.append(";");
		// //System.out.println("First Query QQQQ "+selectTableData.toString());
		Session session = null;
		Transaction transaction = null;
		try {
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		}
		return Data;
	}

	public void setdailyReportTags() {/*
									 * 
									 * 
									 * ConfigurationUtilBean obj;
									 * pageFieldsColumns = new
									 * ArrayList<ConfigurationUtilBean>();
									 * 
									 * List<GridDataPropertyView> deptList =
									 * Configuration.getConfigurationData(
									 * "mapped_dept_config_param",
									 * accountID,connectionSpace, "", 0,
									 * "table_name","dept_config_param"); if
									 * (deptList != null && deptList.size() > 0)
									 * { for (GridDataPropertyView gdv :
									 * deptList) { obj = new
									 * ConfigurationUtilBean(); if
									 * (gdv.getColomnName
									 * ().equalsIgnoreCase("deptName")) {
									 * obj.setKey(gdv.getColomnName());
									 * obj.setValue(gdv.getHeadingName());
									 * obj.setValidationType
									 * (gdv.getValidationType());
									 * obj.setColType("D");
									 * obj.setMandatory(true);
									 * pageFieldsColumns.add(obj); } } }
									 * 
									 * List<String> data = new
									 * ArrayList<String>();
									 * data.add("mappedid");
									 * data.add("table_name");
									 * 
									 * List<GridDataPropertyView>
									 * reportConfigList =
									 * Configuration.getConfigurationData
									 * ("mapped_daily_report_configuration"
									 * ,accountID, connectionSpace, "", 0,
									 * "table_name"
									 * ,"daily_report_configuration");
									 * reportconfigDDColumns = new
									 * ArrayList<ConfigurationUtilBean>();
									 * reportconfigDTColumns = new
									 * ArrayList<ConfigurationUtilBean>();
									 * reportconfigTimeColumns = new
									 * ArrayList<ConfigurationUtilBean>();
									 * reportconfigTextColumns = new
									 * ArrayList<ConfigurationUtilBean>();
									 * 
									 * if (reportConfigList != null &&
									 * reportConfigList.size() > 0) { for
									 * (GridDataPropertyView gdv :
									 * reportConfigList) { obj = new
									 * ConfigurationUtilBean(); if
									 * (gdv.getColomnName
									 * ().trim().equalsIgnoreCase("empId") ||
									 * gdv
									 * .getColomnName().trim().equalsIgnoreCase
									 * ("report_level")) {
									 * obj.setKey(gdv.getColomnName());
									 * obj.setValue(gdv.getHeadingName());
									 * obj.setValidationType
									 * (gdv.getValidationType());
									 * obj.setColType(gdv.getColType()); if
									 * (gdv.
									 * getMandatroy().toString().equals("1")) {
									 * obj.setMandatory(true); } else {
									 * obj.setMandatory(false); }
									 * pageFieldsColumns.add(obj); } else if
									 * (gdv
									 * .getColomnName().trim().equalsIgnoreCase
									 * ("report_type") ||
									 * gdv.getColomnName().trim
									 * ().equalsIgnoreCase("email_subject")) {
									 * obj.setKey(gdv.getColomnName());
									 * obj.setValue(gdv.getHeadingName());
									 * obj.setValidationType
									 * (gdv.getValidationType());
									 * obj.setColType(gdv.getColType()); if
									 * (gdv.
									 * getMandatroy().toString().equals("1")) {
									 * obj.setMandatory(true); } else {
									 * obj.setMandatory(false); }
									 * pageFieldsColumns.add(obj); } else if
									 * (gdv
									 * .getColomnName().trim().equalsIgnoreCase
									 * ("report_date") ||
									 * gdv.getColomnName().trim
									 * ().equalsIgnoreCase("report_time")) {
									 * obj.setKey(gdv.getColomnName());
									 * obj.setValue(gdv.getHeadingName());
									 * obj.setValidationType
									 * (gdv.getValidationType());
									 * obj.setColType(gdv.getColType()); if
									 * (gdv.
									 * getMandatroy().toString().equals("1")) {
									 * obj.setMandatory(true); } else {
									 * obj.setMandatory(false); }
									 * pageFieldsColumns.add(obj); }else if
									 * (gdv.
									 * getColomnName().trim().equalsIgnoreCase
									 * ("module")) {
									 * obj.setKey(gdv.getColomnName());
									 * obj.setValue(gdv.getHeadingName());
									 * obj.setValidationType
									 * (gdv.getValidationType());
									 * obj.setColType(gdv.getColType()); if
									 * (gdv.
									 * getMandatroy().toString().equals("1")) {
									 * obj.setMandatory(true); } else {
									 * obj.setMandatory(false); }
									 * pageFieldsColumns.add(obj); } } }
									 * 
									 * for (ConfigurationUtilBean ooo :
									 * pageFieldsColumns) {
									 * //System.out.println(ooo.getKey()); }
									 */
	}

	public void setReportTags() {
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration
				.getConfigurationData("mapped_report_panel_configuration",
						accountID, connectionSpace, "", 0, "table_name",
						"report_panel_configuration");
		reportconfigDDColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigDTColumns = new ArrayList<ConfigurationUtilBean>();
		reportconfigTimeColumns = new ArrayList<ConfigurationUtilBean>();

		if (reportConfigList != null && reportConfigList.size() > 0) {
			for (GridDataPropertyView gdv : reportConfigList) {
				ConfigurationUtilBean obj = new ConfigurationUtilBean();

				obj.setKey(gdv.getColomnName());
				obj.setValue(gdv.getHeadingName());
				obj.setValidationType(gdv.getValidationType());
				obj.setColType(gdv.getColType());
				if (gdv.getMandatroy().toString().equals("1")) {
					obj.setMandatory(true);
				} else {
					obj.setMandatory(false);
				}
				reportconfigDDColumns.add(obj);
			}
		}
	}

	public void setsubDept_DeptTags(String level) {
		List<GridDataPropertyView> deptList = Configuration
				.getConfigurationData("mapped_dept_config_param", accountID,
						connectionSpace, "", 0, "table_name",
						"dept_config_param");
		subDept_DeptLevelName = new ArrayList<ConfigurationUtilBean>();
		if (deptList != null && deptList.size() > 0) {
			for (GridDataPropertyView gdv : deptList) {
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					subDept_DeptLevelName.add(obj);
				}
			}
		}

		if (level.equals("2")) {
			List<GridDataPropertyView> subdept_deptList = Configuration
					.getConfigurationData("mapped_subdeprtmentconf", accountID,
							connectionSpace, "", 0, "table_name",
							"subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0) {
				for (GridDataPropertyView gdv1 : subdept_deptList) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname")) {
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

	public void setEmailConfigTags() {
		ConfigurationUtilBean obj;
		List<GridDataPropertyView> feedLodgeTags = Configuration
				.getConfigurationData("mapped_email_receiver_config",
						accountID, connectionSpace, "", 0, "table_name",
						"email_receiver_config");
		emailConfigTextColumns = new ArrayList<ConfigurationUtilBean>();
		if (feedLodgeTags != null && feedLodgeTags.size() > 0) {
			for (GridDataPropertyView gdv : feedLodgeTags) {
				obj = new ConfigurationUtilBean();
				obj.setKey(gdv.getColomnName());
				obj.setValue(gdv.getHeadingName());
				obj.setValidationType(gdv.getValidationType());
				obj.setColType(gdv.getColType());
				if (gdv.getMandatroy().toString().equals("1")) {
					obj.setMandatory(true);
				} else {
					obj.setMandatory(false);
				}
				emailConfigTextColumns.add(obj);
			}
		}
	}

	// Get Service Department List
	@SuppressWarnings("unchecked")
	public List getServiceDept_SubDept(String deptLevel, String orgLevel,
			String orgId, String module, SessionFactory connection) {

		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try {
			if (module.equalsIgnoreCase("COMPL")) {
				qry
						.append("select distinct dept.id,dept.deptName from compliance_task as compl");
				qry
						.append(" inner join department as dept on compl.departName=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel
						+ "' and dept.mappedOrgnztnId='" + orgId + "'");
			} else if (module.equalsIgnoreCase("HDM")) {
				qry
						.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				if (deptLevel.equals("2")) {
					qry
							.append(" inner join subdepartment as subdept on feed.dept_subdept=subdept.id");
					qry
							.append(" inner join department as dept on subdept.deptid=dept.id");
				} else if (deptLevel.equals("1")) {
					qry
							.append(" inner join department as dept on feed.dept_subdept=dept.id");
				}
				qry.append(" where dept.orgnztnlevel='" + orgLevel
						+ "' and dept.mappedOrgnztnId='" + orgId
						+ "' and feed.hide_show='Active' and feed.moduleName='"
						+ module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("ASTM")) {
				qry
						.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				if (deptLevel.equals("2")) {
					qry
							.append(" inner join subdepartment as subdept on feed.dept_subdept=subdept.id");
					qry
							.append(" inner join department as dept on subdept.deptid=dept.id");
				} else if (deptLevel.equals("1")) {
					qry
							.append(" inner join department as dept on feed.dept_subdept=dept.id");
				}
				qry.append(" where dept.orgnztnlevel='" + orgLevel
						+ "' and dept.mappedOrgnztnId='" + orgId
						+ "' and feed.hide_show='Active' and feed.moduleName='"
						+ module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("CASTM")) {
				qry
						.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				qry
						.append(" inner join department as dept on feed.dept_subdept=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel
						+ "' and dept.mappedOrgnztnId='" + orgId
						+ "' and feed.hide_show='Active' and feed.moduleName='"
						+ module + "' ORDER BY dept.deptName ASC");
			} else if (module.equalsIgnoreCase("FM")) {
				// Added by Avinash for FM
				qry
						.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
				qry
						.append(" inner join department as dept on feed.dept_subdept=dept.id");
				qry.append(" where dept.orgnztnlevel='" + orgLevel
						+ "' and dept.mappedOrgnztnId='" + orgId
						+ "' and feed.hide_show='Active' and feed.moduleName='"
						+ module + "' ORDER BY dept.deptName ASC");
			} else {
				qry
						.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append(" where orgnztnlevel='" + orgLevel
						+ "' and mappedOrgnztnId='" + orgId
						+ "' ORDER BY dept.deptName ASC");
			}
			// //System.out.println("qry " + qry);
			Session session = null;
			Transaction transaction = null;
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();

		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
		return dept_subdeptList;

	}

	@SuppressWarnings("unchecked")
	public List getParticularDepartment(String module, SessionFactory connection) {
		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try {
			if (module.equalsIgnoreCase("CASTM")) {
				qry
						.append("select distinct dept.id,dept.deptName from asset_detail as asset");
				qry
						.append(" inner join department as dept on asset.dept_subdept=dept.id");
			} else if (module.equalsIgnoreCase("HDM")) {
				qry
						.append("select distinct dept.id,dept.deptName from department as dept");
				qry
						.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
				qry.append(" order by dept.deptName");
			} else {
				qry
						.append("select distinct dept.id,dept.deptName from department as dept");
				qry.append(" order by dept.deptName");
			}
			// //System.out.println("Department Query  "+qry.toString());
			Session session = null;
			Transaction transaction = null;
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();

		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
		return dept_subdeptList;
	}

	@SuppressWarnings("unchecked")
	public List getReportData(String table, String searchfield,
			String searchfieldvalue, String orderfield, String order) {
		List SubdeptallList = new ArrayList();
		try {
			String subdeptall = "select * from " + table + " where "
					+ searchfield + "='" + searchfieldvalue + "' ORDER BY "
					+ orderfield + " " + order + "";
			SubdeptallList = new createTable().executeAllSelectQuery(
					subdeptall, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SubdeptallList;
	}

	/*
	 * @SuppressWarnings("unchecked") public List getLodgedTickets(String dept,
	 * String status_for, String empName, SessionFactory connectionSpace) { List
	 * list = new ArrayList(); StringBuilder query = new StringBuilder(""); try
	 * { if (status_for.equals("H")) { query .append(
	 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and status not in ('Resolved','Ignore','Transfer') and  to_dept_subdept in (select id from subdepartment where deptid="
	 * + dept + ")"); } else if (status_for.equals("M")) { query.append(
	 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and  status not in ('Resolved','Ignore','Transfer')"
	 * ); } else if (status_for.equals("N")) { query .append(
	 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and  status not in ('Resolved','Ignore','Transfer') and feed_by='"
	 * + empName + "'"); } //
	 * //System.out.println("Marquee Ticket Detail Query  is   "+query. //
	 * toString()); list = new
	 * createTable().executeAllSelectQuery(query.toString(), connectionSpace); }
	 * catch (Exception e) { e.printStackTrace(); } return list; }
	 */
	@SuppressWarnings("unchecked")
	public List getLodgedTickets(String dept, String status_for,
			String empName, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (status_for.equals("H")) {
				query
						.append("SELECT feedback.id,feedback.ticket_no,feedback.feed_by,feedback.open_date,");
				query
						.append("feedback.open_time,feedback.status,flrdet.roomno,");
				query
						.append("dept.deptName,emp.empName,dept1.deptName AS allottodept,subcatg.subCategoryName,catg.categoryName");
				query.append(" FROM feedback_status_new AS feedback");
				query
						.append(" LEFT JOIN department AS dept ON dept.id=feedback.by_dept_subdept");
				query
						.append(" LEFT JOIN employee_basic AS emp ON emp.id=feedback.allot_to");
				query
						.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id=feedback.subCatg");
				query
						.append(" LEFT JOIN feedback_category AS catg ON catg.id=subcatg.categoryName");
				query
						.append(" LEFT JOIN subdepartment AS subdept ON subdept.id=feedback.to_dept_subdept");
				query
						.append(" LEFT JOIN department AS dept1 ON dept1.id = subdept.deptid");
				query
						.append(" LEFT JOIN floor_room_detail AS flrdet ON flrdet.id = feedback.location");
				query
						.append(" WHERE  feedback.status not in ('Resolved','Ignore','Transfer') AND dept1.id="
								+ dept);

				/*
				 * query .append(
				 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and status not in ('Resolved','Ignore','Transfer') and  to_dept_subdept in (select id from subdepartment where deptid="
				 * + dept + ")");
				 */
			} else if (status_for.equals("M")) {
				query
						.append("SELECT feedback.id,feedback.ticket_no,feedback.feed_by,feedback.open_date,");
				query
						.append("feedback.open_time,feedback.status,flrdet.roomno,");
				query
						.append("dept.deptName,emp.empName,dept1.deptName AS allottodept,subcatg.subCategoryName,catg.categoryName");
				query.append(" FROM feedback_status_new AS feedback");
				query
						.append(" LEFT JOIN department AS dept ON dept.id=feedback.by_dept_subdept");
				query
						.append(" LEFT JOIN employee_basic AS emp ON emp.id=feedback.allot_to");
				query
						.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id=feedback.subCatg");
				query
						.append(" LEFT JOIN feedback_category AS catg ON catg.id=subcatg.categoryName");
				query
						.append(" LEFT JOIN subdepartment AS subdept ON subdept.id=feedback.to_dept_subdept");
				query
						.append(" LEFT JOIN department AS dept1 ON dept1.id = subdept.deptid");
				query
						.append(" LEFT JOIN floor_room_detail AS flrdet ON flrdet.id = feedback.location");
				query
						.append(" WHERE feedback.status not in ('Resolved','Ignore','Transfer')");

				/*
				 * query.append(
				 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and  status not in ('Resolved','Ignore','Transfer')"
				 * );
				 */
			} else if (status_for.equals("N")) {
				query
						.append("SELECT feedback.id,feedback.ticket_no,feedback.feed_by,feedback.open_date,");
				query
						.append("feedback.open_time,feedback.status,flrdet.roomno,");
				query
						.append("dept.deptName,emp.empName,dept1.deptName AS allottodept,subcatg.subCategoryName,catg.categoryName");
				query.append(" FROM feedback_status AS feedback");
				query
						.append(" LEFT JOIN department AS dept ON dept.id=feedback.by_dept_subdept");
				query
						.append(" LEFT JOIN employee_basic AS emp ON emp.id=feedback.allot_to");
				query
						.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id=feedback.subCatg");
				query
						.append(" LEFT JOIN feedback_category AS catg ON catg.id=subcatg.categoryName");
				query
						.append(" LEFT JOIN subdepartment AS subdept ON subdept.id=feedback.to_dept_subdept");
				query
						.append(" LEFT JOIN department AS dept1 ON dept1.id = subdept.deptid");
				query
						.append(" LEFT JOIN floor_room_detail AS flrdet ON flrdet.id = feedback.location");
				query
						.append(" WHERE feedback.status not in ('Resolved','Ignore','Transfer') feedback.feed_by='"
								+ empName + "'");

				/*
				 * query .append(
				 * "select id, ticket_no, feed_by, open_date, open_time, status from feedback_status where modulename='HDM' and  status not in ('Resolved','Ignore','Transfer') and feed_by='"
				 * + empName + "'");
				 */
			}
			// //System.out.println("Marquee Ticket Detail Query  is   "+query.
			// toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getLevelTickets(String dept, String status_for, String level,
			String dataIn, String empName, String ticket_status,
			SessionFactory connectionSpace, String fromDate, String toDate,
			String reopen) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (dataIn.equalsIgnoreCase("T")) {
				if (status_for.equals("H")) {
					if (ticket_status != null && !ticket_status.equals("")
							&& ticket_status.equals("All")) {
						query
								.append("select feed.status, count(*) from feedback_status_new as feed");
						if (reopen != null && reopen.equalsIgnoreCase("Yes")) {
							query
									.append(" INNER JOIN feedback_status_history feedhis on feedhis.feedId=feed.id");
						}
						query
								.append(" where feed.level='"
										+ level
										+ "'   and  feed.to_dept_subdept ='"
										+ dept
										+ "' and feed.open_date  between '"
										+ DateUtil
												.convertDateToUSFormat(fromDate)
										+ "' and '"
										+ DateUtil
												.convertDateToUSFormat(toDate)
										+ "' ");
						if (reopen != null && reopen.equalsIgnoreCase("Yes")) {
							query.append(" and feedhis.status='Re-OpenedR' ");
						}
						query.append(" group by feed.status ");
					}

				} else if (status_for.equals("M")) {
					if (ticket_status != null && !ticket_status.equals("")
							&& ticket_status.equals("All")) {
						query
								.append("select feed.status, count(*) from feedback_status_new as feed ");
						if (reopen != null && reopen.equalsIgnoreCase("Yes")) {
							query
									.append(" INNER JOIN feedback_status_history feedhis on feedhis.feedId=feed.id");
						}
						query
								.append(" where feed.level='"
										+ level
										+ "' and feed.open_date  between '"
										+ DateUtil
												.convertDateToUSFormat(fromDate)
										+ "' and '"
										+ DateUtil
												.convertDateToUSFormat(toDate)
										+ "' ");
						if (reopen != null && reopen.equalsIgnoreCase("Yes")) {
							query.append(" and feedhis.status='Re-OpenedR' ");
						}
						query.append(" group by feed.status ");
					}

				} else if (status_for.equals("N")) {
					if (ticket_status != null && !ticket_status.equals("")
							&& ticket_status.equals("All")) {
						query
								.append("select status, count(*) from feedback_status where feed_by='"
										+ empName
										+ "' and level='"
										+ level
										+ "'  and status not in ('Ignore','Resolved','Transfer') and moduleName='HDM' and open_date  between '"
										+ DateUtil
												.convertDateToUSFormat(fromDate)
										+ "' and '"
										+ DateUtil
												.convertDateToUSFormat(toDate)
										+ "' group by status ");
					} else if (ticket_status != null
							&& !ticket_status.equals("")
							&& ticket_status.equals("Resolved")) {
						query
								.append("select status, count(*) from feedback_status where feed_by='"
										+ empName
										+ "' and level='"
										+ level
										+ "' and status='Resolved' and moduleName='HDM' and resolve_date  between '"
										+ DateUtil
												.convertDateToUSFormat(fromDate)
										+ "' and '"
										+ DateUtil
												.convertDateToUSFormat(toDate)
										+ "' group by status ");
					}
				}
			} else if (dataIn.equalsIgnoreCase("G")) {
				if (status_for.equals("H")) {
					query
							.append("select level, count(*) from feedback_status where  open_date='"
									+ DateUtil.getCurrentDateUSFormat()
									+ "'  and status not in ('Ignore','Transfer') and  to_dept_subdept in (select id from subdepartment where deptid='"
									+ dept
									+ "') and moduleName='HDM'  group by level ");
				} else if (status_for.equals("M")) {
					query
							.append("select level, count(*) from feedback_status where  open_date='"
									+ DateUtil.getCurrentDateUSFormat()
									+ "'  and status not in ('Ignore','Transfer') and moduleName='HDM'  group by level ");
				} else if (status_for.equals("N")) {
					query
							.append("select level, count(*) from feedback_status where feed_by='"
									+ empName
									+ "' and open_date='"
									+ DateUtil.getCurrentDateUSFormat()
									+ "'  and status not in ('Ignore','Transfer') and moduleName='HDM'  group by level ");
				}
			}

			// System.out.println("Level Tickets Detail  :"+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String beforeLogeComplaintCall() {
		if (ValidateSession.checkSession()) {
			try {
				setfeedBackDraftTags(3);
				List<GridDataPropertyView> compliantColumnList = Configuration
						.getConfigurationData(
								"mapped_lodge_complaint_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "lodge_complaint_configuration");
				complaintDropMap = new ArrayList<ConfigurationUtilBean>();
				if (compliantColumnList != null
						&& compliantColumnList.size() > 0) {
					ConfigurationUtilBean obj = null;
					for (GridDataPropertyView gdp : compliantColumnList) {
						obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							complaintDropMap.add(obj);
						}

					}
				}
				/*
				 * deptList=new LinkedHashMap<Integer, String>();
				 * 
				 * List deptData=newcreateTable().executeAllSelectQuery(
				 * "SELECT id,deptName FROM department ORDER BY deptName ASC "
				 * ,connectionSpace ); if (deptData!=null && deptData.size()>0)
				 * { for (Iterator iterator = deptData.iterator();
				 * iterator.hasNext();) { Object object[] = (Object[])
				 * iterator.next(); if (object[0]!=null && object[1]!=null) {
				 * deptList.put(Integer.parseInt(object[0].toString()),
				 * object[1].toString()); } } }
				 */

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}

	// Method for getting the field Name for feedback Draft Module
	public void setfeedBackDraftTags(int flag) {
		// flag value is getting the employee level data based on the selected
		// no of employee configuration
		ConfigurationUtilBean obj;
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		if (flag > 0) {
			List<GridDataPropertyView> feedbackTypeList = Configuration
					.getConfigurationData("mapped_feedback_type_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"feedback_type_configuration");
			feedbackTypeColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackTypeList != null && feedbackTypeList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackTypeList) {
					if (!gdv.getColomnName().equals("moduleName")) {
						obj = new ConfigurationUtilBean();
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						feedbackTypeColumns.add(obj);
					}
				}
			}
		}
		if (flag > 1) {
			List<GridDataPropertyView> feedbackCategoryList = Configuration
					.getConfigurationData(
							"mapped_feedback_category_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"feedback_category_configuration");
			feedbackCategoryColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackCategoryList != null && feedbackCategoryList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackCategoryList) {
					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("T")
							|| gdv.getColType().trim().equalsIgnoreCase("D")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						feedbackCategoryColumns.add(obj);
					}
				}
			}
		}
		if (flag > 2) {
			List<GridDataPropertyView> feedbackSubCategoryList = Configuration
					.getConfigurationData(
							"mapped_feedback_subcategory_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"feedback_subcategory_configuration");
			feedbackSubCategoryDDColumns = new ArrayList<ConfigurationUtilBean>();
			feedbackSubCategoryTextColumns = new ArrayList<ConfigurationUtilBean>();
			feedbackSubCategoryTimeColumns = new ArrayList<ConfigurationUtilBean>();
			if (feedbackSubCategoryList != null
					&& feedbackSubCategoryList.size() > 0) {
				for (GridDataPropertyView gdv : feedbackSubCategoryList) {

					obj = new ConfigurationUtilBean();
					if (gdv.getColType().trim().equalsIgnoreCase("D")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						feedbackSubCategoryDDColumns.add(obj);
					} else if (gdv.getColType().trim().equalsIgnoreCase("T")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						feedbackSubCategoryTextColumns.add(obj);
					} else if (gdv.getColType().trim().equalsIgnoreCase("Time")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						feedbackSubCategoryTimeColumns.add(obj);
					}
				}
			}
		}
	}

	public JSONArray getAllSubDeptByDeptId(String deptId,
			SessionFactory connectionSpace) {

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		// Map<Integer, String> subdeptMap = new LinkedHashMap<Integer,
		// String>();
		JSONArray subdeptMap = new JSONArray();
		List dataList = null;
		try {
			qryString
					.append("select id,subdeptname from subdepartment where status='Active' && deptid="
							+ deptId + " order by subdeptname asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(),
					connectionSpace);
			if (dataList != null && dataList.size() > 0) {
				for (Iterator iterator = dataList.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null
							&& !objectCol[1].toString().equals("")) {
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						subdeptMap.add(jsonObj);
					}

				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return subdeptMap;
	}

	public String checkMethod() {
		return SUCCESS;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSubcatg() {
		return subcatg;
	}

	public void setSubcatg(String subcatg) {
		this.subcatg = subcatg;
	}

	public FeedbackDraftPojo getFeedDraft() {
		return feedDraft;
	}

	public void setFeedDraft(FeedbackDraftPojo feedDraft) {
		this.feedDraft = feedDraft;
	}

	public String getDeptOrSubDeptId() {
		return deptOrSubDeptId;
	}

	public void setDeptOrSubDeptId(String deptOrSubDeptId) {
		this.deptOrSubDeptId = deptOrSubDeptId;
	}

	public int getLevelOfFeedDraft() {
		return levelOfFeedDraft;
	}

	public void setLevelOfFeedDraft(int levelOfFeedDraft) {
		this.levelOfFeedDraft = levelOfFeedDraft;
	}

	public String getLodgeFeedback() {
		return lodgeFeedback;
	}

	public void setLodgeFeedback(String lodgeFeedback) {
		this.lodgeFeedback = lodgeFeedback;
	}

	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}

	public boolean isCheckdept() {
		return checkdept;
	}

	public void setCheckdept(boolean checkdept) {
		this.checkdept = checkdept;
	}

	public Map<Integer, String> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList) {
		this.subDeptList = subDeptList;
	}

	public List<ConfigurationUtilBean> getSubDept_DeptLevelName() {
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(
			List<ConfigurationUtilBean> subDept_DeptLevelName) {
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public int getLevelOfSurvey() {
		return levelOfSurvey;
	}

	public void setLevelOfSurvey(int levelOfSurvey) {
		this.levelOfSurvey = levelOfSurvey;
	}

	public String getRoasterDownload() {
		return roasterDownload;
	}

	public void setRoasterDownload(String roasterDownload) {
		this.roasterDownload = roasterDownload;
	}

	public String getFeedDarftDownload() {
		return feedDarftDownload;
	}

	public void setFeedDarftDownload(String feedDarftDownload) {
		this.feedDarftDownload = feedDarftDownload;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public Map<Integer, String> getDownloadSubDeptList() {
		return downloadSubDeptList;
	}

	public void setDownloadSubDeptList(Map<Integer, String> downloadSubDeptList) {
		this.downloadSubDeptList = downloadSubDeptList;
	}

	public String getDaily_report() {
		return daily_report;
	}

	public void setDaily_report(String daily_report) {
		this.daily_report = daily_report;
	}

	public List<ConfigurationUtilBean> getComplanantTextColumns() {
		return complanantTextColumns;
	}

	public void setComplanantTextColumns(
			List<ConfigurationUtilBean> complanantTextColumns) {
		this.complanantTextColumns = complanantTextColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigDDColumns() {
		return reportconfigDDColumns;
	}

	public void setReportconfigDDColumns(
			List<ConfigurationUtilBean> reportconfigDDColumns) {
		this.reportconfigDDColumns = reportconfigDDColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigDTColumns() {
		return reportconfigDTColumns;
	}

	public void setReportconfigDTColumns(
			List<ConfigurationUtilBean> reportconfigDTColumns) {
		this.reportconfigDTColumns = reportconfigDTColumns;
	}

	public List<ConfigurationUtilBean> getReportconfigTimeColumns() {
		return reportconfigTimeColumns;
	}

	public void setReportconfigTimeColumns(
			List<ConfigurationUtilBean> reportconfigTimeColumns) {
		this.reportconfigTimeColumns = reportconfigTimeColumns;
	}

	public String getDept_subdept() {
		return dept_subdept;
	}

	public void setDept_subdept(String dept_subdept) {
		this.dept_subdept = dept_subdept;
	}

	public Map<Integer, String> getEmpList() {
		return empList;
	}

	public void setEmpList(Map<Integer, String> empList) {
		this.empList = empList;
	}

	public String getAddressTime() {
		return addressTime;
	}

	public void setAddressTime(String addressTime) {
		this.addressTime = addressTime;
	}

	public String getEscTime() {
		return escTime;
	}

	public void setEscTime(String escTime) {
		this.escTime = escTime;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getTicketflag() {
		return ticketflag;
	}

	public void setTicketflag(String ticketflag) {
		this.ticketflag = ticketflag;
	}

	public List<ConfigurationUtilBean> getReportconfigTextColumns() {
		return reportconfigTextColumns;
	}

	public void setReportconfigTextColumns(
			List<ConfigurationUtilBean> reportconfigTextColumns) {
		this.reportconfigTextColumns = reportconfigTextColumns;
	}

	public List<ConfigurationUtilBean> getEmailConfigTextColumns() {
		return emailConfigTextColumns;
	}

	public void setEmailConfigTextColumns(
			List<ConfigurationUtilBean> emailConfigTextColumns) {
		this.emailConfigTextColumns = emailConfigTextColumns;
	}

	public JSONArray getEmpArray() {
		return empArray;
	}

	public void setEmpArray(JSONArray empArray) {
		this.empArray = empArray;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getSelectVar_One() {
		return selectVar_One;
	}

	public void setSelectVar_One(String selectVar_One) {
		this.selectVar_One = selectVar_One;
	}

	public String getSelectVar_Two() {
		return selectVar_Two;
	}

	public void setSelectVar_Two(String selectVar_Two) {
		this.selectVar_Two = selectVar_Two;
	}

	public String getConditionVar_Key() {
		return conditionVar_Key;
	}

	public void setConditionVar_Key(String conditionVar_Key) {
		this.conditionVar_Key = conditionVar_Key;
	}

	public String getConditionVar_Value() {
		return conditionVar_Value;
	}

	public void setConditionVar_Value(String conditionVar_Value) {
		this.conditionVar_Value = conditionVar_Value;
	}

	public String getConditionVar_Key2() {
		return conditionVar_Key2;
	}

	public void setConditionVar_Key2(String conditionVar_Key2) {
		this.conditionVar_Key2 = conditionVar_Key2;
	}

	public String getConditionVar_Value2() {
		return conditionVar_Value2;
	}

	public void setConditionVar_Value2(String conditionVar_Value2) {
		this.conditionVar_Value2 = conditionVar_Value2;
	}

	public String getOrder_Key() {
		return order_Key;
	}

	public void setOrder_Key(String order_Key) {
		this.order_Key = order_Key;
	}

	public String getOrder_Value() {
		return order_Value;
	}

	public void setOrder_Value(String order_Value) {
		this.order_Value = order_Value;
	}

	public JSONArray getCommonJSONArray() {
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray) {
		this.commonJSONArray = commonJSONArray;
	}

	public String getDataFor() {
		return dataFor;
	}

	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public List<ConfigurationUtilBean> getComplaintDropMap() {
		return complaintDropMap;
	}

	public void setComplaintDropMap(List<ConfigurationUtilBean> complaintDropMap) {
		this.complaintDropMap = complaintDropMap;
	}

	public List<ConfigurationUtilBean> getFeedbackTypeColumns() {
		return feedbackTypeColumns;
	}

	public void setFeedbackTypeColumns(
			List<ConfigurationUtilBean> feedbackTypeColumns) {
		this.feedbackTypeColumns = feedbackTypeColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackCategoryColumns() {
		return feedbackCategoryColumns;
	}

	public void setFeedbackCategoryColumns(
			List<ConfigurationUtilBean> feedbackCategoryColumns) {
		this.feedbackCategoryColumns = feedbackCategoryColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryDDColumns() {
		return feedbackSubCategoryDDColumns;
	}

	public void setFeedbackSubCategoryDDColumns(
			List<ConfigurationUtilBean> feedbackSubCategoryDDColumns) {
		this.feedbackSubCategoryDDColumns = feedbackSubCategoryDDColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryTextColumns() {
		return feedbackSubCategoryTextColumns;
	}

	public void setFeedbackSubCategoryTextColumns(
			List<ConfigurationUtilBean> feedbackSubCategoryTextColumns) {
		this.feedbackSubCategoryTextColumns = feedbackSubCategoryTextColumns;
	}

	public List<ConfigurationUtilBean> getFeedbackSubCategoryTimeColumns() {
		return feedbackSubCategoryTimeColumns;
	}

	public void setFeedbackSubCategoryTimeColumns(
			List<ConfigurationUtilBean> feedbackSubCategoryTimeColumns) {
		this.feedbackSubCategoryTimeColumns = feedbackSubCategoryTimeColumns;
	}

	public String getReopen() {
		return reopen;
	}

	public void setReopen(String reopen) {
		this.reopen = reopen;
	}

}
