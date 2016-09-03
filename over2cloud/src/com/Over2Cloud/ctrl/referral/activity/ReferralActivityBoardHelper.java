package com.Over2Cloud.ctrl.referral.activity;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonOperInterface;

public class ReferralActivityBoardHelper
{
	
	@SuppressWarnings("rawtypes")
	public String getEmpNameMobByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		String empDetails = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("SELECT empName, mobOne FROM employee_basic WHERE  id="+empId, connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString();
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empDetails;
	}
	/*public String getSubDeptByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		String subDeptName = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("select sub.subdeptname,cc.moduleName from subdepartment as sub inner join compliance_contacts as cc on cc.forDept_id = sub.id where  cc.emp_id ='"+empId+"' and cc.moduleName='CRF'", connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					subDeptName = object[0].toString() ;
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return subDeptName;
	}*/
	
	public String getSubDeptByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		@SuppressWarnings("unused")
		String subDeptName = null;
		String temp="";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select sub.subdeptname from subdepartment as sub inner join compliance_contacts as cc on cc.forDept_id = sub.id where  cc.emp_id ='"+empId+"' and cc.moduleName='CRF' and cc.work_status='0' ", connection);

			if (dataList != null && dataList.size() > 0) {
				subDeptName = dataList.get(0).toString();
				for(Object s:dataList)
				{
					temp=temp+"'"+s.toString()+"'"+",";
				}
				temp=temp.substring(0,temp.length()-1);
				/*subDeptName = subDeptName.replace("[", "").replace("]", "");*/
				//System.out.println("subDeptName "+temp);
				
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return temp;
	}
	
	public String getNursingUnitByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		@SuppressWarnings("unused")
		String subDeptName = null;
		String temp="";
		try
		{
			List dataList = cbt.executeAllSelectQuery("select cc.location from compliance_contacts as cc  where  cc.emp_id ='"+empId+"' and cc.work_status='0' and cc.moduleName='CRF' and cc.forDept_id!='12'", connection);

			if (dataList != null && dataList.size() > 0) {
				subDeptName = dataList.get(0).toString();
				for(Object s:dataList)
				{
					temp=temp+"'"+s.toString()+"'"+",";
				}
				temp=temp.substring(0,temp.length()-1);
				/*subDeptName = subDeptName.replace("[", "").replace("]", "");*/
			//	System.out.println("location "+temp);
				
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return temp;
	}
}
