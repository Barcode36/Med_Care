package com.Over2Cloud.common.compliance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceEditGridAction;
import com.opensymphony.xwork2.ActionContext;

public class ComplianceCommonOperation
{

	static final Log log = LogFactory.getLog(ComplianceCommonOperation.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	@SuppressWarnings("rawtypes")
	public List getTaskTypeDetails(String uniqueId, String tableName)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("SELECT ct.msg,ct.taskBrief,cty.taskType,ct.budgetary ");
			qryString.append(" FROM compliance_task as ct INNER JOIN compl_task_type cty ON cty.id=ct.taskType");
			qryString.append(" WHERE ct.id=");
			qryString.append(uniqueId);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public String getTaskDepartmentOrSubDepart(String uniqueId, String fieldName, String tableName)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		String departmentName = null;
		try
		{
			if (uniqueId != null && tableName != null && fieldName != null)
			{
				qryString.append("select ");
				qryString.append(fieldName);
				qryString.append(" from ");
				qryString.append(tableName);
				qryString.append(" where id=");
				qryString.append("'");
				qryString.append(uniqueId);
				qryString.append("'");
			}
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object) iterator.next();
					if (objectCol.toString() != null || !objectCol.toString().equals(""))
					{
						departmentName = objectCol.toString();
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskDepartment of class " + getClass(), exp);
		}
		return departmentName;
	}

