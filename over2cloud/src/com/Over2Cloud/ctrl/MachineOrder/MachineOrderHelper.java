package com.Over2Cloud.ctrl.MachineOrder;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class MachineOrderHelper {

	public List getServiceDept_SubDept(String module, SessionFactory connection) {
		// TODO Auto-generated method stub
		/*
		 * SELECT DISTINCT dept.id, dept.deptName FROM department AS dept
INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id
INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id
WHERE cc.moduleName="ORD" 
		 */

		List dept_subdeptList = null;
		StringBuilder qry = new StringBuilder();
		try
		{
			if (module.equalsIgnoreCase("ORD"))
			{
				qry.append("SELECT DISTINCT dept.id, dept.deptName FROM department AS dept");
				qry.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id");
				qry.append(" INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id");
				qry.append(" WHERE cc.moduleName='"+module+"'");
			}
			
			else
			{
				qry.append(" select distinct dept.id,dept.deptName from department as dept");
				qry.append("  ORDER BY dept.deptName ASC");
			}
			System.out.println("qry "+qry);
			Session session = null;
			Transaction transaction = null;
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			dept_subdeptList = session.createSQLQuery(qry.toString()).list();
			transaction.commit();

		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dept_subdeptList;
		
	}

	public String getdeptIdbyName(String deptName, SessionFactory connection) {
		// TODO Auto-generated method stub
		/*
		 * SELECT DISTINCT dept.id, dept.deptName FROM department AS dept
INNER JOIN subdepartment AS subdept ON subdept.deptid=dept.id
INNER JOIN compliance_contacts AS cc ON cc.fromDept_id=dept.id
WHERE cc.moduleName="ORD"
		 */

		String dept_subdeptList = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			//qry.append(");
			List dataListEcho=	cbt.executeAllSelectQuery("Select id, deptName from department where deptName like '%"+deptName+"%'".toString(), connection);
			//dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (dataListEcho != null && dataListEcho.size() > 0)
			{

				for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
				{
					 Object[] object = (Object[]) iterator.next();
					 //machineSerialList.put(object[0].toString(), object[1].toString());
					 dept_subdeptList = object[0].toString();
				}
			}

		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		return dept_subdeptList;
		
	}


// get the employee name from id from employee_basic
// this method is used in service file for machine order 
public String empNameGet(String id, SessionFactory connection){
	
			String empName = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			try
			{
				//qry.append(");
				List dataListEcho=	cbt.executeAllSelectQuery("SELECT emp.empName, emp.mobOne, dept.deptName FROM employee_basic AS emp INNER JOIN  department AS dept ON emp.deptname= dept.id where emp.id ="+id+"".toString(), connection);
				//dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		
				if (dataListEcho != null && dataListEcho.size() > 0)
				{
		
					for (Iterator iterator = dataListEcho.iterator(); iterator.hasNext();)
					{
						 Object[] object = (Object[]) iterator.next();
						 //machineSerialList.put(object[0].toString(), object[1].toString());
						 empName = object[0].toString()+"#"+object[1].toString()+"#"+object[2].toString();
					}
				}
		
			}
			catch (SQLGrammarException e)
			{
				e.printStackTrace();
			}
			
			return empName;
	
	}





}