	@SuppressWarnings("rawtypes")
	public List getDeptDetailBySubdeptId(String uniqueId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("select deptName,id from department where id IN (select deptid from subdepartment where id=" + uniqueId + ")");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getDeptDetailBySubdeptId of class " + getClass(), exp);
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getSubdeptIdByDeptDetail(String uniqueId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("select deptName,id from department where id IN (select deptid from subdepartment where id=" + uniqueId + ")");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getDeptDetailBySubdeptId of class " + getClass(), exp);
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public String getTaskTypeName(String uniqueId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		String takTypeName = null;
		try
		{
			qryString.append("select taskType from compl_task_type");
			qryString.append(" where id=");
			qryString.append("'");
			qryString.append(uniqueId);
			qryString.append("'");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object) iterator.next();
					if (objectCol.toString() != null || !objectCol.toString().equals(""))
					{
						takTypeName = objectCol.toString();
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeName of class " + getClass(), exp);
		}
		return takTypeName;
	}

	@SuppressWarnings("rawtypes")
	public List getEmployeeDetails(String uniqueId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("select id,mobOne,emailIdOne from employee_basic");
			qryString.append(" where id=");
			qryString.append("'");
			qryString.append(uniqueId);
			qryString.append("'");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getTaskName(String taskDeptID)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		Map<String, String> taskMap = new LinkedHashMap<String, String>();
		List dataList = null;
		try
		{
			Object[] objectCol = null;
			qryString.append("select id,taskName from compliance_task");
			qryString.append(" where departName='");
			qryString.append(taskDeptID);
			qryString.append("' order by taskName asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						taskMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return taskMap;
	}

	@SuppressWarnings("rawtypes")
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
		finally
		{
		}
		return Data;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getAllDepartment()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		Map<Integer, String> taskMap = new LinkedHashMap<Integer, String>();
		List dataList = null;
		try
		{
			qryString.append("select id,deptName from department order by deptName asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						taskMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return taskMap;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getAllContMappedDepartment(String deptId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		Map<Integer, String> taskMap = new LinkedHashMap<Integer, String>();
		List dataList = null;
		try
		{
			qryString.append("SELECT distinct dept.id,dept.deptName FROM compliance_contacts AS cc ");
			qryString.append("LEFT JOIN department AS dept ON dept.id=cc.fromDept_id WHERE cc.forDept_id=" + deptId + " AND cc.moduleName='COMPL' AND cc.work_status!=1 ORDER BY dept.deptName");

			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						taskMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return taskMap;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getCompAllotedDepartment()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder remindFor = new StringBuilder();
		Map<Integer, String> deptMap = new LinkedHashMap<Integer, String>();
		List empList = null;
		try
		{
			String qryString = "SELECT reminder_for FROM compliance_details";
			empList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (empList != null && empList.size() > 0)
			{
				for (int i = 0; i < empList.size(); i++)
				{
					remindFor.append(empList.get(i));
				}
				String empId = remindFor.toString().replace("#", ",").substring(0, (remindFor.toString().length() - 1));
				String qry = "SELECT dept.id,dept.deptName FROM department AS dept INNER JOIN compliance_contacts AS compcont ON dept.id=compcont.fromDept_id WHERE compcont.emp_id IN(" + empId + ") AND moduleName='COMPL' GROUP BY dept.deptName ORDER BY dept.deptName ASC";
				List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object objectCol[] = null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						objectCol = (Object[]) iterator.next();
						if (objectCol[0] != null && objectCol[1] != null && !objectCol[1].toString().equals(""))
						{
							deptMap.put((Integer) objectCol[0], objectCol[1].toString());
						}
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getCompAllotedDepartment of class " + getClass(), exp);
		}
		return deptMap;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, String> getEmpDetailByDeptId(String deptId)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		Map<Integer, String> empMap = new LinkedHashMap<Integer, String>();
		List dataList = null;
		try
		{
			qryString.append("SELECT emp.id,emp.empName FROM employee_basic AS emp " + "INNER JOIN compliance_contacts AS compcont ON emp.id=compcont.emp_id WHERE fromDept_id=" + deptId + " AND moduleName='COMPL' order by emp.empName asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getEmpDetailByDeptId of class " + getClass(), exp);
		}
		return empMap;
	}

	@SuppressWarnings(
	{
		"unchecked"
	})
	public Map<String, String> getComplianceContacts(String uId, String deptId, String moduleName)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE cc.id NOT IN(" + uId + ") AND forDept_id=" + deptId + " AND moduleName='" + moduleName + "' AND work_status!=1 order by empName asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getComplianceAllContacts(String deptId, String moduleName)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE forDept_id=" + deptId + " AND moduleName='" + moduleName + "' AND work_status!=1 order by empName asc");

			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getCompNextEsc(String empIds, String deptId, String moduleName)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.empName FROM employee_basic AS emp");
			qryString.append(" INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE cc.id NOT IN(" + empIds + ") AND forDept_id=" + deptId + " AND moduleName='" + moduleName + "' AND work_status!=1 order by empName asc");

			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	@SuppressWarnings("rawtypes")
	public List getExistComplDetails(String cmplID, SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("SELECT ctt.taskType,cpd.taskBrief,cpd.dueDate,cpd.reminder_for,dept.deptName,cpd.dueTime,cpd.frequency,cpd.actionTaken,cpd.budgetary,ctn.id,cpd.remind_days ");
			qryString.append(",cpd.action_type ,cpd.current_esc_level,cpd.escalate_date,cpd.escalate_time,cpd.escalation_level_1 ");
			qryString.append(",cpd.escalation_level_2,cpd.escalation_level_3,cpd.escalation_level_4,cpd.uploadDoc,cpd.uploadDoc1 ");
			qryString.append(" FROM compliance_details AS cpd INNER JOIN compliance_task ctn ON ctn.id=cpd.taskName LEFT JOIN department dept ON dept.id=ctn.departName LEFT JOIN compl_task_type ctt ON ctt.id=ctn.taskType WHERE cpd.id= ");
			qryString.append("'");
			qryString.append(cmplID);
			qryString.append("'");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connection);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getExistComplDetails of class " + getClass(), exp);
		}
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public List getExistComplDetails1111(String cmplID)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		try
		{
			qryString.append("select id,taskName,taskType,taskBrief,frequency,escalation,dueDate,dueTime from compliance_details where actionStatus='Pending'");
			qryString.append(" and id=");
			qryString.append("'");
			qryString.append(cmplID);
			qryString.append("'");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getExistComplDetails of class " + getClass(), exp);
		}
		return dataList;
	}

	// find the maximum compliance id.
	@SuppressWarnings("unchecked")
	public int getMaximumComplId()
	{

		List complList = null;
		int maxComplId = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		qryString.append("select max(id) from compliance_details");
		complList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		for (int i = 0; i < complList.size(); i++)
		{
			maxComplId = (Integer) complList.get(0);
		}
		return maxComplId;
	}

	// saveComplReminder
	public boolean saveComplReminder(Map<String, String> dataWithColumnName)
	{
		// getting user from session

		boolean status = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		// Code for creating table dynamically
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		// add some extra fields
		TableColumes ob2 = new TableColumes();
		// employee id
		ob2 = new TableColumes();
		ob2.setColumnname("reminder_name");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		ob2 = new TableColumes();
		ob2.setColumnname("reminder_code");
		ob2.setDatatype("varchar(10)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openDate
		ob2 = new TableColumes();
		ob2.setColumnname("due_date");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);
		// openDate
		ob2 = new TableColumes();
		ob2.setColumnname("due_time");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("remind_date");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);
		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("remind_time");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		ob2 = new TableColumes();
		ob2.setColumnname("remind_interval");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// openTime
		ob2 = new TableColumes();
		ob2.setColumnname("compliance_id");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// set status
		ob2 = new TableColumes();
		ob2.setColumnname("reminder_status");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		// set status
		ob2 = new TableColumes();
		ob2.setColumnname("status");
		ob2.setDatatype("varchar(255)");
		ob2.setConstraint("default NULL");
		Tablecolumesaaa.add(ob2);

		status = cbt.createTable22("compliance_reminder", Tablecolumesaaa, connectionSpace);
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		Iterator<Entry<String, String>> hiterator = dataWithColumnName.entrySet().iterator();
		while (hiterator.hasNext())
		{
			Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
			ob = new InsertDataTable();
			ob.setColName(paramPair.getKey().toString());
			ob.setDataName(paramPair.getValue().toString());
			insertData.add(ob);
		}
		status = cbt.insertIntoTable("compliance_reminder", insertData, connectionSpace);
		return status;
	}

	public String getComplianceCount()
	{
		return accountID;
	}

	@SuppressWarnings("rawtypes")
	public String getDepartmentForCompliance(String userActID, SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String departmentIS = null;
		List dataList = new ArrayList();
		try
		{
			qryString.append("select deptName from department where id=(select deptid from subdepartment where id=(select subdept from employee_basic where useraccountid=");
			qryString.append("'");
			qryString.append(userActID);
			qryString.append("'");
			qryString.append("))");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object) iterator.next();
					if (objectCol.toString() != null)
					{
						departmentIS = objectCol.toString();
					}
				}
				return departmentIS;
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return departmentIS;
	}

	@SuppressWarnings("rawtypes")
	public String getDepartmentIDForCompliance(String userActID, SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		String departmentIS = null;
		List dataList = new ArrayList();
		try
		{
			qryString.append("select deptid from subdepartment where id=(select subdept from employee_basic");
			qryString.append(" where useraccountid='");
			qryString.append(userActID);
			qryString.append("'");
			qryString.append(")");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object) iterator.next();
					if (objectCol.toString() != null)
					{
						departmentIS = objectCol.toString();
					}
				}
				return departmentIS;
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return departmentIS;
	}

	@SuppressWarnings("rawtypes")
	public String getCounterForDashboard(String userActID, String time, SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String currentWeek = null;
		List dataList = new ArrayList();
		try
		{
			StringBuilder mailQuery = new StringBuilder();
			mailQuery.append("select count(*) from compliance_details where dueDate");
			if (time.equals("currentWeek"))
			{
				String weekDate = DateUtil.getNextDateAfter(7);
				mailQuery.append(" between ");
				mailQuery.append("'");
				mailQuery.append(DateUtil.getCurrentDateUSFormat());
				mailQuery.append("'");
				mailQuery.append(" and ");
				mailQuery.append("'");
				mailQuery.append(weekDate);
				mailQuery.append("'");
			}
			else if (time.equals("currentMonth"))
			{
				String weekDate = DateUtil.getNextDateAfter(15);
				mailQuery.append(" between ");
				mailQuery.append("'");
				mailQuery.append(DateUtil.getCurrentDateUSFormat());
				mailQuery.append("'");
				mailQuery.append(" and ");
				mailQuery.append("'");
				mailQuery.append(weekDate);
				mailQuery.append("'");
			}
			else if (time.equals("currentYear"))
			{
				String weekDate = DateUtil.getNextDateAfter(1);
				mailQuery.append(" between ");
				mailQuery.append("'");
				mailQuery.append(DateUtil.getCurrentDateUSFormat());
				mailQuery.append("'");
				mailQuery.append(" and ");
				mailQuery.append("'");
				mailQuery.append(weekDate);
				mailQuery.append("'");
			}

			dataList = cbt.executeAllSelectQuery(mailQuery.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				currentWeek = dataList.get(0).toString();
			}
			return currentWeek;
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return currentWeek;
	}

	// METHOD FOR DASHBOARD
	@SuppressWarnings("rawtypes")
	public List<ComplianceDashboardBean> getComplianceDetailForDasgboard(SessionFactory connection)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		ComplianceDashboardBean dashBean = new ComplianceDashboardBean();
		try
		{
			/* NEW */
			StringBuilder qryString = new StringBuilder();
			qryString.append("select deptHierarchy from compliance_details");
			List hierarchyList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			String hierarchyString = hierarchyList.get(0).toString();

			StringBuilder queryString = new StringBuilder();
			String deptName = null;
			queryString.append("select distinct dept_subdept from compliance_details");
			List depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			for (int d = 0; d < depList.size(); d++)
			{
				dashBean = new ComplianceDashboardBean();
				if (hierarchyString.equalsIgnoreCase("2"))
				{
					deptName = getTaskDepartmentOrSubDepart(depList.get(d).toString(), "deptName", "department");
				}
				else if (hierarchyString.equalsIgnoreCase("1"))
				{
					deptName = getTaskDepartmentOrSubDepart(depList.get(d).toString(), "subdeptname", "subdepartment");
				}
				String currentWeekCount = cmnOper.getCounterOfCompliance("CurrentWeek", depList.get(d).toString(), connectionSpace);
				String currentMonthCount = cmnOper.getCounterOfCompliance("CurrentMonth", depList.get(d).toString(), connectionSpace);
				String currentYearCount = cmnOper.getCounterOfCompliance("CurrentYear", depList.get(d).toString(), connectionSpace);
				dashBean.setDepartId(depList.get(d).toString());
				dashBean.setDepartName(deptName);
				dashBean.setDueWeekly(currentWeekCount);
				dashBean.setDueMonthly(currentMonthCount);
				dashBean.setDueYearly(currentYearCount);
				finalDataList.add(dashBean);
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceDetailForDasgboard of class " + getClass(), exp);
		}
		return finalDataList;
	}

	@SuppressWarnings("rawtypes")
	public String getCounterOfCompliance(String currentTime, String deptOrSubDeptID, SessionFactory connsSpace)
	{

		String returnString = null;
		try
		{
			List tempList = new ArrayList();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder qry = new StringBuilder();
			if (currentTime.equalsIgnoreCase("CurrentWeek") || currentTime.equalsIgnoreCase("CurrentMonth") || currentTime.equalsIgnoreCase("CurrentYear"))
			{
				qry.append("select count(*) from compliance_details where dueDate");
			}
			if (currentTime != null && currentTime != "" && currentTime.equalsIgnoreCase("CurrentWeek"))
			{
				String date = DateUtil.getNextDateAfter(7);
				qry.append(" between ");
				qry.append("'");
				qry.append(DateUtil.getCurrentDateUSFormat());
				qry.append("'");
				qry.append(" and ");
				qry.append("'");
				qry.append(date);
				qry.append("'");
				qry.append(" and frequency=");
				qry.append("'");
				qry.append("W");
				qry.append("'");

			}
			else if (currentTime != null && currentTime != "" && currentTime.equalsIgnoreCase("CurrentMonth"))
			{
				String nextUpdateDate = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
				qry.append(" between ");
				qry.append("'");
				qry.append(DateUtil.getCurrentDateUSFormat());
				qry.append("'");
				qry.append(" and ");
				qry.append("'");
				qry.append(nextUpdateDate);
				qry.append("'");
				qry.append(" and frequency=");
				qry.append("'");
				qry.append("M");
				qry.append("'");
			}
			else if (currentTime != null && currentTime != "" && currentTime.equalsIgnoreCase("CurrentYear"))
			{
				String nextUpdateDate = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
				qry.append(" between ");
				qry.append("'");
				qry.append(DateUtil.getCurrentDateUSFormat());
				qry.append("'");
				qry.append(" and ");
				qry.append("'");
				qry.append(nextUpdateDate);
				qry.append("'");
				qry.append(" and frequency=");
				qry.append("'");
				qry.append("Y");
				qry.append("'");
			}
			qry.append(" and ");
			qry.append(" actionStatus=");
			qry.append("'");
			qry.append("Pending");
			qry.append("'");
			qry.append(" and ");
			qry.append(" dept_subdept=");
			qry.append("'");
			qry.append(deptOrSubDeptID);
			qry.append("'");
			tempList = cbt.executeAllSelectQuery(qry.toString(), connsSpace);
			if (tempList != null && tempList.size() > 0)
			{
				returnString = (String) tempList.get(0).toString();
			}
			return returnString;
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return returnString;
	}

	@SuppressWarnings("rawtypes")
	public List getEmpDataByUserName(String uName, String deptLevel, SessionFactory connection)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			if (deptLevel.equals("2"))
			{
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,sdept.id as sdeptid,dept.id, dept.deptName from employee_basic as emp" + " inner join subdepartment as sdept on emp.subdept=sdept.id" + " inner join department as dept on sdept.deptid=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			else
			{
				empall = "select emp.empname,emp.mobone,emp.emailidone,emp.dept as deptid, dept.deptName from employee_basic as emp" + " inner join department as dept on emp.dept=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + uName + "'";
			}
			empList = new createTable().executeAllSelectQuery(empall, connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	/*
	 * MANAGEMENT Dashboard
	 */
	@SuppressWarnings(
	{
			"rawtypes", "unchecked"
	})
	public List<ComplianceDashboardBean> getComplianceMgmntDetailForDasgboard(SessionFactory connection, String dataFor, String logedEmpid, String logedContId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
		ComplianceEditGridAction CEGA = new ComplianceEditGridAction();
		StringBuilder compId = new StringBuilder();
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			List depList = null;

			ComplianceDashboardBean CDB = null;
			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
			// String strQuery =
			// "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="
			// +logedContId+")";
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + logedEmpid + ")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			List contactList = new ArrayList();

			String allContIdQuery = "SELECT id FROM compliance_contacts WHERE moduleName='COMPL' AND emp_id=" + logedEmpid;
			List allContIdList = cbt.executeAllSelectQuery(allContIdQuery, connectionSpace);

			if (allContIdList != null && allContIdList.size() > 0)
			{
				for (int i = 0; i < allContIdList.size(); i++)
				{
					contactList.add("#" + allContIdList.get(i).toString() + "#");
				}
			}

			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactList.add("#" + shareDataCount.get(i).toString() + "#");
					String contactId = getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
					if (contactId != null)
					{
						String str[] = contactId.split(",");
						for (int count = 0; count < str.length; count++)
						{
							contactList.add("#" + str[count] + "#");
						}
					}
				}
			}
			String contactId = getMappedAllContactId(logedEmpid, "COMPL");
			if (contactId != null)
			{
				String str[] = contactId.split(",");
				for (int i = 0; i < str.length; i++)
				{
					contactList.add("#" + str[i] + "#");
				}
			}
			contactList.add("#" + logedContId + "#");
			StringBuilder taskNameId = new StringBuilder();
			if (dataCount != null && dataCount.size() > 0)
			{
				Object object[] = null;
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();
					for (int i = 0; i < contactList.size(); i++)
					{
						if (str.contains(contactList.get(i).toString()))
						{
							taskNameId.append(object[2].toString() + ",");
							compId.append(object[0].toString() + ",");
						}
					}

				}
			}
			if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
			{
				String complianceId = taskNameId.substring(0, (taskNameId.toString().length() - 1));
				StringBuilder queryString = new StringBuilder();
				queryString.append("select dept.id,dept.deptName from department as dept " + "INNER JOIN compliance_task as task on dept.id=task.departName " + "INNER JOIN compliance_details as comp on task.id=comp.taskName WHERE task.id IN(" + complianceId + ")" + "group by dept.deptName");
				depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			}
			// }
			if (depList != null && depList.size() > 0)
			{
				if (dataFor.equalsIgnoreCase("totalMissed"))
				{
					int count = 0;
					for (Iterator iterator = depList.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						CDB = new ComplianceDashboardBean();
						CDB.setDepartId(object[0].toString());
						CDB.setDepartName(object[1].toString());
						if (compId != null && !compId.toString().equalsIgnoreCase(""))
						{
							String compIdList = compId.substring(0, (compId.toString().length() - 1));
							StringBuilder queryString = new StringBuilder();
							queryString.append("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task ");
							queryString.append("AS ctn ON ctn.id=comp.taskName INNER JOIN department ");
							queryString.append("AS dept ON dept.id=ctn.departName WHERE dept.id=" + object[0].toString() + " AND comp.id IN(" + compIdList + ")");
							List dataList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								Object object1 = null;
								StringBuilder strBuilder = new StringBuilder();
								for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
								{
									object1 = (Object) iterator2.next();
									strBuilder.append(object1.toString() + ",");

								}
								String str = strBuilder.substring(0, (strBuilder.toString().length() - 1));

								int annualTotal = cmnOper.getMgmntCounterOfCompliance("Total", "Y", "AnnualTotal", str, connectionSpace, dataFor);
								int annualMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "Y", "AnnualMissed", str, connectionSpace, dataFor);

								int monthlyTotal = cmnOper.getMgmntCounterOfCompliance("Total", "M", "MonthlyTotal", str, connectionSpace, dataFor);
								int monthlyMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "M", "MonthlyMissed", str, connectionSpace, dataFor);

								int weeklyTotal = cmnOper.getMgmntCounterOfCompliance("Total", "W", "WeeklyTotal", str, connectionSpace, dataFor);
								int weeklyMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "W", "WeeklyMissed", str, connectionSpace, dataFor);

								if (annualTotal > 0)
								{
									CDB.setAnnualTotal(String.valueOf(Integer.parseInt(CDB.getAnnualTotal()) + annualTotal));
								}
								if (monthlyTotal > 0)
								{
									CDB.setMonthlyTotal(String.valueOf(Integer.parseInt(CDB.getMonthlyTotal()) + monthlyTotal));
								}
								if (weeklyTotal > 0)
								{
									CDB.setWeeklyTotal(String.valueOf(Integer.parseInt(CDB.getWeeklyTotal()) + weeklyTotal));
								}
								if (annualMissed > 0)
								{
									CDB.setAnnualMissed(String.valueOf(Integer.parseInt(CDB.getAnnualMissed()) + annualMissed));
								}
								if (monthlyMissed > 0)
								{
									CDB.setMonthlyMissed(String.valueOf(Integer.parseInt(CDB.getMonthlyMissed()) + monthlyMissed));
								}
								if (weeklyMissed > 0)
								{
									CDB.setWeeklyMissed(String.valueOf(Integer.parseInt(CDB.getWeeklyMissed()) + weeklyMissed));
								}
								if (str != null && !str.toString().equalsIgnoreCase(""))
								{
									CDB.setCompId(str);
								}
								else
								{
									CDB.setCompId("0");
								}
								finalDataList.add(CDB);
							}

						}
						else
						{
							// String qurStr=
							// "select id from compliance_task where departName='"
							// +object[0].toString()+"'";
							String qurStr = "SELECT ct.id AS taskId,cd.id AS compId FROM compliance_details AS cd INNER JOIN compliance_task AS ct ON ct.id=cd.taskName WHERE ct.departName='" + object[0].toString() + "'";
							List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
							if (taskIdList != null && taskIdList.size() > 0)
							{
								Object object1[] = null;
								StringBuilder str = new StringBuilder();
								for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
								{
									object1 = (Object[]) iterator2.next();

									str.append(object1[1].toString() + ",");

									int annualTotal = cmnOper.getMgmntCounterOfCompliance("Total", "Y", "AnnualTotal", object1[1].toString(), connectionSpace, dataFor);
									int annualMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "Y", "AnnualMissed", object1[1].toString(), connectionSpace, dataFor);

									int monthlyTotal = cmnOper.getMgmntCounterOfCompliance("Total", "M", "MonthlyTotal", object1[1].toString(), connectionSpace, dataFor);
									int monthlyMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "M", "MonthlyMissed", object1[1].toString(), connectionSpace, dataFor);

									int weeklyTotal = cmnOper.getMgmntCounterOfCompliance("Total", "W", "WeeklyTotal", object1[1].toString(), connectionSpace, dataFor);
									int weeklyMissed = cmnOper.getMgmntCounterOfCompliance("Missed", "W", "WeeklyMissed", object1[1].toString(), connectionSpace, dataFor);

									if (annualTotal > 0)
									{
										CDB.setAnnualTotal(String.valueOf(Integer.parseInt(CDB.getAnnualTotal()) + annualTotal));
									}
									if (monthlyTotal > 0)
									{
										CDB.setMonthlyTotal(String.valueOf(Integer.parseInt(CDB.getMonthlyTotal()) + monthlyTotal));
									}
									if (weeklyTotal > 0)
									{
										CDB.setWeeklyTotal(String.valueOf(Integer.parseInt(CDB.getWeeklyTotal()) + weeklyTotal));
									}
									if (annualMissed > 0)
									{
										CDB.setAnnualMissed(String.valueOf(Integer.parseInt(CDB.getAnnualMissed()) + annualMissed));
									}
									if (monthlyMissed > 0)
									{
										CDB.setMonthlyMissed(String.valueOf(Integer.parseInt(CDB.getMonthlyMissed()) + monthlyMissed));
									}
									if (weeklyMissed > 0)
									{
										CDB.setWeeklyMissed(String.valueOf(Integer.parseInt(CDB.getWeeklyMissed()) + weeklyMissed));
									}
								}

								if (str != null && !str.toString().equalsIgnoreCase(""))
								{
									CDB.setCompId(str.toString().substring(0, str.toString().length() - 1));
								}
								else
								{
									CDB.setCompId("0");
								}
								finalDataList.add(CDB);
							}
						}
						count++;
					}
				}
				else if (dataFor.equalsIgnoreCase("upcomeingDues"))
				{
					int count = 0;
					for (Iterator iterator = depList.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						CDB = new ComplianceDashboardBean();
						CDB.setDepartId(object[0].toString());
						CDB.setDepartName(object[1].toString());

						if (compId != null && !compId.toString().equalsIgnoreCase(""))
						{
							String compIdList = compId.toString().substring(0, (compId.toString().length() - 1));
							StringBuilder queryString = new StringBuilder();
							queryString.append("SELECT comp.id FROM compliance_details AS comp INNER JOIN compliance_task ");
							queryString.append("AS ctn ON ctn.id=comp.taskName INNER JOIN department ");
							queryString.append("AS dept ON dept.id=ctn.departName WHERE dept.id=" + object[0].toString() + " AND comp.id IN(" + compIdList + ")");
							List dataList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								Object object1 = null;
								StringBuilder strBuilder = new StringBuilder();
								for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
								{
									object1 = (Object) iterator2.next();
									strBuilder.append(object1.toString() + ",");

								}

								String str = strBuilder.substring(0, (strBuilder.toString().length() - 1));

								int upcomeingAnnualTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "AnnualTotal", str, connectionSpace, dataFor);
								int upcomeingMonthlyTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "MonthlyTotal", str, connectionSpace, dataFor);
								int upcomeingWeeklyTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "WeeklyTotal", str, connectionSpace, dataFor);

								if (upcomeingAnnualTotal > 0)
								{
									CDB.setAnnualTotal(String.valueOf(Integer.parseInt(CDB.getAnnualTotal()) + upcomeingAnnualTotal));
								}
								if (upcomeingMonthlyTotal > 0)
								{
									CDB.setMonthlyTotal(String.valueOf(Integer.parseInt(CDB.getMonthlyTotal()) + upcomeingMonthlyTotal));
								}
								if (upcomeingWeeklyTotal > 0)
								{
									CDB.setWeeklyTotal(String.valueOf(Integer.parseInt(CDB.getWeeklyTotal()) + upcomeingWeeklyTotal));
								}
								if (str != null && !str.toString().equalsIgnoreCase(""))
								{
									CDB.setCompId(str);
								}
								else
								{
									CDB.setCompId("0");
								}

								finalDataList.add(CDB);
							}
						}
						else
						{
							// String qurStr=
							// "select id from compliance_task where departName='"
							// +object[0].toString()+"'";
							String qurStr = "SELECT ct.id AS taskId,cd.id AS compId FROM compliance_details AS cd INNER JOIN compliance_task AS ct ON ct.id=cd.taskName WHERE ct.departName='" + object[0].toString() + "'";
							List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
							if (taskIdList != null && taskIdList.size() > 0)
							{
								Object[] object1 = null;
								StringBuilder str = new StringBuilder();
								for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
								{
									object1 = (Object[]) iterator2.next();
									str.append(object1[1].toString() + ",");

									int upcomeingAnnualTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "AnnualTotal", object1[1].toString(), connectionSpace, dataFor);
									int upcomeingMonthlyTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "MonthlyTotal", object1[1].toString(), connectionSpace, dataFor);
									int upcomeingWeeklyTotal = cmnOper.getMgmntCounterOfCompliance("TotalUpcomeing", null, "WeeklyTotal", object1[1].toString(), connectionSpace, dataFor);

									if (upcomeingAnnualTotal > 0)
									{
										CDB.setAnnualTotal(String.valueOf(Integer.parseInt(CDB.getAnnualTotal()) + upcomeingAnnualTotal));
									}
									if (upcomeingMonthlyTotal > 0)
									{
										CDB.setMonthlyTotal(String.valueOf(Integer.parseInt(CDB.getMonthlyTotal()) + upcomeingMonthlyTotal));
									}
									if (upcomeingWeeklyTotal > 0)
									{
										CDB.setWeeklyTotal(String.valueOf(Integer.parseInt(CDB.getWeeklyTotal()) + upcomeingWeeklyTotal));
									}
								}
								if (str != null && !str.toString().equalsIgnoreCase(""))
								{
									CDB.setCompId(str.toString().substring(0, str.toString().length() - 1));
								}
								else
								{
									CDB.setCompId("0");
								}
								finalDataList.add(CDB);
							}
						}
						count++;
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceDetailForDasgboard of class " + getClass(), exp);
		}
		return finalDataList;
	}

	@SuppressWarnings("rawtypes")
	public int getMgmntCounterOfCompliance(String totalOrMissed, String freq, String currentTime, String taskNameId, SessionFactory connsSpace, String dataFor)
	{
		int counter = 0;
		try
		{
			List tempList = new ArrayList();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder qry = new StringBuilder();

			qry.append("select count(*) from compliance_details where ");
			if (dataFor.equalsIgnoreCase("totalMissed"))
			{
				qry.append(" frequency=");
				qry.append("'");
				if (freq.equalsIgnoreCase("Y"))
				{
					qry.append("Y");
				}
				else if (freq.equalsIgnoreCase("M"))
				{
					qry.append("M");
				}
				else if (freq.equalsIgnoreCase("W"))
				{
					qry.append("W");
				}
				qry.append("'");
				qry.append(" and (actionTaken='Pending' OR actionTaken='Snooze') ");
			}
			else if (dataFor.equalsIgnoreCase("upcomeingDues"))
			{
				qry.append(" dueDate");
				String date;
				if (currentTime.equalsIgnoreCase("WeeklyTotal"))
				{
					date = DateUtil.getNextDateAfter(7);
					qry.append(" between ");
					qry.append("'");
					qry.append(DateUtil.getCurrentDateUSFormat());
					qry.append("'");
					qry.append(" and ");
					qry.append("'");
					qry.append(date);
					qry.append("'");
				}
				else if (currentTime.equalsIgnoreCase("MonthlyTotal"))
				{
					date = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
					qry.append(" between ");
					qry.append("'");
					qry.append(DateUtil.getCurrentDateUSFormat());
					qry.append("'");
					qry.append(" and ");
					qry.append("'");
					qry.append(date);
					qry.append("'");
				}
				else if (currentTime.equalsIgnoreCase("AnnualTotal"))
				{
					date = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
					qry.append(" between ");
					qry.append("'");
					qry.append(DateUtil.getCurrentDateUSFormat());
					qry.append("'");
					qry.append(" and ");
					qry.append("'");
					qry.append(date);
					qry.append("'");
				}
				qry.append(" and (actionTaken='Pending' OR actionTaken='Snooze') ");
			}
			if (currentTime.equalsIgnoreCase("WeeklyMissed") || currentTime.equalsIgnoreCase("MonthlyMissed") || currentTime.equalsIgnoreCase("AnnualMissed"))
			{
				qry.append(" AND dueDate<'" + DateUtil.getCurrentDateUSFormat() + "'");
			}

			qry.append(" and ");
			qry.append("id IN(" + taskNameId + ")");
			/*
			 * if(compViewLevel!=null && compViewLevel.equals("2")) {
			 * qry.append("id ="+taskNameId); } else if(compViewLevel!=null &&
			 * compViewLevel.equals("1")) { qry.append("id IN("+taskNameId+")");
			 * }
			 */
			tempList = cbt.executeAllSelectQuery(qry.toString(), connsSpace);
			if (tempList != null && tempList.size() > 0)
			{
				counter = Integer.parseInt(tempList.get(0).toString());
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return counter;
	}

	@SuppressWarnings(
	{
			"rawtypes", "unchecked"
	})
	public List<ComplianceDashboardBean> getAllUpcomingCompl(SessionFactory connection, String logedEmpId, String logedContId)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		ComplianceDashboardBean dashBean = new ComplianceDashboardBean();
		ComplianceEditGridAction CEGA = new ComplianceEditGridAction();
		try
		{
			StringBuffer compId = new StringBuffer();
			String strCompId = null;
			/*
			 * String compViewLevel=new
			 * ComplianceUniversalAction().getCompContactLevel(userName);
			 * if(compViewLevel!=null && compViewLevel.equalsIgnoreCase("1")) {
			 */
			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details", connectionSpace);
			// String strQuery=
			// "SELECT sharing_with FROM contact_sharing_detail AS csd INNER JOIN compliance_contacts AS cc ON cc.id=csd.contact_id WHERE cc.moduleName='COMPL' AND cc.emp_id="
			// +empId;
			// String strQuery =
			// "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id="
			// +logedContId;
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + logedEmpId + ")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			List contactList = new ArrayList();

			String allContIdQuery = "SELECT id FROM compliance_contacts WHERE moduleName='COMPL' AND emp_id=" + logedEmpId;
			List allContIdList = cbt.executeAllSelectQuery(allContIdQuery, connectionSpace);

			if (allContIdList != null && allContIdList.size() > 0)
			{
				for (int i = 0; i < allContIdList.size(); i++)
				{
					contactList.add("#" + allContIdList.get(i).toString() + "#");
				}
			}

			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactList.add("#" + shareDataCount.get(i).toString() + "#");
					String contactId = getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
					if (contactId != null)
					{
						String str[] = contactId.split(",");
						for (int count = 0; count < str.length; count++)
						{
							contactList.add("#" + str[count] + "#");
						}
					}
				}
			}
			String contactId = getMappedAllContactId(logedEmpId, "COMPL");
			if (contactId != null)
			{
				String str[] = contactId.split(",");
				for (int i = 0; i < str.length; i++)
				{
					contactList.add("#" + str[i] + "#");
				}
			}
			contactList.add("#" + logedContId + "#");
			if (dataCount != null && dataCount.size() > 0)
			{
				Object object[] = null;
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();

					for (int i = 0; i < contactList.size(); i++)
					{
						if (str.contains(contactList.get(i).toString()))
						{
							compId.append(object[0].toString() + ",");
						}
					}
				}
			}
			// }
			if (compId != null && !compId.toString().equalsIgnoreCase(""))
			{
				strCompId = compId.substring(0, (compId.toString().length() - 1));
			}
			StringBuilder qryString = new StringBuilder();
			if (strCompId != null && !strCompId.equalsIgnoreCase(" "))
			{
				qryString.append("SELECT cd.id,ctn.taskName,cd.dueDate,cd.actionTaken,cd.dueTime,cd.current_esc_level FROM " + "compliance_details AS cd INNER JOIN compliance_task AS ctn ON cd.taskName=ctn.id WHERE cd.id IN(" + strCompId + ") AND (cd.actionTaken='Pending' OR cd.actionTaken='Snooze') ORDER BY cd.dueDate");
			}
			/*
			 * else {qryString.append(
			 * "SELECT cd.id,ctn.taskName,cd.dueDate,cd.actionTaken,cd.dueTime,cd.current_esc_level FROM "
			 * +
			 * "compliance_details AS cd INNER JOIN compliance_task AS ctn ON cd.taskName=ctn.id WHERE (cd.actionTaken='Pending' OR cd.actionTaken='Snooze') ORDER BY cd.dueDate"
			 * ); }
			 */
			List dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					dashBean = new ComplianceDashboardBean();
					Object[] object = (Object[]) iterator.next();
					dashBean.setCompId(object[0].toString());
					dashBean.setTaskName(object[1].toString());
					dashBean.setDueDate(DateUtil.convertDateToIndianFormat(object[2].toString()));
					dashBean.setActionStatus(object[3].toString());
					if (object[5] != null)
					{
						dashBean.setCurrentEscLevel("Level " + Integer.valueOf(object[5].toString()));
					}
					else
					{
						dashBean.setCurrentEscLevel("No Esc");
					}
					finalDataList.add(dashBean);
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getAllUpcomingCompl of class " + getClass(), exp);
		}
		return finalDataList;
	}

	@SuppressWarnings(
	{
			"rawtypes", "unchecked"
	})
	public List<ComplianceDashboardBean> getTotalComplByStatus(String userEmpID, String logedContId)
	{
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceEditGridAction CEGA = new ComplianceEditGridAction();
			List depList = new ArrayList();

			ComplianceDashboardBean CDB = null;
			StringBuilder compId = new StringBuilder();
			// String compViewLevel=new
			// ComplianceUniversalAction().getCompContactLevel(userName);
			/*
			 * if(compViewLevel!=null && compViewLevel.equalsIgnoreCase("2")) {
			 * StringBuilder queryString=new StringBuilder();
			 * queryString.append(
			 * "select dept.id as deptId,dept.deptName from department as dept "
			 * +
			 * "INNER JOIN compliance_task as task on dept.id=task.departName "
			 * +
			 * "INNER JOIN compliance_details as comp on task.id=comp.taskName "
			 * + "group by dept.deptName");
			 * depList=cbt.executeAllSelectQuery(queryString.toString(),
			 * connectionSpace); } else {
			 */
			List contactList = new ArrayList();

			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
			// String strQuery =
			// "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id="
			// +logedContId;
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + userEmpID + ")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);

			String allContIdQuery = "SELECT id FROM compliance_contacts WHERE moduleName='COMPL' AND emp_id=" + userEmpID;
			List allContIdList = cbt.executeAllSelectQuery(allContIdQuery, connectionSpace);

			if (allContIdList != null && allContIdList.size() > 0)
			{
				for (int i = 0; i < allContIdList.size(); i++)
				{
					contactList.add("#" + allContIdList.get(i).toString() + "#");
				}
			}

			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactList.add("#" + shareDataCount.get(i).toString() + "#");
					String contactId = getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
					if (contactId != null)
					{
						String str[] = contactId.split(",");
						for (int count = 0; count < str.length; count++)
						{
							contactList.add("#" + str[count] + "#");
						}
					}
				}
			}
			String contactId = getMappedAllContactId(userEmpID, "COMPL");
			if (contactId != null)
			{
				String str[] = contactId.split(",");
				for (int i = 0; i < str.length; i++)
				{
					contactList.add("#" + str[i] + "#");
				}
			}
			contactList.add("#" + logedContId + "#");

			StringBuilder taskNameId = new StringBuilder();

			if (dataCount != null && dataCount.size() > 0)
			{
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();
					for (int i = 0; i < contactList.size(); i++)
					{
						if (str.contains(contactList.get(i).toString()))
						{
							taskNameId.append(object[2].toString() + ",");
							compId.append(object[0].toString() + ",");
						}
					}
				}
			}
			if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
			{
				String complianceId = compId.substring(0, (compId.toString().length() - 1));
				StringBuilder queryString = new StringBuilder();
				queryString.append("select dept.id as deptId,dept.deptName from department as dept " + "INNER JOIN compliance_task as task on dept.id=task.departName " + "INNER JOIN compliance_details as comp on task.id=comp.taskName WHERE comp.id IN(" + complianceId + ")" + "group by dept.deptName");
				depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			}
			// }
			StringBuilder complList = new StringBuilder();
			if (depList != null && depList.size() > 0)
			{
				for (Iterator iterator = depList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					CDB = new ComplianceDashboardBean();
					CDB.setDepartId(object[0].toString());
					CDB.setDepartName(object[1].toString());
					String qurStr = "select id from compliance_task where departName='" + object[0].toString() + "'";
					List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
					if (taskIdList != null && taskIdList.size() > 0)
					{
						for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
						{
							Object object1 = (Object) iterator2.next();
							StringBuilder qryString = new StringBuilder();
							qryString.append("SELECT dueDate,dueTime,actionStatus,id FROM compliance_details WHERE taskName=" + object1.toString() + "");
							if (compId != null && compId.length() > 0)
							{
								qryString.append(" AND id IN (" + compId.substring(0, (compId.toString().length() - 1)) + ")");
							}
							List dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
							qryString.setLength(0);

							for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
							{
								int missedCount = 0, pendingCount = 0, doneCount = 0, snoozeDone = 0;
								Object[] object2 = (Object[]) iterator1.next();
								if (object2[2] != null)
								{
									if (object2[2].toString().equalsIgnoreCase("Pending"))
									{
										/*
										 * String
										 * strDue=object2[0].toString()+" "
										 * +object2[1].toString(); String
										 * strCurrent
										 * =DateUtil.getCurrentDateUSFormat
										 * ()+" "
										 * +DateUtil.getCurrentTimeHourMin();
										 */
										String strDue = object2[0].toString() + " 00:00";
										String strCurrent = DateUtil.getCurrentDateUSFormat() + " 00:00";
										boolean status = DateUtil.compareDateTime(strDue, strCurrent);
										if (status)
										{
											missedCount = 1;
										}
										else
										{
											pendingCount = 1;
										}
									}
									else if (object2[2].toString().equalsIgnoreCase("Snooze"))
									{
										snoozeDone = 1;
									}
									else if (object2[2].toString().equalsIgnoreCase("Done"))
									{
										doneCount = 1;
									}
								}
								if (object2[3] != null)
								{
									complList.append(object2[3].toString() + ",");
								}
								pendingCount = missedCount + pendingCount;
								if (missedCount > 0)
								{
									CDB.setMissed(String.valueOf(Integer.parseInt(CDB.getMissed()) + missedCount));
								}
								if (pendingCount > 0)
								{
									CDB.setPending(String.valueOf(Integer.parseInt(CDB.getPending()) + pendingCount));
								}
								if (doneCount > 0)
								{
									CDB.setDone(String.valueOf(Integer.parseInt(CDB.getDone()) + doneCount));
								}
								if (snoozeDone > 0)
								{
									CDB.setSnooze(String.valueOf(Integer.parseInt(CDB.getSnooze()) + snoozeDone));
								}
								if (complList != null && !complList.toString().equalsIgnoreCase(""))
								{
									CDB.setCompId(complList.substring(0, (complList.toString().length() - 1)));
								}
								else
								{
									CDB.setCompId("0");
								}
							}
						}
						finalDataList.add(CDB);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTotalComplByStatus of class " + getClass(), e);
		}
		return finalDataList;
	}

	@SuppressWarnings(
	{
			"rawtypes", "unchecked"
	})
	public List<ComplianceDashboardBean> getPreviousMonthCompDetail(String userEmpID, String logedContId)
	{
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceEditGridAction CEGA = new ComplianceEditGridAction();
			ComplianceDashboardBean CDB = null;
			List depList = null;
			StringBuilder compId = new StringBuilder();
			/*
			 * String compViewLevel=new
			 * ComplianceUniversalAction().getCompContactLevel(userName);
			 * if(compViewLevel!=null && compViewLevel.equalsIgnoreCase("2")) {
			 * StringBuilder queryString=new StringBuilder();
			 * queryString.append(
			 * "select dept.id,dept.deptName from department as dept " +
			 * "INNER JOIN compliance_task as task on dept.id=task.departName "
			 * +
			 * "INNER JOIN compliance_details as comp on task.id=comp.taskName "
			 * + "group by dept.deptName");
			 * depList=cbt.executeAllSelectQuery(queryString.toString(),
			 * connectionSpace); } else {
			 */
			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
			// String strQuery=
			// "SELECT sharing_with FROM contact_sharing_detail AS csd INNER JOIN compliance_contacts AS cc ON cc.id=csd.contact_id WHERE cc.moduleName='COMPL' AND cc.emp_id="
			// +userEmpID;
			// String strQuery =
			// "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id="
			// +logedContId;
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + userEmpID + ")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			List contactList = new ArrayList();

			String allContIdQuery = "SELECT id FROM compliance_contacts WHERE moduleName='COMPL' AND emp_id=" + userEmpID;
			List allContIdList = cbt.executeAllSelectQuery(allContIdQuery, connectionSpace);

			if (allContIdList != null && allContIdList.size() > 0)
			{
				for (int i = 0; i < allContIdList.size(); i++)
				{
					contactList.add("#" + allContIdList.get(i).toString() + "#");
				}
			}

			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactList.add("#" + shareDataCount.get(i).toString() + "#");
					String contactId = getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
					if (contactId != null)
					{
						String str[] = contactId.split(",");
						for (int count = 0; count < str.length; count++)
						{
							contactList.add("#" + str[count] + "#");
						}
					}
				}
			}
			String contactId = getMappedAllContactId(userEmpID, "COMPL");
			if (contactId != null)
			{
				String str[] = contactId.split(",");
				for (int i = 0; i < str.length; i++)
				{
					contactList.add("#" + str[i] + "#");
				}
			}
			contactList.add("#" + logedContId + "#");
			StringBuilder taskNameId = new StringBuilder();
			if (dataCount != null && dataCount.size() > 0)
			{
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();
					for (int i = 0; i < contactList.size(); i++)
					{
						if (str.contains(contactList.get(i).toString()))
						{
							taskNameId.append(object[2].toString() + ",");
							compId.append(object[0].toString() + ",");
						}
					}
				}
			}
			if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
			{
				String complianceId = compId.substring(0, (compId.toString().length() - 1));
				StringBuilder queryString = new StringBuilder();
				queryString.append("select dept.id as deptId,dept.deptName from department as dept " + "INNER JOIN compliance_task as task on dept.id=task.departName " + "INNER JOIN compliance_details as comp on task.id=comp.taskName WHERE comp.id IN(" + complianceId + ")" + "group by dept.deptName");
				depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			}
			// }
			StringBuilder complList = new StringBuilder();
			if (depList != null && depList.size() > 0)
			{
				for (Iterator iterator = depList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					CDB = new ComplianceDashboardBean();
					CDB.setDepartId(object[0].toString());
					CDB.setDepartName(object[1].toString());
					StringBuilder taskList = new StringBuilder();
					String qurStr = "select id from compliance_task where departName='" + object[0].toString() + "'";
					List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
					if (taskIdList != null && taskIdList.size() > 0)
					{
						for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
						{
							Object object1 = (Object) iterator2.next();
							taskList.append(object1.toString() + ",");
						}
					}
					StringBuilder qryString = new StringBuilder();

					qryString.append("SELECT CD.id,CR.actionTaken FROM compliance_report AS CR INNER JOIN compliance_details ");
					qryString.append(" AS CD ON CD.id=CR.complID INNER JOIN compliance_task AS CT ON CD.taskName=CT.id WHERE CT.id IN(" + taskList.substring(0, taskList.length() - 1) + ") AND CR.actionTakenDate BETWEEN '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' AND '" + DateUtil.getCurrentDateUSFormat() + "' ");
					if (compId != null && compId.length() > 0)
					{
						qryString.append(" AND CD.id IN (" + compId.substring(0, (compId.toString().length() - 1)) + ") ");
					}
					List dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
					qryString.setLength(0);

					for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
					{
						int pendingCount = 0, doneCount = 0;
						Object[] object2 = (Object[]) iterator1.next();
						if (object2[1] != null)
						{
							if (object2[1].toString().equalsIgnoreCase("Pending") || object2[1].toString().equalsIgnoreCase("Snooze") || object2[1].toString().equalsIgnoreCase("Reschedule"))
							{
								pendingCount = 1;
							}
							else if (object2[1].toString().equalsIgnoreCase("Done") || object2[1].toString().equalsIgnoreCase("Recurring"))
							{
								doneCount = 1;
							}
						}
						if (object2[0] != null)
						{
							complList.append(object2[0].toString() + ",");
						}
						if (pendingCount > 0)
						{
							CDB.setPending(String.valueOf(Integer.parseInt(CDB.getPending()) + pendingCount));
						}
						if (doneCount > 0)
						{
							CDB.setDone(String.valueOf(Integer.parseInt(CDB.getDone()) + doneCount));
						}
						if (complList != null && !complList.toString().equalsIgnoreCase(""))
						{
							CDB.setCompId(complList.substring(0, (complList.toString().length() - 1)));
						}
						else
						{
							CDB.setCompId("0");
						}
					}
					finalDataList.add(CDB);
					taskList.setLength(0);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTotalComplByStatus of class " + getClass(), e);
		}
		return finalDataList;
	}

	@SuppressWarnings("rawtypes")
	public List<ComplianceDashboardBean> getDoneLevel(String userEmpID)
	{
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			String complianceId = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceDashboardBean CDB = null;
			List depList = null;
			/*
			 * String compViewLevel=new
			 * ComplianceUniversalAction().getCompContactLevel(userName);
			 * if(compViewLevel!=null && compViewLevel.equalsIgnoreCase("2")) {
			 * StringBuilder queryString=new StringBuilder();
			 * queryString.append(
			 * "select dept.id,dept.deptName from department as dept " +
			 * "INNER JOIN compliance_task as task on dept.id=task.departName "
			 * +
			 * "INNER JOIN compliance_details as comp on task.id=comp.taskName "
			 * + "group by dept.deptName");
			 * depList=cbt.executeAllSelectQuery(queryString.toString(),
			 * connectionSpace); } else {
			 */
			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
			String empString = "#" + userEmpID + "#";
			StringBuilder taskNameId = new StringBuilder();
			if (dataCount != null && dataCount.size() > 0)
			{
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();
					if (str.contains(empString))
					{
						taskNameId.append(object[2].toString() + ",");
					}
				}
			}
			if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
			{
				complianceId = taskNameId.substring(0, (taskNameId.toString().length() - 1));
				StringBuilder queryString = new StringBuilder();
				queryString.append("select dept.id,dept.deptName from department as dept " + "INNER JOIN compliance_task as task on dept.id=task.departName " + "INNER JOIN compliance_details as comp on task.id=comp.taskName WHERE task.id IN(" + complianceId + ")" + "group by dept.deptName");
				depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			}
			// }
			if (depList != null && depList.size() > 0)
			{
				for (Iterator iterator = depList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					CDB = new ComplianceDashboardBean();
					CDB.setDepartId(object[0].toString());
					CDB.setDepartName(object[1].toString());
					String qurStr = "select id from compliance_task where departName='" + object[0].toString() + "'";
					List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
					if (taskIdList != null && taskIdList.size() > 0)
					{
						for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
						{
							Object object1 = (Object) iterator2.next();
							String qryString = "SELECT CD.id,CR.current_esc_level,COUNT(CR.current_esc_level) FROM compliance_report AS CR " + "INNER JOIN compliance_details AS CD ON CD.id=CR.complID INNER JOIN compliance_task AS CT ON CD.taskName=CT.id " + "WHERE CT.id=" + object1.toString() + " AND (CR.actionTaken='Done' || CR.actionTaken='Recurring') GROUP BY CR.current_esc_level";
							List dataList = cbt.executeAllSelectQuery(qryString, connectionSpace);
							for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
							{
								int esc1Count = 0, esc2Count = 0, esc3Count = 0, esc4Count = 0, esc5Count = 0;
								Object[] object2 = (Object[]) iterator1.next();
								if (object2[1] != null)
								{
									if (object2[1].toString().equalsIgnoreCase("1"))
									{
										esc1Count = Integer.valueOf(object2[2].toString());
									}
									else if (object2[1].toString().equalsIgnoreCase("2"))
									{
										esc2Count = Integer.valueOf(object2[2].toString());
									}
									else if (object2[1].toString().equalsIgnoreCase("3"))
									{
										esc3Count = Integer.valueOf(object2[2].toString());
									}
									else if (object2[1].toString().equalsIgnoreCase("4"))
									{
										esc4Count = Integer.valueOf(object2[2].toString());
									}
									else if (object2[1].toString().equalsIgnoreCase("5"))
									{
										esc5Count = Integer.valueOf(object2[2].toString());
									}
								}
								if (esc1Count > 0)
								{
									CDB.setEsc1Count(String.valueOf(Integer.parseInt(CDB.getEsc1Count()) + esc1Count));
								}
								if (esc2Count > 0)
								{
									CDB.setEsc2Count(String.valueOf(Integer.parseInt(CDB.getEsc2Count()) + esc2Count));
								}
								if (esc3Count > 0)
								{
									CDB.setEsc3Count(String.valueOf(Integer.parseInt(CDB.getEsc3Count()) + esc3Count));
								}
								if (esc4Count > 0)
								{
									CDB.setEsc4Count(String.valueOf(Integer.parseInt(CDB.getEsc4Count()) + esc4Count));
								}
								if (esc5Count > 0)
								{
									CDB.setEsc5Count(String.valueOf(Integer.parseInt(CDB.getEsc5Count()) + esc5Count));
								}
							}
						}
						finalDataList.add(CDB);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTotalComplByStatus of class " + getClass(), e);
		}
		return finalDataList;
	}

	@SuppressWarnings("rawtypes")
	public List<ComplianceDashboardBean> getComplainceData(int maxId, String logedUser)
	{
		List<ComplianceDashboardBean> data = new ArrayList<ComplianceDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceDashboardBean cdb = null;
			StringBuilder query = new StringBuilder();
			String mappedTeam = null;
			query.append("SELECT comp.frequency,comp.taskBrief,comp.reminder_for,comp.dueDate,comp.dueTime,comp.msg, ");
			query.append(" compTask.taskName,compType.taskType,comp.escalation_level_1,comp.l1EscDuration ");
			query.append(" FROM compliance_details AS comp ");
			query.append(" INNER JOIN compliance_task AS compTask ON compTask.id=comp.taskName ");
			query.append(" INNER JOIN compl_task_type AS compType ON compType.id=compTask.taskType WHERE comp.id = '" + maxId + "'");
			List compData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (compData != null && compData.size() > 0)
			{
				for (Iterator iterator = compData.iterator(); iterator.hasNext();)
				{
					cdb = new ComplianceDashboardBean();
					Object[] object = (Object[]) iterator.next();
					if (object != null)
					{
						if (object[0] != null)
						{
							cdb.setFrequency(new ComplianceReminderHelper().getFrequencyName(object[0].toString()));
						}
						else
						{
							cdb.setFrequency("NA");
						}
						if (object[1] != null)
						{
							cdb.setTaskBrief(object[1].toString());
						}
						else
						{
							cdb.setTaskBrief("NA");
						}
						if (object[2] != null)
						{
							cdb.setRemindTo(object[2].toString());
						}
						else
						{
							cdb.setRemindTo("NA");
						}
						if (object[3] != null)
						{
							cdb.setDueDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
						}
						else
						{
							cdb.setDueDate("NA");
						}
						if (object[4] != null)
						{
							cdb.setDueTime(object[4].toString());
						}
						else
						{
							cdb.setDueTime("NA");
						}
						if (object[5] != null)
						{
							cdb.setDone(object[5].toString());
						}
						else
						{
							cdb.setDone("NA");
						}
						if (object[6] != null)
						{
							cdb.setTaskName(object[6].toString());
						}
						else
						{
							cdb.setTaskName("NA");
						}
						if (object[7] != null)
						{
							cdb.setTaskType(object[7].toString());
						}
						else
						{
							cdb.setTaskType("NA");
						}
						if (object[8] != null)
						{
							String empId1 = object[8].toString().replace("#", ",").substring(0, (object[8].toString().length() - 1));
							List empDataList = new ComplianceCommonOperation().getEmployeeInfo(empId1);
							if (empDataList != null && empDataList.size() > 0)
							{
								StringBuilder str = new StringBuilder();
								;
								boolean flag = false;
								for (Iterator iterator2 = empDataList.iterator(); iterator2.hasNext();)
								{
									Object object2[] = (Object[]) iterator2.next();
									if (!object2[5].toString().equalsIgnoreCase(logedUser))
									{
										str.append(object2[0].toString() + ", ");
									}
									else
									{
										flag = true;
									}
								}
								if (str != null && str.toString().length() > 0)
								{
									mappedTeam = str.toString().substring(0, str.toString().length() - 2);
								}
								if (mappedTeam == null)
									mappedTeam = "You";
								else if (flag)
									mappedTeam = mappedTeam + " and You";
							}
							cdb.setEsc1Count(mappedTeam);
						}
						else
						{
							cdb.setEsc1Count("NA");
						}
						if (object[9] != null)
						{
							cdb.setEsc2Count(object[9].toString());
						}
						else
						{
							cdb.setEsc2Count("NA");
						}
					}
					data.add(cdb);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("rawtypes")
	public List getEmployeeInfo(String contactId)
	{
		List data = null;
		try
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query2 = "SELECT emp.empName,emp.emailIdOne,emp.mobOne,emp.id,dept.deptName,cc.id " + "AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON " + "emp.id=cc.emp_id " + "INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status!=1 " + "AND cc.id IN(" + contactId + ")";
			data = cbt.executeAllSelectQuery(query2, connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public String getMailBodyComplaince(List<ComplianceDashboardBean> conmpData, String alias, String name, String remindFor, String logedName)
	{
		StringBuffer mailText = new StringBuffer("");
		mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Dear " + name + ",</b></td></tr></tbody></table>");
		mailText.append("<br>");
		if (alias.equalsIgnoreCase("allot"))
		{
			mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Following Operation Task has been allotted to you:</b></tr></tbody></table>");
			mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		}
		if (alias.equalsIgnoreCase("confi"))
		{
			mailText.append("<table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='left' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Following Operation Task has been Registered By you:</b></tr></tbody></table>");
			mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		}
		if (conmpData != null && conmpData.size() > 0)
		{
			for (ComplianceDashboardBean ist : conmpData)
			{
				if (remindFor.equalsIgnoreCase("remToSelf"))
				{
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>You</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToOther") && alias.equals("allot"))
				{
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToOther") && alias.equals("confi"))
				{
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToBoth") && alias.equals("allot"))
				{
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + logedName + "</td></tr>");
				}
				if (remindFor.equalsIgnoreCase("remToBoth") && alias.equals("confi"))
				{
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + logedName + "</td></tr>");
				}
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Task Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getTaskName() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Task Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getTaskType() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Task Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getTaskBrief() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Due Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getDueDate() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Due Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getDueTime() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Frequency:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getFrequency() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Escalation 1 Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getEsc1Count() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Escalation 1 Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getEsc2Count() + "</td></tr>");
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Reminder:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ist.getDone() + "</td></tr>");
			}
		}
		mailText.append("</table>");
		mailText.append("<br>");
		mailText.append("<br>");
		mailText
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailText.toString();
	}

	public String getMsgBodyComplaince(String allotedBy, String taskName, String dueDate, String escTime)
	{
		StringBuilder msgBody = new StringBuilder();
		try
		{
			msgBody.append("Operation Task Allotment Alert: Task Name: " + taskName + " Allotted By: " + allotedBy + ", Due Date: " + dueDate + " Escalation Time: " + escTime + ".");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return msgBody.toString();
	}

	public String getMsgBodyComplainceReg(String allotedTo, String taskName, String dueDate, String escTime)
	{
		StringBuilder msgBody = new StringBuilder();
		try
		{
			msgBody.append("Operation Task Registration Alert: Task Name: " + taskName + " is Allotted To: " + allotedTo + ", Due Date: " + dueDate + " Escalation Time: " + escTime + ".");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return msgBody.toString();
	}

	@SuppressWarnings(
	{
			"rawtypes", "unchecked"
	})
	public List<ComplianceDashboardBean> getTotalTodayDueTask(String userEmpID, String logedContId)
	{
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder teamCompId = new StringBuilder();
			StringBuilder selfCompId = new StringBuilder();
			ComplianceEditGridAction CEGA = new ComplianceEditGridAction();
			List depList = null;
			ComplianceDashboardBean CDB = null;

			List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for,taskName FROM compliance_details", connectionSpace);
			// String strQuery=
			// "SELECT sharing_with FROM contact_sharing_detail AS csd INNER JOIN compliance_contacts AS cc ON cc.id=csd.contact_id WHERE cc.moduleName='COMPL' AND cc.emp_id="
			// +userEmpID;
			// String strQuery =
			// "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id="
			// +logedContId;
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + userEmpID + ")";
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			List contactList = new ArrayList();

			String allContIdQuery = "SELECT id FROM compliance_contacts WHERE moduleName='COMPL' AND emp_id=" + userEmpID;
			List allContIdList = cbt.executeAllSelectQuery(allContIdQuery, connectionSpace);

			if (allContIdList != null && allContIdList.size() > 0)
			{
				for (int i = 0; i < allContIdList.size(); i++)
				{
					contactList.add("#" + allContIdList.get(i).toString() + "#");
				}
			}

			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactList.add("#" + shareDataCount.get(i).toString() + "#");
					String contactId = getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
					if (contactId != null)
					{
						String str[] = contactId.split(",");
						for (int count = 0; count < str.length; count++)
						{
							contactList.add("#" + str[count] + "#");
						}
					}
				}
			}
			String contactId = getMappedAllContactId(userEmpID, "COMPL");
			if (contactId != null)
			{
				String str[] = contactId.split(",");
				for (int i = 0; i < str.length; i++)
				{
					contactList.add("#" + str[i] + "#");
				}
			}

			StringBuilder taskNameId = new StringBuilder();
			if (dataCount != null && dataCount.size() > 0)
			{
				for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					String str = "#";
					str = str + object[1].toString();
					for (int i = 0; i < contactList.size(); i++)
					{
						if (str.contains(contactList.get(i).toString()))
						{
							taskNameId.append(object[2].toString() + ",");
							teamCompId.append(object[0].toString() + ",");
						}
					}
					if (str.contains("#" + logedContId + "#"))
					{
						taskNameId.append(object[2].toString() + ",");
						selfCompId.append(object[0].toString() + ",");
					}
				}
			}
			if (taskNameId != null && !taskNameId.toString().equalsIgnoreCase(""))
			{
				String complianceId = taskNameId.substring(0, (taskNameId.toString().length() - 1));
				StringBuilder queryString = new StringBuilder();
				queryString.append("select dept.id,dept.deptName from department as dept " + "INNER JOIN compliance_task as task on dept.id=task.departName " + "INNER JOIN compliance_details as comp on task.id=comp.taskName WHERE task.id IN(" + complianceId + ")" + "group by dept.deptName");
				depList = cbt.executeAllSelectQuery(queryString.toString(), connectionSpace);
			}
			// }
			StringBuilder taskList = new StringBuilder();
			StringBuilder complDueList = new StringBuilder();
			StringBuilder complRem2List = new StringBuilder();
			StringBuilder complRem1List = new StringBuilder();
			StringBuilder complRem3List = new StringBuilder();
			StringBuilder teamDueComlList = new StringBuilder();
			StringBuilder teamRem1ComlList = new StringBuilder();
			StringBuilder teamRem2ComlList = new StringBuilder();
			StringBuilder teamRem3ComlList = new StringBuilder();
			if (depList != null && depList.size() > 0)
			{
				for (Iterator iterator = depList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					CDB = new ComplianceDashboardBean();
					CDB.setDepartId(object[0].toString());
					CDB.setDepartName(object[1].toString());
					String qurStr = "select id from compliance_task where departName='" + object[0].toString() + "'";
					List taskIdList = cbt.executeAllSelectQuery(qurStr, connectionSpace);
					if (taskIdList != null && taskIdList.size() > 0)
					{
						for (Iterator iterator2 = taskIdList.iterator(); iterator2.hasNext();)
						{
							Object object1 = (Object) iterator2.next();
							taskList.append(object1.toString() + ",");
						}
					}
					if (selfCompId != null && selfCompId.length() > 0)
					{
						String query = "SELECT reminder_name,remind_date,cd.dueDate,cd.id FROM compliance_reminder as cr  INNER JOIN compliance_details as cd on cd.id = cr.compliance_id " + "where cr.compliance_id IN (select id from compliance_details where taskName  IN (" + taskList.substring(0, taskList.length() - 1) + ") AND id IN(" + selfCompId.substring(0, selfCompId.length() - 1) + "))";
						List selfData = cbt.executeAllSelectQuery(query, connectionSpace);
						if (selfData != null && selfData.size() > 0)
						{
							for (Iterator iterator2 = selfData.iterator(); iterator2.hasNext();)
							{
								int dueSelfCount = 0, rem1SelfCount = 0, rem2SelfCount = 0, rem3SelfCount = 0;
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[0] != null)
								{
									if (object2[0].toString().equalsIgnoreCase("Due Reminder") && object2[2].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										dueSelfCount = 1;
										if (object2[3] != null)
										{
											complDueList.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 1") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem1SelfCount = 1;
										if (object2[3] != null)
										{
											complRem1List.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 2") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem2SelfCount = 1;
										if (object2[3] != null)
										{
											complRem2List.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 3") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem3SelfCount = 1;
										if (object2[3] != null)
										{
											complRem3List.append(object2[3].toString() + ",");
										}
									}

								}
								if (dueSelfCount > 0)
								{
									CDB.setSelfDueDate(String.valueOf(Integer.parseInt(CDB.getSelfDueDate()) + dueSelfCount));
								}
								if (rem1SelfCount > 0)
								{
									CDB.setSelfRem1(String.valueOf(Integer.parseInt(CDB.getSelfRem1()) + rem1SelfCount));
								}
								if (rem2SelfCount > 0)
								{
									CDB.setSelfRem2(String.valueOf(Integer.parseInt(CDB.getSelfRem2()) + rem2SelfCount));
								}
								if (rem3SelfCount > 0)
								{
									CDB.setSelfRem3(String.valueOf(Integer.parseInt(CDB.getSelfRem3()) + rem3SelfCount));
								}
								if (complDueList != null && !complDueList.toString().equalsIgnoreCase(""))
								{
									CDB.setSelfDueCompliance(complDueList.substring(0, (complDueList.toString().length() - 1)));
								}
								else
								{
									CDB.setSelfDueCompliance("0");
								}
								if (complRem1List != null && !complRem1List.toString().equalsIgnoreCase(""))
								{
									CDB.setSelfRem1Compliance(complRem1List.substring(0, (complRem1List.toString().length() - 1)));
								}
								else
								{
									CDB.setSelfRem1Compliance("0");
								}
								if (complRem2List != null && !complRem2List.toString().equalsIgnoreCase(""))
								{
									CDB.setSelfRem2Compliance(complRem2List.substring(0, (complRem2List.toString().length() - 1)));
								}
								else
								{
									CDB.setSelfRem2Compliance("0");
								}
								if (complRem3List != null && !complRem3List.toString().equalsIgnoreCase(""))
								{
									CDB.setSelfRem3Compliance(complRem3List.substring(0, (complRem3List.toString().length() - 1)));
								}
								else
								{
									CDB.setSelfRem3Compliance("0");
								}
							}
						}

					}

					if (teamCompId != null && teamCompId.length() > 0)
					{
						String query = "SELECT reminder_name,remind_date,cd.dueDate,cd.id FROM compliance_reminder as cr  INNER JOIN compliance_details as cd on cd.id = cr.compliance_id " + "where cr.compliance_id IN (select id from compliance_details where taskName  IN (" + taskList.substring(0, taskList.length() - 1) + ") AND id IN(" + teamCompId.substring(0, teamCompId.length() - 1) + "))";
						List teamData = cbt.executeAllSelectQuery(query, connectionSpace);
						if (teamData != null && teamData.size() > 0)
						{
							for (Iterator iterator2 = teamData.iterator(); iterator2.hasNext();)
							{
								int dueTeamCount = 0, rem1TeamCount = 0, rem2TeamCount = 0, rem3TeamCount = 0;
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[0] != null)
								{
									if (object2[0].toString().equalsIgnoreCase("Due Reminder") && object2[2].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										dueTeamCount = 1;
										if (object2[3] != null)
										{
											teamDueComlList.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 1") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem1TeamCount = 1;
										if (object2[3] != null)
										{
											teamRem1ComlList.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 2") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem2TeamCount = 1;
										if (object2[3] != null)
										{
											teamRem2ComlList.append(object2[3].toString() + ",");
										}
									}
									if (object2[0].toString().equalsIgnoreCase("Reminder 3") && object2[1].toString().equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
									{
										rem3TeamCount = 1;
										if (object2[3] != null)
										{
											teamRem3ComlList.append(object2[3].toString() + ",");
										}
									}

								}
								if (dueTeamCount > 0)
								{
									CDB.setTeamDueDate(String.valueOf(Integer.parseInt(CDB.getTeamDueDate()) + dueTeamCount));
								}
								if (rem1TeamCount > 0)
								{
									CDB.setTeamRem1(String.valueOf(Integer.parseInt(CDB.getTeamRem1()) + rem1TeamCount));
								}
								if (rem2TeamCount > 0)
								{
									CDB.setTeamRem2(String.valueOf(Integer.parseInt(CDB.getTeamRem2()) + rem2TeamCount));
								}
								if (rem3TeamCount > 0)
								{
									CDB.setTeamRem3(String.valueOf(Integer.parseInt(CDB.getTeamRem3()) + rem3TeamCount));
								}
								if (teamDueComlList != null && !teamDueComlList.toString().equalsIgnoreCase(""))
								{
									CDB.setTeamDueCompliance(teamDueComlList.substring(0, (teamDueComlList.toString().length() - 1)));
								}
								else
								{
									CDB.setTeamDueCompliance("0");
								}
								if (teamRem1ComlList != null && !teamRem1ComlList.toString().equalsIgnoreCase(""))
								{
									CDB.setTeamRem1Compliance(teamRem1ComlList.substring(0, (teamRem1ComlList.toString().length() - 1)));
								}
								else
								{
									CDB.setTeamRem1Compliance("0");
								}
								if (teamRem2ComlList != null && !teamRem2ComlList.toString().equalsIgnoreCase(""))
								{
									CDB.setTeamRem2Compliance(teamRem2ComlList.substring(0, (teamRem2ComlList.toString().length() - 1)));
								}
								else
								{
									CDB.setTeamRem2Compliance("0");
								}
								if (teamRem3ComlList != null && !teamRem3ComlList.toString().equalsIgnoreCase(""))
								{
									CDB.setTeamRem3Compliance(teamRem3ComlList.substring(0, (teamRem3ComlList.toString().length() - 1)));
								}
								else
								{
									CDB.setTeamRem3Compliance("0");
								}
							}
						}
					}
					taskList.setLength(0);
					finalDataList.add(CDB);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTotalComplByStatus of class " + getClass(), e);
		}
		return finalDataList;
	}

	/*@SuppressWarnings("rawtypes")
	public Map<Integer, String> getAllSubDeptByDeptId(String deptId)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		Map<Integer, String> subdeptMap = new LinkedHashMap<Integer, String>();
		List dataList = null;
		try
		{
			qryString.append("select id,subdeptname from subdepartment where status='Active' && deptid=" + deptId + " order by subdeptname asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						subdeptMap.put((Integer) objectCol[0], objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return subdeptMap;
	}*/

	public JSONArray getAllSubDeptByDeptId(String deptId)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		// Map<Integer, String> subdeptMap = new LinkedHashMap<Integer,
		// String>();
		JSONArray subdeptMap = new JSONArray();
		List dataList = null;
		try
		{
			qryString.append("select id,subdeptname from subdepartment where status='Active'  ");
			String userType=(String) session.get("userTpe");
			if(userType!=null && !userType.equalsIgnoreCase("M"))
			{
				qryString.append("&& deptid=" + deptId + "");
			}
			qryString.append("order by subdeptname asc");
			
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					JSONObject jsonObj = new JSONObject();
					if (objectCol[0] != null && !objectCol[1].toString().equals(""))
					{
						jsonObj.put("id", objectCol[0].toString());
						jsonObj.put("name", objectCol[1].toString());
						subdeptMap.add(jsonObj);
					}

				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTaskTypeDetails of class " + getClass(), exp);
		}
		return subdeptMap;
	}

	@SuppressWarnings("rawtypes")
	public List<ComplianceDashboardBean> gettotalProductivity(Map<String, String> employeeMap, String fromDate, String toDate, String userId)
	{
		List<ComplianceDashboardBean> finalDataList = new ArrayList<ComplianceDashboardBean>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceDashboardBean CDB = null;
			StringBuilder complList = new StringBuilder();
			StringBuilder graphMap = null;
			if (employeeMap != null && employeeMap.size() > 0)
			{
				for (Map.Entry<String, String> entry : employeeMap.entrySet())
				{
					CDB = new ComplianceDashboardBean();
					graphMap = new StringBuilder();

					if (userId.equalsIgnoreCase(entry.getKey()))
					{
						CDB.setName(entry.getValue() + " (Self) ");
					}
					else
					{
						CDB.setName(entry.getValue());
					}
					List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details  WHERE dueDate BETWEEN '" + fromDate + "' AND  '" + toDate + "'", connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							String str = "#";
							str = str + object[1].toString();
							if (str.contains("#" + entry.getKey() + "#"))
							{
								complList.append(object[0].toString() + ",");
							}
						}
					}
					if (complList != null && complList.length() > 0)
					{
						CDB.setCompId(complList.toString().substring(0, complList.length() - 1));

						String qry = "SELECT count(*) FROM compliance_details  WHERE id IN(" + complList.toString().substring(0, complList.length() - 1) + ") ";
						List totalData = cbt.executeAllSelectQuery(qry, connectionSpace);
						if (totalData != null && totalData.size() > 0)
						{
							CDB.setTotalTask(String.valueOf(totalData.get(0).toString()));
						}
						qry = "SELECT count(*) FROM compliance_details  WHERE actiontaken='Snooze' AND id IN(" + complList.toString().substring(0, complList.length() - 1) + ") ";
						List snoozeData = cbt.executeAllSelectQuery(qry, connectionSpace);
						if (snoozeData != null && snoozeData.size() > 0)
						{
							CDB.setSnooze(String.valueOf(snoozeData.get(0).toString()));
						}

						qry = "Select dueDate,actionTakenDate,actionTaken,dueTime,actionTakenTime FROM  compliance_report  where complID IN(" + complList.toString().substring(0, complList.length() - 1) + ") " + " AND (actionTaken='Recurring' OR actionTaken='Done' OR actionTaken='Snooze') ";
						List data = cbt.executeAllSelectQuery(qry, connectionSpace);
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								int onTime = 0, offTime = 0;
								Object[] object = (Object[]) iterator.next();
								if (object[2] != null)
								{
									if (object[2].toString().equalsIgnoreCase("Recurring") || object[2].toString().equalsIgnoreCase("Done"))
									{
										String strDue = object[0].toString() + " " + object[3].toString();
										String strCurrent = object[1].toString() + " " + object[4].toString();
										boolean status = DateUtil.compareDateTime(strDue, strCurrent);
										if (object[0].toString().equalsIgnoreCase(object[1].toString()) || !status)
										{
											onTime = 1;
										}
										else
										{
											offTime = 1;
										}
									}
								}
								if (onTime > 0)
								{
									CDB.setOnTime(String.valueOf(Integer.parseInt(CDB.getOnTime()) + onTime));
								}
								if (offTime > 0)
								{
									CDB.setOffTime(String.valueOf(Integer.parseInt(CDB.getOffTime()) + offTime));
								}
							}
							int perOnTime = (Integer.parseInt(CDB.getOnTime()) * 100) / Integer.parseInt(CDB.getTotalTask());
							CDB.setPerOnTime(String.valueOf(perOnTime));
							int perOffTime = (Integer.parseInt(CDB.getOffTime()) * 100) / Integer.parseInt(CDB.getTotalTask());
							CDB.setPerOffTime(String.valueOf(perOffTime));
						}
						String missedQry = "SELECT count(*) FROM compliance_details WHERE  id IN(" + complList.toString().substring(0, complList.length() - 1) + ")  AND dueDate > '" + DateUtil.getCurrentDateUSFormat() + "' AND actionTaken!='Done' ";
						/*System.out.println(missedQry);*/
						List missedList = cbt.executeAllSelectQuery(missedQry, connectionSpace);
						if (missedList != null && missedList.size() > 0)
						{
							CDB.setMissed(missedList.get(0).toString());
							int perMissed = (Integer.parseInt(CDB.getMissed()) * 100) / Integer.parseInt(CDB.getTotalTask());
							CDB.setPerMissed(String.valueOf(perMissed));
						}
						graphMap.append(CDB.getOnTime() + ",");
						graphMap.append(CDB.getOffTime() + ",");
						graphMap.append(CDB.getSnooze() + ",");
						graphMap.append(CDB.getMissed());
						CDB.setGraph(graphMap.toString());
						graphMap.setLength(0);
						complList.setLength(0);
					}
					finalDataList.add(CDB);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getTotalComplByStatus of class " + getClass(), e);
		}
		return finalDataList;
	}

	public String getMappedAllContactId(String empId, String moduleName)
	{
		String contactIds = "";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String mappedId = new ComplianceEditGridAction().getLevelHierarchy(moduleName, empId);
		String strQuery = "SELECT id FROM compliance_contacts WHERE moduleName='" + moduleName + "' AND emp_id IN(" + mappedId + ")";
		List dataList = cbt.executeAllSelectQuery(strQuery, connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (int i = 0; i < dataList.size(); i++)
			{
				contactIds += dataList.get(i).toString() + ",";
			}
		}
		return contactIds;
	}
}
